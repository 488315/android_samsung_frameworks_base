package com.android.systemui.statusbar.policy;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.settingslib.flags.Flags;
import com.android.systemui.bluetooth.BluetoothLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.bluetooth.BluetoothRepository;
import com.android.systemui.statusbar.policy.bluetooth.BluetoothRepositoryImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BluetoothControllerImpl implements BluetoothController, BluetoothCallback, CachedBluetoothDevice.Callback, LocalBluetoothProfileManager.ServiceListener {
    public boolean mAudioProfileOnly;
    public final Executor mBackgroundExecutor;
    public final BluetoothRepository mBluetoothRepository;
    public final List mConnectedDevices = new ArrayList();
    public int mConnectionState = 0;
    public final Context mContext;
    public boolean mEnabled;
    public final H mHandler;
    public boolean mIsActive;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final BluetoothLogger mLogger;
    public int mState;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class H extends Handler {
        public final ArrayList mCallbacks;

        public H(Looper looper) {
            super(looper);
            this.mCallbacks = new ArrayList();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                Iterator it = this.mCallbacks.iterator();
                while (it.hasNext()) {
                    ((BluetoothController.Callback) it.next()).onBluetoothDevicesChanged();
                }
            } else if (i == 2) {
                Iterator it2 = this.mCallbacks.iterator();
                while (it2.hasNext()) {
                    ((BluetoothController.Callback) it2.next()).onBluetoothStateChange(BluetoothControllerImpl.this.mEnabled);
                }
            } else if (i == 3) {
                this.mCallbacks.add((BluetoothController.Callback) message.obj);
            } else {
                if (i != 4) {
                    return;
                }
                this.mCallbacks.remove((BluetoothController.Callback) message.obj);
            }
        }
    }

    public BluetoothControllerImpl(Context context, UserTracker userTracker, DumpManager dumpManager, BluetoothLogger bluetoothLogger, BluetoothRepository bluetoothRepository, Executor executor, Looper looper, LocalBluetoothManager localBluetoothManager, BluetoothAdapter bluetoothAdapter) {
        int i;
        this.mContext = context;
        this.mLogger = bluetoothLogger;
        this.mBluetoothRepository = bluetoothRepository;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mHandler = new H(looper);
        this.mBackgroundExecutor = executor;
        if (localBluetoothManager != null) {
            localBluetoothManager.mEventManager.registerCallback(this);
            ((CopyOnWriteArrayList) localBluetoothManager.mProfileManager.mServiceListeners).add(this);
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            synchronized (localBluetoothAdapter) {
                if (localBluetoothAdapter.mAdapter.getState() != localBluetoothAdapter.mState) {
                    localBluetoothAdapter.setBluetoothStateInt(localBluetoothAdapter.mAdapter.getState());
                }
                i = localBluetoothAdapter.mState;
            }
            onBluetoothStateChanged(i);
        }
        ((UserTrackerImpl) userTracker).getUserId();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "BluetoothController", this);
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
        printWriter.println(this.mAudioProfileOnly);
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
        for (CachedBluetoothDevice cachedBluetoothDevice : getDevices()) {
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

    public final Collection getDevices() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        Collection cachedDevicesCopy = localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : Collections.emptyList();
        Flags.enableHideExclusivelyManagedBluetoothDevice();
        return cachedDevicesCopy.stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.policy.BluetoothControllerImpl$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return !BluetoothUtils.isExclusivelyManagedBluetoothDevice(BluetoothControllerImpl.this.mContext, ((CachedBluetoothDevice) obj).mDevice);
            }
        }).toList();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final boolean isBluetoothEnabled() {
        return this.mEnabled;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        this.mLogger.logAclConnectionStateChanged(cachedBluetoothDevice.mDevice.getAddress(), connectionStateToString(i));
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        this.mLogger.logActiveDeviceChanged(i, cachedBluetoothDevice == null ? null : cachedBluetoothDevice.mDevice.getAddress());
        boolean z = false;
        for (CachedBluetoothDevice cachedBluetoothDevice2 : getDevices()) {
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
        this.mLogger.logStateChange(BluetoothAdapter.nameForState(i));
        this.mEnabled = i == 12 || i == 11;
        this.mState = i;
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        this.mLogger.logDeviceConnectionStateChanged(cachedBluetoothDevice == null ? null : cachedBluetoothDevice.mDevice.getAddress(), connectionStateToString(i));
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mLogger.logDeviceAdded(cachedBluetoothDevice.mDevice.getAddress());
        cachedBluetoothDevice.registerCallback(this);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        this.mLogger.logDeviceAttributesChanged();
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        this.mLogger.logBondStateChange(i, cachedBluetoothDevice.mDevice.getAddress());
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mLogger.logDeviceDeleted(cachedBluetoothDevice.mDevice.getAddress());
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        this.mLogger.logProfileConnectionStateChanged(i2, cachedBluetoothDevice.mDevice.getAddress(), connectionStateToString(i));
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceConnected() {
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        this.mHandler.obtainMessage(4, (BluetoothController.Callback) obj).sendToTarget();
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final void setBluetoothEnabled(boolean z) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mLocalAdapter.setBluetoothEnabled(z);
        }
    }

    public final void updateConnected() {
        ((BluetoothRepositoryImpl) this.mBluetoothRepository).fetchConnectionStatusInBackground(getDevices(), new BluetoothControllerImpl$$ExternalSyntheticLambda1(this));
    }
}
