package com.play.studentsapp.models

import com.play.studentsapp.R

data class Student(
    var name: String,
    val id: String,
    var phone: String,
    var address: String,
    val pictureResId: String,
    var isChecked: Boolean = false
)
