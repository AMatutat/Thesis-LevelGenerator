package interfaces;

import myGame.Item;
import myGame.Monster;
/**
 * Muss von allen Surfaces implementiert werden
 * @author André Matutat
 *
 */
public interface ISurface {
/**
 * 
 * @return Pfad zur Textur des Surface
 */
public String getTexture();


/**
 * 
 * @return ob auf dem Feld etwas platziert ist (Monster,Item)
 */
public boolean isFree();

/**
 * 
 * @param monster welches platziert werden soll
 * @return ob Platzierung erfolgreich war
 */
public boolean setMonsterOnSurface(Monster monster);
/**
 * 
 * @param item welches platziert werden soll
 * @return ob Platzierung erfolgreich war
 */
public boolean setItemOnSurface(Item item);

public int getX();
public int getY();

}
