package se.nackademin.rest.test.gson;

public class SingleLoan {
    private Loan loan;
    
    public SingleLoan(Loan loan){
        this.loan = loan;   
    }

    /**
     * @return the loan
     */
    public Loan getLoan() {
        return loan;
    }

    /**
     * @param loan the loan to set
     */
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
