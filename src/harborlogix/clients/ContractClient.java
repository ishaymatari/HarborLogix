package harborlogix.clients;

/**
 * A client with a negotiated discount between 0 and 40 percent.
 */
public class ContractClient extends Client {

    private final double contractDiscount;

    public ContractClient(String clientId, String name, double contractDiscount) {
        super(clientId, name);
        if (contractDiscount < 0.0 || contractDiscount > 40.0) {
            throw new IllegalArgumentException("Contract discount must be between 0 and 40 percent.");
        }
        this.contractDiscount = contractDiscount;
    }

    @Override
    public double discountPercent() {
        return contractDiscount;
    }

    @Override
    public String clientTier() {
        return "Contract Client (" + contractDiscount + "% discount)";
    }

    @Override
    public String toString() {
        return super.toString() + " [Contract Discount=" + contractDiscount + "%]";
    }
}