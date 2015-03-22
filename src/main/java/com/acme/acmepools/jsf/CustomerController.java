package com.acme.acmepools.jsf;

import com.acme.acmepools.entity.Customer;
import com.acme.acmepools.jsf.util.JsfUtil;
import com.acme.acmepools.jsf.util.JsfUtil.PersistAction;
import com.acme.acmepools.session.CustomerFacade;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("customerController")
@SessionScoped
public class CustomerController implements Serializable {

    @EJB
    private com.acme.acmepools.session.CustomerFacade ejbFacade;
    private List<Customer> items = null;
    private Customer selected;
    
    private List<Customer> customerDiscounts;
    private List<String> cityList;
    private String selectedCity;

    public CustomerController() {
    }
    
    
    

    public Customer getSelected() {
        return selected;
    }

    public void setSelected(Customer selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CustomerFacade getFacade() {
        return ejbFacade;
    }

    public Customer prepareCreate() {
        selected = new Customer();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CustomerCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CustomerUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CustomerDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Customer> getItems() {
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

    public Customer getCustomer(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Customer> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Customer> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    /**
     * @return the customerDiscounts
     */
    public List<Customer> getCustomerDiscounts() {
        return customerDiscounts;
    }

    /**
     * @param customerDiscounts the customerDiscounts to set
     */
    public void setCustomerDiscounts(List<Customer> customerDiscounts) {
        this.customerDiscounts = customerDiscounts;
    }

    /**
     * @return the cityList
     */
    public List<String> getCityList() {
        return cityList;
    }

    /**
     * @param cityList the cityList to set
     */
    public void setCityList(List<String> cityList) {
        this.cityList = cityList;
    }

    /**
     * @return the selectedCity
     */
    public String getSelectedCity() {
        return selectedCity;
    }

    /**
     * @param selectedCity the selectedCity to set
     */
    public void setSelectedCity(String selectedCity) {
        this.selectedCity = selectedCity;
    }

    @FacesConverter(forClass = Customer.class)
    public static class CustomerControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CustomerController controller = (CustomerController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "customerController");
            return controller.getCustomer(getKey(value));
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
            if (object instanceof Customer) {
                Customer o = (Customer) object;
                return getStringKey(o.getCustomerId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Customer.class.getName()});
                return null;
            }
        }

    }
    
        /**
     * Loads selected customer from root customer data list (mobile)
     * @return 
     */
    public String loadCustomer() {
        Map requestMap = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        String customer = (String) requestMap.get("customer");
        for(Customer customerObject:items){
            if(Objects.equals(customerObject.getCustomerId(), Integer.valueOf(customer))){
                selected = customerObject;
                break;
            }
        }
        System.out.println("loadCustomer " + customer);
        // Query database...not the best performance for mobile
        //selected = ejbFacade.find(Integer.valueOf(customer));
        return "pm:customerInfo?transition=slide";
    }
    
     /**
     * Mobile customer update action method.
     * @return 
     */
    public String mobileUpdate(){
        update();
        FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Customer Successfully Updated","Success"));  
        return "/mobile/index";
    }
    
    
    public void loadCustomersWithDiscount(){
        setCustomerDiscounts(ejbFacade.listCustomerDiscounts());
    }
    
    public void populateCityList(){
        cityList = ejbFacade.cityList();
    }
    
    public void updateCustomersByCity(boolean enable){
        ejbFacade.updateMaintenanceByCity(selectedCity, enable);
        if(enable){
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Maintenance enabled for " + selectedCity, "Maintenance enable for " + selectedCity));  
        } else {
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Maintenance disabled for " + selectedCity, "Maintenance enable for " + selectedCity));  
        }
        selectedCity = null;
    }

}
