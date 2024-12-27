package android.content.res;

import android.app.ResourcesManager;
import android.content.om.SamsungThemeUtils;
import android.content.res.loader.ResourcesLoader;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public ResourcesKey(
            String resDir,
            String[] splitResDirs,
            String[] overlayPaths,
            String[] libDirs,
            int overrideDisplayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo,
            ResourcesLoader[] loader) {
        this(
                resDir,
                splitResDirs,
                overlayPaths,
                libDirs,
                overrideDisplayId,
                overrideConfig,
                compatInfo,
                loader,
                0);
    }

    public ResourcesKey(
            String resDir,
            String[] splitResDirs,
            String[] overlayPaths,
            String[] libDirs,
            int overrideDisplayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo,
            ResourcesLoader[] loader,
            int originDisplayId) {
        this.mOriginDisplayId = 0;
        this.mInvalidOverlayPaths = null;
        this.mResDir = resDir;
        this.mSplitResDirs = splitResDirs;
        if (ResourcesManager.isOriginDisplayId(originDisplayId)) {
            this.mOriginDisplayId = originDisplayId;
        }
        if (this.mOriginDisplayId == 2) {
            this.mOverlayPaths = SamsungThemeUtils.removeSamsungThemeOverlays(overlayPaths);
        } else if (this.mOriginDisplayId == 1) {
            this.mOverlayPaths = SamsungThemeUtils.removeSamsungThemeOverlaysForCover(overlayPaths);
        } else {
            this.mOverlayPaths = overlayPaths;
        }
        this.mLibDirs = libDirs;
        this.mLoaders = (loader == null || loader.length != 0) ? loader : null;
        this.mDisplayId = overrideDisplayId;
        this.mOverrideConfiguration =
                new Configuration(overrideConfig != null ? overrideConfig : Configuration.EMPTY);
        this.mCompatInfo =
                compatInfo != null ? compatInfo : CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        int hash = (17 * 31) + Objects.hashCode(this.mResDir);
        this.mHash =
                (((((((((((((((hash * 31) + Arrays.hashCode(this.mSplitResDirs)) * 31)
                                                                                                                        + Arrays
                                                                                                                                .hashCode(
                                                                                                                                        this
                                                                                                                                                .mOverlayPaths))
                                                                                                                * 31)
                                                                                                        + Arrays
                                                                                                                .hashCode(
                                                                                                                        this
                                                                                                                                .mLibDirs))
                                                                                                * 31)
                                                                                        + Objects
                                                                                                .hashCode(
                                                                                                        Integer
                                                                                                                .valueOf(
                                                                                                                        this
                                                                                                                                .mDisplayId)))
                                                                                * 31)
                                                                        + Objects.hashCode(
                                                                                this
                                                                                        .mOverrideConfiguration))
                                                                * 31)
                                                        + Objects.hashCode(this.mCompatInfo))
                                                * 31)
                                        + Arrays.hashCode(this.mLoaders))
                                * 31)
                        + Objects.hashCode(Integer.valueOf(this.mOriginDisplayId));
    }

    public ResourcesKey(
            String resDir,
            String[] splitResDirs,
            String[] overlayPaths,
            String[] libDirs,
            int displayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo) {
        this(
                resDir,
                splitResDirs,
                overlayPaths,
                libDirs,
                displayId,
                overrideConfig,
                compatInfo,
                null);
    }

    public boolean hasOverrideConfiguration() {
        return !Configuration.EMPTY.equals(this.mOverrideConfiguration);
    }

    public boolean isPathReferenced(String path) {
        return (this.mResDir != null && this.mResDir.startsWith(path))
                || anyStartsWith(this.mSplitResDirs, path)
                || anyStartsWith(this.mOverlayPaths, path)
                || anyStartsWith(this.mLibDirs, path);
    }

    private static boolean anyStartsWith(String[] list, String prefix) {
        if (list != null) {
            for (String s : list) {
                if (s != null && s.startsWith(prefix)) {
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
        ResourcesKey peer = (ResourcesKey) obj;
        return this.mHash == peer.mHash
                && Objects.equals(this.mResDir, peer.mResDir)
                && Arrays.equals(this.mSplitResDirs, peer.mSplitResDirs)
                && Arrays.equals(this.mOverlayPaths, peer.mOverlayPaths)
                && Arrays.equals(this.mLibDirs, peer.mLibDirs)
                && this.mDisplayId == peer.mDisplayId
                && Objects.equals(this.mOverrideConfiguration, peer.mOverrideConfiguration)
                && Objects.equals(this.mCompatInfo, peer.mCompatInfo)
                && Arrays.equals(this.mLoaders, peer.mLoaders);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder().append("ResourcesKey{");
        builder.append(" mHash=").append(Integer.toHexString(this.mHash));
        builder.append(" mResDir=").append(this.mResDir);
        builder.append(" mSplitDirs=[");
        if (this.mSplitResDirs != null) {
            builder.append(TextUtils.join(",", this.mSplitResDirs));
        }
        builder.append(NavigationBarInflaterView.SIZE_MOD_END);
        builder.append(" mOverlayDirs=[");
        if (this.mOverlayPaths != null) {
            builder.append(TextUtils.join(",", this.mOverlayPaths));
        }
        builder.append(NavigationBarInflaterView.SIZE_MOD_END);
        builder.append(" mLibDirs=[");
        if (this.mLibDirs != null) {
            builder.append(TextUtils.join(",", this.mLibDirs));
        }
        builder.append(NavigationBarInflaterView.SIZE_MOD_END);
        builder.append(" mDisplayId=").append(this.mDisplayId);
        builder.append(" mOverrideConfig=")
                .append(Configuration.resourceQualifierString(this.mOverrideConfiguration));
        builder.append(" mCompatInfo=").append(this.mCompatInfo);
        builder.append(" mLoaders=[");
        if (this.mLoaders != null) {
            builder.append(TextUtils.join(",", this.mLoaders));
        }
        builder.append("]}");
        return builder.toString();
    }
}
