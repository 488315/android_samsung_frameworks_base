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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGameManagerService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGameManagerService)) {
                return (IGameManagerService) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int gameMode = getGameMode(string, i3);
                    parcel2.writeNoException();
                    parcel2.writeInt(gameMode);
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    int i5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGameMode(string2, i4, i5);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] availableGameModes = getAvailableGameModes(string3, i6);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(availableGameModes);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAngleEnabled = isAngleEnabled(string4, i7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAngleEnabled);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyGraphicsEnvironmentSetup(string5, i8);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    GameState gameState = (GameState) parcel.readTypedObject(GameState.CREATOR);
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setGameState(string6, gameState, i9);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    GameModeInfo gameModeInfo = getGameModeInfo(string7, i10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(gameModeInfo, 1);
                    return true;
                case 8:
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setGameServiceProvider(string8);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string9 = parcel.readString();
                    int i11 = parcel.readInt();
                    float f = parcel.readFloat();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateResolutionScalingFactor(string9, i11, f, i12);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    String string10 = parcel.readString();
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float resolutionScalingFactor = getResolutionScalingFactor(string10, i13, i14);
                    parcel2.writeNoException();
                    parcel2.writeFloat(resolutionScalingFactor);
                    return true;
                case 11:
                    String string11 = parcel.readString();
                    GameModeConfiguration gameModeConfiguration = (GameModeConfiguration) parcel.readTypedObject(GameModeConfiguration.CREATOR);
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    updateCustomGameModeConfiguration(string11, gameModeConfiguration, i15);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    IGameModeListener iGameModeListenerAsInterface = IGameModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addGameModeListener(iGameModeListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    IGameModeListener iGameModeListenerAsInterface2 = IGameModeListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeGameModeListener(iGameModeListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    IGameStateListener iGameStateListenerAsInterface = IGameStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    addGameStateListener(iGameStateListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    IGameStateListener iGameStateListenerAsInterface2 = IGameStateListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    removeGameStateListener(iGameStateListenerAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    toggleGameDefaultFrameRate(z);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameMode(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public int[] getAvailableGameModes(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public boolean isAngleEnabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void notifyGraphicsEnvironmentSetup(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameState(String str, GameState gameState, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(gameState, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public GameModeInfo getGameModeInfo(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (GameModeInfo) parcelObtain2.readTypedObject(GameModeInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameServiceProvider(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void updateResolutionScalingFactor(String str, int i, float f, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeFloat(f);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public float getResolutionScalingFactor(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void updateCustomGameModeConfiguration(String str, GameModeConfiguration gameModeConfiguration, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(gameModeConfiguration, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void addGameModeListener(IGameModeListener iGameModeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGameModeListener);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void removeGameModeListener(IGameModeListener iGameModeListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGameModeListener);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void addGameStateListener(IGameStateListener iGameStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGameStateListener);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void removeGameStateListener(IGameStateListener iGameStateListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iGameStateListener);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void toggleGameDefaultFrameRate(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        protected void toggleGameDefaultFrameRate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_GAME_MODE, getCallingPid(), getCallingUid());
        }
    }
}
