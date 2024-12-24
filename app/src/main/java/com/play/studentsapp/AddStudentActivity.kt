package com.play.studentsapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.play.studentsapp.models.Model

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveButton: Button = findViewById(R.id.add_student_activity_save_button)
        val cancelButton: Button = findViewById(R.id.add_student_activity_cancel_button)
        val nameEditText: EditText = findViewById(R.id.add_student_activity_name_edit_text)
        val idEditText: EditText = findViewById(R.id.add_student_activity_id_edit_text)
        val phoneEditText: EditText = findViewById(R.id.add_student_activity_phone_edit_text)
        val addressEditText: EditText = findViewById(R.id.add_student_activity_adress_edit_text)
        val savedMessageTextView: TextView = findViewById(R.id.add_student_activity_save_message_text_view)
        val checkButton: CheckBox = findViewById(R.id.studentDetailsChecked)

        // קבלת הנתונים שנשלחו מ- MainActivity
        val studentName = intent.getStringExtra("student_name")
        val studentId = intent.getStringExtra("student_id")
        val studentPhone = intent.getStringExtra("student_phone")
        val studentAddress = intent.getStringExtra("student_address")
        val studentIsChecked = intent.getBooleanExtra("student_isChecked", false)

        // הצגת הנתונים בשדות
        nameEditText.setText(studentName)
        idEditText.setText(studentId)
        phoneEditText.setText(studentPhone)
        addressEditText.setText(studentAddress)
        checkButton.isChecked = studentIsChecked

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val address = addressEditText.text.toString()
            val checked = checkButton.isChecked
            Model.shared.addStudent(name, id, phone, address, pictureResId = "", checked)

            setResult(RESULT_OK) // or RESULT_CANCELED if you want to indicate failure

            savedMessageTextView.text = "Name: $name ID: $id $phone $address is saved!!!"
        }
    }
}
