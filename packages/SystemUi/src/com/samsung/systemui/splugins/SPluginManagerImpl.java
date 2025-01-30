package com.samsung.systemui.splugins;

import android.app.ActivityThread;
import android.app.LoadedApk;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.samsung.systemui.splugins.SPluginInstanceManager;
import com.samsung.systemui.splugins.SPluginManager;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class SPluginManagerImpl extends BroadcastReceiver implements SPluginManager {
    private static final String ALL_SPLUGIN_DISABLED = "all_splugin_disabled";
    static final String DISABLE_PLUGIN = "com.samsung.systemui.action.DISABLE_PLUGIN";
    private static final int MAX_EXCEPTION_COUNT = 5;
    private static final int MAX_EXCEPTION_TIME = 180000;
    private static SPluginManager sInstance;
    private final boolean isDebuggable;
    private final ArraySet<String> mAllowedPlugins;
    private BroadcastDispatcher mBroadcastDispatcher;
    private final Map<String, ClassLoader> mClassLoaders;
    private final Context mContext;
    private final PluginInstanceManagerFactory mFactory;
    private boolean mHasOneShot;
    private boolean mListening;
    private Looper mLooper;
    private final ArraySet<String> mOneShotPackages;
    private ClassLoaderFilter mParentClassLoader;
    private final ArrayMap<SPluginListener<?>, SPluginInstanceManager> mPluginMap;
    private final SPluginEnabler mSPluginEnabler;
    private final SPluginInitializer mSPluginInitializer;
    private final SPluginPrefs mSPluginPrefs;
    private static final String TAG = "SPluginManagerImpl";
    static final String[] IGNORE_EXCEPTION = {"com.samsung.systemui.bixby", "com.samsung.systemui.bixby2", "com.samsung.android.dynamiclock", "com.samsung.android.mateagent", "com.samsung.android.app.aodservice"};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class ClassLoaderFilter extends ClassLoader {
        private final ClassLoader mBase;
        private final String mPackage;

        public ClassLoaderFilter(ClassLoader classLoader, String str) {
            super(ClassLoader.getSystemClassLoader());
            this.mBase = classLoader;
            this.mPackage = str;
        }

        @Override // java.lang.ClassLoader
        public Class<?> loadClass(String str, boolean z) {
            if (!str.startsWith("com.samsung.systemui.splugin") && !str.startsWith("com.samsung.android.sdk.bixby")) {
                super.loadClass(str, z);
            }
            return this.mBase.loadClass(str);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class CrashWhilePluginActiveException extends RuntimeException {
        public CrashWhilePluginActiveException(Throwable th) {
            super(th);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class PluginExceptionHandler implements Thread.UncaughtExceptionHandler {
        public /* synthetic */ PluginExceptionHandler(SPluginManagerImpl sPluginManagerImpl, int i) {
            this();
        }

        private boolean checkStack(Throwable th) {
            if (th == null) {
                return false;
            }
            boolean z = false;
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                Iterator it = SPluginManagerImpl.this.mPluginMap.values().iterator();
                while (it.hasNext()) {
                    z |= ((SPluginInstanceManager) it.next()).checkAndDisable(stackTraceElement.getClassName());
                }
            }
            return checkStack(th.getCause()) | z;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(Thread thread, Throwable th) {
            if (SystemProperties.getBoolean("plugin.debugging", false)) {
                return;
            }
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long firstUncaughtExceptionTime = SPluginPrefs.getFirstUncaughtExceptionTime(SPluginManagerImpl.this.mContext);
            if (firstUncaughtExceptionTime == 0 || elapsedRealtime - firstUncaughtExceptionTime > 180000) {
                SPluginPrefs.setUncaughtExceptionCount(SPluginManagerImpl.this.mContext, 1);
                SPluginPrefs.setFirstUncaughtExceptionTime(SPluginManagerImpl.this.mContext, elapsedRealtime);
                return;
            }
            int uncaughtExceptionCount = SPluginPrefs.getUncaughtExceptionCount(SPluginManagerImpl.this.mContext) + 1;
            if (uncaughtExceptionCount < 5) {
                SPluginPrefs.setUncaughtExceptionCount(SPluginManagerImpl.this.mContext, uncaughtExceptionCount);
                return;
            }
            String str = SPluginManagerImpl.TAG;
            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("UncaughtException - currentTime = ", elapsedRealtime, "   firstExceptionTime = ");
            m17m.append(firstUncaughtExceptionTime);
            Log.i(str, m17m.toString());
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(SPluginManagerImpl.IGNORE_EXCEPTION));
            Iterator it = SPluginManagerImpl.this.mPluginMap.values().iterator();
            while (it.hasNext()) {
                ((SPluginInstanceManager) it.next()).disableAll(arrayList);
            }
            SPluginPrefs.setUncaughtExceptionCount(SPluginManagerImpl.this.mContext, 0);
            SPluginPrefs.setFirstUncaughtExceptionTime(SPluginManagerImpl.this.mContext, 0L);
            Settings.Secure.putInt(SPluginManagerImpl.this.mContext.getContentResolver(), SPluginManagerImpl.ALL_SPLUGIN_DISABLED, 1);
        }

        private PluginExceptionHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class PluginInstanceManagerFactory {
        public <T extends SPlugin> SPluginInstanceManager createPluginInstanceManager(Context context, String str, SPluginListener<T> sPluginListener, boolean z, boolean z2, Looper looper, Class<?> cls, SPluginManagerImpl sPluginManagerImpl) {
            return new SPluginInstanceManager(context, str, sPluginListener, z, z2, looper, new SVersionInfo().addClass(cls), sPluginManagerImpl, new SPluginPolicyInteractor(context));
        }
    }

    public SPluginManagerImpl(Context context, SPluginInitializer sPluginInitializer, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager) {
        this(context, new PluginInstanceManagerFactory(), Build.IS_DEBUGGABLE, sPluginInitializer, uncaughtExceptionPreHandlerManager);
    }

    private boolean clearClassLoader(String str) {
        return this.mClassLoaders.remove(str) != null;
    }

    private boolean isPluginLockPackage(String str) {
        return str.equals("com.samsung.android.dynamiclock") || str.equals("com.samsung.android.mateagent");
    }

    private void startListening() {
        if (this.mListening) {
            return;
        }
        this.mListening = true;
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REPLACED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        if (this.mBroadcastDispatcher == null) {
            BroadcastDispatcher broadcastDispatcher = (BroadcastDispatcher) Dependency.get(BroadcastDispatcher.class);
            this.mBroadcastDispatcher = broadcastDispatcher;
            broadcastDispatcher.registerReceiver(intentFilter, this);
        }
        this.mContext.registerReceiver(this, new IntentFilter("android.intent.action.USER_UNLOCKED"));
    }

    private void stopListening() {
        if (!this.mListening || this.mHasOneShot) {
            return;
        }
        this.mListening = false;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        if (broadcastDispatcher != null) {
            broadcastDispatcher.unregisterReceiver(this);
            this.mBroadcastDispatcher = null;
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public <T extends SPlugin> void addPluginListener(SPluginListener<T> sPluginListener, Class<?> cls) {
        addPluginListener((SPluginListener) sPluginListener, cls, false);
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public <T> boolean dependsOn(SPlugin sPlugin, Class<T> cls) {
        for (int i = 0; i < this.mPluginMap.size(); i++) {
            if (this.mPluginMap.valueAt(i).dependsOn(sPlugin, cls)) {
                return true;
            }
        }
        return false;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(String.format("  plugin map (%d):", Integer.valueOf(this.mPluginMap.size())));
        for (SPluginListener<?> sPluginListener : this.mPluginMap.keySet()) {
            printWriter.println(String.format("    %s -> %s", sPluginListener, this.mPluginMap.get(sPluginListener)));
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public String[] getAllowedPlugins() {
        return (String[]) this.mAllowedPlugins.toArray(new String[0]);
    }

    public ClassLoader getClassLoader(ApplicationInfo applicationInfo) {
        if (this.mClassLoaders.containsKey(applicationInfo.packageName)) {
            return this.mClassLoaders.get(applicationInfo.packageName);
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        LoadedApk.makePaths((ActivityThread) null, true, applicationInfo, arrayList, arrayList2);
        String str = File.pathSeparator;
        PathClassLoader pathClassLoader = new PathClassLoader(TextUtils.join(str, arrayList), TextUtils.join(str, arrayList2), getParentClassLoader());
        this.mClassLoaders.put(applicationInfo.packageName, pathClassLoader);
        return pathClassLoader;
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public <T extends SPlugin> T getOneShotPlugin(Class<T> cls) {
        ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
        if (providesInterface == null) {
            throw new RuntimeException(cls + " doesn't provide an interface");
        }
        if (!TextUtils.isEmpty(providesInterface.action())) {
            return (T) getOneShotPlugin(providesInterface.action(), cls);
        }
        throw new RuntimeException(cls + " doesn't provide an action");
    }

    public ClassLoader getParentClassLoader() {
        if (this.mParentClassLoader == null) {
            this.mParentClassLoader = new ClassLoaderFilter(getClass().getClassLoader(), "com.android.systemui.plugin");
        }
        return this.mParentClassLoader;
    }

    public SPluginEnabler getPluginEnabler() {
        return this.mSPluginEnabler;
    }

    public void handleWtfs() {
        this.mSPluginInitializer.handleWtfs();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int disableReason;
        String str;
        String action = intent.getAction();
        String str2 = TAG;
        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("onReceive: ", action, ", size:");
        m4m.append(this.mPluginMap.toString());
        Log.d(str2, m4m.toString());
        if ("android.intent.action.USER_UNLOCKED".equals(action)) {
            Iterator<SPluginInstanceManager> it = this.mPluginMap.values().iterator();
            while (it.hasNext()) {
                it.next().loadAll();
            }
            return;
        }
        if (DISABLE_PLUGIN.equals(action)) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(intent.getData().toString().substring(10));
            if (this.mAllowedPlugins.contains(unflattenFromString.getPackageName())) {
                return;
            }
            getPluginEnabler().setDisabled(unflattenFromString, 1);
            ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).cancel(unflattenFromString.getClassName(), 6);
            return;
        }
        String encodedSchemeSpecificPart = intent.getData().getEncodedSchemeSpecificPart();
        ComponentName unflattenFromString2 = ComponentName.unflattenFromString(encodedSchemeSpecificPart);
        if (this.mOneShotPackages.contains(encodedSchemeSpecificPart)) {
            int identifier = this.mContext.getResources().getIdentifier("tuner", "drawable", this.mContext.getPackageName());
            int identifier2 = Resources.getSystem().getIdentifier("system_notification_accent_color", "color", "android");
            try {
                PackageManager packageManager = this.mContext.getPackageManager();
                str = packageManager.getApplicationInfo(encodedSchemeSpecificPart, 0).loadLabel(packageManager).toString();
            } catch (PackageManager.NameNotFoundException unused) {
                str = encodedSchemeSpecificPart;
            }
            Notification.Builder contentText = new Notification.Builder(this.mContext, "ALR").setSmallIcon(identifier).setWhen(0L).setShowWhen(false).setPriority(2).setVisibility(1).setColor(this.mContext.getColor(identifier2)).setContentTitle("Plugin \"" + str + "\" has updated").setContentText("Restart SysUI for changes to take effect.");
            contentText.addAction(new Notification.Action.Builder((Icon) null, "Restart SysUI", PendingIntent.getBroadcast(this.mContext, 0, new Intent("com.android.systemui.action.RESTART").setData(Uri.parse("package://" + encodedSchemeSpecificPart)), 0)).build());
            ((NotificationManager) this.mContext.getSystemService(NotificationManager.class)).notifyAsUser(encodedSchemeSpecificPart, 6, contentText.build(), UserHandle.ALL);
        }
        clearClassLoader(encodedSchemeSpecificPart);
        if ("android.intent.action.PACKAGE_REPLACED".equals(action) && unflattenFromString2 != null && ((disableReason = getPluginEnabler().getDisableReason(unflattenFromString2)) == 2 || disableReason == 3 || disableReason == 1)) {
            Log.i(TAG, "Re-enabling previously disabled plugin that has been updated: " + unflattenFromString2.flattenToShortString());
            getPluginEnabler().setEnabled(unflattenFromString2);
        }
        if (!"android.intent.action.PACKAGE_REMOVED".equals(action)) {
            if (isPluginLockPackage(encodedSchemeSpecificPart) && "android.intent.action.PACKAGE_REPLACED".equals(action)) {
                return;
            }
            Iterator<SPluginInstanceManager> it2 = this.mPluginMap.values().iterator();
            while (it2.hasNext()) {
                it2.next().onPackageChange(encodedSchemeSpecificPart);
            }
            return;
        }
        for (SPluginInstanceManager sPluginInstanceManager : this.mPluginMap.values()) {
            if (!isPluginLockPackage(encodedSchemeSpecificPart) || intent.getBooleanExtra("android.intent.extra.DATA_REMOVED", false)) {
                sPluginInstanceManager.onPackageRemoved(encodedSchemeSpecificPart);
            } else {
                sPluginInstanceManager.onPackageUpdated(encodedSchemeSpecificPart);
            }
        }
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public void removePluginListener(SPluginListener<?> sPluginListener) {
        if (this.mPluginMap.containsKey(sPluginListener)) {
            this.mPluginMap.remove(sPluginListener).destroy();
            if (this.mPluginMap.size() == 0) {
                stopListening();
            }
        }
    }

    public SPluginManagerImpl(Context context, PluginInstanceManagerFactory pluginInstanceManagerFactory, boolean z, final SPluginInitializer sPluginInitializer, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager) {
        this.mPluginMap = new ArrayMap<>();
        this.mClassLoaders = new ArrayMap();
        this.mOneShotPackages = new ArraySet<>();
        ArraySet<String> arraySet = new ArraySet<>();
        this.mAllowedPlugins = arraySet;
        this.mContext = context;
        this.mFactory = pluginInstanceManagerFactory;
        this.mLooper = sPluginInitializer.getBgLooper();
        this.isDebuggable = z;
        arraySet.addAll(Arrays.asList(sPluginInitializer.getAllowedPlugins(context)));
        this.mSPluginPrefs = new SPluginPrefs(context);
        this.mSPluginEnabler = sPluginInitializer.getPluginEnabler(context);
        this.mSPluginInitializer = sPluginInitializer;
        uncaughtExceptionPreHandlerManager.registerHandler(new PluginExceptionHandler(this, 0));
        new Handler(this.mLooper).post(new Runnable() { // from class: com.samsung.systemui.splugins.SPluginManagerImpl.1
            @Override // java.lang.Runnable
            public void run() {
                sPluginInitializer.onPluginManagerInit();
            }
        });
        SPluginVersions.initVersion();
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public <T extends SPlugin> void addPluginListener(SPluginListener<T> sPluginListener, Class<?> cls, boolean z) {
        addPluginListener(SPluginManager.Helper.getAction(cls), sPluginListener, cls, z, true);
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public <T extends SPlugin> void addPluginListener(SPluginListener<T> sPluginListener, Class<?> cls, boolean z, boolean z2) {
        addPluginListener(SPluginManager.Helper.getAction(cls), sPluginListener, cls, z, z2);
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public <T extends SPlugin> void addPluginListener(String str, SPluginListener<T> sPluginListener, Class<?> cls) {
        addPluginListener(str, sPluginListener, cls, false, true);
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public <T extends SPlugin> void addPluginListener(String str, SPluginListener<T> sPluginListener, Class cls, boolean z, boolean z2) {
        this.mSPluginPrefs.addAction(str);
        SPluginInstanceManager createPluginInstanceManager = this.mFactory.createPluginInstanceManager(this.mContext, str, sPluginListener, z, z2, this.mLooper, cls, this);
        createPluginInstanceManager.loadAll();
        this.mPluginMap.put(sPluginListener, createPluginInstanceManager);
        startListening();
    }

    @Override // com.samsung.systemui.splugins.SPluginManager
    public <T extends SPlugin> T getOneShotPlugin(String str, Class<?> cls) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            SPluginInstanceManager createPluginInstanceManager = this.mFactory.createPluginInstanceManager(this.mContext, str, null, false, false, this.mLooper, cls, this);
            this.mSPluginPrefs.addAction(str);
            SPluginInstanceManager.PluginInfo<T> plugin = createPluginInstanceManager.getPlugin();
            if (plugin == null) {
                return null;
            }
            this.mOneShotPackages.add(plugin.mPackage);
            this.mHasOneShot = true;
            startListening();
            return plugin.mPlugin;
        }
        throw new RuntimeException("Must be called from UI thread");
    }
}
