package com.android.systemui.keyguard;

import android.content.Context;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.view.Display;
import android.view.SurfaceControl;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda4;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.uithreadmonitor.BinderCallMonitor;
import com.android.systemui.uithreadmonitor.BinderCallMonitorImpl;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.os.SemDvfsManager;
import com.sec.ims.settings.ImsProfile;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public class KeyguardFastBioUnlockController implements MessageQueue.IdleHandler {
    public static final int BOOSTER_HINT;
    public static final int BOOSTER_TIMEOUT;
    public static final Companion Companion = new Companion(null);
    public static final boolean DEBUG;
    public static final int MODE_FLAG_ENABLED;
    public static final int MODE_FLAG_FRAME_COMMIT;
    public static final int MODE_FLAG_FRAME_REQUEST;
    public static final int MODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF;
    public static final int MODE_FLAG_STARTED_DISPLAY_ON;
    public static final int MODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN;
    public static final int[] sFlags;
    public static final String[] sFlagsStr;
    public final AODAmbientWallpaperHelper aodAmbientWallpaperHelper;
    public final BinderCallMonitor binderCallMonitor;
    public BiometricSourceType biometricSourceType;
    public final Lazy biometricUnlockControllerLazy;
    public final Lazy centralSurfacesLazy;
    public final Context context;
    public boolean curIsAodBrighterThanNormal;
    public VisibilityController curVisibilityController;
    public final Display defaultDisplay;
    public DelayedActionParams delayedActionParams;
    public final DisplayTracker displayTracker;
    public SemDvfsManager dvfsManager;
    public long goingAwayTime;
    public boolean isBrightnessChangedCallbackRegistered;
    public boolean isInvisibleAfterGoingAwayTransStarted;
    public final Lazy looperSlowLogControllerLazy;
    public final Handler mainHandler;
    public boolean needsBlankScreen;
    public KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda25 reservedKeyguardGoingAway;
    public final ScreenLifecycle screenLifecycle;
    public final Lazy scrimControllerLazy;
    public CentralSurfacesImpl$$ExternalSyntheticLambda4 scrimUpdater;
    private final SettingsHelper settingsHelper;
    public long startKeyguardExitAnimationTime;
    public final SysuiStatusBarStateController statusBarStateController;
    public final SurfaceVisibilityController surfaceVisibilityController;
    public final Lazy updateMonitorLazy;
    public final Lazy viewMediatorHelperLazy;
    public final Lazy visibilityMonitorLazy;
    public long waitStartTime;
    public final WakefulnessLifecycle wakefulnessLifecycle;
    public final WindowVisibilityController windowVisibilityController;
    public final boolean bioUnlockBoosterEnabled = LsRune.KEYGUARD_PERFORMANCE_BIO_UNLOCK_BOOSTER;
    public int curMode = 0;
    public final int scrimVisibility = -1;
    public final KeyguardFastBioUnlockController$resetRunnable$1 resetRunnable = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$resetRunnable$1
        @Override // java.lang.Runnable
        public final void run() {
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.$tmp0;
            if (KeyguardFastBioUnlockController.DEBUG) {
                KeyguardFastBioUnlockController.Companion companion = KeyguardFastBioUnlockController.Companion;
                int mode = keyguardFastBioUnlockController.getMode();
                companion.getClass();
                KeyguardFastBioUnlockController.logD("onReset " + KeyguardFastBioUnlockController.Companion.getModeString(mode));
            } else {
                KeyguardFastBioUnlockController.logD("onReset 0x" + Integer.toHexString(keyguardFastBioUnlockController.getMode()));
            }
            keyguardFastBioUnlockController.reset();
        }
    };
    public final ExecutorService executor = Executors.newSingleThreadExecutor(new ThreadFactory() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$executor$1
        @Override // java.util.concurrent.ThreadFactory
        public final Thread newThread(Runnable runnable) {
            return new Thread(runnable, "BioUnlock");
        }
    });
    public final List pendingRunnableList = new ArrayList();
    public final KeyguardFastBioUnlockController$visibilityChangedListener$1 visibilityChangedListener = new IntConsumer() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$visibilityChangedListener$1
        @Override // java.util.function.IntConsumer
        public final void accept(int i) {
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.$tmp0;
            if (i == 4 && keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) {
                keyguardFastBioUnlockController.reset();
            }
            KeyguardViewMediatorHelperImpl$$ExternalSyntheticLambda25 keyguardViewMediatorHelperImpl$$ExternalSyntheticLambda25 = keyguardFastBioUnlockController.reservedKeyguardGoingAway;
            if (keyguardViewMediatorHelperImpl$$ExternalSyntheticLambda25 != null && keyguardFastBioUnlockController.isFastWakeAndUnlockMode() && i == 8) {
                Log.i("BioUnlock", "onWindowVisibilityChanged keyguardGoingAway");
                keyguardViewMediatorHelperImpl$$ExternalSyntheticLambda25.invoke();
                keyguardFastBioUnlockController.reservedKeyguardGoingAway = null;
            }
        }
    };
    public final KeyguardFastBioUnlockController$brightnessChangedCallback$1 brightnessChangedCallback = new DisplayTracker.Callback() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$brightnessChangedCallback$1
        @Override // com.android.systemui.settings.DisplayTracker.Callback
        public final void onDisplayChanged(int i) {
            BrightnessInfo brightnessInfo;
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.this$0;
            if (i != 0) {
                KeyguardFastBioUnlockController.Companion companion = KeyguardFastBioUnlockController.Companion;
                keyguardFastBioUnlockController.getClass();
                return;
            }
            int state = keyguardFastBioUnlockController.defaultDisplay.getState();
            boolean z = false;
            if (state == 2 || state == 3 || state == 4) {
                brightnessInfo = keyguardFastBioUnlockController.defaultDisplay.getBrightnessInfo();
                if (brightnessInfo != null) {
                    float f = brightnessInfo.brightness;
                    if (f >= 0.0f) {
                        float f2 = brightnessInfo.adjustedBrightness;
                        if (f2 >= 0.0f && f < f2) {
                            z = true;
                        }
                    }
                }
            } else {
                brightnessInfo = null;
            }
            KeyguardFastBioUnlockController$updateBrightnessRunnable$1 keyguardFastBioUnlockController$updateBrightnessRunnable$1 = keyguardFastBioUnlockController.updateBrightnessRunnable;
            keyguardFastBioUnlockController$updateBrightnessRunnable$1.displayState = state;
            keyguardFastBioUnlockController$updateBrightnessRunnable$1.brightness = brightnessInfo != null ? brightnessInfo.brightness : -1.0f;
            keyguardFastBioUnlockController$updateBrightnessRunnable$1.adjustedBrightness = brightnessInfo != null ? brightnessInfo.adjustedBrightness : -1.0f;
            keyguardFastBioUnlockController$updateBrightnessRunnable$1.isAodBrightThanNormal = z;
            Handler handler = keyguardFastBioUnlockController.mainHandler;
            if (handler.hasCallbacks(keyguardFastBioUnlockController$updateBrightnessRunnable$1)) {
                handler.removeCallbacks(keyguardFastBioUnlockController$updateBrightnessRunnable$1);
            }
            keyguardFastBioUnlockController.mainHandler.post(keyguardFastBioUnlockController$updateBrightnessRunnable$1);
        }
    };
    public final KeyguardFastBioUnlockController$updateBrightnessRunnable$1 updateBrightnessRunnable = new KeyguardFastBioUnlockController$updateBrightnessRunnable$1(this);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final String access$getTraceString(Companion companion, int i) {
            companion.getClass();
            return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("BioUnlock_", getModeString(i));
        }

        public static String getModeString(int i) {
            if (i == 0) {
                return "CLEAR";
            }
            StringBuilder sb = new StringBuilder();
            int[] iArr = KeyguardFastBioUnlockController.sFlags;
            int length = iArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = iArr[i2];
                if ((i3 & i) == i3) {
                    if (i2 != 0) {
                        sb.append('|');
                    }
                    sb.append(KeyguardFastBioUnlockController.sFlagsStr[i2]);
                }
            }
            return sb.toString();
        }

        private Companion() {
        }

        public static /* synthetic */ void getMODE_CLEAR$annotations() {
        }

        public static /* synthetic */ void getMODE_FLAG_ENABLED$annotations() {
        }

        public static /* synthetic */ void getMODE_FLAG_FRAME_COMMIT$annotations() {
        }

        public static /* synthetic */ void getMODE_FLAG_FRAME_REQUEST$annotations() {
        }

        public static /* synthetic */ void getMODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF$annotations() {
        }

        public static /* synthetic */ void getMODE_FLAG_STARTED_DISPLAY_ON$annotations() {
        }

        public static /* synthetic */ void getMODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN$annotations() {
        }
    }

    public final class DelayedActionParams {
        public final Function0 action;
        public long atTime;
        public final Handler handler;
        public boolean isDiscard;
        public final long maxDelayMills;
        public final KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 runnableWrapper = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams = this.this$0;
                if (delayedActionParams.isDiscard) {
                    return;
                }
                delayedActionParams.action.invoke();
                KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams2 = this.this$0;
                Handler handler = delayedActionParams2.handler;
                KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 = delayedActionParams2.runnableWrapper;
                if (handler.hasCallbacks(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1)) {
                    handler.removeCallbacks(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1);
                }
                delayedActionParams2.isDiscard = true;
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1] */
        public DelayedActionParams(Handler handler, Function0 function0, long j) {
            this.handler = handler;
            this.action = function0;
            this.maxDelayMills = j;
        }

        public final void start(boolean z) {
            if (this.isDiscard) {
                return;
            }
            Log.d("BioUnlock", "start " + z);
            KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 = this.runnableWrapper;
            Handler handler = this.handler;
            if (!z) {
                handler.post(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1);
                return;
            }
            long jUptimeMillis = SystemClock.uptimeMillis();
            long j = this.maxDelayMills;
            this.atTime = jUptimeMillis + j;
            handler.postDelayed(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1, j);
        }
    }

    public final class Task implements Runnable {
        public final Runnable runnable;
        public final long startTime = System.currentTimeMillis();
        public final String tag;

        public Task(Runnable runnable, String str) {
            this.runnable = runnable;
            this.tag = str;
        }

        @Override // java.lang.Runnable
        public final void run() {
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = jCurrentTimeMillis - this.startTime;
            this.runnable.run();
            long jCurrentTimeMillis2 = System.currentTimeMillis() - jCurrentTimeMillis;
            if (j >= 3 || jCurrentTimeMillis2 >= 3) {
                Log.d("BioUnlock", "** " + this.tag + " run dur=" + jCurrentTimeMillis2 + "ms, delivery=" + j + "ms");
            }
        }
    }

    static {
        int debugLevel;
        DEBUG = !DeviceType.isShipBuild() || (debugLevel = DeviceType.getDebugLevel()) == DeviceType.DEBUG_LEVEL_MID || debugLevel == DeviceType.DEBUG_LEVEL_HIGH;
        MODE_FLAG_ENABLED = 32;
        MODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN = 16;
        MODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF = 8;
        MODE_FLAG_STARTED_DISPLAY_ON = 4;
        MODE_FLAG_FRAME_REQUEST = 2;
        MODE_FLAG_FRAME_COMMIT = 1;
        BOOSTER_HINT = 3101;
        BOOSTER_TIMEOUT = 1000;
        sFlags = new int[]{32, 16, 8, 4, 2, 1};
        sFlagsStr = new String[]{ImsProfile.TIMER_NAME_E, "UNLOCK_ANIM_AOD_FULLSCREEN", "OFF", "ON", "F_REQ", "F_COMMIT"};
    }

    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.keyguard.KeyguardFastBioUnlockController$resetRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.keyguard.KeyguardFastBioUnlockController$visibilityChangedListener$1] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.keyguard.KeyguardFastBioUnlockController$brightnessChangedCallback$1] */
    public KeyguardFastBioUnlockController(Handler handler, Context context, DisplayManager displayManager, DisplayTracker displayTracker, BinderCallMonitor binderCallMonitor, SettingsHelper settingsHelper, SysuiStatusBarStateController sysuiStatusBarStateController, SurfaceVisibilityController surfaceVisibilityController, WindowVisibilityController windowVisibilityController, WakefulnessLifecycle wakefulnessLifecycle, ScreenLifecycle screenLifecycle, Lazy lazy, AODAmbientWallpaperHelper aODAmbientWallpaperHelper, Lazy lazy2, Lazy lazy3, Lazy lazy4, Lazy lazy5, Lazy lazy6, Lazy lazy7) {
        this.mainHandler = handler;
        this.context = context;
        this.displayTracker = displayTracker;
        this.binderCallMonitor = binderCallMonitor;
        this.settingsHelper = settingsHelper;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.surfaceVisibilityController = surfaceVisibilityController;
        this.windowVisibilityController = windowVisibilityController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.screenLifecycle = screenLifecycle;
        this.visibilityMonitorLazy = lazy;
        this.aodAmbientWallpaperHelper = aODAmbientWallpaperHelper;
        this.updateMonitorLazy = lazy2;
        this.centralSurfacesLazy = lazy3;
        this.looperSlowLogControllerLazy = lazy4;
        this.biometricUnlockControllerLazy = lazy5;
        this.viewMediatorHelperLazy = lazy6;
        this.scrimControllerLazy = lazy7;
        this.defaultDisplay = displayManager.getDisplay(0);
    }

    public static void logD(String str) {
        Log.d("BioUnlock", str);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void calculateMode(int i) {
        boolean z = ((KeyguardStateControllerImpl) ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mKeyguardStateController).mShowing;
        boolean z2 = ((KeyguardStateControllerImpl) ((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mKeyguardStateController).mOccluded;
        boolean zIsEnabledBiometricUnlockVI = this.settingsHelper.isEnabledBiometricUnlockVI();
        if (((StatusBarStateControllerImpl) this.statusBarStateController).mLeaveOpenOnKeyguardHide) {
            logD("leaveOpenOnKeyguardHide true");
        } else if (!((CentralSurfacesImpl) ((CentralSurfaces) this.centralSurfacesLazy.get())).mShadeSurface.canBeCollapsed()) {
            Log.w("BioUnlock", "canBeCollapsed false");
        } else if (z && !z2) {
            if (i == 1 || i == 2) {
                if (!LsRune.SECURITY_FINGERPRINT_HOME || this.settingsHelper.isEnabledWof()) {
                    setWakeAndUnlock(true);
                    if (zIsEnabledBiometricUnlockVI) {
                        this.isInvisibleAfterGoingAwayTransStarted = true;
                    }
                    boolean z3 = this.settingsHelper.isEnabledBiometricUnlockVI() ? false : this.curIsAodBrighterThanNormal;
                    if ((!z3 || (LsRune.AOD_FULLSCREEN && this.settingsHelper.isAODShown() && this.aodAmbientWallpaperHelper.isAODFullScreenMode())) && this.screenLifecycle.mScreenState != 0) {
                        this.curVisibilityController = this.surfaceVisibilityController;
                        this.isInvisibleAfterGoingAwayTransStarted = true;
                    } else {
                        this.curVisibilityController = this.windowVisibilityController;
                        if (z3) {
                            this.needsBlankScreen = true;
                        }
                    }
                }
            } else if (i != 5) {
                if (i == 6) {
                }
            } else if (!zIsEnabledBiometricUnlockVI) {
                setWakeAndUnlock(false);
                this.curVisibilityController = this.surfaceVisibilityController;
                this.isInvisibleAfterGoingAwayTransStarted = true;
            }
        }
        VisibilityController visibilityController = this.curVisibilityController;
        if (visibilityController != null) {
            logD("current controller: ".concat(visibilityController.getClass().getSimpleName()));
        }
        boolean zIsFastWakeAndUnlockMode = isFastWakeAndUnlockMode();
        if (zIsFastWakeAndUnlockMode || isFastUnlockMode()) {
            logD("waitGoingAwayTrans=" + this.isInvisibleAfterGoingAwayTransStarted + " needsBlank=" + (zIsFastWakeAndUnlockMode && this.needsBlankScreen) + " ssd=false");
        } else {
            StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("not supported mode=", i, ", animation=", zIsEnabledBiometricUnlockVI, ", showing=");
            sbM.append(z);
            sbM.append(", occluded=");
            sbM.append(z2);
            logD(sbM.toString());
            reset();
        }
        if (this.isBrightnessChangedCallbackRegistered) {
            logD("unregisterBrightnessListener");
            ((DisplayTrackerImpl) this.displayTracker).removeCallback(this.brightnessChangedCallback);
            this.isBrightnessChangedCallbackRegistered = false;
        }
    }

    public final int getMode() {
        int i;
        synchronized (this) {
            i = this.curMode;
        }
        return i;
    }

    public final boolean isFastUnlockMode() {
        return isMode(MODE_FLAG_ENABLED | MODE_FLAG_STARTED_DISPLAY_ON);
    }

    public final boolean isFastWakeAndUnlockMode() {
        return isMode(MODE_FLAG_ENABLED | MODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF);
    }

    public final boolean isMode(int i) {
        return (getMode() & i) == i;
    }

    public final boolean isWakeAndUnlockAnimationAODFullScreenMode() {
        return isMode(MODE_FLAG_ENABLED | MODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN);
    }

    public final void logLapTime(String str, Object... objArr) {
        if (isMode(MODE_FLAG_ENABLED)) {
            LogUtil.lapTime(10000, "BioUnlock", str, Arrays.copyOf(objArr, objArr.length));
        }
    }

    @Override // android.os.MessageQueue.IdleHandler
    public final boolean queueIdle() {
        logD("idle state");
        return false;
    }

    public final void reset() {
        SemDvfsManager semDvfsManager;
        CentralSurfacesImpl$$ExternalSyntheticLambda4 centralSurfacesImpl$$ExternalSyntheticLambda4;
        KeyguardFastBioUnlockController$resetRunnable$1 keyguardFastBioUnlockController$resetRunnable$1 = this.resetRunnable;
        Handler handler = this.mainHandler;
        if (handler.hasCallbacks(keyguardFastBioUnlockController$resetRunnable$1)) {
            handler.removeCallbacks(keyguardFastBioUnlockController$resetRunnable$1);
        }
        boolean zIsMode = isMode(MODE_FLAG_ENABLED);
        if (zIsMode) {
            ((ArrayList) ((KeyguardVisibilityMonitor) this.visibilityMonitorLazy.get()).visibilityChangedListeners).remove(this.visibilityChangedListener);
            runPendingRunnable();
            VisibilityController visibilityController = this.curVisibilityController;
            if (visibilityController != null) {
                visibilityController.resetForceInvisible(false);
            }
            LogUtil.endTime(10000, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController.reset.1
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController = KeyguardFastBioUnlockController.this;
                    Companion companion = KeyguardFastBioUnlockController.Companion;
                    keyguardFastBioUnlockController.getClass();
                    KeyguardFastBioUnlockController.logD("reset / elapsed time: " + j + "ms");
                }
            });
            if (Rune.SYSUI_UI_THREAD_MONITOR) {
                ((LooperSlowLogControllerImpl) ((LooperSlowLogController) this.looperSlowLogControllerLazy.get())).disable(1);
            }
        }
        setMode(0);
        this.biometricSourceType = null;
        if (zIsMode && (centralSurfacesImpl$$ExternalSyntheticLambda4 = this.scrimUpdater) != null && this.scrimVisibility != 0) {
            centralSurfacesImpl$$ExternalSyntheticLambda4.run();
        }
        if (this.bioUnlockBoosterEnabled && (semDvfsManager = this.dvfsManager) != null) {
            semDvfsManager.release();
        }
        this.goingAwayTime = 0L;
        this.startKeyguardExitAnimationTime = 0L;
        this.curVisibilityController = null;
        this.needsBlankScreen = false;
        this.curIsAodBrighterThanNormal = false;
        this.isInvisibleAfterGoingAwayTransStarted = false;
    }

    public final void runPendingRunnable() {
        if (((ArrayList) this.pendingRunnableList).isEmpty()) {
            return;
        }
        ArrayList arrayList = (ArrayList) this.pendingRunnableList;
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            ((Runnable) obj).run();
            i++;
        }
        ((ArrayList) this.pendingRunnableList).clear();
        if (DEBUG) {
            logD("runPendingRunnable executed: " + i);
        }
    }

    public final void setEnabled() {
        SemDvfsManager semDvfsManagerCreateInstance;
        KeyguardFastBioUnlockController$resetRunnable$1 keyguardFastBioUnlockController$resetRunnable$1 = this.resetRunnable;
        Handler handler = this.mainHandler;
        if (handler.hasCallbacks(keyguardFastBioUnlockController$resetRunnable$1)) {
            handler.removeCallbacks(keyguardFastBioUnlockController$resetRunnable$1);
        }
        ((ArrayList) this.pendingRunnableList).clear();
        setMode(MODE_FLAG_ENABLED);
        LogUtil.startTime(10000);
        logD("setEnabled");
        if (Rune.SYSUI_UI_THREAD_MONITOR) {
            ((LooperSlowLogControllerImpl) ((LooperSlowLogController) this.looperSlowLogControllerLazy.get())).enable(1, 10L, 20L, 0L, false, null);
        }
        if (Rune.SYSUI_BINDER_CALL_MONITOR) {
            ((BinderCallMonitorImpl) this.binderCallMonitor).startMonitoring(3, 3L, 3000L);
        }
        this.mainHandler.postDelayed(this.resetRunnable, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
        MessageQueue queue = this.mainHandler.getLooper().getQueue();
        queue.removeIdleHandler(this);
        queue.addIdleHandler(this);
        if (this.bioUnlockBoosterEnabled) {
            if (this.dvfsManager == null && (semDvfsManagerCreateInstance = SemDvfsManager.createInstance(this.context, "KEYGUARD_BIO_UNLOCK")) != null) {
                int i = BOOSTER_HINT;
                if (semDvfsManagerCreateInstance.checkHintSupported(i)) {
                    this.dvfsManager = semDvfsManagerCreateInstance;
                    semDvfsManagerCreateInstance.setHint(i);
                }
            }
            SemDvfsManager semDvfsManager = this.dvfsManager;
            if (semDvfsManager != null) {
                semDvfsManager.acquire(BOOSTER_TIMEOUT);
            }
        }
        ((KeyguardVisibilityMonitor) this.visibilityMonitorLazy.get()).addVisibilityChangedListener(this.visibilityChangedListener);
    }

    public final void setForceInvisible(SurfaceControl.Transaction transaction) {
        VisibilityController visibilityController = this.curVisibilityController;
        if (visibilityController != null && visibilityController.setForceInvisible(transaction) && isMode(MODE_FLAG_ENABLED)) {
            final Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    final KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.f$0;
                    if (keyguardFastBioUnlockController.curVisibilityController != null) {
                        keyguardFastBioUnlockController.setMode(keyguardFastBioUnlockController.getMode() | KeyguardFastBioUnlockController.MODE_FLAG_FRAME_REQUEST);
                        keyguardFastBioUnlockController.waitStartTime = System.nanoTime();
                        VisibilityController visibilityController2 = keyguardFastBioUnlockController.curVisibilityController;
                        if (visibilityController2 != null) {
                            visibilityController2.registerFrameUpdateCallback(new KeyguardFastBioUnlockController$onFrameRequest$1(keyguardFastBioUnlockController));
                        }
                        LogUtil.lapTime(10000, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$onFrameRequest$2
                            @Override // java.util.function.LongConsumer
                            public final void accept(long j) {
                                KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = keyguardFastBioUnlockController;
                                KeyguardFastBioUnlockController.Companion companion = KeyguardFastBioUnlockController.Companion;
                                keyguardFastBioUnlockController2.getClass();
                                KeyguardFastBioUnlockController.logD("waiting for frame drawn / lap time: " + j + "ms");
                            }
                        });
                        if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
                            int state = keyguardFastBioUnlockController.defaultDisplay.getState();
                            KeyguardFastBioUnlockController.logD("onFrameRequest displayState=" + state);
                            VisibilityController visibilityController3 = keyguardFastBioUnlockController.curVisibilityController;
                            if (visibilityController3 != null && (state == 1 || state == 3 || state == 4)) {
                                visibilityController3.invalidate();
                            }
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            if (Looper.getMainLooper().isCurrentThread()) {
                function0.invoke();
            } else {
                this.mainHandler.post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$sam$java_lang_Runnable$0
                    @Override // java.lang.Runnable
                    public final /* synthetic */ void run() {
                        function0.invoke();
                    }
                });
            }
        }
    }

    public final void setMode(int i) {
        synchronized (this) {
            int i2 = this.curMode;
            if (i2 == i) {
                return;
            }
            this.curMode = i;
            Unit unit = Unit.INSTANCE;
            if (DEBUG) {
                String hexString = Integer.toHexString(i2);
                Companion.getClass();
                String modeString = Companion.getModeString(i2);
                String hexString2 = Integer.toHexString(i);
                String modeString2 = Companion.getModeString(i);
                StringBuilder sbM = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("setMode 0x", hexString, "(", modeString, ") -> 0x");
                sbM.append(hexString2);
                sbM.append("(");
                sbM.append(modeString2);
                sbM.append(")");
                logD(sbM.toString());
            } else {
                logD("setMode 0x" + Integer.toHexString(i2) + " -> 0x" + Integer.toHexString(i));
            }
            if (LogUtil.isTraceEnabled()) {
                int i3 = this.curMode;
                if (i3 == MODE_FLAG_ENABLED) {
                    LogUtil.traceBegin(Companion.access$getTraceString(Companion, i3), 0);
                } else {
                    if (i3 == 0) {
                        LogUtil.traceEnd(Companion.access$getTraceString(Companion, i2), 0);
                        return;
                    }
                    Companion companion = Companion;
                    LogUtil.traceEnd(Companion.access$getTraceString(companion, i2), 0);
                    LogUtil.traceBegin(Companion.access$getTraceString(companion, this.curMode), 0);
                }
            }
        }
    }

    public final void setWakeAndUnlock(boolean z) {
        if (isMode(MODE_FLAG_ENABLED)) {
            setMode((z ? (LsRune.AOD_FULLSCREEN && this.aodAmbientWallpaperHelper.isAODFullScreenAndShowing() && this.settingsHelper.isEnabledBiometricUnlockVI()) ? MODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF | MODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN : MODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF : MODE_FLAG_STARTED_DISPLAY_ON) | getMode());
        }
    }

    public static /* synthetic */ void getMode$annotations() {
    }

    public static /* synthetic */ void isEnabledBioUnlockBooster$annotations() {
    }
}
