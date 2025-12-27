package com.example.ddcn;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button btnTaoCSDL, dn, dx, dk;
	db_connection db = new db_connection(this);
	TextView tendn;
	Session_mand S_mand = Session_mand.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("TRANG CHỦ");
        tendn = (TextView) findViewById(R.id.hello);
        
        dn = (Button) findViewById(R.id.btn_dangnhap);
        dn.setOnClickListener(this);
        
        dk = (Button) findViewById(R.id.btn_dk);
        dk.setOnClickListener(this);
        
        dx = (Button) findViewById(R.id.thanhvien_dangxuat);
        dx.setOnClickListener(this);

        btnTaoCSDL = (Button) findViewById(R.id.btn_taoCsdl);
        btnTaoCSDL.setOnClickListener(this);
        
		if (S_mand.getMaND() != null) {
			item_nguoidung user = db.getUser(S_mand.getMaND()); 
			btnTaoCSDL.setVisibility(View.VISIBLE);
			dn.setVisibility(View.INVISIBLE);
			dk.setVisibility(View.INVISIBLE);
			dx.setVisibility(View.VISIBLE);
			tendn.setText("Xin chào " + user.getHoten().toString() + "!");
		} 
		else
		{
			btnTaoCSDL.setVisibility(View.INVISIBLE);
			dn.setVisibility(View.VISIBLE);
			dk.setVisibility(View.VISIBLE);
			dx.setVisibility(View.INVISIBLE);
			tendn.setText("Chào mừng bạn đến với ITS Tech Forum!");
		}

		db.insert_nguoi_dung("ND1", "admin", "123456", "admin@gmail.com", "Phan Hoàng Huy Khải", "avarta_admin", "01/11/2025");
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if (arg0 == btnTaoCSDL)
		{
			taoND();
			taoBV();
			taoPL();
			taoBL();
			
			Toast.makeText(getApplication(), "Tạo CSDL thành công!", Toast.LENGTH_LONG).show();
		}
		if (arg0 == dn)
		{
			Intent intent = new Intent(MainActivity.this, DangNhap.class);
			startActivity(intent);
		}
		if (arg0 == dk)
		{
			Intent intent = new Intent(MainActivity.this, DangKy.class);
			startActivity(intent);
		}
		if (arg0 == dx)
		{
			S_mand.setMaND(null);
			Intent intent = new Intent(MainActivity.this, MainActivity.class);
			finish();
			startActivity(intent);
		}
		
	}
	
	public void taoND()
	{
		if (db.get_row_count("nguoi_dung") > 0)
		{
			db.clear_nguoi_dung();
		}
		db.insert_nguoi_dung("ND1", "admin", "123456", "admin@gmail.com", "Phan Hoàng Huy Khải", "avarta_admin", "01/11/2025");
		db.insert_nguoi_dung("ND2", "user02", "pass02", "user2@email.com", "Nguyễn Văn An", "avarta_cat", "02/11/2025");
		db.insert_nguoi_dung("ND3", "thanhvien3", "pass03", "thanhvien3@gmail.com", "Trần Thị Bình", "avarta_polarbear", "03/11/2025");
		db.insert_nguoi_dung("ND4", "dev4", "devpass", "dev4@code.com", "Lê Hoàng Chung", "avarta_fox", "04/11/2025");
		db.insert_nguoi_dung("ND5", "khach5", "guest5", "khach5@mail.net", "Phạm Minh Đạt", "avarta_penguin", "05/11/2025");
		db.insert_nguoi_dung("ND6", "editor6", "edit6", "editor6@blog.vn", "Võ Thị Em", "avarta_horse", "06/11/2025");
		db.insert_nguoi_dung("ND7", "manager7", "mng789", "manager7@corp.com", "Hoàng Văn Giáp", "avarta_panda", "07/11/2025");
		db.insert_nguoi_dung("ND8", "tester8", "testpass", "tester8@qa.com", "Đỗ Thị Hà", "avarta_monkey", "08/11/2025");
		db.insert_nguoi_dung("ND9", "pro9", "proskill", "pro9@mailserver.com", "Bùi Văn Khoa", "avarta_owl", "09/11/2025");
		db.insert_nguoi_dung("ND10", "client10", "clientpass", "client10@company.org", "Đinh Thị Liên", "avarta_cat", "10/11/2025");
	}
	
	public void taoBV()
	{
		if (db.get_row_count("bai_viet") > 0)
		{
			db.clear_bai_viet();
		}
		// 1. Hardware/Chip - Người đăng: ND2
        db.insert_bai_viet("BV11", 
            "RISC-V: Tương lai của kiến trúc vi xử lý mở?", 
            "Trong khi Intel (x86) và ARM đang thống trị thị trường, RISC-V đang nổi lên như một thế lực mới nhờ tính chất mã nguồn mở (Open Source). \n\nKhác với ARM yêu cầu phí bản quyền đắt đỏ, RISC-V cho phép các công ty tự do tùy biến tập lệnh mà không tốn xu nào. Điều này cực kỳ quan trọng cho các startup IoT và AI.\n\nTuy nhiên, hệ sinh thái phần mềm cho RISC-V vẫn còn non trẻ. Linux chạy ổn, nhưng Windows hay macOS thì chưa. Liệu trong 5 năm tới, chúng ta có thấy một chiếc laptop chạy chip RISC-V hiệu năng cao không? Cùng thảo luận nhé!", 
            "2025-11-04", "2025-11-04", 1200, "ND2", "PL1");

        // 2. Database - Người đăng: ND3
        db.insert_bai_viet("BV12", 
            "Tối ưu tốc độ tải trang thần tốc với Redis Caching", 
            "Nếu ứng dụng của bạn đang query trực tiếp vào MySQL cho mỗi request, đừng hỏi tại sao server lại 'thở oxy' khi lượng truy cập tăng cao.\n\nRedis (Remote Dictionary Server) là giải pháp caching in-memory số 1 hiện nay. Cơ chế là: Khi user hỏi dữ liệu, kiểm tra Redis trước -> Có thì trả về ngay (tốc độ nano giây) -> Không có mới chọc vào DB chính.\n\nCác Use-case phổ biến:\n- Lưu Session người dùng.\n- Làm bảng xếp hạng (Leaderboard) game.\n- Cache kết quả API nặng.\nLưu ý: Redis lưu trên RAM, nên cần có chiến lược Eviction (xóa bớt) hợp lý để không bị tràn bộ nhớ.", 
            "2025-11-05", "2025-11-05", 3400, "ND3", "PL2");

        // 3. Thuật toán - Người đăng: ND4
        db.insert_bai_viet("BV13", 
            "Chinh phục Quy hoạch động (Dynamic Programming) không khó như bạn nghĩ", 
            "Quy hoạch động (DP) luôn là nỗi ám ảnh của sinh viên CNTT mỗi mùa thi hay phỏng vấn thuật toán. Nhưng bản chất của nó chỉ là: Chia bài toán lớn thành các bài toán con nhỏ hơn và LƯU LẠI kết quả của chúng (Memoization).\n\nVí dụ kinh điển: Dãy Fibonacci.\nThay vì đệ quy tính lại F(n-1) và F(n-2) liên tục (độ phức tạp 2^n), ta dùng một mảng để lưu giá trị đã tính. Độ phức tạp giảm xuống O(n).\n\nBí quyết học DP:\n1. Xác định trạng thái bài toán.\n2. Tìm công thức truy hồi.\n3. Xử lý các trường hợp cơ sở (Base cases).\nĐừng học thuộc code, hãy vẽ bảng trạng thái ra giấy!", 
            "2025-11-06", "2025-11-06", 2800, "ND4", "PL3");

        // 4. Ngôn ngữ lập trình - Người đăng: ND5
        db.insert_bai_viet("BV14", 
            "Tại sao Rust lại được Stack Overflow bình chọn là ngôn ngữ được yêu thích nhất 7 năm liền?", 
            "C/C++ cho hiệu năng cực cao nhưng lại dễ dính lỗi quản lý bộ nhớ (Memory Leak, Segmentation Fault). Java/Python an toàn nhưng chậm do Garbage Collector.\n\nRust ra đời để giải quyết cả hai: Nhanh như C++ nhưng an toàn bộ nhớ tuyệt đối nhờ cơ chế 'Ownership' và 'Borrow Checker' ngay lúc compile.\n\nNhược điểm duy nhất: Learning curve (đường cong học tập) cực dốc. Bạn sẽ phải 'đánh nhau' với compiler liên tục trong tháng đầu tiên. Nhưng khi đã code chạy được rồi, thì khả năng cao là nó không có bug runtime. Rất đáng để đầu tư học cho các dự án System Programming.", 
            "2025-11-07", "2025-11-07", 1900, "ND5", "PL4");

        // 5. Bảo mật - Người đăng: ND2
        db.insert_bai_viet("BV15", 
            "SQL Injection: Lỗ hổng cổ đại nhưng vẫn đầy rẫy website dính chưởng", 
            "Năm 2025 rồi nhưng mình vẫn thấy nhiều bạn sinh viên code chức năng đăng nhập kiểu: \n`query = \"SELECT * FROM Users WHERE user = '\" + username + \"'\"`.\n\nĐây là cách nhanh nhất để... bay màu database. Chỉ cần nhập username là `' OR '1'='1`, hacker có thể đăng nhập mà không cần mật khẩu, thậm chí drop luôn bảng dữ liệu.\n\nGiải pháp triệt để:\n1. Luôn dùng Prepared Statements (Tham số hóa câu truy vấn).\n2. Sử dụng các ORM (Hibernate, Entity Framework) thay vì raw SQL.\n3. Validate dữ liệu đầu vào chặt chẽ.\nĐừng bao giờ tin tưởng dữ liệu người dùng nhập vào!", 
            "2025-11-08", "2025-11-08", 4100, "ND2", "PL5");

        // 6. Design Pattern - Người đăng: ND3
        db.insert_bai_viet("BV16", 
            "Singleton Pattern: Nên dùng hay là Anti-Pattern?", 
            "Singleton đảm bảo một class chỉ có duy nhất một instance và cung cấp điểm truy cập toàn cục. Nó thường dùng cho: Database Connection, Logger, Configuration Manager.\n\nTuy nhiên, nhiều Senior Dev coi đây là Anti-Pattern vì:\n- Vi phạm nguyên tắc Single Responsibility (vừa quản lý logic, vừa quản lý vòng đời).\n- Gây khó khăn cho Unit Test (vì trạng thái toàn cục khó mock).\n- Vấn đề Thread-safe trong môi trường đa luồng.\n\nLời khuyên: Chỉ dùng Singleton khi thực sự cần thiết. Nếu có thể, hãy sử dụng Dependency Injection (DI) để quản lý instance thay vì hard-code Singleton.", 
            "2025-11-09", "2025-11-09", 1500, "ND3", "PL6");

        // 7. Kiến trúc hệ thống - Người đăng: ND4
        db.insert_bai_viet("BV17", 
            "Serverless Architecture (AWS Lambda): Code thôi, đừng lo về Server", 
            "Mô hình Serverless đang thay đổi cách chúng ta deploy ứng dụng. Bạn chỉ cần viết code (Function), đẩy lên Cloud (như AWS Lambda, Google Cloud Functions), và nhà cung cấp sẽ lo toàn bộ việc khởi tạo server, scaling, patching.\n\nƯu điểm:\n- Chỉ trả tiền khi code chạy (tính theo mili giây).\n- Tự động scale từ 0 lên hàng nghìn request.\n\nNhược điểm:\n- Cold start (độ trễ khi hàm khởi động lại sau thời gian nghỉ).\n- Giới hạn thời gian chạy (thường là 15 phút).\n- Khó debug cục bộ.\nPhù hợp nhất cho các tác vụ xử lý ảnh, cron job, hoặc API traffic không ổn định.", 
            "2025-11-10", "2025-11-10", 2200, "ND4", "PL7");

        // 8. Di động - Người đăng: ND5
        db.insert_bai_viet("BV18", 
            "Đại chiến Cross-platform 2025: Flutter hay React Native?", 
            "Nếu bạn muốn làm app chạy cả iOS và Android mà lười học 2 ngôn ngữ (Swift/Kotlin), thì Cross-platform là lựa chọn duy nhất.\n\n1. Flutter (Google):\n- Dùng ngôn ngữ Dart.\n- Render bằng engine Skia riêng -> Hiệu năng cực cao, UI đồng nhất trên mọi máy.\n- Hot reload cực sướng.\n\n2. React Native (Meta):\n- Dùng JavaScript/TypeScript -> Dễ học cho dân Web.\n- Dùng native components -> UI cảm giác 'thật' hơn.\n- Cộng đồng cực lớn, thư viện gì cũng có.\n\nCá nhân mình thấy Flutter đang dần chiếm ưu thế về hiệu năng, nhưng React Native vẫn thắng thế về cơ hội việc làm.", 
            "2025-11-11", "2025-11-11", 5600, "ND5", "PL8");

        // 9. UI/UX - Người đăng: ND2
        db.insert_bai_viet("BV19", 
            "Thiết kế Dark Mode: Không đơn giản chỉ là đảo ngược màu sắc", 
            "Dark Mode (Giao diện tối) đã trở thành tiêu chuẩn bắt buộc. Nhưng thiết kế Dark Mode chuẩn không dễ:\n\n- Không dùng màu đen tuyệt đối (#000000): Hãy dùng màu xám đậm (#121212) để giảm mỏi mắt và tránh hiện tượng smearing trên màn hình OLED.\n- Tránh dùng màu bão hòa cao (Saturated colors): Màu quá rực trên nền đen sẽ gây rung mắt. Hãy dùng tông màu pastel hoặc giảm độ sáng.\n- Chiều sâu (Elevation): Trong Light mode ta dùng shadow, nhưng Dark mode phải dùng độ sáng của bề mặt (càng gần người dùng thì càng sáng hơn).\nAnh em dev khi code nhớ chú ý check contrast ratio nhé.", 
            "2025-11-12", "2025-11-12", 900, "ND2", "PL9");

        // 10. Frontend - Người đăng: ND3
        db.insert_bai_viet("BV20", 
            "Tại sao Next.js lại thống trị thế giới React Framework?", 
            "React bản chất chỉ là thư viện View (CSR - Client Side Rendering). Điều này khiến SEO kém và load lần đầu chậm. Next.js sinh ra để giải quyết vấn đề này với Server Side Rendering (SSR) và Static Site Generation (SSG).\n\nCác tính năng 'ăn tiền' của Next.js:\n- File-system Routing: Tạo file trong folder `pages` là tự có route, không cần config lằng nhằng.\n- API Routes: Viết backend (Node.js) ngay trong project frontend.\n- Image Optimization: Tự động tối ưu ảnh WebP.\n\nNếu bạn đang làm dự án Web cần SEO tốt (E-commerce, Blog, News), hãy dùng Next.js ngay thay vì Create-react-app.", 
            "2025-11-13", "2025-11-13", 3100, "ND3", "PL10");
		
	}
	
	public void taoPL()
	{
		if (db.get_row_count("the_phan_loai") > 0)
		{
			db.clear_the_pl();
		}
		db.insert_the_phan_loai("PL1", "Hardware/Chip", "Phân tích chuyên sâu về kiến trúc, hiệu năng, và công nghệ của các bộ xử lý (CPU/GPU), phần cứng, và thiết bị mới.");
		db.insert_the_phan_loai("PL2", "Database", "Hướng dẫn, so sánh, và tối ưu hóa các hệ quản trị CSDL quan hệ (SQL, PostgreSQL) và phi quan hệ (NoSQL, MongoDB, Redis).");
		db.insert_the_phan_loai("PL3", "Thuật toán & Cấu trúc Dữ liệu", "Nghiên cứu về độ phức tạp thuật toán, các kỹ thuật tối ưu hóa code, và triển khai các cấu trúc dữ liệu cơ bản đến nâng cao (Trees, Graphs, Hash Tables).");
		db.insert_the_phan_loai("PL4", "Ngôn ngữ Lập trình", "Phân tích sâu, hướng dẫn, và so sánh các đặc điểm, cú pháp, và ứng dụng của các ngôn ngữ lập trình phổ biến (Python, Java, Go, C#).");
		db.insert_the_phan_loai("PL5", "Bảo mật Thông tin", "Các chủ đề về lỗ hổng bảo mật, an toàn mạng, mã hóa dữ liệu, và các biện pháp phòng thủ chuyên sâu cho hệ thống và ứng dụng.");
		db.insert_the_phan_loai("PL6", "Design Pattern", "Nguyên tắc thiết kế hệ thống, SOLID, Microservices, và các mẫu kiến trúc phần mềm phổ biến (Factory, Observer, Singleton) giúp tăng tính tái sử dụng và dễ bảo trì.");
		db.insert_the_phan_loai("PL7", "Kiến trúc Hệ thống", "Thiết kế và quản lý các dịch vụ hạ tầng, công nghệ điện toán đám mây (AWS, Azure, Google Cloud), DevOps, và phương pháp triển khai hệ thống phân tán.");
		db.insert_the_phan_loai("PL8", "Phát triển Di động", "Các hướng dẫn và tin tức mới nhất về lập trình ứng dụng di động native (Android/iOS), cross-platform (Flutter, React Native), và tối ưu hóa hiệu năng trên thiết bị.");
		db.insert_the_phan_loai("PL9", "Thiết kế UI/UX", "Các xu hướng, nguyên tắc cốt lõi, và công cụ để tạo ra trải nghiệm người dùng (UX) và giao diện người dùng (UI) trực quan, hiệu quả.");
		db.insert_the_phan_loai("PL10", "Frontend/Framework", "Các công nghệ, framework, và thư viện cho giao diện người dùng phía client (React, Vue, Angular), bao gồm quản lý trạng thái và tối ưu hóa hiệu suất web.");
	}
	
	public void taoBL()
	{
		if (db.get_row_count("binh_luan") > 0)
		{
			db.clear_binhLuan();
		}
		// --- BV1: Chip M3 Pro ---
        db.insert_binh_luan("BL1", "Hiệu năng đơn nhân của M3 Pro ấn tượng thật, nhưng mức giá này sinh viên hơi khó với.", "2025-10-26", "ND4", "BV1");
        db.insert_binh_luan("BL2", "Mình đang dùng M1 Pro thấy vẫn mượt, chắc chưa cần lên đời vội. Đợi M4 luôn thể.", "2025-10-26", "ND3", "BV1");

        // --- BV2: PostgreSQL vs MongoDB ---
        db.insert_binh_luan("BL3", "Dự án thương mại điện tử thì cứ RDBMS mà quất thôi, tính toàn vẹn dữ liệu là số 1.", "2025-10-27", "ND5", "BV2");
        db.insert_binh_luan("BL4", "MongoDB tiện cho lúc làm Prototype thật, sửa schema không cần migration lằng nhằng.", "2025-10-29", "ND2", "BV2");

        // --- BV3: Tries & Auto-complete ---
        db.insert_binh_luan("BL5", "Bài viết hay quá! Trước giờ cứ dùng LIKE %...% trong SQL hèn gì nó chậm.", "2025-10-27", "ND4", "BV3");
        db.insert_binh_luan("BL6", "Bác thớt có thể làm thêm bài về Aho-Corasick được không? Thuật toán đó cũng hay dùng với Tries.", "2025-10-28", "ND5", "BV3");

        // --- BV4: Python Async/Await ---
        db.insert_binh_luan("BL7", "Mới chuyển từ Java qua Python, khái niệm Event Loop này hơi khó hiểu lúc đầu.", "2025-10-28", "ND2", "BV4");
        db.insert_binh_luan("BL8", "Dùng `asyncio` kết hợp với `aiohttp` crawl dữ liệu thì tốc độ bàn thờ luôn!", "2025-10-30", "ND3", "BV4");

        // --- BV5: React Hooks ---
        db.insert_binh_luan("BL9", "Cái `useEffect` dễ gây infinite loop lắm nếu không check kỹ dependency array.", "2025-10-30", "ND5", "BV5");
        db.insert_binh_luan("BL10", "Custom Hook là tính năng tuyệt vời nhất của React, giúp code gọn gàng hẳn.", "2025-10-31", "ND2", "BV5");

        // --- BV6: Bảo mật XSS/CSRF ---
        db.insert_binh_luan("BL11", "Luôn validate input ở cả Client lẫn Server là nguyên tắc bất di bất dịch.", "2025-10-31", "ND4", "BV6");
        db.insert_binh_luan("BL12", "Cảm ơn bài viết. Em vừa check lại code đồ án, hổng lỗ XSS tùm lum :((", "2025-11-01", "ND3", "BV6");

        // --- BV7: SOLID ---
        db.insert_binh_luan("BL13", "Nguyên tắc Liskov (LSP) là cái khó áp dụng nhất trong thực tế, hay bị vi phạm vô ý.", "2025-11-01", "ND2", "BV7");
        db.insert_binh_luan("BL14", "Code đúng SOLID lúc đầu hơi lâu, nhưng về sau maintain sướng tay hẳn.", "2025-11-02", "ND5", "BV7");

        // --- BV8: Microservices ---
        db.insert_binh_luan("BL15", "Team nhỏ, dự án nhỏ thì cứ Monolithic mà phang cho lành. Đua đòi Microservices là vỡ mặt.", "2025-11-02", "ND3", "BV8");
        db.insert_binh_luan("BL16", "Cái khó nhất của Microservices là Distributed Transaction (Saga Pattern).", "2025-11-03", "ND4", "BV8");

        // --- BV9: GoLang ---
        db.insert_binh_luan("BL17", "Go cú pháp đơn giản, dễ học nhưng cách xử lý error bằng `if err != nil` hơi dài dòng.", "2025-11-03", "ND2", "BV9");
        db.insert_binh_luan("BL18", "Goroutines nhẹ hơn Thread của Java nhiều, build backend chịu tải cao rất ổn.", "2025-11-04", "ND3", "BV9");

        // --- BV10: UI/UX 2025 ---
        db.insert_binh_luan("BL19", "Tối giản nhưng đừng đơn điệu. Khoảng trắng nhiều mà không biết sắp xếp nhìn rất rỗng.", "2025-11-04", "ND5", "BV10");
        db.insert_binh_luan("BL20", "Dark Mode muôn năm! Giờ app nào không có Dark Mode là mình xóa ngay.", "2025-11-05", "ND4", "BV10");

        // --- BV11: RISC-V ---
        db.insert_binh_luan("BL21", "Mã nguồn mở là tương lai. Hy vọng RISC-V sớm phổ biến để giá chip rẻ đi.", "2025-11-05", "ND3", "BV11");
        db.insert_binh_luan("BL22", "Chắc còn lâu mới thay thế được x86 hay ARM, hệ sinh thái phần mềm quan trọng lắm.", "2025-11-06", "ND4", "BV11");

        // --- BV12: Redis Caching ---
        db.insert_binh_luan("BL23", "Lưu ý cấu hình Eviction Policy cẩn thận kẻo tràn RAM là sập cả node Redis.", "2025-11-06", "ND5", "BV12");
        db.insert_binh_luan("BL24", "Mình dùng Redis làm Message Queue thay cho RabbitMQ được không mọi người?", "2025-11-07", "ND4", "BV12");

        // --- BV13: Quy hoạch động ---
        db.insert_binh_luan("BL25", "Đọc bài này xong mới vỡ lẽ ra nhiều thứ. Trước giờ toàn học vẹt code.", "2025-11-07", "ND5", "BV13");
        db.insert_binh_luan("BL26", "DP quan trọng nhất là tìm ra công thức truy hồi. Tìm được là xong 90%.", "2025-11-08", "ND2", "BV13");

        // --- BV14: Rust ---
        db.insert_binh_luan("BL27", "Học Rust đánh nhau với cái Borrow Checker trầm cảm luôn, nhưng code chạy bao sướng.", "2025-11-08", "ND4", "BV14");
        db.insert_binh_luan("BL28", "Rust đang dần thay thế C++ trong các dự án OS. Microsoft cũng đang viết lại Windows bằng Rust.", "2025-11-09", "ND3", "BV14");

        // --- BV15: SQL Injection ---
        db.insert_binh_luan("BL29", "Giờ dùng Framework như Hibernate hay Entity Framework thì lo gì SQLi nữa?", "2025-11-09", "ND4", "BV15");
        db.insert_binh_luan("BL30", "@ND4: Vẫn dính nếu ông nối chuỗi trong HQL/JPQL nhé. Đừng chủ quan!", "2025-11-09", "ND5", "BV15");

        // --- BV16: Singleton ---
        db.insert_binh_luan("BL31", "Mình toàn dùng Dependency Injection (DI) thay cho Singleton. Dễ test hơn hẳn.", "2025-11-10", "ND2", "BV16");
        db.insert_binh_luan("BL32", "Singleton vẫn hữu dụng với mấy cái như Logger hay Config mà, đâu phải lúc nào cũng xấu.", "2025-11-11", "ND4", "BV16");

        // --- BV17: Serverless ---
        db.insert_binh_luan("BL33", "Cold start của AWS Lambda đôi khi làm API chậm đi vài giây, khá khó chịu.", "2025-11-11", "ND3", "BV17");
        db.insert_binh_luan("BL34", "Serverless cực hợp với mấy task chạy nền (cronjob) hoặc xử lý ảnh, video.", "2025-11-12", "ND5", "BV17");

        // --- BV18: Flutter vs React Native ---
        db.insert_binh_luan("BL35", "Vote Flutter 1 phiếu. UI đồng nhất trên cả iOS và Android là điểm cộng cực lớn.", "2025-11-12", "ND3", "BV18");
        db.insert_binh_luan("BL36", "React Native có cộng đồng JS chống lưng quá mạnh, thư viện gì cũng có.", "2025-11-13", "ND2", "BV18");

        // --- BV19: Dark Mode ---
        db.insert_binh_luan("BL37", "Làm Dark Mode cực nhất là xử lý độ tương phản (Contrast) của text trên nền xám.", "2025-11-13", "ND4", "BV19");
        db.insert_binh_luan("BL38", "Dev hay làm đêm nên Dark Mode là chân ái. Cảm ơn bài viết chi tiết.", "2025-11-14", "ND5", "BV19");

        // --- BV20: Next.js ---
        db.insert_binh_luan("BL39", "Next.js + Vercel đúng là cặp đôi hoàn hảo. Deploy web trong 1 nốt nhạc.", "2025-11-14", "ND4", "BV20");
        db.insert_binh_luan("BL40", "Dùng SSR (Server Side Rendering) SEO tốt hơn hẳn so với React thuần.", "2025-11-15", "ND2", "BV20");
	}

	private void toggleMenuItemVisibility(Menu menu, boolean visible) {
	    // Ẩn/hiện QL BÀN
	    MenuItem qlND = menu.findItem(R.id.menu_main_qlND);
	    MenuItem qlBV = menu.findItem(R.id.menu_main_qlBV);
	    MenuItem qlPL = menu.findItem(R.id.menu_main_qlPL);
	    MenuItem qlBL = menu.findItem(R.id.menu_main_qlBL);

	    // Ẩn/hiện QL NGƯỜI DÙNG
	    if (qlND != null) {
	    	qlND.setVisible(visible);
	    }

	    // Ẩn/hiện QL BÀI VIẾT
	    if (qlBV != null) {
	    	qlBV.setVisible(visible);
	    }

	    // Ẩn/hiện QL THẺ PL
	    if (qlPL != null) {
	    	qlPL.setVisible(visible);
	    }

	    // Ẩn/hiện QL BÌNH LUẬN
	    if (qlBL != null) {
	    	qlBL.setVisible(visible);
	    }
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (S_mand.getMaND() != null) 
		{
		    toggleMenuItemVisibility(menu, true); 
		}
		else
		{
		    toggleMenuItemVisibility(menu, false); 
		}
	    return super.onPrepareOptionsMenu(menu);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
	    
	    if (id == R.id.menu_main_qlND) {
			Intent intent = new Intent(MainActivity.this, QL_nguoiDung.class);
			startActivity(intent);
	        return true;
	    }
	    
	    if (id == R.id.menu_main_qlBV) {
			Intent intent = new Intent(MainActivity.this, QL_baiViet.class);
			startActivity(intent);
	        return true;
	    }
	    
	    if (id == R.id.menu_main_qlPL) {
			Intent intent = new Intent(MainActivity.this, QL_thePL.class);
			startActivity(intent);
	        return true;
	    }
	    
	    if (id == R.id.menu_main_qlBL) {
			Intent intent = new Intent(MainActivity.this, QL_binhLuan.class);
			startActivity(intent);
	        return true;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}
    
}
