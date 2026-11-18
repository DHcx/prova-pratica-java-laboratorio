package siga;

/** Comportamentos padrão: uma operação não permitida lança exceção. */
public abstract class EstadoReserva {

    public abstract String getSituacao();

    public void retirar(Reserva reserva) {
        throw new IllegalStateException(
                "Nao e possivel retirar em " + getSituacao() + ".");
    }

    public void devolver(Reserva reserva) {
        throw new IllegalStateException(
                "Nao e possivel devolver em " + getSituacao() + ".");
    }

    public void cancelar(Reserva reserva) {
        throw new IllegalStateException(
                "Nao e possivel cancelar em " + getSituacao() + ".");
    }
}
