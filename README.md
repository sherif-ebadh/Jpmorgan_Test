# Jpmorgan_Test

<b>Implementation of coding exercise</b><br />
* this is application create some reports of the instructions sent by various clients to JP Morgan to execute in the international
market.
 
* Amount in USD settled incoming everyday
* Amount in USD settled outgoing everyday
* Ranking of entities based on incoming and outgoing amount. Eg: If entity foo instructs the highest amount for a buy instruction, then foo is rank 1 for outgoing

<h3>Runing the application</h3>
download the Jar from target/jpmorgan_test.jar
run the Jar in the command window using the command 
<pre>java -jar jpmorgan_test.jar</pre>

<b>Assumtions</b>
The prices in kept without rounding - just as business case - 
I have used data sample inside the main method - it can be passed in different way I used this for simplesty only

for Validate a record
* Unit can't be 0
* Price per uint can't be zero or negative
* AgreedFX can't be zero or less
* Entity , buy/sell,and currency can't be empty
* for buy/sell must have the values "B"  or "S" only otherwise record will not be added
* No InstructionDate or SettlementDate can be in the past
* SettlementDate can't be before InstructionDate 
