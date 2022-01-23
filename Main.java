package encryptdecrypt;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        String data = "";
        int key = 0;
        String inPath = "";
        String outPath = "";
        String input = "";
        String output;

        if (args.length % 2 != 0) {
            System.out.println("Usage: java Main "
                    + "[-mode enc/dec (default: enc)] "
                    + "[-key int (default: 0)] "
                    + "[-data String (default: \"\")]"
                    + "[-in filename]"
                    + "[-out filename]");
            return;
        }

        for (int i = 0; i < args.length; i += 2) {
            String argName = args[i];
            String argValue = args[i + 1];
            if ("-mode".equalsIgnoreCase(argName)) {
                mode = argValue;
            } else if ("-key".equalsIgnoreCase(argName)) {
                try {
                    key = Integer.parseInt(argValue);
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: wrong -key argument, expecting an integer");
                    return;
                }
            } else if ("-data".equalsIgnoreCase(argName)) {
                data = argValue;
            } else if ("-in".equalsIgnoreCase(argName)) {
                inPath = argValue;
            } else if ("-out".equalsIgnoreCase(argName)) {
                outPath = argValue;
            }
        }

        if (!"".equals(data)) {
            input = data;
        } else if (!"".equals(inPath)) {
            try {
                input = new String(Files.readAllBytes(Paths.get(inPath)));
            } catch (IOException e) {
                System.out.println("Error: cannot read file: " + e);
                return;
            }
        }

        switch (mode) {
            case "enc":
                output = Algorithm.encrypt(input, key);
                break;
            case "dec":
                output = Algorithm.decrypt(input, key);
                break;
            default:
                System.out.println("Error: wrong mode!");
                return;
        }

        if (!"".equals(outPath)) {
            try {
                Files.writeString(Paths.get(outPath), output);
            } catch (IOException e) {
                System.out.println("Error: cannot write to file: " + e);
            }
        } else {
            System.out.println(output);
        }
    }

    class Enigma {
        String mode = "enc";
        Algorithm alg;
        IOMethod input;
        IOMethod output;

        public Enigma(String mode, Algorithm alg, IOMethod input, IOMethod output) {
            this.mode = mode;
            this.alg = alg;
            this.input = input;
            this.output = output;
        }

        Enigma(String[] args) {

        }

        private boolean checkArguments(String[] args) {
            if (args.length % 2 != 0) {
                System.out.println("Usage: java Main "
                        + "[-mode enc/dec (default: enc)] "
                        + "[-key int (default: 0)] "
                        + "[-data String (default: \"\")]"
                        + "[-in filename]"
                        + "[-out filename]");
                return false;
            }
            return true;
        }

    }


    interface Algorithm {

        String encrypt(String input);
        String decrypt(String input);

    }

    class ShiftAlgorith implements Algorithm {

        int offset;

        public ShiftAlgorith(int key) {
            this.offset = key;
        }

        public String encrypt (String input) {
            return shiftSymbols(input);
        }

        public String decrypt (String input) {
            return shiftSymbols(input);
        }

        public String shiftSymbols(String input) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                output.append((char) (input.charAt(i) + offset));
            }
            return output.toString();
        }
    }
    class UnicodeAlgorithm implements Algorithm {

        int offset;

        public UnicodeAlgorithm(int key) {
            this.offset = key;
        }

        public String encrypt (String input) {
            return shiftSymbols(input);
        }

        public String decrypt (String input) {
            return shiftSymbols(input);
        }

        public String shiftSymbols(String input) {
            StringBuilder output = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                output.append((char) (input.charAt(i) + offset));
            }
            return output.toString();
        }
    }

    interface IOMethod {

        String read();
        String write();

    }

}


