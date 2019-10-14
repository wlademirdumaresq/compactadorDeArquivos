package br.edu.imd.edb.application;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Extractor {
    private Map<String, Character> letra = new HashMap<>();
    private Map<Character, String> codigo = new HashMap<>();
    private String mensagem, mapa, traducao;

    public Extractor(String mensagem, String mapa, String traducao) {
        this.mensagem = mensagem;
        this.mapa = mapa;
        this.traducao = traducao;
    }

    public void recuperandoTabela() throws IOException {
        FileReader dicionario = new FileReader(mapa);

        Scanner ler = new Scanner(dicionario);

        String cod = "";
        while (ler.hasNextLine()) {
            String x = ler.nextLine();
            char c = x.charAt(0);
            for (int i = 1; i < x.length(); i++) {
                cod += x.charAt(i);
            }

            letra.put(cod, c);
            codigo.put(c, cod);
            cod = "";

        }
        dicionario.close();
    }

    public void descodificandoTexto() throws IOException {
        FileInputStream fs = new FileInputStream(mensagem);

        byte[] bytes = fs.readAllBytes();
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            str.append(new StringBuilder(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(" ", "0")).reverse());
        }

        String comparador = "";
        FileWriter novo = new FileWriter(traducao);

        //int cont = 0;
        for (int j = 0; j < str.length(); j++) {
            comparador += str.charAt(j);
            if (codigo.containsValue(comparador)) {
                //cont += comparador.length();
                if (comparador.equals(codigo.get((char) 300))) {
                    novo.write("\n");
                    comparador = "";

                } else if (comparador.equals(codigo.get((char) 258))) {

                    if (!codigo.containsKey((char) 300) && codigo.size() > 1) {
                        novo.write("\n");
                        novo.close();
                    } else if (codigo.containsKey((char) 300) && codigo.size() >= 2) {

                        novo.write("\n");
                        novo.close();
                    } else {
                        System.out.println("entrou");
                        novo.close();

                    }
                } else {
                    novo.write(letra.get(comparador));
                    comparador = "";
                }


            }
        }
    }
}






