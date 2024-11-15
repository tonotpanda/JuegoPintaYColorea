import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.juego.R

class ColorAdapter(private var colors: List<Int>, private val colorClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val colorView: ImageView = itemView.findViewById(R.id.colorView)

        fun bind(color: Int) {
            colorView.setBackgroundColor(color) // Establece el color de fondo
            itemView.setOnClickListener {
                colorClickListener(color) // Llama al listener con el color seleccionado
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position]) // Vincula el color a la vista
    }

    override fun getItemCount(): Int {
        return colors.size // Devuelve la cantidad de colores
    }

    // Este método actualiza los colores del adaptador
    fun updateColors(newColors: List<Int>) {
        this.colors = newColors
        notifyDataSetChanged() // Notifica que los datos han cambiado
    }
}
