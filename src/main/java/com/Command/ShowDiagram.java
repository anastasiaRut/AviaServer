package com.Command;

import com.SQLConnect;
import com.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ShowDiagram extends AbstractCommand {
    public ShowDiagram(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {
            Statement stm = sqlConnect.connect();
            String query = "SELECT destinationCode FROM avia_schema.flight";
            ResultSet resSet = null;

            resSet = stm.executeQuery(query);

            ArrayList<String> codes = new ArrayList<String>();
            List<String> cities = new ArrayList<String>();
            while (resSet.next()) {
                    codes.add(resSet.getString("destinationCode"));

            }
            for(String code: codes)
            {
                query = "SELECT city FROM avia_schema.destinations WHERE idAirport = ?";
                PreparedStatement sCity;
                sCity = sqlConnect.getConnection().prepareStatement(query);
                sCity.setObject(1,code);
                ResultSet resultCity = sCity.executeQuery();
                resultCity.next();
                cities.add(resultCity .getString(1));
            }
            coos.writeObject(cities);
            coos.flush();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
