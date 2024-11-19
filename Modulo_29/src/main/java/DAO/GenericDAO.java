package main.java.DAO;

import main.java.DAO.JDBC.ConectionBD;
import main.java.domain.Cliente;
import main.java.domain.IEntry;
import main.java.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            setComando(stm, entry);
            return stm.executeUpdate();

        } catch (Exception e ){
            throw e;
        } finally {
            closeConnection(conection, stm, null);
        }
    }

    @Override
    public Integer atualizar(T entry) throws Exception {
        return 0;
    }

    @Override
    public T buscar(String codigo) throws Exception {
        return null;
    }

    @Override
    public List<T> buscarTodos() throws Exception {
        return List.of();
    }

    @Override
    public Integer excluir(T entry) throws Exception {
        return 0;
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

    private void setComando(PreparedStatement stm, T entry ) throws SQLException {
        stm.setString(1, entry.getCodigo());
        stm.setString(2, entry.getNome());
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
