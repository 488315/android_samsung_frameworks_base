package android.media.tv;

import android.media.tv.ITvRemoteServiceInput;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ITvRemoteProvider extends IInterface {

    public static class Default implements ITvRemoteProvider {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.media.tv.ITvRemoteProvider
        public void onInputBridgeConnected(IBinder iBinder) throws RemoteException {
        }

        @Override // android.media.tv.ITvRemoteProvider
        public void setRemoteServiceInputSink(ITvRemoteServiceInput iTvRemoteServiceInput) throws RemoteException {
        }
    }

    void onInputBridgeConnected(IBinder iBinder) throws RemoteException;

    void setRemoteServiceInputSink(ITvRemoteServiceInput iTvRemoteServiceInput) throws RemoteException;

    public static abstract class Stub extends Binder implements ITvRemoteProvider {
        public static final String DESCRIPTOR = "android.media.tv.ITvRemoteProvider";
        static final int TRANSACTION_onInputBridgeConnected = 2;
        static final int TRANSACTION_setRemoteServiceInputSink = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITvRemoteProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ITvRemoteProvider)) {
                return (ITvRemoteProvider) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "setRemoteServiceInputSink";
            }
            if (i != 2) {
                return null;
            }
            return "onInputBridgeConnected";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ITvRemoteServiceInput asInterface = ITvRemoteServiceInput.Stub.asInterface(parcel.readStrongBinder());
                parcel.enforceNoDataAvail();
                setRemoteServiceInputSink(asInterface);
            } else if (i == 2) {
                IBinder readStrongBinder = parcel.readStrongBinder();
                parcel.enforceNoDataAvail();
                onInputBridgeConnected(readStrongBinder);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ITvRemoteProvider {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.media.tv.ITvRemoteProvider
            public void setRemoteServiceInputSink(ITvRemoteServiceInput iTvRemoteServiceInput) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iTvRemoteServiceInput);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.media.tv.ITvRemoteProvider
            public void onInputBridgeConnected(IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
