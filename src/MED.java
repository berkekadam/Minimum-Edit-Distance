import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
public class MED {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Creating the Frame
        JFrame frame = new JFrame("MED Assignment");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,400);
       

        // Text Area at the Center
        JTextArea ta = new JTextArea();

        JPanel panel = new JPanel(); 
        JLabel label = new JLabel("Enter Word for Part 1");
        JTextField tf = new JTextField(20); 
        JButton button = new JButton("Send");
        button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input0 = tf.getText().toLowerCase();  // Get input for Task1
				try {
					ta.setText(Task1(input0)); // Take input to Task 1 method
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(button);
        
        JPanel panel2 = new JPanel(); 
        JLabel label2 = new JLabel("Enter First Word");
        JLabel label3 = new JLabel("Enter Second Word");
        JTextField tf2 = new JTextField(10); 
        JTextField tf3 = new JTextField(10); 
        JButton button2 = new JButton("Send");
        button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input1 = tf2.getText().toLowerCase();
				String input2 = tf3.getText().toLowerCase();
				ta.setText(Task2(input1,input2));	// Take inputs to Task 2 Method
				
			}
		});
        panel2.add(label2); 
        panel2.add(tf2);
        panel2.add(label3); 
        panel2.add(tf3);
        panel2.add(button2);
        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.NORTH, panel); // Panel for Part 1
        frame.getContentPane().add(BorderLayout.SOUTH, panel2); // Panel for Part 2
        frame.getContentPane().add(BorderLayout.CENTER, ta); // Common Text Area
        frame.setVisible(true);
	}
	
	public static String Task1(String input) throws IOException {
		long startTime = System.currentTimeMillis();
		// Read the dictionary
		BufferedReader bufReader = new BufferedReader(new FileReader("./sozluk.txt"));
		ArrayList<String> dict = new ArrayList<>(); // Words will be stored in here.
		String line = bufReader.readLine();
		while (line != null) { 
			dict.add(line); 	
			line = bufReader.readLine();
		} 
		bufReader.close();
		ArrayList<Integer> distances = new ArrayList<>(); // all distances will be hold here
		for (int i = 0; i < dict.size(); i++) { // travel all dictionary to make comparisons and calculate distances
				int distance = 0;
				String now = dict.get(i);	// String now is the currently selected word to comparisons
				
				// We are taking the MED distance of Input to Now.
				/*		#	N	O	W
				 * #	0	1	2	3
				 * I	1				we need a 2-dimensional matrix: named Compare
				 * N	2				input's length will determine matrix's column number
				 * P	3				now's length will determine matrix's row number
				 * U	4				we can fill first row and column at the first place.
				 * T	5
				 */
				
				int[][] compare = new int[input.length()+1][now.length()+1]; // +1 because of empty string known as #
				for (int j = 0; j <= input.length(); j++) { // fill first column
					compare[j][0] = j;
				}
				for (int j = 0; j <= now.length(); j++) { // fill first row
					compare[0][j] = j;
				}
				
				// We'll fill other elements of compare matrix. But we should start from row = 1, column = 1.
				for (int j = 1; j < input.length() + 1; j++) {
					for (int j2 = 1; j2 < now.length() + 1; j2++) {
						// Make char comparison
						char a = now.charAt(j2-1); // Java String charAt starts counting from 0.
						char b = input.charAt(j-1);  
						if (b == a) { // if chars are the same; we'll directly get the value from upper left corner.
							compare[j][j2] = compare[j-1][j2-1];
						}
						else { // if chars are not same; get the Min from upperleft or left or up
							ArrayList<Integer> getMin = new ArrayList<>();
							getMin.add(compare[j-1][j2-1]); // REPLACE
							getMin.add(compare[j-1][j2]);  // DELETE 
							getMin.add(compare[j][j2-1]); // INSERT
							compare[j][j2] = Collections.min(getMin) + 1;
						}
					}
				}
				
				distance = compare[input.length()][now.length()]; 
				distances.add(distance); // add distance to arrayList
			}
		
		// PRINT THE RESULTS
		
		String returnString = "";
		for (int i = 0; i < 10; i++) {
			int nearest = Collections.min(distances);
			int toRemove = distances.indexOf(Collections.min(distances));
			String theWords = dict.get(toRemove);
			returnString += "\t" + theWords + "---" + nearest + "\r\n";
			dict.remove(toRemove);
			distances.remove(toRemove);
		}
		long estimatedTime = System.currentTimeMillis() - startTime;
	    returnString += ("\tExecution time: " + estimatedTime + " miliseconds");
		return returnString;
	}
	
	// TASK 2
	
	public static String Task2(String first, String second) {
		long startTime = System.currentTimeMillis();
		String returnString = "";
		int distance = 0;
		// We are taking the MED distance of Input(First) to Now(Second).
		// Transformation will be from First to Second
		/*		#	S	E	C	O	N	D		
		 * #	0	1	2	3	4	5	6
		 * F	1			
		 * I	2			
		 * R	3			
		 * S	4			
		 * T	5
		 */
		
		int[][] compare = new int[first.length()+1][second.length()+1]; // +1, because of empty string known as #
		for (int j = 0; j <= first.length(); j++) { // fill first column
			compare[j][0] = j;
		}
		for (int j = 0; j <= second.length(); j++) { // fill first row
			compare[0][j] = j;
		}
		
		// We'll fill other elements of compare matrix. But we should start from row = 1, column = 1.
		for (int j = 1; j < first.length() + 1; j++) {
			for (int j2 = 1; j2 < second.length() + 1; j2++) {
				// Make char comparison
				char a = second.charAt(j2-1); // Java String charAt starts counting from 0.
				char b = first.charAt(j-1);  
				if (b == a) { // if chars are the same; we'll directly get the value from upper left corner.
					compare[j][j2] = compare[j-1][j2-1];
				}
				else { // if chars are not same; get the Min from upperleft or left or up
					ArrayList<Integer> getMin = new ArrayList<>();
					getMin.add(compare[j-1][j2-1]); // REPLACE
					getMin.add(compare[j-1][j2]);  // DELETE 
					getMin.add(compare[j][j2-1]); // INSERT
					compare[j][j2] = Collections.min(getMin) + 1;
				}
			}
		}
		
		distance = compare[first.length()][second.length()]; 
		returnString += "\t";
		
		for (int i = 0; i < compare.length; i++) {
			for (int j = 0; j < compare[1].length; j++) {
				returnString += (compare[i][j]+" ");
			}
			returnString += ("\r\n \t");
		}
		
		returnString += ("Med Value Between Words is: " + distance + "\r\n");
	
	boolean notEnd = true;
	int row = compare.length - 1; 
	int column = compare[1].length - 1; 
	int replaceCount = 0;
	int deleteCount = 0;
	int insertCount = 0;
	while (notEnd) {
		char a = first.charAt(row-1);
		char b = second.charAt(column-1);
		if (a == b) {
			row--;
			column--;
			if(row==0 && column == 0) { // if we reach to start of Compare matrix
				notEnd=false;
				break;
			}
			else if(row==0) {
				while (notEnd) {
					insertCount++;
					column--;
					if(column == 0) {
						notEnd=false;
						break;
					}
				}
			}
			else if(column==0) {
				while (notEnd) {
					deleteCount++;
					row--;
					if(row == 0) {
						notEnd=false;
						break;
					}
				}
			}
		}
		else {
			ArrayList<Integer> getMin = new ArrayList<>();
			getMin.add(compare[row-1][column-1]); // REPLACE
			getMin.add(compare[row-1][column]);  // DELETE 
			getMin.add(compare[row][column-1]); // INSERT
			if(getMin.indexOf(Collections.min(getMin))==0) {
				replaceCount++;
				row--;
				column--;
			}
			else if(getMin.indexOf(Collections.min(getMin))==1) {
				deleteCount++;
				row--;
			}
			else if(getMin.indexOf(Collections.min(getMin))==2) {
				insertCount++;
				column--;
			}
			if(row==0 && column == 0) { // if we reach to start of Compare matrix
				notEnd=false;
				break;
			}
			else if(row==0) {
				while (notEnd) {
					insertCount++;
					column--;
					if(column == 0) {
						notEnd=false;
						break;
					}
				}
			}
			else if(column==0) {
				while (notEnd) {
					deleteCount++;
					row--;
					if(row == 0) {
						notEnd=false;
						break;
					}
				}
			}
		}
	}

	returnString += ("\tThis transform requires \r\n \t" + 
	replaceCount + " Replace/Substitions \r\n \t" + 
	deleteCount + " Deletions \r\n \t" + 
	insertCount + " Insertions ");
	long estimatedTime = System.currentTimeMillis() - startTime;
    returnString += ("\r\n \tExecution time: " + estimatedTime + " miliseconds");
	return returnString;
	}

}
