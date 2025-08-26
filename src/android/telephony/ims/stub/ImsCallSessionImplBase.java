package android.telephony.ims.stub;

import android.annotation.SystemApi;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSessionListener;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsVideoCallProvider;
import android.telephony.ims.RtpHeaderExtension;
import android.telephony.ims.aidl.IImsCallSessionListener;
import android.util.ArraySet;
import android.util.Log;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsVideoCallProvider;
import com.android.internal.telephony.SemRILConstants;
import com.android.internal.telephony.util.TelephonyUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@SystemApi
/* loaded from: classes4.dex */
public class ImsCallSessionImplBase implements AutoCloseable {
    private static final String LOG_TAG = "ImsCallSessionImplBase";
    public static final int MEDIA_STREAM_DIRECTION_DOWNLINK = 2;
    public static final int MEDIA_STREAM_DIRECTION_UPLINK = 1;
    public static final int MEDIA_STREAM_TYPE_AUDIO = 1;
    public static final int MEDIA_STREAM_TYPE_VIDEO = 2;
    public static final int USSD_MODE_NOTIFY = 0;
    public static final int USSD_MODE_REQUEST = 1;
    private Executor mExecutor = new PendingIntent$$ExternalSyntheticLambda0();
    private IImsCallSession mServiceImpl = new AnonymousClass1();

    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaStreamDirection {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface MediaStreamType {
    }

    public void accept(int i, ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void cancelTransferCall() {
    }

    @Override // java.lang.AutoCloseable
    public void close() {
    }

    public void deflect(String str) {
    }

    public void extendToConference(String[] strArr) {
    }

    public String getCallId() {
        return null;
    }

    public ImsCallProfile getCallProfile() {
        return null;
    }

    public ImsVideoCallProvider getImsVideoCallProvider() {
        return null;
    }

    public ImsCallProfile getLocalCallProfile() {
        return null;
    }

    public String getProperty(String str) {
        return null;
    }

    public ImsCallProfile getRemoteCallProfile() {
        return null;
    }

    public int getState() {
        return -1;
    }

    public void hold(ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void inviteParticipants(String[] strArr) {
    }

    public boolean isInCall() {
        return false;
    }

    public boolean isMultiparty() {
        return false;
    }

    public void merge() {
    }

    public void notifyReadyToHandleImsCallbacks() {
    }

    public void reject(int i) {
    }

    public void removeParticipants(String[] strArr) {
    }

    public void resume(ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public void sendDtmf(char c, Message message) {
    }

    public void sendImsCallEvent(String str, Bundle bundle) {
    }

    public void sendRtpHeaderExtensions(Set<RtpHeaderExtension> set) {
    }

    public void sendRttMessage(String str) {
    }

    public void sendRttModifyRequest(ImsCallProfile imsCallProfile) {
    }

    public void sendRttModifyResponse(boolean z) {
    }

    public void sendUssd(String str) {
    }

    @Deprecated
    public void setListener(ImsCallSessionListener imsCallSessionListener) {
    }

    public void setMute(boolean z) {
    }

    public void start(String str, ImsCallProfile imsCallProfile) {
    }

    public void startConference(String[] strArr, ImsCallProfile imsCallProfile) {
    }

    public void startDtmf(char c) {
    }

    public void stopDtmf() {
    }

    public void terminate(int i) {
    }

    public void transfer(ImsCallSessionImplBase imsCallSessionImplBase) {
    }

    public void transfer(String str, boolean z) {
    }

    public void update(int i, ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    public static class State {
        public static final int ESTABLISHED = 4;
        public static final int ESTABLISHING = 3;
        public static final int IDLE = 0;
        public static final int INITIATED = 1;
        public static final int INVALID = -1;
        public static final int NEGOTIATING = 2;
        public static final int REESTABLISHING = 6;
        public static final int RENEGOTIATING = 5;
        public static final int TERMINATED = 8;
        public static final int TERMINATING = 7;

        public static String toString(int i) {
            switch (i) {
                case 0:
                    return "IDLE";
                case 1:
                    return "INITIATED";
                case 2:
                    return "NEGOTIATING";
                case 3:
                    return "ESTABLISHING";
                case 4:
                    return "ESTABLISHED";
                case 5:
                    return "RENEGOTIATING";
                case 6:
                    return "REESTABLISHING";
                case 7:
                    return "TERMINATING";
                case 8:
                    return "TERMINATED";
                default:
                    return "UNKNOWN";
            }
        }

        private State() {
        }
    }

    /* renamed from: android.telephony.ims.stub.ImsCallSessionImplBase$1, reason: invalid class name */
    class AnonymousClass1 extends IImsCallSession.Stub {
        AnonymousClass1() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$close$0() {
            ImsCallSessionImplBase.this.close();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void close() {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$close$0();
                }
            }, "close");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$getCallId$1() {
            return ImsCallSessionImplBase.this.getCallId();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public String getCallId() {
            return (String) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getCallId$1();
                }
            }, "getCallId");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ImsCallProfile lambda$getCallProfile$2() {
            return ImsCallSessionImplBase.this.getCallProfile();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getCallProfile() {
            return (ImsCallProfile) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda9
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getCallProfile$2();
                }
            }, "getCallProfile");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ImsCallProfile lambda$getLocalCallProfile$3() {
            return ImsCallSessionImplBase.this.getLocalCallProfile();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getLocalCallProfile() {
            return (ImsCallProfile) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda4
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getLocalCallProfile$3();
                }
            }, "getLocalCallProfile");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ ImsCallProfile lambda$getRemoteCallProfile$4() {
            return ImsCallSessionImplBase.this.getRemoteCallProfile();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getRemoteCallProfile() {
            return (ImsCallProfile) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda27
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getRemoteCallProfile$4();
                }
            }, "getRemoteCallProfile");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ String lambda$getProperty$5(String str) {
            return ImsCallSessionImplBase.this.getProperty(str);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public String getProperty(final String str) {
            return (String) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda32
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getProperty$5(str);
                }
            }, "getProperty");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Integer lambda$getState$6() {
            return Integer.valueOf(ImsCallSessionImplBase.this.getState());
        }

        @Override // com.android.ims.internal.IImsCallSession
        public int getState() {
            return ((Integer) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda25
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getState$6();
                }
            }, "getState")).intValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Boolean lambda$isInCall$7() {
            return Boolean.valueOf(ImsCallSessionImplBase.this.isInCall());
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isInCall() {
            return ((Boolean) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda33
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$isInCall$7();
                }
            }, "isInCall")).booleanValue();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setListener(IImsCallSessionListener iImsCallSessionListener) {
            final ImsCallSessionListener imsCallSessionListener = new ImsCallSessionListener(iImsCallSessionListener);
            imsCallSessionListener.setDefaultExecutor(ImsCallSessionImplBase.this.mExecutor);
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setListener$8(imsCallSessionListener);
                }
            }, "setListener");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setListener$8(ImsCallSessionListener imsCallSessionListener) {
            ImsCallSessionImplBase.this.setListener(imsCallSessionListener);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$setMute$9(boolean z) {
            ImsCallSessionImplBase.this.setMute(z);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setMute(final boolean z) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$setMute$9(z);
                }
            }, "setMute");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$start$10(String str, ImsCallProfile imsCallProfile) {
            ImsCallSessionImplBase.this.start(str, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void start(final String str, final ImsCallProfile imsCallProfile) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$start$10(str, imsCallProfile);
                }
            }, "start");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startConference$11(String[] strArr, ImsCallProfile imsCallProfile) {
            ImsCallSessionImplBase.this.startConference(strArr, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startConference(final String[] strArr, final ImsCallProfile imsCallProfile) throws RemoteException {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$startConference$11(strArr, imsCallProfile);
                }
            }, "startConference");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$accept$12(int i, ImsStreamMediaProfile imsStreamMediaProfile) {
            ImsCallSessionImplBase.this.accept(i, imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void accept(final int i, final ImsStreamMediaProfile imsStreamMediaProfile) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$accept$12(i, imsStreamMediaProfile);
                }
            }, "accept");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$deflect$13(String str) {
            ImsCallSessionImplBase.this.deflect(str);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void deflect(final String str) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$deflect$13(str);
                }
            }, "deflect");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$reject$14(int i) {
            ImsCallSessionImplBase.this.reject(i);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void reject(final int i) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$reject$14(i);
                }
            }, SemRILConstants.CmcCall.CMC_CALL_SD_REJECT);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$transfer$15(String str, boolean z) {
            ImsCallSessionImplBase.this.transfer(str, z);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void transfer(final String str, final boolean z) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$transfer$15(str, z);
                }
            }, "transfer");
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void consultativeTransfer(final IImsCallSession iImsCallSession) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$consultativeTransfer$16(iImsCallSession);
                }
            }, "consultativeTransfer");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$consultativeTransfer$16(IImsCallSession iImsCallSession) {
            ImsCallSessionImplBase imsCallSessionImplBase = new ImsCallSessionImplBase();
            imsCallSessionImplBase.setServiceImpl(iImsCallSession);
            ImsCallSessionImplBase.this.transfer(imsCallSessionImplBase);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$terminate$17(int i) {
            ImsCallSessionImplBase.this.terminate(i);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void terminate(final int i) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$terminate$17(i);
                }
            }, "terminate");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$hold$18(ImsStreamMediaProfile imsStreamMediaProfile) {
            ImsCallSessionImplBase.this.hold(imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void hold(final ImsStreamMediaProfile imsStreamMediaProfile) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$hold$18(imsStreamMediaProfile);
                }
            }, SemRILConstants.CmcCall.CMC_CALL_SD_HOLD);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$resume$19(ImsStreamMediaProfile imsStreamMediaProfile) {
            ImsCallSessionImplBase.this.resume(imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void resume(final ImsStreamMediaProfile imsStreamMediaProfile) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$resume$19(imsStreamMediaProfile);
                }
            }, "resume");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$merge$20() {
            ImsCallSessionImplBase.this.merge();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void merge() {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$merge$20();
                }
            }, "merge");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$update$21(int i, ImsStreamMediaProfile imsStreamMediaProfile) {
            ImsCallSessionImplBase.this.update(i, imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void update(final int i, final ImsStreamMediaProfile imsStreamMediaProfile) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$update$21(i, imsStreamMediaProfile);
                }
            }, "update");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$extendToConference$22(String[] strArr) {
            ImsCallSessionImplBase.this.extendToConference(strArr);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void extendToConference(final String[] strArr) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$extendToConference$22(strArr);
                }
            }, "extendToConference");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$inviteParticipants$23(String[] strArr) {
            ImsCallSessionImplBase.this.inviteParticipants(strArr);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void inviteParticipants(final String[] strArr) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$inviteParticipants$23(strArr);
                }
            }, "inviteParticipants");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$removeParticipants$24(String[] strArr) {
            ImsCallSessionImplBase.this.removeParticipants(strArr);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void removeParticipants(final String[] strArr) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$removeParticipants$24(strArr);
                }
            }, "removeParticipants");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendDtmf$25(char c, Message message) {
            ImsCallSessionImplBase.this.sendDtmf(c, message);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendDtmf(final char c, final Message message) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendDtmf$25(c, message);
                }
            }, "sendDtmf");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$startDtmf$26(char c) {
            ImsCallSessionImplBase.this.startDtmf(c);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startDtmf(final char c) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$startDtmf$26(c);
                }
            }, "startDtmf");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$stopDtmf$27() {
            ImsCallSessionImplBase.this.stopDtmf();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void stopDtmf() {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$stopDtmf$27();
                }
            }, "stopDtmf");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendUssd$28(String str) {
            ImsCallSessionImplBase.this.sendUssd(str);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendUssd(final String str) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendUssd$28(str);
                }
            }, "sendUssd");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ IImsVideoCallProvider lambda$getVideoCallProvider$29() {
            return ImsCallSessionImplBase.this.getVideoCallProvider();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public IImsVideoCallProvider getVideoCallProvider() {
            return (IImsVideoCallProvider) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$getVideoCallProvider$29();
                }
            }, "getVideoCallProvider");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ Boolean lambda$isMultiparty$30() {
            return Boolean.valueOf(ImsCallSessionImplBase.this.isMultiparty());
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isMultiparty() {
            return ((Boolean) executeMethodAsyncForResult(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda26
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.lambda$isMultiparty$30();
                }
            }, "isMultiparty")).booleanValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendRttModifyRequest$31(ImsCallProfile imsCallProfile) {
            ImsCallSessionImplBase.this.sendRttModifyRequest(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyRequest(final ImsCallProfile imsCallProfile) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendRttModifyRequest$31(imsCallProfile);
                }
            }, "sendRttModifyRequest");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendRttModifyResponse$32(boolean z) {
            ImsCallSessionImplBase.this.sendRttModifyResponse(z);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyResponse(final boolean z) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendRttModifyResponse$32(z);
                }
            }, "sendRttModifyResponse");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendRttMessage$33(String str) {
            ImsCallSessionImplBase.this.sendRttMessage(str);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttMessage(final String str) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendRttMessage$33(str);
                }
            }, "sendRttMessage");
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void cancelTransferCall() {
            ImsCallSessionImplBase.this.cancelTransferCall();
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendImsCallEvent(String str, Bundle bundle) {
            ImsCallSessionImplBase.this.sendImsCallEvent(str, bundle);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void notifyReadyToHandleImsCallbacks() {
            ImsCallSessionImplBase.this.notifyReadyToHandleImsCallbacks();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$sendRtpHeaderExtensions$34(List list) {
            ImsCallSessionImplBase.this.sendRtpHeaderExtensions(new ArraySet(list));
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRtpHeaderExtensions(final List<RtpHeaderExtension> list) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$sendRtpHeaderExtensions$34(list);
                }
            }, "sendRtpHeaderExtensions");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionNotifyAnbr$35(int i, int i2, int i3) {
            ImsCallSessionImplBase.this.callSessionNotifyAnbr(i, i2, i3);
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void callSessionNotifyAnbr(final int i, final int i2, final int i3) {
            executeMethodAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionNotifyAnbr$35(i, i2, i3);
                }
            }, "callSessionNotifyAnbr");
        }

        private void executeMethodAsync(final Runnable runnable, String str) {
            try {
                CompletableFuture.runAsync(new Runnable() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda36
                    @Override // java.lang.Runnable
                    public final void run() {
                        TelephonyUtils.runWithCleanCallingIdentity(runnable);
                    }
                }, ImsCallSessionImplBase.this.mExecutor).join();
            } catch (CancellationException | CompletionException e) {
                Log.w(ImsCallSessionImplBase.LOG_TAG, "ImsCallSessionImplBase Binder - " + str + " exception: " + e.getMessage());
            }
        }

        private <T> T executeMethodAsyncForResult(final Supplier<T> supplier, String str) {
            try {
                return (T) CompletableFuture.supplyAsync(new Supplier() { // from class: android.telephony.ims.stub.ImsCallSessionImplBase$1$$ExternalSyntheticLambda22
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return TelephonyUtils.runWithCleanCallingIdentity(supplier);
                    }
                }, ImsCallSessionImplBase.this.mExecutor).get();
            } catch (InterruptedException | ExecutionException e) {
                Log.w(ImsCallSessionImplBase.LOG_TAG, "ImsCallSessionImplBase Binder - " + str + " exception: " + e.getMessage());
                return null;
            }
        }
    }

    public final void setListener(IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
        setListener(new ImsCallSessionListener(iImsCallSessionListener));
    }

    public IImsVideoCallProvider getVideoCallProvider() {
        ImsVideoCallProvider imsVideoCallProvider = getImsVideoCallProvider();
        if (imsVideoCallProvider != null) {
            return imsVideoCallProvider.getInterface();
        }
        return null;
    }

    public void callSessionNotifyAnbr(int i, int i2, int i3) {
        Log.i(LOG_TAG, "ImsCallSessionImplBase callSessionNotifyAnbr - mediaType: " + i);
    }

    public IImsCallSession getServiceImpl() {
        return this.mServiceImpl;
    }

    public void setServiceImpl(IImsCallSession iImsCallSession) {
        this.mServiceImpl = iImsCallSession;
    }

    public final void setDefaultExecutor(Executor executor) {
        this.mExecutor = executor;
    }
}
