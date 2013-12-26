
package picture;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.*;
import java.util.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import com.sun.image.codec.jpeg.*;

class Frame1 extends JFrame{
    Page page;
    Tool tool;
    
    Frame1(){
        
        this.setSize(200,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //this.setLayeredPane(null);
        page = new Page(this);
        this.getContentPane().add(page,BorderLayout.CENTER);
        
        tool = new Tool(this);
        this.getContentPane().add(tool,BorderLayout.NORTH);
        
        //lab = new JLabel();
        //this.add(lab);
        
        
        this.setVisible(true);
    }
    
  
}

class But extends JButton{
    But(){
        this.setLayout(null);
        this.setSize(100,100);
    }
}


class Tool extends JPanel{
    Frame1 parent;
    FileDialog dialog;
    FileDialog dialog1;
    Tool(Frame1 p)
    {
        parent = p;
        JButton b1 = new JButton();
        b1.setText("Open");
        this.add(b1);
        
        JButton b2 = new JButton();
        b2.setText("Save");
        this.add(b2);
        
        //JLabel lab = new JLabel();
        //this.add(lab);
        
        b1.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                   dialog1 = new FileDialog(parent,"",FileDialog.LOAD);
                    dialog1.setVisible(true);
                            File file = new File(dialog1.getDirectory(),dialog1.getFile());
                            Image image = null;
                            int w,h;
                            w = parent.page.getWidth();
                            h = parent.page.getHeight();
                            if(dialog1.getFile() != null){
                            try{
                                BufferedImage img = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
                                image = ImageIO.read(file);
                                Graphics g = image.getGraphics();
                                parent.page.paint(g);
                                
                                //JLabel lab = new JLabel(new ImageIcon(image));
                                //parent.page.add(lab);
                                
                            }catch (Exception x){x.printStackTrace();}
                            }
                }
            }
        );
        
        b2.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                   dialog = new FileDialog(parent,"",FileDialog.SAVE);
                    dialog.setVisible(true);
                   if(dialog.getFile() != null){
                            int w,h;
                            File file = new File(dialog.getDirectory(),dialog.getFile());
                            w = parent.page.getWidth();
                            h = parent.page.getHeight();
                            try{
                                BufferedImage img = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
                                Graphics2D g2 = img.createGraphics();
                                parent.page.paint(g2);
                                FileOutputStream out=new FileOutputStream(file.getName());
                                JPEGImageEncoder jpegImageEncoder=JPEGCodec.createJPEGEncoder(out);
                                jpegImageEncoder.encode(img);
                                out.flush();
                                out.close();
                                img.flush();
                            }catch (Exception x){x.printStackTrace();}
                
                    }
                }
            }
        );
}}


class Page extends JPanel{
    Frame1 parent;
    Page(Frame1 p){
        parent = p;
        this.setBackground(Color.red);
    }
     public void paintComponent(Graphics pen)
    {
        super.paintComponent(pen);
    }
}

public class Picture{

    public static void main(String[] args) {
        new Frame1();
    }
}
