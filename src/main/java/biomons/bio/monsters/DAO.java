/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biomons.bio.monsters;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author segpr
 */
public class DAO {

    public boolean existeSala(int codeSala) throws Exception {
        String sql = "SELECT idSala FROM mydb.sala WHERE idSala = ?";
        try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codeSala);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int countPerguntas(int dificuldade, int codeSala) throws Exception {
        String sql = "SELECT count(pergunta) FROM mydb.pergunta WHERE dificuldade_nivelDificuldade = ? AND sala_idSala = ?";
        int count = 0;
        try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dificuldade);
            ps.setInt(2, codeSala);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        }
        return count;
    }

    public Pergunta addPergunta(int dificuldade, int codeSala, int indexPergunta) throws Exception {
        String addPergunta = "WITH PerguntaAtual AS (SELECT pergunta, ROW_NUMBER() OVER (ORDER BY idPergunta ASC) as Perg FROM mydb.pergunta WHERE dificuldade_nivelDificuldade = ? AND sala_idSala = ?) SELECT pergunta FROM PerguntaAtual WHERE Perg = ?";
        String idPergunta = "WITH PerguntaAtual AS (SELECT *, ROW_NUMBER() OVER (ORDER BY idPergunta ASC) as Perg FROM mydb.pergunta WHERE dificuldade_nivelDificuldade = ? AND sala_idSala = ?) SELECT idPergunta FROM PerguntaAtual WHERE Perg = ?";
        String valorPergunta = "erro";
        int idPerguntaFinal = 0;
        try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(addPergunta)) {
            ps.setInt(1, dificuldade);
            ps.setInt(2, codeSala);
            ps.setInt(3, indexPergunta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    valorPergunta = rs.getString(1);
                }
            }
        }
        try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(idPergunta)) {
            ps.setInt(1, dificuldade);
            ps.setInt(2, codeSala);
            ps.setInt(3, indexPergunta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idPerguntaFinal = rs.getInt(1);
                }
            }
        }
        Pergunta pergunta = new Pergunta(dificuldade, valorPergunta, idPerguntaFinal);
        return pergunta;
    }

    public Resposta addResposta(int idPergunta, int indexResposta) throws Exception {
        String addResposta = "WITH RespostaAtual AS (SELECT resposta, ROW_NUMBER() OVER (ORDER BY idResposta ASC) as Resp FROM mydb.resposta WHERE pergunta_idPergunta = ?) SELECT resposta FROM RespostaAtual WHERE Resp = ?";
        String addCorreta = "WITH RespostaAtual AS (SELECT *, ROW_NUMBER() OVER (ORDER BY idResposta ASC) as Resp FROM mydb.resposta WHERE pergunta_idPergunta = ?) SELECT certa FROM RespostaAtual WHERE Resp = ?";
        String addErrada = "WITH RespostaAtual AS (SELECT *, ROW_NUMBER() OVER (ORDER BY idResposta ASC) as Resp FROM mydb.resposta WHERE pergunta_idPergunta = ?) SELECT correcao FROM RespostaAtual WHERE Resp = ?";
        String valorResposta = "erro";
        String correct = "erro";
        boolean certa = false;
        Resposta resp = new Resposta(certa, valorResposta, idPergunta);
        try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(addResposta)) {
            ps.setInt(1, idPergunta);
            ps.setInt(2, indexResposta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    valorResposta = rs.getString(1);
                }
            }
        }
        try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(addCorreta)) {
            ps.setInt(1, idPergunta);
            ps.setInt(2, indexResposta);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    certa = rs.getBoolean(1);
                }
            }
        }
        if (!certa) {
            try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(addErrada)) {
                ps.setInt(1, idPergunta);
                ps.setInt(2, indexResposta);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        correct = rs.getString(1);
                    }
                }
            }
            resp.setCorrect(correct);
        }
        resp.setCerta(certa);
        resp.setResposta(valorResposta);
        resp.setIdPergunta(idPergunta);
        return resp;
    }

    public Inimigo addInimigo(int dificuldade) throws Exception {
        String addNome = "SELECT nomeInimigo FROM mydb.inimigo WHERE dificuldade_nivelDificuldade = ?";
        String addDesc = "SELECT infoInimigo FROM mydb.inimigo WHERE dificuldade_nivelDificuldade = ?";
        String nome = "erro";
        String desc = "erro";
        try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(addNome)) {
            ps.setInt(1, dificuldade);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nome = rs.getString(1);
                }
            }
        }
        try (Connection conn = Conexao.obterConexao(); PreparedStatement ps = conn.prepareStatement(addDesc)) {
            ps.setInt(1, dificuldade);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    desc = rs.getString(1);
                }
            }
        }
        Inimigo inimigo = new Inimigo(dificuldade, nome, desc);
        return inimigo;
    }
}
