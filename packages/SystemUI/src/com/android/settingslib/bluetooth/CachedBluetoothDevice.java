package com.android.settingslib.bluetooth;

import android.app.StatusBarManager;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothHidHost;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothPbap;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;
import android.view.InputDevice;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.AdaptiveIcon;
import com.android.settingslib.widget.AdaptiveOutlineDrawable;
import com.android.systemui.R;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.settingslib.bluetooth.BluetoothRestoredDevice;
import com.samsung.android.settingslib.bluetooth.GattProfile;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.SppProfile;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/* loaded from: classes.dex */
public class CachedBluetoothDevice implements Comparable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String mAddress;
    public short mAppearance;
    public String mBluetoothCastMsg;
    public int mBondState;
    public long mBondTimeStamp;
    public Timestamp mBondTimestamp;
    public BluetoothRetryDetector mBondingDetector;
    public BluetoothClass mBtClass;
    public int mCachedMaxConnectionState;
    public final Map mCallbackExecutorMap;
    public final Collection mCallbacks;
    public long mConnectAttempted;
    public final Context mContext;
    public BluetoothDevice mDevice;
    public String mDeviceName;
    LruCache<String, BitmapDrawable> mDrawableCache;
    public String mErrorMsg;
    public int mGroupId;
    public HearingAidInfo mHearingAidInfo;
    public boolean mIsActiveDeviceA2dp;
    public boolean mIsActiveDeviceHeadset;
    public boolean mIsActiveDeviceHearingAid;
    public boolean mIsActiveDeviceLeAudio;
    public boolean mIsAddrSwitched;
    public boolean mIsBondingByCached;
    public boolean mIsHearingAidDeviceByUUID;
    public boolean mIsRestored;
    public boolean mIsSynced;
    public boolean mIsTablet;
    public boolean mJustDiscovered;
    public CachedBluetoothDevice mLeadDevice;
    public final BluetoothAdapter mLocalAdapter;
    public boolean mLocalNapRoleConnected;
    public ManufacturerData mManufacturerData;
    public final Set mMemberDevices;
    public String mName;
    public String mPrefixName;
    public final Object mProfileLock;
    public final LocalBluetoothProfileManager mProfileManager;
    public final LinkedHashSet mProfiles;
    public final LinkedHashSet mRemovedProfiles;
    public final BluetoothRestoredDevice mRestoredDevice;
    public short mRssi;
    public int mRssiGroup;
    public final Collection mSemCallbacks;
    public int mSequence;
    public CachedBluetoothDevice mSubDevice;
    public int mType;
    public boolean mUnpairing;
    public boolean mVisible;

    /* renamed from: com.android.settingslib.bluetooth.CachedBluetoothDevice$3, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$com$samsung$android$bluetooth$SmepTag;

        static {
            int[] iArr = new int[SmepTag.values().length];
            $SwitchMap$com$samsung$android$bluetooth$SmepTag = iArr;
            try {
                iArr[SmepTag.FEATURE_AURACAST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public interface Callback {
        void onDeviceAttributesChanged();
    }

    static {
        ParcelUuid.fromString("4de17a00-52cb-11e6-bdf4-0800200c9a66");
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) throws Throwable {
        InputDevice inputDevice;
        byte[] metadata;
        this.mType = 0;
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mCallbackExecutorMap = new ConcurrentHashMap();
        this.mConnectAttempted = -1L;
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        String str = null;
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mCachedMaxConnectionState = 0;
        this.mContext = context;
        this.mLocalAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileManager = localBluetoothProfileManager;
        this.mDevice = bluetoothDevice;
        this.mAddress = bluetoothDevice.getAddress();
        fillData();
        this.mGroupId = -1;
        initDrawableCache();
        this.mUnpairing = false;
        String address = this.mDevice.getAddress();
        InputManager inputManager = (InputManager) context.getSystemService(InputManager.class);
        if (inputManager != null) {
            for (int i : inputManager.getInputDeviceIds()) {
                String inputDeviceBluetoothAddress = inputManager.getInputDeviceBluetoothAddress(i);
                if (inputDeviceBluetoothAddress != null && inputDeviceBluetoothAddress.equals(address)) {
                    inputDevice = inputManager.getInputDevice(i);
                    break;
                }
            }
            inputDevice = null;
        } else {
            inputDevice = null;
        }
        if (inputDevice == null || !inputDevice.supportsSource(16386)) {
            BluetoothDevice bluetoothDevice2 = this.mDevice;
            if (bluetoothDevice2 != null && (metadata = bluetoothDevice2.getMetadata(17)) != null) {
                str = new String(metadata);
            }
            TextUtils.equals(str, "Stylus");
        }
    }

    public final void addMemberDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        Context context = this.mContext;
        String packageName = (context == null || context.getPackageName() == null) ? "null" : this.mContext.getPackageName();
        int iMyPid = Process.myPid();
        Log.d("CachedBluetoothDevice", this + " addMemberDevice = " + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
        BluetoothDump.BtLog("CachedBtDev -- addMemberDevice: main = " + this.mDevice.getAnonymizedAddress() + ", member = " + cachedBluetoothDevice.mDevice.getAnonymizedAddress() + " called by PID : " + iMyPid + " @ " + packageName);
        if (this.mMemberDevices.contains(cachedBluetoothDevice)) {
            BluetoothDump.BtLog("CachedBtDev -- addMemberDevice: contains already");
        } else {
            this.mMemberDevices.add(cachedBluetoothDevice);
            this.mLeadDevice = null;
            this.mVisible = true;
            cachedBluetoothDevice.mLeadDevice = this;
            cachedBluetoothDevice.mVisible = false;
        }
        Set set = cachedBluetoothDevice.mMemberDevices;
        if (set == null || set.isEmpty()) {
            return;
        }
        set.remove(this);
    }

    public final boolean checkHearingAidByUuid() {
        ParcelUuid[] uuids = this.mDevice.getUuids();
        ParcelUuid parcelUuid = BluetoothUuid.HEARING_AID;
        return ArrayUtils.contains(uuids, parcelUuid) || ArrayUtils.contains(this.mDevice.getLeService16BitsUuidData(), parcelUuid) || ArrayUtils.contains(this.mDevice.getLeComplete128BitsUuidData(), ParcelUuid.fromString("7d74f4bd-c74a-4431-862c-cce884371592"));
    }

    public final void checkLEConnectionGuide(boolean z) {
        LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(this.mContext, null);
        if (localBluetoothManager == null) {
            return;
        }
        if (!localBluetoothManager.semIsForegroundActivity()) {
            Log.d("CachedBluetoothDevice", "notForeground - skip checking LE");
            return;
        }
        if (this.mType != 2) {
            return;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager == null || !hasProfile(localBluetoothProfileManager.mLeAudioProfile)) {
            if (this.mIsRestored) {
                ParcelUuid[] parcelUuidArr = this.mRestoredDevice.mUuids;
                if (ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HEARING_AID) || ArrayUtils.contains(parcelUuidArr, BluetoothUuid.HOGP)) {
                    return;
                }
            }
            if (z) {
                ParcelUuid[] uuids = this.mDevice.getUuids();
                if (ArrayUtils.contains(uuids, BluetoothUuid.HEARING_AID)) {
                    return;
                }
                if (ArrayUtils.contains(uuids, BluetoothUuid.HOGP)) {
                    this.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_HOGP);
                    return;
                }
            } else {
                ParcelUuid[] leService16BitsUuidData = this.mDevice.getLeService16BitsUuidData();
                if (ArrayUtils.contains(leService16BitsUuidData, BluetoothUuid.HEARING_AID) || ArrayUtils.contains(leService16BitsUuidData, BluetoothUuid.LE_AUDIO) || ArrayUtils.contains(this.mDevice.getLeComplete16BitsUuidData(), BluetoothUuid.HOGP) || ArrayUtils.contains(this.mDevice.getLeComplete128BitsUuidData(), ParcelUuid.fromString("7d74f4bd-c74a-4431-862c-cce884371592"))) {
                    return;
                }
            }
            this.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_LE);
            Context context = this.mContext;
            BluetoothUtils.showToast(context, context.getString(R.string.bluetooth_le_connection_guide));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:80:0x00ad A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ae  */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int compareTo(Object obj) {
        int i;
        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
        int i2 = cachedBluetoothDevice.mCachedMaxConnectionState;
        int i3 = this.mCachedMaxConnectionState;
        int i4 = ((!this.mIsRestored || this.mIsSynced) ? 0 : 1) - ((!cachedBluetoothDevice.mIsRestored || cachedBluetoothDevice.mIsSynced) ? 0 : 1);
        if (i4 != 0) {
            return i4;
        }
        int i5 = (this.mIsSynced ? 1 : 0) - (cachedBluetoothDevice.mIsSynced ? 1 : 0);
        if (i5 != 0) {
            return i5;
        }
        int i6 = (i2 == 2 ? 1 : 0) - (i3 == 2 ? 1 : 0);
        if (i6 != 0) {
            return i6;
        }
        int i7 = cachedBluetoothDevice.mBondState == 12 ? 1 : 0;
        int i8 = this.mBondState;
        int i9 = i7 - (i8 == 12 ? 1 : 0);
        if (i9 != 0) {
            return i9;
        }
        if (i8 == 12) {
            int i10 = ((i2 == 1 || i2 == 3) ? 1 : 0) - ((i3 == 1 || i3 == 3) ? 1 : 0);
            if (i10 != 0) {
                return i10;
            }
            int i11 = (cachedBluetoothDevice.mIsHearingAidDeviceByUUID ? 1 : 0) - (this.mIsHearingAidDeviceByUUID ? 1 : 0);
            if (i11 != 0) {
                return i11;
            }
            long connectionTimeStamp = cachedBluetoothDevice.mDevice.getConnectionTimeStamp() - this.mDevice.getConnectionTimeStamp();
            if (connectionTimeStamp <= 0) {
                if (connectionTimeStamp < 0) {
                    return -1;
                }
                i = (cachedBluetoothDevice.mJustDiscovered ? 1 : 0) - (this.mJustDiscovered ? 1 : 0);
                if (i == 0) {
                    return i;
                }
                int i12 = cachedBluetoothDevice.mRssi - this.mRssi;
                return i12 != 0 ? i12 : getName().compareTo(cachedBluetoothDevice.getName());
            }
            return 1;
        }
        int i13 = (cachedBluetoothDevice.mIsHearingAidDeviceByUUID ? 1 : 0) - (this.mIsHearingAidDeviceByUUID ? 1 : 0);
        if (i13 != 0) {
            return i13;
        }
        int i14 = cachedBluetoothDevice.mRssiGroup - this.mRssiGroup;
        if (i14 != 0) {
            return i14;
        }
        long j = cachedBluetoothDevice.mBondTimeStamp - this.mBondTimeStamp;
        if (j <= 0) {
            if (j < 0) {
                return -1;
            }
            int i15 = this.mSequence - cachedBluetoothDevice.mSequence;
            if (i15 != 0) {
                return i15;
            }
            i = (cachedBluetoothDevice.mJustDiscovered ? 1 : 0) - (this.mJustDiscovered ? 1 : 0);
            if (i == 0) {
            }
        }
        return 1;
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x00ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void connect$1() {
        boolean z;
        Intent intent;
        String packageName = this.mContext.getPackageName();
        if (isHearableUsingWearableManager()) {
            try {
                BluetoothUtils.onStartBudsUniteManager(this.mContext, this);
            } catch (RuntimeException e) {
                Log.d("CachedBluetoothDevice", "error getting RuntimeException", e);
            }
            z = false;
        } else {
            String address = this.mDevice.getAddress();
            int deviceType = getDeviceType();
            if (deviceType != 1) {
                if (deviceType == 2) {
                    intent = new Intent("com.samsung.android.action.BLUETOOTH_DEVICE");
                    intent.putExtra("DATA", getManufacturerRawData());
                    intent.setPackage("com.samsung.android.app.watchmanagerstub");
                    Log.d("CachedBluetoothDevice", "shouldLaunchGM :: Send Bradcast to WatchManagerStub, type : ".concat(deviceType != 0 ? deviceType != 1 ? deviceType != 2 ? "UNKNOWN" : "GEAR" : "GEAR1" : "GENERIC"));
                    z = !isHearableUsingWearableManager();
                }
                z = false;
            } else {
                intent = new Intent("com.samsung.android.sconnect.action.CONNECT_WEARABLE");
                intent.putExtra("WM_MANAGER", "watchmanager");
                intent.setPackage("com.samsung.android.app.watchmanagerstub");
                Log.d("CachedBluetoothDevice", "shouldLaunchGM :: Send Bradcast to WatchManagerStub, type : ".concat(deviceType != 0 ? deviceType != 1 ? deviceType != 2 ? "UNKNOWN" : "GEAR" : "GEAR1" : "GENERIC"));
                z = true;
            }
            if (packageName != null) {
                intent.putExtra("request_app_package_name", packageName);
            }
            intent.putExtra("MAC", address);
            String str = this.mDeviceName;
            if (str == null || str.equals(this.mAddress)) {
                String str2 = TextUtils.isEmpty(null) ? this.mDeviceName : null;
                intent.putExtra(PeripheralConstants.Internal.BtPairingExtraDataType.DEVICE_NAME, str2);
                intent.putExtra("IS_START_ACTIVITY", false);
                intent.addFlags(268435456);
                intent.addFlags(32);
                intent.addFlags(16777216);
                this.mContext.sendBroadcast(intent, "com.samsung.bluetooth.permission.BLUETOOTH_DEVICE");
                StatusBarManager statusBarManager = (StatusBarManager) this.mContext.getSystemService("statusbar");
                if (statusBarManager != null) {
                    statusBarManager.collapsePanels();
                }
            }
        }
        if (z) {
            return;
        }
        if (this.mBondState == 10) {
            startPairing();
            return;
        }
        checkLEConnectionGuide(true);
        this.mConnectAttempted = SystemClock.elapsedRealtime();
        connectDevice();
    }

    public final void connectDevice() {
        boolean z;
        boolean zIsTetheringOn;
        boolean z2;
        NetworkCapabilities networkCapabilities;
        synchronized (this.mProfileLock) {
            try {
                if (getProfiles().isEmpty()) {
                    Log.d("CachedBluetoothDevice", "No profiles. Maybe we will connect later for device " + this.mDevice);
                    return;
                }
                LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
                if (localBluetoothProfileManager != null && hasProfile(localBluetoothProfileManager.mLeAudioProfile)) {
                    this.mConnectAttempted = -1L;
                }
                LocalBluetoothProfileManager localBluetoothProfileManager2 = this.mProfileManager;
                if (localBluetoothProfileManager2 != null && hasProfile(localBluetoothProfileManager2.mPanProfile)) {
                    synchronized (this.mProfileLock) {
                        try {
                            z = true;
                            zIsTetheringOn = false;
                            z2 = this.mProfiles.size() == 1 && this.mProfiles.stream().anyMatch(new Predicate() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    CachedBluetoothDevice cachedBluetoothDevice = this.f$0;
                                    LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) obj;
                                    int i = CachedBluetoothDevice.$r8$clinit;
                                    cachedBluetoothDevice.getClass();
                                    if (!(localBluetoothProfile instanceof PanProfile)) {
                                        return false;
                                    }
                                    ((PanProfile) localBluetoothProfile).getClass();
                                    return true;
                                }
                            });
                        } finally {
                        }
                    }
                    if (z2) {
                        this.mErrorMsg = null;
                        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService("connectivity");
                        if (connectivityManager != null && (networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())) != null && networkCapabilities.hasTransport(1)) {
                            Context context = this.mContext;
                            String str = SystemProperties.get("ro.build.characteristics");
                            if (str == null || !str.contains("tablet")) {
                                z = false;
                            }
                            this.mErrorMsg = z ? context.getString(R.string.bluetooth_tethering_error_with_wifi_tablet_summary) : context.getString(R.string.bluetooth_tethering_error_with_wifi_phone_summary);
                            refresh();
                            return;
                        }
                        BluetoothPan bluetoothPan = this.mProfileManager.mPanProfile.mService;
                        if (bluetoothPan != null) {
                            zIsTetheringOn = bluetoothPan.isTetheringOn();
                        }
                        if (zIsTetheringOn) {
                            Context context2 = this.mContext;
                            BluetoothDevice bluetoothDevice = this.mDevice;
                            Intent intent = new Intent("com.samsung.android.settings.bluetooth.LAUNCH_TETHERING_OFF_ACTIVITY");
                            intent.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
                            intent.setFlags(268435456);
                            context2.startActivity(intent);
                            return;
                        }
                    }
                }
                Log.d("CachedBluetoothDevice", "connect " + this);
                this.mDevice.connect();
                if (this.mGroupId != -1) {
                    Iterator it = ((HashSet) this.mMemberDevices).iterator();
                    while (it.hasNext()) {
                        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it.next();
                        if (cachedBluetoothDevice.mBondState == 12) {
                            Log.d("CachedBluetoothDevice", "connect the member(" + cachedBluetoothDevice.mDevice.getAddress() + ")");
                            cachedBluetoothDevice.mDevice.connect();
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String describeDetail() {
        StringBuilder sb = new StringBuilder();
        String identityAddress = this.mDevice.getIdentityAddress();
        if (TextUtils.isEmpty(identityAddress)) {
            identityAddress = this.mDevice.getAddress();
        }
        if (TextUtils.isEmpty(identityAddress) || !identityAddress.equals(this.mAddress)) {
            StringBuilder sb2 = new StringBuilder("[");
            sb2.append(this.mDevice);
            sb2.append(" => ");
            String identityAddress2 = this.mDevice.getIdentityAddress();
            if (!BluetoothUtils.DEBUG) {
                if (identityAddress2 != null) {
                    String strReplaceAll = identityAddress2.replaceAll(":", "");
                    identityAddress2 = strReplaceAll.substring(0, 6) + "_" + strReplaceAll.substring(11);
                } else {
                    identityAddress2 = "null";
                }
            }
            sb2.append(identityAddress2);
            sb2.append("]");
            sb.append(sb2.toString());
        } else {
            sb.append("[" + this.mDevice + "]");
        }
        sb.append(", [" + this.mBondState + "]");
        sb.append(", [" + this.mIsRestored + "]");
        if (this.mBtClass != null) {
            sb.append(", [" + this.mBtClass + "]");
        } else {
            sb.append(", [null]");
        }
        sb.append(", [" + ((int) this.mAppearance) + "]");
        sb.append(", [" + this.mType + "]");
        if (getManufacturerRawData() != null) {
            sb.append(", [" + this.mManufacturerData.mManufacturerType + "]");
            sb.append(", [");
            for (byte b : getManufacturerRawData()) {
                sb.append(String.format("%02X ", Byte.valueOf(b)));
            }
            sb.append("]");
        }
        if (this.mGroupId != -1) {
            sb.append(", [" + this.mGroupId + "]");
        }
        return sb.toString();
    }

    public final void disconnect() {
        boolean z;
        boolean z2;
        PbapServerProfile pbapServerProfile;
        this.mConnectAttempted = -1L;
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice.semGetAutoSwitchMode() != -1) {
            Intent intent = new Intent("com.samsung.android.mcfds.autoswitch.BUDS_DISCONNECTED_BY_SETTINGS");
            intent.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
            this.mContext.sendBroadcast(intent, "android.permission.BLUETOOTH_PRIVILEGED");
        }
        synchronized (this.mProfileLock) {
            try {
                Iterator it = this.mProfiles.iterator();
                z = false;
                z2 = false;
                while (it.hasNext()) {
                    LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                    if (isConnectedProfile(localBluetoothProfile)) {
                        if (localBluetoothProfile instanceof SppProfile) {
                            z = true;
                        } else if (localBluetoothProfile instanceof GattProfile) {
                            z2 = true;
                        }
                    }
                }
            } finally {
            }
        }
        if (z || z2) {
            Log.d("CachedBluetoothDevice", "disconnect :: Send Intent to disconnect. It won't launch GM");
            Intent intent2 = new Intent("com.samsung.android.watchmanager.ACTION_HM_REQUEST_DISCONNECT");
            String packageName = this.mContext.getPackageName();
            if (packageName != null) {
                intent2.putExtra("request_app_package_name", packageName);
            }
            intent2.putExtra("device_address", this.mAddress);
            intent2.addFlags(268435456);
            intent2.addFlags(32);
            intent2.addFlags(16777216);
            this.mContext.sendBroadcast(intent2, "com.samsung.android.hostmanager.permission.ACCESS_UNIFIED_HOST_MANAGER");
        }
        String name = getName();
        if (BluetoothUtils.isRTL(this.mContext)) {
            name = ContentInViewNode$Request$$ExternalSyntheticOutline0.m("\u200e", name, "\u200e");
        }
        BluetoothUtils.showToast(this.mContext, this.mContext.getString(R.string.bluetooth_disconnect_message, name));
        synchronized (this.mProfileLock) {
            try {
                if (this.mGroupId != -1) {
                    for (CachedBluetoothDevice cachedBluetoothDevice : this.mMemberDevices) {
                        Log.d("CachedBluetoothDevice", "Disconnect the member:" + cachedBluetoothDevice);
                        cachedBluetoothDevice.disconnect();
                    }
                }
                Log.d("CachedBluetoothDevice", "Disconnect " + this);
                this.mDevice.disconnect();
            } finally {
            }
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager == null || (pbapServerProfile = localBluetoothProfileManager.mPbapProfile) == null || !isConnectedProfile(pbapServerProfile)) {
            return;
        }
        BluetoothDevice bluetoothDevice2 = this.mDevice;
        BluetoothPbap bluetoothPbap = pbapServerProfile.mService;
        if (bluetoothPbap == null) {
            return;
        }
        bluetoothPbap.setConnectionPolicy(bluetoothDevice2, 0);
    }

    public final void dispatchAttributesChanged$1() {
        CachedBluetoothDevice cachedBluetoothDevice = this.mLeadDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.dispatchAttributesChanged$1();
            return;
        }
        Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onDeviceAttributesChanged();
        }
        synchronized (this.mSemCallbacks) {
            try {
                Iterator it2 = ((ArrayList) this.mSemCallbacks).iterator();
                if (it2.hasNext()) {
                    if (it2.next() != null) {
                        throw new ClassCastException();
                    }
                    throw null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CachedBluetoothDevice)) {
            return false;
        }
        return this.mDevice.equals(((CachedBluetoothDevice) obj).mDevice);
    }

    public final void fetchActiveDevices() {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            A2dpProfile a2dpProfile = localBluetoothProfileManager.mA2dpProfile;
            if (a2dpProfile != null) {
                this.mIsActiveDeviceA2dp = this.mDevice.equals(a2dpProfile.getActiveDevice());
            }
            HeadsetProfile headsetProfile = this.mProfileManager.mHeadsetProfile;
            if (headsetProfile != null) {
                BluetoothDevice bluetoothDevice = this.mDevice;
                BluetoothAdapter bluetoothAdapter = headsetProfile.mBluetoothAdapter;
                BluetoothDevice bluetoothDevice2 = null;
                if (bluetoothAdapter != null) {
                    List activeDevices = bluetoothAdapter.getActiveDevices(1);
                    if (activeDevices.size() > 0) {
                        bluetoothDevice2 = (BluetoothDevice) activeDevices.get(0);
                    }
                }
                this.mIsActiveDeviceHeadset = bluetoothDevice.equals(bluetoothDevice2);
            }
            HearingAidProfile hearingAidProfile = this.mProfileManager.mHearingAidProfile;
            if (hearingAidProfile != null) {
                BluetoothAdapter bluetoothAdapter2 = hearingAidProfile.mBluetoothAdapter;
                this.mIsActiveDeviceHearingAid = (bluetoothAdapter2 == null ? new ArrayList() : bluetoothAdapter2.getActiveDevices(21)).contains(this.mDevice);
            }
            LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
            if (leAudioProfile != null) {
                BluetoothAdapter bluetoothAdapter3 = leAudioProfile.mBluetoothAdapter;
                this.mIsActiveDeviceLeAudio = (bluetoothAdapter3 == null ? new ArrayList() : bluetoothAdapter3.getActiveDevices(22)).contains(this.mDevice);
            }
        }
    }

    public final void fetchManufacturerData(byte[] bArr) {
        setManufacturerData(bArr);
        if (BluetoothUtils.DEBUG) {
            Log.d("CachedBluetoothDevice", "fetchManufacturerData : " + Arrays.toString(getManufacturerRawData()));
        }
    }

    public final void fetchName() {
        String name = this.mDevice.getName();
        String alias = this.mDevice.getAlias();
        if (TextUtils.isEmpty(name)) {
            this.mDeviceName = this.mDevice.getAddress();
            if (BluetoothUtils.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Device has no Device name (yet), use address: "), this.mDeviceName, "CachedBluetoothDevice");
            }
        } else {
            this.mDeviceName = name;
        }
        if (!TextUtils.isEmpty(alias)) {
            this.mName = alias;
            return;
        }
        this.mName = this.mDevice.getAddress();
        if (BluetoothUtils.DEBUG) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("Device has no name (yet), use address: "), this.mName, "CachedBluetoothDevice");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x00d0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void fillData() throws Throwable {
        fetchName();
        BluetoothClass bluetoothClass = this.mDevice.getBluetoothClass();
        if (bluetoothClass != null) {
            setBtClass(bluetoothClass);
        }
        updateProfiles(this.mDevice.getUuids());
        fetchActiveDevices();
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("bluetooth_phonebook_permission", 0);
        if (sharedPreferences.contains(this.mDevice.getAddress())) {
            if (this.mDevice.getPhonebookAccessPermission() == 0) {
                int i = sharedPreferences.getInt(this.mDevice.getAddress(), 0);
                if (i == 1) {
                    this.mDevice.setPhonebookAccessPermission(1);
                } else if (i == 2) {
                    this.mDevice.setPhonebookAccessPermission(2);
                }
            }
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.remove(this.mDevice.getAddress());
            editorEdit.commit();
        }
        SharedPreferences sharedPreferences2 = this.mContext.getSharedPreferences("bluetooth_message_permission", 0);
        if (sharedPreferences2.contains(this.mDevice.getAddress())) {
            if (this.mDevice.getMessageAccessPermission() == 0) {
                int i2 = sharedPreferences2.getInt(this.mDevice.getAddress(), 0);
                if (i2 == 1) {
                    this.mDevice.setMessageAccessPermission(1);
                } else if (i2 == 2) {
                    this.mDevice.setMessageAccessPermission(2);
                }
            }
            SharedPreferences.Editor editorEdit2 = sharedPreferences2.edit();
            editorEdit2.remove(this.mDevice.getAddress());
            editorEdit2.commit();
        }
        this.mAppearance = (short) this.mDevice.semGetAppearance();
        fetchManufacturerData(this.mDevice.semGetManufacturerData());
        if (getManufacturerRawData() != null) {
            ManufacturerData manufacturerData = this.mManufacturerData;
            if (manufacturerData.mManufacturerType == 2 && manufacturerData.mData.mDeviceCategory == 2) {
                this.mIsTablet = true;
            } else {
                this.mIsTablet = false;
            }
        }
        this.mBondState = this.mDevice.getBondState();
        this.mType = this.mDevice.getType();
        this.mVisible = true;
        this.mIsBondingByCached = false;
        if (isRing()) {
            this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING, false);
        } else if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), this.mDevice.getUuids())) {
            this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH, false);
        } else {
            this.mBondingDetector = this.mBondState == 12 ? new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE, false) : new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.PAIRING_FAILURE, false);
        }
        Log.d("CachedBluetoothDevice", "fillData :: " + describeDetail());
        dispatchAttributesChanged$1();
    }

    public final void fillRestoredData() throws Throwable {
        if (TextUtils.isEmpty(this.mRestoredDevice.mName)) {
            fetchName();
        } else {
            String str = this.mRestoredDevice.mName;
            this.mName = str;
            this.mDeviceName = str;
        }
        Log.d("CachedBluetoothDevice", "fillRestoredData() :: Device - " + getNameForLog() + ", Class - " + this.mRestoredDevice.mCod);
        setBtClass(new BluetoothClass(this.mRestoredDevice.mCod));
        BluetoothClass bluetoothClass = this.mBtClass;
        if (bluetoothClass != null && !bluetoothClass.equals(this.mDevice.getBluetoothClass())) {
            this.mDevice.setBluetoothClass(this.mRestoredDevice.mCod);
        }
        BluetoothRestoredDevice bluetoothRestoredDevice = this.mRestoredDevice;
        this.mAppearance = (short) bluetoothRestoredDevice.mAppearance;
        setManufacturerData(bluetoothRestoredDevice.mManufacturerData);
        BluetoothRestoredDevice bluetoothRestoredDevice2 = this.mRestoredDevice;
        this.mBondTimeStamp = bluetoothRestoredDevice2.mTimeStamp;
        this.mType = bluetoothRestoredDevice2.mLinkType;
        if (bluetoothRestoredDevice2.mManufacturerData != null && !Arrays.equals(this.mDevice.semGetManufacturerData(), this.mRestoredDevice.mManufacturerData)) {
            this.mDevice.semSetManufacturerData(this.mRestoredDevice.mManufacturerData);
        }
        this.mIsRestored = true;
        this.mIsBondingByCached = false;
        if (this.mRestoredDevice.mBondState == 4) {
            this.mIsAddrSwitched = true;
        }
        this.mBondState = 10;
        updateProfiles(null);
        if (isRing()) {
            this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING, false);
        } else if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), this.mRestoredDevice.mUuids)) {
            this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH, false);
        } else {
            this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE, false);
        }
    }

    public final int getAppearanceDrawable(int i) {
        if (i == 64) {
            return getName().startsWith("GALAXY Gear (") ? R.drawable.list_ic_wearable : R.drawable.list_ic_mobile;
        }
        if (i == 128) {
            return R.drawable.list_ic_laptop;
        }
        if (i == 512) {
            return R.drawable.list_ic_dongle;
        }
        if (i == 192 || i == 193) {
            String upperCase = this.mDeviceName.toUpperCase();
            return (upperCase.startsWith("GEAR FIT") || upperCase.startsWith("GALAXY FIT")) ? R.drawable.list_ic_band : R.drawable.list_ic_wearable;
        }
        switch (i) {
            case 960:
            case 961:
                return R.drawable.list_ic_keyboard;
            case 962:
                return R.drawable.list_ic_mouse;
            case 963:
            case 964:
                return R.drawable.list_ic_game_device;
            default:
                return 0;
        }
    }

    public final int getBtClassDrawable() {
        int deviceIcon;
        String str = this.mDeviceName;
        String upperCase = str != null ? str.toUpperCase() : null;
        StringBuilder sb = new StringBuilder("getBtClassDrawable :: ");
        sb.append(getNameForLog());
        sb.append(", BluetoothClass = ");
        sb.append(this.mBtClass);
        sb.append(", Appearance = ");
        RecyclerView$$ExternalSyntheticOutline0.m(this.mAppearance, "CachedBluetoothDevice", sb);
        ManufacturerData manufacturerData = this.mManufacturerData;
        if (manufacturerData != null && (deviceIcon = manufacturerData.getDeviceIcon()) != 0) {
            return deviceIcon;
        }
        BluetoothClass bluetoothClass = this.mBtClass;
        if (bluetoothClass != null) {
            int majorDeviceClass = bluetoothClass.getMajorDeviceClass();
            if (majorDeviceClass == 256) {
                return this.mBtClass.getDeviceClass() == 284 ? R.drawable.list_ic_tablet : R.drawable.list_ic_laptop;
            }
            if (majorDeviceClass == 512) {
                return this.mIsTablet ? R.drawable.list_ic_tablet : R.drawable.list_ic_mobile;
            }
            if (majorDeviceClass == 1024) {
                if (upperCase != null) {
                    int i = upperCase.startsWith("SAMSUNG LEVEL") ? upperCase.contains("BOX") ? R.drawable.list_ic_dlna_audio : R.drawable.list_ic_headset : (upperCase.startsWith("GEAR CIRCLE") && isGearIconX()) ? R.drawable.list_ic_gear_circle : 0;
                    if (i != 0) {
                        return i;
                    }
                }
                if (isGearIconX()) {
                    return R.drawable.list_ic_true_wireless_earbuds;
                }
                if (this.mBtClass.getDeviceClass() == 1084) {
                    return R.drawable.list_ic_tv;
                }
                if (this.mBtClass.getDeviceClass() == 1076) {
                    return R.drawable.list_ic_camcoder;
                }
                LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
                if (localBluetoothProfileManager == null || !hasProfile(localBluetoothProfileManager.mA2dpProfile) || !hasProfile(this.mProfileManager.mHeadsetProfile)) {
                }
                return R.drawable.list_ic_sound_accessory_default;
            }
            if (majorDeviceClass == 1280) {
                return HidProfile.getHidClassDrawable(this.mBtClass);
            }
            if (majorDeviceClass == 1536) {
                return (this.mBtClass.getDeviceClass() == 1664 || this.mBtClass.getDeviceClass() == 1600) ? R.drawable.list_ic_printer : R.drawable.list_ic_camera;
            }
            if (majorDeviceClass == 1792 && this.mBtClass.getDeviceClass() == 1796) {
                return upperCase != null ? (upperCase.startsWith("GEAR FIT") || upperCase.startsWith("GALAXY FIT")) ? R.drawable.list_ic_band : R.drawable.list_ic_wearable : R.drawable.list_ic_wearable;
            }
            int appearanceDrawable = getAppearanceDrawable(this.mAppearance);
            if (appearanceDrawable != 0) {
                return appearanceDrawable;
            }
            if (!this.mBtClass.doesClassMatch(1)) {
                if (this.mBtClass.doesClassMatch(0)) {
                    return R.drawable.list_ic_mono_headset;
                }
            }
            return R.drawable.list_ic_sound_accessory_default;
        }
        short s = this.mAppearance;
        if (s != 0) {
            int appearanceDrawable2 = getAppearanceDrawable(s);
            if (appearanceDrawable2 != 0) {
                return appearanceDrawable2;
            }
        } else {
            Log.w("CachedBluetoothDevice", "mBtClass is null");
        }
        if (isHearingDevice()) {
            return R.drawable.sec_bluetooth_2d_hearing_aids;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager2 = this.mProfileManager;
        if (localBluetoothProfileManager2 != null && hasProfile(localBluetoothProfileManager2.mA2dpProfile) && hasProfile(this.mProfileManager.mHeadsetProfile)) {
            setBtClass(new BluetoothClass(1056));
            return R.drawable.list_ic_sound_accessory_default;
        }
        List<LocalBluetoothProfile> profiles = getProfiles();
        for (LocalBluetoothProfile localBluetoothProfile : profiles) {
            for (int i2 = 0; i2 < profiles.size(); i2++) {
                if (profiles.get(i2) instanceof A2dpProfile) {
                    setBtClass(new BluetoothClass(1048));
                    return R.drawable.list_ic_sound_accessory_default;
                }
            }
            int drawableResource = localBluetoothProfile.getDrawableResource(this.mBtClass);
            if (drawableResource != 0) {
                return drawableResource;
            }
        }
        return R.drawable.list_ic_general_device;
    }

    /* JADX WARN: Removed duplicated region for block: B:219:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x03ed  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x03f2 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getConnectionSummary() {
        Integer num;
        String string;
        boolean zSemIsDualPlayMode;
        BluetoothA2dp bluetoothA2dp;
        BluetoothA2dp bluetoothA2dp2;
        int profileConnectionState;
        Iterator it;
        Iterator it2;
        char c;
        String str = this.mBluetoothCastMsg;
        if (str != null) {
            return str;
        }
        boolean z = BluetoothUtils.DEBUG;
        if (this.mDevice != null) {
            if (!this.mIsRestored || this.mBondState == 11 || !TextUtils.isEmpty(this.mErrorMsg)) {
                try {
                    Class[] clsArr = new Class[0];
                    num = (Integer) BluetoothDevice.class.getDeclaredMethod("getKeyMissingCount", null).invoke(this.mDevice, null);
                    num.getClass();
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException unused) {
                    Log.w("BluetoothUtils", "error happens when getKeyMissingCount.");
                    num = null;
                }
                if (isConnected() || num == null || num.intValue() <= 0) {
                    synchronized (this.mProfileLock) {
                        try {
                            Iterator it3 = getProfiles().iterator();
                            boolean z2 = false;
                            boolean z3 = false;
                            boolean z4 = false;
                            boolean z5 = false;
                            boolean z6 = false;
                            boolean z7 = false;
                            boolean z8 = false;
                            boolean z9 = false;
                            boolean z10 = false;
                            boolean z11 = true;
                            while (true) {
                                if (it3.hasNext()) {
                                    LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it3.next();
                                    if (localBluetoothProfile == null) {
                                        Log.d("CachedBluetoothDevice", "getConnectionSummary :: profile is null");
                                    } else if (localBluetoothProfile instanceof CsipSetCoordinatorProfile) {
                                        Log.d("CachedBluetoothDevice", "getConnectionSummary :: csip profile is excluded from the summary");
                                    } else {
                                        profileConnectionState = getProfileConnectionState(localBluetoothProfile);
                                        StringBuilder sb = new StringBuilder();
                                        it = it3;
                                        sb.append("getConnectionSummary :: profile ::");
                                        sb.append(localBluetoothProfile);
                                        sb.append("  connectionStatus::");
                                        sb.append(profileConnectionState);
                                        Log.d("CachedBluetoothDevice", sb.toString());
                                        if (profileConnectionState != 0) {
                                            if (profileConnectionState == 1) {
                                                Iterator it4 = getProfiles().iterator();
                                                boolean z12 = false;
                                                boolean z13 = false;
                                                while (it4.hasNext()) {
                                                    LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) it4.next();
                                                    if (localBluetoothProfile2 != null) {
                                                        int profileConnectionState2 = getProfileConnectionState(localBluetoothProfile2);
                                                        it2 = it4;
                                                        if (profileConnectionState2 == 1 || profileConnectionState2 == 3) {
                                                            z12 = true;
                                                        }
                                                        c = 2;
                                                        if (profileConnectionState2 == 2) {
                                                            z13 = true;
                                                        }
                                                    } else {
                                                        it2 = it4;
                                                        c = 2;
                                                    }
                                                    it4 = it2;
                                                }
                                                if ((z12 && !z13) || this.mBondState == 11) {
                                                    break;
                                                }
                                            } else if (profileConnectionState == 2) {
                                                if (localBluetoothProfile instanceof A2dpProfile) {
                                                    z3 = true;
                                                }
                                                if (localBluetoothProfile instanceof HeadsetProfile) {
                                                    z4 = true;
                                                }
                                                if (localBluetoothProfile instanceof HidProfile) {
                                                    z6 = true;
                                                }
                                                if ((localBluetoothProfile instanceof PanProfile) && ((PanProfile) localBluetoothProfile).isLocalRoleNap(this.mDevice)) {
                                                    z7 = true;
                                                }
                                                if (localBluetoothProfile instanceof PanProfile) {
                                                    z8 = true;
                                                }
                                                if (localBluetoothProfile instanceof SppProfile) {
                                                    z9 = true;
                                                }
                                                if (localBluetoothProfile instanceof GattProfile) {
                                                    z10 = true;
                                                }
                                                z2 = true;
                                                if (localBluetoothProfile instanceof LeAudioProfile) {
                                                    z5 = true;
                                                }
                                            } else if (profileConnectionState == 3) {
                                                string = this.mContext.getString(BluetoothUtils.getConnectionStateSummary(profileConnectionState));
                                            }
                                        } else if (localBluetoothProfile.isProfileReady()) {
                                            if (localBluetoothProfile instanceof HearingAidProfile) {
                                                z11 = false;
                                            } else if (localBluetoothProfile instanceof LeAudioProfile) {
                                                z5 = false;
                                            }
                                        }
                                        it3 = it;
                                    }
                                    it = it3;
                                    it3 = it;
                                } else {
                                    int iOrElse = Stream.concat(Stream.of(this), this.mMemberDevices.stream()).mapToInt(new CachedBluetoothDevice$$ExternalSyntheticLambda8()).filter(new CachedBluetoothDevice$$ExternalSyntheticLambda10()).min().orElse(-1);
                                    if (z2) {
                                        if (BluetoothUtils.getBooleanMetaData(this.mDevice)) {
                                            BluetoothUtils.getIntMetaData(this.mDevice, 10);
                                            BluetoothUtils.getIntMetaData(this.mDevice, 11);
                                        }
                                        if ((BluetoothUtils.getBooleanMetaData(this.mDevice) ? BluetoothUtils.getIntMetaData(this.mDevice, 10) : -1) == -1) {
                                            zSemIsDualPlayMode = false;
                                            getHearingAidSideBattery(0);
                                        } else {
                                            zSemIsDualPlayMode = false;
                                        }
                                        if ((BluetoothUtils.getBooleanMetaData(this.mDevice) ? BluetoothUtils.getIntMetaData(this.mDevice, 11) : -1) == -1) {
                                            getHearingAidSideBattery(1);
                                        }
                                        if (z3 && z4) {
                                            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
                                            A2dpProfile a2dpProfile = localBluetoothProfileManager == null ? null : localBluetoothProfileManager.mA2dpProfile;
                                            if (a2dpProfile != null && (bluetoothA2dp2 = a2dpProfile.mService) != null) {
                                                zSemIsDualPlayMode = bluetoothA2dp2.semIsDualPlayMode();
                                            }
                                            if (zSemIsDualPlayMode && iOrElse != -1) {
                                                StringBuilder sb2 = new StringBuilder();
                                                sb2.append(this.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset_battery, Integer.valueOf(iOrElse)));
                                                string = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.bluetooth_summary_connected_to_a2dp_headset_dual_audio, sb2);
                                            } else if (iOrElse != -1) {
                                                string = this.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset_battery, Integer.valueOf(iOrElse));
                                            } else if (zSemIsDualPlayMode) {
                                                StringBuilder sb3 = new StringBuilder();
                                                sb3.append(this.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset));
                                                string = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.bluetooth_summary_connected_to_a2dp_headset_dual_audio, sb3);
                                            } else {
                                                string = this.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset);
                                            }
                                        } else if (z5) {
                                            string = iOrElse != -1 ? this.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset_battery, Integer.valueOf(iOrElse)) : this.mContext.getString(R.string.bluetooth_summary_connected_to_a2dp_headset);
                                        } else if (z3) {
                                            LocalBluetoothProfileManager localBluetoothProfileManager2 = this.mProfileManager;
                                            A2dpProfile a2dpProfile2 = localBluetoothProfileManager2 == null ? null : localBluetoothProfileManager2.mA2dpProfile;
                                            boolean zSemIsDualPlayMode2 = (a2dpProfile2 == null || (bluetoothA2dp = a2dpProfile2.mService) == null) ? zSemIsDualPlayMode : bluetoothA2dp.semIsDualPlayMode();
                                            if (zSemIsDualPlayMode2 && iOrElse != -1) {
                                                StringBuilder sb4 = new StringBuilder();
                                                sb4.append(this.mContext.getString(R.string.bluetooth_a2dp_profile_summary_connected_battery, Integer.valueOf(iOrElse)));
                                                string = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.bluetooth_summary_connected_to_a2dp_headset_dual_audio, sb4);
                                            } else if (iOrElse != -1) {
                                                string = this.mContext.getString(R.string.bluetooth_a2dp_profile_summary_connected_battery, Integer.valueOf(iOrElse));
                                            } else if (zSemIsDualPlayMode2) {
                                                StringBuilder sb5 = new StringBuilder();
                                                sb5.append(this.mContext.getString(R.string.bluetooth_a2dp_profile_summary_connected));
                                                string = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(this.mContext, R.string.bluetooth_summary_connected_to_a2dp_headset_dual_audio, sb5);
                                            } else {
                                                string = this.mContext.getString(R.string.bluetooth_a2dp_profile_summary_connected);
                                            }
                                        } else {
                                            string = z4 ? iOrElse != -1 ? this.mContext.getString(R.string.bluetooth_headset_profile_summary_connected_battery, Integer.valueOf(iOrElse)) : this.mContext.getString(R.string.bluetooth_headset_profile_summary_connected) : (z6 || z7 || z8 || z9 || z10 || z11 || iOrElse == -1) ? this.mContext.getString(R.string.sec_bluetooth_connected) : this.mContext.getString(R.string.bluetooth_connected_battery, Integer.valueOf(iOrElse));
                                        }
                                    } else {
                                        String str2 = this.mErrorMsg;
                                        if (str2 != null && !str2.isEmpty()) {
                                            string = this.mErrorMsg;
                                        } else if (this.mIsHearingAidDeviceByUUID) {
                                            string = this.mContext.getString(R.string.bluetooth_hearingaid_subtext);
                                        } else {
                                            int i = this.mBondState;
                                            if (i != 10) {
                                                string = i != 11 ? null : this.mContext.getString(R.string.bluetooth_pairing);
                                            } else if (this.mLocalAdapter != null && getName().equals(this.mDevice.getAddress())) {
                                                string = this.mLocalAdapter.isDiscovering() ? this.mContext.getString(R.string.bluetooth_getting_remote_device_name) : this.mContext.getString(R.string.bluetooth_display_remote_device_name_after_pair);
                                            }
                                        }
                                    }
                                }
                            }
                            string = this.mContext.getString(BluetoothUtils.getConnectionStateSummary(profileConnectionState));
                        } finally {
                        }
                    }
                } else {
                    string = this.mContext.getString(R.string.sec_bluetooth_not_connect);
                }
            } else if (!this.mIsSynced) {
                string = this.mIsAddrSwitched ? this.mContext.getString(R.string.bluetooth_summary_addr_switched_device) : this.mContext.getString(R.string.bluetooth_summary_restored_device);
            }
            if (string == null) {
                return string.toString();
            }
            return null;
        }
        Log.e("CachedBluetoothDevice", "getConnectionSummary :: mDevice is null");
        string = null;
        if (string == null) {
        }
    }

    public final int getDeviceSide() {
        HearingAidInfo hearingAidInfo = this.mHearingAidInfo;
        if (hearingAidInfo != null) {
            return hearingAidInfo.mSide;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:60:0x00b0, code lost:
    
        r8 = r8.mManufacturerData.mData;
        r3 = r8.mDeviceStatus;
        r8 = r8.mDeviceSubType;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00bb, code lost:
    
        if ((r3 & 128) != 128) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00bf, code lost:
    
        if (r8 < 16) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00c3, code lost:
    
        if (r8 > 31) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00c5, code lost:
    
        android.util.Log.i("CachedBluetoothDevice", "Found a SEC Wearable device with new type");
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ce, code lost:
    
        if (r0.isValidStub() == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00d0, code lost:
    
        android.util.Log.w("CachedBluetoothDevice", "jump to WM");
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00d5, code lost:
    
        return 2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int getDeviceType() {
        ManufacturerData manufacturerData;
        int i;
        String str;
        Context context = this.mContext;
        boolean z = BluetoothUtils.DEBUG;
        if (((SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE") || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING") || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING")) && SemEmergencyManager.getInstance(context) != null) ? SemEmergencyManager.isEmergencyMode(context) : false) {
            Log.d("CachedBluetoothDevice", "getDeviceType: EmergencyMode enabled");
            return 0;
        }
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager == null) {
            Log.d("CachedBluetoothDevice", "getDeviceType: LocalBluetoothProfileManager is null");
            return 0;
        }
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = localBluetoothProfileManager.mDeviceManager;
        if (cachedBluetoothDeviceManager == null) {
            Log.d("CachedBluetoothDevice", "getDeviceType: CachedBluetoothDeviceManager is null");
            return 0;
        }
        if (getBtClassDrawable() == R.drawable.list_ic_wearable && (str = this.mDeviceName) != null && str.startsWith("GALAXY Gear (")) {
            if (cachedBluetoothDeviceManager.isValidStub()) {
                return 1;
            }
        } else if (getManufacturerRawData() != null && ((i = (manufacturerData = this.mManufacturerData).mManufacturerType) == 1 || i == 2 || i == 3)) {
            return (r4 = (r3 = manufacturerData.mData.mDeviceId)[0]) == 0 ? 2 : 2;
        }
        return 0;
    }

    public final Pair getDrawableWithDescription() throws Resources.NotFoundException {
        byte[] metadata;
        BluetoothDevice bluetoothDevice = this.mDevice;
        boolean z = BluetoothUtils.DEBUG;
        String str = (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(5)) == null) ? null : new String(metadata);
        Uri uri = str != null ? Uri.parse(str) : null;
        Pair btClassDrawableWithDescription = BluetoothUtils.getBtClassDrawableWithDescription(this.mContext, this);
        if (BluetoothUtils.isAdvancedDetailsHeader(this.mDevice) && uri != null) {
            BitmapDrawable bitmapDrawable = this.mDrawableCache.get(uri.toString());
            if (bitmapDrawable != null) {
                return new Pair(new AdaptiveOutlineDrawable(this.mContext.getResources(), bitmapDrawable.getBitmap()), (String) btClassDrawableWithDescription.second);
            }
            refresh();
        }
        Context context = this.mContext;
        Resources resources = context.getResources();
        Pair btDrawableWithDescription = BluetoothUtils.getBtDrawableWithDescription(context, this);
        if (btDrawableWithDescription.first instanceof BitmapDrawable) {
            return new Pair(new AdaptiveOutlineDrawable(resources, ((BitmapDrawable) btDrawableWithDescription.first).getBitmap()), (String) btDrawableWithDescription.second);
        }
        int i = this.mGroupId;
        int iHashCode = i != -1 ? new Integer(i).hashCode() : this.mDevice.getAddress().hashCode();
        Drawable drawable = (Drawable) btDrawableWithDescription.first;
        Resources resources2 = context.getResources();
        int[] intArray = resources2.getIntArray(R.array.bt_icon_fg_colors);
        int[] intArray2 = resources2.getIntArray(R.array.bt_icon_bg_colors);
        int iAbs = Math.abs(iHashCode % intArray2.length);
        drawable.setTint(intArray[iAbs]);
        AdaptiveIcon adaptiveIcon = new AdaptiveIcon(context, drawable);
        adaptiveIcon.setBackgroundColor(intArray2[iAbs]);
        return new Pair(adaptiveIcon, (String) btDrawableWithDescription.second);
    }

    public final int getHearingAidSideBattery(final int i) {
        Optional optionalFindAny = Stream.concat(Stream.of((Object[]) new CachedBluetoothDevice[]{this, this.mSubDevice}), this.mMemberDevices.stream()).filter(new CachedBluetoothDevice$$ExternalSyntheticLambda0(1)).filter(new Predicate() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i2 = i;
                int i3 = CachedBluetoothDevice.$r8$clinit;
                return ((CachedBluetoothDevice) obj).getDeviceSide() == i2;
            }
        }).filter(new CachedBluetoothDevice$$ExternalSyntheticLambda0(2)).findAny();
        if (optionalFindAny.isPresent()) {
            return ((Integer) optionalFindAny.map(new CachedBluetoothDevice$$ExternalSyntheticLambda6()).filter(new CachedBluetoothDevice$$ExternalSyntheticLambda0(5)).orElse(-1)).intValue();
        }
        return -1;
    }

    public final long getHiSyncId() {
        HearingAidInfo hearingAidInfo = this.mHearingAidInfo;
        if (hearingAidInfo != null) {
            return hearingAidInfo.mHiSyncId;
        }
        return 0L;
    }

    public final Drawable getIconDrawable(boolean z) throws Throwable {
        BitmapDrawable listIcon;
        String resourcePath = getResourcePath(true);
        if (TextUtils.isEmpty(resourcePath)) {
            Log.d("CachedBluetoothDevice", "getLocalIconResource: path is null");
            listIcon = null;
        } else if (isSupportAuraCast()) {
            Context context = this.mContext;
            String str = ScspUtils.FILE_PATH_ROOT;
            Log.d("ScspUtils", "getAuraCastIcon: path = " + resourcePath);
            listIcon = ScspUtils.getIcon(context, resourcePath + ScspUtils.FILE_NAME_AURA_CAST + ScspUtils.FILE_EXTENSION_SVG);
        } else {
            listIcon = ScspUtils.getListIcon(this.mContext, resourcePath);
        }
        if (listIcon != null) {
            return listIcon;
        }
        Resources resources = this.mContext.getResources();
        Drawable drawable = resources.getDrawable(getBtClassDrawable());
        if (!z || !isSupportAuraCast()) {
            return drawable;
        }
        ManufacturerData manufacturerData = this.mManufacturerData;
        int deviceIcon = manufacturerData != null ? manufacturerData.getDeviceIcon() : 0;
        return (deviceIcon == 0 || deviceIcon != R.drawable.list_ic_earbuds_stem) ? BluetoothUtils.getOverlayIconTintableDrawable(drawable, this.mContext, R.drawable.auracast_ic_overlay, R.drawable.auracast_ic_tintable) : resources.getDrawable(R.drawable.list_ic_earbuds_stem_auracast);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable getIconDrawableForSolid() throws Throwable {
        BitmapDrawable listIcon;
        ManufacturerData manufacturerData;
        int i = 0;
        String resourcePath = getResourcePath(false);
        if (TextUtils.isEmpty(resourcePath)) {
            Log.d("CachedBluetoothDevice", "getLocalIconResourceForSolid: path is null");
            listIcon = null;
        } else {
            listIcon = ScspUtils.getListIcon(this.mContext, resourcePath);
        }
        if (listIcon == null && (manufacturerData = this.mManufacturerData) != null) {
            int i2 = manufacturerData.mManufacturerType;
            if (i2 == 2 || i2 == 3) {
                ManufacturerData.Data data = manufacturerData.mData;
                byte b = data.mDeviceCategory;
                byte b2 = data.mDeviceIconIndex;
                ManufacturerData.SSdevice sSdevice = manufacturerData.mSSdevice;
                sSdevice.getClass();
                if (b == 21) {
                    if (b2 == 2) {
                        i = R.drawable.list_ic_mono_headset;
                    } else if (b2 != 3) {
                        i = b2 != 4 ? b2 != 5 ? R.drawable.list_ic_headset : R.drawable.systemui_ic_buds3_solid : R.drawable.systemui_ic_buds2_solid;
                    } else if (ManufacturerData.this.isBuds3Device()) {
                    }
                }
            }
            if (i != 0) {
                return this.mContext.getResources().getDrawable(i);
            }
        }
        return listIcon;
    }

    public final byte[] getManufacturerRawData() {
        ManufacturerData manufacturerData = this.mManufacturerData;
        if (manufacturerData == null) {
            return null;
        }
        return manufacturerData.mManufacturerRawData;
    }

    public final String getName() {
        if (!TextUtils.isEmpty(this.mName) && !this.mName.equals(this.mDeviceName)) {
            return this.mName;
        }
        String str = this.mDeviceName;
        return str != null ? str : this.mAddress;
    }

    public final String getNameForLog() {
        StringBuilder sb = new StringBuilder();
        String str = this.mName;
        if (str == null || str.equals(this.mDeviceName)) {
            String str2 = this.mDeviceName;
            if (str2 != null && !str2.equals(this.mDevice.getAddress())) {
                sb.append("(N) ");
            }
        } else {
            sb.append("(A) ");
        }
        String name = getName();
        if (!name.equals(this.mDevice.getAddress()) || BluetoothUtils.DEBUG) {
            sb.append(name);
            return sb.toString();
        }
        return name.substring(0, 14) + ":XX";
    }

    public final int getProfileConnectionState(LocalBluetoothProfile localBluetoothProfile) {
        int connectionStatus = localBluetoothProfile != null ? localBluetoothProfile.getConnectionStatus(this.mDevice) : 0;
        Log.d("CachedBluetoothDevice", "getProfileConnectionState :: " + localBluetoothProfile + ", state : " + connectionStatus);
        return connectionStatus;
    }

    public final List getProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mProfileLock) {
            arrayList.addAll(this.mProfiles);
        }
        return Collections.unmodifiableList(arrayList);
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00f7, code lost:
    
        r2 = r0[1].trim();
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00fd, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0101, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0102, code lost:
    
        android.util.Log.w("ScspUtils", "getMappingKey: Exception.", r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:123:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0137 A[Catch: Exception -> 0x0111, TRY_ENTER, TRY_LEAVE, TryCatch #6 {Exception -> 0x0111, blocks: (B:44:0x010d, B:56:0x0137), top: B:109:0x0092 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getResourcePath(boolean z) throws Throwable {
        char c;
        Throwable th;
        BufferedReader bufferedReader;
        Log.d("CachedBluetoothDevice", "getResourcePath: name = " + getNameForLog());
        ManufacturerData manufacturerData = this.mManufacturerData;
        BufferedReader bufferedReader2 = null;
        if (manufacturerData == null) {
            Log.d("CachedBluetoothDevice", "getResourcePath: mManufacturerData is null");
            return null;
        }
        ManufacturerData.Data data = manufacturerData.mData;
        byte b = data.mDeviceCategory;
        if (b <= 0) {
            return null;
        }
        byte b2 = data.mDeviceIconIndex;
        boolean zIsBuds3Device = isBuds3Device();
        if (b != 21 || b2 != 3 || zIsBuds3Device) {
            if (isBuds3Device()) {
                b2 = 5;
            }
            byte[] bArr = {b, b2};
            String fileDirPath = ScspUtils.getFileDirPath(this.mContext);
            String strMakeByteArrayDir = ScspUtils.makeByteArrayDir(bArr);
            if (TextUtils.isEmpty(fileDirPath) || TextUtils.isEmpty(strMakeByteArrayDir)) {
                ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("getIconIndexFileDirPath: "), TextUtils.isEmpty(fileDirPath) ? "dirPath" : "iconIndexDir", " is null", "ScspUtils");
                return null;
            }
            StringBuilder sb = new StringBuilder("getIconIndexFileDirPath: iconIndex = ");
            sb.append(ScspUtils.byteToString(bArr[0]));
            sb.append(ScspUtils.byteToString(bArr[1]));
            sb.append(", iconIndexDir = ");
            sb.append(strMakeByteArrayDir);
            sb.append(", isLineType = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sb, z, "ScspUtils");
            String str = ScspUtils.FILE_PATH_ICON_INDEX;
            if (z) {
                return fileDirPath + str + strMakeByteArrayDir + File.separator;
            }
            StringBuilder sbM = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(fileDirPath);
            sbM.append(ScspUtils.FILE_PATH_SOLID);
            sbM.append(str);
            sbM.append(strMakeByteArrayDir);
            sbM.append(File.separator);
            return sbM.toString();
        }
        byte[] bArr2 = this.mManufacturerData.mData.mDeviceId;
        Log.d("CachedBluetoothDevice", "getResourcePath: deviceId = " + ScspUtils.byteToString(bArr2[0]) + ScspUtils.byteToString(bArr2[1]));
        Context context = this.mContext;
        String fileDirPath2 = ScspUtils.getFileDirPath(context);
        String strMakeByteArrayDir2 = ScspUtils.makeByteArrayDir(bArr2);
        try {
            if ((strMakeByteArrayDir2 == null || strMakeByteArrayDir2.isEmpty()) ? false : Pattern.compile("^(?:[a-fA-F0-9]|([a-fA-F0-9]{2}))_(?:[a-fA-F0-9]|([a-fA-F0-9]{2}))$").matcher(strMakeByteArrayDir2).matches()) {
                try {
                    try {
                        c = 0;
                    } catch (Exception e) {
                        e = e;
                        c = 0;
                    }
                    try {
                        bufferedReader = new BufferedReader(new FileReader(ScspUtils.getFileDirPath(context) + ScspUtils.FILE_NAME_TABLE));
                        while (true) {
                            try {
                                try {
                                    String line = bufferedReader.readLine();
                                    if (line == null) {
                                        bufferedReader.close();
                                        break;
                                    }
                                    String[] strArrSplit = line.split(",");
                                    if (strArrSplit != null && strArrSplit.length == 2) {
                                        Log.d("ScspUtils", "getMappingKey: row[0] = " + strArrSplit[0] + ", row[1] = " + strArrSplit[1]);
                                        if (strMakeByteArrayDir2.equalsIgnoreCase(strArrSplit[0].trim())) {
                                            break;
                                        }
                                    }
                                } catch (Exception e2) {
                                    e = e2;
                                    Log.w("ScspUtils", "getMappingKey: Exception " + e);
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    String strTrim = null;
                                    if (TextUtils.isEmpty(fileDirPath2)) {
                                    }
                                    ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("getDeviceIdFileDirPath: "), TextUtils.isEmpty(fileDirPath2) ? "dirPath" : "deviceIdDir", " is null", "ScspUtils");
                                    return null;
                                }
                            } catch (Throwable th2) {
                                th = th2;
                                bufferedReader2 = bufferedReader;
                                if (bufferedReader2 != null) {
                                    throw th;
                                }
                                try {
                                    bufferedReader2.close();
                                    throw th;
                                } catch (Exception e3) {
                                    Log.w("ScspUtils", "getMappingKey: Exception.", e3);
                                    throw th;
                                }
                            }
                        }
                    } catch (Exception e4) {
                        e = e4;
                        bufferedReader = null;
                        Log.w("ScspUtils", "getMappingKey: Exception " + e);
                        if (bufferedReader != null) {
                        }
                        String strTrim2 = null;
                        if (TextUtils.isEmpty(fileDirPath2)) {
                        }
                        ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("getDeviceIdFileDirPath: "), TextUtils.isEmpty(fileDirPath2) ? "dirPath" : "deviceIdDir", " is null", "ScspUtils");
                        return null;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (bufferedReader2 != null) {
                    }
                }
            } else {
                c = 0;
            }
        } catch (Exception e5) {
            Log.w("ScspUtils", "getMappingKey: Exception.", e5);
        }
        String strTrim22 = null;
        if (!TextUtils.isEmpty(fileDirPath2) || TextUtils.isEmpty(strTrim22)) {
            ExifInterface$$ExternalSyntheticOutline0.m(new StringBuilder("getDeviceIdFileDirPath: "), TextUtils.isEmpty(fileDirPath2) ? "dirPath" : "deviceIdDir", " is null", "ScspUtils");
            return null;
        }
        StringBuilder sb2 = new StringBuilder("getDeviceIdFileDirPath: deviceId = ");
        sb2.append(ScspUtils.byteToString(bArr2[c]));
        sb2.append(ScspUtils.byteToString(bArr2[1]));
        sb2.append(", deviceIdDir = ");
        sb2.append(strTrim22);
        sb2.append(", isLineType = ");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb2, z, "ScspUtils");
        String str2 = ScspUtils.FILE_PATH_DEVICE_ID;
        if (z) {
            return fileDirPath2 + str2 + strTrim22 + File.separator;
        }
        StringBuilder sbM2 = PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(fileDirPath2);
        sbM2.append(ScspUtils.FILE_PATH_SOLID);
        sbM2.append(str2);
        sbM2.append(strTrim22);
        sbM2.append(File.separator);
        return sbM2.toString();
    }

    public final List getUiAccessibleProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mProfileLock) {
            try {
                Iterator it = this.mProfiles.iterator();
                while (it.hasNext()) {
                    LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                    if (localBluetoothProfile.accessProfileEnabled()) {
                        arrayList.add(localBluetoothProfile);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return arrayList;
    }

    public final boolean hasProfile(LocalBluetoothProfile localBluetoothProfile) {
        if (localBluetoothProfile == null) {
            Log.e("CachedBluetoothDevice", "hasProfile :: target profile is null, return false.");
            return false;
        }
        List profiles = getProfiles();
        for (int i = 0; i < profiles.size(); i++) {
            LocalBluetoothProfile localBluetoothProfile2 = (LocalBluetoothProfile) profiles.get(i);
            if (localBluetoothProfile2 != null && localBluetoothProfile2.equals(localBluetoothProfile)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.mDevice.getAddress().hashCode();
    }

    public final void initDrawableCache() {
        this.mDrawableCache = new LruCache(this, ((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8) { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice.1
            @Override // android.util.LruCache
            public final int sizeOf(Object obj, Object obj2) {
                return ((BitmapDrawable) obj2).getBitmap().getByteCount() / 1024;
            }
        };
    }

    public boolean isActiveDevice(int i) {
        if (i == 1) {
            return this.mIsActiveDeviceHeadset;
        }
        if (i == 2) {
            return this.mIsActiveDeviceA2dp;
        }
        if (i == 21) {
            return this.mIsActiveDeviceHearingAid;
        }
        if (i == 22) {
            return this.mIsActiveDeviceLeAudio;
        }
        RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "getActiveDevice: unknown profile ", "CachedBluetoothDevice");
        return false;
    }

    public final boolean isBuds3Device() {
        int i;
        ManufacturerData manufacturerData = this.mManufacturerData;
        byte[] bArr = manufacturerData == null ? null : manufacturerData.mData.mDeviceId;
        return bArr != null && bArr.length > 1 && bArr[0] == 1 && (i = bArr[1] & 255) >= 77 && i <= 98;
    }

    public final boolean isBusy() {
        int profileConnectionState;
        for (LocalBluetoothProfile localBluetoothProfile : getProfiles()) {
            if (localBluetoothProfile != null && ((profileConnectionState = getProfileConnectionState(localBluetoothProfile)) == 1 || profileConnectionState == 3)) {
                return true;
            }
        }
        return this.mBondState == 11;
    }

    public final boolean isConnected() {
        synchronized (this.mProfileLock) {
            try {
                Iterator it = this.mProfiles.iterator();
                while (it.hasNext()) {
                    if (getProfileConnectionState((LocalBluetoothProfile) it.next()) == 2) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isConnectedA2dpDevice() {
        A2dpProfile a2dpProfile;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        return (localBluetoothProfileManager == null || (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) == null || a2dpProfile.getConnectionStatus(this.mDevice) != 2) ? false : true;
    }

    public final boolean isConnectedAshaHearingAidDevice() {
        HearingAidProfile hearingAidProfile;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        return (localBluetoothProfileManager == null || (hearingAidProfile = localBluetoothProfileManager.mHearingAidProfile) == null || hearingAidProfile.getConnectionStatus(this.mDevice) != 2) ? false : true;
    }

    public final boolean isConnectedHearingAidDevice() {
        if (isConnectedAshaHearingAidDevice()) {
            return true;
        }
        HapClientProfile hapClientProfile = this.mProfileManager.mHapClientProfile;
        return hapClientProfile != null && hapClientProfile.getConnectionStatus(this.mDevice) == 2 && isConnectedLeAudioDevice();
    }

    public final boolean isConnectedLeAudioDevice() {
        LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
        return leAudioProfile != null && leAudioProfile.getConnectionStatus(this.mDevice) == 2;
    }

    public final boolean isConnectedProfile(LocalBluetoothProfile localBluetoothProfile) {
        return getProfileConnectionState(localBluetoothProfile) == 2;
    }

    public final boolean isGearIconX() {
        BluetoothClass bluetoothClass;
        byte[] manufacturerRawData = getManufacturerRawData();
        if (manufacturerRawData == null || (bluetoothClass = this.mBtClass) == null || manufacturerRawData.length < 9) {
            return false;
        }
        byte[] bArr = this.mManufacturerData.mData.mDeviceId;
        byte b = bArr[0];
        return (b == 0 || b == 1) && bArr[1] == 1 && bluetoothClass.getDeviceClass() == 1028;
    }

    public final boolean isHearableUsingWearableManager() {
        BluetoothClass bluetoothClass;
        byte b;
        byte[] manufacturerRawData = getManufacturerRawData();
        if (manufacturerRawData == null || (bluetoothClass = this.mBtClass) == null || manufacturerRawData.length < 9) {
            return false;
        }
        byte[] bArr = this.mManufacturerData.mData.mDeviceId;
        int i = bArr[1] & 255;
        return bluetoothClass.getDeviceClass() == 1028 && (((b = bArr[0]) == 0 && i >= 1 && i < 144) || b == 1 || b == 2 || b == 3);
    }

    public final boolean isHearingAidDevice() {
        return this.mHearingAidInfo != null;
    }

    public final boolean isHearingDevice() {
        return getProfiles().stream().anyMatch(new CachedBluetoothDevice$$ExternalSyntheticLambda0(0));
    }

    public final boolean isRing() {
        byte b;
        ManufacturerData manufacturerData = this.mManufacturerData;
        return manufacturerData != null && manufacturerData.mManufacturerType == 1 && (b = manufacturerData.mData.mDeviceCategory) >= 0 && b == 1;
    }

    public final boolean isSupportAuraCast() {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null && hasProfile(localBluetoothProfileManager.mLeAudioBroadcastAssistant)) {
            if (isHearableUsingWearableManager()) {
                BluetoothDevice bluetoothDevice = this.mDevice;
                SmepTag smepTag = SmepTag.SUPPORTED_FEATURES;
                int tag = smepTag.getTag();
                byte[] bArrSemGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
                if (bArrSemGetMetadata == null || bArrSemGetMetadata.length < 5) {
                    Log.e("CachedBluetoothDevice", "isSupportAssistant: DataPacket is too short.");
                    return false;
                }
                if ((((bArrSemGetMetadata[0] & 255) | ((bArrSemGetMetadata[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL) == smepTag.getTag()) {
                    int i = 2;
                    while (i < bArrSemGetMetadata.length) {
                        int i2 = ((bArrSemGetMetadata[i] & 255) | ((bArrSemGetMetadata[i + 1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL;
                        int i3 = bArrSemGetMetadata[i + 2] & 255;
                        byte[] bArr = new byte[i3];
                        System.arraycopy(bArrSemGetMetadata, i + 3, bArr, 0, i3);
                        i += i3 + 3;
                        if (AnonymousClass3.$SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.getSmepKey(i2).ordinal()] == 1) {
                            if (i3 < 1) {
                                Log.d("CachedBluetoothDevice", "isSupportAssistant: wrong data");
                                return false;
                            }
                            RecyclerView$$ExternalSyntheticOutline0.m(bArr[0], "CachedBluetoothDevice", new StringBuilder("isSupportAssistant: data = "));
                            if (bArr[0] == 1) {
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public final void onProfileStateChanged(LocalBluetoothProfile localBluetoothProfile, int i) {
        BluetoothDevice bluetoothDevice;
        BluetoothRetryDetector bluetoothRetryDetector;
        if (this.mDevice != null) {
            Log.d("CachedBluetoothDevice", "onProfileStateChanged: profile " + localBluetoothProfile + ", device " + this.mDevice.getAnonymizedAddress() + ", newProfileState " + i);
        } else {
            Log.d("CachedBluetoothDevice", "onProfileStateChanged: profile " + localBluetoothProfile + ", device is null, newProfileState " + i);
        }
        if (this.mLocalAdapter.getState() == 13) {
            Log.d("CachedBluetoothDevice", " BT Turninig Off...Profile conn state change ignored...");
            return;
        }
        if (this.mDevice != null && localBluetoothProfile != null && (i == 2 || i == 0)) {
            AuditLog.logEvent(i == 2 ? IKnoxCustomManager.Stub.TRANSACTION_setForceAutoShutDownState : IKnoxCustomManager.Stub.TRANSACTION_getForceAutoShutDownState, new Object[]{localBluetoothProfile.toString(), this.mDevice.getAddress()});
        }
        synchronized (this.mProfileLock) {
            try {
                if (i == 2) {
                    if (!this.mProfiles.contains(localBluetoothProfile)) {
                        this.mRemovedProfiles.remove(localBluetoothProfile);
                        this.mProfiles.add(localBluetoothProfile);
                        if ((localBluetoothProfile instanceof PanProfile) && ((PanProfile) localBluetoothProfile).isLocalRoleNap(this.mDevice)) {
                            this.mLocalNapRoleConnected = true;
                        }
                    }
                    if (localBluetoothProfile instanceof HidProfile) {
                        updatePreferredTransport();
                    }
                    CachedBluetoothDevice cachedBluetoothDevice = this.mLeadDevice;
                    if (cachedBluetoothDevice != null && !(localBluetoothProfile instanceof CsipSetCoordinatorProfile) && !(localBluetoothProfile instanceof VolumeControlProfile) && (bluetoothRetryDetector = cachedBluetoothDevice.mBondingDetector) != null && bluetoothRetryDetector.mIsForRestored) {
                        bluetoothRetryDetector.mRestoredDeviceList.clear();
                    }
                } else if (this.mLocalNapRoleConnected && (localBluetoothProfile instanceof PanProfile) && (bluetoothDevice = this.mDevice) != null) {
                    ParcelUuid[] uuids = bluetoothDevice.getUuids();
                    if (!ArrayUtils.contains(uuids, BluetoothUuid.NAP) && ArrayUtils.contains(uuids, BluetoothUuid.PANU) && i == 0) {
                        Log.d("CachedBluetoothDevice", "Removing PanProfile from device after NAP disconnect");
                        this.mProfiles.remove(localBluetoothProfile);
                        this.mRemovedProfiles.add(localBluetoothProfile);
                        this.mLocalNapRoleConnected = false;
                    }
                }
                if (localBluetoothProfile instanceof LeAudioProfile) {
                    updatePreferredTransport();
                }
                HearingAidStatsLogUtils.updateHistoryIfNeeded(this.mContext, this, localBluetoothProfile, i);
                updateMaxConnectionState();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void refresh() {
        ListenableFuture listenableFutureSubmit = ((AbstractListeningExecutorService) ThreadUtils.getBackgroundExecutor()).submit(new Callable() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                byte[] metadata;
                CachedBluetoothDevice cachedBluetoothDevice = this.f$0;
                if (BluetoothUtils.isAdvancedDetailsHeader(cachedBluetoothDevice.mDevice)) {
                    BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                    String str = (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(5)) == null) ? null : new String(metadata);
                    Uri uri = str == null ? null : Uri.parse(str);
                    if (uri != null && cachedBluetoothDevice.mDrawableCache.get(uri.toString()) == null) {
                        cachedBluetoothDevice.mDrawableCache.put(uri.toString(), (BitmapDrawable) BluetoothUtils.getBtDrawableWithDescription(cachedBluetoothDevice.mContext, cachedBluetoothDevice).first);
                    }
                }
                return null;
            }
        });
        FutureCallback futureCallback = new FutureCallback() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice.2
            @Override // com.google.common.util.concurrent.FutureCallback
            public final void onSuccess(Object obj) {
                CachedBluetoothDevice.this.dispatchAttributesChanged$1();
            }

            @Override // com.google.common.util.concurrent.FutureCallback
            public final void onFailure(Throwable th) {
            }
        };
        listenableFutureSubmit.addListener(new Futures.CallbackListener(listenableFutureSubmit, futureCallback), this.mContext.getMainExecutor());
    }

    public final void refreshName() {
        fetchName();
        if (BluetoothUtils.DEBUG) {
            Log.d("CachedBluetoothDevice", "Device name: " + getName());
        }
        dispatchAttributesChanged$1();
    }

    public final void registerCallback(Callback callback) {
        if (this.mCallbacks.contains(callback)) {
            this.mCallbacks.remove(callback);
        }
        this.mCallbacks.add(callback);
    }

    public final void setBtClass(BluetoothClass bluetoothClass) {
        if (this.mBtClass != bluetoothClass) {
            boolean z = BluetoothUtils.DEBUG;
            if (z) {
                Log.d("CachedBluetoothDevice", "setBtClass :: " + bluetoothClass);
            }
            if (this.mBtClass == null || bluetoothClass.getMajorDeviceClass() != 7936) {
                this.mBtClass = bluetoothClass;
                dispatchAttributesChanged$1();
            } else if (z) {
                Log.d("CachedBluetoothDevice", "setBtClass :: btClass is " + bluetoothClass + ", not set uncategorized");
            }
        }
    }

    public final void setGroupId(int i) {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null && hasProfile(localBluetoothProfileManager.mLeAudioProfile)) {
            BluetoothDump.BtLog("CachedBtDev -- setGroupId: " + this.mDevice + ", groupId = " + i);
        }
        this.mGroupId = i;
    }

    public final void setHearingAidInfo(HearingAidInfo hearingAidInfo) {
        this.mHearingAidInfo = hearingAidInfo;
        Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onDeviceAttributesChanged();
        }
        ((ConcurrentHashMap) this.mCallbackExecutorMap).forEach(new CachedBluetoothDevice$$ExternalSyntheticLambda3());
    }

    public void setIsDeviceStylus(Boolean bool) {
        bool.getClass();
    }

    public final void setJustDiscovered(boolean z) {
        if (this.mJustDiscovered != z) {
            this.mJustDiscovered = z;
            dispatchAttributesChanged$1();
        }
    }

    public final void setManufacturerData(byte[] bArr) {
        if (BluetoothUtils.DEBUG) {
            Log.d("CachedBluetoothDevice", "setManufacturerData to " + Arrays.toString(bArr));
        }
        if (bArr == null) {
            Log.i("CachedBluetoothDevice", "MF is NULL");
            return;
        }
        if (this.mManufacturerData == null) {
            ManufacturerData manufacturerData = new ManufacturerData(bArr);
            this.mManufacturerData = manufacturerData;
            manufacturerData.mData.getClass();
            this.mPrefixName = "";
            dispatchAttributesChanged$1();
            return;
        }
        if (Arrays.equals(getManufacturerRawData(), bArr)) {
            return;
        }
        this.mManufacturerData.updateDeviceInfo(bArr);
        this.mManufacturerData.mData.getClass();
        this.mPrefixName = "";
        dispatchAttributesChanged$1();
    }

    public final void setName(String str) {
        byte b;
        byte[] byteArray;
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, getName())) {
            return;
        }
        BluetoothDevice bluetoothDevice = this.mDevice;
        int tag = SmepTag.FEATURE_CHANGE_DEVICE_NAME.getTag();
        byte[] bArrSemGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (bArrSemGetMetadata == null || bArrSemGetMetadata.length == 0) {
            Log.d("CachedBluetoothDevice", "FEATURE_CHANGE_DEVICE_NAME = null");
        } else {
            Log.d("CachedBluetoothDevice", "FEATURE_CHANGE_DEVICE_NAME = " + Arrays.toString(bArrSemGetMetadata));
        }
        if (bArrSemGetMetadata == null || bArrSemGetMetadata.length <= 3 || (b = bArrSemGetMetadata[3]) <= 0 || b == -1) {
            this.mDevice.setAlias(str);
        } else {
            int tag2 = SmepTag.CMD_PERSONALIZED_NAME_VALUE.getTag();
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            if (!SmepTag.isValidConstantKey(tag2) || bytes == null || bytes.length == 0) {
                byteArray = null;
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byteArrayOutputStream.write(new byte[]{(byte) tag2, (byte) (tag2 >> 8)});
                    byteArrayOutputStream.write((byte) bytes.length);
                    byteArrayOutputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byteArray = byteArrayOutputStream.toByteArray();
            }
            this.mDevice.semSetMetadata(byteArray);
        }
        dispatchAttributesChanged$1();
        Iterator it = ((HashSet) this.mMemberDevices).iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).setName(str);
        }
        CachedBluetoothDevice cachedBluetoothDevice = this.mSubDevice;
        if (cachedBluetoothDevice != null) {
            cachedBluetoothDevice.setName(str);
        }
    }

    public final void setRssi(short s) {
        int i = this.mRssiGroup;
        if (i != (s >= -56 ? 3 : s >= -68 ? 2 : 1)) {
            if (s >= -56) {
                if (i != 3) {
                    this.mRssiGroup = 3;
                    dispatchAttributesChanged$1();
                }
            } else if (s >= -68) {
                if (i != 2) {
                    this.mRssiGroup = 2;
                    dispatchAttributesChanged$1();
                }
            } else if (s < -68 && i != 1) {
                this.mRssiGroup = 1;
                dispatchAttributesChanged$1();
            }
            this.mRssi = s;
        }
    }

    public final void setSubDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice2;
        if (cachedBluetoothDevice != null && cachedBluetoothDevice.equals(this)) {
            Log.d("CachedBluetoothDevice", "setSubDevice: sub device is own");
            return;
        }
        this.mSubDevice = cachedBluetoothDevice;
        if (cachedBluetoothDevice == null || (cachedBluetoothDevice2 = cachedBluetoothDevice.mSubDevice) == null || !cachedBluetoothDevice2.equals(this)) {
            return;
        }
        Log.d("CachedBluetoothDevice", "setSubDevice: sub device's sub device is own.");
        BluetoothDevice bluetoothDevice = this.mDevice;
        BluetoothDevice bluetoothDevice2 = this.mSubDevice.mDevice;
        if (bluetoothDevice == null || bluetoothDevice2 == null) {
            Log.w("CachedBluetoothDevice", "getDevice() or mSubDevice.getDevice() returned null");
        } else {
            BluetoothDump.BtLog("CachedBtDev --  setSubDevice: sub device's sub device is own. main = " + bluetoothDevice.getAnonymizedAddress() + ", subDevice = " + bluetoothDevice2.getAnonymizedAddress());
        }
        this.mSubDevice.setSubDevice(null);
    }

    public final void startPairing() {
        if (this.mLocalAdapter.isDiscovering()) {
            this.mLocalAdapter.cancelDiscovery();
        }
        int i = 0;
        checkLEConnectionGuide(false);
        if (this.mIsRestored && this.mType == 2) {
            i = 2;
        }
        if (this.mDevice.createBond(i)) {
            this.mIsBondingByCached = true;
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CachedBluetoothDevice{anonymizedAddress=");
        sb.append(this.mDevice.getAnonymizedAddress());
        sb.append(", name=");
        sb.append(getName());
        sb.append(", groupId=");
        sb.append(this.mGroupId);
        sb.append(", member=");
        sb.append(this.mMemberDevices);
        if (isHearingAidDevice()) {
            sb.append(", hearingAidInfo=");
            sb.append(this.mHearingAidInfo);
            sb.append(", subDevice=");
            sb.append(this.mSubDevice);
        }
        sb.append("}");
        return sb.toString();
    }

    public final void unpair() {
        if (this.mIsRestored) {
            LocalBluetoothManager localBluetoothManager = LocalBluetoothManager.getInstance(this.mContext, null);
            if (localBluetoothManager != null) {
                localBluetoothManager.mCachedDeviceManager.removeRestoredDevice(this);
            }
        } else {
            unpairLegacy();
        }
        BluetoothRetryDetector bluetoothRetryDetector = this.mBondingDetector;
        if (bluetoothRetryDetector != null) {
            if (bluetoothRetryDetector.mIsForRestored) {
                bluetoothRetryDetector.mRestoredDeviceList.clear();
            }
            if (isRing()) {
                this.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING);
            } else if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), this.mDevice.getUuids())) {
                this.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
            } else {
                this.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.PAIRING_FAILURE);
            }
        }
    }

    public final void unpairLegacy() {
        int i = this.mBondState;
        if (i != 10) {
            if (i == 11) {
                this.mDevice.cancelBondProcess();
            }
            BluetoothDevice bluetoothDevice = this.mDevice;
            if (bluetoothDevice != null) {
                this.mUnpairing = true;
                BluetoothDump.BtLog("CachedBluetoothDevice -- unpairLegacy: decribe = " + describeDetail());
                if (this.mGroupId != -1) {
                    StringBuilder sb = new StringBuilder();
                    for (CachedBluetoothDevice cachedBluetoothDevice : this.mMemberDevices) {
                        if (sb.length() != 0) {
                            sb.append(" : ");
                        }
                        sb.append(cachedBluetoothDevice.getName() + "(" + cachedBluetoothDevice.mDevice.getAddress() + ")");
                    }
                    BluetoothDump.BtLog("CachedBluetoothDevice -- unpairLegacy: member = " + sb.toString());
                }
                if (bluetoothDevice.removeBond()) {
                    this.mDrawableCache.evictAll();
                    boolean z = BluetoothUtils.DEBUG;
                    if (z) {
                        StringBuilder sb2 = new StringBuilder("Command sent successfully:REMOVE_BOND ");
                        StringBuilder sb3 = new StringBuilder();
                        if (z) {
                            sb3.append("Address:");
                            sb3.append(this.mDevice);
                        }
                        sb2.append(sb3.toString());
                        Log.d("CachedBluetoothDevice", sb2.toString());
                    }
                }
            }
        }
    }

    public final void unregisterCallback(Callback callback) {
        if (this.mCallbacks.contains(callback)) {
            this.mCallbacks.remove(callback);
        }
        this.mCallbacks.remove(callback);
        this.mCallbackExecutorMap.remove(callback);
    }

    public final void updateMaxConnectionState() {
        int i;
        int profileConnectionState;
        synchronized (this.mProfileLock) {
            try {
                i = 0;
                for (LocalBluetoothProfile localBluetoothProfile : getProfiles()) {
                    if (localBluetoothProfile != null && !(localBluetoothProfile instanceof CsipSetCoordinatorProfile) && (profileConnectionState = getProfileConnectionState(localBluetoothProfile)) > i) {
                        i = profileConnectionState;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mCachedMaxConnectionState = i;
    }

    public final void updatePreferredTransport() {
        LeAudioProfile leAudioProfile = (LeAudioProfile) this.mProfiles.stream().filter(new CachedBluetoothDevice$$ExternalSyntheticLambda0(3)).findFirst().orElse(null);
        HidProfile hidProfile = (HidProfile) this.mProfiles.stream().filter(new CachedBluetoothDevice$$ExternalSyntheticLambda0(4)).findFirst().orElse(null);
        if (leAudioProfile == null || hidProfile == null) {
            return;
        }
        BluetoothDevice bluetoothDevice = this.mDevice;
        int i = leAudioProfile.isEnabled(bluetoothDevice) ? 2 : 1;
        BluetoothHidHost bluetoothHidHost = hidProfile.mService;
        if (bluetoothHidHost != null) {
            bluetoothHidHost.setPreferredTransport(bluetoothDevice, i);
        }
        Log.w("CachedBluetoothDevice", "Fail to set preferred transport");
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x006b, code lost:
    
        android.util.Log.d("CachedBluetoothDevice", "PAN connection exists. Restore PAN profile.");
        r6.mRemovedProfiles.remove(r1);
        r7 = r6.mProfileLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0079, code lost:
    
        monitor-enter(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x007a, code lost:
    
        r6.mProfiles.add(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x007f, code lost:
    
        monitor-exit(r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final synchronized boolean updateProfiles(ParcelUuid[] parcelUuidArr) throws Throwable {
        Throwable th;
        CachedBluetoothDevice cachedBluetoothDevice;
        ParcelUuid[] parcelUuidArr2;
        CachedBluetoothDevice cachedBluetoothDevice2;
        try {
            if (parcelUuidArr == null) {
                try {
                    Log.e("CachedBluetoothDevice", "updateProfiles :: uuids is null");
                    if (this.mIsRestored) {
                        parcelUuidArr = this.mRestoredDevice.mUuids;
                    }
                    this.mIsHearingAidDeviceByUUID = checkHearingAidByUuid();
                    if (parcelUuidArr == null) {
                        return false;
                    }
                } catch (Throwable th2) {
                    th = th2;
                    cachedBluetoothDevice = this;
                    throw th;
                }
            }
            parcelUuidArr2 = parcelUuidArr;
        } catch (Throwable th3) {
            th = th3;
        }
        try {
            List uuidsList = this.mLocalAdapter.getUuidsList();
            int size = uuidsList.size();
            ParcelUuid[] parcelUuidArr3 = new ParcelUuid[size];
            uuidsList.toArray(parcelUuidArr3);
            if (size == 0) {
                return false;
            }
            synchronized (this.mProfileLock) {
                try {
                    LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
                    if (localBluetoothProfileManager != null) {
                        cachedBluetoothDevice2 = this;
                        try {
                            localBluetoothProfileManager.updateProfiles(parcelUuidArr2, parcelUuidArr3, this.mProfiles, this.mRemovedProfiles, cachedBluetoothDevice2);
                        } catch (Throwable th4) {
                            th = th4;
                            throw th;
                        }
                    } else {
                        cachedBluetoothDevice2 = this;
                    }
                    if (cachedBluetoothDevice2.mLocalNapRoleConnected) {
                        Iterator it = cachedBluetoothDevice2.mRemovedProfiles.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                            if (localBluetoothProfile instanceof PanProfile) {
                                break;
                            }
                        }
                    }
                    cachedBluetoothDevice2.mIsHearingAidDeviceByUUID = cachedBluetoothDevice2.checkHearingAidByUuid();
                    if (BluetoothUtils.DEBUG) {
                        Log.e("CachedBluetoothDevice", "updating profiles for " + cachedBluetoothDevice2.mDevice.getAlias() + ", " + cachedBluetoothDevice2.mDevice);
                        BluetoothClass bluetoothClass = cachedBluetoothDevice2.mDevice.getBluetoothClass();
                        if (bluetoothClass != null) {
                            bluetoothClass.toString();
                        }
                        for (ParcelUuid parcelUuid : parcelUuidArr2) {
                            Objects.toString(parcelUuid);
                        }
                    }
                    return true;
                } catch (Throwable th5) {
                    th = th5;
                }
            }
        } catch (Throwable th6) {
            th = th6;
            cachedBluetoothDevice = this;
            th = th;
            throw th;
        }
    }

    public void setInputDevice(InputDevice inputDevice) {
    }

    public void setLocalBluetoothManager(LocalBluetoothManager localBluetoothManager) {
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00c2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice, Intent intent) throws Throwable {
        this.mType = 0;
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mCallbackExecutorMap = new ConcurrentHashMap();
        this.mConnectAttempted = -1L;
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mCachedMaxConnectionState = 0;
        this.mContext = context;
        this.mLocalAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileManager = localBluetoothProfileManager;
        this.mDevice = bluetoothDevice;
        String address = bluetoothDevice.getAddress();
        this.mAddress = address;
        String stringExtra = intent.getStringExtra("android.bluetooth.device.extra.NAME");
        if (TextUtils.isEmpty(stringExtra)) {
            if (BluetoothUtils.DEBUG) {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("Device has no name (yet), use address: ", address, "CachedBluetoothDevice");
            }
            this.mName = address;
            this.mDeviceName = address;
        } else {
            this.mName = stringExtra;
            this.mDeviceName = stringExtra;
        }
        BluetoothClass bluetoothClass = (BluetoothClass) intent.getParcelableExtra("android.bluetooth.device.extra.CLASS");
        if (bluetoothClass != null) {
            setBtClass(bluetoothClass);
        }
        updateProfiles(this.mDevice.getUuids());
        this.mAppearance = intent.getShortExtra("com.samsung.bluetooth.device.extra.APPEARANCE", (short) 0);
        fetchManufacturerData(intent.getByteArrayExtra("com.samsung.bluetooth.device.extra.MANUFACTURER_DATA"));
        if (getManufacturerRawData() != null) {
            ManufacturerData manufacturerData = this.mManufacturerData;
            if (manufacturerData.mManufacturerType == 2 && manufacturerData.mData.mDeviceCategory == 2) {
                this.mIsTablet = true;
            } else {
                this.mIsTablet = false;
            }
        }
        this.mBondState = 10;
        this.mType = this.mDevice.getType();
        this.mVisible = true;
        this.mIsBondingByCached = false;
        if (isRing()) {
            this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING, false);
        } else if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), null)) {
            this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH, false);
        } else {
            this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.PAIRING_FAILURE, false);
        }
        Log.d("CachedBluetoothDevice", "fillUnBondedData :: " + describeDetail());
        dispatchAttributesChanged$1();
        this.mGroupId = -1;
        initDrawableCache();
        this.mUnpairing = false;
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothRestoredDevice bluetoothRestoredDevice) throws Throwable {
        this.mType = 0;
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mCallbackExecutorMap = new ConcurrentHashMap();
        this.mConnectAttempted = -1L;
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mCachedMaxConnectionState = 0;
        this.mContext = context;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mLocalAdapter = defaultAdapter;
        this.mProfileManager = localBluetoothProfileManager;
        this.mRestoredDevice = bluetoothRestoredDevice;
        String str = bluetoothRestoredDevice.mAddress;
        this.mAddress = str;
        if (defaultAdapter != null) {
            this.mDevice = defaultAdapter.getRemoteDevice(str);
        }
        fillRestoredData();
        this.mGroupId = -1;
        initDrawableCache();
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothRestoredDevice bluetoothRestoredDevice, boolean z) throws Throwable {
        this.mType = 0;
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mCallbackExecutorMap = new ConcurrentHashMap();
        this.mConnectAttempted = -1L;
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mCachedMaxConnectionState = 0;
        this.mContext = context;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mLocalAdapter = defaultAdapter;
        this.mProfileManager = localBluetoothProfileManager;
        this.mRestoredDevice = bluetoothRestoredDevice;
        String str = bluetoothRestoredDevice.mAddress;
        this.mAddress = str;
        if (defaultAdapter != null) {
            this.mDevice = defaultAdapter.getRemoteDevice(str);
        }
        if (z) {
            String str2 = bluetoothRestoredDevice.mName;
            if (str2 == null) {
                fetchName();
            } else {
                this.mName = str2;
                this.mDeviceName = str2;
            }
            Log.d("CachedBluetoothDevice", "fillSyncedData() :: Device - " + getNameForLog() + ", Class - " + bluetoothRestoredDevice.mCod);
            setBtClass(new BluetoothClass(bluetoothRestoredDevice.mCod));
            BluetoothClass bluetoothClass = this.mBtClass;
            if (bluetoothClass != null && !bluetoothClass.equals(this.mDevice.getBluetoothClass())) {
                this.mDevice.setBluetoothClass(bluetoothRestoredDevice.mCod);
            }
            this.mAppearance = (short) bluetoothRestoredDevice.mAppearance;
            setManufacturerData(bluetoothRestoredDevice.mManufacturerData);
            this.mBondTimeStamp = bluetoothRestoredDevice.mTimeStamp;
            this.mType = bluetoothRestoredDevice.mLinkType;
            if (bluetoothRestoredDevice.mManufacturerData != null && !Arrays.equals(this.mDevice.semGetManufacturerData(), bluetoothRestoredDevice.mManufacturerData)) {
                this.mDevice.semSetManufacturerData(bluetoothRestoredDevice.mManufacturerData);
            }
            if (ArrayUtils.contains(bluetoothRestoredDevice.mUuids, ParcelUuid.fromString("f8620674-a1ed-41ab-a8b9-de9ad655729d")) && this.mDevice.semGetAutoSwitchMode() == -1) {
                if (Settings.System.semGetIntForUser(context.getContentResolver(), "mcf_permission_denied", 0, UserHandle.semGetMyUserId()) != 1) {
                    this.mDevice.semSetAutoSwitchMode(1);
                    Log.i("CachedBluetoothDevice", "fillSyncedData :: call semSetAutoSwitchMode to enabled");
                } else {
                    this.mDevice.semSetAutoSwitchMode(0);
                    Log.i("CachedBluetoothDevice", "fillSyncedData :: mcf permission denied");
                    BluetoothDump.BtLog("CachedBluetoothDevice -- fillSyncedData :: mcf permission denied");
                }
            }
            this.mIsSynced = true;
            this.mIsRestored = true;
            this.mBondState = 10;
            this.mIsBondingByCached = false;
            updateProfiles(null);
            if (isRing()) {
                this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_RING, false);
            } else if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), bluetoothRestoredDevice.mUuids)) {
                this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH, false);
            } else {
                this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE, false);
            }
            dispatchAttributesChanged$1();
        } else {
            fillRestoredData();
        }
        this.mGroupId = -1;
        initDrawableCache();
    }
}
