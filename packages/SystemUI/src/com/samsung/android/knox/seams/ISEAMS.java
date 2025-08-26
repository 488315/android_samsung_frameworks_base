package com.samsung.android.knox.seams;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface ISEAMS extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.seams.ISEAMS";

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

    public abstract class Stub extends Binder implements ISEAMS {
        public static final int TRANSACTION_isAuthorized = 1;

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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISEAMS.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISEAMS.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISEAMS)) ? new Proxy(iBinder) : (ISEAMS) iInterfaceQueryLocalInterface;
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
            int i3 = parcel.readInt();
            int i4 = parcel.readInt();
            String string = parcel.readString();
            String string2 = parcel.readString();
            parcel.enforceNoDataAvail();
            int iIsAuthorized = isAuthorized(i3, i4, string, string2);
            parcel2.writeNoException();
            parcel2.writeInt(iIsAuthorized);
            return true;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }
}
