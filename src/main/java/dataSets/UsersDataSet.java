package dataSets;

public class UsersDataSet {
	private Long id;
    private String login;
    private String password;
	
	public UsersDataSet(Long id, String login, String password){
		this.id = id;
        this.login = login;
        this.password = password;
	}


    public long getId() {
        return id;
    }

    /*public String getLogin() {
        return login;
    }*/

    public String getPassword() {
        return password;
    }

}
