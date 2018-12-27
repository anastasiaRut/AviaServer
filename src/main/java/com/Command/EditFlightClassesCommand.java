package com.Command;

import com.Airport;
import com.FlightClass;
import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditFlightClassesCommand extends AbstractCommand {
    public EditFlightClassesCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {

            List<FlightClass> flightClasses;
            Object object = cois.readObject();
            flightClasses = (ArrayList<FlightClass>)object;
            Statement stm = sqlConnect.connect();
            String query="";
            StringBuilder builder =new StringBuilder();
            for(int i=0;i<flightClasses.size();i++)
                builder.append("?,");

            PreparedStatement s;
            query = "DELETE FROM avia_schema.classes WHERE code NOT IN ("+builder.deleteCharAt(builder.length()-1).toString()+")";
            s = sqlConnect.getConnection().prepareStatement(query);
            int index=1;
            for(FlightClass a: flightClasses){
                s.setObject(index++,a.getCode() );
            }
            s.executeUpdate();
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
            stm.executeUpdate("TRUNCATE TABLE avia_schema.classes ");
            stm.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
            for (int i = 0; i < flightClasses.size(); i++) {
                if (flightClasses.get(i).getCode() == null)
                    break;
                query = "INSERT INTO avia_schema.classes (code, name) VALUES (\"" + flightClasses.get(i).getCode() + "\", \"" + flightClasses.get(i).getName()+ "\")";
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
