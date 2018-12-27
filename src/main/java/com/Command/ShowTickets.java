package com.Command;

import com.FlightClass;
import com.SQLConnect;
import com.Ticket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowTickets extends AbstractCommand {
    public ShowTickets(ObjectOutputStream coos, ObjectInputStream cois, SQLConnect sqlConnect) {
        this.coos = coos;
        this.cois = cois;
        this.sqlConnect = sqlConnect;
    }

    @Override
    public void execute() {
        try {
            String cityFiltr = (String)cois.readObject();
            String dayFiltr = (String)cois.readObject();
            String monthFiltr = (String)cois.readObject();
            String yearFiltr = (String)cois.readObject();
            String classFiltr = (String)cois.readObject();
            Statement stm = sqlConnect.connect();
            String query;
            query="SELECT * FROM avia_schema.destinations WHERE city = ?";
            PreparedStatement sCity;
            sCity = sqlConnect.getConnection().prepareStatement(query);
            sCity.setObject(1,cityFiltr);
            ResultSet resultCity = sCity.executeQuery();
            List<String> cityId = new ArrayList<>();
            List<String> airportName = new ArrayList<>();
            Map<String,String> map = new HashMap<>();
            while(resultCity.next()) {
                cityId.add(resultCity.getString("idAirport"));
                airportName.add(resultCity.getString("name"));
                map.put(resultCity.getString("idAirport"),resultCity.getString("name"));

            }
            StringBuilder builder =new StringBuilder();
            for(int i=0;i<cityId.size();i++)
                builder.append("?,");

            query="SELECT * FROM avia_schema.classes WHERE name = ?";
            PreparedStatement sClass;
            sClass = sqlConnect.getConnection().prepareStatement(query);
            sClass.setObject(1,classFiltr);
            ResultSet resultClass = sClass.executeQuery();
            resultClass.next();
            String classId = resultClass.getString("code");


            query = "SELECT * FROM avia_schema.flight WHERE day = ? AND month = ? AND year = ? AND classOfFlight = ? AND destinationCode IN ("+builder.deleteCharAt(builder.length()-1).toString()+")";

            PreparedStatement sFlights;
            sFlights = sqlConnect.getConnection().prepareStatement(query);
            sFlights.setObject(1,dayFiltr);
            sFlights.setObject(2,monthFiltr);
            sFlights.setObject(3,yearFiltr);
            sFlights.setObject(4,classId);
            int index=5;
            for(String a: cityId){
                sFlights.setObject(index++,a );
            }

            ResultSet resSet = sFlights.executeQuery();
            List<Ticket> tickets = new ArrayList<>();
            if(!resSet.isBeforeFirst()){
                coos.writeObject(tickets);
                coos.flush();
                return;
            }

            String date="";
            if(Integer.parseInt(dayFiltr)>=1 && Integer.parseInt(dayFiltr)<=9 )
            date=date+"0"+dayFiltr+".";
            else  date=date+dayFiltr+".";

            if(Integer.parseInt(monthFiltr)>=1 && Integer.parseInt(monthFiltr)<=9 )
                date=date+"0"+monthFiltr+".";
            else  date=date+monthFiltr+".";

            date=date+yearFiltr;

            while (resSet.next()) {
                tickets.add(new Ticket(resSet.getString("flightNumber"),cityFiltr+", "+map.get(resSet.getString("destinationCode")),date,classFiltr,resSet.getString("seatsLeft"),resSet.getString("flightTime")));
            }
            coos.writeObject(tickets);
            coos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
