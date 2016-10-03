package org.gauntlet.importer.itests.googledocs;

import static org.amdatu.testing.configurator.TestConfigurator.createServiceDependency;
import static org.amdatu.testing.configurator.TestConfigurator.createFactoryConfiguration;

import java.io.InputStream;
import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.gauntlet.importer.googledocs.IGSheetProblemsImporter;
import org.gauntlet.importer.itests.BaseJPATest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Stopwatch;

public class GSheetsImporterTest extends BaseJPATest {
	static public final int WAIT_TIMEOUT = 1000*20;
	
	private Stopwatch stopwatch;
	
	private IGSheetProblemsImporter sheetService;
	
	@Override
	@Before
	public void setUp() {
		stopwatch = Stopwatch.createStarted();
		super.setUp();


		conf.add(
				createServiceDependency().setService(IGSheetProblemsImporter.class).setRequired(true))
				.setTimeout(50, TimeUnit.SECONDS)
				.apply();
		
		
		try {
			waitUntilServicesAreRegistered(
					FrameworkUtil.getBundle(GSheetsImporterTest.class)
							.getBundleContext(), WAIT_TIMEOUT, 1,
							IGSheetProblemsImporter.class);
			
			BundleContext ctx = FrameworkUtil.getBundle(GSheetsImporterTest.class).getBundleContext();
			
			sheetService = (IGSheetProblemsImporter) getServiceInstance(ctx, null, IGSheetProblemsImporter.class);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testImportGSheet() throws Exception {
		Assert.assertNotNull(sheetService);
	}	
	
}
