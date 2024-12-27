package android.os;

import java.util.List;

public interface ICustomFrequencyManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.ICustomFrequencyManager";
    public static final int GROUP_ANIMATION = 4;
    public static final int GROUP_FG_APP_HWUI = 3;
    public static final int GROUP_FG_APP_RENDER = 1;
    public static final int GROUP_FIRST = 1;
    public static final int GROUP_LAST = 4;
    public static final int GROUP_NUM = 4;
    public static final int GROUP_SF = 2;

    void acquire(int i, int i2, String str, int i3, int[] iArr) throws RemoteException;

    int addDvfsLockAllowedUid(int i) throws RemoteException;

    boolean checkHintExist(int i) throws RemoteException;

    boolean checkResourceExist(int i) throws RemoteException;

    boolean checkSysfsIdExist(int i) throws RemoteException;

    void disableGpisHint() throws RemoteException;

    void enableInteractionHint(boolean z) throws RemoteException;

    int getBatteryRemainingUsageTime(int i) throws RemoteException;

    int getGameThrottlingLevel() throws RemoteException;

    List<String> getPreloadList() throws RemoteException;

    CpuTrackerInfo getProcessCpuUsage(int[] iArr) throws RemoteException;

    int getSsrmStatus(int i) throws RemoteException;

    int[] getSupportedFrequency(int i, int i2) throws RemoteException;

    boolean isGameByGraphic(int i) throws RemoteException;

    void mpdUpdate(int i) throws RemoteException;

    String readFile(String str, char c) throws RemoteException;

    String readSysfs(int i) throws RemoteException;

    void release(int i, int i2) throws RemoteException;

    boolean removeDvfsLockAllowedUid(int i) throws RemoteException;

    void requestCPUUpdate(int i, int i2) throws RemoteException;

    int requestFreezeSlowdown(int i, boolean z, String str) throws RemoteException;

    void requestGpis(int i, int i2, int i3) throws RemoteException;

    void requestMpParameterUpdate(String str) throws RemoteException;

    void restrictApp(String str, int i, int i2) throws RemoteException;

    void sendCommandToSSRM(String str, String str2) throws RemoteException;

    void sendDrawingTid(int i, int i2, int i3) throws RemoteException;

    void sendTid(int i, int i2, int i3) throws RemoteException;

    void setFrozenTime(int i) throws RemoteException;

    void setGameFps(int i) throws RemoteException;

    void setGamePowerSaving(boolean z) throws RemoteException;

    void setGameTouchParam(String str, String str2, String str3) throws RemoteException;

    void setGameTurboMode(boolean z) throws RemoteException;

    void setGpisHint(boolean z) throws RemoteException;

    float[] supportVRTemperaturesInformation(String str, int i, int i2) throws RemoteException;

    void unsetGameTouchParam() throws RemoteException;

    void writeSysfs(int i, String str) throws RemoteException;

    public static class Default implements ICustomFrequencyManager {
        @Override // android.os.ICustomFrequencyManager
        public void requestMpParameterUpdate(String command) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void requestCPUUpdate(int cpu, int enable) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void mpdUpdate(int mpEnable) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void sendCommandToSSRM(String type, String value) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public int getSsrmStatus(int type) throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public float[] supportVRTemperaturesInformation(String callingPackage, int type, int source)
                throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public int getBatteryRemainingUsageTime(int mode) throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public void setGamePowerSaving(boolean enabled) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void setGameFps(int level) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public int getGameThrottlingLevel() throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public void setGameTurboMode(boolean enabled) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void setGameTouchParam(String level, String head, String tail)
                throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void unsetGameTouchParam() throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public CpuTrackerInfo getProcessCpuUsage(int[] tarPids) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public String readFile(String file, char endChar) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public int addDvfsLockAllowedUid(int uid) throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean removeDvfsLockAllowedUid(int uid) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public void sendDrawingTid(int pid, int tid, int groupId) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public int requestFreezeSlowdown(int pid, boolean isEnabled, String type)
                throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public void setFrozenTime(int timeMs) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void requestGpis(int groupId, int boostLevel, int timeout) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void acquire(int pid, int token, String procName, int hint, int[] list)
                throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void release(int pid, int token) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public int[] getSupportedFrequency(int type, int level) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean checkHintExist(int hint) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean checkResourceExist(int resourceId) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public void writeSysfs(int sysfsId, String value) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public String readSysfs(int sysfsId) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean checkSysfsIdExist(int sysfsId) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public void restrictApp(String packageName, int userId, int restrictReason)
                throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void disableGpisHint() throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void setGpisHint(boolean flag) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public void enableInteractionHint(boolean enable) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public List<String> getPreloadList() throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public void sendTid(int pid, int tid, int type) throws RemoteException {}

        @Override // android.os.ICustomFrequencyManager
        public boolean isGameByGraphic(int pid) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ICustomFrequencyManager {
        static final int TRANSACTION_acquire = 101;
        static final int TRANSACTION_addDvfsLockAllowedUid = 84;
        static final int TRANSACTION_checkHintExist = 104;
        static final int TRANSACTION_checkResourceExist = 105;
        static final int TRANSACTION_checkSysfsIdExist = 108;
        static final int TRANSACTION_disableGpisHint = 110;
        static final int TRANSACTION_enableInteractionHint = 112;
        static final int TRANSACTION_getBatteryRemainingUsageTime = 64;
        static final int TRANSACTION_getGameThrottlingLevel = 75;
        static final int TRANSACTION_getPreloadList = 113;
        static final int TRANSACTION_getProcessCpuUsage = 82;
        static final int TRANSACTION_getSsrmStatus = 50;
        static final int TRANSACTION_getSupportedFrequency = 103;
        static final int TRANSACTION_isGameByGraphic = 115;
        static final int TRANSACTION_mpdUpdate = 15;
        static final int TRANSACTION_readFile = 83;
        static final int TRANSACTION_readSysfs = 107;
        static final int TRANSACTION_release = 102;
        static final int TRANSACTION_removeDvfsLockAllowedUid = 85;
        static final int TRANSACTION_requestCPUUpdate = 14;
        static final int TRANSACTION_requestFreezeSlowdown = 88;
        static final int TRANSACTION_requestGpis = 92;
        static final int TRANSACTION_requestMpParameterUpdate = 13;
        static final int TRANSACTION_restrictApp = 109;
        static final int TRANSACTION_sendCommandToSSRM = 48;
        static final int TRANSACTION_sendDrawingTid = 87;
        static final int TRANSACTION_sendTid = 114;
        static final int TRANSACTION_setFrozenTime = 89;
        static final int TRANSACTION_setGameFps = 74;
        static final int TRANSACTION_setGamePowerSaving = 73;
        static final int TRANSACTION_setGameTouchParam = 77;
        static final int TRANSACTION_setGameTurboMode = 76;
        static final int TRANSACTION_setGpisHint = 111;
        static final int TRANSACTION_supportVRTemperaturesInformation = 51;
        static final int TRANSACTION_unsetGameTouchParam = 78;
        static final int TRANSACTION_writeSysfs = 106;

        public Stub() {
            attachInterface(this, ICustomFrequencyManager.DESCRIPTOR);
        }

        public static ICustomFrequencyManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICustomFrequencyManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ICustomFrequencyManager)) {
                return (ICustomFrequencyManager) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 13:
                    return "requestMpParameterUpdate";
                case 14:
                    return "requestCPUUpdate";
                case 15:
                    return "mpdUpdate";
                case 48:
                    return "sendCommandToSSRM";
                case 50:
                    return "getSsrmStatus";
                case 51:
                    return "supportVRTemperaturesInformation";
                case 64:
                    return "getBatteryRemainingUsageTime";
                case 73:
                    return "setGamePowerSaving";
                case 74:
                    return "setGameFps";
                case 75:
                    return "getGameThrottlingLevel";
                case 76:
                    return "setGameTurboMode";
                case 77:
                    return "setGameTouchParam";
                case 78:
                    return "unsetGameTouchParam";
                case 82:
                    return "getProcessCpuUsage";
                case 83:
                    return "readFile";
                case 84:
                    return "addDvfsLockAllowedUid";
                case 85:
                    return "removeDvfsLockAllowedUid";
                case 87:
                    return "sendDrawingTid";
                case 88:
                    return "requestFreezeSlowdown";
                case 89:
                    return "setFrozenTime";
                case 92:
                    return "requestGpis";
                case 101:
                    return "acquire";
                case 102:
                    return "release";
                case 103:
                    return "getSupportedFrequency";
                case 104:
                    return "checkHintExist";
                case 105:
                    return "checkResourceExist";
                case 106:
                    return "writeSysfs";
                case 107:
                    return "readSysfs";
                case 108:
                    return "checkSysfsIdExist";
                case 109:
                    return "restrictApp";
                case 110:
                    return "disableGpisHint";
                case 111:
                    return "setGpisHint";
                case 112:
                    return "enableInteractionHint";
                case 113:
                    return "getPreloadList";
                case 114:
                    return "sendTid";
                case 115:
                    return "isGameByGraphic";
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
                data.enforceInterface(ICustomFrequencyManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICustomFrequencyManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 13:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    requestMpParameterUpdate(_arg0);
                    reply.writeNoException();
                    return true;
                case 14:
                    int _arg02 = data.readInt();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    requestCPUUpdate(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 15:
                    int _arg03 = data.readInt();
                    data.enforceNoDataAvail();
                    mpdUpdate(_arg03);
                    reply.writeNoException();
                    return true;
                case 48:
                    String _arg04 = data.readString();
                    String _arg12 = data.readString();
                    data.enforceNoDataAvail();
                    sendCommandToSSRM(_arg04, _arg12);
                    return true;
                case 50:
                    int _arg05 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getSsrmStatus(_arg05);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 51:
                    String _arg06 = data.readString();
                    int _arg13 = data.readInt();
                    int _arg2 = data.readInt();
                    data.enforceNoDataAvail();
                    float[] _result2 = supportVRTemperaturesInformation(_arg06, _arg13, _arg2);
                    reply.writeNoException();
                    reply.writeFloatArray(_result2);
                    return true;
                case 64:
                    int _arg07 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result3 = getBatteryRemainingUsageTime(_arg07);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 73:
                    boolean _arg08 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setGamePowerSaving(_arg08);
                    reply.writeNoException();
                    return true;
                case 74:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    setGameFps(_arg09);
                    reply.writeNoException();
                    return true;
                case 75:
                    int _result4 = getGameThrottlingLevel();
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 76:
                    boolean _arg010 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setGameTurboMode(_arg010);
                    reply.writeNoException();
                    return true;
                case 77:
                    String _arg011 = data.readString();
                    String _arg14 = data.readString();
                    String _arg22 = data.readString();
                    data.enforceNoDataAvail();
                    setGameTouchParam(_arg011, _arg14, _arg22);
                    reply.writeNoException();
                    return true;
                case 78:
                    unsetGameTouchParam();
                    reply.writeNoException();
                    return true;
                case 82:
                    int[] _arg012 = data.createIntArray();
                    data.enforceNoDataAvail();
                    CpuTrackerInfo _result5 = getProcessCpuUsage(_arg012);
                    reply.writeNoException();
                    reply.writeTypedObject(_result5, 1);
                    return true;
                case 83:
                    String _arg013 = data.readString();
                    char _arg15 = (char) data.readInt();
                    data.enforceNoDataAvail();
                    String _result6 = readFile(_arg013, _arg15);
                    reply.writeNoException();
                    reply.writeString(_result6);
                    return true;
                case 84:
                    int _arg014 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result7 = addDvfsLockAllowedUid(_arg014);
                    reply.writeNoException();
                    reply.writeInt(_result7);
                    return true;
                case 85:
                    int _arg015 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result8 = removeDvfsLockAllowedUid(_arg015);
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 87:
                    int _arg016 = data.readInt();
                    int _arg16 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    sendDrawingTid(_arg016, _arg16, _arg23);
                    return true;
                case 88:
                    int _arg017 = data.readInt();
                    boolean _arg17 = data.readBoolean();
                    String _arg24 = data.readString();
                    data.enforceNoDataAvail();
                    int _result9 = requestFreezeSlowdown(_arg017, _arg17, _arg24);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 89:
                    int _arg018 = data.readInt();
                    data.enforceNoDataAvail();
                    setFrozenTime(_arg018);
                    reply.writeNoException();
                    return true;
                case 92:
                    int _arg019 = data.readInt();
                    int _arg18 = data.readInt();
                    int _arg25 = data.readInt();
                    data.enforceNoDataAvail();
                    requestGpis(_arg019, _arg18, _arg25);
                    return true;
                case 101:
                    int _arg020 = data.readInt();
                    int _arg19 = data.readInt();
                    String _arg26 = data.readString();
                    int _arg3 = data.readInt();
                    int[] _arg4 = data.createIntArray();
                    data.enforceNoDataAvail();
                    acquire(_arg020, _arg19, _arg26, _arg3, _arg4);
                    return true;
                case 102:
                    int _arg021 = data.readInt();
                    int _arg110 = data.readInt();
                    data.enforceNoDataAvail();
                    release(_arg021, _arg110);
                    return true;
                case 103:
                    int _arg022 = data.readInt();
                    int _arg111 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result10 = getSupportedFrequency(_arg022, _arg111);
                    reply.writeNoException();
                    reply.writeIntArray(_result10);
                    return true;
                case 104:
                    int _arg023 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result11 = checkHintExist(_arg023);
                    reply.writeNoException();
                    reply.writeBoolean(_result11);
                    return true;
                case 105:
                    int _arg024 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result12 = checkResourceExist(_arg024);
                    reply.writeNoException();
                    reply.writeBoolean(_result12);
                    return true;
                case 106:
                    int _arg025 = data.readInt();
                    String _arg112 = data.readString();
                    data.enforceNoDataAvail();
                    writeSysfs(_arg025, _arg112);
                    return true;
                case 107:
                    int _arg026 = data.readInt();
                    data.enforceNoDataAvail();
                    String _result13 = readSysfs(_arg026);
                    reply.writeNoException();
                    reply.writeString(_result13);
                    return true;
                case 108:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result14 = checkSysfsIdExist(_arg027);
                    reply.writeNoException();
                    reply.writeBoolean(_result14);
                    return true;
                case 109:
                    String _arg028 = data.readString();
                    int _arg113 = data.readInt();
                    int _arg27 = data.readInt();
                    data.enforceNoDataAvail();
                    restrictApp(_arg028, _arg113, _arg27);
                    reply.writeNoException();
                    return true;
                case 110:
                    disableGpisHint();
                    reply.writeNoException();
                    return true;
                case 111:
                    boolean _arg029 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setGpisHint(_arg029);
                    reply.writeNoException();
                    return true;
                case 112:
                    boolean _arg030 = data.readBoolean();
                    data.enforceNoDataAvail();
                    enableInteractionHint(_arg030);
                    reply.writeNoException();
                    return true;
                case 113:
                    List<String> _result15 = getPreloadList();
                    reply.writeNoException();
                    reply.writeStringList(_result15);
                    return true;
                case 114:
                    int _arg031 = data.readInt();
                    int _arg114 = data.readInt();
                    int _arg28 = data.readInt();
                    data.enforceNoDataAvail();
                    sendTid(_arg031, _arg114, _arg28);
                    return true;
                case 115:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result16 = isGameByGraphic(_arg032);
                    reply.writeNoException();
                    reply.writeBoolean(_result16);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICustomFrequencyManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICustomFrequencyManager.DESCRIPTOR;
            }

            @Override // android.os.ICustomFrequencyManager
            public void requestMpParameterUpdate(String command) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeString(command);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void requestCPUUpdate(int cpu, int enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(cpu);
                    _data.writeInt(enable);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void mpdUpdate(int mpEnable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(mpEnable);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendCommandToSSRM(String type, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeString(value);
                    this.mRemote.transact(48, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getSsrmStatus(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public float[] supportVRTemperaturesInformation(
                    String callingPackage, int type, int source) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(type);
                    _data.writeInt(source);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    float[] _result = _reply.createFloatArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getBatteryRemainingUsageTime(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGamePowerSaving(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameFps(int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(level);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getGameThrottlingLevel() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameTurboMode(boolean enabled) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeBoolean(enabled);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameTouchParam(String level, String head, String tail)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeString(level);
                    _data.writeString(head);
                    _data.writeString(tail);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void unsetGameTouchParam() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public CpuTrackerInfo getProcessCpuUsage(int[] tarPids) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeIntArray(tarPids);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    CpuTrackerInfo _result =
                            (CpuTrackerInfo) _reply.readTypedObject(CpuTrackerInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public String readFile(String file, char endChar) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeString(file);
                    _data.writeInt(endChar);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int addDvfsLockAllowedUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean removeDvfsLockAllowedUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendDrawingTid(int pid, int tid, int groupId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeInt(tid);
                    _data.writeInt(groupId);
                    this.mRemote.transact(87, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int requestFreezeSlowdown(int pid, boolean isEnabled, String type)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeBoolean(isEnabled);
                    _data.writeString(type);
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setFrozenTime(int timeMs) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(timeMs);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void requestGpis(int groupId, int boostLevel, int timeout)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(groupId);
                    _data.writeInt(boostLevel);
                    _data.writeInt(timeout);
                    this.mRemote.transact(92, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void acquire(int pid, int token, String procName, int hint, int[] list)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeInt(token);
                    _data.writeString(procName);
                    _data.writeInt(hint);
                    _data.writeIntArray(list);
                    this.mRemote.transact(101, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void release(int pid, int token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeInt(token);
                    this.mRemote.transact(102, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int[] getSupportedFrequency(int type, int level) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(level);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkHintExist(int hint) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(hint);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkResourceExist(int resourceId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(resourceId);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void writeSysfs(int sysfsId, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(sysfsId);
                    _data.writeString(value);
                    this.mRemote.transact(106, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public String readSysfs(int sysfsId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(sysfsId);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkSysfsIdExist(int sysfsId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(sysfsId);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void restrictApp(String packageName, int userId, int restrictReason)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeString(packageName);
                    _data.writeInt(userId);
                    _data.writeInt(restrictReason);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void disableGpisHint() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(110, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGpisHint(boolean flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeBoolean(flag);
                    this.mRemote.transact(111, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void enableInteractionHint(boolean enable) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeBoolean(enable);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public List<String> getPreloadList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(113, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendTid(int pid, int tid, int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(pid);
                    _data.writeInt(tid);
                    _data.writeInt(type);
                    this.mRemote.transact(114, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean isGameByGraphic(int pid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    _data.writeInt(pid);
                    this.mRemote.transact(115, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 114;
        }
    }
}
