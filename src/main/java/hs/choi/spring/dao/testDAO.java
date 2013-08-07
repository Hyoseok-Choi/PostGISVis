package hs.choi.spring.dao;

import java.util.Iterator;
import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

public class testDAO {

	public static void main(String[] args) {

		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.load("classpath:/META-INF/spring/app-context.xml");
		// ctx.refresh();

		// gu
		DAOGu daogu = (DAOGu) ctx.getBean("seoulgu");
		List all = daogu.selectAll();
		Iterator it = all.iterator();
		System.out.println("========= all list =========");
		while (it.hasNext()) {

			SeoulGu gu = (SeoulGu) it.next();
			
			System.out.print(gu.getGid());
			System.out.print("	/	");
			System.out.println(gu.getEa002_nam());
			// System.out.println(vo.getThe_geom());
			
			
		}
		System.out.println("============================");
		
		
		
//		System.out.println(allgu.contains(allgu.get));
		
		
		
		// dong
//		DAODong daodong = (DAODong) ctx.getBean("seouldong");
//		List all = daodong.selectAll();
//		Iterator it = all.iterator();
//		System.out.println("========= all list =========");
//		while (it.hasNext()) {
//
//			SeoulDong gu = (SeoulDong) it.next();
//			
//			System.out.print(gu.getGid());
//			System.out.print("	/	");
//			System.out.println(gu.getEa004_nam());
//			// System.out.println(vo.getThe_geom());
//			
//			
//		}
//		System.out.println("============================");
//		
//		
//		// road
//		DAORoad daoroad = (DAORoad) ctx.getBean("seoulroad");
//		List all = daoroad.selectAll();
//		Iterator it = all.iterator();
//		System.out.println("========= all list =========");
//		while (it.hasNext()) {
//
//			SeoulRoad road = (SeoulRoad) it.next();
//
//			System.out.print(road.getGid());
//			System.out.print("	/	");
//			System.out.print(road.getRd_nm());
//			System.out.print("	/	");
//			System.out.println(road.getShape_len());
//			// System.out.println(vo.getThe_geom());
//		}
//		System.out.println("============================");
//		
//		
//		// rpoi
//		DAOPoi daopoi = (DAOPoi) ctx.getBean("seoulpoi");
//		List all = daopoi.selectAll();
//		Iterator it = all.iterator();
//		System.out.println("========= all list =========");
//		while (it.hasNext()) {
//
//			SeoulPoi poi = (SeoulPoi) it.next();
//			
//			System.out.print(poi.getGid());
//			System.out.print("/");
//			System.out.print(poi.getPoi_name());
//			System.out.print("/");
//			System.out.print(poi.getRegiontel());
//			System.out.print("/");
//			System.out.print(poi.getTelno());
//			System.out.print("/");
//			System.out.print(poi.getTelextno());
//			System.out.print("/");
//			System.out.print(poi.getZipcode());
//			System.out.print("/");
//			System.out.print(poi.getDo_name());
//			System.out.print("/");
//			System.out.print(poi.getGun_name());
//			System.out.print("/");
//			System.out.print(poi.getYub_name());
//			System.out.print("/");
//			System.out.print(poi.getLotno());
//			System.out.print("/");
//			System.out.print(poi.getDetailname());
//			System.out.print("/");
//			System.out.print(poi.getKindvalueb());
//			System.out.print("/");
//			System.out.print(poi.getKindvaluem());
//			System.out.print("/");
//			System.out.print(poi.getKindvalues());
//			System.out.print("/");
//			System.out.println(poi.getName_mo());
//			// System.out.println(vo.getThe_geom());
//		}
//		System.out.println("============================");
//		
	}

}
