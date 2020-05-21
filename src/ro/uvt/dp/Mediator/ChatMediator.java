package ro.uvt.dp.Mediator;

import ro.uvt.dp.client.Client;

public interface ChatMediator {

    public void sendMessageToAllClients(String msg);

    public void addClientToTheChat(Client client);
    
    public void removeClientFromTheChat(Client client);
    
    public void sendMessageToClient(String msg, Client client);
    
    public void sendMessageToTheBank(String msg);

}
