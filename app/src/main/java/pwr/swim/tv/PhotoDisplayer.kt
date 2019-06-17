package pwr.swim.tv

import android.view.View


interface PhotoDisplayer {
    fun updateMainPhoto(view: View)
    fun loadMoreData()
}