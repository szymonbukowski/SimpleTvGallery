package pwr.swim.tv

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.JsonObject

const val URL = "https://pixabay.com/api/?key="
const val SAFE_SEARCH_URL = "&safesearch=true"
const val PHOTO_AMOUNT_URL = "&per_page="
const val ORIENTATION_URL = "&orientation=horizontal"
const val DEFAULT_AMOUNT = 20
const val MIN_AMOUNT = 3
const val MAX_AMOUNT = 200
const val IMAGE_ARRAY_ATTRIBUTE = "hits"
const val IMAGE_URL_ATTRIBUTE = "largeImageURL"
const val PAGE_ATTRIBUTE = "&page="

object PhotoDownloader {

    fun addPhotos(context: Context, urlList: ArrayList<String>, amount: Int = DEFAULT_AMOUNT, adapter: PhotoAdapter, page: Int = 1) {
        val perPage = if (amount !in MIN_AMOUNT..MAX_AMOUNT) {
            DEFAULT_AMOUNT
        } else {
            amount
        }
        val queue = Volley.newRequestQueue(context)
        val apiKey = context.getString(R.string.pixabay_api_key)
        //create a http request
        val request = "$URL$apiKey$SAFE_SEARCH_URL$ORIENTATION_URL$PHOTO_AMOUNT_URL$perPage$PAGE_ATTRIBUTE$page"
        Log.i("request", request)
        queue.cache.clear()
        val stringRequest = StringRequest(Request.Method.GET, request, Response.Listener { response ->
            //add urls to list
            urlList.addAll(createUrlList(response))
            adapter.notifyDataSetChanged()
        }, Response.ErrorListener { e ->
            Log.d("error_get", e?.toString() ?: "null networkResponse")
        })
        queue.add(stringRequest)
    }

    private fun createUrlList(json: String): List<String> {
        //extract photo urls from json
        val jObj = Gson().fromJson<JsonObject>(json, JsonObject::class.java)
        val imgArr = jObj.get(IMAGE_ARRAY_ATTRIBUTE).asJsonArray
        return imgArr.map { it.asJsonObject.get(IMAGE_URL_ATTRIBUTE).asString }
    }


}