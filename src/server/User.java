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
	Thread thread; //유저 is a Runnable 이라고 쓰레드인것은 아니다
	
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
		
		//스트림 생성후 부터 대화가 가능하기 때문에 아래서 생성해야함
		thread=new Thread(this);
		thread.start();
		
		la=new JLabel(123+"번 PC");
		add(la);
		setPreferredSize(new Dimension(150, 150));
		setBackground(Color.PINK);
	}
	
	//듣기 
	public void listen(){
		String msg=null;
		try {
			msg=buffr.readLine(); 
			//msg가 대화인지 구매인 구분해야함
			
			/* 요청구문 분석 시작
			requestType=chat
			msg=클라이언트말
			id=클라이언트id */
			String[] data=msg.split("&");
			String[] requestType=data[0].split("=");	
			if(requestType[1].equals("chat")){
				//대화를 원해요
				String[] str=data[1].split("=");
				send(str[1]);//다시보내기
			}else if(requestType[1].equals("buy")){
				//구매를 원해요
				System.out.println("구매를 원하는 군요");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//말하기
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
