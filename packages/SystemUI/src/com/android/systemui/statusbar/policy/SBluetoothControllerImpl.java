package com.android.systemui.statusbar.policy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
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
import com.android.settingslib.flags.Flags;
import com.android.systemui.QpRune;
import com.android.systemui.bluetooth.BluetoothLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastEventManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class SBluetoothControllerImpl implements SBluetoothController, BluetoothCallback, CachedBluetoothDevice.Callback, LocalBluetoothProfileManager.ServiceListener, BluetoothCastCallback {
    public final List mConnectedDevices = new ArrayList();
    public int mConnectionState = 0;
    public final Context mContext;
    public final int mCurrentUser;
    public final AnonymousClass1 mDesktopBluetoothCallback;
    public final DesktopManager mDesktopManager;
    public boolean mEnabled;
    public final H mHandler;
    public boolean mIsActive;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final BluetoothLogger mLogger;
    public int mState;
    public final UserManager mUserManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.policy.SBluetoothControllerImpl$1, reason: invalid class name */
    public final class AnonymousClass1 implements BluetoothDesktopCallback {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface BluetoothDesktopCallback {
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class H extends Handler {
        public final ArrayList mCallbacks;

        public H(Looper looper) {
            super(looper);
            this.mCallbacks = new ArrayList();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            RecyclerView$$ExternalSyntheticOutline0.m(message.what, "SBluetoothControllerImpl", new StringBuilder("handleMessage : "));
            int i = message.what;
            SBluetoothControllerImpl sBluetoothControllerImpl = SBluetoothControllerImpl.this;
            switch (i) {
                case 1:
                    sBluetoothControllerImpl.mHandler.removeMessages(1);
                    Log.d("SBluetoothControllerImpl", " firePairedDevicesChanged ");
                    Log.d("SBluetoothControllerImpl", "firePairedDevicesChanged");
                    Iterator it = this.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((SBluetoothController.SCallback) it.next()).onBluetoothDevicesChanged();
                    }
                    break;
                case 2:
                    Iterator it2 = this.mCallbacks.iterator();
                    while (it2.hasNext()) {
                        ((SBluetoothController.SCallback) it2.next()).onBluetoothStateChange(sBluetoothControllerImpl.mEnabled);
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
                    Iterator it4 = this.mCallbacks.iterator();
                    while (it4.hasNext()) {
                        ((SBluetoothController.SCallback) it4.next()).onMusicShareStateChanged();
                    }
                    break;
                case 7:
                    boolean booleanValue2 = ((Boolean) message.obj).booleanValue();
                    Iterator it5 = this.mCallbacks.iterator();
                    while (it5.hasNext()) {
                        ((SBluetoothController.SCallback) it5.next()).onMusicShareDiscoveryStateChanged(booleanValue2);
                    }
                    break;
            }
        }
    }

    public SBluetoothControllerImpl(Context context, UserTracker userTracker, DumpManager dumpManager, BluetoothLogger bluetoothLogger, Looper looper, LocalBluetoothManager localBluetoothManager, BluetoothAdapter bluetoothAdapter, DesktopManager desktopManager) {
        int i;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mContext = context;
        this.mLogger = bluetoothLogger;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mHandler = new H(looper);
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.registerCallback(this);
            ((CopyOnWriteArrayList) localBluetoothManager.mProfileManager.mServiceListeners).add(this);
            BluetoothEventManager bluetoothEventManager = localBluetoothManager.mEventManager;
            synchronized (bluetoothEventManager.mSemCallbacks) {
                ((ArrayList) bluetoothEventManager.mSemCallbacks).add(this);
            }
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            synchronized (localBluetoothAdapter) {
                if (localBluetoothAdapter.mAdapter.getState() != localBluetoothAdapter.mState) {
                    localBluetoothAdapter.setBluetoothStateInt(localBluetoothAdapter.mAdapter.getState());
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
        desktopManager.setDesktopBluetoothCallback(anonymousClass1);
    }

    public static String connectionStateToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")") : "DISCONNECTING" : "CONNECTED" : "CONNECTING" : "DISCONNECTED";
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        H h = this.mHandler;
        h.obtainMessage(3, (BluetoothController.Callback) obj).sendToTarget();
        h.sendEmptyMessage(2);
    }

    public final boolean canConfigBluetooth() {
        UserManager userManager = this.mUserManager;
        int i = this.mCurrentUser;
        return (userManager.hasUserRestriction("no_config_bluetooth", UserHandle.of(i)) || this.mUserManager.hasUserRestriction("no_bluetooth", UserHandle.of(i))) ? false : true;
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
        for (CachedBluetoothDevice cachedBluetoothDevice : getDevices$1()) {
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
            CarrierTextController$$ExternalSyntheticOutline0.m(sb, sb2.toString(), printWriter);
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
            int groupId = bluetoothLeAudio == null ? -1 : bluetoothLeAudio.getGroupId(bluetoothDevice);
            if (groupId == -1) {
                arrayList.add(bluetoothDevice);
            } else {
                Iterator it = arrayList.iterator();
                boolean z = false;
                while (it.hasNext()) {
                    BluetoothDevice bluetoothDevice2 = (BluetoothDevice) it.next();
                    BluetoothLeAudio bluetoothLeAudio2 = leAudioProfile.mService;
                    if ((bluetoothLeAudio2 == null ? -1 : bluetoothLeAudio2.getGroupId(bluetoothDevice2)) == groupId) {
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

    public final Collection getDevices$1() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        Collection cachedDevicesCopy = localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : null;
        Flags.enableHideExclusivelyManagedBluetoothDevice();
        return cachedDevicesCopy.stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.policy.SBluetoothControllerImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return !BluetoothUtils.isExclusivelyManagedBluetoothDevice(SBluetoothControllerImpl.this.mContext, ((CachedBluetoothDevice) obj).mDevice);
            }
        }).toList();
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
    public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logAclConnectionStateChanged(cachedBluetoothDevice.mDevice.getAddress(), connectionStateToString(i));
        }
        StringBuilder sb = new StringBuilder("ACLConnectionStateChanged=");
        sb.append(cachedBluetoothDevice.mDevice.getAddress());
        sb.append(" ");
        sb.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? LazyListMeasuredItem$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")") : "DISCONNECTING" : "CONNECTED" : "CONNECTING" : "DISCONNECTED");
        Log.d("SBluetoothControllerImpl", sb.toString());
        updateConnected$1();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        if (cachedBluetoothDevice == null) {
            return;
        }
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logActiveDeviceChanged(i, cachedBluetoothDevice.mDevice.getAddress());
        }
        Log.d("SBluetoothControllerImpl", "ActiveDeviceChanged=" + cachedBluetoothDevice.mDevice.getAddress() + " profileId=" + i);
        boolean z = false;
        for (CachedBluetoothDevice cachedBluetoothDevice2 : getDevices$1()) {
            boolean z2 = true;
            if (!cachedBluetoothDevice2.isActiveDevice(1) && !cachedBluetoothDevice2.isActiveDevice(2) && !cachedBluetoothDevice2.isActiveDevice(21) && !cachedBluetoothDevice2.isActiveDevice(22)) {
                z2 = false;
            }
            z |= z2;
        }
        boolean z3 = this.mIsActive;
        H h = this.mHandler;
        if (z3 != z) {
            this.mIsActive = z;
            h.sendEmptyMessage(2);
        }
        h.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onBluetoothStateChanged(int i) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logStateChange(BluetoothAdapter.nameForState(i));
        }
        this.mEnabled = this.mLocalBluetoothManager.mLocalAdapter.mAdapter.isEnabled();
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("onBluetoothStateChanged is called++++++ = "), this.mEnabled, "SBluetoothControllerImpl");
        this.mState = i;
        updateConnected$1();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public final void onCastDeviceAdded() {
        Log.d("SBluetoothControllerImpl", "onCastDeviceAdded");
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public final void onCastDeviceRemoved() {
        Log.d("SBluetoothControllerImpl", "onCastDeviceRemoved:");
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public final void onCastProfileStateChanged(CachedBluetoothCastDevice cachedBluetoothCastDevice) {
        this.mHandler.obtainMessage(6, cachedBluetoothCastDevice).sendToTarget();
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        Log.d("SBluetoothControllerImpl", "onConnectionStateChanged cachedDevice=" + cachedBluetoothDevice + ", state=" + i);
        updateConnected$1();
        this.mConnectionState = i;
        if (i != 1) {
            this.mHandler.sendEmptyMessage(2);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logDeviceAdded(cachedBluetoothDevice.mDevice.getAddress());
        }
        cachedBluetoothDevice.registerCallback(this);
        updateConnected$1();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logDeviceAttributesChanged();
        }
        updateConnected$1();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logBondStateChange(i, cachedBluetoothDevice.mDevice.getAddress());
        }
        updateConnected$1();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logDeviceDeleted(cachedBluetoothDevice.mDevice.getAddress());
        }
        updateConnected$1();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            bluetoothLogger.logProfileConnectionStateChanged(i2, cachedBluetoothDevice.mDevice.getAddress(), connectionStateToString(i));
        }
        updateConnected$1();
        this.mHandler.sendEmptyMessage(2);
    }

    public final void onProfileStateChanged(LocalBluetoothProfile localBluetoothProfile, int i, int i2) {
        StringBuilder sb = new StringBuilder("onProfileStateChanged profile =");
        sb.append(localBluetoothProfile);
        sb.append(", newState=");
        sb.append(i);
        sb.append(", oldState=");
        RecyclerView$$ExternalSyntheticOutline0.m(i2, "SBluetoothControllerImpl", sb);
        H h = this.mHandler;
        h.sendMessageDelayed(h.obtainMessage(1), 100L);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onScanningStateChanged(boolean z) {
        this.mHandler.obtainMessage(5, Boolean.valueOf(z)).sendToTarget();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        Log.d("SBluetoothControllerImpl", "onServiceConnected");
        updateConnected$1();
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
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("scan = ", "SBluetoothControllerImpl", z);
        if (!z) {
            stopScan();
            return;
        }
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        localBluetoothAdapter.getClass();
        Log.d("LocalBluetoothAdapter", "startScanning :: true, isDiscovering : " + localBluetoothAdapter.mAdapter.isDiscovering());
        if (localBluetoothAdapter.mAdapter.isDiscovering() || !localBluetoothAdapter.mAdapter.startDiscovery()) {
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
        EmergencyButtonController$$ExternalSyntheticOutline0.m("scanMusicShareDevices = ", ", detailListening = ", "SBluetoothControllerImpl", z, z2);
        if (z) {
            localBluetoothManager.mLocalCastAdapter.startDiscovery();
        } else if (z2) {
            localBluetoothManager.mLocalCastAdapter.suspendDiscovery();
        } else {
            localBluetoothManager.mLocalCastAdapter.cancelDiscovery();
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
        LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
        if (localBluetoothAdapter.mAdapter.isDiscovering()) {
            localBluetoothAdapter.mAdapter.cancelDiscovery();
        }
        if (localBluetoothManager.mLocalAdapter.mAdapter.isDiscovering()) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                Log.d("SBluetoothControllerImpl", "InterruptedException while waiting: " + e);
            }
        }
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("stopScan = "), !localBluetoothManager.mLocalAdapter.mAdapter.isDiscovering(), "SBluetoothControllerImpl");
    }

    public final void updateConnected$1() {
        int connectionState = this.mLocalBluetoothManager.mLocalAdapter.mAdapter.getConnectionState();
        RecyclerView$$ExternalSyntheticOutline0.m(this.mConnectionState, "SBluetoothControllerImpl", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(connectionState, "updateConnected = ", "mConnectionState = "));
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
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z2, "SBluetoothControllerImpl");
            if (z2 && z) {
                localBluetoothManager.mEventManager.getClass();
                BluetoothUtils.setQuickPannelOn(true);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {
    }

    @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public final void onCastDiscoveryStateChanged(boolean z) {
    }
}
