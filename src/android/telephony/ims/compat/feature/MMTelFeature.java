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

/* loaded from: classes4.dex */
public class MMTelFeature extends ImsFeature {
    private final Object mLock = new Object();
    private final IImsMMTelFeature mImsMMTelBinder = new IImsMMTelFeature.Stub() { // from class: android.telephony.ims.compat.feature.MMTelFeature.1
        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            int startSession;
            synchronized (MMTelFeature.this.mLock) {
                startSession = MMTelFeature.this.startSession(pendingIntent, iImsRegistrationListener);
            }
            return startSession;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void endSession(int i) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.endSession(i);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isConnected(int i, int i2) throws RemoteException {
            boolean isConnected;
            synchronized (MMTelFeature.this.mLock) {
                isConnected = MMTelFeature.this.isConnected(i, i2);
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
        public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.addRegistrationListener(iImsRegistrationListener);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.removeRegistrationListener(iImsRegistrationListener);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException {
            ImsCallProfile createCallProfile;
            synchronized (MMTelFeature.this.mLock) {
                createCallProfile = MMTelFeature.this.createCallProfile(i, i2, i3);
            }
            return createCallProfile;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile) throws RemoteException {
            IImsCallSession createCallSession;
            synchronized (MMTelFeature.this.mLock) {
                createCallSession = MMTelFeature.this.createCallSession(i, imsCallProfile, null);
            }
            return createCallSession;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsCallSession getPendingCallSession(int i, String str) throws RemoteException {
            IImsCallSession pendingCallSession;
            synchronized (MMTelFeature.this.mLock) {
                pendingCallSession = MMTelFeature.this.getPendingCallSession(i, str);
            }
            return pendingCallSession;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsUt getUtInterface() throws RemoteException {
            IImsUt iImsUt;
            synchronized (MMTelFeature.this.mLock) {
                ImsUtImplBase utInterface = MMTelFeature.this.getUtInterface();
                iImsUt = utInterface != null ? utInterface.getInterface() : null;
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
                ImsEcbmImplBase ecbmInterface = MMTelFeature.this.getEcbmInterface();
                imsEcbm = ecbmInterface != null ? ecbmInterface.getImsEcbm() : null;
            }
            return imsEcbm;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setUiTTYMode(int i, Message message) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.setUiTTYMode(i, message);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            IImsMultiEndpoint iImsMultiEndpoint;
            synchronized (MMTelFeature.this.mLock) {
                ImsMultiEndpointImplBase multiEndpointInterface = MMTelFeature.this.getMultiEndpointInterface();
                iImsMultiEndpoint = multiEndpointInterface != null ? multiEndpointInterface.getIImsMultiEndpoint() : null;
            }
            return iImsMultiEndpoint;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void changeAudioPath(int i, int i2) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.changeAudioPath(i, i2);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
            int startLocalRingBackTone;
            synchronized (MMTelFeature.this.mLock) {
                startLocalRingBackTone = MMTelFeature.this.startLocalRingBackTone(i, i2, i3);
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
        public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.setVideoCrtAudio(i, z);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendDtmfEvent(int i, String str) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.sendDtmfEvent(i, str);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public String getTrn(String str, String str2) throws RemoteException {
            String trn;
            synchronized (MMTelFeature.this.mLock) {
                trn = MMTelFeature.this.getTrn(str, str2);
            }
            return trn;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.sendPublishDialog(i, publishDialog);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
            boolean isCmcEmergencyCallSupported;
            synchronized (MMTelFeature.this.mLock) {
                isCmcEmergencyCallSupported = MMTelFeature.this.isCmcEmergencyCallSupported(i);
            }
            return isCmcEmergencyCallSupported;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.sendSms(i, i2, i3, str, str2, z, bArr);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setRetryCount(int i, int i2, int i3) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.setRetryCount(i, i2, i3);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void onMemoryAvailable(int i, int i2) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.onMemoryAvailable(i, i2);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSmsc(int i, String str) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.setSmsc(i, str);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.acknowledgeSms(i, i2, i3, i4);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.acknowledgeSmsReport(i, i2, i3, i4);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.setSmsListener(i, iImsSmsListener);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void onSmsReady(int i) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.onSmsReady(i);
            }
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public String getSmsFormat(int i) throws RemoteException {
            String smsFormat;
            synchronized (MMTelFeature.this.mLock) {
                smsFormat = MMTelFeature.this.getSmsFormat(i);
            }
            return smsFormat;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
            synchronized (MMTelFeature.this.mLock) {
                MMTelFeature.this.acknowledgeSmsWithPdu(i, i2, i3, bArr);
            }
        }
    };

    public void acknowledgeSms(int i, int i2, int i3, int i4) {
    }

    public void acknowledgeSmsReport(int i, int i2, int i3, int i4) {
    }

    public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) {
    }

    public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
    }

    public void changeAudioPath(int i, int i2) throws RemoteException {
    }

    public ImsCallProfile createCallProfile(int i, int i2, int i3) {
        return null;
    }

    public IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) {
        return null;
    }

    public void endSession(int i) {
    }

    public IImsConfig getConfigInterface() {
        return null;
    }

    public ImsEcbmImplBase getEcbmInterface() {
        return null;
    }

    public ImsMultiEndpointImplBase getMultiEndpointInterface() {
        return null;
    }

    public IImsCallSession getPendingCallSession(int i, String str) {
        return null;
    }

    public String getSmsFormat(int i) {
        return null;
    }

    public String getTrn(String str, String str2) throws RemoteException {
        return null;
    }

    public ImsUtImplBase getUtInterface() {
        return null;
    }

    public void initImsSmsImplAdapter() throws RemoteException {
    }

    public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
        return false;
    }

    public boolean isConnected(int i, int i2) {
        return false;
    }

    public boolean isOpened() {
        return false;
    }

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public void onFeatureReady() {
    }

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public void onFeatureRemoved() {
    }

    public void onMemoryAvailable(int i, int i2) {
    }

    public void onSmsReady(int i) {
    }

    public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) {
    }

    public void sendDtmfEvent(int i, String str) throws RemoteException {
    }

    public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
    }

    public void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr) {
    }

    public void setRetryCount(int i, int i2, int i3) {
    }

    public void setSmsListener(int i, IImsSmsListener iImsSmsListener) {
    }

    public void setSmsc(int i, String str) {
    }

    public void setUiTTYMode(int i, Message message) {
    }

    public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
    }

    public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
        return 0;
    }

    public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) {
        return 0;
    }

    public int stopLocalRingBackTone() throws RemoteException {
        return 0;
    }

    public void turnOffIms() {
    }

    public void turnOnIms() {
    }

    @Override // android.telephony.ims.compat.feature.ImsFeature
    public final IImsMMTelFeature getBinder() {
        return this.mImsMMTelBinder;
    }
}
