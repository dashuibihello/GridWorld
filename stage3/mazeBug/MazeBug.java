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
	//储存下一个位置
	private Location next;
	//判断是否结束
	private boolean isEnd = false;
	//存储已经经过的位置
	private Stack<ArrayList<Location>> crossLocation = new Stack<ArrayList<Location>>();
	//已经经过的步数
	private Integer stepCount = 0;
	//final message has been shown
	private boolean hasShown = false;
	//作为判断可能性的数组
	private int[] probability = new int[4];
	//可以前进四个方向
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
		//当步数为0时，需要向栈加入初始位置已经将所有方向的行进次数初始化为1
		if(stepCount == 0) {
			ArrayList<Location> locs = new ArrayList<Location>();
			locs.add(this.getLocation());
			crossLocation.push(locs);
			for(int i = 0; i < 4; i++) {
				probability[i] = 1;
			}
		}
		boolean willMove = canMove();
		if (isEnd) {
		//to show step count when reach the goal		
			if (!hasShown) {
				String msg = stepCount.toString() + " steps";
				JOptionPane.showMessageDialog(null, msg);
				hasShown = true;
			}
		}
		//如果可以前进就正常前进
		else if (willMove) {
			move();
			//increase step count when move 
			stepCount++;
		}
		//如果不可以前进则往回走
		else {
			Grid<Actor> gr = getGrid();
			if(crossLocation.size() != 0) {
				crossLocation.pop();
			}

			ArrayList<Location> locs = crossLocation.peek();
			Location back = locs.get(0);
			setDirection(getLocation().getDirectionToward(back));
			Location temp = this.getLocation();
			moveTo(back);
			Flower flower = new Flower(getColor());
			flower.putSelfInGrid(gr, temp);
			//经过走过的路时将方向的行进次数减1
			probability[back.getDirectionToward(temp)/90]--;
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
		if (gr == null) {
			return null;
		}
		ArrayList<Location> valid = new ArrayList<Location>();
		//向四个方向查询
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
			//创建随机数的种子
			Random rand = new Random();
			//将随机数范围设置为0-size
			int num = rand.nextInt(size);
			int oldnum = 0;
			//如果随机数小于当前probability数组对应范围则选择
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
		//提取栈顶的ArrayList
		ArrayList<Location> locs = crossLocation.pop();
		//给提取的ArrayList后加入下一个位置
		locs.add(next);
		//将操作完成的ArrayList放回栈中
		crossLocation.push(locs);
		//新建一个只有下一个位置的ArrayList并放入栈中
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

