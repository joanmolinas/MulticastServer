/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

/**
 *
 * @author Josep Cañellas <jcanell4@ioc.cat>
 */
public class TemperatureClient {
    ConsoleInterface userInterface = new ConsoleInterface();
    boolean continueRunning = true;
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    
    public void init(String strIp, int portValue) throws SocketException, 
                                                    IOException{
        multicastIP = InetAddress.getByName(strIp);
        port = portValue;
        socket = new MulticastSocket(port);
    }
    
    public void runClient() throws IOException{
        DatagramPacket packet;
        byte [] receivedData = new byte[1024];
        
        
        //activem la subscripció
        socket.joinGroup(multicastIP);
        
        //El client aten el port fins que decideix finalitzar.
        while(continueRunning){
            //creació del paquet per rebre les dades
            packet = new DatagramPacket(receivedData, 1024);
            //Espera de les dades. Màxim 5 segons
            socket.setSoTimeout(5000);
            try{
                //Espera de les dades.
                socket.receive(packet);
                //procesament de les dades rebudes i obtenció de la resposta
                continueRunning = getData(packet.getData());
            }catch(SocketTimeoutException e){
                //S'ha excedit el temps d'espera i cal saber que s'ha de fer
                userInterface.showMessage("S'ha perdut la connexió amb el servidor.");
                continueRunning = timeoutExceeded();
            }
        }

        //Es cancel·la la subscripció
        socket.leaveGroup(multicastIP);
        close();
    }
    
    
    public void close(){
        if(socket!=null && !socket.isClosed()){
            socket.close();
        }
    }
    
    protected  boolean getData(byte[] data) {
        boolean ret = true;
        int temp;
        try {
            temp = getInt(data);
            userInterface.showMessage("La temperatura actual és: " + temp +"º");
        } catch (IOException ex) {
            userInterface.showError("El format de les dades rebudes no es correcte", ex);
        }
        if(userInterface.isKeyReady()){
            ret = userInterface.read()!='s';
        }
        return ret;
    }

    protected boolean timeoutExceeded(){
        return false;
    }

    private void processError(IOException ex) {
        userInterface.showError(ex);
    }
       
    private int getInt(byte[] data) throws IOException{
        int ret;
        DataInputStream dataIn = new DataInputStream(
                            new ByteArrayInputStream(data));
        ret = dataIn.readInt();
        try {
            dataIn.close();
        } catch (IOException ex) {System.out.println("Error");}
        return ret;
    }
}
