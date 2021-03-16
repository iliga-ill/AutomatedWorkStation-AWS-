package com.example.automatedworkstationaws

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    private lateinit var CodeReaderField: LinearLayout
    private lateinit var LogsField: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CodeReaderField = findViewById<LinearLayout>(R.id.CodeReaderPage)
        LogsField = findViewById<LinearLayout>(R.id.LogsPage)



        openLogs(findViewById<Button>(R.id.btLogs))
    }

    @SuppressLint("ResourceAsColor")
    public fun openCodeReader(view: View){
        findViewById<Button>(R.id.btCodeReader).setBackgroundColor(Color.rgb(22, 125, 253));
        findViewById<Button>(R.id.btLogs).setBackgroundColor(Color.rgb(37, 88, 154));
        CodeReaderField.isVisible = true;
        LogsField.isVisible = false;
    }

    @SuppressLint("ResourceAsColor")
    public fun openLogs(view: View){
        findViewById<Button>(R.id.btCodeReader).setBackgroundColor(Color.rgb(37, 88, 154));
        findViewById<Button>(R.id.btLogs).setBackgroundColor(Color.rgb(22, 125, 253));
        CodeReaderField.isVisible = false;
        LogsField.isVisible = true;
    }
}