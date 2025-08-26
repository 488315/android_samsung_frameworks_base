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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICarrierPrivilegesCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICarrierPrivilegesCallback)) {
                return (ICarrierPrivilegesCallback) iInterfaceQueryLocalInterface;
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
                ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                int[] iArrCreateIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onCarrierPrivilegesChanged(arrayListCreateStringArrayList, iArrCreateIntArray);
            } else if (i == 2) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onCarrierServiceChanged(string, i3);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICarrierPrivilegesCallback.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.android.internal.telephony.ICarrierPrivilegesCallback
            public void onCarrierServiceChanged(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICarrierPrivilegesCallback.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
