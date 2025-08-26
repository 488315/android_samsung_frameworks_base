package com.android.wm.shell.naturalswitching;

import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.util.secutil.Slog;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DismissViewManager;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.multiwindow.SurfaceFreezerSnapshot;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.presence.ServiceTuple;
import java.util.List;

/* loaded from: classes3.dex */
public class NaturalSwitchingDropTargetController implements GestureDetector.OnGestureListener, ShellTaskOrganizer.TaskVanishedListener {
    public final ShellExecutor mBackgroundExecutor;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public GestureDetector mGestureDetector;
    public boolean mLayoutChanged;
    public final Handler mMainHandler;
    public NaturalSwitchingLayout mNaturalSwitchingLayout;
    public final SyncTransactionQueue mSyncQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final Transitions mTransitions;
    public boolean mIsRunning = false;
    public boolean mAllowInterceptTouch = true;

    public NaturalSwitchingDropTargetController(Context context, ShellTaskOrganizer shellTaskOrganizer, Handler handler, ShellExecutor shellExecutor, DisplayController displayController, Transitions transitions, SyncTransactionQueue syncTransactionQueue) {
        this.mContext = context;
        this.mTaskOrganizer = shellTaskOrganizer;
        shellTaskOrganizer.addTaskVanishedListener(this);
        this.mMainHandler = handler;
        this.mBackgroundExecutor = shellExecutor;
        this.mDisplayController = displayController;
        this.mTransitions = transitions;
        this.mSyncQueue = syncTransactionQueue;
    }

    public final boolean allowInterceptTouch(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (runningTaskInfo != null && runningTaskInfo.supportsMultiWindow) {
            int windowingMode = runningTaskInfo.getWindowingMode();
            if ((windowingMode == 1 ? CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN : windowingMode == 2 ? CoreRune.MW_NATURAL_SWITCHING_PIP : true) && ((!CoreRune.MW_NATURAL_SWITCHING_PIP || windowingMode != 2 || !runningTaskInfo.supportsPipOnly) && runningTaskInfo.getDisplayId() == 0)) {
                DesktopStateImpl.Companion.getClass();
                if (!DesktopStateImpl.Companion.inDesktopWindowing(0) && !MultiWindowManager.getInstance().preventNaturalSwitching(runningTaskInfo.taskId)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) this.mContext.getSystemService(InputMethodManager.class);
                    if (!(inputMethodManager != null && inputMethodManager.isInputMethodShown()) && !this.mNaturalSwitchingLayout.mSplitScreenController.mSplitState.isSplitStashed()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public final void cancelNaturalSwitching(String str) {
        if (this.mIsRunning) {
            this.mIsRunning = false;
            StringBuilder sbM = ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("cancelNaturalSwitching: ", str, ", ");
            sbM.append(this.mTaskInfo);
            Log.w("NaturalSwitchingDropTargetController", sbM.toString());
            this.mNaturalSwitchingLayout.hide(false);
        }
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:81:0x015e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent, int i) {
        int i2;
        int stagePosition;
        int i3;
        NaturalSwitchingLayout naturalSwitchingLayout;
        boolean z = false;
        if (!this.mAllowInterceptTouch && motionEvent.getAction() != 0) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 0) {
            boolean z2 = true;
            if (action != 1) {
                if (action != 2) {
                    if (action == 3) {
                        cancelNaturalSwitching("ACTION_CANCEL");
                    }
                } else if (this.mIsRunning) {
                    this.mNaturalSwitchingLayout.update(motionEvent);
                    z = true;
                }
            } else if (this.mIsRunning) {
                this.mIsRunning = false;
                StringBuilder sb = new StringBuilder("completeNaturalSwitching: ");
                ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
                sb.append(runningTaskInfo != null ? runningTaskInfo.taskId : -1);
                Log.w("NaturalSwitchingDropTargetController", sb.toString());
                NaturalSwitchingLayout naturalSwitchingLayout2 = this.mNaturalSwitchingLayout;
                DragTargetView dragTargetView = naturalSwitchingLayout2.mDragTargetView;
                if (dragTargetView == null) {
                    Log.i("NaturalSwitchingLayout", "drag target view is already null. : callers=" + Debug.getCallers(3));
                } else {
                    naturalSwitchingLayout2.mHasDropped = true;
                    DismissViewManager dismissViewManager = naturalSwitchingLayout2.mCancelButtonManager;
                    if (dismissViewManager == null || !dismissViewManager.mView.mIsEnterDismissButton) {
                        Rect currentDragTargetRect = dragTargetView.getCurrentDragTargetRect();
                        NaturalSwitchingAlgorithm naturalSwitchingAlgorithm = naturalSwitchingLayout2.mNaturalSwitchingAlgorithm;
                        int i4 = naturalSwitchingAlgorithm.mSplitCreateMode;
                        if (naturalSwitchingLayout2.mNaturalSwitchingMode == 1) {
                            i2 = naturalSwitchingAlgorithm.mToWindowingMode;
                            stagePosition = naturalSwitchingAlgorithm.mToPosition;
                        } else {
                            NonDragTarget nonDragTarget = naturalSwitchingLayout2.mNonDragTargetView.mDropTarget;
                            i2 = nonDragTarget != null ? nonDragTarget.mNsWindowingMode : 0;
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) naturalSwitchingLayout2.mTaskVisibility.mRunningTaskInfo.get(i2);
                            stagePosition = runningTaskInfo2 != null ? runningTaskInfo2.configuration.windowConfiguration.getStagePosition() : 0;
                        }
                        int i5 = stagePosition;
                        if (naturalSwitchingLayout2.mNsWindowingMode == 5 && i2 == 5) {
                            naturalSwitchingLayout2.mDragTargetView.adjustDragTargetViewBoundsIfNeeded();
                        }
                        if (i2 == 0 || (i2 == 5 && naturalSwitchingLayout2.mNsWindowingMode == 5)) {
                            naturalSwitchingLayout2.mNonDragTargetView.startTransition(false);
                        }
                        naturalSwitchingLayout2.mHandler.postDelayed(naturalSwitchingLayout2.mHideRunnable, 5000L);
                        if (i2 == 13) {
                            ActivityManager.RunningTaskInfo runningTaskInfo3 = naturalSwitchingLayout2.mTaskInfo;
                            float rawX = motionEvent.getRawX();
                            float rawY = motionEvent.getRawY();
                            SplitScreenController splitScreenController = naturalSwitchingLayout2.mSplitScreenController;
                            boolean zIsVerticalDivision = splitScreenController.isVerticalDivision();
                            int i6 = 16;
                            if (!CoreRune.MW_MULTI_SPLIT || MultiWindowUtils.isInSubDisplay(naturalSwitchingLayout2.mContext)) {
                                Rect rect = new Rect();
                                splitScreenController.getStageBounds(rect, new Rect());
                                if (!rect.contains((int) rawX, (int) rawY)) {
                                    i6 = zIsVerticalDivision ? 32 : 64;
                                } else if (zIsVerticalDivision) {
                                    i6 = 8;
                                }
                            } else {
                                NonDragTarget nonDragTarget2 = naturalSwitchingLayout2.mNonDragTargetView.mDropTarget;
                                if (nonDragTarget2 != null) {
                                    i6 = nonDragTarget2.mStagePosition;
                                }
                            }
                            naturalSwitchingLayout2.mSplitScreenController.onFreeformToSplitRequested(runningTaskInfo3, true, i6, false, currentDragTargetRect, false);
                            naturalSwitchingLayout2.hide(true);
                            i3 = i2;
                        } else {
                            i3 = i2;
                            NaturalSwitchingLayout$$ExternalSyntheticLambda5 naturalSwitchingLayout$$ExternalSyntheticLambda5 = new NaturalSwitchingLayout$$ExternalSyntheticLambda5(naturalSwitchingLayout2, i3, i5, i4, currentDragTargetRect, 0);
                            if (naturalSwitchingLayout2.mNaturalSwitchingMode == 1) {
                                NonDragTargetView nonDragTargetView = naturalSwitchingLayout2.mNonDragTargetView;
                                nonDragTargetView.mOnDrawCallback = naturalSwitchingLayout$$ExternalSyntheticLambda5;
                                nonDragTargetView.mMainView.setBackgroundColor(Color.parseColor("#FF000000"));
                            } else {
                                naturalSwitchingLayout$$ExternalSyntheticLambda5.run();
                            }
                        }
                        if (naturalSwitchingLayout2.mNsWindowingMode == i3) {
                        }
                        this.mLayoutChanged = z2;
                        naturalSwitchingLayout = this.mNaturalSwitchingLayout;
                        if (!naturalSwitchingLayout.mHasDropped) {
                            naturalSwitchingLayout.hide(false);
                        }
                    } else {
                        naturalSwitchingLayout2.hide(false);
                    }
                }
                z2 = false;
                this.mLayoutChanged = z2;
                naturalSwitchingLayout = this.mNaturalSwitchingLayout;
                if (!naturalSwitchingLayout.mHasDropped) {
                }
            }
        } else {
            ActivityManager.RunningTaskInfo runningTaskInfo4 = this.mTaskOrganizer.getRunningTaskInfo(i);
            this.mTaskInfo = runningTaskInfo4;
            this.mAllowInterceptTouch = allowInterceptTouch(runningTaskInfo4);
            this.mLayoutChanged = false;
            Log.d("NaturalSwitchingDropTargetController", "onInterceptTouchEvent: allow=" + this.mAllowInterceptTouch + ", " + motionEvent);
        }
        if (this.mAllowInterceptTouch) {
            this.mGestureDetector.onTouchEvent(motionEvent);
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:137:0x042e  */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0581  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x00de A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01a3  */
    @Override // android.view.GestureDetector.OnGestureListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onLongPress(MotionEvent motionEvent) throws Resources.NotFoundException {
        int i;
        int i2;
        boolean zIsTaskVisible;
        int iWidth;
        int iMax;
        NonDragTarget nonDragTarget;
        Log.d("NaturalSwitchingDropTargetController", "onLongPress: " + motionEvent);
        this.mIsRunning = true;
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mTaskInfo.displayId);
        NaturalSwitchingLayout naturalSwitchingLayout = this.mNaturalSwitchingLayout;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        naturalSwitchingLayout.getClass();
        Log.d("NaturalSwitchingLayout", "prepare");
        naturalSwitchingLayout.mReadyToStart = false;
        naturalSwitchingLayout.mHideRequested = false;
        naturalSwitchingLayout.mHasDropped = false;
        TaskVisibility taskVisibility = naturalSwitchingLayout.mTaskVisibility;
        taskVisibility.mDisplayLayout = displayLayout;
        if (CoreRune.MW_MULTI_SPLIT) {
            taskVisibility.mSupportOnlyTwoUpMode = taskVisibility.mContext.getResources().getConfiguration().semDisplayDeviceType == 5;
        } else {
            taskVisibility.mSupportOnlyTwoUpMode = true;
        }
        taskVisibility.mRunningTaskInfo.clear();
        List<ActivityManager.RunningTaskInfo> visibleTasks = MultiWindowManager.getInstance().getVisibleTasks();
        int i3 = 3;
        if (visibleTasks == null) {
            Log.w("TaskVisibility", "initRunningTaskInfos: failed to get list");
        } else {
            boolean z = false;
            for (ActivityManager.RunningTaskInfo runningTaskInfo2 : visibleTasks) {
                if (runningTaskInfo2.isVisible() && runningTaskInfo2.displayId == 0) {
                    WindowConfiguration windowConfiguration = runningTaskInfo2.configuration.windowConfiguration;
                    int activityType = windowConfiguration.getActivityType();
                    int windowingMode = windowConfiguration.getWindowingMode();
                    int stageType = windowConfiguration.getStageType();
                    if ((activityType == 2 || activityType == i3) && !z) {
                        taskVisibility.mRunningTaskInfo.put(13, runningTaskInfo2);
                    } else if (activityType == 0 || activityType == 1) {
                        int naturalSwitchingWindowingMode = NaturalSwitchingLayout.getNaturalSwitchingWindowingMode(windowingMode, stageType);
                        if (windowingMode != 1) {
                            if (windowingMode != 2) {
                                if (windowingMode == 5) {
                                    if (taskVisibility.mRunningTaskInfo.contains(naturalSwitchingWindowingMode)) {
                                        taskVisibility.mRunningTaskInfo.put(naturalSwitchingWindowingMode, runningTaskInfo2);
                                    }
                                } else if (windowingMode != 6) {
                                }
                            } else if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                                taskVisibility.mRunningTaskInfo.put(naturalSwitchingWindowingMode, runningTaskInfo2);
                            }
                        }
                        z = true;
                        if (taskVisibility.mRunningTaskInfo.contains(naturalSwitchingWindowingMode)) {
                        }
                    }
                }
                i3 = 3;
            }
            Log.d("TaskVisibility", "initRunningTaskInfos: " + taskVisibility);
        }
        naturalSwitchingLayout.mStatusBarManager.disable(23068672);
        naturalSwitchingLayout.mTouchSlop = ViewConfiguration.get(naturalSwitchingLayout.mContext).getScaledTouchSlop() * 2;
        naturalSwitchingLayout.mPassedInitialSlop = false;
        naturalSwitchingLayout.mTouchGap.set(0, 0);
        naturalSwitchingLayout.mDownPoint.set(-1, -1);
        naturalSwitchingLayout.mTaskInfo = runningTaskInfo;
        int naturalSwitchingWindowingMode2 = NaturalSwitchingLayout.getNaturalSwitchingWindowingMode(runningTaskInfo.getWindowingMode(), naturalSwitchingLayout.mTaskInfo.configuration.windowConfiguration.getStageType());
        naturalSwitchingLayout.mNsWindowingMode = naturalSwitchingWindowingMode2;
        boolean z2 = CoreRune.MW_NATURAL_SWITCHING_PIP;
        if (z2) {
            naturalSwitchingLayout.mIsPipNaturalSwitching = naturalSwitchingWindowingMode2 == 2;
        }
        NaturalSwitchingAlgorithm naturalSwitchingAlgorithm = naturalSwitchingLayout.mNaturalSwitchingAlgorithm;
        naturalSwitchingAlgorithm.mTaskVisibility = taskVisibility;
        SplitScreenController splitScreenController = naturalSwitchingLayout.mSplitScreenController;
        naturalSwitchingAlgorithm.mSplitScreenController = splitScreenController;
        naturalSwitchingAlgorithm.mDragTargetWindowingMode = naturalSwitchingWindowingMode2;
        naturalSwitchingAlgorithm.mUseSingleNonTarget = (NaturalSwitchingLayout.isFloating(naturalSwitchingWindowingMode2) && (naturalSwitchingAlgorithm.mTaskVisibility.isTaskVisible(1) || naturalSwitchingAlgorithm.mTaskVisibility.isTaskVisible(13))) || (((i = naturalSwitchingAlgorithm.mDragTargetWindowingMode) == 3 || i == 4 || i == 12) && naturalSwitchingAlgorithm.mTaskVisibility.isTwoUp());
        naturalSwitchingAlgorithm.mToWindowingMode = naturalSwitchingWindowingMode2;
        naturalSwitchingAlgorithm.mShrunkWindowingMode = 0;
        naturalSwitchingAlgorithm.mSwapWindowingMode = 0;
        naturalSwitchingAlgorithm.mDropSide = 0;
        naturalSwitchingAlgorithm.mToPosition = 0;
        naturalSwitchingAlgorithm.mPushRegion = 0;
        naturalSwitchingAlgorithm.mNeedToReparentCell = false;
        if (NaturalSwitchingLayout.isFloating(naturalSwitchingLayout.mNsWindowingMode)) {
            boolean zIsTwoUp = (!CoreRune.MW_MULTI_SPLIT || taskVisibility.mSupportOnlyTwoUpMode) ? taskVisibility.isTwoUp() : taskVisibility.isMultiSplit();
            if (zIsTwoUp) {
                naturalSwitchingLayout.mNaturalSwitchingMode = 2;
            }
        } else if (taskVisibility.isTaskVisible(13)) {
            naturalSwitchingLayout.mNaturalSwitchingMode = 2;
        } else {
            if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN) {
                i2 = 1;
                if (naturalSwitchingLayout.mNsWindowingMode == 1) {
                    naturalSwitchingLayout.mNaturalSwitchingMode = 2;
                }
            } else {
                i2 = 1;
            }
            naturalSwitchingLayout.mNaturalSwitchingMode = i2;
        }
        NonDragTargetView nonDragTargetView = (NonDragTargetView) LayoutInflater.from(naturalSwitchingLayout.mContext).inflate(R.layout.ns_non_drag_target_view, (ViewGroup) null);
        naturalSwitchingLayout.mNonDragTargetView = nonDragTargetView;
        nonDragTargetView.init(naturalSwitchingLayout.mNsWindowingMode, naturalSwitchingLayout.mTaskVisibility, naturalSwitchingLayout.mNaturalSwitchingMode, naturalSwitchingLayout.mSplitScreenController, naturalSwitchingLayout.mBackgroundExecutor);
        NonDragTargetView nonDragTargetView2 = naturalSwitchingLayout.mNonDragTargetView;
        nonDragTargetView2.getRootView().getViewTreeObserver().addOnDrawListener(nonDragTargetView2.mOnDrawListener);
        DragTargetView dragTargetView = (DragTargetView) LayoutInflater.from(naturalSwitchingLayout.mContext).inflate(R.layout.ns_drag_target_view, (ViewGroup) null);
        naturalSwitchingLayout.mDragTargetView = dragTargetView;
        int i4 = runningTaskInfo.taskId;
        int i5 = naturalSwitchingLayout.mNsWindowingMode;
        dragTargetView.getClass();
        Log.d("DragTargetView", "init: t#" + i4);
        dragTargetView.mWm = (WindowManager) dragTargetView.getContext().getSystemService("window");
        dragTargetView.mController = splitScreenController;
        dragTargetView.mIsDragEndCalled = false;
        dragTargetView.mTaskVisibility = taskVisibility;
        dragTargetView.mDragTargetWindowingMode = i5;
        dragTargetView.mDragTarget = (FrameLayout) dragTargetView.findViewById(R.id.drag_target);
        dragTargetView.mDragTargetImage = (ImageView) dragTargetView.findViewById(R.id.drag_target_image);
        dragTargetView.mDragTargetBounds = dragTargetView.mTaskVisibility.getTaskBounds(dragTargetView.mDragTargetWindowingMode);
        if (z2 && dragTargetView.isPipNaturalSwitching()) {
            dragTargetView.mDragTarget.setZ(0.0f);
        } else {
            dragTargetView.mDragTarget.setClipToOutline(true);
            dragTargetView.mDragTarget.setOutlineProvider(dragTargetView.mOutlineProvider);
        }
        dragTargetView.mTaskVisibility.mDisplayLayout.getDisplayBounds(dragTargetView.mDisplayBounds);
        dragTargetView.mTaskVisibility.mDisplayLayout.getStableBounds(dragTargetView.mStableRect, true);
        dragTargetView.mCornerRadius = dragTargetView.getResources().getDimensionPixelOffset(17105925);
        dragTargetView.mDividerSize = dragTargetView.getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_25) - (dragTargetView.getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_24) * 2);
        if (z2 && dragTargetView.isPipNaturalSwitching()) {
            dragTargetView.mDragTargetImage.setBackgroundColor(NaturalSwitchingLayout.DEBUG_PIP ? 1711341312 : 0);
        } else {
            SurfaceFreezerSnapshot surfaceFreezerSnapshot = MultiWindowManager.getInstance().getSurfaceFreezerSnapshot(i4);
            if (surfaceFreezerSnapshot != null) {
                dragTargetView.mHasProtectedContent = surfaceFreezerSnapshot.hasProtectedContent();
            }
            if (dragTargetView.mHasProtectedContent) {
                dragTargetView.mDragTargetImage.setBackgroundColor(dragTargetView.getResources().getColor(R.color.protected_content_bg_color));
                dragTargetView.mDragTargetImage.setImageDrawable(dragTargetView.getResources().getDrawable(R.drawable.mw_splitview_ic_previewlock_mtrl));
                dragTargetView.mDragTargetImage.getDrawable().setAlpha(76);
                dragTargetView.mDragTargetImage.setScaleType(ImageView.ScaleType.CENTER);
            } else {
                Bitmap snapshotBitmap = surfaceFreezerSnapshot != null ? surfaceFreezerSnapshot.getSnapshotBitmap() : null;
                if (snapshotBitmap == null) {
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(i4, "initThumbnail: failed to get snapshot, task #", "DragTargetView");
                } else {
                    dragTargetView.mDragTargetImage.setImageBitmap(snapshotBitmap);
                }
                dragTargetView.mDragTargetImage.setScaleType(ImageView.ScaleType.MATRIX);
            }
        }
        FrameLayout frameLayout = dragTargetView.mDragTarget;
        DynamicAnimation.AnonymousClass4 anonymousClass4 = DynamicAnimation.SCALE_X;
        SpringAnimation springAnimation = new SpringAnimation(frameLayout, anonymousClass4);
        springAnimation.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(200.0f, 0.99f);
        dragTargetView.mScaleDownAnimX = springAnimation;
        FrameLayout frameLayout2 = dragTargetView.mDragTarget;
        DynamicAnimation.AnonymousClass5 anonymousClass5 = DynamicAnimation.SCALE_Y;
        SpringAnimation springAnimation2 = new SpringAnimation(frameLayout2, anonymousClass5);
        springAnimation2.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(200.0f, 0.99f);
        dragTargetView.mScaleDownAnimY = springAnimation2;
        SpringAnimation springAnimation3 = new SpringAnimation(dragTargetView.mDragTarget, anonymousClass4);
        springAnimation3.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(220.0f, 0.47f);
        dragTargetView.mScaleUpAnimX = springAnimation3;
        SpringAnimation springAnimation4 = new SpringAnimation(dragTargetView.mDragTarget, anonymousClass5);
        springAnimation4.mSpring = ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(220.0f, 0.47f);
        dragTargetView.mScaleUpAnimY = springAnimation4;
        if (!dragTargetView.mDragTargetBounds.isEmpty()) {
            int dimensionPixelSize = dragTargetView.getResources().getDimensionPixelSize(R.dimen.natural_switching_scale_delta);
            int iWidth2 = dragTargetView.mDragTargetBounds.width();
            int iHeight = dragTargetView.mDragTargetBounds.height();
            PointF pointF = dragTargetView.mDownScale;
            float fMin = Math.min((iWidth2 - dimensionPixelSize) / iWidth2, (iHeight - dimensionPixelSize) / iHeight);
            pointF.y = fMin;
            pointF.x = fMin;
            dragTargetView.mUpScale.set(1.015f, 1.015f);
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2016, 131856, -2);
        dragTargetView.mLp = layoutParams;
        layoutParams.setTitle("NS:DragTargetView");
        WindowManager.LayoutParams layoutParams2 = dragTargetView.mLp;
        layoutParams2.privateFlags |= 80;
        layoutParams2.samsungFlags |= 131072;
        layoutParams2.setFitInsetsTypes(0);
        WindowManager.LayoutParams layoutParams3 = dragTargetView.mLp;
        layoutParams3.layoutInDisplayCutoutMode = 3;
        layoutParams3.y = 0;
        layoutParams3.x = 0;
        layoutParams3.width = dragTargetView.mDisplayBounds.width();
        dragTargetView.mLp.height = dragTargetView.mDisplayBounds.height();
        WindowManager.LayoutParams layoutParams4 = dragTargetView.mLp;
        layoutParams4.gravity = 8388659;
        dragTargetView.mWm.addView(dragTargetView, layoutParams4);
        naturalSwitchingAlgorithm.mHalfTarget = naturalSwitchingLayout.mNonDragTargetView.mHalfTarget;
        if (!CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN || naturalSwitchingLayout.mNsWindowingMode != 1) {
            if (z2 && naturalSwitchingLayout.mIsPipNaturalSwitching) {
                boolean zIsTwoUp2 = (!CoreRune.MW_MULTI_SPLIT || taskVisibility.mSupportOnlyTwoUpMode) ? taskVisibility.isTwoUp() : taskVisibility.isMultiSplit();
                if (zIsTwoUp2) {
                    zIsTaskVisible = true;
                }
            }
            zIsTaskVisible = taskVisibility.isTaskVisible(13);
        }
        if (zIsTaskVisible) {
            DismissViewManager dismissViewManager = new DismissViewManager(naturalSwitchingLayout.mContext, 4, 2016);
            naturalSwitchingLayout.mCancelButtonManager = dismissViewManager;
            dismissViewManager.createDismissView();
            dismissViewManager.createOrUpdateWrapper();
        }
        DragTargetView dragTargetView2 = naturalSwitchingLayout.mDragTargetView;
        dragTargetView2.mNonDragTargetView = naturalSwitchingLayout.mNonDragTargetView;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) dragTargetView2.mDragTarget.getLayoutParams();
        Drawable drawable = dragTargetView2.mDragTargetImage.getDrawable();
        if (dragTargetView2.mDragTargetBounds.isEmpty()) {
            dragTargetView2.mDragTargetBounds.set(0, 0, (dragTargetView2.mHasProtectedContent || drawable == null) ? dragTargetView2.mDragTarget.getWidth() : drawable.getIntrinsicWidth(), (dragTargetView2.mHasProtectedContent || drawable == null) ? dragTargetView2.mDragTarget.getHeight() : drawable.getIntrinsicHeight());
        }
        Rect rect = dragTargetView2.mDragTargetBounds;
        marginLayoutParams.leftMargin = rect.left;
        marginLayoutParams.topMargin = rect.top;
        marginLayoutParams.width = rect.width();
        marginLayoutParams.height = dragTargetView2.mDragTargetBounds.height();
        dragTargetView2.mDragTarget.setVisibility(0);
        DragTargetView dragTargetView3 = naturalSwitchingLayout.mDragTargetView;
        dragTargetView3.getClass();
        if (z2 && dragTargetView3.isPipNaturalSwitching()) {
            iWidth = (int) motionEvent.getRawX();
            iMax = (int) motionEvent.getRawY();
        } else {
            Rect rect2 = dragTargetView3.mDragTargetBounds;
            iWidth = (rect2.width() / 2) + rect2.left;
            iMax = Math.max(dragTargetView3.mStableRect.top, dragTargetView3.mDragTargetBounds.top);
        }
        dragTargetView3.mHandlerPosition.set(iWidth, iMax);
        Log.d("DragTargetView", "initHandlerPosition: " + dragTargetView3.mHandlerPosition);
        NonDragTargetView nonDragTargetView3 = naturalSwitchingLayout.mNonDragTargetView;
        for (int size = nonDragTargetView3.mNonTargets.size() + (-1); size >= 0; size--) {
            NonDragTarget nonDragTarget2 = (NonDragTarget) nonDragTargetView3.mNonTargets.valueAt(size);
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) nonDragTarget2.mView.getLayoutParams();
            ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) nonDragTarget2.mBlurView.getLayoutParams();
            int iWidth3 = nonDragTarget2.mBaseBounds.width();
            marginLayoutParams2.width = iWidth3;
            marginLayoutParams3.width = iWidth3;
            int iHeight2 = nonDragTarget2.mBaseBounds.height();
            marginLayoutParams2.height = iHeight2;
            marginLayoutParams3.height = iHeight2;
            Rect rect3 = nonDragTarget2.mBaseBounds;
            int i6 = rect3.left;
            marginLayoutParams2.leftMargin = i6;
            marginLayoutParams3.leftMargin = i6;
            int i7 = rect3.top;
            marginLayoutParams2.topMargin = i7;
            marginLayoutParams3.topMargin = i7;
            if (nonDragTarget2.mHasProtectedContent) {
                nonDragTarget2.mView.setScaleType(ImageView.ScaleType.CENTER);
            } else {
                ImageView imageView = nonDragTarget2.mView;
                ImageView.ScaleType scaleType = ImageView.ScaleType.MATRIX;
                imageView.setScaleType(scaleType);
                nonDragTarget2.mBlurView.setScaleType(scaleType);
            }
        }
        if (nonDragTargetView3.mNaturalSwitchingMode == 2) {
            NonDragTarget nonDragTargetCreateNonDragTarget = nonDragTargetView3.createNonDragTarget();
            int i8 = nonDragTargetView3.mDragTargetWindowingMode;
            if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN) {
                if (i8 == 1) {
                    nonDragTargetCreateNonDragTarget.init(nonDragTargetView3, 0, 5, nonDragTargetView3.getCenterFreeformBounds(), 0);
                    nonDragTarget = nonDragTargetCreateNonDragTarget;
                    i8 = 5;
                } else {
                    nonDragTarget = nonDragTargetCreateNonDragTarget;
                }
                nonDragTarget.initForTaskOnly(nonDragTargetView3, nonDragTargetView3.mContainingBounds, i8);
                nonDragTargetView3.mNonTargets.put(i8, nonDragTarget);
            }
        }
        final DragTargetView dragTargetView4 = naturalSwitchingLayout.mDragTargetView;
        final NaturalSwitchingLayout$$ExternalSyntheticLambda0 naturalSwitchingLayout$$ExternalSyntheticLambda0 = new NaturalSwitchingLayout$$ExternalSyntheticLambda0(naturalSwitchingLayout, 0);
        final View rootView = dragTargetView4.getRootView();
        rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(dragTargetView4, rootView, naturalSwitchingLayout$$ExternalSyntheticLambda0) { // from class: com.android.wm.shell.naturalswitching.DragTargetView.5
            public final /* synthetic */ Runnable val$postRunnable;
            public final /* synthetic */ View val$rootView;

            public AnonymousClass5(final DragTargetView dragTargetView42, final View rootView2, final Runnable naturalSwitchingLayout$$ExternalSyntheticLambda02) {
                this.val$rootView = rootView2;
                this.val$postRunnable = naturalSwitchingLayout$$ExternalSyntheticLambda02;
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public final boolean onPreDraw() {
                this.val$rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                Runnable runnable = this.val$postRunnable;
                if (runnable == null) {
                    return true;
                }
                runnable.run();
                return true;
            }
        });
        NaturalSwitchingLayout naturalSwitchingLayout2 = this.mNaturalSwitchingLayout;
        naturalSwitchingLayout2.mNonDragTargetView.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(108));
        AudioManager audioManager = (AudioManager) naturalSwitchingLayout2.mContext.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        if (audioManager == null) {
            Slog.w("NaturalSwitchingLayout", "Couldn't get audio manager");
        } else {
            audioManager.playSoundEffect(106);
        }
        DismissViewManager dismissViewManager2 = this.mNaturalSwitchingLayout.mCancelButtonManager;
        if (dismissViewManager2 != null) {
            Insets insets = Insets.NONE;
            dismissViewManager2.show();
        }
        this.mNaturalSwitchingLayout.update(motionEvent);
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskVanishedListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        int i = runningTaskInfo.taskId;
        if (this.mIsRunning) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
            if ((runningTaskInfo2 != null ? runningTaskInfo2.taskId : -1) == i) {
                cancelNaturalSwitching("TaskVanished(" + runningTaskInfo.taskId + ")");
            }
        }
    }

    @Override // android.view.GestureDetector.OnGestureListener
    public final void onShowPress(MotionEvent motionEvent) {
    }
}
