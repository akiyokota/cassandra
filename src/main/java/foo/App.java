package foo;

import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        /*----------Step1: Create a Cluster Object-------------*/
        //Creating Cluster.Builder object
        //Adding contact point to the Cluster.Builder object
        Cluster.Builder builder = Cluster.builder().addContactPoint("127.0.0.1" );
        
        //Building a cluster
        Cluster cluster = builder.build();
        cluster.getConfiguration().getSocketOptions().setReadTimeoutMillis(3600);
        
        /*----------Step 2: Create a Session Object-------------*/
        //create a session object with the keyspace you want to connect to
        Session session = cluster.connect("keyspacename");
        
        /*----------Step 3: Execute Query-------------*/
        //session.execute("use keyspacename");
        String query = "select * from playlists";
        
        ResultSet rs = null;
        rs = session.execute(query);
        List<Row> rows = rs.all();
        for(Row r : rows)
        {
        	System.out.println(r.getString("title"));
        }
        
        session.close();
        cluster.close();
    }
}
