import java.io.Serializable;

public class Ulukbek implements Serializable {
    private int[][] desktop;

    public Ulukbek(int[][] desktop) {
        this.desktop = desktop;
    }

    public int[][] getDesktop() {
        return desktop;
    }
}
