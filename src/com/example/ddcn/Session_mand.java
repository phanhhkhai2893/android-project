package com.example.ddcn;

public class Session_mand {
	private static Session_mand instance;
	private String MaND;
	
	private Session_mand()
	{
		this.MaND = null;
	}
	
	public static Session_mand getInstance() {
		if (instance == null) {
            instance = new Session_mand();
        }
        return instance;
	}
	
	public String getMaND() {
		return MaND;
	}

	public void setMaND(String maND) {
		MaND = maND;
	}
}
