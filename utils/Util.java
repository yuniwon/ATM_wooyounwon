package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import account.Account;
import account.AccountDAO;
import client.Client;
import client.ClientDAO;

public class Util {

	private static Scanner sc = new Scanner(System.in);
	private final static String CUR_PATH = System.getProperty("user.dir") + "\\src\\ATM\\";

	//account.txt , client.txt
	//임시데이터
	public static void tempData(AccountDAO accDAO, ClientDAO cliDAO) {
		String userdata = "1001/test01/pw1/김철수\n";
		userdata += "1002/test02/pw2/이영희\n";
		userdata += "1003/test03/pw3/신민아\n";
		userdata += "1004/test04/pw4/최상민\n";
		
		String accountdata = "1001/test01/1111-1111-1111/8000\n";
		accountdata += "1002/test02/2222-2222-2222/5000\n";
		accountdata += "1001/test01/3333-3333-3333/11000\n";
		accountdata += "1003/test03/4444-4444-4444/9000\n";
		accountdata += "1001/test01/5555-5555-5555/5400\n";
		accountdata += "1002/test02/6666-6666-6666/1000\n";
		accountdata += "1003/test03/7777-7777-7777/1000\n";
		accountdata += "1004/test04/8888-8888-8888/1000";

		accDAO.addAccountsFromData(accountdata);
        cliDAO.addClientsFromData(userdata);

	}

    //숫자 입력
	public static int getValue(String msg, int start, int end) {
        while(true) {
            System.out.printf("▶ %s[%d-%d] 입력 :",msg, start, end);
            try {
                int num = sc.nextInt();
                sc.nextLine();
                if(num < start || num > end) {
                    System.out.printf("%d - %d 사이 값 입력해주세요 %n", start, end);
                    continue;
                }
                return num;
            }catch (Exception e) {
                sc.nextLine();
                System.out.println("숫자값을 입력하세요");
            }
        }
        
    }

    // 문자열 입력
    public static String getValue(String msg) {
        System.out.printf("▶ %s 입력 : ", msg);
        
        return sc.next();
        
    }
    // 파일 읽기
    private static String loadFile(String fileName){
        String data = "";
        try(FileReader fr = new FileReader(CUR_PATH + fileName); BufferedReader br = new BufferedReader(fr);) {
                String line = "";
                while((line = br.readLine()) != null) {
                    data += line + "\n";
                }
                data = data.substring(0, data.length()-1);
                System.out.println(fileName + " 데이터 로드 완료");
            }catch (Exception e) {
                e.printStackTrace();
                System.out.println(fileName + "데이터 로드 실패");
            }

            return data;
    }

    //파일이 없을때 생성
    private static void fileInit(String fileName) {
		File file = new File(CUR_PATH + fileName);
        System.out.println(CUR_PATH);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("파일 생성 실패");
				e.printStackTrace();
			}
		}
	}
    //파일에서 데이터 읽기
    public static void laodFromFile(AccountDAO accDAO, ClientDAO cliDAO) {
		String accData =  loadFile("account.txt");
		String cliData =  loadFile("client.txt");
        accDAO.addAccountsFromData(accData);
        cliDAO.addClientsFromData(cliData);
    }
//파일저장
    public static void saveData(String string, String data) {
        try(FileWriter fw = new FileWriter(CUR_PATH + string);) {
            fw.write(data);
            System.out.println(string + "데이터 저장 완료");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(string + "데이터 저장 실패");
        }
    }
//아이디 중복확인
    public static boolean isRealId(String id, Client[] cliList) {
        for(int i = 0; i < cliList.length; i++) {
            if(cliList[i].getId().equals(id)) {
                System.out.println("중복된 아이디입니다.");
                return true;
            }
        }
        return false;
    }
    public static boolean matchAccountNumber(String accNumber) { //계좌확인
        if(!accNumber.matches("\\d{4}-\\d{4}-\\d{4}")) {
            System.out.println("계좌번호 형식이 올바르지 않습니다.");
            return true;
        }
        return false;
    }
    public static int findIdxFromAccountNum(Account[] accList, String accNumber) {
        int cnt = accList.length;
        for(int i = 0; i < cnt; i++) {
            if(accList[i].getAccNumber().equals(accNumber)) {
                return i;
            }
        }
        return -1;
    }
    public static boolean isMyAccount(Account account, int cli) {
        if(account.getClientNo() == cli) {
            return true;
        }
        return false;
    }

}
