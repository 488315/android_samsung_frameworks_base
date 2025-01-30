package com.android.settingslib.bluetooth;

import android.app.ActivityOptions;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.nexus.video.VideoPlayer;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BluetoothEventManager {
    public final IntentFilter mAdapterIntentFilter;
    public final BluetoothBroadcastReceiver mBroadcastReceiver;
    public final Context mContext;
    public final DelayedSyncHandler mDelayedSyncHandler;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final Map mHandlerMap;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final BluetoothBroadcastReceiver mPackageBroadcastReceiver;
    public final BluetoothBroadcastReceiver mProfileBroadcastReceiver;
    public final IntentFilter mProfileIntentFilter;
    public LocalBluetoothProfileManager mProfileManager;
    public final android.os.Handler mReceiverHandler;
    public final ArrayList mReceivers;
    public final UserHandle mUserHandle;
    public final Collection mCallbacks = new CopyOnWriteArrayList();
    public final Collection mSemCallbacks = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AclStateChangedHandler implements Handler {
        public /* synthetic */ AclStateChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            if (bluetoothDevice == null) {
                Log.w("BluetoothEventManager", "AclStateChangedHandler: device is null");
                return;
            }
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            if (bluetoothEventManager.mDeviceManager.isSubDevice(bluetoothDevice)) {
                return;
            }
            String action = intent.getAction();
            if (action == null) {
                Log.w("BluetoothEventManager", "AclStateChangedHandler: action is null");
                return;
            }
            CachedBluetoothDevice findDevice = bluetoothEventManager.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                Log.w("BluetoothEventManager", "AclStateChangedHandler: activeDevice is null");
                return;
            }
            if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                i = 2;
            } else {
                if (!action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                    Log.w("BluetoothEventManager", "ActiveDeviceChangedHandler: unknown action ".concat(action));
                    return;
                }
                i = 0;
            }
            Iterator it = ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onAclConnectionStateChanged(i, findDevice);
            }
        }

        private AclStateChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ActiveDeviceChangedHandler implements Handler {
        public /* synthetic */ ActiveDeviceChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            String action = intent.getAction();
            if (action == null) {
                Log.w("BluetoothEventManager", "ActiveDeviceChangedHandler: action is null");
                return;
            }
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            CachedBluetoothDevice findDevice = bluetoothEventManager.mDeviceManager.findDevice(bluetoothDevice);
            if (action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 2;
            } else if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 1;
            } else if (action.equals("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 21;
            } else {
                if (!action.equals("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED")) {
                    Log.w("BluetoothEventManager", "ActiveDeviceChangedHandler: unknown action ".concat(action));
                    return;
                }
                i = 22;
            }
            bluetoothEventManager.dispatchActiveDeviceChanged(findDevice, i);
        }

        private ActiveDeviceChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AdapterStateChangedHandler implements Handler {
        public /* synthetic */ AdapterStateChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", VideoPlayer.MEDIA_ERROR_SYSTEM);
            ListPopupWindow$$ExternalSyntheticOutline0.m10m("AdapterStateChangedHandler :: BluetoothAdapter.ACTION_STATE_CHANGED, state = ", intExtra, "BluetoothEventManager");
            BluetoothEventManager.this.mLocalAdapter.setBluetoothStateInt(intExtra);
            BluetoothUtils.updateDeviceName(context);
            Iterator it = ((CopyOnWriteArrayList) BluetoothEventManager.this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onBluetoothStateChanged(intExtra);
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            if (cachedBluetoothDeviceManager != null) {
                synchronized (cachedBluetoothDeviceManager) {
                    try {
                        if (intExtra == 13) {
                            int size = cachedBluetoothDeviceManager.mCachedDevices.size();
                            while (true) {
                                size--;
                                if (size < 0) {
                                    break;
                                }
                                CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDeviceManager.mCachedDevices.get(size);
                                HashSet<CachedBluetoothDevice> hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
                                if (hashSet.isEmpty()) {
                                    CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                                    if (cachedBluetoothDevice2 != null && cachedBluetoothDevice2.mBondState != 12) {
                                        cachedBluetoothDevice.mSubDevice = null;
                                    }
                                } else {
                                    for (CachedBluetoothDevice cachedBluetoothDevice3 : hashSet) {
                                        if (cachedBluetoothDevice3.mBondState != 12) {
                                            ((HashSet) cachedBluetoothDevice.mMemberDevices).remove(cachedBluetoothDevice3);
                                            cachedBluetoothDevice3.mLeadDevice = null;
                                        }
                                    }
                                }
                                if (cachedBluetoothDevice.mBondState == 12 || cachedBluetoothDevice.mIsRestored) {
                                    cachedBluetoothDevice.clearProfileConnectionState();
                                    CachedBluetoothDevice cachedBluetoothDevice4 = cachedBluetoothDevice.mSubDevice;
                                    if (cachedBluetoothDevice4 != null) {
                                        cachedBluetoothDevice4.clearProfileConnectionState();
                                    }
                                    BluetoothRetryDetector bluetoothRetryDetector = cachedBluetoothDevice.mBondingDetector;
                                    if (bluetoothRetryDetector != null && bluetoothRetryDetector.mIsForRestored) {
                                        bluetoothRetryDetector.mRestoredDeviceList.clear();
                                    }
                                } else {
                                    cachedBluetoothDevice.setJustDiscovered(false);
                                    cachedBluetoothDeviceManager.removeDevice(cachedBluetoothDevice);
                                }
                            }
                            cachedBluetoothDeviceManager.updateSequeces();
                            cachedBluetoothDeviceManager.mOngoingSetMemberPair = null;
                            cachedBluetoothDeviceManager.mOngoingSetMemberGroupId = -1;
                        } else if (intExtra == 11) {
                            int size2 = cachedBluetoothDeviceManager.mCachedDevices.size();
                            while (true) {
                                size2--;
                                if (size2 < 0) {
                                    break;
                                }
                                CachedBluetoothDevice cachedBluetoothDevice5 = cachedBluetoothDeviceManager.mCachedDevices.get(size2);
                                cachedBluetoothDevice5.clearProfileConnectionState();
                                cachedBluetoothDevice5.mErrorMsg = null;
                                CachedBluetoothDevice cachedBluetoothDevice6 = cachedBluetoothDevice5.mSubDevice;
                                if (cachedBluetoothDevice6 != null) {
                                    cachedBluetoothDevice6.clearProfileConnectionState();
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
            if (intExtra == 12) {
                if (BluetoothUtils.mQuickPannelOn) {
                    LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
                    if (localBluetoothManager != null && !localBluetoothManager.semIsForegroundActivity()) {
                        Intent intent2 = new Intent("com.samsung.settings.bluetooth.scandialog.LAUNCH");
                        intent2.setFlags(276824064);
                        try {
                            if (BluetoothUtils.mDexQuickPannelOn) {
                                int i = ((SemDesktopModeManager) BluetoothEventManager.this.mContext.getSystemService("desktopmode")).getDesktopModeState().getDisplayType() == 102 ? 2 : 0;
                                ActivityOptions makeBasic = ActivityOptions.makeBasic();
                                makeBasic.setLaunchDisplayId(i);
                                BluetoothEventManager.this.mContext.startActivityAsUser(intent2, makeBasic.toBundle(), UserHandle.CURRENT);
                            } else {
                                BluetoothEventManager.this.mContext.startActivityAsUser(intent2, UserHandle.CURRENT);
                            }
                        } catch (ActivityNotFoundException e) {
                            Log.e("BluetoothEventManager", "startActivity() failed: " + e);
                        }
                    }
                    BluetoothUtils.setQuickPannelOn(false);
                }
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager2 = BluetoothEventManager.this.mDeviceManager;
                if (cachedBluetoothDeviceManager2 != null) {
                    cachedBluetoothDeviceManager2.mOngoingSetMemberPair = null;
                    cachedBluetoothDeviceManager2.mOngoingSetMemberGroupId = -1;
                }
            }
            if (intExtra == 10) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("bluetooth_restart", 0);
                if (sharedPreferences.getBoolean("key_bluetooth_restart", false)) {
                    BluetoothEventManager.this.mLocalAdapter.mAdapter.enable();
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putBoolean("key_bluetooth_restart", false);
                    edit.apply();
                }
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager3 = BluetoothEventManager.this.mDeviceManager;
                if (cachedBluetoothDeviceManager3 != null) {
                    cachedBluetoothDeviceManager3.mOngoingSetMemberPair = null;
                    cachedBluetoothDeviceManager3.mOngoingSetMemberGroupId = -1;
                }
            }
        }

        private AdapterStateChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppPackageChangedHandler implements Handler {
        public /* synthetic */ AppPackageChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            String action = intent.getAction();
            if (action == null) {
                Log.w("BluetoothEventManager", "AppPackageChangedHandler: action is null");
                return;
            }
            Log.d("BluetoothEventManager", "AppPackageChangedHandler: action = ".concat(action));
            if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
                String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                Log.d("BluetoothEventManager", "AppPackageChangedHandler: package name = " + schemeSpecificPart);
                if ("com.samsung.android.app.watchmanagerstub".equals(schemeSpecificPart) || "com.sec.android.app.applinker".equals(schemeSpecificPart)) {
                    BluetoothEventManager.this.mDeviceManager.setStubInfo(schemeSpecificPart);
                }
            }
        }

        private AppPackageChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AudioModeChangedHandler implements Handler {
        public /* synthetic */ AudioModeChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (intent.getAction() == null) {
                Log.w("BluetoothEventManager", "AudioModeChangedHandler() action is null");
                return;
            }
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            Iterator it = ((ArrayList) bluetoothEventManager.mDeviceManager.getCachedDevicesCopy()).iterator();
            while (it.hasNext()) {
                ((CachedBluetoothDevice) it.next()).dispatchAttributesChanged();
            }
            Iterator it2 = ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).iterator();
            while (it2.hasNext()) {
                ((BluetoothCallback) it2.next()).onAudioModeChanged();
            }
        }

        private AudioModeChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AudioTypeChangedHandler implements Handler {
        public /* synthetic */ AudioTypeChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.refresh();
            }
        }

        private AudioTypeChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BatteryLevelChangedHandler implements Handler {
        public /* synthetic */ BatteryLevelChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.refresh();
            }
        }

        private BatteryLevelChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BluetoothBroadcastReceiver extends BroadcastReceiver {
        public /* synthetic */ BluetoothBroadcastReceiver(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            if (bluetoothEventManager.mLocalAdapter == null || bluetoothEventManager.mDeviceManager == null || bluetoothEventManager.mProfileManager == null) {
                Log.e("BluetoothEventManager", "onReceive :: ignore this broadcast, because BluetoothSettings instance are not created yet");
                return;
            }
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Handler handler = (Handler) ((HashMap) BluetoothEventManager.this.mHandlerMap).get(action);
            if (handler != null) {
                handler.onReceive(context, intent, bluetoothDevice);
            }
        }

        private BluetoothBroadcastReceiver() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class BondStateChangedHandler implements Handler {
        public /* synthetic */ BondStateChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            boolean z;
            LocalBluetoothManager localBluetoothManager;
            LocalBluetoothManager localBluetoothManager2;
            String string;
            LocalBluetoothProfileManager localBluetoothProfileManager;
            String[] strArr;
            int i;
            if (bluetoothDevice == null) {
                Log.e("BluetoothEventManager", "ACTION_BOND_STATE_CHANGED with no EXTRA_DEVICE");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", VideoPlayer.MEDIA_ERROR_SYSTEM);
            int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", VideoPlayer.MEDIA_ERROR_SYSTEM);
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                BluetoothDevice bluetoothDevice2 = cachedBluetoothDeviceManager.mOngoingSetMemberPair;
                if (bluetoothDevice2 != null && bluetoothDevice2.equals(bluetoothDevice)) {
                    if (intExtra != 11) {
                        cachedBluetoothDeviceManager.mOngoingSetMemberPair = null;
                        cachedBluetoothDeviceManager.mOngoingSetMemberGroupId = -1;
                        if (intExtra != 10 && cachedBluetoothDeviceManager.findDevice(bluetoothDevice) == null) {
                            cachedBluetoothDeviceManager.addDevice(new CachedBluetoothDevice(cachedBluetoothDeviceManager.mContext, cachedBluetoothDeviceManager.mBtManager.mProfileManager, bluetoothDevice));
                            cachedBluetoothDeviceManager.findDevice(bluetoothDevice).secConnect();
                        }
                    }
                    z = true;
                } else {
                    z = false;
                }
            }
            if (z) {
                Log.d("BluetoothEventManager", "Should not update UI for the set member");
            }
            int intExtra3 = intent.getIntExtra("android.bluetooth.device.extra.REASON", VideoPlayer.MEDIA_ERROR_SYSTEM);
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                boolean z2 = BluetoothUtils.DEBUG;
                if (z2) {
                    Log.w("BluetoothEventManager", "Got bonding state changed for " + bluetoothDevice + ", but we have no record of that device.");
                }
                if (!BluetoothEventManager.this.readPairedDevices() && z2) {
                    Log.e("BluetoothEventManager", "Got bonding state changed for " + bluetoothDevice + ", but we have no record of that device.");
                }
                findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            }
            if (findDevice != null) {
                Log.d("BluetoothEventManager", "CachedBluetoothDevice was created from paired devices. It will be refreshed.");
                StringBuilder sb = new StringBuilder("onBondingStateChanged :: Device [");
                sb.append(findDevice.getNameForLog());
                sb.append("], bond state change to ");
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(sb, findDevice.mBondState, " -> ", intExtra, "CachedBluetoothDevice");
                int i2 = findDevice.mBondState;
                findDevice.mBondState = intExtra;
                if (intExtra == 10) {
                    findDevice.clearProfileConnectionState();
                    if (i2 == 11) {
                        findDevice.mBondingDetector.getClass();
                        if (findDevice.mIsRestored && findDevice.mIsBondingByCached && (localBluetoothManager = LocalBluetoothManager.getInstance(findDevice.mContext, null)) != null) {
                            BluetoothRetryDetector bluetoothRetryDetector = localBluetoothManager.mRestoredRetryDetector;
                            String str = findDevice.mAddress;
                            if (bluetoothRetryDetector.mIsForRestored) {
                                bluetoothRetryDetector.mRestoredDeviceList.put(str, Integer.valueOf((bluetoothRetryDetector.mRestoredDeviceList.containsKey(str) ? ((Integer) bluetoothRetryDetector.mRestoredDeviceList.get(str)).intValue() : 0) + 1));
                            }
                            Intent intent2 = new Intent("com.samsung.settings.bluetooth.restoredialog.LAUNCH");
                            intent2.setFlags(335544320);
                            intent2.putExtra("cachedAddress", findDevice.mAddress);
                            findDevice.mContext.startActivityAsUser(intent2, UserHandle.CURRENT);
                        }
                    } else if (i2 == 12) {
                        BluetoothRetryDetector bluetoothRetryDetector2 = findDevice.mBondingDetector;
                        if (bluetoothRetryDetector2.mIsForRestored) {
                            bluetoothRetryDetector2.mRestoredDeviceList.clear();
                        }
                        if (BluetoothUtils.isGalaxyWatchDevice(findDevice.mDeviceName, findDevice.mBtClass, findDevice.getManufacturerRawData(), findDevice.mDevice.getUuids())) {
                            findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
                        } else {
                            findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.PAIRING_FAILURE);
                        }
                    }
                    findDevice.mDevice.setPhonebookAccessPermission(0);
                    findDevice.mDevice.setMessageAccessPermission(0);
                    findDevice.mDevice.setSimAccessPermission(0);
                    findDevice.mIsBondingByCached = false;
                } else if (intExtra == 12) {
                    new Timestamp(System.currentTimeMillis());
                    findDevice.mIsBondingByCached = false;
                    if (findDevice.mIsSynced) {
                        findDevice.mIsSynced = false;
                    }
                    if (findDevice.mIsRestored) {
                        findDevice.mIsRestored = false;
                        BluetoothClass bluetoothClass = findDevice.mDevice.getBluetoothClass();
                        BluetoothClass bluetoothClass2 = findDevice.mBtClass;
                        if (bluetoothClass2 != null && bluetoothClass2.getMajorDeviceClass() != 7936 && (bluetoothClass == null || findDevice.mBtClass != bluetoothClass)) {
                            Log.d("CachedBluetoothDevice", "onBondingStateChanged :: COD - " + findDevice.mBtClass);
                            findDevice.mDevice.setBluetoothClass(findDevice.mRestoredDevice.mCod);
                        }
                        if (findDevice.getManufacturerRawData() != null && !Arrays.equals(findDevice.mDevice.semGetManufacturerData(), findDevice.getManufacturerRawData())) {
                            findDevice.mDevice.semSetManufacturerData(findDevice.getManufacturerRawData());
                        }
                        if (!findDevice.mName.equals(findDevice.mDevice.getAlias())) {
                            findDevice.mDevice.setAlias(findDevice.mName);
                        }
                    }
                    BluetoothRetryDetector bluetoothRetryDetector3 = findDevice.mBondingDetector;
                    if (bluetoothRetryDetector3 != null && bluetoothRetryDetector3.mIsForRestored) {
                        bluetoothRetryDetector3.mRestoredDeviceList.clear();
                    }
                    if (BluetoothUtils.isGalaxyWatchDevice(findDevice.mDeviceName, findDevice.mBtClass, findDevice.getManufacturerRawData(), findDevice.mDevice.getUuids())) {
                        findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
                    } else {
                        findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE);
                    }
                }
                synchronized (findDevice.mCallbacks) {
                    Iterator it = ((CopyOnWriteArrayList) findDevice.mCallbacks).iterator();
                    while (it.hasNext()) {
                        ((CachedBluetoothDevice.Callback) it.next()).onDeviceAttributesChanged();
                    }
                }
                synchronized (findDevice.mSemCallbacks) {
                    Iterator it2 = ((ArrayList) findDevice.mSemCallbacks).iterator();
                    if (it2.hasNext()) {
                        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it2.next());
                        throw null;
                    }
                }
                findDevice.refresh();
                if (intExtra == 12 && findDevice.mDevice.isBondingInitiatedLocally() && LocalBluetoothManager.getInstance(findDevice.mContext, null) != null && LocalBluetoothManager.mSystemUiInstance) {
                    findDevice.mConnectAttempted = SystemClock.elapsedRealtime();
                    findDevice.connectDevice();
                }
                if (findDevice.mVisible) {
                    findDevice.refresh();
                    synchronized (BluetoothEventManager.this.mCallbacks) {
                        Iterator it3 = ((CopyOnWriteArrayList) BluetoothEventManager.this.mCallbacks).iterator();
                        while (it3.hasNext()) {
                            ((BluetoothCallback) it3.next()).onDeviceBondStateChanged(intExtra, findDevice);
                        }
                    }
                } else if (intExtra == 10) {
                    BluetoothEventManager.this.mDeviceManager.removeDevice(findDevice);
                }
            } else if (BluetoothUtils.DEBUG) {
                Log.e("BluetoothEventManager", "Got bonding state changed for " + bluetoothDevice + ", but device not added in cache.");
            }
            if (intExtra != 12) {
                if (intExtra != 10 || (localBluetoothManager2 = LocalBluetoothManager.getInstance(context, null)) == null) {
                    return;
                }
                if (findDevice != null) {
                    if (findDevice.mGroupId != -1 || findDevice.getHiSyncId() != 0) {
                        if (LocalBluetoothManager.mSystemUiInstance) {
                            BluetoothEventManager.this.mDeviceManager.onDeviceUnpaired(findDevice);
                        }
                        if (findDevice.mGroupId != -1) {
                            findDevice.setGroupId(-1);
                        } else if (findDevice.getHiSyncId() != 0) {
                            findDevice.mSubDevice = null;
                        }
                    }
                    if (intExtra2 == 12) {
                        BluetoothEventManager.this.mDeviceManager.removeDevice(findDevice);
                    }
                }
                if (localBluetoothManager2.semIsForegroundActivity() || LocalBluetoothManager.mSystemUiInstance || BluetoothEventManager.this.isBlockingDevice(bluetoothDevice)) {
                    return;
                }
                if (context == null) {
                    Log.d("BluetoothEventManager", "showUnbondMessage: context is null");
                    return;
                }
                if (findDevice != null && findDevice.mIsRestored && intExtra3 != 9) {
                    boolean z3 = BluetoothUtils.DEBUG;
                    String str2 = SystemProperties.get("ro.build.characteristics");
                    BluetoothUtils.showToast(context, str2 != null && str2.contains("tablet") ? context.getString(R.string.bluetooth_pairing_fail_restored_tablet) : context.getString(R.string.bluetooth_pairing_fail_restored));
                    return;
                }
                String alias = bluetoothDevice.getAlias();
                if (alias == null) {
                    alias = bluetoothDevice.getAddress();
                }
                switch (intExtra3) {
                    case 1:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        string = context.getString(R.string.bluetooth_pairing_error_message, alias);
                        break;
                    case 2:
                        string = context.getString(R.string.bluetooth_pairing_rejected_error_message, alias);
                        break;
                    case 3:
                    default:
                        IconCompat$$ExternalSyntheticOutline0.m30m("showUnbondMessage: Not displaying any message for reason: ", intExtra3, "BluetoothEventManager");
                        return;
                    case 4:
                        boolean z4 = BluetoothUtils.DEBUG;
                        String name = bluetoothDevice.getName();
                        String address = bluetoothDevice.getAddress();
                        if (!((name == null || address == null || ((!Pattern.matches("(?i).*BMW.*", name) || !Pattern.matches("(?i).*A0:56:B2.*|(?i).*B8:24:10.*|(?i).*9C:DF:03.*|(?i).*00:19:C0.*", address)) && !Pattern.matches("(?i)MINI[0-9].*", name))) ? false : true)) {
                            string = context.getString(R.string.bluetooth_pairing_device_down_error_message, alias);
                            break;
                        } else {
                            String m29m = PathParser$$ExternalSyntheticOutline0.m29m("\u200e", alias, "\u200e");
                            string = context.getString(R.string.bluetooth_pairing_device_down_black_list_error_message, m29m, m29m);
                            break;
                        }
                }
                BluetoothUtils.showToast(context, string);
                return;
            }
            if (LocalBluetoothManager.getInstance(context, null) != null && !LocalBluetoothManager.mSystemUiInstance) {
                BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
                bluetoothEventManager.getClass();
                StringBuilder sb2 = new StringBuilder();
                String replace = bluetoothDevice.getAddress().replace(":", "");
                Context context2 = bluetoothEventManager.mContext;
                int i3 = 0;
                String string2 = context2.getSharedPreferences("bluetooth_blocking_device", 0).getString("blocking_device_list", "");
                if (!string2.equals("")) {
                    String[] split = string2.split(";");
                    if (split != null) {
                        int length = split.length;
                        char c = 0;
                        while (i3 < length) {
                            String[] split2 = split[i3].split(",");
                            if (split2.length == 5) {
                                String str3 = split2[c];
                                String str4 = split2[1];
                                try {
                                    int parseInt = Integer.parseInt(split2[2]);
                                    strArr = split;
                                    try {
                                        int parseInt2 = Integer.parseInt(split2[3]);
                                        int parseInt3 = Integer.parseInt(split2[4]);
                                        i = length;
                                        if (parseInt3 <= 2 && !replace.equals(str3)) {
                                            sb2.append(str3 + "," + str4 + "," + parseInt + "," + parseInt2 + "," + parseInt3 + ";");
                                        }
                                    } catch (NumberFormatException unused) {
                                    }
                                } catch (NumberFormatException unused2) {
                                }
                                i3++;
                                c = 0;
                                split = strArr;
                                length = i;
                            }
                            strArr = split;
                            i = length;
                            i3++;
                            c = 0;
                            split = strArr;
                            length = i;
                        }
                    }
                    String sb3 = sb2.toString();
                    SharedPreferences.Editor edit = context2.getSharedPreferences("bluetooth_blocking_device", 0).edit();
                    edit.putString("blocking_device_list", sb3);
                    edit.commit();
                }
            }
            if (findDevice == null || (localBluetoothProfileManager = BluetoothEventManager.this.mProfileManager) == null || !findDevice.hasProfile(localBluetoothProfileManager.mCsipSetCoordinatorProfile) || findDevice.mGroupId != -1) {
                return;
            }
            BluetoothEventManager.this.mDeviceManager.initCsipDeviceIfNeeded(findDevice);
        }

        private BondStateChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ClassChangedHandler implements Handler {
        public /* synthetic */ ClassChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothClass bluetoothClass;
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null || (bluetoothClass = bluetoothDevice.getBluetoothClass()) == null || bluetoothClass.equals(findDevice.mBtClass)) {
                return;
            }
            BluetoothClass bluetoothClass2 = findDevice.mDevice.getBluetoothClass();
            if (bluetoothClass2 != null) {
                findDevice.setBtClass(bluetoothClass2);
            }
            findDevice.dispatchAttributesChanged();
        }

        private ClassChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ConnectionStateChangedHandler implements Handler {
        public /* synthetic */ ConnectionStateChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            CachedBluetoothDevice findDevice = bluetoothEventManager.mDeviceManager.findDevice(bluetoothDevice);
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", VideoPlayer.MEDIA_ERROR_SYSTEM);
            Iterator it = ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onConnectionStateChanged(intExtra, findDevice);
            }
        }

        private ConnectionStateChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DelayedSyncHandler extends android.os.Handler {
        public /* synthetic */ DelayedSyncHandler(BluetoothEventManager bluetoothEventManager, Looper looper, int i) {
            this(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ArrayList arrayList;
            if (message.what != 1) {
                return;
            }
            LocalBluetoothAdapter localBluetoothAdapter = BluetoothEventManager.this.mLocalAdapter;
            if (localBluetoothAdapter != null && localBluetoothAdapter.mAdapter.isDiscovering()) {
                BluetoothEventManager.this.mLocalAdapter.mAdapter.cancelDiscovery();
            }
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            bluetoothEventManager.getClass();
            boolean z = BluetoothUtils.DEBUG;
            if (z) {
                Log.d("BluetoothEventManager", "readSyncedDevices()");
            }
            List restoredDevices = BluetoothUtils.getRestoredDevices(bluetoothEventManager.mContext, bluetoothEventManager.mProfileManager, true);
            if (restoredDevices == null) {
                if (z) {
                    Log.d("BluetoothEventManager", "readSyncedDevices():: There are no synced devices");
                    return;
                }
                return;
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = bluetoothEventManager.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                synchronized (cachedBluetoothDeviceManager) {
                    arrayList = new ArrayList();
                    for (int size = cachedBluetoothDeviceManager.mCachedDevices.size() - 1; size >= 0; size--) {
                        CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDeviceManager.mCachedDevices.get(size);
                        if (cachedBluetoothDevice.mIsSynced) {
                            arrayList.add(cachedBluetoothDevice);
                            cachedBluetoothDeviceManager.removeDevice(cachedBluetoothDevice);
                        }
                    }
                }
            }
            Iterator it = ((ArrayList) restoredDevices).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice2.mBondState != 12) {
                    int indexOf = arrayList.indexOf(cachedBluetoothDevice2);
                    if (indexOf > -1) {
                        Log.d("CachedBluetoothDeviceManager", "addSyncedDevices :: newDevice is added already - " + cachedBluetoothDevice2.getName());
                        CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) arrayList.get(indexOf);
                        cachedBluetoothDevice2.mErrorMsg = cachedBluetoothDevice3.mErrorMsg;
                        cachedBluetoothDevice2.mBondState = cachedBluetoothDevice3.mBondState;
                        cachedBluetoothDevice2.mIsBondingByCached = cachedBluetoothDevice3.mIsBondingByCached;
                    }
                    if (cachedBluetoothDeviceManager.mCachedDevices.contains(cachedBluetoothDevice2)) {
                        cachedBluetoothDeviceManager.removeDevice(cachedBluetoothDevice2);
                        cachedBluetoothDeviceManager.mBtManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice2);
                    }
                    boolean addDevice = cachedBluetoothDeviceManager.addDevice(cachedBluetoothDevice2);
                    cachedBluetoothDevice2.mSequence = cachedBluetoothDeviceManager.mCachedDevices.indexOf(cachedBluetoothDevice2);
                    if (!addDevice) {
                        cachedBluetoothDeviceManager.mBtManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDevice2);
                    }
                }
            }
        }

        private DelayedSyncHandler(Looper looper) {
            super(looper);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DeviceFoundHandler implements Handler {
        public /* synthetic */ DeviceFoundHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:55:0x0125  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x013e  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0145  */
        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            ManufacturerData manufacturerData;
            short shortExtra = intent.getShortExtra("android.bluetooth.device.extra.RSSI", Short.MIN_VALUE);
            String stringExtra = intent.getStringExtra("android.bluetooth.device.extra.NAME");
            boolean z = false;
            intent.getBooleanExtra("android.bluetooth.extra.IS_COORDINATED_SET_MEMBER", false);
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
                LocalBluetoothManager localBluetoothManager = cachedBluetoothDeviceManager.mBtManager;
                LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
                LocalBluetoothProfileManager localBluetoothProfileManager = localBluetoothManager.mProfileManager;
                synchronized (cachedBluetoothDeviceManager) {
                    try {
                        CachedBluetoothDevice findDevice2 = cachedBluetoothDeviceManager.findDevice(bluetoothDevice);
                        if (findDevice2 == null) {
                            findDevice2 = new CachedBluetoothDevice(cachedBluetoothDeviceManager.mContext, localBluetoothProfileManager, bluetoothDevice, intent);
                            if (localBluetoothAdapter.mAdapter.isCustomDeviceAddress(findDevice2.getAddress())) {
                                findDevice = null;
                            } else {
                                cachedBluetoothDeviceManager.mCsipDeviceManager.initCsipDeviceIfNeeded(findDevice2);
                                cachedBluetoothDeviceManager.mHearingAidDeviceManager.initHearingAidDeviceIfNeeded(findDevice2);
                                cachedBluetoothDeviceManager.mCsipDeviceManager.setMemberDeviceIfNeeded(findDevice2);
                                if (!cachedBluetoothDeviceManager.mHearingAidDeviceManager.setSubDeviceIfNeeded(findDevice2)) {
                                    if (cachedBluetoothDeviceManager.mCachedDevices.contains(findDevice2)) {
                                        Log.d("CachedBluetoothDeviceManager", "addDevice :: newDevice is added already");
                                        findDevice = cachedBluetoothDeviceManager.findDevice(bluetoothDevice);
                                    } else {
                                        boolean addDevice = cachedBluetoothDeviceManager.addDevice(findDevice2);
                                        findDevice2.mSequence = cachedBluetoothDeviceManager.mCachedDevices.indexOf(findDevice2);
                                        if (!addDevice) {
                                            cachedBluetoothDeviceManager.mBtManager.mEventManager.dispatchDeviceAdded(findDevice2);
                                        }
                                    }
                                }
                            }
                        }
                        findDevice = findDevice2;
                    } finally {
                    }
                }
                if (findDevice == null) {
                    return;
                }
                findDevice.setRssi(shortExtra);
                if (BluetoothUtils.DEBUG) {
                    Log.d("BluetoothEventManager", "DeviceFoundHandler created new CachedBluetoothDevice : " + findDevice);
                }
            } else {
                BluetoothClass bluetoothClass = (BluetoothClass) intent.getParcelableExtra("android.bluetooth.device.extra.CLASS");
                short shortExtra2 = intent.getShortExtra("com.samsung.bluetooth.device.extra.APPEARANCE", (short) 0);
                byte[] byteArrayExtra = intent.getByteArrayExtra("com.samsung.bluetooth.device.extra.MANUFACTURER_DATA");
                int bondState = bluetoothDevice.getBondState();
                if (BluetoothUtils.DEBUG) {
                    Log.d("BluetoothEventManager", "DeviceFoundHandler update CachedBluetoothDevice : " + findDevice);
                }
                findDevice.setRssi(shortExtra);
                if (bluetoothClass != null && !bluetoothClass.equals(findDevice.mBtClass)) {
                    findDevice.setBtClass(bluetoothClass);
                    z = true;
                }
                if (findDevice.mAppearance != shortExtra2) {
                    findDevice.mAppearance = shortExtra2;
                    z = true;
                }
                if (!TextUtils.isEmpty(findDevice.mDeviceName)) {
                    if (!TextUtils.isEmpty(stringExtra) && !findDevice.mDeviceName.equals(stringExtra)) {
                        findDevice.mDeviceName = stringExtra;
                    }
                    if (!findDevice.mVisible) {
                        findDevice.mVisible = true;
                    }
                    if (byteArrayExtra != null && ((manufacturerData = findDevice.mManufacturerData) == null || !Arrays.equals(byteArrayExtra, manufacturerData.mManufacturerRawData))) {
                        findDevice.setManufacturerData(byteArrayExtra);
                        z = true;
                    }
                    if (findDevice.mBondState != bondState) {
                        findDevice.mBondState = bondState;
                        z = true;
                    }
                    if (z) {
                        Log.e("CachedBluetoothDevice", "calling dispatchAttributesChanged");
                        findDevice.dispatchAttributesChanged();
                    }
                    Log.d("CachedBluetoothDevice", "processActionFoundEvent :: " + findDevice.describeDetail());
                } else if (TextUtils.isEmpty(stringExtra)) {
                    findDevice.mDeviceName = findDevice.mDevice.getAddress();
                } else {
                    findDevice.mDeviceName = stringExtra;
                }
                z = true;
                if (!findDevice.mVisible) {
                }
                if (byteArrayExtra != null) {
                    findDevice.setManufacturerData(byteArrayExtra);
                    z = true;
                }
                if (findDevice.mBondState != bondState) {
                }
                if (z) {
                }
                Log.d("CachedBluetoothDevice", "processActionFoundEvent :: " + findDevice.describeDetail());
            }
            findDevice.setJustDiscovered(true);
        }

        private DeviceFoundHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DeviceNameChangedHandler implements Handler {
        private DeviceNameChangedHandler() {
        }

        public /* synthetic */ DeviceNameChangedHandler(int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            Log.d("BluetoothEventManager", "DeviceNameChangedHandler :: com.android.settings.DEVICE_NAME_CHANGED");
            BluetoothUtils.updateDeviceName(context);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DeviceRestoredHandler implements Handler {
        public /* synthetic */ DeviceRestoredHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager.this.readRestoredDevices();
        }

        private DeviceRestoredHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DeviceSyncHandler implements Handler {
        public /* synthetic */ DeviceSyncHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            if (bluetoothDevice2 == null) {
                boolean hasMessages = bluetoothEventManager.mDelayedSyncHandler.hasMessages(1);
                DelayedSyncHandler delayedSyncHandler = bluetoothEventManager.mDelayedSyncHandler;
                if (hasMessages) {
                    Log.d("BluetoothEventManager", "DeviceSyncHandler :: remove MESSAGE_SYNC_INTENT");
                    delayedSyncHandler.removeMessages(1);
                }
                Message obtainMessage = delayedSyncHandler.obtainMessage(1);
                obtainMessage.obj = intent;
                delayedSyncHandler.sendMessageDelayed(obtainMessage, 3000L);
                Log.d("BluetoothEventManager", "DeviceSyncHandler :: send MESSAGE_SYNC_INTENT");
                return;
            }
            boolean booleanExtra = intent.getBooleanExtra("com.samsung.android.intent.extra.IS_UPDATE_SYNC_BLUETOOTH", false);
            String stringExtra = intent.getStringExtra("com.samsung.android.intent.extra.UPDATE_DEVICE_NAME_BLUETOOTH");
            CachedBluetoothDevice findDevice = bluetoothEventManager.mDeviceManager.findDevice(bluetoothDevice2);
            if (findDevice == null) {
                Log.e("BluetoothEventManager", "DeviceSyncHandler :: CachedDevice is null");
                return;
            }
            LocalBluetoothAdapter localBluetoothAdapter = bluetoothEventManager.mLocalAdapter;
            if (!booleanExtra) {
                if (localBluetoothAdapter != null && localBluetoothAdapter.mAdapter.isDiscovering()) {
                    localBluetoothAdapter.mAdapter.cancelDiscovery();
                }
                Log.d("BluetoothEventManager", "DeviceSyncHandler :: Sync device will be removed");
                bluetoothEventManager.dispatchDeviceRemoved(findDevice);
                return;
            }
            Log.d("BluetoothEventManager", "DeviceSyncHandler :: Sync device will be updated");
            LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
            if (stringExtra == null || localBluetoothAdapter.mAdapter.getState() != 12 || localBluetoothManager == null || !LocalBluetoothManager.mSystemUiInstance) {
                return;
            }
            findDevice.setName(stringExtra);
        }

        private DeviceSyncHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Handler {
        void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ManufacturerChangedHandler implements Handler {
        public /* synthetic */ ManufacturerChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (bluetoothDevice == null) {
                Log.e("BluetoothEventManager", "ACTION_MANUFACTURER_CHANGED with no EXTRA_DEVICE");
                return;
            }
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            byte[] byteArrayExtra = intent.getByteArrayExtra("com.samsung.bluetooth.device.extra.MANUFACTURER_DATA");
            if (findDevice != null) {
                findDevice.fetchManufacturerData(byteArrayExtra);
            }
        }

        private ManufacturerChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NameChangedHandler implements Handler {
        public /* synthetic */ NameChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice;
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            if (10 == bluetoothEventManager.mLocalAdapter.mAdapter.getLeState()) {
                Log.d("BluetoothEventManager", "NameChangedHandler :: State - " + bluetoothEventManager.mLocalAdapter.mAdapter.getLeState());
                return;
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = bluetoothEventManager.mDeviceManager;
            CachedBluetoothDevice findDevice2 = cachedBluetoothDeviceManager.findDevice(bluetoothDevice);
            LocalBluetoothManager localBluetoothManager = cachedBluetoothDeviceManager.mBtManager;
            LocalBluetoothAdapter localBluetoothAdapter = localBluetoothManager.mLocalAdapter;
            if (findDevice2 != null) {
                if (!findDevice2.mIsRestored) {
                    if (!localBluetoothAdapter.mAdapter.isCustomDeviceAddress(findDevice2.getAddress()) && !cachedBluetoothDeviceManager.isSubDevice(bluetoothDevice)) {
                        String str = findDevice2.mDeviceName;
                        if (str != null && str.equals(bluetoothDevice.getName()) && findDevice2.getName().equals(bluetoothDevice.getAlias())) {
                            Log.d("CachedBluetoothDeviceManager", "onDeviceNameUpdated :: skip same name update");
                        } else {
                            ArrayList arrayList = (ArrayList) cachedBluetoothDeviceManager.mFilteredCachedDevices;
                            if (!arrayList.contains(findDevice2)) {
                                arrayList.add(findDevice2);
                            }
                            findDevice2.refreshName();
                            localBluetoothManager.mEventManager.dispatchDeviceAdded(findDevice2);
                        }
                    }
                }
                if (findDevice2.mGroupId != -1) {
                    findDevice2.refreshName();
                }
            }
            if (LocalBluetoothManager.getInstance(context, null) == null || !LocalBluetoothManager.mSystemUiInstance || (findDevice = cachedBluetoothDeviceManager.findDevice(bluetoothDevice)) == null || findDevice.mGroupId == -1 || findDevice.mLeadDevice != null) {
                return;
            }
            HashSet hashSet = (HashSet) findDevice.mMemberDevices;
            if (hashSet.isEmpty()) {
                return;
            }
            Iterator it = hashSet.iterator();
            while (it.hasNext()) {
                ((CachedBluetoothDevice) it.next()).setName(bluetoothDevice.getAlias());
            }
        }

        private NameChangedHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class NetworkResetSettingsHandler implements Handler {
        public /* synthetic */ NetworkResetSettingsHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (intent.getAction() == null) {
                Log.w("BluetoothEventManager", "NetworkResetSettingsHandler() action is null");
                return;
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            if (cachedBluetoothDeviceManager != null) {
                synchronized (cachedBluetoothDeviceManager) {
                    cachedBluetoothDeviceManager.mCachedDevices.clear();
                    ((ArrayList) cachedBluetoothDeviceManager.mFilteredCachedDevices).clear();
                }
            }
        }

        private NetworkResetSettingsHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PairingCancelHandler implements Handler {
        public /* synthetic */ PairingCancelHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            LocalBluetoothManager localBluetoothManager;
            if (bluetoothDevice == null) {
                Log.e("BluetoothEventManager", "ACTION_PAIRING_CANCEL with no EXTRA_DEVICE");
                return;
            }
            if (context == null || (localBluetoothManager = LocalBluetoothManager.getInstance(context, null)) == null || !localBluetoothManager.semIsForegroundActivity() || LocalBluetoothManager.mSystemUiInstance || BluetoothEventManager.this.isBlockingDevice(bluetoothDevice)) {
                return;
            }
            String alias = bluetoothDevice.getAlias();
            boolean z = BluetoothUtils.DEBUG;
            BluetoothUtils.showToast(context, context.getString(R.string.bluetooth_pairing_error_message, PathParser$$ExternalSyntheticOutline0.m29m("\u200e", alias, "\u200e")));
        }

        private PairingCancelHandler() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ScanningStateChangedHandler implements Handler {
        public final boolean mStarted;

        public ScanningStateChangedHandler(boolean z) {
            this.mStarted = z;
        }

        /* JADX WARN: Code restructure failed: missing block: B:26:0x0047, code lost:
        
            r2 = r0.iterator();
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x004f, code lost:
        
            if (r2.hasNext() == false) goto L41;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x0051, code lost:
        
            ((com.android.settingslib.bluetooth.CachedBluetoothDevice) r2.next()).setJustDiscovered(false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:?, code lost:
        
            return;
         */
        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            Iterator it = ((CopyOnWriteArrayList) BluetoothEventManager.this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onScanningStateChanged(this.mStarted);
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            boolean z = this.mStarted;
            synchronized (cachedBluetoothDeviceManager) {
                if (!z) {
                    return;
                }
                int size = cachedBluetoothDeviceManager.mCachedDevices.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        return;
                    }
                    CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDeviceManager.mCachedDevices.get(size);
                    cachedBluetoothDevice.setJustDiscovered(false);
                    HashSet hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
                    if (!hashSet.isEmpty()) {
                        break;
                    }
                    CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                    if (cachedBluetoothDevice2 != null) {
                        cachedBluetoothDevice2.setJustDiscovered(false);
                    }
                }
            }
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class UuidChangedHandler implements Handler {
        public /* synthetic */ UuidChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:22:0x0066  */
        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            long j;
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                Log.w("BluetoothEventManager", "UuidChangedHandler: cachedDevice is null");
                return;
            }
            Parcelable[] parcelableArrayExtra = intent.getParcelableArrayExtra("android.bluetooth.device.extra.UUID");
            if (parcelableArrayExtra == null || parcelableArrayExtra.length <= 0) {
                return;
            }
            ParcelUuid[] parcelUuidArr = new ParcelUuid[parcelableArrayExtra.length];
            for (int i = 0; i < parcelableArrayExtra.length; i++) {
                parcelUuidArr[i] = (ParcelUuid) parcelableArrayExtra[i];
            }
            findDevice.updateProfiles(parcelUuidArr);
            if (!ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HOGP)) {
                if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HEARING_AID)) {
                    j = 15000;
                } else if (!ArrayUtils.contains(parcelUuidArr, BluetoothUuid.LE_AUDIO)) {
                    j = 5000;
                } else if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.COORDINATED_SET) && findDevice.mGroupId == -1) {
                    j = 120000;
                }
                if (BluetoothUtils.DEBUG) {
                    Log.d("CachedBluetoothDevice", "onUuidChanged: Time since last connect=" + (SystemClock.elapsedRealtime() - findDevice.mConnectAttempted));
                }
                if (findDevice.mConnectAttempted + j > SystemClock.elapsedRealtime() && findDevice.mConnectAttempted != 0) {
                    findDevice.connectDevice();
                }
                findDevice.dispatchAttributesChanged();
            }
            j = 30000;
            if (BluetoothUtils.DEBUG) {
            }
            if (findDevice.mConnectAttempted + j > SystemClock.elapsedRealtime()) {
                findDevice.connectDevice();
            }
            findDevice.dispatchAttributesChanged();
        }

        private UuidChangedHandler() {
        }
    }

    public BluetoothEventManager(LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, Context context, android.os.Handler handler, UserHandle userHandle) {
        int i = 0;
        this.mBroadcastReceiver = new BluetoothBroadcastReceiver(this, i);
        this.mProfileBroadcastReceiver = new BluetoothBroadcastReceiver(this, i);
        ArrayList arrayList = new ArrayList();
        this.mReceivers = arrayList;
        BluetoothBroadcastReceiver bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver(this, i);
        this.mPackageBroadcastReceiver = bluetoothBroadcastReceiver;
        Log.d("BluetoothEventManager", "BluetoothEventManager Constructor :: ");
        IntentFilter intentFilter = new IntentFilter();
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mAdapterIntentFilter = new IntentFilter();
        this.mProfileIntentFilter = new IntentFilter();
        HashMap hashMap = new HashMap();
        this.mHandlerMap = hashMap;
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mReceiverHandler = handler;
        arrayList.clear();
        addHandler("android.bluetooth.adapter.action.STATE_CHANGED", new AdapterStateChangedHandler(this, i));
        addHandler("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED", new ConnectionStateChangedHandler(this, i));
        addHandler("android.bluetooth.adapter.action.DISCOVERY_STARTED", new ScanningStateChangedHandler(true));
        addHandler("android.bluetooth.adapter.action.DISCOVERY_FINISHED", new ScanningStateChangedHandler(false));
        addHandler("android.bluetooth.device.action.FOUND", new DeviceFoundHandler(this, i));
        addHandler("android.bluetooth.device.action.NAME_CHANGED", new NameChangedHandler(this, i));
        addHandler("android.bluetooth.device.action.ALIAS_CHANGED", new NameChangedHandler(this, i));
        addHandler("android.bluetooth.device.action.BOND_STATE_CHANGED", new BondStateChangedHandler(this, i));
        addHandler("android.bluetooth.device.action.PAIRING_CANCEL", new PairingCancelHandler(this, i));
        addHandler("android.bluetooth.device.action.CLASS_CHANGED", new ClassChangedHandler(this, i));
        addHandler("android.bluetooth.device.action.UUID", new UuidChangedHandler(this, i));
        addHandler("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED", new BatteryLevelChangedHandler(this, i));
        addHandler("com.samsung.bluetooth.device.action.MANUFACTURER_CHANGED", new ManufacturerChangedHandler(this, i));
        addHandler("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler(this, i));
        addHandler("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler(this, i));
        addHandler("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler(this, i));
        addHandler("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler(this, i));
        addHandler("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED", new AudioModeChangedHandler(this, i));
        addHandler("android.intent.action.PHONE_STATE", new AudioModeChangedHandler(this, i));
        addHandler("android.bluetooth.device.action.ACL_CONNECTED", new AclStateChangedHandler(this, i));
        addHandler("android.bluetooth.device.action.ACL_DISCONNECTED", new AclStateChangedHandler(this, i));
        addHandler("com.android.settings.DEVICE_NAME_CHANGED", new DeviceNameChangedHandler(i));
        addHandler("com.samsung.android.intent.action.RESPONSE_RESTORE_BLUETOOTH", new DeviceRestoredHandler(this, i));
        addHandler("com.samsung.android.intent.action.NOTIFY_SC_SYNC_BLUETOOTH", new DeviceSyncHandler(this, i));
        this.mDelayedSyncHandler = new DelayedSyncHandler(this, Looper.getMainLooper(), i);
        addHandler("com.samsung.bluetooth.device.action.AUDIO_TYPE_CHANGED", new AudioTypeChangedHandler(this, i));
        addHandler("com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET", new NetworkResetSettingsHandler(this, i));
        hashMap.put("android.intent.action.PACKAGE_ADDED", new AppPackageChangedHandler(this, i));
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        synchronized (arrayList) {
            if (arrayList.contains(bluetoothBroadcastReceiver)) {
                context.unregisterReceiver(bluetoothBroadcastReceiver);
                arrayList.remove(bluetoothBroadcastReceiver);
                Log.e("BluetoothEventManager", "registerPackageIntentReceiver: mPackageBroadcastReceiver was registered already. Receiver will refresh.");
            }
            registerIntentReceiver(intentFilter, bluetoothBroadcastReceiver);
            arrayList.add(bluetoothBroadcastReceiver);
        }
        registerAdapterIntentReceiver();
    }

    public void addHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mAdapterIntentFilter.addAction(str);
    }

    public void addProfileHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mProfileIntentFilter.addAction(str);
    }

    public void dispatchActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        boolean z;
        Iterator it = ((ArrayList) this.mDeviceManager.getCachedDevicesCopy()).iterator();
        while (it.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
            Set set = cachedBluetoothDevice2.mMemberDevices;
            boolean equals = cachedBluetoothDevice2.equals(cachedBluetoothDevice);
            if (!equals) {
                HashSet hashSet = (HashSet) set;
                if (!hashSet.isEmpty()) {
                    Iterator it2 = hashSet.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        equals = Objects.equals((CachedBluetoothDevice) it2.next(), cachedBluetoothDevice);
                        if (equals) {
                            Log.d("BluetoothEventManager", "The active device is the member device " + cachedBluetoothDevice.mDevice.getAnonymizedAddress() + ". change activeDevice as main device " + cachedBluetoothDevice2.mDevice.getAnonymizedAddress());
                            cachedBluetoothDevice = cachedBluetoothDevice2;
                            break;
                        }
                    }
                }
            }
            Log.d("CachedBluetoothDevice", "onActiveDeviceChanged: profile " + BluetoothProfile.getProfileName(i) + ", device " + cachedBluetoothDevice2.mDevice.getAnonymizedAddress() + ", isActive " + equals);
            if (i == 1) {
                z = cachedBluetoothDevice2.mIsActiveDeviceHeadset != equals;
                cachedBluetoothDevice2.mIsActiveDeviceHeadset = equals;
            } else if (i == 2) {
                z = cachedBluetoothDevice2.mIsActiveDeviceA2dp != equals;
                cachedBluetoothDevice2.mIsActiveDeviceA2dp = equals;
            } else if (i == 21) {
                z = cachedBluetoothDevice2.mIsActiveDeviceHearingAid != equals;
                cachedBluetoothDevice2.mIsActiveDeviceHearingAid = equals;
            } else if (i != 22) {
                Log.w("CachedBluetoothDevice", "onActiveDeviceChanged: unknown profile " + i + " isActive " + equals);
                z = false;
            } else {
                z = cachedBluetoothDevice2.mIsActiveDeviceLeAudio != equals;
                cachedBluetoothDevice2.mIsActiveDeviceLeAudio = equals;
            }
            if (z) {
                cachedBluetoothDevice2.dispatchAttributesChanged();
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                if (cachedBluetoothDevice2.mHearingAidInfo != null) {
                    cachedBluetoothDeviceManager.mHearingAidDeviceManager.onActiveDeviceChanged(cachedBluetoothDevice2);
                }
            }
        }
        Iterator it3 = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it3.hasNext()) {
            ((BluetoothCallback) it3.next()).onActiveDeviceChanged(i, cachedBluetoothDevice);
        }
    }

    public final void dispatchDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice.mIsSynced) {
            synchronized (this.mSemCallbacks) {
                Iterator it = ((ArrayList) this.mSemCallbacks).iterator();
                while (it.hasNext()) {
                    ((SemBluetoothCallback) it.next()).getClass();
                }
            }
        }
        Iterator it2 = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it2.hasNext()) {
            ((BluetoothCallback) it2.next()).onDeviceAdded(cachedBluetoothDevice);
        }
    }

    public final void dispatchDeviceRemoved(CachedBluetoothDevice cachedBluetoothDevice) {
        if (BluetoothUtils.DEBUG) {
            Log.d("BluetoothEventManager", "dispatchDeviceRemoved :: cachedDevice - " + cachedBluetoothDevice.getName());
        }
        Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((BluetoothCallback) it.next()).onDeviceDeleted(cachedBluetoothDevice);
        }
    }

    public final boolean isBlockingDevice(BluetoothDevice bluetoothDevice) {
        String replace = bluetoothDevice.getAddress().replace(":", "");
        String string = this.mContext.getSharedPreferences("bluetooth_blocking_device", 0).getString("blocking_device_list", "");
        if (string.equals("")) {
            return false;
        }
        for (String str : string.split(";")) {
            String[] split = str.split(",");
            if (split.length == 5 && split[0].equals(replace)) {
                try {
                    if (Integer.parseInt(split[4]) == 2) {
                        Log.i("BluetoothEventManager", "It's blocked device for pairing");
                        return true;
                    }
                } catch (NumberFormatException unused) {
                    continue;
                }
            }
        }
        return false;
    }

    public final boolean readPairedDevices() {
        Set<BluetoothDevice> bondedDevices = this.mLocalAdapter.mAdapter.getBondedDevices();
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
        Collection cachedDevicesCopy = cachedBluetoothDeviceManager.getCachedDevicesCopy();
        boolean z = false;
        if (bondedDevices == null || bondedDevices.size() == 0) {
            Iterator it = ((ArrayList) cachedDevicesCopy).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice.mBondState == 12) {
                    cachedBluetoothDevice.mBondState = 10;
                }
            }
            return false;
        }
        Iterator it2 = ((ArrayList) cachedDevicesCopy).iterator();
        while (it2.hasNext()) {
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it2.next();
            if (bondedDevices.contains(cachedBluetoothDevice2.mDevice)) {
                cachedBluetoothDevice2.mBondState = cachedBluetoothDevice2.mDevice.getBondState();
            }
        }
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            CachedBluetoothDevice findDevice = cachedBluetoothDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                cachedBluetoothDeviceManager.addDevice(bluetoothDevice);
                if (cachedBluetoothDeviceManager.findDevice(bluetoothDevice) != null) {
                    z = true;
                }
            } else if (findDevice.getName().equals(findDevice.getAddress())) {
                findDevice.fetchName$1();
            }
        }
        return z;
    }

    public final void readRestoredDevices() {
        ArrayList arrayList;
        List restoredDevices = BluetoothUtils.getRestoredDevices(this.mContext, this.mProfileManager, false);
        if (restoredDevices != null) {
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                synchronized (cachedBluetoothDeviceManager) {
                    arrayList = new ArrayList();
                    for (int size = cachedBluetoothDeviceManager.mCachedDevices.size() - 1; size >= 0; size--) {
                        CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDeviceManager.mCachedDevices.get(size);
                        if (cachedBluetoothDevice.mIsRestored) {
                            arrayList.add(cachedBluetoothDevice);
                            cachedBluetoothDeviceManager.removeDevice(cachedBluetoothDevice);
                        }
                    }
                }
            }
            Iterator it = ((ArrayList) restoredDevices).iterator();
            while (it.hasNext()) {
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) it.next();
                if (cachedBluetoothDevice2.mBondState != 12) {
                    int indexOf = arrayList.indexOf(cachedBluetoothDevice2);
                    if (indexOf > -1) {
                        Log.d("CachedBluetoothDeviceManager", "addRestoredDevices :: newDevice is added already");
                        CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) arrayList.get(indexOf);
                        cachedBluetoothDevice2.mErrorMsg = cachedBluetoothDevice3.mErrorMsg;
                        cachedBluetoothDevice2.mBondState = cachedBluetoothDevice3.mBondState;
                        cachedBluetoothDevice2.mIsBondingByCached = cachedBluetoothDevice3.mIsBondingByCached;
                    }
                    BluetoothRetryDetector bluetoothRetryDetector = cachedBluetoothDeviceManager.mBtManager.mRestoredRetryDetector;
                    if (bluetoothRetryDetector != null) {
                        String address = cachedBluetoothDevice2.getAddress();
                        if (bluetoothRetryDetector.mIsForRestored && bluetoothRetryDetector.mRestoredDeviceList.containsKey(address)) {
                            ((Integer) bluetoothRetryDetector.mRestoredDeviceList.get(address)).intValue();
                        }
                        BluetoothRetryDetector bluetoothRetryDetector2 = cachedBluetoothDevice2.mBondingDetector;
                    }
                    cachedBluetoothDeviceManager.addDevice(cachedBluetoothDevice2);
                    cachedBluetoothDevice2.mSequence = cachedBluetoothDeviceManager.mCachedDevices.indexOf(cachedBluetoothDevice2);
                }
            }
        }
    }

    public void registerAdapterIntentReceiver() {
        synchronized (this.mReceivers) {
            if (this.mReceivers.contains(this.mBroadcastReceiver)) {
                this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                this.mReceivers.remove(this.mBroadcastReceiver);
                Log.e("BluetoothEventManager", "registerAdapterIntentReceiver :: mBroadcastReceiver was registered already. Receiver will refresh.");
            }
            registerIntentReceiver(this.mAdapterIntentFilter, this.mBroadcastReceiver);
            this.mReceivers.add(this.mBroadcastReceiver);
        }
    }

    public final void registerIntentReceiver(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
        UserHandle userHandle = this.mUserHandle;
        if (userHandle == null) {
            this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, this.mReceiverHandler, 2);
        } else {
            this.mContext.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, this.mReceiverHandler, 2);
        }
    }

    public void registerProfileIntentReceiver() {
        synchronized (this.mReceivers) {
            if (this.mReceivers.contains(this.mProfileBroadcastReceiver)) {
                this.mContext.unregisterReceiver(this.mProfileBroadcastReceiver);
                this.mReceivers.remove(this.mProfileBroadcastReceiver);
                Log.e("BluetoothEventManager", "registerProfileIntentReceiver :: mProfileConnectionReceiver was registered already. Receiver will refresh.");
            }
            registerIntentReceiver(this.mProfileIntentFilter, this.mProfileBroadcastReceiver);
            this.mReceivers.add(this.mProfileBroadcastReceiver);
        }
    }
}
