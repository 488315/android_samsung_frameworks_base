package android.app;

import android.content.pm.ApplicationInfo;
import android.content.res.ApkAssets;
import android.content.res.AssetManager;
import android.content.res.CompatResources;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.ResourcesImpl;
import android.content.res.ResourcesKey;
import android.content.res.loader.ResourcesLoader;
import android.hardware.display.DisplayManagerGlobal;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.IBinder;
import android.os.LocaleList;
import android.os.Process;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.DisplayInfo;

import com.android.internal.content.NativeLibraryHelper;
import com.android.internal.util.ArrayUtils;

import com.samsung.android.core.CompatSandbox;
import com.samsung.android.multiwindow.MultiWindowCoreState;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class ResourcesManager {
    private static final int ALL_RESOURCE_REFERENCES_LIMIT = 1000;
    private static final boolean DEBUG = false;
    static final String TAG = "ResourcesManager";
    private static ResourcesManager sResourcesManager;
    private ArrayList<Pair<String[], ApplicationInfo>> mPendingAppInfoUpdates;
    private CompatibilityInfo mResCompatibilityInfo;
    private final Object mLock = new Object();
    private final Configuration mResConfiguration = new Configuration();
    private int mResDisplayId = 0;
    private final ArrayMap<ResourcesKey, WeakReference<ResourcesImpl>> mResourceImpls =
            new ArrayMap<>();
    private final ArrayList<WeakReference<Resources>> mResourceReferences = new ArrayList<>();
    private final ReferenceQueue<Resources> mResourcesReferencesQueue = new ReferenceQueue<>();
    private final ArrayList<WeakReference<Resources>> mAllResourceReferences = new ArrayList<>();
    private final ReferenceQueue<Resources> mAllResourceReferencesQueue = new ReferenceQueue<>();
    private int mAllResourceReferencesCount = 0;
    private LocaleConfig mLocaleConfig = new LocaleConfig(LocaleList.getEmptyLocaleList());
    private final ArrayMap<String, SharedLibraryAssets> mSharedLibAssetsMap = new ArrayMap<>();
    private final ArrayMap<ApkKey, WeakReference<ApkAssets>> mCachedApkAssets = new ArrayMap<>();
    private final WeakHashMap<IBinder, ActivityResources> mActivityResourceReferences =
            new WeakHashMap<>();
    private final UpdateHandler mUpdateCallbacks = new UpdateHandler();
    private final ArraySet<String> mApplicationOwnedApks = new ArraySet<>();

    public ArrayMap<String, SharedLibraryAssets> getRegisteredResourcePaths() {
        return this.mSharedLibAssetsMap;
    }

    public void registerResourcePaths(String uniqueId, ApplicationInfo appInfo) {
        if (!android.content.res.Flags.registerResourcePaths()) {
            return;
        }
        SharedLibraryAssets sharedLibAssets = new SharedLibraryAssets(appInfo);
        synchronized (this.mLock) {
            if (this.mSharedLibAssetsMap.containsKey(uniqueId)) {
                Slog.v(
                        TAG,
                        "Package resources' paths for uniqueId: "
                                + uniqueId
                                + " has already been registered, this is a no-op.");
                return;
            }
            this.mSharedLibAssetsMap.put(uniqueId, sharedLibAssets);
            appendLibAssetsLocked(sharedLibAssets);
            Slog.v(
                    TAG,
                    "The following library key has been added: "
                            + sharedLibAssets.getResourcesKey());
        }
    }

    public int updateResourceImplWithRegisteredLibs(ResourcesImpl impl) {
        if (!android.content.res.Flags.registerResourcePaths()) {
            return 0;
        }
        PathCollector collector = new PathCollector(null);
        int size = this.mSharedLibAssetsMap.size();
        for (int i = 0; i < size; i++) {
            ResourcesKey libraryKey = this.mSharedLibAssetsMap.valueAt(i).getResourcesKey();
            collector.appendKey(libraryKey);
        }
        impl.getAssets().addPresetApkKeys(extractApkKeys(collector.collectedKey()));
        return size;
    }

    public static class ApkKey {
        public final boolean overlay;
        public final String path;
        public final boolean sharedLib;

        public ApkKey(String path, boolean sharedLib, boolean overlay) {
            this.path = path;
            this.sharedLib = sharedLib;
            this.overlay = overlay;
        }

        public int hashCode() {
            int result = (1 * 31) + this.path.hashCode();
            return (((result * 31) + Boolean.hashCode(this.sharedLib)) * 31)
                    + Boolean.hashCode(this.overlay);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ApkKey)) {
                return false;
            }
            ApkKey other = (ApkKey) obj;
            return this.path.equals(other.path)
                    && this.sharedLib == other.sharedLib
                    && this.overlay == other.overlay;
        }

        public String toString() {
            return "ApkKey["
                    + (this.sharedLib ? NativeLibraryHelper.LIB_DIR_NAME : "app")
                    + (this.overlay ? ", overlay" : "")
                    + ": "
                    + this.path
                    + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    protected class ApkAssetsSupplier {
        final ArrayMap<ApkKey, ApkAssets> mLocalCache = new ArrayMap<>();

        protected ApkAssetsSupplier() {}

        ApkAssets load(ApkKey apkKey) throws IOException {
            ApkAssets apkAssets = this.mLocalCache.get(apkKey);
            if (apkAssets == null) {
                ApkAssets apkAssets2 = ResourcesManager.this.loadApkAssets(apkKey);
                this.mLocalCache.put(apkKey, apkAssets2);
                return apkAssets2;
            }
            return apkAssets;
        }
    }

    private static class ActivityResources {
        public final ArrayList<ActivityResource> activityResources;
        public final ReferenceQueue<Resources> activityResourcesQueue;
        public final Configuration overrideConfig;
        public int overrideDisplayId;

        private ActivityResources() {
            this.overrideConfig = new Configuration();
            this.activityResources = new ArrayList<>();
            this.activityResourcesQueue = new ReferenceQueue<>();
        }

        public int countLiveReferences() {
            int count = 0;
            for (int i = 0; i < this.activityResources.size(); i++) {
                WeakReference<Resources> resources = this.activityResources.get(i).resources;
                if (resources != null && resources.get() != null) {
                    count++;
                }
            }
            return count;
        }
    }

    static class ActivityResource {
        public final Configuration overrideConfig;
        public Integer overrideDisplayId;
        public WeakReference<Resources> resources;

        private ActivityResource() {
            this.overrideConfig = new Configuration();
        }
    }

    public static ResourcesManager setInstance(ResourcesManager resourcesManager) {
        ResourcesManager oldResourceManager;
        synchronized (ResourcesManager.class) {
            oldResourceManager = sResourcesManager;
            sResourcesManager = resourcesManager;
        }
        return oldResourceManager;
    }

    public static ResourcesManager getInstance() {
        ResourcesManager resourcesManager;
        synchronized (ResourcesManager.class) {
            if (sResourcesManager == null) {
                sResourcesManager = new ResourcesManager();
            }
            resourcesManager = sResourcesManager;
        }
        return resourcesManager;
    }

    public void invalidatePath(String path) {
        ResourcesImpl resImpl;
        List<ResourcesImpl> implsToFlush = new ArrayList<>();
        synchronized (this.mLock) {
            for (int i = this.mResourceImpls.size() - 1; i >= 0; i--) {
                ResourcesKey key = this.mResourceImpls.keyAt(i);
                if (key.isPathReferenced(path)
                        && (resImpl = this.mResourceImpls.removeAt(i).get()) != null) {
                    implsToFlush.add(resImpl);
                }
            }
        }
        for (int i2 = 0; i2 < implsToFlush.size(); i2++) {
            implsToFlush.get(i2).flushLayoutCache();
        }
        List<ApkAssets> assetsToClose = new ArrayList<>();
        synchronized (this.mCachedApkAssets) {
            for (int i3 = this.mCachedApkAssets.size() - 1; i3 >= 0; i3--) {
                ApkKey key2 = this.mCachedApkAssets.keyAt(i3);
                if (key2.path.equals(path)) {
                    WeakReference<ApkAssets> apkAssetsRef = this.mCachedApkAssets.removeAt(i3);
                    ApkAssets apkAssets = apkAssetsRef != null ? apkAssetsRef.get() : null;
                    if (apkAssets != null) {
                        assetsToClose.add(apkAssets);
                    }
                }
            }
        }
        for (int i4 = 0; i4 < assetsToClose.size(); i4++) {
            assetsToClose.get(i4).close();
        }
        Log.i(
                TAG,
                "Invalidated " + implsToFlush.size() + " asset managers that referenced " + path);
    }

    public Configuration getConfiguration() {
        return this.mResConfiguration;
    }

    public DisplayMetrics getDisplayMetrics() {
        return getDisplayMetrics(
                this.mResDisplayId, DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
    }

    protected DisplayMetrics getDisplayMetrics(int displayId, DisplayAdjustments da) {
        DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
        DisplayMetrics dm = new DisplayMetrics();
        DisplayInfo displayInfo =
                displayManagerGlobal != null
                        ? displayManagerGlobal.getDisplayInfo(displayId)
                        : null;
        if (displayInfo != null) {
            displayInfo.getAppMetrics(dm, da);
        } else {
            dm.setToDefaults();
        }
        return dm;
    }

    private DisplayMetrics getDisplayMetrics(Configuration config) {
        DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
        DisplayMetrics dm = new DisplayMetrics();
        DisplayInfo displayInfo =
                displayManagerGlobal != null
                        ? displayManagerGlobal.getDisplayInfo(this.mResDisplayId)
                        : null;
        if (displayInfo != null) {
            displayInfo.getAppMetrics(
                    dm,
                    DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS.getCompatibilityInfo(),
                    config);
        } else {
            dm.setToDefaults();
        }
        return dm;
    }

    private static void applyDisplayMetricsToConfiguration(
            DisplayMetrics dm, Configuration config) {
        applyDisplayMetricsToConfiguration(dm, config, 0);
    }

    private static void applyDisplayMetricsToConfiguration(
            DisplayMetrics dm, Configuration config, int displayId) {
        config.touchscreen = 1;
        config.densityDpi = dm.densityDpi;
        config.screenWidthDp = (int) ((dm.widthPixels / dm.density) + 0.5f);
        config.screenHeightDp = (int) ((dm.heightPixels / dm.density) + 0.5f);
        int sl = Configuration.resetScreenLayout(config.screenLayout);
        if (dm.widthPixels > dm.heightPixels) {
            config.orientation = 2;
            config.screenLayout =
                    Configuration.reduceScreenLayout(
                            sl, config.screenWidthDp, config.screenHeightDp);
        } else {
            config.orientation = 1;
            config.screenLayout =
                    Configuration.reduceScreenLayout(
                            sl, config.screenHeightDp, config.screenWidthDp);
        }
        if (isOriginDisplayId(displayId)) {
            DisplayInfo info = DisplayManagerGlobal.getInstance().getDisplayInfo(displayId);
            if (info != null) {
                config.smallestScreenWidthDp = (int) (info.smallestNominalAppWidth / dm.density);
                config.windowConfiguration.setMaxBounds(
                        0, 0, info.logicalWidth, info.logicalHeight);
                config.windowConfiguration.setAppBounds(0, 0, info.appWidth, info.appHeight);
                config.windowConfiguration.setDisplayRotation(info.rotation);
            } else {
                config.smallestScreenWidthDp =
                        Math.min(config.screenWidthDp, config.screenHeightDp);
                config.windowConfiguration.setMaxBounds(0, 0, dm.widthPixels, dm.heightPixels);
            }
            config.windowConfiguration.setBounds(config.windowConfiguration.getMaxBounds());
        } else {
            config.smallestScreenWidthDp = Math.min(config.screenWidthDp, config.screenHeightDp);
        }
        config.compatScreenWidthDp = config.screenWidthDp;
        config.compatScreenHeightDp = config.screenHeightDp;
        config.compatSmallestScreenWidthDp = config.smallestScreenWidthDp;
    }

    public boolean applyCompatConfiguration(int displayDensity, Configuration compatConfiguration) {
        synchronized (this.mLock) {
            if (this.mResCompatibilityInfo == null || this.mResCompatibilityInfo.supportsScreen()) {
                return false;
            }
            this.mResCompatibilityInfo.applyToConfiguration(displayDensity, compatConfiguration);
            return true;
        }
    }

    public Display getAdjustedDisplay(int displayId, Resources resources) {
        DisplayManagerGlobal dm = DisplayManagerGlobal.getInstance();
        if (dm == null) {
            return null;
        }
        return dm.getCompatibleDisplay(displayId, resources);
    }

    public void initializeApplicationPaths(String sourceDir, String[] splitDirs) {
        synchronized (this.mLock) {
            if (this.mApplicationOwnedApks.isEmpty()) {
                addApplicationPathsLocked(sourceDir, splitDirs);
            }
        }
    }

    private void addApplicationPathsLocked(String sourceDir, String[] splitDirs) {
        this.mApplicationOwnedApks.add(sourceDir);
        if (splitDirs != null) {
            this.mApplicationOwnedApks.addAll(Arrays.asList(splitDirs));
        }
    }

    private static String overlayPathToIdmapPath(String path) {
        return "/data/resource-cache/" + path.substring(1).replace('/', '@') + "@idmap";
    }

    public ApkAssets loadApkAssets(ApkKey key) throws IOException {
        WeakReference<ApkAssets> apkAssetsRef;
        int flags;
        ApkAssets apkAssets;
        ApkAssets apkAssets2;
        synchronized (this.mCachedApkAssets) {
            apkAssetsRef = this.mCachedApkAssets.get(key);
        }
        if (apkAssetsRef != null
                && (apkAssets2 = apkAssetsRef.get()) != null
                && apkAssets2.isUpToDate()) {
            return apkAssets2;
        }
        int flags2 = 0;
        if (key.sharedLib) {
            flags2 = 0 | 2;
        }
        if (!this.mApplicationOwnedApks.contains(key.path)) {
            flags = flags2;
        } else {
            flags = flags2 | 16;
        }
        if (key.overlay) {
            apkAssets = ApkAssets.loadOverlayFromPath(overlayPathToIdmapPath(key.path), flags);
        } else {
            apkAssets = ApkAssets.loadFromPath(key.path, flags);
        }
        synchronized (this.mCachedApkAssets) {
            this.mCachedApkAssets.put(key, new WeakReference<>(apkAssets));
        }
        return apkAssets;
    }

    private static ArrayList<ApkKey> extractApkKeys(ResourcesKey key) {
        ArrayList<ApkKey> apkKeys = new ArrayList<>();
        if (key.mResDir != null) {
            apkKeys.add(new ApkKey(key.mResDir, false, false));
        }
        if (key.mSplitResDirs != null) {
            for (String splitResDir : key.mSplitResDirs) {
                apkKeys.add(new ApkKey(splitResDir, false, false));
            }
        }
        if (key.mLibDirs != null) {
            for (String libDir : key.mLibDirs) {
                if (libDir.endsWith(".apk")) {
                    apkKeys.add(new ApkKey(libDir, true, false));
                }
            }
        }
        if (key.mOverlayPaths != null) {
            for (String idmapPath : key.mOverlayPaths) {
                apkKeys.add(new ApkKey(idmapPath, false, true));
            }
        }
        return apkKeys;
    }

    protected AssetManager createAssetManager(ResourcesKey key) {
        return createAssetManager(key, null);
    }

    protected AssetManager createAssetManager(ResourcesKey key, ApkAssetsSupplier apkSupplier) {
        ApkAssets load;
        AssetManager.Builder builder = new AssetManager.Builder().setNoInit();
        ArrayList<ApkKey> apkKeys = extractApkKeys(key);
        int n = apkKeys.size();
        for (int i = 0; i < n; i++) {
            ApkKey apkKey = apkKeys.get(i);
            if (apkSupplier != null) {
                try {
                    load = apkSupplier.load(apkKey);
                } catch (IOException e) {
                    if (apkKey.overlay) {
                        Log.w(
                                TAG,
                                String.format("failed to add overlay path '%s'", apkKey.path),
                                e);
                        if (key.mInvalidOverlayPaths == null) {
                            key.mInvalidOverlayPaths = new ArrayList();
                        }
                        key.mInvalidOverlayPaths.add(apkKey.path);
                    } else if (apkKey.sharedLib) {
                        Log.w(
                                TAG,
                                String.format(
                                        "asset path '%s' does not exist or contains no resources",
                                        apkKey.path),
                                e);
                    } else {
                        Log.e(TAG, String.format("failed to add asset path '%s'", apkKey.path), e);
                        return null;
                    }
                }
            } else {
                load = loadApkAssets(apkKey);
            }
            builder.addApkAssets(load);
        }
        if (key.mLoaders != null) {
            for (ResourcesLoader loader : key.mLoaders) {
                builder.addLoader(loader);
            }
        }
        return builder.build();
    }

    private static <T> int countLiveReferences(Collection<WeakReference<T>> collection) {
        int count = 0;
        Iterator<WeakReference<T>> it = collection.iterator();
        while (it.hasNext()) {
            WeakReference<T> ref = it.next();
            T value = ref != null ? ref.get() : null;
            if (value != null) {
                count++;
            }
        }
        return count;
    }

    public void dump(String prefix, PrintWriter printWriter) {
        int references;
        int resImpls;
        int liveAssets;
        synchronized (this.mLock) {
            int refs = countLiveReferences(this.mResourceReferences);
            for (ActivityResources activityResources : this.mActivityResourceReferences.values()) {
                refs += activityResources.countLiveReferences();
            }
            references = refs;
            resImpls = countLiveReferences(this.mResourceImpls.values());
        }
        synchronized (this.mCachedApkAssets) {
            liveAssets = countLiveReferences(this.mCachedApkAssets.values());
        }
        IndentingPrintWriter pw = new IndentingPrintWriter(printWriter, "  ");
        for (int i = 0; i < prefix.length() / 2; i++) {
            pw.increaseIndent();
        }
        pw.println("ResourcesManager:");
        pw.increaseIndent();
        pw.print("total apks: ");
        pw.println(liveAssets);
        pw.print("resources: ");
        pw.println(references);
        pw.print("resource impls: ");
        pw.println(resImpls);
    }

    private Configuration generateConfig(ResourcesKey key) {
        return generateConfig(key, null, null);
    }

    private Configuration generateConfig(
            ResourcesKey key, DisplayMetrics dm, DisplayAdjustments daj) {
        boolean hasOverrideConfig = key.hasOverrideConfiguration();
        if (shouldApplyDisplayMetricsForDex(key) && dm != null) {
            Configuration config = new Configuration(getConfiguration());
            applyDisplayMetricsToConfiguration(dm, config, key.mOriginDisplayId);
            config.updateFrom(key.mOverrideConfiguration);
            if (daj != null) {
                adjustConfigForDexDisplayIfNeeded(config, key.mOriginDisplayId, daj);
                return config;
            }
            return config;
        }
        if (hasOverrideConfig) {
            Configuration config2 = new Configuration(getConfiguration());
            config2.updateFrom(key.mOverrideConfiguration);
            CompatSandbox.resetCompatSandBoxValuesIfNeeded(config2, key.mOverrideConfiguration);
            return config2;
        }
        return getConfiguration();
    }

    private int generateDisplayId(ResourcesKey key) {
        return key.mDisplayId != -1
                ? key.mDisplayId
                : key.mOriginDisplayId != 0 ? key.mOriginDisplayId : this.mResDisplayId;
    }

    private ResourcesImpl createResourcesImpl(ResourcesKey key, ApkAssetsSupplier apkSupplier) {
        AssetManager assets = createAssetManager(key, apkSupplier);
        if (assets == null) {
            return null;
        }
        DisplayAdjustments daj = new DisplayAdjustments(key.mOverrideConfiguration);
        daj.setCompatibilityInfo(key.mCompatInfo);
        DisplayMetrics displayMetrics = getDisplayMetrics(generateDisplayId(key), daj);
        Configuration config = generateConfig(key, displayMetrics, daj);
        ResourcesImpl impl = new ResourcesImpl(assets, displayMetrics, config, daj);
        return impl;
    }

    private ResourcesImpl findResourcesImplForKeyLocked(ResourcesKey key) {
        WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.get(key);
        ResourcesImpl impl = weakImplRef != null ? weakImplRef.get() : null;
        if (impl == null || !impl.getAssets().isUpToDate()) {
            return null;
        }
        return impl;
    }

    public ResourcesImpl findOrCreateResourcesImplForKeyLocked(ResourcesKey key) {
        return findOrCreateResourcesImplForKeyLocked(key, null);
    }

    private ResourcesImpl findOrCreateResourcesImplForKeyLocked(
            ResourcesKey key, ApkAssetsSupplier apkSupplier) {
        ResourcesImpl impl = findResourcesImplForKeyLocked(key);
        if ((impl == null || impl.getAppliedSharedLibsHash() != this.mSharedLibAssetsMap.size())
                && (impl = createResourcesImpl(key, apkSupplier)) != null) {
            WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.get(key);
            ResourcesImpl oldImpl = weakImplRef != null ? weakImplRef.get() : null;
            if (oldImpl != null) {
                key = cleanKeyAndRedirectResourcesImplLocked(key, oldImpl, impl);
            }
            this.mResourceImpls.put(key, new WeakReference<>(impl));
        }
        return impl;
    }

    private ResourcesKey cleanKeyAndRedirectResourcesImplLocked(
            ResourcesKey oldKey, ResourcesImpl oldImpl, ResourcesImpl newImpl) {
        List<String> keyOverlayPaths =
                oldKey.mOverlayPaths != null
                        ? new ArrayList<>(Arrays.asList(oldKey.mOverlayPaths))
                        : new ArrayList<>();
        if (oldKey.mInvalidOverlayPaths != null) {
            for (String path : oldKey.mInvalidOverlayPaths) {
                keyOverlayPaths.remove(path);
            }
        }
        String[] updatedResDirs =
                keyOverlayPaths.isEmpty()
                        ? null
                        : (String[]) keyOverlayPaths.toArray(new String[0]);
        ResourcesKey newKey =
                new ResourcesKey(
                        oldKey.mResDir,
                        oldKey.mSplitResDirs,
                        updatedResDirs,
                        oldKey.mLibDirs,
                        oldKey.mDisplayId,
                        oldKey.mOverrideConfiguration,
                        oldKey.mCompatInfo,
                        oldKey.mLoaders,
                        oldKey.mOriginDisplayId);
        ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys = new ArrayMap<>();
        updatedResourceKeys.put(oldImpl, newKey);
        int resourcesCount = this.mResourceReferences.size();
        for (int i = 0; i < resourcesCount; i++) {
            WeakReference<Resources> ref = this.mResourceReferences.get(i);
            Resources r = ref != null ? ref.get() : null;
            if (r != null) {
                ResourcesKey key = updatedResourceKeys.get(r.getImpl());
                if (key != null) {
                    r.setImpl(newImpl);
                }
            }
        }
        for (ActivityResources activityResources : this.mActivityResourceReferences.values()) {
            int resCount = activityResources.activityResources.size();
            for (int i2 = 0; i2 < resCount; i2++) {
                ActivityResource activityResource = activityResources.activityResources.get(i2);
                Resources r2 = activityResource != null ? activityResource.resources.get() : null;
                if (r2 != null) {
                    ResourcesKey key2 = updatedResourceKeys.get(r2.getImpl());
                    if (key2 != null) {
                        r2.setImpl(newImpl);
                    }
                }
            }
        }
        this.mResourceImpls.remove(oldKey);
        return newKey;
    }

    public ResourcesKey findKeyForResourceImplLocked(ResourcesImpl resourceImpl) {
        int refCount = this.mResourceImpls.size();
        for (int i = 0; i < refCount; i++) {
            WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i);
            if (weakImplRef != null && weakImplRef.refersTo(resourceImpl)) {
                return this.mResourceImpls.keyAt(i);
            }
        }
        return null;
    }

    public boolean isSameResourcesOverrideConfig(
            IBinder activityToken, Configuration overrideConfig) {
        ActivityResources activityResources;
        synchronized (this.mLock) {
            if (activityToken == null) {
                activityResources = null;
            } else {
                try {
                    activityResources = this.mActivityResourceReferences.get(activityToken);
                } finally {
                }
            }
            boolean z = true;
            if (activityResources == null) {
                if (overrideConfig != null) {
                    z = false;
                }
                return z;
            }
            if (!Objects.equals(activityResources.overrideConfig, overrideConfig)
                    && (overrideConfig == null
                            || activityResources.overrideConfig == null
                            || overrideConfig.diffPublicOnly(activityResources.overrideConfig)
                                    != 0)) {
                z = false;
            }
            return z;
        }
    }

    private ActivityResources getOrCreateActivityResourcesStructLocked(IBinder activityToken) {
        ActivityResources activityResources = this.mActivityResourceReferences.get(activityToken);
        if (activityResources == null) {
            ActivityResources activityResources2 = new ActivityResources();
            this.mActivityResourceReferences.put(activityToken, activityResources2);
            return activityResources2;
        }
        return activityResources;
    }

    private Resources findResourcesForActivityLocked(
            IBinder targetActivityToken, ResourcesKey targetKey, ClassLoader targetClassLoader) {
        ActivityResources activityResources =
                getOrCreateActivityResourcesStructLocked(targetActivityToken);
        int size = activityResources.activityResources.size();
        int index = 0;
        while (true) {
            if (index >= size) {
                return null;
            }
            ActivityResource activityResource = activityResources.activityResources.get(index);
            Resources resources = activityResource.resources.get();
            ResourcesKey key =
                    resources != null ? findKeyForResourceImplLocked(resources.getImpl()) : null;
            if (key == null
                    || !Objects.equals(resources.getClassLoader(), targetClassLoader)
                    || !Objects.equals(key, targetKey)) {
                index++;
            } else {
                return resources;
            }
        }
    }

    private Resources createResourcesForActivityLocked(
            IBinder activityToken,
            Configuration initialOverrideConfig,
            Integer overrideDisplayId,
            ClassLoader classLoader,
            ResourcesImpl impl,
            CompatibilityInfo compatInfo) {
        ActivityResources activityResources =
                getOrCreateActivityResourcesStructLocked(activityToken);
        cleanupReferences(
                activityResources.activityResources,
                activityResources.activityResourcesQueue,
                new Function() { // from class:
                    // android.app.ResourcesManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        WeakReference weakReference;
                        weakReference = ((ResourcesManager.ActivityResource) obj).resources;
                        return weakReference;
                    }
                });
        Resources resources =
                compatInfo.needsCompatResources()
                        ? new CompatResources(classLoader)
                        : new Resources(classLoader);
        resources.setImpl(impl);
        resources.setCallbacks(this.mUpdateCallbacks);
        ActivityResource activityResource = new ActivityResource();
        activityResource.resources =
                new WeakReference<>(resources, activityResources.activityResourcesQueue);
        activityResource.overrideConfig.setTo(initialOverrideConfig);
        activityResource.overrideDisplayId = overrideDisplayId;
        activityResources.activityResources.add(activityResource);
        return resources;
    }

    private Resources createResourcesLocked(
            ClassLoader classLoader, ResourcesImpl impl, CompatibilityInfo compatInfo) {
        cleanupReferences(this.mResourceReferences, this.mResourcesReferencesQueue);
        Resources resources =
                compatInfo.needsCompatResources()
                        ? new CompatResources(classLoader)
                        : new Resources(classLoader);
        resources.setImpl(impl);
        resources.setCallbacks(this.mUpdateCallbacks);
        this.mResourceReferences.add(
                new WeakReference<>(resources, this.mResourcesReferencesQueue));
        return resources;
    }

    public Resources createBaseTokenResources(
            IBinder token,
            String resDir,
            String[] splitResDirs,
            String[] legacyOverlayDirs,
            String[] overlayPaths,
            String[] libDirs,
            int displayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo,
            ClassLoader classLoader,
            List<ResourcesLoader> loaders) {
        return createBaseTokenResources(
                token,
                resDir,
                splitResDirs,
                legacyOverlayDirs,
                overlayPaths,
                libDirs,
                displayId,
                overrideConfig,
                compatInfo,
                classLoader,
                loaders,
                0);
    }

    public Resources createBaseTokenResources(
            IBinder token,
            String resDir,
            String[] splitResDirs,
            String[] legacyOverlayDirs,
            String[] overlayPaths,
            String[] libDirs,
            int displayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo,
            ClassLoader classLoader,
            List<ResourcesLoader> loaders,
            int originDisplayId) {
        try {
            Trace.traceBegin(8192L, "ResourcesManager#createBaseActivityResources");
            ResourcesKey key =
                    new ResourcesKey(
                            resDir,
                            splitResDirs,
                            combinedOverlayPaths(legacyOverlayDirs, overlayPaths),
                            libDirs,
                            displayId,
                            overrideConfig,
                            compatInfo,
                            loaders == null
                                    ? null
                                    : (ResourcesLoader[]) loaders.toArray(new ResourcesLoader[0]),
                            originDisplayId);
            ClassLoader classLoader2 =
                    classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
            try {
                synchronized (this.mLock) {
                    try {
                        try {
                            getOrCreateActivityResourcesStructLocked(token);
                        } catch (Throwable th) {
                            th = th;
                            while (true) {
                                try {
                                    throw th;
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        Trace.traceEnd(8192L);
                        throw th;
                    }
                }
                updateResourcesForActivity(token, overrideConfig, displayId);
                synchronized (this.mLock) {
                    Resources resources = findResourcesForActivityLocked(token, key, classLoader2);
                    if (resources != null) {
                        Trace.traceEnd(8192L);
                        return resources;
                    }
                    Resources createResourcesForActivity =
                            createResourcesForActivity(
                                    token, key, Configuration.EMPTY, null, classLoader2, null);
                    Trace.traceEnd(8192L);
                    return createResourcesForActivity;
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    private void rebaseKeyForActivity(
            IBinder activityToken, ResourcesKey key, boolean overridesActivityDisplay) {
        Configuration config;
        synchronized (this.mLock) {
            ActivityResources activityResources =
                    getOrCreateActivityResourcesStructLocked(activityToken);
            if (key.mDisplayId == -1) {
                key.mDisplayId = activityResources.overrideDisplayId;
            }
            if (key.hasOverrideConfiguration()) {
                config = new Configuration(activityResources.overrideConfig);
                config.updateFrom(key.mOverrideConfiguration);
            } else {
                config = activityResources.overrideConfig;
            }
            if (overridesActivityDisplay
                    && key.mOverrideConfiguration.windowConfiguration.getAppBounds() == null) {
                if (!key.hasOverrideConfiguration()) {
                    config = new Configuration(config);
                }
                config.windowConfiguration.setAppBounds(null);
            }
            key.mOverrideConfiguration.setTo(config);
        }
    }

    private void rebaseKeyForDisplay(ResourcesKey key, int overrideDisplay) {
        Configuration temp = new Configuration();
        DisplayAdjustments daj = new DisplayAdjustments(key.mOverrideConfiguration);
        daj.setCompatibilityInfo(key.mCompatInfo);
        DisplayMetrics dm = getDisplayMetrics(overrideDisplay, daj);
        applyDisplayMetricsToConfiguration(dm, temp, key.mDisplayId);
        if (key.hasOverrideConfiguration()) {
            temp.updateFrom(key.mOverrideConfiguration);
        }
        key.mOverrideConfiguration.setTo(temp);
    }

    private static <T> void cleanupReferences(
            ArrayList<WeakReference<T>> references, ReferenceQueue<T> referenceQueue) {
        cleanupReferences(references, referenceQueue, Function.identity());
    }

    private static <C, T> void cleanupReferences(
            ArrayList<C> referenceContainers,
            ReferenceQueue<T> referenceQueue,
            final Function<C, WeakReference<T>> unwrappingFunction) {
        Reference<? extends T> enqueuedRef = referenceQueue.poll();
        if (enqueuedRef == null) {
            return;
        }
        final HashSet<Reference<? extends T>> deadReferences = new HashSet<>();
        while (enqueuedRef != null) {
            deadReferences.add(enqueuedRef);
            enqueuedRef = referenceQueue.poll();
        }
        ArrayUtils.unstableRemoveIf(
                referenceContainers,
                new Predicate() { // from class:
                    // android.app.ResourcesManager$$ExternalSyntheticLambda1
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ResourcesManager.lambda$cleanupReferences$1(
                                unwrappingFunction, deadReferences, obj);
                    }
                });
    }

    static /* synthetic */ boolean lambda$cleanupReferences$1(
            Function unwrappingFunction, HashSet deadReferences, Object refContainer) {
        WeakReference weakReference = (WeakReference) unwrappingFunction.apply(refContainer);
        return weakReference == null || deadReferences.contains(weakReference);
    }

    private ApkAssetsSupplier createApkAssetsSupplierNotLocked(ResourcesKey key) {
        Trace.traceBegin(8192L, "ResourcesManager#createApkAssetsSupplierNotLocked");
        try {
            ApkAssetsSupplier supplier = new ApkAssetsSupplier();
            ArrayList<ApkKey> apkKeys = extractApkKeys(key);
            int n = apkKeys.size();
            for (int i = 0; i < n; i++) {
                ApkKey apkKey = apkKeys.get(i);
                try {
                    supplier.load(apkKey);
                } catch (IOException e) {
                    Log.w(TAG, String.format("failed to preload asset path '%s'", apkKey.path), e);
                }
            }
            return supplier;
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    private Resources createResources(
            ResourcesKey key, ClassLoader classLoader, ApkAssetsSupplier apkSupplier) {
        synchronized (this.mLock) {
            ResourcesImpl resourcesImpl = findOrCreateResourcesImplForKeyLocked(key, apkSupplier);
            if (resourcesImpl == null) {
                return null;
            }
            return createResourcesLocked(classLoader, resourcesImpl, key.mCompatInfo);
        }
    }

    private Resources createResourcesForActivity(
            IBinder activityToken,
            ResourcesKey key,
            Configuration initialOverrideConfig,
            Integer overrideDisplayId,
            ClassLoader classLoader,
            ApkAssetsSupplier apkSupplier) {
        synchronized (this.mLock) {
            ResourcesImpl resourcesImpl = findOrCreateResourcesImplForKeyLocked(key, apkSupplier);
            if (resourcesImpl == null) {
                return null;
            }
            return createResourcesForActivityLocked(
                    activityToken,
                    initialOverrideConfig,
                    overrideDisplayId,
                    classLoader,
                    resourcesImpl,
                    key.mCompatInfo);
        }
    }

    public Resources getResources(
            IBinder activityToken,
            String resDir,
            String[] splitResDirs,
            String[] legacyOverlayDirs,
            String[] overlayPaths,
            String[] libDirs,
            Integer overrideDisplayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo,
            ClassLoader classLoader,
            List<ResourcesLoader> loaders) {
        return getResources(
                activityToken,
                resDir,
                splitResDirs,
                legacyOverlayDirs,
                overlayPaths,
                libDirs,
                overrideDisplayId,
                overrideConfig,
                compatInfo,
                classLoader,
                loaders,
                false,
                false);
    }

    public Resources getResources(
            IBinder activityToken,
            String resDir,
            String[] splitResDirs,
            String[] legacyOverlayDirs,
            String[] overlayPaths,
            String[] libDirs,
            Integer overrideDisplayId,
            Configuration overrideConfig,
            CompatibilityInfo compatInfo,
            ClassLoader classLoader,
            List<ResourcesLoader> loaders,
            boolean creatingDisplayContext,
            boolean creatingOriginConfigContext) {
        ResourcesKey key;
        ClassLoader classLoader2;
        ApkAssetsSupplier assetsSupplier;
        Resources resources;
        try {
            Trace.traceBegin(8192L, "ResourcesManager#getResources");
        } catch (Throwable th) {
            th = th;
        }
        try {
            int originDisplayId =
                    shouldApplyOriginDisplayId(
                            creatingDisplayContext, creatingOriginConfigContext, overrideDisplayId);
            key =
                    new ResourcesKey(
                            resDir,
                            splitResDirs,
                            combinedOverlayPaths(legacyOverlayDirs, overlayPaths),
                            libDirs,
                            overrideDisplayId != null ? overrideDisplayId.intValue() : -1,
                            overrideConfig,
                            compatInfo,
                            loaders == null
                                    ? null
                                    : (ResourcesLoader[]) loaders.toArray(new ResourcesLoader[0]),
                            originDisplayId);
            classLoader2 = classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
            try {
                assetsSupplier = createApkAssetsSupplierNotLocked(key);
                if (overrideDisplayId != null) {
                    try {
                        rebaseKeyForDisplay(key, overrideDisplayId.intValue());
                    } catch (Throwable th2) {
                        th = th2;
                        Trace.traceEnd(8192L);
                        throw th;
                    }
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (Throwable th4) {
            th = th4;
            Trace.traceEnd(8192L);
            throw th;
        }
        try {
            if (activityToken == null) {
                Resources resources2 = createResources(key, classLoader2, assetsSupplier);
                if (resources2 != null && key.mDisplayId == 2) {
                    DisplayAdjustments daj = resources2.getImpl().getDisplayAdjustments();
                    DisplayMetrics dm = getDisplayMetrics(key.mDisplayId, daj);
                    Configuration config = generateConfig(key, dm, daj);
                    if (resources2.getConfiguration().diff(config) != 0) {
                        resources2.getImpl().updateConfiguration(config, dm, null);
                    }
                }
                resources = resources2;
            } else {
                Configuration initialOverrideConfig = new Configuration(key.mOverrideConfiguration);
                rebaseKeyForActivity(activityToken, key, overrideDisplayId != null);
                resources =
                        createResourcesForActivity(
                                activityToken,
                                key,
                                initialOverrideConfig,
                                overrideDisplayId,
                                classLoader2,
                                assetsSupplier);
            }
            Trace.traceEnd(8192L);
            return resources;
        } catch (Throwable th5) {
            th = th5;
            Trace.traceEnd(8192L);
            throw th;
        }
    }

    public void updateResourcesForActivity(
            IBinder activityToken, Configuration overrideConfig, int displayId) {
        ResourcesKey newKey;
        ResourcesImpl resourcesImpl;
        try {
            Trace.traceBegin(8192L, "ResourcesManager#updateResourcesForActivity");
            if (displayId == -1) {
                throw new IllegalArgumentException("displayId can not be INVALID_DISPLAY");
            }
            synchronized (this.mLock) {
                ActivityResources activityResources =
                        getOrCreateActivityResourcesStructLocked(activityToken);
                boolean movedToDifferentDisplay = activityResources.overrideDisplayId != displayId;
                if (Objects.equals(activityResources.overrideConfig, overrideConfig)
                        && !movedToDifferentDisplay) {
                    return;
                }
                new Configuration(activityResources.overrideConfig);
                if (overrideConfig != null) {
                    activityResources.overrideConfig.setTo(overrideConfig);
                } else {
                    activityResources.overrideConfig.unset();
                }
                activityResources.overrideDisplayId = displayId;
                applyAllPendingAppInfoUpdates();
                int refCount = activityResources.activityResources.size();
                for (int i = 0; i < refCount; i++) {
                    ActivityResource activityResource = activityResources.activityResources.get(i);
                    Resources resources = activityResource.resources.get();
                    if (resources != null
                            && (newKey =
                                            rebaseActivityOverrideConfig(
                                                    activityResource, overrideConfig, displayId))
                                    != null
                            && (resourcesImpl = findOrCreateResourcesImplForKeyLocked(newKey))
                                    != null
                            && resourcesImpl != resources.getImpl()) {
                        resources.setImpl(resourcesImpl);
                    }
                }
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    private ResourcesKey rebaseActivityOverrideConfig(
            ActivityResource activityResource, Configuration newOverrideConfig, int displayId) {
        Resources resources = activityResource.resources.get();
        if (resources != null) {
            ResourcesKey oldKey = findKeyForResourceImplLocked(resources.getImpl());
            if (oldKey == null) {
                Slog.e(TAG, "can't find ResourcesKey for resources impl=" + resources.getImpl());
                return null;
            }
            Configuration rebasedOverrideConfig = new Configuration();
            if (newOverrideConfig != null) {
                rebasedOverrideConfig.setTo(newOverrideConfig);
            }
            Integer overrideDisplayId = activityResource.overrideDisplayId;
            if (overrideDisplayId != null) {
                DisplayAdjustments displayAdjustments =
                        new DisplayAdjustments(rebasedOverrideConfig);
                displayAdjustments.getConfiguration().setTo(activityResource.overrideConfig);
                displayAdjustments.setCompatibilityInfo(oldKey.mCompatInfo);
                DisplayMetrics dm =
                        getDisplayMetrics(overrideDisplayId.intValue(), displayAdjustments);
                applyDisplayMetricsToConfiguration(
                        dm, rebasedOverrideConfig, overrideDisplayId.intValue());
            }
            boolean hasOverrideConfig =
                    !activityResource.overrideConfig.equals(Configuration.EMPTY);
            if (hasOverrideConfig) {
                rebasedOverrideConfig.updateFrom(activityResource.overrideConfig);
            }
            if (activityResource.overrideDisplayId != null
                    && activityResource.overrideConfig.windowConfiguration.getAppBounds() == null) {
                rebasedOverrideConfig.windowConfiguration.setAppBounds(null);
            }
            int displayId2 = overrideDisplayId != null ? overrideDisplayId.intValue() : displayId;
            ResourcesKey newKey =
                    new ResourcesKey(
                            oldKey.mResDir,
                            oldKey.mSplitResDirs,
                            oldKey.mOverlayPaths,
                            oldKey.mLibDirs,
                            displayId2,
                            rebasedOverrideConfig,
                            oldKey.mCompatInfo,
                            oldKey.mLoaders,
                            displayId2);
            return newKey;
        }
        return null;
    }

    public void appendPendingAppInfoUpdate(String[] oldSourceDirs, ApplicationInfo appInfo) {
        synchronized (this.mLock) {
            if (this.mPendingAppInfoUpdates == null) {
                this.mPendingAppInfoUpdates = new ArrayList<>();
            }
            for (int i = this.mPendingAppInfoUpdates.size() - 1; i >= 0; i--) {
                if (ArrayUtils.containsAll(
                        oldSourceDirs, this.mPendingAppInfoUpdates.get(i).first)) {
                    this.mPendingAppInfoUpdates.remove(i);
                }
            }
            this.mPendingAppInfoUpdates.add(new Pair<>(oldSourceDirs, appInfo));
        }
    }

    public final void applyAllPendingAppInfoUpdates() {
        synchronized (this.mLock) {
            if (this.mPendingAppInfoUpdates != null) {
                int n = this.mPendingAppInfoUpdates.size();
                for (int i = 0; i < n; i++) {
                    Pair<String[], ApplicationInfo> appInfo = this.mPendingAppInfoUpdates.get(i);
                    applyNewResourceDirsLocked(appInfo.first, appInfo.second);
                }
                this.mPendingAppInfoUpdates = null;
            }
        }
    }

    public final boolean applyConfigurationToResources(
            Configuration config, CompatibilityInfo compat) {
        int i;
        synchronized (this.mLock) {
            try {
                Trace.traceBegin(8192L, "ResourcesManager#applyConfigurationToResources");
                if (!this.mResConfiguration.isOtherSeqNewer(config) && compat == null) {
                    return false;
                }
                int changes = this.mResConfiguration.updateFrom(config);
                if (compat != null
                        && (this.mResCompatibilityInfo == null
                                || !this.mResCompatibilityInfo.equals(compat))) {
                    this.mResCompatibilityInfo = compat;
                    changes |= 3328;
                }
                if ((Integer.MIN_VALUE & changes) != 0) {
                    applyAllPendingAppInfoUpdates();
                }
                DisplayMetrics displayMetrics = getDisplayMetrics(config);
                Resources.updateSystemConfiguration(config, displayMetrics, compat);
                ApplicationPackageManager.configurationChanged();
                Configuration tmpConfig = new Configuration();
                int i2 = this.mResourceImpls.size() - 1;
                while (i2 >= 0) {
                    ResourcesKey key = this.mResourceImpls.keyAt(i2);
                    WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i2);
                    ResourcesImpl r = weakImplRef != null ? weakImplRef.get() : null;
                    if (r != null) {
                        i = i2;
                        applyConfigurationToResourcesLocked(config, compat, tmpConfig, key, r);
                    } else {
                        i = i2;
                        this.mResourceImpls.removeAt(i);
                    }
                    i2 = i - 1;
                }
                return changes != 0;
            } finally {
                Trace.traceEnd(8192L);
            }
        }
    }

    private void applyConfigurationToResourcesLocked(
            Configuration config,
            CompatibilityInfo compat,
            Configuration tmpConfig,
            ResourcesKey key,
            ResourcesImpl resourcesImpl) {
        tmpConfig.setTo(config);
        if (key.hasOverrideConfiguration()) {
            tmpConfig.updateFrom(key.mOverrideConfiguration);
        }
        DisplayAdjustments daj = resourcesImpl.getDisplayAdjustments();
        if (compat != null) {
            daj = new DisplayAdjustments(daj);
            daj.setCompatibilityInfo(compat);
        }
        daj.setConfiguration(tmpConfig);
        DisplayMetrics dm = getDisplayMetrics(generateDisplayId(key), daj);
        adjustConfigForDexDisplayIfNeeded(tmpConfig, key.mOriginDisplayId, daj);
        resourcesImpl.updateConfiguration(tmpConfig, dm, compat);
    }

    public void appendLibAssetForMainAssetPath(String assetPath, String libAsset) {
        appendLibAssetsForMainAssetPath(assetPath, new String[] {libAsset});
    }

    public void appendLibAssetsForMainAssetPath(String assetPath, String[] libAssets) {
        int implCount;
        String[] strArr = libAssets;
        synchronized (this.mLock) {
            ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys = new ArrayMap<>();
            int implCount2 = this.mResourceImpls.size();
            int i = 0;
            while (i < implCount2) {
                ResourcesKey key = this.mResourceImpls.keyAt(i);
                WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i);
                ResourcesImpl impl = weakImplRef != null ? weakImplRef.get() : null;
                if (impl == null || !Objects.equals(key.mResDir, assetPath)) {
                    implCount = implCount2;
                } else {
                    String[] newLibAssets = key.mLibDirs;
                    for (String libAsset : strArr) {
                        newLibAssets =
                                (String[])
                                        ArrayUtils.appendElement(
                                                String.class, newLibAssets, libAsset);
                    }
                    if (Arrays.equals(newLibAssets, key.mLibDirs)) {
                        implCount = implCount2;
                    } else {
                        implCount = implCount2;
                        updatedResourceKeys.put(
                                impl,
                                new ResourcesKey(
                                        key.mResDir,
                                        key.mSplitResDirs,
                                        key.mOverlayPaths,
                                        newLibAssets,
                                        key.mDisplayId,
                                        key.mOverrideConfiguration,
                                        key.mCompatInfo,
                                        key.mLoaders,
                                        key.mOriginDisplayId));
                    }
                }
                i++;
                strArr = libAssets;
                implCount2 = implCount;
            }
            redirectResourcesToNewImplLocked(updatedResourceKeys);
        }
    }

    private static class PathCollector {
        public final ResourcesKey originalKey;
        public final ArrayList<String> orderedLibs = new ArrayList<>();
        public final ArraySet<String> libsSet = new ArraySet<>();
        public final ArrayList<String> orderedOverlays = new ArrayList<>();
        public final ArraySet<String> overlaysSet = new ArraySet<>();

        static void appendNewPath(
                String path, ArraySet<String> uniquePaths, ArrayList<String> orderedPaths) {
            if (uniquePaths.add(path)) {
                orderedPaths.add(path);
            }
        }

        static void appendAllNewPaths(
                String[] paths, ArraySet<String> uniquePaths, ArrayList<String> orderedPaths) {
            if (paths == null) {
                return;
            }
            for (String str : paths) {
                appendNewPath(str, uniquePaths, orderedPaths);
            }
        }

        PathCollector(ResourcesKey original) {
            this.originalKey = original;
            if (this.originalKey != null) {
                appendKey(this.originalKey);
            }
        }

        public void appendKey(ResourcesKey key) {
            appendAllNewPaths(key.mLibDirs, this.libsSet, this.orderedLibs);
            appendAllNewPaths(key.mOverlayPaths, this.overlaysSet, this.orderedOverlays);
        }

        boolean isSameAsOriginal() {
            if (this.originalKey == null) {
                return this.orderedLibs.isEmpty() && this.orderedOverlays.isEmpty();
            }
            if ((this.originalKey.mLibDirs == null && this.orderedLibs.isEmpty())
                    || (this.originalKey.mLibDirs != null
                            && this.originalKey.mLibDirs.length == this.orderedLibs.size())) {
                if (this.originalKey.mOverlayPaths == null && this.orderedOverlays.isEmpty()) {
                    return true;
                }
                if (this.originalKey.mOverlayPaths != null
                        && this.originalKey.mOverlayPaths.length == this.orderedOverlays.size()) {
                    return true;
                }
            }
            return false;
        }

        ResourcesKey collectedKey() {
            return new ResourcesKey(
                    this.originalKey == null ? null : this.originalKey.mResDir,
                    this.originalKey == null ? null : this.originalKey.mSplitResDirs,
                    (String[]) this.orderedOverlays.toArray(new String[0]),
                    (String[]) this.orderedLibs.toArray(new String[0]),
                    this.originalKey == null ? 0 : this.originalKey.mDisplayId,
                    this.originalKey == null ? null : this.originalKey.mOverrideConfiguration,
                    this.originalKey == null ? null : this.originalKey.mCompatInfo,
                    this.originalKey == null ? null : this.originalKey.mLoaders);
        }
    }

    private ResourcesKey createNewResourceKeyIfNeeded(ResourcesKey original, ResourcesKey library) {
        PathCollector collector = new PathCollector(original);
        collector.appendKey(library);
        if (collector.isSameAsOriginal()) {
            return null;
        }
        return collector.collectedKey();
    }

    private void appendLibAssetsLocked(SharedLibraryAssets libAssets) {
        ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys = new ArrayMap<>();
        int implCount = this.mResourceImpls.size();
        for (int i = 0; i < implCount; i++) {
            ResourcesKey key = this.mResourceImpls.keyAt(i);
            WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i);
            ResourcesImpl impl = weakImplRef != null ? weakImplRef.get() : null;
            if (impl == null) {
                Slog.w(TAG, "Found a null ResourcesImpl, skipped.");
            } else {
                ResourcesKey newKey =
                        createNewResourceKeyIfNeeded(key, libAssets.getResourcesKey());
                if (newKey != null) {
                    updatedResourceKeys.put(impl, newKey);
                }
            }
        }
        redirectAllResourcesToNewImplLocked(updatedResourceKeys);
    }

    private void applyNewResourceDirsLocked(String[] oldSourceDirs, ApplicationInfo appInfo) {
        String[] strArr;
        String baseCodePath;
        int i;
        int implCount;
        ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys;
        String[] copiedSplitDirs;
        int myUid;
        ApplicationInfo applicationInfo = appInfo;
        try {
            Trace.traceBegin(8192L, "ResourcesManager#applyNewResourceDirsLocked");
            String baseCodePath2 = appInfo.getBaseCodePath();
            int myUid2 = Process.myUid();
            if (applicationInfo.uid == myUid2) {
                strArr = applicationInfo.splitSourceDirs;
            } else {
                strArr = applicationInfo.splitPublicSourceDirs;
            }
            String[] newSplitDirs = strArr;
            String[] copiedSplitDirs2 = (String[]) ArrayUtils.cloneOrNull(newSplitDirs);
            String[] copiedResourceDirs =
                    combinedOverlayPaths(
                            applicationInfo.resourceDirs, applicationInfo.overlayPaths);
            if (applicationInfo.uid == myUid2) {
                addApplicationPathsLocked(baseCodePath2, copiedSplitDirs2);
            }
            ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys2 = new ArrayMap<>();
            int implCount2 = this.mResourceImpls.size();
            int i2 = 0;
            while (i2 < implCount2) {
                ResourcesKey key = this.mResourceImpls.keyAt(i2);
                WeakReference<ResourcesImpl> weakImplRef = this.mResourceImpls.valueAt(i2);
                ResourcesImpl impl = weakImplRef != null ? weakImplRef.get() : null;
                if (impl == null) {
                    baseCodePath = baseCodePath2;
                    i = i2;
                    implCount = implCount2;
                    updatedResourceKeys = updatedResourceKeys2;
                    copiedSplitDirs = copiedSplitDirs2;
                    myUid = myUid2;
                } else if (key.mResDir == null
                        && ActivityThread.isSystem()
                        && !"android".equals(applicationInfo.packageName)) {
                    Log.i(TAG, "skip fill in resDir with other app resource path");
                    baseCodePath = baseCodePath2;
                    i = i2;
                    implCount = implCount2;
                    updatedResourceKeys = updatedResourceKeys2;
                    copiedSplitDirs = copiedSplitDirs2;
                    myUid = myUid2;
                } else {
                    if (key.mResDir != null) {
                        try {
                            if (!key.mResDir.equals(baseCodePath2)
                                    && !ArrayUtils.contains(oldSourceDirs, key.mResDir)) {
                                baseCodePath = baseCodePath2;
                                i = i2;
                                implCount = implCount2;
                                updatedResourceKeys = updatedResourceKeys2;
                                copiedSplitDirs = copiedSplitDirs2;
                                myUid = myUid2;
                            }
                        } catch (Throwable th) {
                            th = th;
                            Trace.traceEnd(8192L);
                            throw th;
                        }
                    }
                    String[] strArr2 = key.mLibDirs;
                    int implCount3 = key.mDisplayId;
                    myUid = myUid2;
                    baseCodePath = baseCodePath2;
                    i = i2;
                    implCount = implCount2;
                    updatedResourceKeys = updatedResourceKeys2;
                    copiedSplitDirs = copiedSplitDirs2;
                    updatedResourceKeys.put(
                            impl,
                            new ResourcesKey(
                                    baseCodePath2,
                                    copiedSplitDirs2,
                                    copiedResourceDirs,
                                    strArr2,
                                    implCount3,
                                    key.mOverrideConfiguration,
                                    key.mCompatInfo,
                                    key.mLoaders));
                }
                i2 = i + 1;
                updatedResourceKeys2 = updatedResourceKeys;
                implCount2 = implCount;
                copiedSplitDirs2 = copiedSplitDirs;
                myUid2 = myUid;
                baseCodePath2 = baseCodePath;
                applicationInfo = appInfo;
            }
            redirectResourcesToNewImplLocked(updatedResourceKeys2);
            Trace.traceEnd(8192L);
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String[] combinedOverlayPaths(String[] resourceDirs, String[] overlayPaths) {
        if (resourceDirs == null) {
            return (String[]) ArrayUtils.cloneOrNull(overlayPaths);
        }
        if (overlayPaths == null) {
            return (String[]) ArrayUtils.cloneOrNull(resourceDirs);
        }
        ArrayList<String> paths = new ArrayList<>(overlayPaths.length + resourceDirs.length);
        for (String str : overlayPaths) {
            paths.add(str);
        }
        for (String path : resourceDirs) {
            if (!paths.contains(path)) {
                paths.add(path);
            }
        }
        return (String[]) paths.toArray(new String[0]);
    }

    public void redirectResourcesToNewImplLocked(
            ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys) {
        ResourcesKey key;
        ResourcesKey key2;
        if (updatedResourceKeys.isEmpty()) {
            return;
        }
        int resourcesCount = this.mResourceReferences.size();
        int i = 0;
        while (true) {
            if (i < resourcesCount) {
                WeakReference<Resources> ref = this.mResourceReferences.get(i);
                Resources r = ref != null ? ref.get() : null;
                if (r != null && (key2 = updatedResourceKeys.get(r.getImpl())) != null) {
                    ResourcesImpl impl = findOrCreateResourcesImplForKeyLocked(key2);
                    if (impl == null) {
                        throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
                    }
                    r.setImpl(impl);
                }
                i++;
            } else {
                for (ActivityResources activityResources :
                        this.mActivityResourceReferences.values()) {
                    int resCount = activityResources.activityResources.size();
                    for (int i2 = 0; i2 < resCount; i2++) {
                        ActivityResource activityResource =
                                activityResources.activityResources.get(i2);
                        Resources r2 =
                                activityResource != null ? activityResource.resources.get() : null;
                        if (r2 != null && (key = updatedResourceKeys.get(r2.getImpl())) != null) {
                            ResourcesImpl impl2 = findOrCreateResourcesImplForKeyLocked(key);
                            if (impl2 == null) {
                                throw new Resources.NotFoundException(
                                        "failed to redirect ResourcesImpl");
                            }
                            r2.setImpl(impl2);
                        }
                    }
                }
                return;
            }
        }
    }

    private void redirectAllResourcesToNewImplLocked(
            ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceKeys) {
        cleanupReferences(this.mAllResourceReferences, this.mAllResourceReferencesQueue);
        int resourcesCount = this.mAllResourceReferences.size();
        for (int i = 0; i < resourcesCount; i++) {
            WeakReference<Resources> ref = this.mAllResourceReferences.get(i);
            Resources r = ref != null ? ref.get() : null;
            if (r != null) {
                ResourcesKey key = updatedResourceKeys.get(r.getImpl());
                if (key != null) {
                    ResourcesImpl impl = findOrCreateResourcesImplForKeyLocked(key);
                    if (impl == null) {
                        throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
                    }
                    r.setImpl(impl);
                } else if (r.getImpl() != null) {
                    ResourcesImpl oldImpl = r.getImpl();
                    AssetManager oldAssets = oldImpl.getAssets();
                    if (oldAssets != AssetManager.getSystem() && oldAssets.isUpToDate()) {
                        ResourcesImpl newImpl =
                                new ResourcesImpl(
                                        oldAssets,
                                        oldImpl.getMetrics(),
                                        oldImpl.getConfiguration(),
                                        oldImpl.getDisplayAdjustments());
                        r.setImpl(newImpl);
                    } else {
                        Slog.w(
                                TAG,
                                "Skip appending shared library asset paths for the Resource as its"
                                        + " assets are not up to date.");
                    }
                }
            }
        }
    }

    public LocaleConfig getLocaleConfig() {
        return this.mLocaleConfig;
    }

    public void setLocaleConfig(LocaleConfig localeConfig) {
        if (localeConfig != null
                && localeConfig.getSupportedLocales() != null
                && !localeConfig.getSupportedLocales().isEmpty()) {
            this.mLocaleConfig = localeConfig;
        }
    }

    private void adjustConfigForDexDisplayIfNeeded(
            Configuration outConfig, int displayId, DisplayAdjustments displayAdjustments) {
        if (displayId != 2) {
            return;
        }
        Display display = getAdjustedDisplay(displayId, displayAdjustments);
        if (display != null) {
            if (outConfig.semDesktopModeEnabled != 1) {
                outConfig.semDesktopModeEnabled = 1;
            }
            if ((outConfig.uiMode & 2) == 0) {
                outConfig.uiMode = 2 | (outConfig.uiMode & (-16));
            }
            if (outConfig.fontScale != MultiWindowCoreState.DEX_FONT_SCALE) {
                outConfig.fontScale = MultiWindowCoreState.DEX_FONT_SCALE;
            }
        }
    }

    private Display getAdjustedDisplay(int displayId, DisplayAdjustments daj) {
        synchronized (this.mLock) {
            DisplayManagerGlobal dm = DisplayManagerGlobal.getInstance();
            if (dm == null) {
                return null;
            }
            return dm.getCompatibleDisplay(displayId, daj);
        }
    }

    private boolean shouldApplyDisplayMetricsForDex(ResourcesKey key) {
        return key.mOriginDisplayId == 2;
    }

    private class UpdateHandler implements Resources.UpdateCallbacks {
        private UpdateHandler() {}

        @Override // android.content.res.Resources.UpdateCallbacks
        public void onLoadersChanged(Resources resources, List<ResourcesLoader> newLoader) {
            synchronized (ResourcesManager.this.mLock) {
                ResourcesKey oldKey =
                        ResourcesManager.this.findKeyForResourceImplLocked(resources.getImpl());
                if (oldKey == null) {
                    throw new IllegalArgumentException(
                            "Cannot modify resource loaders of ResourcesImpl not registered with"
                                    + " ResourcesManager");
                }
                ResourcesKey newKey =
                        new ResourcesKey(
                                oldKey.mResDir,
                                oldKey.mSplitResDirs,
                                oldKey.mOverlayPaths,
                                oldKey.mLibDirs,
                                oldKey.mDisplayId,
                                oldKey.mOverrideConfiguration,
                                oldKey.mCompatInfo,
                                (ResourcesLoader[]) newLoader.toArray(new ResourcesLoader[0]));
                ResourcesImpl impl =
                        ResourcesManager.this.findOrCreateResourcesImplForKeyLocked(newKey);
                resources.setImpl(impl);
            }
        }

        @Override // android.content.res.loader.ResourcesLoader.UpdateCallbacks
        public void onLoaderUpdated(ResourcesLoader loader) {
            synchronized (ResourcesManager.this.mLock) {
                ArrayMap<ResourcesImpl, ResourcesKey> updatedResourceImplKeys = new ArrayMap<>();
                for (int i = ResourcesManager.this.mResourceImpls.size() - 1; i >= 0; i--) {
                    ResourcesKey key = (ResourcesKey) ResourcesManager.this.mResourceImpls.keyAt(i);
                    WeakReference<ResourcesImpl> impl =
                            (WeakReference) ResourcesManager.this.mResourceImpls.valueAt(i);
                    if (impl != null
                            && !impl.refersTo(null)
                            && ArrayUtils.contains(key.mLoaders, loader)) {
                        ResourcesManager.this.mResourceImpls.remove(key);
                        updatedResourceImplKeys.put(impl.get(), key);
                    }
                }
                ResourcesManager.this.redirectResourcesToNewImplLocked(updatedResourceImplKeys);
            }
        }
    }

    public static class SharedLibraryAssets {
        private final ResourcesKey mResourcesKey;

        private SharedLibraryAssets(ApplicationInfo appInfo) {
            PathCollector collector = new PathCollector(null);
            PathCollector.appendNewPath(
                    appInfo.sourceDir, collector.libsSet, collector.orderedLibs);
            PathCollector.appendAllNewPaths(
                    appInfo.splitSourceDirs, collector.libsSet, collector.orderedLibs);
            PathCollector.appendAllNewPaths(
                    appInfo.sharedLibraryFiles, collector.libsSet, collector.orderedLibs);
            PathCollector.appendAllNewPaths(
                    appInfo.resourceDirs, collector.overlaysSet, collector.orderedOverlays);
            PathCollector.appendAllNewPaths(
                    appInfo.overlayPaths, collector.overlaysSet, collector.orderedOverlays);
            this.mResourcesKey = collector.collectedKey();
        }

        public ResourcesKey getResourcesKey() {
            return this.mResourcesKey;
        }
    }

    public void registerAllResourcesReference(Resources resources) {
        if (android.content.res.Flags.registerResourcePaths()) {
            synchronized (this.mLock) {
                if (this.mAllResourceReferencesCount > 1000) {
                    this.mAllResourceReferencesCount = 0;
                    cleanupReferences(
                            this.mAllResourceReferences, this.mAllResourceReferencesQueue);
                } else {
                    this.mAllResourceReferencesCount++;
                }
                this.mAllResourceReferences.add(
                        new WeakReference<>(resources, this.mAllResourceReferencesQueue));
            }
        }
    }

    public static boolean isOriginDisplayId(int displayId) {
        if (displayId == 2) {
            return true;
        }
        return false;
    }

    private int shouldApplyOriginDisplayId(
            boolean creatingDisplayContext,
            boolean creatingOriginConfigContext,
            Integer overrideDisplayId) {
        if (creatingOriginConfigContext
                || ((creatingDisplayContext
                                && overrideDisplayId != null
                                && overrideDisplayId.intValue() == 2)
                        || (this.mResConfiguration.isDexMode()
                                && this.mResConfiguration.dexMode == 2))) {
            return 2;
        }
        return 0;
    }
}
