package com.android.wm.shell.draganddrop;

import android.app.ActivityManager;
import android.app.StatusBarManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Debug;
import android.os.Handler;
import android.provider.Settings;
import android.util.secutil.Slog;
import android.view.LayoutInflater;
import android.view.SurfaceControl;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.logging.InstanceId;
import com.android.internal.protolog.ProtoLog;
import com.android.systemui.R;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.common.DismissView;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.draganddrop.SplitDragPolicy;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public DropTargetLayout(Context context, SplitScreenController splitScreenController, SurfaceControl.Transaction transaction, Transitions transitions) {
        super(context);
        this.mTmpOptions = new DragAndDropOptions();
        this.mCurrentTarget = null;
        this.mHandler = new Handler();
        this.mDensityChanged = false;
        this.mIsHideDragSourceTask = false;
        this.mPolicy = new SplitDragPolicy(context, splitScreenController, this, transitions);
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
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
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

    public final boolean recomputeDropTargets() {
        ArrayList arrayList;
        int i;
        int i2;
        ActivityManager.RunningTaskInfo topRunningTaskInfoByPosition;
        ComponentName componentName;
        AppResult appResult;
        ExecutableAppHolder executableAppHolder;
        AppResult appResult2;
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
            AppResult appResult3 = splitDragPolicy.mSession.mExecutableAppHolder.mResult;
            String str = null;
            ActivityInfo dragAppActivityInfo = appResult3 != null ? appResult3.getDragAppActivityInfo() : null;
            if (dragAppActivityInfo == null || dragAppActivityInfo.isArchived) {
                String string = splitDragPolicy.mContext.getResources().getString(R.string.multi_window_dismiss_split);
                Toast toast = splitDragPolicy.mToast;
                if (toast != null) {
                    toast.cancel();
                }
                Toast makeText = Toast.makeText(splitDragPolicy.mContext, string, 0);
                splitDragPolicy.mToast = makeText;
                makeText.show();
            } else {
                DragSession dragSession2 = splitDragPolicy.mSession;
                VisibleTasks visibleTasks = dragSession2.mVisibleTasks;
                if (visibleTasks == null || (executableAppHolder = dragSession2.mExecutableAppHolder) == null || (appResult2 = executableAppHolder.mResult) == null || !appResult2.isAlreadyRunningSingleInstanceTask(visibleTasks)) {
                    int displayId = splitDragPolicy.mContext.getDisplay().getDisplayId();
                    DesktopStateImpl.Companion.getClass();
                    if (DesktopStateImpl.Companion.inDesktopWindowing(displayId)) {
                        splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(5, rect, new Rect(), -1));
                        arrayList = splitDragPolicy.mTargets;
                    } else {
                        DragSession dragSession3 = splitDragPolicy.mSession;
                        if (dragSession3.dragItemSupportsSplitscreen) {
                            ExecutableAppHolder executableAppHolder2 = dragSession3.mExecutableAppHolder;
                            if ((executableAppHolder2 == null || (appResult = executableAppHolder2.mResult) == null || !appResult.hasResolveInfoInFullscreenOnly(dragSession3.mVisibleTasks)) && !(CoreRune.MW_MULTI_SPLIT_NOT_SUPPORT_FOR_COVER_DISPLAY && splitDragPolicy.mContext.getResources().getConfiguration().semDisplayDeviceType == 5)) {
                                boolean isLandscape = splitDragPolicy.mSession.displayLayout.isLandscape();
                                SplitScreenController splitScreenController = splitDragPolicy.mSplitScreen;
                                boolean z = splitScreenController != null && splitScreenController.isSplitScreenVisible();
                                float dimensionPixelSize = splitDragPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
                                if (splitScreenController == null || !splitScreenController.isSplitScreenVisible()) {
                                    splitDragPolicy.mSession.getClass();
                                    DragSession dragSession4 = splitDragPolicy.mSession;
                                    if (!dragSession4.runningTaskSupportsSplitScreen || (!dragSession4.isDragFromRecent && (i = dragSession4.runningTaskActType) != 2 && i != 1)) {
                                        splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(0, rect3, rect2, -1));
                                        Rect centerFreeformBounds = splitDragPolicy.getCenterFreeformBounds();
                                        Rect centerFreeformBounds2 = splitDragPolicy.getCenterFreeformBounds();
                                        int i3 = -splitDragPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_hit_size);
                                        centerFreeformBounds2.inset(i3, i3);
                                        splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(5, centerFreeformBounds2, centerFreeformBounds, -1));
                                        arrayList = splitDragPolicy.mTargets;
                                    }
                                }
                                ((SplitDropTargetProvider) splitDragPolicy.mDropTargetProviders.get((CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET && splitDragPolicy.supportMultiSplitDropTarget()) ? 2 : 1)).addSplitTargets(rect, isLandscape, z, dimensionPixelSize, splitDragPolicy.mTargets);
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
                                    int i4 = 0;
                                    while (i4 < size) {
                                        Object obj = arrayList2.get(i4);
                                        i4++;
                                        SplitDragPolicy.Target target = (SplitDragPolicy.Target) obj;
                                        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || splitScreenController.isMultiSplitScreenVisible() || !target.isMultiSplit()) {
                                            boolean isMultiSplit = target.isMultiSplit();
                                            int i5 = target.type;
                                            if (isMultiSplit) {
                                                i2 = 24;
                                                switch (i5) {
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
                                                if (topRunningTaskInfoByPosition != null && (componentName = topRunningTaskInfoByPosition.baseActivity) != null && str.equals(componentName.getPackageName())) {
                                                    target.alreadyRun = true;
                                                }
                                            } else {
                                                if (i5 == 1) {
                                                    i2 = 8;
                                                } else if (i5 == 2) {
                                                    i2 = 16;
                                                } else if (i5 != 3) {
                                                    if (i5 == 4) {
                                                        i2 = 64;
                                                    }
                                                    i2 = 0;
                                                } else {
                                                    i2 = 32;
                                                }
                                                topRunningTaskInfoByPosition = splitScreenController.getTopRunningTaskInfoByPosition(i2);
                                                if (topRunningTaskInfoByPosition != null) {
                                                    target.alreadyRun = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (splitDragPolicy.mIsIntentSenderDropTarget) {
                                    splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(0, IntentSenderDropTargetController.sFullscreenHitRegion, rect2, -1, true));
                                }
                                Rect centerFreeformBounds3 = splitDragPolicy.getCenterFreeformBounds();
                                Rect centerFreeformBounds22 = splitDragPolicy.getCenterFreeformBounds();
                                int i32 = -splitDragPolicy.mContext.getResources().getDimensionPixelSize(R.dimen.dnd_drop_freeform_hit_size);
                                centerFreeformBounds22.inset(i32, i32);
                                splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(5, centerFreeformBounds22, centerFreeformBounds3, -1));
                                arrayList = splitDragPolicy.mTargets;
                            } else {
                                splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(0, rect3, rect2, -1, true));
                                arrayList = splitDragPolicy.mTargets;
                            }
                        } else {
                            splitDragPolicy.mTargets.add(new SplitDragPolicy.Target(0, rect3, rect2, -1, false));
                            arrayList = splitDragPolicy.mTargets;
                        }
                    }
                } else {
                    CharSequence loadLabel = dragAppActivityInfo.loadLabel(splitDragPolicy.mContext.getPackageManager());
                    String string2 = loadLabel == null ? splitDragPolicy.mContext.getResources().getString(R.string.drag_and_drop_already_launch_this_app_toast) : splitDragPolicy.mContext.getResources().getString(R.string.drag_and_drop_already_launch_app_toast, loadLabel.toString());
                    Toast toast2 = splitDragPolicy.mToast;
                    if (toast2 != null) {
                        toast2.cancel();
                    }
                    Toast makeText2 = Toast.makeText(splitDragPolicy.mContext, string2, 0);
                    splitDragPolicy.mToast = makeText2;
                    makeText2.show();
                }
            }
            arrayList = splitDragPolicy.mTargets;
        }
        boolean z2 = !arrayList.isEmpty();
        for (int i6 = 0; i6 < arrayList.size(); i6++) {
            ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DRAG_AND_DROP, "Add target: %s", new Object[]{(SplitDragPolicy.Target) arrayList.get(i6)});
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

    /* JADX WARN: Code restructure failed: missing block: B:174:0x00a2, code lost:
    
        if (r3.y < r12) goto L33;
     */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0135 A[EDGE_INSN: B:154:0x0135->B:9:0x0135 BREAK  A[LOOP:0: B:141:0x0051->B:156:0x012d], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x012d A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x018d  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0196  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void update(android.view.DragEvent r22) {
        /*
            Method dump skipped, instructions count: 990
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.draganddrop.DropTargetLayout.update(android.view.DragEvent):void");
    }

    public final void updateNavigationBarVisibility(boolean z) {
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateNavigationBarVisibility : ", ", caller=", z);
        m.append(Debug.getCallers(5));
        Slog.d("DropTargetLayout", m.toString());
        if (z) {
            this.mStatusBarManager.disable(0);
        } else if (CoreRune.MW_DND_MULTI_SPLIT_DROP_TARGET && !this.mPolicy.isInSubDisplay() && Settings.Global.getInt(getContext().getContentResolver(), SettingsHelper.INDEX_TASK_BAR, 1) == 1) {
            Slog.d("DropTargetLayout", "Failed to disable navibar, Taskbar is shown");
        } else {
            this.mStatusBarManager.disable(23068672);
        }
    }
}
