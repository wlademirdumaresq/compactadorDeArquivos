package br.edu.imd.edb.application;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Extractor {
    private Map<String, Character> letra = new HashMap<>();
    private Map<Character, String> codigo = new HashMap<>();
    private String mensagem, mapa, traducao;

    public Extractor(String mensagem, String mapa, String traducao) throws IOException {
        this.mensagem = mensagem;
        this.mapa = mapa;
        this.traducao = traducao;
        recuperandoTabela();
        descodificandoTexto();
    }

    public Extractor(String mapa) throws IOException {
        this.mensagem = null;
        this.mapa = mapa;
        this.traducao = null;
        recuperandoTabela();
    }

    public Map<Character, String> getCodigo() {
        return codigo;
    }

    public void recuperandoTabela() throws IOException {
        FileInputStream dicionario = new FileInputStream(mapa);
        BufferedInputStream leitor = new BufferedInputStream(dicionario);
        byte linha[] = leitor.readAllBytes();
        String arquivo = new String(linha, "UTF8");
        String []codigos = arquivo.split(String.valueOf((char)-1));

        for (int i = 0; i < codigos.length; ) {
            letra.put(codigos[i+1], codigos[i].charAt(0));
            codigo.put(codigos[i].charAt(0), codigos[i+1]);
            i+=2;
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

        for (int j = 0; j < str.length(); j++) {
            comparador += str.charAt(j);
            if (codigo.containsValue(comparador)) {
                if (comparador.equals(codigo.get((char) 258))) {

                        novo.close();


                } else {
                    novo.write(letra.get(comparador));
                    comparador = "";
                }


            }
        }
    }
}






