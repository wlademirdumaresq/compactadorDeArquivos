package br.edu.imd.edb.tree;

import br.edu.imd.edb.entities.Char;

public class Node {


    private Char value;
    private Node left;
    private Node right;
    private Integer bit;
    private String pathBit;

    public Integer getBit() {
        return bit;
    }

    public void setBit(Integer bit) {
        this.bit = bit;
    }

//construtores

    public Node() {

        this.value = null;
        this.left = null;
        this.right = null;
    }


    public Node(Integer c, Integer x) {
        this.value = new Char(c, x);
        this.left = null;
        this.right = null;
    }



    public Node(Integer x, Node left, Node right) {
        this.value = new Char(x);
        this.left = left;
        this.right = right;
        this.bit = null;
        this.left.setBit(0);
        this.right.setBit(1);
    }

    //Get && Set


    public Char getValue() {
        return value;
    }

    public void setValue(Char value) {
        this.value = value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public String getPathBit() {
        return pathBit;
    }

    public void setPathBit(String pathBit) {
        this.pathBit = pathBit;
    }


}