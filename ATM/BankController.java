package ATM;

public class BankController {
	AccountDAO accDAO;
	ClientDAO cliDAO;
	Util sc;
	int idx;
	int start;
	int end;
	String bankName;
	BankController(){
		accDAO = new AccountDAO();
		cliDAO = new ClientDAO();
		sc = new Util();
		idx = -1;
		start = 0;
		end = 3;
		bankName = "우연뱅크";
		sc.tempData(accDAO, cliDAO);
		
	}
	void init(){
		
	}
	// [1]관리자 [2]사용자 [0]종료
	//관리자
	// [1] 회원목록 [2] 회원수정 [3]회원 삭제 [4]데이터 저장 [5]데이터 불러오기
	// 회원수정 : 회원 아이디로 검색 . 비밀번호 , 이름 수정가능
	// 회원삭제 : 회원 아이디
	// 데이터 저장 : account.txt , client.txt
	
	// 사용자메뉴
	// [1] 회원가입 [2] 로그인 [0] 뒤로가기
	// 회원가입 : 회원 아이디 중복 확인
	
	// 로그인메뉴
	// [1] 계좌추가 [2] 계좌삭제 [3] 입금 [4] 출금 [5] 이체 [6]탈퇴 [7]마이페이지 [0]로그아웃
	// 계좌추가 : 숫자4개-숫자4개-숫자4개 일치할때 추가가능
	// 계좌삭제 : 본인 회원 계좌만 삭제 가능
	// 입금 : account에 계좌가 존재할때만 입금가능 :100이상 입금/이체/출금 : 계좌잔고만큼만 가능
	// 이체 : 잔고 내에서만 이체가능 본인 계좌사이에서 이체 가능.동일계좌 이체불가능
	// 탈퇴 : 비밀번호 다시 입력받아서 탈퇴가능. 
	// 마이페이지 : 내 계좌목록/잔고 확인
	void bankName() {
		System.out.println("=======" + bankName +"=======");
	}
	void mainMenu() {
		//초기화면
		System.out.printf("[1]관리자 %n[2]사용자 %n[0]종료%n");	
	}
	void admin(){  // 관리자메뉴
		System.out.printf("[1] 회원목록 %n[2] 회원수정 %n[3]회원 삭제 %n[4]데이터 저장 %n[5]데이터 불러오기%n");
	}
	void user() { // 사용자 메뉴
		if(idx == -1) {
		System.out.printf("[1] 회원가입 %n[2] 로그인 %n[0] 뒤로가기%n");
			return;
		} // 로그인 후
		System.out.printf("[1] 계좌추가 %n[2] 계좌삭제 %n[3] 입금 %n[4] 출금 %n[5]이체 %n[6]탈퇴 %n[7]마이페이지 [0]로그아웃%n");
	}
	void run() { //주 실행메소드
		
		while (true) {
			bankName();
			mainMenu(); //초기
			
			int sel = sc.getValue("메뉴 선택", start, end); // 메뉴 사용자 입력
			if(sel == 0) { 
				System.out.println("프로그램 종료");
				break;
			}else if(sel == 1){ //관리자 모드
				start = 0;
				end = 5;
				//관리자
				while(true) {
					bankName();
					admin(); //관리자 화면출력
					sel = sc.getValue("메뉴 선택", start, end);
					if(sel == 0) {
						break;
					}else if(sel == 1) {
						//회원목록
						cliDAO.printClientList();
					}else if(sel == 2) {
						//회원수정
						cliDAO.updateClient();
					}else if(sel == 3) {
						//회원삭제
						cliDAO.deleteClient(accDAO);
					}else if(sel == 4) {
						//데이터저장
						cliDAO.saveClientData();
						accDAO.saveAccountData();
					}else if(sel == 5) {
						//데이터불러오기
						sc.laodFromFile(accDAO, cliDAO);
					}
				}
				continue;
			}else if(sel == 2){
				//사용자
				start = 0;
				end = 2;
				while(true){
					bankName();
					user();
					sel = sc.getValue("사용자 메뉴 선택", start, end);
					if(sel == 0) {
						idx = -1;
						break;
					}
					if(idx == -1) {
						if(sel == 1) {
						//회원가입
						cliDAO.addClient();
						}else if(sel == 2) {
						//로그인
						idx = cliDAO.login();
						if(idx == -1) {
							continue;
						}
						System.out.println("로그인 성공");
						end = 7;
						}
						continue;
					}
					if(sel == 1) {
						//계좌추가
						accDAO.addAccount(cliDAO.cliList[idx]);
					}else if(sel == 2) {
						//계좌삭제
						accDAO.deleteAccount(cliDAO.cliList[idx]);
					}else if(sel == 3) {
						//입금
						accDAO.deposit(cliDAO.cliList[idx]);
					}else if(sel == 4) {
						//출금
						accDAO.withdraw(cliDAO.cliList[idx]);
					}else if(sel == 5) {
						//이체
						accDAO.transfer(cliDAO.cliList[idx]);
					}else if(sel == 6) {
						//탈퇴
						idx = cliDAO.deleteClient(accDAO, idx);
					}else if(sel == 7) {
						//마이페이지
						cliDAO.myPage(accDAO, idx);
					}
					
					

				}
				continue;
			}

			break;
		}
	}
	

	
}
