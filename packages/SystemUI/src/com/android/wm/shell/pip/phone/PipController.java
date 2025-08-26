package com.android.wm.shell.pip.phone;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PictureInPictureParams;
import android.app.PictureInPictureUiState;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Log;
import android.util.Size;
import android.util.Slog;
import android.view.InsetsState;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.server.LocalServices;
import com.android.systemui.R;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.wmshell.WMShell;
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
import com.android.wm.shell.common.pip.IPip;
import com.android.wm.shell.common.pip.IPipAnimationListener$Stub$Proxy;
import com.android.wm.shell.common.pip.PhonePipKeepClearAlgorithm;
import com.android.wm.shell.common.pip.PhoneSizeSpecSource;
import com.android.wm.shell.common.pip.PipAppOpsListener;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipKeepClearAlgorithmInterface;
import com.android.wm.shell.common.pip.PipMediaController;
import com.android.wm.shell.common.pip.PipMediaController$mSessionsChangedListener$1;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.pip.PinnedStackListenerForwarder;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipMenuControlService;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda15;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class PipController implements PipTransitionController.PipTransitionCallback, RemoteCallable, ConfigurationChangeListener, KeyguardChangeListener, UserChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long PIP_KEEP_CLEAR_AREAS_DELAY = SystemProperties.getLong("persist.wm.debug.pip_keep_clear_areas_delay", 200);
    public final PipAppOpsListener mAppOpsListener;
    public final AnonymousClass6 mConnection;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final int mEnterAnimationDuration;
    public final Handler mHandler;
    public final PipImpl mImpl;
    public boolean mIsInFixedRotation;
    public boolean mIsKeyguardShowingOrAnimating;
    public final ShellExecutor mMainExecutor;
    public final PipMediaController mMediaController;
    public final PhonePipMenuController mMenuController;
    public final Optional mOneHandedController;
    public PipAnimationListener mPinnedStackAnimationRecentsCallback;
    public final PipAnimationController mPipAnimationController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public PipInputConsumer mPipInputConsumer;
    public final PipKeepClearAlgorithmInterface mPipKeepClearAlgorithm;
    public final PipParamsChangedForwarder mPipParamsChangedForwarder;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipTransitionController mPipTransitionController;
    public final PipTransitionState mPipTransitionState;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public final TabletopModeController mTabletopModeController;
    public final TaskStackListenerImpl mTaskStackListener;
    public final PipTouchHandler mTouchHandler;
    public final WindowManagerShellWrapper mWindowManagerShellWrapper;
    public final Rect mTmpInsetBounds = new Rect();
    public final PipController$$ExternalSyntheticLambda3 mMovePipInResponseToKeepClearAreasChangeCallback = new PipController$$ExternalSyntheticLambda3(this, 1);
    public final PipController$$ExternalSyntheticLambda3 mEnableTouchCallback = new PipController$$ExternalSyntheticLambda3(this, 5);
    public final PipControllerPinnedTaskListener mPinnedTaskListener = new PipControllerPinnedTaskListener(this, 0);
    public final List mOnIsInPipStateChangedListeners = new ArrayList();
    public final PipController$$ExternalSyntheticLambda2 mRotationController = new PipController$$ExternalSyntheticLambda2(this);
    final DisplayController.OnDisplaysChangedListener mDisplaysChangedListener = new DisplayController.OnDisplaysChangedListener() { // from class: com.android.wm.shell.pip.phone.PipController.1
        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayAdded(int i) throws Resources.NotFoundException {
            PipController pipController = PipController.this;
            if (i != pipController.mPipDisplayLayoutState.mDisplayId) {
                return;
            }
            pipController.onDisplayChanged(pipController.mDisplayController.getDisplayLayout(i), true);
        }

        @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
        public final void onDisplayConfigurationChanged(int i, Configuration configuration) throws Resources.NotFoundException {
            PipController pipController = PipController.this;
            if (i != pipController.mPipDisplayLayoutState.mDisplayId) {
                return;
            }
            pipController.onDisplayChanged(pipController.mDisplayController.getDisplayLayout(i), true);
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
            if (pipController.mPipDisplayLayoutState.mDisplayId == i) {
                PipBoundsState pipBoundsState = pipController.mPipBoundsState;
                ((ArraySet) pipBoundsState.mRestrictedKeepClearAreas).clear();
                ((ArraySet) pipBoundsState.mRestrictedKeepClearAreas).addAll(set);
                ((ArraySet) pipBoundsState.mUnrestrictedKeepClearAreas).clear();
                ((ArraySet) pipBoundsState.mUnrestrictedKeepClearAreas).addAll(set2);
                HandlerExecutor handlerExecutor = (HandlerExecutor) pipController.mMainExecutor;
                PipController$$ExternalSyntheticLambda3 pipController$$ExternalSyntheticLambda3 = pipController.mMovePipInResponseToKeepClearAreasChangeCallback;
                handlerExecutor.removeCallbacks(pipController$$ExternalSyntheticLambda3);
                ((HandlerExecutor) pipController.mMainExecutor).executeDelayed(pipController$$ExternalSyntheticLambda3, PipController.PIP_KEEP_CLEAR_AREAS_DELAY);
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -5881928171608984360L, 0, String.valueOf(set), String.valueOf(set2));
                }
            }
        }
    };

    /* renamed from: com.android.wm.shell.pip.phone.PipController$3, reason: invalid class name */
    public class AnonymousClass3 {
        public AnonymousClass3() {
        }
    }

    /* renamed from: com.android.wm.shell.pip.phone.PipController$4, reason: invalid class name */
    public class AnonymousClass4 implements DisplayInsetsController.OnInsetsChangedListener {
        public AnonymousClass4() {
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(InsetsState insetsState) {
            PipController pipController = PipController.this;
            DisplayLayout displayLayout = pipController.mDisplayController.getDisplayLayout(pipController.mPipDisplayLayoutState.mDisplayId);
            if (displayLayout == null) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -6947857911936697226L, 1, Long.valueOf(pipController.mPipDisplayLayoutState.mDisplayId));
                    return;
                }
                return;
            }
            if (pipController.mIsInFixedRotation || pipController.mIsKeyguardShowingOrAnimating || displayLayout.mRotation != pipController.mPipBoundsState.mPipDisplayLayoutState.getDisplayLayout().mRotation) {
                return;
            }
            ((HandlerExecutor) pipController.mMainExecutor).executeDelayed(new PipController$4$$ExternalSyntheticLambda0(this, 0), PipController.PIP_KEEP_CLEAR_AREAS_DELAY);
        }
    }

    public class IPipImpl extends IPip.Stub implements ExternalInterfaceBinder {
        public static final /* synthetic */ int $r8$clinit = 0;
        public PipController mController;
        public final SingleInstanceRemoteListener mListener;
        public final AnonymousClass1 mPipAnimationListener = new PipAnimationListener() { // from class: com.android.wm.shell.pip.phone.PipController.IPipImpl.1
            @Override // com.android.wm.shell.pip.phone.PipController.PipAnimationListener
            public final void onExpandPip() {
                IInterface iInterface = IPipImpl.this.mListener.mListener;
                if (iInterface == null) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call on null listener");
                    return;
                }
                try {
                    IPipAnimationListener$Stub$Proxy iPipAnimationListener$Stub$Proxy = (IPipAnimationListener$Stub$Proxy) iInterface;
                    Parcel parcelObtain = Parcel.obtain(iPipAnimationListener$Stub$Proxy.mRemote);
                    try {
                        parcelObtain.writeInterfaceToken("com.android.wm.shell.common.pip.IPipAnimationListener");
                        iPipAnimationListener$Stub$Proxy.mRemote.transact(3, parcelObtain, null, 1);
                        parcelObtain.recycle();
                    } catch (Throwable th) {
                        parcelObtain.recycle();
                        throw th;
                    }
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
                    ((IPipAnimationListener$Stub$Proxy) iInterface).onPipAnimationStarted();
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
                    ((IPipAnimationListener$Stub$Proxy) iInterface).onPipResourceDimensionsChanged(i, i2);
                } catch (RemoteException e) {
                    Slog.e("SingleInstanceRemoteListener", "Failed remote call", e);
                }
            }
        };

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.wm.shell.pip.phone.PipController$IPipImpl$1] */
        public IPipImpl(PipController pipController) {
            this.mController = pipController;
            this.mListener = new SingleInstanceRemoteListener(pipController, new PipController$$ExternalSyntheticLambda6(this, 1), new PipController$IPipImpl$$ExternalSyntheticLambda2(1));
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void abortSwipePipToHome(int i, ComponentName componentName) {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "abortSwipePipToHome", new PipController$$ExternalSyntheticLambda6(i, componentName), false);
        }

        @Override // com.android.wm.shell.common.ExternalInterfaceBinder
        public final void invalidate() {
            this.mController = null;
            this.mListener.unregister();
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setLauncherAppIconSize(final int i) {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "setLauncherAppIconSize", new Consumer() { // from class: com.android.wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2 = i;
                    int i3 = PipController.IPipImpl.$r8$clinit;
                    ((PipController) obj).mPipBoundsState.mLauncherState.mAppIconSizePx = i2;
                }
            }, false);
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setLauncherKeepClearAreaHeight(int i, boolean z) {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "setLauncherKeepClearAreaHeight", new PipController$IPipImpl$$ExternalSyntheticLambda6(z, i, 1), false);
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setPipAnimationListener(final IPipAnimationListener$Stub$Proxy iPipAnimationListener$Stub$Proxy) {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "setPipAnimationListener", new Consumer() { // from class: com.android.wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws RemoteException {
                    PipController.IPipImpl iPipImpl = this.f$0;
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
        public final void setPipAnimationTypeToAlpha() {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "setPipAnimationTypeToAlpha", new PipController$IPipImpl$$ExternalSyntheticLambda2(0), false);
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void setShelfHeight(int i, boolean z) {
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "setShelfHeight", new PipController$IPipImpl$$ExternalSyntheticLambda6(z, i, 0), false);
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final Rect startSwipePipToHome(final ActivityManager.RunningTaskInfo runningTaskInfo, final int i, final Rect rect) {
            final Rect[] rectArr = new Rect[1];
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "startSwipePipToHome", new Consumer() { // from class: com.android.wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) throws Resources.NotFoundException {
                    Rect[] rectArr2 = rectArr;
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = runningTaskInfo;
                    int i2 = i;
                    Rect rect2 = rect;
                    PipController pipController = (PipController) obj;
                    int i3 = PipController.IPipImpl.$r8$clinit;
                    ComponentName componentName = runningTaskInfo2.topActivity;
                    ActivityInfo activityInfo = runningTaskInfo2.topActivityInfo;
                    PictureInPictureParams pictureInPictureParams = runningTaskInfo2.pictureInPictureParams;
                    PipBoundsState pipBoundsState = pipController.mPipBoundsState;
                    pipBoundsState.setNamedUnrestrictedKeepClearArea(0, rect2);
                    pipController.mPipDisplayLayoutState.rotateTo(i2);
                    Point point = pipBoundsState.mMinSize;
                    Point point2 = pipBoundsState.mMaxSize;
                    pipBoundsState.updateMinMaxSize(pictureInPictureParams.hasSetAspectRatio() ? pictureInPictureParams.getAspectRatioFloat() : pipController.mPipBoundsAlgorithm.mDefaultAspectRatio);
                    PipTaskOrganizer pipTaskOrganizer = pipController.mPipTaskOrganizer;
                    pipTaskOrganizer.getClass();
                    boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0];
                    PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
                    if (z) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 8997930464045709859L, 0, String.valueOf(componentName), String.valueOf(pipTransitionState));
                    }
                    Log.d("PipTaskOrganizer", "startSwipePipToHome");
                    pipTransitionState.mInSwipePipToHomeTransition = true;
                    boolean z2 = Transitions.ENABLE_SHELL_TRANSITIONS;
                    PipTransitionController pipTransitionController = pipTaskOrganizer.mPipTransitionController;
                    if (z2) {
                        pipTransitionController.getClass();
                        try {
                            ActivityTaskManager.getService().onPictureInPictureUiStateChanged(new PictureInPictureUiState.Builder().setTransitioningToPip(true).build());
                        } catch (RemoteException | IllegalStateException unused) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -8189372673897383003L, 0, null);
                            }
                        }
                    } else {
                        pipTaskOrganizer.mPipTransitionState.setTransitionState(3);
                        pipTransitionController.sendOnPipTransitionStarted$1(2);
                        pipTaskOrganizer.mTransitionDirection = 2;
                    }
                    PipBoundsState pipBoundsState2 = pipTaskOrganizer.mPipBoundsState;
                    PipBoundsAlgorithm pipBoundsAlgorithm = pipTaskOrganizer.mPipBoundsAlgorithm;
                    pipBoundsState2.setBoundsStateForEntry(componentName, activityInfo, pictureInPictureParams, pipBoundsAlgorithm);
                    Rect entryDestinationBounds = pipBoundsAlgorithm.getEntryDestinationBounds();
                    pipBoundsState.mMinSize.set(point.x, point.y);
                    pipBoundsState.mMaxSize.set(point2.x, point2.y);
                    pipBoundsState.mNormalBounds.set(entryDestinationBounds);
                    rectArr2[0] = entryDestinationBounds;
                }
            }, true);
            return rectArr[0];
        }

        @Override // com.android.wm.shell.common.pip.IPip
        public final void stopSwipePipToHome(final int i, final ComponentName componentName, final Rect rect, final SurfaceControl surfaceControl, final Rect rect2, final Rect rect3) {
            if (surfaceControl != null) {
                surfaceControl.setUnreleasedWarningCallSite("PipController.stopSwipePipToHome");
            }
            ExternalInterfaceBinder.executeRemoteCallWithTaskPermission(this.mController, "stopSwipePipToHome", new Consumer() { // from class: com.android.wm.shell.pip.phone.PipController$IPipImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i2 = i;
                    ComponentName componentName2 = componentName;
                    Rect rect4 = rect;
                    SurfaceControl surfaceControl2 = surfaceControl;
                    Rect rect5 = rect2;
                    Rect rect6 = rect3;
                    int i3 = PipController.IPipImpl.$r8$clinit;
                    PipTaskOrganizer pipTaskOrganizer = ((PipController) obj).mPipTaskOrganizer;
                    pipTaskOrganizer.getClass();
                    boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0];
                    PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
                    if (z) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -193681310390012114L, 0, String.valueOf(componentName2), String.valueOf(pipTransitionState));
                    }
                    if (rect4 != null) {
                        Log.d("PipTaskOrganizer", "stopSwipePipToHome mInSwipePipToHomeTransition=" + pipTransitionState.mInSwipePipToHomeTransition + " destination=" + rect4);
                    }
                    if (pipTransitionState.mInSwipePipToHomeTransition) {
                        if (rect4.isEmpty()) {
                            Log.w("PipTaskOrganizer", "stopSwipePipToHome PIP empty, setDefaultBounds");
                            rect4.set(pipTaskOrganizer.mPipBoundsAlgorithm.getEntryDestinationBounds());
                        }
                        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                            pipTaskOrganizer.setSwipingPipTaskId(i2, "from_home");
                        }
                        pipTaskOrganizer.mPipBoundsState.setBounds(rect4);
                        pipTaskOrganizer.mPipOverlay = surfaceControl2;
                        if (surfaceControl2 != null) {
                            pipTaskOrganizer.mAppBounds.set(rect5);
                        } else {
                            pipTaskOrganizer.mAppBounds.setEmpty();
                        }
                        pipTaskOrganizer.mSwipeSourceRectHint = rect6;
                        if (!Transitions.ENABLE_SHELL_TRANSITIONS || surfaceControl2 == null) {
                            return;
                        }
                        SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipTaskOrganizer.mSurfaceControlTransactionFactory).getTransaction();
                        pipTaskOrganizer.mTaskOrganizer.reparentChildSurfaceToTask(i2, transaction, surfaceControl2);
                        transaction.setLayer(surfaceControl2, Integer.MAX_VALUE);
                        transaction.apply();
                        long j = (pipTaskOrganizer.mEnterAnimationDuration + 500 + PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS) * 2;
                        ((HandlerExecutor) pipTaskOrganizer.mMainExecutor).executeDelayed(new PipTaskOrganizer$$ExternalSyntheticLambda15(pipTaskOrganizer, new WeakReference(surfaceControl2), 2), j);
                    }
                }
            }, false);
        }
    }

    interface PipAnimationListener {
        void onExpandPip();

        void onPipAnimationStarted();

        void onPipResourceDimensionsChanged(int i, int i2);
    }

    public class PipControllerPinnedTaskListener extends PinnedStackListenerForwarder.PinnedTaskListener {
        public /* synthetic */ PipControllerPinnedTaskListener(PipController pipController, int i) {
            this();
        }

        @Override // com.android.wm.shell.pip.PinnedStackListenerForwarder.PinnedTaskListener
        public final void onImeVisibilityChanged(boolean z, int i) throws Resources.NotFoundException {
            PipController pipController = PipController.this;
            PipBoundsState pipBoundsState = pipController.mPipBoundsState;
            pipBoundsState.mIsImeShowing = z;
            pipBoundsState.mImeHeight = i;
            if (z) {
                pipBoundsState.mRestoreBounds.set(pipBoundsState.getBounds());
            }
            PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
            pipTouchHandler.mIsImeShowing = z;
            pipTouchHandler.mImeHeight = i;
            if (z) {
                pipController.updatePipPositionForKeepClearAreas();
            }
        }

        @Override // com.android.wm.shell.pip.PinnedStackListenerForwarder.PinnedTaskListener
        public final void onMovementBoundsChanged(boolean z) {
            int i = PipController.$r8$clinit;
            PipController.this.updateMovementBounds(null, false, z, false, null);
        }

        private PipControllerPinnedTaskListener() {
        }
    }

    public class PipImpl implements Pip {
        public PipImpl() {
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void addOnIsInPipStateChangedListener(Consumer consumer) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, consumer, 2));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void addPipExclusionBoundsChangeListener(Consumer consumer) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, consumer, 1));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final boolean isExitingPipToLastParent(int i) {
            int i2;
            PipController pipController = PipController.this;
            ActivityManager.RunningTaskInfo runningTaskInfo = pipController.mPipTaskOrganizer.mTaskInfo;
            return runningTaskInfo != null && (i2 = runningTaskInfo.lastParentTaskIdBeforePip) != -1 && i == i2 && pipController.mPipTransitionState.mState == 5;
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void onSystemUiStateChanged(final long j, final boolean z) {
            PipController.this.mMainExecutor.execute(new Runnable(z, j) { // from class: com.android.wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda2
                public final /* synthetic */ boolean f$1;

                @Override // java.lang.Runnable
                public final void run() {
                    PipController.this.mTouchHandler.mPipResizeGestureHandler.mIsSysUiStateValid = this.f$1;
                }
            });
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void registerPipTransitionCallback(final WMShell.AnonymousClass7 anonymousClass7, final Executor executor) {
            PipController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.pip.phone.PipController$PipImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    PipController.PipImpl pipImpl = this.f$0;
                    ((HashMap) PipController.this.mPipTransitionController.mPipTransitionCallbacks).put(anonymousClass7, executor);
                }
            });
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void removeOnIsInPipStateChangedListener(EdgeBackGestureHandler$$ExternalSyntheticLambda0 edgeBackGestureHandler$$ExternalSyntheticLambda0) {
            PipController.this.mMainExecutor.execute(new PipController$$ExternalSyntheticLambda4(1, this, edgeBackGestureHandler$$ExternalSyntheticLambda0));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void removePipExclusionBoundsChangeListener(Consumer consumer) {
            PipController.this.mMainExecutor.execute(new PipController$PipImpl$$ExternalSyntheticLambda0(this, consumer, 0));
        }

        @Override // com.android.wm.shell.pip.Pip
        public final void showPictureInPictureMenu() {
            PipController.this.mMainExecutor.execute(new PipController$4$$ExternalSyntheticLambda0(this, 1));
        }
    }

    public class SettingsObserver extends ContentObserver {
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
    /* JADX WARN: Type inference failed for: r0v7, types: [android.content.ServiceConnection, com.android.wm.shell.pip.phone.PipController$6] */
    public PipController(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, ShellController shellController, DisplayController displayController, PipAnimationController pipAnimationController, PipAppOpsListener pipAppOpsListener, PipBoundsAlgorithm pipBoundsAlgorithm, PipKeepClearAlgorithmInterface pipKeepClearAlgorithmInterface, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipMotionHelper pipMotionHelper, PipMediaController pipMediaController, PhonePipMenuController phonePipMenuController, PipTaskOrganizer pipTaskOrganizer, PipTransitionState pipTransitionState, PipTouchHandler pipTouchHandler, PipTransitionController pipTransitionController, WindowManagerShellWrapper windowManagerShellWrapper, TaskStackListenerImpl taskStackListenerImpl, PipParamsChangedForwarder pipParamsChangedForwarder, DisplayInsetsController displayInsetsController, TabletopModeController tabletopModeController, Optional<OneHandedController> optional, ShellExecutor shellExecutor, Handler handler) {
        ?? r0 = new ServiceConnection() { // from class: com.android.wm.shell.pip.phone.PipController.6
            @Override // android.content.ServiceConnection
            public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                PipMenuControlService pipMenuControlService = (PipMenuControlService) LocalServices.getService(PipMenuControlService.class);
                if (pipMenuControlService != null) {
                    int i = PipMenuControlService.$r8$clinit;
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
                int i = PipMenuControlService.$r8$clinit;
                Log.d("PipMenuControlService", "onServiceDisconnected.");
            }
        };
        this.mConnection = r0;
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.mShellController = shellController;
        this.mHandler = handler;
        this.mImpl = new PipImpl();
        this.mWindowManagerShellWrapper = windowManagerShellWrapper;
        this.mDisplayController = displayController;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipKeepClearAlgorithm = pipKeepClearAlgorithmInterface;
        this.mPipBoundsState = pipBoundsState;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
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
        if (!PipUtils.isPip2ExperimentEnabled()) {
            shellInit.addInitCallback(new PipController$$ExternalSyntheticLambda3(this, 0), this);
        }
        context.bindService(new Intent(context, (Class<?>) PipMenuControlService.class), (ServiceConnection) r0, 1);
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
    public final void onConfigurationChanged(Configuration configuration) throws Resources.NotFoundException {
        this.mPipBoundsAlgorithm.reloadResources(this.mContext);
        PipTouchHandler pipTouchHandler = this.mTouchHandler;
        pipTouchHandler.mPipResizeGestureHandler.reloadResources();
        pipTouchHandler.mMotionHelper.synchronizePinnedStackBounds();
        Resources resources = pipTouchHandler.mContext.getResources();
        pipTouchHandler.mBottomOffsetBufferPx = resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
        pipTouchHandler.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
        PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler.mPipDismissTargetHandler;
        pipDismissTargetHandler.getClass();
        if (pipTouchHandler.mPipTaskOrganizer.isInPip()) {
            pipDismissTargetHandler.getClass();
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.mStashOffset = pipBoundsState.mContext.getResources().getDimensionPixelSize(R.dimen.pip_stash_offset);
        pipBoundsState.mPipEdgeMargin = pipBoundsState.mContext.getResources().getDimensionPixelSize(R.dimen.pip_stash_handle_margin_to_edge_handle);
        ((PhoneSizeSpecSource) pipBoundsState.mSizeSpecSource).reloadResources();
        this.mPipDisplayLayoutState.reloadResources();
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
        PipAnimationListener pipAnimationListener = this.mPinnedStackAnimationRecentsCallback;
        if (pipAnimationListener != null) {
            pipAnimationListener.onPipResourceDimensionsChanged(this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius), this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius));
        }
    }

    public final void onDisplayChanged(DisplayLayout displayLayout, boolean z) throws Resources.NotFoundException {
        if (displayLayout == null) {
            Log.w("PipController", "onDisplayChanged - layout is null");
            return;
        }
        DisplayLayout displayLayout2 = this.mPipDisplayLayoutState.getDisplayLayout();
        int i = displayLayout2.mWidth;
        int i2 = displayLayout.mWidth;
        PipTransitionController pipTransitionController = this.mPipTransitionController;
        if (i == i2 && displayLayout2.mHeight == displayLayout.mHeight && displayLayout2.mRotation == displayLayout.mRotation && displayLayout2.mDensityDpi == displayLayout.mDensityDpi && Objects.equals(displayLayout2.mCutout, displayLayout.mCutout)) {
            if (z) {
                pipTransitionController.getClass();
                return;
            }
            return;
        }
        pipTransitionController.getClass();
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = this.mPipAnimationController.mCurrentAnimator;
        if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning()) {
            pipTransitionAnimator.cancel();
        }
        this.mMenuController.hideMenu();
        onDisplayChangedUncheck(displayLayout, z);
    }

    public final void onDisplayChangedUncheck(DisplayLayout displayLayout, boolean z) throws Resources.NotFoundException {
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (pipTransitionState.mInSwipePipToHomeTransition) {
            return;
        }
        PipController$$ExternalSyntheticLambda4 pipController$$ExternalSyntheticLambda4 = new PipController$$ExternalSyntheticLambda4(0, this, displayLayout);
        if (!pipTransitionState.hasEnteredPip() || !z) {
            pipController$$ExternalSyntheticLambda4.run();
            return;
        }
        PhonePipMenuController phonePipMenuController = this.mMenuController;
        phonePipMenuController.attachPipMenuView();
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect rect = new Rect(pipBoundsState.getBounds());
        float snapFraction = pipSnapAlgorithm.getSnapFraction(pipBoundsState.mStashedState, rect, pipBoundsAlgorithm.getMovementBounds(rect, true));
        pipController$$ExternalSyntheticLambda4.run();
        rect.set(0, 0, Math.round(pipBoundsState.mMaxSize.x * pipBoundsState.mBoundsScale), Math.round(pipBoundsState.mMaxSize.y * pipBoundsState.mBoundsScale));
        Rect movementBounds = pipBoundsAlgorithm.getMovementBounds(rect, false);
        int i = pipBoundsState.mStashedState;
        int i2 = pipBoundsState.mStashOffset;
        PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
        PipSnapAlgorithm.applySnapFraction(rect, movementBounds, snapFraction, i, i2, pipDisplayLayoutState.getDisplayBounds(), pipBoundsState.getStashInsets());
        pipBoundsState.setHasUserResizedPip();
        this.mTouchHandler.mPipResizeGestureHandler.setUserResizeBounds(rect);
        int i3 = pipDisplayLayoutState.getDisplayLayout().mDensityDpi;
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (i3 == 0 || pipDisplayLayoutState.getDisplayLayout().mDensityDpi == displayLayout.mDensityDpi) {
            pipTaskOrganizer.scheduleFinishResizePip(rect, 0, null);
        } else {
            pipTaskOrganizer.scheduleAnimateResizePip(this.mContext.getResources().getInteger(R.integer.config_pipEnterAnimationDuration), 0, rect);
        }
        phonePipMenuController.setSplitMenuEnabled(pipTaskOrganizer.shouldShowSplitMenu());
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardDismissAnimationFinished() {
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (pipTaskOrganizer.isInPip()) {
            this.mIsKeyguardShowingOrAnimating = false;
            if (pipTaskOrganizer.mWaitForFixedRotation) {
                Log.d("PipController", "mWaitForFixedRotation skip setPipVisibility");
                return;
            } else {
                pipTaskOrganizer.setPipVisibility(true);
                return;
            }
        }
        if (this.mIsKeyguardShowingOrAnimating) {
            Log.w("PipController", "onKeyguardDismissAnimationFinished: release keyguard state, " + this.mPipTransitionState);
            this.mIsKeyguardShowingOrAnimating = false;
        }
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3) {
        if (this.mPipTransitionState.hasEnteredPip()) {
            PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
            if (!z) {
                if (z3) {
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
                this.mTouchHandler.mMotionHelper.expandLeavePip$1(false, false);
            }
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
        HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
        handlerExecutor.executeDelayed(this.mEnableTouchCallback, 200L);
        PipTouchHandler pipTouchHandler = this.mTouchHandler;
        pipTouchHandler.mMotionHelper.synchronizePinnedStackBounds();
        pipTouchHandler.updateMovementBounds();
        if (i == 2) {
            pipTouchHandler.mPipResizeGestureHandler.setUserResizeBounds(pipTouchHandler.mPipBoundsState.getBounds());
        }
        if (i == 2) {
            handlerExecutor.executeDelayed(new PipController$$ExternalSyntheticLambda3(this, 4), 150L);
        }
    }

    @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
    public final void onPipTransitionStarted(int i, Rect rect) {
        String str;
        InteractionJankMonitor.Configuration.Builder builderWithSurface = InteractionJankMonitor.Configuration.Builder.withSurface(35, this.mContext, this.mPipTaskOrganizer.mLeash, this.mHandler);
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
        InteractionJankMonitor.getInstance().begin(builderWithSurface.setTag(str).setTimeout(DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY));
        if (PipAnimationController.isOutPipDirection(i)) {
            this.mPipBoundsState.saveReentryState(this.mPipBoundsAlgorithm.getSnapFraction(rect));
        }
        ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mEnableTouchCallback);
        PipTouchState pipTouchState = this.mTouchHandler.mTouchState;
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
    public final void onThemeChanged() throws Resources.NotFoundException {
        this.mTouchHandler.mPipDismissTargetHandler.getClass();
        Context context = this.mContext;
        onDisplayChanged(new DisplayLayout(context, context.getDisplay()), false);
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onUserChanged(int i, Context context) {
        PipMediaController pipMediaController = this.mMediaController;
        MediaSessionManager mediaSessionManager = pipMediaController.mMediaSessionManager;
        mediaSessionManager.getClass();
        PipMediaController$mSessionsChangedListener$1 pipMediaController$mSessionsChangedListener$1 = pipMediaController.mSessionsChangedListener;
        mediaSessionManager.removeOnActiveSessionsChangedListener(pipMediaController$mSessionsChangedListener$1);
        pipMediaController.mMediaSessionManager.addOnActiveSessionsChangedListener(null, UserHandle.CURRENT, pipMediaController.mHandlerExecutor, pipMediaController$mSessionsChangedListener$1);
    }

    public final void setLauncherKeepClearAreaHeight(int i, boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -2945141395292145768L, 7, Boolean.valueOf(z), Long.valueOf(i));
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (z) {
            int i2 = pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().bottom - i;
            PipDisplayLayoutState pipDisplayLayoutState = pipBoundsState.mPipDisplayLayoutState;
            pipBoundsState.setNamedUnrestrictedKeepClearArea(0, new Rect(0, i2, pipDisplayLayoutState.getDisplayBounds().right, pipDisplayLayoutState.getDisplayBounds().bottom));
            updatePipPositionForKeepClearAreas();
            return;
        }
        pipBoundsState.setNamedUnrestrictedKeepClearArea(0, null);
        HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
        PipController$$ExternalSyntheticLambda3 pipController$$ExternalSyntheticLambda3 = this.mMovePipInResponseToKeepClearAreasChangeCallback;
        handlerExecutor.removeCallbacks(pipController$$ExternalSyntheticLambda3);
        handlerExecutor.executeDelayed(pipController$$ExternalSyntheticLambda3, PIP_KEEP_CLEAR_AREAS_DELAY);
    }

    public void setPinnedStackAnimationListener(PipAnimationListener pipAnimationListener) {
        this.mPinnedStackAnimationRecentsCallback = pipAnimationListener;
        if (pipAnimationListener != null) {
            pipAnimationListener.onPipResourceDimensionsChanged(this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius), this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius));
        }
    }

    public final void updateMovementBounds(Rect rect, boolean z, boolean z2, boolean z3, WindowContainerTransaction windowContainerTransaction) {
        int transitionDirection;
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        int i = pipTaskOrganizer.mPipTransitionState.mState;
        if (z3 && (i == 2 || i == 3)) {
            return;
        }
        Rect rect2 = new Rect(rect);
        int i2 = this.mPipDisplayLayoutState.getDisplayLayout().mRotation;
        Rect rect3 = this.mTmpInsetBounds;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        pipBoundsAlgorithm.getInsetBounds(rect3);
        int i3 = 0;
        Rect rectTransformBoundsToAspectRatioIfValid = pipBoundsAlgorithm.transformBoundsToAspectRatioIfValid(pipBoundsAlgorithm.mPipBoundsState.mAspectRatio, pipBoundsAlgorithm.getDefaultBounds(), false, false);
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.mNormalBounds.set(rectTransformBoundsToAspectRatioIfValid);
        if (rect2.isEmpty()) {
            rect2.set(pipBoundsAlgorithm.getDefaultBounds());
        }
        boolean z4 = pipTaskOrganizer.mWaitForFixedRotation;
        PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
        boolean z5 = z4 && pipTransitionState.mState != 4;
        boolean z6 = pipTransitionState.mInSwipePipToHomeTransition;
        if ((!z6 && !z5) || !z) {
            PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipTaskOrganizer.mPipAnimationController.mCurrentAnimator;
            PipBoundsState pipBoundsState2 = pipTaskOrganizer.mPipBoundsState;
            if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning() && pipTransitionAnimator.getTransitionDirection() == 2) {
                Rect rect4 = pipTransitionAnimator.mDestinationBounds;
                rect2.set(rect4);
                if (z2 || z3 || !pipBoundsState2.mPipDisplayLayoutState.getDisplayBounds().contains(rect4)) {
                    Rect entryDestinationBounds = pipTaskOrganizer.mPipBoundsAlgorithm.getEntryDestinationBounds();
                    if (!entryDestinationBounds.equals(rect4)) {
                        pipTaskOrganizer.updateAnimatorBounds(entryDestinationBounds);
                        rect2.set(entryDestinationBounds);
                    }
                }
            } else {
                boolean z7 = PipTransitionState.isInPip(pipTransitionState.mState) && z;
                if (z7 && Transitions.ENABLE_SHELL_TRANSITIONS) {
                    pipBoundsState2.setBounds(rect2);
                } else if (z7 && pipTaskOrganizer.mWaitForFixedRotation && pipTaskOrganizer.mHasFadeOut) {
                    pipBoundsState2.setBounds(rect2);
                } else if (z7) {
                    pipBoundsState2.setBounds(rect2);
                    if (pipTransitionAnimator != null) {
                        transitionDirection = pipTransitionAnimator.getTransitionDirection();
                        PipAnimationController.quietCancel(pipTransitionAnimator);
                        pipTaskOrganizer.mPipTransitionController.sendOnPipTransitionCancelled$1(transitionDirection);
                        pipTaskOrganizer.mTransitionDirection = transitionDirection;
                        pipTaskOrganizer.sendOnPipTransitionFinished(transitionDirection);
                    } else {
                        transitionDirection = 0;
                    }
                    pipTaskOrganizer.prepareFinishResizeTransaction(rect2, transitionDirection, pipTaskOrganizer.createFinishResizeSurfaceTransaction(rect2), windowContainerTransaction);
                } else if (pipTransitionAnimator == null || !pipTransitionAnimator.isRunning()) {
                    if (!pipBoundsState2.getBounds().isEmpty()) {
                        rect2.set(pipBoundsState2.getBounds());
                    }
                } else if (!pipTransitionAnimator.mDestinationBounds.isEmpty()) {
                    rect2.set(pipTransitionAnimator.mDestinationBounds);
                }
            }
        } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1955999652116970868L, 124, "PipTaskOrganizer", Boolean.valueOf(z6), Boolean.valueOf(z4), Long.valueOf(pipTransitionState.mState));
        }
        pipTaskOrganizer.finishResizeForMenu(rect2);
        Rect rect5 = this.mTmpInsetBounds;
        Rect rect6 = pipBoundsState.mNormalBounds;
        PipTouchHandler pipTouchHandler = this.mTouchHandler;
        if (pipTouchHandler.mPipResizeGestureHandler.mUserResizeBounds.isEmpty()) {
            pipTouchHandler.mPipResizeGestureHandler.setUserResizeBounds(rect6);
        }
        int i4 = pipTouchHandler.mIsImeShowing ? pipTouchHandler.mImeHeight : 0;
        if (pipTouchHandler.mDisplayRotation != i2) {
            pipTouchHandler.mTouchState.reset();
        }
        Rect rect7 = new Rect();
        pipTouchHandler.mPipBoundsAlgorithm.getClass();
        PipBoundsAlgorithm.getMovementBounds(rect6, rect5, rect7, i4);
        PipBoundsState pipBoundsState3 = pipTouchHandler.mPipBoundsState;
        if (pipBoundsState3.mMovementBounds.isEmpty()) {
            PipBoundsAlgorithm.getMovementBounds(rect2, rect5, pipBoundsState3.mMovementBounds, 0);
        }
        float fWidth = rect6.width() / rect6.height();
        Size defaultSize = ((PhoneSizeSpecSource) pipTouchHandler.mSizeSpecSource).getDefaultSize(fWidth);
        pipBoundsState3.mExpandedBounds.set(new Rect(0, 0, defaultSize.getWidth(), defaultSize.getHeight()));
        Rect rect8 = new Rect();
        PipBoundsAlgorithm.getMovementBounds(pipBoundsState3.mExpandedBounds, rect5, rect8, i4);
        pipTouchHandler.updatePipSizeConstraints(rect6, fWidth);
        boolean z8 = pipTouchHandler.mIsImeShowing;
        int i5 = z8 ? pipTouchHandler.mImeOffset : 0;
        if (!z8 && pipTouchHandler.mIsShelfShowing) {
            i3 = pipTouchHandler.mShelfHeight;
        }
        int iMax = Math.max(i5, i3);
        pipBoundsState3.mNormalMovementBounds.set(rect7);
        pipBoundsState3.mExpandedMovementBounds.set(rect8);
        pipTouchHandler.mDisplayRotation = i2;
        pipTouchHandler.mInsetBounds.set(rect5);
        pipTouchHandler.updateMovementBounds();
        pipTouchHandler.mMovementBoundsExtraOffsets = iMax;
        Rect rect9 = pipBoundsState3.mExpandedBounds;
        Rect rect10 = pipBoundsState3.mNormalMovementBounds;
        Rect rect11 = pipBoundsState3.mExpandedMovementBounds;
        PipAccessibilityInteractionConnection pipAccessibilityInteractionConnection = pipTouchHandler.mConnection;
        pipAccessibilityInteractionConnection.mNormalBounds.set(rect6);
        pipAccessibilityInteractionConnection.mExpandedBounds.set(rect9);
        pipAccessibilityInteractionConnection.mNormalMovementBounds.set(rect10);
        pipAccessibilityInteractionConnection.mExpandedMovementBounds.set(rect11);
        if (pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation == i2) {
            pipTouchHandler.mMotionHelper.animateToUnexpandedState(rect6, pipTouchHandler.mSavedSnapFraction, pipBoundsState3.mNormalMovementBounds, pipBoundsState3.mMovementBounds, true);
            pipTouchHandler.mSavedSnapFraction = -1.0f;
            pipTouchHandler.mDeferResizeToNormalBoundsUntilRotation = -1;
        }
    }

    public final void updatePipPositionForKeepClearAreas() throws Resources.NotFoundException {
        PipTransitionState pipTransitionState;
        int i;
        if (this.mIsKeyguardShowingOrAnimating || (i = (pipTransitionState = this.mPipTransitionState).mState) < 3 || i == 5) {
            return;
        }
        PhonePipKeepClearAlgorithm phonePipKeepClearAlgorithm = (PhonePipKeepClearAlgorithm) this.mPipKeepClearAlgorithm;
        phonePipKeepClearAlgorithm.getClass();
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        boolean zIsEmpty = pipBoundsState.getBounds().isEmpty();
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        Rect entryDestinationBoundsIgnoringKeepClearAreas = zIsEmpty ? pipBoundsAlgorithm.getEntryDestinationBoundsIgnoringKeepClearAreas() : pipBoundsState.getBounds();
        if (!pipBoundsState.mIsImeShowing && !pipBoundsState.mRestoreBounds.isEmpty()) {
            entryDestinationBoundsIgnoringKeepClearAreas.set(pipBoundsState.mRestoreBounds);
            pipBoundsState.mRestoreBounds.setEmpty();
        }
        Rect rect = new Rect();
        pipBoundsAlgorithm.getInsetBounds(rect);
        if (pipBoundsState.mIsImeShowing) {
            rect.bottom -= pipBoundsState.mImeHeight + phonePipKeepClearAlgorithm.mImeOffset;
        }
        if (pipBoundsState.isStashed()) {
            int i2 = entryDestinationBoundsIgnoringKeepClearAreas.bottom;
            int i3 = rect.bottom;
            if (i2 > i3 || entryDestinationBoundsIgnoringKeepClearAreas.top < rect.top) {
                entryDestinationBoundsIgnoringKeepClearAreas.offset(0, i3 - i2);
            }
        } else {
            Rect rect2 = new Rect(entryDestinationBoundsIgnoringKeepClearAreas);
            boolean z = (pipBoundsState.mHasUserMovedPip || pipBoundsState.mHasUserResizedPip) ? !rect.contains(rect2) : true;
            if (phonePipKeepClearAlgorithm.mKeepClearAreaGravityEnabled || z) {
                float snapFraction = pipBoundsAlgorithm.getSnapFraction(entryDestinationBoundsIgnoringKeepClearAreas);
                char c = (snapFraction < 0.5f || snapFraction >= 2.5f) ? (char) 3 : (char) 5;
                rect2.offsetTo(rect2.left, rect.bottom - rect2.height());
                if (c == 5) {
                    rect2.offsetTo(rect.right - rect2.width(), rect2.top);
                } else {
                    rect2.offsetTo(rect.left, rect2.top);
                }
            }
            entryDestinationBoundsIgnoringKeepClearAreas = phonePipKeepClearAlgorithm.findUnoccludedPosition(rect2, pipBoundsState.mRestrictedKeepClearAreas, pipBoundsState.getUnrestrictedKeepClearAreas(), rect);
        }
        if (entryDestinationBoundsIgnoringKeepClearAreas.equals(pipBoundsState.getBounds())) {
            return;
        }
        boolean zHasEnteredPip = pipTransitionState.hasEnteredPip();
        PipTaskOrganizer pipTaskOrganizer = this.mPipTaskOrganizer;
        if (zHasEnteredPip) {
            pipTaskOrganizer.scheduleAnimateResizePip(this.mEnterAnimationDuration, 0, entryDestinationBoundsIgnoringKeepClearAreas);
        } else if (pipTransitionState.mState == 3) {
            pipTaskOrganizer.updateAnimatorBounds(entryDestinationBoundsIgnoringKeepClearAreas);
        }
    }
}
