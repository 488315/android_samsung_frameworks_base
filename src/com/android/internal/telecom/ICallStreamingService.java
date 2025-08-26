package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.StreamingCall;
import com.android.internal.telecom.IStreamingCallAdapter;

/* loaded from: classes4.dex */
public interface ICallStreamingService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallStreamingService";

    public static class Default implements ICallStreamingService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStarted(StreamingCall streamingCall) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStateChanged(int i) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStopped() throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void setStreamingCallAdapter(IStreamingCallAdapter iStreamingCallAdapter) throws RemoteException {
        }
    }

    void onCallStreamingStarted(StreamingCall streamingCall) throws RemoteException;

    void onCallStreamingStateChanged(int i) throws RemoteException;

    void onCallStreamingStopped() throws RemoteException;

    void setStreamingCallAdapter(IStreamingCallAdapter iStreamingCallAdapter) throws RemoteException;

    public static abstract class Stub extends Binder implements ICallStreamingService {
        static final int TRANSACTION_onCallStreamingStarted = 2;
        static final int TRANSACTION_onCallStreamingStateChanged = 4;
        static final int TRANSACTION_onCallStreamingStopped = 3;
        static final int TRANSACTION_setStreamingCallAdapter = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }

        public Stub() {
            attachInterface(this, ICallStreamingService.DESCRIPTOR);
        }

        public static ICallStreamingService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICallStreamingService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICallStreamingService)) {
                return (ICallStreamingService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setStreamingCallAdapter";
            }
            if (i == 2) {
                return "onCallStreamingStarted";
            }
            if (i == 3) {
                return "onCallStreamingStopped";
            }
            if (i != 4) {
                return null;
            }
            return "onCallStreamingStateChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICallStreamingService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICallStreamingService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                IStreamingCallAdapter iStreamingCallAdapterAsInterface = IStreamingCallAdapter.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setStreamingCallAdapter(iStreamingCallAdapterAsInterface);
            } else if (i == 2) {
                StreamingCall streamingCall = (StreamingCall) parcel.readTypedObject(StreamingCall.CREATOR);
                parcel.enforceNoDataAvail();
                onCallStreamingStarted(streamingCall);
            } else if (i == 3) {
                onCallStreamingStopped();
            } else if (i == 4) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCallStreamingStateChanged(i3);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICallStreamingService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallStreamingService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void setStreamingCallAdapter(IStreamingCallAdapter iStreamingCallAdapter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iStreamingCallAdapter);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStarted(StreamingCall streamingCall) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(streamingCall, 0);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStopped() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
