package edu.uw.ischool.newart.androidmongo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.bson.Document
import org.bson.conversions.Bson

/**
 * This is a strongly-typed Document subclass, which puts a "wrapper"
 * around the (JSON) Document that is natively stored by MongoDB. It
 * uses property delegation to help manaage the translation between
 * properties and their JSON equivalents. It also helps encapsulate
 * the details of how the data is stored in the JSON from the callers
 * who save/load the data.
 */
class Settings(sn : String, p : Int) : Document() {
    constructor() : this("", 0) { }

    var servername : String
        get() = get("servername") as String
        set(newValue) { put("servername", newValue) }
    var port : Int
        get() = get("port") as Int
        set(newValue) { put("port", newValue) }

    init {
        servername = sn
        port = p
    }
}

class Person(fn : String, ln : String, a : Int) : Document() {
    var firstName : String
        get() = get("firstName") as String
        set(newValue) { put("firstName", newValue) }
    var lastName : String
        get() = get("lastName") as String
        set(newValue) { put("lastName", newValue) }
    var age : Int
        get() = get("age") as Int
        set(newValue) { put("age", newValue) }

    init {
        firstName = fn
        lastName = ln
        age = a
    }
}

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    // MongoDB settings and objects
    val uri = "mongodb://10.0.2.2:27017/"
    val mongoClient = MongoClient.create(uri)
    val database = mongoClient.getDatabase("androidtest")
    val settingsCollection = database.getCollection<Settings>("settings")

    lateinit var edtServername : EditText
    lateinit var edtPort : EditText
    lateinit var btnStore : Button
    lateinit var btnLoad : Button
    lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // This demonstrates how MongoDB's Document is always
        // an underlying JSON Document
        val fred = Person("Fred", "Flintstone", 50)
        Log.i(TAG, fred.toJson())
        Log.i(TAG, "Fred is ${fred.age} years old")

        edtServername = findViewById(R.id.edtServername)
        edtPort = findViewById(R.id.edtPortnumber)

        btnLoad = findViewById(R.id.btnLoad)
        btnLoad.setOnClickListener { loadPrefs() }
        btnStore = findViewById(R.id.btnStore)
        btnStore.setOnClickListener { storePrefs() }
        btnDelete = findViewById(R.id.btnDelete)
        btnDelete.setOnClickListener { deletePrefs() }
    }

    fun storePrefs() {
        val settings = Settings(edtServername.text.toString(), edtPort.text.toString().toInt())
        runBlocking {
            // This adds the settings to the collection, regardless of whether
            // one is there already or not. In a typical MongoDB-based application,
            // we'd hold on to the fetched Document (if any) and use that to update,
            // since that document will have an "_id" field which contains the OID
            // for the document (and therefore prevent dupes).
            settingsCollection.insertOne(settings)
        }
    }
    fun loadPrefs() {
        Log.i(TAG, "loadPrefs()")
        runBlocking {
            Log.i(TAG, "loadPrefs() inside runBlocking")

            try {
                // Technically this query returns all of the documents
                // in the "settings" collection, but....
                val results = settingsCollection.find(Document())
                // ... using firstOrNull() will only give us the first
                // document returned in the query, regardless of how
                // many there are. (Whether that's the *right* one is
                // definitely up in the air.)
                val settings = results.firstOrNull()

                Log.i(TAG, "loadPrefs found ${settings?.toJson()}")

                edtServername.setText(settings?.servername)
                edtPort.setText(settings?.port?.toString())
            }
            catch(x : Exception) {
                Log.e(TAG, "Exception", x)
            }
        }
    }
    fun deletePrefs() {
        runBlocking {
            // Just nuke 'em all, empty the whole collection
            settingsCollection.deleteMany(Document())
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        mongoClient.close()
    }
}