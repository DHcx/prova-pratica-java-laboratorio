package siga;

/** Estado final: não permite nenhuma outra operação. */
public class Encerrada extends EstadoReserva {

    @Override
    public String getSituacao() {
        return "ENCERRADA";
    }
}
