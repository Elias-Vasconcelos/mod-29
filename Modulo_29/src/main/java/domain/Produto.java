package main.java.domain;

public class Produto implements IEntry {

    private Long id;

    private String codigo;

    private String nome;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long setId(Long id) {
        return this.id = id;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String setNome(String nome) {
        return this.nome = nome;
    }

    @Override
    public String getCodigo() {
        return codigo;
    }

    @Override
    public String setCodigo(String Codigo) {
        return this.codigo = Codigo;
    }
}
