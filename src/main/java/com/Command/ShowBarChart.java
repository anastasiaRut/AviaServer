package com.Command;

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

public class ShowBarChart extends AbstractCommand {
    public ShowBarChart(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {

        try {
            Statement stm = sqlConnect.connect();
            String query = "SELECT classOfFlight FROM avia_schema.flight";
            ResultSet resSet = null;

            resSet = stm.executeQuery(query);

            ArrayList<String> classes = new ArrayList<String>();
            List<String> namesOfClasses = new ArrayList<String>();
            while (resSet.next()) {
                classes.add(resSet.getString("classOfFlight"));

            }
            for(String code: classes)
            {
                query = "SELECT name FROM avia_schema.classes WHERE code = ?";
                PreparedStatement sClass;
                sClass = sqlConnect.getConnection().prepareStatement(query);
                sClass.setObject(1,code);
                ResultSet resultSet = sClass.executeQuery();
                resultSet.next();
                namesOfClasses.add(resultSet .getString(1));
            }
            coos.writeObject(namesOfClasses);
            coos.flush();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
