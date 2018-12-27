package com.Command;

import com.SQLConnect;
import com.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ShowUsersCommand extends AbstractCommand {

    @Override
    public void execute() {
        try {
            Statement stm = sqlConnect.connect();
            String query = "SELECT * FROM avia_schema.users";
            ResultSet resSet = stm.executeQuery(query);
            ArrayList<User> users = new ArrayList<User>();
            while (resSet.next()) {
                if(!resSet.getString("login").equals("admin"))
                    users.add(new User(resSet.getString("login"), resSet.getString("password")));

            }
            coos.writeObject(users);
            coos.flush();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ShowUsersCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }
}
