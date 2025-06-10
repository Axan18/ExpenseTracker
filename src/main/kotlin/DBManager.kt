package axan18

import org.sqlite.SQLiteDataSource
import java.io.File
import java.sql.BatchUpdateException
import java.sql.Connection
import java.sql.SQLException
import java.sql.Statement
import java.time.LocalDate
import java.util.zip.DataFormatException

object DBManager {
    private val dataSource : SQLiteDataSource = SQLiteDataSource().apply {
        url = "jdbc:sqlite:src/db/expenses.db"
        databaseName = "expenses"
    }
    private var dbExists = false;
    init{
        ensureDatabaseExists()
    }

    fun insertExpense(transaction: Transaction) {
        getConnection().use {
            connection -> connection.prepareStatement(
                "INSERT INTO expenses(date, category, value, description) VALUES(?,?,?,?)"
            ).use { statement ->
                statement.setString(1, transaction.date.toString())
                statement.setString(2, transaction.category)
                statement.setDouble(3, transaction.value)
                statement.setString(4, transaction.description)
                statement.executeUpdate()
            }
        }
    }
    fun batchInsert(transactions : List<Transaction>){
        getConnection().use{
            connection -> connection.prepareStatement(
            "INSERT INTO expenses(date, category, value, description) VALUES(?,?,?,?)"
            ).use { statement ->
                for (transaction in transactions){
                    statement.setString(1, transaction.date.toString())
                    statement.setString(4, transaction.category)
                    statement.setDouble(3, transaction.value)
                    statement.setString(2, transaction.description)
                    statement.addBatch()
                }
                try{
                    statement.executeBatch()

                }catch (e : BatchUpdateException){
                    println(e.message)
                    throw DataFormatException("Records: ${e.updateCounts.filter { it == Statement.EXECUTE_FAILED }}")
                }
            }
        }
    }
    fun selectExpenses() : List<Transaction>{
        getConnection().use{
            connection -> connection.createStatement().use{
                val result = it.executeQuery("SELECT * FROM expenses order by date desc")
                val expenses = ArrayList<Transaction>()
                while(result.next()){
                    expenses.add(Transaction(
                        result.getInt(1),
                        LocalDate.parse(result.getString(2)),
                        result.getString(3),
                        result.getDouble(4),
                        result.getString(5)
                    ))
                }
                return expenses
            }
        }
    }
    private fun ensureDatabaseExists(){
        val dbFile = File("src/db/expenses.db")
        if(!dbFile.exists()){
            try{
                createTables()
            }catch (e : SQLException){
                throw IllegalStateException("Cannot create database", e)
            }
        }
        dbExists = true
    }

    private fun getConnection(): Connection = dataSource.connection
    private fun createTables(){
        val connection = getConnection()
        val statement = connection.createStatement()
        statement.execute("CREATE TABLE IF NOT EXISTS expenses(id INTEGER PRIMARY KEY ,date TEXT NOT NULL, category TEXT, value REAL NOT NULL, description TEXT NOT NULL)")
        connection.close()
    }

    fun removeExpense(idToDelete: Int) : Boolean {
        getConnection().use{
            connection -> connection.prepareStatement("DELETE FROM expenses where ID = ?").use {
                it.setInt(1,idToDelete)
                return it.executeUpdate() > 0
            }
        }
    }
}