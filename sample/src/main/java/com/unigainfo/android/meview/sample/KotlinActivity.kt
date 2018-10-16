package com.unigainfo.android.meview.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.unigainfo.android.meview.adapter.MeFlexAdapter
import com.unigainfo.android.meview.sample.model.Student
import com.unigainfo.android.meview.sample.viewholderfactory.KStudentViewHolderFactory
import com.unigainfo.android.meview.sample.viewholderfactory.StudentViewHolderFactory
import kotlinx.android.synthetic.main.activity_kotlin.*

class KotlinActivity : AppCompatActivity(),StudentViewHolderFactory.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        renderSampleList()
    }

    private fun renderSampleList() {
        val adapter = MeFlexAdapter<Any>(this)
        val list = ArrayList<Any>()
        adapter.registerViewHolderFactory(KStudentViewHolderFactory())
        adapter.registerViewHolderFactory(StudentViewHolderFactory(this),2)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        list.add(Student("Student 1",""))
        list.add(Student("Student 2",""))
        adapter.setItems(list)
        adapter.add(Student("Student 6",""),2)
    }

    override fun onStudentItemClick(item: Student?) {
        Toast.makeText(this, "Student Name =" + item?.name, Toast.LENGTH_SHORT).show()
    }

}
