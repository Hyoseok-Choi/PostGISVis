package hs.choi.geotools;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFactorySpi;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.Query;
import org.geotools.data.shapefile.ShapefileDataStoreFactory;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.filter.text.cql2.CQL;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.JMapPane;
import org.geotools.swing.data.JDataStoreWizard;
import org.geotools.swing.table.FeatureCollectionTableModel;
import org.geotools.swing.wizard.JWizard;

import org.opengis.feature.type.FeatureType;
import org.opengis.filter.Filter;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JButton;

public class MyProject extends JFrame {

	private DataStore dataStore;
	private JPanel contentPane;
	private JComboBox featureTypeCBox;
	private JTextField text;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem shpMenuItem;
	private JMenuItem exitMenuItem;
	private JMenu dataMenu;
	private JMenuItem featuresMenuItem;
	private JTable table;
	private JInternalFrame mapInternalFrame;
	private JMapPane mapPane;

	private void initGUI() {
		
		contentPane.setLayout(null);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		shpMenuItem = new JMenuItem("Open shapefile...");
		fileMenu.add(shpMenuItem);
		shpMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				try {
					connect(new ShapefileDataStoreFactory());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		dataMenu = new JMenu("Data");
		menuBar.add(dataMenu);
		
		featuresMenuItem = new JMenuItem("Get features");
		dataMenu.add(featuresMenuItem);
		featuresMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				try {
					filterFeatures();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		
		JMenuItem countMenuItem = new JMenuItem("Count");
		dataMenu.add(countMenuItem);
		countMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					countFeatures();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JMenuItem geometryMenuItem = new JMenuItem("Geometry");
		dataMenu.add(geometryMenuItem);
		geometryMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {

				try {
					queryFeatures();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		
		
		featureTypeCBox = new JComboBox();
		featureTypeCBox.setBounds(5, 5, 298, 21);
		contentPane.add(featureTypeCBox);
		
		
		
		
		text = new JTextField();
		text.setBounds(5, 36, 298, 21);
		contentPane.add(text);
		text.setColumns(10);
		text.setText("include");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 67, 298, 212);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setModel(new DefaultTableModel(5, 5));
		table.setPreferredScrollableViewportSize(new Dimension(500, 200));
		scrollPane.setViewportView(table);
		
//		mapPane = new JMapPane();
//		mapPane.setBounds(401, 10, 460, 284);
//		contentPane.add(mapPane);
		
//		mapInternalFrame = new JInternalFrame("New JInternalFrame");
//		mapInternalFrame.setBounds(400, 315, 461, 293);
//		contentPane.add(mapInternalFrame);
//		mapInternalFrame.setVisible(true);
		
		JButton mapButton = new JButton("Map");
		mapButton.setBounds(206, 295, 97, 23);
		contentPane.add(mapButton);
		mapButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					myShowMap();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}

	
	

	public void go() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyProject frame = new MyProject();
//					JFrame frame = new MyProject();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	public MyProject() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 870, 391);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		initGUI();
	}
	
    private void connect(DataStoreFactorySpi format) throws Exception {
        JDataStoreWizard wizard = new JDataStoreWizard(format);
        int result = wizard.showModalDialog();
        if (result == JWizard.FINISH) {
            Map<String, Object> connectionParameters = wizard.getConnectionParameters();
            
            // encoding [ EUC-KR ]
            connectionParameters.put("charset","EUC-KR");
            
            dataStore = DataStoreFinder.getDataStore(connectionParameters);
            if (dataStore == null) {
                JOptionPane.showMessageDialog(null, "Could not connect - check parameters");
            }
            updateUI();
        }
    }
    
    private void updateUI() throws Exception {
        ComboBoxModel cbm = new DefaultComboBoxModel(dataStore.getTypeNames());
        featureTypeCBox.setModel(cbm);

        table.setModel(new DefaultTableModel(5, 5));
    }
    
    private void filterFeatures() throws Exception {
        String typeName = (String) featureTypeCBox.getSelectedItem();
        SimpleFeatureSource source = dataStore.getFeatureSource(typeName);

        Filter filter = CQL.toFilter(text.getText());
        SimpleFeatureCollection features = source.getFeatures(filter);
        FeatureCollectionTableModel model = new FeatureCollectionTableModel(features);
        table.setModel(model);
    }
    
    private void countFeatures() throws Exception {
        String typeName = (String) featureTypeCBox.getSelectedItem();
        SimpleFeatureSource source = dataStore.getFeatureSource(typeName);

        Filter filter = CQL.toFilter(text.getText());
        SimpleFeatureCollection features = source.getFeatures(filter);

        int count = features.size();
        JOptionPane.showMessageDialog(text, "Number of selected features:" + count);
    }
    
    private void queryFeatures() throws Exception {
        String typeName = (String) featureTypeCBox.getSelectedItem();
        SimpleFeatureSource source = dataStore.getFeatureSource(typeName);

        FeatureType schema = source.getSchema();
        String name = schema.getGeometryDescriptor().getLocalName();

        Filter filter = CQL.toFilter(text.getText());

        Query query = new Query(typeName, filter, new String[] { name });

        SimpleFeatureCollection features = source.getFeatures(query);

        FeatureCollectionTableModel model = new FeatureCollectionTableModel(features);
        table.setModel(model);
    }
    
    private void myShowMap() throws IOException {
    	
        String typeName = (String) featureTypeCBox.getSelectedItem();
        SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
        
        // Create a map content and add our shapefile to it
        MapContent map = new MapContent();
        map.setTitle("Quickstart");
        
        Style style = SLD.createSimpleStyle(featureSource.getSchema());
        Layer layer = new FeatureLayer(featureSource, style);
        map.addLayer(layer);

        // Now display the map
//        JMapFrame.showMap(map);
                        
        
        // show map
		mapPane = new JMapPane(map);
		mapPane.setBounds(401, 10, 460, 284);
		contentPane.add(mapPane);
		        
        
        
    }
}
