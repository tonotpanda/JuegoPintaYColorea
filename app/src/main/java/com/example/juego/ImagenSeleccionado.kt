package com.example.juego

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ImagenSeleccionado : AppCompatActivity() {

    private var characterType: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.imagen_seleccionada)

        // Recibir el tipo de personaje
        characterType = intent.getStringExtra("character_type")

        // Manejadores de clic para cada imagen en el GridLayout
        val arbol = findViewById<ImageView>(R.id.ballena)
        val barco = findViewById<ImageView>(R.id.canguro)
        val ciervo = findViewById<ImageView>(R.id.caracol)
        val gato = findViewById<ImageView>(R.id.gato)
        val girasol = findViewById<ImageView>(R.id.conejo)
        val gusano = findViewById<ImageView>(R.id.delfin)
        val hoguera = findViewById<ImageView>(R.id.elefante)
        val luna = findViewById<ImageView>(R.id.hipo)
        val montaña = findViewById<ImageView>(R.id.koala)
        val nube = findViewById<ImageView>(R.id.leon)
        val pato = findViewById<ImageView>(R.id.mono)
        val perro = findViewById<ImageView>(R.id.oveja)
        val pescador = findViewById<ImageView>(R.id.pajaro)
        val planta = findViewById<ImageView>(R.id.pulpo)
        val sol = findViewById<ImageView>(R.id.rana)
        val zanahoria = findViewById<ImageView>(R.id.vaca)

        // Asociar cada imagen con el personaje y el ID de la imagen
        arbol.setOnClickListener { navigateToMainActivity("Ballena", R.drawable.ballena) }
        barco.setOnClickListener { navigateToMainActivity("Canguro", R.drawable.canguro) }
        ciervo.setOnClickListener { navigateToMainActivity("Caracol", R.drawable.caracol) }
        gato.setOnClickListener { navigateToMainActivity("Gato", R.drawable.gato) }
        girasol.setOnClickListener { navigateToMainActivity("Conejo", R.drawable.conejo) }
        gusano.setOnClickListener { navigateToMainActivity("Delfin", R.drawable.delfin) }
        hoguera.setOnClickListener { navigateToMainActivity("Elefante", R.drawable.elefante) }
        luna.setOnClickListener { navigateToMainActivity("Hipo", R.drawable.hipo) }
        montaña.setOnClickListener { navigateToMainActivity("Koala", R.drawable.koala) }
        nube.setOnClickListener { navigateToMainActivity("Leon", R.drawable.leon) }
        pato.setOnClickListener { navigateToMainActivity("Mono", R.drawable.mono) }
        perro.setOnClickListener { navigateToMainActivity("Oveja", R.drawable.oveja) }
        pescador.setOnClickListener { navigateToMainActivity("Pajaro", R.drawable.pajaro) }
        planta.setOnClickListener { navigateToMainActivity("Pulpo", R.drawable.pulpo) }
        sol.setOnClickListener { navigateToMainActivity("Rana", R.drawable.rana) }
        zanahoria.setOnClickListener { navigateToMainActivity("Vaca", R.drawable.vaca) }
    }

    private fun navigateToMainActivity(imageName: String, imageId: Int) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("character_type", characterType)
        intent.putExtra("character_name", imageName)
        intent.putExtra("image_id", imageId)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK) // Agregar estas banderas
        startActivity(intent)
    }
}
