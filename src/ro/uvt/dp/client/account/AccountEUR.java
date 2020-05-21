package ro.uvt.dp.client.account;

import ro.uvt.dp.test.ReadWrite;

/**
 * 
 * Euro account type
 *
 */
public class AccountEUR extends Account implements Transfer {

	public AccountEUR(double amount) throws Exception {
		super(amount);
		setIBAN("EUR");
	}

	public double getInterest() {
		return 0.01;

	}
	
	@Override
	public void transfer(Account c, double s) throws Exception{
		String str = ""; 
		str = str + "Transfered " + s + " from " + getAccountNumber() + " to " + c.getAccountNumber() + "\n";
		ReadWrite.writeToFile(str);
		super.activity.add(str);
		c.retrieve(s);
		deposit(s);
	}
	
	@Override
	public String toString() {
		return "Account EUR " + super.toString();
	}
}
