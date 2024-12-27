package com.android.internal.telecom;

import android.media.MediaMetrics;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telecom.CallEndpoint;
import android.telecom.DisconnectCause;
import com.android.internal.telephony.SemRILConstants;

/* loaded from: classes5.dex */
public interface ICallControl extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallControl";

    void answer(int i, String str, ResultReceiver resultReceiver) throws RemoteException;

    void disconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException;

    void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver resultReceiver) throws RemoteException;

    void requestVideoState(int i, String str, ResultReceiver resultReceiver) throws RemoteException;

    void sendEvent(String str, String str2, Bundle bundle) throws RemoteException;

    void setActive(String str, ResultReceiver resultReceiver) throws RemoteException;

    void setInactive(String str, ResultReceiver resultReceiver) throws RemoteException;

    void setMuteState(boolean z, ResultReceiver resultReceiver) throws RemoteException;

    void startCallStreaming(String str, ResultReceiver resultReceiver) throws RemoteException;

    public static class Default implements ICallControl {
        @Override // com.android.internal.telecom.ICallControl
        public void setActive(String callId, ResultReceiver callback) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void answer(int videoState, String callId, ResultReceiver callback) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void setInactive(String callId, ResultReceiver callback) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void disconnect(String callId, DisconnectCause disconnectCause, ResultReceiver callback) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void startCallStreaming(String callId, ResultReceiver callback) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver callback) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void setMuteState(boolean isMuted, ResultReceiver callback) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void sendEvent(String callId, String event, Bundle extras) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void requestVideoState(int videoState, String callId, ResultReceiver callback) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICallControl {
        static final int TRANSACTION_answer = 2;
        static final int TRANSACTION_disconnect = 4;
        static final int TRANSACTION_requestCallEndpointChange = 6;
        static final int TRANSACTION_requestVideoState = 9;
        static final int TRANSACTION_sendEvent = 8;
        static final int TRANSACTION_setActive = 1;
        static final int TRANSACTION_setInactive = 3;
        static final int TRANSACTION_setMuteState = 7;
        static final int TRANSACTION_startCallStreaming = 5;

        public Stub() {
            attachInterface(this, ICallControl.DESCRIPTOR);
        }

        public static ICallControl asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICallControl.DESCRIPTOR);
            if (iin != null && (iin instanceof ICallControl)) {
                return (ICallControl) iin;
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
                    return "setActive";
                case 2:
                    return SemRILConstants.CmcCall.CMC_CALL_SD_ANSWER;
                case 3:
                    return "setInactive";
                case 4:
                    return MediaMetrics.Value.DISCONNECT;
                case 5:
                    return "startCallStreaming";
                case 6:
                    return "requestCallEndpointChange";
                case 7:
                    return "setMuteState";
                case 8:
                    return "sendEvent";
                case 9:
                    return "requestVideoState";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICallControl.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICallControl.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    ResultReceiver _arg1 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    setActive(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    String _arg12 = data.readString();
                    ResultReceiver _arg2 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    answer(_arg02, _arg12, _arg2);
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    ResultReceiver _arg13 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    setInactive(_arg03, _arg13);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    DisconnectCause _arg14 = (DisconnectCause) data.readTypedObject(DisconnectCause.CREATOR);
                    ResultReceiver _arg22 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    disconnect(_arg04, _arg14, _arg22);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    ResultReceiver _arg15 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    startCallStreaming(_arg05, _arg15);
                    return true;
                case 6:
                    CallEndpoint _arg06 = (CallEndpoint) data.readTypedObject(CallEndpoint.CREATOR);
                    ResultReceiver _arg16 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    requestCallEndpointChange(_arg06, _arg16);
                    return true;
                case 7:
                    boolean _arg07 = data.readBoolean();
                    ResultReceiver _arg17 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    setMuteState(_arg07, _arg17);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    String _arg18 = data.readString();
                    Bundle _arg23 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    sendEvent(_arg08, _arg18, _arg23);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    String _arg19 = data.readString();
                    ResultReceiver _arg24 = (ResultReceiver) data.readTypedObject(ResultReceiver.CREATOR);
                    data.enforceNoDataAvail();
                    requestVideoState(_arg09, _arg19, _arg24);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICallControl {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallControl.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setActive(String callId, ResultReceiver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void answer(int videoState, String callId, ResultReceiver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeInt(videoState);
                    _data.writeString(callId);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setInactive(String callId, ResultReceiver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void disconnect(String callId, DisconnectCause disconnectCause, ResultReceiver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeTypedObject(disconnectCause, 0);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void startCallStreaming(String callId, ResultReceiver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeTypedObject(callEndpoint, 0);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(6, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setMuteState(boolean isMuted, ResultReceiver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeBoolean(isMuted);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(7, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void sendEvent(String callId, String event, Bundle extras) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeString(callId);
                    _data.writeString(event);
                    _data.writeTypedObject(extras, 0);
                    this.mRemote.transact(8, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void requestVideoState(int videoState, String callId, ResultReceiver callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    _data.writeInt(videoState);
                    _data.writeString(callId);
                    _data.writeTypedObject(callback, 0);
                    this.mRemote.transact(9, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }
    }
}
