package com.android.systemui.uithreadmonitor;

import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import android.view.Display;
import android.view.ViewRootImpl;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda7;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.LsRune;
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
                    /* JADX WARN: Removed duplicated region for block: B:29:0x006b  */
                    @Override // android.hardware.display.DisplayManager.DisplayListener
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void onDisplayChanged(int i) {
                        UiThreadMonitor uiThreadMonitor2 = uiThreadMonitor;
                        if (i != 0) {
                            boolean z2 = UiThreadMonitor.DEBUG_LOG;
                            uiThreadMonitor2.getClass();
                            return;
                        }
                        int state = ((Display) uiThreadMonitor2.display$delegate.getValue()).getState();
                        if (state == uiThreadMonitor2.lastDisplayState) {
                            return;
                        }
                        uiThreadMonitor2.lastDisplayState = state;
                        boolean z3 = LsRune.AOD_DOZE_AP_SLEEP;
                        if ((z3 && state == 3) || state == 4) {
                            if (((LooperSlowLogControllerImpl) uiThreadMonitor2.looperLogController).enable(8, 1L, 1L, 10000L, true, uiThreadMonitor2.onChoreographerLog)) {
                                uiThreadMonitor2.lastChoreographerLogTime = System.currentTimeMillis();
                                uiThreadMonitor2.lastChoreographerLogCount = 0;
                                uiThreadMonitor2.lastChoreographerTotalDrawCount = 0;
                            }
                        } else {
                            ((LooperSlowLogControllerImpl) uiThreadMonitor2.looperLogController).disable(8);
                        }
                        if (!UiThreadMonitor.ENABLE_PAUSE) {
                            uiThreadMonitor2.isPaused = false;
                            return;
                        }
                        int i2 = uiThreadMonitor2.lastDisplayState;
                        if (i2 == 1) {
                            z3 = true;
                        } else if (i2 != 3) {
                            if (i2 != 4) {
                                if (uiThreadMonitor2.isPaused) {
                                    uiThreadMonitor2.setAwake(1);
                                }
                                z3 = false;
                            }
                        }
                        uiThreadMonitor2.isPaused = z3;
                        String strM = KeyguardUpdateMonitorLogger$$ExternalSyntheticOutline0.m("updatePause isPaused=", uiThreadMonitor2.isPaused);
                        if (UiThreadMonitor.DEBUG_LOG) {
                            Log.d("UiThreadMonitor", strM);
                        }
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
                    Message messageObtain = Message.obtain(handler, uiThreadMonitor$asyncRunnable$1);
                    messageObtain.setAsynchronous(true);
                    handler.sendMessageDelayed(messageObtain, 3000L);
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
                        long jNanoTime = System.nanoTime() - item.startTime;
                        if (jNanoTime >= item.compareDuration) {
                            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("** ", BinderCallMonitorImpl.m3126$$Nest$smgetCallers(item), " ");
                            long j2 = jNanoTime / 1000000;
                            sbM.append(j2);
                            sbM.append("ms");
                            com.android.systemui.keyguard.Log.w("BinderCallMonitor", sbM.toString());
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
                            String strM3126$$Nest$smgetCallers = BinderCallMonitorImpl.m3126$$Nest$smgetCallers(item);
                            BinderCallMonitorImpl.this.mBinderCallHistory.add("==================================================================");
                            CopyOnWriteArrayList copyOnWriteArrayList = BinderCallMonitorImpl.this.mBinderCallHistory;
                            StringBuilder sbM2 = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("** ", strM3126$$Nest$smgetCallers, " ");
                            long j3 = jNanoTime / 1000000;
                            sbM2.append(j3);
                            sbM2.append("ms");
                            copyOnWriteArrayList.add(sbM2.toString());
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
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    long j2 = BinderCallMonitorConstants.MAX_DURATION;
                    synchronized (binderCallMonitorImpl2.mMonitorInfo) {
                        try {
                            int size = binderCallMonitorImpl2.mMonitorInfo.size();
                            i3 = 0;
                            i4 = 0;
                            for (int i5 = 0; i5 < size; i5++) {
                                MonitorInfo monitorInfo = (MonitorInfo) binderCallMonitorImpl2.mMonitorInfo.valueAt(i5);
                                if (monitorInfo.enabled && !monitorInfo.infinite && monitorInfo.timeOut <= jCurrentTimeMillis) {
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
