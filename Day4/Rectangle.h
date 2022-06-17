#ifndef RECTANGLE_H
#define RECTANGLE_H
#include<iostream>
#include<cmath>
using std::cin;
using std::cout;
using std::endl;
using std::string;
using std::to_string;

class Rectangle
{
    int *left;
    int *top;
    int *width;
    int *height;
    int *area;
    int temp1,temp2,temp3,temp4;
    public :
    Rectangle()
    {
        left = new int;
        top = new int;
        width = new int;
        height = new int;
    }  

    Rectangle(int &left, int &top, int &width , int &height)
    {
        this->left = &left;
        this->top = &top;
        this->width = &width;
        this->height = &height;
    }

    Rectangle(float &left, float &top, float &width , float &height)
    {
        temp1 = round(left);

        this->left = &temp1;

        temp2 = round(top);

        this->top = &temp2;

        temp3 = round(width);

        this->width = &temp3;

        temp4 =  round(height);

        this->height = &temp4;
       
    }

    Rectangle(Rectangle &rectangle)
    {
        this->left = rectangle.left;
        this->top = rectangle.top;
        this->width = rectangle.width;
        this->height = rectangle.height;
        this->area = (int *) areana();
    }
    
    int areana()
    {
        return  (*height) * (*width);
    }

    ~Rectangle()
    {
        cout<<"Destructor Called"<<endl;
    
        left = new int;
        top = NULL;
        width = 0;
        height = (int*)malloc(sizeof(int));

        delete left;
        delete top;
        delete width;
        delete height;
    }

    string printRectangle()
    {
        return "Left : "+to_string(*left)+" Top : "+to_string(*top)+" Width : "+to_string(*width)+" Height : "+to_string(*height);
    }


    
};
#endif