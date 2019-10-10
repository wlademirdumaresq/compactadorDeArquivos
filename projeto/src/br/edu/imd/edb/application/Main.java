package br.edu.imd.edb.application;

import br.edu.imd.edb.heap.Heap;
import br.edu.imd.edb.tree.Node;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static br.edu.imd.edb.util.TBinary.LBinary;


public class Main {

    public static void main(String[] args) throws IOException {
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

                    if (map.containsKey(257) && leitor.hasNextLine()) {
                        int value = (map.get(257)) + 1;
                        map.remove(257);
                        map.put(257, value);
                    } else if (!(map.containsKey(257)) && leitor.hasNextLine()) {
                        map.put(257, 1);
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

        do {
            if (heap.getSize() == 0) {
                break;
            }
            Node left = heap.peek();
            heap.remove();
            Node right = heap.peek();
            heap.remove();
            tree = new Node(left.getValue().getQuantitie() + right.getValue().getQuantitie(), left, right);
            heap.insert(tree);
        } while (heap.getSize() > 1);

        String bit[] = LBinary(tree);

        //System.out.println( tree.printTree(0));
        Map<Character, String> binari = new HashMap<>();

        FileWriter tabelaCod = new FileWriter("filename.edt");

        for (int i = 0; i < bit.length; ) {

            tabelaCod.write((char) Integer.parseInt(bit[i]) + "=" + bit[i + 1] + "\n");
            binari.put((char) Integer.parseInt(bit[i]), bit[i + 1]);
            i += 2;
        }
        tabelaCod.close();

        FileWriter b = new FileWriter("filename.edz");
        FileReader arq = new FileReader(args[0]);

        Scanner leitor = new Scanner(arq);
        int contador = 0;
        while (leitor.hasNextLine()) {
            String linha = leitor.nextLine();
            for (int i = 0; i < linha.length(); i++) {
                if (binari.containsKey(linha.charAt(i))) {
                    b.write(binari.get(linha.charAt(i)));
                    for (int j = 0; j < binari.get(linha.charAt(i)).length();j++){
                        contador +=1;
                    }
                }
            }
        }
        b.write(binari.get((char) 258));
        contador +=binari.get((char) 258).length();

        if(contador%8 == 0){
            b.close();
        }else{
            int multiplicacao = (int) ((1-(((float)contador/8) - (contador/8)))*8);
            for(int i = 0; i<multiplicacao; i++){
                b.write('0');
            }
            b.close();
        }

    }
}
