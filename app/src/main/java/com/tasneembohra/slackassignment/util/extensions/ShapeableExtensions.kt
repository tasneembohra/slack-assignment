package com.tasneembohra.slackassignment.util.extensions

import androidx.annotation.StyleRes
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel

fun ShapeableImageView.withShape(@StyleRes shapeAppearanceOverlayResId: Int) {
    shapeAppearanceModel = ShapeAppearanceModel.builder(
        context,
        0,
        shapeAppearanceOverlayResId
    ).build()
}
