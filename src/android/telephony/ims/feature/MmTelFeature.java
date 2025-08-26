package android.telephony.ims.feature;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Binder;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSessionListener;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.ims.MediaThreshold;
import android.telephony.ims.RtpHeaderExtensionType;
import android.telephony.ims.SrvccCall;
import android.telephony.ims.aidl.IImsCallSessionListener;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsMmTelListener;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.aidl.IImsTrafficSessionCallback;
import android.telephony.ims.aidl.ISrvccStartedCallback;
import android.telephony.ims.feature.ImsFeature;
import android.telephony.ims.feature.MmTelFeature;
import android.telephony.ims.stub.ImsCallSessionImplBase;
import android.telephony.ims.stub.ImsEcbmImplBase;
import android.telephony.ims.stub.ImsMultiEndpointImplBase;
import android.telephony.ims.stub.ImsSmsImplBase;
import android.telephony.ims.stub.ImsUtImplBase;
import android.util.ArraySet;
import android.util.Log;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsUt;
import com.android.internal.telephony.PublishDialog;
import com.android.internal.telephony.util.TelephonyUtils;
import com.android.internal.util.FunctionalUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class MmTelFeature extends ImsFeature {

    @SystemApi
    public static final int AUDIO_HANDLER_ANDROID = 0;

    @SystemApi
    public static final int AUDIO_HANDLER_BASEBAND = 1;
    public static final int EPS_FALLBACK_REASON_INVALID = -1;

    @SystemApi
    public static final int EPS_FALLBACK_REASON_NO_NETWORK_RESPONSE = 2;

    @SystemApi
    public static final int EPS_FALLBACK_REASON_NO_NETWORK_TRIGGER = 1;

    @SystemApi
    public static final String EXTRA_IS_UNKNOWN_CALL = "android.telephony.ims.feature.extra.IS_UNKNOWN_CALL";

    @SystemApi
    public static final String EXTRA_IS_USSD = "android.telephony.ims.feature.extra.IS_USSD";

    @SystemApi
    public static final int IMS_TRAFFIC_DIRECTION_INCOMING = 0;

    @SystemApi
    public static final int IMS_TRAFFIC_DIRECTION_OUTGOING = 1;

    @SystemApi
    public static final int IMS_TRAFFIC_TYPE_EMERGENCY = 0;

    @SystemApi
    public static final int IMS_TRAFFIC_TYPE_EMERGENCY_SMS = 1;
    public static final int IMS_TRAFFIC_TYPE_NONE = -1;

    @SystemApi
    public static final int IMS_TRAFFIC_TYPE_REGISTRATION = 5;

    @SystemApi
    public static final int IMS_TRAFFIC_TYPE_SMS = 4;

    @SystemApi
    public static final int IMS_TRAFFIC_TYPE_UT_XCAP = 6;

    @SystemApi
    public static final int IMS_TRAFFIC_TYPE_VIDEO = 3;

    @SystemApi
    public static final int IMS_TRAFFIC_TYPE_VOICE = 2;
    private static final String LOG_TAG = "MmTelFeature";

    @SystemApi
    public static final int PROCESS_CALL_CSFB = 1;

    @SystemApi
    public static final int PROCESS_CALL_IMS = 0;
    private Executor mExecutor;
    private IImsMmTelListener mListener;
    private ImsSmsImplBase mSmsImpl;
    private HashMap<ImsTrafficSessionCallback, ImsTrafficSessionCallbackWrapper> mTrafficCallbacks = new HashMap<>();
    private final IImsMmTelFeature mImsMMTelBinder = new AnonymousClass1();

    @Retention(RetentionPolicy.SOURCE)
    public @interface EpsFallbackReason {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsAudioHandler {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsTrafficDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ImsTrafficType {
    }

    public static class Listener extends IImsMmTelListener.Stub {
        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onAudioModeIsVoipChanged(int i) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onCdpnReceived(String str, int i) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public IImsCallSessionListener onIncomingCall(IImsCallSession iImsCallSession, String str, Bundle bundle) {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onModifyImsTrafficSession(int i, int i2) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onStartImsTrafficSession(int i, int i2, int i3, int i4, IImsTrafficSessionCallback iImsTrafficSessionCallback) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onStopImsTrafficSession(int i) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onTriggerEpsFallback(int i) {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelListener
        public void onVoiceMessageCountUpdate(int i) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProcessCallResult {
    }

    public void changeAudioPath(int i, int i2) throws RemoteException {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @SystemApi
    public void changeEnabledCapabilities(CapabilityChangeRequest capabilityChangeRequest, ImsFeature.CapabilityCallbackProxy capabilityCallbackProxy) {
    }

    @SystemApi
    public void changeOfferedRtpHeaderExtensionTypes(Set<RtpHeaderExtensionType> set) {
    }

    @SystemApi
    public ImsCallProfile createCallProfile(int i, int i2) {
        return null;
    }

    @SystemApi
    public ImsCallSessionImplBase createCallSession(ImsCallProfile imsCallProfile) {
        return null;
    }

    public int getInitialCallNetworkType(int i) throws RemoteException {
        return 0;
    }

    public String getTrn(String str, String str2) throws RemoteException {
        return null;
    }

    public void initImsSmsImplAdapter() throws RemoteException {
    }

    public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
        return false;
    }

    public final void notifyCdpnInfo(String str, int i) {
    }

    public void notifyEpsFallbackResult(int i, int i2) throws RemoteException {
    }

    @SystemApi
    public void notifySrvccCanceled() {
    }

    @SystemApi
    public void notifySrvccCompleted() {
    }

    @SystemApi
    public void notifySrvccFailed() {
    }

    @SystemApi
    public void notifySrvccStarted(Consumer<List<SrvccCall>> consumer) {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @SystemApi
    /* renamed from: onFeatureReady */
    public void lambda$initialize$0() {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @SystemApi
    public void onFeatureRemoved() {
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @SystemApi
    public boolean queryCapabilityConfiguration(int i, int i2) {
        return false;
    }

    public void sendDtmfEvent(int i, String str) throws RemoteException {
    }

    public void sendMmsProcType(int i, boolean z) throws RemoteException {
    }

    public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
    }

    public void setTtyMode(int i) throws RemoteException {
    }

    @SystemApi
    public void setUiTtyMode(int i, Message message) {
    }

    public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
    }

    @SystemApi
    public int shouldProcessCall(String[] strArr) {
        return 0;
    }

    public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
        return 0;
    }

    public int stopLocalRingBackTone() throws RemoteException {
        return 0;
    }

    public void triggerAutoConfigurationForApp(int i) throws RemoteException {
    }

    @SystemApi
    public MmTelFeature() {
    }

    @SystemApi
    public MmTelFeature(Executor executor) {
        this.mExecutor = executor;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Executor getExecutor() {
        if (this.mExecutor == null) {
            this.mExecutor = new PendingIntent$$ExternalSyntheticLambda0();
        }
        return this.mExecutor;
    }

    /* renamed from: android.telephony.ims.feature.MmTelFeature$1, reason: invalid class name */
    class AnonymousClass1 extends IImsMmTelFeature.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$0(IImsMmTelListener iImsMmTelListener) {
            MmTelFeature.this.setListener(iImsMmTelListener);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setListener(final IImsMmTelListener iImsMmTelListener) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setListener$0(iImsMmTelListener);
                }
            }, "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$getFeatureState$1() {
            return Integer.valueOf(MmTelFeature.this.getFeatureState());
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int getFeatureState() throws RemoteException {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda16
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getFeatureState$1();
                }
            }, "getFeatureState")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ImsCallProfile lambda$createCallProfile$2(int i, int i2) {
            return MmTelFeature.this.createCallProfile(i, i2);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public ImsCallProfile createCallProfile(final int i, final int i2) throws RemoteException {
            return (ImsCallProfile) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$createCallProfile$2(i, i2);
                }
            }, "createCallProfile");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$changeOfferedRtpHeaderExtensionTypes$3(List list) {
            MmTelFeature.this.changeOfferedRtpHeaderExtensionTypes(new ArraySet(list));
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeOfferedRtpHeaderExtensionTypes(final List<RtpHeaderExtensionType> list) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$changeOfferedRtpHeaderExtensionTypes$3(list);
                }
            }, "changeOfferedRtpHeaderExtensionTypes");
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public IImsCallSession createCallSession(final ImsCallProfile imsCallProfile) throws UnsupportedOperationException, RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            IImsCallSession iImsCallSession = (IImsCallSession) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda26
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$createCallSession$4(imsCallProfile, atomicReference);
                }
            }, "createCallSession");
            if (atomicReference.get() == null) {
                return iImsCallSession;
            }
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsCallSession lambda$createCallSession$4(ImsCallProfile imsCallProfile, AtomicReference atomicReference) {
            try {
                return MmTelFeature.this.createCallSessionInterface(imsCallProfile);
            } catch (RemoteException e) {
                atomicReference.set(e);
                return null;
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int shouldProcessCall(final String[] strArr) {
            Integer num = (Integer) executeMethodAsyncForResultNoException(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda3
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$shouldProcessCall$5(strArr);
                }
            }, "shouldProcessCall");
            if (num != null) {
                return num.intValue();
            }
            return 1;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$shouldProcessCall$5(String[] strArr) {
            return Integer.valueOf(MmTelFeature.this.shouldProcessCall(strArr));
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public IImsUt getUtInterface() throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            IImsUt iImsUt = (IImsUt) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda15
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getUtInterface$6(atomicReference);
                }
            }, "getUtInterface");
            if (atomicReference.get() == null) {
                return iImsUt;
            }
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsUt lambda$getUtInterface$6(AtomicReference atomicReference) {
            try {
                return MmTelFeature.this.getUtInterface();
            } catch (RemoteException e) {
                atomicReference.set(e);
                return null;
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public IImsEcbm getEcbmInterface() throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            IImsEcbm iImsEcbm = (IImsEcbm) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda8
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getEcbmInterface$7(atomicReference);
                }
            }, "getEcbmInterface");
            if (atomicReference.get() == null) {
                return iImsEcbm;
            }
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsEcbm lambda$getEcbmInterface$7(AtomicReference atomicReference) {
            try {
                return MmTelFeature.this.getEcbmInterface();
            } catch (RemoteException e) {
                atomicReference.set(e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setUiTtyMode$8(int i, Message message) {
            MmTelFeature.this.setUiTtyMode(i, message);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setUiTtyMode(final int i, final Message message) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setUiTtyMode$8(i, message);
                }
            }, "setUiTtyMode");
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            final AtomicReference atomicReference = new AtomicReference();
            IImsMultiEndpoint iImsMultiEndpoint = (IImsMultiEndpoint) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getMultiEndpointInterface$9(atomicReference);
                }
            }, "getMultiEndpointInterface");
            if (atomicReference.get() == null) {
                return iImsMultiEndpoint;
            }
            throw ((RemoteException) atomicReference.get());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsMultiEndpoint lambda$getMultiEndpointInterface$9(AtomicReference atomicReference) {
            try {
                return MmTelFeature.this.getMultiEndpointInterface();
            } catch (RemoteException e) {
                atomicReference.set(e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$queryCapabilityStatus$10() {
            return Integer.valueOf(MmTelFeature.this.queryCapabilityStatus().mCapabilities);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int queryCapabilityStatus() {
            Integer num = (Integer) executeMethodAsyncForResultNoException(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda24
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$queryCapabilityStatus$10();
                }
            }, "queryCapabilityStatus");
            if (num != null) {
                return num.intValue();
            }
            return 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$addCapabilityCallback$11(IImsCapabilityCallback iImsCapabilityCallback) {
            MmTelFeature.this.addCapabilityCallback(iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void addCapabilityCallback(final IImsCapabilityCallback iImsCapabilityCallback) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$addCapabilityCallback$11(iImsCapabilityCallback);
                }
            }, "addCapabilityCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeCapabilityCallback$12(IImsCapabilityCallback iImsCapabilityCallback) {
            MmTelFeature.this.removeCapabilityCallback(iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void removeCapabilityCallback(final IImsCapabilityCallback iImsCapabilityCallback) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeCapabilityCallback$12(iImsCapabilityCallback);
                }
            }, "removeCapabilityCallback");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$changeCapabilitiesConfiguration$13(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) {
            MmTelFeature.this.requestChangeEnabledCapabilities(capabilityChangeRequest, iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeCapabilitiesConfiguration(final CapabilityChangeRequest capabilityChangeRequest, final IImsCapabilityCallback iImsCapabilityCallback) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$changeCapabilitiesConfiguration$13(capabilityChangeRequest, iImsCapabilityCallback);
                }
            }, "changeCapabilitiesConfiguration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$queryCapabilityConfiguration$14(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) {
            MmTelFeature.this.queryCapabilityConfigurationInternal(i, i2, iImsCapabilityCallback);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void queryCapabilityConfiguration(final int i, final int i2, final IImsCapabilityCallback iImsCapabilityCallback) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$queryCapabilityConfiguration$14(i, i2, iImsCapabilityCallback);
                }
            }, "queryCapabilityConfiguration");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setMediaQualityThreshold$15(int i, MediaThreshold mediaThreshold) {
            MmTelFeature.this.setMediaThreshold(i, mediaThreshold);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setMediaQualityThreshold(final int i, final MediaThreshold mediaThreshold) {
            if (mediaThreshold != null) {
                executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda12
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$setMediaQualityThreshold$15(i, mediaThreshold);
                    }
                }, "setMediaQualityThreshold");
            } else {
                executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda13
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$setMediaQualityThreshold$16(i);
                    }
                }, "clearMediaQualityThreshold");
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setMediaQualityThreshold$16(int i) {
            MmTelFeature.this.clearMediaThreshold(i);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ MediaQualityStatus lambda$queryMediaQualityStatus$17(int i) {
            return MmTelFeature.this.queryMediaQualityStatus(i);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public MediaQualityStatus queryMediaQualityStatus(final int i) throws RemoteException {
            return (MediaQualityStatus) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda35
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$queryMediaQualityStatus$17(i);
                }
            }, "queryMediaQualityStatus");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setSmsListener$18(IImsSmsListener iImsSmsListener) {
            MmTelFeature.this.setSmsListener(iImsSmsListener);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setSmsListener(final IImsSmsListener iImsSmsListener) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setSmsListener$18(iImsSmsListener);
                }
            }, "setSmsListener", MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendSms$19(int i, int i2, String str, String str2, boolean z, byte[] bArr) {
            MmTelFeature.this.sendSms(i, i2, str, str2, z, bArr);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendSms(final int i, final int i2, final String str, final String str2, final boolean z, final byte[] bArr) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendSms$19(i, i2, str, str2, z, bArr);
                }
            }, "sendSms", MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onMemoryAvailable$20(int i) {
            MmTelFeature.this.onMemoryAvailable(i);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void onMemoryAvailable(final int i) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onMemoryAvailable$20(i);
                }
            }, "onMemoryAvailable", MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$acknowledgeSms$21(int i, int i2, int i3) {
            MmTelFeature.this.acknowledgeSms(i, i2, i3);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSms(final int i, final int i2, final int i3) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$acknowledgeSms$21(i, i2, i3);
                }
            }, "acknowledgeSms", MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$acknowledgeSmsWithPdu$22(int i, int i2, int i3, byte[] bArr) {
            MmTelFeature.this.acknowledgeSms(i, i2, i3, bArr);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSmsWithPdu(final int i, final int i2, final int i3, final byte[] bArr) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$acknowledgeSmsWithPdu$22(i, i2, i3, bArr);
                }
            }, "acknowledgeSms", MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$acknowledgeSmsReport$23(int i, int i2, int i3) {
            MmTelFeature.this.acknowledgeSmsReport(i, i2, i3);
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSmsReport(final int i, final int i2, final int i3) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$acknowledgeSmsReport$23(i, i2, i3);
                }
            }, "acknowledgeSmsReport", MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$getSmsFormat$24() {
            return MmTelFeature.this.getSmsFormat();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public String getSmsFormat() {
            return (String) executeMethodAsyncForResultNoException(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda27
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getSmsFormat$24();
                }
            }, "getSmsFormat", MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSmsReady$25() {
            MmTelFeature.this.onSmsReady();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void onSmsReady() {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onSmsReady$25();
                }
            }, "onSmsReady", MmTelFeature.this.getImsSmsImpl().getExecutor());
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccStarted(final ISrvccStartedCallback iSrvccStartedCallback) {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifySrvccStarted$27(iSrvccStartedCallback);
                }
            }, "notifySrvccStarted");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySrvccStarted$27(final ISrvccStartedCallback iSrvccStartedCallback) {
            MmTelFeature.this.notifySrvccStarted(new Consumer() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda25
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MmTelFeature.AnonymousClass1.lambda$notifySrvccStarted$26(iSrvccStartedCallback, (List) obj);
                }
            });
        }

        static /* synthetic */ void lambda$notifySrvccStarted$26(ISrvccStartedCallback iSrvccStartedCallback, List list) {
            try {
                iSrvccStartedCallback.onSrvccCallNotified(list);
            } catch (Exception e) {
                Log.e(MmTelFeature.LOG_TAG, "onSrvccCallNotified e=" + e);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccCompleted() {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifySrvccCompleted$28();
                }
            }, "notifySrvccCompleted");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySrvccCompleted$28() {
            MmTelFeature.this.notifySrvccCompleted();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccFailed() {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifySrvccFailed$29();
                }
            }, "notifySrvccFailed");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySrvccFailed$29() {
            MmTelFeature.this.notifySrvccFailed();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccCanceled() {
            executeMethodAsyncNoException(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$notifySrvccCanceled$30();
                }
            }, "notifySrvccCanceled");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$notifySrvccCanceled$30() {
            MmTelFeature.this.notifySrvccCanceled();
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setTerminalBasedCallWaitingStatus(boolean z) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                try {
                    try {
                        MmTelFeature.this.setTerminalBasedCallWaitingStatus(z);
                    } catch (ServiceSpecificException e) {
                        throw new ServiceSpecificException(e.errorCode, e.getMessage());
                    } catch (Exception e2) {
                        throw new RemoteException(e2.getMessage());
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private void executeMethodAsync(final Runnable runnable, String str) throws RemoteException {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda30
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, MmTelFeature.this.getExecutor()).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
                throw new RemoteException(e.getMessage());
            }
        }

        private void executeMethodAsyncNoException(final Runnable runnable, String str) {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda36
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, MmTelFeature.this.getExecutor()).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
            }
        }

        private void executeMethodAsyncNoException(final Runnable runnable, String str, Executor executor) {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda33
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, executor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final Supplier<T> supplier, String str) throws RemoteException {
            try {
                return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda9
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return TelephonyUtils.runWithCleanCallingIdentity(supplier);
                    }
                }, MmTelFeature.this.getExecutor()).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
                if (e.getCause() instanceof UnsupportedOperationException) {
                    throw new UnsupportedOperationException(e.getMessage());
                }
                throw new RemoteException(e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResultNoException(final Supplier<T> supplier, String str) {
            try {
                return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda6
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return TelephonyUtils.runWithCleanCallingIdentity(supplier);
                    }
                }, MmTelFeature.this.getExecutor()).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
                return null;
            }
        }

        private <T> T executeMethodAsyncForResultNoException(final Supplier<T> supplier, String str, Executor executor) {
            try {
                return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.feature.MmTelFeature$1$$ExternalSyntheticLambda14
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return TelephonyUtils.runWithCleanCallingIdentity(supplier);
                    }
                }, executor).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(MmTelFeature.LOG_TAG, "MmTelFeature Binder - " + str + " exception: " + e.getMessage());
                return null;
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setRetryCount(int i, int i2) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.setRetryCount(i, i2);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setSmsc(String str) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.setSmsc(str);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void initImsSmsImplAdapter() throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.initImsSmsImplAdapter();
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeAudioPath(int i, int i2) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.changeAudioPath(i, i2);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
            int iStartLocalRingBackTone;
            synchronized (MmTelFeature.this.mLock) {
                iStartLocalRingBackTone = MmTelFeature.this.startLocalRingBackTone(i, i2, i3);
            }
            return iStartLocalRingBackTone;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int stopLocalRingBackTone() throws RemoteException {
            int iStopLocalRingBackTone;
            synchronized (MmTelFeature.this.mLock) {
                iStopLocalRingBackTone = MmTelFeature.this.stopLocalRingBackTone();
            }
            return iStopLocalRingBackTone;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.setVideoCrtAudio(i, z);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendDtmfEvent(int i, String str) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.sendDtmfEvent(i, str);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public String getTrn(String str, String str2) throws RemoteException {
            String trn;
            synchronized (MmTelFeature.this.mLock) {
                trn = MmTelFeature.this.getTrn(str, str2);
            }
            return trn;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.sendPublishDialog(i, publishDialog);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
            boolean zIsCmcEmergencyCallSupported;
            synchronized (MmTelFeature.this.mLock) {
                zIsCmcEmergencyCallSupported = MmTelFeature.this.isCmcEmergencyCallSupported(i);
            }
            return zIsCmcEmergencyCallSupported;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifyEpsFallbackResult(int i, int i2) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.notifyEpsFallbackResult(i, i2);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setTtyMode(int i) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.setTtyMode(i);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int getInitialCallNetworkType(int i) throws RemoteException {
            int initialCallNetworkType;
            synchronized (MmTelFeature.this.mLock) {
                initialCallNetworkType = MmTelFeature.this.getInitialCallNetworkType(i);
            }
            return initialCallNetworkType;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void triggerAutoConfigurationForApp(int i) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.triggerAutoConfigurationForApp(i);
            }
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendMmsProcType(int i, boolean z) throws RemoteException {
            synchronized (MmTelFeature.this.mLock) {
                MmTelFeature.this.sendMmsProcType(i, z);
            }
        }
    }

    public static class MmTelCapabilities extends ImsFeature.Capabilities {
        public static final int CAPABILITY_TYPE_CALL_COMPOSER = 16;
        public static final int CAPABILITY_TYPE_CALL_COMPOSER_BUSINESS_ONLY = 32;
        public static final int CAPABILITY_TYPE_MAX = 33;
        public static final int CAPABILITY_TYPE_NONE = 0;
        public static final int CAPABILITY_TYPE_SMS = 8;
        public static final int CAPABILITY_TYPE_UT = 4;
        public static final int CAPABILITY_TYPE_VIDEO = 2;
        public static final int CAPABILITY_TYPE_VOICE = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface MmTelCapability {
        }

        @SystemApi
        public MmTelCapabilities() {
        }

        @SystemApi
        @Deprecated
        public MmTelCapabilities(ImsFeature.Capabilities capabilities) {
            this.mCapabilities = capabilities.mCapabilities;
        }

        @SystemApi
        public MmTelCapabilities(int i) {
            super(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        @SystemApi
        public final void addCapabilities(int i) {
            super.addCapabilities(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        @SystemApi
        public final void removeCapabilities(int i) {
            super.removeCapabilities(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public final boolean isCapable(int i) {
            return super.isCapable(i);
        }

        @Override // android.telephony.ims.feature.ImsFeature.Capabilities
        public String toString() {
            return "MmTel Capabilities - [Voice: " + isCapable(1) + " Video: " + isCapable(2) + " UT: " + isCapable(4) + " SMS: " + isCapable(8) + " CALL_COMPOSER: " + isCapable(16) + " BUSINESS_COMPOSER_ONLY: " + isCapable(32) + NavigationBarInflaterView.SIZE_MOD_END;
        }
    }

    public static class ImsTrafficSessionCallbackWrapper {
        public static final int INVALID_TOKEN = -1;
        private static final int MAX_TOKEN = 65536;
        private static final AtomicInteger sTokenGenerator = new AtomicInteger();
        private IImsTrafficSessionCallbackStub mCallback;
        private ImsTrafficSessionCallback mImsTrafficSessionCallback;
        private int mToken;

        private ImsTrafficSessionCallbackWrapper(ImsTrafficSessionCallback imsTrafficSessionCallback) {
            this.mCallback = null;
            this.mToken = -1;
            this.mImsTrafficSessionCallback = imsTrafficSessionCallback;
        }

        final void update(Executor executor) {
            if (executor == null) {
                throw new IllegalArgumentException("ImsTrafficSessionCallback Executor must be non-null");
            }
            IImsTrafficSessionCallbackStub iImsTrafficSessionCallbackStub = this.mCallback;
            if (iImsTrafficSessionCallbackStub == null) {
                this.mCallback = new IImsTrafficSessionCallbackStub(this.mImsTrafficSessionCallback, executor);
                this.mToken = generateToken();
            } else {
                iImsTrafficSessionCallbackStub.update(executor);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        static class IImsTrafficSessionCallbackStub extends IImsTrafficSessionCallback.Stub {
            private Executor mExecutor;
            private WeakReference<ImsTrafficSessionCallback> mImsTrafficSessionCallbackWeakRef;

            IImsTrafficSessionCallbackStub(ImsTrafficSessionCallback imsTrafficSessionCallback, Executor executor) {
                this.mImsTrafficSessionCallbackWeakRef = new WeakReference<>(imsTrafficSessionCallback);
                this.mExecutor = executor;
            }

            void update(Executor executor) {
                this.mExecutor = executor;
            }

            @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
            public void onReady() {
                final ImsTrafficSessionCallback imsTrafficSessionCallback = this.mImsTrafficSessionCallbackWeakRef.get();
                if (imsTrafficSessionCallback == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.ims.feature.MmTelFeature$ImsTrafficSessionCallbackWrapper$IImsTrafficSessionCallbackStub$$ExternalSyntheticLambda3
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() throws Exception {
                        this.f$0.lambda$onReady$1(imsTrafficSessionCallback);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onReady$1(final ImsTrafficSessionCallback imsTrafficSessionCallback) throws Exception {
                this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$ImsTrafficSessionCallbackWrapper$IImsTrafficSessionCallbackStub$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        imsTrafficSessionCallback.onReady();
                    }
                });
            }

            @Override // android.telephony.ims.aidl.IImsTrafficSessionCallback
            public void onError(final ConnectionFailureInfo connectionFailureInfo) {
                final ImsTrafficSessionCallback imsTrafficSessionCallback = this.mImsTrafficSessionCallbackWeakRef.get();
                if (imsTrafficSessionCallback == null) {
                    return;
                }
                Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingRunnable() { // from class: android.telephony.ims.feature.MmTelFeature$ImsTrafficSessionCallbackWrapper$IImsTrafficSessionCallbackStub$$ExternalSyntheticLambda2
                    @Override // com.android.internal.util.FunctionalUtils.ThrowingRunnable
                    public final void runOrThrow() throws Exception {
                        this.f$0.lambda$onError$3(imsTrafficSessionCallback, connectionFailureInfo);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onError$3(final ImsTrafficSessionCallback imsTrafficSessionCallback, final ConnectionFailureInfo connectionFailureInfo) throws Exception {
                this.mExecutor.execute(new Runnable() { // from class: android.telephony.ims.feature.MmTelFeature$ImsTrafficSessionCallbackWrapper$IImsTrafficSessionCallbackStub$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        imsTrafficSessionCallback.onError(connectionFailureInfo);
                    }
                });
            }
        }

        final IImsTrafficSessionCallbackStub getCallbackBinder() {
            return this.mCallback;
        }

        final int getToken() {
            return this.mToken;
        }

        final void reset() {
            this.mCallback = null;
            this.mToken = -1;
        }

        private static int generateToken() {
            AtomicInteger atomicInteger = sTokenGenerator;
            int iIncrementAndGet = atomicInteger.incrementAndGet();
            if (iIncrementAndGet == 65536) {
                atomicInteger.set(0);
            }
            return iIncrementAndGet;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setListener(IImsMmTelListener iImsMmTelListener) {
        synchronized (this.mLock) {
            this.mListener = iImsMmTelListener;
            if (iImsMmTelListener != null) {
                lambda$initialize$0();
            }
        }
    }

    private IImsMmTelListener getListener() {
        IImsMmTelListener iImsMmTelListener;
        synchronized (this.mLock) {
            iImsMmTelListener = this.mListener;
        }
        return iImsMmTelListener;
    }

    @Override // android.telephony.ims.feature.ImsFeature
    @SystemApi
    public final MmTelCapabilities queryCapabilityStatus() {
        return new MmTelCapabilities(super.queryCapabilityStatus());
    }

    @SystemApi
    public final void notifyCapabilitiesStatusChanged(MmTelCapabilities mmTelCapabilities) {
        if (mmTelCapabilities == null) {
            throw new IllegalArgumentException("MmTelCapabilities must be non-null!");
        }
        super.notifyCapabilitiesStatusChanged((ImsFeature.Capabilities) mmTelCapabilities);
    }

    @SystemApi
    public final void notifyMediaQualityStatusChanged(MediaQualityStatus mediaQualityStatus) {
        if (mediaQualityStatus == null) {
            throw new IllegalArgumentException("MediaQualityStatus must be non-null!");
        }
        Log.i(LOG_TAG, "notifyMediaQualityStatusChanged " + mediaQualityStatus);
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        try {
            listener.onMediaQualityStatusChanged(mediaQualityStatus);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    @Deprecated
    public final void notifyIncomingCall(ImsCallSessionImplBase imsCallSessionImplBase, Bundle bundle) {
        if (imsCallSessionImplBase == null || bundle == null) {
            throw new IllegalArgumentException("ImsCallSessionImplBase and Bundle can not be null.");
        }
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        try {
            imsCallSessionImplBase.setDefaultExecutor(this.mExecutor);
            listener.onIncomingCall(imsCallSessionImplBase.getServiceImpl(), null, bundle);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public final ImsCallSessionListener notifyIncomingCall(ImsCallSessionImplBase imsCallSessionImplBase, String str, Bundle bundle) {
        if (imsCallSessionImplBase == null || str == null || bundle == null) {
            throw new IllegalArgumentException("ImsCallSessionImplBase, callId, and Bundle can not be null.");
        }
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        try {
            imsCallSessionImplBase.setDefaultExecutor(this.mExecutor);
            IImsCallSessionListener iImsCallSessionListenerOnIncomingCall = listener.onIncomingCall(imsCallSessionImplBase.getServiceImpl(), str, bundle);
            if (iImsCallSessionListenerOnIncomingCall == null) {
                return null;
            }
            ImsCallSessionListener imsCallSessionListener = new ImsCallSessionListener(iImsCallSessionListenerOnIncomingCall);
            imsCallSessionListener.setDefaultExecutor(this.mExecutor);
            return imsCallSessionListener;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public final void notifyRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) {
        if (imsCallProfile == null || imsReasonInfo == null) {
            throw new IllegalArgumentException("ImsCallProfile and ImsReasonInfo must not be null.");
        }
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        try {
            listener.onRejectedCall(imsCallProfile, imsReasonInfo);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public final void notifyIncomingCallSession(IImsCallSession iImsCallSession, Bundle bundle) {
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        try {
            listener.onIncomingCall(iImsCallSession, null, bundle);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public final void notifyVoiceMessageCountUpdate(int i) {
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        try {
            listener.onVoiceMessageCountUpdate(i);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public final void setCallAudioHandler(int i) {
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        try {
            listener.onAudioModeIsVoipChanged(i);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public final void triggerEpsFallback(int i) {
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        try {
            listener.onTriggerEpsFallback(i);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public final void startImsTrafficSession(int i, int i2, int i3, Executor executor, ImsTrafficSessionCallback imsTrafficSessionCallback) {
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        ImsTrafficSessionCallbackWrapper imsTrafficSessionCallbackWrapper = this.mTrafficCallbacks.get(imsTrafficSessionCallback);
        if (imsTrafficSessionCallbackWrapper == null) {
            imsTrafficSessionCallbackWrapper = new ImsTrafficSessionCallbackWrapper(imsTrafficSessionCallback);
            this.mTrafficCallbacks.put(imsTrafficSessionCallback, imsTrafficSessionCallbackWrapper);
        }
        try {
            imsTrafficSessionCallbackWrapper.update(executor);
            listener.onStartImsTrafficSession(imsTrafficSessionCallbackWrapper.getToken(), i, i2, i3, imsTrafficSessionCallbackWrapper.getCallbackBinder());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public final void modifyImsTrafficSession(int i, ImsTrafficSessionCallback imsTrafficSessionCallback) {
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        ImsTrafficSessionCallbackWrapper imsTrafficSessionCallbackWrapper = this.mTrafficCallbacks.get(imsTrafficSessionCallback);
        if (imsTrafficSessionCallbackWrapper == null) {
            throw new IllegalStateException("Unknown ImsTrafficSessionCallback instance.");
        }
        try {
            listener.onModifyImsTrafficSession(imsTrafficSessionCallbackWrapper.getToken(), i);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public final void stopImsTrafficSession(ImsTrafficSessionCallback imsTrafficSessionCallback) {
        IImsMmTelListener listener = getListener();
        if (listener == null) {
            throw new IllegalStateException("Session is not available.");
        }
        ImsTrafficSessionCallbackWrapper imsTrafficSessionCallbackWrapper = this.mTrafficCallbacks.get(imsTrafficSessionCallback);
        if (imsTrafficSessionCallbackWrapper == null) {
            throw new IllegalStateException("Unknown ImsTrafficSessionCallback instance.");
        }
        try {
            listener.onStopImsTrafficSession(imsTrafficSessionCallbackWrapper.getToken());
            imsTrafficSessionCallbackWrapper.reset();
            this.mTrafficCallbacks.remove(imsTrafficSessionCallback);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @SystemApi
    public void setMediaThreshold(int i, MediaThreshold mediaThreshold) {
        Log.d(LOG_TAG, "setMediaThreshold is not supported." + mediaThreshold);
    }

    @SystemApi
    public void clearMediaThreshold(int i) {
        Log.d(LOG_TAG, "clearMediaThreshold is not supported." + i);
    }

    @SystemApi
    public MediaQualityStatus queryMediaQualityStatus(int i) {
        Log.d(LOG_TAG, "queryMediaQualityStatus is not supported." + i);
        return null;
    }

    public IImsCallSession createCallSessionInterface(ImsCallProfile imsCallProfile) throws UnsupportedOperationException, RemoteException {
        ImsCallSessionImplBase imsCallSessionImplBaseCreateCallSession = createCallSession(imsCallProfile);
        if (imsCallSessionImplBaseCreateCallSession == null) {
            return null;
        }
        imsCallSessionImplBaseCreateCallSession.setDefaultExecutor(getExecutor());
        return imsCallSessionImplBaseCreateCallSession.getServiceImpl();
    }

    protected IImsUt getUtInterface() throws RemoteException {
        ImsUtImplBase ut = getUt();
        if (ut == null) {
            return null;
        }
        ut.setDefaultExecutor(getExecutor());
        return ut.getInterface();
    }

    protected IImsEcbm getEcbmInterface() throws RemoteException {
        ImsEcbmImplBase ecbm = getEcbm();
        if (ecbm == null) {
            return null;
        }
        ecbm.setDefaultExecutor(getExecutor());
        return ecbm.getImsEcbm();
    }

    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
        ImsMultiEndpointImplBase multiEndpoint = getMultiEndpoint();
        if (multiEndpoint == null) {
            return null;
        }
        multiEndpoint.setDefaultExecutor(getExecutor());
        return multiEndpoint.getIImsMultiEndpoint();
    }

    public ImsSmsImplBase getImsSmsImpl() {
        ImsSmsImplBase imsSmsImplBase;
        synchronized (this.mLock) {
            if (this.mSmsImpl == null) {
                ImsSmsImplBase smsImplementation = getSmsImplementation();
                this.mSmsImpl = smsImplementation;
                smsImplementation.setDefaultExecutor(this.mExecutor);
            }
            imsSmsImplBase = this.mSmsImpl;
        }
        return imsSmsImplBase;
    }

    @SystemApi
    public ImsUtImplBase getUt() {
        return new ImsUtImplBase();
    }

    @SystemApi
    public ImsEcbmImplBase getEcbm() {
        return new ImsEcbmImplBase();
    }

    @SystemApi
    public ImsMultiEndpointImplBase getMultiEndpoint() {
        return new ImsMultiEndpointImplBase();
    }

    @SystemApi
    public void setTerminalBasedCallWaitingStatus(boolean z) {
        throw new ServiceSpecificException(2, "Not implemented on device.");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSmsListener(IImsSmsListener iImsSmsListener) {
        getImsSmsImpl().registerSmsListener(iImsSmsListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSms(int i, int i2, String str, String str2, boolean z, byte[] bArr) {
        getImsSmsImpl().sendSms(i, i2, str, str2, z, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onMemoryAvailable(int i) {
        getImsSmsImpl().onMemoryAvailable(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acknowledgeSms(int i, int i2, int i3) {
        getImsSmsImpl().acknowledgeSms(i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acknowledgeSms(int i, int i2, int i3, byte[] bArr) {
        getImsSmsImpl().acknowledgeSms(i, i2, i3, bArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void acknowledgeSmsReport(int i, int i2, int i3) {
        getImsSmsImpl().acknowledgeSmsReport(i, i2, i3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onSmsReady() {
        getImsSmsImpl().onReady();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setRetryCount(int i, int i2) throws RemoteException, RuntimeException {
        getSmsImplementation().setRetryCount(i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSmsc(String str) throws RemoteException, RuntimeException {
        getSmsImplementation().setSmsc(str);
    }

    @SystemApi
    public ImsSmsImplBase getSmsImplementation() {
        return new ImsSmsImplBase();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getSmsFormat() {
        return getImsSmsImpl().getSmsFormat();
    }

    @Override // android.telephony.ims.feature.ImsFeature
    public final IImsMmTelFeature getBinder() {
        return this.mImsMMTelBinder;
    }

    public final void setDefaultExecutor(Executor executor) {
        if (this.mExecutor == null) {
            this.mExecutor = executor;
        }
    }
}
