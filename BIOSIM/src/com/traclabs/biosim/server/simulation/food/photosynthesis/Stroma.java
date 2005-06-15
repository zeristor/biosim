/*
 * Created on Jun 8, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 * TODO
 */
public class Stroma extends PassiveEnzyme{
    private Chemical myProtons = new Chemical(10);
    private Chemical myNADPs = new Chemical(Float.MAX_VALUE / 2);
    private Chemical myADPs = new Chemical(Float.MAX_VALUE / 2);
    private Chemical myPhosphates = new Chemical(Float.MAX_VALUE / 2);
    private Chemical myATPs = new Chemical(0);
    private Chemical myNADPHs = new Chemical(0);
    
    public void tick(){
        myProtons.update();
        myNADPs.update();
        myADPs.update();
        myPhosphates.update();
        myATPs.update();
    }

    /**
     * @return Returns the myADPs.
     */
    public Chemical getADPs() {
        return myADPs;
    }
    /**
     * @return Returns the myATPs.
     */
    public Chemical getATPs() {
        return myATPs;
    }
    /**
     * @return Returns the myNADPs.
     */
    public Chemical getNADPs() {
        return myNADPs;
    }
    /**
     * @return Returns the myPhosphates.
     */
    public Chemical getPhosphates() {
        return myPhosphates;
    }
    /**
     * @return Returns the myProtons.
     */
    public Chemical getProtons() {
        return myProtons;
    }
    /**
     * @return Returns the myNADPHs.
     */
    public Chemical getNADPHs() {
        return myNADPHs;
    }
}
