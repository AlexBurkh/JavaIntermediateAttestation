package lib.dataModels;

import java.io.Serializable;

public class Toy implements Serializable{
    private int _id;
    private String _productName;
    private double _winChance;
    private int _amount;

    public Toy(int id, String productName, double winChance, int amount) {
        this._id = id;
        this._productName = productName;
        this._winChance = winChance;
        this._amount = amount;
    }

    @Override
    public String toString() {
        return "Toy {id: " + _id + ", productName: " + _productName + ", chanceWeight: " + _winChance + ", amount: " + _amount +"}";
    }

    /* GETTERS */
    public int getId() {
        return _id;
    }
    public String getProductName() {
        return _productName;
    }
    public double getWinChance() {
        return _winChance;
    }
    public int getAmount() {
        return _amount;
    }

    public void reduceId() {
        _id -= 1;
    }
    public void increaseId() {
        _id += 1;
    }

    public void changeWinChance(double chance) {
        this._winChance = chance;
    }
    public void takeOne() {
        _amount -= 1;
    }
    public void putOne() {
        _amount += 1;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _id;
        result = prime * result + ((_productName == null) ? 0 : _productName.hashCode());
        long temp;
        temp = Double.doubleToLongBits(_winChance);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Toy other = (Toy) obj;
        if (_id != other._id)
            return false;
        if (_productName == null) {
            if (other._productName != null)
                return false;
        } else if (!_productName.equals(other._productName))
            return false;
        if (Double.doubleToLongBits(_winChance) != Double.doubleToLongBits(other._winChance))
            return false;
        return true;
    }
}
