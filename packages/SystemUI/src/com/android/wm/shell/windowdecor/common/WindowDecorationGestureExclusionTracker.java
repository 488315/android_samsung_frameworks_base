package com.android.wm.shell.windowdecor.common;

import android.content.Context;
import android.graphics.Region;
import android.os.RemoteException;
import android.util.Slog;
import android.view.ISystemGestureExclusionListener;
import android.view.IWindowManager;
import android.window.DesktopExperienceFlags;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.sysui.ShellInit;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowDecorationGestureExclusionTracker implements DisplayController.OnDisplaysChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final DisplayController displayController;
    public final WindowDecorationGestureExclusionTracker$exclusionListener$1 exclusionListener;
    public final Region exclusionRegion = new Region();
    public final WindowDecorationGestureExclusionTracker$exclusionRegions$1 exclusionRegions = new WindowDecorationGestureExclusionTracker$exclusionRegions$1(this);
    public final ShellExecutor mainExecutor;
    public final IWindowManager windowManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.wm.shell.windowdecor.common.WindowDecorationGestureExclusionTracker$exclusionListener$1] */
    public WindowDecorationGestureExclusionTracker(Context context, IWindowManager iWindowManager, DisplayController displayController, ShellExecutor shellExecutor, ShellInit shellInit, final Function2 function2) {
        this.context = context;
        this.windowManager = iWindowManager;
        this.displayController = displayController;
        this.mainExecutor = shellExecutor;
        this.exclusionListener = new ISystemGestureExclusionListener.Stub() { // from class: com.android.wm.shell.windowdecor.common.WindowDecorationGestureExclusionTracker$exclusionListener$1
            public final void onSystemGestureExclusionChanged(final int i, final Region region, Region region2) {
                if (DesktopExperienceFlags.ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY.isTrue()) {
                    final WindowDecorationGestureExclusionTracker windowDecorationGestureExclusionTracker = WindowDecorationGestureExclusionTracker.this;
                    ShellExecutor shellExecutor2 = windowDecorationGestureExclusionTracker.mainExecutor;
                    final Function2 function22 = function2;
                    shellExecutor2.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.common.WindowDecorationGestureExclusionTracker$exclusionListener$1$onSystemGestureExclusionChanged$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ((Region) WindowDecorationGestureExclusionTracker.this.exclusionRegions.get(Integer.valueOf(i))).set(region);
                            function22.invoke(Integer.valueOf(i), WindowDecorationGestureExclusionTracker.this.exclusionRegions.get(Integer.valueOf(i)));
                        }
                    });
                    return;
                }
                if (WindowDecorationGestureExclusionTracker.this.context.getDisplayId() != i) {
                    return;
                }
                final WindowDecorationGestureExclusionTracker windowDecorationGestureExclusionTracker2 = WindowDecorationGestureExclusionTracker.this;
                ShellExecutor shellExecutor3 = windowDecorationGestureExclusionTracker2.mainExecutor;
                final Function2 function23 = function2;
                shellExecutor3.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.common.WindowDecorationGestureExclusionTracker$exclusionListener$1$onSystemGestureExclusionChanged$2
                    @Override // java.lang.Runnable
                    public final void run() {
                        WindowDecorationGestureExclusionTracker.this.exclusionRegion.set(region);
                        function23.invoke(Integer.valueOf(i), WindowDecorationGestureExclusionTracker.this.exclusionRegion);
                    }
                });
            }
        };
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.windowdecor.common.WindowDecorationGestureExclusionTracker.1
            @Override // java.lang.Runnable
            public final void run() {
                WindowDecorationGestureExclusionTracker windowDecorationGestureExclusionTracker = WindowDecorationGestureExclusionTracker.this;
                int i = WindowDecorationGestureExclusionTracker.$r8$clinit;
                windowDecorationGestureExclusionTracker.getClass();
                if (DesktopExperienceFlags.ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY.isTrue()) {
                    windowDecorationGestureExclusionTracker.displayController.addDisplayWindowListener(windowDecorationGestureExclusionTracker, -1);
                    return;
                }
                try {
                    windowDecorationGestureExclusionTracker.windowManager.registerSystemGestureExclusionListener(windowDecorationGestureExclusionTracker.exclusionListener, windowDecorationGestureExclusionTracker.context.getDisplayId());
                    Unit unit = Unit.INSTANCE;
                } catch (RemoteException e) {
                    Slog.e("WindowDecorGestureExclTracker", "Failed to register window manager callbacks for display: " + windowDecorationGestureExclusionTracker.context.getDisplayId(), e);
                }
            }
        }, this);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        try {
            this.windowManager.registerSystemGestureExclusionListener(this.exclusionListener, i);
            this.exclusionRegions.put(Integer.valueOf(i), new Region());
        } catch (RemoteException e) {
            Slog.e("WindowDecorGestureExclTracker", "Failed to register window manager callbacks for display: " + i, e);
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        try {
            this.windowManager.unregisterSystemGestureExclusionListener(this.exclusionListener, i);
        } catch (Exception e) {
            if (!(e instanceof IllegalArgumentException) && !(e instanceof RemoteException)) {
                throw e;
            }
            Slog.e("WindowDecorGestureExclTracker", "Failed to unregister window manager callbacks for display: " + i, e);
            this.exclusionRegions.remove(Integer.valueOf(i));
        }
    }

    public final String toString() {
        return this.exclusionRegions.toString();
    }
}
