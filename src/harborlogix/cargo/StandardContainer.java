package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

public class StandardContainer extends CargoUnit {

    private final double volumeM3;

    public StandardContainer(String unitId, Client owner, double weightKg,
                             int daysStored, double volumeM3) {
        super(unitId, owner, weightKg, daysStored);
        if (volumeM3 <= 0) {
            throw new IllegalArgumentException("Volume must be positive, got " + volumeM3);
        }
        this.volumeM3 = volumeM3;
    }

    public double getVolumeM3() {
        return volumeM3;
    }

    @Override
    public double dailyStorageFee() {
        return TariffPolicy.BASE_STORAGE_RATE * volumeM3;
    }

    @Override
    public String handlingCategory() {
        return "Standard";
    }

    @Override
    public String safetyBriefing() {
        return "Standard container: Ensure standard twist-lock securing on deck/yard.";
    }

    @Override
    public String toString() {
        return String.format("%s, vol=%.1fm3]", super.toString().substring(0, super.toString().length() - 1), volumeM3);
    }
}