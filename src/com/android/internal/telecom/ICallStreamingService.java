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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICallStreamingService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICallStreamingService)) {
                return (ICallStreamingService) queryLocalInterface;
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
                IStreamingCallAdapter asInterface = IStreamingCallAdapter.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setStreamingCallAdapter(asInterface);
            } else if (i == 2) {
                StreamingCall streamingCall = (StreamingCall) parcel.readTypedObject(StreamingCall.CREATOR);
                parcel.enforceNoDataAvail();
                onCallStreamingStarted(streamingCall);
            } else if (i == 3) {
                onCallStreamingStopped();
            } else if (i == 4) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCallStreamingStateChanged(readInt);
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
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    obtain.writeStrongInterface(iStreamingCallAdapter);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStarted(StreamingCall streamingCall) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    obtain.writeTypedObject(streamingCall, 0);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStopped() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStateChanged(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
