## Getting started

### Building
This project uses maven.
The project should build when you pull it in intellij.

But if it does not work properly you can build it in the terminal with:
### `mvn clean install`

### Running

Run the main class in `src/main/java/inf112.skeleton.app`

There are two commands you can run.
To start a server you can simply click the `host` button
or if the menu for some reasons don't work simply hit the button `s`.
If you want to play on the same computer as the server is running as you need to enable multiple instances of main.
The game window will close when you use this option, and server will continue in the terminal.

in intellij you can do this by going to `Run -> Edit Configuration -> Modify options`
Click the first option `Allow multiple instances`.
Remember to hit `Apply` in the bottom right corner.

Now you can run another main and choose to play as a client instead.
Run main and hit the button `Connect`
or if the menu for some reasons don't work simply hit the button `c`.

If you don't want to have two instances of main on the computer that is hosting you can:
Click `Host & Play` in the menu.
or if the menu for some reasons don't work simply hit the button `SPACE`.
One thing to note is that the console wil get very cluttered in this mode because you get information and prints from both server and client. 
It is reccomended to use two instances for now.

From here you can run as many more main as you want to add more players.
This should also work across all computers on the same network without any further instructions.
If you can't connect then try looking at the [errorhandling](#known-bugs-and-possible-solutions) below.

If you want to play from multiple computers on different networks then you need to portforward port `27960`.
Then you can change the local ip in `GameScreen` at the line
`this.networkConnection = new NetworkConnection();`
to `this.networkConnection = new NetworkConnection("IP OF SERVER");`'
This should be a string.
Now you should be able to connect to the other pc by running and choosing client as described above.

When you have collected all the flags you are greeted with a victory screen and a `restart` button.
The restart button does however not shut down the server. So it's recommended to stop main and
run the game again.

If there are any problems try looking at [errorhandling](#known-bugs-and-possible-solutions).

___

# Description 

RoboRally is a board game where the aim is to collect flags to win the game.
The board contains obstacles like walls, holes and lasers.
The player pick cards from a random collection to program the robot to move.
The cards can move the robot forwards, backwards or rotate the robot. Robots can also take damage, and their current
health will affect how you can influence the robot. The fastest player to collect all flags wins.
The classic board game can have 2-8 players.

# Known bugs and possible solutions

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


## Game logic bugs

### Server does not update won/loss graphics for other players
Other players can't see the updated graphics if you win or lose.

### The logic to walls is implemented but not correctly working - Causing a chain bug
The checks for walls is implemented, however there is a bug where this
does not work properly. We have disabled walls for now, but the checks is
still there. So the test for this would not work either. This may cause some problems for example belts and gears.
Gears and belts does not function if a player move through a wall to get there,
because the program is only returning that it is an invalid move.
The player can now move out of the map,
since walls and "out of map" is pretty much the same at the moment.
This also makes the test for player not going outside the map invalid.

### The server never shuts down.
The server never shuts down even if all the player have "won" and gone back to main menu.
The server is already open if the host tries to click on `host & play` again.
The best way is to close the game and stop main after you have finished one game.
