package com.example.recyclerviewtest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val MyRecycler = findViewById<RecyclerView>(R.id.recyclerV)
        MyRecycler.layoutManager = LinearLayoutManager(this)
        val data = ArrayList<Item>()
        data.add(Item("Basic"))
        data.add(Item("Basic"))
        data.add(Item("Basic"))

        val adapter = RecyclerAdapter(data)
        MyRecycler.adapter = adapter


        val btnAdd = findViewById<Button>(R.id.btnAddItem)
        btnAdd.setOnClickListener{
            data.add(Item("nowy"))
            adapter.notifyItemInserted(data.size - 1)
        }




    }
}