package com.android.p038wm.shell.splitscreen;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import com.android.p038wm.shell.common.ExecutorUtils;
import com.android.p038wm.shell.recents.RecentTasksController;
import com.android.p038wm.shell.util.GroupedRecentTaskInfo;
import com.android.p038wm.shell.util.StageUtils;
import com.android.server.LocalServices;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SplitScreenProxyService extends Service {
    public static final boolean TEST_MOCK_REMOTE_TRANSITION = SystemProperties.getBoolean("persist.mt.debug.mock_remote", false);
    public Messenger mMessenger;
    public Message mPendingMsg;
    public RecentTasksController mRecentTasksController;
    public SplitScreenController mSplitScreenController;
    public final C41121 mTestRemoteTransition;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.splitscreen.SplitScreenProxyService$1 */
    public final class C41121 extends IRemoteTransition.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;

        public C41121() {
        }

        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            boolean z = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
            Slog.d("SplitScreenProxyService", "mergeAnimation: info=" + transitionInfo + ", t=" + transaction + ", mergeTarget=" + iBinder2 + ", finishCallback=" + iRemoteTransitionFinishedCallback + ", Callers=" + Debug.getCallers(10));
        }

        public final void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            boolean z = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
            Slog.d("SplitScreenProxyService", "startAnimation: info=" + transitionInfo + ", t=" + transaction + ", finishCallback=" + iRemoteTransitionFinishedCallback + ", Callers=" + Debug.getCallers(10));
            transaction.apply();
            SplitScreenProxyService.this.getMainThreadHandler().postDelayed(new SplitScreenProxyService$$ExternalSyntheticLambda0(iRemoteTransitionFinishedCallback, 1), 1000L);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MessageHandler extends Handler {
        public /* synthetic */ MessageHandler(SplitScreenProxyService splitScreenProxyService, int i) {
            this();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            int i;
            final int i2;
            int i3;
            if (SplitScreenProxyService.this.mSplitScreenController == null) {
                boolean z2 = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
                Slog.e("SplitScreenProxyService", "mSplitScreenController is null");
            }
            if (message.getData() == null) {
                boolean z3 = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
                Slog.e("SplitScreenProxyService", "msg data is empty");
                return;
            }
            if (message.what != 1000) {
                z = false;
            } else {
                Bundle data = message.getData();
                ArrayList<GroupedRecentTaskInfo> recentTasks = SplitScreenProxyService.this.mRecentTasksController.getRecentTasks(data.getInt("recent_tasks_max"), data.getInt("recent_tasks_flag"), data.getInt("userid"));
                Bundle bundle = new Bundle();
                GroupedRecentTaskInfo[] groupedRecentTaskInfoArr = new GroupedRecentTaskInfo[recentTasks.size()];
                recentTasks.toArray(groupedRecentTaskInfoArr);
                bundle.putParcelableArray("response", groupedRecentTaskInfoArr);
                Message obtain = Message.obtain(null, 1000, message.arg1, 0);
                obtain.setData(bundle);
                try {
                    message.replyTo.send(obtain);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                z = true;
            }
            if (z) {
                return;
            }
            final StageLaunchOptions stageLaunchOptions = new StageLaunchOptions(message.getData());
            Message message2 = SplitScreenProxyService.this.mPendingMsg;
            final int i4 = 8;
            final int i5 = 3;
            int i6 = stageLaunchOptions.mTapTaskId;
            Intent intent = stageLaunchOptions.mTapIntent;
            int i7 = stageLaunchOptions.mRightBottomTaskId;
            Intent intent2 = stageLaunchOptions.mCellStageIntent;
            int i8 = stageLaunchOptions.mLeftTopTaskId;
            if (message2 == null) {
                int i9 = message.what;
                if (i9 == 1 || i9 == 2 || i9 == 3 || i9 == 4 || i9 == 5 || i9 == 8) {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    SplitScreenProxyService splitScreenProxyService = SplitScreenProxyService.this;
                    splitScreenProxyService.getClass();
                    PendingIntent pendingIntent = stageLaunchOptions.mPendingIntent;
                    if (pendingIntent != null) {
                        arrayList.add(pendingIntent);
                    }
                    Intent intent3 = stageLaunchOptions.mMainStageIntent;
                    if (intent3 != null) {
                        arrayList.add(splitScreenProxyService.makePendingIntent(intent3, stageLaunchOptions.mMainStageUserHandle));
                    }
                    Intent intent4 = stageLaunchOptions.mSideStageIntent;
                    if (intent4 != null) {
                        arrayList.add(splitScreenProxyService.makePendingIntent(intent4, stageLaunchOptions.mSideStageUserHandle));
                    }
                    if (intent2 != null) {
                        arrayList.add(splitScreenProxyService.makePendingIntent(intent2, stageLaunchOptions.mCellStageUserHandle));
                    }
                    if (intent != null) {
                        arrayList.add(splitScreenProxyService.makePendingIntent(intent, stageLaunchOptions.mTapUserHandle));
                    }
                    int i10 = stageLaunchOptions.mLaunchTaskId;
                    if (i10 != -1) {
                        arrayList2.add(Integer.valueOf(i10));
                    }
                    if (i8 != -1) {
                        arrayList2.add(Integer.valueOf(i8));
                    }
                    if (i7 != -1) {
                        arrayList2.add(Integer.valueOf(i7));
                    }
                    int i11 = stageLaunchOptions.mCellTaskId;
                    if (i11 != -1) {
                        arrayList2.add(Integer.valueOf(i11));
                    }
                    if (i6 != -1) {
                        arrayList2.add(Integer.valueOf(i6));
                    }
                    if (MultiWindowManager.getInstance().shouldDeferEnterSplit(arrayList, arrayList2)) {
                        SplitScreenProxyService.this.mPendingMsg = Message.obtain(message);
                        return;
                    }
                }
            }
            SplitScreenProxyService.this.mPendingMsg = null;
            boolean z4 = SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION;
            Slog.e("SplitScreenProxyService", "handle msg, what:" + message.what + " called:" + message.sendingUid);
            switch (message.what) {
                case 1:
                    if (stageLaunchOptions.mMainStageIntent != null && stageLaunchOptions.mSideStageIntent != null) {
                        if (SplitScreenProxyService.TEST_MOCK_REMOTE_TRANSITION) {
                            Slog.d("SplitScreenProxyService", "START_INTENTS: client request(" + stageLaunchOptions.mRemoteTransition + ") is ignored, reason=test_remote_transition");
                            stageLaunchOptions.mRemoteTransition = new RemoteTransition(SplitScreenProxyService.this.mTestRemoteTransition);
                        }
                        SplitScreenController splitScreenController = SplitScreenProxyService.this.mSplitScreenController;
                        splitScreenController.getClass();
                        if (!CoreRune.MW_MULTI_SPLIT_CREATE_MODE || MultiWindowUtils.isInSubDisplay(splitScreenController.mContext)) {
                            int i12 = splitScreenController.mStageCoordinator.mSideStagePosition;
                            i = -1;
                            if (i12 == -1) {
                                stageLaunchOptions.mSideStagePosition = 1;
                            } else if (i12 == 0) {
                                stageLaunchOptions.mSideStagePosition = i12;
                                Intent intent5 = stageLaunchOptions.mSideStageIntent;
                                UserHandle userHandle = stageLaunchOptions.mSideStageUserHandle;
                                stageLaunchOptions.mSideStageIntent = stageLaunchOptions.mMainStageIntent;
                                stageLaunchOptions.mSideStageUserHandle = stageLaunchOptions.mMainStageUserHandle;
                                stageLaunchOptions.mMainStageIntent = intent5;
                                stageLaunchOptions.mMainStageUserHandle = userHandle;
                            } else {
                                stageLaunchOptions.mSideStagePosition = i12;
                            }
                        } else {
                            int i13 = stageLaunchOptions.mSplitCreateMode;
                            if (i13 == 2) {
                                stageLaunchOptions.mSideStagePosition = 1;
                                stageLaunchOptions.mSplitDivision = 0;
                                stageLaunchOptions.mCellStageWindowConfigPosition = 24;
                            } else if (i13 == 3) {
                                stageLaunchOptions.mSideStagePosition = 1;
                                stageLaunchOptions.mSplitDivision = 1;
                                stageLaunchOptions.mCellStageWindowConfigPosition = 48;
                            } else if (i13 == 4) {
                                stageLaunchOptions.mSideStagePosition = 0;
                                stageLaunchOptions.mSplitDivision = 0;
                                stageLaunchOptions.mCellStageWindowConfigPosition = 96;
                            } else if (i13 == 5) {
                                stageLaunchOptions.mSideStagePosition = 0;
                                stageLaunchOptions.mSplitDivision = 1;
                                stageLaunchOptions.mCellStageWindowConfigPosition = 72;
                            }
                            i = -1;
                        }
                        if (!CoreRune.MW_MULTI_SPLIT || intent2 == null) {
                            i2 = 0;
                            final int i14 = 1;
                            ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                                /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                                
                                    if (r13 == false) goto L38;
                                 */
                                @Override // java.util.function.Consumer
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void accept(Object obj) {
                                    boolean z5;
                                    switch (i14) {
                                        case 0:
                                            StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                            break;
                                        case 1:
                                            StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                            break;
                                        case 2:
                                            StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                            break;
                                        case 3:
                                            StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                            break;
                                        case 4:
                                            StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                            break;
                                        case 5:
                                            StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                            break;
                                        case 6:
                                            StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                            break;
                                        case 7:
                                            StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                            break;
                                        case 8:
                                            StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                            SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                            int i15 = stageLaunchOptions10.mTapTaskId;
                                            Intent intent6 = stageLaunchOptions10.mTapIntent;
                                            UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                            splitScreenController2.getClass();
                                            try {
                                                if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                    break;
                                                } else {
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                    }
                                                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                    if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                        makeBasic.setResumedAffordanceAnimation();
                                                    }
                                                    Bundle bundle2 = makeBasic.toBundle();
                                                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                            StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                            if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                                z5 = true;
                                                                break;
                                                            } else {
                                                                z5 = false;
                                                                break;
                                                            }
                                                        }
                                                        int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                        if (i15 != -1) {
                                                            Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                                splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                            }
                                                            ActivityTaskManager.getService().startActivityFromRecents(i15, resolveStartCellStage);
                                                        } else {
                                                            splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                        }
                                                        if (CoreRune.MW_SA_LOGGING) {
                                                            CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                            break;
                                                        }
                                                    }
                                                    if (i15 == -1) {
                                                        splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i15, 1, bundle2);
                                                        break;
                                                    }
                                                }
                                            } catch (Exception e2) {
                                                Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                            }
                                            break;
                                        default:
                                            StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                            ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                            break;
                                    }
                                }
                            });
                        } else {
                            i2 = 0;
                            ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                                /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                                
                                    if (r13 == false) goto L38;
                                 */
                                @Override // java.util.function.Consumer
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void accept(Object obj) {
                                    boolean z5;
                                    switch (i2) {
                                        case 0:
                                            StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                            break;
                                        case 1:
                                            StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                            break;
                                        case 2:
                                            StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                            break;
                                        case 3:
                                            StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                            break;
                                        case 4:
                                            StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                            break;
                                        case 5:
                                            StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                            break;
                                        case 6:
                                            StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                            break;
                                        case 7:
                                            StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                            break;
                                        case 8:
                                            StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                            SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                            int i15 = stageLaunchOptions10.mTapTaskId;
                                            Intent intent6 = stageLaunchOptions10.mTapIntent;
                                            UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                            splitScreenController2.getClass();
                                            try {
                                                if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                    break;
                                                } else {
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                    }
                                                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                    if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                        makeBasic.setResumedAffordanceAnimation();
                                                    }
                                                    Bundle bundle2 = makeBasic.toBundle();
                                                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                            StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                            if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                                z5 = true;
                                                                break;
                                                            } else {
                                                                z5 = false;
                                                                break;
                                                            }
                                                        }
                                                        int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                        if (i15 != -1) {
                                                            Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                                splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                            }
                                                            ActivityTaskManager.getService().startActivityFromRecents(i15, resolveStartCellStage);
                                                        } else {
                                                            splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                        }
                                                        if (CoreRune.MW_SA_LOGGING) {
                                                            CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                            break;
                                                        }
                                                    }
                                                    if (i15 == -1) {
                                                        splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i15, 1, bundle2);
                                                        break;
                                                    }
                                                }
                                            } catch (Exception e2) {
                                                Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                            }
                                            break;
                                        default:
                                            StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                            ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                            break;
                                    }
                                }
                            });
                        }
                        if (CoreRune.MW_SPLIT_APP_PAIR_SA_LOGGING) {
                            SplitScreenProxyService.this.getClass();
                            String str = stageLaunchOptions.mLaunchFrom;
                            if (str != null) {
                                int hashCode = str.hashCode();
                                if (hashCode == -1537237906) {
                                    if (str.equals("taskbar")) {
                                        i3 = i2;
                                    }
                                    i3 = i;
                                } else if (hashCode != 3208415) {
                                    if (hashCode == 1184899919 && str.equals("appsEdge")) {
                                        i3 = 2;
                                    }
                                    i3 = i;
                                } else {
                                    if (str.equals(BcSmartspaceDataPlugin.UI_SURFACE_HOME_SCREEN)) {
                                        i3 = 1;
                                    }
                                    i3 = i;
                                }
                                String str2 = i3 != 0 ? i3 != 1 ? i3 != 2 ? null : "From Apps edge_AppPair" : "From App pair on Home" : "From App pair on TaskBar";
                                if (str2 != null) {
                                    CoreSaLogger.logForAdvanced("1000", str2);
                                    if (CoreRune.MW_MULTI_SPLIT && intent2 != null) {
                                        CoreSaLogger.logForAdvanced("1021", str2);
                                        break;
                                    }
                                }
                            } else {
                                Slog.d("SplitScreenProxyService", "mLaunchFrom is null");
                                break;
                            }
                        }
                    } else {
                        Slog.w("SplitScreenProxyService", "START_INTENTS StageLaunchOptions has less intent");
                        break;
                    }
                    break;
                case 2:
                    if (stageLaunchOptions.mLaunchTaskId != -1 && stageLaunchOptions.mSideStageIntent != null) {
                        final int i15 = 2;
                        ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                            
                                if (r13 == false) goto L38;
                             */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj) {
                                boolean z5;
                                switch (i15) {
                                    case 0:
                                        StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 1:
                                        StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                        break;
                                    case 2:
                                        StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                        break;
                                    case 3:
                                        StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                        break;
                                    case 4:
                                        StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                        break;
                                    case 5:
                                        StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                        break;
                                    case 6:
                                        StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                        break;
                                    case 7:
                                        StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                        break;
                                    case 8:
                                        StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i152 = stageLaunchOptions10.mTapTaskId;
                                        Intent intent6 = stageLaunchOptions10.mTapIntent;
                                        UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                if (CoreRune.MW_SA_LOGGING) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                    makeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle2 = makeBasic.toBundle();
                                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                    if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                        StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                            z5 = true;
                                                            break;
                                                        } else {
                                                            z5 = false;
                                                            break;
                                                        }
                                                    }
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                    if (i152 != -1) {
                                                        Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i152, resolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                    }
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                                if (i152 == -1) {
                                                    splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                    break;
                                                } else {
                                                    splitScreenController2.startTask(i152, 1, bundle2);
                                                    break;
                                                }
                                            }
                                        } catch (Exception e2) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                        }
                                        break;
                                    default:
                                        StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                        break;
                                }
                            }
                        });
                        break;
                    } else {
                        Slog.w("SplitScreenProxyService", "START_TASK_AND_INTENT has less data. taskId:" + stageLaunchOptions.mLaunchTaskId + ", sideStageIntent:" + stageLaunchOptions.mSideStageIntent);
                        break;
                    }
                    break;
                case 3:
                    if (stageLaunchOptions.mSideStageIntent != null) {
                        if (CoreRune.MW_MULTI_SPLIT && stageLaunchOptions.mPendingIntent != null && stageLaunchOptions.mCellStageWindowConfigPosition != 0) {
                            ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                                /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                                
                                    if (r13 == false) goto L38;
                                 */
                                @Override // java.util.function.Consumer
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void accept(Object obj) {
                                    boolean z5;
                                    switch (i5) {
                                        case 0:
                                            StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                            break;
                                        case 1:
                                            StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                            break;
                                        case 2:
                                            StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                            break;
                                        case 3:
                                            StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                            break;
                                        case 4:
                                            StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                            break;
                                        case 5:
                                            StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                            break;
                                        case 6:
                                            StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                            break;
                                        case 7:
                                            StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                            break;
                                        case 8:
                                            StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                            SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                            int i152 = stageLaunchOptions10.mTapTaskId;
                                            Intent intent6 = stageLaunchOptions10.mTapIntent;
                                            UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                            splitScreenController2.getClass();
                                            try {
                                                if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                    break;
                                                } else {
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                    }
                                                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                    if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                        makeBasic.setResumedAffordanceAnimation();
                                                    }
                                                    Bundle bundle2 = makeBasic.toBundle();
                                                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                            StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                            if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                                z5 = true;
                                                                break;
                                                            } else {
                                                                z5 = false;
                                                                break;
                                                            }
                                                        }
                                                        int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                        if (i152 != -1) {
                                                            Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                                splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                            }
                                                            ActivityTaskManager.getService().startActivityFromRecents(i152, resolveStartCellStage);
                                                        } else {
                                                            splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                        }
                                                        if (CoreRune.MW_SA_LOGGING) {
                                                            CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                            break;
                                                        }
                                                    }
                                                    if (i152 == -1) {
                                                        splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i152, 1, bundle2);
                                                        break;
                                                    }
                                                }
                                            } catch (Exception e2) {
                                                Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                            }
                                            break;
                                        default:
                                            StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                            ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                            break;
                                    }
                                }
                            });
                            break;
                        } else if (CoreRune.MW_MULTI_SPLIT_FREE_POSITION && stageLaunchOptions.mSplitDivision != -1 && !SplitScreenProxyService.this.mSplitScreenController.isSplitScreenVisible()) {
                            final int i16 = 4;
                            ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                                /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                                
                                    if (r13 == false) goto L38;
                                 */
                                @Override // java.util.function.Consumer
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void accept(Object obj) {
                                    boolean z5;
                                    switch (i16) {
                                        case 0:
                                            StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                            break;
                                        case 1:
                                            StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                            break;
                                        case 2:
                                            StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                            break;
                                        case 3:
                                            StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                            break;
                                        case 4:
                                            StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                            break;
                                        case 5:
                                            StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                            break;
                                        case 6:
                                            StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                            break;
                                        case 7:
                                            StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                            break;
                                        case 8:
                                            StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                            SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                            int i152 = stageLaunchOptions10.mTapTaskId;
                                            Intent intent6 = stageLaunchOptions10.mTapIntent;
                                            UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                            splitScreenController2.getClass();
                                            try {
                                                if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                    break;
                                                } else {
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                    }
                                                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                    if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                        makeBasic.setResumedAffordanceAnimation();
                                                    }
                                                    Bundle bundle2 = makeBasic.toBundle();
                                                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                            StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                            if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                                z5 = true;
                                                                break;
                                                            } else {
                                                                z5 = false;
                                                                break;
                                                            }
                                                        }
                                                        int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                        if (i152 != -1) {
                                                            Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                                splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                            }
                                                            ActivityTaskManager.getService().startActivityFromRecents(i152, resolveStartCellStage);
                                                        } else {
                                                            splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                        }
                                                        if (CoreRune.MW_SA_LOGGING) {
                                                            CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                            break;
                                                        }
                                                    }
                                                    if (i152 == -1) {
                                                        splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i152, 1, bundle2);
                                                        break;
                                                    }
                                                }
                                            } catch (Exception e2) {
                                                Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                            }
                                            break;
                                        default:
                                            StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                            ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                            break;
                                    }
                                }
                            });
                            break;
                        } else {
                            final int i17 = 5;
                            ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                                /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                                
                                    if (r13 == false) goto L38;
                                 */
                                @Override // java.util.function.Consumer
                                /*
                                    Code decompiled incorrectly, please refer to instructions dump.
                                */
                                public final void accept(Object obj) {
                                    boolean z5;
                                    switch (i17) {
                                        case 0:
                                            StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                            break;
                                        case 1:
                                            StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                            break;
                                        case 2:
                                            StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                            break;
                                        case 3:
                                            StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                            break;
                                        case 4:
                                            StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                            break;
                                        case 5:
                                            StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                            break;
                                        case 6:
                                            StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                            break;
                                        case 7:
                                            StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                            ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                            break;
                                        case 8:
                                            StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                            SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                            int i152 = stageLaunchOptions10.mTapTaskId;
                                            Intent intent6 = stageLaunchOptions10.mTapIntent;
                                            UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                            splitScreenController2.getClass();
                                            try {
                                                if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                    break;
                                                } else {
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                    }
                                                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                    if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                        makeBasic.setResumedAffordanceAnimation();
                                                    }
                                                    Bundle bundle2 = makeBasic.toBundle();
                                                    if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                        if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                            StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                            if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                                z5 = true;
                                                                break;
                                                            } else {
                                                                z5 = false;
                                                                break;
                                                            }
                                                        }
                                                        int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                        if (i152 != -1) {
                                                            Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                            if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                                splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                            }
                                                            ActivityTaskManager.getService().startActivityFromRecents(i152, resolveStartCellStage);
                                                        } else {
                                                            splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                        }
                                                        if (CoreRune.MW_SA_LOGGING) {
                                                            CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                            break;
                                                        }
                                                    }
                                                    if (i152 == -1) {
                                                        splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                        break;
                                                    } else {
                                                        splitScreenController2.startTask(i152, 1, bundle2);
                                                        break;
                                                    }
                                                }
                                            } catch (Exception e2) {
                                                Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                            }
                                            break;
                                        default:
                                            StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                            ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                            break;
                                    }
                                }
                            });
                            break;
                        }
                    } else {
                        Slog.w("SplitScreenProxyService", "START_INTENT has no intent");
                        break;
                    }
                    break;
                case 4:
                    if (i8 != -1 && i7 != -1) {
                        final int i18 = 6;
                        ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                            
                                if (r13 == false) goto L38;
                             */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj) {
                                boolean z5;
                                switch (i18) {
                                    case 0:
                                        StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 1:
                                        StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                        break;
                                    case 2:
                                        StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                        break;
                                    case 3:
                                        StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                        break;
                                    case 4:
                                        StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                        break;
                                    case 5:
                                        StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                        break;
                                    case 6:
                                        StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                        break;
                                    case 7:
                                        StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                        break;
                                    case 8:
                                        StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i152 = stageLaunchOptions10.mTapTaskId;
                                        Intent intent6 = stageLaunchOptions10.mTapIntent;
                                        UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                if (CoreRune.MW_SA_LOGGING) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                    makeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle2 = makeBasic.toBundle();
                                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                    if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                        StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                            z5 = true;
                                                            break;
                                                        } else {
                                                            z5 = false;
                                                            break;
                                                        }
                                                    }
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                    if (i152 != -1) {
                                                        Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i152, resolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                    }
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                                if (i152 == -1) {
                                                    splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                    break;
                                                } else {
                                                    splitScreenController2.startTask(i152, 1, bundle2);
                                                    break;
                                                }
                                            }
                                        } catch (Exception e2) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                        }
                                        break;
                                    default:
                                        StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                        break;
                                }
                            }
                        });
                        break;
                    } else {
                        Slog.w("SplitScreenProxyService", "START_SPLIT_TASKS has not enough task ids");
                        break;
                    }
                case 5:
                    if (intent != null || i6 != -1) {
                        ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                            
                                if (r13 == false) goto L38;
                             */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj) {
                                boolean z5;
                                switch (i4) {
                                    case 0:
                                        StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 1:
                                        StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                        break;
                                    case 2:
                                        StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                        break;
                                    case 3:
                                        StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                        break;
                                    case 4:
                                        StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                        break;
                                    case 5:
                                        StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                        break;
                                    case 6:
                                        StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                        break;
                                    case 7:
                                        StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                        break;
                                    case 8:
                                        StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i152 = stageLaunchOptions10.mTapTaskId;
                                        Intent intent6 = stageLaunchOptions10.mTapIntent;
                                        UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                if (CoreRune.MW_SA_LOGGING) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                    makeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle2 = makeBasic.toBundle();
                                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                    if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                        StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                            z5 = true;
                                                            break;
                                                        } else {
                                                            z5 = false;
                                                            break;
                                                        }
                                                    }
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                    if (i152 != -1) {
                                                        Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i152, resolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                    }
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                                if (i152 == -1) {
                                                    splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                    break;
                                                } else {
                                                    splitScreenController2.startTask(i152, 1, bundle2);
                                                    break;
                                                }
                                            }
                                        } catch (Exception e2) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                        }
                                        break;
                                    default:
                                        StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                        break;
                                }
                            }
                        });
                        break;
                    } else {
                        Slog.w("SplitScreenProxyService", "OPEN_IN_SPLIT_WITH_TAP has no valid start info");
                        break;
                    }
                case 6:
                    if (i8 != -1 && i7 != -1) {
                        final int i19 = 7;
                        ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                            
                                if (r13 == false) goto L38;
                             */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj) {
                                boolean z5;
                                switch (i19) {
                                    case 0:
                                        StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 1:
                                        StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                        break;
                                    case 2:
                                        StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                        break;
                                    case 3:
                                        StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                        break;
                                    case 4:
                                        StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                        break;
                                    case 5:
                                        StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                        break;
                                    case 6:
                                        StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                        break;
                                    case 7:
                                        StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                        break;
                                    case 8:
                                        StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i152 = stageLaunchOptions10.mTapTaskId;
                                        Intent intent6 = stageLaunchOptions10.mTapIntent;
                                        UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                if (CoreRune.MW_SA_LOGGING) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                    makeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle2 = makeBasic.toBundle();
                                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                    if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                        StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                            z5 = true;
                                                            break;
                                                        } else {
                                                            z5 = false;
                                                            break;
                                                        }
                                                    }
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                    if (i152 != -1) {
                                                        Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i152, resolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                    }
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                                if (i152 == -1) {
                                                    splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                    break;
                                                } else {
                                                    splitScreenController2.startTask(i152, 1, bundle2);
                                                    break;
                                                }
                                            }
                                        } catch (Exception e2) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                        }
                                        break;
                                    default:
                                        StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                        break;
                                }
                            }
                        });
                        break;
                    } else {
                        Slog.w("SplitScreenProxyService", "START_MULTI_SPLIT_TASKS has not enough task ids");
                        break;
                    }
                case 7:
                default:
                    super.handleMessage(message);
                    break;
                case 8:
                    if (stageLaunchOptions.mLaunchTaskId != -1 || stageLaunchOptions.mMainStageIntent != null) {
                        final int i20 = 9;
                        ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new Consumer() { // from class: com.android.wm.shell.splitscreen.SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda0
                            /* JADX WARN: Code restructure failed: missing block: B:36:0x007b, code lost:
                            
                                if (r13 == false) goto L38;
                             */
                            @Override // java.util.function.Consumer
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                            */
                            public final void accept(Object obj) {
                                boolean z5;
                                switch (i20) {
                                    case 0:
                                        StageLaunchOptions stageLaunchOptions2 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions2.mMainStageIntent, stageLaunchOptions2.mSideStageIntent, stageLaunchOptions2.mCellStageIntent, stageLaunchOptions2.mMainStageUserHandle, stageLaunchOptions2.mSideStageUserHandle, stageLaunchOptions2.mCellStageUserHandle, stageLaunchOptions2.mSideStagePosition, stageLaunchOptions2.mCellStageWindowConfigPosition, stageLaunchOptions2.mStageRatio, stageLaunchOptions2.mCellRatio, stageLaunchOptions2.mSplitDivision, stageLaunchOptions2.mRemoteTransition);
                                        break;
                                    case 1:
                                        StageLaunchOptions stageLaunchOptions3 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntents(stageLaunchOptions3.mMainStageIntent, stageLaunchOptions3.mSideStageIntent, stageLaunchOptions3.mMainStageUserHandle, stageLaunchOptions3.mSideStageUserHandle, stageLaunchOptions3.mSideStagePosition, stageLaunchOptions3.mStageRatio, stageLaunchOptions3.mSplitDivision, stageLaunchOptions3.mRemoteTransition);
                                        break;
                                    case 2:
                                        StageLaunchOptions stageLaunchOptions4 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startTaskAndIntent(stageLaunchOptions4.mLaunchTaskId, stageLaunchOptions4.mSideStageIntent, stageLaunchOptions4.mSideStagePosition, stageLaunchOptions4.mSplitDivision);
                                        break;
                                    case 3:
                                        StageLaunchOptions stageLaunchOptions5 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntentToCell(stageLaunchOptions5.mPendingIntent, null, stageLaunchOptions5.mSideStageUserHandle, stageLaunchOptions5.mCellStageWindowConfigPosition);
                                        break;
                                    case 4:
                                        StageLaunchOptions stageLaunchOptions6 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions6.mSideStageIntent, stageLaunchOptions6.mSideStageUserHandle, stageLaunchOptions6.mSideStagePosition, stageLaunchOptions6.mSplitDivision);
                                        break;
                                    case 5:
                                        StageLaunchOptions stageLaunchOptions7 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startIntent(stageLaunchOptions7.mSideStageIntent, stageLaunchOptions7.mSideStageUserHandle, stageLaunchOptions7.mSideStagePosition, -1);
                                        break;
                                    case 6:
                                        StageLaunchOptions stageLaunchOptions8 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions8.mLeftTopTaskId, stageLaunchOptions8.mRightBottomTaskId, -1, true, 0, stageLaunchOptions8.mStageRatio, 0.5f);
                                        break;
                                    case 7:
                                        StageLaunchOptions stageLaunchOptions9 = stageLaunchOptions;
                                        ((SplitScreenController) obj).startSplitTasks(stageLaunchOptions9.mLeftTopTaskId, stageLaunchOptions9.mRightBottomTaskId, stageLaunchOptions9.mCellTaskId, stageLaunchOptions9.mAppsStackedVertically, stageLaunchOptions9.mCellStageWindowConfigPosition, stageLaunchOptions9.mStageRatio, stageLaunchOptions9.mCellRatio);
                                        break;
                                    case 8:
                                        StageLaunchOptions stageLaunchOptions10 = stageLaunchOptions;
                                        SplitScreenController splitScreenController2 = (SplitScreenController) obj;
                                        int i152 = stageLaunchOptions10.mTapTaskId;
                                        Intent intent6 = stageLaunchOptions10.mTapIntent;
                                        UserHandle userHandle2 = stageLaunchOptions10.mTapUserHandle;
                                        splitScreenController2.getClass();
                                        try {
                                            if (!splitScreenController2.mStageCoordinator.mMainStage.mIsActive && SplitScreenController.getToggleSplitScreenTarget() == null) {
                                                break;
                                            } else {
                                                if (CoreRune.MW_SA_LOGGING) {
                                                    CoreSaLogger.logForAdvanced("1000", "From Apps edge_Tap");
                                                }
                                                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                                if (CoreRune.MW_MULTI_SPLIT ? splitScreenController2.isMultiSplitScreenVisible() : splitScreenController2.isSplitScreenVisible()) {
                                                    makeBasic.setResumedAffordanceAnimation();
                                                }
                                                Bundle bundle2 = makeBasic.toBundle();
                                                if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !MultiWindowUtils.isInSubDisplay(splitScreenController2.mContext) && MultiWindowManager.getInstance().supportMultiSplitAppMinimumSize() && splitScreenController2.mStageCoordinator.isSplitScreenVisible()) {
                                                    if (CoreRune.MW_SPLIT_IMMERSIVE_VIDEO_CONTROLS) {
                                                        StageCoordinator stageCoordinator = splitScreenController2.mStageCoordinator;
                                                        if (stageCoordinator != null && stageCoordinator.mIsVideoControls) {
                                                            z5 = true;
                                                            break;
                                                        } else {
                                                            z5 = false;
                                                            break;
                                                        }
                                                    }
                                                    int multiSplitLaunchPosition = StageUtils.getMultiSplitLaunchPosition(splitScreenController2.mStageCoordinator.mCellStageWindowConfigPosition, splitScreenController2.isVerticalDivision());
                                                    if (i152 != -1) {
                                                        Bundle resolveStartCellStage = splitScreenController2.mStageCoordinator.resolveStartCellStage(-1, multiSplitLaunchPosition, bundle2, null);
                                                        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && !splitScreenController2.mStageCoordinator.isMultiSplitActive()) {
                                                            splitScreenController2.mStageCoordinator.mSplitLayout.setCellDividerRatio(0.5f, multiSplitLaunchPosition, true, false);
                                                        }
                                                        ActivityTaskManager.getService().startActivityFromRecents(i152, resolveStartCellStage);
                                                    } else {
                                                        splitScreenController2.startIntentToCell(null, intent6, userHandle2, multiSplitLaunchPosition);
                                                    }
                                                    if (CoreRune.MW_SA_LOGGING) {
                                                        CoreSaLogger.logForAdvanced("1021", "From Apps edge_Tap");
                                                        break;
                                                    }
                                                }
                                                if (i152 == -1) {
                                                    splitScreenController2.startIntent(intent6, userHandle2, 1, -1, bundle2);
                                                    break;
                                                } else {
                                                    splitScreenController2.startTask(i152, 1, bundle2);
                                                    break;
                                                }
                                            }
                                        } catch (Exception e2) {
                                            Slog.e("SplitScreenController", "Failed to open in split with tap", e2);
                                        }
                                        break;
                                    default:
                                        StageLaunchOptions stageLaunchOptions11 = stageLaunchOptions;
                                        ((SplitScreenController) obj).openInSplitWithAllApps(stageLaunchOptions11.mLaunchTaskId, stageLaunchOptions11.mMainStageIntent, stageLaunchOptions11.mMainStageUserHandle);
                                        break;
                                }
                            }
                        });
                        break;
                    } else {
                        Slog.w("SplitScreenProxyService", "OPEN_IN_SPLIT_WITH_ALLAPPS has no valid start info");
                        break;
                    }
                    break;
                case 9:
                    ExecutorUtils.executeRemoteCall(SplitScreenProxyService.this.mSplitScreenController, new SplitScreenProxyService$MessageHandler$$ExternalSyntheticLambda1());
                    break;
            }
        }

        private MessageHandler() {
        }
    }

    public SplitScreenProxyService() {
        this.mTestRemoteTransition = TEST_MOCK_REMOTE_TRANSITION ? new C41121() : null;
    }

    public final PendingIntent makePendingIntent(Intent intent, UserHandle userHandle) {
        if (userHandle == null) {
            userHandle = UserHandle.CURRENT;
        }
        return PendingIntent.getActivityAsUser(this, 0, intent, 167772160, null, userHandle);
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
}
