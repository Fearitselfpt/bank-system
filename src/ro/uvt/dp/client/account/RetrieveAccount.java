package ro.uvt.dp.client.account;

public class RetrieveAccount extends OperationsCommand{
	private int amount;
	
	public RetrieveAccount(Account acc, int amount) {
		super(acc);
		this.amount = amount;
	}
	
	
	public void execute() throws Exception {
		acc.retrieve(amount);
	}
}
