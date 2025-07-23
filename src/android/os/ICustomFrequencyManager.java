package android.os;

import java.util.List;

/* loaded from: classes3.dex */
public interface ICustomFrequencyManager extends IInterface {
    public static final String DESCRIPTOR = "android.os.ICustomFrequencyManager";
    public static final int GROUP_ANIMATION = 4;
    public static final int GROUP_FG_APP_HWUI = 3;
    public static final int GROUP_FG_APP_RENDER = 1;
    public static final int GROUP_FIRST = 1;
    public static final int GROUP_LAST = 4;
    public static final int GROUP_NUM = 4;
    public static final int GROUP_SF = 2;

    public static class Default implements ICustomFrequencyManager {
        @Override // android.os.ICustomFrequencyManager
        public void acquire(int i, int i2, String str, int i3, int[] iArr) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public int addDvfsLockAllowedUid(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean checkHintExist(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean checkResourceExist(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean checkSysfsIdExist(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public void disableGpisHint() throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void enableInteractionHint(boolean z) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public int getBatteryRemainingUsageTime(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public int getGameThrottlingLevel() throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public List<String> getPreloadList() throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public CpuTrackerInfo getProcessCpuUsage(int[] iArr) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public int getSsrmStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public int[] getSupportedFrequency(int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean isGameByGraphic(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public void mpdUpdate(int i) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public String readFile(String str, char c) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public String readSysfs(int i) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public void release(int i, int i2) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public boolean removeDvfsLockAllowedUid(int i) throws RemoteException {
            return false;
        }

        @Override // android.os.ICustomFrequencyManager
        public void requestCPUUpdate(int i, int i2) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public int requestFreezeSlowdown(int i, int i2, boolean z, String str) throws RemoteException {
            return 0;
        }

        @Override // android.os.ICustomFrequencyManager
        public void requestGpis(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void requestMpParameterUpdate(String str) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void restrictApp(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void sendCommandToSSRM(String str, String str2) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void sendDrawingTid(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void sendTid(int i, int i2, int i3) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void setFrozenTime(int i) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void setGameFps(int i) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void setGamePowerSaving(boolean z) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void setGameTouchParam(String str, String str2, String str3) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void setGameTurboMode(boolean z) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void setGpisHint(boolean z) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public float[] supportVRTemperaturesInformation(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // android.os.ICustomFrequencyManager
        public void unsetGameTouchParam() throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void updateUsingCgroupVersion(int i) throws RemoteException {
        }

        @Override // android.os.ICustomFrequencyManager
        public void writeSysfs(int i, String str) throws RemoteException {
        }
    }

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

    int requestFreezeSlowdown(int i, int i2, boolean z, String str) throws RemoteException;

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

    void updateUsingCgroupVersion(int i) throws RemoteException;

    void writeSysfs(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements ICustomFrequencyManager {
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
        static final int TRANSACTION_updateUsingCgroupVersion = 90;
        static final int TRANSACTION_writeSysfs = 106;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 114;
        }

        public Stub() {
            attachInterface(this, ICustomFrequencyManager.DESCRIPTOR);
        }

        public static ICustomFrequencyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICustomFrequencyManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICustomFrequencyManager)) {
                return (ICustomFrequencyManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 48) {
                return "sendCommandToSSRM";
            }
            if (i == 64) {
                return "getBatteryRemainingUsageTime";
            }
            if (i == 92) {
                return "requestGpis";
            }
            if (i == 50) {
                return "getSsrmStatus";
            }
            if (i != 51) {
                switch (i) {
                    case 13:
                        return "requestMpParameterUpdate";
                    case 14:
                        return "requestCPUUpdate";
                    case 15:
                        return "mpdUpdate";
                    default:
                        switch (i) {
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
                            default:
                                switch (i) {
                                    case 82:
                                        return "getProcessCpuUsage";
                                    case 83:
                                        return "readFile";
                                    case 84:
                                        return "addDvfsLockAllowedUid";
                                    case 85:
                                        return "removeDvfsLockAllowedUid";
                                    default:
                                        switch (i) {
                                            case 87:
                                                return "sendDrawingTid";
                                            case 88:
                                                return "requestFreezeSlowdown";
                                            case 89:
                                                return "setFrozenTime";
                                            case 90:
                                                return "updateUsingCgroupVersion";
                                            default:
                                                switch (i) {
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
                                }
                        }
                }
            }
            return "supportVRTemperaturesInformation";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICustomFrequencyManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICustomFrequencyManager.DESCRIPTOR);
                return true;
            }
            if (i == 48) {
                String readString = parcel.readString();
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                sendCommandToSSRM(readString, readString2);
            } else if (i == 64) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                int batteryRemainingUsageTime = getBatteryRemainingUsageTime(readInt);
                parcel2.writeNoException();
                parcel2.writeInt(batteryRemainingUsageTime);
            } else if (i == 92) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                int readInt4 = parcel.readInt();
                parcel.enforceNoDataAvail();
                requestGpis(readInt2, readInt3, readInt4);
            } else if (i == 50) {
                int readInt5 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int ssrmStatus = getSsrmStatus(readInt5);
                parcel2.writeNoException();
                parcel2.writeInt(ssrmStatus);
            } else if (i != 51) {
                switch (i) {
                    case 13:
                        String readString3 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        requestMpParameterUpdate(readString3);
                        parcel2.writeNoException();
                        break;
                    case 14:
                        int readInt6 = parcel.readInt();
                        int readInt7 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        requestCPUUpdate(readInt6, readInt7);
                        parcel2.writeNoException();
                        break;
                    case 15:
                        int readInt8 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        mpdUpdate(readInt8);
                        parcel2.writeNoException();
                        break;
                    default:
                        switch (i) {
                            case 73:
                                boolean readBoolean = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                setGamePowerSaving(readBoolean);
                                parcel2.writeNoException();
                                break;
                            case 74:
                                int readInt9 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                setGameFps(readInt9);
                                parcel2.writeNoException();
                                break;
                            case 75:
                                int gameThrottlingLevel = getGameThrottlingLevel();
                                parcel2.writeNoException();
                                parcel2.writeInt(gameThrottlingLevel);
                                break;
                            case 76:
                                boolean readBoolean2 = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                setGameTurboMode(readBoolean2);
                                parcel2.writeNoException();
                                break;
                            case 77:
                                String readString4 = parcel.readString();
                                String readString5 = parcel.readString();
                                String readString6 = parcel.readString();
                                parcel.enforceNoDataAvail();
                                setGameTouchParam(readString4, readString5, readString6);
                                parcel2.writeNoException();
                                break;
                            case 78:
                                unsetGameTouchParam();
                                parcel2.writeNoException();
                                break;
                            default:
                                switch (i) {
                                    case 82:
                                        int[] createIntArray = parcel.createIntArray();
                                        parcel.enforceNoDataAvail();
                                        CpuTrackerInfo processCpuUsage = getProcessCpuUsage(createIntArray);
                                        parcel2.writeNoException();
                                        parcel2.writeTypedObject(processCpuUsage, 1);
                                        break;
                                    case 83:
                                        String readString7 = parcel.readString();
                                        char readInt10 = (char) parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        String readFile = readFile(readString7, readInt10);
                                        parcel2.writeNoException();
                                        parcel2.writeString(readFile);
                                        break;
                                    case 84:
                                        int readInt11 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        int addDvfsLockAllowedUid = addDvfsLockAllowedUid(readInt11);
                                        parcel2.writeNoException();
                                        parcel2.writeInt(addDvfsLockAllowedUid);
                                        break;
                                    case 85:
                                        int readInt12 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        boolean removeDvfsLockAllowedUid = removeDvfsLockAllowedUid(readInt12);
                                        parcel2.writeNoException();
                                        parcel2.writeBoolean(removeDvfsLockAllowedUid);
                                        break;
                                    default:
                                        switch (i) {
                                            case 87:
                                                int readInt13 = parcel.readInt();
                                                int readInt14 = parcel.readInt();
                                                int readInt15 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                sendDrawingTid(readInt13, readInt14, readInt15);
                                                break;
                                            case 88:
                                                int readInt16 = parcel.readInt();
                                                int readInt17 = parcel.readInt();
                                                boolean readBoolean3 = parcel.readBoolean();
                                                String readString8 = parcel.readString();
                                                parcel.enforceNoDataAvail();
                                                int requestFreezeSlowdown = requestFreezeSlowdown(readInt16, readInt17, readBoolean3, readString8);
                                                parcel2.writeNoException();
                                                parcel2.writeInt(requestFreezeSlowdown);
                                                break;
                                            case 89:
                                                int readInt18 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                setFrozenTime(readInt18);
                                                parcel2.writeNoException();
                                                break;
                                            case 90:
                                                int readInt19 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                updateUsingCgroupVersion(readInt19);
                                                parcel2.writeNoException();
                                                break;
                                            default:
                                                switch (i) {
                                                    case 101:
                                                        int readInt20 = parcel.readInt();
                                                        int readInt21 = parcel.readInt();
                                                        String readString9 = parcel.readString();
                                                        int readInt22 = parcel.readInt();
                                                        int[] createIntArray2 = parcel.createIntArray();
                                                        parcel.enforceNoDataAvail();
                                                        acquire(readInt20, readInt21, readString9, readInt22, createIntArray2);
                                                        break;
                                                    case 102:
                                                        int readInt23 = parcel.readInt();
                                                        int readInt24 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        release(readInt23, readInt24);
                                                        break;
                                                    case 103:
                                                        int readInt25 = parcel.readInt();
                                                        int readInt26 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        int[] supportedFrequency = getSupportedFrequency(readInt25, readInt26);
                                                        parcel2.writeNoException();
                                                        parcel2.writeIntArray(supportedFrequency);
                                                        break;
                                                    case 104:
                                                        int readInt27 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean checkHintExist = checkHintExist(readInt27);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(checkHintExist);
                                                        break;
                                                    case 105:
                                                        int readInt28 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean checkResourceExist = checkResourceExist(readInt28);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(checkResourceExist);
                                                        break;
                                                    case 106:
                                                        int readInt29 = parcel.readInt();
                                                        String readString10 = parcel.readString();
                                                        parcel.enforceNoDataAvail();
                                                        writeSysfs(readInt29, readString10);
                                                        break;
                                                    case 107:
                                                        int readInt30 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        String readSysfs = readSysfs(readInt30);
                                                        parcel2.writeNoException();
                                                        parcel2.writeString(readSysfs);
                                                        break;
                                                    case 108:
                                                        int readInt31 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean checkSysfsIdExist = checkSysfsIdExist(readInt31);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(checkSysfsIdExist);
                                                        break;
                                                    case 109:
                                                        String readString11 = parcel.readString();
                                                        int readInt32 = parcel.readInt();
                                                        int readInt33 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        restrictApp(readString11, readInt32, readInt33);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 110:
                                                        disableGpisHint();
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 111:
                                                        boolean readBoolean4 = parcel.readBoolean();
                                                        parcel.enforceNoDataAvail();
                                                        setGpisHint(readBoolean4);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 112:
                                                        boolean readBoolean5 = parcel.readBoolean();
                                                        parcel.enforceNoDataAvail();
                                                        enableInteractionHint(readBoolean5);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 113:
                                                        List<String> preloadList = getPreloadList();
                                                        parcel2.writeNoException();
                                                        parcel2.writeStringList(preloadList);
                                                        break;
                                                    case 114:
                                                        int readInt34 = parcel.readInt();
                                                        int readInt35 = parcel.readInt();
                                                        int readInt36 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        sendTid(readInt34, readInt35, readInt36);
                                                        break;
                                                    case 115:
                                                        int readInt37 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean isGameByGraphic = isGameByGraphic(readInt37);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(isGameByGraphic);
                                                        break;
                                                    default:
                                                        return super.onTransact(i, parcel, parcel2, i2);
                                                }
                                        }
                                }
                        }
                }
            } else {
                String readString12 = parcel.readString();
                int readInt38 = parcel.readInt();
                int readInt39 = parcel.readInt();
                parcel.enforceNoDataAvail();
                float[] supportVRTemperaturesInformation = supportVRTemperaturesInformation(readString12, readInt38, readInt39);
                parcel2.writeNoException();
                parcel2.writeFloatArray(supportVRTemperaturesInformation);
            }
            return true;
        }

        private static class Proxy implements ICustomFrequencyManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICustomFrequencyManager.DESCRIPTOR;
            }

            @Override // android.os.ICustomFrequencyManager
            public void requestMpParameterUpdate(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void requestCPUUpdate(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void mpdUpdate(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendCommandToSSRM(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(48, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getSsrmStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public float[] supportVRTemperaturesInformation(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getBatteryRemainingUsageTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGamePowerSaving(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameFps(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getGameThrottlingLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameTurboMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameTouchParam(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void unsetGameTouchParam() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public CpuTrackerInfo getProcessCpuUsage(int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                    return (CpuTrackerInfo) obtain2.readTypedObject(CpuTrackerInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public String readFile(String str, char c) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(c);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int addDvfsLockAllowedUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean removeDvfsLockAllowedUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendDrawingTid(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(87, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int requestFreezeSlowdown(int i, int i2, boolean z, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeBoolean(z);
                    obtain.writeString(str);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setFrozenTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void updateUsingCgroupVersion(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void requestGpis(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(92, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void acquire(int i, int i2, String str, int i3, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str);
                    obtain.writeInt(i3);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(101, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void release(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(102, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int[] getSupportedFrequency(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkHintExist(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkResourceExist(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void writeSysfs(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(106, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public String readSysfs(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkSysfsIdExist(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void restrictApp(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void disableGpisHint() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGpisHint(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void enableInteractionHint(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public List<String> getPreloadList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendTid(int i, int i2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(114, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean isGameByGraphic(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(115, obtain, obtain2, 0);
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
