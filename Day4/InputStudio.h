#ifndef INPUTSTUDIO_H
#define INPUTSTUDIO_H

#include<iostream>
using std::string;
using std::cin;
using std::exception;
class InputStudio
{
    public:
    string getString(string input)
    {
        cout<<input;
        try{
        string value = "";
        getline(cin,input);
        return value;
        }
        catch(exception e)
        {
            cout<<"string input only"<<endl;
        }
        return getString(input);
    }

    int getInt(string input)
    {
        cout<<input;
        try{
        int value = 0;
        cin>>value;
        return value;
        }
        catch(exception e)
        {
            cout<<"Integer input only"<<endl;
        }
        return getInt(input);
    }
    

    float getFloat(string input)
    {
        cout<<input;
        try{
        float value = 0.0f;
        cin>>value;
        return value;
        }
        catch(exception e)
        {
            cout<<"Float input only"<<endl;
        }
        return getFloat(input);

    }

    double getDouble(string input)
    {
        cout<<input;
        try{
        double value = 0;
        cin>>value;
        return value;
        }
        catch(exception e)
        {
            cout<<"Double input only"<<endl;
        }
        return getDouble(input);
    }

    char getChar(string input)
    {
        cout<<input;
        try{
        char value;
        cin>>value;
        return value;
        }
        catch(exception e)
        {
            cout<<"Char input only"<<endl;
        }
        return getChar(input);
    }
};
#endif