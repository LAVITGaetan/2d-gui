import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedText {
    private String fullText;
    private int currentIndex = 0;
    private String displayedText = "";
    private Timer timer;

    public AnimatedText(String text, int delayMs) {
        this.fullText = text;
        timer = new Timer(delayMs, e -> {
            if (currentIndex < fullText.length()) {
                currentIndex++;
                displayedText = fullText.substring(0, currentIndex);
            } else {
                timer.stop();
            }
        });
    }

    public void start() {
        currentIndex = 0;
        displayedText = "";
        timer.start();
    }

    public String getDisplayedText() {
        return displayedText;
    }
}
