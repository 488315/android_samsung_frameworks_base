package com.android.wm.shell.splitscreen;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.WindowAnimationState;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.wm.shell.common.RemoteCallable;
import com.android.wm.shell.splitscreen.SplitScreenProxyService;
import com.android.wm.shell.util.StageUtils;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class SplitScreenProxyService extends Service {
    public static final boolean TEST_MOCK_REMOTE_TRANSITION = SystemProperties.getBoolean("persist.mt.debug.mock_remote", false);
    public Messenger mMessenger;
    public SplitScreenController mSplitScreenController;
    public final AnonymousClass1 mTestRemoteTransition;

    public class MessageHandler extends Handler {
        public static final /* synthetic */ int $r8$clinit = 0;

        public /* synthetic */ MessageHandler(SplitScreenProxyService splitScreenProxyService, int i) {
            this();
        }

        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            final int i;
            String str;
            final int i2 = 4;
            final int i3 = 5;
            int i4 = -1;
            final int i5 = 1;
            final int i6 = 2;
            SplitScreenProxyService splitScreenProxyService = SplitScreenProxyService.this;
            if (splitScreenProxyService.mSplitScreenController == null) {
                boolean z = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
                Slog.e("SplitScreenProxyService", "mSplitScreenController is null");
                return;
            }
            if (message.getData() == null) {
                boolean z2 = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
                Slog.e("SplitScreenProxyService", "msg data is empty");
                return;
            }
            if (message.what == 1000) {
                Bundle data = message.getData();
                data.getInt("recent_tasks_max");
                data.getInt("recent_tasks_flag");
                data.getInt("userid");
                boolean z3 = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
                throw null;
            }
            final StageLaunchOptions stageLaunchOptions = new StageLaunchOptions(message.getData());
            int i7 = stageLaunchOptions.mSplitCreateMode;
            int i8 = stageLaunchOptions.mRightBottomTaskId;
            int i9 = stageLaunchOptions.mLeftTopTaskId;
            int i10 = stageLaunchOptions.mLaunchTaskId;
            boolean z4 = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
            Slog.e("SplitScreenProxyService", "handle msg, what:" + message.what + " called:" + message.sendingUid);
            switch (message.what) {
                case 1:
                    if (stageLaunchOptions.mMainStageIntent == null || stageLaunchOptions.mSideStageIntent == null) {
                        Slog.w("SplitScreenProxyService", "START_INTENTS StageLaunchOptions has less intent");
                        return;
                    }
                    if (SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION) {
                        Slog.d("SplitScreenProxyService", "START_INTENTS: client request(" + stageLaunchOptions.mRemoteTransition + ") is ignored, reason=test_remote_transition");
                        stageLaunchOptions.mRemoteTransition = new RemoteTransition(splitScreenProxyService.mTestRemoteTransition);
                    }
                    SplitScreenController splitScreenController = splitScreenProxyService.mSplitScreenController;
                    splitScreenController.getClass();
                    if (!CoreRune.MW_MULTI_SPLIT_CREATE_MODE || MultiWindowUtils.isInSubDisplay(splitScreenController.mContext)) {
                        int i11 = splitScreenController.mStageCoordinator.mSideStagePosition;
                        if (i11 == -1) {
                            stageLaunchOptions.mSideStagePosition = 1;
                        } else if (i11 == 0) {
                            stageLaunchOptions.mSideStagePosition = i11;
                            Intent intent = stageLaunchOptions.mSideStageIntent;
                            UserHandle userHandle = stageLaunchOptions.mSideStageUserHandle;
                            stageLaunchOptions.mSideStageIntent = stageLaunchOptions.mMainStageIntent;
                            stageLaunchOptions.mSideStageUserHandle = stageLaunchOptions.mMainStageUserHandle;
                            stageLaunchOptions.mMainStageIntent = intent;
                            stageLaunchOptions.mMainStageUserHandle = userHandle;
                        } else {
                            stageLaunchOptions.mSideStagePosition = i11;
                        }
                    } else if (CoreRune.MW_PARALLEL_MULTI_SPLIT && stageLaunchOptions.mParallelMultiSplit) {
                        if (i7 == 2) {
                            stageLaunchOptions.mSideStagePosition = 0;
                            stageLaunchOptions.mSplitDivision = 0;
                            stageLaunchOptions.mCellStageWindowConfigPosition = 24;
                        } else if (i7 == 3) {
                            Intent intent2 = stageLaunchOptions.mCellStageIntent;
                            stageLaunchOptions.mCellStageIntent = stageLaunchOptions.mMainStageIntent;
                            stageLaunchOptions.mMainStageIntent = intent2;
                            stageLaunchOptions.mSideStagePosition = 1;
                            stageLaunchOptions.mSplitDivision = 1;
                            stageLaunchOptions.mCellStageWindowConfigPosition = 24;
                        } else if (i7 == 4) {
                            stageLaunchOptions.mSideStagePosition = 0;
                            stageLaunchOptions.mSplitDivision = 0;
                            stageLaunchOptions.mCellStageWindowConfigPosition = 96;
                        } else if (i7 == 5) {
                            Intent intent3 = stageLaunchOptions.mCellStageIntent;
                            stageLaunchOptions.mCellStageIntent = stageLaunchOptions.mMainStageIntent;
                            stageLaunchOptions.mMainStageIntent = intent3;
                            stageLaunchOptions.mSideStagePosition = 0;
                            stageLaunchOptions.mSplitDivision = 1;
                            stageLaunchOptions.mCellStageWindowConfigPosition = 96;
                        }
                    } else if (i7 == 2) {
                        stageLaunchOptions.mSideStagePosition = 1;
                        stageLaunchOptions.mSplitDivision = 0;
                        stageLaunchOptions.mCellStageWindowConfigPosition = 24;
                    } else if (i7 == 3) {
                        stageLaunchOptions.mSideStagePosition = 1;
                        stageLaunchOptions.mSplitDivision = 1;
                        stageLaunchOptions.mCellStageWindowConfigPosition = 48;
                    } else if (i7 == 4) {
                        stageLaunchOptions.mSideStagePosition = 0;
                        stageLaunchOptions.mSplitDivision = 0;
                        stageLaunchOptions.mCellStageWindowConfigPosition = 96;
                    } else if (i7 == 5) {
                        stageLaunchOptions.mSideStagePosition = 0;
                        stageLaunchOptions.mSplitDivision = 1;
                        stageLaunchOptions.mCellStageWindowConfigPosition = 72;
                    }
                    boolean z5 = CoreRune.MW_MULTI_SPLIT;
                    if (!z5 || stageLaunchOptions.mCellStageIntent == null) {
                        i = 0;
                        final int i12 = 3;
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i13 = i12;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i13) {
                                    case 0:
                                        int i14 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i15 = stageLaunchOptions2.mSideStagePosition;
                                        int i16 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i15, i16, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                    } else {
                        i = 0;
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i13 = i;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i13) {
                                    case 0:
                                        int i14 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i15 = stageLaunchOptions2.mSideStagePosition;
                                        int i16 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i15, i16, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                    }
                    if (CoreRune.MW_SPLIT_APP_PAIR_SA_LOGGING) {
                        String str2 = stageLaunchOptions.mLaunchFrom;
                        if (str2 == null) {
                            Slog.d("SplitScreenProxyService", "mLaunchFrom is null");
                            return;
                        }
                        switch (str2.hashCode()) {
                            case -1537237906:
                                if (str2.equals("taskbar")) {
                                    i4 = i;
                                    break;
                                }
                                break;
                            case 3208415:
                                if (str2.equals(BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN)) {
                                    i4 = 1;
                                    break;
                                }
                                break;
                            case 1184899919:
                                if (str2.equals("appsEdge")) {
                                    i4 = 2;
                                    break;
                                }
                                break;
                        }
                        switch (i4) {
                            case 0:
                                str = "From App pair on TaskBar";
                                break;
                            case 1:
                                str = "From App pair on Home";
                                break;
                            case 2:
                                str = "From Apps edge_AppPair";
                                break;
                            default:
                                str = null;
                                break;
                        }
                        if (str != null) {
                            CoreSaLogger.logForAdvanced("1000", str);
                            if (!z5 || stageLaunchOptions.mCellStageIntent == null) {
                                return;
                            }
                            CoreSaLogger.logForAdvanced("1021", str);
                            return;
                        }
                        return;
                    }
                    return;
                case 2:
                    if (i10 != -1 && stageLaunchOptions.mSideStageIntent != null) {
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i13 = i2;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i13) {
                                    case 0:
                                        int i14 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i15 = stageLaunchOptions2.mSideStagePosition;
                                        int i16 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i15, i16, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                        return;
                    }
                    Slog.w("SplitScreenProxyService", "START_TASK_AND_INTENT has less data. taskId:" + i10 + ", sideStageIntent:" + stageLaunchOptions.mSideStageIntent);
                    return;
                case 3:
                    if (stageLaunchOptions.mSideStageIntent == null) {
                        Slog.w("SplitScreenProxyService", "START_INTENT has no intent");
                        return;
                    }
                    if (CoreRune.MW_MULTI_SPLIT && stageLaunchOptions.mPendingIntent != null && stageLaunchOptions.mCellStageWindowConfigPosition != 0) {
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i13 = i3;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i13) {
                                    case 0:
                                        int i14 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i15 = stageLaunchOptions2.mSideStagePosition;
                                        int i16 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i15, i16, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                        return;
                    }
                    if (!CoreRune.MW_MULTI_SPLIT_FREE_POSITION || stageLaunchOptions.mSplitDivision == -1 || splitScreenProxyService.mSplitScreenController.mStageCoordinator.isSplitScreenVisible()) {
                        final int i13 = 7;
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i132 = i13;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i132) {
                                    case 0:
                                        int i14 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i15 = stageLaunchOptions2.mSideStagePosition;
                                        int i16 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i15, i16, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                        return;
                    } else {
                        final int i14 = 6;
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i132 = i14;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i132) {
                                    case 0:
                                        int i142 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i15 = stageLaunchOptions2.mSideStagePosition;
                                        int i16 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i15, i16, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                        return;
                    }
                case 4:
                    if (i9 == -1 || i8 == -1) {
                        Slog.w("SplitScreenProxyService", "START_SPLIT_TASKS has not enough task ids");
                        return;
                    } else {
                        final int i15 = 8;
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i132 = i15;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i132) {
                                    case 0:
                                        int i142 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i152 = stageLaunchOptions2.mSideStagePosition;
                                        int i16 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i152, i16, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                        return;
                    }
                case 5:
                    if (stageLaunchOptions.mTapIntent == null && stageLaunchOptions.mTapTaskId == -1) {
                        Slog.w("SplitScreenProxyService", "OPEN_IN_SPLIT_WITH_TAP has no valid start info");
                        return;
                    } else {
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i132 = i5;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i132) {
                                    case 0:
                                        int i142 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i152 = stageLaunchOptions2.mSideStagePosition;
                                        int i16 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i152, i16, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                        return;
                    }
                case 6:
                    if (i9 == -1 || i8 == -1) {
                        Slog.w("SplitScreenProxyService", "START_MULTI_SPLIT_TASKS has not enough task ids");
                        return;
                    } else {
                        final int i16 = 9;
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i132 = i16;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i132) {
                                    case 0:
                                        int i142 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i152 = stageLaunchOptions2.mSideStagePosition;
                                        int i162 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i152, i162, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                        return;
                    }
                case 7:
                default:
                    super.handleMessage(message);
                    return;
                case 8:
                    if (i10 == -1 && stageLaunchOptions.mMainStageIntent == null) {
                        Slog.w("SplitScreenProxyService", "OPEN_IN_SPLIT_WITH_ALLAPPS has no valid start info");
                        return;
                    } else {
                        SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i132 = i6;
                                StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                switch (i132) {
                                    case 0:
                                        int i142 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        Intent intent4 = stageLaunchOptions2.mMainStageIntent;
                                        Intent intent5 = stageLaunchOptions2.mSideStageIntent;
                                        Intent intent6 = stageLaunchOptions2.mCellStageIntent;
                                        UserHandle userHandle2 = stageLaunchOptions2.mMainStageUserHandle;
                                        UserHandle userHandle3 = stageLaunchOptions2.mSideStageUserHandle;
                                        UserHandle userHandle4 = stageLaunchOptions2.mCellStageUserHandle;
                                        int i152 = stageLaunchOptions2.mSideStagePosition;
                                        int i162 = stageLaunchOptions2.mCellStageWindowConfigPosition;
                                        int i17 = stageLaunchOptions2.mSplitDivision;
                                        RemoteTransition remoteTransition = stageLaunchOptions2.mRemoteTransition;
                                        float f = stageLaunchOptions2.mCellRatio;
                                        boolean z6 = stageLaunchOptions2.mParallelMultiSplit;
                                        float f2 = stageLaunchOptions2.mStageRatio;
                                        StageCoordinator stageCoordinator = ((SplitScreenController) obj).mStageCoordinator;
                                        stageCoordinator.getClass();
                                        if (userHandle2 == null) {
                                            userHandle2 = UserHandle.CURRENT;
                                        }
                                        if (userHandle3 == null) {
                                            userHandle3 = UserHandle.CURRENT;
                                        }
                                        if (userHandle4 == null) {
                                            userHandle4 = UserHandle.CURRENT;
                                        }
                                        stageCoordinator.startSplitScreen(-1, null, intent4, intent5, intent6, userHandle2, userHandle3, userHandle4, i152, i162, f2, f, 1, i17, z6, null, remoteTransition);
                                        break;
                                    case 1:
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i18 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        int i19 = stageLaunchOptions2.mTapTaskId;
                                        Intent intent7 = stageLaunchOptions2.mTapIntent;
                                        UserHandle userHandle5 = stageLaunchOptions2.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                boolean z7 = CoreRune.MW_SA_LOGGING;
                                                if (z7) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions activityOptionsMakeBasic = ActivityOptions.makeBasic();
                                                boolean zIsMultiSplitScreenVisible = CoreRune.MW_MULTI_SPLIT ? splitScreenController2.mStageCoordinator.isMultiSplitScreenVisible() : splitScreenController2.mStageCoordinator.isSplitScreenVisible();
                                                if (CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION && zIsMultiSplitScreenVisible) {
                                                    activityOptionsMakeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle = activityOptionsMakeBasic.toBundle();
                                                if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) || !splitScreenController2.mStageCoordinator.isSplitScreenVisible() || !MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() || splitScreenController2.mSplitState.isSplitStashed()) {
                                                    if (i19 == -1) {
                                                        splitScreenController2.startIntent(intent7, userHandle5, 1, -1, bundle);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i19, 1, bundle, null);
                                                        break;
                                                    }
                                                } else {
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.mStageCoordinator.isVerticalDivision());
                                                    if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                                                        StageCoordinator stageCoordinator2 = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator2.mSplitLayout.mParallelMultiSplit && stageCoordinator2.isMultiSplitScreenVisible()) {
                                                            multiSplitLaunchPosition = splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition;
                                                        }
                                                    }
                                                    if (i19 != -1) {
                                                        Bundle bundleResolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i19, bundleResolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent7, userHandle5, multiSplitLaunchPosition, false);
                                                    }
                                                    if (z7) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e);
                                            return;
                                        }
                                        break;
                                    case 2:
                                        int i20 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mMainStageUserHandle);
                                        break;
                                    case 3:
                                        int i21 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 4:
                                        int i22 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions2.mLaunchTaskId, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision);
                                        break;
                                    case 5:
                                        int i23 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions2.mPendingIntent, null, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                    case 6:
                                        int i24 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mSplitDivision, null);
                                        break;
                                    case 7:
                                        int i25 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mSideStagePosition, -1, null);
                                        break;
                                    case 8:
                                        int i26 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, -1, true, 0, stageLaunchOptions2.mStageRatio, 0.5f, false);
                                        break;
                                    default:
                                        int i27 = SplitScreenProxyService.MessageHandler.$r8$clinit;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions2.mLeftTopTaskId, stageLaunchOptions2.mRightBottomTaskId, stageLaunchOptions2.mCellTaskId, stageLaunchOptions2.mAppsStackedVertically, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mParallelMultiSplit);
                                        break;
                                }
                            }
                        });
                        return;
                    }
                case 9:
                    SplitScreenProxyService.m3278$$Nest$mexecuteRemoteCall(splitScreenProxyService, splitScreenProxyService.mSplitScreenController, new SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda1());
                    return;
            }
        }

        private MessageHandler() {
        }
    }

    /* renamed from: -$$Nest$mexecuteRemoteCall, reason: not valid java name */
    public static void m3278$$Nest$mexecuteRemoteCall(SplitScreenProxyService splitScreenProxyService, final RemoteCallable remoteCallable, final Consumer consumer) {
        splitScreenProxyService.getClass();
        remoteCallable.getRemoteCallExecutor().execute(new Runnable() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Consumer consumer2 = consumer;
                RemoteCallable remoteCallable2 = remoteCallable;
                boolean z = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
                consumer2.accept(remoteCallable2);
            }
        });
    }

    public SplitScreenProxyService() {
        this.mTestRemoteTransition = TEST_MOCK_REMOTE_TRANSITION ? new AnonymousClass1() : null;
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        if (LocalServices.getService(SplitScreenProxyService.class) == null) {
            LocalServices.addService(SplitScreenProxyService.class, this);
        }
        Messenger messenger = new Messenger(new MessageHandler(this, 0));
        this.mMessenger = messenger;
        return messenger.getBinder();
    }

    /* renamed from: com.android.wm.shell.splitscreen.SplitScreenProxyService$1, reason: invalid class name */
    public class AnonymousClass1 extends IRemoteTransition.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public AnonymousClass1() {
        }

        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            boolean z = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
            Slog.d("SplitScreenProxyService", "mergeAnimation: info=" + transitionInfo + ", t=" + transaction + ", mergeTarget=" + iBinder2 + ", finishCallback=" + iRemoteTransitionFinishedCallback + ", Callers=" + Debug.getCallers(10));
        }

        public final void onTransitionConsumed(IBinder iBinder, boolean z) {
            boolean z2 = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
            StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("onTransitionConsumed: aborted=", ", Callers=", z);
            sbM.append(Debug.getCallers(10));
            Slog.d("SplitScreenProxyService", sbM.toString());
        }

        public final void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            boolean z = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
            Slog.d("SplitScreenProxyService", "startAnimation: info=" + transitionInfo + ", t=" + transaction + ", finishCallback=" + iRemoteTransitionFinishedCallback + ", Callers=" + Debug.getCallers(10));
            transaction.apply();
            SplitScreenProxyService.this.getMainThreadHandler().postDelayed(new SplitScreenProxyService$$ExternalSyntheticLambda1(iRemoteTransitionFinishedCallback, 1), 1000L);
        }

        public final void takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback, WindowAnimationState[] windowAnimationStateArr) {
        }
    }
}
