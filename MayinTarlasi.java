import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MayinTarlasi implements MouseListener {
	JFrame frame;
	Button[][] board = new Button[10][10];
	int openButtons;
	
	public MayinTarlasi() {
		openButtons = 0;
		frame = new JFrame("Mayin Tarlasi");
		frame.setSize(800, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(10, 10));
		
		for (int row = 0; row < board.length; row++) {
			for(int col = 0; col < board[0].length; col++) {
				Button b = new Button(row, col);
				frame.add(b);
				b.addMouseListener(this);
				board[row][col] = b;
			}
		}
		
		mineGenerator();
		countUpdate();
		//print();
		//printMines();
		
		frame.setVisible(true);
	}
	
	public void mineGenerator() {
		int i = 0 ;
		while(i < 10) {
			int randomRow = (int) (Math.random() * board.length);
			int randomCol = (int) (Math.random() * board[0].length);
			while (board[randomRow][randomCol].isMine()) {
				randomRow = (int) (Math.random() * board.length);
				randomCol = (int) (Math.random() * board[0].length);			
			}
			board[randomRow][randomCol].setMine(true);
			i++;
		}
	}
		public void print() {
			for (int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[0].length; col++) {
					if(board[row][col].isMine()) {
						board[row][col].setIcon(new ImageIcon("bomb2.png"));
					}else {
						board[row][col].setText(board[row][col].getCount()+""); //stringe donusturdu +yla
						board[row][col].setEnabled(false);
					}
				}
			}
		}

		public void printMines() {
			for (int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[0].length; col++) {
					if(board[row][col].isMine()) {
						board[row][col].setIcon(new ImageIcon("bomb2.png"));
					}
				}
			}
		}
		
		
		public void countUpdate() {
			for (int row = 0; row < board.length; row++) {
				for(int col = 0; col < board[0].length; col++) {
					if(board[row][col].isMine()) {
						counting(row, col);
					}
				}
			}
		}
		
		public void counting(int row, int col) {
			for(int i = row-1 ; i <= row+1 ; i++) {
				for(int k = col-1 ; k <= col+1 ; k++) {
					try {
						int value = board[i][k].getCount();
						board[i][k].setCount(++value);
					}catch(Exception e) {}
				}
			}
		}
		
		public void open(int r, int c) {
			if(r < 0 || r >= board.length || c < 0 || c >= board[0].length || board[r][c].getText().length() > 0 || board[r][c].isEnabled() == false) {
				return ;
			}else if(board[r][c].getCount() != 0){ //etrafta mayin varsa
				board[r][c].setText(board[r][c].getCount()+"");
				board[r][c].setEnabled(false); //bolge ac
				openButtons++;
			}else { // 0 ise her yere bak etrafında
				openButtons++;
				board[r][c].setEnabled(false);
				open(r-1, c);
				open(r+1, c);
				open(r, c-1);
				open(r, c+1);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Button b = (Button) e.getComponent();
			if(e.getButton() == 1) {
				System.out.println("sol tik");
				if(b.isMine()) {
					JOptionPane.showMessageDialog(frame, "You pressed on the mine. Game is over");
					print();
				}else {
					open(b.getRow(), b.getCol());
					if(openButtons == (board.length * board[0].length) - 10) {
						JOptionPane.showMessageDialog(frame, "Congratulations, you win");
						print();
					}
				}
			}else if(e.getButton() == 3) {
				System.out.println("sag tik");
				if(!b.isFlag()) {
					b.setIcon(new ImageIcon("flag1.png"));
					b.setFlag(true);
				}else { //sag tikla bayragi kaldirmak iicn
					b.setIcon(null);
					b.setFlag(false);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
	
}
