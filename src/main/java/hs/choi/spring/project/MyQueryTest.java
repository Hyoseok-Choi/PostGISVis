package hs.choi.spring.project;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureSource;
import org.geotools.geometry.DirectPosition2D;
import org.geotools.map.FeatureLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.SLD;
import org.geotools.styling.Style;
import org.geotools.swing.JMapFrame;
import org.geotools.swing.JMapPane;
import org.geotools.swing.MapLayerTable;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JLabel;

import javax.swing.JPasswordField;

import org.geotools.swing.JMapFrame.Tool;
import org.geotools.swing.action.InfoAction;
import org.geotools.swing.action.NoToolAction;
import org.geotools.swing.action.PanAction;
import org.geotools.swing.action.ResetAction;
import org.geotools.swing.action.ZoomInAction;
import org.geotools.swing.action.ZoomOutAction;
import org.geotools.swing.control.JCRSStatusBarItem;
import org.geotools.swing.control.JCoordsStatusBarItem;
import org.geotools.swing.control.JExtentStatusBarItem;
import org.geotools.swing.control.JMapStatusBar;
import org.geotools.swing.control.JRendererStatusBarItem;
import org.geotools.swing.event.MapMouseAdapter;
import org.geotools.swing.event.MapMouseEvent;

import com.vividsolutions.jts.geom.Envelope;

import java.awt.event.MouseWheelListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyListener;
import java.awt.event.HierarchyEvent;
import java.awt.SystemColor;
import java.awt.FlowLayout;

public class MyQueryTest extends JFrame {

	private DataStore dataStore;
	private JPanel contentPane;
	private JComboBox featureTypeCBox;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem exitMenuItem;
	private JMenu dataMenu;
	private JMapPane mapPane;
	private JButton mapButton;
	private JScrollPane scrollPane_1;
	private JTextArea queryTextArea;
	private JLabel pwLabel;
	private JTextField hostTextField;
	private JTextField portTextField;
	private JTextField dbTextField;
	private JTextField idTextField;
	private JPasswordField passwordField;
	private JButton connectDBButton;
	private JButton addButton;
	private JPanel statusPanel;
	private MapLayerTable layerPanel;
	private JScrollPane scrollPane_2;
	private JTextArea replyTextArea;
	
	private double clickToZoom = 0.1;  // 1 wheel click is 10% zoom
	private int countLog;
	
	
	// my_var
	
    /** Name assigned to toolbar button for feature info queries. */
    public static final String TOOLBAR_INFO_BUTTON_NAME = "ToolbarInfoButton";
    /** Name assigned to toolbar button for map panning. */
    public static final String TOOLBAR_PAN_BUTTON_NAME = "ToolbarPanButton";
    /** Name assigned to toolbar button for default pointer. */
    public static final String TOOLBAR_POINTER_BUTTON_NAME = "ToolbarPointerButton";
    /** Name assigned to toolbar button for map reset. */
    public static final String TOOLBAR_RESET_BUTTON_NAME = "ToolbarResetButton";
    /** Name assigned to toolbar button for map zoom in. */
    public static final String TOOLBAR_ZOOMIN_BUTTON_NAME = "ToolbarZoomInButton";
    /** Name assigned to toolbar button for map zoom out. */
    public static final String TOOLBAR_ZOOMOUT_BUTTON_NAME = "ToolbarZoomOutButton";
    
    
	private static GenericXmlApplicationContext ctx;
	private static DAOGeometry geo;
	private DriverManagerDataSource driverMangerDS;
	private MapContent map = new MapContent();

	



	
	
	// spring_method_1
	public static void initSpring(){
		ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:/META-INF/spring/app-context.xml");
		
	}
	
	// spring_method_2
	public void ConnectDB(String host, String port, String db, String id, String pw){
		
		// DriverManagerDataSource
		driverMangerDS = (DriverManagerDataSource) ctx.getBean("dataSource");
		
		driverMangerDS.setUrl("jdbc:postgresql://" + host + ":"+ port + "/" + db +"");
		driverMangerDS.setUsername(id);
		driverMangerDS.setPassword(pw);
		
		try {
			driverMangerDS.getConnection();
			recordLog("DB ¿¬°á ¼º°ø");
		} catch (SQLException e) {
			recordLog("DB ¿¬°á ½ÇÆÐ");
			e.printStackTrace();
		}

	}
	
	// spring_method_3
	public SimpleFeatureCollection executeQuery(){
		
		SimpleFeatureCollection collection = null;
		
		try {
			
			String sql = queryTextArea.getText();
			System.out.println("sql : " + sql);
			
			geo = (DAOGeometry) ctx.getBean("DAOgeometry");
			
			List<ResultQuery> all = geo.selectGeometry(sql);
			
//			GeometryToFeature gf = new GeometryToFeature();
			GeometryToFeature gf = (GeometryToFeature) ctx.getBean("geometrytofeature");
			collection = gf.convertGtoF(all);
			
		} catch (Exception e) {
			recordLog("DB¿¬°áÁ¤º¸ ¶Ç´Â Äõ¸®°¡ ÀÌ»ó");
			System.out.println("DB¿¬°áÁ¤º¸ ¶Ç´Â Äõ¸®°¡ ÀÌ»ó");
			
		}
		
		return collection;
		
	}
	
    // spring_method_4
    private void myShowMap(SimpleFeatureCollection collection) throws IOException {
    	
        SimpleFeatureCollection col = collection;
              
        // Create a map content and add our shapefile to it
//        map = new MapContent();
                
        Style style = SLD.createSimpleStyle(col.getSchema(), Color.RED);
        Layer layer = new FeatureLayer(col, style);
        
        map.addLayer(layer);
        
        // show map
        
        mapPane.setRenderer(new StreamingRenderer());
		mapPane.setMapContent(map);
		
		// layerpanel
		layerPanel.setMapPane(mapPane);
		
		// set boundary
		MapViewport viewport = map.getViewport();
		viewport.setBounds(layer.getBounds());
		

		
		// test
//		JMapFrame.showMap(map);
		
     }
    
	private void myShowMap() throws IOException {
    	
        String typeName = (String) featureTypeCBox.getSelectedItem();
        SimpleFeatureSource featureSource = dataStore.getFeatureSource(typeName);
        
        
                
        // Create a map content and add our shapefile to it
//        map = new MapContent();
                
        Style style = SLD.createSimpleStyle(featureSource.getSchema(), Color.GRAY);
        Layer layer = new FeatureLayer(featureSource, style);
        
        map.addLayer(layer);
//        map.layers().add(layer);
        
        // show map
        mapPane.setRenderer(new StreamingRenderer());
		mapPane.setMapContent(map);
		
		// layerpanel
		layerPanel.setMapPane(mapPane);
				
		
		// set boundary
		MapViewport viewport = map.getViewport();
		viewport.setBounds(layer.getBounds());
		
		// test
//		JMapFrame.showMap(map);
     }
	
    
    // geotools_method 1
    protected void GTConnectDB(String host, String port, String db, String id, String pw) {
		
    	Map<String,Object> params = new HashMap<String,Object>();
        params.put( "dbtype", "postgis");
        params.put( "host", host);
        params.put( "port", Integer.parseInt(port));
        params.put( "schema", "public");
        params.put( "database", db);
        params.put( "user", id);
        params.put( "passwd", pw);
        
        
        try {
        	
        	dataStore = DataStoreFinder.getDataStore(params);
			myUpdateUI();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    // geotools_method 2
    private void myUpdateUI() throws Exception {
        ComboBoxModel cbm = new DefaultComboBoxModel(dataStore.getTypeNames());
        featureTypeCBox.setModel(cbm);

        
    }
    
    // my_method_1	log
    public void recordLog(String message){
    	
    	countLog++;
    	long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timelog = dayTime.format(new Date(time));
    	
    	String oldlog = replyTextArea.getText();
    	String newlog = oldlog + "\r\n " + countLog +". " + "[" + timelog + "] " + message;
		
		replyTextArea.setText(newlog);
				
    }
	
	
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyQueryTest frame = new MyQueryTest();
//					JFrame frame = new MyQueryTest();
					frame.setTitle("Sejong Univ. GIS Lab");
					frame.setVisible(true);
					
					initSpring();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	
	public MyQueryTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1074, 803);
		contentPane = new JPanel();
		setContentPane(contentPane);
		initGUI();
		
	}
	
	
	private void initGUI() {
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		
		exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		dataMenu = new JMenu("Data");
		menuBar.add(dataMenu);
		contentPane.setLayout(null);

		
		
		featureTypeCBox = new JComboBox();
		featureTypeCBox.setBounds(12, 659, 179, 28);
		featureTypeCBox.setFont(new Font("±¼¸²", Font.PLAIN, 18));
		contentPane.add(featureTypeCBox);
		
	
		
		// my action
		mapButton = new JButton("run");
		mapButton.setBounds(943, 587, 103, 100);
		mapButton.setFont(new Font("êµ´ë¦¼", Font.BOLD, 20));
		contentPane.add(mapButton);
		mapButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					SimpleFeatureCollection collection = executeQuery();
					myShowMap(collection);
					recordLog("query --> map ¼º°ø");
															
					
				} catch (Exception e1) {
					recordLog("query --> map ½ÇÆÐ");
					System.out.println("query --> map ½ÇÆÐ");
					e1.printStackTrace();
				}
				
			}
		});
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(315, 587, 616, 100);
		contentPane.add(scrollPane_1);
		
		queryTextArea = new JTextArea();
		queryTextArea.setText("SELECT st_astext(the_geom) as the_geom\r\nFROM dijkstra_sp_directed('seoul',1333, 8461,false,false);");
		queryTextArea.setFont(new Font("Monospaced", Font.BOLD, 18));
		scrollPane_1.setViewportView(queryTextArea);
		
//		panel = new JPanel();
//		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
//		panel.setBounds(316, 116, 557, 515);
//		contentPane.add(panel);
		
		mapPane = new JMapPane();
		mapPane.setBounds(315, 58, 731, 488);
		mapPane.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent ev) {

				
				
				int clicks = ev.getWheelRotation();
				// -ve means wheel moved up, +ve means down
				int sign = (clicks < 0 ? -1 : 1);

				Envelope env = mapPane.getDisplayArea();
				double width = env.getWidth();
				double delta = width * clickToZoom * sign;

				env.expandBy(delta);
				mapPane.setDisplayArea((org.opengis.geometry.Envelope) env);
				mapPane.repaint();
			}
		});

		mapPane.setBackground(Color.WHITE);
		mapPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		contentPane.add(mapPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 426, 288, 224);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel hostLabel = new JLabel("host");
		hostLabel.setFont(new Font("±¼¸²", Font.BOLD, 18));
		hostLabel.setBounds(12, 10, 57, 26);
		panel.add(hostLabel);
		
		JLabel portLabel = new JLabel("port");
		portLabel.setFont(new Font("±¼¸²", Font.BOLD, 18));
		portLabel.setBounds(12, 46, 57, 26);
		panel.add(portLabel);
		
		JLabel dbLabel = new JLabel("DB");
		dbLabel.setFont(new Font("±¼¸²", Font.BOLD, 18));
		dbLabel.setBounds(12, 82, 57, 26);
		panel.add(dbLabel);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setFont(new Font("±¼¸²", Font.BOLD, 18));
		idLabel.setBounds(12, 117, 57, 26);
		panel.add(idLabel);
		
		pwLabel = new JLabel("PW");
		pwLabel.setFont(new Font("±¼¸²", Font.BOLD, 18));
		pwLabel.setBounds(12, 153, 57, 26);
		panel.add(pwLabel);
		
		hostTextField = new JTextField();
		hostTextField.setFont(new Font("±¼¸²", Font.PLAIN, 18));
		hostTextField.setText("localhost");
		hostTextField.setBounds(81, 10, 195, 26);
		panel.add(hostTextField);
		hostTextField.setColumns(10);
		
		portTextField = new JTextField();
		portTextField.setFont(new Font("±¼¸²", Font.PLAIN, 18));
		portTextField.setText("5432");
		portTextField.setBounds(81, 46, 195, 26);
		panel.add(portTextField);
		portTextField.setColumns(10);
		
		dbTextField = new JTextField();
		dbTextField.setText("test_routing");
		dbTextField.setFont(new Font("±¼¸²", Font.PLAIN, 18));
		dbTextField.setBounds(81, 82, 195, 26);
		panel.add(dbTextField);
		dbTextField.setColumns(10);
		
		idTextField = new JTextField();
		idTextField.setText("postgres");
		idTextField.setFont(new Font("±¼¸²", Font.PLAIN, 18));
		idTextField.setBounds(81, 117, 195, 26);
		panel.add(idTextField);
		idTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("±¼¸²", Font.PLAIN, 18));
		passwordField.setBounds(81, 153, 195, 26);
		panel.add(passwordField);
		
		connectDBButton = new JButton("connect DB");
		connectDBButton.setFont(new Font("±¼¸²", Font.BOLD, 18));
		connectDBButton.setBounds(124, 189, 152, 26);
		panel.add(connectDBButton);
		connectDBButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				ConnectDB(hostTextField.getText(),
						portTextField.getText(),
						dbTextField.getText(),
						idTextField.getText(),
						passwordField.getText());
				
				GTConnectDB(hostTextField.getText(),
						portTextField.getText(),
						dbTextField.getText(),
						idTextField.getText(),
						passwordField.getText());
				
			}
		});
		
		
		addButton = new JButton("add");
		addButton.setBounds(206, 659, 97, 28);
		addButton.setFont(new Font("±¼¸²", Font.BOLD, 18));
		addButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				try {
					myShowMap();
					recordLog("DB -- > map ¼º°ø");
				} catch (IOException e1) {
					recordLog("DB -- > map ½ÇÆÐ");
					e1.printStackTrace();
				}
	
			}
		});
		contentPane.add(addButton);
		
		
		
		// zoom
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(315, 10, 321, 46);
		toolBar.setFloatable(false);
		contentPane.add(toolBar);
		
		ButtonGroup cursorToolGrp = new ButtonGroup();

		JButton noToolButton = new JButton(new NoToolAction(mapPane));
		noToolButton.setName(TOOLBAR_POINTER_BUTTON_NAME);
		toolBar.add(noToolButton);
		toolBar.addSeparator();
		cursorToolGrp.add(noToolButton);
		
		JButton zoomInButton = new JButton(new ZoomInAction(mapPane));
		zoomInButton.setName(TOOLBAR_ZOOMIN_BUTTON_NAME);
		toolBar.add(zoomInButton);
		cursorToolGrp.add(zoomInButton);

		JButton zoomOutButton = new JButton(new ZoomOutAction(mapPane));
		zoomOutButton.setName(TOOLBAR_ZOOMOUT_BUTTON_NAME);
		toolBar.add(zoomOutButton);
		toolBar.addSeparator();
		cursorToolGrp.add(zoomOutButton);

		JButton panButton = new JButton(new PanAction(mapPane));
		panButton.setName(TOOLBAR_PAN_BUTTON_NAME);
		toolBar.add(panButton);
		toolBar.addSeparator();
		cursorToolGrp.add(panButton);
		
		JButton infoButton = new JButton(new InfoAction(mapPane));
		infoButton.setName(TOOLBAR_INFO_BUTTON_NAME);
		toolBar.add(infoButton);
		toolBar.addSeparator();
		cursorToolGrp.add(infoButton);
		
		JButton resetButton = new JButton(new ResetAction(mapPane));
		resetButton.setName(TOOLBAR_RESET_BUTTON_NAME);
		toolBar.add(resetButton);
		toolBar.addSeparator();
		cursorToolGrp.add(resetButton);
		
//		layerPanel = new MapLayerTable(mapPane);
		layerPanel = new MapLayerTable();
		layerPanel.setBounds(12, 10, 288, 406);
		layerPanel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		layerPanel.setBackground(Color.WHITE);
		contentPane.add(layerPanel);
		
		
		statusPanel = new JPanel();
		statusPanel.setBounds(315, 542, 731, 35);
		contentPane.add(statusPanel);
		
		statusPanel.add(JMapStatusBar.createDefaultStatusBar(mapPane));
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 697, 1034, 35);
		contentPane.add(scrollPane_2);
		
		replyTextArea = new JTextArea();
		replyTextArea.setFont(new Font("Monospaced", Font.PLAIN, 18));
		scrollPane_2.setViewportView(replyTextArea);
		
	}
}
