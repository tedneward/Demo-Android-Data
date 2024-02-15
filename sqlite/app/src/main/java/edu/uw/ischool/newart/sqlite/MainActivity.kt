package edu.uw.ischool.newart.sqlite

import android.app.Application
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class SqliteApplication : Application() {
    lateinit var db : SQLiteDatabase

    override fun onCreate() {
        super.onCreate()

        db = SQLiteDatabase.openOrCreateDatabase(filesDir.absolutePath + "/Preferences", null)
        db.execSQL("CREATE TABLE IF NOT EXISTS preferences (servername VARCHAR, portnumber INTEGER)")

        val query = "select sqlite_version() AS sqlite_version"
        val cursor = db.rawQuery(query, null)
        var sqliteVersion = ""
        if (cursor.moveToNext()) {
            sqliteVersion = cursor.getString(0)
            Toast.makeText(this, "SQLite v$sqliteVersion", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
    }
}

/**
 * This example saves and loads the servername/portnumber to a SQLite
 * database, rather than a raw file.
 */
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

    fun loadPrefs() {
        val db = (application as SqliteApplication).db

        // This is really a terrible way to treat a database, but it's
        // just to show how to use SQLite, not provide a great example
        // of relational database design.
        val query = "SELECT servername, portnumber FROM preferences LIMIT 1"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToNext()) {
            edtServername.setText(cursor.getString(0))
            edtPort.setText(cursor.getString(1))
        }
        cursor.close()
    }
    fun storePrefs() {
        val db = (application as SqliteApplication).db

        val servername = edtServername.text.toString()
        val portnumber = edtPort.text.toString().toInt()

        val cv = ContentValues().apply {
            put("servername", servername)
            put("portnumber", portnumber)
        }
        val rowIDInserted = db.insert("preferences", null, cv)
    }
    fun deletePrefs() {
        val db = (application as SqliteApplication).db

    }
}