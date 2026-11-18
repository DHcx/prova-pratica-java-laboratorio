package siga;

import java.util.List;

/** Contrato usado pelo sistema para consultar os equipamentos. */
public interface FonteEquipamentos {
    List<Equipamento> listarTodos();
}
