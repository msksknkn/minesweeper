package window;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class GameWindow extends JFrame implements ActionListener{
	private GridBagLayout gbl = new GridBagLayout();
	//private GridBagConstraints gbc = new GridBagConstraints();
	private JPanel subPanel = new JPanel();
	private GamePanel gamePanel = new GamePanel();
	private int level = 0;
	JCheckBox yutoriButton = new JCheckBox("ゆとり用ボタン");
	public GameWindow() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setTitle("みねすいーぱ");
		setBounds(500,300,660,400);
		
		JPanel contentPanel = new JPanel();
		contentPanel.setBounds(0,0,660,380);
		contentPanel.setPreferredSize(new Dimension(610,320));
		contentPanel.setBackground(new Color(200,200,200));
		//サブパネルにレベル選択画面を入れる
		//ボタンを押すとゲームウィンドウを表示
		subPanel.setLayout(gbl);
		//配置を決めるGridBagConstraintsインスタンス生成
		GridBagConstraints gbc = new GridBagConstraints();
		//配置場所決定
		gbc.gridx= 1; gbc.gridy = 0;
		JLabel label = new JLabel("レベルを選んでね");
		this.gbl.setConstraints(label,gbc);
		subPanel.add(label);
		
		JButton level1 = new JButton("Level1");
		JButton level2 = new JButton("Level2");
		JButton level3 = new JButton("Level3");
		level1.setActionCommand("level1");
		gbc.gridx= 0; gbc.gridy = 1;
		this.gbl.setConstraints(level1,gbc);
		level2.setActionCommand("level2");
		gbc.gridx= 1; gbc.gridy = 1;
		this.gbl.setConstraints(level2,gbc);
		level3.setActionCommand("level3");
		gbc.gridx= 2; gbc.gridy = 1;
		this.gbl.setConstraints(level3,gbc);
		level1.addActionListener(this);
		level2.addActionListener(this);
		level3.addActionListener(this);
		
		
		gbc.gridx = 1; gbc.gridy = 2;
		yutoriButton.setActionCommand("yutori");
		this.gbl.setConstraints(yutoriButton,gbc);
		yutoriButton.addActionListener(this);
		
		
		subPanel.add(level1);
		subPanel.add(level2);
		subPanel.add(level3);
		subPanel.add(yutoriButton);
		contentPanel.add(this.subPanel);
		contentPanel.add(this.gamePanel);
		add(contentPanel);
		
		
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmdName = e.getActionCommand();
			if("level1".equals(cmdName)) {
				System.out.println("レベル1");
				this.subPanel.setVisible(false);
				this.level = 1;
				this.setBounds(500,300,200,280);
			}
			else if("level2".equals(cmdName)) {
				System.out.println("レベル2");
				this.subPanel.setVisible(false);
				this.level = 2;
				this.setBounds(500,300,340,420);
			}
			else if("level3".equals(cmdName)) {
				System.out.println("レベル3");
				this.subPanel.setVisible(false);
				this.level = 3;
				this.setBounds(500,300,620,420);
			}
			boolean easy = false;
			if(yutoriButton.isSelected()) {
				 easy = true;
			}
			if(this.level != 0) {
				this.gamePanel.startGame(this.level,easy);
				//gamePanel.playGame();
			}
				
		
	}
}
