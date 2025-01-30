package com.android.systemui.edgelighting.device;

import android.os.Debug;
import com.samsung.android.sdk.cover.ScoverManager;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class EdgeLightingCoverManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static EdgeLightingCoverManager sInstance;
    public ScoverManager mSCoverManager;
    public C13081 mSCoverStateListener = null;
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
