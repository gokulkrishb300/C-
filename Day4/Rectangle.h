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

    public :
    Rectangle()
    {
        int zero = 0;
        left = &zero;
        top = &zero;
        width = &zero;
        height = &zero;
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

        this->left = (int *)&left;
        this->top = (int *)&top;
        this->width = (int *)&width;
        this->height = (int *)&height;
    }

    Rectangle(Rectangle &rectangle)
    {
        this->left = rectangle.left;
        this->top = rectangle.top;
        this->width = rectangle.width;
        this->height = rectangle.height;
    }

    Rectangle(Rectangle &rectangle , int &area)
    {
        
        this->left = rectangle.left;
        this->top = rectangle.top;
        this->width = rectangle.width;
        this->height = rectangle.height;
        this->area = &area;
    }

    ~Rectangle()
    {
        delete left;
        delete top;
        delete width;
        delete height;
    }

    string printRectangle()
    {
        return "Left "+to_string(*left)+" Top "+to_string(*top)+" Width "+to_string(*width)+" Height "+to_string(*height);
    }


    
};