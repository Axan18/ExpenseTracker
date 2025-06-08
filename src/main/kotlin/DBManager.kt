package axan18

import org.sqlite.SQLiteDataSource
import java.io.File
import java.sql.Connection
import java.sql.SQLException

object DBManager {
    init{
        ensureDatabaseExists()
    }

    fun insertExpense(transaction: Transaction) {
        val connection = Database.getConnection()
        val statement = connection.prepareStatement("INSERT INTO expenses(date, category, value, description) VALUES(?,?,?,?)")
        statement.setString(1, transaction.date.toString())
        statement.setString(2, transaction.category)
        statement.setDouble(3, transaction.value)
        statement.setString(4, transaction.description)
        statement.executeUpdate()
        connection.close()
    }

    private fun ensureDatabaseExists(){
        val dbFile = File("src/db/expenses.db")
        if(!dbFile.exists()){
            try{
                Database.createTables()
            }catch (e : SQLException){
                throw IllegalStateException("Cannot create database", e)
            }
        }
        Database.state = true
    }

     object Database{
        var state : Boolean = false
        private val dataSource: SQLiteDataSource = SQLiteDataSource().apply {
            url = "jdbc:sqlite:src/db/expenses.db"
            databaseName = "expenses"
        }

        fun getConnection(): Connection = dataSource.connection
        fun createTables(){
            val connection = getConnection()
            val statement = connection.createStatement()
            statement.execute("CREATE TABLE IF NOT EXISTS expenses(date TEXT NOT NULL, category TEXT, value REAL NOT NULL, description TEXT NOT NULL)")
            connection.close()
        }
    }
}