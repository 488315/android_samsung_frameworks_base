package com.android.server.locksettings;

import android.app.admin.PasswordMetrics;
import android.content.Context;
import android.content.pm.UserInfo;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.weaver.IWeaver;
import android.hardware.weaver.WeaverConfig;
import android.hardware.weaver.WeaverReadResponse;
import android.os.Binder;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.SystemProperties;
import android.os.UserManager;
import android.provider.Settings;
import android.security.Scrypt;
import android.security.keystore.KeyProtection;
import android.service.gatekeeper.GateKeeperResponse;
import android.service.gatekeeper.IGateKeeperService;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.ICheckCredentialProgressCallback;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.knox.dar.sdp.SDPLog;
import com.android.server.utils.Slogf;

import libcore.util.HexEncoding;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.lock.LsLog;
import com.samsung.android.lock.SPBnRManager;
import com.samsung.android.service.HermesService.IHermesService;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
class SyntheticPasswordManager {
    public static final boolean DEBUG;
    public final Context mContext;
    public final PasswordSlotManager mPasswordSlotManager;
    public final LockSettingsStorage mStorage;
    public final UserManager mUserManager;
    public volatile IWeaver mWeaver;
    public WeaverConfig mWeaverConfig;
    public static final byte[] DEFAULT_PASSWORD = "default-password".getBytes();
    public static final byte[] DEFAULT_TOKEN = "This-byte-array-is-default-token".getBytes();
    public static final boolean SECURITY_UNPACK =
            SemFloatingFeature.getInstance()
                    .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK");
    public static final byte[] PERSONALIZATION_SECDISCARDABLE =
            "secdiscardable-transform".getBytes();
    public static final byte[] PERSONALIZATION_KEY_STORE_PASSWORD = "keystore-password".getBytes();
    public static final byte[] PERSONALIZATION_USER_GK_AUTH = "user-gk-authentication".getBytes();
    public static final byte[] PERSONALIZATION_SP_GK_AUTH = "sp-gk-authentication".getBytes();
    public static final byte[] PERSONALIZATION_FBE_KEY = "fbe-key".getBytes();
    public static final byte[] PERSONALIZATION_AUTHSECRET_KEY = "authsecret-hal".getBytes();
    public static final byte[] PERSONALIZATION_AUTHSECRET_ENCRYPTION_KEY =
            "vendor-authsecret-encryption-key".getBytes();
    public static final byte[] PERSONALIZATION_SP_SPLIT = "sp-split".getBytes();
    public static final byte[] PERSONALIZATION_PASSWORD_HASH = "pw-hash".getBytes();
    public static final byte[] PERSONALIZATION_E0 = "e0-encryption".getBytes();
    public static final byte[] PERSONALIZATION_WEAVER_PASSWORD = "weaver-pwd".getBytes();
    public static final byte[] PERSONALIZATION_WEAVER_KEY = "weaver-key".getBytes();
    public static final byte[] PERSONALIZATION_WEAVER_TOKEN = "weaver-token".getBytes();
    public static final byte[] PERSONALIZATION_PASSWORD_METRICS = "password-metrics".getBytes();
    public static final byte[] PERSONALIZATION_CONTEXT =
            "android-synthetic-password-personalization-context".getBytes();
    public static final byte[] PERSONALIZATION_SDP_MASTER_KEY = "sdp-master-key".getBytes();
    public final RemoteCallbackList mListeners = new RemoteCallbackList();
    public final ArrayMap tokenMap = new ArrayMap();
    public final SdpSyntheticPasswordManager mSdpSyntheticPasswordManager =
            new SdpSyntheticPasswordManager(this, this);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AuthenticationResult {
        public VerifyCredentialResponse gkResponse;
        public SyntheticPassword syntheticPassword;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ConflictInfo {
        public final long handle;
        public final int userId;

        public ConflictInfo(long j, int i) {
            this.handle = j;
            this.userId = i;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PasswordData {
        public int credentialType;
        public byte[] passwordHandle;
        public int pinLength;
        public byte[] salt;
        public byte scryptLogN;
        public byte scryptLogP;
        public byte scryptLogR;
        public int secureMode;

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
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Inside fromBytes() - result.pinLength : "),
                    passwordData.pinLength,
                    "SyntheticPasswordManager.SDP");
            passwordData.secureMode = 0;
            GestureWakeup$$ExternalSyntheticOutline0.m(
                    new StringBuilder("2. result.secureMode : "),
                    passwordData.secureMode,
                    "SyntheticPasswordManager.SDP");
            return passwordData;
        }

        public final byte[] toBytes() {
            int length = this.salt.length + 15;
            byte[] bArr = this.passwordHandle;
            ByteBuffer allocate =
                    ByteBuffer.allocate(length + (bArr != null ? bArr.length : 0) + 4);
            int i = this.credentialType;
            if (i < -32768 || i > 32767) {
                throw new IllegalArgumentException(
                        "Unknown credential type: " + this.credentialType);
            }
            allocate.putInt(i);
            allocate.put(this.scryptLogN);
            allocate.put(this.scryptLogR);
            allocate.put(this.scryptLogP);
            allocate.putInt(this.salt.length);
            allocate.put(this.salt);
            byte[] bArr2 = this.passwordHandle;
            if (bArr2 == null || bArr2.length <= 0) {
                allocate.putInt(0);
            } else {
                allocate.putInt(bArr2.length);
                allocate.put(this.passwordHandle);
            }
            allocate.putInt(this.pinLength);
            return allocate.array();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SdpSyntheticPasswordManager {
        public final SyntheticPasswordManager spManager;

        /* renamed from: -$$Nest$mgetSecureMode, reason: not valid java name */
        public static int m644$$Nest$mgetSecureMode(
                SdpSyntheticPasswordManager sdpSyntheticPasswordManager, int i) {
            int i2;
            sdpSyntheticPasswordManager.getClass();
            int i3 = 0;
            try {
                i2 = SyntheticPasswordMdfpp.getSecureMode(i);
            } catch (SyntheticPasswordMdfpp.EmptySlotException e) {
                sdpSyntheticPasswordManager.spManager.getClass();
                if (!SystemProperties.getBoolean("security.securehw.available", false)) {
                    if (SyntheticPasswordManager.DEBUG) {
                        e.printStackTrace();
                    } else {
                        Slog.d("SyntheticPasswordManager.SDP", e.getMessage());
                    }
                }
                i2 = -1;
            }
            if (i2 == -1) {
                sdpSyntheticPasswordManager.spManager.getClass();
                if (!SystemProperties.getBoolean("security.securehw.available", false)) {
                    SDPLog.i(
                            "user"
                                    + i
                                    + " - isSpecificProcessRequired : false, is Sdp user? false");
                }
            } else {
                i3 = i2;
            }
            SparseArray sparseArray = SyntheticPasswordMdfpp.mSecureModeCache;
            synchronized (sparseArray) {
                sparseArray.put(i, Integer.valueOf(i3));
            }
            boolean z = SyntheticPasswordManager.DEBUG;
            if (z) {
                Slog.d(
                        "SyntheticPasswordManager.SDP",
                        String.format(
                                "Cache - [ Secure Mode : %d, UserId : %d ]",
                                Integer.valueOf(i3), Integer.valueOf(i)));
            }
            if (z) {
                Slog.d(
                        "SyntheticPasswordManager.SDP",
                        String.format(
                                "Get - [ Secure Mode : %d, UserId : %d ]",
                                Integer.valueOf(i3), Integer.valueOf(i)));
            }
            Slog.d(
                    "SyntheticPasswordManager.SDP",
                    String.format(
                            "Secure mode for user %d = %d",
                            Integer.valueOf(i), Integer.valueOf(i3)));
            return i3;
        }

        public SdpSyntheticPasswordManager(
                SyntheticPasswordManager syntheticPasswordManager,
                SyntheticPasswordManager syntheticPasswordManager2) {
            this.spManager = syntheticPasswordManager2;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SyntheticPassword {
        public byte[] mEncryptedEscrowSplit0;
        public byte[] mEscrowSplit1;
        public boolean mSecureFolderAuthToken = false;
        public byte[] mSyntheticPassword;
        public final byte mVersion;

        public SyntheticPassword(byte b) {
            this.mVersion = b;
        }

        public final byte[] deriveSubkey(byte[] bArr) {
            return this.mVersion == 3
                    ? new SP800Derive(this.mSyntheticPassword)
                            .withContext(bArr, SyntheticPasswordManager.PERSONALIZATION_CONTEXT)
                    : SyntheticPasswordCrypto.personalizedHash(bArr, this.mSyntheticPassword);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SyntheticPasswordBlob {
        public byte[] mContent;
        public byte mProtectorType;
        public byte mVersion;

        public static SyntheticPasswordBlob fromBytes(byte[] bArr) {
            SyntheticPasswordBlob syntheticPasswordBlob = new SyntheticPasswordBlob();
            syntheticPasswordBlob.mVersion = bArr[0];
            syntheticPasswordBlob.mProtectorType = bArr[1];
            syntheticPasswordBlob.mContent = Arrays.copyOfRange(bArr, 2, bArr.length);
            return syntheticPasswordBlob;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TokenData {
        public byte[] aggregatedSecret;
        public LockPatternUtils.EscrowTokenStateChangeCallback mCallback;
        public int mType;
        public byte[] secdiscardableOnDisk;
        public byte[] weaverSecret;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WeaverDiedRecipient implements IBinder.DeathRecipient {
        public WeaverDiedRecipient() {}

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Slog.wtf("SyntheticPasswordManager", "Weaver service has died");
            SyntheticPasswordManager.this.mWeaver.asBinder().unlinkToDeath(this, 0);
            SyntheticPasswordManager.this.mWeaver = null;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class WeaverResult {
        public static final SparseArray mWeaverResults = new SparseArray();
        public int mOp;
        public int mSlot;
        public int mStatus;
        public int mUserId;

        public static void begin(int i, int i2, int i3) {
            WeaverResult weaverResult = new WeaverResult();
            weaverResult.mOp = i;
            weaverResult.mSlot = i2;
            weaverResult.mUserId = i3;
            weaverResult.mStatus = -999;
            SparseArray sparseArray = mWeaverResults;
            synchronized (sparseArray) {
                sparseArray.put(i3, weaverResult);
            }
        }

        public static void finish(int i) {
            SparseArray sparseArray = mWeaverResults;
            synchronized (sparseArray) {
                try {
                    WeaverResult weaverResult = (WeaverResult) sparseArray.get(i);
                    if (weaverResult != null) {
                        SDPLog.d(null, weaverResult.toString());
                        sparseArray.remove(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public static void finishOff(int i, String str) {
            SparseArray sparseArray = mWeaverResults;
            synchronized (sparseArray) {
                try {
                    WeaverResult bySlotIdLocked = getBySlotIdLocked(i);
                    if (bySlotIdLocked != null) {
                        Locale locale = Locale.US;
                        SDPLog.d(
                                null,
                                str
                                        + " [ op : "
                                        + bySlotIdLocked.mOp
                                        + ", sl : "
                                        + bySlotIdLocked.mSlot
                                        + ", u : "
                                        + bySlotIdLocked.mUserId
                                        + " ]");
                        sparseArray.remove(bySlotIdLocked.mUserId);
                    }
                } catch (Throwable th) {
                    throw th;
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

        public final String toString() {
            String str;
            int i = this.mUserId;
            int i2 = this.mSlot;
            int i3 = this.mOp;
            if (i3 < 0 || i3 > 1) {
                Locale locale = Locale.US;
                return AmFmBandRange$$ExternalSyntheticOutline0.m(
                        i,
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i3,
                                i2,
                                "Invalid operation for weaver [ op : ",
                                ", sl : ",
                                ", u : "),
                        " ]");
            }
            int i4 = this.mStatus;
            if (i4 == -999) {
                Locale locale2 = Locale.US;
                return AmFmBandRange$$ExternalSyntheticOutline0.m(
                        i,
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i3, i2, "No update for weaver [ op : ", ", sl : ", ", u : "),
                        " ]");
            }
            if (i4 == 0) {
                str = "Weaver read status ok";
            } else if (i4 == 1) {
                str = "weaver read failed (FAILED)";
            } else if (i4 == 2) {
                str = "weaver read failed (INCORRECT_KEY)";
            } else if (i4 != 3) {
                str = "weaver read unknown status " + this.mStatus;
            } else {
                str = "weaver read failed (THROTTLE)";
            }
            Locale locale3 = Locale.US;
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i,
                            "Result of weaver ",
                            i3 == 0 ? "enrollment" : "verification",
                            " for user ",
                            " [ sl : ");
            m.append(i2);
            m.append(", st : ");
            m.append(str);
            m.append(" ]");
            return m.toString();
        }
    }

    static {
        DEBUG =
                "userdebug".equals(SystemProperties.get("ro.build.type"))
                        || "eng".equals(SystemProperties.get("ro.build.type"));
    }

    public SyntheticPasswordManager(
            Context context,
            LockSettingsStorage lockSettingsStorage,
            UserManager userManager,
            PasswordSlotManager passwordSlotManager) {
        this.mContext = context;
        this.mStorage = lockSettingsStorage;
        this.mUserManager = userManager;
        this.mPasswordSlotManager = passwordSlotManager;
    }

    public static byte[] bytesToHex(byte[] bArr) {
        return HexEncoding.encodeToString(bArr).getBytes();
    }

    public static int fakeUserId(int i) {
        return i + 100000;
    }

    public static String getProtectorKeyAlias(long j) {
        return TextUtils.formatSimple(
                "%s%x", new Object[] {"synthetic_password_", Long.valueOf(j)});
    }

    private native long nativeSidFromPasswordHandle(byte[] bArr);

    public static byte[] stretchedLskfToGkPassword(byte[] bArr) {
        return SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_USER_GK_AUTH, bArr);
    }

    public static byte[] transformUnderSecdiscardable(byte[] bArr, byte[] bArr2) {
        return ArrayUtils.concat(
                new byte[][] {
                    bArr,
                    SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_SECDISCARDABLE, bArr2)
                });
    }

    public final long addPendingToken(
            byte[] bArr,
            int i,
            int i2,
            LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback) {
        long nextLong;
        if (SECURITY_UNPACK) {
            bArr = DEFAULT_TOKEN;
        }
        do {
            nextLong = SecureRandomUtils.RNG.nextLong();
        } while (nextLong == 0);
        if (!this.tokenMap.containsKey(Integer.valueOf(i2))) {
            this.tokenMap.put(Integer.valueOf(i2), new ArrayMap());
        }
        TokenData tokenData = new TokenData();
        tokenData.mType = i;
        byte[] randomBytes =
                SecureRandomUtils.randomBytes(
                        EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION);
        if (getWeaverService() != null) {
            byte[] randomBytes2 = SecureRandomUtils.randomBytes(this.mWeaverConfig.valueSize);
            tokenData.weaverSecret = randomBytes2;
            tokenData.secdiscardableOnDisk =
                    SyntheticPasswordCrypto.encrypt(
                            randomBytes2, PERSONALIZATION_WEAVER_TOKEN, randomBytes);
        } else {
            tokenData.secdiscardableOnDisk = randomBytes;
            tokenData.weaverSecret = null;
        }
        tokenData.aggregatedSecret = transformUnderSecdiscardable(bArr, randomBytes);
        tokenData.mCallback = escrowTokenStateChangeCallback;
        SdpSyntheticPasswordManager.m644$$Nest$mgetSecureMode(
                this.mSdpSyntheticPasswordManager, i2);
        SDPLog.i("Not Sdp Mdfpp Mode. keyingMaterial null");
        ((ArrayMap) this.tokenMap.get(Integer.valueOf(i2))).put(Long.valueOf(nextLong), tokenData);
        return nextLong;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0022  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void checkWeaverStatus() {
        /*
            r7 = this;
            r0 = 0
            r1 = 0
            android.hardware.weaver.IWeaver r2 = r7.getWeaverService()     // Catch: java.lang.Exception -> La
            if (r2 == 0) goto L10
            r2 = 1
            goto L11
        La:
            r2 = move-exception
            java.lang.String r3 = "Unexpected exception while check weaver availability"
            com.android.server.knox.dar.sdp.SDPLog.e(r2, r1, r3)
        L10:
            r2 = r0
        L11:
            java.lang.String r3 = "security.securehw.available"
            boolean r0 = android.os.SystemProperties.getBoolean(r3, r0)
            if (r0 != 0) goto L22
            java.lang.String r7 = "SyntheticPasswordManager.SDP"
            java.lang.String r0 = "Device does not support weaver"
            android.util.Slog.d(r7, r0)
            return
        L22:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r3 = "Weaver is "
            r0.<init>(r3)
            if (r2 == 0) goto L2f
            java.lang.String r3 = "now"
            goto L31
        L2f:
            java.lang.String r3 = "NOT"
        L31:
            java.lang.String r4 = " available"
            java.lang.String r0 = android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0.m(r0, r3, r4)
            boolean r3 = com.android.server.locksettings.SyntheticPasswordManager.DEBUG
            if (r3 == 0) goto L58
            if (r2 == 0) goto L58
            java.util.Locale r2 = java.util.Locale.US
            android.hardware.weaver.WeaverConfig r7 = r7.mWeaverConfig
            int r2 = r7.slots
            int r3 = r7.keySize
            int r7 = r7.valueSize
            java.lang.String r4 = " with slots("
            java.lang.String r5 = "), kSize("
            java.lang.String r6 = ") and vSize("
            java.lang.StringBuilder r2 = com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0.m(r2, r3, r4, r5, r6)
            java.lang.String r3 = ")"
            java.lang.String r7 = android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0.m(r7, r2, r3)
            goto L5a
        L58:
            java.lang.String r7 = "!"
        L5a:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            r2.append(r7)
            java.lang.String r7 = r2.toString()
            com.android.server.knox.dar.sdp.SDPLog.d(r1, r7)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locksettings.SyntheticPasswordManager.checkWeaverStatus():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x002f, code lost:

       if (r5 >= 6) goto L14;
    */
    /* JADX WARN: Removed duplicated region for block: B:49:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long createLskfBasedProtector(
            android.service.gatekeeper.IGateKeeperService r17,
            com.android.internal.widget.LockscreenCredential r18,
            long r19,
            com.android.internal.widget.LockscreenCredential r21,
            com.android.server.locksettings.SyntheticPasswordManager.SyntheticPassword r22,
            int r23) {
        /*
            Method dump skipped, instructions count: 587
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locksettings.SyntheticPasswordManager.createLskfBasedProtector(android.service.gatekeeper.IGateKeeperService,"
                    + " com.android.internal.widget.LockscreenCredential, long,"
                    + " com.android.internal.widget.LockscreenCredential,"
                    + " com.android.server.locksettings.SyntheticPasswordManager$SyntheticPassword,"
                    + " int):long");
    }

    public byte[] createSpBlob(String str, byte[] bArr, byte[] bArr2, long j) {
        byte[] bArr3 = SyntheticPasswordCrypto.PROTECTOR_SECRET_PERSONALIZATION;
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, new SecureRandom());
            SecretKey generateKey = keyGenerator.generateKey();
            KeyStore keyStore = SyntheticPasswordCrypto.getKeyStore();
            KeyProtection.Builder criticalToDeviceEncryption =
                    new KeyProtection.Builder(2)
                            .setBlockModes("GCM")
                            .setEncryptionPaddings("NoPadding")
                            .setCriticalToDeviceEncryption(true);
            if (j != 0) {
                criticalToDeviceEncryption
                        .setUserAuthenticationRequired(true)
                        .setBoundToSpecificSecureUserId(j)
                        .setUserAuthenticationValidityDurationSeconds(15);
            }
            KeyProtection build = criticalToDeviceEncryption.build();
            criticalToDeviceEncryption.setRollbackResistant(true);
            KeyProtection build2 = criticalToDeviceEncryption.build();
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(generateKey);
            try {
                keyStore.setEntry(str, secretKeyEntry, build2);
                Slog.i("SyntheticPasswordCrypto", "Using rollback-resistant key");
            } catch (KeyStoreException unused) {
                Slog.w(
                        "SyntheticPasswordCrypto",
                        "Rollback-resistant keys unavailable.  Falling back to"
                            + " non-rollback-resistant key");
                keyStore.setEntry(str, secretKeyEntry, build);
            }
            return SyntheticPasswordCrypto.encrypt(
                    SyntheticPasswordCrypto.encrypt(
                            bArr2, SyntheticPasswordCrypto.PROTECTOR_SECRET_PERSONALIZATION, bArr),
                    generateKey);
        } catch (IOException
                | InvalidKeyException
                | KeyStoreException
                | NoSuchAlgorithmException
                | CertificateException
                | InvalidParameterSpecException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            Slog.e("SyntheticPasswordCrypto", "Failed to create blob", e);
            throw new IllegalStateException("Failed to encrypt blob", e);
        }
    }

    public final void createSyntheticPasswordBlob(
            long j, byte b, SyntheticPassword syntheticPassword, byte[] bArr, long j2, int i) {
        byte[] decrypt;
        if (b == 1 || b == 2) {
            byte[] bArr2 = syntheticPassword.mEncryptedEscrowSplit0;
            decrypt =
                    bArr2 == null
                            ? null
                            : SyntheticPasswordCrypto.decrypt(
                                    syntheticPassword.mSyntheticPassword,
                                    PERSONALIZATION_E0,
                                    bArr2);
        } else {
            decrypt = syntheticPassword.mSyntheticPassword;
        }
        byte[] createSpBlob = createSpBlob(getProtectorKeyAlias(j), decrypt, bArr, j2);
        byte b2 = syntheticPassword.mVersion != 3 ? (byte) 2 : (byte) 3;
        byte[] bArr3 = new byte[createSpBlob.length + 2];
        bArr3[0] = b2;
        bArr3[1] = b;
        System.arraycopy(createSpBlob, 0, bArr3, 2, createSpBlob.length);
        saveState("spblob", bArr3, j, i);
    }

    public final boolean createTokenBasedProtector(
            int i, long j, SyntheticPassword syntheticPassword) {
        TokenData tokenData;
        if (!this.tokenMap.containsKey(Integer.valueOf(i))
                || (tokenData =
                                (TokenData)
                                        ((ArrayMap) this.tokenMap.get(Integer.valueOf(i)))
                                                .get(Long.valueOf(j)))
                        == null) {
            return false;
        }
        if (!loadEscrowData(i, syntheticPassword)) {
            Slog.w("SyntheticPasswordManager", "User is not escrowable");
            return false;
        }
        if (!loadEscrowData(i, syntheticPassword)) {
            Slog.w("SyntheticPasswordManager", "User is not escrowable");
            return false;
        }
        Slogf.i(
                "SyntheticPasswordManager",
                "Creating token-based protector %016x for user %d",
                Long.valueOf(j),
                Integer.valueOf(i));
        IWeaver weaverService = getWeaverService();
        if (weaverService != null) {
            int nextAvailableWeaverSlot = getNextAvailableWeaverSlot();
            Slogf.i(
                    "SyntheticPasswordManager",
                    "Using Weaver slot %d for new token-based protector",
                    Integer.valueOf(nextAvailableWeaverSlot));
            WeaverResult.begin(0, nextAvailableWeaverSlot, i);
            if (weaverEnroll(weaverService, nextAvailableWeaverSlot, null, tokenData.weaverSecret)
                    == null) {
                Slog.e(
                        "SyntheticPasswordManager",
                        "Failed to enroll weaver secret when activating token");
                WeaverResult.finishOff(
                        nextAvailableWeaverSlot, "Failed to enroll token for user " + i);
                return false;
            }
            WeaverResult.finish(i);
            ByteBuffer allocate = ByteBuffer.allocate(5);
            allocate.put((byte) 1);
            allocate.putInt(nextAvailableWeaverSlot);
            saveState("weaver", allocate.array(), j, i);
            this.mPasswordSlotManager.markSlotInUse(nextAvailableWeaverSlot);
        }
        saveState("secdis", tokenData.secdiscardableOnDisk, j, i);
        this.mSdpSyntheticPasswordManager.getClass();
        createSyntheticPasswordBlob(
                j,
                tokenData.mType != 1 ? (byte) 1 : (byte) 2,
                syntheticPassword,
                tokenData.aggregatedSecret,
                0L,
                i);
        syncState(i);
        ((ArrayMap) this.tokenMap.get(Integer.valueOf(i))).remove(Long.valueOf(j));
        LockPatternUtils.EscrowTokenStateChangeCallback escrowTokenStateChangeCallback =
                tokenData.mCallback;
        if (escrowTokenStateChangeCallback != null) {
            escrowTokenStateChangeCallback.onEscrowTokenActivated(j, i);
        }
        return true;
    }

    public byte[] decryptSpBlob(String str, byte[] bArr, byte[] bArr2) {
        try {
            SecretKey secretKey =
                    (SecretKey) SyntheticPasswordCrypto.getKeyStore().getKey(str, null);
            if (secretKey != null) {
                return SyntheticPasswordCrypto.decrypt(
                        bArr2,
                        SyntheticPasswordCrypto.PROTECTOR_SECRET_PERSONALIZATION,
                        SyntheticPasswordCrypto.decrypt(bArr, secretKey));
            }
            throw new IllegalStateException("SP protector key is missing: " + str);
        } catch (IOException
                | InvalidAlgorithmParameterException
                | InvalidKeyException
                | KeyStoreException
                | NoSuchAlgorithmException
                | UnrecoverableKeyException
                | CertificateException
                | BadPaddingException
                | IllegalBlockSizeException
                | NoSuchPaddingException e) {
            Slog.e("SyntheticPasswordCrypto", "Failed to decrypt blob", e);
            throw new IllegalStateException("Failed to decrypt blob", e);
        }
    }

    public final void destroyLskfBasedProtector(int i, long j) {
        Slogf.i(
                "SyntheticPasswordManager",
                "Destroying LSKF-based protector %016x for user %d",
                Long.valueOf(j),
                Integer.valueOf(i));
        destroyState(i, "spblob", j);
        destroyProtectorKey(getProtectorKeyAlias(j));
        destroyState(i, "secdis", j);
        if (hasState(i, "weaver", j)) {
            destroyWeaverSlot(i, j);
        }
        destroyState(i, "pwd", j);
        destroyState(i, "metrics", j);
        SPBnRManager.deleteBackup(i, j);
    }

    public void destroyProtectorKey(String str) {
        try {
            SyntheticPasswordCrypto.getKeyStore().deleteEntry(str);
            Slog.i("SyntheticPasswordCrypto", "Deleted SP protector key " + str);
        } catch (IOException
                | KeyStoreException
                | NoSuchAlgorithmException
                | CertificateException e) {
            Slog.e("SyntheticPasswordCrypto", "Failed to delete SP protector key " + str, e);
        }
    }

    public final void destroyState(int i, String str, long j) {
        if ("spblob".equals(str)) {
            LsLog.enroll(
                    String.format(
                            "User %d Spblob destroy [%016x]\n%s",
                            Integer.valueOf(i), Long.valueOf(j), Debug.getCallers(7, "    ")));
        }
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        lockSettingsStorage.deleteFile(
                lockSettingsStorage.getSyntheticPasswordStateFileForUser(i, str, j));
    }

    public final void destroyTokenBasedProtector(int i, long j) {
        Slogf.i(
                "SyntheticPasswordManager",
                "Destroying token-based protector %016x for user %d",
                Long.valueOf(j),
                Integer.valueOf(i));
        SyntheticPasswordBlob fromBytes =
                SyntheticPasswordBlob.fromBytes(
                        this.mStorage.readSyntheticPasswordState(i, "spblob", j));
        destroyState(i, "spblob", j);
        destroyProtectorKey(getProtectorKeyAlias(j));
        destroyState(i, "secdis", j);
        if (hasState(i, "weaver", j)) {
            destroyWeaverSlot(i, j);
        }
        if (fromBytes.mProtectorType == 2) {
            int beginBroadcast = this.mListeners.beginBroadcast();
            while (beginBroadcast > 0) {
                beginBroadcast--;
                try {
                    try {
                        this.mListeners
                                .getBroadcastItem(beginBroadcast)
                                .onWeakEscrowTokenRemoved(j, i);
                    } catch (RemoteException e) {
                        Slog.e(
                                "SyntheticPasswordManager",
                                "Exception while notifying WeakEscrowTokenRemovedListener.",
                                e);
                    }
                } finally {
                    this.mListeners.finishBroadcast();
                }
            }
        }
    }

    public final void destroyWeaverSlot(int i, long j) {
        int loadWeaverSlot = loadWeaverSlot(i, j);
        destroyState(i, "weaver", j);
        if (loadWeaverSlot != -1) {
            IWeaver weaverService = getWeaverService();
            if (weaverService == null) {
                Slog.e(
                        "SyntheticPasswordManager",
                        "Cannot erase Weaver slot because Weaver is unavailable");
                return;
            }
            Set usedWeaverSlots = getUsedWeaverSlots();
            SDPLog.d(
                    null,
                    DualAppManagerService$$ExternalSyntheticOutline0.m(
                            loadWeaverSlot, i, "Destroy weaver slot [ sl : ", ", u : ", " ]"));
            if (((HashSet) usedWeaverSlots).contains(Integer.valueOf(loadWeaverSlot))) {
                Slogf.i(
                        "SyntheticPasswordManager",
                        "Weaver slot %d was already reused; not erasing it",
                        Integer.valueOf(loadWeaverSlot));
                SDPLog.d(null, "Destroying skipped!");
                return;
            }
            Slogf.i(
                    "SyntheticPasswordManager",
                    "Erasing Weaver slot %d",
                    Integer.valueOf(loadWeaverSlot));
            WeaverResult.begin(0, loadWeaverSlot, i);
            weaverEnroll(weaverService, loadWeaverSlot, null, null);
            WeaverResult.finish(i);
            PasswordSlotManager passwordSlotManager = this.mPasswordSlotManager;
            passwordSlotManager.ensureSlotMapLoaded();
            if (passwordSlotManager.mSlotMap.containsKey(Integer.valueOf(loadWeaverSlot))
                    && !((String) passwordSlotManager.mSlotMap.get(Integer.valueOf(loadWeaverSlot)))
                            .equals(passwordSlotManager.getMode())) {
                throw new IllegalStateException(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                loadWeaverSlot, "password slot ", " cannot be deleted"));
            }
            passwordSlotManager.mSlotMap.remove(Integer.valueOf(loadWeaverSlot));
            passwordSlotManager.saveSlotMap();
        }
    }

    public final int getFailureCount(IGateKeeperService iGateKeeperService, long j, int i) {
        PasswordData passwordData;
        int i2;
        if (j == 0) {
            Slogf.wtf(
                    "SyntheticPasswordManager",
                    "Synthetic password not found for user %d",
                    Integer.valueOf(i));
            return -1;
        }
        byte[] readSyntheticPasswordState = this.mStorage.readSyntheticPasswordState(i, "pwd", j);
        if (readSyntheticPasswordState != null) {
            passwordData = PasswordData.fromBytes(readSyntheticPasswordState);
            i2 = passwordData.credentialType;
        } else {
            passwordData = null;
            i2 = -1;
        }
        if (i2 == -1) {
            Slogf.wtf(
                    "SyntheticPasswordManager",
                    "Synthetic password not found for user %d",
                    Integer.valueOf(i));
            return -1;
        }
        if (passwordData == null || passwordData.passwordHandle == null) {
            Slog.e("SyntheticPasswordManager", "Missing Gatekeeper handle for nonempty LSKF");
            return -1;
        }
        try {
            int failureCount = iGateKeeperService.getFailureCount(fakeUserId(i));
            Slog.d("SyntheticPasswordManager", "getFailureCount = " + failureCount);
            return failureCount;
        } catch (RemoteException e) {
            Slog.e("SyntheticPasswordManager", "getFailureCount, RemoteException " + i, e);
            return -1;
        }
    }

    public final int getFailureCount(IHermesService iHermesService, long j, int i) {
        if (j == 0) {
            Slogf.wtf(
                    "SyntheticPasswordManager",
                    "Synthetic password not found for user %d",
                    Integer.valueOf(i));
            return -1;
        }
        byte[] readSyntheticPasswordState = this.mStorage.readSyntheticPasswordState(i, "pwd", j);
        if ((readSyntheticPasswordState != null
                        ? PasswordData.fromBytes(readSyntheticPasswordState).credentialType
                        : -1)
                == -1) {
            Slogf.wtf(
                    "SyntheticPasswordManager",
                    "Synthetic password not found for user %d",
                    Integer.valueOf(i));
            return -1;
        }
        int loadWeaverSlot = loadWeaverSlot(i, j);
        if (loadWeaverSlot == -1) {
            Slog.e("SyntheticPasswordManager", "Protector uses Weaver, but Weaver is unavailable");
            return -1;
        }
        try {
            int failureCount = iHermesService.getFailureCount(loadWeaverSlot);
            Slog.d("SyntheticPasswordManager", "getFailureCount = " + failureCount);
            return failureCount;
        } catch (RemoteException e) {
            Slog.e(
                    "SyntheticPasswordManager",
                    "getFailureCount, RemoteException " + loadWeaverSlot,
                    e);
            return -1;
        }
    }

    public final int getNextAvailableWeaverSlot() {
        Set usedWeaverSlots = getUsedWeaverSlots();
        PasswordSlotManager passwordSlotManager = this.mPasswordSlotManager;
        passwordSlotManager.ensureSlotMapLoaded();
        usedWeaverSlots.addAll(Collections.unmodifiableSet(passwordSlotManager.mSlotMap.keySet()));
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0)
                == 0) {
            LockSettingsStorage.PersistentData readPersistentDataBlock =
                    this.mStorage.readPersistentDataBlock();
            if (readPersistentDataBlock.type == 2) {
                ((HashSet) usedWeaverSlots).add(Integer.valueOf(readPersistentDataBlock.userId));
            }
        }
        for (int i = 0; i < this.mWeaverConfig.slots; i++) {
            if (!((HashSet) usedWeaverSlots).contains(Integer.valueOf(i))) {
                return i;
            }
        }
        throw new IllegalStateException("Run out of weaver slots.");
    }

    public final PasswordMetrics getPasswordMetrics(
            int i, long j, SyntheticPassword syntheticPassword) {
        byte[] readSyntheticPasswordState =
                this.mStorage.readSyntheticPasswordState(i, "metrics", j);
        if (readSyntheticPasswordState == null) {
            Slogf.e(
                    "SyntheticPasswordManager",
                    "Failed to read password metrics file for user %d",
                    Integer.valueOf(i));
            return null;
        }
        syntheticPassword.getClass();
        byte[] decrypt =
                SyntheticPasswordCrypto.decrypt(
                        syntheticPassword.deriveSubkey(PERSONALIZATION_PASSWORD_METRICS),
                        new byte[0],
                        readSyntheticPasswordState);
        if (decrypt == null) {
            Slogf.e(
                    "SyntheticPasswordManager",
                    "Failed to decrypt password metrics file for user %d",
                    Integer.valueOf(i));
            return null;
        }
        ByteBuffer allocate = ByteBuffer.allocate(decrypt.length);
        allocate.put(decrypt, 0, decrypt.length);
        allocate.flip();
        allocate.getInt();
        return new PasswordMetrics(
                allocate.getInt(),
                allocate.getInt(),
                allocate.getInt(),
                allocate.getInt(),
                allocate.getInt(),
                allocate.getInt(),
                allocate.getInt(),
                allocate.getInt(),
                allocate.getInt(),
                allocate.getInt());
    }

    public final byte[] getPendingTokenForDualDar(int i, long j) {
        if (this.tokenMap.containsKey(Integer.valueOf(i))) {
            return Arrays.copyOf(
                    ((TokenData)
                                    ((ArrayMap) this.tokenMap.get(Integer.valueOf(i)))
                                            .get(Long.valueOf(j)))
                            .aggregatedSecret,
                    ((TokenData)
                                    ((ArrayMap) this.tokenMap.get(Integer.valueOf(i)))
                                            .get(Long.valueOf(j)))
                            .aggregatedSecret
                            .length);
        }
        return null;
    }

    public final Set getPendingTokensForUser(int i) {
        return !this.tokenMap.containsKey(Integer.valueOf(i))
                ? Collections.emptySet()
                : new ArraySet(((ArrayMap) this.tokenMap.get(Integer.valueOf(i))).keySet());
    }

    public final LockSettingsStorage.PersistentData getSpecialUserPersistentData(int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        if (i == -9999) {
            return lockSettingsStorage.readPersistentDataBlock();
        }
        if (i != -9998) {
            throw new IllegalArgumentException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Unknown special user id "));
        }
        byte[] readFile =
                lockSettingsStorage.readFile(lockSettingsStorage.getRepairModePersistentDataFile());
        return readFile == null
                ? LockSettingsStorage.PersistentData.NONE
                : LockSettingsStorage.PersistentData.fromBytes(readFile);
    }

    public final Set getUsedWeaverSlots() {
        String str;
        String str2;
        ArrayList arrayList;
        Map listSyntheticPasswordProtectorsForAllUsers =
                this.mStorage.listSyntheticPasswordProtectorsForAllUsers("weaver");
        HashSet hashSet = new HashSet();
        HashMap hashMap = new HashMap();
        ArrayMap arrayMap = (ArrayMap) listSyntheticPasswordProtectorsForAllUsers;
        Iterator it = arrayMap.entrySet().iterator();
        while (true) {
            String str3 = null;
            String str4 = "Slot conflict at slot #";
            if (!it.hasNext()) {
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            for (Long l : (List) entry.getValue()) {
                int loadWeaverSlot =
                        loadWeaverSlot(((Integer) entry.getKey()).intValue(), l.longValue());
                if (hashSet.contains(Integer.valueOf(loadWeaverSlot))) {
                    int intValue = ((Integer) entry.getKey()).intValue();
                    if (UserManager.isVirtualUserId(intValue)) {
                        SDPLog.d(str3, str4 + loadWeaverSlot);
                        if (hashMap.containsKey(Integer.valueOf(loadWeaverSlot))) {
                            arrayList = (ArrayList) hashMap.get(Integer.valueOf(loadWeaverSlot));
                        } else {
                            arrayList = new ArrayList();
                            hashMap.put(Integer.valueOf(loadWeaverSlot), arrayList);
                        }
                        str2 = str4;
                        arrayList.add(new ConflictInfo(l.longValue(), intValue));
                        hashSet.add(Integer.valueOf(loadWeaverSlot));
                        str4 = str2;
                        str3 = null;
                    }
                }
                str2 = str4;
                hashSet.add(Integer.valueOf(loadWeaverSlot));
                str4 = str2;
                str3 = null;
            }
        }
        String str5 = "Slot conflict at slot #";
        if (!hashMap.isEmpty()) {
            for (Map.Entry entry2 : arrayMap.entrySet()) {
                int intValue2 = ((Integer) entry2.getKey()).intValue();
                if (!UserManager.isVirtualUserId(intValue2)) {
                    Iterator it2 = ((List) entry2.getValue()).iterator();
                    while (it2.hasNext()) {
                        int loadWeaverSlot2 =
                                loadWeaverSlot(
                                        ((Integer) entry2.getKey()).intValue(),
                                        ((Long) it2.next()).longValue());
                        if (hashMap.containsKey(Integer.valueOf(loadWeaverSlot2))) {
                            str = str5;
                            SDPLog.d(null, str + loadWeaverSlot2 + " with user " + intValue2);
                            List<ConflictInfo> list =
                                    (List) hashMap.get(Integer.valueOf(loadWeaverSlot2));
                            if (list != null) {
                                for (ConflictInfo conflictInfo : list) {
                                    long j = conflictInfo.handle;
                                    int i = conflictInfo.userId;
                                    if (UserManager.isVirtualUserId(i)) {
                                        SDPLog.d(
                                                null,
                                                String.format(
                                                        "Mitigate slot conflict on %x.weaver for"
                                                            + " virtual user %d",
                                                        Long.valueOf(j), Integer.valueOf(i)));
                                        if (Binder.getCallingUid() == 1000) {
                                            destroyState(i, "weaver", j);
                                        }
                                    } else {
                                        NandswapManager$$ExternalSyntheticOutline0.m(
                                                i,
                                                "Do not handle conflict for normal user ",
                                                "SyntheticPasswordManager");
                                    }
                                }
                            }
                        } else {
                            str = str5;
                        }
                        str5 = str;
                    }
                }
                str5 = str5;
            }
        }
        return hashSet;
    }

    public android.hardware.weaver.V1_0.IWeaver getWeaverHidlService() throws RemoteException {
        try {
            return android.hardware.weaver.V1_0.IWeaver.getService(true);
        } catch (NoSuchElementException unused) {
            return null;
        }
    }

    public final synchronized IWeaver getWeaverService() {
        IWeaver iWeaver = this.mWeaver;
        if (iWeaver != null) {
            return iWeaver;
        }
        IWeaver weaverServiceInternal = getWeaverServiceInternal();
        if (weaverServiceInternal == null) {
            return null;
        }
        try {
            WeaverConfig config = weaverServiceInternal.getConfig();
            if (config != null && config.slots > 0) {
                this.mWeaver = weaverServiceInternal;
                this.mWeaverConfig = config;
                this.mPasswordSlotManager.refreshActiveSlots(getUsedWeaverSlots());
                Slog.i("SyntheticPasswordManager", "Weaver service initialized");
                return weaverServiceInternal;
            }
            Slog.e("SyntheticPasswordManager", "Invalid weaver config");
            return null;
        } catch (RemoteException | ServiceSpecificException e) {
            Slog.e("SyntheticPasswordManager", "Failed to get weaver config", e);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x005f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.hardware.weaver.IWeaver getWeaverServiceInternal() {
        /*
            r5 = this;
            java.lang.String r0 = "SyntheticPasswordManager"
            r1 = 0
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.SecurityException -> L57
            r2.<init>()     // Catch: java.lang.SecurityException -> L57
            java.lang.String r3 = android.hardware.weaver.IWeaver.DESCRIPTOR     // Catch: java.lang.SecurityException -> L57
            r2.append(r3)     // Catch: java.lang.SecurityException -> L57
            java.lang.String r4 = "/default"
            r2.append(r4)     // Catch: java.lang.SecurityException -> L57
            java.lang.String r2 = r2.toString()     // Catch: java.lang.SecurityException -> L57
            android.os.IBinder r2 = android.os.ServiceManager.waitForDeclaredService(r2)     // Catch: java.lang.SecurityException -> L57
            int r4 = android.hardware.weaver.IWeaver.Stub.$r8$clinit     // Catch: java.lang.SecurityException -> L57
            if (r2 != 0) goto L20
            r3 = r1
            goto L37
        L20:
            android.os.IInterface r3 = r2.queryLocalInterface(r3)     // Catch: java.lang.SecurityException -> L57
            if (r3 == 0) goto L2d
            boolean r4 = r3 instanceof android.hardware.weaver.IWeaver     // Catch: java.lang.SecurityException -> L57
            if (r4 == 0) goto L2d
            android.hardware.weaver.IWeaver r3 = (android.hardware.weaver.IWeaver) r3     // Catch: java.lang.SecurityException -> L57
            goto L37
        L2d:
            android.hardware.weaver.IWeaver$Stub$Proxy r3 = new android.hardware.weaver.IWeaver$Stub$Proxy     // Catch: java.lang.SecurityException -> L57
            r3.<init>()     // Catch: java.lang.SecurityException -> L57
            r4 = -1
            r3.mCachedVersion = r4     // Catch: java.lang.SecurityException -> L57
            r3.mRemote = r2     // Catch: java.lang.SecurityException -> L57
        L37:
            if (r3 != 0) goto L3b
        L39:
            r3 = r1
            goto L5d
        L3b:
            int r2 = r3.getInterfaceVersion()     // Catch: android.os.RemoteException -> L50
            r4 = 2
            if (r2 >= r4) goto L4a
            java.lang.String r3 = "Ignoring AIDL weaver service v"
            java.lang.String r4 = " because only v2 and later are supported"
            com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0.m(r2, r3, r4, r0)
            goto L39
        L4a:
            java.lang.String r4 = "Found AIDL weaver service v"
            com.android.server.HermesService$3$$ExternalSyntheticOutline0.m(r2, r4, r0)
            goto L5d
        L50:
            r2 = move-exception
            java.lang.String r3 = "Cannot get AIDL weaver service version"
            android.util.Slog.e(r0, r3, r2)
            goto L39
        L57:
            java.lang.String r2 = "Does not have permissions to get AIDL weaver service"
            android.util.Slog.w(r0, r2)
            goto L39
        L5d:
            if (r3 == 0) goto L79
            java.lang.String r1 = "Using AIDL weaver service"
            android.util.Slog.i(r0, r1)
            android.os.IBinder r1 = r3.asBinder()     // Catch: android.os.RemoteException -> L72
            com.android.server.locksettings.SyntheticPasswordManager$WeaverDiedRecipient r2 = new com.android.server.locksettings.SyntheticPasswordManager$WeaverDiedRecipient     // Catch: android.os.RemoteException -> L72
            r2.<init>()     // Catch: android.os.RemoteException -> L72
            r5 = 0
            r1.linkToDeath(r2, r5)     // Catch: android.os.RemoteException -> L72
            goto L78
        L72:
            r5 = move-exception
            java.lang.String r1 = "Unable to register Weaver death recipient"
            android.util.Slog.w(r0, r1, r5)
        L78:
            return r3
        L79:
            android.hardware.weaver.V1_0.IWeaver r5 = r5.getWeaverHidlService()     // Catch: android.os.RemoteException -> L8a
            if (r5 == 0) goto L90
            java.lang.String r2 = "Using HIDL weaver service"
            android.util.Slog.i(r0, r2)     // Catch: android.os.RemoteException -> L8a
            com.android.server.locksettings.WeaverHidlAdapter r2 = new com.android.server.locksettings.WeaverHidlAdapter     // Catch: android.os.RemoteException -> L8a
            r2.<init>(r5)     // Catch: android.os.RemoteException -> L8a
            return r2
        L8a:
            r5 = move-exception
            java.lang.String r2 = "Failed to get HIDL weaver service."
            android.util.Slog.w(r0, r2, r5)
        L90:
            java.lang.String r5 = "Device does not support weaver"
            android.util.Slog.w(r0, r5)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.locksettings.SyntheticPasswordManager.getWeaverServiceInternal():android.hardware.weaver.IWeaver");
    }

    public final boolean hasEscrowData(int i) {
        return hasState(i, "e0", 0L) && hasState(i, "p1", 0L);
    }

    public boolean hasPasswordData(long j, int i) {
        return hasState(i, "pwd", j);
    }

    public boolean hasPasswordMetrics(long j, int i) {
        return hasState(i, "metrics", j);
    }

    public final boolean hasState(int i, String str, long j) {
        return !ArrayUtils.isEmpty(this.mStorage.readSyntheticPasswordState(i, str, j));
    }

    public boolean isAutoPinConfirmationFeatureAvailable() {
        return LockPatternUtils.isAutoPinConfirmFeatureAvailable();
    }

    public final boolean loadEscrowData(int i, SyntheticPassword syntheticPassword) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        byte[] readSyntheticPasswordState =
                lockSettingsStorage.readSyntheticPasswordState(i, "e0", 0L);
        byte[] readSyntheticPasswordState2 =
                lockSettingsStorage.readSyntheticPasswordState(i, "p1", 0L);
        syntheticPassword.mEncryptedEscrowSplit0 = readSyntheticPasswordState;
        syntheticPassword.mEscrowSplit1 = readSyntheticPasswordState2;
        return (readSyntheticPasswordState == null || readSyntheticPasswordState2 == null)
                ? false
                : true;
    }

    public final int loadWeaverSlot(int i, long j) {
        byte[] readSyntheticPasswordState =
                this.mStorage.readSyntheticPasswordState(i, "weaver", j);
        if (readSyntheticPasswordState == null || readSyntheticPasswordState.length != 5) {
            return -1;
        }
        ByteBuffer allocate = ByteBuffer.allocate(5);
        allocate.put(readSyntheticPasswordState, 0, readSyntheticPasswordState.length);
        allocate.flip();
        if (allocate.get() == 1) {
            return allocate.getInt();
        }
        Slog.e("SyntheticPasswordManager", "Invalid weaver slot version for protector " + j);
        return -1;
    }

    public final void migrateFrpPasswordLocked(long j, UserInfo userInfo, int i) {
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        if (lockSettingsStorage.getPersistentDataBlockManager() == null
                || !LockPatternUtils.userOwnsFrpCredential(this.mContext, userInfo)) {
            return;
        }
        byte[] readSyntheticPasswordState =
                this.mStorage.readSyntheticPasswordState(userInfo.id, "pwd", j);
        if ((readSyntheticPasswordState == null
                        ? -1
                        : PasswordData.fromBytes(readSyntheticPasswordState).credentialType)
                != -1) {
            Slog.i("SyntheticPasswordManager", "Migrating FRP credential to persistent data block");
            PasswordData fromBytes =
                    PasswordData.fromBytes(
                            lockSettingsStorage.readSyntheticPasswordState(userInfo.id, "pwd", j));
            int loadWeaverSlot = loadWeaverSlot(userInfo.id, j);
            if (loadWeaverSlot != -1) {
                synchronizeWeaverFrpPassword(fromBytes, i, userInfo.id, loadWeaverSlot);
            } else {
                synchronizeGatekeeperFrpPassword(fromBytes, i, userInfo.id);
            }
        }
    }

    public final boolean migrateKeyNamespace() {
        Iterator it =
                ((ArrayMap) this.mStorage.listSyntheticPasswordProtectorsForAllUsers("spblob"))
                        .values()
                        .iterator();
        boolean z = true;
        while (it.hasNext()) {
            for (Long l : (List) it.next()) {
                l.longValue();
                z &=
                        SyntheticPasswordCrypto.migrateLockSettingsKey(
                                TextUtils.formatSimple(
                                        "%s%x", new Object[] {"synthetic_password_", l}));
            }
        }
        return z;
    }

    public final void migratePwdDataForKnox(int i, long j) {
        byte[] readSyntheticPasswordState = this.mStorage.readSyntheticPasswordState(i, "pwd", j);
        if (readSyntheticPasswordState == null) {
            return;
        }
        PasswordData passwordData = new PasswordData();
        ByteBuffer allocate = ByteBuffer.allocate(readSyntheticPasswordState.length);
        allocate.put(readSyntheticPasswordState, 0, readSyntheticPasswordState.length);
        allocate.flip();
        passwordData.credentialType = (short) allocate.getInt();
        passwordData.scryptLogN = allocate.get();
        passwordData.scryptLogR = allocate.get();
        passwordData.scryptLogP = allocate.get();
        byte[] bArr = new byte[allocate.getInt()];
        passwordData.salt = bArr;
        allocate.get(bArr);
        int i2 = allocate.getInt();
        if (i2 > 0) {
            byte[] bArr2 = new byte[i2];
            passwordData.passwordHandle = bArr2;
            allocate.get(bArr2);
        } else {
            passwordData.passwordHandle = null;
        }
        int i3 = allocate.getInt();
        if (i3 < 0 || i3 > 2) {
            passwordData.pinLength = i3;
            passwordData.secureMode = 0;
            SDPLog.d(
                    "SyntheticPasswordManager",
                    "Abnormal migration case - pinLength : "
                            + passwordData.pinLength
                            + ", secureMode : "
                            + passwordData.secureMode);
        } else {
            passwordData.pinLength = -1;
            passwordData.secureMode = i3;
            SDPLog.d(
                    "SyntheticPasswordManager",
                    "Migration case - secureMode : " + passwordData.secureMode);
        }
        saveState("pwd", passwordData.toBytes(), j, i);
        syncState(i);
        SDPLog.d("SyntheticPasswordManager", "Migrated password data for user " + i);
    }

    public final SyntheticPassword newSyntheticPassword(int i) {
        destroyState(i, "handle", 0L);
        SPBnRManager.deleteBackup(i, 0L, "handle");
        SyntheticPassword syntheticPassword = new SyntheticPassword((byte) 3);
        byte[] randomBytes = SecureRandomUtils.randomBytes(32);
        byte[] randomBytes2 = SecureRandomUtils.randomBytes(32);
        byte[] bytesToHex =
                bytesToHex(
                        SyntheticPasswordCrypto.personalizedHash(
                                PERSONALIZATION_SP_SPLIT, randomBytes, randomBytes2));
        syntheticPassword.mSyntheticPassword = bytesToHex;
        byte[] encrypt =
                SyntheticPasswordCrypto.encrypt(bytesToHex, PERSONALIZATION_E0, randomBytes);
        syntheticPassword.mEncryptedEscrowSplit0 = encrypt;
        syntheticPassword.mEscrowSplit1 = randomBytes2;
        saveState("e0", encrypt, 0L, i);
        saveState("p1", syntheticPassword.mEscrowSplit1, 0L, i);
        return syntheticPassword;
    }

    public final void removeUser(IGateKeeperService iGateKeeperService, int i) {
        Iterator it =
                ((ArrayList) this.mStorage.listSyntheticPasswordProtectorsForUser(i, "spblob"))
                        .iterator();
        while (it.hasNext()) {
            long longValue = ((Long) it.next()).longValue();
            destroyWeaverSlot(i, longValue);
            destroyProtectorKey(getProtectorKeyAlias(longValue));
        }
        try {
            iGateKeeperService.clearSecureUserId(fakeUserId(i));
        } catch (RemoteException unused) {
            Slog.w("SyntheticPasswordManager", "Failed to clear SID from gatekeeper");
        }
    }

    public final void savePasswordMetrics(
            LockscreenCredential lockscreenCredential,
            SyntheticPassword syntheticPassword,
            long j,
            int i) {
        syntheticPassword.getClass();
        PasswordMetrics computeForCredential =
                PasswordMetrics.computeForCredential(lockscreenCredential);
        ByteBuffer allocate = ByteBuffer.allocate(44);
        allocate.putInt(1);
        allocate.putInt(computeForCredential.credType);
        allocate.putInt(computeForCredential.length);
        allocate.putInt(computeForCredential.letters);
        allocate.putInt(computeForCredential.upperCase);
        allocate.putInt(computeForCredential.lowerCase);
        allocate.putInt(computeForCredential.numeric);
        allocate.putInt(computeForCredential.symbols);
        allocate.putInt(computeForCredential.nonLetter);
        allocate.putInt(computeForCredential.nonNumeric);
        allocate.putInt(computeForCredential.seqLength);
        saveState(
                "metrics",
                SyntheticPasswordCrypto.encrypt(
                        syntheticPassword.deriveSubkey(PERSONALIZATION_PASSWORD_METRICS),
                        new byte[0],
                        allocate.array()),
                j,
                i);
    }

    public final void saveState(String str, byte[] bArr, long j, int i) {
        if ("spblob".equals(str)) {
            LsLog.enroll(
                    String.format(
                            "User %d Spblob save [%016x]\n%s",
                            Integer.valueOf(i), Long.valueOf(j), Debug.getCallers(7, "    ")));
        }
        LockSettingsStorage lockSettingsStorage = this.mStorage;
        File syntheticPasswordDirectoryForUser =
                lockSettingsStorage.getSyntheticPasswordDirectoryForUser(i);
        if (!syntheticPasswordDirectoryForUser.exists()) {
            syntheticPasswordDirectoryForUser.mkdir();
        }
        lockSettingsStorage.writeFile(
                lockSettingsStorage.getSyntheticPasswordStateFileForUser(i, str, j), bArr, false);
    }

    public byte[] scrypt(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4) {
        return new Scrypt().scrypt(bArr, bArr2, i, i2, i3, i4);
    }

    public final boolean shouldSynchronizeFrpCredential(PasswordData passwordData, int i) {
        if (this.mStorage.getPersistentDataBlockManager() == null) {
            return false;
        }
        if (!LockPatternUtils.userOwnsFrpCredential(
                this.mContext, this.mUserManager.getUserInfo(i))) {
            return false;
        }
        if ((passwordData != null && passwordData.credentialType != -1)
                || Settings.Global.getInt(
                                this.mContext.getContentResolver(), "device_provisioned", 0)
                        != 0) {
            return true;
        }
        Slog.d(
                "SyntheticPasswordManager",
                "Not clearing FRP credential yet because device is not yet provisioned");
        return false;
    }

    public long sidFromPasswordHandle(byte[] bArr) {
        return nativeSidFromPasswordHandle(bArr);
    }

    public byte[] stretchLskf(
            LockscreenCredential lockscreenCredential, PasswordData passwordData) {
        byte[] credential =
                (lockscreenCredential.isNone() || SECURITY_UNPACK)
                        ? DEFAULT_PASSWORD
                        : lockscreenCredential.getCredential();
        if (passwordData != null) {
            return scrypt(
                    credential,
                    passwordData.salt,
                    1 << passwordData.scryptLogN,
                    1 << passwordData.scryptLogR,
                    1 << passwordData.scryptLogP,
                    32);
        }
        Preconditions.checkArgument(lockscreenCredential.isNone());
        return Arrays.copyOf(credential, 32);
    }

    public final byte[] stretchedLskfToWeaverKey(byte[] bArr) {
        byte[] personalizedHash =
                SyntheticPasswordCrypto.personalizedHash(PERSONALIZATION_WEAVER_KEY, bArr);
        int length = personalizedHash.length;
        int i = this.mWeaverConfig.keySize;
        if (length >= i) {
            return Arrays.copyOf(personalizedHash, i);
        }
        throw new IllegalArgumentException("weaver key length too small");
    }

    public final void syncState(int i) {
        LockSettingsStorage.fsyncDirectory(this.mStorage.getSyntheticPasswordDirectoryForUser(i));
    }

    public final void synchronizeGatekeeperFrpPassword(PasswordData passwordData, int i, int i2) {
        if (shouldSynchronizeFrpCredential(passwordData, i2)) {
            Slogf.d(
                    "SyntheticPasswordManager",
                    "Syncing Gatekeeper-based FRP credential tied to user %d",
                    Integer.valueOf(i2));
            boolean z = passwordData == null || passwordData.credentialType == -1;
            LockSettingsStorage lockSettingsStorage = this.mStorage;
            if (z) {
                lockSettingsStorage.writePersistentDataBlock(0, i2, 0, null);
            } else {
                lockSettingsStorage.writePersistentDataBlock(1, i2, i, passwordData.toBytes());
            }
        }
    }

    public final void synchronizeWeaverFrpPassword(
            PasswordData passwordData, int i, int i2, int i3) {
        if (shouldSynchronizeFrpCredential(passwordData, i2)) {
            Slogf.d(
                    "SyntheticPasswordManager",
                    "Syncing Weaver-based FRP credential tied to user %d",
                    Integer.valueOf(i2));
            boolean z = passwordData == null || passwordData.credentialType == -1;
            LockSettingsStorage lockSettingsStorage = this.mStorage;
            if (z) {
                lockSettingsStorage.writePersistentDataBlock(0, 0, 0, null);
            } else {
                lockSettingsStorage.writePersistentDataBlock(2, i3, i, passwordData.toBytes());
            }
        }
    }

    public final AuthenticationResult unlockLskfBasedProtector(
            IGateKeeperService iGateKeeperService,
            long j,
            LockscreenCredential lockscreenCredential,
            int i,
            ICheckCredentialProgressCallback iCheckCredentialProgressCallback) {
        PasswordData passwordData;
        int i2;
        byte[] bArr;
        String str;
        long j2;
        long j3;
        byte[] transformUnderSecdiscardable;
        boolean z;
        PasswordData passwordData2;
        GateKeeperResponse gateKeeperResponse;
        AuthenticationResult authenticationResult = new AuthenticationResult();
        if (j == 0) {
            Slogf.wtf(
                    "SyntheticPasswordManager",
                    "Synthetic password not found for user %d",
                    Integer.valueOf(i));
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
            LsLog.verify("SP not found for user " + i);
            return authenticationResult;
        }
        byte[] readSyntheticPasswordState = this.mStorage.readSyntheticPasswordState(i, "pwd", j);
        if (readSyntheticPasswordState != null) {
            PasswordData fromBytes = PasswordData.fromBytes(readSyntheticPasswordState);
            i2 = fromBytes.credentialType;
            passwordData = fromBytes;
        } else {
            passwordData = null;
            i2 = -1;
        }
        if (!lockscreenCredential.checkAgainstStoredType(i2)) {
            Slogf.e(
                    "SyntheticPasswordManager",
                    "Credential type mismatch: stored type is %s but provided type is %s",
                    LockPatternUtils.credentialTypeToString(i2),
                    LockPatternUtils.credentialTypeToString(lockscreenCredential.getType()));
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
            LsLog.verify(
                    "Credential type mismatch: stored type is "
                            + LockPatternUtils.credentialTypeToString(i2)
                            + " but provided type is "
                            + LockPatternUtils.credentialTypeToString(
                                    lockscreenCredential.getType()));
            return authenticationResult;
        }
        byte[] stretchLskf = stretchLskf(lockscreenCredential, passwordData);
        this.mSdpSyntheticPasswordManager.getClass();
        SDPLog.d("SyntheticPasswordManager", "unlockLskfBasedProtector isSdpMdfppMode ? false");
        LsLog.verifyBegin(i);
        int loadWeaverSlot = loadWeaverSlot(i, j);
        if (loadWeaverSlot != -1) {
            LsLog.verifyUpdate(
                    lockscreenCredential.getType(),
                    loadWeaverSlot,
                    j,
                    passwordData != null ? passwordData.salt : null);
            IWeaver weaverService = getWeaverService();
            if (weaverService == null) {
                Slog.e(
                        "SyntheticPasswordManager",
                        "Protector uses Weaver, but Weaver is unavailable");
                authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                LsLog.verifyFinish(1, -1L, "Protector uses Weaver, but Weaver is unavailable");
                return authenticationResult;
            }
            WeaverResult.begin(1, loadWeaverSlot, i);
            authenticationResult.gkResponse =
                    weaverVerify(
                            weaverService, loadWeaverSlot, stretchedLskfToWeaverKey(stretchLskf));
            WeaverResult.finish(i);
            if (authenticationResult.gkResponse.getResponseCode() != 0) {
                return authenticationResult;
            }
            transformUnderSecdiscardable =
                    ArrayUtils.concat(
                            new byte[][] {
                                stretchLskf,
                                SyntheticPasswordCrypto.personalizedHash(
                                        PERSONALIZATION_WEAVER_PASSWORD,
                                        authenticationResult.gkResponse.getGatekeeperHAT())
                            });
            str = "SyntheticPasswordManager";
            j3 = 0;
        } else {
            LsLog.verifyUpdate(
                    lockscreenCredential.getType(),
                    -1,
                    j,
                    passwordData != null ? passwordData.salt : null);
            if (passwordData == null || passwordData.passwordHandle == null) {
                bArr = stretchLskf;
                str = "SyntheticPasswordManager";
                if (!lockscreenCredential.isNone()) {
                    Slog.e(str, "Missing Gatekeeper password handle for nonempty LSKF");
                    authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                    LsLog.verifyFinish(
                            1, -1L, "Missing Gatekeeper password handle for nonempty LSKF");
                    return authenticationResult;
                }
                j2 = 0;
            } else {
                byte[] stretchedLskfToGkPassword = stretchedLskfToGkPassword(stretchLskf);
                if (UserManager.isVirtualUserId(i)
                        && getWeaverService() != null
                        && passwordData.passwordHandle == null) {
                    SDPLog.d(null, "Virtual User " + i + " may lost weaver slot.");
                    z = false;
                    passwordData.passwordHandle = new byte[0];
                } else {
                    z = false;
                }
                try {
                    passwordData2 = passwordData;
                    bArr = stretchLskf;
                    str = "SyntheticPasswordManager";
                } catch (RemoteException e) {
                    e = e;
                    str = "SyntheticPasswordManager";
                }
                try {
                    GateKeeperResponse verifyChallenge =
                            iGateKeeperService.verifyChallenge(
                                    fakeUserId(i),
                                    0L,
                                    passwordData.passwordHandle,
                                    stretchedLskfToGkPassword);
                    int responseCode = verifyChallenge.getResponseCode();
                    if (responseCode != 0) {
                        if (responseCode == 1) {
                            authenticationResult.gkResponse =
                                    VerifyCredentialResponse.fromTimeout(
                                            verifyChallenge.getTimeout());
                            LsLog.verifyFinish(
                                    3, verifyChallenge.getTimeout(), "gatekeeper THROTTLE");
                            return authenticationResult;
                        }
                        authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                        if (responseCode == -1) {
                            LsLog.verifyFinish(
                                    2, verifyChallenge.getTimeout(), "gatekeeper INCORRECT_KEY");
                        } else {
                            LsLog.verifyFinish(
                                    responseCode,
                                    verifyChallenge.getTimeout(),
                                    "gatekeeper FAILED");
                        }
                        return authenticationResult;
                    }
                    authenticationResult.gkResponse = VerifyCredentialResponse.OK;
                    if (verifyChallenge.getShouldReEnroll()) {
                        try {
                            gateKeeperResponse =
                                    iGateKeeperService.enroll(
                                            fakeUserId(i),
                                            passwordData2.passwordHandle,
                                            stretchedLskfToGkPassword,
                                            stretchedLskfToGkPassword);
                        } catch (RemoteException e2) {
                            Slog.w(str, "Fail to invoke gatekeeper.enroll", e2);
                            gateKeeperResponse = GateKeeperResponse.ERROR;
                        }
                        if (gateKeeperResponse.getResponseCode() == 0) {
                            passwordData2.passwordHandle = gateKeeperResponse.getPayload();
                            passwordData2.credentialType = lockscreenCredential.getType();
                            saveState("pwd", passwordData2.toBytes(), j, i);
                            syncState(i);
                            synchronizeGatekeeperFrpPassword(passwordData2, 0, i);
                        } else {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    i, "Fail to re-enroll user password for user ", str);
                        }
                    }
                    j2 = sidFromPasswordHandle(passwordData2.passwordHandle);
                } catch (RemoteException e3) {
                    e = e3;
                    Slog.e(str, "gatekeeper verify failed", e);
                    authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                    LsLog.verifyFinish(1, -1L, "gatekeeper verify failed");
                    return authenticationResult;
                }
            }
            byte[] readSyntheticPasswordState2 =
                    this.mStorage.readSyntheticPasswordState(i, "secdis", j);
            if (readSyntheticPasswordState2 == null) {
                Slog.e(str, "secdiscardable file not found");
                authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            j3 = j2;
            transformUnderSecdiscardable =
                    transformUnderSecdiscardable(bArr, readSyntheticPasswordState2);
        }
        LsLog.verifyFinish(0, 0L, "verify success!");
        if (iCheckCredentialProgressCallback != null) {
            try {
                iCheckCredentialProgressCallback.onCredentialVerified();
            } catch (RemoteException e4) {
                Slog.w(str, "progressCallback throws exception", e4);
            }
        }
        SyntheticPassword unwrapSyntheticPasswordBlob =
                unwrapSyntheticPasswordBlob(j, (byte) 0, transformUnderSecdiscardable, j3, i);
        authenticationResult.syntheticPassword = unwrapSyntheticPasswordBlob;
        authenticationResult.gkResponse =
                verifyChallenge(iGateKeeperService, unwrapSyntheticPasswordBlob, i);
        if (authenticationResult.syntheticPassword != null
                && !lockscreenCredential.isNone()
                && !hasPasswordMetrics(j, i)) {
            savePasswordMetrics(lockscreenCredential, authenticationResult.syntheticPassword, j, i);
            syncState(i);
        }
        return authenticationResult;
    }

    public final AuthenticationResult unlockTokenBasedProtector(
            int i, long j, IGateKeeperService iGateKeeperService, byte[] bArr) {
        byte[] readSyntheticPasswordState =
                this.mStorage.readSyntheticPasswordState(i, "spblob", j);
        if (readSyntheticPasswordState == null) {
            AuthenticationResult authenticationResult = new AuthenticationResult();
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
            Slogf.w(
                    "SyntheticPasswordManager",
                    "spblob not found for protector %016x, user %d",
                    Long.valueOf(j),
                    Integer.valueOf(i));
            return authenticationResult;
        }
        if (SECURITY_UNPACK) {
            bArr = DEFAULT_TOKEN;
        }
        return unlockTokenBasedProtectorInternal(
                iGateKeeperService,
                j,
                SyntheticPasswordBlob.fromBytes(readSyntheticPasswordState).mProtectorType,
                bArr,
                i);
    }

    public final AuthenticationResult unlockTokenBasedProtectorInternal(
            IGateKeeperService iGateKeeperService, long j, byte b, byte[] bArr, int i) {
        AuthenticationResult authenticationResult = new AuthenticationResult();
        byte[] readSyntheticPasswordState =
                this.mStorage.readSyntheticPasswordState(i, "secdis", j);
        if (readSyntheticPasswordState == null) {
            Slog.e("SyntheticPasswordManager", "secdiscardable file not found");
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
            return authenticationResult;
        }
        int loadWeaverSlot = loadWeaverSlot(i, j);
        if (loadWeaverSlot != -1) {
            IWeaver weaverService = getWeaverService();
            if (weaverService == null) {
                Slog.e(
                        "SyntheticPasswordManager",
                        "Protector uses Weaver, but Weaver is unavailable");
                authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            WeaverResult.begin(1, loadWeaverSlot, i);
            VerifyCredentialResponse weaverVerify =
                    weaverVerify(weaverService, loadWeaverSlot, null);
            WeaverResult.finish(i);
            if (weaverVerify.getResponseCode() != 0 || weaverVerify.getGatekeeperHAT() == null) {
                Slog.e(
                        "SyntheticPasswordManager",
                        "Failed to retrieve Weaver secret when unlocking token-based protector");
                authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
                return authenticationResult;
            }
            readSyntheticPasswordState =
                    SyntheticPasswordCrypto.decrypt(
                            weaverVerify.getGatekeeperHAT(),
                            PERSONALIZATION_WEAVER_TOKEN,
                            readSyntheticPasswordState);
        }
        SdpSyntheticPasswordManager.m644$$Nest$mgetSecureMode(this.mSdpSyntheticPasswordManager, i);
        SyntheticPassword unwrapSyntheticPasswordBlob =
                unwrapSyntheticPasswordBlob(
                        j,
                        b,
                        transformUnderSecdiscardable(bArr, readSyntheticPasswordState),
                        0L,
                        i);
        authenticationResult.syntheticPassword = unwrapSyntheticPasswordBlob;
        if (unwrapSyntheticPasswordBlob != null) {
            VerifyCredentialResponse verifyChallenge =
                    verifyChallenge(iGateKeeperService, unwrapSyntheticPasswordBlob, i);
            authenticationResult.gkResponse = verifyChallenge;
            if (verifyChallenge == null) {
                authenticationResult.gkResponse = VerifyCredentialResponse.OK;
            }
        } else {
            authenticationResult.gkResponse = VerifyCredentialResponse.ERROR;
        }
        return authenticationResult;
    }

    public final SyntheticPassword unwrapSyntheticPasswordBlob(
            long j, byte b, byte[] bArr, long j2, int i) {
        byte[] decryptSpBlob;
        byte[] readSyntheticPasswordState =
                this.mStorage.readSyntheticPasswordState(i, "spblob", j);
        if (readSyntheticPasswordState == null) {
            LsLog.verify(
                    String.format(
                            "Fail to load spblob for protector %016x for user %d",
                            Long.valueOf(j), Integer.valueOf(i)));
            return null;
        }
        SyntheticPasswordBlob fromBytes =
                SyntheticPasswordBlob.fromBytes(readSyntheticPasswordState);
        byte b2 = fromBytes.mVersion;
        if (b2 != 3 && b2 != 2 && b2 != 1) {
            throw new IllegalArgumentException(
                    "Unknown blob version: " + ((int) fromBytes.mVersion));
        }
        if (fromBytes.mProtectorType != b) {
            throw new IllegalArgumentException(
                    "Invalid protector type: " + ((int) fromBytes.mProtectorType));
        }
        if (b2 == 1) {
            String protectorKeyAlias = getProtectorKeyAlias(j);
            byte[] bArr2 = fromBytes.mContent;
            try {
                SecretKey secretKey =
                        (SecretKey)
                                SyntheticPasswordCrypto.getKeyStore()
                                        .getKey(protectorKeyAlias, null);
                if (secretKey == null) {
                    throw new IllegalStateException(
                            "SP protector key is missing: " + protectorKeyAlias);
                }
                decryptSpBlob =
                        SyntheticPasswordCrypto.decrypt(
                                SyntheticPasswordCrypto.decrypt(
                                        bArr,
                                        SyntheticPasswordCrypto.PROTECTOR_SECRET_PERSONALIZATION,
                                        bArr2),
                                secretKey);
            } catch (Exception e) {
                Slog.e("SyntheticPasswordCrypto", "Failed to decrypt V1 blob", e);
                throw new IllegalStateException("Failed to decrypt blob", e);
            }
        } else {
            decryptSpBlob = decryptSpBlob(getProtectorKeyAlias(j), fromBytes.mContent, bArr);
        }
        if (decryptSpBlob == null) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i, "Fail to decrypt SP for user ", "SyntheticPasswordManager");
            return null;
        }
        SyntheticPassword syntheticPassword = new SyntheticPassword(fromBytes.mVersion);
        byte b3 = fromBytes.mProtectorType;
        if (b3 != 1 && b3 != 2) {
            syntheticPassword.mSyntheticPassword =
                    Arrays.copyOf(decryptSpBlob, decryptSpBlob.length);
        } else {
            if (!loadEscrowData(i, syntheticPassword)) {
                NandswapManager$$ExternalSyntheticOutline0.m(
                        i, "User is not escrowable: ", "SyntheticPasswordManager");
                return null;
            }
            Objects.requireNonNull(syntheticPassword.mEscrowSplit1);
            Objects.requireNonNull(syntheticPassword.mEncryptedEscrowSplit0);
            syntheticPassword.mSyntheticPassword =
                    bytesToHex(
                            SyntheticPasswordCrypto.personalizedHash(
                                    PERSONALIZATION_SP_SPLIT,
                                    decryptSpBlob,
                                    syntheticPassword.mEscrowSplit1));
        }
        if (fromBytes.mVersion == 1) {
            SystemServiceManager$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Upgrading v1 SP blob for user ", ", protectorType = "),
                    fromBytes.mProtectorType,
                    "SyntheticPasswordManager");
            createSyntheticPasswordBlob(
                    j, fromBytes.mProtectorType, syntheticPassword, bArr, j2, i);
            syncState(i);
        }
        return syntheticPassword;
    }

    public final VerifyCredentialResponse verifyChallenge(
            IGateKeeperService iGateKeeperService, SyntheticPassword syntheticPassword, int i) {
        syntheticPassword.getClass();
        return verifyChallengeInternal(
                i,
                0L,
                iGateKeeperService,
                syntheticPassword.deriveSubkey(PERSONALIZATION_SP_GK_AUTH));
    }

    public final VerifyCredentialResponse verifyChallengeInternal(
            int i, long j, IGateKeeperService iGateKeeperService, byte[] bArr) {
        GateKeeperResponse gateKeeperResponse;
        byte[] readSyntheticPasswordState =
                this.mStorage.readSyntheticPasswordState(i, "handle", 0L);
        if (readSyntheticPasswordState == null) {
            return null;
        }
        try {
            GateKeeperResponse verifyChallenge =
                    iGateKeeperService.verifyChallenge(i, j, readSyntheticPasswordState, bArr);
            int responseCode = verifyChallenge.getResponseCode();
            if (responseCode != 0) {
                if (responseCode == 1) {
                    Slog.e(
                            "SyntheticPasswordManager",
                            "Gatekeeper verification of synthetic password failed with"
                                + " RESPONSE_RETRY");
                    return VerifyCredentialResponse.fromTimeout(verifyChallenge.getTimeout());
                }
                Slog.e(
                        "SyntheticPasswordManager",
                        "Gatekeeper verification of synthetic password failed with RESPONSE_ERROR");
                return VerifyCredentialResponse.ERROR;
            }
            VerifyCredentialResponse build =
                    new VerifyCredentialResponse.Builder()
                            .setGatekeeperHAT(verifyChallenge.getPayload())
                            .build();
            if (verifyChallenge.getShouldReEnroll()) {
                try {
                    gateKeeperResponse =
                            iGateKeeperService.enroll(
                                    i,
                                    readSyntheticPasswordState,
                                    readSyntheticPasswordState,
                                    bArr);
                } catch (RemoteException e) {
                    Slog.e("SyntheticPasswordManager", "Failed to invoke gatekeeper.enroll", e);
                    gateKeeperResponse = GateKeeperResponse.ERROR;
                }
                if (gateKeeperResponse.getResponseCode() == 0) {
                    saveState("handle", gateKeeperResponse.getPayload(), 0L, i);
                    syncState(i);
                    return verifyChallengeInternal(i, j, iGateKeeperService, bArr);
                }
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "Fail to re-enroll SP handle for user ", "SyntheticPasswordManager");
            }
            return build;
        } catch (RemoteException e2) {
            Slog.e("SyntheticPasswordManager", "Fail to verify with gatekeeper " + i, e2);
            return VerifyCredentialResponse.ERROR;
        }
    }

    public final VerifyCredentialResponse verifySpecialUserCredential(
            int i,
            IGateKeeperService iGateKeeperService,
            LockscreenCredential lockscreenCredential) {
        LockSettingsStorage.PersistentData specialUserPersistentData =
                getSpecialUserPersistentData(i);
        int i2 = specialUserPersistentData.userId;
        byte[] bArr = specialUserPersistentData.payload;
        int i3 = specialUserPersistentData.type;
        if (i3 == 1) {
            PasswordData fromBytes = PasswordData.fromBytes(bArr);
            try {
                return VerifyCredentialResponse.fromGateKeeperResponse(
                        iGateKeeperService.verifyChallenge(
                                fakeUserId(i2),
                                0L,
                                fromBytes.passwordHandle,
                                stretchedLskfToGkPassword(
                                        stretchLskf(lockscreenCredential, fromBytes))));
            } catch (RemoteException e) {
                Slog.e(
                        "SyntheticPasswordManager",
                        "Persistent data credential verifyChallenge failed",
                        e);
                return VerifyCredentialResponse.ERROR;
            }
        }
        if (i3 != 2) {
            NandswapManager$$ExternalSyntheticOutline0.m(
                    i3,
                    "persistentData.type must be TYPE_SP_GATEKEEPER or TYPE_SP_WEAVER, but is ",
                    "SyntheticPasswordManager");
            return VerifyCredentialResponse.ERROR;
        }
        IWeaver weaverService = getWeaverService();
        if (weaverService == null) {
            Slog.e(
                    "SyntheticPasswordManager",
                    "No weaver service to verify SP-based persistent data credential");
            return VerifyCredentialResponse.ERROR;
        }
        byte[] stretchLskf = stretchLskf(lockscreenCredential, PasswordData.fromBytes(bArr));
        try {
            WeaverResult.begin(1, i2, i);
            return weaverVerify(weaverService, i2, stretchedLskfToWeaverKey(stretchLskf))
                    .stripPayload();
        } finally {
            WeaverResult.finish(i);
        }
    }

    public final byte[] weaverEnroll(IWeaver iWeaver, int i, byte[] bArr, byte[] bArr2) {
        if (i != -1) {
            WeaverConfig weaverConfig = this.mWeaverConfig;
            if (i < weaverConfig.slots) {
                if (bArr == null) {
                    bArr = new byte[weaverConfig.keySize];
                } else if (bArr.length != weaverConfig.keySize) {
                    WeaverResult.finishOff(i, "Invalid key size for weaver");
                    LsLog.enrollFinish(1, "Invalid key size for weaver");
                    throw new IllegalArgumentException("Invalid key size for weaver");
                }
                if (bArr2 == null) {
                    bArr2 = SecureRandomUtils.randomBytes(weaverConfig.valueSize);
                }
                try {
                    iWeaver.write(i, bArr, bArr2);
                    return bArr2;
                } catch (ServiceSpecificException e) {
                    Slog.e("SyntheticPasswordManager", "weaver write failed, slot: " + i, e);
                    WeaverResult.finishOff(i, "weaver write failed with ServiceSpecificException");
                    LsLog.enrollFinish(1, "weaver write failed");
                    return null;
                } catch (RemoteException e2) {
                    Slog.e(
                            "SyntheticPasswordManager",
                            "weaver write binder call failed, slot: " + i,
                            e2);
                    WeaverResult.finishOff(
                            i, "weaver write binder call failed with RemoveException");
                    LsLog.enrollFinish(1, "weaver write binder call failed");
                    return null;
                }
            }
        }
        WeaverResult.finishOff(i, "Invalid slot for weaver");
        LsLog.enrollFinish(1, "Invalid slot for weaver");
        throw new IllegalArgumentException("Invalid slot for weaver");
    }

    public final VerifyCredentialResponse weaverVerify(IWeaver iWeaver, int i, byte[] bArr) {
        if (i != -1) {
            WeaverConfig weaverConfig = this.mWeaverConfig;
            if (i < weaverConfig.slots) {
                if (bArr == null) {
                    bArr = new byte[weaverConfig.keySize];
                } else if (bArr.length != weaverConfig.keySize) {
                    WeaverResult.finishOff(i, "Invalid key size for weaver");
                    LsLog.verifyFinish(1, -1L, "Invalid key size for weaver " + bArr.length);
                    throw new IllegalArgumentException("Invalid key size for weaver");
                }
                try {
                    WeaverReadResponse read = iWeaver.read(i, bArr);
                    int i2 = read.status;
                    synchronized (WeaverResult.mWeaverResults) {
                        WeaverResult bySlotIdLocked = WeaverResult.getBySlotIdLocked(i);
                        if (bySlotIdLocked != null) {
                            bySlotIdLocked.mStatus = i2;
                        }
                    }
                    int i3 = read.status;
                    if (i3 == 0) {
                        return new VerifyCredentialResponse.Builder()
                                .setGatekeeperHAT(read.value)
                                .build();
                    }
                    if (i3 == 1) {
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                i,
                                "weaver read failed (FAILED), slot: ",
                                "SyntheticPasswordManager");
                        LsLog.verifyFinish(read.status, -1L, "weaver FAILED");
                        return VerifyCredentialResponse.ERROR;
                    }
                    int i4 = Integer.MAX_VALUE;
                    if (i3 == 2) {
                        if (read.timeout == 0) {
                            NandswapManager$$ExternalSyntheticOutline0.m(
                                    i,
                                    "weaver read failed (INCORRECT_KEY), slot: ",
                                    "SyntheticPasswordManager");
                            LsLog.verifyFinish(read.status, read.timeout, "weaver INCORRECT_KEY");
                            return VerifyCredentialResponse.ERROR;
                        }
                        NandswapManager$$ExternalSyntheticOutline0.m(
                                i,
                                "weaver read failed (INCORRECT_KEY/THROTTLE), slot: ",
                                "SyntheticPasswordManager");
                        LsLog.verifyFinish(
                                read.status, read.timeout, "weaver INCORRECT_KEY/THROTTLE");
                        long j = read.timeout;
                        if (j <= 2147483647L && j >= 0) {
                            i4 = (int) j;
                        }
                        return VerifyCredentialResponse.fromTimeout(i4);
                    }
                    if (i3 != 3) {
                        Slog.e(
                                "SyntheticPasswordManager",
                                "weaver read unknown status " + read.status + ", slot: " + i);
                        LsLog.verifyFinish(read.status, -1L, "weaver read unknown status");
                        return VerifyCredentialResponse.ERROR;
                    }
                    NandswapManager$$ExternalSyntheticOutline0.m(
                            i, "weaver read failed (THROTTLE), slot: ", "SyntheticPasswordManager");
                    LsLog.verifyFinish(read.status, read.timeout, "weaver THROTTLE");
                    long j2 = read.timeout;
                    if (j2 <= 2147483647L && j2 >= 0) {
                        i4 = (int) j2;
                    }
                    return VerifyCredentialResponse.fromTimeout(i4);
                } catch (RemoteException e) {
                    Slog.e("SyntheticPasswordManager", "weaver read failed, slot: " + i, e);
                    LsLog.verifyFinish(1, -1L, "weaver read failed, slot: " + i);
                    return VerifyCredentialResponse.ERROR;
                }
            }
        }
        WeaverResult.finishOff(i, "Invalid slot for weaver");
        LsLog.verifyFinish(1, -1L, "Invalid slot for weaver");
        throw new IllegalArgumentException("Invalid slot for weaver");
    }

    public final boolean writeRepairModeCredentialLocked(int i, long j) {
        if (!LockPatternUtils.canUserEnterRepairMode(
                this.mContext, this.mUserManager.getUserInfo(i))) {
            Slogf.w(
                    "SyntheticPasswordManager",
                    "User %d can't enter repair mode",
                    Integer.valueOf(i));
        } else if (LockPatternUtils.isRepairModeActive(this.mContext)) {
            Slog.w(
                    "SyntheticPasswordManager",
                    "Can't write repair mode credential while repair mode is already active");
        } else {
            if (!LockPatternUtils.isGsiRunning()) {
                LockSettingsStorage lockSettingsStorage = this.mStorage;
                byte[] readSyntheticPasswordState =
                        lockSettingsStorage.readSyntheticPasswordState(i, "pwd", j);
                if (readSyntheticPasswordState == null) {
                    Slogf.w(
                            "SyntheticPasswordManager",
                            "Password data not found for user %d",
                            Integer.valueOf(i));
                    return false;
                }
                PasswordData fromBytes = PasswordData.fromBytes(readSyntheticPasswordState);
                if (fromBytes.credentialType == -1) {
                    Slogf.w(
                            "SyntheticPasswordManager",
                            "User %d has NONE credential",
                            Integer.valueOf(i));
                    return false;
                }
                Slogf.d(
                        "SyntheticPasswordManager",
                        "Writing repair mode credential tied to user %d",
                        Integer.valueOf(i));
                int loadWeaverSlot = loadWeaverSlot(i, j);
                if (loadWeaverSlot != -1) {
                    lockSettingsStorage.writeFile(
                            lockSettingsStorage.getRepairModePersistentDataFile(),
                            LockSettingsStorage.PersistentData.toBytes(
                                    2, loadWeaverSlot, 0, fromBytes.toBytes()),
                            true);
                } else {
                    lockSettingsStorage.writeFile(
                            lockSettingsStorage.getRepairModePersistentDataFile(),
                            LockSettingsStorage.PersistentData.toBytes(
                                    1, i, 0, fromBytes.toBytes()),
                            true);
                }
                return true;
            }
            Slog.w(
                    "SyntheticPasswordManager",
                    "Can't write repair mode credential while GSI is running");
        }
        return false;
    }
}
