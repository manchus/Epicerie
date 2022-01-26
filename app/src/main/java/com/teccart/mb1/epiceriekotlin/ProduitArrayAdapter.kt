package com.teccart.mb1.epiceriekotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ProduitArrayAdapter(context: Context, var resource: Int, var objects: MutableList<Produit>) :
    ArrayAdapter<Produit>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var convertView= LayoutInflater.from(context).inflate(resource,parent,false)

        var img = convertView.findViewById<ImageView>(R.id.image)
        var imgc = convertView.findViewById<ImageView>(R.id.imageCheck)
        var txtprix = convertView.findViewById<TextView>(R.id.prix)
        var txtnom = convertView.findViewById<TextView>(R.id.nom)

        var state = if(objects[position].checked == true) R.drawable.checked else R.drawable.unchecked
        imgc.setImageResource(state)

        img.setImageResource(objects[position].img)
        txtprix.text = objects[position].prix.toString()
        txtnom.text = objects[position].nom



        return convertView
    }
}