package com.android.wm.shell.pip2.phone;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.ViewConfiguration;
import android.window.DesktopExperienceFlags;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.Preconditions;
import com.android.systemui.R;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ImeListener;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.common.pip.IPip;
import com.android.wm.shell.common.pip.IPipAnimationListener$Stub$Proxy;
import com.android.wm.shell.common.pip.PipAppOpsListener;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip2.phone.PipController;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipController implements ConfigurationChangeListener, PipTransitionState.PipTransitionStateChangedListener, DisplayController.OnDisplaysChangedListener, DisplayChangeController.OnDisplayChangingListener, RemoteCallable {
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final PipImpl mImpl;
    public final ShellExecutor mMainExecutor;
    public final List mOnIsInPipStateChangedListeners = new ArrayList();
    public final PipAppOpsListener mPipAppOpsListener;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public final PhonePipMenuController mPipMenuController;
    public IPipImpl.AnonymousClass1 mPipRecentsAnimationListener;
    public final PipScheduler mPipScheduler;
    public final PipTouchHandler mPipTouchHandler;
    public final PipTransitionState mPipTransitionState;
    public final PipUiEventLogger mPipUiEventLogger;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final TaskStackListenerImpl mTaskStackListener;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    interface PipAnimationListener {
    }

    private PipController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, DisplayController displayController, DisplayInsetsController displayInsetsController, PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm, PipDisplayLayoutState pipDisplayLayoutState, PipScheduler pipScheduler, TaskStackListenerImpl taskStackListenerImpl, ShellTaskOrganizer shellTaskOrganizer, PipTransitionState pipTransitionState, PipTouchHandler pipTouchHandler, PipAppOpsListener pipAppOpsListener, PhonePipMenuController phonePipMenuController, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor) {
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        this.mDisplayController = displayController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mPipBoundsState = pipBoundsState;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mPipScheduler = pipScheduler;
        this.mTaskStackListener = taskStackListenerImpl;
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mPipTransitionState = pipTransitionState;
        pipTransitionState.addPipTransitionStateChangedListener(this);
        this.mPipTouchHandler = pipTouchHandler;
        this.mPipAppOpsListener = pipAppOpsListener;
        this.mPipMenuController = phonePipMenuController;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mMainExecutor = shellExecutor;
        this.mImpl = new PipImpl();
        if (PipUtils.isPip2ExperimentEnabled()) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    final PipController pipController = PipController.this;
                    pipController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.pip2.phone.PipController$$ExternalSyntheticLambda1
                        @Override // java.util.function.BiConsumer
                        public final void accept(Object obj, Object obj2) {
                            PipController pipController2 = PipController.this;
                            PrintWriter printWriter = (PrintWriter) obj;
                            printWriter.println("PipController");
                            pipController2.mPipBoundsAlgorithm.dump(printWriter, "  ");
                            pipController2.mPipBoundsState.dump(printWriter);
                            pipController2.mPipDisplayLayoutState.dump(printWriter);
                            PipTransitionState pipTransitionState2 = pipController2.mPipTransitionState;
                            pipTransitionState2.getClass();
                            printWriter.println("  PipTransitionState");
                            printWriter.println("    mState=".concat(PipTransitionState.stateToString(pipTransitionState2.mState)));
                        }
                    }, pipController);
                    int displayId = pipController.mContext.getDisplayId();
                    PipDisplayLayoutState pipDisplayLayoutState2 = pipController.mPipDisplayLayoutState;
                    pipDisplayLayoutState2.mDisplayId = displayId;
                    Context context2 = pipController.mContext;
                    pipDisplayLayoutState2.mDisplayLayout.set(new DisplayLayout(context2, context2.getDisplay()));
                    DisplayController displayController2 = pipController.mDisplayController;
                    displayController2.addDisplayChangingController(pipController);
                    displayController2.addDisplayWindowListener(pipController, -1);
                    int i = pipDisplayLayoutState2.mDisplayId;
                    pipController.mDisplayInsetsController.addInsetsChangedListener(i, new ImeListener(displayController2, i) { // from class: com.android.wm.shell.pip2.phone.PipController.1
                        @Override // com.android.wm.shell.common.ImeListener
                        public final void onImeVisibilityChanged(final boolean z, int i2) {
                            final PipTouchHandler pipTouchHandler2 = PipController.this.mPipTouchHandler;
                            pipTouchHandler2.mIsImeShowing = z;
                            pipTouchHandler2.mImeHeight = i2;
                            pipTouchHandler2.updateMovementBounds();
                            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipTouchHandler$$ExternalSyntheticLambda13
                                @Override // java.lang.Runnable
                                public final void run() {
                                    PipTouchHandler pipTouchHandler3 = PipTouchHandler.this;
                                    boolean z2 = z;
                                    PipBoundsState pipBoundsState2 = pipTouchHandler3.mPipBoundsState;
                                    int i3 = pipBoundsState2.mMovementBounds.bottom - pipBoundsState2.getBounds().top;
                                    boolean z3 = pipBoundsState2.mHasUserMovedPip || pipBoundsState2.mHasUserResizedPip;
                                    if (!z2 && !z3) {
                                        i3 = pipTouchHandler3.mPipBoundsAlgorithm.getEntryDestinationBounds().top - pipBoundsState2.getBounds().top;
                                    }
                                    if ((!z2 || i3 >= 0) && (z2 || z3)) {
                                        return;
                                    }
                                    pipTouchHandler3.mMotionHelper.animateToOffset(i3, pipBoundsState2.getBounds());
                                }
                            };
                            PipTransitionState pipTransitionState2 = pipTouchHandler2.mPipTransitionState;
                            pipTransitionState2.mOnIdlePipTransitionStateRunnable = runnable;
                            pipTransitionState2.maybeRunOnIdlePipTransitionStateCallback();
                        }
                    });
                    Supplier supplier = new Supplier() { // from class: com.android.wm.shell.pip2.phone.PipController$$ExternalSyntheticLambda2
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            PipController pipController2 = PipController.this;
                            pipController2.getClass();
                            return new PipController.IPipImpl(pipController2);
                        }
                    };
                    ShellController shellController2 = pipController.mShellController;
                    shellController2.addExternalInterface("com.android.wm.shell.common.pip.IPip", supplier, pipController);
                    shellController2.addConfigurationChangeListener(pipController);
                    pipController.mTaskStackListener.addListener(new TaskStackListenerCallback() { // from class: com.android.wm.shell.pip2.phone.PipController.2
                        @Override // com.android.wm.shell.common.TaskStackListenerCallback
                        public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1019781757933337558L, 12, String.valueOf(runningTaskInfo.topActivity), Boolean.valueOf(z2));
                            }
                            if (runningTaskInfo.getWindowingMode() == 2 && z2) {
                                PipScheduler pipScheduler2 = PipController.this.mPipScheduler;
                                pipScheduler2.getClass();
                                pipScheduler2.mMainExecutor.execute(new PipScheduler$$ExternalSyntheticLambda4(pipScheduler2));
                            }
                        }
                    });
                    pipController.mPipAppOpsListener.mCallback = pipController.mPipTouchHandler.mMotionHelper;
                }
            }, this);
        }
    }

    public static PipController create(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, DisplayController displayController, DisplayInsetsController displayInsetsController, PipBoundsState pipBoundsState, PipBoundsAlgorithm pipBoundsAlgorithm, PipDisplayLayoutState pipDisplayLayoutState, PipScheduler pipScheduler, TaskStackListenerImpl taskStackListenerImpl, ShellTaskOrganizer shellTaskOrganizer, PipTransitionState pipTransitionState, PipTouchHandler pipTouchHandler, PipAppOpsListener pipAppOpsListener, PhonePipMenuController phonePipMenuController, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor) {
        if (context.getPackageManager().hasSystemFeature("android.software.picture_in_picture")) {
            return new PipController(context, shellInit, shellCommandHandler, shellController, displayController, displayInsetsController, pipBoundsState, pipBoundsAlgorithm, pipDisplayLayoutState, pipScheduler, taskStackListenerImpl, shellTaskOrganizer, pipTransitionState, pipTouchHandler, pipAppOpsListener, phonePipMenuController, pipUiEventLogger, shellExecutor);
        }
        if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
            return null;
        }
        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1604738762234449471L, 0, "PipController");
        return null;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        this.mPipDisplayLayoutState.reloadResources();
        PipTouchHandler pipTouchHandler = this.mPipTouchHandler;
        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
        PipDragToResizeHandler pipDragToResizeHandler = pipResizeGestureHandler.mPipDragToResizeHandler;
        pipDragToResizeHandler.mDelta = pipDragToResizeHandler.mContext.getResources().getDimensionPixelSize(R.dimen.pip_resize_edge_size);
        pipResizeGestureHandler.mTouchSlop = ViewConfiguration.get(pipResizeGestureHandler.mContext).getScaledTouchSlop();
        PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
        pipMotionHelper.cancelPhysicsAnimation();
        pipMotionHelper.mPipBoundsState.mMotionBoundsState.mBoundsInMotion.setEmpty();
        Resources resources = pipTouchHandler.mContext.getResources();
        resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
        resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
        PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler.mPipDismissTargetHandler;
        pipDismissTargetHandler.updateMagneticTargetSize();
        if (pipTouchHandler.mPipTransitionState.isInPip()) {
            pipDismissTargetHandler.createOrUpdateDismissTarget();
        }
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onDensityOrFontScaleChanged$1() {
        IPipImpl.AnonymousClass1 anonymousClass1 = this.mPipRecentsAnimationListener;
        if (anonymousClass1 != null) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
            IInterface iInterface = IPipImpl.this.mListener.mListener;
            if (iInterface == null) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                return;
            }
            try {
                ((IPipAnimationListener$Stub$Proxy) iInterface).onPipResourceDimensionsChanged(dimensionPixelSize, dimensionPixelSize2);
            } catch (RemoteException e) {
                Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
            }
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
        if (i != pipDisplayLayoutState.mDisplayId) {
            return;
        }
        pipDisplayLayoutState.mDisplayLayout.set(this.mDisplayController.getDisplayLayout(i));
    }

    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    public final void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
        if (i != pipDisplayLayoutState.mDisplayId) {
            return;
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect bounds = pipBoundsState.getBounds();
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        float snapFraction = pipBoundsAlgorithm.getSnapFraction(bounds);
        float f = pipBoundsState.mBoundsScale;
        pipDisplayLayoutState.mDisplayLayout.set(this.mDisplayController.getDisplayLayout(i));
        if (i3 != -1) {
            pipDisplayLayoutState.rotateTo(i3);
        }
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (!pipTransitionState.isInPip() && pipTransitionState.mState != 2) {
            if (pipTransitionState.mInFixedRotation && ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1474744958753324630L, 0, null);
                return;
            }
            return;
        }
        this.mPipMenuController.hideMenu();
        boolean z = pipTransitionState.mInFixedRotation;
        PipTouchHandler pipTouchHandler = this.mPipTouchHandler;
        if (z) {
            pipTouchHandler.updateMovementBounds();
            pipTransitionState.mInFixedRotation = false;
            pipTransitionState.maybeRunOnIdlePipTransitionStateCallback();
        } else {
            Rect rect = new Rect(0, 0, (int) Math.ceil(pipBoundsState.mMaxSize.x * f), (int) Math.ceil(pipBoundsState.mMaxSize.y * f));
            pipBoundsState.setBounds(rect);
            pipTouchHandler.updateMovementBounds();
            Rect movementBounds = pipBoundsAlgorithm.getMovementBounds(rect, true);
            pipBoundsAlgorithm.mSnapAlgorithm.getClass();
            PipSnapAlgorithm.applySnapFraction(rect, movementBounds, snapFraction);
            pipBoundsState.setBounds(rect);
        }
        if (pipTransitionState.getPipTaskToken() == null) {
            Log.wtf("PipController", "PipController.onDisplayChange no PiP task token state=" + pipTransitionState.mState + " callers=\n" + Debug.getCallers(4, "    "));
        } else {
            windowContainerTransaction.setBounds(pipTransitionState.getPipTaskToken(), pipBoundsState.getBounds());
        }
        pipBoundsState.updateMinMaxSize(pipBoundsState.mAspectRatio);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        if (DesktopExperienceFlags.ENABLE_CONNECTED_DISPLAYS_PIP.isTrue()) {
            PipTransitionState pipTransitionState = this.mPipTransitionState;
            if (pipTransitionState.isInPip()) {
                PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
                if (i != pipDisplayLayoutState.mDisplayId || i == 0) {
                    return;
                }
                pipTransitionState.setState(7, null);
                pipTransitionState.setState(8, null);
                pipDisplayLayoutState.mDisplayId = 0;
                pipDisplayLayoutState.mDisplayLayout.set(this.mDisplayController.getDisplayLayout(0));
            }
        }
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        ComponentName componentName;
        int i3 = 0;
        int i4 = 0;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (i2 == 1) {
            Preconditions.checkState(bundle != null, "No extra bundle for " + pipTransitionState);
            SurfaceControl surfaceControl = (SurfaceControl) bundle.getParcelable("swipe_to_pip_overlay", SurfaceControl.class);
            Rect rect = (Rect) bundle.getParcelable("pip_app_bounds", Rect.class);
            Preconditions.checkState(rect != null, "App bounds can't be null for " + pipTransitionState);
            pipTransitionState.mInSwipePipToHomeTransition = true;
            if (surfaceControl == null || rect.isEmpty()) {
                return;
            }
            pipTransitionState.mSwipePipToHomeOverlay = surfaceControl;
            pipTransitionState.mSwipePipToHomeAppBounds.set(rect);
            return;
        }
        PipAppOpsListener pipAppOpsListener = this.mPipAppOpsListener;
        PipUiEventLogger pipUiEventLogger = this.mPipUiEventLogger;
        if (i2 != 3) {
            if (i2 != 8) {
                return;
            }
            pipAppOpsListener.mAppOpsManager.stopWatchingMode(pipAppOpsListener.mAppOpsChangedListener);
            pipUiEventLogger.setTaskInfo(null);
            ArrayList arrayList = (ArrayList) this.mOnIsInPipStateChangedListeners;
            int size = arrayList.size();
            while (i3 < size) {
                Object obj = arrayList.get(i3);
                i3++;
                ((Consumer) obj).accept(Boolean.FALSE);
            }
            return;
        }
        TaskInfo taskInfo = pipTransitionState.mPipTaskInfo;
        if (taskInfo != null && (componentName = taskInfo.topActivity) != null) {
            pipAppOpsListener.mAppOpsManager.startWatchingMode(67, componentName.getPackageName(), pipAppOpsListener.mAppOpsChangedListener);
            pipUiEventLogger.setTaskInfo(taskInfo);
        }
        if (pipTransitionState.mInSwipePipToHomeTransition) {
            pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_AUTO_ENTER);
            pipTransitionState.mInSwipePipToHomeTransition = false;
            pipTransitionState.mSwipePipToHomeOverlay = null;
            pipTransitionState.mSwipePipToHomeAppBounds.setEmpty();
        } else {
            pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_ENTER);
        }
        ArrayList arrayList2 = (ArrayList) this.mOnIsInPipStateChangedListeners;
        int size2 = arrayList2.size();
        while (i4 < size2) {
            Object obj2 = arrayList2.get(i4);
            i4++;
            ((Consumer) obj2).accept(Boolean.TRUE);
        }
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onThemeChanged() {
        Context context = this.mContext;
        this.mPipDisplayLayoutState.mDisplayLayout.set(new DisplayLayout(context, context.getDisplay()));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class IPipImpl extends IPip.Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public PipController mController;
        public final SingleInstanceRemoteListener mListener;
        public final AnonymousClass1 mPipAnimationListener = new AnonymousClass1();

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        /* renamed from: com.android.wm.shell.pip2.phone.PipController$IPipImpl$1, reason: invalid class name */
        public class AnonymousClass1 implements PipAnimationListener {
            public AnonymousClass1() {
            }
        }

        public IPipImpl(PipController pipController) {
            this.mController = pipController;
            this.mListener = new SingleInstanceRemoteListener(pipController, new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipController$IPipImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PipController pipController2 = (PipController) obj;
                    PipController.IPipImpl.AnonymousClass1 anonymousClass1 = PipController.IPipImpl.this.mPipAnimationListener;
                    pipController2.mPipRecentsAnimationListener = anonymousClass1;
                    if (anonymousClass1 != null) {
                        int dimensionPixelSize = pipController2.mContext.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
                        int dimensionPixelSize2 = pipController2.mContext.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
                        IInterface iInterface = PipController.IPipImpl.this.mListener.mListener;
                        if (iInterface == null) {
                            Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                            return;
                        }
                        try {
                            ((IPipAnimationListener$Stub$Proxy) iInterface).onPipResourceDimensionsChanged(dimensionPixelSize, dimensionPixelSize2);
                        } catch (RemoteException e) {
                            Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                        }
                    }
                }
            }, new PipController$IPipImpl$$ExternalSyntheticLambda6());
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
            this.mListener.unregister();
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setLauncherAppIconSize(final int i) {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "setLauncherAppIconSize", new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipController$IPipImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2 = i;
                    int i3 = PipController.IPipImpl.$r8$clinit;
                    ((PipController) obj).mPipBoundsState.mLauncherState.mAppIconSizePx = i2;
                }
            }, false);
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setLauncherKeepClearAreaHeight(final int i, final boolean z) {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "setLauncherKeepClearAreaHeight", new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipController$IPipImpl$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    final boolean z2 = z;
                    final int i2 = i;
                    final PipController pipController = (PipController) obj;
                    int i3 = PipController.IPipImpl.$r8$clinit;
                    pipController.getClass();
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -3459105394737332524L, 7, Boolean.valueOf(z2), Long.valueOf(i2));
                    }
                    Runnable runnable = new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipController$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            PipController pipController2 = PipController.this;
                            boolean z3 = z2;
                            int i4 = i2;
                            PipBoundsState pipBoundsState = pipController2.mPipBoundsState;
                            if (z3) {
                                PipDisplayLayoutState pipDisplayLayoutState = pipController2.mPipDisplayLayoutState;
                                pipBoundsState.setNamedUnrestrictedKeepClearArea(0, new Rect(0, pipDisplayLayoutState.getDisplayBounds().bottom - i4, pipDisplayLayoutState.getDisplayBounds().right, pipDisplayLayoutState.getDisplayBounds().bottom));
                            } else {
                                pipBoundsState.setNamedUnrestrictedKeepClearArea(0, null);
                            }
                            PipTouchHandler pipTouchHandler = pipController2.mPipTouchHandler;
                            pipTouchHandler.mIsShelfShowing = z3;
                            pipTouchHandler.mShelfHeight = i4;
                            HandlerExecutor handlerExecutor = (HandlerExecutor) pipTouchHandler.mMainExecutor;
                            PipTouchHandler$$ExternalSyntheticLambda1 pipTouchHandler$$ExternalSyntheticLambda1 = pipTouchHandler.mMoveOnShelVisibilityChanged;
                            handlerExecutor.removeCallbacks(pipTouchHandler$$ExternalSyntheticLambda1);
                            handlerExecutor.executeDelayed(pipTouchHandler$$ExternalSyntheticLambda1, PipTouchHandler.PIP_KEEP_CLEAR_AREAS_DELAY);
                        }
                    };
                    PipTransitionState pipTransitionState = pipController.mPipTransitionState;
                    pipTransitionState.mOnIdlePipTransitionStateRunnable = runnable;
                    pipTransitionState.maybeRunOnIdlePipTransitionStateCallback();
                }
            }, false);
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setPipAnimationListener(final IPipAnimationListener$Stub$Proxy iPipAnimationListener$Stub$Proxy) {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "setPipAnimationListener", new Consumer() { // from class: com.android.wm.shell.pip2.phone.PipController$IPipImpl$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PipController.IPipImpl iPipImpl = PipController.IPipImpl.this;
                    IPipAnimationListener$Stub$Proxy iPipAnimationListener$Stub$Proxy2 = iPipAnimationListener$Stub$Proxy;
                    if (iPipAnimationListener$Stub$Proxy2 != null) {
                        iPipImpl.mListener.register(iPipAnimationListener$Stub$Proxy2);
                    } else {
                        iPipImpl.mListener.unregister();
                    }
                }
            }, false);
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final Rect startSwipePipToHome(ActivityManager.RunningTaskInfo runningTaskInfo, int i, Rect rect) {
            Rect[] rectArr = new Rect[1];
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "startSwipePipToHome", new PipController$IPipImpl$$ExternalSyntheticLambda0(rectArr, runningTaskInfo, i, rect), true);
            return rectArr[0];
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void stopSwipePipToHome(int i, ComponentName componentName, Rect rect, SurfaceControl surfaceControl, Rect rect2, Rect rect3) {
            if (surfaceControl != null) {
                surfaceControl.setUnreleasedWarningCallSite("PipController.stopSwipePipToHome");
            }
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "stopSwipePipToHome", new PipController$IPipImpl$$ExternalSyntheticLambda0(i, componentName, rect, surfaceControl, rect2, rect3), false);
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setPipAnimationTypeToAlpha() {
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void abortSwipePipToHome(int i, ComponentName componentName) {
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setShelfHeight(int i, boolean z) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PipImpl implements Pip {
        public PipImpl() {
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void addOnIsInPipStateChangedListener(Consumer consumer) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, consumer, 2));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void addPipExclusionBoundsChangeListener(Consumer consumer) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, consumer, 0));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void removeOnIsInPipStateChangedListener(EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, edgeBackGestureHandler$$ExternalSyntheticLambda0));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void removePipExclusionBoundsChangeListener(Consumer consumer) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, consumer, 1));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void showPictureInPictureMenu() {
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void onSystemUiStateChanged(long j, boolean z) {
        }
    }
}
