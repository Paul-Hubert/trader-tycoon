package servlet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import data.World;

@WebListener
public class SimulationServlet implements ServletContextListener {
    
	private ScheduledExecutorService scheduler;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
	    scheduler = Executors.newScheduledThreadPool(16);
	    
	    
	    scheduler.scheduleAtFixedRate(() -> {
	    	
			try {
				
				World world = World.create();
				world.step(scheduler);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }, 0, 1, TimeUnit.SECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	    scheduler.shutdownNow();
	 }

}
