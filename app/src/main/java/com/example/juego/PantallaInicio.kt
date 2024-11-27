package com.example.juego  // Asegúrate de que el paquete sea correcto

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PantallaInicio : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
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
        playSound(R.raw.boton)
        val intent = Intent(this, ImagenSeleccionado::class.java)
        intent.putExtra("character_type", characterType)  // Enviar tipo de personaje
        startActivity(intent)
    }

    private fun playSound(soundResId: Int) {
        // Liberar el MediaPlayer anterior si ya está inicializado
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }

        mediaPlayer = MediaPlayer.create(this, soundResId)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener { it.release() }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }
}
