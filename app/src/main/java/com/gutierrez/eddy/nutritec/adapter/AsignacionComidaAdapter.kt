import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gutierrez.eddy.nutritec.R
import com.gutierrez.eddy.nutritec.models.AsignacionComida

class AsignacionComidaAdapter(private val asignaciones: List<AsignacionComida>) : RecyclerView.Adapter<AsignacionComidaAdapter.AsignacionComidaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsignacionComidaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_asignacion_comida, parent, false)
        return AsignacionComidaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AsignacionComidaViewHolder, position: Int) {
        val asignacion = asignaciones[position]
        holder.bind(asignacion)
    }

    override fun getItemCount() = asignaciones.size

    class AsignacionComidaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descripcionComida: TextView = itemView.findViewById(R.id.descripcionComida)
        private val fechaHoraRegistro: TextView = itemView.findViewById(R.id.fechaHoraRegistro)
        private val caloriasComida: TextView = itemView.findViewById(R.id.caloriasComida)
        private val categoriaComida: TextView = itemView.findViewById(R.id.categoriaComida)
        private val imageView: ImageView = itemView.findViewById(R.id.imageViewComida)

        fun bind(asignacion: AsignacionComida) {
            descripcionComida.text = asignacion.comida.nombreComida
            fechaHoraRegistro.text = asignacion.fechaHoraRegistro
            caloriasComida.text = asignacion.comida.calorias.toString()
            categoriaComida.text = asignacion.comida.categoriaComida.nombreCategoria

            // Cargar la imagen usando Glide
            Glide.with(itemView.context)
                .load(asignacion.comida.images)
                .into(imageView)
        }
    }
}
