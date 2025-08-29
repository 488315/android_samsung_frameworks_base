package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayTopology;
import android.hardware.input.InputManager;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Size;
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
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.Toast;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.DisplayAreaInfo;
import android.window.RemoteTransition;
import android.window.TaskAppearedInfo;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.ColorKt;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.protolog.ProtoLog;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.R;
import com.android.systemui.doze.AODUi$$ExternalSyntheticLambda0;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.apptoweb.AppToWebGenericLinksParser;
import com.android.wm.shell.apptoweb.AssistContentRequester;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.DragHintToFullscreen;
import com.android.wm.shell.common.DragHintToFullscreenManager;
import com.android.wm.shell.common.MultiDisplayDragMoveIndicatorController;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.split.DividerHandleView;
import com.android.wm.shell.common.split.DividerRoundedCorner;
import com.android.wm.shell.compatui.api.CompatUIHandler;
import com.android.wm.shell.compatui.impl.CompatUIRequests;
import com.android.wm.shell.desktopmode.DesktopActivityOrientationChangeHandler;
import com.android.wm.shell.desktopmode.DesktopImmersiveController;
import com.android.wm.shell.desktopmode.DesktopModeEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUiEventLogger;
import com.android.wm.shell.desktopmode.DesktopModeUtils;
import com.android.wm.shell.desktopmode.DesktopModeVisualIndicator;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda3;
import com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1;
import com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1;
import com.android.wm.shell.desktopmode.DesktopTasksController$onDragPositioningEnd$1;
import com.android.wm.shell.desktopmode.DesktopTasksLimiter;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.DesktopWallpaperActivity;
import com.android.wm.shell.desktopmode.DragToDesktopTransitionHandler;
import com.android.wm.shell.desktopmode.EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.desktopmode.ReturnToDragStartAnimator;
import com.android.wm.shell.desktopmode.WindowDecorCaptionHandleRepository;
import com.android.wm.shell.desktopmode.common.ToggleTaskSizeInteraction;
import com.android.wm.shell.desktopmode.common.ToggleTaskSizeUtilsKt$WhenMappings;
import com.android.wm.shell.desktopmode.education.AppHandleEducationController;
import com.android.wm.shell.desktopmode.education.AppToWebEducationController;
import com.android.wm.shell.desktopmode.multidesks.DesksOrganizer;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.freeform.FreeformTaskListener;
import com.android.wm.shell.freeform.FreeformTaskTransitionStarter;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.recents.RecentsTransitionStateListener;
import com.android.wm.shell.shared.FocusTransitionListener;
import com.android.wm.shell.shared.desktopmode.DesktopConfig;
import com.android.wm.shell.shared.desktopmode.DesktopConfigImpl;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shortcut.ShortcutController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecoration$$ExternalSyntheticLambda25;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.DragPositioningCallbackUtility;
import com.android.wm.shell.windowdecor.DragResizeInputListener;
import com.android.wm.shell.windowdecor.MultiTaskingCaptionButtonLogger;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.common.AppHandleAndHeaderVisibilityHelper;
import com.android.wm.shell.windowdecor.common.DecorThemeUtil;
import com.android.wm.shell.windowdecor.common.WindowDecorTaskResourceLoader;
import com.android.wm.shell.windowdecor.common.WindowDecorationGestureExclusionTracker;
import com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier;
import com.android.wm.shell.windowdecor.extension.InsetsStateKt;
import com.android.wm.shell.windowdecor.extension.TaskInfoKt;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDecorViewModel;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingDividerWindowManager;
import com.android.wm.shell.windowdecor.tiling.DesktopTilingWindowDecoration;
import com.android.wm.shell.windowdecor.tiling.SnapEventHandler;
import com.android.wm.shell.windowdecor.tiling.TilingDividerView;
import com.android.wm.shell.windowdecor.viewholder.AppHandleViewHolder;
import com.android.wm.shell.windowdecor.viewholder.AppHeaderViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHandleViewHolder;
import com.android.wm.shell.windowdecor.viewholder.MultiTaskingHeaderViewHolder;
import com.android.wm.shell.windowdecor.viewholder.WindowDecorationViewHolder;
import com.android.wm.shell.windowdecor.widget.CaptionAnimationButton;
import com.android.wm.shell.windowdecor.widget.CaptionButton;
import com.android.wm.shell.windowdecor.widget.OutlineView;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.MainCoroutineDispatcher;

/* loaded from: classes3.dex */
public class DesktopModeWindowDecorViewModel implements WindowDecorViewModel, FocusTransitionListener, SnapEventHandler {
    public static int mDesktopDisabledFlags;
    public final Optional mActivityOrientationChangeHandler;
    public final ActivityTaskManager mActivityTaskManager;
    public final ShellExecutor mAnimExecutor;
    public final AppHandleAndHeaderVisibilityHelper mAppHandleAndHeaderVisibilityHelper;
    public final AppHandleViewHolder.Factory mAppHandleViewHolderFactory;
    public final AppHeaderViewHolder.Factory mAppHeaderViewHolderFactory;
    public final Map mAppearedTaskList;
    public final AssistContentRequester mAssistContentRequester;
    public final ShellExecutor mBgExecutor;
    public final CoroutineScope mBgScope;
    public final MultiTaskingCaptionButtonLogger mCaptionButtonLogger;
    public final CompatUIHandler mCompatUI;
    public final Context mContext;
    public final DecorViewModelState mDecorViewModelState;
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
    public final DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0 mDisplayChangingController;
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
    public boolean mIsMultiWindowDisabled;
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
    public boolean mNotifyOfMoveTask;
    public final DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0 mOnDisplayChangingListener;
    public final Lazy mPipOptionalLazy;
    public final RecentsTransitionHandler mRecentsTransitionHandler;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
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

    /* renamed from: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$1, reason: invalid class name */
    public class AnonymousClass1 {
        public AnonymousClass1() {
        }

        public final void onDesktopDisabledFlagsChangedOnDefaultDisplay(int i) {
            DesktopModeWindowDecorViewModel.mDesktopDisabledFlags = i;
            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
            desktopModeWindowDecorViewModel.getClass();
            boolean z = false;
            boolean z2 = ((i & 8) == 0 && (i & 1) == 0) ? false : true;
            if (desktopModeWindowDecorViewModel.mIsMultiWindowDisabled != z2) {
                desktopModeWindowDecorViewModel.mIsMultiWindowDisabled = z2;
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                ArrayList arrayList = (ArrayList) desktopModeWindowDecorViewModel.mTaskOrganizer.getVisibleTaskAppearedInfos(-1);
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) obj;
                    ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
                    int i3 = taskInfo.taskId;
                    if (desktopModeWindowDecorViewModel.mIsMultiWindowDisabled) {
                        Slog.d("DesktopModeWindowDecorViewModel", "updateDisabledWindowDecoration: disabled, t# " + i3);
                        desktopModeWindowDecorViewModel.destroyWindowDecoration(taskInfo);
                    } else if (taskAppearedInfo.getLeash().isValid() && taskInfo.topActivity != null && desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(i3) == null && desktopModeWindowDecorViewModel.shouldShowWindowDecor(taskInfo)) {
                        Slog.d("DesktopModeWindowDecorViewModel", "updateDisabledWindowDecoration: enabled, t# " + i3);
                        desktopModeWindowDecorViewModel.createWindowDecoration(taskInfo, taskAppearedInfo.getLeash(), transaction, transaction2);
                        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(i3);
                        if (desktopModeWindowDecoration != null) {
                            desktopModeWindowDecoration.setCaptionVisibility(desktopModeWindowDecoration.getHandleRootView(), desktopModeWindowDecoration.mIsCaptionVisible);
                        }
                        z = true;
                    }
                }
                if (z) {
                    transaction.apply();
                    transaction2.apply();
                }
            }
        }
    }

    public class DecorViewModelState {
        public boolean mInTransition;

        public DecorViewModelState(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel) {
        }
    }

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
                            Rect rectCalculateMaximizeBounds = DesktopModeUtils.calculateMaximizeBounds(displayLayout, desktopModeWindowDecoration.mTaskInfo);
                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                            windowContainerTransaction.setBounds(desktopModeWindowDecoration.mTaskInfo.token, rectCalculateMaximizeBounds);
                            desktopModeWindowDecoration.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                        }
                        boolean z = desktopModeWindowDecoration.mIsStatusBarVisible;
                        boolean zIsVisible = InsetsStateKt.isVisible(WindowInsets.Type.statusBars(), insetsState);
                        desktopModeWindowDecoration.mIsStatusBarVisible = zIsVisible;
                        if (z != zIsVisible) {
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

    public class DesktopModeOnTaskRepositionAnimationListener {
        public /* synthetic */ DesktopModeOnTaskRepositionAnimationListener(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i) {
            this();
        }

        private DesktopModeOnTaskRepositionAnimationListener() {
        }
    }

    public class DesktopModeOnTaskResizeAnimationListener {
        public /* synthetic */ DesktopModeOnTaskResizeAnimationListener(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i) {
            this();
        }

        public final void onAnimationEnd(int i) {
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(i);
            if (desktopModeWindowDecoration == null) {
                return;
            }
            ResizeVeil resizeVeil = desktopModeWindowDecoration.mResizeVeil;
            if (resizeVeil != null) {
                resizeVeil.hideVeil();
            }
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
                int iKeyAt = desktopModeWindowDecorViewModel.mWindowDecorByTaskId.keyAt(i2);
                ((HashSet) this.mAnimatingTaskIds).add(Integer.valueOf(iKeyAt));
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.get(iKeyAt);
                if (desktopModeWindowDecoration2 != null) {
                    desktopModeWindowDecoration2.mIsRecentsTransitionRunning = true;
                }
            }
        }

        private DesktopModeRecentsTransitionStateListener() {
            this.mAnimatingTaskIds = new HashSet();
        }
    }

    public class DesktopModeTouchEventListener extends GestureDetector.SimpleOnGestureListener implements View.OnClickListener, View.OnTouchListener, View.OnLongClickListener, View.OnGenericMotionListener, DragDetector.MotionEventHandler {
        public final AccessibilityManager mAccessibilityManager;
        public final int mCurrentUserId;
        public final int mDisplayId;
        public boolean mDragInterrupted;
        public boolean mDragMaximizeTaskAllowed;
        public int mDragPointerId;
        public final DragPositioningCallback mDragPositioningCallback;
        public final FreeformCaptionTouchState mFreeformCaptionTouchState;
        public final GestureDetector mGestureDetector;
        public final DragDetector mHandleDragDetector;
        public final DragDetector mHeaderDragDetector;
        public final Point mInputDownPoint;
        public boolean mIsButtonLongPressed;
        public boolean mIsCustomHeaderGesture;
        public boolean mIsDragging;
        public boolean mIsNaturalSwitching;
        public boolean mIsResizeGesture;
        public boolean mIsRestoreAnimRunning;
        public boolean mLongClickDisabled;
        public MotionEvent mMotionEvent;
        public final Rect mOnDragStartInitialBounds;
        public View mTargetView;
        public final int mTaskId;
        public final TaskPositioner mTaskPositioner;
        public final WindowContainerToken mTaskToken;
        public Point mTouchedPosition;

        public /* synthetic */ DesktopModeTouchEventListener(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, ActivityManager.RunningTaskInfo runningTaskInfo, TaskPositioner taskPositioner, TaskPositioner taskPositioner2) {
            this(runningTaskInfo, (DragPositioningCallback) taskPositioner, taskPositioner2);
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00bb, code lost:
        
            if (r7 != 3) goto L124;
         */
        /* JADX WARN: Removed duplicated region for block: B:198:0x049d  */
        /* JADX WARN: Removed duplicated region for block: B:200:0x04a8  */
        /* JADX WARN: Removed duplicated region for block: B:207:0x04bf  */
        /* JADX WARN: Removed duplicated region for block: B:209:0x04c3  */
        /* JADX WARN: Removed duplicated region for block: B:260:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:41:0x00a9 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:46:0x00b5  */
        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean handleMotionEvent(View view, MotionEvent motionEvent) throws Resources.NotFoundException {
            boolean z;
            int actionMasked;
            DesktopModeVisualIndicator visualIndicator;
            int i;
            boolean z2;
            DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1;
            DesktopModeVisualIndicator.DragStartState dragStartState;
            DesktopModeVisualIndicator.IndicatorType indicatorTypeUpdateVisualIndicator;
            DesktopModeVisualIndicator visualIndicator2;
            DisplayLayout displayLayout;
            DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver;
            MotionEvent motionEvent2 = motionEvent;
            int i2 = 2;
            int i3 = 1;
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
            if (!DesktopModeWindowDecorViewModel.this.mDesktopState.canEnterDesktopModeOrShowAppHandle() || runningTaskInfo.isFreeform()) {
                int id = view.getId();
                if (!this.mGestureDetector.onTouchEvent(motionEvent2)) {
                    if (id != R.id.close_window && id != R.id.maximize_window && id != R.id.open_menu_button && id != R.id.minimize_window) {
                        if (CoreRune.MW_CAPTION) {
                            DesktopModeWindowDecorViewModel.this.getClass();
                            if ((view instanceof CaptionButton) || (view instanceof CaptionAnimationButton)) {
                            }
                            boolean zIsTaskInFullImmersiveState = DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getProfile(runningTaskInfo.userId).isTaskInFullImmersiveState(runningTaskInfo.taskId);
                            boolean z3 = !desktopModeWindowDecoration.isDecorHandleState() && id == R.id.caption_handle;
                            actionMasked = motionEvent2.getActionMasked();
                            if (actionMasked == 0) {
                                if (!zIsTaskInFullImmersiveState) {
                                    this.mDragPointerId = motionEvent2.getPointerId(0);
                                    Rect rectOnDragPositioningStart = this.mDragPositioningCallback.onDragPositioningStart(0, motionEvent2.getRawX(0), motionEvent2.getRawY(0), motionEvent2.getDisplayId());
                                    updateDragStatus(desktopModeWindowDecoration, motionEvent2);
                                    this.mOnDragStartInitialBounds.set(rectOnDragPositioningStart);
                                }
                                if (CoreRune.MW_CAPTION_FREEFORM_MOTION) {
                                    FreeformCaptionTouchState freeformCaptionTouchState = this.mFreeformCaptionTouchState;
                                    VelocityTracker velocityTracker = freeformCaptionTouchState.mVelocityTracker;
                                    if (velocityTracker == null) {
                                        freeformCaptionTouchState.mVelocityTracker = VelocityTracker.obtain();
                                    } else {
                                        velocityTracker.clear();
                                    }
                                    this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent2);
                                }
                                if (CoreRune.MW_CAPTION_HANDLE && desktopModeWindowDecoration.isDecorHandleState() && z3) {
                                    return false;
                                }
                                DesktopTasksController desktopTasksController = DesktopModeWindowDecorViewModel.this.mDesktopTasksController;
                                desktopTasksController.getClass();
                                DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
                                int i4 = runningTaskInfo.displayId;
                                companion.getClass();
                                if (DesktopStateImpl.Companion.inDesktopWindowing(i4) && runningTaskInfo.displayId == 0) {
                                    Context displayContext = desktopTasksController.displayController.getDisplayContext(runningTaskInfo.getDisplayId());
                                    if (displayContext == null) {
                                        RecordingInputConnection$$ExternalSyntheticOutline0.m(runningTaskInfo.getDisplayId(), "displayContext is null for ", "DesktopTasksController");
                                    }
                                    DragHintToFullscreenManager dragHintToFullscreenManager = desktopTasksController.dragHintToFullscreenManager;
                                    if (dragHintToFullscreenManager != null) {
                                        dragHintToFullscreenManager.removeWindow(true);
                                        desktopTasksController.dragHintToFullscreenManager = null;
                                    }
                                    if (displayContext == null) {
                                        displayContext = desktopTasksController.context;
                                    }
                                    DragHintToFullscreenManager dragHintToFullscreenManager2 = new DragHintToFullscreenManager(displayContext);
                                    desktopTasksController.dragHintToFullscreenManager = dragHintToFullscreenManager2;
                                    if (dragHintToFullscreenManager2.mView != null) {
                                        dragHintToFullscreenManager2.removeWindow(true);
                                        dragHintToFullscreenManager2.removeView(dragHintToFullscreenManager2.mView);
                                        dragHintToFullscreenManager2.mView = null;
                                    }
                                    DragHintToFullscreen dragHintToFullscreen = (DragHintToFullscreen) LayoutInflater.from(dragHintToFullscreenManager2.getContext()).inflate(R.layout.drag_hint_to_fullscreen, (ViewGroup) dragHintToFullscreenManager2, false);
                                    dragHintToFullscreenManager2.mView = dragHintToFullscreen;
                                    dragHintToFullscreenManager2.addView(dragHintToFullscreen);
                                    Log.i("DragHintToFullscreenManager", "createOrUpdateWrapper  isAttachedToWindow=" + dragHintToFullscreenManager2.isAttachedToWindow());
                                    if (dragHintToFullscreenManager2.isAttachedToWindow() || dragHintToFullscreenManager2.mAddWindowRequested) {
                                        dragHintToFullscreenManager2.mWindowManager.updateViewLayout(dragHintToFullscreenManager2, dragHintToFullscreenManager2.generateLayoutParams());
                                    } else {
                                        dragHintToFullscreenManager2.setVisibility(4);
                                        dragHintToFullscreenManager2.mNeedToRemoveWindow = false;
                                        try {
                                            dragHintToFullscreenManager2.mWindowManager.addView(dragHintToFullscreenManager2, dragHintToFullscreenManager2.generateLayoutParams());
                                            dragHintToFullscreenManager2.mAddWindowRequested = true;
                                        } catch (IllegalStateException e) {
                                            Log.e("DragHintToFullscreenManager", "createOrUpdateWrapper: failed to addView, " + dragHintToFullscreenManager2 + ", e=" + e);
                                            dragHintToFullscreenManager2.mWindowManager.updateViewLayout(dragHintToFullscreenManager2, dragHintToFullscreenManager2.generateLayoutParams());
                                        }
                                    }
                                }
                                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = DesktopModeWindowDecorViewModel.this;
                                desktopModeWindowDecorViewModel.mDragStartDisplayId = runningTaskInfo.displayId;
                                desktopModeWindowDecorViewModel.mNotifyOfMoveTask = false;
                                boolean z4 = desktopModeWindowDecoration.mIsTaskMaximized;
                                this.mDragMaximizeTaskAllowed = z4;
                                if (z4) {
                                    this.mTaskPositioner.updateRestoreAnimationMotionEvent(motionEvent2);
                                    this.mInputDownPoint.set((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY());
                                    this.mIsRestoreAnimRunning = false;
                                }
                                return !z;
                            }
                            if (actionMasked != 1) {
                                if (actionMasked == 2) {
                                    DragResizeInputListener dragResizeInputListener = desktopModeWindowDecoration.mDragResizeListener;
                                    if ((dragResizeInputListener == null || (taskResizeInputEventReceiver = dragResizeInputListener.mInputEventReceiver) == null || !taskResizeInputEventReceiver.mShouldHandleEvents) && !zIsTaskInFullImmersiveState) {
                                        if (this.mDragMaximizeTaskAllowed) {
                                            int scaledTouchSlop = ViewConfiguration.get(DesktopModeWindowDecorViewModel.this.mContext).getScaledTouchSlop();
                                            int iFindPointerIndex = motionEvent2.findPointerIndex(this.mDragPointerId);
                                            if (Math.hypot(motionEvent2.getRawX(iFindPointerIndex) - this.mInputDownPoint.x, motionEvent2.getRawY(iFindPointerIndex) - this.mInputDownPoint.y) > scaledTouchSlop && !this.mIsRestoreAnimRunning) {
                                                Rect rect = new Rect((Rect) DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getCurrent().boundsBeforeMaximizeByTaskId.removeReturnOld(runningTaskInfo.taskId));
                                                Rect bounds = runningTaskInfo.configuration.windowConfiguration.getBounds();
                                                if ((rect.isEmpty() || (bounds.width() == rect.width() && bounds.height() == rect.height())) && (displayLayout = DesktopModeWindowDecorViewModel.this.mDisplayController.getDisplayLayout(runningTaskInfo.displayId)) != null) {
                                                    rect.set(DesktopModeUtils.calculateInitialBounds$default(displayLayout, runningTaskInfo, 0, null, 28));
                                                }
                                                rect.offsetTo((int) (motionEvent2.getRawX() - (rect.width() / 2.0f)), (int) motionEvent2.getRawY());
                                                this.mTaskPositioner.playMaximizedTaskRestoreAnimation(rect, new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda3(this, i3));
                                                this.mIsRestoreAnimRunning = true;
                                                return true;
                                            }
                                            if (this.mIsRestoreAnimRunning) {
                                                this.mTaskPositioner.updateRestoreAnimationMotionEvent(motionEvent2);
                                                return true;
                                            }
                                        } else {
                                            if (CoreRune.MW_CAPTION_FREEFORM_MOTION) {
                                                this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent2);
                                            }
                                            if (!DesktopModeWindowDecorViewModel.this.mNotifyOfMoveTask) {
                                                MultiWindowManager.getInstance().notifyDragTaskStarted();
                                                DesktopModeWindowDecorViewModel.this.mNotifyOfMoveTask = true;
                                            }
                                            desktopModeWindowDecoration.closeMaximizeMenu();
                                            if (motionEvent2.findPointerIndex(this.mDragPointerId) == -1) {
                                                this.mDragPointerId = motionEvent2.getPointerId(0);
                                            }
                                            int iFindPointerIndex2 = motionEvent2.findPointerIndex(this.mDragPointerId);
                                            Rect rectOnDragPositioningMove = this.mDragPositioningCallback.onDragPositioningMove(motionEvent2.getRawX(iFindPointerIndex2), motionEvent2.getRawY(iFindPointerIndex2), motionEvent2.getDisplayId());
                                            DesktopTasksController desktopTasksController2 = DesktopModeWindowDecorViewModel.this.mDesktopTasksController;
                                            SurfaceControl surfaceControl = desktopModeWindowDecoration.mTaskSurface;
                                            float rawX = motionEvent2.getRawX(iFindPointerIndex2);
                                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = DesktopModeWindowDecorViewModel.this;
                                            boolean z5 = desktopModeWindowDecorViewModel2.mIsKeyguardShowing;
                                            boolean z6 = desktopModeWindowDecorViewModel2.mDragStartDisplayId == motionEvent2.getDisplayId();
                                            desktopTasksController2.getClass();
                                            if (runningTaskInfo.getWindowingMode() == 5) {
                                                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel3 = desktopTasksController2.snapEventHandler;
                                                if (desktopModeWindowDecorViewModel3 == null) {
                                                    desktopModeWindowDecorViewModel3 = null;
                                                }
                                                desktopModeWindowDecorViewModel3.removeTaskIfTiled(runningTaskInfo.displayId, runningTaskInfo.taskId);
                                                DesktopModeVisualIndicator.IndicatorType indicatorTypeUpdateVisualIndicator2 = desktopTasksController2.updateVisualIndicator(runningTaskInfo, surfaceControl, rawX, rectOnDragPositioningMove.top, DesktopModeVisualIndicator.DragStartState.FROM_FREEFORM, z5, z6, true);
                                                DesktopStateImpl.Companion companion2 = DesktopStateImpl.Companion;
                                                int i5 = runningTaskInfo.displayId;
                                                companion2.getClass();
                                                if (DesktopStateImpl.Companion.inDesktopWindowing(i5) && (visualIndicator2 = desktopTasksController2.getVisualIndicator()) != null) {
                                                    if (indicatorTypeUpdateVisualIndicator2 != DesktopModeVisualIndicator.IndicatorType.NO_INDICATOR || visualIndicator2.fullscreenTransitionHeight <= rectOnDragPositioningMove.top) {
                                                        DragHintToFullscreenManager dragHintToFullscreenManager3 = desktopTasksController2.dragHintToFullscreenManager;
                                                        if (dragHintToFullscreenManager3 != null) {
                                                            dragHintToFullscreenManager3.hide(null);
                                                        }
                                                    } else {
                                                        DragHintToFullscreenManager dragHintToFullscreenManager4 = desktopTasksController2.dragHintToFullscreenManager;
                                                        if (dragHintToFullscreenManager4 != null) {
                                                            dragHintToFullscreenManager4.setVisibility(0);
                                                            DragHintToFullscreen dragHintToFullscreen2 = dragHintToFullscreenManager4.mView;
                                                            if (!dragHintToFullscreen2.mVisible) {
                                                                dragHintToFullscreen2.mVisible = true;
                                                                Log.i("DragHintToFullscreen", "show");
                                                                dragHintToFullscreen2.startAnimation(dragHintToFullscreen2.mShowAnimation);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (this.mIsDragging || !rectOnDragPositioningMove.equals(this.mOnDragStartInitialBounds)) {
                                                updateDragStatus(desktopModeWindowDecoration, motionEvent2);
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                            if (this.mIsDragging) {
                                DesktopModeWindowDecorViewModel.this.mDesktopModeUiEventLogger.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MOVE_BY_HEADER_DRAG);
                                if (motionEvent2.findPointerIndex(this.mDragPointerId) == -1) {
                                    this.mDragPointerId = motionEvent2.getPointerId(0);
                                }
                                boolean z7 = CoreRune.MW_CAPTION_FREEFORM_MOTION;
                                if (z7) {
                                    this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent2);
                                    FreeformCaptionTouchState freeformCaptionTouchState2 = this.mFreeformCaptionTouchState;
                                    VelocityTracker velocityTracker2 = freeformCaptionTouchState2.mVelocityTracker;
                                    if (velocityTracker2 != null) {
                                        velocityTracker2.computeCurrentVelocity(1000, freeformCaptionTouchState2.mMaximumFlingVelocity);
                                        freeformCaptionTouchState2.mVelocity.set(freeformCaptionTouchState2.mVelocityTracker.getXVelocity(), freeformCaptionTouchState2.mVelocityTracker.getYVelocity());
                                    }
                                    this.mTaskPositioner.setFreeformCaptionTouchState(this.mFreeformCaptionTouchState);
                                }
                                int i6 = runningTaskInfo.displayId;
                                DesktopStateImpl.Companion.getClass();
                                boolean z8 = (DesktopStateImpl.Companion.inDesktopWindowing(i6) && (dragStartState = DesktopModeVisualIndicator.DragStartState.getDragStartState(desktopModeWindowDecoration.mTaskInfo)) != null && ((indicatorTypeUpdateVisualIndicator = DesktopModeWindowDecorViewModel.this.mDesktopTasksController.updateVisualIndicator(desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mTaskSurface, motionEvent2.getRawX(), motionEvent2.getRawY(), dragStartState, false, true, false)) == DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_LEFT_INDICATOR || indicatorTypeUpdateVisualIndicator == DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_RIGHT_INDICATOR)) ? false : true;
                                int iFindPointerIndex3 = motionEvent2.findPointerIndex(this.mDragPointerId);
                                Rect rectOnDragPositioningEnd = this.mDragPositioningCallback.onDragPositioningEnd(motionEvent2.getRawX(iFindPointerIndex3), motionEvent2.getRawY(iFindPointerIndex3), motionEvent2.getDisplayId(), z8);
                                DesktopTasksController desktopTasksController3 = DesktopModeWindowDecorViewModel.this.mDesktopTasksController;
                                SurfaceControl surfaceControl2 = desktopModeWindowDecoration.mTaskSurface;
                                PointF pointF = new PointF(motionEvent2.getRawX(iFindPointerIndex3), motionEvent2.getRawY(iFindPointerIndex3));
                                Rect rectCalculateValidDragArea = desktopModeWindowDecoration.calculateValidDragArea();
                                Rect rect2 = new Rect(this.mOnDragStartInitialBounds);
                                boolean z9 = DesktopModeWindowDecorViewModel.this.mIsKeyguardShowing;
                                boolean zIsStashed = desktopModeWindowDecoration.mFreeformStashState.isStashed();
                                boolean z10 = DesktopModeWindowDecorViewModel.this.mDragStartDisplayId == motionEvent2.getDisplayId();
                                desktopTasksController3.getClass();
                                if (runningTaskInfo.configuration.windowConfiguration.getWindowingMode() == 5 && (visualIndicator = desktopTasksController3.getVisualIndicator()) != null) {
                                    i = -1;
                                    DesktopModeVisualIndicator.IndicatorType indicatorTypeUpdateIndicatorType = visualIndicator.updateIndicatorType(new PointF(pointF.x, rectOnDragPositioningEnd.top), runningTaskInfo, z9, zIsStashed, z10, true);
                                    DragHintToFullscreenManager dragHintToFullscreenManager5 = desktopTasksController3.dragHintToFullscreenManager;
                                    if (dragHintToFullscreenManager5 != null) {
                                        dragHintToFullscreenManager5.hide(new DesktopTasksController$onDragPositioningEnd$1(desktopTasksController3));
                                    }
                                    int i7 = DesktopTasksController.WhenMappings.$EnumSwitchMapping$1[indicatorTypeUpdateIndicatorType.ordinal()];
                                    DesktopModeUiEventLogger desktopModeUiEventLogger = desktopTasksController3.desktopModeUiEventLogger;
                                    switch (i7) {
                                        case 1:
                                            z2 = false;
                                            DesktopConfigImpl desktopConfigImpl = (DesktopConfigImpl) desktopTasksController3.desktopConfig;
                                            desktopConfigImpl.getClass();
                                            if (!(!DesktopExperienceFlags.ENABLE_DRAG_TO_MAXIMIZE.isTrue() ? false : SystemProperties.getBoolean("persist.wm.debug.enable_drag_to_maximize", desktopConfigImpl.context.getResources().getBoolean(android.R.bool.config_earcFeatureEnabled_allowed)))) {
                                                desktopModeUiEventLogger.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HEADER_DRAG_TO_FULL_SCREEN);
                                                desktopTasksController3.moveToFullscreenWithAnimation(runningTaskInfo, new Point(rectOnDragPositioningEnd.left, rectOnDragPositioningEnd.top), DesktopModeTransitionSource.TASK_DRAG, rectOnDragPositioningEnd);
                                            } else if (DesktopModeUtils.isTaskMaximized(runningTaskInfo, desktopTasksController3.displayController)) {
                                                Rect bounds2 = runningTaskInfo.configuration.windowConfiguration.getBounds();
                                                if (!Intrinsics.areEqual(bounds2, rectOnDragPositioningEnd)) {
                                                    int i8 = runningTaskInfo.taskId;
                                                    int i9 = ReturnToDragStartAnimator.$r8$clinit;
                                                    desktopTasksController3.returnToDragStartAnimator.start(i8, surfaceControl2, rectOnDragPositioningEnd, bounds2, null);
                                                }
                                            } else {
                                                ToggleTaskSizeInteraction.Direction direction = ToggleTaskSizeInteraction.Direction.MAXIMIZE;
                                                ToggleTaskSizeInteraction.Source source = ToggleTaskSizeInteraction.Source.HEADER_DRAG_TO_TOP;
                                                DesktopModeEventLogger.Companion.getClass();
                                                desktopTasksController3.toggleDesktopTaskSize(runningTaskInfo, new ToggleTaskSizeInteraction(direction, source, DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent2), rectOnDragPositioningEnd));
                                            }
                                            desktopTasksController3.releaseVisualIndicator();
                                            desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController3.taskbarDesktopTaskListener;
                                            if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                                                desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(desktopTasksController3.doesAnyTaskRequireTaskbarRounding(runningTaskInfo.displayId, null));
                                            }
                                            if (z7) {
                                                FreeformCaptionTouchState freeformCaptionTouchState3 = this.mFreeformCaptionTouchState;
                                                VelocityTracker velocityTracker3 = freeformCaptionTouchState3.mVelocityTracker;
                                                if (velocityTracker3 != null) {
                                                    velocityTracker3.recycle();
                                                    freeformCaptionTouchState3.mVelocityTracker = null;
                                                }
                                                this.mTaskPositioner.setFreeformCaptionTouchState(null);
                                            }
                                            DesktopModeWindowDecorViewModel.this.mDragStartDisplayId = i;
                                            if (!z) {
                                                return z2;
                                            }
                                            updateDragStatus(desktopModeWindowDecoration, motionEvent2);
                                            return true;
                                        case 2:
                                            z2 = false;
                                            desktopModeUiEventLogger.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HEADER_DRAG_TO_TILE_TO_LEFT);
                                            motionEvent2 = motionEvent;
                                            desktopTasksController3.handleSnapResizingTaskOnDrag(runningTaskInfo, DesktopTasksController.SnapPosition.LEFT, surfaceControl2, rectOnDragPositioningEnd, rect2, motionEvent2);
                                            desktopTasksController3.releaseVisualIndicator();
                                            desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController3.taskbarDesktopTaskListener;
                                            if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                                            }
                                            if (z7) {
                                            }
                                            DesktopModeWindowDecorViewModel.this.mDragStartDisplayId = i;
                                            if (!z) {
                                            }
                                            break;
                                        case 3:
                                            desktopModeUiEventLogger.log(runningTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HEADER_DRAG_TO_TILE_TO_RIGHT);
                                            z2 = false;
                                            desktopTasksController3.handleSnapResizingTaskOnDrag(runningTaskInfo, DesktopTasksController.SnapPosition.RIGHT, surfaceControl2, rectOnDragPositioningEnd, rect2, motionEvent2);
                                            motionEvent2 = motionEvent;
                                            desktopTasksController3.releaseVisualIndicator();
                                            desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController3.taskbarDesktopTaskListener;
                                            if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                                            }
                                            if (z7) {
                                            }
                                            DesktopModeWindowDecorViewModel.this.mDragStartDisplayId = i;
                                            if (!z) {
                                            }
                                            break;
                                        case 4:
                                        case 5:
                                        case 6:
                                            Rect rect3 = new Rect(rectOnDragPositioningEnd);
                                            DragPositioningCallbackUtility.snapTaskBoundsIfNecessary(rect3, rectCalculateValidDragArea);
                                            if (rect3.equals(rect2)) {
                                                desktopTasksController3.releaseVisualIndicator();
                                                int i10 = runningTaskInfo.taskId;
                                                int i11 = ReturnToDragStartAnimator.$r8$clinit;
                                                desktopTasksController3.returnToDragStartAnimator.start(i10, surfaceControl2, rectOnDragPositioningEnd, rect2, null);
                                                break;
                                            } else {
                                                int displayId = motionEvent2.getDisplayId();
                                                DisplayAreaInfo displayAreaInfo = desktopTasksController3.rootTaskDisplayAreaOrganizer.getDisplayAreaInfo(displayId);
                                                if (displayId == runningTaskInfo.getDisplayId() || displayAreaInfo == null) {
                                                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                                    windowContainerTransaction.setBounds(runningTaskInfo.token, rect3);
                                                    if (CoreRune.MW_CAPTION_FREEFORM) {
                                                        desktopTasksController3.shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                                                    } else {
                                                        desktopTasksController3.transitions.startTransition(6, windowContainerTransaction, null);
                                                    }
                                                } else {
                                                    desktopTasksController3.moveToDisplay(runningTaskInfo, displayId, rect3, desktopTasksController3.dragToDisplayTransitionHandler);
                                                }
                                                desktopTasksController3.releaseVisualIndicator();
                                                z2 = false;
                                                desktopTasksController3.releaseVisualIndicator();
                                                desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController3.taskbarDesktopTaskListener;
                                                if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                                                }
                                                if (z7) {
                                                }
                                                DesktopModeWindowDecorViewModel.this.mDragStartDisplayId = i;
                                                if (!z) {
                                                }
                                            }
                                            break;
                                        case 7:
                                            throw new IllegalArgumentException("Should not be receiving TO_DESKTOP_INDICATOR for a freeform task.");
                                        case 8:
                                            z2 = false;
                                            desktopTasksController3.releaseVisualIndicator();
                                            desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController3.taskbarDesktopTaskListener;
                                            if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                                            }
                                            if (z7) {
                                            }
                                            DesktopModeWindowDecorViewModel.this.mDragStartDisplayId = i;
                                            if (!z) {
                                            }
                                            break;
                                        default:
                                            throw new NoWhenBranchMatchedException();
                                    }
                                } else {
                                    i = -1;
                                }
                                z2 = false;
                                if (z7) {
                                }
                                DesktopModeWindowDecorViewModel.this.mDragStartDisplayId = i;
                                if (!z) {
                                }
                            }
                        }
                        z = false;
                        boolean zIsTaskInFullImmersiveState2 = DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getProfile(runningTaskInfo.userId).isTaskInFullImmersiveState(runningTaskInfo.taskId);
                        if (desktopModeWindowDecoration.isDecorHandleState()) {
                        }
                        actionMasked = motionEvent2.getActionMasked();
                        if (actionMasked == 0) {
                        }
                    }
                    z = true;
                    boolean zIsTaskInFullImmersiveState22 = DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getProfile(runningTaskInfo.userId).isTaskInFullImmersiveState(runningTaskInfo.taskId);
                    if (desktopModeWindowDecoration.isDecorHandleState()) {
                    }
                    actionMasked = motionEvent2.getActionMasked();
                    if (actionMasked == 0) {
                    }
                }
                return true;
            }
            if (view.getId() == R.id.caption_handle) {
                DesktopModeWindowDecorViewModel.this.handleCaptionThroughStatusBar(motionEvent2, desktopModeWindowDecoration, new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda28(i2, this, desktopModeWindowDecoration));
                boolean z11 = this.mIsDragging;
                updateDragStatus(desktopModeWindowDecoration, motionEvent2);
                if (motionEvent2.getActionMasked() != 1 && motionEvent2.getActionMasked() != 3) {
                    i3 = 0;
                }
                if (z11 && i3 != 0) {
                    view.setPressed(false);
                }
                return z11;
            }
            return false;
        }

        public final void moveTaskToFront(ActivityManager.RunningTaskInfo runningTaskInfo) throws Resources.NotFoundException {
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
        /* JADX WARN: Type inference failed for: r3v42 */
        /* JADX WARN: Type inference failed for: r3v43 */
        /* JADX WARN: Type inference failed for: r3v5 */
        /* JADX WARN: Type inference failed for: r3v6 */
        /* JADX WARN: Type inference failed for: r3v7 */
        /* JADX WARN: Type inference failed for: r3v8 */
        /* JADX WARN: Type inference failed for: r3v9 */
        @Override // android.view.View.OnClickListener
        public final void onClick(View view) throws IllegalAccessException, Resources.NotFoundException, IllegalArgumentException, InvocationTargetException {
            String packageName;
            int i;
            MultiTaskingHandleViewHolder multiTaskingHandleViewHolderAsMultiTaskingAppHandle;
            ?? r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            r3 = 0;
            if (this.mIsDragging) {
                this.mIsDragging = false;
                return;
            }
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (desktopModeWindowDecoration != null) {
                boolean z = CoreRune.MW_CAPTION_POPUP;
                if (z && this.mIsNaturalSwitching && view.getId() == R.id.caption_handle) {
                    return;
                }
                boolean z2 = CoreRune.MW_CAPTION_HANDLE;
                if (z2 && desktopModeWindowDecoration.isDecorHandleState() && view.getId() == R.id.caption_handle && ((multiTaskingHandleViewHolderAsMultiTaskingAppHandle = DesktopModeWindowDecoration.asMultiTaskingAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder)) == null || !multiTaskingHandleViewHolderAsMultiTaskingAppHandle.handleTouchEnabled)) {
                    return;
                }
                int i2 = desktopModeWindowDecoration.mTaskInfo.displayId;
                DesktopStateImpl.Companion.getClass();
                boolean zInDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(i2);
                int windowingMode = desktopModeWindowDecoration.mTaskInfo.getWindowingMode();
                ComponentName componentName = desktopModeWindowDecoration.mTaskInfo.topActivity;
                if (componentName != null) {
                    packageName = componentName.getPackageName();
                } else {
                    packageName = "";
                    Log.w("DesktopModeWindowDecorViewModel", "Unknown activity for SA logging. taskId=" + this.mTaskId);
                }
                int id = view.getId();
                if (id == R.id.close_window) {
                    if (!DesktopModeWindowDecorViewModel.this.isTaskInSplitScreen(this.mTaskId)) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        if (DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getProfile(this.mCurrentUserId).getDeskIdForTask(this.mTaskId) == null) {
                            DesktopModeWindowDecorViewModel.this.mTaskOperations.closeTask(this.mTaskToken, windowContainerTransaction, false);
                        } else {
                            DesktopTasksController desktopTasksController = DesktopModeWindowDecorViewModel.this.mDesktopTasksController;
                            int i3 = this.mDisplayId;
                            ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                            desktopTasksController.getClass();
                            int i4 = runningTaskInfo.taskId;
                            Integer deskIdForTask = desktopTasksController.taskRepository.getDeskIdForTask(i4);
                            if (deskIdForTask == null && DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                                throw new IllegalStateException(("Did not find desk for task: " + i4).toString());
                            }
                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = desktopTasksController.snapEventHandler;
                            if (desktopModeWindowDecorViewModel == null) {
                                desktopModeWindowDecorViewModel = null;
                            }
                            desktopModeWindowDecorViewModel.removeTaskIfTiled(i3, i4);
                            DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default = DesktopTasksController.performDesktopExitCleanUp$default(desktopTasksController, windowContainerTransaction, deskIdForTask, i3, desktopTasksController.willExitDesktop(runningTaskInfo.taskId, false), true, 32);
                            desktopTasksController.taskRepository.addClosingTask(i3, deskIdForTask, i4);
                            DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 = desktopTasksController.taskbarDesktopTaskListener;
                            if (desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 != null) {
                                desktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1.onTaskbarCornerRoundingUpdate(desktopTasksController.doesAnyTaskRequireTaskbarRounding(i3, Integer.valueOf(i4)));
                            }
                            DesktopImmersiveController.ExitResult.Exit exitAsExit = desktopTasksController.desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction, runningTaskInfo, DesktopImmersiveController.ExitReason.CLOSED).asExit();
                            Function1 function1 = exitAsExit != null ? exitAsExit.runOnTransitionStart : null;
                            IBinder iBinderCloseTask = DesktopModeWindowDecorViewModel.this.mTaskOperations.closeTask(this.mTaskToken, windowContainerTransaction, true);
                            if (iBinderCloseTask != null) {
                                if (function1 != null) {
                                    function1.mo781invoke(iBinderCloseTask);
                                }
                                if (desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default != null) {
                                    desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp$default.mo781invoke(iBinderCloseTask);
                                }
                                Unit unit = Unit.INSTANCE;
                            }
                        }
                    } else if (z2) {
                        DesktopModeWindowDecorViewModel.this.mSplitScreenController.dismissSplitTask(desktopModeWindowDecoration.mTaskInfo.token);
                    } else {
                        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = DesktopModeWindowDecorViewModel.this;
                        SplitScreenController splitScreenController = desktopModeWindowDecorViewModel2.mSplitScreenController;
                        splitScreenController.moveTaskToFullscreen(desktopModeWindowDecorViewModel2.mSplitScreenController.getTaskInfo(splitScreenController.getSplitPosition(this.mTaskId) == 1 ? 0 : 1).taskId);
                    }
                    if (z && desktopModeWindowDecoration.isHandleMenuActive()) {
                        WindowDecorationViewHolder windowDecorationViewHolder = desktopModeWindowDecoration.mWindowDecorViewHolder;
                        if (windowDecorationViewHolder != null) {
                            windowDecorationViewHolder.onHandleMenuClosed();
                        }
                        desktopModeWindowDecoration.mHandleMenu.closeMenuPopupImmediately();
                        desktopModeWindowDecoration.mHandleMenu = null;
                        boolean z3 = ((DesktopStateImpl) desktopModeWindowDecoration.mDesktopState).canEnterDesktopMode;
                    }
                    r3 = CoreRune.MW_SA_LOGGING;
                    sendTalkBackFeedback(R.string.sec_decor_button_operation_closed);
                } else if (id == R.id.back_button) {
                    TaskOperations taskOperations = DesktopModeWindowDecorViewModel.this.mTaskOperations;
                    int i5 = this.mDisplayId;
                    taskOperations.sendBackEvent(0, i5);
                    taskOperations.sendBackEvent(1, i5);
                } else if (id == R.id.caption_handle || id == R.id.open_menu_button || id == R.id.more_window) {
                    if (id == R.id.caption_handle && !desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                        DesktopModeWindowDecorViewModel.this.mDesktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_TAP);
                    }
                    if (!desktopModeWindowDecoration.isHandleMenuActive()) {
                        moveTaskToFront(desktopModeWindowDecoration.mTaskInfo);
                        final DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel3 = DesktopModeWindowDecorViewModel.this;
                        final int i6 = this.mTaskId;
                        final DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel3.mWindowDecorByTaskId.get(i6);
                        desktopModeWindowDecorViewModel3.mBgExecutor.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda7
                            @Override // java.lang.Runnable
                            public final void run() {
                                final DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel4 = desktopModeWindowDecorViewModel3;
                                final DesktopModeWindowDecoration desktopModeWindowDecoration3 = desktopModeWindowDecoration2;
                                final int i7 = i6;
                                desktopModeWindowDecorViewModel4.getClass();
                                final ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration3.mTaskInfo;
                                IActivityTaskManager service = ActivityTaskManager.getService();
                                try {
                                    int i8 = ComponentUtils.$r8$clinit;
                                    final String packageName2 = ComponentUtils.getPackageName(((TaskInfo) runningTaskInfo2).baseIntent);
                                    final int size = service.getRecentTasks(Integer.MAX_VALUE, 1, runningTaskInfo2.userId).getList().stream().filter(new Predicate() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda30
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj) {
                                            ActivityManager.RunningTaskInfo runningTaskInfo3 = runningTaskInfo2;
                                            String str = packageName2;
                                            ActivityManager.RecentTaskInfo recentTaskInfo = (ActivityManager.RecentTaskInfo) obj;
                                            if (recentTaskInfo.taskId == runningTaskInfo3.taskId) {
                                                return false;
                                            }
                                            int i9 = ComponentUtils.$r8$clinit;
                                            return str != null && str.equals(ComponentUtils.getPackageName(((TaskInfo) recentTaskInfo).baseIntent));
                                        }
                                    }).toList().size();
                                    desktopModeWindowDecorViewModel4.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda27
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            ImageView imageView;
                                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel5 = desktopModeWindowDecorViewModel4;
                                            int i9 = i7;
                                            DesktopModeWindowDecoration desktopModeWindowDecoration4 = desktopModeWindowDecoration3;
                                            int i10 = size;
                                            desktopModeWindowDecorViewModel5.getClass();
                                            if (CoreRune.MW_CAPTION && desktopModeWindowDecorViewModel5.mWindowDecorByTaskId.get(i9) == null) {
                                                Slog.w("DesktopModeWindowDecorViewModel", "openHandleMenu: failed, already destroyed, " + desktopModeWindowDecoration4);
                                                return;
                                            }
                                            desktopModeWindowDecoration4.mMinimumInstancesFound = i10 >= 2;
                                            if (CoreRune.MW_CAPTION_POPUP) {
                                                desktopModeWindowDecoration4.onAssistContentReceived(null);
                                            } else {
                                                final AssistContentRequester assistContentRequester = desktopModeWindowDecoration4.mAssistContentRequester;
                                                final int i11 = desktopModeWindowDecoration4.mTaskInfo.taskId;
                                                final DesktopModeWindowDecoration$$ExternalSyntheticLambda25 desktopModeWindowDecoration$$ExternalSyntheticLambda25 = new DesktopModeWindowDecoration$$ExternalSyntheticLambda25(desktopModeWindowDecoration4);
                                                assistContentRequester.systemInteractionExecutor.execute(new Runnable() { // from class: com.android.wm.shell.apptoweb.AssistContentRequester$requestAssistContent$1
                                                    @Override // java.lang.Runnable
                                                    public final void run() {
                                                        try {
                                                            IActivityTaskManager iActivityTaskManager = assistContentRequester.activityTaskManager;
                                                            AssistContentRequester.AssistDataReceiver assistDataReceiver = new AssistContentRequester.AssistDataReceiver(desktopModeWindowDecoration$$ExternalSyntheticLambda25, assistContentRequester);
                                                            int i12 = i11;
                                                            AssistContentRequester assistContentRequester2 = assistContentRequester;
                                                            if (iActivityTaskManager.requestAssistDataForTask(assistDataReceiver, i12, assistContentRequester2.packageName, assistContentRequester2.attributionTag, false)) {
                                                                return;
                                                            }
                                                            AssistContentRequester assistContentRequester3 = assistContentRequester;
                                                            final AssistContentRequester.Callback callback = desktopModeWindowDecoration$$ExternalSyntheticLambda25;
                                                            assistContentRequester3.callBackExecutor.execute(new Runnable() { // from class: com.android.wm.shell.apptoweb.AssistContentRequester$requestAssistContent$1.1
                                                                @Override // java.lang.Runnable
                                                                public final void run() {
                                                                    ((DesktopModeWindowDecoration$$ExternalSyntheticLambda25) callback).f$0.onAssistContentReceived(null);
                                                                }
                                                            });
                                                        } catch (RemoteException e) {
                                                            Slog.e("AssistContentRequester", "Requesting assist content failed for task: " + i11, e);
                                                        }
                                                    }
                                                });
                                            }
                                            if (CoreRune.MW_CAPTION_DESKTOP_RESTART && desktopModeWindowDecoration4.mInDesktopWindowing && DesktopModeWindowDecoration.isAppHeader(desktopModeWindowDecoration4.mWindowDecorViewHolder) && (imageView = DesktopModeWindowDecoration.asMultiTaskingAppHeader(desktopModeWindowDecoration4.mWindowDecorViewHolder).captionButtonPolicy.mDesktopNotification) != null) {
                                                imageView.setVisibility(8);
                                            }
                                        }
                                    });
                                } catch (RemoteException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                    if (CoreRune.MW_SA_LOGGING) {
                        r3 = id == R.id.caption_handle ? 4 : 0;
                        if (id == R.id.open_menu_button) {
                            r3 = 11;
                        }
                    }
                } else if (id == R.id.maximize_window || id == R.id.remove_from_workspace) {
                    if (CoreRune.MW_CAPTION) {
                        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel4 = DesktopModeWindowDecorViewModel.this;
                        int i7 = this.mTaskId;
                        DesktopModeWindowDecoration desktopModeWindowDecoration3 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel4.mWindowDecorByTaskId.get(i7);
                        if (desktopModeWindowDecoration3 != null) {
                            desktopModeWindowDecoration3.closeHandleMenu();
                            if (desktopModeWindowDecorViewModel4.isTaskInSplitScreen(i7)) {
                                desktopModeWindowDecorViewModel4.mSplitScreenController.maximizeSplitTask(desktopModeWindowDecoration3.mTaskInfo.token);
                            } else if (DesktopStateImpl.Companion.inDesktopWindowing(desktopModeWindowDecoration3.mTaskInfo.displayId)) {
                                desktopModeWindowDecorViewModel4.mDesktopTasksController.moveToFullscreen(i7, DesktopModeTransitionSource.APP_HANDLE_MENU_BUTTON);
                            } else {
                                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                windowContainerTransaction2.setWindowingMode(desktopModeWindowDecoration3.mTaskInfo.token, 0).setBounds(desktopModeWindowDecoration3.mTaskInfo.token, new Rect());
                                boolean z4 = CoreRune.DW_SHELL_CHANGE_TRANSITION;
                                Transitions transitions = desktopModeWindowDecorViewModel4.mTransitions;
                                if (z4) {
                                    transitions.mChangeTransitProvider.startChangeTransition(windowContainerTransaction2.setChangeTransitMode(desktopModeWindowDecoration3.mTaskInfo.token, 1, "moveStandardFreeformToFullscreenWithAnimation"));
                                } else {
                                    transitions.mChangeTransitProvider.startChangeTransition(windowContainerTransaction2);
                                }
                            }
                        }
                        if (CoreRune.MW_SA_LOGGING) {
                            r3 = id == R.id.maximize_window ? 3 : 0;
                            if (id == R.id.remove_from_workspace) {
                                r3 = 12;
                            }
                        }
                        sendTalkBackFeedback(R.string.sec_decor_button_operation_maximized);
                    } else {
                        DesktopRepository profile = DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getProfile(desktopModeWindowDecoration.mTaskInfo.userId);
                        if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && profile.isTaskInFullImmersiveState(desktopModeWindowDecoration.mTaskInfo.taskId)) {
                            DesktopModeWindowDecorViewModel.this.onEnterOrExitImmersive(desktopModeWindowDecoration.mTaskInfo);
                        } else {
                            DesktopModeWindowDecorViewModel.this.onToggleSizeInteraction(desktopModeWindowDecoration.mTaskInfo.taskId, ToggleTaskSizeInteraction.AmbiguousSource.HEADER_BUTTON, this.mMotionEvent);
                        }
                    }
                } else {
                    if (id != R.id.minimize_window) {
                        if (CoreRune.MW_CAPTION) {
                            if (id == R.id.split_window) {
                                if (windowingMode == 1) {
                                    TaskOperations taskOperations2 = DesktopModeWindowDecorViewModel.this.mTaskOperations;
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration.mTaskInfo;
                                    SplitScreenController splitScreenController2 = taskOperations2.mSplitScreenController;
                                    if (splitScreenController2 != null) {
                                        if (runningTaskInfo2.isTopTransparentActivity && splitScreenController2.isSplitScreenVisible()) {
                                            Log.d("TaskOperations", "dismiss split by transparent activity");
                                            taskOperations2.mSplitScreenController.exitSplitScreen(-1, 14);
                                            taskOperations2.mSplitScreenController.setSplitInvisible();
                                        }
                                        taskOperations2.mSplitScreenController.enterSplitScreen$1();
                                    }
                                } else {
                                    DesktopModeWindowDecorViewModel.this.mTaskOperations.moveFreeformToSplit(desktopModeWindowDecoration.mTaskInfo, 0);
                                }
                                if (DesktopModeWindowDecorViewModel.this.mSplitScreenController.isLeftRightSplit()) {
                                    sendTalkBackFeedback(R.string.accessibility_action_divider_left_50);
                                } else {
                                    sendTalkBackFeedback(R.string.accessibility_action_divider_top_50);
                                }
                                if (CoreRune.MW_SA_LOGGING) {
                                    i = 5;
                                }
                            } else {
                                if (id == R.id.freeform_window) {
                                    DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel5 = DesktopModeWindowDecorViewModel.this;
                                    int i8 = this.mTaskId;
                                    DesktopModeWindowDecoration desktopModeWindowDecoration4 = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel5.mWindowDecorByTaskId.get(i8);
                                    if (desktopModeWindowDecoration4 != null) {
                                        if (desktopModeWindowDecorViewModel5.isTaskInSplitScreen(i8)) {
                                            desktopModeWindowDecorViewModel5.mSplitScreenController.moveSplitToFreeform(desktopModeWindowDecoration4.mTaskInfo.token, null, false);
                                        } else {
                                            WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                            windowContainerTransaction3.setWindowingMode(desktopModeWindowDecoration4.mTaskInfo.token, 5);
                                            boolean z5 = CoreRune.DW_SHELL_CHANGE_TRANSITION;
                                            Transitions transitions2 = desktopModeWindowDecorViewModel5.mTransitions;
                                            if (z5) {
                                                transitions2.mChangeTransitProvider.startChangeTransition(windowContainerTransaction3.setChangeTransitMode(desktopModeWindowDecoration4.mTaskInfo.token, 1, "changeTaskToFreeform"));
                                            } else {
                                                transitions2.mChangeTransitProvider.startChangeTransition(windowContainerTransaction3);
                                            }
                                            desktopModeWindowDecoration4.closeHandleMenu();
                                        }
                                    }
                                    if (desktopModeWindowDecoration.mTaskInfo.topActivityInfo != null) {
                                        view.announceForAccessibility(DesktopModeWindowDecorViewModel.this.mContext.getResources().getString(R.string.sec_decor_button_text_opened_popup_view, desktopModeWindowDecoration.mTaskInfo.topActivityInfo.loadLabel(DesktopModeWindowDecorViewModel.this.mContext.getPackageManager())));
                                    }
                                    if (CoreRune.MW_SA_LOGGING) {
                                        i = 2;
                                    }
                                } else if (id == R.id.toggle_freeform_window) {
                                    DesktopRepository profile2 = DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getProfile(desktopModeWindowDecoration.mTaskInfo.userId);
                                    r3 = CoreRune.MW_SA_LOGGING ? desktopModeWindowDecoration.mIsTaskMaximized ? 10 : 3 : 0;
                                    if (DesktopModeFlags.ENABLE_FULLY_IMMERSIVE_IN_DESKTOP.isTrue() && profile2.isTaskInFullImmersiveState(desktopModeWindowDecoration.mTaskInfo.taskId)) {
                                        DesktopModeWindowDecorViewModel.this.onEnterOrExitImmersive(desktopModeWindowDecoration.mTaskInfo);
                                    } else {
                                        DesktopModeWindowDecorViewModel.this.onToggleSizeInteraction(desktopModeWindowDecoration.mTaskInfo.taskId, ToggleTaskSizeInteraction.AmbiguousSource.HEADER_BUTTON, this.mMotionEvent);
                                    }
                                } else if (id == R.id.apps_window) {
                                    desktopModeWindowDecoration.closeHandleMenu();
                                    if (this.mTouchedPosition != null) {
                                        DesktopModeWindowDecorViewModel.this.mMainHandler.post(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda28(i, this, MultiWindowUtils.getExternalAppsServiceIntent(this.mTaskId, new int[]{r2.x, r2.y})));
                                    } else {
                                        DesktopModeWindowDecorViewModel.this.mMainHandler.post(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda28(i, this, MultiWindowUtils.getExternalAppsServiceIntent(this.mTaskId, new int[]{(int) view.getPivotX(), (int) view.getPivotY()})));
                                    }
                                    if (CoreRune.MW_SA_LOGGING) {
                                        r3 = 7;
                                    }
                                } else {
                                    boolean z6 = CoreRune.MW_CAPTION_FREEFORM_TYPE;
                                    if (z6 && (id == R.id.update_caption_to_handle || id == R.id.update_handle_to_caption)) {
                                        i = id == R.id.update_caption_to_handle ? 0 : 1;
                                        ShellTaskOrganizer shellTaskOrganizer = DesktopModeWindowDecorViewModel.this.mTaskOrganizer;
                                        ActivityManager.RunningTaskInfo runningTaskInfo3 = shellTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                                        if (z6 && runningTaskInfo3 != null && runningTaskInfo3.isFreeform()) {
                                            synchronized (shellTaskOrganizer.mLock) {
                                                try {
                                                    if (shellTaskOrganizer.mTaskListeners.contains(-5)) {
                                                        FreeformTaskListener freeformTaskListener = (FreeformTaskListener) shellTaskOrganizer.mTaskListeners.get(-5);
                                                        if (CoreRune.MW_FREEFORM_HEADER_TYPE_SA_LOGGING) {
                                                            CoreSaLogger.logForAdvanced("2012", i == 0 ? "From header to handle" : "From handle to header");
                                                        }
                                                        freeformTaskListener.resizeTasksByFreeformCaptionType(i);
                                                    }
                                                } finally {
                                                }
                                            }
                                        }
                                        if (i == 0) {
                                            sendTalkBackFeedback(R.string.sec_decor_button_operation_unpinned);
                                        } else {
                                            sendTalkBackFeedback(R.string.sec_decor_button_operation_pinned);
                                        }
                                    } else if (id == R.id.external_display) {
                                        DesktopModeWindowDecorViewModel.this.mDesktopTasksController.toggleExternalDisplay(desktopModeWindowDecoration.mTaskInfo.taskId);
                                        if (CoreRune.MW_SA_LOGGING) {
                                            if (zInDesktopWindowing) {
                                                CoreSaLogger.logForAdvanced("3106", packageName, desktopModeWindowDecoration.mTaskInfo.displayId == 0 ? 2 : 1);
                                            } else {
                                                r3 = 8;
                                            }
                                        }
                                    } else if (CoreRune.MW_CAPTION_DESKTOP && (id == R.id.snap_left || id == R.id.snap_right)) {
                                        if (CoreRune.MW_SA_LOGGING) {
                                            CoreSaLogger.logForAdvanced(id == R.id.snap_left ? "3101" : "3102", packageName, desktopModeWindowDecoration.mTaskInfo.displayId != 0 ? 1 : 2);
                                        }
                                        DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel6 = DesktopModeWindowDecorViewModel.this;
                                        int i9 = desktopModeWindowDecoration.mTaskInfo.taskId;
                                        boolean z7 = id == R.id.snap_left;
                                        MotionEvent motionEvent = this.mMotionEvent;
                                        DesktopModeEventLogger.Companion.getClass();
                                        desktopModeWindowDecorViewModel6.onSnapResize(i9, z7, DesktopModeEventLogger.Companion.getInputMethodFromMotionEvent(motionEvent), true);
                                    } else if (CoreRune.MW_CAPTION_DESKTOP_IMMERSIVE && id == R.id.toggle_immersive_window) {
                                        DesktopModeWindowDecorViewModel.this.onEnterOrExitImmersive(desktopModeWindowDecoration.mTaskInfo);
                                    } else if (CoreRune.MW_CAPTION_DESKTOP_RESTART && id == R.id.restart_app) {
                                        desktopModeWindowDecoration.closeHandleMenu();
                                        DesktopModeWindowDecorViewModel.this.mCompatUI.sendCompatUIRequest(new CompatUIRequests.DisplayCompatRestartTask(desktopModeWindowDecoration.mTaskInfo.taskId));
                                    }
                                }
                            }
                        }
                        if (CoreRune.MW_SA_LOGGING || i == 0) {
                        }
                        MultiTaskingCaptionButtonLogger multiTaskingCaptionButtonLogger = DesktopModeWindowDecorViewModel.this.mCaptionButtonLogger;
                        multiTaskingCaptionButtonLogger.getClass();
                        multiTaskingCaptionButtonLogger.invokeLog((Method) MultiTaskingCaptionButtonLogger.sLoggerMethods.get(new MultiTaskingCaptionButtonLogger.CaptionLoggerPairKey(multiTaskingCaptionButtonLogger, i, 2, zInDesktopWindowing, windowingMode)), packageName);
                        return;
                    }
                    if (!CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION || zInDesktopWindowing) {
                        DesktopModeWindowDecorViewModel.this.mDesktopTasksController.minimizeTask(desktopModeWindowDecoration.mTaskInfo, DesktopModeEventLogger.Companion.MinimizeReason.MINIMIZE_BUTTON);
                        if (CoreRune.MW_SA_LOGGING) {
                            r3 = 9;
                        }
                    } else {
                        MultiWindowManager.getInstance().minimizeTaskById(this.mTaskId);
                    }
                    sendTalkBackFeedback(R.string.sec_decor_button_operation_minimized);
                }
                i = r3;
                if (CoreRune.MW_SA_LOGGING) {
                }
            }
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTapEvent(MotionEvent motionEvent) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            int actionMasked = motionEvent.getActionMasked();
            if (this.mIsDragging) {
                return false;
            }
            if ((actionMasked != 1 && actionMasked != 3) || DesktopModeWindowDecorViewModel.this.mDesktopUserRepositories.getCurrent().isTaskInFullImmersiveState(this.mTaskId)) {
                return false;
            }
            if (CoreRune.MW_CAPTION) {
                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
                if (desktopModeWindowDecoration == null) {
                    return false;
                }
                int i = desktopModeWindowDecoration.mTaskInfo.displayId;
                DesktopStateImpl.Companion.getClass();
                if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                    return false;
                }
                if (CoreRune.MW_SA_LOGGING) {
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
            if (!CoreRune.MW_CAPTION_BUG_FIX || desktopModeWindowDecoration != null) {
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
                                    maximizeButtonView.maximizeWindow.getBackground().setAlpha(((Integer) duration.getAnimatedValue()).intValue());
                                }
                            });
                            Unit unit = Unit.INSTANCE;
                            ObjectAnimator duration2 = ObjectAnimator.ofInt(maximizeButtonView.getProgressBar(), "progress", 100).setDuration(350L);
                            duration2.getClass();
                            duration2.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.windowdecor.MaximizeButtonView$startHoverAnimation$lambda$4$$inlined$doOnStart$1
                                @Override // android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                    MaximizeButtonView maximizeButtonView2 = maximizeButtonView;
                                    int i = MaximizeButtonView.$r8$clinit;
                                    maximizeButtonView2.getProgressBar().setProgress(0, false);
                                    maximizeButtonView.getProgressBar().setVisibility(0);
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
                                    MaximizeButtonView maximizeButtonView2 = maximizeButtonView;
                                    int i = MaximizeButtonView.$r8$clinit;
                                    maximizeButtonView2.getProgressBar().setVisibility(4);
                                    Function0 function0 = maximizeButtonView.onHoverAnimationFinishedListener;
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
                } else if (motionEvent.getAction() == 10 && id == R.id.maximize_window) {
                    desktopModeWindowDecoration.mIsAppHeaderMaximizeButtonHovered = false;
                    desktopModeWindowDecoration.onMaximizeHoverStateChanged();
                    desktopModeWindowDecoration.onMaximizeHoverStateChanged();
                    if (!desktopModeWindowDecoration.isMaximizeMenuActive() && !CoreRune.MW_CAPTION) {
                        DesktopModeWindowDecoration.asAppHeader(desktopModeWindowDecoration.mWindowDecorViewHolder).maximizeButtonView.cancelHoverAnimation();
                    }
                }
                return true;
            }
            return false;
        }

        @Override // android.view.View.OnLongClickListener
        public final boolean onLongClick(View view) throws Resources.NotFoundException {
            DesktopModeWindowDecoration desktopModeWindowDecoration;
            int id = view.getId();
            if (CoreRune.MW_CAPTION_DESKTOP || id != R.id.maximize_window || this.mLongClickDisabled || (desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId)) == null) {
                return false;
            }
            moveTaskToFront(desktopModeWindowDecoration.mTaskInfo);
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
        /* JADX WARN: Removed duplicated region for block: B:102:0x0180  */
        /* JADX WARN: Removed duplicated region for block: B:104:0x0184  */
        /* JADX WARN: Removed duplicated region for block: B:112:0x01a0  */
        /* JADX WARN: Removed duplicated region for block: B:115:0x01b3  */
        /* JADX WARN: Removed duplicated region for block: B:124:0x01c9  */
        /* JADX WARN: Removed duplicated region for block: B:130:0x01d4  */
        /* JADX WARN: Removed duplicated region for block: B:65:0x00bf  */
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
        /* JADX WARN: Type inference failed for: r15v11 */
        /* JADX WARN: Type inference failed for: r5v3 */
        /* JADX WARN: Type inference failed for: r5v4 */
        /* JADX WARN: Type inference failed for: r5v64 */
        @Override // android.view.View.OnTouchListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onTouch(View view, MotionEvent motionEvent) throws Resources.NotFoundException {
            ?? r13;
            boolean z;
            boolean z2;
            View view2;
            ViewRootImpl viewRootImpl;
            boolean z3;
            boolean z4;
            char c;
            DragResizeInputListener.TaskResizeInputEventReceiver taskResizeInputEventReceiver;
            ViewRootImpl viewRootImpl2;
            this.mMotionEvent = motionEvent;
            int id = view.getId();
            DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            ?? r5 = (motionEvent.getSource() & PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_CONNECTION_FAIL) == 4098;
            boolean z5 = CoreRune.MW_CAPTION;
            if (z5 && desktopModeWindowDecoration == null) {
                return false;
            }
            boolean z6 = CoreRune.MW_CAPTION_POPUP;
            if (z6) {
                this.mLongClickDisabled = ((motionEvent.getSource() & 8194) == 8194 || r5 == true || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) ? false : true;
            } else {
                this.mLongClickDisabled = (r5 == true || motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) ? false : true;
            }
            if (id != R.id.caption_handle && id != R.id.desktop_mode_caption && id != R.id.open_menu_button && id != R.id.close_window && id != R.id.maximize_window && id != R.id.minimize_window && z5) {
                DesktopModeWindowDecorViewModel.this.getClass();
                if (!(view instanceof CaptionButton) && !(view instanceof CaptionAnimationButton)) {
                    return false;
                }
            }
            ?? r11 = (!CoreRune.MW_CAPTION_TOOLTIP || (viewRootImpl2 = view.getViewRootImpl()) == null || (viewRootImpl2.mWindowAttributes.multiWindowFlags & 2) == 0) ? false : true;
            if (desktopModeWindowDecoration.isDecorCaptionState$1()) {
                DesktopModeWindowDecorViewModel.this.getClass();
                r13 = (view instanceof CaptionButton) || (view instanceof CaptionAnimationButton);
            }
            boolean zIsDecorHandleState = z5 ? desktopModeWindowDecoration.isDecorHandleState() : !((DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId)).mTaskInfo.isFreeform();
            int actionMasked = motionEvent.getActionMasked();
            ?? r15 = actionMasked == 0;
            ?? r10 = actionMasked == 3 || actionMasked == 1;
            if (r15 == true) {
                moveTaskToFront(desktopModeWindowDecoration.mTaskInfo);
                boolean zContains = desktopModeWindowDecoration.mResult.mCustomizableCaptionRegion.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                boolean zContains2 = ((Region) DesktopModeWindowDecorViewModel.this.mGestureExclusionTracker.exclusionRegions.get(Integer.valueOf(motionEvent.getDisplayId()))).contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                boolean zIsTransparentCaptionBarAppearance = TaskInfoKt.isTransparentCaptionBarAppearance(desktopModeWindowDecoration.mTaskInfo);
                int[] iArr = new int[2];
                view.getLocationInWindow(iArr);
                if (CoreRune.MW_CAPTION_FREEFORM) {
                    this.mIsButtonLongPressed = false;
                    this.mTargetView = r13 != false ? view : null;
                }
                boolean z7 = CoreRune.MW_CAPTION_HANDLE;
                if (z7 && zIsDecorHandleState) {
                    z3 = zContains;
                    if (id == R.id.caption_handle) {
                        z = zIsDecorHandleState;
                        if (((DesktopModeWindowDecoration) DesktopModeWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId)).mTaskInfo.isFreeform()) {
                            z4 = false;
                            iArr[0] = iArr[0] + desktopModeWindowDecoration.mResult.mCaptionX;
                        }
                        boolean z8 = z4;
                        if (z6) {
                            this.mIsNaturalSwitching = z8;
                        }
                        if (z7 && desktopModeWindowDecoration.isDecorHandleState() && id == R.id.caption_handle) {
                            view.performHapticFeedback(z8 ? 1 : 0);
                            if (!desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                                c = 1;
                                desktopModeWindowDecoration.setHandleOnTouchedState(true);
                            }
                            this.mTouchedPosition = null;
                            Point point = new Point(iArr[0], iArr[c]);
                            DragResizeInputListener dragResizeInputListener = desktopModeWindowDecoration.mDragResizeListener;
                            if (dragResizeInputListener == null) {
                                this.mIsResizeGesture = (dragResizeInputListener == null || (taskResizeInputEventReceiver = dragResizeInputListener.mInputEventReceiver) == null || !taskResizeInputEventReceiver.mDragResizeWindowGeometry.shouldHandleEvent(motionEvent, point) || !CoreRune.MW_CAPTION_FREEFORM_RESIZE_GUIDE || desktopModeWindowDecoration.mIsTaskMaximized) ? false : true;
                                if (z3) {
                                    this.mIsCustomHeaderGesture = !z3 && zContains2 && zIsTransparentCaptionBarAppearance;
                                }
                            }
                        } else {
                            c = 1;
                            this.mTouchedPosition = null;
                            Point point2 = new Point(iArr[0], iArr[c]);
                            DragResizeInputListener dragResizeInputListener2 = desktopModeWindowDecoration.mDragResizeListener;
                            this.mIsResizeGesture = (dragResizeInputListener2 == null || (taskResizeInputEventReceiver = dragResizeInputListener2.mInputEventReceiver) == null || !taskResizeInputEventReceiver.mDragResizeWindowGeometry.shouldHandleEvent(motionEvent, point2) || !CoreRune.MW_CAPTION_FREEFORM_RESIZE_GUIDE || desktopModeWindowDecoration.mIsTaskMaximized) ? false : true;
                            this.mIsCustomHeaderGesture = !z3 && zContains2 && zIsTransparentCaptionBarAppearance;
                        }
                    }
                    z4 = false;
                    boolean z82 = z4;
                    if (z6) {
                    }
                    if (z7) {
                        c = 1;
                        this.mTouchedPosition = null;
                        Point point22 = new Point(iArr[0], iArr[c]);
                        DragResizeInputListener dragResizeInputListener22 = desktopModeWindowDecoration.mDragResizeListener;
                    }
                } else {
                    z3 = zContains;
                }
                z = zIsDecorHandleState;
                z4 = false;
                boolean z822 = z4;
                if (z6) {
                }
                if (z7) {
                }
            } else {
                z = zIsDecorHandleState;
            }
            if (!this.mIsCustomHeaderGesture && !this.mIsResizeGesture) {
                if (DesktopModeWindowDecorViewModel.this.mInputManager != null && !DesktopModeFlags.ENABLE_ACCESSIBLE_CUSTOM_HEADERS.isTrue() && (viewRootImpl = view.getViewRootImpl()) != null) {
                    DesktopModeWindowDecorViewModel.this.mInputManager.pilferPointers(viewRootImpl.getInputToken());
                }
                if ((!CoreRune.MW_CAPTION_KEYGUARD || !DesktopModeWindowDecorViewModel.this.mIsKeyguardShowing) && r13 != true && r11 != true && desktopModeWindowDecoration.mTaskInfo.getWindowingMode() != 1 && DesktopModeWindowDecorViewModel.this.mNSController.onInterceptTouchEvent(motionEvent, this.mTaskId)) {
                    this.mIsNaturalSwitching = true;
                    updateDragStatus(desktopModeWindowDecoration, motionEvent);
                    return true;
                }
                boolean z9 = CoreRune.MW_CAPTION_FREEFORM;
                if (z9 && r13 == true && actionMasked == 2 && this.mIsDragging && (view2 = this.mTargetView) != null && view2.isPressed()) {
                    z2 = false;
                    this.mTargetView.setPressed(false);
                } else {
                    z2 = false;
                }
                if (r10 != false) {
                    this.mIsCustomHeaderGesture = z2;
                    this.mIsResizeGesture = z2;
                    if (z6 && this.mIsNaturalSwitching && id == R.id.caption_handle) {
                        view.setPressed(z2);
                    }
                    if (z9) {
                        this.mIsButtonLongPressed = z2;
                        this.mIsNaturalSwitching = z2;
                        this.mTargetView = null;
                    }
                    if (CoreRune.MW_CAPTION_HANDLE && id == R.id.caption_handle && desktopModeWindowDecoration.isDecorHandleState() && !desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                        desktopModeWindowDecoration.setHandleOnTouchedState(false);
                    }
                    if (id == R.id.apps_window) {
                        this.mTouchedPosition = new Point((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
                    }
                }
                if ((!z6 || r11 == false) && (!z9 || !this.mIsButtonLongPressed)) {
                    return z ? this.mHandleDragDetector.onMotionEvent(view, motionEvent) : this.mHeaderDragDetector.onMotionEvent(view, motionEvent);
                }
            }
            return false;
        }

        public final void sendTalkBackFeedback(int i) {
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
                return;
            }
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            String string = DesktopModeWindowDecorViewModel.this.mContext.getString(i);
            if (string.isEmpty()) {
                return;
            }
            accessibilityEventObtain.getText().clear();
            accessibilityEventObtain.getText().add(string);
            this.mAccessibilityManager.sendAccessibilityEvent(accessibilityEventObtain);
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
            this.mInputDownPoint = new Point();
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
            this.mCurrentUserId = ActivityManager.getCurrentUser();
        }
    }

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

        public final void onInputEvent(InputEvent inputEvent) throws Resources.NotFoundException {
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

    public class ExclusionRegionListenerImpl {
        public /* synthetic */ ExclusionRegionListenerImpl(DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel, int i) {
            this();
        }

        private ExclusionRegionListenerImpl() {
        }
    }

    public class InputMonitorFactory {
    }

    class TaskPositionerFactory {
    }

    public DesktopModeWindowDecorViewModel(Context context, ShellExecutor shellExecutor, Handler handler, Choreographer choreographer, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, ShellInit shellInit, ShellCommandHandler shellCommandHandler, IWindowManager iWindowManager, ShellTaskOrganizer shellTaskOrganizer, DesktopUserRepositories desktopUserRepositories, DisplayController displayController, ShellController shellController, DisplayInsetsController displayInsetsController, SyncTransactionQueue syncTransactionQueue, Transitions transitions, Optional<DesktopTasksController> optional, DesktopImmersiveController desktopImmersiveController, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, InteractionJankMonitor interactionJankMonitor, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MultiInstanceHelper multiInstanceHelper, Optional<DesktopTasksLimiter> optional2, AppHandleEducationController appHandleEducationController, AppToWebEducationController appToWebEducationController, AppHandleAndHeaderVisibilityHelper appHandleAndHeaderVisibilityHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, Optional<DesktopActivityOrientationChangeHandler> optional3, FocusTransitionObserver focusTransitionObserver, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, RecentsTransitionHandler recentsTransitionHandler, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, ShellExecutor shellExecutor3, Lazy lazy, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopTilingDecorViewModel desktopTilingDecorViewModel, MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController, CompatUIHandler compatUIHandler, DesksOrganizer desksOrganizer, DesktopState desktopState, DesktopConfig desktopConfig) {
        this(context, shellExecutor, handler, choreographer, mainCoroutineDispatcher, coroutineScope, shellExecutor2, shellInit, shellCommandHandler, iWindowManager, shellTaskOrganizer, desktopUserRepositories, displayController, shellController, displayInsetsController, syncTransactionQueue, transitions, optional, desktopImmersiveController, appToWebGenericLinksParser, assistContentRequester, windowDecorViewHostSupplier, multiInstanceHelper, new DesktopModeWindowDecoration.Factory(), new InputMonitorFactory(), new EnterDesktopTaskTransitionHandler$$ExternalSyntheticLambda0(), new AppHeaderViewHolder.Factory(), new AppHandleViewHolder.Factory(), rootTaskDisplayAreaOrganizer, new SparseArray(), interactionJankMonitor, optional2, appHandleEducationController, appToWebEducationController, appHandleAndHeaderVisibilityHelper, windowDecorCaptionHandleRepository, optional3, new TaskPositionerFactory(), focusTransitionObserver, desktopModeEventLogger, desktopModeUiEventLogger, windowDecorTaskResourceLoader, recentsTransitionHandler, naturalSwitchingDropTargetController, shellExecutor3, lazy, desktopModeCompatPolicy, desktopTilingDecorViewModel, multiDisplayDragMoveIndicatorController, compatUIHandler, desksOrganizer, desktopState, desktopConfig);
    }

    public final void createWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        TaskPositioner fluidResizeTaskPositioner;
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration != null) {
            desktopModeWindowDecoration.close();
        }
        Context displayContext = this.mDisplayController.getDisplayContext(runningTaskInfo.displayId);
        Context contextCreateContextAsUser = this.mContext.createContextAsUser(UserHandle.of(runningTaskInfo.userId), 0);
        SplitScreenController splitScreenController = this.mSplitScreenController;
        Choreographer choreographer = this.mMainChoreographer;
        HandleMenuHelpController handleMenuHelpController = this.mHandleMenuHelpController;
        this.mDesktopModeWindowDecorFactory.getClass();
        DesktopModeWindowDecoration desktopModeWindowDecoration2 = new DesktopModeWindowDecoration(displayContext, contextCreateContextAsUser, this.mDisplayController, this.mTaskResourceLoader, splitScreenController, this.mDesktopUserRepositories, this.mTaskOrganizer, runningTaskInfo, surfaceControl, this.mMainHandler, this.mMainExecutor, this.mMainDispatcher, this.mBgScope, this.mBgExecutor, choreographer, this.mSyncQueue, this.mAppHeaderViewHolderFactory, this.mAppHandleViewHolderFactory, this.mRootTaskDisplayAreaOrganizer, this.mGenericLinksParser, this.mAssistContentRequester, this.mWindowDecorViewHostSupplier, this.mMultiInstanceHelper, this.mWindowDecorCaptionHandleRepository, this.mDesktopModeEventLogger, this.mDesktopModeUiEventLogger, this.mDesktopModeCompatPolicy, this.mDesktopState, this.mDesktopConfig, this.mMultiTaskingHeaderViewHolderFactory, handleMenuHelpController, this.mDesktopImmersiveController, this.mDesktopTilingDecorViewModel, this.mDecorViewModelState);
        this.mWindowDecorByTaskId.put(runningTaskInfo.taskId, desktopModeWindowDecoration2);
        boolean z = CoreRune.MW_CAPTION;
        if (z) {
            Slog.d("DesktopModeWindowDecorViewModel", "createWindowDecoration: " + desktopModeWindowDecoration2 + ", decors=" + getWindowDecortoString());
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
        desktopModeWindowDecoration2.mOnMaximizeOrRestoreClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8(this, runningTaskInfo, desktopModeTouchEventListener, 0);
        desktopModeWindowDecoration2.mOnImmersiveOrRestoreClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, 3);
        desktopModeWindowDecoration2.mOnLeftSnapClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8(this, runningTaskInfo, desktopModeTouchEventListener, 1);
        desktopModeWindowDecoration2.mOnRightSnapClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8(this, runningTaskInfo, desktopModeTouchEventListener, 2);
        desktopModeWindowDecoration2.mOnToDesktopClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda16(this, runningTaskInfo, 0);
        desktopModeWindowDecoration2.mOnToFullscreenClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, 4);
        desktopModeWindowDecoration2.mOnToSplitscreenClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, 5);
        desktopModeWindowDecoration2.mOnToFloatClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, 6);
        desktopModeWindowDecoration2.mOpenInBrowserClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda16(this, runningTaskInfo, 1);
        desktopModeWindowDecoration2.mOnNewWindowClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, 7);
        desktopModeWindowDecoration2.mOnManageWindowsClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, desktopModeWindowDecoration2, 0);
        desktopModeWindowDecoration2.mOnChangeAspectRatioClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, 1);
        desktopModeWindowDecoration2.mOnRestartClickListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda9(this, runningTaskInfo, 2);
        desktopModeWindowDecoration2.mOnMaximizeHoverListener = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda8(this, desktopModeWindowDecoration2, runningTaskInfo);
        desktopModeWindowDecoration2.mOnCaptionButtonClickListener = desktopModeTouchEventListener;
        desktopModeWindowDecoration2.mOnCaptionTouchListener = desktopModeTouchEventListener;
        desktopModeWindowDecoration2.mOnCaptionLongClickListener = desktopModeTouchEventListener;
        desktopModeWindowDecoration2.mOnCaptionGenericMotionListener = desktopModeTouchEventListener;
        desktopModeWindowDecoration2.mExclusionRegionListener = this.mExclusionRegionListener;
        desktopModeWindowDecoration2.mDragPositioningCallback = fluidResizeTaskPositioner;
        if (CoreRune.MW_CAPTION_FREEFORM_MOTION) {
            desktopModeWindowDecoration2.mTaskPositioner = fluidResizeTaskPositioner;
        }
        if (CoreRune.MW_CAPTION_KEYGUARD && this.mIsKeyguardShowing && true != desktopModeWindowDecoration2.mIsKeyguardShowing) {
            desktopModeWindowDecoration2.mIsKeyguardShowing = true;
        }
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
            desktopModeWindowDecoration2.mSplitController = this.mSplitScreenController;
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
            Slog.d("DesktopModeWindowDecorViewModel", "destroyWindowDecoration: " + desktopModeWindowDecoration + ", decors=" + getWindowDecortoString());
        }
        int i2 = runningTaskInfo.taskId;
        NaturalSwitchingDropTargetController naturalSwitchingDropTargetController = this.mNSController;
        if (naturalSwitchingDropTargetController.mIsRunning) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = naturalSwitchingDropTargetController.mTaskInfo;
            if ((runningTaskInfo2 != null ? runningTaskInfo2.taskId : -1) == i2) {
                naturalSwitchingDropTargetController.cancelNaturalSwitching("WindowDecorationDestroyed(" + i2 + ")");
            }
        }
        if (CoreRune.MW_CAPTION_DESKTOP_DIMMING) {
            ((HashMap) this.mAppearedTaskList).remove(Integer.valueOf(runningTaskInfo.taskId));
        }
    }

    public final void ensureHandlerOnTransitionFinished() {
        TaskAppearedInfo taskAppearedInfo;
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
        ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
        ArrayList arrayList = (ArrayList) shellTaskOrganizer.getVisibleTaskAppearedInfos(-1);
        int size = arrayList.size();
        boolean z = false;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            TaskAppearedInfo taskAppearedInfo2 = (TaskAppearedInfo) obj;
            ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo2.getTaskInfo();
            int i2 = taskInfo.taskId;
            if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX && isExitingPipToLastParent(taskInfo)) {
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
            } else if (taskInfo.topActivity != null && this.mWindowDecorByTaskId.get(i2) == null && taskAppearedInfo2.getLeash().isValid() && shouldShowWindowDecor(taskInfo)) {
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

    public final String getWindowDecortoString() {
        if (this.mWindowDecorByTaskId.size() == 0) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < this.mWindowDecorByTaskId.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(this.mWindowDecorByTaskId.keyAt(i));
        }
        sb.append(']');
        return sb.toString();
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x009d, code lost:
    
        if ((r6 != null ? r6.contains(com.android.systemui.popup.util.PopupUIUtil.ACTION_MULTI_WINDOW_ENABLE_VALID_REQUESTER) : false) != false) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:155:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x0440  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x0449  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x047f  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x04a2  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x04af  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x0534  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x057d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleCaptionThroughStatusBar(MotionEvent motionEvent, DesktopModeWindowDecoration desktopModeWindowDecoration, Runnable runnable) throws Resources.NotFoundException {
        boolean z;
        int iIntValue;
        DesktopExperienceFlags desktopExperienceFlags;
        IBinder endTransitionToken;
        DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1;
        Function1 function1;
        DesktopModeVisualIndicator.IndicatorType indicatorType;
        DragToDesktopTransitionHandler.TransitionState fromFullscreen;
        int i;
        Integer numValueOf = null;
        if (desktopModeWindowDecoration == null) {
            if (motionEvent.getActionMasked() == 1) {
                this.mMoveToDesktopAnimator = null;
                this.mTransitionDragActive = false;
                return;
            }
            return;
        }
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            desktopModeWindowDecoration.checkTouchEvent(motionEvent);
            desktopModeWindowDecoration.updateHoverAndPressStatus(motionEvent);
            this.mDragToDesktopAnimationStartBounds.set(desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds());
            int windowingMode = desktopModeWindowDecoration.mTaskInfo.getWindowingMode();
            if (((DesktopStateImpl) this.mDesktopState).canEnterDesktopMode) {
                if (CoreRune.MW_CAPTION_DESKTOP) {
                    z = desktopModeWindowDecoration.mIsDesktopModeSupportedOnDisplay && desktopModeWindowDecoration.mTaskInfo.getWindowingMode() == 1;
                } else {
                    z = windowingMode == 1 || windowingMode == 6;
                }
            }
            boolean z2 = desktopModeWindowDecoration.checkTouchEventInFocusedCaptionHandle(motionEvent) || DesktopModeFlags.ENABLE_HANDLE_INPUT_FIX.isTrue();
            if (z && z2) {
                this.mTransitionDragActive = true;
                return;
            }
            return;
        }
        DesktopTasksController desktopTasksController = this.mDesktopTasksController;
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                if (this.mTransitionDragActive) {
                    if (motionEvent.getRawY() >= (this.mDisplayController.getDisplayLayout(desktopModeWindowDecoration.mTaskInfo.displayId).mStableInsets.top * 2) + desktopModeWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds().top || this.mMoveToDesktopAnimator != null) {
                        if (CoreRune.MW_CAPTION_DESKTOP_DISABLED) {
                            if (this.mContext.getResources().getConfiguration().smallestScreenWidthDp >= 600) {
                                int i2 = mDesktopDisabledFlags;
                                if ((i2 & 2) == 0 && (i2 & 4) == 0) {
                                    if ((i2 & 1) != 0) {
                                        List mWDisableRequesters = MultiWindowManager.getInstance().getMWDisableRequesters();
                                    }
                                }
                            }
                            if (this.mContext.getResources().getConfiguration().smallestScreenWidthDp < 600) {
                                i = R.string.mw_desktop_drag_handle_blocked_sw_600;
                            } else {
                                int i3 = mDesktopDisabledFlags;
                                if (i3 != 2) {
                                    if (i3 == 1) {
                                        List mWDisableRequesters2 = MultiWindowManager.getInstance().getMWDisableRequesters();
                                        if (mWDisableRequesters2 != null ? mWDisableRequesters2.contains(PopupUIUtil.ACTION_MULTI_WINDOW_ENABLE_VALID_REQUESTER) : false) {
                                            i = R.string.mw_desktop_drag_handle_blocked_overheat;
                                        }
                                    }
                                    Log.w("DesktopModeWindowDecorViewModel", "Enter Desktop failed but no reason exist for toast");
                                    this.mTransitionDragActive = false;
                                    return;
                                }
                                i = R.string.mw_desktop_drag_handle_blocked_using_smart_view;
                            }
                            Toast.makeText(this.mContext, i, 0).show();
                            this.mTransitionDragActive = false;
                            return;
                        }
                        DesktopModeVisualIndicator.DragStartState dragStartState = DesktopModeVisualIndicator.DragStartState.getDragStartState(desktopModeWindowDecoration.mTaskInfo);
                        if (dragStartState == null) {
                            return;
                        }
                        if (this.mDesktopTasksController.updateVisualIndicator(desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mTaskSurface, motionEvent.getRawX(), motionEvent.getRawY(), dragStartState, false, true, true) != DesktopModeVisualIndicator.IndicatorType.TO_FULLSCREEN_INDICATOR) {
                            ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskOrganizer.getRunningTaskInfo(desktopModeWindowDecoration.mTaskInfo.taskId);
                            if (runningTaskInfo == null || !runningTaskInfo.isRunning) {
                                return;
                            }
                            if (this.mMoveToDesktopAnimator == null) {
                                MoveToDesktopAnimator moveToDesktopAnimator = new MoveToDesktopAnimator(this.mContext, this.mDragToDesktopAnimationStartBounds, desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mTaskSurface);
                                this.mMoveToDesktopAnimator = moveToDesktopAnimator;
                                ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration.mTaskInfo;
                                SurfaceControl surfaceControl = desktopModeWindowDecoration.mTaskSurface;
                                DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda31 desktopModeWindowDecorViewModel$$ExternalSyntheticLambda31 = new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda31(this, desktopModeWindowDecoration, runnable, 1);
                                desktopTasksController.getClass();
                                DesktopTasksController.logV$1("startDragToDesktop taskId=%d", Integer.valueOf(runningTaskInfo2.taskId));
                                desktopTasksController.interactionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withSurface(107, desktopTasksController.context, surfaceControl, desktopTasksController.handler).setTimeout(DesktopTasksController.APP_HANDLE_DRAG_HOLD_CUJ_TIMEOUT_MS));
                                DesktopModeVisualIndicator desktopModeVisualIndicator = desktopTasksController.visualIndicator;
                                DragToDesktopTransitionHandler dragToDesktopTransitionHandler = desktopTasksController.dragToDesktopTransitionHandler;
                                if (dragToDesktopTransitionHandler.getInProgress$1()) {
                                    DragToDesktopTransitionHandler.logV$4("Drag to desktop transition already in progress.", new Object[0]);
                                } else {
                                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                    activityOptionsMakeBasic.setTransientLaunch();
                                    activityOptionsMakeBasic.setSourceInfo(5, SystemClock.uptimeMillis());
                                    activityOptionsMakeBasic.setPendingIntentCreatorBackgroundActivityStartMode(1);
                                    int i4 = runningTaskInfo2.userId;
                                    DesktopUserRepositories desktopUserRepositories = dragToDesktopTransitionHandler.desktopUserRepositories;
                                    List list = (List) ((LinkedHashMap) desktopUserRepositories.userIdToProfileIdsMap).get(Integer.valueOf(desktopUserRepositories.userId));
                                    if (list != null && list.contains(Integer.valueOf(i4))) {
                                        i4 = desktopUserRepositories.userId;
                                    }
                                    UserHandle userHandleOf = UserHandle.of(i4);
                                    PendingIntent activityAsUser = PendingIntent.getActivityAsUser(dragToDesktopTransitionHandler.context.createContextAsUser(userHandleOf, 0), 0, dragToDesktopTransitionHandler.launchHomeIntent, 50331656, activityOptionsMakeBasic.toBundle(), userHandleOf);
                                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                    windowContainerTransaction.sendPendingIntent(activityAsUser, dragToDesktopTransitionHandler.launchHomeIntent, ActivityOptions.makeBasic().setTransientLaunch().toBundle());
                                    IBinder iBinderStartTransition = dragToDesktopTransitionHandler.transitions.startTransition(VolteConstants.ErrorCode.CALL_SWITCH_FAILURE, windowContainerTransaction, dragToDesktopTransitionHandler);
                                    int i5 = runningTaskInfo2.taskId;
                                    SplitScreenController splitScreenController = dragToDesktopTransitionHandler.splitScreenController;
                                    if (splitScreenController == null) {
                                        splitScreenController = null;
                                    }
                                    if (splitScreenController.isTaskInSplitScreen$1(i5)) {
                                        int i6 = runningTaskInfo2.taskId;
                                        SplitScreenController splitScreenController2 = dragToDesktopTransitionHandler.splitScreenController;
                                        if (splitScreenController2 == null) {
                                            splitScreenController2 = null;
                                        }
                                        int splitPosition = splitScreenController2.getSplitPosition(i6);
                                        if (splitPosition != -1) {
                                            int i7 = splitPosition == 1 ? 0 : 1;
                                            SplitScreenController splitScreenController3 = dragToDesktopTransitionHandler.splitScreenController;
                                            if (splitScreenController3 == null) {
                                                splitScreenController3 = null;
                                            }
                                            ActivityManager.RunningTaskInfo taskInfo = splitScreenController3.getTaskInfo(i7);
                                            if (taskInfo != null) {
                                                numValueOf = Integer.valueOf(taskInfo.taskId);
                                            }
                                        }
                                        if (numValueOf == null) {
                                            throw new IllegalStateException("Expected split task to have a counterpart.");
                                        }
                                        int iIntValue2 = numValueOf.intValue();
                                        int i8 = runningTaskInfo2.taskId;
                                        iBinderStartTransition.getClass();
                                        fromFullscreen = new DragToDesktopTransitionHandler.TransitionState.FromSplit(i8, moveToDesktopAnimator, iBinderStartTransition, null, null, null, null, null, null, null, null, false, desktopModeVisualIndicator, false, null, false, null, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda31, null, iIntValue2, 389112, null);
                                    } else {
                                        int i9 = runningTaskInfo2.taskId;
                                        iBinderStartTransition.getClass();
                                        fromFullscreen = new DragToDesktopTransitionHandler.TransitionState.FromFullscreen(i9, moveToDesktopAnimator, iBinderStartTransition, null, null, null, null, null, null, null, null, false, desktopModeVisualIndicator, false, null, false, null, desktopModeWindowDecorViewModel$$ExternalSyntheticLambda31, null, null, 913400, null);
                                    }
                                    dragToDesktopTransitionHandler.transitionState = fromFullscreen;
                                    dragToDesktopTransitionHandler.recordLogHistory("[S] " + fromFullscreen);
                                    DragToDesktopTransitionHandler.logV$4("startDragToDesktopTransition: " + dragToDesktopTransitionHandler.transitionState + ", c=" + Debug.getCallers(2), new Object[0]);
                                }
                            }
                        }
                        MoveToDesktopAnimator moveToDesktopAnimator2 = this.mMoveToDesktopAnimator;
                        if (moveToDesktopAnimator2 != null) {
                            moveToDesktopAnimator2.mostRecentInput.set(motionEvent.getRawX(), motionEvent.getRawY());
                            if (!moveToDesktopAnimator2.allowSurfaceChangesOnMove || moveToDesktopAnimator2.dragToDesktopAnimator.isRunning()) {
                                return;
                            }
                            moveToDesktopAnimator2.velocityTracker.addMovement(motionEvent);
                            moveToDesktopAnimator2.setTaskPosition(motionEvent.getRawX(), motionEvent.getRawY());
                            SurfaceControl.Transaction transaction = (SurfaceControl.Transaction) moveToDesktopAnimator2.transactionFactory.invoke();
                            SurfaceControl surfaceControl2 = moveToDesktopAnimator2.taskSurface;
                            PointF pointF = moveToDesktopAnimator2.position;
                            transaction.setPosition(surfaceControl2, pointF.x, pointF.y);
                            transaction.setFrameTimeline(Choreographer.getInstance().getVsyncId());
                            transaction.apply();
                            return;
                        }
                        return;
                    }
                    return;
                }
                return;
            }
            if (actionMasked != 3) {
                if (actionMasked == 7 || actionMasked == 9 || actionMasked == 10) {
                    desktopModeWindowDecoration.updateHoverAndPressStatus(motionEvent);
                    return;
                }
                return;
            }
        }
        if (this.mTransitionDragActive) {
            DesktopModeVisualIndicator.DragStartState dragStartState2 = DesktopModeVisualIndicator.DragStartState.getDragStartState(desktopModeWindowDecoration.mTaskInfo);
            if (dragStartState2 == null) {
                return;
            }
            this.mDesktopTasksController.updateVisualIndicator(desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mTaskSurface, motionEvent.getRawX(), motionEvent.getRawY(), dragStartState2, false, true, true);
            this.mTransitionDragActive = false;
            if (this.mMoveToDesktopAnimator != null) {
                desktopModeWindowDecoration.updateHoverAndPressStatus(motionEvent);
                if (motionEvent.getActionMasked() != 3) {
                    DesktopStateImpl.Companion.getClass();
                    if (DesktopStateImpl.Companion.inDesktopWindowing(0) && dragStartState2 == DesktopModeVisualIndicator.DragStartState.FROM_FULLSCREEN) {
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = desktopModeWindowDecoration.mTaskInfo;
                        desktopTasksController.interactionJankMonitor.cancel(107);
                        DesktopTasksController.logV$1("cancelDragToDesktop taskId=%d", Integer.valueOf(runningTaskInfo3.taskId));
                        desktopTasksController.dragToDesktopTransitionHandler.cancelDragToDesktopTransition(DragToDesktopTransitionHandler.CancelState.STANDARD_CANCEL);
                    } else {
                        PointF pointF2 = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
                        ActivityManager.RunningTaskInfo runningTaskInfo4 = desktopModeWindowDecoration.mTaskInfo;
                        SurfaceControl surfaceControl3 = desktopModeWindowDecoration.mTaskSurface;
                        desktopTasksController.interactionJankMonitor.end(107);
                        DesktopModeVisualIndicator visualIndicator = desktopTasksController.getVisualIndicator();
                        if (visualIndicator == null) {
                            indicatorType = DesktopModeVisualIndicator.IndicatorType.NO_INDICATOR;
                        } else {
                            DesktopModeVisualIndicator.IndicatorType indicatorTypeUpdateIndicatorType = visualIndicator.updateIndicatorType(pointF2, runningTaskInfo4, false, false, true, true);
                            int i10 = DesktopTasksController.WhenMappings.$EnumSwitchMapping$1[indicatorTypeUpdateIndicatorType.ordinal()];
                            DragToDesktopTransitionHandler dragToDesktopTransitionHandler2 = desktopTasksController.dragToDesktopTransitionHandler;
                            DesktopModeUiEventLogger desktopModeUiEventLogger = desktopTasksController.desktopModeUiEventLogger;
                            switch (i10) {
                                case 1:
                                case 4:
                                    desktopModeUiEventLogger.log(runningTaskInfo4, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_FULL_SCREEN);
                                    DesktopTasksController.logV$1("cancelDragToDesktop taskId=%d", Integer.valueOf(runningTaskInfo4.taskId));
                                    dragToDesktopTransitionHandler2.cancelDragToDesktopTransition(DragToDesktopTransitionHandler.CancelState.STANDARD_CANCEL);
                                    break;
                                case 2:
                                    desktopModeUiEventLogger.log(runningTaskInfo4, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_SPLIT_SCREEN);
                                    desktopTasksController.requestSplit(runningTaskInfo4, true);
                                    break;
                                case 3:
                                    desktopModeUiEventLogger.log(runningTaskInfo4, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_SPLIT_SCREEN);
                                    desktopTasksController.requestSplit(runningTaskInfo4, false);
                                    break;
                                case 5:
                                    desktopTasksController.requestFloat(runningTaskInfo4, Boolean.TRUE);
                                    break;
                                case 6:
                                    desktopTasksController.requestFloat(runningTaskInfo4, Boolean.FALSE);
                                    break;
                                case 7:
                                    LatencyTracker.getInstance(desktopTasksController.context).onActionStart(30);
                                    desktopTasksController.interactionJankMonitor.begin(surfaceControl3, desktopTasksController.context, desktopTasksController.handler, 116, "to_desktop");
                                    desktopModeUiEventLogger.log(runningTaskInfo4, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_APP_HANDLE_DRAG_TO_DESKTOP_MODE);
                                    int i11 = runningTaskInfo4.displayId;
                                    if (i11 == 0) {
                                        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(i11, true);
                                        if (orCreateDefaultDeskId != null) {
                                            iIntValue = orCreateDefaultDeskId.intValue();
                                            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopTasksController: finalizeDragToDesktop taskId=%d deskId=%d", new Object[]{Integer.valueOf(runningTaskInfo4.taskId), Integer.valueOf(iIntValue)});
                                            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                            desktopTasksController.exitSplitIfApplicable(windowContainerTransaction2, runningTaskInfo4);
                                            desktopExperienceFlags = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND;
                                            if (!desktopExperienceFlags.isTrue()) {
                                                desktopTasksController.moveHomeTaskToTop(windowContainerTransaction2, desktopTasksController.context.getDisplayId());
                                            }
                                            int i12 = iIntValue;
                                            Function1 function1AddDeskActivationChanges$default = DesktopTasksController.addDeskActivationChanges$default(desktopTasksController, i12, windowContainerTransaction2, runningTaskInfo4, 0, 0, 56);
                                            desktopTasksController.addMoveToDeskTaskChanges(windowContainerTransaction2, runningTaskInfo4, i12, pointF2, false);
                                            DesktopImmersiveController.ExitResult exitResultExitImmersiveIfApplicable = desktopTasksController.desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction2, runningTaskInfo4.displayId, null, DesktopImmersiveController.ExitReason.TASK_LAUNCH);
                                            if (dragToDesktopTransitionHandler2.getInProgress$1()) {
                                                DragToDesktopTransitionHandler.logV$4("finishDragToDesktop: not in progress, returning", new Object[0]);
                                            } else {
                                                DragToDesktopTransitionHandler.TransitionState transitionStateRequireTransitionState = dragToDesktopTransitionHandler2.requireTransitionState();
                                                if (transitionStateRequireTransitionState.getStartAborted()) {
                                                    DragToDesktopTransitionHandler.logV$4("finishDragToDesktop: start was aborted, clearing state", new Object[0]);
                                                    dragToDesktopTransitionHandler2.clearState();
                                                } else if (transitionStateRequireTransitionState.getStartInterrupted()) {
                                                    DragToDesktopTransitionHandler.logV$4("finishDragToDesktop: start was interrupted, returning", new Object[0]);
                                                } else {
                                                    transitionStateRequireTransitionState.setEndTransitionToken(dragToDesktopTransitionHandler2.transitions.startTransition(VolteConstants.ErrorCode.CALL_SWITCH_REJECTED, windowContainerTransaction2, dragToDesktopTransitionHandler2));
                                                    endTransitionToken = transitionStateRequireTransitionState.getEndTransitionToken();
                                                    desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = desktopTasksController.desktopModeEnterExitTransitionListener;
                                                    if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
                                                        desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1.onEnterDesktopModeTransitionStarted();
                                                    }
                                                    if (endTransitionToken != null) {
                                                        function1AddDeskActivationChanges$default.mo781invoke(endTransitionToken);
                                                        DesktopImmersiveController.ExitResult.Exit exitAsExit = exitResultExitImmersiveIfApplicable.asExit();
                                                        if (exitAsExit != null && (function1 = exitAsExit.runOnTransitionStart) != null) {
                                                            function1.mo781invoke(endTransitionToken);
                                                        }
                                                        if (!desktopExperienceFlags.isTrue()) {
                                                            desktopTasksController.taskRepository.setActiveDesk(runningTaskInfo4.displayId, i12);
                                                        }
                                                    } else {
                                                        LatencyTracker.getInstance(desktopTasksController.context).onActionCancel(30);
                                                    }
                                                }
                                            }
                                            endTransitionToken = null;
                                            desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = desktopTasksController.desktopModeEnterExitTransitionListener;
                                            if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
                                            }
                                            if (endTransitionToken != null) {
                                            }
                                        }
                                        if (CoreRune.MW_SA_LOGGING) {
                                            CoreSaLogger.logForDexMW("3004", "By full screen handle gesture");
                                            break;
                                        }
                                    } else {
                                        Integer orCreateDefaultDeskId2 = desktopTasksController.getOrCreateDefaultDeskId(i11, false);
                                        if (orCreateDefaultDeskId2 != null) {
                                            iIntValue = orCreateDefaultDeskId2.intValue();
                                            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopTasksController: finalizeDragToDesktop taskId=%d deskId=%d", new Object[]{Integer.valueOf(runningTaskInfo4.taskId), Integer.valueOf(iIntValue)});
                                            WindowContainerTransaction windowContainerTransaction22 = new WindowContainerTransaction();
                                            desktopTasksController.exitSplitIfApplicable(windowContainerTransaction22, runningTaskInfo4);
                                            desktopExperienceFlags = DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND;
                                            if (!desktopExperienceFlags.isTrue()) {
                                            }
                                            int i122 = iIntValue;
                                            Function1 function1AddDeskActivationChanges$default2 = DesktopTasksController.addDeskActivationChanges$default(desktopTasksController, i122, windowContainerTransaction22, runningTaskInfo4, 0, 0, 56);
                                            desktopTasksController.addMoveToDeskTaskChanges(windowContainerTransaction22, runningTaskInfo4, i122, pointF2, false);
                                            DesktopImmersiveController.ExitResult exitResultExitImmersiveIfApplicable2 = desktopTasksController.desktopImmersiveController.exitImmersiveIfApplicable(windowContainerTransaction22, runningTaskInfo4.displayId, null, DesktopImmersiveController.ExitReason.TASK_LAUNCH);
                                            if (dragToDesktopTransitionHandler2.getInProgress$1()) {
                                            }
                                            endTransitionToken = null;
                                            desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 = desktopTasksController.desktopModeEnterExitTransitionListener;
                                            if (desktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 != null) {
                                            }
                                            if (endTransitionToken != null) {
                                            }
                                        }
                                        if (CoreRune.MW_SA_LOGGING) {
                                        }
                                    }
                                    break;
                                case 8:
                                    Log.d("DesktopTasksController", "drag to the desktop maximized window.");
                                    break;
                                default:
                                    throw new NoWhenBranchMatchedException();
                            }
                            indicatorType = indicatorTypeUpdateIndicatorType;
                        }
                        if (indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_LEFT_INDICATOR || indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_SPLIT_RIGHT_INDICATOR) {
                            desktopModeWindowDecoration.disposeStatusBarInputLayer();
                            int splitPosition2 = this.mSplitScreenController.getSplitPosition(desktopModeWindowDecoration.mTaskInfo.taskId);
                            if (splitPosition2 != -1) {
                                ActivityManager.RunningTaskInfo taskInfo2 = this.mSplitScreenController.getTaskInfo(splitPosition2 == 0 ? 1 : 0);
                                if (taskInfo2 != null) {
                                    ((DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(taskInfo2.taskId)).disposeStatusBarInputLayer();
                                }
                            }
                        }
                        if (CoreRune.MW_CAPTION_DESKTOP) {
                            desktopModeWindowDecoration.mInDesktopWindowing = indicatorType == DesktopModeVisualIndicator.IndicatorType.TO_DESKTOP_INDICATOR;
                        }
                    }
                }
                this.mMoveToDesktopAnimator = null;
                return;
            }
            desktopTasksController.releaseVisualIndicator();
        }
        desktopModeWindowDecoration.checkTouchEvent(motionEvent);
    }

    public final void incrementEventReceiverTasks(int i) {
        if (this.mEventReceiversByDisplay.contains(i)) {
            ((EventReceiver) this.mEventReceiversByDisplay.get(i)).mTasksOnDisplay++;
            return;
        }
        InputManager inputManager = (InputManager) this.mContext.getSystemService(InputManager.class);
        this.mInputMonitorFactory.getClass();
        InputMonitor inputMonitorMonitorGestureInput = inputManager.monitorGestureInput("caption-touch", i);
        this.mEventReceiversByDisplay.put(i, new EventReceiver(inputMonitorMonitorGestureInput, inputMonitorMonitorGestureInput.getInputChannel(), Looper.myLooper()));
    }

    public final boolean isExitingPipToLastParent(final ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo != null && ((Boolean) ((Optional) this.mPipOptionalLazy.get()).map(new Function() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda25
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((Pip) obj).isExitingPipToLastParent(runningTaskInfo.taskId));
            }
        }).orElse(Boolean.FALSE)).booleanValue();
    }

    public final boolean isTaskInSplitScreen(int i) {
        SplitScreenController splitScreenController = this.mSplitScreenController;
        return splitScreenController != null && splitScreenController.isTaskInSplitScreen$1(i);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onDecorationTaskTransitionReady(IBinder iBinder, TransitionInfo.Change change) {
        List arrayList = (List) ((HashMap) this.mTransitionToTaskInfo).get(iBinder);
        if (arrayList == null) {
            arrayList = new ArrayList();
            ((HashMap) this.mTransitionToTaskInfo).put(iBinder, arrayList);
        }
        arrayList.add(change.getTaskInfo());
    }

    public final void onEnterOrExitImmersive(ActivityManager.RunningTaskInfo runningTaskInfo) {
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration == null) {
            Log.w("DesktopModeWindowDecorViewModel", "onEnterOrExitImmersive: failed, decoration not exist");
            return;
        }
        DesktopRepository profile = this.mDesktopUserRepositories.getProfile(runningTaskInfo.userId);
        boolean zIsTaskInFullImmersiveState = profile.isTaskInFullImmersiveState(runningTaskInfo.taskId);
        DesktopModeUiEventLogger desktopModeUiEventLogger = this.mDesktopModeUiEventLogger;
        DesktopImmersiveController desktopImmersiveController = this.mDesktopImmersiveController;
        if (zIsTaskInFullImmersiveState) {
            RecyclerView$$ExternalSyntheticOutline0.m(runningTaskInfo.taskId, "DesktopModeWindowDecorViewModel", new StringBuilder("onExitImmersive : taskId="));
            desktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_RESTORE);
            desktopImmersiveController.moveTaskToNonImmersive(desktopModeWindowDecoration.mTaskInfo, DesktopImmersiveController.ExitReason.USER_INTERACTION);
        } else {
            DesktopRepository.Desk activeDesk = profile.desktopData.getActiveDesk(runningTaskInfo.displayId);
            Integer num = activeDesk != null ? activeDesk.fullImmersiveTaskId : null;
            Log.d("DesktopModeWindowDecorViewModel", "onEnterImmersive: taskId=" + runningTaskInfo.taskId + " immersiveTaskId=" + num);
            if (num != null) {
                DesktopModeWindowDecoration desktopModeWindowDecoration2 = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(num.intValue());
                if (desktopModeWindowDecoration2 == null) {
                    Log.w("DesktopModeWindowDecorViewModel", "onEnterImmersive: ImmersiveDecoration not exist");
                    desktopImmersiveController.moveTaskToImmersive(desktopModeWindowDecoration.mTaskInfo);
                } else {
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = desktopModeWindowDecoration.mTaskInfo;
                    ActivityManager.RunningTaskInfo runningTaskInfo3 = desktopModeWindowDecoration2.mTaskInfo;
                    desktopImmersiveController.getClass();
                    String str = "Task must already be in freeform";
                    if (!runningTaskInfo2.isFreeform()) {
                        if (CoreRune.MW_CAPTION_BUG_FIX) {
                            str = "Task must already be in freeform " + runningTaskInfo2;
                        }
                        throw new IllegalStateException(str.toString());
                    }
                    if (!runningTaskInfo3.isFreeform()) {
                        if (CoreRune.MW_CAPTION_BUG_FIX) {
                            str = "Task must already be in freeform " + runningTaskInfo3;
                        }
                        throw new IllegalStateException(str.toString());
                    }
                    if (((ArrayList) desktopImmersiveController.pendingImmersiveTransitions).isEmpty()) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.setBounds(runningTaskInfo2.token, new Rect());
                        windowContainerTransaction.setBounds(runningTaskInfo3.token, desktopImmersiveController.getExitDestinationBounds(runningTaskInfo3));
                        DesktopImmersiveController.logV("Moving task %d out of immersive mode, task %s in immersive mode", Integer.valueOf(runningTaskInfo3.taskId), Integer.valueOf(runningTaskInfo2.taskId));
                        IBinder iBinderStartTransition = desktopImmersiveController.transitions.startTransition(6, windowContainerTransaction, desktopImmersiveController);
                        int i = runningTaskInfo2.taskId;
                        int i2 = runningTaskInfo2.displayId;
                        DesktopImmersiveController.Direction direction = DesktopImmersiveController.Direction.ENTER;
                        iBinderStartTransition.getClass();
                        DesktopImmersiveController.addPendingImmersiveTransition$default(desktopImmersiveController, i, i2, direction, iBinderStartTransition);
                        DesktopImmersiveController.addPendingImmersiveTransition$default(desktopImmersiveController, runningTaskInfo3.taskId, runningTaskInfo3.displayId, DesktopImmersiveController.Direction.EXIT, iBinderStartTransition);
                    } else {
                        DesktopImmersiveController.logV("Cannot start exit because transition(s) already in progress: %s", desktopImmersiveController.pendingImmersiveTransitions);
                    }
                }
            } else {
                desktopModeUiEventLogger.log(desktopModeWindowDecoration.mTaskInfo, DesktopModeUiEventLogger.DesktopUiEventEnum.DESKTOP_WINDOW_MAXIMIZE_BUTTON_MENU_TAP_TO_IMMERSIVE);
                desktopImmersiveController.moveTaskToImmersive(desktopModeWindowDecoration.mTaskInfo);
            }
        }
        desktopModeWindowDecoration.mIsTaskResizing = true;
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
        if (!shouldShowWindowDecor(runningTaskInfo)) {
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
            boolean zInDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(i);
            if (zInDesktopWindowing != desktopModeWindowDecoration.mInDesktopWindowing) {
                desktopModeWindowDecoration.mInDesktopWindowing = zInDesktopWindowing;
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
            int iWidth = bounds.width();
            if (freeformStashState.isLeftStashed()) {
                float f2 = iWidth;
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
        TaskPositioner taskPositioner;
        OutlineView outlineView;
        if (CoreRune.MW_CAPTION_DESKTOP_DIMMING) {
            ((HashMap) this.mAppearedTaskList).remove(Integer.valueOf(runningTaskInfo.taskId));
        }
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration == null) {
            return;
        }
        if (CoreRune.MW_CAPTION_FREEFORM) {
            FreeformOutline freeformOutline = desktopModeWindowDecoration.mFreeformOutline;
            if (freeformOutline != null && (outlineView = freeformOutline.getOutlineView()) != null) {
                outlineView.mIsClosing = true;
                outlineView.invalidate();
            }
            if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && (taskPositioner = desktopModeWindowDecoration.mTaskPositioner) != null) {
                taskPositioner.onDragPositioningCancel();
            }
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
                if (color != ColorKt.m469toArgb8_81llA((tilingDividerView2.isDarkMode ? decorThemeUtil.darkColors : decorThemeUtil.lightColors).outlineVariant)) {
                    Paint paint = tilingDividerView2.paint;
                    DecorThemeUtil decorThemeUtil2 = tilingDividerView2.decorThemeUtil;
                    paint.setColor(ColorKt.m469toArgb8_81llA((tilingDividerView2.isDarkMode ? decorThemeUtil2.darkColors : decorThemeUtil2.lightColors).outlineVariant));
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
                    paint2.setColor(ColorKt.m469toArgb8_81llA((z2 ? decorThemeUtil3.darkColors : decorThemeUtil3.lightColors).outlineVariant));
                    DividerRoundedCorner dividerRoundedCorner2 = tilingDividerView.corners;
                    DividerRoundedCorner dividerRoundedCorner3 = dividerRoundedCorner2 != null ? dividerRoundedCorner2 : null;
                    dividerRoundedCorner3.mDividerBarBackground.setColor(tilingDividerView.paint.getColor());
                    dividerRoundedCorner3.invalidate();
                    tilingDividerView.invalidate();
                }
            }
        }
        this.mActivityOrientationChangeHandler.ifPresent(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda16(runningTaskInfo2, runningTaskInfo));
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final boolean onTaskOpening(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        FreeformOutline freeformOutline;
        OutlineView outlineView;
        if (!shouldShowWindowDecor(runningTaskInfo)) {
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
        TaskPositioner taskPositioner;
        OutlineView outlineView;
        DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (desktopModeWindowDecoration != null) {
            FreeformOutline freeformOutline = desktopModeWindowDecoration.mFreeformOutline;
            if (freeformOutline != null && (outlineView = freeformOutline.getOutlineView()) != null) {
                outlineView.mIsClosing = true;
                outlineView.invalidate();
            }
            if (CoreRune.MW_DND_FREEFORM_DISMISS_VIEW && (taskPositioner = desktopModeWindowDecoration.mTaskPositioner) != null) {
                taskPositioner.onDragPositioningCancel();
            }
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
        } else if (CoreRune.MW_CAPTION_FREEFORM_STASH && (desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) != null && desktopModeWindowDecoration.mFreeformStashState.isStashed()) {
            FreeformStashState freeformStashState = desktopModeWindowDecoration.mFreeformStashState;
            freeformStashState.mAnimType = -1;
            freeformStashState.mAnimating = false;
            freeformStashState.setStashed(-1);
            desktopModeWindowDecoration.mFreeformStashState.setStashed(0);
            desktopModeWindowDecoration.closeFreeformDimInputListener();
        }
        if (CoreRune.MW_CAPTION_DESKTOP_DIMMING) {
            ((HashMap) this.mAppearedTaskList).remove(Integer.valueOf(runningTaskInfo.taskId));
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
            boolean zIsTaskMaximized = DesktopModeUtils.isTaskMaximized(runningTaskInfo, displayController);
            ToggleTaskSizeInteraction.Direction direction = zIsTaskMaximized ? ToggleTaskSizeInteraction.Direction.RESTORE : ToggleTaskSizeInteraction.Direction.MAXIMIZE;
            int i3 = ToggleTaskSizeUtilsKt$WhenMappings.$EnumSwitchMapping$0[ambiguousSource.ordinal()];
            if (i3 == 1) {
                source = zIsTaskMaximized ? ToggleTaskSizeInteraction.Source.HEADER_BUTTON_TO_RESTORE : ToggleTaskSizeInteraction.Source.HEADER_BUTTON_TO_MAXIMIZE;
            } else if (i3 == 2) {
                source = zIsTaskMaximized ? ToggleTaskSizeInteraction.Source.MAXIMIZE_MENU_TO_RESTORE : ToggleTaskSizeInteraction.Source.MAXIMIZE_MENU_TO_MAXIMIZE;
            } else {
                if (i3 != 3) {
                    throw new NoWhenBranchMatchedException();
                }
                source = zIsTaskMaximized ? ToggleTaskSizeInteraction.Source.DOUBLE_TAP_TO_RESTORE : ToggleTaskSizeInteraction.Source.DOUBLE_TAP_TO_MAXIMIZE;
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
        desktopModeWindowDecoration.mIsTaskResizing = true;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionFinished(IBinder iBinder) {
        DesktopModeWindowDecoration desktopModeWindowDecoration;
        OutlineView outlineView;
        if (CoreRune.MW_CAPTION) {
            this.mDecorViewModelState.mInTransition = false;
            List list = (List) ((HashMap) this.mTransitionToTaskInfo).getOrDefault(iBinder, Collections.EMPTY_LIST);
            ((HashMap) this.mTransitionToTaskInfo).remove(iBinder);
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) list.get(i);
                if (runningTaskInfo != null && (desktopModeWindowDecoration = (DesktopModeWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) != null) {
                    FreeformOutline freeformOutline = desktopModeWindowDecoration.mFreeformOutline;
                    if (freeformOutline != null && (outlineView = freeformOutline.getOutlineView()) != null && (outlineView.mIsOpening || outlineView.mIsClosing)) {
                        outlineView.mIsOpening = false;
                        outlineView.mIsClosing = false;
                        outlineView.invalidate();
                    }
                    desktopModeWindowDecoration.mIsTaskResizing = false;
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
        ((HashMap) this.mTransitionToTaskInfo).remove(iBinder);
        List list2 = (List) ((HashMap) this.mTransitionToTaskInfo).get(iBinder2);
        if (list2 != null) {
            list2.addAll(list);
        } else {
            ((HashMap) this.mTransitionToTaskInfo).put(iBinder2, list);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionStarting(IBinder iBinder) {
        if (CoreRune.MW_CAPTION) {
            this.mDecorViewModelState.mInTransition = true;
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
        DesktopTasksController desktopTasksController = this.mDesktopTasksController;
        desktopTasksController.freeformTaskTransitionStarter = freeformTaskTransitionStarter;
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
        shortcutController.mDesktopTasksController = desktopTasksController;
        shortcutController.mDesktopStateImpl = new DesktopStateImpl(shortcutController.mContext);
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x0030  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldShowWindowDecor(ActivityManager.RunningTaskInfo runningTaskInfo) {
        boolean zShouldShowWindowDecor = shouldShowWindowDecor(runningTaskInfo, false);
        if (CoreRune.MW_CAPTION_DESKTOP_DIMMING) {
            if (!((RootTaskDesksOrganizer) this.mDesktopTasksController.desksOrganizer).deskRootsByDeskId.contains(runningTaskInfo.taskId)) {
                int i = runningTaskInfo.displayId;
                DesktopStateImpl.Companion.getClass();
                boolean z = DesktopStateImpl.Companion.inDesktopWindowing(i) && runningTaskInfo.isFreeform() && !zShouldShowWindowDecor;
                boolean z2 = !z;
                boolean zContainsKey = ((HashMap) this.mAppearedTaskList).containsKey(Integer.valueOf(runningTaskInfo.taskId));
                ShellTaskOrganizer shellTaskOrganizer = this.mTaskOrganizer;
                if (zContainsKey) {
                    if (((Boolean) ((HashMap) this.mAppearedTaskList).get(Integer.valueOf(runningTaskInfo.taskId))).booleanValue() != z2) {
                        ((HashMap) this.mAppearedTaskList).put(Integer.valueOf(runningTaskInfo.taskId), Boolean.valueOf(z2));
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.setCaptionShowingState(runningTaskInfo.token, z2);
                        shellTaskOrganizer.applyTransaction(windowContainerTransaction);
                        return zShouldShowWindowDecor;
                    }
                } else {
                    ((HashMap) this.mAppearedTaskList).put(Integer.valueOf(runningTaskInfo.taskId), Boolean.valueOf(z2));
                    if (z) {
                        WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                        windowContainerTransaction2.setCaptionShowingState(runningTaskInfo.token, false);
                        shellTaskOrganizer.applyTransaction(windowContainerTransaction2);
                    }
                }
            }
        }
        return zShouldShowWindowDecor;
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
        forAllDecorations(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda23(0));
    }

    public final void updateFullscreenHandlerState() {
        int i = 0;
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "multi_window_menu_in_full_screen", ((DesktopStateImpl) this.mDesktopState).isDesktopModeSupportedOnDisplay(this.mContext.getDisplay()) ? 1 : 0) == 1;
        if (CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED != z) {
            CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED = z;
            Slog.d("DesktopModeWindowDecorViewModel", "updateFullscreenHandlerState: enabled=" + z);
            if (!z) {
                final ArrayList arrayList = new ArrayList();
                forAllDecorations(new Consumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda22
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
            ArrayList arrayList2 = (ArrayList) this.mTaskOrganizer.getVisibleTaskAppearedInfos(-1);
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj = arrayList2.get(i);
                i++;
                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) obj;
                ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
                int i2 = taskInfo.taskId;
                if (taskInfo.getWindowingMode() == 1 && taskAppearedInfo.getLeash().isValid() && shouldShowWindowDecor(taskInfo)) {
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

    /* JADX WARN: Type inference failed for: r10v22, types: [com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0] */
    public DesktopModeWindowDecorViewModel(Context context, ShellExecutor shellExecutor, Handler handler, Choreographer choreographer, MainCoroutineDispatcher mainCoroutineDispatcher, CoroutineScope coroutineScope, ShellExecutor shellExecutor2, ShellInit shellInit, ShellCommandHandler shellCommandHandler, IWindowManager iWindowManager, ShellTaskOrganizer shellTaskOrganizer, DesktopUserRepositories desktopUserRepositories, DisplayController displayController, ShellController shellController, DisplayInsetsController displayInsetsController, SyncTransactionQueue syncTransactionQueue, Transitions transitions, Optional<DesktopTasksController> optional, DesktopImmersiveController desktopImmersiveController, AppToWebGenericLinksParser appToWebGenericLinksParser, AssistContentRequester assistContentRequester, WindowDecorViewHostSupplier windowDecorViewHostSupplier, MultiInstanceHelper multiInstanceHelper, DesktopModeWindowDecoration.Factory factory, InputMonitorFactory inputMonitorFactory, Supplier<SurfaceControl.Transaction> supplier, AppHeaderViewHolder.Factory factory2, AppHandleViewHolder.Factory factory3, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, SparseArray<DesktopModeWindowDecoration> sparseArray, InteractionJankMonitor interactionJankMonitor, Optional<DesktopTasksLimiter> optional2, AppHandleEducationController appHandleEducationController, AppToWebEducationController appToWebEducationController, AppHandleAndHeaderVisibilityHelper appHandleAndHeaderVisibilityHelper, WindowDecorCaptionHandleRepository windowDecorCaptionHandleRepository, Optional<DesktopActivityOrientationChangeHandler> optional3, TaskPositionerFactory taskPositionerFactory, FocusTransitionObserver focusTransitionObserver, DesktopModeEventLogger desktopModeEventLogger, DesktopModeUiEventLogger desktopModeUiEventLogger, WindowDecorTaskResourceLoader windowDecorTaskResourceLoader, RecentsTransitionHandler recentsTransitionHandler, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, ShellExecutor shellExecutor3, Lazy lazy, DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopTilingDecorViewModel desktopTilingDecorViewModel, MultiDisplayDragMoveIndicatorController multiDisplayDragMoveIndicatorController, CompatUIHandler compatUIHandler, DesksOrganizer desksOrganizer, DesktopState desktopState, DesktopConfig desktopConfig) throws Resources.NotFoundException {
        this.mEventReceiversByDisplay = new SparseArray();
        int i = 0;
        this.mExclusionRegionListener = new ExclusionRegionListenerImpl(this, i);
        this.mDragEventListener = new DragEventListenerImpl(this, i);
        this.mDragToDesktopAnimationStartBounds = new Rect();
        this.mDesktopModeKeyguardChangeListener = new DesktopModeKeyguardChangeListener();
        this.mTransitionToTaskInfo = new HashMap();
        this.mDecorViewModelState = new DecorViewModelState(this);
        final int i2 = 0;
        this.mDisplayChangingController = new DisplayChangeController.OnDisplayChangingListener(this) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Removed duplicated region for block: B:39:0x00bb  */
            /* JADX WARN: Removed duplicated region for block: B:40:0x00c1  */
            @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onDisplayChange(final int i3, final int i4, final int i5, final DisplayAreaInfo displayAreaInfo, final WindowContainerTransaction windowContainerTransaction) throws Resources.NotFoundException {
                switch (i2) {
                    case 0:
                        final DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                        desktopModeWindowDecorViewModel.forAllDecorations(new Consumer(i3, i4, i5, displayAreaInfo, windowContainerTransaction) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda26
                            public final /* synthetic */ int f$0;
                            public final /* synthetic */ int f$1;
                            public final /* synthetic */ int f$2;
                            public final /* synthetic */ WindowContainerTransaction f$4;

                            {
                                this.f$4 = windowContainerTransaction;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:103:0x01ce  */
                            /* JADX WARN: Removed duplicated region for block: B:118:0x01ee  */
                            /* JADX WARN: Removed duplicated region for block: B:221:0x043c  */
                            /* JADX WARN: Removed duplicated region for block: B:223:0x0454  */
                            /* JADX WARN: Removed duplicated region for block: B:57:0x013f  */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj) throws Resources.NotFoundException {
                                DisplayLayout displayLayout;
                                boolean z;
                                boolean z2;
                                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper;
                                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper2;
                                ActivityManager.RunningTaskInfo runningTaskInfo;
                                ActivityManager.RunningTaskInfo runningTaskInfo2;
                                ActivityManager.RunningTaskInfo runningTaskInfo3;
                                Rect rect;
                                Rect rect2;
                                ActivityManager.RunningTaskInfo runningTaskInfo4;
                                ActivityManager.RunningTaskInfo runningTaskInfo5;
                                Rect rect3;
                                Rect rect4;
                                ActivityManager.RunningTaskInfo runningTaskInfo6;
                                int i6;
                                View view;
                                int i7 = this.f$0;
                                int i8 = this.f$1;
                                int i9 = this.f$2;
                                WindowContainerTransaction windowContainerTransaction2 = this.f$4;
                                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) obj;
                                if (i7 == desktopModeWindowDecoration.mDisplay.getDisplayId() && (displayLayout = desktopModeWindowDecoration.mDisplayController.getDisplayLayout(desktopModeWindowDecoration.mTaskInfo.displayId)) != null) {
                                    Rect rect5 = new Rect();
                                    Rect rect6 = new Rect();
                                    if (desktopModeWindowDecoration.mDecorWindowContext != null && (((view = desktopModeWindowDecoration.mResult.mRootView) == null || !((WindowDecorLinearLayout) view).isAttachedToWindow() || displayLayout.mRotation != i9) && (i8 != -1 || i9 != -1))) {
                                        DisplayLayout displayLayout2 = new DisplayLayout(displayLayout);
                                        displayLayout2.rotateTo(desktopModeWindowDecoration.mDecorWindowContext.getResources(), i9);
                                        displayLayout = displayLayout2;
                                    }
                                    boolean z3 = false;
                                    rect6.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                                    displayLayout.getStableBounds(rect5, false);
                                    DisplayCutout displayCutout = displayLayout.mCutout;
                                    boolean z4 = CoreRune.MW_CAPTION_FREEFORM_STASH;
                                    if (z4 && desktopModeWindowDecoration.mFreeformStashState.isStashed()) {
                                        if (CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY && desktopModeWindowDecoration.mTaskInfo.getConfiguration().semDisplayDeviceType != 5) {
                                            z3 = true;
                                        }
                                        if (z3) {
                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration.mLastStableBounds, rect5, new Rect(desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash), desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash);
                                        } else {
                                            RotationUtils.rotateBounds(desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash, desktopModeWindowDecoration.mLastStableBounds, i8, i9);
                                        }
                                        Rect taskBounds = desktopModeWindowDecoration.getTaskBounds();
                                        if (z3) {
                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration.mLastStableBounds, rect5, new Rect(taskBounds), taskBounds);
                                        } else {
                                            RotationUtils.rotateBounds(taskBounds, desktopModeWindowDecoration.mLastStableBounds, i8, i9);
                                        }
                                        if (displayCutout != null) {
                                            Rect safeInsets = displayCutout.getSafeInsets();
                                            rect5.set(rect5.left - safeInsets.left, rect5.top, rect5.right + safeInsets.right, rect5.bottom);
                                        }
                                        TaskMotionController taskMotionController = desktopModeWindowDecoration.mTaskPositioner.getTaskMotionController();
                                        if (taskMotionController != null) {
                                            FreeformStashState freeformStashState = desktopModeWindowDecoration.mFreeformStashState;
                                            int iWidth = taskMotionController.mMinVisibleWidth;
                                            if (freeformStashState.mStashType != 0) {
                                                if (freeformStashState.isLeftStashed()) {
                                                    i6 = rect5.left + iWidth;
                                                    iWidth = taskBounds.width();
                                                } else {
                                                    i6 = rect5.right;
                                                }
                                                taskBounds.offsetTo(i6 - iWidth, (int) (freeformStashState.mFreeformStashYFraction * rect5.height()));
                                            }
                                        }
                                        windowContainerTransaction2.setBounds(desktopModeWindowDecoration.mTaskInfo.token, taskBounds);
                                        if (!taskBounds.isEmpty() && taskMotionController != null) {
                                            float fHeight = desktopModeWindowDecoration.mTaskPositioner.getTaskMotionController().mScaledFreeformHeight / taskBounds.height();
                                            if (desktopModeWindowDecoration.mFreeformStashState.mScale != fHeight) {
                                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                                windowContainerTransaction3.setChangeFreeformStashScale(desktopModeWindowDecoration.mTaskInfo.token, fHeight);
                                                desktopModeWindowDecoration.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                                            }
                                        }
                                    } else if (!z4 || !desktopModeWindowDecoration.mFreeformStashState.isStashed()) {
                                        int iDeltaRotation = RotationUtils.deltaRotation(i8, i9);
                                        if (desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                                            if ((iDeltaRotation == 1 || iDeltaRotation == 3) & (i7 == desktopModeWindowDecoration.mTaskInfo.getDisplayId())) {
                                                WindowContainerToken windowContainerToken = desktopModeWindowDecoration.mTaskInfo.token;
                                                if (desktopModeWindowDecoration.mIsTaskMaximized || desktopModeWindowDecoration.mDesktopUserRepositories.getCurrent().isTaskInFullImmersiveState(desktopModeWindowDecoration.mTaskInfo.taskId)) {
                                                    windowContainerTransaction2.setBounds(windowContainerToken, DesktopModeUtils.calculateMaximizeBounds(displayLayout, desktopModeWindowDecoration.mTaskInfo));
                                                    ActivityManager.RunningTaskInfo runningTaskInfo7 = desktopModeWindowDecoration.mTaskInfo;
                                                    DesktopStateImpl.Companion.getClass();
                                                    if (DesktopStateImpl.Companion.inNonResizableDesktopWindowing(runningTaskInfo7)) {
                                                        desktopModeWindowDecoration.mFreeformAdjustImeController.resetStateIfNeeded("rotationChanged");
                                                    }
                                                } else {
                                                    DesktopTilingDecorViewModel desktopTilingDecorViewModel2 = desktopModeWindowDecoration.mDesktopTilingDecorViewModel;
                                                    int i10 = desktopModeWindowDecoration.mTaskInfo.taskId;
                                                    DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel2.tilingTransitionHandlerByDisplayId.get(i7);
                                                    if (desktopTilingWindowDecoration == null || (appResizingHelper = desktopTilingWindowDecoration.leftTaskResizingHelper) == null || (appResizingHelper2 = desktopTilingWindowDecoration.rightTaskResizingHelper) == null || (((runningTaskInfo = appResizingHelper.taskInfo) == null || i10 != runningTaskInfo.taskId) && ((runningTaskInfo2 = appResizingHelper2.taskInfo) == null || i10 != runningTaskInfo2.taskId))) {
                                                        Rect taskBounds2 = desktopModeWindowDecoration.getTaskBounds();
                                                        if (!CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY || desktopModeWindowDecoration.mTaskInfo.getConfiguration().semDisplayDeviceType == 5) {
                                                            displayLayout.getDisplayBounds(desktopModeWindowDecoration.mTmpRect);
                                                            Rect rect7 = desktopModeWindowDecoration.mTmpRect;
                                                            z2 = false;
                                                            rect7.set(0, 0, rect7.height(), desktopModeWindowDecoration.mTmpRect.width());
                                                            RotationUtils.rotateBounds(taskBounds2, desktopModeWindowDecoration.mTmpRect, i8, i9);
                                                            RotationUtils.fitWithinBounds(taskBounds2, rect5, 48, 32);
                                                            int i11 = rect5.top - taskBounds2.top;
                                                            if (i11 > 0) {
                                                                taskBounds2.offset(0, i11);
                                                            }
                                                        } else {
                                                            DisplayLayout displayLayout3 = new DisplayLayout(displayLayout);
                                                            Context context2 = desktopModeWindowDecoration.mDecorWindowContext;
                                                            if (context2 != null) {
                                                                displayLayout3.rotateTo(context2.getResources(), i9);
                                                            } else {
                                                                Context displayContext = desktopModeWindowDecoration.mDisplayController.getDisplayContext(desktopModeWindowDecoration.mTaskInfo.displayId);
                                                                if (displayContext == null) {
                                                                    displayContext = desktopModeWindowDecoration.mContext;
                                                                }
                                                                displayLayout3.rotateTo(displayContext.getResources(), i9);
                                                            }
                                                            displayLayout3.getDisplayBounds(desktopModeWindowDecoration.mTmpRect2);
                                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration.mTmpRect, desktopModeWindowDecoration.mTmpRect2, taskBounds2, taskBounds2);
                                                            z2 = false;
                                                        }
                                                        ActivityManager.RunningTaskInfo runningTaskInfo8 = desktopModeWindowDecoration.mTaskInfo;
                                                        DesktopStateImpl.Companion.getClass();
                                                        if (DesktopStateImpl.Companion.inNonResizableDesktopWindowing(runningTaskInfo8)) {
                                                            boolean zIsTaskWidthOrHeightGreaterOrEqual = MultiWindowUtils.isTaskWidthOrHeightGreaterOrEqual(taskBounds2, rect5);
                                                            if (!zIsTaskWidthOrHeightGreaterOrEqual) {
                                                                Rect taskBounds3 = desktopModeWindowDecoration.getTaskBounds();
                                                                zIsTaskWidthOrHeightGreaterOrEqual = (taskBounds2.width() == taskBounds3.width() && taskBounds2.height() == taskBounds3.height()) ? z2 : true;
                                                            }
                                                            if (zIsTaskWidthOrHeightGreaterOrEqual) {
                                                                ActivityManager.RunningTaskInfo runningTaskInfo9 = desktopModeWindowDecoration.mTaskInfo;
                                                                ActivityInfo activityInfo = runningTaskInfo9.topActivityInfo;
                                                                if (activityInfo == null) {
                                                                    taskBounds2.set(DesktopModeUtils.calculateMaximizeBounds(displayLayout, runningTaskInfo9));
                                                                } else {
                                                                    MultiWindowUtils.calculateDesktopCompatInitialBounds(taskBounds2, activityInfo.screenOrientation, displayLayout.mWidth, displayLayout.mHeight, displayLayout.mCaptionInsets);
                                                                    taskBounds2.set(DesktopModeUtils.centerInArea(new Size(taskBounds2.width(), taskBounds2.height()), rect5, rect5.left, rect5.top));
                                                                }
                                                            }
                                                        }
                                                        windowContainerTransaction2.setBounds(windowContainerToken, taskBounds2);
                                                        windowContainerTransaction2.addChangeTransitFlags(windowContainerToken, 4);
                                                        desktopModeWindowDecoration.mFreeformAdjustImeController.resetStateIfNeeded("rotationChanged");
                                                    } else {
                                                        DesktopTilingDecorViewModel desktopTilingDecorViewModel3 = desktopModeWindowDecoration.mDesktopTilingDecorViewModel;
                                                        int i12 = desktopModeWindowDecoration.mTaskInfo.taskId;
                                                        DesktopTilingWindowDecoration desktopTilingWindowDecoration2 = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel3.tilingTransitionHandlerByDisplayId.get(i7);
                                                        if (desktopTilingWindowDecoration2 == null) {
                                                            if (desktopTilingWindowDecoration2 != null) {
                                                                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper3 = desktopTilingWindowDecoration2.rightTaskResizingHelper;
                                                                if (i12 == ((appResizingHelper3 == null || (runningTaskInfo3 = appResizingHelper3.taskInfo) == null) ? -1 : runningTaskInfo3.taskId)) {
                                                                    desktopTilingDecorViewModel3.rightTileReadyForRotation = true;
                                                                }
                                                            }
                                                            if (desktopTilingDecorViewModel3.leftTileReadyForRotation && desktopTilingDecorViewModel3.rightTileReadyForRotation) {
                                                                desktopTilingDecorViewModel3.leftTileReadyForRotation = false;
                                                                desktopTilingDecorViewModel3.rightTileReadyForRotation = false;
                                                                if (desktopTilingWindowDecoration2 != null) {
                                                                    DisplayController displayController2 = desktopTilingWindowDecoration2.displayController;
                                                                    int i13 = desktopTilingWindowDecoration2.displayId;
                                                                    DisplayLayout displayLayout4 = displayController2.getDisplayLayout(i13);
                                                                    Context displayContext2 = displayController2.getDisplayContext(i13);
                                                                    if (displayLayout4 != null && displayContext2 != null) {
                                                                        desktopTilingWindowDecoration2.tearDownTiling();
                                                                        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper4 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                                                                        Integer numValueOf = (appResizingHelper4 == null || (rect4 = appResizingHelper4.bounds) == null) ? null : Integer.valueOf(rect4.width());
                                                                        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper5 = desktopTilingWindowDecoration2.rightTaskResizingHelper;
                                                                        Integer numValueOf2 = (appResizingHelper5 == null || (rect3 = appResizingHelper5.bounds) == null) ? null : Integer.valueOf(rect3.width());
                                                                        if (numValueOf != null && numValueOf2 != null) {
                                                                            float fIntValue = (numValueOf.intValue() < 0 || numValueOf2.intValue() < 0) ? 0.0f : numValueOf.intValue() / (numValueOf2.intValue() + numValueOf.intValue());
                                                                            Rect rect8 = new Rect();
                                                                            displayLayout4.getStableBounds(rect8, false);
                                                                            int dimensionPixelSize = displayContext2.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
                                                                            Rect rect9 = new Rect();
                                                                            Rect rect10 = new Rect();
                                                                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper6 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                                                                            int minWidth = (int) DragPositioningCallbackUtility.getMinWidth(displayController2, appResizingHelper6 != null ? appResizingHelper6.desktopModeWindowDecoration : null, ((DesktopStateImpl) desktopTilingWindowDecoration2.desktopState).canEnterDesktopMode, true);
                                                                            if (fIntValue == 0.0f) {
                                                                                int iWidth2 = rect8.width() / 2;
                                                                                int i14 = rect8.left;
                                                                                rect9.set(i14, rect8.top, (iWidth2 + i14) - (dimensionPixelSize / 2), rect8.bottom);
                                                                                rect10.set(rect9.right + dimensionPixelSize, rect8.top, rect8.right, rect8.bottom);
                                                                            } else {
                                                                                rect9.set(rect8.left, rect8.top, (int) ((rect8.width() - dimensionPixelSize) * fIntValue), rect8.bottom);
                                                                                rect10.set(rect9.right + dimensionPixelSize, rect8.top, rect8.right, rect8.bottom);
                                                                                if (rect9.width() < minWidth) {
                                                                                    int i15 = rect9.left + minWidth;
                                                                                    rect9.right = i15;
                                                                                    rect10.set(i15 + dimensionPixelSize, rect8.top, rect8.right, rect8.bottom);
                                                                                } else if (rect10.width() < minWidth) {
                                                                                    int i16 = rect10.right - minWidth;
                                                                                    rect10.left = i16;
                                                                                    rect9.set(rect8.left, rect8.top, i16 - dimensionPixelSize, rect8.bottom);
                                                                                }
                                                                            }
                                                                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper7 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                                                                            WindowContainerToken windowContainerToken2 = (appResizingHelper7 == null || (runningTaskInfo5 = appResizingHelper7.taskInfo) == null) ? null : runningTaskInfo5.token;
                                                                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper8 = desktopTilingWindowDecoration2.rightTaskResizingHelper;
                                                                            WindowContainerToken windowContainerToken3 = (appResizingHelper8 == null || (runningTaskInfo4 = appResizingHelper8.taskInfo) == null) ? null : runningTaskInfo4.token;
                                                                            if (windowContainerToken2 != null && windowContainerToken3 != null) {
                                                                                if (appResizingHelper7 != null && (rect2 = appResizingHelper7.bounds) != null) {
                                                                                    rect2.set(rect9);
                                                                                }
                                                                                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper9 = desktopTilingWindowDecoration2.rightTaskResizingHelper;
                                                                                if (appResizingHelper9 != null && (rect = appResizingHelper9.bounds) != null) {
                                                                                    rect.set(rect10);
                                                                                }
                                                                                windowContainerTransaction2.setBounds(windowContainerToken2, rect9);
                                                                                windowContainerTransaction2.setBounds(windowContainerToken3, rect10);
                                                                                desktopTilingWindowDecoration2.transitions.startTransition(6, windowContainerTransaction2, null);
                                                                            }
                                                                            desktopTilingWindowDecoration2.initTilingForDisplayIfNeeded(false, desktopTilingWindowDecoration2.context.getResources().getConfiguration());
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper10 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                                                            if (i12 == ((appResizingHelper10 == null || (runningTaskInfo6 = appResizingHelper10.taskInfo) == null) ? -1 : runningTaskInfo6.taskId)) {
                                                                desktopTilingDecorViewModel3.leftTileReadyForRotation = true;
                                                            }
                                                            if (desktopTilingDecorViewModel3.leftTileReadyForRotation) {
                                                                desktopTilingDecorViewModel3.leftTileReadyForRotation = false;
                                                                desktopTilingDecorViewModel3.rightTileReadyForRotation = false;
                                                                if (desktopTilingWindowDecoration2 != null) {
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                z = true;
                                            }
                                            if (z) {
                                            }
                                        } else {
                                            z = false;
                                            if (z) {
                                                if (desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                                                    Rect rect11 = desktopModeWindowDecoration.mTmpRect;
                                                    MultiWindowUtils.scaleBounds(desktopModeWindowDecoration.mLastDisplayBounds, rect6, desktopModeWindowDecoration.getTaskBounds(), rect11);
                                                    WindowContainerToken windowContainerToken4 = desktopModeWindowDecoration.mTaskInfo.token;
                                                    windowContainerTransaction2.setBounds(windowContainerToken4, rect11);
                                                    windowContainerTransaction2.addChangeTransitFlags(windowContainerToken4, 4);
                                                }
                                            } else if (displayCutout != null) {
                                                Rect safeInsets2 = displayCutout.getSafeInsets();
                                                rect5.set(rect5.left - safeInsets2.left, rect5.top, rect5.right + safeInsets2.right, rect5.bottom);
                                            }
                                        }
                                    }
                                    desktopModeWindowDecoration.mLastDisplayBounds.set(rect6);
                                    desktopModeWindowDecoration.mLastStableBounds.set(rect5);
                                }
                            }
                        });
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(i3)) {
                            int iDeltaRotation = RotationUtils.deltaRotation(i4, i5);
                            if (iDeltaRotation == 1 || iDeltaRotation == 3) {
                                DisplayController displayController2 = desktopModeWindowDecorViewModel.mDisplayController;
                                DisplayLayout displayLayout = displayController2.getDisplayLayout(i3);
                                DisplayLayout displayLayout2 = null;
                                if (displayLayout != null) {
                                    if (displayLayout.mRotation != i5) {
                                        Context displayContext = displayController2.getDisplayContext(i3);
                                        if (displayContext == null) {
                                            Slog.w("DesktopModeWindowDecorViewModel", "getDisplayLayout: failed, cannot find context, d#" + i3);
                                        } else {
                                            displayLayout2 = new DisplayLayout(displayLayout);
                                            displayLayout2.rotateTo(displayContext.getResources(), i5);
                                        }
                                    }
                                    if (displayLayout == null) {
                                        final Rect rect = new Rect();
                                        displayLayout.getStableBounds(rect, false);
                                        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i4, i5, "handleRotationForNonDecorDesktopTasks: ", "->", ", display(#");
                                        sbM.append(i3);
                                        sbM.append(")=");
                                        sbM.append(rect);
                                        Slog.d("DesktopModeWindowDecorViewModel", sbM.toString());
                                        ((ArrayList) desktopModeWindowDecorViewModel.mTaskOrganizer.getVisibleTaskAppearedInfos(i3)).forEach(new Consumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda32
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = desktopModeWindowDecorViewModel;
                                                Rect rect2 = rect;
                                                WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                                                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) obj;
                                                desktopModeWindowDecorViewModel2.getClass();
                                                ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
                                                int taskId = taskInfo.getTaskId();
                                                if ((desktopModeWindowDecorViewModel2.mWindowDecorByTaskId.contains(taskId) || desktopModeWindowDecorViewModel2.shouldShowWindowDecor(taskInfo) || !taskInfo.isFreeform() || !taskInfo.configuration.windowConfiguration.getBounds().isValid()) ? false : desktopModeWindowDecorViewModel2.mDesktopUserRepositories.getCurrent().isActiveTask(taskId)) {
                                                    Rect bounds = taskInfo.configuration.windowConfiguration.getBounds();
                                                    Rect rect3 = new Rect(bounds);
                                                    MultiWindowUtils.fitInDisplayBounds(rect3, rect2);
                                                    windowContainerTransaction2.setBounds(taskInfo.token, rect3);
                                                    windowContainerTransaction2.addChangeTransitFlags(taskInfo.token, 4);
                                                    Slog.d("DesktopModeWindowDecorViewModel", "handleRotationForNonDecorDesktopTasks: " + taskAppearedInfo.getLeash() + ", old=" + bounds + ", new=" + rect3 + ", tid=" + taskInfo.taskId);
                                                }
                                            }
                                        });
                                        break;
                                    } else {
                                        RecordingInputConnection$$ExternalSyntheticOutline0.m(i3, "handleRotationForNonDecorDesktopTasks: failed, cannot find layout, #", "DesktopModeWindowDecorViewModel");
                                        break;
                                    }
                                } else {
                                    Slog.w("DesktopModeWindowDecorViewModel", "getDisplayLayout: failed, cannot find layout, d#" + i3);
                                }
                                displayLayout = displayLayout2;
                                if (displayLayout == null) {
                                }
                            }
                        }
                        break;
                    default:
                        int i6 = 0;
                        while (true) {
                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = this.f$0;
                            if (i6 >= desktopModeWindowDecorViewModel2.mWindowDecorByTaskId.size()) {
                                break;
                            } else {
                                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel2.mWindowDecorByTaskId.valueAt(i6);
                                if (desktopModeWindowDecoration != null) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                                    if (i3 == runningTaskInfo.displayId && runningTaskInfo.isFreeform() && i4 % 2 != i5 % 2) {
                                        Rect rect2 = new Rect(runningTaskInfo.configuration.windowConfiguration.getBounds());
                                        if (DragPositioningCallbackUtility.snapTaskBoundsIfNecessary(rect2, desktopModeWindowDecoration.calculateValidDragArea())) {
                                            windowContainerTransaction.setBounds(runningTaskInfo.token, rect2);
                                        }
                                    }
                                }
                                i6++;
                            }
                        }
                        break;
                }
            }
        };
        this.mIsMultiWindowDisabled = false;
        this.mAppearedTaskList = new HashMap();
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
        final int i3 = 1;
        this.mOnDisplayChangingListener = new DisplayChangeController.OnDisplayChangingListener(this) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ DesktopModeWindowDecorViewModel f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Removed duplicated region for block: B:39:0x00bb  */
            /* JADX WARN: Removed duplicated region for block: B:40:0x00c1  */
            @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onDisplayChange(final int i32, final int i4, final int i5, final DisplayAreaInfo displayAreaInfo, final WindowContainerTransaction windowContainerTransaction) throws Resources.NotFoundException {
                switch (i3) {
                    case 0:
                        final DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                        desktopModeWindowDecorViewModel.forAllDecorations(new Consumer(i32, i4, i5, displayAreaInfo, windowContainerTransaction) { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda26
                            public final /* synthetic */ int f$0;
                            public final /* synthetic */ int f$1;
                            public final /* synthetic */ int f$2;
                            public final /* synthetic */ WindowContainerTransaction f$4;

                            {
                                this.f$4 = windowContainerTransaction;
                            }

                            /* JADX WARN: Removed duplicated region for block: B:103:0x01ce  */
                            /* JADX WARN: Removed duplicated region for block: B:118:0x01ee  */
                            /* JADX WARN: Removed duplicated region for block: B:221:0x043c  */
                            /* JADX WARN: Removed duplicated region for block: B:223:0x0454  */
                            /* JADX WARN: Removed duplicated region for block: B:57:0x013f  */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj) throws Resources.NotFoundException {
                                DisplayLayout displayLayout;
                                boolean z;
                                boolean z2;
                                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper;
                                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper2;
                                ActivityManager.RunningTaskInfo runningTaskInfo;
                                ActivityManager.RunningTaskInfo runningTaskInfo2;
                                ActivityManager.RunningTaskInfo runningTaskInfo3;
                                Rect rect;
                                Rect rect2;
                                ActivityManager.RunningTaskInfo runningTaskInfo4;
                                ActivityManager.RunningTaskInfo runningTaskInfo5;
                                Rect rect3;
                                Rect rect4;
                                ActivityManager.RunningTaskInfo runningTaskInfo6;
                                int i6;
                                View view;
                                int i7 = this.f$0;
                                int i8 = this.f$1;
                                int i9 = this.f$2;
                                WindowContainerTransaction windowContainerTransaction2 = this.f$4;
                                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) obj;
                                if (i7 == desktopModeWindowDecoration.mDisplay.getDisplayId() && (displayLayout = desktopModeWindowDecoration.mDisplayController.getDisplayLayout(desktopModeWindowDecoration.mTaskInfo.displayId)) != null) {
                                    Rect rect5 = new Rect();
                                    Rect rect6 = new Rect();
                                    if (desktopModeWindowDecoration.mDecorWindowContext != null && (((view = desktopModeWindowDecoration.mResult.mRootView) == null || !((WindowDecorLinearLayout) view).isAttachedToWindow() || displayLayout.mRotation != i9) && (i8 != -1 || i9 != -1))) {
                                        DisplayLayout displayLayout2 = new DisplayLayout(displayLayout);
                                        displayLayout2.rotateTo(desktopModeWindowDecoration.mDecorWindowContext.getResources(), i9);
                                        displayLayout = displayLayout2;
                                    }
                                    boolean z3 = false;
                                    rect6.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                                    displayLayout.getStableBounds(rect5, false);
                                    DisplayCutout displayCutout = displayLayout.mCutout;
                                    boolean z4 = CoreRune.MW_CAPTION_FREEFORM_STASH;
                                    if (z4 && desktopModeWindowDecoration.mFreeformStashState.isStashed()) {
                                        if (CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY && desktopModeWindowDecoration.mTaskInfo.getConfiguration().semDisplayDeviceType != 5) {
                                            z3 = true;
                                        }
                                        if (z3) {
                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration.mLastStableBounds, rect5, new Rect(desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash), desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash);
                                        } else {
                                            RotationUtils.rotateBounds(desktopModeWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash, desktopModeWindowDecoration.mLastStableBounds, i8, i9);
                                        }
                                        Rect taskBounds = desktopModeWindowDecoration.getTaskBounds();
                                        if (z3) {
                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration.mLastStableBounds, rect5, new Rect(taskBounds), taskBounds);
                                        } else {
                                            RotationUtils.rotateBounds(taskBounds, desktopModeWindowDecoration.mLastStableBounds, i8, i9);
                                        }
                                        if (displayCutout != null) {
                                            Rect safeInsets = displayCutout.getSafeInsets();
                                            rect5.set(rect5.left - safeInsets.left, rect5.top, rect5.right + safeInsets.right, rect5.bottom);
                                        }
                                        TaskMotionController taskMotionController = desktopModeWindowDecoration.mTaskPositioner.getTaskMotionController();
                                        if (taskMotionController != null) {
                                            FreeformStashState freeformStashState = desktopModeWindowDecoration.mFreeformStashState;
                                            int iWidth = taskMotionController.mMinVisibleWidth;
                                            if (freeformStashState.mStashType != 0) {
                                                if (freeformStashState.isLeftStashed()) {
                                                    i6 = rect5.left + iWidth;
                                                    iWidth = taskBounds.width();
                                                } else {
                                                    i6 = rect5.right;
                                                }
                                                taskBounds.offsetTo(i6 - iWidth, (int) (freeformStashState.mFreeformStashYFraction * rect5.height()));
                                            }
                                        }
                                        windowContainerTransaction2.setBounds(desktopModeWindowDecoration.mTaskInfo.token, taskBounds);
                                        if (!taskBounds.isEmpty() && taskMotionController != null) {
                                            float fHeight = desktopModeWindowDecoration.mTaskPositioner.getTaskMotionController().mScaledFreeformHeight / taskBounds.height();
                                            if (desktopModeWindowDecoration.mFreeformStashState.mScale != fHeight) {
                                                WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                                windowContainerTransaction3.setChangeFreeformStashScale(desktopModeWindowDecoration.mTaskInfo.token, fHeight);
                                                desktopModeWindowDecoration.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                                            }
                                        }
                                    } else if (!z4 || !desktopModeWindowDecoration.mFreeformStashState.isStashed()) {
                                        int iDeltaRotation = RotationUtils.deltaRotation(i8, i9);
                                        if (desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                                            if ((iDeltaRotation == 1 || iDeltaRotation == 3) & (i7 == desktopModeWindowDecoration.mTaskInfo.getDisplayId())) {
                                                WindowContainerToken windowContainerToken = desktopModeWindowDecoration.mTaskInfo.token;
                                                if (desktopModeWindowDecoration.mIsTaskMaximized || desktopModeWindowDecoration.mDesktopUserRepositories.getCurrent().isTaskInFullImmersiveState(desktopModeWindowDecoration.mTaskInfo.taskId)) {
                                                    windowContainerTransaction2.setBounds(windowContainerToken, DesktopModeUtils.calculateMaximizeBounds(displayLayout, desktopModeWindowDecoration.mTaskInfo));
                                                    ActivityManager.RunningTaskInfo runningTaskInfo7 = desktopModeWindowDecoration.mTaskInfo;
                                                    DesktopStateImpl.Companion.getClass();
                                                    if (DesktopStateImpl.Companion.inNonResizableDesktopWindowing(runningTaskInfo7)) {
                                                        desktopModeWindowDecoration.mFreeformAdjustImeController.resetStateIfNeeded("rotationChanged");
                                                    }
                                                } else {
                                                    DesktopTilingDecorViewModel desktopTilingDecorViewModel2 = desktopModeWindowDecoration.mDesktopTilingDecorViewModel;
                                                    int i10 = desktopModeWindowDecoration.mTaskInfo.taskId;
                                                    DesktopTilingWindowDecoration desktopTilingWindowDecoration = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel2.tilingTransitionHandlerByDisplayId.get(i7);
                                                    if (desktopTilingWindowDecoration == null || (appResizingHelper = desktopTilingWindowDecoration.leftTaskResizingHelper) == null || (appResizingHelper2 = desktopTilingWindowDecoration.rightTaskResizingHelper) == null || (((runningTaskInfo = appResizingHelper.taskInfo) == null || i10 != runningTaskInfo.taskId) && ((runningTaskInfo2 = appResizingHelper2.taskInfo) == null || i10 != runningTaskInfo2.taskId))) {
                                                        Rect taskBounds2 = desktopModeWindowDecoration.getTaskBounds();
                                                        if (!CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY || desktopModeWindowDecoration.mTaskInfo.getConfiguration().semDisplayDeviceType == 5) {
                                                            displayLayout.getDisplayBounds(desktopModeWindowDecoration.mTmpRect);
                                                            Rect rect7 = desktopModeWindowDecoration.mTmpRect;
                                                            z2 = false;
                                                            rect7.set(0, 0, rect7.height(), desktopModeWindowDecoration.mTmpRect.width());
                                                            RotationUtils.rotateBounds(taskBounds2, desktopModeWindowDecoration.mTmpRect, i8, i9);
                                                            RotationUtils.fitWithinBounds(taskBounds2, rect5, 48, 32);
                                                            int i11 = rect5.top - taskBounds2.top;
                                                            if (i11 > 0) {
                                                                taskBounds2.offset(0, i11);
                                                            }
                                                        } else {
                                                            DisplayLayout displayLayout3 = new DisplayLayout(displayLayout);
                                                            Context context2 = desktopModeWindowDecoration.mDecorWindowContext;
                                                            if (context2 != null) {
                                                                displayLayout3.rotateTo(context2.getResources(), i9);
                                                            } else {
                                                                Context displayContext = desktopModeWindowDecoration.mDisplayController.getDisplayContext(desktopModeWindowDecoration.mTaskInfo.displayId);
                                                                if (displayContext == null) {
                                                                    displayContext = desktopModeWindowDecoration.mContext;
                                                                }
                                                                displayLayout3.rotateTo(displayContext.getResources(), i9);
                                                            }
                                                            displayLayout3.getDisplayBounds(desktopModeWindowDecoration.mTmpRect2);
                                                            MultiWindowUtils.adjustBoundsForScreenRatio(desktopModeWindowDecoration.mTmpRect, desktopModeWindowDecoration.mTmpRect2, taskBounds2, taskBounds2);
                                                            z2 = false;
                                                        }
                                                        ActivityManager.RunningTaskInfo runningTaskInfo8 = desktopModeWindowDecoration.mTaskInfo;
                                                        DesktopStateImpl.Companion.getClass();
                                                        if (DesktopStateImpl.Companion.inNonResizableDesktopWindowing(runningTaskInfo8)) {
                                                            boolean zIsTaskWidthOrHeightGreaterOrEqual = MultiWindowUtils.isTaskWidthOrHeightGreaterOrEqual(taskBounds2, rect5);
                                                            if (!zIsTaskWidthOrHeightGreaterOrEqual) {
                                                                Rect taskBounds3 = desktopModeWindowDecoration.getTaskBounds();
                                                                zIsTaskWidthOrHeightGreaterOrEqual = (taskBounds2.width() == taskBounds3.width() && taskBounds2.height() == taskBounds3.height()) ? z2 : true;
                                                            }
                                                            if (zIsTaskWidthOrHeightGreaterOrEqual) {
                                                                ActivityManager.RunningTaskInfo runningTaskInfo9 = desktopModeWindowDecoration.mTaskInfo;
                                                                ActivityInfo activityInfo = runningTaskInfo9.topActivityInfo;
                                                                if (activityInfo == null) {
                                                                    taskBounds2.set(DesktopModeUtils.calculateMaximizeBounds(displayLayout, runningTaskInfo9));
                                                                } else {
                                                                    MultiWindowUtils.calculateDesktopCompatInitialBounds(taskBounds2, activityInfo.screenOrientation, displayLayout.mWidth, displayLayout.mHeight, displayLayout.mCaptionInsets);
                                                                    taskBounds2.set(DesktopModeUtils.centerInArea(new Size(taskBounds2.width(), taskBounds2.height()), rect5, rect5.left, rect5.top));
                                                                }
                                                            }
                                                        }
                                                        windowContainerTransaction2.setBounds(windowContainerToken, taskBounds2);
                                                        windowContainerTransaction2.addChangeTransitFlags(windowContainerToken, 4);
                                                        desktopModeWindowDecoration.mFreeformAdjustImeController.resetStateIfNeeded("rotationChanged");
                                                    } else {
                                                        DesktopTilingDecorViewModel desktopTilingDecorViewModel3 = desktopModeWindowDecoration.mDesktopTilingDecorViewModel;
                                                        int i12 = desktopModeWindowDecoration.mTaskInfo.taskId;
                                                        DesktopTilingWindowDecoration desktopTilingWindowDecoration2 = (DesktopTilingWindowDecoration) desktopTilingDecorViewModel3.tilingTransitionHandlerByDisplayId.get(i7);
                                                        if (desktopTilingWindowDecoration2 == null) {
                                                            if (desktopTilingWindowDecoration2 != null) {
                                                                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper3 = desktopTilingWindowDecoration2.rightTaskResizingHelper;
                                                                if (i12 == ((appResizingHelper3 == null || (runningTaskInfo3 = appResizingHelper3.taskInfo) == null) ? -1 : runningTaskInfo3.taskId)) {
                                                                    desktopTilingDecorViewModel3.rightTileReadyForRotation = true;
                                                                }
                                                            }
                                                            if (desktopTilingDecorViewModel3.leftTileReadyForRotation && desktopTilingDecorViewModel3.rightTileReadyForRotation) {
                                                                desktopTilingDecorViewModel3.leftTileReadyForRotation = false;
                                                                desktopTilingDecorViewModel3.rightTileReadyForRotation = false;
                                                                if (desktopTilingWindowDecoration2 != null) {
                                                                    DisplayController displayController2 = desktopTilingWindowDecoration2.displayController;
                                                                    int i13 = desktopTilingWindowDecoration2.displayId;
                                                                    DisplayLayout displayLayout4 = displayController2.getDisplayLayout(i13);
                                                                    Context displayContext2 = displayController2.getDisplayContext(i13);
                                                                    if (displayLayout4 != null && displayContext2 != null) {
                                                                        desktopTilingWindowDecoration2.tearDownTiling();
                                                                        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper4 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                                                                        Integer numValueOf = (appResizingHelper4 == null || (rect4 = appResizingHelper4.bounds) == null) ? null : Integer.valueOf(rect4.width());
                                                                        DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper5 = desktopTilingWindowDecoration2.rightTaskResizingHelper;
                                                                        Integer numValueOf2 = (appResizingHelper5 == null || (rect3 = appResizingHelper5.bounds) == null) ? null : Integer.valueOf(rect3.width());
                                                                        if (numValueOf != null && numValueOf2 != null) {
                                                                            float fIntValue = (numValueOf.intValue() < 0 || numValueOf2.intValue() < 0) ? 0.0f : numValueOf.intValue() / (numValueOf2.intValue() + numValueOf.intValue());
                                                                            Rect rect8 = new Rect();
                                                                            displayLayout4.getStableBounds(rect8, false);
                                                                            int dimensionPixelSize = displayContext2.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
                                                                            Rect rect9 = new Rect();
                                                                            Rect rect10 = new Rect();
                                                                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper6 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                                                                            int minWidth = (int) DragPositioningCallbackUtility.getMinWidth(displayController2, appResizingHelper6 != null ? appResizingHelper6.desktopModeWindowDecoration : null, ((DesktopStateImpl) desktopTilingWindowDecoration2.desktopState).canEnterDesktopMode, true);
                                                                            if (fIntValue == 0.0f) {
                                                                                int iWidth2 = rect8.width() / 2;
                                                                                int i14 = rect8.left;
                                                                                rect9.set(i14, rect8.top, (iWidth2 + i14) - (dimensionPixelSize / 2), rect8.bottom);
                                                                                rect10.set(rect9.right + dimensionPixelSize, rect8.top, rect8.right, rect8.bottom);
                                                                            } else {
                                                                                rect9.set(rect8.left, rect8.top, (int) ((rect8.width() - dimensionPixelSize) * fIntValue), rect8.bottom);
                                                                                rect10.set(rect9.right + dimensionPixelSize, rect8.top, rect8.right, rect8.bottom);
                                                                                if (rect9.width() < minWidth) {
                                                                                    int i15 = rect9.left + minWidth;
                                                                                    rect9.right = i15;
                                                                                    rect10.set(i15 + dimensionPixelSize, rect8.top, rect8.right, rect8.bottom);
                                                                                } else if (rect10.width() < minWidth) {
                                                                                    int i16 = rect10.right - minWidth;
                                                                                    rect10.left = i16;
                                                                                    rect9.set(rect8.left, rect8.top, i16 - dimensionPixelSize, rect8.bottom);
                                                                                }
                                                                            }
                                                                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper7 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                                                                            WindowContainerToken windowContainerToken2 = (appResizingHelper7 == null || (runningTaskInfo5 = appResizingHelper7.taskInfo) == null) ? null : runningTaskInfo5.token;
                                                                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper8 = desktopTilingWindowDecoration2.rightTaskResizingHelper;
                                                                            WindowContainerToken windowContainerToken3 = (appResizingHelper8 == null || (runningTaskInfo4 = appResizingHelper8.taskInfo) == null) ? null : runningTaskInfo4.token;
                                                                            if (windowContainerToken2 != null && windowContainerToken3 != null) {
                                                                                if (appResizingHelper7 != null && (rect2 = appResizingHelper7.bounds) != null) {
                                                                                    rect2.set(rect9);
                                                                                }
                                                                                DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper9 = desktopTilingWindowDecoration2.rightTaskResizingHelper;
                                                                                if (appResizingHelper9 != null && (rect = appResizingHelper9.bounds) != null) {
                                                                                    rect.set(rect10);
                                                                                }
                                                                                windowContainerTransaction2.setBounds(windowContainerToken2, rect9);
                                                                                windowContainerTransaction2.setBounds(windowContainerToken3, rect10);
                                                                                desktopTilingWindowDecoration2.transitions.startTransition(6, windowContainerTransaction2, null);
                                                                            }
                                                                            desktopTilingWindowDecoration2.initTilingForDisplayIfNeeded(false, desktopTilingWindowDecoration2.context.getResources().getConfiguration());
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            DesktopTilingWindowDecoration.AppResizingHelper appResizingHelper10 = desktopTilingWindowDecoration2.leftTaskResizingHelper;
                                                            if (i12 == ((appResizingHelper10 == null || (runningTaskInfo6 = appResizingHelper10.taskInfo) == null) ? -1 : runningTaskInfo6.taskId)) {
                                                                desktopTilingDecorViewModel3.leftTileReadyForRotation = true;
                                                            }
                                                            if (desktopTilingDecorViewModel3.leftTileReadyForRotation) {
                                                                desktopTilingDecorViewModel3.leftTileReadyForRotation = false;
                                                                desktopTilingDecorViewModel3.rightTileReadyForRotation = false;
                                                                if (desktopTilingWindowDecoration2 != null) {
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                z = true;
                                            }
                                            if (z) {
                                            }
                                        } else {
                                            z = false;
                                            if (z) {
                                                if (desktopModeWindowDecoration.mTaskInfo.isFreeform()) {
                                                    Rect rect11 = desktopModeWindowDecoration.mTmpRect;
                                                    MultiWindowUtils.scaleBounds(desktopModeWindowDecoration.mLastDisplayBounds, rect6, desktopModeWindowDecoration.getTaskBounds(), rect11);
                                                    WindowContainerToken windowContainerToken4 = desktopModeWindowDecoration.mTaskInfo.token;
                                                    windowContainerTransaction2.setBounds(windowContainerToken4, rect11);
                                                    windowContainerTransaction2.addChangeTransitFlags(windowContainerToken4, 4);
                                                }
                                            } else if (displayCutout != null) {
                                                Rect safeInsets2 = displayCutout.getSafeInsets();
                                                rect5.set(rect5.left - safeInsets2.left, rect5.top, rect5.right + safeInsets2.right, rect5.bottom);
                                            }
                                        }
                                    }
                                    desktopModeWindowDecoration.mLastDisplayBounds.set(rect6);
                                    desktopModeWindowDecoration.mLastStableBounds.set(rect5);
                                }
                            }
                        });
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(i32)) {
                            int iDeltaRotation = RotationUtils.deltaRotation(i4, i5);
                            if (iDeltaRotation == 1 || iDeltaRotation == 3) {
                                DisplayController displayController2 = desktopModeWindowDecorViewModel.mDisplayController;
                                DisplayLayout displayLayout = displayController2.getDisplayLayout(i32);
                                DisplayLayout displayLayout2 = null;
                                if (displayLayout != null) {
                                    if (displayLayout.mRotation != i5) {
                                        Context displayContext = displayController2.getDisplayContext(i32);
                                        if (displayContext == null) {
                                            Slog.w("DesktopModeWindowDecorViewModel", "getDisplayLayout: failed, cannot find context, d#" + i32);
                                        } else {
                                            displayLayout2 = new DisplayLayout(displayLayout);
                                            displayLayout2.rotateTo(displayContext.getResources(), i5);
                                        }
                                    }
                                    if (displayLayout == null) {
                                        final Rect rect = new Rect();
                                        displayLayout.getStableBounds(rect, false);
                                        StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i4, i5, "handleRotationForNonDecorDesktopTasks: ", "->", ", display(#");
                                        sbM.append(i32);
                                        sbM.append(")=");
                                        sbM.append(rect);
                                        Slog.d("DesktopModeWindowDecorViewModel", sbM.toString());
                                        ((ArrayList) desktopModeWindowDecorViewModel.mTaskOrganizer.getVisibleTaskAppearedInfos(i32)).forEach(new Consumer() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda32
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = desktopModeWindowDecorViewModel;
                                                Rect rect2 = rect;
                                                WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                                                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) obj;
                                                desktopModeWindowDecorViewModel2.getClass();
                                                ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
                                                int taskId = taskInfo.getTaskId();
                                                if ((desktopModeWindowDecorViewModel2.mWindowDecorByTaskId.contains(taskId) || desktopModeWindowDecorViewModel2.shouldShowWindowDecor(taskInfo) || !taskInfo.isFreeform() || !taskInfo.configuration.windowConfiguration.getBounds().isValid()) ? false : desktopModeWindowDecorViewModel2.mDesktopUserRepositories.getCurrent().isActiveTask(taskId)) {
                                                    Rect bounds = taskInfo.configuration.windowConfiguration.getBounds();
                                                    Rect rect3 = new Rect(bounds);
                                                    MultiWindowUtils.fitInDisplayBounds(rect3, rect2);
                                                    windowContainerTransaction2.setBounds(taskInfo.token, rect3);
                                                    windowContainerTransaction2.addChangeTransitFlags(taskInfo.token, 4);
                                                    Slog.d("DesktopModeWindowDecorViewModel", "handleRotationForNonDecorDesktopTasks: " + taskAppearedInfo.getLeash() + ", old=" + bounds + ", new=" + rect3 + ", tid=" + taskInfo.taskId);
                                                }
                                            }
                                        });
                                        break;
                                    } else {
                                        RecordingInputConnection$$ExternalSyntheticOutline0.m(i32, "handleRotationForNonDecorDesktopTasks: failed, cannot find layout, #", "DesktopModeWindowDecorViewModel");
                                        break;
                                    }
                                } else {
                                    Slog.w("DesktopModeWindowDecorViewModel", "getDisplayLayout: failed, cannot find layout, d#" + i32);
                                }
                                displayLayout = displayLayout2;
                                if (displayLayout == null) {
                                }
                            }
                        }
                        break;
                    default:
                        int i6 = 0;
                        while (true) {
                            DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel2 = this.f$0;
                            if (i6 >= desktopModeWindowDecorViewModel2.mWindowDecorByTaskId.size()) {
                                break;
                            } else {
                                DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel2.mWindowDecorByTaskId.valueAt(i6);
                                if (desktopModeWindowDecoration != null) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo = desktopModeWindowDecoration.mTaskInfo;
                                    if (i32 == runningTaskInfo.displayId && runningTaskInfo.isFreeform() && i4 % 2 != i5 % 2) {
                                        Rect rect2 = new Rect(runningTaskInfo.configuration.windowConfiguration.getBounds());
                                        if (DragPositioningCallbackUtility.snapTaskBoundsIfNecessary(rect2, desktopModeWindowDecoration.calculateValidDragArea())) {
                                            windowContainerTransaction.setBounds(runningTaskInfo.token, rect2);
                                        }
                                    }
                                }
                                i6++;
                            }
                        }
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
        this.mGestureExclusionTracker = new WindowDecorationGestureExclusionTracker(context, iWindowManager, displayController, shellExecutor, shellInit, new Function2() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Region region = (Region) obj2;
                DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                int iIntValue = ((Integer) obj).intValue();
                int size = desktopModeWindowDecorViewModel.mWindowDecorByTaskId.size();
                for (int i4 = 0; i4 < size; i4++) {
                    DesktopModeWindowDecoration desktopModeWindowDecoration = (DesktopModeWindowDecoration) desktopModeWindowDecorViewModel.mWindowDecorByTaskId.valueAt(i4);
                    if (desktopModeWindowDecoration.mTaskInfo.displayId == iIntValue && (!CoreRune.MW_CAPTION || !DesktopModeWindowDecoration.isAppHandle(desktopModeWindowDecoration.mWindowDecorViewHolder))) {
                        desktopModeWindowDecoration.relayout(desktopModeWindowDecoration.mTaskInfo, desktopModeWindowDecoration.mHasGlobalFocus, region);
                    }
                }
                return Unit.INSTANCE;
            }
        });
        shellInit.addInitCallback(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda3(this, 0), this);
        this.mMultiTaskingHeaderViewHolderFactory = new MultiTaskingHeaderViewHolder.Factory();
        this.mNSController = naturalSwitchingDropTargetController;
        this.mAnimExecutor = shellExecutor3;
        if (CoreRune.MW_CAPTION) {
            ((RootTaskDesksOrganizer) desktopTasksController.desksOrganizer).desktopModeWindowDecorViewModel = this;
        }
        if (CoreRune.MW_CAPTION_SPLIT_IMMERSIVE) {
            boolean z = MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
            shellTaskOrganizer.registerMultiWindowCoreStateListener(new ShellTaskOrganizer.MultiWindowCoreStateChangeListener() { // from class: com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda4
                @Override // com.android.wm.shell.ShellTaskOrganizer.MultiWindowCoreStateChangeListener
                public final boolean onMultiWindowCoreStateChanged(int i4) {
                    DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = this.f$0;
                    desktopModeWindowDecorViewModel.getClass();
                    if ((i4 & 512) == 0) {
                        return false;
                    }
                    boolean z2 = MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED;
                    desktopModeWindowDecorViewModel.forAllDecorations(new DesktopModeWindowDecorViewModel$$ExternalSyntheticLambda23(1));
                    return false;
                }
            });
        }
        if (CoreRune.MW_SA_LOGGING) {
            this.mCaptionButtonLogger = new MultiTaskingCaptionButtonLogger();
        }
        if (CoreRune.MW_CAPTION_DESKTOP_DISABLED) {
            int i4 = desktopTasksController.desktopDisabledFlagsOnDefaultDisplay;
            boolean z2 = true;
            if ((i4 & 8) == 0 && (i4 & 1) == 0) {
                z2 = false;
            }
            this.mIsMultiWindowDisabled = z2;
            if (((DesktopStateImpl) desktopState).isDesktopModeSupportedOnDisplay(0)) {
                desktopTasksController.decorViewModelDesktopDisabledChangeListener = new AnonymousClass1();
            }
        }
        this.mPipOptionalLazy = lazy;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldShowWindowDecor(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        ComponentName componentName;
        if (CoreRune.BAIDU_CARLIFE) {
            Display display = this.mDisplayController.mDisplayManager.getDisplay(runningTaskInfo.displayId);
            if (display == null || (display.getFlags() & 1048576) == 0) {
                boolean z2 = CoreRune.MW_CAPTION;
                if (z2) {
                    if (!((RootTaskDesksOrganizer) this.mDesktopTasksController.desksOrganizer).deskRootsByDeskId.contains(runningTaskInfo.taskId)) {
                        if ((runningTaskInfo.getWindowingMode() != 1 || ((!CoreRune.MW_CAPTION_KEYGUARD || !this.mIsKeyguardShowing) && ((!CoreRune.MW_CAPTION_DESKTOP || !this.mDesktopModeCompatPolicy.isTopActivityExemptFromDesktopWindowing(runningTaskInfo)) && (!CoreRune.MW_CAPTION_DESKTOP_DISABLED || (mDesktopDisabledFlags != 8 && MultiWindowCoreState.MW_ENABLED))))) && (!CoreRune.MW_SHELL_TRANSITION_BUG_FIX || z || !isExitingPipToLastParent(runningTaskInfo))) {
                            AppHandleAndHeaderVisibilityHelper appHandleAndHeaderVisibilityHelper = this.mAppHandleAndHeaderVisibilityHelper;
                            appHandleAndHeaderVisibilityHelper.getClass();
                            int i = runningTaskInfo.displayId;
                            DisplayController displayController = appHandleAndHeaderVisibilityHelper.displayController;
                            Display display2 = displayController.mDisplayManager.getDisplay(i);
                            if (display2 != null) {
                                DesktopState desktopState = appHandleAndHeaderVisibilityHelper.desktopState;
                                if (z2) {
                                    SplitScreenController splitScreenController = appHandleAndHeaderVisibilityHelper.splitScreenController;
                                    if ((splitScreenController == null || !splitScreenController.isTaskRootOrStageRoot(runningTaskInfo.taskId)) && !CoreRune.IS_FACTORY_BINARY && (!z2 || !runningTaskInfo.isTranslucentTask)) {
                                        if (CoreRune.MW_CAPTION_HANDLE) {
                                            if ((runningTaskInfo.getWindowingMode() == 5 || !runningTaskInfo.isCaptionHiddenRequested) && CaptionGlobalState.USER_SETUP_COMPLETED) {
                                                runningTaskInfo.isSplitScreen();
                                            }
                                        }
                                        if (runningTaskInfo.resizeMode != 10) {
                                            DesktopWallpaperActivity.Companion.getClass();
                                            if (!DesktopWallpaperActivity.Companion.isWallpaperTask(runningTaskInfo) && runningTaskInfo.getWindowingMode() != 2 && runningTaskInfo.getActivityType() == 1) {
                                                if (runningTaskInfo.getWindowingMode() == 1) {
                                                    if (CoreRune.MW_CAPTION_FULL_SCREEN && !runningTaskInfo.isLaunchedFromHomeSwipe && runningTaskInfo.isFullSizeWindow && (((componentName = runningTaskInfo.realActivity) == null || !MultiWindowUtils.isCtsPackage(componentName.getPackageName())) && runningTaskInfo.configuration.semDisplayDeviceType != 5)) {
                                                        DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
                                                        int displayId = display2.getDisplayId();
                                                        companion.getClass();
                                                        if (!DesktopStateImpl.Companion.inDesktopWindowing(displayId) && CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED && ((runningTaskInfo.supportsMultiWindow || ((DesktopStateImpl) desktopState).isDesktopModeSupportedOnDisplay(display2)) && runningTaskInfo.getWindowingMode() == 1 && runningTaskInfo.getActivityType() == 1)) {
                                                            return true;
                                                        }
                                                    }
                                                } else if (runningTaskInfo.isSplitScreen()) {
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (runningTaskInfo.getWindowingMode() != 5) {
                                        if (!DesktopExperienceFlags.ENABLE_BUG_FIXES_FOR_SECONDARY_DISPLAY.isTrue()) {
                                            return appHandleAndHeaderVisibilityHelper.allowedForTask(runningTaskInfo, display2);
                                        }
                                        if (appHandleAndHeaderVisibilityHelper.allowedForTask(runningTaskInfo, display2)) {
                                            if (display2.getType() != 1) {
                                                int displayId2 = display2.getDisplayId();
                                                DisplayTopology displayTopology = displayController.mDisplayTopology;
                                                if (displayTopology != null && DisplayTopology.findDisplay(displayId2, displayTopology.getRoot()) != null) {
                                                }
                                            }
                                            if (!((DesktopStateImpl) desktopState).isDesktopModeSupportedOnDisplay(display2)) {
                                                desktopState.getClass();
                                            }
                                        }
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
