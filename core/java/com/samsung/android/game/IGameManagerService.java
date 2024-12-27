package com.samsung.android.game;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.game.IGameManagerCallback;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public interface IGameManagerService extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.game.IGameManagerService";

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

    public static class Default implements IGameManagerService {
        @Override // com.samsung.android.game.IGameManagerService
        public int identifyGamePackage(String pkgName) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public int identifyForegroundApp() throws RemoteException {
            return 0;
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
        public boolean registerCallback(IGameManagerCallback callback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean unregisterCallback(IGameManagerCallback callback) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public String getVersion() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public String requestWithJson(String command, String jsonParam) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public String getTopActivityName() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean setPackageConfigurations(List<SemPackageConfiguration> packageConfigurations) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean setPerformanceMode(int tunePerformanceMode, String callerPackageName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean setTargetFrameRate(IBinder surfaceFlinger, int fps) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean disableVrrControl(String pkgName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public boolean enableVrrControl(String pkgName) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public void syncGameList(Map pkgMap) throws RemoteException {
        }

        @Override // com.samsung.android.game.IGameManagerService
        public SemPackageConfiguration getPackageConfiguration(String packageName) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public List<SemPackageConfiguration> getPackageConfigurations() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerService
        public void notifyAppCreated(String packageName, int userId) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

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

        public Stub() {
            attachInterface(this, IGameManagerService.DESCRIPTOR);
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
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
                    data.enforceNoDataAvail();
                    int _result = identifyGamePackage(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    int _result2 = identifyForegroundApp();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 3:
                    String _result3 = getForegroundApp();
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case 4:
                    List<String> _result4 = getGameList();
                    reply.writeNoException();
                    reply.writeStringList(_result4);
                    return true;
                case 5:
                    IGameManagerCallback _arg02 = IGameManagerCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result5 = registerCallback(_arg02);
                    reply.writeNoException();
                    reply.writeBoolean(_result5);
                    return true;
                case 6:
                    IGameManagerCallback _arg03 = IGameManagerCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    boolean _result6 = unregisterCallback(_arg03);
                    reply.writeNoException();
                    reply.writeBoolean(_result6);
                    return true;
                case 7:
                    String _result7 = getVersion();
                    reply.writeNoException();
                    reply.writeString(_result7);
                    return true;
                case 8:
                    String _arg04 = data.readString();
                    String _arg1 = data.readString();
                    data.enforceNoDataAvail();
                    String _result8 = requestWithJson(_arg04, _arg1);
                    reply.writeNoException();
                    reply.writeString(_result8);
                    return true;
                case 9:
                    String _result9 = getTopActivityName();
                    reply.writeNoException();
                    reply.writeString(_result9);
                    return true;
                case 10:
                    List<SemPackageConfiguration> _arg05 = data.createTypedArrayList(SemPackageConfiguration.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result10 = setPackageConfigurations(_arg05);
                    reply.writeNoException();
                    reply.writeBoolean(_result10);
                    return true;
                case 11:
                    int _arg06 = data.readInt();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result11 = setPerformanceMode(_arg06, _arg12);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 12:
                    IBinder _arg07 = data.readStrongBinder();
                    int _arg13 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result12 = setTargetFrameRate(_arg07, _arg13);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 13:
                    String _arg08 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result13 = disableVrrControl(_arg08);
                    reply.writeNoException();
                    reply.writeBoolean(_result13);
                    return true;
                case 14:
                    String _arg09 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result14 = enableVrrControl(_arg09);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 15:
                    ClassLoader cl = getClass().getClassLoader();
                    Map _arg010 = data.readHashMap(cl);
                    data.enforceNoDataAvail();
                    syncGameList(_arg010);
                    reply.writeNoException();
                    return true;
                case 16:
                    String _arg011 = data.readString();
                    data.enforceNoDataAvail();
                    SemPackageConfiguration _result15 = getPackageConfiguration(_arg011);
                    reply.writeNoException();
                    reply.writeTypedObject(_result15, 1);
                    return true;
                case 17:
                    List<SemPackageConfiguration> _result16 = getPackageConfigurations();
                    reply.writeNoException();
                    reply.writeTypedList(_result16, 1);
                    return true;
                case 18:
                    String _arg012 = data.readString();
                    int _arg14 = data.readInt();
                    data.enforceNoDataAvail();
                    notifyAppCreated(_arg012, _arg14);
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

            @Override // com.samsung.android.game.IGameManagerService
            public int identifyGamePackage(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public int identifyForegroundApp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getForegroundApp() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public List<String> getGameList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean registerCallback(IGameManagerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean unregisterCallback(IGameManagerCallback callback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeStrongInterface(callback);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getVersion() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String requestWithJson(String command, String jsonParam) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(command);
                    _data.writeString(jsonParam);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public String getTopActivityName() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setPackageConfigurations(List<SemPackageConfiguration> packageConfigurations) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeTypedList(packageConfigurations, 0);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setPerformanceMode(int tunePerformanceMode, String callerPackageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeInt(tunePerformanceMode);
                    _data.writeString(callerPackageName);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean setTargetFrameRate(IBinder surfaceFlinger, int fps) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeStrongBinder(surfaceFlinger);
                    _data.writeInt(fps);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean disableVrrControl(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public boolean enableVrrControl(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public void syncGameList(Map pkgMap) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeMap(pkgMap);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public SemPackageConfiguration getPackageConfiguration(String packageName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    SemPackageConfiguration _result = (SemPackageConfiguration) _reply.readTypedObject(SemPackageConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public List<SemPackageConfiguration> getPackageConfigurations() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    List<SemPackageConfiguration> _result = _reply.createTypedArrayList(SemPackageConfiguration.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerService
            public void notifyAppCreated(String packageName, int userId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IGameManagerService.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 17;
        }
    }
}
