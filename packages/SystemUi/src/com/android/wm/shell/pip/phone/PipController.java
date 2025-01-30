package com.android.wm.shell.pip.phone;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import android.util.Size;
import android.util.Slog;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.WindowManagerShellWrapper;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ExternalInterfaceBinder;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.common.TabletopModeController;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.pip.IPip$Stub;
import com.android.wm.shell.pip.IPipAnimationListener;
import com.android.wm.shell.pip.IPipAnimationListener$Stub$Proxy;
import com.android.wm.shell.pip.PinnedStackListenerForwarder;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipAppOpsListener;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipKeepClearAlgorithmInterface;
import com.android.wm.shell.pip.PipMediaController;
import com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda0;
import com.android.wm.shell.pip.PipMenuControlService;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import com.android.wm.shell.transition.Transitions;
import com.android.server.LocalServices;
import com.android.systemui.R;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.samsung.android.rune.CoreRune;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipController implements PipTransitionController.PipTransitionCallback, RemoteCallable, ConfigurationChangeListener, KeyguardChangeListener, UserChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long PIP_KEEP_CLEAR_AREAS_DELAY = SystemProperties.getLong("persist.wm.debug.pip_keep_clear_areas_delay", 200);
    public final PipAppOpsListener mAppOpsListener;
    public final ServiceConnectionC40586 mConnection;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final int mEnterAnimationDuration;
    public final PipImpl mImpl;
    public boolean mIsInFixedRotation;
    public boolean mIsKeyguardShowingOrAnimating;
    public final ShellExecutor mMainExecutor;
    public final PipMediaController mMediaController;
    public final PhonePipMenuController mMenuController;
    public final PipController$$ExternalSyntheticLambda1 mMovePipInResponseToKeepClearAreasChangeCallback;
    public Consumer mOnIsInPipStateChangedListener;
    public final Optional mOneHandedController;
    public PipAnimationListener mPinnedStackAnimationRecentsCallback;
    public final PipControllerPinnedTaskListener mPinnedTaskListener;
    public final PipAnimationController mPipAnimationController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public PipInputConsumer mPipInputConsumer;
    public final PipKeepClearAlgorithmInterface mPipKeepClearAlgorithm;
    public final PipMotionHelper mPipMotionHelper;
    public final PipParamsChangedForwarder mPipParamsChangedForwarder;
    public final PipSizeSpecHandler mPipSizeSpecHandler;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipTransitionController mPipTransitionController;
    public final PipTransitionState mPipTransitionState;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final TabletopModeController mTabletopModeController;
    public final TaskStackListenerImpl mTaskStackListener;
    public final PipTouchHandler mTouchHandler;
    public final WindowManagerShellWrapper mWindowManagerShellWrapper;
    public boolean mEnablePipKeepClearAlgorithm = SystemProperties.getBoolean("persist.wm.debug.enable_pip_keep_clear_algorithm", true);
    public final Rect mTmpInsetBounds = new Rect();
    public final PipController$$ExternalSyntheticLambda2 mRotationController = new PipController$$ExternalSyntheticLambda2(this);
    final DisplayController.OnDisplaysChangedListener mDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.pip.phone.PipController.1
        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayAdded(int i) {
            PipController pipController = PipController.this;
            if (i != pipController.mPipDisplayLayoutState.mDisplayId) {
                return;
            }
            pipController.onDisplayChanged(pipController.mDisplayController.getDisplayLayout(i), false);
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
            PipController pipController = PipController.this;
            if (i == pipController.mPipDisplayLayoutState.mDisplayId && !configuration.isDexMode()) {
                pipController.onDisplayChanged(pipController.mDisplayController.getDisplayLayout(i), true);
            }
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onFixedRotationFinished(int i) {
            PipController.this.mIsInFixedRotation = false;
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onFixedRotationStarted(int i, int i2) {
            PipController.this.mIsInFixedRotation = true;
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onKeepClearAreasChanged(int i, Set set, Set set2) {
            PipController pipController = PipController.this;
            if (pipController.mPipDisplayLayoutState.mDisplayId == i && pipController.mEnablePipKeepClearAlgorithm) {
                PipBoundsState pipBoundsState = pipController.mPipBoundsState;
                ArraySet arraySet = (ArraySet) pipBoundsState.mRestrictedKeepClearAreas;
                arraySet.clear();
                arraySet.addAll(set);
                ArraySet arraySet2 = (ArraySet) pipBoundsState.mUnrestrictedKeepClearAreas;
                arraySet2.clear();
                arraySet2.addAll(set2);
                ShellExecutor shellExecutor = pipController.mMainExecutor;
                PipController$$ExternalSyntheticLambda1 pipController$$ExternalSyntheticLambda1 = pipController.mMovePipInResponseToKeepClearAreasChangeCallback;
                ((HandlerExecutor) shellExecutor).removeCallbacks(pipController$$ExternalSyntheticLambda1);
                ((HandlerExecutor) shellExecutor).executeDelayed(PipController.PIP_KEEP_CLEAR_AREAS_DELAY, pipController$$ExternalSyntheticLambda1);
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class IPipImpl extends IPip$Stub implements ExternalInterfaceBinder {
        public PipController mController;
        public final SingleInstanceRemoteListener mListener;
        public final C40591 mPipAnimationListener = new PipAnimationListener() { // from class: com.android.wm.shell.pip.phone.PipController.IPipImpl.1
            @Override // com.android.wm.shell.pip.phone.PipController.PipAnimationListener
            public final void onExpandPip() {
                IInterface iInterface = IPipImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IPipAnimationListener$Stub$Proxy) ((IPipAnimationListener) iInterface)).onExpandPip();
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            @Override // com.android.wm.shell.pip.phone.PipController.PipAnimationListener
            public final void onPipAnimationStarted() {
                IInterface iInterface = IPipImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IPipAnimationListener$Stub$Proxy) ((IPipAnimationListener) iInterface)).onPipAnimationStarted();
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }

            @Override // com.android.wm.shell.pip.phone.PipController.PipAnimationListener
            public final void onPipResourceDimensionsChanged(int i, int i2) {
                IInterface iInterface = IPipImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    ((IPipAnimationListener$Stub$Proxy) ((IPipAnimationListener) iInterface)).onPipResourceDimensionsChanged(i, i2);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }
        };

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.pip.phone.PipController$IPipImpl$1] */
        public IPipImpl(PipController pipController) {
            this.mController = pipController;
            int i = 1;
            this.mListener = new SingleInstanceRemoteListener(pipController, new PipController$$ExternalSyntheticLambda4(this, i), new PipController$IPipImpl$$ExternalSyntheticLambda6(i));
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
            this.mListener.unregister();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    interface PipAnimationListener {
        void onExpandPip();

        void onPipAnimationStarted();

        void onPipResourceDimensionsChanged(int i, int i2);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipControllerPinnedTaskListener extends PinnedStackListenerForwarder.PinnedTaskListener {
        public /* synthetic */ PipControllerPinnedTaskListener(PipController pipController, int i) {
            this();
        }

        @Override // com.android.wm.shell.pip.PinnedStackListenerForwarder.PinnedTaskListener
        public final void onActivityHidden(ComponentName componentName) {
            PipController pipController = PipController.this;
            if (componentName.equals(pipController.mPipBoundsState.mLastPipComponentName)) {
                pipController.mPipBoundsState.setLastPipComponentName(null);
            }
        }

        @Override // com.android.wm.shell.pip.PinnedStackListenerForwarder.PinnedTaskListener
        public final void onImeVisibilityChanged(boolean z, int i) {
            PipController pipController = PipController.this;
            PipBoundsState pipBoundsState = pipController.mPipBoundsState;
            pipBoundsState.mIsImeShowing = z;
            pipBoundsState.mImeHeight = i;
            PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
            pipTouchHandler.mIsImeShowing = z;
            pipTouchHandler.mImeHeight = i;
            if (z) {
                pipController.updatePipPositionForKeepClearAreas();
            }
        }

        @Override // com.android.wm.shell.pip.PinnedStackListenerForwarder.PinnedTaskListener
        public final void onMovementBoundsChanged(boolean z) {
            PipController pipController = PipController.this;
            int i = PipController.$r8$clinit;
            pipController.updateMovementBounds(null, false, z, false, null);
        }

        private PipControllerPinnedTaskListener() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipImpl implements Pip {
        public /* synthetic */ PipImpl(PipController pipController, int i) {
            this();
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void addPipExclusionBoundsChangeListener(Consumer consumer) {
            ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda0(1, this, consumer));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final boolean isExitingPipTask(int i) {
            PipController pipController = PipController.this;
            ActivityManager.RunningTaskInfo runningTaskInfo = pipController.mPipTaskOrganizer.mTaskInfo;
            return (runningTaskInfo == null || runningTaskInfo.taskId != i || pipController.mPipTransitionState.isInPip()) ? false : true;
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void onSystemUiStateChanged(final long j, final boolean z) {
            ((HandlerExecutor) PipController.this.mMainExecutor).execute(new Runnable(z, j) { // from class: com.android.wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda0
                public final /* synthetic */ boolean f$1;

                @Override // java.lang.Runnable
                public final void run() {
                    PipController.this.mTouchHandler.mPipResizeGestureHandler.mIsSysUiStateValid = this.f$1;
                }
            });
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void removePipExclusionBoundsChangeListener(Consumer consumer) {
            ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda0(3, this, consumer));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void setOnIsInPipStateChangedListener(EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0) {
            ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda0(2, this, edgeBackGestureHandler$$ExternalSyntheticLambda0));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void showPictureInPictureMenu() {
            ((HandlerExecutor) PipController.this.mMainExecutor).execute(new PipController$$ExternalSyntheticLambda1(this, 5));
        }

        private PipImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsObserver extends ContentObserver {
        public final Uri mEdgeHandleSizePercentUri;
        public final Uri mEdgeHandlerPositionPercentUri;

        public SettingsObserver(Handler handler) {
            super(handler);
            Uri uriFor = Settings.Global.getUriFor("edge_handle_size_percent");
            this.mEdgeHandleSizePercentUri = uriFor;
            Uri uriFor2 = Settings.System.getUriFor("edge_handler_position_percent");
            this.mEdgeHandlerPositionPercentUri = uriFor2;
            ContentResolver contentResolver = PipController.this.mContext.getContentResolver();
            contentResolver.registerContentObserver(uriFor, false, this, -1);
            contentResolver.registerContentObserver(uriFor2, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            if ((uri.equals(this.mEdgeHandleSizePercentUri) || uri.equals(this.mEdgeHandlerPositionPercentUri)) && PipController.this.mPipBoundsState.isStashed()) {
                PipController pipController = PipController.this;
                PipMotionHelper pipMotionHelper = pipController.mTouchHandler.mMotionHelper;
                Rect bounds = pipController.mPipBoundsState.getBounds();
                pipMotionHelper.adjustPipBoundsForEdge(bounds);
                pipMotionHelper.movePip(bounds, false);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [android.content.ServiceConnection, com.android.wm.shell.pip.phone.PipController$6] */
    public PipController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, DisplayController displayController, PipAnimationController pipAnimationController, PipAppOpsListener pipAppOpsListener, PipBoundsAlgorithm pipBoundsAlgorithm, PipKeepClearAlgorithmInterface pipKeepClearAlgorithmInterface, PipBoundsState pipBoundsState, PipSizeSpecHandler pipSizeSpecHandler, PipDisplayLayoutState pipDisplayLayoutState, PipMotionHelper pipMotionHelper, PipMediaController pipMediaController, PhonePipMenuController phonePipMenuController, PipTaskOrganizer pipTaskOrganizer, PipTransitionState pipTransitionState, PipTouchHandler pipTouchHandler, PipTransitionController pipTransitionController, WindowManagerShellWrapper windowManagerShellWrapper, TaskStackListenerImpl taskStackListenerImpl, PipParamsChangedForwarder pipParamsChangedForwarder, DisplayInsetsController displayInsetsController, TabletopModeController tabletopModeController, Optional<OneHandedController> optional, ShellExecutor shellExecutor) {
        int i = 0;
        this.mMovePipInResponseToKeepClearAreasChangeCallback = new PipController$$ExternalSyntheticLambda1(this, i);
        this.mPinnedTaskListener = new PipControllerPinnedTaskListener(this, i);
        ?? r2 = new ServiceConnection() { // from class: com.android.wm.shell.pip.phone.PipController.6
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PipMenuControlService pipMenuControlService = (PipMenuControlService) LocalServices.getService(PipMenuControlService.class);
                if (pipMenuControlService != null) {
                    int i2 = PipMenuControlService.$r8$clinit;
                    Log.d("PipMenuControlService", "onServiceConnected. inject PhonePipMenuController");
                    PipController pipController = PipController.this;
                    PhonePipMenuController phonePipMenuController2 = pipController.mMenuController;
                    ShellExecutor shellExecutor2 = pipController.mMainExecutor;
                    pipMenuControlService.mPhonePipMenuController = phonePipMenuController2;
                    pipMenuControlService.mMainExecutor = shellExecutor2;
                }
            }

            @Override // android.content.ServiceConnection
            public final void onServiceDisconnected(ComponentName componentName) {
                int i2 = PipMenuControlService.$r8$clinit;
                Log.d("PipMenuControlService", "onServiceDisconnected.");
            }
        };
        this.mConnection = r2;
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        this.mImpl = new PipImpl(this, i);
        this.mWindowManagerShellWrapper = windowManagerShellWrapper;
        this.mDisplayController = displayController;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipKeepClearAlgorithm = pipKeepClearAlgorithmInterface;
        this.mPipBoundsState = pipBoundsState;
        this.mPipSizeSpecHandler = pipSizeSpecHandler;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mPipMotionHelper = pipMotionHelper;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mPipTransitionState = pipTransitionState;
        this.mMainExecutor = shellExecutor;
        this.mMediaController = pipMediaController;
        this.mMenuController = phonePipMenuController;
        this.mTouchHandler = pipTouchHandler;
        this.mPipAnimationController = pipAnimationController;
        this.mAppOpsListener = pipAppOpsListener;
        this.mOneHandedController = optional;
        this.mPipTransitionController = pipTransitionController;
        this.mTaskStackListener = taskStackListenerImpl;
        this.mEnterAnimationDuration = context.getResources().getInteger(R.integer.config_pipEnterAnimationDuration);
        this.mPipParamsChangedForwarder = pipParamsChangedForwarder;
        this.mDisplayInsetsController = displayInsetsController;
        this.mTabletopModeController = tabletopModeController;
        shellInit.addInitCallback(new PipController$$ExternalSyntheticLambda1(this, 1), this);
        context.bindService(new Intent(context, (Class<?>) PipMenuControlService.class), (ServiceConnection) r2, 1);
        new SettingsObserver(context.getMainThreadHandler());
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final Context getContext() {
        return this.mContext;
    }

    @Override // com.android.wm.shell.common.RemoteCallable
    public final ShellExecutor getRemoteCallExecutor() {
        return this.mMainExecutor;
    }

    public boolean hasPinnedStackAnimationListener() {
        return this.mPinnedStackAnimationRecentsCallback != null;
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        this.mPipBoundsAlgorithm.onConfigurationChanged(this.mContext);
        PipTouchHandler pipTouchHandler = this.mTouchHandler;
        pipTouchHandler.mPipResizeGestureHandler.reloadResources();
        pipTouchHandler.mMotionHelper.synchronizePinnedStackBounds();
        pipTouchHandler.reloadResources();
        if (pipTouchHandler.mPipTaskOrganizer.isInPip()) {
            pipTouchHandler.mPipDismissTargetHandler.getClass();
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.onConfigurationChanged();
        this.mPipSizeSpecHandler.reloadResources();
        if (pipBoundsState.isStashed()) {
            PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
            Rect bounds = pipBoundsState.getBounds();
            pipMotionHelper.adjustPipBoundsForEdge(bounds);
            pipMotionHelper.movePip(bounds, false);
        }
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (pipTaskOrganizer.isInPip()) {
            PhonePipMenuController phonePipMenuController = this.mMenuController;
            if (phonePipMenuController.mLastDensityDpi != configuration.densityDpi || !Locale.getDefault().equals(phonePipMenuController.mLastLocale)) {
                phonePipMenuController.attachPipMenuView();
            }
            phonePipMenuController.setSplitMenuEnabled(pipTaskOrganizer.shouldShowSplitMenu());
        }
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onDensityOrFontScaleChanged$1() {
        this.mPipTaskOrganizer.mSurfaceTransactionHelper.onDensityOrFontScaleChanged(this.mContext);
        onPipResourceDimensionsChanged();
    }

    public final void onDisplayChanged(DisplayLayout displayLayout, boolean z) {
        if (displayLayout == null) {
            Log.w("PipController", "onDisplayChanged - layout is null");
            return;
        }
        DisplayLayout displayLayout2 = this.mPipDisplayLayoutState.getDisplayLayout();
        boolean z2 = displayLayout2.mWidth == displayLayout.mWidth && displayLayout2.mHeight == displayLayout.mHeight && displayLayout2.mRotation == displayLayout.mRotation && displayLayout2.mDensityDpi == displayLayout.mDensityDpi && Objects.equals(displayLayout2.mCutout, displayLayout.mCutout);
        PipTransitionController pipTransitionController = this.mPipTransitionController;
        if (!z2 || (z && pipTransitionController.mDeferBoundsTransaction)) {
            if (pipTransitionController.mDeferBoundsTransaction) {
                pipTransitionController.mDeferBoundsTransaction = false;
            }
            PipAnimationController.PipTransitionAnimator pipTransitionAnimator = this.mPipAnimationController.mCurrentAnimator;
            if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning()) {
                pipTransitionAnimator.cancel();
            }
            onDisplayChangedUncheck(displayLayout, z);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x00d3, code lost:
    
        if (r14.getBounds().height() <= (r9.y + 1)) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDisplayChangedUncheck(DisplayLayout displayLayout, boolean z) {
        PipController$$ExternalSyntheticLambda0 pipController$$ExternalSyntheticLambda0 = new PipController$$ExternalSyntheticLambda0(0, this, displayLayout);
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (!pipTaskOrganizer.isInPip() || !z) {
            pipController$$ExternalSyntheticLambda0.run();
            return;
        }
        PhonePipMenuController phonePipMenuController = this.mMenuController;
        phonePipMenuController.attachPipMenuView();
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect rect = new Rect(pipBoundsState.getBounds());
        float snapFraction = pipSnapAlgorithm.getSnapFraction(pipBoundsState.mStashedState, rect, pipBoundsAlgorithm.getMovementBounds(rect, true));
        PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
        boolean z2 = (pipDisplayLayoutState.getDisplayLayout().mDensityDpi == 0 || pipDisplayLayoutState.getDisplayLayout().mDensityDpi == displayLayout.mDensityDpi) ? false : true;
        if (z2) {
            float f = displayLayout.mDensityDpi / pipDisplayLayoutState.getDisplayLayout().mDensityDpi;
            rect.set(0, 0, (int) (rect.width() * f), (int) (rect.height() * f));
        }
        pipController$$ExternalSyntheticLambda0.run();
        PipSnapAlgorithm.applySnapFraction(rect, pipBoundsAlgorithm.getMovementBounds(rect, false), snapFraction, pipBoundsState.mStashedState, pipBoundsState.mStashOffset, pipDisplayLayoutState.getDisplayBounds(), pipBoundsState.getStashInsets());
        if (z2) {
            pipTaskOrganizer.scheduleAnimateResizePip(rect, this.mContext.getResources().getInteger(R.integer.config_pipEnterAnimationDuration), 0);
        } else {
            pipTaskOrganizer.scheduleFinishResizePip(rect, null);
        }
        int width = pipBoundsState.getBounds().width();
        Point point = pipBoundsState.mMinSize;
        if (width >= point.x - 1) {
            int width2 = pipBoundsState.getBounds().width();
            Point point2 = pipBoundsState.mMaxSize;
            if (width2 <= point2.x + 1) {
                if (pipBoundsState.getBounds().height() >= point.y - 1) {
                }
            }
        }
        PipResizeGestureHandler pipResizeGestureHandler = this.mTouchHandler.mPipResizeGestureHandler;
        pipResizeGestureHandler.getClass();
        Rect rect2 = new Rect(pipBoundsState.mNormalBounds);
        PipBoundsAlgorithm pipBoundsAlgorithm2 = pipResizeGestureHandler.mPipBoundsAlgorithm;
        pipResizeGestureHandler.snapToMovementBoundsEdge(rect2, pipBoundsAlgorithm2.getMovementBounds(rect2, true));
        Rect movementBounds = pipBoundsAlgorithm2.getMovementBounds(rect2, true);
        pipBoundsAlgorithm2.mSnapAlgorithm.getClass();
        PipSnapAlgorithm.applySnapFraction(snapFraction, rect2, movementBounds);
        PipBoundsState pipBoundsState2 = pipResizeGestureHandler.mPipBoundsState;
        Rect bounds = pipBoundsState2.getBounds();
        PipTaskOrganizer pipTaskOrganizer2 = pipResizeGestureHandler.mPipTaskOrganizer;
        pipTaskOrganizer2.scheduleUserResizePip(bounds, rect2, 0.0f, null);
        pipBoundsState2.mHasUserResizedPip = true;
        pipTaskOrganizer2.scheduleFinishResizePip(rect2, pipResizeGestureHandler.mUpdateResizeBoundsCallback);
        phonePipMenuController.setSplitMenuEnabled(pipTaskOrganizer.shouldShowSplitMenu());
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardDismissAnimationFinished() {
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (pipTaskOrganizer.isInPip()) {
            this.mIsKeyguardShowingOrAnimating = false;
            if (pipTaskOrganizer.mWaitForFixedRotation) {
                Log.d("PipController", "mWaitForFixedRotation skip setPipVisibility");
            } else {
                pipTaskOrganizer.setPipVisibility(true);
            }
        }
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardVisibilityChanged(boolean z, boolean z2) {
        if (this.mPipTransitionState.mState == 4) {
            PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
            if (!z) {
                if (z2) {
                    return;
                }
                this.mIsKeyguardShowingOrAnimating = false;
                pipTaskOrganizer.setPipVisibility(true);
                return;
            }
            this.mIsKeyguardShowingOrAnimating = true;
            PhonePipMenuController phonePipMenuController = this.mMenuController;
            if (phonePipMenuController.isMenuVisible()) {
                phonePipMenuController.mPipMenuView.hideMenu$1();
            }
            pipTaskOrganizer.setPipVisibility(false);
            if (pipTaskOrganizer.isInPip() && pipTaskOrganizer.mIsInSecureFolder) {
                this.mTouchHandler.mMotionHelper.expandLeavePip(false, false);
            }
        }
    }

    public final void onPipResourceDimensionsChanged() {
        PipAnimationListener pipAnimationListener = this.mPinnedStackAnimationRecentsCallback;
        if (pipAnimationListener != null) {
            boolean z = CoreRune.MW_PIP_DISABLE_ROUND_CORNER;
            Context context = this.mContext;
            pipAnimationListener.onPipResourceDimensionsChanged(z ? 0 : context.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius), CoreRune.MW_PIP_DISABLE_ROUND_CORNER ? 0 : context.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius));
        }
    }

    @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
    public final void onPipTransitionCanceled(int i) {
        onPipTransitionFinishedOrCanceled(i);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
    public final void onPipTransitionFinished(int i) {
        onPipTransitionFinishedOrCanceled(i);
    }

    public final void onPipTransitionFinishedOrCanceled(int i) {
        InteractionJankMonitor.getInstance().end(35);
        PipTouchHandler pipTouchHandler = this.mTouchHandler;
        PipTouchState pipTouchState = pipTouchHandler.mTouchState;
        pipTouchState.mAllowTouches = true;
        if (pipTouchState.mIsUserInteracting) {
            pipTouchState.reset();
        }
        pipTouchHandler.mMotionHelper.synchronizePinnedStackBounds();
        pipTouchHandler.updateMovementBounds();
        int i2 = 2;
        if (i == 2) {
            pipTouchHandler.mPipResizeGestureHandler.setUserResizeBounds(pipTouchHandler.mPipBoundsState.getBounds());
        }
        if (i == 2) {
            ((HandlerExecutor) this.mMainExecutor).executeDelayed(150L, new PipController$$ExternalSyntheticLambda1(this, i2));
        }
    }

    @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
    public final void onPipTransitionStarted(int i, Rect rect) {
        String str;
        InteractionJankMonitor.Configuration.Builder withSurface = InteractionJankMonitor.Configuration.Builder.withSurface(35, this.mContext, this.mPipTaskOrganizer.mLeash);
        switch (i) {
            case 2:
                str = "TRANSITION_TO_PIP";
                break;
            case 3:
                str = "TRANSITION_LEAVE_PIP";
                break;
            case 4:
                str = "TRANSITION_LEAVE_PIP_TO_SPLIT_SCREEN";
                break;
            case 5:
                str = "TRANSITION_REMOVE_STACK";
                break;
            case 6:
                str = "TRANSITION_SNAP_AFTER_RESIZE";
                break;
            case 7:
                str = "TRANSITION_USER_RESIZE";
                break;
            case 8:
                str = "TRANSITION_EXPAND_OR_UNEXPAND";
                break;
            default:
                str = "TRANSITION_LEAVE_UNKNOWN";
                break;
        }
        InteractionJankMonitor.getInstance().begin(withSurface.setTag(str).setTimeout(2000L));
        boolean isOutPipDirection = PipAnimationController.isOutPipDirection(i);
        PipTouchHandler pipTouchHandler = this.mTouchHandler;
        if (isOutPipDirection) {
            PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
            float snapFraction = pipBoundsAlgorithm.mSnapAlgorithm.getSnapFraction(0, rect, pipBoundsAlgorithm.getMovementBounds(rect, true));
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            if (pipBoundsState.mHasUserResizedPip) {
                Size size = new Size(rect.width(), rect.height());
                if (!pipTouchHandler.mPipResizeGestureHandler.mUserResizeBounds.isEmpty()) {
                    Rect rect2 = pipTouchHandler.mPipResizeGestureHandler.mUserResizeBounds;
                    size = new Size(rect2.width(), rect2.height());
                }
                pipBoundsState.mPipReentryState = new PipBoundsState.PipReentryState(size, snapFraction);
            } else {
                pipBoundsState.mPipReentryState = new PipBoundsState.PipReentryState(null, snapFraction);
            }
        }
        PipTouchState pipTouchState = pipTouchHandler.mTouchState;
        pipTouchState.mAllowTouches = false;
        if (pipTouchState.mIsUserInteracting) {
            pipTouchState.reset();
        }
        PipAnimationListener pipAnimationListener = this.mPinnedStackAnimationRecentsCallback;
        if (pipAnimationListener != null) {
            pipAnimationListener.onPipAnimationStarted();
            if (i == 3) {
                this.mPinnedStackAnimationRecentsCallback.onExpandPip();
            }
        }
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onThemeChanged() {
        this.mTouchHandler.mPipDismissTargetHandler.getClass();
        Context context = this.mContext;
        onDisplayChanged(new DisplayLayout(context, context.getDisplay()), false);
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onUserChanged$1(int i) {
        PipMediaController pipMediaController = this.mMediaController;
        MediaSessionManager mediaSessionManager = pipMediaController.mMediaSessionManager;
        PipMediaController$$ExternalSyntheticLambda0 pipMediaController$$ExternalSyntheticLambda0 = pipMediaController.mSessionsChangedListener;
        mediaSessionManager.removeOnActiveSessionsChangedListener(pipMediaController$$ExternalSyntheticLambda0);
        mediaSessionManager.addOnActiveSessionsChangedListener(null, UserHandle.CURRENT, pipMediaController.mHandlerExecutor, pipMediaController$$ExternalSyntheticLambda0);
    }

    public void setEnablePipKeepClearAlgorithm(boolean z) {
        this.mEnablePipKeepClearAlgorithm = z;
    }

    public void setPinnedStackAnimationListener(PipAnimationListener pipAnimationListener) {
        this.mPinnedStackAnimationRecentsCallback = pipAnimationListener;
        onPipResourceDimensionsChanged();
    }

    public final void updateMovementBounds(Rect rect, boolean z, boolean z2, boolean z3, WindowContainerTransaction windowContainerTransaction) {
        int i;
        int i2;
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        int i3 = pipTaskOrganizer.mPipTransitionState.mState;
        if (z3 && (i3 == 2 || i3 == 3)) {
            return;
        }
        Rect rect2 = new Rect(rect);
        int i4 = this.mPipDisplayLayoutState.getDisplayLayout().mRotation;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        Rect rect3 = this.mTmpInsetBounds;
        pipBoundsAlgorithm.getInsetBounds(rect3);
        Rect defaultBounds = pipBoundsAlgorithm.getDefaultBounds(null, -1.0f);
        float f = pipBoundsAlgorithm.mPipBoundsState.mAspectRatio;
        Rect rect4 = new Rect(defaultBounds);
        if (Float.compare(pipBoundsAlgorithm.mMinAspectRatio, f) <= 0 && Float.compare(f, pipBoundsAlgorithm.mMaxAspectRatio) <= 0) {
            pipBoundsAlgorithm.transformBoundsToAspectRatio(rect4, f, false, false);
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.mNormalBounds.set(rect4);
        if (rect2.isEmpty()) {
            rect2.set(pipBoundsAlgorithm.getDefaultBounds(null, -1.0f));
        }
        boolean z4 = pipTaskOrganizer.mWaitForFixedRotation;
        PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
        boolean z5 = z4 && pipTransitionState.mState != 4;
        boolean z6 = pipTransitionState.mInSwipePipToHomeTransition;
        if ((!z6 && !z5) || !z) {
            PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipTaskOrganizer.mPipAnimationController.mCurrentAnimator;
            PipBoundsState pipBoundsState2 = pipTaskOrganizer.mPipBoundsState;
            if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning() && pipTransitionAnimator.getTransitionDirection() == 2) {
                Rect rect5 = pipTransitionAnimator.mDestinationBounds;
                rect2.set(rect5);
                if (z2 || z3 || !pipBoundsState2.getDisplayBounds().contains(rect5)) {
                    Rect entryDestinationBounds = pipTaskOrganizer.mPipBoundsAlgorithm.getEntryDestinationBounds();
                    if (!entryDestinationBounds.equals(rect5)) {
                        pipTaskOrganizer.updateAnimatorBounds(entryDestinationBounds);
                        rect2.set(entryDestinationBounds);
                    }
                }
            } else {
                boolean z7 = pipTransitionState.isInPip() && z;
                if (z7 && Transitions.ENABLE_SHELL_TRANSITIONS) {
                    pipBoundsState2.setBounds(rect2);
                } else if (z7 && pipTaskOrganizer.mWaitForFixedRotation && pipTaskOrganizer.mHasFadeOut) {
                    pipBoundsState2.setBounds(rect2);
                } else if (z7) {
                    pipBoundsState2.setBounds(rect2);
                    if (pipTransitionAnimator != null) {
                        i2 = pipTransitionAnimator.getTransitionDirection();
                        PipAnimationController.quietCancel(pipTransitionAnimator);
                        pipTaskOrganizer.mPipTransitionController.sendOnPipTransitionCancelled(i2);
                        pipTaskOrganizer.sendOnPipTransitionFinished(i2);
                    } else {
                        i2 = 0;
                    }
                    pipTaskOrganizer.prepareFinishResizeTransaction(rect2, i2, pipTaskOrganizer.createFinishResizeSurfaceTransaction(rect2), windowContainerTransaction);
                } else if (pipTransitionAnimator == null || !pipTransitionAnimator.isRunning()) {
                    if (!pipBoundsState2.getBounds().isEmpty()) {
                        rect2.set(pipBoundsState2.getBounds());
                    }
                } else if (!pipTransitionAnimator.mDestinationBounds.isEmpty()) {
                    rect2.set(pipTransitionAnimator.mDestinationBounds);
                }
            }
        } else if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1566841868, 124, "%s: Skip onMovementBoundsChanged on rotation change InSwipePipToHomeTransition=%b mWaitForFixedRotation=%b getTransitionState=%d", "PipTaskOrganizer", Boolean.valueOf(z6), Boolean.valueOf(z4), Long.valueOf(pipTransitionState.mState));
        }
        pipTaskOrganizer.finishResizeForMenu(rect2);
        PipTouchHandler pipTouchHandler = this.mTouchHandler;
        boolean isEmpty = pipTouchHandler.mPipResizeGestureHandler.mUserResizeBounds.isEmpty();
        Rect rect6 = pipBoundsState.mNormalBounds;
        if (isEmpty) {
            pipTouchHandler.mPipResizeGestureHandler.setUserResizeBounds(rect6);
        }
        int i5 = pipTouchHandler.mIsImeShowing ? pipTouchHandler.mImeHeight : 0;
        boolean z8 = pipTouchHandler.mDisplayRotation != i4;
        PipTouchState pipTouchState = pipTouchHandler.mTouchState;
        if (z8) {
            pipTouchState.reset();
        }
        Rect rect7 = new Rect();
        PipBoundsAlgorithm pipBoundsAlgorithm2 = pipTouchHandler.mPipBoundsAlgorithm;
        pipBoundsAlgorithm2.getClass();
        PipBoundsAlgorithm.getMovementBounds(rect6, rect3, rect7, i5);
        PipBoundsState pipBoundsState3 = pipTouchHandler.mPipBoundsState;
        boolean isEmpty2 = pipBoundsState3.mMovementBounds.isEmpty();
        Rect rect8 = pipBoundsState3.mMovementBounds;
        if (isEmpty2) {
            PipBoundsAlgorithm.getMovementBounds(rect2, rect3, rect8, 0);
        }
        float width = rect6.width() / rect6.height();
        Size defaultSize = pipTouchHandler.mPipSizeSpecHandler.mSizeSpecSourceImpl.getDefaultSize(width);
        Rect rect9 = new Rect(0, 0, defaultSize.getWidth(), defaultSize.getHeight());
        Rect rect10 = pipBoundsState3.mExpandedBounds;
        rect10.set(rect9);
        Rect rect11 = new Rect();
        PipBoundsAlgorithm.getMovementBounds(rect10, rect3, rect11, i5);
        pipTouchHandler.updatePipSizeConstraints(rect6, width);
        boolean z9 = pipTouchHandler.mIsImeShowing;
        int max = Math.max(z9 ? pipTouchHandler.mImeOffset : 0, (z9 || !pipTouchHandler.mIsShelfShowing) ? 0 : pipTouchHandler.mShelfHeight);
        if ((z2 || z3) && ((!pipTouchState.mIsUserInteracting || !pipTouchState.mIsDragging) && !pipTouchHandler.mEnablePipKeepClearAlgorithm)) {
            boolean z10 = pipTouchHandler.mMenuState == 1 && pipTouchHandler.willResizeMenu();
            Rect rect12 = new Rect();
            PipBoundsAlgorithm.getMovementBounds(rect2, rect3, rect12, pipTouchHandler.mIsImeShowing ? pipTouchHandler.mImeHeight : 0);
            int i6 = rect8.bottom - pipTouchHandler.mMovementBoundsExtraOffsets;
            int i7 = rect12.bottom;
            if (i7 >= rect12.top) {
                i7 -= max;
            }
            if (z10) {
                rect2.set(rect10);
                float f2 = pipTouchHandler.mSavedSnapFraction;
                pipBoundsAlgorithm2.mSnapAlgorithm.getClass();
                PipSnapAlgorithm.applySnapFraction(f2, rect2, rect12);
            }
            if (i6 < i7) {
                int i8 = rect2.top;
                if (i8 > i6 - pipTouchHandler.mBottomOffsetBufferPx) {
                    pipTouchHandler.mMotionHelper.animateToOffset(i7 - i8, rect2);
                }
            } else if (i6 > i7 && (i = rect2.top) > i7 - pipTouchHandler.mBottomOffsetBufferPx) {
                pipTouchHandler.mMotionHelper.animateToOffset(i7 - i, rect2);
            }
        }
        Rect rect13 = pipBoundsState3.mNormalMovementBounds;
        rect13.set(rect7);
        Rect rect14 = pipBoundsState3.mExpandedMovementBounds;
        rect14.set(rect11);
        pipTouchHandler.mDisplayRotation = i4;
        pipTouchHandler.mInsetBounds.set(rect3);
        pipTouchHandler.updateMovementBounds();
        pipTouchHandler.mMovementBoundsExtraOffsets = max;
        PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection = pipTouchHandler.mConnection;
        pipAccessibilityInteractionConnection.mNormalBounds.set(rect6);
        pipAccessibilityInteractionConnection.mExpandedBounds.set(rect10);
        pipAccessibilityInteractionConnection.mNormalMovementBounds.set(rect13);
        pipAccessibilityInteractionConnection.mExpandedMovementBounds.set(rect14);
        if (pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation == i4) {
            pipTouchHandler.mMotionHelper.animateToUnexpandedState(rect6, pipTouchHandler.mSavedSnapFraction, pipBoundsState3.mNormalMovementBounds, pipBoundsState3.mMovementBounds, true);
            pipTouchHandler.mSavedSnapFraction = -1.0f;
            pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation = -1;
        }
    }

    public final void updatePipPositionForKeepClearAreas() {
        if (this.mEnablePipKeepClearAlgorithm && !this.mIsKeyguardShowingOrAnimating) {
            PipTransitionState pipTransitionState = this.mPipTransitionState;
            int i = pipTransitionState.mState;
            if (i < 3 || i == 5) {
                return;
            }
            PipKeepClearAlgorithmInterface pipKeepClearAlgorithmInterface = this.mPipKeepClearAlgorithm;
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            Rect adjust = pipKeepClearAlgorithmInterface.adjust(pipBoundsState, this.mPipBoundsAlgorithm);
            if (adjust.equals(pipBoundsState.getBounds())) {
                return;
            }
            int i2 = pipTransitionState.mState;
            boolean z = i2 == 4;
            PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
            if (z) {
                pipTaskOrganizer.scheduleAnimateResizePip(adjust, this.mEnterAnimationDuration, 0);
                return;
            }
            if (i2 == 3) {
                pipTaskOrganizer.updateAnimatorBounds(adjust);
            }
        }
    }
}
