package com.samsung.android.systemui.multistar;

import android.util.Singleton;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class MultiStarManager {
    public static PluginMultiStar mPluginMultiStar = null;
    public static final C47801 sInstance = new Singleton() { // from class: com.samsung.android.systemui.multistar.MultiStarManager.1
        public final Object create() {
            return new MultiStarManager(0);
        }
    };
    public static boolean sRecentKeyConsumed = false;
    public MultiStarSystemProxyImpl mMultiStarSystemFacade;

    public /* synthetic */ MultiStarManager(int i) {
        this();
    }

    private MultiStarManager() {
    }
}
