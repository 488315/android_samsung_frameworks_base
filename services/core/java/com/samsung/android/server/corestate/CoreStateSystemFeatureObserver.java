package com.samsung.android.server.corestate;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class CoreStateSystemFeatureObserver {
    public final Context mContext;
    public final Map mSystemFeaturesRepository = new HashMap();
    public final ArrayList mSystemFeaturesList = new ArrayList();

    public CoreStateSystemFeatureObserver(Context context) {
        this.mContext = context;
    }
}
