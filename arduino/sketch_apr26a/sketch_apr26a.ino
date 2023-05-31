#include <Servo.h>
#include "Ultrasonic.h"

int range1;
int range2;
int sit=0;

Ultrasonic __ultrasonic2(2);
Ultrasonic __ultrasonic3(3);
Servo __myservo10;
Servo servo; 
int pos = 0;
void setup()
{
  pinMode(11,OUTPUT);
  pinMode(12,OUTPUT);
  pinMode(13,OUTPUT);
  __myservo10.attach(10);
  servo.attach(4);
  Serial.begin(9600);
}

void loop()
{
  if (Serial.available()){
    pos = Serial.read(); // 讀取Python發來的資料
    servo.write(pos); // 控制馬達轉向
  }
  range1 = __ultrasonic2.MeasureInCentimeters();
  //Serial.print("大門距離 : ");
  //Serial.println(range1);
  range2 = __ultrasonic3.MeasureInCentimeters();
  //Serial.print("座位距離 : ");
  //Serial.println(range2);
  if (range1 < 5) {
    __myservo10.write(0);
    Serial.println("大門 1");
    delay(1000);
  } else {
    Serial.println("大門 0");
    __myservo10.write(90);
  }
  if(sit==0){
    digitalWrite(11,HIGH);
    digitalWrite(12,LOW);
    digitalWrite(13,HIGH);
    Serial.println("座位 0");
  }else{
    digitalWrite(11,HIGH);
    digitalWrite(12,HIGH);
    digitalWrite(13,LOW);
    Serial.println("座位 1");
  }
  if(range2 < 6){
    sit=1;
    //Serial.println("sit");
  }
  else{
    sit=0;
    //Serial.println("not sit");
  }
  delay(1000);
  
}
