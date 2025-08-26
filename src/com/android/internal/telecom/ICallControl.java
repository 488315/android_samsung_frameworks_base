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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICallControl.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICallControl)) {
                return (ICallControl) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setActive(string, resultReceiver);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    String string2 = parcel.readString();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    answer(i3, string2, resultReceiver2);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    ResultReceiver resultReceiver3 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setInactive(string3, resultReceiver3);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    DisconnectCause disconnectCause = (DisconnectCause) parcel.readTypedObject(DisconnectCause.CREATOR);
                    ResultReceiver resultReceiver4 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    disconnect(string4, disconnectCause, resultReceiver4);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    ResultReceiver resultReceiver5 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    startCallStreaming(string5, resultReceiver5);
                    return true;
                case 6:
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    ResultReceiver resultReceiver6 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestCallEndpointChange(callEndpoint, resultReceiver6);
                    return true;
                case 7:
                    boolean z = parcel.readBoolean();
                    ResultReceiver resultReceiver7 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    setMuteState(z, resultReceiver7);
                    return true;
                case 8:
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    sendEvent(string6, string7, bundle);
                    return true;
                case 9:
                    int i4 = parcel.readInt();
                    String string8 = parcel.readString();
                    ResultReceiver resultReceiver8 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestVideoState(i4, string8, resultReceiver8);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void answer(int i, String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setInactive(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void disconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(disconnectCause, 0);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void startCallStreaming(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void requestCallEndpointChange(CallEndpoint callEndpoint, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeTypedObject(callEndpoint, 0);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void setMuteState(boolean z, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void sendEvent(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallControl
            public void requestVideoState(int i, String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallControl.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
