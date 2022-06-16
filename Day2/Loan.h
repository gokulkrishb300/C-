#ifndef LOAN_H
#define LOAN_H
#include<iostream>
using std::cout;
using std::endl;
using std::string;

class Loan
{
protected :
    string loanType;
    string requiredDocument;
    double interestRate;

public:
    // Loan(string loanType,string requiredDocument, double interestRate)
    // {
    //     this->loanType = loanType;
    //     this->requiredDocument = requiredDocument;
    //     this->interestRate = interestRate;
    // }

    void setLoanType(string loanType)
    {
        this->loanType = loanType;
    }

    void setRequiredDocument(string requiredDocument)
    {
        this->requiredDocument = requiredDocument;
    }

    void setInterestRate(double interestRate)
    {
        this->interestRate = interestRate;
    }

    virtual string getLoanType(){}
    virtual string getRequiredDocument(){}
    virtual double getInterestRate(){}
};

#endif

