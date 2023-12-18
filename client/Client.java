package client;

public class Client {

	private int clientNo;
	private String id;
	private String pw;
	private String name;

	public Client(int clientNo, String id, String pw, String name) {
		this.clientNo = clientNo;
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	
	
	public int getClientNo() {
		return clientNo;
	}


	public String getId() {
		return id;
	}


	public String getPw() {
		return pw;
	}


	public void setPw(String pw) {
		this.pw = pw;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void print(){
		System.out.println(clientNo + " " + id + " " + pw + " " + name);
	}
	
}
