## Assumptions:
	* The API is just a microservice within a larger system
    * a drone can handle more than one medication as long as it's withing weight limit
    * one delivery = one drone
    * can only load an idle drone or a loaded drone iif the loaded weight is below the allowed drone weight limit
    * when loading, switch the drone status to loading to prevent another service from picking the same drone
## Overview
- this service runs on a memory database hence, the data is tied to the runtime
- We have DroneController, FileController and MedicationController to handle data input/update
- Dispatch Controller only handles loading and dispatching drones
- Battery checker task will run 3 seconds after launch and every minute thereafter

**Datanase Structure**
![Alt text](uploads/Screenshot%20from%202022-07-11%2018-42-48.png "Database models")

## How to run project
Point to note, this is a gradle project, made with SpringBoot framework. 
The project can by run by your preferred IDE or by navigating to the root directory 
and running 
- `./gradlew clean` then 
- `./gradlew bootRun`
## How to run tests
- if you are using linux or mac, navigate to the root of the project and execute `./gradlew`
- for windows users, execute `gradle.bat`

***Post man Link***
`https://www.getpostman.com/collections/bb11ad8ba7a5d18317c7`


