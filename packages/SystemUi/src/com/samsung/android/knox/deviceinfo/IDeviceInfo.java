package com.samsung.android.knox.deviceinfo;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.samsung.android.knox.ContextInfo;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface IDeviceInfo extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.deviceinfo.IDeviceInfo";

    boolean clearCallingLog(ContextInfo contextInfo);

    boolean clearMMSLog(ContextInfo contextInfo);

    boolean clearSMSLog(ContextInfo contextInfo);

    void dataUsageTimerActivation(ContextInfo contextInfo);

    boolean enableCallingCapture(ContextInfo contextInfo, boolean z);

    boolean enableMMSCapture(ContextInfo contextInfo, boolean z);

    boolean enableSMSCapture(ContextInfo contextInfo, boolean z);

    long getAvailableCapacityExternal(ContextInfo contextInfo);

    long getAvailableCapacityInternal(ContextInfo contextInfo);

    long getAvailableRamMemory(ContextInfo contextInfo);

    long getBytesReceivedNetwork(ContextInfo contextInfo);

    long getBytesReceivedWiFi(ContextInfo contextInfo);

    long getBytesSentNetwork(ContextInfo contextInfo);

    long getBytesSentWiFi(ContextInfo contextInfo);

    String getCellTowerCID(ContextInfo contextInfo);

    String getCellTowerLAC(ContextInfo contextInfo);

    String getCellTowerPSC(ContextInfo contextInfo);

    String getCellTowerRSSI(ContextInfo contextInfo);

    List<String> getDataCallLog(ContextInfo contextInfo, String str);

    boolean getDataCallLoggingEnabled(ContextInfo contextInfo);

    boolean getDataCallStatisticsEnabled(ContextInfo contextInfo);

    int getDataUsageTimer(ContextInfo contextInfo);

    String getDeviceMaker(ContextInfo contextInfo);

    String getDeviceName(ContextInfo contextInfo);

    String getDeviceOS(ContextInfo contextInfo);

    String getDeviceOSVersion(ContextInfo contextInfo);

    String getDevicePlatform(ContextInfo contextInfo);

    String getDeviceProcessorSpeed(ContextInfo contextInfo);

    String getDeviceProcessorType(ContextInfo contextInfo);

    int getDroppedCallsCount(ContextInfo contextInfo);

    List<String> getInboundMMSCaptured(ContextInfo contextInfo);

    List<String> getInboundSMSCaptured(ContextInfo contextInfo);

    List<String> getIncomingCallingCaptured(ContextInfo contextInfo);

    String getKnoxServiceId(ContextInfo contextInfo);

    List<String> getKnoxServicePackageList(ContextInfo contextInfo);

    int getMissedCallsCount(ContextInfo contextInfo);

    String getModelName(ContextInfo contextInfo);

    String getModelNumber(ContextInfo contextInfo);

    String getModemFirmware(ContextInfo contextInfo);

    List<String> getOutboundMMSCaptured(ContextInfo contextInfo);

    List<String> getOutboundSMSCaptured(ContextInfo contextInfo);

    List<String> getOutgoingCallingCaptured(ContextInfo contextInfo);

    int getPlatformSDK(ContextInfo contextInfo);

    String getPlatformVersion(ContextInfo contextInfo);

    String getPlatformVersionName(ContextInfo contextInfo);

    String getSalesCode(ContextInfo contextInfo);

    String getSerialNumber(ContextInfo contextInfo);

    int getSuccessCallsCount(ContextInfo contextInfo);

    long getTotalCapacityExternal(ContextInfo contextInfo);

    long getTotalCapacityInternal(ContextInfo contextInfo);

    long getTotalRamMemory(ContextInfo contextInfo);

    boolean getWifiStatisticEnabled(ContextInfo contextInfo);

    boolean isCallingCaptureEnabled(ContextInfo contextInfo);

    boolean isDeviceLocked(ContextInfo contextInfo);

    boolean isDeviceSecure(ContextInfo contextInfo);

    boolean isMMSCaptureEnabled(ContextInfo contextInfo);

    boolean isSMSCaptureEnabled(ContextInfo contextInfo);

    boolean resetCallsCount(ContextInfo contextInfo);

    boolean resetDataCallLogging(ContextInfo contextInfo, String str);

    void resetDataUsage(ContextInfo contextInfo);

    boolean setDataCallLoggingEnabled(ContextInfo contextInfo, boolean z);

    boolean setDataCallStatisticsEnabled(ContextInfo contextInfo, boolean z);

    boolean setDataUsageTimer(ContextInfo contextInfo, int i);

    boolean setKnoxServiceId(ContextInfo contextInfo, List<String> list, String str);

    boolean setWifiStatisticEnabled(ContextInfo contextInfo, boolean z);

    void storeCalling(String str, String str2, String str3, String str4, boolean z);

    void storeMMS(String str, String str2, String str3, boolean z);

    void storeSMS(String str, String str2, String str3, boolean z);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static abstract class Stub extends Binder implements IDeviceInfo {
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

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public static class Proxy implements IDeviceInfo {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public final IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean clearCallingLog(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean clearMMSLog(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean clearSMSLog(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final void dataUsageTimerActivation(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean enableCallingCapture(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean enableMMSCapture(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean enableSMSCapture(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getAvailableCapacityExternal(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getAvailableCapacityInternal(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getAvailableRamMemory(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getBytesReceivedNetwork(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getBytesReceivedWiFi(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getBytesSentNetwork(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getBytesSentWiFi(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getCellTowerCID(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getCellTowerLAC(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getCellTowerPSC(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getCellTowerRSSI(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final List<String> getDataCallLog(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean getDataCallLoggingEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean getDataCallStatisticsEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final int getDataUsageTimer(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getDeviceMaker(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getDeviceName(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getDeviceOS(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getDeviceOSVersion(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getDevicePlatform(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getDeviceProcessorSpeed(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getDeviceProcessorType(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final int getDroppedCallsCount(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final List<String> getInboundMMSCaptured(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final List<String> getInboundSMSCaptured(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final List<String> getIncomingCallingCaptured(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public final String getInterfaceDescriptor() {
                return IDeviceInfo.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getKnoxServiceId(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final List<String> getKnoxServicePackageList(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final int getMissedCallsCount(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getModelName(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getModelNumber(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getModemFirmware(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final List<String> getOutboundMMSCaptured(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final List<String> getOutboundSMSCaptured(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final List<String> getOutgoingCallingCaptured(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final int getPlatformSDK(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getPlatformVersion(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getPlatformVersionName(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getSalesCode(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final String getSerialNumber(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final int getSuccessCallsCount(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getTotalCapacityExternal(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getTotalCapacityInternal(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final long getTotalRamMemory(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readLong();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean getWifiStatisticEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean isCallingCaptureEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean isDeviceLocked(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean isDeviceSecure(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean isMMSCaptureEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean isSMSCaptureEnabled(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean resetCallsCount(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean resetDataCallLogging(ContextInfo contextInfo, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final void resetDataUsage(ContextInfo contextInfo) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean setDataCallLoggingEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean setDataCallStatisticsEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean setDataUsageTimer(ContextInfo contextInfo, int i) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean setKnoxServiceId(ContextInfo contextInfo, List<String> list, String str) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    obtain.writeString(str);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final boolean setWifiStatisticEnabled(ContextInfo contextInfo, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final void storeCalling(String str, String str2, String str3, String str4, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final void storeMMS(String str, String str2, String str3, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
            public final void storeSMS(String str, String str2, String str3, boolean z) {
                Parcel obtain = Parcel.obtain(this.mRemote);
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IDeviceInfo.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
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
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IDeviceInfo.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IDeviceInfo)) ? new Proxy(iBinder) : (IDeviceInfo) queryLocalInterface;
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

        public final int getMaxTransactionId() {
            return 67;
        }

        public final String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
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
                    boolean isDeviceSecure = isDeviceSecure(contextInfo);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceSecure);
                    return true;
                case 2:
                    ContextInfo contextInfo2 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDeviceLocked = isDeviceLocked(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDeviceLocked);
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
                    boolean resetCallsCount = resetCallsCount(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetCallsCount);
                    return true;
                case 14:
                    ContextInfo contextInfo14 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableSMSCapture = enableSMSCapture(contextInfo14, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableSMSCapture);
                    return true;
                case 15:
                    ContextInfo contextInfo15 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isSMSCaptureEnabled = isSMSCaptureEnabled(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSMSCaptureEnabled);
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
                    String readString = parcel.readString();
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeSMS(readString, readString2, readString3, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    ContextInfo contextInfo18 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearSMSLog = clearSMSLog(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearSMSLog);
                    return true;
                case 20:
                    ContextInfo contextInfo19 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableCallingCapture = enableCallingCapture(contextInfo19, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableCallingCapture);
                    return true;
                case 21:
                    ContextInfo contextInfo20 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCallingCaptureEnabled = isCallingCaptureEnabled(contextInfo20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCallingCaptureEnabled);
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
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    String readString6 = parcel.readString();
                    String readString7 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeCalling(readString4, readString5, readString6, readString7, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    ContextInfo contextInfo23 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearCallingLog = clearCallingLog(contextInfo23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearCallingLog);
                    return true;
                case 26:
                    ContextInfo contextInfo24 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableMMSCapture = enableMMSCapture(contextInfo24, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableMMSCapture);
                    return true;
                case 27:
                    ContextInfo contextInfo25 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isMMSCaptureEnabled = isMMSCaptureEnabled(contextInfo25);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isMMSCaptureEnabled);
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
                    boolean clearMMSLog = clearMMSLog(contextInfo28);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearMMSLog);
                    return true;
                case 31:
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    storeMMS(readString8, readString9, readString10, readBoolean6);
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
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean wifiStatisticEnabled = setWifiStatisticEnabled(contextInfo31, readBoolean7);
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
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataCallStatisticsEnabled = setDataCallStatisticsEnabled(contextInfo33, readBoolean8);
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
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dataUsageTimer = setDataUsageTimer(contextInfo40, readInt);
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
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean dataCallLoggingEnabled = setDataCallLoggingEnabled(contextInfo42, readBoolean9);
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
                    String readString11 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean resetDataCallLogging = resetDataCallLogging(contextInfo44, readString11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetDataCallLogging);
                    return true;
                case 48:
                    ContextInfo contextInfo45 = (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString12 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> dataCallLog = getDataCallLog(contextInfo45, readString12);
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
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    String readString13 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean knoxServiceId = setKnoxServiceId(contextInfo63, createStringArrayList, readString13);
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
        public final IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public static class Default implements IDeviceInfo {
        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean clearCallingLog(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean clearMMSLog(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean clearSMSLog(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean enableCallingCapture(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean enableMMSCapture(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean enableSMSCapture(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getAvailableCapacityExternal(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getAvailableCapacityInternal(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getAvailableRamMemory(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getBytesReceivedNetwork(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getBytesReceivedWiFi(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getBytesSentNetwork(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getBytesSentWiFi(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getCellTowerCID(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getCellTowerLAC(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getCellTowerPSC(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getCellTowerRSSI(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final List<String> getDataCallLog(ContextInfo contextInfo, String str) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean getDataCallLoggingEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean getDataCallStatisticsEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final int getDataUsageTimer(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getDeviceMaker(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getDeviceName(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getDeviceOS(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getDeviceOSVersion(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getDevicePlatform(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getDeviceProcessorSpeed(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getDeviceProcessorType(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final int getDroppedCallsCount(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final List<String> getInboundMMSCaptured(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final List<String> getInboundSMSCaptured(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final List<String> getIncomingCallingCaptured(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getKnoxServiceId(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final List<String> getKnoxServicePackageList(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final int getMissedCallsCount(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getModelName(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getModelNumber(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getModemFirmware(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final List<String> getOutboundMMSCaptured(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final List<String> getOutboundSMSCaptured(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final List<String> getOutgoingCallingCaptured(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final int getPlatformSDK(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getPlatformVersion(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getPlatformVersionName(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getSalesCode(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final String getSerialNumber(ContextInfo contextInfo) {
            return null;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final int getSuccessCallsCount(ContextInfo contextInfo) {
            return 0;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getTotalCapacityExternal(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getTotalCapacityInternal(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final long getTotalRamMemory(ContextInfo contextInfo) {
            return 0L;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean getWifiStatisticEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean isCallingCaptureEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean isDeviceLocked(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean isDeviceSecure(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean isMMSCaptureEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean isSMSCaptureEnabled(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean resetCallsCount(ContextInfo contextInfo) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean resetDataCallLogging(ContextInfo contextInfo, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean setDataCallLoggingEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean setDataCallStatisticsEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean setDataUsageTimer(ContextInfo contextInfo, int i) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean setKnoxServiceId(ContextInfo contextInfo, List<String> list, String str) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final boolean setWifiStatisticEnabled(ContextInfo contextInfo, boolean z) {
            return false;
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final void dataUsageTimerActivation(ContextInfo contextInfo) {
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final void resetDataUsage(ContextInfo contextInfo) {
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final void storeMMS(String str, String str2, String str3, boolean z) {
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final void storeSMS(String str, String str2, String str3, boolean z) {
        }

        @Override // com.samsung.android.knox.deviceinfo.IDeviceInfo
        public final void storeCalling(String str, String str2, String str3, String str4, boolean z) {
        }
    }
}
