#ifndef INDIANBANK_H
#define INDIANBANK_H
#include "Bank.h"
#include "Loan.h"

class IndianBank : public Bank, public Loan
{
public :
 string getBankName()
 {
    return Bank::bankName;
 }

 string getBranchName()
 {
    return Bank::branchName;
 }

 string getBankType()
 {
    return Bank::bankType;
 }

 string getEstablishedDate()
 {
    return Bank::establishedDate;
 }

string getLoanType()
{
    return Loan::loanType;
} 

string getRequiredDocument()
{
    return Loan::requiredDocument;
}

double getInterestRate()
{
    return Loan::interestRate;
}

string getInfo()
{
    return "Bank Name : " + getBankName()+
        "\nBranch Name : " + getBranchName()+
        "\nBank Type : "+getBankType()+
        "\nEstablished Date : "+getEstablishedDate()+
        "\nLoan Type : "+getLoanType()+
        "\nRequired Document : "+getRequiredDocument()+
        "\nInterest Rate : "+ to_string(getInterestRate());
}

};
#endif