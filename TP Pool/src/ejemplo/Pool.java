package ejemplo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Pool extends JPanel implements ActionListener, KeyListener {
	// pantalla
	private int width = 900;
	private int height = 500;
	// tamaño de las bolas
	private int ballsize = 20;
	// bola blanca
	private int ball0PosX = width / 2 - ballsize / 2;
	private int ball0PosY = height / 2 - ballsize / 2;
	private double ball0VelX = 0;
	private double ball0VelY = 0;
	// bola roja
	private int ball1PosX = width / 3 - ballsize / 2;
	private int ball1PosY = height / 3 - ballsize / 2;
	private double ball1VelX = 0;
	private double ball1VelY = 0;
	//elementos
	private double angle = 0; // Ángulo de rotación
	private int lineLength = 150; // Longitud de la línea de rotación
	private boolean shoot = false;//boleano de si la pelota se disparo
	private double speed = 10; // Velocidad de la pelota
	private boolean showLine = true; // Mostrar línea de rotación inicialmente
	private double deceleration = 0.9; // Valor entre 0 y 1 para la desaceleración (ajusta según sea necesario)

    public Pool() {
        setBackground(new Color(141, 182, 0));
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(this);
        Timer timer = new Timer(16, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillOval(ball0PosX - 10, ball0PosY - 10, 20, 20);
        g.setColor(Color.RED);
        g.fillOval(ball1PosX - 10, ball1PosY - 10, 20, 20);
        
        if (showLine==true) {
            int lineEndX = (int) (ball0PosX + lineLength * Math.cos(angle));
            int lineEndY = (int) (ball0PosY + lineLength * Math.sin(angle));
            g.setColor(Color.BLACK);
            g.drawLine(ball0PosX, ball0PosY, lineEndX, lineEndY);
        }

    }

    public void actionPerformed(ActionEvent e) {
        if (shoot==true) {
        	
        	// Aplicar desaceleración
           // ball0VelX *= deceleration;
            //ball0VelY *= deceleration;
            
            ball0PosX += ball0VelX;
            ball0PosY += ball0VelY;
            ball1PosX += ball1VelX;
            ball1PosY += ball1VelY;
            
            // Rebote en los bordes laterales
            if (ball0PosX <= 0 || ball0PosX >= width - ballsize) {
            	ball0VelX = -ball0VelX;
            }
            if (ball1PosX <= 0 || ball1PosX >= width - ballsize) {
                ball1VelX = -ball1VelX;
            }
            // Rebote en la parte superior
            if (ball0PosY <= 0 || ball0PosY >= height - ballsize) {
            	
            	ball0VelY = -ball0VelY;
            }
            if (ball1PosY <= 0 || ball1PosY >= height - ballsize) {
                ball1VelY = -ball1VelY;
            }
            
            // Colisiï¿½n con la pelota roja
            if (ball0PosY + ballsize >= ball1PosY &&
                    ball0PosX + ballsize >= ball1PosX &&
                    ball0PosX <= ball1PosX + ballsize) {
                    double temp = ball0VelY;
                    ball0VelY = ball1VelY;
                    ball1VelY = temp;
                }
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            angle -= 0.01;
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            angle += 0.01;
            repaint();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            ball0VelX = speed * Math.cos(angle);
            ball0VelY = speed * Math.sin(angle);
            shoot = true;
            showLine = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("POOL");
        Pool game = new Pool();
        frame.setResizable(false);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}