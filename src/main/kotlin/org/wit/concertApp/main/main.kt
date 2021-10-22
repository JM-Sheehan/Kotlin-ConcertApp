package org.wit.concertApp.main

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>){
    println("Concert App Version 1.0")
    logger.info { "Launching Concert Console App" }

    var input: Int
    do{
        input = menu()
        when (input){
            1    ->  addConcert()
            2    ->  updateConcer()
            3    ->  listAllConcerts()
            4    ->  deleteConcert()
            5    ->  searchConcert()
            6    ->  addUser()
            7    ->  updateUser()
            8    ->  listAllUsers()
            9    ->  deleteUser()
            10   ->  searchUser()
            -1   ->  println("Exiting App")
            else ->  println("Invalid Option")
        }
    }while (input != -1)

}

fun menu() : Int {

    var option : Int
    var input: String? = null

    println("Main Menu")
    println(" 1. Add Concert")
    println(" 2. Update Concert")
    println(" 3. List All Concerts")
    println(" 4. Delete Concert")
    println(" 5. Search Concert")
    println(" 6. Add User")
    println(" 7. Update User")
    println(" 8. List All Users")
    println(" 9. Delete User")
    println(" 10. Search User")
    println("-1. Exit")
    println()
    print("Enter an integer : ")
    input = readLine()!!
    option = if (input.toIntOrNull() != null && !input.isEmpty())
        input.toInt()
    else
        -9
    return option
}

fun addConcert(){

}
fun updateConcer(){

}

fun listAllConcerts(){

}

fun deleteConcert(){

}

fun searchConcert(){

}

fun addUser(){

}

fun updateUser(){

}

fun listAllUsers(){

}

fun deleteUser(){

}

fun searchUser(){

}