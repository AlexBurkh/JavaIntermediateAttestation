package lib.dataModels;

public class Toy {
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

    public void changeWinChance(double chance) {
        this._winChance = chance;
    }
    public Toy take() {
        this._amount -= 1;
        return this;
    }
    public void put() {
        this._amount += 1;
    }
}
