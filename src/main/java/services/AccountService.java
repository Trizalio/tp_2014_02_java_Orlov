package services;

import dataSets.UsersDataSet;
import database.Connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import dao.UsersDAO;
import messageSystem.MessageSystem;
import messageSystem.Subscriber;
import messageSystem.Address;

public class AccountService implements Subscriber, Runnable {
    private static Map<String, String> sessionKeys = new HashMap<>();
    private Address address;
    private MessageSystem messageSystem;
    private Connection connection;

    public MessageSystem getMessageSystem() {
        return messageSystem;
    }

    public AccountService(MessageSystem messageSystem){
        this.connection = Connector.openConnection();
        this.messageSystem = messageSystem;
        address = new Address();
        messageSystem.addService(this);
    }
    @Override
    public Address getAddress() {
        return address;
    }

    public void run() {
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            messageSystem.execForSubscriber(this);
        }
    }
    public static boolean initBase(){
        System.out.append("\n").append("AccountService: initBase\n");

        Connection connection = Connector.openConnection();
        UsersDAO userDAO = new UsersDAO(connection);
        try {
            userDAO.dropTable();
        } catch(Exception ignore){}
        try {
            userDAO.initTable();
            userDAO.initTableData();
            System.out.append("AccountService: done!").append("\n");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.append("AccountService: fail!").append("\n");
            return false;
        }
    }

    public long loginUser(String login, String password){
        try {
            System.out.append("\n").append("AccountService: loginUser - login: ").append(login)
                    .append(", pass:").append(password).append(".\n");

            //Connection connection = Connector.openConnection();
            UsersDAO userDAO = new UsersDAO(connection);
            UsersDataSet target = userDAO.getUserByLogin(login);
            //connection.close();

            if(target == null){
                System.out.append("AccountService: user not found!").append("\n");
                return -1;
            }
            if(target.getPassword().equals(password)){
                System.out.append("AccountService: right pass.\n");
                return target.getId();
            }else{
                System.out.append("AccountService: wrong pass.\n");
                return -3;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -4;
    }
    public long registerUser(String login, String password){
        try {
            System.out.append("\n").append("AccountService: registerUser - login: ").append(login)
                    .append(", pass:").append(password).append(".\n");

            //Connection connection = Connector.openConnection();
            UsersDAO userDAO = new UsersDAO(connection);
            UsersDataSet target = userDAO.getUserByLogin(login);

            if(target != null){
                //connection.close();
                System.out.append("AccountService: user already exists!").append("\n");
                return -2;
            }

            userDAO.addUser(login, password);
            UsersDataSet result = userDAO.getUserByLogin(login);
            //connection.close();
            System.out.append("AccountService: done!").append("\n");
            return result.getId();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -4;
    }
    /*public boolean setUserKey(String login, String newKey){
        System.out.append("\n").append("AccountService: setUserKey - login: ").append(login)
                .append(", newKey:").append(newKey).append(".\n");

        if(login == null || newKey == null){
            System.out.append("AccountService: login or key does not exists!").append("\n");
            return false;
        }else{
            System.out.append("AccountService: done!").append("\n");
            sessionKeys.put(login, newKey);
            return true;
        }
    }*/
    /*public boolean checkUserKey(String login, String key){
        System.out.append(".\n").append("AccountService: checkUserKey - login: ").append(login)
                .append(", key: ").append(key.toString()).append(".\n");

        if(login == null || key == null){
            System.out.append("AccountService: login or key does not exists!").append("\n");
            return false;
        }else{
            String realKey = sessionKeys.get(login);
            if(realKey == null){
                System.out.append("AccountService: user not found!").append("\n");
                return false;
            }else if(key.equals(realKey)){
                System.out.append("AccountService: right key!").append("\n");
                return true;
            }else{
                System.out.append("AccountService: wrong key!").append("\n");
                return false;
            }
        }
    }*/
    /*public boolean clearUserKey(String login){
        System.out.append("\n").append("AccountService: clearUserKey - login: ").append(login).append(".\n");

        if(login == null){
            System.out.append("AccountService: login or key does not exists!").append("\n");
            return false;
        }else{
            String realKey = sessionKeys.get(login);
            if(realKey == null){
                System.out.append("AccountService: user not found!").append("\n");
                return false;
            }else{
                sessionKeys.remove(login);
                System.out.append("AccountService: done!").append("\n");
                return false;
            }
        }
    }*/
    public int modifyUser(String loginOld, String loginNew){
        try {
            System.out.append("\n").append("AccountService: modifyUser - old login:").append(loginOld)
                    .append(", new login: ").append(loginNew).append(".\n");

            //Connection connection = Connector.openConnection();
            UsersDAO userDAO = new UsersDAO(connection);
            UsersDataSet target = userDAO.getUserByLogin(loginOld);

            if(target == null){
                //connection.close();
                System.out.append("AccountService: user not found!").append("\n");
                return -1;
            }

            userDAO.setUserLoginById(target.getId(), loginNew);
            //connection.close();
            System.out.append("AccountService: done!").append("\n");
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return -4;
        }
    }
    public int deleteUser(String login){
        try {
            System.out.append("\n").append("AccountService: deleteUser - login:").append(login).append(".\n");

            Connection connection = Connector.openConnection();
            UsersDAO userDAO = new UsersDAO(connection);
            UsersDataSet target = userDAO.getUserByLogin(login);

            if(target == null){
                //connection.close();
                System.out.append("AccountService: user not found!").append("\n");
                return -1;
            }

            userDAO.deleteUserById(target.getId());
            //connection.close();
            System.out.append("AccountService: done!").append("\n");
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return -4;
        }
    }
}
