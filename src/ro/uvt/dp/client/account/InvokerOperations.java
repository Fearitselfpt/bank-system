package ro.uvt.dp.client.account;

import java.util.ArrayList;
import java.util.List;

public class InvokerOperations {
	private List <OperationsCommand> operationList = new ArrayList<>();
	
	public void addOperation(OperationsCommand op) {
		operationList.add(op);
	}
	
	public void doOperations() throws Exception {
		for(OperationsCommand op : operationList) {
			op.execute();
		}
		
	}
}
