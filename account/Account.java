package account;
// 한 계정마다 계좌 3개까지 만들 수 있음
public class Account {
	private int clientNo;
	private String clientId;
	private String accNumber;
	private int money;

	Account(int clientNo,String clientId, String accNumber, int money) {
		this.clientNo = clientNo;
		this.clientId = clientId;
		this.accNumber = accNumber;
		this.money= money;
	}
	
	
	public int getClientNo() {
		return clientNo;
	}


	public String getClientId() {
		return clientId;
	}



	public String getAccNumber() {
		return accNumber;
	}


	public int getMoney() {
		return money;
	}


	public void setMoney(int money) {
		this.money = money;
	}


	public void print() {
		System.out.println(clientNo + "/ " + clientId + "/ " + accNumber + "/ " + money);
	}
}
