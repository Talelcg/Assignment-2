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

        val cancelButton: Button = findViewById(R.id.edit_student_activity_cancel_button)
        val saveButton: Button = findViewById(R.id.edit_student_activity_save_button)
        val nameEditText: EditText = findViewById(R.id.edit_student_activity_name_edit_text)
        val idEditText: EditText = findViewById(R.id.edit_student_activity_id_edit_text)
        val phoneEditText: EditText = findViewById(R.id.edit_student_activity_phone_edit_text)
        val addressEditText: EditText = findViewById(R.id.edit_student_activity_adress_edit_text)
        val checkButton: CheckBox = findViewById(R.id.edit_studentDetailsChecked)
        val deleteButton: Button = findViewById(R.id.edit_student_activity_delete_button)

        val studentName = intent.getStringExtra("student_name")
        val studentId = intent.getStringExtra("student_id")
        val studentPhone = intent.getStringExtra("student_phone")
        val studentAddress = intent.getStringExtra("student_address")
        val studentIsChecked = intent.getBooleanExtra("student_isChecked", false)

        nameEditText.setText(studentName)
        idEditText.setText(studentId)
        phoneEditText.setText(studentPhone)
        addressEditText.setText(studentAddress)
        checkButton.isChecked = studentIsChecked

        cancelButton.setOnClickListener {
            finish()
        }

        // שמירת פרטי הסטודנט
        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val id = idEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val checked = checkButton.isChecked

            // בדיקת שם
            if (name.isEmpty()) {
                nameEditText.error = "נא להזין שם"
                return@setOnClickListener
            } else if (!name.matches(Regex("^[a-zA-Zא-ת ]+$"))) {
                nameEditText.error = "שם חייב להכיל אותיות בלבד ללא מספרים או תווים מיוחדים"
                return@setOnClickListener
            }

            // בדיקת תעודת זהות
            if (id.isEmpty()) {
                idEditText.error = "נא להזין תעודת זהות"
                return@setOnClickListener
            } else if (id.length != 9 || !id.all { it.isDigit() }) {
                idEditText.error = "תעודת זהות חייבת להכיל 9 ספרות"
                return@setOnClickListener
            }

            // בדיקת מספר טלפון
            if (phone.isEmpty()) {
                phoneEditText.error = "נא להזין מספר טלפון"
                return@setOnClickListener
            } else if (!phone.matches(Regex("^\\d{10}$"))) {
                phoneEditText.error = "מספר טלפון חייב להיות בן 10 ספרות"
                return@setOnClickListener
            }

            // בדיקת כתובת
            if (address.isEmpty()) {
                addressEditText.error = "נא להזין כתובת"
                return@setOnClickListener
            }


             Model.shared.updateStudent(id, name, phone, address, checked)

            // חזרה ל- MainActivity עם הודעה על הצלחה
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        // מחיקת הסטודנט
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
