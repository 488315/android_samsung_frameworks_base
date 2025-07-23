package com.samsung.android.knox.seams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface ISEAMS extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.seams.ISEAMS";

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Default implements ISEAMS {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.seams.ISEAMS
        public int isAuthorized(int i, int i2, String str, String str2) throws RemoteException {
            return 0;
        }
    }

    int isAuthorized(int i, int i2, String str, String str2) throws RemoteException;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class Stub extends Binder implements ISEAMS {
        public static final int TRANSACTION_isAuthorized = 1;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        class Proxy implements ISEAMS {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISEAMS.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.seams.ISEAMS
            public int isAuthorized(int i, int i2, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ISEAMS.DESCRIPTOR);
        }

        public static ISEAMS asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISEAMS.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof ISEAMS)) ? new Proxy(iBinder) : (ISEAMS) queryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "isAuthorized";
        }

        public int getMaxTransactionId() {
            return 0;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISEAMS.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISEAMS.DESCRIPTOR);
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            int readInt = parcel.readInt();
            int readInt2 = parcel.readInt();
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int isAuthorized = isAuthorized(readInt, readInt2, readString, readString2);
            parcel2.writeNoException();
            parcel2.writeInt(isAuthorized);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
