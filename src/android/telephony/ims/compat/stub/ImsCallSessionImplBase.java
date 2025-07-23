package android.telephony.ims.compat.stub;

import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ServiceState;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import android.telephony.ims.RtpHeaderExtension;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.android.ims.internal.IImsVideoCallProvider;
import java.util.List;

/* loaded from: classes4.dex */
public class ImsCallSessionImplBase extends IImsCallSession.Stub {
    @Override // com.android.ims.internal.IImsCallSession
    public void accept(int i, ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void callSessionNotifyAnbr(int i, int i2, int i3) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void cancelTransferCall() {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void close() {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void consultativeTransfer(IImsCallSession iImsCallSession) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void deflect(String str) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void extendToConference(String[] strArr) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public String getCallId() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public ImsCallProfile getCallProfile() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public ImsCallProfile getLocalCallProfile() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public String getProperty(String str) {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public ImsCallProfile getRemoteCallProfile() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public int getState() {
        return -1;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public IImsVideoCallProvider getVideoCallProvider() {
        return null;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void hold(ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void inviteParticipants(String[] strArr) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public boolean isInCall() {
        return false;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public boolean isMultiparty() {
        return false;
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void merge() {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void notifyReadyToHandleImsCallbacks() {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void reject(int i) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void removeParticipants(String[] strArr) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void resume(ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendDtmf(char c, Message message) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendImsCallEvent(String str, Bundle bundle) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendRtpHeaderExtensions(List<RtpHeaderExtension> list) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendRttMessage(String str) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendRttModifyRequest(ImsCallProfile imsCallProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendRttModifyResponse(boolean z) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void sendUssd(String str) {
    }

    public void setListener(IImsCallSessionListener iImsCallSessionListener) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void setMute(boolean z) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void start(String str, ImsCallProfile imsCallProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void startConference(String[] strArr, ImsCallProfile imsCallProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void startDtmf(char c) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void stopDtmf() {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void terminate(int i) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void transfer(String str, boolean z) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public void update(int i, ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override // com.android.ims.internal.IImsCallSession
    public final void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
        setListener(new ImsCallSessionListenerConverter(this, iImsCallSessionListener));
    }

    private class ImsCallSessionListenerConverter extends IImsCallSessionListener.Stub {
        private final android.telephony.ims.aidl.IImsCallSessionListener mNewListener;

        public ImsCallSessionListenerConverter(ImsCallSessionImplBase imsCallSessionImplBase, android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) {
            this.mNewListener = iImsCallSessionListener;
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionProgressing(IImsCallSession iImsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
            this.mNewListener.callSessionProgressing(imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionInitiated(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionStartFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionInitiatedFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTerminated(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionTerminated(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHeld(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionHeld(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionHoldFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHoldReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionHoldReceived(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumed(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionResumed(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionResumeFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionResumeReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionResumeReceived(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeStarted(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionMergeStarted(iImsCallSession2, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
            this.mNewListener.callSessionMergeComplete(iImsCallSession);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMergeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionMergeFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdated(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionUpdated(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionUpdateFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUpdateReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionUpdateReceived(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtended(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionConferenceExtended(iImsCallSession2, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionConferenceExtendFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionConferenceExtendReceived(iImsCallSession2, imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
            this.mNewListener.callSessionInviteParticipantsRequestDelivered();
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionInviteParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
            this.mNewListener.callSessionRemoveParticipantsRequestDelivered();
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRemoveParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionRemoveParticipantsRequestFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionConferenceStateUpdated(IImsCallSession iImsCallSession, ImsConferenceState imsConferenceState) throws RemoteException {
            this.mNewListener.callSessionConferenceStateUpdated(imsConferenceState);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionUssdMessageReceived(IImsCallSession iImsCallSession, int i, String str) throws RemoteException {
            this.mNewListener.callSessionUssdMessageReceived(i, str);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandover(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionHandover(ServiceState.rilRadioTechnologyToNetworkType(i), ServiceState.rilRadioTechnologyToNetworkType(i2), imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionHandoverFailed(IImsCallSession iImsCallSession, int i, int i2, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionHandoverFailed(ServiceState.rilRadioTechnologyToNetworkType(i), ServiceState.rilRadioTechnologyToNetworkType(i2), imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMayHandover(IImsCallSession iImsCallSession, int i, int i2) throws RemoteException {
            this.mNewListener.callSessionMayHandover(ServiceState.rilRadioTechnologyToNetworkType(i), ServiceState.rilRadioTechnologyToNetworkType(i2));
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTtyModeReceived(IImsCallSession iImsCallSession, int i) throws RemoteException {
            this.mNewListener.callSessionTtyModeReceived(i);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionMultipartyStateChanged(IImsCallSession iImsCallSession, boolean z) throws RemoteException {
            this.mNewListener.callSessionMultipartyStateChanged(z);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSuppServiceReceived(IImsCallSession iImsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
            this.mNewListener.callSessionSuppServiceReceived(imsSuppServiceNotification);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyRequestReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionRttModifyRequestReceived(imsCallProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttModifyResponseReceived(int i) throws RemoteException {
            this.mNewListener.callSessionRttModifyResponseReceived(i);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttMessageReceived(String str) throws RemoteException {
            this.mNewListener.callSessionRttMessageReceived(str);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
            this.mNewListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTransferred() throws RemoteException {
            this.mNewListener.callSessionTransferred();
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionTransferFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionTransferFailed(imsReasonInfo);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callQualityChanged(CallQuality callQuality) throws RemoteException {
            this.mNewListener.callQualityChanged(callQuality);
        }

        @Override // com.android.ims.internal.IImsCallSessionListener
        public void callSessionSendAnbrQuery(int i, int i2, int i3) throws RemoteException {
            this.mNewListener.callSessionSendAnbrQuery(i, i2, i3);
        }
    }
}
