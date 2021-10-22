import mu.KotlinLogging
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.wit.concertApp.console.controllers.ConcertUIController
import org.wit.concertApp.console.helpers.write
import org.wit.concertApp.console.models.*


class ModelTests{
    val logger = KotlinLogging.logger{}
    var concertStore = ConcertJSONStore()

    var userStore = UserJSONStore()

    var concertOne = ConcertModel(date = "2020-04-23", headlineAct = "The Beatles", url = "www.Beatles.net",
        address = "100 Beatles Road")
    var concertTwo = ConcertModel(date = "2000-11-13", headlineAct = "The Rolling Stones", url = "www.Rolling.com",
        address = "23 Rolling Avenue")

    var userOne = UserModel(firstName = "Paul", lastName = "Mccartney")
    var userTwo = UserModel(firstName = "Mick", lastName = "Jagger")

    @BeforeEach
    fun setUp(){
        CONCERT_JSON_FILE = "testConcerts.json"
        USERS_JSON_FILE = "userConcerts.json"
        concertStore.concerts.clear()
        userStore.users.clear()

        val jsonConcertString = concertGsonBuilder.toJson(concertStore.concerts, concertListType)
        write(CONCERT_JSON_FILE, jsonConcertString)

        val jsonUserString = usersGsonBuilder.toJson(userStore.users, usersListType)
        write(USERS_JSON_FILE, jsonUserString)

        concertStore = ConcertJSONStore()
        userStore = UserJSONStore()


    }

    @Test fun addConcerts(){
        var concerts = concertStore.findAll()
        assertTrue(concerts.size == 0)

        concertStore.create(concertOne)
        concerts = concertStore.findAll()

        assertTrue(concerts.size == 1)
        var lastConcert = concerts.get(concerts.size - 1)
        assertTrue(
            lastConcert.headlineAct.equals(concertOne.headlineAct) &&
                    lastConcert.date.equals(concertOne.date) &&
                    lastConcert.address.equals(concertOne.address) &&
                    lastConcert.url.equals(concertOne.url)
        )

        concertStore.create(concertTwo)
        concerts = concertStore.findAll()
        assertTrue(concerts.size == 2)
        lastConcert = concerts.get(concerts.size - 1)
        assertTrue(
            lastConcert.headlineAct.equals(concertTwo.headlineAct) &&
                    lastConcert.date.equals(concertTwo.date) &&
                    lastConcert.address.equals(concertTwo.address) &&
                    lastConcert.url.equals(concertTwo.url)
        )
    }

    @Test fun updateConcert(){
        concertStore.create(concertOne)
        concertStore.create(concertTwo)

        var firstConcert = concertStore.concerts.get(0)
        var secondConcert = concertStore.concerts.get(1)

        val newHeadlineAct = "The Kinks"
        val newUrl = "www.Kinks.gov"
        val newAddress = "37 Sunnyvale"

        firstConcert.headlineAct =  newHeadlineAct
        firstConcert.url = newUrl
        firstConcert.address = newAddress

        concertStore.update(firstConcert)

        val changedConcert = concertStore.concerts.get(0)

        assertTrue(
            changedConcert.headlineAct.equals(newHeadlineAct) &&
                    changedConcert.address.equals(newAddress) &&
                    changedConcert.url.equals(newUrl)
        )

        concertStore.logAll()

    }

    @Test fun deleteConcert(){
        concertStore.create(concertOne)
        concertStore.create(concertTwo)
        var concerts = concertStore.findAll()
        var lastConcert = concerts.get(concerts.size - 1)

        concertStore.delete(lastConcert)
        assertTrue(concertStore.concerts.size == 1)

        lastConcert = concerts.get(concerts.size - 1)

        concertStore.delete(lastConcert)
        assertTrue(concertStore.concerts.size == 0)
    }

    @Test fun addUser(){
        var users = userStore.findAll()
        assertTrue(users.size == 0)

        userStore.create(userOne)
        users = userStore.findAll()

        assertTrue(users.size == 1)
        var lastUser = users.get(users.size - 1)
        assertTrue(
            lastUser.firstName == userOne.firstName &&
                    lastUser.lastName == userOne.lastName
        )

        userStore.create(userTwo)
        users = userStore.findAll()
        assertTrue(users.size == 2)
        lastUser = users.get(users.size - 1)
        assertTrue(
            lastUser.firstName == userTwo.firstName &&
                    lastUser.lastName == userTwo.lastName
        )
    }
    @Test fun deleteUser(){
        userStore.create(userOne)
        userStore.create(userTwo)
        var users = userStore.findAll()
        var lastUser = users.get(users.size - 1)

        userStore.delete(lastUser)
        assertTrue(userStore.users.size == 1)

        lastUser = users.get(users.size - 1)

        userStore.delete(lastUser)
        assertTrue(userStore.users.size == 0)
    }

    @Test fun updateUser(){
        userStore.create(userOne)
        userStore.create(userTwo)

        var firstUser = userStore.users.get(0)
        var secondUser =  userStore.users.get(1)

        val newFirstName = "Test"
        val newLastName = "Subject"

        firstUser.firstName =  newFirstName
        firstUser.lastName = newLastName

        userStore.update(firstUser)

        val changedUser = userStore.users.get(0)

        assertTrue(
            changedUser.firstName.equals(newFirstName) &&
                    changedUser.lastName.equals(newLastName)
        )

        userStore.logAll()

    }

    @Test fun attendConcert(){
        concertStore.create(concertOne)
        concertStore.create(concertTwo)

        userStore.create(userOne)
        userStore.create(userTwo)

        val concertToAttend = concertStore.concerts.get(0)
        var userAttending = userStore.users.get(0)

        var upcoming : ArrayList<Long> = ArrayList<Long>()

        assertTrue(userAttending.upcomingConcerts.equals(upcoming))

        upcoming.add(concertToAttend.concertId)

        logger.info { userAttending.upcomingConcerts }
        userStore.attendConcert(userAttending, concertToAttend.concertId)

        userAttending = userStore.users.get(0)
        assertTrue(userAttending.upcomingConcerts.equals(upcoming))
        assertEquals(concertToAttend.concertId, userAttending.upcomingConcerts.get(0))
        logger.info { userAttending.upcomingConcerts }
    }
}