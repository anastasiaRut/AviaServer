package com.Command;

import com.Airport;
import com.Flight;
import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class ShowFlightsCommand extends  AbstractCommand {
    public ShowFlightsCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }
    @Override
    public void execute() {
        try {
            Statement stm = sqlConnect.connect();
            String query = "SELECT * FROM avia_schema.flight";
            ResultSet resSet = stm.executeQuery(query);
            ArrayList<Flight> flights = new ArrayList<Flight>();
            while (resSet.next()) {
                flights.add(new Flight(resSet.getString("flightNumber"), resSet.getString("destinationCode"),resSet.getString("day"),resSet.getString("month"),resSet.getString("year"),resSet.getString("classOfFlight"),resSet.getString("seatsLeft"),resSet.getString("flightTime")));

            }
            coos.writeObject(flights);
            coos.flush();

            query = "SELECT * FROM avia_schema.destinations";
            resSet = stm.executeQuery(query);
            ArrayList<String> destinations= new ArrayList<>();
            while (resSet.next()) {
                destinations.add( resSet.getString("idAirport"));

            }
            coos.writeObject(destinations);
            coos.flush();

            query = "SELECT * FROM avia_schema.classes";
            resSet = stm.executeQuery(query);
            ArrayList<String> classes= new ArrayList<>();
            while (resSet.next()) {
                classes.add( resSet.getString("code"));

            }
            coos.writeObject(classes);
            coos.flush();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


