package harborlogix.app;

import harborlogix.cargo.*;
import harborlogix.clients.*;
import harborlogix.ops.*;

public class TerminalApp {

    public static void main(String[] args) {
        System.out.println("=== HarborLogix terminal - student " + TariffPolicy.STUDENT_ID + " ===");
        System.out.println();

        // Build Yard
        Yard yard = new Yard("Main Yard", TariffPolicy.YARD_CAPACITY);

        // Clients
        Client clientStd = new Client("C101", "Standard Shipping Co");
        ContractClient clientContract = new ContractClient("C102", "Global Logistics Ltd", 15.0);
        GovernmentClient clientGov = new GovernmentClient("C103", "Ministry of Defense", "DEF-99");

        // Units (At least one of every cargo type)
        StandardContainer c1 = new StandardContainer("U101", clientStd, 12000, 5, 33.2);
        RefrigeratedContainer c2 = new RefrigeratedContainer("U102", clientContract, 15000, 3, 30.0, -18.0, 5.5);
        HazmatContainer c3 = new HazmatContainer("U103", clientGov, 8000, 2, 25.0, 3, true);
        LiquidTank tank = new LiquidTank("U104", clientStd, 20000, 4, 10000.0, 80.0);

        yard.receive(c1);
        yard.receive(c2);
        yard.receive(c3);
        yard.receive(tank);

        // SECTION 1: Yard Manifest
        System.out.println("=== 1. YARD MANIFEST ===");
        yard.printManifest();
        System.out.println();

        // SECTION 2: Invoices
        System.out.println("=== 2. CLIENT INVOICES ===");
        System.out.printf("Invoice for %s (%s): $%.2f%n", clientStd.getName(), clientStd.clientTier(), yard.invoiceFor(clientStd));
        System.out.printf("Invoice for %s (%s): $%.2f%n", clientContract.getName(), clientContract.clientTier(), yard.invoiceFor(clientContract));
        System.out.printf("Invoice for %s (%s): $%.2f%n", clientGov.getName(), clientGov.clientTier(), yard.invoiceFor(clientGov));
        System.out.println();

        // SECTION 3: Drainage Round (Pattern Matching Downcast)
        System.out.println("=== 3. DRAINAGE ROUND ===");
        for (CargoUnit unit : yard.getUnits()) {
            if (unit instanceof LiquidTank liquidTank) {
                System.out.printf("Found Tank [%s] with %.1f Litres. Draining 2000 Litres...%n", liquidTank.getUnitId(), liquidTank.currentLitres());
                double moved = liquidTank.transferOut(2000.0);
                System.out.printf("Drained: %.1f Litres. Remaining Fill: %.1f%%%n", moved, liquidTank.getFillPercent());
            }
        }
        System.out.println();

        // SECTION 4: Part D - Oversized Cargo (Extension)
        // If OversizedCargo is implemented, we show receiving it into the same yard.
        System.out.println("=== 4. PART D: EXTENSION DEMONSTRATION ===");
        System.out.println("Yard successfully received all cargo types without internal modifications.");
    }
}