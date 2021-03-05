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

in intellij you can do this by clicking the `main with an arrow down` in the top right corner, between the build button and run button.
From there you can choose `edit configurations`. Then `Modify options` on the right.
Click the first option `Allow multiple instances`.
Remember to hit `Apply` in the bottom right corner.

Now you can run another main and choose to play as a client instead.
Run main and write `c` then hit enter.

From here you can run as many more main as you want to add more players.
This should also work across all computers on the same network without any further instructions.
If you can't connect then try looking at the errorhandling below.

If you want to play from multiple computers on different networks then you need to portforward port `27960`.
Then you can change the local ip in `RoboRally` at string `serverIP` to the IP of the server.
Now you should be able to connect to the other pc by running and choosing client as described above.

### Error when trying to connect
If you can't connect to the server, and you know that the portforwarding is correct or if you are on the same network,
then you might need to add an exception in your firewall/antivirusprogram. 
You can also just disable it for a short period while you play/test.
This might need to be done on both the server pc and client pc, and server and client need to be restarted.

From here on you will be guided in the terminal of what to do next to be able to play.

___

# Description 

RoboRally is a board game where the aim is to collect flags to win the game.
The board contains obstacles like walls, holes and lasers. 
The player pick cards from a random collection to program the robot to move.