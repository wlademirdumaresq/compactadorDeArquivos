package br.edu.imd.edb.application;

import br.edu.imd.edb.heap.Heap;
import br.edu.imd.edb.tree.Node;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static br.edu.imd.edb.util.TBinary.LBinary;

public class Compressor {

    private Heap heap = new Heap();
    private Map<Integer, Integer> map = new HashMap<>();
    private boolean chave = true;
    private Node tree = new Node();
    private Map<Character, String> binari = new HashMap<>();
    private String texto, mensagem, dicionario;

    public Compressor(String texto, String mensagem, String dicionario){
        this.texto = texto;
        this.mensagem = mensagem;
        this.dicionario = dicionario;
    }

    public void criarDicionario() throws FileNotFoundException {
        File arq = new File(texto);
        Scanner leitor = new Scanner(arq);
        String linha;
        if (arq.exists()) {
            while (leitor.hasNextLine()) {
                linha = leitor.nextLine();

                for (int i = 0; i < linha.length(); i++) {
                    if (map.containsKey((int) linha.charAt(i))) {
                        int value = (map.get((int) linha.charAt(i))) + 1;
                        map.put((int) linha.charAt(i), value);
                    } else {
                        map.put((int) linha.charAt(i), 1);
                    }
                }

                if (map.containsKey(300) && leitor.hasNextLine()) {
                    int value = (map.get(300)) + 1;
                    map.remove(300);
                    map.put(300, value);
                } else if (!(map.containsKey(300)) && leitor.hasNextLine()) {
                    map.put(300, 1);
                }
            }
        } else {
            System.out.println("parametro invalido");
        }

        map.put(258, 1);
        leitor.close();
    }

    public void criandoArvore(){
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

    public Map criandoTabela() throws IOException {
        String bit[] = LBinary(tree, chave);

        FileWriter tabelaCod = new FileWriter(dicionario);

        for (int i = 0; i < bit.length; ) {
            tabelaCod.write((char) Integer.parseInt(bit[i]) + String.valueOf((char)305) + bit[i + 1] + "\n");
            binari.put((char) Integer.parseInt(bit[i]), bit[i + 1]);
            i += 2;
        }
        tabelaCod.close();

        return binari;
    }

    public void codificandoTexto() throws IOException {
        FileOutputStream b = new FileOutputStream(mensagem);
        FileReader arq = new FileReader(texto);
        Scanner leitor = new Scanner(arq);
        int contador = 0;
        BitSet bitSet = new BitSet();
        String bits = "";
        while (leitor.hasNextLine()) {
            String linha = leitor.nextLine();

            for (int i = 0; i < linha.length(); i++) {
                if (binari.containsKey(linha.charAt(i))) {
                    for (int j = 0; j < binari.get(linha.charAt(i)).length(); j++) {
                        if (binari.get(linha.charAt(i)).charAt(j) == '1') {
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
            if (leitor.hasNextLine()) {
                for (int j = 0; j < binari.get((char) 300).length(); j++) {
                    if (binari.get((char) 300).charAt(j) == '1') {
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
                bits += "1";
                bitSet.set(contador);
            } else {
                bits += "0";
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
                bits += "1";
            }
            b.write(bitSet.toByteArray());
            b.close();
            arq.close();
        }
    }

}
