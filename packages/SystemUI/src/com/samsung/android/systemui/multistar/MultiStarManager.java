package com.samsung.android.systemui.multistar;

import android.util.Singleton;
import com.samsung.systemui.splugins.multistar.PluginMultiStar;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
