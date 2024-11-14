package com.example.juego

import ColorAdapter
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juego.PaintView
import com.example.juego.Paleta
import com.example.juego.R

class MainActivity : AppCompatActivity() {

    // Lista de imágenes (color y blanco)
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

    // Paletas de colores correspondientes a cada imagen
    private val paletas = mapOf(
        0 to Paleta().colorsAbeja,
        1 to Paleta().colorsArcoiris,
        2 to Paleta().colorsCaracol,
        3 to Paleta().colorsElefante,
        4 to Paleta().colorsGusano1,
        5 to Paleta().colorsGusano2,
        6 to Paleta().colorsLuna,
        7 to Paleta().colorsMedusa,
        8 to Paleta().colorsMono1,
        9 to Paleta().colorsMono2,
        10 to Paleta().colorsMono3,
        11 to Paleta().colorsNube,
        12 to Paleta().colorsPez,
        13 to Paleta().colorsPulpo,
        14 to Paleta().colorsSeta,
        15 to Paleta().colorsVolcan
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

        val colorRecyclerView = findViewById<RecyclerView>(R.id.colorRecyclerView)
        colorRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Inicializa el adaptador de colores con la paleta de colores de la abeja
        colorAdapter = ColorAdapter(Paleta().colorsAbeja) { selectedColor ->
            paintView.setColor(selectedColor)
        }

        colorRecyclerView.adapter = colorAdapter

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
        // Obtener la paleta de colores asociada con la imagen actual
        val colores = paletas[index] ?: return // Asegúrate de que 'colores' no sea nulo

        // Actualizar el adaptador de colores con la nueva paleta
        colorAdapter.updateColors(colores)  // Asegúrate de que 'colores' sea una lista de enteros
    }

    private fun onImageCompleted() {
        Toast.makeText(this, "Imagen completada!", Toast.LENGTH_SHORT).show()

        // Cargar la siguiente imagen si existe
        currentIndex++
        if (currentIndex < img.size) {
            loadImages(currentIndex)
            paintView.setCurrentImage(currentIndex) // Cambiar la imagen en PaintView
        } else {
            Toast.makeText(this, "¡Has completado todas las imágenes!", Toast.LENGTH_LONG).show()
        }
    }
}
