package ua.kiev.prog.case2;

import ua.kiev.prog.shared.Client;
import ua.kiev.prog.shared.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection())
        {
            // remove this
            try {
                try (Statement st = conn.createStatement()) {
                    st.execute("DROP TABLE IF EXISTS Clients");
                    //st.execute("CREATE TABLE Clients (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, name VARCHAR(20) NOT NULL, age INT)");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            ClientDAOImpl2 dao = new ClientDAOImpl2(conn, "Clients");
            dao.createTable(Client.class);

            Client a = new Client("client A", 18);
            dao.add(a);
            int idA = a.getId();
            System.out.println("id client A \t" + idA);

            Client b = new Client("client B", 23);
            dao.add(b);
            int idB = b.getId();
            System.out.println("id client B \t" + idB);

            Client c = new Client("client C", 32);
            dao.add(c);
            int idC = c.getId();
            System.out.println("id client C \t" + idC);

            List<Client> list = dao.getAll(Client.class);
            for (Client cli : list)
                System.out.println(cli);

            list.get(0).setAge(55);
            dao.update(list.get(0));

            List<Client> listWithAge = dao.getAllWith(Client.class, "id", "age");
            System.out.println("\nlistWithAge:\n");
            for (Client cli : listWithAge){
                System.out.println(cli);
            }

            System.out.println();

            List<Client> listWithName = dao.getAllWith(Client.class, "name");
            System.out.println("\nlistWithName: \n");
            for (Client cli : listWithName){
                System.out.println(cli);
            }

            /*

            List<Client> list = dao.getAll(Client.class, "name", "age");
            List<Client> list = dao.getAll(Client.class, "age");
            for (Client cli : list)
                System.out.println(cli);

             */

            dao.delete(list.get(0));
        }
    }
}
