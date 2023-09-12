package springmvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

// 最傳統作法: implements Controller + ModelAndView, 因為沒有 annotation(@Controller), 所以要 implement Controller
// 你只要 implements Controller, 然後用精靈 Override 即可
public class TestController implements Controller{ // 根據 ppt 要求, 我們要 implements Controller
	
	// (因為舊版的寫法是 implement Controller, 所以要 override, 要寫一大堆東西(ModelAndView handleR...), 新版的就不用
	@Override // 實作 Controller 方法
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 建立一個 ModelAndView
		// /WEB-INF/jsp/hello.jsp 我們之前創建的 jsp
		ModelAndView mv = new ModelAndView("/WEB-INF/jsp/hello.jsp"); 
        return mv;
	}
	
}
