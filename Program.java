import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lib.dataModels.ToyMachine;
import lib.support.Logger;
import lib.support.ConsoleLogger;
import lib.ui.View;
import lib.ui.ConsoleView;

class Program {
    static Logger _logger;
    static View _view;
    static ToyMachine _tm;

    static final String[] mainMenu = {"1. Сыграть", "2. Забрать выигранную игрушку", "3. Добавить игрушку", "4. Выйти"};

    public static void main(String[] args) {
        init();
        run();
    }

    private static void run() {
        boolean status = true;
        _view.println("Добро пожаловать");

        while(status) {
            status = mainMenuHandler();
        }
    }

    private static boolean mainMenuHandler() {
        _view.println(_tm.toString());
        _view.print(mainMenu);
        int userInput = _view.readInt();
        if (userInput == 1) {
            gameHandler();
        }
        if (userInput == 2) {
            getRewardHandler();
        }
        if (userInput == 3) {
            addToyHandler();
        }
        if (userInput == 4) {
            exit();
            return false;
        }
        _view.println("Некорректный ввод. Повторите попытку");
        _logger.WARNING("некорректный ввод");
        return true;
    }

    private static void addToyHandler() {
        String name = _view.readText("Введите имя игрушки");
        double winChance = _view.readDouble("Введите вероятность выигрыша игрушки");
        int amount = _view.readInt("Введите количество игрушек");

        _tm.addToy(name, winChance, amount);
    }

    private static void getRewardHandler() {
        var toy = _tm.getReward();
        if (toy != null) {
            _logger.INFO("выдана игрушка " + toy.getProductName());
        }
    }

    private static void gameHandler() {
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
