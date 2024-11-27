package com.example.juego

import ColorAdapter
import android.annotation.SuppressLint
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
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private val img = listOf(
        Pair(R.drawable.abeja_color, R.drawable.abeja_blanca),
        Pair(R.drawable.arcoiris_color, R.drawable.arcoiris_blanco),
        Pair(R.drawable.caracol_color, R.drawable.caracol_blanco),
        Pair(R.drawable.elefante_color, R.drawable.elefante_blanco),
        Pair(R.drawable.gusano1_color, R.drawable.gusano1_blanco),
        Pair(R.drawable.mono2_color, R.drawable.mono2_blanco),
        Pair(R.drawable.luna_color, R.drawable.luna_blanca),
        Pair(R.drawable.medusa_color, R.drawable.medusa_blanca),
        Pair(R.drawable.mono1_color, R.drawable.mono1_blanco),
        Pair(R.drawable.gusano2_color, R.drawable.gusano2_blanco),
        Pair(R.drawable.mono3_color, R.drawable.mono3_blanco),
        Pair(R.drawable.nube_color, R.drawable.nube_blanca),
        Pair(R.drawable.pez_color, R.drawable.pez_blanco),
        Pair(R.drawable.pulpo_color, R.drawable.pulpo_blanco),
        Pair(R.drawable.seta_color, R.drawable.seta_blanca),
        Pair(R.drawable.volcan_color, R.drawable.volcan_blanco)
    )

    private var characterType: String? = null
    private var imageName: String? = null
    private lateinit var tiempoTextView: TextView
    private lateinit var imgColor: ImageView
    private lateinit var paintView: PaintView
    private lateinit var colorAdapter: ColorAdapter
    private lateinit var btn_Salir: Button
    private var currentIndex = 0
    private var tiempoEnSegundos = 0
    private val handler = Handler()
    private var count = 0
    private var dibujosHechos = 0
    private var juegoAcabado = false
    private lateinit var mediaPlayer: MediaPlayer

    private val sounds = arrayOf(R.raw.abeja, R.raw.arcoiris, R.raw.caracol, R.raw.elefante, R.raw.gusano1,
        R.raw.mono2, R.raw.luna, R.raw.medusa, R.raw.mono1, R.raw.gusano2, R.raw.mono3, R.raw.nube, R.raw.pez, R.raw.pulpo, R.raw.seta, R.raw.volcan)



    private val runnable = object : Runnable {
        override fun run() {
            tiempoEnSegundos++
            val minutos = tiempoEnSegundos / 60
            val segundos = tiempoEnSegundos % 60
            tiempoTextView.text = String.format("Tiempo: %02d:%02d", minutos, segundos)
            if (tiempoEnSegundos == (0 * 60 + 20)) {
                handler.removeCallbacks(this)
                juegoAcabado = true
                onImageCompleted()
                finish()
            }
            handler.postDelayed(this, 1000)

        }
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        characterType = intent.getStringExtra("character_type")
        imageName = intent.getStringExtra("character_name")

        tiempoTextView = findViewById(R.id.tiempo)
        imgColor = findViewById(R.id.img_color)
        paintView = findViewById(R.id.paintView)
        btn_Salir = findViewById(R.id.Btn_salir)
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

        btn_Salir.setOnLongClickListener{
            juegoAcabado = true
            onImageCompleted()
            finish()
            true
        }

        // Configura el PaintView para que llame a onImageCompleted cuando una imagen se complete
        paintView.setOnImageCompletedListener { onImageCompleted() }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // No hacer nada cuando se presiona el botón de retroceso
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
        colorAdapter.completeImage()

        currentIndex++
        if (!juegoAcabado){
        if (currentIndex < img.size) {
            playSound(sounds[count])
            count++
            if (count >= sounds.size) {
                count = 0
            }
            juegoAcabado = false
        }else{
            juegoAcabado = true
        }
        }
        if (!juegoAcabado) {
            dibujosHechos++
            loadImages(currentIndex)
            paintView.setCurrentImage(currentIndex)
        } else {
            val errores = paintView.getErrorCount()
            juntarEstadisticas(errores)
            Toast.makeText(this, "¡Juego terminado!", Toast.LENGTH_SHORT).show()

            // Crear el Intent para pasar los datos
            val intent = Intent(this, PantallaFinalActivity::class.java)
            // Pasa los datos al Intent
            intent.putExtra("character_type", characterType)
            intent.putExtra("character_name", imageName)
            intent.putExtra("errores", errores)
            intent.putExtra("dibujos_completados", dibujosHechos)
            intent.putExtra("tiempo", tiempoTextView.text.toString())
            startActivity(intent)
        }
    }

    private fun juntarEstadisticas(errores: Int) {
        // Asegurarte de que no sean nulos
        val personage = characterType ?: "Desconocido"
        val icono = imageName ?: "Desconocido"
        val dibujosCompletados = dibujosHechos
        val tiempo = tiempoTextView.text.toString()

        val alumno = Alumno(personage, icono, dibujosCompletados, tiempo, errores)
        añadirExploradorEnJson(this, alumno)
    }


    private fun añadirExploradorEnJson(context: Context, nuevoAlumno: Alumno) {
        val archivo = File(getExternalFilesDir(null), "Resultado_Alumnos.json")
        val jsonArray = if (archivo.exists()) {
            // Leer el contenido existente y convertirlo a JSONArray
            JSONArray(archivo.readText())
        } else {
            // Crear un nuevo JSONArray si el archivo no existe
            JSONArray()
        }

        // Añadir el nuevo explorador
        jsonArray.put(JSONObject().apply {
            put("Personage", nuevoAlumno.personage)
            put("Icono", nuevoAlumno.icono)
            put("Dibujos Completados", nuevoAlumno.dibujosCompletados)
            put("Tiempo", nuevoAlumno.tiempo)
            put("Errores", nuevoAlumno.errores)
        })

        // Guardar el JSONArray actualizado en el archivo
        FileOutputStream(archivo).use { fos ->
            fos.write(jsonArray.toString().toByteArray())
        }

        // Agrega un log para verificar que se ha creado el archivo
        Log.d("ArchivoJSON", "Archivo creado en: ${archivo.absolutePath}")
    }

}
