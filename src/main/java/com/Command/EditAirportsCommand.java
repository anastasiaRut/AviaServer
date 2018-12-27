package com.Command;

import com.Airport;
import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditAirportsCommand extends AbstractCommand {
    public EditAirportsCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {

            List<Airport> airports;
            Object object = cois.readObject();
            airports = (ArrayList<Airport>)object;
            Statement stm = sqlConnect.connect();
            String query="";
//отсюда
            StringBuilder builder =new StringBuilder();
            for(int i=0;i<airports.size();i++)
                builder.append("?,");

                PreparedStatement s;
                query = "DELETE FROM avia_schema.destinations WHERE idAirport NOT IN ("+builder.deleteCharAt(builder.length()-1).toString()+")";
                s = sqlConnect.getConnection().prepareStatement(query);
                int index=1;
                for(Airport a: airports){
                s.setObject(index++,a.getIdAirport() );
                }
                s.executeUpdate();


            //досюда
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE TABLE avia_schema.destinations ");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
            for (int i = 0; i < airports.size(); i++) {
                if (airports.get(i).getIdAirport() == null)
                    break;
                query = "INSERT INTO avia_schema.destinations (idAirport, name, city) VALUES (\"" + airports.get(i).getIdAirport() + "\", \"" + airports.get(i).getName()+"\", \"" +airports.get(i).getCity() + "\")";
                stm.execute(query);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
