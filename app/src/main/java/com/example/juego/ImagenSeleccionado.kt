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
        val arbol = findViewById<ImageView>(R.id.arbol)
        val barco = findViewById<ImageView>(R.id.barco)
        val ciervo = findViewById<ImageView>(R.id.ciervo)
        val gato = findViewById<ImageView>(R.id.gato)
        val girasol = findViewById<ImageView>(R.id.girasol)
        val gusano = findViewById<ImageView>(R.id.gusano)
        val hoguera = findViewById<ImageView>(R.id.hoguera)
        val luna = findViewById<ImageView>(R.id.luna)
        val montaña = findViewById<ImageView>(R.id.montaña)
        val nube = findViewById<ImageView>(R.id.nube)
        val pato = findViewById<ImageView>(R.id.pato)
        val perro = findViewById<ImageView>(R.id.perro)
        val pescador = findViewById<ImageView>(R.id.pescador)
        val planta = findViewById<ImageView>(R.id.planta)
        val sol = findViewById<ImageView>(R.id.sol)
        val zanahoria = findViewById<ImageView>(R.id.zanahoria)

        // Asociar cada imagen con el personaje y el ID de la imagen
        arbol.setOnClickListener { navigateToMainActivity("Arbol", R.drawable.arbol) }
        barco.setOnClickListener { navigateToMainActivity("Barco", R.drawable.barco) }
        ciervo.setOnClickListener { navigateToMainActivity("Ciervo", R.drawable.ciervo) }
        gato.setOnClickListener { navigateToMainActivity("Gato", R.drawable.gato) }
        girasol.setOnClickListener { navigateToMainActivity("Girasol", R.drawable.girasol) }
        gusano.setOnClickListener { navigateToMainActivity("Gusano", R.drawable.gusano) }
        hoguera.setOnClickListener { navigateToMainActivity("Hoguera", R.drawable.hoguera) }
        luna.setOnClickListener { navigateToMainActivity("Luna", R.drawable.luna) }
        montaña.setOnClickListener { navigateToMainActivity("Montaña", R.drawable.montana) }
        nube.setOnClickListener { navigateToMainActivity("Nube", R.drawable.nube) }
        pato.setOnClickListener { navigateToMainActivity("Pato", R.drawable.pato) }
        perro.setOnClickListener { navigateToMainActivity("Perro", R.drawable.perro) }
        pescador.setOnClickListener { navigateToMainActivity("Pescador", R.drawable.pescador) }
        planta.setOnClickListener { navigateToMainActivity("Planta", R.drawable.planta) }
        sol.setOnClickListener { navigateToMainActivity("Sol", R.drawable.sol) }
        zanahoria.setOnClickListener { navigateToMainActivity("Zanahoria", R.drawable.zanahoria) }
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
