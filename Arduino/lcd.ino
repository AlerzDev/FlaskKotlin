
#include <LiquidCrystal.h>
#define COLS 16 // Columnas del LCD
#define ROWS 2 // Filas del LCD
const int rs = 12, en = 11, d4 = 5, d5 = 4, d6 = 3, d7 = 2;
LiquidCrystal lcd(rs, en, d4, d5, d6, d7);

String readString;
bool newMessage = true;

void setup()
{
  Serial.begin(9600);
  lcd.begin(COLS, ROWS);
}

void loop()
{
  // put your main code here, to run repeatedly:
  while(Serial.available())
  {
    if(Serial.available()>0)
    {
      if(newMessage)
      {
        readString = "";
        newMessage = false;
      }
      char c = Serial.read();
      readString += c;
    }
  }
  if (readString.length() >0)
  {
    printMessage(readString);
    if(!newMessage)
    {
      newMessage = true;
    }
  }
  printTitleMessage("Message received:");
}

void printMessage(String message)
{
   lcd.setCursor(0,2);
   lcd.print(message);
   delay(150);
   if(message.length()>16)
   {
    lcd.scrollDisplayLeft();
    delay(250);
   }
}
void printTitleMessage(String title)
{
   lcd.setCursor(0,0);
   lcd.print(title);
   delay(150);
   if(title.length()>16)
   {
    lcd.scrollDisplayLeft();
    delay(250);
   }
}
