package Projectt;



public class Wallet {
    public Wallet(){}

    private double balance;



    public void depositMoney(double amount){

        if(amount>0){
            balance+=amount;
        }else{
            System.out.println("INVALID");
        }

    }


    public double getBalance(){
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

