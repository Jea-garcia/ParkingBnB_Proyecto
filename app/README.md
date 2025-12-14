ParkingBnB

ParkingBnB es una aplicación móvil Android desarrollada en Android Studio que permite a personas comunes arrendar sus estacionamientos particulares por horas, mientras que los conductores pueden encontrar espacios disponibles en zonas céntricas, eventos o lugares de alta demanda.
La aplicación busca ahorrar tiempo al usuario, reducir la congestión y generar ingresos extra para quienes tienen espacios disponibles en sus hogares.

Descripción del Proyecto

ParkingBnB funciona como un intermediario entre:

Propietarios de estacionamientos (casas, edificios, patios)

Usuarios conductores que necesitan estacionar cerca de su destino

Ejemplos de uso:

    - Ir a una plaza
    - Asistir a un concierto
    - Visitar el centro de la ciudad

Tecnologías Utilizadas

Lenguaje: Kotlin
IDE: Android Studio
Arquitectura: MVVM (Repository + Data + UI)
Consumo API: Retrofit
Base de Datos Local: SQLite (DBHelper)
Diseño UI: XML

Estructura del Proyecto

com.example.parkingbnb_proyecto
│
├── data
│ ├── api
│ │ ├── ApiService
│ │ └── RetrofitClient
│ ├── model
│ │ ├── Auto
│ │ ├── AutoLocal
│ │ └── Usuario
│ └── repository
│ └── AutosRepository.kt
│
├── models
│ └── DBHelper
│
├── ui
│ ├── adapter
│ ├── AddAutoActivity
│ ├── AgregarUsuarioActivity
│ ├── HomeActivity
│ ├── ListaAutosActivity
│ ├── ListaAutosLocalesActivity
│ └── MainActivity
│
└── res
├── layout
├── drawable
├── values

Funcionalidades Principales

    - Registro de usuarios
    - Agregar autos y estacionamientos
    - Listado de estacionamientos disponibles
    - Visualización de estacionamientos locales
    - Persistencia de datos con SQLite
    - Preparado para consumo de API REST

Instalación y Ejecución

Clona el repositorio:
git clone https://github.com/tu-usuario/ParkingBnB_Proyecto.git
Abre el proyecto en Android Studio
Sincroniza Gradle
Ejecuta en un emulador o dispositivo físico

Estado del Proyecto

    - Proyecto en desarrollo académico

Autor
Jean Paul García
Bryan Carmona
Estudiantes – Duoc UC 
Proyecto académico Android

Licencia
Este proyecto es solo para fines educativos.