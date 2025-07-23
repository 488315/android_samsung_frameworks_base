package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.ims.DelegateMessageCallback;
import android.telephony.ims.DelegateRegistrationState;
import android.telephony.ims.DelegateStateCallback;
import android.telephony.ims.FeatureTagState;
import android.telephony.ims.SipDelegateConfiguration;
import android.telephony.ims.SipDelegateImsConfiguration;
import android.telephony.ims.SipMessage;
import android.telephony.ims.aidl.ISipDelegate;
import android.telephony.ims.stub.SipDelegate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class SipDelegateAidlWrapper implements DelegateStateCallback, DelegateMessageCallback {
    private static final String LOG_TAG = "SipDelegateAW";
    private volatile SipDelegate mDelegate;
    private final ISipDelegate.Stub mDelegateBinder = new AnonymousClass1();
    private final Executor mExecutor;
    private final ISipDelegateMessageCallback mMessageBinder;
    private final ISipDelegateStateCallback mStateBinder;

    /* renamed from: android.telephony.ims.aidl.SipDelegateAidlWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends ISipDelegate.Stub {
        AnonymousClass1() {
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void sendMessage(final SipMessage sipMessage, final long j) {
            final SipDelegate sipDelegate = SipDelegateAidlWrapper.this.mDelegate;
            if (sipDelegate == null) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SipDelegate.this.sendMessage(sipMessage, j);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceived(final String str) {
            final SipDelegate sipDelegate = SipDelegateAidlWrapper.this.mDelegate;
            if (sipDelegate == null) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        SipDelegate.this.notifyMessageReceived(str);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void notifyMessageReceiveError(final String str, final int i) {
            final SipDelegate sipDelegate = SipDelegateAidlWrapper.this.mDelegate;
            if (sipDelegate == null) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SipDelegate.this.notifyMessageReceiveError(str, i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        @Override // android.telephony.ims.aidl.ISipDelegate
        public void cleanupSession(final String str) {
            final SipDelegate sipDelegate = SipDelegateAidlWrapper.this.mDelegate;
            if (sipDelegate == null) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        SipDelegate.this.cleanupSession(str);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public SipDelegateAidlWrapper(Executor executor, ISipDelegateStateCallback iSipDelegateStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) {
        this.mExecutor = executor;
        this.mStateBinder = iSipDelegateStateCallback;
        this.mMessageBinder = iSipDelegateMessageCallback;
    }

    @Override // android.telephony.ims.DelegateMessageCallback
    public void onMessageReceived(SipMessage sipMessage) {
        try {
            this.mMessageBinder.onMessageReceived(sipMessage);
        } catch (RemoteException unused) {
            if (this.mDelegate != null) {
                notifyLocalMessageFailedToBeReceived(sipMessage, 1);
            }
        }
    }

    @Override // android.telephony.ims.DelegateMessageCallback
    public void onMessageSent(String str) {
        try {
            this.mMessageBinder.onMessageSent(str);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.DelegateMessageCallback
    public void onMessageSendFailure(String str, int i) {
        try {
            this.mMessageBinder.onMessageSendFailure(str, i);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onCreated(SipDelegate sipDelegate, Set<FeatureTagState> set) {
        this.mDelegate = sipDelegate;
        if (set == null) {
            set = Collections.EMPTY_SET;
        }
        try {
            this.mStateBinder.onCreated(this.mDelegateBinder, new ArrayList(set));
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onFeatureTagRegistrationChanged(DelegateRegistrationState delegateRegistrationState) {
        try {
            this.mStateBinder.onFeatureTagRegistrationChanged(delegateRegistrationState);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onImsConfigurationChanged(SipDelegateImsConfiguration sipDelegateImsConfiguration) {
        try {
            this.mStateBinder.onImsConfigurationChanged(sipDelegateImsConfiguration);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onConfigurationChanged(SipDelegateConfiguration sipDelegateConfiguration) {
        try {
            this.mStateBinder.onConfigurationChanged(sipDelegateConfiguration);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.DelegateStateCallback
    public void onDestroyed(int i) {
        this.mDelegate = null;
        try {
            this.mStateBinder.onDestroyed(i);
        } catch (RemoteException unused) {
        }
    }

    public SipDelegate getDelegate() {
        return this.mDelegate;
    }

    public ISipDelegate getDelegateBinder() {
        return this.mDelegateBinder;
    }

    public ISipDelegateStateCallback getStateCallbackBinder() {
        return this.mStateBinder;
    }

    private void notifyLocalMessageFailedToBeReceived(SipMessage sipMessage, final int i) {
        final String viaBranchParameter = sipMessage.getViaBranchParameter();
        final SipDelegate sipDelegate = this.mDelegate;
        if (sipDelegate != null) {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateAidlWrapper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SipDelegate.this.notifyMessageReceiveError(viaBranchParameter, i);
                }
            });
        }
    }
}
