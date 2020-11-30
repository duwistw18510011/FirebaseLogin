package com.example.frbase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListData : AppCompatActivity() {
    private var recyclerView:RecyclerView? = null
    private var adapter:RecyclerView.Adapter<*>? = null
    private var layoutManager:RecyclerView.LayoutManager? = null

    val database = FirebaseDatabase.getInstance()
    private var dataFrbase = ArrayList<data_Frbase>()
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_data)
        recyclerView = findViewById(R.id.daftar)
        supportActionBar!!.title = "Daftar Data"
        auth = FirebaseAuth.getInstance()
        MyRecyclerView()
        GetData()
    }

    private fun GetData() {
        Toast.makeText(applicationContext, "Data sedang dimuat",
            Toast.LENGTH_LONG).show()
        val getUserID: String = auth?.getCurrentUser()?.getUid().toString()
        val getReference = database.getReference()
            getReference.child("Admin").child(getUserID).child("Frbase")
            .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                if (datasnapshot.exists()) {
                    for (snapshot in datasnapshot.children){
                        val frbase = snapshot.getValue(data_Frbase::class.java)
                        frbase?.key = snapshot.key
                        dataFrbase.add(frbase!!)
                    }
                    adapter = RecyclerViewAdapter(dataFrbase, this@ListData)
                    recyclerView?.adapter = adapter
                    (adapter as RecyclerViewAdapter).notifyDataSetChanged()
                    Toast.makeText(applicationContext, "Data telah ditampilkan",
                    Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Data gagal ditampilkan",
                    Toast.LENGTH_LONG).show()
                Log.e("ListActivity", error.details + " " + error.message)
            }
        })
    }

    private fun MyRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)

        val itemDecoration = DividerItemDecoration(applicationContext,
        DividerItemDecoration.VERTICAL)
        itemDecoration.setDrawable(ContextCompat.getDrawable(applicationContext,
        R.drawable.line)!!)
        recyclerView?.addItemDecoration(itemDecoration)
    }
}