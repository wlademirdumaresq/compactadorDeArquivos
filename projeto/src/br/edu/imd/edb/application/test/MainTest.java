package br.edu.imd.edb.application.test;

import br.edu.imd.edb.application.Compressor;
import br.edu.imd.edb.application.Extractor;
import br.edu.imd.edb.tree.Node;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void contarCaracteres() throws IOException {
        try {
            //Arrange
            FileWriter myObj = new FileWriter("maintTest1.txt");
            myObj.write("lollapalooza");
            myObj.close();
            Compressor compressor = new Compressor("maintTest1.txt"); // arquivo com "lollapalooza"
            Map<Integer, Integer> teste = new HashMap<>();

            //Act
            teste.put((int) 'l', 4);
            teste.put((int) 'o', 3);
            teste.put((int) 'p', 1);
            teste.put((int) 'a', 3);
            teste.put((int) 'z', 1);
            teste.put(258, 1); //EOF do compactador

            //Assert
            assertEquals(compressor.getMap(), teste);


        } catch (IOException e) {
            e.printStackTrace();
        }
        File aux = new File("maintTest1.txt");
        aux.delete();


    }

    @Test
    public void criacaoArvore() throws IOException {
        try {
            //Arrange
            FileWriter myObj = new FileWriter("maintTest2.txt");
            myObj.write("lollapalooza");
            myObj.close();
            //Arange
            Compressor compressor = new Compressor("maintTest2.txt");
            Node teste;

            //Act
            teste = new Node(13,
                    new Node(6, //esq
                            new Node((int) 'a', 3), //esq
                            new Node(3,  //dir
                                    new Node(300, 1), //esq
                                    new Node(2, //dir
                                            new Node((int) 'p', 1), //esq
                                            new Node((int) 'z', 1)))), // dir
                    new Node(7,
                            new Node((int) 'o', 3), // esq
                            new Node((int) 'l', 4))); // dir
            //Assert

            assertTrue(teste.equals(compressor.getTree()));


        } catch (IOException e) {
            e.printStackTrace();
        }
        File aux = new File("maintTest2.txt");
        aux.delete();

    }

    @Test
    public void gerarBinarioEArquivoChave() {

        try {
            //Arrange
            FileWriter teste = new FileWriter("maintTest3.txt");
            teste.write("lollapalooza");
            teste.close();
            FileWriter chaveTeste = new FileWriter("maintTest3Key.txt");
            chaveTeste.write("l" + (char) -1 + "11" + (char) -1 + "o" + (char) -1 + "10" + (char) -1 + "z" + (char) -1 + "0111" + (char) -1 + "p" + (char) -1 + "0110" + (char) -1 + "Ă" + (char) -1 + "010" + (char) -1 + "a" + (char) -1 + "00" + (char) -1);
            chaveTeste.close();
            Compressor compressor = new Compressor("maintTest3.txt", "mainText3Chave.txt");
            Map<Character, String> mapTeste = new HashMap<>();
            FileInputStream aux1 = new FileInputStream("mainText3Chave.txt");
            FileInputStream aux2 = new FileInputStream("maintTest33Key.txt");
            String str1;
            String str2;

            //Act
            mapTeste.put('l', "11");
            mapTeste.put('o', "10");
            mapTeste.put('z', "0111");
            mapTeste.put('p', "0110");
            mapTeste.put((char) 258, "010");
            mapTeste.put('a', "00");
            str1 = new String(aux1.readAllBytes(), "UTF8");
            str2 = new String(aux2.readAllBytes(), "UTF8");


            //Assert
            assertEquals(mapTeste, compressor.getBinari());
            assertEquals(str1, str2);


        } catch (IOException e) {
            e.printStackTrace();
        }
        File aux = new File("maintTest3.txt");
        File aux1 = new File("maintTest3Key.txt");
        File aux2 = new File("mainText3Chave.txt");

        aux.delete();
        aux1.delete();
        aux2.delete();

    }


    @Test
    public void gerarArquivoCodificado() {

        try {
            //Arrange
            FileWriter arquivoTeste = new FileWriter("maintTest4.txt");
            arquivoTeste.write("lollapalooza");
            arquivoTeste.close();
            FileOutputStream arquivoCodificado = new FileOutputStream("maintTeste4Codificado.txt");
            String bits = "11101111000110001110100111000101";
            int contador = 0;
            BitSet bitSet = new BitSet();
            for (int j = 0; j < bits.length(); j++) {
                if (bits.charAt(j) == '1') {

                    bitSet.set(contador);
                } else {
                    bitSet.set(contador, false);
                }
                contador += 1;
            }
            arquivoCodificado.write(bitSet.toByteArray());
            arquivoCodificado.close();

            Compressor compressor = new Compressor("maintTest4.txt", "mainText4Cod.txt", "mainText4Chave.txt"); // arquivo com "lollapalooza"

            FileInputStream arquivoCodificadoTeste = new FileInputStream("maintTeste4Codificado.txt");
            FileInputStream arquivoCodificadoMainTeste = new FileInputStream("mainText4Cod.txt");

            assertByteEquals(arquivoCodificadoTeste.readAllBytes(), arquivoCodificadoMainTeste.readAllBytes());
            arquivoCodificado.close();
            arquivoCodificadoMainTeste.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        File aux = new File("maintTest4.txt");
        File aux1 = new File("mainText4Cod.txt");
        File aux2 = new File("mainText4Chave.txt");
        File aux3 = new File("maintTeste4Codificado.txt");
        aux.delete();
        aux1.delete();
        aux2.delete();
        aux3.delete();

    }

    @Test
    public void recuperarArquivoChave() {
        try {
            //Arrange
            FileWriter myObj = new FileWriter("maintTest5.txt");
            myObj.write("l" + (char) -1 + "11" + (char) -1 + "o" + (char) -1 + "10" + (char) -1 + "z" + (char) -1 + "0111" + (char) -1 + "p" + (char) -1 + "0110" + (char) -1 + "Ă" + (char) -1 + "010" + (char) -1 + "a" + (char) -1 + "00" + (char) -1);
            myObj.close();
            Extractor extractor = new Extractor("maintTest5.txt"); // arquivo com "lollapalooza"
            Map<Character, String> teste = new HashMap<>();

            //Act
            teste.put('l',"11" );
            teste.put( 'o', "10");
            teste.put( 'p', "0110");
            teste.put( 'a', "00");
            teste.put( 'z', "0111");
            teste.put((char)258, "010"); //EOF do compactador

            //Assert
            assertEquals(extractor.getCodigo(), teste);


        } catch (
                IOException e) {
            e.printStackTrace();
        }

        File aux = new File("maintTest5.txt");
        aux.delete();

    }

    @Test
    public void recuperarArquivoCodificado() {

    }


    public boolean assertByteEquals(byte obj[], byte[] obj1) throws UnsupportedEncodingException {
        String aux = new String(obj, "UTF8");
        String aux1 = new String(obj1, "UTF8");

        return aux.equals(aux1);


    }
}