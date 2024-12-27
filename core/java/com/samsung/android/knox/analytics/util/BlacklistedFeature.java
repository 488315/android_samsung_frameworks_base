package com.samsung.android.knox.analytics.util;

import java.util.List;

public class BlacklistedFeature {
    private List<String> mEvents;
    private String mFeature;

    BlacklistedFeature(String feature, List<String> events) {
        this.mFeature = feature;
        this.mEvents = events;
    }

    public boolean isBlacklisted(String feature, String event) {
        return hasFeatureName(feature) && hasEvent(event);
    }

    public boolean hasFeatureName(String feature) {
        return this.mFeature.equals(feature);
    }

    public boolean hasEvent(String event) {
        for (String current : this.mEvents) {
            if (current.equals("*")) {
                return true;
            }
            String[] wildcardSplit = current.split("\\*");
            if ((current.endsWith("*") && event.startsWith(wildcardSplit[0]))
                    || current.equals(event)) {
                return true;
            }
        }
        return false;
    }
}
