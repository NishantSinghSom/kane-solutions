# kane-solutions
This application provides the features for Kane Solutions

# Below is the Solution for Question No. 1:
We can follow below steps to test the endpoint in Postman which recieves the input file and generates the required output file:
Step 1: Run the Spring Boot Application
Step 2: Create the POST Request with url http://localhost:8081/api/visa/process
Step 3: Select form-data inside Body
Step 4: Key should be file and Content Type should be multipart/form-data
Step 5: Upload the input file in value and hit send button.

# Below is the Solution for Question No. 2:
SELECT 
    e1.name AS first_employee,
    e2.name AS second_employee
FROM EMPLOYEE e1
JOIN EMPLOYEE e2
    ON e1.salary < e2.salary
ORDER BY e1.id ASC, e2.salary ASC;
