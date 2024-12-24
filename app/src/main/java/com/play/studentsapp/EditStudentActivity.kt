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

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       // val saveButton: Button = findViewById(R.id.edit_student_activity_save_button)
        val cancelButton: Button = findViewById(R.id.edit_student_activity_cancel_button)
        val nameEditText: EditText = findViewById(R.id.edit_student_activity_name_edit_text)
        val idEditText: EditText = findViewById(R.id.edit_student_activity_id_edit_text)
        val phoneEditText: EditText = findViewById(R.id.edit_student_activity_phone_edit_text)
        val addressEditText: EditText = findViewById(R.id.edit_student_activity_adress_edit_text)
        val checkButton: CheckBox = findViewById(R.id.edit_studentDetailsChecked)
        val deleteButton: Button = findViewById(R.id.edit_student_activity_delete_button)

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
      
        deleteButton.setOnClickListener {
            studentId?.let {

                Model.shared.removeStudentById(it)

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)

                finish()
            }
        }
    }
}
