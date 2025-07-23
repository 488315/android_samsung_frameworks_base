package com.sec.android.iaft;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IIAFDService extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.iaft.IIAFDService";

    public static class Default implements IIAFDService {
        @Override // com.sec.android.iaft.IIAFDService
        public boolean IAFDParse(String str, String str2, int i, int i2, int i3, String str3, String str4, String str5) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.iaft.IIAFDService
        public void IAFDShow(int i, int i2, String str) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    boolean IAFDParse(String str, String str2, int i, int i2, int i3, String str3, String str4, String str5) throws RemoteException;

    void IAFDShow(int i, int i2, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IIAFDService {
        static final int TRANSACTION_IAFDParse = 1;
        static final int TRANSACTION_IAFDShow = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IIAFDService.DESCRIPTOR);
        }

        public static IIAFDService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IIAFDService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IIAFDService)) {
                return (IIAFDService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "IAFDParse";
            }
            if (i != 2) {
                return null;
            }
            return "IAFDShow";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IIAFDService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IIAFDService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                int readInt = parcel.readInt();
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean IAFDParse = IAFDParse(readString, readString2, readInt, readInt2, readInt3, readString3, readString4, readString5);
                parcel2.writeNoException();
                parcel2.writeBoolean(IAFDParse);
            } else if (i == 2) {
                int readInt4 = parcel.readInt();
                int readInt5 = parcel.readInt();
                String readString6 = parcel.readString();
                parcel.enforceNoDataAvail();
                IAFDShow(readInt4, readInt5, readString6);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IIAFDService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IIAFDService.DESCRIPTOR;
            }

            @Override // com.sec.android.iaft.IIAFDService
            public boolean IAFDParse(String str, String str2, int i, int i2, int i3, String str3, String str4, String str5) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIAFDService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFDService
            public void IAFDShow(int i, int i2, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IIAFDService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
