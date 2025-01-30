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
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Outline;
import android.graphics.Rect;
import android.os.Bundle;
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
import android.window.WindowContainerToken;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieDrawable$$ExternalSyntheticLambda6;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.splitscreen.AppPairShortcutController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DividerPanel implements View.OnClickListener, View.OnLongClickListener, View.OnTouchListener, View.OnHoverListener {
    public AlertDialog mAddToAppPairDialog;
    public AppPairShortcutController mAppPairShortcutController;
    public DividerPanelCallbacks mCallbacks;
    public final ContentResolver mContentResolver;
    public Context mContext;
    public final C38701 mDismissReceiver;

    /* renamed from: mH */
    public final HandlerC3872H f438mH;
    public final boolean mIsSystemUser;
    public SplitLayout mSplitLayout;
    public DividerPanelView mView;
    public final DividerPanelWindowManager mWindowManager;
    public boolean mIsLongPressOrHover = false;
    public final DividerPanel$$ExternalSyntheticLambda0 mRemoveRunnable = new Runnable() { // from class: com.android.wm.shell.common.split.DividerPanel$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            DividerPanel.this.removeDividerPanel();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DividerPanelCallbacks {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.common.split.DividerPanel$H */
    public final class HandlerC3872H extends Handler {
        public /* synthetic */ HandlerC3872H(DividerPanel dividerPanel, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            AlertDialog alertDialog = DividerPanel.this.mAddToAppPairDialog;
            if (alertDialog != null) {
                alertDialog.dismiss();
            }
            DividerPanel.this.removeDividerPanel();
        }

        private HandlerC3872H() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.wm.shell.common.split.DividerPanel$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.BroadcastReceiver, com.android.wm.shell.common.split.DividerPanel$1] */
    public DividerPanel(Context context) {
        this.mContext = new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.DayNight);
        this.mContentResolver = context.getContentResolver();
        this.mWindowManager = new DividerPanelWindowManager(context);
        HandlerC3872H handlerC3872H = new HandlerC3872H(this, 0 == true ? 1 : 0);
        this.f438mH = handlerC3872H;
        this.mIsSystemUser = ActivityManager.getCurrentUser() == 0;
        ?? r3 = new BroadcastReceiver() { // from class: com.android.wm.shell.common.split.DividerPanel.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                DividerPanel.this.f438mH.sendEmptyMessage(0);
            }
        };
        this.mDismissReceiver = r3;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        context.registerReceiver(r3, intentFilter, null, handlerC3872H, 2);
    }

    public final boolean isAddToEdgeEnable() {
        boolean z;
        if (!(!CoreRune.ONE_UI_5_1_1 ? Settings.Global.getInt(this.mContext.getContentResolver(), "edge_enable", 1) != 1 : Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "edge_enable", 0, -2) != 1)) {
            Slog.d("DividerPanel", "Edge disable");
            return false;
        }
        String stringForUser = Settings.System.getStringForUser(this.mContext.getContentResolver(), "cocktail_bar_enabled_cocktails", -2);
        String edgePanelProviderName = MultiWindowUtils.getEdgePanelProviderName();
        if (stringForUser != null) {
            for (String str : stringForUser.split(";")) {
                if (str.equals(edgePanelProviderName)) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (!z) {
            Slog.d("DividerPanel", "AppsEdge disable");
            return false;
        }
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "easy_mode_switch", 1, -2) == 0) {
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
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("supportTaskBar: ", z, "hasTaskBar: ", z2, " inSubDisplay: ");
        m69m.append(isInSubDisplay);
        Slog.d("DividerPanel", m69m.toString());
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
        if (componentName != null && (MultiWindowUtils.isAppsEdgeActivity(componentName) || componentName.getPackageName().equals("com.samsung.android.app.taskedge"))) {
            return false;
        }
        if (componentName2 != null) {
            return (MultiWindowUtils.isAppsEdgeActivity(componentName2) || componentName2.getPackageName().equals("com.samsung.android.app.taskedge")) ? false : true;
        }
        return true;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        StageCoordinator stageCoordinator = this.mSplitLayout.mStageCoordinator;
        if (view.getId() == com.android.systemui.R.id.rotating_icon) {
            if (stageCoordinator != null) {
                stageCoordinator.rotateMultiSplitWithTransition();
                if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
                    CoreSaLogger.logForAdvanced("1032", this.mSplitLayout.isVerticalDivision() ? "Horizontal split -> Vertical split" : "Vertical split -> Horizontal split");
                }
            }
        } else if (view.getId() == com.android.systemui.R.id.switching_icon) {
            if (stageCoordinator != null) {
                stageCoordinator.swapTasksInSplitScreenMode$1();
                if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
                    CoreSaLogger.logForAdvanced("1033");
                }
            }
        } else if (view.getId() == com.android.systemui.R.id.add_app_pair_icon) {
            final ArrayMap arrayMap = new ArrayMap();
            if (isAddToTaskBarEnable()) {
                arrayMap.put(0, this.mContext.getResources().getString(com.android.systemui.R.string.taskbar));
            }
            if (MultiWindowUtils.isDefaultLauncher(this.mContext)) {
                arrayMap.put(1, this.mContext.getResources().getString(com.android.systemui.R.string.home_screen));
            }
            if (isAddToEdgeEnable()) {
                arrayMap.put(2, this.mContext.getResources().getString(com.android.systemui.R.string.apps_edge_panel));
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
            builder.setTitle(com.android.systemui.R.string.add_app_pair_to);
            builder.setItems((CharSequence[]) arrayMap.values().toArray(new String[arrayMap.size()]), new DialogInterface.OnClickListener() { // from class: com.android.wm.shell.common.split.DividerPanel$$ExternalSyntheticLambda2
                @Override // android.content.DialogInterface.OnClickListener
                public final void onClick(DialogInterface dialogInterface, int i) {
                    DividerPanel.this.mAppPairShortcutController.createAppPairShortcut(((Integer) arrayMap.keyAt(i)).intValue());
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
        DividerPanelCallbacks dividerPanelCallbacks = this.mCallbacks;
        if (dividerPanelCallbacks != null) {
            SplitWindowManager splitWindowManager = (SplitWindowManager) dividerPanelCallbacks;
            if (splitWindowManager.mShowingFirstAutoOpenDividerPanel) {
                splitWindowManager.mIsFirstAutoOpenDividerPanel = false;
                SharedPreferences.Editor edit = splitWindowManager.mPref.edit();
                edit.putBoolean("divider_panel_first_auto_open", false);
                edit.apply();
                Slog.d("SplitWindowManager", "Exit DividerPanel first auto open");
            }
        }
        this.mCallbacks = null;
    }

    public final void scheduleRemoveDividerPanel() {
        this.f438mH.removeCallbacks(this.mRemoveRunnable);
        this.f438mH.postDelayed(this.mRemoveRunnable, 3000L);
    }

    /* JADX WARN: Code restructure failed: missing block: B:181:0x00b5, code lost:
    
        if (com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_FREE_POSITION != false) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x00bc, code lost:
    
        r10 = 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x00be, code lost:
    
        r10 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x00ba, code lost:
    
        if (com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_FREE_POSITION != false) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x01bb, code lost:
    
        r3 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x02f9  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x033b  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x03e5  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0434  */
    /* JADX WARN: Removed duplicated region for block: B:121:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x039a  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02fc  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0277  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x029e  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02aa  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x028f  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02bf  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x02f2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateDividerPanel() {
        boolean isSplitScreenFeasible;
        boolean z;
        final boolean z2;
        int childCount;
        int i;
        int i2;
        LottieDrawable lottieDrawable;
        boolean z3;
        boolean z4;
        Bundle bundle;
        WindowContainerToken windowContainerToken;
        removeDividerPanel();
        List list = null;
        DividerPanelView dividerPanelView = (DividerPanelView) LayoutInflater.from(this.mContext).inflate(com.android.systemui.R.layout.divider_panel, (ViewGroup) null);
        this.mView = dividerPanelView;
        dividerPanelView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.wm.shell.common.split.DividerPanel$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                DividerPanel dividerPanel = DividerPanel.this;
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
        lottieAnimationView.setOnClickListener(this);
        lottieAnimationView.setOnLongClickListener(this);
        lottieAnimationView.setOnTouchListener(this);
        lottieAnimationView.setOnHoverListener(this);
        lottieAnimationView2.setOnClickListener(this);
        lottieAnimationView2.setOnLongClickListener(this);
        lottieAnimationView2.setOnTouchListener(this);
        lottieAnimationView2.setOnHoverListener(this);
        lottieAnimationView3.setOnClickListener(this);
        lottieAnimationView3.setOnLongClickListener(this);
        lottieAnimationView3.setOnTouchListener(this);
        lottieAnimationView3.setOnHoverListener(this);
        boolean z5 = !CoreRune.MW_MULTI_SPLIT_FREE_POSITION ? this.mContext.getResources().getConfiguration().orientation != 1 : this.mSplitLayout.isVerticalDivision();
        StageCoordinator stageCoordinator = this.mSplitLayout.mStageCoordinator;
        if (stageCoordinator == null) {
            Slog.d("DividerPanel", "addDividerPanel, failed. StageCoordinator is null");
            return;
        }
        int splitCreateMode = stageCoordinator.getSplitCreateMode();
        final DividerPanelView dividerPanelView2 = this.mView;
        boolean isMultiSplitScreenVisible = stageCoordinator.isMultiSplitScreenVisible();
        char c = 4;
        if (CoreRune.MW_MULTI_SPLIT) {
            if (splitCreateMode != 2) {
                if (splitCreateMode != 3) {
                    if (splitCreateMode != 4) {
                        c = splitCreateMode != 5 ? (char) 65535 : (char) 2;
                    }
                }
            }
            isSplitScreenFeasible = this.mSplitLayout.isSplitScreenFeasible(c == 3 || c == 5);
        } else {
            isSplitScreenFeasible = false;
        }
        StageCoordinator stageCoordinator2 = this.mSplitLayout.mStageCoordinator;
        final boolean z6 = (stageCoordinator2 == null || stageCoordinator2.mCellDividerVisible) ? false : true;
        AppPairShortcutController appPairShortcutController = this.mAppPairShortcutController;
        Context context = this.mContext;
        SplitLayout splitLayout = appPairShortcutController.mSplitLayout;
        WindowContainerToken windowContainerToken2 = splitLayout.mWinToken1;
        if (windowContainerToken2 == null || splitLayout.mWinToken2 == null) {
            Slog.e("AppPairShortcutController", "isSupportAppPairPolicy: Can't find topActivity there is null");
        } else {
            ShellTaskOrganizer shellTaskOrganizer = splitLayout.mTaskOrganizer;
            List childTasks = shellTaskOrganizer.getChildTasks(windowContainerToken2, (int[]) null);
            List childTasks2 = shellTaskOrganizer.getChildTasks(splitLayout.mWinToken2, (int[]) null);
            if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && (windowContainerToken = splitLayout.mWinToken3) != null) {
                list = shellTaskOrganizer.getChildTasks(windowContainerToken, (int[]) null);
            }
            if (childTasks == null || childTasks.isEmpty() || childTasks2 == null || childTasks2.isEmpty()) {
                Log.e("AppPairShortcutController", "[isSupportAppPairPolicy] getChildTasks() is null or empty");
            } else {
                ArrayList arrayList = new ArrayList();
                arrayList.add((ActivityManager.RunningTaskInfo) childTasks.get(0));
                arrayList.add((ActivityManager.RunningTaskInfo) childTasks2.get(0));
                if (CoreRune.MW_MULTI_SPLIT_APP_PAIR && list != null && !list.isEmpty()) {
                    arrayList.add((ActivityManager.RunningTaskInfo) list.get(0));
                }
                HashMap hashMap = new HashMap();
                Iterator it = arrayList.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) it.next();
                    ComponentName componentName = runningTaskInfo.realActivity;
                    if (componentName == null) {
                        Log.w("AppPairShortcutController", "componentName is null");
                        break;
                    }
                    String packageName = componentName.getPackageName();
                    Iterator it2 = it;
                    HashSet hashSet = (HashSet) hashMap.get(Integer.valueOf(runningTaskInfo.userId));
                    if (hashSet == null) {
                        hashSet = new HashSet();
                        hashMap.put(Integer.valueOf(runningTaskInfo.userId), hashSet);
                    }
                    if (!hashSet.contains(packageName)) {
                        hashSet.add(packageName);
                        it = it2;
                    } else if (!MultiWindowUtils.isSingleInstancePerTask(context, packageName)) {
                        try {
                            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 128);
                            if (applicationInfo == null || (bundle = applicationInfo.metaData) == null) {
                                MotionLayout$$ExternalSyntheticOutline0.m23m("[Divider AppPair] appInfo or appInfo.metaData is null=", packageName, "AppPairShortcutController");
                            } else {
                                z3 = bundle.getBoolean("com.samsung.android.multiwindow.support.pair.shortcut", false);
                            }
                        } catch (PackageManager.NameNotFoundException unused) {
                            MotionLayout$$ExternalSyntheticOutline0.m23m("[Divider AppPair] No such package=", packageName, "AppPairShortcutController");
                        }
                    }
                }
                z3 = false;
                if (z3) {
                    Iterator it3 = arrayList.iterator();
                    while (it3.hasNext()) {
                        ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) it3.next();
                        ComponentName componentName2 = runningTaskInfo2.realActivity;
                        if (componentName2 == null) {
                            Log.w("AppPairShortcutController", "componentName is null");
                        } else {
                            ActivityInfo activityInfo = runningTaskInfo2.topActivityInfo;
                            if (activityInfo == null || activityInfo.applicationInfo.uid != 1001) {
                                String packageName2 = componentName2.getPackageName();
                                int i3 = runningTaskInfo2.userId;
                                Intent launchIntentForPackageAsUser = MultiWindowUtils.getLaunchIntentForPackageAsUser(packageName2, i3);
                                if (launchIntentForPackageAsUser == null || launchIntentForPackageAsUser.getComponent() == null) {
                                    Log.w("AppPairShortcutController", "getLaunchIntentForPackageAsUser is null or empty component (" + packageName2 + "," + i3 + ")");
                                }
                            }
                        }
                        z4 = false;
                    }
                    z4 = true;
                    if (z4) {
                        z = true;
                        z2 = !z && (isAddToTaskBarEnable() || MultiWindowUtils.isDefaultLauncher(this.mContext) || isAddToEdgeEnable());
                        dividerPanelView2.getClass();
                        if (CoreRune.MW_MULTI_SPLIT_DIVIDER && isSplitScreenFeasible) {
                            dividerPanelView2.mRotatingIcon.setVisibility(0);
                            if (isMultiSplitScreenVisible) {
                                if (z5) {
                                    i2 = 50;
                                    int i4 = i2 + 30;
                                    lottieDrawable = dividerPanelView2.mRotatingIcon.lottieDrawable;
                                    if (lottieDrawable.composition == null) {
                                    }
                                    dividerPanelView2.mRotatingIcon.lottieDrawable.setMaxFrame(i4);
                                }
                                i2 = 0;
                                int i42 = i2 + 30;
                                lottieDrawable = dividerPanelView2.mRotatingIcon.lottieDrawable;
                                if (lottieDrawable.composition == null) {
                                }
                                dividerPanelView2.mRotatingIcon.lottieDrawable.setMaxFrame(i42);
                            } else {
                                if (splitCreateMode == 4) {
                                    i2 = 100;
                                } else if (splitCreateMode == 5) {
                                    i2 = 150;
                                } else if (splitCreateMode == 2) {
                                    i2 = 200;
                                } else {
                                    if (splitCreateMode == 3) {
                                        i2 = IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend;
                                    }
                                    i2 = 0;
                                }
                                int i422 = i2 + 30;
                                lottieDrawable = dividerPanelView2.mRotatingIcon.lottieDrawable;
                                if (lottieDrawable.composition == null) {
                                    lottieDrawable.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda6(lottieDrawable, i2, 1));
                                } else {
                                    lottieDrawable.animator.setMinAndMaxFrames(i2, (int) r7.maxFrame);
                                }
                                dividerPanelView2.mRotatingIcon.lottieDrawable.setMaxFrame(i422);
                            }
                        }
                        if (z6) {
                            dividerPanelView2.mSwitchingIcon.setVisibility(8);
                        } else {
                            int i5 = z5 ? 50 : 0;
                            int i6 = i5 + 33;
                            LottieDrawable lottieDrawable2 = dividerPanelView2.mSwitchingIcon.lottieDrawable;
                            if (lottieDrawable2.composition == null) {
                                lottieDrawable2.lazyCompositionTasks.add(new LottieDrawable$$ExternalSyntheticLambda6(lottieDrawable2, i5, 1));
                            } else {
                                lottieDrawable2.animator.setMinAndMaxFrames(i5, (int) r7.maxFrame);
                            }
                            dividerPanelView2.mSwitchingIcon.lottieDrawable.setMaxFrame(i6);
                        }
                        if (!z2) {
                            dividerPanelView2.mAddAppPairIcon.setVisibility(8);
                        }
                        final LottieAnimationView lottieAnimationView4 = !isSplitScreenFeasible ? dividerPanelView2.mRotatingIcon : z6 ? dividerPanelView2.mSwitchingIcon : z2 ? dividerPanelView2.mAddAppPairIcon : null;
                        dividerPanelView2.mHandler.postDelayed(new Runnable() { // from class: com.android.wm.shell.common.split.DividerPanelView$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                LottieAnimationView lottieAnimationView5 = LottieAnimationView.this;
                                int i7 = DividerPanelView.$r8$clinit;
                                if (lottieAnimationView5 != null) {
                                    lottieAnimationView5.playAnimation();
                                }
                            }
                        }, 150L);
                        dividerPanelView2.mRotatingIcon.lottieDrawable.animator.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.1
                            public final /* synthetic */ boolean val$canAddToAppPair;
                            public final /* synthetic */ boolean val$canSwapTask;

                            public C38731(final boolean z62, final boolean z22) {
                                r2 = z62;
                                r3 = z22;
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                if (r2) {
                                    DividerPanelView.this.mSwitchingIcon.playAnimation();
                                } else if (r3) {
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
                        dividerPanelView2.mSwitchingIcon.lottieDrawable.animator.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.2
                            public final /* synthetic */ boolean val$canAddToAppPair;

                            public C38742(final boolean z22) {
                                r2 = z22;
                            }

                            @Override // android.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator) {
                                if (r2) {
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
                        int i7 = 0;
                        for (i = 0; i < childCount; i++) {
                            if (dividerPanelView3.mContainer.getChildAt(i).getVisibility() == 0) {
                                i7++;
                            }
                        }
                        final int dimensionPixelSize = (dividerPanelView3.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_button_size) * i7) + (dividerPanelView3.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_rounded_corner_width) * 2);
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
                            rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
                        } else {
                            displayLayout.getStableBounds(rect, true);
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
                        int i8 = dividerView.mSplitLayout.mDividerSize;
                        dividerPanelWindowManager.mLp.gravity = 51;
                        if (z5) {
                            int height = (dividerPanelWindowManager.mDividerView.getHeight() / 2) + dividerView.getTop();
                            WindowManager.LayoutParams layoutParams3 = dividerPanelWindowManager.mLp;
                            layoutParams3.x = (i8 / 2) + (currentPosition - (dimensionPixelSize / 2));
                            layoutParams3.y = (rect.top + height) - (dimensionPixelSize2 / 2);
                        } else {
                            int width = (dividerPanelWindowManager.mDividerView.getWidth() / 2) + dividerView.getLeft();
                            WindowManager.LayoutParams layoutParams4 = dividerPanelWindowManager.mLp;
                            layoutParams4.x = (rect.left + width) - (dimensionPixelSize / 2);
                            layoutParams4.y = (i8 / 2) + (currentPosition - (dimensionPixelSize2 / 2));
                        }
                        dividerPanelWindowManager.mWm.addView(dividerPanelView4, dividerPanelWindowManager.mLp);
                        dividerPanelWindowManager.mView = dividerPanelView4;
                        scheduleRemoveDividerPanel();
                        if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
                            return;
                        }
                        CoreSaLogger.logForAdvanced("1031");
                        return;
                    }
                    Log.i("AppPairShortcutController", "[isSupportAppPairPolicy] isSupportAppPairType returns false. " + arrayList);
                } else {
                    Log.i("AppPairShortcutController", "[isSupportAppPairPolicy] isSupportAppPairForMultiInstance returns false. " + arrayList);
                }
            }
        }
        z = false;
        if (z) {
        }
        dividerPanelView2.getClass();
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
            dividerPanelView2.mRotatingIcon.setVisibility(0);
            if (isMultiSplitScreenVisible) {
            }
        }
        if (z62) {
        }
        if (!z22) {
        }
        if (!isSplitScreenFeasible) {
        }
        dividerPanelView2.mHandler.postDelayed(new Runnable() { // from class: com.android.wm.shell.common.split.DividerPanelView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                LottieAnimationView lottieAnimationView5 = LottieAnimationView.this;
                int i72 = DividerPanelView.$r8$clinit;
                if (lottieAnimationView5 != null) {
                    lottieAnimationView5.playAnimation();
                }
            }
        }, 150L);
        dividerPanelView2.mRotatingIcon.lottieDrawable.animator.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.1
            public final /* synthetic */ boolean val$canAddToAppPair;
            public final /* synthetic */ boolean val$canSwapTask;

            public C38731(final boolean z62, final boolean z22) {
                r2 = z62;
                r3 = z22;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (r2) {
                    DividerPanelView.this.mSwitchingIcon.playAnimation();
                } else if (r3) {
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
        dividerPanelView2.mSwitchingIcon.lottieDrawable.animator.addListener(new Animator.AnimatorListener() { // from class: com.android.wm.shell.common.split.DividerPanelView.2
            public final /* synthetic */ boolean val$canAddToAppPair;

            public C38742(final boolean z22) {
                r2 = z22;
            }

            @Override // android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                if (r2) {
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
        int i72 = 0;
        while (i < childCount) {
        }
        final int dimensionPixelSize3 = (dividerPanelView32.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_button_size) * i72) + (dividerPanelView32.mContext.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mw_divider_panel_rounded_corner_width) * 2);
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
        int i82 = dividerView2.mSplitLayout.mDividerSize;
        dividerPanelWindowManager2.mLp.gravity = 51;
        if (z5) {
        }
        dividerPanelWindowManager2.mWm.addView(dividerPanelView42, dividerPanelWindowManager2.mLp);
        dividerPanelWindowManager2.mView = dividerPanelView42;
        scheduleRemoveDividerPanel();
        if (CoreRune.MW_SPLIT_DIVIDER_SA_LOGGING) {
        }
    }
}
