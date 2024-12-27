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
import com.android.systemui.LsRune;
import com.android.systemui.Rune;
import com.android.systemui.aod.AODAmbientWallpaperHelper;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.uithreadmonitor.BinderCallMonitor;
import com.android.systemui.uithreadmonitor.BinderCallMonitorImpl;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.os.SemDvfsManager;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardFastBioUnlockController implements MessageQueue.IdleHandler {
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
    public Function0 reservedKeyguardGoingAway;
    public final ScreenLifecycle screenLifecycle;
    public final Lazy scrimControllerLazy;
    public Runnable scrimUpdater;
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
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = KeyguardFastBioUnlockController.this;
            if (KeyguardFastBioUnlockController.DEBUG) {
                KeyguardFastBioUnlockController.Companion companion = KeyguardFastBioUnlockController.Companion;
                int mode = keyguardFastBioUnlockController.getMode();
                companion.getClass();
                KeyguardFastBioUnlockController.logD("onReset ".concat(KeyguardFastBioUnlockController.Companion.getModeString(mode)));
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
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = KeyguardFastBioUnlockController.this;
            if (i == 4 && keyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) {
                keyguardFastBioUnlockController.reset();
            }
            Function0 function0 = keyguardFastBioUnlockController.reservedKeyguardGoingAway;
            if (function0 != null && keyguardFastBioUnlockController.isFastWakeAndUnlockMode() && i == 8) {
                Log.i("BioUnlock", "onWindowVisibilityChanged keyguardGoingAway");
                function0.invoke();
                keyguardFastBioUnlockController.reservedKeyguardGoingAway = null;
            }
        }
    };
    public final KeyguardFastBioUnlockController$brightnessChangedCallback$1 brightnessChangedCallback = new DisplayTracker.Callback() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$brightnessChangedCallback$1
        @Override // com.android.systemui.settings.DisplayTracker.Callback
        public final void onDisplayChanged(int i) {
            BrightnessInfo brightnessInfo;
            KeyguardFastBioUnlockController keyguardFastBioUnlockController = KeyguardFastBioUnlockController.this;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
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

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DelayedActionParams {
        public final Function0 action;
        public long atTime;
        public final Handler handler;
        public boolean isDiscard;
        public final long maxDelayMills;
        public final KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1 runnableWrapper = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams = KeyguardFastBioUnlockController.DelayedActionParams.this;
                if (delayedActionParams.isDiscard) {
                    return;
                }
                delayedActionParams.action.invoke();
                KeyguardFastBioUnlockController.DelayedActionParams delayedActionParams2 = KeyguardFastBioUnlockController.DelayedActionParams.this;
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
            long uptimeMillis = SystemClock.uptimeMillis();
            long j = this.maxDelayMills;
            this.atTime = uptimeMillis + j;
            handler.postDelayed(keyguardFastBioUnlockController$DelayedActionParams$runnableWrapper$1, j);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - this.startTime;
            this.runnable.run();
            long currentTimeMillis2 = System.currentTimeMillis() - currentTimeMillis;
            if (j >= 3 || currentTimeMillis2 >= 3) {
                Log.d("BioUnlock", "** " + this.tag + " run dur=" + currentTimeMillis2 + "ms, delivery=" + j + "ms");
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:6:0x0020, code lost:
    
        if ((r0 == com.android.systemui.util.DeviceType.DEBUG_LEVEL_MID || r0 == com.android.systemui.util.DeviceType.DEBUG_LEVEL_HIGH) != false) goto L12;
     */
    static {
        /*
            com.android.systemui.keyguard.KeyguardFastBioUnlockController$Companion r0 = new com.android.systemui.keyguard.KeyguardFastBioUnlockController$Companion
            r1 = 0
            r0.<init>(r1)
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.Companion = r0
            boolean r0 = com.android.systemui.util.DeviceType.isShipBuild()
            r1 = 1
            if (r0 == 0) goto L22
            int r0 = com.android.systemui.util.DeviceType.getDebugLevel()
            int r2 = com.android.systemui.util.DeviceType.DEBUG_LEVEL_MID
            r3 = 0
            if (r0 != r2) goto L1a
        L18:
            r0 = r1
            goto L20
        L1a:
            int r2 = com.android.systemui.util.DeviceType.DEBUG_LEVEL_HIGH
            if (r0 != r2) goto L1f
            goto L18
        L1f:
            r0 = r3
        L20:
            if (r0 == 0) goto L23
        L22:
            r3 = r1
        L23:
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.DEBUG = r3
            r0 = 32
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.MODE_FLAG_ENABLED = r0
            r0 = 16
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.MODE_FLAG_UNLOCK_ANIM_AOD_FULLSCREEN = r0
            r0 = 8
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.MODE_FLAG_STARTED_DISPLAY_DOZE_OR_OFF = r0
            r0 = 4
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.MODE_FLAG_STARTED_DISPLAY_ON = r0
            r0 = 2
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.MODE_FLAG_FRAME_REQUEST = r0
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.MODE_FLAG_FRAME_COMMIT = r1
            r0 = 3101(0xc1d, float:4.345E-42)
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.BOOSTER_HINT = r0
            r0 = 1000(0x3e8, float:1.401E-42)
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.BOOSTER_TIMEOUT = r0
            r0 = 6
            int[] r0 = new int[r0]
            r0 = {x005c: FILL_ARRAY_DATA , data: [32, 16, 8, 4, 2, 1} // fill-array
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.sFlags = r0
            java.lang.String r5 = "F_REQ"
            java.lang.String r6 = "F_COMMIT"
            java.lang.String r1 = "E"
            java.lang.String r2 = "UNLOCK_ANIM_AOD_FULLSCREEN"
            java.lang.String r3 = "OFF"
            java.lang.String r4 = "ON"
            java.lang.String[] r0 = new java.lang.String[]{r1, r2, r3, r4, r5, r6}
            com.android.systemui.keyguard.KeyguardFastBioUnlockController.sFlagsStr = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardFastBioUnlockController.<clinit>():void");
    }

    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.systemui.keyguard.KeyguardFastBioUnlockController$resetRunnable$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.systemui.keyguard.KeyguardFastBioUnlockController$visibilityChangedListener$1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.systemui.keyguard.KeyguardFastBioUnlockController$brightnessChangedCallback$1] */
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

    /* JADX WARN: Code restructure failed: missing block: B:35:0x005e, code lost:
    
        if (r8 != 6) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void calculateMode(int r8) {
        /*
            Method dump skipped, instructions count: 301
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.KeyguardFastBioUnlockController.calculateMode(int):void");
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
        Runnable runnable;
        KeyguardFastBioUnlockController$resetRunnable$1 keyguardFastBioUnlockController$resetRunnable$1 = this.resetRunnable;
        Handler handler = this.mainHandler;
        if (handler.hasCallbacks(keyguardFastBioUnlockController$resetRunnable$1)) {
            handler.removeCallbacks(keyguardFastBioUnlockController$resetRunnable$1);
        }
        boolean isMode = isMode(MODE_FLAG_ENABLED);
        if (isMode) {
            ((ArrayList) ((KeyguardVisibilityMonitor) this.visibilityMonitorLazy.get()).visibilityChangedListeners).remove(this.visibilityChangedListener);
            runPendingRunnable();
            VisibilityController visibilityController = this.curVisibilityController;
            if (visibilityController != null) {
                visibilityController.resetForceInvisible(false);
            }
            LogUtil.endTime(10000, new LongConsumer() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$reset$1
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    KeyguardFastBioUnlockController keyguardFastBioUnlockController = KeyguardFastBioUnlockController.this;
                    KeyguardFastBioUnlockController.Companion companion = KeyguardFastBioUnlockController.Companion;
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
        if (isMode && (runnable = this.scrimUpdater) != null && this.scrimVisibility != 0) {
            runnable.run();
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
        Iterator it = ((ArrayList) this.pendingRunnableList).iterator();
        int i = 0;
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
            i++;
        }
        ((ArrayList) this.pendingRunnableList).clear();
        if (DEBUG) {
            logD("runPendingRunnable executed: " + i);
        }
    }

    public final void setEnabled() {
        SemDvfsManager createInstance;
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
            if (this.dvfsManager == null && (createInstance = SemDvfsManager.createInstance(this.context, "KEYGUARD_BIO_UNLOCK")) != null) {
                int i = BOOSTER_HINT;
                if (createInstance.checkHintSupported(i)) {
                    this.dvfsManager = createInstance;
                    createInstance.setHint(i);
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
            final Function0 function0 = new Function0() { // from class: com.android.systemui.keyguard.KeyguardFastBioUnlockController$requestChangeVisibility$1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    final KeyguardFastBioUnlockController keyguardFastBioUnlockController = KeyguardFastBioUnlockController.this;
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
                                KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = KeyguardFastBioUnlockController.this;
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
                        Function0.this.invoke();
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
                StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("setMode 0x", hexString, "(", modeString, ") -> 0x");
                m.append(hexString2);
                m.append("(");
                m.append(modeString2);
                m.append(")");
                logD(m.toString());
            } else {
                logD("setMode 0x" + Integer.toHexString(i2) + " -> 0x" + Integer.toHexString(i));
            }
            if (LogUtil.isTraceEnabled()) {
                int i3 = this.curMode;
                if (i3 == MODE_FLAG_ENABLED) {
                    Companion.getClass();
                    LogUtil.traceBegin("BioUnlock_".concat(Companion.getModeString(i3)), 0);
                } else if (i3 == 0) {
                    Companion.getClass();
                    LogUtil.traceEnd("BioUnlock_".concat(Companion.getModeString(i2)), 0);
                } else {
                    Companion.getClass();
                    LogUtil.traceEnd("BioUnlock_".concat(Companion.getModeString(i2)), 0);
                    LogUtil.traceBegin("BioUnlock_".concat(Companion.getModeString(this.curMode)), 0);
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
