package com.Command;

import com.FlightClass;
import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShowFlightClassesCommand extends AbstractCommand {
    public ShowFlightClassesCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {
            Statement stm = sqlConnect.connect();
            String query = "SELECT * FROM avia_schema.classes";
            ResultSet resSet = stm.executeQuery(query);
            ArrayList<FlightClass> classes = new ArrayList<FlightClass>();
            while (resSet.next()) {
                classes.add(new FlightClass(resSet.getString("code"), resSet.getString("name")));

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
