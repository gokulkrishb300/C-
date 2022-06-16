#ifndef INPUTSTUDIO_H
#define INPUTSTUDIO_H

#include<iostream>
using std::string;
using std::cin;

class InputStudio
{
    public:
    string getString()
    {
        string input = "";
        getline(cin,input);
        return input;
    }

    int getInt()
    {
        int input = 0;
        cin>>input;
        return input;
    }

    float getFloat()
    {
        float input = 0.0f;
        cin>>input;
        return input;
    }

    double getDouble()
    {
        double input = 0;
        cin>>input;
        return input;
    }

    char getChar()
    {
        char input;
        cin>>input;
        return input;
    }
};
#endif