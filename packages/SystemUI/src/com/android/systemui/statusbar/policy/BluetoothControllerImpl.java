package com.android.systemui.statusbar.policy;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.bluetooth.BluetoothLogger;
import com.android.systemui.bluetooth.BluetoothLogger$$ExternalSyntheticLambda0;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.bluetooth.BluetoothRepository;
import com.android.systemui.statusbar.policy.bluetooth.BluetoothRepositoryImpl;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class BluetoothControllerImpl implements BluetoothController, BluetoothCallback, CachedBluetoothDevice.Callback, LocalBluetoothProfileManager.ServiceListener {
    public boolean mAudioProfileOnly;
    public final Executor mBackgroundExecutor;
    public final BluetoothRepository mBluetoothRepository;
    public final List mConnectedDevices = new ArrayList();
    public int mConnectionState = 0;
    public boolean mEnabled;
    public final H mHandler;
    public boolean mIsActive;
    public final LocalBluetoothManager mLocalBluetoothManager;
    public final BluetoothLogger mLogger;
    public int mState;

    public final class H extends Handler {
        public final ArrayList mCallbacks;

        public H(Looper looper) {
            super(looper);
            this.mCallbacks = new ArrayList();
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            int i2 = 0;
            if (i == 1) {
                ArrayList arrayList = this.mCallbacks;
                int size = arrayList.size();
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    ((BluetoothController.Callback) obj).onBluetoothDevicesChanged();
                }
                return;
            }
            if (i != 2) {
                if (i == 3) {
                    this.mCallbacks.add((BluetoothController.Callback) message.obj);
                    return;
                } else {
                    if (i != 4) {
                        return;
                    }
                    this.mCallbacks.remove((BluetoothController.Callback) message.obj);
                    return;
                }
            }
            ArrayList arrayList2 = this.mCallbacks;
            int size2 = arrayList2.size();
            while (i2 < size2) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                ((BluetoothController.Callback) obj2).onBluetoothStateChange(BluetoothControllerImpl.this.mEnabled);
            }
        }
    }

    public BluetoothControllerImpl(Context context, UserTracker userTracker, DumpManager dumpManager, BluetoothLogger bluetoothLogger, BluetoothRepository bluetoothRepository, Executor executor, Looper looper, LocalBluetoothManager localBluetoothManager, BluetoothAdapter bluetoothAdapter) {
        int i;
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
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")") : "DISCONNECTING" : "CONNECTED" : "CONNECTING" : "DISCONNECTED";
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
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        for (CachedBluetoothDevice cachedBluetoothDevice : localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : Collections.EMPTY_LIST) {
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

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final boolean isBluetoothEnabled() {
        return this.mEnabled;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        String address = cachedBluetoothDevice.mDevice.getAddress();
        String strConnectionStateToString = connectionStateToString(i);
        BluetoothLogger bluetoothLogger = this.mLogger;
        bluetoothLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(6);
        LogBuffer logBuffer = bluetoothLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = address;
        logMessageImpl.str2 = strConnectionStateToString;
        logBuffer.commit(logMessageObtain);
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        String address = cachedBluetoothDevice == null ? null : cachedBluetoothDevice.mDevice.getAddress();
        BluetoothLogger bluetoothLogger = this.mLogger;
        bluetoothLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = bluetoothLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = address;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        boolean z = false;
        for (CachedBluetoothDevice cachedBluetoothDevice2 : localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : Collections.EMPTY_LIST) {
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
        String strNameForState = BluetoothAdapter.nameForState(i);
        BluetoothLogger bluetoothLogger = this.mLogger;
        bluetoothLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = bluetoothLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = strNameForState;
        logBuffer.commit(logMessageObtain);
        this.mEnabled = i == 12 || i == 11;
        this.mState = i;
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        String address = cachedBluetoothDevice == null ? null : cachedBluetoothDevice.mDevice.getAddress();
        String strConnectionStateToString = connectionStateToString(i);
        BluetoothLogger bluetoothLogger = this.mLogger;
        bluetoothLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(5);
        LogBuffer logBuffer = bluetoothLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = address;
        logMessageImpl.str2 = strConnectionStateToString;
        logBuffer.commit(logMessageObtain);
        updateConnected();
        this.mHandler.sendEmptyMessage(2);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        String address = cachedBluetoothDevice.mDevice.getAddress();
        BluetoothLogger bluetoothLogger = this.mLogger;
        bluetoothLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = bluetoothLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = address;
        logBuffer.commit(logMessageObtain);
        cachedBluetoothDevice.registerCallback(this);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        BluetoothLogger bluetoothLogger = this.mLogger;
        bluetoothLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(4);
        LogBuffer logBuffer = bluetoothLogger.logBuffer;
        logBuffer.commit(logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null));
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        String address = cachedBluetoothDevice.mDevice.getAddress();
        BluetoothLogger bluetoothLogger = this.mLogger;
        bluetoothLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(7);
        LogBuffer logBuffer = bluetoothLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = address;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
        updateConnected();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        String address = cachedBluetoothDevice.mDevice.getAddress();
        BluetoothLogger bluetoothLogger = this.mLogger;
        bluetoothLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(2);
        LogBuffer logBuffer = bluetoothLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).str1 = address;
        logBuffer.commit(logMessageObtain);
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
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        ((BluetoothRepositoryImpl) this.mBluetoothRepository).fetchConnectionStatusInBackground(localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : Collections.EMPTY_LIST, new BluetoothControllerImpl$$ExternalSyntheticLambda0(this));
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {
    }
}
