package foo;

import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ColumnDefinitions;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

public class CassandraClient {
	private Cluster cluster;
	private Session session;
	
	public CassandraClient(String IPAddress, String keyspace)
	{
		try {
	        Cluster.Builder builder = Cluster.builder().addContactPoint(IPAddress );
	        cluster = builder.build();
	        if(!keyspace.equals(""))
	        	session = cluster.connect("keyspacename");
	        else
	        	session = cluster.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close()
	{
        session.close();
        cluster.close();
	}
	
	public ResultSet executeQuery(String query)
	{
		ResultSet rs = null;
		try {
			rs = session.execute(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public void printResultSet(ResultSet rs)
	{
		
        try {
    		List<ColumnDefinitions.Definition> columns = rs.getColumnDefinitions().asList();
    		for (ColumnDefinitions.Definition c : columns)
    		{
    			System.out.print(c.getName() + '\t');
    		}
    		System.out.print('\n');
    		
    		List<Row> rows = rs.all();
            for(Row r : rows)
            {
            	for (ColumnDefinitions.Definition c : columns)
        		{
        			System.out.print(r.getObject(c.getName()).toString() + '\t');
        		}
            	System.out.print('\n');
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
