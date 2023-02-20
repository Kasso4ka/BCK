package com.example.bckapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL
import android.view.View;
import android.webkit.WebView
import java.io.IOException
import java.io.InputStream
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import androidx.fragment.app.DialogFragment
import android.app.Dialog
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import android.util.Log

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result: TextView = findViewById(R.id.text1)
        val inputurl: EditText = findViewById(R.id.urlinput)
        val btnSend: Button = findViewById(R.id.btn_request)
        val login: EditText = findViewById(R.id.input1)
        val pass: EditText = findViewById(R.id.input2)
        val nam: EditText = findViewById(R.id.input3)
        val txt: EditText = findViewById(R.id.input4)




        btnSend.setOnClickListener() {

                val PostData = "act=add&login=${login.text}&pass=${pass.text}&uid=no&nam=${nam.text}&txt=${txt.text}"




                GlobalScope.launch (Dispatchers.IO) {

                    val url = URL("https://script.google.com/macros/s/AKfycbz5ixdIqKc1osEF8hBT-86hObnfTNVUU7TJVrAw63kq3c1ga_34E6cTQlD4bVv7hTH2uQ/exec")
                    val conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "POST"
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                    conn.setRequestProperty("Accept","application/json")
                    conn.doOutput = true
                    conn.doInput = true

                    val outStream = OutputStreamWriter(conn.outputStream)
                    Log.d("Input", PostData)
                    outStream.write(PostData)
                    outStream.flush()

                    val status = conn.responseCode
                    if (status == HttpURLConnection.HTTP_OK) {
                        val response = conn.inputStream.bufferedReader()
                            .use { it.readText() }
                        withContext((Dispatchers.Main)) {
                            Log.d("JSON:", response)
                        }
                }
                    else {
                        Log.e("HTTPURLCONNECTION_ERROR", status.toString(1))
                    }
            }


        }

    }




   /* fun send_req (view: android.view.View){
        var url = URL(R.id.urlinput.toString());
        if (url.equals("")) {
            val errUrl = DialogErr()
            val manager = supportFragmentManager
            errUrl.show(manager, "myDialog")
        }
        val postData = "afssaf"
        val conn = url.openConnection()
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
        conn.setRequestProperty("Content-Length",postData.length.toString())
    }*/
}