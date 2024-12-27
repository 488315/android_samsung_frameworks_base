package com.samsung.android.scs.ai.sdkcommon.feature;

import com.android.server.core.jarjar.kotlin.jvm.internal.Intrinsics;

import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class FeatureConfig {
    public static final Companion Companion = new Companion();
    public static final String JSON_KEY_APP_VERSION = "app_version";
    public static final String JSON_KEY_FEATURES = "features";
    private final String appVersion;
    private final Map features;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Companion {}

    public FeatureConfig(String str, Map map) {
        Intrinsics.checkNotNullParameter("appVersion", str);
        Intrinsics.checkNotNullParameter(JSON_KEY_FEATURES, map);
        this.appVersion = str;
        this.features = map;
    }

    public static /* synthetic */ FeatureConfig copy$default(
            FeatureConfig featureConfig, String str, Map map, int i, Object obj) {
        if ((i & 1) != 0) {
            str = featureConfig.appVersion;
        }
        if ((i & 2) != 0) {
            map = featureConfig.features;
        }
        return featureConfig.copy(str, map);
    }

    public final String component1() {
        return this.appVersion;
    }

    public final Map component2() {
        return this.features;
    }

    public final FeatureConfig copy(String str, Map map) {
        Intrinsics.checkNotNullParameter("appVersion", str);
        Intrinsics.checkNotNullParameter(JSON_KEY_FEATURES, map);
        return new FeatureConfig(str, map);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FeatureConfig)) {
            return false;
        }
        FeatureConfig featureConfig = (FeatureConfig) obj;
        String str = this.appVersion;
        String str2 = featureConfig.appVersion;
        if (!(str == null ? str2 == null : str.equals(str2))) {
            return false;
        }
        Map map = this.features;
        Map map2 = featureConfig.features;
        return map == null ? map2 == null : map.equals(map2);
    }

    public final String getAppVersion() {
        return this.appVersion;
    }

    public final Map getFeatures() {
        return this.features;
    }

    public int hashCode() {
        return this.features.hashCode() + (this.appVersion.hashCode() * 31);
    }

    public String toString() {
        return "FeatureConfig(appVersion=" + this.appVersion + ", features=" + this.features + ')';
    }
}
