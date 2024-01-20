package br.com.steffanmartins.alexajfinancassync.view

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.awt.Image
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.TrayIcon.MessageType
import java.awt.TrayIcon.MessageType.INFO
import javax.imageio.ImageIO

@Component
class SystemTrayView(
    @Value("trayIcon.png") private val icon: ClassPathResource
) {

    private lateinit var tray: SystemTray
    private lateinit var trayIcon: TrayIcon

    init {
        System.setProperty("java.awt.headless", "false")
        val image: Image = ImageIO.read(icon.getInputStream())
        trayIcon = TrayIcon(image, "jFinanças Alexa Sync").apply { isImageAutoSize = true }
        tray = SystemTray.getSystemTray().apply { add(trayIcon) }
    }

    fun notificar(msg: String, level: MessageType = INFO) = trayIcon.displayMessage(trayIcon.toolTip, msg, level)

    fun encerrar() = tray.remove(trayIcon)
}
