package br.edu.imd.edb.application;

import java.io.IOException;
import java.time.LocalTime;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args[0].equals("compress")) {
            if (args.length > 3) {
                new Compressor(args[1], args[2], args[3]);
            }


        } else if (args[0].equals("extract")) {
            new Extractor(args[1], args[2], args[3]);

        }

    }
}
