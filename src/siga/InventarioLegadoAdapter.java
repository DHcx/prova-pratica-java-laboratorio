package siga;

import java.util.ArrayList;
import java.util.List;

/** Converte o formato do serviço legado para objetos do sistema. */
public class InventarioLegadoAdapter implements FonteEquipamentos {

    private final InventarioLegadoWS legado;

    public InventarioLegadoAdapter(InventarioLegadoWS legado) {
        this.legado = legado;
    }

    @Override
    public List<Equipamento> listarTodos() {
        List<Equipamento> equipamentos = new ArrayList<>();
        for (String[] linha : legado.consultarEquipamentos()) {
            Equipamento item = new Equipamento(linha[0], linha[1]);
            item.setDisponivel("D".equalsIgnoreCase(linha[2]));
            equipamentos.add(item);
        }
        return equipamentos;
    }
}
