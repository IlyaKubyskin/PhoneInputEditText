package com.example.kubik.j1edittext

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable

class PhoneCode(val code: String, val context: Context) : Drawable() {

    private val paint = Paint()

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun draw(canvas: Canvas) {
        paint.textSize = context.resources.getDimension(R.dimen.editTextSize)
        canvas.drawText(code, 0f, 31f, paint)
    }
}