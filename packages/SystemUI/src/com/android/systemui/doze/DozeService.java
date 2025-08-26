package com.android.systemui.doze;

import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.os.SystemClock;
import android.service.dreams.DreamService;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.DozeServicePlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.aod.PluginAODParameter;
import com.android.systemui.plugins.aod.PluginAODSystemUIConfiguration;
import com.android.systemui.plugins.clockpack.PluginClockPack;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

/* loaded from: classes2.dex */
public class DozeService extends DreamService implements DozeMachine.Service, DozeServicePlugin.RequestDoze, PluginListener<Plugin> {
    public static final boolean DEBUG = Log.isLoggable("DozeService", 3);
    public AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public PluginAOD mAODPlugin;
    public final Executor mBgExecutor;
    public PluginClockPack mClockPackPlugin;
    public final DozeComponent.Builder mDozeComponentBuilder;
    public final DozeLog mDozeLog;
    public AODMachine mDozeMachine;
    public DozeServiceHost mDozeServiceHost;
    public Lazy mFaceWidgetManagerLazy;
    public boolean mIsOccluded;
    public boolean mIsUnlockedState;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public Lazy mPluginAODManagerLazy;
    public final DozeService$$ExternalSyntheticLambda1 mPluginConnectionRunnable = new Runnable() { // from class: com.android.systemui.doze.DozeService$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            DozeService dozeService = this.f$0;
            boolean z = DozeService.DEBUG;
            Log.d("DozeService", "addPluginListener() PluginFaceWidget is connected");
            if (dozeService.mDozeMachine != null) {
                if (LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
                    dozeService.mPluginManager.addPluginListener(PluginAOD.ACTION, dozeService, PluginAOD.class, false, true, 0);
                } else {
                    dozeService.mPluginManager.addPluginListener(PluginClockPack.ACTION, dozeService, PluginClockPack.class, false, true, 0);
                }
            }
        }
    };
    public final PluginManager mPluginManager;
    public WakefulnessLifecycle mWakefulnessLifecycle;

    public static /* synthetic */ void $r8$lambda$64Z7k0lpg02PdLzRjwhu7MKpSVg(DozeService dozeService, int i) {
        dozeService.mDozeLog.traceDozeScreenBrightness(i, false);
        super.setDozeScreenBrightness(i);
        dozeService.mDozeLog.traceDozeScreenBrightness(i, true);
    }

    public static /* synthetic */ void $r8$lambda$9rERu2791NWLKgpRaGCQvv57wYg(DozeService dozeService, float f) {
        dozeService.mDozeLog.traceDozeScreenBrightnessFloat(f, false);
        super.setDozeScreenBrightnessFloat(f);
        dozeService.mDozeLog.traceDozeScreenBrightnessFloat(f, true);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.doze.DozeService$$ExternalSyntheticLambda1] */
    public DozeService(DozeComponent.Builder builder, PluginManager pluginManager, DozeLog dozeLog, Executor executor) {
        this.mDozeLog = dozeLog;
        this.mBgExecutor = executor;
        this.mDozeComponentBuilder = builder;
        setDebug(DEBUG);
        this.mPluginManager = pluginManager;
    }

    public final void dumpOnHandler(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dumpOnHandler(fileDescriptor, printWriter, strArr);
        AODMachine aODMachine = this.mDozeMachine;
        if (aODMachine != null) {
            printWriter.print(" state=");
            printWriter.println(aODMachine.mState);
            printWriter.print(" mUiModeType=");
            printWriter.println(aODMachine.mUiModeType);
            printWriter.print(" wakeLockHeldForCurrentState=");
            printWriter.println(aODMachine.mWakeLockHeldForCurrentState);
            printWriter.print(" wakeLock=");
            printWriter.println(aODMachine.mWakeLock);
            printWriter.println("Parts:");
            for (DozeMachine.Part part : aODMachine.mParts) {
                part.dump(printWriter);
            }
        }
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDozeMachine.onConfigurationChanged(configuration);
        PluginAOD pluginAOD = this.mAODPlugin;
        if (pluginAOD != null) {
            pluginAOD.onConfigurationChanged(configuration);
        }
    }

    @Override // android.service.dreams.DreamService, android.app.Service
    public final void onCreate() {
        DozeService dozeService;
        super.onCreate();
        setWindowless(true);
        Log.i("DozeService", "onCreate: " + this);
        Log.d("DozeService", "addPluginListener: called");
        if (!((PluginFaceWidgetManager) this.mFaceWidgetManagerLazy.get()).mIsConnected) {
            dozeService = this;
            Log.w("DozeService", "addPluginListener() PluginFaceWidget is not connected, wait connection");
            ((PluginAODManager) dozeService.mPluginAODManagerLazy.get()).addConnectionRunnable(dozeService.mPluginConnectionRunnable);
        } else if (LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
            dozeService = this;
            this.mPluginManager.addPluginListener(PluginAOD.ACTION, dozeService, PluginAOD.class, false, true, 0);
        } else {
            dozeService = this;
            dozeService.mPluginManager.addPluginListener(PluginClockPack.ACTION, dozeService, PluginClockPack.class, false, true, 0);
        }
        AODMachine aODMachine = ((DaggerReferenceGlobalRootComponent.DozeComponentImpl) dozeService.mDozeComponentBuilder.build(dozeService)).getAODMachine();
        dozeService.mDozeMachine = aODMachine;
        aODMachine.onConfigurationChanged(dozeService.getResources().getConfiguration());
    }

    @Override // android.service.dreams.DreamService, android.app.Service
    public final void onDestroy() {
        PluginManager pluginManager = this.mPluginManager;
        if (pluginManager != null) {
            pluginManager.removePluginListener(this);
        }
        super.onDestroy();
        for (DozeMachine.Part part : this.mDozeMachine.mParts) {
            part.destroy();
        }
        this.mDozeMachine = null;
    }

    @Override // android.service.dreams.DreamService
    public final void onDreamingStarted() {
        boolean z;
        super.onDreamingStarted();
        if (LsRune.AOD_SAFEMODE) {
            return;
        }
        if (this.mDozeMachine == null) {
            Log.d("DozeService", "onDreamingStarted: mAODDozeMachine is null");
            return;
        }
        Log.d("DozeService", "onDreamingStarted: ");
        DozeServiceHost dozeServiceHost = this.mDozeServiceHost;
        if (dozeServiceHost != null) {
            CentralSurfacesImpl centralSurfacesImpl = dozeServiceHost.mCentralSurfaces;
            boolean z2 = true;
            if (centralSurfacesImpl == null) {
                Log.i("DozeServiceHost", "isUnLockedstate() called before initialize(), returning true");
            } else {
                z2 = true ^ ((KeyguardStateControllerImpl) centralSurfacesImpl.mKeyguardStateController).mShowing;
            }
            this.mIsUnlockedState = z2;
            CentralSurfacesImpl centralSurfacesImpl2 = this.mDozeServiceHost.mCentralSurfaces;
            if (centralSurfacesImpl2 == null) {
                Log.i("DozeServiceHost", "isOccludedstate() called before initialize(), returning false");
                z = false;
            } else {
                z = ((KeyguardStateControllerImpl) centralSurfacesImpl2.mKeyguardStateController).mOccluded;
            }
            this.mIsOccluded = z;
        }
        this.mDozeMachine.requestState(DozeMachine.State.INITIALIZED);
        if (LsRune.AOD_BRIGHTNESS_CONTROL) {
            semSetDozeScreenBrightness(65538, -1);
        }
        startDozing();
        if (!LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
            startClockPack();
        } else {
            if (startAlwaysOnDisplay() || this.mAODPlugin == null) {
                return;
            }
            onRequestHideDoze();
        }
    }

    @Override // android.service.dreams.DreamService
    public final void onDreamingStopped() {
        super.onDreamingStopped();
        if (LsRune.AOD_SAFEMODE) {
            return;
        }
        if (this.mDozeMachine == null) {
            Log.d("DozeService", "onDreamingStopped: mDozeMachine is null");
            return;
        }
        Log.d("DozeService", "onDreamingStopped: ");
        this.mDozeMachine.requestState(DozeMachine.State.FINISH);
        if (LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
            stopAlwaysOnDisplay();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginConnected(Plugin plugin, Context context) {
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).initAODOverlayContainer();
        AODMachine aODMachine = this.mDozeMachine;
        if (aODMachine == null) {
            aODMachine = null;
        }
        if (aODMachine == null) {
            Log.d("DozeService", "onPluginConnected: aodMachine is null, plugin=" + plugin);
            return;
        }
        if (plugin instanceof PluginAOD) {
            Log.d("DozeService", "onPluginConnected: PluginAOD plugin=" + plugin);
            this.mAODPlugin = (PluginAOD) plugin;
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).setAODPlugin(this.mAODPlugin);
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).mAODMachine = aODMachine;
            this.mAODPlugin.setAODPluginCallback(aODMachine);
            PluginAODParameter aODParameter = this.mAODPlugin.getAODParameter();
            AODScreenBrightness aODDozeBrightness = aODMachine.getAODDozeBrightness();
            if (aODDozeBrightness != null && aODParameter != null && aODParameter.getSensorToBrightness() != null && aODParameter.getSensorToBrightness().length > 0) {
                aODDozeBrightness.mBrightnessValues = aODParameter.getSensorToBrightness();
            }
            if (canDoze() && isDozing() && !startAlwaysOnDisplay()) {
                onRequestHideDoze();
                return;
            }
            return;
        }
        if (!(plugin instanceof PluginClockPack)) {
            Log.d("DozeService", "onPluginConnected: abnormal plugin=" + plugin);
            onRequestHideDoze();
            return;
        }
        Log.d("DozeService", "onPluginConnected: PluginClockPack plugin=" + plugin);
        this.mClockPackPlugin = (PluginClockPack) plugin;
        PluginAODManager pluginAODManager = (PluginAODManager) this.mPluginAODManagerLazy.get();
        PluginClockPack pluginClockPack = this.mClockPackPlugin;
        pluginAODManager.mClockPackPlugin = pluginClockPack;
        if (pluginClockPack != null) {
            pluginClockPack.setAODUICallback(pluginAODManager.mAODUICallback);
        }
        this.mClockPackPlugin.setAODPluginCallback(aODMachine);
        if (canDoze() && isDozing()) {
            startClockPack();
        }
    }

    @Override // com.android.systemui.plugins.PluginListener
    public final void onPluginDisconnected(Plugin plugin) {
        if (plugin instanceof PluginAOD) {
            Log.d("DozeService", "onPluginDisconnected: PluginAOD plugin=" + plugin);
            DozeServiceHost dozeServiceHost = this.mDozeServiceHost;
            boolean zIsDozing = dozeServiceHost.mStatusBarStateController.isDozing();
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) dozeServiceHost.mNotificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.dozing = zIsDozing;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            stopAlwaysOnDisplay();
            return;
        }
        if (plugin instanceof PluginClockPack) {
            Log.d("DozeService", "onPluginDisconnected: PluginClockPack plugin=" + plugin);
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).mClockPackPlugin = null;
            Log.d("DozeService", "stopClockPack: mClockPackPlugin=" + this.mClockPackPlugin);
            PluginClockPack pluginClockPack = this.mClockPackPlugin;
            if (pluginClockPack != null) {
                pluginClockPack.onDreamingStopped();
                this.mClockPackPlugin = null;
            }
        }
    }

    @Override // com.android.systemui.plugins.DozeServicePlugin.RequestDoze
    public final void onRequestHideDoze() {
        AODMachine aODMachine = this.mDozeMachine;
        if (aODMachine != null) {
            aODMachine.requestState(DozeMachine.State.DOZE);
        }
    }

    @Override // com.android.systemui.plugins.DozeServicePlugin.RequestDoze
    public final void onRequestShowDoze() {
        AODMachine aODMachine = this.mDozeMachine;
        if (aODMachine != null) {
            aODMachine.requestState(DozeMachine.State.DOZE_AOD);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0022  */
    @Override // com.android.systemui.doze.DozeMachine.Service
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void requestWakeUp(int i) {
        PowerManager powerManager = (PowerManager) getSystemService(PowerManager.class);
        long jUptimeMillis = SystemClock.uptimeMillis();
        int i2 = 3;
        if (i == 3) {
            i2 = 16;
        } else if (i == 4) {
            i2 = 15;
        } else if (i != 6) {
            if (i != 9) {
                i2 = i != 10 ? 4 : 17;
            }
        }
        powerManager.wakeUp(jUptimeMillis, i2, "com.android.systemui:NODOZE ".concat(DozeLog.reasonToString(i)));
    }

    @Override // com.android.systemui.doze.DozeMachine.Service
    public final void setDozeScreenBrightness(final int i) {
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.doze.DozeService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DozeService.$r8$lambda$64Z7k0lpg02PdLzRjwhu7MKpSVg(this.f$0, i);
            }
        });
    }

    @Override // com.android.systemui.doze.DozeMachine.Service
    public final void setDozeScreenBrightnessFloat(final float f) {
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.doze.DozeService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                DozeService.$r8$lambda$9rERu2791NWLKgpRaGCQvv57wYg(this.f$0, f);
            }
        });
    }

    public final void setDozeScreenState(int i) {
        this.mDozeLog.traceDisplayState(i, false);
        super.setDozeScreenState(i);
        this.mDozeLog.traceDisplayState(i, true);
        AODMachine aODMachine = this.mDozeMachine;
        if (aODMachine != null) {
            for (DozeMachine.Part part : aODMachine.mParts) {
                part.onScreenState(i);
            }
        }
    }

    public final boolean startAlwaysOnDisplay() {
        StringBuilder sb = new StringBuilder("startAlwaysOnDisplay: mAODPlugin=");
        sb.append(this.mAODPlugin);
        sb.append(" unlockedState=");
        sb.append(this.mIsUnlockedState);
        sb.append(" occlude=");
        sb.append(this.mIsOccluded);
        sb.append(" screenOffMemoRunning=");
        sb.append(this.mKeyguardUpdateMonitor.isScreenOffMemoRunning());
        sb.append(" bouncerFullyShown=");
        sb.append(this.mKeyguardUpdateMonitor.isBouncerFullyShown());
        sb.append(" aodFullscreenMode=");
        sb.append(this.mAODAmbientWallpaperHelper.isAODFullScreenMode());
        sb.append(" shouldControlUnlockedScreenOff=");
        sb.append(((PluginAODManager) this.mPluginAODManagerLazy.get()).mDozeParameters.mUnlockedScreenOffAnimationController.shouldPlayUnlockedScreenOffAnimation());
        sb.append(" shouldControlScreenOff()=");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, ((PluginAODManager) this.mPluginAODManagerLazy.get()).mDozeParameters.mControlScreenOffAnimation, "DozeService");
        if (this.mAODPlugin == null) {
            return false;
        }
        PluginAODSystemUIConfiguration pluginAODSystemUIConfiguration = ((PluginAODManager) this.mPluginAODManagerLazy.get()).mSysUIConfig;
        if (LsRune.AOD_FULLSCREEN && this.mAODAmbientWallpaperHelper.isAODFullScreenMode() && ((PluginAODManager) this.mPluginAODManagerLazy.get()).mDozeParameters.mUnlockedScreenOffAnimationController.shouldPlayUnlockedScreenOffAnimation()) {
            pluginAODSystemUIConfiguration.set(4, false);
            pluginAODSystemUIConfiguration.set(5, false);
        } else {
            pluginAODSystemUIConfiguration.set(4, this.mIsUnlockedState);
            pluginAODSystemUIConfiguration.set(5, this.mIsOccluded);
        }
        pluginAODSystemUIConfiguration.set(6, this.mKeyguardUpdateMonitor.isScreenOffMemoRunning());
        pluginAODSystemUIConfiguration.set(7, this.mKeyguardUpdateMonitor.isBouncerFullyShown());
        pluginAODSystemUIConfiguration.set(9, ((PluginAODManager) this.mPluginAODManagerLazy.get()).mDozeParameters.mControlScreenOffAnimation);
        pluginAODSystemUIConfiguration.set(10, this.mWakefulnessLifecycle.mLastSleepReason);
        NotificationShadeWindowView notificationPanelView = this.mDozeServiceHost.getNotificationPanelView();
        if (LsRune.AOD_SUB_DISPLAY_AOD_BY_FOLDER_EVENT) {
            if (((PluginAODManager) this.mPluginAODManagerLazy.get()).mStartedByFolderClosed) {
                pluginAODSystemUIConfiguration.set(3, 1);
                ((PluginAODManager) this.mPluginAODManagerLazy.get()).setStartedByFolderClosed(false);
            } else {
                pluginAODSystemUIConfiguration.set(3, 0);
            }
        }
        return this.mAODPlugin.onDreamingStarted(notificationPanelView, pluginAODSystemUIConfiguration);
    }

    public final void startClockPack() {
        Log.d("DozeService", "startClockPack: mClockPackPlugin=" + this.mClockPackPlugin);
        if (this.mClockPackPlugin != null) {
            this.mClockPackPlugin.onDreamingStarted(this.mDozeServiceHost.getNotificationPanelView(), ((PluginAODManager) this.mPluginAODManagerLazy.get()).mSysUIConfig);
        }
    }

    public final void stopAlwaysOnDisplay() {
        Log.d("DozeService", "stopAlwaysOnDisplay: mAODPlugin=" + this.mAODPlugin);
        if (this.mAODPlugin != null) {
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).setAODPlugin(null);
            this.mAODPlugin.onDreamingStopped();
            this.mAODPlugin = null;
        }
    }
}
