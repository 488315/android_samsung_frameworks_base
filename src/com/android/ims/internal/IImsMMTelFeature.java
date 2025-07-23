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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IImsMMTelFeature)) {
                return (IImsMMTelFeature) queryLocalInterface;
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
                    IImsRegistrationListener asInterface = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int startSession = startSession(pendingIntent, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(startSession);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    endSession(readInt);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isConnected = isConnected(readInt2, readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isConnected);
                    return true;
                case 4:
                    boolean isOpened = isOpened();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOpened);
                    return true;
                case 5:
                    int featureStatus = getFeatureStatus();
                    parcel2.writeNoException();
                    parcel2.writeInt(featureStatus);
                    return true;
                case 6:
                    IImsRegistrationListener asInterface2 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addRegistrationListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    IImsRegistrationListener asInterface3 = IImsRegistrationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeRegistrationListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    int readInt4 = parcel.readInt();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ImsCallProfile createCallProfile = createCallProfile(readInt4, readInt5, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createCallProfile, 1);
                    return true;
                case 9:
                    int readInt7 = parcel.readInt();
                    ImsCallProfile imsCallProfile = (ImsCallProfile) parcel.readTypedObject(ImsCallProfile.CREATOR);
                    parcel.enforceNoDataAvail();
                    IImsCallSession createCallSession = createCallSession(readInt7, imsCallProfile);
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(createCallSession);
                    return true;
                case 10:
                    int readInt8 = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    IImsCallSession pendingCallSession = getPendingCallSession(readInt8, readString);
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
                    int readInt9 = parcel.readInt();
                    Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                    parcel.enforceNoDataAvail();
                    setUiTTYMode(readInt9, message);
                    parcel2.writeNoException();
                    return true;
                case 17:
                    IImsMultiEndpoint multiEndpointInterface = getMultiEndpointInterface();
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(multiEndpointInterface);
                    return true;
                case 18:
                    int readInt10 = parcel.readInt();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    changeAudioPath(readInt10, readInt11);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    int readInt12 = parcel.readInt();
                    int readInt13 = parcel.readInt();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int startLocalRingBackTone = startLocalRingBackTone(readInt12, readInt13, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeInt(startLocalRingBackTone);
                    return true;
                case 20:
                    int stopLocalRingBackTone = stopLocalRingBackTone();
                    parcel2.writeNoException();
                    parcel2.writeInt(stopLocalRingBackTone);
                    return true;
                case 21:
                    int readInt15 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setVideoCrtAudio(readInt15, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt16 = parcel.readInt();
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendDtmfEvent(readInt16, readString2);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString3 = parcel.readString();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String trn = getTrn(readString3, readString4);
                    parcel2.writeNoException();
                    parcel2.writeString(trn);
                    return true;
                case 24:
                    int readInt17 = parcel.readInt();
                    PublishDialog publishDialog = (PublishDialog) parcel.readTypedObject(PublishDialog.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendPublishDialog(readInt17, publishDialog);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCmcEmergencyCallSupported = isCmcEmergencyCallSupported(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCmcEmergencyCallSupported);
                    return true;
                case 26:
                    int readInt19 = parcel.readInt();
                    ISecImsMmTelEventListener asInterface4 = ISecImsMmTelEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSecImsMmTelEventListener(readInt19, asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 27:
                    int readInt20 = parcel.readInt();
                    IImsSmsListener asInterface5 = IImsSmsListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    setSmsListener(readInt20, asInterface5);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    int readInt21 = parcel.readInt();
                    int readInt22 = parcel.readInt();
                    int readInt23 = parcel.readInt();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    byte[] createByteArray = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    sendSms(readInt21, readInt22, readInt23, readString5, readString6, readBoolean2, createByteArray);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    int readInt24 = parcel.readInt();
                    int readInt25 = parcel.readInt();
                    int readInt26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setRetryCount(readInt24, readInt25, readInt26);
                    parcel2.writeNoException();
                    return true;
                case 30:
                    int readInt27 = parcel.readInt();
                    int readInt28 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onMemoryAvailable(readInt27, readInt28);
                    parcel2.writeNoException();
                    return true;
                case 31:
                    int readInt29 = parcel.readInt();
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setSmsc(readInt29, readString7);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    int readInt30 = parcel.readInt();
                    int readInt31 = parcel.readInt();
                    int readInt32 = parcel.readInt();
                    int readInt33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSms(readInt30, readInt31, readInt32, readInt33);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    int readInt34 = parcel.readInt();
                    int readInt35 = parcel.readInt();
                    int readInt36 = parcel.readInt();
                    int readInt37 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsReport(readInt34, readInt35, readInt36, readInt37);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt38 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onSmsReady(readInt38);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String smsFormat = getSmsFormat(readInt39);
                    parcel2.writeNoException();
                    parcel2.writeString(smsFormat);
                    return true;
                case 36:
                    int readInt40 = parcel.readInt();
                    int readInt41 = parcel.readInt();
                    int readInt42 = parcel.readInt();
                    byte[] createByteArray2 = parcel.createByteArray();
                    parcel.enforceNoDataAvail();
                    acknowledgeSmsWithPdu(readInt40, readInt41, readInt42, createByteArray2);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void endSession(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isConnected(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isOpened() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int getFeatureStatus() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iImsRegistrationListener);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public ImsCallProfile createCallProfile(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ImsCallProfile) obtain2.readTypedObject(ImsCallProfile.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsCallSession createCallSession(int i, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(imsCallProfile, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsCallSession getPendingCallSession(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsCallSession.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsUt getUtInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsUt.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsConfig getConfigInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsConfig.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void turnOnIms() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void turnOffIms() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsEcbm.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setUiTTYMode(int i, Message message) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return IImsMultiEndpoint.Stub.asInterface(obtain2.readStrongBinder());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void changeAudioPath(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int stopLocalRingBackTone() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setVideoCrtAudio(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void sendDtmfEvent(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public String getTrn(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(publishDialog, 0);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isCmcEmergencyCallSupported(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iSecImsMmTelEventListener);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iImsSmsListener);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setRetryCount(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void onMemoryAvailable(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSmsc(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void onSmsReady(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public String getSmsFormat(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
