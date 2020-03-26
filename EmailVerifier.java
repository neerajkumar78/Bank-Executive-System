package Bank;
import java.util.Scanner;
public class EmailVerifier 
{
private static boolean isValidLocalPart(String email) 
{
	int index=email.lastIndexOf("@");
	if(index>0) 
	{
		if(email.lastIndexOf("..",index-1)>0||(email.indexOf("\"",1)>0&&email.indexOf("\"",1)<index-1)||email.lastIndexOf(" ",index-1)>0||email.lastIndexOf("(",index-1)>0||email.lastIndexOf(")",index-1)>0||email.lastIndexOf(",",index-1)>0||email.lastIndexOf(":",index-1)>0||email.lastIndexOf(";",index-1)>0||email.lastIndexOf("<",index-1)>0||email.lastIndexOf(">",index-1)>0||email.lastIndexOf("@",index-1)>0||email.lastIndexOf("[",index-1)>0||email.lastIndexOf("\\",index-1)>0||email.lastIndexOf("]",index-1)>0)
		{
		if(!(email.charAt(0)=='\"'&&email.charAt(index-1)=='\"'&&(index-1!=0))) 
		{
			
				//System.out.println("must be double quotes");
				return false;
			}
		}
		if(email.charAt(0)=='"') 
		{
			if(email.charAt(index-1)=='"') 
			{
				for(int i=1;i<index-1;i++) 
				{
					int ch=(int)email.charAt(i);
					if(ch<=32||ch==127) {
						//System.out.println((char)ch+" not allowed");
						return false;
					}
				}
			}
			else 
			{
				//System.out.println("closing of double character should be there");
				return false;
			}
		}
		else 
		{
			for(int i=1;i<index;i++) 
			{
				int ch=(int)email.charAt(i);
				if(ch>=33&&ch<=39||ch==42||ch==43||ch>=45&&ch<=57||ch==61||ch==63||ch>=65&&ch<=90||ch>=94&&ch<=126) 
				{
					 // 42=* 43=+ 45=- 46=. 47=/  61== 63=?  94=^    126=~ 
				}
				else 
				{
					//System.out.println((char)ch+" not allowed");
					return false;
				}
			}
		}
	}
	else 
	{
		return false;
	}
	return true;
}
private static boolean isValidDomain(String email) 
{
	int index=email.lastIndexOf("@");
	int id=email.indexOf(".com");
	if(index+1==id) {
		return false;
	}
	for(int i=index+1;i<id;i++)
	{	
	int ch=(int)email.charAt(i);
		if(i==index+1&&ch==(int)'-') 
		{
			return false;
		}
		else if(ch>=48&&ch<=57||ch>=65&&ch<=90||ch>=97&&ch<=122)
		{
	}
		else {
			return false;
		}
	}
	return true;
}
//private static boolean isValidIp(String email) {
//	
//}
public static boolean isValid(String email)
{
	boolean validity=true;
	if(email.endsWith(".com"))
	{
		if(email.indexOf(".com")==email.lastIndexOf(".com")) 
		{	
			validity=isValidLocalPart(email)&&isValidDomain(email);
		}
		else {
			validity=false;
		}
	}
	else if(email.endsWith(".co.in"))
	{
        if(email.indexOf(".co.in")==email.lastIndexOf(".co.in")) 
        {
        	validity=isValidLocalPart(email)&&isValidDomain(email);
		}
	}
//	else if(email.startsWith("[",email.lastIndexOf("@")+1)&&email.endsWith("]")) 
//	{
//		validity=isValidLocalPart(email)&&isValidIp(email);
//	}
	else 
	{
		validity=false;
	}
	
	return validity;
}
}
