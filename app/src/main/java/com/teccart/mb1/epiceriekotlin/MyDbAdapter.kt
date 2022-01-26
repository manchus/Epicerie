package com.teccart.mb1.epiceriekotlin

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.util.*

class MyDbAdapter(private val context: Context) {
    private val DATABASE_NAME = "MaDb"
    private val dbHelper: MyDbHelper
    private val DATABASE_VERSION = 1
    private var db: SQLiteDatabase? = null
    fun Open() {
        db = dbHelper.writableDatabase
    }
//Produit(var img:Int,var nom:String,var prix:Double, var checked:Boolean)
    fun InsertProduit(nom: String?, img: Int, prix: Double) {
        val cv = ContentValues()
        cv.put("nom", nom)
        cv.put("img", img)
        cv.put("prix", prix)
        db!!.insert("produits", null, cv)

        //db!!.execSQL("INSERT INTO produits(nom,img,prix) values('tata','1','1.1');")
        //    this.db.execSQL("INSERT INTO students(name,faculty) values('toto',2);");
        //    this.db.execSQL("INSERT INTO students(name,faculty) values('titi',3);");
        //    this.db.execSQL("INSERT INTO students(name,faculty) values('tutu',4);");
    }

    fun deleteAllStudents() {
        db!!.delete("produits", null, null)
        // this.db.execSQL("DELETE FROM STUDENTS;";
    }

    fun SelectAllProduits(): ArrayList<Produit> {
        /*
        ArrayList<Student> listOfStudents = new ArrayList<Student>();
        Cursor cursor = this.db.query("students",null,null,null,null,null,null);

        if((cursor != null) && cursor.moveToFirst())
        {
            do{
                listOfStudents.add(new Student(cursor.getString(1),cursor.getInt(2)));
            }while(cursor.moveToNext());
        }

        return listOfStudents;
        */
        val listOfProduits: ArrayList<Produit> = ArrayList<Produit>()
        val cursor = db!!.rawQuery("select * from produits", null)
        val nomIndex = cursor!!.getColumnIndex("nom")
        val imgIndex = cursor.getColumnIndex("img")
        val prixIndex = cursor.getColumnIndex("prix")
        val idIndex = cursor.getColumnIndex("id")
        if (cursor != null && cursor.moveToFirst()) {
            do {
                listOfProduits.add(
                    Produit(
                        cursor.getInt(imgIndex),
                        cursor.getString(nomIndex),
                        cursor.getDouble(prixIndex),
                        false//   .getInt(2),
                    )
                )
            } while (cursor.moveToNext())
        }
        return listOfProduits
    }

    fun effacerProduits() {
        db!!.execSQL("delete * from produits;")
    }

    fun modifierProduit(nom: String) {
        db!!.execSQL("update students set name =" + nom + "where id=1;")
    }

    init {
        dbHelper = MyDbHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
    }
}
