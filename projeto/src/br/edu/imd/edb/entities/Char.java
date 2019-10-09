package br.edu.imd.edb.entities;

public class Char {
    private Character character;
    private Integer quantitie;

    public Char(Character character, Integer quantitie) {
        this.character = character;
        this.quantitie = quantitie;
    }
    public Char(Integer quantitie) {
        this.character = null;
        this.quantitie = quantitie;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Integer getQuantitie() {
        return quantitie;
    }

    public void setQuantitie(Integer quantitie) {
        this.quantitie = quantitie;
    }
}

