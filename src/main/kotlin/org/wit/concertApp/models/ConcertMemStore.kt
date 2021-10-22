package org.wit.concertApp.models

import mu.KotlinLogging

private val logger = KotlinLogging.logger {}
var lastConcertId = 0L

internal fun getConcertId(): Long {
    lastConcertId ++
    return lastConcertId
}

class ConcertMemStore : ConcertStore {

    val concerts = ArrayList<ConcertModel>()

    override fun findAll(): List<ConcertModel> {
        return concerts
    }

    override fun findOne(id: Long) : ConcertModel? {
        var foundUsers: ConcertModel? = concerts.find { c -> c.concertId == id }
        return foundUsers
    }

    override fun create(concert: ConcertModel) {
        concert.concertId = getConcertId()
        concerts.add(concert)
        logAll()
    }

    override fun update(concert: ConcertModel) {
        var foundConcert = findOne(concert.concertId!!)
        if (foundConcert != null) {
            foundConcert.date = concert.date
            foundConcert.headlineAct = concert.headlineAct
            foundConcert.supportingActs = concert.supportingActs
            foundConcert.url = concert.url
            foundConcert.address = concert.address
        }
    }

    override fun delete(id: Long){
        concerts.removeAll({it.concertId == id})
    }

    internal fun logAll() {
        concerts.forEach { logger.info("${it}") }
    }
}