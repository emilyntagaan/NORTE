import java.awt.*;
import java.awt.event.KeyEvent;

public class keyTest {
    public static void main(String[] args) throws Exception {
        Robot robot = new Robot();
        System.out.println("Press '1', '2', 'Tab', or 'Enter'. Press 'Q' to quit.");

        while (true) {
            if (isKeyPressed(KeyEvent.VK_1)) System.out.println("You pressed: 1");
            if (isKeyPressed(KeyEvent.VK_2)) System.out.println("You pressed: 2");
            if (isKeyPressed(KeyEvent.VK_TAB)) System.out.println("You pressed: Tab");
            if (isKeyPressed(KeyEvent.VK_ENTER)) System.out.println("You pressed: Enter");
            if (isKeyPressed(KeyEvent.VK_Q)) {
                System.out.println("Exiting...");
                break;
            }
            Thread.sleep(100);
        }
    }

    public static boolean isKeyPressed(int keyCode) {
        return Toolkit.getDefaultToolkit().getLockingKeyState(keyCode);
    }
}
