package com.Command;

import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class FlightClassesUpdate extends AbstractCommand {
    public FlightClassesUpdate(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }
    @Override
    public void execute() {
        String codeClass;
        String newCodeClass;
        try {
            codeClass = (String)cois.readObject();
            newCodeClass =(String)cois.readObject();
            Statement stm = sqlConnect.connect();
            String query="";
            PreparedStatement s;
            query = "UPDATE avia_schema.classes SET code = ? WHERE code = ?";
            s = sqlConnect.getConnection().prepareStatement(query);
            s.setObject(1,newCodeClass);
            s.setObject(2,codeClass);
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
