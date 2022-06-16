#ifndef BANK_H
#define BANK_H

#include<iostream>
using std::string;
using std::endl;
using std::cout;
using std::cin;
class Bank
{
    protected:
    string bankName;
    string branchName;
    string bankType;
    string establishedDate;

public:

    // Bank(string bankName, string branchName, string bankType, string establishedDate)
    // {
    //     this->bankName = bankName;
    //     this->branchName = branchName;
    //     this->bankType = bankType;
    //     this->establishedDate = establishedDate;
    // }

    void setBankName(string bankName)
    {
        this-> bankName = bankName;
    }

    void setBranchName(string branchName)
    {
        this->branchName = branchName;
    }

    void setBankType(string bankType)
    {
        this->bankType = bankType;
    }

    void setEstablishedDate(string estrablishedDate)
    {
        this->establishedDate = establishedDate;
    }




    
    virtual string getBankName(){}

    virtual string getBranchName(){}

    virtual string getBankType(){}
 
    virtual string getEstablishedDate(){}

    virtual string getInfo(){}
    
};
#endif