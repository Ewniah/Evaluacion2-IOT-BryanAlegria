package com.example.evaluacion2_iot

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE) {
        BluetoothAdapter.getDefaultAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnGoToRegister = findViewById<Button>(R.id.btn_go_to_register)
        val tvForgotPassword = findViewById<TextView>(R.id.tv_forgot_password)
        val btnCheckBluetooth = findViewById<Button>(R.id.btn_check_bluetooth)

        btnLogin.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Inicio de Sesión")
                .setMessage("Has iniciado sesión correctamente (simulación).")
                .setPositiveButton("Aceptar", null)
                .show()
        }

        btnGoToRegister.setOnClickListener {
            val intent = Intent(this, RegistrarActivity::class.java)
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, RecuperarActivity::class.java)
            startActivity(intent)
        }

        btnCheckBluetooth.setOnClickListener {
            checkBluetoothStatus()
        }
    }

    private fun checkBluetoothStatus() {
        // Primero, verificamos si el dispositivo tiene Bluetooth
        if (bluetoothAdapter == null) {
            showBluetoothDialog("Dispositivo no compatible", "Este dispositivo no cuenta con Bluetooth.")
            return
        }

        // Verificamos si los permisos están concedidos
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // Si no tenemos permisos, no podemos continuar.
                // En una app real, aquí se solicitarían los permisos. Para la evaluación, solo informamos.
                showBluetoothDialog("Permiso Requerido", "Se necesita permiso para acceder al estado de Bluetooth.")
                return
            }
        }

        // Verificamos el estado (encendido o apagado)
        val statusMessage = if (bluetoothAdapter!!.isEnabled) {
            "El Bluetooth está ENCENDIDO."
        } else {
            "El Bluetooth está APAGADO."
        }

        showBluetoothDialog("Estado de Bluetooth", statusMessage)
    }

    private fun showBluetoothDialog(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Entendido", null)
            .show()
    }
}