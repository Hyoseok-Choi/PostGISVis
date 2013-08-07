package hs.choi.geotools;

import hs.choi.geotools.ExampleService;
import junit.framework.TestCase;

public class ExampleServiceTests extends TestCase {

	private ExampleService service = new ExampleService();
	
	public void testReadOnce() throws Exception {
		assertEquals("Hello world!", service.getMessage());
		
		// my test
		System.out.println(service.getMessage());
	}

}
