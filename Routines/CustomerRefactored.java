class Customer{
    private String name;
    private String address;
    private double balance;
    private int customerType;
    private String email;
    private boolean vip;

    public Customer(String name, String address, double balance, int customerType, String email, boolean vip){
        this.name = name;
        this.address = address;
        this.balance = balance;
        this.customerType = customerType;
        this.email = email;
        this.vip = vip;
    }

    public String getName(){
        return name;
    }

    public String getAddress(){
        return address;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public int getCustomerType(){
        return customerType;
    }

    public String getEmail(){
        return email;
    }

    public boolean isVip(){
        return vip;
    }
}
//String n, String a, double d, int t, String e, boolean g,int x

public class CustomerRefactored{
    private static final int REGULAR_CUSTOMER = 1;
    private static final int PREMIUM_CUSTOMER = 2;

    public void processCustomer(Customer customer, double [] orders){

        validateOrder(customer,orders);

        double orderTotal = calculateOrderTotal(orders);

        double discountRate = determineDiscountRate(customer.getCustomerType());

        double finalTotal = orderTotal - (orderTotal * discountRate);

        String message = createCustomerMessage(customer, finalTotal);

        notifyCustomer(customer, message);

        updateCustomerBalance(customer, finalTotal);


    }

    private void validateOrder(Customer customer, double [] orders){
        if (customer == null){
            throw new IllegalArgumentException("Customer cannot be null");
        }

        if (orders == null || orders.length == 0){
            throw new IllegalArgumentException("Orders cannot be empty!");
        }

        for(double order : orders){
            if (order < 0){
                throw new IllegalArgumentException("Order values must be non-negative");
            }
        }

        int type = customer.getCustomerType();

        if (type != REGULAR_CUSTOMER && type != PREMIUM_CUSTOMER){
            throw new IllegalArgumentException("Invalid Customer Type");
        }
    }

    private double calculateOrderTotal(double[] orders){
        double total = 0;

        for(double order : orders){
            total += order;
        }

        return total;
    }

    private double determineDiscountRate(int customerType){
        if(customerType == REGULAR_CUSTOMER){
            return 0.10;
        }

        if(customerType == PREMIUM_CUSTOMER){
            return 0.20;
        }

        return 0;
    }

    private String createCustomerMessage(Customer customer, double total){
        String message = "Hello" + customer.getName() + "of" + customer.getAddress() + ", your total is" + total;

        if(customer.isVip()){
            message += "(VIP)";
        }

        return message;
    }

    private void notifyCustomer(Customer customer, String message){
        System.out.println(message);
        if (customer.getEmail() != null && !customer.getEmail().isBlank()){
            sendEmail(customer.getEmail(), message);
        }
    }

    private void updateCustomerBalance(Customer customer, double total){
        customer.setBalance(total);
    }

    private void sendEmail(String email, String message){
        System.out.println("Sending email to" + email);
    }
}