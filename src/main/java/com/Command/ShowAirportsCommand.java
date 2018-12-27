package com.Command;

import com.Airport;
import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShowAirportsCommand extends AbstractCommand {
    public ShowAirportsCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {
            Statement stm = sqlConnect.connect();
            String query = "SELECT * FROM avia_schema.destinations";
            ResultSet resSet = stm.executeQuery(query);
            ArrayList<Airport> airports = new ArrayList<Airport>();
            while (resSet.next()) {
                airports.add(new Airport(resSet.getString("idAirport"), resSet.getString("name"), resSet.getString("city")));

            }
            coos.writeObject(airports);
            coos.flush();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
