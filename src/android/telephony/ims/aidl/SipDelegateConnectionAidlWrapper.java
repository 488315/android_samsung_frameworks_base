package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.ims.DelegateRegistrationState;
import android.telephony.ims.FeatureTagState;
import android.telephony.ims.SipDelegateConfiguration;
import android.telephony.ims.SipDelegateConnection;
import android.telephony.ims.SipDelegateImsConfiguration;
import android.telephony.ims.SipMessage;
import android.telephony.ims.aidl.ISipDelegateConnectionStateCallback;
import android.telephony.ims.aidl.ISipDelegateMessageCallback;
import android.telephony.ims.stub.DelegateConnectionMessageCallback;
import android.telephony.ims.stub.DelegateConnectionStateCallback;
import android.util.ArraySet;
import android.util.Log;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.UnaryOperator;

/* loaded from: classes4.dex */
public class SipDelegateConnectionAidlWrapper implements SipDelegateConnection, IBinder.DeathRecipient {
    private static final String LOG_TAG = "SipDelegateCAW";
    private final Executor mExecutor;
    private final DelegateConnectionMessageCallback mMessageCallback;
    private final DelegateConnectionStateCallback mStateCallback;
    private final ISipDelegateConnectionStateCallback.Stub mStateBinder = new AnonymousClass1();
    private final ISipDelegateMessageCallback.Stub mMessageBinder = new AnonymousClass2();
    private final AtomicReference<ISipDelegate> mDelegateBinder = new AtomicReference<>();

    static /* synthetic */ ISipDelegate lambda$invalidateSipDelegateBinder$1(ISipDelegate iSipDelegate) {
        return null;
    }

    /* renamed from: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends ISipDelegateConnectionStateCallback.Stub {
        AnonymousClass1() {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onCreated(ISipDelegate iSipDelegate) {
            SipDelegateConnectionAidlWrapper.this.associateSipDelegate(iSipDelegate);
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCreated$0();
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreated$0() {
            SipDelegateConnectionAidlWrapper.this.mStateCallback.onCreated(SipDelegateConnectionAidlWrapper.this);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onFeatureTagStatusChanged(final DelegateRegistrationState delegateRegistrationState, final List<FeatureTagState> list) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onFeatureTagStatusChanged$1(delegateRegistrationState, list);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onFeatureTagStatusChanged$1(DelegateRegistrationState delegateRegistrationState, List list) {
            SipDelegateConnectionAidlWrapper.this.mStateCallback.onFeatureTagStatusChanged(delegateRegistrationState, new ArraySet(list));
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onImsConfigurationChanged(final SipDelegateImsConfiguration sipDelegateImsConfiguration) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onImsConfigurationChanged$2(sipDelegateImsConfiguration);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onImsConfigurationChanged$2(SipDelegateImsConfiguration sipDelegateImsConfiguration) {
            SipDelegateConnectionAidlWrapper.this.mStateCallback.onImsConfigurationChanged(sipDelegateImsConfiguration);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onConfigurationChanged(final SipDelegateConfiguration sipDelegateConfiguration) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onConfigurationChanged$3(sipDelegateConfiguration);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onConfigurationChanged$3(SipDelegateConfiguration sipDelegateConfiguration) {
            SipDelegateConnectionAidlWrapper.this.mStateCallback.onConfigurationChanged(sipDelegateConfiguration);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateConnectionStateCallback
        public void onDestroyed(final int i) {
            SipDelegateConnectionAidlWrapper.this.invalidateSipDelegateBinder();
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onDestroyed$4(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDestroyed$4(int i) {
            SipDelegateConnectionAidlWrapper.this.mStateCallback.onDestroyed(i);
        }
    }

    /* renamed from: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$2, reason: invalid class name */
    class AnonymousClass2 extends ISipDelegateMessageCallback.Stub {
        AnonymousClass2() {
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageReceived(final SipMessage sipMessage) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onMessageReceived$0(sipMessage);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageReceived$0(SipMessage sipMessage) {
            SipDelegateConnectionAidlWrapper.this.mMessageCallback.onMessageReceived(sipMessage);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageSent(final String str) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$2$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onMessageSent$1(str);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageSent$1(String str) {
            SipDelegateConnectionAidlWrapper.this.mMessageCallback.onMessageSent(str);
        }

        @Override // android.telephony.ims.aidl.ISipDelegateMessageCallback
        public void onMessageSendFailure(final String str, final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipDelegateConnectionAidlWrapper.this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onMessageSendFailure$2(str, i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMessageSendFailure$2(String str, int i) {
            SipDelegateConnectionAidlWrapper.this.mMessageCallback.onMessageSendFailure(str, i);
        }
    }

    public SipDelegateConnectionAidlWrapper(Executor executor, DelegateConnectionStateCallback delegateConnectionStateCallback, DelegateConnectionMessageCallback delegateConnectionMessageCallback) {
        this.mExecutor = executor;
        this.mStateCallback = delegateConnectionStateCallback;
        this.mMessageCallback = delegateConnectionMessageCallback;
    }

    @Override // android.telephony.ims.SipDelegateConnection
    public void sendMessage(SipMessage sipMessage, long j) {
        try {
            ISipDelegate sipDelegateBinder = getSipDelegateBinder();
            if (sipDelegateBinder == null) {
                notifyLocalMessageFailedToSend(sipMessage, 2);
            } else {
                sipDelegateBinder.sendMessage(sipMessage, j);
            }
        } catch (RemoteException unused) {
            notifyLocalMessageFailedToSend(sipMessage, 1);
        }
    }

    @Override // android.telephony.ims.SipDelegateConnection
    public void notifyMessageReceived(String str) {
        try {
            ISipDelegate sipDelegateBinder = getSipDelegateBinder();
            if (sipDelegateBinder == null) {
                return;
            }
            sipDelegateBinder.notifyMessageReceived(str);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.SipDelegateConnection
    public void notifyMessageReceiveError(String str, int i) {
        try {
            ISipDelegate sipDelegateBinder = getSipDelegateBinder();
            if (sipDelegateBinder == null) {
                return;
            }
            sipDelegateBinder.notifyMessageReceiveError(str, i);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.telephony.ims.SipDelegateConnection
    public void cleanupSession(String str) {
        try {
            ISipDelegate sipDelegateBinder = getSipDelegateBinder();
            if (sipDelegateBinder == null) {
                return;
            }
            sipDelegateBinder.cleanupSession(str);
        } catch (RemoteException unused) {
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        invalidateSipDelegateBinder();
        this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$binderDied$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$binderDied$0() {
        this.mStateCallback.onDestroyed(1);
    }

    public ISipDelegateConnectionStateCallback getStateCallbackBinder() {
        return this.mStateBinder;
    }

    public ISipDelegateMessageCallback getMessageCallbackBinder() {
        return this.mMessageBinder;
    }

    public ISipDelegate getSipDelegateBinder() {
        return this.mDelegateBinder.get();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void associateSipDelegate(ISipDelegate iSipDelegate) {
        if (iSipDelegate != null) {
            try {
                iSipDelegate.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
                iSipDelegate = null;
            }
        }
        this.mDelegateBinder.set(iSipDelegate);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void invalidateSipDelegateBinder() {
        ISipDelegate andUpdate = this.mDelegateBinder.getAndUpdate(new UnaryOperator() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SipDelegateConnectionAidlWrapper.lambda$invalidateSipDelegateBinder$1((ISipDelegate) obj);
            }
        });
        if (andUpdate != null) {
            try {
                andUpdate.asBinder().unlinkToDeath(this, 0);
            } catch (NoSuchElementException e) {
                Log.i(LOG_TAG, "invalidateSipDelegateBinder: " + e);
            }
        }
    }

    private void notifyLocalMessageFailedToSend(SipMessage sipMessage, final int i) {
        final String viaBranchParameter = sipMessage.getViaBranchParameter();
        this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.aidl.SipDelegateConnectionAidlWrapper$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$notifyLocalMessageFailedToSend$2(viaBranchParameter, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$notifyLocalMessageFailedToSend$2(String str, int i) {
        this.mMessageCallback.onMessageSendFailure(str, i);
    }
}
