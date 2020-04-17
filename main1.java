import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class SineDraw extends JPanel {
    private static final int SCALEFACTOR = 700;
    public int code = 0; 
    public float amplitude = 1;
    public String dataword ;
    private int cycles;

    private int points;

    private double[] sines;

    private int[] pts,pts_mod;

    public SineDraw() {
        setCycles(5);
    }
    public void set_dataword(String st)
    {
        this.dataword = st;
        repaint();
    }
    public void set_amplitude(float amp)
    {
         this.amplitude = amp;
         repaint();
    }
    public void setCycles(int newCycles) {
        cycles = newCycles;
        points = SCALEFACTOR * cycles * 2;
        sines = new double[points];
        for (int i = 0; i < points; i++) {
            double radians = (Math.PI / SCALEFACTOR) * i;
            sines[i] = Math.sin(radians)*amplitude;
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int maxWidth = getWidth();
        double hstep = (double) maxWidth / (double) points;
        int maxHeight = getHeight();
        pts = new int[points];
        for (int i = 0; i < points; i++)

            pts[i] = (int) ((sines[i] * 100 * .95 + 100)+ amplitude * 60) ;
        
        g.setColor(Color.BLACK);
        for (int i = 1; i < points; i++) {
            int x1 = (int) ((i - 1) * hstep);
            int x2 = (int) (i * hstep);
            int y1 = pts[i - 1];
            int y2 = pts[i];
            g.drawLine(x1, y1, x2, y2 );
        }
////////////////////digital waveform/////////////////

        if(code==1||code==2)
        {

   int st_x = 0;
   int shift_in_y  = (int )(amplitude*195 + 60 + 20);
   int st_y = 0+shift_in_y ;
   int step_width = maxWidth / dataword.length();
   for(int x=0;x<dataword.length();x++)
      {  
      for(int y=0;y<getHeight();y=y+10){
      g.drawLine(st_x,y,st_x,y+3);

      }
      if(dataword.charAt(x)=='0')
      {         st_y=50+shift_in_y;
               
         g.drawLine(st_x,st_y,st_x+step_width,st_y); 
         st_x+=step_width;     
      }
      else
      { 
         st_y=0+shift_in_y;
         if(x>0){
         if(dataword.charAt(x-1)=='0')
         g.drawLine(st_x,50+shift_in_y,st_x,0+shift_in_y);} //up
         g.drawLine(st_x,st_y,st_x+step_width,st_y); //mid
         st_x+=step_width;  
         if(x<dataword.length()-1){
         if(dataword.charAt(x+1)=='0')
         g.drawLine(st_x,0+shift_in_y,st_x,50+shift_in_y); //down
         
      }
      
      }
    
    }
        }
///////////////////////modulated wave drawing/////////////////////
        if(code==2)
        {
          int len = dataword.length();
  
         
          pts_mod = new int[points];
         
          int bookmark = points/len;
          int count=0;
          for (int i = 0; i < points; i++)
          pts_mod[i] =  (int) ((sines[i] * 100 * .95 + 100)+ amplitude * 60);
          
          g.setColor(Color.BLUE);
          for (int i = 1; i < points; i++) {
            int x1 = (int) ((i - 1) * hstep);
            int x2 = (int) (i * hstep);
            int y1,y2;
            if(dataword.charAt(count)=='1')
            {
             y1 = pts[i - 1]+ (int ) (amplitude*155 + 200);
             y2 = pts[i]+ (int ) (amplitude*155 + 200);
             
            }
            else
            {
               y1 = (int ) (amplitude*215 + 300);
               y2 =(int ) (amplitude*215 + 300);
              
            }
         
          g.drawLine(x1, y1, x2, y2);
          if(i%bookmark==0){
            if(count<len-1){
              g.setColor(Color.BLACK);
              count++;
              
              //for(int y=0;y<getHeight();y=y+10){
               // g.drawLine(x1,y,x1,y+3);}
              g.setColor(Color.BLUE);
             } 
           
            }
          
          }
          
          
            
            
          
          






        }
    }
}













public class main1 extends JPanel {
    private static SineDraw sines = new SineDraw();

    private JSlider adjustCycles = new JSlider(1, 300, 5);
    JFrame frame;
    FlowLayout layout ; 


    JPanel panel,bottom_panel;
     JButton square_wave ;
     JButton modulate;
     JButton demodulate,refresh,reset;
  
    ////////////// Labels ////////////////
     JLabel codeword_label ;
     JLabel frequency_label;
     JLabel amplitude_label;
     JLabel error_text;
   
    ////////////// TextViews ////////////////
     JTextField codeword;
     JTextField frequency;
     JTextField amplitude;
    public main1() {
      super(new BorderLayout());
       // add(BorderLayout.CENTER,sines);
       
       
    }
    public main1(int x){}
    public void initialize_components(){
         frame = new JFrame();
         
        // layout = new FlowLayout(); 
  
    
         panel = new JPanel();
         bottom_panel = new JPanel();
          square_wave = new JButton("Square wave");
          modulate = new JButton("Modulate(ASK)");
          demodulate = new JButton("Demodulate(ASK)");
          refresh = new JButton("Refresh");
          reset = new JButton("RESET");
      
        ////////////// Labels ////////////////
        codeword_label = new JLabel("Enter your codeword here");
        frequency_label = new JLabel("Enter the frequency for Carrier wave");
        amplitude_label = new JLabel("Enter the Peak to peak amplitude");
        error_text = new JLabel("Error, if any will be displayed here.");
        codeword_label.setForeground(Color.WHITE);
        frequency_label.setForeground(Color.WHITE);
        amplitude_label.setForeground(Color.WHITE);
        error_text.setForeground(Color.red);
      
        ////////////// TextViews ////////////////
         codeword = new JTextField(20);
         frequency = new JTextField(5);
         amplitude = new JTextField(5);
         frequency.setText("5");
         amplitude.setText("1");
         
    }
    public void load_components(){
        panel.add(codeword_label);
        panel.add(codeword);
        panel.add(amplitude_label);
        panel.add(amplitude);
        panel.add(frequency_label);
        panel.add(frequency);
        panel.add(square_wave);
        panel.add(modulate);
        panel.add(demodulate);
        panel.add(refresh);
        panel.add(reset);
        bottom_panel.add(error_text);
        bottom_panel.setBackground(Color.getHSBColor(0,0,0.19F));
        panel.setBackground(Color.getHSBColor(0,0,0.19F));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      frame.setSize(screenSize.width,screenSize.height );
      frame.add(BorderLayout.CENTER,sines);
      frame.add(BorderLayout.NORTH,panel);
      frame.setTitle("Virtual Lab (ASK)");
      bottom_panel.add(adjustCycles);
      frame.add(BorderLayout.SOUTH, bottom_panel);
      frame.getContentPane().setBackground( Color.red );
        frame.setVisible(true);
    }
     public void dr()
     {
       
       
        
       initialize_components();
      
        square_wave.addActionListener(new ActionListener() {
          public void actionPerformed(final ActionEvent e) {
            
            
            if(codeword.getText().matches("")){
              error_text.setText("Please provide the bit stream.");
              }
            else{
              sines.code=1;
            sines.set_dataword(codeword.getText());
            error_text.setText("No error, everything looks good :) ");
            }
            
            
          }
        });
        modulate.addActionListener(new ActionListener() {
          public void actionPerformed(final ActionEvent e) {
           

           if(codeword.getText().matches("")){
            error_text.setText("Please provide the bit stream.");
            }
          else{
            sines.code=2;
            sines.set_dataword(codeword.getText());
            error_text.setText("No error, everything looks good :) ");
          }
      
          }
        });
        demodulate.addActionListener(new ActionListener() {
          public void actionPerformed(final ActionEvent e) {

            if(sines.code==2){
              if(codeword.getText().matches("")){
                error_text.setText("Please provide the bit stream.");
                }
                else{
               final Square_wave obj = new Square_wave(codeword.getText());
               obj.draw_square_wave(codeword.getText());
               error_text.setText("No error, everything looks good :) ");}
            }
            else{
              error_text.setText("Please modulate the signal first!");
            }
        
           
            }
          });
         refresh.addActionListener(new ActionListener() {
          public void actionPerformed(final ActionEvent e) {
                 sines.amplitude = Float.parseFloat(amplitude.getText());
                 sines.setCycles(Integer.parseInt(frequency.getText()));
                 error_text.setText("No error, everything looks good :) ");
               
            }
          });
          reset.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                   sines.amplitude = 1;
                   sines.code= 0;
                   sines.setCycles(5);
                   amplitude.setText("1");
                   frequency.setText("5");
                   codeword.setText("");
                   error_text.setText("No error, everything looks good :) ");
                 
              }
            });
        adjustCycles.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                sines.setCycles(((JSlider) e.getSource()).getValue());
                frequency.setText(((JSlider) e.getSource()).getValue()+"");
            }
        });
        load_components();
     }
    public static void main(String[] args) {
      main1 obj = new main1(0);
      obj.dr();
    }
}