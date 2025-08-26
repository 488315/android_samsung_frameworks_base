package com.samsung.android.mcf.autohotspot;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISemAutohotspotMcf extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf";

    public static class Default implements ISemAutohotspotMcf {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int genericCommand(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int getMcfConnectedStatus(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int getMcfConnectedStatusFromScanResult(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public List<String> getMcfScanDetail() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int startMcfClientMHSDiscovery(boolean z) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
        public int startMcfMHSAdvertisement(boolean z) throws RemoteException {
            return 0;
        }
    }

    int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4) throws RemoteException;

    int genericCommand(String str) throws RemoteException;

    int getMcfConnectedStatus(String str) throws RemoteException;

    int getMcfConnectedStatusFromScanResult(String str) throws RemoteException;

    List<String> getMcfScanDetail() throws RemoteException;

    int startMcfClientMHSDiscovery(boolean z) throws RemoteException;

    int startMcfMHSAdvertisement(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemAutohotspotMcf {
        static final int TRANSACTION_connectToMcfMHS = 3;
        static final int TRANSACTION_genericCommand = 6;
        static final int TRANSACTION_getMcfConnectedStatus = 4;
        static final int TRANSACTION_getMcfConnectedStatusFromScanResult = 5;
        static final int TRANSACTION_getMcfScanDetail = 1;
        static final int TRANSACTION_startMcfClientMHSDiscovery = 2;
        static final int TRANSACTION_startMcfMHSAdvertisement = 7;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 6;
        }

        public Stub() {
            attachInterface(this, ISemAutohotspotMcf.DESCRIPTOR);
        }

        public static ISemAutohotspotMcf asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemAutohotspotMcf.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemAutohotspotMcf)) {
                return (ISemAutohotspotMcf) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "getMcfScanDetail";
                case 2:
                    return "startMcfClientMHSDiscovery";
                case 3:
                    return "connectToMcfMHS";
                case 4:
                    return "getMcfConnectedStatus";
                case 5:
                    return "getMcfConnectedStatusFromScanResult";
                case 6:
                    return "genericCommand";
                case 7:
                    return "startMcfMHSAdvertisement";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemAutohotspotMcf.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemAutohotspotMcf.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    List<String> mcfScanDetail = getMcfScanDetail();
                    parcel2.writeNoException();
                    parcel2.writeStringList(mcfScanDetail);
                    return true;
                case 2:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iStartMcfClientMHSDiscovery = startMcfClientMHSDiscovery(z);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartMcfClientMHSDiscovery);
                    return true;
                case 3:
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iConnectToMcfMHS = connectToMcfMHS(string, i3, i4, i5, string2, string3, i6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iConnectToMcfMHS);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mcfConnectedStatus = getMcfConnectedStatus(string4);
                    parcel2.writeNoException();
                    parcel2.writeInt(mcfConnectedStatus);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mcfConnectedStatusFromScanResult = getMcfConnectedStatusFromScanResult(string5);
                    parcel2.writeNoException();
                    parcel2.writeInt(mcfConnectedStatusFromScanResult);
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iGenericCommand = genericCommand(string6);
                    parcel2.writeNoException();
                    parcel2.writeInt(iGenericCommand);
                    return true;
                case 7:
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int iStartMcfMHSAdvertisement = startMcfMHSAdvertisement(z2);
                    parcel2.writeNoException();
                    parcel2.writeInt(iStartMcfMHSAdvertisement);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemAutohotspotMcf {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemAutohotspotMcf.DESCRIPTOR;
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public List<String> getMcfScanDetail() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int startMcfClientMHSDiscovery(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i4);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int getMcfConnectedStatus(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int getMcfConnectedStatusFromScanResult(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int genericCommand(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int startMcfMHSAdvertisement(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
