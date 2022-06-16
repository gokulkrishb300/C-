#include<iostream>
using std::string;
using std::cout;
using std::endl;
using std::cin;

class AbstractWheels
{
    virtual int getNoOfWheels()=0;
};
class Vehicle : AbstractWheels 
{
private:
string vehicleType;
string brand;
string model;
string color;
float mileage;
long price;

public :

void setVehicleType(string vehicleType)
{
    this->vehicleType = vehicleType;
}


string getVehicletType()
{
    return vehicleType;
}

void setBrand(string brand)
{
    this->brand = brand; 
}

string getBrand()
{
    return brand;
}

void setModel(string model)
{
    this->model = model;
}

string getModel()
{
    return model;
}

void setColor(string color)
{
    this->color = color;
}

string getColor()
{
    return color;
}

void setMileage(float mileage)
{
    this->mileage = mileage;
}

float getMileage()
{
    return mileage;
}

void setPrice(long price)
{
    this->price = price;
}

long getPrice()
{
    return price;
}

Vehicle(string vehicleType , string brand, string model, string color, float mileage , long price)
{
    this->vehicleType = vehicleType;
    this->brand = brand;
    this->model = model;
    this->color = color;
    this->mileage = mileage;
    this->price = price;
}


};

class Car : public Vehicle
{

int noOfPersons;
string carType;

public:
Car(string vehicleType , string brand, string model, string color, float mileage , long price
, int noOfPersons, string carType) : Vehicle(vehicleType , brand, model, color, mileage , price)
{
    this->noOfPersons = noOfPersons;
    this->carType = carType;
}

void setNoOfPersons(int noOfPersons)
{
    this->noOfPersons = noOfPersons;
}

int getNoOfPersons()
{
    return noOfPersons;
}

void setCarType(string carType)
{
    this->carType = carType;
}

string getCarType()
{
    return carType;
}

int getNoOfWheels()
{
return 4;
}

void result()
{
    cout<<this->getVehicletType()<<" ";
    cout<<this->getBrand()<<" ";
    cout<<this->getModel()<<" ";
    cout<<this->getColor()<<" ";
    cout<<this->getMileage()<<" ";
    cout<<this->getPrice()<<" ";
    cout<<noOfPersons<<" ";
    cout<<carType<<" ";
}
};

class Bike : public Vehicle 
{
int weight;
string bikeType;

public:
Bike(string vehicleType , string brand, string model, string color, float mileage , long price
,int weight,string bikeType) : Vehicle(vehicleType,brand,model,color,mileage,price)
{
    
    this->weight = weight;
    this->bikeType = bikeType;
}


void setWeight(int weight)
{
    this->weight = weight;
}

int getWeight()
{
    return weight;
}

void setBikeType(string bikeType)
{
    this->bikeType = bikeType;
}

string getBikeType()
{
    return bikeType;
}

int getNoOfWheels()
{
    return 2;
}

void result()
{
    cout<<this->getBikeType()<<" ";
    cout<<this->getBrand()<<" ";
    cout<<this->getModel()<<" ";
    cout<<this->getMileage()<<" ";
    cout<<this->getPrice()<<" ";
    cout<<weight<<" ";
    cout<<bikeType<<" ";
}

bool operator > (Car& car)
{
    if(this->getPrice() > car.getPrice())
    {
        return true;
    }
    return false;
}
};

int main()
{
    string vehicleType;
    string brand;
    string model;
    string color;
    float mileage;
    long price;
    int noOfPersons;
    string carType;
    int weight;
    string bikeType;

    cout<<"Car Details "<<endl;
    cout<<"Vehicle Type : ";  cin>>vehicleType;
    cout<<"Brand : ";         cin>>brand;
    cout<<"Model : ";         cin>>model;
    cout<<"Color : ";         cin>>color;
    cout<<"Mileage : ";       cin>>mileage;
    cout<<"Price : ";         cin>>price;
    cout<<"NoOfPersons : ";   cin>>noOfPersons;
    cout<<"CarType : ";       cin>>carType;

    Car car = Car(vehicleType,brand,model,color,mileage,price,noOfPersons,carType);
    
    

    cout<<"Bike Details  "<<endl;
    cout<<"Vehicle Type : ";  cin>>vehicleType;
    cout<<"Brand : ";         cin>>brand;
    cout<<"Model : ";         cin>>model;
    cout<<"Color : ";         cin>>color;
    cout<<"Mileage : ";       cin>>mileage;
    cout<<"Price : ";         cin>>price;
    cout<<"Weight : ";        cin>>weight;
    cout<<"BikeType: ";       cin>>bikeType;

    Bike bike = Bike(vehicleType,brand,model,color,mileage,price,weight,bikeType);


    cout<<"---- Results ----"<<endl;


    // vehicle.setVehicleType(vehicleType);
    // vehicle.setBrand(brand);
    // vehicle.setModel(model);
    // vehicle.setColor(color);
    // vehicle.setMileage(mileage);
    // vehicle.setPrice(price);
    
    // cout<<"---- New Details ----"<<endl;
    // cout<<vehicle.getVehicletType()+" ";
    // cout<<vehicle.getBrand()+" ";
    // cout<<vehicle.getModel()+" ";
    // cout<<vehicle.getColor()+" ";
    // cout<<vehicle.getMileage()<<" ";
    // cout<<vehicle.getPrice()<<endl;

    car.result();
    cout<<endl;
    bike.result();
    cout<<endl;
    cout<<"No Of Wheels in "<< car.getModel()<<" is "<<car.getNoOfWheels()<<endl;
   
    cout<<"No of wheels in "<< bike.getModel()<<" is "<<bike.getNoOfWheels()<<endl;
    
    if(bike.operator>(car))
    {
        cout<<bike.getModel()<<" is costlier than "<<car.getModel()<<endl;
    }
    else
    {
        cout<<car.getModel()<<" is costlier than "<<bike.getModel()<<endl;
    }
    
   
    
    
}