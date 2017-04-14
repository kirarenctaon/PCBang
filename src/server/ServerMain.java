package server;

import java.awt.Color;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ServerMain extends JFrame implements Runnable{
	JPanel p_center; //��üȭ���
	ServerSocket server;
	Thread thread; //������ ������ ������
	int port=7878;
	
	public ServerMain() {
		p_center=new JPanel();
		
		try {
			server=new ServerSocket(port);
			System.out.println("���� ����");
			thread=new Thread(this);
			/*�� ������ ���ξ������ ���������Ǿ������ ���� �ڽ��� �ڵ带 �����ϰ� �ǹǷ� 
			���ξ����忡 ���� �������� ȭ�鿡 �����԰� ���ÿ� ������ accept()�� ���ÿ� �����ϰ� �ȴ�.
			=������ ������ �ϸ� ���������� �����ȴ�.*/
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
		//�����ڸ� �������� �޴´�. 
		while(true){
			try {
				Socket socket= server.accept();
				//�����ڰ� �߰ߵǸ�, pc���� ī���Ϳ� �����ڸ� ǥ���� ȭ�鿡 �����Ŵ
				System.out.println("��������");
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
