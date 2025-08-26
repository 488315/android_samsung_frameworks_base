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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ICustomFrequencyManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICustomFrequencyManager)) {
                return (ICustomFrequencyManager) iInterfaceQueryLocalInterface;
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
                String string = parcel.readString();
                String string2 = parcel.readString();
                parcel.enforceNoDataAvail();
                sendCommandToSSRM(string, string2);
            } else if (i == 64) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int batteryRemainingUsageTime = getBatteryRemainingUsageTime(i3);
                parcel2.writeNoException();
                parcel2.writeInt(batteryRemainingUsageTime);
            } else if (i == 92) {
                int i4 = parcel.readInt();
                int i5 = parcel.readInt();
                int i6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                requestGpis(i4, i5, i6);
            } else if (i == 50) {
                int i7 = parcel.readInt();
                parcel.enforceNoDataAvail();
                int ssrmStatus = getSsrmStatus(i7);
                parcel2.writeNoException();
                parcel2.writeInt(ssrmStatus);
            } else if (i != 51) {
                switch (i) {
                    case 13:
                        String string3 = parcel.readString();
                        parcel.enforceNoDataAvail();
                        requestMpParameterUpdate(string3);
                        parcel2.writeNoException();
                        break;
                    case 14:
                        int i8 = parcel.readInt();
                        int i9 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        requestCPUUpdate(i8, i9);
                        parcel2.writeNoException();
                        break;
                    case 15:
                        int i10 = parcel.readInt();
                        parcel.enforceNoDataAvail();
                        mpdUpdate(i10);
                        parcel2.writeNoException();
                        break;
                    default:
                        switch (i) {
                            case 73:
                                boolean z = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                setGamePowerSaving(z);
                                parcel2.writeNoException();
                                break;
                            case 74:
                                int i11 = parcel.readInt();
                                parcel.enforceNoDataAvail();
                                setGameFps(i11);
                                parcel2.writeNoException();
                                break;
                            case 75:
                                int gameThrottlingLevel = getGameThrottlingLevel();
                                parcel2.writeNoException();
                                parcel2.writeInt(gameThrottlingLevel);
                                break;
                            case 76:
                                boolean z2 = parcel.readBoolean();
                                parcel.enforceNoDataAvail();
                                setGameTurboMode(z2);
                                parcel2.writeNoException();
                                break;
                            case 77:
                                String string4 = parcel.readString();
                                String string5 = parcel.readString();
                                String string6 = parcel.readString();
                                parcel.enforceNoDataAvail();
                                setGameTouchParam(string4, string5, string6);
                                parcel2.writeNoException();
                                break;
                            case 78:
                                unsetGameTouchParam();
                                parcel2.writeNoException();
                                break;
                            default:
                                switch (i) {
                                    case 82:
                                        int[] iArrCreateIntArray = parcel.createIntArray();
                                        parcel.enforceNoDataAvail();
                                        CpuTrackerInfo processCpuUsage = getProcessCpuUsage(iArrCreateIntArray);
                                        parcel2.writeNoException();
                                        parcel2.writeTypedObject(processCpuUsage, 1);
                                        break;
                                    case 83:
                                        String string7 = parcel.readString();
                                        char c = (char) parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        String file = readFile(string7, c);
                                        parcel2.writeNoException();
                                        parcel2.writeString(file);
                                        break;
                                    case 84:
                                        int i12 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        int iAddDvfsLockAllowedUid = addDvfsLockAllowedUid(i12);
                                        parcel2.writeNoException();
                                        parcel2.writeInt(iAddDvfsLockAllowedUid);
                                        break;
                                    case 85:
                                        int i13 = parcel.readInt();
                                        parcel.enforceNoDataAvail();
                                        boolean zRemoveDvfsLockAllowedUid = removeDvfsLockAllowedUid(i13);
                                        parcel2.writeNoException();
                                        parcel2.writeBoolean(zRemoveDvfsLockAllowedUid);
                                        break;
                                    default:
                                        switch (i) {
                                            case 87:
                                                int i14 = parcel.readInt();
                                                int i15 = parcel.readInt();
                                                int i16 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                sendDrawingTid(i14, i15, i16);
                                                break;
                                            case 88:
                                                int i17 = parcel.readInt();
                                                int i18 = parcel.readInt();
                                                boolean z3 = parcel.readBoolean();
                                                String string8 = parcel.readString();
                                                parcel.enforceNoDataAvail();
                                                int iRequestFreezeSlowdown = requestFreezeSlowdown(i17, i18, z3, string8);
                                                parcel2.writeNoException();
                                                parcel2.writeInt(iRequestFreezeSlowdown);
                                                break;
                                            case 89:
                                                int i19 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                setFrozenTime(i19);
                                                parcel2.writeNoException();
                                                break;
                                            case 90:
                                                int i20 = parcel.readInt();
                                                parcel.enforceNoDataAvail();
                                                updateUsingCgroupVersion(i20);
                                                parcel2.writeNoException();
                                                break;
                                            default:
                                                switch (i) {
                                                    case 101:
                                                        int i21 = parcel.readInt();
                                                        int i22 = parcel.readInt();
                                                        String string9 = parcel.readString();
                                                        int i23 = parcel.readInt();
                                                        int[] iArrCreateIntArray2 = parcel.createIntArray();
                                                        parcel.enforceNoDataAvail();
                                                        acquire(i21, i22, string9, i23, iArrCreateIntArray2);
                                                        break;
                                                    case 102:
                                                        int i24 = parcel.readInt();
                                                        int i25 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        release(i24, i25);
                                                        break;
                                                    case 103:
                                                        int i26 = parcel.readInt();
                                                        int i27 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        int[] supportedFrequency = getSupportedFrequency(i26, i27);
                                                        parcel2.writeNoException();
                                                        parcel2.writeIntArray(supportedFrequency);
                                                        break;
                                                    case 104:
                                                        int i28 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean zCheckHintExist = checkHintExist(i28);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(zCheckHintExist);
                                                        break;
                                                    case 105:
                                                        int i29 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean zCheckResourceExist = checkResourceExist(i29);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(zCheckResourceExist);
                                                        break;
                                                    case 106:
                                                        int i30 = parcel.readInt();
                                                        String string10 = parcel.readString();
                                                        parcel.enforceNoDataAvail();
                                                        writeSysfs(i30, string10);
                                                        break;
                                                    case 107:
                                                        int i31 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        String sysfs = readSysfs(i31);
                                                        parcel2.writeNoException();
                                                        parcel2.writeString(sysfs);
                                                        break;
                                                    case 108:
                                                        int i32 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean zCheckSysfsIdExist = checkSysfsIdExist(i32);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(zCheckSysfsIdExist);
                                                        break;
                                                    case 109:
                                                        String string11 = parcel.readString();
                                                        int i33 = parcel.readInt();
                                                        int i34 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        restrictApp(string11, i33, i34);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 110:
                                                        disableGpisHint();
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 111:
                                                        boolean z4 = parcel.readBoolean();
                                                        parcel.enforceNoDataAvail();
                                                        setGpisHint(z4);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 112:
                                                        boolean z5 = parcel.readBoolean();
                                                        parcel.enforceNoDataAvail();
                                                        enableInteractionHint(z5);
                                                        parcel2.writeNoException();
                                                        break;
                                                    case 113:
                                                        List<String> preloadList = getPreloadList();
                                                        parcel2.writeNoException();
                                                        parcel2.writeStringList(preloadList);
                                                        break;
                                                    case 114:
                                                        int i35 = parcel.readInt();
                                                        int i36 = parcel.readInt();
                                                        int i37 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        sendTid(i35, i36, i37);
                                                        break;
                                                    case 115:
                                                        int i38 = parcel.readInt();
                                                        parcel.enforceNoDataAvail();
                                                        boolean zIsGameByGraphic = isGameByGraphic(i38);
                                                        parcel2.writeNoException();
                                                        parcel2.writeBoolean(zIsGameByGraphic);
                                                        break;
                                                    default:
                                                        return super.onTransact(i, parcel, parcel2, i2);
                                                }
                                        }
                                }
                        }
                }
            } else {
                String string12 = parcel.readString();
                int i39 = parcel.readInt();
                int i40 = parcel.readInt();
                parcel.enforceNoDataAvail();
                float[] fArrSupportVRTemperaturesInformation = supportVRTemperaturesInformation(string12, i39, i40);
                parcel2.writeNoException();
                parcel2.writeFloatArray(fArrSupportVRTemperaturesInformation);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void requestCPUUpdate(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void mpdUpdate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendCommandToSSRM(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(48, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getSsrmStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public float[] supportVRTemperaturesInformation(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getBatteryRemainingUsageTime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGamePowerSaving(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameFps(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int getGameThrottlingLevel() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameTurboMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGameTouchParam(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void unsetGameTouchParam() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public CpuTrackerInfo getProcessCpuUsage(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (CpuTrackerInfo) parcelObtain2.readTypedObject(CpuTrackerInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public String readFile(String str, char c) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(c);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int addDvfsLockAllowedUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean removeDvfsLockAllowedUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendDrawingTid(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(87, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int requestFreezeSlowdown(int i, int i2, boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setFrozenTime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void updateUsingCgroupVersion(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void requestGpis(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(92, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void acquire(int i, int i2, String str, int i3, int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeIntArray(iArr);
                    this.mRemote.transact(101, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void release(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(102, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public int[] getSupportedFrequency(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkHintExist(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkResourceExist(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void writeSysfs(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(106, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public String readSysfs(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean checkSysfsIdExist(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void restrictApp(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void disableGpisHint() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void setGpisHint(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void enableInteractionHint(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public List<String> getPreloadList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public void sendTid(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(114, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.ICustomFrequencyManager
            public boolean isGameByGraphic(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICustomFrequencyManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
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
