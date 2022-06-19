#ifndef NESTED_NAMESPACE_H
#define NESTED_NAMESPACE_H
#include "NameSpace.h"

namespace outsidenamespace
{
    inline namespace insidenamespace
    {
        float perimeterOfRectangle(float length , float breadth)
        {
            return 2*(length+breadth);
        }

        float areaOfRectangle(float length , float breadth)
        {
            return length*breadth;
        }

        float areaOfSquare(float length)
        {
            return length*length;
        } 

        float perimeterOfSquare(float length)
        {
            return 4*length;
        }
    }
}
#endif