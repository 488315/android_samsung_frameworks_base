package com.samsung.android.continuity.sem;

import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes6.dex */
public class SemWrapper {
    public static int getFloatingFeatureInt(String str) {
        return SemFloatingFeature.getInstance().getInt(str);
    }
}
