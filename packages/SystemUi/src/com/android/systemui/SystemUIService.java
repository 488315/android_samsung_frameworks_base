package com.android.systemui;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.BinderInternal;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.LogBufferFreezer;
import com.android.systemui.dump.SystemUIAuxiliaryDumpService;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.BatteryStateNotifier;
import com.android.systemui.util.MemoryMonitor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.Thread;
import java.util.Iterator;
import java.util.TreeMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SystemUIService extends Service {
    public final BatteryStateNotifier mBatteryStateNotifier;
    public final BinderProxyDumpHelper mBinderProxyDumpHelper;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final DumpHandler mDumpHandler;
    public final LogBufferFreezer mLogBufferFreezer;
    public final Handler mMainHandler;
    public final MemoryMonitor mMemoryMonitor;

    public SystemUIService(Handler handler, DumpHandler dumpHandler, BroadcastDispatcher broadcastDispatcher, LogBufferFreezer logBufferFreezer, BatteryStateNotifier batteryStateNotifier, BinderProxyDumpHelper binderProxyDumpHelper, MemoryMonitor memoryMonitor) {
        this.mMainHandler = handler;
        this.mDumpHandler = dumpHandler;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mLogBufferFreezer = logBufferFreezer;
        this.mBatteryStateNotifier = batteryStateNotifier;
        if (ScRune.ENHANCEMENT_DUMP_HELPER) {
            this.mBinderProxyDumpHelper = binderProxyDumpHelper;
        }
        this.mMemoryMonitor = memoryMonitor;
    }

    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (strArr.length == 0) {
            strArr = new String[]{"--dump-priority", "CRITICAL"};
        }
        this.mDumpHandler.dump(fileDescriptor, printWriter, strArr);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public final void onCreate() {
        super.onCreate();
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
        final LogBufferFreezer logBufferFreezer = this.mLogBufferFreezer;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        logBufferFreezer.getClass();
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.dump.LogBufferFreezer$attach$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                final LogBufferFreezer logBufferFreezer2 = LogBufferFreezer.this;
                Runnable runnable = logBufferFreezer2.pendingToken;
                if (runnable != null) {
                    runnable.run();
                }
                Log.i("LogBufferFreezer", "Freezing log buffers");
                DumpManager dumpManager = logBufferFreezer2.dumpManager;
                synchronized (dumpManager) {
                    Iterator it = ((TreeMap) dumpManager.buffers).values().iterator();
                    while (it.hasNext()) {
                        ((LogBuffer) ((RegisteredDumpable) it.next()).dumpable).freeze();
                    }
                }
                logBufferFreezer2.pendingToken = logBufferFreezer2.executor.executeDelayed(logBufferFreezer2.freezeDuration, new Runnable() { // from class: com.android.systemui.dump.LogBufferFreezer$onBugreportStarted$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Log.i("LogBufferFreezer", "Unfreezing log buffers");
                        LogBufferFreezer logBufferFreezer3 = LogBufferFreezer.this;
                        logBufferFreezer3.pendingToken = null;
                        DumpManager dumpManager2 = logBufferFreezer3.dumpManager;
                        synchronized (dumpManager2) {
                            Iterator it2 = ((TreeMap) dumpManager2.buffers).values().iterator();
                            while (it2.hasNext()) {
                                ((LogBuffer) ((RegisteredDumpable) it2.next()).dumpable).unfreeze();
                            }
                        }
                    }
                });
            }
        }, new IntentFilter("com.android.internal.intent.action.BUGREPORT_STARTED"), logBufferFreezer.executor, UserHandle.ALL, 0, null, 48);
        final DumpHandler dumpHandler = this.mDumpHandler;
        dumpHandler.getClass();
        dumpHandler.uncaughtExceptionPreHandlerManager.registerHandler(new Thread.UncaughtExceptionHandler() { // from class: com.android.systemui.dump.DumpHandler$init$1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                if (th instanceof Exception) {
                    DumpHandler.this.logBufferEulogizer.record((Exception) th);
                }
            }
        });
        if (getResources().getBoolean(R.bool.config_showNotificationForUnknownBatteryState)) {
            BatteryStateNotifier batteryStateNotifier = this.mBatteryStateNotifier;
            ((BatteryControllerImpl) batteryStateNotifier.controller).addCallback(batteryStateNotifier);
        }
        int i = 0;
        if (Build.IS_DEBUGGABLE && SystemProperties.getBoolean("debug.crash_sysui", false)) {
            throw new RuntimeException();
        }
        Typeface.setFlipFonts();
        MemoryMonitor memoryMonitor = this.mMemoryMonitor;
        memoryMonitor.getClass();
        memoryMonitor.mPreHandlerManager.registerHandler(new MemoryMonitor.SystemUIExceptionHandler(memoryMonitor, i));
        if (ScRune.ENHANCEMENT_DUMP_HELPER) {
            BinderInternal.nSetBinderProxyCountEnabled(true);
            BinderInternal.nSetBinderProxyCountWatermarks(5900, 5400);
            BinderInternal.setBinderProxyCountCallback(new BinderInternal.BinderProxyLimitListener() { // from class: com.android.systemui.SystemUIService.1
                public final void onLimitReached(int i2) {
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("uid ", i2, " sent too many Binder proxies to uid ");
                    m1m.append(Process.myUid());
                    Slog.w("SystemUIService", m1m.toString());
                    SystemUIService.this.mBinderProxyDumpHelper.getClass();
                    Log.d("BinderProxyDumpHelper", BinderProxyDumpHelper.dumpProxyInterfaceCounts());
                    Log.d("BinderProxyDumpHelper", BinderProxyDumpHelper.dumpPerUidProxyCounts());
                }
            }, this.mMainHandler);
        } else if (Build.IS_DEBUGGABLE) {
            BinderInternal.nSetBinderProxyCountEnabled(true);
            BinderInternal.nSetBinderProxyCountWatermarks(1000, 900);
            BinderInternal.setBinderProxyCountCallback(new BinderInternal.BinderProxyLimitListener(this) { // from class: com.android.systemui.SystemUIService.2
                public final void onLimitReached(int i2) {
                    StringBuilder m1m = AbstractC0000x2c234b15.m1m("uid ", i2, " sent too many Binder proxies to uid ");
                    m1m.append(Process.myUid());
                    Slog.w("SystemUIService", m1m.toString());
                }
            }, this.mMainHandler);
        }
        startServiceAsUser(new Intent(getApplicationContext(), (Class<?>) SystemUIAuxiliaryDumpService.class), UserHandle.SYSTEM);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public final synchronized void onTrimMemory(int i) {
        Slog.d("SystemUIService", "onTrimMemory : " + i);
        if (i == 10 || i == 15 || i == 20 || i == 60 || i == 80) {
            this.mMemoryMonitor.dispatchTrimMemory();
        }
    }
}
