######### smart_transfer #########
REST Api to simulate transfers between accounts.
It makes use of Java 8 JDK

######### ENDPOINTS ########
base url => localhost:7071

# Get account by id
GET /account/{id}

# Create account
POST /account/{balance}

# Do transfer posting a JSON
POST /transfer
{
	"accountSource": { "id": id_source },
	"accountTarget": { "id": id_target },
	"value": value
}

# Get transfers by source account
GET /transfer/by_account_source/{source_id}

######### BUILD UP SERVICE ########

# Build the project in maven
mvn clean install -Dmaven.test.skip=true

# Start .jar file at target folder
java -jar smarttransfer-1.0-SNAPSHOT-jar-with-dependencies.jar

######### RUN TESTS#########

# Unitary tests, though fundamental to any system, were not created due to lack of time.
# I only created functional tests. They test the REST api.

Start the server as described in the section above
You can run the tests in any order

# I also created load tests with JMetter. Two small test scenarios (50 threads/2 loops and 10 threads/100 loops)
# simulating transfer operations between two accounts, A and B. The goal was to test the system for concurrent
# transactions. The system behaved correctly. Results my be provided, if needed.






