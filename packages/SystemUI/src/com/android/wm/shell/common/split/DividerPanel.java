package com.android.wm.shell.common.split;

import android.R;
import android.animation.Animator;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.constraintlayout.widget.ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.splitscreen.AppPairShortcutController;
import com.android.wm.shell.splitscreen.SplitScreenTransitions;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda0;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes3.dex */
public class DividerPanel implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener, View.OnHoverListener {
    public AccessibilityManager mAccessibilityManager;
    public AlertDialog mAddToAppPairDialog;
    public AppPairShortcutController mAppPairShortcutController;
    public SplitWindowManager mCallbacks;
    public final ContentResolver mContentResolver;
    public Context mContext;
    public final AnonymousClass1 mDismissReceiver;
    public DividerView mDividerView;
    public final H mH;
    public final boolean mIsSystemUser;
    public SplitLayout mSplitLayout;
    public DividerPanelView mView;
    public final DividerPanelWindowManager mWindowManager;
    public boolean mIsLongPressOrHover = false;
    public final DividerPanel$$ExternalSyntheticLambda1 mRemoveRunnable = new Runnable() { // from class: com.android.wm.shell.common.split.DividerPanel$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.removeDividerPanel();
        }
    };

    public final class H extends Handler {
        public /* synthetic */ H(DividerPanel dividerPanel, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            DividerPanel dividerPanel = DividerPanel.this;
            AlertDialog alertDialog = dividerPanel.mAddToAppPairDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            dividerPanel.removeDividerPanel();
        }

        private H() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.common.split.DividerPanel$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.BroadcastReceiver, com.android.wm.shell.common.split.DividerPanel$1] */
    public DividerPanel(Context context) {
        this.mContext = new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight);
        this.mContentResolver = context.getContentResolver();
        this.mWindowManager = new DividerPanelWindowManager(context);
        H h = new H(this, 0 == true ? 1 : 0);
        this.mH = h;
        this.mIsSystemUser = ActivityManager.getCurrentUser() == 0;
        ?? r3 = new BroadcastReceiver() { // from class: com.android.wm.shell.common.split.DividerPanel.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                DividerPanel.this.mH.sendEmptyMessage(0);
            }
        };
        this.mDismissReceiver = r3;
        context.registerReceiver(r3, KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.USER_SWITCHED", PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS), null, h, 2);
    }

    public final ArrayMap getAppPairDialogItems() {
        ArrayMap arrayMap = new ArrayMap();
        if (isAddToTaskBarEnable()) {
            arrayMap.put(0, this.mContext.getResources().getString(com.android.systemui.R.string.taskbar));
        }
        if (MultiWindowUtils.isDefaultLauncher(this.mContext)) {
            arrayMap.put(1, this.mContext.getResources().getString(com.android.systemui.R.string.home_screen));
        }
        if (isAddToEdgeEnable()) {
            arrayMap.put(2, this.mContext.getResources().getString(com.android.systemui.R.string.apps_edge_panel));
        }
        return arrayMap;
    }

    public final boolean isAddToEdgeEnable() {
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "edge_enable", 0, -2) != 1) {
            Slog.d("DividerPanel", "Edge disable");
            return false;
        }
        String stringForUser = Settings.System.getStringForUser(this.mContext.getContentResolver(), "cocktail_bar_enabled_cocktails", -2);
        if (stringForUser == null || !stringForUser.contains("com.samsung.app.honeyspace.edge.appsedge.ui.panel.AppsEdgePanelProvider")) {
            Slog.d("DividerPanel", "AppsEdge disable");
            return false;
        }
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_EASY_MODE_SWITCH, 1, -2) == 0) {
            Slog.d("DividerPanel", "EasyMode on");
            return false;
        }
        if (!this.mIsSystemUser) {
            Slog.d("DividerPanel", "Not system user");
            return false;
        }
        if (this.mContext.getResources().getConfiguration().orientation == 2) {
            if (MultiWindowUtils.isTablet() || (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && !MultiWindowUtils.isInSubDisplay(this.mContext))) {
                return true;
            }
            Slog.d("DividerPanel", "Is not tablet, or is foldable device, but is in sub-display.");
            return false;
        }
        if (CoreRune.MW_MULTI_SPLIT_APP_PAIR_FOLDING_POLICY) {
            int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "edge_show_screen", 0, -2);
            boolean zIsInSubDisplay = MultiWindowUtils.isInSubDisplay(this.mContext);
            if (intForUser == 0) {
                zIsInSubDisplay = true;
            } else if (intForUser == 1) {
                zIsInSubDisplay = !zIsInSubDisplay;
            } else if (intForUser != 2) {
                zIsInSubDisplay = false;
            }
            if (!zIsInSubDisplay) {
                Slog.d("DividerPanel", "Invalid edge show screen");
                return false;
            }
        }
        return true;
    }

    public final boolean isAddToTaskBarEnable() {
        boolean z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LAUNCHER_SUPPORT_TASKBAR");
        boolean z2 = Settings.Global.getInt(this.mContentResolver, "sem_task_bar_available", 0) == 1;
        boolean zIsInSubDisplay = MultiWindowUtils.isInSubDisplay(this.mContext);
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("supportTaskBar: ", "hasTaskBar: ", " inSubDisplay: ", z, z2);
        sbM.append(zIsInSubDisplay);
        Slog.d("DividerPanel", sbM.toString());
        return z && z2 && !zIsInSubDisplay;
    }

    public final boolean isSupportPanelOpenPolicy() {
        StageCoordinator stageCoordinator = this.mSplitLayout.mStageCoordinator;
        if (stageCoordinator == null) {
            return true;
        }
        ActivityManager.RunningTaskInfo runningTaskInfo = stageCoordinator.mMainStage.mRootTaskInfo;
        ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = stageCoordinator.mSideStage.mRootTaskInfo;
        ComponentName componentName2 = runningTaskInfo2 != null ? runningTaskInfo2.topActivity : null;
        if (componentName == null || !(MultiWindowUtils.isAppsEdgeActivity(componentName) || componentName.getPackageName().equals("com.samsung.android.app.taskedge"))) {
            return (componentName2 == null || !(MultiWindowUtils.isAppsEdgeActivity(componentName2) || componentName2.getPackageName().equals("com.samsung.android.app.taskedge"))) && !this.mSplitLayout.mSplitState.isSplitStashed();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:84:0x0220  */
    @Override // android.view.View.OnClickListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onClick(View view) {
        float fCalculateSplitRatioForParallelMultiSplit;
        StageCoordinator stageCoordinator = this.mSplitLayout.mStageCoordinator;
        if (view.getId() == com.android.systemui.R.id.rotating_icon) {
            if (stageCoordinator != null) {
                stageCoordinator.rotateMultiSplitWithTransition();
            }
            if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1032", this.mSplitLayout.isVerticalDivision() ? "Horizontal split -> Vertical split" : "Vertical split -> Horizontal split");
            }
        } else if (view.getId() == com.android.systemui.R.id.switching_icon) {
            if (stageCoordinator != null) {
                if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit) {
                    boolean z = this.mDividerView.mIsCellDivider;
                    int i = stageCoordinator.getCellHostStageType() == 0 ? 0 : 1;
                    int i2 = z ? 5 : i ^ 1;
                    StageTaskListener stageTaskListenerByStageType = stageCoordinator.getStageTaskListenerByStageType(i);
                    StageTaskListener stageTaskListenerByStageType2 = stageCoordinator.getStageTaskListenerByStageType(i2);
                    if (stageTaskListenerByStageType == null || stageTaskListenerByStageType2 == null) {
                        Slog.w("StageCoordinator", "Cannot swapMultiFoldStageTasks, stage1=" + stageTaskListenerByStageType + ",stage2=" + stageTaskListenerByStageType2 + ",callers=" + Debug.getCallers(3));
                    } else {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        stageTaskListenerByStageType.reparentAllChildren(stageTaskListenerByStageType2.mRootTaskInfo.token, windowContainerTransaction);
                        stageTaskListenerByStageType2.reparentAllChildren(stageTaskListenerByStageType.mRootTaskInfo.token, windowContainerTransaction);
                        windowContainerTransaction.setChangeTransitMode(stageTaskListenerByStageType.mRootTaskInfo.token, 1, "swap_multi_fold_split");
                        windowContainerTransaction.setChangeTransitMode(stageTaskListenerByStageType2.mRootTaskInfo.token, 1, "swap_multi_fold_split");
                        stageCoordinator.mSplitLayout.setDividerInteractive("swapParallelStageTasks", false, false);
                        SplitScreenTransitions splitScreenTransitions = stageCoordinator.mSplitTransitions;
                        StageCoordinator$$ExternalSyntheticLambda0 stageCoordinator$$ExternalSyntheticLambda0 = new StageCoordinator$$ExternalSyntheticLambda0(3, stageCoordinator);
                        StageCoordinator$$ExternalSyntheticLambda0 stageCoordinator$$ExternalSyntheticLambda02 = new StageCoordinator$$ExternalSyntheticLambda0(5, stageCoordinator);
                        SplitDecorManager splitDecorManager = stageCoordinator.mMainStage.mSplitDecorManager;
                        SplitDecorManager splitDecorManager2 = stageCoordinator.mSideStage.mSplitDecorManager;
                        List list = Collections.EMPTY_LIST;
                        splitScreenTransitions.startResizeTransition(windowContainerTransaction, stageCoordinator, stageCoordinator$$ExternalSyntheticLambda0, stageCoordinator$$ExternalSyntheticLambda02, splitDecorManager, splitDecorManager2);
                    }
                } else {
                    stageCoordinator.swapTasksInSplitScreenMode$1();
                }
            }
            if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("1033");
            }
        } else if (view.getId() == com.android.systemui.R.id.add_app_pair_icon) {
            final ArrayMap appPairDialogItems = getAppPairDialogItems();
            if (!appPairDialogItems.isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                builder.setTitle(com.android.systemui.R.string.add_app_pair_to);
                builder.setItems((CharSequence[]) appPairDialogItems.values().toArray(new String[appPairDialogItems.size()]), new DialogInterface.OnClickListener() { // from class: com.android.wm.shell.common.split.DividerPanel$$ExternalSyntheticLambda2
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i3) throws Resources.NotFoundException {
                        this.f$0.mAppPairShortcutController.createAppPairShortcut(((Integer) appPairDialogItems.keyAt(i3)).intValue());
                    }
                });
                AlertDialog alertDialogCreate = builder.create();
                this.mAddToAppPairDialog = alertDialogCreate;
                alertDialogCreate.getWindow().setType(2008);
                this.mAddToAppPairDialog.getWindow().setGravity(80);
                this.mAddToAppPairDialog.show();
            }
        } else if (CoreRune.MW_PARALLEL_MULTI_SPLIT && view.getId() == com.android.systemui.R.id.change_layout_icon) {
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT_SA_LOGGING) {
                if (this.mSplitLayout.mParallelMultiSplit) {
                    CoreSaLogger.logForAdvanced("1039");
                } else {
                    CoreSaLogger.logForAdvanced("1038");
                }
            }
            if (stageCoordinator != null) {
                if (stageCoordinator.isMultiSplitScreenVisible()) {
                    StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
                    if (stageTaskListener.mRootTaskInfo != null) {
                        StageTaskListener stageTaskListener2 = stageCoordinator.mSideStage;
                        if (stageTaskListener2.mRootTaskInfo != null) {
                            StageTaskListener stageTaskListener3 = stageCoordinator.mCellStage;
                            if (stageTaskListener3.mRootTaskInfo == null) {
                                Slog.w("StageCoordinator", "changeParallelMultiSplitWithTransition: failed, multi-split isn't activated");
                            } else {
                                boolean z2 = stageCoordinator.mSplitLayout.mParallelMultiSplit;
                                boolean z3 = !z2;
                                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                MultiSplitLayoutInfo currentMultiSplitLayoutInfo = stageCoordinator.getCurrentMultiSplitLayoutInfo();
                                if (z2) {
                                    int iConvertCreateMode = StageCoordinator.convertCreateMode(currentMultiSplitLayoutInfo);
                                    int cellHostStageType = stageCoordinator.getCellHostStageType();
                                    if (iConvertCreateMode == 2 || iConvertCreateMode == 3) {
                                        StageTaskListener stageTaskListener4 = cellHostStageType == 1 ? stageTaskListener : stageTaskListener2;
                                        stageTaskListener3.reparentAllChildren(stageTaskListener4.mRootTaskInfo.token, windowContainerTransaction2);
                                        stageTaskListener4.reparentAllChildren(stageTaskListener3.mRootTaskInfo.token, windowContainerTransaction2);
                                    }
                                    currentMultiSplitLayoutInfo.sideStagePosition = cellHostStageType == 1 ? 1 : 0;
                                    currentMultiSplitLayoutInfo.splitDivision = 0;
                                    currentMultiSplitLayoutInfo.cellStagePosition = 96;
                                    fCalculateSplitRatioForParallelMultiSplit = 0.5f;
                                } else {
                                    stageCoordinator.applyParallelMultiSplitLayoutInfo(windowContainerTransaction2, currentMultiSplitLayoutInfo);
                                    fCalculateSplitRatioForParallelMultiSplit = stageCoordinator.calculateSplitRatioForParallelMultiSplit(currentMultiSplitLayoutInfo);
                                }
                                stageCoordinator.mChangingParallelMultiSplit = true;
                                SplitLayout splitLayout = stageCoordinator.mSplitLayout;
                                splitLayout.mParallelMultiSplit = z3;
                                int splitDivision = stageCoordinator.getSplitDivision();
                                int i3 = currentMultiSplitLayoutInfo.splitDivision;
                                if (splitLayout.mSplitDivision != i3) {
                                    splitLayout.mSplitDivision = i3;
                                    splitLayout.mIsLeftRightSplit = splitLayout.isVerticalDivision();
                                    splitLayout.updateSnapAlgorithm(splitDivision);
                                }
                                stageCoordinator.mSplitLayout.setDivideRatio(fCalculateSplitRatioForParallelMultiSplit, true, true);
                                stageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, currentMultiSplitLayoutInfo.cellStagePosition, true, false);
                                SplitLayout splitLayout2 = stageCoordinator.mSplitLayout;
                                splitLayout2.releaseCellDivider(null);
                                if (!splitLayout2.mCellInitialized) {
                                    splitLayout2.mCellInitialized = true;
                                    splitLayout2.mCellSplitWindowManager.init(splitLayout2, splitLayout2.mInsetsState, false, splitLayout2.mDesktopState);
                                    splitLayout2.mCellSnapAlgorithm = splitLayout2.createCellSnapAlgorithm();
                                }
                                windowContainerTransaction2.setChangeTransitMode(stageTaskListener.mRootTaskInfo.token, 1, "change_parallel_multi_split");
                                windowContainerTransaction2.setChangeTransitMode(stageTaskListener2.mRootTaskInfo.token, 1, "change_parallel_multi_split");
                                windowContainerTransaction2.setChangeTransitMode(stageTaskListener3.mRootTaskInfo.token, 1, "change_parallel_multi_split");
                                stageCoordinator.updateMultiSplitLayout(currentMultiSplitLayoutInfo, true, windowContainerTransaction2);
                                stageCoordinator.mChangingParallelMultiSplit = false;
                            }
                        }
                    }
                }
            }
        }
        this.mIsLongPressOrHover = false;
        removeDividerPanel();
    }

    @Override // android.view.View.OnHoverListener
    public final boolean onHover(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 9) {
            this.mIsLongPressOrHover = true;
        } else if (action == 10) {
            this.mIsLongPressOrHover = false;
            scheduleRemoveDividerPanel();
        }
        return false;
    }

    @Override // android.view.View.OnLongClickListener
    public final boolean onLongClick(View view) {
        this.mIsLongPressOrHover = true;
        return false;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 1 || action == 3) {
            this.mIsLongPressOrHover = false;
            scheduleRemoveDividerPanel();
        }
        return false;
    }

    public final void removeDividerPanel() {
        if (this.mIsLongPressOrHover) {
            return;
        }
        DividerPanelWindowManager dividerPanelWindowManager = this.mWindowManager;
        if (dividerPanelWindowManager.mView != null) {
            Log.d("DividerPanelWindowManager", "remove, mView=" + dividerPanelWindowManager.mView);
            dividerPanelWindowManager.mWm.removeViewImmediate(dividerPanelWindowManager.mView);
            dividerPanelWindowManager.mView = null;
        }
        SplitWindowManager splitWindowManager = this.mCallbacks;
        if (splitWindowManager != null && splitWindowManager.mShowingFirstAutoOpenDividerPanel) {
            splitWindowManager.mIsFirstAutoOpenDividerPanel = false;
            SharedPreferences.Editor editorEdit = splitWindowManager.mPref.edit();
            editorEdit.putBoolean("divider_panel_first_auto_open", false);
            editorEdit.apply();
            Slog.d("SplitWindowManager", "Exit DividerPanel first auto open");
        }
        this.mCallbacks = null;
    }

    public final void scheduleRemoveDividerPanel() {
        this.mH.removeCallbacks(this.mRemoveRunnable);
        this.mH.postDelayed(this.mRemoveRunnable, this.mAccessibilityManager != null ? r0.getRecommendedTimeoutMillis(3000, 4) : 3000);
    }

    public final void setIconListener(LottieAnimationView lottieAnimationView) {
        lottieAnimationView.setOnClickListener(this);
        lottieAnimationView.setOnLongClickListener(this);
        lottieAnimationView.setOnTouchListener(this);
        lottieAnimationView.setOnHoverListener(this);
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x020e, code lost:
    
        r1 = com.android.wm.shell.splitscreen.AppPairShortcutController.supportAppPairShortCut(r15, r6);
     */
    /* JADX WARN: Removed duplicated region for block: B:140:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02c5  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02d7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x0307  */
    /* JADX WARN: Removed duplicated region for block: B:170:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0348  */
    /* JADX WARN: Removed duplicated region for block: B:200:0x034f  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0361  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0372  */
    /* JADX WARN: Removed duplicated region for block: B:218:0x03b8  */
    /* JADX WARN: Removed duplicated region for block: B:224:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0462  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:249:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00c9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDividerPanel() {
        char c;
        boolean zIsSplitScreenFeasible;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        final boolean z6;
        boolean zSupportAppPairShortCut;
        boolean z7;
        WindowContainerToken windowContainerToken;
        final boolean z8;
        int i;
        int childCount;
        int i2;
        StageCoordinator stageCoordinator;
        removeDividerPanel();
        DividerPanelView dividerPanelView = (DividerPanelView) LayoutInflater.from(this.mContext).inflate(com.android.systemui.R.layout.divider_panel, (ViewGroup) null);
        this.mView = dividerPanelView;
        dividerPanelView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.common.split.DividerPanel$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                DividerPanel dividerPanel = this.f$0;
                dividerPanel.getClass();
                int action = motionEvent.getAction();
                if (action != 0 && action != 4) {
                    return true;
                }
                dividerPanel.removeDividerPanel();
                return true;
            }
        });
        LottieAnimationView lottieAnimationView = (LottieAnimationView) this.mView.findViewById(com.android.systemui.R.id.rotating_icon);
        LottieAnimationView lottieAnimationView2 = (LottieAnimationView) this.mView.findViewById(com.android.systemui.R.id.switching_icon);
        LottieAnimationView lottieAnimationView3 = (LottieAnimationView) this.mView.findViewById(com.android.systemui.R.id.add_app_pair_icon);
        LottieAnimationView lottieAnimationView4 = (LottieAnimationView) this.mView.findViewById(com.android.systemui.R.id.change_layout_icon);
        setIconListener(lottieAnimationView);
        setIconListener(lottieAnimationView2);
        setIconListener(lottieAnimationView3);
        setIconListener(lottieAnimationView4);
        boolean z9 = CoreRune.MW_MULTI_SPLIT_FREE_POSITION;
        boolean z10 = !z9 ? this.mContext.getResources().getConfiguration().orientation != 1 : this.mSplitLayout.isVerticalDivision();
        StageCoordinator stageCoordinator2 = this.mSplitLayout.mStageCoordinator;
        if (stageCoordinator2 == null) {
            Slog.d("DividerPanel", "addDividerPanel, failed. StageCoordinator is null");
            return;
        }
        int splitCreateMode = stageCoordinator2.getSplitCreateMode();
        final DividerPanelView dividerPanelView2 = this.mView;
        boolean z11 = this.mContext.getResources().getConfiguration().orientation == 2;
        boolean zIsMultiSplitScreenVisible = stageCoordinator2.isMultiSplitScreenVisible();
        boolean z12 = stageCoordinator2.mSplitLayout.mParallelMultiSplit;
        if (CoreRune.MW_MULTI_SPLIT && !(CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitLayout.mParallelMultiSplit)) {
            if (splitCreateMode != 2) {
                c = splitCreateMode != 3 ? splitCreateMode != 4 ? splitCreateMode != 5 ? (char) 65535 : (char) 2 : z9 ? (char) 5 : (char) 3 : (char) 4;
            } else if (z9) {
            }
            zIsSplitScreenFeasible = this.mSplitLayout.isSplitScreenFeasible(c == 3 || c == 5);
        } else {
            zIsSplitScreenFeasible = false;
        }
        SplitLayout splitLayout = this.mSplitLayout;
        StageCoordinator stageCoordinator3 = splitLayout.mStageCoordinator;
        final boolean z13 = stageCoordinator3 != null && (!stageCoordinator3.mCellDividerVisible || (CoreRune.MW_PARALLEL_MULTI_SPLIT && splitLayout.mParallelMultiSplit));
        int i3 = this.mContext.getResources().getConfiguration().smallestScreenWidthDp;
        boolean zIsAddToEdgeEnable = isAddToEdgeEnable();
        if (i3 >= 600 || this.mContext.getResources().getConfiguration().orientation != 2 || zIsAddToEdgeEnable) {
            AppPairShortcutController appPairShortcutController = this.mAppPairShortcutController;
            Context context = this.mContext;
            SplitLayout splitLayout2 = appPairShortcutController.mSplitLayout;
            WindowContainerToken windowContainerToken2 = splitLayout2.mWinToken1;
            if (windowContainerToken2 == null || splitLayout2.mWinToken2 == null) {
                z = zIsSplitScreenFeasible;
                z2 = z10;
                z3 = z12;
                z4 = z11;
                Slog.e("AppPairShortcutController", "isSupportAppPairPolicy: Can't find topActivity there is null");
            } else {
                ShellTaskOrganizer shellTaskOrganizer = splitLayout2.mTaskOrganizer;
                List childTasks = shellTaskOrganizer.getChildTasks(windowContainerToken2, (int[]) null);
                z = zIsSplitScreenFeasible;
                List childTasks2 = shellTaskOrganizer.getChildTasks(splitLayout2.mWinToken2, (int[]) null);
                boolean z14 = CoreRune.MW_MULTI_SPLIT_APP_PAIR;
                List childTasks3 = (!z14 || (windowContainerToken = splitLayout2.mWinToken3) == null) ? null : shellTaskOrganizer.getChildTasks(windowContainerToken, (int[]) null);
                if (childTasks == null || childTasks.isEmpty() || childTasks2 == null || childTasks2.isEmpty()) {
                    z2 = z10;
                    z3 = z12;
                    z4 = z11;
                    Log.e("AppPairShortcutController", "[isSupportAppPairPolicy] getChildTasks() is null or empty");
                } else {
                    ArrayList arrayList = new ArrayList();
                    arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                    arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
                    if (z14 && childTasks3 != null && !childTasks3.isEmpty()) {
                        arrayList.add((ActivityManager.RunningTaskInfo) childTasks3.get(0));
                    }
                    HashMap map = new HashMap();
                    HashSet hashSet = new HashSet();
                    int size = arrayList.size();
                    z2 = z10;
                    int i4 = 0;
                    while (true) {
                        if (i4 >= size) {
                            z3 = z12;
                            z4 = z11;
                            zSupportAppPairShortCut = true;
                            break;
                        }
                        Object obj = arrayList.get(i4);
                        i4++;
                        int i5 = size;
                        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj;
                        z3 = z12;
                        ComponentName componentName = runningTaskInfo.realActivity;
                        if (componentName == null) {
                            Log.w("AppPairShortcutController", "componentName is null");
                            z4 = z11;
                            zSupportAppPairShortCut = false;
                            break;
                        }
                        String packageName = componentName.getPackageName();
                        z4 = z11;
                        HashSet hashSet2 = (HashSet) map.get(Integer.valueOf(runningTaskInfo.userId));
                        if (hashSet2 == null) {
                            hashSet2 = new HashSet();
                            map.put(Integer.valueOf(runningTaskInfo.userId), hashSet2);
                            hashSet.add(componentName.getClassName());
                        }
                        if (hashSet2.contains(packageName) && (!packageName.equals("com.google.android.googlequicksearchbox") || hashSet.contains(componentName.getClassName()))) {
                            break;
                        }
                        hashSet2.add(packageName);
                        z12 = z3;
                        size = i5;
                        z11 = z4;
                    }
                    if (zSupportAppPairShortCut) {
                        int size2 = arrayList.size();
                        int i6 = 0;
                        while (i6 < size2) {
                            Object obj2 = arrayList.get(i6);
                            i6++;
                            ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) obj2;
                            ComponentName componentName2 = runningTaskInfo2.realActivity;
                            if (componentName2 == null) {
                                Log.w("AppPairShortcutController", "componentName is null");
                            } else {
                                ActivityInfo activityInfo = runningTaskInfo2.topActivityInfo;
                                if (activityInfo == null || activityInfo.applicationInfo.uid != 1001) {
                                    String packageName2 = componentName2.getPackageName();
                                    int i7 = runningTaskInfo2.userId;
                                    Intent launchIntentForPackageAsUser = MultiWindowUtils.getLaunchIntentForPackageAsUser(packageName2, i7);
                                    if (launchIntentForPackageAsUser == null || launchIntentForPackageAsUser.getComponent() == null) {
                                        Log.w("AppPairShortcutController", ConstraintSet$WriteJsonEngine$$ExternalSyntheticOutline0.m(i7, "getLaunchIntentForPackageAsUser is null or empty component (", packageName2, ",", ")"));
                                    }
                                }
                            }
                            z7 = false;
                            break;
                        }
                        z7 = true;
                        if (z7) {
                            z5 = true;
                            if (!z5 && (isAddToTaskBarEnable() || MultiWindowUtils.isDefaultLauncher(this.mContext) || zIsAddToEdgeEnable)) {
                                z6 = true;
                            }
                            z8 = CoreRune.MW_PARALLEL_MULTI_SPLIT && (stageCoordinator = this.mSplitLayout.mStageCoordinator) != null && stageCoordinator.isMultiSplitScreenVisible();
                            dividerPanelView2.getClass();
                            if (CoreRune.MW_MULTI_SPLIT_DIVIDER && z) {
                                dividerPanelView2.mRotatingIcon.setVisibility(0);
                                if (zIsMultiSplitScreenVisible) {
                                    i = splitCreateMode == 4 ? 100 : splitCreateMode == 5 ? 150 : splitCreateMode == 2 ? 200 : splitCreateMode == 3 ? 250 : 0;
                                    dividerPanelView2.mRotatingIcon.setMinFrame(i);
                                    dividerPanelView2.mRotatingIcon.setMaxFrame(i + 30);
                                } else {
                                    if (z2) {
                                        i = 50;
                                    }
                                    dividerPanelView2.mRotatingIcon.setMinFrame(i);
                                    dividerPanelView2.mRotatingIcon.setMaxFrame(i + 30);
                                }
                            } else {
                                i = 0;
                            }
                            if (z8) {
                                if (z3) {
                                    i = z4 ? 200 : 450;
                                } else if (splitCreateMode == 4) {
                                    i = z4 ? 0 : 250;
                                } else if (splitCreateMode == 5) {
                                    i = z4 ? 50 : 300;
                                } else if (splitCreateMode == 2) {
                                    i = z4 ? 100 : 350;
                                } else if (splitCreateMode == 3) {
                                    i = z4 ? 150 : 400;
                                }
                                dividerPanelView2.mChangeLayoutIcon.setMinFrame(i);
                                dividerPanelView2.mChangeLayoutIcon.setMaxFrame(i + 30);
                            } else {
                                dividerPanelView2.mChangeLayoutIcon.setVisibility(8);
                            }
                            if (z13) {
                                int i8 = z2 ? 50 : 0;
                                dividerPanelView2.mSwitchingIcon.setMinFrame(i8);
                                dividerPanelView2.mSwitchingIcon.setMaxFrame(i8 + 33);
                            } else {
                                dividerPanelView2.mSwitchingIcon.setVisibility(8);
                            }
                            if (!z6) {
                                dividerPanelView2.mAddAppPairIcon.setVisibility(8);
                            }
                            final LottieAnimationView lottieAnimationView5 = z ? dividerPanelView2.mRotatingIcon : z8 ? dividerPanelView2.mChangeLayoutIcon : z13 ? dividerPanelView2.mSwitchingIcon : z6 ? dividerPanelView2.mAddAppPairIcon : null;
                            dividerPanelView2.mHandler.postDelayed(new Runnable() { // from class: com.android.wm.shell.common.split.DividerPanelView$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    LottieAnimationView lottieAnimationView6 = lottieAnimationView5;
                                    int i9 = DividerPanelView.$r8$clinit;
                                    if (lottieAnimationView6 != null) {
                                        lottieAnimationView6.playAnimation();
                                    }
                                }
                            }, 150L);
                            dividerPanelView2.mRotatingIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.1
                                public final /* synthetic */ boolean val$canAddToAppPair;
                                public final /* synthetic */ boolean val$canChangeLayout;
                                public final /* synthetic */ boolean val$canSwapTask;

                                public AnonymousClass1(final boolean z82, final boolean z132, final boolean z62) {
                                    z = z82;
                                    z = z132;
                                    z = z62;
                                }

                                @Override // android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    if (z) {
                                        DividerPanelView.this.mChangeLayoutIcon.playAnimation();
                                    } else if (z) {
                                        DividerPanelView.this.mSwitchingIcon.playAnimation();
                                    } else if (z) {
                                        DividerPanelView.this.mAddAppPairIcon.playAnimation();
                                    }
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
                            dividerPanelView2.mChangeLayoutIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.2
                                public final /* synthetic */ boolean val$canAddToAppPair;
                                public final /* synthetic */ boolean val$canSwapTask;

                                public AnonymousClass2(final boolean z132, final boolean z62) {
                                    z = z132;
                                    z = z62;
                                }

                                @Override // android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    if (z) {
                                        DividerPanelView.this.mSwitchingIcon.playAnimation();
                                    } else if (z) {
                                        DividerPanelView.this.mAddAppPairIcon.playAnimation();
                                    }
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
                            dividerPanelView2.mSwitchingIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.3
                                public final /* synthetic */ boolean val$canAddToAppPair;

                                public AnonymousClass3(final boolean z62) {
                                    z = z62;
                                }

                                @Override // android.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator) {
                                    if (z) {
                                        DividerPanelView.this.mAddAppPairIcon.playAnimation();
                                    }
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
                            DividerPanelView dividerPanelView3 = this.mView;
                            childCount = dividerPanelView3.mContainer.getChildCount();
                            int i9 = 0;
                            for (i2 = 0; i2 < childCount; i2++) {
                                if (dividerPanelView3.mContainer.getChildAt(i2).getVisibility() == 0) {
                                    i9++;
                                }
                            }
                            final int dimensionPixelSize = (dividerPanelView3.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_button_size) * i9) + (dividerPanelView3.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_rounded_corner_width) * 2);
                            final int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_height);
                            this.mView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.wm.shell.common.split.DividerPanel.2
                                @Override // android.view.ViewOutlineProvider
                                public final void getOutline(View view, Outline outline) {
                                    outline.setRoundRect(0, 0, dimensionPixelSize, dimensionPixelSize2, DividerPanel.this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_buttons_radius));
                                    outline.setAlpha(1.0f);
                                }
                            });
                            Rect rect = new Rect();
                            DisplayLayout displayLayout = this.mSplitLayout.getDisplayLayout(this.mContext);
                            displayLayout.getClass();
                            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                                displayLayout.getStableBounds(rect, true);
                            } else {
                                rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                            }
                            DividerPanelWindowManager dividerPanelWindowManager = this.mWindowManager;
                            DividerPanelView dividerPanelView4 = this.mView;
                            dividerPanelWindowManager.getClass();
                            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dimensionPixelSize, dimensionPixelSize2, 2603, 262176, -3);
                            dividerPanelWindowManager.mLp = layoutParams;
                            layoutParams.setTitle("DividerPanel");
                            WindowManager.LayoutParams layoutParams2 = dividerPanelWindowManager.mLp;
                            layoutParams2.layoutInDisplayCutoutMode = 1;
                            layoutParams2.privateFlags |= 80;
                            layoutParams2.windowAnimations = com.android.systemui.R.style.SplitDividerPanel_WindowAnimation;
                            dividerPanelView4.setSystemUiVisibility(1792);
                            int currentPosition = dividerPanelWindowManager.mDividerView.getCurrentPosition();
                            DividerView dividerView = dividerPanelWindowManager.mDividerView;
                            int i10 = dividerView.mSplitLayout.mDividerSize;
                            dividerPanelWindowManager.mLp.gravity = 51;
                            if (z2) {
                                int width = (dividerPanelWindowManager.mDividerView.getWidth() / 2) + dividerView.getLeft();
                                WindowManager.LayoutParams layoutParams3 = dividerPanelWindowManager.mLp;
                                layoutParams3.x = (rect.left + width) - (dimensionPixelSize / 2);
                                layoutParams3.y = (i10 / 2) + (currentPosition - (dimensionPixelSize2 / 2));
                            } else {
                                int height = (dividerPanelWindowManager.mDividerView.getHeight() / 2) + dividerView.getTop();
                                WindowManager.LayoutParams layoutParams4 = dividerPanelWindowManager.mLp;
                                layoutParams4.x = (i10 / 2) + (currentPosition - (dimensionPixelSize / 2));
                                layoutParams4.y = (rect.top + height) - (dimensionPixelSize2 / 2);
                            }
                            dividerPanelWindowManager.mWm.addView(dividerPanelView4, dividerPanelWindowManager.mLp);
                            dividerPanelWindowManager.mView = dividerPanelView4;
                            scheduleRemoveDividerPanel();
                            if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
                                CoreSaLogger.logForAdvanced("1031");
                                return;
                            }
                            return;
                        }
                        Log.i("AppPairShortcutController", "[isSupportAppPairPolicy] isSupportAppPairType returns false. " + arrayList);
                    } else {
                        Log.i("AppPairShortcutController", "[isSupportAppPairPolicy] isSupportAppPairForMultiInstance returns false. " + arrayList);
                    }
                }
            }
            z5 = false;
            if (!z5) {
            }
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
            }
            dividerPanelView2.getClass();
            if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
                i = 0;
            }
            if (z82) {
            }
            if (z132) {
            }
            if (!z62) {
            }
            if (z) {
            }
            dividerPanelView2.mHandler.postDelayed(new Runnable() { // from class: com.android.wm.shell.common.split.DividerPanelView$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    LottieAnimationView lottieAnimationView6 = lottieAnimationView5;
                    int i92 = DividerPanelView.$r8$clinit;
                    if (lottieAnimationView6 != null) {
                        lottieAnimationView6.playAnimation();
                    }
                }
            }, 150L);
            dividerPanelView2.mRotatingIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.1
                public final /* synthetic */ boolean val$canAddToAppPair;
                public final /* synthetic */ boolean val$canChangeLayout;
                public final /* synthetic */ boolean val$canSwapTask;

                public AnonymousClass1(final boolean z82, final boolean z132, final boolean z62) {
                    z = z82;
                    z = z132;
                    z = z62;
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (z) {
                        DividerPanelView.this.mChangeLayoutIcon.playAnimation();
                    } else if (z) {
                        DividerPanelView.this.mSwitchingIcon.playAnimation();
                    } else if (z) {
                        DividerPanelView.this.mAddAppPairIcon.playAnimation();
                    }
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
            dividerPanelView2.mChangeLayoutIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.2
                public final /* synthetic */ boolean val$canAddToAppPair;
                public final /* synthetic */ boolean val$canSwapTask;

                public AnonymousClass2(final boolean z132, final boolean z62) {
                    z = z132;
                    z = z62;
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (z) {
                        DividerPanelView.this.mSwitchingIcon.playAnimation();
                    } else if (z) {
                        DividerPanelView.this.mAddAppPairIcon.playAnimation();
                    }
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
            dividerPanelView2.mSwitchingIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.3
                public final /* synthetic */ boolean val$canAddToAppPair;

                public AnonymousClass3(final boolean z62) {
                    z = z62;
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    if (z) {
                        DividerPanelView.this.mAddAppPairIcon.playAnimation();
                    }
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
            DividerPanelView dividerPanelView32 = this.mView;
            childCount = dividerPanelView32.mContainer.getChildCount();
            int i92 = 0;
            while (i2 < childCount) {
            }
            final int dimensionPixelSize3 = (dividerPanelView32.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_button_size) * i92) + (dividerPanelView32.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_rounded_corner_width) * 2);
            final int dimensionPixelSize22 = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_height);
            this.mView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.wm.shell.common.split.DividerPanel.2
                @Override // android.view.ViewOutlineProvider
                public final void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, dimensionPixelSize3, dimensionPixelSize22, DividerPanel.this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_buttons_radius));
                    outline.setAlpha(1.0f);
                }
            });
            Rect rect2 = new Rect();
            DisplayLayout displayLayout2 = this.mSplitLayout.getDisplayLayout(this.mContext);
            displayLayout2.getClass();
            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            }
            DividerPanelWindowManager dividerPanelWindowManager2 = this.mWindowManager;
            DividerPanelView dividerPanelView42 = this.mView;
            dividerPanelWindowManager2.getClass();
            WindowManager.LayoutParams layoutParams5 = new WindowManager.LayoutParams(dimensionPixelSize3, dimensionPixelSize22, 2603, 262176, -3);
            dividerPanelWindowManager2.mLp = layoutParams5;
            layoutParams5.setTitle("DividerPanel");
            WindowManager.LayoutParams layoutParams22 = dividerPanelWindowManager2.mLp;
            layoutParams22.layoutInDisplayCutoutMode = 1;
            layoutParams22.privateFlags |= 80;
            layoutParams22.windowAnimations = com.android.systemui.R.style.SplitDividerPanel_WindowAnimation;
            dividerPanelView42.setSystemUiVisibility(1792);
            int currentPosition2 = dividerPanelWindowManager2.mDividerView.getCurrentPosition();
            DividerView dividerView2 = dividerPanelWindowManager2.mDividerView;
            int i102 = dividerView2.mSplitLayout.mDividerSize;
            dividerPanelWindowManager2.mLp.gravity = 51;
            if (z2) {
            }
            dividerPanelWindowManager2.mWm.addView(dividerPanelView42, dividerPanelWindowManager2.mLp);
            dividerPanelWindowManager2.mView = dividerPanelView42;
            scheduleRemoveDividerPanel();
            if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
            }
        } else {
            z = zIsSplitScreenFeasible;
            z2 = z10;
            z3 = z12;
            z4 = z11;
        }
        z62 = false;
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
        }
        dividerPanelView2.getClass();
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
        }
        if (z82) {
        }
        if (z132) {
        }
        if (!z62) {
        }
        if (z) {
        }
        dividerPanelView2.mHandler.postDelayed(new Runnable() { // from class: com.android.wm.shell.common.split.DividerPanelView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                LottieAnimationView lottieAnimationView6 = lottieAnimationView5;
                int i922 = DividerPanelView.$r8$clinit;
                if (lottieAnimationView6 != null) {
                    lottieAnimationView6.playAnimation();
                }
            }
        }, 150L);
        dividerPanelView2.mRotatingIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.1
            public final /* synthetic */ boolean val$canAddToAppPair;
            public final /* synthetic */ boolean val$canChangeLayout;
            public final /* synthetic */ boolean val$canSwapTask;

            public AnonymousClass1(final boolean z82, final boolean z132, final boolean z62) {
                z = z82;
                z = z132;
                z = z62;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (z) {
                    DividerPanelView.this.mChangeLayoutIcon.playAnimation();
                } else if (z) {
                    DividerPanelView.this.mSwitchingIcon.playAnimation();
                } else if (z) {
                    DividerPanelView.this.mAddAppPairIcon.playAnimation();
                }
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
        dividerPanelView2.mChangeLayoutIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.2
            public final /* synthetic */ boolean val$canAddToAppPair;
            public final /* synthetic */ boolean val$canSwapTask;

            public AnonymousClass2(final boolean z132, final boolean z62) {
                z = z132;
                z = z62;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (z) {
                    DividerPanelView.this.mSwitchingIcon.playAnimation();
                } else if (z) {
                    DividerPanelView.this.mAddAppPairIcon.playAnimation();
                }
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
        dividerPanelView2.mSwitchingIcon.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.3
            public final /* synthetic */ boolean val$canAddToAppPair;

            public AnonymousClass3(final boolean z62) {
                z = z62;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (z) {
                    DividerPanelView.this.mAddAppPairIcon.playAnimation();
                }
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
        DividerPanelView dividerPanelView322 = this.mView;
        childCount = dividerPanelView322.mContainer.getChildCount();
        int i922 = 0;
        while (i2 < childCount) {
        }
        final int dimensionPixelSize32 = (dividerPanelView322.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_button_size) * i922) + (dividerPanelView322.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_rounded_corner_width) * 2);
        final int dimensionPixelSize222 = this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_height);
        this.mView.setOutlineProvider(new ViewOutlineProvider() { // from class: com.android.wm.shell.common.split.DividerPanel.2
            @Override // android.view.ViewOutlineProvider
            public final void getOutline(View view, Outline outline) {
                outline.setRoundRect(0, 0, dimensionPixelSize32, dimensionPixelSize222, DividerPanel.this.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_buttons_radius));
                outline.setAlpha(1.0f);
            }
        });
        Rect rect22 = new Rect();
        DisplayLayout displayLayout22 = this.mSplitLayout.getDisplayLayout(this.mContext);
        displayLayout22.getClass();
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
        }
        DividerPanelWindowManager dividerPanelWindowManager22 = this.mWindowManager;
        DividerPanelView dividerPanelView422 = this.mView;
        dividerPanelWindowManager22.getClass();
        WindowManager.LayoutParams layoutParams52 = new WindowManager.LayoutParams(dimensionPixelSize32, dimensionPixelSize222, 2603, 262176, -3);
        dividerPanelWindowManager22.mLp = layoutParams52;
        layoutParams52.setTitle("DividerPanel");
        WindowManager.LayoutParams layoutParams222 = dividerPanelWindowManager22.mLp;
        layoutParams222.layoutInDisplayCutoutMode = 1;
        layoutParams222.privateFlags |= 80;
        layoutParams222.windowAnimations = com.android.systemui.R.style.SplitDividerPanel_WindowAnimation;
        dividerPanelView422.setSystemUiVisibility(1792);
        int currentPosition22 = dividerPanelWindowManager22.mDividerView.getCurrentPosition();
        DividerView dividerView22 = dividerPanelWindowManager22.mDividerView;
        int i1022 = dividerView22.mSplitLayout.mDividerSize;
        dividerPanelWindowManager22.mLp.gravity = 51;
        if (z2) {
        }
        dividerPanelWindowManager22.mWm.addView(dividerPanelView422, dividerPanelWindowManager22.mLp);
        dividerPanelWindowManager22.mView = dividerPanelView422;
        scheduleRemoveDividerPanel();
        if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
        }
    }
}
