package harborlogix.ops;

import harborlogix.cargo.CargoUnit;
import harborlogix.clients.Client;
import java.util.ArrayList;

public class Yard {

    private final String yardName;
    private final int capacity;
    private final ArrayList<CargoUnit> units = new ArrayList<>();

    public Yard(String yardName, int capacity) {
        if (yardName == null || yardName.trim().isEmpty()) {
            throw new IllegalArgumentException("Yard name cannot be null or empty.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        this.yardName = yardName;
        this.capacity = capacity;
    }

    public String getYardName() { return yardName; }
    public int getCapacity()    { return capacity; }
    public int getUnitCount()   { return units.size(); }

    public ArrayList<CargoUnit> getUnits() {
        return new ArrayList<>(units);
    }

    public void receive(CargoUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Cargo unit cannot be null.");
        }
        if (units.size() >= capacity) {
            throw new IllegalStateException("Yard capacity reached.");
        }
        for (CargoUnit existingUnit : units) {
            if (existingUnit.getUnitId().equals(unit.getUnitId())) {
                throw new IllegalArgumentException("Duplicate unit ID: " + unit.getUnitId());
            }
        }
        units.add(unit);
    }

    public double totalDailyRevenue() {
        double total = 0.0;
        for (CargoUnit unit : units) {
            total += unit.dailyStorageFee();
        }
        return total;
    }

    public double invoiceFor(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client cannot be null.");
        }
        double rawTotal = 0.0;
        for (CargoUnit unit : units) {
            if (unit.getOwner() != null && client.getClientId().equals(unit.getOwner().getClientId())) {
                rawTotal += unit.totalStorageCharge();
            }
        }
        double discountRate = client.discountPercent() / 100.0;
        return rawTotal * (1.0 - discountRate);
    }

    public CargoUnit heaviestUnit() {
        if (units.isEmpty()) {
            return null;
        }
        CargoUnit heaviest = units.get(0);
        for (CargoUnit unit : units) {
            if (unit.getWeightKg() > heaviest.getWeightKg()) {
                heaviest = unit;
            }
        }
        return heaviest;
    }

    public void printManifest() {
        System.out.println("--- Yard Manifest: " + yardName + " ---");
        for (CargoUnit unit : units) {
            System.out.println(unit.toString() + " | Safety: " + unit.safetyBriefing());
        }
        System.out.printf("Total Daily Revenue: %.2f%n", totalDailyRevenue());
    }
}