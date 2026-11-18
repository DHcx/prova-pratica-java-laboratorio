package siga;

/** Estado inicial: permite retirar ou cancelar a reserva. */
public class Solicitada extends EstadoReserva {

    @Override
    public String getSituacao() {
        return "SOLICITADA";
    }

    @Override
    public void retirar(Reserva reserva) {
        reserva.alterarEstado(new EmUso());
    }

    @Override
    public void cancelar(Reserva reserva) {
        reserva.alterarEstado(new Encerrada());
    }
}
