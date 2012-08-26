package com.tad.arquillian.wls.hibernate.test;

import java.io.File;

import javax.ejb.EJB;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tad.arquillian.wls.hibernate.dao.BasicEntityDAO;
import com.tad.arquillian.wls.hibernate.model.BasicEntity;

@RunWith(Arquillian.class)
public class ArquillianTest {
	@Deployment
    public static WebArchive createDeployment() {
		File[] libs = DependencyResolvers.use(MavenDependencyResolver.class)
                .loadEffectivePom("pom.xml")
                 .importAllDependencies()
                .resolveAsFiles();
        WebArchive wa = ShrinkWrap.create(WebArchive.class,"foo.war")
            .addClasses(BasicEntity.class,BasicEntityDAO.class)
            .addAsLibraries(libs)
            .addAsWebInfResource("web/weblogic.xml","weblogic.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            .addAsResource("META-INF/persistence.xml","META-INF/persistence.xml")
            ;
        
        System.out.println(wa.toString(true));
        return wa;
    }
	
	@EJB BasicEntityDAO dao;
	
	@Test
    public void testStub() throws Exception {
    	//Assert.assertNotNull(cdi);
    	Assert.assertTrue(dao.getEntities().size() == 0);
    }
}
