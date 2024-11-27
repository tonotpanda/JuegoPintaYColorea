package com.example.juego

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PantallaFinalActivity : AppCompatActivity() {

    // Variables globales para almacenar las estadísticas
    private var characterType: String? = null
    private var characterName: String? = null
    private var errores: Int = 0
    private var dibujosCompletados: Int = 0
    private var tiempo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_final)

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
            finish()
        }
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
}
