/*
 * Παναγιώτης Κεραμίδης
    321/2016067
 */

import java.util.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 *
 * @author panos
 */
public class ClientDB extends UnicastRemoteObject implements ClientDBInterface{
    
    private static ServerDBInterface server;        //ενα interface server για υλοποίηση των συναρτήσεων
    private static ClientDB client;             //ενα αντικειμενο τυπου client
            
    public ClientDB() throws RemoteException, NotBoundException, MalformedURLException {
        super();        //constructor του πατέρα UnicastRemoteObject
        server = (ServerDBInterface) Naming.lookup("//localhost/server");    //σύνδεση με τον first server
    }
    
     @Override
    public void print(String s) {       //υλοποιηση της εκτυπωσης στον χρηστη
        System.out.println(s);
    }
    
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        
        client = new ClientDB();        //αρχικοποιω τον client
        Scanner keyboard = new Scanner(System.in);      //keyboard
        System.out.println("Welcome to Music Tracks Storage System\n");         //μενού
        System.out.println("Please choose one of the five operations to activate:\n");
        System.out.println("1. Add new song\n");
        System.out.println("2. Search By title\n");
        System.out.println("3. Search By singer\n");
        System.out.println("4. Rate a song\n");
        System.out.println("5. Search By ratings\n");
        String in = keyboard.nextLine();     //διαβαζω την επιλογή του χρήστη
        int i = Integer.parseInt(in);
        switch (i) {
            case 1:         //αν είναι προσθήκη διαβαζω τα στοιχεία και καλω την αντιστοιχη συναρτηση
              System.out.println("Please give title:");
              String title = keyboard.nextLine();
              System.out.println("Please give genre:");
              String genre = keyboard.nextLine(); 
              System.out.println("Please give singer:");
              String singer = keyboard.nextLine(); 
              System.out.println("Please give duration:");
              int duration = keyboard.nextInt();
              
              server.add(title, genre, singer, duration, client);
              break;
            case 2:             //αν ειναι αναζητηση βαση τιτλου
              System.out.println("Please give title:");
              title = keyboard.nextLine(); 
              
              server.searchTitle(title, client);
              break;
            case 3:             //αν ειναι αναζητηση βαση τραγουδιστή
              System.out.println("Please give singer:");
              singer = keyboard.nextLine(); 
              
              server.searchSinger(singer, client);
              break;
            case 4:         //προσθήκη ή ενημέρωση κριτικής
              System.out.println("Please give title:");
              title = keyboard.nextLine(); 
              System.out.println("Please give username:");
              String username = keyboard.nextLine(); 
              System.out.println("Please give rating:");
              int rating = Integer.parseInt(keyboard.nextLine());
              
              server.rate(username, title, rating, client);
              break;
            case 5:         //αν ειναι αναζητηση βαση βαθμολογίας
              System.out.println("Please give rating:");
              rating = Integer.parseInt(keyboard.nextLine());
              
              server.searchRating(rating, client);
              break;
            }

   
    }
}
