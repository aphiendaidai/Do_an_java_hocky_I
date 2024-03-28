import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.SystemColor;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import java.io.*;

public class sinhvien {

	private JFrame frmSinhVien;
	private JTextField id;
	private JTextField ten;
	private JTextField sdthoai;
	private JTextField quequan;
	private JTable table;
	DefaultTableModel model;
    private final ButtonGroup buttonGroup = new ButtonGroup();
	/**
	 * Launch the application.
	 */
    
    //
	public void run () {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sinhvien window = new sinhvien();
					window.frmSinhVien.setVisible(true);
				//new Login().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public sinhvien() {
		initialize();
		connect();
		Fetch();
		LoadProductNo();
	}

	
	


	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JComboBox txtid;
	
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root","");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
// LoadProductNo  được sử dụng để tải danh sách mã ID của tất cả các sinh viên từ cơ sở dữ liệu và hiển thị lên ComboBox txtid
	public void LoadProductNo() {
	 try {
		pst= con.prepareStatement("SELECT id FROM student");// Tạo một đối tượng Pstt với truy vấn SQL SELECT id FROM student. Truy vấn này sẽ lấy tất cả các mã ID từ bảng student.
		
		rs= pst.executeQuery();// thực hiện truy vấn và nhận về kết quả ResultSet
		
		txtid.removeAllItems();// Xóa tất cả các mục trong ComboBox txtid.
		
// Duyệt qua kết quả ResultSet và thêm mỗi mã ID vào ComboBox txtid.
// sử dụng cái này cho phép người dùng chọn mã ID của sinh viên cần thao tác.
		while(rs.next()) {
			txtid.addItem(rs.getString(1));
		}
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
		
	}

//Fetch  được sử dụng để lấy danh sách sinh viên từ cơ sở dữ liệu và hiển thị lên bảng.
	public void Fetch() {
		
		try {
			int q;
			pst=con.prepareStatement("SELECT * FROM student"); // Tạo một đối tượng PreparedStatement để thực hiện truy vấn SQL, nó sẽ lấy tất cả các cột từ bảng studen
			
			rs=pst.executeQuery();// Thực hiện truy vấn và nhận kết quả ResultSet
			
 // Lấy số lượng cột trong kết quả ResultSet
			ResultSetMetaData rsr= rs.getMetaData();
			q=rsr.getColumnCount();
			
			DefaultTableModel dtm = (DefaultTableModel) table.getModel(); // Lấy mô hình dữ liệu của JTable (DefaultTableModel)
	        
			dtm.setRowCount(0); // Đặt số hàng của mô hình dữ liệu về 0 để xóa dữ liệu hiện tại
		
  // Duyệt qua kết quả ResultSet và thêm dữ liệu vào mô hình dữ liệu
			while(rs.next()) {
				Vector v2= new Vector();
				for(int i=1; i<q; i++) {
					v2.add(rs.getString("id"));
					v2.add(rs.getString("ten"));
					v2.add(rs.getString("sdthoai"));
					v2.add(rs.getString("quequan"));
//Đoạn mã này lấy ra các giá trị của từng cột (id, ten, sdthoai, quequan) trong từng hàng của ResultSet và thêm chúng vào Vector v2 theo thứ tự đó.

				}
				dtm.addRow(v2);
				
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private boolean isIdExists(String studentId) {
        try {
            pst = con.prepareStatement("SELECT * FROM student WHERE id=?");//Tạo truy vấn SQL để kiểm tra sự tồn tại của ID trong cơ sở dữ liệu
            pst.setString(1, studentId);       // Gán giá trị cho tham số trong truy vấn
            rs = pst.executeQuery();            // Thực hiện truy vấn và lấy kết quả
            return rs.next();         // Kiểm tra xem có bản ghi nào trả về hay không nếu true là tồn tại.
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSinhVien = new JFrame();
		frmSinhVien.setTitle("Sinh Viên");
		frmSinhVien.setBounds(100, 100, 851, 525);
		frmSinhVien.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSinhVien.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(10, 10, 820, 493);
		frmSinhVien.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ID :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(10, 163, 80, 28);
		panel.add(lblNewLabel);
		
		JLabel lblName = new JLabel("Tên :");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblName.setBounds(10, 216, 80, 28);
		panel.add(lblName);
		
		JLabel lblPhoneNumber = new JLabel("Số Điện Thoại :");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPhoneNumber.setBounds(10, 265, 181, 28);
		panel.add(lblPhoneNumber);
		
		JLabel lblCourse = new JLabel("Quê quán: ");
		lblCourse.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCourse.setBounds(10, 319, 181, 28);
		panel.add(lblCourse);
		
		id = new JTextField();
		id.setFont(new Font("Tahoma", Font.ITALIC, 20));
		id.setBounds(198, 163, 166, 28);
		panel.add(id);
		id.setColumns(10);
		
		ten = new JTextField();
		ten.setFont(new Font("Tahoma", Font.ITALIC, 20));
		ten.setColumns(10);
		ten.setBounds(198, 216, 166, 28);
		panel.add(ten);
		
		sdthoai = new JTextField();
		sdthoai.setFont(new Font("Tahoma", Font.ITALIC, 20));
		sdthoai.setColumns(10);
		sdthoai.setBounds(198, 265, 166, 28);
		panel.add(sdthoai);
		
		quequan = new JTextField();
		quequan.setFont(new Font("Tahoma", Font.ITALIC, 20));
		quequan.setColumns(10);
		quequan.setBounds(198, 319, 166, 28);
		panel.add(quequan);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(368, 29, 442, 437);
		panel.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent e) {
				int i= table.getSelectedRow();
				id.setText(model.getValueAt(i, 0).toString());
				ten.setText(model.getValueAt(i, 1).toString());
				sdthoai.setText(model.getValueAt(i, 2).toString());
				quequan.setText(model.getValueAt(i, 3).toString());
			}
		});
		table.setBackground(Color.PINK);
		model= new DefaultTableModel();
		Object[] colum= {"ID", "Tên", "Số Điện Thoại", "Quê Quán"};
		Object[] row= new Object[4];
		model.setColumnIdentifiers(colum);
		table.setModel(model);
        scrollPane.setViewportView(table);
		
        
		JButton btnNewButton = new JButton("Add");
		buttonGroup.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
		
			
			public void actionPerformed(ActionEvent e) {
			
				if(id.getText().equals("") || ten.getText().equals("")|| sdthoai.getText().equals("")|| quequan.getText().equals("")  ) {
					JOptionPane.showMessageDialog(null,"Bạn nhập không đầy đủ\nVui lòng nhập hết.");
				}else   if (isIdExists(id.getText())) {
	                JOptionPane.showMessageDialog(null, "ID đã tồn tại. Vui lòng nhập ID khác.");
	            } else {
					
// Lấy dữ liệu từ các trường nhập liệu
				row[0]= id.getText();
				row[1]=ten.getText();
				row[2]=sdthoai.getText();
				row[3]=quequan.getText();
				
 // Thực hiện truy vấn thêm sinh viên vào cơ sở dữ liệu
				try {
					pst= con.prepareStatement("INSERT INTO student (id, ten, sdthoai, quequan) VALUES(?,?,?,?)");
//					Gán giá trị cho dấu hỏi chấm thứ nhất (?) trong truy vấn SQL.
//					Gán giá trị này vào vị trí thứ nhất trong truy vấn SQL (tương ứng với cột id trong bảng).
					pst.setString(1, id.getText());//Lấy giá trị từ trường nhập liệu id bằng phương thức getText().
					pst.setString(2, ten.getText());
					pst.setString(3, sdthoai.getText());
					pst.setString(4, quequan.getText
							());

					pst.executeUpdate();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
// Thêm hàng vào bảng hiển thị
				model.addRow(row);
				
// Xóa nội dung các trường nhập liệu
				id.setText("");
				ten.setText("");
				sdthoai.setText("");
				quequan.setText("");
				JOptionPane.showMessageDialog(null, "Đã thêm thành công ");
				}
			}
		});
		btnNewButton.setFont(new Font(".VnArabia", Font.BOLD | Font.ITALIC, 18));
		btnNewButton.setBounds(10, 429, 94, 37);
		panel.add(btnNewButton);
		
		JButton btnUpdate = new JButton("Update");

		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//			
				try {
				
					

				//	Gán giá trị cho các dấu hỏi chấm (?) trong truy vấn SQL với các giá trị lấy từ các đối tượng Swing trong chương trình.
					pst=con.prepareStatement("UPDATE student set id=?, ten=?, sdthoai=?, quequan=? WHERE id=?");
				    pst.setString(1,txtid.getSelectedItem().toString());//txtid.getSelectedItem().toString(): Lấy mã ID của sinh viên được chọn từ ComboBox txtid.

				    pst.setString(2,ten.getText()); //ten.getText(): Lấy tên sinh viên từ trường nhập liệu ten.		
				    
				    pst.setString(3,sdthoai.getText());//sdthoai.getText(): Lấy số điện thoại từ trường nhập liệu sdthoai.

				    pst.setString(4,quequan.getText());  //quequan.getText(): Lấy quê quán từ trường nhập liệu quequan.

				    pst.setString(5,txtid.getSelectedItem().toString());
//Mã ID được gán cho cả dấu hỏi thứ nhất và thứ năm để đảm bảo chỉ cập nhật đúng sinh viên có mã ID đó.
				    
				    int update= pst.executeUpdate(); //Thực thi truy vấn cập nhật và trả về số hàng bị ảnh hưởng.
				
				    if(update==1) {
					id.setText(" ");
					ten.setText(" ");
					sdthoai.setText(" ");
					quequan.setText(" ");
					ten.requestFocus();
					LoadProductNo();
					//tải lại danh sách ID từ csdl
					Fetch();
					//Dòng mã này sẽ gọi phương thức Fetch() để cập nhật dữ liệu trên bảng hiển thị.
					JOptionPane.showMessageDialog(null, "Đã cập nhập thành công ");

				    }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				
			}
		});
		btnUpdate.setFont(new Font(".VnArabia", Font.BOLD | Font.ITALIC, 18));
		btnUpdate.setBounds(133, 429, 107, 37);
		panel.add(btnUpdate);
		
		JButton btnDalete = new JButton("Dalete");
		btnDalete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				try {
					String pid=txtid.getSelectedItem().toString();//Lấy mã ID của sinh viên được chọn:

					pst=con.prepareStatement("Delete from student where id =?");//Chuẩn bị truy vấn SQL để xóa sinh viên

					pst.setString(1, pid);//Gán giá trị mã ID cho truy vấn

					int k=pst.executeUpdate();//thực thi truy vấn xóa

					if(k==1) {
						JOptionPane.showMessageDialog(null, "Đã xóa thành công ");
						ten.setText(" ");
						sdthoai.setText(" ");
						quequan.setText(" ");
					  LoadProductNo();
						Fetch();
					}
				//	LoadProductNo();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnDalete.setFont(new Font(".VnArabia", Font.BOLD | Font.ITALIC, 18));
		btnDalete.setBounds(256, 429, 94, 37);
		panel.add(btnDalete);
		
		txtid = new JComboBox();
		txtid.setBounds(72, 94, 68, 28);
		panel.add(txtid);
		
		JLabel lblNewLabel_1 = new JLabel("ID ");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(10, 85, 80, 47);
		panel.add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("Search");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String pid=txtid.getSelectedItem().toString(); //lấy mã ID của sv đc chọn
					pst=con.prepareStatement("SELECT * FROM student WHERE id=?");
					pst.setString(1, pid); // Gán giá trị mã ID cho truy vấn:
					rs=pst.executeQuery(); // thực thi truy vấn tìm kiếm
					if(rs.next()==true) {
						//Hiển thị thông tin của sinh viên trên các trường nhập liệu:
						id.setText(rs.getString(1));
						ten.setText(rs.getString(2));
						sdthoai.setText(rs.getString(3));
						quequan.setText(rs.getString(4));
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton_1.setBounds(10, 132, 94, 17);
		panel.add(btnNewButton_1);
		
		JLabel lblNewLabel_2 = new JLabel("Quản Lý Sinh Viên");
		lblNewLabel_2.setForeground(Color.RED);
		lblNewLabel_2.setBackground(Color.CYAN);
		lblNewLabel_2.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 38));
		lblNewLabel_2.setBounds(30, -6, 343, 64);
		panel.add(lblNewLabel_2);
		
		JButton btnExport = new JButton("Export to CSV");
		btnExport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename= "F:\\ExporedFilseJava.csv";
				
				try {
					FileWriter bw = new FileWriter(filename);// một FileWriter được tạo để ghi dữ liệu vào file CSV.
					pst=con.prepareStatement("SELECT * FROM student");//Bạn tạo một đối tượng PST để thực hiện truy vấn SQL SELECT trên bảng "student".
					rs= pst.executeQuery(); //Bạn thực hiện truy vấn và nhận kết quả trong một ResultSet 
//					int rowCount = 0;  // Biến đếm số lượng hàng đã ghi vào file
//					int maxRowsToExport = 10;// Số lượng hàng muốn xuất ra

					while(rs.next() ) {
						bw.append(rs.getString(1)); // Ghi giá trị của cột thứ nhất vào FileWriter.
						bw.append(','); //Ghi dấu phẩy để ngăn cách giữa các trường trong file CSV.
						bw.append(rs.getString(2));
						bw.append(',');
						bw.append(rs.getString(3));
						bw.append(',');
						bw.append(rs.getString(4));
						bw.append('\n'); // tạo ra một hàng mới trong file CSV cho mỗi hàng trong ResultSet.
						
					   // rowCount++;  // Tăng biến đếm

					}
					
					JOptionPane.showMessageDialog(null, "File CSV đã xuất thành công ");

					bw.flush(); //đẩy bất kỳ dữ liệu còn đọng trong bộ đệm của FileWriter xuống file.
					bw.close(); //đóng FileWriter và giải phóng tất cả các tài nguyên hệ thống mà nó đang giữ.
				} catch (IOException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		});
		btnExport.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnExport.setBounds(10, 368, 142, 37);
		panel.add(btnExport);


	}
}
