package com.example.automatedworkstationaws

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
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
    private var heightOfFooter = 130
    private var lastBtnInRow = 1

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

        //openLogs(findViewById<LinearLayout>(R.id.linearLayout))
        bar()
        //findViewById<Button>(R.id.firstPage).setTextColor(Color.BLUE)
    }

    private fun  bar(){
        val view = findViewById<LinearLayout>(R.id.linearLayout)
        view.addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                view.removeOnLayoutChangeListener(this)
                CalcHeight()
                openLogs(findViewById<Button>(R.id.btLogs))
                refreshBtns()
                // get dimensions now
            }
        })
    }

    private fun CalcHeight(){
        val display = windowManager.defaultDisplay
        val width = display.width // deprecated
        val height = display.height
        val foo = height - findViewById<LinearLayout>(R.id.activity).height  - findViewById<LinearLayout>(R.id.TableHead).height - heightOfFooter

        val elmsOnList = Math.floor(foo.toDouble() / (this.height + 40).toDouble()).toInt()
        this.AMOUNT_OF_OBJECTS = elmsOnList
        findViewById<LinearLayout>(R.id.Footer).layoutParams.height = heightOfFooter + foo - elmsOnList * (this.height + 40)

        println("Foo = " + foo + " | " + "Height = " + this.height + " | " + " height = " + height + " | " + "btCodeReader.height = " + findViewById<Button>(R.id.btCodeReader).height +
                " | " + "Num.height = " + findViewById<TextView>(R.id.Num).height + " | " + "Footer.height = " + findViewById<LinearLayout>(R.id.Footer).height)
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
        if (page + 2 <= Math.ceil((db.getData().size.toDouble()/AMOUNT_OF_OBJECTS.toDouble()))) {
            page++
            var oldText =  findViewById<Button>(R.id.firstPage).text.toString()
            findViewById<Button>(R.id.firstPage).text = (oldText.toInt() + 1).toString()
            findViewById<Button>(R.id.firstPage).setTextColor(Color.BLUE)

            oldText =  findViewById<Button>(R.id.secondPage).text.toString()
            findViewById<Button>(R.id.secondPage).text = (oldText.toInt() + 1).toString()

            println("Pages = " + Math.ceil((db.getData().size.toDouble()/AMOUNT_OF_OBJECTS.toDouble())) + " | " + "Text = " + (oldText.toInt() + 1).toString() +
                    " | " + "amount of objects = " + AMOUNT_OF_OBJECTS + " | " + "Size = " + db.getData().size)

            fillData()
            CodeReaderField.isVisible = true;
            LogsField.isVisible = false;
            CodeReaderField.isVisible = false;
            LogsField.isVisible = true;
        }
    }

    @SuppressLint("ResourceAsColor")
    fun prevPage(view: View){
        if (page - 1 != 0) {
            page--

            var oldText =  findViewById<Button>(R.id.firstPage).text.toString()
            findViewById<Button>(R.id.firstPage).text = (oldText.toInt() - 1).toString()

            oldText =  findViewById<Button>(R.id.secondPage).text.toString()
            findViewById<Button>(R.id.secondPage).text = (oldText.toInt() - 1).toString()
            fillData()

            CodeReaderField.isVisible = true;
            LogsField.isVisible = false;
            CodeReaderField.isVisible = false;
            LogsField.isVisible = true;
        }
    }

    private fun refreshBtns(){
        val display = windowManager.defaultDisplay
        val width = display.width
        //val width = 700

        var btnWidth1 = width * 0.35
        var btnWidth2 = width * 0.1
        var num = 3
        println("All width = $width, Big width = $btnWidth1, Small width = $btnWidth2")
        if (btnWidth1 > 350.0) {
            btnWidth1 = 270.0
            btnWidth2 = 90.0

            val balance = width - btnWidth1 * 2
            num = Math.ceil(balance/btnWidth2).toInt()
            if (num % 2 == 0) num -= 1
        }

        lastBtnInRow = num

        findViewById<LinearLayout>(R.id.Footer).removeAllViews()

        val container = LinearLayout(this)
        container.layoutParams = ViewGroup.LayoutParams(width,150)
        container.setBackgroundColor(Color.WHITE)
        container.gravity = Gravity.CENTER

        findViewById<LinearLayout>(R.id.Footer).addView(container)

        val goBack = Button(this)
        goBack.width = btnWidth1.toInt()
        goBack.height = container.height
        goBack.text = "Назад"
        goBack.setPadding(0,5,0,5)
        goBack.setTextColor(Color.DKGRAY)
        goBack.setBackgroundColor(Color.WHITE)

        container.addView(goBack)

        var fff = 0
        for (it in 1..num) {

            val btn = Button(this)
            btn.minimumWidth = 0
            btn.id = it
            btn.width = btnWidth2.toInt()
            btn.height = container.height
            btn.text = it.toString()
            btn.setPadding(0,5,0,5)
            btn.setTextColor(Color.DKGRAY)
            btn.setBackgroundColor(Color.WHITE)
            fff = btn.width
            container.addView(btn)
            println("All width = $width, Big width = $btnWidth1, Small width = $btnWidth2, Width id = $fff")
        }

        val goForward = Button(this)
        goForward.width = btnWidth1.toInt()
        goForward.height = container.height
        goForward.text = "Вперед"
        goForward.setPadding(0,5,0,5)
        goForward.setTextColor(Color.DKGRAY)
        goForward.setBackgroundColor(Color.WHITE)

        container.addView(goForward)
    }

    private fun fillData(){
        val table =  findViewById<TableLayout>(R.id.textLogs)
        table.removeAllViews()
        var color = true
        val allLinks = db.getData()
        val visiblePage : ArrayList<String> = ArrayList()

        for (it in AMOUNT_OF_OBJECTS * (page - 1) until AMOUNT_OF_OBJECTS*page){
            try {
                visiblePage.add(allLinks[it])
            }catch (e: Exception){
                visiblePage.add("||||")
            }
        }

        for (it in 0 until visiblePage.size) {
            val str = visiblePage[it];
            val elements = str.split("|")
            val tableRow = TableRow(this)
            if (it != visiblePage.size - 1){
                if (color) tableRow.setBackgroundColor(Color.WHITE)
                else tableRow.setBackgroundColor(Color.LTGRAY)
            }else{
                tableRow.setBackgroundColor(Color.WHITE)
            }

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