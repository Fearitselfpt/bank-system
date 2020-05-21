package ro.uvt.dp.client.account;

import java.util.ArrayList;
import java.util.List;

import ro.uvt.dp.test.ReadWrite;

public abstract class Account implements Operations {
	protected static int IBANnumber = 0;
	protected String accountIBAN = null;
	protected double amount = 0;
	protected List <String> activity;

	public static enum TYPE {
		EUR, RON
	};

	protected Account(double amount) throws Exception{
		IBANnumber++;
		accountIBAN = Integer.toString(IBANnumber);
		activity = new ArrayList<>();
		deposit(amount);
                // TODO ADD ACTIVITIES HERE JUST TO DEBUG
	}

    public List<String> getActivity() {
        return activity;
    }
	
	

	@Override
	public double getTotalAmount() {

		return amount + amount * getInterest();
	}

	@Override
	public void deposit(double suma) throws Exception{
		if (suma<=0)
			throw new Exception("The minimum amount to deposit should be superior to zero");
		String s = "";
		s = s + "Account with IBAN: " + accountIBAN + " Deposited" + " " + suma + "\n";
		ReadWrite.writeToFile(s);
		activity.add(s);
		this.amount += suma;
	}

	@Override
	public void retrieve(double suma) throws Exception {
		if (suma<=0)
			throw new Exception("The minimum amount to retrieve should be superior to zero");
		if (suma>this.amount)
			throw new Exception("Not enough funds to withdraw");
		String s = "";
		s = s + "Account with IBAN: " + accountIBAN + " Withdrew" + " " + suma + "\n";
		ReadWrite.writeToFile(s);
		activity.add(s);
		this.amount -= suma;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("code = " + accountIBAN + ", amount = " + amount + "\n[Activity]\n");
		for(int i=0; i<activity.size(); i++) {
			sb.append(activity.get(i).toString());
		}
		return sb.toString();
	}

	public String getAccountNumber() {
		return accountIBAN;
	}
	
	public void setIBAN(String curr) {
		accountIBAN = curr + accountIBAN;
	}

}
