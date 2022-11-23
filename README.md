# Task
## Drones
---
### Introduction

There is a major new technology that is destined to be a disruptive force in the field of transportation: **the drone**. Just as the mobile phone allowed developing countries to leapfrog older technologies for personal communication, the drone has the potential to leapfrog traditional transportation infrastructure.

Useful drone functions include delivery of small items that are (urgently) needed in locations with difficult access.

---

### Task description

We have a fleet of **10 drones**. A drone is capable of carrying devices, other than cameras, and capable of delivering small loads. For our use case **the load is medications**.

A **Drone** has:
- serial number (100 characters max);
- model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
- weight limit (500gr max);
- battery capacity (percentage);
- state (IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING).

Each **Medication** has: 
- name (allowed only letters, numbers, ‘-‘, ‘_’);
- weight;
- code (allowed only upper case letters, underscore and numbers);
- image (picture of the medication case).

Develop a service via REST API that allows clients to communicate with the drones (i.e. **dispatch controller**). The specific communicaiton with the drone is outside the scope of this task. 

The service should allow:
- registering a drone;
- loading a drone with medication items;
- checking loaded medication items for a given drone; 
- checking available drones for loading;
- check drone battery level for a given drone;

> Feel free to make assumptions for the design approach. 

---

### Requirements

While implementing your solution **please take care of the following requirements**: 

#### Functional requirements

- There is no need for UI;
- Prevent the drone from being loaded with more weight that it can carry;
- Prevent the drone from being in LOADING state if the battery level is **below 25%**;
- Introduce a periodic task to check drones battery levels and create history/audit event log for this.

---

#### Non-functional requirements

- Input/output data must be in JSON format;
- Your project must be buildable and runnable;
- Your project must have a README file with build/run/test instructions (use DB that can be run locally, e.g. in-memory, via container);
- Required data must be preloaded in the database.
- JUnit tests are optional but advisable (if you have time);
- Advice: Show us how you work through your commit history.

---

# Solution
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
The project can be run by your preferred IDE or by navigating to the root directory 
and running 
- `./gradlew clean` then 
- `./gradlew bootRun`
## How to run tests
- if you are using linux or mac, navigate to the root of the project and execute `./gradlew`
- for windows users, execute `gradle.bat`

***Post man Link***
`https://www.getpostman.com/collections/bb11ad8ba7a5d18317c7`


# Deployment

### Create a runner from git for auto-deploy
- under repo/settings, got to actions and select runner... create one and follow the instructions to install the runner on the VM
- after successful config.. exec 
    - `sudo ./svc.sh install`
    - `sudo ./svc.sh start`
- create a push/pr-merge to main to initiate the runner
### Nginx
- Install nginx `sudo apt-get install nginx`
- Install systemd `sudo apt-get install systemd`
### Install Java
- configure the server for springboot `sudo apt install openjdk-11-jre openjdk-11-jdk`
### Add new User where the application will be runnig
- `adduser drone`
- `usermod -a -G sudo drone`
### Create service file to start the app
- create `/etc/systemd/system/drone_service.service` and add the following config
    ```
    [unit]
    Description=This is a Drone spring app
    Boot
    After=syslog.target
    After=network.target[Service]
    StartLimitIntervalSec=0

    [Service]
    Type=simple
    Restart=always
    RestartSec=1
    User=drone
    StandardOutput=syslog
    StandardError=syslog
    SyslogIdentifier=drone_service
    ExecStart=/usr/bin/java -jar /root/actions-runner/_work/Drones/Drones/build/libs/the-drone-0.0.1-SNAPSHOT.jar

    [Install]
    WantedBy=multi-user.target
    ```
    - Enable autorestart by `systemctl enable drone_service.service`
### Create nginx config file
- create `/etc/nginx/sites-available/drone` file and add the below setup:
    ```
    server {
        listen 80;
        listen [::]:80;
        server_name <servername>;

            location / {
            proxy_pass http://localhost:8080/;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
            proxy_set_header X-Forwarded-Port $server_port;
        }

    }
    ```
- link the file to sites-enabled by `ln -s /etc/nginx/sites-available/drone /etc/nginx/sites-enabled`

- push a commit to the repo to initiate the runner