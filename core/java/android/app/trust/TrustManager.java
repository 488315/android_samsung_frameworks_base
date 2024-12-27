package android.app.trust;

import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;

public class TrustManager {
    public static final String ACTION_BIND_SIGNIFICANT_PLACE_PROVIDER =
            "com.android.trust.provider.SignificantPlaceProvider.BIND";
    private static final String DATA_FLAGS = "initiatedByUser";
    private static final String DATA_GRANTED_MESSAGES = "grantedMessages";
    private static final String DATA_MESSAGE = "message";
    private static final String DATA_NEWLY_UNLOCKED = "newlyUnlocked";
    private static final int MSG_ENABLED_TRUST_AGENTS_CHANGED = 4;
    private static final int MSG_IS_ACTIVE_UNLOCK_RUNNING = 5;
    private static final int MSG_TRUST_CHANGED = 1;
    private static final int MSG_TRUST_ERROR = 3;
    private static final int MSG_TRUST_MANAGED_CHANGED = 2;
    private static final String TAG = "TrustManager";
    private final ITrustManager mService;
    private final Handler mHandler =
            new Handler(Looper.getMainLooper()) { // from class: android.app.trust.TrustManager.2
                @Override // android.os.Handler
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            Bundle data = msg.peekData();
                            int flags = data != null ? data.getInt(TrustManager.DATA_FLAGS) : 0;
                            boolean enabled = msg.arg1 != 0;
                            int newlyUnlockedInt =
                                    data != null
                                            ? data.getInt(TrustManager.DATA_NEWLY_UNLOCKED)
                                            : 0;
                            boolean newlyUnlocked = newlyUnlockedInt != 0;
                            ((TrustListener) msg.obj)
                                    .onTrustChanged(
                                            enabled,
                                            newlyUnlocked,
                                            msg.arg2,
                                            flags,
                                            msg.getData()
                                                    .getStringArrayList(
                                                            TrustManager.DATA_GRANTED_MESSAGES));
                            break;
                        case 2:
                            ((TrustListener) msg.obj)
                                    .onTrustManagedChanged(msg.arg1 != 0, msg.arg2);
                            break;
                        case 3:
                            CharSequence message = msg.peekData().getCharSequence("message");
                            ((TrustListener) msg.obj).onTrustError(message);
                            break;
                        case 4:
                            ((TrustListener) msg.obj).onEnabledTrustAgentsChanged(msg.arg1);
                            break;
                        case 5:
                            ((TrustListener) msg.obj)
                                    .onIsActiveUnlockRunningChanged(msg.arg1 != 0, msg.arg2);
                            break;
                    }
                }
            };
    private final ArrayMap<TrustListener, ITrustListener> mTrustListeners = new ArrayMap<>();

    public interface TrustListener {
        void onEnabledTrustAgentsChanged(int i);

        void onIsActiveUnlockRunningChanged(boolean z, int i);

        void onTrustChanged(boolean z, boolean z2, int i, int i2, List<String> list);

        void onTrustError(CharSequence charSequence);

        void onTrustManagedChanged(boolean z, int i);
    }

    public TrustManager(IBinder b) {
        this.mService = ITrustManager.Stub.asInterface(b);
    }

    public void setDeviceLockedForUser(int userId, boolean locked) {
        try {
            this.mService.setDeviceLockedForUser(userId, locked);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUnlockAttempt(boolean successful, int userId) {
        try {
            this.mService.reportUnlockAttempt(successful, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserRequestedUnlock(int userId, boolean dismissKeyguard) {
        try {
            this.mService.reportUserRequestedUnlock(userId, dismissKeyguard);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserMayRequestUnlock(int userId) {
        try {
            this.mService.reportUserMayRequestUnlock(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUnlockLockout(int timeoutMs, int userId) {
        try {
            this.mService.reportUnlockLockout(timeoutMs, userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportEnabledTrustAgentsChanged(int userId) {
        try {
            this.mService.reportEnabledTrustAgentsChanged(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportKeyguardShowingChanged() {
        try {
            this.mService.reportKeyguardShowingChanged();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isActiveUnlockRunning(int userId) {
        try {
            return this.mService.isActiveUnlockRunning(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTrustListener(final TrustListener trustListener) {
        try {
            ITrustListener.Stub iTrustListener =
                    new ITrustListener.Stub() { // from class: android.app.trust.TrustManager.1
                        @Override // android.app.trust.ITrustListener
                        public void onTrustChanged(
                                boolean z, boolean z2, int i, int i2, List<String> list) {
                            Message obtainMessage =
                                    TrustManager.this.mHandler.obtainMessage(
                                            1, z ? 1 : 0, i, trustListener);
                            if (i2 != 0) {
                                obtainMessage.getData().putInt(TrustManager.DATA_FLAGS, i2);
                            }
                            obtainMessage
                                    .getData()
                                    .putInt(TrustManager.DATA_NEWLY_UNLOCKED, z2 ? 1 : 0);
                            obtainMessage
                                    .getData()
                                    .putCharSequenceArrayList(
                                            TrustManager.DATA_GRANTED_MESSAGES, (ArrayList) list);
                            obtainMessage.sendToTarget();
                        }

                        @Override // android.app.trust.ITrustListener
                        public void onEnabledTrustAgentsChanged(int userId) {
                            Message m =
                                    TrustManager.this.mHandler.obtainMessage(
                                            4, userId, 0, trustListener);
                            m.sendToTarget();
                        }

                        @Override // android.app.trust.ITrustListener
                        public void onTrustManagedChanged(boolean z, int i) {
                            TrustManager.this
                                    .mHandler
                                    .obtainMessage(2, z ? 1 : 0, i, trustListener)
                                    .sendToTarget();
                        }

                        @Override // android.app.trust.ITrustListener
                        public void onTrustError(CharSequence message) {
                            Message m = TrustManager.this.mHandler.obtainMessage(3, trustListener);
                            m.getData().putCharSequence("message", message);
                            m.sendToTarget();
                        }

                        @Override // android.app.trust.ITrustListener
                        public void onIsActiveUnlockRunningChanged(boolean z, int i) {
                            TrustManager.this
                                    .mHandler
                                    .obtainMessage(5, z ? 1 : 0, i, trustListener)
                                    .sendToTarget();
                        }
                    };
            this.mService.registerTrustListener(iTrustListener);
            this.mTrustListeners.put(trustListener, iTrustListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterTrustListener(TrustListener trustListener) {
        ITrustListener iTrustListener = this.mTrustListeners.remove(trustListener);
        if (iTrustListener != null) {
            try {
                this.mService.unregisterTrustListener(iTrustListener);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public boolean isTrustUsuallyManaged(int userId) {
        try {
            return this.mService.isTrustUsuallyManaged(userId);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unlockedByBiometricForUser(int userId, BiometricSourceType source) {
        try {
            this.mService.unlockedByBiometricForUser(userId, source);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearAllBiometricRecognized(BiometricSourceType source, int unlockedUser) {
        try {
            this.mService.clearAllBiometricRecognized(source, unlockedUser);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isInSignificantPlace() {
        try {
            return this.mService.isInSignificantPlace();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
