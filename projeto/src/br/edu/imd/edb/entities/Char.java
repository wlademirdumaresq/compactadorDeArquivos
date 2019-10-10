package br.edu.imd.edb.entities;

public class Char {
    private Integer character;
    private Integer quantitie;

    public Char(Integer character, Integer quantitie) {
        this.character = character;
        this.quantitie = quantitie;
    }
    public Char(Integer quantitie) {
        this.character = null;
        this.quantitie = quantitie;
    }

    public Integer getCharacter() {


        return character;
    }

    public void setCharacter(Integer character) {
        this.character = character;
    }

    public Integer getQuantitie() {
        return quantitie;
    }

    public void setQuantitie(Integer quantitie) {
        this.quantitie = quantitie;
    }


}