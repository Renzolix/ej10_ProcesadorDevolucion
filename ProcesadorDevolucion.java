import java.util.ArrayList;

public class ProcesadorDevolucion {
    private ArrayList<String> prestamosActivos = new ArrayList<>();

    public void agregarPrestamoActivo(String idPrestamo) {
        prestamosActivos.add(idPrestamo);
    }

    public void registrarDevolucion(String idPrestamo, double multaPendiente) throws PrestamoInexistenteException {
        if (multaPendiente > 0.0) {
            throw new ArancelPendienteException("No se puede registrar la devolución. El usuario registra un arancel/multa pendiente de $" + multaPendiente);
        }
        if (!prestamosActivos.contains(idPrestamo)) {
            throw new PrestamoInexistenteException("El ID de préstamo '" + idPrestamo + "' no se encuentra activo en el sistema.");
        }

        prestamosActivos.remove(idPrestamo);
        System.out.println("Devolución registrada exitosamente. Préstamo '" + idPrestamo + "' cerrado.");
    }

    public static void main(String[] args) {
        ProcesadorDevolucion procesador = new ProcesadorDevolucion();
        procesador.agregarPrestamoActivo("PRES-101");
        procesador.agregarPrestamoActivo("PRES-102");

        System.out.println("--- Caso 1: Multa pendiente ---");
        try {
            procesador.registrarDevolucion("PRES-101", 1500.0);
        } catch (PrestamoInexistenteException e) {
            System.err.println("Error de Registro: " + e.getMessage());
        } catch (ArancelPendienteException e) {
            System.err.println("Error Financiero (Runtime): " + e.getMessage());
        } finally {
            System.out.println("Registro de auditoría de devolución cerrado correctamente.\n");
        }

        System.out.println("--- Caso 2: Préstamo inexistente ---");
        try {
            procesador.registrarDevolucion("PRES-999", 0.0);
        } catch (PrestamoInexistenteException e) {
            System.err.println("Error de Registro: " + e.getMessage());
        } catch (ArancelPendienteException e) {
            System.err.println("Error Financiero (Runtime): " + e.getMessage());
        } finally {
            System.out.println("Registro de auditoría de devolución cerrado correctamente.\n");
        }

        System.out.println("--- Caso 3: Devolución exitosa ---");
        try {
            procesador.registrarDevolucion("PRES-102", 0.0);
        } catch (PrestamoInexistenteException e) {
            System.err.println("Error de Registro: " + e.getMessage());
        } catch (ArancelPendienteException e) {
            System.err.println("Error Financiero (Runtime): " + e.getMessage());
        } finally {
            System.out.println("Registro de auditoría de devolución cerrado correctamente.");
        }
    }
}