package com.Command;

import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AuthorizationCommand extends AbstractCommand {

    public AuthorizationCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {
            String login = (String) cois.readObject();
            String pass = (String) cois.readObject();
            view.settext("\n Пытается войти " + login);
            String _login;
            boolean flag = false;
            Statement stm = sqlConnect.connect();
            String query = "SELECT * FROM avia_schema.users";
            ResultSet resSet = stm.executeQuery(query);
            while (resSet.next()) {
                if (resSet.getString("login").equals(login) && resSet.getString("password").equals(pass)) {
                    if (resSet.getString("login").equals("admin"))
                        coos.writeObject("admin");
                    else coos.writeObject("Вошло!");
                    coos.flush();
                    flag = true;
                    break;

                }


            }
            if (!flag) {
                coos.writeObject("Неверный логин или пароль");
                coos.flush();
            }



        } catch (SQLException ex) {
            try {
                coos.writeObject("SQL Exeption");
                coos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
