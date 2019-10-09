package br.edu.imd.edb.application;

import br.edu.imd.edb.heap.Heap;
import br.edu.imd.edb.tree.Node;

public class Main {

    public static void main(String[] args) {

        Node node1 = new Node('l', 4);
        Node node2 = new Node('o', 3);
        Node node3 = new Node('a', 3);
        Node node4 = new Node('p', 1);
        Node node5 = new Node('z', 1);

        Heap heap = new Heap();
        heap.insert(node1);
        heap.insert(node2);
        heap.insert(node3);
        heap.insert(node4);
        heap.insert(node5);

        Node tree = new Node();

         do{
            if(heap.getSize() == 0){
                break;
            }
            Node left = heap.peek();
            heap.remove();
            Node right = heap.peek();
            heap.remove();
            tree = new Node(left.getValue().getQuantitie()+right.getValue().getQuantitie(),left,right);
            heap.insert(tree);
        }while (heap.getSize() > 1);

        System.out.println(tree.printTree(0));







    }
}
