package com.example.automatedworkstationaws

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.text.Layout
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.view.isVisible
import layout.LogsDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var CodeReaderField: LinearLayout
    private lateinit var LogsField: LinearLayout
    private lateinit var db: LogsDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)

        val path = this.filesDir

        db = LogsDatabase(path)
        db.writeLog(12324546, 'K', 1200, "08:15", "14.01")
        db.writeLog(12324547, 'П', 300, "08:25", "14.01")
        db.readLogs()

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

        fillData()
    }

    private fun fillData(){
        val table =  findViewById<TableLayout>(R.id.textLogs)
        table.removeAllViews()
        var color = true

        db.getData().forEach {
            val str = it;
            val elements = str.split("|")
            val tableRow = TableRow(this)
            if (color) tableRow.setBackgroundColor(Color.LTGRAY)
            else tableRow.setBackgroundColor(Color.WHITE)
            tableRow.setPadding(0, 20, 0, 20)
            //tableRow.orientation =TableRow.HORIZONTAL

            val code = TextView(this)
            code.text = elements[0]
            code.textSize = 14F
            code.setTextColor(Color.BLACK)
            code.width = findViewById<TextView>(R.id.Num).width
            code.height = findViewById<TextView>(R.id.Num).height
            code.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(code)

            val type = TextView(this)
            type.text = elements[1]
            type.textSize = 14F
            type.setTextColor(Color.BLACK)
            type.width = findViewById<TextView>(R.id.Type).width
            type.height = findViewById<TextView>(R.id.Type).height
            type.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(type)

            val weight = TextView(this)
            weight.text = elements[2]
            weight.textSize = 14F
            weight.setTextColor(Color.BLACK)
            weight.width = findViewById<TextView>(R.id.WeigthKilo).width
            weight.height = findViewById<TextView>(R.id.WeigthKilo).height
            weight.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(weight)

            val time = TextView(this)
            time.text = elements[3]
            time.textSize = 14F
            time.setTextColor(Color.BLACK)
            time.width = findViewById<TextView>(R.id.Time).width
            time.height = findViewById<TextView>(R.id.Time).height
            time.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(time)

            val date = TextView(this)
            date.text = elements[4]
            date.textSize = 14F
            date.setTextColor(Color.BLACK)
            date.width = findViewById<TextView>(R.id.Date).width
            date.height = findViewById<TextView>(R.id.Date).height
            date.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(date)
            color = !color

            table.addView(tableRow)
        }
    }
}