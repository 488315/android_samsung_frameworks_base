package com.samsung.android.knox.mtd;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface IMtdCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.mtd.IMtdCallback";

    public static class Default implements IMtdCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.mtd.IMtdCallback
        public void onFinished(List<AnalysisResult> list) throws RemoteException {
        }
    }

    void onFinished(List<AnalysisResult> list) throws RemoteException;

    public static abstract class Stub extends Binder implements IMtdCallback {
        static final int TRANSACTION_onFinished = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IMtdCallback.DESCRIPTOR);
        }

        public static IMtdCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IMtdCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IMtdCallback)) {
                return (IMtdCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onFinished";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IMtdCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IMtdCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(AnalysisResult.CREATOR);
                parcel.enforceNoDataAvail();
                onFinished(createTypedArrayList);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IMtdCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMtdCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.mtd.IMtdCallback
            public void onFinished(List<AnalysisResult> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IMtdCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
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
