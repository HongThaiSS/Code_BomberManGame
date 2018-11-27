
package bomberman;


import bomberman.Types.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class MapOfBlocks {

    private BlockType blockType;
    private Position position;
    private Image image;

    public MapOfBlocks(BlockType _blockType, Position _position) {

        blockType = _blockType;
        position = _position;
        switch (_blockType) {
            case BREAKABLE:
                break;
            case UNBREKABLE:
                break;
            case EMPTY:
                break;
            case BOMB:
                image = Images.BOMB_BIG;
                break;
            case FIRE:
                image = Images.fireCenter;
                break;
            case PLAYER:
                image = new ImageIcon("images/bm.gif").getImage();
                break;
        }
    }

    public BlockType getBlockType() {
        return blockType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        position = newPosition;
    }

   
    public Image getImage() {
        return image;
    }
    
    public int getX(){
        return this.getPosition().getxPos();
    }
    public int getY(){
        return this.getPosition().getyPos();
    }
    
    public void setImage(Image im){
        image = im;
    }
    
    ///bomb
        public void setX(int x){
        this.getPosition().setXPos(x);
    }
    public void setY(int y){
        this.getPosition().setYPos(y);
    }
}