/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.acme.acmepools.session;

import com.acme.acmepools.entity.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Juneau
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> {

    @PersistenceContext(unitName = "com.acme_AcmePools_war_AcmePools-1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }

    /**
     * Utilize an entity graph to load the discountCode attribute of the
     * Customer entity.
     *
     * @return
     */
    public List<Customer> listCustomerDiscounts() {
        EntityGraph eg = em.getEntityGraph("customerWithDiscount");
        return em.createQuery("select o from Customer o")
                .setHint("javax.persistence.fetchgraph", eg)
                .getResultList();
    }

    /**
     * Bulk update of all customers by city.
     * @param city 
     * @param enabled 
     */
    public void updateMaintenanceByCity(String city, boolean enabled) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaUpdate<Customer> customerUpdate = 
                builder.createCriteriaUpdate(Customer.class);
        Root<Customer> c = customerUpdate.from(Customer.class);
        customerUpdate.set(c.get("currentMaintenance"), enabled)
                .where(builder.equal(c.get("city"), city));
        Query q = em.createQuery(customerUpdate);
        q.executeUpdate();
        em.flush();
    }
    
    public List<String> cityList(){
        return em.createQuery("select distinct(c.city) from Customer c")
                .getResultList();
    }

}
