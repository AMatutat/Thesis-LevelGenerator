package interfaces;
import interfaces.IMonster;
import interfaces.IItem;
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
public boolean setMonsterOnSurface(IMonster monster);
/**
 * 
 * @param item welches platziert werden soll
 * @return ob Platzierung erfolgreich war
 */
public boolean setItemOnSurface(IItem item);
}
