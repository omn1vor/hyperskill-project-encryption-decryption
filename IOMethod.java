package encryptdecrypt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

interface IOMethod {

    String read();
    void write(String output);

}

class FileIO implements IOMethod {

    String filename;

    public FileIO(String filename) {
        this.filename = filename;
    }

    public String read() {
//        final String srcPath = "Encryption-Decryption/task/src/encryptdecrypt/";
        try {
            return new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            throw new IllegalArgumentException("Error: cannot read file: " + e);
        }
    }

    public void write(String output) {
        try {
            Files.writeString(Paths.get(filename), output);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error: cannot write to file: " + e);
        }
    }
}

class StandardIO implements IOMethod {

    String data = "";

    public StandardIO() {
    }

    public StandardIO(String data) {
        this.data = data;
    }

    public String read() {
        return data;
    }

    public void write(String output) {
        System.out.println(output);
    }
}
