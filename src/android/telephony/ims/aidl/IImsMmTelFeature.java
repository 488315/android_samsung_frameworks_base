package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.MediaQualityStatus;
import android.telephony.ims.MediaThreshold;
import android.telephony.ims.RtpHeaderExtensionType;
import android.telephony.ims.aidl.IImsCapabilityCallback;
import android.telephony.ims.aidl.IImsMmTelListener;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.aidl.ISrvccStartedCallback;
import android.telephony.ims.feature.CapabilityChangeRequest;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsUt;
import com.android.internal.telephony.PublishDialog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IImsMmTelFeature extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelFeature";

    public static class Default implements IImsMmTelFeature {
        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSms(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSmsReport(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeAudioPath(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void changeOfferedRtpHeaderExtensionTypes(List<RtpHeaderExtensionType> list) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public ImsCallProfile createCallProfile(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public IImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public IImsEcbm getEcbmInterface() throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int getFeatureState() throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int getInitialCallNetworkType(int i) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public String getSmsFormat() throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public String getTrn(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public IImsUt getUtInterface() throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void initImsSmsImplAdapter() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
            return false;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifyEpsFallbackResult(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccCanceled() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccCompleted() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccFailed() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void notifySrvccStarted(ISrvccStartedCallback iSrvccStartedCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void onMemoryAvailable(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void onSmsReady() throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int queryCapabilityStatus() throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public MediaQualityStatus queryMediaQualityStatus(int i) throws RemoteException {
            return null;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendDtmfEvent(int i, String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendMmsProcType(int i, boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void sendSms(int i, int i2, String str, String str2, boolean z, byte[] bArr) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setListener(IImsMmTelListener iImsMmTelListener) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setMediaQualityThreshold(int i, MediaThreshold mediaThreshold) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setRetryCount(int i, int i2) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setSmsc(String str) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setTerminalBasedCallWaitingStatus(boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setTtyMode(int i) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setUiTtyMode(int i, Message message) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int shouldProcessCall(String[] strArr) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public int stopLocalRingBackTone() throws RemoteException {
            return 0;
        }

        @Override // android.telephony.ims.aidl.IImsMmTelFeature
        public void triggerAutoConfigurationForApp(int i) throws RemoteException {
        }
    }

    void acknowledgeSms(int i, int i2, int i3) throws RemoteException;

    void acknowledgeSmsReport(int i, int i2, int i3) throws RemoteException;

    void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void changeAudioPath(int i, int i2) throws RemoteException;

    void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void changeOfferedRtpHeaderExtensionTypes(List<RtpHeaderExtensionType> list) throws RemoteException;

    ImsCallProfile createCallProfile(int i, int i2) throws RemoteException;

    IImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws RemoteException;

    IImsEcbm getEcbmInterface() throws RemoteException;

    int getFeatureState() throws RemoteException;

    int getInitialCallNetworkType(int i) throws RemoteException;

    IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException;

    String getSmsFormat() throws RemoteException;

    String getTrn(String str, String str2) throws RemoteException;

    IImsUt getUtInterface() throws RemoteException;

    void initImsSmsImplAdapter() throws RemoteException;

    boolean isCmcEmergencyCallSupported(int i) throws RemoteException;

    void notifyEpsFallbackResult(int i, int i2) throws RemoteException;

    void notifySrvccCanceled() throws RemoteException;

    void notifySrvccCompleted() throws RemoteException;

    void notifySrvccFailed() throws RemoteException;

    void notifySrvccStarted(ISrvccStartedCallback iSrvccStartedCallback) throws RemoteException;

    void onMemoryAvailable(int i) throws RemoteException;

    void onSmsReady() throws RemoteException;

    void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    int queryCapabilityStatus() throws RemoteException;

    MediaQualityStatus queryMediaQualityStatus(int i) throws RemoteException;

    void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException;

    void sendDtmfEvent(int i, String str) throws RemoteException;

    void sendMmsProcType(int i, boolean z) throws RemoteException;

    void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException;

    void sendSms(int i, int i2, String str, String str2, boolean z, byte[] bArr) throws RemoteException;

    void setListener(IImsMmTelListener iImsMmTelListener) throws RemoteException;

    void setMediaQualityThreshold(int i, MediaThreshold mediaThreshold) throws RemoteException;

    void setRetryCount(int i, int i2) throws RemoteException;

    void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException;

    void setSmsc(String str) throws RemoteException;

    void setTerminalBasedCallWaitingStatus(boolean z) throws RemoteException;

    void setTtyMode(int i) throws RemoteException;

    void setUiTtyMode(int i, Message message) throws RemoteException;

    void setVideoCrtAudio(int i, boolean z) throws RemoteException;

    int shouldProcessCall(String[] strArr) throws RemoteException;

    int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

    int stopLocalRingBackTone() throws RemoteException;

    void triggerAutoConfigurationForApp(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsMmTelFeature {
        static final int TRANSACTION_acknowledgeSms = 26;
        static final int TRANSACTION_acknowledgeSmsReport = 28;
        static final int TRANSACTION_acknowledgeSmsWithPdu = 27;
        static final int TRANSACTION_addCapabilityCallback = 13;
        static final int TRANSACTION_changeAudioPath = 34;
        static final int TRANSACTION_changeCapabilitiesConfiguration = 15;
        static final int TRANSACTION_changeOfferedRtpHeaderExtensionTypes = 4;
        static final int TRANSACTION_createCallProfile = 3;
        static final int TRANSACTION_createCallSession = 5;
        static final int TRANSACTION_getEcbmInterface = 8;
        static final int TRANSACTION_getFeatureState = 2;
        static final int TRANSACTION_getInitialCallNetworkType = 43;
        static final int TRANSACTION_getMultiEndpointInterface = 10;
        static final int TRANSACTION_getSmsFormat = 29;
        static final int TRANSACTION_getTrn = 39;
        static final int TRANSACTION_getUtInterface = 7;
        static final int TRANSACTION_initImsSmsImplAdapter = 33;
        static final int TRANSACTION_isCmcEmergencyCallSupported = 41;
        static final int TRANSACTION_notifyEpsFallbackResult = 45;
        static final int TRANSACTION_notifySrvccCanceled = 20;
        static final int TRANSACTION_notifySrvccCompleted = 18;
        static final int TRANSACTION_notifySrvccFailed = 19;
        static final int TRANSACTION_notifySrvccStarted = 17;
        static final int TRANSACTION_onMemoryAvailable = 25;
        static final int TRANSACTION_onSmsReady = 30;
        static final int TRANSACTION_queryCapabilityConfiguration = 16;
        static final int TRANSACTION_queryCapabilityStatus = 11;
        static final int TRANSACTION_queryMediaQualityStatus = 22;
        static final int TRANSACTION_removeCapabilityCallback = 14;
        static final int TRANSACTION_sendDtmfEvent = 38;
        static final int TRANSACTION_sendMmsProcType = 46;
        static final int TRANSACTION_sendPublishDialog = 40;
        static final int TRANSACTION_sendSms = 24;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_setMediaQualityThreshold = 21;
        static final int TRANSACTION_setRetryCount = 31;
        static final int TRANSACTION_setSmsListener = 23;
        static final int TRANSACTION_setSmsc = 32;
        static final int TRANSACTION_setTerminalBasedCallWaitingStatus = 12;
        static final int TRANSACTION_setTtyMode = 42;
        static final int TRANSACTION_setUiTtyMode = 9;
        static final int TRANSACTION_setVideoCrtAudio = 37;
        static final int TRANSACTION_shouldProcessCall = 6;
        static final int TRANSACTION_startLocalRingBackTone = 35;
        static final int TRANSACTION_stopLocalRingBackTone = 36;
        static final int TRANSACTION_triggerAutoConfigurationForApp = 44;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 45;
        }

        public Stub() {
            attachInterface(this, IImsMmTelFeature.DESCRIPTOR);
        }

        public static IImsMmTelFeature asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IImsMmTelFeature.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsMmTelFeature)) {
                return (IImsMmTelFeature) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "setListener";
                case 2:
                    return "getFeatureState";
                case 3:
                    return "createCallProfile";
                case 4:
                    return "changeOfferedRtpHeaderExtensionTypes";
                case 5:
                    return "createCallSession";
                case 6:
                    return "shouldProcessCall";
                case 7:
                    return "getUtInterface";
                case 8:
                    return "getEcbmInterface";
                case 9:
                    return "setUiTtyMode";
                case 10:
                    return "getMultiEndpointInterface";
                case 11:
                    return "queryCapabilityStatus";
                case 12:
                    return "setTerminalBasedCallWaitingStatus";
                case 13:
                    return "addCapabilityCallback";
                case 14:
                    return "removeCapabilityCallback";
                case 15:
                    return "changeCapabilitiesConfiguration";
                case 16:
                    return "queryCapabilityConfiguration";
                case 17:
                    return "notifySrvccStarted";
                case 18:
                    return "notifySrvccCompleted";
                case 19:
                    return "notifySrvccFailed";
                case 20:
                    return "notifySrvccCanceled";
                case 21:
                    return "setMediaQualityThreshold";
                case 22:
                    return "queryMediaQualityStatus";
                case 23:
                    return "setSmsListener";
                case 24:
                    return "sendSms";
                case 25:
                    return "onMemoryAvailable";
                case 26:
                    return "acknowledgeSms";
                case 27:
                    return "acknowledgeSmsWithPdu";
                case 28:
                    return "acknowledgeSmsReport";
                case 29:
                    return "getSmsFormat";
                case 30:
                    return "onSmsReady";
                case 31:
                    return "setRetryCount";
                case 32:
                    return "setSmsc";
                case 33:
                    return "initImsSmsImplAdapter";
                case 34:
                    return "changeAudioPath";
                case 35:
                    return "startLocalRingBackTone";
                case 36:
                    return "stopLocalRingBackTone";
                case 37:
                    return "setVideoCrtAudio";
                case 38:
                    return "sendDtmfEvent";
                case 39:
                    return "getTrn";
                case 40:
                    return "sendPublishDialog";
                case 41:
                    return "isCmcEmergencyCallSupported";
                case 42:
                    return "setTtyMode";
                case 43:
                    return "getInitialCallNetworkType";
                case 44:
                    return "triggerAutoConfigurationForApp";
                case 45:
                    return "notifyEpsFallbackResult";
                case 46:
                    return "sendMmsProcType";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IImsMmTelFeature.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IImsMmTelFeature.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    IImsMmTelListener asInterface = IImsMmTelListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int featureState = getFeatureState();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureState);
                    return true;
                case 3:
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsCallProfile createCallProfile = createCallProfile(readInt, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createCallProfile, 1);
                    return true;
                case 4:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(RtpHeaderExtensionType.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeOfferedRtpHeaderExtensionTypes(createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    IImsCallSession createCallSession = createCallSession(imsCallProfile);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createCallSession);
                    return true;
                case 6:
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int shouldProcessCall = shouldProcessCall(createStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(shouldProcessCall);
                    return true;
                case 7:
                    IImsUt utInterface = getUtInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(utInterface);
                    return true;
                case 8:
                    IImsEcbm ecbmInterface = getEcbmInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(ecbmInterface);
                    return true;
                case 9:
                    int readInt3 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiTtyMode(readInt3, message);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IImsMultiEndpoint multiEndpointInterface = getMultiEndpointInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiEndpointInterface);
                    return true;
                case 11:
                    int queryCapabilityStatus = queryCapabilityStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(queryCapabilityStatus);
                    return true;
                case 12:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTerminalBasedCallWaitingStatus(readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IImsCapabilityCallback asInterface2 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCapabilityCallback(asInterface2);
                    return true;
                case 14:
                    IImsCapabilityCallback asInterface3 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCapabilityCallback(asInterface3);
                    return true;
                case 15:
                    CapabilityChangeRequest capabilityChangeRequest = (CapabilityChangeRequest) parcel.readTypedObject(CapabilityChangeRequest.CREATOR);
                    IImsCapabilityCallback asInterface4 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    changeCapabilitiesConfiguration(capabilityChangeRequest, asInterface4);
                    return true;
                case 16:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    IImsCapabilityCallback asInterface5 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryCapabilityConfiguration(readInt4, readInt5, asInterface5);
                    return true;
                case 17:
                    ISrvccStartedCallback asInterface6 = ISrvccStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    notifySrvccStarted(asInterface6);
                    return true;
                case 18:
                    notifySrvccCompleted();
                    return true;
                case 19:
                    notifySrvccFailed();
                    return true;
                case 20:
                    notifySrvccCanceled();
                    return true;
                case 21:
                    int readInt6 = parcel.readInt();
                    MediaThreshold mediaThreshold = (MediaThreshold) parcel.readTypedObject(MediaThreshold.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMediaQualityThreshold(readInt6, mediaThreshold);
                    return true;
                case 22:
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    MediaQualityStatus queryMediaQualityStatus = queryMediaQualityStatus(readInt7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(queryMediaQualityStatus, 1);
                    return true;
                case 23:
                    IImsSmsListener asInterface7 = IImsSmsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSmsListener(asInterface7);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSms(readInt8, readInt9, readString, readString2, readBoolean2, createByteArray);
                    return true;
                case 25:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMemoryAvailable(readInt10);
                    return true;
                case 26:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSms(readInt11, readInt12, readInt13);
                    return true;
                case 27:
                    int readInt14 = parcel.readInt();
                    int readInt15 = parcel.readInt();
                    int readInt16 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsWithPdu(readInt14, readInt15, readInt16, createByteArray2);
                    return true;
                case 28:
                    int readInt17 = parcel.readInt();
                    int readInt18 = parcel.readInt();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsReport(readInt17, readInt18, readInt19);
                    return true;
                case 29:
                    String smsFormat = getSmsFormat();
                    parcel2.writeNoException();
                    parcel2.writeString(smsFormat);
                    return true;
                case 30:
                    onSmsReady();
                    return true;
                case 31:
                    int readInt20 = parcel.readInt();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRetryCount(readInt20, readInt21);
                    return true;
                case 32:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmsc(readString3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    initImsSmsImplAdapter();
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(readInt22, readInt23);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startLocalRingBackTone = startLocalRingBackTone(readInt24, readInt25, readInt26);
                    parcel2.writeNoException();
                    parcel2.writeInt(startLocalRingBackTone);
                    return true;
                case 36:
                    int stopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopLocalRingBackTone);
                    return true;
                case 37:
                    int readInt27 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoCrtAudio(readInt27, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int readInt28 = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDtmfEvent(readInt28, readString4);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String trn = getTrn(readString5, readString6);
                    parcel2.writeNoException();
                    parcel2.writeString(trn);
                    return true;
                case 40:
                    int readInt29 = parcel.readInt();
                    PublishDialog publishDialog = (PublishDialog) parcel.readTypedObject(PublishDialog.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendPublishDialog(readInt29, publishDialog);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int readInt30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCmcEmergencyCallSupported = isCmcEmergencyCallSupported(readInt30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCmcEmergencyCallSupported);
                    return true;
                case 42:
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTtyMode(readInt31);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int readInt32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int initialCallNetworkType = getInitialCallNetworkType(readInt32);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialCallNetworkType);
                    return true;
                case 44:
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerAutoConfigurationForApp(readInt33);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyEpsFallbackResult(readInt34, readInt35);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int readInt36 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendMmsProcType(readInt36, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsMmTelFeature {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IImsMmTelFeature.DESCRIPTOR;
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setListener(IImsMmTelListener iImsMmTelListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsMmTelListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int getFeatureState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public ImsCallProfile createCallProfile(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsCallProfile) obtain2.readTypedObject(ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void changeOfferedRtpHeaderExtensionTypes(List<RtpHeaderExtensionType> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public IImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int shouldProcessCall(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public IImsUt getUtInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsUt.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsEcbm.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setUiTtyMode(int i, Message message) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsMultiEndpoint.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int queryCapabilityStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setTerminalBasedCallWaitingStatus(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeTypedObject(capabilityChangeRequest, 0);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(16, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccStarted(ISrvccStartedCallback iSrvccStartedCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iSrvccStartedCallback);
                    this.mRemote.transact(17, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccCompleted() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(18, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccFailed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccCanceled() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setMediaQualityThreshold(int i, MediaThreshold mediaThreshold) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(mediaThreshold, 0);
                    this.mRemote.transact(21, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public MediaQualityStatus queryMediaQualityStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return (MediaQualityStatus) obtain2.readTypedObject(MediaQualityStatus.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsSmsListener);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendSms(int i, int i2, String str, String str2, boolean z, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void onMemoryAvailable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSms(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSmsReport(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public String getSmsFormat() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void onSmsReady() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setRetryCount(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setSmsc(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void initImsSmsImplAdapter() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void changeAudioPath(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int stopLocalRingBackTone() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendDtmfEvent(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public String getTrn(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(publishDialog, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setTtyMode(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int getInitialCallNetworkType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void triggerAutoConfigurationForApp(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifyEpsFallbackResult(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendMmsProcType(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
