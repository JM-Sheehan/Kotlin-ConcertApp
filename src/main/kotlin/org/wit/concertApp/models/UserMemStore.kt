package org.wit.concertApp.console.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
var lastUserId = 0L

internal fun getUserId(): Long {
    lastUserId ++
    return lastUserId
}

class UserMemStore : UserStore {

    val users = ArrayList<UserModel>()

    override fun findAll(): List<UserModel> {
        return users
    }

    override fun findOne(id: Long) : UserModel? {
        var foundUsers: UserModel? = users.find { u -> u.userId == id }
        return foundUsers
    }

    override fun create(user: UserModel) {
        user.userId = getUserId()
        users.add(user)
        logAll()
    }

    override fun update(user: UserModel) {
        var foundUser = findOne(user.userId!!)
        if (foundUser != null) {
            foundUser.firstName = user.firstName
            foundUser.lastName = user.lastName
        }
    }

    override fun delete(user : UserModel){
        users.remove(user)
    }

    override fun attendConcert(user : UserModel, concertId: Long) {
        var foundUser = findOne(user.userId!!)
        if(foundUser != null){
            foundUser.upcomingConcerts.add(concertId)
        }
    }
    internal fun logAll() {
        users.forEach { logger.info("${it}") }
    }
}