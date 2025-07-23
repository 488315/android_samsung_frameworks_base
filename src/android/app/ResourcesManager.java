package android.app;

import android.app.ResourcesManager;
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
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public class ResourcesManager {
    private static final String COLOR_THEME_OVERLAY_PREFIX = "/data/resource-cache/";
    private static final boolean DEBUG = false;
    private static final Predicate<String> IS_UPDATABLE_OVERLAY = new Predicate() { // from class: android.app.ResourcesManager$$ExternalSyntheticLambda1
        @Override // java.util.function.Predicate
        public final boolean test(Object obj) {
            return ResourcesManager.lambda$static$0((String) obj);
        }
    };
    public static final String RESOURCE_CACHE_DIR = "/data/resource-cache/";
    private static final String SAMSUNG_THEME_OVERLAY_PREFIX = "/data/overlays/";
    static final String TAG = "ResourcesManager";
    private static volatile ResourcesManager sResourcesManager;
    private ArrayList<Pair<String[], ApplicationInfo>> mPendingAppInfoUpdates;
    private CompatibilityInfo mResCompatibilityInfo;
    private final Object mLock = new Object();
    private final Configuration mResConfiguration = new Configuration();
    private int mResDisplayId = 0;
    private final ArrayMap<ResourcesKey, WeakReference<ResourcesImpl>> mResourceImpls = new ArrayMap<>();
    private final ArrayList<WeakReference<Resources>> mResourceReferences = new ArrayList<>();
    private final ReferenceQueue<Resources> mResourcesReferencesQueue = new ReferenceQueue<>();
    private final ArrayList<WeakReference<Resources>> mAllResourceReferences = new ArrayList<>();
    private final ReferenceQueue<Resources> mAllResourceReferencesQueue = new ReferenceQueue<>();
    private LocaleConfig mLocaleConfig = new LocaleConfig(LocaleList.getEmptyLocaleList());
    private final ArrayMap<String, SharedLibraryAssets> mSharedLibAssetsMap = new ArrayMap<>();
    private final ArrayMap<ApkKey, WeakReference<ApkAssets>> mCachedApkAssets = new ArrayMap<>();
    private final WeakHashMap<IBinder, ActivityResources> mActivityResourceReferences = new WeakHashMap<>();
    private final UpdateHandler mUpdateCallbacks = new UpdateHandler();
    private final ArraySet<String> mApplicationOwnedApks = new ArraySet<>();

    private void applyAllPendingAppInfoUpdates$ravenwood() {
    }

    public static boolean isOriginDisplayId(int i) {
        return false;
    }

    private int shouldApplyOriginDisplayId(boolean z, boolean z2, Integer num) {
        return 0;
    }

    static /* synthetic */ boolean lambda$static$0(String str) {
        return (str.startsWith("/data/resource-cache/") || str.startsWith(SAMSUNG_THEME_OVERLAY_PREFIX)) ? false : true;
    }

    public ResourcesKey filterOverlayPaths(ResourcesKey resourcesKey) {
        return new ResourcesKey(resourcesKey.mResDir, resourcesKey.mSplitResDirs, (String[]) Arrays.stream(resourcesKey.mOverlayPaths).filter(IS_UPDATABLE_OVERLAY).toList().toArray(new String[0]), resourcesKey.mLibDirs, resourcesKey.mDisplayId, resourcesKey.mOverrideConfiguration, resourcesKey.mCompatInfo, resourcesKey.mLoaders);
    }

    public ArrayMap<String, SharedLibraryAssets> getRegisteredResourcePaths() {
        return this.mSharedLibAssetsMap;
    }

    public void registerResourcePaths(String str, ApplicationInfo applicationInfo) {
        if (android.content.res.Flags.registerResourcePaths()) {
            Application application = ActivityThread.currentActivityThread().getApplication();
            SharedLibraryAssets sharedLibraryAssets = new SharedLibraryAssets(applicationInfo, application != null ? application.getApplicationInfo() : null);
            synchronized (this.mLock) {
                if (this.mSharedLibAssetsMap.containsKey(str)) {
                    Slog.v(TAG, "Package resources' paths for uniqueId: " + str + " has already been registered, this is a no-op.");
                    return;
                }
                this.mSharedLibAssetsMap.put(str, sharedLibraryAssets);
                appendLibAssetsLocked(sharedLibraryAssets);
                Slog.v(TAG, "The following library key has been added: " + sharedLibraryAssets.getResourcesKey());
            }
        }
    }

    public Pair<AssetManager, Integer> updateResourceImplAssetsWithRegisteredLibs(AssetManager assetManager, boolean z) {
        if (!android.content.res.Flags.registerResourcePaths()) {
            return new Pair<>(assetManager, 0);
        }
        synchronized (this.mLock) {
            int size = this.mSharedLibAssetsMap.size();
            if (size != 0 && assetManager != AssetManager.getSystem()) {
                PathCollector pathCollector = new PathCollector(resourcesKeyFromAssets(assetManager));
                for (int i = 0; i < size; i++) {
                    ResourcesKey resourcesKey = this.mSharedLibAssetsMap.valueAt(i).getResourcesKey();
                    if (resourcesKey.mOverlayPaths != null && resourcesKey.mOverlayPaths.length > 0) {
                        pathCollector.appendKey(filterOverlayPaths(resourcesKey));
                    } else {
                        pathCollector.appendKey(resourcesKey);
                    }
                }
                if (pathCollector.isSameAsOriginal()) {
                    return new Pair<>(assetManager, Integer.valueOf(size));
                }
                if (z) {
                    assetManager.addPresetApkKeys(extractApkKeys(pathCollector.collectedKey()));
                    return new Pair<>(assetManager, Integer.valueOf(size));
                }
                final AssetManager.Builder noInit = new AssetManager.Builder().setNoInit();
                for (ApkAssets apkAssets : assetManager.getApkAssets()) {
                    if (!apkAssets.isSystem() && !apkAssets.isForLoader() && !apkAssets.isOverlay() && !apkAssets.isSharedLib()) {
                        noInit.addApkAssets(apkAssets);
                    }
                }
                Iterator<ApkKey> it = extractApkKeys(pathCollector.collectedKey()).iterator();
                while (it.hasNext()) {
                    ApkKey next = it.next();
                    try {
                        noInit.addApkAssets(loadApkAssets(next));
                    } catch (IOException e) {
                        Log.e(TAG, "Couldn't load assets for key " + next, e);
                    }
                }
                List<ResourcesLoader> loaders = assetManager.getLoaders();
                Objects.requireNonNull(noInit);
                loaders.forEach(new Consumer() { // from class: android.app.ResourcesManager$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        AssetManager.Builder.this.addLoader((ResourcesLoader) obj);
                    }
                });
                return new Pair<>(noInit.build(), Integer.valueOf(size));
            }
            return new Pair<>(assetManager, Integer.valueOf(size));
        }
    }

    public static class ApkKey {
        public final boolean overlay;
        public final String path;
        public final boolean sharedLib;

        public ApkKey(String str, boolean z, boolean z2) {
            this.path = str;
            this.sharedLib = z;
            this.overlay = z2;
        }

        public int hashCode() {
            return ((((this.path.hashCode() + 31) * 31) + Boolean.hashCode(this.sharedLib)) * 31) + Boolean.hashCode(this.overlay);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof ApkKey)) {
                return false;
            }
            ApkKey apkKey = (ApkKey) obj;
            return this.path.equals(apkKey.path) && this.sharedLib == apkKey.sharedLib && this.overlay == apkKey.overlay;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("ApkKey[");
            sb.append(this.sharedLib ? NativeLibraryHelper.LIB_DIR_NAME : "app");
            sb.append(this.overlay ? ", overlay" : "");
            sb.append(": ");
            sb.append(this.path);
            sb.append(NavigationBarInflaterView.SIZE_MOD_END);
            return sb.toString();
        }
    }

    protected class ApkAssetsSupplier {
        final ArrayMap<ApkKey, ApkAssets> mLocalCache = new ArrayMap<>();

        protected ApkAssetsSupplier() {
        }

        ApkAssets load(ApkKey apkKey) throws IOException {
            ApkAssets apkAssets = this.mLocalCache.get(apkKey);
            if (apkAssets != null) {
                return apkAssets;
            }
            ApkAssets loadApkAssets = ResourcesManager.this.loadApkAssets(apkKey);
            this.mLocalCache.put(apkKey, loadApkAssets);
            return loadApkAssets;
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
            int i = 0;
            for (int i2 = 0; i2 < this.activityResources.size(); i2++) {
                WeakReference<Resources> weakReference = this.activityResources.get(i2).resources;
                if (weakReference != null && weakReference.get() != null) {
                    i++;
                }
            }
            return i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class ActivityResource {
        public final Configuration overrideConfig;
        public Integer overrideDisplayId;
        public WeakReference<Resources> resources;

        private ActivityResource() {
            this.overrideConfig = new Configuration();
        }
    }

    public static ResourcesManager setInstance(ResourcesManager resourcesManager) {
        ResourcesManager resourcesManager2;
        synchronized (ResourcesManager.class) {
            resourcesManager2 = sResourcesManager;
            sResourcesManager = resourcesManager;
        }
        return resourcesManager2;
    }

    public static ResourcesManager getInstance() {
        ResourcesManager resourcesManager;
        ResourcesManager resourcesManager2 = sResourcesManager;
        if (resourcesManager2 != null) {
            return resourcesManager2;
        }
        synchronized (ResourcesManager.class) {
            resourcesManager = sResourcesManager;
            if (resourcesManager == null) {
                resourcesManager = new ResourcesManager();
                sResourcesManager = resourcesManager;
            }
        }
        return resourcesManager;
    }

    public void invalidatePath(String str) {
        ResourcesImpl resourcesImpl;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            for (int size = this.mResourceImpls.size() - 1; size >= 0; size--) {
                if (this.mResourceImpls.keyAt(size).isPathReferenced(str) && (resourcesImpl = this.mResourceImpls.removeAt(size).get()) != null) {
                    arrayList.add(resourcesImpl);
                }
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            ((ResourcesImpl) arrayList.get(i)).flushLayoutCache();
        }
        ArrayList arrayList2 = new ArrayList();
        synchronized (this.mCachedApkAssets) {
            for (int size2 = this.mCachedApkAssets.size() - 1; size2 >= 0; size2--) {
                if (this.mCachedApkAssets.keyAt(size2).path.equals(str)) {
                    WeakReference<ApkAssets> removeAt = this.mCachedApkAssets.removeAt(size2);
                    ApkAssets apkAssets = removeAt != null ? removeAt.get() : null;
                    if (apkAssets != null) {
                        arrayList2.add(apkAssets);
                    }
                }
            }
        }
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            ((ApkAssets) arrayList2.get(i2)).close();
        }
        Log.i(TAG, "Invalidated " + arrayList.size() + " asset managers that referenced " + str);
    }

    public Configuration getConfiguration() {
        return this.mResConfiguration;
    }

    public DisplayMetrics getDisplayMetrics() {
        return getDisplayMetrics(this.mResDisplayId, DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS);
    }

    public DisplayMetrics getDisplayMetrics(int i, DisplayAdjustments displayAdjustments) {
        DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        DisplayInfo displayInfo = displayManagerGlobal != null ? displayManagerGlobal.getDisplayInfo(i) : null;
        if (displayInfo != null) {
            Configuration configuration = displayAdjustments.getConfiguration();
            CompatibilityInfo compatibilityInfo = displayAdjustments.getCompatibilityInfo();
            if (this.mResDisplayId == i && Configuration.EMPTY.equals(configuration)) {
                configuration = this.mResConfiguration;
            }
            displayInfo.getAppMetrics(displayMetrics, compatibilityInfo, configuration);
            return displayMetrics;
        }
        displayMetrics.setToDefaults();
        return displayMetrics;
    }

    private DisplayMetrics getDisplayMetrics(Configuration configuration) {
        DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        DisplayInfo displayInfo = displayManagerGlobal != null ? displayManagerGlobal.getDisplayInfo(this.mResDisplayId) : null;
        if (displayInfo != null) {
            displayInfo.getAppMetrics(displayMetrics, DisplayAdjustments.DEFAULT_DISPLAY_ADJUSTMENTS.getCompatibilityInfo(), configuration);
            return displayMetrics;
        }
        displayMetrics.setToDefaults();
        return displayMetrics;
    }

    private static void applyDisplayMetricsToConfiguration(DisplayMetrics displayMetrics, Configuration configuration) {
        applyDisplayMetricsToConfiguration(displayMetrics, configuration, 0);
    }

    private static void applyDisplayMetricsToConfiguration(DisplayMetrics displayMetrics, Configuration configuration, int i) {
        configuration.touchscreen = 1;
        configuration.densityDpi = displayMetrics.densityDpi;
        configuration.screenWidthDp = (int) ((displayMetrics.widthPixels / displayMetrics.density) + 0.5f);
        configuration.screenHeightDp = (int) ((displayMetrics.heightPixels / displayMetrics.density) + 0.5f);
        int resetScreenLayout = Configuration.resetScreenLayout(configuration.screenLayout);
        if (displayMetrics.widthPixels > displayMetrics.heightPixels) {
            configuration.orientation = 2;
            configuration.screenLayout = Configuration.reduceScreenLayout(resetScreenLayout, configuration.screenWidthDp, configuration.screenHeightDp);
        } else {
            configuration.orientation = 1;
            configuration.screenLayout = Configuration.reduceScreenLayout(resetScreenLayout, configuration.screenHeightDp, configuration.screenWidthDp);
        }
        configuration.smallestScreenWidthDp = Math.min(configuration.screenWidthDp, configuration.screenHeightDp);
        configuration.compatScreenWidthDp = configuration.screenWidthDp;
        configuration.compatScreenHeightDp = configuration.screenHeightDp;
        configuration.compatSmallestScreenWidthDp = configuration.smallestScreenWidthDp;
    }

    public boolean applyCompatConfiguration(int i, Configuration configuration) {
        synchronized (this.mLock) {
            CompatibilityInfo compatibilityInfo = this.mResCompatibilityInfo;
            if (compatibilityInfo == null || compatibilityInfo.supportsScreen()) {
                return false;
            }
            this.mResCompatibilityInfo.applyToConfiguration(i, configuration);
            return true;
        }
    }

    public Display getAdjustedDisplay(int i, Resources resources) {
        DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
        if (displayManagerGlobal == null) {
            return null;
        }
        return displayManagerGlobal.getCompatibleDisplay(i, resources);
    }

    public void initializeApplicationPaths(String str, String[] strArr) {
        synchronized (this.mLock) {
            if (this.mApplicationOwnedApks.isEmpty()) {
                addApplicationPathsLocked(str, strArr);
            }
        }
    }

    private void addApplicationPathsLocked(String str, String[] strArr) {
        this.mApplicationOwnedApks.add(str);
        if (strArr != null) {
            this.mApplicationOwnedApks.addAll(Arrays.asList(strArr));
        }
    }

    private static String overlayPathToIdmapPath(String str) {
        return "/data/resource-cache/" + str.substring(1).replace('/', '@') + "@idmap";
    }

    public ApkAssets loadApkAssets(ApkKey apkKey) throws IOException {
        WeakReference<ApkAssets> weakReference;
        ApkAssets loadFromPath;
        ApkAssets apkAssets;
        synchronized (this.mCachedApkAssets) {
            weakReference = this.mCachedApkAssets.get(apkKey);
        }
        if (weakReference != null && (apkAssets = weakReference.get()) != null && apkAssets.isUpToDate()) {
            return apkAssets;
        }
        int i = apkKey.sharedLib ? 2 : 0;
        if (this.mApplicationOwnedApks.contains(apkKey.path)) {
            i |= 16;
        }
        if (apkKey.overlay) {
            loadFromPath = ApkAssets.loadOverlayFromPath(overlayPathToIdmapPath(apkKey.path), i);
        } else {
            loadFromPath = ApkAssets.loadFromPath(apkKey.path, i);
        }
        synchronized (this.mCachedApkAssets) {
            this.mCachedApkAssets.put(apkKey, new WeakReference<>(loadFromPath));
        }
        return loadFromPath;
    }

    private static ArrayList<ApkKey> extractApkKeys(ResourcesKey resourcesKey) {
        ArrayList<ApkKey> arrayList = new ArrayList<>();
        if (resourcesKey.mResDir != null) {
            arrayList.add(new ApkKey(resourcesKey.mResDir, false, false));
        }
        if (resourcesKey.mSplitResDirs != null) {
            for (String str : resourcesKey.mSplitResDirs) {
                arrayList.add(new ApkKey(str, false, false));
            }
        }
        if (resourcesKey.mLibDirs != null) {
            for (String str2 : resourcesKey.mLibDirs) {
                if (str2.endsWith(".apk")) {
                    arrayList.add(new ApkKey(str2, true, false));
                }
            }
        }
        if (resourcesKey.mOverlayPaths != null) {
            for (String str3 : resourcesKey.mOverlayPaths) {
                arrayList.add(new ApkKey(str3, false, true));
            }
        }
        return arrayList;
    }

    private ResourcesKey resourcesKeyFromAssets(AssetManager assetManager) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (ApkAssets apkAssets : assetManager.getApkAssets()) {
            if (!apkAssets.isSystem() && !apkAssets.isForLoader()) {
                if (apkAssets.isOverlay()) {
                    arrayList2.add(apkAssets.getAssetPath());
                } else if (apkAssets.isSharedLib()) {
                    arrayList.add(apkAssets.getAssetPath());
                }
            }
        }
        return new ResourcesKey(null, null, (String[]) arrayList2.toArray(new String[0]), (String[]) arrayList.toArray(new String[0]), 0, null, null);
    }

    protected AssetManager createAssetManager(ResourcesKey resourcesKey) {
        return createAssetManager(resourcesKey, null);
    }

    protected AssetManager createAssetManager(ResourcesKey resourcesKey, ApkAssetsSupplier apkAssetsSupplier) {
        ApkAssets load;
        AssetManager.Builder noInit = new AssetManager.Builder().setNoInit();
        ArrayList<ApkKey> extractApkKeys = extractApkKeys(resourcesKey);
        int size = extractApkKeys.size();
        for (int i = 0; i < size; i++) {
            ApkKey apkKey = extractApkKeys.get(i);
            if (apkAssetsSupplier != null) {
                try {
                    load = apkAssetsSupplier.load(apkKey);
                } catch (IOException e) {
                    if (apkKey.overlay) {
                        Log.w(TAG, String.format("failed to add overlay path '%s'", apkKey.path), e);
                        if (resourcesKey.mInvalidOverlayPaths == null) {
                            resourcesKey.mInvalidOverlayPaths = new ArrayList();
                        }
                        resourcesKey.mInvalidOverlayPaths.add(apkKey.path);
                    } else if (apkKey.sharedLib) {
                        Log.w(TAG, String.format("asset path '%s' does not exist or contains no resources", apkKey.path), e);
                    } else {
                        Log.e(TAG, String.format("failed to add asset path '%s'", apkKey.path), e);
                        return null;
                    }
                }
            } else {
                load = loadApkAssets(apkKey);
            }
            noInit.addApkAssets(load);
        }
        if (resourcesKey.mLoaders != null) {
            for (ResourcesLoader resourcesLoader : resourcesKey.mLoaders) {
                noInit.addLoader(resourcesLoader);
            }
        }
        return noInit.build();
    }

    private static <T> int countLiveReferences(Collection<WeakReference<T>> collection) {
        Iterator<WeakReference<T>> it = collection.iterator();
        int i = 0;
        while (it.hasNext()) {
            WeakReference<T> next = it.next();
            if ((next != null ? next.get() : null) != null) {
                i++;
            }
        }
        return i;
    }

    public void dump(String str, PrintWriter printWriter) {
        int countLiveReferences;
        int countLiveReferences2;
        int countLiveReferences3;
        synchronized (this.mLock) {
            countLiveReferences = countLiveReferences(this.mResourceReferences);
            Iterator<ActivityResources> it = this.mActivityResourceReferences.values().iterator();
            while (it.hasNext()) {
                countLiveReferences += it.next().countLiveReferences();
            }
            countLiveReferences2 = countLiveReferences(this.mResourceImpls.values());
        }
        synchronized (this.mCachedApkAssets) {
            countLiveReferences3 = countLiveReferences(this.mCachedApkAssets.values());
        }
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        for (int i = 0; i < str.length() / 2; i++) {
            indentingPrintWriter.increaseIndent();
        }
        indentingPrintWriter.println("ResourcesManager:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("total apks: ");
        indentingPrintWriter.println(countLiveReferences3);
        indentingPrintWriter.print("resources: ");
        indentingPrintWriter.println(countLiveReferences);
        indentingPrintWriter.print("resource impls: ");
        indentingPrintWriter.println(countLiveReferences2);
    }

    private Configuration generateConfig(ResourcesKey resourcesKey) {
        return generateConfig(resourcesKey, null, null);
    }

    private Configuration generateConfig(ResourcesKey resourcesKey, DisplayMetrics displayMetrics, DisplayAdjustments displayAdjustments) {
        if (resourcesKey.hasOverrideConfiguration()) {
            Configuration configuration = new Configuration(getConfiguration());
            configuration.updateFrom(resourcesKey.mOverrideConfiguration);
            if (isInExternalDesktopDisplay(resourcesKey.mDisplayId) && configuration.fontScale != MultiWindowCoreState.FONT_SCALE_FOR_EXTERNAL_DESKTOP) {
                configuration.fontScale = MultiWindowCoreState.FONT_SCALE_FOR_EXTERNAL_DESKTOP;
            }
            CompatSandbox.resetCompatSandBoxValuesIfNeeded(configuration, resourcesKey.mOverrideConfiguration);
            return configuration;
        }
        return getConfiguration();
    }

    private int generateDisplayId(ResourcesKey resourcesKey) {
        return resourcesKey.mDisplayId != -1 ? resourcesKey.mDisplayId : this.mResDisplayId;
    }

    private ResourcesImpl createResourcesImpl(ResourcesKey resourcesKey, ApkAssetsSupplier apkAssetsSupplier) {
        AssetManager createAssetManager = createAssetManager(resourcesKey, apkAssetsSupplier);
        if (createAssetManager == null) {
            return null;
        }
        DisplayAdjustments displayAdjustments = new DisplayAdjustments(resourcesKey.mOverrideConfiguration);
        displayAdjustments.setCompatibilityInfo(resourcesKey.mCompatInfo);
        return new ResourcesImpl(createAssetManager, getDisplayMetrics(generateDisplayId(resourcesKey), displayAdjustments), generateConfig(resourcesKey), displayAdjustments, true);
    }

    private ResourcesImpl findResourcesImplForKeyLocked(ResourcesKey resourcesKey) {
        WeakReference<ResourcesImpl> weakReference = this.mResourceImpls.get(resourcesKey);
        ResourcesImpl resourcesImpl = weakReference != null ? weakReference.get() : null;
        if (resourcesImpl == null || !resourcesImpl.getAssets().isUpToDate()) {
            return null;
        }
        return resourcesImpl;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ResourcesImpl findOrCreateResourcesImplForKeyLocked(ResourcesKey resourcesKey) {
        return findOrCreateResourcesImplForKeyLocked(resourcesKey, null);
    }

    private ResourcesImpl findOrCreateResourcesImplForKeyLocked(ResourcesKey resourcesKey, ApkAssetsSupplier apkAssetsSupplier) {
        ResourcesImpl findResourcesImplForKeyLocked = findResourcesImplForKeyLocked(resourcesKey);
        if (findResourcesImplForKeyLocked != null && findResourcesImplForKeyLocked.getAppliedSharedLibsHash() == this.mSharedLibAssetsMap.size()) {
            return findResourcesImplForKeyLocked;
        }
        ResourcesImpl createResourcesImpl = createResourcesImpl(resourcesKey, apkAssetsSupplier);
        if (createResourcesImpl != null) {
            WeakReference<ResourcesImpl> weakReference = this.mResourceImpls.get(resourcesKey);
            ResourcesImpl resourcesImpl = weakReference != null ? weakReference.get() : null;
            if (resourcesImpl != null) {
                resourcesKey = cleanKeyAndRedirectResourcesImplLocked(resourcesKey, resourcesImpl, createResourcesImpl);
            }
            this.mResourceImpls.put(resourcesKey, new WeakReference<>(createResourcesImpl));
        }
        return createResourcesImpl;
    }

    private ResourcesKey cleanKeyAndRedirectResourcesImplLocked(ResourcesKey resourcesKey, ResourcesImpl resourcesImpl, ResourcesImpl resourcesImpl2) {
        ArrayList arrayList = resourcesKey.mOverlayPaths != null ? new ArrayList(Arrays.asList(resourcesKey.mOverlayPaths)) : new ArrayList();
        if (resourcesKey.mInvalidOverlayPaths != null) {
            Iterator<String> it = resourcesKey.mInvalidOverlayPaths.iterator();
            while (it.hasNext()) {
                arrayList.remove(it.next());
            }
        }
        ResourcesKey resourcesKey2 = new ResourcesKey(resourcesKey.mResDir, resourcesKey.mSplitResDirs, arrayList.isEmpty() ? null : (String[]) arrayList.toArray(new String[0]), resourcesKey.mLibDirs, resourcesKey.mDisplayId, resourcesKey.mOverrideConfiguration, resourcesKey.mCompatInfo, resourcesKey.mLoaders, resourcesKey.mOriginDisplayId);
        ArrayMap arrayMap = new ArrayMap();
        arrayMap.put(resourcesImpl, resourcesKey2);
        int size = this.mResourceReferences.size();
        for (int i = 0; i < size; i++) {
            WeakReference<Resources> weakReference = this.mResourceReferences.get(i);
            Resources resources = weakReference != null ? weakReference.get() : null;
            if (resources != null && ((ResourcesKey) arrayMap.get(resources.getImpl())) != null) {
                resources.setImpl(resourcesImpl2);
            }
        }
        for (ActivityResources activityResources : this.mActivityResourceReferences.values()) {
            int size2 = activityResources.activityResources.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ActivityResource activityResource = activityResources.activityResources.get(i2);
                Resources resources2 = activityResource != null ? activityResource.resources.get() : null;
                if (resources2 != null && ((ResourcesKey) arrayMap.get(resources2.getImpl())) != null) {
                    resources2.setImpl(resourcesImpl2);
                }
            }
        }
        this.mResourceImpls.remove(resourcesKey);
        return resourcesKey2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ResourcesKey findKeyForResourceImplLocked(ResourcesImpl resourcesImpl) {
        int size = this.mResourceImpls.size();
        for (int i = 0; i < size; i++) {
            WeakReference<ResourcesImpl> valueAt = this.mResourceImpls.valueAt(i);
            if (valueAt != null && valueAt.refersTo(resourcesImpl)) {
                return this.mResourceImpls.keyAt(i);
            }
        }
        return null;
    }

    public boolean isSameResourcesOverrideConfig(IBinder iBinder, Configuration configuration) {
        ActivityResources activityResources;
        synchronized (this.mLock) {
            if (iBinder != null) {
                try {
                    activityResources = this.mActivityResourceReferences.get(iBinder);
                } finally {
                }
            } else {
                activityResources = null;
            }
            boolean z = true;
            if (activityResources == null) {
                if (configuration != null) {
                    z = false;
                }
                return z;
            }
            if (!Objects.equals(activityResources.overrideConfig, configuration) && (configuration == null || activityResources.overrideConfig == null || configuration.diffPublicOnly(activityResources.overrideConfig) != 0)) {
                z = false;
            }
            return z;
        }
    }

    private ActivityResources getOrCreateActivityResourcesStructLocked(IBinder iBinder) {
        ActivityResources activityResources = this.mActivityResourceReferences.get(iBinder);
        if (activityResources != null) {
            return activityResources;
        }
        ActivityResources activityResources2 = new ActivityResources();
        this.mActivityResourceReferences.put(iBinder, activityResources2);
        return activityResources2;
    }

    private Resources findResourcesForActivityLocked(IBinder iBinder, ResourcesKey resourcesKey, ClassLoader classLoader) {
        ActivityResources orCreateActivityResourcesStructLocked = getOrCreateActivityResourcesStructLocked(iBinder);
        int size = orCreateActivityResourcesStructLocked.activityResources.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                return null;
            }
            Resources resources = orCreateActivityResourcesStructLocked.activityResources.get(i).resources.get();
            ResourcesKey findKeyForResourceImplLocked = resources != null ? findKeyForResourceImplLocked(resources.getImpl()) : null;
            if (findKeyForResourceImplLocked != null && Objects.equals(resources.getClassLoader(), classLoader) && Objects.equals(findKeyForResourceImplLocked, resourcesKey)) {
                return resources;
            }
            i++;
        }
    }

    private Resources createResourcesForActivityLocked(IBinder iBinder, Configuration configuration, Integer num, ClassLoader classLoader, ResourcesImpl resourcesImpl, CompatibilityInfo compatibilityInfo) {
        ActivityResources orCreateActivityResourcesStructLocked = getOrCreateActivityResourcesStructLocked(iBinder);
        cleanupReferences(orCreateActivityResourcesStructLocked.activityResources, orCreateActivityResourcesStructLocked.activityResourcesQueue, new Function() { // from class: android.app.ResourcesManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                WeakReference weakReference;
                weakReference = ((ResourcesManager.ActivityResource) obj).resources;
                return weakReference;
            }
        });
        Resources compatResources = compatibilityInfo.needsCompatResources() ? new CompatResources(classLoader) : new Resources(classLoader);
        compatResources.setImpl(resourcesImpl);
        compatResources.setCallbacks(this.mUpdateCallbacks);
        ActivityResource activityResource = new ActivityResource();
        activityResource.resources = new WeakReference<>(compatResources, orCreateActivityResourcesStructLocked.activityResourcesQueue);
        activityResource.overrideConfig.setTo(configuration);
        activityResource.overrideDisplayId = num;
        orCreateActivityResourcesStructLocked.activityResources.add(activityResource);
        return compatResources;
    }

    private Resources createResourcesLocked(ClassLoader classLoader, ResourcesImpl resourcesImpl, CompatibilityInfo compatibilityInfo) {
        cleanupReferences(this.mResourceReferences, this.mResourcesReferencesQueue);
        Resources compatResources = compatibilityInfo.needsCompatResources() ? new CompatResources(classLoader) : new Resources(classLoader);
        compatResources.setImpl(resourcesImpl);
        compatResources.setCallbacks(this.mUpdateCallbacks);
        this.mResourceReferences.add(new WeakReference<>(compatResources, this.mResourcesReferencesQueue));
        return compatResources;
    }

    public Resources createBaseTokenResources(IBinder iBinder, String str, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, int i, Configuration configuration, CompatibilityInfo compatibilityInfo, ClassLoader classLoader, List<ResourcesLoader> list) {
        return createBaseTokenResources(iBinder, str, strArr, strArr2, strArr3, strArr4, i, configuration, compatibilityInfo, classLoader, list, 0);
    }

    public Resources createBaseTokenResources(IBinder iBinder, String str, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, int i, Configuration configuration, CompatibilityInfo compatibilityInfo, ClassLoader classLoader, List<ResourcesLoader> list, int i2) {
        try {
            Trace.traceBegin(8192L, "ResourcesManager#createBaseActivityResources");
            ResourcesKey resourcesKey = new ResourcesKey(str, strArr, combinedOverlayPaths(strArr2, strArr3), strArr4, i, configuration, compatibilityInfo, list == null ? null : (ResourcesLoader[]) list.toArray(new ResourcesLoader[0]));
            ClassLoader systemClassLoader = classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
            synchronized (this.mLock) {
                getOrCreateActivityResourcesStructLocked(iBinder);
            }
            updateResourcesForActivity(iBinder, configuration, i);
            synchronized (this.mLock) {
                Resources findResourcesForActivityLocked = findResourcesForActivityLocked(iBinder, resourcesKey, systemClassLoader);
                return findResourcesForActivityLocked != null ? findResourcesForActivityLocked : createResourcesForActivity(iBinder, resourcesKey, Configuration.EMPTY, null, systemClassLoader, null);
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    private void rebaseKeyForActivity(IBinder iBinder, ResourcesKey resourcesKey, boolean z) {
        Configuration configuration;
        synchronized (this.mLock) {
            ActivityResources orCreateActivityResourcesStructLocked = getOrCreateActivityResourcesStructLocked(iBinder);
            if (resourcesKey.mDisplayId == -1) {
                resourcesKey.mDisplayId = orCreateActivityResourcesStructLocked.overrideDisplayId;
            }
            if (resourcesKey.hasOverrideConfiguration()) {
                configuration = new Configuration(orCreateActivityResourcesStructLocked.overrideConfig);
                configuration.updateFrom(resourcesKey.mOverrideConfiguration);
            } else {
                configuration = orCreateActivityResourcesStructLocked.overrideConfig;
            }
            if (z && resourcesKey.mOverrideConfiguration.windowConfiguration.getAppBounds() == null) {
                if (!resourcesKey.hasOverrideConfiguration()) {
                    configuration = new Configuration(configuration);
                }
                configuration.windowConfiguration.setAppBounds(null);
            }
            resourcesKey.mOverrideConfiguration.setTo(configuration);
        }
    }

    private void rebaseKeyForDisplay(ResourcesKey resourcesKey, int i) {
        Configuration configuration = new Configuration();
        DisplayAdjustments displayAdjustments = new DisplayAdjustments(resourcesKey.mOverrideConfiguration);
        displayAdjustments.setCompatibilityInfo(resourcesKey.mCompatInfo);
        applyDisplayMetricsToConfiguration(getDisplayMetrics(i, displayAdjustments), configuration);
        if (resourcesKey.hasOverrideConfiguration()) {
            configuration.updateFrom(resourcesKey.mOverrideConfiguration);
        }
        resourcesKey.mOverrideConfiguration.setTo(configuration);
    }

    private static <T> void cleanupReferences(ArrayList<WeakReference<T>> arrayList, ReferenceQueue<T> referenceQueue) {
        cleanupReferences(arrayList, referenceQueue, Function.identity());
    }

    private static <C, T> void cleanupReferences(ArrayList<C> arrayList, ReferenceQueue<T> referenceQueue, final Function<C, WeakReference<T>> function) {
        Reference<? extends T> poll = referenceQueue.poll();
        if (poll == null) {
            return;
        }
        final HashSet hashSet = new HashSet();
        while (poll != null) {
            hashSet.add(poll);
            poll = referenceQueue.poll();
        }
        ArrayUtils.unstableRemoveIf(arrayList, new Predicate() { // from class: android.app.ResourcesManager$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ResourcesManager.lambda$cleanupReferences$2(function, hashSet, obj);
            }
        });
    }

    static /* synthetic */ boolean lambda$cleanupReferences$2(Function function, HashSet hashSet, Object obj) {
        WeakReference weakReference = (WeakReference) function.apply(obj);
        return weakReference == null || hashSet.contains(weakReference);
    }

    private ApkAssetsSupplier createApkAssetsSupplierNotLocked(ResourcesKey resourcesKey) {
        Trace.traceBegin(8192L, "ResourcesManager#createApkAssetsSupplierNotLocked");
        try {
            ApkAssetsSupplier apkAssetsSupplier = new ApkAssetsSupplier();
            ArrayList<ApkKey> extractApkKeys = extractApkKeys(resourcesKey);
            int size = extractApkKeys.size();
            for (int i = 0; i < size; i++) {
                ApkKey apkKey = extractApkKeys.get(i);
                try {
                    apkAssetsSupplier.load(apkKey);
                } catch (IOException e) {
                    Log.w(TAG, String.format("failed to preload asset path '%s'", apkKey.path), e);
                }
            }
            return apkAssetsSupplier;
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    private Resources createResources(ResourcesKey resourcesKey, ClassLoader classLoader, ApkAssetsSupplier apkAssetsSupplier) {
        synchronized (this.mLock) {
            ResourcesImpl findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(resourcesKey, apkAssetsSupplier);
            if (findOrCreateResourcesImplForKeyLocked == null) {
                return null;
            }
            return createResourcesLocked(classLoader, findOrCreateResourcesImplForKeyLocked, resourcesKey.mCompatInfo);
        }
    }

    private Resources createResourcesForActivity(IBinder iBinder, ResourcesKey resourcesKey, Configuration configuration, Integer num, ClassLoader classLoader, ApkAssetsSupplier apkAssetsSupplier) {
        synchronized (this.mLock) {
            ResourcesImpl findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(resourcesKey, apkAssetsSupplier);
            if (findOrCreateResourcesImplForKeyLocked == null) {
                return null;
            }
            return createResourcesForActivityLocked(iBinder, configuration, num, classLoader, findOrCreateResourcesImplForKeyLocked, resourcesKey.mCompatInfo);
        }
    }

    public Resources getResources(IBinder iBinder, String str, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, Integer num, Configuration configuration, CompatibilityInfo compatibilityInfo, ClassLoader classLoader, List<ResourcesLoader> list) {
        return getResources(iBinder, str, strArr, strArr2, strArr3, strArr4, num, configuration, compatibilityInfo, classLoader, list, false, false);
    }

    public Resources getResources(IBinder iBinder, String str, String[] strArr, String[] strArr2, String[] strArr3, String[] strArr4, Integer num, Configuration configuration, CompatibilityInfo compatibilityInfo, ClassLoader classLoader, List<ResourcesLoader> list, boolean z, boolean z2) {
        Resources createResources;
        try {
            Trace.traceBegin(8192L, "ResourcesManager#getResources");
            ResourcesKey resourcesKey = new ResourcesKey(str, strArr, combinedOverlayPaths(strArr2, strArr3), strArr4, num != null ? num.intValue() : -1, configuration, compatibilityInfo, list == null ? null : (ResourcesLoader[]) list.toArray(new ResourcesLoader[0]));
            ClassLoader systemClassLoader = classLoader != null ? classLoader : ClassLoader.getSystemClassLoader();
            ApkAssetsSupplier createApkAssetsSupplierNotLocked = createApkAssetsSupplierNotLocked(resourcesKey);
            if (num != null) {
                rebaseKeyForDisplay(resourcesKey, num.intValue());
            }
            if (iBinder != null) {
                boolean z3 = false;
                Configuration configuration2 = new Configuration(resourcesKey.mOverrideConfiguration);
                if (num != null) {
                    z3 = true;
                }
                rebaseKeyForActivity(iBinder, resourcesKey, z3);
                createResources = createResourcesForActivity(iBinder, resourcesKey, configuration2, num, systemClassLoader, createApkAssetsSupplierNotLocked);
            } else {
                createResources = createResources(resourcesKey, systemClassLoader, createApkAssetsSupplierNotLocked);
            }
            return createResources;
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    public void updateResourcesForActivity(IBinder iBinder, Configuration configuration, int i) {
        ResourcesKey rebaseActivityOverrideConfig;
        ResourcesImpl findOrCreateResourcesImplForKeyLocked;
        try {
            Trace.traceBegin(8192L, "ResourcesManager#updateResourcesForActivity");
            if (i == -1) {
                throw new IllegalArgumentException("displayId can not be INVALID_DISPLAY");
            }
            synchronized (this.mLock) {
                ActivityResources orCreateActivityResourcesStructLocked = getOrCreateActivityResourcesStructLocked(iBinder);
                boolean z = orCreateActivityResourcesStructLocked.overrideDisplayId != i;
                if (!Objects.equals(orCreateActivityResourcesStructLocked.overrideConfig, configuration) || z) {
                    new Configuration(orCreateActivityResourcesStructLocked.overrideConfig);
                    if (configuration != null) {
                        orCreateActivityResourcesStructLocked.overrideConfig.setTo(configuration);
                    } else {
                        orCreateActivityResourcesStructLocked.overrideConfig.unset();
                    }
                    orCreateActivityResourcesStructLocked.overrideDisplayId = i;
                    applyAllPendingAppInfoUpdates();
                    int size = orCreateActivityResourcesStructLocked.activityResources.size();
                    for (int i2 = 0; i2 < size; i2++) {
                        ActivityResource activityResource = orCreateActivityResourcesStructLocked.activityResources.get(i2);
                        Resources resources = activityResource.resources.get();
                        if (resources != null && (rebaseActivityOverrideConfig = rebaseActivityOverrideConfig(activityResource, configuration, i)) != null && (findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(rebaseActivityOverrideConfig)) != null && findOrCreateResourcesImplForKeyLocked != resources.getImpl()) {
                            resources.setImpl(findOrCreateResourcesImplForKeyLocked);
                        }
                    }
                }
            }
        } finally {
            Trace.traceEnd(8192L);
        }
    }

    private ResourcesKey rebaseActivityOverrideConfig(ActivityResource activityResource, Configuration configuration, int i) {
        Resources resources = activityResource.resources.get();
        if (resources == null) {
            return null;
        }
        ResourcesKey findKeyForResourceImplLocked = findKeyForResourceImplLocked(resources.getImpl());
        if (findKeyForResourceImplLocked == null) {
            Slog.e(TAG, "can't find ResourcesKey for resources impl=" + resources.getImpl());
            return null;
        }
        Configuration configuration2 = new Configuration();
        if (configuration != null) {
            configuration2.setTo(configuration);
        }
        Integer num = activityResource.overrideDisplayId;
        if (num != null) {
            DisplayAdjustments displayAdjustments = new DisplayAdjustments(configuration2);
            displayAdjustments.getConfiguration().setTo(activityResource.overrideConfig);
            displayAdjustments.setCompatibilityInfo(findKeyForResourceImplLocked.mCompatInfo);
            applyDisplayMetricsToConfiguration(getDisplayMetrics(num.intValue(), displayAdjustments), configuration2);
        }
        if (!activityResource.overrideConfig.equals(Configuration.EMPTY)) {
            configuration2.updateFrom(activityResource.overrideConfig);
        }
        if (activityResource.overrideDisplayId != null && activityResource.overrideConfig.windowConfiguration.getAppBounds() == null) {
            configuration2.windowConfiguration.setAppBounds(null);
        }
        if (num != null) {
            i = num.intValue();
        }
        return new ResourcesKey(findKeyForResourceImplLocked.mResDir, findKeyForResourceImplLocked.mSplitResDirs, findKeyForResourceImplLocked.mOverlayPaths, findKeyForResourceImplLocked.mLibDirs, i, configuration2, findKeyForResourceImplLocked.mCompatInfo, findKeyForResourceImplLocked.mLoaders);
    }

    public void appendPendingAppInfoUpdate(String[] strArr, ApplicationInfo applicationInfo) {
        synchronized (this.mLock) {
            if (this.mPendingAppInfoUpdates == null) {
                this.mPendingAppInfoUpdates = new ArrayList<>();
            }
            for (int size = this.mPendingAppInfoUpdates.size() - 1; size >= 0; size--) {
                if (ArrayUtils.containsAll(strArr, this.mPendingAppInfoUpdates.get(size).first)) {
                    this.mPendingAppInfoUpdates.remove(size);
                }
            }
            this.mPendingAppInfoUpdates.add(new Pair<>(strArr, applicationInfo));
        }
    }

    public final void applyAllPendingAppInfoUpdates() {
        synchronized (this.mLock) {
            ArrayList<Pair<String[], ApplicationInfo>> arrayList = this.mPendingAppInfoUpdates;
            if (arrayList != null) {
                int size = arrayList.size();
                for (int i = 0; i < size; i++) {
                    Pair<String[], ApplicationInfo> pair = this.mPendingAppInfoUpdates.get(i);
                    applyNewResourceDirsLocked(pair.first, pair.second);
                }
                this.mPendingAppInfoUpdates = null;
            }
        }
    }

    public final boolean applyConfigurationToResources(Configuration configuration, CompatibilityInfo compatibilityInfo) {
        boolean z;
        ResourcesManager resourcesManager;
        Configuration configuration2;
        CompatibilityInfo compatibilityInfo2;
        CompatibilityInfo compatibilityInfo3;
        synchronized (this.mLock) {
            try {
                Trace.traceBegin(8192L, "ResourcesManager#applyConfigurationToResources");
                if (this.mResConfiguration.isOtherSeqNewer(configuration) || compatibilityInfo != null) {
                    int updateFrom = this.mResConfiguration.updateFrom(configuration);
                    if (compatibilityInfo != null && ((compatibilityInfo3 = this.mResCompatibilityInfo) == null || !compatibilityInfo3.equals(compatibilityInfo))) {
                        updateFrom |= compatibilityInfo.getCompatibilityChangesForConfig(this.mResCompatibilityInfo);
                        this.mResCompatibilityInfo = compatibilityInfo;
                    }
                    if ((Integer.MIN_VALUE & updateFrom) != 0) {
                        applyAllPendingAppInfoUpdates();
                    }
                    Resources.updateSystemConfiguration(configuration, getDisplayMetrics(configuration), compatibilityInfo);
                    ApplicationPackageManager.configurationChanged();
                    Configuration configuration3 = new Configuration();
                    int size = this.mResourceImpls.size() - 1;
                    while (size >= 0) {
                        ResourcesKey keyAt = this.mResourceImpls.keyAt(size);
                        WeakReference<ResourcesImpl> valueAt = this.mResourceImpls.valueAt(size);
                        ResourcesImpl resourcesImpl = valueAt != null ? valueAt.get() : null;
                        if (resourcesImpl != null) {
                            resourcesManager = this;
                            configuration2 = configuration;
                            compatibilityInfo2 = compatibilityInfo;
                            resourcesManager.applyConfigurationToResourcesLocked(configuration2, compatibilityInfo2, configuration3, keyAt, resourcesImpl);
                        } else {
                            resourcesManager = this;
                            configuration2 = configuration;
                            compatibilityInfo2 = compatibilityInfo;
                            resourcesManager.mResourceImpls.removeAt(size);
                        }
                        size--;
                        this = resourcesManager;
                        configuration = configuration2;
                        compatibilityInfo = compatibilityInfo2;
                    }
                    z = updateFrom != 0;
                    Trace.traceEnd(8192L);
                }
            } finally {
                Trace.traceEnd(8192L);
            }
        }
        return z;
    }

    private void applyConfigurationToResourcesLocked(Configuration configuration, CompatibilityInfo compatibilityInfo, Configuration configuration2, ResourcesKey resourcesKey, ResourcesImpl resourcesImpl) {
        configuration2.setTo(configuration);
        if (resourcesKey.hasOverrideConfiguration()) {
            configuration2.updateFrom(resourcesKey.mOverrideConfiguration);
        }
        DisplayAdjustments displayAdjustments = resourcesImpl.getDisplayAdjustments();
        if (compatibilityInfo != null) {
            DisplayAdjustments displayAdjustments2 = new DisplayAdjustments(displayAdjustments);
            displayAdjustments2.setCompatibilityInfo(compatibilityInfo);
            displayAdjustments = displayAdjustments2;
        }
        displayAdjustments.setConfiguration(configuration2);
        DisplayMetrics displayMetrics = getDisplayMetrics(generateDisplayId(resourcesKey), displayAdjustments);
        if (isInExternalDesktopDisplay(resourcesKey.mDisplayId) && configuration2.fontScale != MultiWindowCoreState.FONT_SCALE_FOR_EXTERNAL_DESKTOP) {
            configuration2.fontScale = MultiWindowCoreState.FONT_SCALE_FOR_EXTERNAL_DESKTOP;
        }
        resourcesImpl.updateConfiguration(configuration2, displayMetrics, compatibilityInfo);
    }

    public void appendLibAssetForMainAssetPath(String str, String str2) {
        appendLibAssetsForMainAssetPath(str, new String[]{str2});
    }

    public void appendLibAssetsForMainAssetPath(String str, String[] strArr) {
        String[] strArr2 = strArr;
        synchronized (this.mLock) {
            ArrayMap<ResourcesImpl, ResourcesKey> arrayMap = new ArrayMap<>();
            int size = this.mResourceImpls.size();
            int i = 0;
            while (i < size) {
                ResourcesKey keyAt = this.mResourceImpls.keyAt(i);
                WeakReference<ResourcesImpl> valueAt = this.mResourceImpls.valueAt(i);
                ResourcesImpl resourcesImpl = valueAt != null ? valueAt.get() : null;
                if (resourcesImpl != null && Objects.equals(keyAt.mResDir, str)) {
                    String[] strArr3 = keyAt.mLibDirs;
                    for (String str2 : strArr2) {
                        strArr3 = (String[]) ArrayUtils.appendElement(String.class, strArr3, str2);
                    }
                    if (!Arrays.equals(strArr3, keyAt.mLibDirs)) {
                        arrayMap.put(resourcesImpl, new ResourcesKey(keyAt.mResDir, keyAt.mSplitResDirs, keyAt.mOverlayPaths, strArr3, keyAt.mDisplayId, keyAt.mOverrideConfiguration, keyAt.mCompatInfo, keyAt.mLoaders));
                    }
                }
                i++;
                strArr2 = strArr;
            }
            redirectResourcesToNewImplLocked(arrayMap);
        }
    }

    private static class PathCollector {
        public final ResourcesKey originalKey;
        public final ArrayList<String> orderedLibs = new ArrayList<>();
        public final ArraySet<String> libsSet = new ArraySet<>();
        public final ArrayList<String> orderedOverlays = new ArrayList<>();
        public final ArraySet<String> overlaysSet = new ArraySet<>();

        static void appendNewPath(String str, ArraySet<String> arraySet, ArrayList<String> arrayList) {
            if (arraySet.add(str)) {
                arrayList.add(str);
            }
        }

        static void appendAllNewPaths(String[] strArr, ArraySet<String> arraySet, ArrayList<String> arrayList) {
            if (strArr == null) {
                return;
            }
            for (String str : strArr) {
                appendNewPath(str, arraySet, arrayList);
            }
        }

        PathCollector(ResourcesKey resourcesKey) {
            this.originalKey = resourcesKey;
            if (resourcesKey != null) {
                appendKey(resourcesKey);
            }
        }

        public void appendKey(ResourcesKey resourcesKey) {
            appendAllNewPaths(resourcesKey.mLibDirs, this.libsSet, this.orderedLibs);
            appendAllNewPaths(resourcesKey.mOverlayPaths, this.overlaysSet, this.orderedOverlays);
        }

        boolean isSameAsOriginal() {
            ResourcesKey resourcesKey = this.originalKey;
            return resourcesKey == null ? this.orderedLibs.isEmpty() && this.orderedOverlays.isEmpty() : ((resourcesKey.mLibDirs == null && this.orderedLibs.isEmpty()) || (this.originalKey.mLibDirs != null && this.originalKey.mLibDirs.length == this.orderedLibs.size())) && ((this.originalKey.mOverlayPaths == null && this.orderedOverlays.isEmpty()) || (this.originalKey.mOverlayPaths != null && this.originalKey.mOverlayPaths.length == this.orderedOverlays.size()));
        }

        ResourcesKey collectedKey() {
            ResourcesKey resourcesKey = this.originalKey;
            String str = resourcesKey == null ? null : resourcesKey.mResDir;
            ResourcesKey resourcesKey2 = this.originalKey;
            String[] strArr = resourcesKey2 == null ? null : resourcesKey2.mSplitResDirs;
            String[] strArr2 = (String[]) this.orderedOverlays.toArray(new String[0]);
            String[] strArr3 = (String[]) this.orderedLibs.toArray(new String[0]);
            ResourcesKey resourcesKey3 = this.originalKey;
            int i = resourcesKey3 != null ? resourcesKey3.mDisplayId : 0;
            ResourcesKey resourcesKey4 = this.originalKey;
            Configuration configuration = resourcesKey4 == null ? null : resourcesKey4.mOverrideConfiguration;
            ResourcesKey resourcesKey5 = this.originalKey;
            CompatibilityInfo compatibilityInfo = resourcesKey5 == null ? null : resourcesKey5.mCompatInfo;
            ResourcesKey resourcesKey6 = this.originalKey;
            return new ResourcesKey(str, strArr, strArr2, strArr3, i, configuration, compatibilityInfo, resourcesKey6 != null ? resourcesKey6.mLoaders : null);
        }
    }

    private ResourcesKey createNewResourceKeyIfNeeded(ResourcesKey resourcesKey, ResourcesKey resourcesKey2) {
        PathCollector pathCollector = new PathCollector(resourcesKey);
        if (resourcesKey2.mOverlayPaths != null && resourcesKey2.mOverlayPaths.length > 0) {
            pathCollector.appendKey(filterOverlayPaths(resourcesKey2));
        } else {
            pathCollector.appendKey(resourcesKey2);
        }
        if (pathCollector.isSameAsOriginal()) {
            return null;
        }
        return pathCollector.collectedKey();
    }

    private void appendLibAssetsLocked(SharedLibraryAssets sharedLibraryAssets) {
        ArrayMap<ResourcesImpl, ResourcesKey> arrayMap = new ArrayMap<>();
        int size = this.mResourceImpls.size();
        for (int i = 0; i < size; i++) {
            ResourcesKey keyAt = this.mResourceImpls.keyAt(i);
            WeakReference<ResourcesImpl> valueAt = this.mResourceImpls.valueAt(i);
            ResourcesImpl resourcesImpl = valueAt != null ? valueAt.get() : null;
            if (resourcesImpl == null) {
                Slog.w(TAG, "Found a null ResourcesImpl, skipped.");
            } else {
                ResourcesKey createNewResourceKeyIfNeeded = createNewResourceKeyIfNeeded(keyAt, sharedLibraryAssets.getResourcesKey());
                if (createNewResourceKeyIfNeeded != null) {
                    arrayMap.put(resourcesImpl, createNewResourceKeyIfNeeded);
                }
            }
        }
        redirectAllResourcesToNewImplLocked(arrayMap);
    }

    private void applyNewResourceDirsLocked(String[] strArr, ApplicationInfo applicationInfo) {
        long j;
        String[] strArr2;
        long j2 = 8192;
        try {
            Trace.traceBegin(8192L, "ResourcesManager#applyNewResourceDirsLocked");
            String baseCodePath = applicationInfo.getBaseCodePath();
            int myUid = Process.myUid();
            if (applicationInfo.uid == myUid) {
                strArr2 = applicationInfo.splitSourceDirs;
            } else {
                strArr2 = applicationInfo.splitPublicSourceDirs;
            }
            String[] strArr3 = (String[]) ArrayUtils.cloneOrNull(strArr2);
            String[] combinedOverlayPaths = combinedOverlayPaths(applicationInfo.resourceDirs, applicationInfo.overlayPaths);
            if (applicationInfo.uid == myUid) {
                addApplicationPathsLocked(baseCodePath, strArr3);
            }
            ArrayMap<ResourcesImpl, ResourcesKey> arrayMap = new ArrayMap<>();
            int size = this.mResourceImpls.size();
            int i = 0;
            while (i < size) {
                ResourcesKey keyAt = this.mResourceImpls.keyAt(i);
                WeakReference<ResourcesImpl> valueAt = this.mResourceImpls.valueAt(i);
                ResourcesImpl resourcesImpl = valueAt != null ? valueAt.get() : null;
                if (resourcesImpl != null) {
                    if (keyAt.mResDir == null && ActivityThread.isSystem() && !"android".equals(applicationInfo.packageName)) {
                        Log.i(TAG, "skip fill in resDir with other app resource path");
                    } else {
                        if (keyAt.mResDir != null && !keyAt.mResDir.equals(baseCodePath)) {
                            if (ArrayUtils.contains(strArr, keyAt.mResDir)) {
                            }
                        }
                        j = j2;
                        try {
                            arrayMap.put(resourcesImpl, new ResourcesKey(baseCodePath, strArr3, combinedOverlayPaths, keyAt.mLibDirs, keyAt.mDisplayId, keyAt.mOverrideConfiguration, keyAt.mCompatInfo, keyAt.mLoaders));
                            i++;
                            j2 = j;
                        } catch (Throwable th) {
                            th = th;
                            Trace.traceEnd(j);
                            throw th;
                        }
                    }
                }
                j = j2;
                i++;
                j2 = j;
            }
            j = j2;
            redirectResourcesToNewImplLocked(arrayMap);
            Trace.traceEnd(j);
        } catch (Throwable th2) {
            th = th2;
            j = j2;
        }
    }

    private static String[] combinedOverlayPaths(String[] strArr, String[] strArr2) {
        if (strArr == null) {
            return (String[]) ArrayUtils.cloneOrNull(strArr2);
        }
        if (strArr2 == null) {
            return (String[]) ArrayUtils.cloneOrNull(strArr);
        }
        ArrayList arrayList = new ArrayList(strArr2.length + strArr.length);
        for (String str : strArr2) {
            arrayList.add(str);
        }
        for (String str2 : strArr) {
            if (!arrayList.contains(str2)) {
                arrayList.add(str2);
            }
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void redirectResourcesToNewImplLocked(ArrayMap<ResourcesImpl, ResourcesKey> arrayMap) {
        ResourcesKey resourcesKey;
        ResourcesKey resourcesKey2;
        if (arrayMap.isEmpty()) {
            return;
        }
        int size = this.mResourceReferences.size();
        int i = 0;
        while (true) {
            if (i < size) {
                WeakReference<Resources> weakReference = this.mResourceReferences.get(i);
                Resources resources = weakReference != null ? weakReference.get() : null;
                if (resources != null && (resourcesKey2 = arrayMap.get(resources.getImpl())) != null) {
                    ResourcesImpl findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(resourcesKey2);
                    if (findOrCreateResourcesImplForKeyLocked == null) {
                        throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
                    }
                    resources.setImpl(findOrCreateResourcesImplForKeyLocked);
                }
                i++;
            } else {
                for (ActivityResources activityResources : this.mActivityResourceReferences.values()) {
                    int size2 = activityResources.activityResources.size();
                    for (int i2 = 0; i2 < size2; i2++) {
                        ActivityResource activityResource = activityResources.activityResources.get(i2);
                        Resources resources2 = activityResource != null ? activityResource.resources.get() : null;
                        if (resources2 != null && (resourcesKey = arrayMap.get(resources2.getImpl())) != null) {
                            ResourcesImpl findOrCreateResourcesImplForKeyLocked2 = findOrCreateResourcesImplForKeyLocked(resourcesKey);
                            if (findOrCreateResourcesImplForKeyLocked2 == null) {
                                throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
                            }
                            resources2.setImpl(findOrCreateResourcesImplForKeyLocked2);
                        }
                    }
                }
                return;
            }
        }
    }

    private void redirectAllResourcesToNewImplLocked(ArrayMap<ResourcesImpl, ResourcesKey> arrayMap) {
        AssetManager assets;
        cleanupReferences(this.mAllResourceReferences, this.mAllResourceReferencesQueue);
        int size = this.mAllResourceReferences.size();
        for (int i = 0; i < size; i++) {
            WeakReference<Resources> weakReference = this.mAllResourceReferences.get(i);
            Resources resources = weakReference != null ? weakReference.get() : null;
            if (resources != null) {
                ResourcesKey resourcesKey = arrayMap.get(resources.getImpl());
                if (resourcesKey != null) {
                    ResourcesImpl findOrCreateResourcesImplForKeyLocked = findOrCreateResourcesImplForKeyLocked(resourcesKey);
                    if (findOrCreateResourcesImplForKeyLocked == null) {
                        throw new Resources.NotFoundException("failed to redirect ResourcesImpl");
                    }
                    resources.setImpl(findOrCreateResourcesImplForKeyLocked);
                } else {
                    ResourcesImpl impl = resources.getImpl();
                    if (impl != null && (assets = impl.getAssets()) != AssetManager.getSystem()) {
                        if (assets.isUpToDate()) {
                            resources.setImpl(new ResourcesImpl(impl));
                        } else {
                            Slog.w(TAG, "Skip appending shared library asset paths for the Resources as its assets are not up to date.");
                        }
                    }
                }
            }
        }
    }

    public LocaleConfig getLocaleConfig() {
        return this.mLocaleConfig;
    }

    public void setLocaleConfig(LocaleConfig localeConfig) {
        if (localeConfig == null || localeConfig.getSupportedLocales() == null || localeConfig.getSupportedLocales().isEmpty()) {
            return;
        }
        this.mLocaleConfig = localeConfig;
    }

    private void adjustConfigForDexDisplayIfNeeded(Configuration configuration, int i, DisplayAdjustments displayAdjustments) {
        if (i == 2 && getAdjustedDisplay(i, displayAdjustments) != null) {
            if (configuration.semDesktopModeEnabled != 1) {
                configuration.semDesktopModeEnabled = 1;
            }
            if ((configuration.uiMode & 2) == 0) {
                configuration.uiMode = (configuration.uiMode & (-16)) | 2;
            }
        }
    }

    private Display getAdjustedDisplay(int i, DisplayAdjustments displayAdjustments) {
        synchronized (this.mLock) {
            DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
            if (displayManagerGlobal == null) {
                return null;
            }
            return displayManagerGlobal.getCompatibleDisplay(i, displayAdjustments);
        }
    }

    private boolean shouldApplyDisplayMetricsForDex(ResourcesKey resourcesKey) {
        return resourcesKey.mOriginDisplayId == 2;
    }

    private class UpdateHandler implements Resources.UpdateCallbacks {
        private UpdateHandler() {
        }

        @Override // android.content.res.Resources.UpdateCallbacks
        public void onLoadersChanged(Resources resources, List<ResourcesLoader> list) {
            synchronized (ResourcesManager.this.mLock) {
                ResourcesKey findKeyForResourceImplLocked = ResourcesManager.this.findKeyForResourceImplLocked(resources.getImpl());
                if (findKeyForResourceImplLocked == null) {
                    throw new IllegalArgumentException("Cannot modify resource loaders of ResourcesImpl not registered with ResourcesManager");
                }
                resources.setImpl(ResourcesManager.this.findOrCreateResourcesImplForKeyLocked(new ResourcesKey(findKeyForResourceImplLocked.mResDir, findKeyForResourceImplLocked.mSplitResDirs, findKeyForResourceImplLocked.mOverlayPaths, findKeyForResourceImplLocked.mLibDirs, findKeyForResourceImplLocked.mDisplayId, findKeyForResourceImplLocked.mOverrideConfiguration, findKeyForResourceImplLocked.mCompatInfo, (ResourcesLoader[]) list.toArray(new ResourcesLoader[0]))));
            }
        }

        @Override // android.content.res.loader.ResourcesLoader.UpdateCallbacks
        public void onLoaderUpdated(ResourcesLoader resourcesLoader) {
            synchronized (ResourcesManager.this.mLock) {
                ArrayMap arrayMap = new ArrayMap();
                for (int size = ResourcesManager.this.mResourceImpls.size() - 1; size >= 0; size--) {
                    ResourcesKey resourcesKey = (ResourcesKey) ResourcesManager.this.mResourceImpls.keyAt(size);
                    WeakReference weakReference = (WeakReference) ResourcesManager.this.mResourceImpls.valueAt(size);
                    if (weakReference != null && !weakReference.refersTo(null) && ArrayUtils.contains(resourcesKey.mLoaders, resourcesLoader)) {
                        ResourcesManager.this.mResourceImpls.remove(resourcesKey);
                        arrayMap.put((ResourcesImpl) weakReference.get(), resourcesKey);
                    }
                }
                ResourcesManager.this.redirectResourcesToNewImplLocked(arrayMap);
            }
        }
    }

    public static class SharedLibraryAssets {
        private final ResourcesKey mResourcesKey;

        private SharedLibraryAssets(ApplicationInfo applicationInfo, ApplicationInfo applicationInfo2) {
            PathCollector pathCollector = new PathCollector(null);
            if (applicationInfo2 != null && !applicationInfo2.sourceDir.equals(applicationInfo.sourceDir)) {
                pathCollector.libsSet.add(applicationInfo2.sourceDir);
                if (applicationInfo2.splitSourceDirs != null) {
                    pathCollector.libsSet.addAll(Arrays.asList(applicationInfo2.splitSourceDirs));
                }
                if (applicationInfo2.sharedLibraryFiles != null) {
                    pathCollector.libsSet.addAll(Arrays.asList(applicationInfo2.sharedLibraryFiles));
                }
                if (applicationInfo2.resourceDirs != null) {
                    pathCollector.overlaysSet.addAll(Arrays.asList(applicationInfo2.resourceDirs));
                }
                if (applicationInfo2.overlayPaths != null) {
                    pathCollector.overlaysSet.addAll(Arrays.asList(applicationInfo2.overlayPaths));
                }
            }
            PathCollector.appendNewPath(applicationInfo.sourceDir, pathCollector.libsSet, pathCollector.orderedLibs);
            PathCollector.appendAllNewPaths(applicationInfo.splitSourceDirs, pathCollector.libsSet, pathCollector.orderedLibs);
            PathCollector.appendAllNewPaths(applicationInfo.sharedLibraryFiles, pathCollector.libsSet, pathCollector.orderedLibs);
            PathCollector.appendAllNewPaths(applicationInfo.resourceDirs, pathCollector.overlaysSet, pathCollector.orderedOverlays);
            PathCollector.appendAllNewPaths(applicationInfo.overlayPaths, pathCollector.overlaysSet, pathCollector.orderedOverlays);
            this.mResourcesKey = pathCollector.collectedKey();
        }

        public ResourcesKey getResourcesKey() {
            return this.mResourcesKey;
        }
    }

    public void registerAllResourcesReference(Resources resources) {
        if (android.content.res.Flags.registerResourcePaths()) {
            synchronized (this.mLock) {
                cleanupReferences(this.mAllResourceReferences, this.mAllResourceReferencesQueue);
                this.mAllResourceReferences.add(new WeakReference<>(resources, this.mAllResourceReferencesQueue));
            }
        }
    }

    private static boolean isInExternalDesktopDisplay(int i) {
        if (i != -1 && i != 0) {
            DisplayManagerGlobal displayManagerGlobal = DisplayManagerGlobal.getInstance();
            DisplayInfo displayInfo = displayManagerGlobal != null ? displayManagerGlobal.getDisplayInfo(i) : null;
            if (displayInfo != null && (displayInfo.flags & 131072) != 0) {
                return true;
            }
        }
        return false;
    }
}
