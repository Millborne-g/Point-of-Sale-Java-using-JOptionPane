
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
public class ProductOfSaleJOptionpane {

	 static  ProductJOptionpane[] p = new  ProductJOptionpane[10];
	    static Scanner input = new Scanner(System.in);
	    static DecimalFormat d = new DecimalFormat("0.00");
	    static String pId;
	    
	    static int pCount = 0,cnt=0;     // pCount is actual number of product records created, max is 10
	    static int numProd=0;      // numProd is number of products purchased
	    static int[] pIndex = new int[20];       // purchased products indices
	    static int[] purchaseQty = new int[20]; // purchased products quantity
	    static int[] numHS = new int[20];
	    static double[] amountDue =  new double[20]; // purchased products amounts
		static int iii;
		static int PD5;
	    
	    // method to create a new product record
	    static void createNewProduct(){             
	        String ans;
	        
	        String pName = null ,supplier = null;
	        int quantity; 
	        double price;
	        
	        JTextField id = new JTextField();
			JTextField name = new JTextField();
			JTextField quant = new JTextField();
			JTextField pricex = new JTextField();
		
			JTextField supplierx = new JTextField();
			
	        
	        do{
	        	/*System.out.println("\nCreating a New Product Record #"+(pCount+1)+"\nEnter the following Product Details: ");
	        	System.out.print("Product ID: "); pId = input.next();
	          System.out.print("Product Name: "); pName = input.next();
	            System.out.print("Product Quantity: "); quantity = input.nextInt();
	            System.out.print("Product Price: "); price = input.nextDouble();
	            System.out.print("Product Supplier: "); supplier = input.next();
	            */
	        try { 	Object obj[]= {
	             "ID: ",id, "Product Name: ",name,"Product Quantity: ",quant,"Product Price: ",pricex,"Product Supplier: ",supplierx
	        	};
	        	
	        	JOptionPane.showConfirmDialog(null, obj, null, JOptionPane.OK_CANCEL_OPTION);
	        	
	        	pId = id.getText();
	        	pName = name.getText();
	        	quantity = Integer.parseInt(quant.getText());
	        	price = Double.parseDouble(pricex.getText());
	        	supplier = supplierx.getText();
	        	
	        	
	            p[pCount++] = new  ProductJOptionpane(pId,pName,quantity,price,supplier);
	           
	           // System.out.println("Create more product?[y/n]");
	           iii= JOptionPane.showConfirmDialog(null, "Create more product?[y/n]",null , JOptionPane.YES_NO_OPTION);
	           // ans=input.next();
	        }catch(Exception e) {break;}
	        if(iii==JOptionPane.NO_OPTION) {
	        	 break; }
	        id.setText("");
			name.setText("");
			quant.setText("");
			pricex.setText("");
			supplierx.setText("");
	          } while (iii==JOptionPane.YES_OPTION);
	    }
	    
	    // returns the index of product record whose ID is same as the input ID, otherwise returns -1
	    static int productSearch(String xId)
	    {
	    	for (int i=0;i<pCount;i++) // scan all created product records for a product with ID same as the input ID
	    	{
	    		if (p[i].productId.equals(xId))
	    			return i;  // ID is found, return the product index
	    	}
	    	return -1;    	
	    }
	   
	    static int productSearchBS(int xId)
	    {
	    	for (int i=0;i<pCount;i++) // scan all created product records for a product with ID same as the input ID
	    	{
	    		if (p[i].numberSold==xId)
	    			return i;  // ID is found, return the product index
	    	}
	    	return -1;    	
	    }

	    // method to make a purchase
	    static void purchaseProduct(){
	        String ans;
	        int i; 
	        double cash, totalAmountDue=0, change;        
	                     JTextField x = new JTextField(); 
	        do{
	        try {	//System.out.println("Enter Product ID: "); 
	        	//pId = input.next();
	        	Object[] sss= {"Enter Product ID:",x};
	        	int PD1 =JOptionPane.showConfirmDialog(null, sss,null, JOptionPane.OK_CANCEL_OPTION);
	        	if(PD1==JOptionPane.CANCEL_OPTION) {break;}
	        	pId =x.getText();
	        
	        	i=productSearch(pId);
	        	if (i<0) // product is not found
	        	{
	        		int PD2 =JOptionPane.showConfirmDialog(null,"Product ID is not found.",null, JOptionPane.DEFAULT_OPTION);
	        		if(PD2==JOptionPane.CANCEL_OPTION) {break;}
	        	}
	        	else if (p[i].quantityAvailable<=0) // product is found but check the available quantity   		       		
	        	{ // check if product's available quantity is zero, if so then it's out of stock & purchase is not allowed.
	        		int PD3 =JOptionPane.showConfirmDialog(null,"Product is out of stock.",null, JOptionPane.DEFAULT_OPTION);
	        		if(PD3==JOptionPane.CANCEL_OPTION) {break;}
	        	}
	        	else
	        	{  // product's available quantity is >= zero, so purchase is allowed. Display selected product's name, available quantity & ask quantity to purchase 
	        		JTextField s = new JTextField();
	        		
	        		do {	Object[] iiii={
	        			"Product Name: "+p[i].productName
	        			,"Product Price: "+d.format(p[i].price)
	        			,"Product Available Quantity: "+p[i].quantityAvailable 
	        				,"Enter Quantity to Purchase: ",s}  ;
	        		int PD4  = JOptionPane.showConfirmDialog(null, iiii, null, JOptionPane.OK_CANCEL_OPTION);
	        		if(PD4==JOptionPane.CANCEL_OPTION) {break;}
	        				purchaseQty[numProd] = Integer.parseInt(s.getText());
	        				if (purchaseQty[numProd]>p[i].quantityAvailable)
	        					JOptionPane.showMessageDialog(null,"You may purchase a maximum of "+p[i].quantityAvailable+" only.");
	        			} while (purchaseQty[numProd]>p[i].quantityAvailable);
	        			 
	        			pIndex[numProd]=i; // remember purchased product index
	        			p[i].quantityAvailable=p[i].decreaseQuantityBy1(purchaseQty[numProd] );
	        			amountDue[numProd] = purchaseQty[numProd] * p[i].price; // compute amount due to customer for this product
	        			totalAmountDue += amountDue[numProd]; // compute total amount due for all products purchased                                                                       
	        			JOptionPane.showMessageDialog(null,"Amount Due: "+d.format(amountDue[numProd]));        			
	        			numProd++;
	        	
	        	PD5  = JOptionPane.showConfirmDialog(null,"Add more products to your cart? [y/n]",null, JOptionPane.YES_NO_OPTION);
	           // ans=input.next(); 
	        	}}catch(Exception e) {break;}
	        	if(PD5==JOptionPane.NO_OPTION) {break;}
	        	x.setText("");
	        } while (PD5  == JOptionPane.YES_OPTION);
	         
	         if (totalAmountDue>0) 
	         {
	        	   JTextField y = new JTextField();   
	             do 
	             { Object[] OPD1 = {
	            	"Total Amount Due: "+d.format(totalAmountDue)    
	            	 ,"Enter Customer Cash:",y 
	                 };
	             JOptionPane.showConfirmDialog(null,OPD1,null, JOptionPane.DEFAULT_OPTION); 
	             cash = Double.parseDouble(y.getText());
	            
		          
	            if (cash<totalAmountDue)
	            	JOptionPane.showConfirmDialog(null,"Cash tendered is lacking.",null, JOptionPane.DEFAULT_OPTION);
	             } while (cash<totalAmountDue);
	             
	             change = cash - totalAmountDue;
	              Object[] OPD2 = {
	             "Cash Tendered: "+d.format(cash)
	             ,"Change: "+d.format(change)
	             ,"Thank you. Please come again!"};
	              JOptionPane.showMessageDialog(null,OPD2);
	             // After purchased products total amount is paid, update records of those purchased products.  
	             upDatePurchasedProducts();  
	             // clear details of previous purchase made
	             clearPreviousPurchaseDetails();
	         }
	         else
	        	 JOptionPane.showMessageDialog(null,"No purchase made.");                               	                                 
	    }
	    
	    static void upDatePurchasedProducts()
	    {    
	    	for (int i=0 ; i<numProd ; i++) 
	    	{
	    		p[pIndex[i]].decreaseQuantityBy(purchaseQty[i]);
	    		p[pIndex[i]].increaseSales(amountDue[i]);
	    	}
	    }
	  /*  static void Addquant() {
	    	int i ;
	       	for( i = 0 ; i<pCount; i++) {
	     		if(p[i].quantityAvailable==0) {
	     			for( i = 0 ; i<pCount; i++) { 
	     				System.out.println("List of out stock products");
	         		System.out.println("\nProduct Number: "+(i+1)); 
	         		System.out.println("Product ID: "+p[i].productId); 
			            System.out.println("Product Name: "+p[i].productName); 
	     		}
	     			AddquantProcess();}
	     		
			      }   
	       	         
	     	
	     	
	     		}*/
	    	
	    static void AddquantProcess(int i)  {
	    	JTextField xxx = new JTextField();
	    	 p[i].quantityAvailable=0;
	     Object[] AP = {
	    		
			"\nProduct Number: "+(i+1) 
			
			,"Product ID: "+p[i].productId 
	         ,"Product Name: "+p[i].productName 
	        ,"Enter how many quantity you want to add:",xxx
	      };  
	      JOptionPane.showConfirmDialog(null, AP,null, JOptionPane.DEFAULT_OPTION);
	      int Aqty =Integer.parseInt(xxx.getText());
	      p[i].quantityAvailable= p[i].Aqty( Aqty, p[i].quantityAvailable); 
	      JOptionPane.showMessageDialog(null,"The quantity of the product has been sucessfully update"+ p[i].quantityAvailable+"");
	       
	    
	    	
	     	
	    }
	    static void BS() {
	    	int i ;
	    for( i = 0 ; i<pCount;i++) {
	        numHS[i]=p[i].numberSold;
	        cnt++;
			 }
	  	 int n = cnt; 
	    for ( i = 0; i < n-1; i++) 
	        for (int j = 0; j < n-i-1; j++) 
	            if (numHS[j] < numHS[j+1]) 
	            { 
	                // swap temp and arr[i] 
	                int temp = numHS[j]; 
	                numHS[j] = numHS[j+1]; 
	                numHS[j+1] = temp; 
	            } 
	    i=productSearchBS(numHS[0]);
	    if(p[i].numberSold<=0) {
	   	 JOptionPane.showMessageDialog(null,"No product has been sold");
	   	   
	    }
	    else {
	    	 JOptionPane.showMessageDialog(null,"The Best seller product is "+p[i].productName+" with "+p[i].numberSold +" sold items");
	   
	    }
	    }
	    static void clearPreviousPurchaseDetails()
	    {    
	    	for (int i=0 ; i<numProd ; i++) 
	    	{
	    		pIndex[i]=0; 
	    		purchaseQty[i] = 0;
	    		amountDue[i] = 0.0; 
	    	}
	    	numProd = 0;
	    }
	    
	    
	    public static void main(String[] args) {
	        
	        int option,i;
	        do{
	        	
	        	JLabel greetings =new JLabel("\nWELCOME to "+ ProductJOptionpane.storeName);
	        
	        	JFrame myFrame = new JFrame("nani");
	        	myFrame.setSize(400, 700);
	        	myFrame.setVisible(true);
	        	JPanel myPanel = new JPanel(new GridBagLayout());
	        	
	        	myPanel.add(greetings);
	         	;myFrame.add( myPanel);
	        	JButton chButton = new JButton("Create a Product");
	        
	        	JButton chButton1 = new JButton("Purchase a Product");
	        	JButton chButton2 = new JButton("Display a Product");
	            JButton chButton4 = new JButton("Display all out-of-stock products (zero quantity)");
	        	JButton chButton5 = new JButton("Display Bestseller products");
	        	JButton chButton6 = new JButton("Add quantity to out of stock products");
	        	JButton chButton7 = new JButton("Display all products total sales)");
	        	
	        	GridBagConstraints c = new GridBagConstraints();
	        	c.insets = new Insets(10,10,10,10);
	        	c.gridx = 0;
	        	c.gridy = 2;
	        	myPanel.add(chButton,c);
	        	c.gridx = 0;
	        	c.gridy = 3;
	        	myPanel.add(chButton1,c);
	        	c.gridx = 0;
	        	c.gridy = 4;
	        	myPanel.add(chButton2,c);
	       
	        	c.gridx = 0;
	        	c.gridy = 6;
	        	myPanel.add(chButton4,c);
	        	c.gridx = 0;
	        	c.gridy = 7;
	        	myPanel.add(chButton5,c);
	        	c.gridx = 0;
	        	c.gridy = 8;
	        	myPanel.add(chButton6,c);
	        	c.gridx = 0;
	        	c.gridy = 9;
	        	myPanel.add(chButton7,c);
	       
	        	chButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						createNewProduct(); 
				}
	        		
	        	});
	        	chButton1.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

		                if (pCount<=0) { //pCount is no. of product records created
		                	JOptionPane.showMessageDialog(null,"No available product to purchase.");
		                 }
		                else 
						purchaseProduct() ;
				}
	        		
	        	});
	        	chButton2.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
			
				
				String[] Col = new String[]{" ID "," Name "," Quantity ", " Price "," Supplier"};
				
				/*           
					
				    JTable table = new JTable(list,Col);
					table.setPreferredScrollableViewportSize(new Dimension(500,50));
					table.setFillsViewportHeight(true);
					JPanel m = new JPanel();
					m.add(table);
					JScrollPane scroll = new JScrollPane(table);
					
						JTextArea A = new JTextArea();
						A.setVisible(true);
						*/   
				DefaultTableModel model = new DefaultTableModel(Col, 0);
				for(int i=0 ; i<pCount;i++) {
					model.addRow(new Object[]{	p[i].productId,p[i].productName,p[i].quantityAvailable,p[i].price,p[i].supplier});
					} 
				//DefaultTableModel model1 = new DefaultTableModel(Col, 0);
				//model1.addColumn(new  Object[]{" ID "," Name "," Quantity ", " Price "," Supplier"});
						JTable table = new JTable(model);
						table.add(new JScrollPane());
						table.setVisible(true);
						JPanel n = new JPanel();
						n.add(table);
						n.setVisible(true);
						
					JOptionPane.showMessageDialog(null, n, null, JOptionPane.NO_OPTION);
						/* 	  if (pCount<=0) { //pCount is no. of product records created
		                    	System.out.println("\nNo available product to display.");
		                 }
		                 else
		                 {
		                	System.out.println("Displaying a total of "+pCount+" Products."); 
		                    for(int i = 0 ; i<pCount ; i++) {
		                        p[i].displayProductDetails();
		                    }
		                 }*/
					}
	        		
	        	});
	        	
	        	
	        	chButton4.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						 if(pCount<=0){
								JOptionPane.showMessageDialog(null,"No available product to display.");
			                 }
			                 else
			                 {		
			                	 String[] Col = new String[]{" ID "," Name "," Quantity ", " Price "," Supplier"};
			                	 DefaultTableModel model = new DefaultTableModel(Col,10);
			                	int outOfStock = 0;
			                    for(int i = 0;i<pCount;i++){
			                        if(p[i].quantityAvailable<=0){
			                        	model.addRow(new Object[]{	p[i].productId,p[i].productName,p[i].quantityAvailable,p[i].price,p[i].supplier});
			 
			                            outOfStock++;
			                        }
			                    }
			                	JTable table = new JTable(model);
								table.add(new JScrollPane());
								table.setVisible(true);
								JPanel n = new JPanel();
								n.add(table);
								n.add(table.getTableHeader());
								n.setVisible(true);
								
							JOptionPane.showMessageDialog(null, n, " ID "+" Name "+" Quantity "+ " Price "+" Supplier", JOptionPane.NO_OPTION);
							JOptionPane.showMessageDialog(null,"There are "+outOfStock+" out-of-stock product(s).");
			                 }
				}
	        		
	        	});
	        	
	        	
	        	chButton5.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

						  if (pCount<=0) { //pCount is no. of product records created
							  JOptionPane.showMessageDialog(null,"\nNo available product to display.");
			                  
			               }
			            	  else {
			            		  BS();
			            	  }
				}
	        		
	        	});
	        	chButton6.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {

					//pCount is no. of product records created

				                if (pCount<=0) { //pCount is no. of product records created
				                	JOptionPane.showMessageDialog(null,"No available product to purchase.");
			              }
			            	 else {
			            		 
			            		 JTextField tr = new JTextField();
									Object[] sss= {"Enter Product ID that has ZERO quantity:",tr};
						        	JOptionPane.showConfirmDialog(null, sss,null, JOptionPane.OK_CANCEL_OPTION);
						             
						        	pId =tr.getText();
			                 	int i=productSearch(pId);
			                 	if (p[i].quantityAvailable>0) // product is not found
			                 	{
			                 		JOptionPane.showMessageDialog(null,"Product has quantity");
			                 	}
			                 	else if(p[i].quantityAvailable<=0) {
			                 		 AddquantProcess(i);
			                 	}
			            	     		} 
				}
	        		
	        	});
	        	 
	        	
	        
	        	
	        	 
	        /*	System.out.println("\nWELCOME to "+ ProductJOptionpane.storeName);
	        	System.out.println("\nSelect your transaction:");
	        	System.out.println("1-Create a new product record");
	            System.out.println("2-Make a purchase");
	            System.out.println("3-Display all product records details");
	            System.out.println("4-Display all out-of-stock products (zero quantity)");
	            System.out.println("5-Display Bestseller products");
	            System.out.println("6-Add quantity to out of stock products");
	            System.out.println("7-Display all products total sales");
	            System.out.println("8-Exit");
	            System.out.print("\n-> ");*/
	            option = input.nextInt();
	            if (option==1)
	            {                
	            	createNewProduct();                   
	            }
	            else if (option==2)
	            {
	                if (pCount<=0) { //pCount is no. of product records created
	                    System.out.println("\nNo available product to purchase.");
	                 }
	                else 
	                	purchaseProduct();
	                	
	            }     
	            else if (option==3)
	            {
	                 if (pCount<=0) { //pCount is no. of product records created
	                    	System.out.println("\nNo available product to display.");
	                 }
	                 else
	                 {
	                	System.out.println("Displaying a total of "+pCount+" Products."); 
	                    for(i = 0 ; i<pCount ; i++) {
	                        p[i].displayProductDetails();
	                    }
	                 }
	            }    
	            else if (option==4)
	            {
	                 if(pCount<=0){
	                    System.out.println("\nNo available product to display.");
	                 }
	                 else
	                 {
	                	int outOfStock = 0;
	                    for(i = 0;i<pCount;i++){
	                        if(p[i].quantityAvailable<=0){
	                            p[i].displayProductDetails();
	                            outOfStock++;
	                        }
	                    }
	                    System.out.println("There are "+outOfStock+" out-of-stock product(s).");
	                 }
	             }       
	             else if (option==5)
	             {

	            	  if (pCount<=0) { //pCount is no. of product records created
	                  	System.out.println("\nNo available product to display.");
	                  
	               }
	            	  else {
	            		  BS();
	            	  }
	             }  
	             else if (option==6)
	             {
	            	 if (pCount<=0) { //pCount is no. of product records created
	                 	System.out.println("\nNo available product to display.");
	              }
	            	 else {
	            		 
	                	 System.out.println("\nEnter product's id number: ");
	                        pId = input.next();
	                 	i=productSearch(pId);
	                 	if (p[i].quantityAvailable>0) // product is not found
	                 	{
	                 		System.out.println("Product has quantity");
	                 	}
	                 	else if(p[i].quantityAvailable<=0) {
	                 		// AddquantProcess(i);
	                 	}
	            	     		} 
	            		 
		   
	            		 }
	             else if (option==7)
	             {
	            	 if (pCount<=0) { //pCount is no. of product records created
	                 	System.out.println("\nNo available product to display.");
	              }
	            	 else {
	            		 double Psales=0;
	            		 System.out.println("Displaying a total of "+pCount+" Products."); 
	                     for(i = 0 ; i<pCount ; i++) {
	                         p[i].displayProductDetails();
	                         Psales+=p[i].sales;
	                     }
	                     System.out.println("\nAll Products total sales "+d.format(Psales)); 
	                	} 
	            		 
		   
	            		 }
	             
	             else
	                System.out.println("\nThank you for using the program");
	             
	        } while (option<=7);
	        
	    }    
}
