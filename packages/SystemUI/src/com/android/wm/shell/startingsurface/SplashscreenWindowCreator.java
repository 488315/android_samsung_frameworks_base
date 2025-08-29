package com.android.wm.shell.startingsurface;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Debug;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.window.SplashScreenView;
import android.window.StartingWindowRemovalInfo;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda24;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda37;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.startingsurface.SplashscreenWindowCreator;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;
import java.util.ArrayList;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class SplashscreenWindowCreator extends AbsSplashWindowCreator {
    public final SparseArray mAnimatedSplashScreenSurfaceHosts;
    public Choreographer mChoreographer;
    public final WindowManagerGlobal mWindowManagerGlobal;

    public class SplashScreenViewSupplier implements Supplier {
        public boolean mIsViewSet;
        public Runnable mUiThreadInitTask;
        public SplashScreenView mView;

        public /* synthetic */ SplashScreenViewSupplier(int i) {
            this();
        }

        private SplashScreenViewSupplier() {
        }

        @Override // java.util.function.Supplier
        public final SplashScreenView get() {
            SplashScreenView splashScreenView;
            synchronized (this) {
                while (!this.mIsViewSet) {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                    }
                }
                Runnable runnable = this.mUiThreadInitTask;
                if (runnable != null) {
                    runnable.run();
                    this.mUiThreadInitTask = null;
                }
                splashScreenView = this.mView;
            }
            return splashScreenView;
        }
    }

    public class SplashWindowRecord extends StartingSurfaceDrawer.StartingWindowRecord {
        public final IBinder mAppToken;
        public final long mCreateTime = SystemClock.uptimeMillis();
        public final View mRootView;
        public boolean mSetSplashScreen;
        public SplashScreenView mSplashView;
        public final int mSuggestType;

        public SplashWindowRecord(IBinder iBinder, View view, int i) {
            this.mAppToken = iBinder;
            this.mRootView = view;
            this.mSuggestType = i;
        }

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.StartingWindowRecord
        public final boolean removeIfPossible(final StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
            View view = this.mRootView;
            if (view == null) {
                return true;
            }
            SplashScreenView splashScreenView = this.mSplashView;
            SplashscreenWindowCreator splashscreenWindowCreator = SplashscreenWindowCreator.this;
            if (splashScreenView != null) {
                if (z || this.mSuggestType == 4) {
                    SplashscreenWindowCreator.m3280$$Nest$mremoveWindowInner(splashscreenWindowCreator, view, startingWindowRemovalInfo, false);
                    return true;
                }
                if (startingWindowRemovalInfo.playRevealAnimation) {
                    splashscreenWindowCreator.mSplashscreenContentDrawer.applyExitAnimation(splashScreenView, startingWindowRemovalInfo.windowAnimationLeash, startingWindowRemovalInfo.mainFrame, new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$SplashWindowRecord$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SplashscreenWindowCreator.SplashWindowRecord splashWindowRecord = this.f$0;
                            StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRemovalInfo;
                            SplashscreenWindowCreator.m3280$$Nest$mremoveWindowInner(SplashscreenWindowCreator.this, splashWindowRecord.mRootView, startingWindowRemovalInfo2, true);
                        }
                    }, this.mCreateTime, startingWindowRemovalInfo.roundedCornerRadius);
                    return true;
                }
                SplashscreenWindowCreator.m3280$$Nest$mremoveWindowInner(splashscreenWindowCreator, view, startingWindowRemovalInfo, true);
                return true;
            }
            if (!view.isAttachedToWindow()) {
                ArrayList rootViews = splashscreenWindowCreator.mWindowManagerGlobal.getRootViews(this.mAppToken);
                int size = rootViews.size();
                int i = 0;
                while (i < size) {
                    Object obj = rootViews.get(i);
                    i++;
                    if (((ViewRootImpl) obj).getView() == this.mRootView) {
                        Slog.e("ShellStartingWindow", "Need to force-remove empty splash screen added to WM, info=" + startingWindowRemovalInfo + ", caller=" + Debug.getCallers(7));
                    }
                }
            }
            Slog.e("ShellStartingWindow", "Found empty splash screen, remove!");
            SplashscreenWindowCreator.m3280$$Nest$mremoveWindowInner(splashscreenWindowCreator, this.mRootView, startingWindowRemovalInfo, false);
            return true;
        }
    }

    /* renamed from: -$$Nest$mremoveWindowInner, reason: not valid java name */
    public static void m3280$$Nest$mremoveWindowInner(SplashscreenWindowCreator splashscreenWindowCreator, View view, StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
        CentralSurfacesImpl$$ExternalSyntheticLambda24 centralSurfacesImpl$$ExternalSyntheticLambda24 = splashscreenWindowCreator.mSysuiProxy;
        boolean z2 = false;
        if (centralSurfacesImpl$$ExternalSyntheticLambda24 != null) {
            UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfacesImpl$$ExternalSyntheticLambda24.f$0;
            centralSurfacesImpl.getClass();
            centralSurfacesImpl.mMainExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda37(centralSurfacesImpl, z2));
        }
        SurfaceControl surfaceControl = startingWindowRemovalInfo.windowAnimationLeash;
        if (surfaceControl != null && surfaceControl.isValid()) {
            startingWindowRemovalInfo.windowAnimationLeash.release();
        }
        if (view.getParent() == null) {
            Slog.w("ShellStartingWindow", "This root view has no parent, never been added to a ViewRootImpl?");
            return;
        }
        if (z) {
            view.setVisibility(8);
        }
        splashscreenWindowCreator.mWindowManagerGlobal.removeView(view, false);
    }

    public SplashscreenWindowCreator(SplashscreenContentDrawer splashscreenContentDrawer, Context context, ShellExecutor shellExecutor, DisplayManager displayManager, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
        super(splashscreenContentDrawer, context, shellExecutor, displayManager, startingWindowRecordManager);
        this.mAnimatedSplashScreenSurfaceHosts = new SparseArray(1);
        this.mSplashScreenExecutor.execute(new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                SplashscreenWindowCreator splashscreenWindowCreator = this.f$0;
                splashscreenWindowCreator.getClass();
                splashscreenWindowCreator.mChoreographer = Choreographer.getInstance();
            }
        });
        this.mWindowManagerGlobal = WindowManagerGlobal.getInstance();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0031 A[PHI: r5
      0x0031: PHI (r5v6 android.view.View) = (r5v4 android.view.View), (r5v7 android.view.View) binds: [B:20:0x0069, B:7:0x002f] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean addWindow(int i, IBinder iBinder, View view, Display display, WindowManager.LayoutParams layoutParams, int i2) throws Throwable {
        View view2;
        Context context = view.getContext();
        boolean z = false;
        try {
            try {
                Trace.traceBegin(32L, "addRootView");
                view2 = view;
                try {
                    this.mWindowManagerGlobal.addView(view2, layoutParams, display, (Window) null, context.getUserId());
                    Trace.traceEnd(32L);
                } catch (WindowManager.BadTokenException e) {
                    e = e;
                    Slog.w("ShellStartingWindow", iBinder + " already running, starting window not displayed. " + e.getMessage());
                    Trace.traceEnd(32L);
                    if (view2.getParent() == null) {
                    }
                    if (z) {
                    }
                    return z;
                }
            } catch (Throwable th) {
                th = th;
                Trace.traceEnd(32L);
                if (view2.getParent() == null) {
                    Slog.w("ShellStartingWindow", "view not successfully added to wm, removing view");
                    this.mWindowManagerGlobal.removeView(view2, true);
                }
                throw th;
            }
        } catch (WindowManager.BadTokenException e2) {
            e = e2;
            view2 = view;
        } catch (Throwable th2) {
            th = th2;
            view2 = view;
            Trace.traceEnd(32L);
            if (view2.getParent() == null) {
            }
            throw th;
        }
        if (view2.getParent() == null) {
            Slog.w("ShellStartingWindow", "view not successfully added to wm, removing view");
            this.mWindowManagerGlobal.removeView(view2, true);
        } else {
            z = true;
        }
        if (z) {
            StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager = this.mStartingWindowRecordManager;
            StartingWindowRemovalInfo startingWindowRemovalInfo = startingWindowRecordManager.mTmpRemovalInfo;
            startingWindowRemovalInfo.taskId = i;
            startingWindowRecordManager.removeWindow(startingWindowRemovalInfo, true);
            startingWindowRecordManager.addRecord(i, new SplashWindowRecord(iBinder, view2, i2));
        }
        return z;
    }

    public final void onAppSplashScreenViewRemoved(int i, boolean z) {
        SurfaceControlViewHost surfaceControlViewHost = (SurfaceControlViewHost) this.mAnimatedSplashScreenSurfaceHosts.get(i);
        if (surfaceControlViewHost == null) {
            return;
        }
        this.mAnimatedSplashScreenSurfaceHosts.remove(i);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -840513847677537573L, 4, z ? "Server cleaned up" : "App removed", Long.valueOf(i));
        }
        SplashScreenView.releaseIconHost(surfaceControlViewHost);
    }
}
