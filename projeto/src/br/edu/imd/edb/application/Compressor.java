package br.edu.imd.edb.application;

import br.edu.imd.edb.heap.Heap;
import br.edu.imd.edb.tree.Node;

import java.io.*;
import java.util.*;

import static br.edu.imd.edb.util.TBinary.LBinary;

public class Compressor {

    private int tamanhoAntigo;
    private int tamanhoTabela;
    private int tamanhoCodificado;
    private Heap heap = new Heap();
    private Map<Integer, Integer> map = new HashMap<>();
    private boolean chave = true;
    private Node tree = new Node();
    private Map<Character, String> binari = new HashMap<>();
    private String texto, mensagem, dicionario;

    public Compressor(String texto, String mensagem, String dicionario) throws IOException {
        this.texto = texto;
        this.mensagem = mensagem;
        this.dicionario = dicionario;
        criarDicionario();
        criandoArvore();
        criandoTabela();
        codificandoTexto();
    }



    public void criarDicionario() {

        try {
            FileInputStream arq = new FileInputStream(texto);

            BufferedInputStream leitor = new BufferedInputStream(arq);

            byte linha[] = leitor.readAllBytes();
            tamanhoAntigo = linha.length;
            String arquivo = new String(linha, "UTF8");

            for (int i = 0; i < arquivo.length(); i++) {
                if (map.containsKey((int) arquivo.charAt(i))) {
                    int value = (map.get((int) arquivo.charAt(i))) + 1;
                    map.put((int) arquivo.charAt(i), value);
                } else {
                    map.put((int) arquivo.charAt(i), 1);
                }
            }

        } catch (IOException e) {
            System.out.println("parametro invalido" + e.getMessage());
        }

        map.put(258, 1);
    }

    public void criandoArvore() {
        for (Integer i : map.keySet()) {
            Node no = new Node(i, map.get(i));
            heap.insert(no);
        }

        do {
            if (heap.getSize() == 1) {
                chave = false;
                break;
            }
            Node left = heap.peek();
            heap.remove();
            Node right = heap.peek();
            heap.remove();
            tree = new Node(left.getValue().getQuantitie() + right.getValue().getQuantitie(), left, right);
            heap.insert(tree);

        } while (heap.getSize() > 1);

    }

    public void criandoTabela() throws IOException {
        String bit[] = LBinary(tree, chave);

        FileWriter tabelaCod = new FileWriter(dicionario);
        tamanhoTabela = 0;
        for (int i = 0; i < bit.length; ) {
            tabelaCod.write((char) Integer.parseInt(bit[i]) + String.valueOf((char) -1) + bit[i + 1] + String.valueOf((char) -1));
            binari.put((char) Integer.parseInt(bit[i]), bit[i + 1]);
            i += 2;
        }
        tabelaCod.close();
        FileInputStream tabela = new FileInputStream(dicionario);
        tamanhoTabela = tabela.readAllBytes().length;
        tabela.close();

    }

    public void codificandoTexto() throws IOException {
        FileOutputStream b = new FileOutputStream(mensagem);
        FileInputStream arq = new FileInputStream(texto);
        BufferedInputStream leitor = new BufferedInputStream(arq);

        int contador = 0;

        BitSet bitSet = new BitSet();
        String bits = "";


        byte texto[] = leitor.readAllBytes();
        String arquivo = new String(texto, "UTF8");

        for (int i = 0; i < arquivo.length(); i++) {

            if (binari.containsKey(arquivo.charAt(i))) {
                for (int j = 0; j < binari.get(arquivo.charAt(i)).length(); j++) {
                    if (binari.get(arquivo.charAt(i)).charAt(j) == '1') {
                        bits += "1";
                        bitSet.set(contador);
                    } else {
                        bits += "0";
                        bitSet.set(contador, false);
                    }
                    contador += 1;
                }
            }

        }

        for (int j = 0; j < binari.get((char) 258).length(); j++) {
            if (binari.get((char) 258).charAt(j) == '1') {

                bitSet.set(contador);
            } else {

                bitSet.set(contador, false);
            }
            contador += 1;
        }
        int multiplicacao = 0;
        if (contador % 8 == 0) {

            b.write(bitSet.toByteArray());
            b.close();
            arq.close();
        } else {
            multiplicacao = (int) ((1 - (((float) contador / 8) - (contador / 8))) * 8);
            for (int i = 0; i < multiplicacao; i++) {
                bitSet.set(contador);
                contador += 1;

            }
            b.write(bitSet.toByteArray());
            b.close();
            arq.close();
        }

        tamanhoCodificado = (bits.length()) / 8;
        System.out.println(100.0 - (((float) (tamanhoCodificado + tamanhoTabela) * 100) / tamanhoAntigo )+ "% compactação");
    }

}
