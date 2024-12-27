package com.android.systemui;

import android.R;
import android.app.ActivityThread;
import android.app.Application;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Looper;
import android.os.Process;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Log;
import android.util.TimingsTraceLog;
import android.view.SurfaceControl;
import android.view.ThreadedRenderer;
import android.view.View;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import com.android.app.viewcapture.ViewCapture$$ExternalSyntheticLambda9;
import com.android.internal.protolog.common.ProtoLog;
import com.android.keyguard.KeyguardSecPatternView$$ExternalSyntheticOutline0;
import com.android.systemui.BootAnimationFinishedCache;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.dagger.GlobalRootComponent;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.process.ProcessWrapper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SystemUIAnalytics;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.function.Function;
import javax.inject.Provider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class SystemUIApplication extends Application implements SystemUIAppComponentFactoryBase.ContextInitializer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public BootAnimationFinishedTrigger mBootAnimationFinishedTrigger;
    public BootCompleteCacheImpl mBootCompleteCache;
    public SystemUIAppComponentFactoryBase.ContextAvailableCallback mContextAvailableCallback;
    public SystemUIInitializer mInitializer;
    public CoreStartable[] mPostServices;
    public boolean mPostServicesStarted;
    public ProcessWrapper mProcessWrapper;
    public CoreStartable[] mServices;
    public boolean mServicesStarted;
    public SysUIComponent mSysUIComponent;
    public int mFlipfont = 0;
    public final SystemUIThemeHelper mThemeHelper = new SystemUIThemeHelper(0);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SystemUIThemeHelper {
        private SystemUIThemeHelper() {
        }

        public /* synthetic */ SystemUIThemeHelper(int i) {
            this();
        }
    }

    static {
        Looper mainLooper;
        if (!Rune.SYSUI_UI_THREAD_MONITOR || DeviceState.isAlreadyBooted() || !Process.myUserHandle().equals(UserHandle.SYSTEM) || (mainLooper = Looper.getMainLooper()) == null) {
            return;
        }
        Log.i("LooperSlow", "enable looper log on boot");
        mainLooper.setSlowLogThresholdMs(30L, 50L);
    }

    public SystemUIApplication() {
        Trace.registerWithPerfetto();
        ProtoLog.REQUIRE_PROTOLOGTOOL = false;
    }

    public static void notifyBootCompleted(CoreStartable coreStartable) {
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, coreStartable.getClass().getSimpleName().concat(".onBootCompleted()"));
        }
        coreStartable.onBootCompleted();
        Trace.endSection();
    }

    public static void overrideNotificationAppName(Context context, Notification.Builder builder, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", z ? context.getString(R.string.searchview_description_voice) : context.getString(R.string.searchview_description_submit));
        builder.addExtras(bundle);
    }

    public static void startStartable(CoreStartable coreStartable) {
        Log.d("SystemUIService", "running: " + coreStartable);
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, coreStartable.getClass().getSimpleName().concat(".start()"));
        }
        coreStartable.start();
        Trace.endSection();
    }

    public static void timeInitialization(String str, Runnable runnable, TimingsTraceLog timingsTraceLog, String str2) {
        long currentTimeMillis = System.currentTimeMillis();
        timingsTraceLog.traceBegin(str2 + " " + str);
        runnable.run();
        timingsTraceLog.traceEnd();
        long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
        if (currentTimeMillis2 > 1000) {
            Log.w("SystemUIService", "Initialization of " + str + " took " + currentTimeMillis2 + " ms");
        }
    }

    @Override // android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.app.Application, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        if (this.mServicesStarted) {
            SystemUIThemeHelper systemUIThemeHelper = this.mThemeHelper;
            if (systemUIThemeHelper != null) {
                systemUIThemeHelper.getClass();
                Configuration configuration2 = getResources().getConfiguration();
                if (configuration2 != null && (configuration2.updateFrom(Configuration.generateDelta(configuration2, configuration)) & (-2147418112)) != 0) {
                    try {
                        getResources().setImpl(createPackageContext(getPackageName(), 0).getResources().getImpl());
                    } catch (Exception unused) {
                    }
                }
            }
            ConfigurationController configurationController = this.mSysUIComponent.getConfigurationController();
            if (Trace.isEnabled()) {
                Trace.traceBegin(4096L, configurationController.getClass().getSimpleName().concat(".onConfigurationChanged()"));
            }
            ((ConfigurationControllerImpl) configurationController).onConfigurationChanged(configuration);
            Trace.endSection();
            int i = configuration.FlipFont;
            if (i <= 0 || this.mFlipfont == i) {
                return;
            }
            Typeface.setFlipFonts();
            this.mFlipfont = configuration.FlipFont;
        }
    }

    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SystemUIBootTiming", 4096L);
        timingsTraceLog.traceBegin("DependencyInjection");
        SystemUIInitializer onContextAvailable = this.mContextAvailableCallback.onContextAvailable(this);
        this.mInitializer = onContextAvailable;
        SysUIComponent sysUIComponent = onContextAvailable.getSysUIComponent();
        this.mSysUIComponent = sysUIComponent;
        this.mBootCompleteCache = sysUIComponent.provideBootCacheImpl();
        timingsTraceLog.traceEnd();
        GlobalRootComponent rootComponent = this.mInitializer.getRootComponent();
        rootComponent.getMainLooper().setTraceTag(4096L);
        this.mProcessWrapper = rootComponent.getProcessWrapper();
        setTheme(R.style.Theme_SystemUI);
        rootComponent.getSystemPropertiesHelper().getClass();
        View.setTraceLayoutSteps(SystemProperties.getBoolean("persist.debug.trace_layouts", false));
        rootComponent.getSystemPropertiesHelper().getClass();
        View.setTracedRequestLayoutClassClass(SystemProperties.get("persist.debug.trace_request_layout_class", (String) null));
        Flags.FEATURE_FLAGS.getClass();
        this.mProcessWrapper.getClass();
        if (!ProcessWrapper.isSystemUser()) {
            String currentProcessName = ActivityThread.currentProcessName();
            ApplicationInfo applicationInfo = getApplicationInfo();
            if (currentProcessName != null) {
                if (currentProcessName.startsWith(applicationInfo.processName + ":")) {
                    return;
                }
            }
            startSecondaryUserServicesIfNeeded();
            return;
        }
        this.mSysUIComponent.provideBootAnimationFinishedImpl().addListener(new BootAnimationFinishedCache.BootAnimationFinishedListener() { // from class: com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda1
            @Override // com.android.systemui.BootAnimationFinishedCache.BootAnimationFinishedListener
            public final void onBootAnimationFinished() {
                int i = SystemUIApplication.$r8$clinit;
                SystemUIApplication systemUIApplication = SystemUIApplication.this;
                systemUIApplication.getClass();
                TreeMap treeMap = new TreeMap(Comparator.comparing(new ViewCapture$$ExternalSyntheticLambda9()));
                treeMap.putAll(systemUIApplication.mSysUIComponent.getPostStartables());
                if (systemUIApplication.mPostServicesStarted) {
                    return;
                }
                systemUIApplication.mPostServices = new CoreStartable[treeMap.size()];
                Process.myUserHandle().getIdentifier();
                TimingsTraceLog timingsTraceLog2 = new TimingsTraceLog("SystemUIPostBootTiming ", 4096L);
                timingsTraceLog2.traceBegin("StartPostServices ");
                systemUIApplication.startServicesImpl(treeMap, "StartPostServices ", systemUIApplication.mPostServices, null, timingsTraceLog2);
                timingsTraceLog2.traceEnd();
                systemUIApplication.mPostServicesStarted = true;
            }
        });
        IntentFilter intentFilter = new IntentFilter("android.intent.action.LOCKED_BOOT_COMPLETED");
        intentFilter.setPriority(1000);
        int gPUContextPriority = SurfaceControl.getGPUContextPriority();
        KeyguardSecPatternView$$ExternalSyntheticOutline0.m(gPUContextPriority, "Found SurfaceFlinger's GPU Priority: ", "SystemUIService");
        if (gPUContextPriority == ThreadedRenderer.EGL_CONTEXT_PRIORITY_REALTIME_NV) {
            TooltipPopup$$ExternalSyntheticOutline0.m(ThreadedRenderer.EGL_CONTEXT_PRIORITY_HIGH_IMG, "SystemUIService", new StringBuilder("Setting SysUI's GPU Context priority to: "));
            ThreadedRenderer.setContextPriority(ThreadedRenderer.EGL_CONTEXT_PRIORITY_HIGH_IMG);
        }
        registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.SystemUIApplication.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if (SystemUIApplication.this.mBootCompleteCache.bootComplete.get()) {
                    return;
                }
                SystemUIAnalytics.initSystemUIAnalyticsStates(SystemUIApplication.this);
                SystemUIApplication.this.unregisterReceiver(this);
                SystemUIApplication.this.mBootCompleteCache.setBootComplete();
                SystemUIApplication systemUIApplication = SystemUIApplication.this;
                if (systemUIApplication.mServicesStarted) {
                    int length = systemUIApplication.mServices.length;
                    for (int i = 0; i < length; i++) {
                        SystemUIApplication.notifyBootCompleted(SystemUIApplication.this.mServices[i]);
                    }
                }
            }
        }, intentFilter);
        registerReceiver(new BroadcastReceiver() { // from class: com.android.systemui.SystemUIApplication.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                if ("android.intent.action.LOCALE_CHANGED".equals(intent.getAction()) && SystemUIApplication.this.mBootCompleteCache.bootComplete.get()) {
                    NotificationChannels.createAll(context);
                }
            }
        }, new IntentFilter("android.intent.action.LOCALE_CHANGED"));
    }

    @Override // android.app.Application, android.content.ComponentCallbacks2
    public final void onTrimMemory(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "SYSUI_RAM_OPTIMIZATION onTrimMemory=", "SystemUIService");
        if (this.mServicesStarted) {
            int length = this.mServices.length;
            for (int i2 = 0; i2 < length; i2++) {
                CoreStartable coreStartable = this.mServices[i2];
                if (coreStartable != null) {
                    coreStartable.onTrimMemory(i);
                }
            }
        }
        if (this.mPostServicesStarted) {
            int length2 = this.mPostServices.length;
            for (int i3 = 0; i3 < length2; i3++) {
                CoreStartable coreStartable2 = this.mPostServices[i3];
                if (coreStartable2 != null) {
                    coreStartable2.onTrimMemory(i);
                }
            }
        }
    }

    @Override // com.android.systemui.SystemUIAppComponentFactoryBase.ContextInitializer
    public final void setContextAvailableCallback(SystemUIAppComponentFactoryBase.ContextAvailableCallback contextAvailableCallback) {
        this.mContextAvailableCallback = contextAvailableCallback;
    }

    public final void startSecondaryUserServicesIfNeeded() {
        this.mProcessWrapper.getClass();
        if (!ProcessWrapper.isSystemUser()) {
            TreeMap treeMap = new TreeMap(Comparator.comparing(new ViewCapture$$ExternalSyntheticLambda9()));
            treeMap.putAll(this.mSysUIComponent.getPerUserStartables());
            startServicesIfNeeded("StartSecondaryServices", null, treeMap);
        }
    }

    public final void startServicesIfNeeded(String str, String str2, Map map) {
        if (this.mServicesStarted) {
            return;
        }
        this.mServices = new CoreStartable[((TreeMap) map).size() + (str2 == null ? 0 : 1)];
        if (!this.mBootCompleteCache.bootComplete.get()) {
            this.mInitializer.getRootComponent().getSystemPropertiesHelper().getClass();
            if ("1".equals(SystemProperties.get("sys.boot_completed"))) {
                this.mBootCompleteCache.setBootComplete();
                this.mBootAnimationFinishedTrigger = this.mSysUIComponent.provideBootAnimationFinishedTrigger();
                getMainThreadHandler().post(new Runnable() { // from class: com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((BootAnimationFinishedCacheImpl) SystemUIApplication.this.mBootAnimationFinishedTrigger).setBootAnimationFinished();
                    }
                });
                SystemUIAnalytics.initSystemUIAnalyticsStates(this);
            }
        }
        Process.myUserHandle().getIdentifier();
        TimingsTraceLog timingsTraceLog = new TimingsTraceLog("SystemUIBootTiming ", 4096L);
        timingsTraceLog.traceBegin(str);
        startServicesImpl(map, str, this.mServices, str2, timingsTraceLog);
        InitController initController = this.mSysUIComponent.getInitController();
        while (!initController.mTasks.isEmpty()) {
            ((Runnable) initController.mTasks.remove(0)).run();
        }
        initController.mTasksExecuted = true;
        timingsTraceLog.traceEnd();
        this.mServicesStarted = true;
    }

    public final void startServicesImpl(Map map, String str, final CoreStartable[] coreStartableArr, final String str2, TimingsTraceLog timingsTraceLog) {
        ArrayDeque arrayDeque;
        DumpManager createDumpManager = this.mSysUIComponent.createDumpManager();
        HashSet hashSet = new HashSet();
        timingsTraceLog.traceBegin("Topologically start Core Startables");
        TreeMap treeMap = (TreeMap) map;
        ArrayDeque arrayDeque2 = new ArrayDeque(treeMap.entrySet());
        int i = 0;
        final int i2 = 0;
        while (true) {
            arrayDeque = new ArrayDeque(treeMap.size());
            boolean z = false;
            while (!arrayDeque2.isEmpty()) {
                final Map.Entry entry = (Map.Entry) arrayDeque2.removeFirst();
                Class cls = (Class) entry.getKey();
                Set set = (Set) this.mSysUIComponent.getStartableDependencies().get(cls);
                if (set == null || hashSet.containsAll(set)) {
                    final String name = cls.getName();
                    timeInitialization(name, new Runnable() { // from class: com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            CoreStartable[] coreStartableArr2 = coreStartableArr;
                            int i3 = i2;
                            String str3 = name;
                            Map.Entry entry2 = entry;
                            int i4 = SystemUIApplication.$r8$clinit;
                            Provider provider = (Provider) entry2.getValue();
                            Log.d("SystemUIService", "loading: " + str3);
                            if (Trace.isEnabled()) {
                                Trace.traceBegin(4096L, "Provider<" + str3 + ">.get()");
                            }
                            CoreStartable coreStartable = (CoreStartable) provider.get();
                            Trace.endSection();
                            SystemUIApplication.startStartable(coreStartable);
                            coreStartableArr2[i3] = coreStartable;
                        }
                    }, timingsTraceLog, str);
                    hashSet.add(cls);
                    i2++;
                    z = true;
                } else {
                    arrayDeque.add(entry);
                }
            }
            i++;
            if (!z || arrayDeque.isEmpty()) {
                break;
            } else {
                arrayDeque2 = arrayDeque;
            }
        }
        if (!arrayDeque.isEmpty()) {
            while (!arrayDeque.isEmpty()) {
                Class cls2 = (Class) ((Map.Entry) arrayDeque.removeFirst()).getKey();
                Set<Class> set2 = (Set) this.mSysUIComponent.getStartableDependencies().get(cls2);
                StringJoiner stringJoiner = new StringJoiner(", ");
                for (Class cls3 : set2) {
                    if (!hashSet.contains(cls3)) {
                        stringJoiner.add(cls3.getName());
                    }
                }
                Log.e("SystemUIService", "Failed to start " + cls2.getName() + ". Missing dependencies: [" + stringJoiner + "]");
            }
            throw new RuntimeException("Failed to start all CoreStartables. Check logcat!");
        }
        Log.i("SystemUIService", "Topological CoreStartables completed in " + i + " iterations");
        timingsTraceLog.traceEnd();
        if (str2 != null) {
            timeInitialization(str2, new Runnable() { // from class: com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    CoreStartable[] coreStartableArr2 = coreStartableArr;
                    String str3 = str2;
                    int i3 = SystemUIApplication.$r8$clinit;
                    int length = coreStartableArr2.length - 1;
                    Log.d("SystemUIService", "loading: " + str3);
                    if (Trace.isEnabled()) {
                        Trace.traceBegin(4096L, str3 + ".newInstance()");
                    }
                    try {
                        try {
                            Class[] clsArr = new Class[0];
                            CoreStartable coreStartable = (CoreStartable) Class.forName(str3).getDeclaredConstructor(null).newInstance(null);
                            Trace.endSection();
                            SystemUIApplication.startStartable(coreStartable);
                            coreStartableArr2[length] = coreStartable;
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (Throwable th) {
                        Trace.endSection();
                        throw th;
                    }
                }
            }, timingsTraceLog, str);
        }
        for (CoreStartable coreStartable : coreStartableArr) {
            if (this.mBootCompleteCache.bootComplete.get()) {
                notifyBootCompleted(coreStartable);
            }
            if (coreStartable.isDumpCritical()) {
                createDumpManager.getClass();
                createDumpManager.registerCriticalDumpable(coreStartable.getClass().getCanonicalName(), coreStartable);
            } else {
                createDumpManager.registerNormalDumpable(coreStartable);
            }
        }
    }

    public final void startSystemUserServicesIfNeeded() {
        this.mProcessWrapper.getClass();
        if (!ProcessWrapper.isSystemUser()) {
            Log.wtf("SystemUIService", "Tried starting SystemUser services on non-SystemUser");
            return;
        }
        final String vendorComponent = this.mInitializer.getVendorComponent(getResources());
        boolean z = Rune.SYSUI_UI_THREAD_MONITOR;
        TreeMap treeMap = new TreeMap(Comparator.comparing(z ? new Function() { // from class: com.android.systemui.SystemUIApplication$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                String str = vendorComponent;
                Class cls = (Class) obj;
                int i = SystemUIApplication.$r8$clinit;
                return cls.getName().equals(str) ? "a" : cls.getName();
            }
        } : new ViewCapture$$ExternalSyntheticLambda9()));
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            treeMap.putAll(this.mSysUIComponent.getSafeUIStartables());
        } else {
            treeMap.putAll(this.mSysUIComponent.getStartables());
            treeMap.putAll(this.mSysUIComponent.getPreStartables());
            treeMap.putAll(this.mSysUIComponent.getPerUserStartables());
        }
        if (z) {
            vendorComponent = null;
        }
        startServicesIfNeeded("StartServices", vendorComponent, treeMap);
    }
}
