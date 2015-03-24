package server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
/**
 *
 * @author Josep Cañellas <jcanell4@ioc.cat>
 */
public class ContinuousTempServer {
    ConsoleInterface console = new ConsoleInterface();
    MulticastSocket socket;
    InetAddress multicastIP;
    int port;
    boolean continueRunning = true;
    TemperatureSimulator simulator;
    
    public void init(String strIp, int portValue) throws SocketException, 
                                                            IOException, InterruptedException{
        socket = new MulticastSocket();
        multicastIP = InetAddress.getByName(strIp);
        simulator = new TemperatureSimulator(5);
        port = portValue;
    }
    
    public void runServer() throws IOException  {
    	System.out.println("Iniciant servidor...");
    	do {
	    	int temp = simulator.getTemperature(); 
	    	
	    	DatagramPacket packet = new DatagramPacket(intToByteArray(temp), 4, multicastIP, port);
	    	socket.send(packet);
	    	
	    	try {
	    		System.out.println("sleep");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	
	    	if (console.isKeyReady()) {
	    		if (console.read() == 's') {
					continueRunning = !continueRunning;
				}
			}
    	}while(continueRunning);
    	close();
    }
    
    public void close(){
    	if (socket != null) {
			socket.close();
		}
        socket.close();
    } 
    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }
}
