package com.android.server.companion.presence;

import android.bluetooth.BluetoothAdapter;
import android.companion.AssociationInfo;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserManager;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.companion.AssociationStore;
import com.android.server.companion.presence.BleCompanionDeviceScanner;
import com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* loaded from: classes.dex */
public class CompanionDevicePresenceMonitor implements AssociationStore.OnChangeListener, BluetoothCompanionDeviceConnectionListener.Callback, BleCompanionDeviceScanner.Callback {
    public final AssociationStore mAssociationStore;
    public final BleCompanionDeviceScanner mBleScanner;
    public final BluetoothCompanionDeviceConnectionListener mBtConnectionListener;
    public final Callback mCallback;
    public final Set mConnectedBtDevices = new HashSet();
    public final Set mNearbyBleDevices = new HashSet();
    public final Set mReportedSelfManagedDevices = new HashSet();
    public final Set mSimulated = new HashSet();
    public final SimulatedDevicePresenceSchedulerHelper mSchedulerHelper = new SimulatedDevicePresenceSchedulerHelper();

    public interface Callback {
        void onDeviceAppeared(int i);

        void onDeviceDisappeared(int i);
    }

    public CompanionDevicePresenceMonitor(UserManager userManager, AssociationStore associationStore, Callback callback) {
        this.mAssociationStore = associationStore;
        this.mCallback = callback;
        this.mBtConnectionListener = new BluetoothCompanionDeviceConnectionListener(userManager, associationStore, this);
        this.mBleScanner = new BleCompanionDeviceScanner(associationStore, this);
    }

    public void init(Context context) {
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        if (defaultAdapter != null) {
            this.mBtConnectionListener.init(defaultAdapter);
            this.mBleScanner.init(context, defaultAdapter);
        } else {
            Log.w("CDM_CompanionDevicePresenceMonitor", "BluetoothAdapter is NOT available.");
        }
        this.mAssociationStore.registerListener(this);
    }

    public boolean isDevicePresent(int i) {
        return this.mReportedSelfManagedDevices.contains(Integer.valueOf(i)) || this.mConnectedBtDevices.contains(Integer.valueOf(i)) || this.mNearbyBleDevices.contains(Integer.valueOf(i)) || this.mSimulated.contains(Integer.valueOf(i));
    }

    public void onSelfManagedDeviceConnected(int i) {
        onDevicePresent(this.mReportedSelfManagedDevices, i, "application-reported");
    }

    public void onSelfManagedDeviceDisconnected(int i) {
        onDeviceGone(this.mReportedSelfManagedDevices, i, "application-reported");
    }

    public void onSelfManagedDeviceReporterBinderDied(int i) {
        onDeviceGone(this.mReportedSelfManagedDevices, i, "application-reported");
    }

    @Override // com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener.Callback
    public void onBluetoothCompanionDeviceConnected(int i) {
        onDevicePresent(this.mConnectedBtDevices, i, "bt");
    }

    @Override // com.android.server.companion.presence.BluetoothCompanionDeviceConnectionListener.Callback
    public void onBluetoothCompanionDeviceDisconnected(int i) {
        if (this.mNearbyBleDevices.remove(Integer.valueOf(i))) {
            Log.d("CDM_CompanionDevicePresenceMonitor", "Bluetooth device disconnect was detected. Pre-emptively marking the BLE device as lost.");
        }
        onDeviceGone(this.mConnectedBtDevices, i, "bt");
    }

    @Override // com.android.server.companion.presence.BleCompanionDeviceScanner.Callback
    public void onBleCompanionDeviceFound(int i) {
        onDevicePresent(this.mNearbyBleDevices, i, "ble");
    }

    @Override // com.android.server.companion.presence.BleCompanionDeviceScanner.Callback
    public void onBleCompanionDeviceLost(int i) {
        onDeviceGone(this.mNearbyBleDevices, i, "ble");
    }

    public void simulateDeviceAppeared(int i) {
        enforceCallerShellOrRoot();
        enforceAssociationExists(i);
        onDevicePresent(this.mSimulated, i, "simulated");
        this.mSchedulerHelper.scheduleOnDeviceGoneCallForSimulatedDevicePresence(i);
    }

    public void simulateDeviceDisappeared(int i) {
        enforceCallerShellOrRoot();
        enforceAssociationExists(i);
        this.mSchedulerHelper.unscheduleOnDeviceGoneCallForSimulatedDevicePresence(i);
        onDeviceGone(this.mSimulated, i, "simulated");
    }

    public final void enforceAssociationExists(int i) {
        if (this.mAssociationStore.getAssociationById(i) != null) {
            return;
        }
        throw new IllegalArgumentException("Association with id " + i + " does not exist.");
    }

    public final void onDevicePresent(Set set, int i, String str) {
        Slog.i("CDM_CompanionDevicePresenceMonitor", "onDevice_Present() id=" + i + ", source=" + str);
        if (isDevicePresent(i)) {
            Slog.i("CDM_CompanionDevicePresenceMonitor", "Deviceid (" + i + ") already present.");
        }
        if (!set.add(Integer.valueOf(i))) {
            Slog.i("CDM_CompanionDevicePresenceMonitor", "Association with id " + i + " is ALREADY reported as present by this source (" + str + ")");
        }
        this.mCallback.onDeviceAppeared(i);
    }

    public final void onDeviceGone(Set set, int i, String str) {
        Slog.i("CDM_CompanionDevicePresenceMonitor", "onDevice_Gone() id=" + i + ", source=" + str);
        if (!set.remove(Integer.valueOf(i))) {
            Slog.w("CDM_CompanionDevicePresenceMonitor", "Association with id " + i + " was NOT reported as present by this source (" + str + ")");
            return;
        }
        if (isDevicePresent(i)) {
            Slog.w("CDM_CompanionDevicePresenceMonitor", "  Device id (" + i + ") is still present.");
        }
        this.mCallback.onDeviceDisappeared(i);
    }

    @Override // com.android.server.companion.AssociationStore.OnChangeListener
    public void onAssociationRemoved(AssociationInfo associationInfo) {
        int id = associationInfo.getId();
        this.mConnectedBtDevices.remove(Integer.valueOf(id));
        this.mNearbyBleDevices.remove(Integer.valueOf(id));
        this.mReportedSelfManagedDevices.remove(Integer.valueOf(id));
        this.mSimulated.remove(Integer.valueOf(id));
    }

    public SparseArray getPendingConnectedDevices() {
        SparseArray sparseArray;
        synchronized (this.mBtConnectionListener.mPendingConnectedDevices) {
            sparseArray = this.mBtConnectionListener.mPendingConnectedDevices;
        }
        return sparseArray;
    }

    public static void enforceCallerShellOrRoot() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != 2000 && callingUid != 0) {
            throw new SecurityException("Caller is neither Shell nor Root");
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.append("Companion Device Present: ");
        if (this.mConnectedBtDevices.isEmpty() && this.mNearbyBleDevices.isEmpty() && this.mReportedSelfManagedDevices.isEmpty()) {
            printWriter.append("<empty>\n");
            return;
        }
        printWriter.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.append("  Connected Bluetooth Devices: ");
        if (this.mConnectedBtDevices.isEmpty()) {
            printWriter.append("<empty>\n");
        } else {
            printWriter.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            Iterator it = this.mConnectedBtDevices.iterator();
            while (it.hasNext()) {
                printWriter.append("    ").append((CharSequence) this.mAssociationStore.getAssociationById(((Integer) it.next()).intValue()).toShortString()).append('\n');
            }
        }
        printWriter.append("  Nearby BLE Devices: ");
        if (this.mNearbyBleDevices.isEmpty()) {
            printWriter.append("<empty>\n");
        } else {
            printWriter.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            Iterator it2 = this.mNearbyBleDevices.iterator();
            while (it2.hasNext()) {
                printWriter.append("    ").append((CharSequence) this.mAssociationStore.getAssociationById(((Integer) it2.next()).intValue()).toShortString()).append('\n');
            }
        }
        printWriter.append("  Self-Reported Devices: ");
        if (this.mReportedSelfManagedDevices.isEmpty()) {
            printWriter.append("<empty>\n");
            return;
        }
        printWriter.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        Iterator it3 = this.mReportedSelfManagedDevices.iterator();
        while (it3.hasNext()) {
            printWriter.append("    ").append((CharSequence) this.mAssociationStore.getAssociationById(((Integer) it3.next()).intValue()).toShortString()).append('\n');
        }
    }

    public class SimulatedDevicePresenceSchedulerHelper extends Handler {
        public SimulatedDevicePresenceSchedulerHelper() {
            super(Looper.getMainLooper());
        }

        public void scheduleOnDeviceGoneCallForSimulatedDevicePresence(int i) {
            if (hasMessages(i)) {
                removeMessages(i);
            }
            sendEmptyMessageDelayed(i, 60000L);
        }

        public void unscheduleOnDeviceGoneCallForSimulatedDevicePresence(int i) {
            removeMessages(i);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (CompanionDevicePresenceMonitor.this.mSimulated.contains(Integer.valueOf(i))) {
                CompanionDevicePresenceMonitor companionDevicePresenceMonitor = CompanionDevicePresenceMonitor.this;
                companionDevicePresenceMonitor.onDeviceGone(companionDevicePresenceMonitor.mSimulated, i, "simulated");
            }
        }
    }
}
