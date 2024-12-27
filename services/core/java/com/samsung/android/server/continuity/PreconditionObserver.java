package com.samsung.android.server.continuity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.OnAccountsUpdateListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.GestureWakeup$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0;

import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper;
import com.samsung.android.server.continuity.common.Utils;
import com.samsung.android.server.continuity.sem.SemWrapper;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PreconditionObserver {
    public static final int FEATURE_CONTINUITY;
    public static final String SAMSUNG_ACCOUNT_TYPE;
    public final Uri URI_CONTINUITY;
    public final Uri URI_HW_SHARING;
    public final Uri URI_MULTI_CONTROL;
    public final Uri URI_VIDEO_CALL_CONTINUITY;
    public final AnonymousClass1 mAccountChangeReceiver;
    public final AutoSwitchSettingHelper mAutoSwitchSettingHelper;
    public final Context mContext;
    public Account mCurrentAccount;
    public boolean mIsAddedAccountListener;
    public boolean mIsPkgObserverRegistered;
    public McfDeviceSyncManager.AnonymousClass2 mListener;
    public final AbstractPreconditionObserver$$ExternalSyntheticLambda0 mOnAccountsUpdateListener;
    public final AbstractPreconditionObserver$2 mPkgReceiver;
    public boolean mRegisterSettingsObserver;
    public final AbstractPreconditionObserver$1 mSettingObserver;
    public int mState;
    public int mUserId = -10000;

    /* renamed from: -$$Nest$mnotifyReplacedPackage, reason: not valid java name */
    public static void m1226$$Nest$mnotifyReplacedPackage(
            PreconditionObserver preconditionObserver, boolean z) {
        McfDeviceSyncManager.AnonymousClass2 anonymousClass2 = preconditionObserver.mListener;
        if (anonymousClass2 != null) {
            McfDeviceSyncManager mcfDeviceSyncManager = McfDeviceSyncManager.this;
            boolean meetConditions = mcfDeviceSyncManager.mPreconditionObserver.meetConditions();
            Log.d(
                    "[MCF_DS_SYS]_McfDsManager",
                    "onPackageReplaced - "
                            + mcfDeviceSyncManager.mIsValidState
                            + ", "
                            + meetConditions);
            mcfDeviceSyncManager.mIsValidState = meetConditions;
            if (meetConditions) {
                mcfDeviceSyncManager.removeMessage(4);
                if (z) {
                    mcfDeviceSyncManager.removeAndSendMessageDelayed(4, 1000L);
                } else {
                    mcfDeviceSyncManager.removeAndSendMessageDelayed(4, 500L);
                }
            }
        }
    }

    /* renamed from: -$$Nest$msetInstalledFlag, reason: not valid java name */
    public static void m1227$$Nest$msetInstalledFlag(
            PreconditionObserver preconditionObserver, String str) {
        preconditionObserver.getClass();
        if ("com.samsung.android.scloud".equals(str)) {
            Log.i("[MCF_DS_SYS]_PreconditionObserver", "setInstalledFlag - Scloud");
            preconditionObserver.setFlag(240);
        } else if ("com.samsung.android.mcfds".equals(str)) {
            Log.i("[MCF_DS_SYS]_PreconditionObserver", "setInstalledFlag - MCFDS");
            preconditionObserver.setFlag(3840);
        }
    }

    static {
        int i;
        if (Utils.isWatch()) {
            i = 15;
        } else {
            UserHandle userHandle = SemWrapper.SEM_ALL;
            i =
                    SemFloatingFeature.getInstance()
                            .getInt("SEC_FLOATING_FEATURE_MCF_SUPPORT_CONTINUITY");
        }
        FEATURE_CONTINUITY = i;
        SAMSUNG_ACCOUNT_TYPE =
                Utils.isWatch()
                        ? "com.samsung.android.wearable.samsungaccount"
                        : "com.osp.app.signin";
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.server.continuity.AbstractPreconditionObserver$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.samsung.android.server.continuity.AbstractPreconditionObserver$2] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.samsung.android.server.continuity.AbstractPreconditionObserver$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.server.continuity.AbstractPreconditionObserver$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r4v10, types: [com.samsung.android.server.continuity.PreconditionObserver$1] */
    public PreconditionObserver(Context context) {
        final Handler handler = new Handler(Looper.getMainLooper());
        this.mSettingObserver =
                new ContentObserver(
                        handler) { // from class:
                                   // com.samsung.android.server.continuity.AbstractPreconditionObserver$1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        if (uri == null) {
                            return;
                        }
                        try {
                            if (PreconditionObserver.this.URI_CONTINUITY.equals(uri)) {
                                PreconditionObserver.this.onChangeSettings(3);
                            } else if (PreconditionObserver.this.URI_MULTI_CONTROL.equals(uri)) {
                                PreconditionObserver.this.onChangeSettings(7);
                            } else if (PreconditionObserver.this.URI_VIDEO_CALL_CONTINUITY.equals(
                                    uri)) {
                                PreconditionObserver.this.onChangeSettings(8);
                            } else if (PreconditionObserver.this.URI_HW_SHARING.equals(uri)) {
                                PreconditionObserver.this.onChangeSettings(9);
                            }
                        } catch (NullPointerException unused) {
                        }
                    }
                };
        this.mPkgReceiver =
                new BroadcastReceiver() { // from class:
                                          // com.samsung.android.server.continuity.AbstractPreconditionObserver$2
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        Uri data;
                        String schemeSpecificPart;
                        String action = intent.getAction();
                        if (action == null || (data = intent.getData()) == null) {
                            return;
                        }
                        schemeSpecificPart = data.getSchemeSpecificPart();
                        switch (action) {
                            case "android.intent.action.PACKAGE_REPLACED":
                                PreconditionObserver.m1227$$Nest$msetInstalledFlag(
                                        PreconditionObserver.this, schemeSpecificPart);
                                PreconditionObserver.m1226$$Nest$mnotifyReplacedPackage(
                                        PreconditionObserver.this, false);
                                break;
                            case "android.intent.action.PACKAGE_RESTARTED":
                                McfDeviceSyncManager.AnonymousClass2 anonymousClass2 =
                                        PreconditionObserver.this.mListener;
                                if (anonymousClass2 != null) {
                                    McfDeviceSyncManager mcfDeviceSyncManager =
                                            McfDeviceSyncManager.this;
                                    boolean meetConditions =
                                            mcfDeviceSyncManager.mPreconditionObserver
                                                    .meetConditions();
                                    Log.d(
                                            "[MCF_DS_SYS]_McfDsManager",
                                            "onPackageRestarted - "
                                                    + mcfDeviceSyncManager.mIsValidState
                                                    + ", "
                                                    + meetConditions);
                                    mcfDeviceSyncManager.mIsValidState = meetConditions;
                                    if (meetConditions) {
                                        mcfDeviceSyncManager.removeMessage(3);
                                        mcfDeviceSyncManager.sendMessageDelayed(2, 6, 1000L);
                                        break;
                                    }
                                }
                                break;
                            case "android.intent.action.PACKAGE_REMOVED":
                                PreconditionObserver preconditionObserver =
                                        PreconditionObserver.this;
                                preconditionObserver.getClass();
                                if ("com.samsung.android.scloud".equals(schemeSpecificPart)) {
                                    Log.i(
                                            "[MCF_DS_SYS]_PreconditionObserver",
                                            "unsetInstalledFlag - Scloud");
                                    preconditionObserver.mState &= -241;
                                } else if ("com.samsung.android.mcfds".equals(schemeSpecificPart)) {
                                    Log.i(
                                            "[MCF_DS_SYS]_PreconditionObserver",
                                            "unsetInstalledFlag - MCFDS");
                                    preconditionObserver.mState &= -3841;
                                }
                                PreconditionObserver.this.notifyChanged(5);
                                break;
                            case "android.intent.action.PACKAGE_ADDED":
                                boolean booleanExtra =
                                        intent.getBooleanExtra(
                                                "android.intent.extra.REPLACING", false);
                                PreconditionObserver.m1227$$Nest$msetInstalledFlag(
                                        PreconditionObserver.this, schemeSpecificPart);
                                if (!booleanExtra) {
                                    Log.i(
                                            "[MCF_DS_SYS]_PreconditionObserver",
                                            "handlePackageAdded - without EXTRA_REPLACING");
                                    PreconditionObserver.this.notifyChanged(5);
                                    break;
                                } else {
                                    Log.i(
                                            "[MCF_DS_SYS]_PreconditionObserver",
                                            "handlePackageAdded - with EXTRA_REPLACING");
                                    PreconditionObserver.m1226$$Nest$mnotifyReplacedPackage(
                                            PreconditionObserver.this, true);
                                    break;
                                }
                        }
                    }
                };
        this.mOnAccountsUpdateListener =
                new OnAccountsUpdateListener() { // from class:
                                                 // com.samsung.android.server.continuity.AbstractPreconditionObserver$$ExternalSyntheticLambda0
                    @Override // android.accounts.OnAccountsUpdateListener
                    public final void onAccountsUpdated(Account[] accountArr) {
                        PreconditionObserver preconditionObserver = PreconditionObserver.this;
                        preconditionObserver.getClass();
                        Log.d(
                                "[MCF_DS_SYS]_PreconditionObserver",
                                "onAccountsUpdated - " + accountArr.length);
                        preconditionObserver.checkAccountUpdated();
                    }
                };
        this.mContext = context;
        this.mAutoSwitchSettingHelper =
                new AutoSwitchSettingHelper(
                        context,
                        new Consumer() { // from class:
                                         // com.samsung.android.server.continuity.AbstractPreconditionObserver$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                PreconditionObserver preconditionObserver =
                                        PreconditionObserver.this;
                                ((Boolean) obj).booleanValue();
                                preconditionObserver.onChangeSettings(4);
                            }
                        });
        if (Utils.isWatch()) {
            this.URI_CONTINUITY = Settings.System.getUriFor("mcf_watch_continuity");
        } else {
            this.URI_CONTINUITY = Settings.System.getUriFor("mcf_continuity");
        }
        this.URI_MULTI_CONTROL = Settings.System.getUriFor("multi_control_enabled");
        this.URI_VIDEO_CALL_CONTINUITY = Settings.System.getUriFor("vcc_continuity");
        this.URI_HW_SHARING = Settings.System.getUriFor("hwrs_service");
        this.mAccountChangeReceiver =
                new BroadcastReceiver() { // from class:
                    // com.samsung.android.server.continuity.PreconditionObserver.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        if ("android.accounts.LOGIN_ACCOUNTS_CHANGED".equals(intent.getAction())) {
                            Log.i(
                                    "[MCF_DS_SYS]_PreconditionObserver",
                                    "mAccountChangeReceiver.onReceive -"
                                        + " LOGIN_ACCOUNTS_CHANGED_ACTION");
                            PreconditionObserver.this.checkAccountUpdated();
                        }
                    }
                };
    }

    public static boolean isSupported(int i) {
        int i2 = FEATURE_CONTINUITY;
        return i2 > 0 && (i2 & i) == i;
    }

    public static boolean isSupportedContinuity() {
        return isSupported(1) || isSupported(4) || isSupported(8);
    }

    public final void checkAccountUpdated() {
        Account samsungAccount = getSamsungAccount();
        if (samsungAccount != null && Utils.DEBUG) {
            Log.d("[MCF_DS_SYS]_PreconditionObserver", "checkAccountUpdated - " + samsungAccount);
        }
        Account account = this.mCurrentAccount;
        if (!((account == null || samsungAccount == null)
                ? (account == null && samsungAccount == null) ? false : true
                : !account.name.equals(samsungAccount.name))) {
            Log.d("[MCF_DS_SYS]_PreconditionObserver", "checkAccountUpdated - is not changed");
            return;
        }
        this.mCurrentAccount = samsungAccount;
        if (samsungAccount != null) {
            if (!Utils.isHighPowerConsumptionChipset()) {
                setContinuitySetting(1);
                updateSettings(isEnableSettings());
            }
            setFlag(15);
        } else {
            setContinuitySetting(0);
            this.mState &= -16;
        }
        notifyChanged(2);
    }

    public final Account getSamsungAccount() {
        Account[] accountsByType =
                AccountManager.get(this.mContext).getAccountsByType(SAMSUNG_ACCOUNT_TYPE);
        GestureWakeup$$ExternalSyntheticOutline0.m(
                new StringBuilder("getSamsungAccount - "),
                accountsByType.length,
                "[MCF_DS_SYS]_PreconditionObserver");
        Account account = accountsByType.length > 0 ? accountsByType[0] : null;
        if (account != null && Utils.DEBUG) {
            Log.d("[MCF_DS_SYS]_PreconditionObserver", "getSamsungAccount - " + account);
        }
        return account;
    }

    public final boolean isEnableSettings() {
        boolean z = isSupported(2) && this.mAutoSwitchSettingHelper.mIsAutoSwitchModeEnabled;
        boolean z2 =
                !Utils.isWatch()
                        ? !(isSupportedContinuity() && isEnableSettings("mcf_continuity"))
                        : !(isSupportedContinuity() && isEnableSettings("mcf_watch_continuity"));
        boolean z3 = isSupportedContinuity() && isEnableSettings("multi_control_enabled");
        boolean z4 = isSupportedContinuity() && isEnableSettings("vcc_continuity");
        boolean z5 = isSupportedContinuity() && isEnableSettings("hwrs_service");
        StringBuilder m =
                FullScreenMagnificationGestureHandler$$ExternalSyntheticOutline0.m(
                        "isEnableSettings - ", z, ", ", z2, ", ");
        BatteryService$$ExternalSyntheticOutline0.m(m, z3, ", ", z4, ", ");
        RCPManagerService$$ExternalSyntheticOutline0.m("[MCF_DS_SYS]_PreconditionObserver", m, z5);
        return z || z2 || z3 || z4 || z5;
    }

    public final boolean isEnableSettings(String str) {
        try {
            Context context = this.mContext;
            int i = this.mUserId;
            UserHandle userHandle = SemWrapper.SEM_ALL;
            return Settings.System.semGetIntForUser(context.getContentResolver(), str, 0, i) == 1;
        } catch (IllegalArgumentException | SecurityException e) {
            Log.w("[MCF_DS_SYS]_PreconditionObserver", "isEnableSettings - " + e.getMessage());
            return false;
        }
    }

    public final boolean meetConditions() {
        Log.i(
                "[MCF_DS_SYS]_PreconditionObserver",
                "meetConditions - state : " + Integer.toHexString(this.mState));
        return this.mState == 65535;
    }

    public final void notifyChanged(int i) {
        McfDeviceSyncManager.AnonymousClass2 anonymousClass2 = this.mListener;
        if (anonymousClass2 != null) {
            int i2 = this.mState;
            McfDeviceSyncManager mcfDeviceSyncManager = McfDeviceSyncManager.this;
            boolean meetConditions = mcfDeviceSyncManager.mPreconditionObserver.meetConditions();
            if (mcfDeviceSyncManager.mIsValidState == meetConditions) {
                return;
            }
            StringBuilder sb = new StringBuilder("onChanged - ");
            BatteryService$$ExternalSyntheticOutline0.m(
                    sb, mcfDeviceSyncManager.mIsValidState, ", ", meetConditions, ", ");
            GestureWakeup$$ExternalSyntheticOutline0.m(sb, i, "[MCF_DS_SYS]_McfDsManager");
            mcfDeviceSyncManager.mIsValidState = meetConditions;
            if (meetConditions) {
                mcfDeviceSyncManager.removeMessage(3);
                mcfDeviceSyncManager.sendMessageDelayed(2, i, 0L);
                return;
            }
            mcfDeviceSyncManager.removeMessage(2);
            if ((i2 & 3840) > 0) {
                mcfDeviceSyncManager.removeAndSendMessageDelayed(3, 3000L);
            } else {
                mcfDeviceSyncManager.unbindMcf();
            }
        }
    }

    public final void onChangeSettings(int i) {
        boolean isEnableSettings = isEnableSettings();
        if (isEnableSettings == ((this.mState & 61440) == 61440)) {
            return;
        }
        updateSettings(isEnableSettings);
        notifyChanged(i);
    }

    public final void setContinuitySetting(int i) {
        if (isSupportedContinuity()) {
            DirEncryptService$$ExternalSyntheticOutline0.m(
                    i, "setContinuitySetting : ", "[MCF_DS_SYS]_PreconditionObserver");
            Context context = this.mContext;
            int i2 = this.mUserId;
            UserHandle userHandle = SemWrapper.SEM_ALL;
            Settings.System.semPutIntForUser(context.getContentResolver(), "mcf_continuity", i, i2);
            if (i == 1) {
                Intent intent = new Intent();
                intent.setAction("com.samsung.android.mcfds.LOG_SAMSUNG_ANALYTICS");
                intent.putExtra("type", "event");
                intent.putExtra("screenId", "101");
                intent.putExtra("eventId", "10100");
                intent.putExtra("detail", "auto");
                intent.setPackage("com.samsung.android.mcfds");
                this.mContext.sendBroadcast(intent);
            }
        }
    }

    public final void setFlag(int i) {
        this.mState = i | this.mState;
    }

    public final void updateSettings(boolean z) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m(
                "updateSettings - ", "[MCF_DS_SYS]_PreconditionObserver", z);
        if (z) {
            setFlag(61440);
        } else {
            this.mState &= -61441;
        }
    }
}
