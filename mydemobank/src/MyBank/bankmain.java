package MyBank;
import java.util.*;

public class bankmain {
	public static void main(String []args) {
		
		int  choice;
		Scanner sc=new Scanner (System.in);
		
		do {
		System.out.println("Enter your choice");
		
		System.out.println("0 Exit\n1 Add Customer\n2 Credit Money\n3 Debit Money\n4 Check Balannce\n5Delete Account");
		choice=sc.nextInt();
		
		bankproject bp=new bankproject();
		
		{
			switch(choice){
			case 1:
				bp.addCustomer();
				break;
			case 2:
				bp.creditMoney();
				break;
			case 3:
				bp.debitMoney();
				break;
			case 4:
				bp.checkBalance();
				break;
			case 5:
				bp.deleteCustomer();
			}
		
			
		}
			
		}
		while(choice!=0);
		
	}

}
