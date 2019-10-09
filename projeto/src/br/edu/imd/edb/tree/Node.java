package br.edu.imd.edb.tree;

import br.edu.imd.edb.entities.Char;
public class Node {


    private Char value;
    private Node left;
    private Node right;



    //construtores

    public Node() {

        this.value = null;
        this.left = null;
        this.right = null;
    }

    public Node(Char value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Node(Character c , Integer x) {
        this.value = new Char(c,x);
        this.left = null;
        this.right = null;
    }

    public Node(Integer x) {
        this.value = new Char(x);
        this.left = null;
        this.right = null;
    }


    public Node(Integer x, Node left, Node right) {
        this.value = new Char(x);
        this.left = left;
        this.right = right;
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

    public String toString() {

            return "[" + this.getValue().getQuantitie() + " " + this.getValue().getCharacter()+"]";

    }


    public String printTree(int level) {

        String str = toString() + "\n";
        for (int i = 0; i <= level; i++) {
            str += "\t";
        }
        if (this.getLeft() != null) {

            str += "o-ESQ: " + this.left.printTree(level + 1);
        } else {

            str += "o-ESQ: NULL";
        }
        str += "\n";
        for (int i = 0; i <= level; i++) {
            str += "\t";
        }
        if (this.getRight() != null) {

            str += "o-DIR: " + this.right.printTree(level + 1);
        } else {

            str += "o-DIR: NULL";
        }
        str += "\n";

        return str;

    }

}
