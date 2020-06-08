package Semicolon.project;

import java.io.IOException;

public class GameManager {
	City []city=new City[40];//32
	
	Main main;
	
	public GameManager(Main main) {
		this.main = main;
		for (int i = 0; i < city.length; i++) {
			if(i==5 || i==9 || i==19 || i==29 || i==39)
			city[i]=new City(main);
			else
			city[i]=new City(true,main);
		}
		city[0].setCityName("타이베이");
		city[1].setCityName("서안");
		city[2].setCityName("홍콩");
		city[3].setCityName("마닐라");
		city[4].setCityName("제주도");
		city[5].setCityName("가위바위보게임");//게임

		city[6].setCityName("싱가폴");
		city[7].setCityName("카이로");
		city[8].setCityName("이스탄불");
		city[9].setCityName("지렁이게임");//게임 
		city[10].setCityName("알레스카");
		city[11].setCityName("방콕");
		city[12].setCityName("리스본");
		city[13].setCityName("하와이");
		city[14].setCityName("부산");
		city[15].setCityName("시드니");
		city[16].setCityName("상파울루");
		city[17].setCityName("몬트리올");
		city[18].setCityName("오타와"); //게임
		city[19].setCityName("숫자야구게임");//우주여행
		city[20].setCityName("베를린");
		city[21].setCityName("취리이");
		city[22].setCityName("LA");//게임
		city[23].setCityName("스톡홀롬");
		city[24].setCityName("코펜하겐");
		city[25].setCityName("도쿄");
		city[26].setCityName("아테네");
		city[27].setCityName("서울");
		city[28].setCityName("뉴욕");
		city[29].setCityName("스도쿠게임"); // 무인도
		city[30].setCityName("런던");
		city[31].setCityName("로마");
		city[32].setCityName("파리");
		city[33].setCityName("이탈리아");
		city[34].setCityName("중국");//게임
		city[35].setCityName("영국");
		city[36].setCityName("아이슬란드");
		city[37].setCityName("토론토");
		city[38].setCityName("벤쿠버");
		city[39].setCityName("출발지!");// 출발,시작점
	}
	
	public void showCity(Mal mal,int position) {

//		City배열은 0번부터 시작이므로 1을빼준다
		position -= 1;
		
		try {
			main.getNetwork().getOos().writeObject("[Status1]("+mal.getTeam()+") -> "+city[position].getCityName() + "에 도착하여 결정하는 중입니다");
		} catch (IOException e) {
			e.printStackTrace();
		}
		city[position].showDialog(mal);
	}

	public City[] getCity() {
		return city;
	}

	public void setCity(City[] city) {
		this.city = city;
	}
	
	
	
	

}
