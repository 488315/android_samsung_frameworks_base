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
import android.os.Trace;
import android.os.UserHandle;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.BinderInternal;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.DumpsysEntry;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.dump.LogBufferFreezer;
import com.android.systemui.dump.SystemUIAuxiliaryDumpService;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.android.systemui.statusbar.policy.BatteryControllerImpl;
import com.android.systemui.statusbar.policy.BatteryStateNotifier;
import com.android.systemui.util.MemoryMonitor;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.Thread;
import java.util.Iterator;
import java.util.TreeMap;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class SystemUIService extends Service {
    public final BatteryStateNotifier mBatteryStateNotifier;
    public final BinderProxyDumpHelper mBinderProxyDumpHelper;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final DumpHandler mDumpHandler;
    public final LogBufferEulogizer mLogBufferEulogizer;
    public final LogBufferFreezer mLogBufferFreezer;
    public final Handler mMainHandler;
    public final MemoryMonitor mMemoryMonitor;
    public final UncaughtExceptionPreHandlerManager mUncaughtExceptionPreHandlerManager;

    public SystemUIService(Handler handler, DumpHandler dumpHandler, BroadcastDispatcher broadcastDispatcher, LogBufferEulogizer logBufferEulogizer, LogBufferFreezer logBufferFreezer, BatteryStateNotifier batteryStateNotifier, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager, BinderProxyDumpHelper binderProxyDumpHelper, MemoryMonitor memoryMonitor) {
        this.mMainHandler = handler;
        this.mDumpHandler = dumpHandler;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mLogBufferEulogizer = logBufferEulogizer;
        this.mLogBufferFreezer = logBufferFreezer;
        this.mBatteryStateNotifier = batteryStateNotifier;
        this.mUncaughtExceptionPreHandlerManager = uncaughtExceptionPreHandlerManager;
        if (ScRune.ENHANCEMENT_DUMP_HELPER) {
            this.mBinderProxyDumpHelper = binderProxyDumpHelper;
        }
        if (ScRune.ENHANCEMENT_DEBUG_MEMORY_LOG) {
            this.mMemoryMonitor = memoryMonitor;
        }
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
        ((SystemUIApplication) getApplication()).startSystemUserServicesIfNeeded();
        final LogBufferFreezer logBufferFreezer = this.mLogBufferFreezer;
        BroadcastDispatcher broadcastDispatcher = this.mBroadcastDispatcher;
        logBufferFreezer.getClass();
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher, new BroadcastReceiver() { // from class: com.android.systemui.dump.LogBufferFreezer$attach$1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                final LogBufferFreezer logBufferFreezer2 = LogBufferFreezer.this;
                logBufferFreezer2.getClass();
                Trace.instantForTrack(4096L, "bugreport", "BUGREPORT_STARTED broadcast received");
                Runnable runnable = logBufferFreezer2.pendingToken;
                if (runnable != null) {
                    runnable.run();
                }
                Log.i("LogBufferFreezer", "Freezing log buffers");
                DumpManager dumpManager = logBufferFreezer2.dumpManager;
                synchronized (dumpManager) {
                    Iterator it = ((TreeMap) dumpManager.buffers).values().iterator();
                    while (it.hasNext()) {
                        ((DumpsysEntry.LogBufferEntry) it.next()).buffer.freeze();
                    }
                }
                logBufferFreezer2.pendingToken = logBufferFreezer2.executor.executeDelayed(new Runnable() { // from class: com.android.systemui.dump.LogBufferFreezer$onBugreportStarted$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Log.i("LogBufferFreezer", "Unfreezing log buffers");
                        LogBufferFreezer logBufferFreezer3 = LogBufferFreezer.this;
                        logBufferFreezer3.pendingToken = null;
                        DumpManager dumpManager2 = logBufferFreezer3.dumpManager;
                        synchronized (dumpManager2) {
                            Iterator it2 = ((TreeMap) dumpManager2.buffers).values().iterator();
                            while (it2.hasNext()) {
                                ((DumpsysEntry.LogBufferEntry) it2.next()).buffer.unfreeze();
                            }
                        }
                    }
                }, logBufferFreezer2.freezeDuration);
            }
        }, new IntentFilter("com.android.internal.intent.action.BUGREPORT_STARTED"), logBufferFreezer.executor, UserHandle.ALL, 0, null, 48);
        this.mUncaughtExceptionPreHandlerManager.registerHandler(new Thread.UncaughtExceptionHandler() { // from class: com.android.systemui.SystemUIService$$ExternalSyntheticLambda0
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public final void uncaughtException(Thread thread, Throwable th) {
                SystemUIService.this.mLogBufferEulogizer.record(th);
            }
        });
        if (getResources().getBoolean(R.bool.config_showNotificationForUnknownBatteryState)) {
            BatteryStateNotifier batteryStateNotifier = this.mBatteryStateNotifier;
            ((BatteryControllerImpl) batteryStateNotifier.controller).addCallback(batteryStateNotifier);
        }
        boolean z = Build.IS_DEBUGGABLE;
        if (z && SystemProperties.getBoolean("debug.crash_sysui", false)) {
            throw new RuntimeException();
        }
        Typeface.setFlipFonts();
        if (ScRune.ENHANCEMENT_DEBUG_MEMORY_LOG) {
            this.mMemoryMonitor.registerUncaughtException();
        }
        if (ScRune.ENHANCEMENT_DUMP_HELPER) {
            BinderInternal.nSetBinderProxyCountEnabled(true);
            BinderInternal.nSetBinderProxyCountWatermarks(5900, 5400, 5650);
            BinderInternal.setBinderProxyCountCallback(new BinderInternal.BinderProxyCountEventListener() { // from class: com.android.systemui.SystemUIService.1
                public final void onLimitReached(int i) {
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "uid ", " sent too many Binder proxies to uid ");
                    m.append(Process.myUid());
                    Slog.w("SystemUIService", m.toString());
                    SystemUIService.this.mBinderProxyDumpHelper.getClass();
                    Log.d("BinderProxyDumpHelper", BinderProxyDumpHelper.dumpProxyInterfaceCounts());
                    Log.d("BinderProxyDumpHelper", BinderProxyDumpHelper.dumpPerUidProxyCounts());
                }
            }, this.mMainHandler);
        } else if (z) {
            BinderInternal.nSetBinderProxyCountEnabled(true);
            BinderInternal.nSetBinderProxyCountWatermarks(1000, 900, 950);
            BinderInternal.setBinderProxyCountCallback(new BinderInternal.BinderProxyCountEventListener(this) { // from class: com.android.systemui.SystemUIService.2
                public final void onLimitReached(int i) {
                    StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "uid ", " sent too many Binder proxies to uid ");
                    m.append(Process.myUid());
                    Slog.w("SystemUIService", m.toString());
                }
            }, this.mMainHandler);
        }
        startServiceAsUser(new Intent(getApplicationContext(), (Class<?>) SystemUIAuxiliaryDumpService.class), UserHandle.SYSTEM);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks2
    public final synchronized void onTrimMemory(int i) {
        if (ScRune.ENHANCEMENT_DEBUG_MEMORY_LOG) {
            Slog.d("SystemUIService", "onTrimMemory : " + i);
            if (i == 10 || i == 15 || i == 20 || i == 60 || i == 80) {
                this.mMemoryMonitor.dispatchTrimMemory();
            }
        }
    }
}
