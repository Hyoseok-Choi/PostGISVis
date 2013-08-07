package hs.choi.spring.project;

import java.util.Iterator;
import java.util.List;


import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;


public class testGeometry {
	
	

	public static void main(String[] args) {
		
		MyProject myp = new MyProject();
//		MyProject2 myp = new MyProject2();

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:/META-INF/spring/app-context.xml");
//		 ctx.refresh();

		DAOGeometry geo = (DAOGeometry) ctx.getBean("DAOgeometry");
		
//		String sql = "select st_asText(st_buffer(the_geom, 0.0005)) as the_geom from gunew;";
//		String sql = "select st_asText(the_geom) as the_geom from gunew;";
//		String sql = "select st_asText(the_geom) as the_geom from gunew;";
//		String sql = "select the_geom from gunew;";
		
		String sql = myp.getSql();
		System.out.println("sql : " + sql);
		
		List all = geo.selectGeometry(sql);
		
//		GeometryToFeature gf = new GeometryToFeature();
		GeometryToFeature gf = (GeometryToFeature) ctx.getBean("geometrytofeature");
		SimpleFeatureCollection collection = gf.convertGtoF(all);
		
		myp.setFeature(collection);
		
		
		
//		// show map 1
//		DrawMap dm = new DrawMap();
//		DrawMap dm = (DrawMap) ctx.getBean("drawmap");
//		try {
//			dm.go(collection);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		
		// show map 2
		System.out.println("show map : start");
//		MyProject myp = new MyProject(collection);


		

	}
	
	



}
