package com.android.internal.logging;

import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: classes5.dex */
public class AndroidConfig {
    public AndroidConfig() throws SecurityException {
        try {
            Logger logger = Logger.getLogger("");
            logger.addHandler(new AndroidHandler());
            logger.setLevel(Level.INFO);
            Logger.getLogger("org.apache").setLevel(Level.WARNING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
