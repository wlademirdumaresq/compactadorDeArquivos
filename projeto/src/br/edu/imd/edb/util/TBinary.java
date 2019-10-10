package br.edu.imd.edb.util;

import br.edu.imd.edb.tree.Node;

public class TBinary {

    private static String tableCode = "";

    public static String[] LBinary(Node no) {
        tableCode = LBinary(no, "");
        return tableCode.split("=");
    }

    private static String LBinary(Node no, String codigo) {
        if (no.getLeft() == no.getRight()) {
            tableCode+= no.getValue().getCharacter() + "=" + codigo + "=" ;
            return tableCode;
        } else {
            LBinary(no.getRight(), codigo + Integer.toString(no.getRight().getBit()));
            LBinary(no.getLeft(), codigo + Integer.toString(no.getLeft().getBit()));
        }
        return tableCode;

    }

}
