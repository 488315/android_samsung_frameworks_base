package android.os;

import android.Manifest;
import android.app.ActivityThread;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public interface IDeviceIdleController extends IInterface {

    public static class Default implements IDeviceIdleController {
        @Override // android.os.IDeviceIdleController
        public void addPowerSaveTempWhitelistApp(String str, long j, int i, int i2, String str2) throws RemoteException {
        }

        @Override // android.os.IDeviceIdleController
        public long addPowerSaveTempWhitelistAppForMms(String str, int i, int i2, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IDeviceIdleController
        public long addPowerSaveTempWhitelistAppForSms(String str, int i, int i2, String str2) throws RemoteException {
            return 0L;
        }

        @Override // android.os.IDeviceIdleController
        public void addPowerSaveWhitelistApp(String str) throws RemoteException {
        }

        @Override // android.os.IDeviceIdleController
        public int addPowerSaveWhitelistApps(List<String> list) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public void exitIdle(String str) throws RemoteException {
        }

        @Override // android.os.IDeviceIdleController
        public int[] getAppIdTempWhitelist() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public int[] getAppIdUserWhitelist() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public int[] getAppIdWhitelist() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public int[] getAppIdWhitelistExceptIdle() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public String[] getFullPowerWhitelist() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public String[] getFullPowerWhitelistExceptIdle() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public String[] getRemovedSystemPowerWhitelistApps() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public String[] getSystemPowerWhitelist() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public String[] getSystemPowerWhitelistExceptIdle() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public String[] getUserPowerWhitelist() throws RemoteException {
            return null;
        }

        @Override // android.os.IDeviceIdleController
        public boolean isPowerSaveWhitelistApp(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IDeviceIdleController
        public boolean isPowerSaveWhitelistExceptIdleApp(String str) throws RemoteException {
            return false;
        }

        @Override // android.os.IDeviceIdleController
        public void removePowerSaveWhitelistApp(String str) throws RemoteException {
        }

        @Override // android.os.IDeviceIdleController
        public void removeSystemPowerWhitelistApp(String str) throws RemoteException {
        }

        @Override // android.os.IDeviceIdleController
        public void restoreSystemPowerWhitelistApp(String str) throws RemoteException {
        }

        @Override // android.os.IDeviceIdleController
        public long whitelistAppTemporarily(String str, int i, int i2, String str2) throws RemoteException {
            return 0L;
        }
    }

    void addPowerSaveTempWhitelistApp(String str, long j, int i, int i2, String str2) throws RemoteException;

    long addPowerSaveTempWhitelistAppForMms(String str, int i, int i2, String str2) throws RemoteException;

    long addPowerSaveTempWhitelistAppForSms(String str, int i, int i2, String str2) throws RemoteException;

    void addPowerSaveWhitelistApp(String str) throws RemoteException;

    int addPowerSaveWhitelistApps(List<String> list) throws RemoteException;

    void exitIdle(String str) throws RemoteException;

    int[] getAppIdTempWhitelist() throws RemoteException;

    int[] getAppIdUserWhitelist() throws RemoteException;

    int[] getAppIdWhitelist() throws RemoteException;

    int[] getAppIdWhitelistExceptIdle() throws RemoteException;

    String[] getFullPowerWhitelist() throws RemoteException;

    String[] getFullPowerWhitelistExceptIdle() throws RemoteException;

    String[] getRemovedSystemPowerWhitelistApps() throws RemoteException;

    String[] getSystemPowerWhitelist() throws RemoteException;

    String[] getSystemPowerWhitelistExceptIdle() throws RemoteException;

    String[] getUserPowerWhitelist() throws RemoteException;

    boolean isPowerSaveWhitelistApp(String str) throws RemoteException;

    boolean isPowerSaveWhitelistExceptIdleApp(String str) throws RemoteException;

    void removePowerSaveWhitelistApp(String str) throws RemoteException;

    void removeSystemPowerWhitelistApp(String str) throws RemoteException;

    void restoreSystemPowerWhitelistApp(String str) throws RemoteException;

    long whitelistAppTemporarily(String str, int i, int i2, String str2) throws RemoteException;

    public static abstract class Stub extends Binder implements IDeviceIdleController {
        public static final String DESCRIPTOR = "android.os.IDeviceIdleController";
        static final int TRANSACTION_addPowerSaveTempWhitelistApp = 18;
        static final int TRANSACTION_addPowerSaveTempWhitelistAppForMms = 19;
        static final int TRANSACTION_addPowerSaveTempWhitelistAppForSms = 20;
        static final int TRANSACTION_addPowerSaveWhitelistApp = 1;
        static final int TRANSACTION_addPowerSaveWhitelistApps = 2;
        static final int TRANSACTION_exitIdle = 22;
        static final int TRANSACTION_getAppIdTempWhitelist = 15;
        static final int TRANSACTION_getAppIdUserWhitelist = 14;
        static final int TRANSACTION_getAppIdWhitelist = 13;
        static final int TRANSACTION_getAppIdWhitelistExceptIdle = 12;
        static final int TRANSACTION_getFullPowerWhitelist = 11;
        static final int TRANSACTION_getFullPowerWhitelistExceptIdle = 10;
        static final int TRANSACTION_getRemovedSystemPowerWhitelistApps = 6;
        static final int TRANSACTION_getSystemPowerWhitelist = 8;
        static final int TRANSACTION_getSystemPowerWhitelistExceptIdle = 7;
        static final int TRANSACTION_getUserPowerWhitelist = 9;
        static final int TRANSACTION_isPowerSaveWhitelistApp = 17;
        static final int TRANSACTION_isPowerSaveWhitelistExceptIdleApp = 16;
        static final int TRANSACTION_removePowerSaveWhitelistApp = 3;
        static final int TRANSACTION_removeSystemPowerWhitelistApp = 4;
        static final int TRANSACTION_restoreSystemPowerWhitelistApp = 5;
        static final int TRANSACTION_whitelistAppTemporarily = 21;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 21;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IDeviceIdleController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IDeviceIdleController)) {
                return (IDeviceIdleController) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "addPowerSaveWhitelistApp";
                case 2:
                    return "addPowerSaveWhitelistApps";
                case 3:
                    return "removePowerSaveWhitelistApp";
                case 4:
                    return "removeSystemPowerWhitelistApp";
                case 5:
                    return "restoreSystemPowerWhitelistApp";
                case 6:
                    return "getRemovedSystemPowerWhitelistApps";
                case 7:
                    return "getSystemPowerWhitelistExceptIdle";
                case 8:
                    return "getSystemPowerWhitelist";
                case 9:
                    return "getUserPowerWhitelist";
                case 10:
                    return "getFullPowerWhitelistExceptIdle";
                case 11:
                    return "getFullPowerWhitelist";
                case 12:
                    return "getAppIdWhitelistExceptIdle";
                case 13:
                    return "getAppIdWhitelist";
                case 14:
                    return "getAppIdUserWhitelist";
                case 15:
                    return "getAppIdTempWhitelist";
                case 16:
                    return "isPowerSaveWhitelistExceptIdleApp";
                case 17:
                    return "isPowerSaveWhitelistApp";
                case 18:
                    return "addPowerSaveTempWhitelistApp";
                case 19:
                    return "addPowerSaveTempWhitelistAppForMms";
                case 20:
                    return "addPowerSaveTempWhitelistAppForSms";
                case 21:
                    return "whitelistAppTemporarily";
                case 22:
                    return "exitIdle";
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
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addPowerSaveWhitelistApp(readString);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    int addPowerSaveWhitelistApps = addPowerSaveWhitelistApps(createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeInt(addPowerSaveWhitelistApps);
                    return true;
                case 3:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removePowerSaveWhitelistApp(readString2);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    removeSystemPowerWhitelistApp(readString3);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    restoreSystemPowerWhitelistApp(readString4);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String[] removedSystemPowerWhitelistApps = getRemovedSystemPowerWhitelistApps();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(removedSystemPowerWhitelistApps);
                    return true;
                case 7:
                    String[] systemPowerWhitelistExceptIdle = getSystemPowerWhitelistExceptIdle();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(systemPowerWhitelistExceptIdle);
                    return true;
                case 8:
                    String[] systemPowerWhitelist = getSystemPowerWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(systemPowerWhitelist);
                    return true;
                case 9:
                    String[] userPowerWhitelist = getUserPowerWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(userPowerWhitelist);
                    return true;
                case 10:
                    String[] fullPowerWhitelistExceptIdle = getFullPowerWhitelistExceptIdle();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(fullPowerWhitelistExceptIdle);
                    return true;
                case 11:
                    String[] fullPowerWhitelist = getFullPowerWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeStringArray(fullPowerWhitelist);
                    return true;
                case 12:
                    int[] appIdWhitelistExceptIdle = getAppIdWhitelistExceptIdle();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appIdWhitelistExceptIdle);
                    return true;
                case 13:
                    int[] appIdWhitelist = getAppIdWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appIdWhitelist);
                    return true;
                case 14:
                    int[] appIdUserWhitelist = getAppIdUserWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appIdUserWhitelist);
                    return true;
                case 15:
                    int[] appIdTempWhitelist = getAppIdTempWhitelist();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(appIdTempWhitelist);
                    return true;
                case 16:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPowerSaveWhitelistExceptIdleApp = isPowerSaveWhitelistExceptIdleApp(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPowerSaveWhitelistExceptIdleApp);
                    return true;
                case 17:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPowerSaveWhitelistApp = isPowerSaveWhitelistApp(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPowerSaveWhitelistApp);
                    return true;
                case 18:
                    String readString7 = parcel.readString();
                    long readLong = parcel.readLong();
                    int readInt = parcel.readInt();
                    int readInt2 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addPowerSaveTempWhitelistApp(readString7, readLong, readInt, readInt2, readString8);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString9 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    String readString10 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long addPowerSaveTempWhitelistAppForMms = addPowerSaveTempWhitelistAppForMms(readString9, readInt3, readInt4, readString10);
                    parcel2.writeNoException();
                    parcel2.writeLong(addPowerSaveTempWhitelistAppForMms);
                    return true;
                case 20:
                    String readString11 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    int readInt6 = parcel.readInt();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long addPowerSaveTempWhitelistAppForSms = addPowerSaveTempWhitelistAppForSms(readString11, readInt5, readInt6, readString12);
                    parcel2.writeNoException();
                    parcel2.writeLong(addPowerSaveTempWhitelistAppForSms);
                    return true;
                case 21:
                    String readString13 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    int readInt8 = parcel.readInt();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    long whitelistAppTemporarily = whitelistAppTemporarily(readString13, readInt7, readInt8, readString14);
                    parcel2.writeNoException();
                    parcel2.writeLong(whitelistAppTemporarily);
                    return true;
                case 22:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    exitIdle(readString15);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IDeviceIdleController {
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

            @Override // android.os.IDeviceIdleController
            public void addPowerSaveWhitelistApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public int addPowerSaveWhitelistApps(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public void removePowerSaveWhitelistApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public void removeSystemPowerWhitelistApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public void restoreSystemPowerWhitelistApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public String[] getRemovedSystemPowerWhitelistApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public String[] getSystemPowerWhitelistExceptIdle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public String[] getSystemPowerWhitelist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public String[] getUserPowerWhitelist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public String[] getFullPowerWhitelistExceptIdle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public String[] getFullPowerWhitelist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public int[] getAppIdWhitelistExceptIdle() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public int[] getAppIdWhitelist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public int[] getAppIdUserWhitelist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public int[] getAppIdTempWhitelist() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public boolean isPowerSaveWhitelistExceptIdleApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public boolean isPowerSaveWhitelistApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public void addPowerSaveTempWhitelistApp(String str, long j, int i, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public long addPowerSaveTempWhitelistAppForMms(String str, int i, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public long addPowerSaveTempWhitelistAppForSms(String str, int i, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public long whitelistAppTemporarily(String str, int i, int i2, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IDeviceIdleController
            public void exitIdle(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void exitIdle_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DEVICE_POWER, getCallingPid(), getCallingUid());
        }
    }
}
