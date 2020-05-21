package ro.uvt.dp.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;
import ro.uvt.dp.client.account.Account;
import ro.uvt.dp.client.account.AccountRON;
import ro.uvt.dp.client.account.DepositAccount;
import ro.uvt.dp.client.account.InvokerOperations;
import ro.uvt.dp.client.account.RetrieveAccount;



public class Test {
	
	public static void main(String[] args) {
			
		try {
		    Files.delete(Paths.get("loggs.txt")); 
		    System.err.format("%s not empty%n", Paths.get("loggs.txt"));
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
		
		try {
			/**
			 * Create BCR bank with 2 clients
			 */
			Bank bcr = new Bank("Banca BCR");
			// creates Ionescu client that has two accounts one in EUR and one in
			// RON
			//Client cl1 = new Client("Ionescu Ion", "Timisoara", Account.TYPE.EUR, "EUR124", 200.9);
			Client cl1 = new Client.ClientBuilder("Ionescu Ion", Account.TYPE.EUR, 200.9).setAddress("Timisoara").build();
			bcr.addClient(cl1); 
			cl1.addAccount(Account.TYPE.RON, 400);
			// Creates Marinescu client that has only one account in RON
			//Client cl2 = new Client("Marinescu Marin", "Timisoara", Account.TYPE.RON, "RON126", 100);
			Client cl2 = new Client.ClientBuilder("Marinescu Marin", Account.TYPE.RON, 100).setAddress("Timisoara").build();
			bcr.addClient(cl2);
			System.out.println(bcr);
                        
                        bcr.sendMessageToAllClients("We are thinking about closing the bank.");
                        cl2.send("And my money? I want it back");
                        bcr.sendMessageToClient("We will keep that money with us! Thank you for that!", cl2);

			/**
			 * Create bank CEC with one client
			 */
			Bank cec = new Bank("Banca CEC");
			//Client clientCEC = new Client("Vasilescu Vasile", "Brasov", Account.TYPE.EUR, "EUR128", 700);
			Client clientCEC = new Client.ClientBuilder("Vasilescu Vasile", Account.TYPE.EUR, 700).setAddress("Brasov").build();
			cec.addClient(clientCEC);
			System.out.println(cec);

			// depose in account RON126 of client Marinescu
			Client cl = bcr.getClient("Marinescu Marin");
			
			
			InvokerOperations invOp = new InvokerOperations();
			
			if (cl != null) {
				//cl.getAccount("RON126").depose(400);
				//cl.getAccount("RON3").deposit(400);
				DepositAccount depAcc = new DepositAccount(cl.getAccount("RON3"), 400);
								
				invOp.addOperation(depAcc);
				invOp.doOperations();
				System.out.println(cl);
			}

			// retrieve from account RON126 of Marinescu client
			if (cl != null) {
				//cl.getAccount("RON126").retrieve(67);
				//cl.getAccount("RON3").retrieve(67);
				RetrieveAccount retAcc = new RetrieveAccount(cl.getAccount("RON3"), 67);
				
				invOp.addOperation(retAcc);
				invOp.doOperations();
				System.out.println(cl);
			}

			// transfer between accounts RON126 and RON1234
			//AccountRON c1 = (AccountRON) cl.getAccount("RON126");
			AccountRON c1 = (AccountRON) cl.getAccount("RON3");
			//AccountRON c2 = (AccountRON) bcr.getClient("Ionescu Ion").getAccount("RON1234");
			AccountRON c2 = (AccountRON) bcr.getClient("Ionescu Ion").getAccount("RON2");
			c1.transfer(c2, 40);
			System.out.println(bcr);
			
			bcr.closeClientAccount("Ionescu Ion", "RON2");
			System.out.println(bcr);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
