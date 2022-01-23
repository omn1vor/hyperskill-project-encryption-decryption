package encryptdecrypt;

class Enigma {
    String mode = "enc";
    Algorithm alg;
    IOMethod input;
    IOMethod output;

    public Enigma(String[] args) {
        if (!checkArguments(args)) {
            throw new IllegalArgumentException(
                    "Usage: java Main "
                            + "[-mode enc/dec (default: enc)] "
                            + "[-key int (default: 0)] "
                            + "[-data String (default: \"\")]"
                            + "[-in filename]"
                            + "[-out filename]");
        }

        String algName = "shift";
        String data = "";
        String inPath = "";
        String outPath = "";
        int key = 0;
        for (int i = 0; i < args.length; i += 2) {
            String argName = args[i];
            String argValue = args[i + 1];
            if ("-mode".equalsIgnoreCase(argName)) {
                mode = argValue;
            } else if ("-key".equalsIgnoreCase(argName)) {
                try {
                    key = Integer.parseInt(argValue);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Error: wrong -key argument, expecting an integer");
                }
            } else if ("-alg".equalsIgnoreCase(argName)) {
                algName = argValue;
            } else if ("-data".equalsIgnoreCase(argName)) {
                data = argValue;
            } else if ("-in".equalsIgnoreCase(argName)) {
                inPath = argValue;
            } else if ("-out".equalsIgnoreCase(argName)) {
                outPath = argValue;
            }
        }

        setAlgorithm(algName, key);
        setInputMethod(data, inPath);
        setOutputMethod(outPath);
    }

    public void process() {
        String result;
        if ("dec".equalsIgnoreCase(mode)) {
            result = alg.decrypt(input.read());
        } else {
            result = alg.encrypt(input.read());
        }
        output.write(result);
    }

    private boolean checkArguments(String[] args) {
        return args.length % 2 == 0;
    }

    private void setAlgorithm(String name, int key) {
        switch (name.toLowerCase()) {
            case "unicode":
                this.alg = new UnicodeAlgorithm(key);
                break;
            case "shift": // shift is default
            default:
                this.alg = new ShiftAlgorithm(key);
                break;
        }
    }

    private void setInputMethod(String data, String inPath) {
        if (!"".equals(data)) {
            this.input = new StandardIO(data);
        } else if (!"".equals(inPath)) {
            this.input = new FileIO(inPath);
        } else {
            this.input = new StandardIO("");
        }
    }

    private void setOutputMethod(String outPath) {
        if (!"".equals(outPath)) {
            this.output = new FileIO(outPath);
        } else {
            this.output = new StandardIO();
        }
    }
}
