package com.example.ddcn;

public class item_binhluan {
	String MaBL;
	String noidung;
	String ngaytao;
	String MaND;
	String MaBV;

	public item_binhluan(String MaBL, String noidung, String ngaytao,
    		String MaND, String MaBV)
	{
		this.MaBL = MaBL;
		this.noidung = noidung;
		this.ngaytao = ngaytao;
		this.MaND = MaND;
		this.MaBV = MaBV;
	}

	public String getMaBL() {
		return MaBL;
	}

	public void setMaBL(String maBL) {
		MaBL = maBL;
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

	public String getMaND() {
		return MaND;
	}

	public void setMaND(String maND) {
		MaND = maND;
	}

	public String getMaBV() {
		return MaBV;
	}

	public void setMaBV(String maBV) {
		MaBV = maBV;
	}
}
