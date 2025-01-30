package com.android.systemui.edgelighting.effectservice;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
                ArrayList arrayList = effectServiceCollector.mEdgeLightingStyleList;
                arrayList.clear();
                Iterator it = effectServiceCollector.mElpStyleList.iterator();
                while (it.hasNext()) {
                    IEdgeLightingStyle iEdgeLightingStyle = (IEdgeLightingStyle) it.next();
                    if (iEdgeLightingStyle.isSupportEffect()) {
                        arrayList.add(iEdgeLightingStyle);
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
