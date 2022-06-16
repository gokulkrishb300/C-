#ifndef RUNNER_H
#define RUNNER_H
#include "Bank.h"
#include "Loan.h"
#include "HDFC.h"
#include "ICICI.h"
#include "IndianBank.h"
#include "SBI.h"
#include "Broker.h"
#include "InputStudio.h"

class Runner 
{
  InputStudio input;
  string bankType(int choice)
  {
    string input = "";
    switch(choice)
    {
      case 1:
            input = "Public";
            break;
     case 2:
           input = "Private";
           break;
     default:
            throw("Invalid");
            break;
    }
    return input;
  }

  string loanType(int choice)
  {
    string input = "";
    switch(choice)
    {
      case 1:
            input = "Gold Loan";
            break;
      case 2:
            input = "Personal Loan";
            break;
      case 3:
            input = "Land Loan";
            break;
      default:
            throw("Invalid");
            break;
    }
    return input;
  }

  string requiredDocument(int choice)
  {
    string input = "";
    switch(choice)
    {
      case 1:
           input = "Give Gold Loan Document Register Number";
           break;
      case 2:
           input = "Give Personal Loan Document Register Number";
           break;
      case 3:
           input = "Give Land Loan Document Register Number";
           break;
    }
    return input;
  }

  void BankInfo(Bank &bank)
  {
    cout<<"BankName: "<<endl;
    bank.setBankName(input.getString());

    cout<<"BranchName: "<<endl;
    bank.setBranchName(input.getString());

    cout<<"Bank Type: "<<endl;
    cout << "1.Public\n"<<endl;
    cout << "2.Private\n"<<endl;
    bank.setBankType(bankType(input.getInt()));
  
    
    cout<<"Established Date: "<<endl;
    bank.setEstablishedDate(input.getString());
  }

  void LoanInfo(Loan &loan)
  {
    cout<<"LoanType : "<<endl;
    cout<<"1.GoldLoan"<<endl;
    cout<<"2.PersonalLoan"<<endl;
    cout<<"3.LandLoan"<<endl;
    int choice = input.getInt();
    loan.setLoanType(loanType(choice));
    cout<<"Interest Rate"<<endl;
    loan.setInterestRate(input.getDouble());
    cout<<requiredDocument(choice);
    loan.setRequiredDocument(input.getString());
  }



  int main(){

  }  
};
#endif