package com.example.automatedworkstationaws

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import layout.LogsDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var CodeReaderField: LinearLayout
    private lateinit var LogsField: LinearLayout
    private lateinit var db: LogsDatabase
    private var AMOUNT_OF_OBJECTS = 6
    private val height = 60
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState)

        val path = this.filesDir

        db = LogsDatabase(path)
        db.writeLog(12324546, 'K', 1200, "08:15", "14.01")
        db.writeLog(12324547, 'П', 300, "08:25", "14.01")
        db.writeLog(12324552, 'П', 350, "09:25", "15.01")
        db.writeLog(12324579, 'К', 1300, "10:20", "15.01")
//        db.writeLog(12324579, 'П', 450, "14:20", "17.01")
//        db.writeLog(12324579, 'К', 1500, "09:20", "16.01")
        db.readLogs()

        setContentView(R.layout.activity_main)
        CodeReaderField = findViewById<LinearLayout>(R.id.CodeReaderPage)
        LogsField = findViewById<LinearLayout>(R.id.LogsPage)
        CalcHeight()
        openLogs(findViewById<Button>(R.id.btLogs))
        //openLogs(findViewById<LinearLayout>(R.id.linearLayout))
    }

    private fun CalcHeight(){
        val display = windowManager.defaultDisplay
        val width = display.width // deprecated
        val height = display.height
        val foo = height - findViewById<Button>(R.id.btCodeReader).height - findViewById<TextView>(R.id.Num).height - findViewById<LinearLayout>(R.id.Footer).height

        val elmsOnList = Math.ceil((foo / this.height).toDouble()).toInt()
        this.AMOUNT_OF_OBJECTS = elmsOnList
        findViewById<LinearLayout>(R.id.Footer).layoutParams.height += foo - elmsOnList * this.height
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

    @SuppressLint("ResourceAsColor")
    fun nextPage(view: View){
        page++
        fillData()
        CodeReaderField.isVisible = true;
        LogsField.isVisible = false;
        CodeReaderField.isVisible = false;
        LogsField.isVisible = true;
    }

    @SuppressLint("ResourceAsColor")
    fun prevPage(view: View){
        page--
        fillData()
        CodeReaderField.isVisible = true;
        LogsField.isVisible = false;
        CodeReaderField.isVisible = false;
        LogsField.isVisible = true;
    }

    private fun fillData(){
        val table =  findViewById<TableLayout>(R.id.textLogs)
        table.removeAllViews()
        var color = true
        val allLinks = db.getData()
        val visiblePage : ArrayList<String> = ArrayList()
        println("Count = " + allLinks.size)
        for (it in AMOUNT_OF_OBJECTS * (page - 1) until AMOUNT_OF_OBJECTS*page){
            try {
                visiblePage.add(allLinks[it])
            }catch (e: Exception){
                visiblePage.add("||||")
            }
        }

        visiblePage.forEach {
            val str = it;
            val elements = str.split("|")
            val tableRow = TableRow(this)
            if (color) tableRow.setBackgroundColor(Color.LTGRAY)
            else tableRow.setBackgroundColor(Color.WHITE)
            tableRow.setPadding(0, 20, 0, 20)
            tableRow.gravity = Gravity.CENTER
            //tableRow.orientation =TableRow.HORIZONTAL

            val code = TextView(this)
            code.text = elements[0]
            code.textSize = 14F
            code.setTextColor(Color.BLACK)
            code.width = findViewById<TextView>(R.id.Num).width
            code.height = height
            code.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(code)

            val type = TextView(this)
            type.text = elements[1]
            type.textSize = 14F
            type.setTextColor(Color.BLACK)
            type.width = findViewById<TextView>(R.id.Type).width
            type.height = height
            type.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(type)

            val weight = TextView(this)
            weight.text = elements[2]
            weight.textSize = 14F
            weight.setTextColor(Color.BLACK)
            weight.width = findViewById<TextView>(R.id.WeigthKilo).width
            weight.height = height
            weight.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(weight)

            val time = TextView(this)
            time.text = elements[3]
            time.textSize = 14F
            time.setTextColor(Color.BLACK)
            time.width = findViewById<TextView>(R.id.Time).width
            time.height = height
            time.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(time)

            val date = TextView(this)
            date.text = elements[4]
            date.textSize = 14F
            date.setTextColor(Color.BLACK)
            date.width = findViewById<TextView>(R.id.Date).width
            date.height = height
            date.textAlignment = View.TEXT_ALIGNMENT_CENTER
            //code.gravity = Gravity.CENTER
            tableRow.addView(date)
            color = !color

            table.addView(tableRow)
        }
    }
}