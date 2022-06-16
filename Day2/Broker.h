#ifndef BROKER_H
#define BROKER_H
#include "Bank.h"
#include "Loan.h"
#include "HDFC.h"
#include "ICICI.h"
#include "IndianBank.h"
#include "SBI.h"

class Broker
{
public :

    string bankName(float interestRate, HDFC &hdfc,ICICI &icici,IndianBank &indianBank,SBI &sbi)
    {
        if(interestRate == hdfc.getInterestRate())
        {
            return hdfc.getBankName();
        }
        else if(interestRate == icici.getInterestRate())
        {
            return icici.getBankName();
        }
        else if(interestRate == indianBank.getInterestRate())
        {
            return indianBank.getBankName();
        }
        return sbi.getBankName();
    }

    double compareIR(HDFC *hdfc, ICICI *icici,IndianBank *indianBank , SBI *sbi)
    {
        Loan *loan = hdfc;

        if(icici ->getInterestRate() < loan-> getInterestRate())
        {
            loan = icici;
        }

        if(indianBank ->getInterestRate() < loan -> getInterestRate())
        {
            loan = indianBank;
        }

        if(sbi ->getInterestRate() < loan-> getInterestRate())
        {
            loan = sbi;
        }

        return loan ->getInterestRate();
    }

    double compareIR(Loan *loan1 , Loan *loan2)
    {
        if(loan1 ->getInterestRate() < loan2 ->getInterestRate())
        {
            return loan1->getInterestRate();
        }
        return loan2->getInterestRate();
    }

    double compareIR(Loan *loan1 , Loan *loan2 , Loan *loan3)
    {
        Loan *loan = loan1;

        if(loan2 ->getInterestRate() < loan -> getInterestRate())
        {
            loan = loan2;
        }

        if(loan3 -> getInterestRate() < loan -> getInterestRate())
        {
            loan = loan3;
        }

        return loan ->getInterestRate();
    }

    double compareIR(Loan *loanArr[] , int size)
    {
        Loan *loan = loanArr[0];

        for(int i = 1 ; i < size ; i++)
        {
            if(loanArr[i]->getInterestRate() < loan ->getInterestRate())
            {
                loan = loanArr[i];
            }
        }
        return loan->getInterestRate();
    }

    string bankInfo(Bank &bank)
    {
        cout<<"-------Details of single bank-------"<<endl;
    
        return bank.getInfo();
    }

    void bankDetails(Bank *bankArr[], int size)
    {
        cout<<"-------Details of Multiple bank------"<<endl;
        for(int i = 0 ; i < size ; i++)
        {
            cout<<bankArr[i]->getInfo()<<endl;
        }
    }
};
#endif