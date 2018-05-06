# Solution.java
## estimateValue(JigsawNode jNode)
### 采用的估值是以曼哈顿距离为主，以当前位置的错误的位码为辅。即当曼哈顿距离一样时，以当前位置错误的位码来判断
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
