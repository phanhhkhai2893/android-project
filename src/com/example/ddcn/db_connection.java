package com.example.ddcn;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class db_connection extends SQLiteOpenHelper {

	public db_connection(Context context) {
		super(context, "ddcn.db", null, 1);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		// TODO Auto-generated method stub

		sqLiteDatabase.execSQL(create_nguoi_dung());
        sqLiteDatabase.execSQL(create_bai_viet());
        sqLiteDatabase.execSQL(create_the_phan_loai());
        sqLiteDatabase.execSQL(create_binh_luan());
	}
	
	public int get_row_count(String tableName) {
	    SQLiteDatabase db = this.getReadableDatabase();
	    int count = 0;

	    String countQuery = "SELECT COUNT(*) FROM " + tableName;


	    Cursor cursor = db.rawQuery(countQuery, null);


	    if (cursor != null && cursor.moveToFirst()) {
	        count = cursor.getInt(0); 
	    }

	    if (cursor != null) {
	        cursor.close();
	    }
	    db.close();
	    
	    return count;
	}
	
	public void executeSql(String sqlQuery) {

	    SQLiteDatabase database = null; 
	    
	    try {
	        database = getWritableDatabase();

	        database.beginTransaction();

	        database.execSQL(sqlQuery);

	        database.setTransactionSuccessful();

	    } catch (Exception e) {

	        Log.e("SQLiteExecuteError", "LỖI khi thực thi SQL: " + sqlQuery, e);

	    }
	}
	
//	====================	BẢNG NGƯỜI DÙNG	====================	//
	public String create_nguoi_dung()
	{
		String sql = "";

        sql = "CREATE TABLE if not exists nguoi_dung (";
        sql += "MaND TEXT PRIMARY KEY, ";
        sql += "username TEXT UNIQUE, ";
        sql += "password TEXT NOT NULL, ";
        sql += "email TEXT UNIQUE, ";
        sql += "hoten TEXT, ";
        sql += "avarta TEXT, ";
        sql += "ngaytao TEXT )";
        
		return sql;
	}

    public Cursor get_nguoi_dung()
    {
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.query("nguoi_dung", null, null, null, null, null, null);
        return c;
    }
    
    public item_nguoidung getUser(String MaND) {
        SQLiteDatabase database = getReadableDatabase();
        item_nguoidung user = null;
        Cursor c = null;

        String[] columns = {"MaND", "username", "password", "email", "hoten", "avarta", "ngaytao"};
        String selection = "MaND = ?";
        String[] selectionArgs = new String[]{MaND};

        try {
            c = database.query("nguoi_dung", columns, selection, selectionArgs, null, null, null);

            if (c != null && c.moveToFirst()) {
                int maNDIndex = c.getColumnIndex("MaND");
                int usernameIndex = c.getColumnIndex("username");
                
                user = new item_nguoidung(
                    c.getString(maNDIndex),
                    c.getString(usernameIndex),
                    c.getString(c.getColumnIndex("password")),
                    c.getString(c.getColumnIndex("email")),
                    c.getString(c.getColumnIndex("hoten")),
                    c.getString(c.getColumnIndex("avarta")),
                    c.getString(c.getColumnIndex("ngaytao"))
                );
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Lỗi khi lấy dữ liệu người dùng: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return user;
    }
    
    public item_nguoidung getUserByUsn(String USN) {
        SQLiteDatabase database = getReadableDatabase();
        item_nguoidung user = null;
        Cursor c = null;

        String[] columns = {"MaND", "username", "password", "email", "hoten", "avarta", "ngaytao"};
        String selection = "username = ?";
        String[] selectionArgs = new String[]{USN};

        try {
            c = database.query("nguoi_dung", columns, selection, selectionArgs, null, null, null);

            if (c != null && c.moveToFirst()) {
                int maNDIndex = c.getColumnIndex("MaND");
                int usernameIndex = c.getColumnIndex("username");
                
                user = new item_nguoidung(
                    c.getString(maNDIndex),
                    c.getString(usernameIndex),
                    c.getString(c.getColumnIndex("password")),
                    c.getString(c.getColumnIndex("email")),
                    c.getString(c.getColumnIndex("hoten")),
                    c.getString(c.getColumnIndex("avarta")),
                    c.getString(c.getColumnIndex("ngaytao"))
                );
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Lỗi khi lấy dữ liệu người dùng: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return user;
    }

    public String load_nguoi_dung(String MaND)
    {
        SQLiteDatabase database = getReadableDatabase();
        String data = "";
        
        Cursor c = database.query("nguoi_dung", null, "MaND=?", new String[]{MaND}, null, null, null);
        c.moveToFirst();
        while(c.isAfterLast() == false) {
        	data += "Mã người dùng: " + c.getString(0) + "\n"; // MaND
            data += "Username: " + c.getString(1) + "\n"; // username
            data += "Password: " + c.getString(2) + "\n"; // password
            data += "Email: " + c.getString(3) + "\n"; // email
            data += "Họ tên: " + c.getString(4) + "\n"; // hoten
            data += "Ngày tạo: " + c.getString(6); // ngaytao
            data += "\n";
            c.moveToNext();
        }
        c.close();
        
        return data;
    }
    
    public void clear_nguoi_dung() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("nguoi_dung", null, null);
    }

    public void insert_nguoi_dung(String MaND, String username, String password, String email,
    		String hoten, String avarta, String ngaytao)
    {
		SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put("MaND", MaND);
        values.put("username", username);
        values.put("password", password);
        values.put("email", email);
        values.put("hoten", hoten);
        values.put("avarta", avarta);
        values.put("ngaytao", ngaytao);
        database.insert("nguoi_dung", null, values);
    }
    
    public int update_nguoi_dung(String MaND, String username, String password, String email, 
            String hoten, String avarta)
	{
		SQLiteDatabase database = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("username", username);
		values.put("password", password);
		values.put("email", email);
		values.put("hoten", hoten);
		values.put("avarta", avarta);
		
		int count = database.update( "nguoi_dung", values, "MaND=?", new String[]{MaND} );
		
		return count;
	}
    
    public int delete_nguoi_dung(String MaND)
    {
        SQLiteDatabase database = getWritableDatabase();
        
        int count = database.delete(
                "nguoi_dung",
                "MaND=?",
                new String[]{MaND}
        );
        
        return count;
    }
    
    public String getMaNguoiDungLonNhat() {
        SQLiteDatabase database = getReadableDatabase();
        String maLonNhat = null;

        String selectQuery = "SELECT MaND FROM nguoi_dung " +
                "ORDER BY CAST(SUBSTR(MaND, 3) AS INTEGER) DESC " +
                "LIMIT 1";

        Cursor c = null;
        try {
            c = database.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                maLonNhat = c.getString(0); 
            }
        } catch (Exception e) {
            Log.e("DBError", "Lỗi khi lấy MaND lớn nhất: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return maLonNhat;
    }
    
    public ArrayList<String> get_DS_MaND() {
        ArrayList<String> ds = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase(); 
        String SELECT_QUERY = "SELECT MaND FROM nguoi_dung"; 
        
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String ma = cursor.getString(0); 
                ds.add(ma);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close(); 

        return ds;
    }
    
//	====================	BẢNG BÀI VIẾT	====================	//
	public String create_bai_viet()
	{
		String sql = "";

        sql = "CREATE TABLE if not exists bai_viet (";
        sql += "MaBV TEXT PRIMARY KEY, ";
        sql += "tieude TEXT NOT NULL, ";
        sql += "noidung TEXT, ";
        sql += "ngaytao TEXT, ";
        sql += "ngaycapnhat TEXT, ";
        sql += "luotxem INTEGER, ";
        sql += "MaND TEXT, ";
        sql += "MaThePL TEXT, ";
        sql += "FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND), ";
        sql += "FOREIGN KEY (MaThePL) REFERENCES THE_PHAN_LOAI(MaThePL))";
        
		return sql;
	}

    public Cursor get_bai_viet()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT MaBV, tieude, noidung, ngaytao, ngaycapnhat, luotxem, MaND, MaThePL FROM bai_viet";
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        return cursor;
    }

    public Cursor get_bai_viet_orderNgayTao()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT MaBV, tieude, noidung, ngaytao, ngaycapnhat, luotxem, MaND, MaThePL FROM bai_viet ORDER BY ngaytao DESC";
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        return cursor;
    }

    public Cursor get_bai_viet_by_KQTK(String tuKhoa) {
        SQLiteDatabase db = this.getReadableDatabase();
        
        String SELECT_QUERY = "SELECT MaBV, tieude, noidung, ngaytao, ngaycapnhat, luotxem, MaND, MaThePL " +
                              "FROM bai_viet " +
                              "WHERE tieude LIKE ? OR noidung LIKE ? " +
                              "ORDER BY ngaytao DESC";
        
        String searchArgs = "%" + tuKhoa + "%";
        
        Cursor cursor = db.rawQuery(SELECT_QUERY, new String[]{searchArgs, searchArgs});
        
        return cursor;
    }
    
    public Cursor get_bai_viet_theo_nd(String MaND)
    {
    	SQLiteDatabase db = this.getReadableDatabase();
    	
    	String SELECT_QUERY = 
                "SELECT MaBV, tieude, noidung, ngaytao, ngaycapnhat, luotxem, MaND, MaThePL " +
                "FROM bai_viet " +
                "WHERE MaND = ?";
                
        String[] selectionArgs = new String[] { MaND };
        
        Cursor cursor = db.rawQuery(SELECT_QUERY, selectionArgs);
        return cursor;
    }

    public String load_bai_viet(String MaBV)
    {
        SQLiteDatabase database = getReadableDatabase();
        String data = "";
        
        Cursor c = database.query("bai_viet", null, "MaBV=?", new String[]{MaBV}, null, null, null);
        c.moveToFirst();
        while(c.isAfterLast() == false) {
        	data += "Mã BV: " + c.getString(0) + "\n"; // MaBV
            data += "Tiêu đề: " + c.getString(1) + "\n"; // tieude
            data += "Nội dung: " + c.getString(2) + "\n"; // noidung
            data += "Ngày tạo: " + c.getString(3) + "\n"; // ngaytao
            data += "Ngày cập nhật: " + c.getString(4) + "\n"; // ngaycapnhat
            data += "Lượt xem: " + c.getInt(5) + "\n"; // luotxem
            data += "Mã ND: " + c.getString(6) + "\n"; // MaND
            data += "Mã Thẻ PL: " + c.getString(7);    // MaThePL
            data += "\n";
            c.moveToNext();
        }
        c.close();
        
        return data;
    }

    public void insert_bai_viet(String MaBV, String tieude, String noidung, String ngaytao,
    		String ngaycapnhat, int luotxem, String MaND, String MaThePL)
    {
		SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put("MaBV", MaBV);
        values.put("tieude", tieude);
        values.put("noidung", noidung);
        values.put("ngaytao", ngaytao);
        values.put("ngaycapnhat", ngaycapnhat);
        values.put("luotxem", luotxem);
        values.put("MaND", MaND);
        values.put("MaThePL", MaThePL);
        database.insert("bai_viet", null, values);
    }
    
    public int update_bai_viet(String MaBV, String tieude, String noidung, String ngaycapnhat, 
            int luotxem, String MaND, String MaThePL)
	{
		SQLiteDatabase database = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("tieude", tieude);
		values.put("noidung", noidung);
		values.put("ngaycapnhat", ngaycapnhat);
		values.put("luotxem", luotxem);
		values.put("MaND", MaND);
		values.put("MaThePL", MaThePL);
		
		int count = database.update( "bai_viet", values, "MaBV=?", new String[]{MaBV} );
		
		return count;
	}
    
    public int delete_bai_viet(String MaBV)
    {
        SQLiteDatabase database = getWritableDatabase();
        
        int count = database.delete(
                "bai_viet",
                "MaBV=?",
                new String[]{MaBV}
        );
        
        return count;
    }
    
    public item_baiviet getBV(String MaBV) {
        SQLiteDatabase database = getReadableDatabase();
        item_baiviet baiViet = null;
        Cursor c = null;

        String[] columns = {"MaBV", "tieude", "noidung", "ngaytao", "ngaycapnhat",
        		"luotxem", "MaND", "MaThePL"};
        String selection = "MaBV = ?";
        String[] selectionArgs = new String[]{MaBV};

        try {
            // Thực hiện truy vấn
            c = database.query("bai_viet", columns, selection, selectionArgs, null, null, null);

            // Kiểm tra và di chuyển đến hàng đầu tiên
            if (c != null && c.moveToFirst()) {
            	baiViet = new item_baiviet(
                    c.getString(c.getColumnIndex("MaBV")),
                    c.getString(c.getColumnIndex("tieude")),
                    c.getString(c.getColumnIndex("noidung")),
                    c.getString(c.getColumnIndex("ngaytao")),
                    c.getString(c.getColumnIndex("ngaycapnhat")),
                    c.getInt(c.getColumnIndex("luotxem")),
                    c.getString(c.getColumnIndex("MaND")),
                    c.getString(c.getColumnIndex("MaThePL"))
                );
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Lỗi khi lấy dữ liệu bài viết: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return baiViet;
    }
    
    public void clear_bai_viet() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("bai_viet", null, null);
    }
    
    public String getMaBaiVietLonNhat() {
        SQLiteDatabase database = getReadableDatabase();
        String maLonNhat = null;

        String selectQuery = "SELECT MaBV FROM bai_viet " +
                "ORDER BY CAST(SUBSTR(MaBV, 3) AS INTEGER) DESC " +
                "LIMIT 1";

        Cursor c = null;
        try {
            c = database.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                maLonNhat = c.getString(0); 
            }
        } catch (Exception e) {
            Log.e("DBError", "Lỗi khi lấy MaBV lớn nhất: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return maLonNhat;
    }

    public ArrayList<String> get_DS_MaBV() {
        ArrayList<String> ds = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase(); 
        String SELECT_QUERY = "SELECT MaBV FROM bai_viet"; 
        
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String ma = cursor.getString(0); 
                ds.add(ma);
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close(); 

        return ds;
    }
    
    
//	====================	BẢNG THẺ PHÂN LOẠI	====================	//
	public String create_the_phan_loai()
	{
		String sql = "";

        sql = "CREATE TABLE if not exists the_phan_loai (";
        sql += "MaThePL varchar(10) PRIMARY KEY, ";
        sql += "tenpl varchar(30) NOT NULL, ";
        sql += "mota TEXT) ";
        
		return sql;
	}

    public Cursor get_the_phan_loai()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT MaThePL, tenpl, mota FROM the_phan_loai";
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        return cursor;
    }
    
    public String load_the_phan_loai(String MaThePL)
    {
        SQLiteDatabase database = getReadableDatabase();
        String data = "";
        
        Cursor c = database.query("the_phan_loai", null, "MaThePL=?", new String[]{MaThePL},
        		null, null, null);

        c.moveToFirst();
        while(c.isAfterLast() == false) {
            data += "Mã thẻ PL: " + c.getString(0) + "\n"; // MaThePL
            data += "Tên PL: " + c.getString(1) + "\n"; // tenpl
            data += "Mô tả: " + c.getString(2);         // mota
            data += "\n";
            
            c.moveToNext();
        }
        c.close();
        
        return data;
    }

    public void insert_the_phan_loai(String MaThePL, String tenpl, String mota)
    {
		SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put("MaThePL", MaThePL);
        values.put("tenpl", tenpl);
        values.put("mota", mota);
        database.insert("the_phan_loai", null, values);
    }
    
    public int update_the_phan_loai(String MaThePL, String tenpl, String mota)
    {
        SQLiteDatabase database = getWritableDatabase();
        
        ContentValues values = new ContentValues();
        values.put("tenpl", tenpl);
        values.put("mota", mota);
        
        int count = database.update( 
            "the_phan_loai", 
            values, 
            "MaThePL=?", 
            new String[]{MaThePL} 
        );
        
        return count;
    }
    
    public int delete_the_phan_loai(String MaThePL)
    {
        SQLiteDatabase database = getWritableDatabase();
        
        int count = database.delete(
                "the_phan_loai",
                "MaThePL=?",
                new String[]{MaThePL}
        );
        
        return count;
    }
    
    public void clear_the_pl() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("the_phan_loai", null, null);
    }
    
    public String getMaPLLonNhat() {
        SQLiteDatabase database = getReadableDatabase();
        String maLonNhat = null;

        String selectQuery = "SELECT MaThePL FROM the_phan_loai " +
                "ORDER BY CAST(SUBSTR(MaThePL, 3) AS INTEGER) DESC " +
                "LIMIT 1";

        Cursor c = null;
        try {
            c = database.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                maLonNhat = c.getString(0); 
            }
        } catch (Exception e) {
            Log.e("DBError", "Lỗi khi lấy MaThePL lớn nhất: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return maLonNhat;
    }
    
    public ArrayList<item_thepl> get_DS_MaPL() 
    {
        ArrayList<item_thepl> ds = new ArrayList<item_thepl>();
        SQLiteDatabase db = this.getReadableDatabase(); 
        String SELECT_QUERY = "SELECT * FROM the_phan_loai"; 
        
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
            	String ma = cursor.getString(cursor.getColumnIndex("MaThePL"));
                String ten = cursor.getString(cursor.getColumnIndex("tenpl"));
                ds.add(new item_thepl(ma, ten, ""));
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close(); 

        return ds;
    }

    public item_thepl getPL(String MaThePL) {
        SQLiteDatabase database = getReadableDatabase();
        item_thepl item_pl = null;
        Cursor c = null;

        String[] columns = {"MaThePL", "tenpl", "mota"};
        String selection = "MaThePL = ?";
        String[] selectionArgs = new String[]{MaThePL};

        try {
            // Thực hiện truy vấn
            c = database.query("the_phan_loai", columns, selection, selectionArgs, null, null, null);

            // Kiểm tra và di chuyển đến hàng đầu tiên
            if (c != null && c.moveToFirst()) {
            	item_pl = new item_thepl(
                    c.getString(c.getColumnIndex("MaThePL")),
                    c.getString(c.getColumnIndex("tenpl")),
                    c.getString(c.getColumnIndex("mota"))
                );
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Lỗi khi lấy dữ liệu thẻ phân loại: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return item_pl;
    }
    
//	====================	BẢNG BÌNH LUẬN	====================	//
	public String create_binh_luan()
	{
		String sql = "";

        sql = "CREATE TABLE if not exists binh_luan (";
        sql += "MaBL varchar(10) PRIMARY KEY, ";
        sql += "noidung TEXT NOT NULL, ";
        sql += "ngaytao TEXT, ";
        sql += "MaND varchar(10), ";
        sql += "MaBV varchar(10), ";
        sql += "FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND), ";
        sql += "FOREIGN KEY (MaBV) REFERENCES BAI_VIET(MaBV))";
        
		return sql;
	}

    public Cursor get_binh_luan()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT MaBL, noidung, ngaytao, MaND, MaBV FROM binh_luan";
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        return cursor;
    }

    public Cursor get_binh_luan_theo_MaBV(String MaBV)
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_QUERY = "SELECT MaBL, noidung, ngaytao, MaND, MaBV " +
        		"FROM binh_luan " +
        		"WHERE MaBV = ?";
                
        String[] selectionArgs = new String[] { MaBV };
        
        Cursor cursor = db.rawQuery(SELECT_QUERY, selectionArgs);
        return cursor;
    }
    
    public String load_binh_luan(String MaBL)
    {
        SQLiteDatabase database = getReadableDatabase();
        String data = "";
        
        Cursor c = database.query("binh_luan", null, "MaBL=?", new String[]{MaBL}, null, null, "ngaytao DESC");

        c.moveToFirst();
        while(c.isAfterLast() == false) {
            data += "Mã bình luận: " + c.getString(0) + "\n"; // MaBL
            data += "Nội dung: " + c.getString(1) + "\n"; // noidung
            data += "Ngày tạo: " + c.getString(2) + "\n"; // ngaytao
            data += "Mã người dùng: " + c.getString(3) + "\n"; // MaND
            data += "Mã bài viết: " + c.getString(4);         // MaBV
            data += "\n";
            
            c.moveToNext();
        }
        c.close();
        
        return data;
    }

    public void insert_binh_luan(String MaBL, String noidung, String ngaytao,
    		String MaND, String MaBV)
    {
		SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put("MaBL", MaBL);
        values.put("noidung", noidung);
        values.put("ngaytao", ngaytao);
        values.put("MaND", MaND);
        values.put("MaBV", MaBV);
        database.insert("binh_luan", null, values);
    }
    
    public int update_binh_luan(String MaBL, String noidung, String MaND, String MaBV)
	{
		SQLiteDatabase database = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put("noidung", noidung);
		values.put("MaND", MaND);
		values.put("MaBV", MaBV);
		
		// Cập nhật bản ghi có MaBL khớp
		int count = database.update( 
			"binh_luan", 
			values, 
			"MaBL=?", 
			new String[]{MaBL} 
		);
		
		return count;
	}
    
    public int delete_binh_luan(String MaBL)
    {
        SQLiteDatabase database = getWritableDatabase();
        
        int count = database.delete(
                "binh_luan",
                "MaBL=?",
                new String[]{MaBL}
        );
        
        return count;
    }

    public void clear_binhLuan() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("binh_luan", null, null);
    }

    public String getMaBLLonNhat() {
        SQLiteDatabase database = getReadableDatabase();
        String maLonNhat = null;

        String selectQuery = "SELECT MaBL FROM binh_luan " +
                "ORDER BY CAST(SUBSTR(MaBL, 3) AS INTEGER) DESC " +
                "LIMIT 1";

        Cursor c = null;
        try {
            c = database.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {
                maLonNhat = c.getString(0); 
            }
        } catch (Exception e) {
            Log.e("DBError", "Lỗi khi lấy MaBL lớn nhất: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return maLonNhat;
    }

    public item_binhluan getBL(String MaBL) {
        SQLiteDatabase database = getReadableDatabase();
        item_binhluan item_bl = null;
        Cursor c = null;

        String[] columns = {"MaBL", "noidung", "ngaytao", "MaND", "MaBV"};
        String selection = "MaBL = ?";
        String[] selectionArgs = new String[]{MaBL};

        try {
            // Thực hiện truy vấn
            c = database.query("binh_luan", columns, selection, selectionArgs, null, null, null);

            // Kiểm tra và di chuyển đến hàng đầu tiên
            if (c != null && c.moveToFirst()) {
            	item_bl = new item_binhluan(
                    c.getString(c.getColumnIndex("MaBL")),
                    c.getString(c.getColumnIndex("noidung")),
                    c.getString(c.getColumnIndex("ngaytao")),
                    c.getString(c.getColumnIndex("MaND")),
                    c.getString(c.getColumnIndex("MaBV"))
                );
            }
        } catch (Exception e) {
            Log.e("DB_ERROR", "Lỗi khi lấy dữ liệu bình luận: " + e.getMessage());
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return item_bl;
    }
    
    @Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
