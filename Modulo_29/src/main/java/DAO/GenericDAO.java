package main.java.DAO;

import main.java.DAO.JDBC.ConectionBD;
import main.java.domain.Cliente;
import main.java.domain.IEntry;
import main.java.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenericDAO<T extends IEntry > implements iGenericDAO<T> {

    IEntry entry;

    public GenericDAO(T entry){
        this.entry = entry;
    }

    @Override
    public Integer cadastrar(T entry) throws Exception {

        Connection conection = null;
        PreparedStatement stm = null;

        try {
            conection = ConectionBD.getConnection();
            String comando = cadastroSql(entry);
            stm = conection.prepareStatement(comando);
            setComandoCadastro(stm, entry);
            return stm.executeUpdate();

        } catch (Exception e ){
            throw e;
        } finally {
            closeConnection(conection, stm, null);
        }
    }

    @Override
    public Integer atualizar(T entry) throws Exception {


        Connection conection = null;
        PreparedStatement stm = null;

        try {
            conection = ConectionBD.getConnection();
            String comando = atualizarSql(entry);
            stm = conection.prepareStatement(comando);
            setComandoAtualizar(stm, entry);
            return stm.executeUpdate();


        } catch (Exception e ){
            throw e;
        } finally {
            closeConnection(conection, stm, null);
        }
    }

    @Override
    public T buscar(String codigo) throws Exception {

        Connection conection = null;
        PreparedStatement stm = null;
        ResultSet resposta = null;
        IEntry entryRS = null;

        try {
            conection = ConectionBD.getConnection();
            String comando = buscarClientetSql(codigo);
            stm = conection.prepareStatement(comando);
            setComandobuscar(stm, codigo);
            resposta = stm.executeQuery();

            if (resposta.next()){

                entryRS = new Cliente();
                Long id = resposta.getLong("ID");
                String codigoCliente = resposta.getString("codigo");
                String nomeCliente = resposta.getString("nome");

                entryRS.setId(id);
                entryRS.setCodigo(codigoCliente);
                entryRS.setNome(nomeCliente);

                return (T) entryRS;

            } else {
                comando = buscarProdutoSql(codigo);
                stm = conection.prepareStatement(comando);
                setComandobuscar(stm, codigo);
                resposta = stm.executeQuery();

                if (resposta.next()) {

                    entryRS = new Produto();
                    Long id = resposta.getLong("ID");
                    String codigoCliente = resposta.getString("codigo");
                    String nomeCliente = resposta.getString("nome");

                    entryRS.setId(id);
                    entryRS.setCodigo(codigoCliente);
                    entryRS.setNome(nomeCliente);

                    return (T) entryRS;
                }
            }
        } catch (Exception e ){
            throw e;
        } finally {
            closeConnection(conection, stm, null);
        }




        return (T) entryRS;
    }

    @Override
    public List<T> buscarTodos() throws Exception {


        Connection conection = null;
        PreparedStatement stm = null;
        ResultSet resposta = null;
        IEntry entryRS = null;

        try {
            conection = ConectionBD.getConnection();
            String comando = buscartodosClientesSql();
            stm = conection.prepareStatement(comando);
            resposta = stm.executeQuery();
            List<IEntry> list = new ArrayList<>();


            while (resposta.next() ){

                entryRS = new Cliente();
                Long id = resposta.getLong("ID");
                String codigoCliente = resposta.getString("codigo");
                String nomeCliente = resposta.getString("nome");

                entryRS.setId(id);
                entryRS.setCodigo(codigoCliente);
                entryRS.setNome(nomeCliente);

                list.add(entryRS);
            }



            comando = buscartodosProdutosSql();
            stm = conection.prepareStatement(comando);
            resposta = stm.executeQuery();


            while (resposta.next() ){

                entryRS = new Produto();
                Long id = resposta.getLong("ID");
                String codigoCliente = resposta.getString("codigo");
                String nomeCliente = resposta.getString("nome");

                entryRS.setId(id);
                entryRS.setCodigo(codigoCliente);
                entryRS.setNome(nomeCliente);

                list.add(entryRS);
            }

            return (List<T>) list;
        }

        catch (Exception e ){
            throw e;
        } finally {
            closeConnection(conection, stm, null);
        }
    }

    private String buscartodosProdutosSql() {

        StringBuilder comando = new StringBuilder();

        comando.append("SELECT * FROM ProdutoTB ");

        return comando.toString();

    }

    private String buscartodosClientesSql() {

        StringBuilder comando = new StringBuilder();

        comando.append("SELECT * FROM ClienteTB ");

        return comando.toString();
    }

    @Override
    public Integer excluir(T entry) throws Exception {

        Connection conection = null;
        PreparedStatement stm = null;

        try {
            conection = ConectionBD.getConnection();
            String comando = excluirSql(entry);
            stm = conection.prepareStatement(comando);
            setComandoexcluir(stm, entry);
            return stm.executeUpdate();

        } catch (Exception e ){
            throw e;
        } finally {
            closeConnection(conection, stm, null);
        }
}

    private String cadastroSql(T entry ) {
        StringBuilder comando = new StringBuilder();

        if( entry.getClass().equals(Cliente.class) ) {
            comando.append("INSERT INTO ClienteTB ( ID, codigo, nome )");
            comando.append("VALUES (nextval('id_Cliente'),?,?)");
            return comando.toString();
        }

        if (entry.getClass().equals(Produto.class) ) {
            comando.append("INSERT INTO ProdutoTB ( ID, codigo, nome )");
            comando.append("VALUES (nextval('id_Produto'),?,?)");
            return comando.toString();
        }

        return comando.toString();
    }

    private void setComandoCadastro(PreparedStatement stm, T entry ) throws SQLException {
        stm.setString(1, entry.getCodigo());
        stm.setString(2, entry.getNome());
    }

    private String excluirSql(T entry){

        StringBuilder comando = new StringBuilder();

        if( entry.getClass().equals(Cliente.class) ) {
            comando.append("DELETE FROM ClienteTB WHERE codigo = ?; ");
            return comando.toString();
        }

        if (entry.getClass().equals(Produto.class) ) {
            comando.append("DELETE FROM ProdutoTB WHERE codigo = ?; ");
            return comando.toString();
        }

        return comando.toString();
    }

    private void setComandoexcluir(PreparedStatement stm, T entry ) throws SQLException {
        stm.setString(1, entry.getCodigo());
    }

    private String buscarClientetSql(String codigo) {

        StringBuilder comando = new StringBuilder();

        comando.append("SELECT * FROM ClienteTB ");
        comando.append("WHERE codigo = ?");

       return comando.toString();

    }

    private String buscarProdutoSql(String codigo) {

        StringBuilder comando = new StringBuilder();

        comando.append("SELECT * FROM ProdutoTB ");
        comando.append("WHERE codigo = ?");

        return comando.toString();

    }

    private void setComandobuscar(PreparedStatement stm, String codigo ) throws SQLException{
        stm.setString(1, codigo);
    }

    private String atualizarSql(T entry) {

        StringBuilder comando = new StringBuilder();

        if( entry.getClass().equals(Cliente.class) ) {

            comando.append("UPDATE ClienteTB SET nome = ? WHERE codigo = ? ");

            return comando.toString();
        }

        if (entry.getClass().equals(Produto.class) ) {
            comando.append("UPDATE ProdutoTB SET nome = ? WHERE codigo = ? ");

            return comando.toString();
        }

        return comando.toString();

    }


    private void setComandoAtualizar(PreparedStatement stm, T entry ) throws SQLException {
        stm.setString(1, entry.getNome());
        stm.setString(2, entry.getCodigo());
    }


    private void closeConnection(Connection connection, PreparedStatement stm, ResultSet rs) {
        try {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
            if (stm != null && !stm.isClosed()) {
                stm.close();
            }
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}