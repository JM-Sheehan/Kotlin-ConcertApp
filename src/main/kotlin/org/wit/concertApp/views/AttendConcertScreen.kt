package org.wit.concertApp.console.views

import javafx.geometry.Pos
import javafx.scene.layout.HBox
import org.wit.concertApp.console.controllers.ConcertUIController
import org.wit.concertApp.console.models.ConcertModel
import org.wit.concertApp.console.models.ConcertViewModel
import org.wit.concertApp.console.models.UserModel
import org.wit.concertApp.console.models.UserViewModel
import tornadofx.*
class AttendConcertScreen : View("Attend Concert View") {
    val concertUIController: ConcertUIController by inject()
    val userTableContent = concertUIController.users.findAll()
    val usersData = userTableContent.observable()
    val concertTableContent = concertUIController.concerts.findAll()
    val userModel = UserViewModel(UserModel())
    val concertData = concertTableContent.observable()
    val concertModel = ConcertViewModel(ConcertModel())

    override val root = borderpane (){
        setPrefSize(1200.0, 800.0)
    }

    init{
        with(root) {
            bottom{
                hbox() {
                    setPrefSize(1200.0, 100.0)
                    alignment= Pos.CENTER
                    spacing =20.0

                    button("Add to Upcoming") {
                        isDefaultButton = true
                        setPrefSize(100.0, 20.0)
                        action {
                            runAsyncWithProgress {
                                attend()
                            }
                        }
                    }
                    button("Close") {
                        isDefaultButton = true
                        setPrefSize( 100.0, 20.0)
                        action {
                            runAsyncWithProgress {
                                concertUIController.closeAttendConcertScreen()
                            }
                        }
                    }
                }
            }
            left {
                tableview(usersData) {
                    setPrefSize(360.0, 600.0)
                    readonlyColumn("ID", UserModel::userId)
                    readonlyColumn("First Name", UserModel::firstName)
                    readonlyColumn("Last Name", UserModel::lastName)

                    userModel.rebindOnChange(this) { selectedUser ->
                        item = selectedUser ?: UserModel()
                    }
                }

            }
            right {
                tableview(concertData) {
                    setPrefSize(650.0, 600.0)
                    readonlyColumn("ID", ConcertModel::concertId)
                    readonlyColumn("Headliner", ConcertModel::headlineAct)
                    readonlyColumn("Date", ConcertModel::date)
                    readonlyColumn("Address", ConcertModel::address)
                    readonlyColumn("url", ConcertModel::url)

                    concertModel.rebindOnChange(this) { selectedConcert ->
                        item = selectedConcert ?: ConcertModel()
                    }
                }
            }

        }
    }

    private fun attend(){
        userModel.commit()
        concertModel.commit()
        val user = userModel.item
        val concert = concertModel.item

        concertUIController.users.attendConcert(user, concert.concertId)
    }
}
