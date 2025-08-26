package com.samsung.android.systemui.multistar;

import android.util.Singleton;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;

/* loaded from: classes4.dex */
public class MultiStarManager {
    public static PluginMultiStar mPluginMultiStar = null;
    public static final AnonymousClass1 sInstance = new Singleton() { // from class: com.samsung.android.systemui.multistar.MultiStarManager.1
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
