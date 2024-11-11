package org.example;

public class _EntidadeBase {

    private int id;

    public _EntidadeBase() {
    }

    public _EntidadeBase(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "_EntidadeBase{" +
                "id=" + id +
                '}';
    }
}
