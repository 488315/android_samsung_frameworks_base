package com.android.ims.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.RcsContactPresenceTuple;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.aidl.ISipTransport;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.ISecImsMmTelEventListener;
import com.android.internal.telephony.PublishDialog;

/* loaded from: classes5.dex */
public interface IImsService extends IInterface {

    public static class Default implements IImsService {
        @Override // com.android.ims.internal.IImsService
        public void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void addRegistrationListener(int i, int i2, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public void changeAudioPath(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void close(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public android.telephony.ims.aidl.IImsConfig getConfig(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public int getE911CallCount(int i) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsService
        public IImsEcbm getEcbmInterface(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public IImsMultiEndpoint getMultiEndpointInterface(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public IImsCallSession getPendingCallSession(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public IImsRegistration getRegistration(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public ISipTransport getSipTransport(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public String getSmsFormat(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public String getTrn(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public IImsUt getUtInterface(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsService
        public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsService
        public boolean isConnected(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsService
        public boolean isOpened(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsService
        public void notifyEpsFallbackResult(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void onMemoryAvailable(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void onSmsReady(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public int open(int i, int i2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsService
        public boolean queryCapabilityConfiguration(int i, int i2, int i3) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsService
        public void removeImsFeature(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void sendDtmfEvent(int i, String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void sendMmsProcType(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void setRegistrationListener(int i, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void setRetryCount(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void setSmsc(int i, String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void setTtyMode(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void setUiTTYMode(int i, int i2, Message message) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsService
        public int stopLocalRingBackTone() throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsService
        public void triggerAutoConfigurationForApp(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void turnOffIms(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsService
        public void turnOnIms(int i) throws RemoteException {
        }
    }

    void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException;

    void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException;

    void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void addRegistrationListener(int i, int i2, IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void changeAudioPath(int i, int i2) throws RemoteException;

    void close(int i) throws RemoteException;

    ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException;

    IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) throws RemoteException;

    android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int i) throws RemoteException;

    android.telephony.ims.aidl.IImsConfig getConfig(int i) throws RemoteException;

    int getE911CallCount(int i) throws RemoteException;

    IImsEcbm getEcbmInterface(int i) throws RemoteException;

    IImsMultiEndpoint getMultiEndpointInterface(int i) throws RemoteException;

    IImsCallSession getPendingCallSession(int i, String str) throws RemoteException;

    IImsRegistration getRegistration(int i) throws RemoteException;

    ISipTransport getSipTransport(int i) throws RemoteException;

    String getSmsFormat(int i) throws RemoteException;

    String getTrn(String str, String str2) throws RemoteException;

    IImsUt getUtInterface(int i) throws RemoteException;

    boolean isCmcEmergencyCallSupported(int i) throws RemoteException;

    boolean isConnected(int i, int i2, int i3) throws RemoteException;

    boolean isOpened(int i) throws RemoteException;

    void notifyEpsFallbackResult(int i, int i2) throws RemoteException;

    void onMemoryAvailable(int i, int i2) throws RemoteException;

    void onSmsReady(int i) throws RemoteException;

    int open(int i, int i2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    boolean queryCapabilityConfiguration(int i, int i2, int i3) throws RemoteException;

    void removeImsFeature(int i, int i2) throws RemoteException;

    void sendDtmfEvent(int i, String str) throws RemoteException;

    void sendMmsProcType(int i, boolean z) throws RemoteException;

    void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException;

    void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr) throws RemoteException;

    void setRegistrationListener(int i, IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void setRetryCount(int i, int i2, int i3) throws RemoteException;

    void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener) throws RemoteException;

    void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException;

    void setSmsc(int i, String str) throws RemoteException;

    void setTtyMode(int i, int i2) throws RemoteException;

    void setUiTTYMode(int i, int i2, Message message) throws RemoteException;

    void setVideoCrtAudio(int i, boolean z) throws RemoteException;

    int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

    int stopLocalRingBackTone() throws RemoteException;

    void triggerAutoConfigurationForApp(int i) throws RemoteException;

    void turnOffIms(int i) throws RemoteException;

    void turnOnIms(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IImsService {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsService";
        static final int TRANSACTION_acknowledgeSms = 35;
        static final int TRANSACTION_acknowledgeSmsReport = 36;
        static final int TRANSACTION_acknowledgeSmsWithPdu = 40;
        static final int TRANSACTION_addRegistrationListener = 6;
        static final int TRANSACTION_changeAudioPath = 18;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_createCallProfile = 7;
        static final int TRANSACTION_createCallSession = 8;
        static final int TRANSACTION_createRcsFeature = 42;
        static final int TRANSACTION_getConfig = 11;
        static final int TRANSACTION_getE911CallCount = 29;
        static final int TRANSACTION_getEcbmInterface = 14;
        static final int TRANSACTION_getMultiEndpointInterface = 16;
        static final int TRANSACTION_getPendingCallSession = 9;
        static final int TRANSACTION_getRegistration = 17;
        static final int TRANSACTION_getSipTransport = 44;
        static final int TRANSACTION_getSmsFormat = 39;
        static final int TRANSACTION_getTrn = 23;
        static final int TRANSACTION_getUtInterface = 10;
        static final int TRANSACTION_isCmcEmergencyCallSupported = 25;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_notifyEpsFallbackResult = 28;
        static final int TRANSACTION_onMemoryAvailable = 33;
        static final int TRANSACTION_onSmsReady = 38;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_queryCapabilityConfiguration = 41;
        static final int TRANSACTION_removeImsFeature = 43;
        static final int TRANSACTION_sendDtmfEvent = 22;
        static final int TRANSACTION_sendMmsProcType = 45;
        static final int TRANSACTION_sendPublishDialog = 24;
        static final int TRANSACTION_sendSms = 31;
        static final int TRANSACTION_setRegistrationListener = 5;
        static final int TRANSACTION_setRetryCount = 32;
        static final int TRANSACTION_setSecImsMmTelEventListener = 30;
        static final int TRANSACTION_setSmsListener = 37;
        static final int TRANSACTION_setSmsc = 34;
        static final int TRANSACTION_setTtyMode = 27;
        static final int TRANSACTION_setUiTTYMode = 15;
        static final int TRANSACTION_setVideoCrtAudio = 21;
        static final int TRANSACTION_startLocalRingBackTone = 19;
        static final int TRANSACTION_stopLocalRingBackTone = 20;
        static final int TRANSACTION_triggerAutoConfigurationForApp = 26;
        static final int TRANSACTION_turnOffIms = 13;
        static final int TRANSACTION_turnOnIms = 12;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 44;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsService)) {
                return (IImsService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return RcsContactPresenceTuple.TUPLE_BASIC_STATUS_OPEN;
                case 2:
                    return "close";
                case 3:
                    return "isConnected";
                case 4:
                    return "isOpened";
                case 5:
                    return "setRegistrationListener";
                case 6:
                    return "addRegistrationListener";
                case 7:
                    return "createCallProfile";
                case 8:
                    return "createCallSession";
                case 9:
                    return "getPendingCallSession";
                case 10:
                    return "getUtInterface";
                case 11:
                    return "getConfig";
                case 12:
                    return "turnOnIms";
                case 13:
                    return "turnOffIms";
                case 14:
                    return "getEcbmInterface";
                case 15:
                    return "setUiTTYMode";
                case 16:
                    return "getMultiEndpointInterface";
                case 17:
                    return "getRegistration";
                case 18:
                    return "changeAudioPath";
                case 19:
                    return "startLocalRingBackTone";
                case 20:
                    return "stopLocalRingBackTone";
                case 21:
                    return "setVideoCrtAudio";
                case 22:
                    return "sendDtmfEvent";
                case 23:
                    return "getTrn";
                case 24:
                    return "sendPublishDialog";
                case 25:
                    return "isCmcEmergencyCallSupported";
                case 26:
                    return "triggerAutoConfigurationForApp";
                case 27:
                    return "setTtyMode";
                case 28:
                    return "notifyEpsFallbackResult";
                case 29:
                    return "getE911CallCount";
                case 30:
                    return "setSecImsMmTelEventListener";
                case 31:
                    return "sendSms";
                case 32:
                    return "setRetryCount";
                case 33:
                    return "onMemoryAvailable";
                case 34:
                    return "setSmsc";
                case 35:
                    return "acknowledgeSms";
                case 36:
                    return "acknowledgeSmsReport";
                case 37:
                    return "setSmsListener";
                case 38:
                    return "onSmsReady";
                case 39:
                    return "getSmsFormat";
                case 40:
                    return "acknowledgeSmsWithPdu";
                case 41:
                    return "queryCapabilityConfiguration";
                case 42:
                    return "createRcsFeature";
                case 43:
                    return "removeImsFeature";
                case 44:
                    return "getSipTransport";
                case 45:
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    IImsRegistrationListener iImsRegistrationListenerAsInterface = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iOpen = open(i3, i4, pendingIntent, iImsRegistrationListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iOpen);
                    return true;
                case 2:
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    close(i5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsConnected = isConnected(i6, i7, i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsConnected);
                    return true;
                case 4:
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsOpened = isOpened(i9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOpened);
                    return true;
                case 5:
                    int i10 = parcel.readInt();
                    IImsRegistrationListener iImsRegistrationListenerAsInterface2 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setRegistrationListener(i10, iImsRegistrationListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    IImsRegistrationListener iImsRegistrationListenerAsInterface3 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRegistrationListener(i11, i12, iImsRegistrationListenerAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsCallProfile imsCallProfileCreateCallProfile = createCallProfile(i13, i14, i15);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imsCallProfileCreateCallProfile, 1);
                    return true;
                case 8:
                    int i16 = parcel.readInt();
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    IImsCallSessionListener iImsCallSessionListenerAsInterface = IImsCallSessionListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    IImsCallSession iImsCallSessionCreateCallSession = createCallSession(i16, imsCallProfile, iImsCallSessionListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsCallSessionCreateCallSession);
                    return true;
                case 9:
                    int i17 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IImsCallSession pendingCallSession = getPendingCallSession(i17, string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(pendingCallSession);
                    return true;
                case 10:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsUt utInterface = getUtInterface(i18);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(utInterface);
                    return true;
                case 11:
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsConfig config = getConfig(i19);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(config);
                    return true;
                case 12:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    turnOnIms(i20);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    turnOffIms(i21);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsEcbm ecbmInterface = getEcbmInterface(i22);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(ecbmInterface);
                    return true;
                case 15:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiTTYMode(i23, i24, message);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    int i25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsMultiEndpoint multiEndpointInterface = getMultiEndpointInterface(i25);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiEndpointInterface);
                    return true;
                case 17:
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    IImsRegistration registration = getRegistration(i26);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(registration);
                    return true;
                case 18:
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(i27, i28);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartLocalRingBackTone = startLocalRingBackTone(i29, i30, i31);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartLocalRingBackTone);
                    return true;
                case 20:
                    int iStopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopLocalRingBackTone);
                    return true;
                case 21:
                    int i32 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoCrtAudio(i32, z);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i33 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDtmfEvent(i33, string2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String string3 = parcel.readString();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String trn = getTrn(string3, string4);
                    parcel2.writeNoException();
                    parcel2.writeString(trn);
                    return true;
                case 24:
                    int i34 = parcel.readInt();
                    PublishDialog publishDialog = (PublishDialog) parcel.readTypedObject(PublishDialog.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendPublishDialog(i34, publishDialog);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCmcEmergencyCallSupported = isCmcEmergencyCallSupported(i35);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCmcEmergencyCallSupported);
                    return true;
                case 26:
                    int i36 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    triggerAutoConfigurationForApp(i36);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i37 = parcel.readInt();
                    int i38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setTtyMode(i37, i38);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i39 = parcel.readInt();
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyEpsFallbackResult(i39, i40);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int e911CallCount = getE911CallCount(i41);
                    parcel2.writeNoException();
                    parcel2.writeInt(e911CallCount);
                    return true;
                case 30:
                    int i42 = parcel.readInt();
                    ISecImsMmTelEventListener iSecImsMmTelEventListenerAsInterface = ISecImsMmTelEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSecImsMmTelEventListener(i42, iSecImsMmTelEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    int i45 = parcel.readInt();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSms(i43, i44, i45, string5, string6, z2, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i46 = parcel.readInt();
                    int i47 = parcel.readInt();
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRetryCount(i46, i47, i48);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int i49 = parcel.readInt();
                    int i50 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMemoryAvailable(i49, i50);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i51 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmsc(i51, string7);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i52 = parcel.readInt();
                    int i53 = parcel.readInt();
                    int i54 = parcel.readInt();
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSms(i52, i53, i54, i55);
                    parcel2.writeNoException();
                    return true;
                case 36:
                    int i56 = parcel.readInt();
                    int i57 = parcel.readInt();
                    int i58 = parcel.readInt();
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsReport(i56, i57, i58, i59);
                    parcel2.writeNoException();
                    return true;
                case 37:
                    int i60 = parcel.readInt();
                    IImsSmsListener iImsSmsListenerAsInterface = IImsSmsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSmsListener(i60, iImsSmsListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 38:
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSmsReady(i61);
                    parcel2.writeNoException();
                    return true;
                case 39:
                    int i62 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String smsFormat = getSmsFormat(i62);
                    parcel2.writeNoException();
                    parcel2.writeString(smsFormat);
                    return true;
                case 40:
                    int i63 = parcel.readInt();
                    int i64 = parcel.readInt();
                    int i65 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsWithPdu(i63, i64, i65, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                case 41:
                    int i66 = parcel.readInt();
                    int i67 = parcel.readInt();
                    int i68 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zQueryCapabilityConfiguration = queryCapabilityConfiguration(i66, i67, i68);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zQueryCapabilityConfiguration);
                    return true;
                case 42:
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    android.telephony.ims.aidl.IImsRcsFeature iImsRcsFeatureCreateRcsFeature = createRcsFeature(i69);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsRcsFeatureCreateRcsFeature);
                    return true;
                case 43:
                    int i70 = parcel.readInt();
                    int i71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeImsFeature(i70, i71);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ISipTransport sipTransport = getSipTransport(i72);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(sipTransport);
                    return true;
                case 45:
                    int i73 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    sendMmsProcType(i73, z3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsService
            public int open(int i, int i2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void close(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public boolean isConnected(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public boolean isOpened(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setRegistrationListener(int i, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void addRegistrationListener(int i, int i2, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsCallProfile) parcelObtain2.readTypedObject(ImsCallProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsCallProfile, 0);
                    parcelObtain.writeStrongInterface(iImsCallSessionListener);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public IImsCallSession getPendingCallSession(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public IImsUt getUtInterface(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsUt.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public android.telephony.ims.aidl.IImsConfig getConfig(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsConfig.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void turnOnIms(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void turnOffIms(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public IImsEcbm getEcbmInterface(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsEcbm.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setUiTTYMode(int i, int i2, Message message) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeTypedObject(message, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public IImsMultiEndpoint getMultiEndpointInterface(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsMultiEndpoint.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public IImsRegistration getRegistration(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsRegistration.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void changeAudioPath(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public int stopLocalRingBackTone() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void sendDtmfEvent(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public String getTrn(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(publishDialog, 0);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void triggerAutoConfigurationForApp(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setTtyMode(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void notifyEpsFallbackResult(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public int getE911CallCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSecImsMmTelEventListener);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setRetryCount(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void onMemoryAvailable(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setSmsc(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsSmsListener);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void onSmsReady(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public String getSmsFormat(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public boolean queryCapabilityConfiguration(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public android.telephony.ims.aidl.IImsRcsFeature createRcsFeature(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsRcsFeature.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void removeImsFeature(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public ISipTransport getSipTransport(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return ISipTransport.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsService
            public void sendMmsProcType(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
