package ro.uvt.dp.client.account;

import ro.uvt.dp.test.ReadWrite;

public class AccountRON extends Account implements Transfer {

	public AccountRON(double amount) throws Exception{
		super(amount);
		setIBAN("RON");
	}

	public double getInterest() {
		if (amount < 500)
			return 0.03;
		else
			return 0.08;

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
		return "Account RON " + super.toString();
	}
}
