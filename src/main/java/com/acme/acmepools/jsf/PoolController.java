package com.acme.acmepools.jsf;

import com.acme.acmepools.entity.Pool;
import com.acme.acmepools.entity.PoolCustomerObject;
import com.acme.acmepools.session.PoolFacade;
import com.acme.acmepools.entity.util.JsfUtil;
import com.acme.acmepools.entity.util.JsfUtil.PersistAction;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@Named("poolController")
@SessionScoped
public class PoolController implements Serializable {

    @EJB
    private com.acme.acmepools.session.PoolFacade ejbFacade;
    
    @Inject
    private CustomerController customerController;
    private List<Pool> items = null;
    private List<Pool> filteredPools;
    private Pool selected;
    private List<Pool> selectedCustomerPools;
    private List<PoolCustomerObject> poolCustomerObjectList;
    private List customerPoolCount;
    private List<Pool> aboveGroundPools;

    private Long totalAboveGroundCount;
    private Long totalIngroundCount;
    
    private String poolShape;
    
    public PoolController() {
    }
    
    @PostConstruct
    public void init(){
        this.populateAllCustomerPoolList();
        customerPoolCount = ejbFacade.obtainAllCustomers();

    }

    public Pool getSelected() {
        return selected;
    }

    public void setSelected(Pool selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private PoolFacade getFacade() {
        return ejbFacade;
    }

    public Pool prepareCreate() {
        selected = new Pool();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PoolCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PoolUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PoolDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Pool> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Pool getPool(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Pool> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Pool> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * @return the filteredPools
     */
    public List<Pool> getFilteredPools() {
        return filteredPools;
    }

    /**
     * @param filteredPools the filteredPools to set
     */
    public void setFilteredPools(List<Pool> filteredPools) {
        this.filteredPools = filteredPools;
    }

    /**
     * @return the selectedCustomerPools
     */
    public List<Pool> getSelectedCustomerPools() {
        return selectedCustomerPools;
    }

    /**
     * @param selectedCustomerPools the selectedCustomerPools to set
     */
    public void setSelectedCustomerPools(List<Pool> selectedCustomerPools) {
        this.selectedCustomerPools = selectedCustomerPools;
    }

    /**
     * @return the poolCustomerObjectList
     */
    public List<PoolCustomerObject> getPoolCustomerObjectList() {
        return poolCustomerObjectList;
    }

    /**
     * @param poolCustomerObjectList the poolCustomerObjectList to set
     */
    public void setPoolCustomerObjectList(List<PoolCustomerObject> poolCustomerObjectList) {
        this.poolCustomerObjectList = poolCustomerObjectList;
    }

    /**
     * @return the poolShape
     */
    public String getPoolShape() {
        return poolShape;
    }

    /**
     * @param poolShape the poolShape to set
     */
    public void setPoolShape(String poolShape) {
        this.poolShape = poolShape;
    }

    /**
     * @return the customerPoolCount
     */
    public List getCustomerPoolCount() {
        return customerPoolCount;
    }

    /**
     * @param customerPoolCount the customerPoolCount to set
     */
    public void setCustomerPoolCount(List customerPoolCount) {
        this.customerPoolCount = customerPoolCount;
    }

    /**
     * @return the totalAboveGroundCount
     */
    public Long getTotalAboveGroundCount() {
        return totalAboveGroundCount;
    }

    /**
     * @param totalAboveGroundCount the totalAboveGroundCount to set
     */
    public void setTotalAboveGroundCount(Long totalAboveGroundCount) {
        this.totalAboveGroundCount = totalAboveGroundCount;
    }

    /**
     * @return the aboveGroundPools
     */
    public List<Pool> getAboveGroundPools() {
        return aboveGroundPools;
    }

    /**
     * @param aboveGroundPools the aboveGroundPools to set
     */
    public void setAboveGroundPools(List<Pool> aboveGroundPools) {
        this.aboveGroundPools = aboveGroundPools;
    }

    /**
     * @return the totalIngroundCount
     */
    public Long getTotalIngroundCount() {
        return totalIngroundCount;
    }

    /**
     * @param totalIngroundCount the totalIngroundCount to set
     */
    public void setTotalIngroundCount(Long totalIngroundCount) {
        this.totalIngroundCount = totalIngroundCount;
    }

    @FacesConverter(forClass = Pool.class)
    public static class PoolControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PoolController controller = (PoolController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "poolController");
            return controller.getPool(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Pool) {
                Pool o = (Pool) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Pool.class.getName()});
                return null;
            }
        }

    }
    
    /**
     * Invoked on visit to the customer/View.xhtml view to return a customer's
     * pool records
     */
    public void populateCustomerPools(){
        System.out.println("invoking populate customer pools for " + customerController.getSelected().getCustomerId() );
        selectedCustomerPools = ejbFacade.obtainCustomerPoolsUnnamed(customerController.getSelected().getCustomerId());
    }
    
    /**
     * Populates the poolCustomerObjectList with all customer pool information.
     */
    public void populateAllCustomerPoolList(){
        this.poolCustomerObjectList = ejbFacade.obtainAllCustomerPoolInformation();
    }
    
    /**
     * Invoke the EJB obtainPoolByShape method, passing the pool shape that is
     * selected in the view.
     */
    public void selectPoolByShape(){
        filteredPools = ejbFacade.obtainPoolByShape(poolShape);
    }
    
    /**
     * Retrieves the above ground style pool count from the POOL table.
     */
    public void poolCounts(){
        totalAboveGroundCount = ejbFacade.obtainAboveGroundCounts();
        totalIngroundCount = ejbFacade.obtainInGroundCounts();
    }
    

}
