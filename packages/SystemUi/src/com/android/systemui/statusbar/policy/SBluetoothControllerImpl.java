package com.android.systemui.statusbar.policy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.QpRune;
import com.android.systemui.bluetooth.BluetoothLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.statusbar.policy.bluetooth.BluetoothRepository;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SBluetoothControllerImpl implements SBluetoothController, BluetoothCallback, CachedBluetoothDevice.Callback, LocalBluetoothProfileManager.ServiceListener, SemBluetoothCallback, BluetoothCastCallback {
    public static final boolean DEBUG = Log.isLoggable("SBluetoothControllerImpl", 3);
    public final List mConnectedDevices = new ArrayList();
    public int mConnectionState = 0;
    public final int mCurrentUser;
    public final DesktopManager mDesktopManager;
    public boolean mEnabled;
    public final HandlerC3433H mHandler;
    public boolean mIsActive;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final BluetoothLogger mLogger;
    public int mState;
    public final UserManager mUserManager;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.policy.SBluetoothControllerImpl$1 */
    public final class C34321 {
        public C34321() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.policy.SBluetoothControllerImpl$H */
    public final class HandlerC3433H extends Handler {
        public final ArrayList mCallbacks;

        public HandlerC3433H(Looper looper) {
            super(looper);
            this.mCallbacks = new ArrayList();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z = SBluetoothControllerImpl.DEBUG;
            if (z) {
                RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("handleMessage : "), message.what, "SBluetoothControllerImpl");
            }
            switch (message.what) {
                case 1:
                    SBluetoothControllerImpl.this.mHandler.removeMessages(1);
                    Log.d("SBluetoothControllerImpl", " firePairedDevicesChanged ");
                    if (z) {
                        Log.d("SBluetoothControllerImpl", "firePairedDevicesChanged");
                    }
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((SBluetoothController.SCallback) it.next()).onBluetoothDevicesChanged();
                    }
                    break;
                case 2:
                    Iterator it2 = this.mCallbacks.iterator();
                    while (it2.hasNext()) {
                        ((SBluetoothController.SCallback) it2.next()).onBluetoothStateChange(SBluetoothControllerImpl.this.mEnabled);
                    }
                    break;
                case 3:
                    this.mCallbacks.add((SBluetoothController.SCallback) message.obj);
                    break;
                case 4:
                    this.mCallbacks.remove((SBluetoothController.SCallback) message.obj);
                    break;
                case 5:
                    boolean booleanValue = ((Boolean) message.obj).booleanValue();
                    Iterator it3 = this.mCallbacks.iterator();
                    while (it3.hasNext()) {
                        ((SBluetoothController.SCallback) it3.next()).onBluetoothScanStateChanged(booleanValue);
                    }
                    break;
                case 6:
                    Log.d("SBluetoothControllerImpl", "fireMusicShareStateChanged((CachedBluetoothCastDevice) msg.obj)");
                    break;
                case 7:
                    boolean booleanValue2 = ((Boolean) message.obj).booleanValue();
                    Iterator it4 = this.mCallbacks.iterator();
                    while (it4.hasNext()) {
                        ((SBluetoothController.SCallback) it4.next()).onMusicShareDiscoveryStateChanged(booleanValue2);
                    }
                    break;
            }
        }
    }

    public SBluetoothControllerImpl(Context context, FeatureFlags featureFlags, UserTracker userTracker, DumpManager dumpManager, BluetoothLogger bluetoothLogger, BluetoothRepository bluetoothRepository, Looper looper, LocalBluetoothManager localBluetoothManager, BluetoothAdapter bluetoothAdapter, DesktopManager desktopManager) {
        int i;
        C34321 c34321 = new C34321();
        this.mLogger = bluetoothLogger;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mHandler = new HandlerC3433H(looper);
        if (localBluetoothManager != null) {
            ((CopyOnWriteArrayList) localBluetoothManager.mEventManager.mCallbacks).add(this);
            ((CopyOnWriteArrayList) localBluetoothManager.mProfileManager.mServiceListeners).add(this);
            BluetoothEventManager bluetoothEventManager = localBluetoothManager.mEventManager;
            synchronized (bluetoothEventManager.mSemCallbacks) {
                ((ArrayList) bluetoothEventManager.mSemCallbacks).add(this);
            }
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            synchronized (localBluetoothAdapter) {
                BluetoothAdapter bluetoothAdapter2 = localBluetoothAdapter.mAdapter;
                if (bluetoothAdapter2.getState() != localBluetoothAdapter.mState) {
                    localBluetoothAdapter.setBluetoothStateInt(bluetoothAdapter2.getState());
                }
                i = localBluetoothAdapter.mState;
            }
            onBluetoothStateChanged(i);
            if (QpRune.QUICK_BLUETOOTH_MUSIC_SHARE) {
                BluetoothCastEventManager bluetoothCastEventManager = localBluetoothManager.mCastEventManager;
                synchronized (bluetoothCastEventManager.mCallbacks) {
                    ((ArrayList) bluetoothCastEventManager.mCallbacks).add(this);
                }
                LocalBluetoothCastAdapter localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter;
                SBluetoothControllerImpl$$ExternalSyntheticLambda0 sBluetoothControllerImpl$$ExternalSyntheticLambda0 = new SBluetoothControllerImpl$$ExternalSyntheticLambda0(this);
                Log.d(localBluetoothCastAdapter.TAG, "callback added");
                localBluetoothCastAdapter.mCallbacks.add(sBluetoothControllerImpl$$ExternalSyntheticLambda0);
            }
        }
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mCurrentUser = ((UserTrackerImpl) userTracker).getUserId();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "SBluetoothControllerImpl", this);
        this.mDesktopManager = desktopManager;
        ((DesktopManagerImpl) desktopManager).mDesktopBluetoothCallback = c34321;
    }

    public static String connectionStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("UNKNOWN(", i, ")") : "DISCONNECTING" : "CONNECTED" : "CONNECTING" : "DISCONNECTED";
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        HandlerC3433H handlerC3433H = this.mHandler;
        handlerC3433H.obtainMessage(3, (BluetoothController.Callback) obj).sendToTarget();
        handlerC3433H.sendEmptyMessage(2);
    }

    public final boolean canConfigBluetooth() {
        int i = this.mCurrentUser;
        UserHandle of = UserHandle.of(i);
        UserManager userManager = this.mUserManager;
        return (userManager.hasUserRestriction("no_config_bluetooth", of) || userManager.hasUserRestriction("no_bluetooth", UserHandle.of(i))) ? false : true;
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        ArrayList arrayList;
        printWriter.println("BluetoothController state:");
        printWriter.print("  mLocalBluetoothManager=");
        printWriter.println(this.mLocalBluetoothManager);
        if (this.mLocalBluetoothManager == null) {
            return;
        }
        printWriter.print("  mEnabled=");
        printWriter.println(this.mEnabled);
        printWriter.print("  mConnectionState=");
        printWriter.println(connectionStateToString(this.mConnectionState));
        printWriter.print("  mAudioProfileOnly=");
        printWriter.println(false);
        printWriter.print("  mIsActive=");
        printWriter.println(this.mIsActive);
        printWriter.print("  mConnectedDevices=");
        synchronized (this.mConnectedDevices) {
            arrayList = new ArrayList(this.mConnectedDevices);
        }
        printWriter.println(arrayList);
        printWriter.print("  mCallbacks.size=");
        printWriter.println(this.mHandler.mCallbacks.size());
        printWriter.println("  Bluetooth Devices:");
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        for (CachedBluetoothDevice cachedBluetoothDevice : localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : null) {
            StringBuilder sb = new StringBuilder("    ");
            StringBuilder sb2 = new StringBuilder();
            sb2.append(cachedBluetoothDevice.getName());
            sb2.append(" profiles=");
            ArrayList arrayList2 = new ArrayList();
            Iterator it = cachedBluetoothDevice.getProfiles().iterator();
            while (it.hasNext()) {
                arrayList2.add(String.valueOf(((LocalBluetoothProfile) it.next()).getProfileId()));
            }
            sb2.append("[" + String.join(",", arrayList2) + "]");
            sb2.append(" connected=");
            sb2.append(cachedBluetoothDevice.isConnected());
            sb2.append(" active[A2DP]=");
            sb2.append(cachedBluetoothDevice.isActiveDevice(2));
            sb2.append(" active[HEADSET]=");
            sb2.append(cachedBluetoothDevice.isActiveDevice(1));
            sb2.append(" active[HEARING_AID]=");
            sb2.append(cachedBluetoothDevice.isActiveDevice(21));
            sb2.append(" active[LE_AUDIO]=");
            sb2.append(cachedBluetoothDevice.isActiveDevice(22));
            sb.append(sb2.toString());
            printWriter.println(sb.toString());
        }
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final int getBluetoothState() {
        return this.mState;
    }

    public final List getConnectedDevicesForGroup() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return null;
        }
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        List<BluetoothDevice> connectedDeviceList = localBluetoothAdapter.mAdapter.getConnectedDeviceList();
        if (connectedDeviceList == null) {
            return connectedDeviceList;
        }
        ArrayList arrayList = new ArrayList();
        LeAudioProfile leAudioProfile = localBluetoothAdapter.mProfileManager.mLeAudioProfile;
        if (leAudioProfile == null) {
            return connectedDeviceList;
        }
        for (BluetoothDevice bluetoothDevice : connectedDeviceList) {
            BluetoothLeAudio bluetoothLeAudio = leAudioProfile.mService;
            int groupId = (bluetoothLeAudio == null || bluetoothDevice == null) ? -1 : bluetoothLeAudio.getGroupId(bluetoothDevice);
            if (groupId == -1) {
                arrayList.add(bluetoothDevice);
            } else {
                Iterator it = arrayList.iterator();
                boolean z = false;
                while (it.hasNext()) {
                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) it.next();
                    BluetoothLeAudio bluetoothLeAudio2 = leAudioProfile.mService;
                    if (((bluetoothLeAudio2 == null || bluetoothDevice2 == null) ? -1 : bluetoothLeAudio2.getGroupId(bluetoothDevice2)) == groupId) {
                        z = true;
                    }
                }
                if (!z) {
                    arrayList.add(bluetoothDevice);
                }
            }
        }
        return arrayList;
    }

    public final String getLastDeviceName() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return null;
        }
        List connectedDeviceList = localBluetoothManager.mLocalAdapter.mAdapter.getConnectedDeviceList();
        CachedBluetoothDevice findDevice = localBluetoothManager.mCachedDeviceManager.findDevice((connectedDeviceList == null || connectedDeviceList.size() <= 0) ? null : (BluetoothDevice) connectedDeviceList.get(0));
        if (findDevice == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String str = findDevice.mPrefixName;
        if (str == null) {
            str = "";
        }
        sb.append(str);
        sb.append(findDevice.getName());
        return sb.toString();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final boolean isBluetoothEnabled() {
        return this.mEnabled;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logAclConnectionStateChanged(cachedBluetoothDevice.getAddress(), connectionStateToString(i));
        }
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("ACLConnectionStateChanged=");
            sb.append(cachedBluetoothDevice.getAddress());
            sb.append(" ");
            ExifInterface$$ExternalSyntheticOutline0.m35m(sb, i != 0 ? i != 1 ? i != 2 ? i != 3 ? LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("UNKNOWN(", i, ")") : "DISCONNECTING" : "CONNECTED" : "CONNECTING" : "DISCONNECTED", "SBluetoothControllerImpl");
        }
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logActiveDeviceChanged(i, cachedBluetoothDevice == null ? null : cachedBluetoothDevice.getAddress());
        }
        if (DEBUG && cachedBluetoothDevice != null) {
            Log.d("SBluetoothControllerImpl", "ActiveDeviceChanged=" + cachedBluetoothDevice.getAddress() + " profileId=" + i);
        }
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        boolean z = false;
        for (CachedBluetoothDevice cachedBluetoothDevice2 : localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : null) {
            boolean z2 = true;
            if (!cachedBluetoothDevice2.isActiveDevice(1) && !cachedBluetoothDevice2.isActiveDevice(2) && !cachedBluetoothDevice2.isActiveDevice(21) && !cachedBluetoothDevice2.isActiveDevice(22)) {
                z2 = false;
            }
            z |= z2;
        }
        boolean z3 = this.mIsActive;
        HandlerC3433H handlerC3433H = this.mHandler;
        if (z3 != z) {
            this.mIsActive = z;
            handlerC3433H.sendEmptyMessage(2);
        }
        handlerC3433H.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logStateChange(BluetoothAdapter.nameForState(i));
        }
        this.mEnabled = this.mLocalBluetoothManager.mLocalAdapter.mAdapter.isEnabled();
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("onBluetoothStateChanged is called++++++ = "), this.mEnabled, "SBluetoothControllerImpl");
        this.mState = i;
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        if (DEBUG) {
            Log.d("SBluetoothControllerImpl", "onConnectionStateChanged cachedDevice=" + cachedBluetoothDevice + ", state=" + i);
        }
        updateConnected();
        this.mConnectionState = i;
        if (i != 1) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logDeviceAdded(cachedBluetoothDevice.getAddress());
        }
        synchronized (cachedBluetoothDevice.mCallbacks) {
            if (((CopyOnWriteArrayList) cachedBluetoothDevice.mCallbacks).contains(this)) {
                ((CopyOnWriteArrayList) cachedBluetoothDevice.mCallbacks).remove(this);
            }
            ((CopyOnWriteArrayList) cachedBluetoothDevice.mCallbacks).add(this);
        }
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logDeviceAttributesChanged();
        }
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logBondStateChange(i, cachedBluetoothDevice.getAddress());
        }
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logDeviceDeleted(cachedBluetoothDevice.getAddress());
        }
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logProfileConnectionStateChanged(i2, cachedBluetoothDevice.getAddress(), connectionStateToString(i));
        }
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    public final void onProfileStateChanged(LocalBluetoothProfile localBluetoothProfile, int i, int i2) {
        if (DEBUG) {
            StringBuilder sb = new StringBuilder("onProfileStateChanged profile =");
            sb.append(localBluetoothProfile);
            sb.append(", newState=");
            sb.append(i);
            sb.append(", oldState=");
            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, i2, "SBluetoothControllerImpl");
        }
        HandlerC3433H handlerC3433H = this.mHandler;
        handlerC3433H.sendMessageDelayed(handlerC3433H.obtainMessage(1), 100L);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {
        this.mHandler.obtainMessage(5, Boolean.valueOf(z)).sendToTarget();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        if (DEBUG) {
            Log.d("SBluetoothControllerImpl", "onServiceConnected");
        }
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mHandler.obtainMessage(4, (BluetoothController.Callback) obj).sendToTarget();
    }

    public final void scan(boolean z) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return;
        }
        if (DEBUG) {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("scan = ", z, "SBluetoothControllerImpl");
        }
        if (!z) {
            stopScan();
            return;
        }
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        localBluetoothAdapter.getClass();
        StringBuilder sb = new StringBuilder("startScanning :: true, isDiscovering : ");
        BluetoothAdapter bluetoothAdapter = localBluetoothAdapter.mAdapter;
        sb.append(bluetoothAdapter.isDiscovering());
        Log.d("LocalBluetoothAdapter", sb.toString());
        if (bluetoothAdapter.isDiscovering() || !bluetoothAdapter.startDiscovery()) {
            return;
        }
        localBluetoothAdapter.mLastScan = System.currentTimeMillis();
        Log.d("LocalBluetoothAdapter", "startScanning :: done! mLastScan=" + localBluetoothAdapter.mLastScan);
    }

    public final void scanMusicShareDevices(boolean z, boolean z2) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager == null) {
            return;
        }
        if (DEBUG) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m59m("scanMusicShareDevices = ", z, ", detailListening = ", z2, "SBluetoothControllerImpl");
        }
        LocalBluetoothCastAdapter localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter;
        if (z) {
            SemBluetoothCastAdapter semBluetoothCastAdapter = localBluetoothCastAdapter.mCastAdapter;
            String str = localBluetoothCastAdapter.TAG;
            if (semBluetoothCastAdapter == null) {
                Log.d(str, "Cannot startDiscovery");
                return;
            }
            Log.d(str, "startDiscovery");
            localBluetoothCastAdapter.mAlarmManager.setExact(2, SystemClock.elapsedRealtime() + 12000, "Discovery", localBluetoothCastAdapter.mDiscoveryAlarmListener, null);
            localBluetoothCastAdapter.mCastAdapter.startDiscovery();
            return;
        }
        if (z2) {
            localBluetoothCastAdapter.suspendDiscovery();
            return;
        }
        SemBluetoothCastAdapter semBluetoothCastAdapter2 = localBluetoothCastAdapter.mCastAdapter;
        String str2 = localBluetoothCastAdapter.TAG;
        if (semBluetoothCastAdapter2 == null) {
            Log.d(str2, "Cannot cancelDiscovery");
        } else {
            Log.d(str2, "cancelDiscovery");
            localBluetoothCastAdapter.mCastAdapter.cancelDiscovery();
        }
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final void setBluetoothEnabled(boolean z) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mLocalAdapter.setBluetoothEnabled(z);
        }
    }

    public final void setScanMode(int i) {
        if (i == 23 || i == 21) {
            this.mLocalBluetoothManager.mLocalAdapter.mAdapter.setScanMode(i);
        }
    }

    public final void stopScan() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        BluetoothAdapter bluetoothAdapter = localBluetoothManager.mLocalAdapter.mAdapter;
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }
        if (localBluetoothManager.mLocalAdapter.mAdapter.isDiscovering()) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                Log.d("SBluetoothControllerImpl", "InterruptedException while waiting: " + e);
            }
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("stopScan = "), !r4.mAdapter.isDiscovering(), "SBluetoothControllerImpl");
    }

    public final void updateConnected() {
        int connectionState = this.mLocalBluetoothManager.mLocalAdapter.mAdapter.getConnectionState();
        if (DEBUG) {
            RecyclerView$$ExternalSyntheticOutline0.m46m(AbstractC0000x2c234b15.m1m("updateConnected = ", connectionState, "mConnectionState = "), this.mConnectionState, "SBluetoothControllerImpl");
        }
        if (connectionState != this.mConnectionState) {
            this.mConnectionState = connectionState;
            if (connectionState != 1) {
                this.mHandler.sendEmptyMessage(2);
            }
        }
    }

    public final void setBluetoothEnabled(boolean z, boolean z2) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mLocalAdapter.setBluetoothEnabled(z);
            StringBuilder sb = new StringBuilder("setBluetoothEnabled  = ");
            sb.append(z);
            sb.append(" showDialog =");
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(sb, z2, "SBluetoothControllerImpl");
            if (z2 && z) {
                localBluetoothManager.mEventManager.getClass();
                BluetoothUtils.setQuickPannelOn(true);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {
    }
}
