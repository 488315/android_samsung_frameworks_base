package android.telephony.ims;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsServiceController;
import android.telephony.ims.aidl.IImsServiceControllerListener;
import android.telephony.ims.aidl.ISipTransport;
import android.telephony.ims.feature.ImsFeature;
import android.telephony.ims.feature.MmTelFeature;
import android.telephony.ims.feature.RcsFeature;
import android.telephony.ims.stub.ImsConfigImplBase;
import android.telephony.ims.stub.ImsFeatureConfiguration;
import android.telephony.ims.stub.ImsRegistrationImplBase;
import android.telephony.ims.stub.SipTransportImplBase;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.internal.telephony.util.TelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@SystemApi
/* loaded from: classes4.dex */
public class ImsService extends Service {
    public static final long CAPABILITY_EMERGENCY_OVER_MMTEL = 1;
    public static final long CAPABILITY_SIP_DELEGATE_CREATION = 2;
    public static final long CAPABILITY_SUPPORTS_SIMULTANEOUS_CALLING = 8;
    public static final long CAPABILITY_TERMINAL_BASED_CALL_WAITING = 4;
    private static final String LOG_TAG = "ImsService";
    public static final String SERVICE_INTERFACE = "android.telephony.ims.ImsService";
    private Executor mExecutor;
    private IImsServiceControllerListener mListener;
    public static final long CAPABILITY_MAX_INDEX = Long.numberOfTrailingZeros(8);
    private static final Map<Long, String> CAPABILITIES_LOG_MAP = Map.of(1L, "EMERGENCY_OVER_MMTEL", 2L, "SIP_DELEGATE_CREATION", 4L, "TERMINAL_BASED_CALL_WAITING", 8L, "SIMULTANEOUS_CALLING");
    private final SparseArray<SparseArray<ImsFeature>> mFeaturesBySlot = new SparseArray<>();
    private final SparseArray<SparseBooleanArray> mCreateImsFeatureWithSlotIdFlagMap = new SparseArray<>();
    private final Object mListenerLock = new Object();
    private final Object mExecutorLock = new Object();
    protected final IBinder mImsServiceController = new AnonymousClass1();
    private final IBinder.DeathRecipient mDeathRecipient = new AnonymousClass2();

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsServiceCapability {
    }

    public static class Listener extends IImsServiceControllerListener.Stub {
        @Override // android.telephony.ims.aidl.IImsServiceControllerListener
        public void onUpdateSupportedImsFeatures(ImsFeatureConfiguration imsFeatureConfiguration) {
        }
    }

    @Deprecated
    public MmTelFeature createMmTelFeature(int i) {
        return null;
    }

    @Deprecated
    public RcsFeature createRcsFeature(int i) {
        return null;
    }

    @Deprecated
    public void disableIms(int i) {
    }

    @Deprecated
    public void enableIms(int i) {
    }

    public long getImsServiceCapabilities() {
        return 0L;
    }

    public SipTransportImplBase getSipTransport(int i) {
        return null;
    }

    public void readyForFeatureCreation() {
    }

    /* renamed from: android.telephony.ims.ImsService$1, reason: invalid class name */
    class AnonymousClass1 extends IImsServiceController.Stub {
        AnonymousClass1() {
        }

        /* JADX WARN: Removed duplicated region for block: B:13:0x0048 A[Catch: all -> 0x0082, TryCatch #1 {, blocks: (B:4:0x0007, B:6:0x0010, B:8:0x0020, B:10:0x0034, B:11:0x003b, B:13:0x0048, B:14:0x0055, B:16:0x0057, B:19:0x0080, B:18:0x0073), top: B:26:0x0007, inners: #0, #2 }] */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        @Override // android.telephony.ims.aidl.IImsServiceController
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void setListener(IImsServiceControllerListener iImsServiceControllerListener) {
            synchronized (ImsService.this.mListenerLock) {
                if (ImsService.this.mListener != null && ImsService.this.mListener.asBinder().isBinderAlive()) {
                    try {
                        ImsService.this.mListener.asBinder().unlinkToDeath(ImsService.this.mDeathRecipient, 0);
                    } catch (NoSuchElementException unused) {
                        Log.w(ImsService.LOG_TAG, "IImsServiceControllerListener does not exist");
                    }
                    ImsService.this.mListener = iImsServiceControllerListener;
                    if (ImsService.this.mListener != null) {
                    }
                } else {
                    ImsService.this.mListener = iImsServiceControllerListener;
                    if (ImsService.this.mListener != null) {
                        ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$setListener$0();
                            }
                        }, "releaseResource");
                        return;
                    }
                    try {
                        ImsService.this.mListener.asBinder().linkToDeath(ImsService.this.mDeathRecipient, 0);
                        Log.i(ImsService.LOG_TAG, "setListener: register linkToDeath");
                    } catch (RemoteException unused2) {
                        ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda10
                            @Override // java.lang.Runnable
                            public final void run() {
                                this.f$0.lambda$setListener$1();
                            }
                        }, "releaseResource");
                    }
                    return;
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$0() {
            ImsService.this.releaseResource();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$1() {
            ImsService.this.releaseResource();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsMmTelFeature createMmTelFeature(final int i, final int i2) {
            MmTelFeature mmTelFeature = (MmTelFeature) ImsService.this.getImsFeature(i, 1);
            if (mmTelFeature == null) {
                return (IImsMmTelFeature) ImsService.this.executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.lambda$createMmTelFeature$2(i, i2);
                    }
                }, "createMmTelFeature");
            }
            return mmTelFeature.getBinder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsMmTelFeature lambda$createMmTelFeature$2(int i, int i2) {
            return ImsService.this.createMmTelFeatureInternal(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsMmTelFeature createEmergencyOnlyMmTelFeature(final int i) {
            MmTelFeature mmTelFeature = (MmTelFeature) ImsService.this.getImsFeature(i, 1);
            if (mmTelFeature == null) {
                return (IImsMmTelFeature) ImsService.this.executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda11
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.lambda$createEmergencyOnlyMmTelFeature$3(i);
                    }
                }, "createEmergencyOnlyMmTelFeature");
            }
            return mmTelFeature.getBinder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsMmTelFeature lambda$createEmergencyOnlyMmTelFeature$3(int i) {
            return ImsService.this.createEmergencyOnlyMmTelFeatureInternal(i);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsRcsFeature createRcsFeature(final int i, final int i2) {
            RcsFeature rcsFeature = (RcsFeature) ImsService.this.getImsFeature(i, 2);
            if (rcsFeature == null) {
                return (IImsRcsFeature) ImsService.this.executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda14
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return this.f$0.lambda$createRcsFeature$4(i, i2);
                    }
                }, "createRcsFeature");
            }
            return rcsFeature.getBinder();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsRcsFeature lambda$createRcsFeature$4(int i, int i2) {
            return ImsService.this.createRcsFeatureInternal(i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addFeatureStatusCallback$5(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            ImsService.this.addImsFeatureStatusCallback(i, i2, iImsFeatureStatusCallback);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void addFeatureStatusCallback(final int i, final int i2, final IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$addFeatureStatusCallback$5(i, i2, iImsFeatureStatusCallback);
                }
            }, "addFeatureStatusCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeFeatureStatusCallback$6(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            ImsService.this.removeImsFeatureStatusCallback(i, i2, iImsFeatureStatusCallback);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void removeFeatureStatusCallback(final int i, final int i2, final IImsFeatureStatusCallback iImsFeatureStatusCallback) {
            ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeFeatureStatusCallback$6(i, i2, iImsFeatureStatusCallback);
                }
            }, "removeFeatureStatusCallback");
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void removeImsFeature(final int i, final int i2, boolean z) {
            if (z && ImsService.this.isImsFeatureCreatedForSlot(i, i2)) {
                Log.w(ImsService.LOG_TAG, "Do not remove Ims feature for compatibility");
            } else {
                ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$removeImsFeature$7(i, i2);
                    }
                }, "removeImsFeature");
                ImsService.this.setImsFeatureCreatedForSlot(i, i2, false);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeImsFeature$7(int i, int i2) {
            ImsService.this.removeImsFeature(i, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ImsFeatureConfiguration lambda$querySupportedImsFeatures$8() {
            return ImsService.this.querySupportedImsFeatures();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public ImsFeatureConfiguration querySupportedImsFeatures() {
            return (ImsFeatureConfiguration) ImsService.this.executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda12
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$querySupportedImsFeatures$8();
                }
            }, "ImsFeatureConfiguration");
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public long getImsServiceCapabilities() {
            return ((Long) ImsService.this.executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getImsServiceCapabilities$9();
                }
            }, "getImsServiceCapabilities")).longValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Long lambda$getImsServiceCapabilities$9() {
            long imsServiceCapabilities = ImsService.this.getImsServiceCapabilities();
            long jSanitizeCapabilities = ImsService.sanitizeCapabilities(imsServiceCapabilities);
            if (imsServiceCapabilities != jSanitizeCapabilities) {
                Log.w(ImsService.LOG_TAG, "removing invalid bits from field: 0x" + Long.toHexString(imsServiceCapabilities ^ jSanitizeCapabilities));
            }
            return Long.valueOf(jSanitizeCapabilities);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifyImsServiceReadyForFeatureCreation$10() {
            ImsService.this.readyForFeatureCreation();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void notifyImsServiceReadyForFeatureCreation() {
            ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifyImsServiceReadyForFeatureCreation$10();
                }
            }, "notifyImsServiceReadyForFeatureCreation");
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsConfig getConfig(final int i, final int i2) {
            return (IImsConfig) ImsService.this.executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda13
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getConfig$11(i, i2);
                }
            }, "getConfig");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsConfig lambda$getConfig$11(int i, int i2) {
            ImsConfigImplBase configForSubscription = ImsService.this.getConfigForSubscription(i, i2);
            if (configForSubscription == null) {
                return null;
            }
            configForSubscription.setDefaultExecutor(ImsService.this.getCachedExecutor());
            return configForSubscription.getIImsConfig();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public IImsRegistration getRegistration(final int i, final int i2) {
            return (IImsRegistration) ImsService.this.executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getRegistration$12(i, i2);
                }
            }, "getRegistration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsRegistration lambda$getRegistration$12(int i, int i2) {
            ImsRegistrationImplBase registrationForSubscription = ImsService.this.getRegistrationForSubscription(i, i2);
            if (registrationForSubscription == null) {
                return null;
            }
            registrationForSubscription.setDefaultExecutor(ImsService.this.getCachedExecutor());
            return registrationForSubscription.getBinder();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public ISipTransport getSipTransport(final int i) {
            return (ISipTransport) ImsService.this.executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getSipTransport$13(i);
                }
            }, "getSipTransport");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ISipTransport lambda$getSipTransport$13(int i) {
            SipTransportImplBase sipTransport = ImsService.this.getSipTransport(i);
            if (sipTransport == null) {
                return null;
            }
            sipTransport.setDefaultExecutor(ImsService.this.getCachedExecutor());
            return sipTransport.getBinder();
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void enableIms(final int i, final int i2) {
            ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$enableIms$14(i, i2);
                }
            }, "enableIms");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$enableIms$14(int i, int i2) {
            ImsService.this.enableImsForSubscription(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void disableIms(final int i, final int i2) {
            ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$disableIms$15(i, i2);
                }
            }, "disableIms");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$disableIms$15(int i, int i2) {
            ImsService.this.disableImsForSubscription(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsServiceController
        public void resetIms(final int i, final int i2) {
            ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$resetIms$16(i, i2);
                }
            }, "resetIms");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resetIms$16(int i, int i2) {
            ImsService.this.resetImsInternal(i, i2);
        }
    }

    /* renamed from: android.telephony.ims.ImsService$2, reason: invalid class name */
    class AnonymousClass2 implements IBinder.DeathRecipient {
        AnonymousClass2() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Log.w(ImsService.LOG_TAG, "IImsServiceControllerListener binder to framework has died. Cleaning up");
            ImsService.this.executeMethodAsync(new Runnable() { // from class: android.telephony.ims.ImsService$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$binderDied$0();
                }
            }, "releaseResource");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$binderDied$0() {
            ImsService.this.releaseResource();
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        Log.i(LOG_TAG, "ImsService Bound.");
        return this.mImsServiceController;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Executor getCachedExecutor() {
        Executor executor;
        synchronized (this.mExecutorLock) {
            if (this.mExecutor == null) {
                Executor executor2 = getExecutor();
                if (executor2 == null) {
                    executor2 = new PendingIntent$$ExternalSyntheticLambda0();
                }
                this.mExecutor = executor2;
            }
            executor = this.mExecutor;
        }
        return executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IImsMmTelFeature createMmTelFeatureInternal(int i, int i2) {
        MmTelFeature mmTelFeatureCreateMmTelFeatureForSubscription = createMmTelFeatureForSubscription(i, i2);
        if (mmTelFeatureCreateMmTelFeatureForSubscription != null) {
            setupFeature(mmTelFeatureCreateMmTelFeatureForSubscription, i, 1);
            mmTelFeatureCreateMmTelFeatureForSubscription.setDefaultExecutor(getCachedExecutor());
            return mmTelFeatureCreateMmTelFeatureForSubscription.getBinder();
        }
        Log.e(LOG_TAG, "createMmTelFeatureInternal: null feature returned.");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IImsMmTelFeature createEmergencyOnlyMmTelFeatureInternal(int i) {
        MmTelFeature mmTelFeatureCreateEmergencyOnlyMmTelFeature = createEmergencyOnlyMmTelFeature(i);
        if (mmTelFeatureCreateEmergencyOnlyMmTelFeature != null) {
            setupFeature(mmTelFeatureCreateEmergencyOnlyMmTelFeature, i, 1);
            mmTelFeatureCreateEmergencyOnlyMmTelFeature.setDefaultExecutor(getCachedExecutor());
            return mmTelFeatureCreateEmergencyOnlyMmTelFeature.getBinder();
        }
        Log.e(LOG_TAG, "createEmergencyOnlyMmTelFeatureInternal: null feature returned.");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IImsRcsFeature createRcsFeatureInternal(int i, int i2) {
        RcsFeature rcsFeatureCreateRcsFeatureForSubscription = createRcsFeatureForSubscription(i, i2);
        if (rcsFeatureCreateRcsFeatureForSubscription != null) {
            rcsFeatureCreateRcsFeatureForSubscription.setDefaultExecutor(getCachedExecutor());
            setupFeature(rcsFeatureCreateRcsFeatureForSubscription, i, 2);
            return rcsFeatureCreateRcsFeatureForSubscription.getBinder();
        }
        Log.e(LOG_TAG, "createRcsFeatureInternal: null feature returned.");
        return null;
    }

    private void setupFeature(ImsFeature imsFeature, int i, int i2) {
        imsFeature.initialize(this, i);
        addImsFeature(i, i2, imsFeature);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addImsFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                Log.w(LOG_TAG, "Can not add ImsFeatureStatusCallback - no features on slot " + i);
            } else {
                ImsFeature imsFeature = sparseArray.get(i2);
                if (imsFeature != null) {
                    imsFeature.addImsFeatureStatusCallback(iImsFeatureStatusCallback);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsFeatureStatusCallback(int i, int i2, IImsFeatureStatusCallback iImsFeatureStatusCallback) {
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                Log.w(LOG_TAG, "Can not remove ImsFeatureStatusCallback - no features on slot " + i);
            } else {
                ImsFeature imsFeature = sparseArray.get(i2);
                if (imsFeature != null) {
                    imsFeature.removeImsFeatureStatusCallback(iImsFeatureStatusCallback);
                }
            }
        }
    }

    private void addImsFeature(int i, int i2, ImsFeature imsFeature) {
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                sparseArray = new SparseArray<>();
                this.mFeaturesBySlot.put(i, sparseArray);
            }
            sparseArray.put(i2, imsFeature);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void removeImsFeature(int i, int i2) {
        notifySubscriptionRemoved(i);
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                Log.w(LOG_TAG, "Can not remove ImsFeature. No ImsFeatures exist on slot " + i);
                return;
            }
            ImsFeature imsFeature = sparseArray.get(i2);
            if (imsFeature == null) {
                Log.w(LOG_TAG, "Can not remove ImsFeature. No feature with type " + i2 + " exists on slot " + i);
                return;
            }
            imsFeature.onFeatureRemoved();
            sparseArray.remove(i2);
        }
    }

    public ImsFeature getImsFeature(int i, int i2) {
        synchronized (this.mFeaturesBySlot) {
            SparseArray<ImsFeature> sparseArray = this.mFeaturesBySlot.get(i);
            if (sparseArray == null) {
                return null;
            }
            return sparseArray.get(i2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setImsFeatureCreatedForSlot(int i, int i2, boolean z) {
        synchronized (this.mCreateImsFeatureWithSlotIdFlagMap) {
            getImsFeatureCreatedForSlot(i).put(i2, z);
        }
    }

    public boolean isImsFeatureCreatedForSlot(int i, int i2) {
        boolean z;
        synchronized (this.mCreateImsFeatureWithSlotIdFlagMap) {
            z = getImsFeatureCreatedForSlot(i).get(i2);
        }
        return z;
    }

    private SparseBooleanArray getImsFeatureCreatedForSlot(int i) {
        SparseBooleanArray sparseBooleanArray = this.mCreateImsFeatureWithSlotIdFlagMap.get(i);
        if (sparseBooleanArray != null) {
            return sparseBooleanArray;
        }
        SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray();
        this.mCreateImsFeatureWithSlotIdFlagMap.put(i, sparseBooleanArray2);
        return sparseBooleanArray2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseResource() {
        Log.w(LOG_TAG, "cleaning up features");
        synchronized (this.mFeaturesBySlot) {
            for (int i = 0; i < this.mFeaturesBySlot.size(); i++) {
                SparseArray<ImsFeature> sparseArrayValueAt = this.mFeaturesBySlot.valueAt(i);
                if (sparseArrayValueAt != null) {
                    for (int i2 = 0; i2 < sparseArrayValueAt.size(); i2++) {
                        ImsFeature imsFeatureValueAt = sparseArrayValueAt.valueAt(i2);
                        if (imsFeatureValueAt != null) {
                            imsFeatureValueAt.onFeatureRemoved();
                        }
                    }
                    sparseArrayValueAt.clear();
                }
            }
            this.mFeaturesBySlot.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeMethodAsync(final Runnable runnable, String str) {
        try {
            CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.ImsService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TelephonyUtils.runWithCleanCallingIdentity(runnable);
                }
            }, getCachedExecutor()).join();
        } catch (CancellationException | CompletionException e) {
            Log.w(LOG_TAG, "ImsService Binder - " + str + " exception: " + e.getMessage());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <T> T executeMethodAsyncForResult(final Supplier<T> supplier, String str) {
        try {
            return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.ImsService$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return TelephonyUtils.runWithCleanCallingIdentity(supplier);
                }
            }, getCachedExecutor()).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.w(LOG_TAG, "ImsService Binder - " + str + " exception: " + e.getMessage());
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetImsInternal(int i, int i2) {
        try {
            resetIms(i);
        } catch (UnsupportedOperationException unused) {
            disableImsForSubscription(i, i2);
        }
    }

    public ImsFeatureConfiguration querySupportedImsFeatures() {
        return new ImsFeatureConfiguration();
    }

    public final void onUpdateSupportedImsFeatures(ImsFeatureConfiguration imsFeatureConfiguration) throws RemoteException {
        IImsServiceControllerListener iImsServiceControllerListener;
        synchronized (this.mListenerLock) {
            iImsServiceControllerListener = this.mListener;
            if (iImsServiceControllerListener == null) {
                throw new IllegalStateException("Framework is not ready");
            }
        }
        iImsServiceControllerListener.onUpdateSupportedImsFeatures(imsFeatureConfiguration);
    }

    public void enableImsForSubscription(int i, int i2) {
        enableIms(i);
    }

    public void disableImsForSubscription(int i, int i2) {
        disableIms(i);
    }

    private void notifySubscriptionRemoved(int i) {
        ImsRegistrationImplBase registration = getRegistration(i);
        if (registration != null) {
            registration.clearRegistrationCache();
        }
        ImsConfigImplBase config = getConfig(i);
        if (config != null) {
            config.clearConfigurationCache();
        }
    }

    public void resetIms(int i) {
        throw new UnsupportedOperationException();
    }

    public MmTelFeature createMmTelFeatureForSubscription(int i, int i2) {
        setImsFeatureCreatedForSlot(i, 1, true);
        return createMmTelFeature(i);
    }

    public RcsFeature createRcsFeatureForSubscription(int i, int i2) {
        setImsFeatureCreatedForSlot(i, 2, true);
        return createRcsFeature(i);
    }

    public MmTelFeature createEmergencyOnlyMmTelFeature(int i) {
        setImsFeatureCreatedForSlot(i, 1, true);
        return createMmTelFeature(i);
    }

    public ImsConfigImplBase getConfigForSubscription(int i, int i2) {
        return getConfig(i);
    }

    public ImsRegistrationImplBase getRegistrationForSubscription(int i, int i2) {
        return getRegistration(i);
    }

    @Deprecated
    public ImsConfigImplBase getConfig(int i) {
        return new ImsConfigImplBase();
    }

    @Deprecated
    public ImsRegistrationImplBase getRegistration(int i) {
        return new ImsRegistrationImplBase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static long sanitizeCapabilities(long j) {
        return j & (~((-1) << ((int) (CAPABILITY_MAX_INDEX + 1)))) & (-2);
    }

    public static String getCapabilitiesString(long j) {
        StringBuffer stringBuffer = new StringBuffer("capabilities={ ");
        long j2 = -1;
        for (long j3 = 0; (j & j2) != 0 && j3 <= 63; j3++) {
            long j4 = 1 << ((int) j3);
            if ((j & j4) != 0) {
                stringBuffer.append(CAPABILITIES_LOG_MAP.getOrDefault(Long.valueOf(j4), j4 + "?"));
                stringBuffer.append(" ");
            }
            j2 <<= 1;
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public Executor getExecutor() {
        return new PendingIntent$$ExternalSyntheticLambda0();
    }
}
