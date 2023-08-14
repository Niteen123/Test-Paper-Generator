package attendance.system;

import Exception.FilloException;
import Fillo.Connection;
import Fillo.Fillo;
import Fillo.Recordset;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class Attendance implements ActionListener 
{
    JFrame welcome_f,login_f,select_f,commit_f,Confirm_f,Done_f;                           //frame decleration
    JLabel welcome_l,name_l,password_l,select_l,first_l,eventname_l,selected_l,Thank_l,Finallist_l,hours_l;                //labels decleraton
    JButton welcome_b,login_b,select_b,next_b,back_b,commit_b,confirm_b;                     //buttons decleration 
    JPasswordField password_pf,username_pf;                          //password field declaratio
    File FileName;
    String name,strQuery;
    JList first_li;
    JTextField eventname_t;
    JComboBox hours_cb;
    String names[]=new String[150];
    List list;
    String hours[]={"0.5","1","1.5","2","2.5","3","3.5","4","4.5","5","5.5","6","6.5","7","7.5","8","8.5","9","9.5","10","11","12"};
    
    Attendance()
    {
        //design of welcome page started
        
        welcome_f=new JFrame("Attendance System");
        welcome_f.setSize(500,500);
        welcome_f.setLayout(null);
        welcome_f.setResizable(false);
        welcome_f.getContentPane().setBackground(new Color(220,220,220)); 
        
        welcome_l=new JLabel("Attendance System");
        welcome_l.setBounds(180,150,200,100);
        welcome_l.setFont(new Font("TimesRoman",Font.BOLD,20));
        welcome_l.setForeground(Color.RED);
        welcome_f.add(welcome_l);
        
        welcome_b=new JButton("NEXT");
        welcome_b.setBounds(350,350,100,30);
        welcome_b.setToolTipText(" press to go to login page");
        welcome_b.addActionListener(this);
        welcome_b.setActionCommand("NEXT");
        welcome_f.add(welcome_b);
        welcome_f.setVisible(true);
        
        //design of welcom page ended
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String p=e.getActionCommand();
        if(p.equals("NEXT"))
        {
            welcome_f.setVisible(false);
            login_f=new JFrame("Attendance System");
            login_f.setSize(500, 500);
            login_f.setLayout(null);
            login_f.setResizable(false);
            login_f.getContentPane().setBackground(new Color(220,220,220)); 
            
            name_l=new JLabel("Name");
            name_l.setBounds(150,100,100,30);
            login_f.add(name_l);
            
            password_l=new JLabel("Password");
            password_l.setBounds(150,200,100,30);
            login_f.add(password_l);
            
            username_pf=new JPasswordField();
            username_pf.setBounds(250,100,100,30);
            login_f.add(username_pf);
            
            password_pf=new JPasswordField();
            password_pf.setBounds(250,200,100,30);
            login_f.add(password_pf);
            
            login_b=new JButton("Login");
            login_b.setBounds(190,300,100,30);
            login_b.setToolTipText(" press to go to login page");
            login_b.addActionListener(this);
            login_b.setActionCommand("Login");
            login_f.add(login_b);
            
            login_f.setVisible(true);
        }
        
        if(p.equals("Login"))
        {
            String pass=password_pf.getText();
            String username=username_pf.getText();
            if(pass.equals("Niteen")&&username.equals("Niteen"))
            {
                login_f.setVisible(false);
                select_f=new JFrame("Attendance System");
                select_f.setSize(500,500);
                select_f.setLayout(null);
                select_f.setResizable(false);
                select_f.getContentPane().setBackground(new Color(220,220,220)); 
                
                select_b=new JButton("Select");
                select_b.setBounds(80,150,70,20);
                select_f.add(select_b);
                select_b.addActionListener(this);
                select_b.setActionCommand("browse..");
                
                select_l=new JLabel();
                select_l.setBounds(80,180,350,30);
                select_f.add(select_l);
                
                next_b=new JButton("Next");
                next_b.setBounds(350,350,100,30);
                select_f.add(next_b);
                next_b.addActionListener(this);
                next_b.setActionCommand("Next_select");
                
                select_f.setVisible(true);
            }
            else
            {
                JOptionPane.showMessageDialog(login_f,"Wrong Password or Username");
                password_pf.setText("");  //clear password and username fields when wrong content entered
                username_pf.setText("");
            }
        }
        
        if(p.equals("browse.."))
        {
           JButton open=new JButton();
                JFileChooser fc=new JFileChooser();
                //fc.setCurrentDirectory(new java.io.File("Users/niteenpawar"));
                
                fc.setDialogTitle("Directory");
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                if(fc.showOpenDialog(open)==JFileChooser.APPROVE_OPTION)
                {
                    FileName=fc.getSelectedFile();
                   // System.out.print("you choose"+fc.getSelectedFile());
                    name=FileName.toString();
                    select_l.setText(name);  
                }
        }
        
        if(p.equals("Next_select"))
        {
            select_f.setVisible(false);
            commit_f=new JFrame("Attendance System");
            commit_f.setSize(500,500);
            commit_f.setLayout(null);
            commit_f.setResizable(false);
            commit_f.getContentPane().setBackground(new Color(220,220,220)); 
            
            first_l=new JLabel("Names");
            first_l.setBounds(70,50,50,50);
            commit_f.add(first_l);
              
            try 
            {
                FileInputStream fileInputStream=new FileInputStream(name);
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                workbook.getSheetName(0);
                Fillo fillo=new Fillo();        //connecting to fillo to use query statement
                Connection connection =fillo.getConnection(name);
                
                strQuery="Select * from "+workbook.getSheetName(0);
                Recordset rs=connection.executeQuery(strQuery);
                //System.out.print(rs);
                int i=0;
                while(rs.next())
                { 
                names[i]=rs.getField("Name");
                //System.out.println(names[i]);
                i++;
                }
                rs.close();
            } 
            
            
            catch (FilloException | IOException ex) 
            {
               System.out.println(" "+ex);
            } 
            
            JPanel panel = new JPanel();
            first_li=new JList(names);
             JScrollPane scrollPane = new JScrollPane();
            scrollPane.setViewportView(first_li);
            first_li.setLayoutOrientation(JList.VERTICAL);
            first_li.setFixedCellHeight(20);
            first_li.setVisibleRowCount(7);
            panel.add(scrollPane);
            panel.setBounds(150,50,350,150);
            
            first_li.setSelectionModel(new DefaultListSelectionModel() 
            {
                @Override
                public void setSelectionInterval(int index0, int index1) 
                {
                    if(super.isSelectedIndex(index0)) {
                        super.removeSelectionInterval(index0, index1);
                    }
                    else 
                    {
                        super.addSelectionInterval(index0, index1);
                    }
                }});
             panel.setSize(300,150);
            commit_f.add(panel);
            
            eventname_l=new JLabel("Event Name");
            eventname_l.setBounds(70,250,100,50);
            commit_f.add(eventname_l);
            
            eventname_t=new JTextField();
            eventname_t.setBounds(170,250,300,50);
            commit_f.add(eventname_t);
            
            commit_b=new JButton("Commit");
            commit_b.setBounds(350,350,100,30);
            commit_b.addActionListener(this);
            commit_b.setActionCommand("Commit");
            commit_f.add(commit_b);
            
            commit_f.setVisible(true);
        }
        
        if(p.equals("Commit"))
        {
             list=new ArrayList();
             list =first_li.getSelectedValuesList();
             //System.out.println(list);
             
            if(eventname_t.getText()==null)
            {
                JOptionPane.showMessageDialog(commit_f,"Enter Event Name");
            }
            else
            {
                commit_f.setVisible(false);
                Confirm_f=new JFrame("Attendance System");
                Confirm_f.setSize(500,500);
                Confirm_f.setLayout(null);
                Confirm_f.setResizable(false);
                Confirm_f.getContentPane().setBackground(new Color(220,220,220)); 
                while(list.contains(null))
                {
                    list.remove(null);
                }
                
                Finallist_l=new JLabel("<html>"+list.toString()+"</html>");
                Finallist_l.setHorizontalAlignment(JLabel.CENTER);
                Finallist_l.setFont(new Font("TimesRoman",Font.BOLD,13));
                Finallist_l.setForeground(Color.RED);
                Finallist_l.setBounds(10,70,460,350);
                Confirm_f.add(Finallist_l);
                
                hours_cb=new JComboBox(hours);
                hours_cb.setBounds(150,50,70,40);
                Confirm_f.add(hours_cb);
                
                hours_l=new JLabel("Hours");
                hours_l.setBounds(70, 50, 60, 40);
                Confirm_f.add(hours_l);
                
                confirm_b=new JButton("Confirm");
                confirm_b.setBounds(350,400,100,30);
                confirm_b.addActionListener(this);
                confirm_b.setActionCommand("Confirm");
                Confirm_f.add(confirm_b);
                
                back_b=new JButton("Back");
                back_b.setBounds(50,400,100,30);
                back_b.addActionListener(this);
                back_b.setActionCommand("Back");
                Confirm_f.add(back_b);
                
                Confirm_f.setVisible(true);
                
            }  
        }
        if(p.equals("Back"))
        {
            Confirm_f.setVisible(false);
            commit_f.setVisible(true);  
        }
        
        if(p.equals("Confirm"))
        {
            Done_f=new JFrame("Attendance System");
            Done_f.setSize(500,500);
            Done_f.setLayout(null);
            Done_f.setResizable(false);
            Done_f.getContentPane().setBackground(new Color(220,220,220)); 
            
            Thank_l=new JLabel("Thank You");
            Thank_l.setBounds(180,150,200,100);
            Thank_l.setFont(new Font("TimesRoman",Font.BOLD,23));
            Thank_l.setForeground(Color.RED);
            Done_f.add(Thank_l);
            
            Done_f.setVisible(true);
                
            try 
            {
                FileInputStream fileInputStream=new FileInputStream(name);
                XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
                workbook.getSheetName(0);
                Fillo fillo=new Fillo();        //connecting to fillo to use query statement
                Connection connection =fillo.getConnection(name);
                int n=list.size();
                String x=eventname_t.getText();
                //strQuery="Alter table "+workbook.getSheetName(0)+" add column "+x+" Varchar(20)";
                for(int i=0;i<n;i++)
                {
                    strQuery="Update "+workbook.getSheetName(0)+" set "+x+"='"+hours_cb.getSelectedItem()+"' Where Name='"+list.get(i)+"'";
                    int rs=connection.executeUpdate(strQuery);
                }  
            } 
            catch (FilloException | IOException ex) 
            {
                System.out.println("ExceptionFound " +ex);
            }
                
         } 
    
    }

}

public class AttendanceSystem {
    
    public static void main(String[] args) 
    {
        Attendance attend=new Attendance();
    }
}

