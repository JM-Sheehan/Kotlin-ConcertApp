package org.wit.concertApp.models

interface UserStore {
    fun findAll() : List<UserModel>
    fun findOne(id : Long) : UserModel?
    fun create(user : UserModel)
    fun update(user : UserModel)
    fun delete(id : Long)
    fun attendConcert(user : UserModel, concertId : Long)
}