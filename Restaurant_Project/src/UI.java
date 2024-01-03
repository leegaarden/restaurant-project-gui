import java.util.*;
import java.io.*;
 

public class UI {
	
	public static void main (String[] args) {
		
		try {
			Scanner scan = new Scanner(System.in); //스캐너 생성
			//Table table = new Table();
			Restaurant restaurant = new Restaurant();
			
			//테이블, 메뉴 읽어오기 
			try {	
								
				FileInputStream tfIn = null;
				ObjectInputStream toIn = null;
				
				tfIn = new FileInputStream("table.dat");
				toIn = new ObjectInputStream(tfIn);
				
				FileInputStream mfIn = null;
				ObjectInputStream moIn = null;
				
				mfIn = new FileInputStream("menu.dat");
				moIn = new ObjectInputStream(mfIn);
				
				restaurant.tableInput(toIn);
				restaurant.menuInput(moIn);
				
				tfIn.close(); moIn.close();
				
				int ts = restaurant.tableList.size();
				int ms = restaurant.menuList.size();
				
				System.out.println("테이블:");
				for (int i = 0; i <ts; i++) {
					System.out.println(restaurant.showTable(i) + "인");
				}
				System.out.println("\n");
				System.out.println("메뉴 아이디, 메뉴 이름, 가격:");
				for (int i = 0; i <ms; i++) {
					System.out.println(restaurant.showMenu(i));
				}
				/*
	            FileInputStream ofIn = null;
	            ObjectInputStream ooIn = null;
	            
	            ofIn = new FileInputStream("order.dat");
	            ooIn = new ObjectInputStream(ofIn);
	   
	            for(int i = 0; i < ts; i++) {
	               if (restaurant.tableList.get(i).getPay() == 0) {
	                  continue;
	               }
	                    restaurant.tableList.get(i).ordersInput(ooIn);
	               }
	            
	            ofIn.close(); ooIn.close();
	            */
				System.out.println("\n");
				System.out.println("<주문 정보>");
				System.out.println("메뉴 아이디, 주문 수량, 가격:");
				
				for (int i = 0; i < ts; i++) {
					int os = restaurant.tableList.get(i).orderList.size();
					System.out.println(restaurant.tableList.get(i).getTableId());
					for (int j = 0; j < os; j++) {
						System.out.println(restaurant.tableList.get(i).showOrder(j));
					}
				}
				
			} catch (ClassNotFoundException e) {
				System.out.println("초기설정인가요?");
				System.out.println("1. 네(파일 없음) 2. 아니요(파일 있음)");
				int i = scan.nextInt();
				
				if (i == 1) {
					System.out.println("설정을 완료하세요.");
				} else {
					System.out.println("파일을 찾을 수 없습니다.");
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("초기설정인가요?");
				System.out.println("1. 네(파일 없음) 2. 아니요(파일 있음)");
				int i = scan.nextInt();
				
				if (i == 1) {
					System.out.println("설정을 완료하세요.");
				} else {
					System.out.println("파일을 찾을 수 없습니다.");
				}
				
			} catch (IOException e) {
				System.out.println("파일은 있지만 읽는 과정에서 에러가 발생했습니다.");
				System.out.println("프로그램을 종료합니다.");
			} 
		
			while(true) {
				
				System.out.println("-----기능선택창-----");
				System.out.println("0. 설정 완료");
				System.out.println("1. 테이블 검색");
				System.out.println("2. 테이블 추가");
				System.out.println("3. 테이블 삭제");
				System.out.println("4. 메뉴 검색");
				System.out.println("5. 메뉴 추가");
				System.out.println("6. 메뉴 삭제");
				System.out.println("7. 매출 확인");
				System.out.println("output. 파일 저장");
				System.out.println("-----------------");
				System.out.print("기능 선택: ");
				String func = scan.next();
	
				if (func.equals("0")) {
					System.out.println("설정 완료");
					break;				
				}
		
				switch(func) {
				
				
				case "1": //테이블 검색하기 
					System.out.print("검색할 테이블 아이디을 입력하세요: ");
					String tableId1 = scan.next();
					Table searchT = new Table(tableId1);
					
					try {
						restaurant.searchTable(searchT);
						if (restaurant.searchTable(searchT) == -1 ) {
							System.out.println("해당 테이블은 존재하지 않습니다.");
						} else {
							System.out.println("해당 테이블은 존재합니다.");
						}
					} catch (java.lang.NullPointerException NullException) {
						System.out.println("NullException: 해당 테이블 존재 X");
					}
					
					for (int i = 0; i <restaurant.tableList.size(); i++) {
						System.out.println(restaurant.showTable(i) + "인");
					}
					
					System.out.print("\n");
					break;
					
				case "2": //테이블 추가하기 
					System.out.print("추가할 테이블 아이디를 입력하세요: ");
					String tableId2 = scan.next();
					System.out.print("수용할 인원을 입력하세요: ");
					int tableHC = scan.nextInt();
					Table tableAdd = new Table(tableId2, tableHC);
					
					try { 
						restaurant.addTable(tableAdd);
					} catch (Exception OverlapTableException) {
						System.out.println("중복되는 테이블이 있습니다.");
					} 
					
					for (int i = 0; i <restaurant.tableList.size(); i++) {
						System.out.println(restaurant.showTable(i) + "인");
					}
					
					
					System.out.print("\n");
					break;
					
				case "3": //테이블 삭제하기
					System.out.print("삭제할 테이블 아이디를 입력하세요: ");
					String tableId3 = scan.next();
					Table tableDel = new Table(tableId3);
					
					try {
						restaurant.removeTable(tableDel);
					} catch (Exception RemoveTableException) {
						System.out.println("해당 테이블이 없습니다. 다시 확인해주세요.");
					} 
				
					for (int i = 0; i <restaurant.tableList.size(); i++) {
						System.out.println(restaurant.showTable(i) + "인");
					}
					
					System.out.print("\n");
					
					break;
					
				case "4": //메뉴 검색하기 
					System.out.print("검색할 메뉴 아이디를 입력하세요: ");
					String menuId1 = scan.next();
					Menu searchM = new Menu(menuId1);
					
					try {
						restaurant.searchMenu(searchM);
						if (restaurant.searchMenu(searchM) == -1 ) {
							System.out.println("해당 메뉴는 존재하지 않습니다.");
						} else {
							System.out.println("해당 메뉴는 존재합니다.");
						}
					} catch (java.lang.NullPointerException NullException) {
						System.out.println("NullException: 해당 메뉴 존재 X");
					}
					
					System.out.println("메뉴 아이디, 메뉴 이름, 가격:");
					for (int i = 0; i <restaurant.menuList.size(); i++) {
						System.out.println(restaurant.showMenu(i));
					}
					
					System.out.print("\n");
					break;
					
				case "5": //메뉴 추가하기
					System.out.print("추가할 메뉴 아이디를 입력하세요: ");
					String menuId2 = scan.next();
					
					System.out.print("추가할 메뉴 이름을 입력하세요: ");
					String menuName1 = scan.next();
					
					System.out.print("추가할 메뉴 가격을 입력하세요: ");
					int menuPrice1 = scan.nextInt();
					
					Menu menuAdd = new Menu(menuId2, menuName1, menuPrice1);
		
					try { 
						restaurant.addMenu(menuAdd);
					} catch (Exception OverlapMenuException) {
						System.out.println("중복되는 메뉴가 있습니다.");
					}
					
					System.out.println("메뉴 아이디, 메뉴 이름, 가격:");
					for (int i = 0; i <restaurant.menuList.size(); i++) {
						System.out.println(restaurant.showMenu(i));
					}
					
					System.out.print("\n");
					break;
					
				case "6": //메뉴 삭제하기 
					System.out.print("삭제할 메뉴 아이디를 입력하세요: ");
					String menuId3 = scan.next();

					Menu menuDel = new Menu(menuId3);

					try {
						restaurant.removeMenu(menuDel);
					} catch (Exception RemoveMenuException) {
						System.out.println("해당 메뉴는 없습니다. 다시 확인해주세요.");
					}
					
					System.out.println("메뉴 아이디, 메뉴 이름, 가격:");
					for (int i = 0; i <restaurant.menuList.size(); i++) {
						System.out.println(restaurant.showMenu(i));
					}
					
					System.out.print("\n");
					break;
					
				case "7": //총 매출액 조회 
			
					try {
						FileInputStream ttfIn = null;
						DataInputStream ttdIn = null;
						
						ttfIn = new FileInputStream("total.dat");
						ttdIn = new DataInputStream(ttfIn); 
						
						restaurant.totalInput(ttdIn);
						ttfIn.close(); ttdIn.close();
						
						System.out.println("총 매출: " + restaurant.getTotal());
					} catch (FileNotFoundException e) {
						System.out.println("파일을 찾을 수 없습니다.");;
					} catch (IOException e) {
						System.out.print("IOException");
					}
					
					break;
					
				case "output": //파일 저장하기 
					
					try {
						FileOutputStream tfOut = null;
						ObjectOutputStream toOut = null;
						
						tfOut = new FileOutputStream("table.dat");
						toOut = new ObjectOutputStream(tfOut);
						
						FileOutputStream mfOut = null;
						ObjectOutputStream moOut = null;
						
						mfOut = new FileOutputStream("menu.dat");
						moOut = new ObjectOutputStream(mfOut);
						
						restaurant.tableOutput(toOut);
						restaurant.menuOutput(moOut);
						//rfOut.close(); rdOut.close(); //try-catch finally
						
					} catch (FileNotFoundException e) {
						System.out.println("파일을 찾을 수 없습니다.");;
					} catch (IOException e) {
						System.out.println("IOException");
					} 
					
					break;
					
				}
				
			}
			int ts = restaurant.tableList.size();
			int ms = restaurant.menuList.size();
			
			System.out.print("\n");
			System.out.println("====테이블====");
			System.out.println("체크아웃: 인원 0명으로 설정");
			//System.out.println("x: 체크아웃");
			for (int i = 0; i <ts; i++) {
				System.out.println(restaurant.showTable(i) + "인");
			}
			System.out.println("");
			System.out.println("============");
			System.out.println("------메뉴판------");
			System.out.println("0. 체크아웃");
			for (int i = 0; i <ms; i++) {
				System.out.println(restaurant.showMenu(i));
			}
			System.out.println("----------------");	

	        boolean flag = true;
	        while (true) {
	            
	            System.out.println("");
	            System.out.print("인원을 입력하세요: ");
	            int hc = scan.nextInt();
	            
	            if (hc == 0) {
		               System.out.println("영업종료");
		               //flag = false;
		               break;
		        }
	            
	            System.out.print("주문할 테이블 아이디를 입력하세요: ");
	            String tId = scan.next();
	            Table orderT = new Table(tId);
	            int s = restaurant.searchTable(orderT); //인덱스

	            try {
	               if (s == -1 ) {
	                  System.out.println("해당 테이블은 존재하지 않습니다. 다시 확인하시고 주문해주세요.");
	                  continue;
	               } 
	               if (restaurant.searchHc(hc, s) == -1) { //인원 초과하는지 검사 
	                  
	                  System.out.println("인원을 초과했습니다.");
	                  continue;
	               }
	            } catch (java.lang.NullPointerException NullException) {
	               System.out.println("NullException");
	            } catch (java.lang.IndexOutOfBoundsException e) {
	            	System.out.println("IndexOutOfBoundsException");
	            }
	            
	            while (flag == true) {
	               System.out.print("메뉴 아이디를 선택하세요: ");
	               String mId = scan.next();
	               Order o = new Order(mId);
	               int m = restaurant.searchMenu(o);
	               
	               if (mId.equals("0")) {
	                  //결제 및 체크아웃 
	                  System.out.println("결제해드리겠습니다.");
	                  System.out.print("가격은: ");
	                  //int payIndex = restaurant.tableList.get(s).getIndex();
	                  int p = restaurant.tableList.get(s).payTable();
	                  System.out.println(p + "원"); 
	                  //restaurant.tableList.get(s).setPay(0); 
	            
		               try {
		            	   
		            	   //테이블 아웃처리 
		            	   
		            	   FileOutputStream tfOut = null;
						   ObjectOutputStream toOut = null;
						   
						   tfOut = new FileOutputStream("table.dat");
						   toOut = new ObjectOutputStream(tfOut);
							
						   restaurant.tableOutput(toOut);
						   
						 
		                   //주문 파일처리
		                   FileOutputStream ofOut = null;
		                   ObjectOutputStream ooOut = null;
		                  
		                   ofOut = new FileOutputStream("order.dat");
		                   ooOut = new ObjectOutputStream(ofOut);
		                  
		                   for(int i = 0; i < ts; i++) {
		                	    restaurant.tableList.get(i).ordersOutput(ooOut);
		                   }
		                  
		                   //매출 파일처리 
		                   FileOutputStream ttfOut = null;
						   DataOutputStream ttdOut = null;
							
						   ttfOut = new FileOutputStream("total.dat");
						   ttdOut = new DataOutputStream(ttfOut); 
							
						   restaurant.totalOutput(ttdOut);
						   ttfOut.close(); ttdOut.close();
		               
		               } catch (IOException e) {
		                  System.out.println("IOException");
		               } catch (Exception fne) {
		                  System.out.println(fne);
		               } 
	             
	                  break;
	               }
	               
	               try {
	            	   //메뉴 잘못 입력했을 경우
	                  if (m == -1) {
	                     System.out.println("해당 메뉴는 존재하지 않습니다. 다시 확인하시고 주문해주세요.");
	                     continue;
	                  }
	               } catch (java.lang.NullPointerException NullException) {
	                  System.out.println("NullException");
	                  continue;
	               }
	               
	               System.out.print("수량을 입력하세요: ");
	               int oc = scan.nextInt();
	               System.out.println("\n");
	               int menuPrice = restaurant.menuPrice(o);
	               
	               Order orderMenu = new Order(mId, oc, menuPrice);
	               restaurant.tableList.get(s).addOrder(orderMenu);
	               
	            }      
	            
	         }   
			scan.close();
			
		} catch (java.util.InputMismatchException e) {
			System.out.println("올바르게 입력해주세요.");
		}
		
	}
}
