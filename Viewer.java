import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Viewer  {
    private Canvas canvas;
    private JFrame frame;

    public Viewer() {
        Controller controller = new Controller(this);
        Model model = controller.getModel();
        canvas = new Canvas(model);
        JMenuBar menuBar = getMenuBar(controller);

        frame = new JFrame("Sokoban Game MVC Pattern");
        frame.setSize(1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setJMenuBar(menuBar);
        frame.add("Center", canvas);
        frame.setVisible(true);
        canvas.addMouseListener(controller);
        frame.addKeyListener(controller);
    }

    private JMenuBar getMenuBar(Controller controller) {
        JMenu levelMenu = getLevelMenu(controller);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(levelMenu);
        return menuBar;
    }

    private JMenu getLevelMenu(Controller controller) {
        JMenuItem level1 = new JMenuItem("Level-1");
        level1.addActionListener(controller);
        level1.setActionCommand("level_1");
        JMenuItem level2 = new JMenuItem("Level-2");
        level2.addActionListener(controller);
        level2.setActionCommand("level_2");
        JMenuItem level3 = new JMenuItem("Level-3");
        level3.addActionListener(controller);
        level3.setActionCommand("level_3");
        JMenuItem level4 = new JMenuItem("Level-4");
        level4.addActionListener(controller);
        level4.setActionCommand("level_4");
        JMenuItem level5 = new JMenuItem("Level-5");
        level5.addActionListener(controller);
        level5.setActionCommand("level_5");
        JMenuItem level6 = new JMenuItem("Level-6");
        level6.addActionListener(controller);
        level6.setActionCommand("level_6");
        JMenuItem level7 = new JMenuItem("Level-7");
        level7.addActionListener(controller);
        level7.setActionCommand("level_7");
        JMenuItem level8 = new JMenuItem("Level-8");
        level8.addActionListener(controller);
        level8.setActionCommand("level_8");
        JMenuItem level9 = new JMenuItem("Level-9");
        level9.addActionListener(controller);
        level9.setActionCommand("level_9");

        JMenu levelMenu = new JMenu("Levels");
        levelMenu.add(level1);
        levelMenu.add(level2);
        levelMenu.add(level3);
        levelMenu.add(level4);
        levelMenu.add(level5);
        levelMenu.add(level6);
        levelMenu.add(level7);
        levelMenu.add(level8);
        levelMenu.add(level9);

        return levelMenu;
    }

    public void update() {
        canvas.repaint();
    }

    public void showMessageDialog(String message) {
        javax.swing.JOptionPane.showMessageDialog(frame, message);
    }
}
