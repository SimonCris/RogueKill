package rogueTeam_camera;

import com.badlogic.gdx.Gdx;

public class VirtualViewport {  
	  
    float virtualWidth;  
    float virtualHeight;  
  
    public float getVirtualWidth() {  
        return virtualWidth;  
    }  
  
    public float getVirtualHeight() {  
        return virtualHeight;  
    }  
  
    public VirtualViewport(float virtualWidth, float virtualHeight) {  
        this(virtualWidth, virtualHeight, false);  
    }  
  
    public VirtualViewport(float virtualWidth, float virtualHeight, boolean shrink) {  
        this.virtualWidth = virtualWidth;  
        this.virtualHeight = virtualHeight;  
    }  
  
    public float getWidth() {  
        return getWidth(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  
    }  
  
    public float getHeight() {  
        return getHeight(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());  
    }  
  
    /** 
     * Restituisce la larghezza della vista in modo che sia visualizzata tutta la virtualViewport sullo schermo. 
     *  
     * @param screenWidth 
     *            La lunghezza dello schermo. 
     * @param screenHeight 
     *            L'altezza dello schermo. 
     */  
    public float getWidth(float screenWidth, float screenHeight) {  
        float virtualAspect = virtualWidth / virtualHeight;  
        float aspect = screenWidth / screenHeight;  
        if (aspect > virtualAspect || (Math.abs(aspect - virtualAspect) < 0.01f)) {  
            return virtualHeight * aspect;  
        } else {  
            return virtualWidth;  
        }  
    }  
  
    /** 
     * Restituisce l'altezza della visualizzazione per consentire a tutta la virtualViewport di essere visualizzata 
     * sullo schermo.
     *  
     * @param screenWidth 
     *            La lunghezza dello schermo. 
     * @param screenHeight 
     *            L'altezza dello schermo.
     */  
    public float getHeight(float screenWidth, float screenHeight) {  
        float virtualAspect = virtualWidth / virtualHeight;  
        float aspect = screenWidth / screenHeight;  
        if (aspect > virtualAspect || (Math.abs(aspect - virtualAspect) < 0.01f)) {  
            return virtualHeight;  
        } else {  
            return virtualWidth / aspect;  
        }  
    }  
  
}  
