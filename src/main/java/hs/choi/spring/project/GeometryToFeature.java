package hs.choi.spring.project;

import java.util.Iterator;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class GeometryToFeature {
	
	
	public SimpleFeatureCollection convertGtoF(List all){
		SimpleFeatureCollection collection = FeatureCollections.newCollection();
		
		Iterator it = all.iterator();
		System.out.println("========= all list =========");
		int i = 1;
		
		
		ResultGeometry re1 = (ResultGeometry) it.next();
		String a1 = re1.getThe_Geom();
		
		// POI[N]T, LIN[E]STRING, MUL[T]ILINESTRING, POL[Y]GON
		String b0 = Character.toString(a1.charAt(0));
		String b3 = Character.toString(a1.charAt(3));
		String b8 = Character.toString(a1.charAt(8));
		
		SimpleFeatureType TYPE = createFeatureType(b0, b3, b8);
		SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        featureBuilder.add(a1);
        System.out.println("Feature Type : " + TYPE);
		
				
		while (it.hasNext()) {

			ResultGeometry re2 = (ResultGeometry) it.next();
		
//			System.out.print(i);
//			i++;
//			System.out.print("	/	");
//			System.out.println(re2.getThe_Geom());
			
			// get [String]geometry
			String a2 = re2.getThe_Geom();

			// POI[N]T, LIN[E]STRING, MUL[T]ILINESTRING, POL[Y]GON
//			String b0 = Character.toString(a.charAt(0));
//			String b3 = Character.toString(a.charAt(3));
//			String b8 = Character.toString(a.charAt(8));
//			
//			SimpleFeatureType TYPE = createFeatureType(b0, b3, b8);
//			
//	        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
			
	        featureBuilder.add(a2);
            SimpleFeature feature = featureBuilder.buildFeature(null);
            collection.add(feature);
			
		}
		
		return collection;
	}
	
	
	
	
	private static SimpleFeatureType createFeatureType(String b0, String b3, String b8) {

        SimpleFeatureTypeBuilder builder = new SimpleFeatureTypeBuilder();
        
        // srid Ãß°¡
        builder.srid(4326);
        builder.setName("Location");
//        builder.setCRS(DefaultGeographicCRS.WGS84); // <- Coordinate reference system
        
        

        // add attributes in order
        
		if (b3.equals("N")) {
			builder.add("Location", Point.class);

		} else if (b0.equals("M") && b8.equals("N")) {
			builder.add("Location", MultiPoint.class);

		} else if (b3.equals("E")) {
			builder.add("Location", LineString.class);

		} else if (b0.equals("M") && b8.equals("E")) {
			builder.add("Location", MultiLineString.class);

		} else if (b3.equals("Y")) {
			builder.add("Location", Polygon.class);

		} else if (b0.equals("M") && b8.equals("Y")) {
			builder.add("Location", MultiPolygon.class);

		} else if (b0.equals("G")) {
			builder.add("Location", GeometryCollection.class);

		} else {
			System.out.println("point?? linestring?? polygon??");
			builder.add("Location", LineString.class);
		}
           
        builder.length(15).add("Name", String.class); // <- 15 chars width for name field

        // build the type
        final SimpleFeatureType LOCATION = builder.buildFeatureType();

        return LOCATION;
    }

}
