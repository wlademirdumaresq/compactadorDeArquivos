package br.edu.imd.edb.application;

import br.edu.imd.edb.entities.Char;
import br.edu.imd.edb.heap.Heap;
import br.edu.imd.edb.tree.Tree;

public class Main {

    public static void main(String[] args) {

        Tree tree = new Tree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(5);
        tree.insert(2);
        tree.insert(8);
        tree.insert(15);
        tree.insert(12);
        tree.insert(18);
        tree.insert(30);
        tree.insert(25);
        tree.insert(22);
        tree.insert(28);
        tree.insert(35);
        tree.insert(32);
        tree.insert(38);

        System.out.println(tree.getList());

    }
}
