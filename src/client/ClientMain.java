package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientMain extends JFrame implements ActionListener{
	JPanel p_center, p_south;
	JTextArea area;
	JScrollPane scroll;
	JTextField t_input;
	JButton bt;
	Product product;
	String[] path={
		"p1.jpg", "p2.jpg", "p3.jpg", "p4.jpg", "p5.jpg", "p6.jpg", "p7.jpg", "p8.jpg", "p9.jpg", "p10.jpg"
	};
	
	Socket socket;
	String ip="localhost";
	int port=7878;
	
	ClientThread ct;
	
	public ClientMain() {
		p_center=new JPanel();
		p_south=new JPanel();
		area=new JTextArea();
		scroll=new JScrollPane(area);
		t_input=new JTextField(40);
		bt=new JButton("접속");
		
		area.setBackground(Color.YELLOW);
		scroll.setPreferredSize(new Dimension(580, 200));
		
		//상품나열하기
		createProduct();
		
		p_center.add(scroll);
		p_south.add(t_input);
		p_south.add(bt);
		
		add(p_center);
		add(p_south, BorderLayout.SOUTH);
		
		//버튼과 리스너연결
		bt.addActionListener(this);
		//입력박스와 리스너연결
		t_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key=e.getKeyCode();
				if(key==KeyEvent.VK_ENTER){
					String msg=t_input.getText();
					ct.send("requestType=chat&msg="+msg+"&id=batman"); //서버에 메세지 보내기
					t_input.setText(""); //글씨초기화
				}
			}
		});
		
		setSize(700, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	//상품이미지를 웹서버에서 가져오자
	public void createProduct(){
		try {
			for(int i=0;i<path.length/2;i++){
				URL url=new URL("http://localhost:9090/data/"+path[i]);
				product=new Product(url, this);
				p_center.add(product);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(){
		try {
			socket=new Socket(ip, port);
			
			ct=new ClientThread(socket, area);
			ct.start();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		connect();
	}

	public static void main(String[] args) {
		new ClientMain();
	}

}
