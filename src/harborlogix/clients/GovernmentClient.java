package harborlogix.clients;

/**
 * A state client with a fixed statutory discount of 25 percent and priority handling.
 */
public class GovernmentClient extends Client {

    public static final double STATUTORY_DISCOUNT = 25.0;
    private final String agencyCode;

    public GovernmentClient(String clientId, String name, String agencyCode) {
        super(clientId, name);
        if (agencyCode == null || agencyCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Agency code cannot be null or empty.");
        }
        this.agencyCode = agencyCode.trim();
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    @Override
    public double discountPercent() {
        return STATUTORY_DISCOUNT;
    }

    @Override
    public String clientTier() {
        return "Government (" + agencyCode + ")";
    }

    @Override
    public boolean priorityHandling() {
        return true;
    }

    @Override
    public String toString() {
        return super.toString() + " [Agency Code=" + agencyCode + ", Priority=true]";
    }
}
