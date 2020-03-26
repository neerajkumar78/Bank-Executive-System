package Bank;
import java.sql.DriverManager;

import javaee.Email;

import java.sql.Connection;
public class BranchName extends Bank{
    public static void main(String args[]) throws Exception{
     // Class.forName("com.mysql.cj.jdbc.Driver");
      Connection con=DriverManager.getConnection("jdbc:mysql://localhost/Bank","root","");
      int ch;
      do{System.out.println("enter 1.create account\n2.Deposite\n3.Withdraw\n4.Fund Transfer\n5.Check Balance\n6.Deactivate Account\n0.exit\n");
      ch=Put.scan1.nextInt();
      Bank obj=new Bank();
      String account;
      int balance;
      int amount;
      String name;
      String email;
      String mob;
      String adhar;
      int status;
      int choice;
      int minBalance;
      String branch = null;
      switch(ch) {
      case 1:
    	  do {
    	  System.out.println("Choose Bank 1.SBI 2.HDFC");
    	  choice=Put.scan1.nextInt();
    	  if(choice==1) {
    		  branch="SBI";
    	  }
    	  else if(choice==2) {
    		  branch="HDFC";
    	  }
    	  }while(choice!=1&&choice!=2);
    	  System.out.println("enter your name");
    	  name=Put.scan2.nextLine().trim();
    	  System.out.println("enter your email");
    	  email=Put.scan2.nextLine().trim().toLowerCase();
    	  while(!EmailVerifier.isValid(email)) {
    		System.out.println("enter your valid email");
    		 email=Put.scan2.nextLine().trim().toLowerCase();
    		}
    	  System.out.println("enter your mobile number");
    	  mob=Put.scan2.nextLine().trim();
    	  while(mob.length()<10||mob.length()>10) {
    		  System.out.println("enter valid mobile number");
        	  mob=Put.scan2.nextLine().trim();
    	  }
    	  System.out.println("enter your adhar No");
    	  adhar=Put.scan2.nextLine().trim();
    	  while(adhar.length()<12||adhar.length()>12) {
    		  System.out.println("enter valid adhar No");
        	  adhar=Put.scan2.nextLine().trim();
    	  }
    	  if(choice==1)
    	      {System.out.println("enter initial ammount of min Rs:1000 to open acc:Rs");
    	      minBalance=1000;
    	      }
    	  else
    		  {System.out.println("enter initial ammount of min Rs:2000 to open acc:Rs"); 
    		  minBalance=2000;
    		  }
          amount=Put.scan1.nextInt();
          while(amount<minBalance) {
              System.out.println("enter sufficient ammount");
              amount = Put.scan1.nextInt();
          }
    	   obj=new Bank(con,branch,name,email,mob,amount,adhar);
    	   if(obj.mailConfig(args[0],args[1])) {
    		   //email send
    	   }
    	   else {
    		   //email not send
    	   }
    	   break;
      case 2:
    	  System.out.println("enter your account number");
    	  account=Put.scan2.nextLine().trim();
    	  balance=obj.getBalance(con, account);
    	  if(balance<0) {
    		  System.out.println("your account is deactivated");
    		  break;
    	  }
          System.out.println("enter ammount to deposite:Rs");
          boolean flag=false;
          amount=Put.scan1.nextInt();
          if(amount<=balance) {
        	  flag=obj.deposite(con, account, amount);
          }
          if(flag==true) {
        	  System.out.println("transaction successfull");
          }
          else {
        	  System.out.println("transaction unsuccessfull");
          }
          break;
      case 3:
    	  System.out.println("enter your account number");
    	  account=Put.scan2.nextLine().trim();
    	  balance=obj.getBalance(con, account);
    	  if(balance<0) {
    		  System.out.println("your account is deactivated");
    		  break;
    	  }
          System.out.println("enter ammount to withdraw:Rs");
          flag=false;
          amount=Put.scan1.nextInt();   
         flag=obj.withdraw(con, account, amount);
         if(flag==true) {
        	  System.out.println("transaction successfull");
          }
          else {
        	  System.out.println("transaction unsuccessfull");
          }
          break;
      case 4:
    	  System.out.println("enter your account number");
    	  account=Put.scan2.nextLine().trim();
    	  System.out.println("enter account number in which you want to transfer the fund");
    	 String account2=Put.scan2.nextLine().trim();
    	 System.out.println("enter ammont to be transfer");
    	amount=Put.scan1.nextInt();
    	balance=obj.getBalance(con, account);
    	if(balance<0) {
  		  System.out.println("your account is deactivated");
  		  break;
  	  }
    	 if(amount<=balance) {
    		 if(obj.fundTransfer(con, account, account2, amount)) {
    			  System.out.println("transaction successfull");
             }
             else {
           	  System.out.println("transaction unsuccessfull");
             }
    	 }
    	 break;
      case 5:
    	  System.out.println("enter your account number");
    	  account=Put.scan2.nextLine().trim();
    	  balance=obj.getBalance(con, account);
    	  if(balance<0) {
    		  System.out.println("your account is deactivated");
    		  break;
    	  }
    	  System.out.println("Your Balance is:"+balance);
    	  break;
      case 6:
    	  System.out.println("enter your account number");
    	  account=Put.scan2.nextLine().trim();
    	  if(obj.deactivateAccount(con, account)) {
    		  System.out.println("account deactivated successfully");
    	  }
    	  else {
    		  System.out.println("getting trouble in order to deactivate your account");
    	  }
      }
      }while(ch!=0);
      con.close();
      System.out.println("Thank You!");
    }
}


