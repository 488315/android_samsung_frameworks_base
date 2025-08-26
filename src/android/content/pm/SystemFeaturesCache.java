package android.content.pm;

import android.util.ArrayMap;
import java.util.Arrays;
import java.util.Collection;

/* loaded from: classes.dex */
public final class SystemFeaturesCache {
    private static final int UNAVAILABLE_FEATURE_VERSION = Integer.MIN_VALUE;
    private static volatile SystemFeaturesCache sInstance;
    private final int[] mSdkFeatureVersions;

    public static void setInstance(SystemFeaturesCache systemFeaturesCache) {
        if (sInstance != null) {
            throw new IllegalStateException("SystemFeaturesCache instance already initialized.");
        }
        sInstance = systemFeaturesCache;
    }

    public static SystemFeaturesCache getInstance() {
        SystemFeaturesCache systemFeaturesCache = sInstance;
        if (systemFeaturesCache != null) {
            return systemFeaturesCache;
        }
        throw new IllegalStateException("SystemFeaturesCache not initialized");
    }

    public static boolean hasInstance() {
        return sInstance != null;
    }

    public static void clearInstance() {
        sInstance = null;
    }

    public SystemFeaturesCache(ArrayMap<String, FeatureInfo> arrayMap) {
        this(arrayMap.values());
    }

    public SystemFeaturesCache(Collection<FeatureInfo> collection) {
        int[] iArr = new int[186];
        this.mSdkFeatureVersions = iArr;
        Arrays.fill(iArr, Integer.MIN_VALUE);
        for (FeatureInfo featureInfo : collection) {
            int iMaybeGetSdkFeatureIndex = PackageManager.maybeGetSdkFeatureIndex(featureInfo.name);
            if (iMaybeGetSdkFeatureIndex >= 0) {
                this.mSdkFeatureVersions[iMaybeGetSdkFeatureIndex] = featureInfo.version;
            }
        }
    }

    public SystemFeaturesCache(int[] iArr) {
        if (iArr.length != 186) {
            throw new IllegalArgumentException(String.format("Unexpected cached SDK feature count: %d (expected %d)", Integer.valueOf(iArr.length), 186));
        }
        this.mSdkFeatureVersions = iArr;
    }

    public int[] getSdkFeatureVersions() {
        return this.mSdkFeatureVersions;
    }

    public Boolean maybeHasFeature(String str, int i) {
        int iMaybeGetSdkFeatureIndex = PackageManager.maybeGetSdkFeatureIndex(str);
        if (iMaybeGetSdkFeatureIndex < 0) {
            return null;
        }
        if (i == Integer.MIN_VALUE && this.mSdkFeatureVersions[iMaybeGetSdkFeatureIndex] == Integer.MIN_VALUE) {
            return null;
        }
        return Boolean.valueOf(this.mSdkFeatureVersions[iMaybeGetSdkFeatureIndex] >= i);
    }
}
