/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.acme.acmepools.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.InheritanceType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.OneToMany;
import static javax.persistence.ParameterMode.IN;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juneau
 */
@Entity
@Table(name = "POOL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pool.findAll", query = "SELECT p FROM Pool p"),
    @NamedQuery(name = "Pool.findById", query = "SELECT p FROM Pool p WHERE p.id = :id"),
    @NamedQuery(name = "Pool.findByStyle", query = "SELECT p FROM Pool p WHERE p.style = :style"),
    @NamedQuery(name = "Pool.findByShape", query = "SELECT p FROM Pool p WHERE p.shape = :shape"),
    @NamedQuery(name = "Pool.findByLength", query = "SELECT p FROM Pool p WHERE p.length = :length"),
    @NamedQuery(name = "Pool.findByWidth", query = "SELECT p FROM Pool p WHERE p.width = :width"),
    @NamedQuery(name = "Pool.findByRadius", query = "SELECT p FROM Pool p WHERE p.radius = :radius"),
    @NamedQuery(name = "Pool.findByGallons", query = "SELECT p FROM Pool p WHERE p.gallons = :gallons"),
    })

@NamedStoredProcedureQuery(
        name="ObtainCustomerPoolInformation",
        resultClasses=Pool.class,
        procedureName="OBTAIN_CUSTOMER_POOL_INFORMATION",
        parameters = {
           @StoredProcedureParameter(mode=IN, name="customerId", type=Integer.class)
       }
)
@DiscriminatorColumn(name="STYLE", discriminatorType=DiscriminatorType.STRING,length=10)
public class Pool implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Size(max = 10)
    @Column(name = "STYLE")
    private String style;
    @Size(max = 10)
    @Column(name = "SHAPE")
    private String shape;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "LENGTH")
    private Double length;
    @Column(name = "WIDTH")
    private Double width;
    @Column(name = "RADIUS")
    private Double radius;
    @Column(name = "GALLONS")
    private Double gallons;
    @Column(name = "SHALLOW_DEPTH")
    private Double shallowDepth;
    @Column(name = "DEEP_DEPTH")
    private Double deepDepth;
    @OneToMany(mappedBy = "pool", fetch=FetchType.LAZY)
    private Collection<Customer> customer;

    public Pool() {
    }
    
    public Pool(String style){
        this.style = style;
    }
    
    public Pool(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public Double getGallons() {
        return gallons;
    }

    public void setGallons(Double gallons) {
        this.gallons = gallons;
    }

    @XmlTransient
    public Collection<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(Collection<Customer> customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pool)) {
            return false;
        }
        Pool other = (Pool) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.acme.acmepools.entity.util.Pool[ id=" + id + " ]";
    }

    /**
     * @return the shallowDepth
     */
    public Double getShallowDepth() {
        return shallowDepth;
    }

    /**
     * @param shallowDepth the shallowDepth to set
     */
    public void setShallowDepth(Double shallowDepth) {
        this.shallowDepth = shallowDepth;
    }

    /**
     * @return the deepDepth
     */
    public Double getDeepDepth() {
        return deepDepth;
    }

    /**
     * @param deepDepth the deepDepth to set
     */
    public void setDeepDepth(Double deepDepth) {
        this.deepDepth = deepDepth;
    }
    
}
