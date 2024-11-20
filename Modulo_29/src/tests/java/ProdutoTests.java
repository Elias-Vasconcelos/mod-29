package tests.java;


import main.java.DAO.GenericDAO;
import main.java.domain.Cliente;
import main.java.domain.IEntry;
import main.java.domain.Produto;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ProdutoTests {

    Produto Produto = new Produto();
    GenericDAO dao = new GenericDAO(Produto) ;


    @Before
    public void instanciacao(){
        Produto.setNome("Elias Ferrerira");
        Produto.setCodigo("123456");
    }

    @Test
    public void testCadastro() throws Exception {
        Integer contagem = dao.cadastrar(Produto);
        assertTrue(contagem == 1);

        dao.excluir(Produto);
    }


    @Test
    public void testExcluir() throws Exception {
        dao.cadastrar(Produto);

        Integer delContagem = dao.excluir(Produto);
        assertTrue(delContagem == 1);

    }

    @Test
    public void consultar() throws Exception {
        dao.cadastrar(Produto);

        IEntry ProdutoBusca = dao.buscar(Produto.getCodigo());
        assertNotNull(ProdutoBusca);

    }

    @Test
    public void consultarTodos() throws Exception {
        dao.cadastrar(Produto);
        dao.cadastrar(Produto);
        dao.cadastrar(Produto);
        dao.cadastrar(Produto);

        List<IEntry> lista = dao.buscarTodos();

        assertNotNull(lista);


        int countDel = 0;
        for(IEntry entry : lista){
            dao.excluir(entry);
            countDel++;
        }

        assertEquals(lista.size(), countDel);

    }

    @Test
    public void atualizar() throws Exception {
        dao.cadastrar(Produto);

        Produto Produto2 = new Produto();
        Produto2.setNome("Test");
        Produto2.setCodigo("123456");


        Integer contagem = dao.atualizar(Produto2);
        assertTrue(contagem == 1);

        dao.excluir(Produto);

    }



}
