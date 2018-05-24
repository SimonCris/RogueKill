package rougueTeam_actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

import rougueTeam_actors.Nemico;

public class FireAction extends TemporalAction{
	
	private boolean done;
	private float interval;
    private float timeLeftInInterval;
    private Nemico n;
	
	public FireAction(Nemico _n, float interval) {
		
		n = _n;
		this.actor=n;
		this.target=n;
		this.interval = interval;
        this.timeLeftInInterval = interval;
		this.done = false;

	}

	@Override
    public boolean act(float delta){
        super.act(delta);
         
       
            n.fire();
             
        
              
        return true;
    }
	
	
	@Override
	protected void update(float percent) {
		
		if(percent>=1)
			done = true;
		
	}
	

}
