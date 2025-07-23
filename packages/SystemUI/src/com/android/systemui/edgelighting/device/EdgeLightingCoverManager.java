package com.android.systemui.edgelighting.device;

import android.os.Debug;
import com.samsung.android.sdk.cover.ScoverManager;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class EdgeLightingCoverManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static EdgeLightingCoverManager sInstance;
    public ScoverManager mSCoverManager;
    public AnonymousClass1 mSCoverStateListener = null;
    public final ArrayList mCoverStateListeners = new ArrayList();
    public boolean mSwitchState = true;
    public int mCoverType = 2;

    public static synchronized EdgeLightingCoverManager getInstance() {
        EdgeLightingCoverManager edgeLightingCoverManager;
        synchronized (EdgeLightingCoverManager.class) {
            edgeLightingCoverManager = sInstance;
            if (edgeLightingCoverManager == null) {
                edgeLightingCoverManager = new EdgeLightingCoverManager();
                sInstance = edgeLightingCoverManager;
            }
        }
        return edgeLightingCoverManager;
    }
}
