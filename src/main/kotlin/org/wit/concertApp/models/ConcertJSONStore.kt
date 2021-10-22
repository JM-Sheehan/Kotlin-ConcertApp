package org.wit.concertApp.console.models

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import org.wit.concertApp.console.helpers.*
import java.util.*

private val logger = KotlinLogging.logger {}

private val CONCERT_JSON_FILE = "concerts.json"
val concertGsonBuilder = GsonBuilder().setPrettyPrinting().create()
val concertListType = object : TypeToken<java.util.ArrayList<ConcertModel>>() {}.type

fun generateRandomConcertId(): Long {
    return Random().nextLong()
}

class ConcertJSONStore : ConcertStore{
    var concerts = mutableListOf<ConcertModel>()

    init {
        if(exists(CONCERT_JSON_FILE))deserialize()
    }
    override fun findAll(): MutableList<ConcertModel> {
        return concerts
    }

    override fun findOne(id: Long) : ConcertModel? {
        var foundUsers: ConcertModel? = concerts.find { c -> c.concertId == id }
        return foundUsers
    }

    override fun create(concert: ConcertModel) {
        concert.concertId = generateRandomConcertId()
        concerts.add(concert)
        logAll()
        serialize()
    }

    override fun update(concert: ConcertModel) {
        var foundConcert = findOne(concert.concertId!!)
        if (foundConcert != null) {
            foundConcert.date = concert.date
            foundConcert.headlineAct = concert.headlineAct
            foundConcert.url = concert.url
            foundConcert.address = concert.address
        }
        serialize()
    }

    override fun delete(concert: ConcertModel){
        concerts.remove(concert)
        serialize()
    }

    internal fun logAll() {
        concerts.forEach { logger.info("${it}") }
    }

    private fun serialize() {
        val jsonString = concertGsonBuilder.toJson(concerts, concertListType)
        write(CONCERT_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(CONCERT_JSON_FILE)
        concerts = Gson().fromJson(jsonString, concertListType)
    }
}