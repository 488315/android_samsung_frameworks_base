package com.samsung.android.game;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.game.IGameManagerCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public interface IGameManagerService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.game.IGameManagerService";

    public static class Default implements IGameManagerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean disableVrrControl(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean enableVrrControl(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public String getForegroundApp() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public List<String> getGameList() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public SemPackageConfiguration getPackageConfiguration(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public List<SemPackageConfiguration> getPackageConfigurations() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public String getTopActivityName() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public String getVersion() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public int identifyForegroundApp() throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public int identifyGamePackage(String str) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public void notifyAppCreated(String str, int i) throws RemoteException {
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean registerCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public String requestWithJson(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean setPackageConfigurations(List<SemPackageConfiguration> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean setPerformanceMode(int i, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean setTargetFrameRate(IBinder iBinder, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public void syncGameList(Map map) throws RemoteException {
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean unregisterCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException {
            return false;
        }
    }

    boolean disableVrrControl(String str) throws RemoteException;

    boolean enableVrrControl(String str) throws RemoteException;

    String getForegroundApp() throws RemoteException;

    List<String> getGameList() throws RemoteException;

    SemPackageConfiguration getPackageConfiguration(String str) throws RemoteException;

    List<SemPackageConfiguration> getPackageConfigurations() throws RemoteException;

    String getTopActivityName() throws RemoteException;

    String getVersion() throws RemoteException;

    int identifyForegroundApp() throws RemoteException;

    int identifyGamePackage(String str) throws RemoteException;

    void notifyAppCreated(String str, int i) throws RemoteException;

    boolean registerCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException;

    String requestWithJson(String str, String str2) throws RemoteException;

    boolean setPackageConfigurations(List<SemPackageConfiguration> list) throws RemoteException;

    boolean setPerformanceMode(int i, String str) throws RemoteException;

    boolean setTargetFrameRate(IBinder iBinder, int i) throws RemoteException;

    void syncGameList(Map map) throws RemoteException;

    boolean unregisterCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IGameManagerService {
        static final int TRANSACTION_disableVrrControl = 13;
        static final int TRANSACTION_enableVrrControl = 14;
        static final int TRANSACTION_getForegroundApp = 3;
        static final int TRANSACTION_getGameList = 4;
        static final int TRANSACTION_getPackageConfiguration = 16;
        static final int TRANSACTION_getPackageConfigurations = 17;
        static final int TRANSACTION_getTopActivityName = 9;
        static final int TRANSACTION_getVersion = 7;
        static final int TRANSACTION_identifyForegroundApp = 2;
        static final int TRANSACTION_identifyGamePackage = 1;
        static final int TRANSACTION_notifyAppCreated = 18;
        static final int TRANSACTION_registerCallback = 5;
        static final int TRANSACTION_requestWithJson = 8;
        static final int TRANSACTION_setPackageConfigurations = 10;
        static final int TRANSACTION_setPerformanceMode = 11;
        static final int TRANSACTION_setTargetFrameRate = 12;
        static final int TRANSACTION_syncGameList = 15;
        static final int TRANSACTION_unregisterCallback = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }

        public Stub() {
            attachInterface(this, IGameManagerService.DESCRIPTOR);
        }

        public static IGameManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGameManagerService.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGameManagerService)) {
                return (IGameManagerService) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "identifyGamePackage";
                case 2:
                    return "identifyForegroundApp";
                case 3:
                    return "getForegroundApp";
                case 4:
                    return "getGameList";
                case 5:
                    return "registerCallback";
                case 6:
                    return "unregisterCallback";
                case 7:
                    return "getVersion";
                case 8:
                    return "requestWithJson";
                case 9:
                    return "getTopActivityName";
                case 10:
                    return "setPackageConfigurations";
                case 11:
                    return "setPerformanceMode";
                case 12:
                    return "setTargetFrameRate";
                case 13:
                    return "disableVrrControl";
                case 14:
                    return "enableVrrControl";
                case 15:
                    return "syncGameList";
                case 16:
                    return "getPackageConfiguration";
                case 17:
                    return "getPackageConfigurations";
                case 18:
                    return "notifyAppCreated";
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
                parcel.enforceInterface(IGameManagerService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGameManagerService.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int identifyGamePackage = identifyGamePackage(readString);
                    parcel2.writeNoException();
                    parcel2.writeInt(identifyGamePackage);
                    return true;
                case 2:
                    int identifyForegroundApp = identifyForegroundApp();
                    parcel2.writeNoException();
                    parcel2.writeInt(identifyForegroundApp);
                    return true;
                case 3:
                    String foregroundApp = getForegroundApp();
                    parcel2.writeNoException();
                    parcel2.writeString(foregroundApp);
                    return true;
                case 4:
                    List<String> gameList = getGameList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(gameList);
                    return true;
                case 5:
                    IGameManagerCallback asInterface = IGameManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean registerCallback = registerCallback(asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(registerCallback);
                    return true;
                case 6:
                    IGameManagerCallback asInterface2 = IGameManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean unregisterCallback = unregisterCallback(asInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(unregisterCallback);
                    return true;
                case 7:
                    String version = getVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(version);
                    return true;
                case 8:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String requestWithJson = requestWithJson(readString2, readString3);
                    parcel2.writeNoException();
                    parcel2.writeString(requestWithJson);
                    return true;
                case 9:
                    String topActivityName = getTopActivityName();
                    parcel2.writeNoException();
                    parcel2.writeString(topActivityName);
                    return true;
                case 10:
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(SemPackageConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean packageConfigurations = setPackageConfigurations(createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageConfigurations);
                    return true;
                case 11:
                    int readInt = parcel.readInt();
                    String readString4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean performanceMode = setPerformanceMode(readInt, readString4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performanceMode);
                    return true;
                case 12:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean targetFrameRate = setTargetFrameRate(readStrongBinder, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(targetFrameRate);
                    return true;
                case 13:
                    String readString5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean disableVrrControl = disableVrrControl(readString5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(disableVrrControl);
                    return true;
                case 14:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean enableVrrControl = enableVrrControl(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableVrrControl);
                    return true;
                case 15:
                    HashMap readHashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    syncGameList(readHashMap);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SemPackageConfiguration packageConfiguration = getPackageConfiguration(readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageConfiguration, 1);
                    return true;
                case 17:
                    List<SemPackageConfiguration> packageConfigurations2 = getPackageConfigurations();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packageConfigurations2, 1);
                    return true;
                case 18:
                    String readString8 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppCreated(readString8, readInt3);
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IGameManagerService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameManagerService.DESCRIPTOR;
            }

            @Override // com.samsung.android.game.IGameManagerService
            public int identifyGamePackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public int identifyForegroundApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getForegroundApp() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public List<String> getGameList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean registerCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameManagerCallback);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean unregisterCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameManagerCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String requestWithJson(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getTopActivityName() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setPackageConfigurations(List<SemPackageConfiguration> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setPerformanceMode(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setTargetFrameRate(IBinder iBinder, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean disableVrrControl(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean enableVrrControl(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public void syncGameList(Map map) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeMap(map);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public SemPackageConfiguration getPackageConfiguration(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (SemPackageConfiguration) obtain2.readTypedObject(SemPackageConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public List<SemPackageConfiguration> getPackageConfigurations() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemPackageConfiguration.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public void notifyAppCreated(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
