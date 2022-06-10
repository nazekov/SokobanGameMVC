import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Controller implements KeyListener, ActionListener, MouseListener {
    private Model model;

    public Controller(Viewer viewer) {
        model = new Model(viewer);
    }

    public Model getModel() {
        return model;
    }

    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.VK_UP:
                model.move("Up");
                break;
            case KeyEvent.VK_DOWN:
                model.move("Down");
                break;
            case KeyEvent.VK_RIGHT:
                model.move("Right");
                break;
            case KeyEvent.VK_LEFT:
                model.move("Left");
                break;
            default:
                return;
        }
    }

    public void keyReleased(KeyEvent e) {
    }


    public void keyTyped(KeyEvent e) {
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        model.doAction(command);
    }



    public void mousePressed(MouseEvent event) {
        int x = event.getX();
        int y = event.getY();
        model.doAction(x, y);
    }

    public void mouseClicked(MouseEvent event) {
    }


    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }


    public void mouseExited(MouseEvent event) {
    }
}
