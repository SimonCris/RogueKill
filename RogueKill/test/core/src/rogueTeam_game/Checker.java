package rogueTeam_game;

import java.util.Comparator;


import com.shephertz.app42.paas.sdk.java.game.Game;
import com.shephertz.app42.paas.sdk.java.game.Game.Score;

public class Checker implements Comparator<Game.Score>{
	
	public Checker(){
		
		
	}

	@Override
    public int compare(Score p1, Score p2){
    	
        if(p1.getValue().intValue() > p2.getValue().intValue())
        	return 1;
        else if(p1.getValue().intValue() < p2.getValue().intValue()) 
        	return -1;
        else if(p1.getValue().intValue() == p2.getValue().intValue()){
            if(p1.userName.compareTo(p2.userName) < 0)
            	return 1;
            else if(p1.userName.compareTo(p2.userName) > 0) 
            	return -1;
            else if (p1.userName.compareTo(p2.userName) == 0)
            	return 0;
        }
        
        return 0;
    }

}