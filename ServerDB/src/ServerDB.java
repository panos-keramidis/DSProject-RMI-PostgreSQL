
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

/*
 * Παναγιώτης Κεραμίδης
    321/2016067
 */


/**
 *
 * @author panos
 */
public class ServerDB extends UnicastRemoteObject implements ServerDBInterface{

    private static ServerDB server;     //ένα αντικείμενο τύπου server
    
    public ServerDB() throws RemoteException {
        super();        //constructor του πατέρα UnicastRemoteObject
    }
    
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        Registry r = LocateRegistry.createRegistry(1099);       //RMI για εκτέλεση netbeans
        server = new ServerDB();        //αρχικοποιώ το first server
        
        Naming.rebind("//localhost/server", server);      //δηλώνοντας σε ποια πόρτα ακούμε
        

        
    }

    @Override
    public void add(String stitle, String sgenre, String ssinger, int sduration, ClientDBInterface cl) throws RemoteException {
        Connection c = null;            //ξεκινάμε το connection
        
        Statement stmt = null;          //τα statements
        try {
         Class.forName("org.postgresql.Driver");        //δηλώνουμε τον driver της postgre
         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MusicTracks", "testuser", "123");    //τα στοιχεία σύνδεσης
         System.out.println("Opened database successfully");        //ένα μήνυμα αναγνωρισης
         
         String query = "INSERT INTO \"Songs\" (title, genre, singer, duration) VALUES ('"+ stitle + "', '" + sgenre +"', '" + ssinger + "', " + sduration + ")";
         //το query είναι βάλε στον πίνακα Songs στα αντίστοιχα πεδία τα πεδία του χρήστη
         stmt = c.createStatement();        //κανε ένα statement
         try{
             stmt.executeQuery(query);      //εκτέλεσε το statement με βάση το query 
         }catch(SQLException e ){
             System.out.println(e);
         }
         

         stmt.close();      //κλείσε τις συνδέσεις
         c.close();
         cl.print("OK");        //μήνυμα επιτυχίας στον client
      } catch ( ClassNotFoundException | SQLException e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         //System.exit(0);
         cl.print("Not OK");        //μήνυμα αποτυχίας στον client
      }  
        
    }

    @Override
    public void searchTitle(String songtitle, ClientDBInterface cl) throws RemoteException {
        Connection c = null;
        
        Statement stmt = null;          //όμοια με τα παραπάνω
        try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MusicTracks", "testuser", "123");
         System.out.println("Opened database successfully");
         
         String query = "SELECT * FROM \"Songs\" WHERE title = '" + songtitle + "'";
         //διάλεξε όλα τα πεδία από το Songs που ο τίτλος τους είναι ο δωθέντας
         stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery(query);       //ό,τι πάρεις απ την υλοποίηση βάλτο σε ένα αντικείμενο τύπου Result set
         
         while ( rs.next() ) {      //αν δεν είναι άδειο
            String  title = rs.getString("title");      //αποθήκευσε ένα ένα τα στοιχεία που πήρες
            String singer  = rs.getString("singer");
            String  genre = rs.getString("genre");
            int duration = rs.getInt("duration");
            
            cl.print("title = " + title);           //εκτύπωσέ τα στον χρήστη
            cl.print(", singer = " + singer);
            cl.print(", genre = " + genre);
            cl.print(", duration = " + duration);
            cl.print("\n");
            
            System.out.println( "title = " + title );       //εκτύπωσέ τα στον σέρβερ
            System.out.println( ", singer = " + singer );
            System.out.println( ", genre = " + genre );
            System.out.println( ", duration = " + duration );
            System.out.println();
            
         }
         
         rs.close();        //κλείσε τις συνδέσεις
         stmt.close();
         c.close();
      } catch ( ClassNotFoundException | SQLException e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
    }

    @Override
    public void searchSinger(String ssinger, ClientDBInterface cl) throws RemoteException {
        Connection c = null;
        
        Statement stmt = null;
        try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MusicTracks", "testuser", "123");
         System.out.println("Opened database successfully");
         
         String query = "SELECT * FROM \"Songs\" WHERE singer = '" + ssinger + "'";
         //διάλεξε όλα από τα τραγούδια όπου ο τραγουδιστής είναι ο δωθέντας
         stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery(query);
         while ( rs.next() ) {
            String  title = rs.getString("title");
            String singer  = rs.getString("singer");
            String  genre = rs.getString("genre");
            int duration = rs.getInt("duration");
            
            cl.print("title = " + title);
            cl.print(", singer = " + singer);
            cl.print(", genre = " + genre);
            cl.print(", duration = " + duration);
            cl.print("\n");
            
            System.out.println( "title = " + title );
            System.out.println( ", singer = " + singer );
            System.out.println( ", genre = " + genre );
            System.out.println( ", duration = " + duration );
            System.out.println();
            
         }
         
         rs.close();
         stmt.close();
         c.close();
      } catch ( ClassNotFoundException | SQLException e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
    }

    @Override
    public void rate(String susername, String stitle, int srating, ClientDBInterface cl) throws RemoteException {
        if(srating>10 || srating<0){        //έλεγχος εγκυρότητας βαθμολογίας
            cl.print("Invalid rating");
            return;
        }
        
        Connection c = null;
        
        Statement stmt = null;
        try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MusicTracks", "testuser", "123");
         c.setAutoCommit(false);
         System.out.println("Opened database successfully");
         
         String query = "SELECT id FROM \"Songs\" WHERE title = '" + stitle + "'";
         //διάλεξε τα id από τα κομμάτια όπου ο τίτλος είναι ο δωθέντας
         stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery(query);
         
         int id=0;
         boolean rid = false;
         
         if(rs.next()){         //αν υπαρχει ένα id 
             id =rs.getInt("id");        //αποθήκευσε το id
            query = "SELECT * FROM \"Ratings\" WHERE username = '" + susername + "' AND id = " + id ;
            //διάλεξε από τις κριτικές όλα όσα το username είναι του χρήστη και το id είναι το id που ταιριάζει με το όνομα
            Statement stmt2 = c.createStatement();
            rs = stmt2.executeQuery(query);
            if(rs.next()){      //αν βρεθεί κάποιο
                System.out.println(rs.getString("username"));
                rs = stmt2.executeQuery(query);
                rid = true;     //υπάρχει id στον πίνακα ratings άρα έχει βαθμολογηθεί ήδη από τον χρήστη
            }
         }
         
         rs.close();
         System.out.println(id);
         
         if(rid){       //αν έχει ήδη βαθμολογηθεί
            
            query = "UPDATE \"Ratings\" SET grade='" + srating + "' WHERE username='" + susername + "' AND id=" + id;
            //ανανέωσε στα ratings τον βαθμό κριτικής με βάση το δωθέν βαθμό, όπου το username είναι του χρήστη και το id είναι το id του τραγουδιού του χρήστη
            Statement stmt3 = c.createStatement();
            try{
                stmt3.executeQuery(query);
                
            }catch(SQLException e){
                System.out.println(e);
            }
            cl.print("Rate Updated");
         }
         else{          //αν ειναι η πρώτη του κριτική στο τραγούδι αυτό     
            query = "INSERT INTO \"Ratings\" (grade, id, username) VALUES (" +srating + ", " + id + ", '" + susername+ "')";
            //βάλε στον Ratings το όνομα του χρηστη, το id του τραγουδιου και τον βαθμό κριτικής
            Statement stmt4 = c.createStatement();
           try{
                stmt4.executeQuery(query);
                
            }catch(SQLException e){
                System.out.println(e);
            }
            cl.print("Rate Submited");

         }
         
         rs.close();
         stmt.close();
         c.close();
         
      } catch ( ClassNotFoundException | SQLException e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
    }

    @Override
    public void searchRating(int rating, ClientDBInterface cl) throws RemoteException {
        Connection c = null;
        
        Statement stmt = null;
        try {
         Class.forName("org.postgresql.Driver");
         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/MusicTracks", "testuser", "123");
         System.out.println("Opened database successfully");
         
         String query = "SELECT title, singer, genre, duration, AVG(\"Ratings\".grade) AS avggrade\n" +
                    "FROM \"Songs\" LEFT JOIN \"Ratings\" ON \"Songs\".id=\"Ratings\".id\n" +
                    "GROUP BY title, singer, genre, duration\n" +
                    "HAVING AVG(\"Ratings\".grade)>" + rating;
         //διάλεξε ολα τα στοιχεία του τραγουδιού μαζί με τη μέση βαθμολογία από τον ratings (αποθήκευσέ τα ως avggrade)
         //από τον songs που υπάρχουν στον ratings όπου το id του τραγουδιού είναι ίδιο με το id του τραγουδιου της κριτικης
         // κατανεμημενα βαση στοιχειων τραγουδιου
         // δωθέντος πως η μέση βαθμολογία πρέπει να είναι μεγαλύτερη απ την ορισμένη
         
         stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery(query);
         while ( rs.next() ) {      //αν βρεθουν καποια εκτυπωσε τα
            String  title = rs.getString("title");
            String singer  = rs.getString("singer");
            String  genre = rs.getString("genre");
            int duration = rs.getInt("duration");
            
            cl.print("title = " + title);
            cl.print(", singer = " + singer);
            cl.print(", genre = " + genre);
            cl.print(", duration = " + duration);
            cl.print("\n");
            
            System.out.println( "title = " + title );
            System.out.println( ", singer = " + singer );
            System.out.println( ", genre = " + genre );
            System.out.println( ", duration = " + duration );
            System.out.println();
            
         }
         
         rs.close();
         stmt.close();
         c.close();
      } catch ( ClassNotFoundException | SQLException e ) {
         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
         System.exit(0);
      }
    }
    
}
