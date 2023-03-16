package lib.dataModels;

import java.util.ArrayList;
import java.util.List;

import lib.support.Logger;
import lib.ui.View;

public class ToyMachine {
    private Logger _logger;
    private View _view;
    private int _next_id = 0;

    private List<Toy> _playable;
    private List<Toy> _won;

    public ToyMachine(Logger _logger, View _view, List<Toy> playable, List<Toy> won) {
        this._logger = _logger;
        this._view = _view;
        this._playable = playable;
        this._won = won;
    }

    public ToyMachine(Logger logger, View view) {
        this(logger, view, new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public String toString() {
        return "ToyMachine { next_id=" + _next_id + ", playable=" + _playable + ", won=" + _won + " }";
    }
    
    public void roll() {
        List<Double> weights = new ArrayList<Double>();
        double sum = 0;
        for (Toy t : _playable) {
            sum += t.getWinChance();
            weights.add(sum);
        }
        _logger.INFO("сформирована функция распределения вероятностей");
        double userRoll = Math.random() * sum;
        _logger.INFO("выброшено число " + userRoll);
        int toyIndex = -1;
        for (int i = 0; i < weights.size(); i++) {
            if (i == 0) {
                if (userRoll >= 0 && userRoll < weights.get(i)) {
                    toyIndex = 0;
                    _logger.INFO("выпала игрушка № 1");
                }
            }
            else {
                if (userRoll >= weights.get(i - 1) && userRoll < weights.get(i)) {
                    toyIndex = i;
                    _logger.INFO("выпала игрушка № " + (toyIndex + 1));
                }
            }
        }
        if (toyIndex >= 0) {
            Toy t = _playable.get(toyIndex).take();
            _won.add(t);
            _logger.INFO("имя игрушки: " + t.getProductName());
            _logger.INFO("игрушка перемещена в список выигранных");
        }
        else {
            _logger.ERROR("ошибка работы механизма выбора игрушки по броску");
        }
    }
    
    public void addToy(String name, double winChance, int amount) {
        _playable.add(new Toy(this._next_id, name, winChance, amount));
        _next_id += 1;
    }
}
