package pwr.swim.tv

import android.content.Context
import android.view.LayoutInflater
import android.widget.PopupWindow

class Popup(val context: Context) {

    fun createPopUp(): PopupWindow {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup,null,false)
        val pw = PopupWindow(view, 1920, 1080, true)
        return pw
    }
}