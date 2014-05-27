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

    public UsersDataSet getUserByLogin(String login) throws SQLException{
        System.out.append("UsersDAO: getUserByLogin ").append(login).append(".\n");

        TExecutor exec = new TExecutor();
        return exec.execQuery(con, "select * from users where login='" + login + "'", new TResultHandler<UsersDataSet>(){
            public UsersDataSet handle(ResultSet result) throws SQLException {
            if(result.next()){
                return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
            }
            return null;
            }

        });
    }

    public void addUser(String login, String pass) throws SQLException{
        System.out.append("UsersDAO: addUser ").append(login).append(".\n");

        TExecutor exec = new TExecutor();
        exec.execUpdate(con, "insert into users (`login`, `pass`) values ('" +
                login + "', '" + pass + "');\n");
        return;
    }

    public void deleteUserById(Long id) throws SQLException{
        System.out.append("UsersDAO: deleteUserById ").append(id.toString()).append(".\n");

        TExecutor exec = new TExecutor();
        exec.execUpdate(con, "delete from users where `id` = " + id.toString() + ";\n");
        return;
    }

    public void setUserLoginById(Long id, String loginNew) throws SQLException{
        System.out.append("UsersDAO: setUserLoginById ").append(id.toString()).append(".\n");

        TExecutor exec = new TExecutor();
        exec.execUpdate(con, "update users set `login` = '" + loginNew + "' where id = "
                + id.toString() + ";\n");
        return;
    }

    public void initTable() throws SQLException{
        System.out.append("UsersDAO: initTable.\n");

        TExecutor exec = new TExecutor();
        exec.execUpdate(con, "create table `users` (`id` bigint(20) auto_increment not null, `login` varchar(255) not null, `pass`" +
                " varchar(256) not null, primary key(`id`), unique key (`login`)) charset = utf8;");
        System.out.append("UsersDAO: table created!\n");
        return;
    }

    public void initTableData() throws SQLException{
        System.out.append("UsersDAO: initTableData.\n");

        TExecutor exec = new TExecutor();
        exec.execUpdate(con, "insert into users (`login`, `pass`) values ('tully', 'hello');\n");
        System.out.append("UsersDAO: User added\n");
        return;
    }

    public void dropTable() throws SQLException{
        System.out.append("UsersDAO: dropTable.\n");

        TExecutor exec = new TExecutor();
        exec.execUpdate(con, "drop table users");
        System.out.append("UsersDAO: table dropped!\n");
        return;
    }
}
