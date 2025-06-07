package axan18

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    println("ExpenseTracker")
    println("""
        1. Add expense
        2. Remove expense
        3. View your expenses
    """.trimIndent())
    while(true){
        val menuOption = readln()
        when(menuOption){
            "1" -> MenuManager.addExpense()
            else -> continue
        }
    }

}