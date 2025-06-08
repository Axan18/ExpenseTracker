package axan18

import java.time.LocalDate
import java.util.InputMismatchException

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
        val expense = Transaction(LocalDate.now(), input[0], input[1].toDouble(), input[2])
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

    fun removeExpenses(): Nothing {

        return TODO("Provide the return value")
    }

}