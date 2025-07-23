package com.samsung.android.smartface;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISmartFaceClient extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.smartface.ISmartFaceClient";

    public static class Default implements ISmartFaceClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.smartface.ISmartFaceClient
        public void onInfo(int i, FaceInfo faceInfo, int i2) throws RemoteException {
        }
    }

    void onInfo(int i, FaceInfo faceInfo, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISmartFaceClient {
        static final int TRANSACTION_onInfo = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISmartFaceClient.DESCRIPTOR);
        }

        public static ISmartFaceClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISmartFaceClient.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISmartFaceClient)) {
                return (ISmartFaceClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onInfo";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISmartFaceClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISmartFaceClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                FaceInfo faceInfo = (FaceInfo) parcel.readTypedObject(FaceInfo.CREATOR);
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onInfo(readInt, faceInfo, readInt2);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISmartFaceClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISmartFaceClient.DESCRIPTOR;
            }

            @Override // com.samsung.android.smartface.ISmartFaceClient
            public void onInfo(int i, FaceInfo faceInfo, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISmartFaceClient.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(faceInfo, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
