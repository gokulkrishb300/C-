#include "Rectangle.h"

int main()
{
    int left;
    int top;
    int width;
    int height;

    Rectangle rectangle0;
    cout<<"1.Default Constructor"<<endl;
    cout << rectangle0.printRectangle()<<endl;
    cin;
    cout<<"2.Parameterized Constructor with Left, Top as '0'"<<endl;
    
    left = 0;
    top = 0;
    cout<<"Width : ";    cin>>width;
    cout<<"Heigth : ";   cin>>height;

    
    Rectangle rectangle1(left,top,width,height);

    cout<<rectangle1.printRectangle()<<endl;

    cout<<"3.Parameterized Constructor with general values"<<endl;

    cout<<"Left : ";    cin>>left;
    cout<<"Top : ";     cin>>top;
    cout<<"Width : ";    cin>>width;
    cout<<"Heigth : ";   cin>>height;

    
    Rectangle rectangle2(left,top,width,height);

    cout<<rectangle2.printRectangle()<<endl;

    cout<<"4.Parameterized Constructor with float values"<<endl;

    float leftf;
    float topf;
    float widthf;
    float heightf;

    cout<<"Left : ";    cin>>leftf;
    cout<<"Top : ";     cin>>topf;
    cout<<"Width : ";    cin>>widthf;
    cout<<"Heigth : ";   cin>>heightf;

    
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

