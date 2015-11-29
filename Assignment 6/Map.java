/*
 * Matt Asnes
 * COMP-86 Assignment 6
 *
 * This class works as a canvas element for Swing to make a map, built on top
 * of JPanel. It creates a series of planes, and updates them on the map in
 * a drawing loop thread. It also allows limited access to the data values in
 * the planes. Planes can be created or destroyed on the map. It also controls
 * the addition and behavior of various non-player controlled map elements.
 */

import java.awt.*;
import java.awt.font.*;
import java.util.Random;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Map extends JPanel implements MouseListener, GameListener {

        //Size of the map on which the game is to be played
        private static final int G = 2000;

        private int numPlanes, totalPlanes, initPlanes;
        private int w, h;
        private int score;
        private int numTimeTicks;
        private Gamekeeper gamekeeper;
        private int filterVal;
        private boolean isPlaying;
        private double scale, offsetx, offsety;
        private ArrayList<Plane> planes;
        private ArrayList<NoFlyZone> noFly;
        private ArrayList<Mountain> mountains;
        private ArrayList<GameBorder> borders;
        private ArrayList<Enemy> enemies;
        private Random r;
        // The index of the currently active plane
        private int selectedIndex = 0;

        public Map (int numPlanes) {
                this.numPlanes = numPlanes;
                this.totalPlanes = numPlanes;
                this.initPlanes = numPlanes;
                scale = 1.0;
                filterVal = 150;
                score = 0;
                offsetx = 0;
                offsety = 0;
                isPlaying = true;
                numTimeTicks = 0;
                planes = new ArrayList<Plane>();
                noFly = new ArrayList<NoFlyZone>();
                mountains = new ArrayList<Mountain>();
                borders = new ArrayList<GameBorder>();
                enemies = new ArrayList<Enemy>();

                r = new Random();
                gamekeeper = new Gamekeeper();

                // Make a bunch of planes evenly spaced out on the map
                for (int i = 0; i < numPlanes - 6; i++) {
                        PlainPlane p = new PlainPlane(-200 + 100 * i, -100, 0, Integer.toString(i), i);
                        p.setSpeed(r.nextGaussian() * 2 + 0.5, r.nextGaussian() * 2 + 0.5);
                        planes.add(p);
                }

                for (int i = numPlanes - 6; i < numPlanes; i++) {
                        UFO ufo = new UFO(-600 + 100 * i, -200, 0, Integer.toString(i), i);
                        ufo.setSpeed(r.nextGaussian() * 2 + 1, r.nextGaussian() * 2 + 1);
                        planes.add(ufo);
                }

                int numMountains = G / 40;
                for (int i = 0; i < numMountains; i++) {
                        mountains.add(new Mountain(r.nextInt() % G, r.nextInt() %  G, r.nextInt(40000) + 2, r.nextInt(G / 12) + 25));                        
                }

                int numNoFly =  G / 40;
                for (int i = 0; i < numNoFly; i++) {
                        int w, h;
                        w = r.nextInt(G / 24) * 5 + 10;
                        h = r.nextInt(G / 24) * 5 + 10;
                        noFly.add(new NoFlyZone(r.nextInt() % G, r.nextInt() % G, w, h));                        
                }

                // Position the borders to be around the main play field
                // right
                GameBorder b1 = new GameBorder(G, -G, 1.5 * G, 2 * G, Color.DARK_GRAY);
                // top
                GameBorder b2 = new GameBorder(-G - 1.5 * G, -G - 1.5 * G, 5 * G, 1.5 * G, Color.DARK_GRAY);
                // left
                GameBorder b3 = new GameBorder(-G - 1.5 * G, -G, 1.5 * G, 2 * G, Color.DARK_GRAY);
                // bottom
                GameBorder b4 = new GameBorder(-G - 1.5 * G, G, 5 * G, 1.5 * G, Color.DARK_GRAY);
                borders.add(b1);
                borders.add(b2);
                borders.add(b3);
                borders.add(b4);

                addMouseListener(this);

        }
        
        // Store a font for numbering the planes
        private final Font font = new Font("SansSerif", Font.PLAIN, 16);

        private void doDrawing(Graphics g) {

                Graphics2D g2d = (Graphics2D) g;

                Dimension size = getSize();

                w = size.width;
                h = size.height;
                
                // Protect against the case where there are no planes
                if (planes.size() > selectedIndex) {
                        offsetx = w / 2 / scale - planes.get(selectedIndex).getX();
                        offsety = h / 2 / scale - planes.get(selectedIndex).getY();
                }

                // Fill the background with random noise. Simple probability
                // math says that on average 1/8 of the canvas will be filled with
                // a random color.
                for (int i = 0; i <= 0.25 * w * h; i++) {
                        // Grab a new point at a random position
                        int x = Math.abs(r.nextInt()) % w;
                        int y = Math.abs(r.nextInt()) % h;
                        // Make a random color brighter than rgb(150, 150, 150)
                        int R, G, B;
                        R = Math.abs(r.nextInt()) % (255 - filterVal) + filterVal;
                        G = Math.abs(r.nextInt()) % (255 - filterVal) + filterVal;
                        B = Math.abs(r.nextInt()) % (255 - filterVal) + filterVal;
                        // Draw a line from (x,y) to (x,y), i.e. one point
                        g2d.setColor(new Color(R, G, B));
                        g2d.drawLine(x, y, x, y);
                }

                // Real borders get messed up by zoom, so we make our own
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, 5, h);
                g2d.fillRect(0, 0, w, 5);
                g2d.fillRect(w - 5, 0, 5, h);
                g2d.fillRect(0, h - 5, w, 5);

                // If there are no planes, don't bother with the rest of drawing
                if (planes.size() <= selectedIndex) return;

                g2d.scale(scale, scale);
                g2d.translate(offsetx, offsety);

                for (int i = 0; i < mountains.size(); i++) {
                        mountains.get(i).draw(g);
                }

                for (int i = 0; i < noFly.size(); i++) {
                        noFly.get(i).draw(g);
                }

                // We want black airplanes labeled with our font.
                g2d.setColor(Color.BLACK);
                g2d.setFont(font);

                // Draw the airplane polygon NUM_PLANES times, at random coordinates
                for (int i = 0; i < numPlanes; i++) {
                        if (i != selectedIndex) {
                                planes.get(i).draw(g);
                        }
                }

                for (int i = 0; i < enemies.size(); i++) {
                        enemies.get(i).draw(g);
                }

                planes.get(selectedIndex).setSelected();
                planes.get(selectedIndex).draw(g);

                for (int i = 0; i < borders.size(); i++) {
                        borders.get(i).draw(g);
                }
        }

        public void paintEnd(Graphics g) {
                Dimension size = getSize();

                w = size.width;
                h = size.height;

                Graphics2D g2d = (Graphics2D) g.create();

                g2d.setColor(new Color(150, 0, 0));
                g2d.fillRect(0, 0, w, h);

                // Real borders get messed up by zoom, so we make our own
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, 5, h);
                g2d.fillRect(0, 0, w, 5);
                g2d.fillRect(w - 5, 0, 5, h);
                g2d.fillRect(0, h - 5, w, 5);

                g2d.scale(10, 10);
                g2d.setColor(Color.WHITE);
                g2d.drawString("GAME OVER", w / 160, h / 17);
                g2d.scale(0.25, 0.25);
                g2d.drawString("Score: " + score, w / 27, h / 7); 

        }

        public void tickAll(PlaneList list) {

                numTimeTicks++;

                if (numPlanes <= 0 && isPlaying) {
                        boolean lost = true;
                        score = 50 * numTimeTicks + 100 * (initPlanes - totalPlanes);
                        GameEvent e = new GameEvent(this, lost, score);
                        isPlaying = false;
                        gamekeeper.fireGameEvent(e);
                }

                // Loop label for easy control flow
                loopEnemies:
                for (int i = 0; i < enemies.size(); i++) {
                        enemies.get(i).tick();

                        // collisions on finding the target
                        for (int j = 0; j < numPlanes; j++) {
                                double x = enemies.get(i).getX();
                                double y = enemies.get(i).getY();
                                if (planes.get(j).isAt(x, y, 1.0, 0, 0)) {
                                        for (int k = 0; k < enemies.size(); k++) {
                                                if (enemies.get(k).targetEquals(planes.get(j))) {
                                                        enemies.remove(k);
                                                }
                                        }

                                        planes.remove(j);
                                        list.remove(j);
                                        numPlanes--;
                                        selectedIndex = 0;
                                        continue loopEnemies;

                                }
                        }

                        // mountain collisions
                        for (int j = 0; j < mountains.size(); j++) {
                                if(mountains.get(j).contains(enemies.get(i).getX(), enemies.get(i).getY(), enemies.get(i).getAltitude())) {
                                        enemies.remove(i);
                                        continue loopEnemies;
                                }
                        }
                }

                loopPlanes:
                for (int i = 0; i < numPlanes; i++) {
                        planes.get(i).tick();
                        double x = planes.get(i).getX();
                        double y = planes.get(i).getY();

                        // If a plane stops, it stalls and dies
                        if (planes.get(i).getSpeed() < 0.5) {
                                planes.remove(i);
                                list.remove(i);
                                numPlanes--;
                                selectedIndex = 0;
                                continue loopPlanes;
                        }

                        // Check altitudes, if a plane's altitude is too big or too small, destroy it
                        if (planes.get(i).getAltitude() <= 0 || planes.get(i).getAltitude() >= planes.get(i).getMaxAlt()) {
                                planes.remove(i);
                                list.remove(i);
                                numPlanes--;
                                selectedIndex = 0;
                                continue loopPlanes;
                        }

                        // Check plane-plane intersections
                        for (int j = 0; j < numPlanes; j++) {
                                // offset and scale are default because we just want to compare the coordinates
                                if (j != i && planes.get(j).isAt(x, y, 1.0, 0, 0)) {

                                        // Compare the altitudes, break if no collision
                                        double dAlt = planes.get(j).getAltitude() - planes.get(i).getAltitude();
                                        if (Math.abs(dAlt) > 10) break;

                                        // There was a collision! Delete the planes
                                        System.out.println("Collision!");
                                        if (j > i) {
                                                // Order of removals is important!
                                                planes.remove(j);
                                                planes.remove(i);
                                                list.remove(j);
                                                list.remove(i);
                                                selectedIndex = 0;
                                                numPlanes -= 2;
                                                continue loopPlanes;
                                        } else {
                                                planes.remove(i);
                                                planes.remove(j);
                                                list.remove(i);
                                                list.remove(j);
                                                selectedIndex = 0;
                                                numPlanes -= 2;
                                                continue loopPlanes;
                                        }
                                }
                        }   

                        // border collisions
                        for (int j = 0; j < borders.size(); j++) {
                                if (borders.get(j).contains(planes.get(i).getX(), planes.get(i).getY())) {
                                        planes.remove(i);
                                        list.remove(i);
                                        numPlanes--;
                                        selectedIndex = 0;
                                        continue loopPlanes;
                                }
                        }

                        // mountain collisions
                        for (int j = 0; j < mountains.size(); j++) {
                                if(mountains.get(j).contains(planes.get(i).getX(), planes.get(i).getY(), planes.get(i).getAltitude())) {
                                        planes.remove(i);
                                        list.remove(i);
                                        numPlanes--;
                                        selectedIndex = 0;
                                        continue loopPlanes;
                                }
                        }

                        // no fly zone collisions
                        int k;
                        for (k = 0; k < noFly.size(); k++) {
                                if (noFly.get(k).contains(planes.get(i).getX(), planes.get(i).getY())) {
                                        planes.get(i).setColor(Color.BLUE);

                                        if (planes.get(i).canSpawnEnemy()) {
                                                NoFlyZone n = noFly.get(k);
                                                createEnemy(planes.get(i), (int)(n.getX() + n.getWidth() / 2), (int)(n.getY() + n.getHeight() / 2));
                                        }
                                        continue loopPlanes;
                                }
                        }

                        if (k == noFly.size()) {
                                planes.get(i).setColor(Color.BLACK);
                        }
                }
        }

        @Override
        public void paintComponent(Graphics g) {
        
                super.paintComponent(g);
                if (isPlaying)
                        doDrawing(g);
                else 
                        paintEnd(g);
        }

        // Get and set values

        public void setSelectedIndex(int i) {
                selectedIndex = i;
        }

        public int getSelectedIndex() {
                return selectedIndex;
        }

        public String getLabel(int i) {
                if (i >= planes.size()) return "";
                return planes.get(i).getLabel();
        }

        public int getNumPlanes() {
                return numPlanes;
        }

        public int getTotalPlanes() {
                return totalPlanes;
        }


        // Delete the currently selected plane
        public void deleteSelected() {
                if (planes.size() < selectedIndex + 1) return;
                planes.remove(selectedIndex);
                numPlanes--;
        }

        // Add a new plane at the given coordinates
        public void addPlane(int x, int y, String type) {

                if (type.equals("Plain")) {
                        PlainPlane newPlane = new PlainPlane(x, y, 0, Integer.toString(numPlanes), numPlanes);
                        newPlane.setSpeed(r.nextGaussian() * 2, r.nextGaussian() * 2);
                        planes.add(newPlane);
                } else {
                        UFO newPlane = new UFO(x, y, 0, Integer.toString(numPlanes), numPlanes);
                        newPlane.setSpeed(r.nextGaussian() * 2, r.nextGaussian() * 2);
                        planes.add(newPlane);
                }

                numPlanes++;
                totalPlanes++;

        }

        public void createEnemy(Plane target, int x, int y) {
                Enemy enemy = new Enemy(target, x, y);
                enemies.add(enemy);
        }

        public void setFilterValue(int val) {
                filterVal = val;
        }

        // Get and set information about individual planes

        public void setPlaneVelocity(double xv, double yv, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setSpeed(xv, yv);
        }

        public void setPlaneXVelocity(double xv, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setXSpeed(xv);
        }

        public void setPlaneYVelocity(double yv, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setYSpeed(yv);
        }

        public void setPlanePitch(double pitch, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setPitch(pitch);
        }

        public void setPlaneAngle(double angle, int planeIndex) {
                if (planeIndex >= planes.size()) return;
                planes.get(planeIndex).setAngle(angle);
        }

        // Zoom commands
        public void zoomIn() {
                scale *= 1.5;
        }

        public void zoomOut() {
                if (scale > 0.20) {
                        scale /= 1.5;
                }
        }

        // Select a new plane if one is clicked
        public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                for(int i = 0; i < numPlanes; i++) {
                        if(planes.get(i).isAt(x, y, scale, offsetx, offsety)) {
                                planes.get(selectedIndex).unsetSelected();
                                selectedIndex = i;
                                planes.get(i).setSelected();
                        }
                }
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void addGameListener(GameListener listener) {
                gamekeeper.addGameListener(listener);
        }

        public void gameEventOccurred(GameEvent e) {

        }
}