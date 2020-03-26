package Bank;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.Random;
class Put{
	public static Scanner scan1=new Scanner(System.in);
	public static Scanner scan2=new Scanner(System.in);
}
class Bank{
    int amount;
    String name;
    String account;
    String email;
    String mob;
    String adhar;
    int status;
    String branch;
    public Bank() {
    	
    }
    public Bank(Connection con,String branch,String name,String email,String mob,int amount,String adhar) throws Exception{
    	this.email=email;
    	this.account=account;
    	this.branch=branch;
    	Random rand=new Random();
    	String num1=String.valueOf(rand.nextInt(999));
    	String num2=String.valueOf(rand.nextInt(999));
    	String num3=String.valueOf(rand.nextInt(999));
    	for(int i=3-num1.length();i>0;i--) {
    		num1="0"+num1;
    	}
    	for(int i=3-num2.length();i>0;i--) {
    		num2="0"+num2;
    	}
    	for(int i=3-num2.length();i>0;i--) {
    		num3="0"+num3;
    	}
    	account=branch+num1+num2+num3;
    	/*if(branch.equals("SBI"))
    	 account="20"+num1+num2+num3;
    	else
    		account="40"+num1+num2+num3;*/
    	PreparedStatement prstmt=con.prepareStatement("INSERT INTO "+this.branch+" VALUES(?,?,?,?,?,?,?)");
    	prstmt.setString(1,account);
    	prstmt.setString(2,name);
    	prstmt.setString(3,email);
    	prstmt.setString(4,mob);
    	prstmt.setInt(5,amount);
    	prstmt.setString(6,adhar);
    	prstmt.setInt(7,1);
    	prstmt.executeUpdate();
    	System.out.println("account open successfully and your account number is: "+account);
	}
    protected boolean mailConfig(String bankEmailId,String pwd) throws Exception{
    	EmailSender.setMailConfig(bankEmailId,pwd);
    	return EmailSender.sendMail(email,account);
    		
    }
    protected final boolean deposite(Connection con,String account,int amo)throws Exception{
    	PreparedStatement prstmt;
    	branch=""+account.charAt(0)+account.charAt(1)+account.charAt(2);
    	prstmt=con.prepareStatement("SELECT amount,status FROM "+branch+" WHERE account=?");
    	prstmt.setString(1, account);
    	ResultSet rs=prstmt.executeQuery();
    	while(rs.next()) {
    	amount=rs.getInt("amount");
    	status=rs.getInt("status");
    	}
    	if(status==0) {
    		return false;
    	}
        amount=amount+amo;
        prstmt=con.prepareStatement("UPDATE "+branch+" SET amount=? WHERE account=?");
        prstmt.setInt(1, amount);
        prstmt.setString(2, account);
        prstmt.executeUpdate();
        return true;
       
    }
    protected final boolean withdraw(Connection con,String account,int amo) throws Exception{
    	PreparedStatement prstmt;
    	branch=""+account.charAt(0)+account.charAt(1)+account.charAt(2);
    	prstmt=con.prepareStatement("SELECT amount,status FROM "+branch+" WHERE account=?");
    	prstmt.setString(1, account);
    	ResultSet rs=prstmt.executeQuery();
    	while(rs.next()) {
    	amount=rs.getInt("amount");
    	status=rs.getInt("status");
    	}
    	if(status==0) {
    		return false;
    	}
        amount=amount-amo;
        prstmt=con.prepareStatement("UPDATE "+branch+" SET amount=? WHERE account=?");
        prstmt.setInt(1, amount);
        prstmt.setString(2, account);
        prstmt.executeUpdate();
        return true;
    }
    protected final boolean fundTransfer(Connection con,String account,String account2,int amo) throws Exception{
    	PreparedStatement prstmt;
    	int amount2=0;
    	String branch1="",branch2="";
    	branch1=branch1+account.charAt(0)+account.charAt(1)+account.charAt(2);
    	prstmt=con.prepareStatement("SELECT amount,status FROM "+"'"+branch1+"'"+" WHERE account=?");
    	prstmt.setString(1, account);
    	ResultSet rs=prstmt.executeQuery();
    	while(rs.next()) {
    	amount=rs.getInt("amount");
    	status=rs.getInt("status");
    	}
    	if(status==0) {
    		return false;
    	}
        amount=amount-amo;
        branch2=branch2+account2.charAt(0)+account2.charAt(1)+account2.charAt(2);
        prstmt=con.prepareStatement("SELECT amount,status FROM "+branch2+" WHERE account=?");
    	prstmt.setString(1, account2);
    	rs=prstmt.executeQuery();
    	while(rs.next()) {
    	amount2=rs.getInt("amount");
    	status=rs.getInt("status");
    	}
    	if(status==0) {
    		return false;
    	}
        amount2=amount2+amo;
        prstmt=con.prepareStatement("UPDATE "+branch1+" SET amount=? WHERE account=?");
        prstmt.setInt(1, amount);
        prstmt.setString(2, account);
        prstmt.executeUpdate();
        prstmt=con.prepareStatement("UPDATE "+branch2+" SET amount=? WHERE account=?");
        prstmt.setInt(1, amount2);
        prstmt.setString(2, account2);
        prstmt.executeUpdate();
        return true;
    }
    protected final int getBalance(Connection con,String account) throws Exception{
    	branch=""+account.charAt(0)+account.charAt(1)+account.charAt(2);
    	PreparedStatement prstmt=con.prepareStatement("SELECT amount,status FROM "+branch+" WHERE account=?");
    	prstmt.setString(1, account);
    	ResultSet rs=prstmt.executeQuery();
    	while(rs.next()) {
    	amount=rs.getInt("amount");
    	status=rs.getInt("status");
    	}
    	if(status==0) {
    		return -1;
    	}
    	return amount;
    }
    protected final boolean deactivateAccount(Connection con,String account) throws Exception{
    	PreparedStatement prstmt;
    	branch=""+account.charAt(0)+account.charAt(1)+account.charAt(2);
    	prstmt=con.prepareStatement("UPDATE "+branch+" SET status=? WHERE account=?");
         prstmt.setInt(1, 0);
         prstmt.setString(2, account);
         prstmt.executeUpdate();
         return true;
    } 
}
