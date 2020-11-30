package com.example.frbase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var auth:FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logout = findViewById<Button>(R.id.logout)
        val save = findViewById<Button>(R.id.save)
        val showData = findViewById<Button>(R.id.showData)

        logout.setOnClickListener(this)
        save.setOnClickListener(this)
        showData.setOnClickListener(this)

        auth = FirebaseAuth.getInstance()
    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(v: View) {
        when (v.getId()){
            R.id.save -> {
                val getUserID = auth!!.currentUser!!.uid
                val database = FirebaseDatabase.getInstance()

                val nama = findViewById<EditText>(R.id.nama)
                val alamat = findViewById<EditText>(R.id.alamat)
                val noHp = findViewById<EditText>(R.id.nohp)

                val getNama: String = nama.getText().toString()
                val getAlamat: String = alamat.getText().toString()
                val getNoHp: String = noHp.getText().toString()

                val getReference: DatabaseReference
                getReference = database.reference

                if (isEmpty(getNama) || isEmpty(getAlamat) || isEmpty(getNoHp)) {
                    Toast.makeText(this@MainActivity, "Data tidak boleh kosong",
                    Toast.LENGTH_SHORT).show()
                }else{
                    getReference.child("Admin").child(getUserID).child("Frbase").push()
                        .setValue(data_Frbase(getNama, getAlamat, getNoHp))
                        .addOnCompleteListener(this) {
                            nama.setText("")
                            alamat.setText("")
                            noHp.setText("")
                        }
                }
            }
            R.id.logout -> {
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(object : OnCompleteListener<Void>{
                            override fun onComplete(p0: Task<Void>) {
                                Toast.makeText(this@MainActivity, "Logout Berhasil",
                                Toast.LENGTH_SHORT).show()
                                intent = Intent(applicationContext, Login::class.java)
                                startActivity(intent)
                                finish()
                            }
                        })
            }
            R.id.showData -> {
                startActivity(Intent(this@MainActivity, ListData::class.java))
            }
        }
    }
}