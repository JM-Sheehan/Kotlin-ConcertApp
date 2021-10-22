package org.wit.concertApp.controllers
import  mu.KotlinLogging
import org.wit.concertApp.main.*
import org.wit.concertApp.models.ConcertMemStore
import org.wit.concertApp.models.ConcertModel
import org.wit.concertApp.models.UserMemStore
import org.wit.concertApp.models.UserModel
import org.wit.concertApp.views.ConcertView
import kotlin.math.log

class ConcertController {
    val users = UserMemStore()
    val concerts = ConcertMemStore()
    val concertView = ConcertView()
    val logger = KotlinLogging.logger {}


    init {
        logger.info { "Launching Concert App" }
        println("Concert Kotlin App")
    }

    fun start() {
        var input: Int
        do {
            input = concertView.menu()
            when (input) {
                1 -> addConcert()
                2 -> updateConcerts()
                3 -> listConcerts()
                4 -> deleteConcert()
                5 -> showConcert()
                6 -> addUser()
                7 -> updateUsers()
                8 -> listUsers()
                9 -> deleteUser()
                10 -> showUser()
                11 -> attendConcert()
                -1 -> println("Exiting App")
                -99 -> dummyData()
                else -> println("Invalid Option")
            }
        } while (input != -1)
        logger.info { "Shutting Down Concert Console App" }
    }

    fun menu(): Int {
        return concertView.menu()
    }

    fun addConcert() {
        var aConcert = ConcertModel()

        if (concertView.addConcertData(aConcert)) concerts.create(aConcert)
        else logger.info("Concert Not Added")
    }

    fun listConcerts() {
        concertView.listConcerts((concerts))
    }

    fun updateConcerts() {
        concertView.listConcerts((concerts))
        var searchId = concertView.getId()
        val aConcert = searchConcert(searchId)

        if (aConcert != null) {
            if (concertView.updateConcertData(aConcert)) {
                concerts.update((aConcert))
            }
        }
    }

    fun showConcert() {
        concertView.listConcerts((concerts))
        val aConcert = searchConcert(concertView.getId())

        if (aConcert != null) concertView.showConcert(aConcert)
        else logger.info { "No Concert for given Id" }
    }

    fun searchConcert(id: Long): ConcertModel? {
        var foundConcert = concerts.findOne(id)
        return foundConcert
    }

    fun deleteConcert() {
        concertView.listConcerts(concerts)
        var searchId = concertView.getId()
        val aConcert = searchConcert(searchId)

        if (aConcert != null) {
            concerts.delete(searchId)
            logger.info { "Concert Deleted " }
        } else {
            logger.info { "Concert Not Deleted" }
        }

    }

    fun addUser() {
        var aUser = UserModel()

        if (concertView.addUserData(aUser)) users.create(aUser)
        else logger.info("User Not Added")
    }

    fun listUsers() {
        concertView.listUsers(users)
    }

    fun updateUsers() {
        concertView.listUsers(users)
        var searchId = concertView.getId()
        val aUser = searchUser(searchId)

        if (aUser != null) {
            if (concertView.updateUserData(aUser)) {
                users.update(aUser)
                concertView.showUser(aUser)
                logger.info { "User Updated : [$aUser]" }
            } else
                logger.info("User not Updated")
        } else
            println("User Not Updated...")
    }

    fun showUser() {
        concertView.listUsers((users))
        val aUser = searchUser(concertView.getId())

        if (aUser != null) concertView.showUser(aUser)
        else logger.info { "No User for given Id" }
    }

    fun searchUser(id: Long): UserModel? {
        var foundUser = users.findOne(id)
        return foundUser
    }

    fun deleteUser() {
        concertView.listUsers(users)
        var searchId = concertView.getId()
        val aUser = searchUser(searchId)

        if (aUser != null) {
            users.delete(searchId)
            logger.info { "User Deleted " }
        } else {
            logger.info { "User Not Deleted" }
        }

    }

    fun attendConcert() {
        concertView.listUsers(users)
        var userId = concertView.getId()
        val aUser = searchUser(userId)

        concertView.listConcerts(concerts)
        var concertId = concertView.getId()
        val aConcert = searchConcert(concertId)
        if (aUser != null && aConcert != null) {
            users.attendConcert(aUser, concertId)
            logger.info { "Added to Upcoming Concerts for $aUser" }
        } else {
            logger.info { "Not Added to Upcoming Concerts" }
        }
    }

    fun dummyData() {
        concerts.create(
            (ConcertModel(
                date = "24-05-2020", headlineAct = "Foo Fighters", supportingActs = ArrayList<String>(),
                url = "www.foo.com", address = "37 Foovile"
            ))
        )
        concerts.create(
            (ConcertModel(
                date = "30-03-1993", headlineAct = "Led Zepplin", supportingActs = ArrayList<String>(),
                url = "www.zeddy.com", address = "20 Zepperoni Street"
            ))
        )
        concerts.create(
            (ConcertModel(
                date = "09-11-2013", headlineAct = "Ed Sheeran", supportingActs = ArrayList<String>(),
                url = "www.ginger.com", address = "59 Picadilly"
            ))
        )

        users.create((UserModel(firstName = "Jamie", lastName = "Sheehan")))
        users.create((UserModel(firstName = "Paddi", lastName = "Long")))
        users.create((UserModel(firstName = "Cooper", lastName = "Sheehan")))
    }
}