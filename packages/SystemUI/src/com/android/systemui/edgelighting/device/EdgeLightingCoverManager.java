package com.android.systemui.edgelighting.device;

import android.os.Debug;
import com.samsung.android.sdk.cover.ScoverManager;
import java.util.ArrayList;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EdgeLightingCoverManager {
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
