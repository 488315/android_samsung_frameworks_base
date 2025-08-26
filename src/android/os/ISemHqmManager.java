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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemHqmManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemHqmManager)) {
                return (ISemHqmManager) iInterfaceQueryLocalInterface;
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
                    int i3 = parcel3.readInt();
                    String string = parcel3.readString();
                    String string2 = parcel3.readString();
                    String string3 = parcel3.readString();
                    String string4 = parcel3.readString();
                    String string5 = parcel3.readString();
                    String string6 = parcel3.readString();
                    String string7 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean zSendHWParamServer = sendHWParamServer(i3, string, string2, string3, string4, string5, string6, string7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendHWParamServer);
                    return true;
                case 2:
                    int i4 = parcel3.readInt();
                    String string8 = parcel3.readString();
                    String string9 = parcel3.readString();
                    String string10 = parcel3.readString();
                    String string11 = parcel3.readString();
                    String string12 = parcel3.readString();
                    String string13 = parcel3.readString();
                    String string14 = parcel3.readString();
                    String string15 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean zSendHWParamToHQM = sendHWParamToHQM(i4, string8, string9, string10, string11, string12, string13, string14, string15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendHWParamToHQM);
                    return true;
                case 3:
                    int i5 = parcel3.readInt();
                    String string16 = parcel3.readString();
                    String string17 = parcel3.readString();
                    String string18 = parcel3.readString();
                    String string19 = parcel3.readString();
                    String string20 = parcel3.readString();
                    String string21 = parcel3.readString();
                    String string22 = parcel3.readString();
                    String string23 = parcel3.readString();
                    String string24 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean zSendHWParamToHQMwithAppId = sendHWParamToHQMwithAppId(i5, string16, string17, string18, string19, string20, string21, string22, string23, string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendHWParamToHQMwithAppId);
                    return true;
                case 4:
                    int i6 = parcel3.readInt();
                    String string25 = parcel3.readString();
                    String string26 = parcel3.readString();
                    String string27 = parcel3.readString();
                    String string28 = parcel3.readString();
                    String string29 = parcel3.readString();
                    String string30 = parcel3.readString();
                    String string31 = parcel3.readString();
                    String string32 = parcel3.readString();
                    String string33 = parcel3.readString();
                    String string34 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    boolean zSendHWParamToHQMwithFile = sendHWParamToHQMwithFile(i6, string25, string26, string27, string28, string29, string30, string31, string32, string33, string34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zSendHWParamToHQMwithFile);
                    return true;
                case 5:
                    int i7 = parcel3.readInt();
                    String string35 = parcel3.readString();
                    String string36 = parcel3.readString();
                    parcel3.enforceNoDataAvail();
                    sendSystemInfoToHQM(i7, string35, string36);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean sendHWParamToHQM(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeString(str5);
                    parcelObtain.writeString(str6);
                    parcelObtain.writeString(str7);
                    parcelObtain.writeString(str8);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean sendHWParamToHQMwithAppId(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
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
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean sendHWParamToHQMwithFile(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
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
                    parcelObtain.writeString(str10);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public void sendSystemInfoToHQM(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean getHqmEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean getDVServerEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ISemHqmManager
            public boolean getCFServerEnable() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemHqmManager.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
