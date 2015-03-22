/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.acme.acmepools.jsf;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author Juneau
 */
@Named(value = "mobileNavigationController")
@SessionScoped
public class MobileNavigationController implements Serializable {
    
    private boolean useMobile = false;
    private int activetabindex;

    /**
     * Creates a new instance of MobileNavigationController
     */
    public MobileNavigationController() {
    }

    /**
     * @return the useMobile
     */
    public boolean isUseMobile() {
        return useMobile;
    }

    /**
     * @param useMobile the useMobile to set
     */
    public void setUseMobile(boolean useMobile) {
        this.useMobile = useMobile;
    }
    
    public String initMobile(){
        setUseMobile(true);
        return null;
    }

    /**
     * @return the activetabindex
     */
    public int getActivetabindex() {
        return activetabindex;
    }

    /**
     * @param activetabindex the activetabindex to set
     */
    public void setActivetabindex(int activetabindex) {
        this.activetabindex = activetabindex;
    }
    
    /**
     * Navigational Action Method.  
     * Accepts a string to indicate
     * which view should be loaded.
     * @param view
     * @return 
     */
    public String menuNavigation(String view){
        switch(view){
            case "POOLS":
                setActivetabindex(1);
                return "/mobile/pools";
            case "JOBS":
                setActivetabindex(2);
                return "/mobile/jobs";
            case "HOME":
                setActivetabindex(0);
                return "/mobile/index";
            default:
                setActivetabindex(0);
                return "/mobile/index";
        }

    }
}
