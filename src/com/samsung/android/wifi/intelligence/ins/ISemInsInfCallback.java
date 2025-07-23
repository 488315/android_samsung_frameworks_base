package com.samsung.android.wifi.intelligence.ins;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.wifi.intelligence.ins.inf.entity.ResultInfer;

/* loaded from: classes6.dex */
public interface ISemInsInfCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.ins.ISemInsInfCallback";

    public static class Default implements ISemInsInfCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.intelligence.ins.ISemInsInfCallback
        public void onResultInfer(ResultInfer resultInfer) throws RemoteException {
        }
    }

    void onResultInfer(ResultInfer resultInfer) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemInsInfCallback {
        static final int TRANSACTION_onResultInfer = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemInsInfCallback.DESCRIPTOR);
        }

        public static ISemInsInfCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemInsInfCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemInsInfCallback)) {
                return (ISemInsInfCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onResultInfer";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemInsInfCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemInsInfCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ResultInfer resultInfer = (ResultInfer) parcel.readTypedObject(ResultInfer.CREATOR);
                parcel.enforceNoDataAvail();
                onResultInfer(resultInfer);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemInsInfCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemInsInfCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.intelligence.ins.ISemInsInfCallback
            public void onResultInfer(ResultInfer resultInfer) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemInsInfCallback.DESCRIPTOR);
                    obtain.writeTypedObject(resultInfer, 0);
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
