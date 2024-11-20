package tests.java;
import main.java.DAO.GenericDAO;
import main.java.domain.Cliente;
import main.java.domain.IEntry;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


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

    @Test
    public void testExcluir() throws Exception {
        dao.cadastrar(cliente);

        Integer delContagem = dao.excluir(cliente);
        assertTrue(delContagem == 1);

    }


    @Test
    public void consultar() throws Exception {
        dao.cadastrar(cliente);

        IEntry clinteBusca = dao.buscar(cliente.getCodigo());
        assertNotNull(clinteBusca);

        dao.excluir(cliente);

    }

    @Test
    public void consultarTodos() throws Exception {
        dao.cadastrar(cliente);
        dao.cadastrar(cliente);
        dao.cadastrar(cliente);
        dao.cadastrar(cliente);

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
        dao.cadastrar(cliente);

        Cliente cliente2 = new Cliente();
        cliente2.setNome("Test");
        cliente2.setCodigo("123456");


        Integer contagem = dao.atualizar(cliente2);
        assertTrue(contagem == 1);

        dao.excluir(cliente);

    }


}
