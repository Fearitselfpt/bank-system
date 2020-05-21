package ro.uvt.dp.bank;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
import ro.uvt.dp.Mediator.ChatMediator;
import ro.uvt.dp.Mediator.ChatMediatorImpl;

import ro.uvt.dp.client.Client;
import ro.uvt.dp.test.ReadWrite;

/**
 *
 * Class used to store the information about the bank clients and operations
 *
 */
public class Bank {

    //private final static int MAX_CLIENTS_NUMBER = 100;
    //private Client clients[];
    //private int clientsNumber;
    private String bankCode = null;
    private List<Client> clients;
    private ChatMediator mediator;

    public Bank(String codBanca) {
        this.bankCode = codBanca;
        //clients = new Client[MAX_CLIENTS_NUMBER];
        clients = new ArrayList<>();
        this.mediator = new ChatMediatorImpl(this);
    }

    public String getBankCode() {
        return bankCode;
    }

    public void addClient(Client c) {
        //clients[clientsNumber++] = c;
        clients.add(c);
        c.setMediator(this.mediator);
        this.mediator.addClientToTheChat(c);
        String s = "";
        s += "Added client " + c.getName() + " to bank " + bankCode + "\n";
        ReadWrite.writeToFile(s);
    }

    public List<Client> getClients() {
        return clients;
    }

    public Client getClient(String nume) {
        /*for (int i = 0; i < clientsNumber; i++) {
			if (clients[i].getName().equals(nume)) {
				return clients[i];
			}
		}*/
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getName().equalsIgnoreCase(nume)) {
                return clients.get(i);
            }
        }
        return null;
    }

    public void closeClientAccount(String name, String iban) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getName().equalsIgnoreCase(name)) {
                String s = "";
                s += "Removed account " + iban + " from client " + name + " from bank " + bankCode + "\n";
                ReadWrite.writeToFile(s);
                clients.get(i).removeAccount(iban);
                return;
            }
        }
    }

    public void sendMessageToAllClients(String msg) {
        if (this.mediator != null) {
            System.out.println("Bank " + this.bankCode + ": Sending Message=" + msg);
            this.mediator.sendMessageToAllClients(msg);
        }
    }

    public void sendMessageToClient(String msg, Client client) {
        if (this.mediator != null) {
            System.out.println("Bank " + this.bankCode + ": Sending Message=" + msg);
            this.mediator.sendMessageToClient(msg, client);
        }
    }

    public void receive(String msg) {
        System.out.println("Bank " + this.bankCode + ": Received Message:" + msg);
    }

    @Override
    public String toString() {
        //return "Bank [code=" + bankCode + ", clients=" + Arrays.toString(clients) + "]";
        StringBuilder sb = new StringBuilder();
        sb.append("\nBANK [code = ").append(bankCode).append("]\n Bank Clients: ");
        for (int i = 0; i < clients.size(); i++) {
            sb.append(clients.get(i).toString());
        }
        sb.append("\n");

        return sb.toString();
    }

}
