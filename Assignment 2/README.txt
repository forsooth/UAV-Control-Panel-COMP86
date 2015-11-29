README

This program is meant to be a minimally-functional simulator of
a UAV control panel. It contains a main view window which is
a rectangle (default size of 800x600) showing the location of
various planes, on a background of random noise. The selected
plane is highlighted, and buttons on four panels around the 
view window give the possible commands to send to the vehicle.
The left side also contains a scrollable list with the names
of all of the planes. All of these controls output to standard
out when the user interacts with them. 

The architecture of the program is split into five classes:
Main.java
        Does what one would expect. Instantiates iteself and 
        sets up the gui elements before packing the JFrame and
        displaying the window.

JCanvas.java
        Uses the Graphics methods of the JPanel of which it
        is an extension, and draws the planes and background
        and labels for the planes using Graphics2D.

ButtonPanel
        A custom JPanel using a FlowLayout to space out buttons,
        with a routine to easily add new PrintButton.

PrintButton
        A button hooked up to an ActionListener which prints
        out the text of the button when pressed.

PlaneList
        A JScrollPane containing a list of plane names, which
        uses the canvas to draw a certain selected airplane
        in red.

Diagrams of Inheritance:
             --------
            | JFrame |
             --------
                |
                v
              ------
             | Main |
              ------

           -------------
          | JScrollPane |
           -------------
                 |
                 v
            -----------
           | PlaneList |
            -----------

              --------
             | JPanel |
              --------
           /            \
          v              v
       --------      -------------
     | JCanvas |    | ButtonPanel |
       --------      -------------

JButton
PrintButton


Aggregation Hierarchy Diagram:

                        Main
                          |
        PlaneList   —   JCanvas    —    ButtonPanel
                                            |
                                        PrintButton