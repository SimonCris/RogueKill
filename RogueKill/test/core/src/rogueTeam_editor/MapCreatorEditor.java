package rogueTeam_editor;

import java.util.Random;

import rogueTeam_game.GameConfig;

public class MapCreatorEditor {

	Random rand = new Random();
	 
	private int [][]Map;
	
	public int MapWidth;		
	public int MapHeight;		
	public int PercentAreWalls;	
 
	public MapCreatorEditor(int pc) {
		this.MapWidth=GameConfig.MAPCREATORWIDTH;
		this.MapHeight=GameConfig.MAPCREATORHEIGHT;
		
	Map = new int [MapHeight][MapWidth];
	this.PercentAreWalls=pc;
	
	RandomFillMap();
	MakeCaverns(1);
	distribuiteLifepack();
	distribuiteAmmo();
	distribuiteEnemies();
	}
 
	public void MakeCaverns(int numStep)
	{
		for(int i =0;i<numStep;i++){
		// By initilizing column in the outter loop, its only created ONCE
		for(int column=0, row=0; row <= MapHeight-1; row++)
		{
			for(column = 0; column <= MapWidth-1; column++)
			{
				Map[column][row] = PlaceWallLogic(column,row);
			}
		}
	}
		}
 
	public int PlaceWallLogic(int x,int y)
	{
		int numWalls = GetAdjacentWalls(x,y,1,1);
 
 
		if(Map[x][y]==1)
		{
			if( numWalls >= 4 )
			{
				return 1;
			}
			if(numWalls<2)
			{
				return 0;
			}
 
		}
		else
		{
			if(numWalls>=5)
			{
				return 1;
			}
		}
		return 0;
	}
 
	public int GetAdjacentWalls(int x,int y,int scopeX,int scopeY)
	{
		int startX = x - scopeX;
		int startY = y - scopeY;
		int endX = x + scopeX;
		int endY = y + scopeY;
 
		int iX = startX;
		int iY = startY;
 
		int wallCounter = 0;
 
		for(iY = startY; iY <= endY; iY++) {
			for(iX = startX; iX <= endX; iX++)
			{
				if(!(iX==x && iY==y))
				{
					if(IsWall(iX,iY))
					{
						wallCounter += 1;
					}
				}
			}
		}
		return wallCounter;
	}
 
	boolean IsWall(int x,int y)
	{
		// Consider out-of-bound a wall
		if( IsOutOfBounds(x,y) )
		{
			return true;
		}
 
		if( Map[x][y]==1 )
		{
			return true;
		}
 
		if( Map[x][y]==0 )
		{
			return false;
		}
		return false;
	}
 
	boolean IsOutOfBounds(int x, int y)
	{
		if( x<0 || y<0 )
		{
			return true;
		}
		else if( x>MapWidth-1 || y>MapHeight-1 )
		{
			return true;
		}
		return false;
	}
	 
	public void RandomFillMap()
	{
		
 
		int mapMiddle = 0; // Temp variable
		for(int column=0,row=0; row < MapHeight; row++) {
			for(column = 0; column < MapWidth; column++)
			{
				// If coordinants lie on the the edge of the map (creates a border)
				if(column == 0)
				{
					Map[column][row] = 1;
				}
				else if (row == 0)
				{
					Map[column][row] = 1;
				}
				else if (column == MapWidth-1)
				{
					Map[column][row] = 1;
				}
				else if (row == MapHeight-1)
				{
					Map[column][row] = 1;
				}
				// Else, fill with a wall a random percent of the time
				else
				{
					mapMiddle = (MapHeight / 2);
 
					if(row == mapMiddle)
					{
						Map[column][row] = 0;
					}
					else
					{
						Map[column][row] = RandomPercent(PercentAreWalls);
					}
				}
			}
		}
	}
 
	int RandomPercent(int percent)
	{
		if (percent >= rand.nextInt((101) + 1))
		{
			return 1;
		}
		return 0;
	}
	
	public int[][] getMap() {
		return Map;
	}

	public void setMap(int[][] map) {
		Map = map;
	}
	
	//distribuisco i nemici nella mappa in base a num max e num min
	public void distribuiteEnemies(){
		
		int numEnemies = rand.nextInt(GameConfig.NUM_SIMPLESOLDIERS_MAX-GameConfig.NUM_SIMPLESOLDIERS_MIN) + GameConfig.NUM_SIMPLESOLDIERS_MIN;
	
		int X, Y;
		for(int i = 0; i < numEnemies; i++)
		{
			
		  X = rand.nextInt(GameConfig.MAPCREATORWIDTH); // generates a random int between 0 and GAME WIDTH
		  Y = rand.nextInt(GameConfig.MAPCREATORHEIGHT); // generates a random int between 0 and GAME HEIGHT
		  
		  if(Map[X][Y]==0){///se la cella è libera, ossia c'è del terreno, setto un nemico
			  Map[X][Y]=4;
		  
		  }   
		}
	}
	
	
	//distribuisco medikit nella mappa in relazione a num max e min
	public void distribuiteLifepack(){
		
		int numLifepacks = rand.nextInt(GameConfig.NUM_MEDIKIT_MAX-GameConfig.NUM_MEDIKIT_MIN) + GameConfig.NUM_MEDIKIT_MIN;
	
		int X, Y;
		for(int i = 0; i < numLifepacks; i++)
		{
			
		  X = rand.nextInt(GameConfig.MAPCREATORWIDTH); // generates a random int between 0 and GAME WIDTH
		  Y = rand.nextInt(GameConfig.MAPCREATORHEIGHT); // generates a random int between 0 and GAME HEIGHT
		  
		  if(Map[X][Y]==0){///se la cella è libera, ossia c'è del terreno, setto un medikit
			  Map[X][Y]=2;
		  
		  }   
		}
	}


	//distribuisco munizioni nella mappa in relazione a num max e num min
	public void distribuiteAmmo(){
	
	int numAmmo = rand.nextInt(GameConfig.NUM_AMMO_MAX-GameConfig.NUM_AMMO_MIN) + GameConfig.NUM_AMMO_MIN;

	int X, Y;
	for(int i = 0; i < numAmmo; i++)
	{
		
	  X = rand.nextInt(GameConfig.MAPCREATORWIDTH); // generates a random int between 0 and GAME WIDTH
	  Y = rand.nextInt(GameConfig.MAPCREATORHEIGHT); // generates a random int between 0 and GAME HEIGHT
	  
	  if(Map[X][Y]==0){///se la cella è libera, ossia c'è del terreno, setto le munizioni
		  Map[X][Y]=3;
	  
	  }   
	}
}
	




}