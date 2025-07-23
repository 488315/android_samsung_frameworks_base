package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsRegistrationAttributes;
import android.telephony.ims.SipDetails;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsRegistrationCallback;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import android.util.Log;
import com.android.internal.telephony.util.RemoteCallbackListExt;
import com.android.internal.telephony.util.TelephonyUtils;
import com.android.internal.util.ArrayUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class ImsRegistrationImplBase {
    private static final String LOG_TAG = "ImsRegistrationImplBase";
    public static final int REASON_ALLOWED_NETWORK_TYPES_CHANGED = 3;
    public static final int REASON_HANDOVER_FAILED = 6;
    public static final int REASON_NON_IMS_CAPABLE_NETWORK = 4;
    public static final int REASON_RADIO_POWER_OFF = 5;
    public static final int REASON_SIM_REFRESH = 2;
    public static final int REASON_SIM_REMOVED = 1;
    public static final int REASON_UNKNOWN = 0;
    public static final int REASON_VOPS_NOT_SUPPORTED = 7;
    private static final int REGISTRATION_STATE_UNKNOWN = -1;
    public static final int REGISTRATION_TECH_3G = 4;
    public static final int REGISTRATION_TECH_CROSS_SIM = 2;
    public static final int REGISTRATION_TECH_IWLAN = 1;
    public static final int REGISTRATION_TECH_LTE = 0;
    public static final int REGISTRATION_TECH_MAX = 5;
    public static final int REGISTRATION_TECH_NONE = -1;
    public static final int REGISTRATION_TECH_NR = 3;
    private final IImsRegistration mBinder;
    private final RemoteCallbackListExt<IImsRegistrationCallback> mCallbacks;
    private final RemoteCallbackListExt<IImsRegistrationCallback> mEmergencyCallbacks;
    private ImsReasonInfo mEmergencyLastDisconnectCause;
    private int mEmergencyLastDisconnectRadioTech;
    private int mEmergencyLastDisconnectSuggestedAction;
    private ImsRegistrationAttributes mEmergencyRegistrationAttributes;
    private int mEmergencyRegistrationState;
    private Executor mExecutor;
    private ImsReasonInfo mLastDisconnectCause;
    private int mLastDisconnectRadioTech;
    private int mLastDisconnectSuggestedAction;
    private final Object mLock;
    private ImsRegistrationAttributes mRegistrationAttributes;
    private int mRegistrationState;
    private Uri[] mUris;
    private boolean mUrisSet;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsDeregistrationReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsRegistrationTech {
    }

    public void triggerDeregistration(int i) {
    }

    @SystemApi
    public void triggerFullNetworkRegistration(int i, String str) {
    }

    @SystemApi
    public void triggerSipDelegateDeregistration() {
    }

    @SystemApi
    public void updateSipDelegateRegistration() {
    }

    @SystemApi
    public ImsRegistrationImplBase() {
        this.mBinder = new AnonymousClass1();
        this.mCallbacks = new RemoteCallbackListExt<>();
        this.mEmergencyCallbacks = new RemoteCallbackListExt<>();
        this.mLock = new Object();
        this.mRegistrationState = -1;
        this.mEmergencyRegistrationState = -1;
        this.mLastDisconnectCause = new ImsReasonInfo();
        this.mEmergencyLastDisconnectCause = new ImsReasonInfo();
        this.mLastDisconnectSuggestedAction = 0;
        this.mEmergencyLastDisconnectSuggestedAction = 0;
        this.mLastDisconnectRadioTech = -1;
        this.mEmergencyLastDisconnectRadioTech = -1;
        this.mUris = new Uri[0];
        this.mUrisSet = false;
    }

    @SystemApi
    public ImsRegistrationImplBase(Executor executor) {
        this.mBinder = new AnonymousClass1();
        this.mCallbacks = new RemoteCallbackListExt<>();
        this.mEmergencyCallbacks = new RemoteCallbackListExt<>();
        this.mLock = new Object();
        this.mRegistrationState = -1;
        this.mEmergencyRegistrationState = -1;
        this.mLastDisconnectCause = new ImsReasonInfo();
        this.mEmergencyLastDisconnectCause = new ImsReasonInfo();
        this.mLastDisconnectSuggestedAction = 0;
        this.mEmergencyLastDisconnectSuggestedAction = 0;
        this.mLastDisconnectRadioTech = -1;
        this.mEmergencyLastDisconnectRadioTech = -1;
        this.mUris = new Uri[0];
        this.mUrisSet = false;
        this.mExecutor = executor;
    }

    /* renamed from: android.telephony.ims.stub.ImsRegistrationImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends IImsRegistration.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$getRegistrationTechnology$0() {
            return Integer.valueOf(ImsRegistrationImplBase.this.mRegistrationAttributes == null ? -1 : ImsRegistrationImplBase.this.mRegistrationAttributes.getRegistrationTechnology());
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public int getRegistrationTechnology() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$getRegistrationTechnology$0;
                    lambda$getRegistrationTechnology$0 = ImsRegistrationImplBase.AnonymousClass1.this.lambda$getRegistrationTechnology$0();
                    return lambda$getRegistrationTechnology$0;
                }
            }, "getRegistrationTechnology")).intValue();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addRegistrationCallback(final IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$addRegistrationCallback$1(iImsRegistrationCallback, atomicReference);
                }
            }, "addRegistrationCallback");
            if (atomicReference.get() != null) {
                throw ((RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addRegistrationCallback$1(IImsRegistrationCallback iImsRegistrationCallback, AtomicReference atomicReference) {
            try {
                ImsRegistrationImplBase.this.addRegistrationCallback(iImsRegistrationCallback);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void addEmergencyRegistrationCallback(final IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$addEmergencyRegistrationCallback$2(iImsRegistrationCallback, atomicReference);
                }
            }, "addEmergencyRegistrationCallback");
            if (atomicReference.get() != null) {
                throw ((RemoteException) atomicReference.get());
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addEmergencyRegistrationCallback$2(IImsRegistrationCallback iImsRegistrationCallback, AtomicReference atomicReference) {
            try {
                ImsRegistrationImplBase.this.addEmergencyRegistrationCallback(iImsRegistrationCallback);
            } catch (RemoteException e) {
                atomicReference.set(e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeEmergencyRegistrationCallback(final IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$removeEmergencyRegistrationCallback$3(iImsRegistrationCallback);
                }
            }, "removeEmergencyRegistrationCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeEmergencyRegistrationCallback$3(IImsRegistrationCallback iImsRegistrationCallback) {
            ImsRegistrationImplBase.this.removeEmergencyRegistrationCallback(iImsRegistrationCallback);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeRegistrationCallback$4(IImsRegistrationCallback iImsRegistrationCallback) {
            ImsRegistrationImplBase.this.removeRegistrationCallback(iImsRegistrationCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void removeRegistrationCallback(final IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$removeRegistrationCallback$4(iImsRegistrationCallback);
                }
            }, "removeRegistrationCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerFullNetworkRegistration$5(int i, String str) {
            ImsRegistrationImplBase.this.triggerFullNetworkRegistration(i, str);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerFullNetworkRegistration(final int i, final String str) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerFullNetworkRegistration$5(i, str);
                }
            }, "triggerFullNetworkRegistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerUpdateSipDelegateRegistration$6() {
            ImsRegistrationImplBase.this.updateSipDelegateRegistration();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerUpdateSipDelegateRegistration() {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerUpdateSipDelegateRegistration$6();
                }
            }, "triggerUpdateSipDelegateRegistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerSipDelegateDeregistration$7() {
            ImsRegistrationImplBase.this.triggerSipDelegateDeregistration();
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerSipDelegateDeregistration() {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerSipDelegateDeregistration$7();
                }
            }, "triggerSipDelegateDeregistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$triggerDeregistration$8(int i) {
            ImsRegistrationImplBase.this.triggerDeregistration(i);
        }

        @Override // android.telephony.ims.aidl.IImsRegistration
        public void triggerDeregistration(final int i) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    ImsRegistrationImplBase.AnonymousClass1.this.lambda$triggerDeregistration$8(i);
                }
            }, "triggerDeregistration");
        }

        private void executeMethodAsync(final Runnable runnable, String str) throws RemoteException {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, ImsRegistrationImplBase.this.mExecutor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }

        private void executeMethodAsyncNoException(final Runnable runnable, String str) {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, ImsRegistrationImplBase.this.mExecutor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + str + " exception: " + e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final Supplier<T> supplier, String str) throws RemoteException {
            try {
                return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$1$$ExternalSyntheticLambda3
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = TelephonyUtils.runWithCleanCallingIdentity((Supplier<Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, ImsRegistrationImplBase.this.mExecutor).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(ImsRegistrationImplBase.LOG_TAG, "ImsRegistrationImplBase Binder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }
    }

    public final IImsRegistration getBinder() {
        return this.mBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        this.mCallbacks.register(iImsRegistrationCallback);
        updateNewCallbackWithState(iImsRegistrationCallback, false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) {
        this.mCallbacks.unregister(iImsRegistrationCallback);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) throws RemoteException {
        this.mEmergencyCallbacks.register(iImsRegistrationCallback);
        updateNewCallbackWithState(iImsRegistrationCallback, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeEmergencyRegistrationCallback(IImsRegistrationCallback iImsRegistrationCallback) {
        this.mEmergencyCallbacks.unregister(iImsRegistrationCallback);
    }

    @SystemApi
    public final void onRegistered(int i) {
        onRegistered(new ImsRegistrationAttributes.Builder(i).build());
    }

    @SystemApi
    public final void onRegistered(final ImsRegistrationAttributes imsRegistrationAttributes) {
        boolean isEmergency = isEmergency(imsRegistrationAttributes);
        if (isEmergency) {
            updateToEmergencyState(imsRegistrationAttributes, 2);
        } else {
            updateToState(imsRegistrationAttributes, 2);
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onRegistered$0(ImsRegistrationAttributes.this, (IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onRegistered$0(ImsRegistrationAttributes imsRegistrationAttributes, IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onRegistered(imsRegistrationAttributes);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onRegistered(int, Set) - Skipping callback.");
        }
    }

    @SystemApi
    public final void onRegistering(int i) {
        onRegistering(new ImsRegistrationAttributes.Builder(i).build());
    }

    @SystemApi
    public final void onRegistering(final ImsRegistrationAttributes imsRegistrationAttributes) {
        boolean isEmergency = isEmergency(imsRegistrationAttributes);
        if (isEmergency) {
            updateToEmergencyState(imsRegistrationAttributes, 1);
        } else {
            updateToState(imsRegistrationAttributes, 1);
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onRegistering$1(ImsRegistrationAttributes.this, (IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onRegistering$1(ImsRegistrationAttributes imsRegistrationAttributes, IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onRegistering(imsRegistrationAttributes);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onRegistering(int, Set) - Skipping callback.");
        }
    }

    @SystemApi
    public final void onDeregistered(ImsReasonInfo imsReasonInfo) {
        onDeregistered(imsReasonInfo, 0, -1);
    }

    @SystemApi
    public final void onDeregistered(ImsReasonInfo imsReasonInfo, int i, int i2) {
        ImsRegistrationAttributes build;
        if (this.mRegistrationAttributes != null) {
            build = new ImsRegistrationAttributes(i2, this.mRegistrationAttributes.getTransportType(), this.mRegistrationAttributes.getAttributeFlags(), this.mRegistrationAttributes.getFeatureTags());
        } else {
            build = new ImsRegistrationAttributes.Builder(i2).build();
        }
        onDeregistered(imsReasonInfo, i, build);
    }

    @SystemApi
    public final void onDeregistered(final ImsReasonInfo imsReasonInfo, final int i, ImsRegistrationAttributes imsRegistrationAttributes) {
        boolean isEmergency = isEmergency(imsRegistrationAttributes);
        final int registrationTechnology = imsRegistrationAttributes.getRegistrationTechnology();
        if (isEmergency) {
            updateToDisconnectedEmergencyState(imsReasonInfo, i, registrationTechnology);
        } else {
            updateToDisconnectedState(imsReasonInfo, i, registrationTechnology);
        }
        if (imsReasonInfo == null) {
            imsReasonInfo = new ImsReasonInfo();
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onDeregistered$2(ImsReasonInfo.this, i, registrationTechnology, (IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onDeregistered$2(ImsReasonInfo imsReasonInfo, int i, int i2, IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onDeregistered(imsReasonInfo, i, i2);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onDeregistered() - Skipping callback.");
        }
    }

    @SystemApi
    public final void onDeregistered(ImsReasonInfo imsReasonInfo, SipDetails sipDetails) {
        onDeregistered(imsReasonInfo, 0, -1, sipDetails);
    }

    @SystemApi
    public final void onDeregistered(final ImsReasonInfo imsReasonInfo, final int i, final int i2, final SipDetails sipDetails) {
        updateToDisconnectedState(imsReasonInfo, i, i2);
        if (imsReasonInfo == null) {
            imsReasonInfo = new ImsReasonInfo();
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onDeregistered$3(ImsReasonInfo.this, i, i2, sipDetails, (IImsRegistrationCallback) obj);
            }
        }, false);
    }

    static /* synthetic */ void lambda$onDeregistered$3(ImsReasonInfo imsReasonInfo, int i, int i2, SipDetails sipDetails, IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onDeregisteredWithDetails(imsReasonInfo, i, i2, sipDetails);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onDeregistered() - Skipping callback.");
        }
    }

    @SystemApi
    public final void onTechnologyChangeFailed(int i, ImsReasonInfo imsReasonInfo) {
        ImsRegistrationAttributes build;
        if (this.mRegistrationAttributes != null) {
            build = new ImsRegistrationAttributes(i, this.mRegistrationAttributes.getTransportType(), this.mRegistrationAttributes.getAttributeFlags(), this.mRegistrationAttributes.getFeatureTags());
        } else {
            build = new ImsRegistrationAttributes.Builder(i).build();
        }
        onTechnologyChangeFailed(imsReasonInfo, build);
    }

    @SystemApi
    public final void onTechnologyChangeFailed(final ImsReasonInfo imsReasonInfo, ImsRegistrationAttributes imsRegistrationAttributes) {
        boolean isEmergency = isEmergency(imsRegistrationAttributes);
        final int registrationTechnology = imsRegistrationAttributes.getRegistrationTechnology();
        if (imsReasonInfo == null) {
            imsReasonInfo = new ImsReasonInfo();
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.lambda$onTechnologyChangeFailed$4(registrationTechnology, imsReasonInfo, (IImsRegistrationCallback) obj);
            }
        }, isEmergency);
    }

    static /* synthetic */ void lambda$onTechnologyChangeFailed$4(int i, ImsReasonInfo imsReasonInfo, IImsRegistrationCallback iImsRegistrationCallback) {
        try {
            iImsRegistrationCallback.onTechnologyChangeFailed(i, imsReasonInfo);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onTechnologyChangeFailed() - Skipping callback.");
        }
    }

    @SystemApi
    public final void onSubscriberAssociatedUriChanged(final Uri[] uriArr) {
        synchronized (this.mLock) {
            this.mUris = (Uri[]) ArrayUtils.cloneOrNull(uriArr);
            this.mUrisSet = true;
        }
        broadcastToCallbacksLocked(new Consumer() { // from class: android.telephony.ims.stub.ImsRegistrationImplBase$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImsRegistrationImplBase.this.lambda$onSubscriberAssociatedUriChanged$5(uriArr, (IImsRegistrationCallback) obj);
            }
        }, false);
    }

    private boolean isEmergency(ImsRegistrationAttributes imsRegistrationAttributes) {
        return (imsRegistrationAttributes == null || (imsRegistrationAttributes.getAttributeFlags() & 2) == 0) ? false : true;
    }

    private void broadcastToCallbacksLocked(Consumer<IImsRegistrationCallback> consumer, boolean z) {
        if (z) {
            synchronized (this.mEmergencyCallbacks) {
                this.mEmergencyCallbacks.broadcastAction(consumer);
            }
        } else {
            synchronized (this.mCallbacks) {
                this.mCallbacks.broadcastAction(consumer);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onSubscriberAssociatedUriChanged, reason: merged with bridge method [inline-methods] */
    public void lambda$onSubscriberAssociatedUriChanged$5(IImsRegistrationCallback iImsRegistrationCallback, Uri[] uriArr) {
        try {
            iImsRegistrationCallback.onSubscriberAssociatedUriChanged(uriArr);
        } catch (RemoteException e) {
            Log.w(LOG_TAG, e + "onSubscriberAssociatedUriChanged() - Skipping callback.");
        }
    }

    private void updateToState(ImsRegistrationAttributes imsRegistrationAttributes, int i) {
        synchronized (this.mLock) {
            this.mRegistrationAttributes = imsRegistrationAttributes;
            this.mRegistrationState = i;
            this.mLastDisconnectCause = null;
            this.mLastDisconnectSuggestedAction = 0;
            this.mLastDisconnectRadioTech = -1;
        }
    }

    private void updateToEmergencyState(ImsRegistrationAttributes imsRegistrationAttributes, int i) {
        synchronized (this.mLock) {
            this.mEmergencyRegistrationAttributes = imsRegistrationAttributes;
            this.mEmergencyRegistrationState = i;
            this.mEmergencyLastDisconnectCause = null;
            this.mEmergencyLastDisconnectSuggestedAction = 0;
            this.mEmergencyLastDisconnectRadioTech = -1;
        }
    }

    private void updateToDisconnectedState(ImsReasonInfo imsReasonInfo, int i, int i2) {
        synchronized (this.mLock) {
            this.mUrisSet = false;
            this.mUris = null;
            updateToState(new ImsRegistrationAttributes.Builder(-1).build(), 0);
            if (imsReasonInfo != null) {
                this.mLastDisconnectCause = imsReasonInfo;
                this.mLastDisconnectSuggestedAction = i;
                this.mLastDisconnectRadioTech = i2;
            } else {
                Log.w(LOG_TAG, "updateToDisconnectedState: no ImsReasonInfo provided.");
                this.mLastDisconnectCause = new ImsReasonInfo();
            }
        }
    }

    private void updateToDisconnectedEmergencyState(ImsReasonInfo imsReasonInfo, int i, int i2) {
        synchronized (this.mLock) {
            this.mUrisSet = false;
            this.mUris = null;
            updateToEmergencyState(new ImsRegistrationAttributes.Builder(-1).build(), 0);
            if (imsReasonInfo != null) {
                this.mEmergencyLastDisconnectCause = imsReasonInfo;
                this.mEmergencyLastDisconnectSuggestedAction = i;
                this.mEmergencyLastDisconnectRadioTech = i2;
            } else {
                Log.w(LOG_TAG, "updateToDisconnectedState: no ImsReasonInfo provided.");
                this.mEmergencyLastDisconnectCause = new ImsReasonInfo();
            }
        }
    }

    private void updateNewCallbackWithState(IImsRegistrationCallback iImsRegistrationCallback, boolean z) throws RemoteException {
        int i;
        ImsRegistrationAttributes imsRegistrationAttributes;
        ImsReasonInfo imsReasonInfo;
        int i2;
        int i3;
        boolean z2;
        Uri[] uriArr;
        synchronized (this.mLock) {
            i = z ? this.mEmergencyRegistrationState : this.mRegistrationState;
            if (z) {
                imsRegistrationAttributes = this.mEmergencyRegistrationAttributes;
            } else {
                imsRegistrationAttributes = this.mRegistrationAttributes;
            }
            if (z) {
                imsReasonInfo = this.mEmergencyLastDisconnectCause;
            } else {
                imsReasonInfo = this.mLastDisconnectCause;
            }
            if (z) {
                i2 = this.mEmergencyLastDisconnectSuggestedAction;
            } else {
                i2 = this.mLastDisconnectSuggestedAction;
            }
            if (z) {
                i3 = this.mEmergencyLastDisconnectRadioTech;
            } else {
                i3 = this.mLastDisconnectRadioTech;
            }
            z2 = this.mUrisSet;
            uriArr = this.mUris;
        }
        if (i == 0) {
            iImsRegistrationCallback.onDeregistered(imsReasonInfo, i2, i3);
        } else if (i == 1) {
            iImsRegistrationCallback.onRegistering(imsRegistrationAttributes);
        } else if (i == 2) {
            iImsRegistrationCallback.onRegistered(imsRegistrationAttributes);
        }
        if (z2) {
            lambda$onSubscriberAssociatedUriChanged$5(iImsRegistrationCallback, uriArr);
        }
    }

    public final void setDefaultExecutor(Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }

    public final void clearRegistrationCache() {
        synchronized (this.mLock) {
            this.mUris = null;
            this.mUrisSet = false;
        }
    }
}
