package android.content.res;

import android.content.om.SamsungThemeUtils;
import android.content.res.loader.ResourcesLoader;
import android.text.TextUtils;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public final class ResourcesKey {
    public final CompatibilityInfo mCompatInfo;
    public int mDisplayId;
    private final int mHash;
    public List<String> mInvalidOverlayPaths;
    public final String[] mLibDirs;
    public final ResourcesLoader[] mLoaders;
    public int mOriginDisplayId;
    public final String[] mOverlayPaths;
    public final Configuration mOverrideConfiguration;
    public final String mResDir;
    public final String[] mSplitResDirs;

    public ResourcesKey(String str, String[] strArr, String[] strArr2, String[] strArr3, int i, Configuration configuration, CompatibilityInfo compatibilityInfo, ResourcesLoader[] resourcesLoaderArr) {
        this(str, strArr, strArr2, strArr3, i, configuration, compatibilityInfo, resourcesLoaderArr, 0);
    }

    public ResourcesKey(String str, String[] strArr, String[] strArr2, String[] strArr3, int i, Configuration configuration, CompatibilityInfo compatibilityInfo, ResourcesLoader[] resourcesLoaderArr, int i2) {
        this.mOriginDisplayId = 0;
        this.mInvalidOverlayPaths = null;
        this.mResDir = str;
        this.mSplitResDirs = strArr;
        if (i == 1) {
            this.mOverlayPaths = SamsungThemeUtils.removeSamsungThemeOverlaysForCover(strArr2);
        } else if (i != 0 && i != -1) {
            this.mOverlayPaths = SamsungThemeUtils.removeSamsungThemeOverlays(strArr2);
        } else {
            this.mOverlayPaths = strArr2;
        }
        this.mLibDirs = strArr3;
        if (resourcesLoaderArr != null && resourcesLoaderArr.length == 0) {
            resourcesLoaderArr = null;
        }
        this.mLoaders = resourcesLoaderArr;
        this.mDisplayId = i;
        Configuration configuration2 = new Configuration(configuration == null ? Configuration.EMPTY : configuration);
        this.mOverrideConfiguration = configuration2;
        compatibilityInfo = compatibilityInfo == null ? CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO : compatibilityInfo;
        this.mCompatInfo = compatibilityInfo;
        this.mHash = ((((((((((((((527 + Objects.hashCode(str)) * 31) + Arrays.hashCode(strArr)) * 31) + Arrays.hashCode(this.mOverlayPaths)) * 31) + Arrays.hashCode(strArr3)) * 31) + Objects.hashCode(Integer.valueOf(this.mDisplayId))) * 31) + Objects.hashCode(configuration2)) * 31) + Objects.hashCode(compatibilityInfo)) * 31) + Arrays.hashCode(resourcesLoaderArr);
    }

    public ResourcesKey(String str, String[] strArr, String[] strArr2, String[] strArr3, int i, Configuration configuration, CompatibilityInfo compatibilityInfo) {
        this(str, strArr, strArr2, strArr3, i, configuration, compatibilityInfo, null);
    }

    public boolean hasOverrideConfiguration() {
        return !Configuration.EMPTY.equals(this.mOverrideConfiguration);
    }

    public boolean isPathReferenced(String str) {
        String str2 = this.mResDir;
        return (str2 != null && str2.startsWith(str)) || anyStartsWith(this.mSplitResDirs, str) || anyStartsWith(this.mOverlayPaths, str) || anyStartsWith(this.mLibDirs, str);
    }

    private static boolean anyStartsWith(String[] strArr, String str) {
        if (strArr != null) {
            for (String str2 : strArr) {
                if (str2 != null && str2.startsWith(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public int hashCode() {
        return this.mHash;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ResourcesKey)) {
            return false;
        }
        ResourcesKey resourcesKey = (ResourcesKey) obj;
        return this.mHash == resourcesKey.mHash && Objects.equals(this.mResDir, resourcesKey.mResDir) && Arrays.equals(this.mSplitResDirs, resourcesKey.mSplitResDirs) && Arrays.equals(this.mOverlayPaths, resourcesKey.mOverlayPaths) && Arrays.equals(this.mLibDirs, resourcesKey.mLibDirs) && this.mDisplayId == resourcesKey.mDisplayId && Objects.equals(this.mOverrideConfiguration, resourcesKey.mOverrideConfiguration) && Objects.equals(this.mCompatInfo, resourcesKey.mCompatInfo) && Arrays.equals(this.mLoaders, resourcesKey.mLoaders);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ResourcesKey{ mHash=");
        sb.append(Integer.toHexString(this.mHash));
        sb.append(" mResDir=");
        sb.append(this.mResDir);
        sb.append(" mSplitDirs=[");
        String[] strArr = this.mSplitResDirs;
        if (strArr != null) {
            sb.append(TextUtils.join(",", strArr));
        }
        sb.append("] mOverlayDirs=[");
        String[] strArr2 = this.mOverlayPaths;
        if (strArr2 != null) {
            sb.append(TextUtils.join(",", strArr2));
        }
        sb.append("] mLibDirs=[");
        String[] strArr3 = this.mLibDirs;
        if (strArr3 != null) {
            sb.append(TextUtils.join(",", strArr3));
        }
        sb.append("] mDisplayId=");
        sb.append(this.mDisplayId);
        sb.append(" mOverrideConfig=");
        sb.append(Configuration.resourceQualifierString(this.mOverrideConfiguration));
        sb.append(" mCompatInfo=");
        sb.append(this.mCompatInfo);
        sb.append(" mLoaders=[");
        ResourcesLoader[] resourcesLoaderArr = this.mLoaders;
        if (resourcesLoaderArr != null) {
            sb.append(TextUtils.join(",", resourcesLoaderArr));
        }
        sb.append("]}");
        return sb.toString();
    }
}
