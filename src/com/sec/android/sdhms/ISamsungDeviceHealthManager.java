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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISamsungDeviceHealthManager.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISamsungDeviceHealthManager)) {
                return (ISamsungDeviceHealthManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logAction(string, i3, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    String string3 = parcel.readString();
                    ArrayList arrayListCreateTypedArrayList2 = parcel.createTypedArrayList(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logActionWithPkg(string2, i4, string3, arrayListCreateTypedArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string4 = parcel.readString();
                    int i5 = parcel.readInt();
                    int i6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    logActionWithSource(string4, i5, i6);
                    parcel2.writeNoException();
                    return true;
                case 4:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    logAnomaly(bundle);
                    parcel2.writeNoException();
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    sendCommand(string5, string6);
                    return true;
                case 6:
                    int i7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingUsageTime = getRemainingUsageTime(i7);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingUsageTime);
                    return true;
                case 7:
                    int i8 = parcel.readInt();
                    int i9 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int remainingUsageTimeWithSettings = getRemainingUsageTimeWithSettings(i8, i9);
                    parcel2.writeNoException();
                    parcel2.writeInt(remainingUsageTimeWithSettings);
                    return true;
                case 8:
                    String string7 = parcel.readString();
                    int i10 = parcel.readInt();
                    int i11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    float[] fArrSupportVRTemperaturesInformation = supportVRTemperaturesInformation(string7, i10, i11);
                    parcel2.writeNoException();
                    parcel2.writeFloatArray(fArrSupportVRTemperaturesInformation);
                    return true;
                case 9:
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int ssrmStatus = getSsrmStatus(i12);
                    parcel2.writeNoException();
                    parcel2.writeInt(ssrmStatus);
                    return true;
                case 10:
                    IBinder strongBinder = parcel.readStrongBinder();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    initGameSdkMaxlock(strongBinder, strongBinder2);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    int i13 = parcel.readInt();
                    int i14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    acquireGameSdkMaxlock(i13, i14);
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
                    int i15 = parcel.readInt();
                    long j = parcel.readLong();
                    long j2 = parcel.readLong();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    List<SemBatteryStats> batteryStats = getBatteryStats(i15, j, j2, z);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(batteryStats, 1);
                    return true;
                case 16:
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsDownLoadingForUid = isDownLoadingForUid(i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDownLoadingForUid);
                    return true;
                case 17:
                    boolean zIsGameSupportLRP = isGameSupportLRP();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGameSupportLRP);
                    return true;
                case 18:
                    int i17 = parcel.readInt();
                    String string8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int rut = getRUT(i17, string8);
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
                    IThermalEventListener iThermalEventListenerAsInterface = IThermalEventListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallback(iThermalEventListenerAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int temperature = getTemperature(i18);
                    parcel2.writeNoException();
                    parcel2.writeInt(temperature);
                    return true;
                case 23:
                    long j3 = parcel.readLong();
                    long j4 = parcel.readLong();
                    int i19 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<SemBatteryEventHistory> batteryEventHistory = getBatteryEventHistory(j3, j4, i19);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(batteryEventHistory, 1);
                    return true;
                case 24:
                    int supportedHistoryTypes = getSupportedHistoryTypes();
                    parcel2.writeNoException();
                    parcel2.writeInt(supportedHistoryTypes);
                    return true;
                case 25:
                    long j5 = parcel.readLong();
                    long j6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<SemThermalStats> thermalStats = getThermalStats(j5, j6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(thermalStats, 1);
                    return true;
                case 26:
                    long j7 = parcel.readLong();
                    long j8 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<SemProcessUsageStats> processUsageStats = getProcessUsageStats(j7, j8);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(processUsageStats, 1);
                    return true;
                case 27:
                    long j9 = parcel.readLong();
                    long j10 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<SemNetworkUsageStats> networkUsageStats = getNetworkUsageStats(j9, j10);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(networkUsageStats, 1);
                    return true;
                case 28:
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean thermalThrottlingDelta = setThermalThrottlingDelta(i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(thermalThrottlingDelta);
                    return true;
                case 29:
                    String string9 = parcel.readString();
                    int i21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean thermalThrottlingDeltaWithPackageName = setThermalThrottlingDeltaWithPackageName(string9, i21);
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
                    boolean z2 = parcel.readBoolean();
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateSpaOperation(z2, strongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    boolean z3 = parcel.readBoolean();
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    updateGameSdkOperation(z3, strongBinder4);
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
                    long j11 = parcel.readLong();
                    long j12 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    List<OverheatReasonInternal> overheatReason = getOverheatReason(j11, j12);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(overheatReason, 1);
                    return true;
                case 37:
                    String activeSensorList = getActiveSensorList();
                    parcel2.writeNoException();
                    parcel2.writeString(activeSensorList);
                    return true;
                case 38:
                    int i22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean thermalControlFlag = setThermalControlFlag(i22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(thermalControlFlag);
                    return true;
                case 39:
                    int thermalControlFlag2 = getThermalControlFlag();
                    parcel2.writeNoException();
                    parcel2.writeInt(thermalControlFlag2);
                    return true;
                case 40:
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddHeavyLoadApps = addHeavyLoadApps(arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddHeavyLoadApps);
                    return true;
                case 41:
                    List<String> heavyLoadApps = getHeavyLoadApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(heavyLoadApps);
                    return true;
                case 42:
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddLowModeApps = addLowModeApps(arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddLowModeApps);
                    return true;
                case 43:
                    List<String> lowModeApps = getLowModeApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(lowModeApps);
                    return true;
                case 44:
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean zAddLowRefreshRateApps = addLowRefreshRateApps(arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddLowRefreshRateApps);
                    return true;
                case 45:
                    List<String> lowRefreshRateApps = getLowRefreshRateApps();
                    parcel2.writeNoException();
                    parcel2.writeStringList(lowRefreshRateApps);
                    return true;
                case 46:
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int[] allTemperatures = getAllTemperatures(i23);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(allTemperatures);
                    return true;
                case 47:
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iUpdateBatteryStatsInfo = updateBatteryStatsInfo(i24);
                    parcel2.writeNoException();
                    parcel2.writeInt(iUpdateBatteryStatsInfo);
                    return true;
                case 48:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateConfigPart = updateConfigPart(string10, string11, string12);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateConfigPart);
                    return true;
                case 49:
                    String string13 = parcel.readString();
                    String string14 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveConfigPart = removeConfigPart(string13, string14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveConfigPart);
                    return true;
                case 50:
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHighBrightnessMode(z4);
                    parcel2.writeNoException();
                    return true;
                case 51:
                    boolean highBrightnessMode = getHighBrightnessMode();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(highBrightnessMode);
                    return true;
                case 52:
                    String string15 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsGameByGraphic = isGameByGraphic(string15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsGameByGraphic);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logActionWithPkg(String str, int i, String str2, List<Bundle> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logActionWithSource(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void logAnomaly(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void sendCommand(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(5, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRemainingUsageTime(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRemainingUsageTimeWithSettings(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public float[] supportVRTemperaturesInformation(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createFloatArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSsrmStatus(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void initGameSdkMaxlock(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongBinder(iBinder2);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void acquireGameSdkMaxlock(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void releaseGameSdkMaxlock() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void destroyGameSdkMaxlock() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getLRTemperature() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemBatteryStats> getBatteryStats(int i, long j, long j2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemBatteryStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isDownLoadingForUid(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isGameSupportLRP() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getRUT(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<CoolingDevice> getCoolingDevices() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(CoolingDevice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<Temperature> getTemperatures() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Temperature.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void registerCallback(IThermalEventListener iThermalEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iThermalEventListener);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getTemperature(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemBatteryEventHistory> getBatteryEventHistory(long j, long j2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemBatteryEventHistory.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSupportedHistoryTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemThermalStats> getThermalStats(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemThermalStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemProcessUsageStats> getProcessUsageStats(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemProcessUsageStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<SemNetworkUsageStats> getNetworkUsageStats(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(SemNetworkUsageStats.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalThrottlingDelta(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalThrottlingDeltaWithPackageName(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getThermalThrottlingDelta() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getSupportedThermalThrottlingDelta() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void updateSpaOperation(boolean z, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void updateGameSdkOperation(boolean z, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public Bundle getGameSiopInfo() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) parcelObtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setAnomalyConfig(PendingIntent pendingIntent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeTypedObject(pendingIntent, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<OverheatReasonInternal> getOverheatReason(long j, long j2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeLong(j2);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(OverheatReasonInternal.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public String getActiveSensorList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean setThermalControlFlag(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int getThermalControlFlag() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addHeavyLoadApps(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getHeavyLoadApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addLowModeApps(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getLowModeApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean addLowRefreshRateApps(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public List<String> getLowRefreshRateApps() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int[] getAllTemperatures(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public int updateBatteryStatsInfo(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean updateConfigPart(String str, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean removeConfigPart(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public void setHighBrightnessMode(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean getHighBrightnessMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.sec.android.sdhms.ISamsungDeviceHealthManager
            public boolean isGameByGraphic(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISamsungDeviceHealthManager.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
