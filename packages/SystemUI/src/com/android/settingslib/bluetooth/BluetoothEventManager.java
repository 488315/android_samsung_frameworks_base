package com.android.settingslib.bluetooth;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothUuid;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.ArraySet;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentManager$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.SBluetoothControllerImpl;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda2;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BluetoothEventManager {
    public final IntentFilter mAdapterIntentFilter;
    public final BluetoothBroadcastReceiver mBroadcastReceiver;
    public final LocalBluetoothManager mBtManager;
    public final Collection mCallbacks = new CopyOnWriteArrayList();
    public final Context mContext;
    public final DelayedSyncHandler mDelayedSyncHandler;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final Map mHandlerMap;
    public final AnonymousClass1 mIconBroadcastReceiver;
    public final boolean mIsWorkProfile;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final BluetoothBroadcastReceiver mPackageBroadcastReceiver;
    public final BluetoothBroadcastReceiver mProfileBroadcastReceiver;
    public final IntentFilter mProfileIntentFilter;
    public LocalBluetoothProfileManager mProfileManager;
    public final android.os.Handler mReceiverHandler;
    public final ArrayList mReceivers;
    public final Collection mSemCallbacks;
    public final UserHandle mUserHandle;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AclStateChangedHandler implements Handler {
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
                ((BluetoothCallback) it.next()).onAclConnectionStateChanged(findDevice, i);
            }
        }

        private AclStateChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ActiveDeviceChangedHandler implements Handler {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AdapterStateChangedHandler implements Handler {
        public /* synthetic */ AdapterStateChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            ListPopupWindow$$ExternalSyntheticOutline0.m(intExtra, "AdapterStateChangedHandler :: BluetoothAdapter.ACTION_STATE_CHANGED, state = ", "BluetoothEventManager");
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
                            for (int size = cachedBluetoothDeviceManager.mCachedDevices.size() - 1; size >= 0; size--) {
                                CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDeviceManager.mCachedDevices.get(size);
                                if (cachedBluetoothDevice.mBondState == 12) {
                                    cachedBluetoothDevice.mCachedMaxConnectionState = 0;
                                }
                                HashSet hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
                                if (hashSet.isEmpty()) {
                                    CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                                    if (cachedBluetoothDevice2 != null && cachedBluetoothDevice2.mBondState != 12) {
                                        cachedBluetoothDevice.setSubDevice(null);
                                    }
                                } else {
                                    Iterator it2 = hashSet.iterator();
                                    while (it2.hasNext()) {
                                        CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) it2.next();
                                        if (cachedBluetoothDevice3.mBondState != 12) {
                                            ((HashSet) cachedBluetoothDevice.mMemberDevices).remove(cachedBluetoothDevice3);
                                            cachedBluetoothDevice3.mLeadDevice = null;
                                        }
                                    }
                                }
                                if (cachedBluetoothDevice.mBondState == 12 || cachedBluetoothDevice.mIsRestored) {
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
                        } else if (intExtra == 11) {
                            for (int size2 = cachedBluetoothDeviceManager.mCachedDevices.size() - 1; size2 >= 0; size2--) {
                                CachedBluetoothDevice cachedBluetoothDevice4 = cachedBluetoothDeviceManager.mCachedDevices.get(size2);
                                if (cachedBluetoothDevice4.mBondState == 12) {
                                    cachedBluetoothDevice4.updateMaxConnectionState();
                                }
                                cachedBluetoothDevice4.mErrorMsg = null;
                            }
                        }
                    } finally {
                    }
                }
            }
            if (intExtra == 12) {
                if (BluetoothUtils.mQuickPannelOn) {
                    LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(context, null);
                    if (localBluetoothManager != null) {
                        boolean semIsForegroundActivity = localBluetoothManager.semIsForegroundActivity();
                        try {
                            if (semIsForegroundActivity) {
                                if (!TextUtils.isEmpty(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG)) {
                                    Iterator<ActivityManager.RunningTaskInfo> it3 = ((ActivityManager) context.getApplicationContext().getSystemService(ActivityManager.class)).getRunningTasks(50).iterator();
                                    while (it3.hasNext()) {
                                        if (TextUtils.equals(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, it3.next().baseActivity.getPackageName())) {
                                        }
                                    }
                                }
                                synchronized (localBluetoothManager) {
                                    try {
                                        if (localBluetoothManager.mForegroundActivity != null) {
                                            Log.d("LocalBluetoothManager", "setting foreground activity to null");
                                            localBluetoothManager.mForegroundActivity = null;
                                        }
                                        int i = LocalBluetoothManager.mForegroundCount;
                                        if (i > 0) {
                                            LocalBluetoothManager.mForegroundCount = i - 1;
                                        } else {
                                            Log.d("LocalBluetoothManager", "setForegroundActivity :: mForegroundCount is smaller than 0 = ");
                                        }
                                        Log.d("LocalBluetoothManager", "setForegroundActivity :: mForegroundCount = " + LocalBluetoothManager.mForegroundCount);
                                        Settings.Secure.putIntForUser(localBluetoothManager.mContext.getContentResolver(), "bluetooth_settings_foreground", LocalBluetoothManager.mForegroundCount, -2);
                                    } finally {
                                    }
                                }
                                Intent intent2 = new Intent("com.samsung.settings.bluetooth.scandialog.LAUNCH");
                                intent2.setFlags(276824064);
                                boolean z = BluetoothUtils.DEBUG;
                                BluetoothEventManager.this.mContext.startActivityAsUser(intent2, UserHandle.CURRENT);
                            }
                            boolean z2 = BluetoothUtils.DEBUG;
                            BluetoothEventManager.this.mContext.startActivityAsUser(intent2, UserHandle.CURRENT);
                        } catch (ActivityNotFoundException e) {
                            Log.e("BluetoothEventManager", "startActivity() failed: " + e);
                        }
                        if (semIsForegroundActivity) {
                            return;
                        }
                        Intent intent22 = new Intent("com.samsung.settings.bluetooth.scandialog.LAUNCH");
                        intent22.setFlags(276824064);
                    }
                    BluetoothUtils.setQuickPannelOn(false);
                }
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager2 = BluetoothEventManager.this.mDeviceManager;
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
            }
        }

        private AdapterStateChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AppPackageChangedHandler implements Handler {
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
                if ("com.samsung.android.app.watchmanagerstub".equals(schemeSpecificPart)) {
                    BluetoothEventManager.this.mDeviceManager.setStubInfo(schemeSpecificPart);
                }
            }
        }

        private AppPackageChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AudioModeChangedHandler implements Handler {
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
            ArrayList arrayList = (ArrayList) bluetoothEventManager.mDeviceManager.getCachedDevicesCopy();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((CachedBluetoothDevice) obj).dispatchAttributesChanged$1();
            }
            Iterator it = ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onAudioModeChanged();
            }
        }

        private AudioModeChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AudioTypeChangedHandler implements Handler {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AutoOnStateChangedHandler implements Handler {
        public /* synthetic */ AutoOnStateChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (intent.getAction() == null) {
                Log.w("BluetoothEventManager", "AutoOnStateChangedHandler() action is null");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.extra.AUTO_ON_STATE", Integer.MIN_VALUE);
            Iterator it = ((CopyOnWriteArrayList) BluetoothEventManager.this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onAutoOnStateChanged(intExtra);
            }
        }

        private AutoOnStateChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BatteryLevelChangedHandler implements Handler {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BluetoothBroadcastReceiver extends BroadcastReceiver {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class BondStateChangedHandler implements Handler {
        public /* synthetic */ BondStateChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            LocalBluetoothManager localBluetoothManager;
            LocalBluetoothManager localBluetoothManager2;
            String string;
            LocalBluetoothProfileManager localBluetoothProfileManager;
            int i;
            String[] strArr;
            if (bluetoothDevice == null) {
                Log.e("BluetoothEventManager", "ACTION_BOND_STATE_CHANGED with no EXTRA_DEVICE");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
            int intExtra2 = intent.getIntExtra("android.bluetooth.device.extra.PREVIOUS_BOND_STATE", Integer.MIN_VALUE);
            synchronized (BluetoothEventManager.this.mDeviceManager) {
            }
            int intExtra3 = intent.getIntExtra("android.bluetooth.device.extra.REASON", Integer.MIN_VALUE);
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                boolean z = BluetoothUtils.DEBUG;
                if (z) {
                    Log.w("BluetoothEventManager", "Got bonding state changed for " + bluetoothDevice + ", but we have no record of that device.");
                }
                BluetoothEventManager.this.readRestoredDevices();
                if (!BluetoothEventManager.this.readPairedDevices() && z) {
                    Log.e("BluetoothEventManager", "Got bonding state changed for " + bluetoothDevice + ", but we have no record of that device.");
                }
                findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            }
            int i2 = 0;
            if (findDevice != null) {
                Log.d("BluetoothEventManager", "CachedBluetoothDevice was created from paired devices. It will be refreshed.");
                StringBuilder sb = new StringBuilder("onBondingStateChanged :: Device [");
                sb.append(findDevice.getNameForLog());
                sb.append("], bond state change to ");
                KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(sb, findDevice.mBondState, " -> ", intExtra, "CachedBluetoothDevice");
                int i3 = findDevice.mBondState;
                findDevice.mBondState = intExtra;
                if (intExtra == 10) {
                    synchronized (findDevice.mProfileLock) {
                        findDevice.mProfiles.clear();
                    }
                    if (i3 == 11) {
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
                    } else if (i3 == 12) {
                        BluetoothRetryDetector bluetoothRetryDetector2 = findDevice.mBondingDetector;
                        if (bluetoothRetryDetector2.mIsForRestored) {
                            bluetoothRetryDetector2.mRestoredDeviceList.clear();
                        }
                        if (findDevice.isRing()) {
                            findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING);
                        } else if (BluetoothUtils.isGalaxyWatchDevice(findDevice.mDeviceName, findDevice.mBtClass, findDevice.getManufacturerRawData(), findDevice.mDevice.getUuids())) {
                            findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
                        } else {
                            findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.PAIRING_FAILURE);
                        }
                    }
                    findDevice.mDevice.setPhonebookAccessPermission(0);
                    findDevice.mDevice.setMessageAccessPermission(0);
                    findDevice.mDevice.setSimAccessPermission(0);
                    findDevice.mBondTimestamp = null;
                    findDevice.mIsBondingByCached = false;
                } else if (intExtra == 12) {
                    findDevice.mBondTimestamp = new Timestamp(System.currentTimeMillis());
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
                    if (findDevice.isRing()) {
                        findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING);
                    } else if (BluetoothUtils.isGalaxyWatchDevice(findDevice.mDeviceName, findDevice.mBtClass, findDevice.getManufacturerRawData(), findDevice.mDevice.getUuids())) {
                        findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
                    } else {
                        findDevice.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE);
                    }
                }
                Iterator it = ((CopyOnWriteArrayList) findDevice.mCallbacks).iterator();
                while (it.hasNext()) {
                    ((CachedBluetoothDevice.Callback) it.next()).onDeviceAttributesChanged();
                }
                Iterator it2 = ((ArrayList) findDevice.mSemCallbacks).iterator();
                if (it2.hasNext()) {
                    throw FragmentManager$$ExternalSyntheticOutline0.m(it2);
                }
                findDevice.refresh();
                if (intExtra == 12 && findDevice.mDevice.isBondingInitiatedLocally()) {
                    if (LocalBluetoothManager.getInstance(findDevice.mContext, null) != null && LocalBluetoothManager.mSystemUiInstance) {
                        findDevice.mConnectAttempted = SystemClock.elapsedRealtime();
                        findDevice.connectDevice();
                    }
                    ((HashSet) HearingAidStatsLogUtils.sJustBondedDeviceAddressSet).add(findDevice.mDevice.getAddress());
                }
                if (findDevice.mVisible) {
                    if (intExtra == 12 && BluetoothEventManager.this.mDeviceManager.findFilteredDevice(bluetoothDevice) == null) {
                        BluetoothEventManager.this.mDeviceManager.removeDevice(findDevice);
                        BluetoothEventManager.this.mDeviceManager.addDevice(findDevice);
                    }
                    findDevice.refresh();
                    Iterator it3 = ((CopyOnWriteArrayList) BluetoothEventManager.this.mCallbacks).iterator();
                    while (it3.hasNext()) {
                        ((BluetoothCallback) it3.next()).onDeviceBondStateChanged(findDevice, intExtra);
                    }
                } else if (intExtra == 10) {
                    BluetoothEventManager.this.mDeviceManager.removeDevice(findDevice);
                }
            } else if (BluetoothUtils.DEBUG) {
                Log.e("BluetoothEventManager", "Got bonding state changed for " + bluetoothDevice + ", but device not added in cache.");
            }
            char c = 2;
            int i4 = 5;
            if (intExtra == 12) {
                if (LocalBluetoothManager.getInstance(context, null) != null && !LocalBluetoothManager.mSystemUiInstance) {
                    BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
                    bluetoothEventManager.getClass();
                    StringBuilder sb2 = new StringBuilder();
                    String replace = bluetoothDevice.getAddress().replace(":", "");
                    String string2 = bluetoothEventManager.mContext.getSharedPreferences("bluetooth_blocking_device", 0).getString("blocking_device_list", "");
                    if (!string2.equals("")) {
                        String[] split = string2.split(";");
                        if (split != null) {
                            int length = split.length;
                            int i5 = 0;
                            while (i5 < length) {
                                String[] split2 = split[i5].split(",");
                                int i6 = i2;
                                if (split2.length == i4) {
                                    String str2 = split2[i6];
                                    String str3 = split2[1];
                                    try {
                                        int parseInt = Integer.parseInt(split2[c]);
                                        i = i5;
                                        try {
                                            int parseInt2 = Integer.parseInt(split2[3]);
                                            int parseInt3 = Integer.parseInt(split2[4]);
                                            strArr = split;
                                            if (parseInt3 <= 2 && !replace.equals(str2)) {
                                                sb2.append(str2 + "," + str3 + "," + parseInt + "," + parseInt2 + "," + parseInt3 + ";");
                                            }
                                        } catch (NumberFormatException unused) {
                                        }
                                    } catch (NumberFormatException unused2) {
                                    }
                                    i5 = i + 1;
                                    split = strArr;
                                    i2 = i6;
                                    c = 2;
                                    i4 = 5;
                                }
                                i = i5;
                                strArr = split;
                                i5 = i + 1;
                                split = strArr;
                                i2 = i6;
                                c = 2;
                                i4 = 5;
                            }
                        }
                        String sb3 = sb2.toString();
                        SharedPreferences.Editor edit = bluetoothEventManager.mContext.getSharedPreferences("bluetooth_blocking_device", i2).edit();
                        edit.putString("blocking_device_list", sb3);
                        edit.commit();
                    }
                }
                if (findDevice == null || (localBluetoothProfileManager = BluetoothEventManager.this.mProfileManager) == null || !findDevice.hasProfile(localBluetoothProfileManager.mCsipSetCoordinatorProfile) || findDevice.mGroupId != -1) {
                    return;
                }
                CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
                synchronized (cachedBluetoothDeviceManager) {
                    cachedBluetoothDeviceManager.mCsipDeviceManager.initCsipDeviceIfNeeded(findDevice);
                }
                return;
            }
            if (intExtra != 10 || (localBluetoothManager2 = LocalBluetoothManager.getInstance(context, null)) == null) {
                return;
            }
            if (findDevice != null) {
                if (findDevice.mGroupId != -1 || findDevice.getHiSyncId() != 0) {
                    if (LocalBluetoothManager.mSystemUiInstance) {
                        CachedBluetoothDeviceManager cachedBluetoothDeviceManager2 = BluetoothEventManager.this.mDeviceManager;
                        synchronized (cachedBluetoothDeviceManager2) {
                            try {
                                cachedBluetoothDeviceManager2.mHearingAidDeviceManager.clearLocalDataIfNeeded(findDevice);
                                findDevice.setGroupId(-1);
                                CachedBluetoothDevice findMainDevice = cachedBluetoothDeviceManager2.mCsipDeviceManager.findMainDevice(findDevice);
                                HashSet hashSet = new HashSet(findDevice.mMemberDevices);
                                if (!hashSet.isEmpty()) {
                                    Iterator it4 = hashSet.iterator();
                                    while (it4.hasNext()) {
                                        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it4.next();
                                        cachedBluetoothDevice.unpair();
                                        cachedBluetoothDevice.setGroupId(-1);
                                        ((HashSet) findDevice.mMemberDevices).remove(cachedBluetoothDevice);
                                        cachedBluetoothDevice.mLeadDevice = null;
                                    }
                                } else if (findMainDevice != null) {
                                    findMainDevice.unpair();
                                }
                                CachedBluetoothDevice findMainDevice2 = cachedBluetoothDeviceManager2.mHearingAidDeviceManager.findMainDevice(findDevice);
                                CachedBluetoothDevice cachedBluetoothDevice2 = findDevice.mSubDevice;
                                if (cachedBluetoothDevice2 != null) {
                                    if (cachedBluetoothDevice2.mIsRestored) {
                                        cachedBluetoothDeviceManager2.removeRestoredDevice(cachedBluetoothDevice2);
                                    } else {
                                        cachedBluetoothDevice2.unpairLegacy();
                                    }
                                    findDevice.setSubDevice(null);
                                } else if (findMainDevice2 != null) {
                                    if (findMainDevice2.mIsRestored) {
                                        cachedBluetoothDeviceManager2.removeRestoredDevice(findMainDevice2);
                                    } else {
                                        findMainDevice2.unpairLegacy();
                                    }
                                    findMainDevice2.setSubDevice(null);
                                }
                                if (findDevice.isHearingAidDevice()) {
                                    cachedBluetoothDeviceManager2.mHearingAidDeviceManager.notifyDevicesConnectionStatusChanged();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
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
                if (LocalBluetoothManager.mSystemUiInstance && findDevice.mIsRestored && bluetoothDevice.semGetManufacturerData() != null) {
                    bluetoothDevice.semSetManufacturerData(bluetoothDevice.semGetManufacturerData());
                }
            }
            if (localBluetoothManager2.semIsForegroundActivity() || LocalBluetoothManager.mSystemUiInstance) {
                return;
            }
            BluetoothEventManager bluetoothEventManager2 = BluetoothEventManager.this;
            bluetoothEventManager2.getClass();
            String replace2 = bluetoothDevice.getAddress().replace(":", "");
            String string3 = bluetoothEventManager2.mContext.getSharedPreferences("bluetooth_blocking_device", 0).getString("blocking_device_list", "");
            if (!string3.equals("")) {
                for (String str4 : string3.split(";")) {
                    String[] split3 = str4.split(",");
                    if (split3.length == 5) {
                        if (split3[0].equals(replace2)) {
                            try {
                                if (Integer.parseInt(split3[4]) == 2) {
                                    Log.i("BluetoothEventManager", "It's blocked device for pairing");
                                    return;
                                }
                            } catch (NumberFormatException unused3) {
                            }
                        }
                    }
                }
            }
            if (context == null) {
                Log.d("BluetoothEventManager", "showUnbondMessage: context is null");
                return;
            }
            if (findDevice != null && findDevice.mIsRestored && intExtra3 != 9) {
                boolean z2 = BluetoothUtils.DEBUG;
                String str5 = SystemProperties.get("ro.build.characteristics");
                BluetoothUtils.showToast(context, (str5 == null || !str5.contains("tablet")) ? context.getString(R.string.bluetooth_pairing_fail_restored) : context.getString(R.string.bluetooth_pairing_fail_restored_tablet));
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
                    RecordingInputConnection$$ExternalSyntheticOutline0.m(intExtra3, "showUnbondMessage: Not displaying any message for reason: ", "BluetoothEventManager");
                    return;
                case 4:
                    boolean z3 = BluetoothUtils.DEBUG;
                    String name = bluetoothDevice.getName();
                    String address = bluetoothDevice.getAddress();
                    if (name != null && address != null && ((Pattern.matches("(?i).*BMW.*", name) && Pattern.matches("(?i).*A0:56:B2.*|(?i).*B8:24:10.*|(?i).*9C:DF:03.*|(?i).*00:19:C0.*", address)) || Pattern.matches("(?i)MINI[0-9].*", name))) {
                        String m = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("\u200e", alias, "\u200e");
                        string = context.getString(R.string.bluetooth_pairing_device_down_black_list_error_message, m, m);
                        break;
                    } else {
                        string = context.getString(R.string.bluetooth_pairing_device_down_error_message, alias);
                        break;
                    }
            }
            BluetoothUtils.showToast(context, string);
        }

        private BondStateChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ClassChangedHandler implements Handler {
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
            findDevice.dispatchAttributesChanged$1();
        }

        private ClassChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ConnectionStateChangedHandler implements Handler {
        public /* synthetic */ ConnectionStateChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            CachedBluetoothDevice findDevice = bluetoothEventManager.mDeviceManager.findDevice(bluetoothDevice);
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", Integer.MIN_VALUE);
            Iterator it = ((CopyOnWriteArrayList) bluetoothEventManager.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onConnectionStateChanged(findDevice, intExtra);
            }
        }

        private ConnectionStateChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DelayedSyncHandler extends android.os.Handler {
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
                try {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
            ArrayList arrayList2 = (ArrayList) restoredDevices;
            int size2 = arrayList2.size();
            int i = 0;
            while (i < size2) {
                Object obj = arrayList2.get(i);
                i++;
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DeviceFoundHandler implements Handler {
        public /* synthetic */ DeviceFoundHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        /* JADX WARN: Removed duplicated region for block: B:55:0x0127  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x0140  */
        /* JADX WARN: Removed duplicated region for block: B:67:0x0147  */
        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r9, android.content.Intent r10, android.bluetooth.BluetoothDevice r11) {
            /*
                Method dump skipped, instructions count: 360
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.BluetoothEventManager.DeviceFoundHandler.onReceive(android.content.Context, android.content.Intent, android.bluetooth.BluetoothDevice):void");
        }

        private DeviceFoundHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DeviceNameChangedHandler implements Handler {
        public /* synthetic */ DeviceNameChangedHandler(int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            Log.d("BluetoothEventManager", "DeviceNameChangedHandler :: com.android.settings.DEVICE_NAME_CHANGED");
            BluetoothUtils.updateDeviceName(context);
        }

        private DeviceNameChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DeviceRestoredHandler implements Handler {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DeviceSyncHandler implements Handler {
        public /* synthetic */ DeviceSyncHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothDevice bluetoothDevice2 = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            BluetoothEventManager bluetoothEventManager = BluetoothEventManager.this;
            if (bluetoothDevice2 == null) {
                if (bluetoothEventManager.mDelayedSyncHandler.hasMessages(1)) {
                    Log.d("BluetoothEventManager", "DeviceSyncHandler :: remove MESSAGE_SYNC_INTENT");
                    bluetoothEventManager.mDelayedSyncHandler.removeMessages(1);
                }
                Message obtainMessage = bluetoothEventManager.mDelayedSyncHandler.obtainMessage(1);
                obtainMessage.obj = intent;
                bluetoothEventManager.mDelayedSyncHandler.sendMessageDelayed(obtainMessage, 3000L);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class DualModeChangedHandler implements Handler {
        public /* synthetic */ DualModeChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (intent.getAction() == null) {
                Log.w("BluetoothEventManager", "DualModeChangedHandler: action is null");
                return;
            }
            ArrayList arrayList = (ArrayList) BluetoothEventManager.this.mDeviceManager.getCachedDevicesCopy();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((CachedBluetoothDevice) obj).dispatchAttributesChanged$1();
            }
        }

        private DualModeChangedHandler() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Handler {
        void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ManufacturerChangedHandler implements Handler {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class NameChangedHandler implements Handler {
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
                    if (!localBluetoothAdapter.mAdapter.isCustomDeviceAddress(findDevice2.mDevice.getAddress()) && !cachedBluetoothDeviceManager.isSubDevice(bluetoothDevice)) {
                        String str = findDevice2.mDeviceName;
                        if (str != null && str.equals(bluetoothDevice.getName()) && findDevice2.getName().equals(bluetoothDevice.getAlias())) {
                            Log.d("CachedBluetoothDeviceManager", "onDeviceNameUpdated :: skip same name update");
                        } else {
                            if (!((ArrayList) cachedBluetoothDeviceManager.mFilteredCachedDevices).contains(findDevice2)) {
                                ((ArrayList) cachedBluetoothDeviceManager.mFilteredCachedDevices).add(findDevice2);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class NetworkResetSettingsHandler implements Handler {
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class ScanningStateChangedHandler implements Handler {
        public final boolean mStarted;

        public ScanningStateChangedHandler(boolean z) {
            this.mStarted = z;
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            Iterator it = ((CopyOnWriteArrayList) BluetoothEventManager.this.mCallbacks).iterator();
            while (it.hasNext()) {
                ((BluetoothCallback) it.next()).onScanningStateChanged(this.mStarted);
            }
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = BluetoothEventManager.this.mDeviceManager;
            boolean z = this.mStarted;
            synchronized (cachedBluetoothDeviceManager) {
                if (z) {
                    for (int size = cachedBluetoothDeviceManager.mCachedDevices.size() - 1; size >= 0; size--) {
                        CachedBluetoothDevice cachedBluetoothDevice = cachedBluetoothDeviceManager.mCachedDevices.get(size);
                        cachedBluetoothDevice.setJustDiscovered(false);
                        HashSet hashSet = (HashSet) cachedBluetoothDevice.mMemberDevices;
                        if (!hashSet.isEmpty()) {
                            Iterator it2 = hashSet.iterator();
                            while (it2.hasNext()) {
                                ((CachedBluetoothDevice) it2.next()).setJustDiscovered(false);
                            }
                            return;
                        } else {
                            CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice;
                            if (cachedBluetoothDevice2 != null) {
                                cachedBluetoothDevice2.setJustDiscovered(false);
                            }
                        }
                    }
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class UuidChangedHandler implements Handler {
        public /* synthetic */ UuidChangedHandler(BluetoothEventManager bluetoothEventManager, int i) {
            this();
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public final void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
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
            long j = 30000;
            if (!ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HOGP)) {
                if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HEARING_AID)) {
                    j = 15000;
                } else if (!ArrayUtils.contains(parcelUuidArr, BluetoothUuid.LE_AUDIO)) {
                    j = 5000;
                } else if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.COORDINATED_SET) && findDevice.mGroupId == -1) {
                    j = 120000;
                }
            }
            if (BluetoothUtils.DEBUG) {
                long j2 = findDevice.mConnectAttempted;
                if (j2 == -1) {
                    j2 = 0;
                }
                Log.d("CachedBluetoothDevice", "onUuidChanged: Time since last connect/manual disconnect=" + (SystemClock.elapsedRealtime() - j2));
            }
            long j3 = findDevice.mConnectAttempted;
            if (j3 != -1 && j3 + j > SystemClock.elapsedRealtime()) {
                Log.d("CachedBluetoothDevice", "onUuidChanged: triggering connectDevice");
                findDevice.connectDevice();
            }
            findDevice.dispatchAttributesChanged$1();
        }

        private UuidChangedHandler() {
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.content.BroadcastReceiver, com.android.settingslib.bluetooth.BluetoothEventManager$1, java.lang.Object] */
    public BluetoothEventManager(LocalBluetoothAdapter localBluetoothAdapter, LocalBluetoothManager localBluetoothManager, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, final Context context, android.os.Handler handler, UserHandle userHandle) {
        boolean z = false;
        z = false;
        this.mBroadcastReceiver = new BluetoothBroadcastReceiver(this, z ? 1 : 0);
        this.mProfileBroadcastReceiver = new BluetoothBroadcastReceiver(this, z ? 1 : 0);
        this.mIsWorkProfile = false;
        final ArrayList arrayList = new ArrayList();
        this.mSemCallbacks = arrayList;
        ArrayList arrayList2 = new ArrayList();
        this.mReceivers = arrayList2;
        BluetoothBroadcastReceiver bluetoothBroadcastReceiver = new BluetoothBroadcastReceiver(this, z ? 1 : 0);
        this.mPackageBroadcastReceiver = bluetoothBroadcastReceiver;
        ?? r4 = new BroadcastReceiver() { // from class: com.android.settingslib.bluetooth.BluetoothEventManager.1
            @Override // android.content.BroadcastReceiver
            public final void onReceive(final Context context2, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    Log.w("BluetoothEventManager", "onReceive : action is null");
                    return;
                }
                Log.i("BluetoothEventManager", "onReceive : action ".concat(action));
                if (action.equals("com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL")) {
                    ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("com.samsung.bluetooth.adapter.extra.RESOURCE_ALL_URI");
                    if (parcelableArrayListExtra != null) {
                        Log.d("BluetoothEventManager", "BluetoothAdapter.SEM_EXTRA_RESOURCE_ALL_URI: " + parcelableArrayListExtra.toString());
                        Collection collection = BluetoothEventManager.this.mSemCallbacks;
                        String str = ScspUtils.FILE_PATH_ROOT;
                        Executors.newSingleThreadExecutor().execute(new ScspUtils$$ExternalSyntheticLambda2(parcelableArrayListExtra, context2, collection, new android.os.Handler(Looper.getMainLooper())));
                        return;
                    }
                    return;
                }
                if (action.equals("com.samsung.bluetooth.device.action.RESOURCE_UPDATE")) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
                    final ArrayList parcelableArrayListExtra2 = intent.getParcelableArrayListExtra("com.samsung.bluetooth.device.extra.RESOURCE_URI");
                    if (parcelableArrayListExtra2 != null) {
                        Log.d("BluetoothEventManager", "BluetoothDevice.SEM_ACTION_RESOURCE_UPDATE: " + parcelableArrayListExtra2.toString());
                    }
                    if (bluetoothDevice != null) {
                        CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
                        if (findDevice == null) {
                            Log.i("BluetoothEventManager", "onReceive : cachedDevice is null");
                            return;
                        }
                        final String resourcePath = findDevice.getResourcePath(true);
                        if (resourcePath == null) {
                            Log.i("BluetoothEventManager", "onReceive : path is null");
                            return;
                        }
                        final Collection collection2 = BluetoothEventManager.this.mSemCallbacks;
                        String str2 = ScspUtils.FILE_PATH_ROOT;
                        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
                        final android.os.Handler handler2 = new android.os.Handler(Looper.getMainLooper());
                        newSingleThreadExecutor.execute(new Runnable() { // from class: com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                ArrayList arrayList3 = parcelableArrayListExtra2;
                                Context context3 = context2;
                                String str3 = resourcePath;
                                Collection collection3 = collection2;
                                Handler handler3 = handler2;
                                String str4 = ScspUtils.FILE_PATH_ROOT;
                                if (arrayList3 == null || arrayList3.size() <= 0) {
                                    Log.d("ScspUtils", "saveResource: uri is null");
                                    return;
                                }
                                int size = arrayList3.size();
                                boolean z2 = false;
                                int i = 0;
                                while (i < size) {
                                    Object obj = arrayList3.get(i);
                                    i++;
                                    Uri uri = (Uri) obj;
                                    Log.d("ScspUtils", "saveResource: uri = " + uri.toString());
                                    z2 = ScspUtils.saveFileFromUri(context3, uri, str3);
                                    EmergencyButtonController$$ExternalSyntheticOutline0.m("saveResource: success = ", "ScspUtils", z2);
                                }
                                if (!z2 || collection3 == null) {
                                    return;
                                }
                                handler3.post(new ScspUtils$$ExternalSyntheticLambda1(collection3, 1));
                            }
                        });
                    }
                }
            }
        };
        this.mIconBroadcastReceiver = r4;
        Log.d("BluetoothEventManager", "BluetoothEventManager Constructor :: ");
        IntentFilter intentFilter = new IntentFilter();
        IntentFilter intentFilter2 = new IntentFilter();
        this.mLocalAdapter = localBluetoothAdapter;
        this.mBtManager = localBluetoothManager;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mAdapterIntentFilter = new IntentFilter();
        this.mProfileIntentFilter = new IntentFilter();
        HashMap hashMap = new HashMap();
        this.mHandlerMap = hashMap;
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mReceiverHandler = handler;
        arrayList2.clear();
        addHandler("android.bluetooth.adapter.action.STATE_CHANGED", new AdapterStateChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED", new ConnectionStateChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.adapter.action.DISCOVERY_STARTED", new ScanningStateChangedHandler(true));
        addHandler("android.bluetooth.adapter.action.DISCOVERY_FINISHED", new ScanningStateChangedHandler(false));
        addHandler("android.bluetooth.device.action.FOUND", new DeviceFoundHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.device.action.NAME_CHANGED", new NameChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.device.action.ALIAS_CHANGED", new NameChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.device.action.BOND_STATE_CHANGED", new BondStateChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.device.action.CLASS_CHANGED", new ClassChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.device.action.UUID", new UuidChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED", new BatteryLevelChangedHandler(this, z ? 1 : 0));
        addHandler("com.samsung.bluetooth.device.action.MANUFACTURER_CHANGED", new ManufacturerChangedHandler(this, z ? 1 : 0));
        addHandler("com.samsung.bluetooth.a2dp.intent.action.DUAL_PLAY_MODE_ENABLED", new DualModeChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED", new AudioModeChangedHandler(this, z ? 1 : 0));
        addHandler("android.intent.action.PHONE_STATE", new AudioModeChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.device.action.ACL_CONNECTED", new AclStateChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.device.action.ACL_DISCONNECTED", new AclStateChangedHandler(this, z ? 1 : 0));
        addHandler("android.bluetooth.action.AUTO_ON_STATE_CHANGED", new AutoOnStateChangedHandler(this, z ? 1 : 0));
        addHandler("com.android.settings.DEVICE_NAME_CHANGED", new DeviceNameChangedHandler(z ? 1 : 0));
        addHandler("com.samsung.android.intent.action.RESPONSE_RESTORE_BLUETOOTH", new DeviceRestoredHandler(this, z ? 1 : 0));
        addHandler("com.samsung.android.intent.action.NOTIFY_SC_SYNC_BLUETOOTH", new DeviceSyncHandler(this, z ? 1 : 0));
        this.mDelayedSyncHandler = new DelayedSyncHandler(this, Looper.getMainLooper(), z ? 1 : 0);
        addHandler("com.samsung.bluetooth.device.action.AUDIO_TYPE_CHANGED", new AudioTypeChangedHandler(this, z ? 1 : 0));
        addHandler("com.samsung.intent.action.SETTINGS_WIFI_BLUETOOTH_RESET", new NetworkResetSettingsHandler(this, z ? 1 : 0));
        hashMap.put("android.intent.action.PACKAGE_ADDED", new AppPackageChangedHandler(this, z ? 1 : 0));
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        synchronized (arrayList2) {
            try {
                if (arrayList2.contains(bluetoothBroadcastReceiver)) {
                    context.unregisterReceiver(bluetoothBroadcastReceiver);
                    arrayList2.remove(bluetoothBroadcastReceiver);
                    Log.e("BluetoothEventManager", "registerPackageIntentReceiver: mPackageBroadcastReceiver was registered already. Receiver will refresh.");
                }
                registerIntentReceiver(intentFilter, bluetoothBroadcastReceiver);
                arrayList2.add(bluetoothBroadcastReceiver);
            } finally {
            }
        }
        intentFilter2.addAction("com.samsung.bluetooth.device.action.RESOURCE_UPDATE");
        intentFilter2.addAction("com.samsung.bluetooth.adapter.action.RESOURCE_UPDATE_ALL");
        synchronized (arrayList2) {
            try {
                if (arrayList2.contains(r4)) {
                    context.unregisterReceiver(r4);
                    arrayList2.remove((Object) r4);
                    Log.e("BluetoothEventManager", "registerIconIntentReceiver: mIconBroadcastReceiver was registered already. Receiver will refresh.");
                }
                registerIntentReceiver(intentFilter2, r4);
                arrayList2.add(r4);
            } finally {
            }
        }
        String str = ScspUtils.FILE_PATH_ROOT;
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        final android.os.Handler handler2 = new android.os.Handler(Looper.getMainLooper());
        newSingleThreadExecutor.execute(new Runnable() { // from class: com.samsung.android.settingslib.bluetooth.scsp.ScspUtils$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Context context2 = context;
                Collection collection = arrayList;
                Handler handler3 = handler2;
                ScspUtils.removeOldDir(context2);
                BluetoothAdapter adapter = ((BluetoothManager) context2.getSystemService(BluetoothManager.class)).getAdapter();
                if (adapter == null) {
                    return;
                }
                String semGetEtag = adapter.semGetEtag(context2.getPackageName(), null);
                if (TextUtils.isEmpty(semGetEtag)) {
                    Log.d("ScspUtils", "init: etag is empty");
                    return;
                }
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("init: etag = ", semGetEtag, "ScspUtils");
                boolean z2 = false;
                SharedPreferences sharedPreferences = context2.getSharedPreferences("bluetooth_scsp_manager", 0);
                String string = sharedPreferences.getString("etag", "");
                Log.d("ScspUtils", "init: sharedEtag = " + string);
                if (!semGetEtag.equals(string)) {
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("etag", semGetEtag);
                    edit.apply();
                } else {
                    if (new File(ScspUtils.getFileRootPath(context2)).exists()) {
                        Log.d("ScspUtils", "init: etag is not updated");
                        return;
                    }
                    Log.d("ScspUtils", "init: dir is not exist. need update");
                }
                List<Uri> semGetAllIconResourceUri = adapter.semGetAllIconResourceUri(context2.getPackageName());
                if (semGetAllIconResourceUri == null || semGetAllIconResourceUri.size() == 0) {
                    Log.d("ScspUtils", "init: uriList is empty");
                    return;
                }
                for (Uri uri : semGetAllIconResourceUri) {
                    Log.d("ScspUtils", "saveAllResources: uri = " + uri.toString());
                    z2 = ScspUtils.makeAllResourceData(context2, uri);
                }
                if (z2) {
                    handler3.post(new ScspUtils$$ExternalSyntheticLambda1(collection, 0));
                }
            }
        });
        registerAdapterIntentReceiver();
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        if (userManager != null && userManager.isManagedProfile()) {
            z = true;
        }
        this.mIsWorkProfile = z;
    }

    public void addHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mAdapterIntentFilter.addAction(str);
    }

    public void addProfileHandler(String str, Handler handler) {
        ((HashMap) this.mHandlerMap).put(str, handler);
        this.mProfileIntentFilter.addAction(str);
    }

    public void dispatchActiveDeviceChanged(final CachedBluetoothDevice cachedBluetoothDevice, final int i) {
        ArrayList arrayList = (ArrayList) this.mDeviceManager.getCachedDevicesCopy();
        int size = arrayList.size();
        int i2 = 0;
        CachedBluetoothDevice cachedBluetoothDevice2 = cachedBluetoothDevice;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) obj;
            CachedBluetoothDevice cachedBluetoothDevice4 = cachedBluetoothDevice3.mSubDevice;
            Set set = cachedBluetoothDevice3.mMemberDevices;
            ArraySet arraySet = new ArraySet();
            arraySet.add(cachedBluetoothDevice3);
            HashSet hashSet = (HashSet) set;
            if (!hashSet.isEmpty()) {
                arraySet.addAll(hashSet);
            } else if (cachedBluetoothDevice4 != null) {
                arraySet.add(cachedBluetoothDevice4);
            }
            if (cachedBluetoothDevice != null && arraySet.stream().anyMatch(new Predicate() { // from class: com.android.settingslib.bluetooth.BluetoothEventManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj2) {
                    return ((CachedBluetoothDevice) obj2).equals(CachedBluetoothDevice.this);
                }
            })) {
                Log.d("BluetoothEventManager", "The active device is in the set, report main device as active device:" + cachedBluetoothDevice3.mDevice + ", active device:" + cachedBluetoothDevice.mDevice);
                cachedBluetoothDevice2 = cachedBluetoothDevice3;
            }
            final boolean equals = cachedBluetoothDevice3.equals(cachedBluetoothDevice2);
            arraySet.forEach(new Consumer() { // from class: com.android.settingslib.bluetooth.BluetoothEventManager$$ExternalSyntheticLambda1
                /* JADX WARN: Removed duplicated region for block: B:11:0x0086  */
                /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
                @Override // java.util.function.Consumer
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void accept(java.lang.Object r6) {
                    /*
                        r5 = this;
                        boolean r0 = r1
                        int r5 = r2
                        com.android.settingslib.bluetooth.CachedBluetoothDevice r6 = (com.android.settingslib.bluetooth.CachedBluetoothDevice) r6
                        r6.getClass()
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        java.lang.String r2 = "onActiveDeviceChanged: profile "
                        r1.<init>(r2)
                        java.lang.String r2 = android.bluetooth.BluetoothProfile.getProfileName(r5)
                        r1.append(r2)
                        java.lang.String r2 = ", device "
                        r1.append(r2)
                        android.bluetooth.BluetoothDevice r2 = r6.mDevice
                        java.lang.String r2 = r2.getAnonymizedAddress()
                        r1.append(r2)
                        java.lang.String r2 = ", isActive "
                        r1.append(r2)
                        r1.append(r0)
                        java.lang.String r1 = r1.toString()
                        java.lang.String r2 = "CachedBluetoothDevice"
                        android.util.Log.d(r2, r1)
                        r1 = 1
                        r3 = 0
                        if (r5 == r1) goto L7b
                        r4 = 2
                        if (r5 == r4) goto L72
                        r4 = 21
                        if (r5 == r4) goto L69
                        r4 = 22
                        if (r5 == r4) goto L5f
                        java.lang.StringBuilder r1 = new java.lang.StringBuilder
                        java.lang.String r4 = "onActiveDeviceChanged: unknown profile "
                        r1.<init>(r4)
                        r1.append(r5)
                        java.lang.String r5 = " isActive "
                        r1.append(r5)
                        r1.append(r0)
                        java.lang.String r5 = r1.toString()
                        android.util.Log.w(r2, r5)
                        goto L84
                    L5f:
                        boolean r5 = r6.mIsActiveDeviceLeAudio
                        if (r5 == r0) goto L64
                        goto L65
                    L64:
                        r1 = r3
                    L65:
                        r6.mIsActiveDeviceLeAudio = r0
                    L67:
                        r3 = r1
                        goto L84
                    L69:
                        boolean r5 = r6.mIsActiveDeviceHearingAid
                        if (r5 == r0) goto L6e
                        goto L6f
                    L6e:
                        r1 = r3
                    L6f:
                        r6.mIsActiveDeviceHearingAid = r0
                        goto L67
                    L72:
                        boolean r5 = r6.mIsActiveDeviceA2dp
                        if (r5 == r0) goto L77
                        goto L78
                    L77:
                        r1 = r3
                    L78:
                        r6.mIsActiveDeviceA2dp = r0
                        goto L67
                    L7b:
                        boolean r5 = r6.mIsActiveDeviceHeadset
                        if (r5 == r0) goto L80
                        goto L81
                    L80:
                        r1 = r3
                    L81:
                        r6.mIsActiveDeviceHeadset = r0
                        goto L67
                    L84:
                        if (r3 == 0) goto L89
                        r6.dispatchAttributesChanged$1()
                    L89:
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.BluetoothEventManager$$ExternalSyntheticLambda1.accept(java.lang.Object):void");
                }
            });
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                if (cachedBluetoothDevice3.isHearingDevice()) {
                    cachedBluetoothDeviceManager.mHearingAidDeviceManager.onActiveDeviceChanged(cachedBluetoothDevice3);
                    cachedBluetoothDeviceManager.mHearingAidDeviceManager.notifyDevicesConnectionStatusChanged();
                }
            }
        }
        Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((BluetoothCallback) it.next()).onActiveDeviceChanged(cachedBluetoothDevice2, i);
        }
    }

    public final void dispatchDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        if (cachedBluetoothDevice.mIsSynced) {
            synchronized (this.mSemCallbacks) {
                try {
                    ArrayList arrayList = (ArrayList) this.mSemCallbacks;
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((SBluetoothControllerImpl) obj).getClass();
                    }
                } finally {
                }
            }
        }
        Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((BluetoothCallback) it.next()).onDeviceAdded(cachedBluetoothDevice);
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

    public final boolean readPairedDevices() {
        Set<BluetoothDevice> bondedDevices = this.mLocalAdapter.mAdapter.getBondedDevices();
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
        Collection cachedDevicesCopy = cachedBluetoothDeviceManager.getCachedDevicesCopy();
        boolean z = false;
        if (bondedDevices == null || bondedDevices.size() == 0) {
            ArrayList arrayList = (ArrayList) cachedDevicesCopy;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
                if (cachedBluetoothDevice.mBondState == 12) {
                    cachedBluetoothDevice.mBondState = 10;
                }
            }
            return false;
        }
        ArrayList arrayList2 = (ArrayList) cachedDevicesCopy;
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj2;
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
            } else {
                if (cachedBluetoothDeviceManager.findFilteredDevice(bluetoothDevice) == null && findDevice.getProfiles().stream().anyMatch(new BluetoothEventManager$$ExternalSyntheticLambda2()) && findDevice.mLeadDevice == null) {
                    cachedBluetoothDeviceManager.removeDevice(findDevice);
                    cachedBluetoothDeviceManager.addDevice(findDevice);
                }
                if (findDevice.getName().equals(findDevice.mDevice.getAddress())) {
                    findDevice.fetchName();
                }
            }
        }
        return z;
    }

    public final void readRestoredDevices() {
        ArrayList arrayList;
        int i = 0;
        List restoredDevices = BluetoothUtils.getRestoredDevices(this.mContext, this.mProfileManager, false);
        if (restoredDevices != null) {
            CachedBluetoothDeviceManager cachedBluetoothDeviceManager = this.mDeviceManager;
            synchronized (cachedBluetoothDeviceManager) {
                try {
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
                } catch (Throwable th) {
                    throw th;
                }
            }
            ArrayList arrayList2 = (ArrayList) restoredDevices;
            int size2 = arrayList2.size();
            while (i < size2) {
                Object obj = arrayList2.get(i);
                i++;
                CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj;
                if (cachedBluetoothDevice2.mBondState != 12) {
                    int indexOf = arrayList.indexOf(cachedBluetoothDevice2);
                    if (indexOf > -1) {
                        Log.d("CachedBluetoothDeviceManager", "addRestoredDevices :: newDevice is added already");
                        CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) arrayList.get(indexOf);
                        cachedBluetoothDevice2.mErrorMsg = cachedBluetoothDevice3.mErrorMsg;
                        cachedBluetoothDevice2.mBondState = cachedBluetoothDevice3.mBondState;
                        cachedBluetoothDevice2.mIsBondingByCached = cachedBluetoothDevice3.mIsBondingByCached;
                    }
                    if (cachedBluetoothDeviceManager.mCachedDevices.contains(cachedBluetoothDevice2)) {
                        cachedBluetoothDeviceManager.removeDevice(cachedBluetoothDevice2);
                        cachedBluetoothDeviceManager.mBtManager.mEventManager.dispatchDeviceRemoved(cachedBluetoothDevice2);
                    }
                    BluetoothRetryDetector bluetoothRetryDetector = cachedBluetoothDeviceManager.mBtManager.mRestoredRetryDetector;
                    if (bluetoothRetryDetector != null) {
                        String address = cachedBluetoothDevice2.mDevice.getAddress();
                        if (bluetoothRetryDetector.mIsForRestored && bluetoothRetryDetector.mRestoredDeviceList.containsKey(address)) {
                            ((Integer) bluetoothRetryDetector.mRestoredDeviceList.get(address)).getClass();
                        }
                    }
                    boolean addDevice = cachedBluetoothDeviceManager.addDevice(cachedBluetoothDevice2);
                    cachedBluetoothDevice2.mSequence = cachedBluetoothDeviceManager.mCachedDevices.indexOf(cachedBluetoothDevice2);
                    if (!addDevice) {
                        cachedBluetoothDeviceManager.mBtManager.mEventManager.dispatchDeviceAdded(cachedBluetoothDevice2);
                    }
                }
            }
        }
    }

    public void registerAdapterIntentReceiver() {
        synchronized (this.mReceivers) {
            try {
                if (this.mReceivers.contains(this.mBroadcastReceiver)) {
                    this.mContext.unregisterReceiver(this.mBroadcastReceiver);
                    this.mReceivers.remove(this.mBroadcastReceiver);
                    Log.e("BluetoothEventManager", "registerAdapterIntentReceiver :: mBroadcastReceiver was registered already. Receiver will refresh.");
                }
                registerIntentReceiver(this.mAdapterIntentFilter, this.mBroadcastReceiver);
                this.mReceivers.add(this.mBroadcastReceiver);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void registerCallback(BluetoothCallback bluetoothCallback) {
        ((CopyOnWriteArrayList) this.mCallbacks).add(bluetoothCallback);
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
            try {
                if (this.mReceivers.contains(this.mProfileBroadcastReceiver)) {
                    this.mContext.unregisterReceiver(this.mProfileBroadcastReceiver);
                    this.mReceivers.remove(this.mProfileBroadcastReceiver);
                    Log.e("BluetoothEventManager", "registerProfileIntentReceiver :: mProfileConnectionReceiver was registered already. Receiver will refresh.");
                }
                registerIntentReceiver(this.mProfileIntentFilter, this.mProfileBroadcastReceiver);
                this.mReceivers.add(this.mProfileBroadcastReceiver);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unregisterCallback(BluetoothCallback bluetoothCallback) {
        ((CopyOnWriteArrayList) this.mCallbacks).remove(bluetoothCallback);
    }
}
