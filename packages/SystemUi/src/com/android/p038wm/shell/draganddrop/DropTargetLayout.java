package com.android.p038wm.shell.draganddrop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.StatusBarManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.provider.Settings;
import android.util.Slog;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.protolog.common.ProtoLog;
import com.android.p038wm.shell.common.DismissButtonView;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.draganddrop.DragAndDropPolicy;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DropTargetLayout extends FrameLayout implements IDragLayout {
    public final DragAppIcon mAppIcon;
    public int mCurrentDensityDpi;
    public DragAndDropPolicy.Target mCurrentTarget;
    public boolean mDensityChanged;
    public DismissButtonView mDismissButtonView;
    public SurfaceControl mDragSurface;
    public final DropTargetView mDropTargetView;
    public boolean mFirstDropTargetShown;
    public boolean mHasDragSourceTask;
    public boolean mHasDrawable;
    public boolean mHasDropped;
    public boolean mIsShowing;
    public final DragAndDropPolicy mPolicy;
    public final StatusBarManager mStatusBarManager;
    public final DragAndDropOptions mTmpOptions;
    public final SurfaceControl.Transaction mTransaction;

    public DropTargetLayout(Context context, SplitScreenController splitScreenController, SurfaceControl.Transaction transaction) {
        super(context);
        this.mTmpOptions = new DragAndDropOptions();
        this.mCurrentTarget = null;
        this.mDensityChanged = false;
        this.mHasDragSourceTask = false;
        this.mPolicy = new DragAndDropPolicy(context, splitScreenController);
        FrameLayout.inflate(context, R.layout.drop_target_layout, this);
        this.mDropTargetView = (DropTargetView) findViewById(R.id.drop_target);
        createDismissButtonView();
        this.mTransaction = transaction;
        this.mAppIcon = (DragAppIcon) findViewById(R.id.drag_app_icon);
        this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        this.mCurrentDensityDpi = getResources().getConfiguration().densityDpi;
    }

    public final void createDismissButtonView() {
        DismissButtonView dismissButtonView = (DismissButtonView) LayoutInflater.from(getContext()).inflate(R.layout.dismiss_button_view, (ViewGroup) this, false);
        this.mDismissButtonView = dismissButtonView;
        dismissButtonView.setDismissType(1);
        DismissButtonView dismissButtonView2 = this.mDismissButtonView;
        dismissButtonView2.mFocusChangeHapticDisabled = true;
        addView(dismissButtonView2);
    }

    public final void hide(Runnable runnable, boolean z) {
        SurfaceControl surfaceControl;
        this.mIsShowing = false;
        DismissButtonView dismissButtonView = this.mDismissButtonView;
        if (dismissButtonView.mVisible) {
            dismissButtonView.mVisible = false;
            dismissButtonView.clearAnimation();
            dismissButtonView.setVisibility(4);
        }
        updateNavigationBarVisibility(true);
        if (this.mCurrentTarget != null) {
            this.mDropTargetView.setVisibility(8);
        }
        DragAppIcon dragAppIcon = this.mAppIcon;
        dragAppIcon.setVisibility(8);
        dragAppIcon.setImageDrawable(null);
        if (z && (surfaceControl = this.mDragSurface) != null) {
            this.mTransaction.reparent(surfaceControl, null);
            this.mTransaction.apply();
            this.mDragSurface = null;
        }
        if (runnable != null) {
            runnable.run();
        }
        this.mCurrentTarget = null;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        recomputeDropTargets();
        return super.onApplyWindowInsets(windowInsets);
    }

    public final void onConfigChanged(Configuration configuration) {
        onConfigurationChanged(configuration);
        this.mDensityChanged = this.mCurrentDensityDpi != configuration.densityDpi;
    }

    public final void prepare(DisplayLayout displayLayout, ClipData clipData, InstanceId instanceId, SurfaceControl surfaceControl, ExecutableAppHolder executableAppHolder, VisibleTasks visibleTasks) {
        List nonFloatingTopTask;
        Intent intent;
        DragAndDropPolicy dragAndDropPolicy = this.mPolicy;
        dragAndDropPolicy.mLoggerSessionId = instanceId;
        DragAndDropPolicy.DragSession dragSession = new DragAndDropPolicy.DragSession(dragAndDropPolicy.mActivityTaskManager, displayLayout, clipData, dragAndDropPolicy.mMultiWindowManager, executableAppHolder, visibleTasks);
        dragAndDropPolicy.mSession = dragSession;
        ClipData clipData2 = dragSession.mInitialDragData;
        boolean isDragFromRecent = clipData2.getDescription().isDragFromRecent();
        dragSession.isDragFromRecent = isDragFromRecent;
        int intExtra = (!isDragFromRecent || (intent = clipData2.getItemAt(0).getIntent()) == null) ? -1 : intent.getIntExtra("android.intent.extra.DND_RECENT_TOP_TASK_ID", -1);
        int dragSourceTaskId = clipData2.getDescription().getDragSourceTaskId();
        if (dragSession.isDragFromRecent && intExtra != -1) {
            Iterator it = dragSession.mActivityTaskManager.getTasks(Integer.MAX_VALUE, false).iterator();
            while (true) {
                if (!it.hasNext()) {
                    nonFloatingTopTask = Collections.EMPTY_LIST;
                    break;
                }
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
                if (runningTaskInfo.taskId == intExtra) {
                    nonFloatingTopTask = List.of(runningTaskInfo);
                    break;
                }
            }
        } else {
            nonFloatingTopTask = dragSession.getNonFloatingTopTask(dragSourceTaskId);
        }
        if (!nonFloatingTopTask.isEmpty()) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) nonFloatingTopTask.get(0);
            runningTaskInfo2.getWindowingMode();
            dragSession.runningTaskActType = runningTaskInfo2.topActivityType;
            dragSession.runningTaskSupportsSplitScreen = runningTaskInfo2.supportsMultiWindow;
        }
        ActivityInfo activityInfo = clipData2.getItemAt(0).getActivityInfo();
        if (MultiWindowCoreState.MW_ENABLED) {
            ExecutableAppHolder executableAppHolder2 = dragSession.mExecutableAppHolder;
            if (executableAppHolder2 != null) {
                AppResult appResult = executableAppHolder2.mResult;
                dragSession.dragItemSupportsSplitscreen = appResult != null && appResult.hasResizableResolveInfo();
            } else {
                dragSession.dragItemSupportsSplitscreen = activityInfo == null || ActivityInfo.isResizeableMode(activityInfo.resizeMode);
            }
        } else {
            dragSession.dragItemSupportsSplitscreen = false;
        }
        dragSession.dragData = clipData2.getItemAt(0).getIntent();
        Intent intent2 = dragAndDropPolicy.mSession.dragData;
        RectF rectF = intent2 == null ? null : (RectF) intent2.getExtra("DISALLOW_HIT_REGION");
        RectF rectF2 = dragAndDropPolicy.mDisallowHitRegion;
        if (rectF == null) {
            rectF2.setEmpty();
        } else {
            rectF2.set(rectF);
        }
        this.mHasDropped = false;
        this.mCurrentTarget = null;
        this.mHasDrawable = false;
        this.mFirstDropTargetShown = false;
        this.mDragSurface = surfaceControl;
        this.mHasDragSourceTask = clipData.getDescription().getDragSourceTaskId() != -1;
        if (CoreRune.MW_DND_SA_LOGGING) {
            this.mPolicy.mCallingPackageName = clipData.getCallingPackageName();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:144:0x00d5, code lost:
    
        if ((r11.getResources().getConfiguration().semDisplayDeviceType == 5) != false) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00c0, code lost:
    
        if ((r7 != null && r7.hasResolveInfoInFullscreenOnly(r6.mVisibleTasks)) == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d7, code lost:
    
        r6 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0175  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean recomputeDropTargets() {
        boolean z;
        boolean z2;
        int i;
        String str;
        Iterator it;
        int i2;
        ComponentName componentName;
        ExecutableAppHolder executableAppHolder;
        if (!this.mIsShowing) {
            return false;
        }
        DragAndDropPolicy dragAndDropPolicy = this.mPolicy;
        ArrayList arrayList = dragAndDropPolicy.mTargets;
        arrayList.clear();
        DragAndDropPolicy.DragSession dragSession = dragAndDropPolicy.mSession;
        if (dragSession != null) {
            DisplayLayout displayLayout = dragSession.displayLayout;
            Rect rect = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
            Rect rect2 = new Rect(rect);
            Rect rect3 = new Rect(rect);
            DragAndDropPolicy.DragSession dragSession2 = dragAndDropPolicy.mSession;
            VisibleTasks visibleTasks = dragSession2.mVisibleTasks;
            if (visibleTasks != null && (executableAppHolder = dragSession2.mExecutableAppHolder) != null) {
                AppResult appResult = executableAppHolder.mResult;
                if (appResult != null && appResult.isAlreadyRunningSingleInstanceTask(visibleTasks)) {
                    z = true;
                    Context context = dragAndDropPolicy.mContext;
                    if (z) {
                        DragAndDropPolicy.DragSession dragSession3 = dragAndDropPolicy.mSession;
                        if (dragSession3.dragItemSupportsSplitscreen) {
                            ExecutableAppHolder executableAppHolder2 = dragSession3.mExecutableAppHolder;
                            if (executableAppHolder2 != null) {
                                AppResult appResult2 = executableAppHolder2.mResult;
                            }
                            if (CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY) {
                            }
                            boolean z3 = false;
                            if (z3) {
                                arrayList.add(new DragAndDropPolicy.Target(0, rect3, rect2, true));
                            } else {
                                DisplayLayout displayLayout2 = dragAndDropPolicy.mSession.displayLayout;
                                boolean z4 = displayLayout2.mWidth > displayLayout2.mHeight;
                                SplitScreenController splitScreenController = dragAndDropPolicy.mSplitScreen;
                                boolean z5 = splitScreenController != null && splitScreenController.isSplitScreenVisible();
                                float dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
                                if (splitScreenController == null || !splitScreenController.isSplitScreenVisible()) {
                                    DragAndDropPolicy.DragSession dragSession4 = dragAndDropPolicy.mSession;
                                    if (!dragSession4.runningTaskSupportsSplitScreen || (!dragSession4.isDragFromRecent && (i = dragSession4.runningTaskActType) != 2 && i != 1)) {
                                        z2 = false;
                                        if (z2) {
                                            arrayList.add(new DragAndDropPolicy.Target(0, rect3, rect2));
                                        } else {
                                            ((SplitDropTargetProvider) dragAndDropPolicy.mDropTargetProviders.get((CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET && dragAndDropPolicy.supportMultiSplitDropTarget()) ? 2 : 1)).addSplitTargets(rect, z4, z5, dimensionPixelSize, arrayList);
                                            ExecutableAppHolder executableAppHolder3 = dragAndDropPolicy.mSession.mExecutableAppHolder;
                                            if (executableAppHolder3 != null) {
                                                AppResult appResult3 = executableAppHolder3.mResult;
                                                ApplicationInfo dragAppApplicationInfo = appResult3 != null ? appResult3.getDragAppApplicationInfo() : null;
                                                if (dragAppApplicationInfo != null) {
                                                    str = dragAppApplicationInfo.packageName;
                                                    if (str != null && splitScreenController != null && splitScreenController.isSplitScreenVisible()) {
                                                        it = arrayList.iterator();
                                                        while (it.hasNext()) {
                                                            DragAndDropPolicy.Target target = (DragAndDropPolicy.Target) it.next();
                                                            if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || splitScreenController.isMultiSplitScreenVisible() || !target.isMultiSplit()) {
                                                                if (target.isMultiSplit()) {
                                                                    i2 = target.convertTypeToCellStagePosition();
                                                                } else {
                                                                    int i3 = target.type;
                                                                    i2 = i3 != 1 ? i3 != 2 ? i3 != 3 ? i3 != 4 ? 0 : 64 : 32 : 16 : 8;
                                                                }
                                                                ActivityManager.RunningTaskInfo topRunningTaskInfoByPosition = splitScreenController.getTopRunningTaskInfoByPosition(i2);
                                                                if (topRunningTaskInfoByPosition != null && (componentName = topRunningTaskInfoByPosition.baseActivity) != null && str.equals(componentName.getPackageName())) {
                                                                    target.alreadyRun = true;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            str = null;
                                            if (str != null) {
                                                it = arrayList.iterator();
                                                while (it.hasNext()) {
                                                }
                                            }
                                        }
                                        Rect centerFreeformBounds = dragAndDropPolicy.getCenterFreeformBounds();
                                        Rect centerFreeformBounds2 = dragAndDropPolicy.getCenterFreeformBounds();
                                        int i4 = -context.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_hit_size);
                                        centerFreeformBounds2.inset(i4, i4);
                                        arrayList.add(new DragAndDropPolicy.Target(5, centerFreeformBounds2, centerFreeformBounds));
                                    }
                                }
                                z2 = true;
                                if (z2) {
                                }
                                Rect centerFreeformBounds3 = dragAndDropPolicy.getCenterFreeformBounds();
                                Rect centerFreeformBounds22 = dragAndDropPolicy.getCenterFreeformBounds();
                                int i42 = -context.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_hit_size);
                                centerFreeformBounds22.inset(i42, i42);
                                arrayList.add(new DragAndDropPolicy.Target(5, centerFreeformBounds22, centerFreeformBounds3));
                            }
                        } else {
                            arrayList.add(new DragAndDropPolicy.Target(0, rect3, rect2, false));
                        }
                    } else {
                        AppResult appResult4 = dragAndDropPolicy.mSession.mExecutableAppHolder.mResult;
                        ApplicationInfo dragAppApplicationInfo2 = appResult4 != null ? appResult4.getDragAppApplicationInfo() : null;
                        CharSequence loadLabel = dragAppApplicationInfo2 != null ? dragAppApplicationInfo2.loadLabel(context.getPackageManager()) : null;
                        String string = (dragAppApplicationInfo2 == null || loadLabel == null) ? context.getResources().getString(R.string.drag_and_drop_already_launch_this_app_toast) : context.getResources().getString(R.string.drag_and_drop_already_launch_app_toast, loadLabel.toString());
                        Toast toast = dragAndDropPolicy.mToast;
                        if (toast != null) {
                            toast.cancel();
                        }
                        Toast makeText = Toast.makeText(context, string, 0);
                        dragAndDropPolicy.mToast = makeText;
                        makeText.show();
                    }
                }
            }
            z = false;
            Context context2 = dragAndDropPolicy.mContext;
            if (z) {
            }
        }
        boolean z6 = !arrayList.isEmpty();
        for (int i5 = 0; i5 < arrayList.size(); i5++) {
            ProtoLog.m52v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Add target: %s", (DragAndDropPolicy.Target) arrayList.get(i5));
        }
        return z6;
    }

    public final void show() {
        this.mIsShowing = true;
        if (recomputeDropTargets()) {
            updateNavigationBarVisibility(false);
        }
        if (this.mDensityChanged) {
            this.mDensityChanged = false;
            this.mCurrentDensityDpi = getResources().getConfiguration().densityDpi;
            removeView(this.mDismissButtonView);
            this.mDismissButtonView = null;
            createDismissButtonView();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:175:0x0088, code lost:
    
        if (r12.y < r7) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:156:0x010e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0116 A[EDGE_INSN: B:19:0x0116->B:20:0x0116 BREAK  A[LOOP:0: B:6:0x003f->B:157:0x010e], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(DragEvent dragEvent) {
        DragAndDropPolicy.Target target;
        boolean z;
        boolean z2;
        Intent intent;
        AppInfo appInfo;
        Intent intent2;
        int i;
        boolean z3;
        List list;
        int i2;
        int i3;
        boolean z4;
        AppInfo makeExecutableApp;
        int x = (int) dragEvent.getX();
        int y = (int) dragEvent.getY();
        this.mDismissButtonView.updateView(new Rect(x, y, x, y));
        int i4 = 4;
        if (!this.mDismissButtonView.mIsEnterDismissButton) {
            DragAndDropPolicy dragAndDropPolicy = this.mPolicy;
            int x2 = (int) dragEvent.getX();
            int y2 = (int) dragEvent.getY();
            float f = x2;
            float f2 = y2;
            if (!dragAndDropPolicy.mDisallowHitRegion.contains(f, f2)) {
                ArrayList arrayList = dragAndDropPolicy.mTargets;
                int size = arrayList.size() - 1;
                while (size >= 0) {
                    target = (DragAndDropPolicy.Target) arrayList.get(size);
                    Rect rect = target.hitRegion;
                    if (rect != null) {
                        z3 = rect.contains(x2, y2);
                        i = x2;
                    } else if (!CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET || (list = target.polygon) == null || list.size() < i4) {
                        i = x2;
                        z3 = false;
                    } else {
                        int size2 = list.size();
                        boolean z5 = false;
                        int i5 = size2 - 1;
                        int i6 = 0;
                        while (i6 < size2) {
                            PointF pointF = (PointF) list.get(i6);
                            PointF pointF2 = (PointF) list.get(i5);
                            float f3 = pointF.y;
                            if (f3 < f2) {
                                i2 = size2;
                            } else {
                                i2 = size2;
                            }
                            if (pointF2.y >= f2 || f3 < f2) {
                                i3 = x2;
                                i5 = i6;
                                x2 = i3;
                                i6++;
                                size2 = i2;
                            }
                            float f4 = pointF.x;
                            i3 = x2;
                            if (DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF2.x, f4, (f2 - f3) / (pointF2.y - f3), f4) <= f) {
                                z5 = !z5;
                            }
                            i5 = i6;
                            x2 = i3;
                            i6++;
                            size2 = i2;
                        }
                        i = x2;
                        z3 = z5;
                    }
                    if (z3) {
                        DragAndDropPolicy.DragSession dragSession = dragAndDropPolicy.mSession;
                        ExecutableAppHolder executableAppHolder = dragSession.mExecutableAppHolder;
                        if (executableAppHolder != null) {
                            if (executableAppHolder.mResult == null) {
                                makeExecutableApp = null;
                            } else {
                                Map map = executableAppHolder.mExecutableAppMap;
                                int i7 = target.type;
                                HashMap hashMap = (HashMap) map;
                                if (hashMap.containsKey(Integer.valueOf(i7))) {
                                    makeExecutableApp = (AppInfo) ((Optional) hashMap.get(Integer.valueOf(i7))).orElse(null);
                                } else {
                                    makeExecutableApp = executableAppHolder.mResult.makeExecutableApp(executableAppHolder.mContext, i7, dragSession.mVisibleTasks);
                                    hashMap.put(Integer.valueOf(i7), Optional.ofNullable(makeExecutableApp));
                                }
                            }
                            if (makeExecutableApp == null) {
                                z4 = false;
                                if (!z4) {
                                    break;
                                }
                            }
                        }
                        z4 = true;
                        if (!z4) {
                        }
                    }
                    size--;
                    i4 = 4;
                    x2 = i;
                }
            }
        }
        target = null;
        DragAndDropPolicy.Target target2 = this.mCurrentTarget;
        if ((target2 != null || target == null) && (target2 == null || target2.equals(target))) {
            z = true;
        } else {
            ProtoLog.m52v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Current target: %s", target);
            if (target != null || this.mDismissButtonView.mIsEnterDismissButton) {
                DismissButtonView dismissButtonView = this.mDismissButtonView;
                if (!dismissButtonView.mVisible) {
                    dismissButtonView.mVisible = true;
                    dismissButtonView.startAnimation(dismissButtonView.mEnterAnimation);
                }
            } else {
                DismissButtonView dismissButtonView2 = this.mDismissButtonView;
                if (dismissButtonView2.mVisible) {
                    dismissButtonView2.mVisible = false;
                    dismissButtonView2.clearAnimation();
                    dismissButtonView2.setVisibility(4);
                }
            }
            if (target == null) {
                DropTargetView dropTargetView = this.mDropTargetView;
                if (dropTargetView.mHideAnimatorSet == null) {
                    dropTargetView.mHideAnimatorSet = new AnimatorSet();
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(dropTargetView, "alpha", 1.0f, 0.0f);
                    ofFloat.setDuration(150L);
                    ofFloat.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                    ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(dropTargetView.mText, "alpha", 1.0f, 0.0f);
                    ofFloat2.setDuration(150L);
                    ofFloat2.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                    int[] iArr = new int[2];
                    iArr[0] = CoreRune.MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR ? 125 : 80;
                    iArr[1] = 0;
                    ValueAnimator ofInt = ObjectAnimator.ofInt(iArr);
                    ofInt.setDuration(200L);
                    ofInt.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                    ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(dropTargetView.mText, "scaleX", 1.0f, 0.8f);
                    ofFloat3.setDuration(150L);
                    ofFloat3.setInterpolator(InterpolatorUtils.ONE_EASING);
                    ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(dropTargetView.mText, "scaleY", 1.0f, 0.8f);
                    ofFloat4.setDuration(150L);
                    ofFloat4.setInterpolator(InterpolatorUtils.ONE_EASING);
                    ofFloat.addListener(new Animator.AnimatorListener(dropTargetView) { // from class: com.android.wm.shell.draganddrop.DropTargetView.3
                        public C39653(DropTargetView dropTargetView2) {
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

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                        }
                    });
                    ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.draganddrop.DropTargetView.4
                        public C39664() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            DropTargetView dropTargetView2 = DropTargetView.this;
                            int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                            int i8 = DropTargetView.$r8$clinit;
                            dropTargetView2.setBlurEffect(intValue);
                        }
                    });
                    dropTargetView2.mHideAnimatorSet.playTogether(ofFloat, ofFloat2, ofFloat3, ofFloat4, ofInt);
                }
                AnimatorSet animatorSet = dropTargetView2.mShowAnimatorSet;
                if (animatorSet != null && animatorSet.isRunning()) {
                    dropTargetView2.mShowAnimatorSet.cancel();
                }
                if (dropTargetView2.mHideAnimatorSet.isRunning()) {
                    dropTargetView2.mHideAnimatorSet.cancel();
                }
                dropTargetView2.mHideAnimatorSet.start();
                dropTargetView2.mBounds.setEmpty();
            } else {
                if (this.mCurrentTarget == null) {
                    this.mDropTargetView.setVisibility(0);
                }
                DragAndDropOptions dragAndDropOptions = this.mTmpOptions;
                dragAndDropOptions.getClass();
                int i8 = target.type;
                dragAndDropOptions.mIsFreeform = i8 == 5;
                dragAndDropOptions.mIsFullscreen = i8 == 0;
                dragAndDropOptions.mIsResizable = target.isResizable;
                Rect rect2 = dragAndDropOptions.mBounds;
                Rect rect3 = target.drawRegion;
                rect2.set(rect3);
                DropTargetView dropTargetView2 = this.mDropTargetView;
                DragAndDropOptions dragAndDropOptions2 = this.mTmpOptions;
                dropTargetView2.mDropOptions = dragAndDropOptions2;
                dropTargetView2.mIsFreeform = dragAndDropOptions2.mIsFreeform;
                dropTargetView2.mBounds.set(rect3);
                if (dropTargetView2.mShowAnimatorSet == null) {
                    dropTargetView2.mShowAnimatorSet = new AnimatorSet();
                    ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(dropTargetView2, "alpha", 0.0f, 1.0f);
                    ofFloat5.setDuration(200L);
                    ofFloat5.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                    ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(dropTargetView2.mText, "alpha", 0.0f, 1.0f);
                    ofFloat6.setDuration(200L);
                    ofFloat6.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                    int[] iArr2 = new int[2];
                    iArr2[0] = 0;
                    iArr2[1] = CoreRune.MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR ? 125 : 80;
                    ValueAnimator ofInt2 = ObjectAnimator.ofInt(iArr2);
                    ofInt2.setDuration(200L);
                    ofInt2.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                    ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(dropTargetView2.mText, "scaleX", 0.8f, 1.0f);
                    ofFloat7.setDuration(400L);
                    ofFloat7.setInterpolator(InterpolatorUtils.ONE_EASING);
                    ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(dropTargetView2.mText, "scaleY", 0.8f, 1.0f);
                    ofFloat8.setDuration(400L);
                    ofFloat8.setInterpolator(InterpolatorUtils.ONE_EASING);
                    ofFloat5.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.draganddrop.DropTargetView.1
                        public C39631() {
                        }

                        @Override // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            DropTargetView dropTargetView3 = DropTargetView.this;
                            int i9 = DropTargetView.$r8$clinit;
                            dropTargetView3.updateBounds();
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
                    ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.draganddrop.DropTargetView.2
                        public C39642() {
                        }

                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            DropTargetView dropTargetView3 = DropTargetView.this;
                            int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                            int i9 = DropTargetView.$r8$clinit;
                            dropTargetView3.setBlurEffect(intValue);
                        }
                    });
                    dropTargetView2.mShowAnimatorSet.playTogether(ofFloat5, ofFloat7, ofFloat8, ofFloat6, ofInt2);
                }
                AnimatorSet animatorSet2 = dropTargetView2.mHideAnimatorSet;
                if (animatorSet2 != null && animatorSet2.isRunning()) {
                    dropTargetView2.mHideAnimatorSet.cancel();
                }
                if (dropTargetView2.mShowAnimatorSet.isRunning()) {
                    dropTargetView2.mShowAnimatorSet.cancel();
                }
                dropTargetView2.mShowAnimatorSet.start();
            }
            if (getWindowVisibility() == 0) {
                DragAndDropPolicy.Target target3 = this.mCurrentTarget;
                if ((!(target3 == null && target == null) && (target3 == null || target == null || target3.type != target.type)) && this.mFirstDropTargetShown) {
                    performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                }
            }
            if (this.mFirstDropTargetShown) {
                z = true;
            } else {
                z = true;
                this.mFirstDropTargetShown = true;
            }
            this.mCurrentTarget = target;
        }
        if (this.mAppIcon.getVisibility() != 0) {
            DragAppIcon dragAppIcon = this.mAppIcon;
            float x3 = dragEvent.getX();
            float y3 = dragEvent.getY();
            dragAppIcon.setX(x3 - dragAppIcon.mCenterX);
            dragAppIcon.setY(y3 - dragAppIcon.mCenterY);
            z2 = false;
            dragAppIcon.setVisibility(0);
            dragAppIcon.setPivotX(dragAppIcon.mCenterX);
            dragAppIcon.setPivotY(dragAppIcon.mCenterY);
            dragAppIcon.setScaleX(0.0f);
            dragAppIcon.setScaleY(0.0f);
            dragAppIcon.mScaleUpAnimX.animateToFinalPosition(1.0f);
            dragAppIcon.mScaleUpAnimY.animateToFinalPosition(1.0f);
        } else {
            z2 = false;
            DragAppIcon dragAppIcon2 = this.mAppIcon;
            float x4 = dragEvent.getX();
            float y4 = dragEvent.getY();
            dragAppIcon2.setX(x4 - dragAppIcon2.mCenterX);
            dragAppIcon2.setY(y4 - dragAppIcon2.mCenterY);
        }
        DragAndDropPolicy dragAndDropPolicy2 = this.mPolicy;
        DragAndDropPolicy.Target target4 = this.mCurrentTarget;
        int i9 = target4 != null ? target4.type : -1;
        final ExecutableAppHolder executableAppHolder2 = dragAndDropPolicy2.mSession.mExecutableAppHolder;
        if (executableAppHolder2 != null) {
            if (executableAppHolder2.mIsMimeType) {
                intent2 = null;
            } else {
                if (i9 == -1) {
                    appInfo = null;
                    intent = null;
                } else {
                    intent = null;
                    appInfo = (AppInfo) ((Optional) ((HashMap) executableAppHolder2.mExecutableAppMap).getOrDefault(Integer.valueOf(i9), Optional.empty())).orElse(null);
                }
                if (executableAppHolder2.mExecutableApp != appInfo) {
                    executableAppHolder2.mExecutableApp = appInfo;
                    synchronized (executableAppHolder2.mCallbacks) {
                        executableAppHolder2.mCallbacks.stream().forEach(new Consumer() { // from class: com.android.wm.shell.draganddrop.ExecutableAppHolder$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AppInfo appInfo2 = ExecutableAppHolder.this.mExecutableApp;
                                DropTargetLayout dropTargetLayout = (DropTargetLayout) ((IDragLayout) obj);
                                if (appInfo2 == null) {
                                    dropTargetLayout.getClass();
                                    return;
                                }
                                if (dropTargetLayout.mDragSurface != null) {
                                    dropTargetLayout.mAppIcon.setImageDrawable(appInfo2.mIcon);
                                    boolean z6 = dropTargetLayout.mAppIcon.getDrawable() != null;
                                    if (dropTargetLayout.mHasDrawable != z6) {
                                        dropTargetLayout.mHasDrawable = z6;
                                        MultiWindowManager.getInstance().notifyDragSplitAppIconHasDrawable(z6);
                                    }
                                }
                            }
                        });
                    }
                }
                intent2 = intent;
            }
            DragAndDropPolicy.DragSession dragSession2 = dragAndDropPolicy2.mSession;
            ExecutableAppHolder executableAppHolder3 = dragSession2.mExecutableAppHolder;
            if (executableAppHolder3 != null) {
                if (executableAppHolder3.mExecutableApp != null ? z : z2) {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    AppInfo appInfo2 = executableAppHolder3.mExecutableApp;
                    if (appInfo2 != null) {
                        intent2 = appInfo2.mIntent;
                    }
                    dragSession2.dragData = new Intent(intent2).putExtra("android.app.extra.OPTIONS", makeBasic.toBundle()).putExtra("android.intent.extra.ACTIVITY_OPTIONS", makeBasic.toBundle());
                    AppInfo appInfo3 = executableAppHolder3.mExecutableApp;
                    if (appInfo3 == null || !appInfo3.mIsDropResolver) {
                        z = z2;
                    }
                    dragSession2.isDragDataDropResolver = z;
                    if (CoreRune.SAFE_DEBUG) {
                        Slog.d("DragAndDropPolicy", "update: dragData=" + dragSession2.dragData);
                    }
                }
            }
        }
    }

    public final void updateNavigationBarVisibility(boolean z) {
        StringBuilder m49m = RowView$$ExternalSyntheticOutline0.m49m("updateNavigationBarVisibility : ", z, ", caller=");
        m49m.append(Debug.getCallers(5));
        android.util.secutil.Slog.d("DropTargetLayout", m49m.toString());
        if (z) {
            this.mStatusBarManager.disable(0);
            return;
        }
        if (CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET && !this.mPolicy.isInSubDisplay()) {
            if (Settings.System.getInt(getContext().getContentResolver(), "task_bar", 1) == 1) {
                android.util.secutil.Slog.d("DropTargetLayout", "Failed to disalbe navibar, Taskbar is shown");
                return;
            }
        }
        this.mStatusBarManager.disable(23068672);
    }
}
