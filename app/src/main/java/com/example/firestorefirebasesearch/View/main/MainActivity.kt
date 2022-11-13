package com.example.firestorefirebasesearch.View.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.firestorefirebasesearch.Model.DataModel
import com.example.firestorefirebasesearch.Presenter.BaseFunctions
import com.example.firestorefirebasesearch.Presenter.InsertDataAndRead
import com.example.firestorefirebasesearch.R
import com.example.firestorefirebasesearch.View.list_person.PersonList

class MainActivity : AppCompatActivity() ,InsertDataAndRead.View {

    private lateinit var name:TextView
    private lateinit var phone:TextView
    private lateinit var address:TextView
    private lateinit var insert:Button
    private lateinit var database:Button

    val insertitem:BaseFunctions = InsertDataAndRead(this,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.NameText)
        phone = findViewById(R.id.PhoneText)
        address = findViewById(R.id.AdressText)
        insert = findViewById(R.id.insert)
        database = findViewById(R.id.database)

        insert.setOnClickListener {
            insertitem.insertToDataBase(name.text.toString(),phone.text.toString(),address.text.toString())

        }
        database.setOnClickListener { 
            startActivity(Intent(this@MainActivity,PersonList::class.java))
        }


    }

    override fun getInsert(dataModel: DataModel) {
         name.text = dataModel.name
         phone.text = dataModel.phone
         address.text = dataModel.address
    }


}