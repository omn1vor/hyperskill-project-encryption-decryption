package encryptdecrypt;

public class Main {
    public static void main(String[] args) {
        try {
            Enigma enigma = new Enigma(args);
            enigma.process();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}






