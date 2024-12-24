package com.play.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StudentDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val studentName = intent.getStringExtra("student_name")
        val studentId = intent.getStringExtra("student_id")
        val studentPhone = intent.getStringExtra("student_phone")
        val studentAddress = intent.getStringExtra("student_address")
        val studentIsChecked = intent.getBooleanExtra("student_isChecked", false)


        val nameTextView: TextView = findViewById(R.id.View_Name)
        val idTextView: TextView = findViewById(R.id.View_Id)
        val addressTextView: TextView = findViewById(R.id.View_Address)
        val phoneTextView: TextView = findViewById(R.id.View_Phone)
        val statusTextView: TextView = findViewById(R.id.View_Status)

        nameTextView.text = "Name: $studentName"
        idTextView.text = "ID: $studentId"
        addressTextView.text = "Address: $studentAddress"
        phoneTextView.text = "Phone: $studentPhone"
        statusTextView.text = "Checked: ${if (studentIsChecked) "Yes" else "No"}"


        val editButton: Button = findViewById(R.id.button_Edit)
        editButton.setOnClickListener {
            val intent = Intent(this@StudentDetailsActivity, EditStudentActivity::class.java)

            intent.putExtra("student_name", studentName)
            intent.putExtra("student_id", studentId)
            intent.putExtra("student_phone", studentPhone)
            intent.putExtra("student_address", studentAddress)
            intent.putExtra("student_isChecked", studentIsChecked)
            startActivity(intent)
        }
        val cancelButton: Button = findViewById(R.id.button_Cancel)
        cancelButton.setOnClickListener {
            finish()
        }
    }
}
