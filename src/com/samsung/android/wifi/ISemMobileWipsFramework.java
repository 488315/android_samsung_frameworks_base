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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemMobileWipsFramework.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemMobileWipsFramework)) {
                return (ISemMobileWipsFramework) iInterfaceQueryLocalInterface;
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
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                boolean zInvokeMethodBool = invokeMethodBool(i3);
                parcel2.writeNoException();
                parcel2.writeBoolean(zInvokeMethodBool);
            } else if (i == 2) {
                int i4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                String strInvokeMethodStr = invokeMethodStr(i4);
                parcel2.writeNoException();
                parcel2.writeString(strInvokeMethodStr);
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
                int i5 = parcel.readInt();
                String string = parcel.readString();
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                String string4 = parcel.readString();
                String string5 = parcel.readString();
                String string6 = parcel.readString();
                String string7 = parcel.readString();
                String string8 = parcel.readString();
                String string9 = parcel.readString();
                parcel.enforceNoDataAvail();
                sendHWParamToHQMwithAppId(i5, string, string2, string3, string4, string5, string6, string7, string8, string9);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public String invokeMethodStr(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public void partialScanStart(Message message) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    parcelObtain.writeTypedObject(message, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public List<SemMobileWipsScanResult> getScanResults() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemMobileWipsScanResult.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.wifi.ISemMobileWipsFramework
            public void sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemMobileWipsFramework.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeString(str8);
                    parcelObtain.writeString(str9);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
