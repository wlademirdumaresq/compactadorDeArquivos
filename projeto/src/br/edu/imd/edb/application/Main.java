package br.edu.imd.edb.application;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args[0].equals("compress")) {
            Compressor comprimir = new Compressor(args[1], args[2], args[3]);

            comprimir.criarDicionario();
            comprimir.criandoArvore();
            comprimir.criandoTabela();
            comprimir.codificandoTexto();

        }else if(args[0].equals("extract")){
            Extractor extrair = new Extractor(args[1], args[2], args[3]);

            extrair.recuperandoTabela();
            extrair.descodificandoTexto();
        }
    }
}
