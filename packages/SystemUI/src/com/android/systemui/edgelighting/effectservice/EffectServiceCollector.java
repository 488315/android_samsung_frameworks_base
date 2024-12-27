package com.android.systemui.edgelighting.effectservice;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class EffectServiceCollector {
    public static EffectServiceCollector mInstance;
    public final ArrayList mEdgeLightingStyleList = new ArrayList();
    public final ArrayList mElpStyleList;

    static {
        Uri.parse("content://com.samsung.systemui.notilus.NotiCenterContentProvider/edgelighting_plus_effect");
    }

    private EffectServiceCollector() {
        new ArrayList();
        this.mElpStyleList = new ArrayList();
        new Handler(Looper.getMainLooper());
        new Runnable() { // from class: com.android.systemui.edgelighting.effectservice.EffectServiceCollector.1
            @Override // java.lang.Runnable
            public final void run() {
                EffectServiceCollector.this.mEdgeLightingStyleList.clear();
                throw null;
            }
        };
        new Runnable() { // from class: com.android.systemui.edgelighting.effectservice.EffectServiceCollector.2
            @Override // java.lang.Runnable
            public final void run() {
                EffectServiceCollector effectServiceCollector = EffectServiceCollector.this;
                effectServiceCollector.mEdgeLightingStyleList.clear();
                Iterator it = effectServiceCollector.mElpStyleList.iterator();
                while (it.hasNext()) {
                    IEdgeLightingStyle iEdgeLightingStyle = (IEdgeLightingStyle) it.next();
                    if (iEdgeLightingStyle.isSupportEffect()) {
                        effectServiceCollector.mEdgeLightingStyleList.add(iEdgeLightingStyle);
                    }
                }
            }
        };
    }

    public static EffectServiceCollector getInstance() {
        if (mInstance == null) {
            mInstance = new EffectServiceCollector();
        }
        return mInstance;
    }
}
