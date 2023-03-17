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
        run();
    }

    private static void run() {
        init();
        
        
    }

    private static void init() {
        _logger = new ConsoleLogger();
        _view = new ConsoleView();
        _tm = new ToyMachine(_logger);
    }
}
