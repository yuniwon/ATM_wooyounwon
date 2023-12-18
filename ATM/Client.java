package ATM;

public class Client {

	int clientNo;
	String id;
	String pw;
	String name;

	Client(int clientNo, String id, String pw, String name) {
		this.clientNo = clientNo;
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	void print(){
		System.out.println(clientNo + " " + id + " " + pw + " " + name);
	}
	
}
