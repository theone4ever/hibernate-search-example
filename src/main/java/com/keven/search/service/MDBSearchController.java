package com.keven.search.service;

import org.hibernate.search.backend.impl.jms.AbstractJMSHibernateSearchController;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.MessageListener;
import org.hibernate.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName="destinationType",
                propertyValue="javax.jms.Queue"),
        @ActivationConfigProperty(propertyName="destination",
                propertyValue="java:/jms/hibernatesearch"),
        @ActivationConfigProperty(propertyName="DLQMaxResent", propertyValue="1")
} )
public class MDBSearchController extends AbstractJMSHibernateSearchController
        implements MessageListener {
    @PersistenceContext
    EntityManager em;

    //method retrieving the appropriate session
    protected Session getSession() {
        return (Session) em.getDelegate();
    }

    @Override
    protected void cleanSessionIfNeeded(Session session) {
    }
}
