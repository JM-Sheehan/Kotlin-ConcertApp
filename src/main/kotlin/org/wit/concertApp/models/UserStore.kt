package org.wit.concertApp.console.models

interface UserStore {
    fun findAll() : List<UserModel>
    fun findOne(id : Long) : UserModel?
    fun create(user : UserModel)
    fun update(user : UserModel)
    fun delete(user : UserModel)
    fun attendConcert(user : UserModel, concertId : Long)
}