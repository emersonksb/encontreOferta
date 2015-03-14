package br.com.encontreoferta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PromocaoDao {

    private Conexao conexao;
    private ResultSet resultado;

    Promocao selecionarPorId(int id) {
        if (this.conexao == null) {
            this.conexao = new Conexao();
        }

        this.resultado = conexao.consultar("Select * from promocao where id = " + id);
        try {
            resultado.next();
            Promocao promocao = new Promocao(
                    id, resultado.getInt("idVendedor"),
                    resultado.getString("titulo"), resultado.getString("descricao"),
                    resultado.getBigDecimal("valor"), resultado.getString("imagem"),
                    resultado.getInt("quantidade"), resultado.getDate("tempo")
            );
            return promocao;
        } catch (SQLException ex) {
            return null;
        }
    }

    List<Promocao> selecionarTodos() {
        if (this.conexao == null) {
            this.conexao = new Conexao();
        }
        this.resultado = conexao.consultar("Select * from promocao");
        try {
            List<Promocao> lista = new ArrayList<>();
            while (resultado.next()) {
                Promocao promocao = new Promocao(
                        resultado.getInt("id"), resultado.getInt("idVendedor"),
                        resultado.getString("titulo"), resultado.getString("descricao"),
                        resultado.getBigDecimal("valor"), resultado.getString("imagem"),
                        resultado.getInt("quantidade"), resultado.getDate("tempo")
                );
                lista.add(promocao);
            }
            return lista;
        } catch (SQLException ex) {
            return null;
        }
    }

    boolean inserir(Promocao promocao) {
        if (this.conexao == null) {
            this.conexao = new Conexao();
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return conexao.executar(String.format(
                "Insert into promocao(idVendedor, titulo, descricao, valor, "
                + "imagem, quantidade, tempo) values(%d, '%s', '%s', %d, '%s', %d, '%s')",
                promocao.getIdVendedor(), promocao.getTitulo(), promocao.getDescricao(),
                promocao.getValor(), promocao.getImagem(), promocao.getQuantidade(),
                formato.format(promocao.getTempo())
        ));
    }

    boolean alterar(Promocao promocao) {
        if (this.conexao == null) {
            this.conexao = new Conexao();
        }

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        return conexao.executar(String.format(
                "Update promocao set idVendedor = %d, titulo = '%s', descricao = '%s', "
                + "valor = %d, imagem = '%s', quantidade = %d, tempo = '%s'"
                + "where id = %d",
                promocao.getIdVendedor(), promocao.getTitulo(), promocao.getDescricao(),
                promocao.getValor(), promocao.getImagem(), promocao.getQuantidade(),
                formato.format(promocao.getTempo()), promocao.getIdPromocao()
        ));
    }
    
    boolean apagar(Promocao promocao){
        if (this.conexao == null) {
            this.conexao = new Conexao();
        }
        return conexao.executar("delete from promocao where id = "+promocao.getIdPromocao());
    }
}