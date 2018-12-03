
package bomberman;

import bomberman.Types.BlockType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BomberMan {

    static CopyOnWriteArrayList<Bomber> players = new CopyOnWriteArrayList<Bomber>();
    static CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<Enemy>();
 //   static ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    static final int SIZE = 15;
    static int height;
    static int width;
    static Map map;
    static MapAction frame;
  // JFrame menu;
    static int[][] intMap = new int[][]{
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 0, 0, 2, 0, 0, 0, 2, 0, 0, 0, 2, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 2, 0, 1, 0, 1},
        {1, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 2, 0, 2, 1},
        {1, 2, 0, 0, 2, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1},
        {1, 0, 0, 2, 0, 2, 0, 2, 0, 2, 0, 2, 0, 0, 1},
        {1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 2, 2, 1},
        {1, 0, 0, 1, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 1},
        {1, 0, 1, 0, 2, 0, 1, 0, 0, 2, 0, 0, 1, 0, 1},
        {1, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 2, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1, 2, 1},
        {1, 2, 0, 1, 0, 2, 0, 2, 0, 0, 2, 0, 0, 0, 1},
        {1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 1},
        {1, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public static void main(String args[]) {

 JFrame frame = new JFrame("Bomber Man");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JPanel glass = (JPanel) frame.getGlassPane();

        glass.setVisible(true);
        panel.add(new JLabel(new ImageIcon("images/menu.jpg")));

        JButton b1 = new JButton("Start Game");
        JButton b2 = new JButton("High Scores");
        JButton b3 = new JButton("Exit");

        glass.add(b1);
        glass.add(b2);
        glass.add(b3);
        b1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Starting Game");
               
            }
        });

        b2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("High Scores");
            }
        });

        b3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.out.println("Exit");
            }
        });


        frame.add(panel);

        frame.setVisible(true);
    
        Sounds soundBase = new Sounds("Battle.wav");
        soundBase.play(Clip.LOOP_CONTINUOUSLY);
        new BomberMan().startGame();
        //Thread.sleep(2000);
    }
    
    public void startGame() {
        System.out.println("Starting Game");
        map = new Map(intMap, SIZE);
        addEnemies();
        addPlayers(1);
        frame = new MapAction("BomberManGame by Thai", map);

        for (int ii = 0; ii < intMap.length; ii++) {
            for (int jj = 0; jj < intMap.length; jj++) {
                if (intMap[ii][jj] == 1) {
                    int xxx = jj*50;
                    int yyy = ii*50;
                    System.out.println(xxx+","+yyy);
                }
            }
        }
    }

    public void addEnemies() {
        
            enemies.add(new Enemy(new Position(450 + 5, 100 + 10)));
            enemies.add(new Enemy(new Position(600 + 5, 550 + 10)));
            enemies.add(new Enemy(new Position(100 + 5, 350 + 10)));
            enemies.add(new Enemy(new Position(550 + 5, 500 + 10)));
            enemies.add(new Enemy(new Position(350 + 5, 800 + 10)));
            
        
    }

    public void addPlayers(int n) {
        if (n >= 1) {
            players.add(new Bomber(new Position(60, 30)));
        }
        if (n >= 2) {
            players.add(new Bomber(new Position(660, 630)));
        }
    }

    public static void saveGame() {
        ArrayList saveData = new ArrayList();
        ArrayList<Position> enemyPositions = new ArrayList<Position>();
        ArrayList<Position> playerPositions = new ArrayList<Position>();

        for (Enemy i : enemies) {
            enemyPositions.add(i.getPosition());
        }
        for (Bomber j : players) {
            playerPositions.add(j.getPosition());
        }
        saveData.add(enemyPositions);
        saveData.add(playerPositions);
        saveData.add(intMap);
        try {
            FileOutputStream fileOut1 = new FileOutputStream("gameSaveData.pos");
            ObjectOutputStream out1 = new ObjectOutputStream(fileOut1);
            out1.writeObject(saveData);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void loadGame() {
        ArrayList saveData = null;
        try {
            FileInputStream fileIn = new FileInputStream("gameSaveData.pos");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            saveData = (ArrayList) in.readObject();
        } catch (Exception i) {
            i.printStackTrace();
        }

        ArrayList<Position> enemyPositions = (ArrayList) saveData.get(0);
        ArrayList<Position> playerPositions = (ArrayList) saveData.get(1);

        CopyOnWriteArrayList<Enemy> newEnemies = new CopyOnWriteArrayList<Enemy>();
        CopyOnWriteArrayList<Bomber> newPlayers = new CopyOnWriteArrayList<Bomber>();
        
        for (Position i : enemyPositions) {
            newEnemies.add(new Enemy(i));
        }
        enemies = newEnemies;

        for (Position i : playerPositions) {
            newPlayers.add(new Bomber(i));
        }
        players = newPlayers;
        intMap = (int[][]) saveData.get(2);
        map = new Map(intMap, SIZE);
        frame.stopTimers();
        frame.dispose();
        frame = new MapAction("BombermanGame by Thai", map);
    }
}