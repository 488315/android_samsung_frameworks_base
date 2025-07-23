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
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public interface ISamsungDeviceHealthManager extends IInterface {
    public static final String DESCRIPTOR = "com.sec.android.sdhms.ISamsungDeviceHealthManager";

    public static class Default implements ISamsungDeviceHealthManager {
        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void acquireGameSdkMaxlock(int i, int i2) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean addHeavyLoadApps(List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean addLowModeApps(List<String> list) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean addLowRefreshRateApps(List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void destroyGameSdkMaxlock() throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public String getActiveSensorList() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int[] getAllTemperatures(int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemBatteryEventHistory> getBatteryEventHistory(long j, long j2, int i) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemBatteryStats> getBatteryStats(int i, long j, long j2, boolean z) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<CoolingDevice> getCoolingDevices() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public Bundle getGameSiopInfo() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<String> getHeavyLoadApps() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean getHighBrightnessMode() throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getLRTemperature() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<String> getLowModeApps() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<String> getLowRefreshRateApps() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemNetworkUsageStats> getNetworkUsageStats(long j, long j2) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<OverheatReasonInternal> getOverheatReason(long j, long j2) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemProcessUsageStats> getProcessUsageStats(long j, long j2) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getRUT(int i, String str) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getRemainingUsageTime(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getRemainingUsageTimeWithSettings(int i, int i2) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getSsrmStatus(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getSupportedHistoryTypes() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getSupportedThermalThrottlingDelta() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getTemperature(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<Temperature> getTemperatures() throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getThermalControlFlag() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public List<SemThermalStats> getThermalStats(long j, long j2) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int getThermalThrottlingDelta() throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void initGameSdkMaxlock(IBinder iBinder, IBinder iBinder2) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean isDownLoadingForUid(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean isGameByGraphic(String str) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean isGameSupportLRP() throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void logAction(String str, int i, List<Bundle> list) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void logActionWithPkg(String str, int i, String str2, List<Bundle> list) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void logActionWithSource(String str, int i, int i2) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void logAnomaly(Bundle bundle) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void registerCallback(IThermalEventListener iThermalEventListener) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void releaseGameSdkMaxlock() throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean removeConfigPart(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void sendCommand(String str, String str2) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean setAnomalyConfig(PendingIntent pendingIntent) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void setHighBrightnessMode(boolean z) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean setThermalControlFlag(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean setThermalThrottlingDelta(int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean setThermalThrottlingDeltaWithPackageName(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public float[] supportVRTemperaturesInformation(String str, int i, int i2) throws RemoteException {
            return null;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public int updateBatteryStatsInfo(int i) throws RemoteException {
            return 0;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public boolean updateConfigPart(String str, String str2, String str3) throws RemoteException {
            return false;
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void updateGameSdkOperation(boolean z, IBinder iBinder) throws RemoteException {
        }

        @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
        public void updateSpaOperation(boolean z, IBinder iBinder) throws RemoteException {
        }
    }

    void acquireGameSdkMaxlock(int i, int i2) throws RemoteException;

    boolean addHeavyLoadApps(List<String> list) throws RemoteException;

    boolean addLowModeApps(List<String> list) throws RemoteException;

    boolean addLowRefreshRateApps(List<String> list) throws RemoteException;

    void destroyGameSdkMaxlock() throws RemoteException;

    String getActiveSensorList() throws RemoteException;

    int[] getAllTemperatures(int i) throws RemoteException;

    List<SemBatteryEventHistory> getBatteryEventHistory(long j, long j2, int i) throws RemoteException;

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

    public static abstract class Stub extends Binder implements ISamsungDeviceHealthManager {
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

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 51;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, ISamsungDeviceHealthManager.DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static ISamsungDeviceHealthManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISamsungDeviceHealthManager.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISamsungDeviceHealthManager)) {
                return (ISamsungDeviceHealthManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
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
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISamsungDeviceHealthManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISamsungDeviceHealthManager.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logAction(readString, readInt, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    String readString3 = parcel.readString();
                    ArrayList createTypedArrayList2 = parcel.createTypedArrayList(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logActionWithPkg(readString2, readInt2, readString3, createTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString4 = parcel.readString();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logActionWithSource(readString4, readInt3, readInt4);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logAnomaly(bundle);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendCommand(readString5, readString6);
                    return true;
                case 6:
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingUsageTime = getRemainingUsageTime(readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingUsageTime);
                    return true;
                case 7:
                    int readInt6 = parcel.readInt();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingUsageTimeWithSettings = getRemainingUsageTimeWithSettings(readInt6, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingUsageTimeWithSettings);
                    return true;
                case 8:
                    String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    int readInt9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float[] supportVRTemperaturesInformation = supportVRTemperaturesInformation(readString7, readInt8, readInt9);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(supportVRTemperaturesInformation);
                    return true;
                case 9:
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int ssrmStatus = getSsrmStatus(readInt10);
                    parcel2.writeNoException();
                    parcel2.writeInt(ssrmStatus);
                    return true;
                case 10:
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    initGameSdkMaxlock(readStrongBinder, readStrongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int readInt11 = parcel.readInt();
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acquireGameSdkMaxlock(readInt11, readInt12);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    releaseGameSdkMaxlock();
                    parcel2.writeNoException();
                    return true;
                case 13:
                    destroyGameSdkMaxlock();
                    parcel2.writeNoException();
                    return true;
                case 14:
                    int lRTemperature = getLRTemperature();
                    parcel2.writeNoException();
                    parcel2.writeInt(lRTemperature);
                    return true;
                case 15:
                    int readInt13 = parcel.readInt();
                    long readLong = parcel.readLong();
                    long readLong2 = parcel.readLong();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<SemBatteryStats> batteryStats = getBatteryStats(readInt13, readLong, readLong2, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(batteryStats, 1);
                    return true;
                case 16:
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isDownLoadingForUid = isDownLoadingForUid(readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDownLoadingForUid);
                    return true;
                case 17:
                    boolean isGameSupportLRP = isGameSupportLRP();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGameSupportLRP);
                    return true;
                case 18:
                    int readInt15 = parcel.readInt();
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int rut = getRUT(readInt15, readString8);
                    parcel2.writeNoException();
                    parcel2.writeInt(rut);
                    return true;
                case 19:
                    List<CoolingDevice> coolingDevices = getCoolingDevices();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(coolingDevices, 1);
                    return true;
                case 20:
                    List<Temperature> temperatures = getTemperatures();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(temperatures, 1);
                    return true;
                case 21:
                    IThermalEventListener asInterface = IThermalEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(asInterface);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int temperature = getTemperature(readInt16);
                    parcel2.writeNoException();
                    parcel2.writeInt(temperature);
                    return true;
                case 23:
                    long readLong3 = parcel.readLong();
                    long readLong4 = parcel.readLong();
                    int readInt17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SemBatteryEventHistory> batteryEventHistory = getBatteryEventHistory(readLong3, readLong4, readInt17);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(batteryEventHistory, 1);
                    return true;
                case 24:
                    int supportedHistoryTypes = getSupportedHistoryTypes();
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedHistoryTypes);
                    return true;
                case 25:
                    long readLong5 = parcel.readLong();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<SemThermalStats> thermalStats = getThermalStats(readLong5, readLong6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(thermalStats, 1);
                    return true;
                case 26:
                    long readLong7 = parcel.readLong();
                    long readLong8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<SemProcessUsageStats> processUsageStats = getProcessUsageStats(readLong7, readLong8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(processUsageStats, 1);
                    return true;
                case 27:
                    long readLong9 = parcel.readLong();
                    long readLong10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<SemNetworkUsageStats> networkUsageStats = getNetworkUsageStats(readLong9, readLong10);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(networkUsageStats, 1);
                    return true;
                case 28:
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean thermalThrottlingDelta = setThermalThrottlingDelta(readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(thermalThrottlingDelta);
                    return true;
                case 29:
                    String readString9 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean thermalThrottlingDeltaWithPackageName = setThermalThrottlingDeltaWithPackageName(readString9, readInt19);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(thermalThrottlingDeltaWithPackageName);
                    return true;
                case 30:
                    int thermalThrottlingDelta2 = getThermalThrottlingDelta();
                    parcel2.writeNoException();
                    parcel2.writeInt(thermalThrottlingDelta2);
                    return true;
                case 31:
                    int supportedThermalThrottlingDelta = getSupportedThermalThrottlingDelta();
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedThermalThrottlingDelta);
                    return true;
                case 32:
                    boolean readBoolean2 = parcel.readBoolean();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateSpaOperation(readBoolean2, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean readBoolean3 = parcel.readBoolean();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateGameSdkOperation(readBoolean3, readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    Bundle gameSiopInfo = getGameSiopInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(gameSiopInfo, 1);
                    return true;
                case 35:
                    PendingIntent pendingIntent = (PendingIntent) parcel.readTypedObject(PendingIntent.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean anomalyConfig = setAnomalyConfig(pendingIntent);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(anomalyConfig);
                    return true;
                case 36:
                    long readLong11 = parcel.readLong();
                    long readLong12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<OverheatReasonInternal> overheatReason = getOverheatReason(readLong11, readLong12);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(overheatReason, 1);
                    return true;
                case 37:
                    String activeSensorList = getActiveSensorList();
                    parcel2.writeNoException();
                    parcel2.writeString(activeSensorList);
                    return true;
                case 38:
                    int readInt20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean thermalControlFlag = setThermalControlFlag(readInt20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(thermalControlFlag);
                    return true;
                case 39:
                    int thermalControlFlag2 = getThermalControlFlag();
                    parcel2.writeNoException();
                    parcel2.writeInt(thermalControlFlag2);
                    return true;
                case 40:
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addHeavyLoadApps = addHeavyLoadApps(createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addHeavyLoadApps);
                    return true;
                case 41:
                    List<String> heavyLoadApps = getHeavyLoadApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(heavyLoadApps);
                    return true;
                case 42:
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addLowModeApps = addLowModeApps(createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addLowModeApps);
                    return true;
                case 43:
                    List<String> lowModeApps = getLowModeApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(lowModeApps);
                    return true;
                case 44:
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean addLowRefreshRateApps = addLowRefreshRateApps(createStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addLowRefreshRateApps);
                    return true;
                case 45:
                    List<String> lowRefreshRateApps = getLowRefreshRateApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(lowRefreshRateApps);
                    return true;
                case 46:
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] allTemperatures = getAllTemperatures(readInt21);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(allTemperatures);
                    return true;
                case 47:
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int updateBatteryStatsInfo = updateBatteryStatsInfo(readInt22);
                    parcel2.writeNoException();
                    parcel2.writeInt(updateBatteryStatsInfo);
                    return true;
                case 48:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean updateConfigPart = updateConfigPart(readString10, readString11, readString12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateConfigPart);
                    return true;
                case 49:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeConfigPart = removeConfigPart(readString13, readString14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeConfigPart);
                    return true;
                case 50:
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHighBrightnessMode(readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    boolean highBrightnessMode = getHighBrightnessMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highBrightnessMode);
                    return true;
                case 52:
                    String readString15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isGameByGraphic = isGameByGraphic(readString15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isGameByGraphic);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements ISamsungDeviceHealthManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISamsungDeviceHealthManager.DESCRIPTOR;
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logAction(String str, int i, List<Bundle> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logActionWithPkg(String str, int i, String str2, List<Bundle> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logActionWithSource(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logAnomaly(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void sendCommand(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRemainingUsageTime(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRemainingUsageTimeWithSettings(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public float[] supportVRTemperaturesInformation(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createFloatArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSsrmStatus(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void initGameSdkMaxlock(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void acquireGameSdkMaxlock(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void releaseGameSdkMaxlock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void destroyGameSdkMaxlock() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getLRTemperature() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemBatteryStats> getBatteryStats(int i, long j, long j2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemBatteryStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isDownLoadingForUid(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isGameSupportLRP() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRUT(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<CoolingDevice> getCoolingDevices() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CoolingDevice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<Temperature> getTemperatures() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Temperature.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void registerCallback(IThermalEventListener iThermalEventListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeStrongInterface(iThermalEventListener);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getTemperature(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemBatteryEventHistory> getBatteryEventHistory(long j, long j2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemBatteryEventHistory.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSupportedHistoryTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemThermalStats> getThermalStats(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemThermalStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemProcessUsageStats> getProcessUsageStats(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemProcessUsageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemNetworkUsageStats> getNetworkUsageStats(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(SemNetworkUsageStats.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalThrottlingDelta(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalThrottlingDeltaWithPackageName(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getThermalThrottlingDelta() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSupportedThermalThrottlingDelta() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void updateSpaOperation(boolean z, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void updateGameSdkOperation(boolean z, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public Bundle getGameSiopInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setAnomalyConfig(PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<OverheatReasonInternal> getOverheatReason(long j, long j2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeLong(j2);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(OverheatReasonInternal.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public String getActiveSensorList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalControlFlag(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getThermalControlFlag() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addHeavyLoadApps(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getHeavyLoadApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addLowModeApps(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getLowModeApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addLowRefreshRateApps(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getLowRefreshRateApps() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int[] getAllTemperatures(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int updateBatteryStatsInfo(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean updateConfigPart(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean removeConfigPart(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void setHighBrightnessMode(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean getHighBrightnessMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isGameByGraphic(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getBatteryStats_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getBatteryEventHistory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void getSupportedHistoryTypes_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void updateBatteryStatsInfo_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.BATTERY_STATS, getCallingPid(), getCallingUid());
        }

        protected void updateConfigPart_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DUMP, getCallingPid(), getCallingUid());
        }

        protected void removeConfigPart_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.DUMP, getCallingPid(), getCallingUid());
        }
    }
}
