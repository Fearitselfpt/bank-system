package ro.uvt.dp.Mediator;

import java.util.ArrayList;
import java.util.List;
import ro.uvt.dp.bank.Bank;
import ro.uvt.dp.client.Client;

public class ChatMediatorImpl implements ChatMediator {

    private List<Client> clients;
    private Bank bank;

    public ChatMediatorImpl(Bank bank) {
        this.bank = bank;
        this.clients = new ArrayList<>();
    }

    @Override
    public void addClientToTheChat(Client client) {
        this.clients.add(client);
    }

    @Override
    public void removeClientFromTheChat(Client client) {
        this.clients.remove(client);
    }

    @Override
    public void sendMessageToAllClients(String msg) {
        this.clients.forEach((c) -> {
            c.receive(msg);
        });
    }

    @Override
    public void sendMessageToClient(String msg, Client client) {
        client.receive(msg);
    }

    @Override
    public void sendMessageToTheBank(String msg) {
        this.bank.receive(msg);
    }

}
