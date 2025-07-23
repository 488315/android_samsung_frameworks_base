package android.content.pm.dex;

import android.content.pm.dex.ISnapshotRuntimeProfileCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IArtManager extends IInterface {

    public static class Default implements IArtManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.content.pm.dex.IArtManager
        public boolean isRuntimeProfilingEnabled(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.content.pm.dex.IArtManager
        public void snapshotRuntimeProfile(int i, String str, String str2, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, String str3) throws RemoteException {
        }
    }

    boolean isRuntimeProfilingEnabled(int i, String str) throws RemoteException;

    void snapshotRuntimeProfile(int i, String str, String str2, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, String str3) throws RemoteException;

    public static abstract class Stub extends Binder implements IArtManager {
        public static final String DESCRIPTOR = "android.content.pm.dex.IArtManager";
        static final int TRANSACTION_isRuntimeProfilingEnabled = 2;
        static final int TRANSACTION_snapshotRuntimeProfile = 1;

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

        public static IArtManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IArtManager)) {
                return (IArtManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "snapshotRuntimeProfile";
            }
            if (i != 2) {
                return null;
            }
            return "isRuntimeProfilingEnabled";
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
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                ISnapshotRuntimeProfileCallback asInterface = ISnapshotRuntimeProfileCallback.Stub.asInterface(parcel.readStrongBinder());
                String readString3 = parcel.readString();
                parcel.enforceNoDataAvail();
                snapshotRuntimeProfile(readInt, readString, readString2, asInterface, readString3);
                parcel2.writeNoException();
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                String readString4 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean isRuntimeProfilingEnabled = isRuntimeProfilingEnabled(readInt2, readString4);
                parcel2.writeNoException();
                parcel2.writeBoolean(isRuntimeProfilingEnabled);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IArtManager {
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

            @Override // android.content.pm.dex.IArtManager
            public void snapshotRuntimeProfile(int i, String str, String str2, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iSnapshotRuntimeProfileCallback);
                    obtain.writeString(str3);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.content.pm.dex.IArtManager
            public boolean isRuntimeProfilingEnabled(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
