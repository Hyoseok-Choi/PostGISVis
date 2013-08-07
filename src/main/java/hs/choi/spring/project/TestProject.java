package hs.choi.spring.project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JButton;

import org.geotools.data.simple.SimpleFeatureCollection;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class TestProject extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton runButton;
	
	// my_var - start
	private GenericXmlApplicationContext ctx;
	private DAOGeometry geo;
	// my_var - end
	
	
	public TestProject() {
		initGUI();
		
		// my_code 1 - start
		
		ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:/META-INF/spring/app-context.xml");
//		 ctx.refresh();

		geo = (DAOGeometry) ctx.getBean("DAOgeometry");
		
//		my_code 1 - end

	}
	
	public void veiwMap(String sql){
		
//		my_code 2 - start
		
//		 String sql = "select st_asText(st_buffer(the_geom, 0.0005)) as the_geom from gunew;";
		// String sql = "select st_asText(the_geom) as the_geom from gunew;";
//		String sql = "select st_asText(the_geom) as the_geom from gunew;";
		// String sql = "select the_geom from gunew;";
		
		System.out.println("sql : " + sql);
		
		// all[model 1]
		List all = geo.selectGeometry(sql);
		
//		GeometryToFeature gf = new GeometryToFeature();
		GeometryToFeature gf = (GeometryToFeature) ctx.getBean("geometrytofeature");
		// all[model 1] --> collection[model 2]
		SimpleFeatureCollection collection = gf.convertGtoF(all);
		
		// show map 1
//		DrawMap dm = new DrawMap();
		DrawMap dm = (DrawMap) ctx.getBean("drawmap");
		try {
			dm.go(collection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		show map 2
//		System.out.println("show map : start");
//		MyProject myp = new MyProject(collection);
	
//		my_code 2 - end
		
	}
	
	
	
	// GUI
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestProject frame = new TestProject();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}



	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 319);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 669, 261);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setFont(new Font("Monospaced", Font.BOLD, 20));
		textArea.setText("select st_asText(st_buffer(the_geom, 0.0005)) as the_geom from gunew;");
		scrollPane.setViewportView(textArea);
		
		runButton = new JButton("run");
		runButton.setFont(new Font("굴림", Font.BOLD, 22));
		runButton.setBounds(693, 10, 90, 52);
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				veiwMap(textArea.getText());				
			}
		});
		contentPane.add(runButton);
	}
	

}
