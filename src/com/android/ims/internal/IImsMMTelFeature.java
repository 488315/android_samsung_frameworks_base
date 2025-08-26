package com.android.ims.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.aidl.IImsSmsListener;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsUt;
import com.android.ims.internal.ISecImsMmTelEventListener;
import com.android.internal.telephony.PublishDialog;

/* loaded from: classes5.dex */
public interface IImsMMTelFeature extends IInterface {

    public static class Default implements IImsMMTelFeature {
        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void changeAudioPath(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void endSession(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsConfig getConfigInterface() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsEcbm getEcbmInterface() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int getFeatureStatus() throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsCallSession getPendingCallSession(int i, String str) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public String getSmsFormat(int i) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public String getTrn(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsUt getUtInterface() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isConnected(int i, int i2) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isOpened() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void onMemoryAvailable(int i, int i2) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void onSmsReady(int i) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendDtmfEvent(int i, String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setRetryCount(int i, int i2, int i3) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSmsc(int i, String str) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setUiTTYMode(int i, Message message) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int stopLocalRingBackTone() throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void turnOffIms() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void turnOnIms() throws RemoteException {
        }
    }

    void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException;

    void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException;

    void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void changeAudioPath(int i, int i2) throws RemoteException;

    ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException;

    IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile) throws RemoteException;

    void endSession(int i) throws RemoteException;

    IImsConfig getConfigInterface() throws RemoteException;

    IImsEcbm getEcbmInterface() throws RemoteException;

    int getFeatureStatus() throws RemoteException;

    IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException;

    IImsCallSession getPendingCallSession(int i, String str) throws RemoteException;

    String getSmsFormat(int i) throws RemoteException;

    String getTrn(String str, String str2) throws RemoteException;

    IImsUt getUtInterface() throws RemoteException;

    boolean isCmcEmergencyCallSupported(int i) throws RemoteException;

    boolean isConnected(int i, int i2) throws RemoteException;

    boolean isOpened() throws RemoteException;

    void onMemoryAvailable(int i, int i2) throws RemoteException;

    void onSmsReady(int i) throws RemoteException;

    void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    void sendDtmfEvent(int i, String str) throws RemoteException;

    void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException;

    void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr) throws RemoteException;

    void setRetryCount(int i, int i2, int i3) throws RemoteException;

    void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener) throws RemoteException;

    void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException;

    void setSmsc(int i, String str) throws RemoteException;

    void setUiTTYMode(int i, Message message) throws RemoteException;

    void setVideoCrtAudio(int i, boolean z) throws RemoteException;

    int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

    int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException;

    int stopLocalRingBackTone() throws RemoteException;

    void turnOffIms() throws RemoteException;

    void turnOnIms() throws RemoteException;

    public static abstract class Stub extends Binder implements IImsMMTelFeature {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsMMTelFeature";
        static final int TRANSACTION_acknowledgeSms = 32;
        static final int TRANSACTION_acknowledgeSmsReport = 33;
        static final int TRANSACTION_acknowledgeSmsWithPdu = 36;
        static final int TRANSACTION_addRegistrationListener = 6;
        static final int TRANSACTION_changeAudioPath = 18;
        static final int TRANSACTION_createCallProfile = 8;
        static final int TRANSACTION_createCallSession = 9;
        static final int TRANSACTION_endSession = 2;
        static final int TRANSACTION_getConfigInterface = 12;
        static final int TRANSACTION_getEcbmInterface = 15;
        static final int TRANSACTION_getFeatureStatus = 5;
        static final int TRANSACTION_getMultiEndpointInterface = 17;
        static final int TRANSACTION_getPendingCallSession = 10;
        static final int TRANSACTION_getSmsFormat = 35;
        static final int TRANSACTION_getTrn = 23;
        static final int TRANSACTION_getUtInterface = 11;
        static final int TRANSACTION_isCmcEmergencyCallSupported = 25;
        static final int TRANSACTION_isConnected = 3;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_onMemoryAvailable = 30;
        static final int TRANSACTION_onSmsReady = 34;
        static final int TRANSACTION_removeRegistrationListener = 7;
        static final int TRANSACTION_sendDtmfEvent = 22;
        static final int TRANSACTION_sendPublishDialog = 24;
        static final int TRANSACTION_sendSms = 28;
        static final int TRANSACTION_setRetryCount = 29;
        static final int TRANSACTION_setSecImsMmTelEventListener = 26;
        static final int TRANSACTION_setSmsListener = 27;
        static final int TRANSACTION_setSmsc = 31;
        static final int TRANSACTION_setUiTTYMode = 16;
        static final int TRANSACTION_setVideoCrtAudio = 21;
        static final int TRANSACTION_startLocalRingBackTone = 19;
        static final int TRANSACTION_startSession = 1;
        static final int TRANSACTION_stopLocalRingBackTone = 20;
        static final int TRANSACTION_turnOffIms = 14;
        static final int TRANSACTION_turnOnIms = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 35;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsMMTelFeature asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IImsMMTelFeature)) {
                return (IImsMMTelFeature) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "startSession";
                case 2:
                    return "endSession";
                case 3:
                    return "isConnected";
                case 4:
                    return "isOpened";
                case 5:
                    return "getFeatureStatus";
                case 6:
                    return "addRegistrationListener";
                case 7:
                    return "removeRegistrationListener";
                case 8:
                    return "createCallProfile";
                case 9:
                    return "createCallSession";
                case 10:
                    return "getPendingCallSession";
                case 11:
                    return "getUtInterface";
                case 12:
                    return "getConfigInterface";
                case 13:
                    return "turnOnIms";
                case 14:
                    return "turnOffIms";
                case 15:
                    return "getEcbmInterface";
                case 16:
                    return "setUiTTYMode";
                case 17:
                    return "getMultiEndpointInterface";
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
                    return "setSecImsMmTelEventListener";
                case 27:
                    return "setSmsListener";
                case 28:
                    return "sendSms";
                case 29:
                    return "setRetryCount";
                case 30:
                    return "onMemoryAvailable";
                case 31:
                    return "setSmsc";
                case 32:
                    return "acknowledgeSms";
                case 33:
                    return "acknowledgeSmsReport";
                case 34:
                    return "onSmsReady";
                case 35:
                    return "getSmsFormat";
                case 36:
                    return "acknowledgeSmsWithPdu";
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
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    IImsRegistrationListener iImsRegistrationListenerAsInterface = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int iStartSession = startSession(pendingIntent, iImsRegistrationListenerAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartSession);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    endSession(i3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsConnected = isConnected(i4, i5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsConnected);
                    return true;
                case 4:
                    boolean zIsOpened = isOpened();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOpened);
                    return true;
                case 5:
                    int featureStatus = getFeatureStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureStatus);
                    return true;
                case 6:
                    IImsRegistrationListener iImsRegistrationListenerAsInterface2 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRegistrationListener(iImsRegistrationListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IImsRegistrationListener iImsRegistrationListenerAsInterface3 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRegistrationListener(iImsRegistrationListenerAsInterface3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsCallProfile imsCallProfileCreateCallProfile = createCallProfile(i6, i7, i8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(imsCallProfileCreateCallProfile, 1);
                    return true;
                case 9:
                    int i9 = parcel.readInt();
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    IImsCallSession iImsCallSessionCreateCallSession = createCallSession(i9, imsCallProfile);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(iImsCallSessionCreateCallSession);
                    return true;
                case 10:
                    int i10 = parcel.readInt();
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IImsCallSession pendingCallSession = getPendingCallSession(i10, string);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(pendingCallSession);
                    return true;
                case 11:
                    IImsUt utInterface = getUtInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(utInterface);
                    return true;
                case 12:
                    IImsConfig configInterface = getConfigInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(configInterface);
                    return true;
                case 13:
                    turnOnIms();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    turnOffIms();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IImsEcbm ecbmInterface = getEcbmInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(ecbmInterface);
                    return true;
                case 16:
                    int i11 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiTTYMode(i11, message);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IImsMultiEndpoint multiEndpointInterface = getMultiEndpointInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiEndpointInterface);
                    return true;
                case 18:
                    int i12 = parcel.readInt();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(i12, i13);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int i14 = parcel.readInt();
                    int i15 = parcel.readInt();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iStartLocalRingBackTone = startLocalRingBackTone(i14, i15, i16);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartLocalRingBackTone);
                    return true;
                case 20:
                    int iStopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(iStopLocalRingBackTone);
                    return true;
                case 21:
                    int i17 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoCrtAudio(i17, z);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i18 = parcel.readInt();
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDtmfEvent(i18, string2);
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
                    int i19 = parcel.readInt();
                    PublishDialog publishDialog = (PublishDialog) parcel.readTypedObject(PublishDialog.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendPublishDialog(i19, publishDialog);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsCmcEmergencyCallSupported = isCmcEmergencyCallSupported(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCmcEmergencyCallSupported);
                    return true;
                case 26:
                    int i21 = parcel.readInt();
                    ISecImsMmTelEventListener iSecImsMmTelEventListenerAsInterface = ISecImsMmTelEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSecImsMmTelEventListener(i21, iSecImsMmTelEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int i22 = parcel.readInt();
                    IImsSmsListener iImsSmsListenerAsInterface = IImsSmsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSmsListener(i22, iImsSmsListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int i23 = parcel.readInt();
                    int i24 = parcel.readInt();
                    int i25 = parcel.readInt();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSms(i23, i24, i25, string5, string6, z2, bArrCreateByteArray);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int i26 = parcel.readInt();
                    int i27 = parcel.readInt();
                    int i28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRetryCount(i26, i27, i28);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int i29 = parcel.readInt();
                    int i30 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMemoryAvailable(i29, i30);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int i31 = parcel.readInt();
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmsc(i31, string7);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int i32 = parcel.readInt();
                    int i33 = parcel.readInt();
                    int i34 = parcel.readInt();
                    int i35 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSms(i32, i33, i34, i35);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int i36 = parcel.readInt();
                    int i37 = parcel.readInt();
                    int i38 = parcel.readInt();
                    int i39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsReport(i36, i37, i38, i39);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int i40 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSmsReady(i40);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String smsFormat = getSmsFormat(i41);
                    parcel2.writeNoException();
                    parcel2.writeString(smsFormat);
                    return true;
                case 36:
                    int i42 = parcel.readInt();
                    int i43 = parcel.readInt();
                    int i44 = parcel.readInt();
                    byte[] bArrCreateByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsWithPdu(i42, i43, i44, bArrCreateByteArray2);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IImsMMTelFeature {
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

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
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

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void endSession(int i) throws RemoteException {
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

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isConnected(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isOpened() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int getFeatureStatus() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ImsCallProfile) parcelObtain2.readTypedObject(ImsCallProfile.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsCallSession getPendingCallSession(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsCallSession.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsUt getUtInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsUt.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsConfig getConfigInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsConfig.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void turnOnIms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void turnOffIms() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsEcbm.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setUiTTYMode(int i, Message message) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(message, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IImsMultiEndpoint.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
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

            @Override // com.android.ims.internal.IImsMMTelFeature
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

            @Override // com.android.ims.internal.IImsMMTelFeature
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

            @Override // com.android.ims.internal.IImsMMTelFeature
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

            @Override // com.android.ims.internal.IImsMMTelFeature
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

            @Override // com.android.ims.internal.IImsMMTelFeature
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

            @Override // com.android.ims.internal.IImsMMTelFeature
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

            @Override // com.android.ims.internal.IImsMMTelFeature
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

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iSecImsMmTelEventListener);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iImsSmsListener);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
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
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setRetryCount(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void onMemoryAvailable(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSmsc(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void onSmsReady(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public String getSmsFormat(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
