package com.Command;

import com.SQLConnect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegistrationCommand extends AbstractCommand {
    public RegistrationCommand(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {
            String login = (String)cois.readObject();
            String pass = (String)cois.readObject();
            String _login;
            boolean flag = false;
            Statement stm = sqlConnect.connect();
            String query = "SELECT * FROM avia_schema.users";
            ResultSet resSet = stm.executeQuery(query);
            while (resSet.next()) {
                _login = resSet.getString("login");
                if (login.equals(_login)) {
                    coos.writeObject("Логин занят");
                    coos.flush();
                    flag = true;
                    break;
                }

            }
            if (flag)
                return;
            int count = -1;
            if (resSet.last()) {
                count = resSet.getRow();
            }
            resSet.beforeFirst();
            int numb = count + 1;
            query = "INSERT INTO avia_schema.users (id, login, password) VALUES (" + numb + ", \"" + login + "\", \"" + pass + "\")";
            stm.execute(query);
            coos.writeObject("Пользователь добавлен");
            coos.flush();


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
