package org.wit.concertApp.main

import mu.KotlinLogging
import org.wit.concertApp.models.ConcertModel
import org.wit.concertApp.models.UserModel

private val logger = KotlinLogging.logger {}

val concerts = ArrayList<ConcertModel>()
val users = ArrayList<UserModel>()
fun main(args: Array<String>){
    println("Concert App Version 1.0")
    logger.info { "Launching Concert Console App" }

    var input: Int
    do{
        input = menu()
        when (input){
            1    ->  addConcert()
            2    ->  updateConcert()
            3    ->  listAllConcerts()
            4    ->  deleteConcert()
            5    ->  searchConcert()
            6    ->  addUser()
            7    ->  updateUser()
            8    ->  listAllUsers()
            9    ->  deleteUser()
            10   ->  searchUser()
            11   ->  attendConcert()
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
    println(" 11. Add To Users Upcoming Concerts")
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
    var concert = ConcertModel()
    println("Enter Date")
    println("Year")
    val year = readLine()!!
    println("Month")
    val month = readLine()!!
    println("Day")
    val day = readLine()!!
    if (day.isNotEmpty() && month.isNotEmpty() && year.isNotEmpty()){
        concert.date = "$day-$month-$year"
    }

    println("Enter Headline Act")
    concert.headlineAct= readLine()!!

    var addingActs : Boolean = true

    do {
        println("Want to Add Supporting Acts y/n")
        var character : Char
        character = readLine()!!.first()
        if ( character == 'y'){
            println("Enter Act Name")
            val act = readLine()!!
            concert.supportingActs.add(act)
        }
        else if( character == 'n' ){
            addingActs = false
        }
    }while (addingActs)

    println("Enter Web Link")
    concert.url = readLine()!!

    println("Enter Address")
    concert.address = readLine()!!

    concert.id = (concerts.size.toLong()) + 1
    if(concert.date.isNotEmpty() && concert.headlineAct.isNotEmpty()
        && concert.address.isNotEmpty()){
        concerts.add(concert.copy())
        logger.info("Concert Added : [$concert]")
    }
    else{
        logger.info("Concert Not Added")
    }
}
fun updateConcert(){
    println("Update Concert")
    println()
    listAllConcerts()
    var searchId = getId()
    val aConcert = findConcert(searchId)
    if(aConcert != null){
        print("Change Date y/n")
        if(readLine()!!.first() == 'y'){
            println("Enter Date")
            println("Year")
            val year = readLine()!!
            println("Month")
            val month = readLine()!!
            println("Day")
            val day = readLine()!!
            if(year.isNotEmpty() && month.isNotEmpty() && day.isNotEmpty()){
                aConcert.date = "$day-$month-$year"
            }
        }

        println("Change Headline Act y/n")
        if(readLine()!!.first() == 'y'){
            println("Enter Headline Act")
            val headlineAct = readLine()!!
            if(headlineAct.isNotEmpty()){
                aConcert.headlineAct = headlineAct
            }
        }

        println("Change Supporting Acts y/n")
        if(readLine()!!.first() == 'y'){
            var addingActs : Boolean = true

            do {
                println("Want to Add Supporting Acts y/n")
                var character : Char
                character = readLine()!!.first()
                if ( character == 'y'){
                    println("Enter Act Name")
                    val act = readLine()!!
                    if(act.isNotEmpty()){
                        aConcert.supportingActs.add(act)
                    }
                }
                else if( character == 'n' ){
                    addingActs = false
                }
            }while (addingActs)

        }

        println("Change Web Link y/n")
        if (readLine()!!.first() == 'y'){
            println("Enter Web Link")
            val url = readLine()!!
            if (url.isNotEmpty()){
                aConcert.url = readLine()!!
            }

        }

        println("Change Address y/n")
        if(readLine()!!.first() == 'y'){
            print("Enter Address")
            val address = readLine()!!
            if (address.isNotEmpty()){
                aConcert.address = readLine()!!
            }
        }
    }
    else{
        println("Concert Was Not Updated...")
    }

}

fun listAllConcerts(){
    println("List All Concerts")
    println()
    concerts.forEach{ logger.info("${it}")}
}

fun deleteConcert(){
    listAllConcerts()
    println("Choose Concert to Delte")
    val searchId = getId()

    concerts.removeAll({it.id == searchId})
    listAllConcerts()
}

fun getId() : Long{
    var strId: String?
    var searchId : Long
    println("Enter id to Search/Update/Delete/Add")
    strId = readLine()!!
    searchId =
        if(strId.toLongOrNull() != null && strId.isNotEmpty())
            strId.toLong()
        else
            -9L
    return  searchId
}


fun findConcert(id: Long) : ConcertModel?{
    var foundConcert : ConcertModel? = concerts.find {c -> c.id == id}
    return foundConcert
}

fun searchConcert(){
    var searchId = getId()
    val aConcert = findConcert(searchId)

    if(aConcert != null){
        println("Concert Details [$aConcert]")
    }
    else{
        println("Concert Not Found...")
    }
}

fun addUser(){
    var user = UserModel()
    println("Enter User Details")
    println("First Name")
    user.firstName = readLine()!!
    println("Last Name")
    user.lastName = readLine()!!

    user.id = (users.size.toLong()) + 1

    if(user.firstName.isNotEmpty() && user.lastName.isNotEmpty()){
        users.add(user.copy())
        logger.info("User Added : [$user]")
    }
    else{
        logger.info("User Not Added")
    }
}

fun updateUser(){
    println("Update User")
    println()
    listAllUsers()
    var searchId = getId()
    val aUser = findUser(searchId)
    if(aUser != null){
        println("Update User Details")
        println("Change First Name from ${aUser.firstName}")
        val firstName = readLine()!!
        if(firstName.isNotEmpty()){
            aUser.firstName = firstName
        }
        println("Change First Name from ${aUser.lastName}")
        val lastName = readLine()!!
        if(lastName.isNotEmpty()){
            aUser.lastName = lastName
        }
    }
    else{
        println("User Not Updated...")
    }
}

fun listAllUsers(){
    println("List All Users")
    println()
    users.forEach{ logger.info("${it}")}
}

fun deleteUser(){
    listAllUsers()
    val searchId = getId()
    println("Choose User to Delete")

    users.removeAll({it.id == searchId})
    listAllUsers()
}

fun findUser(id: Long) : UserModel?{
    var foundUser : UserModel? = users.find {u -> u.id == id}
    return foundUser
}

fun searchUser(){
    var searchId = getId()
    val aUser = findUser(searchId)

    if(aUser != null){
        println("Concert Details [$aUser]")
    }
    else{
        println("Concert Not Found...")
    }
}

fun attendConcert(){
    listAllUsers()
    println("Choose User to Attend Concert")
    val userId = getId()

    listAllConcerts()
    println("Choose Concert to Attend")
    val concertId = getId()

    val user = findUser(userId)
    if(user != null && concertId != -9L){
        user.upcomingConcerts.add(concertId)
        logger.info ("Concert: [${findConcert(concertId)}] was" +
                "added to [${user}] upcoming concerts")
    }
    else{
        logger.info ("Not added to [${user}] upcoming concerts")
    }
}