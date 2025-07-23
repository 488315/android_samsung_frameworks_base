package com.android.internal.protolog;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IProtoLogClient extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.protolog.IProtoLogClient";

    public static class Default implements IProtoLogClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.protolog.IProtoLogClient
        public void toggleLogcat(boolean z, String[] strArr) throws RemoteException {
        }
    }

    void toggleLogcat(boolean z, String[] strArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IProtoLogClient {
        static final int TRANSACTION_toggleLogcat = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IProtoLogClient.DESCRIPTOR);
        }

        public static IProtoLogClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IProtoLogClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IProtoLogClient)) {
                return (IProtoLogClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "toggleLogcat";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IProtoLogClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IProtoLogClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                String[] createStringArray = parcel.createStringArray();
                parcel.enforceNoDataAvail();
                toggleLogcat(readBoolean, createStringArray);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IProtoLogClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IProtoLogClient.DESCRIPTOR;
            }

            @Override // com.android.internal.protolog.IProtoLogClient
            public void toggleLogcat(boolean z, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IProtoLogClient.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
