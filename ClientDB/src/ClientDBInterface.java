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
public interface ClientDBInterface extends Remote{//το interface του χρηστη για εκτυπωση μεσω του server
    public void print(String s)throws RemoteException;    
}
