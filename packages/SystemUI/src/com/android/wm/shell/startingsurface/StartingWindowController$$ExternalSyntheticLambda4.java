package com.android.wm.shell.startingsurface;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.IWindow;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.view.WindowRelayoutResult;
import android.widget.FrameLayout;
import android.window.ClientWindowFrames;
import android.window.SnapshotDrawerUtils;
import android.window.SplashScreenView;
import android.window.StartingWindowInfo;
import android.window.StartingWindowRemovalInfo;
import android.window.TaskSnapshot;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.util.function.TriConsumer;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda24;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda37;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.startingsurface.SnapshotWindowCreator;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer.SplashViewBuilder;
import com.android.wm.shell.startingsurface.SplashscreenWindowCreator;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.startingsurface.WindowlessSnapshotWindowCreator.SnapshotWindowRecord;
import com.android.wm.shell.startingsurface.WindowlessSplashWindowCreator.SplashWindowRecord;
import com.android.wm.shell.startingsurface.phone.PhoneStartingWindowTypeAlgorithm;

/* loaded from: classes3.dex */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda4(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX WARN: Removed duplicated region for block: B:145:0x0403  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x040d  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0433  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x044e  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x04bb  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x05af  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x05c2  */
    /* JADX WARN: Removed duplicated region for block: B:223:0x05c5  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x05c9  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00dc  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws Throwable {
        int i;
        int i2;
        int i3;
        final int i4;
        Context contextCreateContext;
        int i5;
        int i6;
        int i7;
        boolean zAddWindow;
        int i8;
        String str;
        TaskSnapshot taskSnapshot;
        IWindow iWindow;
        TaskSnapshotWindow taskSnapshotWindow;
        int iAddToDisplay;
        int i9;
        int i10;
        TriConsumer triConsumer;
        Display display;
        Context contextCreateContext2;
        switch (this.$r8$classId) {
            case 0:
                StartingWindowController startingWindowController = (StartingWindowController) this.f$0;
                final StartingWindowInfo startingWindowInfo = (StartingWindowInfo) this.f$1;
                startingWindowController.getClass();
                Trace.traceBegin(32L, "addStartingWindow");
                ((PhoneStartingWindowTypeAlgorithm) startingWindowController.mStartingWindowTypeAlgorithm).getClass();
                int i11 = startingWindowInfo.startingWindowTypeParameter;
                boolean z = (i11 & 1) != 0;
                boolean z2 = (i11 & 2) != 0;
                boolean z3 = (i11 & 4) != 0;
                boolean z4 = (i11 & 8) != 0;
                boolean z5 = (i11 & 16) != 0;
                boolean z6 = (i11 & 32) != 0;
                boolean z7 = (Integer.MIN_VALUE & i11) != 0;
                boolean z8 = (i11 & 64) != 0;
                boolean z9 = (i11 & 256) != 0;
                boolean z10 = startingWindowInfo.taskInfo.topActivityType == 2;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, -3197097598673667676L, 1048575, Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(z3), Boolean.valueOf(z4), Boolean.valueOf(z5), Boolean.valueOf(z6), Boolean.valueOf(z7), Boolean.valueOf(z8), Boolean.valueOf(z9), Boolean.valueOf(z10));
                }
                if (z9) {
                    i = 5;
                } else {
                    if (!z10 && (!z3 || z || (z2 && !z5))) {
                        if (!z6) {
                            if (z7) {
                            }
                        }
                    } else if (z2) {
                        if (z4) {
                            if (startingWindowInfo.taskSnapshot != null) {
                                i = 2;
                            } else if (!z10) {
                                i = 3;
                            }
                        }
                        if (!z8 && !z10) {
                            i2 = z6 ? 3 : z7 ? 4 : 1;
                        }
                        i = 0;
                    } else {
                        i = 0;
                    }
                    i = i2;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = startingWindowInfo.taskInfo;
                if (i != 5) {
                    if (i == 1 || i == 3 || i == 4) {
                        i3 = i;
                        final SplashscreenWindowCreator splashscreenWindowCreator = startingWindowController.mStartingSurfaceDrawer.mSplashscreenWindowCreator;
                        splashscreenWindowCreator.getClass();
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = startingWindowInfo.taskInfo;
                        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
                        if (activityInfo == null) {
                            activityInfo = runningTaskInfo2.topActivityInfo;
                        }
                        if (activityInfo != null && activityInfo.packageName != null) {
                            int splashScreenTheme = AbsSplashWindowCreator.getSplashScreenTheme(startingWindowInfo.splashScreenThemeResId, activityInfo);
                            final SplashscreenContentDrawer splashscreenContentDrawer = splashscreenWindowCreator.mSplashscreenContentDrawer;
                            SplashscreenContentDrawer.PreloadIconData preloadIconData = splashscreenContentDrawer.mPreloadIcon;
                            Context context = preloadIconData.mContext;
                            if (context != null && runningTaskInfo2.displayId == 0) {
                                int i12 = startingWindowInfo.splashScreenThemeResId;
                                if (preloadIconData.mIsPreloaded && i12 != 0 && i12 == context.getThemeResId() && runningTaskInfo2.getConfiguration().isNightModeActive() == preloadIconData.mContext.getResources().getConfiguration().isNightModeActive()) {
                                    contextCreateContext = splashscreenContentDrawer.mPreloadIcon.mContext;
                                    i4 = i3;
                                } else {
                                    i4 = i3;
                                    contextCreateContext = SplashscreenContentDrawer.createContext(splashscreenWindowCreator.mContext, startingWindowInfo, splashScreenTheme, i4, splashscreenWindowCreator.mDisplayManager);
                                }
                                final Context context2 = contextCreateContext;
                                if (context2 != null) {
                                    int i13 = i4;
                                    WindowManager.LayoutParams layoutParamsCreateLayoutParameters = SplashscreenContentDrawer.createLayoutParameters(context2, startingWindowInfo, i13, activityInfo.packageName, i4 == 4 ? -1 : -3, startingWindowInfo.appToken);
                                    int i14 = runningTaskInfo2.displayId;
                                    final int i15 = runningTaskInfo2.taskId;
                                    Display display2 = splashscreenWindowCreator.mDisplayManager.getDisplay(i14);
                                    final SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier = new SplashscreenWindowCreator.SplashScreenViewSupplier(0);
                                    final FrameLayout frameLayout = new FrameLayout(new ContextThemeWrapper(context2, splashscreenContentDrawer.mContext.getTheme()));
                                    frameLayout.setPadding(0, 0, 0, 0);
                                    frameLayout.setFitsSystemWindows(false);
                                    Runnable runnable = new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            SplashscreenWindowCreator splashscreenWindowCreator2 = splashscreenWindowCreator;
                                            SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier2 = splashScreenViewSupplier;
                                            int i16 = i15;
                                            StartingWindowInfo startingWindowInfo2 = startingWindowInfo;
                                            FrameLayout frameLayout2 = frameLayout;
                                            splashscreenWindowCreator2.getClass();
                                            Trace.traceBegin(32L, "addSplashScreenView");
                                            SplashScreenView splashScreenView = splashScreenViewSupplier2.get();
                                            StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord = (StartingSurfaceDrawer.StartingWindowRecord) splashscreenWindowCreator2.mStartingWindowRecordManager.mStartingWindowRecords.get(i16);
                                            SplashscreenWindowCreator.SplashWindowRecord splashWindowRecord = startingWindowRecord instanceof SplashscreenWindowCreator.SplashWindowRecord ? (SplashscreenWindowCreator.SplashWindowRecord) startingWindowRecord : null;
                                            if (splashWindowRecord != null && startingWindowInfo2.appToken == splashWindowRecord.mAppToken) {
                                                if (splashScreenView != null) {
                                                    try {
                                                        frameLayout2.addView(splashScreenView);
                                                    } catch (RuntimeException e) {
                                                        Slog.w("ShellStartingWindow", "failed set content view to starting window at taskId: " + i16, e);
                                                        splashScreenView = null;
                                                    }
                                                }
                                                if (!splashWindowRecord.mSetSplashScreen) {
                                                    splashWindowRecord.mSplashView = splashScreenView;
                                                    splashWindowRecord.mBGColor = splashScreenView != null ? splashScreenView.getInitBackgroundColor() : 0;
                                                    splashWindowRecord.mSetSplashScreen = true;
                                                }
                                            }
                                            Trace.traceEnd(32L);
                                        }
                                    };
                                    CentralSurfacesImpl$$ExternalSyntheticLambda24 centralSurfacesImpl$$ExternalSyntheticLambda24 = splashscreenWindowCreator.mSysuiProxy;
                                    if (centralSurfacesImpl$$ExternalSyntheticLambda24 != null) {
                                        UiEventLogger uiEventLogger = CentralSurfacesImpl.sUiEventLogger;
                                        CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfacesImpl$$ExternalSyntheticLambda24.f$0;
                                        centralSurfacesImpl.getClass();
                                        i5 = 1;
                                        centralSurfacesImpl.mMainExecutor.execute(new CentralSurfacesImpl$$ExternalSyntheticLambda37(centralSurfacesImpl, 1 == true ? 1 : 0));
                                    } else {
                                        i5 = 1;
                                    }
                                    final SplashscreenWindowCreator$$ExternalSyntheticLambda2 splashscreenWindowCreator$$ExternalSyntheticLambda2 = new SplashscreenWindowCreator$$ExternalSyntheticLambda2(splashScreenViewSupplier, 0);
                                    final SplashscreenWindowCreator$$ExternalSyntheticLambda2 splashscreenWindowCreator$$ExternalSyntheticLambda22 = new SplashscreenWindowCreator$$ExternalSyntheticLambda2(splashScreenViewSupplier, i5);
                                    i6 = 0;
                                    i4 = i13;
                                    splashscreenContentDrawer.mSplashscreenWorkerHandler.post(new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda7
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            SplashScreenView splashScreenViewMakeSplashScreenContentView;
                                            SplashscreenContentDrawer splashscreenContentDrawer2 = splashscreenContentDrawer;
                                            Context context3 = context2;
                                            StartingWindowInfo startingWindowInfo2 = startingWindowInfo;
                                            int i16 = i4;
                                            SplashscreenWindowCreator$$ExternalSyntheticLambda2 splashscreenWindowCreator$$ExternalSyntheticLambda23 = splashscreenWindowCreator$$ExternalSyntheticLambda22;
                                            SplashscreenWindowCreator$$ExternalSyntheticLambda2 splashscreenWindowCreator$$ExternalSyntheticLambda24 = splashscreenWindowCreator$$ExternalSyntheticLambda2;
                                            int i17 = SplashscreenContentDrawer.mThemeBackgroundColor;
                                            splashscreenContentDrawer2.getClass();
                                            try {
                                                Trace.traceBegin(32L, "makeSplashScreenContentView");
                                                splashScreenViewMakeSplashScreenContentView = splashscreenContentDrawer2.makeSplashScreenContentView(context3, startingWindowInfo2, i16, splashscreenWindowCreator$$ExternalSyntheticLambda23);
                                                Trace.traceEnd(32L);
                                            } catch (RuntimeException e) {
                                                Slog.w("ShellStartingWindow", "failed creating starting window content at taskId: " + startingWindowInfo2.taskInfo.taskId, e);
                                                splashScreenViewMakeSplashScreenContentView = null;
                                            }
                                            splashscreenWindowCreator$$ExternalSyntheticLambda24.accept(splashScreenViewMakeSplashScreenContentView);
                                        }
                                    });
                                    try {
                                        i7 = i15;
                                        try {
                                            zAddWindow = splashscreenWindowCreator.addWindow(i7, startingWindowInfo.appToken, frameLayout, display2, layoutParamsCreateLayoutParameters, i4);
                                            i4 = i4;
                                        } catch (RuntimeException e) {
                                            e = e;
                                            i4 = i4;
                                        }
                                    } catch (RuntimeException e2) {
                                        e = e2;
                                        i7 = i15;
                                    }
                                    try {
                                        if (zAddWindow) {
                                            splashscreenWindowCreator.mChoreographer.postCallback(2, runnable, null);
                                            if (((SplashscreenWindowCreator.SplashWindowRecord) ((StartingSurfaceDrawer.StartingWindowRecord) splashscreenWindowCreator.mStartingWindowRecordManager.mStartingWindowRecords.get(i7))) != null) {
                                                SplashScreenView splashScreenView = splashScreenViewSupplier.get();
                                                if (i4 != 4) {
                                                    splashScreenView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(splashscreenWindowCreator, splashScreenView) { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator.1
                                                        public final /* synthetic */ SplashScreenView val$contentView;

                                                        public AnonymousClass1(final SplashscreenWindowCreator splashscreenWindowCreator2, SplashScreenView splashScreenView2) {
                                                            this.val$contentView = splashScreenView2;
                                                        }

                                                        @Override // android.view.View.OnAttachStateChangeListener
                                                        public final void onViewAttachedToWindow(View view) {
                                                            this.val$contentView.getWindowInsetsController().setSystemBarsAppearance(ContrastColorUtil.isColorLight(this.val$contentView.getInitBackgroundColor()) ? 24 : 0, 24);
                                                        }

                                                        @Override // android.view.View.OnAttachStateChangeListener
                                                        public final void onViewDetachedFromWindow(View view) {
                                                        }
                                                    });
                                                }
                                            }
                                        } else {
                                            SplashScreenView splashScreenView2 = splashScreenViewSupplier.get();
                                            if (splashScreenView2.getSurfaceHost() != null) {
                                                SplashScreenView.releaseIconHost(splashScreenView2.getSurfaceHost());
                                            }
                                        }
                                    } catch (RuntimeException e3) {
                                        e = e3;
                                        Slog.w("ShellStartingWindow", "failed creating starting window at taskId: " + i7, e);
                                        if (i4 != 0) {
                                        }
                                        Trace.traceEnd(32L);
                                        return;
                                    }
                                }
                            }
                            if (i4 != 0 && i4 != 5) {
                                i9 = runningTaskInfo.taskId;
                                StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord = (StartingSurfaceDrawer.StartingWindowRecord) startingWindowController.mStartingSurfaceDrawer.mWindowRecords.mStartingWindowRecords.get(i9);
                                i10 = startingWindowRecord != null ? i6 : startingWindowRecord.mBGColor;
                                if (i10 != 0) {
                                    synchronized (startingWindowController.mTaskBackgroundColors) {
                                        startingWindowController.mTaskBackgroundColors.append(i9, i10);
                                    }
                                }
                                triConsumer = startingWindowController.mTaskLaunchingCallback;
                                if (triConsumer != null && (i4 == 1 || i4 == 3 || i4 == 4)) {
                                    triConsumer.accept(Integer.valueOf(i9), Integer.valueOf(i4), Integer.valueOf(i10));
                                }
                            }
                            Trace.traceEnd(32L);
                            return;
                        }
                        i6 = 0;
                        if (i4 != 0) {
                            i9 = runningTaskInfo.taskId;
                            StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord2 = (StartingSurfaceDrawer.StartingWindowRecord) startingWindowController.mStartingSurfaceDrawer.mWindowRecords.mStartingWindowRecords.get(i9);
                            if (startingWindowRecord2 != null) {
                            }
                            if (i10 != 0) {
                            }
                            triConsumer = startingWindowController.mTaskLaunchingCallback;
                            if (triConsumer != null) {
                                triConsumer.accept(Integer.valueOf(i9), Integer.valueOf(i4), Integer.valueOf(i10));
                            }
                        }
                        Trace.traceEnd(32L);
                        return;
                    }
                    if (i == 2) {
                        TaskSnapshot taskSnapshot2 = startingWindowInfo.taskSnapshot;
                        final SnapshotWindowCreator snapshotWindowCreator = startingWindowController.mStartingSurfaceDrawer.mSnapshotWindowCreator;
                        snapshotWindowCreator.getClass();
                        final int i16 = startingWindowInfo.taskInfo.taskId;
                        StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager = snapshotWindowCreator.mStartingWindowRecordManager;
                        StartingWindowRemovalInfo startingWindowRemovalInfo = startingWindowRecordManager.mTmpRemovalInfo;
                        startingWindowRemovalInfo.taskId = i16;
                        startingWindowRecordManager.removeWindow(startingWindowRemovalInfo, true);
                        IBinder iBinder = startingWindowInfo.appToken;
                        Runnable runnable2 = new Runnable() { // from class: com.android.wm.shell.startingsurface.SnapshotWindowCreator$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SnapshotWindowCreator snapshotWindowCreator2 = snapshotWindowCreator;
                                int i17 = i16;
                                StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager2 = snapshotWindowCreator2.mStartingWindowRecordManager;
                                StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRecordManager2.mTmpRemovalInfo;
                                startingWindowRemovalInfo2.taskId = i17;
                                startingWindowRecordManager2.removeWindow(startingWindowRemovalInfo2, true);
                            }
                        };
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = startingWindowInfo.taskInfo;
                        int i17 = runningTaskInfo3.taskId;
                        if (runningTaskInfo3.getWindowingMode() == 2) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 5207035868351378455L, 0, null);
                            }
                            i8 = i16;
                            i3 = i;
                        } else {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_STARTING_WINDOW_enabled[1]) {
                                i3 = i;
                                str = "Failed to add snapshot starting window res=";
                                taskSnapshot = taskSnapshot2;
                                i8 = i16;
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 4824035779834623306L, 1, Long.valueOf(i17));
                            } else {
                                i8 = i16;
                                str = "Failed to add snapshot starting window res=";
                                i3 = i;
                                taskSnapshot = taskSnapshot2;
                            }
                            if (taskSnapshot.getHardwareBuffer() == null) {
                                Slog.e("ShellStartingWindow", "TaskSnapshotWindow no snapshot");
                            } else {
                                WindowManager.LayoutParams layoutParamsCreateLayoutParameters2 = SnapshotDrawerUtils.createLayoutParameters(startingWindowInfo, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i17, "SnapshotStartingWindow for taskId="), 3, taskSnapshot.getHardwareBuffer().getFormat(), iBinder);
                                if (layoutParamsCreateLayoutParameters2 == null) {
                                    Slog.e("ShellStartingWindow", "TaskSnapshotWindow no layoutParams");
                                } else {
                                    int orientation = taskSnapshot.getOrientation();
                                    int i18 = runningTaskInfo3.displayId;
                                    IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
                                    SurfaceControl surfaceControl = new SurfaceControl();
                                    ClientWindowFrames clientWindowFrames = new ClientWindowFrames();
                                    InsetsSourceControl.Array array = new InsetsSourceControl.Array();
                                    MergedConfiguration mergedConfiguration = new MergedConfiguration();
                                    taskSnapshot2 = taskSnapshot;
                                    String str2 = str;
                                    TaskSnapshotWindow taskSnapshotWindow2 = new TaskSnapshotWindow(taskSnapshot2, SnapshotDrawerUtils.getOrCreateTaskDescription(runningTaskInfo3), orientation, runnable2, snapshotWindowCreator.mMainExecutor);
                                    IWindow iWindow2 = taskSnapshotWindow2.mWindow;
                                    InsetsState insetsState = new InsetsState();
                                    InputChannel inputChannel = new InputChannel();
                                    float[] fArr = {1.0f};
                                    try {
                                        Trace.traceBegin(32L, "TaskSnapshot#addToDisplay");
                                        iWindow = iWindow2;
                                    } catch (RemoteException unused) {
                                        iWindow = iWindow2;
                                    }
                                    try {
                                        iAddToDisplay = windowSession.addToDisplay(iWindow, layoutParamsCreateLayoutParameters2, 8, i18, startingWindowInfo.requestedVisibleTypes, inputChannel, insetsState, array, new Rect(), fArr);
                                        Trace.traceEnd(32L);
                                    } catch (RemoteException unused2) {
                                        taskSnapshotWindow2.clearWindowSynced();
                                        try {
                                            Trace.traceBegin(32L, "TaskSnapshot#relayout");
                                            windowSession.relayout(iWindow, layoutParamsCreateLayoutParameters2, -1, -1, 0, 0, 0, 0, new WindowRelayoutResult(clientWindowFrames, mergedConfiguration, surfaceControl, insetsState, array));
                                            Trace.traceEnd(32L);
                                            Slog.d("ShellStartingWindow", "Relayout returned: frame=" + clientWindowFrames + ", attrs=" + layoutParamsCreateLayoutParameters2);
                                            if (surfaceControl.isValid()) {
                                            }
                                        } catch (RemoteException unused3) {
                                            taskSnapshotWindow2.clearWindowSynced();
                                            Slog.w("ShellStartingWindow", "Failed to relayout snapshot starting window");
                                        }
                                    }
                                    if (iAddToDisplay < 0) {
                                        Slog.w("ShellStartingWindow", str2 + iAddToDisplay);
                                    } else {
                                        Trace.traceBegin(32L, "TaskSnapshot#relayout");
                                        windowSession.relayout(iWindow, layoutParamsCreateLayoutParameters2, -1, -1, 0, 0, 0, 0, new WindowRelayoutResult(clientWindowFrames, mergedConfiguration, surfaceControl, insetsState, array));
                                        Trace.traceEnd(32L);
                                        Slog.d("ShellStartingWindow", "Relayout returned: frame=" + clientWindowFrames + ", attrs=" + layoutParamsCreateLayoutParameters2);
                                        if (surfaceControl.isValid()) {
                                            taskSnapshotWindow2.clearWindowSynced();
                                            Slog.w("ShellStartingWindow", "Unable to draw snapshot, no valid surface");
                                        } else {
                                            SnapshotDrawerUtils.drawSnapshotOnSurface(layoutParamsCreateLayoutParameters2, surfaceControl, taskSnapshot2, startingWindowInfo.taskBounds, true);
                                            taskSnapshotWindow2.mHasDrawn = true;
                                            try {
                                                taskSnapshotWindow2.mSession.finishDrawing(taskSnapshotWindow2.mWindow, (SurfaceControl.Transaction) null, Integer.MAX_VALUE);
                                            } catch (RemoteException unused4) {
                                                taskSnapshotWindow2.clearWindowSynced();
                                            }
                                            taskSnapshotWindow = taskSnapshotWindow2;
                                            if (taskSnapshotWindow == null) {
                                                startingWindowRecordManager.addRecord(i8, new SnapshotWindowCreator.SnapshotWindowRecord(taskSnapshotWindow, startingWindowInfo.taskInfo.topActivityType, snapshotWindowCreator.mMainExecutor, i8, snapshotWindowCreator.mStartingWindowRecordManager));
                                            } else if (taskSnapshot2 != null && taskSnapshot2.getHardwareBuffer() != null) {
                                                taskSnapshot2.getHardwareBuffer().close();
                                            }
                                        }
                                    }
                                }
                            }
                            taskSnapshot2 = taskSnapshot;
                        }
                        taskSnapshotWindow = null;
                        if (taskSnapshotWindow == null) {
                        }
                    }
                    i4 = i3;
                    i6 = 0;
                    if (i4 != 0) {
                    }
                    Trace.traceEnd(32L);
                    return;
                }
                StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController.mStartingSurfaceDrawer;
                startingSurfaceDrawer.getClass();
                TaskSnapshot taskSnapshot3 = startingWindowInfo.taskSnapshot;
                if (taskSnapshot3 != null) {
                    SurfaceControl surfaceControl2 = startingWindowInfo.rootSurface;
                    WindowlessSnapshotWindowCreator windowlessSnapshotWindowCreator = startingSurfaceDrawer.mWindowlessSnapshotWindowCreator;
                    windowlessSnapshotWindowCreator.getClass();
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = startingWindowInfo.taskInfo;
                    int i19 = runningTaskInfo4.taskId;
                    WindowManager.LayoutParams layoutParamsCreateLayoutParameters3 = SnapshotDrawerUtils.createLayoutParameters(startingWindowInfo, MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i19, "Windowless Snapshot "), 2038, taskSnapshot3.getHardwareBuffer().getFormat(), (IBinder) null);
                    if (layoutParamsCreateLayoutParameters3 != null) {
                        Display display3 = windowlessSnapshotWindowCreator.mDisplayManager.getDisplay(runningTaskInfo4.displayId);
                        StartingSurfaceDrawer.WindowlessStartingWindow windowlessStartingWindow = new StartingSurfaceDrawer.WindowlessStartingWindow(windowlessSnapshotWindowCreator.mContext.getResources().getConfiguration(), surfaceControl2);
                        SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(windowlessSnapshotWindowCreator.mContext, display3, windowlessStartingWindow, "WindowlessSnapshotWindowCreator");
                        Rect bounds = runningTaskInfo4.configuration.windowConfiguration.getBounds();
                        SplashscreenContentDrawer splashscreenContentDrawer2 = windowlessSnapshotWindowCreator.mSplashscreenContentDrawer;
                        Context context3 = windowlessSnapshotWindowCreator.mContext;
                        splashscreenContentDrawer2.getClass();
                        surfaceControlViewHost.setView(new FrameLayout(new ContextThemeWrapper(context3, splashscreenContentDrawer2.mContext.getTheme())), layoutParamsCreateLayoutParameters3);
                        SnapshotDrawerUtils.drawSnapshotOnSurface(layoutParamsCreateLayoutParameters3, windowlessStartingWindow.mChildSurface, taskSnapshot3, bounds, false);
                        windowlessSnapshotWindowCreator.mStartingWindowRecordManager.addRecord(i19, windowlessSnapshotWindowCreator.new SnapshotWindowRecord(surfaceControlViewHost, surfaceControl2, windowlessStartingWindow.mChildSurface, SnapshotDrawerUtils.getOrCreateTaskDescription(runningTaskInfo4).getBackgroundColor(), taskSnapshot3.hasImeSurface(), runningTaskInfo4.topActivityType, startingSurfaceDrawer.mSplashScreenExecutor, i19, windowlessSnapshotWindowCreator.mStartingWindowRecordManager));
                        startingWindowInfo.notifyAddComplete(windowlessStartingWindow.mChildSurface);
                    }
                } else {
                    SurfaceControl surfaceControl3 = startingWindowInfo.rootSurface;
                    WindowlessSplashWindowCreator windowlessSplashWindowCreator = startingSurfaceDrawer.mWindowlessSplashWindowCreator;
                    windowlessSplashWindowCreator.getClass();
                    ActivityManager.RunningTaskInfo runningTaskInfo5 = startingWindowInfo.taskInfo;
                    ActivityInfo activityInfo2 = startingWindowInfo.targetActivityInfo;
                    if (activityInfo2 == null) {
                        activityInfo2 = runningTaskInfo5.topActivityInfo;
                    }
                    if (activityInfo2 != null && activityInfo2.packageName != null && (display = windowlessSplashWindowCreator.mDisplayManager.getDisplay(runningTaskInfo5.displayId)) != null && (contextCreateContext2 = SplashscreenContentDrawer.createContext(windowlessSplashWindowCreator.mContext, startingWindowInfo, AbsSplashWindowCreator.getSplashScreenTheme(0, activityInfo2), 1, windowlessSplashWindowCreator.mDisplayManager)) != null) {
                        StartingSurfaceDrawer.WindowlessStartingWindow windowlessStartingWindow2 = new StartingSurfaceDrawer.WindowlessStartingWindow(windowlessSplashWindowCreator.mContext.getResources().getConfiguration(), surfaceControl3);
                        SurfaceControlViewHost surfaceControlViewHost2 = new SurfaceControlViewHost(contextCreateContext2, display, windowlessStartingWindow2, "WindowlessSplashWindowCreator");
                        WindowManager.LayoutParams layoutParamsCreateLayoutParameters4 = SplashscreenContentDrawer.createLayoutParameters(contextCreateContext2, startingWindowInfo, 1, "Windowless Splash " + runningTaskInfo5.taskId, -3, new Binder());
                        Rect bounds2 = runningTaskInfo5.configuration.windowConfiguration.getBounds();
                        layoutParamsCreateLayoutParameters4.width = bounds2.width();
                        layoutParamsCreateLayoutParameters4.height = bounds2.height();
                        SplashscreenContentDrawer splashscreenContentDrawer3 = windowlessSplashWindowCreator.mSplashscreenContentDrawer;
                        splashscreenContentDrawer3.getClass();
                        FrameLayout frameLayout2 = new FrameLayout(new ContextThemeWrapper(contextCreateContext2, splashscreenContentDrawer3.mContext.getTheme()));
                        surfaceControlViewHost2.setView(frameLayout2, layoutParamsCreateLayoutParameters4);
                        SplashscreenContentDrawer.SplashScreenWindowAttrs splashScreenWindowAttrs = new SplashscreenContentDrawer.SplashScreenWindowAttrs();
                        SplashscreenContentDrawer.getWindowAttrs(contextCreateContext2, splashScreenWindowAttrs);
                        int iPeekWindowBGColor = SplashscreenContentDrawer.peekWindowBGColor(contextCreateContext2, splashScreenWindowAttrs);
                        splashscreenContentDrawer3.updateDensity();
                        SplashscreenContentDrawer.SplashScreenWindowAttrs splashScreenWindowAttrs2 = splashscreenContentDrawer3.mTmpAttrs;
                        splashScreenWindowAttrs2.mWindowBgResId = 0;
                        splashScreenWindowAttrs2.mWindowBgColor = 0;
                        splashScreenWindowAttrs2.mSplashScreenIcon = null;
                        splashScreenWindowAttrs2.mBrandingImage = null;
                        splashScreenWindowAttrs2.mIconBgColor = 0;
                        ActivityInfo activityInfo3 = startingWindowInfo.targetActivityInfo;
                        if (activityInfo3 == null) {
                            activityInfo3 = startingWindowInfo.taskInfo.topActivityInfo;
                        }
                        SplashscreenContentDrawer.SplashViewBuilder splashViewBuilder = splashscreenContentDrawer3.new SplashViewBuilder(contextCreateContext2, activityInfo3);
                        splashViewBuilder.mThemeColor = iPeekWindowBGColor;
                        splashViewBuilder.mSuggestType = 3;
                        SplashScreenView splashScreenViewBuild = splashViewBuilder.build(false);
                        splashScreenViewBuild.setNotCopyable();
                        frameLayout2.addView(splashScreenViewBuild);
                        windowlessSplashWindowCreator.mStartingWindowRecordManager.addRecord(runningTaskInfo5.taskId, windowlessSplashWindowCreator.new SplashWindowRecord(surfaceControlViewHost2, splashScreenViewBuild, windowlessStartingWindow2.mChildSurface, iPeekWindowBGColor));
                        startingWindowInfo.notifyAddComplete(windowlessStartingWindow2.mChildSurface);
                    }
                }
                i4 = i;
                i6 = 0;
                if (i4 != 0) {
                }
                Trace.traceEnd(32L);
                return;
            default:
                StartingWindowController.StartingSurfaceImpl startingSurfaceImpl = (StartingWindowController.StartingSurfaceImpl) this.f$0;
                CentralSurfacesImpl$$ExternalSyntheticLambda24 centralSurfacesImpl$$ExternalSyntheticLambda242 = (CentralSurfacesImpl$$ExternalSyntheticLambda24) this.f$1;
                StartingSurfaceDrawer startingSurfaceDrawer2 = StartingWindowController.this.mStartingSurfaceDrawer;
                startingSurfaceDrawer2.mSplashscreenWindowCreator.mSysuiProxy = centralSurfacesImpl$$ExternalSyntheticLambda242;
                startingSurfaceDrawer2.mWindowlessSplashWindowCreator.mSysuiProxy = centralSurfacesImpl$$ExternalSyntheticLambda242;
                return;
        }
    }
}
