/*
 * Παναγιώτης Κεραμίδης
    321/2016067
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 *
 * @author panos
 */
public interface ServerDBInterface extends Remote{//oι συναρτήσεις λειτουργιών του server
    public void add(String title, String genre, String singer, int duration, ClientDBInterface c)throws RemoteException;
    
    public void searchTitle(String title, ClientDBInterface c)throws RemoteException;
    
    public void searchSinger(String singer, ClientDBInterface c)throws RemoteException;
    
    public void rate(String username, String title, int rating, ClientDBInterface c)throws RemoteException;
    
    public void searchRating(int rating, ClientDBInterface c)throws RemoteException;
}
