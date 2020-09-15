/******************************************************************************
 * Copyright (C) 2012 Heng Sin Low                                            *
 * Copyright (C) 2012 Trek Global                 							  *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.idempiere.fitnesse.server;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.StandardCopyOption;
import java.util.Enumeration;

import org.idempiere.fitnesse.server.fit.OSGiFixtureLoader;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import fit.FixtureLoader;

/**
 * 
 * @author hengsin
 *
 */
public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		FixtureLoader.setInstance(new OSGiFixtureLoader());
		
		//create a compy on temp dir and register geckodriver
		Enumeration<URL> urls = context.getBundle().findEntries("/resources", "geckodriver", false);
		InputStream originalGeckoStream = urls.nextElement().openStream();
		File geckoFileCopy = new File(System.getProperty("java.io.tmpdir") + "/geckodriver");

		java.nio.file.Files.copy(originalGeckoStream, geckoFileCopy.toPath(), StandardCopyOption.REPLACE_EXISTING);
		geckoFileCopy.setExecutable(true, false);

		originalGeckoStream.close();
		System.setProperty("webdriver.gecko.driver", geckoFileCopy.getPath());
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}

