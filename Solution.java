package solution;

import jigsaw.Jigsaw;
import jigsaw.JigsawNode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;
/**
 * 在此类中填充算法，完成重拼图游戏（N-数码问题）
 */
public class Solution extends Jigsaw {	
    private Queue<JigsawNode> exploreList;  // 用以保存已发现但未访问的节点
    private Set<JigsawNode> visitedList;    // 用以保存已发现的节点
    /**
     * 拼图构造函数
     */
    public Solution() {
    }

    /**
     * 拼图构造函数
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     */
    public Solution(JigsawNode bNode, JigsawNode eNode) {
        super(bNode, eNode);
    }
    
    //获取这个节点的没走过的连接点
	private Vector<JigsawNode> findFollowJNodes(JigsawNode jNode) {
		Vector<JigsawNode> followJNodes = new Vector<JigsawNode>();
		JigsawNode tempJNode;
		for(int i = 0; i < 4; i++){
			tempJNode = new JigsawNode(jNode);
			if(tempJNode.move(i) && !visitedList.contains(tempJNode) && !exploreList.contains(tempJNode))
				followJNodes.addElement(tempJNode);
		}
		return followJNodes;
}

    /**
     *（实验一）广度优先搜索算法，求指定5*5拼图（24-数码问题）的最优解
     * 填充此函数，可在Solution类中添加其他函数，属性
     * @param bNode - 初始状态节点
     * @param eNode - 目标状态节点
     * @return 搜索成功时为true,失败为false
     */
    public boolean BFSearch(JigsawNode bNode, JigsawNode eNode) {
    	// 用以保存已发现但未访问的节点
        exploreList = new LinkedList<JigsawNode>();  
        // 用以保存已发现的节点
        visitedList  = new HashSet<>(1000);
        // 已访问节点数：用以记录所有访问过的节点的数量
    	int searchedNodesNum = 0;           
        this.beginJNode = new JigsawNode(bNode);
        this.endJNode = new JigsawNode(eNode);
        this.currentJNode = null;
        
        exploreList.add(bNode);
        visitedList.add(bNode);
        
        while(exploreList.size() > 0) {
        	this.currentJNode = exploreList.poll();
        	if (this.currentJNode.equals(eNode)) {
                this.getPath();
                break;
            }
        	searchedNodesNum++;
        	Vector<JigsawNode>tempvector = findFollowJNodes(this.currentJNode);
        	for(int i = 0; i < tempvector.size(); i++) {
        		exploreList.add(tempvector.get(i));
        	}
        }
        		
        System.out.println("Jigsaw BFS Search Result:");
        System.out.println("Begin state:" + this.getBeginJNode().toString());
        System.out.println("End state:" + this.getEndJNode().toString());
        System.out.println("Solution Path: ");
        System.out.println(this.getSolutionPath());
        System.out.println("Total number of searched nodes:" + searchedNodesNum);
        System.out.println("Depth of the current node is:" + this.getCurrentJNode().getNodeDepth());
        return this.isCompleted();
    }


    /**
     *（Demo+实验二）计算并修改状态节点jNode的代价估计值:f(n)
     * 如 f(n) = s(n). s(n)代表后续节点不正确的数码个数
     * 此函数会改变该节点的estimatedValue属性值
     * 修改此函数，可在Solution类中添加其他函数，属性
     * @param jNode - 要计算代价估计值的节点
     */
    public void estimateValue(JigsawNode jNode) {
    	//所有放错位的数码与其正确位置的曼哈顿距离之和
		int ManhattanDistance = 0; 
		//放错位的数码的总和
		int errorpos = 0;
		int dimension = JigsawNode.getDimension();
		for(int index =1 ; index< dimension*dimension; index++){
			if (jNode.getNodesState()[index] != index && jNode.getNodesState()[0] != index) {
				errorpos ++;
				int x1 = (index-1) % dimension;
				int y1 = (index-1) / dimension;
				int x2 = (jNode.getNodesState()[index]-1) % dimension;
				int y2 = (jNode.getNodesState()[index]-1) / dimension;
				ManhattanDistance += Math.abs(x1-x2) + Math.abs(y1-y2);
			}
		}
		jNode.setEstimatedValue(100 * ManhattanDistance + errorpos);
    }
}
