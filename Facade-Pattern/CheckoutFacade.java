class Inventory {
    boolean checkStock(String productId) {
        return true;
    }

    void reserve(String productId){
        System.out.println("Reserved" + productId);
    }

    void release(String productId){
        System.out.println("Released" + productId);
    }
}

class Payment{
    boolean charge(String userId, double amount){
        return true;
    }

    void refund(String userId, double amount){
        System.out.println("Refunded" + amount);
    }
}

class Shipping {
    String createLabel(String address){
        return "TRK" + System.currentTimeMillis();
    }

    void schedulePickup(String label) {
        System.out.println("Pickup scheduled for" + label);
    }

    boolean isAvailable(){
        return true;
    }
}

class Email{
    void send(String to, String subject, String body){
        System.out.println("Email sent to" + to);
    }

}

public class CheckoutFacade{
    private Inventory invent = new Inventory();
    private Payment pay = new Payment();
    private Shipping ship = new Shipping();
    private Email email = new Email();
    public OrderResult checkout(String userId, String productId, double price, String address){
        if (!invent.checkStock(productId)) {
            return new OrderResult(false, null , "Product not in stock");
        }

        invent.reserve(productId);

        if (!pay.charge(userId, price)){
            invent.release(productId);
            return new OrderResult(false, null, "Payment did not go through");
        }

        if(!ship.isAvailable()){
            pay.refund(userId, price);
            invent.release(productId);
            return new OrderResult(false, null, "Shipping unavailable");
        }

        String trackingNumber = ship.createLabel(address);
        ship.schedulePickup(trackingNumber);

        email.send(userId, "Order Confirmation", "Tracking Number:" + trackingNumber);

        return new OrderResult(true, trackingNumber, "Order placed succesfully");
    }
}

class OrderResult {
    private final boolean success;
    private final String trackingNumber;
    private final String message;

    public OrderResult(boolean success, String the_tracking_number, String message){
        this.success = success;
        this.trackingNumber = the_tracking_number;
        this.message = message;

    }

    public boolean isSuccess() {
        return success;
    }

    public String getTrackingNumber(){
        return trackingNumber;
    }

    public String getMessage(){
        return message;
    }
}