package android.os;

/* loaded from: classes3.dex */
public interface IVoldListener extends IInterface {

    public static class Default implements IVoldListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IVoldListener
        public void onDiskCreated(String str, int i) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onDiskDestroyed(String str) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onDiskMetadataChanged(String str, long j, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onDiskScanned(String str) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onEncryptionStateChanged(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeCreated(String str, int i, String str2, String str3, int i2) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeDestroyed(String str) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeInternalPathChanged(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeMetadataChanged(String str, String str2, String str3, String str4) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumePathChanged(String str, String str2) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void onVolumeStateChanged(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.IVoldListener
        public void sendVoldMessage(String str) throws RemoteException {
        }
    }

    void onDiskCreated(String str, int i) throws RemoteException;

    void onDiskDestroyed(String str) throws RemoteException;

    void onDiskMetadataChanged(String str, long j, String str2, String str3) throws RemoteException;

    void onDiskScanned(String str) throws RemoteException;

    void onEncryptionStateChanged(String str, String str2, String str3) throws RemoteException;

    void onVolumeCreated(String str, int i, String str2, String str3, int i2) throws RemoteException;

    void onVolumeDestroyed(String str) throws RemoteException;

    void onVolumeInternalPathChanged(String str, String str2) throws RemoteException;

    void onVolumeMetadataChanged(String str, String str2, String str3, String str4) throws RemoteException;

    void onVolumePathChanged(String str, String str2) throws RemoteException;

    void onVolumeStateChanged(String str, int i, int i2) throws RemoteException;

    void sendVoldMessage(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IVoldListener {
        public static final String DESCRIPTOR = "android.os.IVoldListener";
        static final int TRANSACTION_onDiskCreated = 1;
        static final int TRANSACTION_onDiskDestroyed = 4;
        static final int TRANSACTION_onDiskMetadataChanged = 3;
        static final int TRANSACTION_onDiskScanned = 2;
        static final int TRANSACTION_onEncryptionStateChanged = 12;
        static final int TRANSACTION_onVolumeCreated = 5;
        static final int TRANSACTION_onVolumeDestroyed = 10;
        static final int TRANSACTION_onVolumeInternalPathChanged = 9;
        static final int TRANSACTION_onVolumeMetadataChanged = 7;
        static final int TRANSACTION_onVolumePathChanged = 8;
        static final int TRANSACTION_onVolumeStateChanged = 6;
        static final int TRANSACTION_sendVoldMessage = 11;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 11;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IVoldListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IVoldListener)) {
                return (IVoldListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "onDiskCreated";
                case 2:
                    return "onDiskScanned";
                case 3:
                    return "onDiskMetadataChanged";
                case 4:
                    return "onDiskDestroyed";
                case 5:
                    return "onVolumeCreated";
                case 6:
                    return "onVolumeStateChanged";
                case 7:
                    return "onVolumeMetadataChanged";
                case 8:
                    return "onVolumePathChanged";
                case 9:
                    return "onVolumeInternalPathChanged";
                case 10:
                    return "onVolumeDestroyed";
                case 11:
                    return "sendVoldMessage";
                case 12:
                    return "onEncryptionStateChanged";
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
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onDiskCreated(string, i3);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDiskScanned(string2);
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    long j = parcel.readLong();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDiskMetadataChanged(string3, j, string4, string5);
                    return true;
                case 4:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onDiskDestroyed(string6);
                    return true;
                case 5:
                    String string7 = parcel.readString();
                    int i4 = parcel.readInt();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVolumeCreated(string7, i4, string8, string9, i5);
                    return true;
                case 6:
                    String string10 = parcel.readString();
                    int i6 = parcel.readInt();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    onVolumeStateChanged(string10, i6, i7);
                    return true;
                case 7:
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumeMetadataChanged(string11, string12, string13, string14);
                    return true;
                case 8:
                    String string15 = parcel.readString();
                    String string16 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumePathChanged(string15, string16);
                    return true;
                case 9:
                    String string17 = parcel.readString();
                    String string18 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumeInternalPathChanged(string17, string18);
                    return true;
                case 10:
                    String string19 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onVolumeDestroyed(string19);
                    return true;
                case 11:
                    String string20 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendVoldMessage(string20);
                    return true;
                case 12:
                    String string21 = parcel.readString();
                    String string22 = parcel.readString();
                    String string23 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    onEncryptionStateChanged(string21, string22, string23);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IVoldListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.os.IVoldListener
            public void onDiskCreated(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onDiskScanned(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onDiskMetadataChanged(String str, long j, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onDiskDestroyed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeCreated(String str, int i, String str2, String str3, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeStateChanged(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeMetadataChanged(String str, String str2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(7, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumePathChanged(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeInternalPathChanged(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(9, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onVolumeDestroyed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void sendVoldMessage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IVoldListener
            public void onEncryptionStateChanged(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(12, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
