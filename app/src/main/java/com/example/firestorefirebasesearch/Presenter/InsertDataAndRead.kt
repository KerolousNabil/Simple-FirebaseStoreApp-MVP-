package com.example.firestorefirebasesearch.Presenter

import android.content.Context
import android.view.View
import android.widget.Toast
import com.example.firestorefirebasesearch.Model.DataModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import org.checkerframework.checker.units.qual.C
import java.util.*
import kotlin.collections.HashMap

class InsertDataAndRead(val view:View, val mycontect:Context):BaseFunctions
{
    override fun insertToDataBase(name:String , phone:String , address:String) {
        val db:FirebaseFirestore = FirebaseFirestore.getInstance()
        val datamodel=DataModel(name,phone,address)
      //  var idd = UUID.randomUUID().toString()
        var doc : HashMap<String, String> = HashMap<String, String> ()
       // datamodel.id = idd
        val document= db.collection("Person").document()
        datamodel.id=  document.id
        doc.put("id",datamodel.id.toString())
        doc.put("Name", datamodel.name.toString())
        doc.put("Phone",datamodel.phone.toString())
        doc.put("Address", datamodel.address.toString() )
        db.collection("Person").add(doc).addOnCompleteListener(OnCompleteListener {
            Success->
           Toast.makeText(mycontect,"Success",Toast.LENGTH_SHORT).show()

        }).addOnFailureListener { OnFailureListener{
            failier->
            Toast.makeText(mycontect,"failed",Toast.LENGTH_SHORT).show()

        } }
        view.getInsert(datamodel)

    }



    interface View
    {
        fun getInsert(dataModel: DataModel)
    }

}