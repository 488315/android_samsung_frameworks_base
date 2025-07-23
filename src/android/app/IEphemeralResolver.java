package android.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IEphemeralResolver extends IInterface {

    public static class Default implements IEphemeralResolver {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IEphemeralResolver
        public void getEphemeralIntentFilterList(IRemoteCallback iRemoteCallback, String str, int i) throws RemoteException {
        }

        @Override // android.app.IEphemeralResolver
        public void getEphemeralResolveInfoList(IRemoteCallback iRemoteCallback, int[] iArr, int i) throws RemoteException {
        }
    }

    void getEphemeralIntentFilterList(IRemoteCallback iRemoteCallback, String str, int i) throws RemoteException;

    void getEphemeralResolveInfoList(IRemoteCallback iRemoteCallback, int[] iArr, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IEphemeralResolver {
        public static final String DESCRIPTOR = "android.app.IEphemeralResolver";
        static final int TRANSACTION_getEphemeralIntentFilterList = 2;
        static final int TRANSACTION_getEphemeralResolveInfoList = 1;

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

        public static IEphemeralResolver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IEphemeralResolver)) {
                return (IEphemeralResolver) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "getEphemeralResolveInfoList";
            }
            if (i != 2) {
                return null;
            }
            return "getEphemeralIntentFilterList";
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
                IRemoteCallback asInterface = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                int[] createIntArray = parcel.createIntArray();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                getEphemeralResolveInfoList(asInterface, createIntArray, readInt);
            } else if (i == 2) {
                IRemoteCallback asInterface2 = IRemoteCallback.Stub.asInterface(parcel.readStrongBinder());
                String readString = parcel.readString();
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                getEphemeralIntentFilterList(asInterface2, readString, readInt2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IEphemeralResolver {
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

            @Override // android.app.IEphemeralResolver
            public void getEphemeralResolveInfoList(IRemoteCallback iRemoteCallback, int[] iArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeIntArray(iArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.IEphemeralResolver
            public void getEphemeralIntentFilterList(IRemoteCallback iRemoteCallback, String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iRemoteCallback);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
