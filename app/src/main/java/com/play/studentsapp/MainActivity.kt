package com.play.studentsapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.play.studentsapp.models.Student
import com.play.studentsapp.models.Model

interface OnItemClickListener {
    fun onItemClick(position: Int)
    fun onItemClick(student: Student?)
}

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: StudentsRecyclerAdapter
    private lateinit var students: MutableList<Student>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // התאמת ה־RecyclerView
        students = Model.shared.students
        val recyclerView: RecyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = StudentsRecyclerAdapter(students)
        recyclerView.adapter = adapter

        // הגדרת כפתור הוספת סטודנט
        val addStudentButton: Button = findViewById(R.id.main_acitivity_add_student_button)
        addStudentButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            resultLauncher.launch(intent)  // פותחים את הפעולה עם שמיעת התוצאה
        }

        // הגדרת OnItemClickListener
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val student = students[position]
                val intent = Intent(this@MainActivity, StudentDetailsActivity::class.java) // פותחים את דף פרטי הסטודנט
                // העברת הנתונים של הסטודנט לדף הפרטים
                intent.putExtra("student_name", student.name)
                intent.putExtra("student_id", student.id)
                intent.putExtra("student_phone", student.phone)
                intent.putExtra("student_address", student.address)
                intent.putExtra("student_isChecked", student.isChecked)
                startActivity(intent) // פותחים את דף פרטי הסטודנט
            }

            override fun onItemClick(student: Student?) {
                // אפשרות נוספת במקרה של פרטי הסטודנט, כאן נתקלנו בקוד מיותר
            }
        }
    }

    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged()
        }
    }

    class StudentViewHolder(
        itemView: View,
        listener: OnItemClickListener?
    ): RecyclerView.ViewHolder(itemView) {

        private var nameTextView: TextView? = null
        private var idTextView: TextView? = null
        private var checkBox: CheckBox? = null
        private var student: Student? = null

        init {
            nameTextView = itemView.findViewById(R.id.student_row_name_text_view)
            idTextView = itemView.findViewById(R.id.student_row_id_text_view)
            checkBox = itemView.findViewById(R.id.student_row_check_box)

            checkBox?.apply {
                setOnClickListener { view ->
                    (tag as? Int)?.let { tag ->
                        student?.isChecked = (view as? CheckBox)?.isChecked ?: false
                    }
                }
            }

            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
                listener?.onItemClick(student)
            }
        }

        fun bind(student: Student?, position: Int) {
            this.student = student
            nameTextView?.text = student?.name
            idTextView?.text = student?.id
            checkBox?.apply {
                isChecked = student?.isChecked ?: false
                tag = position
            }
        }
    }

    class StudentsRecyclerAdapter(private val students: List<Student>?): RecyclerView.Adapter<StudentViewHolder>() {

        var listener: OnItemClickListener? = null

        override fun getItemCount(): Int = students?.size ?: 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
            val inflation = LayoutInflater.from(parent.context)
            val view = inflation.inflate(R.layout.student_item, parent, false)
            return StudentViewHolder(view, listener)
        }

        override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
            holder.bind(students?.get(position), position)
        }
    }
}
