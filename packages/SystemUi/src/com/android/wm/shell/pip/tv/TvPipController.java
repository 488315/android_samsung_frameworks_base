package com.android.wm.shell.pip.tv;

import android.app.ActivityTaskManager;
import android.app.TaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.session.MediaSessionManager;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserHandle;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArraySet;
import android.view.Gravity;
import com.android.wm.shell.WindowManagerShellWrapper;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipAppOpsListener;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipMediaController;
import com.android.wm.shell.pip.PipMediaController$$ExternalSyntheticLambda0;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.tv.TvPipAction;
import com.android.wm.shell.pip.tv.TvPipBoundsController;
import com.android.wm.shell.pip.tv.TvPipMenuController;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.sysui.ConfigurationChangeListener;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.UserChangeListener;
import com.android.systemui.R;
import com.sec.ims.configuration.DATA;
import java.util.ArrayList;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TvPipController implements PipTransitionController.PipTransitionCallback, TvPipBoundsController.PipBoundsListener, TvPipMenuController.Delegate, DisplayController.OnDisplaysChangedListener, ConfigurationChangeListener, UserChangeListener {
    public final ActionBroadcastReceiver mActionBroadcastReceiver;
    public final PipAppOpsListener mAppOpsListener;
    public final Context mContext;
    public final DisplayController mDisplayController;
    public int mEduTextWindowExitAnimationDuration;
    public final TvPipImpl mImpl;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public int mPipForceCloseDelay;
    public final PipMediaController mPipMediaController;
    public final TvPipNotificationController mPipNotificationController;
    public final PipParamsChangedForwarder mPipParamsChangedForwarder;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipTransitionController mPipTransitionController;
    public int mResizeAnimationDuration;
    public final ShellController mShellController;
    public final TaskStackListenerImpl mTaskStackListener;
    public final TvPipActionsProvider mTvPipActionsProvider;
    public final TvPipBoundsAlgorithm mTvPipBoundsAlgorithm;
    public final TvPipBoundsController mTvPipBoundsController;
    public final TvPipBoundsState mTvPipBoundsState;
    public final TvPipMenuController mTvPipMenuController;
    public final WindowManagerShellWrapper mWmShellWrapper;
    public int mState = 0;
    public int mPinnedTaskId = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActionBroadcastReceiver extends BroadcastReceiver {
        public final IntentFilter mIntentFilter;
        public boolean mRegistered;

        public /* synthetic */ ActionBroadcastReceiver(TvPipController tvPipController, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int i = 0;
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1181410214, 0, "%s: on(Broadcast)Receive(), action=%s", "TvPipController", String.valueOf(action));
            }
            if ("com.android.wm.shell.pip.tv.notification.action.SHOW_PIP_MENU".equals(action)) {
                TvPipController.this.showPictureInPictureMenu(false);
                return;
            }
            TvPipController tvPipController = TvPipController.this;
            if ("com.android.wm.shell.pip.tv.notification.action.CLOSE_PIP".equals(action)) {
                i = 1;
            } else if ("com.android.wm.shell.pip.tv.notification.action.MOVE_PIP".equals(action)) {
                i = 2;
            } else if ("com.android.wm.shell.pip.tv.notification.action.TOGGLE_EXPANDED_PIP".equals(action)) {
                i = 3;
            } else if (!"com.android.wm.shell.pip.tv.notification.action.FULLSCREEN".equals(action)) {
                i = 4;
            }
            tvPipController.executeAction(i);
        }

        private ActionBroadcastReceiver() {
            IntentFilter intentFilter = new IntentFilter();
            this.mIntentFilter = intentFilter;
            intentFilter.addAction("com.android.wm.shell.pip.tv.notification.action.CLOSE_PIP");
            intentFilter.addAction("com.android.wm.shell.pip.tv.notification.action.SHOW_PIP_MENU");
            intentFilter.addAction("com.android.wm.shell.pip.tv.notification.action.MOVE_PIP");
            intentFilter.addAction("com.android.wm.shell.pip.tv.notification.action.TOGGLE_EXPANDED_PIP");
            intentFilter.addAction("com.android.wm.shell.pip.tv.notification.action.FULLSCREEN");
            this.mRegistered = false;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TvPipImpl implements Pip {
        public /* synthetic */ TvPipImpl(TvPipController tvPipController, int i) {
            this(tvPipController);
        }

        private TvPipImpl(TvPipController tvPipController) {
        }
    }

    private TvPipController(Context context, ShellInit shellInit, ShellController shellController, TvPipBoundsState tvPipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, TvPipBoundsAlgorithm tvPipBoundsAlgorithm, TvPipBoundsController tvPipBoundsController, PipAppOpsListener pipAppOpsListener, PipTaskOrganizer pipTaskOrganizer, PipTransitionController pipTransitionController, TvPipMenuController tvPipMenuController, PipMediaController pipMediaController, TvPipNotificationController tvPipNotificationController, TaskStackListenerImpl taskStackListenerImpl, PipParamsChangedForwarder pipParamsChangedForwarder, DisplayController displayController, WindowManagerShellWrapper windowManagerShellWrapper, Handler handler, ShellExecutor shellExecutor) {
        int i = 0;
        this.mImpl = new TvPipImpl(this, i);
        this.mContext = context;
        this.mMainHandler = handler;
        this.mMainExecutor = shellExecutor;
        this.mShellController = shellController;
        this.mDisplayController = displayController;
        DisplayLayout displayLayout = new DisplayLayout(context, context.getDisplay());
        this.mTvPipBoundsState = tvPipBoundsState;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        pipDisplayLayoutState.mDisplayLayout.set(displayLayout);
        pipDisplayLayoutState.mDisplayId = context.getDisplayId();
        this.mTvPipBoundsAlgorithm = tvPipBoundsAlgorithm;
        this.mTvPipBoundsController = tvPipBoundsController;
        tvPipBoundsController.mListener = this;
        this.mPipMediaController = pipMediaController;
        TvPipActionsProvider tvPipActionsProvider = new TvPipActionsProvider(context, pipMediaController, new TvPipAction.SystemActionsHandler() { // from class: com.android.wm.shell.pip.tv.TvPipController$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.pip.tv.TvPipAction.SystemActionsHandler
            public final void executeAction(int i2) {
                TvPipController.this.executeAction(i2);
            }
        });
        this.mTvPipActionsProvider = tvPipActionsProvider;
        this.mPipNotificationController = tvPipNotificationController;
        tvPipNotificationController.mTvPipActionsProvider = tvPipActionsProvider;
        ArrayList arrayList = (ArrayList) tvPipActionsProvider.mListeners;
        if (!arrayList.contains(tvPipNotificationController)) {
            arrayList.add(tvPipNotificationController);
        }
        this.mTvPipMenuController = tvPipMenuController;
        tvPipMenuController.getClass();
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -667275149, 0, "%s: setDelegate(), delegate=%s", "TvPipMenuController", String.valueOf(this));
        }
        if (tvPipMenuController.mDelegate != null) {
            throw new IllegalStateException("The delegate has already been set and should not change.");
        }
        tvPipMenuController.mDelegate = this;
        tvPipMenuController.mTvPipActionsProvider = tvPipActionsProvider;
        this.mActionBroadcastReceiver = new ActionBroadcastReceiver(this, i);
        this.mAppOpsListener = pipAppOpsListener;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mPipTransitionController = pipTransitionController;
        this.mPipParamsChangedForwarder = pipParamsChangedForwarder;
        this.mTaskStackListener = taskStackListenerImpl;
        this.mWmShellWrapper = windowManagerShellWrapper;
        shellInit.addInitCallback(new TvPipController$$ExternalSyntheticLambda1(this, i), this);
    }

    public static TvPipImpl create(Context context, ShellInit shellInit, ShellController shellController, TvPipBoundsState tvPipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, TvPipBoundsAlgorithm tvPipBoundsAlgorithm, TvPipBoundsController tvPipBoundsController, PipAppOpsListener pipAppOpsListener, PipTaskOrganizer pipTaskOrganizer, PipTransitionController pipTransitionController, TvPipMenuController tvPipMenuController, PipMediaController pipMediaController, TvPipNotificationController tvPipNotificationController, TaskStackListenerImpl taskStackListenerImpl, PipParamsChangedForwarder pipParamsChangedForwarder, DisplayController displayController, WindowManagerShellWrapper windowManagerShellWrapper, Handler handler, ShellExecutor shellExecutor) {
        return new TvPipController(context, shellInit, shellController, tvPipBoundsState, pipDisplayLayoutState, tvPipBoundsAlgorithm, tvPipBoundsController, pipAppOpsListener, pipTaskOrganizer, pipTransitionController, tvPipMenuController, pipMediaController, tvPipNotificationController, taskStackListenerImpl, pipParamsChangedForwarder, displayController, windowManagerShellWrapper, handler, shellExecutor).mImpl;
    }

    public static TaskInfo getPinnedTaskInfo() {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1615202766, 0, "%s: getPinnedTaskInfo()", "TvPipController");
        }
        try {
            ActivityTaskManager.RootTaskInfo rootTaskInfo = ActivityTaskManager.getService().getRootTaskInfo(2, 0);
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1194212228, 0, "%s: taskInfo=%s", "TvPipController", String.valueOf(rootTaskInfo));
            }
            return rootTaskInfo;
        } catch (RemoteException e) {
            if (!ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                return null;
            }
            ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1679295386, 0, "%s: getRootTaskInfo() failed, %s", "TvPipController", String.valueOf(e));
            return null;
        }
    }

    public static String stateToName(int i) {
        if (i == 0) {
            return "NO_PIP";
        }
        if (i == 1) {
            return DATA.DM_NODE.PIP;
        }
        if (i == 2) {
            return "PIP_MENU";
        }
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown state ", i));
    }

    public final void closeCurrentPiP(int i) {
        if (this.mPinnedTaskId == i) {
            this.mPipTaskOrganizer.removePip();
            onPipDisappeared();
        } else if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1170726109, 0, "%s: PiP has already been closed by custom close action", "TvPipController");
        }
    }

    public final void executeAction(int i) {
        if (i == 0) {
            movePipToFullscreen();
            return;
        }
        int i2 = 1;
        if (i == 1) {
            closeCurrentPiP(this.mPinnedTaskId);
            return;
        }
        if (i == 2) {
            showPictureInPictureMenu(true);
            return;
        }
        if (i != 3) {
            if (i != 5) {
                return;
            }
            ((HandlerExecutor) this.mMainExecutor).executeDelayed(this.mPipForceCloseDelay, new TvPipController$$ExternalSyntheticLambda1(this, i2));
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1575272293, 0, "%s: togglePipExpansion()", "TvPipController");
        }
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        boolean z = true ^ tvPipBoundsState.mIsTvPipExpanded;
        this.mTvPipBoundsAlgorithm.updateGravityOnExpansionToggled(z);
        tvPipBoundsState.mTvPipManuallyCollapsed = !z;
        tvPipBoundsState.mIsTvPipExpanded = z;
        updatePinnedStackBounds();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x008c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void movePip(int i) {
        boolean z;
        int i2;
        TvPipBoundsAlgorithm tvPipBoundsAlgorithm = this.mTvPipBoundsAlgorithm;
        tvPipBoundsAlgorithm.getClass();
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 680378294, 4, "%s: updateGravity, keycode: %d", "TvPipBoundsAlgorithm", Long.valueOf(i));
        }
        TvPipBoundsState tvPipBoundsState = tvPipBoundsAlgorithm.mTvPipBoundsState;
        if (!tvPipBoundsState.mIsTvPipExpanded || (((i2 = tvPipBoundsState.mTvFixedPipOrientation) != 1 || (i != 19 && i != 20)) && (i2 != 2 || (i != 22 && i != 21)))) {
            int i3 = tvPipBoundsState.mTvPipGravity;
            int i4 = i3 & 7;
            int i5 = i3 & 112;
            switch (i) {
                case 19:
                    i5 = 48;
                    break;
                case 20:
                    i5 = 80;
                    break;
                case 21:
                    i4 = 3;
                    break;
                case 22:
                    i4 = 5;
                    break;
            }
            int i6 = i4 | i5;
            if (i6 != i3) {
                tvPipBoundsState.mTvPipGravity = i6;
                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 655169607, 0, "%s: updateGravity, new gravity: %s", "TvPipBoundsAlgorithm", String.valueOf(Gravity.toString(i6)));
                }
                z = true;
                if (z) {
                    if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                        ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1814468056, 0, "%s: Position hasn't changed", "TvPipController");
                        return;
                    }
                    return;
                } else {
                    int i7 = this.mTvPipBoundsState.mTvPipGravity;
                    TvPipMenuView tvPipMenuView = this.mTvPipMenuController.mPipMenuView;
                    tvPipMenuView.mCurrentPipGravity = i7;
                    if (tvPipMenuView.mCurrentMenuMode == 1) {
                        tvPipMenuView.showMovementHints();
                    }
                    updatePinnedStackBounds();
                    return;
                }
            }
        }
        z = false;
        if (z) {
        }
    }

    public final void movePipToFullscreen() {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -267678729, 0, "%s: movePipToFullscreen(), state=%s", "TvPipController", stateToName(this.mState));
        }
        this.mPipTaskOrganizer.exitPip(this.mResizeAnimationDuration, false);
        onPipDisappeared();
    }

    @Override // com.android.wm.shell.sysui.ConfigurationChangeListener
    public final void onConfigurationChanged(Configuration configuration) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 190882132, 0, "%s: onConfigurationChanged(), state=%s", "TvPipController", stateToName(this.mState));
        }
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        int i = tvPipBoundsState.mDefaultGravity & 7;
        reloadResources();
        TvPipNotificationController tvPipNotificationController = this.mPipNotificationController;
        tvPipNotificationController.mDefaultTitle = tvPipNotificationController.mContext.getResources().getString(R.string.pip_notification_unknown_title);
        tvPipNotificationController.updateNotificationContent();
        this.mTvPipBoundsAlgorithm.onConfigurationChanged(this.mContext);
        tvPipBoundsState.updateDefaultGravity();
        int i2 = tvPipBoundsState.mDefaultGravity & 7;
        if (!(this.mState != 0) || i == i2) {
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m231i(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 599056174, "%s: movePipToOppositeSide", "TvPipController");
        }
        int i3 = tvPipBoundsState.mTvPipGravity;
        if ((i3 & 5) == 5) {
            movePip(21);
        } else if ((i3 & 3) == 3) {
            movePip(22);
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onKeepClearAreasChanged(int i, Set set, Set set2) {
        if (this.mPipDisplayLayoutState.mDisplayId == i) {
            TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
            boolean z = !set2.equals(tvPipBoundsState.getUnrestrictedKeepClearAreas());
            ArraySet arraySet = (ArraySet) tvPipBoundsState.mRestrictedKeepClearAreas;
            arraySet.clear();
            arraySet.addAll(set);
            ArraySet arraySet2 = (ArraySet) tvPipBoundsState.mUnrestrictedKeepClearAreas;
            arraySet2.clear();
            arraySet2.addAll(set2);
            updatePinnedStackBounds(this.mResizeAnimationDuration, z);
        }
    }

    public final void onPipDisappeared() {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1662796069, 0, "%s: onPipDisappeared() state=%s", "TvPipController", stateToName(this.mState));
        }
        TvPipNotificationController tvPipNotificationController = this.mPipNotificationController;
        tvPipNotificationController.getClass();
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -858212510, 0, "%s: dismiss()", "TvPipNotificationController");
        }
        tvPipNotificationController.mIsNotificationShown = false;
        tvPipNotificationController.mPackageName = null;
        tvPipNotificationController.mNotificationManager.cancel("TvPip", 1100);
        ActionBroadcastReceiver actionBroadcastReceiver = this.mActionBroadcastReceiver;
        if (actionBroadcastReceiver.mRegistered) {
            TvPipController.this.mContext.unregisterReceiver(actionBroadcastReceiver);
            actionBroadcastReceiver.mRegistered = false;
        }
        this.mTvPipMenuController.closeMenu();
        TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
        tvPipBoundsState.mTvFixedPipOrientation = 0;
        int i = tvPipBoundsState.mDefaultGravity;
        tvPipBoundsState.mTvPipGravity = i;
        tvPipBoundsState.mPreviousCollapsedGravity = i;
        tvPipBoundsState.mTvPipManuallyCollapsed = false;
        TvPipBoundsController tvPipBoundsController = this.mTvPipBoundsController;
        tvPipBoundsController.mCurrentPlacementBounds = null;
        tvPipBoundsController.mPipTargetBounds = null;
        tvPipBoundsController.cancelScheduledPlacement();
        setState(0);
        this.mPinnedTaskId = -1;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
    public final void onPipTransitionCanceled(int i) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1034258818, 0, "%s: onPipTransition_Canceled(), state=%s", "TvPipController", stateToName(this.mState));
        }
        boolean isInPipDirection = PipAnimationController.isInPipDirection(i);
        TvPipMenuController tvPipMenuController = this.mTvPipMenuController;
        tvPipMenuController.getClass();
        tvPipMenuController.mMainHandler.post(new TvPipMenuController$$ExternalSyntheticLambda0(tvPipMenuController, isInPipDirection));
        this.mTvPipActionsProvider.updatePipExpansionState(this.mTvPipBoundsState.mIsTvPipExpanded);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
    public final void onPipTransitionFinished(int i) {
        boolean isInPipDirection = PipAnimationController.isInPipDirection(i);
        if (isInPipDirection && this.mState == 0) {
            setState(1);
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -387812468, 16, "%s: onPipTransition_Finished(), state=%s, direction=%d", "TvPipController", stateToName(this.mState), Long.valueOf(i));
        }
        TvPipMenuController tvPipMenuController = this.mTvPipMenuController;
        tvPipMenuController.getClass();
        tvPipMenuController.mMainHandler.post(new TvPipMenuController$$ExternalSyntheticLambda0(tvPipMenuController, isInPipDirection));
        this.mTvPipActionsProvider.updatePipExpansionState(this.mTvPipBoundsState.mIsTvPipExpanded);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
    public final void onPipTransitionStarted(int i, Rect rect) {
        if (PipAnimationController.isInPipDirection(i) && this.mState == 0) {
            TvPipBoundsState tvPipBoundsState = this.mTvPipBoundsState;
            this.mTvPipActionsProvider.updateExpansionEnabled(tvPipBoundsState.mIsTvExpandedPipSupported && tvPipBoundsState.mDesiredTvExpandedAspectRatio != 0.0f);
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1831687941, 16, "%s: onPipTransition_Started(), state=%s, direction=%d", "TvPipController", stateToName(this.mState), Long.valueOf(i));
        }
    }

    @Override // com.android.wm.shell.sysui.UserChangeListener
    public final void onUserChanged$1(int i) {
        PipMediaController pipMediaController = this.mPipMediaController;
        MediaSessionManager mediaSessionManager = pipMediaController.mMediaSessionManager;
        PipMediaController$$ExternalSyntheticLambda0 pipMediaController$$ExternalSyntheticLambda0 = pipMediaController.mSessionsChangedListener;
        mediaSessionManager.removeOnActiveSessionsChangedListener(pipMediaController$$ExternalSyntheticLambda0);
        mediaSessionManager.addOnActiveSessionsChangedListener(null, UserHandle.CURRENT, pipMediaController.mHandlerExecutor, pipMediaController$$ExternalSyntheticLambda0);
    }

    public final void reloadResources() {
        Resources resources = this.mContext.getResources();
        this.mResizeAnimationDuration = resources.getInteger(R.integer.config_pipResizeAnimationDuration);
        this.mPipForceCloseDelay = resources.getInteger(R.integer.config_pipForceCloseDelay);
        this.mEduTextWindowExitAnimationDuration = resources.getInteger(R.integer.pip_edu_text_window_exit_animation_duration);
    }

    public final void setState(int i) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1495057074, 0, "%s: setState(), state=%s, prev=%s", "TvPipController", stateToName(i), stateToName(this.mState));
        }
        this.mState = i;
    }

    public final void showPictureInPictureMenu(boolean z) {
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1131163854, 0, "%s: showPictureInPictureMenu(), state=%s", "TvPipController", stateToName(this.mState));
        }
        if (this.mState == 0) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1376716975, 0, "%s:  > cannot open Menu from the current state.", "TvPipController");
                return;
            }
            return;
        }
        setState(2);
        TvPipMenuController tvPipMenuController = this.mTvPipMenuController;
        if (z) {
            tvPipMenuController.getClass();
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1455203829, 0, "%s: showMovementMenu()", "TvPipMenuController");
            }
            tvPipMenuController.switchToMenuMode(1, false);
        } else {
            tvPipMenuController.getClass();
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1690488730, 0, "%s: showMenu()", "TvPipMenuController");
            }
            tvPipMenuController.switchToMenuMode(2, true);
        }
        updatePinnedStackBounds();
    }

    public final void updatePinnedStackBounds() {
        updatePinnedStackBounds(this.mResizeAnimationDuration, true);
    }

    public final void updatePinnedStackBounds(int i, boolean z) {
        if (this.mState == 0) {
            return;
        }
        boolean isInMoveMode = this.mTvPipMenuController.isInMoveMode();
        this.mTvPipBoundsController.recalculatePipBounds(isInMoveMode, this.mState == 2 || isInMoveMode, i, z);
    }
}
