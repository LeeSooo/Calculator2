package Calculator;

import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;

public class Calculator extends JFrame implements ActionListener
{	
	// 공통 필요(색상, 버튼)
	private Color c1 = new Color(230, 230, 230);
	private Color c2 = new Color(240, 240, 240);
	private Color c3 = new Color(250, 250, 250);
	private Color c4 = new Color(138, 186, 224);
	
	// 메뉴바
	private JMenuBar mb;	
	private JMenu menu;
	private JLabel norm;
	
	// 계산과정 패널
	private JPanel process;
	private JTextField pt;
	private JTextField rt;
	
	// 계산기 패널 (지우기, 숫자, 사칙연산)
	private JPanel calP;
	
	// 지우기 패널
	private JPanel erase;
	private JButton[] eBtn;
	private String[] e = {"CE", "C", "<-"};
	
	// 숫자 패널
	private JPanel num;
	private JButton[] nBtn;
	private String[] n = {"7", "8", "9", "4", "5", "6", "1", "2", "3", "", "0", ""};
	
	// 사칙연산 패널
	private JPanel fourOper;
	private JButton[] fOBtn;
	private String[] f = {"X","÷", "+", "-", "="};
	
	// 상대위치 설정
	GridBagLayout grid = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    
    // 이벤트 처리시 필요한 필드
    private String operator;
	private String left = null;
	private String right = null;
	private double result = 0;
	// <- 연산 필드
	private double abc;
	private String def;
	// 숫자 입력 시 제한 글씨
	private int nl;
	
    
	// 생성자
	public Calculator() 
	{
		setTitle("계산기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setSize(420, 620);
		setLocation(510, 25);
		
		// 메뉴, 계산 프레임, 버튼 프레임
		makeMenu();
		makeProcess();
		makeCal();
		
		// 메뉴바
		this.setJMenuBar(mb);
		
		// 계산 프레임, 버튼 프레임 삽입 
		add(process, BorderLayout.NORTH);
		add(calP, BorderLayout.CENTER);
		
		setResizable(false);
		setVisible(true);
	}

	// 메뉴바
	private void makeMenu() 
	{
		  mb = new JMenuBar(); 
		  mb.setOpaque(true);
		  mb.setBackground(c1);
		  
		  menu = new JMenu(" ≡ ");
		  menu.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 19));
		  
		  norm = new JLabel(" 표준 ");
		  norm.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 19));
		  
		  mb.add(menu);
		  mb.add(norm);
	}

	// 계산과정 패널
	private void makeProcess()
	{
		process = new JPanel();
		process.setLayout(new FlowLayout(FlowLayout.CENTER));
		process.setPreferredSize(new Dimension(400,150));
		process.setBackground(c1);
		
		// 계산 과정 텍스트필드
		pt = new JTextField(30) 
		{
            @Override
            public void setBorder(Border border) 
            {}
        };
		pt.setFont(new Font("나눔고딕", Font.PLAIN, 14));
		pt.setHorizontalAlignment(JTextField.RIGHT);
		pt.setBackground(c1);
		pt.setEditable(false);
		
		// 계산 결과 텍스트 필드
		rt = new JTextField(6) 
		{
            @Override
            public void setBorder(Border border) 
            {}
        };
		rt.setFont(new Font("나눔고딕", Font.BOLD, 70));
		rt.setHorizontalAlignment(JTextField.RIGHT);
		rt.setBackground(c1);
		rt.setText("0");
		rt.setEditable(false);
		
		process.add(pt);
		process.add(rt);
	}
	
	// 계산기 패널
	private void makeCal() 
	{
		calP = new JPanel();
		calP.setLayout(grid);

		// 지우기, 숫자, 사칙연산 패널 메서드
		erase();
		num();
		fourOper();
		
		// 위 패널의 크기 조절
		calP.setPreferredSize(new Dimension(400,400));
		erase.setPreferredSize(new Dimension(300,80));
		num.setPreferredSize(new Dimension(300,320));
		fourOper.setPreferredSize(new Dimension(100,400));
		
		// 위 패널의 상대위치
		make(erase, 0, 0, 1, 1);
        make(fourOper, 1, 0, 2, 2);
        make(num, 0, 1, 1, 1);
		
        // 패널 합치기?
		calP.add(erase);
		calP.add(num);
		calP.add(fourOper);
	}

	private void erase() 
	{
		// 지우기 버튼 패널
		// {"CE", "C", "<-"}
		erase = new JPanel();
		erase.setLayout(new GridLayout(1, 3, 1, 1));
		erase.setBackground(c1);
		
		eBtn = new JButton[e.length];
		for(int i=0; i < e.length; i++) 
		{
			eBtn[i] = new JButton(e[i]);
			eBtn[i].setFont(new Font("나눔고딕", Font.PLAIN, 17));
			eBtn[i].setBackground(c2);
			eBtn[i].setBorderPainted(false);
			eBtn[i].addActionListener(this);
			
			erase.add(eBtn[i]);
		}
	}
	
	private void num() 
	{
		// 숫자 패널
		// {"7", "8", "9", "4", "5", "6", "1", "2", "3", "", "0", ""};
		num = new JPanel();
		num.setLayout(new GridLayout(4, 3, 1, 1));
		nBtn = new JButton[n.length];
		for(int i=0; i < n.length; i++) 
		{
			nBtn[i] = new JButton(n[i]);
			nBtn[i].setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 18));
			nBtn[i].setBackground(c3);
			nBtn[i].setBorderPainted(false);
			nBtn[i].addActionListener(this);
			num.add(nBtn[i]);
			if(nBtn[i].getText() == "") 
				nBtn[i].setEnabled(false);
		}
	}

	private void fourOper() 
	{
		// 사칙연산 패널
		// {"X","÷", "+", "-", "="};
		fourOper = new JPanel();
		fOBtn = new JButton[n.length];
		fourOper.setLayout(new GridLayout(5, 1, 1, 1));
		for(int i=0; i < f.length; i++) 
		{
			fOBtn[i] = new JButton(f[i]);
			fOBtn[i].setFont(new Font("나눔고딕", Font.PLAIN, 17));
			fOBtn[i].setBorderPainted(false);
			fOBtn[i].addActionListener(this);
			fourOper.add(fOBtn[i]);
			if(fOBtn[i].getText() == "=") 
				fOBtn[i].setBackground(c4);
			else
				fOBtn[i].setBackground(c2);
		}
	}
	
    // 프레임을 x,y의 좌표의 시작점에서 w,h 크기로 만듭니다
    public void make(JComponent c, int x, int y, int w, int h) 
    {
           gbc.gridx = x;
           gbc.gridy = y;
           gbc.gridwidth = w;
           gbc.gridheight = h;
           
           // GridBagLayout의 GridBagConstraints의 set하는 방법
           grid.setConstraints(c, gbc);
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = e.getSource();
		String cmdValue = e.getActionCommand();
		
		// 지우개 버튼 이벤트 처리 
		// {"CE", "C", "⌫"}
		if(obj == eBtn[0] || obj == eBtn[1] || obj == eBtn[2])
		{
			if(obj == eBtn[0])
			{
				pt.setText("");
				rt.setText("0");
				left = null;
				right = null;
			}
			
			else if(obj == eBtn[1]) 
				rt.setText("0");
			
			else if (obj == eBtn[2])
			{
				abc = Double.parseDouble(rt.getText()) / 10;
				def = Double.toString(Math.floor(abc));
				rt.setText(def);
			}
		}
		
		// 사칙연산 버튼 이벤트 처리
		// {"X", "÷", "+", "-", "="};
		else if(obj == fOBtn[0] || obj == fOBtn[1] || obj == fOBtn[2] || obj == fOBtn[3] || obj == fOBtn[4]) 
		{
			right = rt.getText();
			rt.setText("");
			
			if(obj == fOBtn[0])
			{
				operator = "X";
				operation();
				pt.setText(left + operator);
			}
			else if(obj == fOBtn[1]) 
			{
				operator = "÷";
				operation();
				pt.setText(left + operator);
			}
			else if(obj == fOBtn[2]) 
			{
				operator = "+";
				operation();
				pt.setText(left + operator);
			}
			else if(obj == fOBtn[3]) 
			{
				operator = "-";
				operation();
				pt.setText(left + operator);
			}
			else if(obj == fOBtn[4]) 
			{
				operation();
				String re = result+"";
				pt.setText("");
				rt.setText(re);
				left = null;
				right = null;
			}
		}
		
		// 숫자 버튼 이벤트 처리
		for(int i=0; i<nBtn.length; i++) 
		{
			 if(obj == nBtn[i]) 
			{
				nl = rt.getText().length();
				// 9자리까지 입력 가능
				if(nl < 9) 
				{
					if(rt.getText().equals("0"))
					{
						rt.setText(cmdValue);
						break;
					}
					else
					{
						rt.setText(rt.getText() + cmdValue);
						break;
					}
				}
			}
		}
	}

	// 계산 메소드
	private void operation() 
	{
		if(operator.equals("+"))
		{
			if(left == null) 
				left = right;
			else 
			{
				result = Double.parseDouble(left) + Double.parseDouble(right);
				left = Double.toString(result);
			}
		}
		else if (operator.equals("-"))
		{
			if(left == null) 
				left = right;
			else 
			{
				result = Double.parseDouble(left) - Double.parseDouble(right);
				left = Double.toString(result);
			}
		}
		else if (operator.equals("X"))
		{
			if(left == null) 
				left = right;
			else 
			{
				result = Double.parseDouble(left) * Double.parseDouble(right);
				left = Double.toString(result);
			}
		}
		else if (operator.equals("÷"))
		{
			if(left == null) 
				left = right;
			else 
			{
				result = Double.parseDouble(left) / Double.parseDouble(right);
				left = Double.toString(result);
			}
		}
		
	}
}



