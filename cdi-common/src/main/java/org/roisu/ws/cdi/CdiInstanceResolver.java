package org.roisu.ws.cdi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.ws.api.message.Packet;
import com.sun.xml.ws.api.server.InstanceResolver;

public class CdiInstanceResolver extends InstanceResolver<Object> {
	private static final Logger LOG = LoggerFactory.getLogger(CdiInstanceResolver.class);    

	private final Class<?> clazz;
	
	public CdiInstanceResolver(Class<?> clazz) {
		this.clazz = clazz;
	}

	@Override
    public Object resolve(Packet request) {
		try {
			return JNDIBeanManager.getInstance().getBean(clazz);
		} catch (RuntimeException e) {
			LOG.error("Error getting CDI BeanManager: " + e.getMessage(), e);
			throw e;
		}
	}

}