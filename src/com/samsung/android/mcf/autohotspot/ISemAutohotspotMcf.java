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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemAutohotspotMcf.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemAutohotspotMcf)) {
                return (ISemAutohotspotMcf) queryLocalInterface;
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
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startMcfClientMHSDiscovery = startMcfClientMHSDiscovery(readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeInt(startMcfClientMHSDiscovery);
                    return true;
                case 3:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int connectToMcfMHS = connectToMcfMHS(readString, readInt, readInt2, readInt3, readString2, readString3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeInt(connectToMcfMHS);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mcfConnectedStatus = getMcfConnectedStatus(readString4);
                    parcel2.writeNoException();
                    parcel2.writeInt(mcfConnectedStatus);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int mcfConnectedStatusFromScanResult = getMcfConnectedStatusFromScanResult(readString5);
                    parcel2.writeNoException();
                    parcel2.writeInt(mcfConnectedStatusFromScanResult);
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int genericCommand = genericCommand(readString6);
                    parcel2.writeNoException();
                    parcel2.writeInt(genericCommand);
                    return true;
                case 7:
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int startMcfMHSAdvertisement = startMcfMHSAdvertisement(readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeInt(startMcfMHSAdvertisement);
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
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int startMcfClientMHSDiscovery(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int connectToMcfMHS(String str, int i, int i2, int i3, String str2, String str3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i4);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int getMcfConnectedStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int getMcfConnectedStatusFromScanResult(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int genericCommand(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.mcf.autohotspot.ISemAutohotspotMcf
            public int startMcfMHSAdvertisement(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemAutohotspotMcf.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
