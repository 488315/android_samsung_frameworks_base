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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IIAFDService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IIAFDService)) {
                return (IIAFDService) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                String string2 = parcel.readString();
                int i3 = parcel.readInt();
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                String string5 = parcel.readString();
                parcel.enforceNoDataAvail();
                boolean zIAFDParse = IAFDParse(string, string2, i3, i4, i5, string3, string4, string5);
                parcel2.writeNoException();
                parcel2.writeBoolean(zIAFDParse);
            } else if (i == 2) {
                int i6 = parcel.readInt();
                int i7 = parcel.readInt();
                String string6 = parcel.readString();
                parcel.enforceNoDataAvail();
                IAFDShow(i6, i7, string6);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIAFDService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.iaft.IIAFDService
            public void IAFDShow(int i, int i2, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IIAFDService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
