/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.entity;

import java.io.Serializable;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author Juneau
 */
@Entity
@SqlResultSetMapping(
       name="PoolCustomerInformation",
       classes={
          @ConstructorResult(
               targetClass=com.acme.acmepools.entity.PoolCustomerObject.class,
                 columns={
                    @ColumnResult(name="ID", type=Integer.class),
                    @ColumnResult(name="CUSTOMER_ID", type=Integer.class),
                    @ColumnResult(name="NAME", type=String.class),
                    @ColumnResult(name="STYLE", type=String.class),
                    @ColumnResult(name="SHAPE", type=String.class),
                    @ColumnResult(name="LENGTH", type=Double.class),
                    @ColumnResult(name="WIDTH", type=Double.class),
                    @ColumnResult(name="RADIUS", type=Double.class),
                    @ColumnResult(name="GALLONS", type=Double.class),
                    @ColumnResult(name="CURRENT_MAINTENANCE", type=String.class)
                    }
          )
       }
      )
public class PoolCustomerObject implements Serializable {
    
    @Id
    private Integer id;
    private String style;
    private String shape;
    private Double length;
    private Double width;
    private Double radius;
    private Double gallons;
    private Integer customerId;
    private String customerName;
    private String currentMaintenance;
            
    public PoolCustomerObject(){
        
    }
    
    public PoolCustomerObject(Integer poolId,
                              Integer customerId,
                              String customerName,
                              String style,
                              String shape,
                              Double length,
                              Double width,
                              Double radius,
                              Double gallons,
                              String currentMaintenance){
        this.id = poolId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.style = style;
        this.shape = shape;
        this.length = length;
        this.width = width;
        this.radius = radius;
        this.gallons = gallons;
        this.currentMaintenance = currentMaintenance;
        
                
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    

    /**
     * @return the style
     */
    public String getStyle() {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * @return the shape
     */
    public String getShape() {
        return shape;
    }

    /**
     * @param shape the shape to set
     */
    public void setShape(String shape) {
        this.shape = shape;
    }

    /**
     * @return the length
     */
    public Double getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Double length) {
        this.length = length;
    }

    /**
     * @return the width
     */
    public Double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    /**
     * @return the radius
     */
    public Double getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(Double radius) {
        this.radius = radius;
    }

    /**
     * @return the gallons
     */
    public Double getGallons() {
        return gallons;
    }

    /**
     * @param gallons the gallons to set
     */
    public void setGallons(Double gallons) {
        this.gallons = gallons;
    }

    /**
     * @return the customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the currentMaintenance
     */
    public String getCurrentMaintenance() {
        return currentMaintenance;
    }

    /**
     * @param currentMaintenance the currentMaintenance to set
     */
    public void setCurrentMaintenance(String currentMaintenance) {
        this.currentMaintenance = currentMaintenance;
    }
    
    
}
