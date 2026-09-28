package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

public class RefrigeratedContainer extends StandardContainer {

    private final double targetTempC;
    private final double powerDrawKw;

    public RefrigeratedContainer(String unitId, Client owner, double weightKg,
                                 int daysStored, double volumeM3,
                                 double targetTempC, double powerDrawKw) {
        super(unitId, owner, weightKg, daysStored, volumeM3);
        if (targetTempC > 8.0) {
            throw new IllegalArgumentException("Target temperature must be 8.0 C or below, got " + targetTempC);
        }
        if (powerDrawKw <= 0) {
            throw new IllegalArgumentException("Power draw must be positive, got " + powerDrawKw);
        }
        this.targetTempC = targetTempC;
        this.powerDrawKw = powerDrawKw;
    }

    public double getTargetTempC() { return targetTempC; }
    public double getPowerDrawKw() { return powerDrawKw; }

    @Override
    public double dailyStorageFee() {
        return super.dailyStorageFee() + (TariffPolicy.POWER_RATE * powerDrawKw);
    }

    @Override
    public String handlingCategory() {
        return "Reefer";
    }

    @Override
    public String safetyBriefing() {
        return String.format("Reefer: Maintain power grid connection. Target temp: %.1f C.", targetTempC);
    }

    @Override
    public String toString() {
        return String.format("%s, temp=%.1fC, kw=%.1f]", super.toString().substring(0, super.toString().length() - 1), targetTempC, powerDrawKw);
    }
}