package br.edu.imd.edb.tree;


import br.edu.imd.edb.entities.Char;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private Node raiz = null;
    private List<Node> list = new ArrayList<>();

    public List<Node> getList() {
        return list;
    }

    public void Add(Node n){
        list.add(n);
    }

    public void Remove(Node n){
        list.remove(n);
    }

    public boolean isEmpty() {
        return raiz == null;
    }

    public Node getRoot() {
        return raiz;
    }

    public void insert(Node node) {

        Add(node);
    }

    public void insert(Char c) {
        Node n = new Node(c);
        insert(n);
    }

    public void insert(int x) {
        Char c = new Char(x);
        Node n = new Node(c);
        insert(n);
    }


    public Node search(int key) {
        if (isEmpty()) {
            return null;
        }
        return raiz.search(key);
    }

    public void preOrdem() {
        if (!isEmpty()) {
            raiz.preOrdem();
        }


    }

    public void inOrdem() {
        if (!isEmpty()) {
            raiz.inOrdem();

        }
    }

    public void posOrdem() {
        if (!isEmpty()) {
            raiz.posOrdem();
        }
    }

    public void calcularBalanceamento() {
        if (!isEmpty()) {
            raiz.calcularBalanceamento();
        }
    }

    public void remove(int value) {
        if (isEmpty()) {
            return;
        }
        raiz.remove(value);
    }

    public void printTree() {

        if (!isEmpty()) {

            System.out.println("\n"+raiz.printTree(0));
        }
    }

}
