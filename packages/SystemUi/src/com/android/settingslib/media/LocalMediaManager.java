package com.android.settingslib.media;

import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.text.TextUtils;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LocalMediaManager implements BluetoothCallback {
    AudioManager mAudioManager;
    BluetoothAdapter mBluetoothAdapter;
    public final Collection mCallbacks;
    public final Context mContext;
    MediaDevice mCurrentConnectedDevice;
    DeviceAttributeChangeCallback mDeviceAttributeChangeCallback;
    List<MediaDevice> mDisconnectedMediaDevices;
    public final InfoMediaManager mInfoMediaManager;
    public final LocalBluetoothManager mLocalBluetoothManager;
    final MediaDeviceCallback mMediaDeviceCallback;
    List<MediaDevice> mMediaDevices;
    public final Object mMediaDevicesLock;
    public MediaDevice mOnTransferBluetoothDevice;
    public final String mPackageName;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class DeviceAttributeChangeCallback implements CachedBluetoothDevice.Callback {
        public DeviceAttributeChangeCallback() {
        }

        @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
        public final void onDeviceAttributesChanged() {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            MediaDevice mediaDevice = localMediaManager.mOnTransferBluetoothDevice;
            if (mediaDevice != null && !((BluetoothMediaDevice) mediaDevice).mCachedDevice.isBusy() && !localMediaManager.mOnTransferBluetoothDevice.isConnected()) {
                localMediaManager.mOnTransferBluetoothDevice.mState = 3;
                localMediaManager.mOnTransferBluetoothDevice = null;
                Iterator it = ((CopyOnWriteArrayList) localMediaManager.getCallbacks()).iterator();
                while (it.hasNext()) {
                    ((DeviceCallback) it.next()).onRequestFailed(0);
                }
            }
            Iterator it2 = ((CopyOnWriteArrayList) localMediaManager.getCallbacks()).iterator();
            while (it2.hasNext()) {
                ((DeviceCallback) it2.next()).onDeviceAttributesChanged();
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MediaDeviceCallback {
        public MediaDeviceCallback() {
        }

        /* JADX WARN: Code restructure failed: missing block: B:25:0x0064, code lost:
        
            if (r4 != false) goto L30;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final BluetoothMediaDevice getMutingExpectedDevice() {
            boolean z;
            boolean z2;
            LocalBluetoothProfile localBluetoothProfile;
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            if (localMediaManager.mBluetoothAdapter == null || localMediaManager.mAudioManager.getMutingExpectedDevice() == null) {
                Log.w("LocalMediaManager", "BluetoothAdapter is null or muting expected device not exist");
                return null;
            }
            List mostRecentlyConnectedDevices = localMediaManager.mBluetoothAdapter.getMostRecentlyConnectedDevices();
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = localMediaManager.mLocalBluetoothManager.mCachedDeviceManager;
            Iterator it = mostRecentlyConnectedDevices.iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice findDevice = cachedBluetoothDeviceManager.findDevice((BluetoothDevice) it.next());
                boolean z3 = false;
                if (findDevice != null && findDevice.mBondState == 12 && !findDevice.isConnected()) {
                    Iterator it2 = ((ArrayList) findDevice.getConnectableProfiles()).iterator();
                    do {
                        z = true;
                        if (!it2.hasNext()) {
                            z2 = false;
                            break;
                        }
                        localBluetoothProfile = (LocalBluetoothProfile) it2.next();
                        if ((localBluetoothProfile instanceof A2dpProfile) || (localBluetoothProfile instanceof HearingAidProfile)) {
                            break;
                        }
                    } while (!(localBluetoothProfile instanceof LeAudioProfile));
                    z2 = true;
                }
                z = false;
                if (z) {
                    AudioDeviceAttributes mutingExpectedDevice = localMediaManager.mAudioManager.getMutingExpectedDevice();
                    if (mutingExpectedDevice != null && findDevice != null) {
                        z3 = findDevice.getAddress().equals(mutingExpectedDevice.getAddress());
                    }
                    if (z3) {
                        return new BluetoothMediaDevice(localMediaManager.mContext, findDevice, null, null, localMediaManager.mPackageName);
                    }
                }
            }
            return null;
        }
    }

    public LocalMediaManager(Context context, String str, Notification notification2) {
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mMediaDevicesLock = new Object();
        this.mMediaDeviceCallback = new MediaDeviceCallback();
        this.mMediaDevices = new CopyOnWriteArrayList();
        this.mDisconnectedMediaDevices = new CopyOnWriteArrayList();
        this.mDeviceAttributeChangeCallback = new DeviceAttributeChangeCallback();
        this.mContext = context;
        this.mPackageName = str;
        LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (localBluetoothManager == null) {
            Log.e("LocalMediaManager", "Bluetooth is not supported on this device");
        } else {
            this.mInfoMediaManager = new InfoMediaManager(context, str, notification2, localBluetoothManager);
        }
    }

    public final boolean addDeviceToPlayMedia(MediaDevice mediaDevice) {
        mediaDevice.mState = 5;
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        if (TextUtils.isEmpty(infoMediaManager.mPackageName)) {
            Log.w("InfoMediaManager", "addDeviceToPlayMedia() package name is null or empty!");
            return false;
        }
        RoutingSessionInfo routingSessionInfo = infoMediaManager.getRoutingSessionInfo();
        if (routingSessionInfo != null && routingSessionInfo.getSelectableRoutes().contains(mediaDevice.mRouteInfo.getId())) {
            infoMediaManager.mRouterManager.selectRoute(routingSessionInfo, mediaDevice.mRouteInfo);
            return true;
        }
        Log.w("InfoMediaManager", "addDeviceToPlayMedia() Ignoring selecting a non-selectable device : " + mediaDevice.getName());
        return false;
    }

    public final void connectDevice(MediaDevice mediaDevice) {
        MediaDevice mediaDeviceById = getMediaDeviceById(mediaDevice.getId());
        if (mediaDeviceById == null) {
            Log.w("LocalMediaManager", "connectDevice() connectDevice not in the list!");
            return;
        }
        if (mediaDeviceById instanceof BluetoothMediaDevice) {
            CachedBluetoothDevice cachedBluetoothDevice = ((BluetoothMediaDevice) mediaDeviceById).mCachedDevice;
            if (!cachedBluetoothDevice.isConnected() && !cachedBluetoothDevice.isBusy()) {
                this.mOnTransferBluetoothDevice = mediaDevice;
                mediaDeviceById.mState = 1;
                cachedBluetoothDevice.connect$1();
                return;
            } else if (cachedBluetoothDevice.isConnected()) {
                mediaDeviceById.mState = 0;
                mediaDeviceById.mAudioManager.setDeviceToForceByUser(mediaDeviceById.getDevice(), mediaDeviceById.getAddress(), false);
                this.mCurrentConnectedDevice = mediaDeviceById;
                dispatchSelectedDeviceStateChanged(mediaDeviceById);
                return;
            }
        }
        if (mediaDeviceById.equals(this.mCurrentConnectedDevice)) {
            Log.d("LocalMediaManager", "connectDevice() this device is already connected! : " + mediaDeviceById.getName());
            return;
        }
        mediaDeviceById.mState = 1;
        if (mediaDeviceById instanceof PhoneMediaDevice) {
            mediaDeviceById.mAudioManager.setDeviceToForceByUser(mediaDeviceById.getDevice(), mediaDeviceById.getAddress(), false);
            mediaDeviceById.mState = 0;
            this.mCurrentConnectedDevice = mediaDeviceById;
            dispatchSelectedDeviceStateChanged(mediaDeviceById);
            return;
        }
        if (TextUtils.isEmpty(this.mPackageName)) {
            InfoMediaManager infoMediaManager = this.mInfoMediaManager;
            RoutingSessionInfo systemRoutingSession = infoMediaManager.mRouterManager.getSystemRoutingSession((String) null);
            if (systemRoutingSession != null) {
                infoMediaManager.mRouterManager.transfer(systemRoutingSession, mediaDeviceById.mRouteInfo);
                return;
            }
            return;
        }
        if (mediaDeviceById.mRouteInfo == null) {
            Log.w("MediaDevice", "Unable to connect. RouteInfo is empty");
            return;
        }
        mediaDeviceById.mConnectedRecord++;
        ConnectionRecordManager connectionRecordManager = ConnectionRecordManager.getInstance();
        Context context = mediaDeviceById.mContext;
        String id = mediaDeviceById.getId();
        int i = mediaDeviceById.mConnectedRecord;
        synchronized (connectionRecordManager) {
            SharedPreferences.Editor edit = context.getSharedPreferences("seamless_transfer_record", 0).edit();
            connectionRecordManager.mLastSelectedDevice = id;
            edit.putInt(id, i);
            edit.putString("last_selected_device", connectionRecordManager.mLastSelectedDevice);
            edit.apply();
        }
        mediaDeviceById.mRouterManager.transfer(mediaDeviceById.mPackageName, mediaDeviceById.mRouteInfo);
    }

    public final void dispatchSelectedDeviceStateChanged(MediaDevice mediaDevice) {
        Iterator it = ((CopyOnWriteArrayList) getCallbacks()).iterator();
        while (it.hasNext()) {
            ((DeviceCallback) it.next()).onSelectedDeviceStateChanged(mediaDevice);
        }
    }

    public final Collection getCallbacks() {
        return new CopyOnWriteArrayList(this.mCallbacks);
    }

    public final MediaDevice getCurrentConnectedDevice() {
        return this.mCurrentConnectedDevice;
    }

    public final List getDeselectableMediaDevice() {
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        infoMediaManager.getClass();
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(infoMediaManager.mPackageName)) {
            Log.d("InfoMediaManager", "getDeselectableMediaDevice() package name is null or empty!");
        } else {
            RoutingSessionInfo routingSessionInfo = infoMediaManager.getRoutingSessionInfo();
            if (routingSessionInfo != null) {
                for (MediaRoute2Info mediaRoute2Info : infoMediaManager.mRouterManager.getDeselectableRoutes(routingSessionInfo)) {
                    arrayList.add(new InfoMediaDevice(infoMediaManager.mContext, infoMediaManager.mRouterManager, mediaRoute2Info, infoMediaManager.mPackageName, (RouteListingPreference.Item) ((ConcurrentHashMap) infoMediaManager.mPreferenceItemMap).get(mediaRoute2Info.getId())));
                    StringBuilder sb = new StringBuilder();
                    sb.append((Object) mediaRoute2Info.getName());
                    sb.append(" is deselectable for ");
                    ExifInterface$$ExternalSyntheticOutline0.m35m(sb, infoMediaManager.mPackageName, "InfoMediaManager");
                }
            } else {
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("getDeselectableMediaDevice() cannot found deselectable MediaDevice from : "), infoMediaManager.mPackageName, "InfoMediaManager");
            }
        }
        return arrayList;
    }

    public final ComponentName getLinkedItemComponentName() {
        RouteListingPreference routeListingPreference;
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        MediaRouter2Manager mediaRouter2Manager = infoMediaManager.mRouterManager;
        String str = infoMediaManager.mPackageName;
        if (TextUtils.isEmpty(str) || (routeListingPreference = mediaRouter2Manager.getRouteListingPreference(str)) == null) {
            return null;
        }
        return routeListingPreference.getLinkedItemComponentName();
    }

    public final MediaDevice getMediaDeviceById(String str) {
        synchronized (this.mMediaDevicesLock) {
            for (MediaDevice mediaDevice : this.mMediaDevices) {
                if (TextUtils.equals(mediaDevice.getId(), str)) {
                    return mediaDevice;
                }
            }
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m70m("getMediaDeviceById() failed to find device with id: ", str, "LocalMediaManager");
            return null;
        }
    }

    public final List getSelectableMediaDevice() {
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        infoMediaManager.getClass();
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(infoMediaManager.mPackageName)) {
            Log.w("InfoMediaManager", "getSelectableMediaDevice() package name is null or empty!");
        } else {
            RoutingSessionInfo routingSessionInfo = infoMediaManager.getRoutingSessionInfo();
            if (routingSessionInfo != null) {
                for (MediaRoute2Info mediaRoute2Info : infoMediaManager.mRouterManager.getSelectableRoutes(routingSessionInfo)) {
                    arrayList.add(new InfoMediaDevice(infoMediaManager.mContext, infoMediaManager.mRouterManager, mediaRoute2Info, infoMediaManager.mPackageName, (RouteListingPreference.Item) ((ConcurrentHashMap) infoMediaManager.mPreferenceItemMap).get(mediaRoute2Info.getId())));
                }
            } else {
                Log.w("InfoMediaManager", "getSelectableMediaDevice() cannot found selectable MediaDevice from : " + infoMediaManager.mPackageName);
            }
        }
        return arrayList;
    }

    public final List getSelectedMediaDevice() {
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        infoMediaManager.getClass();
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(infoMediaManager.mPackageName)) {
            Log.w("InfoMediaManager", "getSelectedMediaDevice() package name is null or empty!");
        } else {
            RoutingSessionInfo routingSessionInfo = infoMediaManager.getRoutingSessionInfo();
            if (routingSessionInfo != null) {
                for (MediaRoute2Info mediaRoute2Info : infoMediaManager.mRouterManager.getSelectedRoutes(routingSessionInfo)) {
                    arrayList.add(new InfoMediaDevice(infoMediaManager.mContext, infoMediaManager.mRouterManager, mediaRoute2Info, infoMediaManager.mPackageName, (RouteListingPreference.Item) ((ConcurrentHashMap) infoMediaManager.mPreferenceItemMap).get(mediaRoute2Info.getId())));
                }
            } else {
                Log.w("InfoMediaManager", "getSelectedMediaDevice() cannot found selectable MediaDevice from : " + infoMediaManager.mPackageName);
            }
        }
        return arrayList;
    }

    public final boolean isActiveDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        LeAudioProfile leAudioProfile;
        HearingAidProfile hearingAidProfile;
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        A2dpProfile a2dpProfile = localBluetoothManager.mProfileManager.mA2dpProfile;
        boolean equals = a2dpProfile != null ? cachedBluetoothDevice.mDevice.equals(a2dpProfile.getActiveDevice()) : false;
        LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
        if (equals || (hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile) == null) {
            z = false;
        } else {
            BluetoothAdapter bluetoothAdapter = hearingAidProfile.mBluetoothAdapter;
            z = (bluetoothAdapter == null ? new ArrayList() : bluetoothAdapter.getActiveDevices(21)).contains(cachedBluetoothDevice.mDevice);
        }
        return equals || z || ((equals || z || (leAudioProfile = localBluetoothProfileManager.mLeAudioProfile) == null) ? false : leAudioProfile.getActiveDevices().contains(cachedBluetoothDevice.mDevice));
    }

    public final boolean isPreferenceRouteListingExist() {
        RouteListingPreference routeListingPreference;
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        MediaRouter2Manager mediaRouter2Manager = infoMediaManager.mRouterManager;
        String str = infoMediaManager.mPackageName;
        return (TextUtils.isEmpty(str) || (routeListingPreference = mediaRouter2Manager.getRouteListingPreference(str)) == null || routeListingPreference.getUseSystemOrdering()) ? false : true;
    }

    public final void releaseSession() {
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        if (TextUtils.isEmpty(infoMediaManager.mPackageName)) {
            Log.w("InfoMediaManager", "releaseSession() package name is null or empty!");
            return;
        }
        RoutingSessionInfo routingSessionInfo = infoMediaManager.getRoutingSessionInfo();
        if (routingSessionInfo != null) {
            infoMediaManager.mRouterManager.releaseSession(routingSessionInfo);
            return;
        }
        Log.w("InfoMediaManager", "releaseSession() Ignoring release session : " + infoMediaManager.mPackageName);
    }

    public final boolean removeDeviceFromPlayMedia(MediaDevice mediaDevice) {
        mediaDevice.mState = 5;
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        if (TextUtils.isEmpty(infoMediaManager.mPackageName)) {
            Log.w("InfoMediaManager", "removeDeviceFromMedia() package name is null or empty!");
            return false;
        }
        RoutingSessionInfo routingSessionInfo = infoMediaManager.getRoutingSessionInfo();
        if (routingSessionInfo != null && routingSessionInfo.getSelectedRoutes().contains(mediaDevice.mRouteInfo.getId())) {
            infoMediaManager.mRouterManager.deselectRoute(routingSessionInfo, mediaDevice.mRouteInfo);
            return true;
        }
        Log.w("InfoMediaManager", "removeDeviceFromMedia() Ignoring deselecting a non-deselectable device : " + mediaDevice.getName());
        return false;
    }

    public final void startScan() {
        synchronized (this.mMediaDevicesLock) {
            this.mMediaDevices.clear();
        }
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) infoMediaManager.mCallbacks;
        if (!copyOnWriteArrayList.contains(mediaDeviceCallback)) {
            copyOnWriteArrayList.add(mediaDeviceCallback);
        }
        InfoMediaManager infoMediaManager2 = this.mInfoMediaManager;
        if (infoMediaManager2.mIsScanning) {
            return;
        }
        ((CopyOnWriteArrayList) infoMediaManager2.mMediaDevices).clear();
        infoMediaManager2.mRouterManager.registerCallback(infoMediaManager2.mExecutor, infoMediaManager2.mMediaRouterCallback);
        infoMediaManager2.mRouterManager.registerScanRequest();
        infoMediaManager2.mIsScanning = true;
        if (!TextUtils.isEmpty(infoMediaManager2.mPackageName)) {
            RouteListingPreference routeListingPreference = infoMediaManager2.mRouterManager.getRouteListingPreference(infoMediaManager2.mPackageName);
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) infoMediaManager2.mPreferenceItemMap;
            concurrentHashMap.clear();
            if (routeListingPreference != null) {
                routeListingPreference.getItems().forEach(new InfoMediaManager$Api34Impl$$ExternalSyntheticLambda0(concurrentHashMap));
            }
        }
        infoMediaManager2.refreshDevices();
    }

    public final void stopScan() {
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
        CopyOnWriteArrayList copyOnWriteArrayList = (CopyOnWriteArrayList) infoMediaManager.mCallbacks;
        if (copyOnWriteArrayList.contains(mediaDeviceCallback)) {
            copyOnWriteArrayList.remove(mediaDeviceCallback);
        }
        InfoMediaManager infoMediaManager2 = this.mInfoMediaManager;
        if (infoMediaManager2.mIsScanning) {
            infoMediaManager2.mRouterManager.unregisterCallback(infoMediaManager2.mMediaRouterCallback);
            infoMediaManager2.mRouterManager.unregisterScanRequest();
            infoMediaManager2.mIsScanning = false;
        }
        Iterator<MediaDevice> it = this.mDisconnectedMediaDevices.iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice = ((BluetoothMediaDevice) it.next()).mCachedDevice;
            DeviceAttributeChangeCallback deviceAttributeChangeCallback = this.mDeviceAttributeChangeCallback;
            synchronized (cachedBluetoothDevice.mCallbacks) {
                if (cachedBluetoothDevice.mCallbacks.contains(deviceAttributeChangeCallback)) {
                    cachedBluetoothDevice.mCallbacks.remove(deviceAttributeChangeCallback);
                }
                cachedBluetoothDevice.mCallbacks.remove(deviceAttributeChangeCallback);
            }
        }
    }

    public MediaDevice updateCurrentConnectedDevice() {
        LeAudioProfile leAudioProfile;
        List<BluetoothDevice> activeDevices;
        BluetoothDevice activeDevice;
        MediaRoute2Info mediaRoute2Info;
        synchronized (this.mMediaDevicesLock) {
            int semGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
            Log.i("LocalMediaManager", "updateCurrentConnectedDevice curDeviceType = " + semGetCurrentDeviceType);
            Iterator<MediaDevice> it = this.mMediaDevices.iterator();
            String str = null;
            MediaDevice mediaDevice = null;
            while (true) {
                if (!it.hasNext()) {
                    if (mediaDevice == null) {
                        if (semGetCurrentDeviceType == 8) {
                            A2dpProfile a2dpProfile = this.mLocalBluetoothManager.mProfileManager.mA2dpProfile;
                            if (a2dpProfile != null && (activeDevice = a2dpProfile.getActiveDevice()) != null) {
                                str = activeDevice.getAddress();
                            }
                            if (str != null) {
                                for (MediaDevice mediaDevice2 : this.mMediaDevices) {
                                    if ((mediaDevice2 instanceof BluetoothMediaDevice) && mediaDevice2.isConnected() && mediaDevice2.getAddress().equals(str)) {
                                        Log.i("LocalMediaManager", "updateCurrentConnectedDevice a2dp active devicename = " + mediaDevice2.getName() + "device type = " + mediaDevice2.mType);
                                        return mediaDevice2;
                                    }
                                }
                            }
                        } else if ((semGetCurrentDeviceType == 26 || semGetCurrentDeviceType == 27 || semGetCurrentDeviceType == 30) && (leAudioProfile = this.mLocalBluetoothManager.mProfileManager.mLeAudioProfile) != null && (activeDevices = leAudioProfile.getActiveDevices()) != null) {
                            Log.i("LocalMediaManager", "bleDeviceList size = " + activeDevices.size());
                            for (BluetoothDevice bluetoothDevice : activeDevices) {
                                if (bluetoothDevice != null) {
                                    String address = bluetoothDevice.getAddress();
                                    Log.i("LocalMediaManager", "activeBleAddress = " + address + " bleDeviceName = " + bluetoothDevice.getName());
                                    if (address != null) {
                                        for (MediaDevice mediaDevice3 : this.mMediaDevices) {
                                            if ((mediaDevice3 instanceof BluetoothMediaDevice) && mediaDevice3.isConnected() && mediaDevice3.getAddress().equals(address)) {
                                                Log.i("LocalMediaManager", "updateCurrentConnectedDevice ble active devicename = " + mediaDevice3.getName() + "device type = " + mediaDevice3.mType);
                                                return mediaDevice3;
                                            }
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                    return mediaDevice;
                }
                MediaDevice next = it.next();
                Log.i("LocalMediaManager", "updateCurrentConnectedDevice device type = " + next.mType + " name = " + next.getName());
                if (next instanceof BluetoothMediaDevice) {
                    if (semGetCurrentDeviceType != 8 && semGetCurrentDeviceType != 23) {
                        if (semGetCurrentDeviceType == 26 || semGetCurrentDeviceType == 27 || semGetCurrentDeviceType == 30) {
                        }
                    }
                    if (isActiveDevice(((BluetoothMediaDevice) next).mCachedDevice) && next.isConnected() && (mediaRoute2Info = next.mRouteInfo) != null && mediaRoute2Info.getType() == semGetCurrentDeviceType) {
                        Log.i("LocalMediaManager", "updateCurrentConnectedDevice device name = " + next.getName() + " device type = " + next.mType);
                        return next;
                    }
                }
                if ((next instanceof PhoneMediaDevice) && AudioDeviceInfo.convertDeviceTypeToInternalDevice(semGetCurrentDeviceType) == next.getDevice()) {
                    Log.i("LocalMediaManager", "updateCurrentConnectedDevice device name = " + next.getName() + " device type = " + next.mType);
                    mediaDevice = next;
                }
            }
        }
    }

    public LocalMediaManager(Context context, LocalBluetoothManager localBluetoothManager, InfoMediaManager infoMediaManager, String str) {
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mMediaDevicesLock = new Object();
        this.mMediaDeviceCallback = new MediaDeviceCallback();
        this.mMediaDevices = new CopyOnWriteArrayList();
        this.mDisconnectedMediaDevices = new CopyOnWriteArrayList();
        this.mDeviceAttributeChangeCallback = new DeviceAttributeChangeCallback();
        this.mContext = context;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mInfoMediaManager = infoMediaManager;
        this.mPackageName = str;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface DeviceCallback {
        void onDeviceListUpdate(List list);

        void onSelectedDeviceStateChanged(MediaDevice mediaDevice);

        default void onRequestFailed(int i) {
        }

        default void onAboutToConnectDeviceRemoved() {
        }

        default void onDeviceAttributesChanged() {
        }

        default void onAboutToConnectDeviceAdded(String str, Drawable drawable, String str2) {
        }
    }
}
