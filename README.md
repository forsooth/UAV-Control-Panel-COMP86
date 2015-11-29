# UAV Control Panel (COMP86 Assignments 2 through 6)

### Description
Program simulating a UAV control panel for my GUI design class. It was built up
in stages, where each stage had a set goal:

Assignment 2 — Make an empty user interface that prints messages to standard output when they are clicked. Also include a  drawing area in the center, and do something with it.

Assignment 3 — Add planes to the blank drawing area, and make this the map for the UAV program. Add controls that do things other than print messages.

Assignment 4 — Add a main timer that updates the map, a control to pause/play it, and some controls that become unenabled when it is paused.

Assignment 5 — Allow planes to be picked with mouse clicks. Also allow collisions between planes, and zoom the map in and out when the user clicks the relevant controls.

Assignment 6 — Turn the simulator into a functioning game, add a panel to keep track of game state, and add some creative extra features.

See the README.txt files in the respective assignment directories for more specific detail about architecture, content, and implementation.

### Running
Each assignment directory contains a collection of Java files which will compile into the complete program for that stage. Specifically: 
`javac Main.java`
`java Main`
will compile and run any of the phases. 