package org.idempiere.fitnesse.server.slim;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import fitnesse.slim.SlimFactory;
import fitnesse.slim.SlimPipeSocket;
import fitnesse.slim.SlimService;

/**
 * Adapted from http://sourceforge.net/projects/patang/
 * @author hengsin
 *
 */
public class SlimServer extends fitnesse.slim.SlimServer {
	private SlimService slimService;
	private boolean exceptionWhileExecuting;
	private static Logger LOGGER = Logger.getLogger(SlimServer.class.getName());

	public SlimServer(boolean verbose, SlimFactory slimFactory, int portNumber) {
		super(slimFactory);
			  
		try {
			ServerSocket socket = new SlimPipeSocket();;

			  SlimService slimservice = new SlimService(slimFactory.getSlimServer(),
			    socket, false);
			  slimservice.accept();
			 } catch (java.lang.OutOfMemoryError e) {
			  System.err.println("Out of Memory. Aborting.");
			  e.printStackTrace();
			  System.exit(99);
			  throw e;
			 } catch (BindException e) {
			  System.err.println("Can not bind to port " + portNumber + ". Aborting.");
			  e.printStackTrace();
			 } catch (Exception e) {
					exceptionWhileExecuting = true;
					LOGGER.throwing(SlimServer.class.getName(), "Constructor", e);
				}
			}

	@Override
	public void serve(Socket s) {
		// We are storing the instance of SlimService in an instance variable
		// rather than on a static variable. To ensure we close the one we want
		// to close
		//SlimService.instance = null;


		try {
			super.serve(s);

			while (slimService == null || !exceptionWhileExecuting) {
				// wait to close the server socket if we do not have reference
				// to slimService available yet
			}
			s.close();
		} catch (Exception e) {
			LOGGER.throwing(SlimServer.class.getName(), "serve", e);
		}
	}
}
