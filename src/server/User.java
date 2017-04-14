package server;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class User extends JPanel implements Runnable{
	JLabel la;
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	Boolean flag=true;
	Thread thread; //���� is a Runnable �̶�� �������ΰ��� �ƴϴ�
	
	public User(Socket socket) {
		this.socket=socket;
		
		//thread=new Thread(this);
		//thread.start();
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//��Ʈ�� ������ ���� ��ȭ�� �����ϱ� ������ �Ʒ��� �����ؾ���
		thread=new Thread(this);
		thread.start();
		
		la=new JLabel(123+"�� PC");
		add(la);
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.PINK);
	}
	
	//��� 
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine(); 
			//msg�� ��ȭ���� ������ �����ؾ���
			
			/* ��û���� �м� ����
			requestType=chat
			msg=Ŭ���̾�Ʈ��
			id=Ŭ���̾�Ʈid */
			String[] data=msg.split("&");
			String[] requestType=data[0].split("=");	
			if(requestType[1].equals("chat")){
				//��ȭ�� ���ؿ�
				String[] str=data[1].split("=");
				send(str[1]);//�ٽú�����
			}else if(requestType[1].equals("buy")){
				//���Ÿ� ���ؿ�
				System.out.println("���Ÿ� ���ϴ� ����");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//���ϱ�
	public void send(String msg){
		try {
			buffw.write(msg+"\n");
			buffw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(flag){
			listen();
		}
	}
	
}
