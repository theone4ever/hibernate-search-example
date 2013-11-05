package com.keven.search.service;

import org.hibernate.Session;
import org.hibernate.search.backend.impl.jms.AbstractJMSHibernateSearchController;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    @Inject
    private Logger log;

    //method retrieving the appropriate session
    protected Session getSession() {
        return (Session) em.getDelegate();
    }

    @Override
    protected void cleanSessionIfNeeded(Session session) {
    }

    @Override
    public void onMessage(javax.jms.Message message) {
        log.log(Level.INFO, "Get a new msg");
        super.onMessage(message);
    }
}
