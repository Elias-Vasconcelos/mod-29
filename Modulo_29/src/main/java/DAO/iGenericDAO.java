package main.java.DAO;
import java.util.List;

public interface iGenericDAO<T> {

    public Integer cadastrar(T entry) throws Exception;

    public Integer atualizar(T entry) throws Exception;

    public T buscar(String codigo) throws Exception;

    public List<T> buscarTodos() throws Exception;

    public Integer excluir(T entry) throws Exception;


}
