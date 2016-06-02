package se.nackademin.rest.test.gson;

import java.util.List;

public class Loans {
    private List <Loan> loan;

    /**
     * @return the list of Loan:s
     */
    public List <Loan> getLoanList() {
        return loan;
    }

    /**
     * @param loan the list of Loans:s to set
     */
    public void setLoanList(List <Loan> loan) {
        this.loan = loan;
    }  
}
