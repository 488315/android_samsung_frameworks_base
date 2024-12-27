package com.android.server.enterprise.email;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.sec.enterprise.email.EnterpriseExchangeAccount;
import android.text.TextUtils;
import android.util.Log;
import android.util.Slog;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;
import com.android.server.am.Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0;
import com.android.server.chimera.genie.GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0;
import com.android.server.enterprise.EnterpriseService;
import com.android.server.enterprise.EnterpriseServiceCallback;
import com.android.server.enterprise.security.DeviceAccountPolicy;
import com.android.server.enterprise.storage.EdmStorageProvider;
import com.android.server.enterprise.storage.EdmStorageProviderBase;
import com.android.server.enterprise.ucm.UniversalCredentialManagerService;
import com.android.server.enterprise.utils.Utils;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.KnoxInternalFeature;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.accounts.ExchangeAccount;
import com.samsung.android.knox.accounts.IEmailPolicy;
import com.samsung.android.knox.accounts.IExchangeAccountPolicy;
import com.samsung.android.knox.ucm.configurator.CredentialStorage;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ExchangeAccountPolicy extends IExchangeAccountPolicy.Stub
        implements EnterpriseServiceCallback {
    public static final HashMap mDeviceId = new HashMap();
    public final Context mContext;
    public final EdmStorageProvider mEdmStorageProvider;
    public boolean mExchangeServiceDisabled = false;
    public boolean mRestartExtendDelay = false;
    public int preCallingUid = -1;
    public UniversalCredentialManagerService mUCMService = null;
    public EnterpriseDeviceManager mEDM = null;
    public final AnonymousClass2 mHandler = new Handler() { // from class:
                // com.android.server.enterprise.email.ExchangeAccountPolicy.2
                @Override // android.os.Handler
                public final void handleMessage(Message message) {
                    UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Handler : message = "),
                            message.what,
                            "ExchangeAccountPolicy");
                    if (message.what != 1) {
                        return;
                    }
                    int i = message.arg2;
                    ExchangeAccountPolicy exchangeAccountPolicy = ExchangeAccountPolicy.this;
                    if (exchangeAccountPolicy.mExchangeServiceDisabled) {
                        PackageManager packageManager =
                                exchangeAccountPolicy.mContext.getPackageManager();
                        Context context = SettingsUtils.emails;
                        ComponentName componentName =
                                new ComponentName(
                                        "com.samsung.android.email.provider",
                                        "com.samsung.android.email.provider.exchange.ExchangeService");
                        DirEncryptService$$ExternalSyntheticOutline0.m(
                                i,
                                "Handler / RESTART_EMAIL_APP : Enabling EAS ExchangeService user ",
                                "ExchangeAccountPolicy");
                        packageManager.setComponentEnabledSetting(componentName, 1, 0);
                        exchangeAccountPolicy.mExchangeServiceDisabled = false;
                        long clearCallingIdentity = Binder.clearCallingIdentity();
                        try {
                            try {
                                exchangeAccountPolicy.mContext.sendBroadcastAsUser(
                                        new Intent(
                                                "com.samsung.android.knox.intent.action.EAS_INTENT_INTERNAL"),
                                        new UserHandle(i),
                                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                            } catch (Exception e) {
                                Log.e(
                                        "ExchangeAccountPolicy",
                                        "Handler / RESTART_EMAIL_APP : fail to restart"
                                            + " ExchangeService. ",
                                        e);
                            }
                        } finally {
                            Binder.restoreCallingIdentity(clearCallingIdentity);
                        }
                    }
                    exchangeAccountPolicy.mRestartExtendDelay = false;
                }
            };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.enterprise.email.ExchangeAccountPolicy$1, reason: invalid class name */
    public final class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object val$sync;

        public /* synthetic */ AnonymousClass1(int i, Object obj) {
            this.$r8$classId = i;
            this.val$sync = obj;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String str;
            AccountSMIMECertificate sMIMECertificate;
            int i;
            boolean z;
            switch (this.$r8$classId) {
                case 0:
                    int intExtra =
                            intent.getIntExtra(
                                    "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL",
                                    intent.getIntExtra("userid", -1));
                    String stringExtra = intent.getStringExtra("deviceid");
                    if (stringExtra == null) {
                        stringExtra =
                                intent.getStringExtra(
                                        "com.samsung.android.knox.intent.extra.DEVICE_ID_INTERNAL");
                    }
                    Slog.d(
                            "ExchangeAccountPolicy",
                            "getDeviceId() : receive userId = "
                                    + intExtra
                                    + " , deviceid = "
                                    + stringExtra);
                    ExchangeAccountPolicy.mDeviceId.put(Integer.valueOf(intExtra), stringExtra);
                    synchronized (this.val$sync) {
                        this.val$sync.notifyAll();
                    }
                    return;
                default:
                    String action = intent.getAction();
                    ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                            "Received Intent - ", action, "ExchangeAccountPolicy/Receiver");
                    if (action == null) {
                        Log.i("ExchangeAccountPolicy/Receiver", "failed. action is null.");
                        return;
                    }
                    boolean z2 = false;
                    if (action.endsWith(
                            "com.samsung.edm.intent.action.EXCHANGE_SMIME_INSTALL_STATUS")) {
                        Log.i(
                                "ExchangeAccountPolicy/Receiver",
                                "Received - ACTION_SMIME_INSTALL_STATUS");
                        int intExtra2 =
                                intent.getIntExtra(
                                        "com.samsung.android.knox.intent.extra.SMIME_RESULT", 0);
                        long longExtra =
                                intent.getLongExtra(
                                        "com.samsung.android.knox.intent.extra.ACCOUNT_ID", -1L);
                        long longExtra2 =
                                intent.getLongExtra(
                                        "com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL",
                                        -1L);
                        AccountSMIMECertificate sMIMECertificate2 =
                                AccountsReceiver.getSMIMECertificate("S" + longExtra2);
                        if (sMIMECertificate2 == null) {
                            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                                    "failed. no registed work. id = ",
                                    longExtra2,
                                    "ExchangeAccountPolicy/Receiver");
                            return;
                        }
                        if (intExtra2 != -1 || longExtra <= 0) {
                            Log.i(
                                    "ExchangeAccountPolicy/Receiver",
                                    "failed to enforce SMIME. accId = "
                                            + longExtra
                                            + ", status = "
                                            + intExtra2);
                            return;
                        }
                        int intExtra3 =
                                intent.getIntExtra(
                                        "com.samsung.android.knox.intent.extra.SMIME_INSTALL_TYPE",
                                        1);
                        ContextInfo contextInfo = sMIMECertificate2.mCxtInfo;
                        int i2 = contextInfo.mCallerUid;
                        int i3 = contextInfo.mContainerId;
                        if (intExtra3 == 2 || intExtra3 == 1) {
                            z2 =
                                    ((ExchangeAccountPolicy) this.val$sync)
                                            .putPolicyState(
                                                    i2,
                                                    i3,
                                                    longExtra,
                                                    "ForceSmimeCertForEncryption",
                                                    true);
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    "force SMIME Certificate for Enryption. ret = ",
                                    "ExchangeAccountPolicy/Receiver",
                                    z2);
                        }
                        if (intExtra3 == 3 || intExtra3 == 1) {
                            z2 =
                                    ((ExchangeAccountPolicy) this.val$sync)
                                            .putPolicyState(
                                                    i2,
                                                    i3,
                                                    longExtra,
                                                    "ForceSmimeCertForSigning",
                                                    true);
                            DeviceIdleController$$ExternalSyntheticOutline0.m(
                                    "force SMIME Certificate for Signing. ret = ",
                                    "ExchangeAccountPolicy/Receiver",
                                    z2);
                        }
                        if (z2) {
                            DirEncryptService$$ExternalSyntheticOutline0.m(
                                    intExtra3,
                                    "success to SMIME Certificate .",
                                    "ExchangeAccountPolicy/Receiver");
                            return;
                        } else {
                            DirEncryptService$$ExternalSyntheticOutline0.m(
                                    intExtra3,
                                    "failed to SMIME Certificate .",
                                    "ExchangeAccountPolicy/Receiver");
                            return;
                        }
                    }
                    if (!"com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL"
                            .equals(action)) {
                        Log.i("ExchangeAccountPolicy/Receiver", "no Defined Intent.");
                        return;
                    }
                    Log.i(
                            "ExchangeAccountPolicy",
                            "Received - ACTION_ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL");
                    int intExtra4 =
                            intent.getIntExtra(
                                    "com.samsung.android.knox.intent.extra.SMIME_RESULT", 0);
                    long longExtra3 =
                            intent.getLongExtra(
                                    "com.samsung.android.knox.intent.extra.ACCOUNT_ID", -1L);
                    int intExtra5 =
                            intent.getIntExtra(
                                    "com.samsung.android.knox.intent.extra.SMIME_INSTALL_TYPE", -1);
                    long longExtra4 =
                            intent.getLongExtra(
                                    "com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL",
                                    -1L);
                    try {
                        sMIMECertificate = AccountsReceiver.getSMIMECertificate("S" + longExtra4);
                    } catch (Exception e) {
                        e = e;
                        str = "com.samsung.android.knox.intent.extra.ACCOUNT_ID";
                    }
                    try {
                        if (sMIMECertificate != null) {
                            String str2 = sMIMECertificate.mPath;
                            ContextInfo contextInfo2 = sMIMECertificate.mCxtInfo;
                            int i4 = contextInfo2.mCallerUid;
                            str = "com.samsung.android.knox.intent.extra.ACCOUNT_ID";
                            int i5 = contextInfo2.mContainerId;
                            if (intExtra4 != -1 || longExtra3 <= 0) {
                                Log.i("ExchangeAccountPolicy/Receiver", "forcing " + longExtra4);
                            } else {
                                if (intExtra5 == 0) {
                                    if (str2 == null) {
                                        z =
                                                ((ExchangeAccountPolicy) this.val$sync)
                                                        .resetForcedSMIMECertificateInternal(
                                                                contextInfo2,
                                                                longExtra3,
                                                                i5,
                                                                2,
                                                                true);
                                        Slog.d(
                                                "ExchangeAccountPolicy/Receiver",
                                                "release SMIME Certificate for Enryption (Alias) by"
                                                    + " null.");
                                    } else {
                                        z =
                                                ((ExchangeAccountPolicy) this.val$sync)
                                                        .putPolicyState(
                                                                i4,
                                                                i5,
                                                                longExtra3,
                                                                "ForceSmimeCertForEncryption",
                                                                true);
                                        Slog.d(
                                                "ExchangeAccountPolicy/Receiver",
                                                "force SMIME Certificate for Enryption (Alias). ret"
                                                    + " = "
                                                        + z);
                                    }
                                    i = 1;
                                } else {
                                    i = 1;
                                    z = false;
                                }
                                if (intExtra5 == i) {
                                    if (str2 == null) {
                                        z =
                                                ((ExchangeAccountPolicy) this.val$sync)
                                                        .resetForcedSMIMECertificateInternal(
                                                                sMIMECertificate.mCxtInfo,
                                                                longExtra3,
                                                                i5,
                                                                3,
                                                                true);
                                        Slog.d(
                                                "ExchangeAccountPolicy/Receiver",
                                                "release SMIME Certificate for Signing (Alias) by"
                                                    + " null.");
                                    } else {
                                        z =
                                                ((ExchangeAccountPolicy) this.val$sync)
                                                        .putPolicyState(
                                                                i4,
                                                                i5,
                                                                longExtra3,
                                                                "ForceSmimeCertForSigning",
                                                                true);
                                        Slog.d(
                                                "ExchangeAccountPolicy/Receiver",
                                                "force SMIME Certificate for Signing (Alias). ret ="
                                                    + " "
                                                        + z);
                                    }
                                }
                                if (z) {
                                    if (str2 == null) {
                                        Log.i(
                                                "ExchangeAccountPolicy/Receiver",
                                                "success to release SMIME Certificate (Alias) : "
                                                        + intExtra5);
                                    } else {
                                        Log.i(
                                                "ExchangeAccountPolicy/Receiver",
                                                "success to SMIME Certificate (Alias) : "
                                                        + intExtra5);
                                    }
                                } else if (str2 == null) {
                                    Log.i(
                                            "ExchangeAccountPolicy/Receiver",
                                            "failed to release SMIME Certificate (Alias) : "
                                                    + intExtra5);
                                } else {
                                    Log.i(
                                            "ExchangeAccountPolicy/Receiver",
                                            "failed to SMIME Certificate (Alias)." + intExtra5);
                                }
                            }
                        } else {
                            str = "com.samsung.android.knox.intent.extra.ACCOUNT_ID";
                            Log.i(
                                    "ExchangeAccountPolicy/Receiver",
                                    "force SMIME Certificate has failed. status = "
                                            + intExtra4
                                            + ", accId="
                                            + longExtra3);
                        }
                    } catch (Exception e2) {
                        e = e2;
                        Log.e(
                                "ExchangeAccountPolicy/Receiver",
                                "Failed to apply SMIME Alis Policy on success.",
                                e);
                        ExchangeAccountPolicy exchangeAccountPolicy =
                                (ExchangeAccountPolicy) this.val$sync;
                        exchangeAccountPolicy.getClass();
                        Intent intent2 =
                                new Intent(
                                        "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_RESULT");
                        intent2.putExtra(str, longExtra3);
                        intent2.putExtra(
                                "com.samsung.android.knox.intent.extra.SMIME_RESULT", intExtra4);
                        intent2.putExtra(
                                "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_TYPE",
                                intExtra5);
                        exchangeAccountPolicy.mContext.sendBroadcastAsUser(
                                intent2,
                                UserHandle.ALL,
                                "com.samsung.android.knox.permission.KNOX_EXCHANGE");
                        return;
                    }
                    ExchangeAccountPolicy exchangeAccountPolicy2 =
                            (ExchangeAccountPolicy) this.val$sync;
                    exchangeAccountPolicy2.getClass();
                    Intent intent22 =
                            new Intent(
                                    "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_RESULT");
                    intent22.putExtra(str, longExtra3);
                    intent22.putExtra(
                            "com.samsung.android.knox.intent.extra.SMIME_RESULT", intExtra4);
                    intent22.putExtra(
                            "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_TYPE",
                            intExtra5);
                    exchangeAccountPolicy2.mContext.sendBroadcastAsUser(
                            intent22,
                            UserHandle.ALL,
                            "com.samsung.android.knox.permission.KNOX_EXCHANGE");
                    return;
            }
        }
    }

    /* JADX WARN: Type inference failed for: r3v2, types: [com.android.server.enterprise.email.ExchangeAccountPolicy$2] */
    public ExchangeAccountPolicy(Context context) {
        int i = 1;
        this.mContext = context;
        this.mEdmStorageProvider = new EdmStorageProvider(context);
        try {
            IntentFilter intentFilter = new IntentFilter();
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(i, this);
            intentFilter.addAction("com.samsung.edm.intent.action.EXCHANGE_SMIME_INSTALL_STATUS");
            intentFilter.addAction(
                    "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_EMAIL_INTERNAL");
            context.registerReceiverAsUser(
                    anonymousClass1,
                    UserHandle.ALL,
                    intentFilter,
                    "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL",
                    null,
                    2);
            Log.i("ExchangeAccountPolicy", "success to add receiver");
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "Regist BroadCast failed : ", e);
        }
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            Context context2 = SettingsUtils.emails;
            ComponentName componentName =
                    new ComponentName(
                            "com.samsung.android.email.provider",
                            "com.samsung.android.email.provider.exchange.ExchangeService");
            if (packageManager == null
                    || packageManager.getComponentEnabledSetting(componentName) != 2) {
                return;
            }
            Log.i("ExchangeAccountPolicy", "Enabling EAS ExchangeService");
            packageManager.setComponentEnabledSetting(componentName, 1, 0);
        } catch (Exception e2) {
            Log.e("ExchangeAccountPolicy", "Constructor failed : ", e2);
        }
    }

    public static boolean getDefaultValueState$1(String str) {
        boolean z = false;
        if (!str.equals("ReqSigSmime")
                && !str.equals("ReqEncryptSmime")
                && !str.equals("ForceSmimeCert")
                && (str.equals("AllowSettingChange")
                        || str.equals("AllowNotificationChange")
                        || (!str.equals("ForceSmimeCertForEncryption")
                                && !str.equals("ForceSmimeCertForSigning")
                                && str.equals("AttachmentEnable")))) {
            z = true;
        }
        Slog.d("ExchangeAccountPolicy", "getDefaultValueState() : policy = " + str + " ret = " + z);
        return z;
    }

    public static boolean isEmailHTMLAllowed(ContextInfo contextInfo, String str) {
        try {
            IEmailPolicy asInterface =
                    IEmailPolicy.Stub.asInterface(ServiceManager.getService("email_policy"));
            if (asInterface != null) {
                return asInterface.getAllowHTMLEmail(contextInfo, str);
            }
            return true;
        } catch (RemoteException e) {
            Log.e(
                    "ExchangeAccountPolicy",
                    "setMaxEmailBodyTruncationSize() : Failed talking with email policy",
                    e);
            return true;
        }
    }

    public final long addNewAccount(
            ContextInfo contextInfo,
            String str,
            String str2,
            String str3,
            String str4,
            int i,
            int i2,
            boolean z,
            String str5,
            String str6,
            String str7,
            boolean z2,
            boolean z3,
            String str8,
            boolean z4,
            boolean z5,
            boolean z6,
            String str9,
            String str10) {
        Slog.d("ExchangeAccountPolicy", "addNewAccount() EX 2");
        return addNewAccount_ex(
                contextInfo,
                str,
                str2,
                str3,
                str4,
                i,
                i2,
                z,
                str5,
                str6,
                str7,
                z2,
                z3,
                str8,
                z4,
                z5,
                z6,
                str9,
                str10,
                SystemService.PHASE_LOCK_SETTINGS_READY,
                1020,
                62,
                -2,
                0,
                3,
                4,
                true,
                1,
                1,
                null,
                null);
    }

    public final long addNewAccount_ex(
            ContextInfo contextInfo,
            String str,
            String str2,
            String str3,
            String str4,
            int i,
            int i2,
            boolean z,
            String str5,
            String str6,
            String str7,
            boolean z2,
            boolean z3,
            String str8,
            boolean z4,
            boolean z5,
            boolean z6,
            String str9,
            String str10,
            int i3,
            int i4,
            int i5,
            int i6,
            int i7,
            int i8,
            int i9,
            boolean z7,
            int i10,
            int i11,
            byte[] bArr,
            String str11) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", ">>>>>>>>>>>>>>>>>\t\taddNewAccount EX ");
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        int i12 = enforceExchangeAccountPermission.mContainerId;
        String validStr = SettingsUtils.getValidStr(str);
        String validStr2 = SettingsUtils.getValidStr(str2);
        String validStr3 = SettingsUtils.getValidStr(str8);
        String validStr4 = SettingsUtils.getValidStr(str6);
        SettingsUtils.getValidStr(str5);
        String validStr5 = SettingsUtils.getValidStr(str7);
        String validStr6 = SettingsUtils.getValidStr(str3);
        String validStr7 = SettingsUtils.getValidStr(str4);
        String validStr8 = SettingsUtils.getValidStr(str9);
        String validStr9 = SettingsUtils.getValidStr(str10);
        if (validStr2 == null
                || !SettingsUtils.isValidEmailAddress(validStr2)
                || validStr3 == null
                || validStr4 == null
                || validStr6 == null) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "addNewAccount() EX : Error :: Invalid input parameters.");
            return -1L;
        }
        if (!SettingsUtils.isPackageInstalled(i12, "com.samsung.android.email.provider")
                && !SettingsUtils.isPackageInstalled(i12, "com.samsung.android.focus")) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i12,
                    "addNewAccount() EX : Error :: Email and Focus are not installed on user ",
                    "ExchangeAccountPolicy");
            return -1L;
        }
        if (true == z2 && true == z3) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "addNewAccount() EX : Error :: Invalid input parameters."
                        + " 'emailNotificationVibrateAlways' and"
                        + " 'emailNotificationVibrateWhenSilent' only one will be true at a time");
            return -1L;
        }
        if (SettingsUtils.getAccountId(
                        enforceExchangeAccountPermission,
                        validStr7,
                        validStr6,
                        validStr3,
                        "eas",
                        true,
                        this.mContext)
                > 0) {
            Log.i("ExchangeAccountPolicy", "addNewAccount() EX : Error :: Account already exists.");
            return -1L;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i("ExchangeAccountPolicy", "addNewAccount() EX : " + callingOrCurrentUserId);
                long accountEmailPassword =
                        setAccountEmailPassword(enforceExchangeAccountPermission, validStr8);
                long accountCertificatePassword =
                        setAccountCertificatePassword(enforceExchangeAccountPermission, str11);
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL",
                        accountEmailPassword);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", validStr2);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "eas");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL", validStr3);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.EXTRA_USER_NAME_INTERNAL",
                        validStr6);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL",
                        accountEmailPassword);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SERVER_PATH_PREFIX_INTERNAL",
                        validStr9);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USE_SSL_INTERNAL", z4 ? 1 : 0);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USE_TLS_INTERNAL", z5 ? 1 : 0);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.TRUST_ALL_INTERNAL", z6 ? 1 : 0);
                intent.putExtra("com.samsung.android.knox.intent.extra.DOMAIN_INTERNAL", validStr7);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PEAK_START_TIME_INTERNAL", i3);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_END_TIME_INTERNAL", i4);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_DAYS_INTERNAL", i5);
                intent.putExtra("com.samsung.android.knox.intent.extra.PEAK_INTERNAL", i2);
                intent.putExtra("com.samsung.android.knox.intent.extra.OFF_PEAK_INTERNAL", i6);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ROAMING_SCHEDULE_INTERNAL", i7);
                intent.putExtra("com.samsung.android.knox.intent.extra.PERIOD_EMAIL_INTERNAL", i);
                intent.putExtra("com.samsung.android.knox.intent.extra.", i8);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PERIOD_CALENDAR_INTERNAL", i9);
                intent.putExtra("com.samsung.android.knox.intent.extra.NOTIFY_INTERNAL", z7);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SYNC_CONTACTS_INTERNAL", i10);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SYNC_CALENDAR_INTERNAL", i11);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ACCOUNT_NAME_INTERNAL", validStr);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL", validStr5);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.VIBRATE_WHEN_SILENT_INTERNAL", z3);
                intent.putExtra("com.samsung.android.knox.intent.extra.EXTRA_VIBRATE_INTERNAL", z2);
                intent.putExtra("com.samsung.android.knox.intent.extra.IS_DEFAULT_INTERNAL", z);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.CERTIFICATE_DATA_INTERNAL", bArr);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.CERTIFICATE_PASSWD_ID_INTERNAL",
                        accountCertificatePassword);
                intent.setPackage("com.samsung.android.email.provider");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                intent.setPackage("com.samsung.android.focus");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                Slog.d(
                        "ExchangeAccountPolicy",
                        "addNewAccount() EX : sent intent to Email app : " + validStr2);
            } catch (Exception e) {
                Log.e("ExchangeAccountPolicy", "addNewAccount() EX : failed. ", e);
            }
            Log.i(
                    "ExchangeAccountPolicy",
                    "<<<<<<<<<<<<<<<<<\t\taddNewAccount EX : Broadcasting Email");
            return 0L;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final long addNewAccount_new(ContextInfo contextInfo, ExchangeAccount exchangeAccount) {
        String str;
        int i;
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        if (exchangeAccount == null) {
            Log.i("ExchangeAccountPolicy", "addNewAccount_new : Error :: Invalid Account.");
            return -1L;
        }
        enforceUCMPermission(
                enforceExchangeAccountPermission, exchangeAccount.certificateStorageName);
        SettingsUtils.getValidStr(exchangeAccount.displayName);
        String validStr = SettingsUtils.getValidStr(exchangeAccount.emailAddress);
        String validStr2 = SettingsUtils.getValidStr(exchangeAccount.serverAddress);
        String validStr3 = SettingsUtils.getValidStr(exchangeAccount.protocolVersion);
        SettingsUtils.getValidStr(exchangeAccount.senderName);
        SettingsUtils.getValidStr(exchangeAccount.signature);
        String validStr4 = SettingsUtils.getValidStr(exchangeAccount.easUser);
        String validStr5 = SettingsUtils.getValidStr(exchangeAccount.easDomain);
        String validStr6 = SettingsUtils.getValidStr(exchangeAccount.serverPassword);
        SettingsUtils.getValidStr(exchangeAccount.serverPathPrefix);
        boolean z = exchangeAccount.emailNotificationVibrateAlways;
        boolean z2 = !z;
        if (validStr == null
                || !SettingsUtils.isValidEmailAddress(validStr)
                || validStr2 == null
                || validStr3 == null
                || validStr4 == null) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "addNewAccount_new : Error :: Invalid input parameters.");
            return -1L;
        }
        if (!SettingsUtils.isPackageInstalled(i2, "com.samsung.android.email.provider")
                && !SettingsUtils.isPackageInstalled(i2, "com.samsung.android.focus")) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i2,
                    "addNewAccount() EX : Error :: Email and focus are not installed on user ",
                    "ExchangeAccountPolicy");
            return -1L;
        }
        if (true == z && true == z2) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "addNewAccount_new : Error :: Invalid input parameters."
                        + " 'emailNotificationVibrateAlways' and"
                        + " 'emailNotificationVibrateWhenSilent' only one will be true at a time");
            return -1L;
        }
        if (SettingsUtils.getAccountId(
                        enforceExchangeAccountPermission,
                        validStr5,
                        validStr4,
                        validStr2,
                        "eas",
                        true,
                        this.mContext)
                > 0) {
            Log.i("ExchangeAccountPolicy", "addNewAccount : Error :: Account already exists.");
            return -1L;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i("ExchangeAccountPolicy", "addNewAccount() New : " + callingOrCurrentUserId);
                if (exchangeAccount.smimeCertificatePath == null
                        || exchangeAccount.smimeCertificatePassword == null) {
                    str = validStr;
                } else {
                    Log.i(
                            "ExchangeAccountPolicy",
                            "addNewAccount_new : SMIME Certificate at creation time");
                    int i3 = exchangeAccount.smimeCertificareMode;
                    if (KnoxInternalFeature.KNOX_CONFIG_MDM_VERSION >= 17
                            && (i = exchangeAccount.smimeCertificateMode) >= 1
                            && i <= 3) {
                        i3 = i;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(i2);
                    sb.append("#");
                    str = validStr;
                    sb.append(str);
                    AccountsReceiver.pushSMIMECertificate(
                            sb.toString(),
                            new AccountSMIMECertificate(
                                    enforceExchangeAccountPermission,
                                    exchangeAccount.smimeCertificatePath,
                                    exchangeAccount.smimeCertificatePassword,
                                    i3));
                }
                long accountEmailPassword =
                        setAccountEmailPassword(enforceExchangeAccountPermission, validStr6);
                long accountCertificatePassword =
                        setAccountCertificatePassword(
                                enforceExchangeAccountPermission,
                                exchangeAccount.certificatePassword);
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.CREATE_EMAILACCOUNT_INTERNAL");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL",
                        accountEmailPassword);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "eas");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL", validStr2);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.EXTRA_USER_NAME_INTERNAL",
                        validStr4);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USER_PASSWD_ID_INTERNAL",
                        accountEmailPassword);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USE_SSL_INTERNAL",
                        exchangeAccount.useSSL ? 1 : 0);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.USE_TLS_INTERNAL",
                        exchangeAccount.useTLS ? 1 : 0);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.TRUST_ALL_INTERNAL",
                        exchangeAccount.acceptAllCertificates ? 1 : 0);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.DOMAIN_INTERNAL",
                        exchangeAccount.easDomain);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PEAK_START_TIME_INTERNAL",
                        exchangeAccount.peakStartTime);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PEAK_END_TIME_INTERNAL",
                        exchangeAccount.peakEndTime);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PEAK_DAYS_INTERNAL",
                        exchangeAccount.peakDays);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PEAK_INTERNAL",
                        exchangeAccount.syncInterval);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.OFF_PEAK_INTERNAL",
                        exchangeAccount.offPeak);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ROAMING_SCHEDULE_INTERNAL",
                        exchangeAccount.roamingSchedule);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PERIOD_EMAIL_INTERNAL",
                        exchangeAccount.syncLookback);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.", exchangeAccount.retrivalSize);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.PERIOD_CALENDAR_INTERNAL",
                        exchangeAccount.periodCalendar);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.NOTIFY_INTERNAL",
                        exchangeAccount.isNotify);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SYNC_CONTACTS_INTERNAL",
                        exchangeAccount.syncContacts);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SYNC_CALENDAR_INTERNAL",
                        exchangeAccount.syncCalendar);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ACCOUNT_NAME_INTERNAL",
                        exchangeAccount.displayName);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SIGNATURE_INTERNAL",
                        exchangeAccount.signature);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.VIBRATE_WHEN_SILENT_INTERNAL",
                        true ^ exchangeAccount.emailNotificationVibrateAlways);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.EXTRA_VIBRATE_INTERNAL",
                        exchangeAccount.emailNotificationVibrateAlways);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.IS_DEFAULT_INTERNAL",
                        exchangeAccount.isDefault);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.CERTIFICATE_DATA_INTERNAL",
                        exchangeAccount.certificateData);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.CERTIFICATE_PASSWD_ID_INTERNAL",
                        accountCertificatePassword);
                intent.setPackage("com.samsung.android.email.provider");
                if (!TextUtils.isEmpty(exchangeAccount.certificateAlias)) {
                    String convertUcmUri =
                            convertUcmUri(
                                    enforceExchangeAccountPermission,
                                    exchangeAccount.certificateStorageName,
                                    exchangeAccount.certificateAlias);
                    if (convertUcmUri == null) {
                        Log.i(
                                "ExchangeAccountPolicy",
                                "addNewAccount : Error :: certificate storage name "
                                        + exchangeAccount.certificateStorageName
                                        + " does not exist!");
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                        return -1L;
                    }
                    intent.putExtra(
                            "com.samsung.android.knox.intent.extra.CERTIFICATE_ALIAS_INTERNAL",
                            convertUcmUri);
                    Log.i(
                            "ExchangeAccountPolicy",
                            "addNewAccount : alias has added, alias = ".concat(convertUcmUri));
                }
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                intent.setPackage("com.samsung.android.focus");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                Slog.d(
                        "ExchangeAccountPolicy",
                        "addNewAccount_new : sent intent to Email app : " + str);
            } catch (Exception e) {
                Log.e("ExchangeAccountPolicy", "addNewAccount() NEW : " + e);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0L;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean adminSatisfiesForceSMIMECertificateRules(
            int i, int i2, boolean z, long j) {
        if (z) {
            String[] strArr = {"adminUid"};
            ContentValues contentValues = new ContentValues();
            contentValues.put(
                    EdmStorageProviderBase.getAdminLUIDWhereIn(0, UserHandle.getUserId(i)),
                    "#SelectClause#");
            contentValues.put("AccountId", Long.valueOf(j));
            if (i2 == 3) {
                contentValues.put(
                        "(ForceSmimeCertForSigning=1 OR ForceSmimeCert=1)", "#SelectClause#");
            } else if (i2 == 2) {
                contentValues.put(
                        "(ForceSmimeCertForEncryption=1 OR ForceSmimeCert=1)", "#SelectClause#");
            } else {
                contentValues.put(
                        "((ForceSmimeCertForEncryption=1 AND ForceSmimeCertForSigning=1) OR"
                            + " (ForceSmimeCert=1))",
                        "#SelectClause#");
            }
            ArrayList arrayList =
                    (ArrayList)
                            this.mEdmStorageProvider.getValues(
                                    "ExchangeAccountTable", strArr, contentValues);
            if (!arrayList.isEmpty()
                    && ((ContentValues) arrayList.get(0)).getAsInteger("adminUid").intValue()
                            != i) {
                return false;
            }
        } else {
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("adminUid", Integer.valueOf(i));
            contentValues2.put("AccountId", Long.valueOf(j));
            ArrayList arrayList2 =
                    (ArrayList)
                            this.mEdmStorageProvider.getValues(
                                    "ExchangeAccountTable",
                                    new String[] {
                                        "ForceSmimeCert",
                                        "ForceSmimeCertForEncryption",
                                        "ForceSmimeCertForSigning"
                                    },
                                    contentValues2);
            if (arrayList2.isEmpty()) {
                return false;
            }
            ContentValues contentValues3 = (ContentValues) arrayList2.get(0);
            Integer asInteger = contentValues3.getAsInteger("ForceSmimeCert");
            Integer asInteger2 = contentValues3.getAsInteger("ForceSmimeCertForEncryption");
            Integer asInteger3 = contentValues3.getAsInteger("ForceSmimeCertForSigning");
            if (i2 == 3) {
                if ((asInteger3 == null || asInteger3.intValue() != 1)
                        && (asInteger == null || asInteger.intValue() != 1)) {
                    return false;
                }
            } else if (i2 == 2) {
                if ((asInteger2 == null || asInteger2.intValue() != 1)
                        && (asInteger == null || asInteger.intValue() != 1)) {
                    return false;
                }
            } else if ((asInteger2 == null
                            || asInteger2.intValue() != 1
                            || asInteger3 == null
                            || asInteger3.intValue() != 1)
                    && (asInteger == null || asInteger.intValue() != 1)) {
                return false;
            }
        }
        return true;
    }

    public final boolean allowEmailSettingsChange(ContextInfo contextInfo, long j, boolean z) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "allowEmailSettingsChange() : ", j, "ExchangeAccountPolicy");
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "allowEmailSettingsChange() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        boolean putPolicyState = putPolicyState(i2, i, j, "AllowSettingChange", z);
        Slog.d(
                "ExchangeAccountPolicy",
                "allowEmailSettingsChange() : = "
                        + putPolicyState
                        + " , accId  ="
                        + j
                        + " , allow  ="
                        + z);
        return putPolicyState;
    }

    public final boolean allowInComingAttachments(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "allowInComingAttachments() : ", j, "ExchangeAccountPolicy");
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "allowInComingAttachments_new() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        boolean putPolicyState = putPolicyState(i2, i, j, "AttachmentEnable", z);
        BatteryService$$ExternalSyntheticOutline0.m(
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "allowInComingAttachments() : = ",
                        putPolicyState,
                        " , enable  =",
                        z,
                        " , accId  ="),
                j,
                "ExchangeAccountPolicy");
        return putPolicyState;
    }

    public final String convertUcmUri(ContextInfo contextInfo, String str, String str2) {
        CredentialStorage credentialStorage;
        if (TextUtils.isEmpty(str)) {
            return str2;
        }
        if (this.mUCMService == null) {
            this.mUCMService =
                    (UniversalCredentialManagerService)
                            EnterpriseService.getPolicyService("knox_ucsm_policy");
        }
        UniversalCredentialManagerService universalCredentialManagerService = this.mUCMService;
        if (universalCredentialManagerService == null) {
            return str2;
        }
        CredentialStorage[] availableCredentialStorages =
                universalCredentialManagerService.getAvailableCredentialStorages(contextInfo, true);
        if (availableCredentialStorages != null) {
            int length = availableCredentialStorages.length;
            for (int i = 0; i < length; i++) {
                credentialStorage = availableCredentialStorages[i];
                if (credentialStorage != null && str.equals(credentialStorage.name)) {
                    break;
                }
            }
        }
        credentialStorage = null;
        if (credentialStorage != null) {
            return new UniversalCredentialUtil.UcmUriBuilder(credentialStorage.name)
                    .setResourceId(1)
                    .setUid(contextInfo.mCallerUid)
                    .setAlias(str2)
                    .build();
        }
        return null;
    }

    public final long createAccount(
            ContextInfo contextInfo,
            String str,
            String str2,
            String str3,
            String str4,
            String str5) {
        Slog.d("ExchangeAccountPolicy", "addNewAccount() EX 1");
        return addNewAccount(
                contextInfo,
                null,
                str,
                str2,
                str3,
                1,
                -1,
                false,
                null,
                "2.5",
                null,
                false,
                false,
                str4,
                true,
                false,
                true,
                str5,
                null);
    }

    public final boolean deleteAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "deleteAccount() :", j, "ExchangeAccountPolicy");
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        boolean z = false;
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "deleteAccount_new() : Not valid accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        String str = enterpriseExchangeAccount.mEmailAddress;
        String str2 = enterpriseExchangeAccount.mServerAddress;
        if (str == null || str2 == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "deleteAccount() : no Inforamtion for accId = ", j, "ExchangeAccountPolicy");
            return false;
        }
        Context context = SettingsUtils.emails;
        DeviceAccountPolicy deviceAccountPolicy =
                (DeviceAccountPolicy) EnterpriseService.getPolicyService("device_account_policy");
        if (deviceAccountPolicy != null
                && !deviceAccountPolicy.isAccountRemovalAllowed(
                        "com.samsung.android.email", str, false)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "deleteAccount() : MDM DeviceAccountPolicy restriction - cannot delete account"
                        + " : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Log.i(
                        "ExchangeAccountPolicy",
                        "deleteAccount() : accId = " + j + " , userId = " + callingOrCurrentUserId);
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.DELETE_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", j);
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "eas");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL", str2);
                intent.setPackage("com.samsung.android.email.provider");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
                Binder.restoreCallingIdentity(clearCallingIdentity);
                z = true;
            } catch (Exception e) {
                Log.e("ExchangeAccountPolicy", "deleteAccount() : failed. ", e);
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
            return z;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final ContextInfo enforceExchangeAccountPermission(ContextInfo contextInfo) {
        ContextInfo enforceActiveAdminPermissionByContext =
                getEDM$13()
                        .enforceActiveAdminPermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_EXCHANGE")));
        int i = enforceActiveAdminPermissionByContext.mCallerUid;
        if (this.preCallingUid != i) {
            String packageNameForUid = this.mEdmStorageProvider.getPackageNameForUid(i);
            SettingsUtils.preCallingUid = i;
            SettingsUtils.adminPkg = packageNameForUid;
            DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                    new StringBuilder("setPackageNameForUid :   "),
                    SettingsUtils.adminPkg,
                    "SettingsUtils");
            this.preCallingUid = i;
            Slog.d(
                    "ExchangeAccountPolicy",
                    "Calling UID changed : " + packageNameForUid + " callingUid " + i);
        }
        return enforceActiveAdminPermissionByContext;
    }

    public final void enforceUCMPermission(ContextInfo contextInfo, String str) {
        CredentialStorage[] availableCredentialStorages;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        if (this.mUCMService == null) {
            this.mUCMService =
                    (UniversalCredentialManagerService)
                            EnterpriseService.getPolicyService("knox_ucsm_policy");
        }
        UniversalCredentialManagerService universalCredentialManagerService = this.mUCMService;
        if (universalCredentialManagerService == null
                || (availableCredentialStorages =
                                universalCredentialManagerService.getAvailableCredentialStorages(
                                        contextInfo, true))
                        == null) {
            return;
        }
        for (CredentialStorage credentialStorage : availableCredentialStorages) {
            if (credentialStorage != null && str.equals(credentialStorage.name)) {
                this.mUCMService.enforceSecurityPermission(contextInfo, credentialStorage);
                return;
            }
        }
    }

    public final String getAccountCertificatePassword(ContextInfo contextInfo, long j) {
        String str;
        int i =
                getEDM$13()
                        .enforcePermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_EXCHANGE")))
                        .mContainerId;
        try {
            str = SettingsUtils.getSecurityPassword("C#" + j);
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "getAccountCertificatePassword() failed", e);
            str = null;
        }
        Slog.d("ExchangeAccountPolicy", "getAccountCertificatePassword() success");
        return str;
    }

    public final Account getAccountDetails(ContextInfo contextInfo, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        Log.i(
                "ExchangeAccountPolicy",
                "getAccountDetails() : "
                        + j
                        + " ,  userID = "
                        + Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission));
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount != null) {
            return SettingsUtils.getAccountFromEnterpriseExchangeAccount(enterpriseExchangeAccount);
        }
        return null;
    }

    public final String getAccountEmailPassword(ContextInfo contextInfo, long j) {
        ContextInfo enforcePermissionByContext =
                getEDM$13()
                        .enforcePermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_EXCHANGE")));
        int i = enforcePermissionByContext.mContainerId;
        String nameForUid =
                this.mContext
                        .getPackageManager()
                        .getNameForUid(enforcePermissionByContext.mCallerUid);
        String str = null;
        try {
            String str2 = "E#" + j;
            if (nameForUid.equals("com.samsung.android.focus")) {
                try {
                    Map map = SettingsUtils.mServerPasswordFocus;
                    String str3 = (String) ((HashMap) map).get(str2);
                    ((HashMap) map).remove(str2);
                    str = str3;
                } catch (Exception unused) {
                    Log.w("SettingsUtils", "getSecurityPasswordFocus() failed");
                }
            } else {
                str = SettingsUtils.getSecurityPassword(str2);
            }
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "getAccountEmailPassword() failed", e);
        }
        Slog.d("ExchangeAccountPolicy", "getAccountEmailPassword() success");
        return str;
    }

    public final long getAccountId(ContextInfo contextInfo, String str, String str2, String str3) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "getAccountId() : ", str2, "ExchangeAccountPolicy");
        long accountId =
                SettingsUtils.getAccountId(
                        enforceExchangeAccountPermission,
                        str,
                        str2,
                        str3,
                        "eas",
                        true,
                        this.mContext);
        StringBuilder sb = new StringBuilder("getAccountId() : = ");
        sb.append(accountId);
        sb.append(" ,  easDomain = ");
        sb.append(str);
        Slog.d(
                "ExchangeAccountPolicy",
                OptionalModelParameterRange$$ExternalSyntheticOutline0.m(
                        sb, ", easUser = ", str2, ", activeSyncHost = ", str3));
        return accountId;
    }

    public final Account[] getAllEASAccounts(ContextInfo contextInfo) {
        EnterpriseExchangeAccount[] enterpriseExchangeAccountArr;
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        Log.i(
                "ExchangeAccountPolicy",
                "getAllEASAccounts() : userId = "
                        + Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission));
        ArrayList arrayList = new ArrayList();
        Context context = this.mContext;
        Map map = EmailProviderHelper.mAccountObjectMap;
        ArrayList arrayList2 = new ArrayList();
        long[] allAccountIDS =
                EmailProviderHelper.getAllAccountIDS(context, enforceExchangeAccountPermission);
        if (allAccountIDS != null) {
            for (long j : allAccountIDS) {
                EnterpriseExchangeAccount enterpriseExchangeAccount =
                        EmailProviderHelper.getEnterpriseExchangeAccount(
                                j, context, enforceExchangeAccountPermission);
                if (enterpriseExchangeAccount != null) {
                    arrayList2.add(enterpriseExchangeAccount);
                }
            }
        }
        if (arrayList2.size() > 0) {
            enterpriseExchangeAccountArr = new EnterpriseExchangeAccount[arrayList2.size()];
            Iterator it = arrayList2.iterator();
            int i = 0;
            while (it.hasNext()) {
                enterpriseExchangeAccountArr[i] = (EnterpriseExchangeAccount) it.next();
                i++;
            }
        } else {
            enterpriseExchangeAccountArr = null;
        }
        if (enterpriseExchangeAccountArr != null) {
            for (EnterpriseExchangeAccount enterpriseExchangeAccount2 :
                    enterpriseExchangeAccountArr) {
                if (enterpriseExchangeAccount2 != null) {
                    arrayList.add(
                            SettingsUtils.getAccountFromEnterpriseExchangeAccount(
                                    enterpriseExchangeAccount2));
                }
            }
        } else {
            Log.i("ExchangeAccountPolicy", "getAllEASAccounts_new( ): fail to get list.");
        }
        if (arrayList.size() > 0) {
            return (Account[]) arrayList.toArray(new Account[arrayList.size()]);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v3, types: [long] */
    public final String getDeviceId(ContextInfo contextInfo) {
        AnonymousClass1 anonymousClass1;
        Context context;
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission(contextInfo));
        Log.i("ExchangeAccountPolicy", "getDeviceId() : userID = " + callingOrCurrentUserId);
        long j = 0;
        if (!SettingsUtils.isPackageInstalled(
                callingOrCurrentUserId, "com.samsung.android.email.provider")) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "getDeviceId() failed : Email App is not installed. :"
                        + " com.samsung.android.email.provider");
            return null;
        }
        if (getEDM$13() != null
                && !getEDM$13()
                        .getApplicationPolicy()
                        .getApplicationStateEnabled("com.samsung.android.email.provider")) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "getDeviceId() failed : Email App is disabled. :"
                        + " com.samsung.android.email.provider");
            return null;
        }
        HashMap hashMap = mDeviceId;
        if (hashMap.get(Integer.valueOf(callingOrCurrentUserId)) != null) {
            return (String) hashMap.get(Integer.valueOf(callingOrCurrentUserId));
        }
        Object obj = new Object();
        synchronized (obj) {
            try {
                j = Binder.clearCallingIdentity();
                try {
                    anonymousClass1 = new AnonymousClass1(0, obj);
                    IntentFilter intentFilter =
                            new IntentFilter(
                                    "com.samsung.android.knox.intent.action.GET_EMAIL_DEVICEID_RESULT_INTERNAL");
                    intentFilter.addAction("edm.intent.action.internal.GET_DEVICEID_RESULT");
                    this.mContext.registerReceiverAsUser(
                            anonymousClass1, UserHandle.ALL, intentFilter, null, null, 2);
                    Intent intent =
                            new Intent(
                                    "com.samsung.android.knox.intent.action.GET_EMAIL_DEVICEID_REQUEST_INTERNAL");
                    intent.setPackage("com.samsung.android.email.provider");
                    this.mContext.sendBroadcastAsUser(
                            intent,
                            new UserHandle(callingOrCurrentUserId),
                            "com.samsung.android.knox.permission.KNOX_EMAIL");
                    StringBuilder sb = new StringBuilder("getDeviceId() : sendBroadcast ");
                    sb.append(
                            callingOrCurrentUserId != 0
                                    ? Integer.valueOf(callingOrCurrentUserId)
                                    : "");
                    Log.i("ExchangeAccountPolicy", sb.toString());
                } catch (Exception e) {
                    Log.e("ExchangeAccountPolicy", "getDeviceId() : failed. ", e);
                }
                try {
                    try {
                        if (hashMap.get(Integer.valueOf(callingOrCurrentUserId)) == null) {
                            Log.i(
                                    "ExchangeAccountPolicy",
                                    "getDeviceId() : Device id is still null, waiting 5"
                                        + " seconds...");
                            obj.wait(5000L);
                        }
                        context = this.mContext;
                    } catch (InterruptedException e2) {
                        Log.e("ExchangeAccountPolicy", "getDeviceId() Inturrupted.", e2);
                        context = this.mContext;
                    }
                    context.unregisterReceiver(anonymousClass1);
                    Binder.restoreCallingIdentity(j);
                } catch (Throwable th) {
                    this.mContext.unregisterReceiver(anonymousClass1);
                    throw th;
                }
            } catch (Throwable th2) {
                Binder.restoreCallingIdentity(j);
                throw th2;
            }
        }
        return (String) mDeviceId.get(Integer.valueOf(callingOrCurrentUserId));
    }

    public final EnterpriseDeviceManager getEDM$13() {
        if (this.mEDM == null) {
            this.mEDM = EnterpriseDeviceManager.getInstance(this.mContext);
        }
        return this.mEDM;
    }

    public final boolean getForceSMIMECertificate(ContextInfo contextInfo, long j) {
        return getForceSMIMECertificateForEncryption(contextInfo, j)
                && getForceSMIMECertificateForSigning(contextInfo, j);
    }

    public final boolean getForceSMIMECertificateForEncryption(ContextInfo contextInfo, long j) {
        return getPolicyState(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "ForceSmimeCertForEncryption");
    }

    public final boolean getForceSMIMECertificateForSigning(ContextInfo contextInfo, long j) {
        return getPolicyState(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "ForceSmimeCertForSigning");
    }

    public final int getIncomingAttachmentsSize(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "IncomingAttachmentSize");
    }

    public final int getMaxCalendarAgeFilter(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "MaxCalendarAgeFilter");
    }

    public final int getMaxEmailAgeFilter(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "MaxEmailAgeFilter");
    }

    public final int getMaxEmailBodyTruncationSize(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "MaxEmailBodyTruncationSize");
    }

    public final int getMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, long j) {
        return getPolicyStateasInteger(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "MaxEmailHTMLBodyTruncationSize");
    }

    public final boolean getPolicyState(int i, int i2, long j, String str) {
        boolean defaultValueState$1 = getDefaultValueState$1(str);
        String[] strArr = {str};
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "containerID", i2, "userID");
        contentValues.put("AccountId", Long.valueOf(j));
        try {
            List valuesList =
                    this.mEdmStorageProvider.getValuesList(
                            "ExchangeAccountTable", strArr, contentValues);
            ArrayList arrayList = (ArrayList) valuesList;
            if (arrayList.isEmpty()) {
                StringBuilder m =
                        SystemServiceManager$$ExternalSyntheticOutline0.m(
                                i2,
                                "getPolicyState() : results is default value(null), accId = ",
                                j,
                                ", user id = ");
                m.append(", container id = ");
                m.append(i);
                Slog.d("ExchangeAccountPolicy", m.toString());
                return defaultValueState$1;
            }
            Slog.d(
                    "ExchangeAccountPolicy",
                    "getPolicyState() : results = "
                            + valuesList
                            + ", accId = "
                            + j
                            + ", user id = "
                            + i2
                            + ", container id = "
                            + i);
            boolean defaultValueState$12 = getDefaultValueState$1(str);
            boolean z = defaultValueState$12 ^ true;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                ContentValues contentValues2 = (ContentValues) it.next();
                if (contentValues2.getAsString(str) != null) {
                    if (contentValues2.getAsString(str).equals(z ? "1" : "0")) {
                        Slog.d("ExchangeAccountPolicy", "getPolicyState: restricted. ");
                        return z;
                    }
                }
            }
            Slog.d("ExchangeAccountPolicy", "getPolicyState: no restrictData. ");
            return defaultValueState$12;
        } catch (Exception e) {
            Log.e(
                    "ExchangeAccountPolicy",
                    "getPolicyState() : Exception while getValuesList from EDMStorageProvider",
                    e);
            return defaultValueState$1;
        }
    }

    public final int getPolicyStateasInteger(int i, int i2, long j, String str) {
        if (!str.equals("IncomingAttachmentSize")
                && !str.equals("MaxCalendarAgeFilter")
                && !str.equals("MaxEmailAgeFilter")
                && !str.equals("MaxEmailBodyTruncationSize")) {
            str.equals("MaxEmailHTMLBodyTruncationSize");
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                "getDefaultValueStateInteger() : policy = ",
                str,
                " ret = 0",
                "ExchangeAccountPolicy");
        String[] strArr = {str};
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "containerID", i2, "userID");
        contentValues.put("AccountId", Long.valueOf(j));
        try {
            List valuesList =
                    this.mEdmStorageProvider.getValuesList(
                            "ExchangeAccountTable", strArr, contentValues);
            ArrayList arrayList = (ArrayList) valuesList;
            if (arrayList.isEmpty()) {
                return 0;
            }
            Log.d(
                    "ExchangeAccountPolicy",
                    "getPolicyStateasInteger() : results = "
                            + valuesList
                            + ", accId = "
                            + j
                            + ", user id = "
                            + i2
                            + ", container id = "
                            + i);
            Iterator it = arrayList.iterator();
            int i3 = Integer.MAX_VALUE;
            while (it.hasNext()) {
                Integer asInteger = ((ContentValues) it.next()).getAsInteger(str);
                if (asInteger != null && asInteger.intValue() > 0 && asInteger.intValue() < i3) {
                    i3 = asInteger.intValue();
                }
            }
            int i4 = i3 != Integer.MAX_VALUE ? i3 : 0;
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i4, "getPolicyStateasInteger() : result = ", "ExchangeAccountPolicy");
            return i4;
        } catch (Exception e) {
            Log.e(
                    "ExchangeAccountPolicy",
                    "getPolicyStateasInteger() : Exception while getValuesListasInteger from"
                        + " EDMStorageProvider",
                    e);
            return 0;
        }
    }

    public final boolean getRequireEncryptedSMIMEMessages(ContextInfo contextInfo, long j) {
        return getPolicyState(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "ReqEncryptSmime");
    }

    public final boolean getRequireSignedSMIMEMessages(ContextInfo contextInfo, long j) {
        return getPolicyState(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "ReqSigSmime");
    }

    public final String getSMIMECertificateAlias(ContextInfo contextInfo, long j, int i) {
        if (!isValidEASAccountId(contextInfo, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "getSMIMECertificateAlias() : No exist accId : ", j, "ExchangeAccountPolicy");
            return null;
        }
        try {
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "getSMIMECertificateAlias() : failed. ", e);
        }
        if (i == 0) {
            return SettingsUtils.getSMIMEAlias(contextInfo, j, this.mContext, false);
        }
        if (i == 1) {
            return SettingsUtils.getSMIMEAlias(contextInfo, j, this.mContext, true);
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        i, "getSMIMECertificateAlias() : accId = ", j, ", type = "),
                ", ret = ",
                (String) null,
                "ExchangeAccountPolicy");
        return null;
    }

    public final boolean isEmailNotificationsEnabled(ContextInfo contextInfo, long j) {
        return getPolicyState(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "AllowNotificationChange");
    }

    public final boolean isEmailSettingsChangeAllowed(ContextInfo contextInfo, long j) {
        return getPolicyState(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "AllowSettingChange");
    }

    public final boolean isIncomingAttachmentsAllowed(ContextInfo contextInfo, long j) {
        return getPolicyState(
                contextInfo.mContainerId,
                Utils.getCallingOrCurrentUserId(contextInfo),
                j,
                "AttachmentEnable");
    }

    public final boolean isValidEASAccountId(ContextInfo contextInfo, long j) {
        return j > 0
                && EmailProviderHelper.getEnterpriseExchangeAccount(j, this.mContext, contextInfo)
                        != null;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void notifyToAddSystemService(String str, IBinder iBinder) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminAdded(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onAdminRemoved(int i) {}

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void onPreAdminRemoval(int i) {
        Long asLong;
        int userId = UserHandle.getUserId(i);
        ContentValues contentValues = new ContentValues();
        contentValues.put("adminUid", Integer.valueOf(i));
        Iterator it =
                ((ArrayList)
                                this.mEdmStorageProvider.getValuesList(
                                        "ExchangeAccountTable",
                                        new String[] {"AccountId", "ForceSmimeCert"},
                                        contentValues))
                        .iterator();
        while (it.hasNext()) {
            ContentValues contentValues2 = (ContentValues) it.next();
            if (contentValues2.getAsInteger("ForceSmimeCert").intValue() > 0
                    && (asLong = contentValues2.getAsLong("AccountId")) != null) {
                long longValue = asLong.longValue();
                Slog.d(
                        "ExchangeAccountPolicy",
                        "sendReleaseSMIMECertificate() : accId = "
                                + longValue
                                + ", containerId = 0, userId = "
                                + userId);
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", longValue);
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mContext.sendBroadcastAsUser(
                            intent,
                            new UserHandle(userId),
                            "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        }
        Slog.d("ExchangeAccountPolicy", "onPreAdminRemoval() : ");
    }

    public final boolean putPolicyState(int i, int i2, long j, String str, boolean z) {
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "adminUid", i2, "containerID");
        contentValues.put("AccountId", Long.valueOf(j));
        contentValues.put(str, z ? "1" : "0");
        ContentValues contentValues2 = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues2, "adminUid", i2, "containerID");
        contentValues2.put("AccountId", Long.valueOf(j));
        boolean putValues =
                this.mEdmStorageProvider.putValues(
                        "ExchangeAccountTable", contentValues, contentValues2);
        Slog.d(
                "ExchangeAccountPolicy",
                "putPolicyState: ret = "
                        + putValues
                        + " , accId  ="
                        + j
                        + " , state  ="
                        + z
                        + " , policy ="
                        + str);
        return putValues;
    }

    public final boolean putPolicyState(int i, int i2, String str, int i3, long j) {
        ContentValues contentValues = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues, "adminUid", i2, "containerID");
        contentValues.put("AccountId", Long.valueOf(j));
        contentValues.put(str, Integer.valueOf(i3));
        ContentValues contentValues2 = new ContentValues();
        Pageboost$PageboostFileDBHelper$$ExternalSyntheticOutline0.m(
                i, contentValues2, "adminUid", i2, "containerID");
        contentValues2.put("AccountId", Long.valueOf(j));
        boolean putValues =
                this.mEdmStorageProvider.putValues(
                        "ExchangeAccountTable", contentValues, contentValues2);
        Slog.d(
                "ExchangeAccountPolicy",
                "putPolicyState: ret = "
                        + putValues
                        + " , accId  ="
                        + j
                        + " , value  ="
                        + i3
                        + " , policy ="
                        + str);
        return putValues;
    }

    public final void removePendingAccount(
            ContextInfo contextInfo, String str, String str2, String str3, String str4) {
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission(contextInfo));
        Log.i("ExchangeAccountPolicy", "removePendingAccount() : " + str2);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.DELETE_NOT_VALIDATED_EMAILACCOUNT_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.USER_ID_INTERNAL", str);
                intent.putExtra("com.samsung.android.knox.intent.extra.SERVICE_INTERNAL", "eas");
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.SERVICE_NAME_INTERNAL", str4);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.EXTRA_USER_NAME_INTERNAL", str2);
                intent.putExtra("com.samsung.android.knox.intent.extra.DOMAIN_INTERNAL", str3);
                Context context = SettingsUtils.emails;
                intent.setPackage("com.samsung.android.email.provider");
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
            } catch (Exception e) {
                Log.e(
                        "ExchangeAccountPolicy",
                        "removePendingAccount() : fail to remove pending EAS Account. " + str2,
                        e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean resetForcedSMIMECertificateInternal(
            ContextInfo contextInfo, long j, int i, int i2, boolean z) {
        long clearCallingIdentity;
        int i3 = contextInfo.mContainerId;
        int i4 = contextInfo.mCallerUid;
        ContentValues contentValues = new ContentValues();
        if (i2 == 2) {
            contentValues.put("ForceSmimeCertForEncryption", Boolean.FALSE);
            if (!z) {
                StringBuilder m =
                        SystemServiceManager$$ExternalSyntheticOutline0.m(
                                i3,
                                "sendReleaseSMIMECertificateForEncryption() : accId = ",
                                j,
                                ", containerId = ");
                m.append(", userId = ");
                m.append(i);
                Slog.d("ExchangeAccountPolicy", m.toString());
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL");
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mContext.sendBroadcastAsUser(
                            intent,
                            new UserHandle(i),
                            "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
                } finally {
                }
            }
        } else {
            if (i2 != 3) {
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i2,
                        "resetForcedSMIMECertificateInternal() : failed. There is unsupport type"
                            + " requested : ",
                        "ExchangeAccountPolicy");
                return false;
            }
            contentValues.put("ForceSmimeCertForSigning", Boolean.FALSE);
            if (!z) {
                StringBuilder m2 =
                        SystemServiceManager$$ExternalSyntheticOutline0.m(
                                i3,
                                "sendReleaseSMIMECertificateForSigning() : accId = ",
                                j,
                                ", containerId = ");
                m2.append(", userId = ");
                m2.append(i);
                Slog.d("ExchangeAccountPolicy", m2.toString());
                Intent intent2 =
                        new Intent(
                                "com.samsung.android.knox.intent.action.RELEASE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL");
                intent2.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
                clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mContext.sendBroadcastAsUser(
                            intent2,
                            new UserHandle(i),
                            "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
                } finally {
                }
            }
        }
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("adminUid", Integer.valueOf(i4));
        contentValues2.put("AccountId", Long.valueOf(j));
        return this.mEdmStorageProvider.update(
                        "ExchangeAccountTable", contentValues, contentValues2)
                > 0;
    }

    public final void sendAccountsChangedBroadcast(ContextInfo contextInfo) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            SettingsUtils.sendAccountsChangedBroadcast(this.mContext, i2);
            Message obtainMessage = obtainMessage(1);
            obtainMessage.arg1 = i;
            obtainMessage.arg2 = callingOrCurrentUserId;
            if (this.mRestartExtendDelay) {
                Log.i(
                        "ExchangeAccountPolicy",
                        "sendAccountsChangedBroadcast() : Delayed Broadcast");
                sendMessageDelayed(obtainMessage, 15000L);
            } else {
                Log.i("ExchangeAccountPolicy", "sendAccountsChangedBroadcast()");
                sendMessageDelayed(obtainMessage, 5000L);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean setAcceptAllCertificates(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setAcceptAllCertificates() : ", "ExchangeAccountPolicy", z);
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setAcceptAllCertificates_new() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mAcceptAllCertificates = z;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(
                this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public final long setAccountBaseParameters(
            ContextInfo contextInfo, String str, String str2, String str3, String str4, long j) {
        enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setAccountBaseParameters() : deprecated ");
        return -1L;
    }

    public final long setAccountCertificatePassword(ContextInfo contextInfo, String str) {
        int i =
                getEDM$13()
                        .enforcePermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_EXCHANGE")))
                        .mContainerId;
        long createIDforAccount = SettingsUtils.createIDforAccount();
        if (str == null) {
            return -1L;
        }
        try {
            SettingsUtils.setSecurityPassword("C#" + createIDforAccount, str);
            Slog.d("ExchangeAccountPolicy", "setAccountCertificatePassword() success");
            return createIDforAccount;
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "setAccountCertificatePassword() failed", e);
            return -1L;
        }
    }

    public final long setAccountEmailPassword(ContextInfo contextInfo, String str) {
        int i =
                getEDM$13()
                        .enforcePermissionByContext(
                                contextInfo,
                                new ArrayList(
                                        Arrays.asList(
                                                "com.samsung.android.knox.permission.KNOX_EXCHANGE")))
                        .mContainerId;
        long createIDforAccount = SettingsUtils.createIDforAccount();
        if (str == null) {
            return -1L;
        }
        try {
            String str2 = "E#" + createIDforAccount;
            SettingsUtils.setSecurityPassword(str2, str);
            if (str2 != null) {
                try {
                    ((HashMap) SettingsUtils.mServerPasswordFocus).put(str2, str);
                    Log.w("SettingsUtils", "setSecurityPasswordFocus() success");
                } catch (Exception unused) {
                    Log.w("SettingsUtils", "setSecurityPasswordFocus() failed");
                }
            } else {
                Log.w("SettingsUtils", "setSecurityPasswordFocus() failed : invalid parameter");
            }
            Slog.d("ExchangeAccountPolicy", "setAccountEmailPassword() success");
            return createIDforAccount;
        } catch (Exception e) {
            Log.e("ExchangeAccountPolicy", "setAccountEmailPassword() failed", e);
            return -1L;
        }
    }

    public final boolean setAccountName(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setAccountName() : " + j);
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("ExchangeAccountPolicy", "setAccountName() : accountName is null");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setAccountName_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mDisplayName = validStr;
        boolean updateEnterpriseExchangeAccount =
                EmailProviderHelper.updateEnterpriseExchangeAccount(
                        this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        StringBuilder sb = new StringBuilder("setAccountName() = ");
        sb.append(updateEnterpriseExchangeAccount);
        sb.append(", accountName =");
        sb.append(validStr);
        sb.append(" , accId = ");
        BatteryService$$ExternalSyntheticOutline0.m(sb, j, "ExchangeAccountPolicy");
        return updateEnterpriseExchangeAccount;
    }

    public final boolean setAlwaysVibrateOnEmailNotification(
            ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setAlwaysVibrateOnEmailNotification() : ", "ExchangeAccountPolicy", z);
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setAlwaysVibrateOnEmailNotification_new() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mEmailNotificationVibrateAlways = z;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(
                this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public final boolean setAsDefaultAccount(ContextInfo contextInfo, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setAsDefaultAccount() :  ", j, "ExchangeAccountPolicy");
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setAsDefaultAccount_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mIsDefault = true;
        boolean updateEnterpriseExchangeAccount =
                EmailProviderHelper.updateEnterpriseExchangeAccount(
                        this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        Slog.d(
                "ExchangeAccountPolicy",
                "setAsDefaultAccount() = " + updateEnterpriseExchangeAccount + " , accId = " + j);
        return updateEnterpriseExchangeAccount;
    }

    public final void setClientAuthCert(ContextInfo contextInfo, byte[] bArr, String str, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setClientAuthCert() : " + j);
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setClientAuthCert() : No exist accId : ", j, "ExchangeAccountPolicy");
            return;
        }
        if (bArr == null || str == null || j < 1) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setClientAuthCert() : error : ", j, "ExchangeAccountPolicy");
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            try {
                this.mRestartExtendDelay = true;
                removeMessages(1);
                Intent intent =
                        new Intent(
                                "com.samsung.android.knox.intent.action.EMAIL_INSTALL_CERT_INTERNAL");
                long accountCertificatePassword =
                        setAccountCertificatePassword(enforceExchangeAccountPermission, str);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.CERTIFICATE_DATA_INTERNAL", bArr);
                intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID_INTERNAL", j);
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.CERTIFICATE_PASSWD_ID_INTERNAL",
                        accountCertificatePassword);
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.KNOX_EMAIL");
            } catch (Exception e) {
                Log.e("ExchangeAccountPolicy", "setClientAuthCert() : failed", e);
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean setDataSyncs(
            ContextInfo contextInfo, boolean z, boolean z2, boolean z3, boolean z4, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setDataSyncs() : ", j, "ExchangeAccountPolicy");
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setSSL_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mSyncContacts = z2;
        enterpriseExchangeAccount.mSyncCalendar = z;
        enterpriseExchangeAccount.mSyncTasks = z3;
        enterpriseExchangeAccount.mSyncNotes = z4;
        boolean updateEnterpriseExchangeAccount =
                EmailProviderHelper.updateEnterpriseExchangeAccount(
                        this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        StringBuilder m =
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "setDataSyncs() : = ",
                        updateEnterpriseExchangeAccount,
                        " ,  syncCalendar = ",
                        z,
                        ", syncContacts = ");
        m.append(z2);
        m.append(", accid = ");
        m.append(j);
        Slog.d("ExchangeAccountPolicy", m.toString());
        return updateEnterpriseExchangeAccount;
    }

    public final boolean setEmailNotificationsState(ContextInfo contextInfo, long j, boolean z) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setEmailNotificationsState() : ", j, "ExchangeAccountPolicy");
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "allowInComingAttachments() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        boolean putPolicyState = putPolicyState(i2, i, j, "AllowNotificationChange", z);
        Slog.d(
                "ExchangeAccountPolicy",
                "setEmailNotificationsState() : = "
                        + putPolicyState
                        + " , accId  ="
                        + j
                        + " , enable  ="
                        + z);
        return putPolicyState;
    }

    public final int setForceSMIMECertificate(
            ContextInfo contextInfo, long j, String str, String str2) {
        return setForceSMIMECertificateInternal(
                contextInfo,
                j,
                str,
                str2,
                "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_INTERNAL");
    }

    public final boolean setForceSMIMECertificateAlias(
            ContextInfo contextInfo, long j, String str, String str2, int i) {
        boolean z;
        String str3 = str2;
        Slog.d(
                "ExchangeAccountPolicy",
                "setForceSMIMECertificateAlias() : accId = "
                        + j
                        + ", alias = "
                        + str3
                        + ", type = "
                        + i);
        if (!TextUtils.isEmpty(str)) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    "setForceSMIMECertificateAlias() : credential storage name = ",
                    str,
                    "ExchangeAccountPolicy");
        }
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setForceSMIMECertificateAlias() : accId = ", j, "ExchangeAccountPolicy");
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        enforceUCMPermission(enforceExchangeAccountPermission, str);
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        if (i != 1 && i != 0) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i,
                    "setForceSMIMECertificateAlias() : invalid type : ",
                    "ExchangeAccountPolicy");
            return false;
        }
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setForceSMIMECertificateAlias() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        if (!adminSatisfiesForceSMIMECertificateRules(
                enforceExchangeAccountPermission.mCallerUid, i == 0 ? 2 : 3, str3 != null, j)) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setForceSMIMECertificateAlias() : either a smime certificate has already been"
                        + " enforced on this user by other admin or admin "
                            + enforceExchangeAccountPermission.mCallerUid
                            + " is trying to release a certificate which was not enforced by him");
            return false;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            if (str3 != null) {
                try {
                    String convertUcmUri =
                            convertUcmUri(enforceExchangeAccountPermission, str, str3);
                    if (convertUcmUri == null) {
                        Log.i(
                                "ExchangeAccountPolicy",
                                "setForceSMIMECertificateAlias() : storage name "
                                        + str
                                        + " does not exist!");
                        return false;
                    }
                    str3 = convertUcmUri;
                } catch (Exception e) {
                    Log.e("ExchangeAccountPolicy", "setForceSMIMECertificateAlias() : failed. ", e);
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    z = false;
                }
            }
            long createIDforAccount = SettingsUtils.createIDforAccount();
            Intent intent =
                    new Intent(
                            "com.samsung.android.knox.intent.action.ENFORCE_SMIME_ALIAS_INTERNAL");
            intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
            intent.putExtra(
                    "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_NAME_INTERNAL",
                    str3);
            intent.putExtra("com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_TYPE", i);
            intent.putExtra(
                    "com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL",
                    createIDforAccount);
            if (!TextUtils.isEmpty(str)) {
                intent.putExtra(
                        "com.samsung.android.knox.intent.extra.ENFORCE_SMIME_ALIAS_STORAGE_INTERNAL",
                        str);
            }
            z =
                    AccountsReceiver.pushSMIMECertificate(
                            "S" + createIDforAccount,
                            new AccountSMIMECertificate(
                                    enforceExchangeAccountPermission, str3, null, -1));
            if (z) {
                this.mContext.sendBroadcastAsUser(
                        intent,
                        new UserHandle(callingOrCurrentUserId),
                        "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
            }
            Log.i(
                    "ExchangeAccountPolicy",
                    "setForceSMIMECertificateAlias() : accId = " + j + ", ret = " + z);
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final int setForceSMIMECertificateForEncryption(
            ContextInfo contextInfo, long j, String str, String str2) {
        return setForceSMIMECertificateInternal(
                contextInfo,
                j,
                str,
                str2,
                "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL");
    }

    public final int setForceSMIMECertificateForSigning(
            ContextInfo contextInfo, long j, String str, String str2) {
        return setForceSMIMECertificateInternal(
                contextInfo,
                j,
                str,
                str2,
                "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL");
    }

    public final int setForceSMIMECertificateInternal(
            ContextInfo contextInfo, long j, String str, String str2, String str3) {
        long j2;
        int i;
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : " + j + ", " + str3);
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setForceSMIMECertificateInternal() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return 3;
        }
        if (str == null || str.isEmpty()) {
            Log.i("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : certPath is null");
            return 1;
        }
        if (str2 == null || str2.isEmpty()) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setForceSMIMECertificateInternal() : certPassword is null");
            return 2;
        }
        if (!adminSatisfiesForceSMIMECertificateRules(
                i2,
                str3.equals(
                                "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL")
                        ? 3
                        : str3.equals(
                                        "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL")
                                ? 2
                                : 1,
                true,
                j)) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setForceSMIMECertificateInternal() : a smime certificate has already been"
                        + " enforced on this user");
            return 0;
        }
        synchronized (this) {
            long accountCertificatePassword =
                    setAccountCertificatePassword(enforceExchangeAccountPermission, str2);
            long createIDforAccount = SettingsUtils.createIDforAccount();
            Intent intent = new Intent(str3);
            intent.putExtra("com.samsung.android.knox.intent.extra.ACCOUNT_ID", j);
            intent.putExtra("com.samsung.android.knox.intent.extra.CERT_PATH_INTERNAL", str);
            intent.putExtra(
                    "com.samsung.android.knox.intent.extra.CERT_PASSWORD_ID_INTERNAL",
                    accountCertificatePassword);
            intent.putExtra(
                    "com.samsung.android.knox.intent.extra.CERT_RESULT_ID_INTERNAL",
                    createIDforAccount);
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                j2 = clearCallingIdentity;
            } catch (Exception e) {
                e = e;
                j2 = clearCallingIdentity;
            } catch (Throwable th) {
                th = th;
                j2 = clearCallingIdentity;
                Binder.restoreCallingIdentity(j2);
                throw th;
            }
            try {
                try {
                    this.mContext.sendBroadcastAsUser(
                            intent,
                            new UserHandle(callingOrCurrentUserId),
                            "com.samsung.android.knox.permission.SMIME_CERTIFICATE_INTERNAL");
                    boolean pushSMIMECertificate =
                            AccountsReceiver.pushSMIMECertificate(
                                    "S" + createIDforAccount,
                                    new AccountSMIMECertificate(
                                            enforceExchangeAccountPermission,
                                            str,
                                            str2,
                                            str3.equals(
                                                            "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_INTERNAL")
                                                    ? 1
                                                    : str3.equals(
                                                                    "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_ENCRYPTION_INTERNAL")
                                                            ? 2
                                                            : str3.equals(
                                                                            "com.samsung.android.knox.intent.action.FORCE_SMIME_CERTIFICATE_FOR_SIGNING_INTERNAL")
                                                                    ? 3
                                                                    : -1));
                    Log.i(
                            "ExchangeAccountPolicy",
                            "setForceSMIMECertificateInternal() : sendBroadcast success, accId = "
                                    + j);
                    i = pushSMIMECertificate ? -1 : 0;
                    Binder.restoreCallingIdentity(j2);
                } catch (Throwable th2) {
                    th = th2;
                    Binder.restoreCallingIdentity(j2);
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                Log.e("ExchangeAccountPolicy", "setForceSMIMECertificateInternal() : failed. ", e);
                Binder.restoreCallingIdentity(j2);
                i = 0;
                return i;
            }
        }
        return i;
    }

    public final boolean setIncomingAttachmentsSize(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setIncomingAttachmentsSize() : ", j, "ExchangeAccountPolicy");
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setIncomingAttachmentsSize() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        if (i < 0) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setIncomingAttachmentsSize() : Error :: invalid parameter.");
            return false;
        }
        boolean putPolicyState = putPolicyState(i3, i2, "IncomingAttachmentSize", i, j);
        BatteryService$$ExternalSyntheticOutline0.m(
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        "setIncomingAttachmentsSize() : = ",
                        " , size  =",
                        " , accId  =",
                        putPolicyState),
                j,
                "ExchangeAccountPolicy");
        return putPolicyState;
    }

    public final boolean setMaxCalendarAgeFilter(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setMaxCalendarAgeFilter() : ", j, "ExchangeAccountPolicy");
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setMaxCalendarAgeFilter() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        if ((i < 4 || i > 7) && i != 0) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxCalendarAgeFilter() : Error :: Invalid input parameters.");
            return false;
        }
        if (!putPolicyState(i3, i2, "MaxCalendarAgeFilter", i, j)) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxCalendarAgeFilter() : Error :: Fail to update policy.");
            return false;
        }
        int maxCalendarAgeFilter = getMaxCalendarAgeFilter(enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount.mSyncCalendarAge <= maxCalendarAgeFilter
                || maxCalendarAgeFilter == 0) {
            return true;
        }
        Log.i(
                "ExchangeAccountPolicy",
                "setMaxCalendarAgeFilter() : need to change Account value : "
                        + enterpriseExchangeAccount.mSyncCalendarAge
                        + " - "
                        + maxCalendarAgeFilter);
        enterpriseExchangeAccount.mSyncCalendarAge = maxCalendarAgeFilter;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(
                this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public final boolean setMaxEmailAgeFilter(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        int i2 = enforceExchangeAccountPermission.mContainerId;
        int i3 = enforceExchangeAccountPermission.mCallerUid;
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setMaxEmailAgeFilter() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        if (i < 0 || i > 5) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxEmailAgeFilter() : Error :: Invalid input parameters.");
            return false;
        }
        if (!putPolicyState(i3, i2, "MaxEmailAgeFilter", i, j)) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxEmailAgeFilter() : Error :: Fail to update policy.");
            return false;
        }
        int maxEmailAgeFilter = getMaxEmailAgeFilter(enforceExchangeAccountPermission, j);
        if (enterpriseExchangeAccount.mMaxEmailAgeFilter <= maxEmailAgeFilter
                || maxEmailAgeFilter == 0) {
            return true;
        }
        Log.i(
                "ExchangeAccountPolicy",
                "setMaxEmailAgeFilter() : need to change Account value : "
                        + enterpriseExchangeAccount.mMaxEmailAgeFilter
                        + " - "
                        + maxEmailAgeFilter);
        enterpriseExchangeAccount.mMaxEmailAgeFilter = maxEmailAgeFilter;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(
                this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public final boolean setMaxEmailBodyTruncationSize(ContextInfo contextInfo, int i, long j) {
        int i2;
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setMaxEmailBodyTruncationSize() : ", j, "ExchangeAccountPolicy");
        int i3 = enforceExchangeAccountPermission.mContainerId;
        int i4 = enforceExchangeAccountPermission.mCallerUid;
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setMaxEmailBodyTruncationSize() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        if (i < 0) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxEmailBodyTruncationSize() : Error :: invalid parameter.");
            return false;
        }
        if (!putPolicyState(i4, i3, "MaxEmailBodyTruncationSize", i, j)) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxEmailBodyTruncationSize() : Error :: Fail to update policy.");
            return false;
        }
        int maxEmailBodyTruncationSize =
                getMaxEmailBodyTruncationSize(enforceExchangeAccountPermission, j);
        if (!isEmailHTMLAllowed(
                        enforceExchangeAccountPermission, enterpriseExchangeAccount.mEmailAddress)
                && maxEmailBodyTruncationSize != 0
                && ((i2 = enterpriseExchangeAccount.mMaxEmailBodyTruncationSize) == 0
                        || i2 > maxEmailBodyTruncationSize * 1024)) {
            StringBuilder sb =
                    new StringBuilder(
                            "setMaxEmailBodyTruncationSize() : need to change Account value1 : ");
            sb.append(enterpriseExchangeAccount.mMaxEmailBodyTruncationSize);
            sb.append(" - ");
            int i5 = maxEmailBodyTruncationSize * 1024;
            sb.append(i5);
            Slog.d("ExchangeAccountPolicy", sb.toString());
            enterpriseExchangeAccount.mMaxEmailBodyTruncationSize = i5;
            EmailProviderHelper.updateEnterpriseExchangeAccount(
                    this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
            Log.i("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize() : updated account.");
        }
        Log.i("ExchangeAccountPolicy", "setMaxEmailBodyTruncationSize()  = true");
        return true;
    }

    public final boolean setMaxEmailHTMLBodyTruncationSize(ContextInfo contextInfo, int i, long j) {
        int i2;
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setMaxEmailHTMLBodyTruncationSize() : ", j, "ExchangeAccountPolicy");
        int i3 = enforceExchangeAccountPermission.mContainerId;
        int i4 = enforceExchangeAccountPermission.mCallerUid;
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setMaxEmailHTMLBodyTruncationSize() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        if (i < 0) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxEmailHTMLBodyTruncationSize() : Error :: invalid parameter.");
            return false;
        }
        if (!putPolicyState(i4, i3, "MaxEmailHTMLBodyTruncationSize", i, j)) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxEmailHTMLBodyTruncationSize() : Error :: Fail to update policy.");
            return false;
        }
        int maxEmailHTMLBodyTruncationSize =
                getMaxEmailHTMLBodyTruncationSize(enforceExchangeAccountPermission, j);
        boolean isEmailHTMLAllowed =
                isEmailHTMLAllowed(
                        enforceExchangeAccountPermission, enterpriseExchangeAccount.mEmailAddress);
        if (enterpriseExchangeAccount.mAllowHTMLEmail
                && isEmailHTMLAllowed
                && maxEmailHTMLBodyTruncationSize != 0
                && ((i2 = enterpriseExchangeAccount.mMaxEmailHtmlBodyTruncationSize) == 0
                        || i2 > maxEmailHTMLBodyTruncationSize * 1024)) {
            StringBuilder sb =
                    new StringBuilder(
                            "setMaxEmailHTMLBodyTruncationSize() : need to change Account value1"
                                + " : ");
            sb.append(enterpriseExchangeAccount.mMaxEmailHtmlBodyTruncationSize);
            sb.append(" - ");
            int i5 = maxEmailHTMLBodyTruncationSize * 1024;
            sb.append(i5);
            Slog.d("ExchangeAccountPolicy", sb.toString());
            enterpriseExchangeAccount.mMaxEmailHtmlBodyTruncationSize = i5;
            EmailProviderHelper.updateEnterpriseExchangeAccount(
                    this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
            Log.i(
                    "ExchangeAccountPolicy",
                    "setMaxEmailHTMLBodyTruncationSize() : updated account.");
        }
        Log.i("ExchangeAccountPolicy", "setMaxEmailHTMLBodyTruncationSize()  = true");
        return true;
    }

    public final boolean setPassword(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        Log.i(
                "ExchangeAccountPolicy",
                "setPassword() : "
                        + Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission));
        String validStr = SettingsUtils.getValidStr(str);
        if (validStr == null) {
            Log.i("ExchangeAccountPolicy", "setPassword() : Invalid input parameter.");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setPassword_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mPassword = validStr;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(
                this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public final boolean setPastDaysToSync(ContextInfo contextInfo, int i, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setPastDaysToSync() : ", j, "ExchangeAccountPolicy");
        if (1 > i || 6 < i) {
            Log.i("ExchangeAccountPolicy", "setPastDaysToSync : Error :: Invalid input parameter.");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setPastDaysToSync_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mSyncLookback = i;
        boolean updateEnterpriseExchangeAccount =
                EmailProviderHelper.updateEnterpriseExchangeAccount(
                        this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        BatteryService$$ExternalSyntheticOutline0.m(
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        "setPastDaysToSync() = ",
                        " , pastDaysToSync = ",
                        " , accId = ",
                        updateEnterpriseExchangeAccount),
                j,
                "ExchangeAccountPolicy");
        return updateEnterpriseExchangeAccount;
    }

    public final boolean setProtocolVersion(ContextInfo contextInfo, String str, long j) {
        enforceExchangeAccountPermission(contextInfo);
        return false;
    }

    public final boolean setReleaseSMIMECertificate(ContextInfo contextInfo, long j) {
        return setReleaseSMIMECertificateInternal(contextInfo, j, 1);
    }

    public final boolean setReleaseSMIMECertificateForEncryption(ContextInfo contextInfo, long j) {
        return setReleaseSMIMECertificateInternal(contextInfo, j, 2);
    }

    public final boolean setReleaseSMIMECertificateForSigning(ContextInfo contextInfo, long j) {
        return setReleaseSMIMECertificateInternal(contextInfo, j, 3);
    }

    public final boolean setReleaseSMIMECertificateInternal(
            ContextInfo contextInfo, long j, int i) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        int callingOrCurrentUserId =
                Utils.getCallingOrCurrentUserId(enforceExchangeAccountPermission);
        boolean z = false;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setReleaseSMIMECertificateInternal() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        if (!adminSatisfiesForceSMIMECertificateRules(
                enforceExchangeAccountPermission.mCallerUid, i, false, j)) {
            Log.i(
                    "ExchangeAccountPolicy",
                    "setReleaseSMIMECertificateInternal() : admin "
                            + enforceExchangeAccountPermission.mCallerUid
                            + " is trying to release a certificate which was not enforced by him");
            return false;
        }
        if (i == 1 || i == 2) {
            z =
                    resetForcedSMIMECertificateInternal(
                            enforceExchangeAccountPermission, j, callingOrCurrentUserId, 2, false);
            ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                    "setReleaseSMIMECertificateInternal() : release SMIME Encryption = ",
                    "ExchangeAccountPolicy",
                    z);
        }
        if (i != 1 && i != 3) {
            return z;
        }
        boolean resetForcedSMIMECertificateInternal =
                resetForcedSMIMECertificateInternal(
                        enforceExchangeAccountPermission, j, callingOrCurrentUserId, 3, false);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setReleaseSMIMECertificateInternal() : release SMIME Signing = ",
                "ExchangeAccountPolicy",
                resetForcedSMIMECertificateInternal);
        return resetForcedSMIMECertificateInternal;
    }

    public final boolean setRequireEncryptedSMIMEMessages(
            ContextInfo contextInfo, long j, boolean z) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setRequireEncryptedSMIMEMessages () : ", j, "ExchangeAccountPolicy");
        int i = enforceExchangeAccountPermission.mContainerId;
        int i2 = enforceExchangeAccountPermission.mCallerUid;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setRequireEncryptedSMIMEMessages() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        boolean putPolicyState = putPolicyState(i2, i, j, "ReqEncryptSmime", z);
        if (!putPolicyState) {
            Log.i("ExchangeAccountPolicy", "setRequireEncryptedSMIMEMessages() : failed.");
        }
        return putPolicyState;
    }

    public final boolean setRequireSignedSMIMEMessages(ContextInfo contextInfo, long j, boolean z) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setRequireSignedSMIMEMessages() : ", j, "ExchangeAccountPolicy");
        int i = enforceExchangeAccountPermission.mCallerUid;
        int i2 = enforceExchangeAccountPermission.mContainerId;
        if (!isValidEASAccountId(enforceExchangeAccountPermission, j)) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setRequireSignedSMIMEMessages() : No exist accId : ",
                    j,
                    "ExchangeAccountPolicy");
            return false;
        }
        boolean putPolicyState = putPolicyState(i, i2, j, "ReqSigSmime", z);
        if (!putPolicyState) {
            Log.i("ExchangeAccountPolicy", "setRequireSignedSMIMEMessages() : failed.");
        }
        return putPolicyState;
    }

    public final boolean setSSL(ContextInfo contextInfo, boolean z, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "setSSL() : ", "ExchangeAccountPolicy", z);
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setSSL_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mUseSSL = z;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(
                this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public final boolean setSenderName(ContextInfo contextInfo, String str, long j) {
        enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSenderName() :  deprecated , " + str);
        return false;
    }

    public final boolean setSignature(ContextInfo contextInfo, String str, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setSignature() : ", j, "ExchangeAccountPolicy");
        if (str == null) {
            Log.i("ExchangeAccountPolicy", "setSignature() : signature is null");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setSignature_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mSignature = str;
        return EmailProviderHelper.updateEnterpriseExchangeAccount(
                this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
    }

    public final boolean setSilentVibrateOnEmailNotification(
            ContextInfo contextInfo, boolean z, long j) {
        enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSilentVibrateOnEmailNotification() : deprecated. " + z);
        return false;
    }

    public final boolean setSyncInterval(ContextInfo contextInfo, int i, long j) {
        enforceExchangeAccountPermission(contextInfo);
        Log.i("ExchangeAccountPolicy", "setSyncInterval() : deprecated , " + j);
        return false;
    }

    public final boolean setSyncPeakTimings(
            ContextInfo contextInfo, int i, int i2, int i3, long j) {
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setSyncPeakTimings() : ", j, "ExchangeAccountPolicy");
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        if (i < 0 || i > 127) {
            Log.i("ExchangeAccountPolicy", "setSyncPeakTimings() : peakDays is invalid");
            return false;
        }
        if (i2 < 0 || i2 > 1440) {
            Log.i("ExchangeAccountPolicy", "setSyncPeakTimings() : peakStartMinute is invalid");
            return false;
        }
        if (i3 < 0 || i3 > 1440) {
            Log.i("ExchangeAccountPolicy", "setSyncPeakTimings() : peakEndMinute is invalid");
            return false;
        }
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setSSL_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mPeakDays = i;
        enterpriseExchangeAccount.mPeakStartMinute = i2;
        enterpriseExchangeAccount.mPeakEndMinute = i3;
        boolean updateEnterpriseExchangeAccount =
                EmailProviderHelper.updateEnterpriseExchangeAccount(
                        this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        StringBuilder m =
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        "setSyncPeakTimings() = ",
                        " , peakDays =",
                        ", mPeakStartMinute = ",
                        updateEnterpriseExchangeAccount);
        ServiceKeeper$$ExternalSyntheticOutline0.m(i2, i3, ", peakEndMinute = ", ", accid = ", m);
        BatteryService$$ExternalSyntheticOutline0.m(m, j, "ExchangeAccountPolicy");
        return updateEnterpriseExchangeAccount;
    }

    public final boolean setSyncSchedules(ContextInfo contextInfo, int i, int i2, int i3, long j) {
        ContextInfo enforceExchangeAccountPermission =
                enforceExchangeAccountPermission(contextInfo);
        GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                "setSyncSchedules() :  ", j, "ExchangeAccountPolicy");
        EnterpriseExchangeAccount enterpriseExchangeAccount =
                EmailProviderHelper.getEnterpriseExchangeAccount(
                        j, this.mContext, enforceExchangeAccountPermission);
        if (enterpriseExchangeAccount == null) {
            GenieMemoryManager$ReclaimerHandler$$ExternalSyntheticOutline0.m(
                    "setSyncSchedules_new() : No exist accId : ", j, "ExchangeAccountPolicy");
            return false;
        }
        enterpriseExchangeAccount.mPeakSyncSchedule = i;
        enterpriseExchangeAccount.mOffPeakSyncSchedule = i2;
        enterpriseExchangeAccount.mRoamingSyncSchedule = i3;
        boolean updateEnterpriseExchangeAccount =
                EmailProviderHelper.updateEnterpriseExchangeAccount(
                        this.mContext, enforceExchangeAccountPermission, enterpriseExchangeAccount);
        StringBuilder m =
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        i,
                        "setSyncSchedules() : = ",
                        " ,  peakSyncSchedule = ",
                        ", offPeakSyncSchedule = ",
                        updateEnterpriseExchangeAccount);
        ServiceKeeper$$ExternalSyntheticOutline0.m(
                i2, i3, ", roamingSyncSchedule = ", ", accid = ", m);
        BatteryService$$ExternalSyntheticOutline0.m(m, j, "ExchangeAccountPolicy");
        return updateEnterpriseExchangeAccount;
    }

    @Override // com.android.server.enterprise.EnterpriseServiceCallback
    public final void systemReady() {}
}
