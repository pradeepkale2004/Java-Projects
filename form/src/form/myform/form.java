package form.myform;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class form extends Frame implements ActionListener , AdjustmentListener,ItemListener {
	
	
	String url = "jdbc:mysql://localhost:3307/myform";
    String user = "root";   
    String password = "root"; 
    Scanner sc = new Scanner(System.in);

    Connection conn = null;
    PreparedStatement preparedStatement = null;
    
    TextField t1, t2, t3;
    TextArea ta1;
    Checkbox r1, r2;
    Checkbox c1,c2,c3,c4;
    Scrollbar s1;
    Choice ch1;
    

    public form() {
        super("My Admission form");
            
        setSize(500, 900);
        setLayout(null);

        Label l1, l2, l3, l4, l5, l6 ,l7,l8,l9;
        CheckboxGroup cbg = new CheckboxGroup();
        
        Button submit = new Button("Submit");

        r1 = new Checkbox("Male", cbg, true);
        r2 = new Checkbox("Female", cbg, false);
        
        c1= new Checkbox("Java");
        c2= new Checkbox("Python");
        c3= new Checkbox("C");
        c4= new Checkbox("C++");
        

        l1 = new Label("First Name");
        l2 = new Label("Middle Name");
        l3 = new Label("Last Name");
        l4 = new Label("Gender");
        l5 = new Label("Address");
        l6 = new Label("Select Programming Language");
        l7 = new Label("Select Age");
        l8 = new Label("Entered Age");
        l9 = new Label("Languages Known");

        t1 = new TextField();
        t2 = new TextField();
        t3 = new TextField();

        ta1 = new TextArea();
        
        s1=new Scrollbar(Scrollbar.HORIZONTAL,0,20,0,200);
        
        ch1=new Choice();
        ch1.add("Marathi");
        ch1.add("Hindi");
        ch1.add("English");

        l1.setBounds(30, 50, 80, 20);
        t1.setBounds(120, 50, 300, 25);

        l2.setBounds(30, 90, 80, 20);
        t2.setBounds(120, 90, 300, 25);

        l3.setBounds(30, 130, 80, 20);
        t3.setBounds(120, 130, 300, 25);

        l4.setBounds(30, 170, 80, 20);
        r1.setBounds(120, 170, 80, 25);
        r2.setBounds(210, 170, 100, 25);

        l5.setBounds(30, 210, 80, 20);
        ta1.setBounds(120, 210, 300, 120);
        
        l6.setBounds(30, 350, 200, 20);
        c1.setBounds(30,370,80,20);
        c2.setBounds(130,370,80,20);
        c3.setBounds(230,370,80,20);
        c4.setBounds(330,370,80,20);
        
        l7.setBounds(30, 400, 100, 20);
        s1.setBounds(170,400,200,20);
        
        l8.setBounds(30, 450, 100, 40);
        l9.setBounds(30, 500, 100, 40);
        
        ch1.setBounds(30, 540, 100, 40);
        
        
        s1.setMinimum(1);    
        s1.setMaximum(150);  
        s1.setValue(75);     
        s1.setVisibleAmount(10);
        
        submit.setBounds(150, 600, 80, 30);

        add(l1);
        add(t1);

        add(l2);
        add(t2);

        add(l3);
        add(t3);

        add(l4);
        add(r1);
        add(r2);

        add(l5);
        add(ta1);
        
        add(l6);
        add(c1);
        add(c2);
        add(c3);
        add(c4);
        
        add(l7);
        add(s1);
        
        add(l8);
        add(ch1);
        add(l9);
       
        submit.addActionListener(this);
        add(submit);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
        
        ch1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                String selectedItem = ch1.getSelectedItem(); 
                System.out.println("Selected: " + selectedItem);
            }
        });
        
        
        s1.addAdjustmentListener(new AdjustmentListener() {
            
        	@Override
        	public void adjustmentValueChanged(AdjustmentEvent e) {
        		// TODO Auto-generated method stub
        		 int currentValue = s1.getValue(); // Get the scrollbar value
                 l8.setText("Your Age " + currentValue);
        		
        	}
            });

        setVisible(true);
    }

    
    
    
    

    public static void main(String[] args) {
        new form();
    }
    
    

    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("First Name: " + t1.getText());
        System.out.println("Middle Name: " + t2.getText());
        System.out.println("Last Name: " + t3.getText());
        System.out.println("Gender: " + (r1.getState() ? "Male" : "Female"));
        System.out.println("Address: " + ta1.getText());
        
        String result = "Selected Languages: ";
        if (c1.getState()) result += "Java ";
        if (c2.getState()) result += "Python ";
        if (c3.getState()) result += "C ";
        if (c4.getState()) result += "C++ ";
        
        int currentValue = s1.getValue();
        System.out.println("Your age is " + currentValue);
        System.out.println("Language known " + ch1.getSelectedItem());
        
		  try {
				
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            conn = DriverManager.getConnection(url, user, password);
	            String sql = "INSERT INTO form (first_name, middle_name, last_name, gender, address, language,age,languageknown) VALUES (?, ?, ?, ?, ?, ?,?,?)";
	            preparedStatement = conn.prepareStatement(sql);

	       
	            String value1 = t1.getText();
	            String value2 = t2.getText();
	            String value3 = t3.getText();
	            String value4= (r1.getState() ? "Male" : "Female");
	            String value5= ta1.getText();
	            String value6 = "";
	            if (c1.getState()) value6 += "Java ";
	            if (c2.getState()) value6 += "Python ";
	            if (c3.getState()) value6 += "C ";
	            if (c4.getState()) value6 += "C++ ";
	            int value7=s1.getValue();
	            String value8 = ch1.getSelectedItem();
	       
	            preparedStatement.setString(1, value1);
	            preparedStatement.setString(2, value2);
	            preparedStatement.setString(3, value3);
	            preparedStatement.setString(4, value4);
	            preparedStatement.setString(5, value5);
	            preparedStatement.setString(6, value6);
	            preparedStatement.setInt(7, value7);
	            preparedStatement.setString(8, value8);

	     
	            int rowsAffected = preparedStatement.executeUpdate();
	            if (rowsAffected > 0) {
	                System.out.println("Record inserted successfully!");
	            } else {
	                System.out.println("Failed to insert record.");
	            }

	        } catch (ClassNotFoundException e3) {
	            System.err.println("JDBC Driver not found: " + e3.getMessage());
	        } catch (SQLException e4) {
	            System.err.println("Database error: " + e4.getMessage());
	        } finally {

	            try {
	                if (preparedStatement != null) preparedStatement.close();
	                if (conn != null) conn.close();
	          
	            } catch (SQLException e5) {
	                System.err.println("Error closing resources: " + e5.getMessage());
	            }
	        }
        
    

   

	
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		
	}
    }
	

