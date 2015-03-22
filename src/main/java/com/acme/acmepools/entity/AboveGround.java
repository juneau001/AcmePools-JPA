/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Juneau
 */
@Entity
@DiscriminatorValue("ABOVE")
public class AboveGround extends Pool {
    public AboveGround(){
        
    }
}
