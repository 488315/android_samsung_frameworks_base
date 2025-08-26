package com.android.settingslib.media;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceAttributes;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.media.session.MediaController;
import android.text.TextUtils;
import android.util.Log;
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
import com.android.settingslib.media.InfoMediaManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class LocalMediaManager implements BluetoothCallback {
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
    public final Receiver mReceiver;

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

    public class MediaDeviceCallback {
        public MediaDeviceCallback() {
        }

        public static boolean isMediaDevice(CachedBluetoothDevice cachedBluetoothDevice) {
            ArrayList arrayList = (ArrayList) cachedBluetoothDevice.getUiAccessibleProfiles();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) obj;
                if ((localBluetoothProfile instanceof A2dpProfile) || (localBluetoothProfile instanceof HearingAidProfile) || (localBluetoothProfile instanceof LeAudioProfile)) {
                    return true;
                }
            }
            return false;
        }

        public final List buildDisconnectedBluetoothDevice() {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            BluetoothAdapter bluetoothAdapter = localMediaManager.mBluetoothAdapter;
            if (bluetoothAdapter == null) {
                Log.w("LocalMediaManager", "buildDisconnectedBluetoothDevice() BluetoothAdapter is null");
                return new ArrayList();
            }
            List mostRecentlyConnectedDevices = bluetoothAdapter.getMostRecentlyConnectedDevices();
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = localMediaManager.mLocalBluetoothManager.mCachedDeviceManager;
            ArrayList arrayList = new ArrayList();
            Iterator it = mostRecentlyConnectedDevices.iterator();
            int i = 0;
            int i2 = 0;
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDeviceFindDevice = cachedBluetoothDeviceManager.findDevice((BluetoothDevice) it.next());
                if (cachedBluetoothDeviceFindDevice != null && cachedBluetoothDeviceFindDevice.mBondState == 12 && !cachedBluetoothDeviceFindDevice.isConnected() && isMediaDevice(cachedBluetoothDeviceFindDevice)) {
                    i2++;
                    arrayList.add(cachedBluetoothDeviceFindDevice);
                    if (i2 >= 5) {
                        break;
                    }
                }
            }
            Iterator<MediaDevice> it2 = localMediaManager.mDisconnectedMediaDevices.iterator();
            while (it2.hasNext()) {
                ((BluetoothMediaDevice) it2.next()).mCachedDevice.unregisterCallback(localMediaManager.mDeviceAttributeChangeCallback);
            }
            localMediaManager.mDisconnectedMediaDevices.clear();
            int size = arrayList.size();
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                BluetoothMediaDevice bluetoothMediaDevice = new BluetoothMediaDevice(localMediaManager.mContext, cachedBluetoothDevice, null, null);
                if (!localMediaManager.mMediaDevices.contains(bluetoothMediaDevice)) {
                    cachedBluetoothDevice.registerCallback(localMediaManager.mDeviceAttributeChangeCallback);
                    localMediaManager.mDisconnectedMediaDevices.add(bluetoothMediaDevice);
                }
            }
            return new ArrayList(localMediaManager.mDisconnectedMediaDevices);
        }

        public final BluetoothMediaDevice getMutingExpectedDevice() {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            if (localMediaManager.mBluetoothAdapter != null && localMediaManager.mAudioManager.getMutingExpectedDevice() != null) {
                List mostRecentlyConnectedDevices = localMediaManager.mBluetoothAdapter.getMostRecentlyConnectedDevices();
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager = localMediaManager.mLocalBluetoothManager.mCachedDeviceManager;
                Iterator it = mostRecentlyConnectedDevices.iterator();
                while (it.hasNext()) {
                    CachedBluetoothDevice cachedBluetoothDeviceFindDevice = cachedBluetoothDeviceManager.findDevice((BluetoothDevice) it.next());
                    if (cachedBluetoothDeviceFindDevice != null && cachedBluetoothDeviceFindDevice.mBondState == 12 && !cachedBluetoothDeviceFindDevice.isConnected() && isMediaDevice(cachedBluetoothDeviceFindDevice)) {
                        AudioDeviceAttributes mutingExpectedDevice = localMediaManager.mAudioManager.getMutingExpectedDevice();
                        if (mutingExpectedDevice != null ? cachedBluetoothDeviceFindDevice.mDevice.getAddress().equals(mutingExpectedDevice.getAddress()) : false) {
                            return new BluetoothMediaDevice(localMediaManager.mContext, cachedBluetoothDeviceFindDevice, null, null);
                        }
                    }
                }
            }
            return null;
        }
    }

    public final class Receiver extends BroadcastReceiver {
        public /* synthetic */ Receiver(LocalMediaManager localMediaManager, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            MediaDevice mediaDeviceUpdateCurrentConnectedDevice;
            String action = intent.getAction();
            if ((!"android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED".equals(action) && !"android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED".equals(action)) || (mediaDeviceUpdateCurrentConnectedDevice = LocalMediaManager.this.updateCurrentConnectedDevice()) == null || mediaDeviceUpdateCurrentConnectedDevice.equals(LocalMediaManager.this.mCurrentConnectedDevice)) {
                return;
            }
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            localMediaManager.mCurrentConnectedDevice = mediaDeviceUpdateCurrentConnectedDevice;
            localMediaManager.dispatchSelectedDeviceStateChanged(mediaDeviceUpdateCurrentConnectedDevice);
        }

        private Receiver() {
        }
    }

    public LocalMediaManager(Context context, String str) {
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mMediaDevicesLock = new Object();
        this.mMediaDeviceCallback = new MediaDeviceCallback();
        this.mMediaDevices = new CopyOnWriteArrayList();
        this.mDisconnectedMediaDevices = new CopyOnWriteArrayList();
        this.mDeviceAttributeChangeCallback = new DeviceAttributeChangeCallback();
        this.mReceiver = new Receiver(this, 0);
        this.mContext = context;
        LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (localBluetoothManager == null) {
            Log.e("LocalMediaManager", "Bluetooth is not supported on this device");
        } else {
            this.mInfoMediaManager = InfoMediaManager.createInstance(context, str, null, localBluetoothManager, null);
        }
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
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        infoMediaManager.getClass();
        if (mediaDeviceById.mRouteInfo == null) {
            Log.w("InfoMediaManager", "Unable to connect. RouteInfo is empty");
            return;
        }
        mediaDeviceById.mConnectedRecord++;
        ConnectionRecordManager connectionRecordManager = ConnectionRecordManager.getInstance();
        Context context = mediaDeviceById.mContext;
        String id = mediaDeviceById.getId();
        int i = mediaDeviceById.mConnectedRecord;
        synchronized (connectionRecordManager) {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("seamless_transfer_record", 0).edit();
            connectionRecordManager.mLastSelectedDevice = id;
            editorEdit.putInt(id, i);
            editorEdit.putString("last_selected_device", connectionRecordManager.mLastSelectedDevice);
            editorEdit.apply();
        }
        infoMediaManager.transferToRoute(mediaDeviceById.mRouteInfo);
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

    public final MediaDevice getMediaDeviceById(String str) {
        synchronized (this.mMediaDevicesLock) {
            try {
                for (MediaDevice mediaDevice : this.mMediaDevices) {
                    if (TextUtils.equals(mediaDevice.getId(), str)) {
                        return mediaDevice;
                    }
                }
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("getMediaDeviceById() failed to find device with id: ", str, "LocalMediaManager");
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final List getSelectedMediaDevice() {
        InfoMediaManager infoMediaManager = this.mInfoMediaManager;
        RoutingSessionInfo activeRoutingSession = infoMediaManager.getActiveRoutingSession();
        ArrayList arrayList = new ArrayList();
        for (MediaRoute2Info mediaRoute2Info : infoMediaManager.getSelectedRoutes(activeRoutingSession)) {
            arrayList.add(new InfoMediaDevice(infoMediaManager.mContext, mediaRoute2Info, (RouteListingPreference.Item) ((ConcurrentHashMap) infoMediaManager.mPreferenceItemMap).get(mediaRoute2Info.getId())));
        }
        return arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0072  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isActiveDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        boolean z2;
        int iSemGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        A2dpProfile a2dpProfile = localBluetoothManager.mProfileManager.mA2dpProfile;
        boolean z3 = a2dpProfile != null && iSemGetCurrentDeviceType == 8 && cachedBluetoothDevice.mDevice.equals(a2dpProfile.getActiveDevice());
        LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
        HearingAidProfile hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile;
        if (hearingAidProfile == null || iSemGetCurrentDeviceType != 23) {
            z = false;
        } else {
            BluetoothAdapter bluetoothAdapter = hearingAidProfile.mBluetoothAdapter;
            if ((bluetoothAdapter == null ? new ArrayList() : bluetoothAdapter.getActiveDevices(21)).contains(cachedBluetoothDevice.mDevice)) {
                z = true;
            }
        }
        LeAudioProfile leAudioProfile = localBluetoothProfileManager.mLeAudioProfile;
        if (leAudioProfile == null || !(iSemGetCurrentDeviceType == 26 || iSemGetCurrentDeviceType == 27 || iSemGetCurrentDeviceType == 30)) {
            z2 = false;
        } else {
            BluetoothAdapter bluetoothAdapter2 = leAudioProfile.mBluetoothAdapter;
            if ((bluetoothAdapter2 == null ? new ArrayList() : bluetoothAdapter2.getActiveDevices(22)).contains(cachedBluetoothDevice.mDevice)) {
                z2 = true;
            }
        }
        return z3 || z || z2;
    }

    public final void registerCallback(DeviceCallback deviceCallback) {
        boolean zIsEmpty = ((CopyOnWriteArrayList) this.mCallbacks).isEmpty();
        if (((CopyOnWriteArrayList) this.mCallbacks).contains(deviceCallback)) {
            return;
        }
        ((CopyOnWriteArrayList) this.mCallbacks).add(deviceCallback);
        if (zIsEmpty) {
            MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
            InfoMediaManager infoMediaManager = this.mInfoMediaManager;
            boolean zIsEmpty2 = ((CopyOnWriteArrayList) infoMediaManager.mCallbacks).isEmpty();
            if (!((CopyOnWriteArrayList) infoMediaManager.mCallbacks).contains(mediaDeviceCallback)) {
                ((CopyOnWriteArrayList) infoMediaManager.mCallbacks).add(mediaDeviceCallback);
                if (zIsEmpty2) {
                    ((CopyOnWriteArrayList) infoMediaManager.mMediaDevices).clear();
                    infoMediaManager.registerRouter();
                    MediaController mediaController = infoMediaManager.mMediaController;
                    if (mediaController != null) {
                        mediaController.registerCallback(infoMediaManager.mMediaControllerCallback);
                    }
                    InfoMediaManager.Api34Impl.onRouteListingPreferenceUpdated(infoMediaManager.getRouteListingPreference(), infoMediaManager.mPreferenceItemMap);
                    infoMediaManager.refreshDevices();
                }
            }
        }
        Receiver receiver = this.mReceiver;
        receiver.getClass();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
        intentFilter.addAction("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED");
        LocalMediaManager.this.mContext.registerReceiver(receiver, intentFilter);
    }

    public final void unregisterCallback(DeviceCallback deviceCallback) {
        if (((CopyOnWriteArrayList) this.mCallbacks).remove(deviceCallback) && ((CopyOnWriteArrayList) this.mCallbacks).isEmpty()) {
            MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
            InfoMediaManager infoMediaManager = this.mInfoMediaManager;
            if (((CopyOnWriteArrayList) infoMediaManager.mCallbacks).remove(mediaDeviceCallback) && ((CopyOnWriteArrayList) infoMediaManager.mCallbacks).isEmpty()) {
                MediaController mediaController = infoMediaManager.mMediaController;
                if (mediaController != null) {
                    mediaController.unregisterCallback(infoMediaManager.mMediaControllerCallback);
                }
                infoMediaManager.unregisterRouter();
            }
            Iterator<MediaDevice> it = this.mDisconnectedMediaDevices.iterator();
            while (it.hasNext()) {
                ((BluetoothMediaDevice) it.next()).mCachedDevice.unregisterCallback(this.mDeviceAttributeChangeCallback);
            }
            Receiver receiver = this.mReceiver;
            receiver.getClass();
            try {
                LocalMediaManager.this.mContext.unregisterReceiver(receiver);
            } catch (Exception unused) {
            }
        }
    }

    public MediaDevice updateCurrentConnectedDevice() {
        MediaRoute2Info mediaRoute2Info;
        synchronized (this.mMediaDevicesLock) {
            try {
                int iSemGetCurrentDeviceType = this.mAudioManager.semGetCurrentDeviceType();
                Log.i("LocalMediaManager", "updateCurrentConnectedDevice curDeviceType = " + iSemGetCurrentDeviceType);
                MediaDevice mediaDevice = null;
                for (MediaDevice mediaDevice2 : this.mMediaDevices) {
                    if (mediaDevice2 instanceof BluetoothMediaDevice) {
                        if (iSemGetCurrentDeviceType != 8 && iSemGetCurrentDeviceType != 23) {
                            if (iSemGetCurrentDeviceType == 26 || iSemGetCurrentDeviceType == 27 || iSemGetCurrentDeviceType == 30) {
                            }
                        }
                        if (isActiveDevice(((BluetoothMediaDevice) mediaDevice2).mCachedDevice) && mediaDevice2.isConnected() && (mediaRoute2Info = mediaDevice2.mRouteInfo) != null && mediaRoute2Info.getType() == iSemGetCurrentDeviceType) {
                            Log.i("LocalMediaManager", "updateCurrentConnectedDevice device name = " + ((BluetoothMediaDevice) mediaDevice2).mCachedDevice.getName() + " device type = " + mediaDevice2.mType);
                            return mediaDevice2;
                        }
                    }
                    if ((mediaDevice2 instanceof PhoneMediaDevice) && AudioDeviceInfo.convertDeviceTypeToInternalDevice(iSemGetCurrentDeviceType) == mediaDevice2.getDevice()) {
                        Log.i("LocalMediaManager", "updateCurrentConnectedDevice device name = " + mediaDevice2.getName() + " device type = " + mediaDevice2.mType);
                        mediaDevice = mediaDevice2;
                    }
                }
                return mediaDevice;
            } catch (Throwable th) {
                throw th;
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
        this.mReceiver = new Receiver(this, 0);
        this.mContext = context;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mInfoMediaManager = infoMediaManager;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    }

    public interface DeviceCallback {
        void onDeviceListUpdate(List list);

        void onSelectedDeviceStateChanged(MediaDevice mediaDevice);

        default void onAboutToConnectDeviceRemoved() {
        }

        default void onDeviceAttributesChanged() {
        }

        default void onRequestFailed(int i) {
        }

        default void onAboutToConnectDeviceAdded(String str, Drawable drawable, String str2) {
        }
    }
}
