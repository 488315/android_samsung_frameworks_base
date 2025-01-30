package com.android.p038wm.shell.startingsurface;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Trace;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.MergedConfiguration;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.IWindowSession;
import android.view.InputChannel;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.FrameLayout;
import android.window.ClientWindowFrames;
import android.window.SnapshotDrawerUtils;
import android.window.SplashScreenView;
import android.window.StartingWindowInfo;
import android.window.StartingWindowRemovalInfo;
import android.window.TaskSnapshot;
import com.android.internal.R;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.util.function.TriConsumer;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.startingsurface.SnapshotWindowCreator;
import com.android.p038wm.shell.startingsurface.SplashscreenContentDrawer;
import com.android.p038wm.shell.startingsurface.SplashscreenWindowCreator;
import com.android.p038wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.p038wm.shell.startingsurface.StartingWindowController;
import com.android.p038wm.shell.startingsurface.TaskSnapshotWindow;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl$$ExternalSyntheticLambda24;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.wm.shell.startingsurface.SplashscreenContentDrawer.SplashViewBuilder;
import com.android.wm.shell.startingsurface.WindowlessSnapshotWindowCreator.SnapshotWindowRecord;
import com.android.wm.shell.startingsurface.WindowlessSplashWindowCreator.SplashWindowRecord;
import java.lang.ref.WeakReference;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StartingWindowController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ StartingWindowController$$ExternalSyntheticLambda1(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    /* JADX WARN: Removed duplicated region for block: B:132:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x039b  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x056a  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x05a7  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x05b8  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x05a3  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0206  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0274  */
    /* JADX WARN: Type inference failed for: r7v12, types: [com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r8v5, types: [com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda2] */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 2 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        int i;
        boolean z;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        StartingWindowController startingWindowController;
        int i2;
        int i3;
        TaskSnapshotWindow taskSnapshotWindow;
        int addToDisplay;
        boolean z2;
        final Context createContext;
        final int i4;
        int i5;
        int i6;
        int i7;
        TriConsumer triConsumer;
        int i8;
        switch (this.$r8$classId) {
            case 0:
                StartingWindowController startingWindowController2 = (StartingWindowController) this.f$0;
                final StartingWindowInfo startingWindowInfo = (StartingWindowInfo) this.f$1;
                startingWindowController2.getClass();
                Trace.traceBegin(32L, "addStartingWindow");
                final int suggestedWindowType = startingWindowController2.mStartingWindowTypeAlgorithm.getSuggestedWindowType(startingWindowInfo);
                ActivityManager.RunningTaskInfo runningTaskInfo2 = startingWindowInfo.taskInfo;
                TaskSnapshotWindow taskSnapshotWindow2 = null;
                final int i9 = 0;
                if (suggestedWindowType == 5) {
                    StartingSurfaceDrawer startingSurfaceDrawer = startingWindowController2.mStartingSurfaceDrawer;
                    startingSurfaceDrawer.getClass();
                    TaskSnapshot taskSnapshot = startingWindowInfo.taskSnapshot;
                    if (taskSnapshot != null) {
                        SurfaceControl surfaceControl = startingWindowInfo.rootSurface;
                        ShellExecutor shellExecutor = startingSurfaceDrawer.mSplashScreenExecutor;
                        WindowlessSnapshotWindowCreator windowlessSnapshotWindowCreator = startingSurfaceDrawer.mWindowlessSnapshotWindowCreator;
                        windowlessSnapshotWindowCreator.getClass();
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = startingWindowInfo.taskInfo;
                        int i10 = runningTaskInfo3.taskId;
                        WindowManager.LayoutParams createLayoutParameters = SnapshotDrawerUtils.createLayoutParameters(startingWindowInfo, AbstractC0000x2c234b15.m0m("Windowless Snapshot ", i10), 2038, taskSnapshot.getHardwareBuffer().getFormat(), (IBinder) null);
                        if (createLayoutParameters == null) {
                            startingWindowController = startingWindowController2;
                            i2 = suggestedWindowType;
                            runningTaskInfo = runningTaskInfo2;
                        } else {
                            Display display = windowlessSnapshotWindowCreator.mDisplayManager.getDisplay(runningTaskInfo3.displayId);
                            StartingSurfaceDrawer.WindowlessStartingWindow windowlessStartingWindow = new StartingSurfaceDrawer.WindowlessStartingWindow(runningTaskInfo3.configuration, surfaceControl);
                            Context context = windowlessSnapshotWindowCreator.mContext;
                            SurfaceControlViewHost surfaceControlViewHost = new SurfaceControlViewHost(context, display, windowlessStartingWindow, "WindowlessSnapshotWindowCreator");
                            Point taskSize = taskSnapshot.getTaskSize();
                            Rect rect = new Rect(0, 0, taskSize.x, taskSize.y);
                            Rect bounds = runningTaskInfo3.configuration.windowConfiguration.getBounds();
                            InsetsState insetsState = startingWindowInfo.topOpaqueWindowInsetsState;
                            SplashscreenContentDrawer splashscreenContentDrawer = windowlessSnapshotWindowCreator.mSplashscreenContentDrawer;
                            splashscreenContentDrawer.getClass();
                            surfaceControlViewHost.setView(new FrameLayout(new ContextThemeWrapper(context, splashscreenContentDrawer.mContext.getTheme())), createLayoutParameters);
                            SnapshotDrawerUtils.drawSnapshotOnSurface(startingWindowInfo, createLayoutParameters, windowlessStartingWindow.mChildSurface, taskSnapshot, rect, bounds, insetsState, false);
                            windowlessSnapshotWindowCreator.mStartingWindowRecordManager.mStartingWindowRecords.put(i10, windowlessSnapshotWindowCreator.new SnapshotWindowRecord(surfaceControlViewHost, windowlessStartingWindow.mChildSurface, SnapshotDrawerUtils.getOrCreateTaskDescription(runningTaskInfo3).getBackgroundColor(), taskSnapshot.hasImeSurface(), runningTaskInfo3.topActivityType, shellExecutor, i10, windowlessSnapshotWindowCreator.mStartingWindowRecordManager));
                            startingWindowInfo.notifyAddComplete(windowlessStartingWindow.mChildSurface);
                        }
                    } else {
                        SurfaceControl surfaceControl2 = startingWindowInfo.rootSurface;
                        WindowlessSplashWindowCreator windowlessSplashWindowCreator = startingSurfaceDrawer.mWindowlessSplashWindowCreator;
                        windowlessSplashWindowCreator.getClass();
                        ActivityManager.RunningTaskInfo runningTaskInfo4 = startingWindowInfo.taskInfo;
                        ActivityInfo activityInfo = startingWindowInfo.targetActivityInfo;
                        if (activityInfo == null) {
                            activityInfo = runningTaskInfo4.topActivityInfo;
                        }
                        if (activityInfo != null && activityInfo.packageName != null) {
                            int i11 = runningTaskInfo4.displayId;
                            DisplayManager displayManager = windowlessSplashWindowCreator.mDisplayManager;
                            Display display2 = displayManager.getDisplay(i11);
                            if (display2 != null) {
                                Context context2 = windowlessSplashWindowCreator.mContext;
                                Context createContext2 = SplashscreenContentDrawer.createContext(context2, startingWindowInfo, 0, 1, displayManager);
                                if (createContext2 == null) {
                                    i8 = 0;
                                } else {
                                    StartingSurfaceDrawer.WindowlessStartingWindow windowlessStartingWindow2 = new StartingSurfaceDrawer.WindowlessStartingWindow(runningTaskInfo4.configuration, surfaceControl2);
                                    SurfaceControlViewHost surfaceControlViewHost2 = new SurfaceControlViewHost(createContext2, display2, windowlessStartingWindow2, "WindowlessSplashWindowCreator");
                                    WindowManager.LayoutParams createLayoutParameters2 = SplashscreenContentDrawer.createLayoutParameters(createContext2, startingWindowInfo, 1, "Windowless Splash " + runningTaskInfo4.taskId, -3, new Binder());
                                    Rect bounds2 = runningTaskInfo4.configuration.windowConfiguration.getBounds();
                                    createLayoutParameters2.width = bounds2.width();
                                    createLayoutParameters2.height = bounds2.height();
                                    ActivityManager.TaskDescription taskDescription = runningTaskInfo4.taskDescription;
                                    if (taskDescription == null) {
                                        taskDescription = new ActivityManager.TaskDescription();
                                        taskDescription.setBackgroundColor(-1);
                                    }
                                    SplashscreenContentDrawer splashscreenContentDrawer2 = windowlessSplashWindowCreator.mSplashscreenContentDrawer;
                                    splashscreenContentDrawer2.getClass();
                                    FrameLayout frameLayout = new FrameLayout(new ContextThemeWrapper(context2, splashscreenContentDrawer2.mContext.getTheme()));
                                    surfaceControlViewHost2.setView(frameLayout, createLayoutParameters2);
                                    int backgroundColor = taskDescription.getBackgroundColor();
                                    splashscreenContentDrawer2.updateDensity();
                                    SplashscreenContentDrawer.SplashScreenWindowAttrs splashScreenWindowAttrs = splashscreenContentDrawer2.mTmpAttrs;
                                    splashScreenWindowAttrs.mWindowBgResId = 0;
                                    splashScreenWindowAttrs.mWindowBgColor = 0;
                                    splashScreenWindowAttrs.mSplashScreenIcon = null;
                                    splashScreenWindowAttrs.mBrandingImage = null;
                                    splashScreenWindowAttrs.mIconBgColor = 0;
                                    splashScreenWindowAttrs.mWindowBackground = null;
                                    ActivityInfo activityInfo2 = startingWindowInfo.targetActivityInfo;
                                    if (activityInfo2 == null) {
                                        activityInfo2 = startingWindowInfo.taskInfo.topActivityInfo;
                                    }
                                    SplashscreenContentDrawer.SplashViewBuilder splashViewBuilder = splashscreenContentDrawer2.new SplashViewBuilder(createContext2, activityInfo2);
                                    splashViewBuilder.mThemeColor = backgroundColor;
                                    splashViewBuilder.mSuggestType = 1;
                                    i8 = 0;
                                    SplashScreenView build = splashViewBuilder.build(false);
                                    build.setNotCopyable();
                                    frameLayout.addView(build);
                                    windowlessSplashWindowCreator.mStartingWindowRecordManager.mStartingWindowRecords.put(runningTaskInfo4.taskId, windowlessSplashWindowCreator.new SplashWindowRecord(surfaceControlViewHost2, build, windowlessStartingWindow2.mChildSurface, backgroundColor));
                                    startingWindowInfo.notifyAddComplete(windowlessStartingWindow2.mChildSurface);
                                }
                                i9 = i8;
                                runningTaskInfo = runningTaskInfo2;
                                startingWindowController = startingWindowController2;
                                i2 = suggestedWindowType;
                            }
                        }
                    }
                    startingWindowController = startingWindowController2;
                    i2 = suggestedWindowType;
                    runningTaskInfo = runningTaskInfo2;
                    i9 = 0;
                } else {
                    if (suggestedWindowType == 1 || suggestedWindowType == 3) {
                        i = 4;
                    } else {
                        i = 4;
                        if (suggestedWindowType != 4) {
                            z = false;
                            if (z) {
                                runningTaskInfo = runningTaskInfo2;
                                if (suggestedWindowType == 2) {
                                    TaskSnapshot taskSnapshot2 = startingWindowInfo.taskSnapshot;
                                    final SnapshotWindowCreator snapshotWindowCreator = startingWindowController2.mStartingSurfaceDrawer.mSnapshotWindowCreator;
                                    snapshotWindowCreator.getClass();
                                    final int i12 = startingWindowInfo.taskInfo.taskId;
                                    StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager = snapshotWindowCreator.mStartingWindowRecordManager;
                                    StartingWindowRemovalInfo startingWindowRemovalInfo = startingWindowRecordManager.mTmpRemovalInfo;
                                    startingWindowRemovalInfo.taskId = i12;
                                    startingWindowRecordManager.removeWindow(startingWindowRemovalInfo, true);
                                    IBinder iBinder = startingWindowInfo.appToken;
                                    ShellExecutor shellExecutor2 = snapshotWindowCreator.mMainExecutor;
                                    Runnable runnable = new Runnable() { // from class: com.android.wm.shell.startingsurface.SnapshotWindowCreator$$ExternalSyntheticLambda0
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            SnapshotWindowCreator snapshotWindowCreator2 = SnapshotWindowCreator.this;
                                            int i13 = i12;
                                            StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager2 = snapshotWindowCreator2.mStartingWindowRecordManager;
                                            StartingWindowRemovalInfo startingWindowRemovalInfo2 = startingWindowRecordManager2.mTmpRemovalInfo;
                                            startingWindowRemovalInfo2.taskId = i13;
                                            startingWindowRecordManager2.removeWindow(startingWindowRemovalInfo2, true);
                                        }
                                    };
                                    ActivityManager.RunningTaskInfo runningTaskInfo5 = startingWindowInfo.taskInfo;
                                    int i13 = runningTaskInfo5.taskId;
                                    if (runningTaskInfo5.getWindowingMode() != 2) {
                                        if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                                            startingWindowController = startingWindowController2;
                                            i2 = suggestedWindowType;
                                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 1037658567, 1, null, Long.valueOf(i13));
                                        } else {
                                            startingWindowController = startingWindowController2;
                                            i2 = suggestedWindowType;
                                        }
                                        InsetsState insetsState2 = startingWindowInfo.topOpaqueWindowInsetsState;
                                        WindowManager.LayoutParams createLayoutParameters3 = SnapshotDrawerUtils.createLayoutParameters(startingWindowInfo, AbstractC0000x2c234b15.m0m("SnapshotStartingWindow for taskId=", i13), 3, taskSnapshot2.getHardwareBuffer().getFormat(), iBinder);
                                        if (createLayoutParameters3 == null) {
                                            Slog.e("ShellStartingWindow", "TaskSnapshotWindow no layoutParams");
                                        } else {
                                            Point taskSize2 = taskSnapshot2.getTaskSize();
                                            Rect rect2 = new Rect(0, 0, taskSize2.x, taskSize2.y);
                                            int orientation = taskSnapshot2.getOrientation();
                                            int i14 = runningTaskInfo5.displayId;
                                            IWindowSession windowSession = WindowManagerGlobal.getWindowSession();
                                            SurfaceControl surfaceControl3 = new SurfaceControl();
                                            ClientWindowFrames clientWindowFrames = new ClientWindowFrames();
                                            InsetsSourceControl.Array array = new InsetsSourceControl.Array();
                                            MergedConfiguration mergedConfiguration = new MergedConfiguration();
                                            TaskSnapshotWindow taskSnapshotWindow3 = new TaskSnapshotWindow(taskSnapshot2, SnapshotDrawerUtils.getOrCreateTaskDescription(runningTaskInfo5), orientation, runnable, shellExecutor2);
                                            TaskSnapshotWindow.Window window = taskSnapshotWindow3.mWindow;
                                            InsetsState insetsState3 = new InsetsState();
                                            InputChannel inputChannel = new InputChannel();
                                            i3 = 0;
                                            float[] fArr = {1.0f};
                                            try {
                                                Trace.traceBegin(32L, "TaskSnapshot#addToDisplay");
                                                addToDisplay = windowSession.addToDisplay(window, createLayoutParameters3, 8, i14, startingWindowInfo.requestedVisibleTypes, inputChannel, insetsState3, array, new Rect(), fArr);
                                                Trace.traceEnd(32L);
                                            } catch (RemoteException unused) {
                                                ((HandlerExecutor) taskSnapshotWindow3.mSplashScreenExecutor).executeDelayed(0L, taskSnapshotWindow3.mClearWindowHandler);
                                            }
                                            if (addToDisplay < 0) {
                                                Slog.w("ShellStartingWindow", "Failed to add snapshot starting window res=" + addToDisplay);
                                                taskSnapshotWindow2 = null;
                                                taskSnapshotWindow = taskSnapshotWindow2;
                                                if (taskSnapshotWindow != null) {
                                                }
                                                i9 = i3;
                                            } else {
                                                window.getClass();
                                                window.mOuter = new WeakReference(taskSnapshotWindow3);
                                                try {
                                                    Trace.traceBegin(32L, "TaskSnapshot#relayout");
                                                    windowSession.relayout(window, createLayoutParameters3, -1, -1, 0, 0, 0, 0, clientWindowFrames, mergedConfiguration, surfaceControl3, insetsState3, array, new Bundle());
                                                    Trace.traceEnd(32L);
                                                    Slog.d("ShellStartingWindow", "Relayout returned: frame=" + clientWindowFrames + " topInset=" + insetsState2 + ", title=" + ((Object) createLayoutParameters3.getTitle()));
                                                    SnapshotDrawerUtils.drawSnapshotOnSurface(startingWindowInfo, createLayoutParameters3, surfaceControl3, taskSnapshot2, rect2, clientWindowFrames.frame, insetsState2, true);
                                                    taskSnapshotWindow3.mHasDrawn = true;
                                                    try {
                                                        taskSnapshotWindow3.mSession.finishDrawing(taskSnapshotWindow3.mWindow, (SurfaceControl.Transaction) null, Integer.MAX_VALUE);
                                                    } catch (RemoteException unused2) {
                                                        ((HandlerExecutor) taskSnapshotWindow3.mSplashScreenExecutor).executeDelayed(0L, taskSnapshotWindow3.mClearWindowHandler);
                                                    }
                                                    taskSnapshotWindow = taskSnapshotWindow3;
                                                } catch (RemoteException unused3) {
                                                    taskSnapshotWindow2 = null;
                                                    ((HandlerExecutor) taskSnapshotWindow3.mSplashScreenExecutor).executeDelayed(0L, taskSnapshotWindow3.mClearWindowHandler);
                                                    Slog.w("ShellStartingWindow", "Failed to relayout snapshot starting window");
                                                }
                                                if (taskSnapshotWindow != null) {
                                                }
                                                i9 = i3;
                                            }
                                        }
                                    } else if (ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled) {
                                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW, 929975087, 0, null, null);
                                        startingWindowController = startingWindowController2;
                                        i3 = 0;
                                        i2 = suggestedWindowType;
                                        taskSnapshotWindow = taskSnapshotWindow2;
                                        if (taskSnapshotWindow != null) {
                                            startingWindowRecordManager.mStartingWindowRecords.put(i12, new SnapshotWindowCreator.SnapshotWindowRecord(taskSnapshotWindow, startingWindowInfo.taskInfo.topActivityType, snapshotWindowCreator.mMainExecutor, i12, snapshotWindowCreator.mStartingWindowRecordManager));
                                        }
                                        i9 = i3;
                                    } else {
                                        startingWindowController = startingWindowController2;
                                        i2 = suggestedWindowType;
                                    }
                                    taskSnapshotWindow2 = null;
                                    i3 = 0;
                                    taskSnapshotWindow = taskSnapshotWindow2;
                                    if (taskSnapshotWindow != null) {
                                    }
                                    i9 = i3;
                                }
                            } else {
                                final SplashscreenWindowCreator splashscreenWindowCreator = startingWindowController2.mStartingSurfaceDrawer.mSplashscreenWindowCreator;
                                splashscreenWindowCreator.getClass();
                                ActivityManager.RunningTaskInfo runningTaskInfo6 = startingWindowInfo.taskInfo;
                                ActivityInfo activityInfo3 = startingWindowInfo.targetActivityInfo;
                                if (activityInfo3 == null) {
                                    activityInfo3 = runningTaskInfo6.topActivityInfo;
                                }
                                if (activityInfo3 != null && activityInfo3.packageName != null) {
                                    int splashScreenTheme = AbsSplashWindowCreator.getSplashScreenTheme(startingWindowInfo.splashScreenThemeResId, activityInfo3);
                                    final SplashscreenContentDrawer splashscreenContentDrawer3 = splashscreenWindowCreator.mSplashscreenContentDrawer;
                                    SplashscreenContentDrawer.PreloadIconData preloadIconData = splashscreenContentDrawer3.mPreloadIcon;
                                    Context context3 = preloadIconData.mContext;
                                    if (context3 != null && runningTaskInfo6.displayId == 0) {
                                        int i15 = startingWindowInfo.splashScreenThemeResId;
                                        if ((preloadIconData.mIsPreloaded && i15 != 0 && i15 == context3.getThemeResId()) && runningTaskInfo6.getConfiguration().isNightModeActive() == preloadIconData.mContext.getResources().getConfiguration().isNightModeActive()) {
                                            z2 = true;
                                            createContext = !z2 ? splashscreenContentDrawer3.mPreloadIcon.mContext : SplashscreenContentDrawer.createContext(splashscreenWindowCreator.mContext, startingWindowInfo, splashScreenTheme, suggestedWindowType, splashscreenWindowCreator.mDisplayManager);
                                            if (createContext != null) {
                                                WindowManager.LayoutParams createLayoutParameters4 = SplashscreenContentDrawer.createLayoutParameters(createContext, startingWindowInfo, suggestedWindowType, activityInfo3.packageName, suggestedWindowType == i ? -1 : -3, startingWindowInfo.appToken);
                                                int i16 = runningTaskInfo6.displayId;
                                                final int i17 = runningTaskInfo6.taskId;
                                                Display display3 = splashscreenWindowCreator.mDisplayManager.getDisplay(i16);
                                                final SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier = new SplashscreenWindowCreator.SplashScreenViewSupplier(i9);
                                                final FrameLayout frameLayout2 = new FrameLayout(new ContextThemeWrapper(createContext, splashscreenContentDrawer3.mContext.getTheme()));
                                                frameLayout2.setPadding(0, 0, 0, 0);
                                                frameLayout2.setFitsSystemWindows(false);
                                                Runnable runnable2 = new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda1
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        SplashscreenWindowCreator splashscreenWindowCreator2 = SplashscreenWindowCreator.this;
                                                        SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier2 = splashScreenViewSupplier;
                                                        int i18 = i17;
                                                        StartingWindowInfo startingWindowInfo2 = startingWindowInfo;
                                                        FrameLayout frameLayout3 = frameLayout2;
                                                        splashscreenWindowCreator2.getClass();
                                                        Trace.traceBegin(32L, "addSplashScreenView");
                                                        SplashScreenView splashScreenView = splashScreenViewSupplier2.get();
                                                        StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord = (StartingSurfaceDrawer.StartingWindowRecord) splashscreenWindowCreator2.mStartingWindowRecordManager.mStartingWindowRecords.get(i18);
                                                        SplashscreenWindowCreator.SplashWindowRecord splashWindowRecord = startingWindowRecord instanceof SplashscreenWindowCreator.SplashWindowRecord ? (SplashscreenWindowCreator.SplashWindowRecord) startingWindowRecord : null;
                                                        if (splashWindowRecord != null && startingWindowInfo2.appToken == splashWindowRecord.mAppToken) {
                                                            if (splashScreenView != null) {
                                                                try {
                                                                    frameLayout3.addView(splashScreenView);
                                                                } catch (RuntimeException e) {
                                                                    Slog.w("ShellStartingWindow", "failed set content view to starting window at taskId: " + i18, e);
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
                                                CentralSurfacesImpl$$ExternalSyntheticLambda0 centralSurfacesImpl$$ExternalSyntheticLambda0 = splashscreenWindowCreator.mSysuiProxy;
                                                if (centralSurfacesImpl$$ExternalSyntheticLambda0 != null) {
                                                    CentralSurfacesImpl centralSurfacesImpl = (CentralSurfacesImpl) centralSurfacesImpl$$ExternalSyntheticLambda0.f$0;
                                                    centralSurfacesImpl.getClass();
                                                    i4 = 1;
                                                    ((ExecutorImpl) centralSurfacesImpl.mMainExecutor).execute(new CentralSurfacesImpl$$ExternalSyntheticLambda24(centralSurfacesImpl, 1 == true ? 1 : 0, "ShellStartingWindow", i9));
                                                } else {
                                                    i4 = 1;
                                                }
                                                final ?? r7 = new Consumer() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        switch (i9) {
                                                            case 0:
                                                                SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier2 = splashScreenViewSupplier;
                                                                SplashScreenView splashScreenView = (SplashScreenView) obj;
                                                                synchronized (splashScreenViewSupplier2) {
                                                                    splashScreenViewSupplier2.mView = splashScreenView;
                                                                    splashScreenViewSupplier2.mIsViewSet = true;
                                                                    splashScreenViewSupplier2.notify();
                                                                }
                                                                return;
                                                            default:
                                                                SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier3 = splashScreenViewSupplier;
                                                                Runnable runnable3 = (Runnable) obj;
                                                                synchronized (splashScreenViewSupplier3) {
                                                                    splashScreenViewSupplier3.mUiThreadInitTask = runnable3;
                                                                }
                                                                return;
                                                        }
                                                    }
                                                };
                                                final ?? r8 = new Consumer() { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator$$ExternalSyntheticLambda2
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        switch (i4) {
                                                            case 0:
                                                                SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier2 = splashScreenViewSupplier;
                                                                SplashScreenView splashScreenView = (SplashScreenView) obj;
                                                                synchronized (splashScreenViewSupplier2) {
                                                                    splashScreenViewSupplier2.mView = splashScreenView;
                                                                    splashScreenViewSupplier2.mIsViewSet = true;
                                                                    splashScreenViewSupplier2.notify();
                                                                }
                                                                return;
                                                            default:
                                                                SplashscreenWindowCreator.SplashScreenViewSupplier splashScreenViewSupplier3 = splashScreenViewSupplier;
                                                                Runnable runnable3 = (Runnable) obj;
                                                                synchronized (splashScreenViewSupplier3) {
                                                                    splashScreenViewSupplier3.mUiThreadInitTask = runnable3;
                                                                }
                                                                return;
                                                        }
                                                    }
                                                };
                                                runningTaskInfo = runningTaskInfo2;
                                                splashscreenContentDrawer3.mSplashscreenWorkerHandler.post(new Runnable() { // from class: com.android.wm.shell.startingsurface.SplashscreenContentDrawer$$ExternalSyntheticLambda2
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        SplashScreenView splashScreenView;
                                                        SplashscreenContentDrawer splashscreenContentDrawer4 = SplashscreenContentDrawer.this;
                                                        Context context4 = createContext;
                                                        StartingWindowInfo startingWindowInfo2 = startingWindowInfo;
                                                        int i18 = suggestedWindowType;
                                                        Consumer consumer = r8;
                                                        Consumer consumer2 = r7;
                                                        splashscreenContentDrawer4.getClass();
                                                        try {
                                                            Trace.traceBegin(32L, "makeSplashScreenContentView");
                                                            splashScreenView = splashscreenContentDrawer4.makeSplashScreenContentView(context4, startingWindowInfo2, i18, consumer);
                                                            Trace.traceEnd(32L);
                                                        } catch (RuntimeException e) {
                                                            Slog.w("ShellStartingWindow", "failed creating starting window content at taskId: " + startingWindowInfo2.taskInfo.taskId, e);
                                                            splashScreenView = null;
                                                        }
                                                        consumer2.accept(splashScreenView);
                                                    }
                                                });
                                                try {
                                                    i5 = i17;
                                                    try {
                                                        if (splashscreenWindowCreator.addWindow(i17, startingWindowInfo.appToken, frameLayout2, display3, createLayoutParameters4, suggestedWindowType)) {
                                                            splashscreenWindowCreator.mChoreographer.postCallback(2, runnable2, null);
                                                            SplashscreenWindowCreator.SplashWindowRecord splashWindowRecord = (SplashscreenWindowCreator.SplashWindowRecord) ((StartingSurfaceDrawer.StartingWindowRecord) splashscreenWindowCreator.mStartingWindowRecordManager.mStartingWindowRecords.get(i5));
                                                            if (splashWindowRecord != null) {
                                                                TypedArray obtainStyledAttributes = createContext.obtainStyledAttributes(R.styleable.Window);
                                                                splashWindowRecord.mDrawsSystemBarBackgrounds = obtainStyledAttributes.getBoolean(33, false);
                                                                if (obtainStyledAttributes.getBoolean(45, false)) {
                                                                    splashWindowRecord.mSystemBarAppearance |= 8;
                                                                }
                                                                if (obtainStyledAttributes.getBoolean(48, false)) {
                                                                    splashWindowRecord.mSystemBarAppearance |= 16;
                                                                }
                                                                obtainStyledAttributes.recycle();
                                                                SplashScreenView splashScreenView = splashScreenViewSupplier.get();
                                                                if (suggestedWindowType != 4) {
                                                                    splashScreenView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(splashscreenWindowCreator, splashScreenView) { // from class: com.android.wm.shell.startingsurface.SplashscreenWindowCreator.1
                                                                        public final /* synthetic */ SplashScreenView val$contentView;

                                                                        public ViewOnAttachStateChangeListenerC41311(final SplashscreenWindowCreator splashscreenWindowCreator2, SplashScreenView splashScreenView2) {
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
                                                    } catch (RuntimeException e) {
                                                        e = e;
                                                        Slog.w("ShellStartingWindow", "failed creating starting window at taskId: " + i5, e);
                                                        startingWindowController = startingWindowController2;
                                                        i2 = suggestedWindowType;
                                                        i9 = 0;
                                                        if (i2 != 0) {
                                                            int i18 = runningTaskInfo.taskId;
                                                            StartingWindowController startingWindowController3 = startingWindowController;
                                                            StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord = (StartingSurfaceDrawer.StartingWindowRecord) startingWindowController3.mStartingSurfaceDrawer.mWindowRecords.mStartingWindowRecords.get(i18);
                                                            if (startingWindowRecord != null) {
                                                            }
                                                            if (i7 != 0) {
                                                            }
                                                            triConsumer = startingWindowController3.mTaskLaunchingCallback;
                                                            if (triConsumer != null) {
                                                            }
                                                        }
                                                        Trace.traceEnd(32L);
                                                        return;
                                                    }
                                                } catch (RuntimeException e2) {
                                                    e = e2;
                                                    i5 = i17;
                                                }
                                            }
                                        }
                                    }
                                    z2 = false;
                                    createContext = !z2 ? splashscreenContentDrawer3.mPreloadIcon.mContext : SplashscreenContentDrawer.createContext(splashscreenWindowCreator2.mContext, startingWindowInfo, splashScreenTheme, suggestedWindowType, splashscreenWindowCreator2.mDisplayManager);
                                    if (createContext != null) {
                                    }
                                }
                                runningTaskInfo = runningTaskInfo2;
                                startingWindowController = startingWindowController2;
                                i2 = suggestedWindowType;
                            }
                            startingWindowController = startingWindowController2;
                            i2 = suggestedWindowType;
                            i9 = 0;
                        }
                    }
                    z = true;
                    if (z) {
                    }
                    startingWindowController = startingWindowController2;
                    i2 = suggestedWindowType;
                    i9 = 0;
                }
                if (i2 != 0 && (i6 = i2) != 5) {
                    int i182 = runningTaskInfo.taskId;
                    StartingWindowController startingWindowController32 = startingWindowController;
                    StartingSurfaceDrawer.StartingWindowRecord startingWindowRecord2 = (StartingSurfaceDrawer.StartingWindowRecord) startingWindowController32.mStartingSurfaceDrawer.mWindowRecords.mStartingWindowRecords.get(i182);
                    i7 = startingWindowRecord2 != null ? i9 : startingWindowRecord2.mBGColor;
                    if (i7 != 0) {
                        synchronized (startingWindowController32.mTaskBackgroundColors) {
                            startingWindowController32.mTaskBackgroundColors.append(i182, i7);
                        }
                    }
                    triConsumer = startingWindowController32.mTaskLaunchingCallback;
                    if (triConsumer != null) {
                        if (i6 == 1 || i6 == 3 || i6 == 4) {
                            i9 = 1;
                        }
                        if (i9 != 0) {
                            triConsumer.accept(Integer.valueOf(i182), Integer.valueOf(i6), Integer.valueOf(i7));
                        }
                    }
                }
                Trace.traceEnd(32L);
                return;
            default:
                StartingWindowController.StartingSurfaceImpl startingSurfaceImpl = (StartingWindowController.StartingSurfaceImpl) this.f$0;
                CentralSurfacesImpl$$ExternalSyntheticLambda0 centralSurfacesImpl$$ExternalSyntheticLambda02 = (CentralSurfacesImpl$$ExternalSyntheticLambda0) this.f$1;
                StartingSurfaceDrawer startingSurfaceDrawer2 = StartingWindowController.this.mStartingSurfaceDrawer;
                startingSurfaceDrawer2.mSplashscreenWindowCreator.mSysuiProxy = centralSurfacesImpl$$ExternalSyntheticLambda02;
                startingSurfaceDrawer2.mWindowlessSplashWindowCreator.mSysuiProxy = centralSurfacesImpl$$ExternalSyntheticLambda02;
                return;
        }
    }
}
