package com.android.server.media;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRoute2Info;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;

import com.android.media.flags.Flags;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BluetoothDeviceRoutesManager {
    public final BluetoothAdapter mBluetoothAdapter;
    public final BluetoothProfileMonitor mBluetoothProfileMonitor;
    public final Context mContext;
    public final Handler mHandler;
    public final BluetoothRouteController.BluetoothRoutesUpdatedListener mListener;
    public final DeviceStateChangedReceiver mAdapterStateChangedReceiver =
            new DeviceStateChangedReceiver(this, 1);
    public final DeviceStateChangedReceiver mDeviceStateChangedReceiver =
            new DeviceStateChangedReceiver(this, 0);
    public Map mAddressToBondedDevice = new HashMap();
    public final Map mBluetoothRoutes = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BluetoothRouteInfo {
        public BluetoothDevice mBtDevice;
        public SparseBooleanArray mConnectedProfiles;
        public MediaRoute2Info mRoute;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceStateChangedReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ BluetoothDeviceRoutesManager this$0;

        public /* synthetic */ DeviceStateChangedReceiver(
                BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager, int i) {
            this.$r8$classId = i;
            this.this$0 = bluetoothDeviceRoutesManager;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            switch (this.$r8$classId) {
                case 0:
                    String action = intent.getAction();
                    action.getClass();
                    switch (action) {
                        case "android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED":
                        case "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED":
                        case "android.bluetooth.device.action.ALIAS_CHANGED":
                        case "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED":
                            if (!Flags.enableMr2ServiceNonMainBgThread()) {
                                this.this$0.updateBluetoothRoutes();
                                this.this$0.mListener.onBluetoothRoutesUpdated();
                                break;
                            } else {
                                this.this$0.mHandler.post(
                                        new Runnable() { // from class:
                                                         // com.android.server.media.BluetoothDeviceRoutesManager$DeviceStateChangedReceiver$$ExternalSyntheticLambda0
                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                BluetoothDeviceRoutesManager
                                                                .DeviceStateChangedReceiver
                                                        deviceStateChangedReceiver =
                                                                BluetoothDeviceRoutesManager
                                                                        .DeviceStateChangedReceiver
                                                                        .this;
                                                deviceStateChangedReceiver.this$0
                                                        .updateBluetoothRoutes();
                                                deviceStateChangedReceiver.this$0.mListener
                                                        .onBluetoothRoutesUpdated();
                                            }
                                        });
                                break;
                            }
                    }
                default:
                    final int intExtra =
                            intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
                    if (!Flags.enableMr2ServiceNonMainBgThread()) {
                        BluetoothDeviceRoutesManager.m646$$Nest$mhandleBluetoothAdapterStateChange(
                                this.this$0, intExtra);
                        break;
                    } else {
                        this.this$0.mHandler.post(
                                new Runnable() { // from class:
                                                 // com.android.server.media.BluetoothDeviceRoutesManager$AdapterStateChangedReceiver$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BluetoothDeviceRoutesManager.DeviceStateChangedReceiver
                                                deviceStateChangedReceiver =
                                                        BluetoothDeviceRoutesManager
                                                                .DeviceStateChangedReceiver.this;
                                        BluetoothDeviceRoutesManager
                                                .m646$$Nest$mhandleBluetoothAdapterStateChange(
                                                        deviceStateChangedReceiver.this$0,
                                                        intExtra);
                                    }
                                });
                        break;
                    }
            }
        }
    }

    /* renamed from: -$$Nest$mhandleBluetoothAdapterStateChange, reason: not valid java name */
    public static void m646$$Nest$mhandleBluetoothAdapterStateChange(
            BluetoothDeviceRoutesManager bluetoothDeviceRoutesManager, int i) {
        boolean z;
        bluetoothDeviceRoutesManager.getClass();
        if (i == 10 || i == 13) {
            synchronized (bluetoothDeviceRoutesManager) {
                ((HashMap) bluetoothDeviceRoutesManager.mBluetoothRoutes).clear();
            }
            bluetoothDeviceRoutesManager.mListener.onBluetoothRoutesUpdated();
        } else if (i == 12) {
            bluetoothDeviceRoutesManager.updateBluetoothRoutes();
            synchronized (bluetoothDeviceRoutesManager) {
                z = !((HashMap) bluetoothDeviceRoutesManager.mBluetoothRoutes).isEmpty();
            }
            if (z) {
                bluetoothDeviceRoutesManager.mListener.onBluetoothRoutesUpdated();
            }
        }
    }

    public BluetoothDeviceRoutesManager(
            Context context,
            Handler handler,
            BluetoothAdapter bluetoothAdapter,
            BluetoothProfileMonitor bluetoothProfileMonitor,
            BluetoothRouteController.BluetoothRoutesUpdatedListener
                    bluetoothRoutesUpdatedListener) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mHandler = handler;
        Objects.requireNonNull(bluetoothAdapter);
        this.mBluetoothAdapter = bluetoothAdapter;
        Objects.requireNonNull(bluetoothProfileMonitor);
        this.mBluetoothProfileMonitor = bluetoothProfileMonitor;
        Objects.requireNonNull(bluetoothRoutesUpdatedListener);
        this.mListener = bluetoothRoutesUpdatedListener;
    }

    public final BluetoothRouteInfo createBluetoothRoute(BluetoothDevice bluetoothDevice) {
        BluetoothRouteInfo bluetoothRouteInfo = new BluetoothRouteInfo();
        bluetoothRouteInfo.mBtDevice = bluetoothDevice;
        String deviceName = getDeviceName(bluetoothDevice);
        BluetoothProfileMonitor bluetoothProfileMonitor = this.mBluetoothProfileMonitor;
        int i =
                bluetoothProfileMonitor.isProfileSupported(bluetoothDevice, 22)
                        ? 26
                        : bluetoothProfileMonitor.isProfileSupported(bluetoothDevice, 21) ? 23 : 8;
        String routeIdForType = getRouteIdForType(bluetoothDevice, i);
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        if (bluetoothProfileMonitor.isProfileSupported(bluetoothDevice, 2)) {
            sparseBooleanArray.put(2, true);
        }
        if (bluetoothProfileMonitor.isProfileSupported(bluetoothDevice, 21)) {
            sparseBooleanArray.put(21, true);
        }
        if (bluetoothProfileMonitor.isProfileSupported(bluetoothDevice, 22)) {
            sparseBooleanArray.put(22, true);
        }
        bluetoothRouteInfo.mConnectedProfiles = sparseBooleanArray;
        bluetoothRouteInfo.mRoute =
                new MediaRoute2Info.Builder(routeIdForType, deviceName)
                        .addFeature("android.media.route.feature.LIVE_AUDIO")
                        .addFeature("android.media.route.feature.LOCAL_PLAYBACK")
                        .setConnectionState(0)
                        .setDescription(
                                this.mContext
                                        .getResources()
                                        .getText(R.string.config_defaultWellbeingPackage)
                                        .toString())
                        .setType(i)
                        .setAddress(bluetoothDevice.getAddress())
                        .build();
        return bluetoothRouteInfo;
    }

    public final String getDeviceName(BluetoothDevice bluetoothDevice) {
        String alias =
                Flags.enableUseOfBluetoothDeviceGetAliasForMr2infoGetName()
                        ? bluetoothDevice.getAlias()
                        : bluetoothDevice.getName();
        return TextUtils.isEmpty(alias)
                ? this.mContext.getResources().getText(R.string.unknownName).toString()
                : alias;
    }

    public final String getRouteIdForType(BluetoothDevice bluetoothDevice, int i) {
        BluetoothProfileMonitor bluetoothProfileMonitor = this.mBluetoothProfileMonitor;
        if (i == 23) {
            return "HEARING_AID_" + bluetoothProfileMonitor.getGroupId(bluetoothDevice, 21);
        }
        if (i != 26) {
            return bluetoothDevice.getAddress();
        }
        return "LE_AUDIO_" + bluetoothProfileMonitor.getGroupId(bluetoothDevice, 22);
    }

    public final void updateBluetoothRoutes() {
        Set<BluetoothDevice> bondedDevices = this.mBluetoothAdapter.getBondedDevices();
        synchronized (this) {
            try {
                ((HashMap) this.mBluetoothRoutes).clear();
                if (bondedDevices == null) {
                    Log.w("MR2SystemProvider", "BluetoothAdapter.getBondedDevices returned null.");
                    return;
                }
                this.mAddressToBondedDevice =
                        (Map)
                                bondedDevices.stream()
                                        .collect(
                                                Collectors.toMap(
                                                        new BluetoothDeviceRoutesManager$$ExternalSyntheticLambda0(),
                                                        Function.identity()));
                for (BluetoothDevice bluetoothDevice : bondedDevices) {
                    if (bluetoothDevice.isConnected()) {
                        BluetoothRouteInfo createBluetoothRoute =
                                createBluetoothRoute(bluetoothDevice);
                        if (createBluetoothRoute.mConnectedProfiles.size() > 0) {
                            ((HashMap) this.mBluetoothRoutes)
                                    .put(bluetoothDevice.getAddress(), createBluetoothRoute);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
