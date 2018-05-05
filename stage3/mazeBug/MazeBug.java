package info.gridworld.maze;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * A <code>MazeBug</code> can find its way in a maze. <br />
 * The implementation of this class is testable on the AP CS A and AB exams.
 */


public class MazeBug extends Bug {
	public Location next;
	public boolean isEnd = false;
	public Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	public Integer stepCount = 0;
	//final message has been shown
	boolean hasShown = false;
	int[] probability = new int[4];
	private int direction[] = {Location.NORTH, Location.EAST,Location.SOUTH, Location.WEST};

	/**
	 * Constructs a box bug that traces a square of a given side length
	 * 
	 * @param length
	 *            the side length
	 */
	public MazeBug() {
		setColor(Color.GREEN);
		next = null;
	}

	/**
	 * Moves to the next location of the square.
	 */
	public void act() {
		if(stepCount == 0) {
			ArrayList<Location> locs = new ArrayList<Location>();
			locs.add(this.getLocation());
			crossLocation.push(locs);
			for(int i = 0; i < 4; i++) {
				probability[i] = 1;
			}
		}
		boolean willMove = canMove();
		if (isEnd == true) {
		//to show step count when reach the goal		
			if (hasShown == false) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		} else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		} else {
			Grid<Actor> gr = getGrid();
			if(crossLocation.size() != 0) {
				crossLocation.pop();
			}
			System.out.println(crossLocation.peek().toString());
			ArrayList<Location> locs = crossLocation.peek();
			Location back = locs.get(0);
			setDirection(getLocation().getDirectionToward(back));
			moveTo(back);
			Flower flower = new Flower(getColor());
			flower.putSelfInGrid(gr, this.getLocation());
			probability[back.getDirectionToward(this.getLocation())/90]--;
			stepCount++;
		}
	}

	/**
	 * Find all positions that can be move to.
	 * 
	 * @param loc
	 *            the location to detect.
	 * @return List of positions.
	 */
	public ArrayList<Location> getValid(Location loc) {
		Grid<Actor> gr = getGrid();
		if (gr == null)
			return null;
		ArrayList<Location> valid = new ArrayList<Location>();
		for (int i = 0; i < 4; i++) {
			Location nextloc = getLocation().getAdjacentLocation(direction[i]);
		    if (!gr.isValid(nextloc)) {
		    	continue;
		    } 
		    else if (gr.get(nextloc) == null) {
		    	valid.add(nextloc);
		    } 
		    else if ((gr.get(nextloc) instanceof Rock) && (gr.get(nextloc).getColor().equals(Color.RED))) {
		    	setDirection(getLocation().getDirectionToward(nextloc));
				isEnd = true;
				return null;
			}
		}	
		return valid;
	}

	/**
	 * Tests whether this bug can move forward into a location that is empty or
	 * contains a flower.
	 * 
	 * @return true if this bug can move.
	 */
	public boolean canMove() {
		ArrayList<Location> nextLocation = getValid(getLocation());
		if (nextLocation == null || nextLocation.size() == 0) {
			return false;
		} else {
			int size = 0;
			for (int i = 0; i < nextLocation.size(); i++) {
				int tmp = (getLocation().getDirectionToward(nextLocation.get(i)))/90;
				size += probability[tmp];

			}
			Random rand = new Random();
			int num = rand.nextInt(size);
			int oldnum = 0;
			for (int i = 0; i < nextLocation.size(); i++) {
				int tmp = (getLocation().getDirectionToward(nextLocation.get(i)))/90;
				if(num < oldnum + probability[tmp]) {
					next = nextLocation.get(i);
					break;
				}
				else {
					oldnum += probability[tmp];
				}
			}
			for(int i = 0; i < 4; i++) {
				System.out.println(direction[i] + ": " +probability[i]);
			}
	        probability[getLocation().getDirectionToward(next)/90]++;
			return true;
		}
	}
	/**
	 * Moves the bug forward, putting a flower into the location it previously
	 * occupied.
	 */
	public void move() {
		Grid<Actor> gr = getGrid();
		if (gr == null) {
			return;
		}
		Location loc = getLocation();
		if (gr.isValid(next)) {
		setDirection(getLocation().getDirectionToward(next));		
		ArrayList<Location> locs = crossLocation.pop();
		locs.add(next);
		crossLocation.push(locs);
		ArrayList<Location> newlocs = new ArrayList<Location>();
		newlocs.add(next);
		crossLocation.push(newlocs);
		moveTo(next);
		} else {
			removeSelfFromGrid();
		}
		Flower flower = new Flower(getColor());
		flower.putSelfInGrid(gr, loc);		
	}
}