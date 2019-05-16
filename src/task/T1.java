package task;

import com.iceking.dynamicweb.TaskProcessor;

public class T1 extends TaskProcessor{
	
	/**
	 * tp.do?key=T1.m1
	 * @throws Exception
	 */
	
	public void m1() throws Exception{
		String html="Hello,";
		
		toHtmlText(html);
	}
//	
//	public void m2() throws Exception{
//		TmpModel model=new TmpModel();
////		int id=getParamInt("id");
////		double price=getParamDouble("price");
////		String code=getParam("code");
////		model.setId(id);
////		model.setPrice(price);
////		model.setCode(code);
//		
//		loadParamsToObj(model);
//		
//		JSONObject json=JSONObject.fromObject(model);
////		json.put("id", id);
////		json.put("price", price);
////		json.put("code", code);
//		
//		toJson(json.toString());
//		
//	}
//	
//	public void m3() throws Exception{
//		TmpModel model=new TmpModel();
//		loadParamsToObj(model);
//		
//		request.setAttribute("model", model);
//		toJsp("WEB-INF/jsp/j1.jsp");
//	}
//	
//	public void m4() throws Exception{
//		TmpModel model=new TmpModel();
//		loadParamsToObj(model);
//		
//		List<WenZhang> wzList=getDBConn().all(WenZhang.class);
//		
//		FrmkView view=new FrmkView("h2.html");
//		view.addData("model", model);
//		view.addData("wzlist", wzList);
//		toFreemarker(view);
//	}
}
