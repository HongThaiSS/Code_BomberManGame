
package bomberman;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Fire extends MapOfBlocks {

    static CopyOnWriteArrayList<Fire> fire = new CopyOnWriteArrayList<Fire>();
    static int delay = 300;
    Thread fireAnimation;
    int direction;

    public Fire(final Position _position, int dir) {
        super(Types.BlockType.FIRE, _position);
        direction = dir;
        switch (dir) {
            case 1:
                setImage(Images.fireCenter);
                break;
            case 2:
                setImage(Images.fireDown);
                break;
            case 3:
                setImage(Images.fireLeft);
                break;
            case 4:
                setImage(Images.fireRight);
                break;
            case 5:
                setImage(Images.fireUP);
                break;
        }

        fire.add(this);
        final Fire c = this;
        fireAnimation = new Thread(new Runnable() {
            public void run() {
                //  while (timer < explodsionTimer) {
                try {
                    Thread.sleep(delay);
                    fire.remove(c);
                    breakBricks();
                    killEnemies();
                } catch (InterruptedException e) {
                    System.out.println("");
                }
       
            }
        });
        fireAnimation.start();
    }

    public static void startFire(Position pos) {
        int x = round(pos.getxPos());
        int y = round(pos.getyPos());
        Fire f = new Fire(new Position(x, y), 1);
        //right = 4
        Position right = new Position(x + 50, y);
        if (BomberMan.map.brickAtPosition(right) == 0) {
            new Fire(right, 4);
        }
        //down = 2
        Position down = new Position(x, y + 50);
        if (BomberMan.map.brickAtPosition(down) == 0) {
            new Fire(down, 2);
        }
        Position left = new Position(x - 50, y);
        if (BomberMan.map.brickAtPosition(left) == 0) {
            new Fire(left, 3);
        }
        Position up = new Position(x, y - 50);
        if (BomberMan.map.brickAtPosition(up) == 0) {
            new Fire(up, 5);
        }
    }

    public void breakBricks() {
        if (direction != 1) {
            return;
        }
        int x = getX();
        int y = getY();
        Position down = new Position(x, y + 50);
        Position right = new Position(x + 50, y);
        Position left = new Position(x - 50, y);
        Position up = new Position(x, y - 50);
        BomberMan.map.breakBrickAtPosition(up);
        BomberMan.map.breakBrickAtPosition(down);
        BomberMan.map.breakBrickAtPosition(left);
        BomberMan.map.breakBrickAtPosition(right);
        System.out.println("Break Bricks Called");
       

    }

    public void killEnemies() {
        if (direction != 1) {
            return;
        }
        ArrayList<Enemy> tobeKilled = new ArrayList<>();
        int x = this.getX();
        int y = this.getY();

        for (Enemy enemy : BomberMan.enemies) {
            Rectangle enemyBounds = enemy.getBounds(enemy.direction);
            Rectangle t = new Rectangle(x + 50, y, 50, 50);
            if (t.intersects(enemyBounds)) {
                tobeKilled.add(enemy);
            }
            t = new Rectangle(x - 50, y, 50, 50);
            if (t.intersects(enemyBounds)) {
                tobeKilled.add(enemy);
            }
            t = new Rectangle(x, y + 50, 50, 50);
            if (t.intersects(enemyBounds)) {
                tobeKilled.add(enemy);
            }
            t = new Rectangle(x, y - 50, 50, 50);
            if (t.intersects(enemyBounds)) {
                tobeKilled.add(enemy);
            }
        }
        for (Bomber player : BomberMan.players) {
            Rectangle playerBounds = player.getBounds(player.direction);
            Rectangle t = new Rectangle(x + 50, y, 50, 50);
            if (t.intersects(playerBounds)) {
                player.die();
            }
            t = new Rectangle(x - 50, y, 50, 50);
            if (t.intersects(playerBounds)) {
                player.die();
            }
            t = new Rectangle(x, y + 50, 50, 50);
            if (t.intersects(playerBounds)) {
                player.die();
            }
            t = new Rectangle(x, y - 50, 50, 50);
            if (t.intersects(playerBounds)) {
                player.die();
            }
        }

        for (final Enemy i : tobeKilled) {
            i.die();
        }
    }

    public static int round(int x) {
        for (int i = 0; i < 1000; i = i + 50) {
            if (x < i) {
                x = i - 50;
                break;
            }
        }
        return x;
    }
}