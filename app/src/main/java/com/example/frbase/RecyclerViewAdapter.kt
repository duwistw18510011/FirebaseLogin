package com.example.frbase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val listdatadata_Frbase: ArrayList<data_Frbase>, context: Context) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    private val context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Nama: TextView
        val Alamat: TextView
        val NoHP: TextView
        val ListItem: LinearLayout

        init {
            Nama = itemView.findViewById(R.id.nama)
            Alamat = itemView.findViewById(R.id.alamat)
            NoHP = itemView.findViewById(R.id.nohp)
            ListItem = itemView.findViewById(R.id.list_item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val V:View = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design,
        parent, false)
        return ViewHolder(V)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Nama: String? = listdatadata_Frbase.get(position).nama
        val Alamat: String? = listdatadata_Frbase.get(position).alamat
        val NoHP: String? = listdatadata_Frbase.get(position).noHp

        holder.Nama.text = "Nama: $Nama"
        holder.Alamat.text = "Alamat: $Alamat"
        holder.NoHP.text = "No HP: $NoHP"
        holder.ListItem.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                return true
            }
        })
    }

    override fun getItemCount(): Int {
        return listdatadata_Frbase.size
    }

    init {
        this.context = context
    }
}