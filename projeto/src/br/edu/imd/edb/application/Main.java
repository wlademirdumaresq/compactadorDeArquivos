package br.edu.imd.edb.application;

import br.edu.imd.edb.heap.Heap;
import br.edu.imd.edb.tree.Node;

import java.io.*;
import java.util.*;

import static br.edu.imd.edb.util.TBinary.LBinary;


public class Main {

    public static void main(String[] args) throws IOException {

        //  if (args[0].equals("compress")) {
        Map<Integer, Integer> map = new HashMap<>();

        try {
            File arq = new File(args[0]);
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
                map.put(258, 1);

            } else {
                System.out.println("parametro invalido");
            }
            leitor.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Heap heap = new Heap();

        for (Integer i : map.keySet()) {
            Node no = new Node(i, map.get(i));
            heap.insert(no);
        }


        Node tree = new Node();
        boolean chave = true;
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

       // System.out.println(tree.printTree(0));
        String bit[] = LBinary(tree, chave);

        //System.out.println( tree.printTree(0));
        Map<Character, String> binari = new HashMap<>();

        FileWriter tabelaCod = new FileWriter("filename.edt"); //"filename.edt"

        for (int i = 0; i < bit.length; ) {
            tabelaCod.write((char) Integer.parseInt(bit[i]) + "=" + bit[i + 1] + "\n");
            binari.put((char) Integer.parseInt(bit[i]), bit[i + 1]);
            i += 2;
        }
        //tabelaCod.write(String.valueOf(binari));
        tabelaCod.close();

        FileOutputStream b = new FileOutputStream("filename.edz"); //"filename.edz"
        FileReader arq = new FileReader("teste.txt");

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

        FileReader mensagem = new FileReader("filename.edz");
        FileReader dicionario = new FileReader("filename.edt");

        Map<String, Character> letra = new HashMap<>();
        Map<Character, String> codigo = new HashMap<>();
        Scanner ler = new Scanner(dicionario);
        String[] l;
        while (ler.hasNextLine()) {
            l = ler.nextLine().split("=");
            for (int i = 0; i < l.length; ) {
                letra.put(l[i + 1], l[i].charAt(0));
                codigo.put(l[i].charAt(0), l[i + 1]);
                i += 2;
            }

        }
        dicionario.close();


        //FileWriter escreve = new FileWriter("Teste2.txt");
        FileInputStream fs = new FileInputStream("filename.edz");

        //  ler = new Scanner(mensagem);
//        String l1;
        byte[] bytes = fs.readAllBytes();
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            str.append(new StringBuilder(String.format("%8s", Integer.toBinaryString(bytes[i] & 0xFF)).replace(" ", "0")).reverse());
        }
        String comparador = "";
        FileWriter novo = new FileWriter("novo.txt");

        int cont = 0;
        for (int j = 0; j < str.length(); j++) {
            comparador += str.charAt(j);
            if (comparador.equals(codigo.get((char) 258))) {
                novo.close();

            } else if (codigo.containsValue(comparador)) {
                cont += comparador.length();
                if (comparador.equals(codigo.get((char) 300))) {
                    novo.write("\n");
                    comparador = "";

                } else {
                    novo.write(letra.get(comparador));
                    comparador = "";
                }


            }
        }
        //str.trimToSize();

        // } else if (args[0].equals("Extract")) {
//            try{
//
//                File mensagem = new File(args[1]);
//                File dicionario = new File(args[2]);
//                if(mensagem.exists()){
//                    if(dicionario.exists()){
//
//
//
//                    }
//                }
//
//            }catch (IOException){
//                System.out.println("Parâmemtro inválido!");
//            }
//
//        }else {
//            System.out.println("Parâmemtro inválido!");
//        }

        //      }
    }
}
