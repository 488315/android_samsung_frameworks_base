package com.android.internal.telephony;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface ICarrierPrivilegesCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.telephony.ICarrierPrivilegesCallback";

    public static class Default implements ICarrierPrivilegesCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierPrivilegesChanged(List<String> list, int[] iArr) throws RemoteException {
        }

        @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
        public void onCarrierServiceChanged(String str, int i) throws RemoteException {
        }
    }

    void onCarrierPrivilegesChanged(List<String> list, int[] iArr) throws RemoteException;

    void onCarrierServiceChanged(String str, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ICarrierPrivilegesCallback {
        static final int TRANSACTION_onCarrierPrivilegesChanged = 1;
        static final int TRANSACTION_onCarrierServiceChanged = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ICarrierPrivilegesCallback.DESCRIPTOR);
        }

        public static ICarrierPrivilegesCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICarrierPrivilegesCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICarrierPrivilegesCallback)) {
                return (ICarrierPrivilegesCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onCarrierPrivilegesChanged";
            }
            if (i != 2) {
                return null;
            }
            return "onCarrierServiceChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICarrierPrivilegesCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICarrierPrivilegesCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onCarrierPrivilegesChanged(createStringArrayList, createIntArray);
            } else if (i == 2) {
                String readString = parcel.readString();
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCarrierServiceChanged(readString, readInt);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICarrierPrivilegesCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICarrierPrivilegesCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
            public void onCarrierPrivilegesChanged(List<String> list, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICarrierPrivilegesCallback.DESCRIPTOR);
                    obtain.writeStringList(list);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
            public void onCarrierServiceChanged(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICarrierPrivilegesCallback.DESCRIPTOR);
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
