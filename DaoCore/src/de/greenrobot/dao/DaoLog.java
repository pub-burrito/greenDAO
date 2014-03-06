/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.greenrobot.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Internal greenDAO logger class. A wrapper around the Android Log class providing a static Log Tag.
 * 
 * @author markus
 * 
 */
public class DaoLog {
	
	private final static String TAG = "greenDAO";
	private final static Logger Log = Logger.getLogger(Logger.class.getName(), TAG);

    public static final Level VERBOSE = Level.ALL;
    public static final Level DEBUG = Level.FINEST;
    public static final Level INFO = Level.INFO;
    public static final Level WARN = Level.WARNING;
    public static final Level ERROR = Level.SEVERE;
    public static final Level ASSERT = Level.FINE;

    public static boolean isLoggable(Level level) {
        return Log.isLoggable(level);
    }
    
    public static void v(String msg) {
    	Log.log(VERBOSE, msg);
    }

    public static void d(String msg) {
    	Log.log(DEBUG, msg) ;
    }

    public static void d(String msg, Throwable t) {
    	Log.log(DEBUG, msg, t);
    }
    
    public static void i(String msg) {
        Log.log(INFO, msg);
    }

    public static void i(String msg, Throwable t) {
    	Log.log(INFO, msg, t);
    }
    
    public static void w(String msg) {
        Log.log(WARN, msg);
    }

    public static void w(String msg, Throwable t) {
    	Log.log(WARN, msg, t);
    }
    
    public static void e(String msg) {
        Log.log(ERROR, msg);
    }
    public static void e(String msg, Throwable t) {
    	Log.log(ERROR, msg, t);
    }
}
