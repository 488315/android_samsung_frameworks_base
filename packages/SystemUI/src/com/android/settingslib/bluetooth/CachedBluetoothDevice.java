package com.android.settingslib.bluetooth;

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
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.input.InputManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import android.util.Pair;
import android.view.InputDevice;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.widget.AdaptiveIcon;
import com.android.settingslib.widget.AdaptiveOutlineDrawable;
import com.android.systemui.R;
import com.google.common.util.concurrent.AbstractListeningExecutorService;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.settingslib.bluetooth.BluetoothRestoredDevice;
import com.samsung.android.settingslib.bluetooth.GattProfile;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.SppProfile;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import com.samsung.android.settingslib.bluetooth.scsp.ScspUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onDeviceAttributesChanged();
    }

    static {
        ParcelUuid.fromString("4de17a00-52cb-11e6-bdf4-0800200c9a66");
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) {
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
        }
        inputDevice = null;
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
        int myPid = Process.myPid();
        Log.d("CachedBluetoothDevice", this + " addMemberDevice = " + cachedBluetoothDevice.mDevice.getAnonymizedAddress());
        BluetoothDump.BtLog("CachedBtDev -- addMemberDevice: main = " + this.mDevice.getAnonymizedAddress() + ", member = " + cachedBluetoothDevice.mDevice.getAnonymizedAddress() + " called by PID : " + myPid + " @ " + packageName);
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

    /* JADX WARN: Removed duplicated region for block: B:54:0x00ad A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ae  */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int compareTo(java.lang.Object r10) {
        /*
            Method dump skipped, instructions count: 195
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.compareTo(java.lang.Object):int");
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x009e, code lost:
    
        if (android.text.TextUtils.isEmpty(null) == false) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:5:0x00d6 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void connect$1() {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.connect$1():void");
    }

    public final void connectDevice() {
        boolean z;
        boolean z2;
        boolean z3;
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
                            z2 = false;
                            z3 = this.mProfiles.size() == 1 && this.mProfiles.stream().anyMatch(new Predicate() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda1
                                @Override // java.util.function.Predicate
                                public final boolean test(Object obj) {
                                    CachedBluetoothDevice cachedBluetoothDevice = CachedBluetoothDevice.this;
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
                    if (z3) {
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
                            z2 = bluetoothPan.isTetheringOn();
                        }
                        if (z2) {
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
                    String replaceAll = identityAddress2.replaceAll(":", "");
                    identityAddress2 = replaceAll.substring(0, 6) + "_" + replaceAll.substring(11);
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

    /* JADX WARN: Removed duplicated region for block: B:33:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00f6  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void fillData() {
        /*
            Method dump skipped, instructions count: 326
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.fillData():void");
    }

    public final void fillRestoredData() {
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

    /* JADX WARN: Code restructure failed: missing block: B:113:0x0111, code lost:
    
        if (hasProfile(r7.mProfileManager.mHeadsetProfile) != false) goto L87;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getBtClassDrawable() {
        /*
            Method dump skipped, instructions count: 455
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.getBtClassDrawable():int");
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0167 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:10:0x03ed  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x03f2 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getConnectionSummary() {
        /*
            Method dump skipped, instructions count: 1014
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.getConnectionSummary():java.lang.String");
    }

    public final int getDeviceSide() {
        HearingAidInfo hearingAidInfo = this.mHearingAidInfo;
        if (hearingAidInfo != null) {
            return hearingAidInfo.mSide;
        }
        return -1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00b0, code lost:
    
        r8 = r8.mManufacturerData.mData;
        r3 = r8.mDeviceStatus;
        r8 = r8.mDeviceSubType;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00bb, code lost:
    
        if ((r3 & 128) != 128) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00bf, code lost:
    
        if (r8 < 16) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00c3, code lost:
    
        if (r8 > 31) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c5, code lost:
    
        android.util.Log.i("CachedBluetoothDevice", "Found a SEC Wearable device with new type");
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ce, code lost:
    
        if (r0.isValidStub() == false) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00d0, code lost:
    
        android.util.Log.w("CachedBluetoothDevice", "jump to WM");
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00d5, code lost:
    
        return 2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getDeviceType() {
        /*
            r8 = this;
            android.content.Context r0 = r8.mContext
            boolean r1 = com.android.settingslib.bluetooth.BluetoothUtils.DEBUG
            com.samsung.android.feature.SemFloatingFeature r1 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r2 = "SEC_FLOATING_FEATURE_COMMON_SUPPORT_SAFETYCARE"
            boolean r1 = r1.getBoolean(r2)
            r2 = 0
            if (r1 != 0) goto L2c
            com.samsung.android.feature.SemFloatingFeature r1 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r3 = "SEC_FLOATING_FEATURE_COMMON_SUPPORT_ULTRA_POWER_SAVING"
            boolean r1 = r1.getBoolean(r3)
            if (r1 != 0) goto L2c
            com.samsung.android.feature.SemFloatingFeature r1 = com.samsung.android.feature.SemFloatingFeature.getInstance()
            java.lang.String r3 = "SEC_FLOATING_FEATURE_COMMON_SUPPORT_BATTERY_CONVERSING"
            boolean r1 = r1.getBoolean(r3)
            if (r1 == 0) goto L2a
            goto L2c
        L2a:
            r0 = r2
            goto L37
        L2c:
            com.samsung.android.emergencymode.SemEmergencyManager r1 = com.samsung.android.emergencymode.SemEmergencyManager.getInstance(r0)
            if (r1 != 0) goto L33
            goto L2a
        L33:
            boolean r0 = com.samsung.android.emergencymode.SemEmergencyManager.isEmergencyMode(r0)
        L37:
            java.lang.String r1 = "CachedBluetoothDevice"
            if (r0 == 0) goto L41
            java.lang.String r8 = "getDeviceType: EmergencyMode enabled"
            android.util.Log.d(r1, r8)
            return r2
        L41:
            com.android.settingslib.bluetooth.LocalBluetoothProfileManager r0 = r8.mProfileManager
            if (r0 != 0) goto L4b
            java.lang.String r8 = "getDeviceType: LocalBluetoothProfileManager is null"
            android.util.Log.d(r1, r8)
            return r2
        L4b:
            com.android.settingslib.bluetooth.CachedBluetoothDeviceManager r0 = r0.mDeviceManager
            if (r0 != 0) goto L55
            java.lang.String r8 = "getDeviceType: CachedBluetoothDeviceManager is null"
            android.util.Log.d(r1, r8)
            return r2
        L55:
            int r3 = r8.getBtClassDrawable()
            r4 = 2131233955(0x7f080ca3, float:1.8084062E38)
            r5 = 1
            if (r3 != r4) goto L6d
            java.lang.String r3 = r8.mDeviceName
            if (r3 == 0) goto L6d
            java.lang.String r4 = "GALAXY Gear ("
            boolean r3 = r3.startsWith(r4)
            if (r3 == 0) goto L6d
            r3 = r5
            goto L6e
        L6d:
            r3 = r2
        L6e:
            if (r3 == 0) goto L77
            boolean r8 = r0.isValidStub()
            if (r8 == 0) goto Ld6
            return r5
        L77:
            byte[] r3 = r8.getManufacturerRawData()
            if (r3 == 0) goto Ld6
            com.samsung.android.settingslib.bluetooth.ManufacturerData r3 = r8.mManufacturerData
            int r4 = r3.mManufacturerType
            r6 = 3
            r7 = 2
            if (r4 == r5) goto L8a
            if (r4 == r7) goto L8a
            if (r4 == r6) goto L8a
            goto Ld6
        L8a:
            com.samsung.android.settingslib.bluetooth.ManufacturerData$Data r3 = r3.mData
            byte[] r3 = r3.mDeviceId
            r4 = r3[r2]
            if (r4 != 0) goto La3
            r3 = r3[r5]
            r3 = r3 & 255(0xff, float:3.57E-43)
            if (r3 < r5) goto Lb0
            r4 = 144(0x90, float:2.02E-43)
            if (r3 >= r4) goto Lb0
            boolean r3 = r0.isValidStub()
            if (r3 == 0) goto Lb0
            goto Laf
        La3:
            if (r4 == r5) goto La9
            if (r4 == r7) goto La9
            if (r4 != r6) goto Lb0
        La9:
            boolean r3 = r0.isValidStub()
            if (r3 == 0) goto Lb0
        Laf:
            return r7
        Lb0:
            com.samsung.android.settingslib.bluetooth.ManufacturerData r8 = r8.mManufacturerData
            com.samsung.android.settingslib.bluetooth.ManufacturerData$Data r8 = r8.mData
            byte r3 = r8.mDeviceStatus
            byte r8 = r8.mDeviceSubType
            r4 = 128(0x80, float:1.8E-43)
            r3 = r3 & r4
            if (r3 != r4) goto Ld6
            r3 = 16
            if (r8 < r3) goto Ld6
            r3 = 31
            if (r8 > r3) goto Ld6
            java.lang.String r8 = "Found a SEC Wearable device with new type"
            android.util.Log.i(r1, r8)
            boolean r8 = r0.isValidStub()
            if (r8 == 0) goto Ld6
            java.lang.String r8 = "jump to WM"
            android.util.Log.w(r1, r8)
            return r7
        Ld6:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.getDeviceType():int");
    }

    public final Pair getDrawableWithDescription() {
        byte[] metadata;
        BluetoothDevice bluetoothDevice = this.mDevice;
        boolean z = BluetoothUtils.DEBUG;
        String str = (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(5)) == null) ? null : new String(metadata);
        Uri parse = str != null ? Uri.parse(str) : null;
        Pair btClassDrawableWithDescription = BluetoothUtils.getBtClassDrawableWithDescription(this.mContext, this);
        if (BluetoothUtils.isAdvancedDetailsHeader(this.mDevice) && parse != null) {
            BitmapDrawable bitmapDrawable = this.mDrawableCache.get(parse.toString());
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
        int hashCode = i != -1 ? new Integer(i).hashCode() : this.mDevice.getAddress().hashCode();
        Drawable drawable = (Drawable) btDrawableWithDescription.first;
        Resources resources2 = context.getResources();
        int[] intArray = resources2.getIntArray(R.array.bt_icon_fg_colors);
        int[] intArray2 = resources2.getIntArray(R.array.bt_icon_bg_colors);
        int abs = Math.abs(hashCode % intArray2.length);
        drawable.setTint(intArray[abs]);
        AdaptiveIcon adaptiveIcon = new AdaptiveIcon(context, drawable);
        adaptiveIcon.setBackgroundColor(intArray2[abs]);
        return new Pair(adaptiveIcon, (String) btDrawableWithDescription.second);
    }

    public final int getHearingAidSideBattery(final int i) {
        Optional findAny = Stream.concat(Stream.of((Object[]) new CachedBluetoothDevice[]{this, this.mSubDevice}), this.mMemberDevices.stream()).filter(new CachedBluetoothDevice$$ExternalSyntheticLambda0(1)).filter(new Predicate() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda12
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                int i2 = i;
                int i3 = CachedBluetoothDevice.$r8$clinit;
                return ((CachedBluetoothDevice) obj).getDeviceSide() == i2;
            }
        }).filter(new CachedBluetoothDevice$$ExternalSyntheticLambda0(2)).findAny();
        if (findAny.isPresent()) {
            return ((Integer) findAny.map(new CachedBluetoothDevice$$ExternalSyntheticLambda6()).filter(new CachedBluetoothDevice$$ExternalSyntheticLambda0(5)).orElse(-1)).intValue();
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

    public final Drawable getIconDrawable(boolean z) {
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

    /* JADX WARN: Code restructure failed: missing block: B:26:0x004d, code lost:
    
        if (r2.this$0.isBuds3Device() != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.graphics.drawable.Drawable getIconDrawableForSolid() {
        /*
            r8 = this;
            r0 = 0
            java.lang.String r1 = r8.getResourcePath(r0)
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 == 0) goto L14
            java.lang.String r1 = "CachedBluetoothDevice"
            java.lang.String r2 = "getLocalIconResourceForSolid: path is null"
            android.util.Log.d(r1, r2)
            r1 = 0
            goto L1a
        L14:
            android.content.Context r2 = r8.mContext
            android.graphics.drawable.BitmapDrawable r1 = com.samsung.android.settingslib.bluetooth.scsp.ScspUtils.getListIcon(r2, r1)
        L1a:
            if (r1 != 0) goto L67
            com.samsung.android.settingslib.bluetooth.ManufacturerData r2 = r8.mManufacturerData
            if (r2 == 0) goto L67
            int r3 = r2.mManufacturerType
            r4 = 3
            r5 = 2
            if (r3 == r5) goto L29
            if (r3 == r4) goto L29
            goto L5a
        L29:
            com.samsung.android.settingslib.bluetooth.ManufacturerData$Data r3 = r2.mData
            byte r6 = r3.mDeviceCategory
            byte r3 = r3.mDeviceIconIndex
            com.samsung.android.settingslib.bluetooth.ManufacturerData$SSdevice r2 = r2.mSSdevice
            r2.getClass()
            r7 = 21
            if (r6 == r7) goto L39
            goto L5a
        L39:
            if (r3 == r5) goto L57
            if (r3 == r4) goto L47
            r0 = 4
            if (r3 == r0) goto L53
            r0 = 5
            if (r3 == r0) goto L4f
            r0 = 2131233908(0x7f080c74, float:1.8083967E38)
            goto L5a
        L47:
            com.samsung.android.settingslib.bluetooth.ManufacturerData r0 = com.samsung.android.settingslib.bluetooth.ManufacturerData.this
            boolean r0 = r0.isBuds3Device()
            if (r0 == 0) goto L53
        L4f:
            r0 = 2131236208(0x7f081570, float:1.8088632E38)
            goto L5a
        L53:
            r0 = 2131236207(0x7f08156f, float:1.808863E38)
            goto L5a
        L57:
            r0 = 2131233921(0x7f080c81, float:1.8083993E38)
        L5a:
            if (r0 == 0) goto L67
            android.content.Context r8 = r8.mContext
            android.content.res.Resources r8 = r8.getResources()
            android.graphics.drawable.Drawable r8 = r8.getDrawable(r0)
            return r8
        L67:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.getIconDrawableForSolid():android.graphics.drawable.Drawable");
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

    /* JADX WARN: Code restructure failed: missing block: B:55:0x00f7, code lost:
    
        r2 = r0[1].trim();
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00fd, code lost:
    
        r10.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0101, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0102, code lost:
    
        android.util.Log.w("ScspUtils", "getMappingKey: Exception.", r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0142  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0137 A[Catch: Exception -> 0x0111, TRY_ENTER, TRY_LEAVE, TryCatch #6 {Exception -> 0x0111, blocks: (B:67:0x010d, B:73:0x0137), top: B:19:0x0092 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x01bb A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getResourcePath(boolean r18) {
        /*
            Method dump skipped, instructions count: 602
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.getResourcePath(boolean):java.lang.String");
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
                byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
                if (semGetMetadata == null || semGetMetadata.length < 5) {
                    Log.e("CachedBluetoothDevice", "isSupportAssistant: DataPacket is too short.");
                    return false;
                }
                if ((((semGetMetadata[0] & 255) | ((semGetMetadata[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL) == smepTag.getTag()) {
                    int i = 2;
                    while (i < semGetMetadata.length) {
                        int i2 = ((semGetMetadata[i] & 255) | ((semGetMetadata[i + 1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL;
                        int i3 = semGetMetadata[i + 2] & 255;
                        byte[] bArr = new byte[i3];
                        System.arraycopy(semGetMetadata, i + 3, bArr, 0, i3);
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
        ListenableFuture submit = ((AbstractListeningExecutorService) ThreadUtils.getBackgroundExecutor()).submit(new Callable() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda2
            @Override // java.util.concurrent.Callable
            public final Object call() {
                byte[] metadata;
                CachedBluetoothDevice cachedBluetoothDevice = CachedBluetoothDevice.this;
                if (BluetoothUtils.isAdvancedDetailsHeader(cachedBluetoothDevice.mDevice)) {
                    BluetoothDevice bluetoothDevice = cachedBluetoothDevice.mDevice;
                    String str = (bluetoothDevice == null || (metadata = bluetoothDevice.getMetadata(5)) == null) ? null : new String(metadata);
                    Uri parse = str == null ? null : Uri.parse(str);
                    if (parse != null && cachedBluetoothDevice.mDrawableCache.get(parse.toString()) == null) {
                        cachedBluetoothDevice.mDrawableCache.put(parse.toString(), (BitmapDrawable) BluetoothUtils.getBtDrawableWithDescription(cachedBluetoothDevice.mContext, cachedBluetoothDevice).first);
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
        submit.addListener(new Futures.CallbackListener(submit, futureCallback), this.mContext.getMainExecutor());
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
        byte[] bArr;
        if (TextUtils.isEmpty(str) || TextUtils.equals(str, getName())) {
            return;
        }
        BluetoothDevice bluetoothDevice = this.mDevice;
        int tag = SmepTag.FEATURE_CHANGE_DEVICE_NAME.getTag();
        byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
        if (semGetMetadata == null || semGetMetadata.length == 0) {
            Log.d("CachedBluetoothDevice", "FEATURE_CHANGE_DEVICE_NAME = null");
        } else {
            Log.d("CachedBluetoothDevice", "FEATURE_CHANGE_DEVICE_NAME = " + Arrays.toString(semGetMetadata));
        }
        if (semGetMetadata == null || semGetMetadata.length <= 3 || (b = semGetMetadata[3]) <= 0 || b == -1) {
            this.mDevice.setAlias(str);
        } else {
            int tag2 = SmepTag.CMD_PERSONALIZED_NAME_VALUE.getTag();
            byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
            if (!SmepTag.isValidConstantKey(tag2) || bytes == null || bytes.length == 0) {
                bArr = null;
            } else {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                try {
                    byteArrayOutputStream.write(new byte[]{(byte) tag2, (byte) (tag2 >> 8)});
                    byteArrayOutputStream.write((byte) bytes.length);
                    byteArrayOutputStream.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bArr = byteArrayOutputStream.toByteArray();
            }
            this.mDevice.semSetMetadata(bArr);
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

    /* JADX WARN: Code restructure failed: missing block: B:30:0x006b, code lost:
    
        android.util.Log.d("CachedBluetoothDevice", "PAN connection exists. Restore PAN profile.");
        r6.mRemovedProfiles.remove(r1);
        r7 = r6.mProfileLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0079, code lost:
    
        monitor-enter(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x007a, code lost:
    
        r6.mProfiles.add(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x007f, code lost:
    
        monitor-exit(r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final synchronized boolean updateProfiles(android.os.ParcelUuid[] r8) {
        /*
            Method dump skipped, instructions count: 218
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.updateProfiles(android.os.ParcelUuid[]):boolean");
    }

    public void setInputDevice(InputDevice inputDevice) {
    }

    public void setLocalBluetoothManager(LocalBluetoothManager localBluetoothManager) {
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00e4  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public CachedBluetoothDevice(android.content.Context r5, com.android.settingslib.bluetooth.LocalBluetoothProfileManager r6, android.bluetooth.BluetoothDevice r7, android.content.Intent r8) {
        /*
            Method dump skipped, instructions count: 294
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.bluetooth.CachedBluetoothDevice.<init>(android.content.Context, com.android.settingslib.bluetooth.LocalBluetoothProfileManager, android.bluetooth.BluetoothDevice, android.content.Intent):void");
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothRestoredDevice bluetoothRestoredDevice) {
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

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothRestoredDevice bluetoothRestoredDevice, boolean z) {
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
