//상품 1건의 디스플레이를 표현한 클래스
package client;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Product extends JPanel implements ActionListener{
	ClientMain clientMain;
	Canvas can;
	JButton bt_buy;
	BufferedImage image;
	URL url; //자원의 위치에 대한 객체
	
	//캔버스의 크기
	int width=120;
	int height=150;
		
	public Product(URL url, ClientMain clientMain) {
		this.clientMain=clientMain;
		this.url=url;
		
		//이미지 생성부터해야 끌어올 수 있다.
		try {
			image=ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		can=new Canvas(){
			@Override
			public void paint(Graphics g) {
				g.drawImage(image, 0, 0, width, height, this);
			}
		};

		bt_buy=new JButton("구매");
		
		setLayout(new BorderLayout());
		add(can);
		add(bt_buy, BorderLayout.SOUTH);
		bt_buy.addActionListener(this);
		
		//현재 패널의 사이즈
		setPreferredSize(new Dimension(width+4, height+45));
	}

	public void buy(){
		//내가 살꺼라고 스트림을 통해 서버에 보내야함. 근데 스트림은 쓰레드가 가지고 있다?
		//무엇을 보내야할까? 육하원칙으로 생각, 누가, 무엇을
		String msg="requestType=buy&product_id=87&id=batman"; //이게바로 프로토콜 
		//원격지에 떨어진 두 당사자간에 보낸사람쪽에서 보낸 신호를 받는 쪽에서 받을 수 있도록 구성
		clientMain.ct.send(msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		buy();
	}
}
