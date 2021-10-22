package org.wit.concertApp.console.models

interface ConcertStore {
    fun findAll() : List<ConcertModel>
    fun findOne(id : Long) : ConcertModel?
    fun create(concert : ConcertModel)
    fun update(concert : ConcertModel)
    fun delete(concert : ConcertModel)
}