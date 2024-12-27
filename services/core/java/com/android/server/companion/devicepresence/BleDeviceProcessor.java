package com.android.server.companion.devicepresence;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.companion.AssociationInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Slog;

import com.android.server.companion.association.AssociationStore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public final class BleDeviceProcessor implements AssociationStore.OnChangeListener {
    public static final ScanSettings SCAN_SETTINGS;
    public final AssociationStore mAssociationStore;
    public BluetoothLeScanner mBleScanner;
    public BluetoothAdapter mBtAdapter;
    public final DevicePresenceProcessor mCallback;
    public boolean mScanning = false;
    public final AnonymousClass2 mScanCallback =
            new ScanCallback() { // from class:
                                 // com.android.server.companion.devicepresence.BleDeviceProcessor.2
                @Override // android.bluetooth.le.ScanCallback
                public final void onScanFailed(int i) {
                    BleDeviceProcessor.this.mScanning = false;
                }

                @Override // android.bluetooth.le.ScanCallback
                public final void onScanResult(int i, ScanResult scanResult) {
                    BluetoothDevice device = scanResult.getDevice();
                    if (i == 2) {
                        BleDeviceProcessor bleDeviceProcessor = BleDeviceProcessor.this;
                        for (AssociationInfo associationInfo :
                                bleDeviceProcessor.mAssociationStore.getActiveAssociationsByAddress(
                                        device.getAddress())) {
                            bleDeviceProcessor.mCallback.onBleCompanionDeviceFound(
                                    associationInfo.getId(), associationInfo.getUserId());
                        }
                        return;
                    }
                    if (i != 4) {
                        StringBuilder sb = new StringBuilder("Unexpected callback ");
                        sb.append(
                                (i != 1
                                                ? i != 2
                                                        ? i != 4 ? "Unknown" : "MATCH_LOST"
                                                        : "FIRST_MATCH"
                                                : "ALL_MATCHES")
                                        + "("
                                        + i
                                        + ")");
                        Slog.wtf("CDM_BleDeviceProcessor", sb.toString());
                        return;
                    }
                    BleDeviceProcessor bleDeviceProcessor2 = BleDeviceProcessor.this;
                    for (AssociationInfo associationInfo2 :
                            bleDeviceProcessor2.mAssociationStore.getActiveAssociationsByAddress(
                                    device.getAddress())) {
                        int id = associationInfo2.getId();
                        int userId = associationInfo2.getUserId();
                        DevicePresenceProcessor devicePresenceProcessor =
                                bleDeviceProcessor2.mCallback;
                        devicePresenceProcessor.getClass();
                        Slog.i(
                                "CDM_DevicePresenceProcessor",
                                "onBleCompanionDeviceLost associationId( " + id + " )");
                        if (devicePresenceProcessor.mUserManager.isUserUnlockingOrUnlocked(
                                userId)) {
                            devicePresenceProcessor.onDevicePresenceEvent(
                                    devicePresenceProcessor.mNearbyBleDevices, id, 1);
                        } else {
                            devicePresenceProcessor.onDeviceLocked(id, userId, 0, null);
                        }
                    }
                }
            };

    /* renamed from: com.android.server.companion.devicepresence.BleDeviceProcessor$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public AnonymousClass1() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            new Handler(Looper.getMainLooper())
                    .post(new BleDeviceProcessor$$ExternalSyntheticLambda0(1, this));
        }
    }

    static {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setCallbackType(6);
        builder.setScanMode(0);
        Slog.i(
                "CDM_BleDeviceProcessor",
                "getScanSettings: Applying Custom Mode & Rssi Threshold-75");
        builder.setMatchMode(101);
        builder.semSetScanFilterRssiThreshold(-75);
        builder.setNumOfMatches(1);
        SCAN_SETTINGS = builder.build();
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.companion.devicepresence.BleDeviceProcessor$2] */
    public BleDeviceProcessor(
            AssociationStore associationStore, DevicePresenceProcessor devicePresenceProcessor) {
        this.mAssociationStore = associationStore;
        this.mCallback = devicePresenceProcessor;
    }

    public final void checkBleState() {
        BluetoothAdapter bluetoothAdapter = this.mBtAdapter;
        if (bluetoothAdapter == null) {
            throw new IllegalStateException("BleDeviceProcessor is not initialized");
        }
        boolean isLeEnabled = bluetoothAdapter.isLeEnabled();
        if (!isLeEnabled || this.mBleScanner == null) {
            if (isLeEnabled || this.mBleScanner != null) {
                if (!isLeEnabled) {
                    stopScanIfNeeded();
                    this.mBleScanner = null;
                    return;
                }
                BluetoothLeScanner bluetoothLeScanner = this.mBtAdapter.getBluetoothLeScanner();
                this.mBleScanner = bluetoothLeScanner;
                if (bluetoothLeScanner == null) {
                    return;
                }
                startScan();
            }
        }
    }

    @Override // com.android.server.companion.association.AssociationStore.OnChangeListener
    public final void onAssociationChanged(int i, AssociationInfo associationInfo) {
        if (!Looper.getMainLooper().isCurrentThread()) {
            new Handler(Looper.getMainLooper())
                    .post(new BleDeviceProcessor$$ExternalSyntheticLambda0(0, this));
        } else {
            if (this.mBtAdapter == null) {
                throw new IllegalStateException("BleDeviceProcessor is not initialized");
            }
            if (this.mBleScanner == null) {
                return;
            }
            stopScanIfNeeded();
            startScan();
        }
    }

    public final void startScan() {
        String deviceMacAddressAsString;
        if (this.mBtAdapter == null) {
            throw new IllegalStateException("BleDeviceProcessor is not initialized");
        }
        Slog.i("CDM_BleDeviceProcessor", "startBleScan()");
        if (this.mScanning) {
            Slog.w("CDM_BleDeviceProcessor", "Scan is already in progress.");
            return;
        }
        if (this.mBleScanner == null) {
            Slog.w("CDM_BleDeviceProcessor", "BLE is not available.");
            return;
        }
        HashSet hashSet = new HashSet();
        for (AssociationInfo associationInfo : this.mAssociationStore.getActiveAssociations()) {
            if (associationInfo.isNotifyOnDeviceNearby()
                    && (deviceMacAddressAsString = associationInfo.getDeviceMacAddressAsString())
                            != null) {
                hashSet.add(deviceMacAddressAsString);
            }
        }
        if (hashSet.isEmpty()) {
            return;
        }
        ArrayList arrayList = new ArrayList(hashSet.size());
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            arrayList.add(new ScanFilter.Builder().setDeviceAddress((String) it.next()).build());
        }
        if (!this.mBtAdapter.isLeEnabled()) {
            Slog.w("CDM_BleDeviceProcessor", "BLE scanning is not turned on");
            return;
        }
        try {
            this.mBleScanner.startScan(arrayList, SCAN_SETTINGS, this.mScanCallback);
            this.mScanning = true;
        } catch (IllegalStateException e) {
            Slog.w("CDM_BleDeviceProcessor", "Exception while starting BLE scanning", e);
        }
    }

    public final void stopScanIfNeeded() {
        if (this.mBtAdapter == null) {
            throw new IllegalStateException("BleDeviceProcessor is not initialized");
        }
        Slog.i("CDM_BleDeviceProcessor", "stopBleScan()");
        if (this.mScanning) {
            if (this.mBtAdapter.isLeEnabled()) {
                try {
                    this.mBleScanner.stopScan(this.mScanCallback);
                } catch (IllegalStateException e) {
                    Slog.w("CDM_BleDeviceProcessor", "Exception while stopping BLE scanning", e);
                }
            } else {
                Slog.w("CDM_BleDeviceProcessor", "BLE scanning is not turned on");
            }
            this.mScanning = false;
        }
    }
}
