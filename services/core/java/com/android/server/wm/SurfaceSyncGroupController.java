package com.android.server.wm;

import android.util.ArrayMap;
import android.window.SurfaceSyncGroup;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SurfaceSyncGroupController {
    public final Object mLock = new Object();
    public final ArrayMap mSurfaceSyncGroups = new ArrayMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SurfaceSyncGroupData {
        public final int mOwningUid;
        public final SurfaceSyncGroup mSurfaceSyncGroup;

        public SurfaceSyncGroupData(int i, SurfaceSyncGroup surfaceSyncGroup) {
            this.mOwningUid = i;
            this.mSurfaceSyncGroup = surfaceSyncGroup;
        }
    }
}
