package com.ute.studentprofilecard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ute.studentprofilecard.utils.showConfirmDialog
import com.ute.studentprofilecard.utils.toAcademicRanking
import com.ute.studentprofilecard.utils.toast
import com.ute.studentprofilecard.databinding.ActivityMainBinding
import com.ute.studentprofilecard.model.Student

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var currentStudent = Student(
        id = "2415053122117",
        name = "Võ Minh Huy",
        className = "24T1",
        email = "2415053122117@sv.ute.udn.vn",
        phone = "0763612967",
        gpa = 3.8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindStudentData(currentStudent)

        binding.btnUpdateGpa.setOnClickListener {
            val inputStr = binding.edtNewGpa.text.toString().trim()
            val newGpa = inputStr.toDoubleOrNull()

            if (newGpa == null || newGpa !in 0.0..4.0) {
                binding.edtNewGpa.error = "Vui lòng nhập GPA hợp lệ (0.0 - 4.0)"
                toast("Điểm GPA không hợp lệ!")
                return@setOnClickListener
            }

            currentStudent = currentStudent.copy(gpa = newGpa)
            bindStudentData(currentStudent)
            toast("Cập nhật điểm thành công!")
        }

        binding.btnCall.setOnClickListener {
            // Dùng Intent mở ứng dụng gọi điện[cite: 1]
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${currentStudent.phone}")
            }
            startActivity(intent)
        }

        binding.btnDeleteStudent.setOnClickListener {
            // Hiển thị AlertDialog xác nhận[cite: 1]
            showConfirmDialog(
                title = "Xác nhận xóa",
                message = "Bạn có chắc chắn muốn xóa hồ sơ sinh viên này không?"
            ) {
                toast("Đã xóa thành công!")
                binding.cardProfile.visibility = View.GONE
            }
        }
    }

    private fun bindStudentData(student: Student) {
        with(binding) {
            tvName.text = student.name
            tvStudentId.text = "MSSV: ${student.id} | Lớp: ${student.className}"
            tvPhone.text = "SĐT: ${student.phone}"
            tvGpaBadge.text = "${student.gpa} GPA (${student.gpa.toAcademicRanking()})"
            edtNewGpa.setText(student.gpa.toString())
        }
    }
}