package com.example.ddcn;

public class item_baiviet {
	String MaBV;
	String tieude;
	String noidung;
	String ngaytao;
	String ngaycapnhat;
	int luotxem;
	String MaND;
	String MaThePL;
	
	public item_baiviet(String MaBV, String tieude, String noidung, String ngaytao,
    		String ngaycapnhat, int luotxem, String MaND, String MaThePL)
	{
		this.MaBV = MaBV;
		this.tieude = tieude;
		this.noidung = noidung;
		this.ngaytao = ngaytao;
		this.ngaycapnhat = ngaycapnhat;
		this.luotxem = luotxem;
		this.MaND = MaND;
		this.MaThePL = MaThePL;
	}

	public String getMaBV() {
		return MaBV;
	}

	public void setMaBV(String maBV) {
		MaBV = maBV;
	}

	public String getTieude() {
		return tieude;
	}

	public void setTieude(String tieude) {
		this.tieude = tieude;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public String getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNgaycapnhat() {
		return ngaycapnhat;
	}

	public void setNgaycapnhat(String ngaycapnhat) {
		this.ngaycapnhat = ngaycapnhat;
	}

	public int getLuotxem() {
		return luotxem;
	}

	public void setLuotxem(int luotxem) {
		this.luotxem = luotxem;
	}

	public String getMaND() {
		return MaND;
	}

	public void setMaND(String maND) {
		MaND = maND;
	}

	public String getMaThePL() {
		return MaThePL;
	}

	public void setMaThePL(String maThePL) {
		MaThePL = maThePL;
	}
}
