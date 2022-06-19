#include "NestedNameSpace.h"
#include "InputStudio.h"
using namespace createnamespace;
using namespace outsidenamespace;

int main()
{
    InputStudio input;

    NameSpaceInsideClass insideClass;

    insideClass.print();

    namespace alias = outsidenamespace::insidenamespace;

    float length = input.getFloat("Length : ");
    float breadth = input.getFloat("breadth : ");
    float result =  alias::perimeterOfRectangle(length,breadth);
    cout<<"Perimeter of Rectangle : ";
    cout<<result<<endl;

    result = alias::areaOfRectangle(length,breadth);
    cout<<"Area of Rectangle : ";
    cout<<result<<endl;

    result = alias::perimeterOfSquare(length);
    cout<<"Perimeter of Square : ";
    cout<<result<<endl;

    result = alias::areaOfSquare(length);
    cout<<"Area of Square : ";
    cout<<result<<endl;


}