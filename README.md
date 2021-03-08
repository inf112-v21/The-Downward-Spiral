## Getting started

### Building
This project uses maven.
The project should build when you pull it in intellij.

But if it does not work properly you can build it in the terminal with:
### `mvn clean build`

### Running

Run the main class in `src/main/java/inf112.skeleton.app`

There are two commands you can run.
To start a server write `s` and hit enter
If you want to play on the same computer as the server is running as you need to enable multiple instances of main.

in intellij you can do this by going to `Run -> Edit Configuration -> Modify options`
Click the first option `Allow multiple instances`.
Remember to hit `Apply` in the bottom right corner.

Now you can run another main and choose to play as a client instead.
Run main and write `c` then hit enter.

If you don't want to have to instances of main on the computer that is hosting you can:
Write `sc` to start a server and connect.
One thing to note is that the console wil get very cluttered in this mode because you get information and prints from both server and client. 
It is reccomended to use two instances istead of this. 

From here you can run as many more main as you want to add more players.
This should also work across all computers on the same network without any further instructions.
If you can't connect then try looking at the [errorhandling](#known-buggs-and-possible-solutions) below.

If you want to play from multiple computers on different networks then you need to portforward port `27960`.
Then you can change the local ip in `RoboRally` at string `serverIP` to the IP of the server.
Now you should be able to connect to the other pc by running and choosing client as described above.

___

# Description 

RoboRally is a board game where the aim is to collect flags to win the game.
The board contains obstacles like walls, holes and lasers.
The player pick cards from a random collection to program the robot to move.
The cards can move the robot forwards, backwards or rotate the robot. Robots can also take damage, and their current
health will affect how you can influence the robot. The fastest player to collect all flags wins.
The classic board game can have 2-8 players.

# Known buggs and possible solutions

### Running the server error
There is an error when starting the server. 
This error seems like its kryonets faults, and is not harming the program in any way.
To fix this error:
On intellij go to
`Run -> Edit Configuration -> Modify options -> VM Options`.
Then add "--illegal-access=deny".
Hit apply.


### Error when trying to connect
If you can't connect to the server, and you know that the portforwarding is correct or if you are on the same network,
then you might need to add an exception in your firewall/antivirusprogram.
You can also just disable it for a short period while you play/test.
This might need to be done on both the server pc and client pc, and server and client need to be restarted.

From here on you will be guided in the terminal of what to do next to be able to play.

### Error running on mac

Error occur when trying to run on mac.
Try to add -XstartOnFirstThread as a VM option.

On intellij go to
`Run -> Edit Configuration -> Modify options -> VM Options`.
Then add "-XstartOnFirstThread".

Hit apply.


