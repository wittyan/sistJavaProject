package Semicolon.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;


public class City implements Serializable {
//	40개의 칸중 한칸의 정보를 담고 있는 Class
	
	private String cityName;
	private int passfare;// 통행료
	
//	구매된 상태인지 확인하는 변수
	boolean land = false;
	boolean hotel=false;
	boolean building=false;
	boolean villa=false;  
	
	boolean isCity;// 도시인지 게임인지 확인하는 변수
	private Mal mal;// 땅 주인
	private Mal inputMal;

	BuildDialog buildDialog;
	LandDialog landDialog;

//	현재 띄워질 구매창을 확인하는 용도
	boolean isVilla = true;
	boolean isBuilding = false;
	boolean isHotel = false;

//	구입비용
	private int landF = 20, villaF = 10, buildingF = 30, hotelF = 50;
//	통행료
	private int landP = 2, villaP = 15, buildingP = 60, hotelP = 100;

	Main main;
	
	public void sendInfo() {
		
//		지어진 건물정보와 말의 위치, 돈정보를 보낸다
		CityInfo cityInfo = new CityInfo(cityName,passfare,hotel,building,villa,land,isHotel,isBuilding,isVilla,mal);
		
		Mal mal[] = main.getGamePanel().getMal();
		
		int position[] = new int[mal.length];
		int money[] = new int[mal.length];
		
		for (int i = 0; i < mal.length; i++) {
			position[i] = mal[i].getPosition();
			money[i] = mal[i].getMoney();
		}
		MalInfo malInfo = new MalInfo(position, money);

		try {
			main.getNetwork().getOos().writeObject(malInfo);
			main.getNetwork().getOos().writeObject(cityInfo);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showDialog(Mal mal) {
		
		try {
			inputMal = mal;

		if (isCity) { // 도시일때

			if(this.mal == null) {	// 1. 땅주인 없을때 -> 땅을 사야한다
				landDialog = new LandDialog();
			}
			else if (!this.mal.getTeam().equals(inputMal.getTeam())) {// 2. 자신의 땅이 아닐때 (통행료지불)
				//통행료
				if (hotel) {
					int money = inputMal.getMoney();
					money -= hotelP;
					inputMal.setMoney(money);
					this.mal.setMoney(mal.getMoney()+hotelP);					
					sendInfo();
					main.getNetwork().getOos().writeObject("[Status1]"+inputMal.getTeam()+"팀이 "+this.mal.getTeam()+"팀에게 통행료 "+hotelP+ "을 지불 하였습니다.");
				}
				else if (building) {
					int money = inputMal.getMoney();
					money -= buildingP;
					inputMal.setMoney(money);
					this.mal.setMoney(mal.getMoney()+buildingP);
					sendInfo();
					main.getNetwork().getOos().writeObject("[Status1]"+inputMal.getTeam()+"팀이 "+this.mal.getTeam()+"팀에게 통행료 "+buildingP+ "만원을 지불 하였습니다.");
				}
				else if (villa) { 
					int money = inputMal.getMoney();
					money -= villaP;
					inputMal.setMoney(money);
					this.mal.setMoney(mal.getMoney()+villaP);
					sendInfo();
					main.getNetwork().getOos().writeObject("[Status1]"+inputMal.getTeam()+"팀이 "+this.mal.getTeam()+"팀에게 통행료 "+villaP+ "만원을 지불 하였습니다.");
				}
				else if(land) {
					int money = inputMal.getMoney();
					money -= landP;
					inputMal.setMoney(money);
					this.mal.setMoney(mal.getMoney()+landP);
					sendInfo();
					main.getNetwork().getOos().writeObject("[Status1]"+inputMal.getTeam()+"팀이 "+this.mal.getTeam()+"팀에게 통행료 "+landP+ "만원을 지불 하였습니다.");					
				}
			
			}else if(this.mal.getTeam().equals(inputMal.getTeam())) { // 3. 자신의 땅일때
				buildDialog = new BuildDialog();
				sendInfo();
			}
		}
			 else {
	//		도시가 아닐때 -> 게임
				 if(cityName.equals("스도쿠게임")) {
					new Sudoku(main.getFrame(),inputMal);
				 }else if(cityName.equals("지렁이게임")) {
					new GMain(main.getFrame(),inputMal);
				 }else if(cityName.equals("가위바위보게임")) {
					new Rps(main.getFrame(), inputMal);
				 }else if(cityName.equals("숫자야구게임")) {
					 new DMain(main.getFrame(),inputMal);
				 }
				 sendInfo();
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public class LandDialog implements ActionListener, Serializable {

		JDialog jDialog;

		private JButton yes, no;
		private JLabel label;

		private int width = 700, height = 500;

		public LandDialog() {

			jDialog = new JDialog(main.getFrame(), true);

//			땅을 처음 살때
			if (City.this.mal == null) {
				jDialog.setLayout(null);

				label = new JLabel(cityName + "땅을 구입하시겠습니까?");
				label.setBounds(50, 50, 600, 200);
				label.setFont(new Font("돋움", Font.BOLD, 35));

				label.setForeground(Color.BLACK);

				yes = new JButton("YES");
				yes.setBounds(100, 300, width / 2 - 100, 60);
				yes.setBackground(Color.blue);
				yes.setFont(new Font("돋움", Font.BOLD, 40));
				yes.setForeground(Color.WHITE);
				yes.addActionListener(this);
				no = new JButton("NO");
				no.setBounds(350, 300, width / 2 - 100, 60);
				no.setBackground(Color.blue);
				no.setFont(new Font("돋움", Font.BOLD, 40));
				no.setForeground(Color.WHITE);
				no.addActionListener(this);

				jDialog.add(yes);
				jDialog.add(no);

				jDialog.add(label);

				jDialog.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						jDialog.dispose();
					}

				});

				jDialog.setBackground(Color.BLACK);
				jDialog.setBounds(400, 100, width, height);
				jDialog.setVisible(true);
			}
		}

		
	
		@Override
		public void actionPerformed(ActionEvent e) {

			Object o = e.getSource();

			if (o == yes) {// 땅이 구매됨 ---> GameManager에 저장
				try {
					main.getNetwork().getOos()
							.writeObject("[Status2]" + inputMal.getTeam() + "팀이 " + cityName + " 도시를 구매 하였습니다");
					
					City.this.mal = inputMal; // 들어온 말이 현재 City의 주인이 된다.
					inputMal = null;
					mal.setMoney(mal.getMoney()-landF);
					land = true;
					
//					땅을 샀으니 city정보와 money를 전송한다
					sendInfo();
					
				} catch (Exception e2) {
						e2.printStackTrace();
				}
				jDialog.dispose();
			}
			if (o == no) {
				jDialog.dispose();
			}

		}

	}

	public class BuildDialog implements ActionListener, Serializable {

		private JButton buildyes, buildno, goback;
		private JLabel hotelL, buildingL, villaL;
		private JRadioButton hotel, building, villa;

		private JDialog jDialog;

		private int width = 700, height = 500;

		@Override
		public void actionPerformed(ActionEvent e) {
try {
			Object o = e.getSource();
			if (o == goback) {
//				card.show(this, "buyLand");
			}
			// --> GameManager에 저장되고 라디오 버튼 클릭하고 클릭해야 JOptionpane 활성
			// 1.빌라만 활성화 //2.빌딩만 활성화 //3. 호텔만 활성화
			if (o == buildyes) {
		
				if (villa.isSelected()) {
					int money = mal.getMoney();
					mal.setMoney(money - villaF);		
					isVilla = false;
					isBuilding = true;
					City.this.villa = true;
					
					main.getNetwork().getOos()
					.writeObject("[Status2]" + inputMal.getTeam() + "팀이 빌라를 지었습니다.");
				
				} else if (building.isSelected()) {
					int money = mal.getMoney();
					mal.setMoney(money - buildingF);					
					isBuilding = false;
					isHotel = true;
					City.this.building = true;
					
					main.getNetwork().getOos()
					.writeObject("[Status2]" + inputMal.getTeam() + "팀이 빌딩을 지었습니다.");

					sendInfo();
				} else if (hotel.isSelected()) {
					int money = mal.getMoney();
					mal.setMoney(money - hotelF);
					isBuilding = false;
					isHotel = false;
					City.this.hotel = true;
					
					main.getNetwork().getOos()
					.writeObject("[Status2]" + inputMal.getTeam() + "팀이 호텔을 지었습니다.");

					sendInfo();
				}
				JOptionPane.showMessageDialog(jDialog, "구입이 완료되었습니다.");
				jDialog.dispose();
			}

			if (o == buildno) {
				jDialog.dispose();
			}
		}catch(Exception es) {
			
		}
		}

		public void frame() {

			jDialog = new JDialog(main.getFrame(), true);
//			jDialog = new JDialog();
			jDialog.setBounds(0, 0, width, height);

			ImageIcon imgIcon[] = { new ImageIcon("d:\\hotel.jpg"), new ImageIcon("d:\\building.jpg"),
					new ImageIcon("d:\\villa.jpg"), };
			Image img[] = new Image[3];
			for (int i = 0; i < 3; i++) {

				img[i] = imgIcon[i].getImage();
				img[i] = img[i].getScaledInstance(100, 200, Image.SCALE_SMOOTH);
			}
			hotelL = new JLabel(new ImageIcon(img[0]));
			hotelL.setBounds(100, 60, 100, 200);
			buildingL = new JLabel(new ImageIcon(img[1]));
			buildingL.setBounds(300, 60, 100, 200);
			villaL = new JLabel(new ImageIcon(img[2]));
			villaL.setBounds(500, 60, 100, 200);

			hotel = new JRadioButton();
			hotel.setBounds(140, 280, 20, 20);
			hotel.addActionListener(this);
			building = new JRadioButton();
			building.setBounds(340, 280, 20, 20);
			building.addActionListener(this);
			villa = new JRadioButton();
			villa.setBounds(540, 280, 20, 20);
			villa.addActionListener(this);

			hotel.setEnabled(true);
			building.setEnabled(true);
			villa.setEnabled(true);
			
			if (isHotel == false) {
				hotel.setEnabled(false);
			}
			if (isBuilding == false) {
				building.setEnabled(false);
			}
			if (isVilla == false) {
				villa.setEnabled(false);
			}

			buildyes = new JButton("살게요");
			buildyes.setBounds(380, 350, 100, 40);
			buildyes.addActionListener(this);
			buildno = new JButton("안살래요");
			buildno.setBounds(500, 350, 100, 40);
			buildno.addActionListener(this);

			jDialog.add(hotelL);
			jDialog.add(buildingL);
			jDialog.add(villaL);

			jDialog.add(hotel);
			jDialog.add(building);
			jDialog.add(villa);

			jDialog.add(buildyes);
			jDialog.add(buildno);

		}

		public BuildDialog() {
			frame();

			jDialog.setLayout(null);
			jDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
			jDialog.setBounds(400, 100, width, height);
			jDialog.setVisible(true);
		}
	}
	

	public boolean isLand() {
		return land;
	}

	public void setLand(boolean land) {
		this.land = land;
	}

	public boolean isCity() {
		return isCity;
	}

	public String getCityName() {
		return cityName;
	}

	public void setisCity(boolean isCity) {
		this.isCity = isCity;
	}

	public City(boolean isCity) {
		super();
		this.isCity = isCity;
	}

	public City(String cityName, boolean isCity) {
		super();
		this.cityName = cityName;
		this.isCity = isCity;
	}

	public City(boolean isCity, Main main) {
		super();
		this.isCity = isCity;
		this.main = main;
	}

	public City(Main main) {
		this.main = main;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Mal getMal() {
		return mal;
	}

	public void setMal(Mal mal) {
		this.mal = mal;
	}

	public boolean isVilla() {
		return isVilla;
	}

	public void setVilla(boolean isVilla) {
		this.isVilla = isVilla;
	}

	public boolean isBuilding() {
		return isBuilding;
	}

	public void setBuilding(boolean isBuilding) {
		this.isBuilding = isBuilding;
	}

	public boolean isHotel() {
		return isHotel;
	}

	public void setHotel(boolean isHotel) {
		this.isHotel = isHotel;
	}

}
