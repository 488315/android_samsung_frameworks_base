package com.android.server.wm;

import android.util.ArrayMap;
import android.window.SurfaceSyncGroup;

public final class SurfaceSyncGroupController {
    public final Object mLock = new Object();
    public final ArrayMap mSurfaceSyncGroups = new ArrayMap();

    public final class SurfaceSyncGroupData {
        public final int mOwningUid;
        public final SurfaceSyncGroup mSurfaceSyncGroup;

        public SurfaceSyncGroupData(int i, SurfaceSyncGroup surfaceSyncGroup) {
            this.mOwningUid = i;
            this.mSurfaceSyncGroup = surfaceSyncGroup;
        }
    }
}
