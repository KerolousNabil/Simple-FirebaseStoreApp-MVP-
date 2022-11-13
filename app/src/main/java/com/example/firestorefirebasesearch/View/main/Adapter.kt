package com.example.firestorefirebasesearch.View.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.firestorefirebasesearch.Model.DataModel
import com.example.firestorefirebasesearch.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList


class Adapter (var mycontext : Context, private val personList : ArrayList<DataModel>) : RecyclerView.Adapter<Adapter.MyViewHolder>(){

class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    val name: TextView = itemView.findViewById(R.id.tvfirstName)
    val phone: TextView = itemView.findViewById(R.id.tvlPhone)
    val address: TextView = itemView.findViewById(R.id.tvAddress)
    val deletebutton: Button = itemView.findViewById(R.id.Deletebtn)
    val updatebutton: Button = itemView.findViewById(R.id.Updatebtn)

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.person_item,parent , false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataModel : DataModel = personList[position]
        holder.name.text = dataModel.name
        holder.phone.text = dataModel.phone
        holder.address.text = dataModel.address

        holder.deletebutton.setOnClickListener {

            deleteitem(dataModel, position)

        }
        holder.updatebutton.setOnClickListener {
            updateitem(dataModel, position)
        }
    }

    override fun getItemCount(): Int {return personList.size}

    fun deleteitem(dataModel: DataModel, position: Int)
    {
        val db:FirebaseFirestore = FirebaseFirestore.getInstance()
        db.collection("Person").whereEqualTo("Name",dataModel.name).get().addOnCompleteListener(
            OnCompleteListener {
                if (it.isSuccessful)
                {
                    val doc:DocumentSnapshot = it.result.documents.get(0)
                    val docID= doc.id
                    db.collection("Person").document(docID).delete().addOnSuccessListener {
                        Toast.makeText(mycontext,"Success",Toast.LENGTH_SHORT).show()
                        personList.removeAt(position)
                        notifyDataSetChanged()

                    }.addOnFailureListener {
                        Toast.makeText(mycontext,"failed",Toast.LENGTH_SHORT).show()

                    }
                }
                else
                {
                    Toast.makeText(mycontext,"failed",Toast.LENGTH_SHORT).show()

                }


            })

    }
    fun updateitem(dataModel: DataModel , position: Int)
    {
        val db:FirebaseFirestore = FirebaseFirestore.getInstance()

        val builder = AlertDialog.Builder(mycontext)
        builder.setTitle("Update Info")
        val inflater = LayoutInflater.from(mycontext)
        val view = inflater.inflate(R.layout.person_update,null)

        val first_name = view.findViewById<TextView>(R.id.name_update_text)
        val phone_number = view.findViewById<TextView>(R.id.phone_update_text)
        val address_item = view.findViewById<TextView>(R.id.address_update_text)
        first_name.text = dataModel.name
        phone_number.text = dataModel.phone
        address_item.text = dataModel.address
        builder.setView(view)
        builder.setPositiveButton("Update",object : DialogInterface.OnClickListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onClick(p0: DialogInterface?, p1: Int) {
                val name    = first_name.text.toString().trim()
                val phone_num     = phone_number.text.toString().trim()
                val adress     = address_item.text.toString().trim()

                db.collection("Person").whereEqualTo("Name",dataModel.name).get().addOnCompleteListener(
                    OnCompleteListener {
                        if (it.isSuccessful)
                        {
                            val doc:DocumentSnapshot = it.result.documents.get(0)
                            val docID= doc.id
                            db.collection("Person").document(docID)
                                .update("Name" , name, "Phone",phone_num ,"Address",adress)
                                .addOnSuccessListener {
                                    val pdialog = ProgressDialog(mycontext)
                                    pdialog.setTitle("Update progress")
                                    pdialog.setMessage("This progress is done successfully")
                                    pdialog.show()
                                    val progressRunnable = Runnable { pdialog.cancel() }

                                    val pdCanceller = Handler()
                                    pdCanceller.postDelayed(progressRunnable, 1000)
                                    notifyItemChanged(position);
                                    notifyDataSetChanged()


                                }.addOnFailureListener {
                                Toast.makeText(mycontext,"failed",Toast.LENGTH_SHORT).show()

                            }
                        }
                        else
                        {
                            Toast.makeText(mycontext,"failed",Toast.LENGTH_SHORT).show()

                        }


                    })


            }

        })
        builder.setNegativeButton("cancel",object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {

            }

        })

        val alert = builder.create()
        alert.show()


    }


}