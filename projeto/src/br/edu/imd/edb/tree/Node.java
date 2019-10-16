package br.edu.imd.edb.tree;

import br.edu.imd.edb.entities.Char;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this.value.getQuantitie() == ((Node) o).getValue().getQuantitie() && this.value.getCharacter() == ((Node) o).getValue().getCharacter()) {
            if (this.left != null && ((Node) o).left != null) {
                if (!this.left.equals(((Node) o).left)) {
                    return false;
                }

            } else if (this.right != null && ((Node) o).right != null) {
                if (!this.right.equals(((Node) o).right)) {
                    return false;
                }

            }
                return true;

        }

        return false;
    }

//    @Override
////    public int hashCode() {
////        return Objects.hash(value.getQuantitie());
////    }
    //
//
    public String toString(){
        return "["+this.value.getQuantitie()+"]";
    }

    public String printTree(int level) {
        String str = "";
        if(this.value.getCharacter()!= null){
            str =(char) Integer.parseInt(Integer.toString(this.getValue().getCharacter()))+ toString() + "\n";
        }else {
             str = toString() + "\n";
        }


        for (int i = 0; i <= level * 3; i++) {
            str += "\t";
        }
        if (this.getLeft() != null) {

            str += "o-ESQ: " + this.left.printTree(level + 1);
        } else {

            str += "o-ESQ: NULL";
        }
        str += "\n";

        for (int i = 0; i <= level * 3; i++) {
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