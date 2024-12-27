package android.app;

import android.app.servertransaction.ClientTransaction;
import android.app.servertransaction.DestroyActivityItem;
import android.app.servertransaction.PendingTransactionActions;
import android.app.servertransaction.TransactionExecutor;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.util.MergedConfiguration;
import android.view.SurfaceControl;
import android.window.ActivityWindowInfo;
import android.window.SplashScreenView;
import android.window.WindowContextInfo;

import com.android.internal.content.ReferrerIntent;

import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class ClientTransactionHandler {
    private boolean mIsExecutingLocalTransaction;

    public abstract void countLaunchingActivities(int i);

    public abstract Map<IBinder, DestroyActivityItem> getActivitiesToBeDestroyed();

    public abstract Activity getActivity(IBinder iBinder);

    public abstract ActivityThread.ActivityClientRecord getActivityClient(IBinder iBinder);

    public abstract LoadedApk getPackageInfoNoCheck(ApplicationInfo applicationInfo);

    abstract TransactionExecutor getTransactionExecutor();

    public abstract void handleActivityConfigurationChanged(
            ActivityThread.ActivityClientRecord activityClientRecord,
            Configuration configuration,
            int i,
            ActivityWindowInfo activityWindowInfo);

    public abstract void handleAttachSplashScreenView(
            ActivityThread.ActivityClientRecord activityClientRecord,
            SplashScreenView.SplashScreenViewParcelable splashScreenViewParcelable,
            SurfaceControl surfaceControl);

    public abstract void handleConfigurationChanged(Configuration configuration, int i);

    public abstract void handleCoreStatesChanged(Bundle bundle);

    public abstract void handleDestroyActivity(
            ActivityThread.ActivityClientRecord activityClientRecord,
            boolean z,
            boolean z2,
            String str);

    public abstract Activity handleLaunchActivity(
            ActivityThread.ActivityClientRecord activityClientRecord,
            PendingTransactionActions pendingTransactionActions,
            int i,
            Intent intent);

    public abstract void handleNewIntent(
            ActivityThread.ActivityClientRecord activityClientRecord, List<ReferrerIntent> list);

    public abstract void handlePauseActivity(
            ActivityThread.ActivityClientRecord activityClientRecord,
            boolean z,
            boolean z2,
            boolean z3,
            PendingTransactionActions pendingTransactionActions,
            String str);

    public abstract void handlePictureInPictureRequested(
            ActivityThread.ActivityClientRecord activityClientRecord);

    public abstract void handlePictureInPictureStateChanged(
            ActivityThread.ActivityClientRecord activityClientRecord,
            PictureInPictureUiState pictureInPictureUiState);

    public abstract void handleRelaunchActivity(
            ActivityThread.ActivityClientRecord activityClientRecord,
            PendingTransactionActions pendingTransactionActions);

    public abstract void handleResumeActivity(
            ActivityThread.ActivityClientRecord activityClientRecord,
            boolean z,
            boolean z2,
            boolean z3,
            String str);

    public abstract void handleSendResult(
            ActivityThread.ActivityClientRecord activityClientRecord,
            List<ResultInfo> list,
            String str);

    public abstract void handleStartActivity(
            ActivityThread.ActivityClientRecord activityClientRecord,
            PendingTransactionActions pendingTransactionActions,
            ActivityOptions.SceneTransitionInfo sceneTransitionInfo);

    public abstract void handleStopActivity(
            ActivityThread.ActivityClientRecord activityClientRecord,
            PendingTransactionActions pendingTransactionActions,
            boolean z,
            String str);

    public abstract void handleTopResumedActivityChanged(
            ActivityThread.ActivityClientRecord activityClientRecord, boolean z, String str);

    public abstract void handleWindowContextInfoChanged(
            IBinder iBinder, WindowContextInfo windowContextInfo);

    public abstract void handleWindowContextWindowRemoval(IBinder iBinder);

    public abstract boolean isHandleSplashScreenExit(IBinder iBinder);

    public abstract void performRestartActivity(
            ActivityThread.ActivityClientRecord activityClientRecord, boolean z);

    public abstract ActivityThread.ActivityClientRecord prepareRelaunchActivity(
            IBinder iBinder,
            List<ResultInfo> list,
            List<ReferrerIntent> list2,
            int i,
            MergedConfiguration mergedConfiguration,
            boolean z,
            ActivityWindowInfo activityWindowInfo);

    public abstract void reportRefresh(ActivityThread.ActivityClientRecord activityClientRecord);

    public abstract void reportRelaunch(ActivityThread.ActivityClientRecord activityClientRecord);

    public abstract void reportStop(PendingTransactionActions pendingTransactionActions);

    abstract void sendMessage(int i, Object obj);

    public abstract void updatePendingActivityConfiguration(
            IBinder iBinder, Configuration configuration);

    public abstract void updatePendingConfiguration(Configuration configuration);

    public abstract void updateProcessState(int i, boolean z);

    void scheduleTransaction(ClientTransaction transaction) {
        transaction.preExecute(this);
        sendMessage(159, transaction);
    }

    public void executeTransaction(ClientTransaction transaction) {
        this.mIsExecutingLocalTransaction = true;
        try {
            transaction.preExecute(this);
            getTransactionExecutor().execute(transaction);
        } finally {
            this.mIsExecutingLocalTransaction = false;
            transaction.recycle();
        }
    }

    public boolean isExecutingLocalTransaction() {
        return this.mIsExecutingLocalTransaction;
    }
}
