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
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Rune;
import com.android.systemui.VendorServices;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SamsungServiceLoggerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.LogUtil;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
                    public final void onDisplayChanged(int i) {
                        UiThreadMonitor uiThreadMonitor2 = UiThreadMonitor.this;
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
                        boolean z3 = false;
                        if (state == 4) {
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
                        if (i2 == 1 || i2 == 4) {
                            z3 = true;
                        } else if (uiThreadMonitor2.isPaused) {
                            uiThreadMonitor2.setAwake(1);
                        }
                        uiThreadMonitor2.isPaused = z3;
                        String m86m = AbstractC0866xb1ce8deb.m86m("updatePause isPaused=", uiThreadMonitor2.isPaused);
                        if (UiThreadMonitor.DEBUG_LOG) {
                            Log.d("UiThreadMonitor", m86m);
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
                    Message obtain = Message.obtain(handler, uiThreadMonitor$asyncRunnable$1);
                    obtain.setAsynchronous(true);
                    handler.sendMessageDelayed(obtain, 3000L);
                    ((Thread) uiThreadMonitor.monitorThread$delegate.getValue()).start();
                    uiThreadMonitor.dumpManager.registerNsDumpable("UiThreadMonitor", uiThreadMonitor);
                } catch (Throwable th) {
                    MotionLayout$$ExternalSyntheticOutline0.m23m("init exception: ", th.getMessage(), "UiThreadMonitor");
                }
            }
            if (!DeviceState.IS_ALREADY_BOOTED) {
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
        if (BinderCallMonitorConstants.MAX_DURATION != 0 && BinderCallMonitorConstants.MAX_BUF_COUNT != 0) {
            Log.d("BinderCallMonitor", NetworkAnalyticsConstants.DataPoints.OPEN_TIME);
            Binder.setProxyTransactListener(new Binder.ProxyTransactListener() { // from class: com.android.systemui.uithreadmonitor.BinderCallMonitorImpl.1
                public C35091() {
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
                            StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m("** ", str2, " ");
                            long j = nanoTime / 1000000;
                            m4m.append(j);
                            m4m.append("ms");
                            com.android.systemui.keyguard.Log.m142w("BinderCallMonitor", m4m.toString());
                            ((SamsungServiceLoggerImpl) BinderCallMonitorImpl.this.mLogger).log("BinderCallMonitor", LogLevel.DEBUG, LogUtil.getMsg("   * %dms\n%s", Long.valueOf(j), item.stackTrace));
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
                    long j = BinderCallMonitorConstants.MAX_DURATION;
                    synchronized (binderCallMonitorImpl2.mMonitorInfo) {
                        int size = binderCallMonitorImpl2.mMonitorInfo.size();
                        i3 = 0;
                        for (int i4 = 0; i4 < size; i4++) {
                            MonitorInfo monitorInfo = (MonitorInfo) binderCallMonitorImpl2.mMonitorInfo.valueAt(i4);
                            if (monitorInfo.enabled && !monitorInfo.infinite && monitorInfo.timeOut <= currentTimeMillis) {
                                monitorInfo.enabled = false;
                            }
                            if (monitorInfo.enabled) {
                                i3++;
                                long j2 = monitorInfo.duration;
                                if (j2 < j) {
                                    j = j2;
                                }
                            }
                        }
                    }
                    binderCallMonitorImpl2.mDuration = j;
                    if (i3 == 0) {
                        return null;
                    }
                    return new Item(BinderCallMonitorImpl.this.mDuration, 0);
                }
            });
        }
        if (DeviceType.getDebugLevel() == 1) {
            binderCallMonitorImpl.startMonitoring$1(0);
        }
    }
}
