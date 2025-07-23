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

/* loaded from: classes4.dex */
public interface ICallControl extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallControl";

    public static class Default implements ICallControl {
        @Override // com.android.internal.telecom.ICallControl
        public void answer(int i, String str, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallControl
        public void disconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void requestVideoState(int i, String str, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void sendEvent(String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void setActive(String str, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void setInactive(String str, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void setMuteState(boolean z, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallControl
        public void startCallStreaming(String str, ResultReceiver resultReceiver) throws RemoteException {
        }
    }

    void answer(int i, String str, ResultReceiver resultReceiver) throws RemoteException;

    void disconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException;

    void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver resultReceiver) throws RemoteException;

    void requestVideoState(int i, String str, ResultReceiver resultReceiver) throws RemoteException;

    void sendEvent(String str, String str2, Bundle bundle) throws RemoteException;

    void setActive(String str, ResultReceiver resultReceiver) throws RemoteException;

    void setInactive(String str, ResultReceiver resultReceiver) throws RemoteException;

    void setMuteState(boolean z, ResultReceiver resultReceiver) throws RemoteException;

    void startCallStreaming(String str, ResultReceiver resultReceiver) throws RemoteException;

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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 8;
        }

        public Stub() {
            attachInterface(this, ICallControl.DESCRIPTOR);
        }

        public static ICallControl asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICallControl.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICallControl)) {
                return (ICallControl) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICallControl.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallControl.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setActive(readString, resultReceiver);
                    return true;
                case 2:
                    int readInt = parcel.readInt();
                    String readString2 = parcel.readString();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    answer(readInt, readString2, resultReceiver2);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    ResultReceiver resultReceiver3 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInactive(readString3, resultReceiver3);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    DisconnectCause disconnectCause = (DisconnectCause) parcel.readTypedObject(DisconnectCause.CREATOR);
                    ResultReceiver resultReceiver4 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    disconnect(readString4, disconnectCause, resultReceiver4);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    ResultReceiver resultReceiver5 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    startCallStreaming(readString5, resultReceiver5);
                    return true;
                case 6:
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    ResultReceiver resultReceiver6 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCallEndpointChange(callEndpoint, resultReceiver6);
                    return true;
                case 7:
                    boolean readBoolean = parcel.readBoolean();
                    ResultReceiver resultReceiver7 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMuteState(readBoolean, resultReceiver7);
                    return true;
                case 8:
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEvent(readString6, readString7, bundle);
                    return true;
                case 9:
                    int readInt2 = parcel.readInt();
                    String readString8 = parcel.readString();
                    ResultReceiver resultReceiver8 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestVideoState(readInt2, readString8, resultReceiver8);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICallControl {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallControl.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setActive(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void answer(int i, String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setInactive(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void disconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(disconnectCause, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void startCallStreaming(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeTypedObject(callEndpoint, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setMuteState(boolean z, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void sendEvent(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void requestVideoState(int i, String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
