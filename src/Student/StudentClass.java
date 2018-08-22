
package Student;

import DataBaseConnection.DbConnect;
import java.sql.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class StudentClass {
    String roll; 
    String name;
    String course;
    
    public void setRollNumber(String no)
    {
        this.roll = no;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setCourse(String course)
    {
        this.course = course;
    }
    public String getRollNumber()
    {
        return roll;
    }
    public String getName()
    {
        return name;
    }
    public String getCourse()
    {
        return course;
    }
    public void homePage(StudentClass sc1,String roll,String name,String cid)
    {
        this.setRollNumber(roll);
        this.setName(name);
        StudentMain sm = new StudentMain(sc1);
        sm.jTextField1.setText(this.roll);
        sm.jTextField2.setText(this.name);
        sm.setVisible(true);
        sm.parentPanel.removeAll();
        sm.parentPanel.add(sm.jPanel1);
        sm.parentPanel.repaint();
        sm.parentPanel.revalidate();
        try
        {
             DbConnect.Connect();
             String query = "select cname from CourseList where cid = (Select Course from Student where RollNumber = "+this.roll+");";
             ResultSet rs =DbConnect.stmt.executeQuery(query);
             if(rs.next())
             {
                 this.setCourse(rs.getString(1));
             }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    public void setTableMarks(StudentMain sm)
    {
        try
        {
            DbConnect.Connect();
            DefaultTableModel model = (DefaultTableModel)sm.jTable1.getModel();
            while (model.getRowCount()>0)
            {
                model.removeRow(0);
            }
            String query = "select t.sid,s.sname,t.marks from Subject s, Takes t where s.sid=t.sid and RollNumber = "+sm.jTextField1.getText()+";";
            ResultSet rs = DbConnect.stmt.executeQuery(query);
            while(rs.next())
            {
                model.addRow(new Object[]{rs.getString(1),rs.getString(2),rs.getString(3)});
            }
            DbConnect.Disconnect();
            sm.jTable1.setModel(model);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(sm,e.getMessage());
        }
    }
    public void setNoSubjectList(StudentMain sm)
    {
        try
        {
            DbConnect.Connect();
            String query2 = " select s.sname from Subject s,courseSubjects c where c.sid=s.sid "
                    + "and c.cid=(select CID from courseList where cname='"+this.getCourse()+"') "
                    + "and s.sid not in (select sid from takes where RollNumber="+sm.jTextField1.getText()+");";
            ResultSet rs2 = DbConnect.stmt.executeQuery(query2);
            DefaultListModel model = (DefaultListModel)sm.jList1.getModel();
            while(model.getSize()>0)
            {
                model.remove(0);
            }

            while (rs2.next())
            {
               model.addElement(rs2.getString(1));
            }
            DbConnect.Disconnect();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(sm, e.getMessage());
        }
    }
    public void setFacultyTable(StudentMain sm)
    {
        try
        {
            DbConnect.Connect();
            String query = "select t.Name , s.sname from teacher t,subject s "
                    + "where t.sid=s.sid and t.year = "
                    + "(Select year from Student where RollNumber = "+sm.jTextField1.getText()+");";
            ResultSet rs = DbConnect.stmt.executeQuery(query);
            DefaultTableModel model = (DefaultTableModel)sm.jTable2.getModel();
            while (model.getRowCount()>0)
            {
                model.removeRow(0);
            }
            while(rs.next())
            {
                model.addRow(new Object[]{rs.getString(1),rs.getString(2)});
            }
            sm.jTable2.setModel(model);
            DbConnect.Disconnect();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(sm,e.getMessage());
        }
    }
    public void setPersonalDetails(StudentMain sm)
    {
        try
            {
                DbConnect.Connect();
                String query = "Select DOB,Address,Phone,Email,Year,Course from Student where RollNumber = "+sm.jTextField1.getText()+";";
                ResultSet rs = DbConnect.stmt.executeQuery(query);

                if(rs.next())
                {
                    sm.jTextField4.setText(rs.getString(1));
                    sm.jTextField5.setText(rs.getString(2));
                    sm.jTextField6.setText(rs.getString(3));
                    sm.jTextField7.setText(rs.getString(4));
                    sm.jTextField8.setText(rs.getString(5));
                    String cid = rs.getString(6);
                    rs.close();
                    String query1 = "Select cname from courseList where cid = '"+cid+"';";
                    ResultSet rs1 = DbConnect.stmt.executeQuery(query1);
                    if(rs1.next())
                    {
                        sm.jTextField9.setText(rs1.getString(1));
                    }
                }
                
                DbConnect.Disconnect();
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(sm,e.getMessage());
            }
    }
    public static void setNotSubjectList(StudentMain sm)
    {
        try
        {
            DbConnect.Connect();
            String query2 = " select s.sname from Subject s,courseSubjects c where c.sid=s.sid "
                    + "and c.cid=(select CID from courseList where cname='"+sm.jTextField3.getText()+"') "
                    + "and s.sid not in (select sid from takes where RollNumber="+sm.jTextField1.getText()+");";
            ResultSet rs2 = DbConnect.stmt.executeQuery(query2);
            DefaultListModel model = (DefaultListModel)sm.jList1.getModel();
            while(model.getSize()>0)
            {
                model.remove(0);
            }

            while (rs2.next())
            {
               model.addElement(rs2.getString(1));
            }
            sm.jList1.setModel(model);
            DbConnect.Disconnect();
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(sm, e.getMessage());
        }
    }
    public void setCourseBox(StudentMain sm)
    {
        try
           {
               DbConnect.Connect();
               String query = "Select Cname from courseList";
               ResultSet rs = DbConnect.stmt.executeQuery(query);
               DefaultComboBoxModel model = (DefaultComboBoxModel)sm.jComboBox1.getModel();
               model.removeAllElements();
               while(rs.next())
               {
                   model.addElement(rs.getString(1));
               }
               sm.jComboBox1.setModel(model);
               DbConnect.Disconnect();
             
           }
           catch(Exception e)
           {
               JOptionPane.showMessageDialog(sm,e.getMessage());
           }
    }
            
}
