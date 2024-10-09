public class User {
    private int agency;
    private String name;
    private double balance;

    public User(){
        
    }
    
    public User(int agency, String name, double balance) {
        this.agency = agency;
        this.name = name;
        this.balance = 1000.0;
    }

    public int getAgency() {
        return agency;
    }

    public void setAgency(int agency) {
        this.agency = agency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

   
}