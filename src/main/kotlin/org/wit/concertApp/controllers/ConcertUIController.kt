package org.wit.concertApp.console.controllers

import javafx.beans.property.Property
import javafx.beans.property.SimpleObjectProperty
import mu.KotlinLogging
import org.wit.concertApp.console.models.ConcertJSONStore
import org.wit.concertApp.console.models.ConcertModel
import org.wit.concertApp.console.models.UserJSONStore
import org.wit.concertApp.console.models.UserModel
import org.wit.concertApp.console.views.*
import tornadofx.*
import java.time.LocalDate

class ConcertUIController: Controller() {
    val users = UserJSONStore()
    val concerts = ConcertJSONStore()
    val logger = KotlinLogging.logger{}

    init {
        logger.info { "Launching Concert TornadoFX UI App" }
    }
    fun addUser(_firstName : String, _lastName: String){
        var aUser = UserModel(firstName =  _firstName, lastName =  _lastName)
        users.create(aUser)
        logger.info("User Added")
    }
    fun searchUser(_id: Long): UserModel {
        var foundUser = users.findOne(_id)!!
        return foundUser
    }

    fun deleteUser(_id: Long){
        val aUser = searchUser(_id)
        users.delete(aUser)
        logger.info("User Deleted")
    }
    fun updateUser(user: UserModel){
        users.update(user)
        logger.info("User Updated")
    }

    fun attendConcert(user: UserModel, _concertId: Long){
        users.attendConcert(user, _concertId)
        logger.info("Concert Added to Upcoming")
    }

    fun addConcert(_date: String, _headlineAct: String, _url: String, _address: String){
        var aConcert = ConcertModel(date = _date, headlineAct =  _headlineAct,
            url = _url, address = _address)
        concerts.create(aConcert)
        logger.info("Concert Added")
    }

    fun searchConcert(_id: Long): ConcertModel {
        var foundConcert = concerts.findOne(_id)!!
        return foundConcert
    }
    fun deleteConcert(_id: Long){
        val aConcert = searchConcert(_id)
        concerts.delete(aConcert)
        logger.info("Concert Deleted")
    }
    fun updateConcert(concert: ConcertModel){
        concerts.update(concert)
        logger.info("Concert Updated")
    }

    fun loadAddUserScreen(){
        runLater {
            find(MenuScreen::class).replaceWith(AddUserScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }
    fun loadAddConcertScreen(){
        runLater {
            find(MenuScreen::class).replaceWith(AddConcertScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }
    fun loadAttendConcertScreen(){
        runLater {
            find(MenuScreen::class).replaceWith(AttendConcertScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }
    fun loadConcertsScreen(){
        runLater {
            find(MenuScreen::class).replaceWith(ConcertsScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }
    fun loadUsersScreen(){
        runLater {
            find(MenuScreen::class).replaceWith(UsersScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }

    fun closeAddUserScreen(){
        runLater {
            find(AddUserScreen::class).replaceWith(MenuScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }
    fun closeAddConcertScreen(){
        runLater {
            find(AddConcertScreen::class).replaceWith(MenuScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }
    fun closeAttendConcertScreen(){
        runLater {
            find(AttendConcertScreen::class).replaceWith(MenuScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }
    fun closeConcertsScreen(){
        runLater {
            find(ConcertsScreen::class).replaceWith(MenuScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }
    fun closeUsersScreen(){
        runLater {
            find(UsersScreen::class).replaceWith(MenuScreen::class,
                sizeToScene = true, centerOnScreen = true)
        }
    }

}