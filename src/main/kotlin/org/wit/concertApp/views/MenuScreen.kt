package org.wit.concertApp.console.views

import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.FontWeight
import org.wit.concertApp.console.controllers.ConcertUIController
import tornadofx.*

class MenuScreen : View("Concert App Menu Screen") {
    val concertUIController: ConcertUIController by inject()
    override val root = vbox {
        setPrefSize(600.0, 500.0)
        alignment = Pos.CENTER
        spacing = 20.0

        label(title){
            font = Font(30.0)
            textFill = Color.ORANGE
            style{
                fontWeight = FontWeight.EXTRA_BOLD
            }
        }


        button ("Add User"){
            isDefaultButton = true
            setPrefSize(400.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.loadAddUserScreen()
                }
            }
        }

        button ("Add Concert"){
            isDefaultButton = true
            setPrefSize(400.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.loadAddConcertScreen()
                }
            }
        }

        button ("Edit Existing Concerts"){
            isDefaultButton = true
            setPrefSize(400.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.loadConcertsScreen()
                }
            }
        }

        button ("Edit Existing Users"){
            isDefaultButton = true
            setPrefSize(400.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.loadUsersScreen()
                }
            }
        }

        button ("Attend Concert"){
            isDefaultButton = true
            setPrefSize(400.0, 10.0)
            action {
                runAsyncWithProgress {
                    concertUIController.loadAttendConcertScreen()
                }
            }
        }

        button ("Exit"){
            isDefaultButton = true
            setPrefSize(400.0, 10.0)
            action {
                runAsyncWithProgress {
                    Platform.exit();
                    System.exit(0);
                }
            }
        }
    }
}
