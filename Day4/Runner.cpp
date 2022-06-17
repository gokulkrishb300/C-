#include "Rectangle.h"
#include "InputStudio.h"

int main()
{
    int left;
    int top;
    int width;
    int height;
    InputStudio input;
    Rectangle rectangle0;
    cout<<"1.Default Constructor"<<endl;
    cout << rectangle0.printRectangle()<<endl;
    
    cout<<"2.Parameterized Constructor with Left, Top as '0'"<<endl;
    
    left = 0;
    top = 0;
    width = input.getInt("width : ");
    height = input.getInt("height : ");

    
    Rectangle rectangle1(left,top,width,height);

    cout<<rectangle1.printRectangle()<<endl;

    cout<<"3.Parameterized Constructor with general values"<<endl;

    left = input.getInt("left : ");
    top = input.getInt("top : ");
    width = input.getInt("width : ");
    height = input.getInt("height : ");

    
    Rectangle rectangle2(left,top,width,height);

    cout<<rectangle2.printRectangle()<<endl;

    cout<<"4.Parameterized Constructor with float values"<<endl;

    float leftf;
    float topf;
    float widthf;
    float heightf;

    left = input.getFloat("left : ");
    top = input.getFloat("top : ");
    width = input.getFloat("width : ");
    height = input.getFloat("height : ");

    
    Rectangle rectangle3(leftf,topf,widthf,heightf);

    cout<<rectangle3.printRectangle()<<endl;

    cout<<"5.Copying the 3rd constructor values"<<endl;

    Rectangle rectangle4(rectangle2);

    cout<<rectangle4.printRectangle()<<endl;

    cout<<"6.Copying the 3rd constructor values along with area"<<endl;

    Rectangle rectangle5(rectangle2);

    cout<<rectangle5.printRectangle()<<" area : "<<rectangle5.areana()<<endl;

    return 0;
}

