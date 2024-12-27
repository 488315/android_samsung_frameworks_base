package com.android.server.am;

import android.content.pm.FeatureInfo;

import java.util.Comparator;

public final /* synthetic */ class ActivityManagerShellCommand$$ExternalSyntheticLambda2
        implements Comparator {
    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        String str = ((FeatureInfo) obj).name;
        String str2 = ((FeatureInfo) obj2).name;
        if (str == str2) {
            return 0;
        }
        if (str == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str.compareTo(str2);
    }
}
