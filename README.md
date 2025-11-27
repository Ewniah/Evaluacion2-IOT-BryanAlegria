# Aplicación de Noticias IoT - Evaluación 3

Este proyecto es una aplicación Android nativa desarrollada en Kotlin que integra servicios en la nube mediante **Firebase**. Permite a los usuarios autenticarse, visualizar un feed de noticias en tiempo real y crear nuevo contenido.

## 🚀 Características Principales

### 1. Autenticación Segura (Firebase Auth)
* **Inicio de Sesión Híbrido:** Soporte para autenticación mediante Correo/Contraseña y **Google Sign-In**.
* **Gestión de Usuarios:** Registro de nuevas cuentas y recuperación de contraseña vía correo electrónico.
* **Persistencia:** La sesión del usuario se mantiene activa hasta que decide cerrar sesión explícitamente.

### 2. Base de Datos en la Nube (Cloud Firestore)
* **Lectura en Tiempo Real:** El Home se actualiza automáticamente cuando se agregan o eliminan noticias en la base de datos.
* **Escritura:** Formulario para agregar nuevas noticias con campos de Título, Bajada, Autor, Fecha y Contenido.
* **Eliminación:** Funcionalidad para eliminar noticias manteniendo presionado el ítem (Long Click).

### 3. Interfaz y Navegación
* **RecyclerView:** Listado optimizado para mostrar las noticias.
* **Detalle de Noticia:** Navegación fluida para ver el contenido completo de cada artículo.
* **Diseño:** Uso de Material Design, CardViews y Botones Flotantes (FAB).

## 🛠️ Tecnologías Utilizadas
* Android Studio (Kotlin)
* Firebase Authentication
* Firebase Cloud Firestore
* Google Play Services Auth