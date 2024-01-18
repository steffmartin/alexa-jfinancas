package br.com.steffanmartins.alexajfinancassync.view;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.io.IOException;

@Component
public class TrayIconDemo {

    @Value("trayIcon.png")
    private ClassPathResource icon;
    private SystemTray tray;
    private TrayIcon trayIcon;

    @PostConstruct
    private void init() throws AWTException, IOException {
        System.setProperty("java.awt.headless", "false");

        Image image = ImageIO.read(icon.getInputStream());

        trayIcon = new TrayIcon(image, "jFinanças Alexa Sync está em execução");
        trayIcon.setImageAutoSize(true);

        tray = SystemTray.getSystemTray();
        tray.add(trayIcon);
    }

    public void displayTray() {
        trayIcon.displayMessage("jFinanças Alexa Sync",
                "Suas informações financeiras estão sendo sincronizadas com a Alexa agora.",
                MessageType.INFO);
    }

    public void closeTray() {
        tray.remove(trayIcon);
    }
}
