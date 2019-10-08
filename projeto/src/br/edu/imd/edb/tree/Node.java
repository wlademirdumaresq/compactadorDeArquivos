package br.edu.imd.edb.tree;

import br.edu.imd.edb.entities.Char;
public class Node {


    private Char value;
    private Node left;
    private Node right;
    private Integer balanceamento;
    private boolean chave;


    //construtores

    public Node() {

        this.value = null;
        this.left = null;
        this.right = null;
        this.balanceamento = null;
    }

    public Node(Char value) {
        this.value = value;
        this.left = null;
        this.right = null;
        this.balanceamento = null;
    }


    //Gets && Sets


    public Integer getBalanceamento() {
        return balanceamento;
    }

    public void setBalanceamento(Integer balanceamento) {
        this.balanceamento = balanceamento;
    }

    public Char getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }


    //Metodos



    public void insert(Node node) {

        if (node.value.getValue() < this.value.getValue()) { // inserir a esqueda
            if (this.left == null) { // esqueda é uma folha?
                this.left = node;
            } else {
                this.left.insert(node);
            }
        } else if (node.value.getValue() > this.value.getValue()) { // inserir a direita
            if (this.right == null) { // direita é uma folha?
                this.right = node;
            } else {
                this.right.insert(node);
            }
        }

    }

    public Node search(int key) {
        if (key == this.value.getValue()) {
            return this;
        }

        if (key < this.value.getValue()) {
            if (this.left != null) {
                return this.left.search(key);
            }
        }

        if (key > this.value.getValue()) {
            if (this.right != null) {
                return this.right.search(key);
            }
        }

        return null;
    }

    public void preOrdem() {

        System.out.print(this.value + " ");
        if (this.left != null) {
            this.left.preOrdem();
        }
        if (this.right != null) {
            this.right.preOrdem();
        }

    }

    public void inOrdem() {

        if (this.left != null) {
            this.left.inOrdem();
        }
        System.out.print(this.value + " ");

        if (this.right != null) {
            this.right.inOrdem();
        }

    }

    public void posOrdem() {

        if (this.right != null) {
            this.right.posOrdem();
        }
        if (this.left != null) {
            this.left.posOrdem();
        }
        System.out.print(this.value + " ");

    }

    public Node remove(int value) {

        if (this.value.getValue() == value) {

            if (this.right == null && this.left == null) {
                return null;
            } else {

                if (this.right == null && this.left != null) {
                    return this.left;
                } else if (this.right != null && this.left == null) {
                    return this.right;
                } else {
                    Node aux = this.left;
                    while (aux.right != null) {
                        aux = aux.right;
                    }

                    this.value = aux.getValue();
                    aux.value.setValue(value);
                    this.left = left.remove(value);

                }
            }


        } else if (this.value.getValue() > value) {

            this.left = this.left.remove(value);

        } else if (this.value.getValue() < value) {

            this.right = this.right.remove(value);
        }

        return this;

    }

    public String toString() {
        if (this.getBalanceamento() == null) {
            return "[" + this.getValue().getValue() + "]";
        } else {
            return "[" + this.getValue().getValue() + "]" + "( balanceamento :" + this.getBalanceamento() + ")";
        }
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

    public int calcAltura() {

        if (this.left == null && this.right == null) {
            return 1;
        } else if (this.left != null && this.right == null) {
            return 1 + this.left.calcAltura();
        } else if (this.left == null && this.right != null) {
            return 1 + this.right.calcAltura();
        } else {

            return 1 + Math.max(this.left.calcAltura(), this.right.calcAltura());
        }

    }

    public void calcularBalanceamento() {
        if (this.left == null && this.right == null) {
            this.balanceamento = 0;
        } else if (this.left == null && this.right != null) {
            this.balanceamento = this.right.calcAltura() - 0;
        } else if (this.left != null && this.right == null) {
            this.balanceamento = 0 - this.left.calcAltura();
        } else {

            this.balanceamento = this.right.calcAltura() - this.left.calcAltura();
        }

        if (this.right != null) {
            this.right.calcularBalanceamento();
        }
        if (this.left != null) {
            this.left.calcularBalanceamento();
        }
    }


}
