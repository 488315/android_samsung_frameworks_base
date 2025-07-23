package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.ims.DelegateMessageCallback;
import android.telephony.ims.DelegateRequest;
import android.telephony.ims.DelegateStateCallback;
import android.telephony.ims.aidl.ISipDelegate;
import android.telephony.ims.aidl.ISipDelegateMessageCallback;
import android.telephony.ims.aidl.ISipDelegateStateCallback;
import android.telephony.ims.aidl.ISipTransport;
import android.telephony.ims.aidl.SipDelegateAidlWrapper;
import android.telephony.ims.stub.SipTransportImplBase;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes4.dex */
public class SipTransportImplBase {
    private static final String LOG_TAG = "SipTransportIB";
    private Executor mBinderExecutor;
    private final IBinder.DeathRecipient mDeathRecipient = new AnonymousClass1();
    private final ISipTransport.Stub mSipTransportImpl = new AnonymousClass2();
    private final ArrayList<SipDelegateAidlWrapper> mDelegates = new ArrayList<>();

    /* renamed from: android.telephony.ims.stub.SipTransportImplBase$1, reason: invalid class name */
    class AnonymousClass1 implements IBinder.DeathRecipient {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$0() {
            SipTransportImplBase.this.binderDiedInternal(null);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            SipTransportImplBase.this.mBinderExecutor.execute(new Runnable() { // from class: android.telephony.ims.stub.SipTransportImplBase$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    SipTransportImplBase.AnonymousClass1.this.lambda$binderDied$0();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$1(IBinder iBinder) {
            SipTransportImplBase.this.binderDiedInternal(iBinder);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(final IBinder iBinder) {
            SipTransportImplBase.this.mBinderExecutor.execute(new Runnable() { // from class: android.telephony.ims.stub.SipTransportImplBase$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SipTransportImplBase.AnonymousClass1.this.lambda$binderDied$1(iBinder);
                }
            });
        }
    }

    /* renamed from: android.telephony.ims.stub.SipTransportImplBase$2, reason: invalid class name */
    class AnonymousClass2 extends ISipTransport.Stub {
        AnonymousClass2() {
        }

        @Override // android.telephony.ims.aidl.ISipTransport
        public void createSipDelegate(final int i, final DelegateRequest delegateRequest, final ISipDelegateStateCallback iSipDelegateStateCallback, final ISipDelegateMessageCallback iSipDelegateMessageCallback) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipTransportImplBase.this.mBinderExecutor.execute(new Runnable() { // from class: android.telephony.ims.stub.SipTransportImplBase$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        SipTransportImplBase.AnonymousClass2.this.lambda$createSipDelegate$0(i, delegateRequest, iSipDelegateStateCallback, iSipDelegateMessageCallback);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$createSipDelegate$0(int i, DelegateRequest delegateRequest, ISipDelegateStateCallback iSipDelegateStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) {
            SipTransportImplBase.this.createSipDelegateInternal(i, delegateRequest, iSipDelegateStateCallback, iSipDelegateMessageCallback);
        }

        @Override // android.telephony.ims.aidl.ISipTransport
        public void destroySipDelegate(final ISipDelegate iSipDelegate, final int i) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                SipTransportImplBase.this.mBinderExecutor.execute(new Runnable() { // from class: android.telephony.ims.stub.SipTransportImplBase$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        SipTransportImplBase.AnonymousClass2.this.lambda$destroySipDelegate$1(iSipDelegate, i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$destroySipDelegate$1(ISipDelegate iSipDelegate, int i) {
            SipTransportImplBase.this.destroySipDelegateInternal(iSipDelegate, i);
        }
    }

    public SipTransportImplBase() {
    }

    public SipTransportImplBase(Executor executor) {
        if (executor == null) {
            throw new IllegalArgumentException("executor must not be null");
        }
        this.mBinderExecutor = executor;
    }

    public void createSipDelegate(int i, DelegateRequest delegateRequest, DelegateStateCallback delegateStateCallback, DelegateMessageCallback delegateMessageCallback) {
        throw new UnsupportedOperationException("createSipDelegate not implemented!");
    }

    public void destroySipDelegate(SipDelegate sipDelegate, int i) {
        throw new UnsupportedOperationException("destroySipDelegate not implemented!");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void createSipDelegateInternal(int i, DelegateRequest delegateRequest, ISipDelegateStateCallback iSipDelegateStateCallback, ISipDelegateMessageCallback iSipDelegateMessageCallback) {
        SipDelegateAidlWrapper sipDelegateAidlWrapper = new SipDelegateAidlWrapper(this.mBinderExecutor, iSipDelegateStateCallback, iSipDelegateMessageCallback);
        this.mDelegates.add(sipDelegateAidlWrapper);
        linkDeathRecipient(sipDelegateAidlWrapper);
        createSipDelegate(i, delegateRequest, sipDelegateAidlWrapper, sipDelegateAidlWrapper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void destroySipDelegateInternal(ISipDelegate iSipDelegate, int i) {
        SipDelegateAidlWrapper sipDelegateAidlWrapper;
        Iterator<SipDelegateAidlWrapper> it = this.mDelegates.iterator();
        while (true) {
            if (!it.hasNext()) {
                sipDelegateAidlWrapper = null;
                break;
            } else {
                sipDelegateAidlWrapper = it.next();
                if (Objects.equals(iSipDelegate, sipDelegateAidlWrapper.getDelegateBinder())) {
                    break;
                }
            }
        }
        if (sipDelegateAidlWrapper != null) {
            unlinkDeathRecipient(sipDelegateAidlWrapper);
            this.mDelegates.remove(sipDelegateAidlWrapper);
            destroySipDelegate(sipDelegateAidlWrapper.getDelegate(), i);
        } else {
            Log.w(LOG_TAG, "destroySipDelegateInternal, could not findSipDelegate corresponding to " + iSipDelegate);
        }
    }

    private void linkDeathRecipient(SipDelegateAidlWrapper sipDelegateAidlWrapper) {
        try {
            sipDelegateAidlWrapper.getStateCallbackBinder().asBinder().linkToDeath(this.mDeathRecipient, 0);
        } catch (RemoteException unused) {
            Log.w(LOG_TAG, "linkDeathRecipient, remote process already died, cleaning up.");
            this.mDeathRecipient.binderDied(sipDelegateAidlWrapper.getStateCallbackBinder().asBinder());
        }
    }

    private void unlinkDeathRecipient(SipDelegateAidlWrapper sipDelegateAidlWrapper) {
        try {
            sipDelegateAidlWrapper.getStateCallbackBinder().asBinder().unlinkToDeath(this.mDeathRecipient, 0);
        } catch (NoSuchElementException unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void binderDiedInternal(IBinder iBinder) {
        Iterator<SipDelegateAidlWrapper> it = this.mDelegates.iterator();
        while (it.hasNext()) {
            SipDelegateAidlWrapper next = it.next();
            if (iBinder == null || next.getStateCallbackBinder().asBinder().equals(iBinder)) {
                Log.w(LOG_TAG, "Binder death detected for " + next + ", calling destroy and removing.");
                this.mDelegates.remove(next);
                destroySipDelegate(next.getDelegate(), 1);
                return;
            }
        }
        Log.w(LOG_TAG, "Binder death detected for IBinder " + iBinder + ", but couldn't find matching SipDelegate");
    }

    public ISipTransport getBinder() {
        return this.mSipTransportImpl;
    }

    public final void setDefaultExecutor(Executor executor) {
        if (this.mBinderExecutor == null) {
            this.mBinderExecutor = executor;
        }
    }
}
