package sg.edu.nus.iss.sereserch.ethan.model;


/**
 * Coordinate Model.
 *
 */
public class CoordinateModel {

    private float x;
    private float y;
    private String label;
    private String name="New Label";

    private String color;
    private int rotation;
    private int size;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setlabel(String y){this.label=y;}

    public void setName(String y){this.name=y;}

    public String getlabel(){return label;}

    public String getString(){return name; }
    @Override
    public String toString() {
        return ""+label;
    }

}
