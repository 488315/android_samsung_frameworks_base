package com.android.systemui.uithreadmonitor;

import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import android.view.ViewRootImpl;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Rune;
import com.android.systemui.VendorServices;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.SamsungServiceLogger;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SecVendorServices extends VendorServices {
    public BinderCallMonitor binderCallMonitor;
    public LooperSlowLogController looperSlowLogController;
    public UiThreadMonitor uiThreadMonitor;

    @Override // com.android.systemui.VendorServices, com.android.systemui.CoreStartable
    public final void start() {
        if (Rune.SYSUI_UI_THREAD_MONITOR) {
            final UiThreadMonitor uiThreadMonitor = this.uiThreadMonitor;
            if (uiThreadMonitor == null) {
                uiThreadMonitor = null;
            }
            uiThreadMonitor.getClass();
            boolean z = SystemProperties.getBoolean("debug.sysui.anr_detector.disabled", false);
            if (z) {
                Log.d("UiThreadMonitor", "disabled");
            }
            if (!z) {
                Log.d("UiThreadMonitor", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
                uiThreadMonitor.displayManager.registerDisplayListener(new DisplayManager.DisplayListener() { // from class: com.android.systemui.uithreadmonitor.UiThreadMonitor$start$1
                    /* JADX WARN: Removed duplicated region for block: B:28:0x007b  */
                    /* JADX WARN: Removed duplicated region for block: B:30:? A[RETURN, SYNTHETIC] */
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onDisplayChanged(int r15) {
                        /*
                            r14 = this;
                            com.android.systemui.uithreadmonitor.UiThreadMonitor r14 = com.android.systemui.uithreadmonitor.UiThreadMonitor.this
                            if (r15 == 0) goto La
                            boolean r15 = com.android.systemui.uithreadmonitor.UiThreadMonitor.DEBUG_LOG
                            r14.getClass()
                            return
                        La:
                            kotlin.Lazy r15 = r14.display$delegate
                            java.lang.Object r15 = r15.getValue()
                            android.view.Display r15 = (android.view.Display) r15
                            int r15 = r15.getState()
                            int r0 = r14.lastDisplayState
                            if (r15 != r0) goto L1b
                            goto L80
                        L1b:
                            r14.lastDisplayState = r15
                            boolean r0 = com.android.systemui.LsRune.AOD_DOZE_AP_SLEEP
                            r1 = 4
                            r2 = 3
                            r3 = 0
                            if (r0 == 0) goto L26
                            if (r15 == r2) goto L28
                        L26:
                            if (r15 != r1) goto L49
                        L28:
                            com.android.systemui.uithreadmonitor.LooperSlowLogController r15 = r14.looperLogController
                            com.android.systemui.uithreadmonitor.UiThreadMonitor$$ExternalSyntheticLambda3 r13 = r14.onChoreographerLog
                            r4 = r15
                            com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl r4 = (com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl) r4
                            r10 = 10000(0x2710, double:4.9407E-320)
                            r12 = 1
                            r5 = 8
                            r6 = 1
                            r8 = 1
                            boolean r15 = r4.enable(r5, r6, r8, r10, r12, r13)
                            if (r15 == 0) goto L52
                            long r4 = java.lang.System.currentTimeMillis()
                            r14.lastChoreographerLogTime = r4
                            r14.lastChoreographerLogCount = r3
                            r14.lastChoreographerTotalDrawCount = r3
                            goto L52
                        L49:
                            com.android.systemui.uithreadmonitor.LooperSlowLogController r15 = r14.looperLogController
                            r4 = 8
                            com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl r15 = (com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl) r15
                            r15.disable(r4)
                        L52:
                            boolean r15 = com.android.systemui.uithreadmonitor.UiThreadMonitor.ENABLE_PAUSE
                            if (r15 != 0) goto L59
                            r14.isPaused = r3
                            return
                        L59:
                            int r15 = r14.lastDisplayState
                            r4 = 1
                            if (r15 == r4) goto L6b
                            if (r15 == r2) goto L6c
                            if (r15 == r1) goto L6b
                            boolean r15 = r14.isPaused
                            if (r15 == 0) goto L69
                            r14.setAwake(r4)
                        L69:
                            r0 = r3
                            goto L6c
                        L6b:
                            r0 = r4
                        L6c:
                            r14.isPaused = r0
                            boolean r14 = r14.isPaused
                            java.lang.String r15 = "updatePause isPaused="
                            java.lang.String r14 = com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m(r15, r14)
                            boolean r15 = com.android.systemui.uithreadmonitor.UiThreadMonitor.DEBUG_LOG
                            if (r15 == 0) goto L80
                            java.lang.String r15 = "UiThreadMonitor"
                            android.util.Log.d(r15, r14)
                        L80:
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.uithreadmonitor.UiThreadMonitor$start$1.onDisplayChanged(int):void");
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayAdded(int i) {
                    }

                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    public final void onDisplayRemoved(int i) {
                    }
                }, uiThreadMonitor.bgHandler);
                if (DeviceType.isEngOrUTBinary()) {
                    ViewRootImpl.setSafeScheduleTraversals(true);
                }
                uiThreadMonitor.lastAsyncMsgHandledTimed = System.currentTimeMillis();
                uiThreadMonitor.lastAwakeTime = SystemClock.elapsedRealtime();
                try {
                    Handler handler = uiThreadMonitor.handler;
                    UiThreadMonitor$asyncRunnable$1 uiThreadMonitor$asyncRunnable$1 = uiThreadMonitor.asyncRunnable;
                    handler.removeCallbacks(uiThreadMonitor$asyncRunnable$1);
                    Message obtain = Message.obtain(handler, uiThreadMonitor$asyncRunnable$1);
                    obtain.setAsynchronous(true);
                    handler.sendMessageDelayed(obtain, 3000L);
                    ((Thread) uiThreadMonitor.monitorThread$delegate.getValue()).start();
                    uiThreadMonitor.dumpManager.registerNormalDumpable("UiThreadMonitor", uiThreadMonitor);
                } catch (Throwable th) {
                    MotionLayout$$ExternalSyntheticOutline0.m("init exception: ", th.getMessage(), "UiThreadMonitor");
                }
            }
            if (!DeviceState.isAlreadyBooted()) {
                LooperSlowLogController looperSlowLogController = this.looperSlowLogController;
                if (looperSlowLogController == null) {
                    looperSlowLogController = null;
                }
                ((LooperSlowLogControllerImpl) looperSlowLogController).enable(7, 30L, 50L, 60000L, false, null);
            }
        }
        if (!Rune.SYSUI_BINDER_CALL_MONITOR || DejankUtils.STRICT_MODE_ENABLED) {
            return;
        }
        BinderCallMonitor binderCallMonitor = this.binderCallMonitor;
        final BinderCallMonitorImpl binderCallMonitorImpl = (BinderCallMonitorImpl) (binderCallMonitor != null ? binderCallMonitor : null);
        binderCallMonitorImpl.getClass();
        long j = BinderCallMonitorConstants.MAX_DURATION;
        if (j != 0 && BinderCallMonitorConstants.MAX_BUF_COUNT != 0) {
            Log.d("BinderCallMonitor", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            Binder.setProxyTransactListener(new Binder.ProxyTransactListener() { // from class: com.android.systemui.uithreadmonitor.BinderCallMonitorImpl.2
                public final void onTransactEnded(Object obj) {
                    if (ThreadUtils.isMainThread() && (obj instanceof Item)) {
                        Item item = (Item) obj;
                        long nanoTime = System.nanoTime() - item.startTime;
                        if (nanoTime >= item.compareDuration) {
                            StringBuilder m = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("** ", BinderCallMonitorImpl.m3110$$Nest$smgetCallers(item), " ");
                            long j2 = nanoTime / 1000000;
                            m.append(j2);
                            m.append("ms");
                            com.android.systemui.keyguard.Log.w("BinderCallMonitor", m.toString());
                            SamsungServiceLogger samsungServiceLogger = BinderCallMonitorImpl.this.mLogger;
                            LogLevel logLevel = LogLevel.DEBUG;
                            String msg = LogUtil.getMsg("   * %dms\n%s", Long.valueOf(j2), item.stackTrace);
                            SamsungServiceLoggerImpl samsungServiceLoggerImpl = (SamsungServiceLoggerImpl) samsungServiceLogger;
                            samsungServiceLoggerImpl.getClass();
                            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7(msg);
                            LogBuffer logBuffer = samsungServiceLoggerImpl.buffer;
                            logBuffer.commit(logBuffer.obtain("BinderCallMonitor", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda7, null));
                        }
                        if (BinderCallMonitorImpl.this.mBinderCallMonitorState == 1) {
                            String m3110$$Nest$smgetCallers = BinderCallMonitorImpl.m3110$$Nest$smgetCallers(item);
                            BinderCallMonitorImpl.this.mBinderCallHistory.add("==================================================================");
                            CopyOnWriteArrayList copyOnWriteArrayList = BinderCallMonitorImpl.this.mBinderCallHistory;
                            StringBuilder m2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("** ", m3110$$Nest$smgetCallers, " ");
                            long j3 = nanoTime / 1000000;
                            m2.append(j3);
                            m2.append("ms");
                            copyOnWriteArrayList.add(m2.toString());
                            BinderCallMonitorImpl.this.mBinderCallHistory.add(LogUtil.getMsg("   * %dms\n%s", Long.valueOf(j3), item.stackTrace));
                        }
                    }
                }

                public final Object onTransactStarted(IBinder iBinder, int i) {
                    return null;
                }

                public final Object onTransactStarted(IBinder iBinder, int i, int i2) {
                    int i3;
                    int i4;
                    if ((i2 & 1) == 1 || !ThreadUtils.isMainThread()) {
                        return null;
                    }
                    BinderCallMonitorImpl binderCallMonitorImpl2 = BinderCallMonitorImpl.this;
                    binderCallMonitorImpl2.getClass();
                    long currentTimeMillis = System.currentTimeMillis();
                    long j2 = BinderCallMonitorConstants.MAX_DURATION;
                    synchronized (binderCallMonitorImpl2.mMonitorInfo) {
                        try {
                            int size = binderCallMonitorImpl2.mMonitorInfo.size();
                            i3 = 0;
                            i4 = 0;
                            for (int i5 = 0; i5 < size; i5++) {
                                MonitorInfo monitorInfo = (MonitorInfo) binderCallMonitorImpl2.mMonitorInfo.valueAt(i5);
                                if (monitorInfo.enabled && !monitorInfo.infinite && monitorInfo.timeOut <= currentTimeMillis) {
                                    monitorInfo.enabled = false;
                                }
                                if (monitorInfo.enabled) {
                                    i4++;
                                    long j3 = monitorInfo.duration;
                                    if (j3 < j2) {
                                        j2 = j3;
                                    }
                                }
                            }
                        } catch (Throwable th2) {
                            throw th2;
                        }
                    }
                    binderCallMonitorImpl2.mDuration = j2;
                    if (i4 == 0) {
                        return null;
                    }
                    return new Item(BinderCallMonitorImpl.this.mDuration, i3);
                }
            });
        }
        if (DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID) {
            binderCallMonitorImpl.getClass();
            binderCallMonitorImpl.startMonitoring(0, j / 1000000, 8000L);
        }
    }
}
