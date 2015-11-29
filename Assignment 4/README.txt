================================================================================
README
================================================================================

Matt Asnes
Assignment 4

================================================================================
Overview
================================================================================

This program is meant to be a somewhat-functional simulator of
a UAV control panel. It contains a main view window which is
a rectangle (default size of 800x600) showing the location of
various planes, on a background of random noise. A plane can be selected
from the scrollable list, and the selected plane's properties can be changed
by means of the many controls on the right panel. Specifically, a given plane's
velocity, angle, and pitch can be controlled, and these update the plane's 
position, angle, and altitude respectively each timer tick. Several "filters"
can be applied to the background of the main view. On the left panel, there are
three buttons: Add a new plane, delete the selected plane, and play/pause the
animation. These do what one would expect. Several other buttons appear on the
bottom panel, but were not implemented as they would require actual planes.

================================================================================
Architecture
================================================================================

The architecture of the program is split into 18 classes:

Main
        Does what one would expect. Instantiates iteself and 
        sets up the gui elements before packing the JFrame and
        displaying the window. Also controls the main timer ticks
        of the program.

Map
        Uses the Graphics methods of the JPanel of which it
        is an extension, and draws the planes onto a 'map' field
        which is just a random scattering of noise. 

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


================================================================================
Diagrams of Inheritance:
================================================================================
(Sorry for the lack of ASCII, it would be unweildy)

JFrame
        Main
        NewPlaneDialog

JPanel
        Map
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

JButton
        PrintButton
        FilterButton
        DeletePlaneButton
        AddPlaneButton
        AnimationToggleButton

================================================================================
Diagram of Aggregation Hierarchy:
================================================================================

Main
        Map
                PlainPlane
                UFO
        ControlPanel
                AnglePanel
                        AngleSelector
                PitchPanel
                VelocityPanel
                        Selector2D
                PlaneList
                PrintButton
                FilterButton
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
Selector2D and VelocityPanel
AngleSelector and AnglePanel
AddPlaneButton and Map
AddPlaneButton and PlaneList
DeletePlaneButton and Map
DeletePlaneButton and PlaneList
AnimationToggleButton and Main's Timer

================================================================================
Data Encapsulation
================================================================================

Planes are the only class with access to the inner workings of the planes, like
the algorithms for drawing the planes and for increasing position based on
pitch and velocity.

The map is the only class with access to the planes, all other classes must work
through Map.

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
