package com.Command;

import com.SQLConnect;
import com.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class EditUsersCommand extends AbstractCommand {

    public EditUsersCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {
            ArrayList<User> users = (ArrayList<User>)cois.readObject();
            Statement stm = sqlConnect.connect();
            String query="";
            stm.executeUpdate("TRUNCATE TABLE avia_schema.users ");
            query = "INSERT INTO avia_schema.users (login,password) VALUES (\"" + "admin" + "\", \"" + "admin" + "\")";
            stm.execute(query);
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getLogin() == null)
                    break;
                query = "INSERT INTO avia_schema.users (login,password) VALUES (\"" + users.get(i).getLogin() + "\", \"" + users.get(i).getPassword() + "\")";
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
