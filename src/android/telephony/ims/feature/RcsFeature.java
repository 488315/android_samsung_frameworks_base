package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.Context;
import android.net.Uri;
import android.os.RemoteException;
import android.telephony.ims.aidl.CapabilityExchangeAidlWrapper;
import android.telephony.ims.aidl.ICapabilityExchangeEventListener;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IOptionsResponseCallback;
import android.telephony.ims.aidl.IPublishResponseCallback;
import android.telephony.ims.aidl.ISubscribeResponseCallback;
import android.telephony.ims.aidl.RcsOptionsResponseAidlWrapper;
import android.telephony.ims.aidl.RcsPublishResponseAidlWrapper;
import android.telephony.ims.aidl.RcsSubscribeResponseAidlWrapper;
import android.telephony.ims.feature.ImsFeature;
import android.telephony.ims.feature.RcsFeature;
import android.telephony.ims.stub.CapabilityExchangeEventListener;
import android.telephony.ims.stub.RcsCapabilityExchangeImplBase;
import android.util.Log;
import com.android.internal.telephony.util.TelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@SystemApi
/* loaded from: classes4.dex */
public class RcsFeature extends ImsFeature {
    private static final String LOG_TAG = "RcsFeature";
    private CapabilityExchangeEventListener mCapExchangeEventListener;
    private RcsCapabilityExchangeImplBase mCapabilityExchangeImpl;
    private Executor mExecutor;
    private final RcsFeatureBinder mImsRcsBinder;

    @Override // android.telephony.ims.feature.ImsFeature
    public void changeEnabledCapabilities(CapabilityChangeRequest capabilityChangeRequest, ImsFeature.CapabilityCallbackProxy capabilityCallbackProxy) {
    }

    public void destroyCapabilityExchangeImpl(RcsCapabilityExchangeImplBase rcsCapabilityExchangeImplBase) {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    /* renamed from: onFeatureReady, reason: merged with bridge method [inline-methods] */
    public void lambda$initialize$0() {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public void onFeatureRemoved() {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public boolean queryCapabilityConfiguration(int i, int i2) {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class RcsFeatureBinder extends IImsRcsFeature.Stub {
        private Executor mExecutor;
        private final RcsFeature mReference;

        RcsFeatureBinder(RcsFeature rcsFeature, Executor executor) {
            this.mReference = rcsFeature;
            this.mExecutor = executor;
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public int queryCapabilityStatus() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda2
                @Override // java.util.function.Supplier
                public final Object get() {
                    Integer lambda$queryCapabilityStatus$0;
                    lambda$queryCapabilityStatus$0 = RcsFeature.RcsFeatureBinder.this.lambda$queryCapabilityStatus$0();
                    return lambda$queryCapabilityStatus$0;
                }
            }, "queryCapabilityStatus")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCapabilityStatus$0() {
            return Integer.valueOf(this.mReference.queryCapabilityStatus().mCapabilities);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addCapabilityCallback$1(IImsCapabilityCallback iImsCapabilityCallback) {
            this.mReference.addCapabilityCallback(iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void addCapabilityCallback(final IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    RcsFeature.RcsFeatureBinder.this.lambda$addCapabilityCallback$1(iImsCapabilityCallback);
                }
            }, "addCapabilityCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeCapabilityCallback$2(IImsCapabilityCallback iImsCapabilityCallback) {
            this.mReference.removeCapabilityCallback(iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void removeCapabilityCallback(final IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RcsFeature.RcsFeatureBinder.this.lambda$removeCapabilityCallback$2(iImsCapabilityCallback);
                }
            }, "removeCapabilityCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$changeCapabilitiesConfiguration$3(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) {
            this.mReference.requestChangeEnabledCapabilities(capabilityChangeRequest, iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void changeCapabilitiesConfiguration(final CapabilityChangeRequest capabilityChangeRequest, final IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RcsFeature.RcsFeatureBinder.this.lambda$changeCapabilitiesConfiguration$3(capabilityChangeRequest, iImsCapabilityCallback);
                }
            }, "changeCapabilitiesConfiguration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$queryCapabilityConfiguration$4(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) {
            this.mReference.queryCapabilityConfigurationInternal(i, i2, iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void queryCapabilityConfiguration(final int i, final int i2, final IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    RcsFeature.RcsFeatureBinder.this.lambda$queryCapabilityConfiguration$4(i, i2, iImsCapabilityCallback);
                }
            }, "queryCapabilityConfiguration");
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public int getFeatureState() throws RemoteException {
            final RcsFeature rcsFeature = this.mReference;
            Objects.requireNonNull(rcsFeature);
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda6
                @Override // java.util.function.Supplier
                public final Object get() {
                    return Integer.valueOf(RcsFeature.this.getFeatureState());
                }
            }, "getFeatureState")).intValue();
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void setCapabilityExchangeEventListener(ICapabilityExchangeEventListener iCapabilityExchangeEventListener) throws RemoteException {
            final CapabilityExchangeAidlWrapper capabilityExchangeAidlWrapper = iCapabilityExchangeEventListener != null ? new CapabilityExchangeAidlWrapper(iCapabilityExchangeEventListener) : null;
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    RcsFeature.RcsFeatureBinder.this.lambda$setCapabilityExchangeEventListener$5(capabilityExchangeAidlWrapper);
                }
            }, "setCapabilityExchangeEventListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setCapabilityExchangeEventListener$5(CapabilityExchangeEventListener capabilityExchangeEventListener) {
            this.mReference.setCapabilityExchangeEventListener(capabilityExchangeEventListener);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void publishCapabilities(final String str, IPublishResponseCallback iPublishResponseCallback) throws RemoteException {
            final RcsPublishResponseAidlWrapper rcsPublishResponseAidlWrapper = new RcsPublishResponseAidlWrapper(iPublishResponseCallback);
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    RcsFeature.RcsFeatureBinder.this.lambda$publishCapabilities$6(str, rcsPublishResponseAidlWrapper);
                }
            }, "publishCapabilities");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$publishCapabilities$6(String str, RcsCapabilityExchangeImplBase.PublishResponseCallback publishResponseCallback) {
            this.mReference.getCapabilityExchangeImplBaseInternal().publishCapabilities(str, publishResponseCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void subscribeForCapabilities(final List<Uri> list, ISubscribeResponseCallback iSubscribeResponseCallback) throws RemoteException {
            final RcsSubscribeResponseAidlWrapper rcsSubscribeResponseAidlWrapper = new RcsSubscribeResponseAidlWrapper(iSubscribeResponseCallback);
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    RcsFeature.RcsFeatureBinder.this.lambda$subscribeForCapabilities$7(list, rcsSubscribeResponseAidlWrapper);
                }
            }, "subscribeForCapabilities");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$subscribeForCapabilities$7(List list, RcsCapabilityExchangeImplBase.SubscribeResponseCallback subscribeResponseCallback) {
            this.mReference.getCapabilityExchangeImplBaseInternal().subscribeForCapabilities(list, subscribeResponseCallback);
        }

        @Override // android.telephony.ims.aidl.IImsRcsFeature
        public void sendOptionsCapabilityRequest(final Uri uri, final List<String> list, IOptionsResponseCallback iOptionsResponseCallback) throws RemoteException {
            final RcsOptionsResponseAidlWrapper rcsOptionsResponseAidlWrapper = new RcsOptionsResponseAidlWrapper(iOptionsResponseCallback);
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    RcsFeature.RcsFeatureBinder.this.lambda$sendOptionsCapabilityRequest$8(uri, list, rcsOptionsResponseAidlWrapper);
                }
            }, "sendOptionsCapabilityRequest");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendOptionsCapabilityRequest$8(Uri uri, List list, RcsCapabilityExchangeImplBase.OptionsResponseCallback optionsResponseCallback) {
            this.mReference.getCapabilityExchangeImplBaseInternal().sendOptionsCapabilityRequest(uri, new HashSet(list), optionsResponseCallback);
        }

        private void executeMethodAsync(final Runnable runnable, String str) throws RemoteException {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, this.mExecutor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(RcsFeature.LOG_TAG, "RcsFeatureBinder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final Supplier<T> supplier, String str) throws RemoteException {
            try {
                return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.feature.RcsFeature$RcsFeatureBinder$$ExternalSyntheticLambda10
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        Object runWithCleanCallingIdentity;
                        runWithCleanCallingIdentity = TelephonyUtils.runWithCleanCallingIdentity((Supplier<Object>) supplier);
                        return runWithCleanCallingIdentity;
                    }
                }, this.mExecutor).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(RcsFeature.LOG_TAG, "RcsFeatureBinder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }
    }

    public static class RcsImsCapabilities extends ImsFeature.Capabilities {
        public static final int CAPABILITY_TYPE_MAX = 3;
        public static final int CAPABILITY_TYPE_NONE = 0;
        public static final int CAPABILITY_TYPE_OPTIONS_UCE = 1;
        public static final int CAPABILITY_TYPE_PRESENCE_UCE = 2;

        @Retention(RetentionPolicy.SOURCE)
        public @interface RcsImsCapabilityFlag {
        }

        public RcsImsCapabilities(int i) {
            super(i);
        }

        private RcsImsCapabilities(ImsFeature.Capabilities capabilities) {
            super(capabilities.getMask());
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public void addCapabilities(int i) {
            super.addCapabilities(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public void removeCapabilities(int i) {
            super.removeCapabilities(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public boolean isCapable(int i) {
            return super.isCapable(i);
        }
    }

    private Executor getExecutor() {
        if (this.mExecutor == null) {
            this.mExecutor = new PendingIntent$$ExternalSyntheticLambda0();
        }
        return this.mExecutor;
    }

    public RcsFeature() {
        this.mImsRcsBinder = new RcsFeatureBinder(this, getExecutor());
    }

    public RcsFeature(Executor executor) {
        if (executor == null) {
            throw new IllegalArgumentException("executor can not be null.");
        }
        this.mExecutor = executor;
        this.mImsRcsBinder = new RcsFeatureBinder(this, getExecutor());
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public void initialize(Context context, int i) {
        super.initialize(context, i);
        getExecutor().execute(new Runnable() { // from class: android.telephony.ims.feature.RcsFeature$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RcsFeature.this.lambda$initialize$0();
            }
        });
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public final RcsImsCapabilities queryCapabilityStatus() {
        return new RcsImsCapabilities(super.queryCapabilityStatus());
    }

    public final void notifyCapabilitiesStatusChanged(RcsImsCapabilities rcsImsCapabilities) {
        if (rcsImsCapabilities == null) {
            throw new IllegalArgumentException("RcsImsCapabilities must be non-null!");
        }
        super.notifyCapabilitiesStatusChanged((ImsFeature.Capabilities) rcsImsCapabilities);
    }

    public RcsCapabilityExchangeImplBase createCapabilityExchangeImpl(CapabilityExchangeEventListener capabilityExchangeEventListener) {
        return new RcsCapabilityExchangeImplBase();
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public final IImsRcsFeature getBinder() {
        return this.mImsRcsBinder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCapabilityExchangeEventListener(CapabilityExchangeEventListener capabilityExchangeEventListener) {
        synchronized (this.mLock) {
            this.mCapExchangeEventListener = capabilityExchangeEventListener;
            if (capabilityExchangeEventListener != null) {
                initRcsCapabilityExchangeImplBase(capabilityExchangeEventListener);
            } else {
                RcsCapabilityExchangeImplBase rcsCapabilityExchangeImplBase = this.mCapabilityExchangeImpl;
                if (rcsCapabilityExchangeImplBase != null) {
                    destroyCapabilityExchangeImpl(rcsCapabilityExchangeImplBase);
                }
                this.mCapabilityExchangeImpl = null;
            }
        }
    }

    private void initRcsCapabilityExchangeImplBase(CapabilityExchangeEventListener capabilityExchangeEventListener) {
        synchronized (this.mLock) {
            RcsCapabilityExchangeImplBase rcsCapabilityExchangeImplBase = this.mCapabilityExchangeImpl;
            if (rcsCapabilityExchangeImplBase != null) {
                destroyCapabilityExchangeImpl(rcsCapabilityExchangeImplBase);
            }
            this.mCapabilityExchangeImpl = createCapabilityExchangeImpl(capabilityExchangeEventListener);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public RcsCapabilityExchangeImplBase getCapabilityExchangeImplBaseInternal() {
        RcsCapabilityExchangeImplBase rcsCapabilityExchangeImplBase;
        synchronized (this.mLock) {
            rcsCapabilityExchangeImplBase = this.mCapabilityExchangeImpl;
            if (rcsCapabilityExchangeImplBase == null) {
                throw new IllegalStateException("Session is not available.");
            }
        }
        return rcsCapabilityExchangeImplBase;
    }

    public final void setDefaultExecutor(Executor executor) {
        if (this.mImsRcsBinder.mExecutor == null) {
            this.mExecutor = executor;
            this.mImsRcsBinder.mExecutor = executor;
        }
    }
}
