package pwr.swim.tv

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Context
import android.widget.PopupWindow
import android.view.LayoutInflater





class MainActivity : Activity(), PhotoDisplayer {

    private val urlList: ArrayList<String> = ArrayList()
    private var pagesLoaded: Int = 0
    private lateinit var photoAdapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = GridLayoutManager(this, GRID_COLUMNS)
        photoAdapter = PhotoAdapter(this, urlList, this)


        PhotoDownloader.addPhotos(this, urlList, PHOTO_AMOUNT, photoAdapter)
        pagesLoaded++

        //create recycler view
        photosList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = photoAdapter
        }
    }

    fun createPopUp(): PopupWindow {
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup,null,false)
        val pw = PopupWindow(view, 1920, 1080, true)
        return pw
    }
    override fun updateMainPhoto(view: View) {
        val position = photosList.findContainingViewHolder(view)?.adapterPosition
        val pw = createPopUp()


        if (position in 0 until urlList.size) {
            //load full resolution photo
            position?.let {
                Glide.with(this)
                    .load(urlList[position])
                    .centerCrop()
                    .into(pw.contentView.findViewById(R.id.popup_image))

            }
        }
    }

    override fun loadMoreData() {
        pagesLoaded++
        PhotoDownloader.addPhotos(this, urlList, PHOTO_AMOUNT, photoAdapter, pagesLoaded)
    }
    companion object{
        const val PHOTO_AMOUNT = 50
        const val GRID_COLUMNS = 3
    }
}
