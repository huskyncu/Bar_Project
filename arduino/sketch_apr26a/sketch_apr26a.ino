#include <Servo.h>
#include "Ultrasonic.h"
#include "DHT.h"

DHT __dht2(3, DHT22);

int range;
int sit=0;
int tmp;

Ultrasonic __ultrasonic2(2);
Servo __myservo10;

void setup()
{
  pinMode(11,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(13,OUTPUT);
  __myservo10.attach(10);
  Serial.begin(9600);
  __dht2.begin();
}

void loop()
{
  range = __ultrasonic2.MeasureInCentimeters();
  Serial.print("距離 : ");
  Serial.println(range);
  Serial.print("攝氏溫度 : ");
  Serial.println(__dht2.readTemperature());
  if (range < 5) {
    __myservo10.write(0);
    delay(5000);
  } else {
    __myservo10.write(90);
  }
  if(sit==0){
    digitalWrite(11,HIGH);
    digitalWrite(12,HIGH);
    digitalWrite(13,LOW);
  }else{
    digitalWrite(11,LOW);
    digitalWrite(12,LOW);
    digitalWrite(13,LOW);
  }
  if(__dht2.readTemperature()>tmp){
    Serial.println("sit");
  }
  else if(__dht2.readTemperature()<tmp){
    sit=0;
    Serial.println("not sit");
  }
  else{
    
  }
  tmp=__dht2.readTemperature();
  delay(1000);
  
}
