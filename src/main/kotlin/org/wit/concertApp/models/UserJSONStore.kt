package org.wit.concertApp.console.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import org.wit.concertApp.console.helpers.*
import java.util.*

private val logger = KotlinLogging.logger {}

private val USERS_JSON_FILE = "users.json"
val usersGsonBuilder = GsonBuilder().setPrettyPrinting().create()
val usersListType = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore : UserStore{
    var users = mutableListOf<UserModel>()

    init {
        if(exists(USERS_JSON_FILE))deserialize()

    }
    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun findOne(id: Long) : UserModel? {
        var foundUsers: UserModel? = users.find { u -> u.userId == id }
        return foundUsers
    }

    override fun create(user: UserModel) {
        user.userId = generateRandomUserId()
        users.add(user)
        logAll()
        serialize()
    }

    override fun update(user: UserModel) {
        var foundUser = findOne(user.userId!!)
        if (foundUser != null) {
            foundUser.firstName = user.firstName
            foundUser.lastName = user.lastName
        }
        serialize()
    }

    override fun delete(user: UserModel){
        users.remove(user)
        serialize()
    }

    override fun attendConcert(user : UserModel, concertId: Long) {
        var foundUser = findOne(user.userId!!)
        if(foundUser != null){
            foundUser.upcomingConcerts.add(concertId)
        }
        serialize()
    }
    internal fun logAll() {
        users.forEach { logger.info("${it}") }
    }

    private fun serialize() {
        val jsonString = usersGsonBuilder.toJson(users, usersListType)
        write(USERS_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(USERS_JSON_FILE)
        users = Gson().fromJson(jsonString, usersListType)
    }
}