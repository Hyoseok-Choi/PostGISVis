package hs.choi.spring.project;

import javax.swing.JFrame;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;

public class DrawMap extends JFrame {

	
	public void go(SimpleFeatureCollection collection) throws Exception {

        // Create a map content and add our shapefile to it
		
        MapContent map = new MapContent();
        map.setTitle("Quickstart");
        
        Style style = SLD.createSimpleStyle(collection.getSchema());
        Layer layer = new FeatureLayer(collection, style);
        map.addLayer(layer);

        // MyJMapFrame
        MyJMapFrame.showMap(map);

    }

}
