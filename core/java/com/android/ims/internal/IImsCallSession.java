package com.android.ims.internal;

import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Message;
import android.p009os.Parcel;
import android.p009os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.RtpHeaderExtension;
import android.telephony.ims.aidl.IImsCallSessionListener;
import com.android.ims.internal.IImsVideoCallProvider;
import com.android.internal.telephony.SemRILConstants;
import java.util.List;

/* loaded from: classes4.dex */
public interface IImsCallSession extends IInterface {
    void accept(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void callSessionNotifyAnbr(int i, int i2, int i3) throws RemoteException;

    void cancelTransferCall() throws RemoteException;

    void close() throws RemoteException;

    void consultativeTransfer(IImsCallSession iImsCallSession) throws RemoteException;

    void deflect(String str) throws RemoteException;

    void extendToConference(String[] strArr) throws RemoteException;

    String getCallId() throws RemoteException;

    ImsCallProfile getCallProfile() throws RemoteException;

    ImsCallProfile getLocalCallProfile() throws RemoteException;

    String getProperty(String str) throws RemoteException;

    ImsCallProfile getRemoteCallProfile() throws RemoteException;

    int getState() throws RemoteException;

    IImsVideoCallProvider getVideoCallProvider() throws RemoteException;

    void hold(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void inviteParticipants(String[] strArr) throws RemoteException;

    boolean isInCall() throws RemoteException;

    boolean isMultiparty() throws RemoteException;

    void merge() throws RemoteException;

    void notifyReadyToHandleImsCallbacks() throws RemoteException;

    void reject(int i) throws RemoteException;

    void removeParticipants(String[] strArr) throws RemoteException;

    void resume(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    void sendDtmf(char c, Message message) throws RemoteException;

    void sendImsCallEvent(String str, Bundle bundle) throws RemoteException;

    void sendRtpHeaderExtensions(List<RtpHeaderExtension> list) throws RemoteException;

    void sendRttMessage(String str) throws RemoteException;

    void sendRttModifyRequest(ImsCallProfile imsCallProfile) throws RemoteException;

    void sendRttModifyResponse(boolean z) throws RemoteException;

    void sendUssd(String str) throws RemoteException;

    @Deprecated
    void setListener(android.telephony.ims.aidl.IImsCallSessionListener iImsCallSessionListener) throws RemoteException;

    void setMute(boolean z) throws RemoteException;

    void start(String str, ImsCallProfile imsCallProfile) throws RemoteException;

    void startConference(String[] strArr, ImsCallProfile imsCallProfile) throws RemoteException;

    void startDtmf(char c) throws RemoteException;

    void stopDtmf() throws RemoteException;

    void terminate(int i) throws RemoteException;

    void transfer(String str, boolean z) throws RemoteException;

    void update(int i, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException;

    public static class Default implements IImsCallSession {
        @Override // com.android.ims.internal.IImsCallSession
        public void close() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public String getCallId() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getCallProfile() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getLocalCallProfile() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public ImsCallProfile getRemoteCallProfile() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public String getProperty(String name) throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public int getState() throws RemoteException {
            return 0;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isInCall() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setListener(android.telephony.ims.aidl.IImsCallSessionListener listener) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void setMute(boolean muted) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void start(String callee, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startConference(String[] participants, ImsCallProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void accept(int callType, ImsStreamMediaProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void deflect(String deflectNumber) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void reject(int reason) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void transfer(String number, boolean isConfirmationRequired) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void consultativeTransfer(IImsCallSession transferToSession) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void terminate(int reason) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void hold(ImsStreamMediaProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void resume(ImsStreamMediaProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void merge() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void update(int callType, ImsStreamMediaProfile profile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void extendToConference(String[] participants) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void inviteParticipants(String[] participants) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void removeParticipants(String[] participants) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendDtmf(char c, Message result) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void startDtmf(char c) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void stopDtmf() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendUssd(String ussdMessage) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public IImsVideoCallProvider getVideoCallProvider() throws RemoteException {
            return null;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public boolean isMultiparty() throws RemoteException {
            return false;
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyRequest(ImsCallProfile toProfile) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttModifyResponse(boolean status) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRttMessage(String rttMessage) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendRtpHeaderExtensions(List<RtpHeaderExtension> extensions) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void cancelTransferCall() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void sendImsCallEvent(String event, Bundle extras) throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void notifyReadyToHandleImsCallbacks() throws RemoteException {
        }

        @Override // com.android.ims.internal.IImsCallSession
        public void callSessionNotifyAnbr(int mediaType, int direction, int bitsPerSecond) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IImsCallSession {
        public static final String DESCRIPTOR = "com.android.ims.internal.IImsCallSession";
        static final int TRANSACTION_accept = 13;
        static final int TRANSACTION_callSessionNotifyAnbr = 39;
        static final int TRANSACTION_cancelTransferCall = 36;
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_consultativeTransfer = 17;
        static final int TRANSACTION_deflect = 14;
        static final int TRANSACTION_extendToConference = 23;
        static final int TRANSACTION_getCallId = 2;
        static final int TRANSACTION_getCallProfile = 3;
        static final int TRANSACTION_getLocalCallProfile = 4;
        static final int TRANSACTION_getProperty = 6;
        static final int TRANSACTION_getRemoteCallProfile = 5;
        static final int TRANSACTION_getState = 7;
        static final int TRANSACTION_getVideoCallProvider = 30;
        static final int TRANSACTION_hold = 19;
        static final int TRANSACTION_inviteParticipants = 24;
        static final int TRANSACTION_isInCall = 8;
        static final int TRANSACTION_isMultiparty = 31;
        static final int TRANSACTION_merge = 21;
        static final int TRANSACTION_notifyReadyToHandleImsCallbacks = 38;
        static final int TRANSACTION_reject = 15;
        static final int TRANSACTION_removeParticipants = 25;
        static final int TRANSACTION_resume = 20;
        static final int TRANSACTION_sendDtmf = 26;
        static final int TRANSACTION_sendImsCallEvent = 37;
        static final int TRANSACTION_sendRtpHeaderExtensions = 35;
        static final int TRANSACTION_sendRttMessage = 34;
        static final int TRANSACTION_sendRttModifyRequest = 32;
        static final int TRANSACTION_sendRttModifyResponse = 33;
        static final int TRANSACTION_sendUssd = 29;
        static final int TRANSACTION_setListener = 9;
        static final int TRANSACTION_setMute = 10;
        static final int TRANSACTION_start = 11;
        static final int TRANSACTION_startConference = 12;
        static final int TRANSACTION_startDtmf = 27;
        static final int TRANSACTION_stopDtmf = 28;
        static final int TRANSACTION_terminate = 18;
        static final int TRANSACTION_transfer = 16;
        static final int TRANSACTION_update = 22;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IImsCallSession asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IImsCallSession)) {
                return (IImsCallSession) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return "close";
                case 2:
                    return "getCallId";
                case 3:
                    return "getCallProfile";
                case 4:
                    return "getLocalCallProfile";
                case 5:
                    return "getRemoteCallProfile";
                case 6:
                    return "getProperty";
                case 7:
                    return "getState";
                case 8:
                    return "isInCall";
                case 9:
                    return "setListener";
                case 10:
                    return "setMute";
                case 11:
                    return "start";
                case 12:
                    return "startConference";
                case 13:
                    return "accept";
                case 14:
                    return "deflect";
                case 15:
                    return SemRILConstants.CmcCall.CMC_CALL_SD_REJECT;
                case 16:
                    return "transfer";
                case 17:
                    return "consultativeTransfer";
                case 18:
                    return "terminate";
                case 19:
                    return SemRILConstants.CmcCall.CMC_CALL_SD_HOLD;
                case 20:
                    return "resume";
                case 21:
                    return "merge";
                case 22:
                    return "update";
                case 23:
                    return "extendToConference";
                case 24:
                    return "inviteParticipants";
                case 25:
                    return "removeParticipants";
                case 26:
                    return "sendDtmf";
                case 27:
                    return "startDtmf";
                case 28:
                    return "stopDtmf";
                case 29:
                    return "sendUssd";
                case 30:
                    return "getVideoCallProvider";
                case 31:
                    return "isMultiparty";
                case 32:
                    return "sendRttModifyRequest";
                case 33:
                    return "sendRttModifyResponse";
                case 34:
                    return "sendRttMessage";
                case 35:
                    return "sendRtpHeaderExtensions";
                case 36:
                    return "cancelTransferCall";
                case 37:
                    return "sendImsCallEvent";
                case 38:
                    return "notifyReadyToHandleImsCallbacks";
                case 39:
                    return "callSessionNotifyAnbr";
                default:
                    return null;
            }
        }

        @Override // android.p009os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.p009os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            switch (code) {
                case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    switch (code) {
                        case 1:
                            close();
                            reply.writeNoException();
                            return true;
                        case 2:
                            String _result = getCallId();
                            reply.writeNoException();
                            reply.writeString(_result);
                            return true;
                        case 3:
                            ImsCallProfile _result2 = getCallProfile();
                            reply.writeNoException();
                            reply.writeTypedObject(_result2, 1);
                            return true;
                        case 4:
                            ImsCallProfile _result3 = getLocalCallProfile();
                            reply.writeNoException();
                            reply.writeTypedObject(_result3, 1);
                            return true;
                        case 5:
                            ImsCallProfile _result4 = getRemoteCallProfile();
                            reply.writeNoException();
                            reply.writeTypedObject(_result4, 1);
                            return true;
                        case 6:
                            String _arg0 = data.readString();
                            data.enforceNoDataAvail();
                            String _result5 = getProperty(_arg0);
                            reply.writeNoException();
                            reply.writeString(_result5);
                            return true;
                        case 7:
                            int _result6 = getState();
                            reply.writeNoException();
                            reply.writeInt(_result6);
                            return true;
                        case 8:
                            boolean _result7 = isInCall();
                            reply.writeNoException();
                            reply.writeBoolean(_result7);
                            return true;
                        case 9:
                            android.telephony.ims.aidl.IImsCallSessionListener _arg02 = IImsCallSessionListener.Stub.asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            setListener(_arg02);
                            reply.writeNoException();
                            return true;
                        case 10:
                            boolean _arg03 = data.readBoolean();
                            data.enforceNoDataAvail();
                            setMute(_arg03);
                            reply.writeNoException();
                            return true;
                        case 11:
                            String _arg04 = data.readString();
                            ImsCallProfile _arg1 = (ImsCallProfile) data.readTypedObject(ImsCallProfile.CREATOR);
                            data.enforceNoDataAvail();
                            start(_arg04, _arg1);
                            reply.writeNoException();
                            return true;
                        case 12:
                            String[] _arg05 = data.createStringArray();
                            ImsCallProfile _arg12 = (ImsCallProfile) data.readTypedObject(ImsCallProfile.CREATOR);
                            data.enforceNoDataAvail();
                            startConference(_arg05, _arg12);
                            reply.writeNoException();
                            return true;
                        case 13:
                            int _arg06 = data.readInt();
                            ImsStreamMediaProfile _arg13 = (ImsStreamMediaProfile) data.readTypedObject(ImsStreamMediaProfile.CREATOR);
                            data.enforceNoDataAvail();
                            accept(_arg06, _arg13);
                            reply.writeNoException();
                            return true;
                        case 14:
                            String _arg07 = data.readString();
                            data.enforceNoDataAvail();
                            deflect(_arg07);
                            reply.writeNoException();
                            return true;
                        case 15:
                            int _arg08 = data.readInt();
                            data.enforceNoDataAvail();
                            reject(_arg08);
                            reply.writeNoException();
                            return true;
                        case 16:
                            String _arg09 = data.readString();
                            boolean _arg14 = data.readBoolean();
                            data.enforceNoDataAvail();
                            transfer(_arg09, _arg14);
                            reply.writeNoException();
                            return true;
                        case 17:
                            IImsCallSession _arg010 = asInterface(data.readStrongBinder());
                            data.enforceNoDataAvail();
                            consultativeTransfer(_arg010);
                            reply.writeNoException();
                            return true;
                        case 18:
                            int _arg011 = data.readInt();
                            data.enforceNoDataAvail();
                            terminate(_arg011);
                            reply.writeNoException();
                            return true;
                        case 19:
                            ImsStreamMediaProfile _arg012 = (ImsStreamMediaProfile) data.readTypedObject(ImsStreamMediaProfile.CREATOR);
                            data.enforceNoDataAvail();
                            hold(_arg012);
                            reply.writeNoException();
                            return true;
                        case 20:
                            ImsStreamMediaProfile _arg013 = (ImsStreamMediaProfile) data.readTypedObject(ImsStreamMediaProfile.CREATOR);
                            data.enforceNoDataAvail();
                            resume(_arg013);
                            reply.writeNoException();
                            return true;
                        case 21:
                            merge();
                            reply.writeNoException();
                            return true;
                        case 22:
                            int _arg014 = data.readInt();
                            ImsStreamMediaProfile _arg15 = (ImsStreamMediaProfile) data.readTypedObject(ImsStreamMediaProfile.CREATOR);
                            data.enforceNoDataAvail();
                            update(_arg014, _arg15);
                            reply.writeNoException();
                            return true;
                        case 23:
                            String[] _arg015 = data.createStringArray();
                            data.enforceNoDataAvail();
                            extendToConference(_arg015);
                            reply.writeNoException();
                            return true;
                        case 24:
                            String[] _arg016 = data.createStringArray();
                            data.enforceNoDataAvail();
                            inviteParticipants(_arg016);
                            reply.writeNoException();
                            return true;
                        case 25:
                            String[] _arg017 = data.createStringArray();
                            data.enforceNoDataAvail();
                            removeParticipants(_arg017);
                            reply.writeNoException();
                            return true;
                        case 26:
                            char _arg018 = (char) data.readInt();
                            Message _arg16 = (Message) data.readTypedObject(Message.CREATOR);
                            data.enforceNoDataAvail();
                            sendDtmf(_arg018, _arg16);
                            reply.writeNoException();
                            return true;
                        case 27:
                            char _arg019 = (char) data.readInt();
                            data.enforceNoDataAvail();
                            startDtmf(_arg019);
                            reply.writeNoException();
                            return true;
                        case 28:
                            stopDtmf();
                            reply.writeNoException();
                            return true;
                        case 29:
                            String _arg020 = data.readString();
                            data.enforceNoDataAvail();
                            sendUssd(_arg020);
                            reply.writeNoException();
                            return true;
                        case 30:
                            IImsVideoCallProvider _result8 = getVideoCallProvider();
                            reply.writeNoException();
                            reply.writeStrongInterface(_result8);
                            return true;
                        case 31:
                            boolean _result9 = isMultiparty();
                            reply.writeNoException();
                            reply.writeBoolean(_result9);
                            return true;
                        case 32:
                            ImsCallProfile _arg021 = (ImsCallProfile) data.readTypedObject(ImsCallProfile.CREATOR);
                            data.enforceNoDataAvail();
                            sendRttModifyRequest(_arg021);
                            reply.writeNoException();
                            return true;
                        case 33:
                            boolean _arg022 = data.readBoolean();
                            data.enforceNoDataAvail();
                            sendRttModifyResponse(_arg022);
                            reply.writeNoException();
                            return true;
                        case 34:
                            String _arg023 = data.readString();
                            data.enforceNoDataAvail();
                            sendRttMessage(_arg023);
                            reply.writeNoException();
                            return true;
                        case 35:
                            List<RtpHeaderExtension> _arg024 = data.createTypedArrayList(RtpHeaderExtension.CREATOR);
                            data.enforceNoDataAvail();
                            sendRtpHeaderExtensions(_arg024);
                            reply.writeNoException();
                            return true;
                        case 36:
                            cancelTransferCall();
                            reply.writeNoException();
                            return true;
                        case 37:
                            String _arg025 = data.readString();
                            Bundle _arg17 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                            data.enforceNoDataAvail();
                            sendImsCallEvent(_arg025, _arg17);
                            reply.writeNoException();
                            return true;
                        case 38:
                            notifyReadyToHandleImsCallbacks();
                            reply.writeNoException();
                            return true;
                        case 39:
                            int _arg026 = data.readInt();
                            int _arg18 = data.readInt();
                            int _arg2 = data.readInt();
                            data.enforceNoDataAvail();
                            callSessionNotifyAnbr(_arg026, _arg18, _arg2);
                            reply.writeNoException();
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements IImsCallSession {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.p009os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public String getCallId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public ImsCallProfile getCallProfile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    ImsCallProfile _result = (ImsCallProfile) _reply.readTypedObject(ImsCallProfile.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public ImsCallProfile getLocalCallProfile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    ImsCallProfile _result = (ImsCallProfile) _reply.readTypedObject(ImsCallProfile.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public ImsCallProfile getRemoteCallProfile() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    ImsCallProfile _result = (ImsCallProfile) _reply.readTypedObject(ImsCallProfile.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public String getProperty(String name) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(name);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public int getState() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public boolean isInCall() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void setListener(android.telephony.ims.aidl.IImsCallSessionListener listener) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(listener);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void setMute(boolean muted) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(muted);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void start(String callee, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callee);
                    _data.writeTypedObject(profile, 0);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void startConference(String[] participants, ImsCallProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(participants);
                    _data.writeTypedObject(profile, 0);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void accept(int callType, ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callType);
                    _data.writeTypedObject(profile, 0);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void deflect(String deflectNumber) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(deflectNumber);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void reject(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void transfer(String number, boolean isConfirmationRequired) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    _data.writeBoolean(isConfirmationRequired);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void consultativeTransfer(IImsCallSession transferToSession) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(transferToSession);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void terminate(int reason) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(reason);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void hold(ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(profile, 0);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void resume(ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(profile, 0);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void merge() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void update(int callType, ImsStreamMediaProfile profile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(callType);
                    _data.writeTypedObject(profile, 0);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void extendToConference(String[] participants) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(participants);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void inviteParticipants(String[] participants) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(participants);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void removeParticipants(String[] participants) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringArray(participants);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendDtmf(char c, Message result) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(c);
                    _data.writeTypedObject(result, 0);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void startDtmf(char c) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(c);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void stopDtmf() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendUssd(String ussdMessage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(ussdMessage);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public IImsVideoCallProvider getVideoCallProvider() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    IImsVideoCallProvider _result = IImsVideoCallProvider.Stub.asInterface(_reply.readStrongBinder());
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public boolean isMultiparty() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttModifyRequest(ImsCallProfile toProfile) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(toProfile, 0);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttModifyResponse(boolean status) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeBoolean(status);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRttMessage(String rttMessage) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(rttMessage);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendRtpHeaderExtensions(List<RtpHeaderExtension> extensions) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedList(extensions, 0);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void cancelTransferCall() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void sendImsCallEvent(String event, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(event);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void notifyReadyToHandleImsCallbacks() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.android.ims.internal.IImsCallSession
            public void callSessionNotifyAnbr(int mediaType, int direction, int bitsPerSecond) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(mediaType);
                    _data.writeInt(direction);
                    _data.writeInt(bitsPerSecond);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 38;
        }
    }
}
