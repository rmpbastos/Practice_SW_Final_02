package org.example.sw_300335322_rafaelmartins_02.dto;

public class InvestmentProjection {

    private int year;
    private String startingAmount;
    private String interest;
    private String endingBalance;

    public InvestmentProjection(int year, String startingAmount, String interest, String endingBalance) {
        this.year = year;
        this.startingAmount = startingAmount;
        this.interest = interest;
        this.endingBalance = endingBalance;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(String startingAmount) {
        this.startingAmount = startingAmount;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getEndingBalance() {
        return endingBalance;
    }

    public void setEndingBalance(String endingBalance) {
        this.endingBalance = endingBalance;
    }

}
