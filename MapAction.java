/*
 * This class must have all the gui implementation of the game.
 */
package bomberman;

import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MapAction extends JFrame {
    Timer timer;
    Timer timer2;
    static JPanel panel;
    Bomb bomb;
    static Map map;

    public MapAction(String title, Map map) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Creating Panel
        this.map = map;
        GridLayout gL = new GridLayout(15, 15);
        panel = new JPanel(gL) {
            public void paint(Graphics g) {
                super.paint(g);
                panelPaint(g);
            }
        };


        for (int i = 0; i < map.size; i++) {
            for (int j = 0; j < map.size; j++) {
                panel.add(map.getLabel(i, j));
            }
        }


        panel.setDoubleBuffered(true);
        setFocusable(true);

        this.add(panel);
        this.getContentPane().add(panel, BorderLayout.NORTH);
        this.getContentPane().add(new SaveLoad(), BorderLayout.SOUTH);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.repaint();
        this.addKeyListener(new TAdapter());
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
   
        timer = new Timer(5, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //    if(BomberMan.players.isEmpty()) dispose();
                for (Bomber i : BomberMan.players) {
                    MoveIt.Move(i);
                }
                for (Enemy i : BomberMan.enemies) {
                    i.move();
                }
                panel.repaint();
            }
        });

        timer2 = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Bomber i : BomberMan.players) {
                        i.cycle();
                }
            }
        }
        );
        
        startTimers();
    }

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if (key != KeyEvent.VK_SPACE) {
                BomberMan.players.get(0).Stop();
            }
        }

        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    BomberMan.players.get(0).direction = Types.Move.LEFT;
                    break;
                case KeyEvent.VK_RIGHT:
                    BomberMan.players.get(0).direction = Types.Move.RIGHT;
                    break;
                case KeyEvent.VK_UP:
                    BomberMan.players.get(0).direction = Types.Move.UP;
                    break;
                case KeyEvent.VK_DOWN:
                    BomberMan.players.get(0).direction = Types.Move.DOWN;
                    break;
                case KeyEvent.VK_SPACE:
                    System.out.println("SPACE");
                    BomberMan.players.get(0).plantBomb();
                    break;
                case KeyEvent.VK_B:
                    BomberMan.map.map[0][0].setIcon(Images.pathIcon);
                    break;
            }
        }
    }

    public void dialogue(){
        JOptionPane.showMessageDialog(this,"Bye Bye","Game Over",JOptionPane.ERROR_MESSAGE);
    }
    public void panelPaint(Graphics g) {
        if(BomberMan.players.size()==0){
            timer.stop();
            timer2.stop();
            dispose();
            return;
        }
        Graphics2D g2d = (Graphics2D) g;
        //Vẽ Enemies
        for (Enemy enemy : BomberMan.enemies) {
            g2d.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), null);
        }
        //Vẽ Bombers và Bombs
        for (Bomber player : BomberMan.players) {
            g2d.drawImage(player.getImage(), player.getX(), player.getY(), null);
            for(Bomb bomb : player.bombs){
                g2d.drawImage(bomb.getImage(), bomb.getX(), bomb.getY(), panel);
            }
        }
        
        

        g.drawRect(BomberMan.players.get(0).getX(), BomberMan.players.get(0).getY() + Images.playerHeight - 30, Images.playerWidth, 30);
      

        for (Fire f : Fire.fire) {
            g2d.drawImage(f.getImage(), f.getX(), f.getY(), null);
        }
        //Draw PowerUps
        for(PowerUp power:PowerUp.allPowerUps){
            g2d.drawImage(power.getImage(), power.getX(), power.getY(), null);
        }
        Toolkit.getDefaultToolkit().sync();
        g.dispose();

    }
    
    public void stopTimers(){
        timer.stop();
        timer2.stop();
    }
    
    public void startTimers(){
        timer.start();
        timer2.start();
    }
}