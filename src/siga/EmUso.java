package siga;

/** Estado de utilização: permite somente devolver o equipamento. */
public class EmUso extends EstadoReserva {

    @Override
    public String getSituacao() {
        return "EM_USO";
    }

    @Override
    public void devolver(Reserva reserva) {
        reserva.alterarEstado(new Encerrada());
    }
}
