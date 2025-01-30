package com.android.settingslib.bluetooth;

import android.app.StatusBarManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothDump;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.ParcelUuid;
import android.os.Process;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import androidx.core.graphics.drawable.IconCompat$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.ArrayUtils;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.bluetooth.SmepTag;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.settingslib.bluetooth.BluetoothRestoredDevice;
import com.samsung.android.settingslib.bluetooth.ManufacturerData;
import com.samsung.android.settingslib.bluetooth.SppProfile;
import com.samsung.android.settingslib.bluetooth.detector.BluetoothRetryDetector;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CachedBluetoothDevice implements Comparable {
    public final String mAddress;
    public short mAppearance;
    public String mBluetoothCastMsg;
    public int mBondState;
    public long mBondTimeStamp;
    public BluetoothRetryDetector mBondingDetector;
    public BluetoothClass mBtClass;
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
    public final ArrayList mOnlyPANUDevices;
    public String mPrefixName;
    public HashMap mProfileConnectionState;
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
    public int mVersion;
    public boolean mVisible;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.settingslib.bluetooth.CachedBluetoothDevice$2 */
    public abstract /* synthetic */ class AbstractC09172 {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onDeviceAttributesChanged();
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) {
        this.mVersion = 0;
        this.mType = 0;
        this.mOnlyPANUDevices = new ArrayList();
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mContext = context;
        this.mLocalAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileConnectionState = new HashMap();
        this.mProfileManager = localBluetoothProfileManager;
        this.mDevice = bluetoothDevice;
        this.mAddress = bluetoothDevice.getAddress();
        fillData();
        this.mGroupId = -1;
        initDrawableCache();
        this.mUnpairing = false;
    }

    public static String deviceTypeToString(int i) {
        return i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? "UNKNOWN" : "WEARABLE" : "WEARABLE_CONNECT" : "GEAR" : "GEAR1" : "GENERIC";
    }

    public final void addMemberDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        Log.d("CachedBluetoothDevice", this + " addMemberDevice = " + cachedBluetoothDevice.mDevice.getAddressForLogging());
        BluetoothDump.BtLog("CachedBtDev -- addMemberDevice: main = " + this.mDevice.getAddressForLogging() + ", member = " + cachedBluetoothDevice.mDevice.getAddressForLogging());
        if (this.mMemberDevices.contains(cachedBluetoothDevice)) {
            BluetoothDump.BtLog("CachedBtDev -- addMemberDevice: contains already");
            return;
        }
        this.mMemberDevices.add(cachedBluetoothDevice);
        this.mLeadDevice = null;
        cachedBluetoothDevice.mLeadDevice = this;
    }

    public final boolean checkHearingAidByUuid() {
        return ArrayUtils.contains(this.mDevice.getUuids(), BluetoothUuid.HEARING_AID) || ArrayUtils.contains(this.mDevice.getLeService16BitsUuidData(), BluetoothUuid.HEARING_AID) || ArrayUtils.contains(this.mDevice.getLeComplete128BitsUuidData(), ParcelUuid.fromString("7d74f4bd-c74a-4431-862c-cce884371592"));
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

    public final void clearProfileConnectionState() {
        Log.d("CachedBluetoothDevice", " Clearing all connection state for dev:" + getNameForLog());
        Iterator it = getProfiles().iterator();
        while (it.hasNext()) {
            this.mProfileConnectionState.put((LocalBluetoothProfile) it.next(), 0);
        }
        this.mBluetoothCastMsg = null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0086, code lost:
    
        if (r0 < 0) goto L16;
     */
    @Override // java.lang.Comparable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int compareTo(Object obj) {
        int i;
        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj;
        int maxConnectionState = cachedBluetoothDevice.getMaxConnectionState();
        int maxConnectionState2 = getMaxConnectionState();
        int i2 = ((!this.mIsRestored || this.mIsSynced) ? 0 : 1) - ((!cachedBluetoothDevice.mIsRestored || cachedBluetoothDevice.mIsSynced) ? 0 : 1);
        if (i2 == 0) {
            i2 = (this.mIsSynced ? 1 : 0) - (cachedBluetoothDevice.mIsSynced ? 1 : 0);
            if (i2 == 0) {
                int i3 = (maxConnectionState == 2 ? 1 : 0) - (maxConnectionState2 == 2 ? 1 : 0);
                if (i3 != 0) {
                    return i3;
                }
                int i4 = cachedBluetoothDevice.mBondState == 12 ? 1 : 0;
                int i5 = this.mBondState;
                i2 = i4 - (i5 == 12 ? 1 : 0);
                if (i2 == 0) {
                    i2 = -1;
                    if (i5 == 12) {
                        i = ((maxConnectionState == 1 || maxConnectionState == 3) ? 1 : 0) - ((maxConnectionState2 == 1 || maxConnectionState2 == 3) ? 1 : 0);
                        if (i == 0) {
                            i = (cachedBluetoothDevice.mIsHearingAidDeviceByUUID ? 1 : 0) - (this.mIsHearingAidDeviceByUUID ? 1 : 0);
                            if (i == 0) {
                                long connectionTimeStamp = cachedBluetoothDevice.mDevice.getConnectionTimeStamp() - this.mDevice.getConnectionTimeStamp();
                                if (connectionTimeStamp > 0) {
                                    return 1;
                                }
                            }
                        }
                        return i;
                    }
                    i = (cachedBluetoothDevice.mIsHearingAidDeviceByUUID ? 1 : 0) - (this.mIsHearingAidDeviceByUUID ? 1 : 0);
                    if (i == 0 && (i = cachedBluetoothDevice.mRssiGroup - this.mRssiGroup) == 0) {
                        long j = cachedBluetoothDevice.mBondTimeStamp - this.mBondTimeStamp;
                        if (j > 0) {
                            return 1;
                        }
                        if (j >= 0) {
                            int i6 = this.mSequence - cachedBluetoothDevice.mSequence;
                            if (i6 != 0) {
                                return i6;
                            }
                            int i7 = (cachedBluetoothDevice.mJustDiscovered ? 1 : 0) - (this.mJustDiscovered ? 1 : 0);
                            if (i7 != 0) {
                                return i7;
                            }
                            int i8 = cachedBluetoothDevice.mRssi - this.mRssi;
                            return i8 != 0 ? i8 : getName().compareTo(cachedBluetoothDevice.getName());
                        }
                    }
                    return i;
                }
            }
        }
        return i2;
    }

    public final void connect$1() {
        if (shouldLaunchGM(this.mContext.getPackageName(), false)) {
            return;
        }
        secConnect();
    }

    public final void connectDevice() {
        synchronized (this.mProfileLock) {
            if (getProfiles().isEmpty()) {
                Log.d("CachedBluetoothDevice", "No profiles. Maybe we will connect later for device " + this.mDevice);
                return;
            }
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null && hasProfile(localBluetoothProfileManager.mLeAudioProfile)) {
                this.mConnectAttempted = 0L;
            }
            Log.d("CachedBluetoothDevice", "connect " + this);
            this.mDevice.connect();
            if (this.mGroupId != -1) {
                for (CachedBluetoothDevice cachedBluetoothDevice : this.mMemberDevices) {
                    if (cachedBluetoothDevice.mBondState == 12) {
                        Log.d("CachedBluetoothDevice", "connect the member(" + cachedBluetoothDevice.getAddress() + ")");
                        cachedBluetoothDevice.mDevice.connect();
                    }
                }
            }
        }
    }

    public final String describe(LocalBluetoothProfile localBluetoothProfile) {
        StringBuilder sb = new StringBuilder();
        if (BluetoothUtils.DEBUG) {
            sb.append("Address:");
            sb.append(this.mDevice);
        }
        if (localBluetoothProfile != null) {
            sb.append(" Profile:");
            sb.append(localBluetoothProfile);
        }
        return sb.toString();
    }

    public final String describeDetail() {
        StringBuilder sb = new StringBuilder();
        String identityAddress = this.mDevice.getIdentityAddress();
        if (TextUtils.isEmpty(identityAddress)) {
            identityAddress = getAddress();
        }
        if (TextUtils.isEmpty(identityAddress) || !identityAddress.equals(this.mAddress)) {
            StringBuilder sb2 = new StringBuilder("[");
            sb2.append(this.mDevice.getAddressForLogging());
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
            sb.append("[" + this.mDevice.getAddressForLogging() + "]");
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
        BluetoothDevice bluetoothDevice = this.mDevice;
        if (bluetoothDevice.semGetAutoSwitchMode() != -1) {
            Intent intent = new Intent("com.samsung.android.mcfds.autoswitch.BUDS_DISCONNECTED_BY_SETTINGS");
            intent.putExtra("android.bluetooth.device.extra.DEVICE", bluetoothDevice);
            this.mContext.sendBroadcast(intent, "android.permission.BLUETOOTH_PRIVILEGED");
        }
        synchronized (this.mProfileLock) {
            Iterator it = this.mProfiles.iterator();
            z = false;
            z2 = false;
            while (it.hasNext()) {
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                if (getProfileConnectionState(localBluetoothProfile) == 2) {
                    if (localBluetoothProfile instanceof SppProfile) {
                        z = true;
                    } else {
                        z2 = true;
                    }
                }
            }
        }
        if (z && !z2) {
            Log.d("CachedBluetoothDevice", "disconnect :: Connected SPP only. It will launch GM");
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null) {
                localBluetoothProfileManager.mSppProfile.setEnabled(this.mDevice);
                return;
            }
            return;
        }
        String name = getName();
        if (BluetoothUtils.isRTL(this.mContext)) {
            name = PathParser$$ExternalSyntheticOutline0.m29m("\u200e", name, "\u200e");
        }
        BluetoothUtils.showToast(this.mContext, this.mContext.getString(R.string.bluetooth_disconnect_message, name));
        synchronized (this.mProfileLock) {
            if (this.mGroupId != -1) {
                Iterator it2 = ((HashSet) this.mMemberDevices).iterator();
                while (it2.hasNext()) {
                    CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it2.next();
                    Log.d("CachedBluetoothDevice", "Disconnect the member:" + cachedBluetoothDevice);
                    cachedBluetoothDevice.disconnect();
                }
            }
            Log.d("CachedBluetoothDevice", "Disconnect " + this);
            this.mDevice.disconnect();
        }
        LocalBluetoothProfileManager localBluetoothProfileManager2 = this.mProfileManager;
        if (localBluetoothProfileManager2 == null || (pbapServerProfile = localBluetoothProfileManager2.mPbapProfile) == null) {
            return;
        }
        if (getProfileConnectionState(pbapServerProfile) == 2) {
            pbapServerProfile.setEnabled(this.mDevice);
        }
    }

    public final void dispatchAttributesChanged() {
        Iterator it = ((CopyOnWriteArrayList) this.mCallbacks).iterator();
        while (it.hasNext()) {
            ((Callback) it.next()).onDeviceAttributesChanged();
        }
        synchronized (this.mSemCallbacks) {
            Iterator it2 = ((ArrayList) this.mSemCallbacks).iterator();
            if (it2.hasNext()) {
                ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(it2.next());
                throw null;
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
        BluetoothDevice bluetoothDevice;
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null) {
            A2dpProfile a2dpProfile = localBluetoothProfileManager.mA2dpProfile;
            if (a2dpProfile != null) {
                this.mIsActiveDeviceA2dp = this.mDevice.equals(a2dpProfile.getActiveDevice());
            }
            HeadsetProfile headsetProfile = this.mProfileManager.mHeadsetProfile;
            if (headsetProfile != null) {
                BluetoothDevice bluetoothDevice2 = this.mDevice;
                BluetoothAdapter bluetoothAdapter = headsetProfile.mBluetoothAdapter;
                if (bluetoothAdapter != null) {
                    List activeDevices = bluetoothAdapter.getActiveDevices(1);
                    if (activeDevices.size() > 0) {
                        bluetoothDevice = (BluetoothDevice) activeDevices.get(0);
                        this.mIsActiveDeviceHeadset = bluetoothDevice2.equals(bluetoothDevice);
                    }
                }
                bluetoothDevice = null;
                this.mIsActiveDeviceHeadset = bluetoothDevice2.equals(bluetoothDevice);
            }
            HearingAidProfile hearingAidProfile = this.mProfileManager.mHearingAidProfile;
            if (hearingAidProfile != null) {
                BluetoothAdapter bluetoothAdapter2 = hearingAidProfile.mBluetoothAdapter;
                this.mIsActiveDeviceHearingAid = (bluetoothAdapter2 == null ? new ArrayList() : bluetoothAdapter2.getActiveDevices(21)).contains(this.mDevice);
            }
            LeAudioProfile leAudioProfile = this.mProfileManager.mLeAudioProfile;
            if (leAudioProfile != null) {
                this.mIsActiveDeviceLeAudio = leAudioProfile.getActiveDevices().contains(this.mDevice);
            }
        }
    }

    public final void fetchManufacturerData(byte[] bArr) {
        setManufacturerData(bArr);
        if (BluetoothUtils.DEBUG) {
            Log.d("CachedBluetoothDevice", "fetchManufacturerData : " + Arrays.toString(getManufacturerRawData()));
        }
    }

    public final void fetchName$1() {
        String name = this.mDevice.getName();
        String alias = this.mDevice.getAlias();
        if (TextUtils.isEmpty(name)) {
            this.mDeviceName = this.mDevice.getAddress();
            if (BluetoothUtils.DEBUG) {
                ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Device has no Device name (yet), use address: "), this.mDeviceName, "CachedBluetoothDevice");
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
            ExifInterface$$ExternalSyntheticOutline0.m35m(new StringBuilder("Device has no name (yet), use address: "), this.mName, "CachedBluetoothDevice");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0104  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void fillData() {
        fetchName$1();
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
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(this.mDevice.getAddress());
            edit.commit();
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
            SharedPreferences.Editor edit2 = sharedPreferences2.edit();
            edit2.remove(this.mDevice.getAddress());
            edit2.commit();
        }
        this.mAppearance = (short) this.mDevice.semGetAppearance();
        fetchManufacturerData(this.mDevice.semGetManufacturerData());
        if (getManufacturerRawData() != null) {
            ManufacturerData manufacturerData = this.mManufacturerData;
            if (manufacturerData.mManufacturerType == 2 && manufacturerData.mData.mDeviceCategory == 2) {
                this.mIsTablet = true;
                this.mBondState = this.mDevice.getBondState();
                this.mType = this.mDevice.getType();
                this.mVisible = true;
                this.mIsBondingByCached = false;
                if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), this.mDevice.getUuids())) {
                    this.mBondingDetector = this.mBondState == 12 ? new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE, false) : new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.PAIRING_FAILURE, false);
                } else {
                    this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH, false);
                }
                Log.d("CachedBluetoothDevice", "fillData :: " + describeDetail());
                dispatchAttributesChanged();
            }
        }
        this.mIsTablet = false;
        this.mBondState = this.mDevice.getBondState();
        this.mType = this.mDevice.getType();
        this.mVisible = true;
        this.mIsBondingByCached = false;
        if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), this.mDevice.getUuids())) {
        }
        Log.d("CachedBluetoothDevice", "fillData :: " + describeDetail());
        dispatchAttributesChanged();
    }

    public final void fillRestoredData() {
        if (TextUtils.isEmpty(this.mRestoredDevice.mName)) {
            fetchName$1();
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
        this.mBondingDetector = BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), this.mRestoredDevice.mUuids) ? new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH, false) : new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE, false);
    }

    public final String getAddress() {
        return this.mDevice.getAddress();
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
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, this.mAppearance, "CachedBluetoothDevice");
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
                if (localBluetoothProfileManager != null && hasProfile(localBluetoothProfileManager.mA2dpProfile) && hasProfile(this.mProfileManager.mHeadsetProfile)) {
                    return R.drawable.list_ic_sound_accessory_default;
                }
            } else {
                if (majorDeviceClass == 1280) {
                    return HidProfile.getHidClassDrawable(this.mBtClass);
                }
                if (majorDeviceClass == 1536) {
                    return (this.mBtClass.getDeviceClass() == 1664 || this.mBtClass.getDeviceClass() == 1600) ? R.drawable.list_ic_printer : R.drawable.list_ic_camera;
                }
                if (majorDeviceClass == 1792 && this.mBtClass.getDeviceClass() == 1796) {
                    return upperCase != null ? (upperCase.startsWith("GEAR FIT") || upperCase.startsWith("GALAXY FIT")) ? R.drawable.list_ic_band : R.drawable.list_ic_wearable : R.drawable.list_ic_wearable;
                }
            }
            int appearanceDrawable = getAppearanceDrawable(this.mAppearance);
            if (appearanceDrawable != 0) {
                return appearanceDrawable;
            }
            if (this.mBtClass.doesClassMatch(1)) {
                return R.drawable.list_ic_sound_accessory_default;
            }
            if (this.mBtClass.doesClassMatch(0)) {
                return R.drawable.list_ic_mono_headset;
            }
        } else {
            short s = this.mAppearance;
            if (s != 0) {
                int appearanceDrawable2 = getAppearanceDrawable(s);
                if (appearanceDrawable2 != 0) {
                    return appearanceDrawable2;
                }
            } else {
                Log.w("CachedBluetoothDevice", "mBtClass is null");
            }
        }
        LocalBluetoothProfileManager localBluetoothProfileManager2 = this.mProfileManager;
        if (localBluetoothProfileManager2 != null && hasProfile(localBluetoothProfileManager2.mA2dpProfile) && hasProfile(this.mProfileManager.mHeadsetProfile)) {
            Integer num = 1056;
            setBtClass(new BluetoothClass(num.intValue()));
            return R.drawable.list_ic_sound_accessory_default;
        }
        List<LocalBluetoothProfile> profiles = getProfiles();
        for (LocalBluetoothProfile localBluetoothProfile : profiles) {
            for (int i2 = 0; i2 < profiles.size(); i2++) {
                if (profiles.get(i2) instanceof A2dpProfile) {
                    Integer num2 = 1048;
                    setBtClass(new BluetoothClass(num2.intValue()));
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

    public final List getConnectableProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mProfileLock) {
            Iterator it = this.mProfiles.iterator();
            while (it.hasNext()) {
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                if (localBluetoothProfile.accessProfileEnabled()) {
                    arrayList.add(localBluetoothProfile);
                }
            }
        }
        return arrayList;
    }

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
            this.mVersion = cachedBluetoothDeviceManager.getStubVersion("com.samsung.android.app.watchmanagerstub");
            if (cachedBluetoothDeviceManager.isValidStub("com.samsung.android.app.watchmanagerstub")) {
                return 1;
            }
        } else {
            if (getManufacturerRawData() == null || !((i = (manufacturerData = this.mManufacturerData).mManufacturerType) == 1 || i == 2 || i == 3)) {
                return 0;
            }
            byte[] bArr = manufacturerData.mData.mDeviceId;
            byte b = bArr[0];
            if (b == 0) {
                int i2 = bArr[1] & 255;
                if (i2 >= 144 && i2 <= 255) {
                    this.mVersion = cachedBluetoothDeviceManager.getStubVersion("com.sec.android.app.applinker");
                    if (cachedBluetoothDeviceManager.isValidStub("com.sec.android.app.applinker")) {
                        return this.mManufacturerData.mData.mBluetoothType == 1 ? 3 : 4;
                    }
                } else if (i2 >= 1) {
                    this.mVersion = cachedBluetoothDeviceManager.getStubVersion("com.samsung.android.app.watchmanagerstub");
                    if (cachedBluetoothDeviceManager.isValidStub("com.samsung.android.app.watchmanagerstub")) {
                        return 2;
                    }
                }
            } else if (b == 1 || b == 2 || b == 3) {
                this.mVersion = cachedBluetoothDeviceManager.getStubVersion("com.samsung.android.app.watchmanagerstub");
                if (cachedBluetoothDeviceManager.isValidStub("com.samsung.android.app.watchmanagerstub")) {
                    return 2;
                }
            }
        }
        return 0;
    }

    public final long getHiSyncId() {
        HearingAidInfo hearingAidInfo = this.mHearingAidInfo;
        if (hearingAidInfo != null) {
            return hearingAidInfo.mHiSyncId;
        }
        return 0L;
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00ca  */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable getIconDrawable() {
        boolean z;
        Resources resources = this.mContext.getResources();
        Drawable drawable = resources.getDrawable(getBtClassDrawable());
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null && hasProfile(localBluetoothProfileManager.mLeAudioBroadcastAssistant)) {
            z = true;
            if (isHearableUsingWearableManager()) {
                BluetoothDevice bluetoothDevice = this.mDevice;
                int tag = SmepTag.SUPPORTED_FEATURES.getTag();
                int i = 2;
                byte[] semGetMetadata = bluetoothDevice.semGetMetadata(new byte[]{(byte) tag, (byte) (tag >> 8)});
                if (semGetMetadata == null || semGetMetadata.length < 5) {
                    Log.e("CachedBluetoothDevice", "isSupportAssistant: DataPacket is too short.");
                } else if ((((semGetMetadata[0] & 255) | ((semGetMetadata[1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL) == SmepTag.SUPPORTED_FEATURES.getTag()) {
                    while (true) {
                        if (i >= semGetMetadata.length) {
                            break;
                        }
                        int i2 = ((semGetMetadata[i] & 255) | ((semGetMetadata[i + 1] & 255) << 8)) & CustomDeviceManager.QUICK_PANEL_ALL;
                        int i3 = semGetMetadata[i + 2] & 255;
                        byte[] bArr = new byte[i3];
                        System.arraycopy(semGetMetadata, i + 3, bArr, 0, i3);
                        i += i3 + 3;
                        if (AbstractC09172.$SwitchMap$com$samsung$android$bluetooth$SmepTag[SmepTag.getSmepKey(i2).ordinal()] != 1) {
                            Log.e("CachedBluetoothDevice", "isSupportAssistant: NOT SUPPORTED FEATURE : " + String.format("%x", Integer.valueOf(i2)));
                        } else {
                            if (i3 < 1) {
                                Log.d("CachedBluetoothDevice", "isSupportAssistant: wrong data");
                                break;
                            }
                            RecyclerView$$ExternalSyntheticOutline0.m46m(new StringBuilder("isSupportAssistant: data = "), bArr[0], "CachedBluetoothDevice");
                            if (bArr[0] == 1) {
                                break;
                            }
                        }
                    }
                }
            }
            if (z) {
                return drawable;
            }
            ManufacturerData manufacturerData = this.mManufacturerData;
            int deviceIcon = manufacturerData != null ? manufacturerData.getDeviceIcon() : 0;
            return (deviceIcon == 0 || deviceIcon != R.drawable.list_ic_earbuds_stem) ? BluetoothUtils.getOverlayIconTintableDrawable(drawable, this.mContext, R.drawable.auracast_ic_overlay, R.drawable.auracast_ic_tintable) : resources.getDrawable(R.drawable.list_ic_earbuds_stem_auracast);
        }
        z = false;
        if (z) {
        }
    }

    public final byte[] getManufacturerRawData() {
        ManufacturerData manufacturerData = this.mManufacturerData;
        if (manufacturerData == null) {
            return null;
        }
        return manufacturerData.mManufacturerRawData;
    }

    public final int getMaxConnectionState() {
        int i;
        int profileConnectionState;
        synchronized (this.mProfileLock) {
            i = 0;
            for (LocalBluetoothProfile localBluetoothProfile : getProfiles()) {
                if (localBluetoothProfile != null && !(localBluetoothProfile instanceof CsipSetCoordinatorProfile) && (profileConnectionState = getProfileConnectionState(localBluetoothProfile)) > i) {
                    i = profileConnectionState;
                }
            }
        }
        return i;
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
        if (localBluetoothProfile == null) {
            Log.e("CachedBluetoothDevice", "getProfileConnectionState :: profile is null");
            return 0;
        }
        if (this.mProfileConnectionState == null) {
            this.mProfileConnectionState = new HashMap();
        }
        synchronized (this.mProfileLock) {
            if (!this.mProfiles.contains(localBluetoothProfile)) {
                Log.e("CachedBluetoothDevice", "getProfileConnectionState :: not support profile = " + localBluetoothProfile);
                return 0;
            }
            if (this.mProfileConnectionState.get(localBluetoothProfile) != null) {
                return ((Integer) this.mProfileConnectionState.get(localBluetoothProfile)).intValue();
            }
            int connectionStatus = localBluetoothProfile.getConnectionStatus(this.mDevice);
            Log.d("CachedBluetoothDevice", "getProfileConnectionState :: " + localBluetoothProfile + ", state : " + connectionStatus);
            this.mProfileConnectionState.put(localBluetoothProfile, Integer.valueOf(connectionStatus));
            return connectionStatus;
        }
    }

    public final List getProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mProfileLock) {
            arrayList.addAll(this.mProfiles);
        }
        return Collections.unmodifiableList(arrayList);
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
        IconCompat$$ExternalSyntheticOutline0.m30m("getActiveDevice: unknown profile ", i, "CachedBluetoothDevice");
        return false;
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
            Iterator it = this.mProfiles.iterator();
            while (it.hasNext()) {
                if (getProfileConnectionState((LocalBluetoothProfile) it.next()) == 2) {
                    return true;
                }
            }
            return false;
        }
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

    public final void onCastProfileStateChanged(SemBluetoothCastDevice semBluetoothCastDevice, int i) {
        if (i == 2) {
            this.mBluetoothCastMsg = this.mContext.getString(R.string.bluetooth_cast_shared_with, semBluetoothCastDevice.getPeerName());
        } else {
            this.mBluetoothCastMsg = null;
        }
        refresh();
    }

    public final void onProfileStateChanged(LocalBluetoothProfile localBluetoothProfile, int i) {
        BluetoothRetryDetector bluetoothRetryDetector;
        boolean z = BluetoothUtils.DEBUG;
        if (z) {
            Log.d("CachedBluetoothDevice", "onProfileStateChanged: profile " + localBluetoothProfile + ", device=" + this.mDevice + ", newProfileState " + i);
        }
        if (this.mLocalAdapter.getState() == 13) {
            if (z) {
                Log.d("CachedBluetoothDevice", " BT Turninig Off...Profile conn state change ignored...");
                return;
            }
            return;
        }
        if (this.mDevice != null && (i == 2 || i == 0)) {
            AuditLog.log(5, 1, true, Process.myPid(), "CachedBluetoothDevice", String.format(i == 2 ? "Bluetooth profile %s on bluetooth device %s has connected." : "Bluetooth profile %s on bluetooth device %s has disconnected.", localBluetoothProfile, this.mDevice.getAddress()), "");
        }
        this.mProfileConnectionState.put(localBluetoothProfile, Integer.valueOf(i));
        synchronized (this.mProfileLock) {
            boolean z2 = false;
            if (i == 2) {
                if (!this.mProfiles.contains(localBluetoothProfile)) {
                    this.mRemovedProfiles.remove(localBluetoothProfile);
                    this.mProfiles.add(localBluetoothProfile);
                    if (localBluetoothProfile instanceof PanProfile) {
                        BluetoothDevice bluetoothDevice = this.mDevice;
                        HashMap hashMap = ((PanProfile) localBluetoothProfile).mDeviceRoleMap;
                        if (hashMap.containsKey(bluetoothDevice) && ((Integer) hashMap.get(bluetoothDevice)).intValue() == 1) {
                            z2 = true;
                        }
                        if (z2) {
                            this.mLocalNapRoleConnected = true;
                            this.mOnlyPANUDevices.add(this.mDevice);
                        }
                    }
                }
                CachedBluetoothDevice cachedBluetoothDevice = this.mLeadDevice;
                if (cachedBluetoothDevice != null && !(localBluetoothProfile instanceof CsipSetCoordinatorProfile) && !(localBluetoothProfile instanceof VolumeControlProfile) && (bluetoothRetryDetector = cachedBluetoothDevice.mBondingDetector) != null && bluetoothRetryDetector.mIsForRestored) {
                    bluetoothRetryDetector.mRestoredDeviceList.clear();
                }
            } else if (this.mLocalNapRoleConnected && (localBluetoothProfile instanceof PanProfile) && this.mOnlyPANUDevices.contains(this.mDevice) && i == 0) {
                Log.d("CachedBluetoothDevice", "Removing PanProfile from device after NAP disconnect");
                this.mProfiles.remove(localBluetoothProfile);
                this.mRemovedProfiles.add(localBluetoothProfile);
                this.mOnlyPANUDevices.remove(this.mDevice);
                if (this.mOnlyPANUDevices.size() == 0) {
                    this.mLocalNapRoleConnected = false;
                }
            }
        }
    }

    public final void refresh() {
        ThreadUtils.postOnBackgroundThread(new CachedBluetoothDevice$$ExternalSyntheticLambda0(this, 0));
    }

    public final void refreshName() {
        fetchName$1();
        if (BluetoothUtils.DEBUG) {
            Log.d("CachedBluetoothDevice", "Device name: " + getName());
        }
        dispatchAttributesChanged();
    }

    public final void secConnect() {
        boolean z;
        if (this.mBondState == 10) {
            startPairing();
            z = false;
        } else {
            z = true;
        }
        if (z) {
            checkLEConnectionGuide(true);
            this.mConnectAttempted = SystemClock.elapsedRealtime();
            connectDevice();
        }
    }

    public final void setBtClass(BluetoothClass bluetoothClass) {
        if (this.mBtClass != bluetoothClass) {
            boolean z = BluetoothUtils.DEBUG;
            if (z) {
                Log.d("CachedBluetoothDevice", "setBtClass :: " + bluetoothClass);
            }
            if (this.mBtClass == null || bluetoothClass.getMajorDeviceClass() != 7936) {
                this.mBtClass = bluetoothClass;
                dispatchAttributesChanged();
            } else if (z) {
                Log.d("CachedBluetoothDevice", "setBtClass :: btClass is " + bluetoothClass + ", not set uncategorized");
            }
        }
    }

    public final void setGroupId(int i) {
        LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
        if (localBluetoothProfileManager != null && hasProfile(localBluetoothProfileManager.mLeAudioProfile)) {
            BluetoothDump.BtLog("CachedBtDev -- setGroupId: " + this.mDevice.getAddressForLogging() + ", groupId = " + i);
        }
        this.mGroupId = i;
    }

    public final void setJustDiscovered(boolean z) {
        if (this.mJustDiscovered != z) {
            this.mJustDiscovered = z;
            dispatchAttributesChanged();
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
            this.mPrefixName = manufacturerData.mData.mDeviceCategoryPrefix;
            dispatchAttributesChanged();
            return;
        }
        if (Arrays.equals(getManufacturerRawData(), bArr)) {
            return;
        }
        this.mManufacturerData.updateDeviceInfo(bArr);
        this.mPrefixName = this.mManufacturerData.mData.mDeviceCategoryPrefix;
        dispatchAttributesChanged();
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
        dispatchAttributesChanged();
        Iterator it = ((HashSet) this.mMemberDevices).iterator();
        while (it.hasNext()) {
            ((CachedBluetoothDevice) it.next()).setName(str);
        }
    }

    public final void setRssi(short s) {
        int i = this.mRssiGroup;
        if (i != (s >= -56 ? 3 : s >= -68 ? 2 : 1)) {
            if (s >= -56) {
                if (i != 3) {
                    this.mRssiGroup = 3;
                    dispatchAttributesChanged();
                }
            } else if (s >= -68) {
                if (i != 2) {
                    this.mRssiGroup = 2;
                    dispatchAttributesChanged();
                }
            } else if (s < -68 && i != 1) {
                this.mRssiGroup = 1;
                dispatchAttributesChanged();
            }
            this.mRssi = s;
        }
    }

    public final boolean shouldLaunchGM(String str, boolean z) {
        Intent intent;
        Intent intent2;
        String address = this.mDevice.getAddress();
        int deviceType = getDeviceType();
        boolean z2 = true;
        if (deviceType != 1) {
            if (deviceType == 2) {
                int i = this.mVersion;
                if (i < 200) {
                    byte[] manufacturerRawData = getManufacturerRawData();
                    if (manufacturerRawData == null || manufacturerRawData.length <= 10) {
                        intent2 = null;
                    } else {
                        StringBuilder sb = new StringBuilder(manufacturerRawData[10]);
                        for (int i2 = 0; i2 < manufacturerRawData[10]; i2++) {
                            sb.append((char) manufacturerRawData[i2 + 11]);
                        }
                        String sb2 = sb.toString();
                        Intent intent3 = new Intent("com.samsung.android.sconnect.action.CONNECT_WEARABLE");
                        intent3.putExtra("WM_MANAGER", sb2);
                        intent2 = intent3;
                    }
                } else if (i < 300) {
                    intent2 = new Intent("com.samsung.android.wmanger.action.CONNECT_WEARABLE");
                    intent2.putExtra("DATA", getManufacturerRawData());
                } else {
                    intent2 = new Intent("com.samsung.android.action.BLUETOOTH_DEVICE");
                    intent2.putExtra("DATA", getManufacturerRawData());
                }
                if (intent2 != null) {
                    intent2.setPackage("com.samsung.android.app.watchmanagerstub");
                    Log.d("CachedBluetoothDevice", "shouldLaunchGM :: Send Bradcast to WatchManagerStub, type : ".concat(deviceTypeToString(deviceType)));
                }
                z2 = true ^ isHearableUsingWearableManager();
                intent = intent2;
            } else if (deviceType == 3) {
                if (this.mVersion >= 200) {
                    intent = new Intent("com.samsung.android.action.BLUETOOTH_DEVICE");
                    intent.putExtra("DATA", getManufacturerRawData());
                    intent.setPackage("com.sec.android.app.applinker");
                    Log.d("CachedBluetoothDevice", "shouldLaunchGM :: Send Bradcast to AppLinker, type : ".concat(deviceTypeToString(deviceType)));
                } else {
                    Log.d("CachedBluetoothDevice", "shouldLaunchGM :: AppLinker version is not satisfy");
                    intent = null;
                }
                z2 = false;
            } else {
                if (deviceType != 4) {
                    return false;
                }
                if (this.mVersion >= 200) {
                    intent = new Intent("com.samsung.android.action.BLUETOOTH_DEVICE");
                    intent.putExtra("DATA", getManufacturerRawData());
                    intent.setPackage("com.sec.android.app.applinker");
                    Log.d("CachedBluetoothDevice", "shouldLaunchGM :: Send Bradcast to AppLinker, type : ".concat(deviceTypeToString(deviceType)));
                } else {
                    Log.d("CachedBluetoothDevice", "shouldLaunchGM :: AppLinker version is not satisfy");
                    intent = null;
                }
            }
        } else {
            Intent intent4 = new Intent("com.samsung.android.sconnect.action.CONNECT_WEARABLE");
            intent4.putExtra("WM_MANAGER", "watchmanager");
            intent4.setPackage("com.samsung.android.app.watchmanagerstub");
            Log.d("CachedBluetoothDevice", "shouldLaunchGM :: Send Bradcast to WatchManagerStub, type : ".concat(deviceTypeToString(deviceType)));
            intent = intent4;
        }
        if (intent != null) {
            if (str != null) {
                intent.putExtra("request_app_package_name", str);
            }
            intent.putExtra("MAC", address);
            String str2 = this.mDeviceName;
            intent.putExtra(PeripheralConstants.Internal.BtPairingExtraDataType.DEVICE_NAME, (!(str2 == null || str2.equals(this.mAddress)) || TextUtils.isEmpty(null)) ? this.mDeviceName : null);
            intent.putExtra("IS_START_ACTIVITY", z);
            intent.addFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE);
            intent.addFlags(32);
            intent.addFlags(16777216);
            this.mContext.sendBroadcast(intent, "com.samsung.bluetooth.permission.BLUETOOTH_DEVICE");
        }
        StatusBarManager statusBarManager = (StatusBarManager) this.mContext.getSystemService("statusbar");
        if (statusBarManager != null) {
            statusBarManager.collapsePanels();
        }
        return z2;
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

    public final void switchSubDeviceContent() {
        BluetoothDevice bluetoothDevice = this.mDevice;
        short s = this.mRssi;
        boolean z = this.mJustDiscovered;
        HearingAidInfo hearingAidInfo = this.mHearingAidInfo;
        HashMap hashMap = this.mProfileConnectionState;
        String str = this.mName;
        CachedBluetoothDevice cachedBluetoothDevice = this.mSubDevice;
        this.mDevice = cachedBluetoothDevice.mDevice;
        this.mRssi = cachedBluetoothDevice.mRssi;
        this.mJustDiscovered = cachedBluetoothDevice.mJustDiscovered;
        this.mProfileConnectionState = cachedBluetoothDevice.mProfileConnectionState;
        this.mName = cachedBluetoothDevice.mName;
        this.mHearingAidInfo = cachedBluetoothDevice.mHearingAidInfo;
        cachedBluetoothDevice.mDevice = bluetoothDevice;
        cachedBluetoothDevice.mRssi = s;
        cachedBluetoothDevice.mJustDiscovered = z;
        cachedBluetoothDevice.mProfileConnectionState = hashMap;
        cachedBluetoothDevice.mName = str;
        cachedBluetoothDevice.mHearingAidInfo = hearingAidInfo;
        fetchActiveDevices();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("CachedBluetoothDevice{anonymizedAddress=");
        sb.append(this.mDevice.getAnonymizedAddress());
        sb.append(", name=");
        sb.append(getName());
        sb.append(", groupId=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.mGroupId, "}");
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
            if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), this.mDevice.getUuids())) {
                this.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH);
            } else {
                this.mBondingDetector.setFailCase(BluetoothRetryDetector.FailCase.PAIRING_FAILURE);
            }
        }
    }

    public final void unpairLegacy() {
        PbapServerProfile pbapServerProfile;
        int i = this.mBondState;
        if (i != 10) {
            synchronized (this.mProfileLock) {
                Iterator it = this.mProfiles.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                    if ((localBluetoothProfile instanceof SppProfile) || (localBluetoothProfile instanceof HidProfile)) {
                        Log.d("CachedBluetoothDevice", "disconnectLegacy :: skip disconnect " + localBluetoothProfile.toString());
                    } else {
                        Log.d("CachedBluetoothDevice", "disconnectLegacy :: profile : " + localBluetoothProfile);
                        if (localBluetoothProfile.setEnabled(this.mDevice) && BluetoothUtils.DEBUG) {
                            Log.d("CachedBluetoothDevice", "Command sent successfully:DISCONNECT " + describe(localBluetoothProfile));
                        }
                    }
                }
            }
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null && (pbapServerProfile = localBluetoothProfileManager.mPbapProfile) != null) {
                if (getProfileConnectionState(pbapServerProfile) == 2) {
                    pbapServerProfile.setEnabled(this.mDevice);
                }
            }
            if (i == 11) {
                this.mDevice.cancelBondProcess();
            }
            BluetoothDevice bluetoothDevice = this.mDevice;
            if (bluetoothDevice != null) {
                this.mUnpairing = true;
                BluetoothDump.BtLog("CachedBluetoothDevice -- unpairLegacy: decribe = " + describeDetail());
                if (this.mGroupId != -1) {
                    StringBuilder sb = new StringBuilder();
                    Iterator it2 = ((HashSet) this.mMemberDevices).iterator();
                    while (it2.hasNext()) {
                        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) it2.next();
                        if (sb.length() != 0) {
                            sb.append(" : ");
                        }
                        sb.append(cachedBluetoothDevice.getName() + "(" + cachedBluetoothDevice.getAddress() + ")");
                    }
                    BluetoothDump.BtLog("CachedBluetoothDevice -- unpairLegacy: member = " + sb.toString());
                }
                if (bluetoothDevice.removeBond()) {
                    this.mDrawableCache.evictAll();
                    if (BluetoothUtils.DEBUG) {
                        Log.d("CachedBluetoothDevice", "Command sent successfully:REMOVE_BOND " + describe(null));
                    }
                }
            }
        }
    }

    public final boolean updateProfiles(ParcelUuid[] parcelUuidArr) {
        if (parcelUuidArr == null) {
            Log.e("CachedBluetoothDevice", "updateProfiles :: uuids is null");
            if (this.mIsRestored) {
                parcelUuidArr = this.mRestoredDevice.mUuids;
            }
            this.mIsHearingAidDeviceByUUID = checkHearingAidByUuid();
            if (parcelUuidArr == null) {
                return false;
            }
        }
        List uuidsList = this.mLocalAdapter.getUuidsList();
        ParcelUuid[] parcelUuidArr2 = new ParcelUuid[uuidsList.size()];
        uuidsList.toArray(parcelUuidArr2);
        synchronized (this.mProfileLock) {
            LocalBluetoothProfileManager localBluetoothProfileManager = this.mProfileManager;
            if (localBluetoothProfileManager != null) {
                localBluetoothProfileManager.updateProfiles(parcelUuidArr, parcelUuidArr2, this.mProfiles, this.mRemovedProfiles, this);
            }
        }
        if (this.mLocalNapRoleConnected) {
            Iterator it = this.mRemovedProfiles.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                LocalBluetoothProfile localBluetoothProfile = (LocalBluetoothProfile) it.next();
                if (localBluetoothProfile instanceof PanProfile) {
                    Log.d("CachedBluetoothDevice", "PAN connection exists. Restore PAN profile.");
                    this.mRemovedProfiles.remove(localBluetoothProfile);
                    synchronized (this.mProfileLock) {
                        this.mProfiles.add(localBluetoothProfile);
                    }
                    break;
                }
            }
        }
        this.mIsHearingAidDeviceByUUID = checkHearingAidByUuid();
        if (!BluetoothUtils.DEBUG) {
            return true;
        }
        Log.e("CachedBluetoothDevice", "updating profiles for " + this.mDevice.getAlias() + ", " + this.mDevice);
        BluetoothClass bluetoothClass = this.mDevice.getBluetoothClass();
        if (bluetoothClass != null) {
            bluetoothClass.toString();
        }
        for (ParcelUuid parcelUuid : parcelUuidArr) {
            Objects.toString(parcelUuid);
        }
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x00e5  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice, Intent intent) {
        this.mVersion = 0;
        this.mType = 0;
        this.mOnlyPANUDevices = new ArrayList();
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
        this.mContext = context;
        this.mLocalAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileConnectionState = new HashMap();
        this.mProfileManager = localBluetoothProfileManager;
        this.mDevice = bluetoothDevice;
        String address = bluetoothDevice.getAddress();
        this.mAddress = address;
        String stringExtra = intent.getStringExtra("android.bluetooth.device.extra.NAME");
        if (TextUtils.isEmpty(stringExtra)) {
            if (BluetoothUtils.DEBUG) {
                AbstractC0000x2c234b15.m3m("Device has no name (yet), use address: ", address, "CachedBluetoothDevice");
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
                this.mBondState = 10;
                this.mType = this.mDevice.getType();
                this.mVisible = true;
                this.mIsBondingByCached = false;
                if (!BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), null)) {
                    this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH, false);
                } else {
                    this.mBondingDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.PAIRING_FAILURE, false);
                }
                Log.d("CachedBluetoothDevice", "fillUnBondedData :: " + describeDetail());
                dispatchAttributesChanged();
                this.mGroupId = -1;
                initDrawableCache();
                this.mUnpairing = false;
            }
        }
        this.mIsTablet = false;
        this.mBondState = 10;
        this.mType = this.mDevice.getType();
        this.mVisible = true;
        this.mIsBondingByCached = false;
        if (!BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), null)) {
        }
        Log.d("CachedBluetoothDevice", "fillUnBondedData :: " + describeDetail());
        dispatchAttributesChanged();
        this.mGroupId = -1;
        initDrawableCache();
        this.mUnpairing = false;
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothRestoredDevice bluetoothRestoredDevice) {
        this.mVersion = 0;
        this.mType = 0;
        this.mOnlyPANUDevices = new ArrayList();
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
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
        this.mProfileConnectionState = new HashMap();
        fillRestoredData();
        this.mGroupId = -1;
        initDrawableCache();
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothRestoredDevice bluetoothRestoredDevice, boolean z) {
        BluetoothRetryDetector bluetoothRetryDetector;
        this.mVersion = 0;
        this.mType = 0;
        this.mOnlyPANUDevices = new ArrayList();
        this.mVisible = true;
        this.mIsBondingByCached = false;
        this.mSemCallbacks = new ArrayList();
        this.mProfileLock = new Object();
        this.mProfiles = new LinkedHashSet();
        this.mRemovedProfiles = new LinkedHashSet();
        this.mCallbacks = new CopyOnWriteArrayList();
        this.mIsActiveDeviceA2dp = false;
        this.mIsActiveDeviceHeadset = false;
        this.mIsActiveDeviceHearingAid = false;
        this.mIsActiveDeviceLeAudio = false;
        this.mMemberDevices = new HashSet();
        this.mLeadDevice = null;
        this.mIsRestored = false;
        this.mIsSynced = false;
        this.mIsAddrSwitched = false;
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
        this.mProfileConnectionState = new HashMap();
        if (z) {
            String str2 = bluetoothRestoredDevice.mName;
            if (str2 == null) {
                fetchName$1();
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
            if (BluetoothUtils.isGalaxyWatchDevice(this.mDeviceName, this.mBtClass, getManufacturerRawData(), bluetoothRestoredDevice.mUuids)) {
                bluetoothRetryDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE_WATCH, false);
            } else {
                bluetoothRetryDetector = new BluetoothRetryDetector(BluetoothRetryDetector.FailCase.CONNECTION_FAILURE, false);
            }
            this.mBondingDetector = bluetoothRetryDetector;
            dispatchAttributesChanged();
        } else {
            fillRestoredData();
        }
        this.mGroupId = -1;
        initDrawableCache();
    }
}
