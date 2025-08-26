package com.samsung.android.knox.deviceinfo;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public interface IDeviceInfo extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.deviceinfo.IDeviceInfo";

    boolean clearCallingLog(ContextInfo contextInfo) throws RemoteException;

    boolean clearMMSLog(ContextInfo contextInfo) throws RemoteException;

    boolean clearSMSLog(ContextInfo contextInfo) throws RemoteException;

    void dataUsageTimerActivation(ContextInfo contextInfo) throws RemoteException;

    boolean enableCallingCapture(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableMMSCapture(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean enableSMSCapture(ContextInfo contextInfo, boolean z) throws RemoteException;

    long getAvailableCapacityExternal(ContextInfo contextInfo) throws RemoteException;

    long getAvailableCapacityInternal(ContextInfo contextInfo) throws RemoteException;

    long getAvailableRamMemory(ContextInfo contextInfo) throws RemoteException;

    long getBytesReceivedNetwork(ContextInfo contextInfo) throws RemoteException;

    long getBytesReceivedWiFi(ContextInfo contextInfo) throws RemoteException;

    long getBytesSentNetwork(ContextInfo contextInfo) throws RemoteException;

    long getBytesSentWiFi(ContextInfo contextInfo) throws RemoteException;

    String getCellTowerCID(ContextInfo contextInfo) throws RemoteException;

    String getCellTowerLAC(ContextInfo contextInfo) throws RemoteException;

    String getCellTowerPSC(ContextInfo contextInfo) throws RemoteException;

    String getCellTowerRSSI(ContextInfo contextInfo) throws RemoteException;

    List<String> getDataCallLog(ContextInfo contextInfo, String str) throws RemoteException;

    boolean getDataCallLoggingEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean getDataCallStatisticsEnabled(ContextInfo contextInfo) throws RemoteException;

    int getDataUsageTimer(ContextInfo contextInfo) throws RemoteException;

    String getDeviceMaker(ContextInfo contextInfo) throws RemoteException;

    String getDeviceName(ContextInfo contextInfo) throws RemoteException;

    String getDeviceOS(ContextInfo contextInfo) throws RemoteException;

    String getDeviceOSVersion(ContextInfo contextInfo) throws RemoteException;

    String getDevicePlatform(ContextInfo contextInfo) throws RemoteException;

    String getDeviceProcessorSpeed(ContextInfo contextInfo) throws RemoteException;

    String getDeviceProcessorType(ContextInfo contextInfo) throws RemoteException;

    int getDroppedCallsCount(ContextInfo contextInfo) throws RemoteException;

    List<String> getInboundMMSCaptured(ContextInfo contextInfo) throws RemoteException;

    List<String> getInboundSMSCaptured(ContextInfo contextInfo) throws RemoteException;

    List<String> getIncomingCallingCaptured(ContextInfo contextInfo) throws RemoteException;

    String getKnoxServiceId(ContextInfo contextInfo) throws RemoteException;

    List<String> getKnoxServicePackageList(ContextInfo contextInfo) throws RemoteException;

    int getMissedCallsCount(ContextInfo contextInfo) throws RemoteException;

    String getModelName(ContextInfo contextInfo) throws RemoteException;

    String getModelNumber(ContextInfo contextInfo) throws RemoteException;

    String getModemFirmware(ContextInfo contextInfo) throws RemoteException;

    List<String> getOutboundMMSCaptured(ContextInfo contextInfo) throws RemoteException;

    List<String> getOutboundSMSCaptured(ContextInfo contextInfo) throws RemoteException;

    List<String> getOutgoingCallingCaptured(ContextInfo contextInfo) throws RemoteException;

    int getPlatformSDK(ContextInfo contextInfo) throws RemoteException;

    String getPlatformVersion(ContextInfo contextInfo) throws RemoteException;

    String getPlatformVersionName(ContextInfo contextInfo) throws RemoteException;

    String getSalesCode(ContextInfo contextInfo) throws RemoteException;

    String getSerialNumber(ContextInfo contextInfo) throws RemoteException;

    int getSuccessCallsCount(ContextInfo contextInfo) throws RemoteException;

    long getTotalCapacityExternal(ContextInfo contextInfo) throws RemoteException;

    long getTotalCapacityInternal(ContextInfo contextInfo) throws RemoteException;

    long getTotalRamMemory(ContextInfo contextInfo) throws RemoteException;

    boolean getWifiStatisticEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isCallingCaptureEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isDeviceLocked(ContextInfo contextInfo) throws RemoteException;

    boolean isDeviceSecure(ContextInfo contextInfo) throws RemoteException;

    boolean isMMSCaptureEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isSMSCaptureEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean resetCallsCount(ContextInfo contextInfo) throws RemoteException;

    boolean resetDataCallLogging(ContextInfo contextInfo, String str) throws RemoteException;

    void resetDataUsage(ContextInfo contextInfo) throws RemoteException;

    boolean setDataCallLoggingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setDataCallStatisticsEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean setDataUsageTimer(ContextInfo contextInfo, int i) throws RemoteException;

    boolean setKnoxServiceId(ContextInfo contextInfo, List<String> list, String str) throws RemoteException;

    boolean setWifiStatisticEnabled(ContextInfo contextInfo, boolean z) throws RemoteException;

    void storeCalling(String str, String str2, String str3, String str4, boolean z) throws RemoteException;

    void storeMMS(String str, String str2, String str3, boolean z) throws RemoteException;

    void storeSMS(String str, String str2, String str3, boolean z) throws RemoteException;

    public abstract class Stub extends Binder implements IDeviceInfo {
        public static final int TRANSACTION_clearCallingLog = 25;
        public static final int TRANSACTION_clearMMSLog = 30;
        public static final int TRANSACTION_clearSMSLog = 19;
        public static final int TRANSACTION_dataUsageTimerActivation = 32;
        public static final int TRANSACTION_enableCallingCapture = 20;
        public static final int TRANSACTION_enableMMSCapture = 26;
        public static final int TRANSACTION_enableSMSCapture = 14;
        public static final int TRANSACTION_getAvailableCapacityExternal = 4;
        public static final int TRANSACTION_getAvailableCapacityInternal = 6;
        public static final int TRANSACTION_getAvailableRamMemory = 53;
        public static final int TRANSACTION_getBytesReceivedNetwork = 41;
        public static final int TRANSACTION_getBytesReceivedWiFi = 39;
        public static final int TRANSACTION_getBytesSentNetwork = 40;
        public static final int TRANSACTION_getBytesSentWiFi = 38;
        public static final int TRANSACTION_getCellTowerCID = 49;
        public static final int TRANSACTION_getCellTowerLAC = 50;
        public static final int TRANSACTION_getCellTowerPSC = 51;
        public static final int TRANSACTION_getCellTowerRSSI = 52;
        public static final int TRANSACTION_getDataCallLog = 48;
        public static final int TRANSACTION_getDataCallLoggingEnabled = 46;
        public static final int TRANSACTION_getDataCallStatisticsEnabled = 37;
        public static final int TRANSACTION_getDataUsageTimer = 44;
        public static final int TRANSACTION_getDeviceMaker = 54;
        public static final int TRANSACTION_getDeviceName = 55;
        public static final int TRANSACTION_getDeviceOS = 8;
        public static final int TRANSACTION_getDeviceOSVersion = 9;
        public static final int TRANSACTION_getDevicePlatform = 56;
        public static final int TRANSACTION_getDeviceProcessorSpeed = 58;
        public static final int TRANSACTION_getDeviceProcessorType = 57;
        public static final int TRANSACTION_getDroppedCallsCount = 10;
        public static final int TRANSACTION_getInboundMMSCaptured = 29;
        public static final int TRANSACTION_getInboundSMSCaptured = 17;
        public static final int TRANSACTION_getIncomingCallingCaptured = 23;
        public static final int TRANSACTION_getKnoxServiceId = 67;
        public static final int TRANSACTION_getKnoxServicePackageList = 68;
        public static final int TRANSACTION_getMissedCallsCount = 11;
        public static final int TRANSACTION_getModelName = 59;
        public static final int TRANSACTION_getModelNumber = 60;
        public static final int TRANSACTION_getModemFirmware = 61;
        public static final int TRANSACTION_getOutboundMMSCaptured = 28;
        public static final int TRANSACTION_getOutboundSMSCaptured = 16;
        public static final int TRANSACTION_getOutgoingCallingCaptured = 22;
        public static final int TRANSACTION_getPlatformSDK = 62;
        public static final int TRANSACTION_getPlatformVersion = 64;
        public static final int TRANSACTION_getPlatformVersionName = 63;
        public static final int TRANSACTION_getSalesCode = 33;
        public static final int TRANSACTION_getSerialNumber = 7;
        public static final int TRANSACTION_getSuccessCallsCount = 12;
        public static final int TRANSACTION_getTotalCapacityExternal = 3;
        public static final int TRANSACTION_getTotalCapacityInternal = 5;
        public static final int TRANSACTION_getTotalRamMemory = 65;
        public static final int TRANSACTION_getWifiStatisticEnabled = 35;
        public static final int TRANSACTION_isCallingCaptureEnabled = 21;
        public static final int TRANSACTION_isDeviceLocked = 2;
        public static final int TRANSACTION_isDeviceSecure = 1;
        public static final int TRANSACTION_isMMSCaptureEnabled = 27;
        public static final int TRANSACTION_isSMSCaptureEnabled = 15;
        public static final int TRANSACTION_resetCallsCount = 13;
        public static final int TRANSACTION_resetDataCallLogging = 47;
        public static final int TRANSACTION_resetDataUsage = 42;
        public static final int TRANSACTION_setDataCallLoggingEnabled = 45;
        public static final int TRANSACTION_setDataCallStatisticsEnabled = 36;
        public static final int TRANSACTION_setDataUsageTimer = 43;
        public static final int TRANSACTION_setKnoxServiceId = 66;
        public static final int TRANSACTION_setWifiStatisticEnabled = 34;
        public static final int TRANSACTION_storeCalling = 24;
        public static final int TRANSACTION_storeMMS = 31;
        public static final int TRANSACTION_storeSMS = 18;

        class Proxy implements IDeviceInfo {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean clearCallingLog(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean clearMMSLog(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean clearSMSLog(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public void dataUsageTimerActivation(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean enableCallingCapture(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean enableMMSCapture(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean enableSMSCapture(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getAvailableCapacityExternal(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getAvailableCapacityInternal(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getAvailableRamMemory(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getBytesReceivedNetwork(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getBytesReceivedWiFi(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getBytesSentNetwork(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getBytesSentWiFi(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getCellTowerCID(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getCellTowerLAC(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getCellTowerPSC(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getCellTowerRSSI(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public List<String> getDataCallLog(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean getDataCallLoggingEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean getDataCallStatisticsEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public int getDataUsageTimer(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getDeviceMaker(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getDeviceName(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getDeviceOS(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getDeviceOSVersion(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getDevicePlatform(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getDeviceProcessorSpeed(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getDeviceProcessorType(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public int getDroppedCallsCount(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public List<String> getInboundMMSCaptured(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public List<String> getInboundSMSCaptured(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public List<String> getIncomingCallingCaptured(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IDeviceInfo.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getKnoxServiceId(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public List<String> getKnoxServicePackageList(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public int getMissedCallsCount(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getModelName(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getModelNumber(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getModemFirmware(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public List<String> getOutboundMMSCaptured(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public List<String> getOutboundSMSCaptured(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public List<String> getOutgoingCallingCaptured(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public int getPlatformSDK(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getPlatformVersion(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getPlatformVersionName(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getSalesCode(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public String getSerialNumber(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public int getSuccessCallsCount(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getTotalCapacityExternal(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getTotalCapacityInternal(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public long getTotalRamMemory(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean getWifiStatisticEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean isCallingCaptureEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean isDeviceLocked(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean isDeviceSecure(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean isMMSCaptureEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean isSMSCaptureEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean resetCallsCount(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean resetDataCallLogging(ContextInfo contextInfo, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public void resetDataUsage(ContextInfo contextInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean setDataCallLoggingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean setDataCallStatisticsEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean setDataUsageTimer(ContextInfo contextInfo, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean setKnoxServiceId(ContextInfo contextInfo, List<String> list, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public boolean setWifiStatisticEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeTypedObject(contextInfo, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public void storeCalling(String str, String str2, String str3, String str4, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public void storeMMS(String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public void storeSMS(String str, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IDeviceInfo.DESCRIPTOR);
        }

        public static IDeviceInfo asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IDeviceInfo.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDeviceInfo)) ? new Proxy(iBinder) : (IDeviceInfo) iInterfaceQueryLocalInterface;
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "isDeviceSecure";
                case 2:
                    return "isDeviceLocked";
                case 3:
                    return "getTotalCapacityExternal";
                case 4:
                    return "getAvailableCapacityExternal";
                case 5:
                    return "getTotalCapacityInternal";
                case 6:
                    return "getAvailableCapacityInternal";
                case 7:
                    return "getSerialNumber";
                case 8:
                    return "getDeviceOS";
                case 9:
                    return "getDeviceOSVersion";
                case 10:
                    return "getDroppedCallsCount";
                case 11:
                    return "getMissedCallsCount";
                case 12:
                    return "getSuccessCallsCount";
                case 13:
                    return "resetCallsCount";
                case 14:
                    return "enableSMSCapture";
                case 15:
                    return "isSMSCaptureEnabled";
                case 16:
                    return "getOutboundSMSCaptured";
                case 17:
                    return "getInboundSMSCaptured";
                case 18:
                    return "storeSMS";
                case 19:
                    return "clearSMSLog";
                case 20:
                    return "enableCallingCapture";
                case 21:
                    return "isCallingCaptureEnabled";
                case 22:
                    return "getOutgoingCallingCaptured";
                case 23:
                    return "getIncomingCallingCaptured";
                case 24:
                    return "storeCalling";
                case 25:
                    return "clearCallingLog";
                case 26:
                    return "enableMMSCapture";
                case 27:
                    return "isMMSCaptureEnabled";
                case 28:
                    return "getOutboundMMSCaptured";
                case 29:
                    return "getInboundMMSCaptured";
                case 30:
                    return "clearMMSLog";
                case 31:
                    return "storeMMS";
                case 32:
                    return "dataUsageTimerActivation";
                case 33:
                    return "getSalesCode";
                case 34:
                    return "setWifiStatisticEnabled";
                case 35:
                    return "getWifiStatisticEnabled";
                case 36:
                    return "setDataCallStatisticsEnabled";
                case 37:
                    return "getDataCallStatisticsEnabled";
                case 38:
                    return "getBytesSentWiFi";
                case 39:
                    return "getBytesReceivedWiFi";
                case 40:
                    return "getBytesSentNetwork";
                case 41:
                    return "getBytesReceivedNetwork";
                case 42:
                    return "resetDataUsage";
                case 43:
                    return "setDataUsageTimer";
                case 44:
                    return "getDataUsageTimer";
                case 45:
                    return "setDataCallLoggingEnabled";
                case 46:
                    return "getDataCallLoggingEnabled";
                case 47:
                    return "resetDataCallLogging";
                case 48:
                    return "getDataCallLog";
                case 49:
                    return "getCellTowerCID";
                case 50:
                    return "getCellTowerLAC";
                case 51:
                    return "getCellTowerPSC";
                case 52:
                    return "getCellTowerRSSI";
                case 53:
                    return "getAvailableRamMemory";
                case 54:
                    return "getDeviceMaker";
                case 55:
                    return "getDeviceName";
                case 56:
                    return "getDevicePlatform";
                case 57:
                    return "getDeviceProcessorType";
                case 58:
                    return "getDeviceProcessorSpeed";
                case 59:
                    return "getModelName";
                case 60:
                    return "getModelNumber";
                case 61:
                    return "getModemFirmware";
                case 62:
                    return "getPlatformSDK";
                case 63:
                    return "getPlatformVersionName";
                case 64:
                    return "getPlatformVersion";
                case 65:
                    return "getTotalRamMemory";
                case 66:
                    return "setKnoxServiceId";
                case 67:
                    return "getKnoxServiceId";
                case 68:
                    return "getKnoxServicePackageList";
                default:
                    return null;
            }
        }

        public int getMaxTransactionId() {
            return 67;
        }

        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IDeviceInfo.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IDeviceInfo.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDeviceSecure = isDeviceSecure(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceSecure);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsDeviceLocked = isDeviceLocked(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsDeviceLocked);
                    return true;
                case 3:
                    ContextInfo contextInfo3 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long totalCapacityExternal = getTotalCapacityExternal(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeLong(totalCapacityExternal);
                    return true;
                case 4:
                    ContextInfo contextInfo4 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long availableCapacityExternal = getAvailableCapacityExternal(contextInfo4);
                    parcel2.writeNoException();
                    parcel2.writeLong(availableCapacityExternal);
                    return true;
                case 5:
                    ContextInfo contextInfo5 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long totalCapacityInternal = getTotalCapacityInternal(contextInfo5);
                    parcel2.writeNoException();
                    parcel2.writeLong(totalCapacityInternal);
                    return true;
                case 6:
                    ContextInfo contextInfo6 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long availableCapacityInternal = getAvailableCapacityInternal(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeLong(availableCapacityInternal);
                    return true;
                case 7:
                    ContextInfo contextInfo7 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String serialNumber = getSerialNumber(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeString(serialNumber);
                    return true;
                case 8:
                    ContextInfo contextInfo8 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceOS = getDeviceOS(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceOS);
                    return true;
                case 9:
                    ContextInfo contextInfo9 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceOSVersion = getDeviceOSVersion(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceOSVersion);
                    return true;
                case 10:
                    ContextInfo contextInfo10 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int droppedCallsCount = getDroppedCallsCount(contextInfo10);
                    parcel2.writeNoException();
                    parcel2.writeInt(droppedCallsCount);
                    return true;
                case 11:
                    ContextInfo contextInfo11 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int missedCallsCount = getMissedCallsCount(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeInt(missedCallsCount);
                    return true;
                case 12:
                    ContextInfo contextInfo12 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int successCallsCount = getSuccessCallsCount(contextInfo12);
                    parcel2.writeNoException();
                    parcel2.writeInt(successCallsCount);
                    return true;
                case 13:
                    ContextInfo contextInfo13 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zResetCallsCount = resetCallsCount(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetCallsCount);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableSMSCapture = enableSMSCapture(contextInfo14, z);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableSMSCapture);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsSMSCaptureEnabled = isSMSCaptureEnabled(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSMSCaptureEnabled);
                    return true;
                case 16:
                    ContextInfo contextInfo16 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> outboundSMSCaptured = getOutboundSMSCaptured(contextInfo16);
                    parcel2.writeNoException();
                    parcel2.writeStringList(outboundSMSCaptured);
                    return true;
                case 17:
                    ContextInfo contextInfo17 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> inboundSMSCaptured = getInboundSMSCaptured(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeStringList(inboundSMSCaptured);
                    return true;
                case 18:
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    String string3 = parcel.readString();
                    boolean z2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeSMS(string, string2, string3, z2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearSMSLog = clearSMSLog(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearSMSLog);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableCallingCapture = enableCallingCapture(contextInfo19, z3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableCallingCapture);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsCallingCaptureEnabled = isCallingCaptureEnabled(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsCallingCaptureEnabled);
                    return true;
                case 22:
                    ContextInfo contextInfo21 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> outgoingCallingCaptured = getOutgoingCallingCaptured(contextInfo21);
                    parcel2.writeNoException();
                    parcel2.writeStringList(outgoingCallingCaptured);
                    return true;
                case 23:
                    ContextInfo contextInfo22 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> incomingCallingCaptured = getIncomingCallingCaptured(contextInfo22);
                    parcel2.writeNoException();
                    parcel2.writeStringList(incomingCallingCaptured);
                    return true;
                case 24:
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    String string6 = parcel.readString();
                    String string7 = parcel.readString();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeCalling(string4, string5, string6, string7, z4);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearCallingLog = clearCallingLog(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearCallingLog);
                    return true;
                case 26:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zEnableMMSCapture = enableMMSCapture(contextInfo24, z5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnableMMSCapture);
                    return true;
                case 27:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsMMSCaptureEnabled = isMMSCaptureEnabled(contextInfo25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsMMSCaptureEnabled);
                    return true;
                case 28:
                    ContextInfo contextInfo26 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> outboundMMSCaptured = getOutboundMMSCaptured(contextInfo26);
                    parcel2.writeNoException();
                    parcel2.writeStringList(outboundMMSCaptured);
                    return true;
                case 29:
                    ContextInfo contextInfo27 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> inboundMMSCaptured = getInboundMMSCaptured(contextInfo27);
                    parcel2.writeNoException();
                    parcel2.writeStringList(inboundMMSCaptured);
                    return true;
                case 30:
                    ContextInfo contextInfo28 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zClearMMSLog = clearMMSLog(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zClearMMSLog);
                    return true;
                case 31:
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    String string10 = parcel.readString();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeMMS(string8, string9, string10, z6);
                    parcel2.writeNoException();
                    return true;
                case 32:
                    ContextInfo contextInfo29 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    dataUsageTimerActivation(contextInfo29);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    ContextInfo contextInfo30 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String salesCode = getSalesCode(contextInfo30);
                    parcel2.writeNoException();
                    parcel2.writeString(salesCode);
                    return true;
                case 34:
                    ContextInfo contextInfo31 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiStatisticEnabled = setWifiStatisticEnabled(contextInfo31, z7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiStatisticEnabled);
                    return true;
                case 35:
                    ContextInfo contextInfo32 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean wifiStatisticEnabled2 = getWifiStatisticEnabled(contextInfo32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wifiStatisticEnabled2);
                    return true;
                case 36:
                    ContextInfo contextInfo33 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataCallStatisticsEnabled = setDataCallStatisticsEnabled(contextInfo33, z8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataCallStatisticsEnabled);
                    return true;
                case 37:
                    ContextInfo contextInfo34 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean dataCallStatisticsEnabled2 = getDataCallStatisticsEnabled(contextInfo34);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataCallStatisticsEnabled2);
                    return true;
                case 38:
                    ContextInfo contextInfo35 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long bytesSentWiFi = getBytesSentWiFi(contextInfo35);
                    parcel2.writeNoException();
                    parcel2.writeLong(bytesSentWiFi);
                    return true;
                case 39:
                    ContextInfo contextInfo36 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long bytesReceivedWiFi = getBytesReceivedWiFi(contextInfo36);
                    parcel2.writeNoException();
                    parcel2.writeLong(bytesReceivedWiFi);
                    return true;
                case 40:
                    ContextInfo contextInfo37 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long bytesSentNetwork = getBytesSentNetwork(contextInfo37);
                    parcel2.writeNoException();
                    parcel2.writeLong(bytesSentNetwork);
                    return true;
                case 41:
                    ContextInfo contextInfo38 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long bytesReceivedNetwork = getBytesReceivedNetwork(contextInfo38);
                    parcel2.writeNoException();
                    parcel2.writeLong(bytesReceivedNetwork);
                    return true;
                case 42:
                    ContextInfo contextInfo39 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    resetDataUsage(contextInfo39);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    ContextInfo contextInfo40 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dataUsageTimer = setDataUsageTimer(contextInfo40, i3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataUsageTimer);
                    return true;
                case 44:
                    ContextInfo contextInfo41 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int dataUsageTimer2 = getDataUsageTimer(contextInfo41);
                    parcel2.writeNoException();
                    parcel2.writeInt(dataUsageTimer2);
                    return true;
                case 45:
                    ContextInfo contextInfo42 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataCallLoggingEnabled = setDataCallLoggingEnabled(contextInfo42, z9);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataCallLoggingEnabled);
                    return true;
                case 46:
                    ContextInfo contextInfo43 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean dataCallLoggingEnabled2 = getDataCallLoggingEnabled(contextInfo43);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dataCallLoggingEnabled2);
                    return true;
                case 47:
                    ContextInfo contextInfo44 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zResetDataCallLogging = resetDataCallLogging(contextInfo44, string11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zResetDataCallLogging);
                    return true;
                case 48:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String string12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> dataCallLog = getDataCallLog(contextInfo45, string12);
                    parcel2.writeNoException();
                    parcel2.writeStringList(dataCallLog);
                    return true;
                case 49:
                    ContextInfo contextInfo46 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String cellTowerCID = getCellTowerCID(contextInfo46);
                    parcel2.writeNoException();
                    parcel2.writeString(cellTowerCID);
                    return true;
                case 50:
                    ContextInfo contextInfo47 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String cellTowerLAC = getCellTowerLAC(contextInfo47);
                    parcel2.writeNoException();
                    parcel2.writeString(cellTowerLAC);
                    return true;
                case 51:
                    ContextInfo contextInfo48 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String cellTowerPSC = getCellTowerPSC(contextInfo48);
                    parcel2.writeNoException();
                    parcel2.writeString(cellTowerPSC);
                    return true;
                case 52:
                    ContextInfo contextInfo49 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String cellTowerRSSI = getCellTowerRSSI(contextInfo49);
                    parcel2.writeNoException();
                    parcel2.writeString(cellTowerRSSI);
                    return true;
                case 53:
                    ContextInfo contextInfo50 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long availableRamMemory = getAvailableRamMemory(contextInfo50);
                    parcel2.writeNoException();
                    parcel2.writeLong(availableRamMemory);
                    return true;
                case 54:
                    ContextInfo contextInfo51 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceMaker = getDeviceMaker(contextInfo51);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceMaker);
                    return true;
                case 55:
                    ContextInfo contextInfo52 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceName = getDeviceName(contextInfo52);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceName);
                    return true;
                case 56:
                    ContextInfo contextInfo53 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String devicePlatform = getDevicePlatform(contextInfo53);
                    parcel2.writeNoException();
                    parcel2.writeString(devicePlatform);
                    return true;
                case 57:
                    ContextInfo contextInfo54 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceProcessorType = getDeviceProcessorType(contextInfo54);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceProcessorType);
                    return true;
                case 58:
                    ContextInfo contextInfo55 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String deviceProcessorSpeed = getDeviceProcessorSpeed(contextInfo55);
                    parcel2.writeNoException();
                    parcel2.writeString(deviceProcessorSpeed);
                    return true;
                case 59:
                    ContextInfo contextInfo56 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String modelName = getModelName(contextInfo56);
                    parcel2.writeNoException();
                    parcel2.writeString(modelName);
                    return true;
                case 60:
                    ContextInfo contextInfo57 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String modelNumber = getModelNumber(contextInfo57);
                    parcel2.writeNoException();
                    parcel2.writeString(modelNumber);
                    return true;
                case 61:
                    ContextInfo contextInfo58 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String modemFirmware = getModemFirmware(contextInfo58);
                    parcel2.writeNoException();
                    parcel2.writeString(modemFirmware);
                    return true;
                case 62:
                    ContextInfo contextInfo59 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int platformSDK = getPlatformSDK(contextInfo59);
                    parcel2.writeNoException();
                    parcel2.writeInt(platformSDK);
                    return true;
                case 63:
                    ContextInfo contextInfo60 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String platformVersionName = getPlatformVersionName(contextInfo60);
                    parcel2.writeNoException();
                    parcel2.writeString(platformVersionName);
                    return true;
                case 64:
                    ContextInfo contextInfo61 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String platformVersion = getPlatformVersion(contextInfo61);
                    parcel2.writeNoException();
                    parcel2.writeString(platformVersion);
                    return true;
                case 65:
                    ContextInfo contextInfo62 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    long totalRamMemory = getTotalRamMemory(contextInfo62);
                    parcel2.writeNoException();
                    parcel2.writeLong(totalRamMemory);
                    return true;
                case 66:
                    ContextInfo contextInfo63 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    String string13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean knoxServiceId = setKnoxServiceId(contextInfo63, arrayListCreateStringArrayList, string13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(knoxServiceId);
                    return true;
                case 67:
                    ContextInfo contextInfo64 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String knoxServiceId2 = getKnoxServiceId(contextInfo64);
                    parcel2.writeNoException();
                    parcel2.writeString(knoxServiceId2);
                    return true;
                case 68:
                    ContextInfo contextInfo65 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<String> knoxServicePackageList = getKnoxServicePackageList(contextInfo65);
                    parcel2.writeNoException();
                    parcel2.writeStringList(knoxServicePackageList);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    public class Default implements IDeviceInfo {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean clearCallingLog(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean clearMMSLog(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean clearSMSLog(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean enableCallingCapture(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean enableMMSCapture(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean enableSMSCapture(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getAvailableCapacityExternal(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getAvailableCapacityInternal(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getAvailableRamMemory(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getBytesReceivedNetwork(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getBytesReceivedWiFi(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getBytesSentNetwork(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getBytesSentWiFi(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getCellTowerCID(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getCellTowerLAC(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getCellTowerPSC(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getCellTowerRSSI(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public List<String> getDataCallLog(ContextInfo contextInfo, String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean getDataCallLoggingEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean getDataCallStatisticsEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public int getDataUsageTimer(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getDeviceMaker(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getDeviceName(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getDeviceOS(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getDeviceOSVersion(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getDevicePlatform(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getDeviceProcessorSpeed(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getDeviceProcessorType(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public int getDroppedCallsCount(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public List<String> getInboundMMSCaptured(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public List<String> getInboundSMSCaptured(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public List<String> getIncomingCallingCaptured(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getKnoxServiceId(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public List<String> getKnoxServicePackageList(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public int getMissedCallsCount(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getModelName(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getModelNumber(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getModemFirmware(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public List<String> getOutboundMMSCaptured(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public List<String> getOutboundSMSCaptured(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public List<String> getOutgoingCallingCaptured(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public int getPlatformSDK(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getPlatformVersion(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getPlatformVersionName(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getSalesCode(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public String getSerialNumber(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public int getSuccessCallsCount(ContextInfo contextInfo) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getTotalCapacityExternal(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getTotalCapacityInternal(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public long getTotalRamMemory(ContextInfo contextInfo) throws RemoteException {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean getWifiStatisticEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean isCallingCaptureEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean isDeviceLocked(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean isDeviceSecure(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean isMMSCaptureEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean isSMSCaptureEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean resetCallsCount(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean resetDataCallLogging(ContextInfo contextInfo, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean setDataCallLoggingEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean setDataCallStatisticsEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean setDataUsageTimer(ContextInfo contextInfo, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean setKnoxServiceId(ContextInfo contextInfo, List<String> list, String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public boolean setWifiStatisticEnabled(ContextInfo contextInfo, boolean z) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public void dataUsageTimerActivation(ContextInfo contextInfo) throws RemoteException {
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public void resetDataUsage(ContextInfo contextInfo) throws RemoteException {
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public void storeMMS(String str, String str2, String str3, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public void storeSMS(String str, String str2, String str3, boolean z) throws RemoteException {
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public void storeCalling(String str, String str2, String str3, String str4, boolean z) throws RemoteException {
        }
    }
}
