package android.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICellInfoCallback extends IInterface {
    public static final String DESCRIPTOR = "android.telephony.ICellInfoCallback";

    public static class Default implements ICellInfoCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.telephony.ICellInfoCallback
        public void onCellInfo(List<CellInfo> list) throws RemoteException {
        }

        @Override // android.telephony.ICellInfoCallback
        public void onError(int i, String str, String str2) throws RemoteException {
        }
    }

    void onCellInfo(List<CellInfo> list) throws RemoteException;

    void onError(int i, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements ICellInfoCallback {
        static final int TRANSACTION_onCellInfo = 1;
        static final int TRANSACTION_onError = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ICellInfoCallback.DESCRIPTOR);
        }

        public static ICellInfoCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICellInfoCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICellInfoCallback)) {
                return (ICellInfoCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCellInfo";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICellInfoCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICellInfoCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList createTypedArrayList = parcel.createTypedArrayList(CellInfo.CREATOR);
                parcel.enforceNoDataAvail();
                onCellInfo(createTypedArrayList);
            } else if (i == 2) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readInt, readString, readString2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICellInfoCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICellInfoCallback.DESCRIPTOR;
            }

            @Override // android.telephony.ICellInfoCallback
            public void onCellInfo(List<CellInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICellInfoCallback.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.telephony.ICellInfoCallback
            public void onError(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICellInfoCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
