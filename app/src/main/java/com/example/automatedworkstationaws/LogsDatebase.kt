package layout

import java.io.File
import java.io.Serializable

class LogsDatabase : Serializable {

    private lateinit var file: File
    private var data: ArrayList<String> = arrayListOf<String>()
    private val LOGS_LENGTH = 6
    private var allData: ArrayList<String> = ArrayList()

    constructor(path: File) {
        file = File(path, "data.txt")

        try {
            readLogs()
        } catch (e: Exception){

        }

//        if (file.exists())
//            readLogs()
//        else
//            file.createNewFile()
//            readLogs()
    }

    fun readLogs() {
        var fileContent : ArrayList<String>  = file.readLines() as ArrayList<String>
        allData = ArrayList<String>(fileContent)

//        var allData: ArrayList<String> = file.readLines() as ArrayList<String>
//        var resultData: ArrayList<String> = arrayListOf<String>()
//        if (allData.size> LOGS_LENGTH) {
//            for (i in allData.size - LOGS_LENGTH until allData.size)
//                resultData.add(allData[i])
//            cleanLogs(resultData)
//        } else {
//            resultData = allData
//        }

        data = allData
    }

    fun getData(): ArrayList<String> {
        return data
    }

//    fun getData(): String{
//        return allData.toString()
//    }

    fun writeLog(num: Long, type: Char, weight: Int, date: String, data: String) {
//        if (allData.size > LOGS_LENGTH) {
//            cleanLogs(allData)
//        }

        file.appendText("$num|$type|$weight|$date|$data\n")
//        val writer = file.bufferedWriter()
//        writer.write(data)
//        writer.newLine()
//        writer.close()
    }

    fun cleanLogs(list: ArrayList<String>){
        file.writeText("")

        for (i in 1 until list.size)
            file.appendText(list[i] + "\n")
    }
}