package com.hasfun.seoulsubway.common;

import java.io.File;
import org.apache.log4j.Level;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;
/**
 * log config 
 * @author user
 *
 */
public class ConfigureLog4J {
	public static void configure() {
        final LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(Constants.mSdPath + File.separator + "ilovefashion" + File.separator + "log.log");
        logConfigurator.setRootLevel(Level.DEBUG);
        // Set log level of a specific logger
        logConfigurator.setLevel("org.apache", Level.ERROR);
        logConfigurator.setLevel("com.interwater", Level.DEBUG);
        logConfigurator.configure();
    }
}