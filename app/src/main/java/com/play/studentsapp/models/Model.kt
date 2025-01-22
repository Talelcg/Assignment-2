package com.play.studentsapp.models

class Model private constructor() {

    val students: MutableList<Student> = ArrayList()

    companion object {
        val shared = Model()
    }

    fun addStudent(name: String, id: String, phone: String, address: String, pictureResId: String, chek: Boolean): Boolean {
        if (students.none { it.id == id }) {
            val newStudent = Student(
                name = name,
                id = id,
                phone = phone,
                address = address,
                pictureResId = pictureResId,
                isChecked = chek
            )
            students.add(newStudent)
            return true
        }
        return false
    }
    fun updateStudent(id: String, name: String, phone: String, address: String, isChecked: Boolean) {
        val student = students.find { it.id == id }
        if (student != null) {  // אם הסטודנט נמצא
            student.name = name
            student.phone = phone
            student.address = address
            student.isChecked = isChecked
        }
    }


fun isStudentIdExists(id: String): Boolean {
    return students.any { it.id == id }
}

    fun removeStudentById(id: String) {
        students.removeAll { it.id == id }
    }

    init {
        students.add(Student(
            name = "Yair Cohen",
            id = "123456789",
            phone = "0501234567",
            address = "Tel Aviv, Herzl Street 10",
            pictureResId = "",
            isChecked = false
        ))
        students.add(Student(
            name = "Miriam Levy",
            id = "987654321",
            phone = "0527654321",
            address = "Haifa, HaMaginim Street 15",
            pictureResId = "",
            isChecked = false
        ))
        students.add(Student(
            name = "Yossi Barak",
            id = "111223344",
            phone = "0542345678",
            address = "Jerusalem, King David Street 7",
            pictureResId = "",
            isChecked = false
        ))
        students.add(Student(
            name = "Sharon Mizrahi",
            id = "556677889",
            phone = "050-9876543",
            address = "Netanya, Herzl Street 22",
            pictureResId = "",
            isChecked = false
        ))
        students.add(Student(
            name = "Rafael Dayan",
            id = "123987456",
            phone = "0534567890",
            address = "Ashdod, HaNamal Street 5",
            pictureResId = "",
            isChecked = false
        ))
    }
}
