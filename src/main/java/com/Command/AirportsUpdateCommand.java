package com.Command;

import com.Airport;
import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AirportsUpdateCommand extends AbstractCommand {
    public AirportsUpdateCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }
    @Override
    public void execute() {
        String airportId;
        String newairportId;
        try {
        airportId = (String)cois.readObject();
        newairportId =(String)cois.readObject();
        Statement stm = sqlConnect.connect();
        String query="";
        PreparedStatement s;
        query = "UPDATE avia_schema.destinations SET idAirport = ? WHERE idAirport = ?";
        s = sqlConnect.getConnection().prepareStatement(query);
        s.setObject(1,newairportId);
            s.setObject(2,airportId);
        s.executeUpdate();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
