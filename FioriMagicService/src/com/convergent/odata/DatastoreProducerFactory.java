package com.convergent.odata;

import java.util.Properties;

import org.odata4j.producer.ODataProducer;
import org.odata4j.producer.ODataProducerFactory;

import com.convergent.persistence.util.EMF;

public class DatastoreProducerFactory implements ODataProducerFactory {
	public static final String NAMESPACE_PROPNAME = "odata4j.datastore.namespace";
	public static final String NAMESPACE;
	public static final int MAX_RESULTS= 1000;
	static{
		NAMESPACE = System.getProperty(NAMESPACE_PROPNAME);
	}
	
	@Override
	public ODataProducer create(Properties properties) {
		if (!AppEngineUtil.isServer())
			throw new RuntimeException("Must be running on AppEngine (dev or prod)");
		try{
			return new JPAProducerWrapper(EMF.get(),NAMESPACE,MAX_RESULTS);
		}catch (Exception e) {
				e.printStackTrace();
		}
		return null;
	}
	
}
