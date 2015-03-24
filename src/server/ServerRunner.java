/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Josep Cañellas <jcanell4@ioc.cat>
 */
public class ServerRunner {
    public final static int PORT=9995;

    /**
     * @param args the command line arguments
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {
        ContinuousTempServer server = new ContinuousTempServer();
        try {
            server.init("226.0.0.1", PORT);
            server.runServer();            
        } catch (SocketException ex) {
            Logger.getLogger(ServerRunner.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ServerRunner.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            server.close();
        }
    }
}
