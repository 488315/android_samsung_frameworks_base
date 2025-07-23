package com.android.wm.shell.startingsurface;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Debug;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.WindowManagerGlobal;
import android.window.SplashScreenView;
import android.window.StartingWindowRemovalInfo;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda23;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda36;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.startingsurface.SplashscreenWindowCreator;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;
import java.util.ArrayList;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SplashscreenWindowCreator extends AbsSplashWindowCreator {
    public final SparseArray mAnimatedSplashScreenSurfaceHosts;
    public Choreographer mChoreographer;
    public final WindowManagerGlobal mWindowManagerGlobal;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    SplashscreenWindowCreator.m3263$$Nest$mremoveWindowInner(splashscreenWindowCreator, view, startingWindowRemovalInfo, false);
                    return true;
                }
                if (startingWindowRemovalInfo.playRevealAnimation) {
                    splashscreenWindowCreator.mSplashscreenContentDrawer.applyExitAnimation(splashScreenView, startingWindowRemovalInfo.windowAnimationLeash, startingWindowRemovalInfo.mainFrame, new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$SplashWindowRecord$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            SplashscreenWindowCreator.SplashWindowRecord splashWindowRecord = SplashscreenWindowCreator.SplashWindowRecord.this;
                            StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRemovalInfo;
                            SplashscreenWindowCreator.m3263$$Nest$mremoveWindowInner(SplashscreenWindowCreator.this, splashWindowRecord.mRootView, startingWindowRemovalInfo2, true);
                        }
                    }, this.mCreateTime, startingWindowRemovalInfo.roundedCornerRadius);
                    return true;
                }
                SplashscreenWindowCreator.m3263$$Nest$mremoveWindowInner(splashscreenWindowCreator, view, startingWindowRemovalInfo, true);
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
            SplashscreenWindowCreator.m3263$$Nest$mremoveWindowInner(splashscreenWindowCreator, this.mRootView, startingWindowRemovalInfo, false);
            return true;
        }
    }

    /* renamed from: -$$Nest$mremoveWindowInner, reason: not valid java name */
    public static void m3263$$Nest$mremoveWindowInner(SplashscreenWindowCreator splashscreenWindowCreator, View view, StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
        CentralSurfacesImpl$$ExternalSyntheticLambda23 centralSurfacesImpl$$ExternalSyntheticLambda23 = splashscreenWindowCreator.mSysuiProxy;
        boolean z2 = false;
        if (centralSurfacesImpl$$ExternalSyntheticLambda23 != null) {
            UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
            CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfacesImpl$$ExternalSyntheticLambda23.f$0;
            centralSurfacesImpl.getClass();
            centralSurfacesImpl.mMainExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda36(centralSurfacesImpl, z2));
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
                SplashscreenWindowCreator splashscreenWindowCreator = SplashscreenWindowCreator.this;
                splashscreenWindowCreator.getClass();
                splashscreenWindowCreator.mChoreographer = Choreographer.getInstance();
            }
        });
        this.mWindowManagerGlobal = WindowManagerGlobal.getInstance();
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0069, code lost:
    
        if (r5.getParent() != null) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:11:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addWindow(int r17, android.os.IBinder r18, android.view.View r19, android.view.Display r20, android.view.WindowManager.LayoutParams r21, int r22) {
        /*
            r16 = this;
            r1 = r16
            r2 = r17
            r3 = r18
            java.lang.String r10 = "view not successfully added to wm, removing view"
            java.lang.String r11 = "ShellStartingWindow"
            android.content.Context r0 = r19.getContext()
            r12 = 1
            r13 = 0
            r14 = 32
            java.lang.String r4 = "addRootView"
            android.os.Trace.traceBegin(r14, r4)     // Catch: java.lang.Throwable -> L40 android.view.WindowManager.BadTokenException -> L44
            android.view.WindowManagerGlobal r4 = r1.mWindowManagerGlobal     // Catch: java.lang.Throwable -> L40 android.view.WindowManager.BadTokenException -> L44
            int r9 = r0.getUserId()     // Catch: java.lang.Throwable -> L40 android.view.WindowManager.BadTokenException -> L44
            r8 = 0
            r5 = r19
            r7 = r20
            r6 = r21
            r4.addView(r5, r6, r7, r8, r9)     // Catch: java.lang.Throwable -> L3c android.view.WindowManager.BadTokenException -> L3e
            android.os.Trace.traceEnd(r14)
            android.view.ViewParent r0 = r5.getParent()
            if (r0 != 0) goto L3a
        L31:
            android.util.Slog.w(r11, r10)
            android.view.WindowManagerGlobal r0 = r1.mWindowManagerGlobal
            r0.removeView(r5, r12)
            goto L6c
        L3a:
            r13 = r12
            goto L6c
        L3c:
            r0 = move-exception
            goto L82
        L3e:
            r0 = move-exception
            goto L47
        L40:
            r0 = move-exception
            r5 = r19
            goto L82
        L44:
            r0 = move-exception
            r5 = r19
        L47:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L3c
            r4.<init>()     // Catch: java.lang.Throwable -> L3c
            r4.append(r3)     // Catch: java.lang.Throwable -> L3c
            java.lang.String r6 = " already running, starting window not displayed. "
            r4.append(r6)     // Catch: java.lang.Throwable -> L3c
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L3c
            r4.append(r0)     // Catch: java.lang.Throwable -> L3c
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L3c
            android.util.Slog.w(r11, r0)     // Catch: java.lang.Throwable -> L3c
            android.os.Trace.traceEnd(r14)
            android.view.ViewParent r0 = r5.getParent()
            if (r0 != 0) goto L6c
            goto L31
        L6c:
            if (r13 == 0) goto L81
            com.android.wm.shell.startingsurface.StartingSurfaceDrawer$StartingWindowRecordManager r0 = r1.mStartingWindowRecordManager
            android.window.StartingWindowRemovalInfo r4 = r0.mTmpRemovalInfo
            r4.taskId = r2
            r0.removeWindow(r4, r12)
            com.android.wm.shell.startingsurface.SplashscreenWindowCreator$SplashWindowRecord r4 = new com.android.wm.shell.startingsurface.SplashscreenWindowCreator$SplashWindowRecord
            r6 = r22
            r4.<init>(r3, r5, r6)
            r0.addRecord(r2, r4)
        L81:
            return r13
        L82:
            android.os.Trace.traceEnd(r14)
            android.view.ViewParent r2 = r5.getParent()
            if (r2 != 0) goto L93
            android.util.Slog.w(r11, r10)
            android.view.WindowManagerGlobal r1 = r1.mWindowManagerGlobal
            r1.removeView(r5, r12)
        L93:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.startingsurface.SplashscreenWindowCreator.addWindow(int, android.os.IBinder, android.view.View, android.view.Display, android.view.WindowManager$LayoutParams, int):boolean");
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
