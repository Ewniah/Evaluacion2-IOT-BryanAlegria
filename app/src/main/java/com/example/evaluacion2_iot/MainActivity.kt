package com.example.evaluacion2_iot // ⚠️ ¡Asegúrate de que este sea el nombre de tu paquete!

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
// import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // Comentado para evitar errores
import com.example.evaluacion2_iot.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración del ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 1. Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        // 2. Verificar si ya hay sesión iniciada (si el usuario ya entró antes)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            irAHome()
        }

        // --- BOTÓN: LOGIN CON CORREO Y CONTRASEÑA ---
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            irAHome()
                        } else {
                            mostrarAlerta("Error", "Credenciales incorrectas o error de conexión.")
                        }
                    }
            } else {
                mostrarAlerta("Atención", "Por favor completa todos los campos.")
            }
        }

        // --- BOTÓN: LOGIN CON GOOGLE ---
        binding.btnGoogleLogin.setOnClickListener {
            iniciarGoogleSignIn()
        }

        // --- NAVEGACIÓN A OTRAS PANTALLAS ---
        binding.btnGoToRegister.setOnClickListener {
            startActivity(Intent(this, RegistrarActivity::class.java))
        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, RecuperarActivity::class.java))
        }
    }

    // --- CONFIGURACIÓN DE GOOGLE SIGN-IN ---

    // Lanzador para el resultado de Google
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            // Google Login fue exitoso, ahora autenticamos con Firebase
            val account = task.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            mostrarAlerta("Error Google", "Fallo el inicio de sesión: ${e.message}")
        }
    }

    // Función que inicia el intent de Google
    private fun iniciarGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Lee el ID que pusimos en strings.xml
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.signOut() // Cierra sesión previa para permitir elegir cuenta siempre
        googleSignInLauncher.launch(googleSignInClient.signInIntent)
    }

    // Función que intercambia la credencial de Google por una de Firebase
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    irAHome()
                } else {
                    mostrarAlerta("Error", "No se pudo autenticar con Firebase.")
                }
            }
    }

    // --- FUNCIONES AUXILIARES ---

    private fun irAHome() {
        val intent = Intent(this, HomeActivity::class.java)
        // Esto evita que el usuario pueda volver al login presionando "Atrás"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun mostrarAlerta(titulo: String, mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}