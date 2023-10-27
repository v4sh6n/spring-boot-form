package com.bolsadeideas.springboot.form.app.interceptors;

import java.util.Random;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor  {
	
	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(request.getMethod().equalsIgnoreCase("post")) {
			return true;
		}
		
		if(handler instanceof HandlerMethod) {
			HandlerMethod metodo = (HandlerMethod) handler;
			Logger.info("es un método del controlador: " + metodo.getMethod().getName());
		}
		
		
		Logger.info("TiempoTranscurridoInterceptor: preHandle( entrando...");
		Logger.info("Interceptando: " + handler);
		long tiempoInicio = System.currentTimeMillis();
		request.setAttribute("tiempoInicio", tiempoInicio);
		
		//para simular una carga en nuestro request
		
		Random random = new Random();
		Integer delay = random.nextInt(100);
		Thread.sleep(delay);
		
		
				
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		
		if(request.getMethod().equalsIgnoreCase("post")) {
			return;
		}
		
		long tiempoFin = System.currentTimeMillis();
		long tiempoInicio = (long)request.getAttribute("tiempoInicio");
		long tiempoTranscurrido = tiempoFin - tiempoInicio;
		
		if(handler instanceof HandlerMethod && modelAndView != null) {
			modelAndView.addObject("tiempoTranscurrido", tiempoTranscurrido);
			
			
		}
		
		Logger.info("Tiempo Transcurrido"+ tiempoTranscurrido + "milisegundos");
		Logger.info("TiempoTranscurridoInterceptor: postHandleI() saliendo...");
	}
	
	
	
	

}
