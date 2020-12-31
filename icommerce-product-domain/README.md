## Installation

### Pre-installation:

* Please make sure these below ports are available on your local machine:
  * jhipster-registry (8761)
  * kafka (9092)
  * zookeeper (2181)
  * keycloak (9080)
  * gateway (8080)
  * mongodb (27017)
  * postgresql (5432)
  * product (8082)
  * tracker (8081)
* We are using Docker to service the components so please make sure you installed Docker
* edit `hosts` file by adding this line `127.0.0.1 keycloak`.
  * For macOs/LinuxOS, hosts file is located at `/private/etc`
  * For windows, hosts file is located at `C:\Windows\System32\drivers\etc`

### How to start:

Start environment services:

* go to `environments` folder and run the shell script. Depending on your OS, you can run `start-mac-os.sh`
  or `start-windows-os.bat` or `start-linux-os.sh` script file.
* Please make sure all required dockers are up. you can check dockers status but this command: `docker ps -a`

```sh
$ cd environments
$ ./start-linux-os.sh
$ docker ps -a
```

Start our components:

```sh
$ cd 
$ mvn spring-boot:run
```
