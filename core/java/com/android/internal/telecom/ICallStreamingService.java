package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.StreamingCall;
import com.android.internal.telecom.IStreamingCallAdapter;

/* loaded from: classes5.dex */
public interface ICallStreamingService extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telecom.ICallStreamingService";

    void onCallStreamingStarted(StreamingCall streamingCall) throws RemoteException;

    void onCallStreamingStateChanged(int i) throws RemoteException;

    void onCallStreamingStopped() throws RemoteException;

    void setStreamingCallAdapter(IStreamingCallAdapter iStreamingCallAdapter) throws RemoteException;

    public static class Default implements ICallStreamingService {
        @Override // com.android.internal.telecom.ICallStreamingService
        public void setStreamingCallAdapter(IStreamingCallAdapter streamingCallAdapter) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStarted(StreamingCall call) throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStopped() throws RemoteException {
        }

        @Override // com.android.internal.telecom.ICallStreamingService
        public void onCallStreamingStateChanged(int state) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICallStreamingService {
        static final int TRANSACTION_onCallStreamingStarted = 2;
        static final int TRANSACTION_onCallStreamingStateChanged = 4;
        static final int TRANSACTION_onCallStreamingStopped = 3;
        static final int TRANSACTION_setStreamingCallAdapter = 1;

        public Stub() {
            attachInterface(this, ICallStreamingService.DESCRIPTOR);
        }

        public static ICallStreamingService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICallStreamingService.DESCRIPTOR);
            if (iin != null && (iin instanceof ICallStreamingService)) {
                return (ICallStreamingService) iin;
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
                    return "setStreamingCallAdapter";
                case 2:
                    return "onCallStreamingStarted";
                case 3:
                    return "onCallStreamingStopped";
                case 4:
                    return "onCallStreamingStateChanged";
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
                data.enforceInterface(ICallStreamingService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICallStreamingService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IStreamingCallAdapter _arg0 = IStreamingCallAdapter.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    setStreamingCallAdapter(_arg0);
                    return true;
                case 2:
                    StreamingCall _arg02 = (StreamingCall) data.readTypedObject(StreamingCall.CREATOR);
                    data.enforceNoDataAvail();
                    onCallStreamingStarted(_arg02);
                    return true;
                case 3:
                    onCallStreamingStopped();
                    return true;
                case 4:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    onCallStreamingStateChanged(_arg03);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICallStreamingService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICallStreamingService.DESCRIPTOR;
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void setStreamingCallAdapter(IStreamingCallAdapter streamingCallAdapter) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    _data.writeStrongInterface(streamingCallAdapter);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStarted(StreamingCall call) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    _data.writeTypedObject(call, 0);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStopped() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.android.internal.telecom.ICallStreamingService
            public void onCallStreamingStateChanged(int state) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICallStreamingService.DESCRIPTOR);
                    _data.writeInt(state);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
