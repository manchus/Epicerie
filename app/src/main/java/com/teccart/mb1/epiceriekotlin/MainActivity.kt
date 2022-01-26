package com.teccart.mb1.epiceriekotlin

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mylist: ListView
    private lateinit var myAdapter: ProduitArrayAdapter
    private lateinit var listP:ArrayList<Produit>
    private lateinit var txtNom:EditText
    private lateinit var txtPrix:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         listP = ArrayList<Produit>()
         mylist = findViewById<ListView>(R.id.myList)
         txtNom = findViewById<EditText>(R.id.txtNom)
         txtPrix = findViewById<EditText>(R.id.txtPrix)

         myAdapter = ProduitArrayAdapter(applicationContext, R.layout.produit_layout, listP)
         mylist.adapter = myAdapter

        mylist.setOnItemClickListener { adapterView, view, i, l ->


            if(listP[i].checked == true)
            {
                listP[i].checked = false
                view.findViewById<ImageView>(R.id.imageCheck).setImageResource(R.drawable.unchecked)
                txtNom.setText("")
                txtPrix.setText("")
            }
            else
            {
                listP[i].checked = true
                view.findViewById<ImageView>(R.id.imageCheck).setImageResource(R.drawable.checked)
                txtNom.setText(listP[i].nom)
                txtPrix.setText(listP[i].prix.toString())
            }
           // Toast.makeText(applicationContext,adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_LONG).show()

        }

        mylist.setOnItemLongClickListener { adapterView, view, i, l ->  //adapter, item, pos, rowId ->
            view.setOnClickListener { v->
                Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
                var builder = AlertDialog.Builder(this)
                builder.setTitle("Delete Alert")
                builder.setMessage("Voulez-vous supprimer cet élément de votre liste?")
                //builder.setPositiveButton("OK", DialogInterface.OnCancelListener (function = x))
                builder.setPositiveButton(android.R.string.yes){
                    dialog, which -> Toast.makeText(applicationContext, android.R.string.yes, Toast.LENGTH_SHORT).show()
                    listP.remove(listP[i])
                    myAdapter.notifyDataSetChanged()
                    findViewById<TextView>(R.id.nProduits).text = "Produits:"+ listP.size.toString()
                    findViewById<TextView>(R.id.total).text = "Total:"+ total()+"$"
                    }
                builder.setNegativeButton(android.R.string.no){
                        dialog, which -> Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show()
                }
                /*builder.setNeutralButton("Maybe"){
                        dialog, which -> Toast.makeText(applicationContext, "Maybe", Toast.LENGTH_SHORT).show()
                }*/
                builder.show()
            }
            //Toast.makeText(this, "Long click detected", Toast.LENGTH_SHORT).show()
             true

        }

    }

    fun alerts(textToShow:String){
        Toast.makeText(applicationContext,textToShow,Toast.LENGTH_LONG).show()
    }

    fun addproduit(view: View) {
        val nom = txtNom.text.toString()
        val prix = txtPrix.text.toString().toDouble()

        try
        {

            val res = when
            {
                findViewById<RadioButton>(R.id.radBc).isChecked->R.drawable.breadcereal
                findViewById<RadioButton>(R.id.radDr).isChecked->R.drawable.dary
                findViewById<RadioButton>(R.id.radMs).isChecked->R.drawable.meatfish
                findViewById<RadioButton>(R.id.radFv).isChecked->R.drawable.fruitvegies
                findViewById<RadioButton>(R.id.radmDk).isChecked->R.drawable.drinkssnacks
                findViewById<RadioButton>(R.id.radmBk).isChecked->R.drawable.barery
                else->R.drawable.images
            }

            listP.add(Produit(res, nom, prix, false))
            printScreen()

            myAdapter.notifyDataSetChanged()
        }
        catch (ex: Exception)
        {
            var aldb = AlertDialog.Builder(this)
            aldb.setTitle("Bad product entry")
            aldb.setMessage(ex.message)

            var ald = aldb.create()
            ald.show()

        }


    }

    fun total():Double{
        var total = 0.0
        for(i in listP)
            total += i.prix
        return total
    }

    fun saveproduits(view: View) {

        try
        {
            val myDbAdapter = MyDbAdapter(applicationContext)
            myDbAdapter.Open()
            for( i in listP){
                myDbAdapter.InsertProduit(i.nom, i.img, i.prix)
            }
        }
        catch (ex: Exception)
        {
            Log.i("dbTest", ex.message!!)

        }


    }

    fun loadproduits(view: View) {

        try
        {
            val myDbAdapter = MyDbAdapter(applicationContext)
            myDbAdapter.Open()
            var ls: java.util.ArrayList<Produit> = java.util.ArrayList<Produit>()
            ls = myDbAdapter.SelectAllProduits()
            Log.i("dbTest", ls.size.toString())
            for(i in ls){
                listP.add(Produit(i.img, i.nom, i.prix, false))
            }
            myAdapter.notifyDataSetChanged()
            printScreen()
        }
        catch (ex: Exception)
        {
            Log.i("dbTest", ex.message!!)

        }


    }

    fun resetSavedProduit(view: View) {

        try
        {
            val myDbAdapter = MyDbAdapter(applicationContext)
            myDbAdapter.Open()
            for( i in  0 ..listP.size-1) {
                listP.remove(listP[0])
                myAdapter.notifyDataSetChanged()
            }
            myDbAdapter.deleteAllStudents()
            printScreen()
        }
        catch (ex: Exception)
        {
            Log.i("dbTest", ex.message!!)

        }


    }

    fun printScreen(){
        findViewById<TextView>(R.id.nProduits).text = "Produits : "+ listP.size.toString()
        findViewById<TextView>(R.id.total).text = "Total : "+ total()+"$"
        var tps:Double = total()*5/100
        findViewById<TextView>(R.id.tps).text = "T.P.S (5%) : "+ "%.2f".format(tps) +"$"
        var tvq:Double = total()*9.975/100
        findViewById<TextView>(R.id.tvq).text = "TVQ (9.975%):"+ "%.2f".format(tvq)+"$"
        findViewById<TextView>(R.id.bigtotal).text = "Total:"+ "%.2f".format(total()+tps+tvq) +"$"
    }

}