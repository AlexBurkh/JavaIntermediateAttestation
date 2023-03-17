package lib.dataModels;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lib.support.Logger;

public class ToyMachine implements Serializable{
    private Logger _logger;

    private int _next_id = 0;
    private List<Double> _PDF;

    private List<Toy> _playable;
    private List<Toy> _won;

    /* CONTRUCTORS */
    public ToyMachine(Logger _logger, List<Toy> playable, List<Toy> won) {
        this._logger = _logger;
        this._playable = playable;
        this._won = won;
        makePDF();
    }
    public ToyMachine(Logger logger) {
        this(logger, new ArrayList<>(), new ArrayList<>());
    }

    /* OVERRIDE */
    @Override
    public String toString() {
        return "ToyMachine { next_id=" + _next_id + ", playable=" + _playable + ", won=" + _won + " }";
    }

    /* GETTERS */
    public List<Toy> getPlayable() {
        return _playable;
    }
    public List<Toy> getWon() {
        return _won;
    }

    /**
     * Составить функцию распределения вероятности
     * 
     * @variable _PDF - список для хранения самой дискретной функции распределения
     *           вероятности
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

    /**
     * Вычисление текущего максимального индекса игрушек в _playable
     * 
     * @return максимальный индекс списка _playable
     */
    private int current_max_id() {
        int maxId = 0;
        for (var toy : _playable) {
            if (toy.getId() > maxId) {
                maxId = toy.getId();
            }
        }
        return maxId;
    }

    /**
     * Перемещает игрушку с переданным id из списка _playable в список выигранных
     * _won
     * 
     * @param id игрушки в списке _playable, которая подлежит перемещению в списк
     *           выигранных _won
     */
    private void moveToyToWon(int id) {
        var toy = _playable.get(id);
        Toy wonToy = null;
        if (toy.getAmount() != 1) {
            toy.takeOne();
            wonToy = new Toy(toy.getId(), toy.getProductName(), toy.getWinChance(), 1);
        } else {
            wonToy = new Toy(toy.getId(), toy.getProductName(), toy.getWinChance(), 1);
            deleteToy(id);
        }
        if (_won.contains(wonToy)) {
            int index = _won.indexOf(wonToy);
            _won.get(index).putOne();
        }
        else{
            _won.add(wonToy);
        }
    }

    /**
     * Игра. Генерация случайного числа, выбор игрушки, перемещение в список
     * выигранных
     */
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
            } else {
                if (userRoll >= _PDF.get(i - 1) && userRoll < _PDF.get(i)) {
                    toyIndex = i;
                    break;
                }
            }
        }
        if (toyIndex >= 0) {
            moveToyToWon(toyIndex);
            _logger.INFO("игрушка перемещена в список выигранных");
        } else {
            _logger.ERROR("ошибка работы механизма выбора игрушки");
        }
    }

    /**
     * Добавить игрушку в список playable
     * 
     * @param name      имя игрушки для добавления
     * @param winChance весовой коэффициент для выигрыша
     * @param amount    количество игрушек этого типа
     */
    public void addToy(String name, double winChance, int amount) {
        _playable.add(new Toy(this._next_id, name, winChance, amount));
        _next_id += 1;
        makePDF();
    }

    /**
     * Удаление игрушки из списка _playable
     * 
     * @param id id игрушки которую нужно удалить
     * @return результат операции
     */
    public boolean deleteToy(int id) {
        boolean flag = false;
        _playable.remove(id);
        for (int i = 0; i < _playable.size(); i++) {
            _playable.get(i).reduceId();
        }
        if (_playable.size() > 0) {
            _next_id = current_max_id() + 1;
        } else {
            _next_id = 0;
        }
        return flag;
    }

    public Toy getReward() {
        if (! _won.isEmpty()) {
            var toy = _won.get(0);
            var reward = new Toy(toy.getId(), toy.getProductName(), toy.getWinChance(), 1);
            if (toy.getAmount() > 1) {
                toy.takeOne();
            }
            else {
                _won.remove(0);
            }
            return toy;
        }
        return null;
    }
}
