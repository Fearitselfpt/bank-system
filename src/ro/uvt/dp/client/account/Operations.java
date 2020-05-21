package ro.uvt.dp.client.account;

public interface Operations {
	public double getTotalAmount();

	public double getInterest();

	public void deposit(double amount) throws Exception;

	public void retrieve(double amount) throws Exception;
}
