package com.android.server.locksettings;

import android.app.admin.PasswordMetrics;
import android.content.Context;
import android.content.pm.UserInfo;
import android.hardware.weaver.IWeaver;
import android.hardware.weaver.WeaverConfig;
import android.hardware.weaver.WeaverReadResponse;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.IHwBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.security.Scrypt;
import android.service.gatekeeper.GateKeeperResponse;
import android.service.gatekeeper.IGateKeeperService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.IWeakEscrowTokenRemovedListener;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockSettingsInternal;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.LocalServices;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.locksettings.LockSettingsStorage;
import com.android.server.locksettings.SyntheticPasswordManager;
import com.android.server.locksettings.SyntheticPasswordMdfpp;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import com.att.iqi.lib.metrics.p020mm.MM05;
import com.samsung.android.feature.SemFloatingFeature;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import libcore.util.HexEncoding;

/* loaded from: classes2.dex */
class SyntheticPasswordManager {
    public static final boolean DEBUG;
    public final Context mContext;
    public LockSettingsInternal mLockSettingsInternal;
    public PasswordSlotManager mPasswordSlotManager;
    public LockSettingsStorage mStorage;
    public final UserManager mUserManager;
    public IWeaver mWeaver;
    public WeaverConfig mWeaverConfig;
    public WeaverHidlAdapter mWeaverHidlService;
    public static final byte[] DEFAULT_PASSWORD = "default-password".getBytes();
    public static final byte[] DEFAULT_TOKEN = "This-byte-array-is-default-token".getBytes();
    public static final boolean SECURITY_UNPACK = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK");
    public static final byte[] PERSONALIZATION_SECDISCARDABLE = "secdiscardable-transform".getBytes();
    public static final byte[] PERSONALIZATION_KEY_STORE_PASSWORD = "keystore-password".getBytes();
    public static final byte[] PERSONALIZATION_USER_GK_AUTH = "user-gk-authentication".getBytes();
    public static final byte[] PERSONALIZATION_SP_GK_AUTH = "sp-gk-authentication".getBytes();
    public static final byte[] PERSONALIZATION_FBE_KEY = "fbe-key".getBytes();
    public static final byte[] PERSONALIZATION_AUTHSECRET_KEY = "authsecret-hal".getBytes();
    public static final byte[] PERSONALIZATION_AUTHSECRET_ENCRYPTION_KEY = "vendor-authsecret-encryption-key".getBytes();
    public static final byte[] PERSONALIZATION_SP_SPLIT = "sp-split".getBytes();
    public static final byte[] PERSONALIZATION_PASSWORD_HASH = "pw-hash".getBytes();
    public static final byte[] PERSONALIZATION_E0 = "e0-encryption".getBytes();
    public static final byte[] PERSONALIZATION_WEAVER_PASSWORD = "weaver-pwd".getBytes();
    public static final byte[] PERSONALIZATION_WEAVER_KEY = "weaver-key".getBytes();
    public static final byte[] PERSONALIZATION_WEAVER_TOKEN = "weaver-token".getBytes();
    public static final byte[] PERSONALIZATION_PASSWORD_METRICS = "password-metrics".getBytes();
    public static final byte[] PERSONALIZATION_CONTEXT = "android-synthetic-password-personalization-context".getBytes();
    public static final byte[] PERSONALIZATION_SDP_MASTER_KEY = "sdp-master-key".getBytes();
    public boolean mIsWeaverSupported = false;
    public final RemoteCallbackList mListeners = new RemoteCallbackList();
    public final WeaverAidlDeathRecipient mWeaverAidlDeathRecipient = new WeaverAidlDeathRecipient();
    public final WeaverHidlDeathRecipient mWeaverHidlDeathRecipient = new WeaverHidlDeathRecipient();
    public ArrayMap tokenMap = new ArrayMap();
    public final SdpSyntheticPasswordManager mSdpSyntheticPasswordManager = new SdpSyntheticPasswordManager(this);

    public class AuthenticationResult {
        public VerifyCredentialResponse gkResponse;
        public SyntheticPassword syntheticPassword;
    }

    public class TokenData {
        public byte[] aggregatedSecret;

        /* renamed from: km */
        public SyntheticPasswordMdfpp.KeyingMaterial f1714km;
        public LockPatternUtils.EscrowTokenStateChangeCallback mCallback;
        public int mType;
        public byte[] secdiscardableOnDisk;
        public byte[] weaverSecret;

        public TokenData() {
        }
    }

    public static int fakeUserId(int i) {
        return i + 100000;
    }

    private native long nativeSidFromPasswordHandle(byte[] bArr);

    public final byte getTokenBasedProtectorType(int i) {
        return i != 1 ? (byte) 1 : (byte) 2;
    }

    static {
        DEBUG = "userdebug".equals(SystemProperties.get("ro.build.type")) || "eng".equals(SystemProperties.get("ro.build.type"));
    }

    public class WeaverResult {
        public static final SparseArray mWeaverResults = new SparseArray();
        public int mOp;
        public int mSlot;
        public int mStatus = -999;
        public int mUserId;

        public static void begin(int i, int i2, int i3) {
            WeaverResult weaverResult = new WeaverResult(i, i2, i3);
            SparseArray sparseArray = mWeaverResults;
            synchronized (sparseArray) {
                sparseArray.put(i3, weaverResult);
            }
        }

        public static void update(int i, int i2) {
            synchronized (mWeaverResults) {
                WeaverResult bySlotIdLocked = getBySlotIdLocked(i);
                if (bySlotIdLocked != null) {
                    bySlotIdLocked.updateInternal(i2);
                }
            }
        }

        public static void finish(int i) {
            SparseArray sparseArray = mWeaverResults;
            synchronized (sparseArray) {
                WeaverResult weaverResult = (WeaverResult) sparseArray.get(i);
                if (weaverResult != null) {
                    SDPLog.m38d(weaverResult.toString());
                    sparseArray.remove(i);
                }
            }
        }

        public static void finishOff(int i, String str) {
            SparseArray sparseArray = mWeaverResults;
            synchronized (sparseArray) {
                WeaverResult bySlotIdLocked = getBySlotIdLocked(i);
                if (bySlotIdLocked != null) {
                    SDPLog.m38d(String.format(Locale.US, "%s [ op : %d, sl : %d, u : %d ]", str, Integer.valueOf(bySlotIdLocked.mOp), Integer.valueOf(bySlotIdLocked.mSlot), Integer.valueOf(bySlotIdLocked.mUserId)));
                    sparseArray.remove(bySlotIdLocked.mUserId);
                }
            }
        }

        public static WeaverResult getBySlotIdLocked(int i) {
            int i2 = 0;
            while (true) {
                SparseArray sparseArray = mWeaverResults;
                if (i2 >= sparseArray.size()) {
                    return null;
                }
                WeaverResult weaverResult = (WeaverResult) sparseArray.get(sparseArray.keyAt(i2));
                if (weaverResult != null && weaverResult.mSlot == i) {
                    return weaverResult;
                }
                i2++;
            }
        }

        public WeaverResult(int i, int i2, int i3) {
            this.mOp = i;
            this.mSlot = i2;
            this.mUserId = i3;
        }

        public final void updateInternal(int i) {
            this.mStatus = i;
        }

        public String toString() {
            String str;
            int i = this.mOp;
            if (i < 0 || i > 1) {
                return String.format(Locale.US, "Invalid operation for weaver [ op : %d, sl : %d, u : %d ]", Integer.valueOf(i), Integer.valueOf(this.mSlot), Integer.valueOf(this.mUserId));
            }
            int i2 = this.mStatus;
            if (i2 == -999) {
                return String.format(Locale.US, "No update for weaver [ op : %d, sl : %d, u : %d ]", Integer.valueOf(i), Integer.valueOf(this.mSlot), Integer.valueOf(this.mUserId));
            }
            if (i2 == 0) {
                str = "Weaver read status ok";
            } else if (i2 == 1) {
                str = "weaver read failed (FAILED)";
            } else if (i2 == 2) {
                str = "weaver read failed (INCORRECT_KEY)";
            } else if (i2 != 3) {
                str = "weaver read unknown status " + this.mStatus;
            } else {
                str = "weaver read failed (THROTTLE)";
            }
            Locale locale = Locale.US;
            Object[] objArr = new Object[4];
            objArr[0] = this.mOp == 0 ? "enrollment" : "verification";
            objArr[1] = Integer.valueOf(this.mUserId);
            objArr[2] = Integer.valueOf(this.mSlot);
            objArr[3] = str;
            return String.format(locale, "Result of weaver %s for user %d [ sl : %d, st : %s ]", objArr);
        }
    }

    public boolean isWeaverSupported() {
        return this.mIsWeaverSupported;
    }

    public void checkWeaverStatus() {
        boolean z;
        try {
            z = isWeaverAvailable();
        } catch (Exception e) {
            SDPLog.m41e("Unexpected exception while check weaver availability", e);
            z = false;
        }
        if (!isWeaverSupported()) {
            Slog.d("SyntheticPasswordManager.SDP", "Device does not support weaver");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Weaver is ");
        sb.append(z ? "now" : "NOT");
        sb.append(" available");
        SDPLog.m38d(sb.toString() + ((DEBUG && z) ? String.format(Locale.US, " with slots(%d), kSize(%d) and vSize(%d)", Integer.valueOf(this.mWeaverConfig.slots), Integer.valueOf(this.mWeaverConfig.keySize), Integer.valueOf(this.mWeaverConfig.valueSize)) : "!"));
    }

    public void refreshActiveSlots() {
        this.mPasswordSlotManager.refreshActiveSlots(getUsedWeaverSlots());
    }

    public class ConflictInfo {
        public final long handle;
        public final int userId;

        public ConflictInfo(long j, int i) {
            this.handle = j;
            this.userId = i;
        }
    }

    public final void mitigateSlotConflicts(Map map, Map map2) {
        if (map2.isEmpty()) {
            return;
        }
        for (Map.Entry entry : map.entrySet()) {
            int intValue = ((Integer) entry.getKey()).intValue();
            if (!UserManager.isVirtualUserId(intValue)) {
                Iterator it = ((List) entry.getValue()).iterator();
                while (it.hasNext()) {
                    int loadWeaverSlot = loadWeaverSlot(((Long) it.next()).longValue(), ((Integer) entry.getKey()).intValue());
                    if (map2.containsKey(Integer.valueOf(loadWeaverSlot))) {
                        SDPLog.m38d("Slot conflict at slot #" + loadWeaverSlot + " with user " + intValue);
                        List<ConflictInfo> list = (List) map2.get(Integer.valueOf(loadWeaverSlot));
                        if (list != null) {
                            for (ConflictInfo conflictInfo : list) {
                                mitigateSlotConflict(conflictInfo.handle, conflictInfo.userId);
                            }
                        }
                    }
                }
            }
        }
    }

    public final void mitigateSlotConflict(long j, int i) {
        if (UserManager.isVirtualUserId(i)) {
            SDPLog.m38d(String.format("Mitigate slot conflict on %x.weaver for virtual user %d", Long.valueOf(j), Integer.valueOf(i)));
            if (Binder.getCallingUid() == 1000) {
                destroyState("weaver", j, i);
                return;
            }
            return;
        }
        Slog.e("SyntheticPasswordManager", "Do not handle conflict for normal user " + i);
    }

    public class AuthenticationSdpToken {
        public boolean destroyed;
        public int secureMode;

        public AuthenticationSdpToken() {
            this.destroyed = false;
        }

        public void setSecureMode(int i) {
            this.secureMode = i;
        }

        public void setDestroyed(boolean z) {
            this.destroyed = z;
        }
    }

    public class SyntheticPassword {
        public byte[] mEncryptedEscrowSplit0;
        public byte[] mEscrowSplit1;
        public AuthenticationSdpToken mSdpToken;
        public boolean mSecureFolderAuthToken;
        public byte[] mSyntheticPassword;
        public final byte mVersion;

        public boolean isSdpMdfppMode() {
            return false;
        }

        public SyntheticPassword(byte b) {
            this.mVersion = b;
            createSdpToken();
        }

        public final byte[] deriveSubkey(byte[] bArr) {
            if (this.mVersion == 3) {
                return new SP800Derive(this.mSyntheticPassword).withContext(bArr, SyntheticPasswordManager.PERSONALIZATION_CONTEXT);
            }
            return SyntheticPasswordCrypto.personalizedHash(bArr, this.mSyntheticPassword);
        }

        public byte[] deriveKeyStorePassword() {
            return SyntheticPasswordManager.bytesToHex(deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_KEY_STORE_PASSWORD));
        }

        public byte[] deriveGkPassword() {
            return deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_SP_GK_AUTH);
        }

        public byte[] deriveFileBasedEncryptionKey() {
            return deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_FBE_KEY);
        }

        public byte[] deriveVendorAuthSecret() {
            return deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_AUTHSECRET_KEY);
        }

        public byte[] derivePasswordHashFactor() {
            return deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_PASSWORD_HASH);
        }

        public byte[] deriveMetricsKey() {
            return deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_PASSWORD_METRICS);
        }

        public byte[] deriveVendorAuthSecretEncryptionKey() {
            return deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_AUTHSECRET_ENCRYPTION_KEY);
        }

        public void setEscrowData(byte[] bArr, byte[] bArr2) {
            this.mEncryptedEscrowSplit0 = bArr;
            this.mEscrowSplit1 = bArr2;
        }

        public void recreateFromEscrow(byte[] bArr) {
            Objects.requireNonNull(this.mEscrowSplit1);
            Objects.requireNonNull(this.mEncryptedEscrowSplit0);
            recreate(bArr, this.mEscrowSplit1);
        }

        public void recreateDirectly(byte[] bArr) {
            this.mSyntheticPassword = Arrays.copyOf(bArr, bArr.length);
        }

        public static SyntheticPassword create() {
            SyntheticPassword syntheticPassword = new SyntheticPassword((byte) 3);
            byte[] randomBytes = SecureRandomUtils.randomBytes(32);
            byte[] randomBytes2 = SecureRandomUtils.randomBytes(32);
            syntheticPassword.recreate(randomBytes, randomBytes2);
            syntheticPassword.setEscrowData(SyntheticPasswordCrypto.encrypt(syntheticPassword.mSyntheticPassword, SyntheticPasswordManager.PERSONALIZATION_E0, randomBytes), randomBytes2);
            return syntheticPassword;
        }

        public final void recreate(byte[] bArr, byte[] bArr2) {
            this.mSyntheticPassword = SyntheticPasswordManager.bytesToHex(SyntheticPasswordCrypto.personalizedHash(SyntheticPasswordManager.PERSONALIZATION_SP_SPLIT, bArr, bArr2));
        }

        public byte[] getEscrowSecret() {
            if (this.mEncryptedEscrowSplit0 == null) {
                return null;
            }
            return SyntheticPasswordCrypto.decrypt(this.mSyntheticPassword, SyntheticPasswordManager.PERSONALIZATION_E0, this.mEncryptedEscrowSplit0);
        }

        public byte[] getSyntheticPassword() {
            return this.mSyntheticPassword;
        }

        public byte getVersion() {
            return this.mVersion;
        }

        public void setSecureFolderAuthToken(boolean z) {
            this.mSecureFolderAuthToken = true;
        }

        public boolean getSecureFolderAuthToken() {
            return this.mSecureFolderAuthToken;
        }

        public final void createSdpToken() {
            AuthenticationSdpToken authenticationSdpToken = new AuthenticationSdpToken();
            this.mSdpToken = authenticationSdpToken;
            authenticationSdpToken.setSecureMode(0);
            this.mSdpToken.setDestroyed(false);
            this.mSecureFolderAuthToken = false;
        }

        public byte[] deriveSdpMasterKey() {
            return deriveSubkey(SyntheticPasswordManager.PERSONALIZATION_SDP_MASTER_KEY);
        }

        public byte[] deriveSdpMasterKeyPersonalized() {
            return SyntheticPasswordCrypto.personalizedHashSDP(SyntheticPasswordManager.PERSONALIZATION_SDP_MASTER_KEY, this.mSyntheticPassword);
        }
    }

    public class PasswordData {
        public int credentialType;
        public byte[] passwordHandle;
        public int pinLength;
        public byte[] salt;
        public byte scryptLogN;
        public byte scryptLogP;
        public byte scryptLogR;
        public int secureMode;

        public static boolean isNormalSecureMode(int i) {
            return i >= 0 && i <= 2;
        }

        public static PasswordData create(int i, int i2) {
            PasswordData passwordData = new PasswordData();
            passwordData.scryptLogN = MM05.IQ_SIP_CALL_STATE_DISCONNECTING;
            passwordData.scryptLogR = (byte) 3;
            passwordData.scryptLogP = (byte) 1;
            passwordData.credentialType = i;
            passwordData.pinLength = i2;
            passwordData.salt = SecureRandomUtils.randomBytes(16);
            passwordData.secureMode = 0;
            return passwordData;
        }

        public static boolean isBadFormatFromAndroid14Beta(byte[] bArr) {
            return bArr != null && bArr.length >= 2 && bArr[0] == 0 && bArr[1] == 2;
        }

        public static PasswordData fromBytes(byte[] bArr) {
            PasswordData passwordData = new PasswordData();
            ByteBuffer allocate = ByteBuffer.allocate(bArr.length);
            allocate.put(bArr, 0, bArr.length);
            allocate.flip();
            passwordData.credentialType = (short) allocate.getInt();
            passwordData.scryptLogN = allocate.get();
            passwordData.scryptLogR = allocate.get();
            passwordData.scryptLogP = allocate.get();
            byte[] bArr2 = new byte[allocate.getInt()];
            passwordData.salt = bArr2;
            allocate.get(bArr2);
            int i = allocate.getInt();
            if (i > 0) {
                byte[] bArr3 = new byte[i];
                passwordData.passwordHandle = bArr3;
                allocate.get(bArr3);
            } else {
                passwordData.passwordHandle = null;
            }
            passwordData.pinLength = allocate.getInt();
            Log.d("SyntheticPasswordManager.SDP", "Inside fromBytes() - result.pinLength : " + passwordData.pinLength);
            passwordData.secureMode = 0;
            Log.d("SyntheticPasswordManager.SDP", "2. result.secureMode : " + passwordData.secureMode);
            return passwordData;
        }

        public static PasswordData fromBytesForMigration(byte[] bArr) {
            PasswordData passwordData = new PasswordData();
            ByteBuffer allocate = ByteBuffer.allocate(bArr.length);
            allocate.put(bArr, 0, bArr.length);
            allocate.flip();
            passwordData.credentialType = (short) allocate.getInt();
            passwordData.scryptLogN = allocate.get();
            passwordData.scryptLogR = allocate.get();
            passwordData.scryptLogP = allocate.get();
            byte[] bArr2 = new byte[allocate.getInt()];
            passwordData.salt = bArr2;
            allocate.get(bArr2);
            int i = allocate.getInt();
            if (i > 0) {
                byte[] bArr3 = new byte[i];
                passwordData.passwordHandle = bArr3;
                allocate.get(bArr3);
            } else {
                passwordData.passwordHandle = null;
            }
            int i2 = allocate.getInt();
            if (isNormalSecureMode(i2)) {
                passwordData.pinLength = -1;
                passwordData.secureMode = i2;
                SDPLog.m39d("SyntheticPasswordManager", "Migration case - secureMode : " + passwordData.secureMode);
            } else {
                passwordData.pinLength = i2;
                passwordData.secureMode = 0;
                SDPLog.m39d("SyntheticPasswordManager", "Abnormal migration case - pinLength : " + passwordData.pinLength + ", secureMode : " + passwordData.secureMode);
            }
            return passwordData;
        }

        public byte[] toBytes() {
            int length = this.salt.length + 11 + 4;
            byte[] bArr = this.passwordHandle;
            ByteBuffer allocate = ByteBuffer.allocate(length + (bArr != null ? bArr.length : 0) + 4);
            int i = this.credentialType;
            if (i < -32768 || i > 32767) {
                throw new IllegalArgumentException("Unknown credential type: " + this.credentialType);
            }
            allocate.putInt(i);
            allocate.put(this.scryptLogN);
            allocate.put(this.scryptLogR);
            allocate.put(this.scryptLogP);
            allocate.putInt(this.salt.length);
            allocate.put(this.salt);
            byte[] bArr2 = this.passwordHandle;
            if (bArr2 != null && bArr2.length > 0) {
                allocate.putInt(bArr2.length);
                allocate.put(this.passwordHandle);
            } else {
                allocate.putInt(0);
            }
            allocate.putInt(this.pinLength);
            return allocate.array();
        }
    }

    public class SyntheticPasswordBlob {
        public byte[] mContent;
        public byte mProtectorType;
        public byte mVersion;

        public static SyntheticPasswordBlob create(byte b, byte b2, byte[] bArr) {
            SyntheticPasswordBlob syntheticPasswordBlob = new SyntheticPasswordBlob();
            syntheticPasswordBlob.mVersion = b;
            syntheticPasswordBlob.mProtectorType = b2;
            syntheticPasswordBlob.mContent = bArr;
            return syntheticPasswordBlob;
        }

        public static SyntheticPasswordBlob fromBytes(byte[] bArr) {
            SyntheticPasswordBlob syntheticPasswordBlob = new SyntheticPasswordBlob();
            syntheticPasswordBlob.mVersion = bArr[0];
            syntheticPasswordBlob.mProtectorType = bArr[1];
            syntheticPasswordBlob.mContent = Arrays.copyOfRange(bArr, 2, bArr.length);
            return syntheticPasswordBlob;
        }

        public byte[] toByte() {
            byte[] bArr = this.mContent;
            byte[] bArr2 = new byte[bArr.length + 1 + 1];
            bArr2[0] = this.mVersion;
            bArr2[1] = this.mProtectorType;
            System.arraycopy(bArr, 0, bArr2, 2, bArr.length);
            return bArr2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SyntheticPasswordManager(Context context, LockSettingsStorage lockSettingsStorage, UserManager userManager, PasswordSlotManager passwordSlotManager) {
        this.mContext = context;
        this.mStorage = lockSettingsStorage;
        this.mUserManager = userManager;
        this.mPasswordSlotManager = passwordSlotManager;
    }

    public final boolean isDeviceProvisioned() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public class WeaverAidlDeathRecipient implements IBinder.DeathRecipient {
        public int deathCount;

        public WeaverAidlDeathRecipient() {
            this.deathCount = 0;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            this.deathCount++;
            Slog.e("SyntheticPasswordManager", "Weaver AIDL HAL died. " + this.deathCount + "times");
            try {
                SyntheticPasswordManager.this.mWeaver.asBinder().unlinkToDeath(this, 0);
            } catch (Exception e) {
                Slog.e("SyntheticPasswordManager", "Failed to unlinkToDeath", e);
            }
            SyntheticPasswordManager.this.mWeaver = null;
        }
    }

    public class WeaverHidlDeathRecipient implements IHwBinder.DeathRecipient {
        public int deathCount;

        public WeaverHidlDeathRecipient() {
            this.deathCount = 0;
        }

        public void serviceDied(long j) {
            this.deathCount++;
            Slog.e("SyntheticPasswordManager", "Weaver HIDL HAL died. " + this.deathCount + "times");
            try {
                SyntheticPasswordManager.this.mWeaverHidlService.getHidlService().unlinkToDeath(this);
            } catch (RemoteException e) {
                Slog.e("SyntheticPasswordManager", "Failed to unlinkToDeath", e);
            }
            SyntheticPasswordManager.this.mWeaver = null;
        }
    }

    public android.hardware.weaver.V1_0.IWeaver getWeaverHidlService() {
        try {
            if ("0".equals(SystemProperties.get("ro.boot.revision")) && "a25x".equals(SystemProperties.get("ro.product.device")) && "0x1".equals(SystemProperties.get("ro.boot.em.status"))) {
                Slog.w("SyntheticPasswordManager", "NVM is not loaded");
                return android.hardware.weaver.V1_0.IWeaver.getService(false);
            }
            return android.hardware.weaver.V1_0.IWeaver.getService(true);
        } catch (NoSuchElementException unused) {
            return null;
        }
    }

    public final IWeaver getWeaverService() {
        android.hardware.weaver.V1_0.IWeaver weaverHidlService;
        IWeaver asInterface;
        try {
            try {
                asInterface = IWeaver.Stub.asInterface(ServiceManager.waitForDeclaredService(IWeaver.DESCRIPTOR + "/default"));
            } catch (SecurityException unused) {
                Slog.w("SyntheticPasswordManager", "Does not have permissions to get AIDL weaver service");
                this.mIsWeaverSupported = false;
            }
            if (asInterface != null) {
                try {
                    asInterface.asBinder().linkToDeath(this.mWeaverAidlDeathRecipient, 0);
                } catch (RemoteException unused2) {
                    Slog.e("SyntheticPasswordManager", "Cannot register a death recipient");
                }
                Slog.i("SyntheticPasswordManager", "Using AIDL weaver service");
                return asInterface;
            }
            this.mIsWeaverSupported = true;
            try {
                try {
                    weaverHidlService = getWeaverHidlService();
                } catch (RemoteException e) {
                    Slog.w("SyntheticPasswordManager", "Failed to get HIDL weaver service.", e);
                    this.mIsWeaverSupported = false;
                }
                if (weaverHidlService != null) {
                    try {
                        weaverHidlService.linkToDeath(this.mWeaverHidlDeathRecipient, 0L);
                    } catch (RemoteException unused3) {
                        Slog.e("SyntheticPasswordManager", "Cannot register a death recipient");
                    }
                    Slog.i("SyntheticPasswordManager", "Using HIDL weaver service");
                    WeaverHidlAdapter weaverHidlAdapter = new WeaverHidlAdapter(weaverHidlService);
                    this.mWeaverHidlService = weaverHidlAdapter;
                    return weaverHidlAdapter;
                }
                Slog.w("SyntheticPasswordManager", "Device does not support weaver");
                this.mIsWeaverSupported = false;
                return null;
            } finally {
            }
        } finally {
        }
    }

    public boolean isAutoPinConfirmationFeatureAvailable() {
        return LockPatternUtils.isAutoPinConfirmFeatureAvailable();
    }

    public final synchronized boolean isWeaverAvailable() {
        if (this.mWeaver != null) {
            return true;
        }
        IWeaver weaverService = getWeaverService();
        if (weaverService == null) {
            return false;
        }
        try {
            WeaverConfig config = weaverService.getConfig();
            if (config != null && config.slots > 0) {
                this.mWeaver = weaverService;
                this.mWeaverConfig = config;
                this.mPasswordSlotManager.refreshActiveSlots(getUsedWeaverSlots());
                Slog.i("SyntheticPasswordManager", "Weaver service initialized");
                return true;
            }
            Slog.e("SyntheticPasswordManager", "Invalid weaver config");
            return false;
        } catch (RemoteException | ServiceSpecificException e) {
            Slog.e("SyntheticPasswordManager", "Failed to get weaver config", e);
            return false;
        }
    }

    public final byte[] weaverEnroll(int i, byte[] bArr, byte[] bArr2) {
        if (i != -1) {
            WeaverConfig weaverConfig = this.mWeaverConfig;
            if (i < weaverConfig.slots) {
                if (bArr == null) {
                    bArr = new byte[weaverConfig.keySize];
                } else if (bArr.length != weaverConfig.keySize) {
                    WeaverResult.finishOff(i, "Invalid key size for weaver");
                    throw new IllegalArgumentException("Invalid key size for weaver");
                }
                if (bArr2 == null) {
                    bArr2 = SecureRandomUtils.randomBytes(weaverConfig.valueSize);
                }
                try {
                    this.mWeaver.write(i, bArr, bArr2);
                    return bArr2;
                } catch (ServiceSpecificException e) {
                    Slog.e("SyntheticPasswordManager", "weaver write failed, slot: " + i, e);
                    WeaverResult.finishOff(i, "weaver write failed with ServiceSpecificException");
                    return null;
                } catch (RemoteException e2) {
                    Slog.e("SyntheticPasswordManager", "weaver write binder call failed, slot: " + i, e2);
                    WeaverResult.finishOff(i, "weaver write binder call failed with RemoveException");
                    return null;
                }
            }
        }
        WeaverResult.finishOff(i, "Invalid slot for weaver");
        throw new IllegalArgumentException("Invalid slot for weaver");
    }

    public static VerifyCredentialResponse responseFromTimeout(WeaverReadResponse weaverReadResponse) {
        long j = weaverReadResponse.timeout;
        return VerifyCredentialResponse.fromTimeout((j > 2147483647L || j < 0) ? Integer.MAX_VALUE : (int) j);
    }

    public final VerifyCredentialResponse weaverVerify(int i, byte[] bArr) {
        if (i != -1) {
            WeaverConfig weaverConfig = this.mWeaverConfig;
            if (i < weaverConfig.slots) {
                if (bArr == null) {
                    bArr = new byte[weaverConfig.keySize];
                } else if (bArr.length != weaverConfig.keySize) {
                    WeaverResult.finishOff(i, "Invalid key size for weaver");
                    throw new IllegalArgumentException("Invalid key size for weaver");
                }
                try {
                    WeaverReadResponse read = this.mWeaver.read(i, bArr);
                    int i2 = read.status;
                    new int[]{-999}[0] = i2;
                    WeaverResult.update(i, i2);
                    int i3 = read.status;
                    if (i3 == 0) {
                        return new VerifyCredentialResponse.Builder().setGatekeeperHAT(read.value).build();
                    }
                    if (i3 == 1) {
                        Slog.e("SyntheticPasswordManager", "weaver read failed (FAILED), slot: " + i);
                        return VerifyCredentialResponse.ERROR;
                    }
                    if (i3 == 2) {
                        if (read.timeout == 0) {
                            Slog.e("SyntheticPasswordManager", "weaver read failed (INCORRECT_KEY), slot: " + i);
                            return VerifyCredentialResponse.ERROR;
                        }
                        Slog.e("SyntheticPasswordManager", "weaver read failed (INCORRECT_KEY/THROTTLE), slot: " + i);
                        return responseFromTimeout(read);
                    }
                    if (i3 == 3) {
                        Slog.e("SyntheticPasswordManager", "weaver read failed (THROTTLE), slot: " + i);
                        return responseFromTimeout(read);
                    }
                    Slog.e("SyntheticPasswordManager", "weaver read unknown status " + read.status + ", slot: " + i);
                    return VerifyCredentialResponse.ERROR;
                } catch (RemoteException e) {
                    Slog.e("SyntheticPasswordManager", "weaver read failed, slot: " + i, e);
                    return VerifyCredentialResponse.ERROR;
                }
            }
        }
        WeaverResult.finishOff(i, "Invalid slot for weaver");
        throw new IllegalArgumentException("Invalid slot for weaver");
    }

    public void removeUser(IGateKeeperService iGateKeeperService, int i) {
        Iterator it = this.mStorage.listSyntheticPasswordProtectorsForUser("spblob", i).iterator();
        while (it.hasNext()) {
            long longValue = ((Long) it.next()).longValue();
            destroyWeaverSlot(longValue, i);
            destroyProtectorKey(getProtectorKeyAlias(longValue));
        }
        try {
            iGateKeeperService.clearSecureUserId(fakeUserId(i));
        } catch (RemoteException unused) {
            Slog.w("SyntheticPasswordManager", "Failed to clear SID from gatekeeper");
        }
    }

    public int getPinLength(long j, int i) {
        byte[] loadState = loadState("pwd", j, i);
        if (loadState == null) {
            return -1;
        }
        return PasswordData.fromBytes(loadState).pinLength;
    }

    public int getCredentialType(long j, int i) {
        byte[] loadState = loadState("pwd", j, i);
        if (loadState == null) {
            return -1;
        }
        return PasswordData.fromBytes(loadState).credentialType;
    }

    public static int getFrpCredentialType(byte[] bArr) {
        if (bArr == null) {
            return -1;
        }
        return PasswordData.fromBytes(bArr).credentialType;
    }

    public SyntheticPassword newSyntheticPassword(int i) {
        clearSidForUser(i);
        SyntheticPassword create = SyntheticPassword.create();
        saveEscrowData(create, i);
        return create;
    }

    public void newSidForUser(IGateKeeperService iGateKeeperService, SyntheticPassword syntheticPassword, int i) {
        try {
            GateKeeperResponse enroll = iGateKeeperService.enroll(i, (byte[]) null, (byte[]) null, syntheticPassword.deriveGkPassword());
            if (enroll.getResponseCode() != 0) {
                throw new IllegalStateException("Fail to create new SID for user " + i + " response: " + enroll.getResponseCode());
            }
            saveSyntheticPasswordHandle(enroll.getPayload(), i);
        } catch (RemoteException e) {
            throw new IllegalStateException("Failed to create new SID for user", e);
        }
    }

    public void clearSidForUser(int i) {
        destroyState("handle", 0L, i);
    }

    public boolean hasSidForUser(int i) {
        return hasState("handle", 0L, i);
    }

    public final byte[] loadSyntheticPasswordHandle(int i) {
        return loadState("handle", 0L, i);
    }

    public final void saveSyntheticPasswordHandle(byte[] bArr, int i) {
        saveState("handle", bArr, 0L, i);
        syncState(i);
    }

    public final boolean loadEscrowData(SyntheticPassword syntheticPassword, int i) {
        byte[] loadState = loadState("e0", 0L, i);
        byte[] loadState2 = loadState("p1", 0L, i);
        syntheticPassword.setEscrowData(loadState, loadState2);
        return (loadState == null || loadState2 == null) ? false : true;
    }

    public final void saveEscrowData(SyntheticPassword syntheticPassword, int i) {
        saveState("e0", syntheticPassword.mEncryptedEscrowSplit0, 0L, i);
        saveState("p1", syntheticPassword.mEscrowSplit1, 0L, i);
    }

    public boolean hasEscrowData(int i) {
        return hasState("e0", 0L, i) && hasState("p1", 0L, i);
    }

    public boolean hasAnyEscrowData(int i) {
        return hasState("e0", 0L, i) || hasState("p1", 0L, i);
    }

    public void destroyEscrowData(int i) {
        destroyState("e0", 0L, i);
        destroyState("p1", 0L, i);
    }

    public final int loadWeaverSlot(long j, int i) {
        byte[] loadState = loadState("weaver", j, i);
        if (loadState == null || loadState.length != 5) {
            return -1;
        }
        ByteBuffer allocate = ByteBuffer.allocate(5);
        allocate.put(loadState, 0, loadState.length);
        allocate.flip();
        if (allocate.get() != 1) {
            Slog.e("SyntheticPasswordManager", "Invalid weaver slot version for protector " + j);
            return -1;
        }
        return allocate.getInt();
    }

    public final void saveWeaverSlot(int i, long j, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate(5);
        allocate.put((byte) 1);
        allocate.putInt(i);
        saveState("weaver", allocate.array(), j, i2);
    }

    public final void destroyWeaverSlot(long j, int i) {
        int loadWeaverSlot = loadWeaverSlot(j, i);
        destroyState("weaver", j, i);
        if (loadWeaverSlot != -1) {
            if (!isWeaverAvailable()) {
                Slog.e("SyntheticPasswordManager", "Cannot erase Weaver slot because Weaver is unavailable");
                return;
            }
            Set usedWeaverSlots = getUsedWeaverSlots();
            SDPLog.m38d("Destroy weaver slot [ sl : " + loadWeaverSlot + ", u : " + i + " ]");
            if (!usedWeaverSlots.contains(Integer.valueOf(loadWeaverSlot))) {
                Slogf.m96i("SyntheticPasswordManager", "Erasing Weaver slot %d", Integer.valueOf(loadWeaverSlot));
                WeaverResult.begin(0, loadWeaverSlot, i);
                weaverEnroll(loadWeaverSlot, null, null);
                WeaverResult.finish(i);
                this.mPasswordSlotManager.markSlotDeleted(loadWeaverSlot);
                return;
            }
            Slogf.m96i("SyntheticPasswordManager", "Weaver slot %d was already reused; not erasing it", Integer.valueOf(loadWeaverSlot));
            SDPLog.m38d("Destroying skipped!");
        }
    }

    public final Set getUsedWeaverSlots() {
        ArrayList arrayList;
        Map listSyntheticPasswordProtectorsForAllUsers = this.mStorage.listSyntheticPasswordProtectorsForAllUsers("weaver");
        HashSet hashSet = new HashSet();
        HashMap hashMap = new HashMap();
        for (Map.Entry entry : listSyntheticPasswordProtectorsForAllUsers.entrySet()) {
            for (Long l : (List) entry.getValue()) {
                int loadWeaverSlot = loadWeaverSlot(l.longValue(), ((Integer) entry.getKey()).intValue());
                if (hashSet.contains(Integer.valueOf(loadWeaverSlot))) {
                    int intValue = ((Integer) entry.getKey()).intValue();
                    if (UserManager.isVirtualUserId(intValue)) {
                        SDPLog.m38d("Slot conflict at slot #" + loadWeaverSlot);
                        if (hashMap.containsKey(Integer.valueOf(loadWeaverSlot))) {
                            arrayList = (ArrayList) hashMap.get(Integer.valueOf(loadWeaverSlot));
                        } else {
                            arrayList = new ArrayList();
                            hashMap.put(Integer.valueOf(loadWeaverSlot), arrayList);
                        }
                        arrayList.add(new ConflictInfo(l.longValue(), intValue));
                    }
                }
                hashSet.add(Integer.valueOf(loadWeaverSlot));
            }
        }
        mitigateSlotConflicts(listSyntheticPasswordProtectorsForAllUsers, hashMap);
        return hashSet;
    }

    public final int getNextAvailableWeaverSlot() {
        LockSettingsStorage.PersistentData readPersistentDataBlock;
        Set usedWeaverSlots = getUsedWeaverSlots();
        usedWeaverSlots.addAll(this.mPasswordSlotManager.getUsedSlots());
        if (!isDeviceProvisioned() && (readPersistentDataBlock = this.mStorage.readPersistentDataBlock()) != null && readPersistentDataBlock.type == 2) {
            usedWeaverSlots.add(Integer.valueOf(readPersistentDataBlock.userId));
        }
        for (int i = 0; i < this.mWeaverConfig.slots; i++) {
            if (!usedWeaverSlots.contains(Integer.valueOf(i))) {
                return i;
            }
        }
        throw new IllegalStateException("Run out of weaver slots.");
    }

    public long createLskfBasedProtector(IGateKeeperService iGateKeeperService, LockscreenCredential lockscreenCredential, SyntheticPassword syntheticPassword, int i) {
        return createLskfBasedProtector(iGateKeeperService, null, 0L, lockscreenCredential, syntheticPassword, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01d3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public long createLskfBasedProtector(IGateKeeperService iGateKeeperService, LockscreenCredential lockscreenCredential, long j, LockscreenCredential lockscreenCredential2, SyntheticPassword syntheticPassword, int i) {
        PasswordData create;
        LockscreenCredential lockscreenCredential3;
        GateKeeperResponse enroll;
        byte[] transformUnderSecdiscardable;
        long generateProtectorId = generateProtectorId();
        int derivePinLength = isAutoPinConfirmationFeatureAvailable() ? derivePinLength(lockscreenCredential2.size(), lockscreenCredential2.isPin(), i) : -1;
        int secureMode = this.mSdpSyntheticPasswordManager.getSecureMode(i);
        boolean isSdpMdfppMode = this.mSdpSyntheticPasswordManager.isSdpMdfppMode(i);
        SDPLog.m43i("createLskfBasedProtector for user " + i + " secureMode : " + secureMode);
        if (lockscreenCredential2.isNone()) {
            lockscreenCredential3 = lockscreenCredential2;
            create = null;
        } else {
            create = PasswordData.create(lockscreenCredential2.getType(), derivePinLength);
            lockscreenCredential3 = lockscreenCredential2;
        }
        byte[] stretchLskf = stretchLskf(lockscreenCredential3, create);
        if (isSdpMdfppMode) {
            new SyntheticPasswordMdfpp.KeyingMaterial(stretchLskf, secureMode);
        } else {
            SyntheticPasswordMdfpp.KeyingMaterial.getNull();
        }
        if (lockscreenCredential2.isNone()) {
            this.mStorage.addLog(0, "User " + i + ", Lock is None");
        } else {
            this.mStorage.addLog(0, "User " + i + ", new salt : " + LockSettingsServiceLog.gethashStr(create.salt));
        }
        this.mStorage.setString("lockscreen.pwdata.ver", LockSettingsServiceLog.SECURITY_LOG_VERSION, i);
        Slogf.m96i("SyntheticPasswordManager", "Creating LSKF-based protector %016x for user %d", Long.valueOf(generateProtectorId), Integer.valueOf(i));
        long j2 = 0;
        if (isWeaverAvailable()) {
            int nextAvailableWeaverSlot = getNextAvailableWeaverSlot();
            Slogf.m96i("SyntheticPasswordManager", "Enrolling LSKF for user %d into Weaver slot %d", Integer.valueOf(i), Integer.valueOf(nextAvailableWeaverSlot));
            WeaverResult.begin(0, nextAvailableWeaverSlot, i);
            byte[] weaverEnroll = weaverEnroll(nextAvailableWeaverSlot, stretchedLskfToWeaverKey(stretchLskf), null);
            WeaverResult.finish(i);
            if (weaverEnroll == null) {
                makeSpmLog(i, generateProtectorId, "Weaver enroll failed : user " + i);
                SDPLog.m38d("Fail to enroll user password under waver " + i);
                throw new IllegalStateException("Fail to enroll user password under weaver " + i);
            }
            saveWeaverSlot(nextAvailableWeaverSlot, generateProtectorId, i);
            this.mPasswordSlotManager.markSlotInUse(nextAvailableWeaverSlot);
            synchronizeWeaverFrpPassword(create, 0, i, nextAvailableWeaverSlot);
            transformUnderSecdiscardable = transformUnderWeaverSecret(stretchLskf, weaverEnroll);
        } else {
            if (!lockscreenCredential2.isNone()) {
                try {
                    iGateKeeperService.clearSecureUserId(fakeUserId(i));
                } catch (RemoteException unused) {
                    Slog.w("SyntheticPasswordManager", "Failed to clear SID from gatekeeper");
                }
                Slogf.m96i("SyntheticPasswordManager", "Enrolling LSKF for user %d into Gatekeeper", Integer.valueOf(i));
                if (lockscreenCredential != null) {
                    try {
                        if (!lockscreenCredential.isNone() && j != 0) {
                            byte[] loadState = loadState("pwd", j, i);
                            PasswordData fromBytes = loadState != null ? PasswordData.fromBytes(loadState) : null;
                            if (fromBytes != null) {
                                enroll = iGateKeeperService.enroll(fakeUserId(i), fromBytes.passwordHandle, stretchedLskfToGkPassword(stretchLskf(lockscreenCredential, fromBytes)), stretchedLskfToGkPassword(stretchLskf));
                            } else {
                                Slogf.m94i("SyntheticPasswordManager", "previous pwd is null!");
                                enroll = iGateKeeperService.enroll(fakeUserId(i), (byte[]) null, (byte[]) null, stretchedLskfToGkPassword(stretchLskf));
                            }
                            if (enroll.getResponseCode() == 0) {
                                makeSpmLog(i, generateProtectorId, "GateKeeper enroll failed : " + enroll.getResponseCode());
                                throw new IllegalStateException("Failed to enroll LSKF for new SP protector for user " + i);
                            }
                            byte[] payload = enroll.getPayload();
                            create.passwordHandle = payload;
                            j2 = sidFromPasswordHandle(payload);
                        }
                    } catch (RemoteException e) {
                        throw new IllegalStateException("Failed to enroll LSKF for new SP protector for user " + i, e);
                    }
                }
                enroll = iGateKeeperService.enroll(fakeUserId(i), (byte[]) null, (byte[]) null, stretchedLskfToGkPassword(stretchLskf));
                if (enroll.getResponseCode() == 0) {
                }
            }
            transformUnderSecdiscardable = transformUnderSecdiscardable(stretchLskf, createSecdiscardable(generateProtectorId, i));
            synchronizeGatekeeperFrpPassword(create, 0, i);
        }
        byte[] bArr = transformUnderSecdiscardable;
        if (!lockscreenCredential2.isNone()) {
            saveState("pwd", create.toBytes(), generateProtectorId, i);
            savePasswordMetrics(lockscreenCredential2, syntheticPassword, generateProtectorId, i);
        }
        createSyntheticPasswordBlob(generateProtectorId, (byte) 0, syntheticPassword, bArr, j2, i);
        syncState(i);
        return generateProtectorId;
    }

    public final int derivePinLength(int i, boolean z, int i2) {
        if (z && this.mStorage.isAutoPinConfirmSettingEnabled(i2) && i >= 6) {
            return i;
        }
        return -1;
    }

    public VerifyCredentialResponse verifyFrpCredential(IGateKeeperService iGateKeeperService, LockscreenCredential lockscreenCredential, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        LockSettingsStorage.PersistentData readPersistentDataBlock = this.mStorage.readPersistentDataBlock();
        int i = readPersistentDataBlock.type;
        if (i == 1) {
            PasswordData fromBytes = PasswordData.fromBytes(readPersistentDataBlock.payload);
            try {
                return VerifyCredentialResponse.fromGateKeeperResponse(iGateKeeperService.verifyChallenge(fakeUserId(readPersistentDataBlock.userId), 0L, fromBytes.passwordHandle, stretchedLskfToGkPassword(stretchLskf(lockscreenCredential, fromBytes))));
            } catch (RemoteException e) {
                Slog.e("SyntheticPasswordManager", "FRP verifyChallenge failed", e);
                return VerifyCredentialResponse.ERROR;
            }
        }
        if (i == 2) {
            if (!isWeaverAvailable()) {
                Slog.e("SyntheticPasswordManager", "No weaver service to verify SP-based FRP credential");
                return VerifyCredentialResponse.ERROR;
            }
            byte[] stretchLskf = stretchLskf(lockscreenCredential, PasswordData.fromBytes(readPersistentDataBlock.payload));
            int i2 = readPersistentDataBlock.userId;
            try {
                WeaverResult.begin(1, i2, -9999);
                return weaverVerify(i2, stretchedLskfToWeaverKey(stretchLskf)).stripPayload();
            } finally {
                WeaverResult.finish(-9999);
            }
        }
        Slog.e("SyntheticPasswordManager", "persistentData.type must be TYPE_SP_GATEKEEPER or TYPE_SP_WEAVER, but is " + readPersistentDataBlock.type);
        return VerifyCredentialResponse.ERROR;
    }

    public void migrateFrpPasswordLocked(long j, UserInfo userInfo, int i) {
        if (this.mStorage.getPersistentDataBlockManager() == null || !LockPatternUtils.userOwnsFrpCredential(this.mContext, userInfo) || getCredentialType(j, userInfo.id) == -1) {
            return;
        }
        Slog.i("SyntheticPasswordManager", "Migrating FRP credential to persistent data block");
        PasswordData fromBytes = PasswordData.fromBytes(loadState("pwd", j, userInfo.id));
        int loadWeaverSlot = loadWeaverSlot(j, userInfo.id);
        if (loadWeaverSlot != -1) {
            synchronizeWeaverFrpPassword(fromBytes, i, userInfo.id, loadWeaverSlot);
        } else {
            synchronizeGatekeeperFrpPassword(fromBytes, i, userInfo.id);
        }
    }

    public static boolean isNoneCredential(PasswordData passwordData) {
        return passwordData == null || passwordData.credentialType == -1;
    }

    public final boolean shouldSynchronizeFrpCredential(PasswordData passwordData, int i) {
        if (this.mStorage.getPersistentDataBlockManager() == null) {
            return false;
        }
        if (!LockPatternUtils.userOwnsFrpCredential(this.mContext, this.mUserManager.getUserInfo(i))) {
            return false;
        }
        if (!isNoneCredential(passwordData) || isDeviceProvisioned()) {
            return true;
        }
        Slog.d("SyntheticPasswordManager", "Not clearing FRP credential yet because device is not yet provisioned");
        return false;
    }

    public final void synchronizeGatekeeperFrpPassword(PasswordData passwordData, int i, int i2) {
        if (shouldSynchronizeFrpCredential(passwordData, i2)) {
            Slogf.m88d("SyntheticPasswordManager", "Syncing Gatekeeper-based FRP credential tied to user %d", Integer.valueOf(i2));
            if (!isNoneCredential(passwordData)) {
                this.mStorage.writePersistentDataBlock(1, i2, i, passwordData.toBytes());
            } else {
                this.mStorage.writePersistentDataBlock(0, i2, 0, null);
            }
        }
    }

    public final void synchronizeWeaverFrpPassword(PasswordData passwordData, int i, int i2, int i3) {
        if (shouldSynchronizeFrpCredential(passwordData, i2)) {
            Slogf.m88d("SyntheticPasswordManager", "Syncing Weaver-based FRP credential tied to user %d", Integer.valueOf(i2));
            if (!isNoneCredential(passwordData)) {
                this.mStorage.writePersistentDataBlock(2, i3, i, passwordData.toBytes());
            } else {
                this.mStorage.writePersistentDataBlock(0, 0, 0, null);
            }
        }
    }

    public long addPendingToken(byte[] bArr, int i, int i2, LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        if (SECURITY_UNPACK) {
            bArr = DEFAULT_TOKEN;
        }
        long generateProtectorId = generateProtectorId();
        if (!this.tokenMap.containsKey(Integer.valueOf(i2))) {
            this.tokenMap.put(Integer.valueOf(i2), new ArrayMap());
        }
        TokenData tokenData = new TokenData();
        tokenData.mType = i;
        byte[] randomBytes = SecureRandomUtils.randomBytes(16384);
        if (isWeaverAvailable()) {
            byte[] randomBytes2 = SecureRandomUtils.randomBytes(this.mWeaverConfig.valueSize);
            tokenData.weaverSecret = randomBytes2;
            tokenData.secdiscardableOnDisk = SyntheticPasswordCrypto.encrypt(randomBytes2, PERSONALIZATION_WEAVER_TOKEN, randomBytes);
        } else {
            tokenData.secdiscardableOnDisk = randomBytes;
            tokenData.weaverSecret = null;
        }
        tokenData.aggregatedSecret = transformUnderSecdiscardable(bArr, randomBytes);
        tokenData.mCallback = escrowTokenStateChangeCallback;
        this.mSdpSyntheticPasswordManager.getSecureMode(i2);
        this.mSdpSyntheticPasswordManager.isSdpMdfppMode(i2);
        SDPLog.m43i("Not Sdp Mdfpp Mode. keyingMaterial null");
        tokenData.f1714km = SyntheticPasswordMdfpp.KeyingMaterial.getNull();
        ((ArrayMap) this.tokenMap.get(Integer.valueOf(i2))).put(Long.valueOf(generateProtectorId), tokenData);
        return generateProtectorId;
    }

    public byte[] getPendingTokenForDualDar(int i, long j) {
        if (this.tokenMap.containsKey(Integer.valueOf(i))) {
            return Arrays.copyOf(((TokenData) ((ArrayMap) this.tokenMap.get(Integer.valueOf(i))).get(Long.valueOf(j))).aggregatedSecret, ((TokenData) ((ArrayMap) this.tokenMap.get(Integer.valueOf(i))).get(Long.valueOf(j))).aggregatedSecret.length);
        }
        return null;
    }

    public byte[] getActiveTokenForDualDar(int i, byte[] bArr) {
        return SyntheticPasswordMdfpp.deriveResetTokenForDualDAR(bArr);
    }

    public Set getPendingTokensForUser(int i) {
        if (!this.tokenMap.containsKey(Integer.valueOf(i))) {
            return Collections.emptySet();
        }
        return new ArraySet(((ArrayMap) this.tokenMap.get(Integer.valueOf(i))).keySet());
    }

    public boolean removePendingToken(long j, int i) {
        return this.tokenMap.containsKey(Integer.valueOf(i)) && ((ArrayMap) this.tokenMap.get(Integer.valueOf(i))).remove(Long.valueOf(j)) != null;
    }

    public boolean createTokenBasedProtector(long j, SyntheticPassword syntheticPassword, int i) {
        TokenData tokenData;
        if (!this.tokenMap.containsKey(Integer.valueOf(i)) || (tokenData = (TokenData) ((ArrayMap) this.tokenMap.get(Integer.valueOf(i))).get(Long.valueOf(j))) == null) {
            return false;
        }
        if (!loadEscrowData(syntheticPassword, i)) {
            Slog.w("SyntheticPasswordManager", "User is not escrowable");
            return false;
        }
        Slogf.m96i("SyntheticPasswordManager", "Creating token-based protector %016x for user %d", Long.valueOf(j), Integer.valueOf(i));
        if (isWeaverAvailable()) {
            int nextAvailableWeaverSlot = getNextAvailableWeaverSlot();
            Slogf.m96i("SyntheticPasswordManager", "Using Weaver slot %d for new token-based protector", Integer.valueOf(nextAvailableWeaverSlot));
            WeaverResult.begin(0, nextAvailableWeaverSlot, i);
            if (weaverEnroll(nextAvailableWeaverSlot, null, tokenData.weaverSecret) == null) {
                Slog.e("SyntheticPasswordManager", "Failed to enroll weaver secret when activating token");
                WeaverResult.finishOff(nextAvailableWeaverSlot, "Failed to enroll token for user " + i);
                return false;
            }
            WeaverResult.finish(i);
            saveWeaverSlot(nextAvailableWeaverSlot, j, i);
            this.mPasswordSlotManager.markSlotInUse(nextAvailableWeaverSlot);
        }
        saveSecdiscardable(j, tokenData.secdiscardableOnDisk, i);
        this.mSdpSyntheticPasswordManager.isSdpMdfppMode(i);
        createSyntheticPasswordBlob(j, getTokenBasedProtectorType(tokenData.mType), syntheticPassword, tokenData.aggregatedSecret, 0L, i);
        syncState(i);
        ((ArrayMap) this.tokenMap.get(Integer.valueOf(i))).remove(Long.valueOf(j));
        LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback = tokenData.mCallback;
        if (escrowTokenStateChangeCallback == null) {
            return true;
        }
        escrowTokenStateChangeCallback.onEscrowTokenActivated(j, i);
        return true;
    }

    public final void createSyntheticPasswordBlob(long j, byte b, SyntheticPassword syntheticPassword, byte[] bArr, long j2, int i) {
        byte[] escrowSecret;
        if (b == 1 || b == 2) {
            escrowSecret = syntheticPassword.getEscrowSecret();
        } else {
            escrowSecret = syntheticPassword.getSyntheticPassword();
        }
        saveState("spblob", SyntheticPasswordBlob.create(syntheticPassword.mVersion == 3 ? (byte) 3 : (byte) 2, b, createSpBlob(getProtectorKeyAlias(j), escrowSecret, bArr, j2)).toByte(), j, i);
    }

    /* JADX WARN: Removed duplicated region for block: B:65:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0275  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x019f  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01c5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AuthenticationResult unlockLskfBasedProtector(IGateKeeperService iGateKeeperService, long j, LockscreenCredential lockscreenCredential, int i, ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        PasswordData passwordData;
        int i2;
        byte[] loadSecdiscardable;
        byte[] transformUnderSecdiscardable;
        GateKeeperResponse gateKeeperResponse;
        AuthenticationResult authenticationResult = new AuthenticationResult();
        long j2 = 0;
        if (j == 0) {
            Slogf.wtf("SyntheticPasswordManager", "Synthetic password not found for user %d", Integer.valueOf(i));
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
            return authenticationResult;
        }
        byte[] loadState = loadState("pwd", j, i);
        if (loadState != null) {
            PasswordData fromBytes = PasswordData.fromBytes(loadState);
            i2 = fromBytes.credentialType;
            passwordData = fromBytes;
        } else {
            passwordData = null;
            i2 = -1;
        }
        if (!lockscreenCredential.checkAgainstStoredType(i2)) {
            Slogf.m92e("SyntheticPasswordManager", "Credential type mismatch: stored type is %s but provided type is %s", LockPatternUtils.credentialTypeToString(i2), LockPatternUtils.credentialTypeToString(lockscreenCredential.getType()));
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
            return authenticationResult;
        }
        byte[] stretchLskf = stretchLskf(lockscreenCredential, passwordData);
        boolean isSdpMdfppMode = this.mSdpSyntheticPasswordManager.isSdpMdfppMode(i);
        SDPLog.m39d("SyntheticPasswordManager", "unlockLskfBasedProtector isSdpMdfppMode ? " + isSdpMdfppMode);
        if (isSdpMdfppMode) {
            new SyntheticPasswordMdfpp.KeyingMaterial(stretchLskf, this.mSdpSyntheticPasswordManager.getSecureMode(i));
        } else {
            SyntheticPasswordMdfpp.KeyingMaterial.getNull();
        }
        int loadWeaverSlot = loadWeaverSlot(j, i);
        if (loadWeaverSlot != -1) {
            if (!isWeaverAvailable()) {
                Slog.e("SyntheticPasswordManager", "Protector uses Weaver, but Weaver is unavailable");
                authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                this.mStorage.addLog(1, "No weaver service to unwrap password based SP!  userid = " + i);
                return authenticationResult;
            }
            WeaverResult.begin(1, loadWeaverSlot, i);
            authenticationResult.gkResponse = weaverVerify(loadWeaverSlot, stretchedLskfToWeaverKey(stretchLskf));
            WeaverResult.finish(i);
            if (authenticationResult.gkResponse.getResponseCode() != 0) {
                int timeout = authenticationResult.gkResponse.getTimeout();
                StringBuilder sb = new StringBuilder();
                sb.append(", request salt : ");
                sb.append(passwordData == null ? "null" : LockSettingsServiceLog.gethashStr(passwordData.salt));
                String sb2 = sb.toString();
                this.mStorage.addLog(1, "Weaver failed!! User " + i + ", timeout = " + timeout + sb2);
                return authenticationResult;
            }
            transformUnderSecdiscardable = transformUnderWeaverSecret(stretchLskf, authenticationResult.gkResponse.getGatekeeperHAT());
        } else {
            if (passwordData == null || passwordData.passwordHandle == null) {
                if (!lockscreenCredential.isNone()) {
                    Slog.e("SyntheticPasswordManager", "Missing Gatekeeper password handle for nonempty LSKF");
                    authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                    return authenticationResult;
                }
            } else {
                byte[] stretchedLskfToGkPassword = stretchedLskfToGkPassword(stretchLskf);
                if (UserManager.isVirtualUserId(i) && isWeaverAvailable() && passwordData.passwordHandle == null) {
                    SDPLog.m38d("Virtual User " + i + " may lost weaver slot.");
                    passwordData.passwordHandle = new byte[0];
                }
                try {
                    GateKeeperResponse verifyChallenge = iGateKeeperService.verifyChallenge(fakeUserId(i), 0L, passwordData.passwordHandle, stretchedLskfToGkPassword);
                    int responseCode = verifyChallenge.getResponseCode();
                    if (responseCode != 0) {
                        if (responseCode == 1) {
                            authenticationResult.gkResponse = VerifyCredentialResponse.fromTimeout(verifyChallenge.getTimeout());
                            this.mStorage.addLog(1, "gatekeeper failed!! User " + i + ", timeout = " + verifyChallenge.getTimeout() + ", request salt : " + LockSettingsServiceLog.gethashStr(passwordData.salt));
                            return authenticationResult;
                        }
                        authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                        this.mStorage.addLog(1, "gatekeeper failed!! User " + i + ", timeout = " + verifyChallenge.getTimeout() + ", request salt : " + LockSettingsServiceLog.gethashStr(passwordData.salt));
                        return authenticationResult;
                    }
                    authenticationResult.gkResponse = VerifyCredentialResponse.OK;
                    if (verifyChallenge.getShouldReEnroll()) {
                        try {
                            try {
                                gateKeeperResponse = iGateKeeperService.enroll(fakeUserId(i), passwordData.passwordHandle, stretchedLskfToGkPassword, stretchedLskfToGkPassword);
                            } catch (RemoteException e) {
                                e = e;
                                Slog.w("SyntheticPasswordManager", "Fail to invoke gatekeeper.enroll", e);
                                gateKeeperResponse = GateKeeperResponse.ERROR;
                                if (gateKeeperResponse.getResponseCode() != 0) {
                                }
                                j2 = sidFromPasswordHandle(passwordData.passwordHandle);
                                loadSecdiscardable = loadSecdiscardable(j, i);
                                if (loadSecdiscardable != null) {
                                }
                            }
                        } catch (RemoteException e2) {
                            e = e2;
                        }
                        if (gateKeeperResponse.getResponseCode() != 0) {
                            passwordData.passwordHandle = gateKeeperResponse.getPayload();
                            passwordData.credentialType = lockscreenCredential.getType();
                            saveState("pwd", passwordData.toBytes(), j, i);
                            syncState(i);
                            synchronizeGatekeeperFrpPassword(passwordData, 0, i);
                        } else {
                            Slog.w("SyntheticPasswordManager", "Fail to re-enroll user password for user " + i);
                        }
                    }
                    j2 = sidFromPasswordHandle(passwordData.passwordHandle);
                } catch (RemoteException e3) {
                    Slog.e("SyntheticPasswordManager", "gatekeeper verify failed", e3);
                    authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                    return authenticationResult;
                }
            }
            loadSecdiscardable = loadSecdiscardable(j, i);
            if (loadSecdiscardable != null) {
                Slog.e("SyntheticPasswordManager", "secdiscardable file not found");
                authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            transformUnderSecdiscardable = transformUnderSecdiscardable(stretchLskf, loadSecdiscardable);
        }
        long j3 = j2;
        byte[] bArr = transformUnderSecdiscardable;
        if (iCheckCredentialProgressCallback != null) {
            try {
                iCheckCredentialProgressCallback.onCredentialVerified();
            } catch (RemoteException e4) {
                Slog.w("SyntheticPasswordManager", "progressCallback throws exception", e4);
            }
        }
        SyntheticPassword unwrapSyntheticPasswordBlob = unwrapSyntheticPasswordBlob(j, (byte) 0, bArr, j3, i);
        authenticationResult.syntheticPassword = unwrapSyntheticPasswordBlob;
        authenticationResult.gkResponse = verifyChallenge(iGateKeeperService, unwrapSyntheticPasswordBlob, 0L, i);
        if (authenticationResult.syntheticPassword != null && !lockscreenCredential.isNone() && !hasPasswordMetrics(j, i)) {
            savePasswordMetrics(lockscreenCredential, authenticationResult.syntheticPassword, j, i);
            syncState(i);
        }
        return authenticationResult;
    }

    public boolean refreshPinLengthOnDisk(PasswordMetrics passwordMetrics, long j, int i) {
        byte[] loadState;
        if (!isAutoPinConfirmationFeatureAvailable() || (loadState = loadState("pwd", j, i)) == null) {
            return false;
        }
        PasswordData fromBytes = PasswordData.fromBytes(loadState);
        int derivePinLength = derivePinLength(passwordMetrics.length, passwordMetrics.credType == 3, i);
        if (fromBytes.pinLength != derivePinLength) {
            fromBytes.pinLength = derivePinLength;
            saveState("pwd", fromBytes.toBytes(), j, i);
            syncState(i);
        }
        return true;
    }

    public AuthenticationResult unlockTokenBasedProtector(IGateKeeperService iGateKeeperService, long j, byte[] bArr, int i) {
        if (SECURITY_UNPACK) {
            bArr = DEFAULT_TOKEN;
        }
        return unlockTokenBasedProtectorInternal(iGateKeeperService, j, SyntheticPasswordBlob.fromBytes(loadState("spblob", j, i)).mProtectorType, bArr, i);
    }

    public AuthenticationResult unlockWeakTokenBasedProtector(IGateKeeperService iGateKeeperService, long j, byte[] bArr, int i) {
        return unlockTokenBasedProtectorInternal(iGateKeeperService, j, (byte) 2, bArr, i);
    }

    public final AuthenticationResult unlockTokenBasedProtectorInternal(IGateKeeperService iGateKeeperService, long j, byte b, byte[] bArr, int i) {
        AuthenticationResult authenticationResult = new AuthenticationResult();
        byte[] loadSecdiscardable = loadSecdiscardable(j, i);
        if (loadSecdiscardable == null) {
            Slog.e("SyntheticPasswordManager", "secdiscardable file not found");
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
            return authenticationResult;
        }
        int loadWeaverSlot = loadWeaverSlot(j, i);
        if (loadWeaverSlot != -1) {
            if (!isWeaverAvailable()) {
                Slog.e("SyntheticPasswordManager", "Protector uses Weaver, but Weaver is unavailable");
                authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            WeaverResult.begin(1, loadWeaverSlot, i);
            VerifyCredentialResponse weaverVerify = weaverVerify(loadWeaverSlot, null);
            WeaverResult.finish(i);
            if (weaverVerify.getResponseCode() != 0 || weaverVerify.getGatekeeperHAT() == null) {
                Slog.e("SyntheticPasswordManager", "Failed to retrieve Weaver secret when unlocking token-based protector");
                authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            loadSecdiscardable = SyntheticPasswordCrypto.decrypt(weaverVerify.getGatekeeperHAT(), PERSONALIZATION_WEAVER_TOKEN, loadSecdiscardable);
        }
        this.mSdpSyntheticPasswordManager.getSecureMode(i);
        this.mSdpSyntheticPasswordManager.isSdpMdfppMode(i);
        SyntheticPasswordMdfpp.KeyingMaterial.getNull();
        SyntheticPassword unwrapSyntheticPasswordBlob = unwrapSyntheticPasswordBlob(j, b, transformUnderSecdiscardable(bArr, loadSecdiscardable), 0L, i);
        authenticationResult.syntheticPassword = unwrapSyntheticPasswordBlob;
        if (unwrapSyntheticPasswordBlob != null) {
            VerifyCredentialResponse verifyChallenge = verifyChallenge(iGateKeeperService, unwrapSyntheticPasswordBlob, 0L, i);
            authenticationResult.gkResponse = verifyChallenge;
            if (verifyChallenge == null) {
                authenticationResult.gkResponse = VerifyCredentialResponse.OK;
            }
        } else {
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
        }
        return authenticationResult;
    }

    public final SyntheticPassword unwrapSyntheticPasswordBlob(long j, byte b, byte[] bArr, long j2, int i) {
        byte[] decryptSpBlob;
        byte[] loadState = loadState("spblob", j, i);
        if (loadState == null) {
            return null;
        }
        SyntheticPasswordBlob fromBytes = SyntheticPasswordBlob.fromBytes(loadState);
        byte b2 = fromBytes.mVersion;
        if (b2 != 3 && b2 != 2 && b2 != 1) {
            throw new IllegalArgumentException("Unknown blob version: " + ((int) fromBytes.mVersion));
        }
        if (fromBytes.mProtectorType != b) {
            throw new IllegalArgumentException("Invalid protector type: " + ((int) fromBytes.mProtectorType));
        }
        if (b2 == 1) {
            decryptSpBlob = SyntheticPasswordCrypto.decryptBlobV1(getProtectorKeyAlias(j), fromBytes.mContent, bArr);
        } else {
            decryptSpBlob = decryptSpBlob(getProtectorKeyAlias(j), fromBytes.mContent, bArr);
        }
        if (decryptSpBlob == null) {
            Slog.e("SyntheticPasswordManager", "Fail to decrypt SP for user " + i);
            return null;
        }
        SyntheticPassword syntheticPassword = new SyntheticPassword(fromBytes.mVersion);
        byte b3 = fromBytes.mProtectorType;
        if (b3 == 1 || b3 == 2) {
            if (!loadEscrowData(syntheticPassword, i)) {
                Slog.e("SyntheticPasswordManager", "User is not escrowable: " + i);
                return null;
            }
            syntheticPassword.recreateFromEscrow(decryptSpBlob);
        } else {
            syntheticPassword.recreateDirectly(decryptSpBlob);
        }
        if (fromBytes.mVersion == 1) {
            Slog.i("SyntheticPasswordManager", "Upgrading v1 SP blob for user " + i + ", protectorType = " + ((int) fromBytes.mProtectorType));
            createSyntheticPasswordBlob(j, fromBytes.mProtectorType, syntheticPassword, bArr, j2, i);
            syncState(i);
        }
        return syntheticPassword;
    }

    public VerifyCredentialResponse verifyChallenge(IGateKeeperService iGateKeeperService, SyntheticPassword syntheticPassword, long j, int i) {
        return verifyChallengeInternal(iGateKeeperService, syntheticPassword.deriveGkPassword(), j, i);
    }

    public VerifyCredentialResponse verifyChallengeInternal(IGateKeeperService iGateKeeperService, byte[] bArr, long j, int i) {
        GateKeeperResponse gateKeeperResponse;
        byte[] loadSyntheticPasswordHandle = loadSyntheticPasswordHandle(i);
        if (loadSyntheticPasswordHandle == null) {
            return null;
        }
        try {
            GateKeeperResponse verifyChallenge = iGateKeeperService.verifyChallenge(i, j, loadSyntheticPasswordHandle, bArr);
            int responseCode = verifyChallenge.getResponseCode();
            if (responseCode != 0) {
                if (responseCode == 1) {
                    return VerifyCredentialResponse.fromTimeout(verifyChallenge.getTimeout());
                }
                return VerifyCredentialResponse.ERROR;
            }
            VerifyCredentialResponse build = new VerifyCredentialResponse.Builder().setGatekeeperHAT(verifyChallenge.getPayload()).build();
            if (verifyChallenge.getShouldReEnroll()) {
                try {
                    gateKeeperResponse = iGateKeeperService.enroll(i, loadSyntheticPasswordHandle, loadSyntheticPasswordHandle, bArr);
                } catch (RemoteException e) {
                    Slog.e("SyntheticPasswordManager", "Failed to invoke gatekeeper.enroll", e);
                    gateKeeperResponse = GateKeeperResponse.ERROR;
                }
                if (gateKeeperResponse.getResponseCode() == 0) {
                    saveSyntheticPasswordHandle(gateKeeperResponse.getPayload(), i);
                    return verifyChallengeInternal(iGateKeeperService, bArr, j, i);
                }
                Slog.w("SyntheticPasswordManager", "Fail to re-enroll SP handle for user " + i);
            }
            return build;
        } catch (RemoteException e2) {
            Slog.e("SyntheticPasswordManager", "Fail to verify with gatekeeper " + i, e2);
            return VerifyCredentialResponse.ERROR;
        }
    }

    public boolean protectorExists(long j, int i) {
        return hasState("spblob", j, i);
    }

    public void destroyTokenBasedProtector(long j, int i) {
        Slogf.m96i("SyntheticPasswordManager", "Destroying token-based protector %016x for user %d", Long.valueOf(j), Integer.valueOf(i));
        SyntheticPasswordBlob fromBytes = SyntheticPasswordBlob.fromBytes(loadState("spblob", j, i));
        destroyProtectorCommon(j, i);
        if (fromBytes.mProtectorType == 2) {
            notifyWeakEscrowTokenRemovedListeners(j, i);
        }
    }

    public void destroyAllWeakTokenBasedProtectors(int i) {
        Iterator it = this.mStorage.listSyntheticPasswordProtectorsForUser("secdis", i).iterator();
        while (it.hasNext()) {
            long longValue = ((Long) it.next()).longValue();
            byte[] loadState = loadState("spblob", longValue, i);
            if (loadState == null) {
                Slogf.m92e("SyntheticPasswordManager", "!@ Loadstate failed, protector %016x for user %d", Long.valueOf(longValue), Integer.valueOf(i));
            } else if (SyntheticPasswordBlob.fromBytes(loadState).mProtectorType == 2) {
                destroyTokenBasedProtector(longValue, i);
            }
        }
    }

    public void destroyLskfBasedProtector(long j, int i) {
        Slogf.m96i("SyntheticPasswordManager", "Destroying LSKF-based protector %016x for user %d", Long.valueOf(j), Integer.valueOf(i));
        destroyProtectorCommon(j, i);
        destroyState("pwd", j, i);
        destroyState("metrics", j, i);
    }

    public final void destroyProtectorCommon(long j, int i) {
        destroyState("spblob", j, i);
        destroyProtectorKey(getProtectorKeyAlias(j));
        destroyState("secdis", j, i);
        if (hasState("weaver", j, i)) {
            destroyWeaverSlot(j, i);
        }
    }

    public final byte[] transformUnderWeaverSecret(byte[] bArr, byte[] bArr2) {
        return ArrayUtils.concat(new byte[][]{bArr, SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_WEAVER_PASSWORD, bArr2)});
    }

    public final byte[] transformUnderSecdiscardable(byte[] bArr, byte[] bArr2) {
        return ArrayUtils.concat(new byte[][]{bArr, SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_SECDISCARDABLE, bArr2)});
    }

    public final byte[] createSecdiscardable(long j, int i) {
        byte[] randomBytes = SecureRandomUtils.randomBytes(16384);
        saveSecdiscardable(j, randomBytes, i);
        return randomBytes;
    }

    public final void saveSecdiscardable(long j, byte[] bArr, int i) {
        saveState("secdis", bArr, j, i);
    }

    public final byte[] loadSecdiscardable(long j, int i) {
        return loadState("secdis", j, i);
    }

    public boolean hasPasswordData(long j, int i) {
        return hasState("pwd", j, i);
    }

    public PasswordMetrics getPasswordMetrics(SyntheticPassword syntheticPassword, long j, int i) {
        byte[] loadState = loadState("metrics", j, i);
        if (loadState == null) {
            Slogf.m92e("SyntheticPasswordManager", "Failed to read password metrics file for user %d", Integer.valueOf(i));
            return null;
        }
        byte[] decrypt = SyntheticPasswordCrypto.decrypt(syntheticPassword.deriveMetricsKey(), new byte[0], loadState);
        if (decrypt == null) {
            Slogf.m92e("SyntheticPasswordManager", "Failed to decrypt password metrics file for user %d", Integer.valueOf(i));
            return null;
        }
        return VersionedPasswordMetrics.deserialize(decrypt).getMetrics();
    }

    public final void savePasswordMetrics(LockscreenCredential lockscreenCredential, SyntheticPassword syntheticPassword, long j, int i) {
        saveState("metrics", SyntheticPasswordCrypto.encrypt(syntheticPassword.deriveMetricsKey(), new byte[0], new VersionedPasswordMetrics(lockscreenCredential).serialize()), j, i);
    }

    public boolean hasPasswordMetrics(long j, int i) {
        return hasState("metrics", j, i);
    }

    public final boolean hasState(String str, long j, int i) {
        return !ArrayUtils.isEmpty(loadState(str, j, i));
    }

    public final byte[] loadState(String str, long j, int i) {
        return this.mStorage.readSyntheticPasswordState(i, j, str);
    }

    public final void saveState(String str, byte[] bArr, long j, int i) {
        if ("spblob".equals(str)) {
            makeSpmLog(i, j, "saveState() " + str);
        }
        this.mStorage.writeSyntheticPasswordState(i, j, str, bArr);
    }

    public final void syncState(int i) {
        this.mStorage.syncSyntheticPasswordState(i);
    }

    public final void destroyState(String str, long j, int i) {
        if ("spblob".equals(str)) {
            makeSpmLog(i, j, "destroyState() " + str);
        }
        this.mStorage.deleteSyntheticPasswordState(i, j, str);
    }

    public byte[] decryptSpBlob(String str, byte[] bArr, byte[] bArr2) {
        return SyntheticPasswordCrypto.decryptBlob(str, bArr, bArr2);
    }

    public byte[] createSpBlob(String str, byte[] bArr, byte[] bArr2, long j) {
        return SyntheticPasswordCrypto.createBlob(str, bArr, bArr2, j);
    }

    public void destroyProtectorKey(String str) {
        SyntheticPasswordCrypto.destroyProtectorKey(str);
    }

    public static long generateProtectorId() {
        long randomLong;
        do {
            randomLong = SecureRandomUtils.randomLong();
        } while (randomLong == 0);
        return randomLong;
    }

    public final String getProtectorKeyAlias(long j) {
        return TextUtils.formatSimple("%s%x", new Object[]{"synthetic_password_", Long.valueOf(j)});
    }

    public byte[] stretchLskf(LockscreenCredential lockscreenCredential, PasswordData passwordData) {
        byte[] credential = (lockscreenCredential.isNone() || SECURITY_UNPACK) ? DEFAULT_PASSWORD : lockscreenCredential.getCredential();
        if (passwordData == null) {
            Preconditions.checkArgument(lockscreenCredential.isNone());
            return Arrays.copyOf(credential, 32);
        }
        return scrypt(credential, passwordData.salt, 1 << passwordData.scryptLogN, 1 << passwordData.scryptLogR, 1 << passwordData.scryptLogP, 32);
    }

    public final byte[] stretchedLskfToGkPassword(byte[] bArr) {
        return SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_USER_GK_AUTH, bArr);
    }

    public final byte[] stretchedLskfToWeaverKey(byte[] bArr) {
        byte[] personalizedHash = SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_WEAVER_KEY, bArr);
        int length = personalizedHash.length;
        int i = this.mWeaverConfig.keySize;
        if (length < i) {
            throw new IllegalArgumentException("weaver key length too small");
        }
        return Arrays.copyOf(personalizedHash, i);
    }

    public long sidFromPasswordHandle(byte[] bArr) {
        return nativeSidFromPasswordHandle(bArr);
    }

    public byte[] scrypt(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4) {
        return new Scrypt().scrypt(bArr, bArr2, i, i2, i3, i4);
    }

    public static byte[] bytesToHex(byte[] bArr) {
        return HexEncoding.encodeToString(bArr).getBytes();
    }

    public boolean migrateKeyNamespace() {
        Iterator it = this.mStorage.listSyntheticPasswordProtectorsForAllUsers("spblob").values().iterator();
        boolean z = true;
        while (it.hasNext()) {
            Iterator it2 = ((List) it.next()).iterator();
            while (it2.hasNext()) {
                z &= SyntheticPasswordCrypto.migrateLockSettingsKey(getProtectorKeyAlias(((Long) it2.next()).longValue()));
            }
        }
        return z;
    }

    public boolean registerWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        return this.mListeners.register(iWeakEscrowTokenRemovedListener);
    }

    public boolean unregisterWeakEscrowTokenRemovedListener(IWeakEscrowTokenRemovedListener iWeakEscrowTokenRemovedListener) {
        return this.mListeners.unregister(iWeakEscrowTokenRemovedListener);
    }

    public final void notifyWeakEscrowTokenRemovedListeners(long j, int i) {
        int beginBroadcast = this.mListeners.beginBroadcast();
        while (beginBroadcast > 0) {
            beginBroadcast--;
            try {
                try {
                    this.mListeners.getBroadcastItem(beginBroadcast).onWeakEscrowTokenRemoved(j, i);
                } catch (RemoteException e) {
                    Slog.e("SyntheticPasswordManager", "Exception while notifying WeakEscrowTokenRemovedListener.", e);
                }
            } finally {
                this.mListeners.finishBroadcast();
            }
        }
    }

    public void writeVendorAuthSecret(byte[] bArr, SyntheticPassword syntheticPassword, int i) {
        saveState("vendor_auth_secret", SyntheticPasswordCrypto.encrypt(syntheticPassword.deriveVendorAuthSecretEncryptionKey(), new byte[0], bArr), 0L, i);
        syncState(i);
    }

    public byte[] readVendorAuthSecret(SyntheticPassword syntheticPassword, int i) {
        byte[] loadState = loadState("vendor_auth_secret", 0L, i);
        if (loadState == null) {
            return null;
        }
        return SyntheticPasswordCrypto.decrypt(syntheticPassword.deriveVendorAuthSecretEncryptionKey(), new byte[0], loadState);
    }

    public final void makeSpmLog(int i, long j, String str) {
        StringBuilder sb = new StringBuilder(1024);
        sb.append("SyntheticPasswordManager state file event ");
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("Contents : ");
        sb.append(str);
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("Time : ");
        sb.append(makeTime());
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("User id : ");
        sb.append(i);
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("User protectorId : ");
        sb.append(j);
        sb.append(String.format(", %x", Long.valueOf(j)));
        sb.append(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        sb.append("Callers : \n");
        sb.append(Debug.getCallers(10, "    "));
        this.mStorage.addLog(0, sb.toString());
    }

    public final String makeTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        return String.format(Locale.US, "%02d-%02d %02d:%02d:%02d.%03d ", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)));
    }

    public void saveEscrowData(byte[] bArr, byte[] bArr2, int i) {
        saveState("e0", bArr, 0L, i);
        saveState("p1", bArr2, 0L, i);
    }

    public void migratePwdDataForKnox(long j, int i) {
        byte[] loadState = loadState("pwd", j, i);
        if (loadState == null) {
            return;
        }
        saveState("pwd", PasswordData.fromBytesForMigration(loadState).toBytes(), j, i);
        syncState(i);
        SDPLog.m39d("SyntheticPasswordManager", "Migrated password data for user " + i);
    }

    public class SdpSyntheticPasswordManager {
        public SyntheticPasswordManager spManager;

        public final boolean isNeedToEnableSdpMdfppModeForSystem(int i) {
            return false;
        }

        public final boolean isSdpMdfppMode(int i) {
            return false;
        }

        public final boolean isSdpMdfppModeEnabledForSystem(int i) {
            return false;
        }

        public final boolean isSdpUser(int i) {
            return false;
        }

        public SdpSyntheticPasswordManager(SyntheticPasswordManager syntheticPasswordManager) {
            this.spManager = syntheticPasswordManager;
        }

        public final void cacheSecureMode(int i, int i2) {
            SyntheticPasswordMdfpp.cacheSecureMode(i, i2);
            if (SyntheticPasswordManager.DEBUG) {
                Slog.d("SyntheticPasswordManager.SDP", String.format("Cache - [ Secure Mode : %d, UserId : %d ]", Integer.valueOf(i2), Integer.valueOf(i)));
            }
        }

        public final int getSecureMode(final int i) {
            int i2;
            boolean z;
            try {
                i2 = SyntheticPasswordMdfpp.getSecureMode(i);
            } catch (SyntheticPasswordMdfpp.EmptySlotException e) {
                if (!this.spManager.isWeaverSupported()) {
                    if (SyntheticPasswordManager.DEBUG) {
                        e.printStackTrace();
                    } else {
                        Slog.d("SyntheticPasswordManager.SDP", e.getMessage());
                    }
                }
                i2 = -1;
            }
            if (i2 == -1) {
                if (!this.spManager.isWeaverSupported() && (isSpecificProcessRequired(i) || isSdpMdfppModeEnabledForSystem(i))) {
                    if (isNeedToEnableSdpMdfppModeForSystem(i)) {
                        SDPLog.m38d("Secure mode not set yet for System (Device owner) " + i + " using AOSP SP");
                        z = true;
                    } else {
                        if (isSdpMdfppModeEnabledForSystem(i)) {
                            SDPLog.m38d("Secure mode was set for System (Device owner) " + i + " using AOSP SP");
                        }
                        z = false;
                    }
                    if (!z) {
                        i2 = ((Integer) this.spManager.getLockSettingsInternal().map(new Function() { // from class: com.android.server.locksettings.SyntheticPasswordManager$SdpSyntheticPasswordManager$$ExternalSyntheticLambda0
                            @Override // java.util.function.Function
                            public final Object apply(Object obj) {
                                Integer lambda$getSecureMode$0;
                                lambda$getSecureMode$0 = SyntheticPasswordManager.SdpSyntheticPasswordManager.lambda$getSecureMode$0(i, (LockSettingsInternal) obj);
                                return lambda$getSecureMode$0;
                            }
                        }).orElse(-1)).intValue();
                        if (i2 == -1) {
                            if (isDualDarUser(i)) {
                                SDPLog.m38d("Secure Mode doesn't support for initial DualDAR user anymore");
                            } else {
                                SDPLog.m38d("No secure mode for user " + i);
                                i2 = 2;
                            }
                        }
                    }
                }
                i2 = 0;
            }
            cacheSecureMode(i, i2);
            if (SyntheticPasswordManager.DEBUG) {
                Slog.d("SyntheticPasswordManager.SDP", String.format("Get - [ Secure Mode : %d, UserId : %d ]", Integer.valueOf(i2), Integer.valueOf(i)));
            }
            Slog.d("SyntheticPasswordManager.SDP", String.format("Secure mode for user %d = %d", Integer.valueOf(i), Integer.valueOf(i2)));
            return i2;
        }

        public static /* synthetic */ Integer lambda$getSecureMode$0(int i, LockSettingsInternal lockSettingsInternal) {
            return Integer.valueOf(lockSettingsInternal.getSecureMode(i));
        }

        public final boolean isDualDarUser(int i) {
            boolean z;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                UserInfo userInfo = SyntheticPasswordManager.this.getUserManagerInternal().getUserInfo(i);
                if (userInfo != null) {
                    if ((userInfo.flags & 100663296) != 0) {
                        z = true;
                        return z;
                    }
                }
                z = false;
                return z;
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final boolean isSpecificProcessRequired(int i) {
            SDPLog.m43i("user" + i + " - isSpecificProcessRequired : false, is Sdp user? " + isSdpUser(i));
            return false;
        }
    }

    public int getSecureMode(long j, int i) {
        byte[] loadState = loadState("pwd", j, i);
        if (loadState == null) {
            Slog.w("SyntheticPasswordManager.SDP", "getSecureMode: encountered empty password data for user " + i);
            return -1;
        }
        return PasswordData.fromBytes(loadState).secureMode;
    }

    public final Optional getLockSettingsInternal() {
        if (this.mLockSettingsInternal == null) {
            this.mLockSettingsInternal = (LockSettingsInternal) LocalServices.getService(LockSettingsInternal.class);
        }
        return Optional.ofNullable(this.mLockSettingsInternal);
    }

    public final UserManagerInternal getUserManagerInternal() {
        return (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
    }
}
