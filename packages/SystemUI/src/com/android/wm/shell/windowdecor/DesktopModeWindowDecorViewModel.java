package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;
import android.window.DesktopModeFlags;
import android.window.DisplayAreaInfo;
import android.window.RemoteTransition;
import android.window.TaskAppearedInfo;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.R;
import com.android.systemui.doze.AODUi$$ExternalSyntheticLambda0;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.apptoweb.AppToWebGenericLinksParser;
import com.android.wm.shell.apptoweb.AssistContentRequester;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorController;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.split.DividerHandleView;
import com.android.wm.shell.common.split.DividerRoundedCorner;
import com.android.wm.shell.compatui.api.CompatUIHandler;
import com.android.wm.shell.desktopmode.DesktopActivityOrientationChangeHandler;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUtils;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksLimiter;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import com.android.wm.shell.desktopmode.common.ToggleTaskSizeInteraction;
import com.android.wm.shell.desktopmode.common.ToggleTaskSizeUtilsKt$WhenMappings;
import com.android.wm.shell.desktopmode.education.AppHandleEducationController;
import com.android.wm.shell.desktopmode.education.AppToWebEducationController;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.freeform.FreeformAdjustImeController;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.recents.RecentsTransitionStateListener;
import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shortcut.ShortcutController;
import com.android.wm.shell.shortcut.ShortcutPolicyFor1to4Keys;
import com.android.wm.shell.shortcut.ShortcutPolicyForWKey;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.DragPositioningCallbackUtility;
import com.android.wm.shell.windowdecor.MultiTaskingCaptionButtonLogger;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.common.AppHandleAndHeaderVisibilityHelper;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.android.wm.shell.windowdecor.common.WindowDecorationGestureExclusionTracker;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.extension.InsetsStateKt;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDividerWindowManager;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.android.wm.shell.windowdecor.tiling.SnapEventHandler;
import com.android.wm.shell.windowdecor.tiling.TilingDividerView;
import com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder;
import com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder;
import com.android.wm.shell.windowdecor.widget.OutlineView;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DesktopModeWindowDecorViewModel implements WindowDecorViewModel, FocusTransitionListener, SnapEventHandler {
    public final Optional mActivityOrientationChangeHandler;
    public final ActivityTaskManager mActivityTaskManager;
    public final ShellExecutor mAnimExecutor;
    public final AppHandleAndHeaderVisibilityHelper mAppHandleAndHeaderVisibilityHelper;
    public final AppHandleViewHolder.Factory mAppHandleViewHolderFactory;
    public final AppHeaderViewHolder.Factory mAppHeaderViewHolderFactory;
    public final AssistContentRequester mAssistContentRequester;
    public final ShellExecutor mBgExecutor;
    public final CoroutineScope mBgScope;
    public final MultiTaskingCaptionButtonLogger mCaptionButtonLogger;
    public final CompatUIHandler mCompatUI;
    public final Context mContext;
    public final DesksOrganizer mDesksOrganizer;
    public final DesktopConfig mDesktopConfig;
    public final DesktopImmersiveController mDesktopImmersiveController;
    public final DesktopModeCompatPolicy mDesktopModeCompatPolicy;
    public final DesktopModeEventLogger mDesktopModeEventLogger;
    public final DesktopModeKeyguardChangeListener mDesktopModeKeyguardChangeListener;
    public final DesktopModeUiEventLogger mDesktopModeUiEventLogger;
    public final DesktopModeWindowDecoration.Factory mDesktopModeWindowDecorFactory;
    public final DesktopState mDesktopState;
    public final DesktopTasksController mDesktopTasksController;
    public final DesktopTilingDecorViewModel mDesktopTilingDecorViewModel;
    public final DesktopUserRepositories mDesktopUserRepositories;
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final DragEventListenerImpl mDragEventListener;
    public int mDragStartDisplayId;
    public final Rect mDragToDesktopAnimationStartBounds;
    public final SparseArray mEventReceiversByDisplay;
    public final ExclusionRegionListenerImpl mExclusionRegionListener;
    public final FocusTransitionObserver mFocusTransitionObserver;
    public final AppToWebGenericLinksParser mGenericLinksParser;
    public final WindowDecorationGestureExclusionTracker mGestureExclusionTracker;
    public HandleMenuHelpController mHandleMenuHelpController;
    public boolean mInImmersiveMode;
    public final InputManager mInputManager;
    public final InputMonitorFactory mInputMonitorFactory;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public boolean mIsKeyguardShowing;
    public final LatencyTracker mLatencyTracker;
    public final Choreographer mMainChoreographer;
    public final MainCoroutineDispatcher mMainDispatcher;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public MoveToDesktopAnimator mMoveToDesktopAnimator;
    public final MultiDisplayDragMoveIndicatorController mMultiDisplayDragMoveIndicatorController;
    public final MultiInstanceHelper mMultiInstanceHelper;
    public final MultiTaskingHeaderViewHolder.Factory mMultiTaskingHeaderViewHolderFactory;
    public final NaturalSwitchingDropTargetController mNSController;
    public final DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0 mOnDisplayChangingListener;
    public final RecentsTransitionHandler mRecentsTransitionHandler;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public final DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0 mRotationController;
    public final ShellCommandHandler mShellCommandHandler;
    public final ShellController mShellController;
    public SplitScreenController mSplitScreenController;
    public final SyncTransactionQueue mSyncQueue;
    public TaskOperations mTaskOperations;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final TaskPositionerFactory mTaskPositionerFactory;
    public final WindowDecorTaskResourceLoader mTaskResourceLoader;
    public final Supplier mTransactionFactory;
    public boolean mTransitionDragActive;
    public final Map mTransitionToTaskInfo;
    public final Transitions mTransitions;
    public final SparseArray mWindowDecorByTaskId;
    public final WindowDecorCaptionHandleRepository mWindowDecorCaptionHandleRepository;
    public final WindowDecorViewHostSupplier mWindowDecorViewHostSupplier;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DesktopModeKeyguardChangeListener implements KeyguardChangeListener {
        public DesktopModeKeyguardChangeListener() {
        }

        @Override // com.android.wm.shell.sysui.KeyguardChangeListener
        public final void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3) {
            boolean z4 = CoreRune.MW_CAPTION_KEYGUARD;
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
            if (!z4) {
                for (int size = desktopModeWindowDecorViewModel.mWindowDecorByTaskId.size() - 1; size >= 0; size--) {
                    DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.valueAt(size);
                    if (desktopModeWindowDecoration != null) {
                        desktopModeWindowDecoration.onKeyguardStateChanged(z, z2);
                    }
                }
                return;
            }
            if (desktopModeWindowDecorViewModel.mIsKeyguardShowing != z) {
                desktopModeWindowDecorViewModel.mIsKeyguardShowing = z;
                for (int size2 = desktopModeWindowDecorViewModel.mWindowDecorByTaskId.size() - 1; size2 >= 0; size2--) {
                    DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.valueAt(size2);
                    if (desktopModeWindowDecoration2 != null) {
                        desktopModeWindowDecoration2.onKeyguardStateChanged(z, z2);
                    }
                }
                if (desktopModeWindowDecorViewModel.mIsKeyguardShowing) {
                    return;
                }
                desktopModeWindowDecorViewModel.ensureHandlerOnTransitionFinished();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class DesktopModeOnInsetsChangedListener implements DisplayInsetsController.OnInsetsChangedListener {
        public DesktopModeOnInsetsChangedListener() {
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(int i, InsetsState insetsState) {
            DisplayLayout displayLayout;
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
            for (int size = desktopModeWindowDecorViewModel.mWindowDecorByTaskId.size() - 1; size >= 0; size--) {
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.valueAt(size);
                if (desktopModeWindowDecoration != null) {
                    if (desktopModeWindowDecoration.mTaskInfo.displayId == i && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING.isTrue()) {
                        if (!desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                            Insets displayInsetsState = desktopModeWindowDecoration.getDisplayInsetsState(WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout());
                            if (displayInsetsState != null && displayInsetsState.top != desktopModeWindowDecoration.mRelayoutParams.mDisplayTopInset) {
                                desktopModeWindowDecoration.relayout(desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mHasGlobalFocus, desktopModeWindowDecoration.mExclusionRegion);
                            }
                        } else if (CoreRune.MW_CAPTION_DESKTOP && desktopModeWindowDecoration.mInDesktopWindowing && desktopModeWindowDecoration.mIsTaskMaximized && (displayLayout = desktopModeWindowDecoration.mDisplayController.getDisplayLayout(desktopModeWindowDecoration.mTaskInfo.displayId)) != null) {
                            Rect calculateMaximizeBounds = DesktopModeUtils.calculateMaximizeBounds(displayLayout, desktopModeWindowDecoration.mTaskInfo);
                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                            windowContainerTransaction.setBounds(desktopModeWindowDecoration.mTaskInfo.token, calculateMaximizeBounds);
                            desktopModeWindowDecoration.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                        }
                        boolean z = desktopModeWindowDecoration.mIsStatusBarVisible;
                        boolean isVisible = InsetsStateKt.isVisible(WindowInsets.Type.statusBars(), insetsState);
                        desktopModeWindowDecoration.mIsStatusBarVisible = isVisible;
                        if (z != isVisible) {
                            desktopModeWindowDecoration.relayout(desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mHasGlobalFocus, desktopModeWindowDecoration.mExclusionRegion);
                        }
                    }
                    if (!DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
                        desktopModeWindowDecorViewModel.mInImmersiveMode = !InsetsStateKt.isVisible(WindowInsets.Type.statusBars(), insetsState);
                    }
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DesktopModeOnTaskRepositionAnimationListener {
        public /* synthetic */ DesktopModeOnTaskRepositionAnimationListener(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i) {
            this();
        }

        private DesktopModeOnTaskRepositionAnimationListener() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DesktopModeOnTaskResizeAnimationListener {
        public /* synthetic */ DesktopModeOnTaskResizeAnimationListener(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i) {
            this();
        }

        public final void onAnimationEnd(int i) {
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(i);
            if (desktopModeWindowDecoration == null) {
                return;
            }
            desktopModeWindowDecoration.mResizeVeil.hideVeil();
            desktopModeWindowDecoration.setAnimatingTaskResizeOrReposition(false);
        }

        public final void onAnimationStart(int i, SurfaceControl.Transaction transaction, Rect rect) {
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(i);
            if (desktopModeWindowDecoration == null) {
                transaction.apply();
                return;
            }
            if (desktopModeWindowDecoration.mResizeVeil == null) {
                desktopModeWindowDecoration.mResizeVeil = new ResizeVeil(desktopModeWindowDecoration.mContext, desktopModeWindowDecoration.mDisplayController, desktopModeWindowDecoration.mTaskResourceLoader, desktopModeWindowDecoration.mMainDispatcher, desktopModeWindowDecoration.mBgScope, desktopModeWindowDecoration.mTaskSurface, desktopModeWindowDecoration.mSurfaceControlTransactionSupplier, desktopModeWindowDecoration.mTaskInfo);
            }
            desktopModeWindowDecoration.mResizeVeil.showVeil(transaction, desktopModeWindowDecoration.mTaskSurface, rect, desktopModeWindowDecoration.mTaskInfo, false);
            desktopModeWindowDecoration.setAnimatingTaskResizeOrReposition(true);
        }

        public final void onBoundsChange(int i, SurfaceControl.Transaction transaction, Rect rect) {
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(i);
            if (desktopModeWindowDecoration == null) {
                return;
            }
            ResizeVeil resizeVeil = desktopModeWindowDecoration.mResizeVeil;
            if (resizeVeil.isVisible) {
                ValueAnimator valueAnimator = resizeVeil.veilAnimator;
                if (valueAnimator != null && valueAnimator.isStarted()) {
                    valueAnimator.removeAllUpdateListeners();
                    valueAnimator.end();
                }
                resizeVeil.relayout(rect, transaction);
            }
            transaction.apply();
        }

        private DesktopModeOnTaskResizeAnimationListener() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DesktopModeRecentsTransitionStateListener implements RecentsTransitionStateListener {
        public final Set mAnimatingTaskIds;

        public /* synthetic */ DesktopModeRecentsTransitionStateListener(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i) {
            this();
        }

        @Override // com.android.wm.shell.recents.RecentsTransitionStateListener
        public final void onTransitionStateChanged(int i) {
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
            if (i == 1) {
                Iterator it = ((HashSet) this.mAnimatingTaskIds).iterator();
                while (it.hasNext()) {
                    DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(((Integer) it.next()).intValue());
                    if (desktopModeWindowDecoration != null) {
                        desktopModeWindowDecoration.mIsRecentsTransitionRunning = false;
                    }
                }
                ((HashSet) this.mAnimatingTaskIds).clear();
                return;
            }
            if (i != 2) {
                return;
            }
            for (int i2 = 0; i2 < desktopModeWindowDecorViewModel.mWindowDecorByTaskId.size(); i2++) {
                int keyAt = desktopModeWindowDecorViewModel.mWindowDecorByTaskId.keyAt(i2);
                ((HashSet) this.mAnimatingTaskIds).add(Integer.valueOf(keyAt));
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(keyAt);
                if (desktopModeWindowDecoration2 != null) {
                    desktopModeWindowDecoration2.mIsRecentsTransitionRunning = true;
                }
            }
        }

        private DesktopModeRecentsTransitionStateListener() {
            this.mAnimatingTaskIds = new HashSet();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DesktopModeTouchEventListener extends GestureDetector.SimpleOnGestureListener implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener, View.OnGenericMotionListener, DragDetector.MotionEventHandler {
        public final AccessibilityManager mAccessibilityManager;
        public final DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda2 mDismissRunnable;
        public final int mDisplayId;
        public boolean mDragInterrupted;
        public int mDragPointerId;
        public final DragPositioningCallback mDragPositioningCallback;
        public final FreeformCaptionTouchState mFreeformCaptionTouchState;
        public final GestureDetector mGestureDetector;
        public final DragDetector mHandleDragDetector;
        public final DragDetector mHeaderDragDetector;
        public boolean mIsButtonLongPressed;
        public boolean mIsCustomHeaderGesture;
        public boolean mIsDragging;
        public boolean mIsNaturalSwitching;
        public boolean mIsResizeGesture;
        public boolean mLongClickDisabled;
        public MotionEvent mMotionEvent;
        public final Rect mOnDragStartInitialBounds;
        public View mTargetView;
        public final int mTaskId;
        public final TaskPositioner mTaskPositioner;
        public final WindowContainerToken mTaskToken;
        public Point touchedPosition;

        public /* synthetic */ DesktopModeTouchEventListener(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, ActivityManager.RunningTaskInfo runningTaskInfo, TaskPositioner taskPositioner, TaskPositioner taskPositioner2) {
            this(runningTaskInfo, (DragPositioningCallback) taskPositioner, taskPositioner2);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x008b, code lost:
        
            if ((r25 instanceof com.android.wm.shell.windowdecor.widget.CaptionAnimationButton) == false) goto L37;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x00c4, code lost:
        
            if (r10 != 3) goto L106;
         */
        /* JADX WARN: Removed duplicated region for block: B:136:0x03b3  */
        /* JADX WARN: Removed duplicated region for block: B:166:0x03d9  */
        /* JADX WARN: Removed duplicated region for block: B:47:0x00be  */
        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean handleMotionEvent(android.view.View r25, android.view.MotionEvent r26) {
            /*
                Method dump skipped, instructions count: 1290
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener.handleMotionEvent(android.view.View, android.view.MotionEvent):boolean");
        }

        public final void moveTaskToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
            if (CoreRune.MW_CAPTION && runningTaskInfo.isFocused && DesktopModeWindowDecorViewModel.this.mFocusTransitionObserver.hasGlobalFocus(runningTaskInfo)) {
                return;
            }
            if (CoreRune.MW_CAPTION_DESKTOP) {
                int i = runningTaskInfo.displayId;
                DesktopStateImpl.Companion.getClass();
                if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    windowContainerTransaction.reorder(runningTaskInfo.token, true);
                    DesktopModeWindowDecorViewModel.this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                    return;
                }
            }
            DesktopModeWindowDecorViewModel.this.mDesktopModeUiEventLogger.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_HEADER_TAP_TO_REFOCUS);
            DesktopTasksController desktopTasksController = DesktopModeWindowDecorViewModel.this.mDesktopTasksController;
            desktopTasksController.getClass();
            desktopTasksController.moveTaskToFront(runningTaskInfo, (RemoteTransition) null, DesktopModeEventLogger.Companion.UnminimizeReason.UNKNOWN);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:47:0x053b A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:51:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
        /* JADX WARN: Type inference failed for: r3v0 */
        /* JADX WARN: Type inference failed for: r3v1 */
        /* JADX WARN: Type inference failed for: r3v10 */
        /* JADX WARN: Type inference failed for: r3v12 */
        /* JADX WARN: Type inference failed for: r3v13 */
        /* JADX WARN: Type inference failed for: r3v14 */
        /* JADX WARN: Type inference failed for: r3v15 */
        /* JADX WARN: Type inference failed for: r3v16 */
        /* JADX WARN: Type inference failed for: r3v18, types: [boolean] */
        /* JADX WARN: Type inference failed for: r3v2 */
        /* JADX WARN: Type inference failed for: r3v22 */
        /* JADX WARN: Type inference failed for: r3v23 */
        /* JADX WARN: Type inference failed for: r3v24 */
        /* JADX WARN: Type inference failed for: r3v25 */
        /* JADX WARN: Type inference failed for: r3v26 */
        /* JADX WARN: Type inference failed for: r3v27 */
        /* JADX WARN: Type inference failed for: r3v28 */
        /* JADX WARN: Type inference failed for: r3v29 */
        /* JADX WARN: Type inference failed for: r3v3 */
        /* JADX WARN: Type inference failed for: r3v30 */
        /* JADX WARN: Type inference failed for: r3v31 */
        /* JADX WARN: Type inference failed for: r3v32 */
        /* JADX WARN: Type inference failed for: r3v33 */
        /* JADX WARN: Type inference failed for: r3v34 */
        /* JADX WARN: Type inference failed for: r3v35 */
        /* JADX WARN: Type inference failed for: r3v36 */
        /* JADX WARN: Type inference failed for: r3v37 */
        /* JADX WARN: Type inference failed for: r3v38 */
        /* JADX WARN: Type inference failed for: r3v39 */
        /* JADX WARN: Type inference failed for: r3v4 */
        /* JADX WARN: Type inference failed for: r3v40 */
        /* JADX WARN: Type inference failed for: r3v41 */
        /* JADX WARN: Type inference failed for: r3v5 */
        /* JADX WARN: Type inference failed for: r3v6 */
        /* JADX WARN: Type inference failed for: r3v7 */
        /* JADX WARN: Type inference failed for: r3v8 */
        /* JADX WARN: Type inference failed for: r3v9 */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onClick(android.view.View r23) {
            /*
                Method dump skipped, instructions count: 1366
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener.onClick(android.view.View):void");
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (this.mIsDragging) {
                return false;
            }
            if ((actionMasked != 1 && actionMasked != 3) || DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getCurrent().isTaskInFullImmersiveState(this.mTaskId)) {
                return false;
            }
            if (CoreRune.MW_CAPTION) {
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
                if (desktopModeWindowDecoration != null && desktopModeWindowDecoration.isDecorHandleState() && desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                    return false;
                }
                if (CoreRune.MW_SA_LOGGING && desktopModeWindowDecoration != null) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                    if (runningTaskInfo.topActivity != null) {
                        MultiTaskingCaptionButtonLogger multiTaskingCaptionButtonLogger = DesktopModeWindowDecorViewModel.this.mCaptionButtonLogger;
                        int windowingMode = runningTaskInfo.getWindowingMode();
                        String packageName = desktopModeWindowDecoration.mTaskInfo.topActivity.getPackageName();
                        multiTaskingCaptionButtonLogger.getClass();
                        multiTaskingCaptionButtonLogger.invokeLog((Method) MultiTaskingCaptionButtonLogger.sLoggerMethods.get(new MultiTaskingCaptionButtonLogger.CaptionLoggerPairKey(multiTaskingCaptionButtonLogger, 0, 3, true, windowingMode)), packageName);
                    }
                }
            }
            DesktopModeWindowDecorViewModel.this.onToggleSizeInteraction(this.mTaskId, ToggleTaskSizeInteraction.AmbiguousSource.DOUBLE_TAP, motionEvent);
            return true;
        }

        @Override // android.view.View.OnGenericMotionListener
        public final boolean onGenericMotion(View view, MotionEvent motionEvent) {
            this.mMotionEvent = motionEvent;
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            int id = view.getId();
            if (motionEvent.getAction() == 9 && id == R.id.maximize_window) {
                desktopModeWindowDecoration.mIsAppHeaderMaximizeButtonHovered = true;
                desktopModeWindowDecoration.onMaximizeHoverStateChanged();
                if (!desktopModeWindowDecoration.isMaximizeMenuActive() && !CoreRune.MW_CAPTION) {
                    final MaximizeButtonView maximizeButtonView = DesktopModeWindowDecoration.asAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder).maximizeButtonView;
                    if (!maximizeButtonView.hoverDisabled) {
                        if (maximizeButtonView.hoverProgressAnimatorSet.isRunning()) {
                            maximizeButtonView.cancelHoverAnimation();
                        }
                        maximizeButtonView.maximizeWindow.getBackground().setAlpha(0);
                        AnimatorSet animatorSet = maximizeButtonView.hoverProgressAnimatorSet;
                        final ValueAnimator duration = ValueAnimator.ofInt(0, 255).setDuration(50L);
                        duration.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.windowdecor.MaximizeButtonView$startHoverAnimation$1$1
                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                MaximizeButtonView.this.maximizeWindow.getBackground().setAlpha(((Integer) duration.getAnimatedValue()).intValue());
                            }
                        });
                        Unit unit = Unit.INSTANCE;
                        ObjectAnimator duration2 = ObjectAnimator.ofInt(maximizeButtonView.getProgressBar(), "progress", 100).setDuration(350L);
                        duration2.getClass();
                        duration2.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.MaximizeButtonView$startHoverAnimation$lambda$4$$inlined$doOnStart$1
                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                MaximizeButtonView maximizeButtonView2 = MaximizeButtonView.this;
                                int i = MaximizeButtonView.$r8$clinit;
                                maximizeButtonView2.getProgressBar().setProgress(0, false);
                                MaximizeButtonView.this.getProgressBar().setVisibility(0);
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationRepeat(Animator animator) {
                            }
                        });
                        duration2.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.MaximizeButtonView$startHoverAnimation$lambda$4$$inlined$doOnEnd$1
                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                MaximizeButtonView maximizeButtonView2 = MaximizeButtonView.this;
                                int i = MaximizeButtonView.$r8$clinit;
                                maximizeButtonView2.getProgressBar().setVisibility(4);
                                Function0 function0 = MaximizeButtonView.this.onHoverAnimationFinishedListener;
                                if (function0 == null) {
                                    function0 = null;
                                }
                                function0.invoke();
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationCancel(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationRepeat(Animator animator) {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                            }
                        });
                        animatorSet.playSequentially(duration, duration2);
                        maximizeButtonView.hoverProgressAnimatorSet.start();
                        return true;
                    }
                }
            } else {
                if (motionEvent.getAction() != 10 || id != R.id.maximize_window) {
                    return false;
                }
                desktopModeWindowDecoration.mIsAppHeaderMaximizeButtonHovered = false;
                desktopModeWindowDecoration.onMaximizeHoverStateChanged();
                desktopModeWindowDecoration.onMaximizeHoverStateChanged();
                if (!desktopModeWindowDecoration.isMaximizeMenuActive() && !CoreRune.MW_CAPTION) {
                    DesktopModeWindowDecoration.asAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder).maximizeButtonView.cancelHoverAnimation();
                }
            }
            return true;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(View view) {
            DesktopModeWindowDecoration desktopModeWindowDecoration;
            int id = view.getId();
            if (((CoreRune.MW_CAPTION_DESKTOP || id != R.id.maximize_window) && id != R.id.toggle_freeform_window) || this.mLongClickDisabled || (desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId)) == null) {
                return false;
            }
            moveTaskToFront(desktopModeWindowDecoration.mTaskInfo);
            if (!desktopModeWindowDecoration.mIsTaskMaximized) {
                return false;
            }
            if (desktopModeWindowDecoration.isMaximizeMenuActive()) {
                desktopModeWindowDecoration.closeMaximizeMenu();
                return true;
            }
            DesktopModeWindowDecorViewModel.this.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_REVEAL_MENU);
            desktopModeWindowDecoration.createMaximizeMenu();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
            if (this.mTargetView != null) {
                this.mIsButtonLongPressed = true;
            }
            super.onLongPress(motionEvent);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:107:0x01f3 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:150:0x029d  */
        /* JADX WARN: Removed duplicated region for block: B:166:0x02d1  */
        /* JADX WARN: Removed duplicated region for block: B:193:0x01fd  */
        /* JADX WARN: Removed duplicated region for block: B:195:0x00e6  */
        /* JADX WARN: Removed duplicated region for block: B:196:0x00c7  */
        /* JADX WARN: Removed duplicated region for block: B:54:0x00c2  */
        /* JADX WARN: Removed duplicated region for block: B:57:0x00e4  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x00f1  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x0180  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0187  */
        /* JADX WARN: Removed duplicated region for block: B:96:0x01d8  */
        /* JADX WARN: Type inference failed for: r10v1 */
        /* JADX WARN: Type inference failed for: r10v2 */
        /* JADX WARN: Type inference failed for: r10v3 */
        /* JADX WARN: Type inference failed for: r11v1 */
        /* JADX WARN: Type inference failed for: r11v2 */
        /* JADX WARN: Type inference failed for: r11v7 */
        /* JADX WARN: Type inference failed for: r13v1 */
        /* JADX WARN: Type inference failed for: r13v2 */
        /* JADX WARN: Type inference failed for: r13v5 */
        /* JADX WARN: Type inference failed for: r15v0 */
        /* JADX WARN: Type inference failed for: r15v1 */
        /* JADX WARN: Type inference failed for: r15v10 */
        /* JADX WARN: Type inference failed for: r5v3 */
        /* JADX WARN: Type inference failed for: r5v4 */
        /* JADX WARN: Type inference failed for: r5v67 */
        @Override // android.view.View.OnTouchListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onTouch(android.view.View r22, android.view.MotionEvent r23) {
            /*
                Method dump skipped, instructions count: 769
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel.DesktopModeTouchEventListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
        }

        public final void schedulePopupDismiss() {
            DesktopModeWindowDecorViewModel.this.mMainHandler.removeCallbacks(this.mDismissRunnable);
            if (this.mAccessibilityManager.semIsAccessibilityServiceEnabled(114)) {
                return;
            }
            DesktopModeWindowDecorViewModel.this.mMainHandler.postDelayed(this.mDismissRunnable, 3000L);
        }

        public final void sendTalkBackFeedback(int i) {
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
                return;
            }
            AccessibilityEvent obtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            String string = DesktopModeWindowDecorViewModel.this.mContext.getString(i);
            if (string.isEmpty()) {
                return;
            }
            obtain.getText().clear();
            obtain.getText().add(string);
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }

        public final void unschedulePopupDismiss() {
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (desktopModeWindowDecoration != null && desktopModeWindowDecoration.isHandleMenuActive()) {
                DesktopModeWindowDecorViewModel.this.mMainHandler.removeCallbacks(this.mDismissRunnable);
            }
        }

        public final void updateDragStatus(DesktopModeWindowDecoration desktopModeWindowDecoration, MotionEvent motionEvent) {
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0 && actionMasked != 1) {
                if (actionMasked == 2) {
                    if (this.mDragInterrupted) {
                        return;
                    }
                    this.mIsDragging = true;
                    desktopModeWindowDecoration.mIsDragging = true;
                    return;
                }
                if (actionMasked != 3) {
                    return;
                }
            }
            this.mDragInterrupted = false;
            this.mIsDragging = false;
            desktopModeWindowDecoration.mIsDragging = false;
        }

        private DesktopModeTouchEventListener(ActivityManager.RunningTaskInfo runningTaskInfo, DragPositioningCallback dragPositioningCallback, TaskPositioner taskPositioner) {
            this.mOnDragStartInitialBounds = new Rect();
            this.mDragPointerId = -1;
            this.mDismissRunnable = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda2(this, 1);
            this.mTaskId = runningTaskInfo.taskId;
            this.mTaskToken = runningTaskInfo.token;
            this.mDragPositioningCallback = dragPositioningCallback;
            int scaledTouchSlop = ViewConfiguration.get(DesktopModeWindowDecorViewModel.this.mContext).getScaledTouchSlop();
            this.mHandleDragDetector = new DragDetector(this, (!DesktopModeFlags.ENABLE_HOLD_TO_DRAG_APP_HANDLE.isTrue() || CoreRune.MW_CAPTION_HANDLE) ? 0L : 100L, scaledTouchSlop);
            this.mHeaderDragDetector = new DragDetector(this, 0L, scaledTouchSlop);
            this.mGestureDetector = new GestureDetector(DesktopModeWindowDecorViewModel.this.mContext, this);
            this.mDisplayId = runningTaskInfo.displayId;
            this.mAccessibilityManager = AccessibilityManager.getInstance(DesktopModeWindowDecorViewModel.this.mContext);
            this.mFreeformCaptionTouchState = new FreeformCaptionTouchState(ViewConfiguration.get(DesktopModeWindowDecorViewModel.this.mContext));
            this.mTaskPositioner = taskPositioner;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DragEventListenerImpl implements DragPositioningCallbackUtility.DragEventListener {
        public /* synthetic */ DragEventListenerImpl(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i) {
            this();
        }

        @Override // com.android.wm.shell.windowdecor.DragPositioningCallbackUtility.DragEventListener
        public final void onDragStart(int i) {
            ((DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(i)).closeHandleMenu();
        }

        private DragEventListenerImpl() {
        }

        @Override // com.android.wm.shell.windowdecor.DragPositioningCallbackUtility.DragEventListener
        public final void onDragMove(int i) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class EventReceiver extends InputEventReceiver {
        public InputMonitor mInputMonitor;
        public int mTasksOnDisplay;

        public EventReceiver(InputMonitor inputMonitor, InputChannel inputChannel, Looper looper) {
            super(inputChannel, looper);
            this.mInputMonitor = inputMonitor;
            this.mTasksOnDisplay = 1;
        }

        public final void dispose() {
            InputMonitor inputMonitor = this.mInputMonitor;
            if (inputMonitor != null) {
                inputMonitor.dispose();
                this.mInputMonitor = null;
            }
            super.dispose();
        }

        public final void onInputEvent(InputEvent inputEvent) {
            DesktopModeWindowDecoration focusedDecor;
            boolean z = false;
            if (inputEvent instanceof MotionEvent) {
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                InputMonitor inputMonitor = this.mInputMonitor;
                DragToDesktopTransitionHandler.TransitionState transitionState = desktopModeWindowDecorViewModel.mDesktopTasksController.dragToDesktopTransitionHandler.transitionState;
                int draggedTaskId = transitionState != null ? transitionState.getDraggedTaskId() : -1;
                if (draggedTaskId != -1) {
                    focusedDecor = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(draggedTaskId);
                } else {
                    DesktopModeWindowDecoration focusedDecor2 = desktopModeWindowDecorViewModel.getFocusedDecor();
                    if (focusedDecor2 == null) {
                        focusedDecor = null;
                    } else {
                        SplitScreenController splitScreenController = desktopModeWindowDecorViewModel.mSplitScreenController;
                        boolean z2 = splitScreenController != null && splitScreenController.isSplitScreenVisible();
                        SplitScreenController splitScreenController2 = desktopModeWindowDecorViewModel.mSplitScreenController;
                        boolean z3 = splitScreenController2 != null && splitScreenController2.isTaskInSplitScreen$1(focusedDecor2.mTaskInfo.taskId);
                        if (z2 && z3) {
                            ActivityManager.RunningTaskInfo taskInfo = desktopModeWindowDecorViewModel.mSplitScreenController.getTaskInfo(0);
                            ActivityManager.RunningTaskInfo taskInfo2 = desktopModeWindowDecorViewModel.mSplitScreenController.getTaskInfo(1);
                            if (taskInfo != null && taskInfo.getConfiguration().windowConfiguration.getBounds().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                                focusedDecor = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(taskInfo.taskId);
                            } else if (taskInfo2 == null || !taskInfo2.getConfiguration().windowConfiguration.getBounds().contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                                focusedDecor = null;
                            } else {
                                Rect bounds = taskInfo2.getConfiguration().windowConfiguration.getBounds();
                                motionEvent.offsetLocation(-bounds.left, -bounds.top);
                                focusedDecor = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(taskInfo2.taskId);
                            }
                            if (focusedDecor == null) {
                                focusedDecor = desktopModeWindowDecorViewModel.getFocusedDecor();
                            }
                        } else {
                            focusedDecor = desktopModeWindowDecorViewModel.getFocusedDecor();
                        }
                    }
                }
                DesktopStateImpl desktopStateImpl = (DesktopStateImpl) desktopModeWindowDecorViewModel.mDesktopState;
                if (desktopStateImpl.canEnterDesktopMode && !desktopModeWindowDecorViewModel.mInImmersiveMode && ((focusedDecor == null || focusedDecor.mTaskInfo.getWindowingMode() != 5 || desktopModeWindowDecorViewModel.mTransitionDragActive) && (!CoreRune.MW_CAPTION_HANDLE || (focusedDecor != null && focusedDecor.supportStatusBarInputLayer())))) {
                    desktopModeWindowDecorViewModel.handleCaptionThroughStatusBar(motionEvent, focusedDecor, new AODUi$$ExternalSyntheticLambda0());
                }
                if (focusedDecor != null && !focusedDecor.checkTouchEventInCaption(motionEvent)) {
                    focusedDecor.updateHoverAndPressStatus(motionEvent);
                    int actionMasked = motionEvent.getActionMasked();
                    if ((actionMasked == 1 || actionMasked == 3) && !desktopModeWindowDecorViewModel.mTransitionDragActive && !DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
                        focusedDecor.closeHandleMenuIfNeeded(motionEvent);
                    }
                }
                if (desktopStateImpl.canEnterDesktopMode && desktopModeWindowDecorViewModel.mTransitionDragActive) {
                    inputMonitor.pilferPointers();
                }
                z = true;
            }
            finishInputEvent(inputEvent, z);
        }

        public final String toString() {
            return ReorderTile$$ExternalSyntheticOutline0.m(this.mTasksOnDisplay, "}", new StringBuilder("EventReceiver{tasksOnDisplay="));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ExclusionRegionListenerImpl {
        public /* synthetic */ ExclusionRegionListenerImpl(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i) {
            this();
        }

        private ExclusionRegionListenerImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class InputMonitorFactory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    class TaskPositionerFactory {
    }

    public DesktopModeWindowDecorViewModel(Context context, ShellExecutor shellExecutor, Handler handler, Choreographer choreographer, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, ShellInit shellInit, ShellCommandHandler shellCommandHandler, IWindowManager iWindowManager, ShellTaskOrganizer shellTaskOrganizer, DesktopUserRepositories desktopUserRepositories, DisplayController displayController, ShellController shellController, DisplayInsetsController displayInsetsController, SyncTransactionQueue syncTransactionQueue, Transitions transitions, Optional<DesktopTasksController> optional, DesktopImmersiveController desktopImmersiveController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, InteractionJankMonitor interactionJankMonitor, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MultiInstanceHelper multiInstanceHelper, Optional<DesktopTasksLimiter> optional2, AppHandleEducationController appHandleEducationController, AppToWebEducationController appToWebEducationController, AppHandleAndHeaderVisibilityHelper appHandleAndHeaderVisibilityHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, Optional<DesktopActivityOrientationChangeHandler> optional3, FocusTransitionObserver focusTransitionObserver, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, RecentsTransitionHandler recentsTransitionHandler, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, ShellExecutor shellExecutor3, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopTilingDecorViewModel desktopTilingDecorViewModel, MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController, CompatUIHandler compatUIHandler, DesksOrganizer desksOrganizer, DesktopState desktopState, DesktopConfig desktopConfig) {
        this(context, shellExecutor, handler, choreographer, mainCoroutineDispatcher, coroutineScope, shellExecutor2, shellInit, shellCommandHandler, iWindowManager, shellTaskOrganizer, desktopUserRepositories, displayController, shellController, displayInsetsController, syncTransactionQueue, transitions, optional, desktopImmersiveController, appToWebGenericLinksParser, assistContentRequester, windowDecorViewHostSupplier, multiInstanceHelper, new DesktopModeWindowDecoration.Factory(), new InputMonitorFactory(), new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), new AppHeaderViewHolder.Factory(), new AppHandleViewHolder.Factory(), rootTaskDisplayAreaOrganizer, new SparseArray(), interactionJankMonitor, optional2, appHandleEducationController, appToWebEducationController, appHandleAndHeaderVisibilityHelper, windowDecorCaptionHandleRepository, optional3, new TaskPositionerFactory(), focusTransitionObserver, desktopModeEventLogger, desktopModeUiEventLogger, windowDecorTaskResourceLoader, recentsTransitionHandler, naturalSwitchingDropTargetController, shellExecutor3, desktopModeCompatPolicy, desktopTilingDecorViewModel, multiDisplayDragMoveIndicatorController, compatUIHandler, desksOrganizer, desktopState, desktopConfig);
    }

    public final void createWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        TaskPositioner fluidResizeTaskPositioner;
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration != null) {
            desktopModeWindowDecoration.close();
        }
        Context displayContext = this.mDisplayController.getDisplayContext(runningTaskInfo.displayId);
        Context createContextAsUser = this.mContext.createContextAsUser(UserHandle.of(runningTaskInfo.userId), 0);
        SplitScreenController splitScreenController = this.mSplitScreenController;
        Choreographer choreographer = this.mMainChoreographer;
        HandleMenuHelpController handleMenuHelpController = this.mHandleMenuHelpController;
        this.mDesktopModeWindowDecorFactory.getClass();
        DesktopModeWindowDecoration desktopModeWindowDecoration2 = new DesktopModeWindowDecoration(displayContext, createContextAsUser, this.mDisplayController, this.mTaskResourceLoader, splitScreenController, this.mDesktopUserRepositories, this.mTaskOrganizer, runningTaskInfo, surfaceControl, this.mMainHandler, this.mMainExecutor, this.mMainDispatcher, this.mBgScope, this.mBgExecutor, choreographer, this.mSyncQueue, this.mAppHeaderViewHolderFactory, this.mAppHandleViewHolderFactory, this.mRootTaskDisplayAreaOrganizer, this.mGenericLinksParser, this.mAssistContentRequester, this.mWindowDecorViewHostSupplier, this.mMultiInstanceHelper, this.mWindowDecorCaptionHandleRepository, this.mDesktopModeEventLogger, this.mDesktopModeUiEventLogger, this.mDesktopModeCompatPolicy, this.mDesktopState, this.mDesktopConfig, this.mMultiTaskingHeaderViewHolderFactory, handleMenuHelpController, this.mDesktopImmersiveController);
        this.mWindowDecorByTaskId.put(runningTaskInfo.taskId, desktopModeWindowDecoration2);
        boolean z = CoreRune.MW_CAPTION;
        if (z) {
            Slog.d("DesktopModeWindowDecorViewModel", "createWindowDecoration: " + desktopModeWindowDecoration2 + ", num_decors=" + this.mWindowDecorByTaskId.size());
        }
        Context context = this.mContext;
        InteractionJankMonitor interactionJankMonitor = this.mInteractionJankMonitor;
        Supplier supplier = this.mTransactionFactory;
        this.mTaskPositionerFactory.getClass();
        boolean z2 = ((DesktopConfigImpl) this.mDesktopConfig).isVeiledResizeEnabled;
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        DisplayController displayController = this.mDisplayController;
        DragEventListenerImpl dragEventListenerImpl = this.mDragEventListener;
        Transitions transitions = this.mTransitions;
        DesktopState desktopState = this.mDesktopState;
        if (z2) {
            boolean z3 = CoreRune.MW_CAPTION_FREEFORM_MOTION;
            fluidResizeTaskPositioner = new MultiDisplayVeiledResizeTaskPositioner(context, shellTaskOrganizer, desktopModeWindowDecoration2, displayController, dragEventListenerImpl, transitions, interactionJankMonitor, this.mMainHandler, this.mMultiDisplayDragMoveIndicatorController, desktopState, this.mAnimExecutor);
        } else {
            fluidResizeTaskPositioner = new FluidResizeTaskPositioner(shellTaskOrganizer, transitions, desktopModeWindowDecoration2, displayController, dragEventListenerImpl, supplier, desktopState);
        }
        if (z) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration2.mTaskInfo;
            if (!runningTaskInfo2.isResizeable) {
                int i = runningTaskInfo2.displayId;
                DesktopStateImpl.Companion.getClass();
                if (DesktopStateImpl.Companion.inDesktopWindowing(i) && DesktopModeFlags.ENABLE_WINDOWING_SCALED_RESIZING.isTrue()) {
                    fluidResizeTaskPositioner = new FixedAspectRatioTaskPositionerDecorator(desktopModeWindowDecoration2, fluidResizeTaskPositioner);
                }
            }
        }
        desktopModeWindowDecoration2.mTaskDragResizer = fluidResizeTaskPositioner;
        DesktopModeTouchEventListener desktopModeTouchEventListener = new DesktopModeTouchEventListener(this, runningTaskInfo, fluidResizeTaskPositioner, fluidResizeTaskPositioner);
        desktopModeWindowDecoration2.mOnMaximizeOrRestoreClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, desktopModeTouchEventListener, 0);
        desktopModeWindowDecoration2.mOnImmersiveOrRestoreClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(this, runningTaskInfo, 2);
        desktopModeWindowDecoration2.mOnLeftSnapClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, desktopModeTouchEventListener, 1);
        desktopModeWindowDecoration2.mOnRightSnapClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, desktopModeTouchEventListener, 2);
        desktopModeWindowDecoration2.mOnToDesktopClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda17(this, runningTaskInfo, 0);
        desktopModeWindowDecoration2.mOnToFullscreenClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(this, runningTaskInfo, 3);
        desktopModeWindowDecoration2.mOnToSplitscreenClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(this, runningTaskInfo, 4);
        desktopModeWindowDecoration2.mOnToFloatClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(this, runningTaskInfo, 5);
        desktopModeWindowDecoration2.mOpenInBrowserClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda17(this, runningTaskInfo, 1);
        desktopModeWindowDecoration2.mOnNewWindowClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(this, runningTaskInfo, 6);
        desktopModeWindowDecoration2.mOnManageWindowsClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(this, desktopModeWindowDecoration2, 7);
        desktopModeWindowDecoration2.mOnChangeAspectRatioClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(this, runningTaskInfo, 0);
        desktopModeWindowDecoration2.mOnRestartClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda11(this, runningTaskInfo, 1);
        desktopModeWindowDecoration2.mOnMaximizeHoverListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, desktopModeWindowDecoration2, runningTaskInfo);
        desktopModeWindowDecoration2.mOnCaptionButtonClickListener = desktopModeTouchEventListener;
        desktopModeWindowDecoration2.mOnCaptionTouchListener = desktopModeTouchEventListener;
        desktopModeWindowDecoration2.mOnCaptionLongClickListener = desktopModeTouchEventListener;
        desktopModeWindowDecoration2.mOnCaptionGenericMotionListener = desktopModeTouchEventListener;
        desktopModeWindowDecoration2.mExclusionRegionListener = this.mExclusionRegionListener;
        desktopModeWindowDecoration2.mDragPositioningCallback = fluidResizeTaskPositioner;
        if (CoreRune.MW_CAPTION_FREEFORM_MOTION) {
            desktopModeWindowDecoration2.mTaskPositioner = fluidResizeTaskPositioner;
        }
        desktopModeWindowDecoration2.relayout(runningTaskInfo, transaction, transaction2, false, false, this.mFocusTransitionObserver.hasGlobalFocus(runningTaskInfo), (Region) this.mGestureExclusionTracker.exclusionRegions.get(Integer.valueOf(runningTaskInfo.displayId)));
        if (DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
            return;
        }
        if (z && runningTaskInfo.isFreeform()) {
            return;
        }
        incrementEventReceiverTasks(runningTaskInfo.displayId);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void destroyWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo) {
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration == null) {
            return;
        }
        desktopModeWindowDecoration.close();
        int i = runningTaskInfo.displayId;
        if (this.mEventReceiversByDisplay.contains(i) && !DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
            removeTaskFromEventReceiver(i);
        }
        this.mWindowDecorByTaskId.remove(runningTaskInfo.taskId);
        if (CoreRune.MW_CAPTION) {
            Slog.d("DesktopModeWindowDecorViewModel", "destroyWindowDecoration: " + desktopModeWindowDecoration + ", num_decors=" + this.mWindowDecorByTaskId.size());
        }
        int i2 = runningTaskInfo.taskId;
        NaturalSwitchingDropTargetController naturalSwitchingDropTargetController = this.mNSController;
        if (naturalSwitchingDropTargetController.mIsRunning) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = naturalSwitchingDropTargetController.mTaskInfo;
            if ((runningTaskInfo2 != null ? runningTaskInfo2.taskId : -1) == i2) {
                naturalSwitchingDropTargetController.cancelNaturalSwitching("WindowDecorationDestroyed(" + i2 + ")");
            }
        }
    }

    public final void ensureHandlerOnTransitionFinished() {
        TaskAppearedInfo taskAppearedInfo;
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        ArrayList arrayList = (ArrayList) shellTaskOrganizer.getVisibleTaskAppearedInfos();
        int size = arrayList.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TaskAppearedInfo taskAppearedInfo2 = (TaskAppearedInfo) obj;
            ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo2.getTaskInfo();
            int i2 = taskInfo.taskId;
            if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX && taskInfo.lastParentTaskIdBeforePip != -1 && taskInfo.getWindowingMode() != 2) {
                int i3 = taskInfo.lastParentTaskIdBeforePip;
                synchronized (shellTaskOrganizer.mLock) {
                    taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer.mTasks.get(i3);
                }
                SplitScreenController splitScreenController = this.mSplitScreenController;
                if (splitScreenController == null || !splitScreenController.isTaskRootOrStageRoot(taskInfo.taskId)) {
                    if (taskAppearedInfo != null && this.mWindowDecorByTaskId.get(i3) == null && shouldShowWindowDecor(taskInfo, true) && taskAppearedInfo.getLeash().isValid()) {
                        Slog.d("DesktopModeWindowDecorViewModel", "ensureHandlerOnTransitionFinished for PIP parent: show, t# " + taskInfo.taskId);
                        createWindowDecoration(taskAppearedInfo.getTaskInfo(), taskAppearedInfo.getLeash(), transaction, transaction2);
                        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(i3);
                        if (desktopModeWindowDecoration != null) {
                            desktopModeWindowDecoration.setCaptionVisibility(desktopModeWindowDecoration.getHandleRootView(), desktopModeWindowDecoration.mIsCaptionVisible);
                        }
                        z = true;
                    }
                }
            } else if (taskInfo.topActivity != null && this.mWindowDecorByTaskId.get(i2) == null && taskAppearedInfo2.getLeash().isValid() && shouldShowWindowDecor(taskInfo, false)) {
                Slog.d("DesktopModeWindowDecorViewModel", "ensureHandlerOnTransitionFinished: show, t# " + i2);
                createWindowDecoration(taskInfo, taskAppearedInfo2.getLeash(), transaction, transaction2);
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(i2);
                if (desktopModeWindowDecoration2 != null) {
                    desktopModeWindowDecoration2.setCaptionVisibility(desktopModeWindowDecoration2.getHandleRootView(), desktopModeWindowDecoration2.mIsCaptionVisible);
                }
                z = true;
            }
        }
        if (z) {
            transaction.apply();
            transaction2.apply();
        }
    }

    public final void forAllDecorations(Consumer consumer) {
        for (int size = this.mWindowDecorByTaskId.size() - 1; size >= 0; size--) {
            consumer.accept((DesktopModeWindowDecoration) this.mWindowDecorByTaskId.valueAt(size));
        }
    }

    public final DesktopModeWindowDecoration getFocusedDecor() {
        for (int size = this.mWindowDecorByTaskId.size() - 1; size >= 0; size--) {
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.valueAt(size);
            if (desktopModeWindowDecoration != null && desktopModeWindowDecoration.mHasGlobalFocus) {
                return desktopModeWindowDecoration;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:136:0x0345  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x036b  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x03cd  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0374  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleCaptionThroughStatusBar(android.view.MotionEvent r35, com.android.wm.shell.windowdecor.DesktopModeWindowDecoration r36, java.lang.Runnable r37) {
        /*
            Method dump skipped, instructions count: 1264
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel.handleCaptionThroughStatusBar(android.view.MotionEvent, com.android.wm.shell.windowdecor.DesktopModeWindowDecoration, java.lang.Runnable):void");
    }

    public final void incrementEventReceiverTasks(int i) {
        if (this.mEventReceiversByDisplay.contains(i)) {
            ((EventReceiver) this.mEventReceiversByDisplay.get(i)).mTasksOnDisplay++;
            return;
        }
        InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
        this.mInputMonitorFactory.getClass();
        InputMonitor monitorGestureInput = inputManager.monitorGestureInput("caption-touch", i);
        this.mEventReceiversByDisplay.put(i, new EventReceiver(monitorGestureInput, monitorGestureInput.getInputChannel(), Looper.myLooper()));
    }

    public final boolean isTaskInSplitScreen(int i) {
        SplitScreenController splitScreenController = this.mSplitScreenController;
        return splitScreenController != null && splitScreenController.isTaskInSplitScreen$1(i);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onDecorationTaskTransitionReady(IBinder iBinder, TransitionInfo.Change change) {
        List list = (List) ((HashMap) this.mTransitionToTaskInfo).get(iBinder);
        if (list == null) {
            list = new ArrayList();
            ((HashMap) this.mTransitionToTaskInfo).put(iBinder, list);
        }
        list.add(change.getTaskInfo());
    }

    public final void onEnterOrExitImmersive(ActivityManager.RunningTaskInfo runningTaskInfo) {
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration == null) {
            return;
        }
        DesktopRepository profile = this.mDesktopUserRepositories.getProfile(runningTaskInfo.userId);
        boolean isTaskInFullImmersiveState = profile.isTaskInFullImmersiveState(runningTaskInfo.taskId);
        DesktopModeUiEventLogger desktopModeUiEventLogger = this.mDesktopModeUiEventLogger;
        DesktopImmersiveController desktopImmersiveController = this.mDesktopImmersiveController;
        if (isTaskInFullImmersiveState) {
            desktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_RESTORE);
            desktopImmersiveController.moveTaskToNonImmersive(desktopModeWindowDecoration.mTaskInfo, DesktopImmersiveController.ExitReason.USER_INTERACTION);
        } else {
            DesktopRepository.Desk activeDesk = profile.desktopData.getActiveDesk(runningTaskInfo.displayId);
            Integer num = activeDesk != null ? activeDesk.fullImmersiveTaskId : null;
            if (num != null) {
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(num.intValue());
                if (desktopModeWindowDecoration2 != null) {
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration.mTaskInfo;
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = desktopModeWindowDecoration2.mTaskInfo;
                    desktopImmersiveController.getClass();
                    if (!runningTaskInfo2.isFreeform()) {
                        throw new IllegalStateException("Task must already be in freeform");
                    }
                    if (!runningTaskInfo3.isFreeform()) {
                        throw new IllegalStateException("Task must already be in freeform");
                    }
                    if (((ArrayList) desktopImmersiveController.pendingImmersiveTransitions).isEmpty()) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.setBounds(runningTaskInfo2.token, new Rect());
                        windowContainerTransaction.setBounds(runningTaskInfo3.token, desktopImmersiveController.getExitDestinationBounds(runningTaskInfo3));
                        DesktopImmersiveController.logV("Moving task %d out of immersive mode, task %s in immersive mode", Integer.valueOf(runningTaskInfo3.taskId), Integer.valueOf(runningTaskInfo2.taskId));
                        IBinder startTransition = desktopImmersiveController.transitions.startTransition(6, windowContainerTransaction, desktopImmersiveController);
                        int i = runningTaskInfo2.taskId;
                        int i2 = runningTaskInfo2.displayId;
                        DesktopImmersiveController.Direction direction = DesktopImmersiveController.Direction.ENTER;
                        startTransition.getClass();
                        DesktopImmersiveController.addPendingImmersiveTransition$default(desktopImmersiveController, i, i2, direction, startTransition);
                        DesktopImmersiveController.addPendingImmersiveTransition$default(desktopImmersiveController, runningTaskInfo3.taskId, runningTaskInfo3.displayId, DesktopImmersiveController.Direction.EXIT, startTransition);
                    } else {
                        DesktopImmersiveController.logV("Cannot start exit because transition(s) already in progress: %s", desktopImmersiveController.pendingImmersiveTransitions);
                    }
                }
            } else {
                desktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_IMMERSIVE);
                ActivityManager.RunningTaskInfo runningTaskInfo4 = desktopModeWindowDecoration.mTaskInfo;
                desktopImmersiveController.getClass();
                if (!runningTaskInfo4.isFreeform()) {
                    throw new IllegalStateException("Task must already be in freeform");
                }
                if (((ArrayList) desktopImmersiveController.pendingImmersiveTransitions).isEmpty()) {
                    WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                    windowContainerTransaction2.setBounds(runningTaskInfo4.token, new Rect());
                    DesktopImmersiveController.logV(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(runningTaskInfo4.taskId, "Moving task ", " into immersive mode"), new Object[0]);
                    IBinder startTransition2 = desktopImmersiveController.transitions.startTransition(6, windowContainerTransaction2, desktopImmersiveController);
                    int i3 = runningTaskInfo4.taskId;
                    int i4 = runningTaskInfo4.displayId;
                    DesktopImmersiveController.Direction direction2 = DesktopImmersiveController.Direction.ENTER;
                    startTransition2.getClass();
                    DesktopImmersiveController.addPendingImmersiveTransition$default(desktopImmersiveController, i3, i4, direction2, startTransition2);
                } else {
                    DesktopImmersiveController.logV("Cannot start entry because transition(s) already in progress: %s", desktopImmersiveController.pendingImmersiveTransitions);
                }
            }
        }
        desktopModeWindowDecoration.closeMaximizeMenu();
    }

    @Override // com.android.wm.shell.shared.FocusTransitionListener
    public final void onFocusedTaskChanged(int i, boolean z, boolean z2) {
        WindowDecoration windowDecoration = (WindowDecoration) this.mWindowDecorByTaskId.get(i);
        if (windowDecoration != null) {
            windowDecoration.relayout(windowDecoration.mTaskInfo, z2, windowDecoration.mExclusionRegion);
        }
    }

    public final void onSnapResize(int i, boolean z, DesktopModeEventLogger.Companion.InputMethod inputMethod, boolean z2) {
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(i);
        if (desktopModeWindowDecoration == null) {
            return;
        }
        if (z2) {
            this.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, z ? DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_TILE_TO_LEFT : DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_TILE_TO_RIGHT);
        }
        this.mInteractionJankMonitor.begin(desktopModeWindowDecoration.mTaskSurface, this.mContext, this.mMainHandler, 118, "maximize_menu_resizable");
        ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
        DesktopTasksController.SnapPosition snapPosition = z ? DesktopTasksController.SnapPosition.LEFT : DesktopTasksController.SnapPosition.RIGHT;
        DesktopModeEventLogger.Companion.ResizeTrigger resizeTrigger = z ? DesktopModeEventLogger.Companion.ResizeTrigger.SNAP_LEFT_MENU : DesktopModeEventLogger.Companion.ResizeTrigger.SNAP_RIGHT_MENU;
        DesktopTasksController desktopTasksController = this.mDesktopTasksController;
        desktopTasksController.getClass();
        Context displayContext = desktopTasksController.displayController.getDisplayContext(runningTaskInfo.getDisplayId());
        if (displayContext == null) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(runningTaskInfo.getDisplayId(), "displayContext is null for ", "DesktopTasksController");
        }
        if (runningTaskInfo.isResizeable || !DesktopModeFlags.DISABLE_NON_RESIZABLE_APP_SNAP_RESIZE.isTrue()) {
            desktopTasksController.snapToHalfScreen(runningTaskInfo, null, runningTaskInfo.configuration.windowConfiguration.getBounds(), snapPosition, resizeTrigger, inputMethod);
        } else {
            if (displayContext == null) {
                displayContext = desktopTasksController.context;
            }
            Toast.makeText(displayContext, R.string.multiwindow_desktop_mode_non_resizable_snap_text, 0).show();
        }
        desktopModeWindowDecoration.closeHandleMenu();
        desktopModeWindowDecoration.closeMaximizeMenu();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskChanging(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        float f;
        TaskPositioner taskPositioner;
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (!shouldShowWindowDecor(runningTaskInfo, false)) {
            if (desktopModeWindowDecoration != null) {
                destroyWindowDecoration(runningTaskInfo);
                return;
            }
            return;
        }
        if (desktopModeWindowDecoration == null) {
            createWindowDecoration(runningTaskInfo, surfaceControl, transaction, transaction2);
            return;
        }
        if (CoreRune.MW_CAPTION_DESKTOP) {
            int i = runningTaskInfo.displayId;
            DesktopStateImpl.Companion.getClass();
            boolean inDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(i);
            if (inDesktopWindowing != desktopModeWindowDecoration.mInDesktopWindowing) {
                desktopModeWindowDecoration.mInDesktopWindowing = inDesktopWindowing;
            }
        }
        if (CoreRune.MW_CAPTION_FREEFORM && CoreRune.MW_CAPTION_FREEFORM_MOTION && (taskPositioner = desktopModeWindowDecoration.mTaskPositioner) != null) {
            taskPositioner.cancelTaskMotion();
        }
        desktopModeWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false, false, this.mFocusTransitionObserver.hasGlobalFocus(runningTaskInfo), (Region) this.mGestureExclusionTracker.exclusionRegions.get(Integer.valueOf(runningTaskInfo.displayId)));
        if (CoreRune.MW_CAPTION_FREEFORM_STASH && desktopModeWindowDecoration.mFreeformStashState.isStashed() && runningTaskInfo.isFreeform()) {
            Rect bounds = desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
            SurfaceControl surfaceControl2 = desktopModeWindowDecoration.mTaskSurface;
            WindowDecoration.RelayoutResult relayoutResult = desktopModeWindowDecoration.mResult;
            transaction2.setWindowCrop(surfaceControl2, relayoutResult.mWidth, relayoutResult.mHeight);
            FreeformStashState freeformStashState = desktopModeWindowDecoration.mFreeformStashState;
            int width = bounds.width();
            if (freeformStashState.isLeftStashed()) {
                float f2 = width;
                f = f2 - (freeformStashState.mScale * f2);
            } else {
                f = 0.0f;
            }
            transaction.setCornerRadius(desktopModeWindowDecoration.mTaskSurface, desktopModeWindowDecoration.mRelayoutParams.mCornerRadius);
            SurfaceControl.Transaction cornerRadius = transaction2.setPosition(desktopModeWindowDecoration.mTaskSurface, bounds.left + f, bounds.top).setCornerRadius(desktopModeWindowDecoration.mTaskSurface, desktopModeWindowDecoration.mRelayoutParams.mCornerRadius);
            SurfaceControl surfaceControl3 = desktopModeWindowDecoration.mTaskSurface;
            float f3 = desktopModeWindowDecoration.mFreeformStashState.mScale;
            cornerRadius.setMatrix(surfaceControl3, f3, 0.0f, 0.0f, f3);
            desktopModeWindowDecoration.mTaskPosition.set(bounds.left + ((int) f), bounds.top);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskClosing(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration == null) {
            return;
        }
        if (CoreRune.MW_CAPTION_FREEFORM) {
            desktopModeWindowDecoration.onTaskClosing();
        }
        desktopModeWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false, false, this.mFocusTransitionObserver.hasGlobalFocus(runningTaskInfo), (Region) this.mGestureExclusionTracker.exclusionRegions.get(Integer.valueOf(runningTaskInfo.displayId)));
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        TilingDividerView tilingDividerView;
        TilingDividerView tilingDividerView2;
        Display display;
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration == null) {
            return;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration.mTaskInfo;
        boolean z = CoreRune.MW_CAPTION;
        if (z && (display = desktopModeWindowDecoration.mDisplay) != null && !display.isValid() && desktopModeWindowDecoration.mDisplay.getDisplayId() != runningTaskInfo.displayId) {
            Slog.d("DesktopModeWindowDecorViewModel", "onTaskInfoChanged: invalid display, destroy " + desktopModeWindowDecoration);
            destroyWindowDecoration(runningTaskInfo);
            return;
        }
        if (CoreRune.MW_CAPTION_BUG_FIX && !runningTaskInfo.isFreeform() && runningTaskInfo.isCaptionHiddenRequested) {
            Slog.d("DesktopModeWindowDecorViewModel", "onTaskInfoChanged: destroy windowDecoration from app request, " + desktopModeWindowDecoration);
            destroyWindowDecoration(runningTaskInfo);
            return;
        }
        if (CoreRune.MW_CAPTION_FREEFORM_STASH && desktopModeWindowDecoration.mFreeformStashState.isStashed() && desktopModeWindowDecoration.mFreeformStashState.mScale != 1.0f && runningTaskInfo.configuration.isDesktopModeEnabled()) {
            desktopModeWindowDecoration.mTaskPositioner.resetStashedFreeform(false);
        }
        if (runningTaskInfo.displayId != runningTaskInfo2.displayId && !DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue()) {
            removeTaskFromEventReceiver(runningTaskInfo2.displayId);
            incrementEventReceiverTasks(runningTaskInfo.displayId);
        }
        if (z) {
            desktopModeWindowDecoration.relayout(runningTaskInfo, runningTaskInfo.isFocused, desktopModeWindowDecoration.mExclusionRegion);
        } else {
            desktopModeWindowDecoration.relayout(runningTaskInfo, desktopModeWindowDecoration.mHasGlobalFocus, desktopModeWindowDecoration.mExclusionRegion);
        }
        DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) this.mDesktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId.get(runningTaskInfo.displayId);
        if (desktopTilingWindowDecoration != null) {
            String str = DesktopTilingWindowDecoration.TAG;
            boolean z2 = (runningTaskInfo.configuration.uiMode & 48) == 32;
            DesktopTilingDividerWindowManager desktopTilingDividerWindowManager = desktopTilingWindowDecoration.desktopTilingDividerWindowManager;
            if (desktopTilingDividerWindowManager != null && (tilingDividerView2 = desktopTilingDividerWindowManager.tilingDividerView) != null) {
                tilingDividerView2.decorThemeUtil = new DecorThemeUtil(tilingDividerView2.getContext());
                int color = tilingDividerView2.paint.getColor();
                DecorThemeUtil decorThemeUtil = tilingDividerView2.decorThemeUtil;
                if (color != ColorKt.m467toArgb8_81llA((tilingDividerView2.isDarkMode ? decorThemeUtil.darkColors : decorThemeUtil.lightColors).outlineVariant)) {
                    Paint paint = tilingDividerView2.paint;
                    DecorThemeUtil decorThemeUtil2 = tilingDividerView2.decorThemeUtil;
                    paint.setColor(ColorKt.m467toArgb8_81llA((tilingDividerView2.isDarkMode ? decorThemeUtil2.darkColors : decorThemeUtil2.lightColors).outlineVariant));
                    DividerRoundedCorner dividerRoundedCorner = tilingDividerView2.corners;
                    if (dividerRoundedCorner == null) {
                        dividerRoundedCorner = null;
                    }
                    dividerRoundedCorner.mDividerBarBackground.setColor(tilingDividerView2.paint.getColor());
                    dividerRoundedCorner.invalidate();
                    tilingDividerView2.invalidate();
                }
            }
            if (z2 != desktopTilingWindowDecoration.isDarkMode && desktopTilingWindowDecoration.isTilingManagerInitialised) {
                desktopTilingWindowDecoration.isDarkMode = z2;
                DesktopTilingDividerWindowManager desktopTilingDividerWindowManager2 = desktopTilingWindowDecoration.desktopTilingDividerWindowManager;
                if (desktopTilingDividerWindowManager2 != null && (tilingDividerView = desktopTilingDividerWindowManager2.tilingDividerView) != null) {
                    tilingDividerView.isDarkMode = z2;
                    DividerHandleView dividerHandleView = tilingDividerView.handle;
                    if (dividerHandleView == null) {
                        dividerHandleView = null;
                    }
                    dividerHandleView.getClass();
                    int displayId = dividerHandleView.getContext().getDisplayId();
                    DesktopStateImpl.Companion.getClass();
                    if (!DesktopStateImpl.Companion.inDesktopWindowing(displayId)) {
                        dividerHandleView.mPaint.setColor(z2 ? dividerHandleView.getResources().getColor(R.color.tiling_handle_background_dark, null) : dividerHandleView.getResources().getColor(R.color.tiling_handle_background_light, null));
                        dividerHandleView.invalidate();
                    }
                    Paint paint2 = tilingDividerView.paint;
                    DecorThemeUtil decorThemeUtil3 = tilingDividerView.decorThemeUtil;
                    paint2.setColor(ColorKt.m467toArgb8_81llA((z2 ? decorThemeUtil3.darkColors : decorThemeUtil3.lightColors).outlineVariant));
                    DividerRoundedCorner dividerRoundedCorner2 = tilingDividerView.corners;
                    DividerRoundedCorner dividerRoundedCorner3 = dividerRoundedCorner2 != null ? dividerRoundedCorner2 : null;
                    dividerRoundedCorner3.mDividerBarBackground.setColor(tilingDividerView.paint.getColor());
                    dividerRoundedCorner3.invalidate();
                    tilingDividerView.invalidate();
                }
            }
        }
        this.mActivityOrientationChangeHandler.ifPresent(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda17(runningTaskInfo2, runningTaskInfo));
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final boolean onTaskOpening(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        FreeformOutline freeformOutline;
        OutlineView outlineView;
        if (!shouldShowWindowDecor(runningTaskInfo, false)) {
            return false;
        }
        createWindowDecoration(runningTaskInfo, surfaceControl, transaction, transaction2);
        if (CoreRune.MW_CAPTION_FREEFORM && (desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) != null && (freeformOutline = desktopModeWindowDecoration.mFreeformOutline) != null && (outlineView = freeformOutline.getOutlineView()) != null) {
            outlineView.mIsOpening = true;
            outlineView.invalidate();
        }
        return true;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskToBack(ActivityManager.RunningTaskInfo runningTaskInfo) {
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration != null) {
            desktopModeWindowDecoration.onTaskClosing();
        }
        int i = runningTaskInfo.taskId;
        NaturalSwitchingDropTargetController naturalSwitchingDropTargetController = this.mNSController;
        if (naturalSwitchingDropTargetController.mIsRunning) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = naturalSwitchingDropTargetController.mTaskInfo;
            if ((runningTaskInfo2 != null ? runningTaskInfo2.taskId : -1) == i) {
                naturalSwitchingDropTargetController.cancelNaturalSwitching("TaskClosing(" + i + ")");
            }
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        FreeformOutline freeformOutline;
        OutlineView outlineView;
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration == null || (freeformOutline = desktopModeWindowDecoration.mFreeformOutline) == null || (outlineView = freeformOutline.getOutlineView()) == null) {
            return;
        }
        outlineView.mIsOpening = true;
        outlineView.invalidate();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        boolean z = this.mTaskOrganizer.getRunningTaskInfo(runningTaskInfo.taskId) == null;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_DESKTOP_MODE_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, -2894319029625802502L, 13, Long.valueOf(runningTaskInfo.taskId), Boolean.valueOf(z));
        }
        if (z) {
            destroyWindowDecoration(runningTaskInfo);
            return;
        }
        if (CoreRune.MW_CAPTION_FREEFORM_STASH && (desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) != null && desktopModeWindowDecoration.mFreeformStashState.isStashed()) {
            FreeformStashState freeformStashState = desktopModeWindowDecoration.mFreeformStashState;
            freeformStashState.mAnimType = -1;
            freeformStashState.mAnimating = false;
            freeformStashState.setStashed(-1);
            desktopModeWindowDecoration.mFreeformStashState.setStashed(0);
            desktopModeWindowDecoration.closeFreeformDimInputListener();
        }
    }

    public final void onToggleSizeInteraction(int i, ToggleTaskSizeInteraction.AmbiguousSource ambiguousSource, MotionEvent motionEvent) {
        ToggleTaskSizeInteraction.Source source;
        ToggleTaskSizeInteraction toggleTaskSizeInteraction;
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(i);
        if (desktopModeWindowDecoration == null) {
            return;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
        int i2 = runningTaskInfo.displayId;
        DisplayController displayController = this.mDisplayController;
        if (displayController.getDisplayLayout(i2) == null) {
            toggleTaskSizeInteraction = null;
        } else {
            boolean isTaskMaximized = DesktopModeUtils.isTaskMaximized(runningTaskInfo, displayController);
            ToggleTaskSizeInteraction.Direction direction = isTaskMaximized ? ToggleTaskSizeInteraction.Direction.RESTORE : ToggleTaskSizeInteraction.Direction.MAXIMIZE;
            int i3 = ToggleTaskSizeUtilsKt$WhenMappings.$EnumSwitchMapping$0[ambiguousSource.ordinal()];
            if (i3 == 1) {
                source = isTaskMaximized ? ToggleTaskSizeInteraction.Source.HEADER_BUTTON_TO_RESTORE : ToggleTaskSizeInteraction.Source.HEADER_BUTTON_TO_MAXIMIZE;
            } else if (i3 == 2) {
                source = isTaskMaximized ? ToggleTaskSizeInteraction.Source.MAXIMIZE_MENU_TO_RESTORE : ToggleTaskSizeInteraction.Source.MAXIMIZE_MENU_TO_MAXIMIZE;
            } else {
                if (i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                source = isTaskMaximized ? ToggleTaskSizeInteraction.Source.DOUBLE_TAP_TO_RESTORE : ToggleTaskSizeInteraction.Source.DOUBLE_TAP_TO_MAXIMIZE;
            }
            DesktopModeEventLogger.Companion.getClass();
            toggleTaskSizeInteraction = new ToggleTaskSizeInteraction(direction, source, DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent));
        }
        if (toggleTaskSizeInteraction == null) {
            return;
        }
        Integer num = toggleTaskSizeInteraction.cujTracing;
        if (num != null) {
            this.mInteractionJankMonitor.begin(desktopModeWindowDecoration.mTaskSurface, this.mContext, this.mMainHandler, num.intValue(), toggleTaskSizeInteraction.jankTag);
        }
        this.mDesktopTasksController.toggleDesktopTaskSize(desktopModeWindowDecoration.mTaskInfo, toggleTaskSizeInteraction);
        desktopModeWindowDecoration.closeHandleMenu();
        desktopModeWindowDecoration.closeMaximizeMenu();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionFinished(IBinder iBinder) {
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        FreeformOutline freeformOutline;
        OutlineView outlineView;
        if (CoreRune.MW_CAPTION) {
            List list = (List) ((HashMap) this.mTransitionToTaskInfo).getOrDefault(iBinder, Collections.EMPTY_LIST);
            ((HashMap) this.mTransitionToTaskInfo).remove(iBinder);
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) list.get(i);
                if (runningTaskInfo != null && (desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) != null && (freeformOutline = desktopModeWindowDecoration.mFreeformOutline) != null && (outlineView = freeformOutline.getOutlineView()) != null && (outlineView.mIsOpening || outlineView.mIsClosing)) {
                    outlineView.mIsOpening = false;
                    outlineView.mIsClosing = false;
                    outlineView.invalidate();
                }
            }
            if (!CoreRune.MW_SHELL_TRANSITION_BUG_FIX || this.mIsKeyguardShowing) {
                return;
            }
            ensureHandlerOnTransitionFinished();
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
        List list = (List) ((HashMap) this.mTransitionToTaskInfo).get(iBinder);
        if (list == null) {
            return;
        }
        ((HashMap) this.mTransitionToTaskInfo).remove(list);
        List list2 = (List) ((HashMap) this.mTransitionToTaskInfo).get(iBinder2);
        if (list2 != null) {
            list2.addAll(list);
        } else {
            ((HashMap) this.mTransitionToTaskInfo).put(iBinder2, list);
        }
    }

    public final void removeTaskFromEventReceiver(int i) {
        EventReceiver eventReceiver;
        EventReceiver eventReceiver2;
        if (this.mEventReceiversByDisplay.contains(i) && (eventReceiver = (EventReceiver) this.mEventReceiversByDisplay.get(i)) != null) {
            int i2 = eventReceiver.mTasksOnDisplay - 1;
            eventReceiver.mTasksOnDisplay = i2;
            if (i2 != 0 || (eventReceiver2 = (EventReceiver) this.mEventReceiversByDisplay.removeReturnOld(i)) == null) {
                return;
            }
            eventReceiver2.dispose();
        }
    }

    public final void removeTaskIfTiled(int i, int i2) {
        DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) this.mDesktopTilingDecorViewModel.tilingTransitionHandlerByDisplayId.get(i);
        if (desktopTilingWindowDecoration != null) {
            String str = DesktopTilingWindowDecoration.TAG;
            desktopTilingWindowDecoration.removeTaskIfTiled(i2, false, false);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void setFreeformTaskTransitionStarter(FreeformTaskTransitionStarter freeformTaskTransitionStarter) {
        SplitScreenController splitScreenController;
        TaskOperations taskOperations = new TaskOperations(freeformTaskTransitionStarter, this.mContext, this.mSyncQueue);
        this.mTaskOperations = taskOperations;
        this.mDesktopTasksController.freeformTaskTransitionStarter = freeformTaskTransitionStarter;
        if (CoreRune.MW_CAPTION && (splitScreenController = this.mSplitScreenController) != null) {
            taskOperations.mSplitScreenController = splitScreenController;
        }
        ShortcutController shortcutController = ShortcutController.getInstance();
        Context context = this.mContext;
        SplitScreenController splitScreenController2 = this.mSplitScreenController;
        TaskOperations taskOperations2 = this.mTaskOperations;
        shortcutController.mContext = context;
        shortcutController.mSplitScreenController = splitScreenController2;
        shortcutController.mShellTaskOrganizer = this.mTaskOrganizer;
        shortcutController.mDisplayController = this.mDisplayController;
        shortcutController.mTaskOperations = taskOperations2;
        taskOperations2.mSplitScreenController = splitScreenController2;
        shortcutController.mMainExecutor = splitScreenController2.mMainExecutor;
        shortcutController.mShortCutPolicyMap.put(51, new ShortcutPolicyForWKey(shortcutController));
        ShortcutPolicyFor1to4Keys shortcutPolicyFor1to4Keys = new ShortcutPolicyFor1to4Keys(shortcutController);
        shortcutController.mShortCutPolicyMap.put(8, shortcutPolicyFor1to4Keys);
        shortcutController.mShortCutPolicyMap.put(9, shortcutPolicyFor1to4Keys);
        shortcutController.mShortCutPolicyMap.put(10, shortcutPolicyFor1to4Keys);
        shortcutController.mShortCutPolicyMap.put(11, shortcutPolicyFor1to4Keys);
        try {
            ActivityTaskManager.getService().registKeyEventListener(shortcutController.mKeyEventListener);
        } catch (RemoteException e) {
            Log.e("ShortcutController", "init: e=" + e);
        }
        shortcutController.mInitialized = true;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void setSplitScreenController(SplitScreenController splitScreenController) {
        TaskOperations taskOperations;
        this.mSplitScreenController = splitScreenController;
        this.mAppHandleAndHeaderVisibilityHelper.splitScreenController = splitScreenController;
        if (!CoreRune.MW_CAPTION || (taskOperations = this.mTaskOperations) == null) {
            return;
        }
        taskOperations.mSplitScreenController = splitScreenController;
    }

    /* JADX WARN: Code restructure failed: missing block: B:4:0x0013, code lost:
    
        if (((com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer) r8.mDesktopTasksController.desksOrganizer).deskRootsByDeskId.contains(r9.taskId) != false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x013d, code lost:
    
        if (android.hardware.display.DisplayTopology.findDisplay(r8, r9.getRoot()) != null) goto L111;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean shouldShowWindowDecor(android.app.ActivityManager.RunningTaskInfo r9, boolean r10) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel.shouldShowWindowDecor(android.app.ActivityManager$RunningTaskInfo, boolean):boolean");
    }

    public final void updateColorThemeState() {
        boolean z = Settings.System.getInt(this.mContext.getContentResolver(), "wallpapertheme_state", 0) == 1;
        String string = Settings.System.getString(this.mContext.getContentResolver(), "wallpapertheme_color");
        if (string == null) {
            string = "";
        }
        if (CaptionGlobalState.COLOR_THEME_ENABLED != z) {
            CaptionGlobalState.COLOR_THEME_ENABLED = z;
            Slog.d("DesktopModeWindowDecorViewModel", "updateColorThemeState: enabled=" + z);
        }
        if (CaptionGlobalState.COLOR_THEME_COLOR.equals(string)) {
            return;
        }
        CaptionGlobalState.COLOR_THEME_COLOR = string;
        forAllDecorations(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda25(0));
    }

    public final void updateFullscreenHandlerState() {
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "multi_window_menu_in_full_screen", ((DesktopStateImpl) this.mDesktopState).isDesktopModeSupportedOnDisplay(this.mContext.getDisplay()) ? 1 : 0) == 1;
        if (CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED != z) {
            CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED = z;
            Slog.d("DesktopModeWindowDecorViewModel", "updateFullscreenHandlerState: enabled=" + z);
            if (!z) {
                final ArrayList arrayList = new ArrayList();
                forAllDecorations(new Consumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda24
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ArrayList arrayList2 = arrayList;
                        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) obj;
                        if (desktopModeWindowDecoration.mTaskInfo.getWindowingMode() == 1) {
                            arrayList2.add(desktopModeWindowDecoration);
                        }
                    }
                });
                for (int size = arrayList.size() - 1; size >= 0; size += -1) {
                    DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) arrayList.get(size);
                    Slog.d("DesktopModeWindowDecorViewModel", "onFullscreenHandlerDisabled: remove, " + desktopModeWindowDecoration);
                    destroyWindowDecoration(desktopModeWindowDecoration.mTaskInfo);
                }
                return;
            }
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
            ArrayList arrayList2 = (ArrayList) this.mTaskOrganizer.getVisibleTaskAppearedInfos();
            int size2 = arrayList2.size();
            int i = 0;
            while (i < size2) {
                Object obj = arrayList2.get(i);
                i++;
                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) obj;
                ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
                int i2 = taskInfo.taskId;
                if (taskInfo.getWindowingMode() == 1 && taskAppearedInfo.getLeash().isValid() && shouldShowWindowDecor(taskInfo, false)) {
                    Slog.d("DesktopModeWindowDecorViewModel", "onFullscreenHandlerEnabled: show, t# " + i2);
                    createWindowDecoration(taskInfo, taskAppearedInfo.getLeash(), transaction, transaction2);
                }
            }
            transaction.apply();
            transaction2.apply();
        }
    }

    public final void updateSetupCompleteState() {
        boolean z = Settings.Secure.getInt(this.mContext.getContentResolver(), SettingsHelper.INDEX_USER_SETUP_COMPLETE, 0) == 1;
        if (CaptionGlobalState.USER_SETUP_COMPLETED != z) {
            CaptionGlobalState.USER_SETUP_COMPLETED = z;
            Slog.d("DesktopModeWindowDecorViewModel", "updateSetupCompleteState: enabled=" + z);
        }
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r7v22, types: [com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0] */
    public DesktopModeWindowDecorViewModel(Context context, ShellExecutor shellExecutor, Handler handler, Choreographer choreographer, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, ShellInit shellInit, ShellCommandHandler shellCommandHandler, IWindowManager iWindowManager, ShellTaskOrganizer shellTaskOrganizer, DesktopUserRepositories desktopUserRepositories, DisplayController displayController, ShellController shellController, DisplayInsetsController displayInsetsController, SyncTransactionQueue syncTransactionQueue, Transitions transitions, Optional<DesktopTasksController> optional, DesktopImmersiveController desktopImmersiveController, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MultiInstanceHelper multiInstanceHelper, DesktopModeWindowDecoration.Factory factory, InputMonitorFactory inputMonitorFactory, Supplier<SurfaceControl.Transaction> supplier, AppHeaderViewHolder.Factory factory2, AppHandleViewHolder.Factory factory3, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, SparseArray<DesktopModeWindowDecoration> sparseArray, InteractionJankMonitor interactionJankMonitor, Optional<DesktopTasksLimiter> optional2, AppHandleEducationController appHandleEducationController, AppToWebEducationController appToWebEducationController, AppHandleAndHeaderVisibilityHelper appHandleAndHeaderVisibilityHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, Optional<DesktopActivityOrientationChangeHandler> optional3, TaskPositionerFactory taskPositionerFactory, FocusTransitionObserver focusTransitionObserver, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, RecentsTransitionHandler recentsTransitionHandler, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, ShellExecutor shellExecutor3, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopTilingDecorViewModel desktopTilingDecorViewModel, MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController, CompatUIHandler compatUIHandler, DesksOrganizer desksOrganizer, DesktopState desktopState, DesktopConfig desktopConfig) {
        this.mEventReceiversByDisplay = new SparseArray();
        int i = 0;
        this.mExclusionRegionListener = new ExclusionRegionListenerImpl(this, i);
        this.mDragEventListener = new DragEventListenerImpl(this, i);
        this.mDragToDesktopAnimationStartBounds = new Rect();
        this.mDesktopModeKeyguardChangeListener = new DesktopModeKeyguardChangeListener();
        this.mTransitionToTaskInfo = new HashMap();
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mMainHandler = handler;
        this.mMainChoreographer = choreographer;
        this.mMainDispatcher = mainCoroutineDispatcher;
        this.mBgScope = coroutineScope;
        this.mBgExecutor = shellExecutor2;
        this.mActivityTaskManager = (ActivityTaskManager) context.getSystemService(ActivityTaskManager.class);
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mDesktopUserRepositories = desktopUserRepositories;
        this.mShellController = shellController;
        this.mDisplayController = displayController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mSyncQueue = syncTransactionQueue;
        this.mTransitions = transitions;
        DesktopTasksController desktopTasksController = optional.get();
        this.mDesktopTasksController = desktopTasksController;
        this.mDesktopImmersiveController = desktopImmersiveController;
        this.mMultiInstanceHelper = multiInstanceHelper;
        this.mShellCommandHandler = shellCommandHandler;
        this.mDesktopModeWindowDecorFactory = factory;
        this.mInputMonitorFactory = inputMonitorFactory;
        this.mTransactionFactory = supplier;
        this.mAppHeaderViewHolderFactory = factory2;
        this.mAppHandleViewHolderFactory = factory3;
        this.mRootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.mGenericLinksParser = appToWebGenericLinksParser;
        this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
        this.mWindowDecorByTaskId = sparseArray;
        context.getResources().getString(android.R.string.config_systemUi);
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mAppHandleAndHeaderVisibilityHelper = appHandleAndHeaderVisibilityHelper;
        this.mWindowDecorCaptionHandleRepository = windowDecorCaptionHandleRepository;
        this.mActivityOrientationChangeHandler = optional3;
        this.mAssistContentRequester = assistContentRequester;
        this.mWindowDecorViewHostSupplier = windowDecorViewHostSupplier;
        this.mCompatUI = compatUIHandler;
        final int i2 = 0;
        this.mOnDisplayChangingListener = new DisplayChangeController.OnDisplayChangingListener(this) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
            public final void onDisplayChange(final int i3, final int i4, final int i5, final DisplayAreaInfo displayAreaInfo, final WindowContainerTransaction windowContainerTransaction) {
                switch (i2) {
                    case 0:
                        int i6 = 0;
                        while (true) {
                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                            if (i6 >= desktopModeWindowDecorViewModel.mWindowDecorByTaskId.size()) {
                                break;
                            } else {
                                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.valueAt(i6);
                                if (desktopModeWindowDecoration != null) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                                    if (i3 == runningTaskInfo.displayId && runningTaskInfo.isFreeform() && i4 % 2 != i5 % 2) {
                                        Rect rect = new Rect(runningTaskInfo.configuration.windowConfiguration.getBounds());
                                        if (DragPositioningCallbackUtility.snapTaskBoundsIfNecessary(rect, desktopModeWindowDecoration.calculateValidDragArea())) {
                                            windowContainerTransaction.setBounds(runningTaskInfo.token, rect);
                                        }
                                    }
                                }
                                i6++;
                            }
                        }
                        break;
                    default:
                        this.f$0.forAllDecorations(new Consumer(i3, i4, i5, displayAreaInfo, windowContainerTransaction) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda27
                            public final /* synthetic */ int f$0;
                            public final /* synthetic */ int f$1;
                            public final /* synthetic */ int f$2;
                            public final /* synthetic */ WindowContainerTransaction f$4;

                            {
                                this.f$4 = windowContainerTransaction;
                            }

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                DisplayLayout displayLayout;
                                int i7;
                                View view;
                                int i8 = this.f$0;
                                int i9 = this.f$1;
                                int i10 = this.f$2;
                                WindowContainerTransaction windowContainerTransaction2 = this.f$4;
                                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) obj;
                                if (i8 == desktopModeWindowDecoration2.mDisplay.getDisplayId() && (displayLayout = desktopModeWindowDecoration2.mDisplayController.getDisplayLayout(desktopModeWindowDecoration2.mTaskInfo.displayId)) != null) {
                                    Rect rect2 = new Rect();
                                    if (desktopModeWindowDecoration2.mDecorWindowContext != null && (((view = desktopModeWindowDecoration2.mResult.mRootView) == null || !((WindowDecorLinearLayout) view).isAttachedToWindow() || displayLayout.mRotation != i10) && (i9 != -1 || i10 != -1))) {
                                        DisplayLayout displayLayout2 = new DisplayLayout(displayLayout);
                                        displayLayout2.rotateTo(desktopModeWindowDecoration2.mDecorWindowContext.getResources(), i10);
                                        displayLayout = displayLayout2;
                                    }
                                    boolean z = false;
                                    displayLayout.getStableBounds(rect2, false);
                                    DisplayCutout displayCutout = displayLayout.mCutout;
                                    boolean z2 = CoreRune.MW_CAPTION_FREEFORM_STASH;
                                    boolean z3 = true;
                                    if (z2 && desktopModeWindowDecoration2.mFreeformStashState.isStashed()) {
                                        if (CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY && desktopModeWindowDecoration2.mTaskInfo.getConfiguration().semDisplayDeviceType != 5) {
                                            z = true;
                                        }
                                        if (z) {
                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration2.mLastStableBounds, rect2, new Rect(desktopModeWindowDecoration2.mFreeformStashState.mLastFreeformBoundsBeforeStash), desktopModeWindowDecoration2.mFreeformStashState.mLastFreeformBoundsBeforeStash);
                                        } else {
                                            RotationUtils.rotateBounds(desktopModeWindowDecoration2.mFreeformStashState.mLastFreeformBoundsBeforeStash, desktopModeWindowDecoration2.mLastStableBounds, i9, i10);
                                        }
                                        Rect rect3 = new Rect(desktopModeWindowDecoration2.mTaskInfo.configuration.windowConfiguration.getBounds());
                                        if (z) {
                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration2.mLastStableBounds, rect2, new Rect(rect3), rect3);
                                        } else {
                                            RotationUtils.rotateBounds(rect3, desktopModeWindowDecoration2.mLastStableBounds, i9, i10);
                                        }
                                        if (displayCutout != null) {
                                            Rect safeInsets = displayCutout.getSafeInsets();
                                            rect2.set(rect2.left - safeInsets.left, rect2.top, rect2.right + safeInsets.right, rect2.bottom);
                                        }
                                        TaskMotionController taskMotionController = desktopModeWindowDecoration2.mTaskPositioner.getTaskMotionController();
                                        if (taskMotionController != null) {
                                            FreeformStashState freeformStashState = desktopModeWindowDecoration2.mFreeformStashState;
                                            int i11 = taskMotionController.mMinVisibleWidth;
                                            if (freeformStashState.mStashType != 0) {
                                                if (freeformStashState.isLeftStashed()) {
                                                    i7 = rect2.left + i11;
                                                    i11 = rect3.width();
                                                } else {
                                                    i7 = rect2.right;
                                                }
                                                rect3.offsetTo(i7 - i11, (int) (freeformStashState.mFreeformStashYFraction * rect2.height()));
                                            }
                                        }
                                        windowContainerTransaction2.setBounds(desktopModeWindowDecoration2.mTaskInfo.token, rect3);
                                        if (!rect3.isEmpty() && taskMotionController != null) {
                                            float height = desktopModeWindowDecoration2.mTaskPositioner.getTaskMotionController().mScaledFreeformHeight / rect3.height();
                                            if (desktopModeWindowDecoration2.mFreeformStashState.mScale != height) {
                                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                                windowContainerTransaction3.setChangeFreeformStashScale(desktopModeWindowDecoration2.mTaskInfo.token, height);
                                                desktopModeWindowDecoration2.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                                            }
                                        }
                                    } else {
                                        if (!z2 || !desktopModeWindowDecoration2.mFreeformStashState.isStashed()) {
                                            int deltaRotation = RotationUtils.deltaRotation(i9, i10);
                                            if (desktopModeWindowDecoration2.mTaskInfo.isFreeform()) {
                                                boolean z4 = i8 == desktopModeWindowDecoration2.mTaskInfo.getDisplayId();
                                                if (deltaRotation != 1 && deltaRotation != 3) {
                                                    z3 = false;
                                                }
                                                if (z4 & z3) {
                                                    WindowContainerToken windowContainerToken = desktopModeWindowDecoration2.mTaskInfo.token;
                                                    if (desktopModeWindowDecoration2.mIsTaskMaximized || desktopModeWindowDecoration2.mDesktopUserRepositories.getCurrent().isTaskInFullImmersiveState(desktopModeWindowDecoration2.mTaskInfo.taskId)) {
                                                        windowContainerTransaction2.setBounds(windowContainerToken, DesktopModeUtils.calculateMaximizeBounds(displayLayout, desktopModeWindowDecoration2.mTaskInfo));
                                                    } else {
                                                        Rect rect4 = new Rect(desktopModeWindowDecoration2.mTaskInfo.configuration.windowConfiguration.getBounds());
                                                        if (!CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY || desktopModeWindowDecoration2.mTaskInfo.getConfiguration().semDisplayDeviceType == 5) {
                                                            displayLayout.getDisplayBounds(desktopModeWindowDecoration2.mTmpRect);
                                                            Rect rect5 = desktopModeWindowDecoration2.mTmpRect;
                                                            rect5.set(0, 0, rect5.height(), desktopModeWindowDecoration2.mTmpRect.width());
                                                            RotationUtils.rotateBounds(rect4, desktopModeWindowDecoration2.mTmpRect, i9, i10);
                                                            RotationUtils.fitWithinBounds(rect4, rect2, 48, 32);
                                                            int i12 = rect2.top - rect4.top;
                                                            if (i12 > 0) {
                                                                rect4.offset(0, i12);
                                                            }
                                                        } else {
                                                            DisplayLayout displayLayout3 = new DisplayLayout(displayLayout);
                                                            Context context2 = desktopModeWindowDecoration2.mDecorWindowContext;
                                                            if (context2 != null) {
                                                                displayLayout3.rotateTo(context2.getResources(), i10);
                                                            } else {
                                                                Context displayContext = desktopModeWindowDecoration2.mDisplayController.getDisplayContext(desktopModeWindowDecoration2.mTaskInfo.displayId);
                                                                if (displayContext == null) {
                                                                    displayContext = desktopModeWindowDecoration2.mContext;
                                                                }
                                                                displayLayout3.rotateTo(displayContext.getResources(), i10);
                                                            }
                                                            displayLayout3.getDisplayBounds(desktopModeWindowDecoration2.mTmpRect2);
                                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration2.mTmpRect, desktopModeWindowDecoration2.mTmpRect2, rect4, rect4);
                                                        }
                                                        windowContainerTransaction2.setBounds(windowContainerToken, rect4);
                                                        windowContainerTransaction2.addChangeTransitFlags(windowContainerToken, 4);
                                                        FreeformAdjustImeController freeformAdjustImeController = desktopModeWindowDecoration2.mFreeformAdjustImeController;
                                                        if (freeformAdjustImeController.mIsAdjusted) {
                                                            Slog.d("FreeformAdjustImeController", "onFreeformTaskRotated: new" + rect4 + ", origin=" + freeformAdjustImeController.mOriginBounds);
                                                            freeformAdjustImeController.resetState();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (displayCutout != null) {
                                            Rect safeInsets2 = displayCutout.getSafeInsets();
                                            rect2.set(rect2.left - safeInsets2.left, rect2.top, rect2.right + safeInsets2.right, rect2.bottom);
                                        }
                                    }
                                    desktopModeWindowDecoration2.mLastStableBounds.set(rect2);
                                }
                            }
                        });
                        break;
                }
            }
        };
        this.mTaskPositionerFactory = taskPositionerFactory;
        this.mFocusTransitionObserver = focusTransitionObserver;
        this.mDesktopModeEventLogger = desktopModeEventLogger;
        this.mDesktopModeUiEventLogger = desktopModeUiEventLogger;
        this.mTaskResourceLoader = windowDecorTaskResourceLoader;
        this.mRecentsTransitionHandler = recentsTransitionHandler;
        this.mDesktopModeCompatPolicy = desktopModeCompatPolicy;
        this.mDesktopTilingDecorViewModel = desktopTilingDecorViewModel;
        desktopTasksController.snapEventHandler = this;
        this.mMultiDisplayDragMoveIndicatorController = multiDisplayDragMoveIndicatorController;
        this.mLatencyTracker = LatencyTracker.getInstance(context);
        this.mDesksOrganizer = desksOrganizer;
        this.mDesktopState = desktopState;
        this.mDesktopConfig = desktopConfig;
        this.mGestureExclusionTracker = new WindowDecorationGestureExclusionTracker(context, iWindowManager, displayController, shellExecutor, shellInit, new Function2() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Region region = (Region) obj2;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
                int intValue = ((Integer) obj).intValue();
                int size = desktopModeWindowDecorViewModel.mWindowDecorByTaskId.size();
                for (int i3 = 0; i3 < size; i3++) {
                    DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.valueAt(i3);
                    if (desktopModeWindowDecoration.mTaskInfo.displayId == intValue && (!CoreRune.MW_CAPTION || !DesktopModeWindowDecoration.isAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder))) {
                        desktopModeWindowDecoration.relayout(desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mHasGlobalFocus, region);
                    }
                }
                return Unit.INSTANCE;
            }
        });
        shellInit.addInitCallback(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda2(this, 0), this);
        this.mMultiTaskingHeaderViewHolderFactory = new MultiTaskingHeaderViewHolder.Factory();
        this.mNSController = naturalSwitchingDropTargetController;
        this.mAnimExecutor = shellExecutor3;
        final int i3 = 1;
        this.mRotationController = new DisplayChangeController.OnDisplayChangingListener(this) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
            public final void onDisplayChange(final int i32, final int i4, final int i5, final DisplayAreaInfo displayAreaInfo, final WindowContainerTransaction windowContainerTransaction) {
                switch (i3) {
                    case 0:
                        int i6 = 0;
                        while (true) {
                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                            if (i6 >= desktopModeWindowDecorViewModel.mWindowDecorByTaskId.size()) {
                                break;
                            } else {
                                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.valueAt(i6);
                                if (desktopModeWindowDecoration != null) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                                    if (i32 == runningTaskInfo.displayId && runningTaskInfo.isFreeform() && i4 % 2 != i5 % 2) {
                                        Rect rect = new Rect(runningTaskInfo.configuration.windowConfiguration.getBounds());
                                        if (DragPositioningCallbackUtility.snapTaskBoundsIfNecessary(rect, desktopModeWindowDecoration.calculateValidDragArea())) {
                                            windowContainerTransaction.setBounds(runningTaskInfo.token, rect);
                                        }
                                    }
                                }
                                i6++;
                            }
                        }
                        break;
                    default:
                        this.f$0.forAllDecorations(new Consumer(i32, i4, i5, displayAreaInfo, windowContainerTransaction) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda27
                            public final /* synthetic */ int f$0;
                            public final /* synthetic */ int f$1;
                            public final /* synthetic */ int f$2;
                            public final /* synthetic */ WindowContainerTransaction f$4;

                            {
                                this.f$4 = windowContainerTransaction;
                            }

                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                DisplayLayout displayLayout;
                                int i7;
                                View view;
                                int i8 = this.f$0;
                                int i9 = this.f$1;
                                int i10 = this.f$2;
                                WindowContainerTransaction windowContainerTransaction2 = this.f$4;
                                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) obj;
                                if (i8 == desktopModeWindowDecoration2.mDisplay.getDisplayId() && (displayLayout = desktopModeWindowDecoration2.mDisplayController.getDisplayLayout(desktopModeWindowDecoration2.mTaskInfo.displayId)) != null) {
                                    Rect rect2 = new Rect();
                                    if (desktopModeWindowDecoration2.mDecorWindowContext != null && (((view = desktopModeWindowDecoration2.mResult.mRootView) == null || !((WindowDecorLinearLayout) view).isAttachedToWindow() || displayLayout.mRotation != i10) && (i9 != -1 || i10 != -1))) {
                                        DisplayLayout displayLayout2 = new DisplayLayout(displayLayout);
                                        displayLayout2.rotateTo(desktopModeWindowDecoration2.mDecorWindowContext.getResources(), i10);
                                        displayLayout = displayLayout2;
                                    }
                                    boolean z = false;
                                    displayLayout.getStableBounds(rect2, false);
                                    DisplayCutout displayCutout = displayLayout.mCutout;
                                    boolean z2 = CoreRune.MW_CAPTION_FREEFORM_STASH;
                                    boolean z3 = true;
                                    if (z2 && desktopModeWindowDecoration2.mFreeformStashState.isStashed()) {
                                        if (CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY && desktopModeWindowDecoration2.mTaskInfo.getConfiguration().semDisplayDeviceType != 5) {
                                            z = true;
                                        }
                                        if (z) {
                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration2.mLastStableBounds, rect2, new Rect(desktopModeWindowDecoration2.mFreeformStashState.mLastFreeformBoundsBeforeStash), desktopModeWindowDecoration2.mFreeformStashState.mLastFreeformBoundsBeforeStash);
                                        } else {
                                            RotationUtils.rotateBounds(desktopModeWindowDecoration2.mFreeformStashState.mLastFreeformBoundsBeforeStash, desktopModeWindowDecoration2.mLastStableBounds, i9, i10);
                                        }
                                        Rect rect3 = new Rect(desktopModeWindowDecoration2.mTaskInfo.configuration.windowConfiguration.getBounds());
                                        if (z) {
                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration2.mLastStableBounds, rect2, new Rect(rect3), rect3);
                                        } else {
                                            RotationUtils.rotateBounds(rect3, desktopModeWindowDecoration2.mLastStableBounds, i9, i10);
                                        }
                                        if (displayCutout != null) {
                                            Rect safeInsets = displayCutout.getSafeInsets();
                                            rect2.set(rect2.left - safeInsets.left, rect2.top, rect2.right + safeInsets.right, rect2.bottom);
                                        }
                                        TaskMotionController taskMotionController = desktopModeWindowDecoration2.mTaskPositioner.getTaskMotionController();
                                        if (taskMotionController != null) {
                                            FreeformStashState freeformStashState = desktopModeWindowDecoration2.mFreeformStashState;
                                            int i11 = taskMotionController.mMinVisibleWidth;
                                            if (freeformStashState.mStashType != 0) {
                                                if (freeformStashState.isLeftStashed()) {
                                                    i7 = rect2.left + i11;
                                                    i11 = rect3.width();
                                                } else {
                                                    i7 = rect2.right;
                                                }
                                                rect3.offsetTo(i7 - i11, (int) (freeformStashState.mFreeformStashYFraction * rect2.height()));
                                            }
                                        }
                                        windowContainerTransaction2.setBounds(desktopModeWindowDecoration2.mTaskInfo.token, rect3);
                                        if (!rect3.isEmpty() && taskMotionController != null) {
                                            float height = desktopModeWindowDecoration2.mTaskPositioner.getTaskMotionController().mScaledFreeformHeight / rect3.height();
                                            if (desktopModeWindowDecoration2.mFreeformStashState.mScale != height) {
                                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                                windowContainerTransaction3.setChangeFreeformStashScale(desktopModeWindowDecoration2.mTaskInfo.token, height);
                                                desktopModeWindowDecoration2.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                                            }
                                        }
                                    } else {
                                        if (!z2 || !desktopModeWindowDecoration2.mFreeformStashState.isStashed()) {
                                            int deltaRotation = RotationUtils.deltaRotation(i9, i10);
                                            if (desktopModeWindowDecoration2.mTaskInfo.isFreeform()) {
                                                boolean z4 = i8 == desktopModeWindowDecoration2.mTaskInfo.getDisplayId();
                                                if (deltaRotation != 1 && deltaRotation != 3) {
                                                    z3 = false;
                                                }
                                                if (z4 & z3) {
                                                    WindowContainerToken windowContainerToken = desktopModeWindowDecoration2.mTaskInfo.token;
                                                    if (desktopModeWindowDecoration2.mIsTaskMaximized || desktopModeWindowDecoration2.mDesktopUserRepositories.getCurrent().isTaskInFullImmersiveState(desktopModeWindowDecoration2.mTaskInfo.taskId)) {
                                                        windowContainerTransaction2.setBounds(windowContainerToken, DesktopModeUtils.calculateMaximizeBounds(displayLayout, desktopModeWindowDecoration2.mTaskInfo));
                                                    } else {
                                                        Rect rect4 = new Rect(desktopModeWindowDecoration2.mTaskInfo.configuration.windowConfiguration.getBounds());
                                                        if (!CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY || desktopModeWindowDecoration2.mTaskInfo.getConfiguration().semDisplayDeviceType == 5) {
                                                            displayLayout.getDisplayBounds(desktopModeWindowDecoration2.mTmpRect);
                                                            Rect rect5 = desktopModeWindowDecoration2.mTmpRect;
                                                            rect5.set(0, 0, rect5.height(), desktopModeWindowDecoration2.mTmpRect.width());
                                                            RotationUtils.rotateBounds(rect4, desktopModeWindowDecoration2.mTmpRect, i9, i10);
                                                            RotationUtils.fitWithinBounds(rect4, rect2, 48, 32);
                                                            int i12 = rect2.top - rect4.top;
                                                            if (i12 > 0) {
                                                                rect4.offset(0, i12);
                                                            }
                                                        } else {
                                                            DisplayLayout displayLayout3 = new DisplayLayout(displayLayout);
                                                            Context context2 = desktopModeWindowDecoration2.mDecorWindowContext;
                                                            if (context2 != null) {
                                                                displayLayout3.rotateTo(context2.getResources(), i10);
                                                            } else {
                                                                Context displayContext = desktopModeWindowDecoration2.mDisplayController.getDisplayContext(desktopModeWindowDecoration2.mTaskInfo.displayId);
                                                                if (displayContext == null) {
                                                                    displayContext = desktopModeWindowDecoration2.mContext;
                                                                }
                                                                displayLayout3.rotateTo(displayContext.getResources(), i10);
                                                            }
                                                            displayLayout3.getDisplayBounds(desktopModeWindowDecoration2.mTmpRect2);
                                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration2.mTmpRect, desktopModeWindowDecoration2.mTmpRect2, rect4, rect4);
                                                        }
                                                        windowContainerTransaction2.setBounds(windowContainerToken, rect4);
                                                        windowContainerTransaction2.addChangeTransitFlags(windowContainerToken, 4);
                                                        FreeformAdjustImeController freeformAdjustImeController = desktopModeWindowDecoration2.mFreeformAdjustImeController;
                                                        if (freeformAdjustImeController.mIsAdjusted) {
                                                            Slog.d("FreeformAdjustImeController", "onFreeformTaskRotated: new" + rect4 + ", origin=" + freeformAdjustImeController.mOriginBounds);
                                                            freeformAdjustImeController.resetState();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        if (displayCutout != null) {
                                            Rect safeInsets2 = displayCutout.getSafeInsets();
                                            rect2.set(rect2.left - safeInsets2.left, rect2.top, rect2.right + safeInsets2.right, rect2.bottom);
                                        }
                                    }
                                    desktopModeWindowDecoration2.mLastStableBounds.set(rect2);
                                }
                            }
                        });
                        break;
                }
            }
        };
        if (CoreRune.MW_CAPTION) {
            ((RootTaskDesksOrganizer) desktopTasksController.desksOrganizer).desktopModeWindowDecorViewModel = this;
        }
        if (CoreRune.MW_CAPTION_SPLIT_IMMERSIVE) {
            boolean z = MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
            ShellTaskOrganizer.MultiWindowCoreStateChangeListener multiWindowCoreStateChangeListener = new ShellTaskOrganizer.MultiWindowCoreStateChangeListener() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda4
                @Override // com.android.wm.shell.ShellTaskOrganizer.MultiWindowCoreStateChangeListener
                public final boolean onMultiWindowCoreStateChanged(int i4) {
                    DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
                    desktopModeWindowDecorViewModel.getClass();
                    if ((i4 & 512) == 0) {
                        return false;
                    }
                    boolean z2 = MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
                    desktopModeWindowDecorViewModel.forAllDecorations(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda25(1));
                    return false;
                }
            };
            shellTaskOrganizer.mMultiWindowCoreStateChangeListeners.remove(multiWindowCoreStateChangeListener);
            shellTaskOrganizer.mMultiWindowCoreStateChangeListeners.add(multiWindowCoreStateChangeListener);
        }
        if (CoreRune.MW_SA_LOGGING) {
            this.mCaptionButtonLogger = new MultiTaskingCaptionButtonLogger();
        }
    }
}
