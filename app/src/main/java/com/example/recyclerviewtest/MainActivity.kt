package com.example.recyclerviewtest

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val db = DbHelper(this, null)
        val cursor = db.getItems()
        if (cursor.count == 0)
        {
            data.add(Item("ERROR"))
        }
        else
        {
            val infoColIndex = cursor.getColumnIndex(DbHelper.DbInfoCol)
            while(cursor.moveToNext())
            {
                data.add(Item(cursor.getString(infoColIndex)))
            }
        }
        cursor.close()

        val adapter = RecyclerAdapter(data)
        MyRecycler.adapter = adapter


        val btnAdd = findViewById<Button>(R.id.btnAddItem)
        btnAdd.setOnClickListener{
            val id = db.addItem(Item("Dodano z przycisku"))
            val cursorA = db.getItem(id)
            cursorA.moveToNext()

            if (cursorA.count == 0)
            {
                data.add(Item("ERROR"))
            }
            else
            {
                val infoColIndex = cursorA.getColumnIndex(DbHelper.DbInfoCol)
                data.add(Item(cursorA.getString(infoColIndex)))
            }
            adapter.notifyItemInserted(data.size - 1)
        }

        val btnDel = findViewById<Button>(R.id.DEL)
        btnDel.setOnClickListener {
            db.deleteAll()
            data.clear()
            adapter.notifyDataSetChanged()
            db.close()
        }
    }
}