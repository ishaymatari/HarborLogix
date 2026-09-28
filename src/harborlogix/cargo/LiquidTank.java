package harborlogix.cargo;

import harborlogix.clients.Client;
import harborlogix.ops.TariffPolicy;

public class LiquidTank extends CargoUnit {

    private final double capacityLitres;
    private double fillPercent;

    public LiquidTank(String unitId, Client owner, double weightKg,
                      int daysStored, double capacityLitres, double fillPercent) {
        super(unitId, owner, weightKg, daysStored);
        if (capacityLitres <= 0) {
            throw new IllegalArgumentException("Capacity must be positive, got " + capacityLitres);
        }
        if (fillPercent < 0.0 || fillPercent > 100.0) {
            throw new IllegalArgumentException("Fill percent must be between 0 and 100, got " + fillPercent);
        }
        this.capacityLitres = capacityLitres;
        this.fillPercent = fillPercent;
    }

    public double getCapacityLitres() { return capacityLitres; }
    public double getFillPercent()    { return fillPercent; }

    public double currentLitres() {
        return capacityLitres * (fillPercent / 100.0);
    }

    public double transferOut(double litres) {
        if (litres <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive, got " + litres);
        }
        double current = currentLitres();
        double moved = Math.min(current, litres);
        double remaining = current - moved;
        this.fillPercent = (remaining / capacityLitres) * 100.0;
        return moved;
    }

    @Override
    public double dailyStorageFee() {
        return TariffPolicy.LIQUID_RATE * currentLitres();
    }

    @Override
    public String handlingCategory() {
        return "Tank";
    }

    @Override
    public String safetyBriefing() {
        return "Liquid Tank: Verify valve seals and pressure relief systems before transfer.";
    }

    @Override
    public String toString() {
        return String.format("%s, cap=%.0fL, fill=%.1f%%]", super.toString().substring(0, super.toString().length() - 1), capacityLitres, fillPercent);
    }
}