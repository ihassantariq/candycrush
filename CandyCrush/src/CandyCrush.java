import java.util.Random;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


public class CandyCrush extends JFrame implements ActionListener 
{
    private int row;
    private int col;
    private boolean check=false;
    private String picNumber=null;
    private JButton Jb[][];
    private  int m=0, s=0;
    private JLabel score;
    public static void main(String[] args) 
    {
       CandyCrush cc=new CandyCrush();
    }
    public CandyCrush()
   {
        
       
            BufferedImage bf = null;          
             try 
             {
                bf = ImageIO.read(new File("C:\\Users\\Hassan's\\Documents\\NetBeansProjects\\CandyCrush\\src\\background.jpg"));
             } 
             catch (IOException ex) 
             {
                Logger.getLogger(CandyCrush.class.getName()).log(Level.SEVERE, null, ex);
             }
	
                JFrame jf=new JFrame("Candy Crush");
                jf.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Hassan's\\Documents\\NetBeansProjects\\CandyCrush\\src\\icon.jpg"));
                jf.setContentPane(new backImage(bf));
                Container c=jf.getContentPane();
                c.setLayout(new BorderLayout());
                JPanel UpperPanel=new JPanel(new GridLayout(1,2));
                UpperPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 10));
                Font font1 = new Font("SansSerif", Font.BOLD, 30);
                score=new JLabel("Score:00");
                score.setFont(font1);
                score.setForeground(Color.WHITE);
                JLabel Time=new JLabel("                                  Time:00");
                Time.setForeground(Color.WHITE);
                Time.setFont(font1);
               
                //clock code
                 javax.swing.Timer t = new javax.swing.Timer(1000,
                 new ActionListener() {
                  public void actionPerformed(ActionEvent e){
                     s++;
                    checkTime(); 
                     String sr =Time.getText();
                     String sArray[]=sr.split(":");
                     String format=new DecimalFormat("00").format(s);
                     
                     Time.setText(sArray[0]+":"+format);
                  }
              });
                t.start();  // Start the timer                                
                UpperPanel.add(score);
                UpperPanel.add(Time);
                UpperPanel.setOpaque(false);
                c.add(UpperPanel,BorderLayout.NORTH);
		
                JPanel jp=new JPanel();
                
                jp.setLayout(new GridLayout(7,7,0,0));
                c.add(jp,BorderLayout.CENTER);
                jp.setOpaque(false);
                
		jp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
                Jb=new JButton[7][];
                
                for(int i=0;i<7;i++)
                Jb[i] = new JButton[7];
              
              
                        for(int i=0;i<7;i++)    
			for(int j=0;j<7;j++)
			{
                             
                                String rand=generateRandom();
				Jb[i][j]=new JButton();  
                                Jb[i][j].setToolTipText(Integer.toString(i)+","+Integer.toString(j)+","+rand);
                               // Jb[i][j].
				jp.add(Jb[i][j]);
                                Jb[i][j].addActionListener(this);
                                Jb[i][j].setRequestFocusEnabled(false);
                                Jb[i][j].setIcon(new ImageIcon(getClass().getResource(rand+ ".png")));
                                Jb[i][j].setOpaque(false);
                                Jb[i][j].setContentAreaFilled(false);
                                Jb[i][j].setBorderPainted(false);
			}
                        checkMatch();
                     
		ToolTipManager.sharedInstance().setEnabled(false);
		jf.setVisible(true);
		jf.setSize(960,670);
                jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setResizable(false);
                jf.setLocationRelativeTo(null);
    }
    public  void actionPerformed(ActionEvent e)
    {
        if(!check)
        for(int i=0;i<7;i++)
           {
               for(int j=0;j<7;j++)
               {
                   if(e.getSource()==Jb[i][j])
                   {   
                       row=i;
                       col=j;
                       picNumber=getpictureNumber(Jb[i][j].getToolTipText());
                       check=true;
                       playsound();

                   }
               }
           }
        else
        {
           for(int i=0;i<7;i++)
           {
               for(int j=0;j<7;j++)
               {
                   if(e.getSource()==Jb[i][j])
                   {  
                       if(row==i)
                       {
                        if(col==j-1 || col==j+1)
                        { 
                            ReplacePictures(Jb[i][j]);
                            check=false;
                        }
                       }
                       else if(col==j)
                       {
                           if(row==i-1 || row == i+1)
                           {
                                ReplacePictures(Jb[i][j]);
                                check=false;
                           }
                       }
                       else
                       {
                             row=i;
                             col=j;
                             picNumber=getpictureNumber(Jb[i][j].getToolTipText());
                             check=true;
                       }
                      // check=true;
                       
                   }
               }
               
           }
        playsound();   
        checkMatch();   

        }
    }
    public void checkTime()
    {
        if(s==60)
        {
            String s=score.getText();
            String s1[]=s.split(":");
            int i=Integer.parseInt(s1[1]);
            String str= "your score is: "+i+" :) \n Do you want to Play Again?";
            int select = JOptionPane.showConfirmDialog(this,
            str,"Candy Crush Sega" , JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE, null);
            if(select == JOptionPane.YES_OPTION)
            {
                ResetButtons();
            }
            else if(select==JOptionPane.NO_OPTION)
            {
                System.exit(0);
            }
            else
            {
                ResetButtons();
            }
        }
    }
    public void ResetButtons()
    {
                        s=0; 
                        score.setText("Score:00");
                        for(int i=0;i<7;i++)
			for(int j=0;j<7;j++)
			{
				String rand=generateRandom();  
                                Jb[i][j].setToolTipText(Integer.toString(i)+","+Integer.toString(j)+","+rand);
                                Jb[i][j].setIcon(new ImageIcon(getClass().getResource(rand+ ".png")));
			}
                        checkMatch();
        
    }
    public String generateRandom()
    {
        Random rand=new Random();
        int i=rand.nextInt(5)+1;
       return Integer.toString(i);
    }
    public String getpictureNumber(String s)
    {
        String str[]=s.split(",");
        return str[2];
    }
    public void ReplacePictures(JButton b)
    {
        String picNumber1=getpictureNumber(b.getToolTipText());
        Jb[row][col].setIcon(new ImageIcon(getClass().getResource(picNumber1+".png")));
        Jb[row][col].setToolTipText(Integer.toString(row)+","+Integer.toString(col)+","+picNumber1);
        b.setIcon(new ImageIcon(getClass().getResource(picNumber+".png"))); 
        String s=b.getToolTipText();
        String str[]=s.split(",");
        str[2]=picNumber;
        b.setToolTipText(str[0]+","+str[1]+","+ str[2]);
    }
    public void checkMatch()
    {
        for(int i=0;i<7;i++)
            for(int j=0;j<7;j++)
            {
                Matching(i,j);
            }
        
    }
    public void Matching(int row,int col)
    {
        
        int sCompare=Integer.parseInt(getpictureNumber(Jb[row][col].getToolTipText()));       
        int j=0,count=0;
        //check for rows
        for(j=col+1;j<7;j++)
        {
            int sCompareNew=Integer.parseInt(getpictureNumber(Jb[row][j].getToolTipText()));
            if(sCompare==sCompareNew)
                count++;
            else 
                break;
        }
        if(count>=2)
        {
            generatePics(row,col,j,true,false);
            
        }
        //check for cols
        j=0;count=0;
        for(j=row+1;j<7;j++)
        {
            int sCompareNew=Integer.parseInt(getpictureNumber(Jb[j][col].getToolTipText()));
            if(sCompare==sCompareNew)
                count++;
            else 
                break;
        }
        if(count>=2)
        {
           // System.out.println(count);
            generatePics(row,col,j,false,true);
            
        }
        
    //    System.out.println(count);
        
        
    }
    public void generatePics(int row,int col,int limit,boolean rows,boolean cols)
    {
        if(rows)
        {
            for(int j=col;j<limit;j++)
            {
                IncreaseScore();
                try {
                     Thread.sleep(100);                 //1000 milliseconds is one second.
                    } catch(InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                String rand=generateRandom();
                Jb[row][j].setToolTipText(Integer.toString(row)+","+Integer.toString(col)+","+rand);
                Jb[row][j].setIcon(new ImageIcon(getClass().getResource(rand+ ".png")));
            }
        }
        else if(cols)
        {
            for(int j=row;j<limit;j++)
            {
                IncreaseScore();
                String rand=generateRandom();
                Jb[j][col].setToolTipText(Integer.toString(row)+","+Integer.toString(col)+","+rand);
                Jb[j][col].setIcon(new ImageIcon(getClass().getResource(rand+ ".png")));
            }
        }
        checkMatch();
    }
    public void IncreaseScore()
    {
        String s=score.getText();
        String s1[]=s.split(":");
        int i=Integer.parseInt(s1[1]);
        i=i+10;
        score.setText(s1[0]+":"+Integer.toString(i));
    }
public void playsound()
{
    String gongFile = "C:\\Users\\Hassan's\\Documents\\NetBeansProjects\\CandyCrush\\src\\click.wav";
    InputStream in;
        try {
            in = new FileInputStream(gongFile);
        
 
    // create an audiostream from the inputstream
         AudioStream audioStream;
    
         audioStream = new AudioStream(in);
         AudioPlayer.player.start(audioStream);
        } catch (IOException ex) {
            Logger.getLogger(CandyCrush.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    // play the audio clip with the audioplayer class
   
}
    
}

class backImage extends JComponent 
{
 
Image i;
 
//Creating Constructer
public backImage(Image i) {
this.i = i;
 
}
 
//Overriding the paintComponent method
@Override
public void paintComponent(Graphics g) {
 
g.drawImage(i, 0, 0, null);  // Drawing image using drawImage method
 
}
}
