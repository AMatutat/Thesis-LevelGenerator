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
}
