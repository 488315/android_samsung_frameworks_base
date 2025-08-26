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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICallEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICallEventCallback)) {
                return (ICallEventCallback) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    ICallControl iCallControlAsInterface = ICallControl.Stub.asInterface(parcel.readStrongBinder());
                    CallException callException = (CallException) parcel.readTypedObject(CallException.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAddCallControl(string, i3, iCallControlAsInterface, callException);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    ResultReceiver resultReceiver = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetActive(string2, resultReceiver);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    ResultReceiver resultReceiver2 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onSetInactive(string3, resultReceiver2);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i4 = parcel.readInt();
                    ResultReceiver resultReceiver3 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAnswer(string4, i4, resultReceiver3);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    DisconnectCause disconnectCause = (DisconnectCause) parcel.readTypedObject(DisconnectCause.CREATOR);
                    ResultReceiver resultReceiver4 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onDisconnect(string5, disconnectCause, resultReceiver4);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    ResultReceiver resultReceiver5 = (ResultReceiver) parcel.readTypedObject(ResultReceiver.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallStreamingStarted(string6, resultReceiver5);
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onCallStreamingFailed(string7, i5);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    CallEndpoint callEndpoint = (CallEndpoint) parcel.readTypedObject(CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onCallEndpointChanged(string8, callEndpoint);
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(CallEndpoint.CREATOR);
                    parcel.enforceNoDataAvail();
                    onAvailableCallEndpointsChanged(string9, arrayListCreateTypedArrayList);
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    onMuteStateChanged(string10, z);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVideoStateChanged(string11, i6);
                    return true;
                case 12:
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    onEvent(string12, string13, bundle);
                    return true;
                case 13:
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeCallFromTransactionalServiceWrapper(string14);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongInterface(iCallControl);
                    parcelObtain.writeTypedObject(callException, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onSetActive(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onSetInactive(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onAnswer(String str, int i, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onDisconnect(String str, DisconnectCause disconnectCause, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(disconnectCause, 0);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallStreamingStarted(String str, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(resultReceiver, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallStreamingFailed(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onCallEndpointChanged(String str, CallEndpoint callEndpoint) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(callEndpoint, 0);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onAvailableCallEndpointsChanged(String str, List<CallEndpoint> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onMuteStateChanged(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onVideoStateChanged(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void onEvent(String str, String str2, Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallEventCallback
            public void removeCallFromTransactionalServiceWrapper(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
