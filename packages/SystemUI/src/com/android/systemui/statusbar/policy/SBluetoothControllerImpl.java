package com.android.systemui.statusbar.policy;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHearingAid;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.os.UserManager;
import android.sec.enterprise.auditlog.AuditLog;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.BluetoothEventManager;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothAdapter;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.QpRune;
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
import com.android.systemui.statusbar.policy.SBluetoothController;
import com.android.systemui.util.DesktopManager;
import com.samsung.android.bluetooth.SemBluetoothCastAdapter;
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
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SBluetoothControllerImpl implements SBluetoothController, BluetoothCallback, CachedBluetoothDevice.Callback, LocalBluetoothProfileManager.ServiceListener, BluetoothCastCallback {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.policy.SBluetoothControllerImpl$1, reason: invalid class name */
    public class AnonymousClass1 implements BluetoothDesktopCallback {
        public AnonymousClass1() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface BluetoothDesktopCallback {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int i2 = 0;
            switch (i) {
                case 1:
                    sBluetoothControllerImpl.mHandler.removeMessages(1);
                    Log.d("SBluetoothControllerImpl", " firePairedDevicesChanged ");
                    Log.d("SBluetoothControllerImpl", "firePairedDevicesChanged");
                    ArrayList arrayList = this.mCallbacks;
                    int size = arrayList.size();
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((SBluetoothController.SCallback) obj).onBluetoothDevicesChanged();
                    }
                    break;
                case 2:
                    ArrayList arrayList2 = this.mCallbacks;
                    int size2 = arrayList2.size();
                    while (i2 < size2) {
                        Object obj2 = arrayList2.get(i2);
                        i2++;
                        ((SBluetoothController.SCallback) obj2).onBluetoothStateChange(sBluetoothControllerImpl.mEnabled);
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
                    ArrayList arrayList3 = this.mCallbacks;
                    int size3 = arrayList3.size();
                    while (i2 < size3) {
                        Object obj3 = arrayList3.get(i2);
                        i2++;
                        ((SBluetoothController.SCallback) obj3).onBluetoothScanStateChanged(booleanValue);
                    }
                    break;
                case 6:
                    Log.d("SBluetoothControllerImpl", "fireMusicShareStateChanged((CachedBluetoothCastDevice) msg.obj)");
                    ArrayList arrayList4 = this.mCallbacks;
                    int size4 = arrayList4.size();
                    while (i2 < size4) {
                        Object obj4 = arrayList4.get(i2);
                        i2++;
                        ((SBluetoothController.SCallback) obj4).onMusicShareStateChanged();
                    }
                    break;
                case 7:
                    boolean booleanValue2 = ((Boolean) message.obj).booleanValue();
                    ArrayList arrayList5 = this.mCallbacks;
                    int size5 = arrayList5.size();
                    while (i2 < size5) {
                        Object obj5 = arrayList5.get(i2);
                        i2++;
                        ((SBluetoothController.SCallback) obj5).onMusicShareDiscoveryStateChanged(booleanValue2);
                    }
                    break;
            }
        }
    }

    public SBluetoothControllerImpl(Context context, UserTracker userTracker, DumpManager dumpManager, BluetoothLogger bluetoothLogger, Looper looper, LocalBluetoothManager localBluetoothManager, BluetoothAdapter bluetoothAdapter, DesktopManager desktopManager) {
        int i;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mDesktopBluetoothCallback = anonymousClass1;
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
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")") : "DISCONNECTING" : "CONNECTED" : "CONNECTING" : "DISCONNECTED";
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
        List connectedDeviceList = localBluetoothAdapter.mAdapter.getConnectedDeviceList();
        if (connectedDeviceList != null) {
            new ArrayList();
            final LeAudioProfile leAudioProfile = localBluetoothAdapter.mProfileManager.mLeAudioProfile;
            if (leAudioProfile != null) {
                final int i = 0;
                connectedDeviceList = LocalBluetoothAdapter.getFilteredGroupList(connectedDeviceList, -1L, new Function() { // from class: com.android.settingslib.bluetooth.LocalBluetoothAdapter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        int i2 = i;
                        LocalBluetoothProfile localBluetoothProfile = leAudioProfile;
                        switch (i2) {
                            case 0:
                                BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                                return Long.valueOf(((LeAudioProfile) localBluetoothProfile).mService == null ? -1 : r1.getGroupId(bluetoothDevice));
                            default:
                                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) obj;
                                BluetoothHearingAid bluetoothHearingAid = ((HearingAidProfile) localBluetoothProfile).mService;
                                return Long.valueOf((bluetoothHearingAid == null || bluetoothDevice2 == null) ? 0L : bluetoothHearingAid.getHiSyncId(bluetoothDevice2));
                        }
                    }
                });
            }
            final HearingAidProfile hearingAidProfile = localBluetoothAdapter.mProfileManager.mHearingAidProfile;
            if (hearingAidProfile != null) {
                final int i2 = 1;
                return LocalBluetoothAdapter.getFilteredGroupList(connectedDeviceList, 0L, new Function() { // from class: com.android.settingslib.bluetooth.LocalBluetoothAdapter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        int i22 = i2;
                        LocalBluetoothProfile localBluetoothProfile = hearingAidProfile;
                        switch (i22) {
                            case 0:
                                BluetoothDevice bluetoothDevice = (BluetoothDevice) obj;
                                return Long.valueOf(((LeAudioProfile) localBluetoothProfile).mService == null ? -1 : r1.getGroupId(bluetoothDevice));
                            default:
                                BluetoothDevice bluetoothDevice2 = (BluetoothDevice) obj;
                                BluetoothHearingAid bluetoothHearingAid = ((HearingAidProfile) localBluetoothProfile).mService;
                                return Long.valueOf((bluetoothHearingAid == null || bluetoothDevice2 == null) ? 0L : bluetoothHearingAid.getHiSyncId(bluetoothDevice2));
                        }
                    }
                });
            }
        }
        return connectedDeviceList;
    }

    public final Collection getDevices$1() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        Collection cachedDevicesCopy = localBluetoothManager != null ? localBluetoothManager.mCachedDeviceManager.getCachedDevicesCopy() : null;
        return cachedDevicesCopy != null ? (Collection) cachedDevicesCopy.stream().filter(new Predicate() { // from class: com.android.systemui.statusbar.policy.SBluetoothControllerImpl$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return !BluetoothUtils.isExclusivelyManagedBluetoothDevice(SBluetoothControllerImpl.this.mContext, ((CachedBluetoothDevice) obj).mDevice);
            }
        }).collect(Collectors.toCollection(new SBluetoothControllerImpl$$ExternalSyntheticLambda2())) : cachedDevicesCopy;
    }

    public final String getLastDeviceName() {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            List connectedDeviceList = localBluetoothManager.mLocalAdapter.mAdapter.getConnectedDeviceList();
            CachedBluetoothDevice findDevice = localBluetoothManager.mCachedDeviceManager.findDevice((connectedDeviceList == null || connectedDeviceList.size() <= 0) ? null : (BluetoothDevice) connectedDeviceList.get(0));
            if (findDevice != null) {
                StringBuilder sb = new StringBuilder();
                String str = findDevice.mPrefixName;
                if (str == null) {
                    str = "";
                }
                sb.append(str);
                sb.append(findDevice.getName());
                return sb.toString();
            }
        }
        return null;
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final boolean isBluetoothEnabled() {
        return this.mEnabled;
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAclConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            String address = cachedBluetoothDevice.mDevice.getAddress();
            String connectionStateToString = connectionStateToString(i);
            LogLevel logLevel = LogLevel.DEBUG;
            BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(6);
            LogBuffer logBuffer = bluetoothLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = address;
            logMessageImpl.str2 = connectionStateToString;
            logBuffer.commit(obtain);
        }
        StringBuilder sb = new StringBuilder("ACLConnectionStateChanged=");
        sb.append(cachedBluetoothDevice.mDevice.getAddress());
        sb.append(" ");
        sb.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "UNKNOWN(", ")") : "DISCONNECTING" : "CONNECTED" : "CONNECTING" : "DISCONNECTED");
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
            String address = cachedBluetoothDevice.mDevice.getAddress();
            LogLevel logLevel = LogLevel.DEBUG;
            BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(1);
            LogBuffer logBuffer = bluetoothLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = address;
            logMessageImpl.int1 = i;
            logBuffer.commit(obtain);
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
            String nameForState = BluetoothAdapter.nameForState(i);
            LogLevel logLevel = LogLevel.DEBUG;
            BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(3);
            LogBuffer logBuffer = bluetoothLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = nameForState;
            logBuffer.commit(obtain);
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
            String address = cachedBluetoothDevice.mDevice.getAddress();
            LogLevel logLevel = LogLevel.DEBUG;
            BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(0);
            LogBuffer logBuffer = bluetoothLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = address;
            logBuffer.commit(obtain);
        }
        cachedBluetoothDevice.registerCallback(this);
        updateConnected$1();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
    public final void onDeviceAttributesChanged() {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            LogLevel logLevel = LogLevel.DEBUG;
            BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(4);
            LogBuffer logBuffer = bluetoothLogger.logBuffer;
            logBuffer.commit(logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null));
        }
        updateConnected$1();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceBondStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            String address = cachedBluetoothDevice.mDevice.getAddress();
            LogLevel logLevel = LogLevel.DEBUG;
            BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(7);
            LogBuffer logBuffer = bluetoothLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = address;
            logMessageImpl.int1 = i;
            logBuffer.commit(obtain);
        }
        updateConnected$1();
        this.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onDeviceDeleted(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothLogger bluetoothLogger = this.mLogger;
        if (bluetoothLogger != null) {
            String address = cachedBluetoothDevice.mDevice.getAddress();
            LogLevel logLevel = LogLevel.DEBUG;
            BluetoothLogger$$ExternalSyntheticLambda0 bluetoothLogger$$ExternalSyntheticLambda0 = new BluetoothLogger$$ExternalSyntheticLambda0(2);
            LogBuffer logBuffer = bluetoothLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain("BluetoothLog", logLevel, bluetoothLogger$$ExternalSyntheticLambda0, null);
            ((LogMessageImpl) obtain).str1 = address;
            logBuffer.commit(obtain);
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
        EmergencyButtonController$$ExternalSyntheticOutline0.m("scan = ", "SBluetoothControllerImpl", z);
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
        KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("scanMusicShareDevices = ", ", detailListening = ", "SBluetoothControllerImpl", z, z2);
        if (z) {
            localBluetoothManager.mLocalCastAdapter.startDiscovery();
            return;
        }
        if (!z2) {
            localBluetoothManager.mLocalCastAdapter.cancelDiscovery();
            return;
        }
        LocalBluetoothCastAdapter localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter;
        SemBluetoothCastAdapter semBluetoothCastAdapter = localBluetoothCastAdapter.mCastAdapter;
        String str = localBluetoothCastAdapter.TAG;
        if (semBluetoothCastAdapter == null) {
            Log.d(str, "Cannot suspendDiscovery");
            return;
        }
        semBluetoothCastAdapter.suspendDiscovery();
        Log.d(str, "suspendDiscovery");
        localBluetoothCastAdapter.mAlarmManager.cancel(localBluetoothCastAdapter.mDiscoveryAlarmListener);
    }

    @Override // com.android.systemui.statusbar.policy.BluetoothController
    public final void setBluetoothEnabled(boolean z) {
        LocalBluetoothManager localBluetoothManager = this.mLocalBluetoothManager;
        if (localBluetoothManager != null) {
            localBluetoothManager.mLocalAdapter.setBluetoothEnabled(z);
            AuditLog.logEvent(z ? 23 : 24, new Object[0]);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setScanMode(int r9) {
        /*
            r8 = this;
            java.lang.String r1 = "CursorWindowAllocationException"
            java.lang.String r0 = "content://com.sec.knox.provider/BluetoothPolicy"
            android.net.Uri r3 = android.net.Uri.parse(r0)
            android.content.Context r0 = r8.mContext
            android.content.ContentResolver r2 = r0.getContentResolver()
            r6 = 0
            r7 = 0
            r4 = 0
            java.lang.String r5 = "isDiscoverableEnabled"
            android.database.Cursor r2 = r2.query(r3, r4, r5, r6, r7)
            r3 = 0
            if (r2 == 0) goto L4e
            r2.moveToFirst()     // Catch: java.lang.Throwable -> L31 android.database.CursorWindowAllocationException -> L34
            java.lang.String r0 = "isDiscoverableEnabled"
            int r0 = r2.getColumnIndex(r0)     // Catch: java.lang.Throwable -> L31 android.database.CursorWindowAllocationException -> L34
            java.lang.String r0 = r2.getString(r0)     // Catch: java.lang.Throwable -> L31 android.database.CursorWindowAllocationException -> L34
            java.lang.String r4 = "false"
            boolean r0 = r0.equals(r4)     // Catch: java.lang.Throwable -> L31 android.database.CursorWindowAllocationException -> L34
            r2.close()
            goto L4f
        L31:
            r0 = move-exception
            r8 = r0
            goto L4a
        L34:
            r0 = move-exception
            java.lang.String r4 = "SBluetoothControllerImpl"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L31
            r5.<init>(r1)     // Catch: java.lang.Throwable -> L31
            r5.append(r0)     // Catch: java.lang.Throwable -> L31
            java.lang.String r0 = r5.toString()     // Catch: java.lang.Throwable -> L31
            android.util.Log.e(r4, r0)     // Catch: java.lang.Throwable -> L31
            r2.close()
            goto L4e
        L4a:
            r2.close()
            throw r8
        L4e:
            r0 = r3
        L4f:
            r1 = 23
            if (r9 != r1) goto L65
            if (r0 == 0) goto L5d
            r8 = 70
            java.lang.Object[] r9 = new java.lang.Object[r3]
            android.sec.enterprise.auditlog.AuditLog.logEvent(r8, r9)
            return
        L5d:
            r0 = 26
            java.lang.Object[] r2 = new java.lang.Object[r3]
            android.sec.enterprise.auditlog.AuditLog.logEvent(r0, r2)
            goto L6e
        L65:
            if (r0 != 0) goto L6e
            r0 = 27
            java.lang.Object[] r2 = new java.lang.Object[r3]
            android.sec.enterprise.auditlog.AuditLog.logEvent(r0, r2)
        L6e:
            if (r9 == r1) goto L74
            r0 = 21
            if (r9 != r0) goto L7d
        L74:
            com.android.settingslib.bluetooth.LocalBluetoothManager r8 = r8.mLocalBluetoothManager
            com.android.settingslib.bluetooth.LocalBluetoothAdapter r8 = r8.mLocalAdapter
            android.bluetooth.BluetoothAdapter r8 = r8.mAdapter
            r8.setScanMode(r9)
        L7d:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SBluetoothControllerImpl.setScanMode(int):void");
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
            AuditLog.logEvent(z ? 23 : 24, new Object[0]);
        }
    }

    @Override // com.samsung.android.settingslib.bluetooth.bluetoothcast.BluetoothCastCallback
    public final void onCastDiscoveryStateChanged(boolean z) {
    }

    @Override // com.android.settingslib.bluetooth.BluetoothCallback
    public final void onAudioModeChanged() {
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfileManager.ServiceListener
    public final void onServiceDisconnected() {
    }
}
