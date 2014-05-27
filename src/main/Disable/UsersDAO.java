package database.dao;

import database.dataSets.UsersDataSet;
import database.TExecutor;
import database.TResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAO {
	
	private Connection con;
	
	public UsersDAO(Connection con){
		this.con = con;
	}

    /*public UsersDataSet getUserById(long id) throws SQLException{
        TExecutor exec = new TExecutor();
        return exec.execQuery(con, "select * from users where id='" + id + "'", new TResultHandler<UsersDataSet>(){

            public UsersDataSet handle(ResultSet result) throws SQLException {
                result.next();
                return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getLong(4));
            }

        });
    }*/
    public UsersDataSet getUserByLogin(String login) throws SQLException{
        TExecutor exec = new TExecutor();
        System.out.append("UsersDAO: looking for ");
        System.out.append(login);
        System.out.append(" \n");
        return exec.execQuery(con, "select * from users where login='" + login+"'", new TResultHandler<UsersDataSet>(){
            public UsersDataSet handle(ResultSet result) throws SQLException {
                if(result.next()){
                    return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3), result.getLong(4));
                }
                return null;
            }

        });
    }

    public boolean addUser(String login, String pass) throws SQLException{
        TExecutor exec = new TExecutor();
        System.out.append("UsersDAO: add user ");
        System.out.append(login);
        System.out.append(" \n");
        UsersDataSet checkedUser = getUserByLogin(login);
        if(checkedUser != null){
            System.out.append("UsersDAO: already exists \n");
            return false;
        }else{
            exec.execUpdate(con, "insert into users (`login`, `pass`, `key`) values ('" +
                    login + "', '" + pass + "', null);\n");
            System.out.append("UsersDAO: user added \n");
            return true;
        }
    }
    public boolean deleteUserbyLogin(String login) throws SQLException{
        TExecutor exec = new TExecutor();
        System.out.append("UsersDAO: delete user ");
        System.out.append(login);
        System.out.append(" \n");
        UsersDataSet checkedUser = getUserByLogin(login);
        if(checkedUser == null){
            System.out.append("UsersDAO: user not found \n");
            return false;
        }else{
            exec.execUpdate(con, "delete from users where `login` = '" + login + "';\n");
            System.out.append("UsersDAO: user deleted \n");
            return true;
        }
    }

    /*public boolean deleteUserbyId(long id) throws SQLException{
        TExecutor exec = new TExecutor();
        System.out.append("UsersDAO: delete user ");
        System.out.append(String.valueOf(id));
        System.out.append(" \n");
        UsersDataSet checkedUser = getUserById(id);
        if(checkedUser == null){
            System.out.append("UsersDAO: user not found \n");
            return false;
        }else{
            exec.execUpdate(con, "delete from users where `id` = " + String.valueOf(id) + ";\n");
            System.out.append("UsersDAO: user deleted \n");
            return true;
        }
    }*/
    public boolean setUserKey(String login, Long key) throws SQLException{
        TExecutor exec = new TExecutor();
        System.out.append("UsersDAO: Change key for ");
        System.out.append(login);
        System.out.append(" \n");
        UsersDataSet checkedUser = getUserByLogin(login);
        if(checkedUser == null){
            System.out.append("UsersDAO: user not found \n");
            return false;
        }else{
            exec.execUpdate(con, "update users set `key` = " + key.toString() + " where login = '"
                    + login + "';\n");
            System.out.append("UsersDAO: key changed \n");
            return true;
        }
    }
    public boolean setUserLogin(String loginOld, String loginNew) throws SQLException{
        TExecutor exec = new TExecutor();
        System.out.append("UsersDAO: Change login for ");
        System.out.append(loginOld);
        System.out.append(" \n");
        UsersDataSet checkedUser = getUserByLogin(loginOld);
        if(checkedUser == null){
            System.out.append("UsersDAO: user not found \n");
            return false;
        }else{
            exec.execUpdate(con, "update users set `login` = '" + loginNew + "' where login = '"
                    + loginOld + "';\n");
            System.out.append("UsersDAO: key changed \n");
            return true;
        }
    }
}
