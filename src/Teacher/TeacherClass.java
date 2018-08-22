package Teacher;

import java.sql.*;
import DataBaseConnection.DbConnect;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
public class TeacherClass {
    String id;
    String name;
    String subject;
    
    public void setId(String id)
    {
        this.id = id;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setSubject(String sub)
    {
        this.subject = sub;
    }
    public String getId()
    {
        return this.id;
    }
    public String getName()
    {
        return this.name;
    }
    public String getSubject()
    {
        return this.subject;
    }
    public void homePage(String no,String name,String subject)
    {
        this.setId(no);
        this.setName(name);
        TeacherMain tm = new TeacherMain(this);
        tm.jTextField1.setText(this.id);
        tm.jTextField2.setText(this.name);
        tm.setVisible(true);
        tm.parentPanel.removeAll();
        tm.parentPanel.add(tm.jPanel1);
        tm.parentPanel.repaint();
        tm.parentPanel.revalidate();
        try
        {
             DbConnect.Connect();
             if (subject != null)
             {
                String query = "Select Sname from Subject where sid = '"+subject+"';";
                ResultSet rs = DbConnect.stmt.executeQuery(query);
                if(rs.next())
                {
                    this.setSubject(rs.getString(1));
                }
             }
             else 
                 this.setSubject(null);
             System.out.println(this.id+this.name+this.subject);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    public void chooseSubject(TeacherMain tm)
    {
        try
        {
            DbConnect.Connect();
            String query = "Select SID from Subject where Sname = '"+tm.jComboBox1.getSelectedItem()+"';";
            ResultSet rs = DbConnect.stmt.executeQuery(query);

            if(rs.next())
            {
                String query1 = "Update Teacher set SID = '"+rs.getString(1)+"' where TID = "+tm.jTextField1.getText()+";";
                DbConnect.stmt.executeUpdate(query1);
                JOptionPane.showMessageDialog(tm,"Updated Successfully");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(tm,e.getMessage());
        }
    }
    public void checkSubject(TeacherMain tm)
    {
        if(this.subject==null)
        {
            tm.jButton1.setEnabled(true);
            showSubject(tm);
        }
        else 
        {   
            tm.jTextField3.setText(this.getSubject());
            JOptionPane.showMessageDialog(tm,"Subject is already Selected.Contact ADMIN to change");
        }
    }
    public void showSubject(TeacherMain tm)
    {
        try
        {
            DbConnect.Connect();
            String query = "Select Sname from Subject;";
            ResultSet rs = DbConnect.stmt.executeQuery(query);
            DefaultComboBoxModel model = (DefaultComboBoxModel) tm.jComboBox1.getModel();
            while(rs.next())
            {
                model.addElement(rs.getString(1));
            }          
            DbConnect.Disconnect();
            tm.jComboBox1.setModel(model);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(tm,e.getMessage());
        }
    }
    public void setTable(TeacherMain tm)
    {
        try
        {
            DbConnect.Connect();
            DefaultTableModel model = (DefaultTableModel)tm.jTable1.getModel();

            while (model.getRowCount()>0)
            {
                model.removeRow(0);
            }
            String query="Select S.name,t.marks from Student s ,Takes t where s.RollNumber=t.RollNumber and t.sid In (Select SID from Teacher where TID = "+tm.jTextField1.getText()+");";
            ResultSet rs = DbConnect.stmt.executeQuery(query);
            while(rs.next())
            {
                model.addRow(new Object[]{rs.getString(1),rs.getString(2)});
            } 
            DbConnect.Disconnect();
            //model = null;
            tm.jTable1.setModel(model);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(tm,e.getMessage());
        }
    }
    public void setStudents(TeacherMain tm)
    {
        try
        {
            DbConnect.Connect();
            String query = "Select S.name,t.sid from Student s ,Takes t where s.RollNumber=t.RollNumber and t.sid = (Select SID from Teacher where TID = "+tm.jTextField1.getText()+");";
            
            ResultSet rs = DbConnect.stmt.executeQuery(query);
            tm.jComboBox2.removeAllItems();
            DefaultComboBoxModel model = (DefaultComboBoxModel)tm.jComboBox2.getModel();
            
            
            /*while (model.getSize()>0)
            {
                tm.jComboBox2.remove(0);
            }*/
            
            while(rs.next())
            {
                model.addElement(rs.getString(1));
            }
            DbConnect.Disconnect();
           // model = null;
            tm.jComboBox2.setModel(model);
            
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(tm,e.getMessage());
        }
    }
    
    public void setDetails(TeacherMain tm)
    {
        try
            {
                DbConnect.Connect();
                String query = "Select DOB,Address,Phone,Email,Year,SID from Teacher where TID = "+tm.jTextField1.getText()+";";
                ResultSet rs = DbConnect.stmt.executeQuery(query);

                if(rs.next())
                {
                    tm.jTextField9.setText(rs.getString(1));
                    tm.jTextField10.setText(rs.getString(2));
                    tm.jTextField11.setText(rs.getString(3));
                    tm.jTextField12.setText(rs.getString(4));
                    tm.jTextField13.setText(rs.getString(5));
                    String sid = rs.getString(6);
                    rs.close();
                    String query1 = "Select sname from subject where sid = '"+sid+"';";
                    ResultSet rs1 = DbConnect.stmt.executeQuery(query1);
                    if(rs1.next())
                    {
                        tm.jTextField14.setText(rs1.getString(1));
                    }
                }
                DbConnect.Disconnect();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(tm,e.getMessage());
            }
    }
}
