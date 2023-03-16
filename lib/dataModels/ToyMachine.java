package lib.dataModels;

import java.util.ArrayList;
import java.util.List;

import lib.support.Logger;

public class ToyMachine {
    private Logger _logger;
    private int _next_id = 0;
    private List<Double> _PDF;

    private List<Toy> _playable;
    private List<Toy> _won;

    public ToyMachine(Logger _logger, List<Toy> playable, List<Toy> won) {
        this._logger = _logger;
        this._playable = playable;
        this._won = won;
        makePDF();
    }

    public ToyMachine(Logger logger) {
        this(logger, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public String toString() {
        return "ToyMachine { next_id=" + _next_id + ", playable=" + _playable + ", won=" + _won + " }";
    }
    
    /**
     * Составить функцию распределения вероятности
     * @variable _PDF - список для хранения самой дискретной функции распределения вероятности
     * @variable sum - сумма всех вероятностей (для нормализации)
     */
    private void makePDF() {
        this._PDF = new ArrayList<Double>();
        double sum = 0;
        // Вычисление максимума функции распределения вероятности
        for (Toy t : _playable) {
            sum += t.getWinChance();
        }
        // Составление нормализованной функции распределения вероятности
        double prev = 0;
        for (Toy t : _playable) {
            prev += t.getWinChance() / sum;
            _PDF.add(prev);
        }
    }

    private void moveToyToWon(int id) {
        var toy = _playable.get(id);
        Toy wonToy = null;
        if (toy.getAmount() != 1) {
            toy.takeOne();
            wonToy = new Toy(toy.getId(), toy.getProductName(), toy.getWinChance(), 1);
        }
        else {
            wonToy = new Toy(toy.getId(), toy.getProductName(), toy.getWinChance(), 1);
            _playable.remove(id);
        }
        _won.add(wonToy);
    }

    public void play() {
        double userRoll = Math.random();
        _logger.INFO("выброшено число " + userRoll);
        int toyIndex = -1;
        for (int i = 0; i < _PDF.size(); i++) {
            if (i == 0) {
                if (userRoll >= 0 && userRoll < _PDF.get(i)) {
                    toyIndex = 0;
                    break;
                }
            }
            else {
                if (userRoll >= _PDF.get(i - 1) && userRoll < _PDF.get(i)) {
                    toyIndex = i;
                    break;
                }
            }
        }
        if (toyIndex >= 0) {
            moveToyToWon(toyIndex);
            _logger.INFO("игрушка перемещена в список выигранных");
        }
        else {
            _logger.ERROR("ошибка работы механизма выбора игрушки по броску");
        }
    }
    
    public void addToy(String name, double winChance, int amount) {
        _playable.add(new Toy(this._next_id, name, winChance, amount));
        _next_id += 1;
        makePDF();
    }
}
