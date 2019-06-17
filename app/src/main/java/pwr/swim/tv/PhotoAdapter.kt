package pwr.swim.tv


import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.recycler_item.view.*
import pwr.swim.tv.MainActivity.Companion.GRID_COLUMNS


class PhotoAdapter(private val context: Context, private val photoUrls: List<String>, private val photoDisplayer: PhotoDisplayer) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        view.setOnClickListener{



        }
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return photoUrls.size
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowView = holder.rowView
        val url = photoUrls[position]

        rowView.setOnClickListener { v ->
            v.isSelected = true
        }
        rowView.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                //highlight focused item
                view.setBackgroundResource(R.drawable.focusable_border)

            }
            if (!hasFocus) {
                //unhighlight deselected item
                view.setBackgroundColor(Color.TRANSPARENT)
            }
        }
        //if item is the last one load additional data
        if (position >= photoUrls.size - GRID_COLUMNS) {
            photoDisplayer.loadMoreData()
        }

        Log.i("url", url)
        Glide.with(context)
            .load(url)
            .centerCrop()
            .error(R.drawable.error)
            .into(rowView.imageCard.photo)
//        //load the photo
//        Picasso.get().load(url)
//                .error(R.drawable.error)
//                .fit()
//                .into(rowView.imageCard.photo)
    }

    class ViewHolder(val rowView: View) :RecyclerView.ViewHolder(rowView)
}