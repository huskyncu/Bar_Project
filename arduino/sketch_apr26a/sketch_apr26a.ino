#include <Servo.h>
#include "Ultrasonic.h"

int range1;
int range2;
int sit=0;

Ultrasonic __ultrasonic2(2);
Ultrasonic __ultrasonic3(3);
Servo __myservo10;

void setup()
{
  pinMode(11,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(13,OUTPUT);
  __myservo10.attach(10);
  Serial.begin(9600);
}

void loop()
{
  range1 = __ultrasonic2.MeasureInCentimeters();
  Serial.print("大門距離 : ");
  Serial.println(range1);
  range2 = __ultrasonic3.MeasureInCentimeters();
  Serial.print("座位距離 : ");
  Serial.println(range2);
  if (range1 < 5) {
    __myservo10.write(0);
    delay(3000);
  } else {
    __myservo10.write(90);
  }
  if(sit==0){
    digitalWrite(11,HIGH);
    digitalWrite(12,LOW);
    digitalWrite(13,HIGH);
  }else{
    digitalWrite(11,HIGH);
    digitalWrite(12,HIGH);
    digitalWrite(13,LOW);
  }
  if(range2 < 6){
    sit=1;
    Serial.println("sit");
    
  }
  else{
    sit=0;
    Serial.println("not sit");
    
  }
  delay(1000);
  
}
