package services;

import database.dataSets.UsersDataSet;
import database.Connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import database.dao.UsersDAO;
import messageSystem.MessageSystem;
import messageSystem.Subscriber;
import messageSystem.Address;

public class AccountService implements Subscriber, Runnable {
    private static final int NO_USER = -1;
    private static final int USER_EXISTS = -2;
    private static final int WRONG_PASS = -3;
    private static final int SQL_EXCEPTION = -4;
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

            UsersDAO userDAO = new UsersDAO(connection);
            UsersDataSet target = userDAO.getUserByLogin(login);

            if(target == null){
                System.out.append("AccountService: user not found!").append("\n");
                return NO_USER;
            }
            if(target.getPassword().equals(password)){
                System.out.append("AccountService: right pass.\n");
                return target.getId();
            }else{
                System.out.append("AccountService: wrong pass.\n");
                return WRONG_PASS;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SQL_EXCEPTION;
    }
    public long registerUser(String login, String password){
        try {
            System.out.append("\n").append("AccountService: registerUser - login: ").append(login)
                    .append(", pass:").append(password).append(".\n");

            UsersDAO userDAO = new UsersDAO(connection);
            UsersDataSet target = userDAO.getUserByLogin(login);

            if(target != null){
                System.out.append("AccountService: user already exists!").append("\n");
                return USER_EXISTS;
            }

            userDAO.addUser(login, password);
            UsersDataSet result = userDAO.getUserByLogin(login);
            System.out.append("AccountService: done!").append("\n");
            return result.getId();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return SQL_EXCEPTION;
    }
    public int modifyUser(String loginOld, String loginNew){
        try {
            System.out.append("\n").append("AccountService: modifyUser - old login:").append(loginOld)
                    .append(", new login: ").append(loginNew).append(".\n");

            UsersDAO userDAO = new UsersDAO(connection);
            UsersDataSet target = userDAO.getUserByLogin(loginOld);

            if(target == null){
                System.out.append("AccountService: user not found!").append("\n");
                return NO_USER;
            }

            userDAO.setUserLoginById(target.getId(), loginNew);
            System.out.append("AccountService: done!").append("\n");
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return SQL_EXCEPTION;
        }
    }
    public int deleteUser(String login){
        try {
            System.out.append("\n").append("AccountService: deleteUser - login:").append(login).append(".\n");

            Connection connection = Connector.openConnection();
            UsersDAO userDAO = new UsersDAO(connection);
            UsersDataSet target = userDAO.getUserByLogin(login);

            if(target == null){
                System.out.append("AccountService: user not found!").append("\n");
                return NO_USER;
            }

            userDAO.deleteUserById(target.getId());
            System.out.append("AccountService: done!").append("\n");
            return 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return SQL_EXCEPTION;
        }
    }
}
