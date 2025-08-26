package com.samsung.android.sepunion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemExclusiveTaskManager extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.sepunion.ISemExclusiveTaskManager";

    public static class Default implements ISemExclusiveTaskManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.sepunion.ISemExclusiveTaskManager
        public List<String> getExclusiveTaskList(String str) throws RemoteException {
            return null;
        }
    }

    List<String> getExclusiveTaskList(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemExclusiveTaskManager {
        static final int TRANSACTION_getExclusiveTaskList = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISemExclusiveTaskManager.DESCRIPTOR);
        }

        public static ISemExclusiveTaskManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemExclusiveTaskManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemExclusiveTaskManager)) {
                return (ISemExclusiveTaskManager) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "getExclusiveTaskList";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemExclusiveTaskManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemExclusiveTaskManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                parcel.enforceNoDataAvail();
                List<String> exclusiveTaskList = getExclusiveTaskList(string);
                parcel2.writeNoException();
                parcel2.writeStringList(exclusiveTaskList);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemExclusiveTaskManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemExclusiveTaskManager.DESCRIPTOR;
            }

            @Override // com.samsung.android.sepunion.ISemExclusiveTaskManager
            public List<String> getExclusiveTaskList(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemExclusiveTaskManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
