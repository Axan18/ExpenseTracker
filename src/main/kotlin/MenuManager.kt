package axan18

import java.time.LocalDate
import java.util.InputMismatchException
import javax.swing.JFileChooser

object MenuManager {
     fun addExpense() : Unit{
        clearConsole()
        var input : List<String>
        while(true){
            try{
                input = getExpenseInput()
                break
            }catch (e : Exception){
                println(e)
            }
        }
        val expense = Transaction(null,LocalDate.now(), input[0], input[1].toDouble(), input[2])
        DBManager.insertExpense(expense)
        clearConsole()
    }
    private fun getExpenseInput() : List<String>{
        println("Input 3 values separated by tabs:")
        println("Category Value Description")
        val input = readlnOrNull()?.split("\t")
        if(input==null || input.size!=3)
            throw IllegalStateException("Wrong number of arguments")
        if(input[1].toDoubleOrNull() == null){
            throw InputMismatchException("Value is not valid number, try 123.456 notation")
        }
        return input
    }

    fun viewExpenses(): Unit {
        clearConsole()
        println("EXPENSES")
        val expenses = DBManager.selectExpenses()
        println("ID\tDate\tDescription\tValue\tCategory")
        expenses.stream()
            .map {
                "${it.id}\t${it.date}\t${it.description}\t${it.value}\t${it.category}\n"
            }.forEach { print(it) }

        println("Pass X to exit")
        while(true){
            val input = readlnOrNull()
            if(input == "X"){
                break
            }
        }
        clearConsole()
    }

    fun clearConsole() {
        print("\u001b[H\u001b[2J")
        System.out.flush()
    }

    fun removeExpense(): Unit {
        var idToDelete :Int?
        while(true){
            clearConsole()
            println("Provide ID of the expense, that you would like to delete")
            idToDelete = readln().toIntOrNull()
            if(idToDelete == null) continue
            when(DBManager.removeExpense(idToDelete)){
                true -> println("Expense succesfully deleted")
                false -> println("Expense has not be deleted")
            }
            break
        }
        println("Pass X to exit")
        while(true){
            val input = readlnOrNull()
            if(input == "X"){
                break
            }
        }
        clearConsole()


    }

    fun importCSV(): Unit {
        var chooser : JFileChooser
        var result : Int
        while(true){
            chooser = JFileChooser()
            result = chooser.showOpenDialog(null)
            if(result != JFileChooser.APPROVE_OPTION)
                return
            break
        }
        val csvFile = chooser.selectedFile
        val transactions = CSVHandler().readCsv(csvFile.inputStream())
        DBManager.batchInsert(transactions)
        println("")

    }

}