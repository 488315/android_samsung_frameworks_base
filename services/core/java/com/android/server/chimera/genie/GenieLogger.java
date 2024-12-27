package com.android.server.chimera.genie;

import java.util.Queue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class GenieLogger {
    public static Queue sDump;
    public static final Object sLock = new Object();
    public static int sReclaimedRequests;
    public static int sRequestCount;
}
