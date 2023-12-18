package account;

import client.Client;
import utils.Util;

public class AccountDAO {
public Account[] accList;
private int cnt;

private AccountDAO() {
	
}
private static AccountDAO intstance = new AccountDAO();
public static AccountDAO getInstance() {
	return intstance;
}
// 데이터로부터 계좌정보를 읽어서 계좌목록에 추가
    public void addAccountsFromData(String accData) {
        String[] temp = accData.split("\n");
        cnt = temp.length;
        accList = new Account[cnt];
        for(int i = 0; i < cnt;i++) {
            String[] temp2 = temp[i].split("/");
            accList[i] = new Account(Integer.parseInt(temp2[0]),temp2[1],temp2[2],Integer.parseInt(temp2[3]));
        }
    }
    public void saveAccountData() {
    	if(cnt == 0) {
    		System.out.println("저장할 데이터가 없습니다");
    		return;
    	}
        String data = "";
        for(int i = 0; i < cnt; i++) {
            data += accList[i].getClientNo() + "/" + accList[i].getClientId() + "/" + accList[i].getAccNumber() + "/" + accList[i].getMoney() + "\n";
        }
        data = data.substring(0, data.length() -1);
        Util.saveData("account.txt", data);
    }
    public void addAccount(Client cli) {
    	//본인 계좌 갯수 확인
    	int accCnt = getAccountCnt(cli);
    	if(accCnt >=3) {
    		System.out.println("최대 3개의 계좌만 개설할 수 있습니다.");
    		return;
    	}
        String accNumber = Util.getValue("계좌번호를 입력하세요");
        //계좌번호 유효성 확인
        if(Util.matchAccountNumber(accNumber)){
            return;
        }
        int idx = Util.findIdxFromAccountNum(accList, accNumber);
        if(idx != -1) {
            System.out.println("이미 존재하는 계좌번호입니다.");
            return;
        }
        Account[] temp = new Account[cnt+1];
        for(int i = 0; i < cnt; i++) {
            temp[i] = accList[i];
        }
        temp[cnt++] = new Account(cli.getClientNo(),cli.getId(),accNumber,0);
        temp[cnt-1].print();
        accList = temp;
        System.out.println("계좌가 생성되었습니다.");

    }
    
	private int getAccountCnt(Client client) {
		// TODO Auto-generated method stub
		cnt = 0;
		for(Account account : accList) {
			if(client.getClientNo() == account.getClientNo()) {
				cnt++;
			}
		}
		return cnt;
	}
	
   public void deleteAccount(Client client) {
        String accNumber = Util.getValue("삭제할 계좌번호를 입력하세요");
        int idx =  Util.findIdxFromAccountNum(accList, accNumber);
        if(idx == -1) {
            System.out.println("해당 계좌가 존재하지 않습니다.");
            return;
        }

        if(!Util.isMyAccount(accList[idx], client.getClientNo())){
            System.out.println("본인의 계좌만 삭제할 수 있습니다.");
            return;
        } 

        Account[] temp = new Account[cnt-1];
        for(int i = 0; i < idx; i++) {
            temp[i] = accList[i];
        }
        for(int i = idx; i < cnt-1; i++) {
            temp[i] = accList[i+1];
        }
        accList = temp;
        cnt--;
        for(int i = 0; i < cnt; i++) {
            accList[i].print();
        }
        System.out.println("계좌가 삭제되었습니다.");

    }
   public void deposit(Client cli) {
        String accNumber = Util.getValue("입금할 계좌번호를 입력하세요");
        int idx = Util.findIdxFromAccountNum(accList, accNumber);
        if(idx == -1) {
            System.out.println("해당 계좌가 존재하지 않습니다.");
            return;
        }
        if(!Util.isMyAccount(accList[idx], cli.getClientNo())){
            System.out.println("본인의 계좌만 입금할 수 있습니다.");
            return;
        }
        int money = Util.getValue("입금할 금액을 입력하세요", 100, 1000000);
        accList[idx].setMoney(money);
        System.out.println("입금이 완료되었습니다.");
        accList[idx].print();
    }
    public void withdraw(Client client) {
        String accNumber = Util.getValue("출금할 계좌번호를 입력하세요");
        int idx = Util.findIdxFromAccountNum(accList, accNumber);
        if(idx == -1) {
            System.out.println("해당 계좌가 존재하지 않습니다.");
            return;
        }
        if(!Util.isMyAccount(accList[idx], client.getClientNo())){
            System.out.println("본인의 계좌만 출금할 수 있습니다.");
             return;
        }
        System.out.println("현재 잔액은 " + accList[idx].getMoney() + "원 입니다.");
        int money = Util.getValue("출금할 금액을 입력하세요", 100, 1000000);
        if(accList[idx].getMoney() < money) {
            System.out.println("잔액이 부족합니다.");
            return;
        }
        accList[idx].setMoney(money);
        System.out.println("출금이 완료되었습니다.");
        accList[idx].print();
    }
   public void transfer(Client client) {
        String accNumber = Util.getValue("출금할 계좌번호를 입력하세요");
        int idx = Util.findIdxFromAccountNum(accList, accNumber);
        if(idx == -1) {
            System.out.println("해당 계좌가 존재하지 않습니다.");
            return;
        }
        if(!Util.isMyAccount(accList[idx], client.getClientNo())){
            System.out.println("본인의 계좌만 이체할 수 있습니다.");
            return;
        }
        System.out.println("현재 잔액은 " + accList[idx].getMoney() + "원 입니다.");
        int money = Util.getValue("이체할 금액을 입력하세요", 100, 1000000);
        if(accList[idx].getMoney() < money) {
            System.out.println("잔액이 부족합니다.");
            return;
        }
        System.out.println("이체할 계좌번호를 입력하세요");
        String accNumber2 = Util.getValue("계좌번호를 입력하세요");
        int idx2 = Util.findIdxFromAccountNum(accList, accNumber2);
        if(idx2 == -1) {
            System.out.println("해당 계좌가 존재하지 않습니다.");
            return;
        }
        accList[idx].setMoney(money); 
        accList[idx2].setMoney(money); 
        System.out.println("이체가 완료되었습니다.");
        accList[idx].print();
        accList[idx2].print();
    }
    public void deleteAccountFromOneClient(Client client) {
        int cnt2 = 0;
        for(int i = 0; i < cnt; i++) {
            if(accList[i].getClientNo() == client.getClientNo()) {
                cnt2++;
            }
        }
        if(cnt2 == 0) {
            System.out.println("삭제할 계좌가 없습니다.");
            return;
        }
        Account[] temp = new Account[cnt-cnt2];
        int j = 0;
        for(int i = 0; i < cnt; i++) {
            if(accList[i].getClientNo() == client.getClientNo()) continue;
            temp[j++] = accList[i];
        }
        accList = temp;
        cnt -= cnt2;
    }
    public void printAccountListFromOneClient(Client client) {
        for(int i = 0; i < cnt; i++) {
            if(accList[i].getClientNo() == client.getClientNo()) {
                accList[i].print();
            }
        }
    }


}
