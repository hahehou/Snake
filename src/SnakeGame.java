import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.Timer;

public class SnakeGame{
	private int TableW = 400;
	private int TableH = 400;
	private int UnitSize = 10;
	
	private JFrame f = new JFrame("Snake");
	private MyCanvas TableArea = new MyCanvas();
	
	LinkedList<SnakeUnit> u = new LinkedList<SnakeUnit>();
	Timer timer;

	private int CurrentDrc = 0;		//up 0, right 0, down 2, left 3.
	private int WantedDrc;
	
	public void init() {
		f.add(TableArea);
		TableArea.setBounds(0, 0, TableW, TableH);
		f.setLocation(200, 200);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		u.add(new SnakeUnit(10,10));		
		u.add(new SnakeUnit(10,11));
		u.add(new SnakeUnit(10,12));
		
		KeyAdapter keyProcessor = new KeyAdapter() {
			public void keyPressed(KeyEvent k) {
				if(k.getKeyCode()== KeyEvent.VK_UP) {
					WantedDrc = 0;
				}else if(k.getKeyCode() == KeyEvent.VK_DOWN) {
					WantedDrc = 2;
				}else if(k.getKeyCode() == KeyEvent.VK_LEFT) {
					WantedDrc = 3;
				}else if(k.getKeyCode() == KeyEvent.VK_RIGHT) {
					WantedDrc = 1;
				}
			}
		};
		f.addKeyListener(keyProcessor);
		TableArea.addKeyListener(keyProcessor);
		 
		ActionListener TaskPerfommer = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent a) {
					
				if(Math.abs(CurrentDrc - WantedDrc)!=2)	
					CurrentDrc = WantedDrc;
					
				if(CurrentDrc == 0)
					u.addFirst(new SnakeUnit(u.getFirst().getSnakeUnit_x(),u.getFirst().getSnakeUnit_y()-1));
				else if(CurrentDrc == 1)
					u.addFirst(new SnakeUnit(u.getFirst().getSnakeUnit_x()+1,u.getFirst().getSnakeUnit_y()));
				else if(CurrentDrc == 2)
					u.addFirst(new SnakeUnit(u.getFirst().getSnakeUnit_x(),u.getFirst().getSnakeUnit_y()+1));
				else if(CurrentDrc == 3)
					u.addFirst(new SnakeUnit(u.getFirst().getSnakeUnit_x()-1,u.getFirst().getSnakeUnit_y()));
				
				if(u.getFirst().getSnakeUnit_x() < 0 || u.getFirst().getSnakeUnit_x() >= TableW/UnitSize || u.getFirst().getSnakeUnit_y() < 0 || u.getFirst().getSnakeUnit_y() >= TableH/UnitSize) {
					timer.stop();
				}else {	
					u.removeLast();
					TableArea.repaint();
				}			
			}
		};
		
		timer = new Timer(400,TaskPerfommer);
		timer.start();
		f.pack();
		f.setVisible(true);
		
		
	}
	
	public static void main(String[] args) {
		
		new SnakeGame().init();
		
	}
	
	class SnakeUnit{
		private int x;
		private int y;
		
		public SnakeUnit(int x,int y) {
			this.x = x;
			this.y = y;
		}
		public int getSnakeUnit_x() {
			return x;
		}
		public int getSnakeUnit_y() {
			return y;
		}
	}
	
	class MyCanvas extends Canvas{
		
		private static final long serialVersionUID = 1L;

		public void paint(Graphics g) {
				
			for(int i = 0; i<u.size(); i++) {
				g.fillRect(u.get(i).getSnakeUnit_x()*UnitSize, u.get(i).getSnakeUnit_y()*UnitSize, UnitSize, UnitSize);
			}	
		}
	}
}