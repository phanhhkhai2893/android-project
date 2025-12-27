package com.example.ddcn;

public class item_thepl {
	String MaThePL;
	String tenpl;
	String mota;

	public item_thepl(String MaThePL, String tenpl, String mota)
	{
		this.MaThePL = MaThePL;
		this.tenpl = tenpl;
		this.mota = mota;
	}

	public String getMaThePL() {
		return MaThePL;
	}

	public void setMaThePL(String maThePL) {
		MaThePL = maThePL;
	}

	public String getTenpl() {
		return tenpl;
	}

	public void setTenpl(String tenpl) {
		this.tenpl = tenpl;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}
	
	@Override
    public String toString() {
        return tenpl; 
    }
}
