package com.samsung.android.server.corestate;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class CoreStateSystemFeatureObserver {
    public final Context mContext;
    public final Map mSystemFeaturesRepository = new HashMap();
    public final ArrayList mSystemFeaturesList = new ArrayList();

    public CoreStateSystemFeatureObserver(Context context) {
        this.mContext = context;
    }
}
