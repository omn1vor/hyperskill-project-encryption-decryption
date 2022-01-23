package encryptdecrypt;

interface Algorithm {

    String encrypt(String input);
    String decrypt(String input);

}

class ShiftAlgorithm implements Algorithm {

    int key;

    public ShiftAlgorithm(int key) {
        this.key = key;
    }

    public String encrypt (String input) {
        return shiftSymbols(input, key);
    }

    public String decrypt (String input) {
        return shiftSymbols(input, -key);
    }

    private String shiftSymbols(String input, int offset) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char symbol = input.charAt(i);
            boolean isUpper = symbol >= 'A' && symbol <= 'Z';
            boolean isLower = symbol >= 'a' && symbol <= 'z';
            if (isLower || isUpper) {
                int min = isLower ? 'a' : 'A';
                int max = isLower ? 'z' : 'Z';
                int shifted = Math.floorMod(symbol + offset - min, max - min + 1) + min;
                if (shifted < 0) {
                    shifted = shifted + max - min;
                }
                output.append((char) shifted);
            } else {
                output.append(symbol);
            }
        }
        return output.toString();
    }

}
class UnicodeAlgorithm implements Algorithm {

    int key;

    public UnicodeAlgorithm(int key) {
        this.key = key;
    }

    public String encrypt (String input) {
        return shiftSymbols(input, key);
    }

    public String decrypt (String input) {
        return shiftSymbols(input, -key);
    }

    private String shiftSymbols(String input, int offset) {
        StringBuilder output = new StringBuilder();
        final char min = Character.MIN_VALUE;
        final char max = Character.MAX_VALUE;
        int shifted;
        for (int i = 0; i < input.length(); i++) {
            shifted = (input.charAt(i) + offset) % (max + 1);
            if (shifted < 0) {
                shifted = shifted + max - min;
            }
            output.append((char) shifted);
//            output.append((char) (input.charAt(i) + offset));
        }
        return output.toString();
    }
}
