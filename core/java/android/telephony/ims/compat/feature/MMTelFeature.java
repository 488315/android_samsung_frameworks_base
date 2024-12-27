package android.telephony.ims.compat.feature;

import android.app.PendingIntent;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.stub.ImsEcbmImplBase;
import android.telephony.ims.stub.ImsMultiEndpointImplBase;
import android.telephony.ims.stub.ImsUtImplBase;

import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMMTelFeature;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.ISecImsMmTelEventListener;
import com.android.internal.telephony.PublishDialog;

public class MMTelFeature extends ImsFeature {
    private final Object mLock = new Object();
    private final IImsMMTelFeature mImsMMTelBinder =
            new IImsMMTelFeature
                    .Stub() { // from class: android.telephony.ims.compat.feature.MMTelFeature.1
                @Override // com.android.ims.internal.IImsMMTelFeature
                public int startSession(
                        PendingIntent incomingCallIntent, IImsRegistrationListener listener)
                        throws RemoteException {
                    int startSession;
                    synchronized (MMTelFeature.this.mLock) {
                        startSession = MMTelFeature.this.startSession(incomingCallIntent, listener);
                    }
                    return startSession;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void endSession(int sessionId) throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.endSession(sessionId);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public boolean isConnected(int callSessionType, int callType)
                        throws RemoteException {
                    boolean isConnected;
                    synchronized (MMTelFeature.this.mLock) {
                        isConnected = MMTelFeature.this.isConnected(callSessionType, callType);
                    }
                    return isConnected;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public boolean isOpened() throws RemoteException {
                    boolean isOpened;
                    synchronized (MMTelFeature.this.mLock) {
                        isOpened = MMTelFeature.this.isOpened();
                    }
                    return isOpened;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public int getFeatureStatus() throws RemoteException {
                    int featureState;
                    synchronized (MMTelFeature.this.mLock) {
                        featureState = MMTelFeature.this.getFeatureState();
                    }
                    return featureState;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void addRegistrationListener(IImsRegistrationListener listener)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.addRegistrationListener(listener);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void removeRegistrationListener(IImsRegistrationListener listener)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.removeRegistrationListener(listener);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public ImsCallProfile createCallProfile(
                        int sessionId, int callSessionType, int callType) throws RemoteException {
                    ImsCallProfile createCallProfile;
                    synchronized (MMTelFeature.this.mLock) {
                        createCallProfile =
                                MMTelFeature.this.createCallProfile(
                                        sessionId, callSessionType, callType);
                    }
                    return createCallProfile;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public IImsCallSession createCallSession(int sessionId, ImsCallProfile profile)
                        throws RemoteException {
                    IImsCallSession createCallSession;
                    synchronized (MMTelFeature.this.mLock) {
                        createCallSession =
                                MMTelFeature.this.createCallSession(sessionId, profile, null);
                    }
                    return createCallSession;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public IImsCallSession getPendingCallSession(int sessionId, String callId)
                        throws RemoteException {
                    IImsCallSession pendingCallSession;
                    synchronized (MMTelFeature.this.mLock) {
                        pendingCallSession =
                                MMTelFeature.this.getPendingCallSession(sessionId, callId);
                    }
                    return pendingCallSession;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public IImsUt getUtInterface() throws RemoteException {
                    IImsUt iImsUt;
                    synchronized (MMTelFeature.this.mLock) {
                        ImsUtImplBase implBase = MMTelFeature.this.getUtInterface();
                        iImsUt = implBase != null ? implBase.getInterface() : null;
                    }
                    return iImsUt;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public IImsConfig getConfigInterface() throws RemoteException {
                    IImsConfig configInterface;
                    synchronized (MMTelFeature.this.mLock) {
                        configInterface = MMTelFeature.this.getConfigInterface();
                    }
                    return configInterface;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void turnOnIms() throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.turnOnIms();
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void turnOffIms() throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.turnOffIms();
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public IImsEcbm getEcbmInterface() throws RemoteException {
                    IImsEcbm imsEcbm;
                    synchronized (MMTelFeature.this.mLock) {
                        ImsEcbmImplBase implBase = MMTelFeature.this.getEcbmInterface();
                        imsEcbm = implBase != null ? implBase.getImsEcbm() : null;
                    }
                    return imsEcbm;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void setUiTTYMode(int uiTtyMode, Message onComplete) throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.setUiTTYMode(uiTtyMode, onComplete);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                    IImsMultiEndpoint iImsMultiEndpoint;
                    synchronized (MMTelFeature.this.mLock) {
                        ImsMultiEndpointImplBase implBase =
                                MMTelFeature.this.getMultiEndpointInterface();
                        iImsMultiEndpoint =
                                implBase != null ? implBase.getIImsMultiEndpoint() : null;
                    }
                    return iImsMultiEndpoint;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void changeAudioPath(int phoneId, int direction) throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.changeAudioPath(phoneId, direction);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public int startLocalRingBackTone(int streamType, int volume, int toneType)
                        throws RemoteException {
                    int startLocalRingBackTone;
                    synchronized (MMTelFeature.this.mLock) {
                        startLocalRingBackTone =
                                MMTelFeature.this.startLocalRingBackTone(
                                        streamType, volume, toneType);
                    }
                    return startLocalRingBackTone;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public int stopLocalRingBackTone() throws RemoteException {
                    int stopLocalRingBackTone;
                    synchronized (MMTelFeature.this.mLock) {
                        stopLocalRingBackTone = MMTelFeature.this.stopLocalRingBackTone();
                    }
                    return stopLocalRingBackTone;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void setVideoCrtAudio(int phoneId, boolean on) throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.setVideoCrtAudio(phoneId, on);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void sendDtmfEvent(int phoneId, String dtmfEvent) throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.sendDtmfEvent(phoneId, dtmfEvent);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public String getTrn(String srcMsisdn, String dstMsisdn) throws RemoteException {
                    String trn;
                    synchronized (MMTelFeature.this.mLock) {
                        trn = MMTelFeature.this.getTrn(srcMsisdn, dstMsisdn);
                    }
                    return trn;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void sendPublishDialog(int phoneId, PublishDialog publishDialog)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.sendPublishDialog(phoneId, publishDialog);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public boolean isCmcEmergencyCallSupported(int phoneId) throws RemoteException {
                    boolean isCmcEmergencyCallSupported;
                    synchronized (MMTelFeature.this.mLock) {
                        isCmcEmergencyCallSupported =
                                MMTelFeature.this.isCmcEmergencyCallSupported(phoneId);
                    }
                    return isCmcEmergencyCallSupported;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void setSecImsMmTelEventListener(
                        int phoneId, ISecImsMmTelEventListener listener) throws RemoteException {}

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void sendSms(
                        int phoneId,
                        int token,
                        int messageRef,
                        String format,
                        String smsc,
                        boolean isRetry,
                        byte[] pdu)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.sendSms(
                                phoneId, token, messageRef, format, smsc, isRetry, pdu);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void setRetryCount(int phoneId, int token, int retryCount)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.setRetryCount(phoneId, token, retryCount);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void onMemoryAvailable(int phoneId, int token) throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.onMemoryAvailable(phoneId, token);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void setSmsc(int phoneId, String smsc) throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.setSmsc(phoneId, smsc);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void acknowledgeSms(int phoneId, int token, int messageRef, int result)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.acknowledgeSms(phoneId, token, messageRef, result);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void acknowledgeSmsReport(int phoneId, int token, int messageRef, int result)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.acknowledgeSmsReport(phoneId, token, messageRef, result);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void setSmsListener(int phoneId, IImsSmsListener listener)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.setSmsListener(phoneId, listener);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void onSmsReady(int phoneId) throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.onSmsReady(phoneId);
                    }
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public String getSmsFormat(int phoneId) throws RemoteException {
                    String smsFormat;
                    synchronized (MMTelFeature.this.mLock) {
                        smsFormat = MMTelFeature.this.getSmsFormat(phoneId);
                    }
                    return smsFormat;
                }

                @Override // com.android.ims.internal.IImsMMTelFeature
                public void acknowledgeSmsWithPdu(
                        int phoneId, int token, int messageRef, byte[] data)
                        throws RemoteException {
                    synchronized (MMTelFeature.this.mLock) {
                        MMTelFeature.this.acknowledgeSmsWithPdu(phoneId, token, messageRef, data);
                    }
                }
            };

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public final IImsMMTelFeature getBinder() {
        return this.mImsMMTelBinder;
    }

    public void initImsSmsImplAdapter() throws RemoteException {}

    public int startSession(PendingIntent incomingCallIntent, IImsRegistrationListener listener) {
        return 0;
    }

    public void endSession(int sessionId) {}

    public boolean isConnected(int callSessionType, int callType) {
        return false;
    }

    public boolean isOpened() {
        return false;
    }

    public void addRegistrationListener(IImsRegistrationListener listener) {}

    public void removeRegistrationListener(IImsRegistrationListener listener) {}

    public ImsCallProfile createCallProfile(int sessionId, int callSessionType, int callType) {
        return null;
    }

    public IImsCallSession createCallSession(
            int sessionId, ImsCallProfile profile, IImsCallSessionListener listener) {
        return null;
    }

    public IImsCallSession getPendingCallSession(int sessionId, String callId) {
        return null;
    }

    public ImsUtImplBase getUtInterface() {
        return null;
    }

    public IImsConfig getConfigInterface() {
        return null;
    }

    public void turnOnIms() {}

    public void turnOffIms() {}

    public ImsEcbmImplBase getEcbmInterface() {
        return null;
    }

    public void setUiTTYMode(int uiTtyMode, Message onComplete) {}

    public ImsMultiEndpointImplBase getMultiEndpointInterface() {
        return null;
    }

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public void onFeatureReady() {}

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public void onFeatureRemoved() {}

    public void changeAudioPath(int phoneId, int direction) throws RemoteException {}

    public int startLocalRingBackTone(int streamType, int volume, int toneType)
            throws RemoteException {
        return 0;
    }

    public int stopLocalRingBackTone() throws RemoteException {
        return 0;
    }

    public void setVideoCrtAudio(int phoneId, boolean on) throws RemoteException {}

    public void sendDtmfEvent(int phoneId, String dtmfEvent) throws RemoteException {}

    public String getTrn(String srcMsisdn, String dstMsisdn) throws RemoteException {
        return null;
    }

    public void sendPublishDialog(int phoneId, PublishDialog publishDialog)
            throws RemoteException {}

    public boolean isCmcEmergencyCallSupported(int phoneId) throws RemoteException {
        return false;
    }

    public void sendSms(
            int phoneId,
            int token,
            int messageRef,
            String format,
            String smsc,
            boolean isRetry,
            byte[] pdu) {}

    public void setRetryCount(int phoneId, int token, int retryCount) {}

    public void onMemoryAvailable(int phoneId, int token) {}

    public void setSmsc(int phoneId, String smsc) {}

    public void acknowledgeSms(int phoneId, int token, int messageRef, int result) {}

    public void acknowledgeSmsReport(int phoneId, int token, int messageRef, int result) {}

    public void setSmsListener(int phoneId, IImsSmsListener listener) {}

    public void onSmsReady(int phoneId) {}

    public String getSmsFormat(int phoneId) {
        return null;
    }

    public void acknowledgeSmsWithPdu(int phoneId, int token, int messageRef, byte[] data) {}
}
