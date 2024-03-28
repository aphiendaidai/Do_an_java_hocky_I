import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textusername;
	private JPasswordField txtpassword;
	private JButton btnlogin;
	private JButton btnexit;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	private JComboBox txtid;
	private Login frame;
	
	public Login() {
		setForeground(new Color(0, 191, 255));
		setBackground(new Color(0, 128, 128));
		intarg();
		connect();
		
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con= DriverManager.getConnection("jdbc:mysql://localhost:3306/sinhvien", "root","");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	/**
	 * Create the frame.
	 */
	public void intarg() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 408, 392);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.info);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username :");
		lblNewLabel.setFont(new Font(".VnArabia", Font.PLAIN, 18));
		lblNewLabel.setBounds(25, 137, 118, 31);
		contentPane.add(lblNewLabel);
		
		textusername = new JTextField();
		textusername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textusername.setBounds(153, 140, 135, 28);
		contentPane.add(textusername);
		textusername.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password :");
		lblNewLabel_1.setFont(new Font(".VnArabia", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(25, 194, 97, 28);
		contentPane.add(lblNewLabel_1);
		
		txtpassword = new JPasswordField();
		txtpassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpassword.setBounds(153, 196, 135, 28);
		contentPane.add(txtpassword);
		
		btnlogin = new JButton("Login");
		btnlogin.setForeground(new Color(255, 0, 0));
		btnlogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String user = textusername.getText();//Lấy giá trị từ textusername và lưu vào biến user.
					String pws= new String(txtpassword.getPassword());//Lấy giá trị từ trường mật khẩu txtpassword và lưu vào biến pws

					pst=con.prepareStatement("SELECT * FROM user_tbl"); // thực hiện truy vấn SQL SELECT trên bảng user_tbl
					rs=pst.executeQuery(); //Thực hiện truy vấn và nhận kết quả trong ResultSet rs.
					
					while(rs.next()) { // duyệt qua tất cả các hàng của ResultSet để kiểm tra thông tin đăng nhập.
						String uname= rs.getString("username"); 
						String password= rs.getString("password");//Lấy giá trị của cột "username" và "password" từ ResultSet.
						if((user.equals(uname) ) && (pws.equals(password))) {
							sinhvien sv = new sinhvien();
							sv.run();
							setVisible(false);
							break;
						}else {
							JOptionPane.showInputDialog(this, "Ket noi khong thanh cong");
							break;
						}
					}
				
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnlogin.setBackground(new Color(119, 136, 153));
		btnlogin.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnlogin.setBounds(57, 287, 121, 36);
		contentPane.add(btnlogin);
		
		btnexit = new JButton("Exit");
		btnexit.setForeground(new Color(255, 0, 0));
		btnexit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnexit.setFont(new Font("Tahoma", Font.PLAIN, 19));
		btnexit.setBackground(new Color(119, 136, 153));
		btnexit.setBounds(248, 287, 121, 36);
		contentPane.add(btnexit);
		
		lblNewLabel_2 = new JLabel("LOGIN");
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBackground(new Color(220, 20, 60));
		lblNewLabel_2.setFont(new Font(".Vn3DH", Font.PLAIN, 76));
		lblNewLabel_2.setBounds(37, 0, 312, 124);
		contentPane.add(lblNewLabel_2);
		frame = this;
	}
}
