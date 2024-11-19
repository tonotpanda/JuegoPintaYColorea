package com.example.juego

import ColorAdapter
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

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
    private var count = 0
    private lateinit var mediaPlayer: MediaPlayer
    private val sounds = arrayOf(R.raw.abeja, R.raw.arcoiris, R.raw.caracol, R.raw.elefante)

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

        // Inicializa el adaptador de colores
        val paleta = Paleta()
        colorAdapter = ColorAdapter(this, paleta.getColorsForCurrentImage(0)) { selectedColor ->
            paintView.setColor(selectedColor)
            playSound(R.raw.boton)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 4)
        recyclerView.adapter = colorAdapter

        // Cargar imágenes y sonidos en un hilo diferente
        loadResources()

        handler.post(runnable)

        // Configura el PaintView para que llame a onImageCompleted cuando una imagen se complete
        paintView.setOnImageCompletedListener { onImageCompleted() }
    }

    private fun loadResources() {
        CoroutineScope(Dispatchers.IO).launch {
            // Pre-cargar imágenes y sonidos aquí
            // Por ejemplo, cargar el primer drawable en un Bitmap
            for (index in img.indices) {
                val (colorImg, _) = img[index]
                resources.getDrawable(colorImg, null) // Pre-cargar el drawable
            }
            withContext(Dispatchers.Main) {
                loadImages(currentIndex) // Cargar la imagen inicial
            }
        }
    }

    private fun loadImages(index: Int) {
        if (index < img.size) {
            val (colorImg, _) = img[index]
            imgColor.setImageResource(colorImg) // Mostrar imagen de color
            updateColorPalette(index) // Actualizar paleta de colores
        }
    }

    private fun updateColorPalette(index: Int) {
        val paleta = Paleta()
        val colores = paleta.getColorsForCurrentImage(index)
        colorAdapter.updateColors(colores)
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

    private fun onImageCompleted() {
        playSound(sounds[count])
        count++
        if (count >= sounds.size) {
            count = 0
        }

        currentIndex++
        if (currentIndex < img.size) {
            loadImages(currentIndex)
            paintView.setCurrentImage(currentIndex)
        } else {
            Toast.makeText(this, "¡Juego terminado!", Toast.LENGTH_SHORT).show()
        }
    }
}
