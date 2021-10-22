package org.wit.concertApp.console.views

import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import org.wit.concertApp.console.controllers.ConcertUIController
import tornadofx.*

class AddUserScreen : View("My View") {
    val model = ViewModel()
    val _firstName = model.bind { SimpleStringProperty() }
    val _lastName = model.bind {SimpleStringProperty()}
    val concertUIController: ConcertUIController by inject()

    override val root = vbox {
        setPrefSize(400.0, 200.0)
        alignment = Pos.CENTER
        spacing = 20.0

        hbox {
            alignment = Pos.CENTER
            spacing = 40.0
            style{
                fontWeight = FontWeight.EXTRA_BOLD
            }
            label("First Name")

            textfield(_firstName).required()
        }
        hbox {
            alignment = Pos.CENTER
            spacing = 40.0
            style{
                fontWeight = FontWeight.EXTRA_BOLD
            }
            label("Last Name")
            textfield(_lastName).required()
        }

        button("Add User") {
            enableWhen(model.valid)
            isDefaultButton = true
            setPrefSize(200.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.addUser(_firstName.value.toString(),
                        _lastName.value.toString())
                }
            }
        }
        button("Close") {
            isDefaultButton = true
            setPrefSize(200.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.closeAddUserScreen()
                }
            }
        }
    }

    override fun onDock() {
        _firstName.value = ""
        _lastName.value = ""
        model.clearDecorators()
    }
}
