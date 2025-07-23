package com.android.internal.telecom;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.telecom.CallEndpoint;
import android.telecom.CallException;
import android.telecom.DisconnectCause;
import com.android.internal.telecom.ICallControl;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICallEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallEventCallback";

    public static class Default implements ICallEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAddCallControl(String str, int i, ICallControl iCallControl, CallException callException) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAnswer(String str, int i, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallEndpointChanged(String str, CallEndpoint callEndpoint) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingFailed(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onCallStreamingStarted(String str, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onDisconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onEvent(String str, String str2, Bundle bundle) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onMuteStateChanged(String str, boolean z) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetActive(String str, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onSetInactive(String str, ResultReceiver resultReceiver) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void onVideoStateChanged(String str, int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallEventCallback
        public void removeCallFromTransactionalServiceWrapper(String str) throws RemoteException {
        }
    }

    void onAddCallControl(String str, int i, ICallControl iCallControl, CallException callException) throws RemoteException;

    void onAnswer(String str, int i, ResultReceiver resultReceiver) throws RemoteException;

    void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list) throws RemoteException;

    void onCallEndpointChanged(String str, CallEndpoint callEndpoint) throws RemoteException;

    void onCallStreamingFailed(String str, int i) throws RemoteException;

    void onCallStreamingStarted(String str, ResultReceiver resultReceiver) throws RemoteException;

    void onDisconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException;

    void onEvent(String str, String str2, Bundle bundle) throws RemoteException;

    void onMuteStateChanged(String str, boolean z) throws RemoteException;

    void onSetActive(String str, ResultReceiver resultReceiver) throws RemoteException;

    void onSetInactive(String str, ResultReceiver resultReceiver) throws RemoteException;

    void onVideoStateChanged(String str, int i) throws RemoteException;

    void removeCallFromTransactionalServiceWrapper(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallEventCallback {
        static final int TRANSACTION_onAddCallControl = 1;
        static final int TRANSACTION_onAnswer = 4;
        static final int TRANSACTION_onAvailableCallEndpointsChanged = 9;
        static final int TRANSACTION_onCallEndpointChanged = 8;
        static final int TRANSACTION_onCallStreamingFailed = 7;
        static final int TRANSACTION_onCallStreamingStarted = 6;
        static final int TRANSACTION_onDisconnect = 5;
        static final int TRANSACTION_onEvent = 12;
        static final int TRANSACTION_onMuteStateChanged = 10;
        static final int TRANSACTION_onSetActive = 2;
        static final int TRANSACTION_onSetInactive = 3;
        static final int TRANSACTION_onVideoStateChanged = 11;
        static final int TRANSACTION_removeCallFromTransactionalServiceWrapper = 13;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 12;
        }

        public Stub() {
            attachInterface(this, ICallEventCallback.DESCRIPTOR);
        }

        public static ICallEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICallEventCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICallEventCallback)) {
                return (ICallEventCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onAddCallControl";
                case 2:
                    return "onSetActive";
                case 3:
                    return "onSetInactive";
                case 4:
                    return "onAnswer";
                case 5:
                    return "onDisconnect";
                case 6:
                    return "onCallStreamingStarted";
                case 7:
                    return "onCallStreamingFailed";
                case 8:
                    return "onCallEndpointChanged";
                case 9:
                    return "onAvailableCallEndpointsChanged";
                case 10:
                    return "onMuteStateChanged";
                case 11:
                    return "onVideoStateChanged";
                case 12:
                    return "onEvent";
                case 13:
                    return "removeCallFromTransactionalServiceWrapper";
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
                parcel.enforceInterface(ICallEventCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallEventCallback.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    ICallControl asInterface = ICallControl.Stub.asInterface(parcel.readStrongBinder());
                    CallException callException = (CallException) parcel.readTypedObject(CallException.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAddCallControl(readString, readInt, asInterface, callException);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetActive(readString2, resultReceiver);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetInactive(readString3, resultReceiver2);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    ResultReceiver resultReceiver3 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAnswer(readString4, readInt2, resultReceiver3);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    DisconnectCause disconnectCause = (DisconnectCause) parcel.readTypedObject(DisconnectCause.CREATOR);
                    ResultReceiver resultReceiver4 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisconnect(readString5, disconnectCause, resultReceiver4);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    ResultReceiver resultReceiver5 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallStreamingStarted(readString6, resultReceiver5);
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallStreamingFailed(readString7, readInt3);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEndpointChanged(readString8, callEndpoint);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAvailableCallEndpointsChanged(readString9, createTypedArrayList);
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onMuteStateChanged(readString10, readBoolean);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoStateChanged(readString11, readInt4);
                    return true;
                case 12:
                    String readString12 = parcel.readString();
                    String readString13 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEvent(readString12, readString13, bundle);
                    return true;
                case 13:
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCallFromTransactionalServiceWrapper(readString14);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ICallEventCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallEventCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onAddCallControl(String str, int i, ICallControl iCallControl, CallException callException) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeStrongInterface(iCallControl);
                    obtain.writeTypedObject(callException, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onSetActive(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onSetInactive(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onAnswer(String str, int i, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onDisconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(disconnectCause, 0);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallStreamingStarted(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallStreamingFailed(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallEndpointChanged(String str, CallEndpoint callEndpoint) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(callEndpoint, 0);
                    this.mRemote.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onMuteStateChanged(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onVideoStateChanged(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onEvent(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void removeCallFromTransactionalServiceWrapper(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
