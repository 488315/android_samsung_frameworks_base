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
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Rune;
import com.android.systemui.VendorServices;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

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
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void onDisplayChanged(int r15) {
                        /*
                            r14 = this;
                            com.android.systemui.uithreadmonitor.UiThreadMonitor r14 = com.android.systemui.uithreadmonitor.UiThreadMonitor.this
                            if (r15 == 0) goto Lb
                            boolean r15 = com.android.systemui.uithreadmonitor.UiThreadMonitor.DEBUG_LOG
                            r14.getClass()
                            goto L81
                        Lb:
                            kotlin.Lazy r15 = r14.display$delegate
                            java.lang.Object r15 = r15.getValue()
                            android.view.Display r15 = (android.view.Display) r15
                            int r15 = r15.getState()
                            int r0 = r14.lastDisplayState
                            if (r15 != r0) goto L1c
                            goto L81
                        L1c:
                            r14.lastDisplayState = r15
                            boolean r0 = com.android.systemui.LsRune.AOD_DOZE_AP_SLEEP
                            r1 = 4
                            r2 = 3
                            r3 = 0
                            if (r0 == 0) goto L27
                            if (r15 == r2) goto L29
                        L27:
                            if (r15 != r1) goto L4a
                        L29:
                            com.android.systemui.uithreadmonitor.LooperSlowLogController r15 = r14.looperLogController
                            kotlin.jvm.functions.Function2 r13 = r14.onChoreographerLog
                            r4 = r15
                            com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl r4 = (com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl) r4
                            r10 = 10000(0x2710, double:4.9407E-320)
                            r12 = 1
                            r5 = 8
                            r6 = 1
                            r8 = 1
                            boolean r15 = r4.enable(r5, r6, r8, r10, r12, r13)
                            if (r15 == 0) goto L53
                            long r4 = java.lang.System.currentTimeMillis()
                            r14.lastChoreographerLogTime = r4
                            r14.lastChoreographerLogCount = r3
                            r14.lastChoreographerTotalDrawCount = r3
                            goto L53
                        L4a:
                            com.android.systemui.uithreadmonitor.LooperSlowLogController r15 = r14.looperLogController
                            r4 = 8
                            com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl r15 = (com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl) r15
                            r15.disable(r4)
                        L53:
                            boolean r15 = com.android.systemui.uithreadmonitor.UiThreadMonitor.ENABLE_PAUSE
                            if (r15 != 0) goto L5a
                            r14.isPaused = r3
                            goto L81
                        L5a:
                            int r15 = r14.lastDisplayState
                            r4 = 1
                            if (r15 == r4) goto L6c
                            if (r15 == r2) goto L6d
                            if (r15 == r1) goto L6c
                            boolean r15 = r14.isPaused
                            if (r15 == 0) goto L6a
                            r14.setAwake(r4)
                        L6a:
                            r0 = r3
                            goto L6d
                        L6c:
                            r0 = r4
                        L6d:
                            r14.isPaused = r0
                            boolean r14 = r14.isPaused
                            java.lang.String r15 = "updatePause isPaused="
                            java.lang.String r14 = com.android.keyguard.logging.KeyguardUpdateMonitorLogger$allowFingerprintOnCurrentOccludingActivityChanged$2$$ExternalSyntheticOutline0.m(r15, r14)
                            boolean r15 = com.android.systemui.uithreadmonitor.UiThreadMonitor.DEBUG_LOG
                            if (r15 == 0) goto L81
                            java.lang.String r15 = "UiThreadMonitor"
                            android.util.Log.d(r15, r14)
                        L81:
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
        BinderCallMonitorImpl binderCallMonitorImpl = (BinderCallMonitorImpl) (binderCallMonitor != null ? binderCallMonitor : null);
        binderCallMonitorImpl.getClass();
        long j = BinderCallMonitorConstants.MAX_DURATION;
        if (j != 0 && BinderCallMonitorConstants.MAX_BUF_COUNT != 0) {
            Log.d("BinderCallMonitor", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            Binder.setProxyTransactListener(new Binder.ProxyTransactListener() { // from class: com.android.systemui.uithreadmonitor.BinderCallMonitorImpl.1
                public AnonymousClass1() {
                }

                public final void onTransactEnded(Object obj) {
                    String str;
                    if (ThreadUtils.isMainThread() && (obj instanceof Item)) {
                        Item item = (Item) obj;
                        long nanoTime = System.nanoTime() - item.startTime;
                        if (nanoTime >= item.compareDuration) {
                            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
                            StringBuilder sb = new StringBuilder();
                            if (BinderCallMonitorImpl.sSkipCallCount == -1) {
                                int length = stackTrace.length;
                                int i = 0;
                                int i2 = 0;
                                StackTraceElement stackTraceElement = null;
                                while (true) {
                                    if (i >= length) {
                                        i2 = 2;
                                        break;
                                    }
                                    StackTraceElement stackTraceElement2 = stackTrace[i];
                                    i2++;
                                    if (stackTraceElement != null && stackTraceElement.getMethodName().endsWith("onTransactEnded") && stackTraceElement2 != null && stackTraceElement2.getMethodName().endsWith("transact")) {
                                        break;
                                    }
                                    i++;
                                    stackTraceElement = stackTraceElement2;
                                }
                                BinderCallMonitorImpl.sSkipCallCount = i2;
                            }
                            String str2 = null;
                            for (int i3 = 0; i3 < 20; i3++) {
                                int i4 = BinderCallMonitorImpl.sSkipCallCount + i3;
                                if (i4 >= stackTrace.length) {
                                    str = null;
                                } else {
                                    StackTraceElement stackTraceElement3 = stackTrace[i4];
                                    str = stackTraceElement3.getClassName() + "." + stackTraceElement3.getMethodName() + ":" + stackTraceElement3.getLineNumber();
                                }
                                if (str == null) {
                                    break;
                                }
                                if (i3 == 0) {
                                    str2 = str;
                                }
                                sb.append("    ");
                                sb.append(str);
                                sb.append('\n');
                            }
                            item.stackTrace = sb.toString();
                            StringBuilder m = ActivityResultRegistry$$ExternalSyntheticOutline0.m("** ", str2, " ");
                            long j2 = nanoTime / 1000000;
                            m.append(j2);
                            m.append("ms");
                            com.android.systemui.keyguard.Log.w("BinderCallMonitor", m.toString());
                            ((SamsungServiceLoggerImpl) BinderCallMonitorImpl.this.mLogger).log("BinderCallMonitor", LogLevel.DEBUG, LogUtil.getMsg("   * %dms\n%s", Long.valueOf(j2), item.stackTrace));
                        }
                    }
                }

                public final Object onTransactStarted(IBinder iBinder, int i) {
                    return null;
                }

                public final Object onTransactStarted(IBinder iBinder, int i, int i2) {
                    int i3;
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
                            for (int i4 = 0; i4 < size; i4++) {
                                MonitorInfo monitorInfo = (MonitorInfo) binderCallMonitorImpl2.mMonitorInfo.valueAt(i4);
                                if (monitorInfo.enabled && !monitorInfo.infinite && monitorInfo.timeOut <= currentTimeMillis) {
                                    monitorInfo.enabled = false;
                                }
                                if (monitorInfo.enabled) {
                                    i3++;
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
                    if (i3 == 0) {
                        return null;
                    }
                    return new Item(BinderCallMonitorImpl.this.mDuration, 0);
                }
            });
        }
        if (DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID) {
            binderCallMonitorImpl.getClass();
            binderCallMonitorImpl.startMonitoring(0, j / 1000000, 8000L);
        }
    }
}
