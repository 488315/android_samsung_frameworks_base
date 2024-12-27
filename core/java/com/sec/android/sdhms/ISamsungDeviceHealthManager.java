package com.sec.android.sdhms;

import android.Manifest;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.os.Binder;
import android.os.Bundle;
import android.os.CoolingDevice;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IThermalEventListener;
import android.os.Parcel;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.Temperature;

import com.samsung.android.sdhms.SemBatteryEventHistory;
import com.samsung.android.sdhms.SemBatteryStats;
import com.samsung.android.sdhms.SemNetworkUsageStats;
import com.samsung.android.sdhms.SemProcessUsageStats;
import com.samsung.android.sdhms.SemThermalStats;

import java.util.List;

/* loaded from: classes6.dex */
public interface ISamsungDeviceHealthManager extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.sdhms.ISamsungDeviceHealthManager";

    void acquireGameSdkMaxlock(int i, int i2) throws RemoteException;

    boolean addHeavyLoadApps(List<String> list) throws RemoteException;

    boolean addLowModeApps(List<String> list) throws RemoteException;

    boolean addLowRefreshRateApps(List<String> list) throws RemoteException;

    void destroyGameSdkMaxlock() throws RemoteException;

    String getActiveSensorList() throws RemoteException;

    int[] getAllTemperatures(int i) throws RemoteException;

    List<SemBatteryEventHistory> getBatteryEventHistory(long j, long j2, int i)
            throws RemoteException;

    List<SemBatteryStats> getBatteryStats(int i, long j, long j2, boolean z) throws RemoteException;

    List<CoolingDevice> getCoolingDevices() throws RemoteException;

    Bundle getGameSiopInfo() throws RemoteException;

    List<String> getHeavyLoadApps() throws RemoteException;

    boolean getHighBrightnessMode() throws RemoteException;

    int getLRTemperature() throws RemoteException;

    List<String> getLowModeApps() throws RemoteException;

    List<String> getLowRefreshRateApps() throws RemoteException;

    List<SemNetworkUsageStats> getNetworkUsageStats(long j, long j2) throws RemoteException;

    List<OverheatReasonInternal> getOverheatReason(long j, long j2) throws RemoteException;

    List<SemProcessUsageStats> getProcessUsageStats(long j, long j2) throws RemoteException;

    int getRUT(int i, String str) throws RemoteException;

    int getRemainingUsageTime(int i) throws RemoteException;

    int getRemainingUsageTimeWithSettings(int i, int i2) throws RemoteException;

    int getSsrmStatus(int i) throws RemoteException;

    int getSupportedHistoryTypes() throws RemoteException;

    int getSupportedThermalThrottlingDelta() throws RemoteException;

    int getTemperature(int i) throws RemoteException;

    List<Temperature> getTemperatures() throws RemoteException;

    int getThermalControlFlag() throws RemoteException;

    List<SemThermalStats> getThermalStats(long j, long j2) throws RemoteException;

    int getThermalThrottlingDelta() throws RemoteException;

    void initGameSdkMaxlock(IBinder iBinder, IBinder iBinder2) throws RemoteException;

    boolean isDownLoadingForUid(int i) throws RemoteException;

    boolean isGameByGraphic(String str) throws RemoteException;

    boolean isGameSupportLRP() throws RemoteException;

    void logAction(String str, int i, List<Bundle> list) throws RemoteException;

    void logActionWithPkg(String str, int i, String str2, List<Bundle> list) throws RemoteException;

    void logActionWithSource(String str, int i, int i2) throws RemoteException;

    void logAnomaly(Bundle bundle) throws RemoteException;

    void registerCallback(IThermalEventListener iThermalEventListener) throws RemoteException;

    void releaseGameSdkMaxlock() throws RemoteException;

    boolean removeConfigPart(String str, String str2) throws RemoteException;

    void sendCommand(String str, String str2) throws RemoteException;

    boolean setAnomalyConfig(PendingIntent pendingIntent) throws RemoteException;

    void setHighBrightnessMode(boolean z) throws RemoteException;

    boolean setThermalControlFlag(int i) throws RemoteException;

    boolean setThermalThrottlingDelta(int i) throws RemoteException;

    boolean setThermalThrottlingDeltaWithPackageName(String str, int i) throws RemoteException;

    float[] supportVRTemperaturesInformation(String str, int i, int i2) throws RemoteException;

    int updateBatteryStatsInfo(int i) throws RemoteException;

    boolean updateConfigPart(String str, String str2, String str3) throws RemoteException;

    void updateGameSdkOperation(boolean z, IBinder iBinder) throws RemoteException;

    void updateSpaOperation(boolean z, IBinder iBinder) throws RemoteException;

    public static class Default implements ISamsungDeviceHealthManager {
        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void logAction(String pkgNameForContext, int category, List<Bundle> taggedData)
                throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void logActionWithPkg(
                String pkgNameForContext, int category, String pkg, List<Bundle> taggedData)
                throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void logActionWithSource(String pkgNameForContext, int source, int category)
                throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void logAnomaly(Bundle bundle) throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void sendCommand(String type, String value) throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getRemainingUsageTime(int mode) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getRemainingUsageTimeWithSettings(int mode, int flags) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public float[] supportVRTemperaturesInformation(String callingPackage, int type, int source)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getSsrmStatus(int type) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void initGameSdkMaxlock(IBinder token, IBinder callbackBinder)
                throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void acquireGameSdkMaxlock(int cpuPerc, int gpuPerc) throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void releaseGameSdkMaxlock() throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void destroyGameSdkMaxlock() throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getLRTemperature() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemBatteryStats> getBatteryStats(
                int intervalType, long from, long to, boolean includeApp) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean isDownLoadingForUid(int uid) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean isGameSupportLRP() throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getRUT(int mode, String settings) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<CoolingDevice> getCoolingDevices() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<Temperature> getTemperatures() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void registerCallback(IThermalEventListener cb) throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getTemperature(int type) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemBatteryEventHistory> getBatteryEventHistory(
                long from, long to, int historyTypes) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getSupportedHistoryTypes() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemThermalStats> getThermalStats(long from, long to) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemProcessUsageStats> getProcessUsageStats(long from, long to)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemNetworkUsageStats> getNetworkUsageStats(long from, long to)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean setThermalThrottlingDelta(int value) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean setThermalThrottlingDeltaWithPackageName(String pkgName, int value)
                throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getThermalThrottlingDelta() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getSupportedThermalThrottlingDelta() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void updateSpaOperation(boolean on, IBinder token) throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void updateGameSdkOperation(boolean on, IBinder token) throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public Bundle getGameSiopInfo() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean setAnomalyConfig(PendingIntent pendingIntent) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<OverheatReasonInternal> getOverheatReason(long from, long to)
                throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public String getActiveSensorList() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean setThermalControlFlag(int flag) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getThermalControlFlag() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean addHeavyLoadApps(List<String> appList) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<String> getHeavyLoadApps() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean addLowModeApps(List<String> appList) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<String> getLowModeApps() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean addLowRefreshRateApps(List<String> appList) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<String> getLowRefreshRateApps() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int[] getAllTemperatures(int numOfType) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int updateBatteryStatsInfo(int type) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean updateConfigPart(String configName, String configPartName, String configData)
                throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean removeConfigPart(String configName, String configPartName)
                throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void setHighBrightnessMode(boolean on) throws RemoteException {}

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean getHighBrightnessMode() throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean isGameByGraphic(String pkgName) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements ISamsungDeviceHealthManager {
        static final int TRANSACTION_acquireGameSdkMaxlock = 11;
        static final int TRANSACTION_addHeavyLoadApps = 40;
        static final int TRANSACTION_addLowModeApps = 42;
        static final int TRANSACTION_addLowRefreshRateApps = 44;
        static final int TRANSACTION_destroyGameSdkMaxlock = 13;
        static final int TRANSACTION_getActiveSensorList = 37;
        static final int TRANSACTION_getAllTemperatures = 46;
        static final int TRANSACTION_getBatteryEventHistory = 23;
        static final int TRANSACTION_getBatteryStats = 15;
        static final int TRANSACTION_getCoolingDevices = 19;
        static final int TRANSACTION_getGameSiopInfo = 34;
        static final int TRANSACTION_getHeavyLoadApps = 41;
        static final int TRANSACTION_getHighBrightnessMode = 51;
        static final int TRANSACTION_getLRTemperature = 14;
        static final int TRANSACTION_getLowModeApps = 43;
        static final int TRANSACTION_getLowRefreshRateApps = 45;
        static final int TRANSACTION_getNetworkUsageStats = 27;
        static final int TRANSACTION_getOverheatReason = 36;
        static final int TRANSACTION_getProcessUsageStats = 26;
        static final int TRANSACTION_getRUT = 18;
        static final int TRANSACTION_getRemainingUsageTime = 6;
        static final int TRANSACTION_getRemainingUsageTimeWithSettings = 7;
        static final int TRANSACTION_getSsrmStatus = 9;
        static final int TRANSACTION_getSupportedHistoryTypes = 24;
        static final int TRANSACTION_getSupportedThermalThrottlingDelta = 31;
        static final int TRANSACTION_getTemperature = 22;
        static final int TRANSACTION_getTemperatures = 20;
        static final int TRANSACTION_getThermalControlFlag = 39;
        static final int TRANSACTION_getThermalStats = 25;
        static final int TRANSACTION_getThermalThrottlingDelta = 30;
        static final int TRANSACTION_initGameSdkMaxlock = 10;
        static final int TRANSACTION_isDownLoadingForUid = 16;
        static final int TRANSACTION_isGameByGraphic = 52;
        static final int TRANSACTION_isGameSupportLRP = 17;
        static final int TRANSACTION_logAction = 1;
        static final int TRANSACTION_logActionWithPkg = 2;
        static final int TRANSACTION_logActionWithSource = 3;
        static final int TRANSACTION_logAnomaly = 4;
        static final int TRANSACTION_registerCallback = 21;
        static final int TRANSACTION_releaseGameSdkMaxlock = 12;
        static final int TRANSACTION_removeConfigPart = 49;
        static final int TRANSACTION_sendCommand = 5;
        static final int TRANSACTION_setAnomalyConfig = 35;
        static final int TRANSACTION_setHighBrightnessMode = 50;
        static final int TRANSACTION_setThermalControlFlag = 38;
        static final int TRANSACTION_setThermalThrottlingDelta = 28;
        static final int TRANSACTION_setThermalThrottlingDeltaWithPackageName = 29;
        static final int TRANSACTION_supportVRTemperaturesInformation = 8;
        static final int TRANSACTION_updateBatteryStatsInfo = 47;
        static final int TRANSACTION_updateConfigPart = 48;
        static final int TRANSACTION_updateGameSdkOperation = 33;
        static final int TRANSACTION_updateSpaOperation = 32;
        private final PermissionEnforcer mEnforcer;

        public Stub(PermissionEnforcer enforcer) {
            attachInterface(this, ISamsungDeviceHealthManager.DESCRIPTOR);
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

        public static ISamsungDeviceHealthManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ISamsungDeviceHealthManager.DESCRIPTOR);
            if (iin != null && (iin instanceof ISamsungDeviceHealthManager)) {
                return (ISamsungDeviceHealthManager) iin;
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
                    return "logAction";
                case 2:
                    return "logActionWithPkg";
                case 3:
                    return "logActionWithSource";
                case 4:
                    return "logAnomaly";
                case 5:
                    return "sendCommand";
                case 6:
                    return "getRemainingUsageTime";
                case 7:
                    return "getRemainingUsageTimeWithSettings";
                case 8:
                    return "supportVRTemperaturesInformation";
                case 9:
                    return "getSsrmStatus";
                case 10:
                    return "initGameSdkMaxlock";
                case 11:
                    return "acquireGameSdkMaxlock";
                case 12:
                    return "releaseGameSdkMaxlock";
                case 13:
                    return "destroyGameSdkMaxlock";
                case 14:
                    return "getLRTemperature";
                case 15:
                    return "getBatteryStats";
                case 16:
                    return "isDownLoadingForUid";
                case 17:
                    return "isGameSupportLRP";
                case 18:
                    return "getRUT";
                case 19:
                    return "getCoolingDevices";
                case 20:
                    return "getTemperatures";
                case 21:
                    return "registerCallback";
                case 22:
                    return "getTemperature";
                case 23:
                    return "getBatteryEventHistory";
                case 24:
                    return "getSupportedHistoryTypes";
                case 25:
                    return "getThermalStats";
                case 26:
                    return "getProcessUsageStats";
                case 27:
                    return "getNetworkUsageStats";
                case 28:
                    return "setThermalThrottlingDelta";
                case 29:
                    return "setThermalThrottlingDeltaWithPackageName";
                case 30:
                    return "getThermalThrottlingDelta";
                case 31:
                    return "getSupportedThermalThrottlingDelta";
                case 32:
                    return "updateSpaOperation";
                case 33:
                    return "updateGameSdkOperation";
                case 34:
                    return "getGameSiopInfo";
                case 35:
                    return "setAnomalyConfig";
                case 36:
                    return "getOverheatReason";
                case 37:
                    return "getActiveSensorList";
                case 38:
                    return "setThermalControlFlag";
                case 39:
                    return "getThermalControlFlag";
                case 40:
                    return "addHeavyLoadApps";
                case 41:
                    return "getHeavyLoadApps";
                case 42:
                    return "addLowModeApps";
                case 43:
                    return "getLowModeApps";
                case 44:
                    return "addLowRefreshRateApps";
                case 45:
                    return "getLowRefreshRateApps";
                case 46:
                    return "getAllTemperatures";
                case 47:
                    return "updateBatteryStatsInfo";
                case 48:
                    return "updateConfigPart";
                case 49:
                    return "removeConfigPart";
                case 50:
                    return "setHighBrightnessMode";
                case 51:
                    return "getHighBrightnessMode";
                case 52:
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
                data.enforceInterface(ISamsungDeviceHealthManager.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISamsungDeviceHealthManager.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    int _arg1 = data.readInt();
                    List<Bundle> _arg2 = data.createTypedArrayList(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    logAction(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    int _arg12 = data.readInt();
                    String _arg22 = data.readString();
                    List<Bundle> _arg3 = data.createTypedArrayList(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    logActionWithPkg(_arg02, _arg12, _arg22, _arg3);
                    reply.writeNoException();
                    return true;
                case 3:
                    String _arg03 = data.readString();
                    int _arg13 = data.readInt();
                    int _arg23 = data.readInt();
                    data.enforceNoDataAvail();
                    logActionWithSource(_arg03, _arg13, _arg23);
                    reply.writeNoException();
                    return true;
                case 4:
                    Bundle _arg04 = (Bundle) data.readTypedObject(Bundle.CREATOR);
                    data.enforceNoDataAvail();
                    logAnomaly(_arg04);
                    reply.writeNoException();
                    return true;
                case 5:
                    String _arg05 = data.readString();
                    String _arg14 = data.readString();
                    data.enforceNoDataAvail();
                    sendCommand(_arg05, _arg14);
                    return true;
                case 6:
                    int _arg06 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result = getRemainingUsageTime(_arg06);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 7:
                    int _arg07 = data.readInt();
                    int _arg15 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result2 = getRemainingUsageTimeWithSettings(_arg07, _arg15);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 8:
                    String _arg08 = data.readString();
                    int _arg16 = data.readInt();
                    int _arg24 = data.readInt();
                    data.enforceNoDataAvail();
                    float[] _result3 = supportVRTemperaturesInformation(_arg08, _arg16, _arg24);
                    reply.writeNoException();
                    reply.writeFloatArray(_result3);
                    return true;
                case 9:
                    int _arg09 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result4 = getSsrmStatus(_arg09);
                    reply.writeNoException();
                    reply.writeInt(_result4);
                    return true;
                case 10:
                    IBinder _arg010 = data.readStrongBinder();
                    IBinder _arg17 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    initGameSdkMaxlock(_arg010, _arg17);
                    reply.writeNoException();
                    return true;
                case 11:
                    int _arg011 = data.readInt();
                    int _arg18 = data.readInt();
                    data.enforceNoDataAvail();
                    acquireGameSdkMaxlock(_arg011, _arg18);
                    reply.writeNoException();
                    return true;
                case 12:
                    releaseGameSdkMaxlock();
                    reply.writeNoException();
                    return true;
                case 13:
                    destroyGameSdkMaxlock();
                    reply.writeNoException();
                    return true;
                case 14:
                    int _result5 = getLRTemperature();
                    reply.writeNoException();
                    reply.writeInt(_result5);
                    return true;
                case 15:
                    int _arg012 = data.readInt();
                    long _arg19 = data.readLong();
                    long _arg25 = data.readLong();
                    boolean _arg32 = data.readBoolean();
                    data.enforceNoDataAvail();
                    List<SemBatteryStats> _result6 =
                            getBatteryStats(_arg012, _arg19, _arg25, _arg32);
                    reply.writeNoException();
                    reply.writeTypedList(_result6, 1);
                    return true;
                case 16:
                    int _arg013 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result7 = isDownLoadingForUid(_arg013);
                    reply.writeNoException();
                    reply.writeBoolean(_result7);
                    return true;
                case 17:
                    boolean _result8 = isGameSupportLRP();
                    reply.writeNoException();
                    reply.writeBoolean(_result8);
                    return true;
                case 18:
                    int _arg014 = data.readInt();
                    String _arg110 = data.readString();
                    data.enforceNoDataAvail();
                    int _result9 = getRUT(_arg014, _arg110);
                    reply.writeNoException();
                    reply.writeInt(_result9);
                    return true;
                case 19:
                    List<CoolingDevice> _result10 = getCoolingDevices();
                    reply.writeNoException();
                    reply.writeTypedList(_result10, 1);
                    return true;
                case 20:
                    List<Temperature> _result11 = getTemperatures();
                    reply.writeNoException();
                    reply.writeTypedList(_result11, 1);
                    return true;
                case 21:
                    IThermalEventListener _arg015 =
                            IThermalEventListener.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerCallback(_arg015);
                    reply.writeNoException();
                    return true;
                case 22:
                    int _arg016 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result12 = getTemperature(_arg016);
                    reply.writeNoException();
                    reply.writeInt(_result12);
                    return true;
                case 23:
                    long _arg017 = data.readLong();
                    long _arg111 = data.readLong();
                    int _arg26 = data.readInt();
                    data.enforceNoDataAvail();
                    List<SemBatteryEventHistory> _result13 =
                            getBatteryEventHistory(_arg017, _arg111, _arg26);
                    reply.writeNoException();
                    reply.writeTypedList(_result13, 1);
                    return true;
                case 24:
                    int _result14 = getSupportedHistoryTypes();
                    reply.writeNoException();
                    reply.writeInt(_result14);
                    return true;
                case 25:
                    long _arg018 = data.readLong();
                    long _arg112 = data.readLong();
                    data.enforceNoDataAvail();
                    List<SemThermalStats> _result15 = getThermalStats(_arg018, _arg112);
                    reply.writeNoException();
                    reply.writeTypedList(_result15, 1);
                    return true;
                case 26:
                    long _arg019 = data.readLong();
                    long _arg113 = data.readLong();
                    data.enforceNoDataAvail();
                    List<SemProcessUsageStats> _result16 = getProcessUsageStats(_arg019, _arg113);
                    reply.writeNoException();
                    reply.writeTypedList(_result16, 1);
                    return true;
                case 27:
                    long _arg020 = data.readLong();
                    long _arg114 = data.readLong();
                    data.enforceNoDataAvail();
                    List<SemNetworkUsageStats> _result17 = getNetworkUsageStats(_arg020, _arg114);
                    reply.writeNoException();
                    reply.writeTypedList(_result17, 1);
                    return true;
                case 28:
                    int _arg021 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result18 = setThermalThrottlingDelta(_arg021);
                    reply.writeNoException();
                    reply.writeBoolean(_result18);
                    return true;
                case 29:
                    String _arg022 = data.readString();
                    int _arg115 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result19 = setThermalThrottlingDeltaWithPackageName(_arg022, _arg115);
                    reply.writeNoException();
                    reply.writeBoolean(_result19);
                    return true;
                case 30:
                    int _result20 = getThermalThrottlingDelta();
                    reply.writeNoException();
                    reply.writeInt(_result20);
                    return true;
                case 31:
                    int _result21 = getSupportedThermalThrottlingDelta();
                    reply.writeNoException();
                    reply.writeInt(_result21);
                    return true;
                case 32:
                    boolean _arg023 = data.readBoolean();
                    IBinder _arg116 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    updateSpaOperation(_arg023, _arg116);
                    reply.writeNoException();
                    return true;
                case 33:
                    boolean _arg024 = data.readBoolean();
                    IBinder _arg117 = data.readStrongBinder();
                    data.enforceNoDataAvail();
                    updateGameSdkOperation(_arg024, _arg117);
                    reply.writeNoException();
                    return true;
                case 34:
                    Bundle _result22 = getGameSiopInfo();
                    reply.writeNoException();
                    reply.writeTypedObject(_result22, 1);
                    return true;
                case 35:
                    PendingIntent _arg025 =
                            (PendingIntent) data.readTypedObject(PendingIntent.CREATOR);
                    data.enforceNoDataAvail();
                    boolean _result23 = setAnomalyConfig(_arg025);
                    reply.writeNoException();
                    reply.writeBoolean(_result23);
                    return true;
                case 36:
                    long _arg026 = data.readLong();
                    long _arg118 = data.readLong();
                    data.enforceNoDataAvail();
                    List<OverheatReasonInternal> _result24 = getOverheatReason(_arg026, _arg118);
                    reply.writeNoException();
                    reply.writeTypedList(_result24, 1);
                    return true;
                case 37:
                    String _result25 = getActiveSensorList();
                    reply.writeNoException();
                    reply.writeString(_result25);
                    return true;
                case 38:
                    int _arg027 = data.readInt();
                    data.enforceNoDataAvail();
                    boolean _result26 = setThermalControlFlag(_arg027);
                    reply.writeNoException();
                    reply.writeBoolean(_result26);
                    return true;
                case 39:
                    int _result27 = getThermalControlFlag();
                    reply.writeNoException();
                    reply.writeInt(_result27);
                    return true;
                case 40:
                    List<String> _arg028 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    boolean _result28 = addHeavyLoadApps(_arg028);
                    reply.writeNoException();
                    reply.writeBoolean(_result28);
                    return true;
                case 41:
                    List<String> _result29 = getHeavyLoadApps();
                    reply.writeNoException();
                    reply.writeStringList(_result29);
                    return true;
                case 42:
                    List<String> _arg029 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    boolean _result30 = addLowModeApps(_arg029);
                    reply.writeNoException();
                    reply.writeBoolean(_result30);
                    return true;
                case 43:
                    List<String> _result31 = getLowModeApps();
                    reply.writeNoException();
                    reply.writeStringList(_result31);
                    return true;
                case 44:
                    List<String> _arg030 = data.createStringArrayList();
                    data.enforceNoDataAvail();
                    boolean _result32 = addLowRefreshRateApps(_arg030);
                    reply.writeNoException();
                    reply.writeBoolean(_result32);
                    return true;
                case 45:
                    List<String> _result33 = getLowRefreshRateApps();
                    reply.writeNoException();
                    reply.writeStringList(_result33);
                    return true;
                case 46:
                    int _arg031 = data.readInt();
                    data.enforceNoDataAvail();
                    int[] _result34 = getAllTemperatures(_arg031);
                    reply.writeNoException();
                    reply.writeIntArray(_result34);
                    return true;
                case 47:
                    int _arg032 = data.readInt();
                    data.enforceNoDataAvail();
                    int _result35 = updateBatteryStatsInfo(_arg032);
                    reply.writeNoException();
                    reply.writeInt(_result35);
                    return true;
                case 48:
                    String _arg033 = data.readString();
                    String _arg119 = data.readString();
                    String _arg27 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result36 = updateConfigPart(_arg033, _arg119, _arg27);
                    reply.writeNoException();
                    reply.writeBoolean(_result36);
                    return true;
                case 49:
                    String _arg034 = data.readString();
                    String _arg120 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result37 = removeConfigPart(_arg034, _arg120);
                    reply.writeNoException();
                    reply.writeBoolean(_result37);
                    return true;
                case 50:
                    boolean _arg035 = data.readBoolean();
                    data.enforceNoDataAvail();
                    setHighBrightnessMode(_arg035);
                    reply.writeNoException();
                    return true;
                case 51:
                    boolean _result38 = getHighBrightnessMode();
                    reply.writeNoException();
                    reply.writeBoolean(_result38);
                    return true;
                case 52:
                    String _arg036 = data.readString();
                    data.enforceNoDataAvail();
                    boolean _result39 = isGameByGraphic(_arg036);
                    reply.writeNoException();
                    reply.writeBoolean(_result39);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ISamsungDeviceHealthManager {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISamsungDeviceHealthManager.DESCRIPTOR;
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logAction(String pkgNameForContext, int category, List<Bundle> taggedData)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(pkgNameForContext);
                    _data.writeInt(category);
                    _data.writeTypedList(taggedData, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logActionWithPkg(
                    String pkgNameForContext, int category, String pkg, List<Bundle> taggedData)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(pkgNameForContext);
                    _data.writeInt(category);
                    _data.writeString(pkg);
                    _data.writeTypedList(taggedData, 0);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logActionWithSource(String pkgNameForContext, int source, int category)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(pkgNameForContext);
                    _data.writeInt(source);
                    _data.writeInt(category);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logAnomaly(Bundle bundle) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void sendCommand(String type, String value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(type);
                    _data.writeString(value);
                    this.mRemote.transact(5, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRemainingUsageTime(int mode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRemainingUsageTimeWithSettings(int mode, int flags)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeInt(flags);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public float[] supportVRTemperaturesInformation(
                    String callingPackage, int type, int source) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeInt(type);
                    _data.writeInt(source);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    float[] _result = _reply.createFloatArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSsrmStatus(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void initGameSdkMaxlock(IBinder token, IBinder callbackBinder)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeStrongBinder(token);
                    _data.writeStrongBinder(callbackBinder);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void acquireGameSdkMaxlock(int cpuPerc, int gpuPerc) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(cpuPerc);
                    _data.writeInt(gpuPerc);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void releaseGameSdkMaxlock() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void destroyGameSdkMaxlock() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getLRTemperature() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemBatteryStats> getBatteryStats(
                    int intervalType, long from, long to, boolean includeApp)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(intervalType);
                    _data.writeLong(from);
                    _data.writeLong(to);
                    _data.writeBoolean(includeApp);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    List<SemBatteryStats> _result =
                            _reply.createTypedArrayList(SemBatteryStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isDownLoadingForUid(int uid) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(uid);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isGameSupportLRP() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRUT(int mode, String settings) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(mode);
                    _data.writeString(settings);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<CoolingDevice> getCoolingDevices() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    List<CoolingDevice> _result =
                            _reply.createTypedArrayList(CoolingDevice.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<Temperature> getTemperatures() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    List<Temperature> _result = _reply.createTypedArrayList(Temperature.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void registerCallback(IThermalEventListener cb) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeStrongInterface(cb);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getTemperature(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemBatteryEventHistory> getBatteryEventHistory(
                    long from, long to, int historyTypes) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeLong(from);
                    _data.writeLong(to);
                    _data.writeInt(historyTypes);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    List<SemBatteryEventHistory> _result =
                            _reply.createTypedArrayList(SemBatteryEventHistory.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSupportedHistoryTypes() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemThermalStats> getThermalStats(long from, long to)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeLong(from);
                    _data.writeLong(to);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    List<SemThermalStats> _result =
                            _reply.createTypedArrayList(SemThermalStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemProcessUsageStats> getProcessUsageStats(long from, long to)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeLong(from);
                    _data.writeLong(to);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    List<SemProcessUsageStats> _result =
                            _reply.createTypedArrayList(SemProcessUsageStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemNetworkUsageStats> getNetworkUsageStats(long from, long to)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeLong(from);
                    _data.writeLong(to);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                    List<SemNetworkUsageStats> _result =
                            _reply.createTypedArrayList(SemNetworkUsageStats.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalThrottlingDelta(int value) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(value);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalThrottlingDeltaWithPackageName(String pkgName, int value)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(pkgName);
                    _data.writeInt(value);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getThermalThrottlingDelta() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSupportedThermalThrottlingDelta() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void updateSpaOperation(boolean on, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeBoolean(on);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void updateGameSdkOperation(boolean on, IBinder token) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeBoolean(on);
                    _data.writeStrongBinder(token);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public Bundle getGameSiopInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                    Bundle _result = (Bundle) _reply.readTypedObject(Bundle.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setAnomalyConfig(PendingIntent pendingIntent) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<OverheatReasonInternal> getOverheatReason(long from, long to)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeLong(from);
                    _data.writeLong(to);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                    List<OverheatReasonInternal> _result =
                            _reply.createTypedArrayList(OverheatReasonInternal.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public String getActiveSensorList() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalControlFlag(int flag) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(flag);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getThermalControlFlag() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addHeavyLoadApps(List<String> appList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeStringList(appList);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getHeavyLoadApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addLowModeApps(List<String> appList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeStringList(appList);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getLowModeApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addLowRefreshRateApps(List<String> appList) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeStringList(appList);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getLowRefreshRateApps() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int[] getAllTemperatures(int numOfType) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(numOfType);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int updateBatteryStatsInfo(int type) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean updateConfigPart(
                    String configName, String configPartName, String configData)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(configName);
                    _data.writeString(configPartName);
                    _data.writeString(configData);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean removeConfigPart(String configName, String configPartName)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(configName);
                    _data.writeString(configPartName);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void setHighBrightnessMode(boolean on) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeBoolean(on);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean getHighBrightnessMode() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isGameByGraphic(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        protected void getBatteryStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getBatteryEventHistory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getSupportedHistoryTypes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void updateBatteryStatsInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void updateConfigPart_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.DUMP, getCallingPid(), getCallingUid());
        }

        protected void removeConfigPart_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(
                    Manifest.permission.DUMP, getCallingPid(), getCallingUid());
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 51;
        }
    }
}
