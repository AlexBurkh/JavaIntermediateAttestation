package lib.dataModels;

public class Toy {
    private int _id;
    private String _productName;
    private double _winChance;

    public Toy(int id, String productName, double winChance) {
        this._id = id;
        this._productName = productName;
        this._winChance = winChance;
    }

    @Override
    public String toString() {
        return "Toy {id=" + _id + ", productName=" + _productName + ", chanceWeight=" + _winChance + "}";
    }

    /* GETTERS */
    public int get_id() {
        return _id;
    }
    public String get_productName() {
        return _productName;
    }
    public double get_chanceWeight() {
        return _winChance;
    }

    public void changeWinChance(double chance) {
        this._winChance = chance;
    }
}
