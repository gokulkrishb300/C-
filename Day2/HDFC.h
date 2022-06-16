#ifndef HDFC_H
#define HDFC_H
#include "Bank.h"
#include "Loan.h"

using std::to_string;
class HDFC : public Bank, public Loan
{

 public:
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
        "\nBranch Name : " + getBankName()+
        "\nBank Type : "+getBranchName()+
        "\nEstablished Date : "+getEstablishedDate()+
        "\nLoan Type : "+getLoanType()+
        "\nRequired Document : "+getRequiredDocument()+
        "\nInterest Rate : "+ to_string(getInterestRate());
}

};
#endif