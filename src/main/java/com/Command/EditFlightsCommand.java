package com.Command;

import com.Flight;
import com.FlightClass;
import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditFlightsCommand extends AbstractCommand {
    public EditFlightsCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {

            List<Flight> flights;
            Object object = cois.readObject();
            flights = (ArrayList<Flight>)object;
            Statement stm = sqlConnect.connect();
            String query="";
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE TABLE avia_schema.flight ");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
            for (int i = 0; i < flights.size(); i++) {
                if (flights.get(i).getFlightNumber() == null)
                    break;
                query = "INSERT INTO avia_schema.flight (flightNumber, destinationCode, day, month, year, classOfFlight, seatsLeft, flightTime) VALUES (\"" + flights.get(i).getFlightNumber() + "\", \"" + flights.get(i).getDestinationCode()+"\", \"" + flights.get(i).getDay()+"\", \"" + flights.get(i).getMonth()+"\", \"" + flights.get(i).getYear()+"\", \"" + flights.get(i).getClassOfFlight()+"\", \"" + flights.get(i).getSeatsLeft()+"\", \"" + flights.get(i).getFlightTime()+ "\")";
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
