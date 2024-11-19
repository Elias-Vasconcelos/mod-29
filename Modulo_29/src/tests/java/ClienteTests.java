package tests.java;
import main.java.DAO.GenericDAO;
import main.java.domain.Cliente;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class ClienteTests {

    Cliente cliente = new Cliente();
    GenericDAO dao = new GenericDAO(cliente) ;


    @Before
    public void instanciacao(){
        cliente.setNome("Elias Ferrerira");
        cliente.setCodigo("123456");
    }

    @Test
    public void testCadastro() throws Exception {

        Integer contagem = dao.cadastrar(cliente);
        assertTrue(contagem == 1);
    }

}
