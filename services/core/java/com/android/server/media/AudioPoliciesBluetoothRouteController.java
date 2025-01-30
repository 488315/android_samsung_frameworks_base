package com.android.server.media;

import android.R;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* loaded from: classes2.dex */
public class AudioPoliciesBluetoothRouteController implements BluetoothRouteController {
  public final AdapterStateChangedReceiver mAdapterStateChangedReceiver;
  public final AudioManager mAudioManager;
  public final BluetoothAdapter mBluetoothAdapter;
  public final BluetoothProfileMonitor mBluetoothProfileMonitor;
  public final Map mBluetoothRoutes;
  public final Context mContext;
  public final DeviceStateChangedReceiver mDeviceStateChangedReceiver;
  public final BluetoothRouteController.BluetoothRoutesUpdatedListener mListener;
  public BluetoothRouteInfo mSelectedBluetoothRoute;
  public final SparseIntArray mVolumeMap;

  public AudioPoliciesBluetoothRouteController(
      Context context,
      BluetoothAdapter bluetoothAdapter,
      BluetoothRouteController.BluetoothRoutesUpdatedListener bluetoothRoutesUpdatedListener) {
    this(
        context,
        bluetoothAdapter,
        new BluetoothProfileMonitor(context, bluetoothAdapter),
        bluetoothRoutesUpdatedListener);
  }

  /* JADX WARN: Multi-variable type inference failed */
  public AudioPoliciesBluetoothRouteController(
      Context context,
      BluetoothAdapter bluetoothAdapter,
      BluetoothProfileMonitor bluetoothProfileMonitor,
      BluetoothRouteController.BluetoothRoutesUpdatedListener bluetoothRoutesUpdatedListener) {
    this.mAdapterStateChangedReceiver = new AdapterStateChangedReceiver();
    this.mDeviceStateChangedReceiver = new DeviceStateChangedReceiver();
    this.mBluetoothRoutes = new HashMap();
    this.mVolumeMap = new SparseIntArray();
    Objects.requireNonNull(context);
    Objects.requireNonNull(bluetoothAdapter);
    Objects.requireNonNull(bluetoothProfileMonitor);
    Objects.requireNonNull(bluetoothRoutesUpdatedListener);
    this.mContext = context;
    this.mBluetoothAdapter = bluetoothAdapter;
    this.mBluetoothProfileMonitor = bluetoothProfileMonitor;
    this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    this.mListener = bluetoothRoutesUpdatedListener;
    updateBluetoothRoutes();
  }

  @Override // com.android.server.media.BluetoothRouteController
  public void start(UserHandle userHandle) {
    this.mBluetoothProfileMonitor.start();
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction("android.bluetooth.adapter.action.STATE_CHANGED");
    this.mContext.registerReceiverAsUser(
        this.mAdapterStateChangedReceiver, userHandle, intentFilter, null, null);
    IntentFilter intentFilter2 = new IntentFilter();
    intentFilter2.addAction("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
    intentFilter2.addAction("android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED");
    intentFilter2.addAction("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED");
    intentFilter2.addAction("android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED");
    intentFilter2.addAction("android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED");
    intentFilter2.addAction("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED");
    this.mContext.registerReceiverAsUser(
        this.mDeviceStateChangedReceiver, userHandle, intentFilter2, null, null);
  }

  @Override // com.android.server.media.BluetoothRouteController
  public void stop() {
    this.mContext.unregisterReceiver(this.mAdapterStateChangedReceiver);
    this.mContext.unregisterReceiver(this.mDeviceStateChangedReceiver);
  }

  @Override // com.android.server.media.BluetoothRouteController
  public boolean selectRoute(String str) {
    synchronized (this) {
      updateBluetoothRoutes();
      if (str == null) {
        this.mSelectedBluetoothRoute = null;
        return true;
      }
      BluetoothRouteInfo bluetoothRouteInfo = (BluetoothRouteInfo) this.mBluetoothRoutes.get(str);
      if (bluetoothRouteInfo == null) {
        Slog.w("APBtRouteController", "Cannot find bluetooth route for " + str);
        return false;
      }
      this.mSelectedBluetoothRoute = bluetoothRouteInfo;
      setRouteConnectionState(bluetoothRouteInfo, 2);
      updateConnectivityStateForDevicesInTheSameGroup();
      return true;
    }
  }

  public final void updateConnectivityStateForDevicesInTheSameGroup() {
    synchronized (this) {
      for (BluetoothRouteInfo bluetoothRouteInfo : this.mBluetoothRoutes.values()) {
        if (TextUtils.equals(
                bluetoothRouteInfo.mRoute.getId(), this.mSelectedBluetoothRoute.mRoute.getId())
            && !TextUtils.equals(
                bluetoothRouteInfo.mBtDevice.getAddress(),
                this.mSelectedBluetoothRoute.mBtDevice.getAddress())) {
          setRouteConnectionState(bluetoothRouteInfo, 2);
        }
      }
    }
  }

  @Override // com.android.server.media.BluetoothRouteController
  public void transferTo(String str) {
    if (str == null) {
      Log.w(
          "APBtRouteController",
          "transeferTo blocked mBluetoothAdapter.removeActiveDevice() for BT connection");
      return;
    }
    BluetoothRouteInfo findBluetoothRouteWithRouteId = findBluetoothRouteWithRouteId(str);
    if (findBluetoothRouteWithRouteId == null) {
      Slog.w("APBtRouteController", "transferTo: Unknown route. ID=" + str);
      return;
    }
    this.mBluetoothAdapter.setActiveDevice(findBluetoothRouteWithRouteId.mBtDevice, 0);
  }

  public final BluetoothRouteInfo findBluetoothRouteWithRouteId(String str) {
    if (str == null) {
      return null;
    }
    synchronized (this) {
      for (BluetoothRouteInfo bluetoothRouteInfo : this.mBluetoothRoutes.values()) {
        if (TextUtils.equals(bluetoothRouteInfo.mRoute.getId(), str)) {
          return bluetoothRouteInfo;
        }
      }
      return null;
    }
  }

  public final void updateBluetoothRoutes() {
    Set<BluetoothDevice> bondedDevices = this.mBluetoothAdapter.getBondedDevices();
    if (bondedDevices == null) {
      return;
    }
    synchronized (this) {
      this.mBluetoothRoutes.clear();
      for (BluetoothDevice bluetoothDevice : bondedDevices) {
        if (isDeviceConnected(bluetoothDevice)) {
          BluetoothRouteInfo createBluetoothRoute = createBluetoothRoute(bluetoothDevice);
          if (createBluetoothRoute.mConnectedProfiles.size() > 0) {
            this.mBluetoothRoutes.put(bluetoothDevice.getAddress(), createBluetoothRoute);
          }
        }
      }
    }
  }

  public boolean isDeviceConnected(BluetoothDevice bluetoothDevice) {
    return bluetoothDevice.isConnected();
  }

  @Override // com.android.server.media.BluetoothRouteController
  public MediaRoute2Info getSelectedRoute() {
    synchronized (this) {
      BluetoothRouteInfo bluetoothRouteInfo = this.mSelectedBluetoothRoute;
      if (bluetoothRouteInfo == null) {
        return null;
      }
      return bluetoothRouteInfo.mRoute;
    }
  }

  @Override // com.android.server.media.BluetoothRouteController
  public List getTransferableRoutes() {
    List allBluetoothRoutes = getAllBluetoothRoutes();
    synchronized (this) {
      BluetoothRouteInfo bluetoothRouteInfo = this.mSelectedBluetoothRoute;
      if (bluetoothRouteInfo != null) {
        allBluetoothRoutes.remove(bluetoothRouteInfo.mRoute);
      }
    }
    return allBluetoothRoutes;
  }

  @Override // com.android.server.media.BluetoothRouteController
  public List getAllBluetoothRoutes() {
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    MediaRoute2Info selectedRoute = getSelectedRoute();
    if (selectedRoute != null) {
      arrayList.add(selectedRoute);
      arrayList2.add(selectedRoute.getId());
    }
    synchronized (this) {
      for (BluetoothRouteInfo bluetoothRouteInfo : this.mBluetoothRoutes.values()) {
        if (!arrayList2.contains(bluetoothRouteInfo.mRoute.getId())) {
          arrayList.add(bluetoothRouteInfo.mRoute);
          arrayList2.add(bluetoothRouteInfo.mRoute.getId());
        }
      }
    }
    return arrayList;
  }

  @Override // com.android.server.media.BluetoothRouteController
  public boolean updateVolumeForDevices(int i, int i2) {
    int i3;
    if ((134217728 & i) != 0) {
      i3 = 23;
    } else if ((i & 896) != 0) {
      i3 = 8;
    } else {
      if ((i & 536870912) == 0) {
        return false;
      }
      i3 = 26;
    }
    synchronized (this) {
      this.mVolumeMap.put(i3, i2);
      BluetoothRouteInfo bluetoothRouteInfo = this.mSelectedBluetoothRoute;
      if (bluetoothRouteInfo != null && bluetoothRouteInfo.mRoute.getType() == i3) {
        this.mSelectedBluetoothRoute.mRoute =
            new MediaRoute2Info.Builder(this.mSelectedBluetoothRoute.mRoute).setVolume(i2).build();
        notifyBluetoothRoutesUpdated();
        return true;
      }
      return false;
    }
  }

  public final void notifyBluetoothRoutesUpdated() {
    this.mListener.onBluetoothRoutesUpdated(getAllBluetoothRoutes());
  }

  public final BluetoothRouteInfo createBluetoothRoute(BluetoothDevice bluetoothDevice) {
    int i;
    BluetoothRouteInfo bluetoothRouteInfo = new BluetoothRouteInfo();
    bluetoothRouteInfo.mBtDevice = bluetoothDevice;
    String address = bluetoothDevice.getAddress();
    String name = bluetoothDevice.getName();
    if (TextUtils.isEmpty(name)) {
      name = this.mContext.getResources().getText(R.string.unknownName).toString();
    }
    bluetoothRouteInfo.mConnectedProfiles = new SparseBooleanArray();
    if (this.mBluetoothProfileMonitor.isProfileSupported(2, bluetoothDevice)) {
      bluetoothRouteInfo.mConnectedProfiles.put(2, true);
    }
    if (this.mBluetoothProfileMonitor.isProfileSupported(21, bluetoothDevice)) {
      bluetoothRouteInfo.mConnectedProfiles.put(21, true);
      address = "HEARING_AID_" + this.mBluetoothProfileMonitor.getGroupId(21, bluetoothDevice);
      i = 23;
    } else {
      i = 8;
    }
    if (this.mBluetoothProfileMonitor.isProfileSupported(22, bluetoothDevice)) {
      bluetoothRouteInfo.mConnectedProfiles.put(22, true);
      address = "LE_AUDIO_" + this.mBluetoothProfileMonitor.getGroupId(22, bluetoothDevice);
      i = 26;
    }
    bluetoothRouteInfo.mRoute =
        new MediaRoute2Info.Builder(address, name)
            .addFeature("android.media.route.feature.LIVE_AUDIO")
            .addFeature("android.media.route.feature.LOCAL_PLAYBACK")
            .setConnectionState(0)
            .setDescription(
                this.mContext
                    .getResources()
                    .getText(R.string.config_defaultProfcollectReportUploaderAction)
                    .toString())
            .setType(i)
            .setVolumeHandling(1)
            .setVolumeMax(this.mAudioManager.getStreamMaxVolume(3))
            .setAddress(bluetoothDevice.getAddress())
            .build();
    return bluetoothRouteInfo;
  }

  public final void setRouteConnectionState(BluetoothRouteInfo bluetoothRouteInfo, int i) {
    int i2;
    if (bluetoothRouteInfo == null) {
      Slog.w("APBtRouteController", "setRouteConnectionState: route shouldn't be null");
      return;
    }
    if (bluetoothRouteInfo.mRoute.getConnectionState() == i) {
      return;
    }
    MediaRoute2Info.Builder connectionState =
        new MediaRoute2Info.Builder(bluetoothRouteInfo.mRoute).setConnectionState(i);
    connectionState.setType(bluetoothRouteInfo.getRouteType());
    if (i == 2) {
      synchronized (this) {
        i2 = this.mVolumeMap.get(bluetoothRouteInfo.getRouteType(), 0);
      }
      connectionState.setVolume(i2);
    }
    bluetoothRouteInfo.mRoute = connectionState.build();
  }

  public class BluetoothRouteInfo {
    public BluetoothDevice mBtDevice;
    public SparseBooleanArray mConnectedProfiles;
    public MediaRoute2Info mRoute;

    public BluetoothRouteInfo() {}

    public int getRouteType() {
      if (this.mConnectedProfiles.get(21, false)) {
        return 23;
      }
      return this.mConnectedProfiles.get(22, false) ? 26 : 8;
    }
  }

  public class AdapterStateChangedReceiver extends BroadcastReceiver {
    public AdapterStateChangedReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      boolean z;
      int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", -1);
      if (intExtra == 10 || intExtra == 13) {
        synchronized (AudioPoliciesBluetoothRouteController.this) {
          AudioPoliciesBluetoothRouteController.this.mBluetoothRoutes.clear();
        }
        AudioPoliciesBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
      } else if (intExtra == 12) {
        AudioPoliciesBluetoothRouteController.this.updateBluetoothRoutes();
        synchronized (AudioPoliciesBluetoothRouteController.this) {
          z = !AudioPoliciesBluetoothRouteController.this.mBluetoothRoutes.isEmpty();
        }
        if (z) {
          AudioPoliciesBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
        }
      }
    }
  }

  public class DeviceStateChangedReceiver extends BroadcastReceiver {
    public DeviceStateChangedReceiver() {}

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      action.hashCode();
      switch (action) {
        case "android.bluetooth.action.LE_AUDIO_CONNECTION_STATE_CHANGED":
        case "android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED":
        case "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED":
        case "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED":
        case "android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED":
        case "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED":
          AudioPoliciesBluetoothRouteController.this.updateBluetoothRoutes();
          AudioPoliciesBluetoothRouteController.this.notifyBluetoothRoutesUpdated();
          break;
      }
    }
  }
}
