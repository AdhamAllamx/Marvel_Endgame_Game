package Controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;
import engine.Game;
import engine.GameListener;
import engine.Player;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.effects.Effect;
import model.world.Champion;
import model.world.Cover;
import model.world.Direction;
import view.ButtonTest;
import view.GameView;
import view.SoftJButton;

public class Controller implements ActionListener, MouseListener , KeyListener {
	private Game game;
	private GameView view;
	private ArrayList<JButton> loadingScreenChampionButtons;
	private Player firstPlayer;
	private Player secondPlayer;
	private ArrayList<String> champselectednames;
	private ArrayList<JButton> teamsbutton;
	private ArrayList<Champion> temp;
    private boolean stopplz;	
	private int [] firstPlayerIndices = new int[3];
	private int [] secondPlayerIndices = new int [3];
    private boolean antherclickFlaginboardbanner = false;
	private int x = -1;
	private int i =1;
	private JButton [][] boardButtons = new JButton [5][5] ;
	private ArrayList<Integer>  abIndex  = new ArrayList<>();
	private boolean abilityKeyPressed = false;
	private boolean castAbilityKeyPressed = false;
	private Ability ability;
	private boolean championSelectedToSetAsLeader = false;
	private ImageIcon subback;
	private ImageIcon champselection1 ;
	private ImageIcon champselection2;
	private boolean flagleader1 =false;
	private boolean flagleader2 =false;
	private boolean flagnext =false;
	private boolean flagselect1 = false;
	private ImageIcon abilityicon ;
	private ImageIcon subback2;
	private Font font = new Font("Arial", Font.PLAIN, 10);
  	private Color darkSlateGray = new Color(49,79,79);
  	private Color dimGray = new Color(105,105,105);
  	private Color slateGray = new Color(112,138,144);
  	private Color lightSlateGray = new Color(119,136,153);
  	private Color gray = new Color(190,190,190);
  	private Color lightGray = new Color(211,211,211);
  	private Color[] colors = { Color.black, darkSlateGray, dimGray, slateGray,   lightSlateGray, gray, lightGray, Color.white };
  	private Thread runThread;	
  	private int fadeSpeed = 50;
    private boolean animate = false;
    private boolean allowAnimate = true;
    private Color foreground = Color.black;
    public void setDefaultForeground(Color foreground) { this.foreground = foreground; }

	
	public Controller() throws IOException, FontFormatException {
		Game.loadAbilities("Abilities.csv");
		Game.loadChampions("Champions.csv");
		loadingScreenChampionButtons = new ArrayList<JButton>();
		view = new GameView();
		teamsbutton = new ArrayList<JButton>();
		champselectednames = new ArrayList<String>();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Miamagon-5YBx.ttf")));
	      temp = new ArrayList<Champion>();
		GraphicsEnvironment ge2 = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge2.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("MoonwalkMiss-BgZB.ttf")));
		
		GraphicsEnvironment ge3 = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge3.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf")));

		GraphicsEnvironment ge4 = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge4.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("BraveEightyoneRegular-ZVGvm.ttf")));
		view.getLoadingbutton().addActionListener(this);
		championSelectionButtons(view);
		firstPlayer = new Player(view.getPlayer1nameDialogue());
		secondPlayer = new Player(view.getPlayer2nameDialogue());
		view.getSkip().addActionListener(this);
		view.getVedio().addKeyListener(this);
		view.getCountdown().addKeyListener(this);
		view.getConfirm().addActionListener(this);
		view.getSelectFirstLeader().addActionListener(this);
		view.getSelectSecondLeader().addActionListener(this);
		view.getStart().addActionListener(this);
		view.getBack().addActionListener(this);
		view.getFirstAbility().addActionListener(this);
		view.getSecondAbility().addActionListener(this);
		view.getThirdAbility().addActionListener(this);
		view.getNext().addActionListener(this);
		view.getNext2().addActionListener(this);
		view.getNext3().addActionListener(this);
		view.getClicktostart().addActionListener(this);
		
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			 
		    public void eventDispatched(AWTEvent event) {
		        if(event instanceof KeyEvent){
		            KeyEvent keyEvent = (KeyEvent) event;
		            if(keyEvent.getID() == KeyEvent.KEY_PRESSED){
		                if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
		                	if (stopplz == false) {
		                	view.getLoadingbutton().doClick();
		                	stopplz =true;}
		                
		                	
		                }
		                try {
		        			if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT)
		        				game.move(Direction.LEFT);
		        			if ( keyEvent.getKeyCode()  == KeyEvent.VK_RIGHT)
		        				game.move(Direction.RIGHT);
		        			if ( keyEvent.getKeyCode()  == KeyEvent.VK_UP)
		        				game.move(Direction.UP);
		        			if ( keyEvent.getKeyCode()  == KeyEvent.VK_DOWN)
		        				game.move(Direction.DOWN);
		        		
		        		}
		        		catch (NotEnoughResourcesException ex){
		        			JOptionPane.showMessageDialog(view, ex.getMessage());
		        			 
		        		}
		        		catch (UnallowedMovementException ex) {
		        			JOptionPane.showMessageDialog(view, ex.getMessage());
		        			
		        		}
		                
		        		
		                try {
		                if(keyEvent.getKeyChar()== 'w') {
	        				game.attack(Direction.UP);
		                int h = (int)(Math.random()*3)+1 ;
	                	view.getHiteffect().mediaPlayer().media().play(view.getHiteffectmusicnames().get(h));}
	        			else if(keyEvent.getKeyChar() =='a') {
	        				game.attack(Direction.LEFT);
	        				int h = (int)(Math.random()*3)+1 ;
		                	view.getHiteffect().mediaPlayer().media().play(view.getHiteffectmusicnames().get(h));
	        			}
	        				
	        			else if(keyEvent.getKeyChar()== 's') {
	        				game.attack(Direction.DOWN);
        				int h = (int)(Math.random()*3)+1 ;
	                	view.getHiteffect().mediaPlayer().media().play(view.getHiteffectmusicnames().get(h));
	        			}
	        			else if(keyEvent.getKeyChar()=='d') {
	        				game.attack(Direction.RIGHT);
	        				int h = (int)(Math.random()*3)+1 ;
		                	view.getHiteffect().mediaPlayer().media().play(view.getHiteffectmusicnames().get(h));
	        			}
	        			
		                }
		                catch (NotEnoughResourcesException ex){
		        			JOptionPane.showMessageDialog(view, ex.getMessage());
		        			 
		        		}
		                catch(InvalidTargetException ex) {
		        			JOptionPane.showMessageDialog(view, ex.getMessage());
		        		}
		                catch(ChampionDisarmedException ex ) {
		        			JOptionPane.showMessageDialog(view, ex.getMessage());
		        		}
		                
		                refreshBoard();
		        		fillAndRefreshTurnorder();
		        		view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
		               
		        		
		            }
		        
		        }
		    }
		}, AWTEvent.KEY_EVENT_MASK + AWTEvent.MOUSE_EVENT_MASK + AWTEvent.MOUSE_MOTION_EVENT_MASK);
		
		
		view.getCastAbility().addActionListener(this);
		
		view.getFIRSTuseleaderAbility().addActionListener(this);
		view.getSECONDuseleaderAbility().addActionListener(this);
		
		view.getPunchAbility().addActionListener(this);//----------

		view.getEndTurn().addActionListener(this);
		view.revalidate();
		view.repaint();
	}
	//why was this method static
	private void championSelectionButtons(GameView view) {
		for (int i = 0; i< Game.getAvailableChampions().size(); i++) {
			JButton b = new JButton(Game.getAvailableChampions().get(i).getName());
			b.addActionListener(this);
			b.setBorderPainted(false);
			b.setOpaque(false);
	                b.setLayout(null);
	                b.setContentAreaFilled(false);
			loadingIconscard(b);
			
			loadingScreenChampionButtons.add(b); 
			view.getChampionButton().add(b); 
			view.revalidate();
			view.repaint();
		}
	}
	
	private void loadingIconscard(JButton b) {
		String champ = b.getText();
		ImageIcon icon = new ImageIcon() ;
		switch (champ)
		{
		case "Captain America" :  icon= new ImageIcon ("capcard.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpoolcard.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrangecardd.png"); 
		break;
		case "Black Panther" : icon= new ImageIcon ("blackcard.png"); 
		break;
		case "StarLord" : icon= new ImageIcon ("starlordcard.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("helacard.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulkcard.png");
		break;
		case "Antman" : icon= new ImageIcon ("antmancard.png");
		break;
		case "Ironman" : icon= new ImageIcon ("ironmancardd.png");
		break;
		case "Loki" : icon= new ImageIcon ("lokicard.png");
		break;
		case "Wanda" : icon= new ImageIcon ("wandacard.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spidermancard.png");
		break;
		case "Thor" : icon= new ImageIcon ("thorcard.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venomcard.png");
		break;
		case "Thanos" : icon= new ImageIcon ("thanoscard.png");
		break;
		case "Black Widow" : icon = new ImageIcon("blackwidowcard.png");
		}
		b.setIcon(icon);
		champselectednames.add(b.getText());
		b.setText("");
		
	}
	private void loadingIconscard2(JButton b) {
		String champ = b.getText();
		ImageIcon icon = new ImageIcon() ;
		switch (champ)
		{
		case "Captain America" :  icon= new ImageIcon ("capcardr.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpoolcardr.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrangecarddr.png"); 
		break;
		case "Black Panther" : icon= new ImageIcon ("blackcardr.png"); 
		break;
		case "StarLord" : icon= new ImageIcon ("starlordcardr.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("helacardr.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulkcardr.png");
		break;
		case "Antman" : icon= new ImageIcon ("antmancardr.png");
		break;
		case "Ironman" : icon= new ImageIcon ("ironmancarddr.png");
		break;
		case "Loki" : icon= new ImageIcon ("lokicardr.png");
		break;
		case "Wanda" : icon= new ImageIcon ("wandacardr.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spidermancardr.png");
		break;
		case "Thor" : icon= new ImageIcon ("thorcardr.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venomcardr.png");
		break;
		case "Thanos" : icon= new ImageIcon ("thanoscardr.png");
		break;
		case "Black Widow" : icon = new ImageIcon("blackwidowcardr.png");
		}
		b.setIcon(icon);
		
		b.setText("");
		
	}
		
	




	private void gameOverAfterAction() {
		Player winner = game.checkGameOver();
		if (winner==null) {
			return;
		}
		view.getGamesongboard().mediaPlayer().controls().stop();
		
		ImageIcon winnerIcon;
		if(winner ==game.getFirstPlayer())
		{
			 winnerIcon = new ImageIcon("playerone1win.jpg");
		}
		else 
			 winnerIcon = new ImageIcon("playertwowin.jpg");

		view.setWinner(new JLabel("",winnerIcon,JLabel.CENTER));
		view.setWinnername(new JTextArea(winner.getName() + " IS THE WINNER!"+'\n'+ "GAME OVER"));
		view.getWinnername().setBounds(200, 200, 800, 600);
		view.getWinnername().setOpaque(false);
		view.getWinnername().setEditable(false);
		view.getWinner().setSize(1920,1080);
		view.getWinner().setLayout(null);
		view.getGamelabel().setVisible(false);
		 Font font = null;
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf"));
			} catch (FontFormatException | IOException e2) {
				e2.printStackTrace();
			}
		Font used = new Font(font.getName(),Font.BOLD,60);
		view.getWinnername().setFont(used);
		view.getWinnername().setForeground(Color.white);
		view.getWinner().add(view.getWinnername());
		view.setExit(new JButton());
		ImageIcon exiticon = new ImageIcon("exit.png");
		view.getExit().setBounds(1200, 820, 350, 260);
		view.getExit().addActionListener(this);
		view.getExit().setContentAreaFilled(false);
		view.getExit().setOpaque(false);
		view.getExit().setBorderPainted(false);
		view.getExit().setIcon(exiticon);
		view.getWinner().add(view.getExit());
		view.setContentPane(view.getWinner());
		view.revalidate();
		view.repaint();
		
		
	}
	
	private boolean hasPunch() {
		for (Ability ab: game.getCurrentChampion().getAbilities())
			if (ab.getName().equals("Punch"))
				return true;
		return false;
	}
	
	private int getPunchIndex() {
		for (int i = 0; i< game.getCurrentChampion().getAbilities().size();i++)
			if (game.getCurrentChampion().getAbilities().get(i).getName().equals("Punch"))
				return i;
		return 0;
	}
	private void shakeButton(int x , int y , JButton b ) {
	  
	
	
	
			    final Point point = b.getLocation();
			    final int delay = 75;
			    Runnable r = new Runnable() {
			      @Override
			      public void run() {
			        for (int i = 0; i < 30; i++) {
			          try {

			            moveButton(new Point(point.x + 5, point.y),b);
			            Thread.sleep(delay);
			            moveButton(point,b);
			            Thread.sleep(delay);
			            moveButton(new Point(point.x - 5, point.y),b);
			            Thread.sleep(delay);
			            moveButton(point,b);
			            Thread.sleep(delay);
			          } catch (InterruptedException ex) {
			            ex.printStackTrace();
			          }
			        }
			      }
			    };
			    Thread t = new Thread(r);
			    t.start();
			  }
	  private void moveButton( Point p ,JButton b) {
	    SwingUtilities.invokeLater(new Runnable() {
	      @Override
	      public void run() {
	    	animate(b,p,1000,1000/60);
	    	
	        view.revalidate();
	        view.repaint();
	
	      }
	    });
	  }
	  private void animate(JComponent component, Point newPoint, int frames, int interval) {
		    Rectangle compBounds = component.getBounds();
		    switch(x) {
			   case 4 : newPoint.x = 0;break;
			   case 3 : newPoint.x = 1 ;break;
			   case 2 : newPoint.x =2 ; break;
			   case 1 : newPoint.x = 3 ; break;
			   case 0 : newPoint.x = 4;break;
			   }
		    Point oldPoint = new Point(compBounds.x, compBounds.y),
		          animFrame = new Point((newPoint.x - oldPoint.x) / frames,
		                                (newPoint.y - oldPoint.y) / frames);

		    new Timer(interval, new ActionListener() {
		        int currentFrame = 0;
		        public void actionPerformed(ActionEvent e) {
		            component.setBounds(oldPoint.x + (animFrame.x * currentFrame),
		                                oldPoint.y + (animFrame.y * currentFrame),
		                                compBounds.width,
		                                compBounds.height);

		            if (currentFrame != frames)
		                currentFrame++;
		            else
		                ((Timer)e.getSource()).stop();
		        }
		    }).start();
		    view.revalidate();
   	       view.repaint();
		}
	  public void createAndShowGUI(SoftJButton b) {
	   
	      Timer  alphaChanger = new Timer(30, new ActionListener() {

	            private float incrementer = -.03f;

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                float newAlpha = b.getAlpha() + incrementer;
	                if (newAlpha < 0) {
	                    newAlpha = 0;
	                    incrementer = -incrementer;
	                } else if (newAlpha > 1f) {
	                    newAlpha = 1f;
	                    incrementer = -incrementer;
	                }
	                b.setAlpha(newAlpha);
	                b.setAlpha(newAlpha);
	            }
	        });
	        alphaChanger.start();
	        Timer uiChanger = new Timer(3500, new ActionListener() {

	            private final LookAndFeelInfo[] laf = UIManager.getInstalledLookAndFeels();
	            private int index = 1;

	            @Override
	            public void actionPerformed(ActionEvent e) {
	                try {
	                    UIManager.setLookAndFeel(laf[0].getClassName());
	                    SwingUtilities.updateComponentTreeUI(b);
	                
	                } catch (Exception exc) {
	                    exc.printStackTrace();
	                }
	         
	            }
	        });
	        uiChanger.start();
	  
	      
	        }
	  
	    
	private void refreshBoard() {
		//gameOverAfterAction();
        view.getBoardPanel().removeAll();
        JProgressBar myBar = null;
		for (int i = Game.getBoardheight()-1; i>=0;i--) {
            for (int j = 0; j<=4;j++) {
                JButton b = new JButton();
    	        
    	        view.revalidate();
    	        view.repaint();
                if (game.getBoard()[i][j] !=null) {
                    if (game.getBoard()[i][j] instanceof Cover) {       
                        myBar = new JProgressBar();
                        myBar.setValue((int)((((float)((((Cover)game.getBoard()[i][j]).getCurrentHP())))/((float)((((Cover)game.getBoard()[i][j]).getMaxhp()))))*100));
                        myBar.setPreferredSize(new Dimension(b.getWidth(),10));
                        myBar.setForeground(Color.GREEN);
                        myBar.setAlignmentY(100);
                        b.setBorder(new EtchedBorder(Color.GRAY, Color.GRAY));
                        b.setBackground(Color.decode("#818c8b"));
                        b.setBorderPainted(true);
            			b.setOpaque(false);
            	        b.setContentAreaFilled(false);
            	        b.add(myBar,Component.BOTTOM_ALIGNMENT);
            	        b.setIcon(new ImageIcon ("shield.png"));
                         
                    }
                    else {
                    	if(game.getBoard()[i][j]==game.getCurrentChampion()) {
                  	    	b.addKeyListener(this);
                  	    	
                  	    	b.doClick();
                  	    	b.setSelected(true);
                  	     //  shakeButton(i,j,b);
                  	    	SoftJButton tmp = new SoftJButton(b.getText(),b.getIcon());
                  	    	tmp.addKeyListener(this);
                  	    	createAndShowGUI(tmp);
                  	    	b = tmp;
                            
                 
                  	
                  	         
                  	    	b.removeMouseListener(this);
                    	}
                    	else {
                    		b.addMouseListener(this);
                 
                    	}
                    	b.setText(((Champion)game.getBoard()[i][j]).getName());
                    	loadingIcons2(b);
                    	b.setAlignmentX(Component.CENTER_ALIGNMENT);
              	        myBar = new JProgressBar();
              	        myBar.setPreferredSize(new Dimension(b.getWidth(),5));
              	        myBar.setValue((int)((((float)((((Champion)game.getBoard()[i][j]).getCurrentHP())))/((float)((((Champion)game.getBoard()[i][j]).getMaxHP()))))*100));
                        myBar.setForeground(Color.GREEN);
                        myBar.setAlignmentY(100);
                    	b.setBorderPainted(true);
            			b.setOpaque(false);
            	       // b.setLayout(null);
            	        b.setContentAreaFilled(false);
                    	b.setText("<html><center>" +((Champion) game.getBoard()[i][j]).getName() + "<br>" +((Champion)game.getBoard()[i][j]).getCurrentHP()+"</center></html>");

                        if (firstPlayer.getTeam().contains(game.getBoard()[i][j])) {
                        	b.setForeground(Color.BLUE);
                        }
                        else {
                        	b.setForeground(Color.RED);
                        }
                        b.add(myBar,Component.BOTTOM_ALIGNMENT);
                    }
                }
                else {
                	b.setBorderPainted(true);
        			b.setOpaque(false);
        	        b.setLayout(null);
        	        b.setContentAreaFilled(false);
                }
                boardButtons[i][j] =  b;
                
                view.getBoardPanel().add(b);
                view.revalidate();
                view.repaint();
            }
        }
		gameOverAfterAction();
    }

	private void loadingIcons2(JButton b) {
		String champ = b.getText();
		ImageIcon icon = new ImageIcon() ;
		switch (champ)
		{
		case "Captain America" :  icon= new ImageIcon ("captainamerciab.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpoolb.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrangeb.png"); 
		break;
		case "Black Panther" : icon= new ImageIcon ("blackpantherb.png"); 
		break;
		case "StarLord" : icon= new ImageIcon ("starlordb.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("helab.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulk1b.png");
		break;
		case "Antman" : icon= new ImageIcon ("antmanb.png");
		break;
		case "Ironman" : icon= new ImageIcon ("iron manb.png");
		break;
		case "Loki" : icon= new ImageIcon ("lokib.png");
		break;
		case "Wanda" : icon= new ImageIcon ("wandab.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spidermanb.png");
		break;
		case "Thor" : icon= new ImageIcon ("thor pngb.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venomb.png");
		break;
		case "Thanos" : icon= new ImageIcon ("thanosb.png");
		break;
		case "Black Widow" : icon = new ImageIcon("blackwidowb.png");
		}
		b.setIcon(icon);
		b.setText("");
	}
	
	private void loadingIcons(JButton b) {
		String champ = b.getText();
		ImageIcon icon = new ImageIcon() ;
		switch (champ)
		{
		case "Captain America" :  icon= new ImageIcon ("captainamercia.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpool.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrange.png"); 
		break;
		case "Black Panther" : icon= new ImageIcon ("blackpanther.png"); 
		break;
		case "StarLord" : icon= new ImageIcon ("starlord.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("hela.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulk1.png");
		break;
		case "Antman" : icon= new ImageIcon ("antman.png");
		break;
		case "Ironman" : icon= new ImageIcon ("iron man.png");
		break;
		case "Loki" : icon= new ImageIcon ("loki.png");
		break;
		case "Wanda" : icon= new ImageIcon ("wanda.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spiderman.png");
		break;
		case "Thor" : icon= new ImageIcon ("thor png.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venom.png");
		break;
		case "Thanos" : icon= new ImageIcon ("thanos.png");
		break;
		case "Black Widow" : icon = new ImageIcon("bw.jpg");
		}
		b.setIcon(icon);

	}
	
	public static void main(String[] args) throws IOException, FontFormatException {
		new Controller();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() instanceof JButton) {
			JButton b = (JButton)e.getSource();
			if (b == view.getConfirm()) {
				if (x<0 || !championSelectedToSetAsLeader) {
					JOptionPane.showMessageDialog(view, "Need to Select Champion First");
				}
				else {
					if (i<=6) {
						confirmLogic(x);
						i++;

						championSelectedToSetAsLeader = false;
					}
				}
			}
			else if (b == view.getSelectFirstLeader()) {
				if (!championSelectedToSetAsLeader) //-------------
					JOptionPane.showMessageDialog(view, "Need to Select Champion First"); //-----------------
				else {
					firstPlayer.setLeader(Game.getAvailableChampions().get(x));
					for (int i = 0; i<firstPlayerIndices.length; i++)
						loadingScreenChampionButtons.get(firstPlayerIndices[i]).setEnabled(false);
					for (int i = 0; i<secondPlayerIndices.length; i++)
						loadingScreenChampionButtons.get(secondPlayerIndices[i]).setEnabled(true);
					view.getSelectSecondLeader().setEnabled(true);
					view.getSelectFirstLeader().setEnabled(false);
					view.getChampionDetails().setText(null); //--------------
					championSelectedToSetAsLeader = false;
					flagleader1 = true;
				}
			}
			else if (b == view.getSelectSecondLeader()) {
				if (!championSelectedToSetAsLeader) //---------------
					JOptionPane.showMessageDialog(view, "Need to Select Champion First"); //-----------------
				else {
					secondPlayer.setLeader(Game.getAvailableChampions().get(x));
					for (int i = 0; i<secondPlayerIndices.length; i++)
						loadingScreenChampionButtons.get(secondPlayerIndices[i]).setEnabled(false);
					view.getSelectSecondLeader().setEnabled(false);
					view.getStart().setEnabled(true);
					view.getChampionDetails().setText(null);
					game = new Game(firstPlayer,secondPlayer);
					championSelectedToSetAsLeader = false;//----------
					flagleader2 = true;
				}
				
			}
			else if (b == view.getStart()) {

				view.getButtonsForSelectionChampion().setVisible(false);
				view.getChampionDetails().setVisible(false);
				view.getChampionButton().setVisible(false);
				view.setLayout(null);			
				view.add(view.getBoardPanel());
				view.getLeftmost().setVisible(true);
				fillAndRefreshTurnorder();
				view.add(view.getRightmost());
				view.getBoardPanel().setBounds(250,80,770,view.getHeight()-180); //1025
				view.getPlayerUseLeaderPanel().setVisible(true);
				view.getFIRSTuseleaderAbility().setText(firstPlayer.getLeader().getName() + " LEADER ABILITY");//-------
				view.getSECONDuseleaderAbility().setText(secondPlayer.getLeader().getName() + " LEADER ABILITY");//----------
				view.getLowerButtonsPanel().setVisible(true);
				view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
				refreshBoard();
				view.revalidate();
				view.repaint();
			}
			else if (b == view.getFirstAbility()){

				loadabilityicon(game.getCurrentChampion().getName(),abilityicon);
				JOptionPane.showMessageDialog(view,game.getCurrentChampion().getAbilities().get(0).toString2(), game.getCurrentChampion().getName()+"'s Ability", JOptionPane.INFORMATION_MESSAGE, abilityicon);
				abIndex.add(0);
				abilityKeyPressed = true;
				view.getFirstAbility().setSelected(true);
				view.getSecondAbility().setSelected(false);
				view.getThirdAbility().setSelected(false);
				view.getPunchAbility().setSelected(false);
			}
			else if (b == view.getSecondAbility()){

				loadabilityicon(game.getCurrentChampion().getName(),abilityicon);			
				JOptionPane.showMessageDialog(view,game.getCurrentChampion().getAbilities().get(1).toString2(), game.getCurrentChampion().getName()+"'s Ability", JOptionPane.INFORMATION_MESSAGE, abilityicon);
				abIndex.add(1);
				abilityKeyPressed = true;
				view.getFirstAbility().setSelected(false);
				view.getSecondAbility().setSelected(true);
				view.getThirdAbility().setSelected(false);
				view.getPunchAbility().setSelected(false);
			}
			else if (b == view.getThirdAbility()){

				loadabilityicon(game.getCurrentChampion().getName(),abilityicon);
				JOptionPane.showMessageDialog(view,game.getCurrentChampion().getAbilities().get(2).toString2(), game.getCurrentChampion().getName()+"'s Ability", JOptionPane.INFORMATION_MESSAGE, abilityicon);
				abIndex.add(2);
				abilityKeyPressed = true;
				view.getFirstAbility().setSelected(false);
				view.getSecondAbility().setSelected(false);
				view.getThirdAbility().setSelected(true);
				view.getPunchAbility().setSelected(false);
			}
			else if (b == view.getPunchAbility()) {
				int i = getPunchIndex();
				loadabilityicon(game.getCurrentChampion().getName(),abilityicon);
				JOptionPane.showMessageDialog(view,game.getCurrentChampion().getAbilities().get(i).toString2(), game.getCurrentChampion().getName()+"'s Ability", JOptionPane.INFORMATION_MESSAGE, abilityicon);				
				abIndex.add(i);
				abilityKeyPressed = true;
				view.getFirstAbility().setSelected(false);
				view.getSecondAbility().setSelected(false);
				view.getThirdAbility().setSelected(false);
				view.getPunchAbility().setSelected(true);
			}
			else if (b== view.getCastAbility()) {
				if(!abilityKeyPressed) {
					JOptionPane.showMessageDialog(view, " You need to select an Ability "); 
				}
				else {
			
					   ability = game.getCurrentChampion().getAbilities().get(abIndex.get(abIndex.size()-1));
					   castAbilityKeyPressed = true;
					   abilityGUIlogic(ability);
					   abilityKeyPressed = false;
					   view.getFirstAbility().setSelected(false);
					   view.getSecondAbility().setSelected(false);
					   view.getThirdAbility().setSelected(false);
					   view.getPunchAbility().setSelected(false);
				}
			}
			else if (b == view.getFIRSTuseleaderAbility()) {
					if (firstPlayer.getTeam().contains(game.getCurrentChampion())) {
						try {
							game.useLeaderAbility();
							view.getFIRSTuseleaderAbility().setEnabled(true);
							refreshBoard();
							fillAndRefreshTurnorder();
							view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
						} catch (LeaderNotCurrentException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						} catch (LeaderAbilityAlreadyUsedException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						}
					}
					else {
						JOptionPane.showMessageDialog(view,"Current Champion is not in your team");
					}
			}
			else if (b == view.getSECONDuseleaderAbility()) {
					if (secondPlayer.getTeam().contains(game.getCurrentChampion())) {
						try {
							game.useLeaderAbility();
							view.getSECONDuseleaderAbility().setEnabled(false);
							refreshBoard();
							fillAndRefreshTurnorder();
							view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
						} catch (LeaderNotCurrentException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						} catch (LeaderAbilityAlreadyUsedException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						}	
					}
					else {
						JOptionPane.showMessageDialog(view,"Current Champion is not in your team");
					}
			}
			else if (b == view.getEndTurn()) {
				game.endTurn();
				view.getFirstAbility().setSelected(false);
				view.getSecondAbility().setSelected(false);
				view.getThirdAbility().setSelected(false);
				loadingIconsCardsforBoard(view.getCurrentplayerbanner(),game.getCurrentChampion());
				if (hasPunch()) {
					view.getPunchAbility().setVisible(true); //-----------------
				}
				else
					view.getPunchAbility().setVisible(false);
				refreshBoard();
				fillAndRefreshTurnorder();
				view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n"+game.getCurrentChampion().effectsList());
			}
			
			else if(b== view.getBack()) {
				view.getChampDet().setVisible(false);
				view.remove(view.getChampDet());
				view.setContentPane(view.getStartScreenBackground());
				view.getStartScreenBackground().setVisible(true);
				view.revalidate();
				view.repaint();
			}
			else if (b==view.getNext()){
				if(flagnext == false)
				{
					JOptionPane.showMessageDialog(view, "Need to Select Champions First");
				}
				else {
				flagselect1= true;
				view.getStartScreenBackground().setVisible(false);
				view.setContentPane(view.getChampselection1background());
				view.revalidate();
			    view.repaint();
				}
			}
			else if(b==view.getNext2()) {
				if(flagleader1 == false)
				{
					JOptionPane.showMessageDialog(view, "Need to Select Champion First");
				}
				else {
				view.getChampselection1background().setVisible(false);
				view.setContentPane(view.getChampselection2background());
				view.revalidate();
			    view.repaint();
				}
			}
			else if (b==view.getNext3()) {
				if(flagleader2 == false)
				{
					JOptionPane.showMessageDialog(view, "Need to Select Champion First");
				}
				else {
				view.getIntrosong().mediaPlayer().controls().stop();	
				view.getChampselection2background().setVisible(false);
				view.getCountdownpanel().add(view.getLoadingbutton());
				view.getCountdownpanel().add(view.getCountdown());
				
		
			    view.setContentPane(view.getCountdownpanel());	
			    view.getCountdown().mediaPlayer().media().play("countdown.mp4");
				view.revalidate();
			    view.repaint();
				}	
			}
			else if (b==view.getLoadingbutton())
			{
				
				view.getCountdownpanel().setVisible(false);
				view.getCountdown().mediaPlayer().controls().stop();
				
				
				view.remove(view.getCountdownpanel());
				fillAndRefreshTurnorder();	
				view.add(view.getGamesongboard());
				view.getGamesongboard().mediaPlayer().media().play("The Avengers Theme Song.mp3");
	
				view.getGamesongboard().mediaPlayer().controls().setRepeat(true);
		   
				view.getGamesongboard().mediaPlayer().media().play("The Avengers Theme Song.mp3");
			
                view.setCurrentplayerbanner(new JButton());
                loadingIconsCardsforBoard(view.getCurrentplayerbanner(),game.getCurrentChampion());
				view.getCurrentplayerbanner().setLayout(null);
				view.getCurrentplayerbanner().setBorderPainted(false);
				view.getCurrentplayerbanner().setContentAreaFilled(false);
				view.getCurrentplayerbanner().setOpaque(false);
				view.getCurrentplayerbanner().addActionListener(this);
				view.getCurrentplayerbanner().setBounds(25, 190, 256, 256);
				view.getGamelabel().add(view.getCurrentplayerbanner());
				view.getGamelabel().add(view.getHiteffect());
		  
				while(!game.getTurnOrder().isEmpty()) {
					temp.add((Champion) game.getTurnOrder().remove());
				}
				
				
					
						 view.setB2( new JButton(temp.get(0).getName())) ;
						 view.setB3( new JButton(temp.get(1).getName())); 
						 view.setB4( new JButton(temp.get(2).getName())) ;
						 view.setB5 (new JButton(temp.get(3).getName())) ;
						 view.setB6( new JButton(temp.get(4).getName())) ;
						 view.setB7( new JButton(temp.get(5).getName())) ;
						
						view.getB2().addActionListener(this);
						view.getB2().setBorderPainted(false);
						view.getB2().setOpaque(false);
						view.getB2().setLayout(null);
						view.getB2().setContentAreaFilled(false);
						loadingIconscard2(view.getB2());
						teamsbutton.add(view.getB2());
						view.getTeams().add(view.getB2()); 
						
						view.getB3().addActionListener(this);
						view.getB3().setBorderPainted(false);
						view.getB3().setOpaque(false);
						view.getB3().setLayout(null);
						view.getB3().setContentAreaFilled(false);
						loadingIconscard2(view.getB3());
						teamsbutton.add(view.getB3());
						
						view.getTeams().add(view.getB3()); 
						view.getB4().addActionListener(this);
						view.getB4().setBorderPainted(false);
						view.getB4().setOpaque(false);
						view.getB4().setLayout(null);
						view.getB4().setContentAreaFilled(false);
						loadingIconscard2(view.getB4());
						teamsbutton.add(view.getB4());
						
						view.getTeams().add(view.getB4()); 
						view.getB5().addActionListener(this);
						view.getB5().setBorderPainted(false);
						view.getB5().setOpaque(false);
						view.getB5().setLayout(null);
						view.getB5().setContentAreaFilled(false);
						loadingIconscard2(view.getB5());
						teamsbutton.add(view.getB5());
						view.getTeams().add(view.getB5()); 
						
						view.getB6().addActionListener(this);
						view.getB6().setBorderPainted(false);
						view.getB6().setOpaque(false);
						view.getB6().setLayout(null);
						view.getB6().setContentAreaFilled(false);
						loadingIconscard2(view.getB6());
						teamsbutton.add(view.getB6());
						
						view.getTeams().add(view.getB6()); 
						view.getB7().addActionListener(this);
						view.getB7().setBorderPainted(false);
						view.getB7().setOpaque(false);
						view.getB7().setLayout(null);
						view.getB7().setContentAreaFilled(false);
						loadingIconscard2(view.getB7());
						teamsbutton.add(view.getB7());
						
						view.getTeams().add(view.getB7()); 
					
			
				for(int i = 0 ; i < temp.size();i++) {
					game.getTurnOrder().insert(temp.get(i));
					
					
				}
					
					
				
				view.getGamelabel().add(view.getTeams());
				view.revalidate();
				view.repaint();
					
				
				
				
				 Font font = null;
					try {
						font = Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf"));
					} catch (FontFormatException | IOException e2) {
						e2.printStackTrace();
					}
				Font used = new Font(font.getName(),Font.BOLD,20);
				view.getRemainingChampionDetails().setFont(used);
				view.getRemainingChampionDetails().setBounds(0, 550, 300, 250);
				view.getRemainingChampionDetails().setForeground(Color.RED);
				
			    view.getGamelabel().add(view.getRemainingChampionDetails());
				
				view.getFirstplayerlabel().setBounds(50, 25, 300, 200);
				view.getSecondplayerlabel().setBounds(1550, 25, 300, 200);
				view.getGamelabel().add(view.getFirstplayerlabel());
				view.getGamelabel().add(view.getSecondplayerlabel());
				view.setContentPane(view.getGamelabel());
				refreshBoard();
				view.revalidate();
			    view.repaint();
				
			}
			else if (b==view.getB2()||b==view.getB3()|| b==view.getB4()|| b==view.getB5()|| b==view.getB6()|| b==view.getB7()) {
				if(antherclickFlaginboardbanner==true) {
					antherclickFlaginboardbanner = false;
					view.getGamelabel().remove(view.getCurrentChampBannerLabel());
					view.revalidate();
					view.repaint();
					
				}
			
			Champion tmp = null;
	
			     if (b==view.getB2())
			    	 tmp = temp.get(0);
			     else if (b==view.getB3())
			    	 tmp =temp.get(1);
			    
			     else if (b==view.getB4())
			    	 tmp =temp.get(2);
			     else if (b==view.getB5())
			    	 tmp =temp.get(3);
			     else if (b==view.getB6())
			    	 tmp =temp.get(4);
			     else if (b==view.getB7())
			    	 tmp =temp.get(5);
			     
				switch (tmp.getName())
				{
				case "Captain America" : subback2 = new ImageIcon("capback2.jpg");
		
				break;
				case "Deadpool" : subback2 = new ImageIcon("deadpoolback2.jpg");
				
				break;
				case "Dr Strange" :subback2 = new ImageIcon("drback2.jpg");
			
				break;
				case "Black Panther" : subback2 = new ImageIcon("blackback2.jpg");
			
				break;
				case "StarLord" : subback2 = new ImageIcon("starback2.png");
	
				break;
				case "Hela" : subback2 = new ImageIcon("helaback2.jpg");
	
				break;
				case "Hulk" : subback2 = new ImageIcon("hulkback2.jpg");
			
				break;
				case "Antman" : subback2 = new ImageIcon("antback2.jpg");

				break;
				case "Ironman" :subback2 = new ImageIcon("ironback2.jpg");
	
				break;
				case "Loki" : subback2 = new ImageIcon("lokiback2.jpg");
		
				break;
				case "Wanda" :subback2 = new ImageIcon("wandaback2.jpg");
		
				break;
				case "Spiderman" :subback2 = new ImageIcon("spiderback2.jpg");
			
				break;
				case "Thor" : subback2 = new ImageIcon("thorback2.jpg");
	
				break;
				case "Venom" : subback2 = new ImageIcon("venomback2.jpg");
		
				break;
				case "Thanos" :subback2 = new ImageIcon("thanosback2.jpg");
			
				break;
				case "Black Widow" :subback2 = new ImageIcon("blackwidowback2.jpg");
				
			
				
				}
				view.getBoardPanel().setVisible(false);
				 Font font = null;
					try {
						font = Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf"));
					} catch (FontFormatException | IOException e2) {
						e2.printStackTrace();
					}
				Font used = new Font(font.getName(),Font.BOLD,30);
				
				view.setOk(new JButton());
	            ImageIcon okicon = new ImageIcon("ok.png");
				view.getOk().setIcon(okicon);
				view.getOk().setLayout(null);
				view.getOk().setBorderPainted(false);
				view.getOk().setContentAreaFilled(false);
				view.getOk().setOpaque(false);
				view.getOk().addActionListener(this);
				view.getOk().setBounds(800,250,360, 260);
				String s =tmp.toString3();
		
				if (tmp.equals(firstPlayer.getLeader()) ||tmp.equals(secondPlayer.getLeader())) {
					s += "Leader: YES } \n \n";
				}
				else {
					s += "Leader: NO } \n \n";
				}
				s += tmp.effectsList();
				view.getCurrentChampionDetails().setText(s);
				
			    view.getCurrentChampionDetails().setFont(used);
				view.setCurrentChampBannerLabel(new JLabel("",subback2,JLabel.CENTER));
				if(tmp.getName().equals("Black Widow")||tmp.getName().equals("Hulk"))
				{
					view.getCurrentChampionDetails().setForeground(Color.BLACK);
				
				}
				else {
					view.getCurrentChampionDetails().setForeground(Color.WHITE);
					
				}
				view.getCurrentChampBannerLabel().setLayout(null);
				view.getCurrentChampBannerLabel().setSize(1300,600);
				view.getCurrentChampBannerLabel().setBounds(300, 200, 1300, 600);
				view.getCurrentChampBannerLabel().add(view.getOk());
				view.getCurrentChampBannerLabel().add(view.getCurrentChampionDetails());
				view.getCurrentChampBannerLabel().setVisible(true);
				view.getGamelabel().add(view.getCurrentChampBannerLabel());
				antherclickFlaginboardbanner =true;
		
				view.revalidate();
				view.repaint();
				
				
			}
			
			else if (b==view.getClicktostart()){
				view.getClicktostart().setVisible(false);
				view.setContentPane(view.getStartScreenBackground());
				view.revalidate();
				view.repaint();
			}
			else if(b == view.getCurrentplayerbanner()){
				
				switch (game.getCurrentChampion().getName())
				{
				case "Captain America" : subback2 = new ImageIcon("capback2.jpg");
		
				break;
				case "Deadpool" : subback2 = new ImageIcon("deadpoolback2.jpg");
				
				break;
				case "Dr Strange" :subback2 = new ImageIcon("drback2.jpg");
			
				break;
				case "Black Panther" : subback2 = new ImageIcon("blackback2.jpg");
			
				break;
				case "StarLord" : subback2 = new ImageIcon("starback2.png");
	
				break;
				case "Hela" : subback2 = new ImageIcon("helaback2.jpg");
	
				break;
				case "Hulk" : subback2 = new ImageIcon("hulkback2.jpg");
			
				break;
				case "Antman" : subback2 = new ImageIcon("antback2.jpg");

				break;
				case "Ironman" :subback2 = new ImageIcon("ironback2.jpg");
	
				break;
				case "Loki" : subback2 = new ImageIcon("lokiback2.jpg");
		
				break;
				case "Wanda" :subback2 = new ImageIcon("wandaback2.jpg");
		
				break;
				case "Spiderman" :subback2 = new ImageIcon("spiderback2.jpg");
			
				break;
				case "Thor" : subback2 = new ImageIcon("thorback2.jpg");
	
				break;
				case "Venom" : subback2 = new ImageIcon("venomback2.jpg");
		
				break;
				case "Thanos" :subback2 = new ImageIcon("thanosback2.jpg");
			
				break;
				case "Black Widow" :subback2 = new ImageIcon("blackwidowback2.jpg");
				
			
				
				}
				view.getBoardPanel().setVisible(false);
				 Font font = null;
					try {
						font = Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf"));
					} catch (FontFormatException | IOException e2) {
						e2.printStackTrace();
					}
				Font used = new Font(font.getName(),Font.BOLD,30);
				
				view.setOk(new JButton());
	            ImageIcon okicon = new ImageIcon("ok.png");
				view.getOk().setIcon(okicon);
				view.getOk().setLayout(null);
				view.getOk().setBorderPainted(false);
				view.getOk().setContentAreaFilled(false);
				view.getOk().setOpaque(false);
				view.getOk().addActionListener(this);
				view.getOk().setBounds(800,250,360, 260);
				String s =game.getCurrentChampion().toString3();
		
				if (game.getCurrentChampion().equals(firstPlayer.getLeader()) || game.getCurrentChampion().equals(secondPlayer.getLeader())) {
					s += "Leader: YES } \n \n";
				}
				else {
					s += "Leader: NO } \n \n";
				}
				s += game.getCurrentChampion().effectsList();
				view.getCurrentChampionDetails().setText(s);
				
			    view.getCurrentChampionDetails().setFont(used);
				view.setCurrentChampBannerLabel(new JLabel("",subback2,JLabel.CENTER));
				if(game.getCurrentChampion().getName().equals("Black Widow")||game.getCurrentChampion().getName().equals("Hulk"))
				{
					view.getCurrentChampionDetails().setForeground(Color.BLACK);
				
				}
				else {
					view.getCurrentChampionDetails().setForeground(Color.WHITE);
					
				}
				view.getCurrentChampBannerLabel().setLayout(null);
				view.getCurrentChampBannerLabel().setSize(1300,600);
				view.getCurrentChampBannerLabel().setBounds(300, 200, 1300, 600);
				view.getCurrentChampBannerLabel().add(view.getOk());
				view.getCurrentChampBannerLabel().add(view.getCurrentChampionDetails());
				view.getCurrentChampBannerLabel().setVisible(true);
				view.getGamelabel().add(view.getCurrentChampBannerLabel());
				view.revalidate();
				view.repaint();
				
			}
			else if (b == view.getOk()) {
				view.getGamelabel().remove(view.getCurrentChampBannerLabel());
				view.getBoardPanel().setBackground(new Color(10,10,10,240));
				view.getBoardPanel().setVisible(true);
			
				view.revalidate();
				view.repaint();
			}
			else  if (b== view.getExit()){
				view.setContentPane(view.getBwendvedio());
				view.getBwendvedio().mediaPlayer().media().play("bw.mp4");
		      
		
				
		        try {
					TimeUnit.SECONDS.sleep(17);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			    System.exit(0);
			
		        
		        
			
			}
			else if (b== view.getSkip()){
				view.remove(view.getVediopanel1());
	
				view.getVedio().mediaPlayer().controls().stop();
			
				view.setContentPane(view.getIntro());
				view.getIntro().add(view.getIntrosong());
				view.getIntrosong().mediaPlayer().media().play("The Avengers Theme Song.mp3");
				
				view.revalidate();
				view.repaint();
				
				
			}
			else {	
				if(flagselect1 == true)
				{
					int r = loadingScreenChampionButtons.indexOf(b);
					x = r; 
					championSelectedToSetAsLeader = true; 
				}
				else {
				view.getStartScreenBackground().setVisible(false);

				int r = loadingScreenChampionButtons.indexOf(b);
				String champinfo = "";
				String champ = Game.getAvailableChampions().get(r).getName();
				switch (champ)
				{
				case "Captain America" : subback = new ImageIcon("capback.jpg");
		
				break;
				case "Deadpool" : subback = new ImageIcon("deadpoolback.jpg");
				
				break;
				case "Dr Strange" :subback = new ImageIcon("drback.jpg");
			
				break;
				case "Black Panther" : subback = new ImageIcon("blackback.jpg");
			
				break;
				case "StarLord" : subback = new ImageIcon("starback.png");
	
				break;
				case "Hela" : subback = new ImageIcon("helaback.jpg");
	
				break;
				case "Hulk" : subback = new ImageIcon("hulkback.jpg");
			
				break;
				case "Antman" : subback = new ImageIcon("antback.jpg");

				break;
				case "Ironman" :subback = new ImageIcon("ironback.jpg");
	
				break;
				case "Loki" : subback = new ImageIcon("lokiback.jpg");
		
				break;
				case "Wanda" :subback = new ImageIcon("wandaback.jpg");
		
				break;
				case "Spiderman" :subback = new ImageIcon("spiderback.jpg");
			
				break;
				case "Thor" : subback = new ImageIcon("thorback.jpg");
	
				break;
				case "Venom" : subback = new ImageIcon("venomback.jpg");
		
				break;
				case "Thanos" :subback = new ImageIcon("thanosback.jpg");
			
				break;
				case "Black Widow" :subback = new ImageIcon("blackwidowback.jpg");
				
			
				
				}
				
				
			    for (int i = 0 ; i < Game.getAvailableChampions().get(r).getAbilities().size();i++)
				{
					String ab = "";
					if (i==0)
						ab = "FIRST ";
					if (i==1)
						ab = "SECOND ";
					if (i==2)
						ab = "THIRD ";
					champinfo += ab + Game.getAvailableChampions().get(r).getAbilities().get(i).toString();
					
				}
			    Font font = null;
				try {
					font = Font.createFont(Font.TRUETYPE_FONT, new File("LoveNature-48z9.ttf"));
				} catch (FontFormatException | IOException e2) {
					e2.printStackTrace();
				}
				 Font font2 = null;
					try {
						font2 = Font.createFont(Font.TRUETYPE_FONT, new File("BraveEightyoneRegular-ZVGvm.ttf"));
					} catch (FontFormatException | IOException e2) {
						e2.printStackTrace();
					}
				Font used = new Font(font.getName(),Font.BOLD,30);
				view.getChampionDetails().setText(champinfo);
				view.getChampionDetails().setFont(used);
				if(champ.equals("Black Widow")||champ.equals("Hulk"))
				{
					view.getChampionDetails().setForeground(Color.BLACK);
					
				}
				else {
					view.getChampionDetails().setForeground(Color.WHITE);
					
				}
				view.getChampionDetails().setOpaque(false);
			    view.setChampDet(new JLabel("",subback,JLabel.CENTER));
				view.getChampDet().setLayout(null);
				view.getChampDet().setSize(1920,1080);
				view.getChampDet().add(view.getConfirm());
				view.getChampDet().add(view.getBack());
				view.getChampDet().add(view.getChampionDetails());
				view.getChampDet().setVisible(true);
				view.getStartScreenBackground().setVisible(false);
				view.setContentPane(view.getChampDet());
				view.revalidate();
			    view.repaint();
				
			    x = r;
				championSelectedToSetAsLeader = true; 
			}
		}
      }
	}
			
	
	private void loadingIconsCardsforBoard(JButton currentplayerbanner, Champion currentChampion) {
		String champ = currentChampion.getName();
		ImageIcon icon = new ImageIcon() ;
		switch (champ)
		{
		case "Captain America" :  icon= new ImageIcon ("capcard.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpoolcard.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrangecardd.png"); 
		break;
		case "Black Panther" : icon= new ImageIcon ("blackcard.png"); 
		break;
		case "StarLord" : icon= new ImageIcon ("starlordcard.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("helacard.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulkcard.png");
		break;
		case "Antman" : icon= new ImageIcon ("antmancard.png");
		break;
		case "Ironman" : icon= new ImageIcon ("ironmancardd.png");
		break;
		case "Loki" : icon= new ImageIcon ("lokicard.png");
		break;
		case "Wanda" : icon= new ImageIcon ("wandacard.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spidermancard.png");
		break;
		case "Thor" : icon= new ImageIcon ("thorcard.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venomcard.png");
		break;
		case "Thanos" : icon= new ImageIcon ("thanoscard.png");
		break;
		case "Black Widow" : icon = new ImageIcon("blackwidowcard.png");
		}
		currentplayerbanner.setIcon(icon);
		currentplayerbanner.setText("");
		
	}
		
	




	private void loadabilityicon(String champname,ImageIcon icon) {

		
		switch (champname)
		{
		case "Captain America" :  icon= new ImageIcon ("captainamerciab.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpoolb.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrangeb.png"); 
		break;
		case "Black Panther" : icon= new ImageIcon ("blackpantherb.png"); 
		break;
		case "StarLord" : icon= new ImageIcon ("starlordb.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("helab.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulk1b.png");
		break;
		case "Antman" : icon= new ImageIcon ("antmanb.png");
		break;
		case "Ironman" : icon= new ImageIcon ("iron manb.png");
		break;
		case "Loki" : icon= new ImageIcon ("lokib.png");
		break;
		case "Wanda" : icon= new ImageIcon ("wandab.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spidermanb.png");
		break;
		case "Thor" : icon= new ImageIcon ("thor pngb.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venomb.png");
		break;
		case "Thanos" : icon= new ImageIcon ("thanosb.png");
		break;
		case "Black Widow" : icon = new ImageIcon("blackwidowb.png");		
		}
		
	}
		
	

	private void loadbanner(JButton currentplayerbanner, Champion champion) {
		String champ = champion.getName();
		ImageIcon icon = new ImageIcon() ;
		switch (champ)
		{
		case "Captain America" :  icon= new ImageIcon ("captainamericabanner.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpoolbanner.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrangebanner.png"); 
		break;
		case "Black Panther" : icon= new ImageIcon ("blackpantherbanner.png"); 
		break;
		case "StarLord" : icon= new ImageIcon ("starlordbanner.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("helabanner.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulkbanner.png");
		break;
		case "Antman" : icon= new ImageIcon ("antmanbanner.png");
		break;
		case "Ironman" : icon= new ImageIcon ("ironmanbanner.png");
		break;
		case "Loki" : icon= new ImageIcon ("lokibanner.png");
		break;
		case "Wanda" : icon= new ImageIcon ("wandabanner.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spidermanbanner.png");
		break;
		case "Thor" : icon= new ImageIcon ("thorbanner.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venombanner.png");
		break;
		case "Thanos" : icon= new ImageIcon ("thanosbanner.png");
		break;
		case "Black Widow" : icon = new ImageIcon("blackwidowbanner.png");
		}
		currentplayerbanner.setIcon(icon);
		currentplayerbanner.setText("");
	}
		
	
	private void abilityGUIlogic(Ability ability2) {
		
		try {
		switch(ability2.getCastArea()) {
		case SURROUND :
		case TEAMTARGET :
		case SELFTARGET :game.castAbility(ability2);
						castAbilityKeyPressed = false;
						break;
		}
		
		}
		catch (NotEnoughResourcesException ex)
		{
			castAbilityKeyPressed = false;
			JOptionPane.showMessageDialog(view, ex.getMessage());
			
		}
		catch (AbilityUseException ex)
		{
			castAbilityKeyPressed = false;
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		catch ( CloneNotSupportedException ex)
		{
			castAbilityKeyPressed = false;
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		
		if( ability2.getCastArea()== AreaOfEffect.SINGLETARGET)
			{
				JOptionPane.showMessageDialog(view, "Please Choose a Target ");
			}
		else if(ability2.getCastArea()==AreaOfEffect.DIRECTIONAL)
		{
			JOptionPane.showMessageDialog(view, "Please click on the champion you want only that lies in : NORTH , SOUTH , EAST ,WEST");
		}
		
		refreshBoard();
		fillAndRefreshTurnorder();
		view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());

	
	}
		
	private  void addaButtonTurnorder(String text, JPanel container, int i) {
		JButton b = new JButton();
		b.setAlignmentX(Component.CENTER_ALIGNMENT);
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setLayout(null);
		loadbanner(b,text);
		b.setPreferredSize(new Dimension(260,100));
		if (i==0) {
		b.setSelected(true);
		
		}
		container.add(b);
	}
	private void loadbanner(JButton b, String text) {
		String champ = text;
		ImageIcon icon = new ImageIcon() ;
		switch (champ)
		{
		case "Captain America" :  icon= new ImageIcon ("captainamericabanner.png");
		break;
		case "Deadpool" : icon= new ImageIcon ("deadpoolbanner.png"); 
		break;
		case "Dr Strange" : icon= new ImageIcon ("drstrangebanner.png"); 
		break;
		case "Black Panther" : icon= new ImageIcon ("blackpantherbanner.png"); 
		break;
		case "StarLord" : icon= new ImageIcon ("starlordbanner.png"); 
		break;
		case "Hela" : icon= new ImageIcon ("helabanner.png"); 
		break;
		case "Hulk" : icon= new ImageIcon ("hulkbanner.png");
		break;
		case "Antman" : icon= new ImageIcon ("antmanbanner.png");
		break;
		case "Ironman" : icon= new ImageIcon ("ironmanbanner.png");
		break;
		case "Loki" : icon= new ImageIcon ("lokibanner.png");
		break;
		case "Wanda" : icon= new ImageIcon ("wandabanner.png");
		break;
		case "Spiderman" : icon= new ImageIcon ("spidermanbanner.png");
		break;
		case "Thor" : icon= new ImageIcon ("thorbanner.png"); 
		break;
		case "Venom" : icon= new ImageIcon ("venombanner.png");
		break;
		case "Thanos" : icon= new ImageIcon ("thanosbanner.png");
		break;
		case "Black Widow" : icon = new ImageIcon("blackwidowbanner.png");
		}
		b.setIcon(icon);
		b.setText("");
	}
		
	
	
	public void fillAndRefreshTurnorder() {
		view.getTurnOrderPanel().removeAll();
		ArrayList<Champion> temp = new ArrayList<Champion>();
		while(!game.getTurnOrder().isEmpty()) {
			temp.add((Champion) game.getTurnOrder().remove());
		}
		for(int i = 0 ; i < temp.size();i++) {
			addaButtonTurnorder(temp.get(i).getName(), view.getTurnOrderPanel(), i);
			
		}
		
		for(int i = 0 ; i < temp.size();i++) {
			game.getTurnOrder().insert(temp.get(i));
			
			
		}
		view.revalidate();
		view.repaint();
		
		
	}
	
	public void confirmLogic(int r) {
		JButton b = loadingScreenChampionButtons.get(r);
		champselection1 = new ImageIcon("blue.jpg");
		champselection2 = new ImageIcon("red.jpg");
        if (i == 1 ) {
            view.getChampionSelection1().add(b);
            
            b.setBounds(200,250,400,400);
            loadingpicsforleader(b,r);
            firstPlayerIndices[0] = x;
           	b.setEnabled(false);
        	
        }
        else if (i == 2)
        {
            view.getChampionSelection1().add(b);
            b.setBounds(750,250,400,400);
            loadingpicsforleader(b,r);
            firstPlayerIndices[1] = x;
            b.setEnabled(false);
        }
        else if (i == 3)
        {
            view.getChampionSelection1().add(b);
            b.setBounds(1350,250,400,400);
            loadingpicsforleader(b,r);
            firstPlayerIndices[2] = x;
            b.setEnabled(false);
            view.setChampselection1background(new JLabel("",champselection1,JLabel.CENTER));
	    	view.getChampselection1background().setLayout(null);
	    	view.getChampselection1background().setSize(1920,1080);
	    	view.getChampionSelection1().add(view.getSelectFirstLeader());
	    	view.getChampselection1background().add(view.getChampionSelection1());
	    	view.revalidate();
	    	view.repaint();
        }
        else if (i == 4)
        {
            view.getChampionSelection2().add(b);
            b.setBounds(200,270,400,400);
            loadingpicsforleader(b,r);
            b.setEnabled(false);
            secondPlayerIndices[0] = x;
        }
        else if (i == 5)
        {
            view.getChampionSelection2().add(b);
            b.setBounds(750,270,400,400);
            loadingpicsforleader(b,r);
             b.setEnabled(false);
            secondPlayerIndices[1] = x;
        }
        else if (i == 6)
        {
            view.getChampionSelection2().add(b);
            b.setBounds(1350,270,400,400);
            loadingpicsforleader(b,r);
            b.setEnabled(false);
            secondPlayerIndices[2] = x;
            view.getConfirm().setEnabled(false);
			view.getSelectFirstLeader().setEnabled(true);
			for (int i=0; i<loadingScreenChampionButtons.size();i++) {
				if (i!=firstPlayerIndices[0] && i!=firstPlayerIndices[1] &&i!=firstPlayerIndices[2] &&
						i!=secondPlayerIndices[0] && i!=secondPlayerIndices[1] && i!=secondPlayerIndices[2] ) {
					loadingScreenChampionButtons.get(i).setEnabled(false);
				}
				else {
					if (i==firstPlayerIndices[0] || i==firstPlayerIndices[1] || i==firstPlayerIndices[2] ) {
						loadingScreenChampionButtons.get(i).setEnabled(true);
					}
				}
			}
			view.setChampselection2background(new JLabel("",champselection2,JLabel.CENTER));
	    	view.getChampselection2background().setLayout(null);
	    	view.getChampselection2background().setSize(1920,1080);
	    	view.getChampionSelection2().add(view.getSelectSecondLeader());
	    	view.getChampselection2background().add(view.getChampionSelection2());
	    	
	    	view.revalidate();
	    	view.repaint();
	    	flagnext = true;
			
			
        }
        view.revalidate();
        view.repaint();
        view.getChampionButton().remove(b);
        if (i<=3) {
        	
        	firstPlayer.getTeam().add(Game.getAvailableChampions().get(r));
        
        	
        }
        else {
        	secondPlayer.getTeam().add(Game.getAvailableChampions().get(r));
        
        }
        
    }
	private void loadingpicsforleader(JButton b,int i ) {
		ImageIcon icon = new ImageIcon() ;
		switch (champselectednames.get(i))
		{
	case "Captain America" :  icon= new ImageIcon ("capcard.png");
	break;
	case "Deadpool" : icon= new ImageIcon ("deadpoolcard.png"); 
	break;
	case "Dr Strange" : icon= new ImageIcon ("drstrangecardd.png"); 
	break;
	case "Black Panther" : icon= new ImageIcon ("blackcard.png"); 
	break;
	case "StarLord" : icon= new ImageIcon ("starlordcard.png"); 
	break;
	case "Hela" : icon= new ImageIcon ("helacard.png"); 
	break;
	case "Hulk" : icon= new ImageIcon ("hulkcard.png");
	break;
	case "Antman" : icon= new ImageIcon ("antmancard.png");
	break;
	case "Ironman" : icon= new ImageIcon ("ironmancardd.png");
	break;
	case "Loki" : icon= new ImageIcon ("lokicard.png");
	break;
	case "Wanda" : icon= new ImageIcon ("wandacard.png");
	break;
	case "Spiderman" : icon= new ImageIcon ("spidermancard.png");
	break;
	case "Thor" : icon= new ImageIcon ("thorcard.png"); 
	break;
	case "Venom" : icon= new ImageIcon ("venomcard.png");
	break;
	case "Thanos" : icon= new ImageIcon ("thanoscard.png");
	break;
	case "Black Widow" : icon = new ImageIcon("blackwidowcard.png");
	}
		b.setIcon(icon);
		b.setText("");
	}
		
	
	@Override
	public void mouseClicked(MouseEvent e) {
		  
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if (castAbilityKeyPressed) {
			JButton b = (JButton) e.getSource();
			
			for (int i =0; i<=4; i++) {
				for(int j = 0; j<=4; j++) {
					if (b == boardButtons[i][j]) {
						try {
								game.castAbility(game.getCurrentChampion().getAbilities().get(abIndex.get(abIndex.size()-1)), i, j);
						}
						catch (NotEnoughResourcesException ex)
						{
							JOptionPane.showMessageDialog(view, ex.getMessage());
							
						}
						catch (AbilityUseException ex)
						{
							JOptionPane.showMessageDialog(view, ex.getMessage());
						}
						catch ( CloneNotSupportedException ex)
						{
							JOptionPane.showMessageDialog(view, ex.getMessage());
						}
						catch (InvalidTargetException ex) {
							JOptionPane.showMessageDialog(view, ex.getMessage());
						}
						finally{
							refreshBoard();
							fillAndRefreshTurnorder();
							view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
			
					
							castAbilityKeyPressed=false;

						};
					};
				};
			};
			
			
		};
	
	};

	public void mouseReleased(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {
		 JButton b = (JButton)e.getSource();
		 String s = "";
		 for (int i=0 ; i<=4 ;i++ )
		 {
			 for (int j = 0; j<= 4 ; j++)
			 {
				 if (boardButtons[i][j]==b)
				 
				 {
					if(game.getBoard()[i][j] instanceof Champion)
					{
						s += ((Champion)(game.getBoard())[i][j]).toString2();
						if (game.getBoard()[i][j].equals(firstPlayer.getLeader()) || game.getBoard()[i][j].equals(secondPlayer.getLeader())) {
							s += "Leader: YES } \n \n";
						}
						else {
							s += "Leader: NO } \n \n";
						}
						s += ((Champion)(game.getBoard())[i][j]).effectsList();
					}
					else
						s= "";
				 }
				 view.getRemainingChampionDetails().setText(s);
			 }
		 }
		
	}


	
	public void keyPressed(KeyEvent e) {
	    

		try {
			if (castAbilityKeyPressed) { 
				if (e.getKeyCode()=='W') {
					game.castAbility(ability, Direction.UP);
					view.getAbilityDetails().setText(null);
				}
				else if(e.getKeyCode() =='A') {
					game.castAbility(ability, Direction.LEFT);
					view.getAbilityDetails().setText(null);
				}
				else if(e.getKeyCode()== 'S') {
					game.castAbility(ability, Direction.DOWN);
					view.getAbilityDetails().setText(null);
				}
				else if(e.getKeyCode()=='D') {
					game.castAbility(ability, Direction.RIGHT);
					view.getAbilityDetails().setText(null);
				}
			}
		}
		catch (NotEnoughResourcesException ex)
		{
			JOptionPane.showMessageDialog(view, ex.getMessage());
			
		}
		catch (AbilityUseException ex)
		{
			JOptionPane.showMessageDialog(view, ex.getMessage());
		}
		catch (CloneNotSupportedException ex) {
			JOptionPane.showMessageDialog(view, ex.getMessage());
			
		}
		finally {
			castAbilityKeyPressed = false;
		}


		refreshBoard();
		fillAndRefreshTurnorder();
		view.getCurrentChampionDetails().setText(game.getCurrentChampion().toString3()+ "\n" +game.getCurrentChampion().effectsList());
		
	}




	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
