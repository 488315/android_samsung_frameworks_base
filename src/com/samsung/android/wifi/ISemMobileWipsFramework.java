package com.samsung.android.wifi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemMobileWipsFramework extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.wifi.ISemMobileWipsFramework";

    public static class Default implements ISemMobileWipsFramework {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsFramework
        public List<SemMobileWipsScanResult> getScanResults() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsFramework
        public boolean invokeMethodBool(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsFramework
        public String invokeMethodStr(int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsFramework
        public void partialScanStart(Message message) throws RemoteException {
        }

        @Override // com.samsung.android.wifi.ISemMobileWipsFramework
        public void sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException {
        }
    }

    List<SemMobileWipsScanResult> getScanResults() throws RemoteException;

    boolean invokeMethodBool(int i) throws RemoteException;

    String invokeMethodStr(int i) throws RemoteException;

    void partialScanStart(Message message) throws RemoteException;

    void sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemMobileWipsFramework {
        static final int TRANSACTION_getScanResults = 4;
        static final int TRANSACTION_invokeMethodBool = 1;
        static final int TRANSACTION_invokeMethodStr = 2;
        static final int TRANSACTION_partialScanStart = 3;
        static final int TRANSACTION_sendHWParamToHQMwithAppId = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, ISemMobileWipsFramework.DESCRIPTOR);
        }

        public static ISemMobileWipsFramework asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemMobileWipsFramework.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemMobileWipsFramework)) {
                return (ISemMobileWipsFramework) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "invokeMethodBool";
            }
            if (i == 2) {
                return "invokeMethodStr";
            }
            if (i == 3) {
                return "partialScanStart";
            }
            if (i == 4) {
                return "getScanResults";
            }
            if (i != 5) {
                return null;
            }
            return "sendHWParamToHQMwithAppId";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemMobileWipsFramework.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemMobileWipsFramework.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean invokeMethodBool = invokeMethodBool(readInt);
                parcel2.writeNoException();
                parcel2.writeBoolean(invokeMethodBool);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String invokeMethodStr = invokeMethodStr(readInt2);
                parcel2.writeNoException();
                parcel2.writeString(invokeMethodStr);
            } else if (i == 3) {
                Message message = (Message) parcel.readTypedObject(Message.CREATOR);
                parcel.enforceNoDataAvail();
                partialScanStart(message);
                parcel2.writeNoException();
            } else if (i == 4) {
                List<SemMobileWipsScanResult> scanResults = getScanResults();
                parcel2.writeNoException();
                parcel2.writeTypedList(scanResults, 1);
            } else if (i == 5) {
                int readInt3 = parcel.readInt();
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                String readString3 = parcel.readString();
                String readString4 = parcel.readString();
                String readString5 = parcel.readString();
                String readString6 = parcel.readString();
                String readString7 = parcel.readString();
                String readString8 = parcel.readString();
                String readString9 = parcel.readString();
                parcel.enforceNoDataAvail();
                sendHWParamToHQMwithAppId(readInt3, readString, readString2, readString3, readString4, readString5, readString6, readString7, readString8, readString9);
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISemMobileWipsFramework {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemMobileWipsFramework.DESCRIPTOR;
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public boolean invokeMethodBool(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public String invokeMethodStr(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public void partialScanStart(Message message) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    obtain.writeTypedObject(message, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public List<SemMobileWipsScanResult> getScanResults() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemMobileWipsScanResult.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public void sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeString(str8);
                    obtain.writeString(str9);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
