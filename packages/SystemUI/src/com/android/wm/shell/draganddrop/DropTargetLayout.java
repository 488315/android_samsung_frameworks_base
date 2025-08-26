package com.android.wm.shell.draganddrop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.os.Handler;
import android.provider.Settings;
import android.util.secutil.Slog;
import android.view.DragEvent;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.common.DismissView;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.MultiInstanceHelper;
import com.android.wm.shell.draganddrop.SplitDragPolicy;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class DropTargetLayout extends FrameLayout implements DragLayoutProvider, DragZoneAnimator {
    public final DragAppIcon mAppIcon;
    public int mCurrentDensityDpi;
    public SplitDragPolicy.Target mCurrentTarget;
    public boolean mDensityChanged;
    public DismissView mDismissView;
    public SurfaceControl mDragSurface;
    public final DropTargetView mDropTargetView;
    public final Handler mHandler;
    public boolean mHasDrawable;
    public boolean mHasDropped;
    public boolean mIsHideDragSourceTask;
    public boolean mIsIntentSenderDropTarget;
    public boolean mIsShowing;
    public final SplitDragPolicy mPolicy;
    public final StatusBarManager mStatusBarManager;
    public final DragAndDropOptions mTmpOptions;
    public final SurfaceControl.Transaction mTransaction;

    public DropTargetLayout(Context context, SplitScreenController splitScreenController, SurfaceControl.Transaction transaction, Transitions transitions, MultiInstanceHelper multiInstanceHelper) {
        super(context);
        this.mTmpOptions = new DragAndDropOptions();
        this.mCurrentTarget = null;
        this.mHandler = new Handler();
        this.mDensityChanged = false;
        this.mIsHideDragSourceTask = false;
        this.mPolicy = new SplitDragPolicy(context, splitScreenController, this, transitions, multiInstanceHelper);
        FrameLayout.inflate(context, R.layout.drop_target_layout, this);
        this.mDropTargetView = (DropTargetView) findViewById(R.id.drop_target);
        createDismissView();
        this.mTransaction = transaction;
        this.mAppIcon = (DragAppIcon) findViewById(R.id.drag_app_icon);
        this.mStatusBarManager = (StatusBarManager) context.getSystemService("statusbar");
        this.mCurrentDensityDpi = getResources().getConfiguration().densityDpi;
    }

    public final void createDismissView() {
        if (this.mIsIntentSenderDropTarget) {
            return;
        }
        DismissView dismissView = (DismissView) LayoutInflater.from(getContext()).inflate(R.layout.dismiss_view, (ViewGroup) this, false);
        this.mDismissView = dismissView;
        dismissView.setDismissType(1);
        DismissView dismissView2 = this.mDismissView;
        dismissView2.mFocusChangeHapticDisabled = true;
        addView(dismissView2);
    }

    public final void hide(Runnable runnable, boolean z) {
        SurfaceControl surfaceControl;
        this.mIsShowing = false;
        if (!this.mIsIntentSenderDropTarget) {
            this.mDismissView.hide(null);
        }
        updateNavigationBarVisibility(true);
        if (this.mCurrentTarget != null) {
            this.mDropTargetView.hide();
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
            this.mHandler.postDelayed(runnable, 300L);
        }
        this.mCurrentTarget = null;
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) throws Resources.NotFoundException {
        recomputeDropTargets();
        return super.onApplyWindowInsets(windowInsets);
    }

    public final void onConfigChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mDensityChanged = this.mCurrentDensityDpi != configuration.densityDpi;
    }

    public final void prepare(DragSession dragSession, InstanceId instanceId, SurfaceControl surfaceControl, Rect rect, boolean z) {
        SplitDragPolicy splitDragPolicy = this.mPolicy;
        splitDragPolicy.mLoggerSessionId = instanceId;
        splitDragPolicy.mSession = dragSession;
        Intent intent = dragSession.appData;
        RectF rectF = intent != null ? (RectF) intent.getExtra("DISALLOW_HIT_REGION") : null;
        if (rectF == null) {
            splitDragPolicy.mDisallowHitRegion.setEmpty();
        } else {
            splitDragPolicy.mDisallowHitRegion.set(rectF);
        }
        splitDragPolicy.mIsIntentSenderDropTarget = z;
        this.mHasDropped = false;
        this.mCurrentTarget = null;
        this.mHasDrawable = false;
        this.mDragSurface = surfaceControl;
        if (CoreRune.MW_DND_SA_LOGGING) {
            SplitDragPolicy splitDragPolicy2 = this.mPolicy;
            String str = dragSession.mExecutableAppHolder.mCallingPackageName;
            splitDragPolicy2.getClass();
        }
        this.mIsHideDragSourceTask = dragSession.hideDragSourceTaskId != -1;
        this.mIsIntentSenderDropTarget = z;
        DismissView dismissView = this.mDismissView;
        if (rect != null) {
            dismissView.mHiddenDropTargetArea.set(rect);
        } else {
            dismissView.getClass();
        }
        this.mDismissView.updateMarginBottom();
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00c4  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0181  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean recomputeDropTargets() throws Resources.NotFoundException {
        int displayId;
        int i;
        ArrayList arrayList;
        int i2;
        ActivityManager.RunningTaskInfo topRunningTaskInfoByPosition;
        ComponentName componentName;
        AppResult appResult;
        ExecutableAppHolder executableAppHolder;
        AppResult appResult2;
        PendingIntent pendingIntent;
        if (!this.mIsShowing) {
            return false;
        }
        SplitDragPolicy splitDragPolicy = this.mPolicy;
        splitDragPolicy.mTargets.clear();
        DragSession dragSession = splitDragPolicy.mSession;
        if (dragSession == null) {
            arrayList = splitDragPolicy.mTargets;
        } else {
            DisplayLayout displayLayout = dragSession.displayLayout;
            Rect rect = new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
            Rect rect2 = new Rect(rect);
            Rect rect3 = new Rect(rect);
            DragSession dragSession2 = splitDragPolicy.mSession;
            String str = null;
            if (dragSession2.mExecutableAppHolder != null) {
                MultiInstanceHelper multiInstanceHelper = splitDragPolicy.mMultiInstanceHelper;
                if (multiInstanceHelper == null || (pendingIntent = dragSession2.launchableIntent) == null) {
                    AppResult appResult3 = splitDragPolicy.mSession.mExecutableAppHolder.mResult;
                    ActivityInfo dragAppActivityInfo = appResult3 != null ? appResult3.getDragAppActivityInfo() : null;
                    if (dragAppActivityInfo == null || dragAppActivityInfo.isArchived) {
                        String string = splitDragPolicy.mContext.getResources().getString(R.string.multi_window_dismiss_split);
                        Toast toast = splitDragPolicy.mToast;
                        if (toast != null) {
                            toast.cancel();
                        }
                        Toast toastMakeText = Toast.makeText(splitDragPolicy.mContext, string, 0);
                        splitDragPolicy.mToast = toastMakeText;
                        toastMakeText.show();
                    } else {
                        DragSession dragSession3 = splitDragPolicy.mSession;
                        VisibleTasks visibleTasks = dragSession3.mVisibleTasks;
                        if (visibleTasks != null && (executableAppHolder = dragSession3.mExecutableAppHolder) != null && (appResult2 = executableAppHolder.mResult) != null && appResult2.isAlreadyRunningSingleInstanceTask(visibleTasks)) {
                            CharSequence charSequenceLoadLabel = dragAppActivityInfo.loadLabel(splitDragPolicy.mContext.getPackageManager());
                            String string2 = charSequenceLoadLabel == null ? splitDragPolicy.mContext.getResources().getString(R.string.drag_and_drop_already_launch_this_app_toast) : splitDragPolicy.mContext.getResources().getString(R.string.drag_and_drop_already_launch_app_toast, charSequenceLoadLabel.toString());
                            Toast toast2 = splitDragPolicy.mToast;
                            if (toast2 != null) {
                                toast2.cancel();
                            }
                            Toast toastMakeText2 = Toast.makeText(splitDragPolicy.mContext, string2, 0);
                            splitDragPolicy.mToast = toastMakeText2;
                            toastMakeText2.show();
                        }
                        displayId = splitDragPolicy.mContext.getDisplay().getDisplayId();
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(displayId)) {
                            DragSession dragSession4 = splitDragPolicy.mSession;
                            if (dragSession4.dragItemSupportsSplitscreen) {
                                ExecutableAppHolder executableAppHolder2 = dragSession4.mExecutableAppHolder;
                                if ((executableAppHolder2 == null || (appResult = executableAppHolder2.mResult) == null || !appResult.hasResolveInfoInFullscreenOnly(dragSession4.mVisibleTasks)) && !(CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY && splitDragPolicy.mContext.getResources().getConfiguration().semDisplayDeviceType == 5)) {
                                    boolean zIsLandscape = splitDragPolicy.mSession.displayLayout.isLandscape();
                                    SplitScreenController splitScreenController = splitDragPolicy.mSplitScreen;
                                    boolean z = splitScreenController != null && splitScreenController.isSplitScreenVisible();
                                    float dimensionPixelSize = splitDragPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
                                    if (splitScreenController == null || !splitScreenController.isSplitScreenVisible()) {
                                        splitDragPolicy.mSession.getClass();
                                        DragSession dragSession5 = splitDragPolicy.mSession;
                                        if (dragSession5.runningTaskSupportsSplitScreen && (dragSession5.isDragFromRecent || (i = dragSession5.runningTaskActType) == 2 || i == 1)) {
                                            ((SplitDropTargetProvider) splitDragPolicy.mDropTargetProviders.get((CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET && splitDragPolicy.supportMultiSplitDropTarget()) ? 2 : 1)).addSplitTargets(rect, zIsLandscape, z, dimensionPixelSize, splitDragPolicy.mTargets);
                                            ExecutableAppHolder executableAppHolder3 = splitDragPolicy.mSession.mExecutableAppHolder;
                                            if (executableAppHolder3 != null) {
                                                AppResult appResult4 = executableAppHolder3.mResult;
                                                ActivityInfo dragAppActivityInfo2 = appResult4 != null ? appResult4.getDragAppActivityInfo() : null;
                                                if (dragAppActivityInfo2 != null) {
                                                    str = dragAppActivityInfo2.packageName;
                                                }
                                            }
                                            if (str != null && splitScreenController != null && splitScreenController.isSplitScreenVisible()) {
                                                ArrayList arrayList2 = splitDragPolicy.mTargets;
                                                int size = arrayList2.size();
                                                int i3 = 0;
                                                while (i3 < size) {
                                                    Object obj = arrayList2.get(i3);
                                                    i3++;
                                                    SplitDragPolicy.Target target = (SplitDragPolicy.Target) obj;
                                                    if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || splitScreenController.isMultiSplitScreenVisible() || !target.isMultiSplit()) {
                                                        boolean zIsMultiSplit = target.isMultiSplit();
                                                        int i4 = target.type;
                                                        if (zIsMultiSplit) {
                                                            i2 = 24;
                                                            switch (i4) {
                                                                case 6:
                                                                case 10:
                                                                case 11:
                                                                    break;
                                                                case 7:
                                                                    i2 = 72;
                                                                    break;
                                                                case 8:
                                                                    i2 = 48;
                                                                    break;
                                                                case 9:
                                                                case 12:
                                                                case 13:
                                                                    i2 = 96;
                                                                    break;
                                                                default:
                                                                    i2 = 0;
                                                                    break;
                                                            }
                                                            topRunningTaskInfoByPosition = splitScreenController.getTopRunningTaskInfoByPosition(i2);
                                                            if (topRunningTaskInfoByPosition == null && (componentName = topRunningTaskInfoByPosition.baseActivity) != null && str.equals(componentName.getPackageName())) {
                                                                target.alreadyRun = true;
                                                            }
                                                        } else {
                                                            if (i4 == 1) {
                                                                i2 = 8;
                                                            } else if (i4 == 2) {
                                                                i2 = 16;
                                                            } else if (i4 == 3) {
                                                                i2 = 32;
                                                            } else if (i4 == 4) {
                                                                i2 = 64;
                                                            }
                                                            topRunningTaskInfoByPosition = splitScreenController.getTopRunningTaskInfoByPosition(i2);
                                                            if (topRunningTaskInfoByPosition == null) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            if (splitDragPolicy.mIsIntentSenderDropTarget) {
                                                splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(0, IntentSenderDropTargetController.sFullscreenHitRegion, rect2, -1, true));
                                            }
                                        } else {
                                            splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(0, rect3, rect2, -1));
                                        }
                                        Rect centerFreeformBounds = splitDragPolicy.getCenterFreeformBounds();
                                        Rect centerFreeformBounds2 = splitDragPolicy.getCenterFreeformBounds();
                                        int i5 = -splitDragPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_hit_size);
                                        centerFreeformBounds2.inset(i5, i5);
                                        splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(5, centerFreeformBounds2, centerFreeformBounds, -1));
                                        arrayList = splitDragPolicy.mTargets;
                                    }
                                } else {
                                    splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(0, rect3, rect2, -1, true));
                                    arrayList = splitDragPolicy.mTargets;
                                }
                            } else {
                                splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(0, rect3, rect2, -1, false));
                                arrayList = splitDragPolicy.mTargets;
                            }
                        } else {
                            splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(5, rect, new Rect(), -1));
                            arrayList = splitDragPolicy.mTargets;
                        }
                    }
                    arrayList = splitDragPolicy.mTargets;
                } else {
                    Intent intent = pendingIntent.getIntent();
                    int i6 = splitDragPolicy.mSession.mExecutableAppHolder.mCallingUserId;
                    if (intent != null && multiInstanceHelper.supportsMultiInstanceSplit(i6, intent.getComponent())) {
                        displayId = splitDragPolicy.mContext.getDisplay().getDisplayId();
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(displayId)) {
                        }
                    }
                }
            }
        }
        boolean z2 = !arrayList.isEmpty();
        for (int i7 = 0; i7 < arrayList.size(); i7++) {
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Add target: %s", new Object[]{(SplitDragPolicy.Target) arrayList.get(i7)});
        }
        return z2;
    }

    public final void show() {
        this.mIsShowing = true;
        if (recomputeDropTargets()) {
            updateNavigationBarVisibility(false);
        }
        if (!this.mIsIntentSenderDropTarget && this.mDensityChanged) {
            this.mDensityChanged = false;
            this.mCurrentDensityDpi = getResources().getConfiguration().densityDpi;
            removeView(this.mDismissView);
            this.mDismissView = null;
            createDismissView();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0135 A[EDGE_INSN: B:188:0x0135->B:61:0x0135 BREAK  A[LOOP:0: B:15:0x0051->B:59:0x012d], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x012d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b1 A[PHI: r20
      0x00b1: PHI (r20v6 boolean) = (r20v4 boolean), (r20v7 boolean) binds: [B:36:0x00af, B:30:0x00a2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0196  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update(DragEvent dragEvent) {
        SplitDragPolicy.Target target;
        boolean z;
        int i;
        boolean zContains;
        List list;
        boolean z2;
        boolean z3;
        AppInfo appInfoMakeExecutableApp;
        boolean z4;
        Intent intent;
        AppInfo appInfo;
        boolean z5;
        boolean z6 = true;
        if (!this.mIsIntentSenderDropTarget) {
            int x = (int) dragEvent.getX();
            int y = (int) dragEvent.getY();
            this.mDismissView.updateView(new Rect(x, y, x, y));
        }
        int i2 = 4;
        if (this.mIsIntentSenderDropTarget || !this.mDismissView.mIsEnterDismissButton) {
            SplitDragPolicy splitDragPolicy = this.mPolicy;
            int x2 = (int) dragEvent.getX();
            int y2 = (int) dragEvent.getY();
            float f = x2;
            float f2 = y2;
            if (splitDragPolicy.mDisallowHitRegion.contains(f, f2)) {
                z = z6;
                i = -1;
                target = null;
            } else {
                int size = splitDragPolicy.mTargets.size() - 1;
                while (size >= 0) {
                    target = (SplitDragPolicy.Target) splitDragPolicy.mTargets.get(size);
                    Rect rect = target.hitRegion;
                    if (rect != null) {
                        zContains = rect.contains(x2, y2);
                        z = z6;
                        i = -1;
                    } else if (!CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET || (list = target.polygon) == null || list.size() < i2) {
                        z = z6;
                        i = -1;
                        zContains = false;
                    } else {
                        int size2 = target.polygon.size();
                        int i3 = size2 - 1;
                        int i4 = 0;
                        boolean z7 = false;
                        i = -1;
                        while (i4 < size2) {
                            PointF pointF = (PointF) target.polygon.get(i4);
                            PointF pointF2 = (PointF) target.polygon.get(i3);
                            float f3 = pointF.y;
                            if (f3 < f2) {
                                z2 = z6;
                                if (pointF2.y >= f2) {
                                    float f4 = pointF.x;
                                    if (DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF2.x, f4, (f2 - f3) / (pointF2.y - f3), f4) <= f) {
                                        z7 = !z7;
                                    }
                                }
                                i3 = i4;
                                i4++;
                                z6 = z2;
                            } else {
                                z2 = z6;
                            }
                            if (pointF2.y >= f2 || f3 < f2) {
                            }
                            i3 = i4;
                            i4++;
                            z6 = z2;
                        }
                        z = z6;
                        zContains = z7;
                    }
                    if (zContains) {
                        DragSession dragSession = splitDragPolicy.mSession;
                        ExecutableAppHolder executableAppHolder = dragSession.mExecutableAppHolder;
                        if (executableAppHolder != null) {
                            if (executableAppHolder.mResult == null) {
                                appInfoMakeExecutableApp = null;
                            } else {
                                Map map = executableAppHolder.mExecutableAppMap;
                                int i5 = target.type;
                                if (((HashMap) map).containsKey(Integer.valueOf(i5))) {
                                    appInfoMakeExecutableApp = (AppInfo) ((Optional) ((HashMap) executableAppHolder.mExecutableAppMap).get(Integer.valueOf(i5))).orElse(null);
                                } else {
                                    appInfoMakeExecutableApp = executableAppHolder.mResult.makeExecutableApp(executableAppHolder.mContext, i5, dragSession.mVisibleTasks);
                                    ((HashMap) executableAppHolder.mExecutableAppMap).put(Integer.valueOf(i5), Optional.ofNullable(appInfoMakeExecutableApp));
                                }
                            }
                            if (appInfoMakeExecutableApp == null) {
                                z3 = false;
                            }
                            if (!z3) {
                                break;
                            }
                        }
                        z3 = z;
                        if (!z3) {
                        }
                    }
                    size--;
                    z6 = z;
                    i2 = 4;
                }
                z = z6;
                i = -1;
                target = null;
            }
        } else {
            z = true;
            target = null;
            i = -1;
        }
        SplitDragPolicy.Target target2 = this.mCurrentTarget;
        if ((target2 == null && target != null) || (target2 != null && !target2.equals(target))) {
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Current target: %s", new Object[]{target});
            if (target != null) {
                z5 = z;
                if (!this.mIsIntentSenderDropTarget) {
                    if (z5) {
                        DismissView dismissView = this.mDismissView;
                        if (!dismissView.mVisible) {
                            dismissView.mVisible = z;
                            dismissView.startAnimation(dismissView.mEnterAnimation);
                        }
                    } else {
                        DismissView dismissView2 = this.mDismissView;
                        if (dismissView2.mVisible) {
                            dismissView2.mVisible = false;
                            dismissView2.clearAnimation();
                            dismissView2.setVisibility(4);
                        }
                    }
                }
                if (target != null) {
                    this.mDropTargetView.hide();
                    z = true;
                } else {
                    if (this.mCurrentTarget == null) {
                        this.mDropTargetView.setVisibility(0);
                    }
                    DragAndDropOptions dragAndDropOptions = this.mTmpOptions;
                    dragAndDropOptions.getClass();
                    int i6 = target.type;
                    dragAndDropOptions.mIsFreeform = i6 == 5;
                    dragAndDropOptions.mIsFullscreen = i6 == 0;
                    dragAndDropOptions.mIsResizable = target.isResizable;
                    dragAndDropOptions.mBounds.set(target.drawRegion);
                    DropTargetView dropTargetView = this.mDropTargetView;
                    DragAndDropOptions dragAndDropOptions2 = this.mTmpOptions;
                    dropTargetView.mDropOptions = dragAndDropOptions2;
                    dropTargetView.mIsFreeform = dragAndDropOptions2.mIsFreeform;
                    Bitmap bitmap = dropTargetView.mCapture;
                    if (bitmap != null) {
                        bitmap.recycle();
                        dropTargetView.mCapture = null;
                    }
                    DropTargetView dropTargetView2 = this.mDropTargetView;
                    dropTargetView2.mBounds.set(target.drawRegion);
                    if (dropTargetView2.mShowAnimatorSet == null) {
                        dropTargetView2.mShowAnimatorSet = new AnimatorSet();
                        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(dropTargetView2, "alpha", 0.0f, 1.0f);
                        objectAnimatorOfFloat.setDuration(200L);
                        PathInterpolator pathInterpolator = InterpolatorUtils.SINE_OUT_60;
                        objectAnimatorOfFloat.setInterpolator(pathInterpolator);
                        ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(dropTargetView2.mText, "alpha", 0.0f, 1.0f);
                        objectAnimatorOfFloat2.setDuration(200L);
                        objectAnimatorOfFloat2.setInterpolator(pathInterpolator);
                        ValueAnimator valueAnimatorOfInt = ObjectAnimator.ofInt(0, CoreRune.MW_SUPPORT_DRAG_AND_DROP_REAL_TIME_BLUR ? 125 : 80);
                        valueAnimatorOfInt.setDuration(200L);
                        valueAnimatorOfInt.setInterpolator(pathInterpolator);
                        ObjectAnimator objectAnimatorOfFloat3 = ObjectAnimator.ofFloat(dropTargetView2.mText, "scaleX", 0.8f, 1.0f);
                        objectAnimatorOfFloat3.setDuration(400L);
                        PathInterpolator pathInterpolator2 = InterpolatorUtils.ONE_EASING;
                        objectAnimatorOfFloat3.setInterpolator(pathInterpolator2);
                        ObjectAnimator objectAnimatorOfFloat4 = ObjectAnimator.ofFloat(dropTargetView2.mText, "scaleY", 0.8f, 1.0f);
                        objectAnimatorOfFloat4.setDuration(400L);
                        objectAnimatorOfFloat4.setInterpolator(pathInterpolator2);
                        objectAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.draganddrop.DropTargetView.1
                            public AnonymousClass1() {
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationStart(Animator animator) {
                                DropTargetView dropTargetView3 = DropTargetView.this;
                                int i7 = DropTargetView.$r8$clinit;
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
                        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.draganddrop.DropTargetView.2
                            public AnonymousClass2() {
                            }

                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                DropTargetView dropTargetView3 = DropTargetView.this;
                                int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                                int i7 = DropTargetView.$r8$clinit;
                                dropTargetView3.setBlurEffect(iIntValue);
                            }
                        });
                        z = true;
                        dropTargetView2.mShowAnimatorSet.playTogether(objectAnimatorOfFloat, objectAnimatorOfFloat3, objectAnimatorOfFloat4, objectAnimatorOfFloat2, valueAnimatorOfInt);
                    } else {
                        z = true;
                    }
                    AnimatorSet animatorSet = dropTargetView2.mHideAnimatorSet;
                    if (animatorSet != null && animatorSet.isRunning()) {
                        dropTargetView2.mHideAnimatorSet.cancel();
                    }
                    if (dropTargetView2.mShowAnimatorSet.isRunning()) {
                        dropTargetView2.mShowAnimatorSet.cancel();
                    }
                    dropTargetView2.mShowAnimatorSet.start();
                }
                if (getWindowVisibility() == 0) {
                    SplitDragPolicy.Target target3 = this.mCurrentTarget;
                    if ((!(target3 == null && target == null) && (target3 == null || target == null || target3.type != target.type)) ? z : false) {
                        performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(41));
                    }
                }
                this.mCurrentTarget = target;
            } else {
                if (!(this.mIsIntentSenderDropTarget ? false : this.mDismissView.mIsEnterDismissButton)) {
                    z5 = false;
                }
                if (!this.mIsIntentSenderDropTarget) {
                }
                if (target != null) {
                }
                if (getWindowVisibility() == 0) {
                }
                this.mCurrentTarget = target;
            }
        }
        if (this.mIsIntentSenderDropTarget) {
            return;
        }
        if (this.mAppIcon.getVisibility() != 0) {
            DragAppIcon dragAppIcon = this.mAppIcon;
            float x3 = dragEvent.getX();
            float y3 = dragEvent.getY();
            dragAppIcon.setX(x3 - dragAppIcon.mCenterX);
            dragAppIcon.setY(y3 - dragAppIcon.mCenterY);
            z4 = false;
            dragAppIcon.setVisibility(0);
            dragAppIcon.setPivotX(dragAppIcon.mCenterX);
            dragAppIcon.setPivotY(dragAppIcon.mCenterY);
            dragAppIcon.setScaleX(0.0f);
            dragAppIcon.setScaleY(0.0f);
            dragAppIcon.mScaleUpAnimX.animateToFinalPosition(1.0f);
            dragAppIcon.mScaleUpAnimY.animateToFinalPosition(1.0f);
        } else {
            z4 = false;
            DragAppIcon dragAppIcon2 = this.mAppIcon;
            float x4 = dragEvent.getX();
            float y4 = dragEvent.getY();
            dragAppIcon2.setX(x4 - dragAppIcon2.mCenterX);
            dragAppIcon2.setY(y4 - dragAppIcon2.mCenterY);
        }
        SplitDragPolicy splitDragPolicy2 = this.mPolicy;
        SplitDragPolicy.Target target4 = this.mCurrentTarget;
        int i7 = target4 != null ? target4.type : i;
        final ExecutableAppHolder executableAppHolder2 = splitDragPolicy2.mSession.mExecutableAppHolder;
        if (executableAppHolder2 != null) {
            if (executableAppHolder2.mIsMimeType) {
                intent = null;
            } else {
                if (i7 == i) {
                    appInfo = null;
                    intent = null;
                } else {
                    intent = null;
                    appInfo = (AppInfo) ((Optional) ((HashMap) executableAppHolder2.mExecutableAppMap).getOrDefault(Integer.valueOf(i7), Optional.empty())).orElse(null);
                }
                if (executableAppHolder2.mExecutableApp != appInfo) {
                    executableAppHolder2.mExecutableApp = appInfo;
                    synchronized (executableAppHolder2.mCallbacks) {
                        executableAppHolder2.mCallbacks.stream().forEach(new Consumer() { // from class: com.android.wm.shell.draganddrop.ExecutableAppHolder$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                AppInfo appInfo2 = executableAppHolder2.mExecutableApp;
                                DropTargetLayout dropTargetLayout = (DropTargetLayout) ((DragLayoutProvider) obj);
                                if (appInfo2 == null) {
                                    dropTargetLayout.getClass();
                                    return;
                                }
                                if (dropTargetLayout.mDragSurface != null) {
                                    dropTargetLayout.mAppIcon.setImageDrawable(appInfo2.mIcon);
                                    boolean z8 = dropTargetLayout.mAppIcon.getDrawable() != null;
                                    if (dropTargetLayout.mHasDrawable != z8) {
                                        dropTargetLayout.mHasDrawable = z8;
                                        MultiWindowManager.getInstance().notifyDragSplitAppIconHasDrawable(z8);
                                    }
                                }
                            }
                        });
                    }
                }
            }
            DragSession dragSession2 = splitDragPolicy2.mSession;
            ExecutableAppHolder executableAppHolder3 = dragSession2.mExecutableAppHolder;
            if (executableAppHolder3 != null) {
                if (executableAppHolder3.mExecutableApp != null ? z : z4) {
                    ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                    AppInfo appInfo2 = executableAppHolder3.mExecutableApp;
                    dragSession2.appData = new Intent(appInfo2 != null ? appInfo2.mIntent : intent).putExtra("android.app.extra.OPTIONS", activityOptionsMakeBasic.toBundle()).putExtra("android.intent.extra.ACTIVITY_OPTIONS", activityOptionsMakeBasic.toBundle());
                    AppInfo appInfo3 = executableAppHolder3.mExecutableApp;
                    dragSession2.isDragDataDropResolver = (appInfo3 == null || !appInfo3.mIsDropResolver) ? z4 : z;
                }
            }
        }
    }

    public final void updateNavigationBarVisibility(boolean z) {
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("updateNavigationBarVisibility : ", ", caller=", z);
        sbM.append(Debug.getCallers(5));
        Slog.d("DropTargetLayout", sbM.toString());
        if (z) {
            this.mStatusBarManager.disable(0);
        } else if (CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET && !this.mPolicy.isInSubDisplay() && Settings.Global.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_TASK_BAR, 1) == 1) {
            Slog.d("DropTargetLayout", "Failed to disable navibar, Taskbar is shown");
        } else {
            this.mStatusBarManager.disable(23068672);
        }
    }
}
