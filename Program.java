import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lib.dataModels.Toy;
import lib.dataModels.ToyMachine;
import lib.support.Logger;
import lib.support.ConsoleLogger;
import lib.ui.View;
import lib.ui.ConsoleView;

class Program {
    static Logger _logger;
    static View _view;
    static ToyMachine _tm;

    public static void main(String[] args) {
        init();
        run();
    }

    private static void run() {
        
        

    }

    private static void saveToyMachine() {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tm.dat")))
        {
            oos.writeObject(_tm);
        }
        catch(Exception ex){
             
            _logger.CRYTICAL(ex.getMessage());
        } 
    }
    
    private static void loadToyMachine() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("tm.dat")))
        {
            _tm =(ToyMachine)ois.readObject();
        }
        catch(Exception ex){
             
            System.out.println(ex.getMessage());
        }
    }

    private static void init() {
        _logger = new ConsoleLogger();
        _view = new ConsoleView();
        File f = new File("tm.dat");
        if (f.exists()) {
            loadToyMachine();
        }
        _tm = new ToyMachine(_logger);
    }

    private static void exit() {
        saveToyMachine();
    }
}
