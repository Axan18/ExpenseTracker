package axan18

import java.time.LocalDate

private data class Category(val name: String){

}

data class Transaction(
    val date : LocalDate,
    val category : String?,
    val value : Double,
    val description: String) {

    init{
        require(category?.isNotBlank() == true){"Category cannot be blank"}
        require(value>0){"Value cannot be negative"}
        require(description.isNotBlank()){"Describe your transaction"}
        //TODO: category verification
    }
}