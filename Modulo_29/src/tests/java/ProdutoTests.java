package tests.java;


import main.java.DAO.GenericDAO;
import main.java.domain.Produto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
    }

}
