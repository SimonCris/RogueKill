package rogueTeam_structure;


public class Map {
	
	private boolean[][] map;
	private int mapHeight;
	private int mapWidth;
	


	public Map(int x,int y){
		this.mapHeight=x;
		this.mapWidth=y;
		map = new boolean[mapHeight][mapWidth];
		
	}

	public void cleanMap(){
		for(int i =0;i<mapHeight;i++)
			for(int j=0;j<mapWidth;j++)
				 map[i][j]=false;
	}
	
	public boolean[][] getMap() {
		return map;
	}

	public void setMap(boolean[][] booleans) {
		this.map = booleans;
	}

    public boolean getCella(int x,int y){
    	return map[x][y];
    }

    public void setCella(int x,int y, boolean bs){
    	map[x][y]=bs;
    	
    }
    
    public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

}
