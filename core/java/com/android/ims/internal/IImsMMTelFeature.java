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

import com.android.internal.telephony.PublishDialog;

/* loaded from: classes5.dex */
public interface IImsMMTelFeature extends IInterface {
    void acknowledgeSms(int i, int i2, int i3, int i4) throws RemoteException;

    void acknowledgeSmsReport(int i, int i2, int i3, int i4) throws RemoteException;

    void acknowledgeSmsWithPdu(int i, int i2, int i3, byte[] bArr) throws RemoteException;

    void addRegistrationListener(IImsRegistrationListener iImsRegistrationListener)
            throws RemoteException;

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

    void removeRegistrationListener(IImsRegistrationListener iImsRegistrationListener)
            throws RemoteException;

    void sendDtmfEvent(int i, String str) throws RemoteException;

    void sendPublishDialog(int i, PublishDialog publishDialog) throws RemoteException;

    void sendSms(int i, int i2, int i3, String str, String str2, boolean z, byte[] bArr)
            throws RemoteException;

    void setRetryCount(int i, int i2, int i3) throws RemoteException;

    void setSecImsMmTelEventListener(int i, ISecImsMmTelEventListener iSecImsMmTelEventListener)
            throws RemoteException;

    void setSmsListener(int i, IImsSmsListener iImsSmsListener) throws RemoteException;

    void setSmsc(int i, String str) throws RemoteException;

    void setUiTTYMode(int i, Message message) throws RemoteException;

    void setVideoCrtAudio(int i, boolean z) throws RemoteException;

    int startLocalRingBackTone(int i, int i2, int i3) throws RemoteException;

    int startSession(PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener)
            throws RemoteException;

    int stopLocalRingBackTone() throws RemoteException;

    void turnOffIms() throws RemoteException;

    void turnOnIms() throws RemoteException;

    public static class Default implements IImsMMTelFeature {
        @Override // com.android.ims.internal.IImsMMTelFeature
        public int startSession(PendingIntent incomingCallIntent, IImsRegistrationListener listener)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void endSession(int sessionId) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isConnected(int callSessionType, int callType) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isOpened() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int getFeatureStatus() throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void addRegistrationListener(IImsRegistrationListener listener)
                throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void removeRegistrationListener(IImsRegistrationListener listener)
                throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public ImsCallProfile createCallProfile(int sessionId, int callSessionType, int callType)
                throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsCallSession createCallSession(int sessionId, ImsCallProfile profile)
                throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsCallSession getPendingCallSession(int sessionId, String callId)
                throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsUt getUtInterface() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsConfig getConfigInterface() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void turnOnIms() throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void turnOffIms() throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsEcbm getEcbmInterface() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setUiTTYMode(int uiTtyMode, Message onComplete) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void changeAudioPath(int phoneId, int direction) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int startLocalRingBackTone(int streamType, int volume, int toneType)
                throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public int stopLocalRingBackTone() throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setVideoCrtAudio(int phoneId, boolean on) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendDtmfEvent(int phoneId, String dtmfEvent) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public String getTrn(String srcMsisdn, String dstMsisdn) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendPublishDialog(int phoneId, PublishDialog publishDialog)
                throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public boolean isCmcEmergencyCallSupported(int phoneId) throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSecImsMmTelEventListener(int phoneId, ISecImsMmTelEventListener listener)
                throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSmsListener(int phoneId, IImsSmsListener l) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void sendSms(
                int phoneId,
                int token,
                int messageRef,
                String format,
                String smsc,
                boolean isRetry,
                byte[] pdu)
                throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setRetryCount(int phoneId, int token, int retryCount) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void onMemoryAvailable(int phoneId, int token) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void setSmsc(int phoneId, String smsc) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSms(int phoneId, int token, int messageRef, int result)
                throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSmsReport(int phoneId, int token, int messageRef, int result)
                throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void onSmsReady(int phoneId) throws RemoteException {}

        @Override // com.android.ims.internal.IImsMMTelFeature
        public String getSmsFormat(int phoneId) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsMMTelFeature
        public void acknowledgeSmsWithPdu(int phoneId, int token, int messageRef, byte[] data)
                throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IImsMMTelFeature {
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

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsMMTelFeature asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IImsMMTelFeature)) {
                return (IImsMMTelFeature) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    PendingIntent _arg0 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    IImsRegistrationListener _arg1 =
                            IImsRegistrationListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result = startSession(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    endSession(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    int _arg03 = data.readInt();
                    int _arg12 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result2 = isConnected(_arg03, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result2);
                    return true;
                case 4:
                    boolean _result3 = isOpened();
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 5:
                    int _result4 = getFeatureStatus();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 6:
                    IImsRegistrationListener _arg04 =
                            IImsRegistrationListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addRegistrationListener(_arg04);
                    reply.writeNoException();
                    return true;
                case 7:
                    IImsRegistrationListener _arg05 =
                            IImsRegistrationListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeRegistrationListener(_arg05);
                    reply.writeNoException();
                    return true;
                case 8:
                    int _arg06 = data.readInt();
                    int _arg13 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    ImsCallProfile _result5 = createCallProfile(_arg06, _arg13, _arg2);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 9:
                    int _arg07 = data.readInt();
                    ImsCallProfile _arg14 =
                            (ImsCallProfile) data.readTypedObject(ImsCallProfile.CREATOR);
                    data.enforceNoDataAvail();
                    IImsCallSession _result6 = createCallSession(_arg07, _arg14);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result6);
                    return true;
                case 10:
                    int _arg08 = data.readInt();
                    String _arg15 = data.readString();
                    data.enforceNoDataAvail();
                    IImsCallSession _result7 = getPendingCallSession(_arg08, _arg15);
                    reply.writeNoException();
                    reply.writeStrongInterface(_result7);
                    return true;
                case 11:
                    IImsUt _result8 = getUtInterface();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result8);
                    return true;
                case 12:
                    IImsConfig _result9 = getConfigInterface();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result9);
                    return true;
                case 13:
                    turnOnIms();
                    reply.writeNoException();
                    return true;
                case 14:
                    turnOffIms();
                    reply.writeNoException();
                    return true;
                case 15:
                    IImsEcbm _result10 = getEcbmInterface();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result10);
                    return true;
                case 16:
                    int _arg09 = data.readInt();
                    Message _arg16 = (Message) data.readTypedObject(Message.CREATOR);
                    data.enforceNoDataAvail();
                    setUiTTYMode(_arg09, _arg16);
                    reply.writeNoException();
                    return true;
                case 17:
                    IImsMultiEndpoint _result11 = getMultiEndpointInterface();
                    reply.writeNoException();
                    reply.writeStrongInterface(_result11);
                    return true;
                case 18:
                    int _arg010 = data.readInt();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    changeAudioPath(_arg010, _arg17);
                    reply.writeNoException();
                    return true;
                case 19:
                    int _arg011 = data.readInt();
                    int _arg18 = data.readInt();
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result12 = startLocalRingBackTone(_arg011, _arg18, _arg22);
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 20:
                    int _result13 = stopLocalRingBackTone();
                    reply.writeNoException();
                    reply.writeInt(_result13);
                    return true;
                case 21:
                    int _arg012 = data.readInt();
                    boolean _arg19 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setVideoCrtAudio(_arg012, _arg19);
                    reply.writeNoException();
                    return true;
                case 22:
                    int _arg013 = data.readInt();
                    String _arg110 = data.readString();
                    data.enforceNoDataAvail();
                    sendDtmfEvent(_arg013, _arg110);
                    reply.writeNoException();
                    return true;
                case 23:
                    String _arg014 = data.readString();
                    String _arg111 = data.readString();
                    data.enforceNoDataAvail();
                    String _result14 = getTrn(_arg014, _arg111);
                    reply.writeNoException();
                    reply.writeString(_result14);
                    return true;
                case 24:
                    int _arg015 = data.readInt();
                    PublishDialog _arg112 =
                            (PublishDialog) data.readTypedObject(PublishDialog.CREATOR);
                    data.enforceNoDataAvail();
                    sendPublishDialog(_arg015, _arg112);
                    reply.writeNoException();
                    return true;
                case 25:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result15 = isCmcEmergencyCallSupported(_arg016);
                    reply.writeNoException();
                    reply.writeBoolean(_result15);
                    return true;
                case 26:
                    int _arg017 = data.readInt();
                    ISecImsMmTelEventListener _arg113 =
                            ISecImsMmTelEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setSecImsMmTelEventListener(_arg017, _arg113);
                    reply.writeNoException();
                    return true;
                case 27:
                    int _arg018 = data.readInt();
                    IImsSmsListener _arg114 =
                            IImsSmsListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setSmsListener(_arg018, _arg114);
                    reply.writeNoException();
                    return true;
                case 28:
                    int _arg019 = data.readInt();
                    int _arg115 = data.readInt();
                    int _arg23 = data.readInt();
                    String _arg3 = data.readString();
                    String _arg4 = data.readString();
                    boolean _arg5 = data.readBoolean();
                    byte[] _arg6 = data.createByteArray();
                    data.enforceNoDataAvail();
                    sendSms(_arg019, _arg115, _arg23, _arg3, _arg4, _arg5, _arg6);
                    reply.writeNoException();
                    return true;
                case 29:
                    int _arg020 = data.readInt();
                    int _arg116 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    setRetryCount(_arg020, _arg116, _arg24);
                    reply.writeNoException();
                    return true;
                case 30:
                    int _arg021 = data.readInt();
                    int _arg117 = data.readInt();
                    data.enforceNoDataAvail();
                    onMemoryAvailable(_arg021, _arg117);
                    reply.writeNoException();
                    return true;
                case 31:
                    int _arg022 = data.readInt();
                    String _arg118 = data.readString();
                    data.enforceNoDataAvail();
                    setSmsc(_arg022, _arg118);
                    reply.writeNoException();
                    return true;
                case 32:
                    int _arg023 = data.readInt();
                    int _arg119 = data.readInt();
                    int _arg25 = data.readInt();
                    int _arg32 = data.readInt();
                    data.enforceNoDataAvail();
                    acknowledgeSms(_arg023, _arg119, _arg25, _arg32);
                    reply.writeNoException();
                    return true;
                case 33:
                    int _arg024 = data.readInt();
                    int _arg120 = data.readInt();
                    int _arg26 = data.readInt();
                    int _arg33 = data.readInt();
                    data.enforceNoDataAvail();
                    acknowledgeSmsReport(_arg024, _arg120, _arg26, _arg33);
                    reply.writeNoException();
                    return true;
                case 34:
                    int _arg025 = data.readInt();
                    data.enforceNoDataAvail();
                    onSmsReady(_arg025);
                    reply.writeNoException();
                    return true;
                case 35:
                    int _arg026 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result16 = getSmsFormat(_arg026);
                    reply.writeNoException();
                    reply.writeString(_result16);
                    return true;
                case 36:
                    int _arg027 = data.readInt();
                    int _arg121 = data.readInt();
                    int _arg27 = data.readInt();
                    byte[] _arg34 = data.createByteArray();
                    data.enforceNoDataAvail();
                    acknowledgeSmsWithPdu(_arg027, _arg121, _arg27, _arg34);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IImsMMTelFeature {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int startSession(
                    PendingIntent incomingCallIntent, IImsRegistrationListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(incomingCallIntent, 0);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void endSession(int sessionId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isConnected(int callSessionType, int callType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callSessionType);
                    _data.writeInt(callType);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isOpened() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int getFeatureStatus() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void addRegistrationListener(IImsRegistrationListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void removeRegistrationListener(IImsRegistrationListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public ImsCallProfile createCallProfile(
                    int sessionId, int callSessionType, int callType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeInt(callSessionType);
                    _data.writeInt(callType);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    ImsCallProfile _result =
                            (ImsCallProfile) _reply.readTypedObject(ImsCallProfile.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsCallSession createCallSession(int sessionId, ImsCallProfile profile)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeTypedObject(profile, 0);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    IImsCallSession _result =
                            IImsCallSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsCallSession getPendingCallSession(int sessionId, String callId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(sessionId);
                    _data.writeString(callId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    IImsCallSession _result =
                            IImsCallSession.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsUt getUtInterface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    IImsUt _result = IImsUt.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsConfig getConfigInterface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    IImsConfig _result = IImsConfig.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void turnOnIms() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void turnOffIms() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsEcbm getEcbmInterface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    IImsEcbm _result = IImsEcbm.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setUiTTYMode(int uiTtyMode, Message onComplete) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(uiTtyMode);
                    _data.writeTypedObject(onComplete, 0);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    IImsMultiEndpoint _result =
                            IImsMultiEndpoint.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void changeAudioPath(int phoneId, int direction) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(direction);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int startLocalRingBackTone(int streamType, int volume, int toneType)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(streamType);
                    _data.writeInt(volume);
                    _data.writeInt(toneType);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public int stopLocalRingBackTone() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setVideoCrtAudio(int phoneId, boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeBoolean(on);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void sendDtmfEvent(int phoneId, String dtmfEvent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(dtmfEvent);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public String getTrn(String srcMsisdn, String dstMsisdn) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(srcMsisdn);
                    _data.writeString(dstMsisdn);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void sendPublishDialog(int phoneId, PublishDialog publishDialog)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeTypedObject(publishDialog, 0);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public boolean isCmcEmergencyCallSupported(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSecImsMmTelEventListener(int phoneId, ISecImsMmTelEventListener listener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSmsListener(int phoneId, IImsSmsListener l) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeStrongInterface(l);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

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
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(token);
                    _data.writeInt(messageRef);
                    _data.writeString(format);
                    _data.writeString(smsc);
                    _data.writeBoolean(isRetry);
                    _data.writeByteArray(pdu);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setRetryCount(int phoneId, int token, int retryCount)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(token);
                    _data.writeInt(retryCount);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void onMemoryAvailable(int phoneId, int token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(token);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void setSmsc(int phoneId, String smsc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(smsc);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSms(int phoneId, int token, int messageRef, int result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(token);
                    _data.writeInt(messageRef);
                    _data.writeInt(result);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSmsReport(int phoneId, int token, int messageRef, int result)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(token);
                    _data.writeInt(messageRef);
                    _data.writeInt(result);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void onSmsReady(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public String getSmsFormat(int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsMMTelFeature
            public void acknowledgeSmsWithPdu(int phoneId, int token, int messageRef, byte[] data)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeInt(token);
                    _data.writeInt(messageRef);
                    _data.writeByteArray(data);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 35;
        }
    }
}
