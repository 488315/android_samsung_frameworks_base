package com.android.systemui.edgelighting.effectservice;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class EffectServiceCollector {
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
                ArrayList arrayList = effectServiceCollector.mElpStyleList;
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    IEdgeLightingStyle iEdgeLightingStyle = (IEdgeLightingStyle) obj;
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
