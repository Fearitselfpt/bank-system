package ro.uvt.dp.client.account;

public abstract class OperationsCommand {
	protected Account acc;
	
	public OperationsCommand(Account acc) {
		this.acc = acc;
	}
	
	abstract void execute() throws Exception;
	
}
