package com.play.studentsapp

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

        saveButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val id = idEditText.text.toString().trim()
            val phone = phoneEditText.text.toString().trim()
            val address = addressEditText.text.toString().trim()
            val checked = checkButton.isChecked


            if (name.isEmpty()) {
                nameEditText.error = "נא להזין שם"
                return@setOnClickListener
            } else if (!name.matches(Regex("^[a-zA-Zא-ת ]+$"))) { // תומך באותיות בעברית ובאנגלית
                nameEditText.error = "שם חייב להכיל אותיות בלבד ללא מספרים או תווים מיוחדים"
                return@setOnClickListener
            }

            if (id.isEmpty()) {
                idEditText.error = "נא להזין תעודת זהות"
                return@setOnClickListener
            } else if (id.length != 9 || !id.all { it.isDigit() }) {
                idEditText.error = "תעודת זהות חייבת להכיל 9 ספרות"
                return@setOnClickListener
            }

            if (phone.isEmpty()) {
                phoneEditText.error = "נא להזין מספר טלפון"
                return@setOnClickListener
            } else if (!phone.matches(Regex("^\\d{10}$"))) {
                phoneEditText.error = "מספר טלפון חייב להיות בן 10 ספרות"
                return@setOnClickListener
            }

            if (address.isEmpty()) {
                addressEditText.error = "נא להזין כתובת"
                return@setOnClickListener
            }

            val success = Model.shared.addStudent(name, id, phone, address, pictureResId = "", checked)
            if (success) {
                savedMessageTextView.text = "הסטודנט נוסף בהצלחה!\nשם: $name, ת.ז: $id"
                setResult(RESULT_OK)
                finish()
            } else {
                savedMessageTextView.text = "סטודנט עם תעודת זהות $id כבר קיים במערכת."
            }
        }
    }
}
