package android.app;

import android.Manifest;
import android.app.IGameModeListener;
import android.app.IGameStateListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGameManagerService extends IInterface {
    public static final String DESCRIPTOR = "android.app.IGameManagerService";

    public static class Default implements IGameManagerService {
        @Override // android.app.IGameManagerService
        public void addGameModeListener(IGameModeListener iGameModeListener) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void addGameStateListener(IGameStateListener iGameStateListener) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.IGameManagerService
        public int[] getAvailableGameModes(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IGameManagerService
        public int getGameMode(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.IGameManagerService
        public GameModeInfo getGameModeInfo(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.IGameManagerService
        public float getResolutionScalingFactor(String str, int i, int i2) throws RemoteException {
            return 0.0f;
        }

        @Override // android.app.IGameManagerService
        public boolean isAngleEnabled(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.IGameManagerService
        public void notifyGraphicsEnvironmentSetup(String str, int i) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void removeGameModeListener(IGameModeListener iGameModeListener) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void removeGameStateListener(IGameStateListener iGameStateListener) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void setGameMode(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void setGameServiceProvider(String str) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void setGameState(String str, GameState gameState, int i) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void toggleGameDefaultFrameRate(boolean z) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void updateCustomGameModeConfiguration(String str, GameModeConfiguration gameModeConfiguration, int i) throws RemoteException {
        }

        @Override // android.app.IGameManagerService
        public void updateResolutionScalingFactor(String str, int i, float f, int i2) throws RemoteException {
        }
    }

    void addGameModeListener(IGameModeListener iGameModeListener) throws RemoteException;

    void addGameStateListener(IGameStateListener iGameStateListener) throws RemoteException;

    int[] getAvailableGameModes(String str, int i) throws RemoteException;

    int getGameMode(String str, int i) throws RemoteException;

    GameModeInfo getGameModeInfo(String str, int i) throws RemoteException;

    float getResolutionScalingFactor(String str, int i, int i2) throws RemoteException;

    boolean isAngleEnabled(String str, int i) throws RemoteException;

    void notifyGraphicsEnvironmentSetup(String str, int i) throws RemoteException;

    void removeGameModeListener(IGameModeListener iGameModeListener) throws RemoteException;

    void removeGameStateListener(IGameStateListener iGameStateListener) throws RemoteException;

    void setGameMode(String str, int i, int i2) throws RemoteException;

    void setGameServiceProvider(String str) throws RemoteException;

    void setGameState(String str, GameState gameState, int i) throws RemoteException;

    void toggleGameDefaultFrameRate(boolean z) throws RemoteException;

    void updateCustomGameModeConfiguration(String str, GameModeConfiguration gameModeConfiguration, int i) throws RemoteException;

    void updateResolutionScalingFactor(String str, int i, float f, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IGameManagerService {
        static final int TRANSACTION_addGameModeListener = 12;
        static final int TRANSACTION_addGameStateListener = 14;
        static final int TRANSACTION_getAvailableGameModes = 3;
        static final int TRANSACTION_getGameMode = 1;
        static final int TRANSACTION_getGameModeInfo = 7;
        static final int TRANSACTION_getResolutionScalingFactor = 10;
        static final int TRANSACTION_isAngleEnabled = 4;
        static final int TRANSACTION_notifyGraphicsEnvironmentSetup = 5;
        static final int TRANSACTION_removeGameModeListener = 13;
        static final int TRANSACTION_removeGameStateListener = 15;
        static final int TRANSACTION_setGameMode = 2;
        static final int TRANSACTION_setGameServiceProvider = 8;
        static final int TRANSACTION_setGameState = 6;
        static final int TRANSACTION_toggleGameDefaultFrameRate = 16;
        static final int TRANSACTION_updateCustomGameModeConfiguration = 11;
        static final int TRANSACTION_updateResolutionScalingFactor = 9;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, IGameManagerService.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
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
                    return "getGameMode";
                case 2:
                    return "setGameMode";
                case 3:
                    return "getAvailableGameModes";
                case 4:
                    return "isAngleEnabled";
                case 5:
                    return "notifyGraphicsEnvironmentSetup";
                case 6:
                    return "setGameState";
                case 7:
                    return "getGameModeInfo";
                case 8:
                    return "setGameServiceProvider";
                case 9:
                    return "updateResolutionScalingFactor";
                case 10:
                    return "getResolutionScalingFactor";
                case 11:
                    return "updateCustomGameModeConfiguration";
                case 12:
                    return "addGameModeListener";
                case 13:
                    return "removeGameModeListener";
                case 14:
                    return "addGameStateListener";
                case 15:
                    return "removeGameStateListener";
                case 16:
                    return "toggleGameDefaultFrameRate";
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int gameMode = getGameMode(readString, readInt);
                    parcel2.writeNoException();
                    parcel2.writeInt(gameMode);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGameMode(readString2, readInt2, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] availableGameModes = getAvailableGameModes(readString3, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(availableGameModes);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAngleEnabled = isAngleEnabled(readString4, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAngleEnabled);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyGraphicsEnvironmentSetup(readString5, readInt6);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    GameState gameState = (GameState) parcel.readTypedObject(GameState.CREATOR);
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGameState(readString6, gameState, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    GameModeInfo gameModeInfo = getGameModeInfo(readString7, readInt8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(gameModeInfo, 1);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGameServiceProvider(readString8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    float readFloat = parcel.readFloat();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateResolutionScalingFactor(readString9, readInt9, readFloat, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String readString10 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float resolutionScalingFactor = getResolutionScalingFactor(readString10, readInt11, readInt12);
                    parcel2.writeNoException();
                    parcel2.writeFloat(resolutionScalingFactor);
                    return true;
                case 11:
                    String readString11 = parcel.readString();
                    GameModeConfiguration gameModeConfiguration = (GameModeConfiguration) parcel.readTypedObject(GameModeConfiguration.CREATOR);
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCustomGameModeConfiguration(readString11, gameModeConfiguration, readInt13);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IGameModeListener asInterface = IGameModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addGameModeListener(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IGameModeListener asInterface2 = IGameModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeGameModeListener(asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IGameStateListener asInterface3 = IGameStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addGameStateListener(asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IGameStateListener asInterface4 = IGameStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeGameStateListener(asInterface4);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    toggleGameDefaultFrameRate(readBoolean);
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

            @Override // android.app.IGameManagerService
            public int getGameMode(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameMode(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public int[] getAvailableGameModes(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public boolean isAngleEnabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void notifyGraphicsEnvironmentSetup(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameState(String str, GameState gameState, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(gameState, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public GameModeInfo getGameModeInfo(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (GameModeInfo) obtain2.readTypedObject(GameModeInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameServiceProvider(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void updateResolutionScalingFactor(String str, int i, float f, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeFloat(f);
                    obtain.writeInt(i2);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public float getResolutionScalingFactor(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readFloat();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void updateCustomGameModeConfiguration(String str, GameModeConfiguration gameModeConfiguration, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(gameModeConfiguration, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void addGameModeListener(IGameModeListener iGameModeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameModeListener);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void removeGameModeListener(IGameModeListener iGameModeListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameModeListener);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void addGameStateListener(IGameStateListener iGameStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameStateListener);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void removeGameStateListener(IGameStateListener iGameStateListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeStrongInterface(iGameStateListener);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void toggleGameDefaultFrameRate(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void toggleGameDefaultFrameRate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_GAME_MODE, getCallingPid(), getCallingUid());
        }
    }
}
