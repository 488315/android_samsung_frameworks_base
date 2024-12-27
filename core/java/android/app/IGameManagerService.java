package android.app;

import android.Manifest;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IGameManagerService extends IInterface {
    public static final String DESCRIPTOR = "android.app.IGameManagerService";

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

    void updateCustomGameModeConfiguration(
            String str, GameModeConfiguration gameModeConfiguration, int i) throws RemoteException;

    void updateResolutionScalingFactor(String str, int i, float f, int i2) throws RemoteException;

    public static class Default implements IGameManagerService {
        @Override // android.app.IGameManagerService
        public int getGameMode(String packageName, int userId) throws RemoteException {
            return 0;
        }

        @Override // android.app.IGameManagerService
        public void setGameMode(String packageName, int gameMode, int userId)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public int[] getAvailableGameModes(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IGameManagerService
        public boolean isAngleEnabled(String packageName, int userId) throws RemoteException {
            return false;
        }

        @Override // android.app.IGameManagerService
        public void notifyGraphicsEnvironmentSetup(String packageName, int userId)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public void setGameState(String packageName, GameState gameState, int userId)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public GameModeInfo getGameModeInfo(String packageName, int userId) throws RemoteException {
            return null;
        }

        @Override // android.app.IGameManagerService
        public void setGameServiceProvider(String packageName) throws RemoteException {}

        @Override // android.app.IGameManagerService
        public void updateResolutionScalingFactor(
                String packageName, int gameMode, float scalingFactor, int userId)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public float getResolutionScalingFactor(String packageName, int gameMode, int userId)
                throws RemoteException {
            return 0.0f;
        }

        @Override // android.app.IGameManagerService
        public void updateCustomGameModeConfiguration(
                String packageName, GameModeConfiguration gameModeConfig, int userId)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public void addGameModeListener(IGameModeListener gameModeListener)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public void removeGameModeListener(IGameModeListener gameModeListener)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public void addGameStateListener(IGameStateListener gameStateListener)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public void removeGameStateListener(IGameStateListener gameStateListener)
                throws RemoteException {}

        @Override // android.app.IGameManagerService
        public void toggleGameDefaultFrameRate(boolean isEnabled) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IGameManagerService {
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

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, IGameManagerService.DESCRIPTOR);
            if (enforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = enforcer;
        }

        @Deprecated
        public Stub() {
            this(
                    PermissionEnforcer.fromContext(
                            ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static IGameManagerService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGameManagerService.DESCRIPTOR);
            if (iin != null && (iin instanceof IGameManagerService)) {
                return (IGameManagerService) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IGameManagerService.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGameManagerService.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getGameMode(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    setGameMode(_arg02, _arg12, _arg2);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result2 = getAvailableGameModes(_arg03, _arg13);
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 4:
                    String _arg04 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result3 = isAngleEnabled(_arg04, _arg14);
                    reply.writeNoException();
                    reply.writeBoolean(_result3);
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyGraphicsEnvironmentSetup(_arg05, _arg15);
                    reply.writeNoException();
                    return true;
                case 6:
                    String _arg06 = data.readString();
                    GameState _arg16 = (GameState) data.readTypedObject(GameState.CREATOR);
                    int _arg22 = data.readInt();
                    data.enforceNoDataAvail();
                    setGameState(_arg06, _arg16, _arg22);
                    reply.writeNoException();
                    return true;
                case 7:
                    String _arg07 = data.readString();
                    int _arg17 = data.readInt();
                    data.enforceNoDataAvail();
                    GameModeInfo _result4 = getGameModeInfo(_arg07, _arg17);
                    reply.writeNoException();
                    reply.writeTypedObject(_result4, 1);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    setGameServiceProvider(_arg08);
                    reply.writeNoException();
                    return true;
                case 9:
                    String _arg09 = data.readString();
                    int _arg18 = data.readInt();
                    float _arg23 = data.readFloat();
                    int _arg3 = data.readInt();
                    data.enforceNoDataAvail();
                    updateResolutionScalingFactor(_arg09, _arg18, _arg23, _arg3);
                    reply.writeNoException();
                    return true;
                case 10:
                    String _arg010 = data.readString();
                    int _arg19 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    float _result5 = getResolutionScalingFactor(_arg010, _arg19, _arg24);
                    reply.writeNoException();
                    reply.writeFloat(_result5);
                    return true;
                case 11:
                    String _arg011 = data.readString();
                    GameModeConfiguration _arg110 =
                            (GameModeConfiguration)
                                    data.readTypedObject(GameModeConfiguration.CREATOR);
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    updateCustomGameModeConfiguration(_arg011, _arg110, _arg25);
                    reply.writeNoException();
                    return true;
                case 12:
                    IGameModeListener _arg012 =
                            IGameModeListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addGameModeListener(_arg012);
                    reply.writeNoException();
                    return true;
                case 13:
                    IGameModeListener _arg013 =
                            IGameModeListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeGameModeListener(_arg013);
                    reply.writeNoException();
                    return true;
                case 14:
                    IGameStateListener _arg014 =
                            IGameStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    addGameStateListener(_arg014);
                    reply.writeNoException();
                    return true;
                case 15:
                    IGameStateListener _arg015 =
                            IGameStateListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    removeGameStateListener(_arg015);
                    reply.writeNoException();
                    return true;
                case 16:
                    boolean _arg016 = data.readBoolean();
                    data.enforceNoDataAvail();
                    toggleGameDefaultFrameRate(_arg016);
                    reply.writeNoException();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGameManagerService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameManagerService.DESCRIPTOR;
            }

            @Override // android.app.IGameManagerService
            public int getGameMode(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameMode(String packageName, int gameMode, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(gameMode);
                    _data.writeInt(userId);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public int[] getAvailableGameModes(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public boolean isAngleEnabled(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void notifyGraphicsEnvironmentSetup(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameState(String packageName, GameState gameState, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(gameState, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public GameModeInfo getGameModeInfo(String packageName, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    GameModeInfo _result =
                            (GameModeInfo) _reply.readTypedObject(GameModeInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void setGameServiceProvider(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void updateResolutionScalingFactor(
                    String packageName, int gameMode, float scalingFactor, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(gameMode);
                    _data.writeFloat(scalingFactor);
                    _data.writeInt(userId);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public float getResolutionScalingFactor(String packageName, int gameMode, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(gameMode);
                    _data.writeInt(userId);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    float _result = _reply.readFloat();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void updateCustomGameModeConfiguration(
                    String packageName, GameModeConfiguration gameModeConfig, int userId)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeTypedObject(gameModeConfig, 0);
                    _data.writeInt(userId);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void addGameModeListener(IGameModeListener gameModeListener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeStrongInterface(gameModeListener);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void removeGameModeListener(IGameModeListener gameModeListener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeStrongInterface(gameModeListener);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void addGameStateListener(IGameStateListener gameStateListener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeStrongInterface(gameStateListener);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void removeGameStateListener(IGameStateListener gameStateListener)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeStrongInterface(gameStateListener);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.app.IGameManagerService
            public void toggleGameDefaultFrameRate(boolean isEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeBoolean(isEnabled);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void toggleGameDefaultFrameRate_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.MANAGE_GAME_MODE, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 15;
        }
    }
}
