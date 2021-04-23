## Getting started

### Building
This project uses maven.
The project should build when you pull it in intellij.

But if it does not work properly you can build it in the terminal with:
### `mvn clean install`

### Running

Run the main class in `src/main/java/inf112.skeleton.app`

To start a server you can simply click the `host` button.
If you want to play on the same computer as the server is running as you need to enable multiple instances of main.
The game window will close when you use this option, and server will continue in the terminal.

in intellij you can do this by going to `Run -> Edit Configuration -> Modify options`
Click the first option `Allow multiple instances`.
Remember to hit `Apply` in the bottom right corner.

Now you can run another main and choose to play as a client instead.
Run main and hit the button `Connect`
Here you can choose between localhost or a custom ip.
Next screen is to select map, this map needs to be the same for each player to work properly.

If you don't want to have two instances of main on the computer that is hosting you can:
Click `Host & Play` in the menu.
When you click host and play you also need to select the map. Remember this map should be the same for all connected players.
One thing to note is that the console wil get very cluttered in this mode because you get information and prints from both server and client. 
It is recommended to use two instances for a more clean console.

From here you can run as many more main as you want to add more players.
This should also work across all computers on the same network without any further instructions.
If you can't connect then try looking at the [errorhandling](#known-bugs-and-possible-solutions) below.

If you want to play from multiple computers on different networks then you need to portforward port `27960`.
Then you can choose the `connect` option from the menu and write in the IP of the server.
Then click `connect` or hit `Enter`
Now you should be able to connect to the other pc by running and choosing client as described above.

When you have collected all the flags you are greeted with a victory screen and a `restart` button.
The restart button does however not shut down the server. So it's recommended to stop main and
run the game again.
Note that the colors is not the same for each client. The client bound to the player will always be blue,
and then the rest is loaded accordingly. 

Moving players with WASD is only availible in debug mode.
If there are any problems try looking at [errorhandling](#known-bugs-and-possible-solutions).

___
# Description 

RoboRally is a board game where the aim is to collect flags to win the game.
The board contains obstacles like walls, holes and lasers.
The player pick cards from a random collection to program the robot to move.
The cards can move the robot forwards, backwards or rotate the robot. Robots can also take damage, and their current
health will affect how you can influence the robot. The fastest player to collect all flags wins.
The classic board game can have 2-8 players.

## How to play
First you have to connect to a server or create your own as described above.
You should wait for all players to connect before you continue.
When all the player are connected each player can hit `Enter` to draw cards.
Now you will see the cards on the right of the game and here you can click on them to choose what card to start with.
The order you click on the card is the order you want your robot to move.
After you are done picking out cards, each player must click execute.
Note that to click execute the program requires you to have picked 5 cards.
Now all the players should move in turn, and the cards will be executed.
After this the round will end, and you will get new cards.
To win you need to collect all the flags in right order from 1-3.
Each time you go outside the map or in a hole you will lose a lifetoken.
You also lose a lifetoken when you have been damaged 10 times by a laser.
If you lose all the lifetokens you will die and the player will no longer be on the screen.

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

### Game-logic does not apply twice for each card
There is only exectued one logic for each cell each card,
since there can not be two types of logic that applies to one cell in the current code.
This is mainly a problem for the map Factory Rejects.
This map has a lot more going on for each cell, which causes some logic to fail.

### The server never shuts down.
The server never shuts down even if all the player have "won" and gone back to main menu.
The server is already open if the host tries to click on `host & play` again.
The best way is to close the game and stop main after you have finished one game.

## Using the exit button don't close terminal or server
The exit button only closes the game window.
You need to manually close main after.

## The graphics for the victory screen is somewhat broken.
The buttons and functionality is working properly, but the screen is behaving strange.

## Players who go into holes is not rendered before the end of the round for other players
If a player dies, then the graphics for that player does not render properly for other players
before the end of that round.