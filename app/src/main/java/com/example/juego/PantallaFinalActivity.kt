package com.example.juego

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PantallaFinalActivity : AppCompatActivity() {

    // Variables globales para almacenar las estadísticas
    private var characterType: String? = null
    private var characterName: String? = null
    private var errores: Int = 0
    private var dibujosCompletados: Int = 0
    private var tiempo: String? = null

    // Handler para ocultar la barra de navegación después de 5 segundos
    private val hideHandler = Handler(Looper.getMainLooper())
    private val hideNavigationRunnable = Runnable {
        enableImmersiveMode()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_final)
        enableImmersiveMode()

        // Recuperar los datos pasados desde MainActivity
        characterType = intent.getStringExtra("character_type")
        characterName = intent.getStringExtra("character_name")
        errores = intent.getIntExtra("errores", 0)
        dibujosCompletados = intent.getIntExtra("dibujos_completados", 0)
        tiempo = intent.getStringExtra("tiempo")

        val btn_reiniciar = findViewById<ImageView>(R.id.btn_reiniciar)
        val btn_salir = findViewById<ImageView>(R.id.btn_salir)

        btn_reiniciar.setOnClickListener {
            // Reiniciar estadísticas
            resetStatistics()
            // Navegar a PantallaInicio
            val intent = Intent(this, PantallaInicio::class.java)
            startActivity(intent)
        }

        btn_salir.setOnClickListener {
            finishAffinity()

        }

        // Monitorear los cambios en la visibilidad del sistema
        window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            if ((visibility and View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                // Si los botones de navegación son visibles, ocultarlos después de 5 segundos
                hideHandler.removeCallbacks(hideNavigationRunnable)
                hideHandler.postDelayed(hideNavigationRunnable, 5000)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        enableImmersiveMode() // Asegura que el modo inmersivo siga activo
    }

    override fun onDestroy() {
        super.onDestroy()
        hideHandler.removeCallbacks(hideNavigationRunnable) // Evita fugas de memoria
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // No hacer nada cuando se presiona el botón de retroceso
    }

    // Método para reiniciar las estadísticas
    private fun resetStatistics() {
        // Reiniciar las estadísticas de las variables
        characterType = null
        characterName = null
        errores = 0
        dibujosCompletados = 0
        tiempo = "00:00"
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun enableImmersiveMode() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                )
    }
}
