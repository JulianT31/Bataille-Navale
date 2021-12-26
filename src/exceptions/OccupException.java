package exceptions;

public class OccupException extends Exception {

    // constructeurs
    public OccupException(int x, int y) {
        super("La case [" + x + ", " + y + "] est déjà occupée !");
    }

    public OccupException(String message) {
        super(message);
    }
}
