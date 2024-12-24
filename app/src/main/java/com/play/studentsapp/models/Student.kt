package com.play.studentsapp.models

import com.play.studentsapp.R

data class Student(
    val name: String,
    val id: String,
    val phone: String,
    val address: String,
    val pictureResId: String,
    var isChecked: Boolean = false
)
