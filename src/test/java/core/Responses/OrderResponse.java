package core.Responses;
public class OrderResponse
{
    private String id;
    private String petId;
    private String quantity;
    private String shipDate;
    private String status;
    private boolean complete;

    public OrderResponse(){}

    public OrderResponse(String id, String petId, String quantity, String shipDate, String status, boolean complete) {
        this.id = id;
        this.petId = petId;
        this.quantity = quantity;
        this.shipDate = shipDate;
        this.status = status;
        this.complete = complete;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "id=" + id +
                ", petId=" + petId +
                ", quantity=" + quantity +
                ", shipDate='" + shipDate + '\'' +
                ", status='" + status + '\'' +
                ", complete=" + complete +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getPetId() {
        return petId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getShipDate() {
        return shipDate;
    }

    public String getStatus() {
        return status;
    }

    public boolean getComplete() {
        return complete;
    }
}