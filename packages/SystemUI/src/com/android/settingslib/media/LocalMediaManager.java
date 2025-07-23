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
import com.android.settingslib.media.InfoMediaManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                CachedBluetoothDevice findDevice = cachedBluetoothDeviceManager.findDevice((BluetoothDevice) it.next());
                if (findDevice != null && findDevice.mBondState == 12 && !findDevice.isConnected() && isMediaDevice(findDevice)) {
                    i2++;
                    arrayList.add(findDevice);
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
                    CachedBluetoothDevice findDevice = cachedBluetoothDeviceManager.findDevice((BluetoothDevice) it.next());
                    if (findDevice != null && findDevice.mBondState == 12 && !findDevice.isConnected() && isMediaDevice(findDevice)) {
                        AudioDeviceAttributes mutingExpectedDevice = localMediaManager.mAudioManager.getMutingExpectedDevice();
                        if (mutingExpectedDevice != null ? findDevice.mDevice.getAddress().equals(mutingExpectedDevice.getAddress()) : false) {
                            return new BluetoothMediaDevice(localMediaManager.mContext, findDevice, null, null);
                        }
                    }
                }
            }
            return null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Receiver extends BroadcastReceiver {
        public /* synthetic */ Receiver(LocalMediaManager localMediaManager, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            MediaDevice updateCurrentConnectedDevice;
            String action = intent.getAction();
            if ((!"android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED".equals(action) && !"android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED".equals(action)) || (updateCurrentConnectedDevice = LocalMediaManager.this.updateCurrentConnectedDevice()) == null || updateCurrentConnectedDevice.equals(LocalMediaManager.this.mCurrentConnectedDevice)) {
                return;
            }
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            localMediaManager.mCurrentConnectedDevice = updateCurrentConnectedDevice;
            localMediaManager.dispatchSelectedDeviceStateChanged(updateCurrentConnectedDevice);
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
            SharedPreferences.Editor edit = context.getSharedPreferences("seamless_transfer_record", 0).edit();
            connectionRecordManager.mLastSelectedDevice = id;
            edit.putInt(id, i);
            edit.putString("last_selected_device", connectionRecordManager.mLastSelectedDevice);
            edit.apply();
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

    /* JADX WARN: Removed duplicated region for block: B:28:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0075 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0062  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isActiveDevice(com.android.settingslib.bluetooth.CachedBluetoothDevice r7) {
        /*
            r6 = this;
            android.media.AudioManager r0 = r6.mAudioManager
            int r0 = r0.semGetCurrentDeviceType()
            com.android.settingslib.bluetooth.LocalBluetoothManager r6 = r6.mLocalBluetoothManager
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r1 = r6.mProfileManager
            com.android.settingslib.bluetooth.A2dpProfile r1 = r1.mA2dpProfile
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L22
            r4 = 8
            if (r0 != r4) goto L22
            android.bluetooth.BluetoothDevice r4 = r7.mDevice
            android.bluetooth.BluetoothDevice r1 = r1.getActiveDevice()
            boolean r1 = r4.equals(r1)
            if (r1 == 0) goto L22
            r1 = r2
            goto L23
        L22:
            r1 = r3
        L23:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r6 = r6.mProfileManager
            com.android.settingslib.bluetooth.HearingAidProfile r4 = r6.mHearingAidProfile
            if (r4 == 0) goto L47
            r5 = 23
            if (r0 != r5) goto L47
            android.bluetooth.BluetoothAdapter r4 = r4.mBluetoothAdapter
            if (r4 != 0) goto L37
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            goto L3d
        L37:
            r5 = 21
            java.util.List r4 = r4.getActiveDevices(r5)
        L3d:
            android.bluetooth.BluetoothDevice r5 = r7.mDevice
            boolean r4 = r4.contains(r5)
            if (r4 == 0) goto L47
            r4 = r2
            goto L48
        L47:
            r4 = r3
        L48:
            com.android.settingslib.bluetooth.LeAudioProfile r6 = r6.mLeAudioProfile
            if (r6 == 0) goto L72
            r5 = 26
            if (r0 == r5) goto L58
            r5 = 27
            if (r0 == r5) goto L58
            r5 = 30
            if (r0 != r5) goto L72
        L58:
            android.bluetooth.BluetoothAdapter r6 = r6.mBluetoothAdapter
            if (r6 != 0) goto L62
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            goto L68
        L62:
            r0 = 22
            java.util.List r6 = r6.getActiveDevices(r0)
        L68:
            android.bluetooth.BluetoothDevice r7 = r7.mDevice
            boolean r6 = r6.contains(r7)
            if (r6 == 0) goto L72
            r6 = r2
            goto L73
        L72:
            r6 = r3
        L73:
            if (r1 != 0) goto L7b
            if (r4 != 0) goto L7b
            if (r6 == 0) goto L7a
            goto L7b
        L7a:
            return r3
        L7b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.media.LocalMediaManager.isActiveDevice(com.android.settingslib.bluetooth.CachedBluetoothDevice):boolean");
    }

    public final void registerCallback(DeviceCallback deviceCallback) {
        boolean isEmpty = ((CopyOnWriteArrayList) this.mCallbacks).isEmpty();
        if (((CopyOnWriteArrayList) this.mCallbacks).contains(deviceCallback)) {
            return;
        }
        ((CopyOnWriteArrayList) this.mCallbacks).add(deviceCallback);
        if (isEmpty) {
            MediaDeviceCallback mediaDeviceCallback = this.mMediaDeviceCallback;
            InfoMediaManager infoMediaManager = this.mInfoMediaManager;
            boolean isEmpty2 = ((CopyOnWriteArrayList) infoMediaManager.mCallbacks).isEmpty();
            if (!((CopyOnWriteArrayList) infoMediaManager.mCallbacks).contains(mediaDeviceCallback)) {
                ((CopyOnWriteArrayList) infoMediaManager.mCallbacks).add(mediaDeviceCallback);
                if (isEmpty2) {
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

    /* JADX WARN: Removed duplicated region for block: B:22:0x0097 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x004e A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.android.settingslib.media.MediaDevice updateCurrentConnectedDevice() {
        /*
            r7 = this;
            java.lang.String r0 = "updateCurrentConnectedDevice curDeviceType = "
            java.lang.Object r1 = r7.mMediaDevicesLock
            monitor-enter(r1)
            android.media.AudioManager r2 = r7.mAudioManager     // Catch: java.lang.Throwable -> L95
            int r2 = r2.semGetCurrentDeviceType()     // Catch: java.lang.Throwable -> L95
            java.lang.String r3 = "LocalMediaManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L95
            r4.<init>(r0)     // Catch: java.lang.Throwable -> L95
            r4.append(r2)     // Catch: java.lang.Throwable -> L95
            java.lang.String r0 = r4.toString()     // Catch: java.lang.Throwable -> L95
            android.util.Log.i(r3, r0)     // Catch: java.lang.Throwable -> L95
            java.util.List<com.android.settingslib.media.MediaDevice> r0 = r7.mMediaDevices     // Catch: java.lang.Throwable -> L95
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> L95
            r3 = 0
        L24:
            boolean r4 = r0.hasNext()     // Catch: java.lang.Throwable -> L95
            if (r4 == 0) goto Lcd
            java.lang.Object r4 = r0.next()     // Catch: java.lang.Throwable -> L95
            com.android.settingslib.media.MediaDevice r4 = (com.android.settingslib.media.MediaDevice) r4     // Catch: java.lang.Throwable -> L95
            boolean r5 = r4 instanceof com.android.settingslib.media.BluetoothMediaDevice     // Catch: java.lang.Throwable -> L95
            if (r5 == 0) goto L97
            r5 = 8
            if (r2 == r5) goto L4e
            r5 = 23
            if (r2 == r5) goto L4e
            r5 = 26
            if (r2 == r5) goto L4b
            r5 = 27
            if (r2 == r5) goto L4b
            r5 = 30
            if (r2 != r5) goto L49
            goto L4b
        L49:
            r5 = 0
            goto L4c
        L4b:
            r5 = 1
        L4c:
            if (r5 == 0) goto L97
        L4e:
            r5 = r4
            com.android.settingslib.media.BluetoothMediaDevice r5 = (com.android.settingslib.media.BluetoothMediaDevice) r5     // Catch: java.lang.Throwable -> L95
            com.android.settingslib.bluetooth.CachedBluetoothDevice r5 = r5.mCachedDevice     // Catch: java.lang.Throwable -> L95
            boolean r5 = r7.isActiveDevice(r5)     // Catch: java.lang.Throwable -> L95
            if (r5 == 0) goto L24
            boolean r5 = r4.isConnected()     // Catch: java.lang.Throwable -> L95
            if (r5 == 0) goto L24
            android.media.MediaRoute2Info r5 = r4.mRouteInfo     // Catch: java.lang.Throwable -> L95
            if (r5 == 0) goto L24
            int r5 = r5.getType()     // Catch: java.lang.Throwable -> L95
            if (r5 != r2) goto L24
            java.lang.String r7 = "LocalMediaManager"
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L95
            r0.<init>()     // Catch: java.lang.Throwable -> L95
            java.lang.String r2 = "updateCurrentConnectedDevice device name = "
            r0.append(r2)     // Catch: java.lang.Throwable -> L95
            r2 = r4
            com.android.settingslib.media.BluetoothMediaDevice r2 = (com.android.settingslib.media.BluetoothMediaDevice) r2     // Catch: java.lang.Throwable -> L95
            com.android.settingslib.bluetooth.CachedBluetoothDevice r2 = r2.mCachedDevice     // Catch: java.lang.Throwable -> L95
            java.lang.String r2 = r2.getName()     // Catch: java.lang.Throwable -> L95
            r0.append(r2)     // Catch: java.lang.Throwable -> L95
            java.lang.String r2 = " device type = "
            r0.append(r2)     // Catch: java.lang.Throwable -> L95
            int r2 = r4.mType     // Catch: java.lang.Throwable -> L95
            r0.append(r2)     // Catch: java.lang.Throwable -> L95
            java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L95
            android.util.Log.i(r7, r0)     // Catch: java.lang.Throwable -> L95
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L95
            return r4
        L95:
            r7 = move-exception
            goto Lcf
        L97:
            boolean r5 = r4 instanceof com.android.settingslib.media.PhoneMediaDevice     // Catch: java.lang.Throwable -> L95
            if (r5 == 0) goto L24
            int r5 = android.media.AudioDeviceInfo.convertDeviceTypeToInternalDevice(r2)     // Catch: java.lang.Throwable -> L95
            int r6 = r4.getDevice()     // Catch: java.lang.Throwable -> L95
            if (r5 != r6) goto L24
            java.lang.String r3 = "LocalMediaManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L95
            r5.<init>()     // Catch: java.lang.Throwable -> L95
            java.lang.String r6 = "updateCurrentConnectedDevice device name = "
            r5.append(r6)     // Catch: java.lang.Throwable -> L95
            java.lang.String r6 = r4.getName()     // Catch: java.lang.Throwable -> L95
            r5.append(r6)     // Catch: java.lang.Throwable -> L95
            java.lang.String r6 = " device type = "
            r5.append(r6)     // Catch: java.lang.Throwable -> L95
            int r6 = r4.mType     // Catch: java.lang.Throwable -> L95
            r5.append(r6)     // Catch: java.lang.Throwable -> L95
            java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L95
            android.util.Log.i(r3, r5)     // Catch: java.lang.Throwable -> L95
            r3 = r4
            goto L24
        Lcd:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L95
            return r3
        Lcf:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L95
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.media.LocalMediaManager.updateCurrentConnectedDevice():com.android.settingslib.media.MediaDevice");
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
