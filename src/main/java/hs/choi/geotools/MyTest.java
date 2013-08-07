package hs.choi.geotools;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MyTest {

	public static void main(String[] args) {

//		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
//		Quickstart qs = ctx.getBean("quick", Quickstart.class);
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:/META-INF/spring/app-context.xml");
		ctx.refresh();
		
//		Quickstart qs = (Quickstart) ctx.getBean("quickstart");
		MyProject mp = (MyProject) ctx.getBean("myproject");
		
		try {
//			qs.go();
			mp.go();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

