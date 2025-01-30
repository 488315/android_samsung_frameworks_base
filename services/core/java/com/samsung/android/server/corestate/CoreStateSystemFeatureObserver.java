package com.samsung.android.server.corestate;

import android.content.Context;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes2.dex */
public class CoreStateSystemFeatureObserver {
    public final Context mContext;
    public final Map mSystemFeaturesRepository = new HashMap();
    public final ArrayList mSystemFeaturesList = new ArrayList();

    public void registerObservingItems() {
    }

    public CoreStateSystemFeatureObserver(Context context) {
        this.mContext = context;
        registerObservingItems();
    }

    public void init() {
        Iterator it = this.mSystemFeaturesList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            this.mSystemFeaturesRepository.put(str, Boolean.valueOf(this.mContext.getPackageManager().hasSystemFeature(str)));
        }
    }

    public int populateState(Bundle bundle, int i) {
        for (Map.Entry entry : this.mSystemFeaturesRepository.entrySet()) {
            bundle.putBoolean((String) entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
        }
        return 2;
    }
}
