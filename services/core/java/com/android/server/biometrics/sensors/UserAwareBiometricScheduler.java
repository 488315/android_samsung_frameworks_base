package com.android.server.biometrics.sensors;

import android.hardware.biometrics.IBiometricService;
import android.os.Handler;
import android.util.Slog;
import com.android.server.biometrics.sensors.UserAwareBiometricScheduler;
import com.android.server.biometrics.sensors.fingerprint.GestureAvailabilityDispatcher;

/* loaded from: classes.dex */
public class UserAwareBiometricScheduler extends SemConcurrentBiometricScheduler {
    public final CurrentUserRetriever mCurrentUserRetriever;
    public StopUserClient mStopUserClient;
    public final UserSwitchCallback mUserSwitchCallback;

    public interface CurrentUserRetriever {
        int getCurrentUserId();
    }

    public interface UserSwitchCallback {
        StartUserClient getStartUserClient(int i);

        StopUserClient getStopUserClient(int i);
    }

    public class ClientFinishedCallback implements ClientMonitorCallback {
        public final BaseClientMonitor mOwner;

        public ClientFinishedCallback(BaseClientMonitor baseClientMonitor) {
            this.mOwner = baseClientMonitor;
        }

        @Override // com.android.server.biometrics.sensors.ClientMonitorCallback
        public void onClientFinished(final BaseClientMonitor baseClientMonitor, final boolean z) {
            UserAwareBiometricScheduler.this.mHandler.post(new Runnable() { // from class: com.android.server.biometrics.sensors.UserAwareBiometricScheduler$ClientFinishedCallback$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UserAwareBiometricScheduler.ClientFinishedCallback.this.lambda$onClientFinished$0(baseClientMonitor, z);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClientFinished$0(BaseClientMonitor baseClientMonitor, boolean z) {
            Slog.d(UserAwareBiometricScheduler.this.getTag(), "[Client finished] " + baseClientMonitor + ", success: " + z);
            if (baseClientMonitor instanceof StopUserClient) {
                if (!z) {
                    Slog.w(UserAwareBiometricScheduler.this.getTag(), "StopUserClient failed(), is the HAL stuck? Clearing mStopUserClient");
                }
                UserAwareBiometricScheduler.this.mStopUserClient = null;
            }
            BiometricSchedulerOperation biometricSchedulerOperation = UserAwareBiometricScheduler.this.mCurrentOperation;
            if (biometricSchedulerOperation != null && biometricSchedulerOperation.isFor(this.mOwner)) {
                UserAwareBiometricScheduler.this.mCurrentOperation.destroy();
                UserAwareBiometricScheduler.this.mCurrentOperation = null;
            } else {
                Slog.w(UserAwareBiometricScheduler.this.getTag(), "operation is already null or different (reset?): " + UserAwareBiometricScheduler.this.mCurrentOperation);
            }
            UserAwareBiometricScheduler.this.startNextOperationIfIdle();
        }
    }

    public UserAwareBiometricScheduler(String str, Handler handler, int i, GestureAvailabilityDispatcher gestureAvailabilityDispatcher, IBiometricService iBiometricService, CurrentUserRetriever currentUserRetriever, UserSwitchCallback userSwitchCallback) {
        super(str, i, handler, gestureAvailabilityDispatcher);
        this.mCurrentUserRetriever = currentUserRetriever;
        this.mUserSwitchCallback = userSwitchCallback;
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    public String getTag() {
        return "UaBiometricScheduler/" + this.mBiometricTag;
    }

    @Override // com.android.server.biometrics.sensors.BiometricScheduler
    public void startNextOperationIfIdle() {
        if (this.mCurrentOperation != null) {
            Slog.v(getTag(), "Not idle, current operation: " + this.mCurrentOperation);
            return;
        }
        if (this.mPendingOperations.isEmpty()) {
            Slog.d(getTag(), "No operations, returning to idle");
            return;
        }
        int currentUserId = this.mCurrentUserRetriever.getCurrentUserId();
        int targetUserId = ((BiometricSchedulerOperation) this.mPendingOperations.getFirst()).getTargetUserId();
        if (targetUserId == currentUserId) {
            super.startNextOperationIfIdle();
            return;
        }
        if (currentUserId == -10000) {
            StartUserClient startUserClient = this.mUserSwitchCallback.getStartUserClient(targetUserId);
            ClientFinishedCallback clientFinishedCallback = new ClientFinishedCallback(startUserClient);
            Slog.d(getTag(), "[Starting User] " + startUserClient);
            this.mCurrentOperation = new BiometricSchedulerOperation(startUserClient, clientFinishedCallback, 2);
            startUserClient.start(clientFinishedCallback);
            return;
        }
        if (this.mStopUserClient != null) {
            Slog.d(getTag(), "[Waiting for StopUser] " + this.mStopUserClient);
            return;
        }
        StopUserClient stopUserClient = this.mUserSwitchCallback.getStopUserClient(currentUserId);
        this.mStopUserClient = stopUserClient;
        ClientFinishedCallback clientFinishedCallback2 = new ClientFinishedCallback(stopUserClient);
        Slog.d(getTag(), "[Stopping User] current: " + currentUserId + ", next: " + targetUserId + ". " + this.mStopUserClient);
        this.mCurrentOperation = new BiometricSchedulerOperation(this.mStopUserClient, clientFinishedCallback2, 2);
        this.mStopUserClient.start(clientFinishedCallback2);
    }

    public void onUserStopped() {
        if (this.mStopUserClient == null) {
            Slog.e(getTag(), "Unexpected onUserStopped");
            return;
        }
        Slog.d(getTag(), "[OnUserStopped]: " + this.mStopUserClient);
        this.mStopUserClient.onUserStopped();
        this.mStopUserClient = null;
    }

    public StopUserClient getStopUserClient() {
        return this.mStopUserClient;
    }
}
