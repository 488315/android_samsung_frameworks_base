package com.samsung.android.server.dynamicfeature;

import android.util.Slog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Butler {
    public ConcurrentHashMap featureHandlers;

    public final void processNewFeature(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            DFeature dFeature = (DFeature) it.next();
            if ("DYNAMIC_FEATURE".equals(dFeature.namespace)) {
                try {
                    if (this.featureHandlers.keySet().contains(dFeature.name)) {
                        ((Consumer) this.featureHandlers.get(dFeature.name)).accept(dFeature);
                    }
                } catch (Exception e) {
                    Slog.e(
                            "dynamicfeature_Butler",
                            "processNewFeature : " + dFeature.value + "/" + e.getMessage());
                }
            }
        }
    }
}
