import android.content.Context
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

    inner class ColorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val colorImageView: ImageView = view.findViewById(R.id.colorImageView)

        init {
            view.setOnClickListener {
                onClick(colors[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_item, parent, false)
        return ColorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.colorImageView.setBackgroundColor(colors[position])
    }

    override fun getItemCount(): Int = colors.size

    fun updateColors(newColors: List<Int>) {
        this.colors = newColors
        notifyDataSetChanged()
    }
}
