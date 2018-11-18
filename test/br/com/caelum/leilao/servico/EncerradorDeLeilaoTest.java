package br.com.caelum.leilao.servico;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class EncerradorDeLeilaoTest {

    @Test
    public void deveEncerrarLeiloesQueComecaramUmaSemanaAtras() {

        Calendar antiga = Calendar.getInstance();
        antiga.set(1999, 1, 20);

        Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
                .naData(antiga).constroi();
        Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
                .naData(antiga).constroi();

        LeilaoDao dao = new LeilaoDao();
        dao.salva(leilao1);
        dao.salva(leilao2);

        EncerradorDeLeilao encerrador = new EncerradorDeLeilao(dao);
        encerrador.encerra();

        // busca no banco a lista de encerrados
        List<Leilao> encerrados = dao.encerrados();

        // vamos conferir tambem o tamanho da lista!
        assertEquals(2, encerrados.size());
        assertTrue(encerrados.get(0).isEncerrado());
        assertTrue(encerrados.get(1).isEncerrado());
    }


}
