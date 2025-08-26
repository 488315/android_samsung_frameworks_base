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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISmartFaceClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISmartFaceClient)) {
                return (ISmartFaceClient) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                FaceInfo faceInfo = (FaceInfo) parcel.readTypedObject(FaceInfo.CREATOR);
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onInfo(i3, faceInfo, i4);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISmartFaceClient.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(faceInfo, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
