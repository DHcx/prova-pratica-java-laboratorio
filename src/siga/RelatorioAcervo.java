package siga;

import java.util.List;

/** Apresenta o acervo recebido pela abstração FonteEquipamentos. */
public class RelatorioAcervo {

    private final FonteEquipamentos fonte;

    public RelatorioAcervo(FonteEquipamentos fonte) {
        this.fonte = fonte;
    }

    public List<Equipamento> listarTodos() {
        return fonte.listarTodos();
    }

    public void imprimir() {
        System.out.println("   Situacao segundo o relatorio:");
        for (Equipamento item : listarTodos()) {
            System.out.println("     " + item + " - "
                    + (item.isDisponivel() ? "disponivel" : "baixado"));
        }
    }
}
