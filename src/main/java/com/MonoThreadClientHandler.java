package com;

import com.Command.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class MonoThreadClientHandler implements Runnable {
    private static Socket clientDialog;
    public static View view;

    public MonoThreadClientHandler(Socket client, View view) {
        MonoThreadClientHandler.clientDialog = client;
        MonoThreadClientHandler.view = view;
    }

    @Override
    public void run() {

        try {

            ObjectOutputStream coos = new ObjectOutputStream(clientDialog.getOutputStream());
            ObjectInputStream cois = new ObjectInputStream(clientDialog.getInputStream());
            SQLConnect sqlConnect = new SQLConnect();
            //  Statement connect = sqlConnect.connect();
            while (!clientDialog.isClosed()) {
                view.settext("\nЧтение из канала связи\n");
                Integer entry;
                try {
                    entry = (Integer) cois.readObject();
                    view.settext(entry.toString());
                    switch (entry) {
                        case 1: {
                            AbstractCommand command = new RegistrationCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 2: {
                            AbstractCommand command = new AuthorizationCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 3: {
                            AbstractCommand command = new ShowUsersCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 4: {
                            AbstractCommand command = new EditUsersCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 5: {
                           AbstractCommand command = new ShowAirportsCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 6: {
                            AbstractCommand command = new EditAirportsCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 7: {
                            AbstractCommand command = new ShowFlightClassesCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 8: {
                            AbstractCommand command = new EditFlightClassesCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 9: {
                            AbstractCommand command = new ShowFlightsCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 10: {
                            AbstractCommand command = new EditFlightsCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 11: {
                            AbstractCommand command = new AirportsUpdateCommand(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 12: {
                            AbstractCommand command = new FlightClassesUpdate(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 13: {
                            AbstractCommand command = new ShowFiltr(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 14: {
                            AbstractCommand command = new ShowTickets(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 15: {
                            AbstractCommand command = new SellTickets(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 16: {
                            AbstractCommand command = new ShowDiagram(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                        case 17: {
                            AbstractCommand command = new ShowBarChart(coos, cois, sqlConnect);
                            command.execute();
                            break;
                        }
                    }

                } catch (SocketException e) {
                    view.settext(e.getMessage());
                    clientDialog.close();
                } catch (ClassNotFoundException e) {
                    view.settext("Не удалось считать сообщение");
                    e.printStackTrace();
                }

            }

            coos.close();
            cois.close();

            clientDialog.close();

            view.settext("\nКлиент отключился \n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
