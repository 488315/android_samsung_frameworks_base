package com.android.systemui.statusbar.policy;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.core.os.LocaleListCompatWrapper$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.bluetooth.BluetoothLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.bluetooth.BluetoothRepository;
import com.android.systemui.statusbar.policy.bluetooth.ConnectionStatusModel;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BluetoothControllerImpl implements BluetoothController, BluetoothCallback, CachedBluetoothDevice.Callback, LocalBluetoothProfileManager.ServiceListener {
    public boolean mAudioProfileOnly;
    public final List mConnectedDevices = new ArrayList();
    public int mConnectionState = 0;
    public boolean mEnabled;
    public final FeatureFlags mFeatureFlags;
    public final HandlerC3373H mHandler;
    public boolean mIsActive;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final BluetoothLogger mLogger;
    public int mState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.policy.BluetoothControllerImpl$H */
    public final class HandlerC3373H extends Handler {
        public final ArrayList mCallbacks;

        public HandlerC3373H(Looper looper) {
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

    public BluetoothControllerImpl(Context context, FeatureFlags featureFlags, UserTracker userTracker, DumpManager dumpManager, BluetoothLogger bluetoothLogger, BluetoothRepository bluetoothRepository, Looper looper, LocalBluetoothManager localBluetoothManager, BluetoothAdapter bluetoothAdapter) {
        int i;
        this.mFeatureFlags = featureFlags;
        this.mLogger = bluetoothLogger;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mHandler = new HandlerC3373H(looper);
        if (localBluetoothManager != null) {
            ((CopyOnWriteArrayList) localBluetoothManager.mEventManager.mCallbacks).add(this);
            ((CopyOnWriteArrayList) localBluetoothManager.mProfileManager.mServiceListeners).add(this);
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            synchronized (localBluetoothAdapter) {
                BluetoothAdapter bluetoothAdapter2 = localBluetoothAdapter.mAdapter;
                if (bluetoothAdapter2.getState() != localBluetoothAdapter.mState) {
                    localBluetoothAdapter.setBluetoothStateInt(bluetoothAdapter2.getState());
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
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? LocaleListCompatWrapper$$ExternalSyntheticOutline0.m31m("UNKNOWN(", i, ")") : "DISCONNECTING" : "CONNECTED" : "CONNECTING" : "DISCONNECTED";
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        HandlerC3373H handlerC3373H = this.mHandler;
        handlerC3373H.obtainMessage(3, (BluetoothController.Callback) obj).sendToTarget();
        handlerC3373H.sendEmptyMessage(2);
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
            sb.append(sb2.toString());
            printWriter.println(sb.toString());
        }
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final int getBluetoothState() {
        return this.mState;
    }

    public final Collection getDevices() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            return localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy();
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final boolean isBluetoothEnabled() {
        return this.mEnabled;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        this.mLogger.logAclConnectionStateChanged(cachedBluetoothDevice.getAddress(), connectionStateToString(i));
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        this.mLogger.logActiveDeviceChanged(i, cachedBluetoothDevice == null ? null : cachedBluetoothDevice.getAddress());
        boolean z = false;
        for (CachedBluetoothDevice cachedBluetoothDevice2 : getDevices()) {
            boolean z2 = true;
            if (!cachedBluetoothDevice2.isActiveDevice(1) && !cachedBluetoothDevice2.isActiveDevice(2) && !cachedBluetoothDevice2.isActiveDevice(21) && !cachedBluetoothDevice2.isActiveDevice(22)) {
                z2 = false;
            }
            z |= z2;
        }
        boolean z3 = this.mIsActive;
        HandlerC3373H handlerC3373H = this.mHandler;
        if (z3 != z) {
            this.mIsActive = z;
            handlerC3373H.sendEmptyMessage(2);
        }
        handlerC3373H.sendEmptyMessage(2);
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
    public final void onConnectionStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        this.mLogger.logDeviceConnectionStateChanged(cachedBluetoothDevice == null ? null : cachedBluetoothDevice.getAddress(), connectionStateToString(i));
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    public final void onConnectionStatusFetched(ConnectionStatusModel connectionStatusModel) {
        List list = connectionStatusModel.connectedDevices;
        int i = connectionStatusModel.maxConnectionState;
        synchronized (this.mConnectedDevices) {
            ((ArrayList) this.mConnectedDevices).clear();
            ((ArrayList) this.mConnectedDevices).addAll(list);
        }
        if (i != this.mConnectionState) {
            this.mConnectionState = i;
            this.mHandler.sendEmptyMessage(2);
        }
        boolean z = false;
        boolean z2 = false;
        boolean z3 = false;
        for (CachedBluetoothDevice cachedBluetoothDevice : getDevices()) {
            for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getProfiles()) {
                int profileId = localBluetoothProfile.getProfileId();
                boolean z4 = cachedBluetoothDevice.getProfileConnectionState(localBluetoothProfile) == 2;
                if (profileId == 1 || profileId == 2 || profileId == 21 || profileId == 22) {
                    z2 |= z4;
                } else {
                    z3 |= z4;
                }
            }
        }
        if (z2 && !z3) {
            z = true;
        }
        if (z != this.mAudioProfileOnly) {
            this.mAudioProfileOnly = z;
            this.mHandler.sendEmptyMessage(2);
        }
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mLogger.logDeviceAdded(cachedBluetoothDevice.getAddress());
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
        this.mLogger.logDeviceAttributesChanged();
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(int i, CachedBluetoothDevice cachedBluetoothDevice) {
        this.mLogger.logBondStateChange(i, cachedBluetoothDevice.getAddress());
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mLogger.logDeviceDeleted(cachedBluetoothDevice.getAddress());
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        this.mLogger.logProfileConnectionStateChanged(i2, cachedBluetoothDevice.getAddress(), connectionStateToString(i));
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
        Flags flags = Flags.INSTANCE;
        this.mFeatureFlags.getClass();
        int connectionState = this.mLocalBluetoothManager.mLocalAdapter.mAdapter.getConnectionState();
        ArrayList arrayList = new ArrayList();
        for (CachedBluetoothDevice cachedBluetoothDevice : getDevices()) {
            int maxConnectionState = cachedBluetoothDevice.getMaxConnectionState();
            if (maxConnectionState > connectionState) {
                connectionState = maxConnectionState;
            }
            if (cachedBluetoothDevice.isConnected()) {
                arrayList.add(cachedBluetoothDevice);
            }
        }
        if (arrayList.isEmpty() && connectionState == 2) {
            connectionState = 0;
        }
        onConnectionStatusFetched(new ConnectionStatusModel(connectionState, arrayList));
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {
    }
}
