package harborlogix.clients;

/**
 * Represents a standard walk-in client with no discount.
 */
public class Client {

    private final String clientId;
    private final String name;

    public Client(String clientId, String name) {
        if (clientId == null || clientId.trim().isEmpty()) {
            throw new IllegalArgumentException("Client ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.clientId = clientId.trim();
        this.name = name.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    /** Walk-in clients get no discount. Subclasses may override. */
    public double discountPercent() {
        return 0.0;
    }

    public String clientTier() {
        return "Standard Walk-in";
    }

    /** Priority clients are unloaded first. */
    public boolean priorityHandling() {
        return false;
    }

    @Override
    public String toString() {
        return "Client [ID=" + clientId + ", Name=" + name + ", Tier=" + clientTier() + "]";
    }
}