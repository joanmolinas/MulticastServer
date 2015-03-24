package server;
import java.io.IOException;

/**
 *
 * @author Josep Cañellas <jcanell4@ioc.cat>
 */
public class ClientRunner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            TemperatureClient prg = new TemperatureClient();
            prg.init("226.0.0.1", ServerRunner.PORT);
            prg.runClient();
        } catch (IOException ex) {
            System.out.println("Error rebent o enviant");
        }
    }
}
