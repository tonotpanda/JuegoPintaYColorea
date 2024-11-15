package com.example.juego

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*
import java.util.Stack

class PaintView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var paint: Paint = Paint()
    private var currentColor: Int = Color.RED
    private lateinit var bitmap: Bitmap
    private lateinit var canvasBitmap: Canvas
    private lateinit var referenceBitmap: Bitmap
    private var errorCount = 0
    private var currentImageIndex = 0
    private var onImageCompletedListener: (() -> Unit)? = null
    private var isWaitingForNextImage = false
    private var isInteractionBlocked = false

    // Variables para el porcentaje de acierto
    private var totalPixels = 0
    private var filledPixels = 0

    val img = listOf(
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

    init {
        paint.isAntiAlias = true
        paint.isDither = true
        paint.style = Paint.Style.FILL
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.color = currentColor
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvasBitmap = Canvas(bitmap)
        loadImage(currentImageIndex, w, h)
    }


    private fun loadImage(index: Int, w: Int, h: Int) {
        if (index < 0 || index >= img.size) {
            throw IllegalArgumentException("Índice de imagen fuera de rango")
        }

        // Limpiar el bitmap antes de cargar la nueva imagen
        canvasBitmap.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)

        try {
            val drawable = ContextCompat.getDrawable(context, img[index].second)
            val referenceDrawable = ContextCompat.getDrawable(context, img[index].first)

            // Obtener dimensiones originales
            val originalWidth = drawable?.intrinsicWidth ?: 0
            val originalHeight = drawable?.intrinsicHeight ?: 0

            // Calcular la escala para mantener proporciones
            val scale = Math.min(w.toFloat() / originalWidth, h.toFloat() / originalHeight)

            // Calcular nuevas dimensiones
            val newWidth = (originalWidth * scale).toInt()
            val newHeight = (originalHeight * scale).toInt()

            // Establecer límites y dibujar la imagen escalada
            drawable?.setBounds(0, 0, newWidth, newHeight)
            drawable?.draw(canvasBitmap)

            referenceBitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888)
            val referenceCanvas = Canvas(referenceBitmap)
            referenceDrawable?.setBounds(0, 0, newWidth, newHeight)
            referenceDrawable?.draw(referenceCanvas)

            // Inicializar el conteo de píxeles
            totalPixels = 0 // Reiniciar totalPixels
            filledPixels = 0 // Reiniciar filledPixels

            // Contar solo los píxeles válidos
            for (y in 0 until newHeight) {
                for (x in 0 until newWidth) {
                    if (!isNearBlack(referenceBitmap.getPixel(x, y), currentImageIndex)) {
                        totalPixels++ // Solo contar píxeles que no son casi negros
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error al cargar la imagen", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
    }

    private fun setFilledPixelsForNextImage() {
        // Reiniciar filledPixels
        filledPixels = 0

        // Verificamos el porcentaje de píxeles pintados por imagen
        when (currentImageIndex) {

            0 -> filledPixels = (totalPixels * 0.095).toInt() //abeja
            1 -> filledPixels = (totalPixels * 0.45).toInt() //arcoiris
            2 -> filledPixels = (totalPixels * 0.09).toInt() //caracol
            3 -> filledPixels = (totalPixels * 0.1055).toInt() //elefante
            4 -> filledPixels = (totalPixels * 0.083).toInt() //gusano1


            5 -> filledPixels = (totalPixels * 0.20).toInt() //gusano2

            6 -> filledPixels = (totalPixels * 0.20).toInt()
            7 -> filledPixels = (totalPixels * 0.20).toInt()
            8 -> filledPixels = (totalPixels * 0.20).toInt()
            9 -> filledPixels = (totalPixels * 0.20).toInt()
            10 -> filledPixels = (totalPixels * 0.15).toInt()
            11 -> filledPixels = (totalPixels * 0.20).toInt()
            12 -> filledPixels = (totalPixels * 0.15).toInt()
            13 -> filledPixels = (totalPixels * 0.15).toInt()
            14 -> filledPixels = (totalPixels * 0.40).toInt()
            15 -> filledPixels = (totalPixels * 0.30).toInt()
            else -> filledPixels = totalPixels // Por defecto, llena toda la imagen
        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Si las interacciones están bloqueadas, ignoramos los toques
        if (isInteractionBlocked) {
            return false
        }

        if (event.action == MotionEvent.ACTION_DOWN) {
            val x = event.x.toInt()
            val y = event.y.toInt()

            if (x in 0 until bitmap.width && y in 0 until bitmap.height) {
                val correctColor = referenceBitmap.getPixel(x, y)

                if (!isNearBlack(correctColor, currentImageIndex)) {
                    if (areColorsSimilar(currentColor, correctColor)) {
                        startFloodFill(Point(x, y))
                    } else {
                        Toast.makeText(context, "Color incorrecto", Toast.LENGTH_SHORT).show()
                        errorCount++
                    }
                }
            }
        }
        return true
    }

    private fun startFloodFill(point: Point) {
        GlobalScope.launch(Dispatchers.Default) {
            floodFill(bitmap, point, bitmap.getPixel(point.x, point.y), currentColor)
            withContext(Dispatchers.Main) {
                invalidate()
                if (isImageCompleted()) {
                    // Bloquear interacciones antes de pasar a la siguiente imagen
                    isInteractionBlocked = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        val nextIndex = (currentImageIndex + 1) % img.size
                        onImageCompletedListener?.invoke()
                        setCurrentImage(nextIndex) // Pasar a la siguiente imagen
                        setFilledPixelsForNextImage() // Establecer el porcentaje de llenado correcto
                        isInteractionBlocked = false // Liberar interacciones después de 2 segundos
                    }, 2000) // 2000 ms = 2 segundos
                }
            }
        }
    }


    private fun floodFill(bitmap: Bitmap, point: Point, targetColor: Int, fillColor: Int) {
        val width = bitmap.width
        val height = bitmap.height

        if (targetColor == fillColor || isNearBlack(targetColor, currentImageIndex)) return

        val pixels = IntArray(width * height)
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)

        val stack = Stack<Point>()
        stack.push(point)

        while (stack.isNotEmpty()) {
            val p = stack.pop()
            val x = p.x
            val y = p.y

            if (x in 0 until width && y in 0 until height) {
                val index = y * width + x
                val pixelColor = pixels[index]

                if (!isNearBlack(pixelColor, currentImageIndex) && areColorsSimilar(pixelColor, targetColor) && isNearWhite(pixelColor)) {
                    pixels[index] = fillColor
                    filledPixels++ // Incrementar el conteo de píxeles rellenados

                    // Agregar los píxeles vecinos
                    stack.push(Point(x + 1, y))
                    stack.push(Point(x - 1, y))
                    stack.push(Point(x, y + 1))
                    stack.push(Point(x, y - 1))
                }
            }
        }
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
    }

    private fun isNearBlack(color: Int, index: Int): Boolean {
        return Color.red(color) < 80 && Color.green(color) < 80 && Color.blue(color) < 80 // Mantener el umbral original
    }

    private fun isNearWhite(color: Int): Boolean {
        val tolerance = 200
        return Color.red(color) > tolerance && Color.green(color) > tolerance && Color.blue(color) > tolerance
    }

    private fun areColorsSimilar(color1: Int, color2: Int): Boolean {
        val tolerance = 30 // Mantener la tolerancia original
        return Math.abs(Color.red(color1) - Color.red(color2)) < tolerance &&
                Math.abs(Color.green(color1) - Color.green(color2)) < tolerance &&
                Math.abs(Color.blue(color1) - Color.blue(color2)) < tolerance
    }

    private fun isImageCompleted(): Boolean {

        val completionPercentage: Int
        if (totalPixels > 0) {
            completionPercentage = (filledPixels.toFloat() / totalPixels * 106).toInt()
        } else {
            completionPercentage = 0 // Si no hay píxeles válidos, el porcentaje es 0
        }


        if (completionPercentage >= 100) {
            Toast.makeText(context, "¡Imagen completada al 100%!", Toast.LENGTH_SHORT).show()
            return true
        } else {
            return false
        }

    }

    fun setColor(color: Int) {
        currentColor = color
        paint.color = currentColor
    }

    fun setCurrentImage(index: Int) {
        if (!isWaitingForNextImage) { // Asegurarse de que no se pase a la siguiente imagen mientras se espera
            currentImageIndex = index
            loadImage(currentImageIndex, width, height)
            invalidate()
        }
    }

    fun setOnImageCompletedListener(listener: () -> Unit) {
        onImageCompletedListener = listener
    }
}
