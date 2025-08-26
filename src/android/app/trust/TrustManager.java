package android.app.trust;

import android.app.trust.ITrustListener;
import android.app.trust.ITrustManager;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.ArrayMap;
import com.android.internal.policy.IDeviceLockedStateListener;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class TrustManager {
    public static final String ACTION_BIND_SIGNIFICANT_PLACE_PROVIDER = "com.android.trust.provider.SignificantPlaceProvider.BIND";
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
    private final Handler mHandler = new Handler(this, Looper.getMainLooper()) { // from class: android.app.trust.TrustManager.2
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Bundle bundlePeekData = message.peekData();
                ((TrustListener) message.obj).onTrustChanged(message.arg1 != 0, (bundlePeekData != null ? bundlePeekData.getInt(TrustManager.DATA_NEWLY_UNLOCKED) : 0) != 0, message.arg2, bundlePeekData != null ? bundlePeekData.getInt(TrustManager.DATA_FLAGS) : 0, message.getData().getStringArrayList(TrustManager.DATA_GRANTED_MESSAGES));
            } else {
                if (i == 2) {
                    ((TrustListener) message.obj).onTrustManagedChanged(message.arg1 != 0, message.arg2);
                    return;
                }
                if (i == 3) {
                    ((TrustListener) message.obj).onTrustError(message.peekData().getCharSequence("message"));
                } else if (i == 4) {
                    ((TrustListener) message.obj).onEnabledTrustAgentsChanged(message.arg1);
                } else {
                    if (i != 5) {
                        return;
                    }
                    ((TrustListener) message.obj).onIsActiveUnlockRunningChanged(message.arg1 != 0, message.arg2);
                }
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

    public TrustManager(IBinder iBinder) {
        this.mService = ITrustManager.Stub.asInterface(iBinder);
    }

    public void setDeviceLockedForUser(int i, boolean z) {
        try {
            this.mService.setDeviceLockedForUser(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUnlockAttempt(boolean z, int i) {
        try {
            this.mService.reportUnlockAttempt(z, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserRequestedUnlock(int i, boolean z) {
        try {
            this.mService.reportUserRequestedUnlock(i, z);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUserMayRequestUnlock(int i) {
        try {
            this.mService.reportUserMayRequestUnlock(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportUnlockLockout(int i, int i2) {
        try {
            this.mService.reportUnlockLockout(i, i2);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void reportEnabledTrustAgentsChanged(int i) {
        try {
            this.mService.reportEnabledTrustAgentsChanged(i);
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

    public boolean isActiveUnlockRunning(int i) {
        try {
            return this.mService.isActiveUnlockRunning(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void registerTrustListener(final TrustListener trustListener) {
        try {
            ITrustListener.Stub stub = new ITrustListener.Stub() { // from class: android.app.trust.TrustManager.1
                @Override // android.app.trust.ITrustListener
                public void onTrustChanged(boolean z, boolean z2, int i, int i2, List<String> list) {
                    Message messageObtainMessage = TrustManager.this.mHandler.obtainMessage(1, z ? 1 : 0, i, trustListener);
                    if (i2 != 0) {
                        messageObtainMessage.getData().putInt(TrustManager.DATA_FLAGS, i2);
                    }
                    messageObtainMessage.getData().putInt(TrustManager.DATA_NEWLY_UNLOCKED, z2 ? 1 : 0);
                    messageObtainMessage.getData().putCharSequenceArrayList(TrustManager.DATA_GRANTED_MESSAGES, (ArrayList) list);
                    messageObtainMessage.sendToTarget();
                }

                @Override // android.app.trust.ITrustListener
                public void onEnabledTrustAgentsChanged(int i) {
                    TrustManager.this.mHandler.obtainMessage(4, i, 0, trustListener).sendToTarget();
                }

                @Override // android.app.trust.ITrustListener
                public void onTrustManagedChanged(boolean z, int i) {
                    TrustManager.this.mHandler.obtainMessage(2, z ? 1 : 0, i, trustListener).sendToTarget();
                }

                @Override // android.app.trust.ITrustListener
                public void onTrustError(CharSequence charSequence) {
                    Message messageObtainMessage = TrustManager.this.mHandler.obtainMessage(3, trustListener);
                    messageObtainMessage.getData().putCharSequence("message", charSequence);
                    messageObtainMessage.sendToTarget();
                }

                @Override // android.app.trust.ITrustListener
                public void onIsActiveUnlockRunningChanged(boolean z, int i) {
                    TrustManager.this.mHandler.obtainMessage(5, z ? 1 : 0, i, trustListener).sendToTarget();
                }
            };
            this.mService.registerTrustListener(stub);
            this.mTrustListeners.put(trustListener, stub);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterTrustListener(TrustListener trustListener) {
        ITrustListener iTrustListenerRemove = this.mTrustListeners.remove(trustListener);
        if (iTrustListenerRemove != null) {
            try {
                this.mService.unregisterTrustListener(iTrustListenerRemove);
            } catch (RemoteException e) {
                throw e.rethrowFromSystemServer();
            }
        }
    }

    public void registerDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener, int i) {
        try {
            this.mService.registerDeviceLockedStateListener(iDeviceLockedStateListener, i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unregisterDeviceLockedStateListener(IDeviceLockedStateListener iDeviceLockedStateListener) {
        try {
            this.mService.unregisterDeviceLockedStateListener(iDeviceLockedStateListener);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public boolean isTrustUsuallyManaged(int i) {
        try {
            return this.mService.isTrustUsuallyManaged(i);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void unlockedByBiometricForUser(int i, BiometricSourceType biometricSourceType) {
        try {
            this.mService.unlockedByBiometricForUser(i, biometricSourceType);
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    public void clearAllBiometricRecognized(BiometricSourceType biometricSourceType, int i) {
        try {
            this.mService.clearAllBiometricRecognized(biometricSourceType, i);
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
