public class User {
    private int agency;
    private String name;
    private double balance;
    private String password;

    public User(){
        
    }
    
    public User(int agency, String name, double balance, String password) {
        this.agency = agency;
        this.name = name;
        this.balance = balance;
        this.password = password;
    }

    public User(int agency, String name, String password) {
        this.agency = agency;
        this.name = name;
        this.balance = 1000.0;
        this.password = password;
    }

    public User(int agency, String password) {
        this.agency = agency;
        this.name = null;
        this.balance = 1000.0;
        this.password = password;
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

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

   
}
