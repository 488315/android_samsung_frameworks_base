package com.samsung.android.game;

import android.app.PendingIntent;
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
        public void registerGameEventListener(PendingIntent pendingIntent, int[] iArr, boolean z, List<String> list) throws RemoteException {
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

        @Override // com.samsung.android.game.IGameManagerService
        public void unregisterGameEventListener(PendingIntent pendingIntent) throws RemoteException {
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

    void registerGameEventListener(PendingIntent pendingIntent, int[] iArr, boolean z, List<String> list) throws RemoteException;

    String requestWithJson(String str, String str2) throws RemoteException;

    boolean setPackageConfigurations(List<SemPackageConfiguration> list) throws RemoteException;

    boolean setPerformanceMode(int i, String str) throws RemoteException;

    boolean setTargetFrameRate(IBinder iBinder, int i) throws RemoteException;

    void syncGameList(Map map) throws RemoteException;

    boolean unregisterCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException;

    void unregisterGameEventListener(PendingIntent pendingIntent) throws RemoteException;

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
        static final int TRANSACTION_registerGameEventListener = 19;
        static final int TRANSACTION_requestWithJson = 8;
        static final int TRANSACTION_setPackageConfigurations = 10;
        static final int TRANSACTION_setPerformanceMode = 11;
        static final int TRANSACTION_setTargetFrameRate = 12;
        static final int TRANSACTION_syncGameList = 15;
        static final int TRANSACTION_unregisterCallback = 6;
        static final int TRANSACTION_unregisterGameEventListener = 20;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 19;
        }

        public Stub() {
            attachInterface(this, IGameManagerService.DESCRIPTOR);
        }

        public static IGameManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGameManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGameManagerService)) {
                return (IGameManagerService) iInterfaceQueryLocalInterface;
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
                case 19:
                    return "registerGameEventListener";
                case 20:
                    return "unregisterGameEventListener";
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
                    String string = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int iIdentifyGamePackage = identifyGamePackage(string);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIdentifyGamePackage);
                    return true;
                case 2:
                    int iIdentifyForegroundApp = identifyForegroundApp();
                    parcel2.writeNoException();
                    parcel2.writeInt(iIdentifyForegroundApp);
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
                    IGameManagerCallback iGameManagerCallbackAsInterface = IGameManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zRegisterCallback = registerCallback(iGameManagerCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRegisterCallback);
                    return true;
                case 6:
                    IGameManagerCallback iGameManagerCallbackAsInterface2 = IGameManagerCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zUnregisterCallback = unregisterCallback(iGameManagerCallbackAsInterface2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUnregisterCallback);
                    return true;
                case 7:
                    String version = getVersion();
                    parcel2.writeNoException();
                    parcel2.writeString(version);
                    return true;
                case 8:
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String strRequestWithJson = requestWithJson(string2, string3);
                    parcel2.writeNoException();
                    parcel2.writeString(strRequestWithJson);
                    return true;
                case 9:
                    String topActivityName = getTopActivityName();
                    parcel2.writeNoException();
                    parcel2.writeString(topActivityName);
                    return true;
                case 10:
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(SemPackageConfiguration.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean packageConfigurations = setPackageConfigurations(arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(packageConfigurations);
                    return true;
                case 11:
                    int i3 = parcel.readInt();
                    String string4 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean performanceMode = setPerformanceMode(i3, string4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(performanceMode);
                    return true;
                case 12:
                    IBinder strongBinder = parcel.readStrongBinder();
                    int i4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean targetFrameRate = setTargetFrameRate(strongBinder, i4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(targetFrameRate);
                    return true;
                case 13:
                    String string5 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDisableVrrControl = disableVrrControl(string5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDisableVrrControl);
                    return true;
                case 14:
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zEnableVrrControl = enableVrrControl(string6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableVrrControl);
                    return true;
                case 15:
                    HashMap hashMap = parcel.readHashMap(getClass().getClassLoader());
                    parcel.enforceNoDataAvail();
                    syncGameList(hashMap);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    SemPackageConfiguration packageConfiguration = getPackageConfiguration(string7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packageConfiguration, 1);
                    return true;
                case 17:
                    List<SemPackageConfiguration> packageConfigurations2 = getPackageConfigurations();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(packageConfigurations2, 1);
                    return true;
                case 18:
                    String string8 = parcel.readString();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyAppCreated(string8, i5);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    int[] iArrCreateIntArray = parcel.createIntArray();
                    boolean z = parcel.readBoolean();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    registerGameEventListener(pendingIntent, iArrCreateIntArray, z, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    PendingIntent pendingIntent2 = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    unregisterGameEventListener(pendingIntent2);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public int identifyForegroundApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getForegroundApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public List<String> getGameList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean registerCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGameManagerCallback);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean unregisterCallback(IGameManagerCallback iGameManagerCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGameManagerCallback);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String requestWithJson(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getTopActivityName() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setPackageConfigurations(List<SemPackageConfiguration> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setPerformanceMode(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setTargetFrameRate(IBinder iBinder, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean disableVrrControl(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean enableVrrControl(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public void syncGameList(Map map) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public SemPackageConfiguration getPackageConfiguration(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemPackageConfiguration) parcelObtain2.readTypedObject(SemPackageConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public List<SemPackageConfiguration> getPackageConfigurations() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemPackageConfiguration.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public void notifyAppCreated(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public void registerGameEventListener(PendingIntent pendingIntent, int[] iArr, boolean z, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public void unregisterGameEventListener(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
