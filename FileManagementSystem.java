import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;



class AboutWindow extends JFrame {

    public AboutWindow() {
        setTitle("Developer Details");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Create a JPanel to hold the content
        JPanel contentPanel = new JPanel();
        
        // Create JLabels for each detail
        JLabel enrolmentLabel = new JLabel("Enrollment No.: 2100450021043");
        JLabel nameLabel = new JLabel("Name: Shah Aayush Nitinbhai");
		
        JLabel divLabel = new JLabel("Div: A");
        JLabel contactLabel = new JLabel("Contact No.: 9428747500");
        JLabel emailLabel = new JLabel("Email: shahaayush928@gmail.com");
        
        // Add the JLabels to the content panel
        contentPanel.add(enrolmentLabel);
        contentPanel.add(nameLabel);
        contentPanel.add(divLabel);
        contentPanel.add(contactLabel);
        contentPanel.add(emailLabel);
        
		
        // Create a JLabel for the app icon
        JLabel appIconLabel = new JLabel();
        ImageIcon icon = new ImageIcon("C:/Users/HP/Desktop/48/aayush.png");
		Image image = icon.getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        appIconLabel.setIcon(new ImageIcon(image));
        appIconLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
        appIconLabel.setIcon(icon);
        appIconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        contentPanel.add(appIconLabel, BorderLayout.SOUTH);

        // Set the background color
        contentPanel.setBackground(Color.WHITE);

        // Add the content panel to the window
        add(contentPanel);

        // Set the window size and make it visible
        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}


public class FileManagementSystem extends JFrame {

    private static final long serialVersionUID = 1L;

    private final String url = "jdbc:mysql://localhost:3306/mydatabase";
    private final String user = "root";
    private final String password = "";

    private Connection conn;

    public FileManagementSystem() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create file menu
        JMenu fileMenu = new JMenu("File");
		JMenu sortMenu = new JMenu("Sort");
		JMenu report = new JMenu("Report");
		JMenu detail = new JMenu("About");
		JMenu exit = new JMenu("Exit");
		

        // Create upload menu item
        JMenuItem uploadMenuItem = new JMenuItem("Upload");
        uploadMenuItem.addActionListener((event) -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String name = file.getName();
                try (FileInputStream fis = new FileInputStream(file)) {
                    byte[] data = fis.readAllBytes();
                    String sql = "INSERT INTO files (name, data) VALUES (?, ?)";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, name);
                        pstmt.setBytes(2, data);
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "File uploaded successfully!");
                    }
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create delete menu item
        JMenuItem deleteMenuItem = new JMenuItem("Delete");
        deleteMenuItem.addActionListener((event) -> {
            int fileId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter file ID to delete:"));
            String sql = "DELETE FROM files WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, fileId);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "File deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "File not found!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Create download menu item
        JMenuItem downloadMenuItem = new JMenuItem("Download");
        downloadMenuItem.addActionListener((event) -> {
            int fileId = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter file ID to download:"));
            String sql = "SELECT name, data FROM files WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, fileId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    byte[] data = rs.getBytes("data");
                    try (FileOutputStream fos = new FileOutputStream(name)) {
                        fos.write(data);
                        JOptionPane.showMessageDialog(this, "File downloaded successfully!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "File not found!");
                }
            } 
			catch (SQLException e)
			{
                System.out.println(e.getMessage());
			}
		});
		        // Create list menu item
        JMenuItem listMenuItem = new JMenuItem("List");
        listMenuItem.addActionListener((event) -> {
            String sql = "SELECT id, name FROM files";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                List<String> fileList = new ArrayList<>();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    fileList.add(id + ". " + name);
                }
                if (fileList.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No files found!");
                } else {
                    // Sort the list alphabetically by file name
                    Collections.sort(fileList, Comparator.comparing(String::toString));
                    String files = String.join("\n", fileList);
                    JOptionPane.showMessageDialog(this, files, "List of Files", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
			
        });
    // Create sort by name menu item
    JMenuItem sortByNameMenuItem = new JMenuItem("Sort by name");
    sortByNameMenuItem.addActionListener((event) -> {
        String sql = "SELECT id, name FROM files ORDER BY name";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            List<FileData> fileList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                FileData fileData = new FileData(id, name);
                fileList.add(fileData);
            }
            Collections.sort(fileList, Comparator.comparing(FileData::getName));
            StringBuilder sb = new StringBuilder();
            for (FileData fileData : fileList) {
                sb.append(fileData.getId()).append(" | ").append(fileData.getName()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    });

    // Create sort by size menu item
    JMenuItem sortBySizeMenuItem = new JMenuItem("Sort by size");
    sortBySizeMenuItem.addActionListener((event) -> {
        String sql = "SELECT id, name, LENGTH(data) AS size FROM files ORDER BY size";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            List<FileData> fileList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                long size = rs.getLong("size");
                FileData fileData = new FileData(id, name, size);
                fileList.add(fileData);
            }
            Collections.sort(fileList, Comparator.comparing(FileData::getSize));
            StringBuilder sb = new StringBuilder();
            for (FileData fileData : fileList) {
                sb.append(fileData.getId()).append(" | ").append(fileData.getName()).append(" | ")
                        .append(fileData.getSize()).append(" bytes\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    });
	
	
	JMenuItem reportItem = new JMenuItem("Generate Report");
			reportItem.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					try{
					PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM files");
					ResultSet rs = null;
					rs = ps.executeQuery();
					FileOutputStream fos = new FileOutputStream("File Detail.txt");
					String s = "    Files REPORT\n";
					
					while(rs.next())
					{
						s = s + rs.getInt(1) + " " +
									rs.getString(2)+"\n";
					}
					
					for(int x = 0 ; x < s.length() ; x++)
					{
						fos.write(s.charAt(x));
					}
					JOptionPane.showMessageDialog(null, s);
					fos.close();
					}
					catch (Exception e3) {
							e3.printStackTrace();
					}
				}
			});
	
	JMenuItem detailItem = new JMenuItem("About Developer");
			detailItem.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					 new AboutWindow();
				}
			});
	
	JMenuItem exitMenuItem = new JMenuItem("Exit");
		 exitMenuItem.setBackground(Color.red);
			exitMenuItem.addActionListener(new ActionListener() 
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					JOptionPane.showMessageDialog(null,"Developed By Aayush Shah\nContact No :- 9428747500\n shahaayush928@gmail.com");
					System.exit(0);
				}
			});
	
	/*
	   // Create login menu item
    JMenuItem loginMenuItem = new JMenuItem("Login");
    loginMenuItem.addActionListener((event) -> {
        String username = JOptionPane.showInputDialog(this, "Enter username:");
        String password = JOptionPane.showInputDialog(this, "Enter password:");
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    });

*/
    // Add menu items to menu
    fileMenu.add(uploadMenuItem);
    fileMenu.add(deleteMenuItem);
    fileMenu.add(downloadMenuItem);
	fileMenu.add(listMenuItem);
//	fileMenu.addSeparator();
	//fileMenu.add(loginMenuItem);
    
    sortMenu.add(sortByNameMenuItem);
    sortMenu.add(sortBySizeMenuItem);
	
	report.add(reportItem);
	detail.add(detailItem);
	
	exit.add(exitMenuItem);

    // Add menus to menu bar
    menuBar.add(fileMenu);
    menuBar.add(sortMenu);
	menuBar.add(report);
	menuBar.add(detail);
	menuBar.add(exit);

    // Set menu bar
    setJMenuBar(menuBar);




    // Set frame properties
    setTitle("File Management System \t\t\t\t\t\t Desined By Aayush");
    setSize(700, 500);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
}

public static void main(String[] args) {
	
	EventQueue.invokeLater(new Runnable() 
		{
			String username = "aayush";
			String password = "aayush";
			String enteredUsername = JOptionPane.showInputDialog("Enter username:");
			String enteredPassword = JOptionPane.showInputDialog("Enter password:");
			// Check if entered username and password match with the stored credentials
			@Override
			public void run() 
			{
				if (enteredUsername.equals(username) && enteredPassword.equals(password)) 
				{
					 new FileManagementSystem().setVisible(true);
				}
				else 
				{
					JOptionPane.showMessageDialog(null, "Invalid username or password.");
					System.exit(0);
				}
					
			}
		});
}

private class FileData {
    private int id;
    private String name;
    private long size;

    public FileData(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public FileData(int id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
}