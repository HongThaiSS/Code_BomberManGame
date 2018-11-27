
package bomberman;


import bomberman.Types.*;


public class MoveExecutor{
    
    
    public static void executeMove( Bomber player, Move move){
        int x = player.getPosition().getxPos();
        int y = player.getPosition().getyPos();

        switch(player.direction)
        {
            case UP:
                y-=player.speed;
                break;
            case DOWN:
                y+=player.speed;
                break;
            case LEFT:
                x-=player.speed;
                break;
            case RIGHT:
                x+=player.speed;
                break;
        }
        x+=player.dx;
        y+=player.dy;
        player.setPosition(new Position(x, y));
    }
    
    
}