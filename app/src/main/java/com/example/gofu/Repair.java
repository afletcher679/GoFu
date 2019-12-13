package com.example.gofu;

public class Repair {

    //private String CartId;
    private String ReportDate;
    private int Reportnum;
    private String Status;
    private String Issues;

    public Repair() {
    }

    public Repair( String date, int id, String status, String issues){
        //this.CartId = cartPath;
        this.ReportDate = date;
        this.Reportnum = id;
        this.Status = status;
        this.Issues = issues;
    }

    public String getStatus() {
        return Status;
    }

//    public String getCartId() {
//        return CartId;
//    }
//
//    public void setCartId(String cartId) {
//        CartId = cartId;
//    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReportDate() {
        return ReportDate;
    }

    public void setReportDate(String reportDate) {
        ReportDate = reportDate;
    }

    public int getReportNum() {
        return Reportnum;
    }

    public void setReportNum(int reportNum) {
        Reportnum = reportNum;
    }

    public String getIssues() {
        return Issues;
    }

    public void setIssues(String issues) {
        Issues = issues;
    }
}
