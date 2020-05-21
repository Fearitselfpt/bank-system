package ro.uvt.dp.client;

import java.util.ArrayList;
import java.util.List;
import ro.uvt.dp.Mediator.ChatMediator;

import ro.uvt.dp.client.account.Account;
import ro.uvt.dp.client.account.AccountFactory;
import ro.uvt.dp.test.ReadWrite;
import ro.uvt.dp.client.account.Account.TYPE;

public class Client {

    public static final int NUMAR_MAX_CONTURI = 5;

    private String name;
    private String address;
    private String bdate;
    //private Account accounts[];
    //private int accountsNr = 0;
    private List<Account> accounts;
    private ChatMediator mediator;

    public Client(ClientBuilder builder) throws Exception {
        this.name = builder.name;
        this.address = builder.address;
        this.bdate = builder.bdate;
        //accounts = new Account[NUMAR_MAX_CONTURI];
        accounts = builder.accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public static class ClientBuilder {

        private String name;
        private String address;
        private String bdate;
        private List<Account> accounts; //just need one account, no need to have the list here

        public ClientBuilder(String nume, TYPE tip, double suma) throws Exception {
            this.name = nume;
            //accounts = new Account[NUMAR_MAX_CONTURI];
            accounts = new ArrayList<>();
            addAccount(tip, suma);
        }

        public ClientBuilder setName(String nume) {
            this.name = nume;
            return this;
        }

        public ClientBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public ClientBuilder setBdate(String date) {
            bdate = date;
            return this;
        }

        public ClientBuilder addAccount(TYPE tip, double suma) throws Exception {
            /*
			Account c = null;
			
			if (tip == Account.TYPE.EUR) {
				String s = "";
				s += "Added account of type " + Account.TYPE.EUR.toString() + " to client " + name + "\n";
			    ReadWrite.writeToFile(s);
				c = new AccountEUR(suma);
			}
			else if (tip == Account.TYPE.RON) {
				String s = "";
				s += "Added account of type " + Account.TYPE.RON.toString() + " to client " + name + "\n";
			    ReadWrite.writeToFile(s);
				c = new AccountRON(suma);
				//accounts[accountsNr++] = c;
			}
             */

            Account c = AccountFactory.createAccount(tip, suma);

            String s = "";
            s += "Added account of type " + tip.toString() + " to client " + name + "\n";
            ReadWrite.writeToFile(s);

            accounts.add(c);

            return this;
        }

        public Client build() throws Exception {
            return new Client(this);
        }

    }

    public void addAccount(TYPE tip, double suma) throws Exception {
        /*
		Account c = null;
	
		if (tip == Account.TYPE.EUR) {
			String s = "";
			s += "Added account of type " + Account.TYPE.EUR.toString() + " to client " + name + "\n";
		    ReadWrite.writeToFile(s);
			c = new AccountEUR(suma);
		}
		else if (tip == Account.TYPE.RON) {
			String s = "";
			s += "Added account of type " + Account.TYPE.RON.toString() + " to client " + name + "\n";
		    ReadWrite.writeToFile(s);
			c = new AccountRON(suma);
			//accounts[accountsNr++] = c;
		}*/

        Account c = AccountFactory.createAccount(tip, suma);

        String s = "";
        s += "Added account of type " + tip.toString() + " to client " + name + "\n";
        ReadWrite.writeToFile(s);

        accounts.add(c);
    }

    public void removeAccount(String IBAN) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equalsIgnoreCase(IBAN)) {
                accounts.remove(accounts.get(i));
                return;
            }
        }
    }

    public String getName() {
        return name;
    }

    public Account getAccount(String accountCode) {
        /*for (int i = 0; i < accountsNr; i++) {
			if (accounts[i].getAccountNumber().equals(accountCode)) {
				return accounts[i];
			}
		}*/

        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equalsIgnoreCase(accountCode)) {
                return accounts.get(i);
            }
        }

        return null;
    }

    public void setMediator(ChatMediator mediator) {
        this.mediator = mediator;
    }

    public void send(String msg) {
        if (this.mediator != null) {
            System.out.println(this.name + ": Sending Message=" + msg);
            this.mediator.sendMessageToTheBank(msg);
        }
    }

    public void receive(String msg) {
        System.out.println(this.name + ": Received Message:" + msg);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nClient Name = ").append(name).append(", address = ").append(address).append(", Birth Date = ").append(bdate).append("\n|Accounts|\n");
        for (int i = 0; i < accounts.size(); i++) {
            sb.append(accounts.get(i).toString());
        }
        return sb.toString();
    }
}
