package com.android.systemui.doze;

import android.content.Context;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.os.SystemClock;
import android.service.dreams.DreamService;
import android.util.Log;
import android.view.Display;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.LsRune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
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
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class DozeService extends DreamService implements DozeMachine.Service, DozeServicePlugin.RequestDoze, PluginListener<Plugin> {
    public static final boolean DEBUG = Log.isLoggable("DozeService", 3);
    public AODAmbientWallpaperHelper mAODAmbientWallpaperHelper;
    public PluginAOD mAODPlugin;
    public PluginClockPack mClockPackPlugin;
    public final DozeComponent.Builder mDozeComponentBuilder;
    public AODMachine mDozeMachine;
    public DozeServiceHost mDozeServiceHost;
    public Lazy mFaceWidgetManagerLazy;
    public boolean mIsOccluded;
    public boolean mIsUnlockedState;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public Lazy mPluginAODManagerLazy;
    public final DozeService$$ExternalSyntheticLambda0 mPluginConnectionRunnable = new Runnable() { // from class: com.android.systemui.doze.DozeService$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            DozeService dozeService = DozeService.this;
            boolean z = DozeService.DEBUG;
            dozeService.getClass();
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

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.doze.DozeService$$ExternalSyntheticLambda0] */
    public DozeService(DozeComponent.Builder builder, PluginManager pluginManager) {
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
        super.onCreate();
        setWindowless(true);
        Log.i("DozeService", "onCreate: " + this);
        Log.d("DozeService", "addPluginListener: called");
        if (!((PluginFaceWidgetManager) this.mFaceWidgetManagerLazy.get()).mIsConnected) {
            Log.w("DozeService", "addPluginListener() PluginFaceWidget is not connected, wait connection");
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).addConnectionRunnable(this.mPluginConnectionRunnable);
        } else if (LsRune.LOCKUI_AOD_PACKAGE_AVAILABLE) {
            this.mPluginManager.addPluginListener(PluginAOD.ACTION, this, PluginAOD.class, false, true, 0);
        } else {
            this.mPluginManager.addPluginListener(PluginClockPack.ACTION, this, PluginClockPack.class, false, true, 0);
        }
        AODMachine aODMachine = this.mDozeComponentBuilder.build(this).getAODMachine();
        this.mDozeMachine = aODMachine;
        aODMachine.onConfigurationChanged(getResources().getConfiguration());
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
            CentralSurfaces centralSurfaces = dozeServiceHost.mCentralSurfaces;
            boolean z2 = true;
            if (centralSurfaces == null) {
                Log.i("DozeServiceHost", "isUnLockedstate() called before initialize(), returning true");
            } else {
                z2 = true ^ ((KeyguardStateControllerImpl) ((CentralSurfacesImpl) centralSurfaces).mKeyguardStateController).mShowing;
            }
            this.mIsUnlockedState = z2;
            CentralSurfaces centralSurfaces2 = this.mDozeServiceHost.mCentralSurfaces;
            if (centralSurfaces2 == null) {
                Log.i("DozeServiceHost", "isOccludedstate() called before initialize(), returning false");
                z = false;
            } else {
                z = ((KeyguardStateControllerImpl) ((CentralSurfacesImpl) centralSurfaces2).mKeyguardStateController).mOccluded;
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
            boolean z = ((StatusBarStateControllerImpl) dozeServiceHost.mStatusBarStateController).mIsDozing;
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) dozeServiceHost.mNotificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            notificationShadeWindowState.dozing = z;
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

    @Override // com.android.systemui.doze.DozeMachine.Service
    public final void requestWakeUp(int i) {
        PowerManager powerManager = (PowerManager) getSystemService(PowerManager.class);
        long uptimeMillis = SystemClock.uptimeMillis();
        int i2 = 3;
        if (i != 3) {
            if (i != 4) {
                if (i != 6) {
                    if (i != 9) {
                        i2 = i != 10 ? 4 : 17;
                    }
                }
            }
            i2 = 15;
        } else {
            i2 = 16;
        }
        powerManager.wakeUp(uptimeMillis, i2, "com.android.systemui:NODOZE ".concat(DozeLog.reasonToString(i)));
    }

    public final void setDozeScreenState(int i) {
        super.setDozeScreenState(i);
        AODMachine aODMachine = this.mDozeMachine;
        if (aODMachine != null) {
            DozeLogger dozeLogger = aODMachine.mDozeLog.mLogger;
            dozeLogger.getClass();
            LogLevel logLevel = LogLevel.INFO;
            DozeLogger$logDisplayStateChanged$2 dozeLogger$logDisplayStateChanged$2 = new Function1() { // from class: com.android.systemui.doze.DozeLogger$logDisplayStateChanged$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("Display state changed to ", ((LogMessage) obj).getStr1());
                }
            };
            LogBuffer logBuffer = dozeLogger.buffer;
            LogMessage obtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$logDisplayStateChanged$2, null);
            ((LogMessageImpl) obtain).str1 = Display.stateToString(i);
            logBuffer.commit(obtain);
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
