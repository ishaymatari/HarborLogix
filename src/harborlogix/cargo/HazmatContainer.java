package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

public class HazmatContainer extends StandardContainer {

    private final int hazardClass;
    private final boolean requiresEscort;

    public HazmatContainer(String unitId, Client owner, double weightKg,
                           int daysStored, double volumeM3,
                           int hazardClass, boolean requiresEscort) {
        super(unitId, owner, weightKg, daysStored, volumeM3);
        if (hazardClass < 1 || hazardClass > 9) {
            throw new IllegalArgumentException("Hazard class must be between 1 and 9, got " + hazardClass);
        }
        this.hazardClass = hazardClass;
        this.requiresEscort = requiresEscort;
    }

    public int getHazardClass() { return hazardClass; }
    public boolean isRequiresEscort() { return requiresEscort; }

    @Override
    public double dailyStorageFee() {
        return super.dailyStorageFee() * TariffPolicy.HAZMAT_MULTIPLIER;
    }

    @Override
    public String handlingCategory() {
        return "Hazmat";
    }

    @Override
    public String safetyBriefing() {
        return String.format("HAZMAT Class %d: Handle with protective gear. Escort required: %b.", hazardClass, requiresEscort);
    }

    @Override
    public String toString() {
        return String.format("%s, hazClass=%d, escort=%b]", super.toString().substring(0, super.toString().length() - 1), hazardClass, requiresEscort);
    }
}