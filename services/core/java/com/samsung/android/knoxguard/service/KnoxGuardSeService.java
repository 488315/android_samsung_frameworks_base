package com.samsung.android.knoxguard.service;

import android.R;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.DumpUtils;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.IRemoteLockMonitorCallback;
import com.android.internal.widget.RemoteLockInfo;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;

import com.samsung.android.knoxguard.IKnoxGuardManager;
import com.samsung.android.knoxguard.service.receiver.AlarmReceiver;
import com.samsung.android.knoxguard.service.receiver.IntentRelayReceiver;
import com.samsung.android.knoxguard.service.receiver.SystemSeReceiver;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.knoxguard.service.utils.IntegritySeUtil;
import com.samsung.android.knoxguard.service.utils.Utils;
import com.samsung.android.server.pm.mm.MaintenanceModeManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

public final class KnoxGuardSeService extends IKnoxGuardManager.Stub {
    public static final String KEY_INTEGRITY_RESULT = "integrity_result";
    public static final String KEY_IS_MAINTENANCE_MODE_SUPPORTED = "is_maintenance_mode_supported";
    public static final String KEY_LOCK_RESULT = "lock_result";
    public static final int KG_FAILCOUNT_FOR_DELAY = 1;
    public static final String KNOXGUARD_SERVICE = "knoxguard_service";
    public static final boolean LOG = false;
    public static final int TA_VERSION_NOT_SET = -1;
    public static final String adbBlockKey = "adbCommandBlock";
    public static final String doBlockKey = "doProvisionBlock";
    public static Context mContext;
    public static ILockSettings mLockSettingsService;
    public static boolean mSkipPin;
    public static boolean mSkipSupport;
    public ConnectivityManager mConnectivityManagerService = null;
    public static final String TAG = "KG.KnoxGuardSeService";
    public static String mPreFix = "knox.guard";
    public static List mActionList = null;
    public static IntentRelayReceiver intentRelayReceiver = null;
    public static SystemSeReceiver userPresentReceiver = null;
    public static String mSettedInterface = null;
    public static int mFailureCount = -1;
    public static KgErrWrapper mTAError = null;
    public static String mClientName = null;
    public static String mPhoneNumber = null;
    public static String mEmailAddress = null;
    public static String mMessage = null;
    public static int mTAVersion = -1;
    public static Bundle mBundle = null;
    public static int mLockResult = 0;
    public static RemoteLockInfo mRetryRemoteLockInfo = null;
    public static String sfPolicyCache = null;
    public static AppOpsManager.OnOpChangedInternalListener opListener = new AnonymousClass1();
    public static IRemoteLockMonitorCallback mRemoteLockMonitorCallback = new AnonymousClass2();

    /* renamed from: com.samsung.android.knoxguard.service.KnoxGuardSeService$1, reason: invalid class name */
    public final class AnonymousClass1 extends AppOpsManager.OnOpChangedInternalListener {
        public final void onOpChanged(int i, String str) {
            if ("com.samsung.android.kgclient".equals(str)
                    && Constants.PROTECTED_APP_OPS_LIST.contains(Integer.valueOf(i))) {
                try {
                    IntegritySeUtil.enableAppOpIfNotAllowed(
                            KnoxGuardSeService.mContext
                                    .getPackageManager()
                                    .getPackageInfo("com.samsung.android.kgclient", 0),
                            (AppOpsManager) KnoxGuardSeService.mContext.getSystemService("appops"),
                            i);
                } catch (Throwable th) {
                    Slog.e(KnoxGuardSeService.TAG, "Error - appOps : " + th.getMessage());
                }
            }
        }
    }

    /* renamed from: com.samsung.android.knoxguard.service.KnoxGuardSeService$2, reason: invalid class name */
    public final class AnonymousClass2 extends IRemoteLockMonitorCallback.Stub {
        public final void changeRemoteLockState(RemoteLockInfo remoteLockInfo)
                throws RemoteException {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    new StringBuilder("changeRemoteLockState data = "),
                    remoteLockInfo.lockType,
                    KnoxGuardSeService.TAG);
        }

        public final int checkRemoteLockPassword(byte[] bArr) {
            String str = KnoxGuardSeService.TAG;
            Slog.i(str, "checkRemoteLockPassword");
            try {
                int intResult =
                        KnoxGuardSeService.getIntResult(
                                KnoxGuardNative.verifyHOTPPinRefactor(
                                        new String(bArr, StandardCharsets.UTF_8)),
                                true);
                KnoxGuardSeService.mFailureCount = intResult;
                if (intResult == 0) {
                    Slog.i(str, "[HOTP] pin is correct!");
                    Utils.setKGSystemProperty();
                    KnoxGuardSeService.unregisterUserPresentReceiver();
                    KnoxGuardSeService.unlockCompleted();
                } else {
                    Slog.e(
                            str,
                            "[HOTP] pin is wrong!!! current failure count ("
                                    + KnoxGuardSeService.mFailureCount
                                    + ")");
                    KnoxGuardSeService.setRemoteLockToLockscreen(true);
                }
            } catch (Throwable th) {
                th.printStackTrace();
                Slog.e(KnoxGuardSeService.TAG, "[HOTP] verify pin error");
            }
            return KnoxGuardSeService.mFailureCount;
        }
    }

    class KGLockscreenInfo implements Serializable {
        private String mClientName;
        private String mCustomAppName;
        private String mCustomAppPackageName;
        private String mEmailAddress;
        private String mMessage;
        private String mPhoneNumber;
        private boolean mSkipPin;
        private boolean mSkipSupport;

        public KGLockscreenInfo(
                String str,
                String str2,
                String str3,
                String str4,
                boolean z,
                boolean z2,
                Bundle bundle) {
            this.mClientName = str;
            this.mMessage = str4;
            this.mPhoneNumber = str2;
            this.mEmailAddress = str3;
            this.mSkipPin = z;
            this.mSkipSupport = z2;
            setBundle(bundle);
        }

        public final Bundle getBundle() {
            Bundle bundle = new Bundle();
            bundle.putCharSequence("customer_package_name", this.mCustomAppPackageName);
            bundle.putCharSequence("customer_app_name", this.mCustomAppName);
            return bundle;
        }

        public final String getClientName() {
            return this.mClientName;
        }

        public final String getEmailAddress() {
            return this.mEmailAddress;
        }

        public final String getMessage() {
            return this.mMessage;
        }

        public final String getPhoneNumber() {
            return this.mPhoneNumber;
        }

        public final boolean getSkipPin() {
            return this.mSkipPin;
        }

        public final boolean getSkipSupport() {
            return this.mSkipSupport;
        }

        public final void setBundle(Bundle bundle) {
            if (bundle == null) {
                this.mCustomAppPackageName = null;
                this.mCustomAppName = null;
                return;
            }
            CharSequence charSequence = bundle.getCharSequence("customer_package_name");
            if (charSequence != null) {
                this.mCustomAppPackageName = charSequence.toString();
            } else {
                this.mCustomAppPackageName = null;
            }
            CharSequence charSequence2 = bundle.getCharSequence("customer_app_name");
            if (charSequence2 != null) {
                this.mCustomAppName = charSequence2.toString();
            } else {
                this.mCustomAppName = null;
            }
        }

        public final void setClientName(String str) {
            this.mClientName = str;
        }

        public final void setEmailAddress(String str) {
            this.mEmailAddress = str;
        }

        public final void setMessage(String str) {
            this.mMessage = str;
        }

        public final void setPhoneNumber(String str) {
            this.mPhoneNumber = str;
        }

        public final void setSkipPin(boolean z) {
            this.mSkipPin = z;
        }

        public final void setSkipSupport(boolean z) {
            this.mSkipSupport = z;
        }
    }

    public KnoxGuardSeService(Context context) {
        mContext = context;
        int stateAndSetToKGSystemProperty = Utils.getStateAndSetToKGSystemProperty();
        registerReceiver(mContext);
        registerAlarmReceiver(mContext, stateAndSetToKGSystemProperty);
        registerUserPresentReceiverIfLocked(stateAndSetToKGSystemProperty);
        IntegritySeUtil.setInitialState(mContext, stateAndSetToKGSystemProperty, opListener);
    }

    public static void bindAndSetToLockScreen() {
        Slog.i(TAG, "bindAndSetToLockScreen");
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService =
                        ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            mLockSettingsService.registerRemoteLockCallback(3, mRemoteLockMonitorCallback);
            setRemoteLockToLockscreen(true);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005a, code lost:

       if (r5 == 0) goto L48;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x006b, code lost:

       if (r5 == 0) goto L48;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.Object deserialize(byte[] r5, java.lang.Class r6) {
        /*
            java.lang.String r6 = "Deserialize inputstream failed IO exception"
            java.lang.String r0 = "Deserialize failed IO exception"
            r1 = 0
            if (r5 == 0) goto L86
            int r2 = r5.length
            if (r2 != 0) goto Lc
            goto L86
        Lc:
            java.io.ByteArrayInputStream r2 = new java.io.ByteArrayInputStream     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 java.lang.ClassNotFoundException -> L48
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L3f java.io.IOException -> L44 java.lang.ClassNotFoundException -> L48
            java.io.ObjectInputStream r5 = new java.io.ObjectInputStream     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L39 java.lang.ClassNotFoundException -> L3c
            r5.<init>(r2)     // Catch: java.lang.Throwable -> L34 java.io.IOException -> L39 java.lang.ClassNotFoundException -> L3c
            java.lang.Object r1 = r5.readObject()     // Catch: java.lang.Throwable -> L2e java.io.IOException -> L30 java.lang.ClassNotFoundException -> L32
            r2.close()     // Catch: java.io.IOException -> L1e
            goto L23
        L1e:
            java.lang.String r2 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            android.util.Slog.e(r2, r0)
        L23:
            r5.close()     // Catch: java.io.IOException -> L28
            goto L6e
        L28:
            java.lang.String r5 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            android.util.Slog.e(r5, r6)
            goto L6e
        L2e:
            r1 = move-exception
            goto L6f
        L30:
            r3 = move-exception
            goto L4c
        L32:
            r3 = move-exception
            goto L5d
        L34:
            r5 = move-exception
            r4 = r1
            r1 = r5
            r5 = r4
            goto L6f
        L39:
            r3 = move-exception
            r5 = r1
            goto L4c
        L3c:
            r3 = move-exception
            r5 = r1
            goto L5d
        L3f:
            r5 = move-exception
            r2 = r1
            r1 = r5
            r5 = r2
            goto L6f
        L44:
            r3 = move-exception
            r5 = r1
            r2 = r5
            goto L4c
        L48:
            r3 = move-exception
            r5 = r1
            r2 = r5
            goto L5d
        L4c:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L2e
            if (r2 == 0) goto L5a
            r2.close()     // Catch: java.io.IOException -> L55
            goto L5a
        L55:
            java.lang.String r2 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            android.util.Slog.e(r2, r0)
        L5a:
            if (r5 == 0) goto L6e
            goto L23
        L5d:
            r3.printStackTrace()     // Catch: java.lang.Throwable -> L2e
            if (r2 == 0) goto L6b
            r2.close()     // Catch: java.io.IOException -> L66
            goto L6b
        L66:
            java.lang.String r2 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            android.util.Slog.e(r2, r0)
        L6b:
            if (r5 == 0) goto L6e
            goto L23
        L6e:
            return r1
        L6f:
            if (r2 == 0) goto L7a
            r2.close()     // Catch: java.io.IOException -> L75
            goto L7a
        L75:
            java.lang.String r2 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            android.util.Slog.e(r2, r0)
        L7a:
            if (r5 == 0) goto L85
            r5.close()     // Catch: java.io.IOException -> L80
            goto L85
        L80:
            java.lang.String r5 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            android.util.Slog.e(r5, r6)
        L85:
            throw r1
        L86:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.knoxguard.service.KnoxGuardSeService.deserialize(byte[],"
                    + " java.lang.Class):java.lang.Object");
    }

    public static byte[] getByteArrayResult(KgErrWrapper kgErrWrapper) {
        return getByteArrayResult(kgErrWrapper, false);
    }

    public static byte[] getByteArrayResult(KgErrWrapper kgErrWrapper, boolean z) {
        if (z) {
            mTAError = kgErrWrapper;
        }
        if (kgErrWrapper == null || kgErrWrapper.err != 0) {
            return null;
        }
        return kgErrWrapper.data;
    }

    public static int getIntResult(KgErrWrapper kgErrWrapper) {
        return getIntResult(kgErrWrapper, true);
    }

    public static int getIntResult(KgErrWrapper kgErrWrapper, boolean z) {
        if (z) {
            mTAError = kgErrWrapper;
        }
        if (kgErrWrapper == null) {
            return -1;
        }
        int i = kgErrWrapper.err;
        if (i == 0 || i == 771) {
            return kgErrWrapper.result;
        }
        return -1;
    }

    public static KGLockscreenInfo getKGLockObject() {
        String str = TAG;
        Slog.i(str, "getKGVaultData");
        try {
            byte[] byteArrayResult =
                    getByteArrayResult(
                            KnoxGuardNative.tz_getLockObject(KnoxGuardNative.KGTA_PARAM_DEFAULT),
                            false);
            if (byteArrayResult == null) {
                return null;
            }
            if (byteArrayResult.length != 0) {
                return (KGLockscreenInfo) deserialize(byteArrayResult, KGLockscreenInfo.class);
            }
            Slog.w(str, "No data");
            return null;
        } catch (Throwable th) {
            Slog.e(TAG, "getKGVaultData error: " + th);
            return null;
        }
    }

    public static long getLockoutDelayTime(int i) {
        if (i == 6) {
            return 60000L;
        }
        if (i == 7) {
            return 300000L;
        }
        if (i == 8) {
            return 900000L;
        }
        if (i == 9) {
            return ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        }
        if (i >= 10) {
            return BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        }
        return 0L;
    }

    public static RemoteLockInfo getRemoteLockInfoForRetry() {
        Slog.d(TAG, "getRemoteLockInfoForRetry : " + mRetryRemoteLockInfo);
        return mRetryRemoteLockInfo;
    }

    public static String getStringResult(KgErrWrapper kgErrWrapper) {
        mTAError = kgErrWrapper;
        if (kgErrWrapper == null || kgErrWrapper.err != 0) {
            return null;
        }
        return kgErrWrapper.getStr();
    }

    public static int getTaErrorCode() {
        KgErrWrapper kgErrWrapper = mTAError;
        if (kgErrWrapper != null) {
            return kgErrWrapper.err;
        }
        return 0;
    }

    public static void initializeFailureCount() {
        try {
            mFailureCount = Integer.parseInt(getStringResult(KnoxGuardNative.tz_getTAInfo(3)));
        } catch (Throwable th) {
            Slog.e(TAG, "initializeFailureCount error : " + th.getMessage());
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                new StringBuilder("mFailureCount : "), mFailureCount, TAG);
    }

    public static void registerReceiver(
            Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        if (MaintenanceModeManager.isInMaintenanceMode()) {
            context.registerReceiverForAllUsers(broadcastReceiver, intentFilter, null, null);
        } else {
            context.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    public static void registerReceiver(
            Context context,
            BroadcastReceiver broadcastReceiver,
            IntentFilter intentFilter,
            int i) {
        if (MaintenanceModeManager.isInMaintenanceMode()) {
            context.registerReceiverForAllUsers(broadcastReceiver, intentFilter, null, null, i);
        } else {
            context.registerReceiver(broadcastReceiver, intentFilter, i);
        }
    }

    public static void registerUserPresentReceiver() {
        Slog.i(TAG, "call registerUserPresentReceiver");
        unregisterUserPresentReceiver();
        IntentFilter intentFilter = new IntentFilter();
        userPresentReceiver = new SystemSeReceiver();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.setPriority(100000001);
        mContext.registerReceiverForAllUsers(userPresentReceiver, intentFilter, null, null);
    }

    public static void registerUserPresentReceiverIfLocked(int i) {
        if (i == 3) {
            registerUserPresentReceiver();
        }
    }

    public static void setActionList(List list) {
        Utils.checkCallerAndKgPermission(mContext);
        mActionList = list;
    }

    public static void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public static void setClientName(String str) {
        mClientName = str;
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void setEmailAddress(String str) {
        mEmailAddress = str;
    }

    public static void setFailureCount(int i) {
        mFailureCount = i;
    }

    public static void setIntentRelayReceiver(IntentRelayReceiver intentRelayReceiver2) {
        intentRelayReceiver = intentRelayReceiver2;
    }

    public static void setLockResult(boolean z) {
        mLockResult = z ? 1 : 2;
    }

    public static void setMessage(String str) {
        mMessage = str;
    }

    public static void setPhoneNumber(String str) {
        mPhoneNumber = str;
    }

    public static void setPreFix(String str) {
        Utils.checkCallerAndKgPermission(mContext);
        mPreFix = str;
    }

    public static void setRemoteLockToLockscreen(boolean z) {
        String string;
        String format;
        String str = TAG;
        Slog.i(str, "setRemoteLockToLockscreen");
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService =
                        ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            if (mFailureCount < 0) {
                initializeFailureCount();
            }
            long lockoutDelayTime = getLockoutDelayTime(mFailureCount);
            Slog.i(str, "kgvDelayTime " + lockoutDelayTime);
            KGLockscreenInfo kGLockObject = getKGLockObject();
            if (kGLockObject != null) {
                mClientName = kGLockObject.getClientName();
                mPhoneNumber = kGLockObject.getPhoneNumber();
                mEmailAddress = kGLockObject.getEmailAddress();
                mMessage = kGLockObject.getMessage();
                mSkipPin = kGLockObject.getSkipPin();
                mSkipSupport = kGLockObject.getSkipSupport();
                mBundle = kGLockObject.getBundle();
            }
            if (Utils.isTabletDevice()) {
                string = mContext.getString(R.string.permdesc_manageOngoingCalls);
                format =
                        String.format(
                                mContext.getString(R.string.permdesc_manageFingerprint),
                                "Device Services");
            } else {
                string = mContext.getString(R.string.permdesc_manageNetworkPolicy);
                format =
                        String.format(
                                mContext.getString(R.string.permdesc_invokeCarrierSetup),
                                "Device Services");
            }
            RemoteLockInfo.Builder builder = new RemoteLockInfo.Builder(3, z);
            String str2 = mClientName;
            if (str2 != null && !str2.equalsIgnoreCase("")) {
                string = mClientName;
            }
            RemoteLockInfo.Builder clientName = builder.setClientName(string);
            String str3 = mPhoneNumber;
            if (str3 == null) {
                str3 = "";
            }
            RemoteLockInfo.Builder phoneNumber = clientName.setPhoneNumber(str3);
            String str4 = mEmailAddress;
            if (str4 == null) {
                str4 = "";
            }
            RemoteLockInfo.Builder emailAddress = phoneNumber.setEmailAddress(str4);
            String str5 = mMessage;
            if (str5 != null && !str5.equalsIgnoreCase("")) {
                format = mMessage;
            }
            RemoteLockInfo build =
                    emailAddress
                            .setMessage(format)
                            .setAllowFailCount(1)
                            .setLockTimeOut(lockoutDelayTime)
                            .setBlockCount(0)
                            .setSkipPinContainer(mSkipPin)
                            .setSkipSupportContainer(mSkipSupport)
                            .setBundle(mBundle)
                            .build();
            boolean performLockscreen = Utils.performLockscreen(mLockSettingsService, build, z);
            if (!z || performLockscreen) {
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            Utils.startRetryLockAlarm(mContext, build);
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void setRetryRemoteLockInfo(RemoteLockInfo remoteLockInfo) {
        Slog.d(TAG, "setRetryRemoteLockInfo : " + remoteLockInfo);
        mRetryRemoteLockInfo = remoteLockInfo;
    }

    public static void setSettedInterface(String str) {
        mSettedInterface = str;
    }

    public static void setSfPolicyCache(String str) {
        sfPolicyCache = str;
    }

    public static void setSkipPin(boolean z) {
        mSkipPin = z;
    }

    public static void setSkipSupport(boolean z) {
        mSkipSupport = z;
    }

    public static void setTAVersion(int i) {
        mTAVersion = i;
    }

    public static void setTaError(KgErrWrapper kgErrWrapper) {
        mTAError = kgErrWrapper;
    }

    public static void setUserPresentReceiverEnabled(boolean z) {
        if (z) {
            registerUserPresentReceiver();
        } else {
            unregisterUserPresentReceiver();
        }
    }

    public static void unbindFromLockScreen() {
        Slog.i(TAG, "unbindFromLockScreen");
        try {
            if (mLockSettingsService == null) {
                mLockSettingsService =
                        ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
            }
            mLockSettingsService.unregisterRemoteLockCallback(3, mRemoteLockMonitorCallback);
            setRemoteLockToLockscreen(false);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Throwable th) {
            throw th;
        }
    }

    public static void unlockCompleted() {
        Slog.d(TAG, "onUnlockedByPasscode");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        IntentRelayManager.sendRequestedIntent(
                mContext, "com.samsung.kgclient.android.intent.action.MANUAL_UNLOCK", null);
        Binder.restoreCallingIdentity(clearCallingIdentity);
    }

    public static void unregisterUserPresentReceiver() {
        Slog.i(TAG, "call unregisterUserPresentReceiver");
        SystemSeReceiver systemSeReceiver = userPresentReceiver;
        if (systemSeReceiver != null) {
            try {
                mContext.unregisterReceiver(systemSeReceiver);
                userPresentReceiver = null;
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
    }

    public final void bindToLockScreen() {
        bindAndSetToLockScreen();
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String str = TAG;
        Slog.i(str, "call dump");
        if (DumpUtils.checkDumpPermission(mContext, str, printWriter)) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            printWriter.println(str + " state: ");
            try {
                Cursor query =
                        mContext.getContentResolver()
                                .query(Constants.KG_LOG_URI, null, null, null, null);
                if (query != null) {
                    while (query.moveToNext()) {
                        try {
                            printWriter.println(query.getString(query.getColumnIndex("data")));
                        } finally {
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
                Binder.restoreCallingIdentity(clearCallingIdentity);
            } catch (Throwable th) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                throw th;
            }
        }
    }

    public final String generateHotpDHRequest() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "generateHotpDHRequest");
        return getStringResult(
                KnoxGuardNative.tz_generateHotpDhRequest(KnoxGuardNative.KGTA_PARAM_DEFAULT));
    }

    public final boolean getBooleanValueFromJson(String str, String str2, boolean z) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            Slog.e(TAG, "getBooleanValueFromJson empty values");
            return z;
        }
        try {
            return new JSONObject(str).getBoolean(str2);
        } catch (JSONException e) {
            Slog.e(TAG, "getBooleanValueFromJson error : " + e.getMessage());
            return z;
        }
    }

    public final String getClientData() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "getClientData");
        return getStringResult(
                KnoxGuardNative.tz_getClientData(KnoxGuardNative.KGTA_PARAM_DEFAULT));
    }

    public final ConnectivityManager getConnectivityManagerService() {
        Slog.i(TAG, "call getConnectivityManagerService");
        if (ServiceManager.getService("connectivity") != null
                && this.mConnectivityManagerService == null) {
            this.mConnectivityManagerService =
                    (ConnectivityManager) mContext.getSystemService("connectivity");
        }
        return this.mConnectivityManagerService;
    }

    public final String getHotpChallenge() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "getHotpChallenge");
        return getStringResult(
                KnoxGuardNative.tz_getHotpChallenge(KnoxGuardNative.KGTA_PARAM_DEFAULT));
    }

    public final String getKGID() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "getKGID");
        return getStringResult(KnoxGuardNative.tz_getKGID(KnoxGuardNative.KGTA_PARAM_DEFAULT));
    }

    public final String getKGPolicy() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "getKGPolicy");
        return getStringResult(KnoxGuardNative.getKGPolicyRefactor());
    }

    public final Bundle getKGServiceInfo() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "getKGServiceInfo");
        IntegritySeUtil.IntegritySeResult checkKGClientIntegrityAndEnableComponentsWithFlag =
                IntegritySeUtil.checkKGClientIntegrityAndEnableComponentsWithFlag(
                        mContext, KnoxGuardNative.getTAState(), false);
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_LOCK_RESULT, mLockResult);
        bundle.putString(
                KEY_INTEGRITY_RESULT,
                IntegritySeUtil.getFailedIntegrityResult(
                        checkKGClientIntegrityAndEnableComponentsWithFlag));
        bundle.putBoolean(KEY_IS_MAINTENANCE_MODE_SUPPORTED, true);
        return bundle;
    }

    public final int getKGServiceVersion() {
        return Constants.KG_SERVICE_VERSION;
    }

    public final String getLockAction() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "getLockAction");
        return getStringResult(
                KnoxGuardNative.tz_getLockAction(KnoxGuardNative.KGTA_PARAM_DEFAULT));
    }

    public final String getNonce(String str, String str2) {
        Utils.checkCallerAndKgPermission(mContext);
        String str3 = TAG;
        Slog.d(str3, "getNonce");
        if (str != null && str2 != null) {
            return getStringResult(KnoxGuardNative.getNonceRefactor(str, str2));
        }
        Slog.i(str3, "getNonce null parameter!");
        return null;
    }

    public final String getPBAUniqueNumber() {
        String str;
        String str2;
        Utils.checkCallerAndKgPermission(mContext);
        Slog.i(TAG, "call getPBAUniqueNumber");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (Utils.isExistFile(Constants.UFS_UN_R)) {
                str = Utils.getTextFromFile(Constants.UFS_UN_R);
            } else if (Utils.isExistFile(Constants.UFS_UN)) {
                str = Utils.getTextFromFile(Constants.UFS_UN);
            } else if (Utils.isExistFile(Constants.EMMC_UN_R)) {
                str = Utils.getTextFromFile(Constants.EMMC_UN_R);
            } else if (Utils.isExistFile(Constants.EMMC_UN)) {
                str = Utils.getTextFromFile(Constants.EMMC_UN);
            } else {
                if (!Utils.isExistFile(Constants.EMMC_CID)) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    return "";
                }
                String textFromFile = Utils.getTextFromFile(Constants.EMMC_CID);
                String textFromFile2 = Utils.getTextFromFile(Constants.EMMC_NAME);
                if (textFromFile != null) {
                    String substring = textFromFile.substring(0, 2);
                    if (textFromFile2 != null) {
                        if (substring.equalsIgnoreCase("15")) {
                            str2 = textFromFile2.substring(0, 2);
                        } else {
                            if (!substring.equalsIgnoreCase("02")
                                    && !substring.equalsIgnoreCase("45")) {
                                if (!substring.equalsIgnoreCase(Constants.OTP_BIT_KG_COMPLETED)
                                        && !substring.equalsIgnoreCase("90")) {
                                    if (substring.equalsIgnoreCase("FE")) {
                                        str2 = textFromFile2.substring(4, 6);
                                    }
                                }
                                str2 = textFromFile2.substring(1, 3);
                            }
                            str2 = textFromFile2.substring(3, 5);
                        }
                        str =
                                ((("c" + str2) + textFromFile.substring(18, 20))
                                                + textFromFile.substring(20, 28))
                                        + textFromFile.substring(28, 30);
                    }
                    str2 = "";
                    str =
                            ((("c" + str2) + textFromFile.substring(18, 20))
                                            + textFromFile.substring(20, 28))
                                    + textFromFile.substring(28, 30);
                } else {
                    str = "c";
                }
            }
            if (str == null) {
                Binder.restoreCallingIdentity(clearCallingIdentity);
                return "";
            }
            String upperCase = str.toUpperCase();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return upperCase;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final String getSfPolicy() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "getSfPolicy");
        return getStringResult(KnoxGuardNative.tz_getSfPolicy(KnoxGuardNative.KGTA_PARAM_DEFAULT));
    }

    public final boolean getSfPolicyValue(String str) {
        try {
            if (sfPolicyCache == null) {
                sfPolicyCache =
                        getStringResult(
                                KnoxGuardNative.tz_getSfPolicy(KnoxGuardNative.KGTA_PARAM_DEFAULT));
            }
            return getBooleanValueFromJson(sfPolicyCache, str, true);
        } catch (Throwable th) {
            Slog.e(TAG, "getSfPolicyValue error : " + th.getMessage());
            return true;
        }
    }

    public final String getStringSystemProperty(String str, String str2) {
        Utils.checkCallerAndKgPermission(mContext);
        return Utils.getStringSystemProperty(str, str2);
    }

    public final int getTAError() {
        Utils.checkCallerAndKgPermission(mContext);
        return getTaErrorCode();
    }

    public final String getTAInfo(int i) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "getTAInfo: infoFlag : " + i);
        return getStringResult(KnoxGuardNative.tz_getTAInfo(i));
    }

    public final int getTAState() {
        return getTAStateSetError(false);
    }

    public final int getTAStateSetError(boolean z) {
        int intResult =
                getIntResult(KnoxGuardNative.tz_getTAState(KnoxGuardNative.KGTA_PARAM_DEFAULT), z);
        AnyMotionDetector$$ExternalSyntheticOutline0.m(intResult, "getTAState : ", TAG);
        if (intResult < 0 || intResult > 5) {
            return 5;
        }
        return intResult;
    }

    public final int getTAVersion() {
        int i = mTAVersion;
        if (-1 != i) {
            return i;
        }
        try {
            String stringResult = getStringResult(KnoxGuardNative.tz_getTAInfo(1));
            mTAVersion = Integer.valueOf(stringResult).intValue();
            Slog.d(TAG, "getTAVersion : " + stringResult);
        } catch (Throwable th) {
            Slog.e(TAG, "TA version not converted to int: " + th.getMessage());
        }
        return mTAVersion;
    }

    public final boolean isKGAllowADB() {
        if (!isSfPolicyRequired()) {
            return true;
        }
        boolean z = !getSfPolicyValue(adbBlockKey);
        DeviceIdleController$$ExternalSyntheticOutline0.m("isKGAllowADB : ", TAG, z);
        return z;
    }

    public final boolean isKGAllowDO() {
        if (!isSfPolicyRequired()) {
            return true;
        }
        boolean z = !getSfPolicyValue(doBlockKey);
        DeviceIdleController$$ExternalSyntheticOutline0.m("isKGAllowDO : ", TAG, z);
        return z;
    }

    public final boolean isRetryLockActivationRequired(int i) {
        return getTAVersion() >= 3
                ? Utils.isSingleOtpBitFusedAndStateIsNotCompleted(i)
                : Utils.isOtpBitFusedWithActive() || 3 == i || 2 == i;
    }

    public final boolean isSfPolicyRequired() {
        String stringSystemProperty =
                Utils.getStringSystemProperty(
                        Constants.KG_OTP_BIT_SYSTEM_PROPERTY, Constants.OTP_BIT_KG_UNKNOWN);
        if ("0".equals(stringSystemProperty)
                || Constants.OTP_BIT_KG_UNKNOWN.equals(stringSystemProperty)) {
            DeviceIdleController$$ExternalSyntheticOutline0.m(
                    "isSfPolicyRequired: not fused (", stringSystemProperty, ")", TAG);
            return false;
        }
        if (!Constants.IS_FIRST_API_SUPPORT_SF_POLICY && getTAVersion() < 4) {
            Slog.d(TAG, "isSfPolicyRequired: not supported");
            return false;
        }
        if (!"1".equals(stringSystemProperty) || 4 != KnoxGuardNative.getTAState()) {
            return true;
        }
        Slog.d(TAG, "isSfPolicyRequired: completed");
        return false;
    }

    public final boolean isSkipSupportContainerSupported() {
        Slog.i(TAG, "call isSkipSupportContainerSupported");
        return Utils.isSkipSupportContainerSupported();
    }

    public final boolean isVpnExceptionRequired() {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] strArr = Constants.strState;
            String stringSystemProperty =
                    Utils.getStringSystemProperty(Constants.KG_SYSTEM_PROPERTY, strArr[5]);
            if (getTAVersion() >= 3) {
                z = Utils.isSingleOtpBitFusedAndStateIsNotCompleted(stringSystemProperty);
            } else {
                if (!stringSystemProperty.equals(strArr[2])
                        && !stringSystemProperty.equals(strArr[3])
                        && !stringSystemProperty.equals(strArr[5])
                        && !Utils.isOtpBitFusedWithActive()) {
                    z = false;
                }
                z = true;
            }
            Slog.i(
                    TAG,
                    "call isVpnExceptionRequired, state : "
                            + stringSystemProperty
                            + " , result : "
                            + z);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int lockScreen(
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            boolean z,
            boolean z2,
            Bundle bundle) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "lockScreen");
        mClientName = str2;
        mPhoneNumber = str3;
        mEmailAddress = str4;
        mMessage = str5;
        mSkipPin = z;
        mSkipSupport = z2;
        mBundle = bundle;
        int intResult =
                getIntResult(
                        KnoxGuardNative.lockScreenRefactor(
                                str, str2, str3, str4, str5, z, z2, bundle),
                        true);
        Utils.setKGSystemProperty();
        bindAndSetToLockScreen();
        return intResult;
    }

    public final String makeRotReturn(int i, int i2) {
        return String.format("<%d>:<%d>", Integer.valueOf(i), Integer.valueOf(i2));
    }

    public final int provisionCert(String str, String str2, String str3, String str4) {
        Utils.checkCallerAndKgPermission(mContext);
        String str5 = TAG;
        Slog.d(str5, "provisionCert");
        if (!TextUtils.isEmpty(str)
                && !TextUtils.isEmpty(str2)
                && !TextUtils.isEmpty(str3)
                && !TextUtils.isEmpty(str4)) {
            return getIntResult(KnoxGuardNative.provisionCert(str, str2, str3, str4), true);
        }
        Slog.e(str5, "provisionCert failed: input null");
        return -1;
    }

    public final void registerAlarmReceiver(Context context, int i) {
        Slog.i(TAG, "call registerAlarmReceiver");
        if (isRetryLockActivationRequired(i)) {
            registerReceiver(
                    context,
                    new AlarmReceiver(),
                    BatteryService$$ExternalSyntheticOutline0.m(Constants.INTENT_RETRY_LOCK),
                    4);
        }
    }

    public final void registerIntent(String str, List list) {
        Utils.checkCallerAndKgPermission(mContext);
        unRegisterIntent();
        intentRelayReceiver = new IntentRelayReceiver();
        IntentFilter intentFilter = new IntentFilter();
        setPreFix(str);
        setActionList(list);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            intentFilter.addAction((String) it.next());
        }
        registerReceiver(mContext, intentRelayReceiver, intentFilter);
        Slog.i(TAG, "KG registerIntent");
    }

    public final void registerReceiver(Context context) {
        Slog.i(TAG, "call registerReceiver");
        IntentFilter intentFilter = new IntentFilter();
        SystemSeReceiver systemSeReceiver = new SystemSeReceiver();
        if (Utils.isChinaDevice()) {
            intentFilter.addAction(Constants.INTENT_SECSETUPWIZARD_COMPLETE);
            intentFilter.addAction(Constants.INTENT_SETUPWIZARD_COMPLETE);
            registerReceiver(context, systemSeReceiver, intentFilter);
        } else {
            intentFilter.addDataScheme("package");
            intentFilter.addDataSchemeSpecificPart("com.samsung.android.kgclient", 0);
            intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
            ActivityManagerService$$ExternalSyntheticOutline0.m(
                    intentFilter,
                    "android.intent.action.PACKAGE_CHANGED",
                    "android.intent.action.PACKAGE_REPLACED",
                    "android.intent.action.PACKAGE_REMOVED",
                    "android.intent.action.PACKAGE_DATA_CLEARED");
            intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
            registerReceiver(context, systemSeReceiver, intentFilter);
        }
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter2.setPriority(100000001);
        registerReceiver(context, systemSeReceiver, intentFilter2);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addDataScheme("package");
        intentFilter3.addDataSchemeSpecificPart(Constants.SYSTEMUI_PACKAGE_NAME, 0);
        intentFilter3.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter3.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter3.setPriority(100000001);
        registerReceiver(context, systemSeReceiver, intentFilter3);
    }

    public final int resetRPMB() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "resetRPMB");
        int intResult = getIntResult(KnoxGuardNative.resetRPMBRefactor(null), true);
        Utils.setKGSystemProperty();
        return intResult;
    }

    public final void setAirplaneMode(boolean z) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.i(TAG, "call setAirplaneMode");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                ((ConnectivityManager) mContext.getSystemService("connectivity"))
                        .setAirplaneMode(z);
            } catch (Exception e) {
                Slog.w(TAG, "Exception : " + e.getMessage());
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int setCheckingState() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "setCheckingState");
        int intResult =
                getIntResult(
                        KnoxGuardNative.tz_userChecking(KnoxGuardNative.KGTA_PARAM_DEFAULT), true);
        Utils.setKGSystemProperty();
        return intResult;
    }

    public final int setClientData(String str) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "setClientData");
        return getIntResult(KnoxGuardNative.setClientDataRefactor(str), true);
    }

    public final void setRemoteLockToLockscreen(
            int i,
            boolean z,
            String str,
            String str2,
            String str3,
            boolean z2,
            String str4,
            int i2,
            long j,
            int i3,
            boolean z3,
            Bundle bundle) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.i(TAG, "call setRemoteLockToLockscreen");
        Utils.setRemoteLockToLockscreen(
                mContext, i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle, true);
    }

    public final void setRemoteLockToLockscreenWithSkipSupport(
            int i,
            boolean z,
            String str,
            String str2,
            String str3,
            boolean z2,
            String str4,
            int i2,
            long j,
            int i3,
            boolean z3,
            Bundle bundle,
            boolean z4) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.i(TAG, "call setRemoteLockToLockscreen with skipSupportContainer");
        Utils.setRemoteLockToLockscreen(
                mContext, i, z, str, str2, str3, z2, str4, i2, j, i3, z3, bundle, z4);
    }

    public final boolean shouldBlockCustomRom() {
        String[] strArr = Constants.strState;
        String stringSystemProperty =
                Utils.getStringSystemProperty(Constants.KG_SYSTEM_PROPERTY, strArr[5]);
        boolean z = true;
        if (getTAVersion() < 3
                ? !(stringSystemProperty.equals(strArr[0])
                        || stringSystemProperty.equals(strArr[2])
                        || stringSystemProperty.equals(strArr[3])
                        || Utils.isOtpBitFusedWithActive())
                : !(Utils.isSingleOtpBitFusedAndStateIsNotCompleted(stringSystemProperty)
                        || stringSystemProperty.equals(strArr[0])
                        || stringSystemProperty.equals(strArr[2])
                        || stringSystemProperty.equals(strArr[3])
                        || stringSystemProperty.equals(strArr[5]))) {
            z = false;
        }
        Slog.i(
                TAG,
                "call shouldBlockCustomRom, state : " + stringSystemProperty + " , result : " + z);
        return z;
    }

    public final boolean showInstallmentStatus() {
        boolean z;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            String[] strArr = Constants.strState;
            String stringSystemProperty =
                    Utils.getStringSystemProperty(Constants.KG_SYSTEM_PROPERTY, strArr[5]);
            if (getTAVersion() >= 3) {
                z = Utils.isSingleOtpBitFusedAndStateIsNotCompleted(stringSystemProperty);
            } else {
                if (!stringSystemProperty.equals(strArr[2])
                        && !stringSystemProperty.equals(strArr[3])
                        && !Utils.isOtpBitFusedWithActive()) {
                    z = false;
                }
                z = true;
            }
            Slog.i(
                    TAG,
                    "call showInstallmentStatus, state : "
                            + stringSystemProperty
                            + " , result : "
                            + z);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final void unRegisterIntent() {
        Utils.checkCallerAndKgPermission(mContext);
        setActionList(null);
        IntentRelayReceiver intentRelayReceiver2 = intentRelayReceiver;
        if (intentRelayReceiver2 != null) {
            try {
                mContext.unregisterReceiver(intentRelayReceiver2);
                intentRelayReceiver = null;
            } catch (Throwable th) {
                Slog.e(TAG, th.getMessage(), th);
            }
        }
        Slog.i(TAG, "KG unRegisterIntent");
    }

    public final int unlockScreen() {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "unlockScreen");
        unbindFromLockScreen();
        int intResult =
                getIntResult(
                        KnoxGuardNative.tz_unlockScreen(KnoxGuardNative.KGTA_PARAM_DEFAULT), true);
        mFailureCount = 0;
        Utils.setKGSystemProperty();
        return intResult;
    }

    public final int verifyCompleteToken(String str) {
        Utils.checkCallerAndKgPermission(mContext);
        int intResult = getIntResult(KnoxGuardNative.verifyCompleteTokenRefactor(str), true);
        Slog.d(TAG, "verifyCompleteToken result : " + intResult);
        Utils.setKGSystemProperty();
        if (intResult == 0) {
            sfPolicyCache = null;
            unbindFromLockScreen();
        }
        return intResult;
    }

    public final int verifyHOTPDHChallenge(String str, String str2, String str3) {
        Utils.checkCallerAndKgPermission(mContext);
        int intResult =
                getIntResult(KnoxGuardNative.verifyHotpDHChallengeRefactor(str, str2, str3), true);
        Slog.d(TAG, "verifyHOTPDHChallenge result : " + intResult);
        Utils.setKGSystemProperty();
        return intResult;
    }

    public final int verifyHOTPPin(String str) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "verifyHOTPPin");
        int intResult = getIntResult(KnoxGuardNative.verifyHOTPPinRefactor(str), true);
        mFailureCount = intResult;
        return intResult;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String verifyKgRot() {
        /*
            r6 = this;
            android.content.Context r0 = com.samsung.android.knoxguard.service.KnoxGuardSeService.mContext
            com.samsung.android.knoxguard.service.utils.Utils.checkCallerAndKgPermission(r0)
            java.lang.String r0 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            java.lang.String r1 = "verifyKgRot"
            android.util.Slog.d(r0, r1)
            int r1 = android.os.Binder.getCallingUid()
            android.content.Context r2 = com.samsung.android.knoxguard.service.KnoxGuardSeService.mContext
            android.content.pm.PackageManager r2 = r2.getPackageManager()
            java.lang.String r2 = r2.getNameForUid(r1)
            java.lang.String r3 = "caller: "
            java.lang.String r4 = " ("
            java.lang.String r5 = ")"
            java.lang.String r1 = com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(r1, r3, r2, r4, r5)
            android.util.Slog.d(r0, r1)
            r0 = 0
            java.lang.String r1 = r6.makeRotReturn(r0, r0)
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L5d
            java.lang.String r3 = "com.samsung.android.kgclient"
            boolean r2 = r3.equals(r2)
            if (r2 != 0) goto L3c
            goto L5d
        L3c:
            android.content.Context r2 = com.samsung.android.knoxguard.service.KnoxGuardSeService.mContext
            boolean r2 = com.samsung.android.knoxguard.service.utils.IntegritySeUtil.checkSignatures(r2)
            if (r2 != 0) goto L4c
            r1 = 6002(0x1772, float:8.41E-42)
            java.lang.String r1 = r6.makeRotReturn(r1, r0)
        L4a:
            r2 = r0
            goto L64
        L4c:
            android.content.Context r2 = com.samsung.android.knoxguard.service.KnoxGuardSeService.mContext
            boolean r2 = com.samsung.android.knoxguard.service.utils.IntegritySeUtil.isEnabled(r2)
            if (r2 != 0) goto L5b
            r1 = 6001(0x1771, float:8.409E-42)
            java.lang.String r1 = r6.makeRotReturn(r1, r0)
            goto L4a
        L5b:
            r2 = 1
            goto L64
        L5d:
            r1 = 6000(0x1770, float:8.408E-42)
            java.lang.String r1 = r6.makeRotReturn(r1, r0)
            goto L4a
        L64:
            if (r2 == 0) goto L80
            int r1 = com.samsung.android.knoxguard.service.KnoxGuardNative.KGTA_PARAM_DEFAULT     // Catch: java.lang.Exception -> L71
            com.samsung.android.knoxguard.service.KgErrWrapper r1 = com.samsung.android.knoxguard.service.KnoxGuardNative.tz_verifyKgRot(r1)     // Catch: java.lang.Exception -> L71
            java.lang.String r1 = getStringResult(r1)     // Catch: java.lang.Exception -> L71
            goto L80
        L71:
            r1 = move-exception
            r2 = 6003(0x1773, float:8.412E-42)
            java.lang.String r6 = r6.makeRotReturn(r2, r0)
            java.lang.String r0 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            java.lang.String r2 = "Error verifyKgRot - "
            android.util.Slog.e(r0, r2, r1)
            r1 = r6
        L80:
            java.lang.String r6 = com.samsung.android.knoxguard.service.KnoxGuardSeService.TAG
            java.lang.String r0 = "RoT: "
            com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r0, r1, r6)
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.knoxguard.service.KnoxGuardSeService.verifyKgRot():java.lang.String");
    }

    public final String verifyPolicy(String str, String str2) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "verifyPolicy");
        return getStringResult(KnoxGuardNative.verifyPolicyRefactor(str, str2));
    }

    public final String verifyRegistrationInfo(String str, String str2) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "verifyRegistrationInfo");
        String stringResult =
                getStringResult(KnoxGuardNative.verifyRegistrationInfoRefactor(str, str2));
        Utils.setKGSystemProperty();
        return stringResult;
    }

    public final int verifySfPolicy(String str, String str2) {
        Utils.checkCallerAndKgPermission(mContext);
        Slog.d(TAG, "verifySfPolicy");
        sfPolicyCache = null;
        return getIntResult(KnoxGuardNative.verifySfPolicy(str, str2), true);
    }
}
