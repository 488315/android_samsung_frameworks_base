package com.samsung.android.wifi.intelligence.icc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIccTrService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.intelligence.icc.IIccTrService";

    public static class Default implements IIccTrService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.intelligence.icc.IIccTrService
        public void initialize(String str, int i, int i2, int i3, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.intelligence.icc.IIccTrService
        public void trainAll() throws RemoteException {
        }

        @Override // com.samsung.android.wifi.intelligence.icc.IIccTrService
        public void trainKey(String str) throws RemoteException {
        }
    }

    void initialize(String str, int i, int i2, int i3, boolean z) throws RemoteException;

    void trainAll() throws RemoteException;

    void trainKey(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IIccTrService {
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_trainAll = 3;
        static final int TRANSACTION_trainKey = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }

        public Stub() {
            attachInterface(this, IIccTrService.DESCRIPTOR);
        }

        public static IIccTrService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIccTrService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIccTrService)) {
                return (IIccTrService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "initialize";
            }
            if (i == 2) {
                return "trainKey";
            }
            if (i != 3) {
                return null;
            }
            return "trainAll";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIccTrService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIccTrService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String string = parcel.readString();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                boolean z = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                initialize(string, i3, i4, i5, z);
            } else if (i == 2) {
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                trainKey(string2);
            } else if (i == 3) {
                trainAll();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIccTrService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIccTrService.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.intelligence.icc.IIccTrService
            public void initialize(String str, int i, int i2, int i3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIccTrService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.icc.IIccTrService
            public void trainKey(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIccTrService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.intelligence.icc.IIccTrService
            public void trainAll() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IIccTrService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
