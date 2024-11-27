import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.juego.R

class ColorAdapter(
    private val context: Context,
    private var colors: List<Int>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION // Para rastrear el color seleccionado

    inner class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val colorImageView: ImageView = view.findViewById(R.id.colorImageView)

        init {
            view.setOnClickListener {
                // Actualiza la posición seleccionada
                val previousPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousPosition) // Notifica que el item anterior ha cambiado
                notifyItemChanged(selectedPosition) // Notifica que el nuevo item ha cambiado
                onClick(colors[adapterPosition]) // Llama al callback con el color seleccionado
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        // Crear un GradientDrawable para definir el fondo y el borde
        val borderDrawable = GradientDrawable()
        borderDrawable.shape = GradientDrawable.OVAL // Forma redonda
        borderDrawable.setColor(colors[position]) // Establece el color de fondo

        // Si el item está seleccionado, le ponemos el borde negro
        if (position == selectedPosition) {
            borderDrawable.setStroke(10, context.getColor(android.R.color.black)) // Borde negro de 10px
        } else {
            // Si no está seleccionado, no tiene borde
            borderDrawable.setStroke(0, context.getColor(android.R.color.transparent)) // Sin borde
        }

        // Aplicar el GradientDrawable al ImageView
        holder.colorImageView.background = borderDrawable
    }

    override fun getItemCount(): Int = colors.size

    fun updateColors(newColors: List<Int>) {
        this.colors = newColors
        // Si el color previamente seleccionado ya no existe, reiniciamos la selección
        if (selectedPosition >= newColors.size) {
            selectedPosition = RecyclerView.NO_POSITION
        }
        notifyDataSetChanged() // Notifica que los datos han cambiado
    }

    // Método para completar la imagen y quitar la selección
    fun completeImage() {
        selectedPosition = RecyclerView.NO_POSITION // Reinicia la selección
        notifyDataSetChanged() // Notifica que los datos han cambiado
    }
}

