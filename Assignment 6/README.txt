================================================================================
README
================================================================================

Matt Asnes
Assignment 6

================================================================================
Game Instructions
================================================================================

The game is played by keeping a set of planes alive for as long as possible. The
planes face many obstacle: mountains that can be hit, no-fly zones which send 
out AI enemy jets, losing all velocity and stalling, crashing, going too high,
or hitting the game bounds. The player's task is to navigate as many of these
planes as possible around the field for as long as possible, adding or deleting
planes as they see fit. The game ends when the last plane dies. 

Planes can die in many ways:
        Reaching an altitude of 0
        Reaching a maximum altitude (80,000m for Plain Planes, 200,000 for UFOs)
        Hitting another plane
        Being chased down by a fighter jet
        Hitting a mountain at a point higher than the plane's altitude
        Being set to a velocity of zero and stalling
        Going out of the game bounds

As mentioned above, planes can be added, but each added plane detracts from 
total score, so make sure you can keep the new plane alive long enough for it to
count! New planes are created with a random initial velocity to add randomness.

Tip: Destroy the enemy planes by crashing them into mountains!


================================================================================
UI Design
================================================================================

Personally, I think the user interface is pretty functional. Things scale when
the window is pulled, to a reasonable degree, and the design fits in with the
rest of the operating system. 

I created careful controls for plane data objects. The program includes 2D grids
for selecting velocity, a slider locked from -89 degrees to 89 degrees for pitch, 
and a circle selector for angle. This not only makes the controls intuitive, but
also engineers out many use errors. The UI elements sometimes have text boxes 
attached, for more accurate input, but these round down to allowed values. 
Similarly, when creating a new plane the user can only select a radio button for
the type of plane to be created. 

Within the map, I tried to communicate as much information as possible without 
text. For example, no-fly zones are dangerous and so they are drawn in red and
stand out. Enemy planes flash red to draw attention. Mountains have a 
topographic element that gives a rough idea as to their height, with labels 
attached if one needs to know exactly. The map also commands attention in the
program, leaving the (hopefully intuitive) controls to be used only as 
necessary. 


================================================================================
Program Overview
================================================================================

This program is meant to be a mostly-functional game/simulator of a UAV control 
panel. It contains a main view window which is a rectangle (default size
of 800x600) showing the location of various planes, on a background of random 
noise. A plane can be selected from the scrollable list, or selected by clicking
in the bounds of the plane polygon with the mouse. The map also contains world
bounds, represented as big gray rectangles, no-fly zones (red striped areas),
and mountains (randomly generated brown polygonal topographic maps of 
mountains). 

The selected plane's properties can be changed by means of the many controls 
on the right panel. Specifically, a given plane's velocity, angle, and pitch 
can be controlled, and these update the plane's position, angle, and altitude 
respectively each timer tick. 

The map is centered around whatever plane is currently selected, and follows it
as it moves (i.e. the plane stays at the center and all other planes move 
relative to it). The map can also be zoomed, which preserves the location and
distance between various objects, but draws everything bigger — the view is 
still centered on the selected plane. If two planes have the same (x, y) 
position and an altitude within 10 meters of each other, they are said to have
collided and both planes are deleted.

Several "filters" can be applied to the background of the main view
to change the brightness/contrast. On the left panel, there are three buttons: 
Add a new plane, delete the selected plane, and play/pause the animation. 
These do what one would expect. In particular the pause button stops the main
timer, so no positions or other properties are updated, and a new filter cannot
be selected. Several other buttons appear on the bottom panel, but were not 
implemented as they would require actual planes.

When the game is lost, the map is replaced with a GAME OVER message and a score
report.

================================================================================
Architecture
================================================================================

The architecture of the program is split into 30 classes:

Main
        Does what one would expect. Instantiates iteself and 
        sets up the gui elements before packing the JFrame and
        displaying the window. Also controls the main timer ticks
        of the program.

Map
        Uses the Graphics methods of the JPanel of which it
        is an extension, and draws the planes onto a 'map' field
        which is just a random scattering of noise. Also deals with collisions
        of planes, and most plane behavior. Controls the game state.

ControlPanel
        A simple extension of JPanel that has a nice titled border
        and padding to make things look nice.

PlaneList
        A list from which values can be selected. The list is able to 
        scroll to accommodate large number of values, and can have items 
        added or removed. 

PrintButton
        A basic extension of JButton that prints whatever text it is
        constructed with.

FilterButton
        A button that functions similarly to PrintButton, but also
        changes the map background when clicked in addition to printing
        out a message.

ZoomButton
        A button that functions similarly to PrintButton, but also
        changes the zoom level of the map when clicked, depending on a
        boolean value chosen when the button is created.

DeletePlaneButton
        A button which deletes the selected plane. This both removes the
        plane from the plane list to which the button is connected and removes
        the plane from the main map.

AnimationToggleButton
        A button which toggles the play/pause state of the main animation and
        update functions. This effectively pauses the program's main timer. 
        This also prevents a new filter from being selected.

AddPlaneButton
        Opens the NewPlaneDialog window to allow the selection of properties
        of a new plane. 

NewPlaneDialog
        Opens a small frame object which contains options for selecting the
        position and class of a new plane. The "OK" button creates this plane
        at the given coordinates, while the "Cancel" or "X" button will close
        the window. Options are saved, and clicking "OK" multiple times will
        create multiple planes.

Plane
        An abstract class holding a plane's information, like position,
        velocity, altitude, color, etc. The plane class draws the plane
        on the map when asked, and controls all modification of a given
        plane's attributes.

PlainPlane
        A boring implementation of the Plane abstraction that changes from
        the abstraction only by drawing a polygon and being instantiable.

UFO
        Another implementation of Plane, this time shaped like a UFO and
        changing color randomly to various shades of dark green.

Enemy
        A subclass of Plane which differs from the others in that it is AI
        controlled instead of player controlled. It follows a given target
        plane, and flashes red instead of green.

AngleSelector
        A custom UI component shaped like a circle, which allows the user
        to select an angle by moving a line around a unit circle.

Selector2D
        Another custom UI component, allowing the user to select an (x, y)
        coordinate pair by moving a bright green point around a 2D cartesian
        grid.

AnglePanel
PitchPanel
VelocityPanel
        Three similar classes, which create components, add them to a panel,
        and then report the changes in those components to Map so the selected
        plane can have its properties changed. The angle panel uses an angle
        selector, the pitch panel uses a default JSlider and a text box, and the
        velocity panel uses a 2D selector and 2 text boxes. All also have nice 
        titled borders. 

StatePanel
        A panel which reports on the status of a given game, i.e. its runtime,
        and the number of planes destroyed/left.

GameBorder
        A GameBorder is a simple rectangle drawn on the map, which destroys 
        planes.

Mountain
        Mountains are brown polygons that can destroy planes in an 
        altitude-dependent way. They are randomly generated by placing a set
        (random) number of points around a circle and pushing them in or
        out from the center by a random amount. The polygon of a mountain is
        drawn concentrically to create a logarithmic topographical map of the
        game world.

NoFlyZone
        A no-fly zone is a red rectangle filled with repeating diagonal stripes.
        If a player plane flies over one, it spawns an enemy fighter jet.

GameEvent
Gamekeeper
GameListener
        These three classes are the implementation of a listener for when the
        game ends based on the number of planes left. When the game ends, they
        report to the various components in the program, which behave in ways
        that prohibit further meaningful player action.


================================================================================
Diagrams of Inheritance:
================================================================================
(Sorry for the lack of ASCII art, it would be unweildy)

JFrame
        Main
        NewPlaneDialog

JPanel
        Map
        StatePanel
        AnglePanel
        ControlPanel
        PitchPanel
        VelocityPanel
        Selector2D
        AngleSelector

JScrollPane
        PlaneList

Plane
        PlainPlane
        UFO
        Enemy

JButton
        PrintButton
        FilterButton
        ZoomButton
        DeletePlaneButton
        AddPlaneButton
        AnimationToggleButton

EventObject
        GameEvent

EventListener
        GameListener

No Inheritance
        Gamekeeper
        Mountain
        NoFlyZone
        GameBorder

================================================================================
Diagram of Aggregation Hierarchy:
================================================================================

Main
        Map
                PlainPlane
                UFO
                Enemy
                NoFlyZone
                Mountain
                GameBorder
                GameEvent
                GameListener
                Gamekeeper
        StatePanel
        ControlPanel
                AnglePanel
                        AngleSelector
                PitchPanel
                VelocityPanel
                        Selector2D
                PlaneList
                PrintButton
                FilterButton
                ZoomButton
                AddPlaneButton
                        NewPlaneDialog
                DeletePlaneButton
                AnimationToggleButton

================================================================================
Collaboration Relationships
================================================================================

(AnglePanel, VelocityPanel, PitchPanel) and Map
Map and Plane
Map and PlaneList
FilterButton and Map
ZoomButton and Map
Selector2D and VelocityPanel
AngleSelector and AnglePanel
AddPlaneButton and Map
AddPlaneButton and PlaneList
DeletePlaneButton and Map
DeletePlaneButton and PlaneList
AnimationToggleButton and Main's Timer
Map, Gamekeeper, GameEvent, and GameListener
Map and NoFlyZone
Map and Mountain
Map and GameBorder
Gamekeeper and every object implementing GameListener

================================================================================
Data Encapsulation
================================================================================

Planes are the only class with access to the inner workings of the planes, like
the algorithms for drawing the planes and for increasing position based on
pitch and velocity.

The map is the only class with access to the planes, all other classes must work
through Map. It also holds all other map objects, like mountains and enemies.
The map controls the triggering of GameEvents to other components, and keeps
all flow of GameEvents to itself.

Selector2D and AngleSelector respectively are the only classes which know the
information necessary for drawing lines on their components, like the x
and y position of the point to draw to. They only output a value-mapped version.

PitchPanel, AnglePanel, and VelocityPanel are the only classes which know how
they communicate data to Map, and how data is communicated between the elements
on the panels.

The AddPlaneButton is the only class which interacts with the NewPlaneDialog
window, and controls its actions.

The DeletePlaneButton and AddPlaneButton classes interact with PlaneList to
add and remove planes, but PlaneList controls all of the representations of
planes in the list, and these interactions are done via methods.

NoFlyZone, Mountain, and GameObject respectively hide their locations and
drawing data. 

Gamekeeper keeps all of the components listening for GameEvents to itself.