package android.os;

/* loaded from: classes3.dex */
public interface ISemHqmManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.ISemHqmManager";

    public static class Default implements ISemHqmManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ISemHqmManager
        public boolean getCFServerEnable() throws RemoteException {
            return false;
        }

        @Override // android.os.ISemHqmManager
        public boolean getDVServerEnable() throws RemoteException {
            return false;
        }

        @Override // android.os.ISemHqmManager
        public boolean getHqmEnable() throws RemoteException {
            return false;
        }

        @Override // android.os.ISemHqmManager
        public boolean sendHWParamServer(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) throws RemoteException {
            return false;
        }

        @Override // android.os.ISemHqmManager
        public boolean sendHWParamToHQM(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) throws RemoteException {
            return false;
        }

        @Override // android.os.ISemHqmManager
        public boolean sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException {
            return false;
        }

        @Override // android.os.ISemHqmManager
        public boolean sendHWParamToHQMwithFile(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) throws RemoteException {
            return false;
        }

        @Override // android.os.ISemHqmManager
        public void sendSystemInfoToHQM(int i, String str, String str2) throws RemoteException {
        }
    }

    boolean getCFServerEnable() throws RemoteException;

    boolean getDVServerEnable() throws RemoteException;

    boolean getHqmEnable() throws RemoteException;

    boolean sendHWParamServer(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) throws RemoteException;

    boolean sendHWParamToHQM(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) throws RemoteException;

    boolean sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException;

    boolean sendHWParamToHQMwithFile(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) throws RemoteException;

    void sendSystemInfoToHQM(int i, String str, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemHqmManager {
        static final int TRANSACTION_getCFServerEnable = 8;
        static final int TRANSACTION_getDVServerEnable = 7;
        static final int TRANSACTION_getHqmEnable = 6;
        static final int TRANSACTION_sendHWParamServer = 1;
        static final int TRANSACTION_sendHWParamToHQM = 2;
        static final int TRANSACTION_sendHWParamToHQMwithAppId = 3;
        static final int TRANSACTION_sendHWParamToHQMwithFile = 4;
        static final int TRANSACTION_sendSystemInfoToHQM = 5;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 7;
        }

        public Stub() {
            attachInterface(this, ISemHqmManager.DESCRIPTOR);
        }

        public static ISemHqmManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISemHqmManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISemHqmManager)) {
                return (ISemHqmManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendHWParamServer";
                case 2:
                    return "sendHWParamToHQM";
                case 3:
                    return "sendHWParamToHQMwithAppId";
                case 4:
                    return "sendHWParamToHQMwithFile";
                case 5:
                    return "sendSystemInfoToHQM";
                case 6:
                    return "getHqmEnable";
                case 7:
                    return "getDVServerEnable";
                case 8:
                    return "getCFServerEnable";
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
            Parcel parcel3;
            if (i < 1 || i > 16777215) {
                parcel3 = parcel;
            } else {
                parcel3 = parcel;
                parcel3.enforceInterface(ISemHqmManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemHqmManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    int readInt = parcel3.readInt();
                    String readString = parcel3.readString();
                    String readString2 = parcel3.readString();
                    String readString3 = parcel3.readString();
                    String readString4 = parcel3.readString();
                    String readString5 = parcel3.readString();
                    String readString6 = parcel3.readString();
                    String readString7 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean sendHWParamServer = sendHWParamServer(readInt, readString, readString2, readString3, readString4, readString5, readString6, readString7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendHWParamServer);
                    return true;
                case 2:
                    int readInt2 = parcel3.readInt();
                    String readString8 = parcel3.readString();
                    String readString9 = parcel3.readString();
                    String readString10 = parcel3.readString();
                    String readString11 = parcel3.readString();
                    String readString12 = parcel3.readString();
                    String readString13 = parcel3.readString();
                    String readString14 = parcel3.readString();
                    String readString15 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean sendHWParamToHQM = sendHWParamToHQM(readInt2, readString8, readString9, readString10, readString11, readString12, readString13, readString14, readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendHWParamToHQM);
                    return true;
                case 3:
                    int readInt3 = parcel3.readInt();
                    String readString16 = parcel3.readString();
                    String readString17 = parcel3.readString();
                    String readString18 = parcel3.readString();
                    String readString19 = parcel3.readString();
                    String readString20 = parcel3.readString();
                    String readString21 = parcel3.readString();
                    String readString22 = parcel3.readString();
                    String readString23 = parcel3.readString();
                    String readString24 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean sendHWParamToHQMwithAppId = sendHWParamToHQMwithAppId(readInt3, readString16, readString17, readString18, readString19, readString20, readString21, readString22, readString23, readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendHWParamToHQMwithAppId);
                    return true;
                case 4:
                    int readInt4 = parcel3.readInt();
                    String readString25 = parcel3.readString();
                    String readString26 = parcel3.readString();
                    String readString27 = parcel3.readString();
                    String readString28 = parcel3.readString();
                    String readString29 = parcel3.readString();
                    String readString30 = parcel3.readString();
                    String readString31 = parcel3.readString();
                    String readString32 = parcel3.readString();
                    String readString33 = parcel3.readString();
                    String readString34 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean sendHWParamToHQMwithFile = sendHWParamToHQMwithFile(readInt4, readString25, readString26, readString27, readString28, readString29, readString30, readString31, readString32, readString33, readString34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(sendHWParamToHQMwithFile);
                    return true;
                case 5:
                    int readInt5 = parcel3.readInt();
                    String readString35 = parcel3.readString();
                    String readString36 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    sendSystemInfoToHQM(readInt5, readString35, readString36);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    boolean hqmEnable = getHqmEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hqmEnable);
                    return true;
                case 7:
                    boolean dVServerEnable = getDVServerEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dVServerEnable);
                    return true;
                case 8:
                    boolean cFServerEnable = getCFServerEnable();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(cFServerEnable);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISemHqmManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemHqmManager.DESCRIPTOR;
            }

            @Override // android.os.ISemHqmManager
            public boolean sendHWParamServer(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean sendHWParamToHQM(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeString(str8);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
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
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean sendHWParamToHQMwithFile(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
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
                    obtain.writeString(str10);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public void sendSystemInfoToHQM(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean getHqmEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean getDVServerEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean getCFServerEnable() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
