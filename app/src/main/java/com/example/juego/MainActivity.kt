package com.example.juego

import ColorAdapter
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val img = listOf(
        Pair(R.drawable.abeja_color, R.drawable.abeja_blanca),
        Pair(R.drawable.arcoiris_color, R.drawable.arcoiris_blanco),
        Pair(R.drawable.caracol_color, R.drawable.caracol_blanco),
        Pair(R.drawable.elefante_color, R.drawable.elefante_blanco),
        Pair(R.drawable.gusano1_color, R.drawable.gusano1_blanco),
        Pair(R.drawable.gusano2_color, R.drawable.gusano2_blanco),
        Pair(R.drawable.luna_color, R.drawable.luna_blanca),
        Pair(R.drawable.medusa_color, R.drawable.medusa_blanca),
        Pair(R.drawable.mono1_color, R.drawable.mono1_blanco),
        Pair(R.drawable.mono2_color, R.drawable.mono2_blanco),
        Pair(R.drawable.mono3_color, R.drawable.mono3_blanco),
        Pair(R.drawable.nube_color, R.drawable.nube_blanca),
        Pair(R.drawable.pez_color, R.drawable.pez_blanco),
        Pair(R.drawable.pulpo_color, R.drawable.pulpo_blanco),
        Pair(R.drawable.seta_color, R.drawable.seta_blanca),
        Pair(R.drawable.volcan_color, R.drawable.volcan_blanco)
    )

    private lateinit var tiempoTextView: TextView
    private lateinit var imgColor: ImageView
    private lateinit var paintView: PaintView
    private lateinit var colorAdapter: ColorAdapter
    private var currentIndex = 0
    private var tiempoEnSegundos = 0
    private val handler = Handler()

    private val runnable = object : Runnable {
        override fun run() {
            tiempoEnSegundos++
            val minutos = tiempoEnSegundos / 60
            val segundos = tiempoEnSegundos % 60
            tiempoTextView.text = String.format("Tiempo: %02d:%02d", minutos, segundos)
            handler.postDelayed(this, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tiempoTextView = findViewById(R.id.tiempo)
        imgColor = findViewById(R.id.img_color)
        paintView = findViewById(R.id.paintView)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Inicializa el adaptador de colores con la paleta de colores de la abeja
        val paleta = Paleta()
        colorAdapter = ColorAdapter(this, paleta.getColorsForCurrentImage(0)) { selectedColor ->
            paintView.setColor(selectedColor)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = colorAdapter

        // Cargar las imágenes y colores del primer índice (abeja)
        loadImages(currentIndex)
        handler.post(runnable)

        // Configura el PaintView para que llame a onImageCompleted cuando una imagen se complete
        paintView.setOnImageCompletedListener { onImageCompleted() }
    }

    private fun loadImages(index: Int) {
        if (index < img.size) { // Verifica que el índice esté dentro de los límites
            val (colorImg, _) = img[index]
            imgColor.setImageResource(colorImg) // Mostrar imagen de color
            updateColorPalette(index) // Actualizar paleta de colores según la imagen
        }
    }

    private fun updateColorPalette(index: Int) {
        val paleta = Paleta()
        // Obtener la paleta de colores asociada con la imagen actual
        val colores = paleta.getColorsForCurrentImage(index)

        // Actualizar el adaptador de colores con la nueva paleta
        colorAdapter.updateColors(colores)
    }

    private fun onImageCompleted() {
        Toast.makeText(this, "Imagen completada!", Toast.LENGTH_SHORT).show()

        // Cargar la siguiente imagen si existe
        currentIndex++
        if (currentIndex < img.size) {
            loadImages(currentIndex)
            paintView.setCurrentImage(currentIndex) // Cambiar la imagen en PaintView
        } else {
            Toast.makeText(this, "¡Has completado todas las imágenes!", Toast.LENGTH_SHORT).show()
            handler.removeCallbacks(runnable)
        }
    }
}
