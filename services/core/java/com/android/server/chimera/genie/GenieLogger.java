package com.android.server.chimera.genie;

import java.util.Queue;

public abstract class GenieLogger {
    public static Queue sDump;
    public static final Object sLock = new Object();
    public static int sReclaimedRequests;
    public static int sRequestCount;
}
