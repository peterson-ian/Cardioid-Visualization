package tablescardioidvisualization;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TablesCardioidVisualization extends JPanel{
    private final int total = 200;
    private double factor = 1;
    private final float r = 600/2;
    private int color[] = new int[3];
    private int incr[];
 
    public TablesCardioidVisualization() throws HeadlessException {
        this.incr = new int[]{(int) (7 * Math.random()),(int) (3 * Math.random()),(int) (5 * Math.random())};
        setPreferredSize(new Dimension(640, 640));
        setBackground(Color.BLACK);

        Timer timer = new Timer(50, (ActionEvent arg0) -> {
            factor += 0.01;
            
            color[0] = incrementaRGBCor(color[0], incr[0]);
            color[1] = incrementaRGBCor(color[1], incr[1]);
            color[2] = incrementaRGBCor(color[2], incr[2]);
            
            repaint();
        }); 
        timer.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        
        Graphics2D graphics = (Graphics2D) g;
        graphics.setColor(new Color(color[0], color[1], color[2]));
  
        Ellipse2D.Double circulo = new Ellipse2D.Double(0, 0, 600, 600);
        graphics.draw(circulo);
        
        double somaAng = ((double)360) / total;
        double ang = 0;
          
        for (int i = 0; i < total; i++) {
            Ponto p1 = calcCordenadas(ang);
            double conecta = factor * i;

            if(conecta > total - 1){
                conecta = conecta % total;
            }

            Ponto p2 = calcCordenadas((conecta) * somaAng);

            Line2D.Double linha = new Line2D.Double(p1.getX(),p1.getY(), p2.getX(),p2.getY());
            graphics.draw(linha);

            ang+=somaAng;
        }
         
    }
    
    private Ponto calcCordenadas(double angulo){
        double radians = Math.toRadians(angulo);
                   
        double x =  r * Math.sin(radians) + r;
        double y =  r * Math.cos(radians) + r;
        
        return (new Ponto(x, y));
    }
    
    private int incrementaRGBCor(int x, int i){
        if(x >= (255 - i)){
            x = 50;
            this.incr = new int[]{Math.abs((int) (5 * Math.random()) - i), Math.abs((int) (3 * Math.random()) - i), Math.abs((int) (2 * Math.random()) - i)};
        }
        else{
            x = x + i;
        }
            
        return x;
       
    }
    
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cardioid Visualization");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new FlowLayout(15,15,15));
        frame.add(new TablesCardioidVisualization());
        frame.setSize(new Dimension(640, 670));
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}



