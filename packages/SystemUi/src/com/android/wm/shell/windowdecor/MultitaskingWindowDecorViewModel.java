package com.android.wm.shell.windowdecor;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArraySet;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Choreographer;
import android.view.ContextThemeWrapper;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.SeekBar;
import android.window.DisplayAreaInfo;
import android.window.TaskAppearedInfo;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.common.DismissButtonManager;
import com.android.wm.shell.common.DismissButtonManager$$ExternalSyntheticLambda0;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.FreeformDragPositioningController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.desktopmode.DesktopModeController;
import com.android.wm.shell.freeform.AdjustImeStateController;
import com.android.wm.shell.freeform.DexSnappingGuide;
import com.android.wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.shortcut.DexCompatRestartDialogUtils;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.windowdecor.DragDetector;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecoration;
import com.android.wm.shell.windowdecor.TaskMotionController;
import com.android.wm.shell.windowdecor.WindowDecoration;
import com.android.wm.shell.windowdecor.animation.CaptionAnimationUtils;
import com.android.wm.shell.windowdecor.widget.HandleView;
import com.android.wm.shell.windowdecor.widget.OutlineView;
import com.android.systemui.R;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.IDexTransientCaptionDelayListener;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MultitaskingWindowDecorViewModel implements WindowDecorViewModel, DisplayController.OnDisplaysChangedListener, AdjustImeStateController {
    public int mAddedDisplayId;
    public final ShellExecutor mAnimExecutor;
    public final Rect mAnimatedBounds;
    public final Context mContext;
    public final DexCompatRestartDialogUtils mDexCompatRestartDialogUtils;
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final Rect mImeAdjustedTargetBounds;
    public boolean mIsPinned;
    public boolean mIsTranslucent;
    public final C41741 mKeyguardChangeListener;
    public MultitaskingWindowDecoration mLastImmersiveDecoration;
    public int mLastImmersiveTaskId;
    public final Choreographer mMainChoreographer;
    public final Handler mMainHandler;
    public final NaturalSwitchingDropTargetController mNSController;
    public int mPinTaskId;
    public final Optional mPipOptional;
    public final MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda0 mRotationController;
    public SettingsObserver mSettingsObserver;
    public final Optional mSplitScreenController;
    public final SyncTransactionQueue mSyncQueue;
    public TaskOperations mTaskOperations;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Rect mTmpRect;
    public final Transitions mTransitions;
    public final SparseArray mWindowDecorByTaskId = new SparseArray();
    public final Map mTransitionToTaskInfo = new HashMap();
    public final ArraySet mFixedRotatingDisplayIds = new ArraySet();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class CaptionTouchEventListener implements View.OnClickListener, View.OnTouchListener, DragDetector.MotionEventHandler, SeekBar.OnSeekBarChangeListener, GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener, View.OnHoverListener {
        public final AccessibilityManager mAccessibilityManager;
        public final RunnableC4173x28c31a39 mDismissRunnable;
        public final DragDetector mDragDetector;
        public int mDragPointerId;
        public final DragPositioningCallback mDragPositioningCallback;
        public final FreeformCaptionTouchState mFreeformCaptionTouchState;
        public final GestureDetector mGestureDetector;
        public boolean mIsButtonLongPressed;
        public boolean mIsButtonTouched;
        public boolean mIsDoubleTapEnabled;
        public boolean mIsDragging;
        public boolean mIsLongPressed;
        public boolean mIsScrolled;
        public MotionEvent mLongPressMotionEvent;
        public View mTargetView;
        public final int mTaskId;
        public final TaskPositioner mTaskPositioner;
        public final WindowContainerToken mTaskToken;

        public /* synthetic */ CaptionTouchEventListener(MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel, ActivityManager.RunningTaskInfo runningTaskInfo, TaskPositioner taskPositioner) {
            this(runningTaskInfo, (DragPositioningCallback) taskPositioner);
        }

        public final void dismissPopup() {
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration == null) {
                return;
            }
            boolean z = false;
            if (multitaskingWindowDecoration.mIsDexEnabled) {
                if (multitaskingWindowDecoration.mSliderPopup != null && multitaskingWindowDecoration.mIsSliderPopupShowing) {
                    z = true;
                }
                if (z) {
                    multitaskingWindowDecoration.closeSliderPopup();
                } else {
                    multitaskingWindowDecoration.closeMoreMenu();
                }
            } else {
                multitaskingWindowDecoration.closeHandleMenu(false);
            }
            boolean z2 = CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY;
        }

        /* JADX WARN: Code restructure failed: missing block: B:20:0x0070, code lost:
        
            if (r4 != 3) goto L38;
         */
        @Override // com.android.wm.shell.windowdecor.DragDetector.MotionEventHandler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean handleMotionEvent(MotionEvent motionEvent) {
            ActivityManager.RunningTaskInfo runningTaskInfo = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration != null && runningTaskInfo != null) {
                int windowingMode = runningTaskInfo.getWindowingMode();
                boolean z = multitaskingWindowDecoration.mAdjustState.mIsAdjusted;
                if ((CoreRune.MW_CAPTION_SHELL && windowingMode == 6 && !runningTaskInfo.getConfiguration().isNewDexMode()) || z) {
                    DismissButtonManager dismissButtonManager = FreeformDragPositioningController.getInstance(MultitaskingWindowDecorViewModel.this.mContext).mFreeformDragListener.mDismissButtonManager;
                    Objects.requireNonNull(dismissButtonManager);
                    dismissButtonManager.hide(new DismissButtonManager$$ExternalSyntheticLambda0(dismissButtonManager));
                    if (!z) {
                        return false;
                    }
                } else if ((!multitaskingWindowDecoration.mIsDexEnabled || windowingMode != 1) && windowingMode == 1) {
                    return false;
                }
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked != 0) {
                    if (actionMasked != 1) {
                        if (actionMasked == 2) {
                            if (CoreRune.MW_FREEFORM_MOTION_ANIMATION && supportTaskMotion()) {
                                this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
                            }
                            int findPointerIndex = motionEvent.findPointerIndex(this.mDragPointerId);
                            if (findPointerIndex != -1) {
                                if (!CoreRune.MW_CAPTION_SHELL) {
                                    this.mDragPositioningCallback.onDragPositioningMove(motionEvent.getRawX(findPointerIndex), motionEvent.getRawY(findPointerIndex));
                                } else if (MultitaskingWindowDecorViewModel.m2762$$Nest$misCaptionDragEnabled(MultitaskingWindowDecorViewModel.this, runningTaskInfo, motionEvent) && (this.mIsScrolled || this.mIsLongPressed)) {
                                    if (this.mIsButtonTouched && this.mTargetView.isPressed()) {
                                        this.mTargetView.setPressed(false);
                                    }
                                    this.mDragPositioningCallback.onDragPositioningMove(motionEvent.getRawX(findPointerIndex), motionEvent.getRawY(findPointerIndex));
                                }
                                this.mIsDragging = true;
                                return true;
                            }
                            StringBuilder m1m = AbstractC0000x2c234b15.m1m("Invalid dragPointerIndex=", findPointerIndex, ", mDragPointerId=");
                            m1m.append(this.mDragPointerId);
                            m1m.append(", mIsScrolled=");
                            m1m.append(this.mIsScrolled);
                            m1m.append(" in MultitaskingWindowDecorViewModel#handleMotionEvent");
                            Slog.w("MultitaskingWindowDecorViewModel", m1m.toString());
                        }
                        return true;
                    }
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.mDragPointerId);
                    if (CoreRune.MW_FREEFORM_MOTION_ANIMATION && supportTaskMotion()) {
                        this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
                        this.mFreeformCaptionTouchState.computeCurrentVelocity();
                        this.mTaskPositioner.mFreeformCaptionTouchState = this.mFreeformCaptionTouchState;
                    }
                    if (findPointerIndex2 == -1) {
                        Slog.w("MultitaskingWindowDecorViewModel", "Invalid dragPointerIndex=" + findPointerIndex2 + " in MultitaskingWindowDecorViewModel#handleMotionEvent");
                        this.mDragPositioningCallback.onDragPositioningEnd(-1.0f, -1.0f);
                    } else {
                        if (multitaskingWindowDecoration.mIsDexMode && motionEvent.getActionMasked() == 3) {
                            this.mDragPositioningCallback.onDragPositioningEnd(-2.0f, -2.0f);
                        }
                        this.mDragPositioningCallback.onDragPositioningEnd(motionEvent.getRawX(findPointerIndex2), motionEvent.getRawY(findPointerIndex2));
                    }
                    if (CoreRune.MW_FREEFORM_MOTION_ANIMATION && supportTaskMotion()) {
                        FreeformCaptionTouchState freeformCaptionTouchState = this.mFreeformCaptionTouchState;
                        VelocityTracker velocityTracker = freeformCaptionTouchState.mVelocityTracker;
                        if (velocityTracker != null) {
                            velocityTracker.recycle();
                            freeformCaptionTouchState.mVelocityTracker = null;
                        }
                        this.mTaskPositioner.mFreeformCaptionTouchState = null;
                    }
                    boolean z2 = this.mIsDragging;
                    this.mIsDragging = false;
                    return z2;
                }
                this.mDragPointerId = motionEvent.getPointerId(0);
                if (!CoreRune.MW_CAPTION_SHELL || this.mIsScrolled || windowingMode != 5) {
                    this.mDragPositioningCallback.onDragPositioningStart(motionEvent.getRawX(0), motionEvent.getRawY(0), 0);
                }
                if (CoreRune.MW_FREEFORM_MOTION_ANIMATION && supportTaskMotion()) {
                    FreeformCaptionTouchState freeformCaptionTouchState2 = this.mFreeformCaptionTouchState;
                    VelocityTracker velocityTracker2 = freeformCaptionTouchState2.mVelocityTracker;
                    if (velocityTracker2 == null) {
                        freeformCaptionTouchState2.mVelocityTracker = VelocityTracker.obtain();
                    } else {
                        velocityTracker2.clear();
                    }
                    this.mFreeformCaptionTouchState.addMovementToVelocityTracker(motionEvent);
                }
                TaskPositioner taskPositioner = multitaskingWindowDecoration.mTaskPositioner;
                taskPositioner.getClass();
                int toolType = motionEvent.getToolType(0);
                if (taskPositioner.mToolType != toolType) {
                    taskPositioner.mToolType = toolType;
                }
                this.mIsDragging = false;
            }
            return false;
        }

        /* JADX WARN: Removed duplicated region for block: B:115:0x024a  */
        /* JADX WARN: Removed duplicated region for block: B:118:0x025f  */
        /* JADX WARN: Removed duplicated region for block: B:120:0x0267  */
        @Override // android.view.View.OnClickListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onClick(View view) {
            Rect rect;
            WindowMenuItemView windowMenuItemView;
            int i;
            int id = view.getId();
            final MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration != null) {
                int i2 = 0;
                if (multitaskingWindowDecoration.mCaptionType == 0) {
                    MultitaskingWindowDecorViewModel.this.getClass();
                    if (((view instanceof WindowMenuItemView) || (view instanceof WindowMenuAnimationView) || (view instanceof WindowMenuAddDisplayItemView)) && !multitaskingWindowDecoration.isHandleMenuActive()) {
                        return;
                    }
                }
                if (CoreRune.MW_FREEFORM_MOTION_ANIMATION && multitaskingWindowDecoration.isMotionOrBoundsAnimating()) {
                    return;
                }
                int windowingMode = multitaskingWindowDecoration.mTaskInfo.getWindowingMode();
                if (id == R.id.close_window) {
                    MultitaskingWindowDecorViewModel.this.mTaskOperations.closeTask(this.mTaskToken);
                    if (CoreRune.MW_SA_LOGGING) {
                        if (windowingMode == 6) {
                            CoreSaLogger.logForAdvanced("1005", "Tap 'Close window' button");
                            CoreSaLogger.logForAdvanced("1014");
                        } else if (windowingMode == 5) {
                            if (multitaskingWindowDecoration.mIsDexEnabled) {
                                CoreSaLogger.logForDexMW("2508", (String) null);
                            } else {
                                CoreSaLogger.logForAdvanced("2003", "Window option");
                            }
                        }
                    }
                    sendTalkBackFeedback(R.string.sec_decor_button_operation_closed);
                } else if (id == R.id.back_button) {
                    TaskOperations taskOperations = MultitaskingWindowDecorViewModel.this.mTaskOperations;
                    Context context = taskOperations.mContext;
                    taskOperations.sendBackEvent(0, context.getDisplayId());
                    taskOperations.sendBackEvent(1, context.getDisplayId());
                } else if (id == R.id.minimize_window) {
                    if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                        MultiWindowManager.getInstance().minimizeTaskById(this.mTaskId);
                        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                            multitaskingWindowDecoration.mTaskPositioner.removeTaskToMotionInfo(multitaskingWindowDecoration.mTaskInfo, true);
                        }
                    } else {
                        MultitaskingWindowDecorViewModel.this.mTaskOperations.minimizeTask(this.mTaskToken);
                    }
                    sendTalkBackFeedback(R.string.sec_decor_button_operation_minimized);
                } else if (id == R.id.maximize_window) {
                    ActivityManager.RunningTaskInfo runningTaskInfo = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                    MultitaskingWindowDecorViewModel.this.mDexCompatRestartDialogUtils.getClass();
                    if (DexCompatRestartDialogUtils.isDexCompatEnabled(runningTaskInfo)) {
                        MultitaskingWindowDecorViewModel.this.mDexCompatRestartDialogUtils.toggleFreeformForDexCompatApp(this.mTaskId);
                    } else {
                        MultitaskingWindowDecorViewModel.this.mTaskOperations.maximizeTask(runningTaskInfo);
                    }
                    if (CoreRune.MW_SA_LOGGING) {
                        if (CoreRune.MD_DEX_SA_LOGGING && multitaskingWindowDecoration.mIsDexMode) {
                            CoreSaLogger.logForDexMW("2507", MultitaskingWindowDecorViewModel.this.mContext.getApplicationContext().getPackageName(), 1);
                        } else if (windowingMode == 6) {
                            CoreSaLogger.logForAdvanced("1013");
                            CoreSaLogger.logForAdvanced("1005", "Switch to Full screen");
                            CoreSaLogger.logForAdvanced("2090", "From split screen option");
                        } else if (windowingMode == 5) {
                            CoreSaLogger.logForAdvanced("2002");
                            CoreSaLogger.logForAdvanced("2090", "From popup view option");
                        }
                    }
                    sendTalkBackFeedback(R.string.sec_decor_button_operation_maximized);
                } else if (id == R.id.reduce_window) {
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                    MultitaskingWindowDecorViewModel.this.mDexCompatRestartDialogUtils.getClass();
                    if (DexCompatRestartDialogUtils.isDexCompatEnabled(runningTaskInfo2)) {
                        MultitaskingWindowDecorViewModel.this.mDexCompatRestartDialogUtils.toggleFreeformForDexCompatApp(this.mTaskId);
                    } else {
                        MultiWindowManager.getInstance().toggleFreeformWindowingModeForDex(runningTaskInfo2.getToken());
                    }
                } else if (id == R.id.caption_handle) {
                    if (CoreRune.MW_CAPTION_SHELL_POPUP) {
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                        if (runningTaskInfo3 != null && !runningTaskInfo3.isFocused) {
                            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                            windowContainerTransaction.reorder(this.mTaskToken, true);
                            MultitaskingWindowDecorViewModel.this.mSyncQueue.queue(windowContainerTransaction);
                        }
                        multitaskingWindowDecoration.showHandleMenu();
                        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                            multitaskingWindowDecoration.mTaskPositioner.removeTaskToMotionInfo(multitaskingWindowDecoration.mTaskInfo, false);
                        }
                    }
                    if (CoreRune.MW_SA_LOGGING && windowingMode == 6) {
                        CoreSaLogger.logForAdvanced("1011");
                    }
                } else if (id == R.id.caption_pin_window) {
                    if (CoreRune.MW_CAPTION_SHELL_POPUP) {
                        dismissPopup();
                    }
                    if (CoreRune.MW_FREEFORM_HEADER_TYPE_SA_LOGGING) {
                        CoreSaLogger.logSettingStatusForAdvanced("2014", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
                    }
                    WindowContainerTransaction changeByFreeformCaptionType = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.changeByFreeformCaptionType(this.mTaskId, 1);
                    if (changeByFreeformCaptionType != null) {
                        Rect rect2 = new Rect(multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds());
                        Rect bounds = multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
                        if (multitaskingWindowDecoration.mTaskInfo.getWindowingMode() == 5) {
                            int loadDimensionPixelSize = WindowDecoration.loadDimensionPixelSize(android.R.dimen.timepicker_time_label_size, multitaskingWindowDecoration.mContext.getResources()) + WindowDecoration.loadDimensionPixelSize(android.R.dimen.tooltip_horizontal_padding, multitaskingWindowDecoration.mContext.getResources());
                            multitaskingWindowDecoration.mTmpRect.set(multitaskingWindowDecoration.mInsetsState.getDisplayFrame());
                            int i3 = bounds.top - multitaskingWindowDecoration.mInsetsState.calculateInsets(multitaskingWindowDecoration.mTmpRect, WindowInsets.Type.displayCutout() | WindowInsets.Type.statusBars(), false).top;
                            if (i3 < loadDimensionPixelSize) {
                                i = loadDimensionPixelSize - i3;
                                if (i != 0) {
                                    rect2.offset(0, i);
                                    changeByFreeformCaptionType.setBounds(multitaskingWindowDecoration.mTaskInfo.token, rect2);
                                }
                                TaskOperations taskOperations2 = MultitaskingWindowDecorViewModel.this.mTaskOperations;
                                taskOperations2.getClass();
                                if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                                    taskOperations2.mSyncQueue.queue(changeByFreeformCaptionType);
                                } else {
                                    ((FreeformTaskTransitionHandler) taskOperations2.mTransitionStarter).startWindowingModeTransition(changeByFreeformCaptionType, 5);
                                }
                                sendTalkBackFeedback(R.string.sec_decor_button_operation_pinned);
                            }
                        }
                        i = 0;
                        if (i != 0) {
                        }
                        TaskOperations taskOperations22 = MultitaskingWindowDecorViewModel.this.mTaskOperations;
                        taskOperations22.getClass();
                        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                        }
                        sendTalkBackFeedback(R.string.sec_decor_button_operation_pinned);
                    }
                } else if (id == R.id.caption_unpin_window) {
                    if (CoreRune.MW_FREEFORM_HEADER_TYPE_SA_LOGGING) {
                        CoreSaLogger.logSettingStatusForAdvanced("2014", "1");
                    }
                    WindowContainerTransaction changeByFreeformCaptionType2 = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.changeByFreeformCaptionType(this.mTaskId, 0);
                    if (changeByFreeformCaptionType2 != null) {
                        TaskOperations taskOperations3 = MultitaskingWindowDecorViewModel.this.mTaskOperations;
                        taskOperations3.getClass();
                        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                            ((FreeformTaskTransitionHandler) taskOperations3.mTransitionStarter).startWindowingModeTransition(changeByFreeformCaptionType2, 5);
                        } else {
                            taskOperations3.mSyncQueue.queue(changeByFreeformCaptionType2);
                        }
                        sendTalkBackFeedback(R.string.sec_decor_button_operation_unpinned);
                    }
                } else if (id == R.id.split_window) {
                    ActivityManager.RunningTaskInfo runningTaskInfo4 = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                    if (runningTaskInfo4.getWindowingMode() == 1) {
                        if (CoreRune.MW_SA_LOGGING) {
                            CoreSaLogger.logForAdvanced("1000", "From fullscreen handle");
                        }
                        TaskOperations taskOperations4 = MultitaskingWindowDecorViewModel.this.mTaskOperations;
                        taskOperations4.getClass();
                        taskOperations4.mSplitScreenController.ifPresent(new TaskOperations$$ExternalSyntheticLambda1());
                    } else {
                        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                            multitaskingWindowDecoration.mTaskPositioner.removeTaskToMotionInfo(multitaskingWindowDecoration.mTaskInfo, true);
                        }
                        MultitaskingWindowDecoration.AdjustState adjustState = multitaskingWindowDecoration.mAdjustState;
                        if (adjustState.mIsAdjusted) {
                            adjustState.reset();
                        }
                        TaskOperations taskOperations5 = MultitaskingWindowDecorViewModel.this.mTaskOperations;
                        taskOperations5.getClass();
                        taskOperations5.mSplitScreenController.ifPresent(new TaskOperations$$ExternalSyntheticLambda0(runningTaskInfo4, i2));
                        if (CoreRune.MW_SA_LOGGING) {
                            CoreSaLogger.logForAdvanced("1000", "From Popup view");
                        }
                    }
                    if (CoreRune.MW_SA_LOGGING) {
                        CoreSaLogger.logForAdvanced("2006", MultitaskingWindowDecorViewModel.this.mContext.getPackageName());
                    }
                } else if (id == R.id.freeform_window) {
                    MultitaskingWindowDecorViewModel.this.mTaskOperations.moveToFreeform(this.mTaskToken);
                    if (CoreRune.MW_SA_LOGGING) {
                        if (windowingMode == 1) {
                            CoreSaLogger.logForAdvanced("2004", "From fullscreen handle");
                        } else {
                            CoreSaLogger.logForAdvanced("1005", "Tap 'Open in Pop-up view' button");
                            CoreSaLogger.logForAdvanced("1012", MultitaskingWindowDecorViewModel.this.mContext.getPackageName());
                            CoreSaLogger.logForAdvanced("2004", "From split view Option");
                        }
                    }
                } else if (id == R.id.rotate_window) {
                    MultiWindowManager.getInstance().rotateDexCompatTask(this.mTaskToken.asBinder());
                } else if (id == R.id.opacity_window) {
                    if (multitaskingWindowDecoration.mIsDexMode) {
                        if (multitaskingWindowDecoration.mResult.mRootView != null) {
                            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                            Resources resources = multitaskingWindowDecoration.mDecorWindowContext.getResources();
                            int loadDimensionPixelSize2 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_slider_popup_xoffset, resources);
                            int loadDimensionPixelSize3 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_slider_popup_width, resources);
                            int width = multitaskingWindowDecoration.mTaskInfo.getConfiguration().windowConfiguration.getBounds().width() - (loadDimensionPixelSize2 * 2);
                            int min = multitaskingWindowDecoration.mIsDexDockingEnabled ? Math.min(loadDimensionPixelSize3, width) : loadDimensionPixelSize3;
                            int loadDimensionPixelSize4 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_slider_popup_height, resources);
                            int loadDimensionPixelSize5 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_popup_elevation, resources);
                            if (!((WindowDecorLinearLayout) multitaskingWindowDecoration.mResult.mRootView).isLayoutRtl()) {
                                loadDimensionPixelSize2 = (multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds().width() - min) - loadDimensionPixelSize2;
                            }
                            int loadDimensionPixelSize6 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_slider_popup_yoffset, resources);
                            WindowDecoration.RelayoutResult relayoutResult = multitaskingWindowDecoration.mResult;
                            WindowDecoration.AdditionalWindow addWindow = multitaskingWindowDecoration.addWindow(R.layout.sec_decor_slider_popup_desktop, "Slider Popup", transaction, loadDimensionPixelSize2 - relayoutResult.mDecorContainerOffsetX, loadDimensionPixelSize6 - relayoutResult.mDecorContainerOffsetY, min, loadDimensionPixelSize4, 2, 262152, loadDimensionPixelSize5, true);
                            multitaskingWindowDecoration.mSliderPopup = addWindow;
                            int i4 = 1;
                            Animator createSurfaceAlphaAnimator = CaptionAnimationUtils.createSurfaceAlphaAnimator(addWindow.mWindowSurface, true, 250L, InterpolatorUtils.ONE_EASING);
                            createSurfaceAlphaAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration.5
                                public C41795() {
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    MultitaskingWindowDecoration multitaskingWindowDecoration2 = MultitaskingWindowDecoration.this;
                                    multitaskingWindowDecoration2.mIsSliderPopupShowing = true;
                                    MultitaskingWindowDecorViewModel.CaptionTouchEventListener captionTouchEventListener = multitaskingWindowDecoration2.mOnCaptionTouchListener;
                                    if (captionTouchEventListener != null) {
                                        captionTouchEventListener.schedulePopupDismiss();
                                    }
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public final void onAnimationStart(Animator animator) {
                                }
                            });
                            createSurfaceAlphaAnimator.start();
                            multitaskingWindowDecoration.mSyncQueue.runInSync(new MultitaskingWindowDecoration$$ExternalSyntheticLambda1(i4, transaction));
                            View view2 = multitaskingWindowDecoration.mSliderPopup.mWindowViewHost.getView();
                            if (view2 != null) {
                                ((GradientDrawable) view2.getBackground()).setColor(multitaskingWindowDecoration.mDecorWindowContext.getResources().getColor(multitaskingWindowDecoration.mIsNightMode ? android.R.color.system_on_shade_active_dark : android.R.color.system_on_shade_active_light, null));
                                WindowDecorSlider windowDecorSlider = (WindowDecorSlider) view2.findViewById(R.id.slider);
                                if (windowDecorSlider != null) {
                                    windowDecorSlider.setOnSeekBarChangeListener(multitaskingWindowDecoration.mOnCaptionTouchListener);
                                    windowDecorSlider.setProgress((int) ((1.0f - multitaskingWindowDecoration.mAlpha) * 100.0f));
                                    if (multitaskingWindowDecoration.mIsDexDockingEnabled && width < loadDimensionPixelSize3) {
                                        int i5 = (loadDimensionPixelSize3 - width) / 2;
                                        int loadDimensionPixelSize7 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_slider_popup_min_Padding, resources);
                                        windowDecorSlider.setPadding(Math.max(windowDecorSlider.getPaddingLeft() - i5, loadDimensionPixelSize7), windowDecorSlider.getPaddingTop(), Math.max(windowDecorSlider.getPaddingRight() - i5, loadDimensionPixelSize7), windowDecorSlider.getPaddingBottom());
                                    }
                                }
                                view2.setOnTouchListener(new MultitaskingWindowDecoration$$ExternalSyntheticLambda0(i4, multitaskingWindowDecoration));
                            }
                        }
                        schedulePopupDismiss();
                    } else if (multitaskingWindowDecoration.mIsNewDexMode) {
                        WindowMenuCaptionPresenter windowMenuCaptionPresenter = multitaskingWindowDecoration.mCaptionMenuPresenter;
                        if (windowMenuCaptionPresenter != null) {
                            WindowDecorSlider windowDecorSlider2 = windowMenuCaptionPresenter.mSlider;
                            if (windowDecorSlider2 != null) {
                                windowDecorSlider2.setControllable(true);
                                windowMenuCaptionPresenter.mSlider.setProgress((int) ((1.0f - windowMenuCaptionPresenter.mAlpha) * 100.0f));
                            }
                        } else if (multitaskingWindowDecoration.mPopupMenuPresenter != null) {
                            multitaskingWindowDecoration.setOpacitySlider();
                        }
                    } else {
                        multitaskingWindowDecoration.setOpacitySlider();
                    }
                } else if (id == R.id.back_window) {
                    ActivityManager.RunningTaskInfo runningTaskInfo5 = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                    TaskOperations taskOperations6 = MultitaskingWindowDecorViewModel.this.mTaskOperations;
                    int i6 = runningTaskInfo5.displayId;
                    taskOperations6.sendBackEvent(0, i6);
                    taskOperations6.sendBackEvent(1, i6);
                } else {
                    if (id == R.id.window_pin_window) {
                        MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel = MultitaskingWindowDecorViewModel.this;
                        multitaskingWindowDecorViewModel.mIsPinned = multitaskingWindowDecorViewModel.mTaskOrganizer.togglePinTaskState(this.mTaskId);
                        MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel2 = MultitaskingWindowDecorViewModel.this;
                        boolean z = multitaskingWindowDecorViewModel2.mIsPinned;
                        multitaskingWindowDecorViewModel2.mPinTaskId = z ? this.mTaskId : -1;
                        if (CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE) {
                            multitaskingWindowDecoration.mIsPopupWindowPinned = z;
                        }
                        if (view instanceof WindowMenuDexItemView) {
                            WindowMenuCaptionPresenter windowMenuCaptionPresenter2 = multitaskingWindowDecoration.mCaptionMenuPresenter;
                            if (windowMenuCaptionPresenter2 != null && (windowMenuItemView = (WindowMenuItemView) windowMenuCaptionPresenter2.mButtons.get(R.id.window_pin_window)) != null) {
                                windowMenuItemView.mShowIconBackground = z;
                                windowMenuItemView.setContentDescription(z ? multitaskingWindowDecoration.mContext.getString(R.string.sec_decor_button_text_window_unpin) : multitaskingWindowDecoration.mContext.getString(R.string.sec_decor_button_text_window_pin));
                            }
                        } else if (view instanceof WindowMenuItemView) {
                            ((WindowMenuItemView) view).mShowIconBackground = z;
                            view.invalidate();
                            MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel3 = MultitaskingWindowDecorViewModel.this;
                            boolean z2 = multitaskingWindowDecorViewModel3.mIsPinned;
                            Context context2 = multitaskingWindowDecorViewModel3.mContext;
                            view.setContentDescription(z2 ? context2.getString(R.string.sec_decor_button_text_window_unpin) : context2.getString(R.string.sec_decor_button_text_window_pin));
                        }
                        MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel4 = MultitaskingWindowDecorViewModel.this;
                        multitaskingWindowDecorViewModel4.toggleDisableAllPinButton(this.mTaskId, multitaskingWindowDecorViewModel4.mIsPinned);
                        ActivityManager.RunningTaskInfo runningTaskInfo6 = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                        if (runningTaskInfo6 != null && !runningTaskInfo6.isFocused) {
                            WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                            windowContainerTransaction2.reorder(this.mTaskToken, true);
                            MultitaskingWindowDecorViewModel.this.mSyncQueue.queue(windowContainerTransaction2);
                        }
                        if (multitaskingWindowDecoration.mCaptionType == 0) {
                            multitaskingWindowDecoration.closeHandleMenu(false);
                        }
                    } else if (CoreRune.MW_CAPTION_SHELL_OVERFLOW_MENU && id == R.id.more_window) {
                        if ((multitaskingWindowDecoration.mIsDexEnabled || MultitaskingWindowDecorViewModel.this.mAddedDisplayId != -1) && multitaskingWindowDecoration.mCaptionMenuPresenter != null) {
                            Animator animator = multitaskingWindowDecoration.mOverflowMenuAnim;
                            if (animator != null && animator.isRunning()) {
                                multitaskingWindowDecoration.mOverflowMenuAnim.cancel();
                            }
                            if (multitaskingWindowDecoration.isHandleMenuActive()) {
                                multitaskingWindowDecoration.closeMoreMenu();
                            } else if (multitaskingWindowDecoration.mResult.mRootView != null && multitaskingWindowDecoration.mCaptionMenuPresenter != null) {
                                SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(multitaskingWindowDecoration.mDecorWindowContext, android.R.style.Theme.DeviceDefault.DayNight);
                                Resources resources2 = contextThemeWrapper.getResources();
                                int i7 = multitaskingWindowDecoration.mIsDexEnabled ? multitaskingWindowDecoration.mIsNewDexMode ? R.layout.sec_caption_handle_more_menu_new_dex : R.layout.sec_caption_handle_menu_desktop : R.layout.sec_caption_handle_more_menu_move_display;
                                View inflate = LayoutInflater.from(contextThemeWrapper).inflate(i7, (ViewGroup) null);
                                inflate.setOnTouchListener(new MultitaskingWindowDecoration$$ExternalSyntheticLambda0(i2, multitaskingWindowDecoration));
                                WindowMenuMorePopupPresenter windowMenuMorePopupPresenter = new WindowMenuMorePopupPresenter(contextThemeWrapper, multitaskingWindowDecoration.mTaskInfo, inflate, multitaskingWindowDecoration.mOnCaptionTouchListener, multitaskingWindowDecoration.mAlpha, multitaskingWindowDecoration.mCaptionColor, multitaskingWindowDecoration.mCaptionMenuPresenter, multitaskingWindowDecoration.mIsAdditionalDisplayAdded);
                                View view3 = windowMenuMorePopupPresenter.mRootView;
                                if (view3 == null) {
                                    rect = new Rect(0, 0, 0, 0);
                                } else {
                                    int dimensionPixelSize = windowMenuMorePopupPresenter.mContext.getResources().getDimensionPixelSize(17105904);
                                    view3.measure(0, 0);
                                    int max = Math.max(dimensionPixelSize, view3.getMeasuredWidth());
                                    if (windowMenuMorePopupPresenter.mTaskInfo != null) {
                                        max = Math.min(max, windowMenuMorePopupPresenter.mTaskInfo.getConfiguration().windowConfiguration.getBounds().width() - (windowMenuMorePopupPresenter.mContext.getResources().getDimensionPixelSize(R.dimen.sec_dex_decor_more_menu_window_right_inset) * 2));
                                    }
                                    view3.measure(View.MeasureSpec.makeMeasureSpec(max, VideoPlayer.MEDIA_ERROR_SYSTEM), 0);
                                    rect = new Rect(0, 0, max, view3.getMeasuredHeight());
                                }
                                int width2 = rect.width();
                                int height = rect.height();
                                int loadDimensionPixelSize8 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_dex_decor_more_menu_window_right_inset, resources2);
                                int loadDimensionPixelSize9 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_decor_popup_elevation, resources2);
                                int width3 = ((WindowDecorLinearLayout) multitaskingWindowDecoration.mResult.mRootView).isLayoutRtl() ? loadDimensionPixelSize8 : (multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds().width() - width2) - loadDimensionPixelSize8;
                                int loadDimensionPixelSize10 = WindowDecoration.loadDimensionPixelSize(R.dimen.sec_dex_decor_more_menu_window_top_inset, resources2);
                                WindowMenuItemView windowMenuItemView2 = (WindowMenuItemView) multitaskingWindowDecoration.mCaptionMenuPresenter.mButtons.get(R.id.more_window);
                                if (windowMenuItemView2 != null) {
                                    loadDimensionPixelSize10 -= windowMenuItemView2.getHeight();
                                }
                                WindowDecoration.AdditionalWindow addWindow2 = multitaskingWindowDecoration.addWindow(i7, "Caption More menu", transaction2, width3, loadDimensionPixelSize10, width2, height, 2, 262152, loadDimensionPixelSize9, true, inflate);
                                multitaskingWindowDecoration.mHandleMenu = addWindow2;
                                Animator createSurfaceAlphaAnimator2 = CaptionAnimationUtils.createSurfaceAlphaAnimator(addWindow2.mWindowSurface, true, 250L, InterpolatorUtils.ONE_EASING);
                                multitaskingWindowDecoration.mOverflowMenuAnim = createSurfaceAlphaAnimator2;
                                createSurfaceAlphaAnimator2.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecoration.7
                                    public C41817() {
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationEnd(Animator animator2) {
                                        MultitaskingWindowDecoration.this.mIsHandleMenuShowing = true;
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                    public final void onAnimationStart(Animator animator2) {
                                    }
                                });
                                multitaskingWindowDecoration.mOverflowMenuAnim.start();
                                multitaskingWindowDecoration.mSyncQueue.runInSync(new MultitaskingWindowDecoration$$ExternalSyntheticLambda1(i2, transaction2));
                            }
                        }
                    } else if (CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY && id == R.id.move_display_window) {
                        if (multitaskingWindowDecoration.mCaptionType == 0) {
                            multitaskingWindowDecoration.closeHandleMenu(false);
                        } else {
                            multitaskingWindowDecoration.closeMoreMenu();
                        }
                    } else if (view.getId() == R.id.apps_window && this.mAccessibilityManager.semIsAccessibilityServiceEnabled(114)) {
                        dismissPopup();
                        MultitaskingWindowDecorViewModel.this.mMainHandler.post(new RunnableC4172x471e6e13(this, MultiWindowUtils.getExternalAppsServiceIntent(this.mTaskId, new int[]{(int) view.getPivotX(), (int) view.getPivotY()})));
                        if (CoreRune.MW_SA_LOGGING) {
                            CoreSaLogger.logForAdvanced("1051");
                        }
                    }
                }
                if (CoreRune.MW_CAPTION_SHELL_OVERFLOW_MENU && view.getId() != R.id.more_window && multitaskingWindowDecoration.mIsDexEnabled) {
                    multitaskingWindowDecoration.closeMoreMenu();
                }
            }
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTap(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
            ActivityManager.RunningTaskInfo runningTaskInfo;
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration != null && multitaskingWindowDecoration.mIsDexEnabled) {
                int action = motionEvent.getAction();
                if (action != 0) {
                    if (action == 1 && this.mIsDoubleTapEnabled && (runningTaskInfo = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId)) != null) {
                        MultitaskingWindowDecorViewModel.this.mDexCompatRestartDialogUtils.getClass();
                        if (DexCompatRestartDialogUtils.isDexCompatEnabled(runningTaskInfo)) {
                            MultitaskingWindowDecorViewModel.this.mDexCompatRestartDialogUtils.toggleFreeformForDexCompatApp(this.mTaskId);
                        } else {
                            if (CoreRune.MW_SA_LOGGING) {
                                int windowingMode = runningTaskInfo.getWindowingMode();
                                if (windowingMode == 1) {
                                    CoreSaLogger.logForAdvanced("2004", "From mouse double clicking");
                                } else if (windowingMode == 5) {
                                    CoreSaLogger.logForAdvanced("2090", "From mouse double clicking");
                                }
                            }
                            MultiWindowManager.getInstance().toggleFreeformWindowingModeForDex(runningTaskInfo.getToken());
                        }
                    }
                } else if (this.mIsDoubleTapEnabled) {
                    this.mIsDoubleTapEnabled = (motionEvent.getToolType(0) == 3 && motionEvent.getButtonState() == 1) ? false : true;
                }
            }
            return true;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            return false;
        }

        @Override // android.view.View.OnHoverListener
        public final boolean onHover(View view, MotionEvent motionEvent) {
            ImmersiveCaptionBehavior immersiveCaptionBehavior;
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration == null || !multitaskingWindowDecoration.mIsDexMode || !multitaskingWindowDecoration.mIsImmersiveMode) {
                int action = motionEvent.getAction();
                if (action == 9) {
                    unschedulePopupDismiss();
                } else if (action == 10) {
                    schedulePopupDismiss();
                }
                return false;
            }
            PointF offsetCaptionLocation = multitaskingWindowDecoration.offsetCaptionLocation(motionEvent);
            Rect bounds = multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
            float f = offsetCaptionLocation.x;
            if (f >= 0.0f && f <= bounds.width()) {
                float f2 = offsetCaptionLocation.y;
                if (f2 >= 0.0f && f2 <= bounds.height() && multitaskingWindowDecoration.mIsImmersiveMode && (immersiveCaptionBehavior = multitaskingWindowDecoration.mImmersiveCaptionBehavior) != null) {
                    int i = (int) offsetCaptionLocation.y;
                    int i2 = 1;
                    boolean z = motionEvent.getToolType(0) == 2;
                    boolean z2 = (motionEvent.getFlags() & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) != 0;
                    if (!z || z2 || motionEvent.getSource() == 8194) {
                        int actionMasked = motionEvent.getActionMasked();
                        ImmersiveCaptionBehavior$$ExternalSyntheticLambda0 immersiveCaptionBehavior$$ExternalSyntheticLambda0 = immersiveCaptionBehavior.mHideRunnable;
                        Handler handler = immersiveCaptionBehavior.mHandler;
                        int i3 = immersiveCaptionBehavior.mCaptionHeight;
                        if (actionMasked != 7) {
                            if (actionMasked == 9) {
                                if (z && z2) {
                                    i2 = i3 / 2;
                                }
                                immersiveCaptionBehavior.mPositionToShow = i2;
                            } else if (actionMasked == 10 && immersiveCaptionBehavior.mIsShowing && !handler.hasCallbacks(immersiveCaptionBehavior$$ExternalSyntheticLambda0)) {
                                handler.postDelayed(immersiveCaptionBehavior$$ExternalSyntheticLambda0, 1000L);
                            }
                        } else if (immersiveCaptionBehavior.mIsShowing) {
                            if (i > i3) {
                                handler.postDelayed(immersiveCaptionBehavior$$ExternalSyntheticLambda0, 1000L);
                            } else if (handler.hasCallbacks(immersiveCaptionBehavior$$ExternalSyntheticLambda0)) {
                                handler.removeCallbacks(immersiveCaptionBehavior$$ExternalSyntheticLambda0);
                            }
                        } else if (i <= immersiveCaptionBehavior.mPositionToShow) {
                            ImmersiveCaptionBehavior$$ExternalSyntheticLambda0 immersiveCaptionBehavior$$ExternalSyntheticLambda02 = immersiveCaptionBehavior.mShowRunnable;
                            if (!handler.hasCallbacks(immersiveCaptionBehavior$$ExternalSyntheticLambda02)) {
                                immersiveCaptionBehavior.mShownByTouch = false;
                                int i4 = CaptionGlobalState.TRANSIENT_DELAY;
                                handler.postDelayed(immersiveCaptionBehavior$$ExternalSyntheticLambda02, i4 != -1 ? i4 : 500L);
                            }
                        }
                    }
                }
            }
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onLongPress(MotionEvent motionEvent) {
            MultitaskingWindowDecoration multitaskingWindowDecoration;
            if (CoreRune.MW_CAPTION_SHELL) {
                this.mIsLongPressed = true;
                this.mLongPressMotionEvent = motionEvent;
                View view = this.mTargetView;
                if ((view instanceof WindowMenuItemView) || (view instanceof WindowMenuAnimationView)) {
                    this.mIsButtonLongPressed = true;
                }
            }
            if (!CoreRune.MW_FREEFORM_DISMISS_VIEW || (multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId)) == null || multitaskingWindowDecoration.mIsDexMode || !this.mIsScrolled) {
                return;
            }
            DismissButtonManager dismissButtonManager = FreeformDragPositioningController.getInstance(MultitaskingWindowDecorViewModel.this.mContext).mFreeformDragListener.mDismissButtonManager;
            Objects.requireNonNull(dismissButtonManager);
            dismissButtonManager.hide(new DismissButtonManager$$ExternalSyntheticLambda0(dismissButtonManager));
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            float f = 1.0f - (i * 0.01f);
            if (f < 0.4f) {
                f = 0.4f;
            }
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration == null) {
                return;
            }
            boolean z2 = f < 1.0f && f >= 0.0f;
            MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel = MultitaskingWindowDecorViewModel.this;
            if (multitaskingWindowDecorViewModel.mIsTranslucent != z2) {
                multitaskingWindowDecorViewModel.mIsTranslucent = z2;
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setFreeformTranslucent(this.mTaskToken, z2 ? 1 : 2);
                MultitaskingWindowDecorViewModel.this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
            }
            MultitaskingWindowDecorViewModel.this.mTaskOrganizer.setFreeformTaskOpacity(this.mTaskId, f);
            multitaskingWindowDecoration.setDecorationOpacity(f);
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (CoreRune.MW_CAPTION_SHELL) {
                ActivityManager.RunningTaskInfo runningTaskInfo = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                if (motionEvent != null && runningTaskInfo != null && MultitaskingWindowDecorViewModel.m2762$$Nest$misCaptionDragEnabled(MultitaskingWindowDecorViewModel.this, runningTaskInfo, motionEvent)) {
                    MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
                    if (multitaskingWindowDecoration == null || multitaskingWindowDecoration.isHandleMenuActive()) {
                        Slog.w("MultitaskingWindowDecorViewModel", "OnScroll : Decoration not exist or HandleMenu Activated " + multitaskingWindowDecoration);
                    } else if (!this.mIsScrolled) {
                        this.mIsScrolled = true;
                        this.mDragPointerId = motionEvent.getPointerId(0);
                        Slog.d("MultitaskingWindowDecorViewModel", "DragPositioningStart from scrolled : " + multitaskingWindowDecoration.toString());
                        this.mDragPositioningCallback.onDragPositioningStart(motionEvent.getRawX(0), motionEvent.getRawY(0), 0);
                    }
                }
                return false;
            }
            return true;
        }

        @Override // android.view.GestureDetector.OnDoubleTapListener
        public final boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStartTrackingTouch(SeekBar seekBar) {
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration == null) {
                return;
            }
            if (!multitaskingWindowDecoration.mIsDexMode && (seekBar instanceof WindowDecorSlider)) {
                ((WindowDecorSlider) seekBar).onStartTrackingTouch();
            }
            unschedulePopupDismiss();
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public final void onStopTrackingTouch(SeekBar seekBar) {
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration == null) {
                return;
            }
            if (!multitaskingWindowDecoration.mIsDexMode && (seekBar instanceof WindowDecorSlider)) {
                ((WindowDecorSlider) seekBar).onStopTrackingTouch();
            }
            schedulePopupDismiss();
            if (CoreRune.MW_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("2005", String.valueOf((seekBar.getProgress() * 10) / 60));
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:117:0x01a8, code lost:
        
            if (r0 != 3) goto L200;
         */
        /* JADX WARN: Code restructure failed: missing block: B:16:0x002e, code lost:
        
            if ((r11.getId() == com.android.systemui.R.id.caption || r11.getId() == com.android.systemui.R.id.caption_handle || (r11 instanceof com.android.wm.shell.windowdecor.WindowMenuItemView) || (r11 instanceof com.android.wm.shell.windowdecor.WindowMenuAnimationView) || (r11 instanceof com.android.wm.shell.windowdecor.WindowMenuAddDisplayItemView)) == false) goto L20;
         */
        @Override // android.view.View.OnTouchListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean onTouch(View view, MotionEvent motionEvent) {
            View view2;
            MultitaskingWindowDecoration multitaskingWindowDecoration;
            ImmersiveCaptionBehavior immersiveCaptionBehavior;
            int toolType;
            int action;
            if (view.getId() != R.id.caption) {
                if (CoreRune.MW_CAPTION_SHELL) {
                }
                return false;
            }
            if (CoreRune.MW_CAPTION_SHELL_IMMERSIVE_MODE) {
                MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel = MultitaskingWindowDecorViewModel.this;
                int i = multitaskingWindowDecorViewModel.mLastImmersiveTaskId;
                int i2 = this.mTaskId;
                if (i == i2 && (multitaskingWindowDecoration = (MultitaskingWindowDecoration) multitaskingWindowDecorViewModel.mWindowDecorByTaskId.get(i2)) != null && multitaskingWindowDecoration.mIsImmersiveMode) {
                    ImmersiveCaptionBehavior immersiveCaptionBehavior2 = multitaskingWindowDecoration.mImmersiveCaptionBehavior;
                    if (immersiveCaptionBehavior2 != null && (immersiveCaptionBehavior2.mIsShowing ^ true)) {
                        if (!this.mIsButtonLongPressed) {
                            MultitaskingWindowDecoration multitaskingWindowDecoration2 = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
                            if (!(multitaskingWindowDecoration2 != null && multitaskingWindowDecoration2.isHandleMenuActive() && this.mIsButtonTouched)) {
                                DexSnappingGuide dexSnappingGuide = multitaskingWindowDecoration.mTaskPositioner.mDexSnappingGuide;
                                if ((dexSnappingGuide != null && dexSnappingGuide.mView.isShown()) && ((action = motionEvent.getAction()) == 1 || action == 2 || action == 3)) {
                                    this.mDragDetector.onMotionEvent(motionEvent);
                                    return false;
                                }
                            }
                        }
                        PointF offsetCaptionLocation = multitaskingWindowDecoration.offsetCaptionLocation(motionEvent);
                        Rect bounds = multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds();
                        float f = offsetCaptionLocation.x;
                        if (f >= 0.0f && f <= bounds.width()) {
                            float f2 = offsetCaptionLocation.y;
                            if (f2 >= 0.0f && f2 <= bounds.height() && multitaskingWindowDecoration.mIsImmersiveMode && (immersiveCaptionBehavior = multitaskingWindowDecoration.mImmersiveCaptionBehavior) != null) {
                                int i3 = (int) offsetCaptionLocation.y;
                                if ((motionEvent.getFlags() & QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY) == 0 && ((toolType = motionEvent.getToolType(0)) == 1 || toolType == 2 || toolType == 3)) {
                                    int action2 = motionEvent.getAction();
                                    if (action2 == 0) {
                                        immersiveCaptionBehavior.mDownY = i3;
                                        immersiveCaptionBehavior.mDownTime = motionEvent.getEventTime();
                                    } else if (action2 == 2) {
                                        Handler handler = immersiveCaptionBehavior.mHandler;
                                        ImmersiveCaptionBehavior$$ExternalSyntheticLambda0 immersiveCaptionBehavior$$ExternalSyntheticLambda0 = immersiveCaptionBehavior.mShowRunnable;
                                        if (!handler.hasCallbacks(immersiveCaptionBehavior$$ExternalSyntheticLambda0)) {
                                            float f3 = i3;
                                            long eventTime = motionEvent.getEventTime() - immersiveCaptionBehavior.mDownTime;
                                            float f4 = immersiveCaptionBehavior.mDownY;
                                            float f5 = immersiveCaptionBehavior.mCaptionHeight;
                                            if (f4 <= f5 && f3 > f4 && f3 > f5 && eventTime < 500) {
                                                immersiveCaptionBehavior.mShownByTouch = true;
                                                int i4 = CaptionGlobalState.TRANSIENT_DELAY;
                                                handler.postDelayed(immersiveCaptionBehavior$$ExternalSyntheticLambda0, i4 != -1 ? i4 : 500L);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        return false;
                    }
                }
            }
            if (CoreRune.MW_CAPTION_SHELL) {
                this.mGestureDetector.onTouchEvent(motionEvent);
                if (MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId) == null) {
                    MultitaskingWindowDecoration multitaskingWindowDecoration3 = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
                    Slog.d("MultitaskingWindowDecorViewModel", "OnTouch: task " + this.mTaskId + " might be vanished. Try destroy decoration=" + multitaskingWindowDecoration3);
                    if (multitaskingWindowDecoration3 != null) {
                        multitaskingWindowDecoration3.close();
                    }
                    return false;
                }
            }
            if ((view.getId() == R.id.caption || view.getId() == R.id.caption_handle) && MultitaskingWindowDecorViewModel.this.mNSController.onInterceptTouchEvent(motionEvent, this.mTaskId)) {
                return true;
            }
            if (CoreRune.MW_CAPTION_SHELL) {
                int action3 = motionEvent.getAction();
                if (action3 != 0) {
                    if (action3 != 1) {
                        if (action3 == 2) {
                            ActivityManager.RunningTaskInfo runningTaskInfo = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                            int windowingMode = runningTaskInfo.getWindowingMode();
                            if (windowingMode == 5 && (view2 = this.mTargetView) != null && this.mIsButtonTouched && this.mIsScrolled && view2.isPressed()) {
                                this.mTargetView.setPressed(false);
                            }
                            if (CoreRune.MW_CAPTION_SHELL_DEX && runningTaskInfo.configuration.isDexMode() && !this.mIsScrolled && this.mIsLongPressed && windowingMode == 5) {
                                int rawX = (int) (this.mLongPressMotionEvent.getRawX() - motionEvent.getRawX());
                                int rawY = (int) (this.mLongPressMotionEvent.getRawY() - motionEvent.getRawY());
                                int scaledTouchSlop = ViewConfiguration.get(MultitaskingWindowDecorViewModel.this.mContext).getScaledTouchSlop();
                                if ((rawY * rawY) + (rawX * rawX) > scaledTouchSlop * scaledTouchSlop) {
                                    onScroll(this.mLongPressMotionEvent, motionEvent, rawX, rawY);
                                }
                            }
                        }
                    }
                    this.mTargetView = null;
                    this.mIsScrolled = false;
                    this.mLongPressMotionEvent = null;
                    schedulePopupDismiss();
                    if (this.mIsButtonTouched) {
                        Drawable background = view.getBackground();
                        if (background instanceof RippleDrawable) {
                            ((RippleDrawable) background).setState(new int[0]);
                        }
                    }
                    if (view.getId() == R.id.apps_window) {
                        if (!this.mIsLongPressed && !this.mIsButtonLongPressed) {
                            dismissPopup();
                            MultitaskingWindowDecorViewModel.this.mMainHandler.post(new RunnableC4172x471e6e13(this, MultiWindowUtils.getExternalAppsServiceIntent(this.mTaskId, new int[]{(int) motionEvent.getRawX(), (int) motionEvent.getRawY()})));
                        }
                        if (CoreRune.MW_SA_LOGGING) {
                            CoreSaLogger.logForAdvanced("1051");
                        }
                    }
                    this.mIsLongPressed = false;
                    this.mIsButtonLongPressed = false;
                } else {
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
                    if (!runningTaskInfo2.isFocused) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        windowContainerTransaction.reorder(this.mTaskToken, true);
                        MultitaskingWindowDecorViewModel.this.mSyncQueue.queue(windowContainerTransaction);
                    }
                    if (view instanceof HandleView) {
                        view.performHapticFeedback(0);
                    }
                    MultitaskingWindowDecorViewModel.this.getClass();
                    boolean z = (view instanceof WindowMenuItemView) || (view instanceof WindowMenuAnimationView) || (view instanceof WindowMenuAddDisplayItemView);
                    this.mIsButtonTouched = z;
                    if (!z && view.getId() != R.id.caption_handle) {
                        view = null;
                    }
                    this.mTargetView = view;
                    this.mIsScrolled = false;
                    this.mIsLongPressed = false;
                    this.mIsButtonLongPressed = false;
                    this.mLongPressMotionEvent = null;
                    this.mIsDoubleTapEnabled = view == null && runningTaskInfo2.getWindowingMode() != 6;
                    unschedulePopupDismiss();
                }
            } else if (motionEvent.getAction() == 0 && !MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId).isFocused) {
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                windowContainerTransaction2.reorder(this.mTaskToken, true);
                MultitaskingWindowDecorViewModel.this.mSyncQueue.queue(windowContainerTransaction2);
            }
            if (!this.mIsButtonLongPressed) {
                MultitaskingWindowDecoration multitaskingWindowDecoration4 = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
                if (!(multitaskingWindowDecoration4 != null && multitaskingWindowDecoration4.isHandleMenuActive() && this.mIsButtonTouched) && this.mDragDetector.onMotionEvent(motionEvent)) {
                    return true;
                }
            }
            return false;
        }

        public final void schedulePopupDismiss() {
            if (((MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId)) == null) {
                return;
            }
            MultitaskingWindowDecorViewModel.this.mMainHandler.removeCallbacks(this.mDismissRunnable);
            if (this.mAccessibilityManager.semIsAccessibilityServiceEnabled(114)) {
                return;
            }
            MultitaskingWindowDecorViewModel.this.mMainHandler.postDelayed(this.mDismissRunnable, 3000L);
        }

        public final void sendTalkBackFeedback(int i) {
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
                return;
            }
            AccessibilityEvent obtain = AccessibilityEvent.obtain(16384);
            String string = MultitaskingWindowDecorViewModel.this.mContext.getString(i);
            obtain.getText().clear();
            obtain.getText().add(string);
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }

        public final boolean supportTaskMotion() {
            ActivityManager.RunningTaskInfo runningTaskInfo = MultitaskingWindowDecorViewModel.this.mTaskOrganizer.getRunningTaskInfo(this.mTaskId);
            return (runningTaskInfo == null || runningTaskInfo.getWindowingMode() != 5 || runningTaskInfo.configuration.isDexMode()) ? false : true;
        }

        /* JADX WARN: Code restructure failed: missing block: B:12:0x001e, code lost:
        
            if ((r0.mSliderPopup != null && r0.mIsSliderPopupShowing) == false) goto L14;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void unschedulePopupDismiss() {
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) MultitaskingWindowDecorViewModel.this.mWindowDecorByTaskId.get(this.mTaskId);
            if (multitaskingWindowDecoration == null) {
                return;
            }
            if (multitaskingWindowDecoration.mIsDexEnabled) {
            }
            if (!multitaskingWindowDecoration.isHandleMenuActive()) {
                return;
            }
            MultitaskingWindowDecorViewModel.this.mMainHandler.removeCallbacks(this.mDismissRunnable);
        }

        private CaptionTouchEventListener(ActivityManager.RunningTaskInfo runningTaskInfo, DragPositioningCallback dragPositioningCallback) {
            this.mDragPointerId = -1;
            this.mDismissRunnable = new RunnableC4173x28c31a39(this, 2);
            this.mTaskId = runningTaskInfo.taskId;
            this.mTaskToken = runningTaskInfo.token;
            this.mDragPositioningCallback = dragPositioningCallback;
            this.mDragDetector = new DragDetector(this);
            this.mAccessibilityManager = AccessibilityManager.getInstance(MultitaskingWindowDecorViewModel.this.mContext);
            Context context = MultitaskingWindowDecorViewModel.this.mContext;
            GestureDetector gestureDetector = new GestureDetector(context, this);
            this.mGestureDetector = gestureDetector;
            gestureDetector.setOnDoubleTapListener(this);
            this.mFreeformCaptionTouchState = new FreeformCaptionTouchState(ViewConfiguration.get(context));
            this.mTaskPositioner = (TaskPositioner) dragPositioningCallback;
        }

        @Override // android.view.GestureDetector.OnGestureListener
        public final void onShowPress(MotionEvent motionEvent) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SettingsObserver extends ContentObserver {
        public final Uri mColorThemeColorUri;
        public final Uri mFullScreenUri;

        public SettingsObserver(Context context) {
            super(null);
            ContentResolver contentResolver = context.getContentResolver();
            Uri uriFor = Settings.System.getUriFor("wallpapertheme_color");
            this.mColorThemeColorUri = uriFor;
            Uri uriFor2 = Settings.Global.getUriFor("multi_window_menu_in_full_screen");
            this.mFullScreenUri = uriFor2;
            contentResolver.registerContentObserver(uriFor, false, this, -1);
            MultitaskingWindowDecorViewModel.m2763$$Nest$mupdateColorThemeState(MultitaskingWindowDecorViewModel.this);
            if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN) {
                contentResolver.registerContentObserver(uriFor2, false, this, -1);
                MultitaskingWindowDecorViewModel.m2764$$Nest$mupdateFullscreenHandlerState(MultitaskingWindowDecorViewModel.this);
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            if (this.mColorThemeColorUri.equals(uri)) {
                MultitaskingWindowDecorViewModel.this.mMainHandler.post(new RunnableC4173x28c31a39(this, 0));
            } else if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN && this.mFullScreenUri.equals(uri)) {
                MultitaskingWindowDecorViewModel.this.mMainHandler.post(new RunnableC4173x28c31a39(this, 1));
            }
        }
    }

    /* renamed from: -$$Nest$misCaptionDragEnabled, reason: not valid java name */
    public static boolean m2762$$Nest$misCaptionDragEnabled(MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel, ActivityManager.RunningTaskInfo runningTaskInfo, MotionEvent motionEvent) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) multitaskingWindowDecorViewModel.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (multitaskingWindowDecoration == null) {
            return false;
        }
        int windowingMode = runningTaskInfo.getWindowingMode();
        if ((motionEvent.getFlags() & QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) != 0 && (motionEvent.getButtonState() & 1) == 0) {
            return false;
        }
        if (windowingMode != 5) {
            if (runningTaskInfo.configuration.isNewDexMode()) {
                if (multitaskingWindowDecoration.mCaptionType != 1 || motionEvent.getToolType(0) != 3) {
                    return false;
                }
                if (windowingMode != 1 && !multitaskingWindowDecoration.mTaskInfo.getConfiguration().windowConfiguration.isSplitScreen()) {
                    return false;
                }
            } else if (!runningTaskInfo.configuration.isDexMode() || windowingMode != 1) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: -$$Nest$mupdateColorThemeState, reason: not valid java name */
    public static void m2763$$Nest$mupdateColorThemeState(MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel) {
        boolean z;
        Context context = multitaskingWindowDecorViewModel.mContext;
        boolean z2 = false;
        int i = 1;
        boolean z3 = Settings.System.getInt(context.getContentResolver(), "wallpapertheme_state", 0) == 1;
        String string = Settings.System.getString(context.getContentResolver(), "wallpapertheme_color");
        if (string == null) {
            string = "";
        }
        if (CaptionGlobalState.COLOR_THEME_ENABLED != z3) {
            CaptionGlobalState.COLOR_THEME_ENABLED = z3;
            z = true;
        } else {
            z = false;
        }
        if (z) {
            Slog.d("MultitaskingWindowDecorViewModel", "updateColorThemeState: enabled=" + z3);
        }
        if (!CaptionGlobalState.COLOR_THEME_COLOR.equals(string)) {
            CaptionGlobalState.COLOR_THEME_COLOR = string;
            z2 = true;
        }
        if (z2) {
            multitaskingWindowDecorViewModel.forAllDecorations(new MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda2(i));
        }
    }

    /* renamed from: -$$Nest$mupdateFullscreenHandlerState, reason: not valid java name */
    public static void m2764$$Nest$mupdateFullscreenHandlerState(MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel) {
        boolean z = false;
        boolean z2 = Settings.Global.getInt(multitaskingWindowDecorViewModel.mContext.getContentResolver(), "multi_window_menu_in_full_screen", 0) == 1;
        if (CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED != z2) {
            CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED = z2;
            z = true;
        }
        if (!z) {
            return;
        }
        Slog.d("MultitaskingWindowDecorViewModel", "updateFullscreenHandlerState: enabled=" + z2);
        if (z2) {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
            Iterator it = ((ArrayList) multitaskingWindowDecorViewModel.mTaskOrganizer.getVisibleTaskAppearedInfos()).iterator();
            while (it.hasNext()) {
                TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) it.next();
                ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
                int i = taskInfo.taskId;
                if (taskInfo.getWindowingMode() == 1 && taskAppearedInfo.getLeash().isValid() && multitaskingWindowDecorViewModel.shouldShowWindowDecor(taskInfo)) {
                    Slog.d("MultitaskingWindowDecorViewModel", "onFullscreenHandlerEnabled: show, t# " + i);
                    multitaskingWindowDecorViewModel.createWindowDecoration(taskInfo, taskAppearedInfo.getLeash(), transaction, transaction2);
                    MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) multitaskingWindowDecorViewModel.mWindowDecorByTaskId.get(i);
                    if (multitaskingWindowDecoration != null) {
                        multitaskingWindowDecoration.updateNonFreeformCaptionVisibility();
                    }
                }
            }
            transaction.apply();
            transaction2.apply();
            return;
        }
        final ArrayList arrayList = new ArrayList();
        multitaskingWindowDecorViewModel.forAllDecorations(new Consumer() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ArrayList arrayList2 = arrayList;
                MultitaskingWindowDecoration multitaskingWindowDecoration2 = (MultitaskingWindowDecoration) obj;
                if (multitaskingWindowDecoration2.mTaskInfo.getWindowingMode() == 1) {
                    arrayList2.add(multitaskingWindowDecoration2);
                }
            }
        });
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            MultitaskingWindowDecoration multitaskingWindowDecoration2 = (MultitaskingWindowDecoration) arrayList.get(size);
            Slog.d("MultitaskingWindowDecorViewModel", "onFullscreenHandlerDisabled: remove, " + multitaskingWindowDecoration2);
            multitaskingWindowDecorViewModel.destroyWindowDecoration(multitaskingWindowDecoration2.mTaskInfo);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda0, java.lang.Object] */
    public MultitaskingWindowDecorViewModel(Context context, Handler handler, Choreographer choreographer, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, SyncTransactionQueue syncTransactionQueue, ShellExecutor shellExecutor, Optional<SplitScreenController> optional, DisplayInsetsController displayInsetsController, Optional<Pip> optional2, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController, Optional<DesktopModeController> optional3, DexCompatRestartDialogUtils dexCompatRestartDialogUtils, Transitions transitions, ShellController shellController) {
        ?? r5 = new KeyguardChangeListener() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel.1
            @Override // com.android.wm.shell.sysui.KeyguardChangeListener
            public final void onKeyguardVisibilityChanged(final boolean z, boolean z2) {
                MultitaskingWindowDecorViewModel.this.forAllDecorations(new Consumer() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        boolean z3 = z;
                        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) obj;
                        if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN) {
                            multitaskingWindowDecoration.mIsKeyguardShowing = z3;
                            WindowMenuPopupPresenter windowMenuPopupPresenter = multitaskingWindowDecoration.mPopupMenuPresenter;
                            if (windowMenuPopupPresenter == null || !multitaskingWindowDecoration.mTaskInfo.isRunning) {
                                return;
                            }
                            windowMenuPopupPresenter.setFreeformButtonEnabled(!z3);
                        }
                    }
                });
            }
        };
        this.mKeyguardChangeListener = r5;
        this.mImeAdjustedTargetBounds = new Rect();
        this.mTmpRect = new Rect();
        this.mIsPinned = false;
        this.mPinTaskId = -1;
        this.mLastImmersiveTaskId = -1;
        this.mIsTranslucent = false;
        this.mAnimatedBounds = new Rect();
        this.mAddedDisplayId = -1;
        ?? r6 = new DisplayChangeController.OnDisplayChangingListener() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
            public final void onDisplayChange(final int i, final int i2, final int i3, final DisplayAreaInfo displayAreaInfo, final WindowContainerTransaction windowContainerTransaction) {
                MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel = MultitaskingWindowDecorViewModel.this;
                multitaskingWindowDecorViewModel.getClass();
                multitaskingWindowDecorViewModel.forAllDecorations(new Consumer(i, i2, i3, displayAreaInfo, windowContainerTransaction) { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda3
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
                        DisplayCutout displayCutout;
                        int i4;
                        int i5 = this.f$0;
                        int i6 = this.f$1;
                        int i7 = this.f$2;
                        WindowContainerTransaction windowContainerTransaction2 = this.f$4;
                        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) obj;
                        if (i5 != 0) {
                            multitaskingWindowDecoration.getClass();
                            return;
                        }
                        if (i5 == multitaskingWindowDecoration.mDisplay.getDisplayId() && (displayLayout = multitaskingWindowDecoration.mDisplayController.getDisplayLayout(multitaskingWindowDecoration.mTaskInfo.displayId)) != null) {
                            Rect rect = new Rect();
                            View view = multitaskingWindowDecoration.mResult.mRootView;
                            boolean z = false;
                            if (view != null && ((WindowDecorLinearLayout) view).isAttachedToWindow() && displayLayout.mRotation == i7) {
                                displayLayout.getStableBounds(rect, false);
                                displayCutout = displayLayout.mCutout;
                            } else {
                                DisplayLayout displayLayout2 = new DisplayLayout(displayLayout);
                                displayLayout2.rotateTo(i7, multitaskingWindowDecoration.mDecorWindowContext.getResources());
                                displayLayout2.getStableBounds(rect, false);
                                displayCutout = displayLayout2.mCutout;
                            }
                            if (CoreRune.MW_FREEFORM_LARGE_SCREEN_BOUNDS_POLICY && multitaskingWindowDecoration.mTaskInfo.getConfiguration().semDisplayDeviceType != 5) {
                                z = true;
                            }
                            if (z) {
                                MultiWindowUtils.adjustBoundsForScreenRatio(multitaskingWindowDecoration.mLastStableBounds, rect, new Rect(multitaskingWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash), multitaskingWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash);
                            } else {
                                RotationUtils.rotateBounds(multitaskingWindowDecoration.mFreeformStashState.mLastFreeformBoundsBeforeStash, multitaskingWindowDecoration.mLastStableBounds, i6, i7);
                            }
                            if (multitaskingWindowDecoration.mFreeformStashState.isStashed()) {
                                Rect rect2 = multitaskingWindowDecoration.mTmpRect;
                                rect2.set(multitaskingWindowDecoration.mTaskInfo.configuration.windowConfiguration.getBounds());
                                if (z) {
                                    MultiWindowUtils.adjustBoundsForScreenRatio(multitaskingWindowDecoration.mLastStableBounds, rect, new Rect(rect2), rect2);
                                } else {
                                    RotationUtils.rotateBounds(rect2, multitaskingWindowDecoration.mLastStableBounds, i6, i7);
                                }
                                if (displayCutout != null) {
                                    Rect safeInsets = displayCutout.getSafeInsets();
                                    rect.set(rect.left - safeInsets.left, rect.top, rect.right + safeInsets.right, rect.bottom);
                                }
                                FreeformStashState freeformStashState = multitaskingWindowDecoration.mFreeformStashState;
                                int i8 = multitaskingWindowDecoration.mTaskPositioner.mTaskMotionController.mMinVisibleWidth;
                                if (freeformStashState.mStashType != 0) {
                                    if (freeformStashState.isLeftStashed()) {
                                        i4 = rect.left + i8;
                                        i8 = rect2.width();
                                    } else {
                                        i4 = rect.right;
                                    }
                                    rect2.offsetTo(i4 - i8, (int) (freeformStashState.mFreeformStashYFraction * rect.height()));
                                }
                                windowContainerTransaction2.setBounds(multitaskingWindowDecoration.mTaskInfo.token, rect2);
                                if (!rect2.isEmpty()) {
                                    multitaskingWindowDecoration.mFreeformStashState.updateDimBounds(multitaskingWindowDecoration.getFreeformThickness$1(), multitaskingWindowDecoration.getCaptionVisibleHeight(), rect2);
                                    float height = multitaskingWindowDecoration.mTaskPositioner.mTaskMotionController.mScaledFreeformHeight / rect2.height();
                                    if (multitaskingWindowDecoration.mFreeformStashState.mScale != height) {
                                        WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
                                        windowContainerTransaction3.setChangeFreeformStashScale(multitaskingWindowDecoration.mTaskInfo.token, height);
                                        multitaskingWindowDecoration.mTaskOrganizer.applyTransaction(windowContainerTransaction3);
                                    }
                                }
                                multitaskingWindowDecoration.mLastStableBounds.set(rect);
                            }
                        }
                    }
                });
            }
        };
        this.mRotationController = r6;
        this.mContext = context;
        this.mMainHandler = handler;
        this.mMainChoreographer = choreographer;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mDisplayController = displayController;
        this.mSyncQueue = syncTransactionQueue;
        if (!Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mTaskOperations = new TaskOperations(null, context, syncTransactionQueue, optional);
        }
        this.mAnimExecutor = shellExecutor;
        this.mSplitScreenController = optional;
        this.mDisplayInsetsController = displayInsetsController;
        this.mPipOptional = optional2;
        this.mNSController = naturalSwitchingDropTargetController;
        this.mDexCompatRestartDialogUtils = dexCompatRestartDialogUtils;
        if (CoreRune.MW_CAPTION_SHELL) {
            displayController.addDisplayWindowListener(this);
        }
        displayController.mChangeController.mDisplayChangeListener.add(r6);
        if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN) {
            CopyOnWriteArrayList copyOnWriteArrayList = shellController.mKeyguardChangeListeners;
            copyOnWriteArrayList.remove((Object) r5);
            copyOnWriteArrayList.add(r5);
        }
        MultiWindowManager.getInstance().registerDexTransientDelayListener(new IDexTransientCaptionDelayListener.Stub() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel.2
            public final void onDelayChanged(final int i) {
                MultitaskingWindowDecorViewModel.this.mMainHandler.post(new Runnable() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        if (CaptionGlobalState.TRANSIENT_DELAY != i2) {
                            CaptionGlobalState.TRANSIENT_DELAY = i2;
                        }
                    }
                });
            }
        });
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
            this.mTransitions = transitions;
        }
    }

    public static boolean canUseFullscreenHandler(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z) {
        if (!MultiWindowCoreState.MW_ENABLED) {
            return false;
        }
        if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && runningTaskInfo.getConfiguration().semDisplayDeviceType == 5) {
            return false;
        }
        if (((CoreRune.MW_CAPTION_SHELL_NEW_DEX_CAPTION_TYPE && runningTaskInfo.getConfiguration().isNewDexMode()) || CaptionGlobalState.FULLSCREEN_HANDLER_ENABLED) && runningTaskInfo.supportsMultiWindow) {
            return (z || (runningTaskInfo.getWindowingMode() == 1)) && runningTaskInfo.getActivityType() == 1;
        }
        return false;
    }

    public static boolean isExitingPipTask(Pip pip, ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (pip == null) {
            return false;
        }
        boolean isExitingPipTask = pip.isExitingPipTask(runningTaskInfo.taskId);
        return (runningTaskInfo.getConfiguration().isDesktopModeEnabled() || !canUseFullscreenHandler(runningTaskInfo, true)) ? runningTaskInfo.getConfiguration().isNewDexMode() && runningTaskInfo.getWindowingMode() == 2 && isExitingPipTask : runningTaskInfo.getWindowingMode() == 2 && isExitingPipTask;
    }

    public final void createWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        MultitaskingWindowDecoration multitaskingWindowDecoration;
        MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel;
        SparseArray sparseArray = this.mWindowDecorByTaskId;
        MultitaskingWindowDecoration multitaskingWindowDecoration2 = (MultitaskingWindowDecoration) sparseArray.get(runningTaskInfo.taskId);
        if (multitaskingWindowDecoration2 != null) {
            multitaskingWindowDecoration2.close();
        }
        MultitaskingWindowDecoration multitaskingWindowDecoration3 = new MultitaskingWindowDecoration(this.mContext, this.mDisplayController, this.mTaskOrganizer, runningTaskInfo, surfaceControl, this.mMainHandler, this.mMainChoreographer, this.mSyncQueue, this.mDisplayInsetsController, this.mDexCompatRestartDialogUtils, (Pip) this.mPipOptional.get(), (SplitScreenController) this.mSplitScreenController.get());
        sparseArray.put(runningTaskInfo.taskId, multitaskingWindowDecoration3);
        if (CoreRune.MW_CAPTION_SHELL) {
            Slog.d("MultitaskingWindowDecorViewModel", "MultitaskingWindowDecoration created. taskId=" + runningTaskInfo.taskId + ", num_remains=" + sparseArray.size());
        }
        if (CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY) {
            multitaskingWindowDecoration = multitaskingWindowDecoration3;
            multitaskingWindowDecorViewModel = this;
            if (multitaskingWindowDecorViewModel.mAddedDisplayId != -1) {
                multitaskingWindowDecoration.onDisplayAdded(false);
            }
        } else {
            multitaskingWindowDecoration = multitaskingWindowDecoration3;
            multitaskingWindowDecorViewModel = this;
        }
        TaskPositioner taskPositioner = new TaskPositioner(multitaskingWindowDecorViewModel.mTaskOrganizer, multitaskingWindowDecoration, multitaskingWindowDecorViewModel.mDisplayController, multitaskingWindowDecorViewModel.mAnimExecutor, multitaskingWindowDecorViewModel.mMainHandler);
        if (CoreRune.MW_CAPTION_SHELL && multitaskingWindowDecoration.shouldHideHandlerByAppRequest(runningTaskInfo)) {
            multitaskingWindowDecoration.mDragPositioningCallback = taskPositioner;
            multitaskingWindowDecoration.mTaskPositioner = taskPositioner;
            multitaskingWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false, false);
            multitaskingWindowDecoration.setCaptionColor$1();
            Slog.d("MultitaskingWindowDecorViewModel", "createWindowDecoration: forceHidden, t#" + runningTaskInfo.taskId);
        } else {
            CaptionTouchEventListener captionTouchEventListener = new CaptionTouchEventListener(multitaskingWindowDecorViewModel, runningTaskInfo, taskPositioner);
            if (CoreRune.MW_CAPTION_SHELL) {
                multitaskingWindowDecoration.mOnCaptionButtonClickListener = captionTouchEventListener;
                multitaskingWindowDecoration.mOnCaptionTouchListener = captionTouchEventListener;
            }
            multitaskingWindowDecoration.mDragPositioningCallback = taskPositioner;
            multitaskingWindowDecoration.mTaskPositioner = taskPositioner;
            DragDetector dragDetector = captionTouchEventListener.mDragDetector;
            multitaskingWindowDecoration.mDragDetector = dragDetector;
            dragDetector.mTouchSlop = ViewConfiguration.get(multitaskingWindowDecoration.mContext).getScaledTouchSlop();
            multitaskingWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false, false);
            multitaskingWindowDecoration.setCaptionColor$1();
        }
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
            multitaskingWindowDecoration.updateDimensions(multitaskingWindowDecorViewModel.mContext.getResources().getDisplayMetrics());
        }
        if (CoreRune.MW_CAPTION_SHELL_KEEP_SCREEN_ON && multitaskingWindowDecorViewModel.mTaskOrganizer.isKeepScreenOn(runningTaskInfo.taskId)) {
            multitaskingWindowDecorViewModel.onKeepScreenOnChanged(runningTaskInfo.taskId, true);
        }
        multitaskingWindowDecoration.updateRoundedCornerForSplit();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void destroyWindowDecoration(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = runningTaskInfo.taskId;
        SparseArray sparseArray = this.mWindowDecorByTaskId;
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) sparseArray.removeReturnOld(i);
        if (multitaskingWindowDecoration == null) {
            return;
        }
        multitaskingWindowDecoration.close();
        if (multitaskingWindowDecoration.mIsDexMode && this.mIsPinned) {
            int i2 = this.mPinTaskId;
            int i3 = runningTaskInfo.taskId;
            if (i2 == i3) {
                this.mTaskOrganizer.togglePinTaskState(i3);
                toggleDisableAllPinButton(runningTaskInfo.taskId, false);
            }
        }
        if (CoreRune.MW_CAPTION_SHELL_IMMERSIVE_MODE && this.mLastImmersiveDecoration == multitaskingWindowDecoration) {
            resetLastImmersiveDecoration();
        }
        if (CoreRune.MW_CAPTION_SHELL) {
            Slog.d("MultitaskingWindowDecorViewModel", "MultitaskingWindowDecoration destroyed. taskId=" + runningTaskInfo.taskId + ", num_remains=" + sparseArray.size() + ", callers=" + Debug.getCallers(3));
        }
    }

    public final void forAllDecorations(Consumer consumer) {
        SparseArray sparseArray = this.mWindowDecorByTaskId;
        int size = sparseArray.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                consumer.accept((MultitaskingWindowDecoration) sparseArray.valueAt(size));
            }
        }
    }

    @Override // com.android.wm.shell.freeform.AdjustImeStateController
    public final void getImeStartBounds(ActivityManager.RunningTaskInfo runningTaskInfo, Rect rect) {
        MultitaskingWindowDecoration multitaskingWindowDecoration;
        if (runningTaskInfo == null || (multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) == null) {
            return;
        }
        TaskPositioner taskPositioner = multitaskingWindowDecoration.mTaskPositioner;
        TaskMotionController taskMotionController = taskPositioner.mTaskMotionController;
        boolean z = false;
        if (taskMotionController.isMotionAnimating()) {
            synchronized (taskMotionController) {
                TaskMotionController.TaskMotionInfo taskMotionInfo = taskMotionController.mTaskMotionInfo;
                if (taskMotionInfo != null) {
                    taskMotionInfo.clearAnimator(false);
                }
            }
        }
        PhysicsAnimator physicsAnimator = taskMotionController.mTemporaryBoundsPhysicsAnimator;
        if (physicsAnimator != null && physicsAnimator.isRunning()) {
            z = true;
        }
        if (z) {
            taskMotionController.cancelBoundsAnimator(rect, "ime");
        }
        if (rect.isEmpty()) {
            PointF pointF = taskPositioner.mRepositionStartPoint;
            if (pointF.x != 0.0f && pointF.y != 0.0f) {
                rect.set(taskPositioner.mRepositionTaskBounds);
            }
        } else {
            taskPositioner.mFlingCanceled = true;
        }
        if (rect.isEmpty()) {
            return;
        }
        this.mAnimatedBounds.set(rect);
    }

    public final void imePositionChanged(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (multitaskingWindowDecoration == null || multitaskingWindowDecoration.mAdjustState.mOriginBounds.isEmpty()) {
            return;
        }
        multitaskingWindowDecoration.mAdjustState.moveSurface(i);
        MultitaskingWindowDecoration.AdjustState adjustState = multitaskingWindowDecoration.mAdjustState;
        boolean z = i != 0;
        if (adjustState.mIsAdjusted != z) {
            adjustState.mIsAdjusted = z;
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        if (i != 2) {
            return;
        }
        Slog.d("MultitaskingWindowDecorViewModel", "on Display Added: displayId= " + i);
        this.mAddedDisplayId = i;
        forAllDecorations(new MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda2(0));
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(final int i, Configuration configuration) {
        forAllDecorations(new Consumer() { // from class: com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) obj;
                if (CoreRune.MW_CAPTION_SHELL_POPUP && multitaskingWindowDecoration.mTaskInfo.displayId == i2 && multitaskingWindowDecoration.isHandleMenuActive()) {
                    multitaskingWindowDecoration.closeHandleMenu(true);
                    multitaskingWindowDecoration.mTaskPositioner.removeTaskToMotionInfo(multitaskingWindowDecoration.mTaskInfo, true);
                }
            }
        });
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        Slog.d("MultitaskingWindowDecorViewModel", "on Display removed: displayId= " + i);
        if (this.mAddedDisplayId == i) {
            this.mAddedDisplayId = -1;
            forAllDecorations(new MultitaskingWindowDecorViewModel$$ExternalSyntheticLambda2(2));
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onFixedRotationFinished(int i) {
        this.mFixedRotatingDisplayIds.remove(Integer.valueOf(i));
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onFixedRotationStarted(int i, int i2) {
        this.mFixedRotatingDisplayIds.add(Integer.valueOf(i));
    }

    @Override // com.android.wm.shell.freeform.AdjustImeStateController
    public final void onImePositionChanged(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        imePositionChanged(runningTaskInfo, i);
    }

    @Override // com.android.wm.shell.freeform.AdjustImeStateController
    public final void onImeStartPositioning(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (multitaskingWindowDecoration == null) {
            return;
        }
        MultitaskingWindowDecoration.AdjustState adjustState = multitaskingWindowDecoration.mAdjustState;
        Rect rect = this.mAnimatedBounds;
        boolean isEmpty = rect.isEmpty();
        Rect rect2 = this.mTmpRect;
        if (isEmpty) {
            rect2.set(runningTaskInfo.configuration.windowConfiguration.getBounds());
        } else {
            rect2.set(rect);
            rect.setEmpty();
        }
        int i2 = rect2.left;
        boolean z = adjustState.mIsAdjusted;
        Rect rect3 = adjustState.mAdjustingBounds;
        if (z ? rect3.left != i2 : !(rect3.isEmpty() && i >= 0)) {
            adjustState.setOriginBounds(rect2);
        }
        adjustState.adjustConfig(runningTaskInfo.getToken(), i);
        rect2.offset(0, i);
        this.mImeAdjustedTargetBounds.set(rect2);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onInit() {
        Slog.d("MultitaskingWindowDecorViewModel", "onInit");
        if (this.mSettingsObserver == null) {
            this.mSettingsObserver = new SettingsObserver(this.mContext);
        }
    }

    public final void onKeepScreenOnChanged(int i, boolean z) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(i);
        if (multitaskingWindowDecoration == null) {
            return;
        }
        multitaskingWindowDecoration.mIsKeepScreenOn = z;
        if (multitaskingWindowDecoration.getHandleView() == null || !multitaskingWindowDecoration.mIsHandleVisibleState) {
            return;
        }
        if (multitaskingWindowDecoration.mTaskInfo.getWindowingMode() != 5) {
            multitaskingWindowDecoration.setHandleAutoHideEnabled(multitaskingWindowDecoration.mIsKeepScreenOn);
        } else {
            multitaskingWindowDecoration.setHandleAutoHideEnabled(false);
        }
    }

    @Override // com.android.wm.shell.freeform.AdjustImeStateController
    public final void onLayoutPositionEnd(ActivityManager.RunningTaskInfo runningTaskInfo, int i) {
        imePositionChanged(runningTaskInfo, i);
        this.mImeAdjustedTargetBounds.setEmpty();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskChanging(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (!shouldShowWindowDecor(runningTaskInfo)) {
            if (multitaskingWindowDecoration != null) {
                destroyWindowDecoration(runningTaskInfo);
            }
        } else {
            if (multitaskingWindowDecoration == null) {
                createWindowDecoration(runningTaskInfo, surfaceControl, transaction, transaction2);
                return;
            }
            multitaskingWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false, false);
            if (CoreRune.MW_SHELL_FREEFORM_CAPTION_TYPE) {
                multitaskingWindowDecoration.setCaptionColor$1();
            }
            if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
                multitaskingWindowDecoration.mTaskPositioner.removeTaskToMotionInfo(runningTaskInfo, true);
            }
            if (!CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY || this.mAddedDisplayId == -1) {
                return;
            }
            multitaskingWindowDecoration.onDisplayAdded(false);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskClosing(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (multitaskingWindowDecoration == null) {
            return;
        }
        if (CoreRune.MW_CAPTION_SHELL) {
            multitaskingWindowDecoration.onTaskClosing();
        }
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
            multitaskingWindowDecoration.mTaskPositioner.removeTaskToMotionInfo(runningTaskInfo, false);
        }
        if (multitaskingWindowDecoration.mIsDexEnabled && this.mIsPinned) {
            int i = this.mPinTaskId;
            int i2 = runningTaskInfo.taskId;
            if (i == i2) {
                this.mTaskOrganizer.togglePinTaskState(i2);
                toggleDisableAllPinButton(runningTaskInfo.taskId, false);
            }
        }
        multitaskingWindowDecoration.relayout(runningTaskInfo, transaction, transaction2, false, false);
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        boolean z;
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (multitaskingWindowDecoration == null) {
            return;
        }
        if (CoreRune.MW_CAPTION_SHELL_BUG_FIX && !shouldShowWindowDecor(runningTaskInfo)) {
            destroyWindowDecoration(runningTaskInfo);
            return;
        }
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION) {
            multitaskingWindowDecoration.updateDimensions(this.mContext.getResources().getDisplayMetrics());
        }
        if (multitaskingWindowDecoration.mFreeformStashState.isStashed() && multitaskingWindowDecoration.mFreeformStashState.mScale != 1.0f && runningTaskInfo.configuration.isDesktopModeEnabled()) {
            multitaskingWindowDecoration.mTaskPositioner.resetStashedFreeform(false);
        }
        if (CoreRune.MW_CAPTION_SHELL_MOVE_DISPLAY && this.mAddedDisplayId != -1) {
            multitaskingWindowDecoration.onDisplayAdded(false);
        }
        if (CoreRune.MW_FREEFORM_MOTION_ANIMATION && !multitaskingWindowDecoration.isMotionOrBoundsAnimating()) {
            TaskPositioner taskPositioner = multitaskingWindowDecoration.mTaskPositioner;
            if (!taskPositioner.mHasMoved) {
                boolean z2 = runningTaskInfo.isCaptionHandlerHidden;
                if (z2 != multitaskingWindowDecoration.mTaskInfo.isCaptionHandlerHidden && !z2) {
                    if (!((multitaskingWindowDecoration.mOnCaptionTouchListener == null || multitaskingWindowDecoration.mOnCaptionButtonClickListener == null) ? false : true)) {
                        CaptionTouchEventListener captionTouchEventListener = new CaptionTouchEventListener(this, runningTaskInfo, taskPositioner);
                        DragDetector dragDetector = captionTouchEventListener.mDragDetector;
                        multitaskingWindowDecoration.mDragDetector = dragDetector;
                        dragDetector.mTouchSlop = ViewConfiguration.get(multitaskingWindowDecoration.mContext).getScaledTouchSlop();
                        if (CoreRune.MW_CAPTION_SHELL) {
                            multitaskingWindowDecoration.mOnCaptionButtonClickListener = captionTouchEventListener;
                            multitaskingWindowDecoration.mOnCaptionTouchListener = captionTouchEventListener;
                        }
                    }
                }
                if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
                    Transitions transitions = this.mTransitions;
                    if (!(transitions.mPendingTransitions.isEmpty() && !transitions.isAnimating()) && transitions.isAnimating()) {
                        int i = 0;
                        while (true) {
                            ArrayList arrayList = transitions.mTracks;
                            if (i >= arrayList.size()) {
                                break;
                            }
                            if (((Transitions.Track) arrayList.get(i)).mActiveTransition != null && (((Transitions.Track) arrayList.get(i)).mActiveTransition.mInfo.getFlags() & 256) != 0) {
                                z = true;
                                break;
                            }
                            i++;
                        }
                    }
                    z = false;
                    if (z) {
                        multitaskingWindowDecoration.relayout(runningTaskInfo, true);
                        multitaskingWindowDecoration.setCaptionColor$1();
                    }
                }
                multitaskingWindowDecoration.relayout(runningTaskInfo, false);
                multitaskingWindowDecoration.setCaptionColor$1();
            }
        }
        multitaskingWindowDecoration.updateRoundedCornerForSplit();
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final boolean onTaskOpening(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        MultitaskingWindowDecoration multitaskingWindowDecoration;
        if (!shouldShowWindowDecor(runningTaskInfo)) {
            return false;
        }
        createWindowDecoration(runningTaskInfo, surfaceControl, transaction, transaction2);
        if (!CoreRune.MW_CAPTION_SHELL || (multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) == null) {
            return true;
        }
        multitaskingWindowDecoration.onTaskOpening();
        return true;
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskToBack(ActivityManager.RunningTaskInfo runningTaskInfo) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (multitaskingWindowDecoration != null) {
            multitaskingWindowDecoration.onTaskClosing();
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTaskToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId);
        if (multitaskingWindowDecoration != null) {
            multitaskingWindowDecoration.onTaskOpening();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x00d0  */
    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTransitionFinished(IBinder iBinder) {
        SparseArray sparseArray;
        MultitaskingWindowDecoration multitaskingWindowDecoration;
        MultiTaskingHelpController multiTaskingHelpController;
        HandleView handleView;
        boolean z;
        WindowDecoration.RelayoutResult relayoutResult;
        View view;
        boolean z2;
        boolean z3;
        OutlineView outlineView;
        if (CoreRune.MW_CAPTION_SHELL) {
            HashMap hashMap = (HashMap) this.mTransitionToTaskInfo;
            List list = (List) hashMap.getOrDefault(iBinder, Collections.emptyList());
            hashMap.remove(iBinder);
            boolean z4 = false;
            int i = 0;
            while (true) {
                int size = list.size();
                sparseArray = this.mWindowDecorByTaskId;
                if (i >= size) {
                    break;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) list.get(i);
                if (runningTaskInfo != null && (multitaskingWindowDecoration = (MultitaskingWindowDecoration) sparseArray.get(runningTaskInfo.taskId)) != null) {
                    MultitaskingWindowDecoration.FreeformOutlineWrapper freeformOutlineWrapper = multitaskingWindowDecoration.mFreeformOutlineWrapper;
                    if (freeformOutlineWrapper != null && (outlineView = freeformOutlineWrapper.getOutlineView()) != null && (outlineView.mIsOpening || outlineView.mIsClosing)) {
                        outlineView.mIsOpening = false;
                        outlineView.mIsClosing = false;
                        outlineView.invalidate();
                        if (OutlineView.SAFE_DEBUG) {
                            Log.d("OutlineView", "onTransitionFinished: " + outlineView);
                        }
                    }
                    int windowingMode = runningTaskInfo.configuration.windowConfiguration.getWindowingMode();
                    if (windowingMode == 6 || windowingMode == 1) {
                        multitaskingWindowDecoration.updateNonFreeformCaptionVisibility();
                        if (CoreRune.MW_CAPTION_SHELL_IMMERSIVE_MODE) {
                            if (runningTaskInfo.taskId == this.mLastImmersiveTaskId) {
                                updateLastImmersiveDecoration(multitaskingWindowDecoration);
                            }
                            if (multitaskingWindowDecoration.mIsDexEnabled && (z = multitaskingWindowDecoration.mIsImmersiveMode) && windowingMode == 1 && z && (view = (relayoutResult = multitaskingWindowDecoration.mResult).mRootView) != null) {
                                ImmersiveCaptionBehavior immersiveCaptionBehavior = multitaskingWindowDecoration.mImmersiveCaptionBehavior;
                                if (immersiveCaptionBehavior == null) {
                                    ImmersiveCaptionBehavior immersiveCaptionBehavior2 = new ImmersiveCaptionBehavior(multitaskingWindowDecoration.mTaskInfo, multitaskingWindowDecoration.mHandler, view, relayoutResult.mCaptionHeight);
                                    multitaskingWindowDecoration.mImmersiveCaptionBehavior = immersiveCaptionBehavior2;
                                    if (multitaskingWindowDecoration.mIsImmersiveMode) {
                                        immersiveCaptionBehavior2.setShownState(true);
                                        immersiveCaptionBehavior2.mIsPaused = false;
                                        immersiveCaptionBehavior2.hide();
                                    } else {
                                        immersiveCaptionBehavior2.pause();
                                    }
                                } else {
                                    if (immersiveCaptionBehavior.mIsShowing) {
                                        if (!immersiveCaptionBehavior.mHandler.hasCallbacks(immersiveCaptionBehavior.mHideRunnable)) {
                                            z2 = false;
                                            z3 = multitaskingWindowDecoration.mIsImmersiveMode;
                                            if (z2 != z3) {
                                                if (z3) {
                                                    ImmersiveCaptionBehavior immersiveCaptionBehavior3 = multitaskingWindowDecoration.mImmersiveCaptionBehavior;
                                                    immersiveCaptionBehavior3.setShownState(true);
                                                    immersiveCaptionBehavior3.mIsPaused = false;
                                                    immersiveCaptionBehavior3.hide();
                                                } else {
                                                    multitaskingWindowDecoration.mImmersiveCaptionBehavior.pause();
                                                }
                                            }
                                        }
                                    }
                                    z2 = true;
                                    z3 = multitaskingWindowDecoration.mIsImmersiveMode;
                                    if (z2 != z3) {
                                    }
                                }
                            }
                        }
                        if (CoreRune.MW_CAPTION_SHELL_POPUP_HELP && (multiTaskingHelpController = multitaskingWindowDecoration.mMultiTaskingHelpController) != null) {
                            int i2 = multiTaskingHelpController.mWindowingMode;
                            if ((i2 == 6 ? MultiTaskingHelpController.SPLIT_HANDLER_HELP_POPUP_ENABLED : i2 == 5 ? MultiTaskingHelpController.FREEFORM_HANDLER_HELP_POPUP_ENABLED : false) && (handleView = multitaskingWindowDecoration.getHandleView()) != null && handleView.getVisibility() == 0) {
                                multitaskingWindowDecoration.showHandleMenu();
                            }
                        }
                    }
                }
                i++;
            }
            if (CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
                SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                Iterator it = ((ArrayList) this.mTaskOrganizer.getVisibleTaskAppearedInfos()).iterator();
                while (it.hasNext()) {
                    TaskAppearedInfo taskAppearedInfo = (TaskAppearedInfo) it.next();
                    ActivityManager.RunningTaskInfo taskInfo = taskAppearedInfo.getTaskInfo();
                    int i3 = taskInfo.taskId;
                    if (taskInfo.topActivity != null && (taskInfo.getWindowingMode() == 6 || taskInfo.getWindowingMode() == 5)) {
                        if (sparseArray.get(i3) == null && taskAppearedInfo.getLeash().isValid() && shouldShowWindowDecor(taskInfo)) {
                            Slog.d("MultitaskingWindowDecorViewModel", "ensureHandlerOnTransitionFinished: show, t# " + i3);
                            createWindowDecoration(taskInfo, taskAppearedInfo.getLeash(), transaction, transaction2);
                            MultitaskingWindowDecoration multitaskingWindowDecoration2 = (MultitaskingWindowDecoration) sparseArray.get(i3);
                            if (multitaskingWindowDecoration2 != null) {
                                multitaskingWindowDecoration2.updateNonFreeformCaptionVisibility();
                            }
                            z4 = true;
                        }
                    }
                }
                if (z4) {
                    transaction.apply();
                    transaction2.apply();
                }
            }
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, TransitionInfo.Change change) {
        HashMap hashMap = (HashMap) this.mTransitionToTaskInfo;
        List list = (List) hashMap.get(iBinder);
        if (list == null) {
            list = new ArrayList();
            hashMap.put(iBinder, list);
        }
        list.add(change.getTaskInfo());
    }

    public final void resetLastImmersiveDecoration() {
        MultitaskingWindowDecoration multitaskingWindowDecoration = this.mLastImmersiveDecoration;
        if (multitaskingWindowDecoration != null) {
            multitaskingWindowDecoration.setImmersiveMode(false);
            MultitaskingWindowDecoration multitaskingWindowDecoration2 = this.mLastImmersiveDecoration;
            if (multitaskingWindowDecoration2.mIsNewDexMode) {
                multitaskingWindowDecoration2.setNewDexImmersiveCaptionBackground(false);
            }
            this.mLastImmersiveDecoration = null;
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void setFreeformTaskTransitionStarter(FreeformTaskTransitionHandler freeformTaskTransitionHandler) {
        Context context = this.mContext;
        SyncTransactionQueue syncTransactionQueue = this.mSyncQueue;
        Optional optional = this.mSplitScreenController;
        this.mTaskOperations = new TaskOperations(freeformTaskTransitionHandler, context, syncTransactionQueue, optional);
        if (CoreRune.MW_CAPTION_SHELL && optional.isPresent()) {
            ((SplitScreenController) optional.get()).setWindowDecorViewModel(Optional.of(this));
        }
    }

    public final boolean shouldShowWindowDecor(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Optional optional = this.mSplitScreenController;
        boolean z = optional.isPresent() && ((SplitScreenController) optional.get()).isTaskRootOrStageRoot(runningTaskInfo.taskId);
        if (CoreRune.MW_CAPTION_SHELL && runningTaskInfo.isSplitScreen()) {
            return (runningTaskInfo.isCaptionHandlerHidden || z) ? false : true;
        }
        if (CoreRune.MW_CAPTION_SHELL_BUG_FIX && runningTaskInfo.getConfiguration().isDesktopModeEnabled() && runningTaskInfo.getWindowingMode() == 6) {
            return false;
        }
        boolean z2 = CoreRune.MW_CAPTION_SHELL;
        if (z2 && runningTaskInfo.isTranslucentTask) {
            return false;
        }
        if (z2 && runningTaskInfo.getWindowingMode() == 2) {
            if (!this.mFixedRotatingDisplayIds.contains(Integer.valueOf(runningTaskInfo.displayId))) {
                return CoreRune.MT_NEW_DEX_PIP && isExitingPipTask((Pip) this.mPipOptional.get(), runningTaskInfo);
            }
        }
        if (CoreRune.MW_CAPTION_SHELL_FULL_SCREEN && canUseFullscreenHandler(runningTaskInfo, false)) {
            return !z;
        }
        if (runningTaskInfo.getWindowingMode() != 5) {
            return runningTaskInfo.getActivityType() == 1 && runningTaskInfo.configuration.windowConfiguration.getDisplayWindowingMode() == 5;
        }
        return true;
    }

    @Override // com.android.wm.shell.freeform.AdjustImeStateController
    public final void taskGainFocus(ActivityManager.RunningTaskInfo runningTaskInfo) {
        MultitaskingWindowDecoration multitaskingWindowDecoration;
        if (runningTaskInfo == null || (multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) == null) {
            return;
        }
        MultitaskingWindowDecoration.AdjustState adjustState = multitaskingWindowDecoration.mAdjustState;
        if (adjustState.mIsAdjusted) {
            return;
        }
        adjustState.setOriginBounds(runningTaskInfo.configuration.windowConfiguration.getBounds());
    }

    @Override // com.android.wm.shell.freeform.AdjustImeStateController
    public final void taskLostFocus(ActivityManager.RunningTaskInfo runningTaskInfo) {
        MultitaskingWindowDecoration multitaskingWindowDecoration;
        if (runningTaskInfo == null || (multitaskingWindowDecoration = (MultitaskingWindowDecoration) this.mWindowDecorByTaskId.get(runningTaskInfo.taskId)) == null) {
            return;
        }
        MultitaskingWindowDecoration.AdjustState adjustState = multitaskingWindowDecoration.mAdjustState;
        if (adjustState.mIsAdjusted) {
            adjustState.moveSurface(0);
            adjustState.adjustConfig(MultitaskingWindowDecoration.this.mTaskInfo.token, 0);
            adjustState.reset();
        }
    }

    public final void toggleDisableAllPinButton(int i, boolean z) {
        WindowMenuCaptionPresenter windowMenuCaptionPresenter;
        SparseArray sparseArray = this.mWindowDecorByTaskId;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) sparseArray.valueAt(size);
            if (multitaskingWindowDecoration != null && multitaskingWindowDecoration.mIsDexEnabled && multitaskingWindowDecoration.mTaskInfo.getWindowingMode() == 5 && ((multitaskingWindowDecoration.mTaskInfo.taskId != i || !z) && ((!CoreRune.MT_NEW_DEX_TASK_PINNING || !multitaskingWindowDecoration.mIsNewDexMode) && (windowMenuCaptionPresenter = multitaskingWindowDecoration.mCaptionMenuPresenter) != null))) {
                windowMenuCaptionPresenter.changePinButtonDisable(z);
            }
        }
    }

    public final void updateLastImmersiveDecoration(MultitaskingWindowDecoration multitaskingWindowDecoration) {
        if (this.mLastImmersiveDecoration != multitaskingWindowDecoration) {
            resetLastImmersiveDecoration();
            this.mLastImmersiveDecoration = multitaskingWindowDecoration;
        }
        boolean z = multitaskingWindowDecoration.mIsNewDexMode;
        boolean z2 = true;
        if (z && this.mLastImmersiveDecoration.mTaskInfo.getWindowingMode() != 1 && (!CoreRune.MT_NEW_DEX_PIP || !isExitingPipTask((Pip) this.mPipOptional.get(), this.mLastImmersiveDecoration.mTaskInfo))) {
            z2 = false;
        }
        multitaskingWindowDecoration.setImmersiveMode(z2);
        if (z) {
            multitaskingWindowDecoration.setNewDexImmersiveCaptionBackground(z2);
        }
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final AdjustImeStateController asAdjustImeStateController() {
        return this;
    }

    @Override // com.android.wm.shell.freeform.AdjustImeStateController
    public final void clearImeAdjustedTask() {
    }

    @Override // com.android.wm.shell.windowdecor.WindowDecorViewModel
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }
}
