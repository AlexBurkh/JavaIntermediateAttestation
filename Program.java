import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

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

    static final String[] mainMenu = {"1. Сыграть", "2. Забрать выигранную игрушку", "3. Добавить игрушку", "4. Сменить весовой коэффициент", "5. Выйти"};

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
        _view.println("Игрушки в розыгрыше:");
        _view.print(_tm.getPlayable());
        _view.println("Выигранные игрушки, которые можно забрать:");
        _view.print(_tm.getWon());
        _view.print(mainMenu);
        int userInput = _view.readInt();
        switch(userInput) {
            case 1:
            {
                gameHandler();
                break;
            }
            case 2:
            {
                getRewardHandler();
                break;
            }
                
            case 3:
            {
                addToyHandler();
                break;
            }
            case 4:
            {
                break;
            }
            case 5:
            {
                exit();
                return false;
            }
            default:
            {
                _logger.WARNING("некорректный ввод");
                break;
            }  
        }
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
            _logger.INFO("выдана игрушка " + toy.getName());
        }
    }

    private static void gameHandler() {
        if (! _tm.getPlayable().isEmpty()) {
            _tm.play();
        }
        else {
            _logger.ERROR("невозможно сыграть, список игрушек пуст");
        }
    }

    private static void saveToyMachine() {

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("playable.dat")))
        {
            oos.writeObject(_tm.getPlayable());
        }
        catch(Exception ex){
             
            _logger.CRYTICAL(ex.getMessage());
        } 
    }
    
    private static void loadToyMachine() {
        List<Toy> playable = new ArrayList<>();
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("playable.dat")))
        {
            Object obj = ois.readObject();
            playable = (ArrayList<Toy>)obj;
            
        }
        catch(Exception ex){
            _logger.CRYTICAL(ex.getMessage());
        }
        _tm = new ToyMachine(new ConsoleLogger(), playable, new ArrayList<Toy>());
    }

    private static void init() {
        _logger = new ConsoleLogger();
        _view = new ConsoleView();
        loadToyMachine();
    }

    private static void exit() {
        saveToyMachine();
        _view.println("До свидания!");
    }
}
