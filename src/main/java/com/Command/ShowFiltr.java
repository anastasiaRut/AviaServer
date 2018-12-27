package com.Command;

import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShowFiltr extends AbstractCommand {
    public ShowFiltr(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
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
            List<String> cities = new ArrayList<>();
            while (resSet.next()) {
                cities.add(resSet.getString("city"));
            }
            coos.writeObject(cities);
            coos.flush();
            query = "SELECT * FROM avia_schema.classes";
            resSet = stm.executeQuery(query);
            List<String> classes = new ArrayList<>();
            while (resSet.next()) {
                classes.add(resSet.getString("name"));
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
