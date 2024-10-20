# CustomerRewardApplication

This Spring Boot application calculates reward points for customers based on their transactions.

Condition : A Customer recieves 2 points for every doller spent over $100 in each transaction, plus 1 point for every doller spent between $50 and $100 in each transaction.
## Technology Used
- Java 17
- Maven 4.0.0
-	SpringBoot 3.3.4
-	MYSQL8
## APIEndPoints
### Create Customer
-	URI:" http://localhost:8081/api/customer/save"
-	method: POST
-	Function: create customer
-	ReqBody:
  
      { 
  

      "name": "mounika",

      "email": "mounika5@gmail.com",

      "phoneNo":8688131024"

      }
-	Response: Customer Details are saved Successfully

### Create Transaction
-	URI:" http://localhost:8081/api/9/transaction"
-	method: POST
-	Function:create Transaction
-	ReqBody:  
      {


    "transactionDate": "2024-10-08",

    "amount": 250

      }
-	Response: Transaction Details are saved Successfully

### Retrieving the default 3 months Reward details based on customerId
-	URI:" http://localhost:8081/rewards/8/rewardDetails"
-	method: GET
-	Function: get default 3 months reward poins
-	Response:
  
  {


  "customer": 

  {

    "id": 8, 
    
    "name": "vishwa",
    
    "phoneNo": "9849211276"

  },

  "transaction": [

  {

  "transactionId": 8,

  "transactionDate": "2024-09-08",

  "amount": 210,

  "rewardPoints": 270

  },

  {

  "transactionId": 9,

  "transactionDate": "2024-09-15",

  "amount": 150,

  "rewardPoints": 150

  },

  {

  "transactionId": 10,

  "transactionDate": "2024-08-15",

  "amount": 120,

  "rewardPoints": 90

  },

  {

  "transactionId": 11,

  "transactionDate": "2024-10-15",

  "amount": 120,

  "rewardPoints": 90

  }

  ],

  "totalRewardPoints": 600
  
  }
  
### Retrieving montly wise transactions based on customer id ,start and end Date
-	URI: http://localhost:8081/rewards/monthlyPoints/8?startDate=2024-08-01&endDate=2024-10-10
-	method: GET
-	Function: get monthly wise reward points and total points
-	Response:

  {


  "monthlyData":
   {

  "AUGUST": {

    "transactions": [{

              "transactionId": 10,

              "transactionDate": "2024-08-15",

              "amount": 120,

              "rewardPoints": 90

                  }],

    "points": 90

            },

   "SEPTEMBER": {

      "transactions":[{

              "transactionId": 8,

              "transactionDate": "2024-09-08",

              "amount": 210,

              "rewardPoints": 270

             },

             {

              "transactionId": 9,

              "transactionDate": "2024-09-15",

              "amount": 150,

              "rewardPoints": 150
            }

            ],

        "points": 420

          }   
          
        },  
        
         "customer":  
         
           {

              "id": 8,

              "name": "vishwa",

              "phoneNo": "9849211276"

            }

          }
### Look in POC folder for more evidences
