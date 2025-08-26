package com.samsung.android.knox.analytics.util;

import java.util.List;

/* loaded from: classes6.dex */
public class BlacklistedFeature {
    private List<String> mEvents;
    private String mFeature;

    BlacklistedFeature(String str, List<String> list) {
        this.mFeature = str;
        this.mEvents = list;
    }

    public boolean isBlacklisted(String str, String str2) {
        return hasFeatureName(str) && hasEvent(str2);
    }

    public boolean hasFeatureName(String str) {
        return this.mFeature.equals(str);
    }

    public boolean hasEvent(String str) {
        for (String str2 : this.mEvents) {
            if (str2.equals("*")) {
                return true;
            }
            String[] strArrSplit = str2.split("\\*");
            if ((str2.endsWith("*") && str.startsWith(strArrSplit[0])) || str2.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
