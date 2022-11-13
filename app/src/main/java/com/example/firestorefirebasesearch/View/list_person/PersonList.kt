package com.example.firestorefirebasesearch.View.list_person

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firestorefirebasesearch.Model.DataModel
import com.example.firestorefirebasesearch.R
import com.example.firestorefirebasesearch.View.main.Adapter
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class PersonList : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    var personlist:ArrayList<DataModel> = ArrayList<DataModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_list)
        recyclerView = findViewById(R.id.recycler)
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        getData()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getData()
    {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Person").get().addOnCompleteListener(OnCompleteListener {
                task->
          personlist.clear()
            for (doc: DocumentSnapshot in task.result)
            {

                val dataModel:DataModel = DataModel(
                    doc.getString("Name")
                    ,doc.getString("Phone")
                    ,doc.getString("Address")
                )
                personlist.add(dataModel)
                adapter = Adapter(this,personlist)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()


            }

        })
    }
}