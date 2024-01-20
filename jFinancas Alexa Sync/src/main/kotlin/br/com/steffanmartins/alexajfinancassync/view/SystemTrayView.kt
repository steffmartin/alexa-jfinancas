package br.com.steffanmartins.alexajfinancassync.view

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.awt.Image
import java.awt.SystemTray
import java.awt.TrayIcon
import java.awt.TrayIcon.MessageType
import java.io.Closeable
import javax.imageio.ImageIO

@Component
class SystemTrayView(
    @Value("trayIcon.png") private val icon: ClassPathResource
) : Closeable {

    private lateinit var tray: SystemTray
    private lateinit var trayIcon: TrayIcon

    init {
        System.setProperty("java.awt.headless", "false")

        val image: Image = ImageIO.read(icon.getInputStream())

        trayIcon = TrayIcon(image, "jFinanças Alexa Sync está em execução")
        trayIcon.setImageAutoSize(true)

        tray = SystemTray.getSystemTray()
        tray.add(trayIcon)
    }

    suspend fun showInfo(msg: String) = trayIcon.displayMessage("jFinanças Alexa Sync", msg, MessageType.INFO)

    suspend fun showError(msg: String) = trayIcon.displayMessage("jFinanças Alexa Sync", msg, MessageType.ERROR)

    override fun close() = tray.remove(trayIcon)
}