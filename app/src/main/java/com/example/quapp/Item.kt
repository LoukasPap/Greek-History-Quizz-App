package com.example.quapp

import androidx.annotation.DrawableRes

data class Item(
    var imageResource: DrawableRes,
    var isSelected: Boolean,
)
//{
//    @DrawableRes
//    var imageResource = when (avatar) {
//        CartoonAvatar.MAN1 -> R.drawable.man1_ma
//        CartoonAvatar.MAN2 -> R.drawable.man2_ma2
//        CartoonAvatar.WOMAN1 -> R.drawable.woman1_5
//        CartoonAvatar.WOMAN2 -> R.drawable.woman2_aa1
//    }
//}
//
