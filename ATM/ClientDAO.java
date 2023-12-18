package ATM;

public class ClientDAO {
public Client[] cliList;
private int cnt;


private ClientDAO() {
	
}
private static ClientDAO instance = new ClientDAO();

public static ClientDAO getInstance() {
	
	return instance;
}

    public void addClientsFromData(String cliData) {
        String[] temp = cliData.split("\n");
        cnt = temp.length;
        cliList = new Client[cnt];
        for(int i = 0; i < cnt;i++) {
            String[] temp2 = temp[i].split("/");
            cliList[i] = new Client(Integer.parseInt(temp2[0]),temp2[1],temp2[2],temp2[3]);
        }
    }

    public void printClientList() {
        for(int i = 0; i < cnt; i++) {
            System.out.printf("%d. %s %s %s %n",cliList[i].getClientNo(),cliList[i].getId(),cliList[i].getPw(),cliList[i].getName());
        }
    }

    public void updateClient() {
        
        String id = Util.getValue("수정할 회원 아이디를 입력하세요");
        int idx = -1;
        for(int i = 0; i < cnt; i++) {
            if(cliList[i].getId().equals(id)) {
                idx = i;
                break;
            }
        }
        if(idx == -1) {
            System.out.println("해당 회원이 존재하지 않습니다.");
            return;
        }
        System.out.println("수정할 회원 정보를 입력하세요");
        String pw = Util.getValue("비밀번호");
        String name = Util.getValue("이름");
        cliList[idx].setPw(pw);
        cliList[idx].setName(name);
        System.out.println("회원정보가 수정되었습니다.");
        System.out.println(cliList[idx].getId() + " " + cliList[idx].getPw() + " " + cliList[idx].getName());
    }

    void deleteClient(AccountDAO adao) {
        String id = Util.getValue("삭제할 회원 아이디를 입력하세요");
        int idx = -1;
        for(int i = 0; i < cnt; i++) {
            if(cliList[i].getId().equals(id)) {
                idx = i;
                break;
            }
        }
        if(idx == -1) {
            System.out.println("해당 회원이 존재하지 않습니다.");
            return;
        }
        System.out.println("정말로 삭제하시겠습니까? 예 : 0, 아니오 : 1");
        int sel = Util.getValue("선택", 0, 1);
        if(sel == 0) {
            adao.deleteAccountFromOneClient(cliList[idx]);
            deleteClient(idx);
            System.out.println("회원이 삭제되었습니다.");
        }else {
            System.out.println("회원삭제가 취소되었습니다.");
        }
    }

    void saveClientData() {
    	if(cnt == 0) {
    		System.out.println("저장할 데이터가 없습니다");
    		return;
    	}
        String data = "";
        for(int i = 0; i < cnt; i++) {
            data += cliList[i].getClientNo() + "/" + cliList[i].getId() + "/" + cliList[i].getPw() + "/" + cliList[i].getName() + "\n";
        }
        data = data.substring(0, data.length() -1);
        Util.saveData("client.txt", data);
    }

    public void addClient() {
        String id = Util.getValue("아이디");
        if(cliList != null && Util.isRealId(id, cliList)) return;
        String pw = Util.getValue("비밀번호");
        String name = Util.getValue("이름");
        if(cliList == null) {
            cliList = new Client[1];
            cliList[0] = new Client(1001+cnt,id,pw,name);
            cliList[0].print();
            cnt++;
            System.out.println("회원가입이 완료되었습니다.");
            return;
        }
        Client[] temp = new Client[cnt+1];
        for(int i = 0; i < cnt; i++) {
            temp[i] = cliList[i];
        }
        temp[cnt] = new Client(1001+cnt,id,pw,name);
        temp[cnt].print();
        cliList = temp;
        cnt++;
        System.out.println("회원가입이 완료되었습니다.");
    }

    public int login() {
        String id = Util.getValue("아이디");
        String pw = Util.getValue("비밀번호");
        int idx = -1;
        for(int i = 0; i < cnt; i++) {
            if(cliList[i].getId().equals(id) && cliList[i].getPw().equals(pw)) {
                idx = i;
                return idx;
            }
        }
        System.out.println("아이디 또는 비밀번호가 일치하지 않습니다.");
        return idx;
    }

    public int deleteClient(AccountDAO adao,int idx) {
        String pw = Util.getValue("비밀번호 확인");
        if(!pw.equals(cliList[idx].getPw())) {
            System.out.println("비밀번호가 일치하지 않습니다.");
            return idx;
        }
        System.out.println("정말로 탈퇴하시겠습니까? 예 : 0, 아니오 : 1");
        int sel = Util.getValue("선택", 0, 1);
        if(sel == 0) {
            adao.deleteAccountFromOneClient(cliList[idx]);
            deleteClient(idx);
            System.out.println("회원이 탈퇴되었습니다.");
            return -1;
        }
            System.out.println("회원탈퇴가 취소되었습니다.");
       
            return idx;
    }
    public void deleteClient(int idx){
         Client[] temp = new Client[cnt-1];
            int j = 0;
            for(int i = 0; i < cnt; i++) {
                if(i != idx) {
                    temp[j] = cliList[i];
                    j++;
                }
            }
            cliList = temp;
            cnt--;
    }

    public void myPage(AccountDAO accDAO, int idx) {
        System.out.println("아이디 : " + cliList[idx].getId());
        System.out.println("비밀번호 : " + cliList[idx].getPw());
        System.out.println("이름 : " + cliList[idx].getName());
        accDAO.printAccountListFromOneClient(cliList[idx]);
    }

}
