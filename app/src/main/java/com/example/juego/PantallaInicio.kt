package com.example.juego  // Asegúrate de que el paquete sea correcto

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PantallaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pantalla_inicio)

        val explorador = findViewById<ImageView>(R.id.explorador)
        val exploradora = findViewById<ImageView>(R.id.exploradora)

        explorador.setOnClickListener {
            navigateToImagenSeleccionada("explorador")
        }

        exploradora.setOnClickListener {
            navigateToImagenSeleccionada("exploradora")
        }
    }

    private fun navigateToImagenSeleccionada(characterType: String) {
        val intent = Intent(this, ImagenSeleccionado::class.java)
        intent.putExtra("character_type", characterType)  // Enviar tipo de personaje
        startActivity(intent)
    }
}
