package edu.uw.ischool.newart.sharedprefs

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    lateinit var edtServername : EditText
    lateinit var edtPort : EditText
    lateinit var btnStore : Button
    lateinit var btnLoad : Button
    lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtServername = findViewById(R.id.edtServername)
        edtPort = findViewById(R.id.edtPortnumber)

        btnLoad = findViewById(R.id.btnLoad)
        btnLoad.setOnClickListener { loadPrefs() }
        btnStore = findViewById(R.id.btnStore)
        btnStore.setOnClickListener { storePrefs() }
        btnDelete = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener { deletePrefs() }
    }

    fun loadPrefs() : Unit {
        // Uses Context.getSharedPreferences()
        val sharedPrefs = getSharedPreferences("SharedPrefs", MODE_PRIVATE)

        // Could also use this, which ties the file name to the Activity name:
        //val sharedPrefs = getPreferences(MODE_PRIVATE)

        edtServername.setText(sharedPrefs.getString("servername", ""))
        edtPort.setText(sharedPrefs.getInt("portnumber", 0).toString())
    }
    fun storePrefs() {
        // Uses Context.getSharedPreferences()
        val sharedPrefs = getSharedPreferences("SharedPrefs", MODE_PRIVATE)

        // Could also use this, which ties the file name to the Activity name:
        //val sharedPrefs = getPreferences(MODE_PRIVATE)

        sharedPrefs.edit().apply() {
            putString("servername", edtServername.text.toString())
            putInt("portnumber", edtPort.text.toString().toInt())
            apply() // or commit()
        }
    }

    /**
     * Note that "deleting" prefs here means "deleting the preferences keys
     * from the shared preferences file", *not* "deleting the shared preferences
     * file altogether". We'll still have a file once it's created, it'll
     * just be empty. This does mean, by the way, that if you add more than
     * the two keys in the demo, without remove()ing them here, you'll have
     * leftovers remaining after calling deletePrefs().
     */
    fun deletePrefs() {
        // Uses Context.getSharedPreferences()
        val sharedPrefs = getSharedPreferences("SharedPrefs", MODE_PRIVATE)

        // Could also use this, which ties the file name to the Activity name:
        //val sharedPrefs = getPreferences(MODE_PRIVATE)

        sharedPrefs.edit().apply() {
            remove("servername")
            remove("portnumber")
            commit() // or apply()
        }
    }
}
