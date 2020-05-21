package ro.uvt.dp.client.account;

public class DepositAccount extends OperationsCommand{
	private int amount;
	
	public DepositAccount(Account acc, int amount) {
		super(acc);
		this.amount = amount;
	}
	
	
        @Override
	public void execute() throws Exception {
		acc.deposit(amount);
	}
}
