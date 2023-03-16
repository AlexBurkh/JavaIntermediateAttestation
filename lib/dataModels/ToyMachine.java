package lib.dataModels;

import java.util.ArrayList;
import java.util.List;

import lib.support.Logger;
import lib.ui.View;

public class ToyMachine {
    private Logger _logger;
    private View _view;
    private int _next_id = 0;
    private List<Double> _PDF;

    private List<Toy> _playable;
    private List<Toy> _won;

    public ToyMachine(Logger _logger, View _view, List<Toy> playable, List<Toy> won) {
        this._logger = _logger;
        this._view = _view;
        this._playable = playable;
        this._won = won;
        makePDF();
    }

    public ToyMachine(Logger logger, View view) {
        this(logger, view, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public String toString() {
        return "ToyMachine { next_id=" + _next_id + ", playable=" + _playable + ", won=" + _won + " }";
    }
    
    /**
     * Сделать функцию плотности вероятности
     * @variable _PDF - список для хранения самой дискретной функции плотности вероятности
     * @variable sum - сумма всех вероятностей (для нормализации)
     */
    private void makePDF() {
        this._PDF = new ArrayList<Double>();
        double sum = 0;
        // Вычисление максимума функции плотности вероятности
        for (Toy t : _playable) {
            sum += t.getWinChance();
        }
        // Составление нормализованной функции плотности вероятности
        double prev = 0;
        for (Toy t : _playable) {
            prev += t.getWinChance() / sum;
            _PDF.add(prev);
        }
        if (_PDF.size() > 0) {
            _logger.INFO("сформирована функция распределения вероятностей");
        }
    }

    public void roll() {
        double userRoll = Math.random();
        _logger.INFO("выброшено число " + userRoll);
        int toyIndex = -1;
        for (int i = 0; i < _PDF.size(); i++) {
            if (i == 0) {
                if (userRoll >= 0 && userRoll < _PDF.get(i)) {
                    toyIndex = 0;
                    _logger.INFO("выпала игрушка № 1");
                    break;
                }
            }
            else {
                if (userRoll >= _PDF.get(i - 1) && userRoll < _PDF.get(i)) {
                    toyIndex = i;
                    _logger.INFO("выпала игрушка № " + (toyIndex + 1));
                    break;
                }
            }
        }
        if (toyIndex >= 0) {
            Toy t = _playable.get(toyIndex).take();
            _logger.INFO("имя игрушки: " + t.getProductName());
            _won.add(t);
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
