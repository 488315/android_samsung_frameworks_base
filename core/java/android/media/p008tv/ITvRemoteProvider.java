package android.media.p008tv;

import android.media.p008tv.ITvRemoteServiceInput;
import android.p009os.Binder;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.RemoteException;

/* loaded from: classes2.dex */
public interface ITvRemoteProvider extends IInterface {
    void onInputBridgeConnected(IBinder iBinder) throws RemoteException;

    void setRemoteServiceInputSink(ITvRemoteServiceInput iTvRemoteServiceInput) throws RemoteException;

    public static class Default implements ITvRemoteProvider {
        @Override // android.media.p008tv.ITvRemoteProvider
        public void setRemoteServiceInputSink(ITvRemoteServiceInput tvServiceInput) throws RemoteException {
        }

        @Override // android.media.p008tv.ITvRemoteProvider
        public void onInputBridgeConnected(IBinder token) throws RemoteException {
        }

        @Override // android.p009os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ITvRemoteProvider {
        public static final String DESCRIPTOR = "android.media.tv.ITvRemoteProvider";
        static final int TRANSACTION_onInputBridgeConnected = 2;
        static final int TRANSACTION_setRemoteServiceInputSink = 1;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvRemoteProvider asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof ITvRemoteProvider)) {
                return (ITvRemoteProvider) iin;
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
                    return "setRemoteServiceInputSink";
                case 2:
                    return "onInputBridgeConnected";
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
                            IBinder _arg0 = data.readStrongBinder();
                            ITvRemoteServiceInput _arg02 = ITvRemoteServiceInput.Stub.asInterface(_arg0);
                            data.enforceNoDataAvail();
                            setRemoteServiceInputSink(_arg02);
                            return true;
                        case 2:
                            IBinder _arg03 = data.readStrongBinder();
                            data.enforceNoDataAvail();
                            onInputBridgeConnected(_arg03);
                            return true;
                        default:
                            return super.onTransact(code, data, reply, flags);
                    }
            }
        }

        private static class Proxy implements ITvRemoteProvider {
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

            @Override // android.media.p008tv.ITvRemoteProvider
            public void setRemoteServiceInputSink(ITvRemoteServiceInput tvServiceInput) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(tvServiceInput);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.media.p008tv.ITvRemoteProvider
            public void onInputBridgeConnected(IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.p009os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
