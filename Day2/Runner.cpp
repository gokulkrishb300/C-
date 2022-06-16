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
    input.getString();
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
    cout<<requiredDocument(choice)<<endl;
    loan.setRequiredDocument(input.getString());
    input.getString();
    cout<<"Interest Rate"<<endl;
    loan.setInterestRate(input.getDouble());
    input.getString();
  }

  public:

  void hdfcInfo(HDFC &hdfc)
  {
    BankInfo(hdfc);
    LoanInfo(hdfc);
  }

  void iciciInfo(ICICI &icici)
  {
    BankInfo(icici);
    LoanInfo(icici);
  }

  void indianBankInfo(IndianBank &indianBank)
  {
    BankInfo(indianBank);
    LoanInfo(indianBank);
  }

  void sbiInfo(SBI &sbi)
  {
    BankInfo(sbi);
    LoanInfo(sbi);
  }

};

int main()
{
  Runner run;

  HDFC hdfc;
  ICICI icici;
  IndianBank indianBank;
  SBI sbi;

  run.hdfcInfo(hdfc);
  run.iciciInfo(icici);
  run.indianBankInfo(indianBank);
  run.sbiInfo(sbi);

  Broker broker;

  HDFC *hdfcVal = &hdfc;
  ICICI *iciciVal = &icici;
  IndianBank *indianBankVal = &indianBank;
  SBI *sbiVal = &sbi;

  double interestRate = broker.compareIR(hdfcVal,iciciVal,indianBankVal,sbiVal);

  string bankName = broker.bankName(interestRate,hdfc,icici,indianBank,sbi);

  cout<<"Lowest Bank InterestRate is : "+to_string(interestRate)+" Bank Name : "+bankName<<endl;

  interestRate = broker.compareIR(hdfcVal,sbiVal);
  bankName = broker.bankName(interestRate,hdfc,icici,indianBank,sbi);
  cout<<"Two Bank Compared Low InterestRate is : "+to_string(interestRate)+" Bank Name : "+bankName<<endl;

  interestRate = broker.compareIR(sbiVal,iciciVal,indianBankVal);
  bankName = broker.bankName(interestRate,hdfc,icici,indianBank,sbi);
  cout<<"Three Bank Compared Low InterestRate is : "+to_string(interestRate)+" Bank Name : "+bankName<<endl;

  Loan *loanAr[] = {&hdfc, &icici, &indianBank, &sbi};

  interestRate = broker.compareIR(loanAr,4);

  bankName = broker.bankName(interestRate,hdfc,icici,indianBank,sbi);

  cout<<"N Bank Compared Low InterestRate is : "+to_string(interestRate)+" Bank Name : "+bankName<<endl;

  cout<<broker.bankInfo(hdfc)<<endl;
  cout<<broker.bankInfo(icici)<<endl;
  cout<<broker.bankInfo(indianBank)<<endl;
  cout<<broker.bankInfo(sbi)<<endl;

  Bank *loanArr[] = {&hdfc, &icici , &indianBank,&sbi};
  broker.bankDetails(loanArr,4);
  return 0;

}
#endif