package axan18

import java.util.InputMismatchException

object MenuManager {

    @JvmStatic
    public fun addExpense() : Unit{
        var input : List<String>?
        while(true){
            try{
                input = getExpenseInput()
                break
            }catch (e : Exception){
                println(e)
            }
        }
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
}