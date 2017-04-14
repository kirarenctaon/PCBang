package server;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerMain extends JFrame implements Runnable{
	JPanel p_center; //전체화면용
	ServerSocket server;
	Thread thread; //접속자 감지용 쓰레드
	int port=7878;
	
	public ServerMain() {
		p_center=new JPanel();
		
		try {
			server=new ServerSocket(port);
			System.out.println("서버 생성");
			thread=new Thread(this);
			/*이 시점엔 메인쓰레드와 개발자정의쓰레드는 각자 자신의 코드를 수행하게 되므로 
			메인쓰레드에 의해 프레임이 화면에 등장함과 동시에 서버에 accept()도 동시에 동작하게 된다.
			=별도의 실행을 하며 독립적으로 가동된다.*/
			thread.start(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		p_center.setBackground(Color.WHITE);
		add(p_center);

		setSize(800, 700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	@Override
	public void run() {
		//접속자를 무한으로 받는다. 
		while(true){
			try {
				Socket socket= server.accept();
				//접속자가 발견되면, pc메인 카운터에 접속자를 표현한 화면에 등장시킴
				System.out.println("유저접속");
				User user=new User(socket);
				
				p_center.add(user);
				p_center.updateUI();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		new ServerMain();
	}

}
