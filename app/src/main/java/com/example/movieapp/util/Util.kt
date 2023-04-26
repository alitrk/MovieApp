package com.example.movieapp.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.movieapp.R

fun ImageView.downloadFromUrl(url: String?, context: Context) {
    val options = RequestOptions()
        .error(R.drawable.bg_rectangle_darker_gray)
        .placeholder(CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius = 40f
            start()
        })

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .fitCenter()
        .into(this)
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url: String?) {
    view.downloadFromUrl(url, view.context)
}

fun Navigation.navigate(v: View, id: Int) {
    findNavController(v).navigate(id)
}

fun Navigation.navigate(v: View, id: NavDirections) {
    findNavController(v).navigate(id)
}