package com.android.p038wm.shell.startingsurface;

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
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.window.SplashScreenView;
import android.window.StartingWindowRemovalInfo;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda24;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.Iterator;
import java.util.function.Supplier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SplashscreenWindowCreator extends AbsSplashWindowCreator {
    public final SparseArray mAnimatedSplashScreenSurfaceHosts;
    public Choreographer mChoreographer;
    public final WindowManagerGlobal mWindowManagerGlobal;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SplashScreenViewSupplier implements Supplier {
        public boolean mIsViewSet;
        public Runnable mUiThreadInitTask;
        public SplashScreenView mView;

        private SplashScreenViewSupplier() {
        }

        public /* synthetic */ SplashScreenViewSupplier(int i) {
            this();
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SplashWindowRecord extends StartingSurfaceDrawer.StartingWindowRecord {
        public final IBinder mAppToken;
        public final long mCreateTime = SystemClock.uptimeMillis();
        public boolean mDrawsSystemBarBackgrounds;
        public final View mRootView;
        public boolean mSetSplashScreen;
        public SplashScreenView mSplashView;
        public final int mSuggestType;
        public int mSystemBarAppearance;

        public SplashWindowRecord(IBinder iBinder, View view, int i) {
            this.mAppToken = iBinder;
            this.mRootView = view;
            this.mSuggestType = i;
        }

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.StartingWindowRecord
        public final boolean removeIfPossible(StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
            View view = this.mRootView;
            if (view == null) {
                return true;
            }
            SplashScreenView splashScreenView = this.mSplashView;
            SplashscreenWindowCreator splashscreenWindowCreator = SplashscreenWindowCreator.this;
            if (splashScreenView == null) {
                if (!view.isAttachedToWindow()) {
                    Iterator it = splashscreenWindowCreator.mWindowManagerGlobal.getRootViews(this.mAppToken).iterator();
                    while (it.hasNext()) {
                        if (((ViewRootImpl) it.next()).getView() == view) {
                            Slog.e("ShellStartingWindow", "Force remove empty splash screen added to WM, info=" + startingWindowRemovalInfo + ", caller=" + Debug.getCallers(7));
                            splashscreenWindowCreator.removeWindowInner(view, false, true);
                            return true;
                        }
                    }
                }
                Slog.e("ShellStartingWindow", "Found empty splash screen, remove!");
                splashscreenWindowCreator.removeWindowInner(view, false, false);
                return true;
            }
            if (view != null && view.isAttachedToWindow()) {
                if (view.getLayoutParams() instanceof WindowManager.LayoutParams) {
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) view.getLayoutParams();
                    if (this.mDrawsSystemBarBackgrounds) {
                        layoutParams.flags |= VideoPlayer.MEDIA_ERROR_SYSTEM;
                    } else {
                        layoutParams.flags &= Integer.MAX_VALUE;
                    }
                    view.setLayoutParams(layoutParams);
                }
                view.getWindowInsetsController().setSystemBarsAppearance(this.mSystemBarAppearance, 24);
            }
            if (z || this.mSuggestType == 4) {
                splashscreenWindowCreator.removeWindowInner(view, false, false);
            } else if (startingWindowRemovalInfo.playRevealAnimation) {
                splashscreenWindowCreator.mSplashscreenContentDrawer.applyExitAnimation(this.mSplashView, startingWindowRemovalInfo.windowAnimationLeash, startingWindowRemovalInfo.mainFrame, new SplashscreenWindowCreator$$ExternalSyntheticLambda0(this, 1), this.mCreateTime, startingWindowRemovalInfo.roundedCornerRadius, startingWindowRemovalInfo.duration);
            } else {
                splashscreenWindowCreator.removeWindowInner(view, true, false);
            }
            return true;
        }
    }

    public SplashscreenWindowCreator(SplashscreenContentDrawer splashscreenContentDrawer, Context context, ShellExecutor shellExecutor, DisplayManager displayManager, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
        super(splashscreenContentDrawer, context, shellExecutor, displayManager, startingWindowRecordManager);
        this.mAnimatedSplashScreenSurfaceHosts = new SparseArray(1);
        ((HandlerExecutor) this.mSplashScreenExecutor).execute(new SplashscreenWindowCreator$$ExternalSyntheticLambda0(this, 0));
        this.mWindowManagerGlobal = WindowManagerGlobal.getInstance();
    }

    /* JADX WARN: Finally extract failed */
    /* JADX WARN: Removed duplicated region for block: B:8:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean addWindow(int i, IBinder iBinder, View view, Display display, WindowManager.LayoutParams layoutParams, int i2) {
        boolean z;
        boolean z2;
        WindowManagerGlobal windowManagerGlobal = this.mWindowManagerGlobal;
        Context context = view.getContext();
        try {
            try {
                Trace.traceBegin(32L, "addRootView");
                this.mWindowManagerGlobal.addView(view, layoutParams, display, (Window) null, context.getUserId());
                Trace.traceEnd(32L);
            } catch (WindowManager.BadTokenException e) {
                Slog.w("ShellStartingWindow", iBinder + " already running, starting window not displayed. " + e.getMessage());
                Trace.traceEnd(32L);
                z = view.getParent() != null ? true : true;
            }
            if (view.getParent() != null) {
                z = true;
                z2 = true;
                if (z2) {
                    StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager = this.mStartingWindowRecordManager;
                    StartingWindowRemovalInfo startingWindowRemovalInfo = startingWindowRecordManager.mTmpRemovalInfo;
                    startingWindowRemovalInfo.taskId = i;
                    startingWindowRecordManager.removeWindow(startingWindowRemovalInfo, z);
                    startingWindowRecordManager.mStartingWindowRecords.put(i, new SplashWindowRecord(iBinder, view, i2));
                }
                return z2;
            }
            Slog.w("ShellStartingWindow", "view not successfully added to wm, removing view");
            windowManagerGlobal.removeView(view, true);
            z2 = false;
            if (z2) {
            }
            return z2;
        } catch (Throwable th) {
            Trace.traceEnd(32L);
            if (view.getParent() == null) {
                Slog.w("ShellStartingWindow", "view not successfully added to wm, removing view");
                windowManagerGlobal.removeView(view, true);
            }
            throw th;
        }
    }

    public final void onAppSplashScreenViewRemoved(int i, boolean z) {
        SparseArray sparseArray = this.mAnimatedSplashScreenSurfaceHosts;
        SurfaceControlViewHost surfaceControlViewHost = (SurfaceControlViewHost) sparseArray.get(i);
        if (surfaceControlViewHost == null) {
            return;
        }
        sparseArray.remove(i);
        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -2017810598, 4, null, z ? "Server cleaned up" : "App removed", Long.valueOf(i));
        }
        SplashScreenView.releaseIconHost(surfaceControlViewHost);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void removeWindowInner(View view, boolean z, boolean z2) {
        CentralSurfacesImpl$$ExternalSyntheticLambda0 centralSurfacesImpl$$ExternalSyntheticLambda0 = this.mSysuiProxy;
        boolean z3 = false;
        Object[] objArr = 0;
        if (centralSurfacesImpl$$ExternalSyntheticLambda0 != null) {
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfacesImpl$$ExternalSyntheticLambda0.f$0;
            centralSurfacesImpl.getClass();
            ((ExecutorImpl) centralSurfacesImpl.mMainExecutor).execute(new CentralSurfacesImpl$$ExternalSyntheticLambda24(centralSurfacesImpl, z3, "ShellStartingWindow", objArr == true ? 1 : 0));
        }
        if (view.isAttachedToWindow() || z2) {
            WindowManagerGlobal windowManagerGlobal = this.mWindowManagerGlobal;
            if (z) {
                if (view.getViewRootImpl() != null) {
                    WindowManager.LayoutParams layoutParams = view.getViewRootImpl().mWindowAttributes;
                    layoutParams.alpha = 0.0f;
                    windowManagerGlobal.updateViewLayout(view, layoutParams);
                }
                view.setVisibility(8);
            }
            windowManagerGlobal.removeView(view, false);
        }
    }
}
