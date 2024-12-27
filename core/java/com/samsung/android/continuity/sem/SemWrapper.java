package com.samsung.android.continuity.sem;

import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes5.dex */
public class SemWrapper {
    public static int getFloatingFeatureInt(String key) {
        return SemFloatingFeature.getInstance().getInt(key);
    }
}
