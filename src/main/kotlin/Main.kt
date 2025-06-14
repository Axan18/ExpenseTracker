package axan18

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    while(true){
        MenuManager.clearConsole()
        println("ExpenseTracker")
        println("""
        1. Add expense
        2. View your expenses
        3. Remove expense
        4. Import from CSV
        5. Export to CSV
        6. Exit
    """.trimIndent())
        val menuOption = readln()
        when(menuOption){
            "1" -> MenuManager.addExpense()
            "2" -> MenuManager.viewExpenses()
            "3" -> MenuManager.removeExpense()
            "4" -> MenuManager.importCSV()
            //"5" -> MenuManager.exportCSV()
            "6" -> return
            else -> continue
        }
    }

}