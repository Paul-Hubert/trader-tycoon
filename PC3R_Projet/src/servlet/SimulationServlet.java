package servlet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import simulation.World;

@WebListener
public class SimulationServlet implements ServletContextListener {
    
	private ScheduledExecutorService scheduler;
	
	public static long SIMULATION_INTERVAL = 1000;
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
	    scheduler = Executors.newScheduledThreadPool(16);
	    
	    try {
			
			World world = World.create();
			
			scheduler.scheduleAtFixedRate(() -> {
				
				//long start = System.currentTimeMillis();
				try {
					world.step(scheduler);
				} catch (Exception e) {
					e.printStackTrace();
				}
				//long diff = System.currentTimeMillis() - start;
				
			}, 0, SIMULATION_INTERVAL, TimeUnit.MILLISECONDS);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	    scheduler.shutdownNow();
	 }

}
