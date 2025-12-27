package com.example.ddcn;

public class item_nguoidung {
	String MaND;
	String username;
	String password;
	String email;
	String hoten; 
	String avarta;
	String ngaytao;
	
	public item_nguoidung(String MaND, String username, String password, String email,
    		String hoten, String avarta, String ngaytao)
	{
		this.MaND = MaND;
		this.username = username;
		this.password = password;
		this.email = email;
		this.hoten = hoten;
		this.avarta = avarta;
		this.ngaytao = ngaytao;
	}

	public String getMaND() {
		return MaND;
	}

	public void setMaND(String maND) {
		MaND = maND;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHoten() {
		return hoten;
	}

	public void setHoten(String hoten) {
		this.hoten = hoten;
	}

	public String getAvarta() {
		return avarta;
	}

	public void setAvarta(String avarta) {
		this.avarta = avarta;
	}

	public String getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}
}
