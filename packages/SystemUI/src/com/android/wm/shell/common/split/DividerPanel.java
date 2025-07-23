package com.android.wm.shell.common.split;

import android.R;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.window.WindowContainerTransaction;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.wm.shell.splitscreen.AppPairShortcutController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            DividerPanel.this.removeDividerPanel();
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            boolean isInSubDisplay = MultiWindowUtils.isInSubDisplay(this.mContext);
            if (intForUser == 0) {
                isInSubDisplay = true;
            } else if (intForUser == 1) {
                isInSubDisplay = !isInSubDisplay;
            } else if (intForUser != 2) {
                isInSubDisplay = false;
            }
            if (!isInSubDisplay) {
                Slog.d("DividerPanel", "Invalid edge show screen");
                return false;
            }
        }
        return true;
    }

    public final boolean isAddToTaskBarEnable() {
        boolean z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LAUNCHER_SUPPORT_TASKBAR");
        boolean z2 = Settings.Global.getInt(this.mContentResolver, "sem_task_bar_available", 0) == 1;
        boolean isInSubDisplay = MultiWindowUtils.isInSubDisplay(this.mContext);
        StringBuilder m = EmergencyButtonController$$ExternalSyntheticOutline0.m("supportTaskBar: ", "hasTaskBar: ", " inSubDisplay: ", z, z2);
        m.append(isInSubDisplay);
        Slog.d("DividerPanel", m.toString());
        return z && z2 && !isInSubDisplay;
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

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        float f;
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
                        stageCoordinator.mSplitTransitions.startEnterTransition(windowContainerTransaction, null, stageCoordinator, VolteConstants.ErrorCode.CALL_SESSION_ABORT, false, 1);
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
                    public final void onClick(DialogInterface dialogInterface, int i3) {
                        DividerPanel.this.mAppPairShortcutController.createAppPairShortcut(((Integer) appPairDialogItems.keyAt(i3)).intValue());
                    }
                });
                AlertDialog create = builder.create();
                this.mAddToAppPairDialog = create;
                create.getWindow().setType(2008);
                this.mAddToAppPairDialog.getWindow().setGravity(80);
                this.mAddToAppPairDialog.show();
                if (CoreRune.MW_SPLIT_APP_PAIR_SA_LOGGING) {
                    CoreSaLogger.logForAdvanced("1036");
                }
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
                            if (stageTaskListener3.mRootTaskInfo != null) {
                                boolean z2 = stageCoordinator.mSplitLayout.mParallelMultiSplit;
                                boolean z3 = !z2;
                                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                                MultiSplitLayoutInfo currentMultiSplitLayoutInfo = stageCoordinator.getCurrentMultiSplitLayoutInfo();
                                if (z2) {
                                    int convertCreateMode = StageCoordinator.convertCreateMode(currentMultiSplitLayoutInfo);
                                    int cellHostStageType = stageCoordinator.getCellHostStageType();
                                    if (convertCreateMode == 2 || convertCreateMode == 3) {
                                        StageTaskListener stageTaskListener4 = cellHostStageType == 1 ? stageTaskListener : stageTaskListener2;
                                        stageTaskListener3.reparentAllChildren(stageTaskListener4.mRootTaskInfo.token, windowContainerTransaction2);
                                        stageTaskListener4.reparentAllChildren(stageTaskListener3.mRootTaskInfo.token, windowContainerTransaction2);
                                    }
                                    currentMultiSplitLayoutInfo.sideStagePosition = cellHostStageType == 1 ? 1 : 0;
                                    currentMultiSplitLayoutInfo.splitDivision = 0;
                                    currentMultiSplitLayoutInfo.cellStagePosition = 96;
                                    f = 0.5f;
                                } else {
                                    stageCoordinator.applyParallelMultiSplitLayoutInfo(windowContainerTransaction2, currentMultiSplitLayoutInfo);
                                    f = stageCoordinator.calculateSplitRatioForParallelMultiSplit(currentMultiSplitLayoutInfo);
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
                                stageCoordinator.mSplitLayout.setDivideRatio(f, true, true);
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
                Slog.w("StageCoordinator", "changeParallelMultiSplitWithTransition: failed, multi-split isn't activated");
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
            SharedPreferences.Editor edit = splitWindowManager.mPref.edit();
            edit.putBoolean("divider_panel_first_auto_open", false);
            edit.apply();
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

    /* JADX WARN: Code restructure failed: missing block: B:166:0x020e, code lost:
    
        r1 = com.android.wm.shell.splitscreen.AppPairShortcutController.supportAppPairShortCut(r15, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:231:0x00c5, code lost:
    
        if (r1 != false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x00c7, code lost:
    
        r1 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x00c9, code lost:
    
        r1 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x00cd, code lost:
    
        if (r1 != false) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0348  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x02b7  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x02d7 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x034f  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0368  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x036f  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x03b8  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0462  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:83:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0484  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x041a  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0372  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0361  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateDividerPanel() {
        /*
            Method dump skipped, instructions count: 1211
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerPanel.updateDividerPanel():void");
    }
}
