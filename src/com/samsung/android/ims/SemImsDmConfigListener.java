package com.samsung.android.ims;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface SemImsDmConfigListener extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.ims.SemImsDmConfigListener";

    public static class Default implements SemImsDmConfigListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.ims.SemImsDmConfigListener
        public void onChangeDmValue(String str, boolean z) throws RemoteException {
        }
    }

    void onChangeDmValue(String str, boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements SemImsDmConfigListener {
        static final int TRANSACTION_onChangeDmValue = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, SemImsDmConfigListener.DESCRIPTOR);
        }

        public static SemImsDmConfigListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(SemImsDmConfigListener.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof SemImsDmConfigListener)) {
                return (SemImsDmConfigListener) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onChangeDmValue";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(SemImsDmConfigListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(SemImsDmConfigListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onChangeDmValue(readString, readBoolean);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements SemImsDmConfigListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return SemImsDmConfigListener.DESCRIPTOR;
            }

            @Override // com.samsung.android.ims.SemImsDmConfigListener
            public void onChangeDmValue(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(SemImsDmConfigListener.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
