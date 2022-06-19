#ifndef NAMESPACE_H
#define NAMESPACE_H
#include<iostream>
using std::cout;
using std::endl;

namespace createnamespace
{
    class NameSpaceInsideClass
    {
        public:
        void print()
        {
           cout<<"Inside a namespace, a class is created"<<endl;
        }
        ~NameSpaceInsideClass()
        {
            cout<<"NameSpaceInsideClass destructor called"<<endl;
        }
    };
}
#endif
