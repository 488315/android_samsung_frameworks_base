package com.samsung.android.scs.ai.sdkcommon.feature;

import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FeatureConfig {
    public static final Companion Companion = new Companion(null);
    public static final String JSON_KEY_APP_VERSION = "app_version";
    public static final String JSON_KEY_FEATURES = "features";
    private final String appVersion;
    private final Map<String, Integer> features;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public FeatureConfig(String str, Map<String, Integer> map) {
        this.appVersion = str;
        this.features = map;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ FeatureConfig copy$default(FeatureConfig featureConfig, String str, Map map, int i, Object obj) {
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

    public final Map<String, Integer> component2() {
        return this.features;
    }

    public final FeatureConfig copy(String str, Map<String, Integer> map) {
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
        return Intrinsics.areEqual(this.appVersion, featureConfig.appVersion) && Intrinsics.areEqual(this.features, featureConfig.features);
    }

    public final String getAppVersion() {
        return this.appVersion;
    }

    public final Map<String, Integer> getFeatures() {
        return this.features;
    }

    public int hashCode() {
        return this.features.hashCode() + (this.appVersion.hashCode() * 31);
    }

    public String toString() {
        return "FeatureConfig(appVersion=" + this.appVersion + ", features=" + this.features + ')';
    }
}
