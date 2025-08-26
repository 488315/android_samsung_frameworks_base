package com.android.wm.shell.performance;

import android.content.Context;
import android.os.PerformanceHintManager;
import android.os.Process;
import android.window.SystemPerformanceHinter;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/* loaded from: classes3.dex */
public final class PerfHintController {
    public final SystemPerformanceHinter hinter;
    public final Context mContext;
    public final ShellCommandHandler mShellCommandHandler;

    public PerfHintController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer) {
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.hinter = new SystemPerformanceHinter(context, rootTaskDisplayAreaOrganizer.mPerfRootProvider);
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.performance.PerfHintController.1
            @Override // java.lang.Runnable
            public final void run() {
                PerformanceHintManager.Session sessionCreateHintSession;
                final PerfHintController perfHintController = PerfHintController.this;
                perfHintController.getClass();
                perfHintController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.performance.PerfHintController$onInit$1
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        perfHintController.hinter.dump((PrintWriter) obj, (String) obj2);
                    }
                }, perfHintController);
                PerformanceHintManager performanceHintManager = (PerformanceHintManager) perfHintController.mContext.getSystemService(PerformanceHintManager.class);
                if (performanceHintManager == null || (sessionCreateHintSession = performanceHintManager.createHintSession(new int[]{Process.myTid()}, TimeUnit.SECONDS.toNanos(1L))) == null) {
                    return;
                }
                perfHintController.hinter.setAdpfSession(sessionCreateHintSession);
            }
        }, this);
    }
}
