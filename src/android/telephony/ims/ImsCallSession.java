package android.telephony.ims;

import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ims.aidl.IImsCallSessionListener;
import android.util.ArraySet;
import android.util.Log;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsVideoCallProvider;
import com.android.internal.hidden_from_bootclasspath.com.android.internal.telephony.flags.Flags;
import com.android.internal.telephony.util.TelephonyUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class ImsCallSession {
    private static final String TAG = "ImsCallSession";
    private String mCallId;
    private boolean mClosed;
    private IImsCallSessionListenerProxy mIImsCallSessionListenerProxy;
    private Listener mListener;
    private Executor mListenerExecutor;
    private IImsCallSession miSession;

    public static class Listener {
        public void callQualityChanged(CallQuality callQuality) {
        }

        public void callSessionCancelTransferFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionCancelTransferred(ImsCallSession imsCallSession) {
        }

        public void callSessionConferenceExtendFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionConferenceExtendReceived(ImsCallSession imsCallSession, ImsCallSession imsCallSession2, ImsCallProfile imsCallProfile) {
        }

        public void callSessionConferenceExtended(ImsCallSession imsCallSession, ImsCallSession imsCallSession2, ImsCallProfile imsCallProfile) {
        }

        public void callSessionConferenceStateUpdated(ImsCallSession imsCallSession, ImsConferenceState imsConferenceState) {
        }

        public void callSessionDtmfReceived(char c) {
        }

        public void callSessionHandover(ImsCallSession imsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHandoverFailed(ImsCallSession imsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHeld(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionHoldFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHoldReceived(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionImsCallEvent(String str, Bundle bundle) {
        }

        public void callSessionInitiating(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionInitiatingFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionInviteParticipantsRequestDelivered(ImsCallSession imsCallSession) {
        }

        public void callSessionInviteParticipantsRequestFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionMayHandover(ImsCallSession imsCallSession, int i, int i2) {
        }

        public void callSessionMergeComplete(ImsCallSession imsCallSession) {
        }

        public void callSessionMergeFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionMergeStarted(ImsCallSession imsCallSession, ImsCallSession imsCallSession2, ImsCallProfile imsCallProfile) {
        }

        public void callSessionMultipartyStateChanged(ImsCallSession imsCallSession, boolean z) {
        }

        public void callSessionProgressing(ImsCallSession imsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) {
        }

        public void callSessionRemoveParticipantsRequestDelivered(ImsCallSession imsCallSession) {
        }

        public void callSessionRemoveParticipantsRequestFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionResumeFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionResumeReceived(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionResumed(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionRtpHeaderExtensionsReceived(Set<RtpHeaderExtension> set) {
        }

        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) {
        }

        public void callSessionRttMessageReceived(String str) {
        }

        public void callSessionRttModifyRequestReceived(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionRttModifyResponseReceived(int i) {
        }

        public void callSessionSendAnbrQuery(int i, int i2, int i3) {
        }

        public void callSessionStartFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionStarted(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionSuppServiceReceived(ImsCallSession imsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) {
        }

        public void callSessionTerminated(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionTransferFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionTransferred(ImsCallSession imsCallSession) {
        }

        public void callSessionTtyModeReceived(ImsCallSession imsCallSession, int i) {
        }

        public void callSessionUpdateFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionUpdateReceived(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionUpdated(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionUssdMessageReceived(ImsCallSession imsCallSession, int i, String str) {
        }
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

    public ImsCallSession(IImsCallSession iImsCallSession) {
        this.mClosed = false;
        this.mCallId = null;
        this.mListenerExecutor = new PendingIntent$$ExternalSyntheticLambda0();
        this.mIImsCallSessionListenerProxy = null;
        this.miSession = iImsCallSession;
        IImsCallSessionListenerProxy iImsCallSessionListenerProxy = new IImsCallSessionListenerProxy();
        this.mIImsCallSessionListenerProxy = iImsCallSessionListenerProxy;
        if (iImsCallSession != null) {
            try {
                iImsCallSession.setListener(iImsCallSessionListenerProxy);
                return;
            } catch (RemoteException e) {
                if (Flags.ignoreAlreadyTerminatedIncomingCallBeforeRegisteringListener()) {
                    Log.e(TAG, "ImsCallSession : " + e);
                    this.mClosed = true;
                    return;
                }
                return;
            }
        }
        this.mClosed = true;
    }

    public ImsCallSession(IImsCallSession iImsCallSession, Listener listener, Executor executor) {
        this(iImsCallSession);
        setListener(listener, executor);
    }

    public final IImsCallSessionListenerProxy getIImsCallSessionListenerProxy() {
        return this.mIImsCallSessionListenerProxy;
    }

    public void close() {
        synchronized (this) {
            if (this.mClosed) {
                return;
            }
            try {
                this.miSession.close();
                this.mClosed = true;
            } catch (RemoteException unused) {
            } catch (Throwable th) {
                this.miSession = null;
                throw th;
            }
            this.miSession = null;
        }
    }

    public String getCallId() {
        if (this.mClosed) {
            return null;
        }
        String str = this.mCallId;
        if (str != null) {
            return str;
        }
        try {
            String callId = this.miSession.getCallId();
            this.mCallId = callId;
            return callId;
        } catch (RemoteException unused) {
            return null;
        }
    }

    public void setCallId(String str) {
        if (str != null) {
            this.mCallId = str;
        }
    }

    public ImsCallProfile getCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getCallProfile();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public ImsCallProfile getLocalCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getLocalCallProfile();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public ImsCallProfile getRemoteCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getRemoteCallProfile();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public IImsVideoCallProvider getVideoCallProvider() {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getVideoCallProvider();
        } catch (RemoteException unused) {
            return null;
        }
    }

    public String getProperty(String str) {
        if (this.mClosed) {
            return null;
        }
        try {
            return this.miSession.getProperty(str);
        } catch (RemoteException unused) {
            return null;
        }
    }

    public int getState() {
        if (this.mClosed) {
            return -1;
        }
        try {
            return this.miSession.getState();
        } catch (RemoteException unused) {
            return -1;
        }
    }

    public boolean isAlive() {
        if (this.mClosed) {
            return false;
        }
        switch (getState()) {
        }
        return false;
    }

    public IImsCallSession getSession() {
        return this.miSession;
    }

    public boolean isInCall() {
        if (this.mClosed) {
            return false;
        }
        try {
            return this.miSession.isInCall();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void setListener(Listener listener, Executor executor) {
        this.mListener = listener;
        if (executor != null) {
            this.mListenerExecutor = executor;
        }
    }

    public void setMute(boolean z) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.setMute(z);
        } catch (RemoteException unused) {
        }
    }

    public void start(String str, ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.start(str, imsCallProfile);
        } catch (RemoteException unused) {
        }
    }

    public void start(String[] strArr, ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.startConference(strArr, imsCallProfile);
        } catch (RemoteException unused) {
        }
    }

    public void accept(int i, ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.accept(i, imsStreamMediaProfile);
        } catch (RemoteException unused) {
        }
    }

    public void deflect(String str) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.deflect(str);
        } catch (RemoteException unused) {
        }
    }

    public void reject(int i) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.reject(i);
        } catch (RemoteException unused) {
        }
    }

    public void transfer(String str, boolean z) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.transfer(str, z);
        } catch (RemoteException unused) {
        }
    }

    public void transfer(ImsCallSession imsCallSession) {
        if (this.mClosed || imsCallSession == null) {
            return;
        }
        try {
            this.miSession.consultativeTransfer(imsCallSession.getSession());
        } catch (RemoteException unused) {
        }
    }

    public void terminate(int i) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.terminate(i);
        } catch (RemoteException unused) {
        }
    }

    public void hold(ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.hold(imsStreamMediaProfile);
        } catch (RemoteException unused) {
        }
    }

    public void resume(ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.resume(imsStreamMediaProfile);
        } catch (RemoteException unused) {
        }
    }

    public void merge() {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.merge();
        } catch (RemoteException unused) {
        }
    }

    public void update(int i, ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.update(i, imsStreamMediaProfile);
        } catch (RemoteException unused) {
        }
    }

    public void extendToConference(String[] strArr) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.extendToConference(strArr);
        } catch (RemoteException unused) {
        }
    }

    public void inviteParticipants(String[] strArr) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.inviteParticipants(strArr);
        } catch (RemoteException unused) {
        }
    }

    public void removeParticipants(String[] strArr) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.removeParticipants(strArr);
        } catch (RemoteException unused) {
        }
    }

    public void sendDtmf(char c, Message message) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendDtmf(c, message);
        } catch (RemoteException unused) {
        }
    }

    public void startDtmf(char c) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.startDtmf(c);
        } catch (RemoteException unused) {
        }
    }

    public void stopDtmf() {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.stopDtmf();
        } catch (RemoteException unused) {
        }
    }

    public void sendUssd(String str) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendUssd(str);
        } catch (RemoteException unused) {
        }
    }

    public boolean isMultiparty() {
        if (this.mClosed) {
            return false;
        }
        try {
            return this.miSession.isMultiparty();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public void sendRttMessage(String str) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttMessage(str);
        } catch (RemoteException unused) {
        }
    }

    public void sendRttModifyRequest(ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttModifyRequest(imsCallProfile);
        } catch (RemoteException unused) {
        }
    }

    public void sendRttModifyResponse(boolean z) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttModifyResponse(z);
        } catch (RemoteException unused) {
        }
    }

    public void notifyReadyToHandleImsCallbacks() {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.notifyReadyToHandleImsCallbacks();
        } catch (RemoteException unused) {
        }
    }

    public void cancelTransferCall() {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.cancelTransferCall();
        } catch (RemoteException unused) {
        }
    }

    public void sendImsCallEvent(String str, Bundle bundle) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendImsCallEvent(str, bundle);
        } catch (RemoteException unused) {
        }
    }

    public void sendRtpHeaderExtensions(Set<RtpHeaderExtension> set) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRtpHeaderExtensions(new ArrayList(set));
        } catch (RemoteException unused) {
        }
    }

    public void callSessionNotifyAnbr(int i, int i2, int i3) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.callSessionNotifyAnbr(i, i2, i3);
        } catch (RemoteException e) {
            Log.e(TAG, "callSessionNotifyAnbr" + e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class IImsCallSessionListenerProxy extends IImsCallSessionListener.Stub {
        private IImsCallSessionListenerProxy() {
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiating(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda38
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionInitiating$0(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInitiating$0(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionInitiating(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionProgressing(final ImsStreamMediaProfile imsStreamMediaProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda35
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionProgressing$1(imsStreamMediaProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionProgressing$1(ImsStreamMediaProfile imsStreamMediaProfile) {
            if (ImsCallSession.this.mClosed) {
                Log.d(ImsCallSession.TAG, "The session is closed so don't notify callSessionProgressing");
            } else if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionProgressing(ImsCallSession.this, imsStreamMediaProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiated(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda26
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionInitiated$2(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInitiated$2(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionStarted(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatingFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionInitiatingFailed$3(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInitiatingFailed$3(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionStartFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInitiatedFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionInitiatedFailed$4(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInitiatedFailed$4(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionStartFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTerminated(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda19
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionTerminated$5(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionTerminated$5(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionTerminated(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHeld(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda40
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionHeld$6(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHeld$6(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHeld(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda18
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionHoldFailed$7(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHoldFailed$7(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHoldFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHoldReceived(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda31
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionHoldReceived$8(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHoldReceived$8(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHoldReceived(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumed(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionResumed$9(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionResumed$9(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionResumed(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda36
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionResumeFailed$10(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionResumeFailed$10(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionResumeFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionResumeReceived(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionResumeReceived$11(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionResumeReceived$11(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionResumeReceived(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
            Log.d(ImsCallSession.TAG, "callSessionMergeStarted");
            if (ImsCallSession.this.mListener == null || iImsCallSession == null) {
                return;
            }
            try {
                ImsCallSession imsCallSession = new ImsCallSession(iImsCallSession);
                iImsCallSession.setListener(null);
                ImsCallSession.this.mListener.callSessionMergeStarted(ImsCallSession.this, imsCallSession, imsCallProfile);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeComplete(final IImsCallSession iImsCallSession) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionMergeComplete$12(iImsCallSession);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionMergeComplete$12(IImsCallSession iImsCallSession) {
            if (ImsCallSession.this.mListener != null) {
                if (iImsCallSession != null) {
                    ImsCallSession.this.mListener.callSessionMergeComplete(new ImsCallSession(iImsCallSession));
                } else {
                    ImsCallSession.this.mListener.callSessionMergeComplete(null);
                }
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMergeFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda32
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionMergeFailed$13(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionMergeFailed$13(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionMergeFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdated(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionUpdated$14(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionUpdated$14(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionUpdated(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionUpdateFailed$15(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionUpdateFailed$15(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionUpdateFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUpdateReceived(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda21
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionUpdateReceived$16(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionUpdateReceived$16(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionUpdateReceived(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtended(final IImsCallSession iImsCallSession, final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda41
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionConferenceExtended$17(iImsCallSession, imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionConferenceExtended$17(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionConferenceExtended(ImsCallSession.this, new ImsCallSession(iImsCallSession), imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionConferenceExtendFailed$18(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionConferenceExtendFailed$18(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionConferenceExtendFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(final IImsCallSession iImsCallSession, final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionConferenceExtendReceived$19(iImsCallSession, imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionConferenceExtendReceived$19(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionConferenceExtendReceived(ImsCallSession.this, new ImsCallSession(iImsCallSession), imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered() {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda39
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionInviteParticipantsRequestDelivered$20();
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInviteParticipantsRequestDelivered$20() {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionInviteParticipantsRequestDelivered(ImsCallSession.this);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda29
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionInviteParticipantsRequestFailed$21(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionInviteParticipantsRequestFailed$21(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionInviteParticipantsRequestFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered() {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionRemoveParticipantsRequestDelivered$22();
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRemoveParticipantsRequestDelivered$22() {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRemoveParticipantsRequestDelivered(ImsCallSession.this);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionRemoveParticipantsRequestFailed$23(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRemoveParticipantsRequestFailed$23(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRemoveParticipantsRequestFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(final ImsConferenceState imsConferenceState) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda37
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionConferenceStateUpdated$24(imsConferenceState);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionConferenceStateUpdated$24(ImsConferenceState imsConferenceState) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionConferenceStateUpdated(ImsCallSession.this, imsConferenceState);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionUssdMessageReceived(final int i, final String str) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda28
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionUssdMessageReceived$25(i, str);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionUssdMessageReceived$25(int i, String str) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionUssdMessageReceived(ImsCallSession.this, i, str);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMayHandover(final int i, final int i2) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionMayHandover$26(i, i2);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionMayHandover$26(int i, int i2) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionMayHandover(ImsCallSession.this, i, i2);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandover(final int i, final int i2, final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionHandover$27(i, i2, imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHandover$27(int i, int i2, ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHandover(ImsCallSession.this, i, i2, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionHandoverFailed(final int i, final int i2, final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionHandoverFailed$28(i, i2, imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionHandoverFailed$28(int i, int i2, ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHandoverFailed(ImsCallSession.this, i, i2, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTtyModeReceived(final int i) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda30
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionTtyModeReceived$29(i);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionTtyModeReceived$29(int i) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionTtyModeReceived(ImsCallSession.this, i);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(final boolean z) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda16
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionMultipartyStateChanged$30(z);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionMultipartyStateChanged$30(boolean z) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionMultipartyStateChanged(ImsCallSession.this, z);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSuppServiceReceived(final ImsSuppServiceNotification imsSuppServiceNotification) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionSuppServiceReceived$31(imsSuppServiceNotification);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionSuppServiceReceived$31(ImsSuppServiceNotification imsSuppServiceNotification) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionSuppServiceReceived(ImsCallSession.this, imsSuppServiceNotification);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(final ImsCallProfile imsCallProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda34
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionRttModifyRequestReceived$32(imsCallProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRttModifyRequestReceived$32(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRttModifyRequestReceived(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(final int i) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda27
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionRttModifyResponseReceived$33(i);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRttModifyResponseReceived$33(int i) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRttModifyResponseReceived(i);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttMessageReceived(final String str) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionRttMessageReceived$34(str);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRttMessageReceived$34(String str) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRttMessageReceived(str);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(final ImsStreamMediaProfile imsStreamMediaProfile) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda20
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionRttAudioIndicatorChanged$35(imsStreamMediaProfile);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRttAudioIndicatorChanged$35(ImsStreamMediaProfile imsStreamMediaProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTransferred() {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda33
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionTransferred$36();
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionTransferred$36() {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionTransferred(ImsCallSession.this);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionTransferFailed(final ImsReasonInfo imsReasonInfo) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda23
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionTransferFailed$37(imsReasonInfo);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionTransferFailed$37(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionTransferFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionCancelTransferred() {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionCancelTransferred(ImsCallSession.this);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionCancelTransferFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionCancelTransferFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionImsCallEvent(String str, Bundle bundle) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionImsCallEvent(str, bundle);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionDtmfReceived(final char c) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda24
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionDtmfReceived$38(c);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionDtmfReceived$38(char c) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionDtmfReceived(c);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callQualityChanged(final CallQuality callQuality) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callQualityChanged$39(callQuality);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callQualityChanged$39(CallQuality callQuality) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callQualityChanged(callQuality);
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionRtpHeaderExtensionsReceived(final List<RtpHeaderExtension> list) {
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda25
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionRtpHeaderExtensionsReceived$40(list);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionRtpHeaderExtensionsReceived$40(List list) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRtpHeaderExtensionsReceived(new ArraySet(list));
            }
        }

        @Override // android.telephony.ims.aidl.IImsCallSessionListener
        public void callSessionSendAnbrQuery(final int i, final int i2, final int i3) {
            Log.d(ImsCallSession.TAG, "callSessionSendAnbrQuery in ImsCallSession");
            TelephonyUtils.runWithCleanCallingIdentity(new Runnable() { // from class: android.telephony.ims.ImsCallSession$IImsCallSessionListenerProxy$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$callSessionSendAnbrQuery$41(i, i2, i3);
                }
            }, ImsCallSession.this.mListenerExecutor);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$callSessionSendAnbrQuery$41(int i, int i2, int i3) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionSendAnbrQuery(i, i2, i3);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[ImsCallSession objId:");
        sb.append(System.identityHashCode(this));
        sb.append(" callId:");
        String str = this.mCallId;
        if (str == null) {
            str = "[UNINITIALIZED]";
        }
        sb.append(str);
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        return sb.toString();
    }
}
