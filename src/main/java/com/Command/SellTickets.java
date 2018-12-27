package com.Command;

import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SellTickets extends AbstractCommand {
    public SellTickets(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        String number;
        String newSeats;
        try {
            number = (String)cois.readObject();
            newSeats =(String)cois.readObject();
            newSeats=Integer.toString(Integer.parseInt(newSeats)-1);
            Statement stm = sqlConnect.connect();
            String query="";
            PreparedStatement s;
            query = "UPDATE avia_schema.flight SET seatsLeft = ? WHERE flightNumber = ?";
            s = sqlConnect.getConnection().prepareStatement(query);
            s.setObject(1,newSeats);
            s.setObject(2,number);
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
