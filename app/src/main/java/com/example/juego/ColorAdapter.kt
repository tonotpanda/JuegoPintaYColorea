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
                onClick(colors[adapterPosition])
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
        if (position == selectedPosition) {
            borderDrawable.setStroke(10, context.getColor(android.R.color.black)) // Borde negro de 10px
        } else {
            borderDrawable.setStroke(0, context.getColor(android.R.color.transparent)) // Sin borde
        }

        // Aplicar el GradientDrawable al ImageView
        holder.colorImageView.background = borderDrawable
    }

    override fun getItemCount(): Int = colors.size

    fun updateColors(newColors: List<Int>) {
        this.colors = newColors
        notifyDataSetChanged() // Notifica que los datos han cambiado
    }
}