package org.wit.concertApp.views

import org.wit.concertApp.models.ConcertMemStore
import org.wit.concertApp.models.ConcertModel
import org.wit.concertApp.models.UserMemStore
import org.wit.concertApp.models.UserModel

class ConcertView {

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
    fun addConcertData(concert : ConcertModel) : Boolean{
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
            var character : Char = readLine()!!.first()
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

        return (concert.headlineAct.isNotEmpty() && concert.date.isNotEmpty() &&
                concert.address.isNotEmpty())
    }

    fun updateConcertData(concert : ConcertModel) : Boolean{
        var tempDate : String?
        var tempHeadlineAct : String?
        var tempSupportingActs : ArrayList<String> = ArrayList<String>()
        var tempUrl : String?
        var tempAddress : String?

        println("Update Concert")
        println()
        if(concert != null){
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
                    tempDate = "$day-$month-$year"
                }
                else{
                    tempDate =concert.date
                }
            }
            else{
                tempDate = concert.date
            }

            println("Change Headline Act y/n")
            if(readLine()!!.first() == 'y'){
                println("Enter Headline Act")
                tempHeadlineAct = readLine()!!
            }
            else{
                tempHeadlineAct = concert.headlineAct
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
                            tempSupportingActs.add(act)
                        }
                    }
                    else if( character == 'n' ){
                        addingActs = false
                    }
                }while (addingActs)

            }
            else{
                tempSupportingActs = concert.supportingActs
            }

            println("Change Web Link y/n")
            if (readLine()!!.first() == 'y'){
                println("Enter Web Link")
                tempUrl = readLine()!!
            }
            else{
                tempUrl = concert.url
            }

            println("Change Address y/n")
            if(readLine()!!.first() == 'y'){
                print("Enter Address")
                tempAddress = readLine()!!
            }
            else{
                tempAddress = concert.address
            }
            if(!tempDate.isNullOrEmpty() && !tempHeadlineAct.isNullOrEmpty() && !tempAddress.isNullOrEmpty()
                &&!tempUrl.isNullOrEmpty()){
                concert.date = tempDate
                concert.headlineAct = tempHeadlineAct
                concert.supportingActs = tempSupportingActs
                concert.url = tempUrl
                concert.address = tempAddress

                return true
            }
        }
        return false
    }

    fun listConcerts(concerts : ConcertMemStore){
        println("List All Concerts")
        println()
        concerts.logAll()
        println()
    }

    fun showConcert(concert : ConcertModel){
        if (concert != null) println("Concert Details [$concert]")
        else println("Concert Not Found")
    }

    fun addUserData(user : UserModel) : Boolean{
        println("Enter User Details")
        println("First Name")
        user.firstName = readLine()!!
        println("Last Name")
        user.lastName = readLine()!!

        return (user.firstName.isNotEmpty() && user.lastName.isNotEmpty())
    }
    fun updateUserData(user : UserModel) : Boolean{
        var tempFirstName: String?
        var tempLastName: String?

        if(user != null){
            println("Update User Details")
            println("Change First Name from ${user.firstName}")
            tempFirstName = readLine()!!
            println("Change First Name from ${user.lastName}")
            tempLastName = readLine()!!

            if(!tempFirstName.isNullOrEmpty() && !tempLastName.isNullOrEmpty()){
                user.firstName = tempFirstName
                user.lastName = tempLastName
                return true
            }
        }
        return false
    }

    fun listUsers(users : UserMemStore) {
        println("List All Users")
        println()
        users.logAll()
        println()
    }

    fun showUser(user : UserModel) {
        if (user != null) println("User Details [$user]")
        else println("User Not Found")
    }


    fun getId() : Long {
        var strId : String? // String to hold user input
        var searchId : Long // Long to hold converted id
        print("Enter id to Search/Update/Create/Delete : ")
        strId = readLine()!!
        searchId = if (strId.toLongOrNull() != null && !strId.isEmpty())
            strId.toLong()
        else
            -9
        return searchId
    }

}