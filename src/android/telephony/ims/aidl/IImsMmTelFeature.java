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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IImsMmTelFeature.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsMmTelFeature)) {
                return (IImsMmTelFeature) iInterfaceQueryLocalInterface;
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
                    IImsMmTelListener iImsMmTelListenerAsInterface = IImsMmTelListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setListener(iImsMmTelListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    int featureState = getFeatureState();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureState);
                    return true;
                case 3:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsCallProfile imsCallProfileCreateCallProfile = createCallProfile(i3, i4);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imsCallProfileCreateCallProfile, 1);
                    return true;
                case 4:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(RtpHeaderExtensionType.CREATOR);
                    parcel.enforceNoDataAvail();
                    changeOfferedRtpHeaderExtensionTypes(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    IImsCallSession iImsCallSessionCreateCallSession = createCallSession(imsCallProfile);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsCallSessionCreateCallSession);
                    return true;
                case 6:
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    int iShouldProcessCall = shouldProcessCall(strArrCreateStringArray);
                    parcel2.writeNoException();
                    parcel2.writeInt(iShouldProcessCall);
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
                    int i5 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiTtyMode(i5, message);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    IImsMultiEndpoint multiEndpointInterface = getMultiEndpointInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiEndpointInterface);
                    return true;
                case 11:
                    int iQueryCapabilityStatus = queryCapabilityStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(iQueryCapabilityStatus);
                    return true;
                case 12:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setTerminalBasedCallWaitingStatus(z);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addCapabilityCallback(iImsCapabilityCallbackAsInterface);
                    return true;
                case 14:
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface2 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeCapabilityCallback(iImsCapabilityCallbackAsInterface2);
                    return true;
                case 15:
                    CapabilityChangeRequest capabilityChangeRequest = (CapabilityChangeRequest) parcel.readTypedObject(CapabilityChangeRequest.CREATOR);
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface3 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    changeCapabilitiesConfiguration(capabilityChangeRequest, iImsCapabilityCallbackAsInterface3);
                    return true;
                case 16:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    IImsCapabilityCallback iImsCapabilityCallbackAsInterface4 = IImsCapabilityCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    queryCapabilityConfiguration(i6, i7, iImsCapabilityCallbackAsInterface4);
                    return true;
                case 17:
                    ISrvccStartedCallback iSrvccStartedCallbackAsInterface = ISrvccStartedCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    notifySrvccStarted(iSrvccStartedCallbackAsInterface);
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
                    int i8 = parcel.readInt();
                    MediaThreshold mediaThreshold = (MediaThreshold) parcel.readTypedObject(MediaThreshold.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMediaQualityThreshold(i8, mediaThreshold);
                    return true;
                case 22:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    MediaQualityStatus mediaQualityStatusQueryMediaQualityStatus = queryMediaQualityStatus(i9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(mediaQualityStatusQueryMediaQualityStatus, 1);
                    return true;
                case 23:
                    IImsSmsListener iImsSmsListenerAsInterface = IImsSmsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSmsListener(iImsSmsListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSms(i10, i11, string, string2, z2, bArrCreateByteArray);
                    return true;
                case 25:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMemoryAvailable(i12);
                    return true;
                case 26:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSms(i13, i14, i15);
                    return true;
                case 27:
                    int i16 = parcel.readInt();
                    int i17 = parcel.readInt();
                    int i18 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsWithPdu(i16, i17, i18, bArrCreateByteArray2);
                    return true;
                case 28:
                    int i19 = parcel.readInt();
                    int i20 = parcel.readInt();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsReport(i19, i20, i21);
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
                    int i22 = parcel.readInt();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRetryCount(i22, i23);
                    return true;
                case 32:
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmsc(string3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    initImsSmsImplAdapter();
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(i24, i25);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartLocalRingBackTone = startLocalRingBackTone(i26, i27, i28);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartLocalRingBackTone);
                    return true;
                case 36:
                    int iStopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopLocalRingBackTone);
                    return true;
                case 37:
                    int i29 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoCrtAudio(i29, z3);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i30 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDtmfEvent(i30, string4);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String trn = getTrn(string5, string6);
                    parcel2.writeNoException();
                    parcel2.writeString(trn);
                    return true;
                case 40:
                    int i31 = parcel.readInt();
                    PublishDialog publishDialog = (PublishDialog) parcel.readTypedObject(PublishDialog.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendPublishDialog(i31, publishDialog);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i32 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCmcEmergencyCallSupported = isCmcEmergencyCallSupported(i32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCmcEmergencyCallSupported);
                    return true;
                case 42:
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTtyMode(i33);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    int i34 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int initialCallNetworkType = getInitialCallNetworkType(i34);
                    parcel2.writeNoException();
                    parcel2.writeInt(initialCallNetworkType);
                    return true;
                case 44:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerAutoConfigurationForApp(i35);
                    parcel2.writeNoException();
                    return true;
                case 45:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyEpsFallbackResult(i36, i37);
                    parcel2.writeNoException();
                    return true;
                case 46:
                    int i38 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendMmsProcType(i38, z4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsMmTelListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int getFeatureState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public ImsCallProfile createCallProfile(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsCallProfile) parcelObtain2.readTypedObject(ImsCallProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void changeOfferedRtpHeaderExtensionTypes(List<RtpHeaderExtensionType> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public IImsCallSession createCallSession(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int shouldProcessCall(String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public IImsUt getUtInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsUt.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsEcbm.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setUiTtyMode(int i, Message message) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(message, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsMultiEndpoint.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int queryCapabilityStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setTerminalBasedCallWaitingStatus(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void addCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void removeCapabilityCallback(IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void changeCapabilitiesConfiguration(CapabilityChangeRequest capabilityChangeRequest, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeTypedObject(capabilityChangeRequest, 0);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(15, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void queryCapabilityConfiguration(int i, int i2, IImsCapabilityCallback iImsCapabilityCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iImsCapabilityCallback);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccStarted(ISrvccStartedCallback iSrvccStartedCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iSrvccStartedCallback);
                    this.mRemote.transact(17, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccCompleted() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(18, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccFailed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifySrvccCanceled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setMediaQualityThreshold(int i, MediaThreshold mediaThreshold) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(mediaThreshold, 0);
                    this.mRemote.transact(21, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public MediaQualityStatus queryMediaQualityStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (MediaQualityStatus) parcelObtain2.readTypedObject(MediaQualityStatus.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setSmsListener(IImsSmsListener iImsSmsListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsSmsListener);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendSms(int i, int i2, String str, String str2, boolean z, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(24, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void onMemoryAvailable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSms(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(26, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void acknowledgeSmsReport(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(28, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public String getSmsFormat() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void onSmsReady() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setRetryCount(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(31, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setSmsc(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void initImsSmsImplAdapter() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void changeAudioPath(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int stopLocalRingBackTone() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendDtmfEvent(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public String getTrn(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(publishDialog, 0);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void setTtyMode(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public int getInitialCallNetworkType(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void triggerAutoConfigurationForApp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void notifyEpsFallbackResult(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.telephony.ims.aidl.IImsMmTelFeature
            public void sendMmsProcType(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IImsMmTelFeature.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
