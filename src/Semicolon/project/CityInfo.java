package Semicolon.project;

import java.io.Serializable;

public class CityInfo implements Serializable{
	
	String cityName;
	int passfare;// 통행료
	boolean hotel, building, villa, land;// 유무
	
	boolean isHotel, isBuilding,isVilla; // 체크상태
	Mal mal;// 땅 주인
	
	public CityInfo(String cityName, int passfare, boolean hotel, boolean building, boolean villa, boolean land,
			boolean isHotel,boolean isBuilding, boolean isVilla,
			 Mal mal) {
		super();
		this.cityName = cityName;
		this.passfare = passfare;
		this.hotel = hotel;
		this.building = building;
		this.villa = villa;
		this.land = land;
		
		this.isVilla = isVilla;
		this.isHotel = isHotel;
		this.isBuilding = isBuilding;
		this.mal = mal;
	}

	
	
}
