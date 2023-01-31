package view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.border.Border;
import javax.tools.Tool;

import uk.co.caprica.vlcj.binding.LibVlc;
//import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.test.basic.PlayerControlsPanel;

public class GameView extends JFrame {
	private JProgressBar loadingScreen;
	private JLabel startScreenBackground;
	private JPanel championSelection1;
	private JPanel championSelection2;//LeftMost panel 
	public JPanel getChampionSelection2() {
		return championSelection2;
	}
	public void setChampionSelection2(JPanel championSelection2) {
		this.championSelection2 = championSelection2;
	}

	private JLabel firstplayer ; 
	private JLabel secondplayer ;
	private JPanel championButton;
	private JTextArea championDetails; 
	private JPanel ButtonsForSelectionChampion ;
	private JButton confirm ;
	private JButton start ; 
	private JButton selectFirstLeader;
	private JButton selectSecondLeader;
	private int buttonpositioningindex;
	private String player1nameDialogue;
	private String player2nameDialogue;
	private JPanel boardPanel; 
	private JPanel leftmost; 
    private JTextArea currentChampionDetails , abilityDetails;
	private JPanel rightmost; 
	private JTextArea remainingChampionDetails;
	private JLabel currentChampionTurn;
	private JPanel turnOrderPanel;
	private JPanel playerUseLeaderPanel;
	private JPanel lowerButtonsPanel;
	private JButton FIRSTuseleaderAbility;
	private JButton SECONDuseleaderAbility;
	private JLabel firstPlayerInGame;
	private JLabel secondPlayerInGame;
    private JButton castAbility , firstAbility , secondAbility ,thirdAbility ,endTurn ;
	private File vlcInstallPath = new File("D:/vlc");
    private EmbeddedMediaPlayerComponent vedio;
    private JButton b2 ;
	private JButton b3 ;
	private JButton b4 ;
	private JButton b5 ;
	private JButton b6 ;
	private JButton b7 ;
	private EmbeddedMediaPlayerComponent introsong;
	private EmbeddedMediaPlayerComponent hiteffect;
	private EmbeddedMediaPlayerComponent bwendvedio;
	private EmbeddedMediaPlayerComponent gamesongboard;
	private EmbeddedMediaPlayerComponent liveWallpaper;
    private JPanel vediopanel;
    private JLabel champDet;
    private JButton next;
    private JButton back;
    private JLabel champselection1background;
    private JLabel champselection2background;
    private JButton next2;
    private JButton next3;
    private JTextArea champname;
    private JLabel intro;
    private JButton clicktostart;
    private JLabel gamelabel ; 
    private JButton firstplayerlabel;
    private JButton secondplayerlabel;
    private JTextArea firstplayername;
    private JButton currentplayerbanner;
    private JButton leftmostlabel2;
    private JButton currentIconButton;
    private JButton ok;
    private JButton rightmostlabel;
    private JLabel winner;
    private JTextArea winnername;
    private JButton exit;
    private JButton skip;
    private JPanel vediopanel1;
    private JPanel countdownpanel;
    private JButton loadingbutton;
    private PlayerControlsPanel tmp;
    private JPanel teams;
    private  ArrayList <String> hiteffectmusicnames;
	
    public EmbeddedMediaPlayerComponent getGamesongboard() {
		return gamesongboard;
	}
	public void setGamesongboard(EmbeddedMediaPlayerComponent gamesongboard) {
		this.gamesongboard = gamesongboard;
	}
	public EmbeddedMediaPlayerComponent getBwendvedio() {
		return bwendvedio;
	}
	public EmbeddedMediaPlayerComponent getHiteffect() {
		return hiteffect;
	}
	public JButton getB2() {
		return b2;
	}
	public void setB2(JButton b2) {
		this.b2 = b2;
	}
	public JButton getB3() {
		return b3;
	}
	public void setB3(JButton b3) {
		this.b3 = b3;
	}
	public JButton getB4() {
		return b4;
	}
	public void setB4(JButton b4) {
		this.b4 = b4;
	}
	public JButton getB5() {
		return b5;
	}
	public void setB5(JButton b5) {
		this.b5 = b5;
	}
	public JButton getB6() {
		return b6;
	}
	public void setB6(JButton b6) {
		this.b6 = b6;
	}
	public JButton getB7() {
		return b7;
	}
	public void setB7(JButton b7) {
		this.b7 = b7;
	}

	private EmbeddedMediaPlayerComponent countdown;
    public EmbeddedMediaPlayerComponent getCountdown() {
		return countdown;
	}
	public void setCountdown(EmbeddedMediaPlayerComponent countdown) {
		this.countdown = countdown;
	}


    
    public JPanel getTeams() {
		return teams;
	}
	public void setTeams(JPanel teams) {
		this.teams = teams;
	}
	public JButton getLoadingbutton() {
		return loadingbutton;
	}
	public void setLoadingbutton(JButton loadingbutton) {
		this.loadingbutton = loadingbutton;
	}
	public JPanel getCountdownpanel() {
		return countdownpanel;
	}
	public void setCountdownpanel(JPanel countdownpanel) {
		this.countdownpanel = countdownpanel;
	}
	public JPanel getVediopanel1() {
		return vediopanel1;
	}
	public void setVediopanel1(JPanel vediopanel1) {
		this.vediopanel1 = vediopanel1;
	}
	public JButton getExit() {
		return exit;
	}
	public void setExit(JButton exit) {
		this.exit = exit;
	}
	public JTextArea getWinnername() {
		return winnername;
	}
	public void setWinnername(JTextArea winnername) {
		this.winnername = winnername;
	}
	public JLabel getWinner() {
		return winner;
	}
	public void setWinner(JLabel winner) {
		this.winner = winner;
	}
	public JButton getRightmostlabel() {
		return rightmostlabel;
	}
	public void setRightmostlabel(JButton rightmostlabel) {
		this.rightmostlabel = rightmostlabel;
	}
	public JButton getOk() {
		return ok;
	}
	public void setOk(JButton ok) {
		this.ok = ok;
	}
	public JLabel getCurrentChampBannerLabel() {
		return currentChampBannerLabel;
	}
	public void setCurrentChampBannerLabel(JLabel currentChampBannerLabel) {
		this.currentChampBannerLabel = currentChampBannerLabel;
	}

	private JLabel currentChampBannerLabel;

    
    

	public JButton getCurrentIconButton() {
		return currentIconButton;
	}
	public void setCurrentIconButton(JButton currentIconButton) {
		this.currentIconButton = currentIconButton;
	}
	public JButton getLeftmostlabel2() {
		return leftmostlabel2;
	}
	public void setLeftmostlabel2(JButton leftmostlabel2) {
		this.leftmostlabel2 = leftmostlabel2;
	}

	public JButton getCurrentplayerbanner() {
		return currentplayerbanner;
	}
	public void setCurrentplayerbanner(JButton currentplayerbanner) {
		this.currentplayerbanner = currentplayerbanner;
	}
	public JButton getSecondplayerlabel() {
		return secondplayerlabel;
	}
	public void setSecondplayerlabel(JButton secondplayerlabel) {
		this.secondplayerlabel = secondplayerlabel;
	}
	public JButton getFirstplayerlabel() {
		return firstplayerlabel;
	}
	public void setFirstplayerlabel(JButton firstplayerlabel) {
		this.firstplayerlabel = firstplayerlabel;
	}
	public JLabel getGamelabel() {
		return gamelabel;
	}
	public void setGamelabel(JLabel gamelabel) {
		this.gamelabel = gamelabel;
	}
	public JLabel getInto() {
		return intro;
	}
	public void setInto(JLabel into) {
		this.intro = into;
	}
	public JButton getNext2() {
		return next2;
	}
	public void setNext2(JButton next2) {
		this.next2 = next2;
	}
	public JButton getNext3() {
		return next3;
	}
	public void setNext3(JButton next3) {
		this.next3 = next3;
	}
	public JLabel getChampselection1background() {
		return champselection1background;
	}
	public void setChampselection1background(JLabel champselection1background) {
		this.champselection1background = champselection1background;
	}
	public JLabel getChampselection2background() {
		return champselection2background;
	}
	public void setChampselection2background(JLabel champselection2background) {
		this.champselection2background = champselection2background;
	}
	public JButton getNext() {
		return next;
	}

	private JPanel firstpage;
   
    public JLabel getChampDet() {
		return champDet;
	}
	public EmbeddedMediaPlayerComponent getVedio() {
		return vedio;
	}
	public JButton getPunchAbility() {
		return punchAbility;
	}

	private JButton punchAbility;
	
	
    public JTextArea getCurrentChampionDetails() {
		return currentChampionDetails;
	}
    public ArrayList<String> getHiteffectmusicnames() {
		return hiteffectmusicnames;
	}
	public EmbeddedMediaPlayerComponent getIntrosong() {
		return introsong;
	}
	public JButton getSkip() {
		return skip;
	}
	public void setSkip(JButton skip) {
		this.skip = skip;
	}
	public JLabel getIntro() {
		return intro;
	}
	public void setIntro(JLabel intro) {
		this.intro = intro;
	}
	public JButton getClicktostart() {
		return clicktostart;
	}
	public void setClicktostart(JButton clicktostart) {
		this.clicktostart = clicktostart;
	}
	public JTextArea getChampname() {
		return champname;
	}
	public void setChampname(JTextArea champname) {
		this.champname = champname;
	}
	public JPanel getChampionSelection1() {
		return championSelection1;
	}
	public void setChampionSelection1(JPanel championSelection1) {
		this.championSelection1 = championSelection1;
	}
	public void setChampDet(JLabel champDet) {
		this.champDet = champDet;
	}
	public JButton getBack() {
		return back;
	}
	public EmbeddedMediaPlayerComponent getLiveWallpaper() {
		return liveWallpaper;
	}
	public JLabel getCurrentChampionTurn() {
		return currentChampionTurn;
	}

	public JButton getCastAbility() {
		return castAbility;
	}

	public JButton getFirstAbility() {
		return firstAbility;
	}

	public JButton getSecondAbility() {
		return secondAbility;
	}

	public JButton getThirdAbility() {
		return thirdAbility;
	}

	public JButton getEndTurn() {
		return endTurn;
	}

	public JProgressBar getLoadingScreen() {
		return loadingScreen;
	}

	public JPanel getPlayerUseLeaderPanel() {
		return playerUseLeaderPanel;
	}

	public JPanel getLowerButtonsPanel() {
		return lowerButtonsPanel;
	}

	public JButton getFIRSTuseleaderAbility() {
		return FIRSTuseleaderAbility;
	}

	public JButton getSECONDuseleaderAbility() {
		return SECONDuseleaderAbility;
	}

	public JLabel getFirstPlayerInGame() {
		return firstPlayerInGame;
	}

	public JLabel getSecondPlayerInGame() {
		return secondPlayerInGame;
	}

	public JLabel getStartScreenBackground() {
		return startScreenBackground;
	}

	public JPanel getChampionButton() {
		return championButton;
	}

	public static void main(String[] args) throws FontFormatException, IOException {
		GameView g = new GameView();
	}

	public JTextArea getChampionDetails() {
		return championDetails;
	}



	public JLabel getFirstplayer() {
		return firstplayer;
	}

	public JLabel getSecondplayer() {
		return secondplayer;
	}

	public JPanel getButtonsForSelectionChampion() {
		return ButtonsForSelectionChampion;
	}

	public JButton getConfirm() {
		return confirm;
	}

	public JButton getStart() {
		return start;
	}

	public JButton getSelectFirstLeader() {
		return selectFirstLeader;
	}

	public JButton getSelectSecondLeader() {
		return selectSecondLeader;
	}

	public void setButtonpositioningindex(int buttonpositioningindex) {
		this.buttonpositioningindex = buttonpositioningindex;
	}

	public int getButtonpositioningindex() {
		return buttonpositioningindex;
	}
	
	public String getPlayer1nameDialogue() {
		return player1nameDialogue;
	}
	public String getPlayer2nameDialogue() {
		return player2nameDialogue;
	}
	public int getButtonPositioningIndex() {
		return buttonpositioningindex;
	}

	public JPanel getBoardPanel() {
		return boardPanel;
	}



	public JPanel getRightmost() {
		return rightmost;
	}

	public JTextArea getRemainingChampionDetails() {
		return remainingChampionDetails;
	}

	public JPanel getTurnOrderPanel() {
		return turnOrderPanel;
	}
	public JPanel getLeftmost() {
		return leftmost;
	}

	public JTextArea getAbilityDetails() {
		return abilityDetails;
	}
	public void setPlayer1nameDialogue(JOptionPane jOptionPane) {
		// TODO Auto-generated method stub
		
	}
	public void setPlayer2nameDialogue(JOptionPane jOptionPane) {
		// TODO Auto-generated method stub
		
	}

    
    
	public GameView() throws FontFormatException, IOException {

		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension dim = toolkit.getScreenSize();
		this.setSize(dim.width,dim.height);
		buttonpositioningindex =20;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setVisible(true);
		this.setResizable(false);
		this.setTitle("Marvel: Ultimate War");
		GraphicsEnvironment ge3 = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge3.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf")));
		Font font1 = new Font("STEAMER", Font.PLAIN|Font.BOLD,40);
		Font font2 = new Font(Font.SERIF, Font.BOLD, 20);
		Font font = null ,font3 = null;
		 
		 
	    try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf"));
				font3 = Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf"));
			} catch (FontFormatException | IOException e2) {
	
				e2.printStackTrace();
			}
	    
	    
        Font used = new Font(font.getName(),Font.BOLD,20);
		Font used2 = new Font(font3.getName(),Font.BOLD,15);
		ImageIcon image = new ImageIcon("Marvel_Logo.png");
		
		
		skip = new JButton ();
		ImageIcon skipicon = new ImageIcon("skip.png");
		skip.setIcon(skipicon);
        skip.setContentAreaFilled(false);
        skip.setBounds(850, 950, 200, 75);
        skip.setBorderPainted(false);
        skip.setOpaque(false);

        
        vediopanel1 = new JPanel();
        vediopanel1.setSize(dim.width,dim.height);
        vediopanel1.setLayout(null);
       
        
        
        
        
        

		this.setIconImage(image.getImage());
		
		NativeLibrary.addSearchPath("libvlc", "C:/VideoLAN/VLC");
	    vedio = new EmbeddedMediaPlayerComponent();
	    vedio.setBounds(0, 0, dim.width, dim.height);
	    vediopanel1.add(skip);
	    vediopanel1.add(vedio);
	    
	    introsong = new EmbeddedMediaPlayerComponent();
	    introsong.setBounds(0, 0, 1, 1);
	    
	    hiteffect = new  EmbeddedMediaPlayerComponent();
	    hiteffect.setBounds(0, 0, 1, 1);

	 
	    this.setContentPane(vediopanel1);
	     
	    vedio.mediaPlayer().media().play("marvel2.mp4");


	    
        hiteffectmusicnames= new ArrayList<String>();
     
        hiteffectmusicnames.add("1.wav");
        hiteffectmusicnames.add("2.wav");
        hiteffectmusicnames.add("3.wav");
        hiteffectmusicnames.add("4.wav");
        hiteffectmusicnames.add("5.wav");
        
        gamesongboard = new EmbeddedMediaPlayerComponent();
        gamesongboard.setBounds(0, 0, 1, 1);
        
        bwendvedio = new EmbeddedMediaPlayerComponent();
        bwendvedio.setBounds(0, 0, dim.width, dim.height);
	    
       countdown = new EmbeddedMediaPlayerComponent();
       countdown.setBounds(0, 0, dim.width, dim.height);
       
	   countdownpanel = new JPanel();
	   countdownpanel.setSize(dim.width,dim.height);
	   countdownpanel.setLayout(null);
	   
	   loadingbutton = new JButton("loading");
	   ImageIcon loadingicon = new ImageIcon("loading.png");
	   loadingbutton.setIcon(loadingicon);
	   loadingbutton.setContentAreaFilled(false);
	   loadingbutton.setBounds(850, 950, 200, 75);
	   loadingbutton.setBorderPainted(false);
	   loadingbutton.setOpaque(false);
	   loadingbutton.setVisible(false);
	   
		this.revalidate();
		this.repaint();
	   
	    ImageIcon intoicon = new ImageIcon("we2.jpg");
	    intro = new JLabel("",intoicon,JLabel.CENTER);
	    intro.setLayout(null);
	    intro.setSize(dim.width,dim.height);
	    
	    clicktostart = new JButton ();
		ImageIcon clicktoicon = new ImageIcon("clicktostart.png");
		clicktostart.setIcon(clicktoicon);
		clicktostart.setBounds(750, 820, 350, 260);
		clicktostart.setFont(font2);
		clicktostart.setOpaque(false);
		clicktostart.setBorderPainted(false);
		clicktostart.setLayout(null);
		clicktostart.setContentAreaFilled(false);
		intro.add(clicktostart);
		do {
			player1nameDialogue = JOptionPane.showInputDialog("First Player Name");
		}while (player1nameDialogue.equals(""));
		player1nameDialogue = player1nameDialogue.toUpperCase();
		
		do {
			player2nameDialogue= JOptionPane.showInputDialog("Second Player Name");
		}while (player2nameDialogue.equals(""));
		player2nameDialogue = player2nameDialogue.toUpperCase();
		
		ImageIcon backgroundImage = new ImageIcon("space.jpg");
	
		startScreenBackground = new JLabel("",backgroundImage,JLabel.CENTER);
		startScreenBackground.setLayout(null);
		startScreenBackground.setSize(dim.width,dim.height);
		startScreenBackground.setLayout(null);
	

		firstpage = new JPanel();
		firstpage.setLayout(null);
		firstpage.setBounds(0,0,1920,1080);
		firstpage.setVisible(true);
		firstpage.setOpaque(false);
		firstpage.setBackground(new Color(0,0,0,25));
		
		
		championButton = new JPanel ();
		championButton.setPreferredSize(new Dimension(600,this.getHeight()));
		championButton.setBounds(150,0,1500,1080);
		championButton.setLayout(new FlowLayout ());
		championButton.setVisible(true);
		championButton.setOpaque(false);
		championButton.setBackground(new Color(0,0,0,40));
		
		firstpage.add(championButton);
		this.revalidate();
		this.repaint();
		ImageIcon nexticon = new ImageIcon("nextupdated.png");
		next = new JButton ();
		next.setIcon(nexticon);

		Border emptyBorder = BorderFactory.createEmptyBorder();
		next.setBorder(emptyBorder);
		next.setHideActionText(true);
		next.setBorder(emptyBorder);
		
		next.setBounds(1650, 820, 250, 200);
		next.setFont(font2);
		next.setOpaque(false);
		next.setBorderPainted(false);
		next.setLayout(null);
		next.setContentAreaFilled(false);
		firstpage.add(next);
		startScreenBackground.add(firstpage);
		
		this.revalidate();
		this.repaint();
        
		

		championDetails = new JTextArea();
		championDetails.setBounds(150,25,400,1000);
		championDetails.setEditable(false);
		championDetails.setOpaque(false);
		championDetails.setFont(font1);
		championDetails.setForeground(Color.WHITE);
		
		
		selectFirstLeader = new JButton ();
		ImageIcon setfirstleader = new ImageIcon("selectfirst.png");
		selectFirstLeader.setIcon(setfirstleader);
		selectFirstLeader.setBounds(750, 820, 350, 260);
		selectFirstLeader.setFont(font2);
		selectFirstLeader.setEnabled(false);
		selectFirstLeader.setOpaque(false);
		selectFirstLeader.setBorderPainted(false);
		selectFirstLeader.setLayout(null);
		selectFirstLeader.setContentAreaFilled(false);
		
		
		selectSecondLeader = new JButton ();
		ImageIcon secondleader = new ImageIcon("selectsec.png");
		selectSecondLeader.setIcon(secondleader);
		selectSecondLeader.setBounds(750, 820, 350, 260);
		selectSecondLeader.setFont(font2);
		selectSecondLeader.setEnabled(false);
		selectSecondLeader.setOpaque(false);
		selectSecondLeader.setBorderPainted(false);
		selectSecondLeader.setLayout(null);
		selectSecondLeader.setContentAreaFilled(false);
		
		
		confirm = new JButton ();
		ImageIcon confirmicon = new javax.swing.ImageIcon("confirm2.png");
		confirm.setIcon(confirmicon);
		confirm.setBounds(1500, 300, 350, 260);
		confirm.setFont(font2);
		confirm.setOpaque(true);
		confirm.setBorderPainted(false);
		confirm.setLayout(null);
		confirm.setContentAreaFilled(false);
		confirm.setBorder(null);
		
		back = new JButton ();
		ImageIcon backicon = new ImageIcon("back2.png");
		back.setIcon(backicon);
		back.setBounds(1500, 600, 350, 260);
		back.setFont(font2);
		back.setOpaque(false);
		back.setBorderPainted(false);
		back.setLayout(null);
		back.setContentAreaFilled(false);
		
		start = new JButton ();
		start.setText("START");
		start.setBounds(850, 920, 200, 100);
		start.setFont(font2);
		start.setOpaque(false);
		start.setBorderPainted(true);
		start.setLayout(null);
		start.setContentAreaFilled(false);
	
		
		ButtonsForSelectionChampion = new JPanel();
		ButtonsForSelectionChampion.setOpaque(false);
		ButtonsForSelectionChampion.setBackground(new Color(0,0,0,65));
		ButtonsForSelectionChampion.add(selectFirstLeader);
		ButtonsForSelectionChampion.add(selectSecondLeader);
		ButtonsForSelectionChampion.add(confirm);
	

		
		
		ImageIcon firstplayericon = new ImageIcon("secplayericon.png");
		ImageIcon secondplayericon= new ImageIcon("firstplayericon.png");

		firstplayerlabel = new JButton(player1nameDialogue +"'S TEAM", firstplayericon);
		firstplayerlabel.setHorizontalTextPosition(SwingConstants.CENTER);
		firstplayerlabel.setBounds(50, 25, 300, 200);

		firstplayerlabel.setFont(used);
		firstplayerlabel.setForeground(Color.WHITE);
		firstplayerlabel.setEnabled(true);
		firstplayerlabel.setOpaque(true);
		firstplayerlabel.setBorderPainted(false);
		firstplayerlabel.setLayout(null);
		firstplayerlabel.setContentAreaFilled(false);
		


        secondplayerlabel = new JButton(player2nameDialogue +"'S TEAM", secondplayericon);
        secondplayerlabel.setHorizontalTextPosition(SwingConstants.CENTER);
        secondplayerlabel.setBounds(50, 25, 300, 200);
        
        secondplayerlabel.setFont(used);
        secondplayerlabel.setForeground(Color.WHITE);
        secondplayerlabel.setEnabled(true);
		secondplayerlabel.setOpaque(true);
		secondplayerlabel.setBorderPainted(false);
		secondplayerlabel.setLayout(null);
		secondplayerlabel.setContentAreaFilled(false);

		
		championSelection1 = new JPanel( );
		championSelection1.setBounds(0, 0, 1920, 1080);
		championSelection1.setVisible(true);
		championSelection1.setOpaque(false);
		championSelection1.setBackground(new Color(0,0,0,65));
		championSelection1.setLayout(null);

		championSelection1.add(firstplayerlabel);
		
		ImageIcon nexticon2 = new ImageIcon("nextupdated.png");
		next2 = new JButton ();
		next2.setIcon(nexticon2);
		next2.setBounds(1650, 820, 250, 200);
		next2.setFont(font2);
		next2.setOpaque(false);
		next2.setBorderPainted(false);
		next2.setLayout(null);
		next2.setContentAreaFilled(false);
		
		championSelection1.add(next2);
		
		championSelection2 = new JPanel( );
		championSelection2.setBounds(0, 0, 1920, 1080);
		championSelection2.setVisible(true);
		championSelection2.setOpaque(false);
		championSelection2.setBackground(new Color(0,0,0,65));
		championSelection2.setLayout(null);
		championSelection2.add(secondplayerlabel);
		
		next3 = new JButton ();
		next3.setIcon(nexticon2);
		next3.setBounds(1650, 820, 250, 200);
		next3.setFont(font2);
		next3.setOpaque(false);
		next3.setBorderPainted(false);
		next3.setLayout(null);
		next3.setContentAreaFilled(false);
		
		championSelection2.add(next3);

		championSelection1.revalidate();
		championSelection1.repaint();
		championSelection2.revalidate();
		championSelection2.repaint();

		
		ImageIcon gamelabelback = new ImageIcon("live2gif.gif");
		
		gamelabel = new JLabel("",gamelabelback,JLabel.CENTER);
		gamelabel.setLayout(null);
		gamelabel.setSize(dim.width,dim.height);
	
		
		
		boardPanel = new JPanel();
		boardPanel.setBounds(300, 200, 1300, 600);
		boardPanel.setLayout(new GridLayout (5,5));
		boardPanel.setVisible(true);
		boardPanel.setOpaque(true);
		boardPanel.setBackground(new Color(7,7,7,240));
	
	    teams = new JPanel ();
	    teams.setBounds(0, 446, 300,400 );
	    teams.setLayout(new FlowLayout ());
	    teams.setVisible(true);
	    teams.setOpaque(false);
	    teams.setBackground(new Color(0,0,0,40));
	    
		
		gamelabel.add(boardPanel);
		
	    ImageIcon useleader = new ImageIcon("leader.png");
		FIRSTuseleaderAbility = new JButton(player1nameDialogue +" use leader Ability", useleader);
		FIRSTuseleaderAbility.setHorizontalTextPosition(SwingConstants.CENTER);
		FIRSTuseleaderAbility.setBounds(550, 25, 300, 200);	  
		FIRSTuseleaderAbility.setFont(used2);
		FIRSTuseleaderAbility.setForeground(Color.WHITE);
		FIRSTuseleaderAbility.setEnabled(true);
		FIRSTuseleaderAbility.setOpaque(true);
		FIRSTuseleaderAbility.setBorderPainted(false);
		FIRSTuseleaderAbility.setLayout(null);
		FIRSTuseleaderAbility.setContentAreaFilled(false);
		
		
		SECONDuseleaderAbility = new JButton(player2nameDialogue +" use leader Ability", useleader);
		SECONDuseleaderAbility.setHorizontalTextPosition(SwingConstants.CENTER);
		SECONDuseleaderAbility.setBounds(950, 25, 300, 200);
		SECONDuseleaderAbility.setFont(used2);
		SECONDuseleaderAbility.setForeground(Color.WHITE);
		SECONDuseleaderAbility.setEnabled(true);
		SECONDuseleaderAbility.setOpaque(true);
		SECONDuseleaderAbility.setBorderPainted(false);
		SECONDuseleaderAbility.setLayout(null);
		SECONDuseleaderAbility.setContentAreaFilled(false);


	    gamelabel.add(FIRSTuseleaderAbility);
	    gamelabel.add(SECONDuseleaderAbility);
	    
		
	    this.revalidate();
	    this.repaint();
	    
		
		castAbility = new JButton ();
		ImageIcon casticon = new ImageIcon("castability.png");
		castAbility.setIcon(casticon);
		castAbility.setBounds(0, 880, 380, 200);
		castAbility.setFont(font2);
		castAbility.setOpaque(false);
		castAbility.setBorderPainted(false);
		castAbility.setLayout(null);
		castAbility.setContentAreaFilled(false);
		
		
		firstAbility = new JButton ();
		firstAbility.setBounds(384, 880, 380, 200);
		ImageIcon firsticon = new ImageIcon("first.png");
		firstAbility.setIcon(firsticon);
		firstAbility.setOpaque(false);
		firstAbility.setBorderPainted(false);
		firstAbility.setLayout(null);
		firstAbility.setContentAreaFilled(false);
		
		secondAbility = new JButton ();
		secondAbility.setBounds(768, 880, 380, 200);
		ImageIcon secondicon = new ImageIcon("second.png");
		secondAbility.setIcon(secondicon);
		secondAbility.setOpaque(false);
		secondAbility.setBorderPainted(false);
		secondAbility.setLayout(null);
		secondAbility.setContentAreaFilled(false);
		
		thirdAbility = new JButton ();
		thirdAbility.setBounds(1152, 880, 380, 200);
		ImageIcon thirdicon = new ImageIcon("third.png");
		thirdAbility.setIcon(thirdicon);
		thirdAbility.setOpaque(false);
		thirdAbility.setBorderPainted(false);
		thirdAbility.setLayout(null);
		thirdAbility.setContentAreaFilled(false);
		
		
		endTurn = new JButton ();
		ImageIcon endicon = new ImageIcon("endturn2.png");
		endTurn.setIcon(endicon);
		endTurn.setBounds(1536, 880, 370, 200);
		endTurn.setOpaque(false);
		endTurn.setBorderPainted(false);
		endTurn.setLayout(null);
		endTurn.setContentAreaFilled(false);
		
		
		gamelabel.add(castAbility);
		gamelabel.add(firstAbility);
		gamelabel.add(secondAbility);
		gamelabel.add(thirdAbility);
		gamelabel.add(endTurn);
		
		this.revalidate();
		this.repaint();
		

		currentChampionDetails = new JTextArea();
		currentChampionDetails.setBounds(0,0,600,600);
		currentChampionDetails.setOpaque(false);
		currentChampionDetails.setEditable(false);


		
		punchAbility = new JButton ("PUNCH"); //
		ImageIcon punchicon = new ImageIcon ("punchicon.png");
		punchAbility.setIcon(punchicon);
		punchAbility.setBounds(60,780,200,100); //
		punchAbility.setOpaque(false);
		punchAbility.setBorderPainted(true);
		punchAbility.setLayout(null);
		punchAbility.setContentAreaFilled(false);
		punchAbility.setVisible(false); //
		
	   
		gamelabel.add(punchAbility);

	
		this.revalidate();
		this.repaint();

	    remainingChampionDetails = new JTextArea();
	    remainingChampionDetails.setOpaque(false);
	    remainingChampionDetails.setEditable(false);
	  
	    remainingChampionDetails.setEditable(false);
	    remainingChampionDetails.setVisible(false);
	  

	    
	    turnOrderPanel = new JPanel();
	    turnOrderPanel.setBounds(1600, 215, 320, 600);
	    turnOrderPanel.setLayout(new FlowLayout());
	    turnOrderPanel.setBackground(new Color(0,0,0,0));
	    gamelabel.add(turnOrderPanel);
	    
		
		
		
	}


};	
