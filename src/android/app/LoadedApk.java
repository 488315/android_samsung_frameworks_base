package android.app;

import android.app.IServiceConnection;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.dex.ArtManager;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.GraphicsEnvironment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.Settings;
import android.security.net.config.NetworkSecurityConfigProvider;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.PerfLog;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DisplayAdjustments;
import com.android.internal.R;
import com.android.internal.os.DebugStore;
import com.android.internal.util.ArrayUtils;
import com.samsung.android.rune.CoreRune;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes.dex */
public final class LoadedApk {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean DEBUG = false;
    private static final int DEFAULT_SPEG_COLLECT_TIME_MS = 2000;
    static final String TAG = "LoadedApk";
    private static final String TAG_SPEG = "SPEG";
    private final ActivityThread mActivityThread;
    private AppComponentFactory mAppComponentFactory;
    private String mAppDir;
    private Application mApplication;
    private ApplicationInfo mApplicationInfo;
    private final ClassLoader mBaseClassLoader;
    private ClassLoader mClassLoader;
    private File mCredentialProtectedDataDirFile;
    private String mDataDir;
    private File mDataDirFile;
    private ClassLoader mDefaultClassLoader;
    private File mDeviceProtectedDataDirFile;
    private final DisplayAdjustments mDisplayAdjustments;
    private final boolean mIncludeCode;
    private String[] mLegacyOverlayDirs;
    private String mLibDir;
    private final Object mLock;
    private String[] mOverlayPaths;
    final String mPackageName;
    private final ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> mReceivers;
    private final boolean mRegisterPackage;
    private String mResDir;
    Resources mResources;
    private final boolean mSecurityViolation;
    private final ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> mServices;
    private String[] mSplitAppDirs;
    private String[] mSplitClassLoaderNames;
    private SplitDependencyLoaderImpl mSplitLoader;
    private String[] mSplitNames;
    private String[] mSplitResDirs;
    private final ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> mUnboundServices;
    private final ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> mUnregisteredReceivers;
    private static final boolean DEBUG_STORE_ENABLED = com.android.internal.os.Flags.debugStoreEnabled();
    private static final ArrayMap<String, Application> sApplications = new ArrayMap<>(4);

    Application getApplication() {
        return this.mApplication;
    }

    public LoadedApk(ActivityThread activityThread, ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, ClassLoader classLoader, boolean z, boolean z2, boolean z3) {
        DisplayAdjustments displayAdjustments = new DisplayAdjustments();
        this.mDisplayAdjustments = displayAdjustments;
        this.mReceivers = new ArrayMap<>();
        this.mUnregisteredReceivers = new ArrayMap<>();
        this.mServices = new ArrayMap<>();
        this.mUnboundServices = new ArrayMap<>();
        this.mLock = new Object();
        this.mActivityThread = activityThread;
        setApplicationInfo(applicationInfo);
        this.mPackageName = applicationInfo.packageName;
        this.mBaseClassLoader = classLoader;
        this.mSecurityViolation = z;
        this.mIncludeCode = z2;
        this.mRegisterPackage = z3;
        displayAdjustments.setCompatibilityInfo(compatibilityInfo);
        this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, classLoader);
    }

    private static ApplicationInfo adjustNativeLibraryPaths(ApplicationInfo applicationInfo) {
        if (applicationInfo.primaryCpuAbi != null && applicationInfo.secondaryCpuAbi != null) {
            String strVmInstructionSet = VMRuntime.getRuntime().vmInstructionSet();
            String instructionSet = VMRuntime.getInstructionSet(applicationInfo.secondaryCpuAbi);
            String str = SystemProperties.get("ro.dalvik.vm.isa." + instructionSet);
            if (!str.isEmpty()) {
                instructionSet = str;
            }
            if (strVmInstructionSet.equals(instructionSet)) {
                ApplicationInfo applicationInfo2 = new ApplicationInfo(applicationInfo);
                applicationInfo2.nativeLibraryDir = applicationInfo2.secondaryNativeLibraryDir;
                applicationInfo2.primaryCpuAbi = applicationInfo2.secondaryCpuAbi;
                return applicationInfo2;
            }
        }
        return applicationInfo;
    }

    LoadedApk(ActivityThread activityThread) {
        this.mDisplayAdjustments = new DisplayAdjustments();
        this.mReceivers = new ArrayMap<>();
        this.mUnregisteredReceivers = new ArrayMap<>();
        this.mServices = new ArrayMap<>();
        this.mUnboundServices = new ArrayMap<>();
        this.mLock = new Object();
        this.mActivityThread = activityThread;
        ApplicationInfo applicationInfo = new ApplicationInfo();
        this.mApplicationInfo = applicationInfo;
        applicationInfo.packageName = "android";
        this.mPackageName = "android";
        this.mAppDir = null;
        this.mResDir = null;
        this.mSplitAppDirs = null;
        this.mSplitResDirs = null;
        this.mSplitClassLoaderNames = null;
        this.mLegacyOverlayDirs = null;
        this.mOverlayPaths = null;
        this.mDataDir = null;
        this.mDataDirFile = null;
        this.mDeviceProtectedDataDirFile = null;
        this.mCredentialProtectedDataDirFile = null;
        this.mLibDir = null;
        this.mBaseClassLoader = null;
        this.mSecurityViolation = false;
        this.mIncludeCode = true;
        this.mRegisterPackage = false;
        this.mResources = Resources.getSystem();
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        this.mDefaultClassLoader = systemClassLoader;
        AppComponentFactory appComponentFactoryCreateAppFactory = createAppFactory(this.mApplicationInfo, systemClassLoader);
        this.mAppComponentFactory = appComponentFactoryCreateAppFactory;
        this.mClassLoader = appComponentFactoryCreateAppFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
    }

    void installSystemApplicationInfo(ApplicationInfo applicationInfo, ClassLoader classLoader) {
        this.mApplicationInfo = applicationInfo;
        this.mDefaultClassLoader = classLoader;
        AppComponentFactory appComponentFactoryCreateAppFactory = createAppFactory(applicationInfo, classLoader);
        this.mAppComponentFactory = appComponentFactoryCreateAppFactory;
        this.mClassLoader = appComponentFactoryCreateAppFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
    }

    private AppComponentFactory createAppFactory(ApplicationInfo applicationInfo, ClassLoader classLoader) {
        if (this.mIncludeCode && applicationInfo.appComponentFactory != null && classLoader != null) {
            try {
                return (AppComponentFactory) classLoader.loadClass(applicationInfo.appComponentFactory).newInstance();
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                Slog.e(TAG, "Unable to instantiate appComponentFactory", e);
            }
        }
        return AppComponentFactory.DEFAULT;
    }

    public AppComponentFactory getAppFactory() {
        return this.mAppComponentFactory;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public ApplicationInfo getApplicationInfo() {
        return this.mApplicationInfo;
    }

    public int getTargetSdkVersion() {
        return this.mApplicationInfo.targetSdkVersion;
    }

    public boolean isSecurityViolation() {
        return this.mSecurityViolation;
    }

    public CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    public void setCompatibilityInfo(CompatibilityInfo compatibilityInfo) {
        this.mDisplayAdjustments.setCompatibilityInfo(compatibilityInfo);
    }

    private static String[] getLibrariesFor(String str) {
        try {
            ApplicationInfo applicationInfo = ActivityThread.getPackageManager().getApplicationInfo(str, 1024L, UserHandle.myUserId());
            if (applicationInfo == null) {
                return null;
            }
            return applicationInfo.sharedLibraryFiles;
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void updateApplicationInfo(ApplicationInfo applicationInfo, List<String> list) {
        if (setApplicationInfo(applicationInfo)) {
            ArrayList<String> arrayList = new ArrayList();
            makePaths(this.mActivityThread, applicationInfo, arrayList);
            List<String> arrayList2 = new ArrayList<>(arrayList.size());
            if (list != null) {
                for (String str : arrayList) {
                    String strSubstring = str.substring(str.lastIndexOf(File.separator));
                    Iterator<String> it = list.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            String next = it.next();
                            if (strSubstring.equals(next.substring(next.lastIndexOf(File.separator)))) {
                                break;
                            }
                        } else {
                            arrayList2.add(str);
                            break;
                        }
                    }
                }
            } else {
                arrayList2.addAll(arrayList);
            }
            synchronized (this.mLock) {
                createOrUpdateClassLoaderLocked(arrayList2);
                if (this.mResources != null) {
                    try {
                        String[] splitPaths = getSplitPaths(null);
                        ResourcesManager resourcesManager = ResourcesManager.getInstance();
                        String str2 = this.mResDir;
                        String[] strArr = this.mLegacyOverlayDirs;
                        String[] strArr2 = this.mOverlayPaths;
                        String[] strArr3 = this.mApplicationInfo.sharedLibraryFiles;
                        CompatibilityInfo compatibilityInfo = getCompatibilityInfo();
                        ClassLoader classLoader = getClassLoader();
                        Application application = this.mApplication;
                        this.mResources = resourcesManager.getResources(null, str2, splitPaths, strArr, strArr2, strArr3, null, null, compatibilityInfo, classLoader, application != null ? application.getResources().getLoaders() : null);
                    } catch (PackageManager.NameNotFoundException unused) {
                        throw new AssertionError("null split not found");
                    }
                }
            }
            this.mAppComponentFactory = createAppFactory(applicationInfo, this.mDefaultClassLoader);
        }
    }

    private boolean setApplicationInfo(ApplicationInfo applicationInfo) {
        ApplicationInfo applicationInfo2 = this.mApplicationInfo;
        if (applicationInfo2 != null && applicationInfo2.createTimestamp > applicationInfo.createTimestamp) {
            Slog.w(TAG, "New application info for package " + applicationInfo.packageName + " is out of date with TS " + applicationInfo.createTimestamp + " < the current TS " + this.mApplicationInfo.createTimestamp);
            return false;
        }
        int iMyUid = Process.myUid();
        ApplicationInfo applicationInfoAdjustNativeLibraryPaths = adjustNativeLibraryPaths(applicationInfo);
        this.mApplicationInfo = applicationInfoAdjustNativeLibraryPaths;
        this.mAppDir = applicationInfoAdjustNativeLibraryPaths.sourceDir;
        this.mResDir = applicationInfoAdjustNativeLibraryPaths.uid == iMyUid ? applicationInfoAdjustNativeLibraryPaths.sourceDir : applicationInfoAdjustNativeLibraryPaths.publicSourceDir;
        this.mLegacyOverlayDirs = applicationInfoAdjustNativeLibraryPaths.resourceDirs;
        this.mOverlayPaths = applicationInfoAdjustNativeLibraryPaths.overlayPaths;
        this.mDataDir = applicationInfoAdjustNativeLibraryPaths.dataDir;
        this.mLibDir = applicationInfoAdjustNativeLibraryPaths.nativeLibraryDir;
        this.mDataDirFile = FileUtils.newFileOrNull(applicationInfoAdjustNativeLibraryPaths.dataDir);
        this.mDeviceProtectedDataDirFile = FileUtils.newFileOrNull(applicationInfoAdjustNativeLibraryPaths.deviceProtectedDataDir);
        this.mCredentialProtectedDataDirFile = FileUtils.newFileOrNull(applicationInfoAdjustNativeLibraryPaths.credentialProtectedDataDir);
        this.mSplitNames = applicationInfoAdjustNativeLibraryPaths.splitNames;
        this.mSplitAppDirs = applicationInfoAdjustNativeLibraryPaths.splitSourceDirs;
        this.mSplitResDirs = applicationInfoAdjustNativeLibraryPaths.uid == iMyUid ? applicationInfoAdjustNativeLibraryPaths.splitSourceDirs : applicationInfoAdjustNativeLibraryPaths.splitPublicSourceDirs;
        this.mSplitClassLoaderNames = applicationInfoAdjustNativeLibraryPaths.splitClassLoaderNames;
        if (!applicationInfoAdjustNativeLibraryPaths.requestsIsolatedSplitLoading() || ArrayUtils.isEmpty(this.mSplitNames)) {
            return true;
        }
        this.mSplitLoader = new SplitDependencyLoaderImpl(applicationInfoAdjustNativeLibraryPaths.splitDependencies);
        return true;
    }

    void setSdkSandboxStorage(String str, String str2) {
        int iMyUserId = UserHandle.myUserId();
        this.mDeviceProtectedDataDirFile = Environment.getDataMiscDeSharedSdkSandboxDirectory(str, iMyUserId, str2).getAbsoluteFile();
        this.mCredentialProtectedDataDirFile = Environment.getDataMiscCeSharedSdkSandboxDirectory(str, iMyUserId, str2).getAbsoluteFile();
        if ((this.mApplicationInfo.privateFlags & 32) != 0) {
            this.mDataDirFile = this.mDeviceProtectedDataDirFile;
        } else {
            this.mDataDirFile = this.mCredentialProtectedDataDirFile;
        }
        this.mDataDir = this.mDataDirFile.getAbsolutePath();
    }

    public static void makePaths(ActivityThread activityThread, ApplicationInfo applicationInfo, List<String> list) {
        makePaths(activityThread, false, applicationInfo, list, null);
    }

    private static void appendSharedLibrariesLibPathsIfNeeded(List<SharedLibraryInfo> list, ApplicationInfo applicationInfo, Set<String> set, List<String> list2) {
        if (list == null) {
            return;
        }
        for (SharedLibraryInfo sharedLibraryInfo : list) {
            if (!sharedLibraryInfo.isNative()) {
                List<String> allCodePaths = sharedLibraryInfo.getAllCodePaths();
                set.addAll(allCodePaths);
                Iterator<String> it = allCodePaths.iterator();
                while (it.hasNext()) {
                    appendApkLibPathIfNeeded(it.next(), applicationInfo, list2);
                }
                appendSharedLibrariesLibPathsIfNeeded(sharedLibraryInfo.getDependencies(), applicationInfo, set, list2);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0078  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void makePaths(ActivityThread activityThread, boolean z, ApplicationInfo applicationInfo, List<String> list, List<String> list2) {
        String[] librariesFor;
        String str = applicationInfo.sourceDir;
        String str2 = applicationInfo.nativeLibraryDir;
        list.clear();
        list.add(str);
        if (applicationInfo.splitSourceDirs != null && !applicationInfo.requestsIsolatedSplitLoading()) {
            Collections.addAll(list, applicationInfo.splitSourceDirs);
        }
        if (list2 != null) {
            list2.clear();
        }
        if (activityThread != null) {
            String str3 = activityThread.mInstrumentationPackageName;
            String str4 = activityThread.mInstrumentationAppDir;
            String[] strArr = activityThread.mInstrumentationSplitAppDirs;
            String str5 = activityThread.mInstrumentationLibDir;
            String str6 = activityThread.mInstrumentedAppDir;
            String[] strArr2 = activityThread.mInstrumentedSplitAppDirs;
            String str7 = activityThread.mInstrumentedLibDir;
            if (str.equals(str4) || str.equals(str6)) {
                list.clear();
                list.add(str4);
                if (!str4.equals(str6)) {
                    list.add(str6);
                }
                if (!applicationInfo.requestsIsolatedSplitLoading()) {
                    if (strArr != null) {
                        Collections.addAll(list, strArr);
                    }
                    if (!str4.equals(str6) && strArr2 != null) {
                        Collections.addAll(list, strArr2);
                    }
                }
                if (list2 != null) {
                    list2.add(str5);
                    if (!str5.equals(str7)) {
                        list2.add(str7);
                    }
                }
                librariesFor = !str6.equals(str4) ? getLibrariesFor(str3) : null;
            }
        }
        if (list2 != null) {
            if (list2.isEmpty()) {
                list2.add(str2);
            }
            if (applicationInfo.primaryCpuAbi != null) {
                if (applicationInfo.targetSdkVersion < 24) {
                    list2.add("/system/fake-libs".concat(VMRuntime.is64BitAbi(applicationInfo.primaryCpuAbi) ? "64" : ""));
                }
                Iterator<String> it = list.iterator();
                while (it.hasNext()) {
                    list2.add(it.next() + "!/lib/" + applicationInfo.primaryCpuAbi);
                }
            }
            if (z) {
                list2.add(System.getProperty("java.library.path"));
            }
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        appendSharedLibrariesLibPathsIfNeeded(applicationInfo.sharedLibraryInfos, applicationInfo, linkedHashSet, list2);
        if (applicationInfo.sharedLibraryFiles != null) {
            int i = 0;
            for (String str8 : applicationInfo.sharedLibraryFiles) {
                if (str8.endsWith(".apk") && !linkedHashSet.contains(str8) && !list.contains(str8)) {
                    list.add(i, str8);
                    i++;
                    appendApkLibPathIfNeeded(str8, applicationInfo, list2);
                }
            }
        }
        if (librariesFor != null) {
            for (String str9 : librariesFor) {
                if (!list.contains(str9)) {
                    list.add(0, str9);
                    appendApkLibPathIfNeeded(str9, applicationInfo, list2);
                }
            }
        }
    }

    private static void appendApkLibPathIfNeeded(String str, ApplicationInfo applicationInfo, List<String> list) {
        if (list == null || applicationInfo.primaryCpuAbi == null || !str.endsWith(".apk") || applicationInfo.targetSdkVersion < 26) {
            return;
        }
        list.add(str + "!/lib/" + applicationInfo.primaryCpuAbi);
    }

    private class SplitDependencyLoaderImpl extends SplitDependencyLoader<PackageManager.NameNotFoundException> {
        private final ClassLoader[] mCachedClassLoaders;
        private final String[][] mCachedResourcePaths;

        SplitDependencyLoaderImpl(SparseArray<int[]> sparseArray) {
            super(sparseArray);
            this.mCachedResourcePaths = new String[LoadedApk.this.mSplitNames.length + 1][];
            this.mCachedClassLoaders = new ClassLoader[LoadedApk.this.mSplitNames.length + 1];
        }

        @Override // android.content.pm.split.SplitDependencyLoader
        protected boolean isSplitCached(int i) {
            boolean z;
            synchronized (LoadedApk.this.mLock) {
                z = this.mCachedClassLoaders[i] != null;
            }
            return z;
        }

        @Override // android.content.pm.split.SplitDependencyLoader
        protected void constructSplit(int i, int[] iArr, int i2) throws PackageManager.NameNotFoundException {
            synchronized (LoadedApk.this.mLock) {
                ArrayList arrayList = new ArrayList();
                if (i == 0) {
                    LoadedApk.this.createOrUpdateClassLoaderLocked(null);
                    this.mCachedClassLoaders[0] = LoadedApk.this.mClassLoader;
                    for (int i3 : iArr) {
                        arrayList.add(LoadedApk.this.mSplitResDirs[i3 - 1]);
                    }
                    this.mCachedResourcePaths[0] = (String[]) arrayList.toArray(new String[arrayList.size()]);
                    return;
                }
                ClassLoader[] classLoaderArr = this.mCachedClassLoaders;
                int i4 = i - 1;
                classLoaderArr[i] = ApplicationLoaders.getDefault().getClassLoader(LoadedApk.this.mSplitAppDirs[i4], LoadedApk.this.getTargetSdkVersion(), false, null, null, classLoaderArr[i2], LoadedApk.this.mSplitClassLoaderNames[i4]);
                Collections.addAll(arrayList, this.mCachedResourcePaths[i2]);
                arrayList.add(LoadedApk.this.mSplitResDirs[i4]);
                for (int i5 : iArr) {
                    arrayList.add(LoadedApk.this.mSplitResDirs[i5 - 1]);
                }
                this.mCachedResourcePaths[i] = (String[]) arrayList.toArray(new String[arrayList.size()]);
            }
        }

        private int ensureSplitLoaded(String str) throws PackageManager.NameNotFoundException {
            int i;
            if (str != null) {
                int iBinarySearch = Arrays.binarySearch(LoadedApk.this.mSplitNames, str);
                if (iBinarySearch < 0) {
                    throw new PackageManager.NameNotFoundException("Split name '" + str + "' is not installed");
                }
                i = iBinarySearch + 1;
            } else {
                i = 0;
            }
            loadDependenciesForSplit(i);
            return i;
        }

        ClassLoader getClassLoaderForSplit(String str) throws PackageManager.NameNotFoundException {
            ClassLoader classLoader;
            int iEnsureSplitLoaded = ensureSplitLoaded(str);
            synchronized (LoadedApk.this.mLock) {
                classLoader = this.mCachedClassLoaders[iEnsureSplitLoaded];
            }
            return classLoader;
        }

        String[] getSplitPathsForSplit(String str) throws PackageManager.NameNotFoundException {
            String[] strArr;
            int iEnsureSplitLoaded = ensureSplitLoaded(str);
            synchronized (LoadedApk.this.mLock) {
                strArr = this.mCachedResourcePaths[iEnsureSplitLoaded];
            }
            return strArr;
        }
    }

    ClassLoader getSplitClassLoader(String str) throws PackageManager.NameNotFoundException {
        SplitDependencyLoaderImpl splitDependencyLoaderImpl = this.mSplitLoader;
        if (splitDependencyLoaderImpl == null) {
            return this.mClassLoader;
        }
        return splitDependencyLoaderImpl.getClassLoaderForSplit(str);
    }

    String[] getSplitPaths(String str) throws PackageManager.NameNotFoundException {
        SplitDependencyLoaderImpl splitDependencyLoaderImpl = this.mSplitLoader;
        if (splitDependencyLoaderImpl == null) {
            return this.mSplitResDirs;
        }
        return splitDependencyLoaderImpl.getSplitPathsForSplit(str);
    }

    ClassLoader createSharedLibraryLoader(SharedLibraryInfo sharedLibraryInfo, boolean z, String str, String str2) {
        List<String> allCodePaths = sharedLibraryInfo.getAllCodePaths();
        Pair<List<ClassLoader>, List<ClassLoader>> pairCreateSharedLibrariesLoaders = createSharedLibrariesLoaders(sharedLibraryInfo.getDependencies(), z, str, str2);
        return ApplicationLoaders.getDefault().getSharedLibraryClassLoaderWithSharedLibraries(allCodePaths.size() == 1 ? allCodePaths.get(0) : TextUtils.join(File.pathSeparator, allCodePaths), this.mApplicationInfo.targetSdkVersion, z, str, str2, null, null, pairCreateSharedLibrariesLoaders.first, pairCreateSharedLibrariesLoaders.second);
    }

    private Pair<List<ClassLoader>, List<ClassLoader>> createSharedLibrariesLoaders(List<SharedLibraryInfo> list, boolean z, String str, String str2) {
        if (list == null || list.isEmpty()) {
            return new Pair<>(null, null);
        }
        HashSet hashSet = new HashSet();
        Collections.addAll(hashSet, Resources.getSystem().getStringArray(R.array.config_sharedLibrariesLoadedAfterApp));
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (SharedLibraryInfo sharedLibraryInfo : list) {
            if (!sharedLibraryInfo.isNative() && !sharedLibraryInfo.isSdk()) {
                if (hashSet.contains(sharedLibraryInfo.getName())) {
                    arrayList2.add(createSharedLibraryLoader(sharedLibraryInfo, z, str, str2));
                } else {
                    arrayList.add(createSharedLibraryLoader(sharedLibraryInfo, z, str, str2));
                }
            }
        }
        return new Pair<>(arrayList, arrayList2);
    }

    private StrictMode.ThreadPolicy allowThreadDiskReads() {
        if (this.mActivityThread == null) {
            return null;
        }
        return StrictMode.allowThreadDiskReads();
    }

    private void setThreadPolicy(StrictMode.ThreadPolicy threadPolicy) {
        if (this.mActivityThread == null || threadPolicy == null) {
            return;
        }
        StrictMode.setThreadPolicy(threadPolicy);
    }

    private StrictMode.VmPolicy allowVmViolations() {
        if (this.mActivityThread == null) {
            return null;
        }
        return StrictMode.allowVmViolations();
    }

    private void setVmPolicy(StrictMode.VmPolicy vmPolicy) {
        if (this.mActivityThread == null || vmPolicy == null) {
            return;
        }
        StrictMode.setVmPolicy(vmPolicy);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createOrUpdateClassLoaderLocked(List<String> list) {
        if (this.mPackageName.equals("android")) {
            if (this.mClassLoader != null) {
                return;
            }
            ClassLoader classLoader = this.mBaseClassLoader;
            if (classLoader != null) {
                this.mDefaultClassLoader = classLoader;
            } else {
                this.mDefaultClassLoader = ClassLoader.getSystemClassLoader();
            }
            AppComponentFactory appComponentFactoryCreateAppFactory = createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
            this.mAppComponentFactory = appComponentFactoryCreateAppFactory;
            this.mClassLoader = appComponentFactoryCreateAppFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
            return;
        }
        if (this.mActivityThread != null && !Objects.equals(this.mPackageName, ActivityThread.currentPackageName()) && this.mIncludeCode) {
            try {
                ActivityThread.getPackageManager().notifyPackageUse(this.mPackageName, 6);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
        if (this.mRegisterPackage) {
            try {
                ActivityManager.getService().addPackageDependency(this.mPackageName);
            } catch (RemoteException e2) {
                throw e2.rethrowFromSystemServer();
            }
        }
        ArrayList arrayList = new ArrayList(10);
        ArrayList arrayList2 = new ArrayList(10);
        boolean z = true;
        boolean z2 = false;
        boolean z3 = this.mApplicationInfo.isSystemApp() && !this.mApplicationInfo.isUpdatedSystemApp();
        String property = System.getProperty("java.library.path");
        boolean zContains = property.contains("/vendor/lib");
        if (this.mApplicationInfo.getCodePath() != null && this.mApplicationInfo.isVendor() && !zContains) {
            z3 = false;
        }
        boolean z4 = (this.mApplicationInfo.getCodePath() == null || !this.mApplicationInfo.isProduct()) ? z3 : false;
        makePaths(this.mActivityThread, z4, this.mApplicationInfo, arrayList, arrayList2);
        String str = canAccessDataDir() ? this.mDataDir : "";
        if (z4) {
            str = (str + File.pathSeparator + Paths.get(getAppDir(), new String[0]).getParent().toString()) + File.pathSeparator + property;
        }
        String strJoin = TextUtils.join(File.pathSeparator, arrayList2);
        ActivityThread activityThread = this.mActivityThread;
        if (activityThread != null) {
            String stringCoreSetting = activityThread.getStringCoreSetting(Settings.Global.GPU_DEBUG_APP, "");
            if (!stringCoreSetting.isEmpty() && this.mPackageName.equals(stringCoreSetting)) {
                try {
                    String debugLayerPathsFromSettings = GraphicsEnvironment.getInstance().getDebugLayerPathsFromSettings(this.mActivityThread.getCoreSettings(), ActivityThread.getPackageManager(), this.mPackageName, ActivityThread.getPackageManager().getApplicationInfo(this.mPackageName, 128L, UserHandle.myUserId()));
                    if (debugLayerPathsFromSettings != null) {
                        str = str + File.pathSeparator + debugLayerPathsFromSettings;
                    }
                } catch (RemoteException unused) {
                    Slog.e(ActivityThread.TAG, "RemoteException when fetching debug layer paths for: " + this.mPackageName);
                }
            }
        }
        String str2 = str;
        if (!this.mIncludeCode) {
            if (this.mDefaultClassLoader == null) {
                StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = allowThreadDiskReads();
                this.mDefaultClassLoader = ApplicationLoaders.getDefault().getClassLoader("", this.mApplicationInfo.targetSdkVersion, z4, strJoin, str2, this.mBaseClassLoader, null);
                setThreadPolicy(threadPolicyAllowThreadDiskReads);
                this.mAppComponentFactory = AppComponentFactory.DEFAULT;
            }
            if (this.mClassLoader == null) {
                this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
                return;
            }
            return;
        }
        String strJoin2 = arrayList.size() == 1 ? (String) arrayList.get(0) : TextUtils.join(File.pathSeparator, arrayList);
        if (this.mDefaultClassLoader == null) {
            if (this.mActivityThread != null && !ActivityThread.isSystem()) {
                BaseDexClassLoader.setReporter(DexLoadReporter.getInstance());
            }
            StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads2 = allowThreadDiskReads();
            Pair<List<ClassLoader>, List<ClassLoader>> pairCreateSharedLibrariesLoaders = createSharedLibrariesLoaders(this.mApplicationInfo.sharedLibraryInfos, z4, strJoin, str2);
            ArrayList arrayList3 = new ArrayList();
            if (this.mApplicationInfo.sharedLibraryInfos != null) {
                for (SharedLibraryInfo sharedLibraryInfo : this.mApplicationInfo.sharedLibraryInfos) {
                    if (sharedLibraryInfo.isNative()) {
                        arrayList3.add(sharedLibraryInfo.getName());
                    }
                }
            }
            ClassLoader classLoaderWithSharedLibraries = ApplicationLoaders.getDefault().getClassLoaderWithSharedLibraries(strJoin2, this.mApplicationInfo.targetSdkVersion, z4, strJoin, str2, this.mBaseClassLoader, this.mApplicationInfo.classLoaderName, pairCreateSharedLibrariesLoaders.first, arrayList3, pairCreateSharedLibrariesLoaders.second);
            this.mDefaultClassLoader = classLoaderWithSharedLibraries;
            this.mAppComponentFactory = createAppFactory(this.mApplicationInfo, classLoaderWithSharedLibraries);
            setThreadPolicy(threadPolicyAllowThreadDiskReads2);
            z2 = true;
        }
        if (!arrayList2.isEmpty()) {
            StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads3 = allowThreadDiskReads();
            try {
                ApplicationLoaders.getDefault().addNative(this.mDefaultClassLoader, arrayList2);
            } finally {
                setThreadPolicy(threadPolicyAllowThreadDiskReads3);
            }
        }
        if (list == null || list.size() <= 0) {
            z = z2;
        } else {
            ApplicationLoaders.getDefault().addPath(this.mDefaultClassLoader, TextUtils.join(File.pathSeparator, list));
        }
        if (z && !ActivityThread.isSystem() && this.mActivityThread != null) {
            registerAppInfoToArt();
        }
        if (this.mClassLoader == null) {
            this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
        }
    }

    private boolean canAccessDataDir() {
        if (this.mActivityThread == null) {
            return false;
        }
        if (Objects.equals(this.mPackageName, ActivityThread.currentPackageName())) {
            return true;
        }
        if (this.mDataDir == null) {
            return false;
        }
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = allowThreadDiskReads();
        StrictMode.VmPolicy vmPolicyAllowVmViolations = allowVmViolations();
        try {
            return new File(this.mDataDir).canExecute();
        } finally {
            setThreadPolicy(threadPolicyAllowThreadDiskReads);
            setVmPolicy(vmPolicyAllowVmViolations);
        }
    }

    public ClassLoader getClassLoader() {
        ClassLoader classLoader;
        ClassLoader classLoader2 = this.mClassLoader;
        if (classLoader2 != null) {
            return classLoader2;
        }
        synchronized (this.mLock) {
            if (this.mClassLoader == null) {
                createOrUpdateClassLoaderLocked(null);
            }
            classLoader = this.mClassLoader;
        }
        return classLoader;
    }

    private boolean isSpeg() {
        String str;
        if (!CoreRune.SYSFW_APP_SPEG || (str = this.mApplicationInfo.sourceDir) == null) {
            return false;
        }
        String str2 = str.substring(0, str.lastIndexOf("/")) + "/base.speg" + this.mApplicationInfo.uid;
        StrictMode.ThreadPolicy threadPolicyAllowThreadDiskReads = allowThreadDiskReads();
        try {
            return new File(str2).exists();
        } finally {
            setThreadPolicy(threadPolicyAllowThreadDiskReads);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean waitForCacheImageDump(long j) throws InterruptedException {
        long jCurrentTimeMillis = System.currentTimeMillis();
        File file = new File(this.mApplicationInfo.dataDir + "/cache/oat_primary/" + VMRuntime.getRuntime().vmInstructionSet());
        while (j > System.currentTimeMillis() - jCurrentTimeMillis && (!file.isDirectory() || !file.canRead())) {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException unused) {
            }
        }
        while (true) {
            if (j <= System.currentTimeMillis() - jCurrentTimeMillis) {
                Slog.e("SPEG", "Failed to wait cache in " + file.getAbsolutePath());
                return false;
            }
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                Slog.e("SPEG", "Failed to read cache dir " + file.getAbsolutePath());
                return false;
            }
            for (File file2 : fileArrListFiles) {
                if (!file2.isDirectory() && file2.getName().endsWith(".art")) {
                    Slog.d("SPEG", "Cache " + file2.getAbsolutePath() + ", size " + file2.length());
                    return true;
                }
            }
            try {
                Thread.sleep(10L);
            } catch (InterruptedException unused2) {
            }
        }
    }

    private void registerAppInfoToArt() {
        if (this.mApplicationInfo.uid != Process.myUid()) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if ((this.mApplicationInfo.flags & 4) != 0) {
            arrayList.add(this.mApplicationInfo.sourceDir);
        }
        if (this.mApplicationInfo.splitSourceDirs != null) {
            Collections.addAll(arrayList, this.mApplicationInfo.splitSourceDirs);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        int size = arrayList.size() - 1;
        while (size >= 0) {
            String str = size == 0 ? null : this.mApplicationInfo.splitNames[size - 1];
            VMRuntime.registerAppInfo(this.mPackageName, ArtManager.getCurrentProfilePath(this.mPackageName, UserHandle.myUserId(), str), ArtManager.getReferenceProfilePath(this.mPackageName, UserHandle.myUserId(), str), new String[]{(String) arrayList.get(size)}, ((String) arrayList.get(size)).equals(this.mApplicationInfo.sourceDir) ? 1 : 2);
            size--;
        }
        if (isSpeg()) {
            try {
                System.loadLibrary("speg");
            } catch (UnsatisfiedLinkError e) {
                Log.e("SPEG", "Library not found: " + e);
            }
            if (SystemProperties.getBoolean("com.samsung.speg.cache_mode", true)) {
                new Thread(new Runnable() { // from class: android.app.LoadedApk.1
                    @Override // java.lang.Runnable
                    public void run() throws InterruptedException {
                        try {
                            Thread.sleep(SystemProperties.getInt("com.samsung.speg.collect_time_ms", 2000));
                        } catch (InterruptedException unused) {
                        }
                        Slog.d("SPEG", "Notify startup completed to write cache");
                        VMRuntime.getRuntime().notifyStartupCompleted();
                        if (LoadedApk.this.waitForCacheImageDump(1000L)) {
                            System.exit(0);
                        }
                    }
                }).start();
            }
        }
        DexLoadReporter.getInstance().registerAppDataDir(this.mPackageName, this.mDataDir);
    }

    private void initializeJavaContextClassLoader() {
        ClassLoader warningContextClassLoader;
        ActivityThread.getPackageManager();
        PackageInfo packageInfoAsUserCached = PackageManager.getPackageInfoAsUserCached(this.mPackageName, 268435456L, UserHandle.myUserId());
        if (packageInfoAsUserCached == null) {
            throw new IllegalStateException("Unable to get package info for " + this.mPackageName + "; is package not installed?");
        }
        boolean z = packageInfoAsUserCached.sharedUserId != null;
        boolean z2 = (packageInfoAsUserCached.applicationInfo == null || this.mPackageName.equals(packageInfoAsUserCached.applicationInfo.processName)) ? false : true;
        if (z || z2) {
            warningContextClassLoader = new WarningContextClassLoader();
        } else {
            warningContextClassLoader = this.mClassLoader;
        }
        Thread.currentThread().setContextClassLoader(warningContextClassLoader);
    }

    private static class WarningContextClassLoader extends ClassLoader {
        private static boolean warned = false;

        private WarningContextClassLoader() {
        }

        private void warn(String str) {
            if (warned) {
                return;
            }
            warned = true;
            Thread.currentThread().setContextClassLoader(getParent());
            Slog.w(ActivityThread.TAG, "ClassLoader." + str + ": The class loader returned by Thread.getContextClassLoader() may fail for processes that host multiple applications. You should explicitly specify a context class loader. For example: Thread.setContextClassLoader(getClass().getClassLoader());");
        }

        @Override // java.lang.ClassLoader
        public URL getResource(String str) {
            warn("getResource");
            return getParent().getResource(str);
        }

        @Override // java.lang.ClassLoader
        public Enumeration<URL> getResources(String str) throws IOException {
            warn("getResources");
            return getParent().getResources(str);
        }

        @Override // java.lang.ClassLoader
        public InputStream getResourceAsStream(String str) {
            warn("getResourceAsStream");
            return getParent().getResourceAsStream(str);
        }

        @Override // java.lang.ClassLoader
        public Class<?> loadClass(String str) throws ClassNotFoundException {
            warn("loadClass");
            return getParent().loadClass(str);
        }

        @Override // java.lang.ClassLoader
        public void setClassAssertionStatus(String str, boolean z) {
            warn("setClassAssertionStatus");
            getParent().setClassAssertionStatus(str, z);
        }

        @Override // java.lang.ClassLoader
        public void setPackageAssertionStatus(String str, boolean z) {
            warn("setPackageAssertionStatus");
            getParent().setPackageAssertionStatus(str, z);
        }

        @Override // java.lang.ClassLoader
        public void setDefaultAssertionStatus(boolean z) {
            warn("setDefaultAssertionStatus");
            getParent().setDefaultAssertionStatus(z);
        }

        @Override // java.lang.ClassLoader
        public void clearAssertionStatus() {
            warn("clearAssertionStatus");
            getParent().clearAssertionStatus();
        }
    }

    public String getAppDir() {
        return this.mAppDir;
    }

    public String getLibDir() {
        return this.mLibDir;
    }

    public String getResDir() {
        return this.mResDir;
    }

    public String[] getSplitAppDirs() {
        return this.mSplitAppDirs;
    }

    public String[] getSplitResDirs() {
        return this.mSplitResDirs;
    }

    public String[] getOverlayDirs() {
        return this.mLegacyOverlayDirs;
    }

    public String[] getOverlayPaths() {
        return this.mOverlayPaths;
    }

    public String getDataDir() {
        return this.mDataDir;
    }

    public File getDataDirFile() {
        return this.mDataDirFile;
    }

    public File getDeviceProtectedDataDirFile() {
        return this.mDeviceProtectedDataDirFile;
    }

    public File getCredentialProtectedDataDirFile() {
        return this.mCredentialProtectedDataDirFile;
    }

    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    public Resources getResources() {
        if (this.mResources == null) {
            try {
                String[] splitPaths = getSplitPaths(null);
                if (Process.myUid() == this.mApplicationInfo.uid) {
                    ResourcesManager.getInstance().initializeApplicationPaths(this.mResDir, splitPaths);
                }
                this.mResources = ResourcesManager.getInstance().getResources(null, this.mResDir, splitPaths, this.mLegacyOverlayDirs, this.mOverlayPaths, this.mApplicationInfo.sharedLibraryFiles, null, null, getCompatibilityInfo(), getClassLoader(), null);
            } catch (PackageManager.NameNotFoundException unused) {
                throw new AssertionError("null split not found");
            }
        }
        return this.mResources;
    }

    public Application makeApplication(boolean z, Instrumentation instrumentation) {
        return makeApplicationInner(z, instrumentation, true);
    }

    public Application makeApplicationInner(boolean z, Instrumentation instrumentation) {
        return makeApplicationInner(z, instrumentation, false);
    }

    private Application makeApplicationInner(boolean z, Instrumentation instrumentation, boolean z2) {
        Application application = this.mApplication;
        if (application != null) {
            return application;
        }
        if (Trace.isTagEnabled(64L)) {
            Trace.traceBegin(64L, "makeApplication");
        }
        try {
            ArrayMap<String, Application> arrayMap = sApplications;
            synchronized (arrayMap) {
                Application application2 = arrayMap.get(this.mPackageName);
                if (application2 != null) {
                    if (!"android".equals(this.mPackageName)) {
                        Slog.wtfStack(TAG, "App instance already created for package=" + this.mPackageName + " instance=" + application2);
                    }
                    if (!z2) {
                        this.mApplication = application2;
                        return application2;
                    }
                }
                String customApplicationClassNameForProcess = this.mApplicationInfo.getCustomApplicationClassNameForProcess(Process.myProcessName());
                if (z || customApplicationClassNameForProcess == null) {
                    customApplicationClassNameForProcess = "android.app.Application";
                }
                Application applicationNewApplication = null;
                try {
                    ClassLoader classLoader = getClassLoader();
                    if (!this.mPackageName.equals("android")) {
                        Trace.traceBegin(64L, "initializeJavaContextClassLoader");
                        initializeJavaContextClassLoader();
                        Trace.traceEnd(64L);
                    }
                    SparseArray<String> assignedPackageIdentifiers = getAssets().getAssignedPackageIdentifiers(false, false);
                    int size = assignedPackageIdentifiers.size();
                    for (int i = 0; i < size; i++) {
                        int iKeyAt = assignedPackageIdentifiers.keyAt(i);
                        if (iKeyAt != 1 && iKeyAt != 127) {
                            rewriteRValues(classLoader, assignedPackageIdentifiers.valueAt(i), iKeyAt);
                        }
                    }
                    ContextImpl contextImplCreateAppContext = ContextImpl.createAppContext(this.mActivityThread, this);
                    NetworkSecurityConfigProvider.handleNewApplication(contextImplCreateAppContext);
                    applicationNewApplication = this.mActivityThread.mInstrumentation.newApplication(classLoader, customApplicationClassNameForProcess, contextImplCreateAppContext);
                    contextImplCreateAppContext.setOuterContext(applicationNewApplication);
                } catch (Exception e) {
                    if (!this.mActivityThread.mInstrumentation.onException(applicationNewApplication, e)) {
                        throw new RuntimeException("Unable to instantiate application " + customApplicationClassNameForProcess + " package " + this.mPackageName + ": " + e.toString(), e);
                    }
                }
                this.mActivityThread.addApplication(applicationNewApplication);
                this.mApplication = applicationNewApplication;
                if (!z2) {
                    ArrayMap<String, Application> arrayMap2 = sApplications;
                    synchronized (arrayMap2) {
                        arrayMap2.put(this.mPackageName, applicationNewApplication);
                    }
                }
                if (instrumentation != null) {
                    try {
                        instrumentation.callApplicationOnCreate(applicationNewApplication);
                    } catch (Exception e2) {
                        if (!instrumentation.onException(applicationNewApplication, e2)) {
                            throw new RuntimeException("Unable to create application " + applicationNewApplication.getClass().getName() + ": " + e2.toString(), e2);
                        }
                    }
                }
                return applicationNewApplication;
            }
        } finally {
            Trace.traceEnd(64L);
        }
    }

    private void rewriteRValues(ClassLoader classLoader, String str, int i) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        Throwable e;
        try {
            try {
                try {
                    classLoader.loadClass(str + ".R").getMethod("onResourcesLoaded", Integer.TYPE).invoke(null, Integer.valueOf(i));
                } catch (IllegalAccessException e2) {
                    e = e2;
                    throw new RuntimeException("Failed to rewrite resource references for " + str, e);
                } catch (InvocationTargetException e3) {
                    e = e3.getCause();
                    throw new RuntimeException("Failed to rewrite resource references for " + str, e);
                }
            } catch (NoSuchMethodException unused) {
            }
        } catch (ClassNotFoundException unused2) {
            Log.i(TAG, "No resource references to update in package " + str);
        }
    }

    public void removeContextRegistrations(Context context, String str, String str2) {
        int i;
        boolean zVmRegistrationLeaksEnabled = StrictMode.vmRegistrationLeaksEnabled();
        synchronized (this.mReceivers) {
            ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMapRemove = this.mReceivers.remove(context);
            if (arrayMapRemove != null) {
                for (int i2 = 0; i2 < arrayMapRemove.size(); i2++) {
                    ReceiverDispatcher receiverDispatcherValueAt = arrayMapRemove.valueAt(i2);
                    IntentReceiverLeaked intentReceiverLeaked = new IntentReceiverLeaked(str2 + " " + str + " has leaked IntentReceiver " + receiverDispatcherValueAt.getIntentReceiver() + " that was originally registered here. Are you missing a call to unregisterReceiver()?");
                    intentReceiverLeaked.setStackTrace(receiverDispatcherValueAt.getLocation().getStackTrace());
                    Slog.e(ActivityThread.TAG, intentReceiverLeaked.getMessage(), intentReceiverLeaked);
                    if (zVmRegistrationLeaksEnabled) {
                        StrictMode.onIntentReceiverLeaked(intentReceiverLeaked);
                    }
                    try {
                        ActivityManager.getService().unregisterReceiver(receiverDispatcherValueAt.getIIntentReceiver());
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnregisteredReceivers.remove(context);
        }
        synchronized (this.mServices) {
            ArrayMap<ServiceConnection, ServiceDispatcher> arrayMapRemove2 = this.mServices.remove(context);
            if (arrayMapRemove2 != null) {
                for (i = 0; i < arrayMapRemove2.size(); i++) {
                    ServiceDispatcher serviceDispatcherValueAt = arrayMapRemove2.valueAt(i);
                    ServiceConnectionLeaked serviceConnectionLeaked = new ServiceConnectionLeaked(str2 + " " + str + " has leaked ServiceConnection " + serviceDispatcherValueAt.getServiceConnection() + " that was originally bound here");
                    serviceConnectionLeaked.setStackTrace(serviceDispatcherValueAt.getLocation().getStackTrace());
                    Slog.e(ActivityThread.TAG, serviceConnectionLeaked.getMessage(), serviceConnectionLeaked);
                    if (zVmRegistrationLeaksEnabled) {
                        StrictMode.onServiceConnectionLeaked(serviceConnectionLeaked);
                    }
                    try {
                        ActivityManager.getService().unbindService(serviceDispatcherValueAt.getIServiceConnection());
                        serviceDispatcherValueAt.doForget();
                    } catch (RemoteException e2) {
                        throw e2.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnboundServices.remove(context);
        }
    }

    public IIntentReceiver getReceiverDispatcher(BroadcastReceiver broadcastReceiver, Context context, Handler handler, Instrumentation instrumentation, boolean z) {
        ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap;
        IIntentReceiver iIntentReceiver;
        synchronized (this.mReceivers) {
            ReceiverDispatcher receiverDispatcher = null;
            if (z) {
                try {
                    arrayMap = this.mReceivers.get(context);
                    if (arrayMap != null) {
                        receiverDispatcher = arrayMap.get(broadcastReceiver);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } else {
                arrayMap = null;
            }
            if (receiverDispatcher == null) {
                ReceiverDispatcher receiverDispatcher2 = new ReceiverDispatcher(this.mActivityThread.getApplicationThread(), broadcastReceiver, context, handler, instrumentation, z);
                if (z) {
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap<>();
                        this.mReceivers.put(context, arrayMap);
                    }
                    arrayMap.put(broadcastReceiver, receiverDispatcher2);
                }
                receiverDispatcher = receiverDispatcher2;
            } else {
                receiverDispatcher.validate(context, handler);
            }
            receiverDispatcher.mForgotten = false;
            iIntentReceiver = receiverDispatcher.getIIntentReceiver();
        }
        return iIntentReceiver;
    }

    IIntentReceiver findRegisteredReceiverDispatcher(BroadcastReceiver broadcastReceiver, Context context) {
        synchronized (this.mReceivers) {
            ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap = this.mReceivers.get(context);
            IIntentReceiver iIntentReceiver = null;
            if (arrayMap == null) {
                return null;
            }
            ReceiverDispatcher receiverDispatcher = arrayMap.get(broadcastReceiver);
            if (receiverDispatcher != null) {
                iIntentReceiver = receiverDispatcher.getIIntentReceiver();
            }
            return iIntentReceiver;
        }
    }

    public IIntentReceiver forgetReceiverDispatcher(Context context, BroadcastReceiver broadcastReceiver) {
        ReceiverDispatcher receiverDispatcher;
        ReceiverDispatcher receiverDispatcher2;
        IIntentReceiver iIntentReceiver;
        synchronized (this.mReceivers) {
            ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap = this.mReceivers.get(context);
            if (arrayMap != null && (receiverDispatcher2 = arrayMap.get(broadcastReceiver)) != null) {
                arrayMap.remove(broadcastReceiver);
                if (arrayMap.size() == 0) {
                    this.mReceivers.remove(context);
                }
                if (broadcastReceiver.getDebugUnregister()) {
                    ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap2 = this.mUnregisteredReceivers.get(context);
                    if (arrayMap2 == null) {
                        arrayMap2 = new ArrayMap<>();
                        this.mUnregisteredReceivers.put(context, arrayMap2);
                    }
                    receiverDispatcher2.setUnregisterLocation(new IllegalArgumentException("Originally unregistered here:"));
                    arrayMap2.put(broadcastReceiver, receiverDispatcher2);
                }
                receiverDispatcher2.mForgotten = true;
                iIntentReceiver = receiverDispatcher2.getIIntentReceiver();
            } else {
                ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap3 = this.mUnregisteredReceivers.get(context);
                if (arrayMap3 != null && (receiverDispatcher = arrayMap3.get(broadcastReceiver)) != null) {
                    throw new IllegalArgumentException("Unregistering Receiver " + broadcastReceiver + " that was already unregistered", receiverDispatcher.getUnregisterLocation());
                }
                if (context == null) {
                    throw new IllegalStateException("Unbinding Receiver " + broadcastReceiver + " from Context that is no longer in use: " + context);
                }
                throw new IllegalArgumentException("Receiver not registered: " + broadcastReceiver);
            }
        }
        return iIntentReceiver;
    }

    static final class ReceiverDispatcher {
        final Handler mActivityThread;
        final IApplicationThread mAppThread;
        final Context mContext;
        boolean mForgotten;
        final IIntentReceiver.Stub mIIntentReceiver;
        final Instrumentation mInstrumentation;
        final IntentReceiverLeaked mLocation;
        final BroadcastReceiver mReceiver;
        final boolean mRegistered;
        RuntimeException mUnregisterLocation;

        static final class InnerReceiver extends IIntentReceiver.Stub {
            final IApplicationThread mApplicationThread;
            final WeakReference<ReceiverDispatcher> mDispatcher;
            final ReceiverDispatcher mStrongRef;

            InnerReceiver(IApplicationThread iApplicationThread, ReceiverDispatcher receiverDispatcher, boolean z) {
                this.mApplicationThread = iApplicationThread;
                this.mDispatcher = new WeakReference<>(receiverDispatcher);
                this.mStrongRef = z ? receiverDispatcher : null;
            }

            @Override // android.content.IIntentReceiver
            public void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, int i2) {
                Log.wtf(LoadedApk.TAG, "performReceive() called targeting raw IIntentReceiver for " + intent);
                performReceive(intent, i, str, bundle, z, z2, BroadcastReceiver.PendingResult.guessAssumeDelivered(1, z), i2, -1, null);
            }

            public void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, String str2) {
                ReceiverDispatcher receiverDispatcher;
                if (intent == null) {
                    Log.wtf(LoadedApk.TAG, "Null intent received");
                    receiverDispatcher = null;
                } else {
                    receiverDispatcher = this.mDispatcher.get();
                }
                if (receiverDispatcher != null) {
                    receiverDispatcher.performReceive(intent, i, str, bundle, z, z2, z3, i2, i3, str2);
                    return;
                }
                if (z3) {
                    return;
                }
                IActivityManager service = ActivityManager.getService();
                if (bundle != null) {
                    try {
                        bundle.setAllowFds(false);
                    } catch (RemoteException e) {
                        throw e.rethrowFromSystemServer();
                    }
                }
                service.finishReceiver(this.mApplicationThread.asBinder(), i, str, bundle, false, intent != null ? intent.getFlags() : 0);
            }

            public String toString() {
                ReceiverDispatcher receiverDispatcher = this.mDispatcher.get();
                if (receiverDispatcher != null) {
                    return String.valueOf(receiverDispatcher.getIntentReceiver());
                }
                return "";
            }
        }

        final class Args extends BroadcastReceiver.PendingResult {
            private Intent mCurIntent;
            private boolean mDispatched;
            private long mHandleOnSystemMainOLOGThresMs;
            private boolean mRunCalled;

            public Args(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, String str2) {
                super(i, str, bundle, ReceiverDispatcher.this.mRegistered ? 1 : 2, z, z2, z3, ReceiverDispatcher.this.mAppThread.asBinder(), i2, intent.getFlags(), i3, str2);
                this.mHandleOnSystemMainOLOGThresMs = 100L;
                this.mCurIntent = intent;
            }

            public final Runnable getRunnable() {
                return new Runnable() { // from class: android.app.LoadedApk$ReceiverDispatcher$Args$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$getRunnable$0();
                    }
                };
            }

            /* JADX INFO: Access modifiers changed from: private */
            /* JADX WARN: Removed duplicated region for block: B:40:0x00e4 A[DONT_GENERATE, PHI: r17
              0x00e4: PHI (r17v2 long) = (r17v1 long), (r17v4 long) binds: [B:39:0x00e2, B:26:0x00bc] A[DONT_GENERATE, DONT_INLINE]] */
            /* JADX WARN: Removed duplicated region for block: B:43:0x00ed  */
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public /* synthetic */ void lambda$getRunnable$0() {
                long j;
                BroadcastReceiver broadcastReceiver = ReceiverDispatcher.this.mReceiver;
                IActivityManager service = ActivityManager.getService();
                Intent intent = this.mCurIntent;
                if (intent == null) {
                    StringBuilder sb = new StringBuilder("Null intent being dispatched, mDispatched=");
                    sb.append(this.mDispatched);
                    sb.append(this.mRunCalled ? ", run() has already been called" : "");
                    Log.wtf(LoadedApk.TAG, sb.toString());
                }
                this.mCurIntent = null;
                this.mDispatched = true;
                this.mRunCalled = true;
                if (broadcastReceiver == null || intent == null || ReceiverDispatcher.this.mForgotten) {
                    sendFinished(service);
                    return;
                }
                long jElapsedRealtime = SystemClock.elapsedRealtime();
                if (Trace.isTagEnabled(64L)) {
                    Trace.traceBegin(64L, "broadcastReceiveReg: " + ReceiverDispatcher.this.mReceiver + ", for " + intent.getAction());
                }
                long jRecordBroadcastReceiveReg = LoadedApk.DEBUG_STORE_ENABLED ? DebugStore.recordBroadcastReceiveReg(intent, System.identityHashCode(this)) : -1L;
                try {
                    try {
                        ClassLoader classLoader = ReceiverDispatcher.this.mReceiver.getClass().getClassLoader();
                        intent.setExtrasClassLoader(classLoader);
                        j = 64;
                        try {
                            intent.prepareToEnterProcess(ActivityThread.isProtectedBroadcast(intent), ReceiverDispatcher.this.mContext.getAttributionSource());
                            setExtrasClassLoader(classLoader);
                            broadcastReceiver.setPendingResult(this);
                            broadcastReceiver.onReceive(ReceiverDispatcher.this.mContext, intent);
                        } catch (Exception e) {
                            e = e;
                            sendFinished(service);
                            if (ReceiverDispatcher.this.mInstrumentation == null || !ReceiverDispatcher.this.mInstrumentation.onException(ReceiverDispatcher.this.mReceiver, e)) {
                                Trace.traceEnd(j);
                                throw new RuntimeException("Error receiving broadcast " + intent + " in " + ReceiverDispatcher.this.mReceiver, e);
                            }
                            if (broadcastReceiver.getPendingResult() != null) {
                            }
                            Trace.traceEnd(j);
                            long jElapsedRealtime2 = SystemClock.elapsedRealtime() - jElapsedRealtime;
                            if (Looper.myLooper().isPerfLogEnable()) {
                                return;
                            } else {
                                return;
                            }
                        }
                    } finally {
                        if (LoadedApk.DEBUG_STORE_ENABLED) {
                            DebugStore.recordEventEnd(jRecordBroadcastReceiveReg);
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    j = 64;
                }
                if (LoadedApk.DEBUG_STORE_ENABLED) {
                }
                if (broadcastReceiver.getPendingResult() != null) {
                    finish();
                }
                Trace.traceEnd(j);
                long jElapsedRealtime22 = SystemClock.elapsedRealtime() - jElapsedRealtime;
                try {
                    if (Looper.myLooper().isPerfLogEnable() || jElapsedRealtime22 <= this.mHandleOnSystemMainOLOGThresMs) {
                        return;
                    }
                    PerfLog.d(5, " system_server main thread handled for " + intent.getAction() + " took " + jElapsedRealtime22 + " ms, Receiver = " + ReceiverDispatcher.this.mReceiver);
                    Slog.w(LoadedApk.TAG, "Slow system_server main thread handled for " + intent.getAction() + " took " + jElapsedRealtime22 + " ms, Receiver = " + ReceiverDispatcher.this.mReceiver);
                } catch (Exception e3) {
                    Slog.e(LoadedApk.TAG, "Exception : " + e3.toString());
                }
            }
        }

        ReceiverDispatcher(IApplicationThread iApplicationThread, BroadcastReceiver broadcastReceiver, Context context, Handler handler, Instrumentation instrumentation, boolean z) {
            if (handler == null) {
                throw new NullPointerException("Handler must not be null");
            }
            this.mAppThread = iApplicationThread;
            this.mIIntentReceiver = new InnerReceiver(iApplicationThread, this, !z);
            this.mReceiver = broadcastReceiver;
            this.mContext = context;
            this.mActivityThread = handler;
            this.mInstrumentation = instrumentation;
            this.mRegistered = z;
            this.mLocation = new IntentReceiverLeaked(null);
        }

        void validate(Context context, Handler handler) {
            if (this.mContext != context) {
                throw new IllegalStateException("Receiver " + this.mReceiver + " registered with differing Context (was " + this.mContext + " now " + context + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityThread == handler) {
                return;
            }
            throw new IllegalStateException("Receiver " + this.mReceiver + " registered with differing handler (was " + this.mActivityThread + " now " + handler + NavigationBarInflaterView.KEY_CODE_END);
        }

        IntentReceiverLeaked getLocation() {
            return this.mLocation;
        }

        BroadcastReceiver getIntentReceiver() {
            return this.mReceiver;
        }

        IIntentReceiver getIIntentReceiver() {
            return this.mIIntentReceiver;
        }

        void setUnregisterLocation(RuntimeException runtimeException) {
            this.mUnregisterLocation = runtimeException;
        }

        RuntimeException getUnregisterLocation() {
            return this.mUnregisterLocation;
        }

        public void performReceive(Intent intent, int i, String str, Bundle bundle, boolean z, boolean z2, boolean z3, int i2, int i3, String str2) {
            Args args = new Args(intent, i, str, bundle, z, z2, z3, i2, i3, str2);
            if (intent == null) {
                Log.wtf(LoadedApk.TAG, "Null intent received");
            }
            if (intent == null || !this.mActivityThread.post(args.getRunnable())) {
                args.sendFinished(ActivityManager.getService());
            }
        }
    }

    public final IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Context context, Handler handler, long j) {
        return getServiceDispatcherCommon(serviceConnection, context, handler, null, j);
    }

    public final IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Context context, Executor executor, long j) {
        return getServiceDispatcherCommon(serviceConnection, context, null, executor, j);
    }

    private IServiceConnection getServiceDispatcherCommon(ServiceConnection serviceConnection, Context context, Handler handler, Executor executor, long j) {
        IServiceConnection iServiceConnection;
        ServiceConnection serviceConnection2;
        Context context2;
        synchronized (this.mServices) {
            ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap = this.mServices.get(context);
            ServiceDispatcher serviceDispatcher = arrayMap != null ? arrayMap.get(serviceConnection) : null;
            if (serviceDispatcher == null) {
                if (executor != null) {
                    serviceConnection2 = serviceConnection;
                    context2 = context;
                    serviceDispatcher = new ServiceDispatcher(serviceConnection, context, executor, j);
                } else {
                    serviceConnection2 = serviceConnection;
                    context2 = context;
                    serviceDispatcher = new ServiceDispatcher(serviceConnection2, context2, handler, j);
                }
                if (arrayMap == null) {
                    arrayMap = new ArrayMap<>();
                    this.mServices.put(context2, arrayMap);
                }
                arrayMap.put(serviceConnection2, serviceDispatcher);
            } else {
                serviceDispatcher.validate(context, handler, executor);
            }
            iServiceConnection = serviceDispatcher.getIServiceConnection();
        }
        return iServiceConnection;
    }

    public IServiceConnection lookupServiceDispatcher(ServiceConnection serviceConnection, Context context) {
        IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap = this.mServices.get(context);
            ServiceDispatcher serviceDispatcher = arrayMap != null ? arrayMap.get(serviceConnection) : null;
            iServiceConnection = serviceDispatcher != null ? serviceDispatcher.getIServiceConnection() : null;
        }
        return iServiceConnection;
    }

    public final IServiceConnection forgetServiceDispatcher(Context context, ServiceConnection serviceConnection) {
        ServiceDispatcher serviceDispatcher;
        ServiceDispatcher serviceDispatcher2;
        IServiceConnection iServiceConnection;
        synchronized (this.mServices) {
            ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap = this.mServices.get(context);
            if (arrayMap != null && (serviceDispatcher2 = arrayMap.get(serviceConnection)) != null) {
                arrayMap.remove(serviceConnection);
                serviceDispatcher2.doForget();
                if (arrayMap.size() == 0) {
                    this.mServices.remove(context);
                }
                if ((serviceDispatcher2.getFlags() & 2) != 0) {
                    ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap2 = this.mUnboundServices.get(context);
                    if (arrayMap2 == null) {
                        arrayMap2 = new ArrayMap<>();
                        this.mUnboundServices.put(context, arrayMap2);
                    }
                    serviceDispatcher2.setUnbindLocation(new IllegalArgumentException("Originally unbound here:"));
                    arrayMap2.put(serviceConnection, serviceDispatcher2);
                }
                iServiceConnection = serviceDispatcher2.getIServiceConnection();
            } else {
                ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap3 = this.mUnboundServices.get(context);
                if (arrayMap3 != null && (serviceDispatcher = arrayMap3.get(serviceConnection)) != null) {
                    throw new IllegalArgumentException("Unbinding Service " + serviceConnection + " that was already unbound", serviceDispatcher.getUnbindLocation());
                }
                if (context == null) {
                    throw new IllegalStateException("Unbinding Service " + serviceConnection + " from Context that is no longer in use: " + context);
                }
                throw new IllegalArgumentException("Service not registered: " + serviceConnection);
            }
        }
        return iServiceConnection;
    }

    static final class ServiceDispatcher {
        private final ArrayMap<ComponentName, ConnectionInfo> mActiveConnections;
        private final Executor mActivityExecutor;
        private final Handler mActivityThread;
        private final ServiceConnection mConnection;
        private final Context mContext;
        private final long mFlags;
        private boolean mForgotten;
        private final InnerConnection mIServiceConnection;
        private final ServiceConnectionLeaked mLocation;
        private RuntimeException mUnbindLocation;

        private static class ConnectionInfo {
            IBinder binder;
            IBinder.DeathRecipient deathMonitor;

            private ConnectionInfo() {
            }
        }

        private static class InnerConnection extends IServiceConnection.Stub {
            final WeakReference<ServiceDispatcher> mDispatcher;

            InnerConnection(ServiceDispatcher serviceDispatcher) {
                this.mDispatcher = new WeakReference<>(serviceDispatcher);
            }

            @Override // android.app.IServiceConnection
            public void connected(ComponentName componentName, IBinder iBinder, boolean z) throws RemoteException {
                ServiceDispatcher serviceDispatcher = this.mDispatcher.get();
                if (serviceDispatcher != null) {
                    serviceDispatcher.connected(componentName, iBinder, z);
                }
            }
        }

        ServiceDispatcher(ServiceConnection serviceConnection, Context context, Handler handler, long j) {
            this.mActiveConnections = new ArrayMap<>();
            this.mIServiceConnection = new InnerConnection(this);
            this.mConnection = serviceConnection;
            this.mContext = context;
            this.mActivityThread = handler;
            this.mActivityExecutor = null;
            this.mLocation = new ServiceConnectionLeaked(null);
            this.mFlags = j;
        }

        ServiceDispatcher(ServiceConnection serviceConnection, Context context, Executor executor, long j) {
            this.mActiveConnections = new ArrayMap<>();
            this.mIServiceConnection = new InnerConnection(this);
            this.mConnection = serviceConnection;
            this.mContext = context;
            this.mActivityThread = null;
            this.mActivityExecutor = executor;
            this.mLocation = new ServiceConnectionLeaked(null);
            this.mFlags = j;
        }

        void validate(Context context, Handler handler, Executor executor) {
            if (this.mContext != context) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing Context (was " + this.mContext + " now " + context + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityThread != handler) {
                throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing handler (was " + this.mActivityThread + " now " + handler + NavigationBarInflaterView.KEY_CODE_END);
            }
            if (this.mActivityExecutor == executor) {
                return;
            }
            throw new RuntimeException("ServiceConnection " + this.mConnection + " registered with differing executor (was " + this.mActivityExecutor + " now " + executor + NavigationBarInflaterView.KEY_CODE_END);
        }

        void doForget() {
            synchronized (this) {
                for (int i = 0; i < this.mActiveConnections.size(); i++) {
                    ConnectionInfo connectionInfoValueAt = this.mActiveConnections.valueAt(i);
                    try {
                        connectionInfoValueAt.binder.unlinkToDeath(connectionInfoValueAt.deathMonitor, 0);
                    } catch (NoSuchElementException e) {
                        Log.e(LoadedApk.TAG, "Error during unlinkToDeath, " + this.mActiveConnections.keyAt(i).toString(), e);
                    }
                }
                this.mActiveConnections.clear();
                this.mForgotten = true;
            }
        }

        ServiceConnectionLeaked getLocation() {
            return this.mLocation;
        }

        ServiceConnection getServiceConnection() {
            return this.mConnection;
        }

        IServiceConnection getIServiceConnection() {
            return this.mIServiceConnection;
        }

        long getFlags() {
            return this.mFlags;
        }

        void setUnbindLocation(RuntimeException runtimeException) {
            this.mUnbindLocation = runtimeException;
        }

        RuntimeException getUnbindLocation() {
            return this.mUnbindLocation;
        }

        public void connected(ComponentName componentName, IBinder iBinder, boolean z) {
            Executor executor = this.mActivityExecutor;
            if (executor != null) {
                executor.execute(new RunConnection(componentName, iBinder, 0, z));
                return;
            }
            Handler handler = this.mActivityThread;
            if (handler != null) {
                handler.post(new RunConnection(componentName, iBinder, 0, z));
            } else {
                doConnected(componentName, iBinder, z);
            }
        }

        public void death(ComponentName componentName, IBinder iBinder) {
            Executor executor = this.mActivityExecutor;
            if (executor != null) {
                executor.execute(new RunConnection(componentName, iBinder, 1, false));
                return;
            }
            Handler handler = this.mActivityThread;
            if (handler != null) {
                handler.post(new RunConnection(componentName, iBinder, 1, false));
            } else {
                doDeath(componentName, iBinder);
            }
        }

        public void doConnected(ComponentName componentName, IBinder iBinder, boolean z) {
            synchronized (this) {
                if (this.mForgotten) {
                    return;
                }
                ConnectionInfo connectionInfo = this.mActiveConnections.get(componentName);
                if (connectionInfo == null || connectionInfo.binder != iBinder) {
                    if (iBinder != null) {
                        ConnectionInfo connectionInfo2 = new ConnectionInfo();
                        connectionInfo2.binder = iBinder;
                        connectionInfo2.deathMonitor = new DeathMonitor(componentName, iBinder);
                        try {
                            iBinder.linkToDeath(connectionInfo2.deathMonitor, 0);
                            this.mActiveConnections.put(componentName, connectionInfo2);
                        } catch (RemoteException unused) {
                            this.mActiveConnections.remove(componentName);
                            return;
                        }
                    } else {
                        this.mActiveConnections.remove(componentName);
                    }
                    if (connectionInfo != null) {
                        try {
                            connectionInfo.binder.unlinkToDeath(connectionInfo.deathMonitor, 0);
                        } catch (NoSuchElementException e) {
                            Log.e(LoadedApk.TAG, "Error during unlinkToDeath, " + componentName.toString(), e);
                        }
                    }
                    if (connectionInfo != null) {
                        this.mConnection.onServiceDisconnected(componentName);
                    }
                    if (z) {
                        this.mConnection.onBindingDied(componentName);
                    } else if (iBinder != null) {
                        this.mConnection.onServiceConnected(componentName, iBinder);
                    } else {
                        this.mConnection.onNullBinding(componentName);
                    }
                }
            }
        }

        public void doDeath(ComponentName componentName, IBinder iBinder) {
            synchronized (this) {
                ConnectionInfo connectionInfo = this.mActiveConnections.get(componentName);
                if (connectionInfo != null && connectionInfo.binder == iBinder) {
                    this.mActiveConnections.remove(componentName);
                    try {
                        connectionInfo.binder.unlinkToDeath(connectionInfo.deathMonitor, 0);
                    } catch (NoSuchElementException e) {
                        Log.e(LoadedApk.TAG, "Error during unlinkToDeath, " + componentName.toString(), e);
                    }
                    this.mConnection.onServiceDisconnected(componentName);
                }
            }
        }

        private final class RunConnection implements Runnable {
            final int mCommand;
            final boolean mDead;
            final ComponentName mName;
            final IBinder mService;

            RunConnection(ComponentName componentName, IBinder iBinder, int i, boolean z) {
                this.mName = componentName;
                this.mService = iBinder;
                this.mCommand = i;
                this.mDead = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                int i = this.mCommand;
                if (i == 0) {
                    ServiceDispatcher.this.doConnected(this.mName, this.mService, this.mDead);
                } else if (i == 1) {
                    ServiceDispatcher.this.doDeath(this.mName, this.mService);
                }
            }
        }

        private final class DeathMonitor implements IBinder.DeathRecipient {
            final ComponentName mName;
            final IBinder mService;

            DeathMonitor(ComponentName componentName, IBinder iBinder) {
                this.mName = componentName;
                this.mService = iBinder;
            }

            @Override // android.os.IBinder.DeathRecipient
            public void binderDied() {
                ServiceDispatcher.this.death(this.mName, this.mService);
            }
        }
    }

    public static void checkAndUpdateApkPaths(ApplicationInfo applicationInfo) {
        ActivityThread activityThreadCurrentActivityThread = ActivityThread.currentActivityThread();
        if (activityThreadCurrentActivityThread == null) {
            Log.e(TAG, "Cannot find activity thread");
        } else {
            checkAndUpdateApkPaths(activityThreadCurrentActivityThread, applicationInfo, true);
            checkAndUpdateApkPaths(activityThreadCurrentActivityThread, applicationInfo, false);
        }
    }

    private static void checkAndUpdateApkPaths(ActivityThread activityThread, ApplicationInfo applicationInfo, boolean z) {
        String codePath = applicationInfo.getCodePath();
        LoadedApk loadedApkPeekPackageInfo = activityThread.peekPackageInfo(applicationInfo.packageName, z);
        if (loadedApkPeekPackageInfo == null || loadedApkPeekPackageInfo.getApplicationInfo() == null || loadedApkPeekPackageInfo.getApplicationInfo().getCodePath().equals(codePath)) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        makePaths(activityThread, applicationInfo, arrayList);
        loadedApkPeekPackageInfo.updateApplicationInfo(applicationInfo, arrayList);
    }
}
