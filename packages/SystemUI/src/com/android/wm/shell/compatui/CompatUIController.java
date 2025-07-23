package com.android.wm.shell.compatui;

import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.Pair;
import android.util.SparseArray;
import android.view.Display;
import android.view.InsetsSourceControl;
import android.view.InsetsState;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.ShellTaskOrganizer$$ExternalSyntheticLambda0;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.DockStateReader;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.compatui.api.CompatUIHandler;
import com.android.wm.shell.compatui.api.CompatUIInfo;
import com.android.wm.shell.compatui.coverlauncher.CoverLauncherAppCompatUIWindowManager;
import com.android.wm.shell.compatui.impl.CompatUIEvents;
import com.android.wm.shell.compatui.impl.CompatUIRequests;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.rune.CoreRune;
import dagger.Lazy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CompatUIController implements DisplayController.OnDisplaysChangedListener, DisplayImeController.ImePositionProcessor, KeyguardChangeListener, CompatUIHandler {
    public LetterboxEduWindowManager mActiveLetterboxEduLayout;
    public ReachabilityEduWindowManager mActiveReachabilityEduLayout;
    public ShellTaskOrganizer$$ExternalSyntheticLambda0 mCallback;
    public final CompatUIConfiguration mCompatUIConfiguration;
    public final CompatUIShellCommandHandler mCompatUIShellCommandHandler;
    public final CompatUIStatusManager mCompatUIStatusManager;
    public final Context mContext;
    public boolean mDeferVisibilityUpdate;
    public final DesktopState mDesktopState;
    public final Optional mDesktopUserRepositories;
    public final CompatUIController$$ExternalSyntheticLambda10 mDisappearTimeSupplier;
    public final DisplayController mDisplayController;
    public final DisplayInsetsController mDisplayInsetsController;
    public final DockStateReader mDockStateReader;
    public final CompatUIController$$ExternalSyntheticLambda9 mHandleFixedRotation;
    public final DisplayImeController mImeController;
    public boolean mIsFirstReachabilityEducationRunning;
    public boolean mIsInDesktopMode;
    public boolean mKeyguardShowing;
    public final ShellExecutor mMainExecutor;
    public final ShellController mShellController;
    public final SyncTransactionQueue mSyncQueue;
    public int mTopActivityTaskId;
    public final Lazy mTransitionsLazy;
    public UserAspectRatioSettingsWindowManager mUserAspectRatioSettingsLayout;
    public boolean mWaitingForFixedRotation;
    public final Set mDisplaysWithIme = new ArraySet(1);
    public final SparseArray mOnInsetsChangedListeners = new SparseArray(0);
    public final SparseArray mActiveCompatLayouts = new SparseArray(0);
    public final SparseArray mActiveMultiTaskingAppCompatLayouts = new SparseArray(0);
    public final SparseArray mActiveCoverLauncherAppCompatLayouts = new SparseArray(0);
    public final SparseArray mTaskIdToRestartDialogWindowManagerMap = new SparseArray(0);
    public final SparseArray mTaskIdToCompatUIInfoMap = new SparseArray(0);
    public final Set mSetOfTaskIdsShowingRestartDialog = new HashSet();
    public final SparseArray mDisplayContextCache = new SparseArray(0);
    public boolean mHasShownUserAspectRatioSettingsButton = false;
    public final CompatUIHintsState mCompatUIHintsState = new CompatUIHintsState();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class CompatUIHintsState {
        public boolean mHasShownSizeCompatHint;
        public boolean mHasShownUserAspectRatioSettingsButtonHint;
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PerDisplayOnInsetsChangedListener implements DisplayInsetsController.OnInsetsChangedListener {
        public final int mDisplayId;
        public final InsetsState mInsetsState = new InsetsState();

        public PerDisplayOnInsetsChangedListener(int i) {
            this.mDisplayId = i;
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsChanged(InsetsState insetsState) {
            if (this.mInsetsState.equals(insetsState)) {
                return;
            }
            this.mInsetsState.set(insetsState);
            CompatUIController compatUIController = CompatUIController.this;
            DisplayController displayController = compatUIController.mDisplayController;
            int i = this.mDisplayId;
            compatUIController.forAllLayouts(new CompatUIController$$ExternalSyntheticLambda18(i), new CompatUIController$$ExternalSyntheticLambda19(displayController.getDisplayLayout(i), 1));
        }

        @Override // com.android.wm.shell.common.DisplayInsetsController.OnInsetsChangedListener
        public final void insetsControlChanged(InsetsState insetsState, InsetsSourceControl[] insetsSourceControlArr) {
            insetsChanged(insetsState);
        }
    }

    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda9] */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda10] */
    public CompatUIController(Context context, ShellInit shellInit, ShellController shellController, DisplayController displayController, DisplayInsetsController displayInsetsController, DisplayImeController displayImeController, SyncTransactionQueue syncTransactionQueue, ShellExecutor shellExecutor, Lazy lazy, DockStateReader dockStateReader, CompatUIConfiguration compatUIConfiguration, CompatUIShellCommandHandler compatUIShellCommandHandler, final AccessibilityManager accessibilityManager, CompatUIStatusManager compatUIStatusManager, Optional<DesktopUserRepositories> optional, DesktopState desktopState) {
        final int i = 0;
        this.mHandleFixedRotation = new Runnable(this) { // from class: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda9
            public final /* synthetic */ CompatUIController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i2 = i;
                CompatUIController compatUIController = this.f$0;
                switch (i2) {
                    case 0:
                        compatUIController.mWaitingForFixedRotation = false;
                        if (compatUIController.mDeferVisibilityUpdate) {
                            compatUIController.mDeferVisibilityUpdate = false;
                            compatUIController.forAllLayouts(new CompatUIController$$ExternalSyntheticLambda6(1), new CompatUIController$$ExternalSyntheticLambda2(compatUIController, 2));
                            break;
                        }
                        break;
                    default:
                        compatUIController.mShellController.addKeyguardChangeListener(compatUIController);
                        compatUIController.mDisplayController.addDisplayWindowListener(compatUIController, -1);
                        compatUIController.mImeController.addPositionProcessor(compatUIController);
                        CompatUIShellCommandHandler compatUIShellCommandHandler2 = compatUIController.mCompatUIShellCommandHandler;
                        compatUIShellCommandHandler2.mShellCommandHandler.addCommandCallback("compatui", compatUIShellCommandHandler2, compatUIShellCommandHandler2);
                        break;
                }
            }
        };
        this.mContext = context;
        this.mShellController = shellController;
        this.mDisplayController = displayController;
        this.mDisplayInsetsController = displayInsetsController;
        this.mImeController = displayImeController;
        this.mSyncQueue = syncTransactionQueue;
        this.mMainExecutor = shellExecutor;
        this.mTransitionsLazy = lazy;
        this.mDockStateReader = dockStateReader;
        this.mCompatUIConfiguration = compatUIConfiguration;
        this.mCompatUIShellCommandHandler = compatUIShellCommandHandler;
        this.mDisappearTimeSupplier = new Function() { // from class: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Integer.valueOf(accessibilityManager.getRecommendedTimeoutMillis(5000, ((Integer) obj).intValue()));
            }
        };
        this.mCompatUIStatusManager = compatUIStatusManager;
        this.mDesktopUserRepositories = optional;
        this.mDesktopState = desktopState;
        final int i2 = 1;
        shellInit.addInitCallback(new Runnable(this) { // from class: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda9
            public final /* synthetic */ CompatUIController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i22 = i2;
                CompatUIController compatUIController = this.f$0;
                switch (i22) {
                    case 0:
                        compatUIController.mWaitingForFixedRotation = false;
                        if (compatUIController.mDeferVisibilityUpdate) {
                            compatUIController.mDeferVisibilityUpdate = false;
                            compatUIController.forAllLayouts(new CompatUIController$$ExternalSyntheticLambda6(1), new CompatUIController$$ExternalSyntheticLambda2(compatUIController, 2));
                            break;
                        }
                        break;
                    default:
                        compatUIController.mShellController.addKeyguardChangeListener(compatUIController);
                        compatUIController.mDisplayController.addDisplayWindowListener(compatUIController, -1);
                        compatUIController.mImeController.addPositionProcessor(compatUIController);
                        CompatUIShellCommandHandler compatUIShellCommandHandler2 = compatUIController.mCompatUIShellCommandHandler;
                        compatUIShellCommandHandler2.mShellCommandHandler.addCommandCallback("compatui", compatUIShellCommandHandler2, compatUIShellCommandHandler2);
                        break;
                }
            }
        }, this);
    }

    public static void launchUserAspectRatioSettings(Context context, TaskInfo taskInfo) {
        Intent intent = new Intent("android.settings.MANAGE_USER_ASPECT_RATIO_SETTINGS");
        intent.addFlags(268435456);
        intent.addFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID);
        ComponentName componentName = taskInfo.topActivity;
        if (componentName != null) {
            intent.setData(Uri.parse("package:" + componentName.getPackageName()));
        }
        context.startActivityAsUser(intent, UserHandle.of(taskInfo.userId));
    }

    public CompatUIWindowManager createCompatUiWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        ShellTaskOrganizer$$ExternalSyntheticLambda0 shellTaskOrganizer$$ExternalSyntheticLambda0 = this.mCallback;
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(taskInfo.displayId);
        CompatUIController$$ExternalSyntheticLambda2 compatUIController$$ExternalSyntheticLambda2 = new CompatUIController$$ExternalSyntheticLambda2(this, 1);
        return new CompatUIWindowManager(context, taskInfo, this.mSyncQueue, shellTaskOrganizer$$ExternalSyntheticLambda0, taskListener, displayLayout, this.mCompatUIHintsState, this.mCompatUIConfiguration, compatUIController$$ExternalSyntheticLambda2, this.mDesktopState);
    }

    public CoverLauncherAppCompatUIWindowManager createCoverLauncherAppCompatUiWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new CoverLauncherAppCompatUIWindowManager(context, taskInfo, this.mSyncQueue, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), this);
    }

    public LetterboxEduWindowManager createLetterboxEduWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new LetterboxEduWindowManager(context, taskInfo, this.mSyncQueue, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), (Transitions) this.mTransitionsLazy.get(), new CompatUIController$$ExternalSyntheticLambda2(this, 5), this.mDockStateReader, this.mCompatUIConfiguration, this.mCompatUIStatusManager);
    }

    public MultiTaskingAppCompatUIWindowManager createMultiTaskingAppCompatUiWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new MultiTaskingAppCompatUIWindowManager(context, taskInfo, this.mSyncQueue, this.mCallback, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), this);
    }

    public final void createOrUpdateReachabilityEduLayout(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        Context orCreateDisplayContext;
        ReachabilityEduWindowManager reachabilityEduWindowManager = this.mActiveReachabilityEduLayout;
        if (reachabilityEduWindowManager != null) {
            if (!reachabilityEduWindowManager.needsToBeRecreated(taskInfo, taskListener) && !this.mIsInDesktopMode) {
                ReachabilityEduWindowManager reachabilityEduWindowManager2 = this.mActiveReachabilityEduLayout;
                if (reachabilityEduWindowManager2.updateCompatInfo(taskInfo, taskListener, showOnDisplay(reachabilityEduWindowManager2.mDisplayId))) {
                    return;
                }
                this.mActiveReachabilityEduLayout.release();
                this.mActiveReachabilityEduLayout = null;
                return;
            }
            this.mActiveReachabilityEduLayout.release();
            this.mActiveReachabilityEduLayout = null;
        }
        if (this.mIsInDesktopMode || (orCreateDisplayContext = getOrCreateDisplayContext(taskInfo.displayId)) == null) {
            return;
        }
        ReachabilityEduWindowManager createReachabilityEduWindowManager = createReachabilityEduWindowManager(orCreateDisplayContext, taskInfo, taskListener);
        if (createReachabilityEduWindowManager.createLayout(showOnDisplay(taskInfo.displayId))) {
            ReachabilityEduWindowManager reachabilityEduWindowManager3 = this.mActiveReachabilityEduLayout;
            if (reachabilityEduWindowManager3 != null) {
                reachabilityEduWindowManager3.release();
            }
            this.mActiveReachabilityEduLayout = createReachabilityEduWindowManager;
        }
    }

    public final void createOrUpdateUserAspectRatioSettingsLayout(TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        Context orCreateDisplayContext;
        this.mDesktopState.getClass();
        UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager = this.mUserAspectRatioSettingsLayout;
        if (userAspectRatioSettingsWindowManager != null) {
            if (!userAspectRatioSettingsWindowManager.needsToBeRecreated(taskInfo, taskListener) && !this.mIsInDesktopMode) {
                UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager2 = this.mUserAspectRatioSettingsLayout;
                if (userAspectRatioSettingsWindowManager2.updateCompatInfo(taskInfo, taskListener, showOnDisplay(userAspectRatioSettingsWindowManager2.mDisplayId))) {
                    return;
                }
                this.mUserAspectRatioSettingsLayout.release();
                this.mUserAspectRatioSettingsLayout = null;
                return;
            }
            this.mUserAspectRatioSettingsLayout.release();
            this.mUserAspectRatioSettingsLayout = null;
        }
        if (this.mIsInDesktopMode || (orCreateDisplayContext = getOrCreateDisplayContext(taskInfo.displayId)) == null) {
            return;
        }
        UserAspectRatioSettingsWindowManager createUserAspectRatioSettingsWindowManager = createUserAspectRatioSettingsWindowManager(orCreateDisplayContext, taskInfo, taskListener);
        if (createUserAspectRatioSettingsWindowManager.createLayout(showOnDisplay(taskInfo.displayId))) {
            this.mUserAspectRatioSettingsLayout = createUserAspectRatioSettingsWindowManager;
        }
    }

    public ReachabilityEduWindowManager createReachabilityEduWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(taskInfo.displayId);
        CompatUIController$$ExternalSyntheticLambda0 compatUIController$$ExternalSyntheticLambda0 = new CompatUIController$$ExternalSyntheticLambda0(this, 1);
        return new ReachabilityEduWindowManager(context, taskInfo, this.mSyncQueue, taskListener, displayLayout, this.mCompatUIConfiguration, this.mMainExecutor, compatUIController$$ExternalSyntheticLambda0, this.mDisappearTimeSupplier);
    }

    public RestartDialogWindowManager createRestartDialogWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        return new RestartDialogWindowManager(context, taskInfo, this.mSyncQueue, taskListener, this.mDisplayController.getDisplayLayout(taskInfo.displayId), (Transitions) this.mTransitionsLazy.get(), new CompatUIController$$ExternalSyntheticLambda2(this, 3), new CompatUIController$$ExternalSyntheticLambda2(this, 4), this.mCompatUIConfiguration);
    }

    public UserAspectRatioSettingsWindowManager createUserAspectRatioSettingsWindowManager(Context context, TaskInfo taskInfo, ShellTaskOrganizer.TaskListener taskListener) {
        DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(taskInfo.displayId);
        int i = 0;
        CompatUIController$$ExternalSyntheticLambda0 compatUIController$$ExternalSyntheticLambda0 = new CompatUIController$$ExternalSyntheticLambda0(this, i);
        Supplier supplier = new Supplier() { // from class: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return Boolean.valueOf(CompatUIController.this.mHasShownUserAspectRatioSettingsButton);
            }
        };
        CompatUIController$$ExternalSyntheticLambda2 compatUIController$$ExternalSyntheticLambda2 = new CompatUIController$$ExternalSyntheticLambda2(this, i);
        return new UserAspectRatioSettingsWindowManager(context, taskInfo, this.mSyncQueue, taskListener, displayLayout, this.mCompatUIHintsState, compatUIController$$ExternalSyntheticLambda0, this.mMainExecutor, this.mDisappearTimeSupplier, supplier, compatUIController$$ExternalSyntheticLambda2);
    }

    public final void forAllLayouts(Predicate predicate, Consumer consumer) {
        for (int i = 0; i < this.mActiveCompatLayouts.size(); i++) {
            CompatUIWindowManager compatUIWindowManager = (CompatUIWindowManager) this.mActiveCompatLayouts.get(this.mActiveCompatLayouts.keyAt(i));
            if (compatUIWindowManager != null && predicate.test(compatUIWindowManager)) {
                consumer.accept(compatUIWindowManager);
            }
        }
        LetterboxEduWindowManager letterboxEduWindowManager = this.mActiveLetterboxEduLayout;
        if (letterboxEduWindowManager != null && predicate.test(letterboxEduWindowManager)) {
            consumer.accept(this.mActiveLetterboxEduLayout);
        }
        for (int i2 = 0; i2 < this.mActiveMultiTaskingAppCompatLayouts.size(); i2++) {
            MultiTaskingAppCompatUIWindowManager multiTaskingAppCompatUIWindowManager = (MultiTaskingAppCompatUIWindowManager) this.mActiveMultiTaskingAppCompatLayouts.get(this.mActiveMultiTaskingAppCompatLayouts.keyAt(i2));
            if (multiTaskingAppCompatUIWindowManager != null && predicate.test(multiTaskingAppCompatUIWindowManager)) {
                consumer.accept(multiTaskingAppCompatUIWindowManager);
            }
        }
        for (int i3 = 0; i3 < this.mTaskIdToRestartDialogWindowManagerMap.size(); i3++) {
            RestartDialogWindowManager restartDialogWindowManager = (RestartDialogWindowManager) this.mTaskIdToRestartDialogWindowManagerMap.get(this.mTaskIdToRestartDialogWindowManagerMap.keyAt(i3));
            if (restartDialogWindowManager != null && predicate.test(restartDialogWindowManager)) {
                consumer.accept(restartDialogWindowManager);
            }
        }
        ReachabilityEduWindowManager reachabilityEduWindowManager = this.mActiveReachabilityEduLayout;
        if (reachabilityEduWindowManager != null && predicate.test(reachabilityEduWindowManager)) {
            consumer.accept(this.mActiveReachabilityEduLayout);
        }
        UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager = this.mUserAspectRatioSettingsLayout;
        if (userAspectRatioSettingsWindowManager == null || !predicate.test(userAspectRatioSettingsWindowManager)) {
            return;
        }
        consumer.accept(this.mUserAspectRatioSettingsLayout);
    }

    public final Context getOrCreateDisplayContext(int i) {
        if (i == 0) {
            return this.mContext;
        }
        WeakReference weakReference = (WeakReference) this.mDisplayContextCache.get(i);
        Context context = weakReference != null ? (Context) weakReference.get() : null;
        if (context == null) {
            Display display = ((DisplayManager) this.mContext.getSystemService(DisplayManager.class)).getDisplay(i);
            if (display != null) {
                Context createDisplayContext = this.mContext.createDisplayContext(display);
                this.mDisplayContextCache.put(i, new WeakReference(createDisplayContext));
                return createDisplayContext;
            }
            ClockEventController$$ExternalSyntheticOutline0.m(i, "Cannot get context for display ", "CompatUIController");
        }
        return context;
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x02c1  */
    /* JADX WARN: Removed duplicated region for block: B:142:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01b1 A[ADDED_TO_REGION] */
    @Override // com.android.wm.shell.compatui.api.CompatUIHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCompatInfoChanged(com.android.wm.shell.compatui.api.CompatUIInfo r11) {
        /*
            Method dump skipped, instructions count: 750
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.compatui.CompatUIController.onCompatInfoChanged(com.android.wm.shell.compatui.api.CompatUIInfo):void");
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayAdded(int i) {
        PerDisplayOnInsetsChangedListener perDisplayOnInsetsChangedListener = new PerDisplayOnInsetsChangedListener(i);
        CompatUIController.this.mDisplayInsetsController.addInsetsChangedListener(perDisplayOnInsetsChangedListener.mDisplayId, perDisplayOnInsetsChangedListener);
        this.mOnInsetsChangedListeners.put(i, perDisplayOnInsetsChangedListener);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        forAllLayouts(new CompatUIController$$ExternalSyntheticLambda18(i), new CompatUIController$$ExternalSyntheticLambda19(this.mDisplayController.getDisplayLayout(i), 1));
        if (i == 0 && this.mWaitingForFixedRotation) {
            ((HandlerExecutor) this.mMainExecutor).removeCallbacks(this.mHandleFixedRotation);
            this.mWaitingForFixedRotation = false;
            if (this.mDeferVisibilityUpdate) {
                this.mDeferVisibilityUpdate = false;
                forAllLayouts(new CompatUIController$$ExternalSyntheticLambda6(1), new CompatUIController$$ExternalSyntheticLambda2(this, 2));
            }
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayRemoved(int i) {
        this.mDisplayContextCache.remove(i);
        PerDisplayOnInsetsChangedListener perDisplayOnInsetsChangedListener = (PerDisplayOnInsetsChangedListener) this.mOnInsetsChangedListeners.get(i);
        if (perDisplayOnInsetsChangedListener != null) {
            CompatUIController.this.mDisplayInsetsController.removeInsetsChangedListener(perDisplayOnInsetsChangedListener.mDisplayId, perDisplayOnInsetsChangedListener);
            this.mOnInsetsChangedListeners.remove(i);
        }
        ArrayList arrayList = new ArrayList();
        forAllLayouts(new CompatUIController$$ExternalSyntheticLambda18(i), new CompatUIController$$ExternalSyntheticLambda19(arrayList, 0));
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            removeLayouts(((Integer) arrayList.get(size)).intValue());
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onFixedRotationStarted(int i, int i2) {
        if (i == 0) {
            this.mWaitingForFixedRotation = true;
            HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
            CompatUIController$$ExternalSyntheticLambda9 compatUIController$$ExternalSyntheticLambda9 = this.mHandleFixedRotation;
            handlerExecutor.removeCallbacks(compatUIController$$ExternalSyntheticLambda9);
            handlerExecutor.executeDelayed(compatUIController$$ExternalSyntheticLambda9, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
        }
    }

    @Override // com.android.wm.shell.common.DisplayImeController.ImePositionProcessor
    public final void onImeVisibilityChanged(final int i, boolean z) {
        if (z) {
            ((ArraySet) this.mDisplaysWithIme).add(Integer.valueOf(i));
        } else {
            ((ArraySet) this.mDisplaysWithIme).remove(Integer.valueOf(i));
        }
        forAllLayouts(new CompatUIController$$ExternalSyntheticLambda18(i), new Consumer() { // from class: com.android.wm.shell.compatui.CompatUIController$$ExternalSyntheticLambda17
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CompatUIWindowManagerAbstract) obj).updateVisibility(CompatUIController.this.showOnDisplay(i));
            }
        });
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3) {
        this.mKeyguardShowing = z;
        if (!this.mWaitingForFixedRotation || z) {
            forAllLayouts(new CompatUIController$$ExternalSyntheticLambda6(2), new CompatUIController$$ExternalSyntheticLambda2(this, 7));
        } else {
            this.mDeferVisibilityUpdate = true;
            forAllLayouts(new CompatUIController$$ExternalSyntheticLambda6(0), new CompatUIController$$ExternalSyntheticLambda2(this, 6));
        }
    }

    public final void onRestartButtonClicked(Pair pair) {
        CompatUIConfiguration compatUIConfiguration = this.mCompatUIConfiguration;
        if (compatUIConfiguration.mIsRestartDialogOverrideEnabled || (compatUIConfiguration.mIsRestartDialogEnabled && compatUIConfiguration.mIsLetterboxRestartDialogAllowed)) {
            TaskInfo taskInfo = (TaskInfo) pair.first;
            SharedPreferences sharedPreferences = compatUIConfiguration.mCompatUISharedPreferences;
            int i = taskInfo.userId;
            if (!sharedPreferences.getBoolean(taskInfo.topActivity.getPackageName() + "@" + i, false)) {
                ((HashSet) this.mSetOfTaskIdsShowingRestartDialog).add(Integer.valueOf(((TaskInfo) pair.first).taskId));
                onCompatInfoChanged(new CompatUIInfo((TaskInfo) pair.first, (ShellTaskOrganizer.TaskListener) pair.second));
                return;
            }
        }
        this.mCallback.accept(new CompatUIEvents.SizeCompatRestartButtonClicked(((TaskInfo) pair.first).taskId));
    }

    public void removeLayouts(int i) {
        CoverLauncherAppCompatUIWindowManager coverLauncherAppCompatUIWindowManager;
        CompatUIWindowManager compatUIWindowManager = (CompatUIWindowManager) this.mActiveCompatLayouts.get(i);
        if (compatUIWindowManager != null) {
            compatUIWindowManager.release();
            this.mActiveCompatLayouts.remove(i);
        }
        LetterboxEduWindowManager letterboxEduWindowManager = this.mActiveLetterboxEduLayout;
        if (letterboxEduWindowManager != null && letterboxEduWindowManager.mTaskId == i) {
            letterboxEduWindowManager.release();
            this.mActiveLetterboxEduLayout = null;
        }
        MultiTaskingAppCompatUIWindowManager multiTaskingAppCompatUIWindowManager = (MultiTaskingAppCompatUIWindowManager) this.mActiveMultiTaskingAppCompatLayouts.get(i);
        if (multiTaskingAppCompatUIWindowManager != null) {
            multiTaskingAppCompatUIWindowManager.release();
            this.mActiveMultiTaskingAppCompatLayouts.remove(i);
        }
        if (CoreRune.FW_FLIP_FULL_COVER_SCREEN_APP_COMPAT_UI && (coverLauncherAppCompatUIWindowManager = (CoverLauncherAppCompatUIWindowManager) this.mActiveCoverLauncherAppCompatLayouts.get(i)) != null) {
            coverLauncherAppCompatUIWindowManager.release();
            this.mActiveCoverLauncherAppCompatLayouts.remove(i);
        }
        RestartDialogWindowManager restartDialogWindowManager = (RestartDialogWindowManager) this.mTaskIdToRestartDialogWindowManagerMap.get(i);
        if (restartDialogWindowManager != null) {
            restartDialogWindowManager.release();
            this.mTaskIdToRestartDialogWindowManagerMap.remove(i);
            ((HashSet) this.mSetOfTaskIdsShowingRestartDialog).remove(Integer.valueOf(i));
        }
        ReachabilityEduWindowManager reachabilityEduWindowManager = this.mActiveReachabilityEduLayout;
        if (reachabilityEduWindowManager != null && reachabilityEduWindowManager.mTaskId == i) {
            reachabilityEduWindowManager.release();
            this.mActiveReachabilityEduLayout = null;
        }
        UserAspectRatioSettingsWindowManager userAspectRatioSettingsWindowManager = this.mUserAspectRatioSettingsLayout;
        if (userAspectRatioSettingsWindowManager == null || userAspectRatioSettingsWindowManager.mTaskId != i) {
            return;
        }
        userAspectRatioSettingsWindowManager.release();
        this.mUserAspectRatioSettingsLayout = null;
    }

    @Override // com.android.wm.shell.compatui.api.CompatUIHandler
    public final void sendCompatUIRequest(CompatUIRequests.DisplayCompatShowRestartDialog displayCompatShowRestartDialog) {
        CompatUIInfo compatUIInfo;
        if (displayCompatShowRestartDialog.requestId == 0 && (compatUIInfo = (CompatUIInfo) this.mTaskIdToCompatUIInfoMap.get(displayCompatShowRestartDialog.taskId)) != null) {
            onRestartButtonClicked(new Pair(compatUIInfo.taskInfo, compatUIInfo.listener));
        }
    }

    @Override // com.android.wm.shell.compatui.api.CompatUIHandler
    public final void setCallback(ShellTaskOrganizer$$ExternalSyntheticLambda0 shellTaskOrganizer$$ExternalSyntheticLambda0) {
        this.mCallback = shellTaskOrganizer$$ExternalSyntheticLambda0;
    }

    public final boolean showOnDisplay(int i) {
        if (this.mKeyguardShowing) {
            return false;
        }
        return !((ArraySet) this.mDisplaysWithIme).contains(Integer.valueOf(i));
    }
}
